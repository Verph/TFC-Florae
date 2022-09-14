package tfcflorae.world.layer;

import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.SourceLayer;
import net.dries007.tfc.world.noise.Noise2D;
import tfcflorae.mixin.world.layer.TFCLayersMixin;

public class ForestInitLayer implements SourceLayer
{
    private final Noise2D forestBaseNoise;

    ForestInitLayer(Noise2D forestBaseNoise)
    {
        this.forestBaseNoise = forestBaseNoise;
    }

    @Override
    public int apply(AreaContext context, int x, int z)
    {
        final float noise = forestBaseNoise.noise(x, z);
        if (noise < 0)
        {
            return TFCLayersMixin.FOREST_NONE;
        }
        else
        {
            return TFCLayersMixin.FOREST_NORMAL;
        }
    }
}
