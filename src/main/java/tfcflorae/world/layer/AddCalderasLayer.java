package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterTransformLayer;
import net.dries007.tfc.world.layer.TFCLayers;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum AddCalderasLayer implements CenterTransformLayer
{
    SMALL(16),
    LARGE(80);

    private final int chance;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int CALDERA_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCalderaMarker();
    static final int SHRUBLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShrublands();
    static final int VINICUNCA_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticVinicuncaMountains();

    AddCalderasLayer(int chance)
    {
        this.chance = chance;
    }

    @Override
    public int apply(AreaContext context, int value)
    {
        if (value == INLAND_MARKER && isMountains(value) && !(value == SHRUBLANDS || value == VINICUNCA_MOUNTAINS) && context.random().nextInt(chance) == 0)
        {
            return CALDERA_MARKER;
        }
        return value;
    }
}