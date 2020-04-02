package tfcelementia.api.registries;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.recipes.*;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.quern.QuernRecipe;
//import net.dries007.tfc.api.types.*;

import tfcelementia.TFCElementia;
import tfcelementia.api.types.*;

/**
 * Get Registry instances for standard TFC objects here.
 */
public class TFCERegistries
{
    /**
     * To developers: If you are considering creating one of these pre-block registries, take a minute to ask "is this the best idea"
     * i.e create an interface + enum, so addons register their own instances of TFC block classes using a custom implementation of the enum
     * - AlcatrazEscapee
     */
    public static final IForgeRegistry<RockTFCE> ROCKS = GameRegistry.findRegistry(RockTFCE.class);
        public static final IForgeRegistry<RockCategoryTFCE> ROCK_CATEGORIES = GameRegistry.findRegistry(RockCategoryTFCE.class);
    /*
    public static final IForgeRegistry<Ore> ORES = GameRegistry.findRegistry(Ore.class);
    public static final IForgeRegistry<Tree> TREES = GameRegistry.findRegistry(Tree.class);
    public static final IForgeRegistry<Metal> METALS = GameRegistry.findRegistry(Metal.class);

    public static final IForgeRegistry<Plant> PLANTS = GameRegistry.findRegistry(Plant.class);
    */
    
    static
    {
        // Make sure all public static final fields have values, should stop people from prematurely loading this class.
        try
        {
            int publicStaticFinal = Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL;

            for (Field field : TFCERegistries.class.getFields())
            {
                if (!field.getType().isAssignableFrom(IForgeRegistry.class))
                {
                	TFCElementia.getLog().warn("[Please inform developers] Weird field? (Not a registry) {}", field);
                    continue;
                }
                if ((field.getModifiers() & publicStaticFinal) != publicStaticFinal)
                {
                	TFCElementia.getLog().warn("[Please inform developers] Weird field? (not Public Static Final) {}", field);
                    continue;
                }
                if (field.get(null) == null)
                {
                    throw new RuntimeException("Oh nooo! Someone tried to use the registries before they exist. Now everything is broken!");
                }
            }
        }
        catch (Exception e)
        {
        	TFCElementia.getLog().fatal("Fatal error! This is likely a programming mistake.", e);
            throw new RuntimeException(e);
        }
    }
}