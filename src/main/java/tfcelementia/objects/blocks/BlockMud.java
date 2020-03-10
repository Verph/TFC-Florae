package tfcelementia.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import net.dries007.tfc.util.OreDictionaryHelper;
import tfcelementia.TFCElementia;

public class BlockMud extends Block
{
    public BlockMud(Material material)
    {
        super(material);
        setSoundType(SoundType.GROUND);
        setHardness(0.6F);
        setHarvestLevel("shovel", 0);
        OreDictionaryHelper.register(this, "mud");
        Blocks.FIRE.setFireInfo(this, 5, 10);
    }
}