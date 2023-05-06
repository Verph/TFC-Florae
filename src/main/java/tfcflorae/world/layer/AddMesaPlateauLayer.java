package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterTransformLayer;
import net.dries007.tfc.world.layer.TFCLayers;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum AddMesaPlateauLayer implements CenterTransformLayer
{
    SMALL(70),
    LARGE(200);

    private final int chance;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int MESA_PLATEAU_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMesaPlateauMarker();

    AddMesaPlateauLayer(int chance)
    {
        this.chance = chance;
    }

    @Override
    public int apply(AreaContext context, int value)
    {
        if (value == INLAND_MARKER && context.random().nextInt(chance) == 0)
        {
            return MESA_PLATEAU_MARKER;
        }
        return value;
    }
}