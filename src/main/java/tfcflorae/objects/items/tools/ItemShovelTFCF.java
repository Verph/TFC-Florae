package tfcflorae.objects.items.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.damage.DamageType;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;

import tfcflorae.util.OreDictionaryHelper;

@ParametersAreNonnullByDefault
public class ItemShovelTFCF extends ItemSpade implements IItemSize
{
    public final ToolMaterial material;

    public ItemShovelTFCF(ToolMaterial material, float AttackDamage, float AttackSpeed, int Durability, Object... oreNameParts)
    {
        super(material);
	    this.material = material;
	    this.attackDamage = AttackDamage * material.getAttackDamage();
	    this.attackSpeed = AttackSpeed;
        this.setMaxDamage(Durability);
        this.setHarvestLevel("shovel", material.getHarvestLevel());

        for (Object obj : oreNameParts)
        {
            if (obj instanceof Object[])
                OreDictionaryHelper.register(this, (Object[]) obj);
            else
                OreDictionaryHelper.register(this, obj);
        }
        OreDictionaryHelper.registerDamageType(this, DamageType.CRUSHING);
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();
            if (!(block instanceof BlockRockVariant))
            {
                return EnumActionResult.PASS;
            }
            BlockRockVariant rockVariant = (BlockRockVariant) block;
            if (ConfigTFC.General.OVERRIDES.enableGrassPath && facing != EnumFacing.DOWN && worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR && rockVariant.getType() == Rock.Type.GRASS || rockVariant.getType() == Rock.Type.DRY_GRASS || rockVariant.getType() == Rock.Type.DIRT)
            {
                IBlockState iblockstate1 = BlockRockVariant.get(rockVariant.getRock(), Rock.Type.PATH).getDefaultState();
                worldIn.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

                if (!worldIn.isRemote)
                {
                    worldIn.setBlockState(pos, iblockstate1, 11);
                    itemstack.damageItem(1, player);
                }

                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack)
    {
        return Size.LARGE; // Stored only in chests
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack)
    {
        return Weight.MEDIUM;
    }

    @Override
    public boolean canStack(ItemStack stack)
    {
        return false;
    }
}