package tfcflorae.proxy;

import net.minecraft.item.Item;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import tfcflorae.ConfigTFCF;
import tfcflorae.TFCFlorae;
import tfcflorae.world.worldgen.*;
import tfcflorae.world.worldgen.structures.WorldGenStructures;
import tfcflorae.world.worldgen.structures.WorldGenStructuresCorals;

@Mod.EventBusSubscriber
public class CommonProxy 
{
    public void preInit(FMLPreInitializationEvent event) 
    {
        if (ConfigTFCF.General.STRUCTURES.activateStructureGeneration)
        {
    	    GameRegistry.registerWorldGenerator(new WorldGenStructures(), 0);
    	    GameRegistry.registerWorldGenerator(new WorldGenStructuresCorals(), 0);
        }
        if (ConfigTFCF.General.WORLD.enableAllWorldGen)
        {
            GameRegistry.registerWorldGenerator(new WorldGenCorals(), 0);
            GameRegistry.registerWorldGenerator(new WorldGenMossyRaw(), 0);
            GameRegistry.registerWorldGenerator(new WorldGeneratorPlants(), 0);
            GameRegistry.registerWorldGenerator(new WorldGeneratorUnderground(), 0);
            GameRegistry.registerWorldGenerator(new WorldGenLightstones(), 0);
            GameRegistry.registerWorldGenerator(new WorldGenGlowPlant(), 0);
            if (ConfigTFCF.General.WORLD.enableTrees)
            {
                GameRegistry.registerWorldGenerator(new WorldGeneratorTrees(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableSoilPits)
            {
                GameRegistry.registerWorldGenerator(new WorldGenSoilSurface(), 0);
                GameRegistry.registerWorldGenerator(new WorldGenSoilPitsTFCF(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverRock)
            {
                GameRegistry.registerWorldGenerator(new WorldGenSurfaceRocks(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverSeashell)
            {
                GameRegistry.registerWorldGenerator(new WorldGenSurfaceSeashells(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverFlint)
            {
                GameRegistry.registerWorldGenerator(new WorldGenSurfaceFlint(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverBones)
            {
                GameRegistry.registerWorldGenerator(new WorldGenSurfaceBones(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverOreDeposit)
            {
                GameRegistry.registerWorldGenerator(new WorldGenSurfaceOreDeposits(true), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverPinecone)
            {
                GameRegistry.registerWorldGenerator(new WorldGenSurfacePinecone(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverDriftwood)
            {
                GameRegistry.registerWorldGenerator(new WorldGenSurfaceDriftwood(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverTwig)
            {
                GameRegistry.registerWorldGenerator(new WorldGenSurfaceTwig(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGourdWorldGen && TFCFlorae.FirmaLifeAdded)
            {
                GameRegistry.registerWorldGenerator(new WorldGenGourds(), 0);
            }
        }
    }

    public void init(FMLInitializationEvent event) 
    {
    }

    public void postInit(FMLPostInitializationEvent event) 
    {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) 
    {
    }
}
