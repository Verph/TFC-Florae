package tfcflorae.types;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import tfcflorae.api.recipes.*;
import tfcflorae.api.registries.*;

import static tfcflorae.TFCFlorae.MODID;

@Mod.EventBusSubscriber(modid = MODID)
@GameRegistry.ObjectHolder(MODID)
public class RegistriesTFCF
{
    @SubscribeEvent
    public static void onNewRegistryEvent(RegistryEvent.NewRegistry event)
    {
        newRegistry(TFCFRegistryNames.NUT_TREES_REGISTRY, NutRecipe.class);
        newRegistry(TFCFRegistryNames.CRACKING_RECIPE, CrackingRecipe.class);
    }

    private static <T extends IForgeRegistryEntry<T>> void newRegistry(ResourceLocation name, Class<T> tClass)
    {
        IForgeRegistry<T> reg = new RegistryBuilder<T>().setName(name).allowModification().setType(tClass).create();
    }
}