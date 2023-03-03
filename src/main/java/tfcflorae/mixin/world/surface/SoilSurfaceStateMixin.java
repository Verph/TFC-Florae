package tfcflorae.mixin.world.surface;

import org.spongepowered.asm.mixin.*;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;
import net.dries007.tfc.world.surface.SoilSurfaceState;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceState;

import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.world.surface.TFCFSoilSurfaceState;

@Mixin(SoilSurfaceState.class)
public class SoilSurfaceStateMixin implements SurfaceState
{
    @Shadow public static final Noise2D PATCH_NOISE = new OpenSimplex2D(18273952837592L).octaves(2).spread(0.04f);

    @Overwrite(remap = false)
    public static SurfaceState buildType(SoilBlockType type)
    {
        final ImmutableList<SurfaceState> regions = ImmutableList.of(
            sand(),
            transitionSoil(sand(), soil(type, SoilBlockType.Variant.SANDY_LOAM)),
            soil(type, SoilBlockType.Variant.SANDY_LOAM),
            transitionSoil(soil(type, SoilBlockType.Variant.SANDY_LOAM), soil(type, SoilBlockType.Variant.LOAM)),
            soil(type, SoilBlockType.Variant.LOAM),
            transitionSoil(soil(type, SoilBlockType.Variant.LOAM), soil(type, SoilBlockType.Variant.SILTY_LOAM)),
            soil(type, SoilBlockType.Variant.SILTY_LOAM),
            transitionSoil(soil(type, SoilBlockType.Variant.SILTY_LOAM), soil(type, SoilBlockType.Variant.SILT)),
            soil(type, SoilBlockType.Variant.SILT)
        );
        return type == SoilBlockType.GRASS ? new SoilSurfaceStateMixin.NeedsPostProcessing(regions) : new SoilSurfaceStateMixin(regions);
    }

    @Overwrite(remap = false)
    public static SurfaceState buildSandOrGravel(boolean sandIsSandstone)
    {
        final SurfaceState sand = sandIsSandstone ? sandstone() : sand();
        final SurfaceState gravel = gravel();
        return new SoilSurfaceStateMixin(ImmutableList.of(
            sand,
            transitionSoil(sand, gravel),
            gravel,
            gravel,
            gravel,
            gravel,
            gravel,
            gravel,
            gravel
        ));
    }

    @Unique
    private static SurfaceState transitionSoil(SurfaceState first, SurfaceState second)
    {
        return context -> {
            final BlockPos pos = context.pos();
            final Random random = new Random();
            float noise = PATCH_NOISE.noise(pos.getX(), pos.getZ());
            float noiseGauss = noise + (2 * (float) random.nextGaussian());
            float noiseRainfall = context.rainfall() + (10 * (float) random.nextGaussian());

            if (noiseRainfall <= 80f)
            {
                if (noiseRainfall <= 50f)
                {
                    return sand().getState(context);
                }
                else
                {
                    SandBlockType sandColor = SandBlockType.YELLOW;
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
                    if (noiseGauss > 0)
                        return sand().getState(context);
                    else if (noiseGauss > -0.5F)
                        return TFCFSoilSurfaceState.rockSandSparseGrass(sandColor).getState(context);
                    else
                        return TFCFSoilSurfaceState.rockSandDenseGrass(sandColor).getState(context);
                }
            }
            else if (noiseRainfall <= 110f)
            {
                if (noiseGauss > 0)
                    return TFCFSoilSurfaceState.buildType(TFCFSoil.DENSE_GRASS).getState(context);
                else
                    return TFCFSoilSurfaceState.buildType(TFCFSoil.SPARSE_GRASS).getState(context);
            }
            else
            {
                return noiseGauss > 0 ? first.getState(context) : second.getState(context);
            }
        };
    }

    @Shadow
    private static SurfaceState sand()
    {
        return context -> context.getRock().sand().defaultBlockState();
    }

    @Shadow
    private static SurfaceState sandstone()
    {
        return context -> context.getRock().sandstone().defaultBlockState();
    }

    @Shadow
    private static SurfaceState gravel()
    {
        return context -> context.getRock().gravel().defaultBlockState();
    }

    @Shadow
    private static SurfaceState soil(SoilBlockType type, SoilBlockType.Variant variant)
    {
        final Supplier<Block> block = TFCBlocks.SOIL.get(type).get(variant);
        return context -> block.get().defaultBlockState();
    }

    @Shadow
    private final List<SurfaceState> regions;

    private SoilSurfaceStateMixin(List<SurfaceState> regions)
    {
        this.regions = regions;
    }

    @Shadow
    @Override
    public BlockState getState(SurfaceBuilderContext context)
    {
        // Adjust rainfall to bias a little bit towards sand regions
        // Without: pure sand < 55mm, mixed sand < 105mm. With: pure sand < 75mm, mixed sand < 136mm
        final float rainfall = context.rainfall() + 20f;
        final int index = Mth.clamp((int) Mth.clampedMap(rainfall, 0, 500, 0, regions.size() - 0.01f), 0, regions.size() - 1);

        return regions.get(index).getState(context);
    }

    static class NeedsPostProcessing extends SoilSurfaceStateMixin
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
