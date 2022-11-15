package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.CenterMergeLayer;

public enum TFCFMergeRiverLayer implements CenterMergeLayer
{
    INSTANCE;

    @Override
    public int apply(AreaContext context, int main, int river)
    {
        return main;
    }
}
