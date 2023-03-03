package tfcflorae.mixin;

import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import tfcflorae.Config;
import tfcflorae.codec.MixinHooks;

/**
 * Credit to <a href="https://github.com/alcatrazEscapee/cyanide/blob/1.18.x/src/main/java/com/alcatrazescapee/cyanide/mixin/BiomeSourceMixin.java">alcatrazEscapee</a>
 */
@Mixin(BiomeSource.class)
public abstract class BiomeSourceMixin
{
    /**
     * Replace this with a method that has a much better error tracing and is probably more efficient
     */
    @Inject(method = "buildFeaturesPerStep", at = @At("HEAD"), cancellable = true)
    private void buildFeaturesPerStepWithAdvancedCycleDetection(List<Holder<Biome>> biomes, boolean topLevel, CallbackInfoReturnable<List<BiomeSource.StepFeatureData>> cir)
    {
        /*if (Config.COMMON.enableDebug.get())
        {
            cir.setReturnValue(MixinHooks.buildFeaturesPerStepAndPopulateErrors(biomes));
        }*/
    }
}
