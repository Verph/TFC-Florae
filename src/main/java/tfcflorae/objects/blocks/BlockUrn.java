package tfcflorae.objects.blocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.util.Helpers;
import tfcflorae.client.GuiHandler;
import tfcflorae.objects.te.TEUrn;

/**
 * Urn is an inventory that preserves the contents when sealed
 * It can be picked up and keeps it's inventory
 * Sealed state is stored in a block state property, and cached in the TE (for gui purposes)
 */
@ParametersAreNonnullByDefault
public class BlockUrn extends Block implements IItemSize
{
    public static final PropertyBool SEALED = PropertyBool.create("sealed");
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1D, 0.875D);
    private static final AxisAlignedBB BOUNDING_BOX_SEALED = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1D, 0.875D);

    /**
     * Used to update the vessel seal state and the TE, in the correct order
     */
    public static void toggleUrnSeal(World world, BlockPos pos)
    {
        TEUrn tile = Helpers.getTE(world, pos, TEUrn.class);
        if (tile != null)
        {
            IBlockState state = world.getBlockState(pos);
            boolean previousSealed = state.getValue(SEALED);
            world.setBlockState(pos, state.withProperty(SEALED, !previousSealed));
            if (previousSealed)
            {
                tile.onUnseal();
            }
            else
            {
                tile.onSealed();
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    public BlockUrn()
    {
        super(Material.CIRCUITS);
        setSoundType(SoundType.GLASS);
        setHardness(2F);
        setDefaultState(blockState.getBaseState().withProperty(SEALED, false));
    }

    @Override
    @Nonnull
    public Size getSize(ItemStack stack)
    {
        return stack.getTagCompound() == null ? Size.VERY_LARGE : Size.HUGE; // Causes overburden if sealed
    }

    @Override
    @Nonnull
    public Weight getWeight(ItemStack stack)
    {
        return Weight.VERY_HEAVY; // Stacksize = 1
    }

    @Override
    public boolean canStack(@Nonnull ItemStack stack)
    {
        return stack.getTagCompound() == null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isTopSolid(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(SEALED, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(SEALED) ? 1 : 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isBlockNormalCube(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isNormalCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return state.getValue(SEALED) ? BOUNDING_BOX_SEALED : BOUNDING_BOX;
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!canStay(world, pos))
        {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TEUrn tile = Helpers.getTE(worldIn, pos, TEUrn.class);
        if (tile != null)
        {
            tile.onBreakBlock(worldIn, pos, state);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return canStay(world, pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            TEUrn te = Helpers.getTE(worldIn, pos, TEUrn.class);
            if (te != null)
            {
                if (heldItem.isEmpty() && playerIn.isSneaking())
                {
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1.0F, 0.85F);
                    toggleUrnSeal(worldIn, pos);
                }
                else
                {
                    GuiHandler.openGui(worldIn, pos, playerIn, GuiHandler.Type.URN);
                }
            }
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        // If the barrel was sealed, then copy the contents from the item
        if (!worldIn.isRemote)
        {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt != null)
            {
                TEUrn te = Helpers.getTE(worldIn, pos, TEUrn.class);
                if (te != null)
                {
                    worldIn.setBlockState(pos, state.withProperty(SEALED, true));
                    te.readFromItemTag(nbt);
                }
            }
        }
    }

    @Override
    @Nonnull
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, SEALED);
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TEUrn();
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        // Only drop the barrel if it's not sealed, since the barrel with contents will be already dropped by the TE
        if (!state.getValue(SEALED))
        {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion)
    {
        // Unseal the barrel if an explosion destroys it, so it drops it's contents
        world.setBlockState(pos, world.getBlockState(pos).withProperty(SEALED, false));
        super.onBlockExploded(world, pos, explosion);
    }

    private boolean canStay(IBlockAccess world, BlockPos pos)
    {
        return world.getBlockState(pos.down()).getBlockFaceShape(world, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
    }
}