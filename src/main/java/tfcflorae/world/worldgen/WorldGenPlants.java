package tfcflorae.world.worldgen;

import scala.reflect.internal.Trees.Return;

import java.util.Random;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.gen.feature.WorldGenerator;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.blocks.plants.*;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.objects.blocks.plants.*;
import tfcflorae.objects.blocks.plants.BlockPlant.BlockPlantDummy1;
import tfcflorae.objects.blocks.plants.BlockPlant.BlockPlantTFCF;
import tfcflorae.types.PlantsTFCF;

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
            case HANGING:
            {
                if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.BEARDED_MOSS) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.HANGING_VINE) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.JUNGLE_VINE) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.LIANA))
                {
                    BlockHangingPlantTFCF plantBlock = BlockHangingPlantTFCF.get(plant);
                    IBlockState state = plantBlock.getDefaultState();

                    for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 4; ++i)
                    {
                        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(16), rand.nextInt(7) - rand.nextInt(7));

                        int j = 1 + rand.nextInt(plant.getMaxHeight());

                        for (int k = 0; k < j; ++k)
                        {
                            if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.down(k))) &&
                                worldIn.isAirBlock(blockpos.down(k)) &&
                                plantBlock.canBlockStay(worldIn, blockpos.down(k), state) &&
                                plantBlock.canPlaceBlockAt(worldIn, blockpos.down(k)))
                            {
                                int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                                setBlockAndNotifyAdequately(worldIn, blockpos.down(k), state.withProperty(BlockHangingPlantTFCF.AGE, plantAge));
                            }
                        }
                    }
                    break;
                }
            }
            case WATER:
            case WATER_SEA:
            {
                BlockWaterPlantTFCF plantBlock = BlockWaterPlantTFCF.get(plant);
                IBlockState state = plantBlock.getDefaultState();
                IBlockState water = plant.getWaterType();

                int depth = plant.getValidWaterDepth(worldIn, position, water);
                if (depth == -1) return false;

                BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), -depth + 1, rand.nextInt(7) - rand.nextInt(7));

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
            case SHORT_GRASS:
            {
                BlockShortGrassTFC plantBlock = BlockShortGrassTFC.get(plant);
                IBlockState state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 4; ++i)
                {
                    BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                    if (plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                        plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                        worldIn.isAirBlock(blockpos) &&
                        plantBlock.canBlockStay(worldIn, blockpos, state))
                    {
                        int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                        setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockShortGrassTFC.AGE, plantAge));
                    }
                }
                break;
            }
            case TALL_GRASS:
            {
                if (plant != TFCRegistries.PLANTS.getValue(PlantsTFCF.SAWGRASS))
                {
                    BlockTallGrassTFC plantBlock = BlockTallGrassTFC.get(plant);
                    IBlockState state = plantBlock.getDefaultState();

                    for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 16; ++i)
                    {
                        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                        int j = 1 + rand.nextInt(plant.getMaxHeight());

                        for (int k = 0; k < j; ++k)
                        {
                            if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                                worldIn.isAirBlock(blockpos.up(k)) &&
                                plantBlock.canBlockStay(worldIn, blockpos.up(k), state))
                            {
                                int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                                setBlockAndNotifyAdequately(worldIn, blockpos.up(k), state.withProperty(BlockShortGrassTFC.AGE, plantAge));
                            }
                        }
                    }
                }
                if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.SAWGRASS))
                {
                    BlockTallGrassWater plantBlock = BlockTallGrassWater.get(plant);
                    IBlockState state = plantBlock.getDefaultState();
                    IBlockState water = plant.getWaterType();
    
                    for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 16; ++i)
                    {
                        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), 0, rand.nextInt(7) - rand.nextInt(7));
    
                        int j = 1 + rand.nextInt(plant.getMaxHeight());
    
                        for (int k = 0; k < j; ++k)
                        {
                            if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                                worldIn.isAirBlock(blockpos.up(k)) &&
                                plantBlock.canPlaceBlockAt(worldIn, blockpos.up(k)) &&
                                plant.isValidFloatingWaterDepth(worldIn, blockpos.up(k), water) &&
                                plantBlock.canBlockStay(worldIn, blockpos.up(k), state))
                            {
                                int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                                setBlockAndNotifyAdequately(worldIn, blockpos.up(k), state.withProperty(BlockTallGrassWater.AGE, plantAge));
                            }
                        }
                    }
                }
                break;
            }
            case TALL_PLANT:
            {
                BlockTallPlantTFC plantBlock = BlockTallPlantTFC.get(plant);
                IBlockState state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 16; ++i)
                {
                    BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                    int j = 1 + rand.nextInt(plant.getMaxHeight());

                    for (int k = 0; k < j; ++k)
                    {
                        if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                            worldIn.isAirBlock(blockpos.up(k)) &&
                            plantBlock.canBlockStay(worldIn, blockpos.up(k), state))
                        {
                            int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                            setBlockAndNotifyAdequately(worldIn, blockpos.up(k), state.withProperty(BlockTallPlantTFC.AGE, plantAge));
                        }
                    }
                }
                break;
            }
            case EPIPHYTE:
            {
                if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.SPORE_BLOSSOM))
                {
                    BlockSporeBlossom plantBlock = BlockSporeBlossom.get(plant);
    
                    for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 4; ++i)
                    {
                        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(16), rand.nextInt(7) - rand.nextInt(7));
    
                        if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos) &&
                            plantBlock.canPlaceBlockAt(worldIn, blockpos))
                        {
                            int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                            setBlockAndNotifyAdequately(worldIn, blockpos, plantBlock.getStateForWorldGen(worldIn, blockpos).withProperty(BlockSporeBlossom.AGE, plantAge));
                        }
                    }
                    break;
                }
                else
                {
                    BlockEpiphyteTFC plantBlock = BlockEpiphyteTFC.get(plant);

                    for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 4; ++i)
                    {
                        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(16), rand.nextInt(7) - rand.nextInt(7));
    
                        if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos) &&
                            plantBlock.canPlaceBlockAt(worldIn, blockpos))
                        {
                            int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                            setBlockAndNotifyAdequately(worldIn, blockpos, plantBlock.getStateForWorldGen(worldIn, blockpos).withProperty(BlockEpiphyteTFC.AGE, plantAge));
                        }
                    }
                    break;
                }
            }
            case DRY:
            {
                BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                IBlockState state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 4; ++i)
                {
                    BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                    if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                        plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                        worldIn.isAirBlock(blockpos) &&
                        plantBlock.canBlockStay(worldIn, blockpos, state))
                    {
                        int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                        setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockPlantTFC.AGE, plantAge));
                    }
                }
                break;
            }
            default:
            {
                BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                IBlockState state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 16; ++i)
                {
                    BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                    if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                        plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                        worldIn.isAirBlock(blockpos) &&
                        plantBlock.canBlockStay(worldIn, blockpos, state))
                    {
                        int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                        setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockPlantTFC.AGE, plantAge));
                    }
                }
            }
        }
        return true;
    }
}
