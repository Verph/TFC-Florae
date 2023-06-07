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

import tfcflorae.interfaces.*;
import tfcflorae.world.layer.*;
import tfcflorae.world.layer.EdgeBiomeLayer;
import tfcflorae.world.layer.PlateBiomeLayer;
import tfcflorae.world.layer.river.EdgeRiverbankLayer;
import tfcflorae.world.layer.river.MergeRiverBanksLayer;
import tfcflorae.world.layer.river.TFCFMergeRiverLayer;
import tfcflorae.world.layer.river.Watershed;
import tfcflorae.world.layer.river.WatershedBank;

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

    @Unique private static final int GRASSLANDS;
    @Unique private static final int WETLANDS;
    @Unique private static final int MARSHES;
    @Unique private static final int SWAMPS;
    @Unique private static final int MANGROVES;
    @Unique private static final int PUY_MOUNTAINS;
    @Unique private static final int BRYCE_CANYONS;
    @Unique private static final int MESA_HILLS;
    @Unique private static final int BADLANDS_PLATEAU;
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
    @Unique private static final int RIVERBANK;
    @Unique private static final int RIVER_EDGE;
    @Unique private static final int CHASMS;
    @Unique private static final int PLATEAU_CLIFFS;
    @Unique private static final int STEPPES;
    @Unique private static final int SHRUBLANDS;
    @Unique private static final int MOORLANDS;
    @Unique private static final int MISTY_PEAKS;
    @Unique private static final int COASTAL_CLIFFS;
    @Unique private static final int SHORE_DUNES;
    @Unique private static final int VINICUNCA_MOUNTAINS;
    @Unique private static final int NEAR_SHORE;
    @Unique private static final int LAKE_SHORE;
    @Unique private static final int PELAGIC_ZONE;
    @Unique private static final int SEAMOUNTS;
    @Unique private static final int GUYOTS;
    @Unique private static final int ATOLL;

    /**
     * These IDs are used as markers for biomes. They should all be removed by the time the biome layers are finished
     */
    @Shadow public static final int OCEAN_OCEAN_CONVERGING_MARKER;
    @Shadow public static final int OCEAN_OCEAN_DIVERGING_MARKER;
    @Shadow public static final int LAKE_MARKER;
    @Shadow public static final int NULL_MARKER;
    @Shadow public static final int INLAND_MARKER;
    @Shadow public static final int OCEAN_REEF_MARKER;
    @Unique private static final int CALDERA_MARKER;
    @Unique private static final int PUY_MOUNTAINS_MARKER;
    @Unique private static final int MESA_PLATEAU_MARKER;
    @Unique private static final int PELAGIC_ZONE_MARKER;
    @Unique private static final int SEAMOUNTS_MARKER;
    @Unique private static final int GUYOTS_MARKER;
    @Unique private static final int ATOLL_MARKER;

    @Shadow @Mutable @Final private static final BiomeExtension[] BIOME_LAYERS = new BiomeExtension[128];
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
        BADLANDS_PLATEAU = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticBadlandsPlateau);
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
        RIVERBANK = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticRiverbank);
        RIVER_EDGE = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticRiverEdge);
        CHASMS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticChasms);
        PLATEAU_CLIFFS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticPlateauCliffs);
        STEPPES = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticSteppes);
        SHRUBLANDS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticShrublands);
        MOORLANDS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticMoorlands);
        MISTY_PEAKS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticMistyPeaks);
        COASTAL_CLIFFS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticCoastalCliffs);
        SHORE_DUNES = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticShoreDunes);
        VINICUNCA_MOUNTAINS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticVinicuncaMountains);
        NEAR_SHORE = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticNearShore);
        LAKE_SHORE = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticLakeShore);
        PELAGIC_ZONE = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticPelagicZone);
        SEAMOUNTS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticSeamounts);
        GUYOTS = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticGuyots);
        ATOLL = register(((TFCBiomesMixinInterface) (Object) staticBiomes)::getStaticAtoll);

        OCEAN_OCEAN_CONVERGING_MARKER = register();
        OCEAN_OCEAN_DIVERGING_MARKER = register();
        LAKE_MARKER = register();
        NULL_MARKER = register();
        INLAND_MARKER = register();
        OCEAN_REEF_MARKER = register();
        CALDERA_MARKER = register();
        PUY_MOUNTAINS_MARKER = register();
        MESA_PLATEAU_MARKER = register();
        PELAGIC_ZONE_MARKER = register();
        SEAMOUNTS_MARKER = register();
        GUYOTS_MARKER = register();
        ATOLL_MARKER = register();
    }

    @Unique @Override
    public int getStaticCalderaMarker()
    {
        return CALDERA_MARKER;
    }

    @Unique @Override
    public int getStaticPuyMountainsMarker()
    {
        return PUY_MOUNTAINS_MARKER;
    }

    @Unique @Override
    public int getStaticMesaPlateauMarker()
    {
        return MESA_PLATEAU_MARKER;
    }

    @Unique @Override
    public int getStaticPelagicZoneMarker()
    {
        return PELAGIC_ZONE_MARKER;
    }

    @Unique @Override
    public int getStaticSeamountsMarker()
    {
        return SEAMOUNTS_MARKER;
    }

    @Unique @Override
    public int getStaticGuyotsMarker()
    {
        return GUYOTS_MARKER;
    }

    @Unique @Override
    public int getStaticAtollMarker()
    {
        return ATOLL_MARKER;
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
    public int getStaticBadlandsPlateau()
    {
        return BADLANDS_PLATEAU;
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

    @Unique @Override
    public int getStaticRiverbank()
    {
        return RIVERBANK;
    }

    @Unique @Override
    public int getStaticRiverEdge()
    {
        return RIVER_EDGE;
    }

    @Unique @Override
    public int getStaticChasms()
    {
        return CHASMS;
    }

    @Unique @Override
    public int getStaticPlateauCliffs()
    {
        return PLATEAU_CLIFFS;
    }

    @Unique @Override
    public int getStaticSteppes()
    {
        return STEPPES;
    }

    @Unique @Override
    public int getStaticMoorlands()
    {
        return SHRUBLANDS;
    }

    @Unique @Override
    public int getStaticShrublands()
    {
        return MOORLANDS;
    }

    @Unique @Override
    public int getStaticMistyPeaks()
    {
        return MISTY_PEAKS;
    }

    @Unique @Override
    public int getStaticCoastalCliffs()
    {
        return COASTAL_CLIFFS;
    }

    @Unique @Override
    public int getStaticShoreDunes()
    {
        return SHORE_DUNES;
    }

    @Unique @Override
    public int getStaticVinicuncaMountains()
    {
        return VINICUNCA_MOUNTAINS;
    }

    @Unique @Override
    public int getStaticNearShore()
    {
        return NEAR_SHORE;
    }

    @Unique @Override
    public int getStaticLakeShore()
    {
        return LAKE_SHORE;
    }

    @Unique @Override
    public int getStaticPelagicZone()
    {
        return PELAGIC_ZONE;
    }

    @Unique @Override
    public int getStaticSeamounts()
    {
        return SEAMOUNTS;
    }

    @Unique @Override
    public int getStaticGuyots()
    {
        return GUYOTS;
    }

    @Unique @Override
    public int getStaticAtoll()
    {
        return ATOLL;
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

        TypedAreaFactory<Plate> plateLayer, riverLayer;
        AreaFactory mainLayer, lakeLayer, calderaLayer, puyMountainsLayer, mesaPlateauLayer, pelagicZoneLayer, seamountsLayer, guyotsLayer, atollLayer;

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

        // Initial Biomes -> Caldera Setup
        calderaLayer = InlandLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("caldera", 1, calderaLayer);
        calderaLayer = ZoomLayer.NORMAL.apply(1001, calderaLayer);
        layerArtist.draw("caldera", 2, calderaLayer);

        // Calderas
        calderaLayer = AddCalderasLayer.LARGE.apply(random.nextLong(), calderaLayer);
        layerArtist.draw("caldera", 3, calderaLayer);
        calderaLayer = ZoomLayer.NORMAL.apply(1002, calderaLayer);
        layerArtist.draw("caldera", 4, calderaLayer);
        calderaLayer = AddCalderasLayer.SMALL.apply(random.nextLong(), calderaLayer);
        layerArtist.draw("caldera", 5, calderaLayer);
        calderaLayer = ZoomLayer.NORMAL.apply(1003, calderaLayer);
        layerArtist.draw("caldera", 6, calderaLayer);

        // Initial Biomes -> Puy Mountain Setup
        puyMountainsLayer = InlandLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("puy_mountains", 1, puyMountainsLayer);
        puyMountainsLayer = ZoomLayer.NORMAL.apply(1001, puyMountainsLayer);
        layerArtist.draw("puy_mountains", 2, puyMountainsLayer);

        // Puy Mountains
        puyMountainsLayer = AddPuyMountainsLayer.LARGE.apply(random.nextLong(), puyMountainsLayer);
        layerArtist.draw("puy_mountains", 3, puyMountainsLayer);
        puyMountainsLayer = ZoomLayer.NORMAL.apply(1002, puyMountainsLayer);
        layerArtist.draw("puy_mountains", 4, puyMountainsLayer);
        puyMountainsLayer = AddPuyMountainsLayer.SMALL.apply(random.nextLong(), puyMountainsLayer);
        layerArtist.draw("puy_mountains", 5, puyMountainsLayer);
        puyMountainsLayer = ZoomLayer.NORMAL.apply(1003, puyMountainsLayer);
        layerArtist.draw("puy_mountains", 6, puyMountainsLayer);

        // Initial Biomes -> Mesa Plateau Setup
        mesaPlateauLayer = InlandLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("mesa_plateau", 1, mesaPlateauLayer);
        mesaPlateauLayer = ZoomLayer.NORMAL.apply(1001, mesaPlateauLayer);
        layerArtist.draw("mesa_plateau", 2, mesaPlateauLayer);

        // Mesa Plateau
        mesaPlateauLayer = AddMesaPlateauLayer.LARGE.apply(random.nextLong(), mesaPlateauLayer);
        layerArtist.draw("mesa_plateau", 3, mesaPlateauLayer);
        mesaPlateauLayer = ZoomLayer.NORMAL.apply(1002, mesaPlateauLayer);
        layerArtist.draw("mesa_plateau", 4, mesaPlateauLayer);
        mesaPlateauLayer = AddMesaPlateauLayer.SMALL.apply(random.nextLong(), mesaPlateauLayer);
        layerArtist.draw("mesa_plateau", 5, mesaPlateauLayer);
        mesaPlateauLayer = ZoomLayer.NORMAL.apply(1003, mesaPlateauLayer);
        layerArtist.draw("mesa_plateau", 6, mesaPlateauLayer);

        // Initial Biomes -> Pelagic Zones Setup
        pelagicZoneLayer = InlandLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("pelagic_zone", 1, pelagicZoneLayer);
        pelagicZoneLayer = ZoomLayer.NORMAL.apply(1001, pelagicZoneLayer);
        layerArtist.draw("pelagic_zone", 2, pelagicZoneLayer);

        // Pelagic Zones
        pelagicZoneLayer = AddPelagicZonesLayer.LARGE.apply(random.nextLong(), pelagicZoneLayer);
        layerArtist.draw("pelagic_zone", 3, pelagicZoneLayer);
        pelagicZoneLayer = ZoomLayer.NORMAL.apply(1002, pelagicZoneLayer);
        layerArtist.draw("pelagic_zone", 4, pelagicZoneLayer);
        pelagicZoneLayer = AddPelagicZonesLayer.SMALL.apply(random.nextLong(), pelagicZoneLayer);
        layerArtist.draw("pelagic_zone", 5, pelagicZoneLayer);
        pelagicZoneLayer = ZoomLayer.NORMAL.apply(1003, pelagicZoneLayer);
        layerArtist.draw("pelagic_zone", 6, pelagicZoneLayer);

        // Initial Biomes -> Seamounts Setup
        seamountsLayer = InlandLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("seamounts", 1, seamountsLayer);
        seamountsLayer = ZoomLayer.NORMAL.apply(1001, seamountsLayer);
        layerArtist.draw("seamounts", 2, seamountsLayer);

        // Seamounts
        seamountsLayer = AddSeamountsLayer.LARGE.apply(random.nextLong(), seamountsLayer);
        layerArtist.draw("seamounts", 3, seamountsLayer);
        seamountsLayer = ZoomLayer.NORMAL.apply(1002, seamountsLayer);
        layerArtist.draw("seamounts", 4, seamountsLayer);
        seamountsLayer = AddSeamountsLayer.SMALL.apply(random.nextLong(), seamountsLayer);
        layerArtist.draw("seamounts", 5, seamountsLayer);
        seamountsLayer = ZoomLayer.NORMAL.apply(1003, seamountsLayer);
        layerArtist.draw("seamounts", 6, seamountsLayer);

        // Initial Biomes -> Guyots Setup
        guyotsLayer = InlandLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("guyots", 1, guyotsLayer);
        guyotsLayer = ZoomLayer.NORMAL.apply(1001, guyotsLayer);
        layerArtist.draw("guyots", 2, guyotsLayer);

        // Guyots
        guyotsLayer = AddGuyotsLayer.LARGE.apply(random.nextLong(), guyotsLayer);
        layerArtist.draw("guyots", 3, guyotsLayer);
        guyotsLayer = ZoomLayer.NORMAL.apply(1002, guyotsLayer);
        layerArtist.draw("guyots", 4, guyotsLayer);
        guyotsLayer = AddGuyotsLayer.SMALL.apply(random.nextLong(), guyotsLayer);
        layerArtist.draw("guyots", 5, guyotsLayer);
        guyotsLayer = ZoomLayer.NORMAL.apply(1003, guyotsLayer);
        layerArtist.draw("guyots", 6, guyotsLayer);

        // Initial Biomes -> Guyots Setup
        atollLayer = InlandLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("atoll", 1, atollLayer);
        atollLayer = ZoomLayer.NORMAL.apply(1001, atollLayer);
        layerArtist.draw("atoll", 2, atollLayer);

        // Guyots
        atollLayer = AddAtollLayer.LARGE.apply(random.nextLong(), atollLayer);
        layerArtist.draw("atoll", 3, atollLayer);
        atollLayer = ZoomLayer.NORMAL.apply(1002, atollLayer);
        layerArtist.draw("atoll", 4, atollLayer);
        atollLayer = AddAtollLayer.SMALL.apply(random.nextLong(), atollLayer);
        layerArtist.draw("atoll", 5, atollLayer);
        atollLayer = ZoomLayer.NORMAL.apply(1003, atollLayer);
        layerArtist.draw("atoll", 6, atollLayer);

        // Biome level features - ocean borders, lakes, island chains, edge biomes, shores
        // Apply lakes back to biomes
        mainLayer = MergePelagicZonesLayer.INSTANCE.apply(random.nextLong(), mainLayer, seamountsLayer);
        layerArtist.draw("biomes", 2, mainLayer);
        mainLayer = MergeSeamountsLayer.INSTANCE.apply(random.nextLong(), mainLayer, seamountsLayer);
        layerArtist.draw("biomes", 3, mainLayer);
        mainLayer = MergeGuyotsLayer.INSTANCE.apply(random.nextLong(), mainLayer, guyotsLayer);
        layerArtist.draw("biomes", 4, mainLayer);
        mainLayer = OceanBorderLayerTFCF.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 5, mainLayer);
        mainLayer = ZoomLayer.NORMAL.apply(1001, mainLayer);
        layerArtist.draw("biomes", 6, mainLayer);
        mainLayer = ArchipelagoLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 7, mainLayer);
        mainLayer = ReefBorderLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 8, mainLayer);
        mainLayer = ZoomLayer.NORMAL.apply(1002, mainLayer);
        layerArtist.draw("biomes", 9, mainLayer);
        mainLayer = EdgeBiomeLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 10, mainLayer);
        mainLayer = ZoomLayer.NORMAL.apply(1003, mainLayer);
        layerArtist.draw("biomes", 11, mainLayer);
        mainLayer = MergeLakeLayer.INSTANCE.apply(random.nextLong(), mainLayer, lakeLayer);
        layerArtist.draw("biomes", 12, mainLayer);
        mainLayer = MergeCalderaLayer.INSTANCE.apply(random.nextLong(), mainLayer, calderaLayer);
        layerArtist.draw("biomes", 13, mainLayer);
        mainLayer = MergePuyMountainsLayer.INSTANCE.apply(random.nextLong(), mainLayer, puyMountainsLayer);
        layerArtist.draw("biomes", 14, mainLayer);
        mainLayer = MergeMesaPlateauLayer.INSTANCE.apply(random.nextLong(), mainLayer, mesaPlateauLayer);
        layerArtist.draw("biomes", 15, mainLayer);
        mainLayer = MergeAtollLayer.INSTANCE.apply(random.nextLong(), mainLayer, atollLayer);
        layerArtist.draw("biomes", 16, mainLayer);

        mainLayer = ShoreLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 17, mainLayer);
        mainLayer = LakeEdgeLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 18, mainLayer);
        mainLayer = NearShoreLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 19, mainLayer);
        mainLayer = PlateauCliffsLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 20, mainLayer);

        for (int i = 0; i < 4; i++)
        {
            mainLayer = ZoomLayer.NORMAL.apply(random.nextLong(), mainLayer);
            layerArtist.draw("biomes", 21 + i, mainLayer);
        }

        mainLayer = SmoothLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 25, mainLayer);
        mainLayer = ExtraDunesShoreLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 26, mainLayer);

        ShoreDunes.Context shoreDunesContext = new ShoreDunes.Context(createEarlyPlateLayers(seed), seed + random.nextLong(), 0.6f, 0.8f, 14, 0.2f);
        mainLayer = new MergeShoreDunesLayer(shoreDunesContext).apply(seed + random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 27, mainLayer);

        CoastalCliffs.Context cliffContext = new CoastalCliffs.Context(createEarlyPlateLayers(seed), seed + 2, 0.5f, 0.8f, 14, 0.2f);
        mainLayer = new MergeCoastalCliffsLayer(cliffContext).apply(seed + 2, mainLayer);
        layerArtist.draw("biomes", 28, mainLayer);

        for (int i = 0; i < 4; i++)
        {
            mainLayer = ExtraDunesShoreLayer.INSTANCE.apply(seed + random.nextLong(), mainLayer);
            layerArtist.draw("biomes", 29 + i, mainLayer);
            mainLayer = EdgeDunesShoreLayer.INSTANCE.apply(seed + random.nextLong(), mainLayer);
            layerArtist.draw("biomes", 33 + i, mainLayer);
        }

        riverLayer = createEarlyPlateLayers(seed);
        Watershed.Context riverContext = new Watershed.Context(riverLayer, seed, 0.5f, 0.8f, 14, 0.2f);
        WatershedBank.Context riverbankContext = new WatershedBank.Context(riverLayer, seed, 0.5f, 0.8f, 14, 0.2f);
        //WatershedTertiary.Context riverTertiaryContext = new WatershedTertiary.Context(riverLayer, mainLayer, seed, 0.2f, 0.8f, 14, 0.2f);

        mainLayer = new TFCFMergeRiverLayer(riverContext, seed).apply(seed, mainLayer);
        layerArtist.draw("biomes", 38, mainLayer);
        /*mainLayer = new MergeRiverTertiaryLayer(riverTertiaryContext).apply(seed, mainLayer);
        layerArtist.draw("biomes", 34, mainLayer);*/
        mainLayer = new MergeRiverBanksLayer(riverbankContext, riverContext, seed).apply(seed, mainLayer);
        layerArtist.draw("biomes", 39, mainLayer);
        mainLayer = new TFCFMergeRiverLayer(riverContext, seed).apply(seed, mainLayer);
        layerArtist.draw("biomes", 40, mainLayer);
        mainLayer = EdgeRiverbankLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 41, mainLayer);

        Chasm.Context chasmContext = new Chasm.Context(createEarlyPlateLayers(seed), seed + 5, 0.58F, 0.7F, 16, 0.9F);
        mainLayer = new MergeChasmsLayer(chasmContext).apply(seed + 5, mainLayer);
        layerArtist.draw("biomes", 42, mainLayer);

        mainLayer = PlateauCliffsLayer.INSTANCE.apply(random.nextLong(), mainLayer);
        layerArtist.draw("biomes", 43, mainLayer);

        return mainLayer;
    }

    /*@Overwrite(remap = false)
    public static AreaFactory createOverworldBiomeLayerWithRivers(long seed, Watershed.Context watersheds, IArtist<TypedAreaFactory<Plate>> plateArtist, IArtist<AreaFactory> layerArtist)
    {
        return new MergeRiverLayer(watersheds).apply(seed, createOverworldBiomeLayer(seed, plateArtist, layerArtist));
    }*/

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
        if ((value == PLATEAU || value == PUY_MOUNTAINS || value == CALDERAS || isMountains(value)) && (value != MOUNTAINS || value != VOLCANIC_MOUNTAINS || value != ALPINE_MOUNTAINS))
        {
            return COASTAL_CLIFFS;
        }
        if (value == COASTAL_CLIFFS)
        {
            return GRAVEL_SHORE;
        }
        if (value == ALPINE_MOUNTAINS)
        {
            return ALPINE_HIGHLANDS;
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
        return !isOcean(value) && value != BADLANDS && value != CALDERAS && value != NEAR_SHORE && value != ATOLL;
    }

    @Overwrite(remap = false)
    public static int lakeFor(int value)
    {
        if (value == ALPINE_MOUNTAINS)
        {
            return VOLCANIC_MOUNTAIN_LAKE;
        }
        if (value == THERMAL_CANYONS)
        {
            return THERMAL_CANYONS;
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
        return LAKE;
    }

    @Shadow
    public static boolean hasRiver(int value)
    {
        return !isOcean(value) && !isLake(value) && value != RIVERBANK && value != RIVER_EDGE && value != NEAR_SHORE && value != ATOLL;
    }

    @Overwrite(remap = false)
    public static int riverFor(int value)
    {
        if (value == CALDERAS || value == CANYON_RIVER)
        {
            return CANYON_RIVER;
        }
        if (value == ALPINE_MOUNTAINS || value == ALPINE_MOUNTAIN_RIVER)
        {
            return ALPINE_MOUNTAIN_RIVER;
        }
        if (value == MOUNTAINS || value == ALPINE_HIGHLANDS || value == MOUNTAIN_RIVER)
        {
            return MOUNTAIN_RIVER;
        }
        if (value == VOLCANIC_MOUNTAINS || value == VOLCANIC_MOUNTAIN_RIVER)
        {
            return VOLCANIC_MOUNTAIN_RIVER;
        }
        if (value == OLD_MOUNTAINS || value == OLD_MOUNTAIN_RIVER)
        {
            return OLD_MOUNTAIN_RIVER;
        }
        if (value == OCEANIC_MOUNTAINS || value == OCEANIC_MOUNTAIN_RIVER)
        {
            return OCEANIC_MOUNTAIN_RIVER;
        }
        if (value == VOLCANIC_OCEANIC_MOUNTAINS || value == VOLCANIC_OCEANIC_MOUNTAIN_RIVER)
        {
            return VOLCANIC_OCEANIC_MOUNTAIN_RIVER;
        }
        return RIVER;
    }

    @Shadow
    public static boolean isOcean(int value)
    {
        return value == OCEAN || value == DEEP_OCEAN || value == DEEP_OCEAN_TRENCH || value == OCEAN_REEF || value == PELAGIC_ZONE || value == SEAMOUNTS || value == GUYOTS || value == ATOLL;
    }

    @Shadow
    public static boolean isOceanOrMarker(int value)
    {
        return isOcean(value) || value == OCEAN_OCEAN_CONVERGING_MARKER || value == OCEAN_OCEAN_DIVERGING_MARKER || value == OCEAN_REEF_MARKER || value == PELAGIC_ZONE_MARKER || value == SEAMOUNTS_MARKER || value == GUYOTS_MARKER;
    }

    @Overwrite(remap = false)
    public static boolean isLake(int value)
    {
        return value == LAKE || value == OCEANIC_MOUNTAIN_LAKE || value == OLD_MOUNTAIN_LAKE || value == MOUNTAIN_LAKE || value == VOLCANIC_OCEANIC_MOUNTAIN_LAKE || value == VOLCANIC_MOUNTAIN_LAKE || value == PLATEAU_LAKE;
    }

    @Overwrite(remap = false)
    public static boolean isRiver(int value)
    {
        return value == RIVER || value == OCEANIC_MOUNTAIN_RIVER || value == OLD_MOUNTAIN_RIVER || value == MOUNTAIN_RIVER || value == VOLCANIC_OCEANIC_MOUNTAIN_RIVER || value == VOLCANIC_MOUNTAIN_RIVER || value == CANYON_RIVER || value == ALPINE_MOUNTAIN_RIVER;
    }

    @Overwrite(remap = false)
    public static boolean isMountains(int value)
    {
        return value == MOUNTAINS || value == OCEANIC_MOUNTAINS || value == OLD_MOUNTAINS || value == VOLCANIC_MOUNTAINS || value == VOLCANIC_OCEANIC_MOUNTAINS || value == ALPINE_MOUNTAINS || value == ALPINE_HIGHLANDS || value == THERMAL_CANYONS || value == MESA_PLATEAU || value == SHRUBLANDS || value == MISTY_PEAKS || value == VINICUNCA_MOUNTAINS;
    }

    @Overwrite(remap = false)
    public static boolean isLow(int value)
    {
        return value == TFCLayers.PLAINS || value == TFCLayers.HILLS || value == TFCLayers.LOW_CANYONS || value == TFCLayers.LOWLANDS || value == GRASSLANDS || value == WETLANDS || value == MARSHES || value == SWAMPS || value == STEPPES;
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
