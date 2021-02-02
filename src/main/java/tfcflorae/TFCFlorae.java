package tfcflorae;

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
import tfcflorae.client.GuiHandler;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.proxy.CommonProxy;
import tfcflorae.util.HelpersTFCF;
import tfcflorae.proxy.ClientProxy;

import static tfcflorae.TFCFlorae.MODID;

@SuppressWarnings({ "WeakerAccess", "unused" })
@Mod(modid = TFCFlorae.MODID, name = TFCFlorae.NAME, version = TFCFlorae.VERSION, dependencies = TFCFlorae.DEPENDENCIES, certificateFingerprint = TFCFlorae.SIGNING_KEY)
public class TFCFlorae
{
    public static final String MODID = "tfcflorae";
    public static final String NAME = "TFC Florae";
    public static final String VERSION = "@VERSION@";
    public static final String SIGNING_KEY = "@FINGERPRINT@";
    public static final String DEPENDENCIES = "required-after:tfc@[1.0,)";

    @Mod.Instance
    public static TFCFlorae instance;
    public static Logger logger;
    public static boolean signedBuild = true;

    @SidedProxy(serverSide = "tfcflorae.proxy.CommonProxy", clientSide = "tfcflorae.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static Logger getLog()
    {
        return logger;
    }

    public static TFCFlorae getInstance()
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
        if (!signedBuild)
        {
            logger.error("INVALID FINGERPRINT DETECTED!");
        }

        proxy.preInit(event);

        HelpersTFCF.insertWhitelistFluids();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event)
    {
        // This is the latest point that we can possibly stop creating non-decaying stacks on both server + client
        // It should be safe to use as we're only using it internally
    }
}
