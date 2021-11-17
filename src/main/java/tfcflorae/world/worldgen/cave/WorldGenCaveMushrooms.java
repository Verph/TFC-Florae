package tfcflorae.world.worldgen.cave;

import java.util.Random;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.plants.*;

@ParametersAreNonnullByDefault
public class WorldGenCaveMushrooms extends WorldGenerator
{
	@Override
    public boolean generate(World worldIn, Random rng, BlockPos pos)
    {
        int chance = rng.nextInt(5);
        if (chance == 0)
        {
            BlockCaveMushroom mushroomBlock = BlocksTFCF.BLUESHROOM;
            IBlockState state = mushroomBlock.getDefaultState();

            for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, pos) / 16; ++i)
            {
                BlockPos blockpos = pos.add(rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4));

                if (worldIn.isAirBlock(blockpos) &&
                    pos.getY() < WorldTypeTFC.SEALEVEL - 3 &&
                    worldIn.getLightFor(EnumSkyBlock.SKY, blockpos) < 14 && 
                    !worldIn.canSeeSky(blockpos) &&
                    mushroomBlock.canBlockStay(worldIn, blockpos, state))
                {
                    setBlockAndNotifyAdequately(worldIn, blockpos, state);
                }
            }
        }
        else if (chance == 1)
        {
            BlockCaveMushroom mushroomBlock = BlocksTFCF.GLOWSHROOM;
            IBlockState state = mushroomBlock.getDefaultState();

            for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, pos) / 16; ++i)
            {
                BlockPos blockpos = pos.add(rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4));

                if (worldIn.isAirBlock(blockpos) &&
                    pos.getY() < WorldTypeTFC.SEALEVEL - 3 &&
                    worldIn.getLightFor(EnumSkyBlock.SKY, blockpos) < 14 && 
                    !worldIn.canSeeSky(blockpos) &&
                    mushroomBlock.canBlockStay(worldIn, blockpos, state))
                {
                    setBlockAndNotifyAdequately(worldIn, blockpos, state);
                }
            }
        }
        else if (chance == 2)
        {
            BlockCaveMushroom mushroomBlock = BlocksTFCF.MAGMA_SHROOM;
            IBlockState state = mushroomBlock.getDefaultState();

            for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, pos) / 16; ++i)
            {
                BlockPos blockpos = pos.add(rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4));

                if (worldIn.isAirBlock(blockpos) &&
                    pos.getY() < WorldTypeTFC.SEALEVEL - 3 &&
                    worldIn.getLightFor(EnumSkyBlock.SKY, blockpos) < 14 && 
                    !worldIn.canSeeSky(blockpos) &&
                    mushroomBlock.canBlockStay(worldIn, blockpos, state))
                {
                    setBlockAndNotifyAdequately(worldIn, blockpos, state);
                }
            }
        }
        else if (chance == 3)
        {
            BlockCaveMushroom mushroomBlock = BlocksTFCF.POISON_SHROOM;
            IBlockState state = mushroomBlock.getDefaultState();

            for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, pos) / 16; ++i)
            {
                BlockPos blockpos = pos.add(rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4));

                if (worldIn.isAirBlock(blockpos) &&
                    pos.getY() < WorldTypeTFC.SEALEVEL - 3 &&
                    worldIn.getLightFor(EnumSkyBlock.SKY, blockpos) < 14 && 
                    !worldIn.canSeeSky(blockpos) &&
                    mushroomBlock.canBlockStay(worldIn, blockpos, state))
                {
                    setBlockAndNotifyAdequately(worldIn, blockpos, state);
                }
            }
        }
        else if (chance == 4)
        {
            BlockCaveMushroom mushroomBlock = BlocksTFCF.SULPHUR_SHROOM;
            IBlockState state = mushroomBlock.getDefaultState();

            for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, pos) / 16; ++i)
            {
                BlockPos blockpos = pos.add(rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4));

                if (worldIn.isAirBlock(blockpos) &&
                    pos.getY() < WorldTypeTFC.SEALEVEL - 3 &&
                    worldIn.getLightFor(EnumSkyBlock.SKY, blockpos) < 14 && 
                    !worldIn.canSeeSky(blockpos) &&
                    mushroomBlock.canBlockStay(worldIn, blockpos, state))
                {
                    setBlockAndNotifyAdequately(worldIn, blockpos, state);
                }
            }
        }
        return true;
    }
}