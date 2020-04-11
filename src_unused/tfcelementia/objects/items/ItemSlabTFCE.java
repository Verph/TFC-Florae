package tfcelementia.objects.items;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import tfcelementia.objects.blocks.BlockSlabTFCE;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemSlabTFCE extends ItemSlab implements IItemSize
{
    public ItemSlabTFCE(BlockSlabTFCE.Half slab, BlockSlabTFCE.Half slab1, BlockSlabTFCE.Double doubleSlab)
    {
        super(slab, slab1, doubleSlab);
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack)
    {
        return Size.SMALL; // if blocks fits in small vessels, this should too
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack)
    {
        return Weight.VERY_LIGHT; // Double the stacksize of a block (or 64)
    }
}