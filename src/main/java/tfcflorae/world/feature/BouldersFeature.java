package tfcflorae.world.feature;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import com.mojang.serialization.Codec;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.noise.Metaballs3D;
import net.dries007.tfc.world.settings.RockSettings;

public class BouldersFeature extends Feature<BoulderConfig>
{
    public BouldersFeature(Codec<BoulderConfig> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BoulderConfig> context)
    {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final Random random = context.random();
        final BoulderConfig config = context.config();

        final ChunkDataProvider provider = ChunkDataProvider.get(context.chunkGenerator());
        final ChunkData data = provider.get(context.level(), pos);
        final RockSettings rock = data.getRockData().getRock(pos);
        final List<BlockState> states = config.getStates(rock.raw());
        final int seaLevel = level.getLevel().getChunkSource().getGenerator().getSeaLevel();

        if (states != null && pos.getY() < seaLevel - 8)
        {
            place(level, pos, states, random, context);
            return true;
        }
        return false;
    }

    private void place(WorldGenLevel level, BlockPos pos, List<BlockState> states, Random random, FeaturePlaceContext<BoulderConfig> context)
    {
        final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        final BoulderConfig config = context.config();
        final int minSize = config.sizeMin();
        final int randomSize = config.sizeRandom();

        final int sizeX = minSize + random.nextInt(randomSize);
        final int sizeY = minSize + random.nextInt(randomSize);
        final int sizeZ = minSize + random.nextInt(randomSize);

        final Metaballs3D noise = new Metaballs3D(Helpers.fork(random), 6, 8, -0.12f * sizeX, 0.3f * sizeY, 0.3f * sizeZ);

        Supplier<BlockState> state;
        if (states.size() == 1)
        {
            final BlockState onlyState = states.get(0);
            state = () -> onlyState;
        }
        else
        {
            state = () -> states.get(random.nextInt(states.size()));
        }

        for (int x = -sizeX; x <= sizeX; x++)
        {
            for (int y = -sizeY; y <= sizeY; y++)
            {
                for (int z = -sizeZ; z <= sizeZ; z++)
                {
                    if (noise.inside(x, y, z))
                    {
                        mutablePos.setWithOffset(pos, x, y, z);
                        setBlock(level, mutablePos, state.get());
                    }
                }
            }
        }
    }
}