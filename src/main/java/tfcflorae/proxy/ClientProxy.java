package tfcflorae.proxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy 
{
    @Override
    public void preInit(FMLPreInitializationEvent event) 
    {
        super.preInit(event);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void postInit(FMLPostInitializationEvent event) 
    {
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) 
    {
    }
}