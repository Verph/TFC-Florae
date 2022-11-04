package tfcflorae.common.items;

import java.util.function.Function;

import com.mojang.datafixers.util.Either;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.common.capabilities.player.PlayerDataCapability;
import net.dries007.tfc.common.recipes.CollapseRecipe;
import net.dries007.tfc.config.TFCConfig;

import tfcflorae.client.TFCFSounds;
import tfcflorae.common.recipes.BrushingRecipe;

public class BrushItem extends Item implements Vanishable
{
    public static final int USE_TIME = 100;

    public BrushItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        final Player player = context.getPlayer();
        if (player != null)
        {
            final Level level = context.getLevel();
            final BlockPos pos = context.getClickedPos();
            final BlockState state = level.getBlockState(pos);
            final Either<BlockState, InteractionResult> result = BrushingRecipe.computeResult(player, state, new BlockHitResult(context.getClickLocation(), context.getClickedFace(), pos, context.isInside()), true);
            return result.map(resultState -> {
                player.playSound(TFCFSounds.BRUSHING.get(), 1f, 1f);

                ItemStack held = player.getMainHandItem();
                if (!level.isClientSide)
                {
                    if (TFCConfig.SERVER.enableChiselsStartCollapses.get())
                    {
                        if (CollapseRecipe.tryTriggerCollapse(level, pos))
                        {
                            return InteractionResult.SUCCESS; // Abort chiseling
                        }
                    }

                    player.getCapability(PlayerDataCapability.CAPABILITY).ifPresent(cap -> {
                        final BrushingRecipe recipeUsed = BrushingRecipe.getRecipe(state, held);
                        if (recipeUsed != null)
                        {
                            ItemStack extraDrop = recipeUsed.getExtraDrop(held);
                            if (!extraDrop.isEmpty())
                            {
                                ItemHandlerHelper.giveItemToPlayer(player, extraDrop);
                            }
                        }
                    });
                }

                level.setBlockAndUpdate(pos, resultState);

                held.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                player.getCooldowns().addCooldown(this, 10);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }, Function.identity()); // returns the interaction result if we are given one
        }
        return InteractionResult.PASS;
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return USE_TIME;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack)
    {
        return UseAnim.CROSSBOW;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int countLeft)
    {
        if (countLeft % 16 == 0 && !level.isClientSide)
        {
            level.playSound(null, entity, TFCFSounds.BRUSHING.get(), SoundSource.PLAYERS, 1f, 1f);
        }
    }
}
