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

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.blocks.BlockPlacedHide.SIZE;
import static net.dries007.tfc.objects.blocks.agriculture.BlockCropDead.MATURE;
import static net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC.WILD;

import tfcelementia.TFCElementia;
import tfcelementia.api.capability.food.IFoodTFCE;
import tfcelementia.api.capability.food.CapabilityFoodTFCE;
import tfcelementia.objects.GemTFCE;
import tfcelementia.objects.blocks.agriculture.BlockFruitTreeLeavesTFCE;
import tfcelementia.objects.blocks.BlocksTFCE;
import tfcelementia.objects.items.ItemGemTFCE;
import tfcelementia.objects.items.metal.ItemMetalTFCE;
import tfcelementia.util.ItemsTFCE;

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
		
		for (Block block : BlocksTFCE.getAllCropBlocks())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(WILD).build());

        for (Block block : BlocksTFCE.getAllDeadCropBlocks())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(MATURE).build());

        for (Block block : BlocksTFCE.getAllFruitTreeLeavesBlocks())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockFruitTreeLeavesTFCE.DECAYABLE).ignore(BlockFruitTreeLeavesTFCE.HARVESTABLE).build());
        
        // Gems
        for (ItemGemTFCE item : ItemsTFCE.getAllGemTFCEItems())
            for (GemTFCE.Grade grade : GemTFCE.Grade.values())
                registerEnumBasedMetaItems("gem", grade, item);
        
        // Blocks with Ignored Properties
        for (Block block : BlocksTFCE.getAllFluidBlocks())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());

        // Metals
        for (Item item : ItemsTFCE.getAllMetalItems())
        	ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));       
    }
	
	@SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerColorHandlerBlocks(ColorHandlerEvent.Block event)
    {
		BlockColors blockColors = event.getBlockColors();

        // Grass Colors
        IBlockColor grassColor = (state, worldIn, pos, tintIndex) -> {
            if (pos != null)
            {
                double temp = MathHelper.clamp((ClimateTFC.getMonthlyTemp(pos) + 30) / 60, 0, 1);
                double rain = MathHelper.clamp((ClimateTFC.getRainfall(pos) - 50) / 400, 0, 1);
                return ColorizerGrass.getGrassColor(temp, rain);
            }
            return ColorizerGrass.getGrassColor(0.5, 0.5);
        };
		
        // Foliage Color
        // todo: do something different for conifers - they should have a different color mapping through the seasons
        IBlockColor foliageColor = (state, worldIn, pos, tintIndex) -> {
            if (pos != null)
            {
                double temp = MathHelper.clamp((ClimateTFC.getMonthlyTemp(pos) + 30) / 60, 0, 1);
                double rain = MathHelper.clamp((ClimateTFC.getRainfall(pos) - 50) / 400, 0, 1);
                return ColorizerGrass.getGrassColor(temp, rain);
            }
            return ColorizerGrass.getGrassColor(0.5, 0.5);
        };
        
        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCE.getAllFruitTreeLeavesBlocks().toArray(new Block[0]));
        
		blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
        	worldIn != null && pos != null ? BiomeColorHelper.getWaterColorAtPos(worldIn, pos) : 0,
        		BlocksTFCE.getAllFluidBlocks().stream().filter(x -> x.getDefaultState().getMaterial() == Material.WATER).toArray(BlockFluidBase[]::new));
    }
	
	@SubscribeEvent
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public static void registerColorHandlerItems(ColorHandlerEvent.Item event)
    {
		ItemColors itemColors = event.getItemColors();

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCE.getAllFruitTreeLeavesBlocks().toArray(new BlockFruitTreeLeavesTFCE[0]));
        
		itemColors.registerItemColorHandler((stack, tintIndex) -> {
            IFoodTFCE food = stack.getCapability(CapabilityFoodTFCE.CAPABILITY, null);
            if (food != null)
            {
                return food.isRotten() ? ConfigTFC.CLIENT.rottenFoodOverlayColor : 0xFFFFFF;
            }
            return 0xFFFFFF;
        }, ForgeRegistries.ITEMS.getValuesCollection().stream().filter(x -> x instanceof ItemFood).toArray(Item[]::new));
    }
	
    @SideOnly(Side.CLIENT)
    private static void registerEnumBasedMetaItems(String prefix, Enum e, Item item)
    {
        //noinspection ConstantConditions
        String registryName = item.getRegistryName().getPath();
        StringBuilder path = new StringBuilder(MOD_ID).append(':');
        if (!Strings.isNullOrEmpty(prefix)) path.append(prefix).append('/');
        path.append(e.name());
        if (!Strings.isNullOrEmpty(prefix))
            path.append(registryName.replace(prefix, "")); // There well be a '/' at the start of registryName due to the prefix, so don't add an extra one.
        else path.append('/').append(registryName);
        ModelLoader.setCustomModelResourceLocation(item, e.ordinal(), new ModelResourceLocation(path.toString().toLowerCase()));
    }
}
