package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.Area;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.TransformLayer;
import net.dries007.tfc.world.river.MidpointFractal;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public class MergeRiverBanksLayer implements TransformLayer
{
    private final WatershedBank.Context watersheds;
    public static TFCLayers staticBiomes = new TFCLayers();

    static final int GRASSLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGrasslands();
    static final int WETLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticWetlands();
    static final int MARSHES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMarshes();
    static final int SWAMPS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticSwamps();
    static final int BRYCE_CANYONS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticBryceCanyons();
    static final int MESA_HILLS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMesaHills();
    static final int ALPINE_MOUNTAINS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineMountains();
    static final int ALPINE_HIGHLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticAlpineHighlands();
    static final int ROLLING_HIGHLANDS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticRollingHighlands();
    static final int CALDERAS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticCalderas();
    static final int THERMAL_CANYONS = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticThermalCanyons();
    static final int MESA_PLATEAU = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticMesaPlateau();
    static final int PEAT_BOG = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticPeatBog();
    static final int GRAVEL_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGravelShores();

    public MergeRiverBanksLayer(WatershedBank.Context watersheds)
    {
        this.watersheds = watersheds;
    }

    @Override
    public int apply(AreaContext context, Area area, int x, int z)
    {
        final int value = area.get(x, z);
        if (TFCLayers.hasRiver(value))
        {
            final float scale = 1f / (1 << 7);
            final float x0 = x * scale, z0 = z * scale;
            for (MidpointFractal fractal : watersheds.getFractalsByPartition(x, z))
            {
                // maybeIntersect will skip the more expensive calculation if it fails
                if (fractal.maybeIntersect(x0, z0, WatershedBank.RIVER_WIDTH) && fractal.intersect(x0, z0, WatershedBank.RIVER_WIDTH))
                {
                    if (TFCLayers.isLake(value))
                    {
                        return LAKE;
                    }
                    else if (value == ALPINE_MOUNTAINS)
                    {
                        return ALPINE_MOUNTAINS;
                    }
                    else if (value == MOUNTAINS)
                    {
                        return MOUNTAINS;
                    }
                    else if (value == VOLCANIC_MOUNTAINS)
                    {
                        return VOLCANIC_MOUNTAINS;
                    }
                    else if (value == OLD_MOUNTAINS)
                    {
                        return OLD_MOUNTAINS;
                    }
                    else if (value == OCEANIC_MOUNTAINS)
                    {
                        return OCEANIC_MOUNTAINS;
                    }
                    else if (value == VOLCANIC_OCEANIC_MOUNTAIN_RIVER)
                    {
                        return VOLCANIC_OCEANIC_MOUNTAIN_RIVER;
                    }
                    else if (value == SHORE)
                    {
                        return SHORE;
                    }
                    else if (value == GRAVEL_SHORE)
                    {
                        return GRAVEL_SHORE;
                    }
                    else if (TFCLayers.isOcean(value))
                    {
                        return OCEAN;
                    }
                    else
                    {
                        return GRASSLANDS;
                    }
                }
            }
        }
        return value;
    }
}