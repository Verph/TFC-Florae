package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.Area;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.TransformLayer;
import net.dries007.tfc.world.river.MidpointFractal;
import net.dries007.tfc.world.river.Watershed;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public class MergeRiverBanksLayer implements TransformLayer
{
    private final WatershedBank.Context watersheds;
    public static TFCLayers staticBiomes = new TFCLayers();

    static final int RIVERBANK = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRiverbank();
    static final int WETLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticWetlands();

    public MergeRiverBanksLayer(WatershedBank.Context watersheds)
    {
        this.watersheds = watersheds;
    }

    @Override
    public int apply(AreaContext context, Area area, int x, int z)
    {
        final int value = area.get(x, z);
        if (TFCLayers.hasRiver(value) && value == RIVER)
        {
            final float scale = 1f / (1 << 7);
            final float x0 = x * scale, z0 = z * scale;
            for (MidpointFractal fractal : watersheds.getFractalsByPartition(x, z))
            {
                // maybeIntersect will skip the more expensive calculation if it fails
                if ((fractal.maybeIntersect(x0, z0, WatershedBank.RIVER_WIDTH * 4.5f) && fractal.intersect(x0, z0, WatershedBank.RIVER_WIDTH * 4.5f)) && !(fractal.maybeIntersect(x0, z0, Watershed.RIVER_WIDTH * 2f) && fractal.intersect(x0, z0, Watershed.RIVER_WIDTH * 2f)))
                {
                    return WETLANDS;
                }
            }
        }
        return value;
    }
}