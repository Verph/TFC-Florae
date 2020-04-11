package tfcelementia.objects.items;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
//import net.dries007.tfc.util.OreDictionaryHelper;

import tfcelementia.objects.items.ItemMiscTFCE;
import tfcelementia.objects.items.ItemTFCE;
import tfcelementia.util.OreDictionaryHelper;

public class ItemMiscTFCE extends ItemTFCE implements IItemSize
{
    private final Size size;
    private final Weight weight;

    public ItemMiscTFCE(Size size, Weight weight, Object... oreNameParts)
    {
        this(size, weight);

        for (Object obj : oreNameParts)
        {
            if (obj instanceof Object[])
                OreDictionaryHelper.register(this, (Object[]) obj);
            else
                OreDictionaryHelper.register(this, obj);
        }
    }

    public ItemMiscTFCE(Size size, Weight weight)
    {
        this.size = size;
        this.weight = weight;
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack)
    {
        return size;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack)
    {
        return weight;
    }
}