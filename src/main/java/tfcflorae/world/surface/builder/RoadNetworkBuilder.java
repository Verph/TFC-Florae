/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package tfcflorae.world.surface.builder;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
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
import net.dries007.tfc.world.surface.SoilSurfaceState;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceState;
import net.dries007.tfc.world.surface.SurfaceStates;
import net.dries007.tfc.world.surface.builder.*;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;
import tfcflorae.common.blocks.soil.TFCFRockSoil;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.world.surface.TFCFSoilSurfaceState;

public class RoadNetworkBuilder implements SurfaceBuilder
{
    public static SurfaceBuilderFactory create(SurfaceBuilderFactory parent)
    {
        return seed -> new RoadNetworkBuilder(parent.apply(seed), seed);
    }

    private final SurfaceBuilder parent;
    public final Noise2D surfaceMaterialNoiseBaseRidged;

    public RoadNetworkBuilder(SurfaceBuilder parent, long seed)
    {
        this.parent = parent;
        this.surfaceMaterialNoiseBaseRidged = new OpenSimplex2D(seed + 9).octaves(1).spread(0.01f);
    }

    @Override
    public void buildSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        parent.buildSurface(context, startY, endY);
        buildRoadSurface(context, startY, endY);
    }

    private void buildRoadSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        final float noiseRoad = surfaceMaterialNoiseBaseRidged.noise(context.pos().getX(), context.pos().getZ());
        //final float noiseRoad = surfaceMaterialNoiseBaseRidged.noise(context.pos().getX(), context.pos().getZ()) * 0.9f + context.random().nextFloat() * 0.1f;

        int topBlock = startY - 1;
        ChunkData data = context.getChunkData();
        BlockPos pos = new BlockPos(context.pos().getX(), topBlock, context.pos().getZ());
        RockSettings surfaceRock = data.getRockData().getRock(context.pos().getX(), startY, context.pos().getZ());

        final float rainfall = context.getChunkData().getRainfall(pos);
        final float forestDensity = context.getChunkData().getForestDensity();

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

        final SurfaceState DIRTY_STONE_TILES = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.DIRTY_STONE_TILES, rock);
        final SurfaceState DIRTIER_STONE_TILES = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.DIRTIER_STONE_TILES, rock);
        final SurfaceState DIRTIEST_STONE_TILES = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.DIRTIEST_STONE_TILES, rock);
        final BlockState STONE_TILES = TFCFBlocks.ROCK_BLOCKS.get(rock).get(TFCFRock.TFCFBlockType.STONE_TILES).get().defaultBlockState();

        final double randomGauss = Math.abs(context.random().nextGaussian()) * 0.1f;
        if (pos.getY() >= context.getSeaLevel() && canPlaceHere(context.level(), pos))
        {
            if (noiseRoad == 0f || noiseRoad == 0f + randomGauss)
            {
                final BlockState stateAt = context.getBlockState(topBlock);
    
                if (!stateAt.isAir())
                {
                    final int random = context.random().nextInt(7);
                    if (random == 0)
                    {
                        context.setBlockState(topBlock, DIRTIEST_STONE_TILES);
                    }
                    else if (random == 1)
                    {
                        context.setBlockState(topBlock, DIRTIER_STONE_TILES);
                    }
                    else if (random == 2)
                    {
                        context.setBlockState(topBlock, DIRTY_STONE_TILES);
                    }
                    else if (random == 3)
                    {
                        context.setBlockState(topBlock, STONE_TILES);
                    }
                    else if (random == 4)
                    {
                        context.setBlockState(topBlock, ROCKIEST_COMPACT_DIRT);
                    }
                    else if (random == 5)
                    {
                        context.setBlockState(topBlock, ROCKIER_COMPACT_DIRT);
                    }
                    else if (random == 6)
                    {
                        context.setBlockState(topBlock, DENSE_GRASS);
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
