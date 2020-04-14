package tfcelementia.objects.items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
//import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.objects.items.ItemMisc;

import tfcelementia.objects.items.ItemMiscTFCE;
import tfcelementia.objects.items.ItemTFCE;
import tfcelementia.util.OreDictionaryHelper;

@SuppressWarnings("WeakerAccess")
//public class ItemMiscTFCE extends ItemMisc
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

	/*
    private String oreDictionary;

    public ItemMiscTFCE(Size size, Weight weight, String oreDictionary)
    {
        super(size, weight);
        this.oreDictionary = oreDictionary;
    }

    public ItemMiscTFCE(Size size, Weight weight)
    {
        super(size, weight);
        oreDictionary = null;
    }

    @Nullable
    public String getOreDictionary()
    {
        return oreDictionary;
    }
    */
}