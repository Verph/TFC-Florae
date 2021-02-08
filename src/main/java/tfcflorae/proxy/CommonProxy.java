package tfcflorae.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import tfcflorae.world.worldgen.WorldGenSoilPitsTFCF;
import tfcflorae.world.worldgen.WorldGenSurfaceBones;
import tfcflorae.world.worldgen.WorldGenSurfaceFlint;
import tfcflorae.world.worldgen.WorldGenSurfaceOreDeposits;
import tfcflorae.world.worldgen.WorldGenSurfaceRocks;
import tfcflorae.world.worldgen.WorldGenSurfaceSeashells;
import tfcflorae.world.worldgen.WorldGeneratorTFCF;

@Mod.EventBusSubscriber
public class CommonProxy 
{
    public void preInit(FMLPreInitializationEvent e) 
    {
        GameRegistry.registerWorldGenerator(new WorldGeneratorTFCF(), 0);
        GameRegistry.registerWorldGenerator(new WorldGenSoilPitsTFCF(), 0);
        GameRegistry.registerWorldGenerator(new WorldGenSurfaceRocks(), 0);
        GameRegistry.registerWorldGenerator(new WorldGenSurfaceSeashells(), 0);
        GameRegistry.registerWorldGenerator(new WorldGenSurfaceFlint(), 0);
        GameRegistry.registerWorldGenerator(new WorldGenSurfaceBones(), 0);
        GameRegistry.registerWorldGenerator(new WorldGenSurfaceOreDeposits(true), 0);
    }

    public void init(FMLInitializationEvent e) 
    {
    }

    public void postInit(FMLPostInitializationEvent e) 
    {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) 
    {
    }
}
