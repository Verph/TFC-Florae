package tfcflorae.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import tfcflorae.client.model.entity.SilkmothModel;
import tfcflorae.common.entities.Silkmoth;
import tfcflorae.util.TFCFHelpers;
import tfcflorae.client.TFCFRenderHelpers;

public class SilkmothRenderer extends MobRenderer<Silkmoth, SilkmothModel>
{
	public SilkmothRenderer(EntityRendererProvider.Context ctx)
    {
		super(ctx, new SilkmothModel(ctx.bakeLayer(TFCFRenderHelpers.modelIdentifier("silk_moth"))), 0.5f);
	}

    @Override
    public ResourceLocation getTextureLocation(Silkmoth entity)
    {
        return TFCFHelpers.identifier("textures/entity/silk_moth.png");
    }
}