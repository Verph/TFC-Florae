package tfcflorae.api.registries;

import net.minecraft.util.ResourceLocation;

import static tfcflorae.TFCFlorae.MODID;

/**
 * The names are separate from the instances TFCRegistries so they can be used without loading the class prematurely.
 */
public final class TFCFRegistryNames
{
    public static final ResourceLocation PLANT = new ResourceLocation(MODID, "plant");
}