package tfcflorae.common.recipes.ingredients;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.recipes.ingredients.DelegateIngredient;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.JsonHelpers;

/**
 * An ingredient which respects non-rotten foods
 */
public class IsRottenIngredient extends DelegateIngredient
{
    public static IsRottenIngredient of(Ingredient ingredient)
    {
        return new IsRottenIngredient(ingredient);
    }

    protected IsRottenIngredient(@Nullable Ingredient delegate)
    {
        super(delegate);
    }

    @Override
    public boolean test(@Nullable ItemStack stack)
    {
        return super.test(stack) && stack != null && stack.getCapability(FoodCapability.CAPABILITY).map(cap -> cap.isRotten()).orElse(false);
    }

    @Override
    public IIngredientSerializer<? extends DelegateIngredient> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    @Nullable
    @Override
    protected ItemStack testDefaultItem(ItemStack stack)
    {
        return stack.getCapability(FoodCapability.CAPABILITY).map(food -> {
            food.setNonDecaying();
            return stack;
        }).orElse(null);
    }

    public enum Serializer implements IIngredientSerializer<IsRottenIngredient>
    {
        INSTANCE;

        @Override
        public IsRottenIngredient parse(JsonObject json)
        {
            final Ingredient internal = json.has("ingredient") ? Ingredient.fromJson(JsonHelpers.get(json, "ingredient")) : null;
            return new IsRottenIngredient(internal);
        }

        @Override
        public IsRottenIngredient parse(FriendlyByteBuf buffer)
        {
            return new IsRottenIngredient(Helpers.decodeNullable(buffer, Ingredient::fromNetwork));
        }

        @Override
        public void write(FriendlyByteBuf buffer, IsRottenIngredient ingredient)
        {
            Helpers.encodeNullable(ingredient.delegate, buffer, Ingredient::toNetwork);
        }
    }
}
