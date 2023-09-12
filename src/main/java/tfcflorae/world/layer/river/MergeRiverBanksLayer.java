package tfcflorae.world.layer.river;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.Area;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.TransformLayer;
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.river.MidpointFractal;
import net.dries007.tfc.world.settings.ClimateSettings;
import net.dries007.tfc.world.settings.RockLayerSettings;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import tfcflorae.interfaces.ChunkDataInterface;
import tfcflorae.interfaces.TFCLayersMixinInterface;
import tfcflorae.util.TFCFHelpers;

import static net.dries007.tfc.world.layer.TFCLayers.*;

import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.world.biome.BiomeSourceExtension;
import net.dries007.tfc.world.biome.BiomeSourceExtension.Settings;
import net.dries007.tfc.world.biome.LegacyBiomeSource;
import net.dries007.tfc.world.biome.RegionBiomeSource;
import net.dries007.tfc.world.biome.TFCBiomeSource;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.chunkdata.ChunkGeneratorExtension;
import net.dries007.tfc.world.chunkdata.TFCChunkDataGenerator;

public class MergeRiverBanksLayer implements TransformLayer
{
    public final long seed;
    public final WatershedBank.Context watershedBank;
    public final Watershed.Context watersheds;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int CHASMS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticChasms();
    static final int RIVERBANK = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRiverbank();
    static final int RIVER_EDGE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRiverEdge();
    static final int WETLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticWetlands();
    static final int CALDERAS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCalderas();
    static final int ALPINE_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineMountains();
    static final int ALPINE_HIGHLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineHighlands();
    static final int CANYON_RIVER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCanyonRivers();
    static final int ALPINE_MOUNTAIN_RIVER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineMountainRivers();
    static final int THERMAL_CANYONS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticThermalCanyons();
    static final int MARSHES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticThermalCanyons();
    static final int SWAMPS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMarshes();
    static final int MANGROVES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMangroves();
    static final int LAKE_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticLakeShore();

    public MergeRiverBanksLayer(WatershedBank.Context watershedBank, Watershed.Context watersheds, long seed)
    {
        this.watershedBank = watershedBank;
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
        final float riverWidth = Watershed.RIVER_WIDTH_OLD * extraWidth;
        final float riverbankWidth = WatershedBank.RIVERBANK_WIDTH_OLD * (extraWidth * 2);*/

        final int value = area.get(x, z);

        if (specialRiver(value))
        {
            final float scale = 1f / (1 << 7);
            final float x0 = x * scale, z0 = z * scale;
            for (MidpointFractal fractal : watershedBank.getFractalsByPartition(x, z))
            {
                // maybeIntersect will skip the more expensive calculation if it fails
                //if (fractal.maybeIntersect(x0, z0, watersheds.getRiverWidth(seed, x, z)) && fractal.intersect(x0, z0, watersheds.getRiverWidth(seed, x, z)))
                if (fractal.maybeIntersect(x0, z0, Watershed.RIVER_WIDTH_OLD) && fractal.intersect(x0, z0, Watershed.RIVER_WIDTH_OLD))
                {
                    return value;
                }
            }
        }
        else if (!isRiver(value) && hasRiver(value) && !hasRiverCave(value) && !isLowlands(value) && !isOcean(value) && !isLake(value) && value != LAKE_SHORE && !isOceanOrMarker(value))
        {
            final float scale = 1f / (1 << 7);
            final float x0 = x * scale, z0 = z * scale;
            for (MidpointFractal fractal : watershedBank.getFractalsByPartition(x, z))
            {
                // maybeIntersect will skip the more expensive calculation if it fails
                //if (fractal.maybeIntersect(x0, z0, watershedBank.getRiverWidth(seed, x, z)) && fractal.intersect(x0, z0, watershedBank.getRiverWidth(seed, x, z)))
                if (fractal.maybeIntersect(x0, z0, WatershedBank.RIVERBANK_WIDTH_OLD) && fractal.intersect(x0, z0, WatershedBank.RIVERBANK_WIDTH_OLD))
                {
                    return RIVER_EDGE;
                }
            }
        }
        else if (value == RIVER)
        {
            final float scale = 1f / (1 << 7);
            final float x0 = x * scale, z0 = z * scale;
            for (MidpointFractal fractal : watershedBank.getFractalsByPartition(x, z))
            {
                // maybeIntersect will skip the more expensive calculation if it fails
                //if (fractal.maybeIntersect(x0, z0, watersheds.getRiverWidth(seed, x, z)) && fractal.intersect(x0, z0, watersheds.getRiverWidth(seed, x, z)))
                if (fractal.maybeIntersect(x0, z0, Watershed.RIVER_WIDTH_OLD) && fractal.intersect(x0, z0, Watershed.RIVER_WIDTH_OLD))
                {
                    return RIVER;
                }
            }
        }
        return value;
    }

    public static boolean hasRiverCave(int value)
    {
        return value == CALDERAS || value == ALPINE_MOUNTAINS || value == ALPINE_HIGHLANDS || value == MOUNTAINS || value == VOLCANIC_MOUNTAINS || value == OLD_MOUNTAINS || value == OCEANIC_MOUNTAINS || value == VOLCANIC_OCEANIC_MOUNTAINS /*|| value == OCEANIC_MOUNTAIN_RIVER || value == OLD_MOUNTAIN_RIVER || value == MOUNTAIN_RIVER || value == VOLCANIC_OCEANIC_MOUNTAIN_RIVER || value == VOLCANIC_MOUNTAIN_RIVER || value == CANYON_RIVER || value == ALPINE_MOUNTAIN_RIVER*/;
    }

    public static boolean isLowlands(int value)
    {
        return value == LOWLANDS || value == THERMAL_CANYONS || value == LOW_CANYONS || value == WETLANDS || value == MARSHES || value == SWAMPS || value == MANGROVES;
    }

    public static boolean specialRiver(int value)
    {
        return value == CANYON_RIVER || value == ALPINE_MOUNTAIN_RIVER || value == MOUNTAIN_RIVER || value == VOLCANIC_MOUNTAIN_RIVER || value == OLD_MOUNTAIN_RIVER || value == OCEANIC_MOUNTAIN_RIVER || value == VOLCANIC_OCEANIC_MOUNTAIN_RIVER;
    }
}