package tfcflorae.world.layer;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum EdgeBiomeLayer implements AdjacentTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int GRASSLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGrasslands();
    static final int WETLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticWetlands();
    static final int MARSHES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMarshes();
    static final int SWAMPS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSwamps();
    static final int BRYCE_CANYONS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBryceCanyons();
    static final int MESA_HILLS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMesaHills();
    static final int BADLANDS_PLATEAU = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBadlandsPlateau();
    static final int ALPINE_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineMountains();
    static final int ALPINE_HIGHLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineHighlands();
    static final int ROLLING_HIGHLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRollingHighlands();
    static final int CALDERAS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCalderas();
    static final int THERMAL_CANYONS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticThermalCanyons();
    static final int MESA_PLATEAU = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMesaPlateau();
    static final int PEAT_BOG = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPeatBog();
    static final int GRAVEL_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGravelShores();
    static final int STEPPES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSteppes();
    static final int SHRUBLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShrublands();
    static final int MOORLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMoorlands();
    static final int MISTY_PEAKS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMistyPeaks();
    static final int NEAR_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticNearShore();
    static final int SHORE_DUNES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShoreDunes();
    static final int PLATEAU_CLIFFS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPlateauCliffs();
    static final int PELAGIC_ZONE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPelagicZone();
    static final int SEAMOUNTS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSeamounts();
    static final int GUYOTS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGuyots();
    static final int SAWTOOTH_CLIFFS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSawtoothCliffs();
    static final int TABLELANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticTablelands();
    static final int PITLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPitlands();
    static final int FRACTURED_MISTY_PEAKS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticFracturedMistyPeaks();

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (center == PLATEAU || center == BADLANDS || center == INVERTED_BADLANDS || center == ALPINE_HIGHLANDS || center == MESA_HILLS)
        {
            if (matcher.test(i -> i == LOW_CANYONS || i == LOWLANDS || i == WETLANDS || i == MARSHES || i == SWAMPS))
            {
                return HILLS;
            }
            else if (matcher.test(i -> i == PLAINS || i == HILLS || i == GRASSLANDS))
            {
                return ROLLING_HILLS;
            }
        }
        else if (center == BRYCE_CANYONS)
        {
            if (matcher.test(i -> i != BRYCE_CANYONS))
            {
                return MESA_HILLS;
            }
            /*else if (matcher.test(i -> i == PLAINS || i == HILLS || i == GRASSLANDS || i == LOW_CANYONS || i == LOWLANDS || i == WETLANDS || i == MARSHES || i == SWAMPS || i == ALPINE_HIGHLANDS))
            {
                return MESA_HILLS;
            }*/
        }
        else if (center == MESA_HILLS)
        {
            if (matcher.test(i -> i == BRYCE_CANYONS))
            {
                return MESA_HILLS;
            }
            else if (matcher.test(i -> i != MESA_HILLS))
            {
                return STEPPES;
            }
        }
        else if (center == BADLANDS_PLATEAU)
        {
            if (matcher.test(i -> i == MESA_HILLS || i == BRYCE_CANYONS))
            {
                return BADLANDS;
            }
        }
        else if (center == FRACTURED_MISTY_PEAKS)
        {
            if (matcher.test(i -> i != FRACTURED_MISTY_PEAKS))
            {
                return MISTY_PEAKS;
            }
        }
        else if (center == MISTY_PEAKS)
        {
            if (matcher.test(TFCLayers::isLow))
            {
                return MESA_PLATEAU;
            }
        }
        else if (center == CALDERAS)
        {
            if (matcher.test(i -> i != CALDERAS))
            {
                return ALPINE_HIGHLANDS;
            }
        }
        else if (center == ALPINE_MOUNTAINS)
        {
            if (matcher.test(i -> i != ALPINE_MOUNTAINS))
            {
                return ALPINE_HIGHLANDS;
            }
            /*else if (matcher.test(i -> i == PLAINS || i == HILLS || i == GRASSLANDS || i == LOW_CANYONS || i == LOWLANDS || i == WETLANDS || i == MARSHES || i == SWAMPS || i == MESA_HILLS))
            {
                return ALPINE_HIGHLANDS;
            }*/
        }
        else if (center == ALPINE_HIGHLANDS)
        {
            if (matcher.test(i -> i != ALPINE_MOUNTAINS))
            {
                return ROLLING_HIGHLANDS;
            }
        }
        else if (center == THERMAL_CANYONS)
        {
            if (matcher.test(i -> i != THERMAL_CANYONS))
            {
                return THERMAL_CANYONS;
            }
        }
        else if (center == MESA_PLATEAU)
        {
            if (matcher.test(i -> i != MESA_PLATEAU && !isLow(i)))
            {
                return ROLLING_HIGHLANDS;
            }
        }
        else if (center == ROLLING_HIGHLANDS)
        {
            if (matcher.test(i -> i != ROLLING_HIGHLANDS))
            {
                return ROLLING_HILLS;
            }
        }
        else if (center == PEAT_BOG)
        {
            if (matcher.test(i -> i != PEAT_BOG))
            {
                return PLAINS;
            }
        }
        else if (center == MOORLANDS)
        {
            if (matcher.test(i -> i != MOORLANDS))
            {
                return MARSHES;
            }
        }
        else if (center == TABLELANDS)
        {
            if (matcher.test(i -> i != TABLELANDS))
            {
                return PITLANDS;
            }
        }
        else if (center == SAWTOOTH_CLIFFS)
        {
            if (matcher.test(i -> i != SAWTOOTH_CLIFFS))
            {
                return TABLELANDS;
            }
        }
        else if (TFCLayers.isMountains(center))
        {
            if (matcher.test(TFCLayers::isLow))
            {
                return ROLLING_HILLS;
            }
        }
        else if (center == LAKE)
        {
            if (matcher.test(i -> i != LAKE))
            {
                return GRAVEL_SHORE;
            }
        }
        // Inverses of above conditions
        else if (center == LOWLANDS || center == LOW_CANYONS || center == WETLANDS || center == MARSHES || center == SWAMPS)
        {
            if (matcher.test(i -> i == PLATEAU || i == BADLANDS || i == INVERTED_BADLANDS || i == ALPINE_HIGHLANDS || i == MESA_HILLS))
            {
                return HILLS;
            }
            else if (matcher.test(TFCLayers::isMountains))
            {
                return ROLLING_HILLS;
            }
        }
        else if (center == PLAINS || center == HILLS || center == GRASSLANDS)
        {
            if (matcher.test(i -> i == PLATEAU || i == BADLANDS || i == INVERTED_BADLANDS || i == ALPINE_HIGHLANDS || i == MESA_HILLS))
            {
                return HILLS;
            }
            else if (matcher.test(TFCLayers::isMountains))
            {
                return ROLLING_HILLS;
            }
        }
        else if (center == PELAGIC_ZONE)
        {
            if (matcher.test(i -> i == SEAMOUNTS))
            {
                return SEAMOUNTS;
            }
            else if (matcher.test(i -> i == GUYOTS))
            {
                return GUYOTS;
            }
            else if (matcher.test(i -> i == DEEP_OCEAN))
            {
                return DEEP_OCEAN_TRENCH;
            }
            else if (matcher.test(i -> i == OCEAN))
            {
                return DEEP_OCEAN;
            }
            else if (matcher.test(i -> !isOcean(i)))
            {
                return DEEP_OCEAN;
            }
        }
        else if (center == DEEP_OCEAN_TRENCH)
        {
            if (matcher.test(i -> !isOcean(i)))
            {
                return OCEAN;
            }
        }
        return center;
    }
}