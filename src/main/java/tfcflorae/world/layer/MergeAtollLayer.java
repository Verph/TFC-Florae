package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterMergeLayer;

import tfcflorae.interfaces.TFCLayersMixinInterface;

public enum MergeAtollLayer implements CenterMergeLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int ATOLL_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtollMarker();
    static final int ATOLL = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtoll();

    @Override
    public int apply(AreaContext context, int center, int layer)
    {
        return layer == ATOLL_MARKER ? ATOLL : center;
    }
}
