package tfcflorae.client.render.entity;

import java.util.function.Function;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

import net.dries007.tfc.client.render.entity.SimpleMobRenderer;

import org.jetbrains.annotations.Nullable;

import tfcflorae.client.TFCFRenderHelpers;
import tfcflorae.util.TFCFHelpers;

public class TFCFSimpleMobRenderer<T extends Mob, M extends EntityModel<T>> extends SimpleMobRenderer<T, M>
{
    private final ResourceLocation texture;
    @Nullable
    private final ResourceLocation babyTexture;
    @Nullable
    private final Function<T, ResourceLocation> textureGetter;
    private final boolean doesFlop;
    private final float scale;

    public TFCFSimpleMobRenderer(EntityRendererProvider.Context ctx, M model, String name, float shadow, boolean flop, float scale, boolean hasBabyTexture, boolean itemInMouth, @Nullable Function<T, ResourceLocation> textureGetter)
    {
        super(ctx, model, name, shadow, itemInMouth, scale, itemInMouth, itemInMouth, textureGetter);
        doesFlop = flop;
        texture = TFCFHelpers.animalTexture(name);
        babyTexture = hasBabyTexture ? TFCFHelpers.animalTexture(name + "_young") : null;
        this.textureGetter = textureGetter != null ? textureGetter : e -> babyTexture != null && e.isBaby() ? babyTexture : texture;
        this.scale = scale;
    }

    public static class Builder<T extends Mob, M extends EntityModel<T>>
    {
        private final EntityRendererProvider.Context ctx;
        private final Function<ModelPart, M> model;
        private final String name;

        private float shadow = 0.3f;
        private boolean flop = false;
        private float scale = 1f;
        private boolean hasBabyTexture = false;
        private boolean itemInMouth = false;
        @Nullable private Function<T, ResourceLocation> textureGetter = null;

        public Builder(EntityRendererProvider.Context ctx, Function<ModelPart, M> model, String name)
        {
            this.ctx = ctx;
            this.model = model;
            this.name = name;
        }

        public Builder<T, M> flops()
        {
            this.flop = true;
            return this;
        }

        public Builder<T, M> shadow(float size)
        {
            this.shadow = size;
            return this;
        }

        public Builder<T, M> scale(float scale)
        {
            this.scale = scale;
            return this;
        }

        public Builder<T, M> hasBabyTexture()
        {
            this.hasBabyTexture = true;
            return this;
        }

        public Builder<T, M> mouthy()
        {
            this.itemInMouth = true;
            return this;
        }

        public Builder<T, M> texture(Function<T, ResourceLocation> getter)
        {
            this.textureGetter = getter;
            return this;
        }

        public SimpleMobRenderer<T, M> build()
        {
            return new SimpleMobRenderer<>(ctx, model.apply(TFCFRenderHelpers.bakeSimple(ctx, name)), name, shadow, flop, scale, hasBabyTexture, itemInMouth, textureGetter);
        }
    }
}
