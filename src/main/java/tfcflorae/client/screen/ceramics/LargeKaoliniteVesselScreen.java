package tfcflorae.client.screen.ceramics;

import java.util.function.Consumer;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.client.screen.BlockEntityScreen;
import net.dries007.tfc.util.Helpers;

import tfcflorae.client.screen.button.KaoliniteVesselSealButton;
import tfcflorae.common.blockentities.ceramics.LargeKaoliniteVesselBlockEntity;
import tfcflorae.common.blocks.ceramics.LargeKaoliniteVesselBlock;
import tfcflorae.common.container.ceramics.LargeKaoliniteVesselContainer;

public class LargeKaoliniteVesselScreen extends BlockEntityScreen<LargeKaoliniteVesselBlockEntity, LargeKaoliniteVesselContainer>
{
    private static final Component SEAL = new TranslatableComponent(TerraFirmaCraft.MOD_ID + ".tooltip.seal_barrel");
    private static final Component UNSEAL = new TranslatableComponent(TerraFirmaCraft.MOD_ID + ".tooltip.unseal_barrel");
    public static final ResourceLocation BACKGROUND = Helpers.identifier("textures/gui/large_vessel.png");

    public LargeKaoliniteVesselScreen(LargeKaoliniteVesselContainer container, Inventory playerInventory, Component name)
    {
        super(container, playerInventory, name, BACKGROUND);
    }

    @Override
    public void init()
    {
        super.init();
        addRenderableWidget(new KaoliniteVesselSealButton(blockEntity, getGuiLeft() + 9, getGuiTop(), new Button.OnTooltip()
        {
            @Override
            public void onTooltip(Button button, PoseStack poseStack, int x, int y)
            {
                renderTooltip(poseStack, isSealed() ? UNSEAL : SEAL, x, y);
            }

            @Override
            public void narrateTooltip(Consumer<Component> consumer)
            {
                consumer.accept(isSealed() ? UNSEAL : SEAL);
            }
        }));
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY)
    {
        super.renderLabels(poseStack, mouseX, mouseY);
        if (isSealed())
        {
            drawDisabled(poseStack, 0, LargeKaoliniteVesselBlockEntity.SLOTS - 1);
        }
    }

    private boolean isSealed()
    {
        return blockEntity.getBlockState().getValue(LargeKaoliniteVesselBlock.SEALED);
    }
}
