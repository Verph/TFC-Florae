package tfcflorae.objects.blocks.FruitWood;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.ObjectUtils;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;


import net.dries007.tfc.api.types.IFruitTree;

import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.items.ItemFruitDoor;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.util.OreDictionaryHelper;

public class BlockFruitDoor extends BlockDoor
{
    public BlockFruitDoor()
    {
        super(Material.WOOD);
        setHardness(3.0F);
        disableStats();
        OreDictionaryHelper.register(this, "door", "door_fruit");
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    public Item getItem() //From the way we build the ImmutableLists these two should always be sorted
    {
        Iterator<ItemFruitDoor> ifd = ItemsTFCF.getAllFruitDoors().iterator();
        Iterator<BlockFruitDoor> bfd = BlocksTFCF.getAllFruitDoors().iterator();
        while (ifd.hasNext() && bfd.hasNext())
        {
            ItemFruitDoor i = ifd.next();
            BlockFruitDoor b = bfd.next();
            if (this == b)
                return i;
        }
        return null;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(HALF) == EnumDoorHalf.UPPER ? Items.AIR : getItem();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(getItem());
    }
}