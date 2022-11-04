package tfcflorae.client.render.blockentity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;

import tfcflorae.common.blockentities.*;
import tfcflorae.common.blocks.wood.*;
import tfcflorae.util.TFCFHelpers;

import com.mojang.blaze3d.vertex.PoseStack;

public class TFCFChestBlockEntityRenderer extends ChestRenderer<TFCFChestBlockEntity>
{
    private static String getFolder(BlockEntity blockEntity, ChestType type)
    {
        final String prefix = blockEntity instanceof TFCFTrappedChestBlockEntity ? "trapped" : "normal";
        return chooseForType(type, prefix, prefix + "_left", prefix + "_right");
    }

    private static String chooseForType(ChestType type, String single, String left, String right)
    {
        return switch (type)
            {
                case LEFT -> left;
                case RIGHT -> right;
                case SINGLE -> single;
            };
    }
    private String wood;

    public TFCFChestBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        super(context);
        wood = "oak";
    }

    @Override
    public void render(TFCFChestBlockEntity be, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay)
    {
        if (be.getBlockState().getBlock() instanceof TFCFChestBlock chestBlock)
        {
            wood = chestBlock.getTextureLocation();
        }
        super.render(be, partialTicks, poseStack, buffer, combinedLight, combinedOverlay);
    }

    @Override
    protected Material getMaterial(TFCFChestBlockEntity blockEntity, ChestType chestType)
    {
        return new Material(Sheets.CHEST_SHEET, TFCFHelpers.identifier("entity/chest/" + getFolder(blockEntity, chestType) + "/" + wood));
    }
}
