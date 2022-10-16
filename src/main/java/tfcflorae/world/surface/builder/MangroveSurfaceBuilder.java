package tfcflorae.world.surface.builder;

import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceState;
import net.dries007.tfc.world.surface.SurfaceStates;
import net.dries007.tfc.world.surface.builder.NormalSurfaceBuilder;
import net.dries007.tfc.world.surface.builder.SurfaceBuilder;
import net.dries007.tfc.world.surface.builder.SurfaceBuilderFactory;

public class MangroveSurfaceBuilder implements SurfaceBuilder
{
    public static SurfaceBuilderFactory create(SurfaceBuilderFactory parent)
    {
        return seed -> new MangroveSurfaceBuilder(parent.apply(seed), seed);
    }

    private final SurfaceBuilder parent;
    private final Noise2D surfaceMaterialNoise;

    public MangroveSurfaceBuilder(SurfaceBuilder parent, long seed)
    {
        this.parent = parent;
        surfaceMaterialNoise = new OpenSimplex2D(seed).octaves(2).spread(0.04f);
    }

    @Override
    public void buildSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        parent.buildSurface(context, startY, endY);
        buildMudSurface(context, startY, endY);
    }

    private void buildMudSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        final float noise = surfaceMaterialNoise.noise(context.pos().getX(), context.pos().getZ()) * 0.9f + context.random().nextFloat() * 0.1f;
        NormalSurfaceBuilder.INSTANCE.buildSurface(context, startY, endY, noise < 0f ? SurfaceStates.GRASS : SurfaceStates.MUD, SurfaceStates.GRAVEL, SurfaceStates.SANDSTONE_OR_GRAVEL);
    }
}
