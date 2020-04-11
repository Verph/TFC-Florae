package tfcelementia.api.registries;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.DumbStorage;
import net.dries007.tfc.api.recipes.*;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.quern.QuernRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.*;

import tfcelementia.TFCElementia;
import tfcelementia.api.capability.food.FoodHandlerTFCE;
import tfcelementia.api.capability.food.IFoodTFCE;
import tfcelementia.api.types.*;

/**
 * Get Registry instances for standard TFC objects here.
 */
public class TFCERegistries extends TFCRegistries
{
    /**
     * To developers: If you are considering creating one of these pre-block registries, take a minute to ask "is this the best idea"
     * i.e create an interface + enum, so addons register their own instances of TFC block classes using a custom implementation of the enum
     * - AlcatrazEscapee
     */
    public static final IForgeRegistry<RockTFCE> ROCKS_TFCE = GameRegistry.findRegistry(RockTFCE.class);
    public static final IForgeRegistry<RockCategoryTFCE> ROCK_CATEGORIES_TFCE = GameRegistry.findRegistry(RockCategoryTFCE.class);
}