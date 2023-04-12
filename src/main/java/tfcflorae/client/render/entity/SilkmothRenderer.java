package tfcflorae.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import tfcflorae.client.model.entity.SilkmothModel;
import tfcflorae.common.entities.Silkmoth;
import tfcflorae.util.TFCFHelpers;
import tfcflorae.client.TFCFRenderHelpers;

public class SilkmothRenderer extends TFCFSimpleMobRenderer<Silkmoth, SilkmothModel>
{
	public SilkmothRenderer(EntityRendererProvider.Context ctx)
    {
        super(ctx, new SilkmothModel(TFCFRenderHelpers.bakeSimple(ctx, "silk_moth")), "silk_moth", 0.4f, false, 0.8f, false, false, null);
	}

    @Override
    public ResourceLocation getTextureLocation(Silkmoth entity)
    {
        return TFCFHelpers.identifier("textures/entity/animals/silk_moth.png");
    }
}