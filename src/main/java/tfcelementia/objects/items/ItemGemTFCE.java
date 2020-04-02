package tfcelementia.objects.items;

import java.util.EnumMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
//import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.items.ItemTFC;
//import net.dries007.tfc.util.OreDictionaryHelper;
import tfcelementia.util.OreDictionaryHelper;
import tfcelementia.objects.GemTFCE;
import tfcelementia.TFCElementia;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemGemTFCE extends ItemTFC
{
    private static final EnumMap<GemTFCE, ItemGemTFCE> MAP = new EnumMap<>(GemTFCE.class);

    public static ItemGemTFCE get(GemTFCE gem)
    {
        return MAP.get(gem);
    }

    public static ItemStack get(GemTFCE ore, GemTFCE.Grade grade, int amount)
    {
        return new ItemStack(MAP.get(ore), amount, grade.ordinal());
    }

    public final GemTFCE gem;

    public ItemGemTFCE(GemTFCE gem)
    {
        this.gem = gem;
        if (MAP.put(gem, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
        setHasSubtypes(true);
        for (GemTFCE.Grade grade : GemTFCE.Grade.values())
        {
            if (grade == GemTFCE.Grade.NORMAL)
            {
                OreDictionaryHelper.registerMeta(this, grade.ordinal(), "gem", gem);
            }
            OreDictionaryHelper.registerMeta(this, grade.ordinal(), "gem", grade);
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
    	GemTFCE.Grade grade = getGradeFromStack(stack);
        if (grade != null)
        {
            return super.getTranslationKey(stack) + "." + grade.name().toLowerCase();
        }
        return super.getTranslationKey(stack);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (isInCreativeTab(tab))
        {
            for (GemTFCE.Grade grade : GemTFCE.Grade.values())
            {
                items.add(new ItemStack(this, 1, grade.ordinal()));
            }
        }
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

    @Nullable
    private GemTFCE.Grade getGradeFromStack(ItemStack stack)
    {
        return GemTFCE.Grade.valueOf(stack.getItemDamage());
    }
}