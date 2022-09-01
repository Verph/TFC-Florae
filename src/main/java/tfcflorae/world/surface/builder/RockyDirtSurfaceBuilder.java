/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package tfcflorae.world.surface.builder;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;

import net.dries007.tfc.common.blocks.RiverWaterBlock;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.TFCMaterials;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;
import net.dries007.tfc.world.settings.RockSettings;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceState;
import net.dries007.tfc.world.surface.SurfaceStates;
import net.dries007.tfc.world.surface.builder.*;

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

    public RockyDirtSurfaceBuilder(SurfaceBuilder parent, long seed)
    {
        this.parent = parent;
        this.surfaceMaterialNoise = new OpenSimplex2D(seed).octaves(2).spread(0.04f);
    }

    @Override
    public void buildSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        parent.buildSurface(context, startY, endY);
        //buildSoilSurface(context, startY, endY);
    }

    private void buildSoilSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        int topBlock = startY - 1;
        ChunkData data = context.getChunkData();
        BlockPos pos = new BlockPos(context.pos().getX(), topBlock, context.pos().getZ());
        final float rainfall = context.getChunkData().getRainfall(pos) * 0.0005f;
        RockSettings surfaceRock = data.getRockData().getRock(context.pos().getX(), startY, context.pos().getZ());

        Rock rock = Rock.GRANITE;
        for (Rock r : Rock.values())
        {
            if (surfaceRock.get(Rock.BlockType.RAW).getRegistryName().toString().equalsIgnoreCase(TFCBlocks.ROCK_BLOCKS.get(r).get(Rock.BlockType.RAW).get().getRegistryName().toString()))
            {
                rock = r;
                break;
            }
        }

        final SurfaceState SPARSE_GRASS = TFCFSoilSurfaceState.buildType(TFCFSoil.SPARSE_GRASS);
        final SurfaceState DENSE_GRASS = TFCFSoilSurfaceState.buildType(TFCFSoil.DENSE_GRASS);
        final SurfaceState COARSE_DIRT = TFCFSoilSurfaceState.buildType(TFCFSoil.COARSE_DIRT);
        final SurfaceState COMPACT_DIRT = TFCFSoilSurfaceState.buildType(TFCFSoil.COMPACT_DIRT);
        final SurfaceState PEBBLE_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.PEBBLE_COMPACT_DIRT, rock);
        final SurfaceState ROCKY_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKY_COMPACT_DIRT, rock);
        final SurfaceState ROCKIER_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKIER_COMPACT_DIRT, rock);
        final SurfaceState ROCKIEST_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKIEST_COMPACT_DIRT, rock);

        final double randomGauss = Math.abs(context.random().nextGaussian()) * 0.1f;
        //double chance = Math.pow(1.3f, (topBlock * 0.05f) + 5);
        double altitudeChance = Math.pow(2f, (topBlock * 0.02f) + 1.75) + 5;

        final float noise = surfaceMaterialNoise.noise(context.pos().getX(), context.pos().getZ()) * 0.9f + context.random().nextFloat() * 0.1f;

        if (pos.getY() >= context.getSeaLevel() && canPlaceHere(context.level(), pos))
        {
            if (Math.abs(context.random().nextGaussian()) * 25 <= altitudeChance && topBlock >= 95 /*&& context.getSlope() >= 7f + (Math.abs(context.random().nextGaussian()) * 3f)*/)
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
            if (rainfall / 5 <= context.random().nextInt(500) && topBlock <= 90)
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

    public boolean canPlaceHere(LevelAccessor level, BlockPos pos)
    {
        return level.getBlockState(pos) != SurfaceStates.SANDSTONE_OR_GRAVEL || 
            level.getBlockState(pos) != SurfaceStates.SAND_OR_GRAVEL || 
            level.getBlockState(pos) != SurfaceStates.DIRT || 
            level.getBlockState(pos) != SurfaceStates.GRAVEL || 
            level.getBlockState(pos) != SurfaceStates.COBBLE || 
            !level.getBlockState(pos).isAir() || 
            !Helpers.isFluid(level.getBlockState(pos).getFluidState(), FluidTags.WATER) || 
            !level.getBlockState(pos).hasProperty(RiverWaterBlock.FLOW) ||
            level.getBlockState(pos).getMaterial() != Material.WATER || 
            level.getBlockState(pos).getMaterial() != TFCMaterials.SALT_WATER || 
            level.getBlockState(pos).getMaterial() != TFCMaterials.SPRING_WATER || 
            level.getBlockState(pos).getBlock() != TFCBlocks.SALT_WATER.get() || 
            level.getBlockState(pos).getBlock() != TFCBlocks.SPRING_WATER.get() || 
            level.getBlockState(pos).getBlock() != TFCBlocks.RIVER_WATER.get();
    }
}
