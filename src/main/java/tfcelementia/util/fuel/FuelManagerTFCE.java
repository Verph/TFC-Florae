package tfcelementia.util.fuel;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.fuel.Fuel;

public final class FuelManagerTFCE
{
    private static final List<Fuel> FUELS = new ArrayList<>();
    private static final Fuel EMPTY = new Fuel(IIngredient.empty(), 0, 0);

    @Nonnull
    public static Fuel getFuel(ItemStack stack)
    {
        return FUELS.stream().filter(x -> x.matchesInput(stack)).findFirst().orElse(EMPTY);
    }

    public static boolean isItemFuel(ItemStack stack)
    {
        return getFuel(stack) != EMPTY;
    }

    public static boolean isItemForgeFuel(ItemStack stack)
    {
        Fuel fuel = getFuel(stack);
        return fuel != EMPTY && fuel.isForgeFuel();
    }

    public static boolean isItemBloomeryFuel(ItemStack stack)
    {
        Fuel fuel = getFuel(stack);
        return fuel != EMPTY && fuel.isBloomeryFuel();
    }

    public static void postInit()
    {
        // Coals
        FUELS.add(new Fuel(IIngredient.of("gemAnthracite"), 2800, 2710f, true, true));
        FUELS.add(new Fuel(IIngredient.of("coalAnthracite"), 2800, 2710f, true, true));
        FUELS.add(new Fuel(IIngredient.of("itemAnthracite"), 2800, 2710f, true, true));
        FUELS.add(new Fuel(IIngredient.of("fuelAnthracite"), 2800, 2710f, true, true));
        FUELS.add(new Fuel(IIngredient.of("anthracite"), 2800, 2710f, true, true));
        FUELS.add(new Fuel(IIngredient.of("gemCoke"), 2400, 2500f, true, true));
        FUELS.add(new Fuel(IIngredient.of("coalCoke"), 2400, 2500f, true, true));
        FUELS.add(new Fuel(IIngredient.of("itemCoke"), 2400, 2500f, true, true));
        FUELS.add(new Fuel(IIngredient.of("fuelCoke"), 2400, 2500f, true, true));
        FUELS.add(new Fuel(IIngredient.of("coke"), 2400, 2500f, true, true));
        
        // Firewood
        FUELS.add(new Fuel(IIngredient.of("lumberFirewood"), 2500, 800, true, false));
        FUELS.add(new Fuel(IIngredient.of("woodFirewood"), 2500, 800, true, false));
        FUELS.add(new Fuel(IIngredient.of("itemFirewood"), 2500, 800, true, false));
        FUELS.add(new Fuel(IIngredient.of("fuelFirewood"), 2500, 800, true, false));
        FUELS.add(new Fuel(IIngredient.of("firewood"), 2500, 800, true, false));
    }

    /**
     * Register a new fuel only if the fuel is unique
     *
     * @param fuel the fuel obj to register
     */
    public static void addFuel(Fuel fuel)
    {
        if (canRegister(fuel))
        {
            FUELS.add(fuel);
        }
    }

    /**
     * Checks if this fuel can be registered
     *
     * @param fuel the fuel obj to register
     * @return true if the new fuel is unique (eg: don't have at least one itemstack that is equal to another already registered fuel)
     */
    public static boolean canRegister(Fuel fuel)
    {
        return FUELS.stream().noneMatch(x -> x.matchesInput(fuel));
    }
}