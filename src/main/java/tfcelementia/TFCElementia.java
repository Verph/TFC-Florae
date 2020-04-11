package tfcelementia;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.server.FMLServerHandler;

import net.dries007.tfc.api.capability.damage.CapabilityDamageResistance;
import net.dries007.tfc.api.capability.egg.CapabilityEgg;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.FoodHandler;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.size.CapabilityItemSize;
import net.dries007.tfc.client.ClientEvents;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.client.TFCKeybindings;
import net.dries007.tfc.client.gui.overlay.PlayerDataOverlay;
import net.dries007.tfc.command.*;
import net.dries007.tfc.network.*;
import net.dries007.tfc.objects.LootTablesTFC;
import net.dries007.tfc.objects.entity.EntitiesTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.proxy.IProxy;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.fuel.FuelManager;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.chunkdata.CapabilityChunkData;
import net.dries007.tfc.world.classic.worldgen.vein.VeinRegistry;
import net.dries007.tfc.util.OreDictionaryHelper;
import tfcelementia.api.capability.food.CapabilityFoodTFCE;
import tfcelementia.api.capability.food.FoodHandlerTFCE;
import tfcelementia.objects.items.ItemsTFCE;
//import tfcelementia.util.OreDictionaryHelper;
import tfcelementia.util.VeinLoader;

import static tfcelementia.TFCElementia.MODID;

@SuppressWarnings("WeakerAccess")
@Mod(modid = TFCElementia.MODID, name = TFCElementia.NAME, version = TFCElementia.VERSION, dependencies = TFCElementia.DEPENDENCIES, certificateFingerprint = TFCElementia.SIGNING_KEY)
public class TFCElementia
{
    public static final String MODID = "tfcelementia";
    public static final String NAME = "TFC Elementia";
    public static final String VERSION = "@VERSION@";
    public static final String SIGNING_KEY = "@FINGERPRINT@";
    public static final String DEPENDENCIES = "required-after:tfc@[0.30.3,)";

    @Mod.Instance
    private static TFCElementia instance = null;
    private static Logger logger;
    private static boolean signedBuild = true;

    public static Logger getLog()
    {
        return logger;
    }

    public static TFCElementia getInstance()
    {
        return instance;
    }
    
    @EventHandler
    public void onFingerprintViolation(FMLFingerprintViolationEvent event)
    {
        /*if (!event.isDirectory())
        {
            signedBuild = false; // todo disabled for the time being
        }*/
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        VeinLoader.INSTANCE.preInit(event.getModConfigurationDirectory());
        if (!signedBuild)
        {
            logger.error("INVALID FINGERPRINT DETECTED! This means this jar file has been compromised and is not supported.");
        }

        CapabilityFoodTFCE.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	ItemsTFCE.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event)
    {
        // This is the latest point that we can possibly stop creating non-decaying stacks on both server + client
        // It should be safe to use as we're only using it internally
        FoodHandlerTFCE.setNonDecaying(false);
    }
}
