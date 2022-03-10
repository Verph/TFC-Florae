package tfcflorae.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import tfcflorae.objects.te.TEDryer;

public class TESRDryer extends TileEntitySpecialRenderer<TEDryer>
{
    @Override
    public void render(TEDryer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        if (te.hasWorld())
        {
            IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (cap != null)
            {
                double magic = 0.003125D;
                GlStateManager.pushMatrix();
                GlStateManager.translate(x + 0.5, y + 0.075, z + 0.5);
                GlStateManager.scale(0.5f, 0.5f, 0.5f);
                GlStateManager.rotate(90f, 1f, 0f, 0f);

                ItemStack item = cap.getStackInSlot(0);
                if (!item.isEmpty())
                {
                    Minecraft.getMinecraft().getRenderItem().renderItem(item, ItemCameraTransforms.TransformType.FIXED);
                }
                ItemStack item2 = cap.getStackInSlot(1);
                if (!item2.isEmpty())
                {
                    GlStateManager.translate(0, 0, -1);
                    Minecraft.getMinecraft().getRenderItem().renderItem(item2, ItemCameraTransforms.TransformType.FIXED);
                }
                GlStateManager.popMatrix();
            }
        }
    }
}
