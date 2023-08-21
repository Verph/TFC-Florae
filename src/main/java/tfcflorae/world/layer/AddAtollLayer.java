package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterTransformLayer;
import net.dries007.tfc.world.layer.TFCLayers;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum AddAtollLayer implements CenterTransformLayer
{
    SMALL(1),
    LARGE(6);

    private final int chance;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int LAGOON_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticLagoonMarker();
    static final int NEAR_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticNearShore();

    AddAtollLayer(int chance)
    {
        this.chance = chance;
    }

    @Override
    public int apply(AreaContext context, int value)
    {
        //if ((isOceanOrMarker(value) || value != INLAND_MARKER) && !(value == SHORE || value == NEAR_SHORE) && context.random().nextInt(chance) == 0)
        /*if (isOceanOrMarker(value) && !(value == SHORE || value == NEAR_SHORE) && context.random().nextInt(chance) == 0)
        {
            return LAGOON_MARKER;
        }
        return value;*/

        if (value == OCEAN_OCEAN_CONVERGING_MARKER)
        {
            final int r = context.random().nextInt(15);
            if (r <= chance)
            {
                return LAGOON_MARKER;
            }
        }
        else if (value == OCEAN_OCEAN_DIVERGING_MARKER)
        {
            final int r = context.random().nextInt(30);
            if (r <= chance)
            {
                return LAGOON_MARKER;
            }
        }
        else if (value == DEEP_OCEAN)
        {
            final int r = context.random().nextInt(50);
            if (r <= chance)
            {
                return LAGOON_MARKER;
            }
        }
        else if (value == OCEAN)
        {
            final int r = context.random().nextInt(30);
            if (r <= chance)
            {
                return LAGOON_MARKER;
            }
        }
        return value;
    }
}