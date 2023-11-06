package tfcflorae.common.blocks.wood;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.fruit.IBushBlock;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.soil.FarmlandBlock;
import net.dries007.tfc.common.blocks.soil.HoeOverlayBlock;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.util.climate.ClimateRange;

import tfcflorae.Config;
import tfcflorae.common.blocks.ICooldown;

public class PalmTrunkBlock extends TFCPalmTrunkBlock implements IBushBlock, HoeOverlayBlock, ICooldown
{
    public static final EnumProperty<Lifecycle> LIFECYCLE = TFCBlockStateProperties.LIFECYCLE;

    public final TFCFWood wood;
    @Nullable public final Supplier<? extends Block> leaves;
    @Nullable public final Supplier<? extends Block> trunk;
    @Nullable public final Supplier<? extends Block> fruitBlock;
    public final Supplier<? extends Item> productItem;
    public final Supplier<ClimateRange> climateRange;
    public final Lifecycle[] lifecycle;
    public long lastUpdateTick;
    public long cooldown;

    public static PalmTrunkBlock create(TFCFWood wood, ExtendedProperties properties, @Nullable Supplier<? extends Block> leaves, @Nullable Supplier<? extends Block> trunk, @Nullable Supplier<? extends Block> fruitBlock, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, Supplier<ClimateRange> climateRange)
    {
        return new PalmTrunkBlock(wood, properties, leaves, trunk, fruitBlock, productItem, lifecycle, climateRange) {};
    }

    public PalmTrunkBlock(TFCFWood wood, ExtendedProperties properties, @Nullable Supplier<? extends Block> leaves, @Nullable Supplier<? extends Block> trunk, @Nullable Supplier<? extends Block> fruitBlock, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, Supplier<ClimateRange> climateRange)
    {
        super(wood, properties, leaves, trunk);
        this.wood = wood;
        this.leaves = leaves;
        this.trunk = trunk;
        this.fruitBlock = fruitBlock;
        this.climateRange = climateRange;
        this.lifecycle = lifecycle;
        this.productItem = productItem;
        this.cooldown = Long.MIN_VALUE;
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(LIFECYCLE, Lifecycle.HEALTHY).setValue(NATURAL, true).setValue(TOP_BLOCK, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(LIFECYCLE));
    }

    @Override
    public Block getTrunk()
    {
        return trunk.get();
    }

    @Override
    public Block getLeaves()
    {
        return leaves.get();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        super.randomTick(state, level, pos, random);

        if (random.nextInt(Config.COMMON.fruitingLeavesUpdateChance.get()) == 0 && state.getValue(AGE) >= 5 && state.getValue(TOP_BLOCK))
        {
            Lifecycle currentLifecycle = state.getValue(LIFECYCLE);
            Lifecycle expectedLifecycle = getLifecycleForCurrentMonth();

            if (state.getValue(NATURAL))
            {
                if (currentLifecycle != expectedLifecycle && (level.getRawBrightness(pos, 0) >= 11 || level.isDay()))
                {
                    lastUpdateTick = Calendars.SERVER.getTicks();
                    onUpdate(level, pos, state);
                }
                if (getCooldown(level, cooldown))
                {
                    for (Direction direction : Direction.Plane.HORIZONTAL)
                    {
                        BlockPos posBelow = pos.relative(direction).below();
                        BlockState stateBelow = level.getBlockState(posBelow);
                        // Only create a new fruit block if the current lifecycle is *fruiting*, otherwise only proceed if there's already an existing fruit block
                        if ((state.getValue(LIFECYCLE) == Lifecycle.FRUITING && stateBelow.isAir()))
                        {
                            setCooldown(level, Math.round(ICalendar.TICKS_IN_DAY * (Calendars.get(level).getCalendarDaysInMonth() * 0.15D)), cooldown); // Wait 3/20th of a month before next update (1.2 days by default)
                            level.setBlock(pos, fruitBlock.get().defaultBlockState().setValue(PalmLeavesBlock.DIRECTION, direction.getOpposite()), Block.UPDATE_ALL);
                        }
                        else if (stateBelow.getBlock().equals(fruitBlock.get()))
                        {
                            setCooldown(level, Mth.ceil(ICalendar.TICKS_IN_DAY * (Calendars.get(level).getCalendarDaysInMonth() * 0.15D)), cooldown); // Wait 3/20th of a month before next update (1.2 days by default)
                            // Increment fruit age
                            if (stateBelow.getValue(PalmFruitBlock.AGE) < 3)
                            {
                                stateBelow.setValue(PalmFruitBlock.AGE, Mth.clamp(stateBelow.getValue(PalmFruitBlock.AGE) + 1, 0, 3));
                            }
                            // Rot
                            else if (random.nextInt(Config.COMMON.fruitingLeavesUpdateChance.get()) == 0)
                            {
                                level.destroyBlock(posBelow, false);
                            }
                        }
                    }
                }
            }
        }
    }

    // this is superficially the same as the StationaryBerryBushBlock onUpdate, we can condense them
    @Override
    public void onUpdate(Level level, BlockPos pos, BlockState state)
    {
        // Fruit tree leaves work like berry bushes, but don't have propagation or growth functionality.
        // Which makes them relatively simple, as then they only need to keep track of their lifecycle.
        if (!state.getValue(NATURAL)) return; // persistent leaves don't grow

        Lifecycle currentLifecycle = state.getValue(LIFECYCLE);
        Lifecycle expectedLifecycle = getLifecycleForCurrentMonth();
        // if we are not working with a plant that is or should be dormant
        if (!checkAndSetDormant(level, pos, state, currentLifecycle, expectedLifecycle))
        {
            // Otherwise, we do a month-by-month evaluation of how the bush should have grown.
            // We only do this up to a year. Why? Because eventually, it will have become dormant, and any 'progress' during that year would've been lost anyway because it would unconditionally become dormant.
            long deltaTicks = Math.min(getTicksSinceBushUpdate(), Calendars.SERVER.getCalendarTicksInYear());
            long currentCalendarTick = Calendars.SERVER.getCalendarTicks();
            long nextCalendarTick = currentCalendarTick - deltaTicks;

            final ClimateRange range = climateRange.get();
            final int hydration = getHydration(level, pos);

            int monthsSpentDying = 0;
            do
            {
                // This always runs at least once. It is called through random ticks, and calendar updates - although calendar updates will only call this if they've waited at least a day, or the average delta between random ticks.
                // Otherwise it will just wait for the next random tick.

                // Jump forward to nextTick.
                // Advance the lifecycle (if the at-the-time conditions were valid)
                nextCalendarTick = Math.min(nextCalendarTick + Calendars.SERVER.getCalendarTicksInMonth(), currentCalendarTick);

                float temperatureAtNextTick = Climate.getTemperature(level, pos, nextCalendarTick, Calendars.SERVER.getCalendarDaysInMonth());
                Lifecycle lifecycleAtNextTick = getLifecycleForMonth(ICalendar.getMonthOfYear(nextCalendarTick, Calendars.SERVER.getCalendarDaysInMonth()));
                if (range.checkBoth(hydration, temperatureAtNextTick, false))
                {
                    currentLifecycle = currentLifecycle.advanceTowards(lifecycleAtNextTick);
                }
                else
                {
                    currentLifecycle = Lifecycle.DORMANT;
                }

                if (lifecycleAtNextTick != Lifecycle.DORMANT && currentLifecycle == Lifecycle.DORMANT)
                {
                    monthsSpentDying++; // consecutive months spent where the conditions were invalid, but they shouldn't've been
                }
                else
                {
                    monthsSpentDying = 0;
                }

            }
            while (nextCalendarTick < currentCalendarTick);

            BlockState newState;

            if (mayDie(level, pos, state, monthsSpentDying))
            {
                newState = Blocks.AIR.defaultBlockState();
            }
            else
            {
                newState = state.setValue(LIFECYCLE, currentLifecycle);
            }

            // And update the block
            if (state != newState)
            {
                level.setBlock(pos, newState, Block.UPDATE_ALL);
                level.blockUpdated(pos, newState.getBlock());
            }
        }
    }

    public long getTicksSinceBushUpdate()
    {
        return Calendars.SERVER.getTicks() - lastUpdateTick;
    }

    @Override
    public void addHoeOverlayInfo(Level level, BlockPos pos, BlockState state, List<Component> text, boolean isDebug)
    {
        final ClimateRange range = climateRange.get();
        text.add(FarmlandBlock.getHydrationTooltip(level, pos, range, false, getHydration(level, pos)));
        text.add(FarmlandBlock.getTemperatureTooltip(level, pos, range, false));
    }

    public boolean mayDie(Level level, BlockPos pos, BlockState state, int monthsSpentDying)
    {
        return monthsSpentDying >= MONTHS_SPENT_DORMANT_TO_DIE;
    }

    public static int getHydration(Level level, BlockPos pos)
    {
        return (int) (Climate.getRainfall(level, pos) / 5);
    }

    public static boolean checkAndSetDormant(Level level, BlockPos pos, BlockState state, Lifecycle current, Lifecycle expected)
    {
        if (expected == Lifecycle.DORMANT)
        {
            // When we're in dormant time, no matter what conditions, or time since appearance, the bush will be dormant.
            if (expected != current)
            {
                level.setBlock(pos, state.setValue(LIFECYCLE, Lifecycle.DORMANT), Block.UPDATE_ALL);
            }
            return true;
        }
        return false;
    }

    public BlockState stateAfterPicking(BlockState state)
    {
        return state.setValue(LIFECYCLE, Lifecycle.HEALTHY);
    }

    public ItemStack getProductItem(Random random)
    {
        return new ItemStack(productItem.get());
    }

    public Lifecycle getLifecycleForCurrentMonth()
    {
        return getLifecycleForMonth(Calendars.SERVER.getCalendarMonthOfYear());
    }

    public Lifecycle getLifecycleForMonth(Month month)
    {
        return lifecycle[month.ordinal()];
    }
}
