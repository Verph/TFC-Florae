package tfcflorae.world.surface.builder;

import net.minecraft.core.BlockPos;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
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
import tfcflorae.common.blocks.soil.TFCFRockSand;
import tfcflorae.world.surface.TFCFSoilSurfaceState;

public class DuneShoreSurfaceBuilder implements SurfaceBuilder
{
    public static SurfaceBuilderFactory create(SurfaceBuilderFactory parent)
    {
        return seed -> new DuneShoreSurfaceBuilder(parent.apply(seed), seed);
    }

    private final SurfaceBuilder parent;
    private final Noise2D surfaceMaterialNoise;
    private final Noise2D variantNoise;

    public DuneShoreSurfaceBuilder(SurfaceBuilder parent, long seed)
    {
        this.parent = parent;
        this.surfaceMaterialNoise = new OpenSimplex2D(seed).octaves(2).spread(0.04f);
        this.variantNoise = new OpenSimplex2D(seed).octaves(2).spread(0.003f).abs();
    }

    @Override
    public void buildSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        parent.buildSurface(context, startY, endY);

        float noiseRainfall = context.rainfall() + (10 * (float) context.random().nextGaussian());
        if (noiseRainfall >= 50f)
        {
            buildSandySurface(context, startY, endY);
        }
    }

    private void buildSandySurface(SurfaceBuilderContext context, int startY, int endY)
    {
        int surface = startY - 1;
        ChunkData data = context.getChunkData();
        BlockPos pos = new BlockPos(context.pos().getX(), surface, context.pos().getZ());
        RockSettings surfaceRock = data.getRockData().getRock(context.pos().getX(), surface, context.pos().getZ());
        float variantNoiseValue = variantNoise.noise(context.pos().getX(), context.pos().getZ());

        SandBlockType sandColor = SandBlockType.YELLOW;
        Rock rockTFC = Rock.GRANITE;
        TFCFRock rockTFCF = TFCFRock.ARKOSE;

        SurfaceState PEBBLE = TFCFSoilSurfaceState.rockSandTFC(TFCFRockSand.PEBBLE, rockTFC);
        SurfaceState ROCKY = TFCFSoilSurfaceState.rockSandTFC(TFCFRockSand.ROCKY, rockTFC);
        SurfaceState ROCKIER = TFCFSoilSurfaceState.rockSandTFC(TFCFRockSand.ROCKIER, rockTFC);
        SurfaceState ROCKIEST = TFCFSoilSurfaceState.rockSandTFC(TFCFRockSand.ROCKIEST, rockTFC);

        if (context.getRock().sand() != null)
        {
            for (SandBlockType sandColors : SandBlockType.values())
            {
                if (context.getRock().sand().getRegistryName().toString().equalsIgnoreCase(TFCBlocks.SAND.get(sandColors).get().getRegistryName().toString()))
                {
                    sandColor = sandColors;
                    break;
                }
            }
        }

        if (surfaceRock != null)
        {
            for (Rock r : Rock.values())
            {
                if (surfaceRock.get(Rock.BlockType.RAW).getRegistryName().toString().equalsIgnoreCase(TFCBlocks.ROCK_BLOCKS.get(r).get(Rock.BlockType.RAW).get().getRegistryName().toString()))
                {
                    rockTFC = r;
                    if (variantNoiseValue > 0.6f)
                    {
                        PEBBLE = TFCFSoilSurfaceState.rockRareSandTFC(TFCFRockSand.PEBBLE, rockTFC);
                        ROCKY = TFCFSoilSurfaceState.rockRareSandTFC(TFCFRockSand.ROCKY, rockTFC);
                        ROCKIER = TFCFSoilSurfaceState.rockRareSandTFC(TFCFRockSand.ROCKIER, rockTFC);
                        ROCKIEST = TFCFSoilSurfaceState.rockRareSandTFC(TFCFRockSand.ROCKIEST, rockTFC);
                    }
                    else
                    {
                        PEBBLE = TFCFSoilSurfaceState.rockSandTFC(TFCFRockSand.PEBBLE, rockTFC);
                        ROCKY = TFCFSoilSurfaceState.rockSandTFC(TFCFRockSand.ROCKY, rockTFC);
                        ROCKIER = TFCFSoilSurfaceState.rockSandTFC(TFCFRockSand.ROCKIER, rockTFC);
                        ROCKIEST = TFCFSoilSurfaceState.rockSandTFC(TFCFRockSand.ROCKIEST, rockTFC);
                    }
                    break;
                }
                else
                {
                    for (TFCFRock r2 : TFCFRock.values())
                    {
                        if (surfaceRock.get(Rock.BlockType.RAW).getRegistryName().toString().equalsIgnoreCase(TFCFBlocks.TFCF_ROCK_BLOCKS.get(r2).get(Rock.BlockType.RAW).get().getRegistryName().toString()))
                        {
                            rockTFCF = r2;
                            if (variantNoiseValue > 0.6f)
                            {
                                PEBBLE = TFCFSoilSurfaceState.rockRareSandTFCF(TFCFRockSand.PEBBLE, rockTFCF);
                                ROCKY = TFCFSoilSurfaceState.rockRareSandTFCF(TFCFRockSand.ROCKY, rockTFCF);
                                ROCKIER = TFCFSoilSurfaceState.rockRareSandTFCF(TFCFRockSand.ROCKIER, rockTFCF);
                                ROCKIEST = TFCFSoilSurfaceState.rockRareSandTFCF(TFCFRockSand.ROCKIEST, rockTFCF);
                            }
                            else
                            {
                                PEBBLE = TFCFSoilSurfaceState.rockSandTFCF(TFCFRockSand.PEBBLE, rockTFCF);
                                ROCKY = TFCFSoilSurfaceState.rockSandTFCF(TFCFRockSand.ROCKY, rockTFCF);
                                ROCKIER = TFCFSoilSurfaceState.rockSandTFCF(TFCFRockSand.ROCKIER, rockTFCF);
                                ROCKIEST = TFCFSoilSurfaceState.rockSandTFCF(TFCFRockSand.ROCKIEST, rockTFCF);
                            }
                            break;
                        }
                    }
                }
            }
        }

        SurfaceState SPARSE_GRASS = TFCFSoilSurfaceState.rockSandSparseGrass();
        SurfaceState DENSE_GRASS = TFCFSoilSurfaceState.rockSandDenseGrass();
        SurfaceState SHORE_SAND = SurfaceStates.SHORE_SAND;

        if (variantNoiseValue > 0.6f)
        {
            SHORE_SAND = SurfaceStates.RARE_SHORE_SAND;
            SPARSE_GRASS = TFCFSoilSurfaceState.rockRareSandSparseGrass();
            DENSE_GRASS = TFCFSoilSurfaceState.rockRareSandDenseGrass();
        }

        final double randomGauss = Math.abs(context.random().nextGaussian()) * 0.1f;
        final double gauss = context.random().nextGaussian();
        final float noise = surfaceMaterialNoise.noise(context.pos().getX(), context.pos().getZ()) * 0.9f + context.random().nextFloat() * 0.1f;

        if (pos.getY() > context.getSeaLevel() + 3 + gauss)
        {
            final int random = context.random().nextInt(7);
            if (noise >= -0.45f + randomGauss && noise < -0.2f + randomGauss)
            {
                context.setBlockState(surface, DENSE_GRASS);
            }
            else if (noise >= -0.2f + randomGauss && noise < -0.05f + randomGauss)
            {
                context.setBlockState(surface, DENSE_GRASS);
            }
            else if (noise >= -0.05f + randomGauss && random <= 0)
            {
                context.setBlockState(surface, SPARSE_GRASS);
            }
            else if (noise >= -0.05f + randomGauss && random == 1)
            {
                context.setBlockState(surface, SHORE_SAND);
            }
            else if (noise >= -0.05f + randomGauss && random == 2)
            {
                context.setBlockState(surface, PEBBLE);
            }
            else if (noise >= -0.05f + randomGauss && random == 3)
            {
                context.setBlockState(surface, ROCKY);
            }
            else if (noise >= -0.05f + randomGauss && random == 4)
            {
                context.setBlockState(surface, ROCKIER);
            }
            else if (noise >= -0.05f + randomGauss && random == 5)
            {
                context.setBlockState(surface, ROCKIEST);
            }
            else if (noise >= -0.05f + randomGauss && random == 6)
            {
                context.setBlockState(surface, SPARSE_GRASS);
            }
        }
    }
}