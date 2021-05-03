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
import tfcflorae.compat.firmalife.jei.category.*;
import tfcflorae.compat.firmalife.jei.wrappers.*;

import static tfcflorae.TFCFlorae.MODID;

@JEIPlugin
public class JEIPluginFLCompat implements IModPlugin
{
    public static final String CASTING_EARTHENWARE_UID = MODID + ".casting_earthenware_firmalife";
    public static final String CASTING_KAOLINITE_UID = MODID + ".casting_kaolinite_firmalife";
    public static final String CASTING_STONEWARE_UID = MODID + ".casting_stoneware_firmalife";

    private static IModRegistry REGISTRY;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        if (TFCFlorae.FirmaLifeAdded)
        {
            registry.addRecipeCategories(new CastingCategoryEarthenwareFLCompat(registry.getJeiHelpers().getGuiHelper(), CASTING_EARTHENWARE_UID));
            registry.addRecipeCategories(new CastingCategoryKaoliniteFLCompat(registry.getJeiHelpers().getGuiHelper(), CASTING_KAOLINITE_UID));
            registry.addRecipeCategories(new CastingCategoryStonewareFLCompat(registry.getJeiHelpers().getGuiHelper(), CASTING_STONEWARE_UID));
        }
    }

    @Override
    public void register(IModRegistry registry)
    {
        if (TFCFlorae.FirmaLifeAdded)
        {
            REGISTRY = registry;

            // Earthenware
            // Molds
            List<UnmoldRecipeWrapperEarthenwareFL> moldRecipesEarthenware = TFCRegistries.METALS.getValuesCollection().stream()
                .filter(metal -> metal.isToolMetal() && metal.getTier().isAtMost(Metal.Tier.TIER_II))
                .map(metal -> new UnmoldRecipeWrapperEarthenwareFL(metal, "mallet"))
                .collect(Collectors.toList());

            // Casts
            List<CastingRecipeWrapperEarthenwareFL> castRecipesEarthenware = TFCRegistries.METALS.getValuesCollection().stream()
                .filter(metal -> metal.isToolMetal() && metal.getTier().isAtMost(Metal.Tier.TIER_II))
                .map(metal -> new CastingRecipeWrapperEarthenwareFL(metal, "mallet"))
                .collect(Collectors.toList());

            // Kaolinite
            // Molds
            List<UnmoldRecipeWrapperKaoliniteFL> moldRecipesKaolinite = TFCRegistries.METALS.getValuesCollection().stream()
                .filter(metal -> metal.isToolMetal() && metal.getTier().isAtMost(Metal.Tier.TIER_II))
                .map(metal -> new UnmoldRecipeWrapperKaoliniteFL(metal, "mallet"))
                .collect(Collectors.toList());

            // Casts
            List<CastingRecipeWrapperKaoliniteFL> castRecipesKaolinite = TFCRegistries.METALS.getValuesCollection().stream()
                .filter(metal -> metal.isToolMetal() && metal.getTier().isAtMost(Metal.Tier.TIER_II))
                .map(metal -> new CastingRecipeWrapperKaoliniteFL(metal, "mallet"))
                .collect(Collectors.toList());

            // Stoneware
            // Molds
            List<UnmoldRecipeWrapperStonewareFL> moldRecipesStoneware = TFCRegistries.METALS.getValuesCollection().stream()
                .filter(metal -> metal.isToolMetal() && metal.getTier().isAtMost(Metal.Tier.TIER_II))
                .map(metal -> new UnmoldRecipeWrapperStonewareFL(metal, "mallet"))
                .collect(Collectors.toList());

            // Casts
            List<CastingRecipeWrapperStonewareFL> castRecipesStoneware = TFCRegistries.METALS.getValuesCollection().stream()
                .filter(metal -> metal.isToolMetal() && metal.getTier().isAtMost(Metal.Tier.TIER_II))
                .map(metal -> new CastingRecipeWrapperStonewareFL(metal, "mallet"))
                .collect(Collectors.toList());

            registry.addRecipes(moldRecipesEarthenware, "minecraft.crafting");
            registry.addRecipes(castRecipesEarthenware, CASTING_EARTHENWARE_UID);
            registry.addRecipes(moldRecipesKaolinite, "minecraft.crafting");
            registry.addRecipes(castRecipesKaolinite, CASTING_KAOLINITE_UID);
            registry.addRecipes(moldRecipesStoneware, "minecraft.crafting");
            registry.addRecipes(castRecipesStoneware, CASTING_STONEWARE_UID);
        }
    }
}