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

import tfcflorae.compat.tfcelementia.jei.wrappers.CastingRecipeKaoliniteTFCEWrapper;
import tfcflorae.compat.tfcelementia.jei.wrappers.UnmoldRecipeKaoliniteTFCEWrapper;

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
        List<UnmoldRecipeKaoliniteTFCEWrapper> unmoldList = new ArrayList<>();
        List<CastingRecipeKaoliniteTFCEWrapper> castingList = new ArrayList<>();
        List<Metal> tierOrdered = TFCRegistries.METALS.getValuesCollection()
            .stream()
            .sorted(Comparator.comparingInt(metal -> metal.getTier().ordinal()))
            .collect(Collectors.toList());
        for (Metal metal : tierOrdered)
        {
            for (ItemMetalTFCE.ItemType type : ItemMetalTFCE.ItemType.values())
            {
                if (type.hasMold(metal))
                {
                    unmoldList.add(new UnmoldRecipeKaoliniteTFCEWrapper(metal, type));
                    castingList.add(new CastingRecipeKaoliniteTFCEWrapper(metal, type));
                }
            }
        }
        registry.addRecipes(unmoldList, VanillaRecipeCategoryUid.CRAFTING);
    }
}