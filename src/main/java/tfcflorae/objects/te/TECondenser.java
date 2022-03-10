package tfcflorae.objects.te;

import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import net.dries007.tfc.api.capability.size.CapabilityItemSize;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.objects.fluids.capability.FluidHandlerSided;
import net.dries007.tfc.objects.fluids.capability.FluidTankCallback;
import net.dries007.tfc.objects.fluids.capability.IFluidHandlerSidedCallback;
import net.dries007.tfc.objects.fluids.capability.IFluidTankCallback;
import net.dries007.tfc.objects.inventory.capability.IItemHandlerSidedCallback;
import net.dries007.tfc.objects.inventory.capability.ItemHandlerSidedWrapper;
import net.dries007.tfc.objects.te.TETickableInventory;
import net.dries007.tfc.util.FluidTransferHelper;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendarFormatted;
import net.dries007.tfc.util.calendar.ICalendarTickable;

import tfcflorae.objects.items.itemblock.ItemBlockCondenser;

@ParametersAreNonnullByDefault
public class TECondenser extends TETickableInventory implements ITickable, ICalendarTickable, IItemHandlerSidedCallback, IFluidHandlerSidedCallback, IFluidTankCallback
{
    public static final int SLOT_FLUID_CONTAINER_IN = 0;
    public static final int SLOT_FLUID_CONTAINER_OUT = 1;
    public static final int SLOT_ITEM = 2;
    public static final int MAX_FLUID_TEMPERATURE = 500;

    private final FluidTank tank = new BarrelFluidTank(this, 0);
    private final Queue<ItemStack> surplus = new LinkedList<>(); // Surplus items from a recipe with output > stackSize
    private long lastPlayerTick; // Last player tick this barrel was ticked (for purposes of catching up)
    private BarrelRecipe recipe;
    private int tickCounter;
    private boolean checkInstantRecipe = false;

    public TECondenser()
    {
        super(3);
    }

    /**
     * Save up item and fluid handler contents to a barrel's ItemStack
     *
     * @param stack the barrel's stack to save contents to
     */
    public void saveToItemStack(ItemStack stack)
    {
        IFluidHandler alembicCapability = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (alembicCapability instanceof ItemBlockCondenser.ItemCondenserFluidHandler)
        {
            NBTTagCompound inventoryTag = null;
            // Check if inventory has contents
            for (int i = 0; i < inventory.getSlots(); i++)
            {
                if (!inventory.getStackInSlot(i).isEmpty())
                {
                    inventoryTag = inventory.serializeNBT();
                    break;
                }
            }
            NBTTagList surplusTag = null;
            // Check if there's remaining surplus from recipe
            if (!surplus.isEmpty())
            {
                surplusTag = new NBTTagList();
                for (ItemStack surplusStack : surplus)
                {
                    surplusTag.appendTag(surplusStack.serializeNBT());
                }
            }
            FluidStack storing = tank.getFluid();
            if (storing != null || inventoryTag != null || surplusTag != null)
            {
                ((ItemBlockCondenser.ItemCondenserFluidHandler) alembicCapability).setBarrelContents(storing, inventoryTag, surplusTag);
            }
        }
    }

    /**
     * Load up item and fluid handler contents from a barrel's ItemStack
     *
     * @param stack the barrel's stack to load contents from
     */
    public void loadFromItemStack(ItemStack stack)
    {
        IFluidHandler alembicCapability = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (alembicCapability instanceof ItemBlockCondenser.ItemCondenserFluidHandler)
        {
            NBTTagCompound contents = ((ItemBlockCondenser.ItemCondenserFluidHandler) alembicCapability).getBarrelContents();
            if (contents != null)
            {
                inventory.deserializeNBT(contents.getCompoundTag("inventory"));
                surplus.clear();
                NBTTagList surplusItems = contents.getTagList("surplus", Constants.NBT.TAG_COMPOUND);
                if (!surplusItems.isEmpty())
                {
                    for (int i = 0; i < surplusItems.tagCount(); i++)
                    {
                        surplus.add(new ItemStack(surplusItems.getCompoundTagAt(i)));
                    }
                }
                tank.fill(((ItemBlockCondenser.ItemCondenserFluidHandler) alembicCapability).getFluid(), true);
                recipe = BarrelRecipe.get(inventory.getStackInSlot(SLOT_ITEM), tank.getFluid());
                markForSync();
            }
        }
    }

    /**
     * Called once per side when the TileEntity has finished loading.
     * On servers, this is the earliest point in time to safely access the TE's World object.
     */
    @Override
    public void onLoad()
    {
        if (!world.isRemote)
        {
            recipe = BarrelRecipe.get(inventory.getStackInSlot(SLOT_ITEM), tank.getFluid());
        }
    }

    @Nullable
    public BarrelRecipe getRecipe()
    {
        return recipe;
    }

    /*@Nonnull
    public String getSealedDate()
    {
        return ICalendarFormatted.getTimeAndDate(sealedCalendarTick, CalendarTFC.CALENDAR_TIME.getDaysInMonth());
    }*/

    @Override
    public void setAndUpdateFluidTank(int fluidTankID)
    {
        markForSync();
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, EnumFacing side)
    {
        return (isItemValid(slot, stack) || side == null && slot == SLOT_FLUID_CONTAINER_OUT);
    }

    @Override
    public boolean canExtract(int slot, EnumFacing side)
    {
        return (side == null || slot != SLOT_FLUID_CONTAINER_IN);
    }

    @Override
    public boolean canFill(FluidStack resource, EnumFacing side)
    {
        return (resource.getFluid() == null || resource.getFluid().getTemperature(resource) < MAX_FLUID_TEMPERATURE);
    }

    @Override
    public boolean canDrain(EnumFacing side)
    {
        return true;
    }

    @Override
    public void update()
    {
        super.update();
        checkForCalendarUpdate();
        if (!world.isRemote)
        {
            tickCounter++;
            if (tickCounter == 10)
            {
                tickCounter = 0;

                ItemStack fluidContainerIn = inventory.getStackInSlot(SLOT_FLUID_CONTAINER_IN);
                FluidActionResult result = FluidTransferHelper.emptyContainerIntoTank(fluidContainerIn, tank, inventory, SLOT_FLUID_CONTAINER_OUT, 1000, world, pos); //Change the "1000" to config for tank capacity

                if (!result.isSuccess())
                {
                    result = FluidTransferHelper.fillContainerFromTank(fluidContainerIn, tank, inventory, SLOT_FLUID_CONTAINER_OUT, 1000, world, pos); //Change the "1000" to config for tank capacity
                }

                if (result.isSuccess())
                {
                    inventory.setStackInSlot(SLOT_FLUID_CONTAINER_IN, result.getResult());
                }

                Fluid freshWater = FluidRegistry.getFluid("fresh_water");

                if (world.isRainingAt(pos.up()) && (tank.getFluid() == null || tank.getFluid().getFluid() == freshWater))
                {
                    tank.fill(new FluidStack(freshWater, 10), true);
                }

                if (inventory.getStackInSlot(SLOT_ITEM) == ItemStack.EMPTY && !surplus.isEmpty())
                {
                    inventory.setStackInSlot(SLOT_ITEM, surplus.poll());
                }
            }

            if (checkInstantRecipe)
            {
                ItemStack inputStack = inventory.getStackInSlot(SLOT_ITEM);
                FluidStack inputFluid = tank.getFluid();
                BarrelRecipe instantRecipe = BarrelRecipe.getInstant(inputStack, inputFluid);
                if (instantRecipe != null && inputFluid != null && instantRecipe.isValidInputInstant(inputStack, inputFluid))
                {
                    tank.setFluid(instantRecipe.getOutputFluid(inputFluid, inputStack));
                    List<ItemStack> output = instantRecipe.getOutputItem(inputFluid, inputStack);
                    ItemStack first = output.get(0);
                    output.remove(0);
                    inventory.setStackInSlot(SLOT_ITEM, first);
                    surplus.addAll(output);
                    instantRecipe.onRecipeComplete(world, pos);
                    markForSync();
                }
                else
                {
                    checkInstantRecipe = false;
                }
            }
        }
    }

    @Override
    public void setAndUpdateSlots(int slot)
    {
        checkInstantRecipe = true;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        tank.readFromNBT(nbt.getCompoundTag("tank"));
        if (tank.getFluidAmount() > tank.getCapacity())
        {
            // Fix config changes
            FluidStack fluidStack = tank.getFluid();
            if (fluidStack != null)
            {
                fluidStack.amount = tank.getCapacity();
            }
            tank.setFluid(fluidStack);
        }
        lastPlayerTick = nbt.getLong("lastPlayerTick");

        surplus.clear();
        if (nbt.hasKey("surplus"))
        {
            NBTTagList surplusItems = nbt.getTagList("surplus", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < surplusItems.tagCount(); i++)
            {
                surplus.add(new ItemStack(surplusItems.getCompoundTagAt(i)));
            }
        }

        recipe = BarrelRecipe.get(inventory.getStackInSlot(SLOT_ITEM), tank.getFluid());
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
        nbt.setLong("lastPlayerTick", lastPlayerTick);

        if (!surplus.isEmpty())
        {
            NBTTagList surplusList = new NBTTagList();
            for (ItemStack stack : surplus)
            {
                surplusList.appendTag(stack.serializeNBT());
            }
            nbt.setTag("surplus", surplusList);
        }

        return super.writeToNBT(nbt);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) new ItemHandlerSidedWrapper(this, inventory, facing);
        }

        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return (T) new FluidHandlerSided(this, tank, facing);
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void onBreakBlock(World world, BlockPos pos, IBlockState state)
    {
        ItemStack barrelStack = new ItemStack(state.getBlock());


        int slotsToDrop = inventory.getSlots();
        for (int i = 0; i < slotsToDrop; i++)
        {
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
            inventory.setStackInSlot(i, new ItemStack(Items.AIR, 0));
        }
        if (!surplus.isEmpty())
        {
            for (ItemStack surplusToDrop : surplus)
            {
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), surplusToDrop);
            }
            surplus.clear();
        }
        InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), barrelStack);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack)
    {
        switch (slot)
        {
            case SLOT_FLUID_CONTAINER_IN:
                return stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            case SLOT_ITEM:
                IItemSize size = CapabilityItemSize.getIItemSize(stack);
                if (size != null)
                {
                    return size.getSize(stack).isSmallerThan(Size.HUGE);
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onCalendarUpdate(long deltaPlayerTicks)
    {
        while (deltaPlayerTicks > 0)
        {
            deltaPlayerTicks = 0;
            if (recipe != null && recipe.getDuration() > 0)
            {
                long tickFinish = recipe.getDuration();
                if (tickFinish <= CalendarTFC.PLAYER_TIME.getTicks())
                {
                    // Mark to run this transaction again in case this recipe produces valid output for another which could potentially finish in this time period.
                    deltaPlayerTicks = 1;
                    long offset = tickFinish - CalendarTFC.PLAYER_TIME.getTicks();

                    CalendarTFC.runTransaction(offset, offset, () -> {
                        ItemStack inputStack = inventory.getStackInSlot(SLOT_ITEM);
                        FluidStack inputFluid = tank.getFluid();
                        if (recipe.isValidInput(inputFluid, inputStack))
                        {
                            tank.setFluid(recipe.getOutputFluid(inputFluid, inputStack));
                            List<ItemStack> output = recipe.getOutputItem(inputFluid, inputStack);
                            ItemStack first = output.get(0);
                            output.remove(0);
                            inventory.setStackInSlot(SLOT_ITEM, first);
                            surplus.addAll(output);
                            markForSync();
                        }
                        else
                        {
                            recipe = null;
                        }
                    });
                }
            }
        }
    }

    @Override
    public long getLastUpdateTick()
    {
        return lastPlayerTick;
    }

    @Override
    public void setLastUpdateTick(long tick)
    {
        this.lastPlayerTick = tick;
    }

    protected static class BarrelFluidTank extends FluidTankCallback
    {
        private final Set<Fluid> whitelist;

        public BarrelFluidTank(IFluidTankCallback callback, int fluidTankID)
        {
            super(callback, fluidTankID, 1000); //Change the "1000" to config for tank capacity
            whitelist = Arrays.stream(new String[] {"fresh_water", "salt_water", "water"}).map(FluidRegistry::getFluid).filter(Objects::nonNull).collect(Collectors.toSet()); //Change string array to whitelist for condenser cooling water
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid)
        {
            return fluid != null && (whitelist.contains(fluid.getFluid()) || BarrelRecipe.isBarrelFluid(fluid));
        }
    }
}