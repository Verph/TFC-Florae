package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterTransformLayer;
import net.dries007.tfc.world.layer.TFCLayers;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum AddAtollLayer implements CenterTransformLayer
{
    SMALL(40),
    LARGE(160);

    private final int chance;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int LAGOON_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticLagoonMarker();

    AddAtollLayer(int chance)
    {
        this.chance = chance;
    }

    @Override
    public int apply(AreaContext context, int value)
    {
        if ((value == DEEP_OCEAN || value == OCEAN || value == OCEAN_REEF) && context.random().nextInt(chance) == 0)
        {
            return LAGOON_MARKER;
        }
        return value;
    }
}