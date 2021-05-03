package tfcflorae.util.fuel;

import tfcflorae.objects.blocks.wood.BlockLogTFCF;
import tfcflorae.types.TreesTFCF;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.fuel.Fuel;
import net.dries007.tfc.util.fuel.FuelManager;

public class FuelsTFCF
{
    public static void postInit()
    {
        // Eucalyptus
        FuelManager.addFuel(new Fuel(IIngredient.of(BlockLogTFCF.get(TFCRegistries.TREES.getValue(TreesTFCF.EUCALYPTUS))), 1000, 705f));
        FuelManager.addFuel(new Fuel(IIngredient.of("logWoodEucalyptus"), 1000, 705f));

        // Wood
        FuelManager.addFuel(new Fuel(IIngredient.of("driftwood"), 600, 900));
        FuelManager.addFuel(new Fuel(IIngredient.of("twig"), 400, 600));
        FuelManager.addFuel(new Fuel(IIngredient.of("pinecone"), 200, 300));
    }
}