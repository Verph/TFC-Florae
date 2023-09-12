package tfcflorae.world.surface;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.settings.RockSettings;
import net.dries007.tfc.world.surface.SoilSurfaceState;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceState;
import net.dries007.tfc.util.registry.RegistryRock;
import net.dries007.tfc.util.registry.RegistrySoilVariant;
import tfcflorae.Config;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;
import tfcflorae.common.blocks.soil.*;
import tfcflorae.util.TFCFHelpers;

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

    public static SurfaceState buildTypeRock(TFCFRockSoil type)
    {
        final ImmutableList<SurfaceState> regions = ImmutableList.of(
            sand(),
            transition(sand(), rockSoil(type, SoilBlockType.Variant.SANDY_LOAM)),
            rockSoil(type, SoilBlockType.Variant.SANDY_LOAM),
            transition(rockSoil(type, SoilBlockType.Variant.SANDY_LOAM), rockSoil(type, SoilBlockType.Variant.LOAM)),
            rockSoil(type, SoilBlockType.Variant.LOAM),
            transition(rockSoil(type, SoilBlockType.Variant.LOAM), rockSoil(type, SoilBlockType.Variant.SILTY_LOAM)),
            rockSoil(type, SoilBlockType.Variant.SILTY_LOAM),
            transition(rockSoil(type, SoilBlockType.Variant.SILTY_LOAM), rockSoil(type, SoilBlockType.Variant.SILT)),
            rockSoil(type, SoilBlockType.Variant.SILT)
        );
        return new TFCFSoilSurfaceState(regions);
    }

    public static SurfaceState buildTypeRockSand(TFCFRockSand type)
    {
        final ImmutableList<SurfaceState> regions = ImmutableList.of(
            sand(),
            transition(sand(), rockSand(type)),
            rockSand(type),
            transition(rockSand(type), rockSand(type)),
            rockSand(type),
            transition(rockSand(type), rockSand(type)),
            rockSand(type),
            transition(rockSand(type), rockSand(type)),
            rockSand(type)
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

    /*public static SurfaceState rockTFC(Rock rock, Rock.BlockType type)
    {
        final Supplier<Block> block = TFCBlocks.ROCK_BLOCKS.get(rock).get(type);
        return context -> block.get().defaultBlockState();
    }*/

    public static SurfaceState rock(Rock.BlockType type)
    {
        return context -> {
            if (isTFCFRock(context))
            {
                return TFCFBlocks.TFCF_ROCK_BLOCKS.get(rockType(context)).get(type).get().defaultBlockState();
            }
            else
            {
                return TFCBlocks.ROCK_BLOCKS.get(rockType(context)).get(type).get().defaultBlockState();
            }
        };
    }

    public static SurfaceState rockCustom(RegistryRock rock, Rock.BlockType type)
    {
        return context -> {
            if (isCurrentRock(context, rock))
            {
                return TFCFBlocks.TFCF_ROCK_BLOCKS.get(rockType(context)).get(type).get().defaultBlockState();
            }
            else
            {
                return TFCBlocks.ROCK_BLOCKS.get(rockType(context)).get(type).get().defaultBlockState();
            }
        };
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

    /*public static SurfaceState rockSoilTFC(TFCFRockSoil type, SoilBlockType.Variant variant, Rock rock)
    {
        final Supplier<Block> block = TFCFBlocks.TFCROCKSOIL.get(type).get(variant).get(rock);
        return context -> block.get().defaultBlockState();
    }*/

    public static SurfaceState rockSoil(TFCFRockSoil type, SoilBlockType.Variant variant)
    {
        return context -> {
            if (isTFCFRock(context))
            {
                return TFCFBlocks.TFCROCKSOIL2.get(type).get(variant).get(rockType(context)).get().defaultBlockState();
            }
            else
            {
                return TFCFBlocks.TFCROCKSOIL.get(type).get(variant).get(rockType(context)).get().defaultBlockState();
            }
        };
    }

    public static SurfaceState rockSandGrass()
    {
        return context -> TFCFBlocks.SAND_GRASS.get(sandColor(context)).get().defaultBlockState();
    }

    public static SurfaceState rockSandSparseGrass()
    {
        return context -> TFCFBlocks.SPARSE_SAND_GRASS.get(sandColor(context)).get().defaultBlockState();
    }

    public static SurfaceState rockSandDenseGrass()
    {
        return context -> TFCFBlocks.DENSE_SAND_GRASS.get(sandColor(context)).get().defaultBlockState();
    }

    /*public static SurfaceState rockSandTFC(TFCFRockSand type, Rock rock)
    {
        return context -> TFCFBlocks.ROCKY_SAND_TFC.get(type).get(sandColor(context)).get(rock).get().defaultBlockState();
    }*/

    public static SurfaceState rockSand(TFCFRockSand type)
    {
        return context -> {
            if (isTFCFRock(context))
            {
                return TFCFBlocks.ROCKY_SAND_TFCF.get(type).get(sandColor(context)).get(rockType(context)).get().defaultBlockState();
            }
            else
            {
                return TFCFBlocks.ROCKY_SAND_TFC.get(type).get(sandColor(context)).get(rockType(context)).get().defaultBlockState();
            }
        };
    }

    public static SurfaceState rockRareSandGrass()
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
                return TFCFBlocks.SAND_GRASS.get(sandColor(context)).get().defaultBlockState();
            }
        };
    }

    public static SurfaceState rockRareSandSparseGrass()
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
                return TFCFBlocks.SPARSE_SAND_GRASS.get(sandColor(context)).get().defaultBlockState();
            }
        };
    }

    public static SurfaceState rockRareSandDenseGrass()
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
                return TFCFBlocks.DENSE_SAND_GRASS.get(sandColor(context)).get().defaultBlockState();
            }
        };
    }

    /*public static SurfaceState rockRareSandTFC(TFCFRockSand type)
    {
        return context -> {
            final Supplier<Block> pinkSand = TFCFBlocks.ROCKY_SAND_TFC.get(type).get(SandBlockType.PINK).get(rockType(context));
            final Supplier<Block> blackSand =  TFCFBlocks.ROCKY_SAND_TFC.get(type).get(SandBlockType.BLACK).get(rockType(context));

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
                return TFCFBlocks.ROCKY_SAND_TFC.get(type).get(sandColor(context)).get(rockType(context)).get().defaultBlockState();
            }
        };
    }*/

    public static SurfaceState rockRareSand(TFCFRockSand type)
    {
        return context -> {
            if (isTFCFRock(context))
            {
                final Supplier<Block> pinkSand = TFCFBlocks.ROCKY_SAND_TFCF.get(type).get(SandBlockType.PINK).get(rockType(context));
                final Supplier<Block> blackSand =  TFCFBlocks.ROCKY_SAND_TFCF.get(type).get(SandBlockType.BLACK).get(rockType(context));

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
                    return TFCFBlocks.ROCKY_SAND_TFCF.get(type).get(sandColor(context)).get(rockType(context)).get().defaultBlockState();
                }
            }
            else
            {
                final Supplier<Block> pinkSand = TFCFBlocks.ROCKY_SAND_TFC.get(type).get(SandBlockType.PINK).get(rockType(context));
                final Supplier<Block> blackSand =  TFCFBlocks.ROCKY_SAND_TFC.get(type).get(SandBlockType.BLACK).get(rockType(context));

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
                    return TFCFBlocks.ROCKY_SAND_TFC.get(type).get(sandColor(context)).get(rockType(context)).get().defaultBlockState();
                }
            }
        };
    }

    public static SandBlockType sandColor(SurfaceBuilderContext context)
    {
        SandBlockType sandColor = SandBlockType.YELLOW;
        if (context.getBottomRock().sand() != null)
        {
            for (SandBlockType sandColors : SandBlockType.values())
            {
                if (context.getBottomRock().sand() == TFCBlocks.SAND.get(sandColors).get())
                {
                    sandColor = sandColors;
                    break;
                }
            }
        }
        return sandColor;
    }

    public static RegistryRock rockType(SurfaceBuilderContext context)
    {
        Rock rockTFC = null;
        TFCFRock rockTFCF = null;

        if (surfaceRock(context) != null)
        {
            for (Rock r : Rock.values())
            {
                if (surfaceRock(context).raw() == TFCBlocks.ROCK_BLOCKS.get(r).get(Rock.BlockType.RAW).get())
                {
                    rockTFC = r;
                    break;
                }
                else
                {
                    for (TFCFRock r2 : TFCFRock.values())
                    {
                        if (surfaceRock(context).raw() == TFCFBlocks.TFCF_ROCK_BLOCKS.get(r2).get(Rock.BlockType.RAW).get())
                        {
                            rockTFCF = r2;
                            break;
                        }
                    }
                }
            }
        }
        if (rockTFC != null)
            return rockTFC;
        else if (rockTFCF != null)
            return rockTFCF;
        else
            return Rock.GRANITE;
    }

    public static boolean isTFCFRock(SurfaceBuilderContext context)
    {
        if (surfaceRock(context) != null)
        {
            for (TFCFRock r2 : TFCFRock.values())
            {
                if (surfaceRock(context).raw() == TFCFBlocks.TFCF_ROCK_BLOCKS.get(r2).get(Rock.BlockType.RAW).get())
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isCurrentRock(SurfaceBuilderContext context, RegistryRock rock)
    {
        return rockType(context) == rock;
    }

    public static RegistrySoilVariant currentSoilVariant(SurfaceBuilderContext context)
    {
        SoilBlockType.Variant variantTFC = null;

        for (SoilBlockType type : SoilBlockType.values())
        {
            for (SoilBlockType.Variant r : SoilBlockType.Variant.values())
            {
                if (SoilSurfaceState.buildType(type).getState(context).getBlock() == TFCBlocks.SOIL.get(type).get(r).get())
                {
                    variantTFC = r;
                    break;
                }
            }
        }
        if (variantTFC != null)
            return variantTFC;
        else
            return SoilBlockType.Variant.LOAM;
    }

    public static RockSettings surfaceRock(SurfaceBuilderContext context)
    {
        return context.getRockData().getRock(context.pos());
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

    public static RegistrySoilVariant getSoilVariant(WorldGenLevel level, BlockPos pos)
    {
        final Random random = level.getRandom();
        final ChunkDataProvider provider = ChunkDataProvider.get(level);
        final ChunkData data = provider.get(level, pos);

        // Adjust rainfall to bias a little bit towards sand regions
        // Without: pure sand < 55mm, mixed sand < 105mm. With: pure sand < 75mm, mixed sand < 136mm
        final float rainfall = data.getRainfall(pos) + 20f;
        final int index = Mth.clamp((int) Mth.clampedMap(rainfall, 0, 500, 0, 9 - 0.01f), 0, 9 - 1);

        switch (index)
        {
            case 1:
                return transitionSoil(SoilBlockType.Variant.SANDY_LOAM, SoilBlockType.Variant.SANDY_LOAM, pos, random);
            case 2:
                return SoilBlockType.Variant.SANDY_LOAM;
            case 3:
                return transitionSoil(SoilBlockType.Variant.SANDY_LOAM, SoilBlockType.Variant.LOAM, pos, random);
            case 4:
                return SoilBlockType.Variant.LOAM;
            case 5:
                return transitionSoil(SoilBlockType.Variant.LOAM, SoilBlockType.Variant.SILTY_LOAM, pos, random);
            case 6:
                return SoilBlockType.Variant.SILTY_LOAM;
            case 7:
                return transitionSoil(SoilBlockType.Variant.SILTY_LOAM, SoilBlockType.Variant.SILT, pos, random);
            case 8:
                return SoilBlockType.Variant.SILT;
            default:
                return SoilBlockType.Variant.SANDY_LOAM;
        }
    }

    public static RegistrySoilVariant transitionSoil(RegistrySoilVariant first, RegistrySoilVariant second, BlockPos pos, Random random)
    {
        float noise = SoilSurfaceState.PATCH_NOISE.noise(pos.getX(), pos.getZ());
        float noiseGauss = noise + (2 * (float) random.nextGaussian());
        return noiseGauss > 0 ? first : second;
    }
}