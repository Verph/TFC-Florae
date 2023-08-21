package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterMergeLayer;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum MergeGuyotsLayer implements CenterMergeLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int GUYOTS_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGuyotsMarker();
    static final int GUYOTS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGuyots();
    static final int PELAGIC_ZONE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPelagicZone();
    static final int NEAR_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticNearShore();

    @Override
    public int apply(AreaContext context, int center, int layer)
    {
        return layer == GUYOTS_MARKER ? GUYOTS : center;
    }
}
