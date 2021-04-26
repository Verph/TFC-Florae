package tfcflorae.compat.firmalife.jei;

import java.util.List;
import java.util.stream.Collectors;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;

import tfcflorae.TFCFlorae;
import tfcflorae.compat.firmalife.jei.category.CastingCategoryFLCompat;
import tfcflorae.compat.firmalife.jei.wrappers.CastingRecipeWrapperKaoliniteFL;
import tfcflorae.compat.firmalife.jei.wrappers.UnmoldRecipeWrapperKaoliniteFL;

import static tfcflorae.TFCFlorae.MODID;

@JEIPlugin
public class JEIPluginFLCompat implements IModPlugin
{
    public static final String CASTING_UID = MODID + ".castingfl";

    private static IModRegistry REGISTRY;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        if (TFCFlorae.FirmaLifeAdded)
        {
            registry.addRecipeCategories(new CastingCategoryFLCompat(registry.getJeiHelpers().getGuiHelper(), CASTING_UID));
        }
    }

    @Override
    public void register(IModRegistry registry)
    {
        if (TFCFlorae.FirmaLifeAdded)
        {
            REGISTRY = registry;

            // Molds
            List<UnmoldRecipeWrapperKaoliniteFL> moldRecipes = TFCRegistries.METALS.getValuesCollection().stream()
                .filter(metal -> metal.isToolMetal() && metal.getTier().isAtMost(Metal.Tier.TIER_II))
                .map(metal -> new UnmoldRecipeWrapperKaoliniteFL(metal, "mallet"))
                .collect(Collectors.toList());

            // Casts
            List<CastingRecipeWrapperKaoliniteFL> castRecipes = TFCRegistries.METALS.getValuesCollection().stream()
                .filter(metal -> metal.isToolMetal() && metal.getTier().isAtMost(Metal.Tier.TIER_II))
                .map(metal -> new CastingRecipeWrapperKaoliniteFL(metal, "mallet"))
                .collect(Collectors.toList());

            registry.addRecipes(moldRecipes, "minecraft.crafting");
            registry.addRecipes(castRecipes, CASTING_UID);
        }
    }
}