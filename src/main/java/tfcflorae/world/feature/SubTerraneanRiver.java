package tfcflorae.world.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.world.biome.BiomeExtension;
import net.dries007.tfc.world.biome.TFCBiomes;
import net.dries007.tfc.world.noise.Noise3D;
import net.dries007.tfc.world.noise.OpenSimplex3D;

import tfcflorae.Config;
import tfcflorae.common.TFCFTags;
import tfcflorae.interfaces.TFCBiomesMixinInterface;

import static net.dries007.tfc.world.BiomeNoiseSampler.*;

public class SubTerraneanRiver extends Feature<NoneFeatureConfiguration>
{
    private static TFCBiomes staticBiomes = new TFCBiomes();

    public static final BiomeExtension ALPINE_MOUNTAINS = ((TFCBiomesMixinInterface) (Object) staticBiomes).getStaticAlpineMountains();
    public static final BiomeExtension CHASMS = ((TFCBiomesMixinInterface) (Object) staticBiomes).getStaticChasms();

    public static final int REFERENCE_DEPTH = -246;
    public static final float HOLLOWING_THRESHOLD = 0.85f;//AIR_THRESHOLD;

    public SubTerraneanRiver(Codec<NoneFeatureConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        if (!Config.COMMON.enableUndergroundRivers.get()) return false; // Stop early.

        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final Random random = context.random();

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int i = 0; i < 16; ++i)
        {
            for (int j = 0; j < 16; ++j)
            {
                int x = pos.getX() + i;
                int z = pos.getZ() + j;
                mutablePos.set(x, 0, z);

                BiomeExtension biome = TFCBiomes.getExtension(level, level.getBiome(mutablePos).value());
                if (biome.isRiver() || biome.equals(CHASMS) || biome.equals(ALPINE_MOUNTAINS) || biome.toString().equals(TFCFTags.Biomes.IS_RIVER.toString()) || biome.toString().equals(TFCFTags.Biomes.IS_LAKE.toString()))
                {
                    for (int y = REFERENCE_DEPTH - 10; y < REFERENCE_DEPTH + 23; ++y)
                    {
                        mutablePos.set(x, y, z);

                        double carving = carvingNoise(level.getSeed() + 1, x, y, z); // 0 = solid, while > 0.4 air
                        //TFCFlorae.LOGGER.debug("Carving noise is " + carving + " at coords: " + x + ", " + y + ", " + z);
                        if (y > level.getMinBuildHeight() && y < level.getMaxBuildHeight() && level.getBlockState(mutablePos).getBlock() != Blocks.BEDROCK)
                        {
                            if (carving >= HOLLOWING_THRESHOLD && (EnvironmentHelpers.isWorldgenReplaceable(level, mutablePos) || level.getBlockState(mutablePos).getMaterial() == Material.STONE || level.getBlockState(mutablePos).getMaterial() == Material.DIRT))
                            {
                                if (y <= REFERENCE_DEPTH)
                                {
                                    level.setBlock(mutablePos, Blocks.LAVA.defaultBlockState(), Block.UPDATE_ALL);
                                }
                                else
                                {
                                    level.setBlock(mutablePos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                                }
                            }
                            else if (carving < HOLLOWING_THRESHOLD && carving >= random.nextFloat(3) * 0.1f && y < REFERENCE_DEPTH - 2 && EnvironmentHelpers.isWorldgenReplaceable(level, mutablePos))
                            {
                                level.setBlock(mutablePos, TFCBlocks.ROCK_BLOCKS.get(Rock.BASALT).get(Rock.BlockType.RAW).get().defaultBlockState(), Block.UPDATE_ALL);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /*public static BiomeNoiseSampler riverSampler(long seed)
    {
        Noise2D riverHeight = new OpenSimplex2D(seed).octaves(4).spread(0.2f).scaled(REFERENCE_DEPTH - 11, REFERENCE_DEPTH - 5);
        Noise3D cliffNoise = new OpenSimplex3D(seed).octaves(2).spread(0.1f).scaled(0, 3);

        return new BiomeNoiseSampler()
        {
            private double height;
            private int x, z;

            @Override
            public void setColumn(int x, int z)
            {
                height = riverHeight.noise(x, z);
                this.x = x;
                this.z = z;
            }

            @Override
            public double height()
            {
                return height;
            }

            @Override
            public double noise(int y)
            {
                if (y > REFERENCE_DEPTH + 20)
                {
                    return SOLID;
                }
                else if (y > REFERENCE_DEPTH + 10)
                {
                    double easing = 1 - (y - REFERENCE_DEPTH - 10) / 10f;
                    return easing * cliffNoise.noise(x, y, z);
                }
                else if (y > REFERENCE_DEPTH)
                {
                    return cliffNoise.noise(x, y, z);
                }
                else if (y > REFERENCE_DEPTH - 8)
                {
                    double easing = (y - REFERENCE_DEPTH + 8) / 8d;
                    return easing * cliffNoise.noise(x, y, z);
                }
                return SOLID;
            }
        };
    }*/

    public double carvingNoise(long seed, int x, int y, int z)
    {
        Noise3D cliffNoise = new OpenSimplex3D(seed).octaves(2).spread(0.1f).scaled(0, 3);
        if (y > REFERENCE_DEPTH + 20)
        {
            return SOLID;
        }
        else if (y > REFERENCE_DEPTH + 10)
        {
            double easing = 1 - (y - REFERENCE_DEPTH - 10) / 10f;
            return easing * cliffNoise.noise(x, y, z);
        }
        else if (y > REFERENCE_DEPTH)
        {
            return cliffNoise.noise(x, y, z);
        }
        else if (y > REFERENCE_DEPTH - 8)
        {
            double easing = (y - REFERENCE_DEPTH + 8) / 8d;
            return easing * cliffNoise.noise(x, y, z);
        }
        return SOLID;
    }
}