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

    public static Noise2D extremeBadlands(long seed, int minHeight, int maxHeight)
    {
        return new OpenSimplex2D(seed)
            .octaves(4)
            .spread(0.025f * (minHeight * 0.6f))
            .scaled(SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight)
            .add(new OpenSimplex2D(seed + 1)
                .octaves(4)
                .spread(0.04f)
                .ridged()
                .map(x -> 1.3f * -(x > 0 ? x * x * x : 0.5f * x))
                .scaled(-1f, 0.3f, -1f, 1f)
                .terraces(15)
                .scaled(-19.5f, 0 + maxHeight)
            )
            .map(x -> x < SEA_LEVEL_Y ? SEA_LEVEL_Y - 0.3f * (SEA_LEVEL_Y - x) : x);
    }

    public static Noise2D moorlands(long seed, int minHeight, int maxHeight)
    {
        return new OpenSimplex2D(seed)
            .octaves(4)
            .spread(0.2f / minHeight)
            .scaled(SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight)
            .add(new OpenSimplex2D(seed + 1)
                .octaves(4)
                .spread(0.01f)
                .ridged()
                .map(x -> 1.3f * -(x > 0 ? x * x * x : 0.5f * x))
                .scaled(-1f, 0.3f, -1f * 1.3f, 1f * 1.3f)
                .terraces(15)
                .scaled(-19.5f, 0)
            )
            .map(x -> x < SEA_LEVEL_Y ? SEA_LEVEL_Y - 0.3f * 1.3f * (SEA_LEVEL_Y - x) : x);
    }

    public static Noise2D lake(long seed, int minHeight, int maxHeight)
    {
        return new OpenSimplex2D(seed).octaves(4).spread(0.15f).scaled(SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight);
    }

    public static Noise2D riverBank(long seed, int minHeight, int maxHeight)
    {
        return new OpenSimplex2D(seed).octaves(4).spread(0.8f).scaled(SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight).clamped(SEA_LEVEL_Y - 11, SEA_LEVEL_Y + 2);
    }

    public static BiomeNoiseSampler riverSampler(long seed)
    {
        Noise2D riverHeight = new OpenSimplex2D(seed).octaves(4).spread(0.2f).scaled(SEA_LEVEL_Y - 11, SEA_LEVEL_Y - 5);
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
                if (y > SEA_LEVEL_Y + 20)
                {
                    return SOLID;
                }
                else if (y > SEA_LEVEL_Y + 10)
                {
                    double easing = 1 - (y - SEA_LEVEL_Y - 10) / 10f;
                    return easing * cliffNoise.noise(x, y, z);
                }
                else if (y > SEA_LEVEL_Y)
                {
                    return cliffNoise.noise(x, y, z);
                }
                else if (y > SEA_LEVEL_Y - 8)
                {
                    double easing = (y - SEA_LEVEL_Y + 8) / 8d;
                    return easing * cliffNoise.noise(x, y, z);
                }
                return AIR_THRESHOLD;
            }
        };
    }

    public static BiomeNoiseSampler riverBankSampler(long seed)
    {
        //Noise2D riverBankHeight = new OpenSimplex2D(seed).octaves(0).spread(0f).scaled(SEA_LEVEL_Y - 11, SEA_LEVEL_Y + 10);
        Noise2D riverBankHeight = new OpenSimplex2D(seed).octaves(4).spread(0.2f).scaled(SEA_LEVEL_Y - 11, SEA_LEVEL_Y - 5);
        Noise3D cliffNoise = new OpenSimplex3D(seed).octaves(2).spread(0.1f).scaled(0, 3);

        return new BiomeNoiseSampler()
        {
            private double height;
            private int x, z;

            @Override
            public void setColumn(int x, int z)
            {
                height = riverBankHeight.noise(x, z);
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
                if (y > SEA_LEVEL_Y + 2)
                {
                    return ((Math.sin(y * 8) / 15) + Math.pow(1.02D, y - SEA_LEVEL_Y) - 0.4D) * (cliffNoise.noise(x, y, z) / 2);
                }
                else if (y > SEA_LEVEL_Y)
                {
                    //return (Math.pow(1.3D, y - SEA_LEVEL_Y - 13) - 2) / SEA_LEVEL_Y;
                    //return (Math.pow(1.25D, y - 47) - 8) / (SEA_LEVEL_Y * 1.25D); // Terracing
                    //return (Math.pow(1.08D, y) - (SEA_LEVEL_Y * 2)) / 40;
                    return (Math.pow(1.08D, y) - (SEA_LEVEL_Y * 2.1)) / 40;
                }
                else if (y > 0)
                {
                    //return (Math.pow(1.3D, y - SEA_LEVEL_Y - 13) - 2) / SEA_LEVEL_Y;
                    //return (Math.pow(1.25D, y - 47) - 8) / (SEA_LEVEL_Y * 1.25D); // Terracing
                    return 0.0D;//(y / (63 * 4)) - 0.22D;
                }
                return SOLID;
            }
        };
    }

    public static BiomeNoiseSampler chasmSampler(long seed)
    {
        Noise2D riverBankHeight = new OpenSimplex2D(seed).octaves(4).spread(0.2f).scaled(SEA_LEVEL_Y - 1, SEA_LEVEL_Y + 11);
        Noise3D cliffNoise = new OpenSimplex3D(seed).octaves(2).spread(0.1f).scaled(0, 3);

        return new BiomeNoiseSampler()
        {
            private double height;
            private int x, z;

            @Override
            public void setColumn(int x, int z)
            {
                height = riverBankHeight.noise(x, z);
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
                if (y > SEA_LEVEL_Y + 40 + (cliffNoise.noise(x, y, z) / 2))
                {
                    //return (Math.pow(1.3D, y - SEA_LEVEL_Y - 13) - 2) / SEA_LEVEL_Y;
                    //return (Math.pow(1.25D, y - 47) - 8) / (SEA_LEVEL_Y * 1.25D); // Terracing
                    //return (Math.pow(1.08D, y) - (SEA_LEVEL_Y * 2)) / 40;
                    return -((Math.pow(1.08D, y) - (SEA_LEVEL_Y * SEA_LEVEL_Y)) / -2800) * (cliffNoise.noise(x, y, z) / 2);
                }
                else if (y > SEA_LEVEL_Y + 2)
                {
                    return ((Math.sin(y * 8) / 15) + Math.pow(1.02D, y - SEA_LEVEL_Y) - 0.4D) * (cliffNoise.noise(x, y, z) / 2);
                }
                else if (y > SEA_LEVEL_Y)
                {
                    //return (Math.pow(1.3D, y - SEA_LEVEL_Y - 13) - 2) / SEA_LEVEL_Y;
                    //return (Math.pow(1.25D, y - 47) - 8) / (SEA_LEVEL_Y * 1.25D); // Terracing
                    //return (Math.pow(1.08D, y) - (SEA_LEVEL_Y * 2)) / 40;
                    return (Math.pow(1.08D, y) - (SEA_LEVEL_Y * 2.1)) / 40;
                }
                else if (y > 0)
                {
                    //return (Math.pow(1.3D, y - SEA_LEVEL_Y - 13) - 2) / SEA_LEVEL_Y;
                    //return (Math.pow(1.25D, y - 47) - 8) / (SEA_LEVEL_Y * 1.25D); // Terracing
                    return 0.0D;//(y / (63 * 4)) - 0.22D;
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
        final int rng = generator.nextInt(SEA_LEVEL_Y / 2);

        Noise2D noise = new OpenSimplex2D(generator.nextLong()).octaves(4).spread(0.01f).scaled(SEA_LEVEL_Y - rng, SEA_LEVEL_Y + 96);
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
                    .scaled(rng, 40)));
        }
        return noise;
    }

    public static Noise2D mountainsBadlands(long seed, int baseHeight, int scaleHeight)
    {
        final Random generator = new Random(seed);

        final Noise2D baseNoise = new OpenSimplex2D(seed) // A simplex noise forms the majority of the base
            .octaves(6) // High octaves to create highly fractal terrain
            .spread(0.14f)
            .add(new OpenSimplex2D(seed + 1) // Ridge noise is added to mimic real mountain ridges. It is scaled smaller than the base noise to not be overpowering
                .octaves(4)
                .spread(0.02f)
                .scaled(-0.7f, 0.7f)
                .ridged() // Ridges are applied after octaves as it creates less directional artifacts this way
            )
            .map(x -> {
                final float x0 = 0.125f * (x + 1) * (x + 1) * (x + 1); // Power scaled, flattens most areas but maximizes peaks
                return SEA_LEVEL_Y + baseHeight + scaleHeight * x0; // Scale the entire thing to mountain ranges
            });

        // Cliff noise consists of noise that's been artificially clamped over half the domain, which is then selectively added above a base height level
        // This matches up with the distinction between dirt and stone
        final Noise2D cliffNoise = new OpenSimplex2D(seed + 2).octaves(2).spread(0.01f).scaled(-25, 25).map(x -> x > 0 ? x : 0);
        final Noise2D cliffHeightNoise = new OpenSimplex2D(seed + 3)
        .octaves(2)
        .spread(0.01f)
        .scaled(140 - 20, 140 + 20)
            .lazyProduct(new OpenSimplex2D(generator.nextLong())
                .octaves(4)
                .spread(0.1f)
                .scaled(5, 11))
                    .ridged();

        return (x, z) -> {
            float height = baseNoise.noise(x, z);
            if (height > 120) // Only sample each cliff noise layer if the base noise could be influenced by it
            {
                final float cliffHeight = cliffHeightNoise.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            return height;
        };
    }

    public static BiomeNoiseSampler undergroundRivers(long seed, Noise2D heightNoise)
    {
        final Noise2D carvingCenterNoise = new OpenSimplex2D(seed).octaves(2).spread(0.02f).scaled(SEA_LEVEL_Y - 3, SEA_LEVEL_Y + 3);
        final Noise2D carvingHeightNoise = new OpenSimplex2D(seed + 1).octaves(4).spread(0.15f).scaled(8, 14);

        return BiomeNoiseSampler.fromHeightAndCarvingNoise(heightNoise, carvingCenterNoise, carvingHeightNoise);
    }

    public static BiomeNoiseSampler carvingBadlands(long seed, Noise2D heightNoise)
    {
        final Noise2D blobsNoise = new OpenSimplex2D(seed + 1).octaves(6).spread(0.016f).abs();
        final Noise2D depthNoise = new OpenSimplex2D(seed + 2).octaves(6).scaled(10, 60).spread(0.02f);
        final Noise2D centerNoise = new OpenSimplex2D(seed + 3).octaves(6).spread(0.01f).scaled(SEA_LEVEL_Y, SEA_LEVEL_Y + 100);

        return new BiomeNoiseSampler()
        {
            private float surfaceHeight, center, height;

            @Override
            public void setColumn(int x, int z)
            {
                float h0 = Mth.clamp((0.5f - blobsNoise.noise(x, z)) * (1 / 0.3f), 0, 100);
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
                return Mth.clamp(0.4f + 0.05f * (height - delta), 0, 20);
            }
        };
    }

    public static BiomeNoiseSampler undergroundCavesUpper(long seed, Noise2D heightNoise)
    {
        final Noise2D blobsNoise = new OpenSimplex2D(seed + 1).octaves(4).spread(0.016f).abs();
        final Noise2D depthNoise = new OpenSimplex2D(seed + 2).octaves(4).scaled(0, 18).spread(0.02f);
        final Noise2D centerNoise = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(SEA_LEVEL_Y - 50, SEA_LEVEL_Y - 20);

        return new BiomeNoiseSampler()
        {
            private float surfaceHeight, center, height;

            @Override
            public void setColumn(int x, int z)
            {
                float h0 = Mth.clamp((0.5f - blobsNoise.noise(x, z)) * (1 / 0.3f), 0, 20);
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
                return Mth.clamp(0.4f + 0.05f * (height - delta), 0, 20);
            }
        };
    }

    public static BiomeNoiseSampler undergroundCaves(long seed, Noise2D heightNoise)
    {
        final Noise2D blobsNoise = new OpenSimplex2D(seed + 1).octaves(4).spread(0.016f).abs();
        final Noise2D depthNoise = new OpenSimplex2D(seed + 2).octaves(4).scaled(2, 18).spread(0.02f);
        final Noise2D centerNoise = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(SEA_LEVEL_Y - 93, SEA_LEVEL_Y - 53);

        return new BiomeNoiseSampler()
        {
            private float surfaceHeight, center, height;

            @Override
            public void setColumn(int x, int z)
            {
                float h0 = Mth.clamp((0.5f - blobsNoise.noise(x, z)) * (1 / 0.3f), 0, 20);
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
                return Mth.clamp(0.4f + 0.05f * (height - delta), 0, 20);
            }
        };
    }

    public static BiomeNoiseSampler undergroundCavesMiddle(long seed, Noise2D heightNoise)
    {
        final Noise2D blobsNoise = new OpenSimplex2D(seed + 1).octaves(4).spread(0.018f).abs();
        final Noise2D depthNoise = new OpenSimplex2D(seed + 2).octaves(4).scaled(2, 18).spread(0.05f);
        final Noise2D centerNoise = new OpenSimplex2D(seed + 3).octaves(2).spread(0.05f).scaled(SEA_LEVEL_Y - 183, SEA_LEVEL_Y - 163);

        return new BiomeNoiseSampler()
        {
            private float surfaceHeight, center, height;

            @Override
            public void setColumn(int x, int z)
            {
                float h0 = Mth.clamp((0.3f - blobsNoise.noise(x, z)) * (1 / 0.3f), 0, 20);
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
                return Mth.clamp(0.4f + 0.05f * (height - delta), 0, 20);
            }
        };
    }

    public static BiomeNoiseSampler undergroundCavesDeep(long seed, Noise2D heightNoise)
    {
        final Noise2D blobsNoise = new OpenSimplex2D(seed + 1).octaves(4).spread(0.018f).abs();
        final Noise2D depthNoise = new OpenSimplex2D(seed + 2).octaves(4).scaled(2, 26).spread(0.2f);
        final Noise2D centerNoise = new OpenSimplex2D(seed + 3).octaves(2).spread(0.05f).scaled(SEA_LEVEL_Y - 273, SEA_LEVEL_Y - 253);

        return new BiomeNoiseSampler()
        {
            private float surfaceHeight, center, height;

            @Override
            public void setColumn(int x, int z)
            {
                float h0 = Mth.clamp((0.3f - blobsNoise.noise(x, z)) * (1 / 0.3f), 0, 20);
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
                return Mth.clamp(0.4f + 0.05f * (height - delta), 0, 20);
            }
        };
    }
}
