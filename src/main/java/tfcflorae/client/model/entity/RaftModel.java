package tfcflorae.client.model.entity;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;

public class RaftModel extends ListModel<Boat>
{
    private static final String LEFT_PADDLE = "left_paddle";
    private static final String RIGHT_PADDLE = "right_paddle";
    private static final String BOTTOM = "bottom";
    private final ModelPart leftPaddle;
    private final ModelPart rightPaddle;
    private final ImmutableList<ModelPart> parts;

    public RaftModel(ModelPart part)
    {
        this.leftPaddle = part.getChild("left_paddle");
        this.rightPaddle = part.getChild("right_paddle");
        this.parts = this.createPartsBuilder(part).build();
    }

    protected ImmutableList.Builder<ModelPart> createPartsBuilder(ModelPart part)
    {
        ImmutableList.Builder<ModelPart> builder = new ImmutableList.Builder<>();
        builder.add(part.getChild("bottom"), this.leftPaddle, this.rightPaddle);
        return builder;
    }

    public static void createChildren(PartDefinition definition)
    {
        definition.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -11.0F, -4.0F, 28.0F, 20.0F, 4.0F).texOffs(0, 0).addBox(-14.0F, -9.0F, -8.0F, 28.0F, 16.0F, 4.0F), PartPose.offsetAndRotation(0.0F, -2.0F, 1.0F, 1.5708F, 0.0F, 0.0F));
        int i = 20;
        int j = 7;
        int k = 6;
        float f = -5.0F;
        definition.addOrReplaceChild("left_paddle", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).addBox(-1.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), PartPose.offsetAndRotation(3.0F, -4.0F, 9.0F, 0.0F, 0.0F, 0.19634955F));
        definition.addOrReplaceChild("right_paddle", CubeListBuilder.create().texOffs(40, 24).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).addBox(0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), PartPose.offsetAndRotation(3.0F, -4.0F, -9.0F, 0.0F, (float)Math.PI, 0.19634955F));
    }

    public static LayerDefinition createBodyModel()
    {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        createChildren(partdefinition);
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(Boat boat, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        animatePaddle(boat, 0, this.leftPaddle, limbSwing);
        animatePaddle(boat, 1, this.rightPaddle, limbSwing);
    }

    @Override
    public ImmutableList<ModelPart> parts()
    {
        return this.parts;
    }

    /*public ModelPart waterPatch()
    {
        return this.waterPatch;
    }*/

    private static void animatePaddle(Boat boat, int side, ModelPart part, float limbSwing)
    {
        float f = boat.getRowingTime(side, limbSwing);
        part.xRot = Mth.clampedLerp((-(float)Math.PI / 3F), -0.2617994F, (Mth.sin(-f) + 1.0F) / 2.0F);
        part.yRot = Mth.clampedLerp((-(float)Math.PI / 4F), ((float)Math.PI / 4F), (Mth.sin(-f + 1.0F) + 1.0F) / 2.0F);
        if (side == 1)
        {
            part.yRot = (float)Math.PI - part.yRot;
        }
    }
}
