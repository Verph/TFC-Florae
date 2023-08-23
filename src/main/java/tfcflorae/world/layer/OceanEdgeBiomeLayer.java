package tfcflorae.world.layer;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum OceanEdgeBiomeLayer implements AdjacentTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int GRAVEL_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGravelShores();
    static final int SHRUBLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShrublands();
    static final int NEAR_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticNearShore();
    static final int SHORE_DUNES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShoreDunes();

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (TFCLayers.isOcean(center))
        {
            if (matcher.test(TFCLayers::isMountains))
            {
                return OLD_MOUNTAINS;
            }
            if (matcher.test(i -> i == OLD_MOUNTAINS))
            {
                return SHRUBLANDS;
            }
            if (matcher.test(i -> i == SHRUBLANDS))
            {
                return SHORE_DUNES;
            }
            if (matcher.test(i -> i == SHORE_DUNES))
            {
                return GRAVEL_SHORE;
            }
            if (matcher.test(i -> i == GRAVEL_SHORE))
            {
                return NEAR_SHORE;
            }
            if (matcher.test(i -> i == NEAR_SHORE))
            {
                return OCEAN;
            }
        }
        return center;
    }
}