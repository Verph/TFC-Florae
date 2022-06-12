package tfcflorae.common.container;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.items.ItemStackHandler;

import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.common.container.*;
import net.dries007.tfc.common.recipes.TFCRecipeTypes;
import net.dries007.tfc.common.recipes.inventory.EmptyInventory;
import net.dries007.tfc.util.KnappingPattern;
import org.jetbrains.annotations.Nullable;

import tfcflorae.common.recipes.TFCFKnappingRecipe;
import tfcflorae.common.recipes.TFCFRecipeTypes;

public class TFCFKnappingContainer extends ItemStackContainer implements ButtonHandlerContainer, EmptyInventory, ISlotCallback
{
    public static final int SLOT_OUTPUT = 0;

    public static TFCFKnappingContainer createEarthenwareClay(ItemStack stack, InteractionHand hand, Inventory playerInventory, int windowId)
    {
        return new TFCFKnappingContainer(TFCFContainerTypes.EARTHENWARE_CLAY_KNAPPING.get(), TFCFRecipeTypes.EARTHENWARE_CLAY_KNAPPING.get(), windowId, playerInventory, stack, hand, 5, true, true, TFCSounds.KNAP_CLAY.get()).init(playerInventory, 20);
    }

    public static TFCFKnappingContainer createKaoliniteClay(ItemStack stack, InteractionHand hand, Inventory playerInventory, int windowId)
    {
        return new TFCFKnappingContainer(TFCFContainerTypes.KAOLINITE_CLAY_KNAPPING.get(), TFCFRecipeTypes.KAOLINITE_CLAY_KNAPPING.get(), windowId, playerInventory, stack, hand, 5, true, true, TFCSounds.KNAP_CLAY.get()).init(playerInventory, 20);
    }

    public static TFCFKnappingContainer createStonewareClay(ItemStack stack, InteractionHand hand, Inventory playerInventory, int windowId)
    {
        return new TFCFKnappingContainer(TFCFContainerTypes.STONEWARE_CLAY_KNAPPING.get(), TFCFRecipeTypes.STONEWARE_CLAY_KNAPPING.get(), windowId, playerInventory, stack, hand, 5, true, true, TFCSounds.KNAP_CLAY.get()).init(playerInventory, 20);
    }

    private final int amountToConsume;
    private final boolean usesDisabledTex;
    private final boolean consumeAfterComplete;
    private final RecipeType<? extends TFCFKnappingRecipe> recipeType;
    private final SoundEvent sound;

    private final KnappingPattern pattern;
    private final ItemStack originalStack;

    private boolean requiresReset;
    private boolean hasBeenModified;
    private boolean hasConsumedIngredient;

    public TFCFKnappingContainer(MenuType<?> containerType, RecipeType<? extends TFCFKnappingRecipe> recipeType, int windowId, Inventory playerInv, ItemStack stack, InteractionHand hand, int amountToConsume, boolean consumeAfterComplete, boolean usesDisabledTex, SoundEvent sound)
    {
        super(containerType, windowId, playerInv, stack, hand);

        this.amountToConsume = amountToConsume;
        this.usesDisabledTex = usesDisabledTex;
        this.consumeAfterComplete = consumeAfterComplete;
        this.recipeType = recipeType;
        this.sound = sound;

        pattern = new KnappingPattern();
        hasBeenModified = false;
        hasConsumedIngredient = false;
        originalStack = stack.copy();

        setRequiresReset(false);
    }

    @Override
    public void onButtonPress(int buttonID, @Nullable CompoundTag extraNBT)
    {
        // Set the matching patterns slot to clicked
        pattern.set(buttonID, false);

        // Maybe consume one of the input items, if we should
        if (!hasBeenModified)
        {
            if (!player.isCreative() && !consumeAfterComplete)
            {
                stack.shrink(amountToConsume);
            }
            hasBeenModified = true;
        }

        // Update the output slot based on the recipe
        final Slot slot = slots.get(SLOT_OUTPUT);
        if (player.level instanceof ServerLevel level)
        {
            slot.set(level.getRecipeManager().getRecipeFor(recipeType, this, level)
                .map(recipe -> recipe.assemble(this))
                .orElse(ItemStack.EMPTY));
        }
    }

    @Override
    public void removed(Player player)
    {
        final Slot slot = slots.get(SLOT_OUTPUT);
        final ItemStack stack = slot.getItem();
        if (!stack.isEmpty())
        {
            if (!player.level.isClientSide())
            {
                player.getInventory().placeItemBackInInventory(stack);
                consumeIngredientStackAfterComplete();
            }
        }
        super.removed(player);
    }

    public KnappingPattern getPattern()
    {
        return pattern;
    }

    public ItemStack getOriginalStack()
    {
        return originalStack;
    }

    public boolean usesDisabledTexture()
    {
        return usesDisabledTex;
    }

    public SoundEvent getSound()
    {
        return sound;
    }

    @Override
    public void onSlotTake(Player player, int slot, ItemStack stack)
    {
        resetPattern();
    }

    public boolean requiresReset()
    {
        return requiresReset;
    }

    public void setRequiresReset(boolean requiresReset)
    {
        this.requiresReset = requiresReset;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack)
    {
        return false;
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex)
    {
        return switch (typeOf(slotIndex))
            {
                case CONTAINER -> !moveItemStackTo(stack, containerSlots, containerSlots + 36, true); // Hotbar first
                case HOTBAR -> !moveItemStackTo(stack, containerSlots, containerSlots + 27, false);
                case MAIN_INVENTORY -> !moveItemStackTo(stack, containerSlots + 27, containerSlots + 36, false);
            };
    }

    @Override
    protected void addContainerSlots()
    {
        addSlot(new CallbackSlot(this, new ItemStackHandler(1), 0, 128, 46));
    }

    private void resetPattern()
    {
        pattern.setAll(false);
        setRequiresReset(true);
        consumeIngredientStackAfterComplete();
    }

    protected void consumeIngredientStackAfterComplete()
    {
        if (consumeAfterComplete && !hasConsumedIngredient)
        {
            stack.shrink(amountToConsume);
            hasConsumedIngredient = true;
        }
    }
}
