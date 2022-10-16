package tfcflorae.common.blocks.plant;

import java.util.Arrays;
import java.util.Map;
import java.util.function.ToIntFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.dries007.tfc.common.blocks.DirectionPropertyBlock;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.*;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.util.registry.RegistryPlant;

public abstract class CreepingWaterPlantBlock extends CreepingPlantBlock implements DirectionPropertyBlock, IFluidLoggable
{
    protected static final Direction[] DIRECTIONS = Direction.values();

    public static CreepingWaterPlantBlock create(RegistryPlant plant, ExtendedProperties properties, FluidProperty fluid)
    {
        return new CreepingWaterPlantBlock(properties)
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

    protected final Map<BlockState, VoxelShape> shapeCache;

    protected CreepingWaterPlantBlock(ExtendedProperties properties)
    {
        super(properties);

        registerDefaultState(DirectionPropertyBlock.setAllDirections(getStateDefinition().any(), false).setValue(getFluidProperty(), getFluidProperty().keyFor(Fluids.EMPTY)));
        shapeCache = DirectionPropertyBlock.makeShapeCache(getStateDefinition(), SHAPES::get);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        super.getStateForPlacement(context);
        BlockPos pos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(pos);
        BlockState state = updateStateWithCurrentMonth(defaultBlockState());
        if (getFluidProperty().canContain(fluidState.getType()))
        {
            state = state.setValue(getFluidProperty(), getFluidProperty().keyFor(fluidState.getType()));
        }
        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(getFluidProperty()));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        FluidHelpers.tickFluid(level, currentPos, state);
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state)
    {
        return IFluidLoggable.super.getFluidState(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos)
    {
        return state.getFluidState().isEmpty();
    }

    public static ToIntFunction<BlockState> emission(int direction)
    {
        return (state) -> {
            return hasAnyFace(state) ? direction : 0;
        };
    }

    public static boolean hasAnyFace(BlockState state)
    {
        return Arrays.stream(DIRECTIONS).anyMatch((direction) -> {
            return hasFace(state, direction);
        });
    }

    public static boolean hasFace(BlockState state, Direction direction)
    {
        BooleanProperty booleanProperty = getFaceProperty(direction);
        return state.hasProperty(booleanProperty) && state.getValue(booleanProperty);
    }

    public static BooleanProperty getFaceProperty(Direction direction)
    {
        return PROPERTY_BY_DIRECTION.get(direction);
    }
}
