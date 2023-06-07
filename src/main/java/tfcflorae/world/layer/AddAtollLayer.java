package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterTransformLayer;
import net.dries007.tfc.world.layer.TFCLayers;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum AddAtollLayer implements CenterTransformLayer
{
    //SMALL(80),
    //LARGE(200);
    SMALL(1),
    LARGE(6);

    private final int chance;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int ATOLL_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtollMarker();
    static final int ATOLL = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtoll();
    static final int NEAR_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticNearShore();

    AddAtollLayer(int chance)
    {
        this.chance = chance;
    }

    @Override
    public int apply(AreaContext context, int value)
    {
        if ((isOceanOrMarker(value) && !(value == OCEAN || value == OCEAN_REEF || value == NEAR_SHORE)) && context.random().nextInt(chance) == 0)
        {
            return ATOLL_MARKER;
        }
        return value;
    }
}