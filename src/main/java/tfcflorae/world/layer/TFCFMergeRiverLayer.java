package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterMergeLayer;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum TFCFMergeRiverLayer implements CenterMergeLayer
{
    INSTANCE;

    @Override
    public int apply(AreaContext context, int main, int river)
    {
        return river == RIVER && TFCLayers.hasRiver(main) ? TFCLayers.riverFor(main) : main;
    }
}
