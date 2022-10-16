package tfcflorae.world.biome;

import java.util.Random;

import net.minecraft.util.Mth;

import net.dries007.tfc.world.BiomeNoiseSampler;
import net.dries007.tfc.world.noise.*;

import static net.dries007.tfc.world.TFCChunkGenerator.SEA_LEVEL_Y;

/**
 * Collections of biome noise factories
 * These are built by hand and assigned to different biomes
 */
public final class TFCFBiomeNoise
{
    /**
     * Generates a flat base with twisting carved canyons using many smaller terraces.
     * Inspired by imagery of Drumheller, Alberta
     */
    public static Noise2D badlands(long seed, int minHeight, int maxHeight)
    {
        return new OpenSimplex2D(seed)
            .octaves(4)
            .spread(0.025f)
            .scaled(SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight)
            .add(new OpenSimplex2D(seed + 1)
                .octaves(4)
                .spread(0.04f)
                .ridged()
                .map(x -> 1.3f * -(x > 0 ? x * x * x : 0.5f * x))
                .scaled(-1f, 0.3f, -1f, 1f)
                .terraces(15)
                .scaled(-19.5f, 0)
            )
            .map(x -> x < SEA_LEVEL_Y ? SEA_LEVEL_Y - 0.3f * (SEA_LEVEL_Y - x) : x);
    }

    public static Noise2D lake(long seed, int minHeight, int maxHeight)
    {
        return new OpenSimplex2D(seed).octaves(4).spread(0.15f).scaled(SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight);
    }

    public static Noise2D river(long seed)
    {
        return new OpenSimplex2D(seed).octaves(4).spread(0.2f).scaled(SEA_LEVEL_Y - 8, SEA_LEVEL_Y - 2);
    }

    public static BiomeNoiseSampler riverSampler(long seed)
    {
        Noise2D riverHeight = new OpenSimplex2D(seed).octaves(4).spread(0.05f).scaled(SEA_LEVEL_Y - 11, SEA_LEVEL_Y);
        Noise3D cliffNoise = new OpenSimplex3D(seed).octaves(4).spread(0.05f).scaled(SEA_LEVEL_Y - 12, SEA_LEVEL_Y - 2);

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
                if (y > SEA_LEVEL_Y + 60)
                {
                    return SOLID;
                }
                else if (y > SEA_LEVEL_Y + 50)
                {
                    double easing = 1 - (y - SEA_LEVEL_Y - 50) / 10f;
                    return easing * cliffNoise.noise(x, y, z);
                }
                else if (y > SEA_LEVEL_Y)
                {
                    return cliffNoise.noise(x, y, z);
                }
                else if (y > SEA_LEVEL_Y - 8)
                {
                    double easing = (y - SEA_LEVEL_Y + 80) / 8d;
                    return easing * cliffNoise.noise(x, y, z);
                }
                return SOLID;
            }
        };
    }

    /**
     * Creates a variant of badlands with stacked pillar like structures, as opposed to relief carved.
     * Inspired by imagery of Bryce Canyon, Utah.
     */
    public static Noise2D grandCanyon(long seed)
    {
        final Random generator = new Random(seed);

        Noise2D noise = new OpenSimplex2D(generator.nextLong()).octaves(4).spread(0.01f).scaled(SEA_LEVEL_Y + -16, SEA_LEVEL_Y + 96);
        for (int layer = 0; layer < 3; layer++)
        {
            final float threshold = 0.25f;
            final float delta = 0.015f;

            noise = noise.add(new OpenSimplex2D(generator.nextLong())
                .octaves(3)
                .spread(0.02f + 0.01f * layer)
                .abs()
                .affine(1, -0.05f * layer)
                .map(t -> Mth.clampedMap(t, threshold, threshold + delta, 0, 1))
                .lazyProduct(new OpenSimplex2D(generator.nextLong())
                    .octaves(4)
                    .spread(0.1f)
                    .scaled(-24, 40)));
        }
        return noise;
    }

    public static BiomeNoiseSampler undergroundCaves(long seed, Noise2D heightNoise)
    {
        final Noise2D blobsNoise = new OpenSimplex2D(seed + 1).spread(0.04f).abs();
        final Noise2D depthNoise = new OpenSimplex2D(seed + 2).octaves(4).scaled(2, 18).spread(0.2f);
        final Noise2D centerNoise = new OpenSimplex2D(seed + 3).octaves(2).spread(0.06f).scaled(SEA_LEVEL_Y - 18, SEA_LEVEL_Y - 6);

        return new BiomeNoiseSampler()
        {
            private float surfaceHeight, center, height;

            @Override
            public void setColumn(int x, int z)
            {
                float h0 = Mth.clamp((0.7f - blobsNoise.noise(x, z)) * (1 / 0.3f), 0, 1);
                float h1 = depthNoise.noise(x, z);

                surfaceHeight = heightNoise.noise(x, z);
                center = centerNoise.noise(x, z);
                height = h0 * h1;
            }

            @Override
            public double height()
            {
                return surfaceHeight;
            }

            @Override
            public double noise(int y)
            {
                float delta = Math.abs(center - y);
                return Mth.clamp(0.4f + 0.05f * (height - delta), 0, 1);
            }
        };
    }

    public static BiomeNoiseSampler undergroundCavesDeep(long seed, Noise2D heightNoise)
    {
        final Noise2D blobsNoise = new OpenSimplex2D(seed + 1).spread(0.04f).abs();
        final Noise2D depthNoise = new OpenSimplex2D(seed + 2).octaves(4).scaled(8, 48).spread(0.2f);
        final Noise2D centerNoise = new OpenSimplex2D(seed + 3).octaves(2).spread(0.06f).scaled(SEA_LEVEL_Y - 64, SEA_LEVEL_Y);

        return new BiomeNoiseSampler()
        {
            private float surfaceHeight, center, height;

            @Override
            public void setColumn(int x, int z)
            {
                float h0 = Mth.clamp((0.7f - blobsNoise.noise(x, z)) * (1 / 0.3f), 0, 1);
                float h1 = depthNoise.noise(x, z);

                surfaceHeight = heightNoise.noise(x, z);
                center = centerNoise.noise(x, z);
                height = h0 * h1;
            }

            @Override
            public double height()
            {
                return surfaceHeight;
            }

            @Override
            public double noise(int y)
            {
                float delta = Math.abs(center - y);
                return Mth.clamp(0.4f + 0.05f * (height - delta), 0, 1);
            }
        };
    }
}
