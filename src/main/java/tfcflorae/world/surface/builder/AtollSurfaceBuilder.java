package tfcflorae.world.surface.builder;

import net.dries007.tfc.world.TFCChunkGenerator;
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;
import net.dries007.tfc.world.surface.SurfaceBuilderContext;
import net.dries007.tfc.world.surface.SurfaceState;
import net.dries007.tfc.world.surface.SurfaceStates;
import net.dries007.tfc.world.surface.builder.NormalSurfaceBuilder;
import net.dries007.tfc.world.surface.builder.SurfaceBuilder;
import net.dries007.tfc.world.surface.builder.SurfaceBuilderFactory;

public class AtollSurfaceBuilder implements SurfaceBuilder
{
    public static SurfaceBuilderFactory create(SurfaceBuilderFactory parent)
    {
        return seed -> new AtollSurfaceBuilder(parent.apply(seed), seed);
    }

    private final SurfaceBuilder parent;
    private final Noise2D variantNoise;

    public AtollSurfaceBuilder(SurfaceBuilder parent, long seed)
    {
        this.parent = parent;
        this.variantNoise = new OpenSimplex2D(seed).octaves(2).spread(0.003f).abs();
    }

    @Override
    public void buildSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        parent.buildSurface(context, startY, endY);
        if (startY <= TFCChunkGenerator.SEA_LEVEL_Y + 1 + (int) Math.abs(context.random().nextGaussian()))
        {
            buildAtollSurface(context, startY, endY);
        }
    }

    private void buildAtollSurface(SurfaceBuilderContext context, int startY, int endY)
    {
        float variantNoiseValue = variantNoise.noise(context.pos().getX(), context.pos().getZ());
        if (variantNoiseValue > 0.6f)
        {
            NormalSurfaceBuilder.INSTANCE.buildSurface(context, startY, endY, SurfaceStates.RARE_SHORE_SAND, SurfaceStates.RARE_SHORE_SAND, SurfaceStates.RARE_SHORE_SANDSTONE);
        }
        else
        {
            SurfaceState top = context.rainfall() > 400 && variantNoiseValue > 0.4f ? SurfaceStates.SHORE_MUD : SurfaceStates.SHORE_SAND;
            NormalSurfaceBuilder.INSTANCE.buildSurface(context, startY, endY, top, top, SurfaceStates.SHORE_SANDSTONE);
        }
    }
}