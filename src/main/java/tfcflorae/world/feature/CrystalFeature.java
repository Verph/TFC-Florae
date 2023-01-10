package tfcflorae.world.feature;

import com.mojang.serialization.Codec;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraftforge.common.Tags;
import net.dries007.tfc.util.Helpers;

public class CrystalFeature extends Feature<BlockStateConfiguration>
{
    public CrystalFeature(Codec<BlockStateConfiguration> codec)
    {
        super(codec);
    }

    /**
        * Places the given feature at the given location.
        * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
        * that they can safely generate into.
        * @param pContext A context object with a reference to the level and the position the feature is being placed at
        */
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context)
    {
        final WorldGenLevel worldgenlevel = context.level();
        final BlockPos blockpos = context.origin();
        final Random random = context.random();
        BlockState state = (context.config()).state;

        if (!worldgenlevel.isEmptyBlock(blockpos))
        {
            return false;
        }
        else
        {
            BlockState blockstate = worldgenlevel.getBlockState(blockpos.above());
            if (!Helpers.isBlock(blockstate, Tags.Blocks.STONE) || blockstate != state)
            {
                return false;
            }
            else
            {
                worldgenlevel.setBlock(blockpos, state, 2);
                for(int i = 0; i < 1500; ++i)
                {
                BlockPos blockpos1 = blockpos.offset(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));
                if (worldgenlevel.getBlockState(blockpos1).isAir())
                {
                    int j = 0;
                    for(Direction direction : Direction.values())
                    {
                        if (worldgenlevel.getBlockState(blockpos1.relative(direction)) == state)
                        {
                            ++j;
                        }
                        if (j > 1)
                        {
                            break;
                        }
                    }
                    if (j == 1)
                    {
                        worldgenlevel.setBlock(blockpos1, state, 2);
                    }
                }
                }
                return true;
            }
        }
    }
}