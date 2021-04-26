package tfcflorae.api.registries;

import net.minecraft.util.ResourceLocation;

import static tfcflorae.TFCFlorae.MODID;

/**
 * The names are separate from the instances TFCRegistries so they can be used without loading the class prematurely.
 */
public class TFCFRegistryNames
{
    public static final ResourceLocation NUT_TREES_REGISTRY = new ResourceLocation(MODID, "nut_trees");
    public static final ResourceLocation CRACKING_RECIPE = new ResourceLocation(MODID, "cracking_recipe");
}