package tfcflorae.common.blocks;

import net.minecraft.world.level.Level;

import net.dries007.tfc.util.calendar.Calendars;

public interface ICooldown
{
    default boolean getCooldown(Level level, long cooldown)
    {
        return Calendars.get(level).getTicks() < cooldown;
    }

    default void setCooldown(Level level, long timeToWait, long cooldown)
    {
        final long ticks = Calendars.SERVER.getTicks();
        if (ticks > cooldown)
        {
            cooldown = ticks + timeToWait;
        }
    }
}
