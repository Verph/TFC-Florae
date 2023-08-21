package tfcflorae.client.render.entity;

import java.util.Map;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import tfcflorae.TFCFlorae;
import tfcflorae.client.model.entity.SpecialParrotModel;
import tfcflorae.common.entities.TFCFParrot;

public class SpecialParrotRenderer extends TFCFSimpleMobRenderer<TFCFParrot, SpecialParrotModel>
{
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation(TFCFlorae.MOD_ID, "parrot"), "main");

    private static final Map<TFCFParrot.Variant, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), hashMap -> {
        for (TFCFParrot.Variant variant : TFCFParrot.Variant.values())
        {
            hashMap.put(variant, new ResourceLocation(TFCFlorae.MOD_ID, String.format("textures/entity/animal/parrot/%s.png", variant.getName())));
        }
    });

    public SpecialParrotRenderer(EntityRendererProvider.Context context)
    {
        super(context, new SpecialParrotModel(context.bakeLayer(MODEL_LAYER)), "parrot", 0.3f, false, 1f, false, false, null);
    }

    @Override
    public ResourceLocation getTextureLocation(TFCFParrot parrot)
    {
        return TEXTURES.get(parrot.getParrotVariant());
    }

    public float getBob(TFCFParrot parrot, float delta)
    {
        float f = Mth.lerp(delta, parrot.oFlap, parrot.flap);
        float f1 = Mth.lerp(delta, parrot.oFlapSpeed, parrot.flapSpeed);
        return (Mth.sin(f) + 1.0F) * f1;
    }
}