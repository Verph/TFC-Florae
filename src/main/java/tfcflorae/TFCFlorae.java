package tfcflorae;

import net.dries007.tfc.util.Helpers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import com.mojang.logging.LogUtils;

import org.slf4j.Logger;

import tfcflorae.client.*;
import tfcflorae.common.blockentities.TFCFBlockEntities;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;
import tfcflorae.common.container.TFCFContainerTypes;
import tfcflorae.common.entities.TFCFEntities;
import tfcflorae.common.entities.ai.TFCFPoiType;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.common.recipes.TFCFRecipeSerializers;
import tfcflorae.common.recipes.TFCFRecipeTypes;
import tfcflorae.util.TFCFDispenserBehaviors;
import tfcflorae.util.TFCFInteractionManager;
import tfcflorae.world.carver.TFCFCarvers;
import tfcflorae.world.feature.TFCFFeatures;

@Mod(TFCFlorae.MOD_ID)
public class TFCFlorae
{
    public static final String MOD_ID = "tfcflorae";
    public static final String MOD_NAME = "TFCFlorae";
    public static final String MOD_VERSION = "${version}";
    public static final Logger LOGGER = LogUtils.getLogger();
    //public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(TFCFHelpers.identifier(MOD_ID), () -> MOD_VERSION, MOD_VERSION::equals, MOD_VERSION::equals);

    public TFCFlorae()
    {
        LOGGER.info("Initializing TFC-Florae");
        LOGGER.info("Options: Assertions Enabled = {}, Boostrap = {}, Test = {}, Debug Logging = {}", Helpers.ASSERTIONS_ENABLED, Helpers.BOOTSTRAP_ENVIRONMENT, Helpers.TEST_ENVIRONMENT, LOGGER.isDebugEnabled());

        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::setup);
        bus.addListener(TFCFEntities::onEntityAttributeCreation);

        TFCFItems.ITEMS.register(bus);
        TFCFBlocks.BLOCKS.register(bus);
        TFCFContainerTypes.CONTAINERS.register(bus);
        TFCFEntities.ENTITIES.register(bus);
        TFCFRecipeTypes.RECIPE_TYPES.register(bus);
        TFCFRecipeSerializers.RECIPE_SERIALIZERS.register(bus);
        TFCFSounds.SOUNDS.register(bus);
        TFCFBlockEntities.BLOCK_ENTITIES.register(bus);

        TFCFFeatures.FEATURES.register(bus);
        TFCFFeatures.TRUNK_DECOR.register(bus);
        TFCFFeatures.LEAF_DECOR.register(bus);
        TFCFCarvers.CARVERS.register(bus);
        TFCFPoiType.registerAll(bus);

        Config.init();
        TFCFForgeEventHandler.init();

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            ClientEventHandler.init();
        }
    }

    public void setup(FMLCommonSetupEvent event)
    {
        LOGGER.info("TFCFlorae Common Setup");

        TFCFRock.registerDefaultRocks();

        event.enqueueWork(() -> {
            TFCFInteractionManager.init();
            TFCFDispenserBehaviors.registerDispenserBehaviors();
            TFCFBlocks.registerFlowerPotFlowers();
        });
    }

    /*private static <T> void register(int id, Class<T> cls, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, NetworkEvent.Context> handler)
    {
        CHANNEL.registerMessage(id, cls, encoder, decoder, (packet, context) -> {
            context.get().setPacketHandled(true);
            handler.accept(packet, context.get());
        });
    }

    private static <T> void register(int id, Class<T> cls, Supplier<T> factory, BiConsumer<T, NetworkEvent.Context> handler)
    {
        CHANNEL.registerMessage(id, cls, (packet, buffer) -> {}, buffer -> factory.get(), (packet, context) -> {
            context.get().setPacketHandled(true);
            handler.accept(packet, context.get());
        });
    }*/
}
