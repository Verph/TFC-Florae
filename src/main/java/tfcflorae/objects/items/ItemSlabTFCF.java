package tfcflorae.objects.items;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

import mcp.MethodsReturnNonnullByDefault;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import tfcflorae.objects.blocks.blocktype.BlockSlabTFCF;
import tfcflorae.objects.blocks.wood.fruitwood.BlockFruitSlab;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemSlabTFCF extends ItemSlab implements IItemSize
{
    public ItemSlabTFCF(BlockFruitSlab.Half slab, BlockFruitSlab.Half slab1, BlockFruitSlab.Double doubleSlab)
    {
        super(slab, slab1, doubleSlab);
    }

    public ItemSlabTFCF(BlockSlabTFCF.Half slab, BlockSlabTFCF.Half slab1, BlockSlabTFCF.Double doubleSlab)
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
