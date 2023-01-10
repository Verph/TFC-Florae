package tfcflorae.codec;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.core.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import net.minecraftforge.server.ServerLifecycleHooks;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import org.slf4j.Logger;

/**
 * Credit to <a href="https://github.com/alcatrazEscapee/cyanide/blob/1.18.x/src/main/java/com/alcatrazescapee/cyanide/codec/MixinHooks.java">alcatrazEscapee</a>
 */
public final class MixinHooks
{
    private static final GenerationStep.Decoration[] DECORATION_STEPS = GenerationStep.Decoration.values();
    private static final Logger LOGGER = LogUtils.getLogger();
    @Nullable private static Codec<Holder<StructureProcessorList>> STRUCTURE_PROCESSOR_LIST_CODEC;

    public static List<BiomeSource.StepFeatureData> buildFeaturesPerStepAndPopulateErrors(List<Holder<Biome>> allBiomes)
    {
        // This is delayed enough (as the underlying function is made lazy), so we can just retrieve the registry access from the server
        final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null)
        {
            final RegistryAccess registryAccess = server.registryAccess();
            final Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registry.BIOME_REGISTRY);
            final Registry<PlacedFeature> placedFeatureRegistry = registryAccess.registryOrThrow(Registry.PLACED_FEATURE_REGISTRY);
            return FeatureCycleDetector.buildFeaturesPerStep(allBiomes, b -> idFor(biomeRegistry, b), f -> idFor(placedFeatureRegistry, f));
        }
        return FeatureCycleDetector.buildFeaturesPerStep(allBiomes);
    }

    private static <T> String idFor(Registry<T> registry, T element)
    {
        return registry.getResourceKey(element)
            .map(e -> e.location().toString())
            .orElseGet(() -> "[Unknown " + registry.key().location() + ']');
    }
}
