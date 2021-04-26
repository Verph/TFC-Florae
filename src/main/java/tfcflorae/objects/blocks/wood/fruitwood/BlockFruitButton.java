package tfcflorae.objects.blocks.wood.fruitwood;

import net.minecraft.block.BlockButtonWood;
import net.minecraft.block.SoundType;
import net.minecraft.init.Blocks;

import tfcflorae.util.OreDictionaryHelper;

public class BlockFruitButton extends BlockButtonWood
{
    public BlockFruitButton()
    {
        setHardness(0.5F);
        setSoundType(SoundType.WOOD);
        OreDictionaryHelper.register(this, "button");
        OreDictionaryHelper.register(this, "button", "wood");
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}