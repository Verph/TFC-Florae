package tfcelementia.objects.items;

import java.util.EnumMap;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.items.ItemTFC;
//import net.dries007.tfc.util.OreDictionaryHelper;
import tfcelementia.util.OreDictionaryHelper;
import tfcelementia.objects.PowderGemTFCE;
import tfcelementia.TFCElementia;

@SuppressWarnings("WeakerAccess")
@ParametersAreNonnullByDefault
public class ItemPowderGemTFCE extends ItemTFC
{
    private static final EnumMap<PowderGemTFCE, ItemPowderGemTFCE> MAP = new EnumMap<>(PowderGemTFCE.class);

    public static ItemPowderGemTFCE get(PowderGemTFCE powder)
    {
        return MAP.get(powder);
    }

    public static ItemStack get(PowderGemTFCE powder, int amount)
    {
        return new ItemStack(MAP.get(powder), amount);
    }

    private final PowderGemTFCE powder;

    public ItemPowderGemTFCE(PowderGemTFCE powder)
    {
        this.powder = powder;
        if (MAP.put(powder, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
        OreDictionaryHelper.register(this, "dust", powder);
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack)
    {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack)
    {
        return Weight.VERY_LIGHT;
    }

    @Nonnull
    public PowderGemTFCE getPowder()
    {
        return powder;
    }
}