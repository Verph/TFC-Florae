package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.Area;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.TransformLayer;
import net.dries007.tfc.world.river.MidpointFractal;

import tfcflorae.interfaces.TFCLayersMixinInterface;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public class MergeShoreDunesLayer implements TransformLayer
{
    private final ShoreDunes.Context cliff;
    public static TFCLayers staticBiomes = new TFCLayers();

    static final int GRAVEL_SHORE = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticGravelShores();
    static final int SHORE_DUNES = ((TFCLayersMixinInterface) (Object) staticBiomes).getStaticShoreDunes();

    public MergeShoreDunesLayer(ShoreDunes.Context cliff)
    {
        this.cliff = cliff;
    }

    @Override
    public int apply(AreaContext context, Area area, int x, int z)
    {
        final int value = area.get(x, z);
        if (value == SHORE)
        {
            final float scale = 1f / (1 << 7);
            final float x0 = x * scale, z0 = z * scale;
            for (MidpointFractal fractal : cliff.getFractalsByPartition(x, z))
            {
                // maybeIntersect will skip the more expensive calculation if it fails
                if (fractal.maybeIntersect(x0, z0, ShoreDunes.SHORE_DUNES_WIDTH) && fractal.intersect(x0, z0, ShoreDunes.SHORE_DUNES_WIDTH))
                {
                    return SHORE_DUNES;
                }
            }
        }
        return value;
    }
}