package tfcflorae.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cod;

import tfcflorae.util.TFCFHelpers;

public class CodLikeRenderer extends CodRenderer
{
    private final ResourceLocation texture;

    public CodLikeRenderer(EntityRendererProvider.Context context, String name)
    {
        super(context);
        texture = TFCFHelpers.animalTexture(name);
    }

    @Override
    public ResourceLocation getTextureLocation(Cod cod)
    {
        return texture;
    }
}
