package tfcflorae.world.worldgen.soil;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.registries.ForgeRegistry;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.types.DefaultRocks;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.dries007.tfc.world.classic.genlayers.GenLayerTFC;
import net.dries007.tfc.world.classic.genlayers.datalayers.ph.GenPHLayer;

import tfcflorae.ConfigTFCF;
import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

public class WorldGenSoil implements IWorldGenerator
{
    public static final float RAINFALL_SAND = 75;
    public static final float RAINFALL_SAND_SANDY_MIX = 125;
    public static final float RAINFALL_SANDY = 200; // Upper thresholds
    public static final float RAINFALL_SILTY = 275; // Lower thresholds
    public static final float RAINFALL_SILT_SILTY_MIX = 350;
    public static final float RAINFALL_SILT = 400;

    protected static final IBlockState HARDENED_CLAY = Blocks.HARDENED_CLAY.getDefaultState();
    protected static final IBlockState STAINED_HARDENED_CLAY = Blocks.STAINED_HARDENED_CLAY.getDefaultState();
    public final int yOffset = 112;
    public final int[] seaLevelOffsetMap = new int[256];

	@Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (!(chunkGenerator instanceof ChunkGenTFC)) return;
        final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);

        if (ConfigTFCF.General.WORLD.enableAllBlockTypes)
        {
            /*if (ConfigTFCF.General.WORLD.enablePodzolGen)
            {
                BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos);
                generatePodzol(world, random, pos);
            }

            if (ConfigTFCF.General.WORLD.enableAllCoarse)
            {
                BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos);
                generateCoarse(world, random, pos);
            }

            if (ConfigTFCF.General.WORLD.enableMudGen)
            {
                BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos);
                generateMud(world, random, pos);
            }

            if (ConfigTFCF.General.WORLD.enableSandGen)
            {
                BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos);
                generateSand(world, random, pos);
            }*/
        }
    }

    /*private void generateSand(World world, Random rng, BlockPos start)
    {
        ChunkDataTFC data = ChunkDataTFC.get(world, start);
        Biome b = world.getBiome(start);
        final float floraDensity = data.getFloraDensity();
        final float floraDiversity = data.getFloraDiversity();

        int depth = 2;

        if (start.getY() <= WorldTypeTFC.SEALEVEL && floraDensity > 0.1f + Math.abs(0.05f * rng.nextGaussian()) && 
            (b == BiomesTFC.OCEAN || b == BiomesTFC.DEEP_OCEAN || b == BiomesTFC.BEACH || b == BiomesTFC.LAKE))
        {
            for (int i = rng.nextInt(Math.round(normalRarity / floraDiversity)); i < (5 + floraDensity) * 15; i++)
            {
                final BlockPos posHorizontal = start.add(rng.nextInt(7) - rng.nextInt(7), 0, rng.nextInt(7) - rng.nextInt(7));
                for (int y = -depth; y <= 0; y++)
                {
                    final BlockPos pos = posHorizontal.add(0, y, 0);
                    final IBlockState current = world.getBlockState(pos);

                    if ((BlocksTFC.isSoilOrGravel(current) || BlocksTFC.isRawStone(current)))
                    {
                        world.setBlockState(pos, BlockRockVariant.get(ChunkDataTFC.getRockHeight(world, pos), Rock.Type.SAND).getDefaultState(), 2);
                        TFCFlorae.getLog().warn("TFCFlorae: Sand block attempted to generate at " + "X: " + pos.getX() + ", Y: " + pos.getY() + ", Z: " + pos.getZ());
                    }
                }
            }
        }
    }

    private void generateSand(World world, Random rng, BlockPos start)
    {
        int depth = 2;

        for (int x = 0; x < 16; ++x)
        {
            for (int z = 0; z < 16; ++z)
            {
                if (rng.nextInt(ConfigTFCF.General.WORLD.sandRarity) == 0)
                {
                    BlockPos newStart = world.getTopSolidOrLiquidBlock(start.add(x, 0, z));
                    for (int y = -depth; y <= 0; y++)
                    {
                        final BlockPos pos = newStart.add(0, y, 0);
                        final Biome b = world.getBiome(pos);
                        ChunkDataTFC data = ChunkDataTFC.get(world, pos);

                        if ((b == BiomesTFC.OCEAN || b == BiomesTFC.DEEP_OCEAN || b == BiomesTFC.BEACH || b == BiomesTFC.LAKE) &&
                            pos.getY() <= WorldTypeTFC.SEALEVEL &&
                            data.getFloraDensity() >= 0.2f + (rng.nextGaussian() / 10) &&
                            ChunkDataTFC.getRainfall(world, pos) >= RAINFALL_SAND + 15)
                        {
                            final IBlockState current = world.getBlockState(pos);

                            if ((BlocksTFC.isSoilOrGravel(current) || BlocksTFC.isRawStone(current)))
                            {
                                world.setBlockState(pos, BlockRockVariant.get(ChunkDataTFC.getRockHeight(world, pos), Rock.Type.SAND).getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void generatePodzol(World world, Random rng, BlockPos start)
    {
        int depth = 1;

        for (int x = 0; x < 16; ++x)
        {
            for (int z = 0; z < 16; ++z)
            {
                if (rng.nextInt(ConfigTFCF.General.WORLD.podzolRarity) == 0)
                {
                    BlockPos newStart = world.getTopSolidOrLiquidBlock(start.add(x, 0, z));
                    for (int y = -depth; y <= 0; y++)
                    {
                        final BlockPos pos = newStart.add(0, y, 0);
                        ChunkDataTFC data = ChunkDataTFC.get(world, pos);
                        //TFCFlorae.getLog().warn("TFCFlorae: Drainage value is " + ChunkDataTFC.getDrainage(world, pos) + " here.");

                        if (pos.getY() >= WorldTypeTFC.SEALEVEL + 2 && pos.getY() <= 175 &&
                            ChunkDataTFC.getDrainage(world, pos) >= 2 &&
                            data.getFloraDensity() >= 0.3f + (rng.nextGaussian() / 10) &&
                            //data.getFloraDensity() <= 0.6f + (rng.nextGaussian() / 10) &&
                            data.getFloraDiversity() >= 0.1f &&
                            //data.getFloraDiversity() <= 0.3f &&
                            ChunkDataTFC.getRainfall(world, pos) >= RAINFALL_SAND + 15)
                        {
                            final IBlockState current = world.getBlockState(pos);

                            if (BlocksTFC.isGrass(current) || BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.PODZOL).getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateCoarse(World world, Random rng, BlockPos start)
    {
        int depth = 3;

        for (int x = 0; x < 16; ++x)
        {
            for (int z = 0; z < 16; ++z)
            {
                if (rng.nextInt(ConfigTFCF.General.WORLD.mudRarity) == 0)
                {
                    BlockPos newStart = world.getTopSolidOrLiquidBlock(start.add(x, 0, z));
                    for (int y = -depth; y <= 0; y++)
                    {
                        final BlockPos pos = newStart.add(0, y, 0);
                        final Biome b = world.getBiome(pos);
                        ChunkDataTFC data = ChunkDataTFC.get(world, pos);

                        if (b == BiomesTFC.SWAMPLAND &&
                            start.getY() >= WorldTypeTFC.SEALEVEL && start.getY() <= 150 &&
                            ChunkDataTFC.getDrainage(world, pos) <= 2 &&
                            data.getFloraDensity() <= 0.3f + (rng.nextGaussian() / 10) &&
                            ChunkDataTFC.getRainfall(world, pos) >= RAINFALL_SAND_SANDY_MIX + 1.3 * rng.nextGaussian())
                        {
                            final IBlockState current = world.getBlockState(pos);

                            if (BlocksTFC.isGrass(current) || BlocksTFC.isDryGrass(current) || BlocksTFC.isDirt(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_DIRT).getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateMud(World world, Random rng, BlockPos start)
    {
        int depth = 3;

        for (int x = 0; x < 16; ++x)
        {
            for (int z = 0; z < 16; ++z)
            {
                if (rng.nextInt(ConfigTFCF.General.WORLD.mudRarity) == 0)
                {
                    BlockPos newStart = world.getTopSolidOrLiquidBlock(start.add(x, 0, z));
                    for (int y = -depth; y <= 0; y++)
                    {
                        final BlockPos pos = newStart.add(0, y, 0);
                        final Biome b = world.getBiome(pos);
                        ChunkDataTFC data = ChunkDataTFC.get(world, pos);

                        if (b == BiomesTFC.SWAMPLAND &&
                            start.getY() >= WorldTypeTFC.SEALEVEL && start.getY() <= 150 &&
                            ChunkDataTFC.getDrainage(world, pos) <= 2 &&
                            data.getFloraDensity() <= 0.3f + (rng.nextGaussian() / 10) &&
                            ChunkDataTFC.getRainfall(world, pos) >= RAINFALL_SAND_SANDY_MIX + 1.3 * rng.nextGaussian())
                        {
                            final IBlockState current = world.getBlockState(pos);

                            if (BlocksTFC.isGrass(current) || BlocksTFC.isDryGrass(current) || BlocksTFC.isDirt(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.MUD).getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }
    }*/
}