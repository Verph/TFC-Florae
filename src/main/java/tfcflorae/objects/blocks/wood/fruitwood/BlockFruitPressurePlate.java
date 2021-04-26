package tfcflorae.objects.blocks.wood.fruitwood;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import tfcflorae.util.OreDictionaryHelper;

public class BlockFruitPressurePlate extends BlockPressurePlate
{
	public BlockFruitPressurePlate()
    {
        super(Material.WOOD, Sensitivity.EVERYTHING);
        setHardness(0.5F);
        setSoundType(SoundType.WOOD);
        OreDictionaryHelper.register(this, "pressure_plate");
        OreDictionaryHelper.register(this, "pressure_plate", "wood");
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}