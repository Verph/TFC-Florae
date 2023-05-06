package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterMergeLayer;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum MergeCalderaLayer implements CenterMergeLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int CALDERA_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCalderaMarker();
    static final int CALDERAS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCalderas();
    static final int SHRUBLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShrublands();
    static final int VINICUNCA_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticVinicuncaMountains();

    @Override
    public int apply(AreaContext context, int center, int caldera)
    {
        return caldera == CALDERA_MARKER && isMountains(center) && !(center == SHRUBLANDS || center == VINICUNCA_MOUNTAINS) ? CALDERAS : center;
    }
}
