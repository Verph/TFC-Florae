package tfcflorae.client.render.entity;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import tfcflorae.client.TFCFRenderHelpers;
import tfcflorae.client.model.entity.RaftModel;
import tfcflorae.util.TFCFHelpers;

public class TFCFBoatRenderer extends BoatRenderer
{
    public static ModelLayerLocation boatName(String name)
    {
        return TFCFRenderHelpers.modelIdentifier("boat/" + name);
    }

    private final Pair<ResourceLocation, ListModel<Boat>> location;
    private final String name;
    private final boolean hasRaftModel;

    public TFCFBoatRenderer(EntityRendererProvider.Context context, String name, boolean hasRaftModel)
    {
        super(context);
        this.hasRaftModel = hasRaftModel;
        this.name = name;
        this.location = Pair.of(TFCFHelpers.identifier("textures/entity/boat/" + name + ".png"), hasRaftModel ? new RaftModel(context.bakeLayer(boatName(name))) : new BoatModel(context.bakeLayer(boatName(name))));
    }

    @Override
    public ResourceLocation getTextureLocation(Boat boat)
    {
        return getModelWithLocationNew(boat).getFirst();
    }

    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocationNew(Boat boat)
    {
        return location;
    }

    @Override
    public void render(Boat pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight)
    {
        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, 0.375D, 0.0D);
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - pEntityYaw));
        float f = (float)pEntity.getHurtTime() - pPartialTicks;
        float f1 = pEntity.getDamage() - pPartialTicks;
        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f > 0.0F)
        {
            pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)pEntity.getHurtDir()));
        }

        float f2 = pEntity.getBubbleAngle(pPartialTicks);
        if (!Mth.equal(f2, 0.0F))
        {
            pMatrixStack.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), pEntity.getBubbleAngle(pPartialTicks), true));
        }

        Pair<ResourceLocation, ListModel<Boat>> pair = getModelWithLocationNew(pEntity);
        ResourceLocation resourcelocation = pair.getFirst();
        ListModel<Boat> listmodel = pair.getSecond();
        pMatrixStack.scale(-1.0F, -1.0F, 1.0F);
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        listmodel.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(listmodel.renderType(resourcelocation));
        listmodel.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!pEntity.isUnderWater() && !hasRaftModel)
        {
            VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RenderType.waterMask());
            if (listmodel instanceof BoatModel)
            {
                BoatModel waterpatchmodel = (BoatModel)listmodel;
                if (waterpatchmodel.waterPatch() != null)
                {
                    waterpatchmodel.waterPatch().render(pMatrixStack, vertexconsumer1, pPackedLight, OverlayTexture.NO_OVERLAY);
                }
            }
        }

        pMatrixStack.popPose();

        RenderNameplateEvent renderNameplateEvent = new RenderNameplateEvent(pEntity, pEntity.getDisplayName(), this, pMatrixStack, pBuffer, pPackedLight, pPartialTicks);
        MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
        if (renderNameplateEvent.getResult() != Event.Result.DENY && (renderNameplateEvent.getResult() == Event.Result.ALLOW || this.shouldShowName(pEntity)))
        {
            this.renderNameTag(pEntity, renderNameplateEvent.getContent(), pMatrixStack, pBuffer, pPackedLight);
        }
        //super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
