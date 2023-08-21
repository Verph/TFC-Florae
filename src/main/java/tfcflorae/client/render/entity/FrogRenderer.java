package tfcflorae.client.render.entity;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import tfcflorae.TFCFlorae;
import tfcflorae.client.model.entity.FrogModel;
import tfcflorae.common.entities.Frog;

import java.util.Map;

public class FrogRenderer extends TFCFSimpleMobRenderer<Frog, FrogModel<Frog>>
{
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation(TFCFlorae.MOD_ID, "frog"), "main");

    private static final Map<Frog.Variant, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), hashMap -> {
        for (Frog.Variant variant : Frog.Variant.values())
        {
            hashMap.put(variant, new ResourceLocation(TFCFlorae.MOD_ID, String.format("textures/entity/animal/frog/%s_frog.png", variant.getName())));
        }
    });

    public FrogRenderer(EntityRendererProvider.Context context)
    {
        super(context, new FrogModel<>(context.bakeLayer(MODEL_LAYER)), "frog", 0.3f, false, 0.8f, false, false, null);
    }

    @Override
    public ResourceLocation getTextureLocation(Frog frog)
    {
        return TEXTURES.get(frog.getVariant());
    }

    @Override
    protected void setupRotations(Frog frog, PoseStack stack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        float scale = frog.getVisualScale();
        super.setupRotations(frog, stack, ageInTicks, rotationYaw, partialTicks);
        stack.scale(scale, scale, scale);
    }
}