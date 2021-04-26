package tfcflorae.objects.items.groundcover;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

import tfcflorae.objects.blocks.groundcover.BlockSurfaceRock;
import tfcflorae.util.OreDictionaryHelper;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemRockTFCF extends ItemBlockTFC
{
    public ItemRockTFCF(BlockSurfaceRock block)
    {
        super(block);
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack)
    {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack)
    {
        return Weight.LIGHT;
    }

    @Nonnull
    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return getStackSize(stack);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && !player.isSneaking() && stack.getCount() > 1)
        {
            TFCGuiHandler.openGui(world, player.getPosition(), player, TFCGuiHandler.Type.KNAPPING_STONE);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}