package tfcflorae.util;

import java.util.*;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.world.worldgen.groundcover.WorldGenSurfaceBones;
import tfcflorae.world.worldgen.groundcover.WorldGenSurfaceDriftwood;
import tfcflorae.world.worldgen.groundcover.WorldGenSurfaceFlint;
import tfcflorae.world.worldgen.groundcover.WorldGenSurfacePinecone;
import tfcflorae.world.worldgen.groundcover.WorldGenSurfaceRocks;
import tfcflorae.world.worldgen.groundcover.WorldGenSurfaceSeashells;
import tfcflorae.world.worldgen.groundcover.WorldGenSurfaceTwig;

import static tfcflorae.TFCFlorae.MODID;

/**
 * Seasonally regenerates rocks, sticks, snow, plants, crops and bushes.
 */

@Mod.EventBusSubscriber(modid = MODID)
public class WorldRegenHandlerTFCF
{
    private static final WorldGenSurfaceBones BONE_GEN = new WorldGenSurfaceBones();
    private static final WorldGenSurfaceDriftwood DRIFTWOOD_GEN = new WorldGenSurfaceDriftwood();
    private static final WorldGenSurfaceFlint FLINT_GEN = new WorldGenSurfaceFlint();
    private static final WorldGenSurfacePinecone PINECONE_GEN = new WorldGenSurfacePinecone();
    private static final WorldGenSurfaceRocks ROCKS_GEN = new WorldGenSurfaceRocks();
    private static final WorldGenSurfaceSeashells SEASHELLS_GEN = new WorldGenSurfaceSeashells();
    private static final WorldGenSurfaceTwig TWIG_GEN = new WorldGenSurfaceTwig();
    private static final Random RANDOM = new Random();
    private static final List<ChunkPos> POSITIONS = new LinkedList<>();


    @SubscribeEvent
    public static void onChunkLoad(ChunkDataEvent.Load event)
    {
        ChunkDataTFC chunkDataTFC = ChunkDataTFC.get(event.getChunk());
        if (event.getWorld().provider.getDimension() == 0 && chunkDataTFC.isInitialized() && POSITIONS.size() < 1000)
        {
            //Only run this in the early months of each year
            if (CalendarTFC.CALENDAR_TIME.getMonthOfYear().isWithin(Month.APRIL, Month.JULY) && !chunkDataTFC.isSpawnProtected() && CalendarTFC.CALENDAR_TIME.getTotalYears() > chunkDataTFC.getLastUpdateYear())
            {
                POSITIONS.add(event.getChunk().getPos());
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (!event.world.isRemote && event.phase == TickEvent.Phase.END)
        {
            if (!POSITIONS.isEmpty())
            {
                double tps = Helpers.getTPS(event.world, 0);
                ChunkPos pos = POSITIONS.remove(0);
                if (tps > ConfigTFC.General.WORLD_REGEN.minRegenTps)
                {
                    Chunk chunk = event.world.getChunk(pos.x, pos.z);
                    ChunkDataTFC chunkDataTFC = ChunkDataTFC.get(event.world, pos.getBlock(0, 0, 0));
                    IChunkProvider chunkProvider = event.world.getChunkProvider();
                    IChunkGenerator chunkGenerator = ((ChunkProviderServer) chunkProvider).chunkGenerator;

                    if (CalendarTFC.CALENDAR_TIME.getMonthOfYear().isWithin(Month.APRIL, Month.JULY) && !chunkDataTFC.isSpawnProtected() && CalendarTFC.CALENDAR_TIME.getTotalYears() > chunkDataTFC.getLastUpdateYear())
                    {
                        if (ConfigTFC.General.WORLD_REGEN.sticksRocksModifier > 0)
                        {
                            ROCKS_GEN.generate(RANDOM, pos.x, pos.z, event.world, chunkGenerator, chunkProvider);
                            FLINT_GEN.generate(RANDOM, pos.x, pos.z, event.world, chunkGenerator, chunkProvider);
                            TWIG_GEN.generate(RANDOM, pos.x, pos.z, event.world, chunkGenerator, chunkProvider);
                            DRIFTWOOD_GEN.generate(RANDOM, pos.x, pos.z, event.world, chunkGenerator, chunkProvider);
                        }
                        BONE_GEN.generate(RANDOM, pos.x, pos.z, event.world, chunkGenerator, chunkProvider);
                        PINECONE_GEN.generate(RANDOM, pos.x, pos.z, event.world, chunkGenerator, chunkProvider);
                        SEASHELLS_GEN.generate(RANDOM, pos.x, pos.z, event.world, chunkGenerator, chunkProvider);

                        chunkDataTFC.resetLastUpdateYear();
                    }
                    chunk.markDirty();
                    ((ChunkProviderServer) chunkProvider).queueUnload(chunk);
                }
            }
        }
    }
}
