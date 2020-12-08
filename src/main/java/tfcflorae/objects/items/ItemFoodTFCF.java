package tfcflorae.objects.items;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.api.capability.food.FoodHandler;
import net.dries007.tfc.api.capability.food.IItemFoodTFC;

import tfcflorae.util.agriculture.FoodDataTFCF;

public class ItemFoodTFCF extends ItemFood implements IItemFoodTFC
{
    public FoodData data;

    public ItemFoodTFCF(FoodData data)
    {
        super(0, 0.0F, false);
        this.setMaxDamage(0);
        this.data = data;
    }

    @Override
    public ICapabilityProvider getCustomFoodHandler()
    {
        return new FoodHandler(null, data);
    }

    private static final Map<ItemTFCF, ItemFoodTFCF> MAP = new HashMap<>();

    public static ItemFoodTFCF get(ItemFoodTFCF food)
    {
        return MAP.get(food);
    }

    public static ItemStack get(ItemTFCF food, int amount)
    {
        return new ItemStack(MAP.get(food), amount);
    }
}