package tfcflorae.client;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Stream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.dries007.tfc.client.*;
import net.dries007.tfc.client.model.ContainedFluidModel;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;

import tfcflorae.client.render.entity.TFCFBoatRenderer;
import tfcflorae.client.screen.TFCFKnappingScreen;
import tfcflorae.client.screen.ceramics.*;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.common.blocks.wood.TFCFWood;
import tfcflorae.common.container.TFCFContainerTypes;
import tfcflorae.common.entities.TFCFEntities;

import static net.dries007.tfc.common.blocks.wood.Wood.BlockType.*;

public class ClientEventHandler
{
    public static void init()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(ClientEventHandler::clientSetup);
        bus.addListener(ClientEventHandler::onConfigReload);
        bus.addListener(ClientEventHandler::registerModelLoaders);
        bus.addListener(ClientEventHandler::registerColorHandlerBlocks);
        bus.addListener(ClientEventHandler::registerColorHandlerItems);
        bus.addListener(ClientEventHandler::registerParticleFactories);
        bus.addListener(ClientEventHandler::registerClientReloadListeners);
        bus.addListener(ClientEventHandler::registerEntityRenderers);
        bus.addListener(ClientEventHandler::registerLayerDefinitions);
        bus.addListener(ClientEventHandler::onTextureStitch);
    }

    public static void clientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {

            // Screens
            MenuScreens.register(TFCFContainerTypes.EARTHENWARE_CLAY_KNAPPING.get(), TFCFKnappingScreen::new);
            MenuScreens.register(TFCFContainerTypes.KAOLINITE_CLAY_KNAPPING.get(), TFCFKnappingScreen::new);
            MenuScreens.register(TFCFContainerTypes.STONEWARE_CLAY_KNAPPING.get(), TFCFKnappingScreen::new);

            MenuScreens.register(TFCFContainerTypes.LARGE_EARTHENWARE_VESSEL.get(), LargeEarthenwareVesselScreen::new);
            MenuScreens.register(TFCFContainerTypes.LARGE_KAOLINITE_VESSEL.get(), LargeKaoliniteVesselScreen::new);
            MenuScreens.register(TFCFContainerTypes.LARGE_STONEWARE_VESSEL.get(), LargeStonewareVesselScreen::new);

            Stream.of(TFCFBlocks.LARGE_EARTHENWARE_VESSEL, TFCFBlocks.GLAZED_LARGE_EARTHENWARE_VESSELS.values()).<Supplier<? extends Block>>flatMap(Helpers::flatten).forEach(vessel -> ItemProperties.register(vessel.get().asItem(), Helpers.identifier("sealed"), (stack, level, entity, unused) -> stack.hasTag() ? 1.0f : 0f));
            Stream.of(TFCFBlocks.LARGE_KAOLINITE_VESSEL, TFCFBlocks.GLAZED_LARGE_KAOLINITE_VESSELS.values()).<Supplier<? extends Block>>flatMap(Helpers::flatten).forEach(vessel -> ItemProperties.register(vessel.get().asItem(), Helpers.identifier("sealed"), (stack, level, entity, unused) -> stack.hasTag() ? 1.0f : 0f));
            Stream.of(TFCFBlocks.LARGE_STONEWARE_VESSEL, TFCFBlocks.GLAZED_LARGE_STONEWARE_VESSELS.values()).<Supplier<? extends Block>>flatMap(Helpers::flatten).forEach(vessel -> ItemProperties.register(vessel.get().asItem(), Helpers.identifier("sealed"), (stack, level, entity, unused) -> stack.hasTag() ? 1.0f : 0f));

            TFCFBlocks.WOODS.values().forEach(map -> ItemProperties.register(map.get(BARREL).get().asItem(), Helpers.identifier("sealed"), (stack, level, entity, unused) -> stack.hasTag() ? 1.0f : 0f));
        });

        // Keybindings
        ClientRegistry.registerKeyBinding(TFCKeyBindings.PLACE_BLOCK);
        ClientRegistry.registerKeyBinding(TFCKeyBindings.CYCLE_CHISEL_MODE);
        ClientRegistry.registerKeyBinding(TFCKeyBindings.STACK_FOOD);

        // Render Types
        final RenderType solid = RenderType.solid();
        final RenderType cutout = RenderType.cutout();
        final RenderType cutoutMipped = RenderType.cutoutMipped();
        final RenderType translucent = RenderType.translucent();

        // TFCFWood blocks
        TFCFBlocks.WOODS.values().forEach(map -> {
            Stream.of(SAPLING, DOOR, TRAPDOOR, FENCE, FENCE_GATE, BUTTON, PRESSURE_PLATE, SLAB, STAIRS, TWIG, BARREL, SCRIBING_TABLE).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), cutout));
            Stream.of(LEAVES, FALLEN_LEAVES).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), layer -> Minecraft.useFancyGraphics() ? layer == cutoutMipped : layer == solid));
        });

        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.LARGE_EARTHENWARE_VESSEL.get(), cutout);
        TFCFBlocks.GLAZED_LARGE_EARTHENWARE_VESSELS.values().forEach(vessel -> ItemBlockRenderTypes.setRenderLayer(vessel.get(), cutout));

        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.LARGE_KAOLINITE_VESSEL.get(), cutout);
        TFCFBlocks.GLAZED_LARGE_KAOLINITE_VESSELS.values().forEach(vessel -> ItemBlockRenderTypes.setRenderLayer(vessel.get(), cutout));

        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.LARGE_STONEWARE_VESSEL.get(), cutout);
        TFCFBlocks.GLAZED_LARGE_STONEWARE_VESSELS.values().forEach(vessel -> ItemBlockRenderTypes.setRenderLayer(vessel.get(), cutout));
    }

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        // Entities
        for (TFCFWood wood : TFCFWood.VALUES)
        {
            event.registerEntityRenderer(TFCFEntities.BOATS.get(wood).get(), ctx -> new TFCFBoatRenderer(ctx, wood.getSerializedName()));
        }
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        LayerDefinition boatLayer = BoatModel.createBodyModel();
        LayerDefinition signLayer = SignRenderer.createSignLayer();
        for (TFCFWood wood : TFCFWood.VALUES)
        {
            event.registerLayerDefinition(TFCFBoatRenderer.boatName(wood.getSerializedName()), () -> boatLayer);
            event.registerLayerDefinition(RenderHelpers.modelIdentifier("sign/" + wood.name().toLowerCase(Locale.ROOT)), () -> signLayer);
        }
    }

    public static void onConfigReload(ModConfigEvent.Reloading event)
    {
        IngameOverlays.reloadOverlays();
    }

    public static void registerModelLoaders(ModelRegistryEvent event)
    {
        ModelLoaderRegistry.registerLoader(Helpers.identifier("contained_fluid"), new ContainedFluidModel.Loader());
    }

    public static void registerColorHandlerBlocks(ColorHandlerEvent.Block event)
    {
        final BlockColors registry = event.getBlockColors();
        final BlockColor grassColor = (state, worldIn, pos, tintIndex) -> TFCColors.getGrassColor(pos, tintIndex);
        final BlockColor tallGrassColor = (state, worldIn, pos, tintIndex) -> TFCColors.getTallGrassColor(pos, tintIndex);
        final BlockColor foliageColor = (state, worldIn, pos, tintIndex) -> TFCColors.getFoliageColor(pos, tintIndex);
        final BlockColor seasonalFoliageColor = (state, worldIn, pos, tintIndex) -> TFCColors.getSeasonalFoliageColor(pos, tintIndex);

        TFCFBlocks.TFCFSOIL.get(TFCFSoil.GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.EARTHENWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_EARTHENWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_EARTHENWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.KAOLINITE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_KAOLINITE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_KAOLINITE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.STONEWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_STONEWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_STONEWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));

        TFCFBlocks.WOODS.forEach((wood, reg) -> registry.register(wood.isConifer() ? foliageColor : seasonalFoliageColor, reg.get(Wood.BlockType.LEAVES).get(), reg.get(Wood.BlockType.FALLEN_LEAVES).get()));
        TFCFBlocks.LEAVES_ONLY.forEach((wood, reg) -> registry.register(wood.isConifer() ? foliageColor : seasonalFoliageColor, reg.get(), reg.get()));
    }

    public static void registerColorHandlerItems(ColorHandlerEvent.Item event)
    {
        final ItemColors registry = event.getItemColors();
        final ItemColor grassColor = (stack, tintIndex) -> TFCColors.getGrassColor(null, tintIndex);
        final ItemColor seasonalFoliageColor = (stack, tintIndex) -> TFCColors.getFoliageColor(null, tintIndex);

        TFCFBlocks.WOODS.forEach((key, value) -> registry.register(seasonalFoliageColor, value.get(FALLEN_LEAVES).get(), value.get(LEAVES).get()));
        TFCFBlocks.LEAVES_ONLY.forEach((key, value) -> registry.register(seasonalFoliageColor, value.get(), value.get()));
    }

    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event)
    {
        // Color maps
    }

    public static void registerParticleFactories(ParticleFactoryRegisterEvent event)
    {
        //ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
    }

    public static void onTextureStitch(TextureStitchEvent.Pre event)
    {
        final ResourceLocation sheet = event.getAtlas().location();
        if (sheet.equals(RenderHelpers.BLOCKS_ATLAS))
        {
            event.addSprite(Helpers.identifier("block/burlap"));
            event.addSprite(Helpers.identifier("block/devices/bellows/back"));
            event.addSprite(Helpers.identifier("block/devices/bellows/side"));

            for (Metal.Default metal : Metal.Default.values())
            {
                event.addSprite(Helpers.identifier("block/metal/full/" + metal.getSerializedName()));
            }
            for (String texture : TFCConfig.CLIENT.additionalMetalSheetTextures.get())
            {
                event.addSprite(new ResourceLocation(texture));
            }
        }
        else if (sheet.equals(Sheets.CHEST_SHEET))
        {
            Arrays.stream(TFCFWood.VALUES).map(TFCFWood::getSerializedName).forEach(name -> {
                event.addSprite(Helpers.identifier("entity/chest/normal/" + name));
                event.addSprite(Helpers.identifier("entity/chest/normal_left/" + name));
                event.addSprite(Helpers.identifier("entity/chest/normal_right/" + name));
                event.addSprite(Helpers.identifier("entity/chest/trapped/" + name));
                event.addSprite(Helpers.identifier("entity/chest/trapped_left/" + name));
                event.addSprite(Helpers.identifier("entity/chest/trapped_right/" + name));
            });
        }
        else if (sheet.equals(Sheets.SIGN_SHEET))
        {
            Arrays.stream(TFCFWood.VALUES).map(TFCFWood::getSerializedName).forEach(name -> event.addSprite(Helpers.identifier("entity/signs/" + name)));
        }
    }
}
