package tfcflorae.objects.te;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import net.dries007.tfc.objects.te.TEInventory;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;

import tfcflorae.TFCFlorae;
import tfcflorae.objects.recipes.StickBundleRecipe;
import tfcflorae.util.HelpersTFCF;

@ParametersAreNonnullByDefault
public class TEStickBundle extends TEInventory implements ITickable
{
    private long startTick;
    private int tickGoal;

    public TEStickBundle()
    {
        super(1);
        startTick = 0;
        tickGoal = 0;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack)
    {
        if (slot == 0)
            return StickBundleRecipe.get(stack) != null;
        else
            return false;
    }

    @Override
    public void update()
    {
        if (!world.isRemote)
        {
            if ((int) (CalendarTFC.PLAYER_TIME.getTicks() - startTick) > tickGoal)
            {
                if (recipeExists())
                {
                    grow();
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        startTick = nbt.getLong("startTick");
        tickGoal = nbt.getInteger("tickGoal");
        super.readFromNBT(nbt);
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setLong("startTick", startTick);
        nbt.setInteger("tickGoal", tickGoal);
        return super.writeToNBT(nbt);
    }

    public void onBreakBlock(World world, BlockPos pos, IBlockState state)
    {
        Helpers.spawnItemStack(world, pos, inventory.getStackInSlot(0));
    }

    public void clear()
    {
        startTick = 0;
        tickGoal = 0;
        markDirty();
    }

    public void deleteSlot()
    {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void start()
    {
        if (recipeExists())
        {
            startTick = CalendarTFC.PLAYER_TIME.getTicks();
            setDuration();
        }
        else
        {
            Helpers.spawnItemStack(world, pos, inventory.getStackInSlot(0));
            deleteSlot();
        }
        markDirty();
    }

    public void rain()
    {
        tickGoal += 25;
    }

    private boolean recipeExists()
    {
        ItemStack input = inventory.getStackInSlot(0);
        StickBundleRecipe recipe = null;
        if (!input.isEmpty() && !world.isRemote)
        {
            recipe = StickBundleRecipe.get(input);
        }
        return recipe != null;
    }

    private void setDuration()
    {
        ItemStack input = inventory.getStackInSlot(0);
        int recipeTime = 0;
        if (!input.isEmpty() && !world.isRemote)
        {
            StickBundleRecipe recipe = StickBundleRecipe.get(input);
            if (recipe != null)
            {
                recipeTime = StickBundleRecipe.getDuration(recipe);
            }
        }
        tickGoal = recipeTime;
    }

    private void grow()
    {
        ItemStack input = inventory.getStackInSlot(0);
        if (!input.isEmpty())
        {
            StickBundleRecipe recipe = StickBundleRecipe.get(input);
            if (recipe != null && !world.isRemote)
            {
                inventory.setStackInSlot(0, HelpersTFCF.updateFoodFuzzed(input, recipe.getOutputItem(input)));
                setAndUpdateSlots(0);
                markForSync();
            }
        }
        markDirty();
    }

    public long getTicksRemaining()
	{
        long ticks = CalendarTFC.PLAYER_TIME.getTicks() - startTick;
        if (ticks > tickGoal) return 0;

        return tickGoal - ticks;
    }

    public double getCurrentTicks()
    {
        return CalendarTFC.PLAYER_TIME.getTicks() - startTick;
    }

    public double getGoalTick()
    {
        return tickGoal;
    }

    public double calculatePercentage()
    {
        if(getTicksRemaining() == 0 || tickGoal == 0) return 0;
        return ((double)getTicksRemaining()) / tickGoal;
    }
}
