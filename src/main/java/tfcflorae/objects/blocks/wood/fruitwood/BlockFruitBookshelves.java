package tfcflorae.objects.blocks.wood.fruitwood;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import tfcflorae.util.OreDictionaryHelper;

public class BlockFruitBookshelves extends Block
{
    public BlockFruitBookshelves()
    {
        super(Material.WOOD, Material.WOOD.getMaterialMapColor());
        setHarvestLevel("axe", 0);
        setHardness(2.0F);
        setResistance(15.0F);
        setSoundType(SoundType.WOOD);
        OreDictionaryHelper.register(this, "bookshelf");
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public float getEnchantPowerBonus(World world, BlockPos pos)
    {
        return 1.0F; // Same as vanilla
    }
}