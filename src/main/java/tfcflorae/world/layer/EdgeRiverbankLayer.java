package tfcflorae.world.layer;

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

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (center == RIVER_EDGE)
        {
            if (matcher.test(i -> i == SHORE))
            {
                return SHORE;
            }
            else if (matcher.test(i -> i == GRAVEL_SHORE))
            {
                return GRAVEL_SHORE;
            }
            else if (matcher.test(TFCLayers::isOcean))
            {
                return OCEAN;
            }
            else if (matcher.test(i -> !TFCLayers.isRiver(i) && TFCLayers.hasRiver(i) && !hasRiverCave(i)))
            {
                return RIVERBANK;
            }
            else if (matcher.test(i -> i == RIVER))
            {
                return RIVER;
            }
            else if (matcher.test(i -> i == LOWLANDS))
            {
                return LOWLANDS;
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
            else if (matcher.test(i -> i == LAKE))
            {
                return LAKE;
            }
            return RIVER;
        }
        /*if (center == RIVER_EDGE) // Works with shores --> mimic this
        {
            if (matcher.test(i -> i == RIVER))
            {
                return RIVER;
            }
            else if (matcher.test(i -> i != LOWLANDS || i != WETLANDS || i != MARSHES || i != SWAMPS))
            {
                return RIVERBANK;
            }
            else if (matcher.test(i -> i == LOWLANDS))
            {
                return LOWLANDS;
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
        }*/
        return center;
    }

    public static boolean hasRiverCave(int value)
    {
        return value == CALDERAS || value == ALPINE_MOUNTAINS || value == ALPINE_HIGHLANDS || value == MOUNTAINS || value == VOLCANIC_MOUNTAINS || value == OLD_MOUNTAINS || value == OCEANIC_MOUNTAINS || value == VOLCANIC_OCEANIC_MOUNTAINS || value == OCEANIC_MOUNTAIN_RIVER || value == OLD_MOUNTAIN_RIVER || value == MOUNTAIN_RIVER || value == VOLCANIC_OCEANIC_MOUNTAIN_RIVER || value == VOLCANIC_MOUNTAIN_RIVER || value == CANYON_RIVER || value == ALPINE_MOUNTAIN_RIVER;
    }
}