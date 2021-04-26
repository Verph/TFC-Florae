package tfcflorae.proxy;

import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import tfcflorae.ConfigTFCF;
import tfcflorae.world.worldgen.*;
import tfcflorae.world.worldgen.structures.WorldGenStructures;

@Mod.EventBusSubscriber
public class CommonProxy 
{
    public void preInit(FMLPreInitializationEvent event) 
    {
        if (ConfigTFCF.General.STRUCTURES.activateStructureGeneration)
        {
    	    GameRegistry.registerWorldGenerator(new WorldGenStructures(), 0);
        }
        if (ConfigTFCF.General.WORLD.enableAllWorldGen)
        {
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
