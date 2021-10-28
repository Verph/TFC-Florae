package tfcflorae.world.worldgen;

import java.util.*;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

@ParametersAreNonnullByDefault
public class WorldGeneratorPlants implements IWorldGenerator
{
    private final WorldGenPlants plantGen;
    private int waterCount = 1;
    private int waterSeaCount = 1;

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
                default:
                    return;
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
                        if (floraDensity >= 0.2f)
                        {
                            for (int i = rng.nextInt(Math.round(waterCount / floraDiversity)); i < (5 + floraDensity) * 8; i++)
                            {
                                BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                                plantGen.generate(world, rng, blockPos);
                            }
                        }
                        break;
                    }
                    case WATER_SEA:
                    {
                        for (int i = rng.nextInt(Math.round(waterSeaCount / floraDiversity)); i < (5 + floraDensity) * 15; i++)
                        {
                            BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                            plantGen.generate(world, rng, blockPos);
                        }
                        break;
                    }
                    case TALL_WATER_SEA:
                    {
                        if (floraDensity >= 0.2f)
                        {
                            for (int i = rng.nextInt(Math.round(waterSeaCount / floraDiversity)); i < (5 + floraDensity) * 8; i++)
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