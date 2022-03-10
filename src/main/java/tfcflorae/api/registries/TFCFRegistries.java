package tfcflorae.api.registries;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import tfcflorae.TFCFlorae;
import tfcflorae.objects.recipes.*;

/**
 * This is where we initialize our registry instances!
 */
public class TFCFRegistries
{
    public static final IForgeRegistry<AlembicRecipe> ALEMBIC = GameRegistry.findRegistry(AlembicRecipe.class);
    public static final IForgeRegistry<DryingRecipe> DRYING = GameRegistry.findRegistry(DryingRecipe.class);
    public static final IForgeRegistry<StickBundleRecipe> STICK_BUNDLE = GameRegistry.findRegistry(StickBundleRecipe.class);

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
                    TFCFlorae.getLog().warn("[Please inform TFCF Team] Something missing? (Not a registry) {}", field);
                    continue;
                }
                if ((field.getModifiers() & publicStaticFinal) != publicStaticFinal)
                {
                    TFCFlorae.getLog().warn("[Please inform TFCF Team] Something missing? (not Public Static Final) {}", field);
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
            TFCFlorae.getLog().fatal("Fatal error! This is likely a programming mistake.", e);
            throw new RuntimeException(e);
        }
    }
}