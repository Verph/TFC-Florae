package tfcflorae.world.layer;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum EdgeDunesShoreLayer implements AdjacentTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int GRAVEL_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGravelShores();
    static final int SHORE_DUNES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShoreDunes();
    static final int COASTAL_CLIFFS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCoastalCliffs();

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (center == SHORE_DUNES)
        {
            if (matcher.test(i -> !TFCLayers.isRiver(i) && (TFCLayers.hasRiver(i) || TFCLayers.isLow(i))))
            {
                return SHORE_DUNES;
            }
            return SHORE;
        }
        else if (TFCLayers.isMountains(center) || center == COASTAL_CLIFFS)
        {
            if (matcher.test(TFCLayers::isOceanOrMarker))
            {
                return GRAVEL_SHORE;
            }
        }
        return center;
    }
}