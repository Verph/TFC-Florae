package tfcflorae.world.biome;

import java.util.Random;

import net.minecraft.util.Mth;

import net.dries007.tfc.world.BiomeNoiseSampler;
import net.dries007.tfc.world.biome.BiomeNoise;
import net.dries007.tfc.world.noise.*;

import static net.dries007.tfc.world.TFCChunkGenerator.SEA_LEVEL_Y;

/**
 * Collections of biome noise factories
 * These are built by hand and assigned to different biomes
 */
public final class TFCFBiomeNoise
{
    public static Noise2D lowlandsNew(long seed)
    {
        return BiomeNoise.hills(seed, -3, -2)
            .add(new OpenSimplex2D(seed + 1)
                .octaves(6)
                .spread(0.55f)
                .scaled(-2, 2)
                .clamped(-2, 1)
            );
    }

    public static Noise2D sandDunes(long seed, int baseHeight, int scaleHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed).octaves(3).spread(0.01f).scaled(-100f, 100f);
        final Noise2D baseNoise = new OpenSimplex2D(seed)
            .octaves(3)
            .spread(0.05f)
            .ridged()
            .warped(warp)
            .scaled(-0.2f, 0.8f)
            .ridged()
            .map(x -> {
                final float x0 = 0.125f * (x + 1) * (x + 1) * (x + 1);
                return SEA_LEVEL_Y + baseHeight + scaleHeight * x0;
            });

        return (x, z) -> {
            float height = baseNoise.noise(x, z);
            return height;
        };
    }

    public static Noise2D sandDunesOld(long seed, int minHeight, int maxHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed + 1).octaves(3).spread(0.01f).scaled(0.8f, -0.8f, SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight);
        return new OpenSimplex2D(seed + 1)
            .octaves(5)
            .spread(0.1f)
            .ridged()
            .warped(warp)
            .scaled(1f, 0f, SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight)
            .ridged();
    }

    public static Noise2D tablelands(long seed, int minHeight, int maxHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed + 1).octaves(4).spread(0.05f).scaled(-100f, 100f);
        return new Cellular2D(seed + 1)
            .octaves(4)
            .spread(0.025f)
            .ridged()
            .warped(warp)
            .scaled(-0.4f, 0.4f)
            .ridged()
            .clamped(SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight);
    }

    public static Noise2D funk(long seed, int minHeight, int maxHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed + 1).octaves(3).spread(0.01f).scaled(0.8f, -0.8f, SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight);
        return new OpenSimplex2D(seed + 1)
            .octaves(5)
            .spread(0.001f)
            .ridged()
            .warped(warp)
            .scaled(0.4f, -0.4f, SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight)
            .ridged();
    }

    public static Noise2D sawtoothCliffs(long seed, int minHeight, int maxHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed + 1).octaves(3).spread(0.05f).scaled(-100f, 100f);
        return new OpenSimplex2D(seed + 1)
            .octaves(3)
            .spread(0.01f)
            .ridged()
            .warped(warp)
            .add(tablelands(seed + 2, -5, 15))
            .scaled(-0.3f, 0.5f, SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight)
            .ridged()
            .clamped(SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight);
    }

    public static Noise2D pitlandCanyons(long seed, int minHeight, int maxHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed + 1).octaves(3).spread(0.05f).scaled(-100f, 100f);
        return new Cellular2D(seed)
            .warped(warp)
            .ridged()
            .octaves(4)
            .spread(0.055f)
            .ridged()
            .warped(warp)
            .scaled(-0.4f, 0.8f, SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight)
            .add(BiomeNoise.hills(seed + 1, 20, 30))
            .terraces(8);
    }

    public static Noise2D pitlandCanyonsTweaked(long seed, int minHeight, int maxHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed + 1).octaves(3).spread(0.01f).scaled(-0.8f, 0.8f, SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight);
        final Noise2D baseNoise = new OpenSimplex2D(seed + 1)
            .octaves(5)
            .spread(0.1f)
            .ridged()
            .warped(warp)
            .scaled(-30, 30)
            .ridged();

        return (x, z) -> {
            float height = baseNoise.noise(x, z);
            if (height < BiomeNoise.hills(seed, minHeight, minHeight + 10).noise(x, z))
            {
                height = BiomeNoise.hills(seed, minHeight, minHeight + 10).noise(x, z);
            }
            if (height > BiomeNoise.hills(seed + 1, maxHeight, maxHeight + 10).noise(x, z))
            {
                height = BiomeNoise.hills(seed + 1, maxHeight, maxHeight + 10).noise(x, z);
            }
            return height;
        };
    }

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
                .spread(0.02f)
                .ridged()
                .map(x -> 1.3f * -(x > 0 ? x * x * x : 0.5f * x))
                .scaled(-1f, 0.3f, -1f * 1.3f, 1f * 1.3f)
                .scaled(-19.5f, 0.7f)
            )
            .map(x -> x < SEA_LEVEL_Y ? SEA_LEVEL_Y - 0.3f * 1.3f * (SEA_LEVEL_Y - x) : x);
    }

    public static Noise2D dunes(long seed, int minHeight, int maxHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed).octaves(4).spread(0.05f).scaled(-100f, 100f);
        return new OpenSimplex2D(seed + 1)
            .octaves(4)
            .spread(0.06f)
            .warped(warp)
            .map(x -> x > 0.4 ? x - 0.8f : -x)
            .scaled(-0.4f, 1.2f, SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight);
    }

    public static Noise2D pitlands(long seed, int minHeight, int maxHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed).octaves(4).spread(0.03f).scaled(-100f, 100f);
        final Noise2D baseNoise = new OpenSimplex2D(seed + 1)
            .octaves(4)
            .spread(0.06f)
            .warped(warp)
            .map(x -> x > 0.4 ? x - 0.8f : -x)
            .scaled(-0.4f, 0.8f, SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight);

        final Noise2D upperNoise = new OpenSimplex2D(seed)
            .octaves(4)
            .spread(0.05f)
            .scaled(-0.4f, 0.8f, SEA_LEVEL_Y + minHeight + 8, SEA_LEVEL_Y + maxHeight - 8);

        /*final Noise2D baseNoise = new OpenSimplex2D(seed) // A simplex noise forms the majority of the base
            .octaves(4) // High octaves to create highly fractal terrain
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
            });*/

        return (x, z) -> {
            float height = baseNoise.noise(x, z);
            if (height > SEA_LEVEL_Y + 18)
            {
                height = upperNoise.noise(x, z);
            }
            if (height < SEA_LEVEL_Y + 1)
            {
                height = upperNoise.noise(x, z);
            }
            return height;
        };
    }

    /**
     * Domain warping creates twisting land patterns
     */
    public static Noise2D canyonCliffs(long seed, int minHeight, int maxHeight)
    {
        final Random generator = new Random(seed);
        final OpenSimplex2D warp = new OpenSimplex2D(seed).octaves(4).spread(0.03f).scaled(-100f, 100f);
        final Noise2D baseNoise = new OpenSimplex2D(seed + 1)
            .octaves(4)
            .spread(0.06f)
            .warped(warp)
            .map(x -> x > 0.4 ? x - 0.8f : -x)
            .scaled(-0.4f, 0.8f, SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight);

        // Cliff noise consists of noise that's been artificially clamped over half the domain, which is then selectively added above a base height level
        // This matches up with the distinction between dirt and stone
        final int extra = generator.nextInt(3);
        final Noise2D cliffNoise = new OpenSimplex2D(seed + 2).octaves(2).spread(0.01f).scaled(0, 25).map(x -> x > 0 ? x : 0);
        final Noise2D cliffHeightNoiseLower = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(70, 90);
        //final Noise2D cliffHeightNoiseMiddle = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(90, 100);

        return (x, z) -> {
            float height = baseNoise.noise(x, z);
            if (height > 70 + extra) // Only sample each cliff noise layer if the base noise could be influenced by it
            {
                final float cliffHeight = cliffHeightNoiseLower.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            /*if (height > 90 + extra) // Only sample each cliff noise layer if the base noise could be influenced by it
            {
                final float cliffHeight = cliffHeightNoiseMiddle.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }*/
            return height;
        };
    }

    /**
     * Creates a variant of badlands with stacked pillar like structures, as opposed to relief carved.
     * Inspired by imagery of Bryce Canyon, Utah.
     */
    public static Noise2D grandCanyon(long seed)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed).octaves(2).spread(0.01f).scaled(-25f, 25f);
        final Random generator = new Random(seed);
        //final int rng = generator.nextInt(SEA_LEVEL_Y / 2);

        //Noise2D noise = new OpenSimplex2D(generator.nextLong()).octaves(4).spread(0.01f).scaled(SEA_LEVEL_Y - rng, SEA_LEVEL_Y + 96);
        Noise2D noise = new OpenSimplex2D(generator.nextLong()).octaves(4).spread(0.05f).scaled(SEA_LEVEL_Y + 2, SEA_LEVEL_Y + 14).terraces(2);
        for (int layer = 0; layer < 5; layer++)
        {
            final float threshold = 0.25f;
            final float delta = 0.025f;

            noise = noise.add(new OpenSimplex2D(generator.nextLong())
                .octaves(3)
                .spread(0.02f + 0.01f * layer)
                .warped(warp)
                .abs()
                .affine(1, -0.05f * layer)
                .map(t -> Mth.clampedMap(t, threshold, threshold + delta, 0, 1))
                .lazyProduct(new OpenSimplex2D(generator.nextLong())
                    .octaves(4)
                    .spread(0.1f)
                    .scaled(-10f, 25f)));
                    //.scaled(rng, 40)));
        }
        return noise;
    }

    public static Noise2D seamounts(long seed, int depthMin, int depthMax)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed).octaves(2).spread(0.015f).scaled(-30, 30);
        final Noise2D ridgeNoise = new OpenSimplex2D(seed + 1).octaves(4).spread(0.015f).ridged().map(x -> { // In [-1, 1]
            if (x > -0.3f)
            {
                x = (x + 0.3f) / 1.3f;  // In [0, 1]
                x = x * x * x; // Power scaled
                return -16f * x; // In [0, -16]
            }
            return 0; // No modifications outside of ridge area
        });
        final Noise2D baseNoise = new OpenSimplex2D(seed + 2).octaves(4).spread(0.11f).scaled(SEA_LEVEL_Y + depthMin, SEA_LEVEL_Y + depthMax).add(ridgeNoise).warped(warp);

        // Cliff noise consists of noise that's been artificially clamped over half the domain, which is then selectively added above a base height level
        // This matches up with the distinction between dirt and stone
        final Noise2D cliffNoise = new OpenSimplex2D(seed + 2).octaves(2).spread(0.01f).scaled(0, 25).map(x -> x > 0 ? x : 0);
        final Noise2D cliffHeightNoiseLower = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(0, 15);
        final Noise2D cliffHeightNoiseMiddle = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(15, 25);
        final Noise2D cliffHeightNoiseUpper = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(25, 40);

        return (x, z) -> {
            final Random generator = new Random((long) baseNoise.noise(x, z));
            final int extra = (int) generator.nextGaussian() * 4;
            float height = baseNoise.noise(x, z);
            if (height > -245 + extra)
            {
                final float cliffHeight = cliffHeightNoiseLower.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > -237 + extra)
            {
                final float cliffHeight = cliffHeightNoiseMiddle.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > -230 + extra)
            {
                final float cliffHeight = cliffHeightNoiseUpper.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            return height;
        };
    }

    public static Noise2D mountainsBadlands(long seed, int baseHeight, int scaleHeight)
    {
        final Noise2D baseNoise = new OpenSimplex2D(seed) // A simplex noise forms the majority of the base
            .octaves(6) // High octaves to create highly fractal terrain
            .spread(0.2f)
            .add(new OpenSimplex2D(seed + 1) // Ridge noise is added to mimic real mountain ridges. It is scaled smaller than the base noise to not be overpowering
                .octaves(4)
                .spread(0.02f)
                .scaled(-1f, 1f)
                .ridged() // Ridges are applied after octaves as it creates less directional artifacts this way
            )
            .map(x -> {
                final float x0 = 0.125f * (x + 1) * (x + 1) * (x + 1); // Power scaled, flattens most areas but maximizes peaks
                return SEA_LEVEL_Y + baseHeight + scaleHeight * x0; // Scale the entire thing to mountain ranges
            });

        // Cliff noise consists of noise that's been artificially clamped over half the domain, which is then selectively added above a base height level
        // This matches up with the distinction between dirt and stone
        final Noise2D cliffNoise = new OpenSimplex2D(seed + 2).octaves(2).spread(0.01f).scaled(0, 25).map(x -> x > 0 ? x : 0);
        final Noise2D cliffHeightNoiseLower = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(75 - 20, 95 + 20);
        final Noise2D cliffHeightNoiseMiddle = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(95 - 20, 120 + 20);
        final Noise2D cliffHeightNoiseUpper = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(120 - 20, 140 + 20);

        return (x, z) -> {
            final Random generator = new Random((long) baseNoise.noise(x, z));
            final int extra = (int) generator.nextGaussian() * 4;
            float height = baseNoise.noise(x, z);
            if (height > 75 + extra)
            {
                final float cliffHeight = cliffHeightNoiseLower.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 95 + extra)
            {
                final float cliffHeight = cliffHeightNoiseMiddle.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 120 + extra)
            {
                final float cliffHeight = cliffHeightNoiseUpper.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            return height;
        };
    }

    public static Noise2D pits(long seed, int minHeight, int maxHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed + 1).octaves(3).spread(0.01f).scaled(0.8f, -0.8f, SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight);
        return new OpenSimplex2D(seed + 1)
            .octaves(5)
            .spread(0.1f)
            .ridged()
            .warped(warp)
            .scaled(-1f, -0.1f, SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight)
            .ridged();
    }

    public static Noise2D mistyPeaks(long seed, int baseHeight, int scaleHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed).octaves(4).spread(0.03f).scaled(-100f, 100f);

        final Noise2D baseNoise = new OpenSimplex2D(seed) // A simplex noise forms the majority of the base
            .octaves(6) // High octaves to create highly fractal terrain
            .spread(0.05f)
            .warped(warp)
            .add(new OpenSimplex2D(seed + 1) // Ridge noise is added to mimic real mountain ridges. It is scaled smaller than the base noise to not be overpowering
                .octaves(4)
                .spread(0.02f)
                .warped(warp)
                .scaled(-1f, 1f)
                .ridged() // Ridges are applied after octaves as it creates less directional artifacts this way
            )
            .map(x -> {
                final float x0 = 0.125f * (x + 1) * (x + 1) * (x + 1); // Power scaled, flattens most areas but maximizes peaks
                return SEA_LEVEL_Y + baseHeight + scaleHeight * x0; // Scale the entire thing to mountain ranges
            });

        // Cliff noise consists of noise that's been artificially clamped over half the domain, which is then selectively added above a base height level
        // This matches up with the distinction between dirt and stone
        final Noise2D cliffNoise = new OpenSimplex2D(seed + 2).octaves(2).spread(0.01f).scaled(0, 25).map(x -> x > 0 ? x : 0);
        final Noise2D cliffHeightNoiseLower = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(70 - 15, 80 + 15);
        final Noise2D cliffHeightNoiseMiddle = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(80 - 10, 90 + 10);
        final Noise2D cliffHeightNoiseUpper = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(90 - 15, 110 + 15);
        final Noise2D cliffHeightNoiseHighUpper = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(110 - 20, 160 + 20);

        return (x, z) -> {
            final Random generator = new Random((long) warp.noise(x, z));
            final int extra = (int) generator.nextGaussian() * 4;
            float height = baseNoise.noise(x, z);
            if (height > 70 + extra)
            {
                final float cliffHeight = cliffHeightNoiseLower.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 80 + extra)
            {
                final float cliffHeight = cliffHeightNoiseMiddle.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 90 + extra)
            {
                final float cliffHeight = cliffHeightNoiseUpper.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 110 + extra)
            {
                final float cliffHeight = cliffHeightNoiseHighUpper.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            return height;
        };
    }

    public static Noise2D mistyPeaksNew(long seed, int baseHeight, int scaleHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed).octaves(4).spread(0.03f).scaled(-100f, 100f);

        final Noise2D baseNoise = new OpenSimplex2D(seed) // A simplex noise forms the majority of the base
            .octaves(6) // High octaves to create highly fractal terrain
            .spread(0.1f)
            .warped(warp)
            .add(new OpenSimplex2D(seed + 1) // Ridge noise is added to mimic real mountain ridges. It is scaled smaller than the base noise to not be overpowering
                .octaves(8)
                .spread(0.1f)
                .warped(warp)
                .scaled(-0.2f, 0.8f)
                .ridged() // Ridges are applied after octaves as it creates less directional artifacts this way
            )
            .map(x -> {
                final float x0 = 0.125f * (x + 1) * (x + 1) * (x + 1); // Power scaled, flattens most areas but maximizes peaks
                return SEA_LEVEL_Y + baseHeight + scaleHeight * x0; // Scale the entire thing to mountain ranges
            });

        // Cliff noise consists of noise that's been artificially clamped over half the domain, which is then selectively added above a base height level
        // This matches up with the distinction between dirt and stone
        final Noise2D cliffNoise = new OpenSimplex2D(seed + 2).octaves(2).spread(0.01f).scaled(0, 25).map(x -> x > 0 ? x : 0);
        final Noise2D cliffHeightNoiseLower = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(70 - 15, 80 + 15);
        final Noise2D cliffHeightNoiseMiddle = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(80 - 10, 90 + 10);
        final Noise2D cliffHeightNoiseUpper = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(90 - 15, 110 + 15);
        final Noise2D cliffHeightNoiseHighUpper = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(110 - 20, 160 + 20);

        return (x, z) -> {
            /*final Random generator = new Random((long) warp.noise(x, z));
            final int extra = (int) generator.nextGaussian() * 4;*/
            final float extra = pits(seed + 1, -70, -50).noise(x, z);
            float height = baseNoise.noise(x, z);
            if (height > 70 + extra)
            {
                final float cliffHeight = cliffHeightNoiseLower.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 80 + extra)
            {
                final float cliffHeight = cliffHeightNoiseMiddle.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 90 + extra)
            {
                final float cliffHeight = cliffHeightNoiseUpper.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 110 + extra)
            {
                final float cliffHeight = cliffHeightNoiseHighUpper.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            return height;
        };
    }

    public static Noise2D mistyPeaksFractured(long seed, int baseHeight, int scaleHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed).octaves(4).spread(0.03f).scaled(-100f, 100f);

        final Noise2D baseNoise = new Cellular2D(seed) // A simplex noise forms the majority of the base
            .octaves(4) // High octaves to create highly fractal terrain
            .spread(0.1f)
            .warped(warp)
            .add(new Cellular2D(seed + 1) // Ridge noise is added to mimic real mountain ridges. It is scaled smaller than the base noise to not be overpowering
                .octaves(6)
                .spread(0.1f)
                .warped(warp)
                .scaled(-0.2f, 0.8f)
                .ridged() // Ridges are applied after octaves as it creates less directional artifacts this way
            )
            .map(x -> {
                final float x0 = 0.125f * (x + 1) * (x + 1) * (x + 1); // Power scaled, flattens most areas but maximizes peaks
                return SEA_LEVEL_Y + baseHeight + scaleHeight * x0; // Scale the entire thing to mountain ranges
            });

        // Cliff noise consists of noise that's been artificially clamped over half the domain, which is then selectively added above a base height level
        // This matches up with the distinction between dirt and stone
        final Noise2D cliffNoise = new OpenSimplex2D(seed + 2).octaves(2).spread(0.01f).scaled(0, 25).map(x -> x > 0 ? x : 0);
        final Noise2D cliffHeightNoiseLower = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(70 - 15, 80 + 15);
        final Noise2D cliffHeightNoiseMiddle = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(80 - 10, 90 + 10);
        final Noise2D cliffHeightNoiseUpper = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(90 - 15, 110 + 15);
        final Noise2D cliffHeightNoiseHighUpper = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(110 - 20, 160 + 20);

        return (x, z) -> {
            /*final Random generator = new Random((long) warp.noise(x, z));
            final int extra = (int) generator.nextGaussian() * 4;*/
            final float extra = pits(seed + 1, -70, -50).noise(x, z);
            float height = baseNoise.noise(x, z);
            if (height > 70 + extra)
            {
                final float cliffHeight = cliffHeightNoiseLower.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 80 + extra)
            {
                final float cliffHeight = cliffHeightNoiseMiddle.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 90 + extra)
            {
                final float cliffHeight = cliffHeightNoiseUpper.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 110 + extra)
            {
                final float cliffHeight = cliffHeightNoiseHighUpper.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            return height;
        };
    }

    public static Noise2D mistyPeaksCell(long seed, int baseHeight, int scaleHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed).octaves(4).spread(0.03f).scaled(-100f, 100f);

        final Noise2D baseNoise = new OpenSimplex2D(seed) // A simplex noise forms the majority of the base
            .octaves(6) // High octaves to create highly fractal terrain
            .spread(0.1f)
            .warped(warp)
            .add(new OpenSimplex2D(seed + 1) // Ridge noise is added to mimic real mountain ridges. It is scaled smaller than the base noise to not be overpowering
                .octaves(8)
                .spread(0.1f)
                .warped(warp)
                .scaled(-0.2f, 0.8f)
                .ridged() // Ridges are applied after octaves as it creates less directional artifacts this way
            )
            .map(x -> {
                final float x0 = 0.125f * (x + 1) * (x + 1) * (x + 1); // Power scaled, flattens most areas but maximizes peaks
                return SEA_LEVEL_Y + baseHeight + scaleHeight * x0; // Scale the entire thing to mountain ranges
            });

        // Cliff noise consists of noise that's been artificially clamped over half the domain, which is then selectively added above a base height level
        // This matches up with the distinction between dirt and stone
        final Noise2D cliffNoise = new OpenSimplex2D(seed + 2).octaves(2).spread(0.01f).scaled(0, 25).map(x -> x > 0 ? x : 0);
        final Noise2D cliffHeightNoiseLower = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(70 - 15, 80 + 15);
        final Noise2D cliffHeightNoiseMiddle = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(80 - 10, 90 + 10);
        final Noise2D cliffHeightNoiseUpper = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(90 - 15, 110 + 15);
        final Noise2D cliffHeightNoiseHighUpper = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(110 - 20, 160 + 20);

        return (x, z) -> {
            /*final Random generator = new Random((long) warp.noise(x, z));
            final int extra = (int) generator.nextGaussian() * 4;*/
            final float extra = sawtoothCliffs(seed + 1, baseHeight, scaleHeight).noise(x, z);
            float height = baseNoise.noise(x, z);
            if (height > 70 + extra)
            {
                final float cliffHeight = cliffHeightNoiseLower.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 80 + extra)
            {
                final float cliffHeight = cliffHeightNoiseMiddle.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 90 + extra)
            {
                final float cliffHeight = cliffHeightNoiseUpper.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 110 + extra)
            {
                final float cliffHeight = cliffHeightNoiseHighUpper.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            return height;
        };
    }

    public static Noise2D frostedGlass(long seed, int baseHeight, int scaleHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed).octaves(4).spread(0.03f).scaled(-100f, 100f);

        final Noise2D baseNoise = new OpenSimplex2D(seed) // A simplex noise forms the majority of the base
            .octaves(6) // High octaves to create highly fractal terrain
            .spread(0.1f)
            .warped(warp)
            .add(new OpenSimplex2D(seed + 1) // Ridge noise is added to mimic real mountain ridges. It is scaled smaller than the base noise to not be overpowering
                .octaves(2)
                .spread(0.1f)
                .warped(warp)
                .scaled(-0.2f, 0.8f)
                .ridged() // Ridges are applied after octaves as it creates less directional artifacts this way
            )
            .map(x -> {
                final float x0 = 0.125f * (x + 1) * (x + 1) * (x + 1); // Power scaled, flattens most areas but maximizes peaks
                return SEA_LEVEL_Y + baseHeight + scaleHeight * x0; // Scale the entire thing to mountain ranges
            });

        // Cliff noise consists of noise that's been artificially clamped over half the domain, which is then selectively added above a base height level
        // This matches up with the distinction between dirt and stone
        final Noise2D cliffNoise = new OpenSimplex2D(seed + 2).octaves(2).spread(0.01f).scaled(0, 25).map(x -> x > 0 ? x : 0);
        final Noise2D cliffHeightNoiseLower = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(70 - 15, 80 + 15);
        final Noise2D cliffHeightNoiseMiddle = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(80 - 10, 90 + 10);
        final Noise2D cliffHeightNoiseUpper = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(90 - 15, 110 + 15);
        final Noise2D cliffHeightNoiseHighUpper = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(110 - 20, 160 + 20);

        return (x, z) -> {
            /*final Random generator = new Random((long) warp.noise(x, z));
            final int extra = (int) generator.nextGaussian() * 4;*/
            final float extra = pits(seed + 1, -70, -50).noise(x, z);
            float height = baseNoise.noise(x, z);
            if (height > 70 + extra)
            {
                final float cliffHeight = cliffHeightNoiseLower.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 80 + extra)
            {
                final float cliffHeight = cliffHeightNoiseMiddle.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 90 + extra)
            {
                final float cliffHeight = cliffHeightNoiseUpper.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 110 + extra)
            {
                final float cliffHeight = cliffHeightNoiseHighUpper.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            return height;
        };
    }

    public static Noise2D coastalCliffs(long seed, int minHeight, int maxHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed).octaves(3).spread(0.03f).scaled(-100f, 100f);

        final Noise2D baseNoise = new OpenSimplex2D(seed + 1)
            .octaves(6)
            .spread(0.06f)
            .warped(warp)
            .map(x -> x > 0.4 ? x - 0.8f : -x)
            .scaled(-0.4f, 0.8f, SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight);

        // Cliff noise consists of noise that's been artificially clamped over half the domain, which is then selectively added above a base height level
        // This matches up with the distinction between dirt and stone
        final Noise2D cliffNoise = new OpenSimplex2D(seed + 2).octaves(2).spread(0.01f).scaled(0, 25).map(x -> x > 0 ? x : 0);
        final Noise2D cliffHeightNoiseLower = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(60, 70);
        final Noise2D cliffHeightNoiseMiddle = new OpenSimplex2D(seed + 3).octaves(2).spread(0.01f).scaled(70, 90);

        return (x, z) -> {
            float height = baseNoise.noise(x, z);
            if (height > 60)
            {
                final float cliffHeight = cliffHeightNoiseLower.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            if (height > 70)
            {
                final float cliffHeight = cliffHeightNoiseMiddle.noise(x, z) - height;
                if (cliffHeight < 0)
                {
                    final float mappedCliffHeight = Mth.clampedMap(cliffHeight, 0, -1, 0, 1);
                    height += mappedCliffHeight * cliffNoise.noise(x, z);
                }
            }
            return height;
        };
    }

    public static Noise2D rainbowMountains(long seed, int baseHeight, int scaleHeight)
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed).octaves(4).spread(0.03f).scaled(-100f, 100f);
        return new OpenSimplex2D(seed) // A simplex noise forms the majority of the base
            .octaves(6) // High octaves to create highly fractal terrain
            .spread(0.08f)
            .warped(warp)
            .add(new OpenSimplex2D(seed + 1) // Ridge noise is added to mimic real mountain ridges. It is scaled smaller than the base noise to not be overpowering
                .octaves(4)
                .spread(0.02f)
                .warped(warp)
                .scaled(-0.5f, 1.0f)
                .ridged() // Ridges are applied after octaves as it creates less directional artifacts this way
            )
            .map(x -> {
                final float x0 = 0.125f * (x + 1) * (x + 1) * (x + 1); // Power scaled, flattens most areas but maximizes peaks
                return SEA_LEVEL_Y + baseHeight + scaleHeight * x0; // Scale the entire thing to mountain ranges
            });
    }

    public static Noise2D riverShore(long seed)
    {
        return new OpenSimplex2D(seed).octaves(6).spread(0.15f).scaled(SEA_LEVEL_Y - 1.5f, SEA_LEVEL_Y + 1.3f);
    }

    public static Noise2D lakeShore(long seed, int minHeight, int maxHeight)
    {
        return new OpenSimplex2D(seed).octaves(4).spread(0.17f).scaled(SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight);
    }

    public static Noise2D lake(long seed, int minHeight, int maxHeight)
    {
        return new OpenSimplex2D(seed).octaves(4).spread(0.15f).scaled(SEA_LEVEL_Y + minHeight, SEA_LEVEL_Y + maxHeight);
    }

        /**
     * Effectively a {@code lerp(noiseA(), noiseB(), piecewise(noiseB()) + noiseC()} with the following additional techniques:
     * <ul>
     *     <li>{@code noiseA} is scaled to outside it's range, then biased towards 1.0, to expose more cliffs, as opposed to hills </li>
     *     <li>{@code piecewise()} is a piecewise linear function that creates cliff shapes from the standard noise distribution.</li>
     *     <li>{@code noiseC} is added on top to provide additional variance (in places where the piecewise function would otherwise flatten areas.</li>
     * </ul>
     */
    public static Noise2D sharpHills(long seed)
    {
        final Noise2D base = new OpenSimplex2D(seed)
            .octaves(4)
            .spread(0.08f);

        final Noise2D lerp = new OpenSimplex2D(seed + 7198234123L)
            .spread(0.013f)
            .scaled(-0.3f, 1.6f)
            .clamped(0, 1);

        final Noise2D lerpMapped = (x, z) -> {
            float in = base.noise(x, z);
            return Mth.lerp(lerp.noise(x, z), in, sharpHillsMap(in));
        };

        final OpenSimplex2D variance = new OpenSimplex2D(seed + 67981832123L)
            .octaves(3)
            .spread(0.06f)
            .scaled(-0.2f, 0.2f);

        return lerpMapped
            .add(variance)
            .scaled(-0.75f, 0.7f, SEA_LEVEL_Y - 3, SEA_LEVEL_Y + 28);
    }

    public static float sharpHillsMap(float in)
    {
        final float in0 = 1.0f, in1 = 0.67f, in2 = 0.15f, in3 = -0.15f, in4 = -0.67f, in5 = -1.0f;
        final float out0 = 1.0f, out1 = 0.7f, out2 = 0.5f, out3 = -0.5f, out4 = -0.7f, out5 = -1.0f;

        if (in > in1)
            return Mth.map(in, in1, in0, out1, out0);
        if (in > in2)
            return Mth.map(in, in2, in1, out2, out1);
        if (in > in3)
            return Mth.map(in, in3, in2, out3, out2);
        if (in > in4)
            return Mth.map(in, in4, in3, out4, out3);
        else
            return Mth.map(in, in5, in4, out5, out4);
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
        Noise2D riverBankHeight = new OpenSimplex2D(seed).octaves(4).spread(0.2f).scaled(SEA_LEVEL_Y - 11, SEA_LEVEL_Y + 11);
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
                else if (y >= SEA_LEVEL_Y)
                {
                    //return (Math.pow(1.3D, y - SEA_LEVEL_Y - 13) - 2) / SEA_LEVEL_Y;
                    //return (Math.pow(1.25D, y - 47) - 8) / (SEA_LEVEL_Y * 1.25D); // Terracing
                    //return (Math.pow(1.08D, y) - (SEA_LEVEL_Y * 2)) / 40;
                    return (Math.pow(1.08D, y) - (SEA_LEVEL_Y * 2.1)) / 40;
                }
                else if (y > SEA_LEVEL_Y - 8)
                {
                    double easing = (y - SEA_LEVEL_Y + 8) / 8d;
                    return easing * cliffNoise.noise(x, y, z);
                }
                else if (y <= SEA_LEVEL_Y - 8)
                {
                    return SOLID;
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

    public static BiomeNoiseSampler plateauCliffsSampler(long seed)
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
                    return -((Math.pow(1.08D, y) - (SEA_LEVEL_Y * SEA_LEVEL_Y)) / -2800) * (cliffNoise.noise(x, y, z) / 2);
                }
                else if (y > SEA_LEVEL_Y + 8)
                {
                    return ((Math.sin(y * 8) / 15) + Math.pow(1.02D, y - SEA_LEVEL_Y) - 0.4D) * (cliffNoise.noise(x, y, z) / 2);
                }
                return SOLID;
            }
        };
    }

    public static BiomeNoiseSampler coastalCliffsSampler(long seed)
    {
        Noise2D riverHeight = new OpenSimplex2D(seed).octaves(5).spread(0.2f).scaled(SEA_LEVEL_Y - 20, SEA_LEVEL_Y + 100);
        Noise3D cliffNoise = new OpenSimplex3D(seed).octaves(8).spread(0.1f).scaled(0, 5);

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
                return SOLID * cliffNoise.noise(x, y, z);
            }
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

    public static BiomeNoiseSampler undergroundCavesUpper1(long seed, Noise2D heightNoise)
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

    public static BiomeNoiseSampler undergroundCavesUpper2(long seed, Noise2D heightNoise)
    {
        final Noise2D blobsNoise = new OpenSimplex2D(seed + 2).octaves(4).spread(0.016f).abs();
        final Noise2D depthNoise = new OpenSimplex2D(seed + 4).octaves(4).scaled(0, 18).spread(0.02f);
        final Noise2D centerNoise = new OpenSimplex2D(seed + 6).octaves(2).spread(0.01f).scaled(SEA_LEVEL_Y - 50, SEA_LEVEL_Y - 20);

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

    public static BiomeNoiseSampler undergroundCavesUpper3(long seed, Noise2D heightNoise)
    {
        final Noise2D blobsNoise = new OpenSimplex2D(seed + 3).octaves(4).spread(0.016f).abs();
        final Noise2D depthNoise = new OpenSimplex2D(seed + 6).octaves(4).scaled(0, 18).spread(0.02f);
        final Noise2D centerNoise = new OpenSimplex2D(seed + 9).octaves(2).spread(0.01f).scaled(SEA_LEVEL_Y - 50, SEA_LEVEL_Y - 20);

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
                return Mth.clamp(0.4f + 0.05f * (height - delta), 0, 32);
            }
        };
    }

    public static BiomeNoiseSampler undergroundCavesMiddle1(long seed, Noise2D heightNoise)
    {
        final Noise2D blobsNoise = new OpenSimplex2D(seed + 1).octaves(4).spread(0.018f).abs();
        final Noise2D depthNoise = new OpenSimplex2D(seed + 2).octaves(4).scaled(2, 18).spread(0.05f);
        final Noise2D centerNoise = new OpenSimplex2D(seed + 3).octaves(3).spread(0.08f).scaled(SEA_LEVEL_Y - 183, SEA_LEVEL_Y - 163);

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
                return Mth.clamp(0.4f + 0.05f * (height - delta), 0, 32);
            }
        };
    }

    public static BiomeNoiseSampler undergroundCavesMiddle2(long seed, Noise2D heightNoise)
    {
        final Noise2D blobsNoise = new OpenSimplex2D(seed + 2).octaves(4).spread(0.018f).abs();
        final Noise2D depthNoise = new OpenSimplex2D(seed + 4).octaves(4).scaled(2, 18).spread(0.05f);
        final Noise2D centerNoise = new OpenSimplex2D(seed + 6).octaves(3).spread(0.08f).scaled(SEA_LEVEL_Y - 183, SEA_LEVEL_Y - 163);

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
                return Mth.clamp(0.4f + 0.05f * (height - delta), 0, 32);
            }
        };
    }

    public static BiomeNoiseSampler undergroundCavesDeep1(long seed, Noise2D heightNoise)
    {
        final Noise2D blobsNoise = new OpenSimplex2D(seed + 1).octaves(5).spread(0.018f).abs();
        final Noise2D depthNoise = new OpenSimplex2D(seed + 2).octaves(5).scaled(2, 26).spread(0.2f);
        final Noise2D centerNoise = new OpenSimplex2D(seed + 3).octaves(5).spread(0.25f).scaled(SEA_LEVEL_Y - 273, SEA_LEVEL_Y - 253);

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
                return Mth.clamp(0.4f + 0.05f * (height - delta), 0, 64);
            }
        };
    }

    public static BiomeNoiseSampler undergroundCavesDeep2(long seed, Noise2D heightNoise)
    {
        final Noise2D blobsNoise = new OpenSimplex2D(seed + 2).octaves(5).spread(0.018f).abs();
        final Noise2D depthNoise = new OpenSimplex2D(seed + 4).octaves(5).scaled(2, 26).spread(0.2f);
        final Noise2D centerNoise = new OpenSimplex2D(seed + 6).octaves(5).spread(0.25f).scaled(SEA_LEVEL_Y - 273, SEA_LEVEL_Y - 253);

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
                return Mth.clamp(0.4f + 0.05f * (height - delta), 0, 64);
            }
        };
    }

    public static BiomeNoiseSampler undergroundCavesDeep3(long seed, Noise2D heightNoise)
    {
        final Noise2D blobsNoise = new OpenSimplex2D(seed + 3).octaves(5).spread(0.018f).abs();
        final Noise2D depthNoise = new OpenSimplex2D(seed + 6).octaves(5).scaled(2, 26).spread(0.2f);
        final Noise2D centerNoise = new OpenSimplex2D(seed + 9).octaves(5).spread(0.25f).scaled(SEA_LEVEL_Y - 273, SEA_LEVEL_Y - 253);

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
                return Mth.clamp(0.4f + 0.05f * (height - delta), 0, 64);
            }
        };
    }
}
