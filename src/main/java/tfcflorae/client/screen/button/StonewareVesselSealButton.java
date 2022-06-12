package tfcflorae.client.screen.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.network.PacketDistributor;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.dries007.tfc.network.PacketHandler;
import net.dries007.tfc.network.ScreenButtonPacket;

import tfcflorae.client.screen.ceramics.LargeStonewareVesselScreen;
import tfcflorae.common.blockentities.ceramics.LargeStonewareVesselBlockEntity;
import tfcflorae.common.blocks.ceramics.LargeStonewareVesselBlock;

public class StonewareVesselSealButton extends Button
{
    private final LargeStonewareVesselBlockEntity vessel;

    public StonewareVesselSealButton(LargeStonewareVesselBlockEntity barrel, int guiLeft, int guiTop, OnTooltip onTooltip)
    {
        super(guiLeft + 123, guiTop + 35, 20, 20, TextComponent.EMPTY, b -> {}, onTooltip);
        this.vessel = barrel;
    }

    @Override
    public void onPress()
    {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), new ScreenButtonPacket(0, null));
        playDownSound(Minecraft.getInstance().getSoundManager());
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, LargeStonewareVesselScreen.BACKGROUND);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        final int v = vessel.getBlockState().getValue(LargeStonewareVesselBlock.SEALED) ? 0 : 20;
        blit(poseStack, x, y, 236, v, 20, 20, 256, 256);

        if (isHoveredOrFocused())
        {
            renderToolTip(poseStack, mouseX, mouseY);
        }
    }
}
