package tfcflorae.world.layer;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum LakeEdgeLayer implements AdjacentTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int LAKE_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticLakeShore();

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (center != LAKE && !isRiver(center))
        {
            if (matcher.test(i -> i == LAKE))
            {
                return LAKE_SHORE;
            }
        }
        return center;
    }
}