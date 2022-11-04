package tfcflorae.compat.jei.category;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;

import net.dries007.tfc.compat.jei.category.BaseRecipeCategory;

import tfcflorae.common.TFCFTags;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.common.recipes.BrushingRecipe;

public class BrushingRecipeCategory extends BaseRecipeCategory<BrushingRecipe>
{
    public BrushingRecipeCategory(RecipeType<BrushingRecipe> type, IGuiHelper helper)
    {
        super(type, helper, helper.createBlankDrawable(118, 26), new ItemStack(TFCFItems.BRUSHES.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BrushingRecipe recipe, IFocusGroup focuses)
    {
        Ingredient chiselIngredient = recipe.getItemIngredient();
        if (chiselIngredient == null)
        {
            chiselIngredient = Ingredient.of(TFCFTags.Items.BRUSHES);
        }

        builder.addSlot(RecipeIngredientRole.INPUT, 6, 5)
            .addIngredients(collapse(recipe.getBlockIngredient()))
            .setBackground(slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.INPUT, 26, 5)
            .addIngredients(chiselIngredient)
            .setBackground(slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 76, 5)
            .addItemStack(new ItemStack(recipe.getBlockRecipeOutput()))
            .setBackground(slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 5)
            .addItemStack(recipe.getExtraDrop(ItemStack.EMPTY))
            .setBackground(slot, -1, -1);
    }
}