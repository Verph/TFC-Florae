package tfcflorae.objects.blocks.FruitWood;

import java.util.HashMap;
import java.util.Map;

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
        OreDictionaryHelper.register(this, "pressure_plate", "pressure_plate_wood", "pressure_plate_wood_fruit");
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}