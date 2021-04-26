package tfcflorae.objects.items.itemblock;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.heat.ItemHeatHandler;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

import tfcflorae.objects.blocks.BlockUrnLoot;

@ParametersAreNonnullByDefault
public class ItemBlockUrnLoot extends ItemBlockTFC implements IItemSize
{
    public ItemBlockUrnLoot(BlockUrnLoot block)
    {
        super(block);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        // Since this is technically still a pottery item, despite being a block
        return new ItemHeatHandler(nbt, 1.0f, 1599f);
    }
}