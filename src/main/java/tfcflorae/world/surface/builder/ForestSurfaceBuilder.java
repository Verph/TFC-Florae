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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import net.dries007.tfc.common.blocks.RiverWaterBlock;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.TFCMaterials;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ForestType;
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;
import net.dries007.tfc.world.settings.RockSettings;
import net.dries007.tfc.world.surface.SoilSurfaceState;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceState;
import net.dries007.tfc.world.surface.SurfaceStates;
import net.dries007.tfc.world.surface.builder.*;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream

=======
import tfcflorae.TFCFlorae;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;
>>>>>>> Stashed changes
=======
import tfcflorae.TFCFlorae;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;
>>>>>>> Stashed changes
=======
import tfcflorae.TFCFlorae;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;
>>>>>>> Stashed changes
import tfcflorae.common.blocks.soil.TFCFRockSoil;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.world.surface.TFCFSoilSurfaceState;

public class ForestSurfaceBuilder implements SurfaceBuilder
{
    public static SurfaceBuilderFactory create(SurfaceBuilderFactory parent)
    {
        return seed -> new ForestSurfaceBuilder(parent.apply(seed), seed);
    }

    private final SurfaceBuilder parent;
    private final Noise2D surfaceMaterialNoise;

    public ForestSurfaceBuilder(SurfaceBuilder parent, long seed)
    {
        this.parent = parent;
        this.surfaceMaterialNoise = new OpenSimplex2D(seed + 3).octaves(2).spread(0.04f);
    }

    @Override
    public void buildSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        parent.buildSurface(context, startY, endY);
        buildForestSurface(context, startY, endY);
    }

    private void buildForestSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        int topBlock = startY - 1;
        ChunkData data = context.getChunkData();
        BlockPos pos = new BlockPos(context.pos().getX(), topBlock, context.pos().getZ());
        final ForestType forestType = context.getChunkData().getForestType();
        final float forestDensity = data.getForestDensity();
        RockSettings surfaceRock = data.getRockData().getRock(context.pos().getX(), startY, context.pos().getZ());

        // How the f to simplify??
        Rock rockTFC = Rock.GRANITE;
        TFCFRock rockTFCF = TFCFRock.ARKOSE;
        SurfaceState PEBBLE_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.PEBBLE_COMPACT_DIRT, rockTFC);
        SurfaceState ROCKY_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.ROCKY_COMPACT_DIRT, rockTFC);
        SurfaceState ROCKIER_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.ROCKIER_COMPACT_DIRT, rockTFC);
        SurfaceState ROCKIEST_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.ROCKIEST_COMPACT_DIRT, rockTFC);

        if (surfaceRock != null)
        {
            for (Rock r : Rock.values())
            {
                if (surfaceRock.get(Rock.BlockType.RAW).getRegistryName().toString().equalsIgnoreCase(TFCBlocks.ROCK_BLOCKS.get(r).get(Rock.BlockType.RAW).get().getRegistryName().toString()))
                {
                    rockTFC = r;
                    PEBBLE_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.PEBBLE_COMPACT_DIRT, rockTFC);
                    ROCKY_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.ROCKY_COMPACT_DIRT, rockTFC);
                    ROCKIER_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.ROCKIER_COMPACT_DIRT, rockTFC);
                    ROCKIEST_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.ROCKIEST_COMPACT_DIRT, rockTFC);
                    break;
                }
                else
                {
                    for (TFCFRock r2 : TFCFRock.values())
                    {
                        if (surfaceRock.get(Rock.BlockType.RAW).getRegistryName().toString().equalsIgnoreCase(TFCFBlocks.TFCF_ROCK_BLOCKS.get(r2).get(Rock.BlockType.RAW).get().getRegistryName().toString()))
                        {
                            rockTFCF = r2;
                            PEBBLE_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFCF(TFCFRockSoil.PEBBLE_COMPACT_DIRT, rockTFCF);
                            ROCKY_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFCF(TFCFRockSoil.ROCKY_COMPACT_DIRT, rockTFCF);
                            ROCKIER_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFCF(TFCFRockSoil.ROCKIER_COMPACT_DIRT, rockTFCF);
                            ROCKIEST_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFCF(TFCFRockSoil.ROCKIEST_COMPACT_DIRT, rockTFCF);
                            break;
                        }
                    }
                }
            }
        }

        /*Boolean bool = false;
        if (surfaceRock != null)
        {
            if (bool == false)
            {
                for (Rock r : Rock.values())
                {
                    if (surfaceRock.get(Rock.BlockType.RAW).getRegistryName().toString().equalsIgnoreCase(TFCBlocks.ROCK_BLOCKS.get(r).get(Rock.BlockType.RAW).get().getRegistryName().toString()))
                    {
                        rockTFC = r;
                        bool = false;
                        break;
                    }
                    else
                    {
                        bool = true;
                        break;
                    }
                }
                PEBBLE_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.PEBBLE_COMPACT_DIRT, rockTFC);
                ROCKY_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.ROCKY_COMPACT_DIRT, rockTFC);
                ROCKIER_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.ROCKIER_COMPACT_DIRT, rockTFC);
                ROCKIEST_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.ROCKIEST_COMPACT_DIRT, rockTFC);
            }
            if (bool == true)
            {
                for (TFCFRock r : TFCFRock.values())
                {
                    if (surfaceRock.get(Rock.BlockType.RAW).getRegistryName().toString().equalsIgnoreCase(TFCFBlocks.TFCF_ROCK_BLOCKS.get(r).get(Rock.BlockType.RAW).get().getRegistryName().toString()))
                    {
                        rockTFCF = r;
                        bool = true;
                        break;
                    }
                    else
                    {
                        bool = false;
                        break;
                    }
                }
                PEBBLE_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFCF(TFCFRockSoil.PEBBLE_COMPACT_DIRT, rockTFCF);
                ROCKY_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFCF(TFCFRockSoil.ROCKY_COMPACT_DIRT, rockTFCF);
                ROCKIER_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFCF(TFCFRockSoil.ROCKIER_COMPACT_DIRT, rockTFCF);
                ROCKIEST_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFCF(TFCFRockSoil.ROCKIEST_COMPACT_DIRT, rockTFCF);
            }
        }*/

        final SurfaceState GRASS = SoilSurfaceState.buildType(SoilBlockType.GRASS);
        final SurfaceState PODZOL = TFCFSoilSurfaceState.buildType(TFCFSoil.PODZOL);
        final SurfaceState ROOTED_DIRT = SoilSurfaceState.buildType(SoilBlockType.ROOTED_DIRT);
        final SurfaceState SPARSE_GRASS = TFCFSoilSurfaceState.buildType(TFCFSoil.SPARSE_GRASS);
        final SurfaceState DENSE_GRASS = TFCFSoilSurfaceState.buildType(TFCFSoil.DENSE_GRASS);
        final SurfaceState COARSE_DIRT = TFCFSoilSurfaceState.buildType(TFCFSoil.COARSE_DIRT);
        final SurfaceState COMPACT_DIRT = TFCFSoilSurfaceState.buildType(TFCFSoil.COMPACT_DIRT);

        final double randomGauss = Math.abs(context.random().nextGaussian()) * 0.1f;
        final float noise = surfaceMaterialNoise.noise(context.pos().getX(), context.pos().getZ()) * 0.9f + context.random().nextFloat() * 0.1f;

        if (pos.getY() > context.getSeaLevel() && canPlaceHere(context, topBlock) && canPlaceHere(context, startY))
        {
            if (forestDensity >= 0.3f && topBlock <= 110 && (forestType == ForestType.NORMAL || forestType == ForestType.OLD_GROWTH))
            {
                final int random = context.random().nextInt(7);
                if (noise >= -0.22f + randomGauss && noise < -0.13f + randomGauss)
                {
                    context.setBlockState(topBlock, DENSE_GRASS);
                }
                else if (noise >= -0.13f + randomGauss && noise < 0f + randomGauss)
                {
                    if (random > 3)
                        context.setBlockState(topBlock, SPARSE_GRASS);
                    else
                        context.setBlockState(topBlock, COMPACT_DIRT);
                }
                else if (noise >= 0f + randomGauss && noise < 0.1f + randomGauss)
                {
                    if (random > 4)
                        context.setBlockState(topBlock, DENSE_GRASS);
                    else
                        context.setBlockState(topBlock, PEBBLE_COMPACT_DIRT);
                }
                else if (noise >= 0.1f + randomGauss && noise < 0.2f + randomGauss)
                {
                    if (random > 4)
                        context.setBlockState(topBlock, DENSE_GRASS);
                    else
                        context.setBlockState(topBlock, ROCKY_COMPACT_DIRT);
                }
                else if (noise >= 0.2f + randomGauss && noise < 0.35f + randomGauss)
                {
                    if (random > 4)
                        context.setBlockState(topBlock, ROCKY_COMPACT_DIRT);
                    else
                        context.setBlockState(topBlock, ROCKIER_COMPACT_DIRT);
                }
                else if (noise >= 0.35f + randomGauss && noise < 0.55f + randomGauss)
                {
                    if (random > 4)
                        context.setBlockState(topBlock, DENSE_GRASS);
                    else
                        context.setBlockState(topBlock, ROOTED_DIRT);
                }
                else if (noise >= 0.55f + randomGauss && noise < 0.75f + randomGauss)
                {
                    context.setBlockState(topBlock, PODZOL);
                }
                else if (noise >= 0.75f + randomGauss)
                {
                    if (random > 3)
                        context.setBlockState(topBlock, PODZOL);
                    else
                        context.setBlockState(topBlock, ROOTED_DIRT);
                }
            }
        }
    }

    public boolean canPlaceHere(SurfaceBuilderContext context, int y)
    {
        BlockState state = context.getBlockState(y);
        Material stateMat = state.getMaterial();
        Block stateBlock = state.getBlock();

        return (state != SurfaceStates.SANDSTONE_OR_GRAVEL || 
            state != SurfaceStates.SAND_OR_GRAVEL || 
            state != SurfaceStates.DIRT || 
            state != SurfaceStates.GRAVEL || 
            state != SurfaceStates.COBBLE || 
            !state.isAir() || 
            !Helpers.isFluid(state.getFluidState(), FluidTags.WATER) || 
            !state.hasProperty(RiverWaterBlock.FLOW) ||
            stateMat != Material.WATER || 
            stateMat != TFCMaterials.SALT_WATER || 
            stateMat != TFCMaterials.SPRING_WATER || 
            stateBlock != TFCBlocks.SALT_WATER.get() || 
            stateBlock != TFCBlocks.SPRING_WATER.get() || 
            stateBlock != TFCBlocks.RIVER_WATER.get());
    }
}
