package tfcflorae.world.surface.builder;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.world.TFCChunkGenerator;
import net.dries007.tfc.world.biome.BiomeExtension;
import net.dries007.tfc.world.biome.TFCBiomes;
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceState;
import net.dries007.tfc.world.surface.builder.*;

import tfcflorae.common.blocks.soil.TFCFRockSand;
import tfcflorae.common.blocks.soil.TFCFRockSoil;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.world.surface.TFCFSoilSurfaceState;

public class SubSoilSurfaceBuilder implements SurfaceBuilder
{
    public static final int SEA_LEVEL_Y = TFCChunkGenerator.SEA_LEVEL_Y; // Matches vanilla
    private final Noise2D lowlands;
    private final Noise2D lake;
    private final Noise2D river;
    private final Noise2D lowCanyons;

    public static SurfaceBuilderFactory create(SurfaceBuilderFactory parent)
    {
        return seed -> new SubSoilSurfaceBuilder(parent.apply(seed), seed);
    }

    private final SurfaceBuilder parent;

    public SubSoilSurfaceBuilder(SurfaceBuilder parent, long seed)
    {
        this.parent = parent;
        this.lowlands = new OpenSimplex2D(seed).octaves(6).spread(0.55f).scaled(SEA_LEVEL_Y - 5, SEA_LEVEL_Y + 2).clamped(SEA_LEVEL_Y - 2, SEA_LEVEL_Y + 2);
        this.lake = new OpenSimplex2D(seed).octaves(4).spread(0.15f).scaled(SEA_LEVEL_Y - 12, SEA_LEVEL_Y - 2);
        this.river = new OpenSimplex2D(seed).octaves(4).spread(0.2f).scaled(SEA_LEVEL_Y - 11, SEA_LEVEL_Y - 5);
        this.lowCanyons = new OpenSimplex2D(seed).octaves(4).spread(0.03f).scaled(-100f, 100f);
    }

    @Override
    public void buildSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        parent.buildSurface(context, startY, endY);

        float noiseRainfall = context.rainfall() + (10 * (float) context.random().nextGaussian());
        if (noiseRainfall >= 80f)
        {
            buildSubSoil(context, startY, endY);
        }
        else
        {
            buildSubSand(context, startY, endY);
        }
    }

    private void buildSubSoil(SurfaceBuilderContext context, int startY, int endY)
    {
        BlockPos pos = new BlockPos(context.pos().getX(), startY - 1, context.pos().getZ());

        SurfaceState PEBBLE_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.PEBBLE_COMPACT_DIRT);
        SurfaceState ROCKY_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKY_COMPACT_DIRT);
        SurfaceState ROCKIER_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKIER_COMPACT_DIRT);
        SurfaceState ROCKIEST_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKIEST_COMPACT_DIRT);
        SurfaceState COMPACT_DIRT = TFCFSoilSurfaceState.buildType(TFCFSoil.COMPACT_DIRT);

        BlockState localGravelType = TFCFSoilSurfaceState.rock(Rock.BlockType.GRAVEL).getState(context);
        BlockState localDirtSoilVariant = TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(TFCFSoilSurfaceState.currentSoilVariant(context)).get().defaultBlockState();

        int grassY = startY - 1;
        int gravelY = -1;
        int transitionHeight = -1;

        for (int y = startY - 2; y >= endY; --y)
        {
            final BlockState stateAt = context.getBlockState(y);
            if (y < startY - 2 && y > endY + 2 && !stateAt.isAir() && (stateAt == localGravelType || stateAt == localDirtSoilVariant))
            {
                gravelY = y - 1;
                transitionHeight = gravelY + 4;
                break;
            }
        }

        if (gravelY != -1)
        {
            if (transitionHeight >= grassY - 1) transitionHeight = grassY - 1;
            for (int y = gravelY; y < transitionHeight; ++y)
            {
                final double randomGauss = context.random().nextGaussian();

                if (pos.getY() > context.getSeaLevel())
                {
                    if (y == gravelY)
                    {
                        if (randomGauss >= -0.3f)
                        {
                            if (randomGauss >= 0.8f)
                            {
                                context.setBlockState(y, ROCKY_COMPACT_DIRT.getState(context));
                            }
                            else if (randomGauss >= 0.45f && randomGauss < 0.8f)
                            {
                                context.setBlockState(y, ROCKIER_COMPACT_DIRT.getState(context));
                            }
                            else
                            {
                                context.setBlockState(y, ROCKIEST_COMPACT_DIRT.getState(context));
                            }
                        }
                    }
                    else if (y == gravelY + 1)
                    {
                        if (randomGauss >= 0f)
                        {
                            if (randomGauss >= 0.7f)
                            {
                                context.setBlockState(y, ROCKY_COMPACT_DIRT.getState(context));
                            }
                            else if (randomGauss >= 0.45f && randomGauss < 0.7f)
                            {
                                context.setBlockState(y, ROCKIER_COMPACT_DIRT.getState(context));
                            }
                            else
                            {
                                context.setBlockState(y, ROCKIEST_COMPACT_DIRT.getState(context));
                            }
                        }
                    }
                    else if (y == gravelY + 2)
                    {
                        if (randomGauss >= 0.1f)
                        {
                            if (randomGauss >= 0.7f)
                            {
                                context.setBlockState(y, PEBBLE_COMPACT_DIRT.getState(context));
                            }
                            else if (randomGauss >= 0.4f && randomGauss < 0.7f)
                            {
                                context.setBlockState(y, ROCKY_COMPACT_DIRT.getState(context));
                            }
                            else
                            {
                                context.setBlockState(y, ROCKIER_COMPACT_DIRT.getState(context));
                            }
                        }
                    }
                    else if (y == gravelY + 3)
                    {
                        if (randomGauss >= 0.2f)
                        {
                            if (randomGauss >= 0.6f)
                            {
                                context.setBlockState(y, COMPACT_DIRT.getState(context));
                            }
                            else
                            {
                                context.setBlockState(y, PEBBLE_COMPACT_DIRT.getState(context));
                            }
                        }
                    }
                    else if (y > gravelY + 3)
                    {
                        if (0.3f >= randomGauss)
                        {
                            context.setBlockState(y, COMPACT_DIRT.getState(context));
                        }
                    }
                }
            }
        }
    }

    private void buildSubSand(SurfaceBuilderContext context, int startY, int endY)
    {
        BlockPos pos = new BlockPos(context.pos().getX(), startY - 1, context.pos().getZ());

        SurfaceState PEBBLE = TFCFSoilSurfaceState.buildTypeRockSand(TFCFRockSand.PEBBLE);
        SurfaceState ROCKY = TFCFSoilSurfaceState.buildTypeRockSand(TFCFRockSand.ROCKY);
        SurfaceState ROCKIER = TFCFSoilSurfaceState.buildTypeRockSand(TFCFRockSand.ROCKIER);
        SurfaceState ROCKIEST = TFCFSoilSurfaceState.buildTypeRockSand(TFCFRockSand.ROCKIEST);
        SurfaceState COMPACT_DIRT = TFCFSoilSurfaceState.buildType(TFCFSoil.COMPACT_DIRT);

        BlockState localGravelType = TFCFSoilSurfaceState.rock(Rock.BlockType.GRAVEL).getState(context);
        BlockState localDirtSoilVariant = TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(TFCFSoilSurfaceState.currentSoilVariant(context)).get().defaultBlockState();


        int grassY = startY - 1;
        int gravelY = -1;
        int transitionHeight = -1;

        for (int y = startY - 2; y >= endY; --y)
        {
            final BlockState stateAt = context.getBlockState(y);
            if (y < startY - 2 && y > endY + 2 && !stateAt.isAir() && (stateAt == localGravelType || stateAt == localDirtSoilVariant))
            {
                gravelY = y - 1;
                transitionHeight = gravelY + 4;
                break;
            }
        }

        if (gravelY != -1)
        {
            if (transitionHeight >= grassY - 1) transitionHeight = grassY - 1;
            for (int y = gravelY; y < transitionHeight; ++y)
            {
                final double randomGauss = context.random().nextGaussian();

                if (pos.getY() > context.getSeaLevel())
                {
                    if (y == gravelY)
                    {
                        if (randomGauss >= -0.3f)
                        {
                            if (randomGauss >= 0.8f)
                            {
                                context.setBlockState(y, ROCKY.getState(context));
                            }
                            else if (randomGauss >= 0.45f && randomGauss < 0.8f)
                            {
                                context.setBlockState(y, ROCKIER.getState(context));
                            }
                            else
                            {
                                context.setBlockState(y, ROCKIEST.getState(context));
                            }
                        }
                    }
                    else if (y == gravelY + 1)
                    {
                        if (randomGauss >= 0f)
                        {
                            if (randomGauss >= 0.7f)
                            {
                                context.setBlockState(y, ROCKY.getState(context));
                            }
                            else if (randomGauss >= 0.45f && randomGauss < 0.7f)
                            {
                                context.setBlockState(y, ROCKIER.getState(context));
                            }
                            else
                            {
                                context.setBlockState(y, ROCKIEST.getState(context));
                            }
                        }
                    }
                    else if (y == gravelY + 2)
                    {
                        if (randomGauss >= 0.1f)
                        {
                            if (randomGauss >= 0.7f)
                            {
                                context.setBlockState(y, PEBBLE.getState(context));
                            }
                            else if (randomGauss >= 0.4f && randomGauss < 0.7f)
                            {
                                context.setBlockState(y, ROCKY.getState(context));
                            }
                            else
                            {
                                context.setBlockState(y, ROCKIER.getState(context));
                            }
                        }
                    }
                    else if (y == gravelY + 3)
                    {
                        if (randomGauss >= 0.2f)
                        {
                            if (randomGauss >= 0.6f)
                            {
                                context.setBlockState(y, COMPACT_DIRT.getState(context));
                            }
                            else
                            {
                                context.setBlockState(y, PEBBLE.getState(context));
                            }
                        }
                    }
                    else if (y > gravelY + 3)
                    {
                        if (0.3f >= randomGauss)
                        {
                            context.setBlockState(y, COMPACT_DIRT.getState(context));
                        }
                    }
                }
            }
        }
    }

    public static boolean isLake(BiomeExtension biome)
    {
        return biome == TFCBiomes.LAKE || biome == TFCBiomes.OCEANIC_MOUNTAIN_LAKE || biome == TFCBiomes.OLD_MOUNTAIN_LAKE || biome == TFCBiomes.MOUNTAIN_LAKE || biome == TFCBiomes.VOLCANIC_OCEANIC_MOUNTAIN_LAKE || biome == TFCBiomes.VOLCANIC_MOUNTAIN_LAKE || biome == TFCBiomes.PLATEAU_LAKE;
    }

    public static boolean isRiver(BiomeExtension biome)
    {
        return biome == TFCBiomes.RIVER || biome == TFCBiomes.OCEANIC_MOUNTAIN_RIVER || biome == TFCBiomes.OLD_MOUNTAIN_RIVER || biome == TFCBiomes.MOUNTAIN_RIVER || biome == TFCBiomes.VOLCANIC_OCEANIC_MOUNTAIN_RIVER || biome == TFCBiomes.VOLCANIC_MOUNTAIN_RIVER;
    }

    public static boolean isLow(BiomeExtension biome)
    {
        return biome == TFCBiomes.PLAINS || biome == TFCBiomes.HILLS || biome == TFCBiomes.LOWLANDS;
    }

    public static boolean isCanyon(BiomeExtension biome)
    {
        return biome == TFCBiomes.LOW_CANYONS || biome == TFCBiomes.CANYONS;
    }
}