package tfcflorae.common.recipes.ingredients;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import tfcflorae.util.TFCFHelpers;

public final class TFCFIngredients
{
    public static void registerIngredientTypes()
    {
        register("is_rotten", IsRottenIngredient.Serializer.INSTANCE);
    }

    private static <T extends Ingredient> void register(String name, IIngredientSerializer<T> serializer)
    {
        CraftingHelper.register(TFCFHelpers.identifier(name), serializer);
    }
}
