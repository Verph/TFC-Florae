package tfcflorae.world.layer.river;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum EdgeRiverbankLayer implements AdjacentTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int CHASMS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticChasms();
    static final int RIVERBANK = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRiverbank();
    static final int RIVER_EDGE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRiverEdge();
    static final int WETLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticWetlands();
    static final int MARSHES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMarshes();
    static final int SWAMPS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSwamps();
    static final int CALDERAS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCalderas();
    static final int ALPINE_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineMountains();
    static final int ALPINE_HIGHLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineHighlands();
    static final int CANYON_RIVER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCanyonRivers();
    static final int ALPINE_MOUNTAIN_RIVER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineMountainRivers();
    static final int GRAVEL_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGravelShores();
    static final int THERMAL_CANYONS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticThermalCanyons();
    static final int NEAR_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticNearShore();
    static final int SHORE_DUNES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShoreDunes();
    static final int MANGROVES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMangroves();
    static final int LAKE_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticLakeShore();

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (matcher.test(i -> !isOceanOrMarker(i)))
        {
            if (center == RIVER_EDGE)
            {
                if (matcher.test(i -> i == CALDERAS))
                {
                    return CANYON_RIVER;
                }
                else if (matcher.test(i -> i == ALPINE_MOUNTAINS))
                {
                    return ALPINE_MOUNTAIN_RIVER;
                }
                else if (matcher.test(i -> i == MOUNTAINS))
                {
                    return MOUNTAIN_RIVER;
                }
                else if (matcher.test(i -> i == VOLCANIC_MOUNTAINS))
                {
                    return VOLCANIC_MOUNTAIN_RIVER;
                }
                else if (matcher.test(i -> i == OLD_MOUNTAINS))
                {
                    return OLD_MOUNTAIN_RIVER;
                }
                else if (matcher.test(i -> i == OCEANIC_MOUNTAINS))
                {
                    return OCEANIC_MOUNTAIN_RIVER;
                }
                else if (matcher.test(i -> i == VOLCANIC_OCEANIC_MOUNTAINS))
                {
                    return VOLCANIC_OCEANIC_MOUNTAIN_RIVER;
                }
                else if (matcher.test(i -> i == SHORE))
                {
                    return SHORE;
                }
                else if (matcher.test(i -> i == GRAVEL_SHORE))
                {
                    return GRAVEL_SHORE;
                }
                else if (matcher.test(i -> isOcean(i)))
                {
                    return OCEAN;
                }
                else if (matcher.test(i -> i == LOWLANDS))
                {
                    return LOWLANDS;
                }
                else if (matcher.test(i -> i == THERMAL_CANYONS))
                {
                    return THERMAL_CANYONS;
                }
                else if (matcher.test(i -> i == LOW_CANYONS))
                {
                    return LOW_CANYONS;
                }
                else if (matcher.test(i -> i == WETLANDS))
                {
                    return WETLANDS;
                }
                else if (matcher.test(i -> i == MARSHES))
                {
                    return MARSHES;
                }
                else if (matcher.test(i -> i == SWAMPS))
                {
                    return SWAMPS;
                }
                else if (matcher.test(i -> i == LAKE || i == LAKE_SHORE))
                {
                    return LAKE;
                }
                else if (matcher.test(i -> !isRiver(i) && hasRiver(i) && riverFor(i) == RIVER && !isLowlands(i)))
                {
                    if (matcher.test(i -> i == SHORE || i == NEAR_SHORE || i == GRAVEL_SHORE))
                    {
                        return NEAR_SHORE;
                    }
                    else if (matcher.test(i -> i == SHORE_DUNES))
                    {
                        return SHORE_DUNES;
                    }
                    return RIVERBANK;
                }
                else if (matcher.test(i -> i == RIVER))
                {
                    return RIVER;
                }
            }
            else if (center == RIVER)
            {
                if (matcher.test(i -> i == CALDERAS))
                {
                    return CANYON_RIVER;
                }
                else if (matcher.test(i -> i == ALPINE_MOUNTAINS))
                {
                    return ALPINE_MOUNTAIN_RIVER;
                }
                else if (matcher.test(i -> i == MOUNTAINS))
                {
                    return MOUNTAIN_RIVER;
                }
                else if (matcher.test(i -> i == VOLCANIC_MOUNTAINS))
                {
                    return VOLCANIC_MOUNTAIN_RIVER;
                }
                else if (matcher.test(i -> i == OLD_MOUNTAINS))
                {
                    return OLD_MOUNTAIN_RIVER;
                }
                else if (matcher.test(i -> i == OCEANIC_MOUNTAINS))
                {
                    return OCEANIC_MOUNTAIN_RIVER;
                }
                else if (matcher.test(i -> i == VOLCANIC_OCEANIC_MOUNTAINS))
                {
                    return VOLCANIC_OCEANIC_MOUNTAIN_RIVER;
                }
            }
        }
        return center;
    }

    public static boolean hasRiverCave(int value)
    {
        return value == ALPINE_MOUNTAINS || value == MOUNTAINS || value == VOLCANIC_MOUNTAINS || value == OLD_MOUNTAINS || value == OCEANIC_MOUNTAINS || value == VOLCANIC_OCEANIC_MOUNTAINS;
    }

    public static boolean isLowlands(int value)
    {
        return value == LOWLANDS || value == THERMAL_CANYONS || value == LOW_CANYONS || value == WETLANDS || value == MARSHES || value == SWAMPS || value == MANGROVES;
    }
}