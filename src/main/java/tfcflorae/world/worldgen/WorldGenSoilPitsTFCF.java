package tfcflorae.world.worldgen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

public class WorldGenSoilPitsTFCF implements IWorldGenerator
{
	@Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (!(chunkGenerator instanceof ChunkGenTFC)) return;
        final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);

        BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateHumus(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generatePodzol(world, random, pos);
        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generatePodzol(world, random, pos);
        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generatePodzol(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateMud(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateLoamySand(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateSandyLoam(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateLoam(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateSiltLoam(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateSilt(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateSandyClay(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateSandyClayLoam(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateClayLoam(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateSiltyClayLoam(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateSiltyClay(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateClayHumus(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateKaoliniteClay(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateSandyKaoliniteClay(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateSandyKaoliniteClayLoam(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateKaoliniteClayLoam(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateSiltyKaoliniteClayLoam(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateSiltyKaoliniteClay(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateKaoliniteClayHumus(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateCoarseDirt(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateCoarseLoamySand(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateCoarseSandyLoam(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateCoarseLoam(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateCoarseSiltLoam(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateCoarseSilt(world, random, pos);
    }

    /*private void generatePlate(World world, Random rng, BlockPos start, int density)
    {
        int radius = rng.nextInt(7) + 1;
        int depth = rng.nextInt(3) + 1;

        if (rng.nextInt(1) == 0 && start.getY() > WorldTypeTFC.SEALEVEL + 1) return;
        ChunkDataTFC data = ChunkDataTFC.get(world, start);
        if (data.isInitialized() && data.getRainfall() <= 100f && data.getFloraDensity() <= 0.8f && data.getFloraDiversity() <= 0.8f)
            return;

        for (int x = -radius; x <= radius; x++)
        {
            for (int z = -radius; z <= radius; z++)
            {
                if (x * x + z * z > radius * radius) continue;
                final BlockPos posHorizontal = start.add(x, 0, z);

                for (int y = -depth; y <= +depth; y++)
                {
                    final BlockPos pos = posHorizontal.add(0, y, 0);
                    final IBlockState current = world.getBlockState(pos);
                    if (BlocksTFC.isGrass(current))
                    {
                        if(rng.nextInt(100) <= density)
                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.PODZOL).getDefaultState(), 2);
                    }
                }
            }
        }
    }

    private void fancy(World world, Random rng, BlockPos start, int density)
    {
        int Length = rng.nextInt(15) + 1;
        int depth = rng.nextInt(3) + 1;
        int widthMultiplier = rng.nextInt(2) + 1;

        int z;
        int tz;
        int angle = rng.nextInt(360);

        int rx;
        int rz;

        for (int x = -Length; x <= Length; x++)
        {
            z = (int) (0.3 * x * MathHelper.sin((-0.2f + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
            tz = MathHelper.abs(z) * widthMultiplier;

            for (int width = -tz; width <= tz; width++)
            {
                rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                final BlockPos posHorizontal = start.add(rx, 0, rz);

                for (int y = -depth; y <= +depth; y++)
                {
                    final BlockPos pos = posHorizontal.add(0, y, 0);
                    final IBlockState current = world.getBlockState(pos);
                    if (BlocksTFC.isGrass(current))
                    {
                        if(rng.nextInt(100) <= density)
                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.PODZOL).getDefaultState(), 2);
                    }
                }
            }
        }
    }*/

    private void generateHumus(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(8) == 0 && start.getY() >= 155 && start.getY() <= 175)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 190f && data.getFloraDensity() >= 0.4f)
            {
                int length = rng.nextInt(7) + 1;
                int depth = rng.nextInt(3) + 1;
                float widthMultiplier = rng.nextInt(3) + 1;
                int curveHeight = rng.nextInt(3) + 1;
                int curveSlope = rng.nextInt(2) + 1;
                float curveFrequency = (rng.nextInt(9) + 1) / 10;

                int z;
                int tz;
                float tWidth = widthMultiplier / 4;

                int angle = rng.nextInt(360);

                int rx;
                int rz;

                for (int x = -length; x <= length; x++)
                {
                    if(x < -length + 3)
                        tWidth *= 2;
                    else if(length - x < 3)
                        tWidth /= 2;

                    //tx = x + shiftMultiplier;
                    z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
                    //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
                    tz = (int)((float)MathHelper.abs(z) * tWidth);

                    for (int width = -tz; width <= tz; width++)
                    {
                        rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                        rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                        final BlockPos posHorizontal = start.add(rx, 0, rz);

                        for (int y = -depth; y <= +depth; y++)
                        {
                            final BlockPos pos = posHorizontal.add(0, y, 0);
                            final IBlockState current = world.getBlockState(pos);
                            if (BlocksTFC.isDirt(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.HUMUS).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_HUMUS_GRASS).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.HUMUS_GRASS).getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void generatePodzol(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(1) == 0 && start.getY() >= 146 && start.getY() <= 175)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 90f && data.getFloraDensity() >= 0.5f)
            {
                int length = rng.nextInt(7) + 1;
                int depth = rng.nextInt(3) + 1;
                float widthMultiplier = rng.nextInt(3) + 1;
                int curveHeight = rng.nextInt(3) + 1;
                int curveSlope = rng.nextInt(2) + 1;
                float curveFrequency = (rng.nextInt(9) + 1) / 10;

                int z;
                int tz;
                float tWidth = widthMultiplier / 4;

                int angle = rng.nextInt(360);

                int rx;
                int rz;

                for (int x = -length; x <= length; x++)
                {
                    if(x < -length + 3)
                        tWidth *= 2;
                    else if(length - x < 3)
                        tWidth /= 2;

                    //tx = x + shiftMultiplier;
                    z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
                    //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
                    tz = (int)((float)MathHelper.abs(z) * tWidth);

                    for (int width = -tz; width <= tz; width++)
                    {
                        rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                        rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                        final BlockPos posHorizontal = start.add(rx, 0, rz);

                        for (int y = -depth; y <= +depth; y++)
                        {
                            final BlockPos pos = posHorizontal.add(0, y, 0);
                            final IBlockState current = world.getBlockState(pos);
                            if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.PODZOL).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.PODZOL).getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateMud(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(8) == 0 && start.getY() <= WorldTypeTFC.SEALEVEL + 1)
        {
            final Biome b = world.getBiome(start);
            if (b == BiomesTFC.SWAMPLAND)
            {
                ChunkDataTFC data = ChunkDataTFC.get(world, start);
                if (data.isInitialized() && data.getRainfall() <= 200f)
                {
                    int length = rng.nextInt(7) + 1;
                    int depth = rng.nextInt(3) + 1;
                    float widthMultiplier = rng.nextInt(3) + 1;
                    int curveHeight = rng.nextInt(3) + 1;
                    int curveSlope = rng.nextInt(2) + 1;
                    float curveFrequency = (rng.nextInt(9) + 1) / 10;

                    int z;
                    int tz;
                    float tWidth = widthMultiplier / 4;

                    int angle = rng.nextInt(360);

                    int rx;
                    int rz;

                    for (int x = -length; x <= length; x++)
                    {
                        if(x < -length + 3)
                            tWidth *= 2;
                        else if(length - x < 3)
                            tWidth /= 2;

                        //tx = x + shiftMultiplier;
                        z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
                        //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
                        tz = (int)((float)MathHelper.abs(z) * tWidth);

                        for (int width = -tz; width <= tz; width++)
                        {
                            rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                            rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                            final BlockPos posHorizontal = start.add(rx, 0, rz);

                            for (int y = -depth; y <= +depth; y++)
                            {
                                final BlockPos pos = posHorizontal.add(0, y, 0);
                                final IBlockState current = world.getBlockState(pos);
                                if (BlocksTFC.isDirt(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.MUD).getDefaultState(), 2);
                                }
                                else if (BlocksTFC.isGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.MUD).getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateLoamySand(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(8) == 0 && start.getY() <= 146 && start.getY() >= 175)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() <= 125f && data.getRainfall() <= 180f && data.getFloraDensity() <= 0.4f)
            {
                int length = rng.nextInt(7) + 1;
                int depth = rng.nextInt(3) + 1;
                float widthMultiplier = rng.nextInt(3) + 1;
                int curveHeight = rng.nextInt(3) + 1;
                int curveSlope = rng.nextInt(2) + 1;
                float curveFrequency = (rng.nextInt(9) + 1) / 10;

                int z;
                int tz;
                float tWidth = widthMultiplier / 4;

                int angle = rng.nextInt(360);

                int rx;
                int rz;

                for (int x = -length; x <= length; x++)
                {
                    if(x < -length + 3)
                        tWidth *= 2;
                    else if(length - x < 3)
                        tWidth /= 2;

                    //tx = x + shiftMultiplier;
                    z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
                    //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
                    tz = (int)((float)MathHelper.abs(z) * tWidth);

                    for (int width = -tz; width <= tz; width++)
                    {
                        rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                        rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                        final BlockPos posHorizontal = start.add(rx, 0, rz);

                        for (int y = -depth; y <= +depth; y++)
                        {
                            final BlockPos pos = posHorizontal.add(0, y, 0);
                            final IBlockState current = world.getBlockState(pos);
                            if (BlocksTFC.isDirt(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.LOAMY_SAND).getDefaultState(), 2);
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.LOAMY_SAND_PODZOL).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_LOAMY_SAND_GRASS).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.LOAMY_SAND_GRASS).getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateSandyLoam(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(8) == 0 && start.getY() >= 146 && start.getY() <= 175)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 125f && data.getFloraDensity() <= 0.4f)
            {
                int length = rng.nextInt(7) + 1;
                int depth = rng.nextInt(3) + 1;
                float widthMultiplier = rng.nextInt(3) + 1;
                int curveHeight = rng.nextInt(3) + 1;
                int curveSlope = rng.nextInt(2) + 1;
                float curveFrequency = (rng.nextInt(9) + 1) / 10;

                int z;
                int tz;
                float tWidth = widthMultiplier / 4;

                int angle = rng.nextInt(360);

                int rx;
                int rz;

                for (int x = -length; x <= length; x++)
                {
                    if(x < -length + 3)
                        tWidth *= 2;
                    else if(length - x < 3)
                        tWidth /= 2;

                    //tx = x + shiftMultiplier;
                    z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
                    //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
                    tz = (int)((float)MathHelper.abs(z) * tWidth);

                    for (int width = -tz; width <= tz; width++)
                    {
                        rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                        rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                        final BlockPos posHorizontal = start.add(rx, 0, rz);

                        for (int y = -depth; y <= +depth; y++)
                        {
                            final BlockPos pos = posHorizontal.add(0, y, 0);
                            final IBlockState current = world.getBlockState(pos);
                            if (BlocksTFC.isDirt(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_LOAM).getDefaultState(), 2);
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_LOAM_PODZOL).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_LOAM_GRASS).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_LOAM_GRASS).getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateLoam(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(8) == 0 && start.getY() >= 146 && start.getY() <= 175)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 200f && data.getFloraDensity() >= 0.4f)
            {
                int length = rng.nextInt(7) + 1;
                int depth = rng.nextInt(3) + 1;
                float widthMultiplier = rng.nextInt(3) + 1;
                int curveHeight = rng.nextInt(3) + 1;
                int curveSlope = rng.nextInt(2) + 1;
                float curveFrequency = (rng.nextInt(9) + 1) / 10;

                int z;
                int tz;
                float tWidth = widthMultiplier / 4;

                int angle = rng.nextInt(360);

                int rx;
                int rz;

                for (int x = -length; x <= length; x++)
                {
                    if(x < -length + 3)
                        tWidth *= 2;
                    else if(length - x < 3)
                        tWidth /= 2;

                    //tx = x + shiftMultiplier;
                    z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
                    //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
                    tz = (int)((float)MathHelper.abs(z) * tWidth);

                    for (int width = -tz; width <= tz; width++)
                    {
                        rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                        rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                        final BlockPos posHorizontal = start.add(rx, 0, rz);

                        for (int y = -depth; y <= +depth; y++)
                        {
                            final BlockPos pos = posHorizontal.add(0, y, 0);
                            final IBlockState current = world.getBlockState(pos);
                            if (BlocksTFC.isDirt(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.LOAM).getDefaultState(), 2);
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.LOAM_PODZOL).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_LOAM_GRASS).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.LOAM_GRASS).getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateSiltLoam(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(8) == 0 && start.getY() >= 146 && start.getY() <= 175)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 275f && data.getRainfall() <= 350f)
            {
                int length = rng.nextInt(7) + 1;
                int depth = rng.nextInt(3) + 1;
                float widthMultiplier = rng.nextInt(3) + 1;
                int curveHeight = rng.nextInt(3) + 1;
                int curveSlope = rng.nextInt(2) + 1;
                float curveFrequency = (rng.nextInt(9) + 1) / 10;
        
                int z;
                int tz;
                float tWidth = widthMultiplier / 4;
        
                int angle = rng.nextInt(360);
        
                int rx;
                int rz;
        
                for (int x = -length; x <= length; x++)
                {
                    if(x < -length + 3)
                        tWidth *= 2;
                    else if(length - x < 3)
                        tWidth /= 2;

                    //tx = x + shiftMultiplier;
                    z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
                    //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
                    tz = (int)((float)MathHelper.abs(z) * tWidth);

                    for (int width = -tz; width <= tz; width++)
                    {
                        rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                        rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                        final BlockPos posHorizontal = start.add(rx, 0, rz);

                        for (int y = -depth; y <= +depth; y++)
                        {
                            final BlockPos pos = posHorizontal.add(0, y, 0);
                            final IBlockState current = world.getBlockState(pos);
                            if (BlocksTFC.isDirt(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILT_LOAM).getDefaultState(), 2);
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILT_LOAM_PODZOL).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILT_LOAM_GRASS).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILT_LOAM_GRASS).getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateSilt(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(8) == 0 && start.getY() >= 146 && start.getY() <= 175)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 350f)
            {
                int length = rng.nextInt(7) + 1;
                int depth = rng.nextInt(3) + 1;
                float widthMultiplier = rng.nextInt(3) + 1;
                int curveHeight = rng.nextInt(3) + 1;
                int curveSlope = rng.nextInt(2) + 1;
                float curveFrequency = (rng.nextInt(9) + 1) / 10;

                int z;
                int tz;
                float tWidth = widthMultiplier / 4;

                int angle = rng.nextInt(360);

                int rx;
                int rz;

                for (int x = -length; x <= length; x++)
                {
                    if(x < -length + 3)
                        tWidth *= 2;
                    else if(length - x < 3)
                        tWidth /= 2;

                    //tx = x + shiftMultiplier;
                    z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
                    //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
                    tz = (int)((float)MathHelper.abs(z) * tWidth);

                    for (int width = -tz; width <= tz; width++)
                    {
                        rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                        rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                        final BlockPos posHorizontal = start.add(rx, 0, rz);

                        for (int y = -depth; y <= +depth; y++)
                        {
                            final BlockPos pos = posHorizontal.add(0, y, 0);
                            final IBlockState current = world.getBlockState(pos);
                            if (BlocksTFC.isDirt(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILT).getDefaultState(), 2);
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILT_PODZOL).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILT_GRASS).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILT_GRASS).getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    // Clay Generation

    private void generateSandyClay(World world, Random rng, BlockPos start)
    {
        int radius = rng.nextInt(7) + 1;
        int depth = rng.nextInt(3) + 1;

        if (rng.nextInt(40) == 0 && start.getY() <= WorldTypeTFC.SEALEVEL + 6)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() <= 125f && data.getRainfall() <= 180f && data.getFloraDensity() <= 0.4f)
            {
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
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_PODZOL).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_CLAY_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                        }
                        if (flag && rng.nextInt(15) == 0)
                        {
                            final BlockPos pos = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, pos));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(pos) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, pos), ChunkDataTFC.getRainfall(world, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) &&
                                        world.isAirBlock(pos) &&
                                        plantBlock.canBlockStay(world, pos, state))
                                    {
                                        world.setBlockState(pos, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateSandyClayLoam(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(7) + 1;
        int depth = rng.nextInt(3) + 1;

        if (rng.nextInt(40) == 0 && start.getY() <= WorldTypeTFC.SEALEVEL + 6)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 125f && data.getFloraDensity() <= 0.4f)
            {
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
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_LOAM).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                        }
                        if (flag && rng.nextInt(15) == 0)
                        {
                            final BlockPos pos = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, pos));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(pos) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, pos), ChunkDataTFC.getRainfall(world, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) &&
                                        world.isAirBlock(pos) &&
                                        plantBlock.canBlockStay(world, pos, state))
                                    {
                                        world.setBlockState(pos, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateClayLoam(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(7) + 1;
        int depth = rng.nextInt(3) + 1;

        if (rng.nextInt(40) == 0 && start.getY() <= WorldTypeTFC.SEALEVEL + 6)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 200f && data.getFloraDensity() >= 0.4f)
            {
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
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_LOAM).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_LOAM_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                        }
                        if (flag && rng.nextInt(15) == 0)
                        {
                            final BlockPos pos = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, pos));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(pos) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, pos), ChunkDataTFC.getRainfall(world, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) &&
                                        world.isAirBlock(pos) &&
                                        plantBlock.canBlockStay(world, pos, state))
                                    {
                                        world.setBlockState(pos, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateSiltyClayLoam(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(7) + 1;
        int depth = rng.nextInt(3) + 1;

        if (rng.nextInt(40) == 0 && start.getY() <= WorldTypeTFC.SEALEVEL + 6)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 275f && data.getRainfall() <= 350f)
            {
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
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_LOAM).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                        }
                        if (flag && rng.nextInt(15) == 0)
                        {
                            final BlockPos pos = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, pos));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(pos) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, pos), ChunkDataTFC.getRainfall(world, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) &&
                                        world.isAirBlock(pos) &&
                                        plantBlock.canBlockStay(world, pos, state))
                                    {
                                        world.setBlockState(pos, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateSiltyClay(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(7) + 1;
        int depth = rng.nextInt(3) + 1;

        if (rng.nextInt(40) == 0 && start.getY() <= WorldTypeTFC.SEALEVEL + 6)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 350f)
            {
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
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_PODZOL).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_CLAY_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                        }
                        if (flag && rng.nextInt(15) == 0)
                        {
                            final BlockPos pos = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, pos));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(pos) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, pos), ChunkDataTFC.getRainfall(world, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) &&
                                        world.isAirBlock(pos) &&
                                        plantBlock.canBlockStay(world, pos, state))
                                    {
                                        world.setBlockState(pos, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateClayHumus(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(7) + 1;
        int depth = rng.nextInt(3) + 1;

        if (rng.nextInt(40) == 0 && start.getY() <= WorldTypeTFC.SEALEVEL + 6)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 190f && data.getFloraDensity() >= 0.4f)
            {
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
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_HUMUS).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                        }
                        if (flag && rng.nextInt(15) == 0)
                        {
                            final BlockPos pos = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, pos));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(pos) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, pos), ChunkDataTFC.getRainfall(world, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) &&
                                        world.isAirBlock(pos) &&
                                        plantBlock.canBlockStay(world, pos, state))
                                    {
                                        world.setBlockState(pos, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Kaolinite Clay Generation

    private void generateKaoliniteClay(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(6) + 2;
        int depth = rng.nextInt(3) + 1;
        if (rng.nextInt(60) == 0 && start.getY() <= WorldTypeTFC.SEALEVEL + 6)
        {
            if (ChunkDataTFC.getRainfall(world, start) < ConfigTFC.General.WORLD.clayRainfallThreshold)
            {
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
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.KAOLINITE_CLAY).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                        }
                        if (flag && rng.nextInt(15) == 0)
                        {
                            final BlockPos pos = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, pos));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(pos) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, pos), ChunkDataTFC.getRainfall(world, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) &&
                                        world.isAirBlock(pos) &&
                                        plantBlock.canBlockStay(world, pos, state))
                                    {
                                        world.setBlockState(pos, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateSandyKaoliniteClay(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(7) + 1;
        int depth = rng.nextInt(3) + 1;

        if (rng.nextInt(60) == 0 && start.getY() <= WorldTypeTFC.SEALEVEL + 6)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() <= 125f && data.getRainfall() <= 180f && data.getFloraDensity() <= 0.4f)
            {
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
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_KAOLINITE_CLAY).getDefaultState(), 2);
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_KAOLINITE_CLAY_PODZOL).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                            }
                        }
                        if (flag && rng.nextInt(15) == 0)
                        {
                            final BlockPos pos = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, pos));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(pos) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, pos), ChunkDataTFC.getRainfall(world, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) &&
                                        world.isAirBlock(pos) &&
                                        plantBlock.canBlockStay(world, pos, state))
                                    {
                                        world.setBlockState(pos, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateSandyKaoliniteClayLoam(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(7) + 1;
        int depth = rng.nextInt(3) + 1;

        if (rng.nextInt(60) == 0 && start.getY() <= WorldTypeTFC.SEALEVEL + 6)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 125f && data.getFloraDensity() <= 0.4f)
            {
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
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_KAOLINITE_CLAY_LOAM).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                        }
                        if (flag && rng.nextInt(15) == 0)
                        {
                            final BlockPos pos = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, pos));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(pos) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, pos), ChunkDataTFC.getRainfall(world, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) &&
                                        world.isAirBlock(pos) &&
                                        plantBlock.canBlockStay(world, pos, state))
                                    {
                                        world.setBlockState(pos, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateKaoliniteClayLoam(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(7) + 1;
        int depth = rng.nextInt(3) + 1;

        if (rng.nextInt(60) == 0 && start.getY() <= WorldTypeTFC.SEALEVEL + 6)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 200f && data.getFloraDensity() >= 0.4f)
            {
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
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.KAOLINITE_CLAY_LOAM).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.KAOLINITE_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                        }
                        if (flag && rng.nextInt(15) == 0)
                        {
                            final BlockPos pos = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, pos));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(pos) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, pos), ChunkDataTFC.getRainfall(world, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) &&
                                        world.isAirBlock(pos) &&
                                        plantBlock.canBlockStay(world, pos, state))
                                    {
                                        world.setBlockState(pos, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateSiltyKaoliniteClayLoam(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(7) + 1;
        int depth = rng.nextInt(3) + 1;

        if (rng.nextInt(60) == 0 && start.getY() <= WorldTypeTFC.SEALEVEL + 6)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 275f && data.getRainfall() <= 350f)
            {
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
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_KAOLINITE_CLAY_LOAM).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                        }
                        if (flag && rng.nextInt(15) == 0)
                        {
                            final BlockPos pos = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, pos));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(pos) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, pos), ChunkDataTFC.getRainfall(world, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) &&
                                        world.isAirBlock(pos) &&
                                        plantBlock.canBlockStay(world, pos, state))
                                    {
                                        world.setBlockState(pos, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateSiltyKaoliniteClay(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(7) + 1;
        int depth = rng.nextInt(3) + 1;

        if (rng.nextInt(60) == 0 && start.getY() <= WorldTypeTFC.SEALEVEL + 6)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 350f)
            {
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
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_KAOLINITE_CLAY).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFCF.isPodzol(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_KAOLINITE_CLAY_PODZOL).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                        }
                        if (flag && rng.nextInt(15) == 0)
                        {
                            final BlockPos pos = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, pos));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(pos) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, pos), ChunkDataTFC.getRainfall(world, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) &&
                                        world.isAirBlock(pos) &&
                                        plantBlock.canBlockStay(world, pos, state))
                                    {
                                        world.setBlockState(pos, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateKaoliniteClayHumus(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(7) + 1;
        int depth = rng.nextInt(3) + 1;

        if (rng.nextInt(60) == 0 && start.getY() <= WorldTypeTFC.SEALEVEL + 6)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getRainfall() >= 190f && data.getFloraDensity() >= 0.4f)
            {
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
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.KAOLINITE_CLAY_HUMUS).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isDryGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                            else if (BlocksTFC.isGrass(current))
                            {
                                world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.KAOLINITE_CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                flag = true;
                            }
                        }
                        if (flag && rng.nextInt(15) == 0)
                        {
                            final BlockPos pos = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, pos));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(pos) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, pos), ChunkDataTFC.getRainfall(world, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) &&
                                        world.isAirBlock(pos) &&
                                        plantBlock.canBlockStay(world, pos, state))
                                    {
                                        world.setBlockState(pos, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Coarse Soils

    private void generateCoarseDirt(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(20) == 0 && start.getY() >= 146 && start.getY() <= 175)
        {
            final Biome b = world.getBiome(start);
            if (b != BiomesTFC.PLAINS)
            {
                ChunkDataTFC data = ChunkDataTFC.get(world, start);
                if (data.isInitialized() && data.getRainfall() >= 100f)
                {
                    int length = rng.nextInt(7) + 1;
                    int depth = rng.nextInt(1) + 1;
                    float widthMultiplier = rng.nextInt(3) + 1;
                    int curveHeight = rng.nextInt(3) + 1;
                    int curveSlope = rng.nextInt(2) + 1;
                    float curveFrequency = (rng.nextInt(9) + 1) / 10;

                    int z;
                    int tz;
                    float tWidth = widthMultiplier / 4;

                    int angle = rng.nextInt(360);

                    int rx;
                    int rz;

                    for (int x = -length; x <= length; x++)
                    {
                        if(x < -length + 3)
                            tWidth *= 2;
                        else if(length - x < 3)
                            tWidth /= 2;

                        //tx = x + shiftMultiplier;
                        z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
                        //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
                        tz = (int)((float)MathHelper.abs(z) * tWidth);

                        for (int width = -tz; width <= tz; width++)
                        {
                            rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                            rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                            final BlockPos posHorizontal = start.add(rx, 0, rz);

                            for (int y = -depth; y <= +depth; y++)
                            {
                                final BlockPos pos = posHorizontal.add(0, y, 0);
                                final IBlockState current = world.getBlockState(pos);

                                if (BlocksTFC.isGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_DIRT).getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateCoarseLoamySand(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(20) == 0 && start.getY() >= 146 && start.getY() <= 175)
        {
            final Biome b = world.getBiome(start);
            if (b != BiomesTFC.PLAINS)
            {
                ChunkDataTFC data = ChunkDataTFC.get(world, start);
                if (data.isInitialized() && data.getRainfall() <= 125f && data.getRainfall() <= 180f && data.getFloraDensity() <= 0.4f)
                {
                    int length = rng.nextInt(7) + 1;
                    int depth = rng.nextInt(1) + 1;
                    float widthMultiplier = rng.nextInt(3) + 1;
                    int curveHeight = rng.nextInt(3) + 1;
                    int curveSlope = rng.nextInt(2) + 1;
                    float curveFrequency = (rng.nextInt(9) + 1) / 10;

                    int z;
                    int tz;
                    float tWidth = widthMultiplier / 4;

                    int angle = rng.nextInt(360);

                    int rx;
                    int rz;

                    for (int x = -length; x <= length; x++)
                    {
                        if(x < -length + 3)
                            tWidth *= 2;
                        else if(length - x < 3)
                            tWidth /= 2;
                        

                        //tx = x + shiftMultiplier;
                        z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
                        //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
                        tz = (int)((float)MathHelper.abs(z) * tWidth);

                        for (int width = -tz; width <= tz; width++)
                        {
                            rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                            rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                            final BlockPos posHorizontal = start.add(rx, 0, rz);

                            for (int y = -depth; y <= +depth; y++)
                            {
                                final BlockPos pos = posHorizontal.add(0, y, 0);
                                final IBlockState current = world.getBlockState(pos);

                                if (BlocksTFC.isGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_LOAMY_SAND).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayDryGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayPodzol(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayDirt(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SANDY_CLAY).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayDryGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayPodzol(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayDirt(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SANDY_KAOLINITE_CLAY).getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateCoarseSandyLoam(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(20) == 0 && start.getY() >= 146 && start.getY() <= 175)
        {
            final Biome b = world.getBiome(start);
            if (b != BiomesTFC.PLAINS)
            {
                ChunkDataTFC data = ChunkDataTFC.get(world, start);
                if (data.isInitialized() && data.getRainfall() >= 125f && data.getFloraDensity() <= 0.4f)
                {
                    int length = rng.nextInt(7) + 1;
                    int depth = rng.nextInt(1) + 1;
                    float widthMultiplier = rng.nextInt(3) + 1;
                    int curveHeight = rng.nextInt(3) + 1;
                    int curveSlope = rng.nextInt(2) + 1;
                    float curveFrequency = (rng.nextInt(9) + 1) / 10;

                    int z;
                    int tz;
                    float tWidth = widthMultiplier / 4;

                    int angle = rng.nextInt(360);

                    int rx;
                    int rz;

                    for (int x = -length; x <= length; x++)
                    {
                        if(x < -length + 3)
                            tWidth *= 2;
                        else if(length - x < 3)
                            tWidth /= 2;
                        

                        //tx = x + shiftMultiplier;
                        z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
                        //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
                        tz = (int)((float)MathHelper.abs(z) * tWidth);

                        for (int width = -tz; width <= tz; width++)
                        {
                            rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                            rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                            final BlockPos posHorizontal = start.add(rx, 0, rz);

                            for (int y = -depth; y <= +depth; y++)
                            {
                                final BlockPos pos = posHorizontal.add(0, y, 0);
                                final IBlockState current = world.getBlockState(pos);

                                if (BlocksTFC.isGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SANDY_LOAM).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayDryGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayPodzol(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayDirt(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SANDY_CLAY_LOAM).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayDryGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayPodzol(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayDirt(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SANDY_KAOLINITE_CLAY_LOAM).getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateCoarseLoam(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(20) == 0 && start.getY() >= 146 && start.getY() <= 175)
        {
            final Biome b = world.getBiome(start);
            if (b != BiomesTFC.PLAINS)
            {
                ChunkDataTFC data = ChunkDataTFC.get(world, start);
                if (data.isInitialized() && data.getRainfall() >= 200f && data.getFloraDensity() >= 0.4f)
                {
                    int length = rng.nextInt(7) + 1;
                    int depth = rng.nextInt(1) + 1;
                    float widthMultiplier = rng.nextInt(3) + 1;
                    int curveHeight = rng.nextInt(3) + 1;
                    int curveSlope = rng.nextInt(2) + 1;
                    float curveFrequency = (rng.nextInt(9) + 1) / 10;

                    int z;
                    int tz;
                    float tWidth = widthMultiplier / 4;

                    int angle = rng.nextInt(360);

                    int rx;
                    int rz;

                    for (int x = -length; x <= length; x++)
                    {
                        if(x < -length + 3)
                            tWidth *= 2;
                        else if(length - x < 3)
                            tWidth /= 2;
                        

                        //tx = x + shiftMultiplier;
                        z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
                        //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
                        tz = (int)((float)MathHelper.abs(z) * tWidth);

                        for (int width = -tz; width <= tz; width++)
                        {
                            rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                            rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                            final BlockPos posHorizontal = start.add(rx, 0, rz);

                            for (int y = -depth; y <= +depth; y++)
                            {
                                final BlockPos pos = posHorizontal.add(0, y, 0);
                                final IBlockState current = world.getBlockState(pos);

                                if (BlocksTFC.isGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_LOAM).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayDryGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayPodzol(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayDirt(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_CLAY_LOAM).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayDryGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayPodzol(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayDirt(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_KAOLINITE_CLAY_LOAM).getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateCoarseSiltLoam(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(20) == 0 && start.getY() >= 146 && start.getY() <= 175)
        {
            final Biome b = world.getBiome(start);
            if (b != BiomesTFC.PLAINS)
            {
                ChunkDataTFC data = ChunkDataTFC.get(world, start);
                if (data.isInitialized() && data.getRainfall() >= 275f && data.getRainfall() <= 350f)
                {
                    int length = rng.nextInt(7) + 1;
                    int depth = rng.nextInt(1) + 1;
                    float widthMultiplier = rng.nextInt(3) + 1;
                    int curveHeight = rng.nextInt(3) + 1;
                    int curveSlope = rng.nextInt(2) + 1;
                    float curveFrequency = (rng.nextInt(9) + 1) / 10;

                    int z;
                    int tz;
                    float tWidth = widthMultiplier / 4;

                    int angle = rng.nextInt(360);

                    int rx;
                    int rz;

                    for (int x = -length; x <= length; x++)
                    {
                        if(x < -length + 3)
                            tWidth *= 2;
                        else if(length - x < 3)
                            tWidth /= 2;
                        

                        //tx = x + shiftMultiplier;
                        z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
                        //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
                        tz = (int)((float)MathHelper.abs(z) * tWidth);

                        for (int width = -tz; width <= tz; width++)
                        {
                            rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                            rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                            final BlockPos posHorizontal = start.add(rx, 0, rz);

                            for (int y = -depth; y <= +depth; y++)
                            {
                                final BlockPos pos = posHorizontal.add(0, y, 0);
                                final IBlockState current = world.getBlockState(pos);

                                if (BlocksTFC.isGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILT_LOAM).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayDryGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayPodzol(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayDirt(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILTY_CLAY_LOAM).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayDryGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayPodzol(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayDirt(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILTY_KAOLINITE_CLAY_LOAM).getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateCoarseSilt(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(20) == 0 && start.getY() >= 146 && start.getY() <= 175)
        {
            final Biome b = world.getBiome(start);
            if (b != BiomesTFC.PLAINS)
            {
                ChunkDataTFC data = ChunkDataTFC.get(world, start);
                if (data.isInitialized() && data.getRainfall() >= 350f)
                {
                    int length = rng.nextInt(7) + 1;
                    int depth = rng.nextInt(1) + 1;
                    float widthMultiplier = rng.nextInt(3) + 1;
                    int curveHeight = rng.nextInt(3) + 1;
                    int curveSlope = rng.nextInt(2) + 1;
                    float curveFrequency = (rng.nextInt(9) + 1) / 10;

                    int z;
                    int tz;
                    float tWidth = widthMultiplier / 4;

                    int angle = rng.nextInt(360);

                    int rx;
                    int rz;

                    for (int x = -length; x <= length; x++)
                    {
                        if(x < -length + 3)
                            tWidth *= 2;
                        else if(length - x < 3)
                            tWidth /= 2;
                        

                        //tx = x + shiftMultiplier;
                        z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
                        //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
                        tz = (int)((float)MathHelper.abs(z) * tWidth);

                        for (int width = -tz; width <= tz; width++)
                        {
                            rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                            rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                            final BlockPos posHorizontal = start.add(rx, 0, rz);

                            for (int y = -depth; y <= +depth; y++)
                            {
                                final BlockPos pos = posHorizontal.add(0, y, 0);
                                final IBlockState current = world.getBlockState(pos);

                                if (BlocksTFC.isGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILT).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayDryGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayPodzol(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isClayDirt(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILTY_CLAY).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayDryGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayPodzol(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isKaoliniteClayDirt(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_KAOLINITE_CLAY).getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}