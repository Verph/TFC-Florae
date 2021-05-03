package tfcflorae.compat.tfcelementia.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;

import tfcelementia.objects.items.metal.ItemMetalTFCE;

import tfcflorae.compat.tfcelementia.jei.wrappers.*;

@JEIPlugin
public class JEIPluginTFCECompat implements IModPlugin
{
    private static IModRegistry REGISTRY;

    /**
     * Helper method to return a collection containing all possible itemstacks registered in JEI
     *
     * @return Collection of ItemStacks
     */
    public static Collection<ItemStack> getAllIngredients()
    {
        return REGISTRY.getIngredientRegistry().getAllIngredients(VanillaTypes.ITEM);
    }

    @Override
    public void register(IModRegistry registry)
    {
        REGISTRY = registry;

        // Register metal related stuff (put everything here for performance + sorted registration)
        List<UnmoldRecipeEarthenwareTFCEWrapper> unmoldListEarthenware = new ArrayList<>();
        List<CastingRecipeEarthenwareTFCEWrapper> castingListEarthenware = new ArrayList<>();
        List<UnmoldRecipeKaoliniteTFCEWrapper> unmoldListKaolinite = new ArrayList<>();
        List<CastingRecipeKaoliniteTFCEWrapper> castingListKaolinite = new ArrayList<>();
        List<UnmoldRecipeStonewareTFCEWrapper> unmoldListStoneware = new ArrayList<>();
        List<CastingRecipeStonewareTFCEWrapper> castingListStoneware = new ArrayList<>();
        List<Metal> tierOrdered = TFCRegistries.METALS.getValuesCollection()
            .stream()
            .sorted(Comparator.comparingInt(metal -> metal.getTier().ordinal()))
            .collect(Collectors.toList());
        for (Metal metal : tierOrdered)
        {
            for (ItemMetalTFCE.ItemType type : ItemMetalTFCE.ItemType.values())
            {
                if (type.hasMold(metal) && type.isTypeActive())
                {
                    unmoldListEarthenware.add(new UnmoldRecipeEarthenwareTFCEWrapper(metal, type));
                    castingListEarthenware.add(new CastingRecipeEarthenwareTFCEWrapper(metal, type));
                    unmoldListKaolinite.add(new UnmoldRecipeKaoliniteTFCEWrapper(metal, type));
                    castingListKaolinite.add(new CastingRecipeKaoliniteTFCEWrapper(metal, type));
                    unmoldListStoneware.add(new UnmoldRecipeStonewareTFCEWrapper(metal, type));
                    castingListStoneware.add(new CastingRecipeStonewareTFCEWrapper(metal, type));
                }
            }
        }
        registry.addRecipes(unmoldListEarthenware, VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipes(unmoldListKaolinite, VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipes(unmoldListStoneware, VanillaRecipeCategoryUid.CRAFTING);
    }
}