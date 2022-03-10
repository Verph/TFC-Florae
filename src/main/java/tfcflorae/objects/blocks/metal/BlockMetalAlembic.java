package tfcflorae.objects.blocks.metal;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal;

import tfcflorae.util.OreDictionaryHelper;

import static net.minecraft.block.BlockHorizontal.FACING;

public class BlockMetalAlembic extends Block implements IItemSize
{
    private static final AxisAlignedBB ALEMBIC_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 1.0D, 0.9D);

    private static final Map<Metal, BlockMetalAlembic> TABLE = new HashMap<>();

    public static BlockMetalAlembic get(Metal metal)
    {
        return TABLE.get(metal);
    }

    public static ItemStack get(Metal metal, int amount)
    {
        return new ItemStack(TABLE.get(metal), amount);
    }

    public final Metal metal;

	public BlockMetalAlembic(Metal metal)
	{
		super(Material.IRON);
        if (TABLE.put(metal, this) != null) throw new IllegalStateException("There can only be one.");
        this.metal = metal;

        setHardness(4.0F);
        setResistance(10F);
        setHarvestLevel("pickaxe", 0);

		this.setSoundType(SoundType.METAL);
        setDefaultState(this.blockState.getBaseState());
        OreDictionaryHelper.register(this, "alembic", "metal");
        OreDictionaryHelper.register(this, "alembic", "metal", this.metal);
        OreDictionaryHelper.register(this, "metal", "alembic");
        OreDictionaryHelper.register(this, "metal", this.metal, "alembic");
        OreDictionaryHelper.register(this, "alembic");
        OreDictionaryHelper.register(this, this.metal, "alembic");
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing side)
	{
		return BlockFaceShape.UNDEFINED;
	}

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            if(player.isSneaking())
            {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                return true;
            }
		}
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        if (facing.getAxis() == EnumFacing.Axis.Y)
        {
            if (placer.isSneaking())
            {
                facing = placer.getHorizontalFacing().getOpposite();
            }
            else
            {
                facing = placer.getHorizontalFacing();
            }
        }
        return getDefaultState().withProperty(FACING, facing);
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
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
	}

	private boolean canBlockStay(World worldIn, BlockPos pos)
	{
		IBlockState state = worldIn.getBlockState(pos.down());
		return (state.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return ALEMBIC_AABB;
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
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }
}