package tfcflorae.client.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.client.button.IButtonTooltip;
import net.dries007.tfc.client.gui.GuiContainerTE;
import net.dries007.tfc.network.PacketGuiButton;

import tfcflorae.client.gui.button.GuiButtonCrate;
import tfcflorae.objects.te.TECrate;

import static tfcflorae.TFCFlorae.MODID;

public class GuiCrate extends GuiContainerTE<TECrate>
{
    public static final ResourceLocation CRATE_BACKGROUND = new ResourceLocation(MODID, "textures/gui/crate.png");
    private final String translationKey;

    public GuiCrate(Container container, InventoryPlayer playerInv, TECrate tile, String translationKey)
    {
        super(container, playerInv, tile, CRATE_BACKGROUND);

        this.translationKey = translationKey;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        addButton(new GuiButtonCrate(tile, 0, guiTop, guiLeft));
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY)
    {
        super.renderHoveredToolTip(mouseX, mouseY);

        // Button Tooltips
        for (GuiButton button : buttonList)
        {
            if (button instanceof IButtonTooltip && button.isMouseOver())
            {
                IButtonTooltip tooltip = (IButtonTooltip) button;
                if (tooltip.hasTooltip())
                {
                    drawHoveringText(I18n.format(tooltip.getTooltip()), mouseX, mouseY);
                }
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String name = I18n.format(translationKey + ".name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);

        if (tile.isSealed())
        {
            // Draw over the input items, making them look unavailable
            IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (handler != null)
            {
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                for (int slotId = 0; slotId < handler.getSlots(); slotId++)
                {
                    drawSlotOverlay(inventorySlots.getSlot(slotId));
                }
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }

            // Draw the text displaying both the seal date, and the recipe name
            fontRenderer.drawString(tile.getSealedDate(), xSize / 2 - fontRenderer.getStringWidth(tile.getSealedDate()) / 2, 74, 0x404040);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button instanceof GuiButtonCrate)
        {
            TerraFirmaCraft.getNetwork().sendToServer(new PacketGuiButton(button.id));
        }
        super.actionPerformed(button);
    }
}