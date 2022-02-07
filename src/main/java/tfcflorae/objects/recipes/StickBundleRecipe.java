package tfcflorae.objects.recipes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.compat.jei.IJEISimpleRecipe;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import tfcflorae.api.registries.TFCFRegistries;

public class StickBundleRecipe extends IForgeRegistryEntry.Impl<StickBundleRecipe> implements IJEISimpleRecipe
{
    protected IIngredient<ItemStack> inputItem;
    protected ItemStack outputItem;
    private final int duration;

    @Nullable
    public static StickBundleRecipe get(ItemStack item)
    {
        return TFCFRegistries.STICK_BUNDLE.getValuesCollection().stream().filter(x -> x.isValidInput(item)).findFirst().orElse(null);
    }

    public static int getDuration(StickBundleRecipe recipe)
    {
        return recipe.duration;
    }

    public StickBundleRecipe(IIngredient<ItemStack> input, ItemStack output, int duration)
    {
        this.inputItem = input;
        this.outputItem = output;
        this.duration = duration;

        if (inputItem == null || outputItem == null)
        {
            throw new IllegalArgumentException("Stick bundle growing recipes must have an input and output set.");
        }
        if (duration < 1)
        {
            throw new IllegalArgumentException("Stick bundle growing recipes must have a duration set.");
        }
    }

    @Nonnull
    public ItemStack getOutputItem(ItemStack stack)
    {
        return CapabilityFood.updateFoodFromPrevious(stack, outputItem.copy());
    }

    // for JEI
    @Override
    public NonNullList<IIngredient<ItemStack>> getIngredients()
    {
        return NonNullList.withSize(1, inputItem);
    }

    @Override
    public NonNullList<ItemStack> getOutputs()
    {
        return NonNullList.withSize(1, outputItem);
    }

    private boolean isValidInput(ItemStack inputItem)
    {
        return this.inputItem.test(inputItem);
    }
}