package tfcflorae.objects.blocks.wood.fruitwood;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
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
        setSoundType(SoundType.WOOD);
        OreDictionaryHelper.register(this, "fence_gate");
        OreDictionaryHelper.register(this, "fence_gate", "log");
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}