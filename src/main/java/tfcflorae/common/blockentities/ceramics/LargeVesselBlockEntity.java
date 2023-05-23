package tfcflorae.common.blockentities.ceramics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.capabilities.InventoryItemHandler;
import net.dries007.tfc.common.capabilities.PartialItemHandler;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.capabilities.food.FoodTraits;
import net.dries007.tfc.common.capabilities.size.ItemSizeManager;
import net.dries007.tfc.common.capabilities.size.Size;
import net.dries007.tfc.config.TFCConfig;

import tfcflorae.common.blockentities.TFCFBlockEntities;
import tfcflorae.common.blocks.ceramics.LargeVesselBlock;
import tfcflorae.common.container.ceramics.LargeVesselContainer;
import tfcflorae.util.TFCFHelpers;

import static tfcflorae.TFCFlorae.MOD_ID;

public class LargeVesselBlockEntity extends InventoryBlockEntity<LargeVesselBlockEntity.VesselInventory>
{
    public static final int SLOTS = 9;
    private static final Component NAME = TFCFHelpers.translatable(MOD_ID + ".block_entity.large_vessel");

    public LargeVesselBlockEntity(BlockPos pos, BlockState state)
    {
        super(TFCFBlockEntities.LARGE_VESSEL.get(), pos, state, VesselInventory::new, NAME);
    }

    public LargeVesselBlockEntity(BlockEntityType<? extends LargeVesselBlockEntity> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, VesselInventory::new, NAME);
        /*if (TFCConfig.SERVER.largeVesselEnableAutomation.get())
        {
            sidedInventory.on(new PartialItemHandler(inventory).insert(0, 1, 2, 3, 4, 5, 6, 7, 8), d -> d != Direction.DOWN);
            sidedInventory.on(new PartialItemHandler(inventory).extract(0, 1, 2, 3, 4, 5, 6, 7, 8), Direction.DOWN);
        }*/
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowID, Inventory inv, Player player)
    {
        return LargeVesselContainer.create(this, inv, windowID);
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

    @Override
    public void loadAdditional(CompoundTag nbt)
    {
        super.loadAdditional(nbt);
    }

    @Override
    public void saveAdditional(CompoundTag nbt)
    {
        super.saveAdditional(nbt);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        return super.getCapability(cap, side);
    }

    @Override
    public void clearContent()
    {
        super.clearContent();
    }

    @Override
    public void ejectInventory()
    {
        super.ejectInventory();
    }

    public static class VesselInventory extends InventoryItemHandler implements INBTSerializable<CompoundTag>
    {
        private final LargeVesselBlockEntity vessel;

        VesselInventory(InventoryBlockEntity<?> entity)
        {
            super(entity, SLOTS);
            vessel = (LargeVesselBlockEntity) entity;
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
            return !vessel.getBlockState().getValue(LargeVesselBlock.SEALED);
        }
    }
}