package tfcflorae.objects.blocks.groundcover;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.property.PropertyFloat;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.stone.BlockFarmlandTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.metal.ItemOreTFC;
import net.dries007.tfc.objects.items.metal.ItemSmallOre;
import net.dries007.tfc.objects.items.rock.ItemRock;

import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.util.OreDictionaryHelper;

@ParametersAreNonnullByDefault
public class BlockLightstone extends BlockBush implements IItemSize
{
    private static final PropertyDirection FACING = PropertyDirection.create("facing");
    private static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);
    private static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.1D, 0.2D, 0.1D, 0.9D, 1.0D, 0.9D);
    private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.1D, 0.1D, 0.2D, 0.9D, 0.9D, 1.0D);
    private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.1D, 0.1D, 0.0D, 0.9D, 0.9D, 0.8D);
    private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.1D, 0.1D, 0.8D, 0.9D, 0.9D);
    private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.2D, 0.1D, 0.1D, 1.0D, 0.9D, 0.9D);

    protected final BlockStateContainer blockState;

    public BlockLightstone(float lightLevel)
    {
        super(Material.ROCK);
        setSoundType(SoundType.GLASS);
        setHardness(0.5f).setResistance(5.0F);
        this.setLightLevel(lightLevel);
        blockState = this.createPlantBlockState();
        this.setDefaultState(this.blockState.getBaseState());
        OreDictionaryHelper.register(this, "ore", "yooperlite");
        OreDictionaryHelper.register(this, "gem", "yooperlite");
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack)
    {
        return Size.TINY; // Store anywhere
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack)
    {
        return Weight.LIGHT;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        this.onNeighborChangeInternal(worldIn, pos, state);
    }

    @Nonnull
    @Override
    public BlockStateContainer getBlockState()
    {
        return this.blockState;
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    @Override
    @Nonnull
    public Block.EnumOffsetType getOffsetType()
    {
        return EnumOffsetType.NONE;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        for (EnumFacing enumfacing : FACING.getAllowedValues())
        {
            if (this.canPlaceAt(worldIn, pos, enumfacing))
            {
                return worldIn.getBlockState(pos).getBlock() != this;
            }
        }

        return false;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        for (EnumFacing enumfacing : FACING.getAllowedValues())
        {
            if (this.canPlaceAt(worldIn, pos, enumfacing))
            {
                return true;
            }
        }

        return false;
    }

    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing)
    {
        BlockPos blockpos = pos.offset(facing.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

        return blockfaceshape == BlockFaceShape.SOLID || BlocksTFC.isGround(iblockstate) || BlocksTFCF.isGround(iblockstate) || worldIn.getBlockState(blockpos).isFullBlock();
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (state.getValue(FACING))
        {
            case EAST:
                return EAST_AABB;
            case WEST:
                return WEST_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case NORTH:
                return NORTH_AABB;
            case DOWN:
                return DOWN_AABB;
            default:
                return UP_AABB;
        }
    }

    @Nonnull
    protected BlockStateContainer createPlantBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        if (this.canPlaceAt(worldIn, pos, facing))
        {
            return this.getDefaultState().withProperty(FACING, facing);
        }
        else
        {
            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
            {
                if (this.canPlaceAt(worldIn, pos, enumfacing))
                {
                    return this.getDefaultState().withProperty(FACING, enumfacing);
                }
            }

            return this.getDefaultState();
        }
    }

    public IBlockState getStateForWorldGen(World worldIn, BlockPos pos)
    {
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            if (this.canPlaceAt(worldIn, pos, enumfacing))
            {
                return this.getDefaultState().withProperty(FACING, enumfacing);
            }
        }
        for (EnumFacing enumfacing : EnumFacing.Plane.VERTICAL)
        {
            if (this.canPlaceAt(worldIn, pos, enumfacing))
            {
                return this.getDefaultState().withProperty(FACING, enumfacing);
            }
        }

        return this.getDefaultState();
    }

    private void onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state)
    {
        if (this.checkForDrop(worldIn, pos, state))
        {
            EnumFacing facing = state.getValue(FACING);
            EnumFacing.Axis axis = facing.getAxis();
            BlockPos blockpos = pos.offset(facing.getOpposite());
            boolean flag = false;

            if (axis.isHorizontal() && worldIn.getBlockState(blockpos).getBlockFaceShape(worldIn, blockpos, facing) != BlockFaceShape.SOLID)
            {
                flag = true;
            }
            else if (axis.isVertical() && !this.canPlaceAt(worldIn, pos, state.getValue(FACING)))
            {
                flag = true;
            }

            if (flag)
            {
                worldIn.destroyBlock(pos, true);
            }
        }
    }

    private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, state.getValue(FACING)))
        {
            return true;
        }
        else
        {
            if (worldIn.getBlockState(pos).getBlock() == this)
            {
                checkAndDropBlock(worldIn, pos, state);
            }

            return false;
        }
    }

    /*@Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ItemOreTFC.get(TFCRegistries.ORES.getValue(OreTypesTFCF.YOOPERLITE));
    }*/

    @Override
    @Nonnull
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(state.getBlock());
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
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
    public boolean isTopSolid(IBlockState state)
    {
        return false;
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isBlockNormalCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isNormalCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }
}
