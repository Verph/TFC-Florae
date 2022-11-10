package tfcflorae.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blockentities.TFCBlockEntity;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.ICalendarTickable;

import tfcflorae.common.blocks.wood.ISeasonalLeavesBlock;
// todo: don't extend tick counter anymore
public class FruitTreeBlockEntity extends TFCBlockEntity implements ICalendarTickable
{
    public static void serverTick(Level level, BlockPos pos, BlockState state, FruitTreeBlockEntity bush)
    {
        bush.checkForCalendarUpdate();
    }

    private long lastTick; // The last tick this bush was ticked via the block entity's serverTick() method. A delta of > 1 is used to detect time skips
    private long lastUpdateTick; // The last tick the bush block was ticked via IBushBlock#onUpdate()

    public FruitTreeBlockEntity(BlockPos pos, BlockState state)
    {
        this(TFCFBlockEntities.LARGE_FRUIT_TREE.get(), pos, state);
    }

    protected FruitTreeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
        lastTick = Integer.MIN_VALUE;
        lastUpdateTick = Calendars.SERVER.getTicks();
    }

    /**
     * @return The number of ticks since this bush block was ticked in {@link ISeasonalLeavesBlock#onUpdate(Level, BlockPos, BlockState)}
     */
    public long getTicksSinceBushUpdate()
    {
        return Calendars.SERVER.getTicks() - lastUpdateTick;
    }

    @Override
    public void loadAdditional(CompoundTag nbt)
    {
        lastUpdateTick = nbt.getLong("lastUpdateTick");
        lastTick = nbt.getLong("lastTick");
        super.loadAdditional(nbt);
    }

    @Override
    public void saveAdditional(CompoundTag nbt)
    {
        nbt.putLong("lastUpdateTick", lastUpdateTick);
        nbt.putLong("lastTick", lastTick);
        super.saveAdditional(nbt);
    }

    @Override
    public void onCalendarUpdate(long ticks)
    {
        if (level != null && ticks >= ICalendar.TICKS_IN_DAY)
        {
            final BlockState state = level.getBlockState(worldPosition);
            if (state.getBlock() instanceof ISeasonalLeavesBlock bush)
            {
                bush.onUpdate(level, worldPosition, state); // Update the bush
                lastUpdateTick = Calendars.SERVER.getTicks(); // And the current time
                setChanged();
            }
        }
    }

    @Override
    @Deprecated
    public long getLastUpdateTick()
    {
        return lastTick;
    }

    @Override
    @Deprecated
    public void setLastUpdateTick(long tick)
    {
        lastTick = tick;
    }

    public void setLastBushTick(long ticks)
    {
        lastUpdateTick = ticks;
    }
}
