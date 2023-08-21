package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.Area;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.TransformLayer;
import net.dries007.tfc.world.river.MidpointFractal;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public class MergeChasmsLayer implements TransformLayer
{
    private final Chasm.Context chasm;
    public static TFCLayers staticBiomes = new TFCLayers();

    static final int RIVERBANK = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRiverbank();
    static final int CHASMS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticChasms();
    static final int ATOLL = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtoll();
    static final int NEAR_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticNearShore();
    static final int SHORE_DUNES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShoreDunes();

    public MergeChasmsLayer(Chasm.Context chasm)
    {
        this.chasm = chasm;
    }

    @Override
    public int apply(AreaContext context, Area area, int x, int z)
    {
        final int value = area.get(x, z);
        if (!currentBiome(value))
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

    public boolean currentBiome(int value)
    {
        return TFCLayers.isOceanOrMarker(value) || TFCLayers.isLake(value) || TFCLayers.isRiver(value) || TFCLayers.isLow(value) || value == ATOLL || value == NEAR_SHORE || value == SHORE || value == SHORE_DUNES || value == NULL_MARKER || value == LAKE_MARKER;
    }
}