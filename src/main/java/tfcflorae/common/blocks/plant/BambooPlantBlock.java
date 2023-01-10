package tfcflorae.common.blocks.plant;

import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolActions;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.PlantBlock;
import net.dries007.tfc.common.blocks.plant.TFCBushBlock;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.registry.RegistryPlant;

public abstract class BambooPlantBlock extends TFCBushBlock
{
    protected static final float SMALL_LEAVES_AABB_OFFSET = 3.0F;
    protected static final float LARGE_LEAVES_AABB_OFFSET = 5.0F;
    protected static final float COLLISION_AABB_OFFSET = 1.5F;
    protected static final VoxelShape SMALL_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    protected static final VoxelShape LARGE_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    protected static final VoxelShape COLLISION_SHAPE = Block.box(6.5D, 0.0D, 6.5D, 9.5D, 16.0D, 9.5D);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    public static final EnumProperty<BambooLeaves> LEAVES = BlockStateProperties.BAMBOO_LEAVES;
    public static final IntegerProperty GROWTH_STAGE = BlockStateProperties.STAGE;
    public static final int MAX_HEIGHT = 16;
    public static final int GROWTH_STAGE_GROWING = 0;
    public static final int GROWTH_STAGE_DONE_GROWING = 1;
    public static final int AGE_THIN_BAMBOO = 0;
    public static final int AGE_THICK_BAMBOO = 1;

    private final Supplier<? extends Block> bambooStem;
    private final Supplier<? extends Block> bambooSapling;

    public static BambooPlantBlock create(RegistryPlant plant, ExtendedProperties properties, Supplier<? extends Block> bambooStem, Supplier<? extends Block> bambooSapling)
    {
        return new BambooPlantBlock(properties, bambooStem, bambooSapling)
        {
            @Override
            public RegistryPlant getPlant()
            {
                return plant;
            }
        };
    }

    protected BambooPlantBlock(ExtendedProperties properties, Supplier<? extends Block> bambooStem, Supplier<? extends Block> bambooSapling)
    {
        super(properties);

        this.bambooStem = bambooStem;
        this.bambooSapling = bambooSapling;

        BlockState stateDefinition = getStateDefinition().any();
        IntegerProperty stageProperty = getPlant().getStageProperty();
        if (stageProperty != null)
        {
            stateDefinition = stateDefinition.setValue(stageProperty, 0);
        }
        this.registerDefaultState(stateDefinition.setValue(AGE, Integer.valueOf(0)).setValue(LEAVES, BambooLeaves.NONE).setValue(GROWTH_STAGE, Integer.valueOf(0)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        //super.createBlockStateDefinition(builder.add(AGE, LEAVES, GROWTH_STAGE));
        if (getPlant().getStageProperty() != null)
        {
            builder.add(getPlant().getStageProperty());
        }
        builder.add(AGE, LEAVES, GROWTH_STAGE);
    }

    @Override
    public BlockBehaviour.OffsetType getOffsetType()
    {
        return BlockBehaviour.OffsetType.XZ;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter pReader, BlockPos pos)
    {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        VoxelShape voxelshape = state.getValue(LEAVES) == BambooLeaves.LARGE ? LARGE_SHAPE : SMALL_SHAPE;
        Vec3 vec3 = state.getOffset(level, pos);
        return voxelshape.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type)
    {
        return false;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        Vec3 vec3 = state.getOffset(level, pos);
        return COLLISION_SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos)
    {
        return false;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        super.getStateForPlacement(context);
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        if (!fluidstate.isEmpty())
        {
            return null;
        }
        else
        {
            BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().below());
            if (blockstate.is(BlockTags.BAMBOO_PLANTABLE_ON))
            {
                if (blockstate.is(bambooSapling.get()))
                {
                    return this.defaultBlockState().setValue(AGE, Integer.valueOf(0));
                }
                else if (blockstate.is(bambooStem.get()))
                {
                    int i = blockstate.getValue(AGE) > 0 ? 1 : 0;
                    return this.defaultBlockState().setValue(AGE, Integer.valueOf(i));
                }
                else
                {
                    BlockState blockstate1 = context.getLevel().getBlockState(context.getClickedPos().above());
                    return blockstate1.is(bambooStem.get()) ? this.defaultBlockState().setValue(AGE, blockstate1.getValue(AGE)) : bambooSapling.get().defaultBlockState();
                }
            }
            else
            {
                return null;
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (!state.canSurvive(level, pos))
        {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(GROWTH_STAGE) == 0;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (random.nextDouble() < TFCConfig.SERVER.plantGrowthChance.get() && state.getValue(GROWTH_STAGE) == 0)
        {
            if (level.isEmptyBlock(pos.above()) && level.getRawBrightness(pos.above(), 0) >= 9)
            {
                int i = this.getHeightBelowUpToMax(level, pos) + 1;
                if (i < 16 && ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(3) == 0))
                {
                    this.growBamboo(state, level, pos, random, i);
                    ForgeHooks.onCropsGrowPost(level, pos, state);
                }
            }
        }
        level.setBlockAndUpdate(pos, updateStateWithCurrentMonth(state));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return super.canSurvive(state, level, pos) || level.getBlockState(pos.below()).is(BlockTags.BAMBOO_PLANTABLE_ON) || level.getBlockState(pos.below()).is(bambooStem.get());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        if (!state.canSurvive(level, currentPos))
        {
            level.scheduleTick(currentPos, this, 1);
        }
        if (facing == Direction.UP && facingState.is(bambooStem.get()) && facingState.getValue(AGE) > state.getValue(AGE))
        {
            level.setBlock(currentPos, state.cycle(AGE), 2);
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos)
    {
        return player.getMainHandItem().canPerformAction(ToolActions.SWORD_DIG) ? 1.0F : super.getDestroyProgress(state, player, level, pos);
    }

    public void growBamboo(BlockState state, Level level, BlockPos pos, Random random, int pMaxTotalSize)
    {
        BlockState blockstate = level.getBlockState(pos.below());
        BlockPos blockpos = pos.below(2);
        BlockState blockstate1 = level.getBlockState(blockpos);
        BambooLeaves bambooleaves = BambooLeaves.NONE;
        if (pMaxTotalSize >= 1)
        {
            if (blockstate.is(bambooStem.get()) && blockstate.getValue(LEAVES) != BambooLeaves.NONE)
            {
                if (blockstate.is(bambooStem.get()) && blockstate.getValue(LEAVES) != BambooLeaves.NONE)
                {
                    bambooleaves = BambooLeaves.LARGE;
                    if (blockstate1.is(bambooStem.get()))
                    {
                        level.setBlock(pos.below(), blockstate.setValue(LEAVES, BambooLeaves.SMALL), 3);
                        level.setBlock(blockpos, blockstate1.setValue(LEAVES, BambooLeaves.NONE), 3);
                    }
                }
            }
            else
            {
                bambooleaves = BambooLeaves.SMALL;
            }
        }
        int i = state.getValue(AGE) != 1 && !blockstate1.is(bambooStem.get()) ? 0 : 1;
        int j = (pMaxTotalSize < 11 || !(random.nextFloat() < 0.25F)) && pMaxTotalSize != 15 ? 0 : 1;
        level.setBlock(pos.above(), this.defaultBlockState().setValue(AGE, Integer.valueOf(i)).setValue(LEAVES, bambooleaves).setValue(GROWTH_STAGE, Integer.valueOf(j)), 3);
    }

    public int getHeightAboveUpToMax(BlockGetter level, BlockPos pos)
    {
        int i;
        for(i = 0; i < 16 && level.getBlockState(pos.above(i + 1)).is(bambooStem.get()); ++i) {}
        return i;
    }

    public int getHeightBelowUpToMax(BlockGetter level, BlockPos pos)
    {
        int i;
        for(i = 0; i < 16 && level.getBlockState(pos.below(i + 1)).is(bambooStem.get()); ++i) {}
        return i;
    }

    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos)
    {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() != this) return defaultBlockState();
        return state;
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
    public float getSpeedFactor()
    {
        final float modifier = TFCConfig.SERVER.plantsMovementModifier.get().floatValue(); // 0.0 = full speed factor, 1.0 = no modifier
        return Helpers.lerp(modifier, speedFactor, 1.0f);
    }

    protected BlockState updateStateWithCurrentMonth(BlockState state)
    {
        return getPlant().getStageProperty() != null ? state.setValue(getPlant().getStageProperty(), getPlant().stageFor(Calendars.SERVER.getCalendarMonthOfYear())) : state;
    }
}
