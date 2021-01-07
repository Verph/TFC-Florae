package tfcflorae.objects.blocks.FruitWood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import tfcflorae.util.OreDictionaryHelper;

public abstract class BlockFruitDoubleSlabBase extends BlockSlab 
{
	public BlockFruitDoubleSlabBase() 
	{
        super((Material) Material.WOOD);
        setHarvestLevel("axe", 0);
        setHardness(2.0F)
        .setResistance(5.0F);
        OreDictionaryHelper.register(this, "slab_double");
        Blocks.FIRE.setFireInfo(this, 5, 20);
		this.useNeighborBrightness = true;
        setSoundType(SoundType.WOOD);
	}
}