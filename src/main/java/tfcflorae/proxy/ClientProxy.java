package tfcflorae.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tfcflorae.objects.blocks.BlocksTFCF;
//import tfcflorae.objects.blocks.BlocksTypeTFCF;
import tfcflorae.TFCFlorae;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy 
{
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        /*
        for(Block block : BlocksTypeTFCF.getNormalBlocks())
        {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "normal"));
        }
        for(Block block : BlocksTypeTFCF.getInventoryBlocks())
        {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        }
        */
    }
}