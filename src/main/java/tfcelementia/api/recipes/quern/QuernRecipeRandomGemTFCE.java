package tfcelementia.api.recipes.quern;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.recipes.quern.QuernRecipe;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ItemGem;

import tfcelementia.objects.GemTFCE;
import tfcelementia.objects.items.ItemGemTFCE;

public class QuernRecipeRandomGemTFCE extends QuernRecipe
{
    private final GemTFCE gem;

    public QuernRecipeRandomGemTFCE(IIngredient<ItemStack> input, GemTFCE gem)
    {
        super(input, ItemGemTFCE.get(gem, GemTFCE.Grade.NORMAL, 1));
        this.gem = gem;
    }

    @Nonnull
    @Override
    public ItemStack getOutputItem(ItemStack stack)
    {
    	GemTFCE.Grade grade = GemTFCE.Grade.randomGrade(Constants.RNG);
        return ItemGemTFCE.get(gem, grade, 1);
    }
}