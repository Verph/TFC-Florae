package tfcflorae.objects.items.itemblock;

import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

import tfcflorae.objects.blocks.plants.BlockCaveMushroom;

public class ItemBlockCaveMushroom extends ItemBlockTFC
{
    public final BlockCaveMushroom block;

	public ItemBlockCaveMushroom(BlockCaveMushroom block)
    {
		super(block);
        this.block = block;
	}
}
