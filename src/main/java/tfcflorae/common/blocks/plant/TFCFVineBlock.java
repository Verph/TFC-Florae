package tfcflorae.common.blocks.plant;

import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.IForgeBlockExtension;
import net.dries007.tfc.common.blocks.plant.PlantBlock;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.registry.RegistryPlant;

public abstract class TFCFVineBlock extends VineBlock implements IForgeBlockExtension
{
    public static final float AABB_OFFSET = 1.0F;
    public static final VoxelShape UP_AABB = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    public static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    public static final VoxelShape EAST_AABB = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    public static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    public static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
    public final Map<BlockState, VoxelShape> shapesCache;

    public final ExtendedProperties properties;

    public static TFCFVineBlock create(RegistryPlant plant, ExtendedProperties properties)
    {
        return new TFCFVineBlock(properties)
        {
            @Override
            public RegistryPlant getPlant()
            {
                return plant;
            }
        };
    }

    protected TFCFVineBlock(ExtendedProperties properties)
    {
        super(properties.properties());
        this.properties = properties;

        BlockState stateDefinition = getStateDefinition().any().setValue(UP, Boolean.valueOf(false)).setValue(NORTH, Boolean.valueOf(false)).setValue(EAST, Boolean.valueOf(false)).setValue(SOUTH, Boolean.valueOf(false)).setValue(WEST, Boolean.valueOf(false));
        IntegerProperty stageProperty = getPlant().getStageProperty();
        if (stageProperty != null)
        {
            stateDefinition = stateDefinition.setValue(stageProperty, 0);
        }
        registerDefaultState(stateDefinition);
        this.shapesCache = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), TFCFVineBlock::calculateShapeNew)));
    }

    public static VoxelShape calculateShapeNew(BlockState state)
    {
        VoxelShape voxelshape = Shapes.empty();
        if (state.getValue(UP))
        {
            voxelshape = UP_AABB;
        }
        if (state.getValue(NORTH))
        {
            voxelshape = Shapes.or(voxelshape, NORTH_AABB);
        }
        if (state.getValue(SOUTH))
        {
            voxelshape = Shapes.or(voxelshape, SOUTH_AABB);
        }
        if (state.getValue(EAST))
        {
            voxelshape = Shapes.or(voxelshape, EAST_AABB);
        }
        if (state.getValue(WEST))
        {
            voxelshape = Shapes.or(voxelshape, WEST_AABB);
        }
        return voxelshape.isEmpty() ? Shapes.block() : voxelshape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context)
    {
        return this.shapesCache.get(state);
    }

    /**
     * Gets the plant metadata for this block.
     *
     * The stage property is isolated and referenced via this as it is needed in the {@link Block} constructor - which builds the state container, and requires all property references to be computed in {@link Block#createBlockStateDefinition(StateDefinition.Builder)}.
     *
     * See the various {@link PlantBlock#create(RegistryPlant, ExtendedProperties)} methods and subclass versions for how to use.
     */
    public abstract RegistryPlant getPlant();

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        boolean flag = blockstate.is(this);
        BlockState blockstate1 = flag ? blockstate : this.defaultBlockState();

        for(Direction direction : context.getNearestLookingDirections())
        {
            if (direction != Direction.DOWN)
            {
                BooleanProperty bool = getPropertyForFace(direction);
                boolean flag1 = flag && blockstate.getValue(bool);
                if (!flag1 && this.canSupportAtFaceNew(context.getLevel(), context.getClickedPos(), direction))
                {
                    if (getPlant().getStageProperty() != null)
                    {
                        return blockstate1.setValue(bool, Boolean.valueOf(true)).setValue(getPlant().getStageProperty(), getPlant().stageFor(Calendars.SERVER.getCalendarMonthOfYear()));
                    }
                    else
                    {
                        return blockstate1.setValue(bool, Boolean.valueOf(true));
                    }
                }
            }
        }

        return flag ? blockstate1 : null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        if (getPlant().getStageProperty() != null)
        {
            builder.add(getPlant().getStageProperty());
        }
    }

    @Override
    public float getSpeedFactor()
    {
        final float modifier = TFCConfig.SERVER.plantsMovementModifier.get().floatValue(); // 0.0 = full speed factor, 1.0 = no modifier
        return Helpers.lerp(modifier, speedFactor, 1.0f);
    }

    protected BlockState updateStateWithCurrentMonth(BlockState state)
    {
        return getPlant().getStageProperty() != null ? state.setValue(getPlant().getStageProperty(), getPlant().stageFor(Calendars.SERVER.getCalendarMonthOfYear())) : state;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        super.randomTick(state, level, pos, random);
        level.setBlockAndUpdate(pos, updateStateWithCurrentMonth(state));
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (Direction direction : UPDATE_SHAPE_ORDER)
        {
            BlockState attachedState = level.getBlockState(mutablePos.setWithOffset(pos, direction.getOpposite()));
            if (Helpers.isBlock(attachedState, TFCTags.Blocks.CREEPING_PLANTABLE_ON) || attachedState.getBlock() instanceof LeavesBlock || this.hasFacesNew(this.getUpdatedStateNew(state, level, pos)))
            {
                return true;
            }
        }
        return false;
    }

    public boolean hasFacesNew(BlockState state)
    {
        return this.countFacesNew(state) > 0;
    }

    public int countFacesNew(BlockState state)
    {
        int i = 0;
        for(BooleanProperty booleanproperty : PROPERTY_BY_DIRECTION.values())
        {
            if (state.getValue(booleanproperty))
            {
                ++i;
            }
        }
        return i;
    }

    public boolean canSupportAtFaceNew(BlockGetter getter, BlockPos pos, Direction direction)
    {
        if (direction == Direction.DOWN)
        {
            return false;
        }
        else
        {
            BlockPos blockpos = pos.relative(direction);
            if (isAcceptableNeighbour(getter, blockpos, direction))
            {
                return true;
            }
            else if (direction.getAxis() == Direction.Axis.Y)
            {
                return false;
            }
            else
            {
                BooleanProperty booleanproperty = PROPERTY_BY_DIRECTION.get(direction);
                BlockState blockstate = getter.getBlockState(pos.above());
                return blockstate.is(this) && blockstate.getValue(booleanproperty);
            }
        }
    }

    public static boolean isAcceptableNeighbour(BlockGetter getter, BlockPos pos, Direction direction)
    {
        BlockState blockstate = getter.getBlockState(pos);
        BlockState attachedState = getter.getBlockState(pos.relative(direction.getOpposite()));

        return Block.isFaceFull(blockstate.getCollisionShape(getter, pos), direction.getOpposite()) || attachedState.getBlock() instanceof LeavesBlock || Helpers.isBlock(attachedState, TFCTags.Blocks.CREEPING_PLANTABLE_ON);
    }

    public BlockState getUpdatedStateNew(BlockState state, BlockGetter getter, BlockPos pos)
    {
        BlockPos blockpos = pos.above();
        if (state.getValue(UP))
        {
            state = state.setValue(UP, Boolean.valueOf(isAcceptableNeighbour(getter, blockpos, Direction.DOWN)));
        }
        BlockState blockstate = null;
        for(Direction direction : Direction.Plane.HORIZONTAL)
        {
            BooleanProperty booleanproperty = getPropertyForFace(direction);
            if (state.getValue(booleanproperty))
            {
                boolean flag = this.canSupportAtFaceNew(getter, pos, direction);
                if (!flag)
                {
                    if (blockstate == null)
                    {
                        blockstate = getter.getBlockState(blockpos);
                    }
                    flag = blockstate.is(this) && blockstate.getValue(booleanproperty);
                }
                state = state.setValue(booleanproperty, Boolean.valueOf(flag));
            }
        }
        return state;
    }
}
