package tfcflorae.common.blocks.wood;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import tfcflorae.common.blocks.TFCFBlocks;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.wood.TFCLeavesBlock;
import net.dries007.tfc.common.blocks.wood.Wood;

public abstract class TFCFMangroveLeavesBlock extends TFCLeavesBlock implements BonemealableBlock
{
    public final TFCFWood wood;

    public TFCFMangroveLeavesBlock(TFCFWood wood, ExtendedProperties properties, int maxDecayDistance)
    {
        super(properties, maxDecayDistance);

        this.wood = wood;
    }

    public TFCFWood getWood()
    {
        return wood;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter block, BlockPos pos, BlockState state, boolean flag)
    {
        return block.getBlockState(pos.below()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, Random random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state)
    {
        level.setBlock(pos.below(), TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.SAPLING).get().defaultBlockState(), 2);
    }
}
