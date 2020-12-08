package tfcflorae.objects.items;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;

import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.IFruitTree;

import tfcflorae.objects.blocks.FruitWood.BlockFruitDoor;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemFruitDoor extends ItemDoor implements IItemSize
{
    public ItemFruitDoor(BlockFruitDoor block)
    {
        super(block);
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack) { return Size.VERY_LARGE; }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack) { return Weight.HEAVY; }

    @Override
    public int getItemStackLimit(ItemStack stack) { return getStackSize(stack); }
}