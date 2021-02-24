package tfcflorae.api.registries;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.Rock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import tfcflorae.api.recipes.CrackingRecipe;
import tfcflorae.api.recipes.NutRecipe;
import tfcflorae.api.types.PlantTFCF;

/**
 * This is where we initialize our registry instances!
 */
public class TFCFRegistries
{
    public static final IForgeRegistry<NutRecipe> NUT_TREES = GameRegistry.findRegistry(NutRecipe.class);
    public static final IForgeRegistry<CrackingRecipe> CRACKING = GameRegistry.findRegistry(CrackingRecipe.class);
    public static final IForgeRegistry<PlantTFCF> PLANTS_TFCF = GameRegistry.findRegistry(PlantTFCF.class);

    /*This is kindly hijacked from TerraFirmCraft TFCRegistries.java*/
    static
    {
        // Make sure all public static final fields have values, should stop people from prematurely loading this class.
        try
        {
            int publicStaticFinal = Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL;

            for (Field field : TFCFRegistries.class.getFields())
            {
                if (!field.getType().isAssignableFrom(IForgeRegistry.class))
                {
                    TerraFirmaCraft.getLog().warn("[Please inform TFCF Team] Something missing? (Not a registry) {}", field);
                    continue;
                }
                if ((field.getModifiers() & publicStaticFinal) != publicStaticFinal)
                {
                    TerraFirmaCraft.getLog().warn("[Please inform TFCF Team] Something missing? (not Public Static Final) {}", field);
                    continue;
                }
                if (field.get(null) == null)
                {
                    throw new RuntimeException("Ey, it doesn't exist, you idiot!");
                }
            }
        }
        catch (Exception e)
        {
            TerraFirmaCraft.getLog().fatal("Fatal error! This is likely a programming mistake.", e);
            throw new RuntimeException(e);
        }
    }
}