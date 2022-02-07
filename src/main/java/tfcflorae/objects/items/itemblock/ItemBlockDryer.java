package tfcflorae.objects.items.itemblock;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import tfcflorae.objects.blocks.devices.BlockDryer;
import tfcflorae.objects.te.TEDryer;

public class ItemBlockDryer extends ItemBlock
{
	public ItemBlockDryer(BlockDryer dryer)
    {
		super(dryer);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		ItemStack itemstack = player.getHeldItem(hand);

		if (!itemstack.isEmpty() && player.canPlayerEdit(pos.offset(facing), facing, itemstack))
        {
			IBlockState state = worldIn.getBlockState(pos);
			if (state.getBlock() == this.block)
            {
				if (state.getValue(BlockDryer.SIMPLE))
                {
					IBlockState madeState = this.block.getActualState(this.block.getDefaultState().withProperty(BlockDryer.SIMPLE, false), worldIn, pos);
					TEDryer tileEntity = (TEDryer) worldIn.getTileEntity(pos);
					AxisAlignedBB aABB = madeState.getCollisionBoundingBox(worldIn, pos);

					if (aABB != null && worldIn.checkNoEntityCollision(aABB.offset(pos)) && worldIn.setBlockState(pos, madeState, 11))
                    {
						SoundType soundtype = this.block.getSoundType(madeState, worldIn, pos, player);
						worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
						itemstack.shrink(1);
						if (player instanceof EntityPlayerMP) CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);

						TEDryer newTileEntity = (TEDryer) worldIn.getTileEntity(pos);
						//if (newTileEntity != null) newTileEntity.upgrade(tileEntity);
					}
					return EnumActionResult.SUCCESS;
				}
			}
			return this.tryPlace(player, itemstack, worldIn, pos.offset(facing)) ? EnumActionResult.SUCCESS : super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		}
        else return EnumActionResult.FAIL;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack)
    {
		if(worldIn.getBlockState(pos).getBlock() == this.block) return true;
		return super.canPlaceBlockOnSide(worldIn, pos, side, player, stack);
	}

	private boolean tryPlace(EntityPlayer player, ItemStack stack, World worldIn, BlockPos pos)
    {
		IBlockState state = worldIn.getBlockState(pos);

		if (state.getBlock() == this.block)
        {
			IBlockState madeState = this.block.getActualState(this.block.getDefaultState(), worldIn, pos);
			AxisAlignedBB aABB = madeState.getCollisionBoundingBox(worldIn, pos);

			if (aABB != null && worldIn.checkNoEntityCollision(aABB.offset(pos)) && worldIn.setBlockState(pos, madeState, 11))
            {
				SoundType soundtype = this.block.getSoundType(madeState, worldIn, pos, player);
				worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				stack.shrink(1);
			}

			return true;
		}
		return false;
	}
}