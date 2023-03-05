package tfcflorae.common.container;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.dries007.tfc.common.blocks.devices.BarrelBlock;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.ButtonHandlerContainer;
import net.dries007.tfc.common.container.CallbackSlot;

import tfcflorae.common.blockentities.TFCFBarrelBlockEntity;

import org.jetbrains.annotations.Nullable;

public class TFCFBarrelContainer extends BlockEntityContainer<TFCFBarrelBlockEntity> implements ButtonHandlerContainer
{
    public static TFCFBarrelContainer create(TFCFBarrelBlockEntity barrel, Inventory playerInv, int windowId)
    {
        return new TFCFBarrelContainer(windowId, barrel).init(playerInv, 12);
    }

    private TFCFBarrelContainer(int windowId, TFCFBarrelBlockEntity barrel)
    {
        super(TFCFContainerTypes.BARREL.get(), windowId, barrel);
    }

    @Override
    public void onButtonPress(int buttonID, @Nullable CompoundTag extraNBT)
    {
        Level level = blockEntity.getLevel();
        if (level != null)
        {
            BarrelBlock.toggleSeal(level, blockEntity.getBlockPos(), blockEntity.getBlockState());
        }
    }

    @Override
    protected void addContainerSlots()
    {
        blockEntity.getCapability(Capabilities.ITEM).ifPresent(inventory -> {
            addSlot(new CallbackSlot(blockEntity, inventory, TFCFBarrelBlockEntity.SLOT_FLUID_CONTAINER_IN, 35, 20));
            addSlot(new CallbackSlot(blockEntity, inventory, TFCFBarrelBlockEntity.SLOT_FLUID_CONTAINER_OUT, 35, 54));
            addSlot(new CallbackSlot(blockEntity, inventory, TFCFBarrelBlockEntity.SLOT_ITEM, 89, 37));
        });
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex)
    {
        if (blockEntity.getBlockState().getValue(BarrelBlock.SEALED)) return true;
        final int containerSlot = stack.getCapability(Capabilities.FLUID_ITEM).isPresent() && stack.getCapability(HeatCapability.CAPABILITY).map(cap -> cap.getTemperature() == 0f).orElse(false) ? TFCFBarrelBlockEntity.SLOT_FLUID_CONTAINER_IN : TFCFBarrelBlockEntity.SLOT_ITEM;

        return switch (typeOf(slotIndex))
            {
                case MAIN_INVENTORY, HOTBAR -> !moveItemStackTo(stack, containerSlot, containerSlot + 1, false);
                case CONTAINER -> !moveItemStackTo(stack, containerSlots, slots.size(), false);
            };
    }
}
