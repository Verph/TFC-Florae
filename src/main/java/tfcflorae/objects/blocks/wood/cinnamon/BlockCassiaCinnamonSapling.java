package tfcflorae.objects.blocks.wood.cinnamon;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.api.util.IGrowingPlant;
import net.dries007.tfc.objects.blocks.wood.BlockSaplingTFC;

import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.types.TreesTFCF;
import tfcflorae.world.worldgen.WorldGenCinnamon;

public class BlockCassiaCinnamonSapling extends BlockSaplingTFC implements IGrowingPlant
{
    public BlockCassiaCinnamonSapling()
    {
        super(TreesTFCF.CASSIA_CINNAMON_TREE);
        setSoundType(SoundType.WOOD);
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState blockState)
    {
        for (int air = 1; air < 15; air++)
        {
            if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                return;
        }
        int height = 7 + rand.nextInt(5);
        IBlockState leaves = BlocksTFCF.CASSIA_CINNAMON_LEAVES.getDefaultState();
        for (int trunk = 0; trunk < height; trunk++)
        {
            BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
            world.setBlockState(trunkPos, BlocksTFCF.CASSIA_CINNAMON_LOG.getDefaultState());
            if (trunk < 3)
                continue;
            for (EnumFacing d : EnumFacing.HORIZONTALS)
            {
                world.setBlockState(trunkPos.offset(d, 1), leaves);
                if (rand.nextFloat() < 1 - (float) trunk / height)
                {
                    world.setBlockState(trunkPos.offset(d, 2), leaves);
                }
                else { continue; }
                if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                    world.setBlockState(trunkPos.offset(d, 3), leaves);
            }
        }
        world.setBlockState(pos.offset(EnumFacing.UP, height), leaves);
    }

    @Override
    public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos)
    {
        return GrowthStatus.GROWING;
    }
}