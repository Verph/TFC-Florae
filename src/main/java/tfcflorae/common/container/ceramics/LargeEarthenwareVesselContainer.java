package tfcflorae.common.container.ceramics;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.CapabilityItemHandler;

import net.dries007.tfc.common.blockentities.LargeVesselBlockEntity;
import net.dries007.tfc.common.blocks.LargeVesselBlock;
import net.dries007.tfc.common.container.*;

import org.jetbrains.annotations.Nullable;

import tfcflorae.common.blockentities.ceramics.LargeEarthenwareVesselBlockEntity;
import tfcflorae.common.blocks.ceramics.LargeEarthenwareVesselBlock;
import tfcflorae.common.container.TFCFContainerTypes;

public class LargeEarthenwareVesselContainer extends BlockEntityContainer<LargeEarthenwareVesselBlockEntity> implements ButtonHandlerContainer
{
    public static LargeEarthenwareVesselContainer create(LargeEarthenwareVesselBlockEntity vessel, Inventory playerInventory, int windowId)
    {
        return new LargeEarthenwareVesselContainer(vessel, playerInventory, windowId).init(playerInventory);
    }

    public LargeEarthenwareVesselContainer(LargeEarthenwareVesselBlockEntity vessel, Inventory playerInventory, int windowId)
    {
        super(TFCFContainerTypes.LARGE_EARTHENWARE_VESSEL.get(), windowId, vessel);
    }

    @Override
    public void onButtonPress(int buttonID, @Nullable CompoundTag extraNBT)
    {
        Level level = blockEntity.getLevel();
        if (level != null)
        {
            LargeEarthenwareVesselBlock.toggleSeal(level, blockEntity.getBlockPos(), blockEntity.getBlockState());
        }
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex)
    {
        if (blockEntity.getBlockState().getValue(LargeVesselBlock.SEALED)) return true;
        return switch (typeOf(slotIndex))
            {
                case MAIN_INVENTORY, HOTBAR -> !moveItemStackTo(stack, 0, LargeEarthenwareVesselBlockEntity.SLOTS, false);
                case CONTAINER -> !moveItemStackTo(stack, containerSlots, slots.size(), false);
            };
    }

    @Override
    protected void addContainerSlots()
    {
        blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            addSlot(new CallbackSlot(blockEntity, handler, 0, 62, 19));
            addSlot(new CallbackSlot(blockEntity, handler, 1, 80, 19));
            addSlot(new CallbackSlot(blockEntity, handler, 2, 98, 19));
            addSlot(new CallbackSlot(blockEntity, handler, 3, 62, 37));
            addSlot(new CallbackSlot(blockEntity, handler, 4, 80, 37));
            addSlot(new CallbackSlot(blockEntity, handler, 5, 98, 37));
            addSlot(new CallbackSlot(blockEntity, handler, 6, 62, 55));
            addSlot(new CallbackSlot(blockEntity, handler, 7, 80, 55));
            addSlot(new CallbackSlot(blockEntity, handler, 8, 98, 55));
        });
    }
}
