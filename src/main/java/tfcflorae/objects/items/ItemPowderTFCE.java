package tfcflorae.objects.items;

import java.util.EnumMap;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.util.Helpers;

import tfcelementia.objects.PowderTFCE;

import tfcflorae.objects.blocks.groundcover.BlockPowderTFCE;
import tfcflorae.objects.te.TEPowderTFCE;

@SuppressWarnings("WeakerAccess")
@ParametersAreNonnullByDefault
public class ItemPowderTFCE extends ItemTFCF
{
    private static final EnumMap<PowderTFCE, ItemPowderTFCE> MAP = new EnumMap<>(PowderTFCE.class);

    public static ItemPowderTFCE get(PowderTFCE powder)
    {
        return MAP.get(powder);
    }

    public static ItemStack get(PowderTFCE powder, int amount)
    {
        return new ItemStack(MAP.get(powder), amount);
    }

    public final PowderTFCE powder;

    public ItemPowderTFCE(PowderTFCE powder)
    {
        this.powder = powder;
        if (MAP.put(powder, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack)
    {
        return Size.SMALL; // Stored everywhere
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack)
    {
        return Weight.VERY_LIGHT; // Stacksize = 64
    }

    @Nonnull
    public PowderTFCE getPowder()
    {
        return powder;
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (worldIn.getBlockState(pos).isNormalCube() && stack.getItem() instanceof ItemPowderTFCE)
        {
            if (!ItemStack.areItemStacksEqual(new ItemStack(stack.getItem(), stack.getCount()), stack))
            {
                return EnumActionResult.FAIL;
            }
            ItemPowderTFCE block = (ItemPowderTFCE) stack.getItem();
            BlockPos posAt = pos.offset(facing);
            IBlockState stateAt = worldIn.getBlockState(posAt);

            if (stateAt.getBlock() instanceof BlockPowderTFCE)
            {
                // Existing sheet block
                PowderTFCE powderItem = ((BlockPowderTFCE) stateAt.getBlock()).getPowder();
                if (powderItem == block.powder)
                {
                    stack.shrink(1);
                    player.setHeldItem(hand, stack);
                    return placeBlock(worldIn, posAt, facing);
                }
            }
            else if (stateAt.getBlock().isReplaceable(worldIn, posAt))
            {
                // Place a new block
                if (!worldIn.isRemote)
                {
                    worldIn.setBlockState(posAt, BlockPowderTFCE.get(block.powder).getDefaultState());
                    stack.shrink(1);
                    player.setHeldItem(hand, stack);
                    placeBlock(worldIn, posAt, facing);
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }

    public EnumActionResult placeBlock(World world, BlockPos pos, EnumFacing facing)
    {
        TEPowderTFCE tile = Helpers.getTE(world, pos, TEPowderTFCE.class);
        if (tile != null && !tile.getFace(facing))
        {
            if (!world.isRemote)
            {
                tile.setFace(facing, true);
                world.playSound(null, pos.offset(facing), SoundEvents.BLOCK_GRAVEL_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }
}