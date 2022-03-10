package tfcflorae.types;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import tfcflorae.objects.recipes.*;

import static tfcflorae.TFCFlorae.MODID;
import static tfcflorae.api.registries.TFCFRegistryNames.*;

@Mod.EventBusSubscriber(modid = MODID)
@GameRegistry.ObjectHolder(MODID)
public final class RegistriesTFCF
{
    private static final Map<ResourceLocation, IForgeRegistry<?>> preBlockRegistries = new LinkedHashMap<>(); // Needs to respect insertion order

    @SubscribeEvent
    public static void onNewRegistryEvent(RegistryEvent.NewRegistry event)
    {
        newRegistry(ALEMBIC_RECIPE, AlembicRecipe.class, false);
        newRegistry(DRYING_RECIPE, DryingRecipe.class, false);
        newRegistry(STICK_BUNDLE_RECIPE, StickBundleRecipe.class, false);
    }

    private static <T extends IForgeRegistryEntry<T>> void newRegistry(ResourceLocation name, Class<T> tClass, boolean isPreBlockRegistry)
    {
        IForgeRegistry<T> reg = new RegistryBuilder<T>().setName(name).allowModification().setType(tClass).create();
        if (isPreBlockRegistry)
        {
            preBlockRegistries.put(name, reg);
        }
    }
}
