package tfcflorae.world.surface;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.world.surface.SoilSurfaceState;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceState;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.soil.TFCFSoil;

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

    private static SurfaceState transition(SurfaceState first, SurfaceState second)
    {
        return context -> {
            final BlockPos pos = context.pos();
            float noise = SoilSurfaceState.PATCH_NOISE.noise(pos.getX(), pos.getZ());
            return noise > 0 ? first.getState(context) : second.getState(context);
        };
    }

    private static SurfaceState sand()
    {
        return context -> context.getRock().sand().defaultBlockState();
    }

    private static SurfaceState sandstone()
    {
        return context -> context.getRock().sandstone().defaultBlockState();
    }

    private static SurfaceState gravel()
    {
        return context -> context.getRock().gravel().defaultBlockState();
    }

    private static SurfaceState soil(TFCFSoil type, SoilBlockType.Variant variant)
    {
        final Supplier<Block> block = TFCFBlocks.TFCSOIL.get(type).get(variant);
        return context -> block.get().defaultBlockState();
    }

    private static SurfaceState TFCFsoil(TFCFSoil type, TFCFSoil.TFCFVariant variant)
    {
        final Supplier<Block> block = TFCFBlocks.TFCFSOIL.get(type).get(variant);
        return context -> block.get().defaultBlockState();
    }

    private final List<SurfaceState> regions;

    private TFCFSoilSurfaceState(List<SurfaceState> regions)
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