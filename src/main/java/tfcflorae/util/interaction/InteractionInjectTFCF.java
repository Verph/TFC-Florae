package tfcflorae.util.interaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.types.ICrop;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;
import net.dries007.tfc.objects.blocks.stone.BlockFarmlandTFC;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.util.agriculture.Crop;
import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.blocktype.farmland.FarmlandTFCF;
import tfcflorae.util.agriculture.CropTFCF;

public class InteractionInjectTFCF
{
    @Nonnull
    public static EnumActionResult onItemUse(ItemSeedsTFC itemSeed, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);
        if (state.getBlock() instanceof BlockFarmlandTFC)
        {
            return itemstack.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, itemSeed) && worldIn.isAirBlock(pos.up()) && state.getBlock() instanceof FarmlandTFCF)
        {
            ICrop seedCrop = null;
            for (Crop crop : Crop.values())
                if (itemSeed == ItemSeedsTFC.get(crop))
                    seedCrop = crop;
            if (seedCrop == null)
                for(CropTFCF crop : CropTFCF.values())
                    if (itemSeed == ItemSeedsTFC.get(crop))
                        seedCrop = crop;

            worldIn.setBlockState(pos.up(), BlockCropTFC.get(seedCrop).getDefaultState());

            if (player instanceof EntityPlayerMP)
            {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos.up(), itemstack);
            }

            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }
}
