package tfcflorae.common.blocks.plant;

import java.util.Map;
import java.util.Random;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableMap;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.EpiphytePlantBlock;
import net.dries007.tfc.common.blocks.plant.PlantBlock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.Season;
import net.dries007.tfc.util.registry.RegistryPlant;

public abstract class SporeBlossomPlantBlock extends PlantBlock implements IFluidLoggable
{
    public static final int ADD_PARTICLE_ATTEMPTS = 14;
    public static final int PARTICLE_XZ_RADIUS = 10;
    public static final int PARTICLE_Y_MAX = 10;

    public static final DirectionProperty FACING_SPORE = BlockStateProperties.FACING;

    public static final VoxelShape UP_SHAPE = box(00.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    public static final VoxelShape DOWN_SHAPE = box(0.0, 4.0, 0.0, 16.0, 16.0, 16.0);
    public static final VoxelShape NORTH_SHAPE = box(0.0, 0.0, 4.0, 16.0, 16.0, 16.0);
    public static final VoxelShape SOUTH_SHAPE = box(0.0, 0.0, 0.0, 16.0, 16.0, 12.0);
    public static final VoxelShape WEST_SHAPE = box(4.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    public static final VoxelShape EAST_SHAPE = box(0.0, 0.0, 0.0, 12.0, 16.0, 16.0);

    public static final Map<Direction, VoxelShape> SHAPES = ImmutableMap.of(Direction.UP, UP_SHAPE, Direction.DOWN, DOWN_SHAPE, Direction.NORTH, NORTH_SHAPE, Direction.SOUTH, SOUTH_SHAPE, Direction.WEST, WEST_SHAPE, Direction.EAST, EAST_SHAPE);

    public static SporeBlossomPlantBlock create(RegistryPlant plant, ExtendedProperties properties, FluidProperty fluid)
    {
        return new SporeBlossomPlantBlock(properties, fluid)
        {
            @Override
            public RegistryPlant getPlant()
            {
                return plant;
            }
            @Override
            public FluidProperty getFluidProperty()
            {
                return fluid;
            }
        };
    }

    public SporeBlossomPlantBlock(ExtendedProperties properties, FluidProperty fluid)
    {
        super(properties);

        registerDefaultState(getStateDefinition().any().setValue(FACING_SPORE, Direction.UP).setValue(getFluidProperty(), getFluidProperty().keyFor(Fluids.EMPTY)));
    }

    /**
     * Called periodically clientside on blocks near the player to show effects (like furnace fire particles).
     */
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random)
    {
        //if (getPlant().stageFor(Calendars.SERVER.getCalendarMonthOfYear()) >= 1 || Calendars.CLIENT.getCalendarMonthOfYear().getSeason() == Season.SPRING)
        //{
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            double d0 = (double)i + random.nextDouble();
            double d1 = (double)j + 0.7D;
            double d2 = (double)k + random.nextDouble();
            level.addParticle(ParticleTypes.FALLING_SPORE_BLOSSOM, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            BlockPos.MutableBlockPos blockpos$mutableBlockpos = new BlockPos.MutableBlockPos();

            for(int l = 0; l < 14; ++l)
            {
                blockpos$mutableBlockpos.set(i + Mth.nextInt(random, -10, 10), j - random.nextInt(10), k + Mth.nextInt(random, -10, 10));
                BlockState blockstate = level.getBlockState(blockpos$mutableBlockpos);
                if (!blockstate.isCollisionShapeFullBlock(level, blockpos$mutableBlockpos))
                {
                    level.addParticle(ParticleTypes.SPORE_BLOSSOM_AIR, (double)blockpos$mutableBlockpos.getX() + random.nextDouble(), (double)blockpos$mutableBlockpos.getY() + random.nextDouble(), (double)blockpos$mutableBlockpos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
                }
            }
        //}
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
    {
        if (!canSurvive(state, level, pos))
        {
            level.destroyBlock(pos, false);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        FluidHelpers.tickFluid(level, currentPos, state);
        if (direction.getOpposite() == state.getValue(FACING_SPORE) && !Helpers.isBlock(facingState, TFCTags.Blocks.CREEPING_PLANTABLE_ON))
        {
            return Blocks.AIR.defaultBlockState();
        }
        return state;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockState attachedState = level.getBlockState(pos.relative(state.getValue(FACING_SPORE).getOpposite()));
        return Helpers.isBlock(attachedState, TFCTags.Blocks.CREEPING_PLANTABLE_ON);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPES.get(state.getValue(FACING_SPORE));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Direction direction = context.getClickedFace();
        BlockPos pos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(pos);
        BlockState state = updateStateWithCurrentMonth(defaultBlockState()).setValue(FACING_SPORE, direction);
        if (getFluidProperty().canContain(fluidState.getType()))
        {
            state = updateStateWithCurrentMonth(state.setValue(getFluidProperty(), getFluidProperty().keyFor(fluidState.getType()))).setValue(FACING_SPORE, direction);
        }
        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        if (getPlant().getStageProperty() != null)
        {
            builder.add(getPlant().getStageProperty());
        }
        builder.add(AGE, FACING_SPORE, getFluidProperty());
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return IFluidLoggable.super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation)
    {
        return state.setValue(FACING_SPORE, rotation.rotate(state.getValue(FACING_SPORE)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return state.rotate(mirror.getRotation(state.getValue(FACING_SPORE)));
    }

    @Override
    public boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return true;
    }
}
