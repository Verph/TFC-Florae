package tfcflorae.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import net.dries007.tfc.common.container.ItemStackContainerProvider;
import net.dries007.tfc.util.collections.IndirectHashCollection;

import tfcflorae.common.TFCFTags;
import tfcflorae.common.container.TFCFContainerProviders;

/**
 * This exists due to problems in handling right click events
 * Forge provides a right click block event. This works for intercepting would-be calls to {@link BlockState#use(Level, Player, InteractionHand, BlockHitResult)}
 * However, this cannot be used (maintaining vanilla behavior) for item usages, or calls to {@link ItemStack#onItemUse(UseOnContext, Function)}, as the priority of those two behaviors are very different (blocks take priority, cancelling the event with an item behavior forces the item to take priority
 *
 * This is in lieu of a system such as https://github.com/MinecraftForge/MinecraftForge/pull/6615
 */
public final class TFCFInteractionManager
{
    private static final ThreadLocal<Boolean> ACTIVE = ThreadLocal.withInitial(() -> false);
    private static final List<Entry> ACTIONS = new ArrayList<>();
    private static final IndirectHashCollection<Item, Entry> CACHE = IndirectHashCollection.create(e -> Arrays.stream(e.item.getItems()).map(ItemStack::getItem).toList(), () -> ACTIONS);

    // Public API

    /**
     * Register an interaction for a block item placement. Will only target blocks using the selected item.
     *
     * This method is safe to call during parallel mod loading.
     */
    /*public static void register(BlockItemPlacement wrapper)
    {
        register(new Entry(wrapper, Ingredient.of(wrapper.getItem()), true, false));
    }*/

    /**
     * Register an interaction. This method is safe to call during parallel mod loading.
     *
     * @see #register(Ingredient, boolean, boolean, OnItemUseAction)
     */
    public static void register(Ingredient item, OnItemUseAction action)
    {
        register(item, false, action);
    }

    /**
     * Register an interaction. This method is safe to call during parallel mod loading.
     *
     * @see #register(Ingredient, boolean, boolean, OnItemUseAction)
     */
    public static void register(Ingredient item, boolean targetAir, OnItemUseAction action)
    {
        register(item, true, targetAir, action);
    }

    /**
     * Register an interaction. This method is safe to call during parallel mod loading.
     *
     * @param item         The items this action should apply to
     * @param targetBlocks if this action should trigger when targeting a block with the item
     * @param targetAir    if this action should trigger when targeting air with the item
     * @param action       The action to run.
     */
    public static void register(Ingredient item, boolean targetBlocks, boolean targetAir, OnItemUseAction action)
    {
        register(new Entry(action, item, targetBlocks, targetAir));
    }

    /**
     * Registers TFCFlorae's interactions.
     */
    public static void registerDefaultInteractions()
    {
        // Knapping
        register(Ingredient.of(TFCFTags.Items.EARTHENWARE_CLAY_KNAPPING), true, createKnappingInteraction((stack, player) -> stack.getCount() >= 5, TFCFContainerProviders.EARTHENWARE_CLAY_KNAPPING));
        register(Ingredient.of(TFCFTags.Items.KAOLINITE_CLAY_KNAPPING), true, createKnappingInteraction((stack, player) -> stack.getCount() >= 5, TFCFContainerProviders.KAOLINITE_CLAY_KNAPPING));
        register(Ingredient.of(TFCFTags.Items.STONEWARE_CLAY_KNAPPING), true, createKnappingInteraction((stack, player) -> stack.getCount() >= 5, TFCFContainerProviders.STONEWARE_CLAY_KNAPPING));
    }

    public static OnItemUseAction createKnappingInteraction(BiPredicate<ItemStack, Player> condition, ItemStackContainerProvider container)
    {
        return (stack, context) -> {
            final Player player = context.getPlayer();
            if (player != null && condition.test(stack, player) && context.getClickedPos().equals(BlockPos.ZERO))
            {
                if (player instanceof ServerPlayer serverPlayer)
                {
                    NetworkHooks.openGui(serverPlayer, container.of(stack, context.getHand()), ItemStackContainerProvider.write(context.getHand()));
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        };
    }

    public static Optional<InteractionResult> onItemUse(ItemStack stack, UseOnContext context, boolean isTargetingAir)
    {
        if (!ACTIVE.get())
        {
            for (Entry entry : CACHE.getAll(stack.getItem()))
            {
                if ((isTargetingAir ? entry.targetAir : entry.targetBlocks) && entry.item.test(stack))
                {
                    InteractionResult result;
                    ACTIVE.set(true);
                    try
                    {
                        result = entry.action().onItemUse(stack, context);
                    }
                    finally
                    {
                        ACTIVE.set(false);
                    }
                    return result == InteractionResult.PASS ? Optional.empty() : Optional.of(result);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Register an interaction. This method is safe to call during parallel mod loading.
     */
    private static synchronized void register(Entry entry)
    {
        ACTIONS.add(entry);
    }

    /**
     * Return {@link InteractionResult#PASS} to allow normal right click handling
     */
    @FunctionalInterface
    public interface OnItemUseAction
    {
        InteractionResult onItemUse(ItemStack stack, UseOnContext context);
    }

    private record Entry(OnItemUseAction action, Ingredient item, boolean targetBlocks, boolean targetAir) {}
}
