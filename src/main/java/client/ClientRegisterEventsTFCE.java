package client;

import java.util.Arrays;
import javax.annotation.Nonnull;

import net.minecraft.block.*;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.blocks.BlockPlacedHide.SIZE;
import static net.dries007.tfc.objects.blocks.agriculture.BlockCropDead.MATURE;
import static net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC.WILD;

import tfcelementia.objects.blocks.BlocksTFCE;
import tfcelementia.TFCElementia;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TFCElementia.MODID)
public final class ClientRegisterEventsTFCE 
{
	@SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerModels(ModelRegistryEvent event)
    {
		for (Block block : BlocksTFCE.getAllCropBlocks())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(WILD).build());

        for (Block block : BlocksTFCE.getAllDeadCropBlocks())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(MATURE).build());
    }
}
