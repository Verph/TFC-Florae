package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

public enum BarrierReefAtollBorderLayer implements AdjacentTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int BARRIER_REEF_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReefMarker();
    static final int BARRIER_REEF_SHORE_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReefShoreMarker();
    static final int BARRIER_REEF_OCEAN_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReefOceanMarker();
    static final int LAGOON = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticLagoon();
    static final int LAGOON_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticLagoonMarker();
    static final int ATOLL_MARKER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtollMarker();

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (center == BARRIER_REEF_OCEAN_MARKER)
        {
            if (matcher.test(i -> i != BARRIER_REEF_OCEAN_MARKER && i != BARRIER_REEF_SHORE_MARKER && i != BARRIER_REEF_MARKER))
            {
                return ATOLL_MARKER;
            }
        }
        else if (center == LAGOON_MARKER || center == LAGOON)
        {
            if (matcher.test(i -> !(i == LAGOON_MARKER || i == LAGOON_MARKER)))
            {
                return ATOLL_MARKER;
            }
        }
        return center;
    }
}