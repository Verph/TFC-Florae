package tfcflorae.common.entities.ai.frog;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

import tfcflorae.common.entities.ai.TFCFBrain;

import java.util.Set;

public class IsInWaterSensor extends Sensor<LivingEntity>
{
    @Override
    public Set<MemoryModuleType<?>> requires()
    {
        return ImmutableSet.of(TFCFBrain.IS_IN_WATER_MEMORY.get());
    }

    @Override
    protected void doTick(ServerLevel world, LivingEntity entity)
    {
        if (entity.isInWater())
        {
            entity.getBrain().setMemory(TFCFBrain.IS_IN_WATER_MEMORY.get(), Unit.INSTANCE);
        }
        else
        {
            entity.getBrain().eraseMemory(TFCFBrain.IS_IN_WATER_MEMORY.get());
        }
    }
}