package tfcflorae.common.blocks.rock;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;

import net.dries007.tfc.common.blocks.rock.IMossGrowingBlock;
import net.dries007.tfc.common.blocks.rock.MossSpreadingBlock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.config.TFCConfig;

public class MossSpreadingRotatedPillarBlock extends MossSpreadingBlock
{
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public MossSpreadingRotatedPillarBlock(BlockBehaviour.Properties properties)
    {
        super(properties.randomTicks());

        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation)
    {
        return rotatePillar(state, rotation);
    }

    public static BlockState rotatePillar(BlockState state, Rotation rotation)
    {
        switch(rotation) {
        case COUNTERCLOCKWISE_90:
        case CLOCKWISE_90:
            switch((Direction.Axis)state.getValue(AXIS)) {
            case X:
                return state.setValue(AXIS, Direction.Axis.Z);
            case Z:
                return state.setValue(AXIS, Direction.Axis.X);
            default:
                return state;
            }
        default:
            return state;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(AXIS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
    }

    @SuppressWarnings("deprecation")
    public static void spreadMoss(Level world, BlockPos pos, Random random)
    {
        if (world.isAreaLoaded(pos, 5) && TFCConfig.SERVER.enableMossyRockSpreading.get() && random.nextInt(TFCConfig.SERVER.mossyRockSpreadRate.get()) == 0)
        {
            final BlockPos targetPos = pos.offset(random.nextInt(4) - random.nextInt(4), random.nextInt(4) - random.nextInt(4), random.nextInt(4) - random.nextInt(4));
            final BlockState targetState = world.getBlockState(targetPos);
            if (targetState.getBlock() instanceof IMossGrowingBlock block)
            {
                block.convertToMossy(world, targetPos, targetState, true);
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random)
    {
        MossSpreadingRotatedPillarBlock.spreadMoss(worldIn, pos, random);
    }
}
