package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * Creates oceans on borders between land and deep ocean
 */
public enum OceanBorderLayerTFCF implements AdjacentTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int PELAGIC_ZONE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPelagicZone();
    static final int SEAMOUNTS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSeamounts();
    static final int GUYOTS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGuyots();
    static final int SHORE_DUNES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShoreDunes();

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (center == PELAGIC_ZONE)
        {
            if (matcher.test(i -> i == SEAMOUNTS))
            {
                return SEAMOUNTS;
            }
            else if (matcher.test(i -> i == GUYOTS))
            {
                return GUYOTS;
            }
            else if (matcher.test(i -> i == DEEP_OCEAN))
            {
                return DEEP_OCEAN_TRENCH;
            }
            else if (matcher.test(i -> i == OCEAN))
            {
                return DEEP_OCEAN;
            }
            else if (matcher.test(i -> !isOceanOrMarker(i)))
            {
                return DEEP_OCEAN;
            }
        }
        else if (center == GUYOTS)
        {
            if (matcher.test(i -> i != GUYOTS))
            {
                return DEEP_OCEAN_TRENCH;
            }
        }
        else if (center == SEAMOUNTS)
        {
            if (matcher.test(i -> i != SEAMOUNTS))
            {
                return DEEP_OCEAN_TRENCH;
            }
        }
        else if (center == DEEP_OCEAN)
        {
            // Add ocean to land - deep ocean borders
            if (matcher.test(TFCLayers::isOceanOrMarker))
            {
                return PELAGIC_ZONE;
            }
            else if (matcher.test(i -> !TFCLayers.isOceanOrMarker(i)))
            {
                return OCEAN;
            }
        }
        else if (center == OCEAN)
        {
            // And in the reverse, in large sections of ocean, add deep ocean in fully ocean-locked area
            if (matcher.test(TFCLayers::isOceanOrMarker))
            {
                return DEEP_OCEAN;
            }
        }
        return center;
    }
}