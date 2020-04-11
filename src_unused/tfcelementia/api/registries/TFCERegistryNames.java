package tfcelementia.api.registries;

import net.minecraft.util.ResourceLocation;

import static tfcelementia.TFCElementia.MODID;

/**
 * The names are separate from the instances TFCRegistries so they can be used without loading the class prematurely.
 */
public final class TFCERegistryNames
{
    public static final ResourceLocation ROCK_TYPE_TFCE = new ResourceLocation(MODID, "rock_type_tfce");
    public static final ResourceLocation ROCK_TFCE = new ResourceLocation(MODID, "rock_tfce");
}