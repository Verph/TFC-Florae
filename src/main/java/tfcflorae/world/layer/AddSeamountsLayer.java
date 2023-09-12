package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterTransformLayer;
import net.dries007.tfc.world.layer.TFCLayers;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum AddSeamountsLayer implements CenterTransformLayer
{
    SMALL(60),
    LARGE(200);

    private final int chance;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int SEAMOUNTS_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSeamountsMarker();
    static final int PELAGIC_ZONE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPelagicZone();

    AddSeamountsLayer(int chance)
    {
        this.chance = chance;
    }

    @Override
    public int apply(AreaContext context, int value)
    {
        if ((value == DEEP_OCEAN || value == DEEP_OCEAN_TRENCH || value == PELAGIC_ZONE) && context.random().nextInt(chance) == 0)
        {
            return SEAMOUNTS_MARKER;
        }
        return value;
    }
}