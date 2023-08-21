package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterTransformLayer;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum PlateBiomeLayer implements CenterTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int GRASSLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGrasslands();
    static final int WETLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticWetlands();
    static final int MARSHES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMarshes();
    static final int SWAMPS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSwamps();
    static final int PUY_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPuyMountains();
    static final int BRYCE_CANYONS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBryceCanyons();
    static final int MESA_HILLS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMesaHills();
    static final int BADLANDS_PLATEAU = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBadlandsPlateau();
    static final int ALPINE_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineMountains();
    static final int ALPINE_HIGHLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineHighlands();
    static final int ROLLING_HIGHLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRollingHighlands();
    static final int THERMAL_CANYONS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticThermalCanyons();
    static final int MESA_PLATEAU = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMesaPlateau();
    static final int PEAT_BOG = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPeatBog();
    static final int STEPPES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSteppes();
    static final int SHRUBLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShrublands();
    static final int MOORLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMoorlands();
    static final int MISTY_PEAKS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMistyPeaks();
    static final int VINICUNCA_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticVinicuncaMountains();
    static final int PELAGIC_ZONE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPelagicZone();
    static final int SEAMOUNTS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSeamounts();
    static final int GUYOTS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGuyots();
    //static final int SAWTOOTH_CLIFFS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSawtoothCliffs();
    //static final int TABLELANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticTablelands();
    static final int PITLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPitlands();
    static final int FRACTURED_MISTY_PEAKS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticFracturedMistyPeaks();

    private static final int[] OCEANIC_BIOMES = {DEEP_OCEAN, PELAGIC_ZONE};
    private static final int[] OCEAN_CONTINENT_CONVERGING = {DEEP_OCEAN_TRENCH, PELAGIC_ZONE, SEAMOUNTS, GUYOTS};
    private static final int[] SUBDUCTION_BIOMES = {VOLCANIC_OCEANIC_MOUNTAINS, VOLCANIC_OCEANIC_MOUNTAINS, VOLCANIC_MOUNTAINS, VOLCANIC_MOUNTAINS, MOUNTAINS, PLATEAU, ALPINE_MOUNTAINS, ALPINE_HIGHLANDS, ROLLING_HIGHLANDS, THERMAL_CANYONS, THERMAL_CANYONS, MISTY_PEAKS, FRACTURED_MISTY_PEAKS/*, SAWTOOTH_CLIFFS*/};
    private static final int[] OROGENY_BIOMES = {MOUNTAINS, MOUNTAINS, MOUNTAINS, OLD_MOUNTAINS, PLATEAU, ALPINE_MOUNTAINS, ALPINE_HIGHLANDS, ROLLING_HIGHLANDS, THERMAL_CANYONS, THERMAL_CANYONS, MISTY_PEAKS, VINICUNCA_MOUNTAINS, FRACTURED_MISTY_PEAKS, PITLANDS/*, SAWTOOTH_CLIFFS*/};
    private static final int[] RIFT_BIOMES = {CANYONS, CANYONS, CANYONS, CANYONS, ROLLING_HILLS, OLD_MOUNTAINS, PUY_MOUNTAINS, BRYCE_CANYONS, MESA_HILLS, ROLLING_HIGHLANDS, THERMAL_CANYONS, THERMAL_CANYONS, PITLANDS};
    private static final int[] CONTINENT_LOW_BIOMES = {PLAINS, PLAINS, HILLS, ROLLING_HILLS, LOW_CANYONS, LOW_CANYONS, LOWLANDS, LOWLANDS, GRASSLANDS, GRASSLANDS, WETLANDS, WETLANDS, MARSHES, MARSHES, SWAMPS, SWAMPS, ROLLING_HIGHLANDS, MESA_PLATEAU, PEAT_BOG, STEPPES, STEPPES, MOORLANDS};
    private static final int[] CONTINENT_MID_BIOMES = {PLAINS, HILLS, ROLLING_HILLS, INVERTED_BADLANDS, BADLANDS, BADLANDS_PLATEAU, PLATEAU, LOW_CANYONS, LOWLANDS, GRASSLANDS, WETLANDS, MARSHES, SWAMPS, PUY_MOUNTAINS, MESA_HILLS, ALPINE_HIGHLANDS, ROLLING_HIGHLANDS, MESA_PLATEAU, STEPPES, SHRUBLANDS, SHRUBLANDS, MOORLANDS/*, TABLELANDS*/, PITLANDS};
private static final int[] CONTINENT_HIGH_BIOMES = {HILLS, ROLLING_HILLS, ROLLING_HILLS, INVERTED_BADLANDS, BADLANDS, BADLANDS_PLATEAU, PLATEAU, PLATEAU, OLD_MOUNTAINS, OLD_MOUNTAINS, PUY_MOUNTAINS, BRYCE_CANYONS, MESA_HILLS, ALPINE_MOUNTAINS, ALPINE_HIGHLANDS, ROLLING_HIGHLANDS, THERMAL_CANYONS, SHRUBLANDS, MOORLANDS, MISTY_PEAKS, VINICUNCA_MOUNTAINS, FRACTURED_MISTY_PEAKS/*, TABLELANDS, SAWTOOTH_CLIFFS*/};

    @Override
    public int apply(AreaContext context, int value)
    {
        switch (value)
        {
            case OCEANIC:
                // Main oceanic plate body - generate deep oceans
                return context.choose(OCEANIC_BIOMES);
            case CONTINENTAL_LOW:
                // Normal biomes
                return context.choose(CONTINENT_LOW_BIOMES);
            case CONTINENTAL_MID:
                // Mid scale height biomes
                return context.choose(CONTINENT_MID_BIOMES);
            case CONTINENTAL_HIGH:
                // High height biomes
                return context.choose(CONTINENT_HIGH_BIOMES);
            case OCEAN_OCEAN_DIVERGING:
                // Oceanic diverging - new plate being generated
                // Return a marker that will get replaced with sporadic flooded mountains (non-volcanic) or ocean
                return OCEAN_OCEAN_DIVERGING_MARKER;
            case OCEAN_OCEAN_CONVERGING_LOWER:
                // The subducting plate of an oceanic converging boundary. Creates a trench on the subducting side and volcanic islands on the upper side
                // This is the trench side, so generate deep ocean trench
                return context.choose(OCEAN_CONTINENT_CONVERGING);
            case OCEAN_OCEAN_CONVERGING_UPPER:
                // The upper of two subducting plates. Creates an series of oceanic volcanic islands.
                return OCEAN_OCEAN_CONVERGING_MARKER;
            case OCEAN_CONTINENT_CONVERGING_LOWER:
                // A subducting oceanic plate under a continental plate. Creates a deep trench adjacent to the shore
                return context.choose(OCEAN_CONTINENT_CONVERGING);
            case OCEAN_CONTINENT_CONVERGING_UPPER:
                // Continental subduction biomes. Highly volcanic mountain areas
                return context.choose(SUBDUCTION_BIOMES);
            case CONTINENT_CONTINENT_DIVERGING:
                // Diverging areas create volcanoes, rifts, and rift valleys. This is a very varied set of biomes with a lot of volcanic activity
                return context.choose(RIFT_BIOMES);
            case CONTINENT_CONTINENT_CONVERGING:
                // Non-volcanic mountain building
                return context.choose(OROGENY_BIOMES);
            case CONTINENTAL_SHELF:
                // Continental shelf, for continental plate area that is still underwater
                // This is generated as a replacement for ocean-continental diverging boundaries, and helps create better mid-ocean ridges
                return OCEAN;
        }
        throw new IllegalStateException("What is this: " + value);
    }
}
