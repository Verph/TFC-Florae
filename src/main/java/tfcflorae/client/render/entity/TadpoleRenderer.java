package tfcflorae.client.render.entity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import tfcflorae.TFCFlorae;
import tfcflorae.client.model.entity.TadpoleModel;
import tfcflorae.common.entities.Tadpole;

public class TadpoleRenderer extends MobRenderer<Tadpole, TadpoleModel<Tadpole>>
{
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation(TFCFlorae.MOD_ID, "tadpole"), "main");

    private static final ResourceLocation TEXTURE = new ResourceLocation(TFCFlorae.MOD_ID, "textures/entity/animal/tadpole/tadpole.png");

    public TadpoleRenderer(EntityRendererProvider.Context context)
    {
        super(context, new TadpoleModel<>(context.bakeLayer(MODEL_LAYER)), 0.14F);
    }

    @Override
    public ResourceLocation getTextureLocation(Tadpole entity)
    {
        return TEXTURE;
    }
}