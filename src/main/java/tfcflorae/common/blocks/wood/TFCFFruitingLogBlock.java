package tfcflorae.common.blocks.wood;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.IForgeBlockExtension;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.plant.fruit.SeasonalPlantBlock;
import net.dries007.tfc.common.blocks.soil.FarmlandBlock;
import net.dries007.tfc.common.blocks.soil.HoeOverlayBlock;
import net.dries007.tfc.common.blocks.wood.ILeavesBlock;
import net.dries007.tfc.common.blocks.wood.LogBlock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.util.climate.ClimateRange;
import tfcflorae.common.blockentities.FruitTreeBlockEntity;
import tfcflorae.common.blockentities.TFCFBlockEntities;

public class TFCFFruitingLogBlock extends SeasonalPlantBlock implements IForgeBlockExtension, ILeavesBlock, ISeasonalLeavesBlock, HoeOverlayBlock
{
    protected static final VoxelShape COLLISION_SHAPE = box(0, 0, 0, 16, 16, 16);

    /**
     * Taking into account only environment rainfall, on a scale [0, 100]
     */
    public static int getHydration(Level level, BlockPos pos)
    {
        return (int) (Climate.getRainfall(level, pos) / 5);
    }

    public static final IntegerProperty STAGE = TFCBlockStateProperties.STAGE_2;
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final BooleanProperty NATURAL = TFCBlockStateProperties.NATURAL;
    public static final EnumProperty<Lifecycle> LIFECYCLE = TFCBlockStateProperties.LIFECYCLE;
    private static final int MONTHS_SPENT_DORMANT_TO_DIE = 4;

    protected final Supplier<? extends Item> productItem;
    protected final Supplier<ClimateRange> climateRange;
    private final Lifecycle[] lifecycle;
    @Nullable private final Supplier<? extends Block> stripped;
    private final ExtendedProperties properties;

    public TFCFFruitingLogBlock(ExtendedProperties properties, @Nullable Supplier<? extends Block> stripped, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, Supplier<ClimateRange> climateRange)
    {
        super(properties, climateRange, productItem, lifecycle);
        this.stripped = stripped;
        this.properties = properties;
        this.climateRange = climateRange;
        this.lifecycle = lifecycle;
        this.productItem = productItem;

        registerDefaultState(getStateDefinition().any().setValue(AXIS, Direction.Axis.Y).setValue(LIFECYCLE, Lifecycle.HEALTHY).setValue(NATURAL, false));
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return Shapes.block();
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return COLLISION_SHAPE;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type)
    {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        //super.createBlockStateDefinition(builder.add(AXIS, NATURAL, LIFECYCLE));
        builder.add(AXIS, NATURAL, LIFECYCLE);
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos)
    {
        // Modified from the super() method, including the Forge patch, to add the 2x hardness in natural state modifier.
        final float baseSpeed = (state.getValue(NATURAL) ? 2 : 1) * state.getDestroySpeed(level, pos);
        if (baseSpeed == -1.0F)
        {
            return 0.0F;
        }
        else
        {
            final int toolModifier = ForgeHooks.isCorrectToolForDrops(state, player) ? 30 : 100;
            return player.getDigSpeed(state, pos) / baseSpeed / (float) toolModifier;
        }
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction action, boolean simulate)
    {
        if (context.getItemInHand().canPerformAction(action) && action == ToolActions.AXE_STRIP && stripped != null)
        {
            return Helpers.copyProperties(stripped.get().defaultBlockState(), state);
        }
        return null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rotation)
    {
        return rotatePillar(state, rotation);
    }

    public static BlockState rotatePillar(BlockState state, Rotation rotation)
    {
        switch(rotation)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
            switch((Direction.Axis)state.getValue(AXIS))
            {
                case X:
                    return state.setValue(AXIS, Direction.Axis.Z);
                case Z:
                    return state.setValue(AXIS, Direction.Axis.X);
                default:
                    return state;
            }
            default:
                return state;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
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
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (state.getValue(LIFECYCLE) == Lifecycle.FRUITING && productItem != null)
        {
            level.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.PLAYERS, 1.0f, level.getRandom().nextFloat() + 0.7f + 0.3f);
            if (!level.isClientSide())
            {
                ItemHandlerHelper.giveItemToPlayer(player, getProductItem(level.random));
                level.setBlockAndUpdate(pos, stateAfterPicking(state));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public BlockState stateAfterPicking(BlockState state)
    {
        return state.setValue(LIFECYCLE, Lifecycle.HEALTHY);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        ISeasonalLeavesBlock.randomTick(this, state, level, pos, random);
    }

    @Override
    public void onUpdate(Level level, BlockPos pos, BlockState state)
    {
        // Fruit tree leaves work like berry bushes, but don't have propagation or growth functionality.
        // Which makes them relatively simple, as then they only need to keep track of their lifecycle.
        if (state.getValue(NATURAL) == false) return; // logs placed by players don't grow
        if (level.getBlockEntity(pos) instanceof FruitTreeBlockEntity leaves)
        {
            Lifecycle currentLifecycle = state.getValue(LIFECYCLE);
            Lifecycle expectedLifecycle = getLifecycleForCurrentMonth();
            // if we are not working with a plant that is or should be dormant
            if (!checkAndSetDormant(level, pos, state, currentLifecycle, expectedLifecycle))
            {
                // Otherwise, we do a month-by-month evaluation of how the bush should have grown.
                // We only do this up to a year. Why? Because eventually, it will have become dormant, and any 'progress' during that year would've been lost anyway because it would unconditionally become dormant.
                long deltaTicks = Math.min(leaves.getTicksSinceBushUpdate(), Calendars.SERVER.getCalendarTicksInYear());
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

    @Override
    public ItemStack getProductItem(Random random)
    {
        return new ItemStack(productItem.get());
    }

    @Override
    protected Lifecycle getLifecycleForCurrentMonth()
    {
        return getLifecycleForMonth(Calendars.SERVER.getCalendarMonthOfYear());
    }

    @Override
    protected Lifecycle getLifecycleForMonth(Month month)
    {
        return lifecycle[month.ordinal()];
    }

    @Override
    public boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return true;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return true;
    }
}
