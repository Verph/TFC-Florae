package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.Area;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.TransformLayer;
import net.dries007.tfc.world.river.MidpointFractal;
import net.dries007.tfc.world.river.Watershed;
import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public class MergeRiverBanksLayer implements TransformLayer
{
    private final WatershedBank.Context watersheds;
    public static TFCLayers staticBiomes = new TFCLayers();

    static final int CHASMS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticChasms();
    static final int RIVERBANK = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRiverbank();
    static final int RIVER_EDGE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRiverEdge();
    static final int WETLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticWetlands();
    static final int CALDERAS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCalderas();
    static final int ALPINE_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineMountains();
    static final int ALPINE_HIGHLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineHighlands();
    static final int CANYON_RIVER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCanyonRivers();
    static final int ALPINE_MOUNTAIN_RIVER = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineMountainRivers();

    public MergeRiverBanksLayer(WatershedBank.Context watersheds)
    {
        this.watersheds = watersheds;
    }

    @Override
    public int apply(AreaContext context, Area area, int x, int z)
    {
        final int value = area.get(x, z);
        if (!TFCLayers.isRiver(value) && TFCLayers.hasRiver(value) && !hasRiverCave(value))
        {
            final float scale = 1f / (1 << 7);
            final float x0 = x * scale, z0 = z * scale;
            for (MidpointFractal fractal : watersheds.getFractalsByPartition(x, z))
            {
                // maybeIntersect will skip the more expensive calculation if it fails
                if (fractal.maybeIntersect(x0, z0, WatershedBank.RIVER_WIDTH) && fractal.intersect(x0, z0, WatershedBank.RIVER_WIDTH))
                {
                    return RIVER_EDGE;
                }
            }
        }
        if (value == RIVER)
        {
            final float scale = 1f / (1 << 7);
            final float x0 = x * scale, z0 = z * scale;
            for (MidpointFractal fractal : watersheds.getFractalsByPartition(x, z))
            {
                // maybeIntersect will skip the more expensive calculation if it fails
                if (fractal.maybeIntersect(x0, z0, Watershed.RIVER_WIDTH) && fractal.intersect(x0, z0, Watershed.RIVER_WIDTH))
                {
                    return RIVER;
                }
            }
        }
        return value;
    }

    public static boolean hasRiverCave(int value)
    {
        return value == CALDERAS || value == ALPINE_MOUNTAINS || value == ALPINE_HIGHLANDS || value == MOUNTAINS || value == VOLCANIC_MOUNTAINS || value == OLD_MOUNTAINS || value == OCEANIC_MOUNTAINS || value == VOLCANIC_OCEANIC_MOUNTAINS || value == OCEANIC_MOUNTAIN_RIVER || value == OLD_MOUNTAIN_RIVER || value == MOUNTAIN_RIVER || value == VOLCANIC_OCEANIC_MOUNTAIN_RIVER || value == VOLCANIC_MOUNTAIN_RIVER || value == CANYON_RIVER || value == ALPINE_MOUNTAIN_RIVER;
    }
}