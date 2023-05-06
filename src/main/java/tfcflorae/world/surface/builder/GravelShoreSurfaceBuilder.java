package tfcflorae.world.surface.builder;

import net.dries007.tfc.world.surface.*;
import net.dries007.tfc.world.surface.builder.*;

public class GravelShoreSurfaceBuilder implements SurfaceBuilder
{
    public static final SurfaceBuilderFactory INSTANCE = GravelShoreSurfaceBuilder::new;

    public GravelShoreSurfaceBuilder(long seed)
    {}

    @Override
    public void buildSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        final NormalSurfaceBuilder surfaceBuilder = NormalSurfaceBuilder.INSTANCE;
        surfaceBuilder.buildSurface(context, startY, endY, SurfaceStates.GRAVEL, SurfaceStates.GRAVEL, SurfaceStates.SHORE_SANDSTONE);
    }
}