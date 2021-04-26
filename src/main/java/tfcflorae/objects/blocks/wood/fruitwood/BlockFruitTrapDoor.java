package tfcflorae.objects.blocks.wood.fruitwood;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import tfcflorae.util.OreDictionaryHelper;

public class BlockFruitTrapDoor extends BlockTrapDoor
{
    public BlockFruitTrapDoor()
    {
        super(Material.WOOD);
        setHardness(0.5F);
        setSoundType(SoundType.WOOD);
        OreDictionaryHelper.register(this, "trapdoor");
    }
}