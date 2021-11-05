package tfcflorae.objects.blocks.groundcover;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.stone.BlockFarmlandTFC;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.util.OreDictionaryHelper;

@ParametersAreNonnullByDefault
public class BlockSurfaceSeashells extends BlockBush
{
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.9, 0.4, 0.9);

    int[] chance = {38, 5, 38, 5, 5, 5, 3, 1};
    int[] amount = {2, 1, 2, 1, 1, 1, 1, 1};
    int index = 0;

    public BlockSurfaceSeashells()
    {
        super(Material.GROUND);
        setSoundType(SoundType.STONE);
        setHardness(0.1f);
        OreDictionaryHelper.register(this, "seashell");
        OreDictionaryHelper.register(this, "seashells");
    }

    private Item getWeightedDrop(int chance, int index, int currentNumber)
    {
        this.index = index;
        if(chance <= currentNumber)
        {
            Item[] drops = {ItemsTFCF.CLAM, ItemsTFCF.LIVE_CLAM, ItemsTFCF.SCALLOP, ItemsTFCF.LIVE_SCALLOP, ItemsTFCF.LIVE_STARFISH, ItemsTFCF.CONCH, ItemsTFCF.PEARL, ItemsTFCF.BLACK_PEARL};
            return drops[index];
        }
        else
            return getWeightedDrop(chance, index + 1, currentNumber + this.chance[index + 1]);
    }

    @Override
    public int quantityDropped(Random random)
    {
        int dropAmount = random.nextInt(amount[index]);

        return dropAmount + 1;
    }

    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        int chance = rand.nextInt(100) + 1;
        return getWeightedDrop(chance, 0, this.chance[0]);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isTopSolid(IBlockState state)
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
    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB.offset(state.getOffset(source, pos));
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
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        if (!worldIn.isSideSolid(pos.down(), EnumFacing.UP) && !(worldIn.getBlockState(pos.down()).getBlock() instanceof BlockFarmlandTFC))
        {
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles)
    {
        return true;
    }

    @Override
    public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager)
    {
        return true;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        IBlockState soil = worldIn.getBlockState(pos.down());

        if (state.getBlock() == this)
        {
            return (BlocksTFC.isGround(soil) || BlocksTFCF.isGround(soil) || worldIn.getBlockState(pos.down()).isFullBlock()) && !(BlocksTFC.isSaltWater(soil) || BlocksTFC.isFreshWater(soil));
        }
        return this.canSustainBush(soil);
    }
}
