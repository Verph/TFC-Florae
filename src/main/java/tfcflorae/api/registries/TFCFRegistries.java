package tfcflorae.api.registries;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import tfcflorae.api.recipes.CrackingRecipe;
import tfcflorae.api.recipes.NutRecipe;

/**
 * This is where we initialize our registry instances!
 */
public class TFCFRegistries
{
    public static final IForgeRegistry<NutRecipe> NUT_TREES = GameRegistry.findRegistry(NutRecipe.class);
    public static final IForgeRegistry<CrackingRecipe> CRACKING = GameRegistry.findRegistry(CrackingRecipe.class);
}