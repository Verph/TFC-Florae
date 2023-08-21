package tfcflorae.common.entities.ai;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.entity.schedule.ScheduleBuilder;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import tfcflorae.TFCFlorae;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.entities.ai.frog.FrogAttackablesSensor;
import tfcflorae.common.entities.ai.frog.IsInWaterSensor;

public class TFCFBrain
{
    public static final DeferredRegister<Activity> ACTIVITIES = DeferredRegister.create(ForgeRegistries.ACTIVITIES, TFCFlorae.MOD_ID);
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_TYPES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, TFCFlorae.MOD_ID);
    public static final DeferredRegister<Schedule> SCHEDULES = DeferredRegister.create(ForgeRegistries.SCHEDULES, TFCFlorae.MOD_ID);
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, TFCFlorae.MOD_ID);
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, TFCFlorae.MOD_ID);

    public static final RegistryObject<Activity> SNIFF = registerActivity("sniff");
    public static final RegistryObject<Activity> INVESTIGATE = registerActivity("investigate");
    public static final RegistryObject<Activity> ROAR = registerActivity("roar");
    public static final RegistryObject<Activity> EMERGE = registerActivity("emerge");
    public static final RegistryObject<Activity> TONGUE = registerActivity("tongue");
    public static final RegistryObject<Activity> DIG = registerActivity("dig");
    public static final RegistryObject<Activity> SWIM = registerActivity("swim");
    public static final RegistryObject<Activity> LAY_SPAWN = registerActivity("lay_spawn");

    public static final RegistryObject<MemoryModuleType<Unit>> IS_IN_WATER_MEMORY = registerMemory("is_in_water", Codec.unit(Unit.INSTANCE));
    public static final RegistryObject<MemoryModuleType<Unit>> IS_PREGNANT = registerMemory("is_pregnant", Codec.unit(Unit.INSTANCE));
    public static final RegistryObject<MemoryModuleType<List<UUID>>> UNREACHABLE_TONGUE_TARGETS = registerMemory("unreachable_tongue_targets");

    public static final RegistryObject<SensorType<TemptingSensor>> FROG_TEMPTATIONS = registerSensorType("frog_temptations", () -> new TemptingSensor(FrogAi.getTemptItems()));
    public static final RegistryObject<SensorType<FrogAttackablesSensor>> FROG_ATTACKABLES = registerSensorType("frog_attackables", FrogAttackablesSensor::new);
    public static final RegistryObject<SensorType<IsInWaterSensor>> IS_IN_WATER_SENSOR = registerSensorType("is_in_water", IsInWaterSensor::new);

    public static final RegistryObject<PoiType> MOTH_NEST = registerPoi("moth_nest", () -> new PoiType("moth_nest", PoiType.getBlockStates(TFCFBlocks.SILKMOTH_NEST.get()), 0, 1));

    public static RegistryObject<PoiType> registerPoi(String name, Supplier<PoiType> supplier)
    {
        return POI_TYPES.register(name, supplier);
    }

    public static <T extends Sensor<?>> RegistryObject<SensorType<T>> registerSensorType(String name, Supplier<T> supplier)
    {
        return SENSOR_TYPES.register(name, () -> new SensorType<>(supplier));
    }

    public static RegistryObject<Activity> registerActivity(String name)
    {
        return ACTIVITIES.register(name, () -> new Activity(name));
    }

    public static <T> RegistryObject<MemoryModuleType<T>> registerMemory(String name)
    {
        return MEMORY_TYPES.register(name, () -> new MemoryModuleType<>(Optional.empty()));
    }

    public static <T> RegistryObject<MemoryModuleType<T>> registerMemory(String name, Codec<T> codec)
    {
        return MEMORY_TYPES.register(name, () -> new MemoryModuleType<>(Optional.of(codec)));
    }

    public static ScheduleBuilder newSchedule()
    {
        return new ScheduleBuilder(new Schedule());
    }

    public static RegistryObject<Schedule> registerSchedule(String name, Supplier<Schedule> supplier)
    {
        return SCHEDULES.register(name, supplier);
    }

    public static void registerAll(IEventBus bus)
    {
        ACTIVITIES.register(bus);
        MEMORY_TYPES.register(bus);
        SCHEDULES.register(bus);
        SENSOR_TYPES.register(bus);
        POI_TYPES.register(bus);
    }
}
