package tfcflorae.objects.items;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.IItemSize;

@ParametersAreNonnullByDefault
public abstract class ItemTFCF extends Item implements IItemSize
{
    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return getStackSize(stack);
    }
}