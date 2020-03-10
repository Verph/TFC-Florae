package tfcelementia.objects.te;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.nbt.NBTTagCompound;

//import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendarTickable;

import tfcelementia.objects.blocks.agriculture.BlockCropTFCE;

@ParametersAreNonnullByDefault
public class TECropBaseTFCE extends TETickCounter implements ICalendarTickable
{
    private static final String NBT_LAST_TICK_CAL_CHECKED = "lastTickCalChecked";

    protected long lastTickCalChecked;

    public TECropBaseTFCE()
    {
        lastTickCalChecked = CalendarTFC.PLAYER_TIME.getTicks();
    }

    @Override
    public void onCalendarUpdate(long playerTickDelta)
    {
        BlockCropTFCE block = (BlockCropTFCE) getBlockType();
        block.checkGrowth(world, pos, world.getBlockState(pos), world.rand);
    }

    @Override
    public long getLastUpdateTick()
    {
        return lastTickCalChecked;
    }

    @Override
    public void setLastUpdateTick(long tick)
    {
        lastTickCalChecked = tick;
        markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        if (nbt.hasKey(NBT_LAST_TICK_CAL_CHECKED))
            lastTickCalChecked = nbt.getLong(NBT_LAST_TICK_CAL_CHECKED);
        super.readFromNBT(nbt);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setLong(NBT_LAST_TICK_CAL_CHECKED, lastTickCalChecked);
        return super.writeToNBT(nbt);
    }
}