package tfcflorae.client.render.blockentity;

import java.util.Locale;
import java.util.Random;
import java.util.function.Function;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.dries007.tfc.common.blocks.DirectionPropertyBlock;
import net.dries007.tfc.util.Helpers;

import tfcflorae.TFCFlorae;
import tfcflorae.client.TFCFRenderHelpers;
import tfcflorae.common.blockentities.MineralSheetBlockEntity;
import tfcflorae.common.blocks.rock.Mineral;
import tfcflorae.common.blocks.rock.MineralSheetBlock;
import tfcflorae.util.TFCFHelpers;

public class MineralSheetBlockEntityRenderer implements BlockEntityRenderer<MineralSheetBlockEntity>
{
    @Override
    public void render(MineralSheetBlockEntity pile, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay)
    {
        final BlockState state = pile.getBlockState();
        if (state.getBlock() instanceof DirectionPropertyBlock)
        {
            final Function<ResourceLocation, TextureAtlasSprite> textureAtlas = Minecraft.getInstance().getTextureAtlas(TFCFRenderHelpers.BLOCKS_ATLAS);
            final VertexConsumer builder = buffer.getBuffer(RenderType.cutout());

            for (Direction direction : Helpers.DIRECTIONS)
            {
                if (state.getValue(DirectionPropertyBlock.getProperty(direction))) // The properties are authoritative on which sides should be rendered
                {
                    Random random = TFCFHelpers.RANDOM;
                    Mineral mineral = pile.getOrCacheMineral(direction);
                    String mineralName = "block/mineral/" + mineral.name().toLowerCase(Locale.ROOT) + "_" + Mth.clamp(random.nextInt(directionInt(direction)), 0, 3);

                    /*TFCFlorae.LOGGER.debug(mineral.name().toLowerCase(Locale.ROOT));
                    TFCFlorae.LOGGER.debug(mineralName);
                    TFCFlorae.LOGGER.debug(TFCFHelpers.identifier(mineralName).toString());*/

                    final ResourceLocation textureId = TFCFHelpers.identifier(mineralName);
                    final TextureAtlasSprite sprite = textureAtlas.apply(textureId);

                    renderSheet(poseStack, sprite, builder, direction, packedLight, packedOverlay);
                }
            }
        }
    }

    public void renderSheet(PoseStack poseStack, TextureAtlasSprite sprite, VertexConsumer buffer, Direction direction, int packedLight, int packedOverlay)
    {
        TFCFRenderHelpers.renderTexturedCuboid(poseStack, buffer, sprite, packedLight, packedOverlay, MineralSheetBlock.getShapeForSingleFace(direction).bounds());
    }

	public int directionInt(Direction direction)
	{
        switch (direction)
        {
            case NORTH:
                return 1;
            case SOUTH:
                return 2;
            case EAST:
                return 3;
            case WEST:
                return 4;
            case UP:
                return 5;
            case DOWN:
                return 6;
            default:
                return 1;
        }
	}
}
