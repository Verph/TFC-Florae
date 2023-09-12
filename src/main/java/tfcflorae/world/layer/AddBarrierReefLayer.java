package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterTransformLayer;
import net.dries007.tfc.world.layer.TFCLayers;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum AddBarrierReefLayer implements CenterTransformLayer
{
    SMALL(50),
    LARGE(180);

    private final int chance;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int BARRIER_REEF_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReefMarker();

    AddBarrierReefLayer(int chance)
    {
        this.chance = chance;
    }

    @Override
    public int apply(AreaContext context, int value)
    {
        if ((value == DEEP_OCEAN || value == OCEAN || value == OCEAN_REEF) && context.random().nextInt(chance) == 0)
        {
            return BARRIER_REEF_MARKER;
        }
        return value;
    }
}