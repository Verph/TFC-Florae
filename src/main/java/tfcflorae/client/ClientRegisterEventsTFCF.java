package tfcflorae.client;

import java.awt.*;
import javax.annotation.Nonnull;

import com.eerussianguy.firmalife.items.ItemMetalMallet;
import com.eerussianguy.firmalife.items.ItemMetalMalletHead;
import com.eerussianguy.firmalife.items.ItemMetalMalletMold;
import com.google.common.base.Strings;

import net.minecraft.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.objects.blocks.BlockSlabTFC;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropDead;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.client.GrassColorHandler;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves;
import net.dries007.tfc.objects.blocks.wood.BlockSaplingTFC;

import tfcelementia.objects.items.metal.ItemMetalTFCE;
import tfcelementia.objects.items.metal.ItemMetalTFCE.ItemType;

import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.groundcover.*;
import tfcflorae.objects.blocks.plants.*;
import tfcflorae.objects.blocks.plants.BlockPlant.*;
import tfcflorae.objects.blocks.blocktype.BlockRockRawTFCF;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.objects.blocks.blocktype.BlockSlabTFCF;
import tfcflorae.objects.blocks.blocktype.farmland.BlockHumusFarmland;
import tfcflorae.objects.blocks.blocktype.farmland.BlockLoamFarmland;
import tfcflorae.objects.blocks.blocktype.farmland.BlockLoamySandFarmland;
import tfcflorae.objects.blocks.blocktype.farmland.BlockSandyLoamFarmland;
import tfcflorae.objects.blocks.blocktype.farmland.BlockSiltFarmland;
import tfcflorae.objects.blocks.blocktype.farmland.BlockSiltLoamFarmland;
import tfcflorae.objects.blocks.wood.fruitwood.*;
import tfcflorae.objects.blocks.wood.BlockFenceGateLog;
import tfcflorae.objects.blocks.wood.BlockJoshuaTreeSapling;
import tfcflorae.objects.blocks.wood.BlockLeavesTFCF;
import tfcflorae.objects.blocks.wood.BlockLogTFCF;
import tfcflorae.objects.items.ItemArmorTFCF;
import tfcflorae.objects.items.ItemFruitDoor;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.objects.items.ceramics.*;
import tfcflorae.objects.items.groundcover.*;
import tfcflorae.objects.te.*;
import tfcflorae.client.render.*;
import tfcflorae.compat.firmalife.ceramics.*;
import tfcflorae.compat.tfcelementia.ceramics.*;
import tfcflorae.types.PlantsTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;
import tfcflorae.ConfigTFCF;
import tfcflorae.TFCFlorae;
import tfcflorae.api.registries.TFCFRegistries;
import tfcflorae.api.stateproperty.StatePropertiesTFCF;
import tfcflorae.objects.GemTFCF;
import tfcflorae.objects.items.ItemGemTFCF;

import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
import static net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC.WILD;
import static tfcflorae.TFCFlorae.MODID;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = {Side.CLIENT}, modid = TFCFlorae.MODID)
public class ClientRegisterEventsTFCF 
{
    private final java.util.Map<net.minecraftforge.registries.IRegistryDelegate<Item>, IItemColor> itemColorMap = com.google.common.collect.Maps.newHashMap();

    public ClientRegisterEventsTFCF() {}

	@SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerModels(ModelRegistryEvent event)
    {
        // ITEMS

        if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
        {
            ModelLoader.setCustomModelResourceLocation(ItemsTFCF.FIRED_EARTHENWARE_JUG, 0, new ModelResourceLocation(ItemsTFCF.FIRED_EARTHENWARE_JUG.getRegistryName(), "inventory"));
        }
        if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
        {
            ModelLoader.setCustomModelResourceLocation(ItemsTFCF.FIRED_KAOLINITE_JUG, 0, new ModelResourceLocation(ItemsTFCF.FIRED_KAOLINITE_JUG.getRegistryName(), "inventory"));
        }
        if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
        {
            ModelLoader.setCustomModelResourceLocation(ItemsTFCF.FIRED_STONEWARE_JUG, 0, new ModelResourceLocation(ItemsTFCF.FIRED_STONEWARE_JUG.getRegistryName(), "inventory"));
        }

        for (Item item : ItemsTFCF.getAllSimpleItems())
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));

        for (ItemGemTFCF item : ItemsTFCF.getAllGemTFCFItems())
            for (GemTFCF.Grade grade : GemTFCF.Grade.values())
                registerEnumBasedMetaItems("gem", grade, item);

        /*for (Item item : ItemsTFCF.getAllItemBows())
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));*/

        for (ItemFruitDoor item : ItemsTFCF.getAllFruitDoors())
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));

        for (EnumDyeColor color : EnumDyeColor.values())
        {
            if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
            {
                ModelLoader.setCustomModelResourceLocation(ItemsTFCF.UNFIRED_EARTHENWARE_VESSEL_GLAZED, color.getDyeDamage(), new ModelResourceLocation(ItemsTFCF.UNFIRED_EARTHENWARE_VESSEL_GLAZED.getRegistryName().toString()));
                ModelLoader.setCustomModelResourceLocation(ItemsTFCF.FIRED_EARTHENWARE_VESSEL_GLAZED, color.getDyeDamage(), new ModelResourceLocation(ItemsTFCF.FIRED_EARTHENWARE_VESSEL_GLAZED.getRegistryName().toString()));
            }
            if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
            {
                ModelLoader.setCustomModelResourceLocation(ItemsTFCF.UNFIRED_KAOLINITE_VESSEL_GLAZED, color.getDyeDamage(), new ModelResourceLocation(ItemsTFCF.UNFIRED_KAOLINITE_VESSEL_GLAZED.getRegistryName().toString()));
                ModelLoader.setCustomModelResourceLocation(ItemsTFCF.FIRED_KAOLINITE_VESSEL_GLAZED, color.getDyeDamage(), new ModelResourceLocation(ItemsTFCF.FIRED_KAOLINITE_VESSEL_GLAZED.getRegistryName().toString()));
            }
            if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
            {
                ModelLoader.setCustomModelResourceLocation(ItemsTFCF.UNFIRED_STONEWARE_VESSEL_GLAZED, color.getDyeDamage(), new ModelResourceLocation(ItemsTFCF.UNFIRED_STONEWARE_VESSEL_GLAZED.getRegistryName().toString()));
                ModelLoader.setCustomModelResourceLocation(ItemsTFCF.FIRED_STONEWARE_VESSEL_GLAZED, color.getDyeDamage(), new ModelResourceLocation(ItemsTFCF.FIRED_STONEWARE_VESSEL_GLAZED.getRegistryName().toString()));
            }
        }

        for (ItemArmorTFCF item : ItemsTFCF.getAllArmorItems())
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));

        // BLOCKS

        for (ItemBlock itemBlock : BlocksTFCF.getAllItemBlockCondenser())
            ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(itemBlock.getRegistryName().toString()));

        for (ItemBlock itemBlock : BlocksTFCF.getAllNormalItemBlocks())
            ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(itemBlock.getRegistryName().toString()));

        for (Block block : BlocksTFCF.getAllCoralPlants())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockCoral.LEVEL).build());

        for (Block block : BlocksTFCF.getAllGlowWaterPlants())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockWaterGlowPlant.LEVEL).build());

        if (ConfigTFCF.General.WORLD.enableGroundcoverBones)
        {
            for (Block block : BlocksTFCF.getAllSurfaceBones())
                ModelLoader.setCustomStateMapper(block, new StateMap.Builder().build());
        }

        if (ConfigTFCF.General.WORLD.enableGroundcoverDriftwood)
        {
            for (Block block : BlocksTFCF.getAllSurfaceDriftwood())
                ModelLoader.setCustomStateMapper(block, new StateMap.Builder().build());
        }

        if (ConfigTFCF.General.WORLD.enableGroundcoverFlint)
        {
            for (Block block : BlocksTFCF.getAllSurfaceFlint())
                ModelLoader.setCustomStateMapper(block, new StateMap.Builder().build());
        }

        if (ConfigTFCF.General.WORLD.enableGroundcoverPinecone)
        {
            for (Block block : BlocksTFCF.getAllSurfacePinecone())
                ModelLoader.setCustomStateMapper(block, new StateMap.Builder().build());
        }

        if (ConfigTFCF.General.WORLD.enableGroundcoverSeashell)
        {
            for (Block block : BlocksTFCF.getAllSurfaceSeashells())
                ModelLoader.setCustomStateMapper(block, new StateMap.Builder().build());
        }

        if (ConfigTFCF.General.WORLD.enableGroundcoverTwig)
        {
            for (Block block : BlocksTFCF.getAllSurfaceTwig())
                ModelLoader.setCustomStateMapper(block, new StateMap.Builder().build());
        }

        if (ConfigTFCF.General.WORLD.enableGroundcoverRock)
        {
            for (Block block : BlocksTFCF.getAllSurfaceRocks())
                ModelLoader.setCustomStateMapper(block, new StateMap.Builder().build());
        }

        if (ConfigTFCF.General.WORLD.enableGroundcoverOreDeposit)
        {
            for (Block block : BlocksTFCF.getAllSurfaceOreBlocks())
                ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockSurfaceOreDeposit.GRADE).build());
        }

        for (Block block : BlocksTFCF.getAllJoshuaTreeSaplingBlocks())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockJoshuaTreeSapling.STAGE).build());

        for (BlockFruitTreeLeaves leaves : BlocksTFCF.getAllFruitLeaves())
            ModelLoader.setCustomStateMapper(leaves, new StateMap.Builder().ignore(BlockFruitTreeLeaves.DECAYABLE).ignore(BlockFruitTreeLeaves.HARVESTABLE).build());

        for (BlockLeavesTFCF leaves : BlocksTFCF.getAllNormalTreeLeaves())
            ModelLoader.setCustomStateMapper(leaves, new StateMap.Builder().ignore(BlockLeavesTFCF.DECAYABLE).ignore(BlockLeavesTFCF.HARVESTABLE).build());

        for (BlockLogTFCF Logs : BlocksTFCF.getAllNormalTreeLog())
            ModelLoader.setCustomStateMapper(Logs, new StateMap.Builder().ignore(BlockLogTFCF.PLACED).build());

        for (Block block : BlocksTFCF.getAllCropBlocks())
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

        for (Block block : BlocksTFCF.getAllFruitChestBlocks())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockChest.FACING).build());

        for (BlockSlabTFC.Half block : BlocksTFCF.getAllSlabBlocksTFC())
        {
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockSlabTFC.VARIANT).build());
            ModelLoader.setCustomStateMapper(block.doubleSlab, new StateMap.Builder().ignore(BlockSlabTFC.VARIANT).build());
        }

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

        if (ConfigTFCF.General.WORLD.enableAllBlockTypes && ConfigTFCF.General.WORLD.enableAllFarmland)
        {
            BlocksTFCF.getAllBlockRockVariantsTFCF().forEach(e -> {
                if (e.getType() == RockTFCF.LOAMY_SAND_FARMLAND)
                {
                    ModelLoader.setCustomStateMapper(e, new StateMap.Builder().ignore(BlockLoamySandFarmland.MOISTURE).build());
                }
                else if (e.getType() == RockTFCF.SANDY_LOAM_FARMLAND)
                {
                    ModelLoader.setCustomStateMapper(e, new StateMap.Builder().ignore(BlockSandyLoamFarmland.MOISTURE).build());
                }
                else if (e.getType() == RockTFCF.LOAM_FARMLAND)
                {
                    ModelLoader.setCustomStateMapper(e, new StateMap.Builder().ignore(BlockLoamFarmland.MOISTURE).build());
                }
                else if (e.getType() == RockTFCF.SILT_LOAM_FARMLAND)
                {
                    ModelLoader.setCustomStateMapper(e, new StateMap.Builder().ignore(BlockSiltLoamFarmland.MOISTURE).build());
                }
                else if (e.getType() == RockTFCF.SILT_FARMLAND)
                {
                    ModelLoader.setCustomStateMapper(e, new StateMap.Builder().ignore(BlockSiltFarmland.MOISTURE).build());
                }
                else if (e.getType() == RockTFCF.HUMUS_FARMLAND)
                {
                    ModelLoader.setCustomStateMapper(e, new StateMap.Builder().ignore(BlockHumusFarmland.MOISTURE).build());
                }
                else if (e.getType() == RockTFCF.MOSSY_RAW)
                {
                    ModelLoader.setCustomStateMapper(e, new StateMap.Builder().ignore(BlockRockRawTFCF.CAN_FALL).build());
                }
            });
        }

        // Ceramic Molds
        if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
        {
            ModelBakery.registerItemVariants(ItemEarthenwareMold.get(Metal.ItemType.INGOT), new ModelResourceLocation(ItemEarthenwareMold.get(Metal.ItemType.INGOT).getRegistryName() + "/unknown"));
        }
        if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
        {
            ModelBakery.registerItemVariants(ItemKaoliniteMold.get(Metal.ItemType.INGOT), new ModelResourceLocation(ItemKaoliniteMold.get(Metal.ItemType.INGOT).getRegistryName() + "/unknown"));
        }
        if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
        {
            ModelBakery.registerItemVariants(ItemStonewareMold.get(Metal.ItemType.INGOT), new ModelResourceLocation(ItemStonewareMold.get(Metal.ItemType.INGOT).getRegistryName() + "/unknown"));
        }

        if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
        {
            for (Metal.ItemType value : Metal.ItemType.values())
            {
                ItemEarthenwareMold item = ItemEarthenwareMold.get(value);
                if (item == null) continue;

                final ModelResourceLocation FALLBACK = new ModelResourceLocation(item.getRegistryName().toString() + "/empty");
                final ModelResourceLocation FILLED = new ModelResourceLocation(item.getRegistryName().toString() + "/" + value.name().toLowerCase());
                ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
                {
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
                                return FILLED;
                            }
                        }
                        return FALLBACK;
                    }
                });
                ModelBakery.registerItemVariants(item, FALLBACK, FILLED);
            }
        }

        if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
        {
            for (Metal.ItemType value : Metal.ItemType.values())
            {
                ItemKaoliniteMold item = ItemKaoliniteMold.get(value);
                if (item == null) continue;

                final ModelResourceLocation FALLBACK = new ModelResourceLocation(item.getRegistryName().toString() + "/empty");
                final ModelResourceLocation FILLED = new ModelResourceLocation(item.getRegistryName().toString() + "/" + value.name().toLowerCase());
                ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
                {
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
                                return FILLED;
                            }
                        }
                        return FALLBACK;
                    }
                });
                ModelBakery.registerItemVariants(item, FALLBACK, FILLED);
            }
        }

        if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
        {
            for (Metal.ItemType value : Metal.ItemType.values())
            {
                ItemStonewareMold item = ItemStonewareMold.get(value);
                if (item == null) continue;

                final ModelResourceLocation FALLBACK = new ModelResourceLocation(item.getRegistryName().toString() + "/empty");
                final ModelResourceLocation FILLED = new ModelResourceLocation(item.getRegistryName().toString() + "/" + value.name().toLowerCase());
                ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
                {
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
                                return FILLED;
                            }
                        }
                        return FALLBACK;
                    }
                });
                ModelBakery.registerItemVariants(item, FALLBACK, FILLED);
            }
        }

        // TFC Elementia Compat
        if (TFCFlorae.TFCElementiaAdded)
        {
            if (ItemMetalTFCE.ItemType.NAIL.isTypeActive())
            {
                if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
                {
                    ModelBakery.registerItemVariants(ItemEarthenwareMoldTFCE.get(ItemMetalTFCE.ItemType.NAIL), new ModelResourceLocation(ItemEarthenwareMoldTFCE.get(ItemMetalTFCE.ItemType.NAIL).getRegistryName() + "/unknown"));
                }
                if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
                {
                    ModelBakery.registerItemVariants(ItemKaoliniteMoldTFCE.get(ItemMetalTFCE.ItemType.NAIL), new ModelResourceLocation(ItemKaoliniteMoldTFCE.get(ItemMetalTFCE.ItemType.NAIL).getRegistryName() + "/unknown"));
                }
                if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
                {
                    ModelBakery.registerItemVariants(ItemStonewareMoldTFCE.get(ItemMetalTFCE.ItemType.NAIL), new ModelResourceLocation(ItemStonewareMoldTFCE.get(ItemMetalTFCE.ItemType.NAIL).getRegistryName() + "/unknown"));
                }
            }
            if (ItemMetalTFCE.ItemType.RING.isTypeActive())
            {
                if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
                {
                    ModelBakery.registerItemVariants(ItemEarthenwareMoldTFCE.get(ItemMetalTFCE.ItemType.RING), new ModelResourceLocation(ItemEarthenwareMoldTFCE.get(ItemMetalTFCE.ItemType.RING).getRegistryName() + "/unknown"));
                }
                if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
                {
                    ModelBakery.registerItemVariants(ItemKaoliniteMoldTFCE.get(ItemMetalTFCE.ItemType.RING), new ModelResourceLocation(ItemKaoliniteMoldTFCE.get(ItemMetalTFCE.ItemType.RING).getRegistryName() + "/unknown"));
                }
                if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
                {
                    ModelBakery.registerItemVariants(ItemStonewareMoldTFCE.get(ItemMetalTFCE.ItemType.RING), new ModelResourceLocation(ItemStonewareMoldTFCE.get(ItemMetalTFCE.ItemType.RING).getRegistryName() + "/unknown"));
                }
            }
            if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
            {
                ModelBakery.registerItemVariants(ItemEarthenwareMoldTFCE.get(ItemMetalTFCE.ItemType.HALBERD_BLADE), new ModelResourceLocation(ItemEarthenwareMoldTFCE.get(ItemMetalTFCE.ItemType.HALBERD_BLADE).getRegistryName() + "/unknown"));
            }
            if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
            {
                ModelBakery.registerItemVariants(ItemKaoliniteMoldTFCE.get(ItemMetalTFCE.ItemType.HALBERD_BLADE), new ModelResourceLocation(ItemKaoliniteMoldTFCE.get(ItemMetalTFCE.ItemType.HALBERD_BLADE).getRegistryName() + "/unknown"));
            }
            if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
            {
                ModelBakery.registerItemVariants(ItemStonewareMoldTFCE.get(ItemMetalTFCE.ItemType.HALBERD_BLADE), new ModelResourceLocation(ItemStonewareMoldTFCE.get(ItemMetalTFCE.ItemType.HALBERD_BLADE).getRegistryName() + "/unknown"));
            }

            if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
            {
                ItemEarthenwareMoldTFCE item = ItemEarthenwareMoldTFCE.get(ItemType.HALBERD_BLADE);
                if (item != null)
                {
                    final ModelResourceLocation FALLBACK = new ModelResourceLocation(item.getRegistryName().toString() + "/empty");
                    final ModelResourceLocation FILLED = new ModelResourceLocation(item.getRegistryName().toString() + "/halberd_blade");

                    ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
                    {
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
                                    return FILLED;
                                }
                            }
                            return FALLBACK;
                        }
                    });
                    ModelBakery.registerItemVariants(item, FALLBACK, FILLED);
                }
            }

            if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
            {
                ItemKaoliniteMoldTFCE item = ItemKaoliniteMoldTFCE.get(ItemType.HALBERD_BLADE);
                if (item != null)
                {
                    final ModelResourceLocation FALLBACK = new ModelResourceLocation(item.getRegistryName().toString() + "/empty");
                    final ModelResourceLocation FILLED = new ModelResourceLocation(item.getRegistryName().toString() + "/halberd_blade");

                    ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
                    {
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
                                    return FILLED;
                                }
                            }
                            return FALLBACK;
                        }
                    });
                    ModelBakery.registerItemVariants(item, FALLBACK, FILLED);
                }
            }

            if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
            {
                ItemStonewareMoldTFCE item = ItemStonewareMoldTFCE.get(ItemType.HALBERD_BLADE);
                if (item != null)
                {
                    final ModelResourceLocation FALLBACK = new ModelResourceLocation(item.getRegistryName().toString() + "/empty");
                    final ModelResourceLocation FILLED = new ModelResourceLocation(item.getRegistryName().toString() + "/halberd_blade");

                    ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
                    {
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
                                    return FILLED;
                                }
                            }
                            return FALLBACK;
                        }
                    });
                    ModelBakery.registerItemVariants(item, FALLBACK, FILLED);
                }
            }

            if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
            {
                for (ItemMetalTFCE.ItemType value : ItemMetalTFCE.ItemType.values())
                {
                    ItemEarthenwareMoldTFCE item = ItemEarthenwareMoldTFCE.get(value);
                    if (item == null || !value.isTypeActive() || value == ItemMetalTFCE.ItemType.HALBERD_BLADE) continue;

                    final ModelResourceLocation FALLBACK = new ModelResourceLocation(item.getRegistryName().toString() + "/empty");
                    final ModelResourceLocation FILLED = new ModelResourceLocation(item.getRegistryName().toString() + "/" + value.name().toLowerCase());

                    ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
                    {
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
                                    return FILLED;
                                }
                            }
                            return FALLBACK;
                        }
                    });
                    ModelBakery.registerItemVariants(item, FALLBACK, FILLED);
                }
            }

            if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
            {
                for (ItemMetalTFCE.ItemType value : ItemMetalTFCE.ItemType.values())
                {
                    ItemKaoliniteMoldTFCE item = ItemKaoliniteMoldTFCE.get(value);
                    if (item == null || !value.isTypeActive() || value == ItemMetalTFCE.ItemType.HALBERD_BLADE) continue;

                    final ModelResourceLocation FALLBACK = new ModelResourceLocation(item.getRegistryName().toString() + "/empty");
                    final ModelResourceLocation FILLED = new ModelResourceLocation(item.getRegistryName().toString() + "/" + value.name().toLowerCase());

                    ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
                    {
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
                                    return FILLED;
                                }
                            }
                            return FALLBACK;
                        }
                    });
                    ModelBakery.registerItemVariants(item, FALLBACK, FILLED);
                }
            }

            if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
            {
                for (ItemMetalTFCE.ItemType value : ItemMetalTFCE.ItemType.values())
                {
                    ItemStonewareMoldTFCE item = ItemStonewareMoldTFCE.get(value);
                    if (item == null || !value.isTypeActive() || value == ItemMetalTFCE.ItemType.HALBERD_BLADE) continue;

                    final ModelResourceLocation FALLBACK = new ModelResourceLocation(item.getRegistryName().toString() + "/empty");
                    final ModelResourceLocation FILLED = new ModelResourceLocation(item.getRegistryName().toString() + "/" + value.name().toLowerCase());

                    ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
                    {
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
                                    return FILLED;
                                }
                            }
                            return FALLBACK;
                        }
                    });
                    ModelBakery.registerItemVariants(item, FALLBACK, FILLED);
                }
            }
        }

        // FirmaLife Compat
        if (TFCFlorae.FirmaLifeAdded)
        {
            if (ItemsTFCF.malletMoldEarthenware instanceof ItemEarthenwareMalletMoldFL && ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
            {
                ItemEarthenwareMalletMoldFL item = ItemsTFCF.malletMoldEarthenware;

                final ModelResourceLocation FALLBACK = new ModelResourceLocation(item.getRegistryName().toString() + "/empty");
                final ModelResourceLocation FILLED = new ModelResourceLocation(item.getRegistryName().toString() + "/mallet_head");
                ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
                {
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
                                return FILLED;
                            }
                        }
                        return FALLBACK;
                    }
                });
                ModelBakery.registerItemVariants(item, FALLBACK, FILLED);
            }

            if (ItemsTFCF.malletMoldKaolinite instanceof ItemKaoliniteMalletMoldFL && ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
            {
                ItemKaoliniteMalletMoldFL item = ItemsTFCF.malletMoldKaolinite;

                final ModelResourceLocation FALLBACK = new ModelResourceLocation(item.getRegistryName().toString() + "/empty");
                final ModelResourceLocation FILLED = new ModelResourceLocation(item.getRegistryName().toString() + "/mallet_head");
                ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
                {
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
                                return FILLED;
                            }
                        }
                        return FALLBACK;
                    }
                });
                ModelBakery.registerItemVariants(item, FALLBACK, FILLED);
            }

            if (ItemsTFCF.malletMoldStoneware instanceof ItemStonewareMalletMoldFL && ConfigTFCF.General.WORLD.enableAllStonewareClay)
            {
                ItemStonewareMalletMoldFL item = ItemsTFCF.malletMoldStoneware;

                final ModelResourceLocation FALLBACK = new ModelResourceLocation(item.getRegistryName().toString() + "/empty");
                final ModelResourceLocation FILLED = new ModelResourceLocation(item.getRegistryName().toString() + "/mallet_head");
                ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
                {
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
                                return FILLED;
                            }
                        }
                        return FALLBACK;
                    }
                });
                ModelBakery.registerItemVariants(item, FALLBACK, FILLED);
            }
        }

        //TESRs
        ClientRegistry.bindTileEntitySpecialRenderer(TEFruitChest.class, new TESRFruitChestTFCF());
        ClientRegistry.bindTileEntitySpecialRenderer(TEFruitLoom.class, new TESRFruitLoomTFCF());
        ClientRegistry.bindTileEntitySpecialRenderer(TEDryer.class, new TESRDryer());
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerColorHandlerItems(ColorHandlerEvent.Item event)
    {
        ItemColors itemColors = event.getItemColors();

        /*itemColors.registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
                return tintIndex > 0 ? -1 : ((ItemArmorTFCF)stack.getItem()).getColor(stack);
            }
        }, ItemsTFCF.getAllArmorItems().toArray(new ItemArmorTFCF[0]));*/

        itemColors.registerItemColorHandler((stack, tintIndex) ->
            tintIndex > 0 ? -1 : ((ItemArmorTFCF)stack.getItem()).getColor(stack),
            ItemsTFCF.getAllArmorItems().toArray(new ItemArmorTFCF[0]));

        if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
        {
            itemColors.registerItemColorHandler((stack, tintIndex) -> tintIndex == 1 ? EnumDyeColor.byDyeDamage(stack.getItemDamage()).getColorValue() : 0xFFFFFF,
                ItemsTFCF.UNFIRED_EARTHENWARE_VESSEL_GLAZED, ItemsTFCF.FIRED_EARTHENWARE_VESSEL_GLAZED);
        }
        if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
        {
            itemColors.registerItemColorHandler((stack, tintIndex) -> tintIndex == 1 ? EnumDyeColor.byDyeDamage(stack.getItemDamage()).getColorValue() : 0xFFFFFF,
                ItemsTFCF.UNFIRED_KAOLINITE_VESSEL_GLAZED, ItemsTFCF.FIRED_KAOLINITE_VESSEL_GLAZED);
        }
        if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
        {
            itemColors.registerItemColorHandler((stack, tintIndex) -> tintIndex == 1 ? EnumDyeColor.byDyeDamage(stack.getItemDamage()).getColorValue() : 0xFFFFFF,
                ItemsTFCF.UNFIRED_STONEWARE_VESSEL_GLAZED, ItemsTFCF.FIRED_STONEWARE_VESSEL_GLAZED);
        }

        if (ConfigTFCF.General.WORLD.enableAllBlockTypes)
        {
            itemColors.registerItemColorHandler((stack, tintIndex) ->
                    event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
                BlocksTFCF.getAllBlockRockVariantsTFCF().stream().filter(x -> x.getType().isGrass).toArray(BlockRockVariantTFCF[]::new));
        }

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllFruitLeaves().toArray(new BlockFruitTreeLeaves[0])
        );

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllNormalTreeLeaves().toArray(new BlockLeavesTFCF[0])
        );

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.CASSIA_CINNAMON_LEAVES);

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.CEYLON_CINNAMON_LEAVES);

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllTallGrassWaterBlocks().toArray(new BlockTallGrassWater[0]));

        /*itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllShortGrassBlocks().toArray(new BlockShortGrassTFCF[0]));

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllTallGrassBlocks().toArray(new BlockTallGrassTFCF[0]));*/

        /*itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllStandardBlocks().toArray(new BlockPlantTFCF[0]));

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllStandardBlocks().toArray(new BlockPlantDummy1[0]));

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllStandardBlocks().toArray(new BlockPlantDummy2[0]));*/

        /*itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllHangingCreepingPlantBlocks().toArray(new BlockHangingCreepingPlantTFCF[0]));

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllCreepingPlantBlocks().toArray(new BlockCreepingPlantTFCF[0]));*/

        if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay || ConfigTFCF.General.WORLD.enableAllKaoliniteClay || ConfigTFCF.General.WORLD.enableAllStonewareClay)
        {
            for (Item item : ItemsTFCF.getAllCeramicMoldItems())
            {
                itemColors.registerItemColorHandler(
                    (stack, tintIndex) -> {
                        if (tintIndex == 1)
                        {
                            IFluidHandler capFluidHandler = stack.getCapability(FLUID_HANDLER_CAPABILITY, null);
                            if (capFluidHandler instanceof IMoldHandler)
                            {
                                Metal metal = ((IMoldHandler) capFluidHandler).getMetal();
                                if (metal != null)
                                {
                                    return (new Color(metal.getColor())).brighter().getRGB();
                                }
                            }
                            return 0xFF000000;
                        }
                        return -1;
                    },
                    item);
            }
        }

        if (TFCFlorae.FirmaLifeAdded)
        {
            if (ItemsTFCF.malletMoldEarthenware instanceof ItemEarthenwareMalletMoldFL && ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
            {
                ItemEarthenwareMalletMoldFL item = ItemsTFCF.malletMoldEarthenware;
                itemColors.registerItemColorHandler(
                    (stack, tintIndex) -> {
                        if (tintIndex == 1)
                        {
                            IFluidHandler capFluidHandler = stack.getCapability(FLUID_HANDLER_CAPABILITY, null);
                            if (capFluidHandler instanceof IMoldHandler)
                            {
                                Metal metal = ((IMoldHandler) capFluidHandler).getMetal();
                                if (metal != null)
                                {
                                    return (new Color(metal.getColor())).brighter().getRGB();
                                }
                            }
                            return 0xFF000000;
                        }
                        return -1;
                    },
                    item);
            }
            if (ItemsTFCF.malletMoldKaolinite instanceof ItemKaoliniteMalletMoldFL && ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
            {
                ItemKaoliniteMalletMoldFL item = ItemsTFCF.malletMoldKaolinite;
                itemColors.registerItemColorHandler(
                    (stack, tintIndex) -> {
                        if (tintIndex == 1)
                        {
                            IFluidHandler capFluidHandler = stack.getCapability(FLUID_HANDLER_CAPABILITY, null);
                            if (capFluidHandler instanceof IMoldHandler)
                            {
                                Metal metal = ((IMoldHandler) capFluidHandler).getMetal();
                                if (metal != null)
                                {
                                    return (new Color(metal.getColor())).brighter().getRGB();
                                }
                            }
                            return 0xFF000000;
                        }
                        return -1;
                    },
                    item);
            }
            if (ItemsTFCF.malletMoldStoneware instanceof ItemStonewareMalletMoldFL && ConfigTFCF.General.WORLD.enableAllStonewareClay)
            {
                ItemStonewareMalletMoldFL item = ItemsTFCF.malletMoldStoneware;
                itemColors.registerItemColorHandler(
                    (stack, tintIndex) -> {
                        if (tintIndex == 1)
                        {
                            IFluidHandler capFluidHandler = stack.getCapability(FLUID_HANDLER_CAPABILITY, null);
                            if (capFluidHandler instanceof IMoldHandler)
                            {
                                Metal metal = ((IMoldHandler) capFluidHandler).getMetal();
                                if (metal != null)
                                {
                                    return (new Color(metal.getColor())).brighter().getRGB();
                                }
                            }
                            return 0xFF000000;
                        }
                        return -1;
                    },
                    item);
            }
        }
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

        if (ConfigTFCF.General.WORLD.enableAllBlockTypes)
        {
            blockColors.registerBlockColorHandler(grassColor, BlocksTFCF.getAllBlockRockVariantsTFCF().stream().filter(x -> x.getType().isGrass).toArray(BlockRockVariantTFCF[]::new));
        }

        blockColors.registerBlockColorHandler(grassColor, BlocksTFCF.getAllShortGrassBlocks().toArray(new BlockShortGrassTFCF[0]));
        //blockColors.registerBlockColorHandler(grassColor, BlocksTFCF.getAllTallGrassBlocks().toArray(new BlockTallGrassTFCF[0]));

        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllFruitLeaves().toArray(new Block[0]));
        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllNormalTreeLeaves().toArray(new Block[0]));

        for (BlockCropDead block : BlocksTFCF.getAllDeadCrops())
            blockColors.registerBlockColorHandler((state, world, os, tintIndex) -> 0xCC7400, block);
            
        for (Block block : BlocksTFCF.getAllBambooLeaves())
            blockColors.registerBlockColorHandler(foliageColor, block);

        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.CASSIA_CINNAMON_LEAVES);
        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.CEYLON_CINNAMON_LEAVES);
        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllWaterPlantBlocks().toArray(new BlockWaterPlantTFCF[0]));
        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllHangingPlantBlocks().toArray(new BlockHangingPlantTFCF[0]));
        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllHangingGlowingPlantBlocks().toArray(new BlockHangingGlowingPlant[0]));
        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllHangingCreepingPlantBlocks().toArray(new BlockHangingCreepingPlantTFCF[0]));
        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllHangingGlowingCreepingPlantBlocks().toArray(new BlockHangingGlowingCreepingPlant[0]));
        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllCreepingPlantBlocks().toArray(new BlockCreepingPlantTFCF[0]));
        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllTallGrassWaterBlocks().toArray(new BlockTallGrassWater[0]));
        //blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllStandardBlocks().toArray(new BlockPlantTFCF[0]));
        blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllStandardBlocks().toArray(new BlockPlantDummy1[0]));
        //blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllStandardBlocks().toArray(new BlockPlantDummy2[0]));

        if (ConfigTFCF.General.WORLD.enableAllBlockTypes && ConfigTFCF.General.WORLD.enableAllFarmland)
        {
            blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) -> BlockLoamySandFarmland.TINT[state.getValue(BlockLoamySandFarmland.MOISTURE)],
                BlocksTFCF.getAllBlockRockVariantsTFCF().stream().filter(x -> x.getType() == RockTFCF.LOAMY_SAND_FARMLAND).toArray(BlockRockVariantTFCF[]::new));

            blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) -> BlockSandyLoamFarmland.TINT[state.getValue(BlockSandyLoamFarmland.MOISTURE)],
                BlocksTFCF.getAllBlockRockVariantsTFCF().stream().filter(x -> x.getType() == RockTFCF.SANDY_LOAM_FARMLAND).toArray(BlockRockVariantTFCF[]::new));

            blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) -> BlockLoamFarmland.TINT[state.getValue(BlockLoamFarmland.MOISTURE)],
                BlocksTFCF.getAllBlockRockVariantsTFCF().stream().filter(x -> x.getType() == RockTFCF.LOAM_FARMLAND).toArray(BlockRockVariantTFCF[]::new));

            blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) -> BlockSiltLoamFarmland.TINT[state.getValue(BlockSiltLoamFarmland.MOISTURE)],
                BlocksTFCF.getAllBlockRockVariantsTFCF().stream().filter(x -> x.getType() == RockTFCF.SILT_LOAM_FARMLAND).toArray(BlockRockVariantTFCF[]::new));

            blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) -> BlockSiltFarmland.TINT[state.getValue(BlockSiltFarmland.MOISTURE)],
                BlocksTFCF.getAllBlockRockVariantsTFCF().stream().filter(x -> x.getType() == RockTFCF.SILT_FARMLAND).toArray(BlockRockVariantTFCF[]::new));

            blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) -> BlockHumusFarmland.TINT[state.getValue(BlockHumusFarmland.MOISTURE)],
                BlocksTFCF.getAllBlockRockVariantsTFCF().stream().filter(x -> x.getType() == RockTFCF.HUMUS_FARMLAND).toArray(BlockRockVariantTFCF[]::new));
        }
    }
}
