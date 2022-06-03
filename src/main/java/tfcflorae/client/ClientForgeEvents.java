package tfcflorae.client;

import com.google.common.base.Stopwatch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

import tfcflorae.TFCFlorae;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.SelfTests;

public class ClientForgeEvents
{
    public static void init()
    {
        final IEventBus bus = MinecraftForge.EVENT_BUS;

        bus.addListener(ClientForgeEvents::onSelfTest);
    }

    public static void onSelfTest(SelfTests.ClientSelfTestEvent event)
    {
        /*if (Helpers.detectAssertionsEnabled())
        {
            final Stopwatch tick = Stopwatch.createStarted();
            ClientSelfTests.validateModels();
            TFCFlorae.LOGGER.info("Client self tests passed in {}", tick.stop());
        }*/
    }
}
