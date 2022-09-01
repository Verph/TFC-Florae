package tfcflorae.world.surface.builder;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.RiverWaterBlock;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.TFCMaterials;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.settings.RockSettings;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceState;
import net.dries007.tfc.world.surface.SurfaceStates;
import net.dries007.tfc.world.surface.builder.*;

import tfcflorae.common.blocks.soil.TFCFRockSoil;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.world.surface.TFCFSoilSurfaceState;

public class SubSoilSurfaceBuilder implements SurfaceBuilder
{
    public static SurfaceBuilderFactory create(SurfaceBuilderFactory parent)
    {
        return seed -> new SubSoilSurfaceBuilder(parent.apply(seed), seed);
    }

    private final SurfaceBuilder parent;

    public SubSoilSurfaceBuilder(SurfaceBuilder parent, long seed)
    {
        this.parent = parent;
    }

    @Override
    public void buildSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        parent.buildSurface(context, startY, endY);
        buildSubSoil(context, startY, endY);
    }

    private void buildSubSoil(SurfaceBuilderContext context, int startY, int endY)
    {
        final NormalSurfaceBuilder surfaceBuilder = NormalSurfaceBuilder.INSTANCE;

        ChunkData data = context.getChunkData();
        BlockPos pos = new BlockPos(context.pos().getX(), startY - 1, context.pos().getZ());
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
        SoilBlockType.Variant variant = SoilBlockType.Variant.SILT; // Fallback
        for (SoilBlockType.Variant r : SoilBlockType.Variant.values())
        {
            if (context.getBlockState(startY).getBlock().getRegistryName().toString().equalsIgnoreCase(TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(r).get().getRegistryName().toString()))
            {
                variant = r;
                break;
            }
        }
        final BlockState localGravelType = TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL).get().defaultBlockState();
        final BlockState localDirtSoilVariant = TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant).get().defaultBlockState();

        final SurfaceState COMPACT_DIRT = TFCFSoilSurfaceState.buildType(TFCFSoil.COMPACT_DIRT);
        final SurfaceState PEBBLE_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.PEBBLE_COMPACT_DIRT, rock);
        final SurfaceState ROCKY_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKY_COMPACT_DIRT, rock);
        final SurfaceState ROCKIER_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKIER_COMPACT_DIRT, rock);
        final SurfaceState ROCKIEST_COMPACT_DIRT = TFCFSoilSurfaceState.buildTypeRock(TFCFRockSoil.ROCKIEST_COMPACT_DIRT, rock);

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

                BlockPos posY = new BlockPos(context.pos().getX(), y, context.pos().getZ());
                if (y == gravelY && canPlaceHere(context.level(), posY))
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

    public boolean canPlaceHere(LevelAccessor level, BlockPos pos)
    {
        return !level.getBlockState(pos).isAir() || 
            !Helpers.isFluid(level.getBlockState(pos).getFluidState(), FluidTags.WATER) || 
            level.getBlockState(pos).getMaterial() != Material.WATER || 
            level.getBlockState(pos).getMaterial() != TFCMaterials.SALT_WATER || 
            level.getBlockState(pos).getMaterial() != TFCMaterials.SPRING_WATER || 
            level.getBlockState(pos).getBlock() != TFCBlocks.SALT_WATER.get() || 
            level.getBlockState(pos).getBlock() != TFCBlocks.SPRING_WATER.get() || 
            level.getBlockState(pos).getBlock() != TFCBlocks.RIVER_WATER.get() || 
            !level.getBlockState(pos).hasProperty(RiverWaterBlock.FLOW);
    }
}