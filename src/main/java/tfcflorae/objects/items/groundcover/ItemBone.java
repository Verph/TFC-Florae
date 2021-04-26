package tfcflorae.objects.items.groundcover;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

import tfcflorae.objects.blocks.groundcover.BlockSurfaceBones;
import tfcflorae.util.OreDictionaryHelper;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemBone extends ItemBlockTFC
{
    public ItemBone(BlockSurfaceBones block)
    {
        super(block);
        OreDictionaryHelper.register(this, "bone");
        OreDictionaryHelper.register(this, "bones");
        OreDictionaryHelper.register(this, "dye_white");
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