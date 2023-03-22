package tfcflorae.world.layer;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum EdgeRiverLayer implements AdjacentTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int BRYCE_CANYONS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBryceCanyons();
    static final int MESA_HILLS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMesaHills();
    static final int ALPINE_HIGHLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineHighlands();
    static final int ROLLING_HIGHLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRollingHighlands();
    static final int THERMAL_CANYONS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticThermalCanyons();
    static final int MESA_PLATEAU = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMesaPlateau();
    static final int PUY_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPuyMountains();
    static final int RIVERBANK = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRiverbank();
    static final int CHASMS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticChasms();
    static final int RIVER_EDGE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRiverEdge();
    static final int WETLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticWetlands();
    static final int MARSHES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMarshes();
    static final int SWAMPS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSwamps();
    static final int CALDERAS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCalderas();
    static final int ALPINE_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineMountains();
    static final int CANYON_RIVER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCanyonRivers();
    static final int ALPINE_MOUNTAIN_RIVER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineMountainRivers();

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (center == RIVER)
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
            else if (matcher.test(i -> i == LOWLANDS || i == THERMAL_CANYONS || i == LOW_CANYONS || i == WETLANDS || i == MARSHES || i == SWAMPS))
            {
                return RIVER;
            }
            else if (matcher.test(i -> i == LAKE))
            {
                return LAKE;
            }
            else if (matcher.test(i -> !hasRiverCave(i) && i == BRYCE_CANYONS || i == MESA_HILLS || i == ALPINE_HIGHLANDS || i == ROLLING_HIGHLANDS /*|| i == THERMAL_CANYONS*/ || i == MESA_PLATEAU || i == PUY_MOUNTAINS || i == PLAINS || i == HILLS /*|| i == LOW_CANYONS*/ || i == ROLLING_HILLS || i == BADLANDS || i == INVERTED_BADLANDS || i == PLATEAU || i == CANYONS)/* && matcher.test(i -> i != RIVER) */)
            {
                return RIVERBANK;
            }
        }
        return center;
    }

    public static boolean hasRiverCave(int value)
    {
        return value == ALPINE_MOUNTAINS || value == MOUNTAINS || value == VOLCANIC_MOUNTAINS || value == OLD_MOUNTAINS || value == OCEANIC_MOUNTAINS || value == VOLCANIC_OCEANIC_MOUNTAINS;
    }
}
