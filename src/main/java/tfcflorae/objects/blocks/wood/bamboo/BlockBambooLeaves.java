package tfcflorae.objects.blocks.wood.bamboo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFC;

import tfcflorae.types.TreesTFCF;
import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.objects.blocks.BlocksTFCF;

public class BlockBambooLeaves extends BlockLeavesTFC
{
    private BlockBambooSapling sapling;

    public BlockBambooLeaves(Tree tree)
    {
        super(tree);
        setSoundType(SoundType.PLANT);
        setDefaultState(blockState.getBaseState().withProperty(DECAYABLE, true));
        OreDictionaryHelper.register(this, "tree", "leaves");
        OreDictionaryHelper.register(this, "tree", "leaves", wood.getRegistryName().getPath());
    }

    public void setBambooSapling(BlockBambooSapling sapling)
    {
        this.sapling = sapling;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        //worldIn.scheduleUpdate(pos, state.getBlock(), 1);
        doLeafDecay(worldIn, pos, state);
    }

    @Override
    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos)
    {
        // Don't do vanilla decay
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        int chance = 10;
        if (RANDOM.nextInt(101) < chance)
        {
            drops.add(new ItemStack(sapling));
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World world, BlockPos pos, @Nullable Block blockIn, @Nullable BlockPos fromPos)
    {
        for (EnumFacing d : EnumFacing.VALUES)
        {
            for (int i = 0; i < 6; i++)
            {
                Block offsetBlock = world.getBlockState(pos.offset(d, i)).getBlock();
                if (offsetBlock instanceof BlockBambooLog)
                    return;
            }
        }
        //world.destroyBlock(pos, true);
        //doLeafDecay(world, pos, state);
        world.scheduleUpdate(pos, this, 0);
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, DECAYABLE);
    }

    @Override
    @Nonnull
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        return ImmutableList.of(new ItemStack(this));
    }

    private void doLeafDecay(World world, BlockPos pos, IBlockState state)
    {
        if (world.isRemote)
            return;

        Set<BlockPos> paths = new HashSet<>();
        Set<BlockPos> evaluated = new HashSet<>(); // Leaves that everything was evaluated so no need to do it again
        List<BlockPos> pathsToAdd; // New Leaves that needs evaluation
        BlockPos.MutableBlockPos pos1 = new BlockPos.MutableBlockPos(pos);
        IBlockState state1;
        paths.add(pos); // Center block

        for (int i = 0; i < 6; i++)
        {
            pathsToAdd = new ArrayList<>();
            for (BlockPos p1 : paths)
            {
                for (EnumFacing face : EnumFacing.values())
                {
                    pos1.setPos(p1).move(face);
                    if (evaluated.contains(pos1) || !world.isBlockLoaded(pos1))
                        continue;
                    state1 = world.getBlockState(pos1);
                    if (state1.getBlock() instanceof BlockBambooLog)
                        return;
                    if (state1.getBlock() == this)
                        pathsToAdd.add(pos1.toImmutable());
                }
                evaluated.add(p1); // Evaluated
            }
            paths.addAll(pathsToAdd);
            paths.removeAll(evaluated);
        }

        world.setBlockToAir(pos);
    }
}