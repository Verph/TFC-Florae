package tfcflorae.objects.items.tools;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.objects.items.ItemTFC;

import tfcflorae.util.OreDictionaryHelper;

public class ItemToolHeadTFCF extends ItemTFC implements IItemSize
{
    private final Size size;
    private final Weight weight;

    public ItemToolHeadTFCF(Size size, Weight weight, Object... oreNameParts)
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

    public ItemToolHeadTFCF(Size size, Weight weight)
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