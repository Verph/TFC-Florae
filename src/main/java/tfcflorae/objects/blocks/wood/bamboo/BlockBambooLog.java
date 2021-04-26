package tfcflorae.objects.blocks.wood.bamboo;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.items.ItemMisc;

import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.util.OreDictionaryHelper;

import static tfcflorae.api.stateproperty.StatePropertiesTFCF.*;

public class BlockBambooLog extends Block
{
    public static final AxisAlignedBB SMALL_LOG = new AxisAlignedBB(0.3, 0, 0.3, 0.7, 1, 0.7);

    private ItemMisc drop;

    public BlockBambooLog()
    {
        super(Material.WOOD, MapColor.GREEN_STAINED_HARDENED_CLAY);
        setHarvestLevel("axe", 0);
        setHardness(2.0F);
        setResistance(5.0F);
        Blocks.FIRE.setFireInfo(this, 5, 5);
        setTickRandomly(true);
        setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(GROWN, true).withProperty(CONNECTED, false));
        OreDictionaryHelper.register(this, "log", "wood");
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        drops.add(new ItemStack(drop, 1));
    }

    public void setDrop(ItemMisc drop)
    {
        this.drop = drop;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        IBlockState downState = worldIn.getBlockState(pos.down());
        boolean shouldDestroy = true;
        if (downState.getBlock() instanceof BlockBambooLog || BlocksTFC.isGrowableSoil(downState) || BlocksTFCF.isGrowableSoil(downState))
            shouldDestroy = false;
        if (shouldDestroy)
        {
            worldIn.destroyBlock(pos, true);
            return;
        }
        boolean shouldConnect = false;
        for (EnumFacing facing : EnumFacing.HORIZONTALS)
        {
            if (worldIn.getBlockState(pos.offset(facing)).getBlock() instanceof BlockBambooLeaves)
            {
                worldIn.setBlockState(pos, state.withProperty(CONNECTED, true));
                shouldConnect = true;
                break;
            }
        }
        worldIn.setBlockState(pos, state.withProperty(CONNECTED, shouldConnect));
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, GROWN, CONNECTED);
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        boolean grown = false;
        if (meta >= 2)
        {
            meta -= 2;
            grown = true;
        }
        return this.getDefaultState().withProperty(CONNECTED, meta == 1).withProperty(GROWN, grown);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int wet = state.getValue(CONNECTED) ? 1 : 0;
        int grown = state.getValue(GROWN) ? 2 : 0;
        return wet + grown;
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        if (state.getValue(CONNECTED))
        {
            return FULL_BLOCK_AABB;
        }
        return SMALL_LOG;
    }

    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        if (state.getValue(CONNECTED))
        {
            return FULL_BLOCK_AABB;
        }
        return SMALL_LOG;
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
}