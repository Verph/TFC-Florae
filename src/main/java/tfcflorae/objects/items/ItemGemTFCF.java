package tfcflorae.objects.items;

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
import net.dries007.tfc.objects.items.ItemTFC;
import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.objects.GemTFCF;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemGemTFCF extends ItemTFCF
{
    private static final EnumMap<GemTFCF, ItemGemTFCF> MAP = new EnumMap<>(GemTFCF.class);

    public static ItemGemTFCF get(GemTFCF gem)
    {
        return MAP.get(gem);
    }

    public static ItemStack get(GemTFCF ore, GemTFCF.Grade grade, int amount)
    {
        return new ItemStack(MAP.get(ore), amount, grade.ordinal());
    }

    public final GemTFCF gem;

    public ItemGemTFCF(GemTFCF gem)
    {
        this.gem = gem;
        if (MAP.put(gem, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
        setHasSubtypes(true);
        for (GemTFCF.Grade grade : GemTFCF.Grade.values())
        {
            OreDictionaryHelper.registerMeta(this, grade.ordinal(), "gem", grade, gem);
            OreDictionaryHelper.registerMeta(this, grade.ordinal(), "gem", grade);
            OreDictionaryHelper.registerMeta(this, grade.ordinal(), "gem", gem);
            /*
            if (grade == GemTFCF.Grade.NORMAL)
            {
                OreDictionaryHelper.registerMeta(this, grade.ordinal(), "gem", gem);
            }
            else
            {
                OreDictionaryHelper.registerMeta(this, grade.ordinal(), "gem", grade, gem);
            }
            */
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
    	GemTFCF.Grade grade = getGradeFromStack(stack);
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
            for (GemTFCF.Grade grade : GemTFCF.Grade.values())
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
    private GemTFCF.Grade getGradeFromStack(ItemStack stack)
    {
        return GemTFCF.Grade.valueOf(stack.getItemDamage());
    }
}