package tfcflorae.world.surface.builder;

import net.minecraft.world.level.block.Blocks;

import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceStates;
import net.dries007.tfc.world.surface.builder.*;

public class RainbowMountainsSurfaceBuilder implements SurfaceBuilder
{
    public static SurfaceBuilderFactory create(SurfaceBuilderFactory parent)
    {
        return seed -> new RainbowMountainsSurfaceBuilder(parent.apply(seed), seed);
    }

    private final SurfaceBuilder parent;
    private final Noise2D surfaceMaterialNoise;
    //private final Noise2D generateNoise;
    private final Noise2D heightNoise;

    public RainbowMountainsSurfaceBuilder(SurfaceBuilder parent, long seed)
    {
        this.parent = parent;
        this.surfaceMaterialNoise = new OpenSimplex2D(seed).octaves(2).spread(1f).abs();
        //this.generateNoise = new OpenSimplex2D(seed).octaves(2).spread(0.3f);
        this.heightNoise = new OpenSimplex2D(seed + 71829341L).octaves(2).spread(0.1f);
    }

    @Override
    public void buildSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        parent.buildSurface(context, startY, endY);
        buildRainbowSurface(context, startY, endY);

        /*final double generateNoiseValue = generateNoise.noise(context.pos().getX(), context.pos().getZ());
        if (generateNoiseValue > 0.25D)
        {
            buildRainbowSurface(context, startY, endY);
        }*/
    }

    private void buildRainbowSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        //int surface = startY - 1;

        final NormalSurfaceBuilder surfaceBuilder = NormalSurfaceBuilder.INSTANCE;
        //final double randomGauss = Math.abs(context.random().nextGaussian()) * 0.1f;
        final double gaussSpread = context.random().nextGaussian() * 2;
        //final double colorNoise = surfaceMaterialNoise.noise(context.pos().getX(), context.pos().getZ());
        final double heightNoise = this.heightNoise.noise(context.pos().getX(), context.pos().getZ()) * 4f + startY;

        if (heightNoise > 118 + gaussSpread)
        {
            float surfaceMaterialValue = surfaceMaterialNoise.noise(context.pos().getX(), context.pos().getZ()) + 0.1f * context.random().nextFloat() - 0.05f;
            if (surfaceMaterialValue > 0.3f)
            {
                surfaceBuilder.buildSurface(context, startY, endY, SurfaceStates.COBBLE, SurfaceStates.COBBLE, SurfaceStates.RAW);
            }
            else if (surfaceMaterialValue < -0.3f)
            {
                surfaceBuilder.buildSurface(context, startY, endY, SurfaceStates.GRAVEL, SurfaceStates.GRAVEL, SurfaceStates.RAW);
            }
            else
            {
                surfaceBuilder.buildSurface(context, startY, endY, SurfaceStates.RAW, SurfaceStates.RAW, SurfaceStates.RAW);
            }
        }
        /*if (heightNoise > 160 + gaussSpread)
        {
            if (colorNoise >= 0.9D + randomGauss)
            {
                context.setBlockState(surface, Blocks.WHITE_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= 0.8D + randomGauss)
            {
                context.setBlockState(surface, Blocks.ORANGE_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= 0.7D + randomGauss)
            {
                context.setBlockState(surface, Blocks.MAGENTA_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= 0.6D + randomGauss)
            {
                context.setBlockState(surface, Blocks.LIGHT_BLUE_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= 0.5D + randomGauss)
            {
                context.setBlockState(surface, Blocks.YELLOW_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= 0.4D + randomGauss)
            {
                context.setBlockState(surface, Blocks.LIME_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= 0.3D + randomGauss)
            {
                context.setBlockState(surface, Blocks.PINK_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= 0.2D + randomGauss)
            {
                context.setBlockState(surface, Blocks.GRAY_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= 0.1D + randomGauss)
            {
                context.setBlockState(surface, Blocks.LIGHT_GRAY_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= 0.0D + randomGauss)
            {
                context.setBlockState(surface, Blocks.CYAN_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= -0.1D + randomGauss)
            {
                context.setBlockState(surface, Blocks.PURPLE_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= -0.2D + randomGauss)
            {
                context.setBlockState(surface, Blocks.BLUE_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= -0.3D + randomGauss)
            {
                context.setBlockState(surface, Blocks.BROWN_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= -0.4D + randomGauss)
            {
                context.setBlockState(surface, Blocks.GREEN_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= -0.5D + randomGauss)
            {
                context.setBlockState(surface, Blocks.RED_TERRACOTTA.defaultBlockState());
            }
            else if (colorNoise >= -0.6D + randomGauss)
            {
                context.setBlockState(surface, Blocks.BLACK_TERRACOTTA.defaultBlockState());
            }
        }*/
    }
}