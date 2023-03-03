package tfcflorae.world.layer;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import tfcflorae.interfaces.TFCLayersMixinInterface;

public enum ExtraDunesShoreLayer implements AdjacentTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();
    static final int SHORE_DUNES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShoreDunes();
    static final int COASTAL_CLIFFS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCoastalCliffs();
    static final int GRAVEL_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGravelShores();

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (!TFCLayers.isOcean(center) && (center == SHORE_DUNES || center == COASTAL_CLIFFS))
        {
            if (matcher.test(TFCLayers::isOcean))
            {
                return TFCLayers.shoreFor(center);
            }
        }
        else if (!TFCLayers.isOcean(center) && center == GRAVEL_SHORE)
        {
            if (matcher.test(TFCLayers::isOcean))
            {
                return GRAVEL_SHORE;
            }
        }
        return center;
    }
}