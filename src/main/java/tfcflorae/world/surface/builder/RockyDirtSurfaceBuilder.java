package tfcflorae.world.surface.builder;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import net.dries007.tfc.common.blocks.RiverWaterBlock;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.TFCMaterials;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;
import net.dries007.tfc.world.settings.RockSettings;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceState;
import net.dries007.tfc.world.surface.SurfaceStates;
import net.dries007.tfc.world.surface.builder.*;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;
import tfcflorae.common.blocks.soil.TFCFRockSoil;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.world.surface.TFCFSoilSurfaceState;

public class RockyDirtSurfaceBuilder implements SurfaceBuilder
{
    public static SurfaceBuilderFactory create(SurfaceBuilderFactory parent)
    {
        return seed -> new RockyDirtSurfaceBuilder(parent.apply(seed), seed);
    }

    private final SurfaceBuilder parent;
    private final Noise2D surfaceMaterialNoise;
    private final Noise2D heightNoise;

    public RockyDirtSurfaceBuilder(SurfaceBuilder parent, long seed)
    {
        this.parent = parent;
        this.surfaceMaterialNoise = new OpenSimplex2D(seed).octaves(2).spread(0.04f);
        heightNoise = new OpenSimplex2D(seed + 71829341L).octaves(2).spread(0.1f);
    }

    @Override
    public void buildSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        parent.buildSurface(context, startY, endY);

        float noiseRainfall = context.rainfall() + (10 * (float) context.random().nextGaussian());
        if (noiseRainfall >= 80f)
        {
            buildSoilSurface(context, startY, endY);
        }
    }

    private void buildSoilSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        int topBlock = startY - 1;
        ChunkData data = context.getChunkData();
        BlockPos pos = new BlockPos(context.pos().getX(), topBlock, context.pos().getZ());
        final float rainfallFraction = data.getRainfall(pos) * 0.0005f;
        final float rainfall = data.getRainfall(pos);

        SurfaceState PEBBLE_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.PEBBLE_COMPACT_DIRT);
        SurfaceState ROCKY_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKY_COMPACT_DIRT);
        SurfaceState ROCKIER_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKIER_COMPACT_DIRT);
        SurfaceState ROCKIEST_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKIEST_COMPACT_DIRT);

        final SurfaceState SPARSE_GRASS = TFCFSoilSurfaceState.buildType(TFCFSoil.SPARSE_GRASS);
        final SurfaceState DENSE_GRASS = TFCFSoilSurfaceState.buildType(TFCFSoil.DENSE_GRASS);
        final SurfaceState COARSE_DIRT = TFCFSoilSurfaceState.buildType(TFCFSoil.COARSE_DIRT);
        final SurfaceState COMPACT_DIRT = TFCFSoilSurfaceState.buildType(TFCFSoil.COMPACT_DIRT);

        final double randomGauss = Math.abs(context.random().nextGaussian()) * 0.1f;
        final double gauss = context.random().nextGaussian();
        double altitudeChance = Math.pow(2f, (topBlock * 0.02f) + 1.75) + 5;

        final float noise = surfaceMaterialNoise.noise(context.pos().getX(), context.pos().getZ()) * 0.9f + context.random().nextFloat() * 0.1f;
        final double heightNoise = this.heightNoise.noise(context.pos().getX(), context.pos().getZ()) * 4f + startY;

        if (pos.getY() > context.getSeaLevel() && heightNoise <= 130)
        {
            if (rainfall < +1.5 * gauss + 100f)
            {
                if (rainfall >= +1.5 * gauss + 65f)
                {
                    context.setBlockState(topBlock, DENSE_GRASS);
                }
                else
                {
                    context.setBlockState(topBlock, SPARSE_GRASS);
                }
            }
            if (Math.abs(context.random().nextGaussian()) * 25 <= altitudeChance && topBlock >= 95)
            {
                final int random = context.random().nextInt(7);
                if (noise >= -0.45f + randomGauss && noise < -0.2f + randomGauss)
                {
                    context.setBlockState(topBlock, DENSE_GRASS);
                }
                else if (noise >= -0.2f + randomGauss && noise < -0.05f + randomGauss)
                {
                    context.setBlockState(topBlock, SPARSE_GRASS);
                }
                else if (noise >= -0.05f + randomGauss && random <= 0)
                {
                    context.setBlockState(topBlock, COARSE_DIRT);
                }
                else if (noise >= -0.05f + randomGauss && random == 1)
                {
                    context.setBlockState(topBlock, COMPACT_DIRT);
                }
                else if (noise >= -0.05f + randomGauss && random == 2)
                {
                    context.setBlockState(topBlock, PEBBLE_COMPACT_DIRT);
                }
                else if (noise >= -0.05f + randomGauss && random == 3)
                {
                    context.setBlockState(topBlock, ROCKY_COMPACT_DIRT);
                }
                else if (noise >= -0.05f + randomGauss && random == 4)
                {
                    context.setBlockState(topBlock, ROCKIER_COMPACT_DIRT);
                }
                else if (noise >= -0.05f + randomGauss && random == 5)
                {
                    context.setBlockState(topBlock, ROCKIEST_COMPACT_DIRT);
                }
                else if (noise >= -0.05f + randomGauss && random == 6)
                {
                    context.setBlockState(topBlock, SPARSE_GRASS);
                }
            }
            if (rainfallFraction / 5 <= context.random().nextInt(500) && topBlock <= 90)
            {
                if (noise >= 0.05f + randomGauss)
                {
                    final int random = context.random().nextInt(7);
                    if (noise >= 0.05f + randomGauss && noise < 0.2f + randomGauss)
                    {
                        context.setBlockState(topBlock, DENSE_GRASS);
                    }
                    else if (noise >= 0.2f + randomGauss && noise < 0.35f + randomGauss)
                    {
                        context.setBlockState(topBlock, SPARSE_GRASS);
                    }
                    else if (noise >= 0.35f + randomGauss && random <= 0)
                    {
                        context.setBlockState(topBlock, COARSE_DIRT);
                    }
                    else if (noise >= 0.35f + randomGauss && random == 1)
                    {
                        context.setBlockState(topBlock, COMPACT_DIRT);
                    }
                    else if (noise >= 0.35f + randomGauss && random == 2)
                    {
                        context.setBlockState(topBlock, PEBBLE_COMPACT_DIRT);
                    }
                    else if (noise >= 0.35f + randomGauss && random == 3)
                    {
                        context.setBlockState(topBlock, ROCKY_COMPACT_DIRT);
                    }
                    else if (noise >= 0.35f + randomGauss && random == 4)
                    {
                        context.setBlockState(topBlock, ROCKIER_COMPACT_DIRT);
                    }
                    else if (noise >= 0.35f + randomGauss && random == 5)
                    {
                        context.setBlockState(topBlock, ROCKIEST_COMPACT_DIRT);
                    }
                    else if (noise >= 0.35f + randomGauss && random == 6)
                    {
                        context.setBlockState(topBlock, SPARSE_GRASS);
                    }
                }
            }
        }
    }
}
