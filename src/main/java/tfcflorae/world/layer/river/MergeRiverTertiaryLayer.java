package tfcflorae.world.layer.river;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.Area;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.TransformLayer;
import net.dries007.tfc.world.river.MidpointFractal;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public class MergeRiverTertiaryLayer implements TransformLayer
{
    private final WatershedTertiary.Context watersheds;
    public static TFCLayers staticBiomes = new TFCLayers();

    static final int RIVER_EDGE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRiverEdge();

    public MergeRiverTertiaryLayer(WatershedTertiary.Context watersheds)
    {
        this.watersheds = watersheds;
    }

    @Override
    public int apply(AreaContext context, Area area, int x, int z)
    {
        final int value = area.get(x, z);
        if ((hasRiver(value) || value == RIVER_EDGE))
        {
            final float scale = 1f / (1 << 7);
            final float x0 = x * scale, z0 = z * scale;
            for (MidpointFractal fractal : watersheds.getFractalsByPartition(x, z))
            {
                // maybeIntersect will skip the more expensive calculation if it fails
                if (fractal.maybeIntersect(x0, z0, WatershedTertiary.RIVER_WIDTH) && fractal.intersect(x0, z0, WatershedTertiary.RIVER_WIDTH))
                {
                    return riverFor(value);
                }
            }
        }
        return value;
    }
}
