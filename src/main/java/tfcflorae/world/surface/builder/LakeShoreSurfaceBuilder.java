package tfcflorae.world.surface.builder;

import net.dries007.tfc.world.noise.*;
import net.dries007.tfc.world.surface.*;
import net.dries007.tfc.world.surface.builder.*;

public class LakeShoreSurfaceBuilder implements SurfaceBuilder
{
    public static final SurfaceBuilderFactory INSTANCE = LakeShoreSurfaceBuilder::new;

    private final Noise2D surfaceMaterialNoise;

    public LakeShoreSurfaceBuilder(long seed)
    {
        surfaceMaterialNoise = new OpenSimplex2D(seed).octaves(2).spread(0.04f);
    }

    @Override
    public void buildSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        final float noise = surfaceMaterialNoise.noise(context.pos().getX(), context.pos().getZ()) * 0.9f + context.random().nextFloat() * 0.1f;
        final NormalSurfaceBuilder surfaceBuilder = NormalSurfaceBuilder.INSTANCE;
        surfaceBuilder.buildSurface(context, startY, endY, noise < 0f ? SurfaceStates.GRAVEL : SurfaceStates.MUD, SurfaceStates.GRAVEL, SurfaceStates.SHORE_SANDSTONE);
    }
}