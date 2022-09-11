package tfcflorae.mixin.world.layer;

import org.spongepowered.asm.mixin.*;

import net.dries007.tfc.world.layer.ForestInitLayer;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.SourceLayer;
import net.dries007.tfc.world.noise.Noise2D;

@Mixin(ForestInitLayer.class)
public class ForestInitLayerMixin implements SourceLayer
{
    @Shadow
    private final Noise2D forestBaseNoise;

    ForestInitLayerMixin(Noise2D forestBaseNoise)
    {
        this.forestBaseNoise = forestBaseNoise;
    }

    @Shadow @Mutable @Override
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
