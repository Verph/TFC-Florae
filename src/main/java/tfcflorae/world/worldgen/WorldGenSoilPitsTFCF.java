package tfcflorae.world.worldgen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
//import tfcflorae.objects.blocks.blocktype.BlockGrass;
import tfcflorae.types.BlockTypesTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.worldgen.WorldGenSoilPits;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.dries007.tfc.world.classic.worldgen.WorldGenSoilPits;

/**
 * todo: make these bigger without causing cascading lag.
 * This will require larger re-writes on the scale of oregen
 * Wait for 1.14+ as AlcatrazEscapee is doing a worldgen rewrite anyway
 */
public class WorldGenSoilPitsTFCF extends WorldGenSoilPits
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (!(chunkGenerator instanceof ChunkGenTFC)) return;
        final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);

        BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generatePodzol(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateCoarseDirtAverage(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateCoarseDirtDry(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateMud(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateLoam(world, random, pos);
    }
    
    private void generatePodzol(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(4) + 4;
        int depth = rng.nextInt(2) + 2;

        if (rng.nextInt(ConfigTFC.General.WORLD.clayRarity) != 0 || start.getY() > WorldTypeTFC.SEALEVEL + 6) return;
        if (ChunkDataTFC.getRainfall(world, start) < ConfigTFC.General.WORLD.clayRainfallThreshold) return;

        /*
        if (rng.nextInt(30) != 0 || start.getY() > WorldTypeTFC.SEALEVEL + 1) return;
        ChunkDataTFC data = ChunkDataTFC.get(world, start);
        if (data.isInitialized() && data.getRainfall() <= 180f && data.getFloraDiversity() <= 0.5f && data.getFloraDensity() <= 0.5f && data.getAverageTemp() <= 0f  && data.getAverageTemp() >= 10f )
            return;
        */

        for (int x = -radius; x <= radius; x++)
        {
            for (int z = -radius; z <= radius; z++)
            {
                if (x * x + z * z > radius * radius) continue;
                final BlockPos posHorizontal = start.add(x, 0, z);

                boolean flag = false;
                for (int y = -depth; y <= +depth; y++)
                {
                    final BlockPos pos = posHorizontal.add(0, y, 0);
                    final IBlockState current = world.getBlockState(pos);
                    if (BlocksTFC.isDirt(current))
                    {
                        world.setBlockState(pos, BlockRockVariant.get(ChunkDataTFC.getRockHeight(world, pos), Rock.Type.DIRT).getDefaultState(), 2);
                        flag = true;
                    }
                    else if (BlocksTFC.isGrass(current))
                    {
                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.PODZOL).getDefaultState(), 2);
                        flag = true;
                    }
                }
            }
        }
    }

    private void generateCoarseDirtAverage(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(4) + 4;
        int depth = rng.nextInt(2) + 2;

        if (rng.nextInt(30) != 0 || start.getY() > WorldTypeTFC.SEALEVEL) return;
        ChunkDataTFC data = ChunkDataTFC.get(world, start);
        if (data.isInitialized() && data.getRainfall() <= 180f && data.getFloraDiversity() <= 0.5f && data.getFloraDensity() <= 0.5f && data.getAverageTemp() <= 0f  && data.getAverageTemp() >= 10f )
            return;

        for (int x = -radius; x <= radius; x++)
        {
            for (int z = -radius; z <= radius; z++)
            {
                if (x * x + z * z > radius * radius) continue;
                final BlockPos posHorizontal = start.add(x, 0, z);

                boolean flag = false;
                for (int y = -depth; y <= +depth; y++)
                {
                    final BlockPos pos = posHorizontal.add(0, y, 0);
                    final IBlockState current = world.getBlockState(pos);
                    if (BlocksTFC.isDirt(current))
                    {
                        world.setBlockState(pos, BlockRockVariant.get(ChunkDataTFC.getRockHeight(world, pos), Rock.Type.DIRT).getDefaultState(), 2);
                        flag = true;
                    }
                    else if (BlocksTFC.isGrass(current))
                    {
                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_DIRT).getDefaultState(), 2);
                        flag = true;
                    }
                }
            }
        }
    }

    private void generateCoarseDirtDry(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(4) + 4;
        int depth = rng.nextInt(2) + 2;

        if (rng.nextInt(30) != 0 || start.getY() > WorldTypeTFC.SEALEVEL) return;
        ChunkDataTFC data = ChunkDataTFC.get(world, start);
        if (data.isInitialized() && data.getRainfall() >= 100f && data.getAverageTemp() <= 17f)
            return;

        for (int x = -radius; x <= radius; x++)
        {
            for (int z = -radius; z <= radius; z++)
            {
                if (x * x + z * z > radius * radius) continue;
                final BlockPos posHorizontal = start.add(x, 0, z);

                boolean flag = false;
                for (int y = -depth; y <= +depth; y++)
                {
                    final BlockPos pos = posHorizontal.add(0, y, 0);
                    final IBlockState current = world.getBlockState(pos);
                    if (BlocksTFC.isDirt(current))
                    {
                        world.setBlockState(pos, BlockRockVariant.get(ChunkDataTFC.getRockHeight(world, pos), Rock.Type.DIRT).getDefaultState(), 2);
                        flag = true;
                    }
                    else if (BlocksTFC.isGrass(current))
                    {
                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_DIRT).getDefaultState(), 2);
                        flag = true;
                    }
                }
            }
        }
    }

    private void generateMud(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(4) + 4;
        int depth = rng.nextInt(2) + 1;

        if (rng.nextInt(30) != 0 || start.getY() > WorldTypeTFC.SEALEVEL) return;
        ChunkDataTFC data = ChunkDataTFC.get(world, start);
        if (data.isInitialized() && data.getRainfall() <= 150f && data.getFloraDiversity() <= 0.5f && data.getFloraDensity() <= 0.5f && data.getAverageTemp() <= 7f)
            return;

        for (int x = -radius; x <= radius; x++)
        {
            for (int z = -radius; z <= radius; z++)
            {
                if (x * x + z * z > radius * radius) continue;
                final BlockPos posHorizontal = start.add(x, 0, z);

                boolean flag = false;
                for (int y = -depth; y <= +depth; y++)
                {
                    final BlockPos pos = posHorizontal.add(0, y, 0);
                    final IBlockState current = world.getBlockState(pos);
                    if (BlocksTFC.isDirt(current))
                    {
                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.MUD).getDefaultState(), 2);
                        flag = true;
                    }
                    else if (BlocksTFC.isGrass(current))
                    {
                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.MUD).getDefaultState(), 2);
                        flag = true;
                    }
                }
            }
        }
    }
    
    private void generateLoam(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(4) + 4;
        int depth = rng.nextInt(2) + 2;

        if (rng.nextInt(30) != 0 || start.getY() > WorldTypeTFC.SEALEVEL + 1) return;
        ChunkDataTFC data = ChunkDataTFC.get(world, start);
        if (data.isInitialized() && data.getRainfall() <= 180f && data.getFloraDiversity() <= 0.5f && data.getFloraDensity() <= 0.5f && data.getAverageTemp() <= 0f  && data.getAverageTemp() >= 10f )
            return;

        for (int x = -radius; x <= radius; x++)
        {
            for (int z = -radius; z <= radius; z++)
            {
                if (x * x + z * z > radius * radius) continue;
                final BlockPos posHorizontal = start.add(x, 0, z);

                boolean flag = false;
                for (int y = -depth; y <= +depth; y++)
                {
                    final BlockPos pos = posHorizontal.add(0, y, 0);
                    final IBlockState current = world.getBlockState(pos);
                    if (BlocksTFC.isDirt(current))
                    {
                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.LOAM).getDefaultState(), 2);
                        flag = true;
                    }
                    else if (BlocksTFC.isGrass(current))
                    {
                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.LOAM_GRASS).getDefaultState(), 2);
                        flag = true;
                    }
                }
            }
        }
    }
}