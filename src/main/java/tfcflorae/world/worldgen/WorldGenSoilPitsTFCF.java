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
import net.minecraftforge.registries.ForgeRegistry;
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

import tfcflorae.ConfigTFCF;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

public class WorldGenSoilPitsTFCF implements IWorldGenerator
{
    public static final float RAINFALL_SAND = 75;
    public static final float RAINFALL_SAND_SANDY_MIX = 125;
    public static final float RAINFALL_SANDY = 200; // Upper thresholds
    public static final float RAINFALL_SILTY = 275; // Lower thresholds
    public static final float RAINFALL_SILT_SILTY_MIX = 350;
    public static final float RAINFALL_SILT = 400;

	@Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (!(chunkGenerator instanceof ChunkGenTFC)) return;
        final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);

        if (ConfigTFCF.General.WORLD.enableAllBlockTypes)
        {
            BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
            generateMud(world, random, pos);

            if (ConfigTFCF.General.WORLD.enableAllSpecialSoil)
            {
                pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateClaySurface(world, random, pos);
            }

            if (ConfigTFCF.General.WORLD.enableAllPodzol)
            {
                pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generatePodzol(world, random, pos);
                pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generatePodzol(world, random, pos);
                pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generatePodzol(world, random, pos);
            }

            if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
            {
                pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateEarthenwareClaySurface(world, random, pos);
            }

            if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
            {
                pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateKaoliniteClaySurface(world, random, pos);
            }

            if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
            {
                pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateStonewareClaySurface(world, random, pos);
            }

            if (ConfigTFCF.General.WORLD.enableAllBogIron)
            {
                pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateBogIron(world, random, pos);
            }
        }
    }

    private void generatePodzol(World world, Random rng, BlockPos start)
    {
        if (ConfigTFCF.General.WORLD.enableAllBlockTypes && ConfigTFCF.General.WORLD.enableAllPodzol)
        {
            if (rng.nextInt(ConfigTFCF.General.WORLD.podzolRarity) == 0 && start.getY() >= 146 && start.getY() <= 175)
            {
                ChunkDataTFC data = ChunkDataTFC.get(world, start);
                if (data.isInitialized() && data.getRainfall() >= 90f && data.getFloraDensity() >= 0.5f)
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
    }

    private void generateMud(World world, Random rng, BlockPos start)
    {
        if (rng.nextInt(ConfigTFCF.General.WORLD.mudRarity) == 0 && start.getY() >= WorldTypeTFC.SEALEVEL && start.getY() <= 150)
        {
            final Biome b = world.getBiome(start);
            if (b == BiomesTFC.SWAMPLAND)
            {
                ChunkDataTFC data = ChunkDataTFC.get(world, start);
                if (data.isInitialized() && data.getRainfall() >= RAINFALL_SAND_SANDY_MIX)
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

    private void generateClaySurface(World world, Random rng, BlockPos start)
    {
        if (ConfigTFCF.General.WORLD.enableAllSpecialSoil)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);

            int radius = rng.nextInt(6) + 2;
            int depth = rng.nextInt(3) + 1;

            int sandyClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.sandyClayRarity);
            int sandyClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.sandyClayLoamRarity);
            int clayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.clayLoamRarity);
            int siltyClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltyClayLoamRarity);
            int clayHumusRarity = rng.nextInt(ConfigTFCF.General.WORLD.clayHumusRarity);
            int siltyClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltyClayRarity);

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
                            if (data.getRainfall() > RAINFALL_SAND_SANDY_MIX)
                            {
                                if (sandyClayRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SANDY_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                            else if (data.getRainfall() > RAINFALL_SAND)
                            {
                                if (sandyClayLoamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SANDY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                        else if (data.getRainfall() > RAINFALL_SANDY)
                        {
                            if (data.getRainfall() < RAINFALL_SILTY)
                            {
                                if (clayLoamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                        else if (data.getRainfall() > RAINFALL_SILTY)
                        {
                            if (data.getRainfall() < RAINFALL_SILT_SILTY_MIX)
                            {
                                if (siltyClayLoamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SILTY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                            else if (data.getRainfall() < RAINFALL_SILT)
                            {
                                if (clayHumusRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_HUMUS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                            else
                            {
                                if (siltyClayRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SILTY_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                        if (rng.nextInt(10) == 0)
                        {
                            final BlockPos posTop = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, posTop));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(posTop) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, posTop), ChunkDataTFC.getRainfall(world, posTop), world.getLightFor(EnumSkyBlock.SKY, posTop)) &&
                                        world.isAirBlock(posTop) &&
                                        plantBlock.canBlockStay(world, posTop, state))
                                    {
                                        if (BlocksTFC.isClay(current) || BlocksTFCF.isClay(current))
                                        {
                                            world.setBlockState(posTop, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
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

    private void generateEarthenwareClaySurface(World world, Random rng, BlockPos start)
    {
        if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);

            int radius = rng.nextInt(5) + 1;
            int depth = rng.nextInt(3) + 1;

            int sandyEarthenwareClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.sandyEarthenwareClayRarity);
            int sandyEarthenwareClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.sandyEarthenwareClayLoamRarity);
            int kaoliniteClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.kaoliniteClayRarity);
            int kaoliniteClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.kaoliniteClayLoamRarity);
            int siltyEarthenwareClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltyEarthenwareClayLoamRarity);
            int kaoliniteClayHumusRarity = rng.nextInt(ConfigTFCF.General.WORLD.kaoliniteClayHumusRarity);
            int siltyEarthenwareClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltyEarthenwareClayRarity);

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

                        if (data.getRainfall() < RAINFALL_SANDY && ConfigTFCF.General.WORLD.enableAllSpecialSoil)
                        {
                            if (data.getRainfall() > RAINFALL_SAND_SANDY_MIX)
                            {
                                if (sandyEarthenwareClayRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_EARTHENWARE_CLAY).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_EARTHENWARE_CLAY_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_EARTHENWARE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                            else if (data.getRainfall() > RAINFALL_SAND)
                            {
                                if (sandyEarthenwareClayLoamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                        else if (data.getRainfall() > RAINFALL_SANDY)
                        {
                            if (kaoliniteClayRarity == 0)
                            {
                                if (BlocksTFC.isDirt(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.EARTHENWARE_CLAY).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_EARTHENWARE_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFC.isDryGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_EARTHENWARE_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFC.isGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.EARTHENWARE_CLAY_GRASS).getDefaultState(), 2);
                                }
                            }
                            if (data.getRainfall() < RAINFALL_SILTY && ConfigTFCF.General.WORLD.enableAllSpecialSoil)
                            {
                                if (kaoliniteClayLoamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.EARTHENWARE_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.EARTHENWARE_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_EARTHENWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_EARTHENWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.EARTHENWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                        else if (data.getRainfall() > RAINFALL_SILTY && ConfigTFCF.General.WORLD.enableAllSpecialSoil)
                        {
                            if (data.getRainfall() < RAINFALL_SILT_SILTY_MIX)
                            {
                                if (siltyEarthenwareClayLoamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                            else if (data.getRainfall() < RAINFALL_SILT)
                            {
                                if (kaoliniteClayHumusRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.EARTHENWARE_CLAY_HUMUS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_EARTHENWARE_CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.EARTHENWARE_CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                            else
                            {
                                if (siltyEarthenwareClayRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_EARTHENWARE_CLAY).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_EARTHENWARE_CLAY_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_EARTHENWARE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                        if (rng.nextInt(10) == 0)
                        {
                            final BlockPos posTop = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, posTop));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(posTop) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, posTop), ChunkDataTFC.getRainfall(world, posTop), world.getLightFor(EnumSkyBlock.SKY, posTop)) &&
                                        world.isAirBlock(posTop) &&
                                        plantBlock.canBlockStay(world, posTop, state))
                                    {
                                        if (BlocksTFC.isClay(current) || BlocksTFCF.isClay(current))
                                        {
                                            world.setBlockState(posTop, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
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

    private void generateKaoliniteClaySurface(World world, Random rng, BlockPos start)
    {
        if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);

            int radius = rng.nextInt(5) + 1;
            int depth = rng.nextInt(3) + 1;

            int sandyKaoliniteClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.sandyKaoliniteClayRarity);
            int sandyKaoliniteClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.sandyKaoliniteClayLoamRarity);
            int kaoliniteClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.kaoliniteClayRarity);
            int kaoliniteClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.kaoliniteClayLoamRarity);
            int siltyKaoliniteClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltyKaoliniteClayLoamRarity);
            int kaoliniteClayHumusRarity = rng.nextInt(ConfigTFCF.General.WORLD.kaoliniteClayHumusRarity);
            int siltyKaoliniteClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltyKaoliniteClayRarity);

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

                        if (data.getRainfall() < RAINFALL_SANDY && ConfigTFCF.General.WORLD.enableAllSpecialSoil)
                        {
                            if (data.getRainfall() > RAINFALL_SAND_SANDY_MIX)
                            {
                                if (sandyKaoliniteClayRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_KAOLINITE_CLAY).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_KAOLINITE_CLAY_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
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
                            }
                            else if (data.getRainfall() > RAINFALL_SAND)
                            {
                                if (sandyKaoliniteClayLoamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_KAOLINITE_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                        else if (data.getRainfall() > RAINFALL_SANDY)
                        {
                            if (kaoliniteClayRarity == 0)
                            {
                                if (BlocksTFC.isDirt(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.KAOLINITE_CLAY).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFC.isDryGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFC.isGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                }
                            }
                            if (data.getRainfall() < RAINFALL_SILTY && ConfigTFCF.General.WORLD.enableAllSpecialSoil)
                            {
                                if (kaoliniteClayLoamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.KAOLINITE_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.KAOLINITE_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                        else if (data.getRainfall() > RAINFALL_SILTY && ConfigTFCF.General.WORLD.enableAllSpecialSoil)
                        {
                            if (data.getRainfall() < RAINFALL_SILT_SILTY_MIX)
                            {
                                if (siltyKaoliniteClayLoamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_KAOLINITE_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                            else if (data.getRainfall() < RAINFALL_SILT)
                            {
                                if (kaoliniteClayHumusRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.KAOLINITE_CLAY_HUMUS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_KAOLINITE_CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.KAOLINITE_CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                            else
                            {
                                if (siltyKaoliniteClayRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_KAOLINITE_CLAY).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_KAOLINITE_CLAY_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_KAOLINITE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                        if (rng.nextInt(10) == 0)
                        {
                            final BlockPos posTop = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, posTop));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(posTop) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, posTop), ChunkDataTFC.getRainfall(world, posTop), world.getLightFor(EnumSkyBlock.SKY, posTop)) &&
                                        world.isAirBlock(posTop) &&
                                        plantBlock.canBlockStay(world, posTop, state))
                                    {
                                        if (BlocksTFC.isClay(current) || BlocksTFCF.isClay(current))
                                        {
                                            world.setBlockState(posTop, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
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

    private void generateStonewareClaySurface(World world, Random rng, BlockPos start)
    {
        if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);

            int radius = rng.nextInt(5) + 1;
            int depth = rng.nextInt(3) + 1;

            int sandyStonewareClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.sandyStonewareClayRarity);
            int sandyStonewareClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.sandyStonewareClayLoamRarity);
            int kaoliniteClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.kaoliniteClayRarity);
            int kaoliniteClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.kaoliniteClayLoamRarity);
            int siltyStonewareClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltyStonewareClayLoamRarity);
            int kaoliniteClayHumusRarity = rng.nextInt(ConfigTFCF.General.WORLD.kaoliniteClayHumusRarity);
            int siltyStonewareClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltyStonewareClayRarity);

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

                        if (data.getRainfall() < RAINFALL_SANDY && ConfigTFCF.General.WORLD.enableAllSpecialSoil)
                        {
                            if (data.getRainfall() > RAINFALL_SAND_SANDY_MIX)
                            {
                                if (sandyStonewareClayRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_STONEWARE_CLAY).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_STONEWARE_CLAY_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_STONEWARE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_STONEWARE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                            else if (data.getRainfall() > RAINFALL_SAND)
                            {
                                if (sandyStonewareClayLoamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_STONEWARE_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_STONEWARE_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_STONEWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                        else if (data.getRainfall() > RAINFALL_SANDY)
                        {
                            if (kaoliniteClayRarity == 0)
                            {
                                if (BlocksTFC.isDirt(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.STONEWARE_CLAY).getDefaultState(), 2);
                                }
                                else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_STONEWARE_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFC.isDryGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_STONEWARE_CLAY_GRASS).getDefaultState(), 2);
                                }
                                else if (BlocksTFC.isGrass(current))
                                {
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.STONEWARE_CLAY_GRASS).getDefaultState(), 2);
                                }
                            }
                            if (data.getRainfall() < RAINFALL_SILTY && ConfigTFCF.General.WORLD.enableAllSpecialSoil)
                            {
                                if (kaoliniteClayLoamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.STONEWARE_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.STONEWARE_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_STONEWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_STONEWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.STONEWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                        else if (data.getRainfall() > RAINFALL_SILTY && ConfigTFCF.General.WORLD.enableAllSpecialSoil)
                        {
                            if (data.getRainfall() < RAINFALL_SILT_SILTY_MIX)
                            {
                                if (siltyStonewareClayLoamRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_STONEWARE_CLAY_LOAM).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_STONEWARE_CLAY_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_STONEWARE_CLAY_LOAM_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                            else if (data.getRainfall() < RAINFALL_SILT)
                            {
                                if (kaoliniteClayHumusRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.STONEWARE_CLAY_HUMUS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_STONEWARE_CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_STONEWARE_CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.STONEWARE_CLAY_HUMUS_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                            else
                            {
                                if (siltyStonewareClayRarity == 0)
                                {
                                    if (BlocksTFC.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_STONEWARE_CLAY).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_STONEWARE_CLAY_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_STONEWARE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_STONEWARE_CLAY_GRASS).getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                        if (rng.nextInt(10) == 0)
                        {
                            final BlockPos posTop = world.getTopSolidOrLiquidBlock(posHorizontal);

                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                            {
                                if (plant.getIsClayMarking())
                                {
                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                    IBlockState state = plantBlock.getDefaultState();
                                    int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, posTop));

                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(posTop) &&
                                        plant.isValidLocation(ClimateTFC.getActualTemp(world, posTop), ChunkDataTFC.getRainfall(world, posTop), world.getLightFor(EnumSkyBlock.SKY, posTop)) &&
                                        world.isAirBlock(posTop) &&
                                        plantBlock.canBlockStay(world, posTop, state))
                                    {
                                        if (BlocksTFC.isClay(current) || BlocksTFCF.isClay(current))
                                        {
                                            world.setBlockState(posTop, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
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

    private void generateBogIron(World world, Random rng, BlockPos start)
    {
        if (ConfigTFCF.General.WORLD.enableAllBogIron)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (rng.nextInt(ConfigTFCF.General.WORLD.bogIronRarity) == 0 && start.getY() <= 150 && data.getAverageTemp() >= 0f)
            {
                final Biome b = world.getBiome(start);
                if (b == BiomesTFC.SWAMPLAND)
                {
                    int radius = rng.nextInt(5) + 2;
                    int depth = rng.nextInt(3) + 1;

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

                                if (data.getRainfall() >= RAINFALL_SAND_SANDY_MIX)
                                {
                                    if (BlocksTFC.isDirt(current) || BlocksTFCF.isDirt(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.BOG_IRON).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.BOG_IRON_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_BOG_IRON_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isDryGrass(current) || BlocksTFCF.isDryGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_BOG_IRON_GRASS).getDefaultState(), 2);
                                    }
                                    else if (BlocksTFC.isGrass(current) || BlocksTFCF.isGrass(current))
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_BOG_IRON_GRASS).getDefaultState(), 2);
                                    }
                                }
                                if (rng.nextInt(15) == 0)
                                {
                                    final BlockPos posTop = world.getTopSolidOrLiquidBlock(posHorizontal);

                                    for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
                                    {
                                        if (plant.getIsClayMarking())
                                        {
                                            BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                                            IBlockState state = plantBlock.getDefaultState();
                                            int plantAge = plant.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(world, posTop));

                                            if (!world.provider.isNether() && !world.isOutsideBuildHeight(posTop) &&
                                                plant.isValidLocation(ClimateTFC.getActualTemp(world, posTop), ChunkDataTFC.getRainfall(world, posTop), world.getLightFor(EnumSkyBlock.SKY, posTop)) &&
                                                world.isAirBlock(posTop) &&
                                                plantBlock.canBlockStay(world, posTop, state))
                                            {
                                                if (BlocksTFC.isClay(current) || BlocksTFCF.isClay(current))
                                                {
                                                    world.setBlockState(posTop, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
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
    }
}