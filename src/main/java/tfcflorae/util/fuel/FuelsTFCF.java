package tfcflorae.util.fuel;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.fuel.Fuel;
import net.dries007.tfc.util.fuel.FuelManager;

import tfcflorae.objects.blocks.wood.BlockLogTFCF;
import tfcflorae.types.TreesTFCF;
import tfcflorae.util.agriculture.SeasonalTrees;

public class FuelsTFCF
{
    public static void postInit()
    {
        for (Tree wood : TFCRegistries.TREES.getValuesCollection())
        {
            BlockLogTFCF log = BlockLogTFCF.get(wood);
            FuelManager.addFuel(new Fuel(IIngredient.of(new ItemStack(log)), wood.getBurnTicks(), wood.getBurnTemp()));
        }

        for (SeasonalTrees tree : SeasonalTrees.values())
        {
            BlockLogTFCF log = BlockLogTFCF.get(tree);
            FuelManager.addFuel(new Fuel(IIngredient.of(new ItemStack(log)), tree.normalTree.getBurnTicks(), tree.normalTree.getBurnTemp()));
        }

        // Eucalyptus
        // FuelManager.addFuel(new Fuel(IIngredient.of(new ItemStack(BlockLogTFCF.get(TFCRegistries.TREES.getValue(TreesTFCF.EUCALYPTUS)))), 1000, 705, false, false));
        // FuelManager.addFuel(new Fuel(IIngredient.of("logWoodEucalyptus"), 1000, 705, false, false));

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