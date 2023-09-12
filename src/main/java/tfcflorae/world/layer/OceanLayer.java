package tfcflorae.world.layer;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum OceanLayer implements AdjacentTransformLayer
{
    INSTANCE;

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        return matcher.test(TFCLayers::isOceanOrMarker) ? center : NULL_MARKER;
    }
}
