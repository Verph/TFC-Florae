package tfcflorae.world.layer.river;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.Area;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.TransformLayer;
import net.dries007.tfc.world.river.MidpointFractal;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

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
        else if (!isRiver(value) && hasRiver(value) && !hasRiverCave(value) && !isLowlands(value) && !isOcean(value) && !isLake(value) && value != LAKE_SHORE)
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