package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterMergeLayer;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum MergeAtollLayer implements CenterMergeLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int ATOLL_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtollMarker();
    static final int ATOLL = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtoll();
    static final int LAGOON_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticLagoonMarker();
    static final int LAGOON = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticLagoon();
    static final int SHORE_DUNES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShoreDunes();

    @Override
    public int apply(AreaContext context, int center, int layer)
    {
        /*if (isOceanOrMarker(center))
        {
            if (layer == ATOLL_MARKER)
            {
                return ATOLL;
            }
            else if (layer == LAGOON_MARKER)
            {
                return LAGOON;
            }
            else if (layer == SHORE_DUNES)
            {
                return SHORE_DUNES;
            }
            else if (layer == SHORE)
            {
                return SHORE;
            }
        }*/
        if (layer == ATOLL_MARKER)
        {
            return ATOLL;
        }
        else if (layer == LAGOON_MARKER)
        {
            return LAGOON;
        }
        else if (layer == SHORE_DUNES)
        {
            return SHORE_DUNES;
        }
        else if (layer == SHORE)
        {
            return SHORE;
        }
        return center;
    }
}
