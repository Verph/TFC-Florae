package tfcflorae.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import net.dries007.tfc.common.blocks.DirectionPropertyBlock;
import net.dries007.tfc.util.BlockItemPlacement;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.InteractionManager;

import tfcflorae.common.TFCFTags;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.MineralSheetBlock;
import tfcflorae.common.container.TFCFContainerProviders;

public final class TFCFInteractionManager
{
    public static void init()
    {
        InteractionManager.register(Ingredient.of(TFCFTags.Items.EARTHENWARE_CLAY_KNAPPING), false, true, InteractionManager.createKnappingInteraction((stack, player) -> stack.getCount() >= 5, TFCFContainerProviders.EARTHENWARE_CLAY_KNAPPING));
        InteractionManager.register(Ingredient.of(TFCFTags.Items.KAOLINITE_CLAY_KNAPPING), false, true, InteractionManager.createKnappingInteraction((stack, player) -> stack.getCount() >= 5, TFCFContainerProviders.KAOLINITE_CLAY_KNAPPING));
        InteractionManager.register(Ingredient.of(TFCFTags.Items.STONEWARE_CLAY_KNAPPING), false, true, InteractionManager.createKnappingInteraction((stack, player) -> stack.getCount() >= 5, TFCFContainerProviders.STONEWARE_CLAY_KNAPPING));
        InteractionManager.register(Ingredient.of(TFCFTags.Items.FLINT_KNAPPING), false, true, InteractionManager.createKnappingInteraction((stack, player) -> stack.getCount() >= 2, TFCFContainerProviders.FLINT_KNAPPING));

        InteractionManager.register(Ingredient.of(TFCFTags.Items.MINERAL_SHEETS), false, (stack, context) -> {
            final Player player = context.getPlayer();
            if (player != null && player.isShiftKeyDown())
            {
                final Level level = context.getLevel();
                final Direction clickedFace = context.getClickedFace(); // i.e. click on UP
                final Direction sheetFace = clickedFace.getOpposite(); // i.e. place on DOWN

                final BlockPos clickedPos = context.getClickedPos();
                final BlockPos relativePos = clickedPos.relative(clickedFace);

                final BlockState clickedState = level.getBlockState(clickedPos);
                final BlockState relativeState = level.getBlockState(relativePos);

                final BlockPlaceContext blockContext = new BlockPlaceContext(context);
                final BooleanProperty property = DirectionPropertyBlock.getProperty(sheetFace);

                if (blockContext.replacingClickedOnBlock())
                {
                    // Sheets are not allowed to place on replaceable blocks, as it is dependent on the face clicked - but when we click on a replaceable block, that face doesn't make sense.
                    return InteractionResult.FAIL;
                }

                // Sheets behave differently than ingots, because we need to check the targeted face if it's empty or not
                // We assume immediately that we want to target the relative pos and state
                if (Helpers.isBlock(relativeState, TFCFBlocks.MINERAL_SHEET.get()))
                {
                    // We targeted an existing sheet pile, so we need to check if there's an empty space for it
                    if (!relativeState.getValue(property) && BlockItemPlacement.canPlace(blockContext, clickedState) && clickedState.isFaceSturdy(level, clickedPos, clickedFace))
                    {
                        // Add to an existing sheet pile
                        final ItemStack insertStack = stack.split(1);
                        MineralSheetBlock.addSheet(level, relativePos, relativeState, sheetFace, insertStack);
                        return InteractionResult.SUCCESS;
                    }
                    else
                    {
                        // No space
                        return InteractionResult.FAIL;
                    }
                }
                // This is where we assert that we can only replace replaceable blocks
                else if (level.getBlockState(relativePos).canBeReplaced(blockContext))
                {
                    // Want to place a new sheet at the above location
                    final BlockState placingState = TFCFBlocks.MINERAL_SHEET.get().defaultBlockState().setValue(property, true);
                    if (BlockItemPlacement.canPlace(blockContext, placingState) && clickedState.isFaceSturdy(level, clickedPos, clickedFace))
                    {
                        final ItemStack insertStack = stack.split(1);
                        MineralSheetBlock.addSheet(level, relativePos, placingState, sheetFace, insertStack);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            return InteractionResult.PASS;
        });
    }
}
