package tfcflorae.client;

import com.google.common.base.Strings;

import net.minecraft.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockStem;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.objects.blocks.BlockSlabTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropDead;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.client.GrassColorHandler;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves;
import net.dries007.tfc.objects.blocks.wood.BlockSaplingTFC;

import tfcflorae.objects.blocks.BlockStemCrop;
import tfcflorae.objects.blocks.BlockSurfaceOreDeposit;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.blocktype.BlockRockRawTFCF;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.objects.blocks.blocktype.BlockSlabTFCF;
import tfcflorae.objects.blocks.fruitwood.*;
import tfcflorae.objects.blocks.wood.BlockFenceGateLog;
import tfcflorae.objects.items.ItemFruitDoor;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.objects.items.ceramics.ItemKaoliniteMold;
import tfcflorae.client.render.VanillaStemStateMapper;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;
import tfcflorae.TFCFlorae;
import tfcflorae.api.stateproperty.StatePropertiesTFCF;
import tfcflorae.objects.GemTFCF;
import tfcflorae.objects.items.ItemGemTFCF;

import static net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC.WILD;
import static tfcflorae.TFCFlorae.MODID;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = {Side.CLIENT}, modid = TFCFlorae.MODID)
public class ClientRegisterEventsTFCF 
{
    public ClientRegisterEventsTFCF() {}

	@SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerModels(ModelRegistryEvent event)
    {
        // ITEMS & BLOCKS //
        
        ModelLoader.setCustomModelResourceLocation(ItemsTFCF.FIRED_KAOLINITE_JUG, 0, new ModelResourceLocation(ItemsTFCF.FIRED_KAOLINITE_JUG.getRegistryName(), "inventory"));

		// Simple Items
        for (Item item : ItemsTFCF.getAllSimpleItems())
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));

        // Gems
        for (ItemGemTFCF item : ItemsTFCF.getAllGemTFCFItems())
            for (GemTFCF.Grade grade : GemTFCF.Grade.values())
                registerEnumBasedMetaItems("gem", grade, item);

        //Setting the model resource location for items
        /*for (Item i : ItemsTFCF.getAllSimpleItems())
            ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation(i.getRegistryName().toString()));*/

        for (ItemFruitDoor i : ItemsTFCF.getAllFruitDoors())
            ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation(i.getRegistryName().toString()));

        // Dye color Items
        for (EnumDyeColor color : EnumDyeColor.values())
        {
            ModelLoader.setCustomModelResourceLocation(ItemsTFCF.UNFIRED_KAOLINITE_VESSEL_GLAZED, color.getDyeDamage(), new ModelResourceLocation(ItemsTFCF.UNFIRED_KAOLINITE_VESSEL_GLAZED.getRegistryName().toString()));
            ModelLoader.setCustomModelResourceLocation(ItemsTFCF.FIRED_KAOLINITE_VESSEL_GLAZED, color.getDyeDamage(), new ModelResourceLocation(ItemsTFCF.FIRED_KAOLINITE_VESSEL_GLAZED.getRegistryName().toString()));
        }

        for (ItemBlock ib : BlocksTFCF.getAllNormalItemBlocks())
            ModelLoader.setCustomModelResourceLocation(ib, 0, new ModelResourceLocation(ib.getRegistryName().toString()));

        for (BlockFruitTreeLeaves leaves : BlocksTFCF.getAllFruitLeaves())
            ModelLoader.setCustomStateMapper(leaves, new StateMap.Builder().ignore(BlockFruitTreeLeaves.DECAYABLE).ignore(BlockFruitTreeLeaves.HARVESTABLE).build());

        ModelLoader.setCustomModelResourceLocation(ItemsTFCF.CRACKED_COCONUT, 0, new ModelResourceLocation(ItemsTFCF.CRACKED_COCONUT.getRegistryName(), "inventory"));

        //Configuring block states to ignore certain properties / use others
        //use vanilla stem rendering for StemCrops
        for (BlockStemCrop block : BlocksTFCF.getAllCropBlocks())
            ModelLoader.setCustomStateMapper(block, new VanillaStemStateMapper());

        for (Block block : BlocksTFCF.getAllCropBlocksTFC())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(WILD).build());

        for (BlockFruitDoor door : BlocksTFCF.getAllFruitDoors())
            ModelLoader.setCustomStateMapper(door, new StateMap.Builder().ignore(BlockDoor.POWERED).build());

        for (BlockFruitFenceGate gate : BlocksTFCF.getAllFruitFenceGates())
            ModelLoader.setCustomStateMapper(gate, new StateMap.Builder().ignore(BlockFruitFenceGate.POWERED).build());

        for (BlockFruitLogFenceGate gate : BlocksTFCF.getAllFruitLogFenceGates())
            ModelLoader.setCustomStateMapper(gate, new StateMap.Builder().ignore(BlockFruitLogFenceGate.POWERED).build());

        for (BlockFenceGateLog gate : BlocksTFCF.getAllFenceGateLogBlocks())
            ModelLoader.setCustomStateMapper(gate, new StateMap.Builder().ignore(BlockFenceGateLog.POWERED).build());

        for (Block block : BlocksTFCF.getAllWallBlocks())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockWall.VARIANT).build());

        for (BlockSlabTFCF.Half block : BlocksTFCF.getAllSlabBlocks())
        {
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockSlabTFCF.VARIANT).build());
            ModelLoader.setCustomStateMapper(block.doubleSlab, new StateMap.Builder().ignore(BlockSlabTFCF.VARIANT).build());
        }

        for (BlockFruitSlab.Half block : BlocksTFCF.getAllFruitSlabBlocks())
        {
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockFruitSlab.VARIANT).build());
            ModelLoader.setCustomStateMapper(block.doubleSlab, new StateMap.Builder().ignore(BlockFruitSlab.VARIANT).build());
        }

        for (BlockSlabTFC.Half block : BlocksTFCF.getAllSlabBlocksTFC())
        {
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockSlabTFC.VARIANT).build());
            ModelLoader.setCustomStateMapper(block.doubleSlab, new StateMap.Builder().ignore(BlockSlabTFC.VARIANT).build());
        }

        for (Block block : BlocksTFCF.getAllSurfaceOreBlocks())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockSurfaceOreDeposit.GRADE).build());

        for (Block block : BlocksTFCF.getAllCropBlocks())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(WILD).build());

        for (Block block : BlocksTFCF.getAllFluidBlocks())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());

        for (Block block : BlocksTFCF.getAllBambooLog())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(StatePropertiesTFCF.CAN_GROW).build());

        for (Block block : BlocksTFCF.getAllBambooLeaves())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE).build());

        for (Block block : BlocksTFCF.getAllBambooSapling())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockSaplingTFC.STAGE).build());

        ModelLoader.setCustomStateMapper(BlocksTFCF.CASSIA_CINNAMON_LOG, new StateMap.Builder().ignore(StatePropertiesTFCF.CAN_GROW).build());
        ModelLoader.setCustomStateMapper(BlocksTFCF.CASSIA_CINNAMON_LEAVES, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(BlocksTFCF.CASSIA_CINNAMON_SAPLING, new StateMap.Builder().ignore(BlockSaplingTFC.STAGE).build());

        ModelLoader.setCustomStateMapper(BlocksTFCF.CEYLON_CINNAMON_LOG, new StateMap.Builder().ignore(StatePropertiesTFCF.CAN_GROW).build());
        ModelLoader.setCustomStateMapper(BlocksTFCF.CEYLON_CINNAMON_LEAVES, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(BlocksTFCF.CEYLON_CINNAMON_SAPLING, new StateMap.Builder().ignore(BlockSaplingTFC.STAGE).build());

        BlocksTFCF.getAllBlockRockVariantsTFCF().forEach(e -> {
            if (e.getType() == RockTFCF.MOSSY_RAW)
            {
                ModelLoader.setCustomStateMapper(e, new StateMap.Builder().ignore(BlockRockRawTFCF.CAN_FALL).build());
            }
        });

        // Ceramic Kaolinite Molds
        ModelBakery.registerItemVariants(ItemKaoliniteMold.get(Metal.ItemType.INGOT), new ModelResourceLocation(ItemKaoliniteMold.get(Metal.ItemType.INGOT).getRegistryName() + "/unknown"));
        for (Metal.ItemType value : Metal.ItemType.values())
        {
            ItemKaoliniteMold item = ItemKaoliniteMold.get(value);
            if (item == null) continue;

            ModelBakery.registerItemVariants(item, new ModelResourceLocation(item.getRegistryName().toString() + "/empty"));
            ModelBakery.registerItemVariants(item, TFCRegistries.METALS.getValuesCollection()
                .stream()
                .filter(value::hasMold)
                .map(x -> new ModelResourceLocation(item.getRegistryName().toString() + "/" + x.getRegistryName().getPath()))
                .toArray(ModelResourceLocation[]::new));
            ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
            {
                private final ModelResourceLocation FALLBACK = new ModelResourceLocation(item.getRegistryName().toString() + "/empty");

                @Override
                @Nonnull
                public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack)
                {
                    IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                    if (cap instanceof IMoldHandler)
                    {
                        Metal metal = ((IMoldHandler) cap).getMetal();
                        if (metal != null)
                        {
                            return new ModelResourceLocation(stack.getItem().getRegistryName() + "/" + metal.getRegistryName().getPath());
                        }
                    }
                    return FALLBACK;
                }
            });
        }
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerColorHandlerItems(ColorHandlerEvent.Item event)
    {
        ItemColors itemColors = event.getItemColors();

        itemColors.registerItemColorHandler((stack, tintIndex) -> tintIndex == 1 ? EnumDyeColor.byDyeDamage(stack.getItemDamage()).getColorValue() : 0xFFFFFF,
            ItemsTFCF.UNFIRED_KAOLINITE_VESSEL_GLAZED, ItemsTFCF.FIRED_KAOLINITE_VESSEL_GLAZED);

        /*
        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTypeTFCF.getAllBlockRockVariantsTFCF().stream().filter(x -> x.getType().isGrass).toArray(BlockRockVariantTFCF[]::new));
        */

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllBlockRockVariantsTFCF().stream().filter(x -> x.getType().isGrass).toArray(BlockRockVariantTFCF[]::new));

        /*
        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllGrassBlocks().toArray(new BlockPlantTFCF[0]));
        */

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllFruitLeaves().toArray(new BlockFruitTreeLeaves[0])
        );

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.CASSIA_CINNAMON_LEAVES);

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.CEYLON_CINNAMON_LEAVES);
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

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerColorHandlerBlocks(ColorHandlerEvent.Block event)
    {
        BlockColors blockColors = event.getBlockColors();
        IBlockColor grassColor = GrassColorHandler::computeGrassColor;
        IBlockColor foliageColor = GrassColorHandler::computeGrassColor;

        //blockColors.registerBlockColorHandler(grassColor, BlocksTypeTFCF.getAllBlockRockVariantsTFCF().stream().filter(x -> x.getType().isGrass).toArray(BlockRockVariantTFCF[]::new));
        blockColors.registerBlockColorHandler(grassColor, BlocksTFCF.getAllBlockRockVariantsTFCF().stream().filter(x -> x.getType().isGrass).toArray(BlockRockVariantTFCF[]::new));
        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllFruitLeaves().toArray(new Block[0]));

        // This is talking about tall grass vs actual grass blocks
        //blockColors.registerBlockColorHandler(grassColor, BlocksTFCF.getAllGrassBlocks().toArray(new BlockPlantTFCF[0]));
        //blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllPlantBlocks().toArray(new BlockPlantTFCF[0]));

        //use vanilla stem coloring for stemcrops
        for (BlockStemCrop block : BlocksTFCF.getAllCropBlocks())
        {
            blockColors.registerBlockColorHandler((state, world, pos, tintIndex) ->
            {
                int vanillaAge = VanillaStemStateMapper.getVanillaAge(state);
                if (vanillaAge == -1)
                    vanillaAge = 7; //for fully grown, we color it like stage 7
                return blockColors.colorMultiplier(Blocks.MELON_STEM.getDefaultState().withProperty(BlockStem.AGE, vanillaAge), world, pos, tintIndex);
            }, block);
        }

        for (BlockCropDead block : BlocksTFCF.getAllDeadCrops())
            blockColors.registerBlockColorHandler((state, world, os, tintIndex) -> 0xCC7400, block);
            
        for (Block block : BlocksTFCF.getAllBambooLeaves())
            blockColors.registerBlockColorHandler(foliageColor, block);

        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.CASSIA_CINNAMON_LEAVES);
        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.CEYLON_CINNAMON_LEAVES);
        //blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.ARROW_BAMBOO_LEAVES);
    }
}
