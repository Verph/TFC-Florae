package tfcflorae.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blockentities.TFCBlockEntity;
import net.dries007.tfc.util.calendar.Calendars;

public class TFCFTickCounterBlockEntity extends TFCBlockEntity
{
    public static void reset(Level level, BlockPos pos)
    {
        level.getBlockEntity(pos, TFCFBlockEntities.TICK_COUNTER.get()).ifPresent(TFCFTickCounterBlockEntity::resetCounter);
    }

    protected long lastUpdateTick = Integer.MIN_VALUE;

    public TFCFTickCounterBlockEntity(BlockPos pos, BlockState state)
    {
        this(TFCFBlockEntities.TICK_COUNTER.get(), pos, state);
    }

    protected TFCFTickCounterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public long getTicksSinceUpdate()
    {
        assert level != null;
        return Calendars.get(level).getTicks() - lastUpdateTick;
    }

    public void setLastUpdateTick(long tick)
    {
        lastUpdateTick = tick;
        setChanged();
    }

    public long getLastUpdateTick()
    {
        return lastUpdateTick;
    }

    public void resetCounter()
    {
        lastUpdateTick = Calendars.SERVER.getTicks();
        setChanged();
    }

    public void reduceCounter(long amount)
    {
        lastUpdateTick += amount;
        setChanged();
    }

    @Override
    public void loadAdditional(CompoundTag nbt)
    {
        lastUpdateTick = nbt.getLong("tick");
        super.loadAdditional(nbt);
    }

    @Override
    public void saveAdditional(CompoundTag nbt)
    {
        nbt.putLong("tick", lastUpdateTick);
        super.saveAdditional(nbt);
    }
}
