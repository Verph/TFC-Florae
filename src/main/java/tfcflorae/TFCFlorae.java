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
import tfcflorae.common.ForgeEvents;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.entities.TFCFEntities;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.common.network.Packets;
import tfcflorae.common.recipes.RecipeSerializers;
import tfcflorae.common.recipes.RecipeTypes;

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
        TFCFEntities.ENTITIES.register(bus);
        RecipeTypes.RECIPE_TYPES.register(bus);
        RecipeSerializers.RECIPE_SERIALIZERS.register(bus);

        Packets.init();

        bus.addListener(this::setup);

        ForgeEvents.init();
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            ClientEventHandler.init();
            ClientForgeEvents.init();
        }
    }

    public void setup(FMLCommonSetupEvent event)
    {
    }
}
