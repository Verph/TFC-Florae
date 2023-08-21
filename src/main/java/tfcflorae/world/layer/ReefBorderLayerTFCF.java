package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;
import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.TFCLayers;

/**
 * Operates on the {@link TFCLayers#OCEAN_REEF_MARKER} markers
 * Borders reef - land with ocean, and adds ocean to reef - deep ocean borders
 */
public enum ReefBorderLayerTFCF implements AdjacentTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int SHORE_DUNES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShoreDunes();

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (center == OCEAN_REEF_MARKER)
        {
            if (matcher.test(i -> !isOceanOrMarker(i)))
            {
                return OCEAN;
            }
            return OCEAN_REEF;
        }
        else if (center == OCEANIC_MOUNTAINS)
        {
            if (matcher.test(i -> i != OCEANIC_MOUNTAINS))
            {
                return SHORE_DUNES;
            }
        }
        else if (center == VOLCANIC_OCEANIC_MOUNTAINS)
        {
            if (matcher.test(i -> i != VOLCANIC_OCEANIC_MOUNTAINS))
            {
                return SHORE_DUNES;
            }
        }
        else if (isOceanOrMarker(center) && matcher.test(i -> i == OCEAN_REEF_MARKER))
        {
            return OCEAN;
        }
        return center;
    }
}
