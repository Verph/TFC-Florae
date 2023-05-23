package tfcflorae.common.blocks.plant;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.PlantBlock;
import net.dries007.tfc.common.blocks.plant.fruit.IBushBlock;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.soil.FarmlandBlock;
import net.dries007.tfc.common.blocks.soil.HoeOverlayBlock;
import net.dries007.tfc.common.blocks.wood.ILeavesBlock;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.util.climate.ClimateRange;
import net.dries007.tfc.util.registry.RegistryPlant;

public abstract class PitahayaBlock extends PlantBlock implements ILeavesBlock, IBushBlock, HoeOverlayBlock
{
    /**
     * Taking into account only environment rainfall, on a scale [0, 100]
     */
    public static int getHydration(Level level, BlockPos pos)
    {
        return (int) (Climate.getRainfall(level, pos) / 5);
    }

    public static final EnumProperty<Lifecycle> LIFECYCLE = TFCBlockStateProperties.LIFECYCLE;
    private static final int MONTHS_SPENT_DORMANT_TO_DIE = 4;

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected static final VoxelShape NORTH_SHAPE = box(0.0, 0.0, 12.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape SOUTH_SHAPE = box(0.0, 0.0, 0.0, 16.0, 16.0, 4.0);
    protected static final VoxelShape WEST_SHAPE = box(12.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EAST_SHAPE = box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0);
    public static final BooleanProperty TOP = BooleanProperty.create("top");

    protected final Supplier<? extends Item> productItem;
    protected final Supplier<ClimateRange> climateRange;
    private final Lifecycle[] lifecycle;
    private long lastUpdateTick;

    protected static final Map<Direction, VoxelShape> SHAPES = ImmutableMap.of(Direction.NORTH, NORTH_SHAPE, Direction.SOUTH, SOUTH_SHAPE, Direction.WEST, WEST_SHAPE, Direction.EAST, EAST_SHAPE);

    public static PitahayaBlock create(RegistryPlant plant, ExtendedProperties properties, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, Supplier<ClimateRange> climateRange)
    {
        return new PitahayaBlock(properties, productItem, lifecycle, climateRange)
        {
            @Override
            public RegistryPlant getPlant()
            {
                return plant;
            }
        };
    }

    protected PitahayaBlock(ExtendedProperties properties, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, Supplier<ClimateRange> climateRange)
    {
        super(properties);

        Preconditions.checkArgument(lifecycle.length == 12, "Lifecycle length must be 12");

        this.climateRange = climateRange;
        this.lifecycle = lifecycle;
        this.productItem = productItem;

        lastUpdateTick = Calendars.SERVER.getTicks();

        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(TOP, false).setValue(LIFECYCLE, Lifecycle.HEALTHY));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
    {
        if (!canSurvive(state, level, pos))
        {
            level.destroyBlock(pos, false);
        }
        if (level.getBlockState(pos.above()).getMaterial().isSolid() && !(level.getBlockState(pos.above()).getBlock() instanceof PitahayaBlock))
        {
            state.setValue(TOP, true);
        }
        if (!level.getBlockState(pos.above()).getMaterial().isSolid())
        {
            state.setValue(TOP, false);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        if (level.getBlockState(currentPos.above()).getMaterial().isSolid() && !(level.getBlockState(currentPos.above()).getBlock() instanceof PitahayaBlock))
        {
            state.setValue(TOP, true);
        }
        if (!level.getBlockState(currentPos.above()).getMaterial().isSolid())
        {
            state.setValue(TOP, false);
        }
        if (level instanceof ServerLevel server && getLifecycleForCurrentMonth() != getLifecycleForMonth(Calendars.SERVER.getCalendarMonthOfYear()))
        {
            onUpdate(server, currentPos, state);
        }
        // Must be attached to a log
        if (direction.getOpposite() == state.getValue(FACING) && !canSurvive(state, level, currentPos))
        {
            return Blocks.AIR.defaultBlockState();
        }
        return state;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockState attachedState = level.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));
        return (Helpers.isBlock(attachedState, BlockTags.LOGS) || Helpers.isBlock(attachedState, TFCTags.Blocks.WILD_CROP_GROWS_ON)) && (level.getBlockState(pos.below()).is(this) || (Helpers.isBlock(level.getBlockState(pos.below()), TFCTags.Blocks.GRASS_PLANTABLE_ON) || Helpers.isBlock(level.getBlockState(pos.below()), TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(level.getBlockState(pos.below()), TFCTags.Blocks.WILD_CROP_GROWS_ON)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPES.get(state.getValue(FACING));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Direction direction = context.getClickedFace();
        if (direction.getAxis() != Direction.Axis.Y)
        {
            return updateStateWithCurrentMonth(defaultBlockState()).setValue(FACING, direction);
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(FACING, TOP, LIFECYCLE));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        BlockState attachedState = level.getBlockState(pos.above().relative(state.getValue(FACING).getOpposite()));
        if (getLifecycleForCurrentMonth() != getLifecycleForMonth(Calendars.SERVER.getCalendarMonthOfYear()))
        {
            onUpdate(level, pos, state);
            lastUpdateTick = Calendars.SERVER.getTicks();
        }
        if (random.nextDouble() < TFCConfig.SERVER.plantGrowthChance.get() && state.getValue(AGE) >= 3 && (level.getBlockState(pos.above()).isAir() || EnvironmentHelpers.isWorldgenReplaceable(level.getBlockState(pos.above()))) && 
            (Helpers.isBlock(attachedState, BlockTags.LOGS) || Helpers.isBlock(attachedState, TFCTags.Blocks.WILD_CROP_GROWS_ON)) && (level.getBlockState(pos.below()).is(this) || (Helpers.isBlock(level.getBlockState(pos.below()), TFCTags.Blocks.GRASS_PLANTABLE_ON) || Helpers.isBlock(level.getBlockState(pos.below()), TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(level.getBlockState(pos.below()), TFCTags.Blocks.WILD_CROP_GROWS_ON))))
        {
            level.setBlock(pos.above(), updateStateWithCurrentMonth(defaultBlockState().setValue(FACING, state.getValue(FACING))), 2);
        }
        super.randomTick(state, level, pos, random);
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity)
    {
        entity.hurt(DamageSource.CACTUS, 1.0f);
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
        return monthsSpentDying >= MONTHS_SPENT_DORMANT_TO_DIE;
    }

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
}
