package tfcflorae.world.worldgen.structures;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.ConfigTFCF;

public class WorldGenStructures implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (chunkGenerator instanceof ChunkGenTFC && world.provider.getDimension() == 0)
        {
			final int x = (chunkX << 4) + random.nextInt(16) + 8;
			final int z = (chunkZ << 4) + random.nextInt(16) + 8;
			final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
			final Biome b = world.getBiome(pos);
			ChunkDataTFC data = ChunkDataTFC.get(world, pos);
            IBlockState down = world.getBlockState(pos.down());

            if (ConfigTFCF.General.STRUCTURES.activateStructureGeneration)
            {
				if (!(world.getBlockState(pos).getBlock() == ChunkGenTFC.FRESH_WATER.getBlock() || world.getBlockState(pos).getBlock() == ChunkGenTFC.SALT_WATER.getBlock() || world.getBlockState(pos).getBlock() == ChunkGenTFC.HOT_WATER.getBlock() || b == BiomesTFC.OCEAN || b == BiomesTFC.DEEP_OCEAN || b == BiomesTFC.LAKE || b == BiomesTFC.RIVER || b == BiomesTFC.BEACH || b == BiomesTFC.GRAVEL_BEACH))
				{
					if (data.isInitialized() && data.getRainfall() >= 100f && random.nextInt(ConfigTFCF.General.STRUCTURES.spawnChanceRuins) == 0)
					{
						int chance = random.nextInt(7);

						if (chance == 0)
							generateStructure(StructureList.STONE_CIRCLE_RUIN, world, random, pos);
						else if (chance == 1)
							generateStructure(StructureList.STONE_CIRCLE_RUIN_A, world, random, pos);
						else if (chance == 2)
							generateStructure(StructureList.STONE_CIRCLE_RUIN_B, world, random, pos);
						else if (chance == 3)
							generateStructure(StructureList.RUIN_HOUSE_1A, world, random, pos);
						else if (chance == 4)
							generateStructure(StructureList.RUIN_HOUSE_1B, world, random, pos);
						else if (chance == 5)
							generateStructure(StructureList.RUIN_TEMPLE_1A, world, random, pos);
						else if (chance == 6)
							generateStructure(StructureList.RUIN_TEMPLE_1B, world, random, pos);
					}
					if (data.isInitialized() && data.getRainfall() >= 320f && data.getFloraDensity() >= 0.3f && data.getAverageTemp() >= 9f && random.nextInt(ConfigTFCF.General.STRUCTURES.spawnChanceMoai) == 0)
					{
						int chance = random.nextInt(6);

						if (chance == 0)
							generateStructure(StructureList.MOAI_1, world, random, pos);
						else if (chance == 1)
							generateStructure(StructureList.MOAI_1A, world, random, pos);
						else if (chance == 2)
							generateStructure(StructureList.MOAI_1B, world, random, pos);
						else if (chance == 3)
							generateStructure(StructureList.MOAI_2, world, random, pos);
						else if (chance == 4)
							generateStructure(StructureList.MOAI_2A, world, random, pos);
						else if (chance == 5)
							generateStructure(StructureList.MOAI_2B, world, random, pos);
					}
					if (data.isInitialized() && data.getRainfall() >= 320f && data.getFloraDensity() >= 0.2f && data.getAverageTemp() >= 13f && random.nextInt(ConfigTFCF.General.STRUCTURES.spawnChanceMaya) == 0)
					{
						generateStructure(StructureList.MAYAN_TEMPLE_1A, world, random, pos);
					}
				}
            }
        }
	}

	private void generateStructure(WorldGenerator generator, World world, Random random, BlockPos pos)
    {
        generator.generate(world, random, pos);
	}
}