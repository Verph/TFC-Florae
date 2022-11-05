package tfcflorae;

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
import tfcflorae.common.container.TFCFContainerTypes;
import tfcflorae.common.entities.TFCFEntities;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.common.network.Packets;
import tfcflorae.common.recipes.TFCFRecipeSerializers;
import tfcflorae.common.recipes.TFCFRecipeTypes;
import tfcflorae.util.DispenserBehaviors;
import tfcflorae.util.TFCFInteractionManager;
import tfcflorae.world.feature.TFCFFeatures;

@Mod(TFCFlorae.MOD_ID)
public class TFCFlorae
{
    public static final String MOD_ID = "tfcflorae";
    public static final String MOD_NAME = "TFCFlorae";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TFCFlorae()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::setup);
        bus.addListener(TFCFEntities::onEntityAttributeCreation);

        TFCFItems.ITEMS.register(bus);
        TFCFBlocks.BLOCKS.register(bus);
        TFCFContainerTypes.CONTAINERS.register(bus);
        TFCFEntities.ENTITIES.register(bus);
        TFCFFeatures.FEATURES.register(bus);
        TFCFRecipeTypes.RECIPE_TYPES.register(bus);
        TFCFRecipeSerializers.RECIPE_SERIALIZERS.register(bus);
        TFCFBlockEntities.BLOCK_ENTITIES.register(bus);
        TFCFSounds.SOUNDS.register(bus);

        Packets.init();

        bus.addListener(this::setup);

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            ClientEventHandler.init();
        }
    }

    public void setup(FMLCommonSetupEvent event)
    {
        LOGGER.info("TFCFlorae Common Setup");

<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        TFCFInteractionManager.registerDefaultInteractions();
=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
        TFCFRock.registerDefaultRocks();
>>>>>>> Stashed changes

        event.enqueueWork(() -> {
            TFCFInteractionManager.init();
            DispenserBehaviors.registerDispenserBehaviors();
            TFCFBlocks.registerFlowerPotFlowers();
        });
    }
}
