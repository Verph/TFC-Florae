package tfcflorae.mixin.world.region;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.region.ChooseBiomes;
import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

@Mixin(ChooseBiomes.class)
public class ChooseBiomesMixin
{
    @Unique private static TFCLayers staticBiomes = new TFCLayers();

    @Unique private static final int GRASSLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGrasslands();
    @Unique private static final int WETLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticWetlands();
    @Unique private static final int MARSHES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMarshes();
    @Unique private static final int SWAMPS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSwamps();
    @Unique private static final int PUY_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPuyMountains();
    @Unique private static final int BRYCE_CANYONS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBryceCanyons();
    @Unique private static final int MESA_HILLS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMesaHills();
    @Unique private static final int BADLANDS_PLATEAU = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBadlandsPlateau();
    @Unique private static final int ALPINE_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineMountains();
    @Unique private static final int ALPINE_HIGHLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineHighlands();
    @Unique private static final int ROLLING_HIGHLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRollingHighlands();
    @Unique private static final int THERMAL_CANYONS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticThermalCanyons();
    @Unique private static final int MESA_PLATEAU = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMesaPlateau();
    @Unique private static final int PEAT_BOG = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPeatBog();
    @Unique private static final int STEPPES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSteppes();
    @Unique private static final int SHRUBLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShrublands();
    @Unique private static final int MOORLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMoorlands();
    @Unique private static final int MISTY_PEAKS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMistyPeaks();
    @Unique private static final int VINICUNCA_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticVinicuncaMountains();
    @Unique private static final int SAWTOOTH_CLIFFS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSawtoothCliffs();
    //@Unique private static final int TABLELANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticTablelands();
    @Unique private static final int PITLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPitlands();
    @Unique private static final int FRACTURED_MISTY_PEAKS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticFracturedMistyPeaks();

    @Unique private static final int PELAGIC_ZONE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPelagicZone();
    @Unique private static final int SEAMOUNTS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSeamounts();
    @Unique private static final int GUYOTS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGuyots();

    @Shadow @Mutable @Final private static final int[] MOUNTAIN_ALTITUDE_BIOMES = {MOUNTAINS, MOUNTAINS, MOUNTAINS, OLD_MOUNTAINS, OLD_MOUNTAINS, PLATEAU, MESA_PLATEAU, ALPINE_MOUNTAINS, ALPINE_HIGHLANDS, ROLLING_HIGHLANDS, THERMAL_CANYONS, THERMAL_CANYONS, MISTY_PEAKS, VINICUNCA_MOUNTAINS, FRACTURED_MISTY_PEAKS, PITLANDS, SAWTOOTH_CLIFFS};
    @Shadow @Mutable @Final private static final int[] OCEANIC_MOUNTAIN_ALTITUDE_BIOMES = {VOLCANIC_MOUNTAINS, VOLCANIC_OCEANIC_MOUNTAINS, VOLCANIC_OCEANIC_MOUNTAINS, OCEANIC_MOUNTAINS, OCEANIC_MOUNTAINS, ROLLING_HILLS, ROLLING_HIGHLANDS, MISTY_PEAKS, ALPINE_HIGHLANDS, FRACTURED_MISTY_PEAKS};
    @Shadow @Mutable @Final private static final int[] HIGH_ALTITUDE_BIOMES = {HILLS, ROLLING_HILLS, ROLLING_HILLS, INVERTED_BADLANDS, BADLANDS, PLATEAU, PLATEAU, OLD_MOUNTAINS, OLD_MOUNTAINS, PUY_MOUNTAINS, BRYCE_CANYONS, MESA_HILLS, BADLANDS_PLATEAU, MESA_PLATEAU, ROLLING_HIGHLANDS, ALPINE_MOUNTAINS, ALPINE_HIGHLANDS, THERMAL_CANYONS, SHRUBLANDS, MOORLANDS, MISTY_PEAKS, VINICUNCA_MOUNTAINS, FRACTURED_MISTY_PEAKS/*, TABLELANDS*/, SAWTOOTH_CLIFFS};
    @Shadow @Mutable @Final private static final int[] MID_ALTITUDE_BIOMES = {PLAINS, HILLS, ROLLING_HILLS, INVERTED_BADLANDS, BADLANDS, PLATEAU, CANYONS, LOW_CANYONS, LOWLANDS, PUY_MOUNTAINS, BRYCE_CANYONS, MESA_HILLS, BADLANDS_PLATEAU, MESA_PLATEAU, ROLLING_HIGHLANDS, GRASSLANDS, WETLANDS, MARSHES, SWAMPS, PUY_MOUNTAINS, MESA_HILLS, ALPINE_HIGHLANDS, STEPPES, SHRUBLANDS, SHRUBLANDS, MOORLANDS/*, TABLELANDS*/, PITLANDS};
    @Shadow @Mutable @Final private static final int[] LOW_ALTITUDE_BIOMES = {PLAINS, PLAINS, HILLS, HILLS, ROLLING_HILLS, LOW_CANYONS, LOWLANDS, GRASSLANDS, GRASSLANDS, WETLANDS, WETLANDS, MARSHES, MARSHES, SWAMPS, SWAMPS, ROLLING_HIGHLANDS, PEAT_BOG, STEPPES, STEPPES, MOORLANDS, THERMAL_CANYONS};
    @Shadow @Mutable @Final private static final int[] ISLAND_BIOMES = {PLAINS, HILLS, ROLLING_HILLS, VOLCANIC_OCEANIC_MOUNTAINS, VOLCANIC_OCEANIC_MOUNTAINS};
    @Shadow @Mutable @Final private static final int[] MID_DEPTH_OCEAN_BIOMES = {PELAGIC_ZONE, SEAMOUNTS, GUYOTS, DEEP_OCEAN, DEEP_OCEAN, OCEAN, OCEAN, OCEAN, OCEAN, OCEAN_REEF, OCEAN_REEF, OCEAN_REEF, OCEAN_REEF, OCEAN_REEF, OCEAN_REEF, OCEAN_REEF, OCEAN_REEF};
}
