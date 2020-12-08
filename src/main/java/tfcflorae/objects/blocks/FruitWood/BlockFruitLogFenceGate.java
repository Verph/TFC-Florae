package tfcflorae.objects.blocks.FruitWood;

import java.util.HashMap;
import java.util.Map;

import net.dries007.tfc.api.types.IFruitTree;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;

import tfcflorae.util.OreDictionaryHelper;

public class BlockFruitLogFenceGate extends BlockFenceGate 
{
    public BlockFruitLogFenceGate()
    {
        super(BlockPlanks.EnumType.OAK);
        setHarvestLevel("axe", 0);
        setHardness(2.0F);
        setResistance(15.0F);
        OreDictionaryHelper.register(this, "fence_gate", "fence_gate_fruit", "fence_gate_log", "fence_gate_log_fruit");
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}