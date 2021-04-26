package tfcflorae.objects.items.groundcover;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

import tfcflorae.objects.blocks.groundcover.BlockTwig;
import tfcflorae.util.OreDictionaryHelper;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemTwig extends ItemBlockTFC
{
    public ItemTwig(BlockTwig block)
    {
        super(block);
        OreDictionaryHelper.register(this, "wood");
        OreDictionaryHelper.register(this, "wood_twig");
        OreDictionaryHelper.register(this, "twig");
        OreDictionaryHelper.register(this, "wood_stick");
        OreDictionaryHelper.register(this, "stick");
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
        return Weight.LIGHT;
    }

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return getStackSize(stack);
    }
}