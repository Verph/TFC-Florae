package tfcflorae.util;

import net.minecraft.world.item.crafting.Ingredient;

import net.dries007.tfc.util.InteractionManager;

import tfcflorae.common.TFCFTags;
import tfcflorae.common.container.TFCFContainerProviders;

public final class TFCFInteractionManager
{
    public static void init()
    {
        InteractionManager.register(Ingredient.of(TFCFTags.Items.EARTHENWARE_CLAY_KNAPPING), false, true, InteractionManager.createKnappingInteraction((stack, player) -> stack.getCount() >= 5, TFCFContainerProviders.EARTHENWARE_CLAY_KNAPPING));
        InteractionManager.register(Ingredient.of(TFCFTags.Items.KAOLINITE_CLAY_KNAPPING), false, true, InteractionManager.createKnappingInteraction((stack, player) -> stack.getCount() >= 5, TFCFContainerProviders.KAOLINITE_CLAY_KNAPPING));
        InteractionManager.register(Ingredient.of(TFCFTags.Items.STONEWARE_CLAY_KNAPPING), false, true, InteractionManager.createKnappingInteraction((stack, player) -> stack.getCount() >= 5, TFCFContainerProviders.STONEWARE_CLAY_KNAPPING));
        InteractionManager.register(Ingredient.of(TFCFTags.Items.FLINT_KNAPPING), false, true, InteractionManager.createKnappingInteraction((stack, player) -> stack.getCount() >= 2, TFCFContainerProviders.FLINT_KNAPPING));
    }
}
