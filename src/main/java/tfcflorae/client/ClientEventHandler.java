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
import net.minecraft.client.renderer.blockentity.LecternRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
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
import net.dries007.tfc.client.render.blockentity.AnvilBlockEntityRenderer;
import net.dries007.tfc.client.render.blockentity.BarrelBlockEntityRenderer;
import net.dries007.tfc.client.render.blockentity.SluiceBlockEntityRenderer;
import net.dries007.tfc.client.screen.KnappingScreen;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.rock.RockCategory;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;

import tfcflorae.client.render.blockentity.TFCFChestBlockEntityRenderer;
import tfcflorae.client.render.blockentity.TFCFSignBlockEntityRenderer;
import tfcflorae.client.render.entity.TFCFBoatRenderer;
import tfcflorae.client.screen.TFCFAnvilPlanScreen;
import tfcflorae.client.screen.TFCFAnvilScreen;
import tfcflorae.client.screen.TFCFBarrelScreen;
import tfcflorae.client.screen.ceramics.*;
import tfcflorae.common.blockentities.TFCFBlockEntities;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.common.blocks.wood.TFCFWood;
import tfcflorae.common.container.TFCFContainerTypes;
import tfcflorae.common.entities.TFCFEntities;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.util.TFCFHelpers;

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
            MenuScreens.register(TFCFContainerTypes.EARTHENWARE_CLAY_KNAPPING.get(), KnappingScreen::new);
            MenuScreens.register(TFCFContainerTypes.KAOLINITE_CLAY_KNAPPING.get(), KnappingScreen::new);
            MenuScreens.register(TFCFContainerTypes.STONEWARE_CLAY_KNAPPING.get(), KnappingScreen::new);
            MenuScreens.register(TFCFContainerTypes.FLINT_KNAPPING.get(), KnappingScreen::new);

            MenuScreens.register(TFCFContainerTypes.LARGE_EARTHENWARE_VESSEL.get(), LargeEarthenwareVesselScreen::new);
            MenuScreens.register(TFCFContainerTypes.LARGE_KAOLINITE_VESSEL.get(), LargeKaoliniteVesselScreen::new);
            MenuScreens.register(TFCFContainerTypes.LARGE_STONEWARE_VESSEL.get(), LargeStonewareVesselScreen::new);

            MenuScreens.register(TFCFContainerTypes.BARREL.get(), TFCFBarrelScreen::new);
            MenuScreens.register(TFCFContainerTypes.ANVIL.get(), TFCFAnvilScreen::new);
            MenuScreens.register(TFCFContainerTypes.ANVIL_PLAN.get(), TFCFAnvilPlanScreen::new);

            TFCFBlocks.WOODS.values().forEach(map -> ItemProperties.register(map.get(BARREL).get().asItem(), Helpers.identifier("sealed"), (stack, level, entity, unused) -> stack.hasTag() ? 1.0f : 0f));
        });

        // Render Types
        final RenderType solid = RenderType.solid();
        final RenderType cutout = RenderType.cutout();
        final RenderType cutoutMipped = RenderType.cutoutMipped();
        final RenderType translucent = RenderType.translucent();

        TFCFItems.FLINT_TOOLS.values().forEach(tool -> {
            Item javelin = tool.get(RockCategory.ItemType.JAVELIN).get();
            ItemProperties.register(javelin, Helpers.identifier("throwing"), (stack, level, entity, unused) ->
                entity != null && ((entity.isUsingItem() && entity.getUseItem() == stack) || (entity instanceof Monster monster && monster.isAggressive())) ? 1.0F : 0.0F
            );
        });

        TFCFBlocks.WOODS.values().forEach(map -> {
            Stream.of(SAPLING, DOOR, TRAPDOOR, FENCE, FENCE_GATE, BUTTON, PRESSURE_PLATE, SLAB, STAIRS, TWIG, BARREL, SCRIBING_TABLE).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), cutout));
            Stream.of(FALLEN_LEAVES).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), layer -> Minecraft.useFancyGraphics() ? layer == cutoutMipped : layer == solid));
        });

        TFCFBlocks.WOODS.forEach((key, value) -> {
            if (!key.isFruitTree() && !key.isMangrove())
            Stream.of(LEAVES).forEach(type -> ItemBlockRenderTypes.setRenderLayer(value.get(type).get(), layer -> Minecraft.useFancyGraphics() ? layer == cutoutMipped : layer == cutoutMipped));
        });

        TFCFBlocks.LEAVES_ONLY.values().forEach(map -> {
            Stream.of(LEAVES).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(), layer -> Minecraft.useFancyGraphics() ? layer == cutoutMipped : layer == cutoutMipped));
        });
        TFCFBlocks.WOODS_SEASONAL_LEAVES.values().forEach(map -> {
            Stream.of(LEAVES).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(), layer -> Minecraft.useFancyGraphics() ? layer == cutoutMipped : layer == cutoutMipped));
        });
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.CHARRED_TREE_TWIG.get(), cutout);
        TFCFBlocks.MANGROVE_ROOTS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));

        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.LARGE_EARTHENWARE_VESSEL.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.LARGE_KAOLINITE_VESSEL.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.LARGE_STONEWARE_VESSEL.get(), cutout);

        // Grasses and such
        //TFCFBlocks.TFCSOIL.get(TFCFSoil.GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.PODZOL).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DRY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.SPARSE_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DENSE_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        //TFCFBlocks.TFCSOIL.get(TFCFSoil.CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DRY_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.SPARSE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DENSE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.EARTHENWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DRY_EARTHENWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.SPARSE_EARTHENWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DENSE_EARTHENWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.KAOLINITE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DRY_KAOLINITE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.SPARSE_KAOLINITE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DENSE_KAOLINITE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.STONEWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DRY_STONEWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.SPARSE_STONEWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DENSE_STONEWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));

        TFCFBlocks.TFCFSOIL.get(TFCFSoil.GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.PODZOL).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DENSE_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DENSE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.EARTHENWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_EARTHENWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_EARTHENWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DENSE_EARTHENWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.KAOLINITE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_KAOLINITE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_KAOLINITE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DENSE_KAOLINITE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.STONEWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_STONEWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_STONEWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DENSE_STONEWARE_CLAY_GRASS).values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));

        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.SPARSE_BOG_IRON_GRASS.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.DENSE_BOG_IRON_GRASS.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.BOG_IRON_GRASS.get(), cutout);

        TFCFBlocks.JOSHUA_TRUNK.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.JOSHUA_LEAVES.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.WOODS_SEASONAL_LEAVES.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.ROOT_SPIKES.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));

        // Plants
        TFCFBlocks.PLANTS.forEach((key, reg) -> {
            if (key.isVine())
                ItemBlockRenderTypes.setRenderLayer(reg.get(), translucent);
            else
                ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout);
        });
        TFCFBlocks.FRUITING_PLANTS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));
        TFCFBlocks.POTTED_PLANTS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));

        // Rock blocks
        TFCFBlocks.TFCF_ROCK_BLOCKS.values().forEach(map -> {
            ItemBlockRenderTypes.setRenderLayer(map.get(Rock.BlockType.SPIKE).get(), cutout);
            ItemBlockRenderTypes.setRenderLayer(map.get(Rock.BlockType.AQUEDUCT).get(), cutout);
        });
        TFCFBlocks.ROCK_BLOCKS.values().forEach(map -> {
            ItemBlockRenderTypes.setRenderLayer(map.get(TFCFRock.TFCFBlockType.ROCK_PILE).get(), cutout);
            ItemBlockRenderTypes.setRenderLayer(map.get(TFCFRock.TFCFBlockType.MOSSY_ROCK_PILE).get(), cutout);
        });
        TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.values().forEach(map -> {
            ItemBlockRenderTypes.setRenderLayer(map.get(TFCFRock.TFCFBlockType.ROCK_PILE).get(), cutout);
            ItemBlockRenderTypes.setRenderLayer(map.get(TFCFRock.TFCFBlockType.MOSSY_ROCK_PILE).get(), cutout);
        });
        TFCFBlocks.DRIPSTONE_BLOCKS.values().forEach(map -> {
            ItemBlockRenderTypes.setRenderLayer(map.get(Rock.BlockType.SPIKE).get(), cutout);
        });

        TFCFBlocks.ORES.values().forEach(map -> map.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout)));
        TFCFBlocks.GRADED_ORES.values().forEach(map -> map.values().forEach(inner -> inner.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout))));
        TFCFBlocks.ORE_DEPOSITS.values().forEach(map -> map.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout)));

        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.GLOWSTONE.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.GLOWSTONE_BUDDING.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.GLOWSTONE_CLUSTER.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.LARGE_GLOWSTONE_BUD.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.MEDIUM_GLOWSTONE_BUD.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.SMALL_GLOWSTONE_BUD.get(), cutout);

        TFCFBlocks.CRYSTAL.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));
        TFCFBlocks.BUDDING_CRYSTAL.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));
        TFCFBlocks.CLUSTER_CRYSTAL.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));
        TFCFBlocks.LARGE_BUD_CRYSTAL.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));
        TFCFBlocks.MEDIUM_BUD_CRYSTAL.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));
        TFCFBlocks.SMALL_BUD_CRYSTAL.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));

        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.SPIDER_EGG.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.SPIDER_EGGS.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.LARGE_SPIDER_EGG.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.WEBBED_GLOW_BLOCK.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.WEBBED_TORCH_BLOCK.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.WEBBED_CHEST.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.CREEPING_WEBS.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.HANGING_SPIDER_WEB_SLENDER.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.HANGING_SPIDER_WEB_THICK.get(), cutout);

        TFCFBlocks.SPARSE_SAND_GRASS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.DENSE_SAND_GRASS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.SAND_GRASS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));

        TFCFBlocks.BAMBOO_LOGS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));
        TFCFBlocks.STRIPPED_BAMBOO_LOGS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));
        TFCFBlocks.BAMBOO_LEAVES.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));
        TFCFBlocks.BAMBOO_SAPLINGS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutoutMipped));

        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.VANILLA_BAMBOO_LOGS.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(TFCFBlocks.VANILLA_STRIPPED_BAMBOO_LOGS.get(), cutout);
    }

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        // Entities
        for (TFCFWood wood : TFCFWood.VALUES)
        {
            event.registerEntityRenderer(TFCFEntities.BOATS.get(wood).get(), ctx -> new TFCFBoatRenderer(ctx, wood.getSerializedName()));
        }

        // BEs
        event.registerBlockEntityRenderer(TFCFBlockEntities.CHEST.get(), TFCFChestBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TFCFBlockEntities.TRAPPED_CHEST.get(), TFCFChestBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TFCFBlockEntities.SIGN.get(), TFCFSignBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TFCFBlockEntities.LECTERN.get(), LecternRenderer::new);
        event.registerBlockEntityRenderer(TFCFBlockEntities.ANVIL.get(), ctx -> new AnvilBlockEntityRenderer());
        event.registerBlockEntityRenderer(TFCFBlockEntities.BARREL.get(), ctx -> new BarrelBlockEntityRenderer());
        event.registerBlockEntityRenderer(TFCFBlockEntities.SLUICE.get(), ctx -> new SluiceBlockEntityRenderer());
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        LayerDefinition boatLayer = BoatModel.createBodyModel();
        LayerDefinition signLayer = SignRenderer.createSignLayer();
        for (TFCFWood wood : TFCFWood.VALUES)
        {
            event.registerLayerDefinition(TFCFBoatRenderer.boatName(wood.getSerializedName()), () -> boatLayer);
            event.registerLayerDefinition(TFCFRenderHelpers.modelIdentifier("sign/" + wood.name().toLowerCase(Locale.ROOT)), () -> signLayer);
        }
    }

    public static void onConfigReload(ModConfigEvent.Reloading event)
    {
        //IngameOverlays.reloadOverlays();
    }

    public static void registerModelLoaders(ModelRegistryEvent event)
    {
        //ModelLoaderRegistry.registerLoader(Helpers.identifier("contained_fluid"), new ContainedFluidModel.Loader());
    }

    public static void registerColorHandlerBlocks(ColorHandlerEvent.Block event)
    {
        final BlockColors registry = event.getBlockColors();
        final BlockColor grassColor = (state, worldIn, pos, tintIndex) -> TFCColors.getGrassColor(pos, tintIndex);
        final BlockColor tallGrassColor = (state, worldIn, pos, tintIndex) -> TFCColors.getTallGrassColor(pos, tintIndex);
        final BlockColor foliageColor = (state, worldIn, pos, tintIndex) -> TFCColors.getFoliageColor(pos, tintIndex);
        final BlockColor seasonalFoliageColor = (state, worldIn, pos, tintIndex) -> TFCColors.getSeasonalFoliageColor(pos, tintIndex);

        //TFCFBlocks.TFCSOIL.get(TFCFSoil.GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.PODZOL).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DRY_GRASS).values().forEach(reg -> registry.register(tallGrassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.SPARSE_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DENSE_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        //TFCFBlocks.TFCSOIL.get(TFCFSoil.CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DRY_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.SPARSE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DENSE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.EARTHENWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DRY_EARTHENWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.SPARSE_EARTHENWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DENSE_EARTHENWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.KAOLINITE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DRY_KAOLINITE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.SPARSE_KAOLINITE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DENSE_KAOLINITE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.STONEWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DRY_STONEWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.SPARSE_STONEWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCSOIL.get(TFCFSoil.DENSE_STONEWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));

        TFCFBlocks.TFCFSOIL.get(TFCFSoil.GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.PODZOL).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_GRASS).values().forEach(reg -> registry.register(tallGrassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DENSE_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DENSE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.EARTHENWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_EARTHENWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_EARTHENWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DENSE_EARTHENWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.KAOLINITE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_KAOLINITE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_KAOLINITE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DENSE_KAOLINITE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.STONEWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRY_STONEWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.SPARSE_STONEWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));
        TFCFBlocks.TFCFSOIL.get(TFCFSoil.DENSE_STONEWARE_CLAY_GRASS).values().forEach(reg -> registry.register(grassColor, reg.get()));

        registry.register(grassColor, TFCFBlocks.SPARSE_BOG_IRON_GRASS.get());
        registry.register(grassColor, TFCFBlocks.DENSE_BOG_IRON_GRASS.get());
        registry.register(grassColor, TFCFBlocks.BOG_IRON_GRASS.get());

        TFCFBlocks.WOODS.forEach((wood, reg) -> {
            if (!wood.isFruitTree() && !wood.isMangrove())
                registry.register(wood.isConifer() ? foliageColor : seasonalFoliageColor, reg.get(Wood.BlockType.LEAVES).get(), reg.get(Wood.BlockType.FALLEN_LEAVES).get());
            else
                registry.register(wood.isConifer() ? foliageColor : seasonalFoliageColor, reg.get(Wood.BlockType.FALLEN_LEAVES).get());
        });
        //TFCFBlocks.WOODS.forEach((wood, reg) -> registry.register(wood.isConifer() ? foliageColor : seasonalFoliageColor, reg.get(Wood.BlockType.LEAVES).get(), reg.get(Wood.BlockType.FALLEN_LEAVES).get()));
        TFCFBlocks.LEAVES_ONLY.forEach((wood, reg) -> registry.register(wood.isConifer() ? foliageColor : seasonalFoliageColor, reg.get(), reg.get()));
        TFCFBlocks.JOSHUA_LEAVES.forEach((wood, reg) -> registry.register(wood.isConifer() ? foliageColor : seasonalFoliageColor, reg.get(), reg.get()));
        TFCFBlocks.WOODS_SEASONAL_LEAVES.forEach((wood, reg) -> registry.register(wood.isConifer() ? foliageColor : seasonalFoliageColor, reg.get(), reg.get()));

        // Plants
        TFCFBlocks.PLANTS.forEach((plant, reg) -> registry.register(plant.isConifer() ? foliageColor : plant.isTallGrass() ? tallGrassColor : plant.isSeasonal() ? seasonalFoliageColor : plant.isFoliage() ? foliageColor : grassColor, reg.get()));
        TFCFBlocks.FRUITING_PLANTS.forEach((plant, reg) -> registry.register(plant.isConifer() ? foliageColor : plant.isTallGrass() ? tallGrassColor : plant.isSeasonal() ? seasonalFoliageColor : plant.isFoliage() ? foliageColor : grassColor, reg.get()));
        TFCFBlocks.POTTED_PLANTS.forEach((plant, reg) -> registry.register(grassColor, reg.get()));

        TFCFBlocks.SPARSE_SAND_GRASS.forEach((grass, reg) -> registry.register(grassColor, reg.get()));
        TFCFBlocks.DENSE_SAND_GRASS.forEach((grass, reg) -> registry.register(grassColor, reg.get()));
        TFCFBlocks.SAND_GRASS.forEach((grass, reg) -> registry.register(grassColor, reg.get()));

        TFCFBlocks.BAMBOO_LEAVES.forEach((plant, reg) -> registry.register(seasonalFoliageColor, reg.get()));
    }

    public static void registerColorHandlerItems(ColorHandlerEvent.Item event)
    {
        final ItemColors registry = event.getItemColors();
        final ItemColor grassColor = (stack, tintIndex) -> TFCColors.getGrassColor(null, tintIndex);
        final ItemColor seasonalFoliageColor = (stack, tintIndex) -> TFCColors.getFoliageColor(null, tintIndex);

        TFCFBlocks.WOODS.forEach((key, value) -> {
            if (!key.isFruitTree() && !key.isMangrove())
                registry.register(seasonalFoliageColor, value.get(LEAVES).get());
            else
                registry.register(seasonalFoliageColor, value.get(FALLEN_LEAVES).get());
        });
        TFCFBlocks.WOODS.forEach((key, value) -> {
            if (key.isFruitTree() || key.isMangrove())
                registry.register(seasonalFoliageColor, value.get(FALLEN_LEAVES).get());
        });

        TFCFBlocks.LEAVES_ONLY.forEach((key, value) -> registry.register(seasonalFoliageColor, value.get(), value.get()));
        TFCFBlocks.JOSHUA_LEAVES.forEach((key, value) -> registry.register(seasonalFoliageColor, value.get(), value.get()));
        TFCFBlocks.WOODS_SEASONAL_LEAVES.forEach((key, value) -> registry.register(seasonalFoliageColor, value.get(), value.get()));

        // Plants
        TFCFBlocks.PLANTS.forEach((plant, reg) -> {
            if (plant.isItemTinted())
                registry.register(plant.isConifer() ? seasonalFoliageColor : plant.isSeasonal() ? seasonalFoliageColor : grassColor, reg.get());
        });
        TFCFBlocks.FRUITING_PLANTS.forEach((plant, reg) -> {
            if (plant.isItemTinted())
                registry.register(plant.isConifer() ? seasonalFoliageColor : plant.isSeasonal() ? seasonalFoliageColor : grassColor, reg.get());
        });

        TFCFBlocks.BAMBOO_LEAVES.forEach((plant, reg) -> registry.register(seasonalFoliageColor, reg.get()));
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
        if (sheet.equals(Sheets.CHEST_SHEET)/* && hasLeavesOnly()*/)
        {
            Arrays.stream(TFCFWood.VALUES).map(TFCFWood::getSerializedName).forEach(name -> {
                event.addSprite(TFCFHelpers.identifier("entity/chest/normal/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/normal_left/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/normal_right/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/trapped/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/trapped_left/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/trapped_right/" + name));
            });
            Arrays.stream(Rock.VALUES).map(Rock::getSerializedName).forEach(name -> {
                event.addSprite(TFCFHelpers.identifier("entity/chest/normal/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/normal_left/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/normal_right/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/trapped/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/trapped_left/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/trapped_right/" + name));
            });
            Arrays.stream(TFCFRock.VALUES).map(TFCFRock::getSerializedName).forEach(name -> {
                event.addSprite(TFCFHelpers.identifier("entity/chest/normal/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/normal_left/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/normal_right/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/trapped/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/trapped_left/" + name));
                event.addSprite(TFCFHelpers.identifier("entity/chest/trapped_right/" + name));
            });
            event.addSprite(TFCFHelpers.identifier("entity/chest/normal/rock"));
            event.addSprite(TFCFHelpers.identifier("entity/chest/normal_left/rock"));
            event.addSprite(TFCFHelpers.identifier("entity/chest/normal_right/rock"));
            event.addSprite(TFCFHelpers.identifier("entity/chest/trapped/rock"));
            event.addSprite(TFCFHelpers.identifier("entity/chest/trapped_left/rock"));
            event.addSprite(TFCFHelpers.identifier("entity/chest/trapped_right/rock"));
        }
        else if (sheet.equals(Sheets.SIGN_SHEET)/* && hasLeavesOnly()*/)
        {
            Arrays.stream(TFCFWood.VALUES).map(TFCFWood::getSerializedName).forEach(name -> event.addSprite(TFCFHelpers.identifier("entity/signs/" + name)));
        }
    }

    /*static final Boolean hasLeavesOnly()
    {
        for (TFCFWood wood : TFCFWood.class.getEnumConstants())
        {
            if (!wood.hasLeavesOnly())
            {
                return true;
            }
        }
        return false;
    }*/
}
