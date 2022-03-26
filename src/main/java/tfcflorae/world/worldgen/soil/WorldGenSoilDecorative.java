package tfcflorae.world.worldgen.soil;

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

public class WorldGenSoilDecorative implements IWorldGenerator
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
            if (ConfigTFCF.General.WORLD.enableSandGen)
            {
                BlockPos pos1 = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateSand(world, random, pos1);
                BlockPos pos2 = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateSand(world, random, pos2);
            }

            if (ConfigTFCF.General.WORLD.enableAllPodzol)
            {
                BlockPos pos1 = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generatePodzol(world, random, pos1);
                BlockPos pos2 = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generatePodzol(world, random, pos2);
                BlockPos pos3 = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generatePodzol(world, random, pos3);
            }

            if (ConfigTFCF.General.WORLD.enableMudGen)
            {
                BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateMud(world, random, pos);
            }

            if (ConfigTFCF.General.WORLD.enableAllBogIron)
            {
                BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
                generateBogIron(world, random, pos);
            }

            BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
            generatePeat(world, random, pos);
        }
    }

    private void generateSand(World world, Random rng, BlockPos start)
    {
        if (ConfigTFCF.General.WORLD.enableAllBlockTypes && ConfigTFCF.General.WORLD.enableSandGen)
        {
            final Biome b = world.getBiome(start);
            if (b == BiomesTFC.OCEAN || b == BiomesTFC.DEEP_OCEAN || b == BiomesTFC.BEACH || b == BiomesTFC.LAKE)
            {
                ChunkDataTFC data = ChunkDataTFC.get(world, start);
                if (data.isInitialized() && start.getY() <= WorldTypeTFC.SEALEVEL && data.getFloraDensity() >= 0.2f + (rng.nextGaussian() / 10) && ChunkDataTFC.getRainfall(world, start) >= RAINFALL_SAND + 15)
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
                                if (BlocksTFC.isSoilOrGravel(current) || BlocksTFC.isRawStone(current))
                                {
                                    world.setBlockState(pos, BlockRockVariant.get(ChunkDataTFC.getRockHeight(world, pos), Rock.Type.SAND).getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }
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
                if (data.isInitialized() && data.getRainfall() >= 90f && data.getFloraDensity() >= 0.35f + 0.05f * rng.nextGaussian() && ChunkDataTFC.getDrainage(world, start) >= 2 * rng.nextGaussian())
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

                                if (current.getBlock() instanceof BlockRockVariant)
                                {
                                    BlockRockVariant blockRock = (BlockRockVariant) current.getBlock();

                                    if (blockRock.getType() == Rock.Type.GRASS || blockRock.getType() == Rock.Type.DRY_GRASS)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.PODZOL).getDefaultState(), 2);
                                    }
                                }
                                else if (current.getBlock() instanceof BlockRockVariantTFCF)
                                {
                                    BlockRockVariantTFCF blockRockTFCF = (BlockRockVariantTFCF) current.getBlock();

                                    if (blockRockTFCF.getType() == RockTFCF.LOAMY_SAND_GRASS || blockRockTFCF.getType() == RockTFCF.DRY_LOAMY_SAND_GRASS)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.LOAMY_SAND_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (blockRockTFCF.getType() == RockTFCF.SANDY_LOAM_GRASS || blockRockTFCF.getType() == RockTFCF.DRY_SANDY_LOAM_GRASS)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (blockRockTFCF.getType() == RockTFCF.LOAM_GRASS || blockRockTFCF.getType() == RockTFCF.DRY_LOAM_GRASS)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (blockRockTFCF.getType() == RockTFCF.SILT_LOAM_GRASS || blockRockTFCF.getType() == RockTFCF.DRY_SILT_LOAM_GRASS)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILT_LOAM_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (blockRockTFCF.getType() == RockTFCF.SILT_GRASS || blockRockTFCF.getType() == RockTFCF.DRY_SILT_GRASS)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILT_PODZOL).getDefaultState(), 2);
                                    }
                                    else if (blockRockTFCF.getType() == RockTFCF.HUMUS_GRASS || blockRockTFCF.getType() == RockTFCF.DRY_HUMUS_GRASS)
                                    {
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_HUMUS_GRASS).getDefaultState(), 2);
                                    }
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
        if (rng.nextInt(ConfigTFCF.General.WORLD.mudRarity) == 0 && start.getY() >= WorldTypeTFC.SEALEVEL && start.getY() <= 150 && ChunkDataTFC.getDrainage(world, start) <= 2 * rng.nextGaussian())
        {
            final Biome b = world.getBiome(start);
            if (b == BiomesTFC.SWAMPLAND || b == BiomesTFC.BAYOU || b == BiomesTFC.MANGROVE || b == BiomesTFC.MARSH)
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

    private void generateBogIron(World world, Random rng, BlockPos start)
    {
        if (ConfigTFCF.General.WORLD.enableAllBogIron)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, start);
            if (rng.nextInt(ConfigTFCF.General.WORLD.bogIronRarity) == 0 && start.getY() <= 150 && data.getAverageTemp() >= 0f && ChunkDataTFC.getDrainage(world, start) <= 2 * rng.nextGaussian())
            {
                final Biome b = world.getBiome(start);
                if (b == BiomesTFC.SWAMPLAND || b == BiomesTFC.BAYOU || b == BiomesTFC.MANGROVE || b == BiomesTFC.MARSH)
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

    private boolean generatePeat(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(4) + 4;
        byte depth = 2;

        if (rng.nextInt(30) != 0 || start.getY() > WorldTypeTFC.SEALEVEL) return false;
        ChunkDataTFC data = ChunkDataTFC.get(world, start);
        final Biome b = world.getBiome(start);
        if (b == BiomesTFC.SWAMPLAND || b == BiomesTFC.BAYOU || b == BiomesTFC.MANGROVE || b == BiomesTFC.MARSH)
        {
            if (data.isInitialized() && data.getRainfall() >= 375f && data.getFloraDiversity() >= 0.5f && data.getFloraDensity() >= 0.5f && world.getBiome(start).getHeightVariation() < 0.15)
                return false;

            for (int x = -radius; x <= radius; ++x)
            {
                for (int z = -radius; z <= radius; ++z)
                {
                    if (x * x + z * z > radius * radius) continue;

                    for (int y = -depth; y <= depth; ++y)
                    {
                        final BlockPos pos = start.add(x, y, z);
                        final IBlockState current = world.getBlockState(pos);

                        if (BlocksTFC.isGrass(current))
                        {
                            world.setBlockState(pos, BlocksTFC.PEAT_GRASS.getDefaultState(), 2);
                        }
                        else if (BlocksTFC.isDirt(current) || BlocksTFC.isClay(current))
                        {
                            world.setBlockState(pos, BlocksTFC.PEAT.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}