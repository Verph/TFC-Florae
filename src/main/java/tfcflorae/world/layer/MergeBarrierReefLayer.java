package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterMergeLayer;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum MergeBarrierReefLayer implements CenterMergeLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int BARRIER_REEF = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReef();
    static final int SHORE_DUNES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShoreDunes();
    static final int BARRIER_REEF_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReefMarker();
    static final int BARRIER_REEF_SHORE_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReefShoreMarker();
    static final int BARRIER_REEF_OCEAN_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReefOceanMarker();
    static final int ATOLL_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtollMarker();
    static final int ATOLL = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtoll();
    static final int NEAR_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticNearShore();

    @Override
    public int apply(AreaContext context, int center, int layer)
    {
        /*if (isOceanOrMarker(center) && !(center == OCEAN || center == OCEAN_REEF || center == NEAR_SHORE || center == SHORE))
        {
            if (layer == BARRIER_REEF_MARKER)
            {
                return BARRIER_REEF;
            }
            else if (layer == SHORE)
            {
                return SHORE;
            }
            else if (layer == BARRIER_REEF_SHORE_MARKER)
            {
                return SHORE;
            }
            else if (layer == OCEAN_REEF)
            {
                return OCEAN_REEF;
            }
            else if (layer == BARRIER_REEF_OCEAN_MARKER)
            {
                return OCEAN_REEF;
            }
            else if (layer == ATOLL_MARKER)
            {
                return ATOLL;
            }
            else if (layer == SHORE_DUNES)
            {
                return SHORE_DUNES;
            }
        }*/
        if (layer == BARRIER_REEF_MARKER)
        {
            return BARRIER_REEF;
        }
        else if (layer == SHORE)
        {
            return SHORE;
        }
        else if (layer == BARRIER_REEF_SHORE_MARKER)
        {
            return SHORE;
        }
        else if (layer == OCEAN_REEF)
        {
            return OCEAN_REEF;
        }
        else if (layer == BARRIER_REEF_OCEAN_MARKER)
        {
            return OCEAN_REEF;
        }
        else if (layer == ATOLL_MARKER)
        {
            return ATOLL;
        }
        else if (layer == SHORE_DUNES)
        {
            return SHORE_DUNES;
        }
        return center;
    }
}
