package tfcflorae.objects.blocks.wood;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.api.util.IGrowingPlant;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.types.TreesTFCF;
import tfcflorae.util.OreDictionaryHelper;

@ParametersAreNonnullByDefault
public class BlockJoshuaTreeSapling extends BlockBush implements IGrowable, IGrowingPlant
{
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 4);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.1, 0, 0.1, 0.9, 0.9, 0.9);
    private static final Map<Tree, BlockJoshuaTreeSapling> MAP = new HashMap<>();

    public static BlockJoshuaTreeSapling get(Tree wood)
    {
        return MAP.get(wood);
    }

    private final Tree wood;

    public BlockJoshuaTreeSapling(Tree wood)
    {
        if (MAP.put(wood, this) != null) throw new IllegalStateException("There can only be one.");
        this.wood = wood;
        setDefaultState(blockState.getBaseState().withProperty(STAGE, 0));
        setSoundType(SoundType.PLANT);
        setHardness(0.0F);
        OreDictionaryHelper.register(this, "tree", "sapling");
        //noinspection ConstantConditions
        OreDictionaryHelper.register(this, "tree", "sapling", wood.getRegistryName().getPath());
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(STAGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(STAGE);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        return (super.canPlaceBlockAt(worldIn, pos) || BlocksTFC.isSand(worldIn.getBlockState(pos.down())) || BlocksTFC.isSoilOrGravel(worldIn.getBlockState(pos.down())) || BlocksTFCF.isSand(worldIn.getBlockState(pos.down())) || BlocksTFCF.isSoilOrGravel(worldIn.getBlockState(pos.down())) || block == Blocks.HARDENED_CLAY || block == Blocks.STAINED_HARDENED_CLAY);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TETickCounter te = Helpers.getTE(worldIn, pos, TETickCounter.class);
        if (te != null)
        {
            te.resetCounter();
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, STAGE);
    }

    @Override
    @Nonnull
    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XZ;
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
        return new TETickCounter();
    }

    public Tree getWood()
    {
        return wood;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
    {
        super.updateTick(world, pos, state, random);

        if (!world.isRemote)
        {
            TETickCounter te = Helpers.getTE(world, pos, TETickCounter.class);
            if (te != null)
            {
                long days = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_DAY;
                if (days > wood.getMinGrowthTime())
                {
                    grow(world, random, pos, state);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SAPLING_AABB;
    }

    @Override
    @Nonnull
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Plains;
    }

    @Override
    public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean b)
    {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState)
    {
        return false;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState blockState)
    {
        /*for (int air = 1; air < 15; air++)
        {
            if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                return;
        }
        int height = 1 + rand.nextInt(2);
        IBlockState flower = BlockJoshuaTreeFlower.get(wood).getDefaultState();
        for (int trunk = 0; trunk < height; trunk++)
        {
            BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
            world.setBlockState(trunkPos, BlockJoshuaTreeLog.get(wood).getDefaultState());
            if (trunk < 0)
                continue;
            for (EnumFacing d : EnumFacing.HORIZONTALS)
            {
                world.setBlockState(trunkPos.offset(d, 1), flower);
                if (rand.nextFloat() < 1 - (float) trunk / height)
                {
                    world.setBlockState(trunkPos.offset(d, 2), flower);
                }
                else { continue; }
                if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                    world.setBlockState(trunkPos.offset(d, 3), flower);
            }
        }
        world.setBlockState(pos.offset(EnumFacing.UP, height), flower);*/

        float avgTemperature = ClimateTFC.getAvgTemp(world, pos);
        float rainfall = ChunkDataTFC.getRainfall(world, pos);

        int j = rand.nextInt(5);
        for (int k = 0; k < j; ++k)
        {
            int l = rand.nextInt(16) + 8;
            int i1 = rand.nextInt(16) + 8;
            int j1 = world.getHeight(pos.add(l, 0, i1)).getY();
            if (j1 > 0)
            {
                int k1 = j1 - 1;
                Block block = world.getBlockState(pos.down()).getBlock();
                if (world.isAirBlock(pos.add(l, k1 + 1, i1)) && (BlocksTFC.isSand(world.getBlockState(pos.add(l, k1, i1))) || BlocksTFC.isSoilOrGravel(world.getBlockState(pos.add(l, k1, i1))) || BlocksTFCF.isSand(world.getBlockState(pos.add(l, k1, i1))) || BlocksTFCF.isSoilOrGravel(world.getBlockState(pos.add(l, k1, i1))) || block == Blocks.HARDENED_CLAY || block == Blocks.STAINED_HARDENED_CLAY))
                {
                    BlockJoshuaTreeFlower.get(wood).generatePlant(world, pos.add(l, k1 + 1, i1), rand, 8);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        wood.addInfo(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos)
    {
        return GrowthStatus.GROWING;
    }
}