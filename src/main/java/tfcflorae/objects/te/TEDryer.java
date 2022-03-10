package tfcflorae.objects.te;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.objects.te.TEInventory;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;

import tfcflorae.objects.recipes.DryingRecipe;
import tfcflorae.util.HelpersTFCF;

@ParametersAreNonnullByDefault
public class TEDryer extends TEInventory implements ITickable
{
    private long[] startTick = new long[2];
    private int[] tickGoal = new int[2];
    private boolean[] done = new boolean[2];

    public TEDryer()
    {
        super(2);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack)
    {
        if (slot == 0)
            return DryingRecipe.get(stack) != null;
        if (slot == 1)
            return DryingRecipe.get(stack) != null;
        return false;
    }

    @Override
    public void update()
    {
        if (!world.isRemote)
        {
            if ((int) (CalendarTFC.PLAYER_TIME.getTicks() - startTick[0]) > tickGoal[0])
            {
                if (recipeExists(0))
                {
                    dry(0);
                }
            }
            if ((int) (CalendarTFC.PLAYER_TIME.getTicks() - startTick[1]) > tickGoal[1])
            {
                if (recipeExists(1))
                {
                    dry(1);
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        startTick[0] = nbt.getLong("startTick1");
        startTick[1] = nbt.getLong("startTick2");
        tickGoal[0] = nbt.getInteger("tickGoal1");
        tickGoal[1] = nbt.getInteger("tickGoal2");
        done[0] = nbt.getBoolean("done1");
        done[1] = nbt.getBoolean("done2");
        super.readFromNBT(nbt);
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setLong("startTick1", startTick[0]);
        nbt.setLong("startTick2", startTick[1]);
        nbt.setInteger("tickGoal1", tickGoal[0]);
        nbt.setInteger("tickGoal2", tickGoal[1]);
        nbt.setBoolean("done1", done[0]);
        nbt.setBoolean("done2", done[1]);
        return super.writeToNBT(nbt);
    }

    public void onBreakBlock(World world, BlockPos pos, IBlockState state)
    {
        Helpers.spawnItemStack(world, pos, inventory.getStackInSlot(0));
        Helpers.spawnItemStack(world, pos, inventory.getStackInSlot(1));
    }

    public ItemStack removeItem()
    {
        if (!inventory.getStackInSlot(1).isEmpty())
        {
            ItemStack stack = inventory.extractItem(1, 1, false);
            clear(1);
            deleteSlot(1);

            return stack;
        }
        else if (!inventory.getStackInSlot(0).isEmpty())
        {
            ItemStack stack = inventory.extractItem(0, 1, false);
            clear(0);
            deleteSlot(0);

            return stack;
        }

        return ItemStack.EMPTY;
    }

    public ItemStack removeIfDone()
    {
        if (!inventory.getStackInSlot(1).isEmpty() && done[1] == true)
        {
            ItemStack stack = inventory.extractItem(1, 1, false);
            clear(1);
            deleteSlot(1);

            return stack;
        }
        else if (!inventory.getStackInSlot(0).isEmpty() && done[0] == true)
        {
            ItemStack stack = inventory.extractItem(0, 1, false);
            clear(0);
            deleteSlot(0);

            return stack;
        }

        return ItemStack.EMPTY;
    }

    private void clear(int index)
    {
        startTick[index] = 0;
        tickGoal[index] = 0;
        done[index] = false;
        markDirty();
    }

    private void deleteSlot(int index)
    {
        inventory.setStackInSlot(index, ItemStack.EMPTY);
    }

	public ItemStack addItem(ItemStack stack, boolean isSimple)
    {
        if (inventory.getStackInSlot(0).isEmpty())
        {
            ItemStack leftover = inventory.insertItem(0, stack.splitStack(1), false);
            start(0);
            return leftover;
        }
        if (inventory.getStackInSlot(1).isEmpty() && !isSimple)
        {
            ItemStack leftover = inventory.insertItem(1, stack.splitStack(1), false);
            start(1);
            return leftover;
        }

        return ItemStack.EMPTY;
	}

    private void start(int index)
    {
        if (recipeExists(index))
        {
            startTick[index] = CalendarTFC.PLAYER_TIME.getTicks();
            setDuration(index);
        }
        else
        {
            Helpers.spawnItemStack(world, pos, inventory.getStackInSlot(index));
            deleteSlot(index);
        }
        markDirty();
    }

    public void rain()
    {
        tickGoal[0] += 25;
        tickGoal[1] += 25;
    }

    private boolean recipeExists(int index)
    {
        ItemStack input = inventory.getStackInSlot(index);
        DryingRecipe recipe = null;
        if (!input.isEmpty() && !world.isRemote)
        {
            recipe = DryingRecipe.get(input);
        }
        return recipe != null;
    }

    private void setDuration(int index)
    {
        ItemStack input = inventory.getStackInSlot(index);
        int recipeTime = 0;
        if (!input.isEmpty() && !world.isRemote)
        {
            DryingRecipe recipe = DryingRecipe.get(input);
            if (recipe != null)
            {
                recipeTime = DryingRecipe.getDuration(recipe);
            }
        }
        tickGoal[index] = recipeTime;
    }

    private void dry(int index)
    {
        ItemStack input = inventory.getStackInSlot(index);
        if (!input.isEmpty())
        {
            DryingRecipe recipe = DryingRecipe.get(input);
            if (recipe != null && !world.isRemote)
            {
                inventory.setStackInSlot(index, HelpersTFCF.updateFoodFuzzed(input, recipe.getOutputItem(input)));
                setAndUpdateSlots(index);
                markForSync();
                done[index] = true;
            }
        }
        markDirty();
    }

    public long getTicksRemaining(int index)
	{
        return tickGoal[index] - (CalendarTFC.PLAYER_TIME.getTicks() - startTick[index]);
    }
}
