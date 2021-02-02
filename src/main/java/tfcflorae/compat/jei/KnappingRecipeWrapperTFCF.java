package tfcflorae.compat.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.compat.jei.wrappers.KnappingRecipeWrapper;
import net.dries007.tfc.util.Helpers;

import tfcflorae.TFCFlorae;
import tfcflorae.api.knapping.KnappingTypes;

public class KnappingRecipeWrapperTFCF extends KnappingRecipeWrapper
{
    private static final ResourceLocation MUD_TEXTURE = new ResourceLocation(TFCFlorae.MODID,"textures/gui/knapping/mud_button.png");
    private static final ResourceLocation MUD_DISABLED_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/mud_button_disabled.png");
    private static final ResourceLocation KAOLINITE_CLAY_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/kaolinite_clay_button.png");
    private static final ResourceLocation KAOLINITE_CLAY_DISABLED_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/kaolinite_clay_button_disabled.png");

    private static ResourceLocation getHighTexture(KnappingType type) {
        if(type == KnappingTypes.MUD)
        {
            return MUD_TEXTURE;
        }
        else if(type == KnappingTypes.KAOLINITE_CLAY)
        {
            return KAOLINITE_CLAY_TEXTURE;
        }
        return null;
    }
    private static ResourceLocation getLowTexture(KnappingType type) {
        if(type == KnappingTypes.MUD)
        {
            return MUD_DISABLED_TEXTURE;
        }
        else if(type == KnappingTypes.KAOLINITE_CLAY)
        {
            return KAOLINITE_CLAY_DISABLED_TEXTURE;
        }
        return null;
    }

    public KnappingRecipeWrapperTFCF(KnappingRecipe recipe, IGuiHelper helper)
    {
        super(recipe, helper, getHighTexture(recipe.getType()), getLowTexture(recipe.getType()));

    }
}