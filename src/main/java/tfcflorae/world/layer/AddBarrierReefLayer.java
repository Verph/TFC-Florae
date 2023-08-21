package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterTransformLayer;
import net.dries007.tfc.world.layer.TFCLayers;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum AddBarrierReefLayer implements CenterTransformLayer
{
    SMALL(1),
    LARGE(3),
    HUGE(6);

    private final int chance;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int BARRIER_REEF_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReefMarker();
    static final int NEAR_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticNearShore();

    AddBarrierReefLayer(int chance)
    {
        this.chance = chance;
    }

    @Override
    public int apply(AreaContext context, int value)
    {
        //if ((isOceanOrMarker(value) || value != INLAND_MARKER) && !(value == SHORE || value == NEAR_SHORE) && context.random().nextInt(chance) == 0)
        /*if (isOceanOrMarker(value) && !(value == SHORE || value == NEAR_SHORE) && context.random().nextInt(chance) == 0)
        {
            return BARRIER_REEF_MARKER;
        }
        return value;*/

        if (value == OCEAN_OCEAN_CONVERGING_MARKER)
        {
            final int r = context.random().nextInt(15);
            if (r <= chance)
            {
                return BARRIER_REEF_MARKER;
            }
        }
        else if (value == OCEAN_OCEAN_DIVERGING_MARKER)
        {
            final int r = context.random().nextInt(30);
            if (r <= chance)
            {
                return BARRIER_REEF_MARKER;
            }
        }
        else if (value == DEEP_OCEAN)
        {
            final int r = context.random().nextInt(50);
            if (r <= chance)
            {
                return BARRIER_REEF_MARKER;
            }
        }
        else if (value == OCEAN)
        {
            final int r = context.random().nextInt(30);
            if (r <= chance)
            {
                return BARRIER_REEF_MARKER;
            }
        }
        return value;
    }
}