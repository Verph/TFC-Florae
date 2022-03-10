package tfcflorae.objects.container;

import javax.annotation.Nullable;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.dries007.tfc.objects.container.ContainerTE;
import net.dries007.tfc.objects.container.IButtonHandler;
import net.dries007.tfc.objects.inventory.slot.SlotCallback;

import tfcflorae.objects.te.TECondenser;
import static tfcflorae.objects.te.TECondenser.*;

public class ContainerCondenser extends ContainerTE<TECondenser> implements IButtonHandler
{
    public ContainerCondenser(InventoryPlayer playerInv, TECondenser teCondenser)
    {
        super(playerInv, teCondenser);
    }

    @Nullable
    public IFluidHandler getBarrelTank()
    {
        return tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    }

    @Override
    public void onButtonPress(int buttonID, @Nullable NBTTagCompound extraNBT)
    {
        // Slot will always be 0, extraNBT will be empty
        /*if (!tile.getWorld().isRemote)
        {
            BlockMetalAlembicCondenser.toggleBarrelSeal(tile.getWorld(), tile.getPos());
        }*/
    }

    @Override
    protected void addContainerSlots()
    {
        IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        if (inventory != null)
        {
            addSlotToContainer(new SlotCallback(inventory, SLOT_FLUID_CONTAINER_IN, 35, 20, tile));
            addSlotToContainer(new SlotCallback(inventory, SLOT_FLUID_CONTAINER_OUT, 35, 54, tile));
            addSlotToContainer(new SlotCallback(inventory, SLOT_ITEM, 89, 37, tile));
        }
    }
}
