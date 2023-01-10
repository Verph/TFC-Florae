package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.Area;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.TransformLayer;
import net.dries007.tfc.world.river.MidpointFractal;

import tfcflorae.interfaces.TFCLayersMixinInterface;

public class MergeChasmsLayer implements TransformLayer
{
    private final Chasm.Context chasm;
    public static TFCLayers staticBiomes = new TFCLayers();

    static final int CHASMS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticChasms();

    public MergeChasmsLayer(Chasm.Context chasm)
    {
        this.chasm = chasm;
    }

    @Override
    public int apply(AreaContext context, Area area, int x, int z)
    {
        final int value = area.get(x, z);
        //if (TFCLayers.isMountains(value))
        if (!(TFCLayers.isOceanOrMarker(value) || TFCLayers.isLake(value) || TFCLayers.isRiver(value) || TFCLayers.isLow(value)))
        {
            final float scale = 1f / (1 << 7);
            final float x0 = x * scale, z0 = z * scale;
            for (MidpointFractal fractal : chasm.getFractalsByPartition(x, z))
            {
                // maybeIntersect will skip the more expensive calculation if it fails
                if (fractal.maybeIntersect(x0, z0, Chasm.CHASM_WIDTH) && fractal.intersect(x0, z0, Chasm.CHASM_WIDTH))
                {
                    return CHASMS;
                }
            }
        }
        return value;
    }
}