package tfcflorae.common.blocks.plant;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import com.google.common.base.Preconditions;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.*;
import net.dries007.tfc.common.blocks.plant.fruit.IBushBlock;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.soil.FarmlandBlock;
import net.dries007.tfc.common.blocks.soil.HoeOverlayBlock;
import net.dries007.tfc.common.blocks.wood.ILeavesBlock;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.util.climate.ClimateRange;

public class FruitingTopPlantBlock extends TopPlantBlock implements ILeavesBlock, IBushBlock, HoeOverlayBlock
{
    /**
     * Taking into account only environment rainfall, on a scale [0, 100]
     */
    public static int getHydration(Level level, BlockPos pos)
    {
        return (int) (Climate.getRainfall(level, pos) / 5);
    }

    public static final EnumProperty<Lifecycle> LIFECYCLE = TFCBlockStateProperties.LIFECYCLE;
    public static final BooleanProperty NATURAL = TFCBlockStateProperties.NATURAL;
    private static final int MONTHS_SPENT_DORMANT_TO_DIE = 4;

    private final Supplier<? extends Block> bodyBlock;
    private final ExtendedProperties properties;
    protected final Supplier<? extends Item> productItem;
    protected final Supplier<ClimateRange> climateRange;
    private final Lifecycle[] lifecycle;
    private long lastUpdateTick;

    public FruitingTopPlantBlock(ExtendedProperties properties, Supplier<? extends Block> bodyBlock, VoxelShape shape, Direction direction, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, Supplier<ClimateRange> climateRange)
    {
        super(properties, bodyBlock, direction, shape);

        Preconditions.checkArgument(lifecycle.length == 12, "Lifecycle length must be 12");

        this.bodyBlock = bodyBlock;
        this.properties = properties;
        this.climateRange = climateRange;
        this.lifecycle = lifecycle;
        this.productItem = productItem;

        lastUpdateTick = Calendars.SERVER.getTicks();

        registerDefaultState(getStateDefinition().any().setValue(LIFECYCLE, Lifecycle.HEALTHY).setValue(NATURAL, false));
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(NATURAL, LIFECYCLE));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (getLifecycleForCurrentMonth() != getLifecycleForMonth(Calendars.SERVER.getCalendarMonthOfYear()))
        {
            onUpdate(level, pos, state);
            lastUpdateTick = Calendars.SERVER.getTicks();
        }
    }

    /**
     * Checks if the plant is outside its growing season, and if so sets it to dormant.
     *
     * @return if the plant is dormant
     */
    public static boolean checkAndSetDormant(Level level, BlockPos pos, BlockState state, Lifecycle current, Lifecycle expected)
    {
        if (expected == Lifecycle.DORMANT)
        {
            // When we're in dormant time, no matter what conditions, or time since appearance, the bush will be dormant.
            if (expected != current)
            {
                level.setBlockAndUpdate(pos, state.setValue(LIFECYCLE, Lifecycle.DORMANT));
            }
            return true;
        }
        return false;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        if (level instanceof ServerLevel server && getLifecycleForCurrentMonth() != getLifecycleForMonth(Calendars.SERVER.getCalendarMonthOfYear()))
        {
            onUpdate(server, currentPos, state);
        }
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (state.getValue(LIFECYCLE) == Lifecycle.FRUITING && productItem != null)
        {
            level.playSound(player, pos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.PLAYERS, 1.0f, level.getRandom().nextFloat() + 0.7f + 0.3f);
            if (!level.isClientSide())
            {
                ItemHandlerHelper.giveItemToPlayer(player, getProductItem(level.random));
                level.setBlockAndUpdate(pos, stateAfterPicking(state));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    // this is superficially the same as the StationaryBerryBushBlock onUpdate, we can condense them
    @Override
    public void onUpdate(Level level, BlockPos pos, BlockState state)
    {
        // Fruit tree leaves work like berry bushes, but don't have propagation or growth functionality.
        // Which makes them relatively simple, as then they only need to keep track of their lifecycle.
        // if (state.getValue(NATURAL) == false) return; // plants placed by players don't grow

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

            } while (nextCalendarTick < currentCalendarTick);

            BlockState newState;

            if (mayDie(level, pos, state, monthsSpentDying))
            {
                newState = state.setValue(LIFECYCLE, Lifecycle.DORMANT);
            }
            else
            {
                newState = state.setValue(LIFECYCLE, currentLifecycle);
            }

            // And update the block
            if (state != newState)
            {
                level.setBlock(pos, newState, 3);
            }
        }
    }

    public long getTicksSinceBushUpdate()
    {
        return Calendars.SERVER.getTicks() - lastUpdateTick;
    }

    public BlockState stateAfterPicking(BlockState state)
    {
        return state.setValue(LIFECYCLE, Lifecycle.HEALTHY);
    }

    /**
     * Can this bush die, given that it spent {@code monthsSpentDying} consecutive months in a dormant state, when it should've been in a non-dormant state.
     */
    protected boolean mayDie(Level level, BlockPos pos, BlockState state, int monthsSpentDying)
    {
        return monthsSpentDying >= MONTHS_SPENT_DORMANT_TO_DIE && state.getValue(NATURAL);
    }

    @Override
    public void addHoeOverlayInfo(Level level, BlockPos pos, BlockState state, List<Component> text, boolean isDebug)
    {
        final ClimateRange range = climateRange.get();
        text.add(FarmlandBlock.getHydrationTooltip(level, pos, range, false, getHydration(level, pos)));
        text.add(FarmlandBlock.getTemperatureTooltip(level, pos, range, false));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return true; // Not for the purposes of leaf decay, but for the purposes of seasonal updates
    }

    public ItemStack getProductItem(Random random)
    {
        return new ItemStack(productItem.get());
    }

    protected Lifecycle getLifecycleForCurrentMonth()
    {
        return getLifecycleForMonth(Calendars.SERVER.getCalendarMonthOfYear());
    }

    protected Lifecycle getLifecycleForMonth(Month month)
    {
        return lifecycle[month.ordinal()];
    }

    static ToIntFunction<BlockState> emission(int brightness, boolean glows)
    {
        if (glows)
        {
            return (state) -> {
                return state.getValue(LIFECYCLE) == Lifecycle.FRUITING ? brightness : 0;
            };
        }
        else
        {
            return (state) -> {
                return 0;
            };
        }
    }

    @Override
    protected Block getBodyBlock()
    {
        return bodyBlock.get();
    }
}
