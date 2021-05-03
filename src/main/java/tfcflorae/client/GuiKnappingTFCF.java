package tfcflorae.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.client.gui.GuiKnapping;

import tfcflorae.api.knapping.KnappingTypes;

public class GuiKnappingTFCF extends GuiKnapping
{
    private KnappingType type = null;
    private ResourceLocation backgroundTexture;

    public GuiKnappingTFCF(Container container, EntityPlayer player, KnappingType type, ResourceLocation buttonTexture)
    {
        super(container, player, type, buttonTexture);
        this.type = type;
    }

    public GuiKnappingTFCF(Container container, EntityPlayer player, KnappingType type, ResourceLocation buttonTexture, ResourceLocation backgroundTexture)
    {
        super(container, player, type, buttonTexture);
        this.type = type;
        this.backgroundTexture = backgroundTexture;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        if (type == KnappingTypes.MUD || type == KnappingTypes.EARTHENWARE_CLAY || type == KnappingTypes.KAOLINITE_CLAY || type == KnappingTypes.STONEWARE_CLAY)
        {
            GlStateManager.color(1, 1, 1, 1);
            if(type == KnappingTypes.MUD) mc.getTextureManager().bindTexture(backgroundTexture);
            else if(type == KnappingTypes.EARTHENWARE_CLAY) mc.getTextureManager().bindTexture(GuiHandler.EARTHENWARE_CLAY_DISABLED_TEXTURE);
            else if(type == KnappingTypes.KAOLINITE_CLAY) mc.getTextureManager().bindTexture(GuiHandler.KAOLINITE_CLAY_DISABLED_TEXTURE);
            else if(type == KnappingTypes.STONEWARE_CLAY) mc.getTextureManager().bindTexture(GuiHandler.STONEWARE_CLAY_DISABLED_TEXTURE);
            for (GuiButton button : buttonList)
            {
                if (!button.visible)
                {
                    Gui.drawModalRectWithCustomSizedTexture(button.x, button.y, 0, 0, 16, 16, 16, 16);
                }
            }
        }
    }
}