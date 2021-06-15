package tfcflorae.world.worldgen;

import java.util.*;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraft.world.gen.structure.template.TemplateManager;

import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.te.TEPlacedItemFlat;
import net.dries007.tfc.api.util.ITreeGenerator;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.biomes.BiomeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

public class WorldGeneratorTrees implements IWorldGenerator
{
    private final WorldGenCinnamon cinnamon_trees = new WorldGenCinnamon();
    private final WorldGenBamboo bamboo_trees = new WorldGenBamboo();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, chunkBlockPos);
        final Biome b = world.getBiome(chunkBlockPos);
        float temperature = ClimateTFC.getAvgTemp(world, chunkBlockPos);
        float gauss = 2f * (float)random.nextGaussian();
        List<Tree> trees = chunkData.getValidTrees();

        BlockPos center = new BlockPos(chunkX * 16 + 8, world.getHeight(chunkX * 16 + 8, chunkZ * 16 + 8), chunkZ * 16 + 8);

        cinnamon_trees.generate(world, random, center);
        //bamboo_trees.generate(world, random, center);

        trees.removeIf(t -> !t.hasBushes());

        // Dense foliage chaparral/shrubland forests in dry & sparsely populated mountain regions
        // Similarly to Mediterranean and Californian areas 
        if ((b == BiomesTFC.MOUNTAINS || b == BiomesTFC.MOUNTAINS_EDGE || b == BiomesTFC.HIGH_HILLS || b == BiomesTFC.HIGH_HILLS_EDGE) && (temperature >= 4 + gauss))
        {
            genBush(random, chunkX, chunkZ, world, chunkData, 0.0f, 0.3f, 60f + gauss, 200f + gauss, 4 + random.nextInt(10), trees);
        }

        // Mid-dense foliage chaparral/shrubland forests in dry & sparsely populated hilly landscapes
        // Similarly to South African areas
        if ((b == BiomesTFC.ROLLING_HILLS || b == BiomesTFC.HIGH_PLAINS) && (temperature >= 1 + gauss))
        {
            genBush(random, chunkX, chunkZ, world, chunkData, 0.1f, 0.3f, 70f + gauss, 230f + gauss, 3 + random.nextInt(7), trees);
        }

        // Mid-dense foliage chaparral/shrubland forests in temperate regions
        // Similarly to steppes across Eurasian regions
        if ((b == BiomesTFC.ROLLING_HILLS || b == BiomesTFC.PLAINS || b == BiomesTFC.HIGH_PLAINS) && (temperature <= 10 + gauss))
        {
            genBush(random, chunkX, chunkZ, world, chunkData, 0.1f, 0.3f, 150f + gauss, 380f + gauss, 1 + random.nextInt(7), trees);
        }

        // More foliage bushes to woodlands
        if (!(b == BiomesTFC.OCEAN || b == BiomesTFC.DEEP_OCEAN))
        {
            genBush(random, chunkX, chunkZ, world, chunkData, 0.3f, 1f, 150f + gauss, 500f - gauss, 1 + random.nextInt(5), trees);
        }

        // Sparse foliage were it's otherwise just completely barren and boring...
        if (!(b == BiomesTFC.OCEAN || b == BiomesTFC.DEEP_OCEAN))
        {
            genBush(random, chunkX, chunkZ, world, chunkData, 0.0f, 0.2f, 70f + gauss, 500f - gauss, 0 + random.nextInt(5), trees);
        }
    }

    private void genBush(Random random, int chunkX, int chunkZ, World world, ChunkDataTFC chunkData, float minFlora, float maxFlora, float minRainfall, float maxRainfall, int numBushes, List<Tree> trees)
    {
        final TemplateManager manager = ((WorldServer) world).getStructureTemplateManager();
        final float density = chunkData.getFloraDensity();
        final float rainfall = chunkData.getRainfall();

        if (density > minFlora && density < maxFlora && rainfall > minRainfall && rainfall < maxRainfall && !trees.isEmpty())
        {
            for (int i = 0; i < numBushes; i++)
            {
                final int x = chunkX * 16 + random.nextInt(16) + 8;
                final int z = chunkZ * 16 + random.nextInt(16) + 8;
                final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
                final Tree tree = getTree(trees, density, random);
                ITreeGenerator bushGen = tree.getBushGen();
                if (bushGen != null && tree.hasBushes() && bushGen.canGenerateTree(world, pos, tree))
                {
                    bushGen.generateTree(manager, world, pos, tree, random, true);
                }
            }
        }
    }

    private Tree getTree(List<Tree> trees, float density, Random random)
    {
        if (trees.size() == 1 || random.nextFloat() < 0.8f - density * 0.4f)
        {
            return trees.get(0);
        }
        return trees.get(1 + random.nextInt(trees.size() - 1));
    }
}