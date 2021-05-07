package tfcflorae.util.fuel;

import tfcflorae.objects.blocks.wood.BlockLogTFCF;
import tfcflorae.types.TreesTFCF;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.fuel.Fuel;
import net.dries007.tfc.util.fuel.FuelManager;
import net.minecraft.item.ItemStack;

public class FuelsTFCF
{
    public static void postInit()
    {
        // Eucalyptus
        FuelManager.addFuel(new Fuel(IIngredient.of(new ItemStack(BlockLogTFCF.get(TFCRegistries.TREES.getValue(TreesTFCF.EUCALYPTUS)))), 1000, 705, false, false));
        FuelManager.addFuel(new Fuel(IIngredient.of("logWoodEucalyptus"), 1000, 705, false, false));

        // Wood
        FuelManager.addFuel(new Fuel(IIngredient.of("poleWooden"), 900, 700));
        FuelManager.addFuel(new Fuel(IIngredient.of("driftwood"), 750, 650));
        FuelManager.addFuel(new Fuel(IIngredient.of("twig"), 400, 550));
        FuelManager.addFuel(new Fuel(IIngredient.of("bamboo"), 450, 550));
        FuelManager.addFuel(new Fuel(IIngredient.of("pinecone"), 200, 300));
        FuelManager.addFuel(new Fuel(IIngredient.of("lumberFirewood"), 1000, 700, true, false));
        FuelManager.addFuel(new Fuel(IIngredient.of("woodFirewood"), 1000, 700, true, false));
        FuelManager.addFuel(new Fuel(IIngredient.of("itemFirewood"), 1000, 700, true, false));
        FuelManager.addFuel(new Fuel(IIngredient.of("fuelFirewood"), 1000, 700, true, false));
        FuelManager.addFuel(new Fuel(IIngredient.of("firewood"), 1000, 700, true, false));
    }
}