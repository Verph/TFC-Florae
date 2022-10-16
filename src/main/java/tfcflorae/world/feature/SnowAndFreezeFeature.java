package tfcflorae.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SnowAndFreezeFeature extends Feature<NoneFeatureConfiguration>
{
    public SnowAndFreezeFeature(Codec<NoneFeatureConfiguration> codec)
    {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        BlockPos.MutableBlockPos pos$mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos pos$mutableBlockPos1 = new BlockPos.MutableBlockPos();

        for(int i = 0; i < 16; ++i)
        {
            for(int j = 0; j < 16; ++j)
            {
                int k = pos.getX() + i;
                int l = pos.getZ() + j;
                int i1 = level.getHeight(Heightmap.Types.MOTION_BLOCKING, k, l);
                pos$mutableBlockPos.set(k, i1, l);
                pos$mutableBlockPos1.set(pos$mutableBlockPos).move(Direction.DOWN, 1);
                Biome biome = level.getBiome(pos$mutableBlockPos).value();
                if (biome.shouldFreeze(level, pos$mutableBlockPos1, false))
                {
                    level.setBlock(pos$mutableBlockPos1, Blocks.ICE.defaultBlockState(), 2);
                }

                if (biome.shouldSnow(level, pos$mutableBlockPos))
                {
                    level.setBlock(pos$mutableBlockPos, Blocks.SNOW.defaultBlockState(), 2);
                    BlockState state = level.getBlockState(pos$mutableBlockPos1);
                    if (state.hasProperty(SnowyDirtBlock.SNOWY))
                    {
                        level.setBlock(pos$mutableBlockPos1, state.setValue(SnowyDirtBlock.SNOWY, Boolean.valueOf(true)), 2);
                    }
                }
            }
        }
        return true;
    }
}