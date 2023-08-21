package tfcflorae.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.resource.ResourceCacheManager;

/**
 * From FirmaLife by EERussianGuy.
 */
@Pseudo
@Mixin(ResourceCacheManager.class)
public class ResourceCacheManagerMixin
{
    @Inject(method = "index", at = @At("HEAD"), cancellable = true, remap = false)
    private void inject$initForNamespace(String namespace, CallbackInfo ci)
    {
        if (!ResourceLocation.isValidNamespace(namespace))
        {
            ci.cancel();
            //TFCFlorae.LOGGER.info("Namespace REJECTED: " + namespace);
        }
    }
}
