package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterTransformLayer;
import net.dries007.tfc.world.layer.TFCLayers;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum AddPelagicZonesLayer implements CenterTransformLayer
{
    SMALL(60),
    LARGE(200);

    private final int chance;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int PELAGIC_ZONES_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPelagicZoneMarker();

    AddPelagicZonesLayer(int chance)
    {
        this.chance = chance;
    }

    @Override
    public int apply(AreaContext context, int value)
    {
        if ((value == DEEP_OCEAN || value == DEEP_OCEAN_TRENCH) && context.random().nextInt(chance) == 0)
        {
            return PELAGIC_ZONES_MARKER;
        }
        return value;
    }
}