package tfcflorae.world.worldgen;

import java.util.Random;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.plants.*;
import tfcflorae.types.PlantsTFCF;

@ParametersAreNonnullByDefault
public class WorldGenUnderground extends WorldGenerator
{
	@Override
    public boolean generate(World worldIn, Random rng, BlockPos pos)
    {
        int chance = rng.nextInt(4);
        if (chance == 1)
        {
            return generateMushroom(worldIn, rng, pos);
        }
        if (chance == 1)
        {
            return generateHangingCreeping(worldIn, rng, pos);
        }
        if (chance == 2)
        {
            return generateHanging(worldIn, rng, pos);
        }
        if (chance == 3)
        {
            return generateCreeping(worldIn, rng, pos);
        }
        return false;
    }

    private boolean generateMushroom(World worldIn, Random rng, BlockPos pos)
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
                    mushroomBlock.canBlockStay(worldIn, blockpos, state))
                {
                    setBlockAndNotifyAdequately(worldIn, blockpos, state);
                    return true;
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
                    mushroomBlock.canBlockStay(worldIn, blockpos, state))
                {
                    setBlockAndNotifyAdequately(worldIn, blockpos, state);
                    return true;
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
                    mushroomBlock.canBlockStay(worldIn, blockpos, state))
                {
                    setBlockAndNotifyAdequately(worldIn, blockpos, state);
                    return true;
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
                    mushroomBlock.canBlockStay(worldIn, blockpos, state))
                {
                    setBlockAndNotifyAdequately(worldIn, blockpos, state);
                    return true;
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
                    mushroomBlock.canBlockStay(worldIn, blockpos, state))
                {
                    setBlockAndNotifyAdequately(worldIn, blockpos, state);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean generateHangingCreeping(World worldIn, Random rng, BlockPos pos)
    {
        float avgTemperature = ClimateTFC.getAvgTemp(worldIn, pos);
        float rainfall = ChunkDataTFC.getRainfall(worldIn, pos);

        for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
        {
            if (plant.isValidTempForWorldGen(avgTemperature) && plant.isValidRain(rainfall))
            {
                if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.BEARDED_MOSS) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.HANGING_VINE) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.JUNGLE_VINE))
                {
                    BlockHangingCreepingPlantTFCF plantBlock = BlockHangingCreepingPlantTFCF.get(plant);
                    IBlockState state = plantBlock.getDefaultState();

                    for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, pos) / 4; ++i)
                    {
                        BlockPos blockpos = pos.add(rng.nextInt(7) - rng.nextInt(7), rng.nextInt(16), rng.nextInt(7) - rng.nextInt(7));

                        int j = 1 + rng.nextInt(plant.getMaxHeight());

                        for (int k = 0; k < j; ++k)
                        {
                            if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.down(k))) &&
                                worldIn.isAirBlock(blockpos.down(k)) &&
                                pos.getY() < WorldTypeTFC.SEALEVEL - 3 &&
                                plantBlock.canBlockStay(worldIn, blockpos.down(k), state))
                            {
                                int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(worldIn, blockpos));
                                setBlockAndNotifyAdequately(worldIn, blockpos.down(k), state.withProperty(BlockHangingCreepingPlantTFCF.AGE, plantAge));
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean generateHanging(World worldIn, Random rng, BlockPos pos)
    {
        float avgTemperature = ClimateTFC.getAvgTemp(worldIn, pos);
        float rainfall = ChunkDataTFC.getRainfall(worldIn, pos);

        for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
        {
            if (plant.isValidTempForWorldGen(avgTemperature) && plant.isValidRain(rainfall))
            {
                if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.BEARDED_MOSS) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.HANGING_VINE) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.JUNGLE_VINE) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.LIANA))
                {
                    BlockHangingPlantTFCF plantBlock = BlockHangingPlantTFCF.get(plant);
                    IBlockState state = plantBlock.getDefaultState();

                    for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, pos) / 4; ++i)
                    {
                        BlockPos blockpos = pos.add(rng.nextInt(7) - rng.nextInt(7), rng.nextInt(16), rng.nextInt(7) - rng.nextInt(7));

                        int j = 1 + rng.nextInt(plant.getMaxHeight());

                        for (int k = 0; k < j; ++k)
                        {
                            if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.down(k))) &&
                                worldIn.isAirBlock(blockpos.down(k)) &&
                                pos.getY() < WorldTypeTFC.SEALEVEL - 3 &&
                                plantBlock.canBlockStay(worldIn, blockpos.down(k), state))
                            {
                                int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(worldIn, blockpos));
                                setBlockAndNotifyAdequately(worldIn, blockpos.down(k), state.withProperty(BlockHangingPlantTFCF.AGE, plantAge));
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean generateCreeping(World worldIn, Random rng, BlockPos pos)
    {
        float avgTemperature = ClimateTFC.getAvgTemp(worldIn, pos);
        float rainfall = ChunkDataTFC.getRainfall(worldIn, pos);

        for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
        {
            if (plant.isValidTempForWorldGen(avgTemperature) && plant.isValidRain(rainfall))
            {
                if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.TACKWEED) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.TAKAKIA) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.IVY) || 
                    plant == TFCRegistries.PLANTS.getValue(DefaultPlants.MORNING_GLORY) || 
                    plant == TFCRegistries.PLANTS.getValue(DefaultPlants.MOSS) || 
                    plant == TFCRegistries.PLANTS.getValue(DefaultPlants.REINDEER_LICHEN))
                {
                    BlockCreepingPlantTFCF plantBlock = BlockCreepingPlantTFCF.get(plant);
                    IBlockState state = plantBlock.getDefaultState();

                    for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, pos) / 16; ++i)
                    {
                        BlockPos blockpos = pos.add(rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4));

                        if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.isAirBlock(blockpos) &&
                            pos.getY() < WorldTypeTFC.SEALEVEL - 3 &&
                            plantBlock.canBlockStay(worldIn, blockpos, state))
                        {
                            int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(worldIn, blockpos));
                            setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockCreepingPlantTFCF.AGE, plantAge));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}