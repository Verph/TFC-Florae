package tfcflorae.common.entities.ai;

import java.util.function.Supplier;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.entity.schedule.ScheduleBuilder;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import tfcflorae.TFCFlorae;
import tfcflorae.common.blocks.TFCFBlocks;

public class TFCFPoiType
{
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, TFCFlorae.MOD_ID);

    public static final RegistryObject<PoiType> MOTH_NEST = registerPoi("moth_nest", () -> new PoiType("moth_nest", PoiType.getBlockStates(TFCFBlocks.SILKMOTH_NEST.get()), 0, 1));

    public static RegistryObject<PoiType> registerPoi(String name, Supplier<PoiType> supplier)
    {
        return POI_TYPES.register(name, supplier);
    }

    public static ScheduleBuilder newSchedule()
    {
        return new ScheduleBuilder(new Schedule());
    }

    public static void registerAll(IEventBus bus)
    {
        POI_TYPES.register(bus);
    }
}
