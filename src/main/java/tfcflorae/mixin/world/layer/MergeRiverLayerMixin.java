package tfcflorae.mixin.world.layer;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.LerpFloatLayer;
import net.dries007.tfc.world.layer.MergeRiverLayer;
import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.Area;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.TransformLayer;
import net.dries007.tfc.world.river.MidpointFractal;
import net.dries007.tfc.world.river.Watershed;

@Mixin(MergeRiverLayer.class)
public class MergeRiverLayerMixin implements TransformLayer
{
    @Unique @Final private ChunkData chunkData;
    @Nullable @Unique @Final private LerpFloatLayer rainfallLayer;
    @Shadow private final Watershed.Context watersheds;

    public MergeRiverLayerMixin(Watershed.Context watersheds, LerpFloatLayer rainfallLayer)
    {
        this.watersheds = watersheds;
        this.rainfallLayer = rainfallLayer;
    }

    @Overwrite(remap = false)
    @Override
    public int apply(AreaContext context, Area area, int x, int z)
    {
        final int value = area.get(x, z);
        if (TFCLayers.hasRiver(value))
        {
            final float scale = 1f / (1 << 7);
            final float x0 = x * scale, z0 = z * scale;
            for (MidpointFractal fractal : watersheds.getFractalsByPartition(x, z))
            {
                float rainfall = 1f;
                if (rainfallLayer != null)
                {
                    rainfall = rainfallLayer.getValue(((int) z0 & 15) / 16f, 1 - (((int) x0 & 15) / 16f)) / 65f;
                }
                else if (chunkData != null)
                {
                    rainfall = chunkData.getRainfall((int) x0, (int) z0) / 65f;
                }

                // maybeIntersect will skip the more expensive calculation if it fails
                if (fractal.maybeIntersect(x0, z0, Watershed.RIVER_WIDTH * rainfall) && fractal.intersect(x0, z0, Watershed.RIVER_WIDTH * rainfall))
                {
                    return TFCLayers.riverFor(value);
                }
            }
        }
        return value;
    }
}
