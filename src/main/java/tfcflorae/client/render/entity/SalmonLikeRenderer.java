package tfcflorae.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SalmonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Salmon;

import tfcflorae.util.TFCFHelpers;

public class SalmonLikeRenderer extends SalmonRenderer
{
    private final ResourceLocation texture;

    public SalmonLikeRenderer(EntityRendererProvider.Context context, String name)
    {
        super(context);
        texture = TFCFHelpers.animalTexture(name);
    }

    @Override
    public ResourceLocation getTextureLocation(Salmon salmon)
    {
        return texture;
    }
}
