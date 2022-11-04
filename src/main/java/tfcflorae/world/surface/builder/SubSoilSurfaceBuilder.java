package tfcflorae.world.surface.builder;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Arrays;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.RiverWaterBlock;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.TFCMaterials;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.TFCChunkGenerator;
import net.dries007.tfc.world.biome.BiomeExtension;
import net.dries007.tfc.world.biome.BiomeNoise;
import net.dries007.tfc.world.biome.TFCBiomes;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;
import net.dries007.tfc.world.settings.RockSettings;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceState;
import net.dries007.tfc.world.surface.SurfaceStates;
import net.dries007.tfc.world.surface.builder.*;

import tfcflorae.TFCFlorae;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;
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
        buildSubSoil(context, startY, endY);
    }

    private void buildSubSoil(SurfaceBuilderContext context, int startY, int endY)
    {
        ChunkData data = context.getChunkData();
        BlockPos pos = new BlockPos(context.pos().getX(), startY - 1, context.pos().getZ());
        RockSettings surfaceRock = data.getRockData().getRock(context.pos().getX(), startY, context.pos().getZ());

        Rock rockTFC = Rock.GRANITE;
        TFCFRock rockTFCF = TFCFRock.ARKOSE;
        SurfaceState PEBBLE_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.PEBBLE_COMPACT_DIRT, rockTFC);
        SurfaceState ROCKY_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.ROCKY_COMPACT_DIRT, rockTFC);
        SurfaceState ROCKIER_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.ROCKIER_COMPACT_DIRT, rockTFC);
        SurfaceState ROCKIEST_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRockTFC(TFCFRockSoil.ROCKIEST_COMPACT_DIRT, rockTFC);
        BlockState localGravelType = TFCBlocks.ROCK_BLOCKS.get(rockTFC).get(Rock.BlockType.GRAVEL).get().defaultBlockState();
        BlockState localRawType = TFCBlocks.ROCK_BLOCKS.get(rockTFC).get(Rock.BlockType.RAW).get().defaultBlockState();

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
                    localGravelType = TFCBlocks.ROCK_BLOCKS.get(rockTFC).get(Rock.BlockType.GRAVEL).get().defaultBlockState();
                    localRawType = TFCBlocks.ROCK_BLOCKS.get(rockTFC).get(Rock.BlockType.RAW).get().defaultBlockState();
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
                            localGravelType = TFCFBlocks.TFCF_ROCK_BLOCKS.get(rockTFCF).get(Rock.BlockType.GRAVEL).get().defaultBlockState();
                            localRawType = TFCFBlocks.TFCF_ROCK_BLOCKS.get(rockTFCF).get(Rock.BlockType.RAW).get().defaultBlockState();
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
                localGravelType = TFCBlocks.ROCK_BLOCKS.get(rockTFC).get(Rock.BlockType.GRAVEL).get().defaultBlockState();
                localRawType = TFCBlocks.ROCK_BLOCKS.get(rockTFC).get(Rock.BlockType.RAW).get().defaultBlockState();
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
                localGravelType = TFCFBlocks.TFCF_ROCK_BLOCKS.get(rockTFCF).get(Rock.BlockType.GRAVEL).get().defaultBlockState();
                localRawType = TFCFBlocks.TFCF_ROCK_BLOCKS.get(rockTFCF).get(Rock.BlockType.RAW).get().defaultBlockState();
            }
        }*/

        SoilBlockType.Variant variant = SoilBlockType.Variant.SILT; // Fallback
        for (SoilBlockType.Variant r : SoilBlockType.Variant.values())
        {
            if (context.getBlockState(startY).getBlock().getRegistryName().toString().equalsIgnoreCase(TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(r).get().getRegistryName().toString()))
            {
                variant = r;
                break;
            }
        }

        /*final BlockState localGravelType = TFCBlocks.ROCK_BLOCKS.get((Rock) (Object) surfaceRock).get(Rock.BlockType.GRAVEL).get().defaultBlockState();
        final BlockState localRawType = TFCBlocks.ROCK_BLOCKS.get((Rock) (Object) surfaceRock).get(Rock.BlockType.RAW).get().defaultBlockState();*/
        final BlockState localDirtSoilVariant = TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant).get().defaultBlockState();

        final SurfaceState COMPACT_DIRT = TFCFSoilSurfaceState.buildType(TFCFSoil.COMPACT_DIRT);
        /*final SurfaceState PEBBLE_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.PEBBLE_COMPACT_DIRT, rock);
        final SurfaceState ROCKY_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKY_COMPACT_DIRT, rock);
        final SurfaceState ROCKIER_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKIER_COMPACT_DIRT, rock);
        final SurfaceState ROCKIEST_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKIEST_COMPACT_DIRT, rock);*/

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

                /*BlockPos posChecker = new BlockPos(context.pos().getX(), y, context.pos().getZ());
                TFCFlorae.LOGGER.info("generating subsoil block at XYZ " + posChecker.getX() + ", " + posChecker.getY() + ", " + posChecker.getZ());
                TFCFlorae.LOGGER.info("blockstate at XYZ " + posChecker.getX() + ", " + posChecker.getY() + ", " + posChecker.getZ() + " is " + context.getBlockState(y).toString());*/
                //if (canPlaceHere(context, y) && (((isLow(variants) && y > context.getSeaLevel()) || (isLake(variants) && y > context.getSeaLevel()) || (isRiver(variants) && y > context.getSeaLevel()) || (isCanyon(variants) && y > context.getSeaLevel())) || !isLow(variants) || !isLake(variants) || !isRiver(variants) || !isCanyon(variants)))
                if (canPlaceHere(context, y) && pos.getY() > context.getSeaLevel())
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

    public boolean canPlaceHere(SurfaceBuilderContext context, int y)
    {
        BlockState state = context.getBlockState(y);
        Material stateMat = state.getMaterial();
        Block stateBlock = state.getBlock();

        return (state != SurfaceStates.RAW || 
            !state.isAir() || 
            !Helpers.isFluid(state.getFluidState(), FluidTags.WATER) || 
            stateMat != Material.WATER || 
            stateMat != TFCMaterials.SALT_WATER || 
            stateMat != TFCMaterials.SPRING_WATER || 
            stateBlock != TFCBlocks.SALT_WATER.get() || 
            stateBlock != TFCBlocks.SPRING_WATER.get() || 
            stateBlock != TFCBlocks.RIVER_WATER.get() || 
            !state.hasProperty(RiverWaterBlock.FLOW));
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