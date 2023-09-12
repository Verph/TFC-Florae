package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterMergeLayer;

import tfcflorae.interfaces.TFCLayersMixinInterface;

public enum MergeBarrierReefLayer implements CenterMergeLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int BARRIER_REEF_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReefMarker();
    static final int BARRIER_REEF = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReef();

    @Override
    public int apply(AreaContext context, int center, int layer)
    {
        if (layer == BARRIER_REEF_MARKER)
        {
            return BARRIER_REEF;
        }
        return center;
    }
}
