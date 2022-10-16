package tfcflorae.common.blocks.rock;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.dries007.tfc.common.blocks.rock.MossGrowingBlock;
import net.dries007.tfc.common.fluids.FluidHelpers;

public class MossGrowingRotatedPillarBlock extends MossGrowingBlock implements IMossGrowingBlock
{
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    private final Supplier<? extends Block> mossy;

    public MossGrowingRotatedPillarBlock(BlockBehaviour.Properties properties, Supplier<? extends Block> mossy)
    {
        super(properties, mossy);
        this.mossy = mossy;

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

    @Override
    public void convertToMossy(Level worldIn, BlockPos pos, BlockState state, boolean needsWater)
    {
        if (!needsWater || FluidHelpers.isSame(worldIn.getFluidState(pos.above()), Fluids.WATER))
        {
            worldIn.setBlock(pos, mossy.get().defaultBlockState(), 3);
        }
    }
}
