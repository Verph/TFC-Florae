package tfcflorae.world.layer;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.AdjacentTransformLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum PlateauCliffsLayer implements AdjacentTransformLayer
{
    INSTANCE;

    public static TFCLayers staticBiomes = new TFCLayers();

    static final int MESA_PLATEAU = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMesaPlateau();
    static final int PLATEAU_CLIFFS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPlateauCliffs();
    static final int MISTY_PEAKS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMistyPeaks();
    static final int FRACTURED_MISTY_PEAKS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticFracturedMistyPeaks();
    static final int RIVERBANK = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRiverbank();
    static final int RIVER_EDGE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRiverEdge();
    static final int LAKE_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticLakeShore();

    @Override
    public int apply(AreaContext context, int north, int east, int south, int west, int center)
    {
        Predicate<IntPredicate> matcher = p -> p.test(north) || p.test(east) || p.test(south) || p.test(west);
        if (center == PLATEAU || center == MESA_PLATEAU || center == MISTY_PEAKS || center == FRACTURED_MISTY_PEAKS)
        {
            if (matcher.test(i -> isLow(i) || i == RIVERBANK || i == RIVER_EDGE || i == LAKE_SHORE))
            {
                return PLATEAU_CLIFFS;
            }
        }
        return center;
    }
}