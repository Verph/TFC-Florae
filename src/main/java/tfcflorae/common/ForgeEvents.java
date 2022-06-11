package tfcflorae.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.PacketDistributor;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.network.Packets;
import net.dries007.tfc.util.events.StartFireEvent;

public class ForgeEvents
{
    public static void init()
    {
        final IEventBus bus = MinecraftForge.EVENT_BUS;

        bus.addListener(ForgeEvents::onBiomeLoad);
        bus.addListener(ForgeEvents::onFireStart);
        bus.addListener(ForgeEvents::addReloadListeners);
        bus.addListener(ForgeEvents::onDataPackSync);
    }

    public static void onBiomeLoad(BiomeLoadingEvent event)
    {

    }

    public static void addReloadListeners(AddReloadListenerEvent event)
    {
    }

    public static void onDataPackSync(OnDatapackSyncEvent event)
    {
        final ServerPlayer player = event.getPlayer();
        final PacketDistributor.PacketTarget target = player == null ? PacketDistributor.ALL.noArg() : PacketDistributor.PLAYER.with(() -> player);
    }

    public static void onFireStart(StartFireEvent event)
    {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        Block block = state.getBlock();
    }
}
