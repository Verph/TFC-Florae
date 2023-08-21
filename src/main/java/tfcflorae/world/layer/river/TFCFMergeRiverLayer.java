package tfcflorae.world.layer.river;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.Area;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.TransformLayer;
import net.dries007.tfc.world.river.MidpointFractal;

import tfcflorae.interfaces.ChunkDataInterface;
import tfcflorae.interfaces.TFCLayersMixinInterface;
import tfcflorae.util.TFCFHelpers;

import net.dries007.tfc.world.biome.BiomeSourceExtension.Settings;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.chunkdata.ChunkGeneratorExtension;
import net.dries007.tfc.world.chunkdata.TFCChunkDataGenerator;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public class TFCFMergeRiverLayer implements TransformLayer
{
    public final long seed;
    public final Watershed.Context watersheds;
    public static TFCLayers staticBiomes = new TFCLayers();

    static final int RIVER_EDGE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRiverEdge();

    public TFCFMergeRiverLayer(Watershed.Context watersheds, long seed)
    {
        this.watersheds = watersheds;
        this.seed = seed;
    }

    @Override
    public int apply(AreaContext context, Area area, int x, int z)
    {
        /*float rainfall = 1F;
        float temperature = 1F;
        float forestDensity = 0F;

        if (TFCFHelpers.getLevel() != null)
        {
            final float scale = 1f / (1 << 7);
            final float x0 = x * scale, z0 = z * scale;
            final ServerLevel serverLevel = TFCFHelpers.getLevel();

            ChunkData data = ChunkDataProvider.get(serverLevel).get(serverLevel.getChunk(x, z));
            Settings settings = serverLevel.getChunkSource().getGenerator() instanceof ChunkGeneratorExtension ex ? ex.getBiomeSourceExtension().settings() : null;

            if (data != null)
            {
                rainfall = data.getRainfall(x, z);
                temperature = data.getAverageTemp(x, z);
                forestDensity = data.getForestDensity();
            }
            else if (settings != null && data == null)
            {
                final TFCChunkDataGenerator chunkDataGen = new TFCChunkDataGenerator(settings);
                rainfall = ((ChunkDataInterface) (Object) chunkDataGen).getRainfallNoise().noise(x0, z0);
                temperature = ((ChunkDataInterface) (Object) chunkDataGen).getTemperatureNoise().noise(x0, z0);
                forestDensity = ((ChunkDataInterface) (Object) chunkDataGen).getForestDensityNoise().noise(x0, z0);
            }
        }
        if (temperature == 0F) temperature = 1F;

        float extraWidth = Mth.clamp(((rainfall / temperature) * 0.1F) - forestDensity, 1, 10);
        final float riverWidth = Watershed.RIVER_WIDTH * extraWidth;*/

        final int value = area.get(x, z);

        if ((hasRiver(value) || value == RIVER_EDGE))
        {
            final float scale = 1f / (1 << 7);
            final float x0 = x * scale, z0 = z * scale;
            for (MidpointFractal fractal : watersheds.getFractalsByPartition(x, z))
            {
                // maybeIntersect will skip the more expensive calculation if it fails
                //if (fractal.maybeIntersect(x0, z0, watersheds.getRiverWidth(seed, x, z)) && fractal.intersect(x0, z0, watersheds.getRiverWidth(seed, x, z)))
                if (fractal.maybeIntersect(x0, z0, Watershed.RIVER_WIDTH) && fractal.intersect(x0, z0, Watershed.RIVER_WIDTH))
                {
                    return riverFor(value);
                }
            }
        }
        return value;
    }
}
