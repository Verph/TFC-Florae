package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterMergeLayer;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum MergeLagoonLayer implements CenterMergeLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int BARRIER_REEF_SHORE_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReefOceanMarker();
    static final int BARRIER_REEF_OCEAN_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReefShoreMarker();
    static final int BARRIER_REEF_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReefMarker();
    static final int BARRIER_REEF = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReef();
    static final int LAGOON_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticLagoonMarker();
    static final int LAGOON = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticLagoon();
    static final int ATOLL_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtollMarker();
    static final int ATOLL = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtoll();

    @Override
    public int apply(AreaContext context, int center, int layer)
    {
        if (layer == BARRIER_REEF_SHORE_MARKER)
        {
            return SHORE;
        }
        if (layer == ATOLL_MARKER)
        {
            return ATOLL;
        }
        if (layer == BARRIER_REEF_OCEAN_MARKER)
        {
            return LAGOON;
        }
        if (layer == LAGOON_MARKER)
        {
            return LAGOON;
        }
        if (layer == BARRIER_REEF_MARKER)
        {
            return BARRIER_REEF;
        }
        return center;
    }
}
