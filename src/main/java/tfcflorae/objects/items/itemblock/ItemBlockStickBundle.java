package tfcflorae.objects.items.itemblock;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.devices.BlockStickBundle;

@ParametersAreNonnullByDefault
public class ItemBlockStickBundle extends ItemBlockTFC
{
    public final BlockStickBundle block;

	public ItemBlockStickBundle(BlockStickBundle block)
    {
		super(block);
        this.block = block;
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
    @Override
    @Nonnull
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if (worldIn.isRemote) return EnumActionResult.SUCCESS;
		else if (facing != EnumFacing.DOWN) return EnumActionResult.FAIL;
		else
        {
			IBlockState state = worldIn.getBlockState(pos);
			Block block = state.getBlock();
			if (!block.isReplaceable(worldIn, pos)) pos = pos.down();
			BlockPos posDown = pos.down();
			ItemStack itemstack = player.getHeldItem(hand);

			if (player.canPlayerEdit(pos, facing, itemstack) && player.canPlayerEdit(posDown, facing, itemstack))
            {
				IBlockState stateDown = worldIn.getBlockState(posDown);
				boolean canPutBlock = block.isReplaceable(worldIn, pos) || worldIn.isAirBlock(pos);
				boolean canPutBlockDown = stateDown.getBlock().isReplaceable(worldIn, posDown) || worldIn.isAirBlock(posDown);
				if (canPutBlock && canPutBlockDown)
                {
					worldIn.setBlockState(pos, BlocksTFCF.STICK_BUNDLE.getDefaultState().withProperty(BlockStickBundle.PART, BlockStickBundle.EnumBlockPart.UPPER), 10);
					worldIn.setBlockState(posDown, BlocksTFCF.STICK_BUNDLE.getDefaultState(), 10);
					SoundType soundtype = BlocksTFCF.STICK_BUNDLE.getSoundType(stateDown, worldIn, pos, player);
					worldIn.playSound(null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					itemstack.shrink(1);
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return EnumActionResult.FAIL;
	}
}