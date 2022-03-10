package tfcflorae.objects.blocks.devices;

import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.util.Helpers;
import tfcflorae.TFCFlorae;
import tfcflorae.objects.items.itemblock.ItemBlockDryer;
import tfcflorae.objects.recipes.DryingRecipe;
import tfcflorae.objects.te.TEDryer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockDryer extends Block implements IItemSize
{
	public static final PropertyBool SIMPLE = PropertyBool.create("simple");
	private static final PropertyBool VERTICAL_CONNECTION = PropertyBool.create("vertical_connection");

	private static final AxisAlignedBB AABB_SIMPLE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
	private static final AxisAlignedBB AABB_DOUBLE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

	public BlockDryer()
    {
        super(Material.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SIMPLE, true).withProperty(VERTICAL_CONNECTION, false));
        setSoundType(SoundType.WOOD);
        setHardness(1.0F);
        setResistance(2.0F);
        setHarvestLevel("axe", 0);
        setTickRandomly(true);
        Blocks.FIRE.setFireInfo(this, 5, 20);
	}

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack)
    {
        return Size.LARGE; // Can only store in chests
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack)
    {
        return Weight.VERY_HEAVY; // Stacksize = 1
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
		return new TEDryer();
	}

	@Override
	public BlockStateContainer createBlockState()
    {
		return new BlockStateContainer(this, SIMPLE, VERTICAL_CONNECTION);
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
		if(state.getValue(SIMPLE)) return state.withProperty(VERTICAL_CONNECTION, false);
		else return state.withProperty(VERTICAL_CONNECTION, worldIn.getBlockState(pos.up()).getBlock() instanceof BlockDryer);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
	}

	private boolean canBlockStay(World worldIn, BlockPos pos)
	{
		IBlockState state = worldIn.getBlockState(pos.down());
		if(state.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID) return true;
		else if(state.getBlock() instanceof BlockDryer)
		{
			return !state.getValue(SIMPLE);
		}
		else return false;
	}

	/**
	 * Called when a neighboring blocks was changed and marks that this state should perform any checks during a neighbor
	 * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
	 * blocks, etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if(!canBlockStay(worldIn, pos))
		{
			IBlockState iblockstate = worldIn.getBlockState(pos);

			if (!iblockstate.getBlock().isAir(iblockstate, worldIn, pos))
			{
				worldIn.playEvent(2001, pos, Block.getStateId(iblockstate));

				spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1));
				if(!state.getValue(SIMPLE)) spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1));

				worldIn.setBlockToAir(pos);
			}
		}
	}

	@Override
	public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		drops.add(new ItemStack(Item.getItemFromBlock(this), 1));
		if(!state.getValue(SIMPLE)) drops.add(new ItemStack(Item.getItemFromBlock(this), 1));
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
        TEDryer tile = Helpers.getTE(worldIn, pos, TEDryer.class);
        if (tile != null)
        {
            tile.onBreakBlock(worldIn, pos, state);
        }
        super.breakBlock(worldIn, pos, state);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(SIMPLE) ? 0 : 1;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(SIMPLE, meta == 0);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(stack.hasDisplayName())
		{
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if(tileEntity instanceof TileEntityDispenser) ((TileEntityDispenser)tileEntity).setCustomName(stack.getDisplayName());
		}
	}

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            ItemStack held = player.getHeldItem(hand);
            if (held.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) return false;
            TEDryer te = Helpers.getTE(worldIn, pos, TEDryer.class);
			if(te != null)
			{
                IItemHandler inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (inventory != null)
                {
					if(player.isSneaking())
					{
						ItemStack takeStack = te.removeItem();
						//Helpers.spawnItemStack(worldIn, pos, takeStack);
						ItemHandlerHelper.giveItemToPlayer(player, takeStack);
						te.markForSync();
						return true;
					}
					else
					{
						ItemStack output = te.removeIfDone();
						if (output != ItemStack.EMPTY)
						{
							ItemHandlerHelper.giveItemToPlayer(player, output);
							te.markForSync();
							return true;
						}

						ItemStack itemstack = player.getHeldItem(hand);
						ItemStack tryStack = new ItemStack(held.getItem(), 1);
						if (DryingRecipe.get(itemstack) != null || DryingRecipe.get(tryStack) != null)
						{
							//ItemStack leftover = inventory.insertItem(0, held.splitStack(1), false);
							ItemStack leftover = te.addItem(held, state.getValue(SIMPLE));
							ItemHandlerHelper.giveItemToPlayer(player, leftover);
							te.markForSync();
							return true;
						}
					}
				}
			}
		}
		return true;
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
    {
        if (worldIn.isRainingAt(pos.up()))
        {
            TEDryer te = Helpers.getTE(worldIn, pos, TEDryer.class);
            if (te != null)
            {
                te.rain();
            }
        }
    }

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		state = this.getActualState(state, worldIn, pos);
		if(state.getValue(SIMPLE)) return AABB_SIMPLE;
		else return (state.getValue(VERTICAL_CONNECTION)) ? FULL_BLOCK_AABB : AABB_DOUBLE;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing side)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	public Item getCustomItemBlock()
	{
		return new ItemBlockDryer(this)
			.setTranslationKey(this.getTranslationKey())
			.setRegistryName(this.getRegistryName());
	}

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }
}