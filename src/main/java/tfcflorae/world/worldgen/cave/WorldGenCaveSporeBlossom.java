package tfcflorae.world.worldgen.cave;

import java.util.Random;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.objects.blocks.plants.*;
import tfcflorae.types.PlantsTFCF;

@ParametersAreNonnullByDefault
public class WorldGenCaveSporeBlossom extends WorldGenerator
{
    private Plant plant;

    public void setGeneratedPlant(Plant plantIn)
    {
        this.plant = plantIn;
    }

	@Override
    public boolean generate(World worldIn, Random rng, BlockPos pos)
    {
        if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.SPORE_BLOSSOM))
        {
            BlockSporeBlossom plantBlock = BlockSporeBlossom.get(plant);

            for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, pos) / 4; ++i)
            {
                BlockPos blockpos = pos.add(rng.nextInt(7) - rng.nextInt(7), rng.nextInt(16), rng.nextInt(7) - rng.nextInt(7));

                if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                    plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                    pos.getY() < WorldTypeTFC.SEALEVEL - 3 &&
                    worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos) &&
                    plantBlock.canPlaceBlockAt(worldIn, blockpos))
                {
                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(worldIn, blockpos));
                    setBlockAndNotifyAdequately(worldIn, blockpos, plantBlock.getStateForWorldGen(worldIn, blockpos).withProperty(BlockSporeBlossom.AGE, plantAge));
                }
            }
            return true;
        }
        return false;
    }
}