package tfcflorae.common.blocks.soil;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blocks.soil.ConnectedGrassBlock;
import net.dries007.tfc.common.blocks.soil.IDirtBlock;
import org.jetbrains.annotations.Nullable;

public class TFCFConnectedGrassBlock extends ConnectedGrassBlock
{
    //@Nullable private Supplier<? extends Block> spreadBlock;

    public TFCFConnectedGrassBlock(Properties properties, Supplier<? extends Block> dirt, @Nullable Supplier<? extends Block> path, @Nullable Supplier<? extends Block> farmland)
    {
        super(properties, dirt, path, farmland);
    }

    /*public void setSpreadBlock(Supplier<? extends Block> spreadBlock)
    {
        if (this.spreadBlock != null)
            this.spreadBlock = spreadBlock;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (!canBeGrass(state, level, pos))
        {
            if (level.isAreaLoaded(pos, 3))
            {
                // Turn to not-grass
                level.setBlockAndUpdate(pos, getDirt());
            }
        }
        else
        {
            if (level.getMaxLocalRawBrightness(pos.above()) >= 9)
            {
                for (int i = 0; i < 4; ++i)
                {
                    BlockPos posAt = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    BlockState stateAt = level.getBlockState(posAt);
                    if (stateAt.getBlock() instanceof IDirtBlock dirt)
                    {
                        // Spread grass to others
                        BlockState grassState = spreadBlock.get().defaultBlockState();
                        if (grassState == null)
                            grassState = dirt.getGrass();
                            
                        if (canPropagate(grassState, level, posAt))
                        {
                            level.setBlockAndUpdate(posAt, updateStateFromNeighbors(level, posAt, grassState));
                        }
                    }
                }
            }
        }
    }*/
}
