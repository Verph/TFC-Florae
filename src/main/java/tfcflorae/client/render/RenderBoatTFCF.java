package tfcflorae.client.render;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.client.model.IMultipassModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.api.types.Tree;

import tfcflorae.TFCFlorae;
import tfcflorae.objects.entity.EntityBoatTFCF;

import static tfcflorae.TFCFlorae.MODID;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderBoatTFCF extends Render<EntityBoatTFCF>
{
    private final ModelBase modelBoat = new ModelBoat();

    public RenderBoatTFCF(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.shadowSize = 0.5F;
    }

    @Override
    public void doRender(EntityBoatTFCF entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        this.setupTranslation(x, y, z);
        this.setupRotation(entity, entityYaw, partialTicks);
        this.bindEntityTexture(entity);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.modelBoat.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityBoatTFCF entity)
    {
        final IFruitTree wood = entity.getWood();
        if (wood != null)
        {
            //noinspection ConstantConditions
            return new ResourceLocation(MODID, "textures/entity/boat/" + wood.getName().toLowerCase() + ".png");
        }

        final Tree tree = entity.getTree();
        if (tree != null)
        {
            //noinspection ConstantConditions
            return new ResourceLocation(MODID, "textures/entity/boat/" + tree.getRegistryName().getPath().toLowerCase() + ".png");
        }

        // Fallback
        return new ResourceLocation(MODID, "textures/entity/boat/oak.png");
    }

    @Override
    public boolean isMultipass()
    {
        return true;
    }

    @Override
    public void renderMultipass(EntityBoatTFCF entityIn, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        this.setupTranslation(x, y, z);
        this.setupRotation(entityIn, entityYaw, partialTicks);
        this.bindEntityTexture(entityIn);
        ((IMultipassModel) this.modelBoat).renderMultipass(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
    }

    private void setupRotation(EntityBoat entityIn, float entityYaw, float partialTicks)
    {
        GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
        float f = (float) entityIn.getTimeSinceHit() - partialTicks;
        float f1 = entityIn.getDamageTaken() - partialTicks;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f > 0.0F)
        {
            GlStateManager.rotate(MathHelper.sin(f) * f * f1 / 10.0F * (float) entityIn.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }

        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
    }

    private void setupTranslation(double x, double y, double z)
    {
        GlStateManager.translate((float) x, (float) y + 0.375F, (float) z);
    }
}
