package tfcflorae.world.layer;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import net.dries007.tfc.world.layer.TFCLayers;

import static net.dries007.tfc.world.layer.TFCLayers.*;

/**
 * Operates on the {@link TFCLayers#OCEAN_REEF_MARKER} markers
 * Borders reef - land with ocean, and adds ocean to reef - deep ocean borders
 */
public enum ReefBorderLayerTFCF implements AdjacentTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int ATOLL = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtoll();
    static final int LAGOON = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticLagoon();
    static final int BARRIER_REEF = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReef();

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (center == OCEAN_REEF_MARKER)
        {
            if (matcher.test(i -> !isOceanOrMarker(i) || i != ATOLL || i != LAGOON || i != BARRIER_REEF))
            {
                return OCEAN;
            }
            return OCEAN_REEF;
        }
        else if (isOceanOrMarker(center) && matcher.test(i -> i == OCEAN_REEF_MARKER))
        {
            return OCEAN_REEF;
        }
        return center;
    }
}
