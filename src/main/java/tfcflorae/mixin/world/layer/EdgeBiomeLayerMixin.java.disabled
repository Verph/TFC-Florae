package tfcflorae.mixin.world.layer;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import static tfcflorae.mixin.world.layer.TFCLayersMixin.*;

public enum EdgeBiomeLayerMixin implements AdjacentTransformLayer
{
    INSTANCE;

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (center == PLATEAU || center == BADLANDS || center == INVERTED_BADLANDS)
        {
            if (matcher.test(i -> i == LOW_CANYONS || i == LOWLANDS || i == WETLANDS || i == MARSHES || i == SWAMPS))
            {
                return HILLS;
            }
            else if (matcher.test(i -> i == PLAINS || i == HILLS || i == GRASSLANDS))
            {
                return ROLLING_HILLS;
            }
        }
        else if (TFCLayersMixin.isMountains(center))
        {
            if (matcher.test(TFCLayersMixin::isLow))
            {
                return ROLLING_HILLS;
            }
        }
        // Inverses of above conditions
        else if (center == LOWLANDS || center == LOW_CANYONS || center == WETLANDS || center == MARSHES || center == SWAMPS)
        {
            if (matcher.test(i -> i == PLATEAU || i == BADLANDS || i == INVERTED_BADLANDS))
            {
                return HILLS;
            }
            else if (matcher.test(TFCLayersMixin::isMountains))
            {
                return ROLLING_HILLS;
            }
        }
        else if (center == PLAINS || center == HILLS || center == GRASSLANDS)
        {
            if (matcher.test(i -> i == PLATEAU || i == BADLANDS || i == INVERTED_BADLANDS))
            {
                return HILLS;
            }
            else if (matcher.test(TFCLayersMixin::isMountains))
            {
                return ROLLING_HILLS;
            }
        }
        else if (center == DEEP_OCEAN_TRENCH)
        {
            if (matcher.test(i -> !TFCLayersMixin.isOcean(i)))
            {
                return OCEAN;
            }
        }
        return center;
    }
}