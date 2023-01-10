package tfcflorae.common.blocks.wood;

import java.util.Random;

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
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.EntityBlockExtension;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.util.Helpers;

import tfcflorae.common.blocks.TFCFBlocks;

public class TFCFJoshuaTrunkBlock extends PipeBlock implements IFluidLoggable, EntityBlockExtension
{
    public static final FluidProperty FLUID = TFCBlockStateProperties.FRESH_WATER;
    public static final BooleanProperty NATURAL = TFCBlockStateProperties.NATURAL;
    public final TFCFWood wood;
    private final ExtendedProperties properties;

    public static TFCFJoshuaTrunkBlock create(TFCFWood wood, BlockBehaviour.Properties builder, ExtendedProperties properties, FluidProperty fluid)
    {
        return new TFCFJoshuaTrunkBlock(wood, properties.properties(), properties)
        {
            @Override
            public FluidProperty getFluidProperty()
            {
                return fluid;
            }
        };
    }

    public TFCFJoshuaTrunkBlock(TFCFWood wood, BlockBehaviour.Properties builder, ExtendedProperties properties)
    {
        super(0.3125F, properties.properties());
        this.properties = properties;
        this.wood = wood;
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false).setValue(getFluidProperty(), getFluidProperty().keyFor(Fluids.EMPTY)).setValue(NATURAL, false));
    }

    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos)
    {
        // Modified from the super() method, including the Forge patch, to add the 2x hardness in natural state modifier.
        final float baseSpeed = (state.getValue(NATURAL) ? 2 : 1) * state.getDestroySpeed(level, pos);
        if (baseSpeed == -1.0F)
        {
            return 0.0F;
        }
        else
        {
            final int toolModifier = ForgeHooks.isCorrectToolForDrops(state, player) ? 30 : 100;
            return player.getDigSpeed(state, pos) / baseSpeed / (float) toolModifier;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.getStateForPlacement(context.getLevel(), context.getClickedPos());
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        super.playerWillDestroy(level, pos, state, player);
        FluidHelpers.tickFluid(level, pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(getFluidProperty(), NORTH, EAST, SOUTH, WEST, UP, DOWN, NATURAL);
    }

    public BlockState getStateForPlacement(BlockGetter world, BlockPos pos)
    {
        BlockState downBlockState = world.getBlockState(pos.below());
        BlockState upBlockState = world.getBlockState(pos.above());
        BlockState northBlockState = world.getBlockState(pos.north());
        BlockState eastBlockState = world.getBlockState(pos.east());
        BlockState southBlockState = world.getBlockState(pos.south());
        BlockState westBlockState = world.getBlockState(pos.west());
        return defaultBlockState()
            .setValue(DOWN, (getJoshuaTrunkBlock(downBlockState) || canConnectTo(downBlockState)) && !getJoshuaLeavesBlock(northBlockState))
            .setValue(UP, getJoshuaTreeBlocks(upBlockState))
            .setValue(NORTH, getJoshuaTrunkBlock(northBlockState) && !getJoshuaLeavesBlock(northBlockState))
            .setValue(EAST, getJoshuaTrunkBlock(eastBlockState) && !getJoshuaLeavesBlock(eastBlockState))
            .setValue(SOUTH, getJoshuaTrunkBlock(southBlockState) && !getJoshuaLeavesBlock(southBlockState))
            .setValue(WEST, getJoshuaTrunkBlock(westBlockState) && !getJoshuaLeavesBlock(westBlockState));
    }

    public boolean getJoshuaTreeBlocks(BlockState state)
    {
        return (Helpers.isBlock(state, TFCFBlocks.JOSHUA_TRUNK.get(wood).get().defaultBlockState().getBlock()) || Helpers.isBlock(state, TFCFBlocks.JOSHUA_LEAVES.get(wood).get().defaultBlockState().getBlock()));
    }

    public boolean getJoshuaTrunkBlock(BlockState state)
    {
        return Helpers.isBlock(state, TFCFBlocks.JOSHUA_TRUNK.get(wood).get());
    }

    public boolean getJoshuaLeavesBlock(BlockState state)
    {
        return Helpers.isBlock(state, TFCFBlocks.JOSHUA_LEAVES.get(wood).get());
    }

    public static boolean canConnectTo(BlockState state)
    {
        return (Helpers.isBlock(state, TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(state, TFCTags.Blocks.TREE_GROWS_ON) || Helpers.isBlock(state, BlockTags.SAND) || Helpers.isBlock(state, Tags.Blocks.GRAVEL) || state.getMaterial().isSolid());
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        if (!state.canSurvive(level, currentPos))
        {
            level.scheduleTick(currentPos, this, 1);
            FluidHelpers.tickFluid(level, currentPos, state);
            //return state;
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        }
        else
        {
            FluidHelpers.tickFluid(level, currentPos, state);
            boolean flag = getJoshuaTreeBlocks(facingState) || (facing == Direction.DOWN && canConnectTo(facingState));
            return state.setValue(PROPERTY_BY_DIRECTION.get(facing), flag);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state)
    {
        return IFluidLoggable.super.getFluidState(state);
    }

    @Override
    public FluidProperty getFluidProperty()
    {
        return FLUID;
    }

    /**
     * {@link net.minecraft.world.level.block.ChorusPlantBlock#canSurvive}
     */
    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockState belowState = level.getBlockState(pos.below());
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            BlockPos relativePos = pos.relative(direction);
            if (Helpers.isBlock(level.getBlockState(relativePos), TFCFBlocks.JOSHUA_TRUNK.get(wood).get()))
            {
                //Block below = level.getBlockState(relativePos.below()).getBlock();
                BlockState belowState2 = level.getBlockState(relativePos.below());
                if (getJoshuaTrunkBlock(belowState2) || canConnectTo(belowState2))
                {
                    return true;
                }
            }
        }
        //Block blockIn = belowState.getBlock();
        return getJoshuaTrunkBlock(belowState) || canConnectTo(belowState);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random rand)
    {
        if (!state.canSurvive(level, pos))
        {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType)
    {
        return false;
    }
}
