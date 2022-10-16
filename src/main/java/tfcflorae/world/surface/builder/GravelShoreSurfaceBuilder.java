package tfcflorae.world.surface.builder;

import net.dries007.tfc.world.noise.*;
import net.dries007.tfc.world.surface.*;
import net.dries007.tfc.world.surface.builder.*;

public class GravelShoreSurfaceBuilder implements SurfaceBuilder
{
    public static final SurfaceBuilderFactory INSTANCE = GravelShoreSurfaceBuilder::new;

    private final Noise2D variantNoise;

    public GravelShoreSurfaceBuilder(long seed)
    {
        this.variantNoise = new OpenSimplex2D(seed).octaves(2).spread(0.003f).abs();
    }

    @Override
    public void buildSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        final NormalSurfaceBuilder surfaceBuilder = NormalSurfaceBuilder.INSTANCE;
        surfaceBuilder.buildSurface(context, startY, endY, SurfaceStates.GRAVEL, SurfaceStates.GRAVEL, SurfaceStates.SHORE_SANDSTONE);
    }
}