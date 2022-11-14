package tfcflorae.mixin.world.layer;

import org.spongepowered.asm.mixin.*;

import java.util.Random;
import java.util.function.Supplier;

import org.apache.commons.lang3.mutable.MutableInt;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.IArtist;
import net.dries007.tfc.world.biome.BiomeExtension;
import net.dries007.tfc.world.biome.TFCBiomes;
import net.dries007.tfc.world.chunkdata.ForestType;
import net.dries007.tfc.world.chunkdata.PlateTectonicsClassification;
import net.dries007.tfc.world.layer.*;
import net.dries007.tfc.world.layer.framework.AreaFactory;
import net.dries007.tfc.world.layer.framework.TypedAreaFactory;
import net.dries007.tfc.world.noise.*;
import net.dries007.tfc.world.river.*;

import tfcflorae.interfaces.*;
import tfcflorae.world.layer.EdgeBiomeLayer;
import tfcflorae.world.layer.PlateBiomeLayer;

@Mixin(TFCLayers.class)
public class TFCLayersMixin implements TFCLayersMixinInterface
{
    @Unique private static TFCBiomes staticBiomes = new TFCBiomes();

    /**
     * These IDs are used during plate tectonic layer generation
     * They're declared here as compile time constants so they can be used optimally in switch statements later
     *
     * @see PlateTectonicsClassification
     */
    @Shadow public static final int OCEANIC = 0;
    @Shadow public static final int CONTINENTAL_LOW = 1;
    @Shadow public static final int CONTINENTAL_MID = 2;
    @Shadow public static final int CONTINENTAL_HIGH = 3;
    @Shadow public static final int OCEAN_OCEAN_DIVERGING = 4;
    @Shadow public static final int OCEAN_OCEAN_CONVERGING_LOWER = 5;
    @Shadow public static final int OCEAN_OCEAN_CONVERGING_UPPER = 6;
    @Shadow public static final int OCEAN_CONTINENT_CONVERGING_LOWER = 7;
    @Shadow public static final int OCEAN_CONTINENT_CONVERGING_UPPER = 8;
    @Shadow public static final int OCEAN_CONTINENT_DIVERGING = 9;
    @Shadow public static final int CONTINENT_CONTINENT_DIVERGING = 10;
    @Shadow public static final int CONTINENT_CONTINENT_CONVERGING = 11;
    @Shadow public static final int CONTINENTAL_SHELF = 12;

    /**
     * These are the int IDs that are used for forest layer generation
     */
    @Shadow public static final int FOREST_NONE = ForestType.NONE.ordinal();
    @Shadow public static final int FOREST_NORMAL = ForestType.NORMAL.ordinal();
    @Shadow public static final int FOREST_SPARSE = ForestType.SPARSE.ordinal();
    @Shadow public static final int FOREST_EDGE = ForestType.EDGE.ordinal();
    @Shadow public static final int FOREST_OLD = ForestType.OLD_GROWTH.ordinal();

    /**
     * These are the int IDs that are used for biome layer generation
     * They are mapped to {@link BiomeExtension} through the internal registry
     */
    @Shadow public static final int OCEAN;
    @Shadow public static final int OCEAN_REEF;
    @Shadow public static final int DEEP_OCEAN;
    @Shadow public static final int DEEP_OCEAN_TRENCH;
    @Shadow public static final int PLAINS;
    @Shadow public static final int HILLS;
    @Shadow public static final int LOWLANDS;
    @Shadow public static final int LOW_CANYONS;
    @Shadow public static final int ROLLING_HILLS;
    @Shadow public static final int BADLANDS;
    @Shadow public static final int INVERTED_BADLANDS;
    @Shadow public static final int PLATEAU;
    @Shadow public static final int OLD_MOUNTAINS;
    @Shadow public static final int MOUNTAINS;
    @Shadow public static final int VOLCANIC_MOUNTAINS;
    @Shadow public static final int OCEANIC_MOUNTAINS;
    @Shadow public static final int VOLCANIC_OCEANIC_MOUNTAINS;
    @Shadow public static final int CANYONS;
    @Shadow public static final int SHORE;
    @Shadow public static final int LAKE;
    @Shadow public static final int RIVER;
    @Shadow public static final int MOUNTAIN_RIVER;
    @Shadow public static final int VOLCANIC_MOUNTAIN_RIVER;
    @Shadow public static final int OLD_MOUNTAIN_RIVER;
    @Shadow public static final int OCEANIC_MOUNTAIN_RIVER;
    @Shadow public static final int VOLCANIC_OCEANIC_MOUNTAIN_RIVER;
    @Shadow public static final int MOUNTAIN_LAKE;
    @Shadow public static final int VOLCANIC_MOUNTAIN_LAKE;
    @Shadow public static final int OLD_MOUNTAIN_LAKE;
    @Shadow public static final int OCEANIC_MOUNTAIN_LAKE;
    @Shadow public static final int VOLCANIC_OCEANIC_MOUNTAIN_LAKE;
    @Shadow public static final int PLATEAU_LAKE;

    /*@Unique private static final int GRASSLANDS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticGrasslands);
    @Unique private static final int WETLANDS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticWetlands);
    @Unique private static final int MARSHES = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticMarshes);
    @Unique private static final int SWAMPS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticSwamps);*/

    @Unique private static final int GRASSLANDS;
    @Unique private static final int WETLANDS;
    @Unique private static final int MARSHES;
    @Unique private static final int SWAMPS;
    @Unique private static final int MANGROVES;
    @Unique private static final int PUY_MOUNTAINS;
    @Unique private static final int BRYCE_CANYONS;
    @Unique private static final int MESA_HILLS;
    @Unique private static final int CANYON_RIVER;
    @Unique private static final int ALPINE_MOUNTAIN_RIVER;
    @Unique private static final int ALPINE_MOUNTAINS;
    @Unique private static final int ALPINE_HIGHLANDS;
    @Unique private static final int ROLLING_HIGHLANDS;
    @Unique private static final int CALDERAS;
    @Unique private static final int GRAVEL_SHORE;
    @Unique private static final int THERMAL_CANYONS;
    @Unique private static final int MESA_PLATEAU;
    @Unique private static final int PEAT_BOG;

    /**
     * These IDs are used as markers for biomes. They should all be removed by the time the biome layers are finished
     */
    @Shadow public static final int OCEAN_OCEAN_CONVERGING_MARKER;
    @Shadow public static final int OCEAN_OCEAN_DIVERGING_MARKER;
    @Shadow public static final int LAKE_MARKER;
    @Shadow public static final int NULL_MARKER;
    @Shadow public static final int INLAND_MARKER;
    @Shadow public static final int OCEAN_REEF_MARKER;

    @Shadow private static final BiomeExtension[] BIOME_LAYERS = new BiomeExtension[64];
    @Shadow private static final MutableInt BIOME_LAYER_INDEX = new MutableInt(0);

    static
    {
        OCEAN = register(() -> TFCBiomes.OCEAN);
        OCEAN_REEF = register(() -> TFCBiomes.OCEAN_REEF);
        DEEP_OCEAN = register(() -> TFCBiomes.DEEP_OCEAN);
        DEEP_OCEAN_TRENCH = register(() -> TFCBiomes.DEEP_OCEAN_TRENCH);
        PLAINS = register(() -> TFCBiomes.PLAINS);
        HILLS = register(() -> TFCBiomes.HILLS);
        LOWLANDS = register(() -> TFCBiomes.LOWLANDS);
        LOW_CANYONS = register(() -> TFCBiomes.LOW_CANYONS);
        ROLLING_HILLS = register(() -> TFCBiomes.ROLLING_HILLS);
        BADLANDS = register(() -> TFCBiomes.BADLANDS);
        INVERTED_BADLANDS = register(() -> TFCBiomes.INVERTED_BADLANDS);
        PLATEAU = register(() -> TFCBiomes.PLATEAU);
        OLD_MOUNTAINS = register(() -> TFCBiomes.OLD_MOUNTAINS);
        MOUNTAINS = register(() -> TFCBiomes.MOUNTAINS);
        VOLCANIC_MOUNTAINS = register(() -> TFCBiomes.VOLCANIC_MOUNTAINS);
        OCEANIC_MOUNTAINS = register(() -> TFCBiomes.OCEANIC_MOUNTAINS);
        VOLCANIC_OCEANIC_MOUNTAINS = register(() -> TFCBiomes.VOLCANIC_OCEANIC_MOUNTAINS);
        CANYONS = register(() -> TFCBiomes.CANYONS);
        SHORE = register(() -> TFCBiomes.SHORE);
        LAKE = register(() -> TFCBiomes.LAKE);
        RIVER = register(() -> TFCBiomes.RIVER);
        MOUNTAIN_RIVER = register(() -> TFCBiomes.MOUNTAIN_RIVER);
        VOLCANIC_MOUNTAIN_RIVER = register(() -> TFCBiomes.VOLCANIC_MOUNTAIN_RIVER);
        OLD_MOUNTAIN_RIVER = register(() -> TFCBiomes.OLD_MOUNTAIN_RIVER);
        OCEANIC_MOUNTAIN_RIVER = register(() -> TFCBiomes.OCEANIC_MOUNTAIN_RIVER);
        VOLCANIC_OCEANIC_MOUNTAIN_RIVER = register(() -> TFCBiomes.VOLCANIC_OCEANIC_MOUNTAIN_RIVER);
        MOUNTAIN_LAKE = register(() -> TFCBiomes.MOUNTAIN_LAKE);
        VOLCANIC_MOUNTAIN_LAKE = register(() -> TFCBiomes.VOLCANIC_MOUNTAIN_LAKE);
        OLD_MOUNTAIN_LAKE = register(() -> TFCBiomes.OLD_MOUNTAIN_LAKE);
        OCEANIC_MOUNTAIN_LAKE = register(() -> TFCBiomes.OCEANIC_MOUNTAIN_LAKE);
        VOLCANIC_OCEANIC_MOUNTAIN_LAKE = register(() -> TFCBiomes.VOLCANIC_OCEANIC_MOUNTAIN_LAKE);
        PLATEAU_LAKE = register(() -> TFCBiomes.PLATEAU_LAKE);

        GRASSLANDS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticGrasslands);
        WETLANDS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticWetlands);
        MARSHES = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticMarshes);
        SWAMPS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticSwamps);
        MANGROVES = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticMangroves);
        PUY_MOUNTAINS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticPuyMountains);
        BRYCE_CANYONS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticBryceCanyons);
        MESA_HILLS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticMesaHills);
        CANYON_RIVER = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticCanyonRivers);
        ALPINE_MOUNTAIN_RIVER = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticAlpineMountainRivers);
        ALPINE_MOUNTAINS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticAlpineMountains);
        ALPINE_HIGHLANDS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticAlpineHighlands);
        ROLLING_HIGHLANDS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticRollingHighlands);
        CALDERAS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticCalderas);
        GRAVEL_SHORE = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticGravelShores);
        THERMAL_CANYONS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticThermalCanyons);
        MESA_PLATEAU = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticMesaPlateau);
        PEAT_BOG = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticPeatBog);

        OCEAN_OCEAN_CONVERGING_MARKER = register();
        OCEAN_OCEAN_DIVERGING_MARKER = register();
        LAKE_MARKER = register();
        NULL_MARKER = register();
        INLAND_MARKER = register();
        OCEAN_REEF_MARKER = register();
    }

    @Override
    public int getStaticGrasslands()
    {
        return GRASSLANDS;
    }

    @Override
    public int getStaticWetlands()
    {
        return WETLANDS;
    }

    @Override
    public int getStaticMarshes()
    {
        return MARSHES;
    }

    @Override
    public int getStaticSwamps()
    {
        return SWAMPS;
    }

    @Override
    public int getStaticMangroves()
    {
        return MANGROVES;
    }

    @Override
    public int getStaticPuyMountains()
    {
        return PUY_MOUNTAINS;
    }

    @Override
    public int getStaticBryceCanyons()
    {
        return BRYCE_CANYONS;
    }

    @Override
    public int getStaticMesaHills()
    {
        return MESA_HILLS;
    }

    @Override
    public int getStaticCanyonRivers()
    {
        return CANYON_RIVER;
    }

    @Override
    public int getStaticAlpineMountainRivers()
    {
        return ALPINE_MOUNTAIN_RIVER;
    }

    @Override
    public int getStaticAlpineMountains()
    {
        return ALPINE_MOUNTAINS;
    }

    @Override
    public int getStaticAlpineHighlands()
    {
        return ALPINE_HIGHLANDS;
    }

    @Override
    public int getStaticRollingHighlands()
    {
        return ROLLING_HIGHLANDS;
    }

    @Override
    public int getStaticCalderas()
    {
        return CALDERAS;
    }

    @Override
    public int getStaticGravelShores()
    {
        return GRAVEL_SHORE;
    }

    @Unique @Override
    public int getStaticThermalCanyons()
    {
        return THERMAL_CANYONS;
    }

    @Unique @Override
    public int getStaticMesaPlateau()
    {
        return MESA_PLATEAU;
    }

    @Unique @Override
    public int getStaticPeatBog()
    {
        return PEAT_BOG;
    }

    @Overwrite(remap = false)
    public static BiomeExtension getFromLayerId(int id)
    {
        final BiomeExtension v = BIOME_LAYERS[id];
        if (v == null)
        {
            throw new NullPointerException("Layer id = " + id + " returned null!");
        }
        return v;
    }

    @Overwrite(remap = false)
    public static AreaFactory createOverworldBiomeLayer(long seed, IArtist<TypedAreaFactory<Plate>> plateArtist, IArtist<AreaFactory> layerArtist)
    {
        final Random random = new Random(seed);

        TypedAreaFactory<Plate> plateLayer;
        AreaFactory mainLayer, lakeLayer;

        // Tectonic Plates - generate plates and annotate border regions with converging / diverging boundaries
        plateLayer = new PlateGenerationLayer(new Cellular2D(random.nextInt()).spread(0.2f), 40).apply(random.nextLong());
        plateArtist.draw("plate_generation", 1, plateLayer);
        plateLayer = new TypedZoomLayer.Fuzzy<Plate>().apply(random.nextLong(), plateLayer);
        plateArtist.draw("plate_generation", 2, plateLayer);

        mainLayer = PlateBoundaryLayer.INSTANCE.apply(random.nextLong(), plateLayer);
        layerArtist.draw("plate_boundary", 1, mainLayer);
        mainLayer = SmoothLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("plate_boundary", 2, mainLayer);
        mainLayer = PlateBoundaryModifierLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("plate_boundary", 3, mainLayer);

        // Plates -> Biomes
        mainLayer = PlateBiomeLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 1, mainLayer);

        // Initial Biomes -> Lake Setup
        lakeLayer = InlandLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("lake", 1, lakeLayer);
        lakeLayer = ZoomLayer.NORMAL.apply(1001, lakeLayer);
        layerArtist.draw("lake", 2, lakeLayer);

        // Lakes
        lakeLayer = AddLakesLayer.LARGE.apply(random.nextLong(), lakeLayer);
        layerArtist.draw("lake", 3, lakeLayer);
        lakeLayer = ZoomLayer.NORMAL.apply(1002, lakeLayer);
        layerArtist.draw("lake", 4, lakeLayer);
        lakeLayer = AddLakesLayer.SMALL.apply(random.nextLong(), lakeLayer);
        layerArtist.draw("lake", 5, lakeLayer);
        lakeLayer = ZoomLayer.NORMAL.apply(1003, lakeLayer);
        layerArtist.draw("lake", 6, lakeLayer);

        // Biome level features - ocean borders, lakes, island chains, edge biomes, shores
        // Apply lakes back to biomes
        mainLayer = OceanBorderLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 2, mainLayer);
        mainLayer = ZoomLayer.NORMAL.apply(1001, mainLayer);
        layerArtist.draw("biomes", 3, mainLayer);
        mainLayer = ArchipelagoLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 4, mainLayer);
        mainLayer = ReefBorderLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 5, mainLayer);
        mainLayer = ZoomLayer.NORMAL.apply(1002, mainLayer);
        layerArtist.draw("biomes", 6, mainLayer);
        mainLayer = EdgeBiomeLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 7, mainLayer);
        mainLayer = ZoomLayer.NORMAL.apply(1003, mainLayer);
        layerArtist.draw("biomes", 8, mainLayer);
        mainLayer = MergeLakeLayer.INSTANCE.apply(random.nextLong(), mainLayer, lakeLayer);
        layerArtist.draw("biomes", 9, mainLayer);
        mainLayer = ShoreLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 10, mainLayer);

        for (int i = 0; i < 4; i++)
        {
            mainLayer = ZoomLayer.NORMAL.apply(random.nextLong(), mainLayer);
            layerArtist.draw("biomes", 11 + i, mainLayer);
        }

        mainLayer = SmoothLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 15, mainLayer);

        return mainLayer;
    }

    @Overwrite(remap = false)
    public static AreaFactory createOverworldBiomeLayerWithRivers(long seed, Watershed.Context watersheds, IArtist<TypedAreaFactory<Plate>> plateArtist, IArtist<AreaFactory> layerArtist)
    {
        final Random random = new Random(seed);

        AreaFactory riverLayer;

        riverLayer = new MergeRiverLayer(watersheds).apply(seed, createOverworldBiomeLayer(seed, plateArtist, layerArtist));
        /*layerArtist.draw("river", 1, riverLayer);
        riverLayer = ZoomLayer.NORMAL.apply(1002, riverLayer);
        layerArtist.draw("river", 2, riverLayer);
        riverLayer = EdgeBiomeLayer.INSTANCE.apply(random.nextLong(), riverLayer);
        layerArtist.draw("river", 3, riverLayer);
        riverLayer = ZoomLayer.NORMAL.apply(1003, riverLayer);
        layerArtist.draw("river", 4, riverLayer);
        riverLayer = SmoothLayer.INSTANCE.apply(random.nextLong(), riverLayer);
        layerArtist.draw("river", 5, riverLayer);*/

        return riverLayer;
    }

    @Shadow
    public static TypedAreaFactory<Plate> createEarlyPlateLayers(long seed)
    {
        final Random random = new Random(seed);
        TypedAreaFactory<Plate> plateLayer;

        // Tectonic Plates - generate plates and annotate border regions with converging / diverging boundaries
        // This diverges from normal plate generation by using the biased layer.
        // We do this in order to expand the shores of land, so that when we start rivers from ocean plates, they always terminate in an ocean after zooming.
        plateLayer = new PlateGenerationLayer(new Cellular2D(random.nextInt()).spread(0.2f), 40).apply(random.nextLong());
        plateLayer = BiasedLandPlateZoomLayer.INSTANCE.apply(random.nextLong(), plateLayer);

        return plateLayer;
    }

    @Shadow
    public static AreaFactory createOverworldPlateTectonicInfoLayer(long seed)
    {
        final Random random = new Random(seed);

        TypedAreaFactory<Plate> plateLayer;
        AreaFactory mainLayer;

        // Tectonic Plates - generate plates and annotate border regions with converging / diverging boundaries
        plateLayer = new PlateGenerationLayer(new Cellular2D(random.nextInt()).spread(0.2f), 40).apply(random.nextLong());
        plateLayer = new TypedZoomLayer.Fuzzy<Plate>().apply(random.nextLong(), plateLayer);
        mainLayer = PlateBoundaryLayer.INSTANCE.apply(random.nextLong(), plateLayer);

        for (int i = 0; i < 5; i++)
        {
            mainLayer = ZoomLayer.NORMAL.apply(random.nextLong(), mainLayer);
        }

        return mainLayer;
    }

    @Shadow
    public static AreaFactory createOverworldRockLayer(long seed, int layerScale, int rockCount)
    {
        final Random random = new Random(seed);

        AreaFactory layer;

        layer = new RockLayer(rockCount).apply(random.nextLong());

        // The following results were obtained about the number of applications of this layer. (over 10 M samples each time)
        // None => 95.01% of adjacent pairs were equal (which lines up pretty good with theoretical predictions)
        // 1x => 98.49%
        // 2x => 99.42%
        // 3x => 99.54%
        // 4x => 99.55%
        // And thus we only apply once, as it's the best result to reduce adjacent pairs without too much effort / performance cost
        layer = new RandomizeNeighborsLayer(rockCount).apply(random.nextLong(), layer);

        for (int i = 0; i < 2; i++)
        {
            layer = ZoomLayer.NORMAL.apply(random.nextLong(), layer);
            layer = ZoomLayer.NORMAL.apply(random.nextLong(), layer);
            layer = SmoothLayer.INSTANCE.apply(random.nextLong(), layer);
        }

        for (int i = 0; i < layerScale; i++)
        {
            layer = ZoomLayer.NORMAL.apply(random.nextLong(), layer);
        }

        return layer;
    }

    @Shadow
    public static boolean isContinental(int value)
    {
        return value == CONTINENTAL_LOW || value == CONTINENTAL_MID || value == CONTINENTAL_HIGH;
    }

    @Overwrite(remap = false)
    public static boolean hasShore(int value)
    {
        return value != LOW_CANYONS && value != CANYONS && value != OCEANIC_MOUNTAINS && value != VOLCANIC_OCEANIC_MOUNTAINS && value != MANGROVES;
    }

    @Overwrite(remap = false)
    public static int shoreFor(int value)
    {
        if (value == ALPINE_MOUNTAINS)
        {
            return ALPINE_HIGHLANDS;
        }
        if (value == ALPINE_HIGHLANDS)
        {
            return GRAVEL_SHORE;
        }
        if (value == OLD_MOUNTAINS || value == PLATEAU || value == PUY_MOUNTAINS || value == CALDERAS)
        {
            return GRAVEL_SHORE;
        }
        if (value == MOUNTAINS)
        {
            return OCEANIC_MOUNTAINS;
        }
        if (value == VOLCANIC_MOUNTAINS)
        {
            return VOLCANIC_OCEANIC_MOUNTAINS;
        }
        if (value == LOWLANDS || value == WETLANDS || value == MARSHES || value == SWAMPS)
        {
            return MANGROVES;
        }
        return SHORE;
    }

    @Shadow
    public static boolean hasLake(int value)
    {
        return !isOcean(value) && value != BADLANDS;
    }

    @Overwrite(remap = false)
    public static int lakeFor(int value)
    {
        if (value == ALPINE_MOUNTAINS)
        {
            return VOLCANIC_MOUNTAINS;
        }
        if (value == ALPINE_HIGHLANDS)
        {
            return CALDERAS;
        }
        if (value == THERMAL_CANYONS)
        {
            return THERMAL_CANYONS;
        }
        if (value == ROLLING_HIGHLANDS)
        {
            return MESA_PLATEAU;
        }
        if (value == MOUNTAINS)
        {
            return MOUNTAIN_LAKE;
        }
        if (value == VOLCANIC_MOUNTAINS)
        {
            return VOLCANIC_MOUNTAIN_LAKE;
        }
        if (value == OLD_MOUNTAINS)
        {
            return OLD_MOUNTAIN_LAKE;
        }
        if (value == OCEANIC_MOUNTAINS)
        {
            return OCEANIC_MOUNTAIN_LAKE;
        }
        if (value == VOLCANIC_OCEANIC_MOUNTAINS)
        {
            return VOLCANIC_OCEANIC_MOUNTAIN_LAKE;
        }
        if (value == PLATEAU)
        {
            return PLATEAU_LAKE;
        }
        if (value == ROLLING_HILLS)
        {
            return PUY_MOUNTAINS;
        }
        return LAKE;
    }

    @Shadow
    public static boolean hasRiver(int value)
    {
        return !isOcean(value) && !isLake(value);
    }

    @Overwrite(remap = false)
    public static int riverFor(int value)
    {
        if (value == ALPINE_MOUNTAINS)
        {
            return ALPINE_MOUNTAIN_RIVER;
        }
        if (value == ALPINE_HIGHLANDS)
        {
            return RIVER;
        }
        if (value == MOUNTAINS)
        {
            return MOUNTAIN_RIVER;
        }
        if (value == VOLCANIC_MOUNTAINS)
        {
            return VOLCANIC_MOUNTAIN_RIVER;
        }
        if (value == OLD_MOUNTAINS)
        {
            return OLD_MOUNTAIN_RIVER;
        }
        if (value == OCEANIC_MOUNTAINS)
        {
            return OCEANIC_MOUNTAIN_RIVER;
        }
        if (value == VOLCANIC_OCEANIC_MOUNTAINS)
        {
            return VOLCANIC_OCEANIC_MOUNTAIN_RIVER;
        }
        return RIVER;
    }

    @Shadow
    public static boolean isOcean(int value)
    {
        return value == OCEAN || value == DEEP_OCEAN || value == DEEP_OCEAN_TRENCH || value == OCEAN_REEF;
    }

    @Shadow
    public static boolean isOceanOrMarker(int value)
    {
        return isOcean(value) || value == OCEAN_OCEAN_CONVERGING_MARKER || value == OCEAN_OCEAN_DIVERGING_MARKER || value == OCEAN_REEF_MARKER;
    }

    @Overwrite(remap = false)
    public static boolean isLake(int value)
    {
        return value == LAKE || value == OCEANIC_MOUNTAIN_LAKE || value == OLD_MOUNTAIN_LAKE || value == MOUNTAIN_LAKE || value == VOLCANIC_OCEANIC_MOUNTAIN_LAKE || value == VOLCANIC_MOUNTAIN_LAKE || value == PLATEAU_LAKE || value == CALDERAS;
    }

    @Overwrite(remap = false)
    public static boolean isRiver(int value)
    {
        return value == RIVER || value == OCEANIC_MOUNTAIN_RIVER || value == OLD_MOUNTAIN_RIVER || value == MOUNTAIN_RIVER || value == VOLCANIC_OCEANIC_MOUNTAIN_RIVER || value == VOLCANIC_MOUNTAIN_RIVER || value == CANYON_RIVER || value == ALPINE_MOUNTAIN_RIVER;
    }

    @Overwrite(remap = false)
    public static boolean isMountains(int value)
    {
        return value == MOUNTAINS || value == OCEANIC_MOUNTAINS || value == OLD_MOUNTAINS || value == VOLCANIC_MOUNTAINS || value == VOLCANIC_OCEANIC_MOUNTAINS || value == ALPINE_MOUNTAINS || value == ALPINE_HIGHLANDS || value == THERMAL_CANYONS || value == MESA_PLATEAU;
    }

    @Overwrite(remap = false)
    public static boolean isLow(int value)
    {
        return value == TFCLayers.PLAINS || value == TFCLayers.HILLS || value == TFCLayers.LOW_CANYONS || value == TFCLayers.LOWLANDS || value == GRASSLANDS || value == WETLANDS || value == MARSHES || value == SWAMPS;
    }

    @Shadow
    public static int register()
    {
        return register(() -> null);
    }

    @Shadow
    public static int register(Supplier<BiomeExtension> variants)
    {
        final int index = BIOME_LAYER_INDEX.getAndIncrement();
        if (index >= BIOME_LAYERS.length)
        {
            throw new IllegalStateException("Tried to register layer id " + index + " but only had space for " + BIOME_LAYERS.length + " layers");
        }
        BIOME_LAYERS[index] = Helpers.BOOTSTRAP_ENVIRONMENT ? null : variants.get();
        return index;
    }
}
