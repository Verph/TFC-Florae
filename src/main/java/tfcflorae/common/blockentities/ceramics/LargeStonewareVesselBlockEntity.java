package tfcflorae.common.blockentities.ceramics;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;

import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.capabilities.InventoryItemHandler;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.capabilities.food.FoodTraits;
import net.dries007.tfc.common.capabilities.size.ItemSizeManager;
import net.dries007.tfc.common.capabilities.size.Size;
import net.dries007.tfc.common.recipes.inventory.EmptyInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import tfcflorae.common.blockentities.TFCFBlockEntities;
import tfcflorae.common.blocks.ceramics.LargeStonewareVesselBlock;
import tfcflorae.common.container.ceramics.LargeStonewareVesselContainer;

import static tfcflorae.TFCFlorae.MOD_ID;

public class LargeStonewareVesselBlockEntity extends InventoryBlockEntity<LargeStonewareVesselBlockEntity.VesselInventory>
{
    public static final int SLOTS = 9;
    private static final Component NAME = new TranslatableComponent(MOD_ID + ".block_entity.large_stoneware_vessel");

    public LargeStonewareVesselBlockEntity(BlockPos pos, BlockState state)
    {
        super(TFCFBlockEntities.LARGE_STONEWARE_VESSEL.get(), pos, state, VesselInventory::new, NAME);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowID, Inventory inv, Player player)
    {
        return LargeStonewareVesselContainer.create(this, inv, windowID);
    }

    public void onUnseal()
    {
        for (int i = 0; i < SLOTS; i++)
        {
            inventory.setStackInSlot(i, FoodCapability.removeTrait(inventory.getStackInSlot(i).copy(), FoodTraits.PRESERVED));
        }
    }

    public void onSeal()
    {
        for (int i = 0; i < SLOTS; i++)
        {
            inventory.setStackInSlot(i, FoodCapability.applyTrait(inventory.getStackInSlot(i).copy(), FoodTraits.PRESERVED));
        }
    }

    public static class VesselInventory extends InventoryItemHandler implements INBTSerializable<CompoundTag>, EmptyInventory
    {
        private final LargeStonewareVesselBlockEntity vessel;

        VesselInventory(InventoryBlockEntity<?> entity)
        {
            super(entity, SLOTS);
            vessel = (LargeStonewareVesselBlockEntity) entity;
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
        {
            return canModify() ? super.insertItem(slot, stack, simulate) : stack;
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return canModify() ? super.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return canModify() && ItemSizeManager.get(stack).getSize(stack).isSmallerThan(Size.LARGE) && super.isItemValid(slot, stack);
        }

        private boolean canModify()
        {
            return !vessel.getBlockState().getValue(LargeStonewareVesselBlock.SEALED);
        }
    }
}