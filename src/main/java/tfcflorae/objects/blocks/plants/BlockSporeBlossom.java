package tfcflorae.objects.blocks.plants;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import tfcflorae.client.particle.TFCFParticles;
import tfcflorae.objects.blocks.plants.BlockPlant.BlockPlantDummy1;

@ParametersAreNonnullByDefault
public class BlockSporeBlossom extends BlockPlantDummy1
{
    private static final PropertyDirection FACING = PropertyDirection.create("facing");
    private static final AxisAlignedBB PLANT_DOWN_AABB = new AxisAlignedBB(0.1D, 0.2D, 0.1D, 0.9D, 1.0D, 0.9D);
    private static final AxisAlignedBB PLANT_UP_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);
    private static final AxisAlignedBB PLANT_SOUTH_AABB = new AxisAlignedBB(0.1D, 0.1D, 0.0D, 0.9D, 0.9D, 0.8D);
    private static final AxisAlignedBB PLANT_NORTH_AABB = new AxisAlignedBB(0.1D, 0.1D, 0.2D, 0.9D, 0.9D, 1.0D);
    private static final AxisAlignedBB PLANT_WEST_AABB = new AxisAlignedBB(0.2D, 0.1D, 0.1D, 1.0D, 0.9D, 0.9D);
    private static final AxisAlignedBB PLANT_EAST_AABB = new AxisAlignedBB(0.0D, 0.1D, 0.1D, 0.8D, 0.9D, 0.9D);

    private static final Map<Plant, BlockSporeBlossom> MAP = new HashMap<>();

    public static BlockSporeBlossom get(Plant plant)
    {
        return MAP.get(plant);
    }

    public BlockSporeBlossom(Plant plant)
    {
        super(plant);
        if (MAP.put(plant, this) != null) throw new IllegalStateException("There can only be one.");
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        this.onNeighborChangeInternal(worldIn, pos, state);
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
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        world.setBlockState(pos, state.withProperty(DAYPERIOD, getDayPeriod()).withProperty(growthStageProperty, plant.getStageForMonth()));
        checkAndDropBlock(world, pos, state);
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
    protected boolean canSustainBush(IBlockState state)
    {
        return true;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        for (EnumFacing enumfacing : FACING.getAllowedValues())
        {
            if (this.canPlaceAt(worldIn, pos, enumfacing))
            {
                return plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) && plant.isValidRain(ChunkDataTFC.getRainfall(worldIn, pos));
            }
        }

        return false;
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (state.getValue(FACING))
        {
            case EAST:
                return PLANT_EAST_AABB;
            case WEST:
                return PLANT_WEST_AABB;
            case SOUTH:
                return PLANT_SOUTH_AABB;
            case NORTH:
                return PLANT_NORTH_AABB;
            case DOWN:
                return PLANT_DOWN_AABB;
            default:
                return PLANT_UP_AABB;
        }
    }

    @Nonnull
    protected BlockStateContainer createPlantBlockState()
    {
        return new BlockStateContainer(this, FACING, growthStageProperty, DAYPERIOD, AGE);
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
            else if (axis.isVertical() && !this.canPlaceAt(worldIn, blockpos, facing))
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

    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing)
    {
        BlockPos blockpos = pos.offset(facing.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

        return blockfaceshape == BlockFaceShape.SOLID;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random random)
    {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        double d0 = i + random.nextDouble();
        double d1 = j + 0.7D;
        double d2 = k + random.nextDouble();
        TFCFParticles.FALLING_BLOSSOM.spawn(worldIn, d0, d1, d2, 0.0D, 0.0D, 0.0D, (int)(64 / (Math.random() * 0.9D + 0.1D)));
        BlockPos.MutableBlockPos blockPos$mutableBlockPos = new BlockPos.MutableBlockPos();

        for (int l = 0; l < 14; ++l)
        {
            blockPos$mutableBlockPos.setPos(i + MathHelper.getInt(random, -10, 10), j - random.nextInt(10), k + MathHelper.getInt(random, -10, 10));
            IBlockState blockState = worldIn.getBlockState(blockPos$mutableBlockPos);
            if (!blockState.isSideSolid(worldIn, blockPos$mutableBlockPos, state.getValue(FACING)))
            {
                TFCFParticles.BLOSSOM.spawn(worldIn, (double)blockPos$mutableBlockPos.getX() + random.nextDouble(), (double)blockPos$mutableBlockPos.getY() + random.nextDouble(), (double)blockPos$mutableBlockPos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D, (random.nextInt(1000 - 500) + 500));
            }
        }
    }
}
