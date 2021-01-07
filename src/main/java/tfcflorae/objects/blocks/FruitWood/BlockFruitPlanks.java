package tfcflorae.objects.blocks.FruitWood;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import net.dries007.tfc.objects.blocks.wood.BlockPlanksTFC;
import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.api.types.Tree;

import tfcflorae.util.OreDictionaryHelper;

public class BlockFruitPlanks extends Block
{
    public BlockFruitPlanks()
    {
        super(Material.WOOD, Material.WOOD.getMaterialMapColor());
        setHarvestLevel("axe", 0);
        setHardness(2.0F)
        .setResistance(5.0F);
        OreDictionaryHelper.register(this, "planks");
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}