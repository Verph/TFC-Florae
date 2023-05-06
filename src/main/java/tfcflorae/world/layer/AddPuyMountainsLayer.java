package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterTransformLayer;
import net.dries007.tfc.world.layer.TFCLayers;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum AddPuyMountainsLayer implements CenterTransformLayer
{
    SMALL(110),
    LARGE(260);

    private final int chance;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int PUY_MOUNTAINS_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPuyMountainsMarker();

    AddPuyMountainsLayer(int chance)
    {
        this.chance = chance;
    }

    @Override
    public int apply(AreaContext context, int value)
    {
        if (value == INLAND_MARKER && context.random().nextInt(chance) == 0)
        {
            return PUY_MOUNTAINS_MARKER;
        }
        return value;
    }
}