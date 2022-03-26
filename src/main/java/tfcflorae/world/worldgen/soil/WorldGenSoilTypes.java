package tfcflorae.world.worldgen.soil;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.ConfigTFCF;
import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

public class WorldGenSoilTypes implements IWorldGenerator
{
    public static final float RAINFALL_SAND = 75;
    public static final float RAINFALL_SAND_SANDY_MIX = 125;
    public static final float RAINFALL_SANDY = 200; // Upper thresholds
    public static final float RAINFALL_SILTY = 275; // Lower thresholds
    public static final float RAINFALL_SILT_SILTY_MIX = 350;
    public static final float RAINFALL_SILT = 400;

    public static final float FLORA_DIVERSITY_SAND = 0.15f;
    public static final float FLORA_DIVERSITY_SAND_SANDY_MIX = 0.25f;
    public static final float FLORA_DIVERSITY_SANDY = 0.4f; // Upper thresholds
    public static final float FLORA_DIVERSITY_SILTY = 0.55f; // Lower thresholds
    public static final float FLORA_DIVERSITY_SILT_SILTY_MIX = 0.7f;
    public static final float FLORA_DIVERSITY_SILT = 0.8f;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (!(chunkGenerator instanceof ChunkGenTFC)) return;
        final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        ChunkDataTFC data = ChunkDataTFC.get(world, chunkBlockPos);

        final float avgTemperature = ClimateTFC.getAvgTemp(world, chunkBlockPos);
        final float rainfall = ChunkDataTFC.getRainfall(world, chunkBlockPos);
        final float drainage = ChunkDataTFC.getDrainage(world, chunkBlockPos);
        final float floraDensity = data.getFloraDensity(); // Use for various plant based decoration (tall grass, those vanilla jungle shrub things, etc.)
        final float floraDiversity = data.getFloraDiversity();

        if (ConfigTFCF.General.WORLD.enableAllBlockTypes)
        {
            if (ConfigTFCF.General.WORLD.enableAllSparseGrass)
            {
                BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateSparseGrassSurface(world, random, pos);
                BlockPos pos1 = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateSparseGrassSurface(world, random, pos1);
            }

            /*if (ConfigTFCF.General.WORLD.enableAllSpecialSoil)
            {
                BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateSoilSurface(world, random, pos);
                BlockPos pos1 = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateSoilSurface(world, random, pos1);
            }*/

            if (ConfigTFCF.General.WORLD.enableAllCoarse)
            {
                if (floraDensity >= 0.3f + 0.05f * random.nextGaussian() && drainage >= 2 * random.nextGaussian())
                {
                    BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                    generateCoarseSoilSurface(world, random, pos);
                    BlockPos pos1 = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                    generateCoarseSoilSurface(world, random, pos1);
                    /*for (int i = random.nextInt(Math.round(1 / floraDiversity)); i < (1 + floraDensity) * random.nextInt(ConfigTFCF.General.WORLD.coarseDirtRarity); i++)
                    {
                        BlockPos blockPos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8));
                        IBlockState blockPosState = world.getBlockState(blockPos);
                        if (blockPos.getY() >= WorldTypeTFC.SEALEVEL && blockPos.getY() <= WorldTypeTFC.SEALEVEL + 30)
                        {
                            if (BlocksTFC.isGrass(blockPosState) || BlocksTFCF.isGrass(blockPosState) || BlocksTFC.isDirt(blockPosState) || BlocksTFCF.isDirt(blockPosState))
                            {
                                world.setBlockState(blockPos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, blockPos), RockTFCF.COARSE_LOAM).getDefaultState(), 2);
                            }
                        }
                    }*/
                }
                else
                {
                    BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                    generateCoarseSoilSurface(world, random, pos);
                }
            }

            if (ConfigTFCF.General.WORLD.enableAllRooted && floraDensity >= 0.35f)
            {
                BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateRootedSoilSurface(world, random, pos);
                BlockPos pos1 = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateRootedSoilSurface(world, random, pos1);
            }
        }
    }

    private void generateSparseGrassSurface(World world, Random rng, BlockPos start)
    {
        if (ConfigTFCF.General.WORLD.enableAllSparseGrass)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && rng.nextInt(ConfigTFCF.General.WORLD.sparseGrassRarity) == 0 && data.getFloraDensity() >= 0.35f && ChunkDataTFC.getDrainage(world, start) >= 2 * rng.nextGaussian())
            {
                int normalRarity = rng.nextInt(1);
                int sandyLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.sandyLoamRarity / 5);
                int loamySandRarity = rng.nextInt(ConfigTFCF.General.WORLD.loamySandRarity / 5);
                int loamRarity = rng.nextInt(ConfigTFCF.General.WORLD.loamRarity / 5);
                int siltLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltLoamRarity / 5);
                int humusRarity = rng.nextInt(ConfigTFCF.General.WORLD.humusRarity / 5);
                int siltRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltRarity / 5);

                int length = rng.nextInt(4) + 3;
                int depth = rng.nextInt(3) + 1;
                float widthMultiplier = rng.nextInt(1) + 1f;
                int curveHeight = rng.nextInt(4) + 3;
                float curveFrequency = (rng.nextInt(1) + 1f) / 10f;

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

                    z = (int) (curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float) (x)));
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

                            if (normalRarity == 0)
                            {
                                if ((BlocksTFC.isDryGrass(current) || BlocksTFC.isGrass(current)) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_GRASS).getDefaultState(), 2);
                                }
                            }
                            if (data.getFloraDiversity() < FLORA_DIVERSITY_SANDY)
                            {
                                if (data.getFloraDiversity() > FLORA_DIVERSITY_SAND_SANDY_MIX)
                                {
                                    if (sandyLoamRarity == 0)
                                    {
                                        if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_LOAM_PODZOL).getDefaultState(), 2);
                                        }
                                        else if ((BlocksTFC.isDryGrass(current) || BlocksTFCF.isDryGrass(current)) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SANDY_LOAM_GRASS).getDefaultState(), 2);
                                        }
                                        else if ((BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current)) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SANDY_LOAM_GRASS).getDefaultState(), 2);
                                        }
                                    }
                                }
                                else if (data.getFloraDiversity() > FLORA_DIVERSITY_SAND)
                                {
                                    if (loamySandRarity== 0)
                                    {
                                        if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.LOAMY_SAND_PODZOL).getDefaultState(), 2);
                                        }
                                        else if ((BlocksTFC.isDryGrass(current) || BlocksTFCF.isDryGrass(current)) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_LOAMY_SAND_GRASS).getDefaultState(), 2);
                                        }
                                        else if ((BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current)) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_LOAMY_SAND_GRASS).getDefaultState(), 2);
                                        }
                                    }
                                }
                            }
                            else if (data.getFloraDiversity() > FLORA_DIVERSITY_SANDY)
                            {
                                if (data.getFloraDiversity() < FLORA_DIVERSITY_SILTY)
                                {
                                    if (loamRarity == 0)
                                    {
                                        if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.LOAM_PODZOL).getDefaultState(), 2);
                                        }
                                        else if ((BlocksTFC.isDryGrass(current) || BlocksTFCF.isDryGrass(current)) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_LOAM_GRASS).getDefaultState(), 2);
                                        }
                                        else if ((BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current)) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_LOAM_GRASS).getDefaultState(), 2);
                                        }
                                    }
                                }
                            }
                            else if (data.getFloraDiversity() > FLORA_DIVERSITY_SILTY)
                            {
                                if (data.getFloraDiversity() < FLORA_DIVERSITY_SILT_SILTY_MIX)
                                {
                                    if (siltLoamRarity == 0)
                                    {
                                        if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILT_LOAM_PODZOL).getDefaultState(), 2);
                                        }
                                        else if ((BlocksTFC.isDryGrass(current) || BlocksTFCF.isDryGrass(current)) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SILT_LOAM_GRASS).getDefaultState(), 2);
                                        }
                                        else if ((BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current)) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SILT_LOAM_GRASS).getDefaultState(), 2);
                                        }
                                    }
                                }
                                else if (data.getFloraDiversity() < FLORA_DIVERSITY_SILT)
                                {
                                    if (humusRarity == 0)
                                    {
                                        if (BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.HUMUS).getDefaultState(), 2);
                                        }
                                        else if ((BlocksTFC.isDryGrass(current) || BlocksTFCF.isDryGrass(current)) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_HUMUS_GRASS).getDefaultState(), 2);
                                        }
                                        else if ((BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current)) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_HUMUS_GRASS).getDefaultState(), 2);
                                        }
                                    }
                                }
                                else
                                {
                                    if (siltRarity == 0)
                                    {
                                        if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILT_PODZOL).getDefaultState(), 2);
                                        }
                                        else if ((BlocksTFC.isDryGrass(current) || BlocksTFCF.isDryGrass(current)) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SILT_GRASS).getDefaultState(), 2);
                                        }
                                        else if ((BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current)) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SILT_GRASS).getDefaultState(), 2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /*private void generateWeightedCircle(World world, Random rng, BlockPos start, int circleInterval)
    {
        ChunkDataTFC data = ChunkDataTFC.get(world, start);

        float a = rng.nextFloat() * 5.0F;

        float xStart = start.getX();
        float zStart = start.getZ();
        
        float xEnd = start.getX() + MathHelper.sin(a) * circleInterval / 8.0F;
        float zEnd = start.getZ() + MathHelper.cos(a) * circleInterval / 8.0F;

        for (int i = 0; i < circleInterval; i++)
        {
            float x = xStart + (xEnd - xStart) * i / circleInterval;
            float z = zStart + (zEnd - zStart) * i / circleInterval;

            a = rng.nextFloat() * circleInterval / 8.0F;

            float diameter = (MathHelper.sin(i * 5.0F / circleInterval) + 1.0F) * a;


        }
    }

    private void replaceCircle(World world, BlockPos center, int depth, int radius)
    {
        for(int xPos = -radius; xPos < radius; xPos++)
        {
            for(int zPos = -radius; zPos < radius; zPos++)
            {
                if (xPos * xPos + zPos * zPos > radius * radius) continue;

                for (int yPos = -depth; yPos < depth; yPos++)
                {
                    final BlockPos pos = center.add(xPos, yPos, zPos);
                    final IBlockState current = world.getBlockState(pos);

                    
                }
            }
        }
    }*/

    /*private void generateSoilSurface(World world, Random rng, BlockPos start)
    {
        if (ConfigTFCF.General.WORLD.enableAllSpecialSoil)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);

            int radius = rng.nextInt(4) + 4;
            int depth = rng.nextInt(4) + 2;
            int sandyLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.sandyLoamRarity);
            int loamySandRarity = rng.nextInt(ConfigTFCF.General.WORLD.loamySandRarity);
            int loamRarity = rng.nextInt(ConfigTFCF.General.WORLD.loamRarity);
            int siltLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltLoamRarity);
            int humusRarity = rng.nextInt(ConfigTFCF.General.WORLD.humusRarity);
            int siltRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltRarity);

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

                        if (data.getRainfall() < RAINFALL_SANDY)
                        {
                            if (data.getRainfall() > RAINFALL_SAND_SANDY_MIX && ChunkDataTFC.getDrainage(world, start) >= 3)
                            {
                                if (sandyLoamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                            else if (data.getRainfall() > RAINFALL_SAND && ChunkDataTFC.getDrainage(world, start) >= 4)
                            {
                                if (loamySandRarity== 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.LOAMY_SAND).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
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
                        else if (data.getRainfall() > RAINFALL_SANDY)
                        {
                            if (data.getRainfall() < RAINFALL_SILTY && ChunkDataTFC.getDrainage(world, start) >= 2)
                            {
                                if (loamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
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
                        else if (data.getRainfall() > RAINFALL_SILTY)
                        {
                            if (data.getRainfall() < RAINFALL_SILT_SILTY_MIX && ChunkDataTFC.getDrainage(world, start) <= 2)
                            {
                                if (siltLoamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILT_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
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
                            else if (data.getRainfall() < RAINFALL_SILT && ChunkDataTFC.getDrainage(world, start) >= 2 && ChunkDataTFC.getDrainage(world, start) <= 3)
                            {
                                if (humusRarity == 0)
                                {
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
                            else if (ChunkDataTFC.getDrainage(world, start) <= 1)
                            {
                                if (siltRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILT).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
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
        }
    }*/

    private void generateCoarseSoilSurface(World world, Random rng, BlockPos start)
    {
        if (ConfigTFCF.General.WORLD.enableAllCoarse)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getFloraDensity() >= 0.3f + 0.05f * rng.nextGaussian() && ChunkDataTFC.getDrainage(world, start) >= 2 * rng.nextGaussian())
            {
                int length = rng.nextInt(4) + 3;
                int depth = rng.nextInt(3) + 1;
                float widthMultiplier = rng.nextInt(1) + 1f;
                int curveHeight = rng.nextInt(4) + 3;
                float curveFrequency = (rng.nextInt(1) + 1f) / 10f;

                int z;
                int tz;
                float tWidth = widthMultiplier / 4;

                int angle = rng.nextInt(360);

                int rx;
                int rz;

                int coarseSandyLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.coarseSandyLoamRarity);
                int coarseLoamySandRarity = rng.nextInt(ConfigTFCF.General.WORLD.coarseLoamySandRarity);
                int coarseDirtRarity = rng.nextInt(ConfigTFCF.General.WORLD.coarseDirtRarity);
                int coarseLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.coarseLoamRarity);
                int coarseSiltLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.coarseSiltLoamRarity);
                int coarseHumusRarity = rng.nextInt(ConfigTFCF.General.WORLD.coarseHumusRarity);
                int coarseSiltRarity = rng.nextInt(ConfigTFCF.General.WORLD.coarseSiltRarity);

                for (int x = -length; x <= length; x++)
                {
                    if (x < -length + 3)
                        tWidth *= 2;
                    else if (length - x < 3)
                        tWidth /= 2;

                    z = (int)(curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float)(x)));
                    tz = (int)((float) MathHelper.abs(z) * tWidth);

                    for (int width = -tz; width <= tz; width++)
                    {
                        rx = (int)(x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                        rz = (int)(x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                        final BlockPos posHorizontal = start.add(rx, 0, rz);

                        for (int y = -depth; y <= +depth; y++)
                        {
                            final BlockPos pos = posHorizontal.add(0, y, 0);
                            final IBlockState current = world.getBlockState(pos);

                            if (data.getFloraDiversity() < FLORA_DIVERSITY_SANDY)
                            {       
                                if (data.getFloraDiversity() > FLORA_DIVERSITY_SAND_SANDY_MIX) {
                                    if (coarseSandyLoamRarity == 0)
                                    {
                                        if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current) || BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SANDY_LOAM).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFC.isClay(current) || BlocksTFCF.isClay(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SANDY_CLAY_LOAM).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isKaoliniteClayGrass(current) || BlocksTFCF.isKaoliniteClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SANDY_KAOLINITE_CLAY_LOAM).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isStonewareClayGrass(current) || BlocksTFCF.isStonewareClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SANDY_STONEWARE_CLAY_LOAM).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isEarthenwareClayGrass(current) || BlocksTFCF.isEarthenwareClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY_LOAM).getDefaultState(), 2);
                                        }
                                    }
                                }
                                else if (data.getFloraDiversity() > FLORA_DIVERSITY_SAND)
                                {
                                    if (coarseLoamySandRarity == 0)
                                    {
                                        if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current) || BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_LOAMY_SAND).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFC.isClay(current) || BlocksTFCF.isClay(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SANDY_CLAY).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isKaoliniteClayGrass(current) || BlocksTFCF.isKaoliniteClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SANDY_KAOLINITE_CLAY).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isStonewareClayGrass(current) || BlocksTFCF.isStonewareClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SANDY_STONEWARE_CLAY).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isEarthenwareClayGrass(current) || BlocksTFCF.isEarthenwareClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY).getDefaultState(), 2);
                                        }
                                    }
                                }
                            }       
                            if (data.getFloraDiversity() > FLORA_DIVERSITY_SANDY)
                            {       
                                if (coarseDirtRarity == 0)
                                {
                                    if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current) || BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_DIRT).getDefaultState(), 2);
                                    }
                                }
                            }
                            if (data.getFloraDiversity() < FLORA_DIVERSITY_SILTY)
                            {
                                if (coarseLoamRarity == 0)
                                {
                                    if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current) || BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isClay(current) || BlocksTFCF.isClay(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isKaoliniteClayGrass(current) || BlocksTFCF.isKaoliniteClayDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_KAOLINITE_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isStonewareClayGrass(current) || BlocksTFCF.isStonewareClayDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_STONEWARE_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isEarthenwareClayGrass(current) || BlocksTFCF.isEarthenwareClayDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_EARTHENWARE_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                }
                            }       
                            if (data.getFloraDiversity() > FLORA_DIVERSITY_SILTY)
                            {       
                                if (data.getFloraDiversity() < FLORA_DIVERSITY_SILT_SILTY_MIX)
                                {
                                    if (coarseSiltLoamRarity == 0)
                                    {
                                        if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current) || BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILT_LOAM).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFC.isClay(current) || BlocksTFCF.isClay(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILTY_CLAY_LOAM).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isKaoliniteClayGrass(current) || BlocksTFCF.isKaoliniteClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILTY_KAOLINITE_CLAY_LOAM).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isStonewareClayGrass(current) || BlocksTFCF.isStonewareClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILTY_STONEWARE_CLAY_LOAM).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isEarthenwareClayGrass(current) || BlocksTFCF.isEarthenwareClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY_LOAM).getDefaultState(), 2);
                                        }
                                    }
                                }
                                else if (data.getFloraDiversity() < FLORA_DIVERSITY_SILT)
                                {
                                    if (coarseHumusRarity == 0)
                                    {
                                        if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current) || BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_HUMUS).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFC.isClay(current) || BlocksTFCF.isClay(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_CLAY_HUMUS).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isKaoliniteClayGrass(current) || BlocksTFCF.isKaoliniteClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_KAOLINITE_CLAY_HUMUS).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isStonewareClayGrass(current) || BlocksTFCF.isStonewareClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_STONEWARE_CLAY_HUMUS).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isEarthenwareClayGrass(current) || BlocksTFCF.isEarthenwareClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_EARTHENWARE_CLAY_HUMUS).getDefaultState(), 2);
                                        }
                                    }
                                }
                                else
                                {
                                    if (coarseSiltRarity == 0)
                                    {
                                        if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current) || BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILT).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFC.isClay(current) || BlocksTFCF.isClay(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILTY_CLAY).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isKaoliniteClayGrass(current) || BlocksTFCF.isKaoliniteClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILTY_KAOLINITE_CLAY).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isStonewareClayGrass(current) || BlocksTFCF.isStonewareClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILTY_STONEWARE_CLAY).getDefaultState(), 2);
                                        }
                                        else if (BlocksTFCF.isEarthenwareClayGrass(current) || BlocksTFCF.isEarthenwareClayDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY).getDefaultState(), 2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateRootedSoilSurface(World world, Random rng, BlockPos start)
    {
        if (ConfigTFCF.General.WORLD.enableAllRooted)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (data.isInitialized() && data.getFloraDensity() >= 0.3f)
            {
                int length = rng.nextInt(4) + 3;
                int depth = rng.nextInt(3) + 1;
                float widthMultiplier = rng.nextInt(1) + 1f;
                int curveHeight = rng.nextInt(4) + 3;
                float curveFrequency = (rng.nextInt(1) + 1f) / 10f;

                int z;
                int tz;
                float tWidth = widthMultiplier / 4;

                int angle = rng.nextInt(360);

                int rx;
                int rz;

                int rootedSandyLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.rootedSandyLoamRarity);
                int rootedLoamySandRarity = rng.nextInt(ConfigTFCF.General.WORLD.rootedLoamySandRarity);
                int rootedDirtRarity = rng.nextInt(ConfigTFCF.General.WORLD.rootedDirtRarity);
                int rootedLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.rootedLoamRarity);
                int rootedSiltLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.rootedSiltLoamRarity);
                int rootedHumusRarity = rng.nextInt(ConfigTFCF.General.WORLD.rootedHumusRarity);
                int rootedSiltRarity = rng.nextInt(ConfigTFCF.General.WORLD.rootedSiltRarity);

                for (int x = -length; x <= length; x++)
                {
                    if (x < -length + 3)
                        tWidth *= 2;
                    else if (length - x < 3)
                        tWidth /= 2;

                    z = (int)(curveHeight + curveFrequency * x * MathHelper.sin((-curveHeight + MathHelper.sin(x))) + MathHelper.sin((float)(x)));
                    tz = (int)((float) MathHelper.abs(z) * tWidth);

                    for (int width = -tz; width <= tz; width++)
                    {
                        rx = (int)(x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
                        rz = (int)(x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

                        final BlockPos posHorizontal = start.add(rx, 0, rz);

                        for (int y = -depth; y <= +depth; y++)
                        {
                            final BlockPos pos = posHorizontal.add(0, y, 0);
                            final IBlockState current = world.getBlockState(pos);

                            if (data.getFloraDiversity() < FLORA_DIVERSITY_SANDY)
                            {       
                                if (data.getFloraDiversity() > FLORA_DIVERSITY_SAND_SANDY_MIX) {
                                    if (rootedSandyLoamRarity == 0)
                                    {
                                        if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current) || BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.ROOTED_SANDY_LOAM).getDefaultState(), 2);
                                        }
                                    }
                                }
                                else if (data.getFloraDiversity() > FLORA_DIVERSITY_SAND)
                                {
                                    if (rootedLoamySandRarity == 0)
                                    {
                                        if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current) || BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.ROOTED_LOAMY_SAND).getDefaultState(), 2);
                                        }
                                    }
                                }
                            }       
                            if (data.getFloraDiversity() > FLORA_DIVERSITY_SANDY)
                            {       
                                if (rootedDirtRarity == 0)
                                {
                                    if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current) || BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.ROOTED_DIRT).getDefaultState(), 2);
                                    }
                                }
                            }
                            if (data.getFloraDiversity() < FLORA_DIVERSITY_SILTY)
                            {
                                if (rootedLoamRarity == 0)
                                {
                                    if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current) || BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.ROOTED_LOAM).getDefaultState(), 2);
                                    }
                                }
                            }       
                            if (data.getFloraDiversity() > FLORA_DIVERSITY_SILTY)
                            {       
                                if (data.getFloraDiversity() < FLORA_DIVERSITY_SILT_SILTY_MIX)
                                {
                                    if (rootedSiltLoamRarity == 0)
                                    {
                                        if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current) || BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.ROOTED_SILT_LOAM).getDefaultState(), 2);
                                        }
                                    }
                                }
                                else if (data.getFloraDiversity() < FLORA_DIVERSITY_SILT)
                                {
                                    if (rootedHumusRarity == 0)
                                    {
                                        if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current) || BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.ROOTED_HUMUS).getDefaultState(), 2);
                                        }
                                    }
                                }
                                else
                                {
                                    if (rootedSiltRarity == 0)
                                    {
                                        if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current) || BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                        {
                                            world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.ROOTED_SILT).getDefaultState(), 2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}