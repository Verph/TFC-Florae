package tfcflorae.world.worldgen;

import java.util.*;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.plants.*;
import tfcflorae.types.PlantsTFCF;

@ParametersAreNonnullByDefault
public class WorldGeneratorPlants implements IWorldGenerator
{
    private final WorldGenCaveVines plantCaveVines = new WorldGenCaveVines();
    private final WorldGenCaveCreepingVines plantCaveCreepingVines = new WorldGenCaveCreepingVines();
    private final WorldGenCaveMoss plantCaveMoss = new WorldGenCaveMoss();
    private final WorldGenCaveMushrooms plantCaveMushrooms = new WorldGenCaveMushrooms();
    private final WorldGenPlants plantGen;
    private int waterCount = 1;
    private int waterSeaCount = 1;
    private int hangingCount = 1;
    private int grassCount = 1;
    private int tallGrassCount = 1;
    private int tallCount = 1;
    private int epiphyteCount = 1;
    private int standardCount = 1;

    public WorldGeneratorPlants()
    {
        plantGen = new WorldGenPlants();

        for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
        {
            switch (plant.getPlantType())
            {
                case WATER:
                case TALL_WATER:
                    waterCount++;
                    break;
                case WATER_SEA:
                case TALL_WATER_SEA:
                    waterSeaCount++;
                    break;
                case HANGING:
                    hangingCount++;
                    break;
                case SHORT_GRASS:
                    grassCount++;
                    break;
                case TALL_GRASS:
                    tallGrassCount++;
                    break;
                case TALL_PLANT:
                    tallCount++;
                    break;
                case EPIPHYTE:
                    epiphyteCount++;
                    break;
                default:
                    standardCount++;
            }
        }
    }

    @Override
    public void generate(Random rng, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        final BlockPos chunkPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        ChunkPos forgeChunkPos = new ChunkPos(chunkPos); // actual ChunkPos instead of BlockPos, used for events
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rng, forgeChunkPos));

        ChunkDataTFC data = ChunkDataTFC.get(world, chunkPos);
        if (!data.isInitialized()) return;

        Biome b = world.getBiome(chunkPos);
        final float avgTemperature = ClimateTFC.getAvgTemp(world, chunkPos);
        final float rainfall = ChunkDataTFC.getRainfall(world, chunkPos);
        final float floraDensity = data.getFloraDensity(); // Use for various plant based decoration (tall grass, those vanilla jungle shrub things, etc.)
        final float floraDiversity = data.getFloraDiversity();

        // this.chunkPos = chunkPos;
        // todo: settings for all the rarities?

        for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
        {
            if (plant.isValidTempForWorldGen(avgTemperature) && plant.isValidRain(rainfall))
            {
                plantGen.setGeneratedPlant(plant);

                switch (plant.getPlantType())
                {
                    case WATER:
                    {
                        for (int i = rng.nextInt(Math.round(waterCount / floraDiversity)); i < (5 + floraDensity) * 15; i++)
                        {
                            BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                            plantGen.generate(world, rng, blockPos);
                        }
                        break;
                    }
                    case TALL_WATER:
                    {
                        for (int i = rng.nextInt(Math.round(waterCount / floraDiversity)); i < (5 + floraDensity) * 8; i++)
                        {
                            BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                            plantGen.generate(world, rng, blockPos);
                        }
                        break;
                    }
                    case WATER_SEA:
                    {
                        if (floraDensity >= 0.2f && floraDensity <= 0.6f && plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.RED_ALGAE) || plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.RED_SEA_WHIP) || plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.SEA_ANEMONE))
                        {
                            for (int i = rng.nextInt(Math.round(waterSeaCount / floraDiversity)); i < (3 + floraDensity); i++)
                            {
                                BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                                plantGen.generate(world, rng, blockPos);
                            }
                        }
                        else if (plant != TFCRegistries.PLANTS.getValue(PlantsTFCF.RED_ALGAE) || plant != TFCRegistries.PLANTS.getValue(PlantsTFCF.RED_SEA_WHIP) || plant != TFCRegistries.PLANTS.getValue(PlantsTFCF.SEA_ANEMONE))
                        {
                            for (int i = rng.nextInt(Math.round(waterSeaCount / floraDiversity)); i < (5 + floraDensity) * 15; i++)
                            {
                                BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                                plantGen.generate(world, rng, blockPos);
                            }
                        }
                        break;
                    }
                    case TALL_WATER_SEA:
                    {
                        if (floraDensity >= 0.2f && plant != TFCRegistries.PLANTS.getValue(PlantsTFCF.SEAGRASS))
                        {
                            for (int i = rng.nextInt(Math.round(waterSeaCount / floraDiversity)); i < (5 + floraDensity) * 8; i++)
                            {
                                BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                                plantGen.generate(world, rng, blockPos);
                            }
                        }
                        else if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.SEAGRASS))
                        {
                            for (int i = rng.nextInt(Math.round(waterSeaCount / floraDiversity)); i < (5 + floraDensity) * 15; i++)
                            {
                                BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                                plantGen.generate(world, rng, blockPos);
                            }
                        }
                        break;
                    }
                    case HANGING:
                    {
                        if (floraDensity >= 0.1f && rainfall >= 255f && ClimateTFC.getActualTemp(world, chunkPos) >= 20f && (
                            plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE) || 
                            plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.HANGING_VINE) || 
                            plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.JUNGLE_VINE) || 
                            plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.LIANA)))
                        {
                            for (float i = rng.nextInt(Math.round(hangingCount / floraDiversity)); i < (5 + floraDensity + floraDiversity) * 2; i++)
                            {
                                BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                                plantGen.generate(world, rng, blockPos);
                            }
                        }
                        else if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.BEARDED_MOSS) && (b == BiomesTFC.SWAMPLAND || b == BiomesTFC.LAKE))
                        {
                            for (float i = rng.nextInt(Math.round(hangingCount / floraDiversity)); i < (2 + floraDensity); i++)
                            {
                                BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                                plantGen.generate(world, rng, blockPos);
                            }
                        }
                        break;
                    }
                    case SHORT_GRASS:
                    {
                        if (rainfall >= 255f && ClimateTFC.getActualTemp(world, chunkPos) >= 20f)
                        {
                            for (int i = rng.nextInt(Math.round(grassCount / floraDiversity)); i < (5 + floraDensity) * 15; i++)
                            {
                                BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                                plantGen.generate(world, rng, blockPos);
                            }
                        }
                        break;
                    }
                    case TALL_GRASS:
                    {
                        if (rainfall >= 255f && ClimateTFC.getActualTemp(world, chunkPos) >= 20f)
                        {
                            for (int i = rng.nextInt(Math.round((tallGrassCount + 8) / floraDiversity)); i < (1 + floraDensity) * 8; i++)
                            {
                                BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                                plantGen.generate(world, rng, blockPos);
                            }
                        }
                        break;
                    }
                    case TALL_PLANT:
                    {
                        if (rainfall >= 255f && ClimateTFC.getActualTemp(world, chunkPos) >= 20f)
                        {
                            for (float i = rng.nextInt(Math.round((tallCount + 8) / floraDiversity)); i < (1 + floraDensity) * 3; i++)
                            {
                                BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                                plantGen.generate(world, rng, blockPos);
                            }
                        }
                        break;
                    }
                    case EPIPHYTE:
                    {
                        if (rainfall >= 255f && ClimateTFC.getActualTemp(world, chunkPos) >= 20f && plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.MONSTERA_EPIPHYTE))
                        {
                            for (float i = rng.nextInt(Math.round(epiphyteCount / floraDiversity)); i < (5 + floraDensity + floraDiversity) * 3; i++)
                            {
                                BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                                plantGen.generate(world, rng, blockPos);
                            }
                        }
                        break;
                    }
                    case STANDARD:
                    {
                        if (rainfall >= 255f && ClimateTFC.getActualTemp(world, chunkPos) >= 20f)
                        {
                            for (float i = rng.nextInt(Math.round((standardCount + 8) / floraDiversity)); i < (1 + floraDensity) * 3; i++)
                            {
                                BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                                plantGen.generate(world, rng, blockPos);
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rng, forgeChunkPos));
    }
}