package tfcflorae.world.layer;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import net.dries007.tfc.world.layer.TFCLayers;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum OceanBorderLayerTFCF implements AdjacentTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int PELAGIC_ZONE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPelagicZone();
    static final int SEAMOUNTS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSeamounts();
    static final int GUYOTS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGuyots();
    static final int NEAR_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticNearShore();
    static final int ATOLL = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAtoll();
    static final int LAGOON = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticLagoon();
    static final int BARRIER_REEF = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBarrierReef();
    static final int SHORE_DUNES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShoreDunes();

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (center == DEEP_OCEAN)
        {
            // Add ocean to land - deep ocean borders
            if (matcher.test(i -> !TFCLayers.isOceanOrMarker(i)) || isShallowWaters(matcher))
            {
                return OCEAN;
            }
        }
        /*else if (center == OCEAN)
        {
            // And in the reverse, in large sections of ocean, add deep ocean in fully ocean-locked area
            if (matcher.test(TFCLayers::isOceanOrMarker))
            {
                return DEEP_OCEAN;
            }
        }*/
        else if (center == PELAGIC_ZONE || center == SEAMOUNTS || center == GUYOTS)
        {
            if (!isShallowWaters(matcher) && matcher.test(TFCLayers::isOceanOrMarker))
            {
                return DEEP_OCEAN_TRENCH;
            }
            else if (!matcher.test(TFCLayers::isOceanOrMarker) || isShallowWaters(matcher))
            {
                return OCEAN;
            }
        }
        return center;
    }

    public boolean isShallowWaters(Predicate<IntPredicate> matcher)
    {
        return matcher.test(i -> i == OCEAN) || matcher.test(i -> i == OCEAN_REEF) || matcher.test(i -> i == NEAR_SHORE) || matcher.test(i -> i == ATOLL) || matcher.test(i -> i == LAGOON) || matcher.test(i -> i == BARRIER_REEF);
    }
}