/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package tfcflorae.world.surface.builder;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.dries007.tfc.common.blocks.RiverWaterBlock;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ForestType;
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;
import net.dries007.tfc.world.surface.SoilSurfaceState;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceState;
import net.dries007.tfc.world.surface.SurfaceStates;
import net.dries007.tfc.world.surface.builder.*;

import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.world.surface.TFCFSoilSurfaceState;

public class ForestSurfaceBuilder implements SurfaceBuilder
{

    public static SurfaceBuilderFactory create(SurfaceBuilderFactory parent)
    {
        return seed -> new ForestSurfaceBuilder(parent.apply(seed), seed);
    }

    private final SurfaceBuilder parent;
    private final Noise2D surfaceMaterialNoise1;
    private final Noise2D surfaceMaterialNoise2;
    private final Noise2D surfaceMaterialNoise3;

    public ForestSurfaceBuilder(SurfaceBuilder parent, long seed)
    {
        this.parent = parent;
        this.surfaceMaterialNoise1 = new OpenSimplex2D(seed).octaves(2).spread(0.04f);
        this.surfaceMaterialNoise2 = new OpenSimplex2D(seed).octaves(2).spread(0.02f);
        this.surfaceMaterialNoise3 = new OpenSimplex2D(seed).octaves(2).scaled(-0.3f, 1.3f).spread(0.0003f);
    }

    @Override
    public void buildSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        ForestType forestType = context.getChunkData().getForestType();
        float forestDensity = context.getChunkData().getForestDensity();
        float adjustedForestDensity = context.getChunkData().getAdjustedForestDensity();

        parent.buildSurface(context, startY, endY);

        // Forest Floor Decorations
        if (forestDensity >= 0.35f + ((context.random().nextGaussian() * 1) / 10) && adjustedForestDensity >= 0.6f + ((context.random().nextGaussian() * 1) / 10))
            buildForestSurface(context, startY, endY);
    }

    private void buildForestSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        final SurfaceState COARSE_DIRT = TFCFSoilSurfaceState.buildType(TFCFSoil.COARSE_DIRT);
        final SurfaceState PODZOL = TFCFSoilSurfaceState.buildType(TFCFSoil.PODZOL);
        final SurfaceState DENSE_GRASS = TFCFSoilSurfaceState.buildType(TFCFSoil.DENSE_GRASS);
        final SurfaceState SPARSE_GRASS = TFCFSoilSurfaceState.buildType(TFCFSoil.SPARSE_GRASS);
        final SurfaceState ROOTED_DIRT = SoilSurfaceState.buildType(SoilBlockType.ROOTED_DIRT);

        int topBlock = startY - 1;
        BlockPos pos = new BlockPos(context.pos().getX(), startY, context.pos().getZ());
        float rainfall = context.getChunkData().getRainfall(pos);

        final double noise1 = surfaceMaterialNoise1.noise(context.pos().getX(), context.pos().getZ()) * 0.9f + context.random().nextGaussian() * 0.1f;
        final double noise2 = surfaceMaterialNoise2.noise(context.pos().getX(), context.pos().getZ()) + 0.1f * context.random().nextGaussian() - 0.05f;
        final double noise3 = surfaceMaterialNoise3.noise(context.pos().getX(), context.pos().getZ());

        if (topBlock >= context.getSeaLevel() && (
            context.getBlockState(topBlock) != SurfaceStates.MUD || 
            context.getBlockState(topBlock) != SurfaceStates.SANDSTONE_OR_GRAVEL || 
            context.getBlockState(topBlock) != SurfaceStates.SAND_OR_GRAVEL || 
            context.getBlockState(topBlock) != SurfaceStates.DIRT || 
            context.getBlockState(topBlock) != SurfaceStates.GRAVEL || 
            context.getBlockState(topBlock) != SurfaceStates.COBBLE || 
            !Helpers.isFluid(context.getBlockState(topBlock).getFluidState(), FluidTags.WATER) || 
            !context.level().getBlockState(pos).hasProperty(RiverWaterBlock.FLOW)))
        {
            double random = context.random().nextGaussian();
            if (context.level().getBrightness(LightLayer.BLOCK, pos) <= 11)
            {
                context.setBlockState(topBlock, noise1 + random < 0f + random ? PODZOL : ROOTED_DIRT);
            }
            else if (context.level().getBrightness(LightLayer.BLOCK, pos) == 12)
            {
                context.setBlockState(topBlock, noise1 + random < 0f + random ? COARSE_DIRT : SPARSE_GRASS);
            }
            else if (context.level().getBrightness(LightLayer.BLOCK, pos) == 13)
            {
                context.setBlockState(topBlock, noise1 + random < 0f + random ? SPARSE_GRASS : DENSE_GRASS);
            }
            else if (context.level().getBrightness(LightLayer.BLOCK, pos) > 13 && context.level().getBrightness(LightLayer.SKY, pos) <= 14)
            {
                context.setBlockState(topBlock, noise1 + random < 0f + random ? DENSE_GRASS : PODZOL);
            }
            else
            {
                if (noise1 + (noise3 * random) < 0f + random)
                {
                    if (noise2 + random > 0.3f + random)
                    {
                        if (noise3 * random > noise3 - random)
                        {
                            if (rainfall <= 175 + (random * 0.9f))
                            {
                                context.setBlockState(topBlock, SPARSE_GRASS);
                            }
                            else
                            {
                                context.setBlockState(topBlock, ROOTED_DIRT);
                            }
                        }
                        else
                        {
                            context.setBlockState(topBlock, SPARSE_GRASS);
                        }
                    }
                    else if (noise2 * random < -0.3f + random)
                    {
                        if (noise3 * random < noise3 + random)
                        {
                            if (rainfall <= 175 + (random * 0.9f))
                            {
                                if (random > 0f)
                                {
                                    context.setBlockState(topBlock, COARSE_DIRT);
                                }
                                else    
                                {
                                    context.setBlockState(topBlock, PODZOL);
                                }
                            }
                            else
                            {
                                context.setBlockState(topBlock, PODZOL);
                            }
                        }
                        else
                        {
                            if (noise2 * random < noise2 - random)
                            {
                                context.setBlockState(topBlock, DENSE_GRASS);
                            }
                            else
                            {
                                context.setBlockState(topBlock, SurfaceStates.GRASS);
                            }
                            
                        }
                    }
                    else
                    {
                        context.setBlockState(topBlock, COARSE_DIRT);
                    }
                }
                else
                {
                    if (rainfall <= 175 + (random * 0.9f))
                    {
                        context.setBlockState(topBlock, DENSE_GRASS);
                    }
                    else
                    {
                        context.setBlockState(topBlock, SurfaceStates.GRASS);
                    }
                }
            }
        }
    }
}
