package tfcflorae.world.surface;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.world.TFCChunkGenerator;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.RockData;
import net.dries007.tfc.world.chunkdata.TFCChunkDataGenerator;
import net.dries007.tfc.world.settings.RockLayer;
import net.dries007.tfc.world.settings.RockLayerSettings;
import net.dries007.tfc.world.settings.RockSettings;
import net.dries007.tfc.world.surface.SoilSurfaceState;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceState;
import net.dries007.tfc.mixin.accessor.ChunkAccessAccessor;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;
import tfcflorae.common.blocks.soil.*;

public class TFCFSoilSurfaceState implements SurfaceState
{
    public static SurfaceState buildType(TFCFSoil type)
    {
        final ImmutableList<SurfaceState> regions = ImmutableList.of(
            sand(),
            transition(sand(), soil(type, SoilBlockType.Variant.SANDY_LOAM)),
            soil(type, SoilBlockType.Variant.SANDY_LOAM),
            transition(soil(type, SoilBlockType.Variant.SANDY_LOAM), soil(type, SoilBlockType.Variant.LOAM)),
            soil(type, SoilBlockType.Variant.LOAM),
            transition(soil(type, SoilBlockType.Variant.LOAM), soil(type, SoilBlockType.Variant.SILTY_LOAM)),
            soil(type, SoilBlockType.Variant.SILTY_LOAM),
            transition(soil(type, SoilBlockType.Variant.SILTY_LOAM), soil(type, SoilBlockType.Variant.SILT)),
            soil(type, SoilBlockType.Variant.SILT)
        );
        return new TFCFSoilSurfaceState(regions);
    }

    public static SurfaceState buildTypeGrass(TFCFSoil type)
    {
        final ImmutableList<SurfaceState> regions = ImmutableList.of(
            sand(),
            transition(sand(), soil(type, SoilBlockType.Variant.SANDY_LOAM)),
            soil(type, SoilBlockType.Variant.SANDY_LOAM),
            transition(soil(type, SoilBlockType.Variant.SANDY_LOAM), soil(type, SoilBlockType.Variant.LOAM)),
            soil(type, SoilBlockType.Variant.LOAM),
            transition(soil(type, SoilBlockType.Variant.LOAM), soil(type, SoilBlockType.Variant.SILTY_LOAM)),
            soil(type, SoilBlockType.Variant.SILTY_LOAM),
            transition(soil(type, SoilBlockType.Variant.SILTY_LOAM), soil(type, SoilBlockType.Variant.SILT)),
            soil(type, SoilBlockType.Variant.SILT)
        );
        return new TFCFSoilSurfaceState.NeedsPostProcessing(regions);
    }

    public static SurfaceState buildTypeRockTFC(TFCFRockSoil type, Rock rock)
    {
        final ImmutableList<SurfaceState> regions = ImmutableList.of(
            sand(),
            transition(sand(), rockSoilTFC(type, SoilBlockType.Variant.SANDY_LOAM, rock)),
            rockSoilTFC(type, SoilBlockType.Variant.SANDY_LOAM, rock),
            transition(rockSoilTFC(type, SoilBlockType.Variant.SANDY_LOAM, rock), rockSoilTFC(type, SoilBlockType.Variant.LOAM, rock)),
            rockSoilTFC(type, SoilBlockType.Variant.LOAM, rock),
            transition(rockSoilTFC(type, SoilBlockType.Variant.LOAM, rock), rockSoilTFC(type, SoilBlockType.Variant.SILTY_LOAM, rock)),
            rockSoilTFC(type, SoilBlockType.Variant.SILTY_LOAM, rock),
            transition(rockSoilTFC(type, SoilBlockType.Variant.SILTY_LOAM, rock), rockSoilTFC(type, SoilBlockType.Variant.SILT, rock)),
            rockSoilTFC(type, SoilBlockType.Variant.SILT, rock)
        );
        return new TFCFSoilSurfaceState(regions);
    }

    public static SurfaceState buildTypeRockTFCF(TFCFRockSoil type, TFCFRock rock)
    {
        final ImmutableList<SurfaceState> regions = ImmutableList.of(
            sand(),
            transition(sand(), rockSoilTFCF(type, SoilBlockType.Variant.SANDY_LOAM, rock)),
            rockSoilTFCF(type, SoilBlockType.Variant.SANDY_LOAM, rock),
            transition(rockSoilTFCF(type, SoilBlockType.Variant.SANDY_LOAM, rock), rockSoilTFCF(type, SoilBlockType.Variant.LOAM, rock)),
            rockSoilTFCF(type, SoilBlockType.Variant.LOAM, rock),
            transition(rockSoilTFCF(type, SoilBlockType.Variant.LOAM, rock), rockSoilTFCF(type, SoilBlockType.Variant.SILTY_LOAM, rock)),
            rockSoilTFCF(type, SoilBlockType.Variant.SILTY_LOAM, rock),
            transition(rockSoilTFCF(type, SoilBlockType.Variant.SILTY_LOAM, rock), rockSoilTFCF(type, SoilBlockType.Variant.SILT, rock)),
            rockSoilTFCF(type, SoilBlockType.Variant.SILT, rock)
        );
        return new TFCFSoilSurfaceState(regions);
    }

    public static SurfaceState buildSandOrGravel(boolean sandIsSandstone)
    {
        final SurfaceState sand = sandIsSandstone ? sandstone() : sand();
        final SurfaceState gravel = gravel();
        return new TFCFSoilSurfaceState(ImmutableList.of(
            sand,
            transition(sand, gravel),
            gravel,
            gravel,
            gravel,
            gravel,
            gravel,
            gravel,
            gravel
        ));
    }

    public static SurfaceState transition(SurfaceState first, SurfaceState second)
    {
        return context -> {
            final BlockPos pos = context.pos();
            float noise = SoilSurfaceState.PATCH_NOISE.noise(pos.getX(), pos.getZ());
            return noise > 0 ? first.getState(context) : second.getState(context);
        };
    }

    public static SurfaceState rockTFC(Rock rock, Rock.BlockType type)
    {
        final Supplier<Block> block = TFCBlocks.ROCK_BLOCKS.get(rock).get(type);
        return context -> block.get().defaultBlockState();
    }

    public static SurfaceState rockTFCF(TFCFRock rock, Rock.BlockType type)
    {
        final Supplier<Block> block = TFCFBlocks.TFCF_ROCK_BLOCKS.get(rock).get(type);
        return context -> block.get().defaultBlockState();
    }

    public static SurfaceState sand()
    {
        return context -> context.getRock().sand().defaultBlockState();
    }

    public static SurfaceState sandstone()
    {
        return context -> context.getRock().sandstone().defaultBlockState();
    }

    public static SurfaceState gravel()
    {
        return context -> context.getRock().gravel().defaultBlockState();
    }

    public static SurfaceState soil(TFCFSoil type, SoilBlockType.Variant variant)
    {
        final Supplier<Block> block = TFCFBlocks.TFCSOIL.get(type).get(variant);
        return context -> block.get().defaultBlockState();
    }

    public static SurfaceState TFCFsoil(TFCFSoil type, TFCFSoil.TFCFVariant variant)
    {
        final Supplier<Block> block = TFCFBlocks.TFCFSOIL.get(type).get(variant);
        return context -> block.get().defaultBlockState();
    }

    public static SurfaceState rockSoilTFC(TFCFRockSoil type, SoilBlockType.Variant variant, Rock rock)
    {
        final Supplier<Block> block = TFCFBlocks.TFCROCKSOIL.get(type).get(variant).get(rock);
        return context -> block.get().defaultBlockState();
    }

    public static SurfaceState rockSoilTFCF(TFCFRockSoil type, SoilBlockType.Variant variant, TFCFRock rock)
    {
        final Supplier<Block> block = TFCFBlocks.TFCROCKSOIL2.get(type).get(variant).get(rock);
        return context -> block.get().defaultBlockState();
    }

    public static SurfaceState rockSandGrass(SandBlockType sandColor)
    {
        return context -> TFCFBlocks.SAND_GRASS.get(sandColor).get().defaultBlockState();
    }

    public static SurfaceState rockSandSparseGrass(SandBlockType sandColor)
    {
        return context -> TFCFBlocks.SPARSE_SAND_GRASS.get(sandColor).get().defaultBlockState();
    }

    public static SurfaceState rockSandDenseGrass(SandBlockType sandColor)
    {
        return context -> TFCFBlocks.DENSE_SAND_GRASS.get(sandColor).get().defaultBlockState();
    }

    public static SurfaceState rockSandTFC(TFCFRockSand type, SandBlockType sandColor, Rock rock)
    {
        return context -> TFCFBlocks.ROCKY_SAND_TFC.get(type).get(sandColor).get(rock).get().defaultBlockState();
    }

    public static SurfaceState rockSandTFCF(TFCFRockSand type, SandBlockType sandColor, TFCFRock rock)
    {
        return context -> TFCFBlocks.ROCKY_SAND_TFCF.get(type).get(sandColor).get(rock).get().defaultBlockState();
    }

    public static SurfaceState rockRareSandGrass(SandBlockType sandColor)
    {
        final Supplier<Block> pinkSand = TFCFBlocks.SAND_GRASS.get(SandBlockType.PINK);
        final Supplier<Block> blackSand =  TFCFBlocks.SAND_GRASS.get(SandBlockType.BLACK);

        return context -> {
            if (context.rainfall() > 300f && context.averageTemperature() > 15f)
            {
                return pinkSand.get().defaultBlockState();
            }
            else if (context.rainfall() > 300f)
            {
                return blackSand.get().defaultBlockState();
            }
            else
            {
                return TFCFBlocks.SAND_GRASS.get(sandColor).get().defaultBlockState();
            }
        };
    }

    public static SurfaceState rockRareSandSparseGrass(SandBlockType sandColor)
    {
        final Supplier<Block> pinkSand = TFCFBlocks.SPARSE_SAND_GRASS.get(SandBlockType.PINK);
        final Supplier<Block> blackSand =  TFCFBlocks.SPARSE_SAND_GRASS.get(SandBlockType.BLACK);

        return context -> {
            if (context.rainfall() > 300f && context.averageTemperature() > 15f)
            {
                return pinkSand.get().defaultBlockState();
            }
            else if (context.rainfall() > 300f)
            {
                return blackSand.get().defaultBlockState();
            }
            else
            {
                return TFCFBlocks.SPARSE_SAND_GRASS.get(sandColor).get().defaultBlockState();
            }
        };
    }

    public static SurfaceState rockRareSandDenseGrass(SandBlockType sandColor)
    {
        final Supplier<Block> pinkSand = TFCFBlocks.DENSE_SAND_GRASS.get(SandBlockType.PINK);
        final Supplier<Block> blackSand =  TFCFBlocks.DENSE_SAND_GRASS.get(SandBlockType.BLACK);

        return context -> {
            if (context.rainfall() > 300f && context.averageTemperature() > 15f)
            {
                return pinkSand.get().defaultBlockState();
            }
            else if (context.rainfall() > 300f)
            {
                return blackSand.get().defaultBlockState();
            }
            else
            {
                return TFCFBlocks.DENSE_SAND_GRASS.get(sandColor).get().defaultBlockState();
            }
        };
    }

    public static SurfaceState rockRareSandTFC(TFCFRockSand type, SandBlockType sandColor, Rock rock)
    {
        final Supplier<Block> pinkSand = TFCFBlocks.ROCKY_SAND_TFC.get(type).get(SandBlockType.PINK).get(rock);
        final Supplier<Block> blackSand =  TFCFBlocks.ROCKY_SAND_TFC.get(type).get(SandBlockType.BLACK).get(rock);

        return context -> {
            if (context.rainfall() > 300f && context.averageTemperature() > 15f)
            {
                return pinkSand.get().defaultBlockState();
            }
            else if (context.rainfall() > 300f)
            {
                return blackSand.get().defaultBlockState();
            }
            else
            {
                return TFCFBlocks.ROCKY_SAND_TFC.get(type).get(sandColor).get(rock).get().defaultBlockState();
            }
        };
    }

    public static SurfaceState rockRareSandTFCF(TFCFRockSand type, SandBlockType sandColor, TFCFRock rock)
    {
        final Supplier<Block> pinkSand = TFCFBlocks.ROCKY_SAND_TFCF.get(type).get(SandBlockType.PINK).get(rock);
        final Supplier<Block> blackSand =  TFCFBlocks.ROCKY_SAND_TFCF.get(type).get(SandBlockType.BLACK).get(rock);

        return context -> {
            if (context.rainfall() > 300f && context.averageTemperature() > 15f)
            {
                return pinkSand.get().defaultBlockState();
            }
            else if (context.rainfall() > 300f)
            {
                return blackSand.get().defaultBlockState();
            }
            else
            {
                return TFCFBlocks.ROCKY_SAND_TFCF.get(type).get(sandColor).get(rock).get().defaultBlockState();
            }
        };
    }

    public final List<SurfaceState> regions;

    public TFCFSoilSurfaceState(List<SurfaceState> regions)
    {
        this.regions = regions;
    }

    @Override
    public BlockState getState(SurfaceBuilderContext context)
    {
        // Adjust rainfall to bias a little bit towards sand regions
        // Without: pure sand < 55mm, mixed sand < 105mm. With: pure sand < 75mm, mixed sand < 136mm
        final float rainfall = context.rainfall() + 20f;
        final int index = Mth.clamp((int) Mth.clampedMap(rainfall, 0, 500, 0, regions.size() - 0.01f), 0, regions.size() - 1);

        return regions.get(index).getState(context);
    }

    static class NeedsPostProcessing extends TFCFSoilSurfaceState
    {
        private NeedsPostProcessing(List<SurfaceState> regions)
        {
            super(regions);
        }

        @Override
        public void setState(SurfaceBuilderContext context)
        {
            context.chunk().setBlockState(context.pos(), getState(context), false);
            context.chunk().markPosForPostprocessing(context.pos());
        }
    }
}