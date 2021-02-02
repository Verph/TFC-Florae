package tfcflorae.objects.items;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.ItemMisc;

public class ItemRoastedFoodTFCF extends ItemMisc
{

    public ItemRoastedFoodTFCF()
    {
        super(Size.SMALL, Weight.LIGHT);
    }

    @Override
    @Nonnull
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        return new ItemStack(ItemsTFCF.COCOA_POWDER);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }
}