package tfcflorae.mixin.world.biome;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import net.dries007.tfc.world.biome.RiverSource;
import net.dries007.tfc.world.biome.TFCBiomeSource;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.LerpFloatLayer;
import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.river.*;

@Mixin(TFCBiomeSource.class)
public class TFCBiomeSourceMixin implements RiverSource
{
    @Unique @Final private ChunkData chunkData;
    @Nullable @Unique @Final private LerpFloatLayer rainfallLayer;
    @Shadow private final long seed;
    @Shadow private final Watershed.Context watersheds;

    public TFCBiomeSourceMixin(long seed, LerpFloatLayer rainfallLayer)
    {
        this.seed = seed;
        this.rainfallLayer = rainfallLayer;
        this.watersheds = new Watershed.Context(TFCLayers.createEarlyPlateLayers(seed), seed, 0.5f, 0.8f, 14, 0.2f);
    }

    @Overwrite(remap = false)
    @Override
    public Flow getRiverFlow(int quartX, int quartZ)
    {
        final float scale = 1f / (1 << 7);
        final float x0 = quartX * scale, z0 = quartZ * scale;
        for (MidpointFractal fractal : watersheds.getFractalsByPartition(quartX, quartZ))
        {
            float rainfall = 1f;
            if (rainfallLayer != null)
            {
                rainfall = rainfallLayer.getValue(((int) z0 & 15) / 16f, 1 - (((int) x0 & 15) / 16f)) / 65f;
            }
            else if (chunkData != null)
            {
                rainfall = chunkData.getRainfall((int) x0, (int) z0) / 65f;
            }

            // maybeIntersect will skip the more expensive calculation if it fails
            if (fractal.maybeIntersect(x0, z0, Watershed.RIVER_WIDTH))
            {
                final Flow flow = fractal.intersectWithFlow(x0, z0, Watershed.RIVER_WIDTH * rainfall);
                if (flow != Flow.NONE)
                {
                    return flow;
                }
            }
        }
        return Flow.NONE;
    }
}
