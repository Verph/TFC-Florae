package tfcflorae.world.worldgen;

import scala.reflect.internal.Trees.Return;

import java.util.Random;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.gen.feature.WorldGenerator;

import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.util.climate.ClimateTFC;

import tfcflorae.objects.blocks.plants.*;

@ParametersAreNonnullByDefault
public class WorldGenPlants extends WorldGenerator
{
    private Plant plant;

    public void setGeneratedPlant(Plant plantIn)
    {
        this.plant = plantIn;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        switch (plant.getPlantType())
        {
            case WATER:
            case WATER_SEA:
            {
                BlockWaterPlantTFCF plantBlock = BlockWaterPlantTFCF.get(plant);
                IBlockState state = plantBlock.getDefaultState();
                IBlockState water = plant.getWaterType();

                int depth = plant.getValidWaterDepth(worldIn, position, water);
                if (depth == -1) return false;

                BlockPos blockpos = position.add(0, -depth + 1, 0);

                if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                    plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                    plantBlock.canPlaceBlockAt(worldIn, blockpos))
                {
                    int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                    setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockWaterPlantTFCF.AGE, plantAge));
                }
                break;
            }
            case TALL_WATER:
            case TALL_WATER_SEA:
            {
                BlockTallWaterPlantTFCF plantBlock = BlockTallWaterPlantTFCF.get(plant);
                IBlockState state = plantBlock.getDefaultState();
                IBlockState water = plant.getWaterType();

                int depth = plant.getValidWaterDepth(worldIn, position, water);
                if (depth == -1) return false;
                BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), -depth + 1, rand.nextInt(7) - rand.nextInt(7));

                int j = 1 + rand.nextInt(plant.getMaxHeight());

                for (int k = 0; k < j; ++k)
                {
                    if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                        plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                        plantBlock.canPlaceBlockAt(worldIn, blockpos.up(k)))
                    {
                        int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                        setBlockAndNotifyAdequately(worldIn, blockpos.up(k), state.withProperty(BlockTallWaterPlantTFCF.AGE, plantAge));
                        if (rand.nextInt(4) < plantAge && plantBlock.canGrow(worldIn, blockpos, state, worldIn.isRemote))
                            setBlockAndNotifyAdequately(worldIn, blockpos.up(k), state);
                    }
                }
                break;
            }
            default:
                return false;
        }
        return true;
    }
}
