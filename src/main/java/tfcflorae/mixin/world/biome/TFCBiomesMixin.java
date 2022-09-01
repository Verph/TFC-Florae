package tfcflorae.mixin.world.biome;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.CommonLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.TFCChunkGenerator;
import net.dries007.tfc.world.biome.BiomeBuilder;
import net.dries007.tfc.world.biome.BiomeExtension;
import net.dries007.tfc.world.biome.BiomeNoise;
import net.dries007.tfc.world.biome.*;
import net.dries007.tfc.world.surface.builder.*;

import tfcflorae.world.surface.builder.*;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.world.biome.BiomeBuilder.builder;

@Mixin(TFCBiomes.class)
public final class TFCBiomesMixin
{
    @Shadow
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, MOD_ID);

    @Shadow
    private static final Map<ResourceKey<Biome>, BiomeExtension> EXTENSIONS = new IdentityHashMap<>();

    // Aquatic biomes
    @Shadow @Mutable @Final
    public static final BiomeExtension OCEAN = register("ocean", builder().heightmap(seed -> BiomeNoise.ocean(seed, -26, -12)).surface(OceanSurfaceBuilder.INSTANCE).aquiferHeightOffset(-24).salty().group(BiomeExtension.Group.OCEAN)); // Ocean biome found near continents.
    @Shadow @Mutable @Final
    public static final BiomeExtension OCEAN_REEF = register("ocean_reef", builder().heightmap(seed -> BiomeNoise.ocean(seed, -16, -8)).surface(OceanSurfaceBuilder.INSTANCE).aquiferHeightOffset(-24).salty().group(BiomeExtension.Group.OCEAN)); // Ocean biome with reefs depending on climate. Could be interpreted as either barrier, fringe, or platform reefs.
    @Shadow @Mutable @Final
    public static final BiomeExtension DEEP_OCEAN = register("deep_ocean", builder().heightmap(seed -> BiomeNoise.ocean(seed, -30, -16)).surface(OceanSurfaceBuilder.INSTANCE).aquiferHeightOffset(-24).group(BiomeExtension.Group.OCEAN).salty()); // Deep ocean biome covering most all oceans.
    @Shadow @Mutable @Final
    public static final BiomeExtension DEEP_OCEAN_TRENCH = register("deep_ocean_trench", builder().heightmap(seed -> BiomeNoise.oceanRidge(seed, -30, -16)).surface(OceanSurfaceBuilder.INSTANCE).aquiferHeightOffset(-24).group(BiomeExtension.Group.OCEAN).salty()); // Deeper ocean with sharp relief carving to create very deep trenches

    // Low biomes
    @Shadow @Mutable @Final
    public static final BiomeExtension PLAINS = register("plains", builder().heightmap(seed -> BiomeNoise.hills(seed, 4, 10)).surface(RoadNetworkBuilder.create(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(ForestSurfaceBuilder.create(NormalSurfaceBuilder.INSTANCE))))).spawnable()); // Very flat, slightly above sea level.
    @Shadow @Mutable @Final
    public static final BiomeExtension HILLS = register("hills", builder().heightmap(seed -> BiomeNoise.hills(seed, -5, 16)).surface(RoadNetworkBuilder.create(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(ForestSurfaceBuilder.create(NormalSurfaceBuilder.INSTANCE))))).spawnable()); // Small hills, slightly above sea level.
    @Shadow @Mutable @Final
    public static final BiomeExtension LOWLANDS = register("lowlands", builder().heightmap(BiomeNoise::lowlands).surface(RoadNetworkBuilder.create(SubSoilSurfaceBuilder.create(ForestSurfaceBuilder.create(LowlandsSurfaceBuilder.INSTANCE)))).aquiferHeightOffset(-16).spawnable()); // Flat, swamp-like, lots of shallow pools below sea level.
    @Shadow @Mutable @Final
    public static final BiomeExtension LOW_CANYONS = register("low_canyons", builder().heightmap(seed -> BiomeNoise.canyons(seed, -8, 21)).surface(RoadNetworkBuilder.create(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(ForestSurfaceBuilder.create(NormalSurfaceBuilder.INSTANCE))))).aquiferHeightOffset(-16).spawnable()); // Sharp, small hills, with lots of water / snaking winding rivers.

    // Mid biomes
    @Shadow @Mutable @Final
    public static final BiomeExtension ROLLING_HILLS = register("rolling_hills", builder().heightmap(seed -> BiomeNoise.hills(seed, -5, 28)).surface(RoadNetworkBuilder.create(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(ForestSurfaceBuilder.create(NormalSurfaceBuilder.INSTANCE))))).spawnable()); // Higher hills, above sea level. Some larger / steeper hills.
    @Shadow @Mutable @Final
    public static final BiomeExtension BADLANDS = register("badlands", builder().heightmap(BiomeNoise::badlands).surface(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(ForestSurfaceBuilder.create(BadlandsSurfaceBuilder.NORMAL)))).spawnable()); // Very high flat area with steep relief carving, similar to vanilla mesas.
    @Shadow @Mutable @Final
    public static final BiomeExtension INVERTED_BADLANDS = register("inverted_badlands", builder().heightmap(BiomeNoise::bryceCanyon).surface(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(BadlandsSurfaceBuilder.INVERTED))).spawnable()); // Inverted badlands: hills with additive ridges, similar to vanilla bryce canyon mesas.
    @Shadow @Mutable @Final
    public static final BiomeExtension PLATEAU = register("plateau", builder().heightmap(seed -> BiomeNoise.hills(seed, 20, 30)).surface(RoadNetworkBuilder.create(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(ForestSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE))))).spawnable()); // Very high area, very flat top.
    @Shadow @Mutable @Final
    public static final BiomeExtension CANYONS = register("canyons", builder().heightmap(seed -> BiomeNoise.canyons(seed, -2, 40)).surface(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(NormalSurfaceBuilder.INSTANCE))).volcanoes(6, 14, 30, 28).spawnable()); // Medium height with snake like ridges, minor volcanic activity

    // High biomes
    @Shadow @Mutable @Final
    public static final BiomeExtension MOUNTAINS = register("mountains", builder().heightmap(seed -> BiomeNoise.mountains(seed, 10, 70)).surface(RoadNetworkBuilder.create(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(ForestSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE))))).spawnable()); // High, picturesque mountains. Pointed peaks, low valleys well above sea level.
    @Shadow @Mutable @Final
    public static final BiomeExtension OLD_MOUNTAINS = register("old_mountains", builder().heightmap(seed -> BiomeNoise.mountains(seed, 16, 40)).surface(RoadNetworkBuilder.create(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(ForestSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE))))).spawnable()); // Rounded top mountains, very large hills.
    @Shadow @Mutable @Final
    public static final BiomeExtension OCEANIC_MOUNTAINS = register("oceanic_mountains", builder().heightmap(seed -> BiomeNoise.mountains(seed, -16, 60)).surface(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE))).aquiferHeightOffset(-8).salty().spawnable()); // Mountains with high areas, and low, below sea level valleys. Water is salt water here.
    @Shadow @Mutable @Final
    public static final BiomeExtension VOLCANIC_MOUNTAINS = register("volcanic_mountains", builder().heightmap(seed -> BiomeNoise.mountains(seed, 10, 60)).surface(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE))).volcanoes(4, 25, 50, 40)); // Volcanic mountains - slightly smaller, but with plentiful tall volcanoes
    @Shadow @Mutable @Final
    public static final BiomeExtension VOLCANIC_OCEANIC_MOUNTAINS = register("volcanic_oceanic_mountains", builder().heightmap(seed -> BiomeNoise.mountains(seed, -24, 50)).surface(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE))).aquiferHeightOffset(-8).salty().volcanoes(2, -12, 50, 20)); // Volcanic oceanic islands. Slightly smaller and lower but with very plentiful volcanoes

    // Shores
    @Shadow @Mutable @Final
    public static final BiomeExtension SHORE = register("shore", builder().heightmap(BiomeNoise::shore).surface(ShoreSurfaceBuilder.INSTANCE).aquiferHeightOffset(-16).group(BiomeExtension.Group.OCEAN).salty()); // Standard shore / beach. Material will vary based on location

    // Water
    @Shadow @Mutable @Final
    public static final BiomeExtension LAKE = register("lake", builder().heightmap(BiomeNoise::lake).surface(RoadNetworkBuilder.create(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(NormalSurfaceBuilder.INSTANCE)))).aquiferHeightOffset(-16).group(BiomeExtension.Group.LAKE));
    @Shadow @Mutable @Final
    public static final BiomeExtension RIVER = register("river", builder().noise(BiomeNoise::riverSampler).surface(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(NormalSurfaceBuilder.INSTANCE))).aquiferHeight(h -> TFCChunkGenerator.SEA_LEVEL_Y - 16).group(BiomeExtension.Group.RIVER));

    // Mountain Fresh water / carving biomes
    @Shadow @Mutable @Final
    public static final BiomeExtension MOUNTAIN_RIVER = register("mountain_river", builder().heightmap(seed -> BiomeNoise.mountains(seed, 10, 70)).surface(RoadNetworkBuilder.create(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE)))).carving(BiomeNoise::undergroundRivers).group(BiomeExtension.Group.RIVER));
    @Shadow @Mutable @Final
    public static final BiomeExtension OLD_MOUNTAIN_RIVER = register("old_mountain_river", builder().heightmap(seed -> BiomeNoise.mountains(seed, 16, 40)).surface(RoadNetworkBuilder.create(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE)))).carving(BiomeNoise::undergroundRivers).group(BiomeExtension.Group.RIVER));
    @Shadow @Mutable @Final
    public static final BiomeExtension OCEANIC_MOUNTAIN_RIVER = register("oceanic_mountain_river", builder().heightmap(seed -> BiomeNoise.mountains(seed, -16, 60)).surface(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE))).carving(BiomeNoise::undergroundRivers).salty().group(BiomeExtension.Group.RIVER));
    @Shadow @Mutable @Final
    public static final BiomeExtension VOLCANIC_MOUNTAIN_RIVER = register("volcanic_mountain_river", builder().heightmap(seed -> BiomeNoise.mountains(seed, 10, 60)).surface(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE))).volcanoes(4, 25, 50, 40).carving(BiomeNoise::undergroundRivers).group(BiomeExtension.Group.RIVER));
    @Shadow @Mutable @Final
    public static final BiomeExtension VOLCANIC_OCEANIC_MOUNTAIN_RIVER = register("volcanic_oceanic_mountain_river", builder().heightmap(seed -> BiomeNoise.mountains(seed, -24, 50)).surface(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE))).volcanoes(2, -12, 50, 20).carving(BiomeNoise::undergroundRivers).salty().group(BiomeExtension.Group.RIVER));

    @Shadow @Mutable @Final
    public static final BiomeExtension MOUNTAIN_LAKE = register("mountain_lake", builder().heightmap(seed -> BiomeNoise.mountains(seed, 10, 70)).surface(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE))).carving(BiomeNoise::undergroundLakes).group(BiomeExtension.Group.LAKE));
    @Shadow @Mutable @Final
    public static final BiomeExtension OLD_MOUNTAIN_LAKE = register("old_mountain_lake", builder().heightmap(seed -> BiomeNoise.mountains(seed, -16, 60)).surface(RoadNetworkBuilder.create(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE)))).carving(BiomeNoise::undergroundLakes).group(BiomeExtension.Group.LAKE));
    @Shadow @Mutable @Final
    public static final BiomeExtension OCEANIC_MOUNTAIN_LAKE = register("oceanic_mountain_lake", builder().heightmap(seed -> BiomeNoise.mountains(seed, -16, 60)).surface(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE))).carving(BiomeNoise::undergroundLakes).salty().group(BiomeExtension.Group.LAKE));
    @Shadow @Mutable @Final
    public static final BiomeExtension VOLCANIC_MOUNTAIN_LAKE = register("volcanic_mountain_lake", builder().heightmap(seed -> BiomeNoise.mountains(seed, 10, 60)).surface(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE))).volcanoes(4, 25, 50, 40).carving(BiomeNoise::undergroundLakes).group(BiomeExtension.Group.LAKE));
    @Shadow @Mutable @Final
    public static final BiomeExtension VOLCANIC_OCEANIC_MOUNTAIN_LAKE = register("volcanic_oceanic_mountain_lake", builder().heightmap(seed -> BiomeNoise.mountains(seed, -24, 50)).surface(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE))).volcanoes(2, -12, 50, 20).carving(BiomeNoise::undergroundLakes).salty().group(BiomeExtension.Group.LAKE));

    @Shadow @Mutable @Final
    public static final BiomeExtension PLATEAU_LAKE = register("plateau_lake", builder().heightmap(seed -> BiomeNoise.hills(seed, 20, 30)).surface(RoadNetworkBuilder.create(SubSoilSurfaceBuilder.create(RockyDirtSurfaceBuilder.create(MountainSurfaceBuilder.INSTANCE)))).carving(BiomeNoise::undergroundLakes).group(BiomeExtension.Group.LAKE));

    @Shadow
    public static BiomeExtension getExtensionOrThrow(LevelAccessor level, Biome biome)
    {
        return Objects.requireNonNull(getExtension(level, biome), () -> "Biome: " + biome.getRegistryName());
    }

    @Shadow
    public static boolean hasExtension(CommonLevelAccessor level, Biome biome)
    {
        return getExtension(level, biome) != null;
    }

    @Shadow
    @Nullable
    @SuppressWarnings("ConstantConditions")
    public static BiomeExtension getExtension(CommonLevelAccessor level, Biome biome)
    {
        return ((BiomeBridge) (Object) biome).tfc$getExtension(() -> findExtension(level, biome));
    }

    @Shadow
    public static Collection<ResourceKey<Biome>> getAllKeys()
    {
        return EXTENSIONS.keySet();
    }

    @Shadow
    public static Collection<BiomeExtension> getExtensions()
    {
        return EXTENSIONS.values();
    }

    @Shadow
    public static Collection<ResourceLocation> getExtensionKeys()
    {
        return EXTENSIONS.keySet().stream().map(ResourceKey::location).toList();
    }

    @Shadow
    @Nullable
    public static BiomeExtension getById(ResourceLocation id)
    {
        return EXTENSIONS.get(ResourceKey.create(Registry.BIOME_REGISTRY, id));
    }

    @Shadow
    @Nullable
    private static BiomeExtension findExtension(CommonLevelAccessor level, Biome biome)
    {
        final RegistryAccess registryAccess = level.registryAccess();
        final Registry<Biome> registry = registryAccess.registryOrThrow(Registry.BIOME_REGISTRY);
        return registry.getResourceKey(biome).map(EXTENSIONS::get).orElse(null);
    }

    @Shadow
    private static BiomeExtension register(String name, BiomeBuilder builder)
    {
        final ResourceLocation id = Helpers.identifier(name);
        final ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, id);
        final BiomeExtension variants = builder.build(key);

        EXTENSIONS.put(key, variants);
        TFCBiomes.BIOMES.register(name, OverworldBiomes::theVoid);

        return variants;
    }
}
