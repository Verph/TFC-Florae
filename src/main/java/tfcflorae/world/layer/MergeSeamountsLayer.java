package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterMergeLayer;

import tfcflorae.interfaces.TFCLayersMixinInterface;

public enum MergeSeamountsLayer implements CenterMergeLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int SEAMOUNTS_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSeamountsMarker();
    static final int SEAMOUNTS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSeamounts();
    static final int PELAGIC_ZONE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPelagicZone();

    @Override
    public int apply(AreaContext context, int center, int layer)
    {
        return layer == SEAMOUNTS_MARKER ? SEAMOUNTS : center;
    }
}
