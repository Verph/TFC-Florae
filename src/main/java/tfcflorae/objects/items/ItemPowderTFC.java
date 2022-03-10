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
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.util.Helpers;

import tfcflorae.objects.blocks.groundcover.BlockPowder;
import tfcflorae.objects.te.TEPowder;

@SuppressWarnings("WeakerAccess")
@ParametersAreNonnullByDefault
public class ItemPowderTFC extends ItemTFCF
{
    private static final EnumMap<Powder, ItemPowderTFC> MAP = new EnumMap<>(Powder.class);

    public static ItemPowderTFC get(Powder powder)
    {
        return MAP.get(powder);
    }

    public static ItemStack get(Powder powder, int amount)
    {
        return new ItemStack(MAP.get(powder), amount);
    }

    public final Powder powder;

    public ItemPowderTFC(Powder powder)
    {
        this.powder = powder;
        if (MAP.put(powder, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
        /*OreDictionaryHelper.register(this, "dust", powder);
        OreDictionaryHelper.register(this, "powder", powder);
        if (powder == Powder.SULFUR)
        {
            OreDictionaryHelper.register(this, "dust", "sulphur");
            OreDictionaryHelper.register(this, "powder", "sulphur");
        }
        if (powder == Powder.LAPIS_LAZULI)
        {
            OreDictionaryHelper.register(this, "dust", "lapis");
            OreDictionaryHelper.register(this, "powder", "lapis");
        }*/
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
    public Powder getPowder()
    {
        return powder;
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (worldIn.getBlockState(pos).isNormalCube() && stack.getItem() instanceof ItemPowderTFC)
        {
            if (!ItemStack.areItemStacksEqual(new ItemStack(stack.getItem(), stack.getCount()), stack))
            {
                return EnumActionResult.FAIL;
            }
            ItemPowderTFC block = (ItemPowderTFC) stack.getItem();
            BlockPos posAt = pos.offset(facing);
            IBlockState stateAt = worldIn.getBlockState(posAt);

            if (stateAt.getBlock() instanceof BlockPowder)
            {
                // Existing sheet block
                Powder powderItem = ((BlockPowder) stateAt.getBlock()).getPowder();
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
                    worldIn.setBlockState(posAt, BlockPowder.get(block.powder).getDefaultState());
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
        TEPowder tile = Helpers.getTE(world, pos, TEPowder.class);
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