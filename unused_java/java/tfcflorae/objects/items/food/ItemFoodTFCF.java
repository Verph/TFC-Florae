/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package tfcflorae.objects.items.food;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import tfcflorae.api.capability.food.FoodHandlerTFCF;
import tfcflorae.api.capability.food.FoodHeatHandlerTFCF;
import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.util.agriculture.FoodTFCF;

@ParametersAreNonnullByDefault
public class ItemFoodTFCF extends ItemFood implements IItemSize
{
    private static final Map<FoodTFCF, ItemFoodTFCF> MAP = new HashMap<>();

    public static ItemFoodTFCF get(FoodTFCF food)
    {
        return MAP.get(food);
    }

    public static ItemStack get(FoodTFCF food, int amount)
    {
        return new ItemStack(MAP.get(food), amount);
    }

    protected final FoodTFCF food;

    public ItemFoodTFCF(@Nonnull FoodTFCF food)
    {
        super(0, 0, food.getCategory() == FoodTFCF.Category.MEAT || food.getCategory() == FoodTFCF.Category.COOKED_MEAT);
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

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            // Makes creative items not decay (like JEI)
            ItemStack stack = new ItemStack(this);
            IFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
            if (cap != null)
            {
                cap.setNonDecaying();
            }
            items.add(stack);
        }
    }

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return getStackSize(stack);
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack)
    {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack)
    {
        return Weight.VERY_LIGHT;
    }

    public ICapabilityProvider getCustomFoodHandler()
    {
        return food.isHeatable() ? new FoodHeatHandlerTFCF(null, food) : new FoodHandlerTFCF(null, food);
    }
}