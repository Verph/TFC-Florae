package tfcflorae.common.container.ceramics;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.ButtonHandlerContainer;
import net.dries007.tfc.common.container.CallbackSlot;
import net.dries007.tfc.common.container.PestContainer;

import tfcflorae.common.blockentities.ceramics.LargeVesselBlockEntity;
import tfcflorae.common.blocks.ceramics.LargeVesselBlock;
import tfcflorae.common.container.TFCFContainerTypes;

public class LargeVesselContainer extends BlockEntityContainer<LargeVesselBlockEntity> implements ButtonHandlerContainer, PestContainer
{
    public static LargeVesselContainer create(LargeVesselBlockEntity vessel, Inventory playerInventory, int windowId)
    {
        return new LargeVesselContainer(vessel, windowId).init(playerInventory);
    }

    public LargeVesselContainer(LargeVesselBlockEntity vessel, int windowId)
    {
        super(TFCFContainerTypes.LARGE_VESSEL.get(), windowId, vessel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onButtonPress(int buttonID, @Nullable CompoundTag extraNBT)
    {
        Level level = blockEntity.getLevel();
        if (level != null)
        {
            LargeVesselBlock.toggleSeal(level, blockEntity.getBlockPos(), blockEntity.getBlockState(), (BlockEntityType<? extends LargeVesselBlockEntity>) blockEntity.getType());
        }
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex)
    {
        if (blockEntity.getBlockState().getValue(LargeVesselBlock.SEALED))
        {
            return true;
        }
        return switch (typeOf(slotIndex))
            {
                case MAIN_INVENTORY, HOTBAR -> !moveItemStackTo(stack, 0, LargeVesselBlockEntity.SLOTS, false);
                case CONTAINER -> !moveItemStackTo(stack, containerSlots, slots.size(), false);
            };
    }

    @Override
    protected void addContainerSlots()
    {
        blockEntity.getCapability(Capabilities.ITEM).ifPresent(handler -> {
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

    @Override
    public boolean canBeInfested()
    {
        return !isSealed();
    }

    public boolean isSealed()
    {
        return blockEntity.getBlockState().hasProperty(LargeVesselBlock.SEALED) && blockEntity.getBlockState().getValue(LargeVesselBlock.SEALED);
    }
}
