package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterTransformLayer;
import net.dries007.tfc.world.layer.TFCLayers;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum AddPelagicZonesLayer implements CenterTransformLayer
{
    SMALL(1),
    LARGE(6);

    private final int chance;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int PELAGIC_ZONES_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPelagicZoneMarker();
    static final int PELAGIC_ZONE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPelagicZone();
    static final int NEAR_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticNearShore();

    AddPelagicZonesLayer(int chance)
    {
        this.chance = chance;
    }

    @Override
    public int apply(AreaContext context, int value)
    {
        if ((isOceanOrMarker(value) && !(value == OCEAN || value == OCEAN_REEF || value == NEAR_SHORE)) && context.random().nextInt(chance) == 0)
        {
            return PELAGIC_ZONES_MARKER;
        }
        return value;
    }
}