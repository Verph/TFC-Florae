package tfcflorae.mixin.world.biome; 

import java.util.List;
import java.util.Set;
import java.util.function.DoubleUnaryOperator;
import java.util.function.LongFunction;

import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import net.dries007.tfc.world.BiomeNoiseSampler;
import net.dries007.tfc.world.biome.BiomeExtension;
import net.dries007.tfc.world.biome.BiomeExtension.Group;
import net.dries007.tfc.world.biome.TFCBiomes;
import net.dries007.tfc.world.surface.builder.SurfaceBuilderFactory;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import tfcflorae.interfaces.TFCBiomesMixinInterface;

@Mixin(BiomeExtension.class)
public abstract class BiomeExtensionMixin
{
    @Unique private static TFCBiomes staticBiomes = new TFCBiomes();
    @Unique private static final BiomeExtension RIVERBANK = ((TFCBiomesMixinInterface) (Object) staticBiomes).getStaticRiverbank();
    @Unique private static final BiomeExtension GRAVEL_SHORE = ((TFCBiomesMixinInterface) (Object) staticBiomes).getStaticGravelShores();
    @Unique private static final BiomeExtension LAKE_SHORE = ((TFCBiomesMixinInterface) (Object) staticBiomes).getStaticLakeShore();
    @Unique private static final BiomeExtension SHORE_DUNES = ((TFCBiomesMixinInterface) (Object) staticBiomes).getStaticShoreDunes();
    @Unique private static final BiomeExtension COASTAL_CLIFFS = ((TFCBiomesMixinInterface) (Object) staticBiomes).getStaticCoastalCliffs();

    @Shadow private final ResourceKey<Biome> key;

    @Shadow private final LongFunction<BiomeNoiseSampler> noiseFactory;
    @Shadow private final DoubleUnaryOperator aquiferSurfaceHeight;
    @Shadow private final SurfaceBuilderFactory surfaceBuilderFactory;

    @Shadow private final Group group;
    @Shadow private final boolean salty;
    @Shadow private final boolean volcanic;
    @Shadow private final int volcanoRarity;
    @Shadow private final int volcanoBasaltHeight;
    @Shadow private final boolean spawnable;

    @Shadow @Nullable private List<HolderSet<PlacedFeature>> flattenedFeatures;
    @Shadow @Nullable private Set<PlacedFeature> flattenedFeatureSet;
    @Shadow @Nullable private Biome prevBiome;

    BiomeExtensionMixin(ResourceKey<Biome> key, LongFunction<BiomeNoiseSampler> noiseFactory, SurfaceBuilderFactory surfaceBuilderFactory, DoubleUnaryOperator aquiferSurfaceHeight, Group group, boolean salty, boolean volcanic, int volcanoRarity, int volcanoBasaltHeight, boolean spawnable)
    {
        this.key = key;
        this.noiseFactory = noiseFactory;
        this.surfaceBuilderFactory = surfaceBuilderFactory;
        this.aquiferSurfaceHeight = aquiferSurfaceHeight;
        this.group = group;
        this.salty = salty;
        this.volcanic = volcanic;
        this.volcanoRarity = volcanoRarity;
        this.volcanoBasaltHeight = volcanoBasaltHeight;
        this.spawnable = spawnable;
    }

    @Overwrite(remap = false)
    public boolean isShore()
    {
        BiomeExtension biome = (BiomeExtension) (Object) this;
        return biome == TFCBiomes.SHORE || biome == GRAVEL_SHORE || biome == LAKE_SHORE;
    }
}
