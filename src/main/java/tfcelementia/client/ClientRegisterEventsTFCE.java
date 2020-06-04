package tfcelementia.client;

import java.util.Arrays;
import javax.annotation.Nonnull;

import com.google.common.base.Strings;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.*;
import net.minecraft.item.ItemFood;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.util.climate.ClimateTFC;

import tfcelementia.TFCElementia;
import tfcelementia.objects.GemTFCE;
import tfcelementia.objects.items.ItemGemTFCE;
import tfcelementia.objects.items.ItemsTFCE;
import tfcelementia.objects.items.metal.ItemMetalTFCE;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.blocks.BlockPlacedHide.SIZE;
import static net.dries007.tfc.objects.blocks.agriculture.BlockCropDead.MATURE;
import static net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC.WILD;

import static tfcelementia.TFCElementia.MODID;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TFCElementia.MODID)
public final class ClientRegisterEventsTFCE 
{
	@SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerModels(ModelRegistryEvent event)
    {
		// ITEMS //
		
		// Simple Items
        for (Item item : ItemsTFCE.getAllSimpleItems())
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));
        
        // Gems
        for (ItemGemTFCE item : ItemsTFCE.getAllGemTFCEItems())
            for (GemTFCE.Grade grade : GemTFCE.Grade.values())
                registerEnumBasedMetaItems("gem", grade, item);
        
        // Metals
        for (Item item : ItemsTFCE.getAllMetalItems())
        	ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));       
    }
	
    @SideOnly(Side.CLIENT)
    private static void registerEnumBasedMetaItems(String prefix, Enum e, Item item)
    {
        //noinspection ConstantConditions
        String registryName = item.getRegistryName().getPath();
        StringBuilder path = new StringBuilder(MODID).append(':');
        if (!Strings.isNullOrEmpty(prefix)) path.append(prefix).append('/');
        path.append(e.name());
        if (!Strings.isNullOrEmpty(prefix))
            path.append(registryName.replace(prefix, "")); // There well be a '/' at the start of registryName due to the prefix, so don't add an extra one.
        else path.append('/').append(registryName);
        ModelLoader.setCustomModelResourceLocation(item, e.ordinal(), new ModelResourceLocation(path.toString().toLowerCase()));
    }
}
