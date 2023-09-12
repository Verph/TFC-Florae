package tfcflorae.world.layer;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import tfcflorae.interfaces.TFCLayersMixinInterface;

public enum BarrierReefIslandShoreBorderLayer implements AdjacentTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int BARRIER_REEF_SHORE_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReefShoreMarker();
    static final int BARRIER_REEF = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReef();
    static final int ATOLL = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtoll();

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (center == BARRIER_REEF)
        {
            if (matcher.test(i -> i != center))
            {
                return BARRIER_REEF_SHORE_MARKER;
            }
        }
        return center;
    }
}