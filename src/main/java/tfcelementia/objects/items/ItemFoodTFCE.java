package tfcelementia.objects.items;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

//import net.dries007.tfc.api.capability.food.FoodHandler;
//import net.dries007.tfc.api.capability.food.FoodHeatHandler;
import net.dries007.tfc.api.capability.food.IFoodStatsTFC;
//import net.dries007.tfc.util.OreDictionaryHelper;
//import net.dries007.tfc.util.agriculture.Food;

import tfcelementia.util.OreDictionaryHelper;
import tfcelementia.util.agriculture.FoodTFCE;
import tfcelementia.api.capability.food.FoodHandlerTFCE;
import tfcelementia.api.capability.food.FoodHeatHandlerTFCE;

public class ItemFoodTFCE extends ItemFood
{
	private static final Map<FoodTFCE, ItemFoodTFCE> MAP = new HashMap<>();

    public static ItemFoodTFCE get(FoodTFCE food)
    {
        return MAP.get(food);
    }

    public static ItemStack get(FoodTFCE food, int amount)
    {
        return new ItemStack(MAP.get(food), amount);
    }

    private final FoodTFCE food;

    public ItemFoodTFCE(@Nonnull FoodTFCE food)
    {
        super(IFoodStatsTFC.FOOD_HUNGER_AMOUNT, food.getCalories(), food.getCategory() == FoodTFCE.Category.MEAT);
        this.food = food;
        if (MAP.put(food, this) != null)
        {
            throw new IllegalStateException("There can only be one.");
        }

        // Use "category" here as to not conflict with actual items, i.e. grain
        OreDictionaryHelper.register(this, "category", food.getCategory());
        if (food.getOreDictNames() != null)
        {
            for (Object name : food.getOreDictNames())
            {
                OreDictionaryHelper.register(this, name);
            }
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return food.isHeatable() ? new FoodHeatHandlerTFCE(nbt, food) : new FoodHandlerTFCE(nbt, food);
    }
}