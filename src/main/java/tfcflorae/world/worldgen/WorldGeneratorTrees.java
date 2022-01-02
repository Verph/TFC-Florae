package tfcflorae.world.worldgen;

import java.util.*;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.wood.BlockJoshuaTreeFlower;
import tfcflorae.types.TreesTFCF;
import tfcflorae.world.worldgen.structures.StructureGeneratorCorals;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.te.TEPlacedItemFlat;
import net.dries007.tfc.api.util.ITreeGenerator;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
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
        BlockPos chunkPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, chunkPos);
        final Biome b = world.getBiome(chunkPos);
        final TemplateManager manager = ((WorldServer) world).getStructureTemplateManager();
        final float avgTemperature = ClimateTFC.getAvgTemp(world, chunkPos);
        final float rainfall = ChunkDataTFC.getRainfall(world, chunkPos);
        final float diversity = chunkData.getFloraDiversity();
        final float density = chunkData.getFloraDensity();
        float gauss = 2f * (float)random.nextGaussian();

        List<Tree> trees = chunkData.getValidTrees();
        Collections.rotate(trees, -(int) (diversity * (trees.size() - 1f)));

        BlockPos center = new BlockPos(chunkX * 16 + 8, world.getHeight(chunkX * 16 + 8, chunkZ * 16 + 8), chunkZ * 16 + 8);

        cinnamon_trees.generate(world, random, center);
        bamboo_trees.generate(world, random, center);

        trees.removeIf(t -> !t.hasBushes());

        // Dense foliage chaparral/shrubland forests in dry & sparsely populated mountain regions
        // Similarly to Mediterranean and Californian areas 
        if ((b == BiomesTFC.MOUNTAINS || b == BiomesTFC.MOUNTAINS_EDGE || b == BiomesTFC.HIGH_HILLS || b == BiomesTFC.HIGH_HILLS_EDGE) && (avgTemperature >= 4 + gauss))
        {
            genBush(random, chunkX, chunkZ, world, chunkData, 0.0f, 0.3f, 60f + gauss, 200f + gauss, 4 + random.nextInt(10), trees);
        }

        // Mid-dense foliage chaparral/shrubland forests in dry & sparsely populated hilly landscapes
        // Similarly to South African areas
        if ((b == BiomesTFC.ROLLING_HILLS || b == BiomesTFC.HIGH_PLAINS) && (avgTemperature >= 1 + gauss))
        {
            genBush(random, chunkX, chunkZ, world, chunkData, 0.0f, 0.3f, 70f + gauss, 230f + gauss, 4 + random.nextInt(9), trees);
        }

        // Mid-dense foliage chaparral/shrubland forests in temperate regions
        // Similarly to steppes across Eurasian regions
        if ((b == BiomesTFC.ROLLING_HILLS || b == BiomesTFC.FIELDS || b == BiomesTFC.FLATLANDS || b == BiomesTFC.PLAINS || b == BiomesTFC.HIGH_PLAINS) && (avgTemperature <= 10 + gauss))
        {
            genBush(random, chunkX, chunkZ, world, chunkData, 0.0f, 0.3f, 150f + gauss, 380f + gauss, 1 + random.nextInt(7), trees);
        }

        // More foliage bushes to woodlands
        if (!(b == BiomesTFC.OCEAN || b == BiomesTFC.DEEP_OCEAN))
        {
            genBush(random, chunkX, chunkZ, world, chunkData, 0.3f, 1f, 150f + gauss, 500f - gauss, 1 + random.nextInt(5), trees);
        }

        // Jungle Foliage
        if (!(b == BiomesTFC.OCEAN || b == BiomesTFC.DEEP_OCEAN) && (avgTemperature >= 10 + gauss))
        {
            genBush(random, chunkX, chunkZ, world, chunkData, 0.3f, 1f, 150f + gauss, 500f - gauss, 5 + random.nextInt(10), trees);
        }

        // Sparse foliage were it's otherwise just completely barren and boring...
        if (!(b == BiomesTFC.OCEAN || b == BiomesTFC.DEEP_OCEAN))
        {
            genBush(random, chunkX, chunkZ, world, chunkData, 0.0f, 0.2f, 260f + gauss, 500f - gauss, 0 + random.nextInt(5), trees);
        }

        {
            int treesPerChunk = (int) (density * 12 - 2);
            for (int i = random.nextInt(Math.round(1 / diversity)); i < (1 + density) * treesPerChunk; i++)
            {
				final int x = (chunkX << 4) + random.nextInt(16) + 8;
				final int z = (chunkZ << 4) + random.nextInt(16) + 8;
				BlockPos blockPos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
				IBlockState down = world.getBlockState(blockPos.down());
                Biome b1 = world.getBiome(blockPos);
                //BlockPos blockPos = world.getHeight(chunkPos.add(random.nextInt(16) + 8, (random.nextInt(7) - random.nextInt(7)) * -1, random.nextInt(16) + 8));

                if ((BlocksTFC.isGround(down) || BlocksTFCF.isGround(down) || world.getBlockState(blockPos).getBlock() == ChunkGenTFC.FRESH_WATER.getBlock()) && b1 == BiomesTFC.BAYOU)
                {
                    //if (TFCRegistries.TREES.getValue(TreesTFCF.BALD_CYPRESS).isValidLocation(avgTemperature, rainfall, density))
                    if (10f <= avgTemperature && 38f >= avgTemperature && 180f <= rainfall && 500f >= rainfall && blockPos.getY() >= WorldTypeTFC.SEALEVEL - 8)
                    {
                        int randomTree = random.nextInt(13) + 1;
                        StructureGeneratorCorals gen = new StructureGeneratorCorals("bald_cypress/" + randomTree);
                        generateStructure(gen, world, random, blockPos);

                        //TFCRegistries.TREES.getValue(TreesTFCF.BALD_CYPRESS).makeTree(manager, world, blockPos, random, true);
                        //TFCFlorae.getLog().warn("TFCFlorae: Bald Cypress attempted to generate at " + "X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ());
                    }
                }
            }
        }
        {
            int treesPerChunk = (int) (density * 12 - 2);
            for (int i = random.nextInt(Math.round(1 / diversity)); i < (1 + density) * treesPerChunk; i++)
            {
				final int x = (chunkX << 4) + random.nextInt(16) + 8;
				final int z = (chunkZ << 4) + random.nextInt(16) + 8;
				BlockPos blockPos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
				IBlockState down = world.getBlockState(blockPos.down());
                final Biome b1 = world.getBiome(blockPos);
                //BlockPos blockPos = world.getHeight(chunkPos.add(random.nextInt(16) + 8, (random.nextInt(7) - random.nextInt(7)) * -1, random.nextInt(16) + 8));

                if ((BlocksTFC.isGround(down) || BlocksTFCF.isGround(down) || world.getBlockState(blockPos).getBlock() == ChunkGenTFC.SALT_WATER.getBlock()) && b1 == BiomesTFC.MANGROVE)
                {
                    //if (TFCRegistries.TREES.getValue(TreesTFCF.MANGROVE).isValidLocation(avgTemperature, rainfall, density))
                    if (15f <= avgTemperature && 40f >= avgTemperature && 200f <= rainfall && 500f >= rainfall && blockPos.getY() >= WorldTypeTFC.SEALEVEL - 8)
                    {
                        int randomTree = random.nextInt(13) + 1;
                        StructureGeneratorCorals gen = new StructureGeneratorCorals("mangrove/" + randomTree);
                        generateStructure(gen, world, random, blockPos);

                        //TFCRegistries.TREES.getValue(TreesTFCF.MANGROVE).makeTree(manager, world, blockPos, random, true);
                        //TFCFlorae.getLog().warn("TFCFlorae: Mangrove attempted to generate at " + "X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ());
                    }
                }
            }
        }
        if (((rainfall >= 100 && density <= 0.3f) || rainfall <= 100))
        {
            int treesPerChunk = (int) (density * 10 - 2);
            for (int i = random.nextInt(Math.round(1 / diversity)); i < (density) * treesPerChunk; i++)
            {
                final int x = (chunkX << 4) + random.nextInt(16) + 8;
                final int z = (chunkZ << 4) + random.nextInt(16) + 8;
                BlockPos blockPos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
                IBlockState down = world.getBlockState(blockPos.down());
                final Biome b1 = world.getBiome(blockPos);

                if (b1 != BiomesTFC.BAYOU && b1 != BiomesTFC.MARSH && !BiomesTFC.isOceanicBiome(b1) && !BiomesTFC.isLakeBiome(b1) && !BiomesTFC.isBeachBiome(b1) && !BiomesTFC.isMesaBiome(b1))
                {
                    if ((BlocksTFC.isSand(down) || BlocksTFC.isSoilOrGravel(down) || BlocksTFCF.isSand(down) || BlocksTFCF.isSoilOrGravel(down)) && (down != Blocks.HARDENED_CLAY && down != Blocks.STAINED_HARDENED_CLAY))
                    {
                        if (15f <= avgTemperature && 40f >= avgTemperature && 65f <= rainfall && 150f >= rainfall && blockPos.getY() >= WorldTypeTFC.SEALEVEL)
                        {
                            BlockJoshuaTreeFlower.get(TFCRegistries.TREES.getValue(TreesTFCF.JOSHUA_TREE)).generatePlant(world, blockPos, random, 8);
                            //TFCFlorae.getLog().warn("TFCFlorae: Joshua Tree attempted to generate at " + "X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ());
                        }
                    }
                }
            }
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

	private void generateStructure(WorldGenerator generator, World world, Random random, BlockPos pos)
    {
        generator.generate(world, random, pos);
	}
}