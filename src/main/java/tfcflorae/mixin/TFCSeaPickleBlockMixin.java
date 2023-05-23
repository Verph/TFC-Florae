package tfcflorae.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.coral.TFCSeaPickleBlock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.util.Helpers;

@Mixin(TFCSeaPickleBlock.class)
public abstract class TFCSeaPickleBlockMixin extends Block implements IFluidLoggable
{
    @Unique @Final
    private static final FluidProperty FLUID_ALL = TFCBlockStateProperties.ALL_WATER;

    public TFCSeaPickleBlockMixin(Properties properties)
    {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(TFCSeaPickleBlock.PICKLES, 1));
    }

    @Overwrite(remap = false)
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        if (Helpers.isBlock(blockstate, this))
        {
            return blockstate.setValue(TFCSeaPickleBlock.PICKLES, Math.min(4, blockstate.getValue(TFCSeaPickleBlock.PICKLES) + 1));
        }
        else
        {
            BlockState state = defaultBlockState();
            FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());

            if (getFluidProperty().canContain(fluidState.getType()))
            {
                return state.setValue(getFluidProperty(), getFluidProperty().keyFor(fluidState.getType()));
            }
            return state.setValue(getFluidProperty(), getFluidProperty().keyFor(Fluids.EMPTY));
        }
    }

    @Overwrite(remap = false)
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(TFCSeaPickleBlock.PICKLES, getFluidProperty());
    }

    @Overwrite(remap = false)
    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        FluidHelpers.tickFluid(level, currentPos, state);
        return !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    /*@Overwrite(remap = false)
    @Override
    public FluidState getFluidState(BlockState state)
    {
        return IFluidLoggable.super.getFluidState(state);
    }*/

    @Overwrite(remap = false)
    @Override
    public FluidProperty getFluidProperty()
    {
        return FLUID_ALL;
    }
}
