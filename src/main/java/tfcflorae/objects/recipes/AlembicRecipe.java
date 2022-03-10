package tfcflorae.objects.recipes;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.Helpers;

import tfcflorae.api.registries.TFCFRegistries;

public class AlembicRecipe extends IForgeRegistryEntry.Impl<AlembicRecipe>
{
    @Nullable
    public static AlembicRecipe get(ItemStack stack, FluidStack fluidStack)
    {
        return TFCFRegistries.ALEMBIC.getValuesCollection().stream().filter(x -> x.isValidInput(fluidStack) && x.getDuration() != 0).findFirst().orElse(null);
    }

    /**
     * Checks if a fluidstack is an ingredient for any recipe
     * Used as a complement to alembic's whitelist
     */
    public static boolean isAlembicFluid(FluidStack fluidStack)
    {
        return TFCFRegistries.ALEMBIC.getValuesCollection().stream().filter(x -> x.inputFluid.testIgnoreCount(fluidStack)).findFirst().orElse(null) != null;
    }

    protected final IIngredient<FluidStack> inputFluid;
    private final FluidStack outputFluid;
    private final ItemStack outputStack;
    private final int duration;
    private final float evaporationTemp;

    /**
     * Creates a alembic recipe
     *
     * @param inputFluid  fluid ingredients used to check if it is valid.
     * @param outputFluid the output fluid, when this recipe is completed. Drain fluid to the connected alembic condenser's fluid reservoir/tank. If the condenser has a bucket (or any other functioning fluid container) in the proper item slot, the fluid will transfer to that. If there's no item, then the distillate will spill on a random adjacent block.
     * @param outputStack the output stack, when this recipe is completed. If an item output is defined, produce said item after the distillation process has finished.
     * @param duration    the duration, in ticks, for this recipe to complete.
     * @param evaporation the temperature at which the inputFluid starts converting (boiling/evaporating) into the outputFluid.
     */
    public AlembicRecipe(@Nonnull IIngredient<FluidStack> inputFluid, @Nullable FluidStack outputFluid, @Nonnull ItemStack outputStack, int duration, float evaporationTemp)
    {
        this.inputFluid = inputFluid;
        this.outputFluid = outputFluid;
        this.outputStack = outputStack;
        this.duration = duration;
        this.evaporationTemp = evaporationTemp;
    }

    public boolean isValidInput(@Nullable FluidStack inputFluid)
    {
        return this.inputFluid.test(inputFluid);
    }

    public int getDuration()
    {
        return duration;
    }

    /**
     * @param temperature a temperature
     * @return true if the recipe should evaporate / transform at this temperature
     */
    public boolean isValidTemperature(float temperature)
    {
        return temperature >= evaporationTemp;
    }

    /**
     * Only for GUI purposes - not intended as a crafting mechanic
     *
     * @return The output fluid stack
     */
    @Nullable
    public FluidStack getOutputFluid()
    {
        return outputFluid != null ? outputFluid.copy() : null;
    }

    /**
     * Only for GUI purposes - not intended as a crafting mechanic
     *
     * @return the output item stack
     */
    @Nonnull
    public ItemStack getOutputStack()
    {
        return outputStack;
    }

    @Nonnull
    public IIngredient<FluidStack> getFluidIngredient()
    {
        return inputFluid;
    }

    @Nullable
    public FluidStack getOutputFluid(FluidStack inputFluid, ItemStack inputStack)
    {
        if (outputFluid != null)
        {
            // Ignore input and replace with output
            int outputAmount = Math.min(outputFluid.amount, 1000);
            return new FluidStack(outputFluid.getFluid(), outputAmount);
        }
        else
        {
            // Try and keep as much of the original input as possible
            int retainAmount = inputFluid.amount - (this.inputFluid.getAmount());
            if (retainAmount > 0)
            {
                return new FluidStack(inputFluid.getFluid(), inputFluid.amount - (this.inputFluid.getAmount()));
            }
        }
        return null;
    }

    @Nonnull
    public List<ItemStack> getOutputItem(FluidStack inputFluid, ItemStack inputStack)
    {
        List<ItemStack> outputList = new ArrayList<>();
        if (!this.outputStack.isEmpty())
        {
            // Ignore input and replace with output
            int outputCount = outputStack.getCount();
            do
            {
                int count = Math.min(outputCount, outputStack.getMaxStackSize());
                ItemStack output = outputStack.copy();
                output.setCount(count);
                outputCount -= count;
                outputList.add(output);
            } while (outputCount > 0);
        }
        else
        {
            // Try and keep as much of the original input as possible
            int retainCount = inputStack.getCount();
            if (retainCount > 0)
            {
                inputStack.setCount(retainCount);
                outputList.add(inputStack);
            }
            else
            {
                outputList.add(ItemStack.EMPTY);
            }
        }
        return outputList;
    }

    /**
     * Called by TEAlembic when a recipe finishes
     * Used if you want to play a sound / cause an update of some sort
     *
     * @param world The world
     * @param pos   The TE pos
     */
    public void onRecipeComplete(World world, BlockPos pos) {}

    /**
     * Called by TEAlembic when the alembic just sealed
     * Use this if you want to do something to the input (like add a food trait) when the recipe "just started"
     *
     * You shouldn't consume the input here (use on unsealed if needed), otherwise, you're gonna keep consuming when the alembic reloads
     *
     * @param inputFluid the fluid that is in the alembic when it got sealed
     * @param inputStack the stack that is in the alembic when it got sealed
     */
    public void onAlembicSealed(FluidStack inputFluid, ItemStack inputStack) {}


    /**
     * Called by TEAlembic when the alembic is unsealed mid operation (the recipe didn't finish)
     * Use this if you want to do something to the input (like remove a food trait, or consume part of it) when the recipe is "broken"
     *
     * @param inputFluid the fluid that was in the alembic when the recipe "broke"
     * @param inputStack the stack that was in the alembic when the recipe "broke"
     * @return a fluid to replace the input when the alembic is unsealed
     */
    @Nullable
    public FluidStack getOutputFluidOnUnseal(FluidStack inputFluid, ItemStack inputStack)
    {
        return inputFluid;
    }

    /**
     * Called by TEAlembic when the alembic is unsealed mid operation (the recipe didn't finish)
     * Use this if you want to do something to the input (like remove a food trait, or consume part of it) when the recipe is "broken"
     *
     * @param inputFluid the fluid that was in the alembic when the recipe "broke"
     * @param inputStack the stack that was in the alembic when the recipe "broke"
     * @return a list of items to replace the input when the alembic is unsealed
     */
    @Nonnull
    public List<ItemStack> getOutputItemOnUnseal(FluidStack inputFluid, ItemStack inputStack)
    {
        return Helpers.listOf(inputStack);
    }

    /**
     * Gets the name of the recipe, to be displayed in the gui
     *
     * @return the name of the item stack produced, or the fluid produced, or a custom name if needed
     */
    @SideOnly(Side.CLIENT)
    public String getResultName()
    {
        ItemStack resultStack = getOutputStack();
        if (!resultStack.isEmpty())
        {
            return resultStack.getDisplayName();
        }
        else
        {
            FluidStack fluid = getOutputFluid();
            if (fluid == null)
            {
                return "???";
            }
            else
            {
                return fluid.getFluid().getLocalizedName(fluid);
            }
        }
    }
}