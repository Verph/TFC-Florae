package tfcflorae.objects.blocks.FruitWood;

import java.util.HashMap;
import java.util.Map;

import net.dries007.tfc.api.types.IFruitTree;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import tfcflorae.util.OreDictionaryHelper;

public class BlockFruitLogFence extends BlockFence 
{
    public BlockFruitLogFence()
    {
        super(Material.WOOD, Material.WOOD.getMaterialMapColor());
        setHarvestLevel("axe", 0);
        setHardness(2.0F);
        setResistance(15.0F);
        OreDictionaryHelper.register(this, "fence");
        OreDictionaryHelper.register(this, "fence", "log");
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}