package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterMergeLayer;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum MergeBarrierShoreLayer implements CenterMergeLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int BARRIER_REEF_SHORE_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReefShoreMarker();

    @Override
    public int apply(AreaContext context, int center, int layer)
    {
        if (layer == BARRIER_REEF_SHORE_MARKER)
        {
            return SHORE;
        }
        return center;
    }
}
