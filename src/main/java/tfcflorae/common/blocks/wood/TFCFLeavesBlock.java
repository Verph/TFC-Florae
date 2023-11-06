package tfcflorae.common.blocks.wood;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.google.common.base.Preconditions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.fruit.IBushBlock;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.soil.FarmlandBlock;
import net.dries007.tfc.common.blocks.soil.HoeOverlayBlock;
import net.dries007.tfc.common.blocks.wood.TFCLeavesBlock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.calendar.Season;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.util.climate.ClimateModel;
import net.dries007.tfc.util.climate.ClimateRange;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;

import tfcflorae.Config;
import tfcflorae.common.blockentities.SilkmothNestBlockEntity;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.entities.Silkmoth;
import tfcflorae.common.entities.TFCFEntities;

public abstract class TFCFLeavesBlock extends TFCLeavesBlock implements IBushBlock, HoeOverlayBlock
{
    /**
     * Any leaf block that spends four consecutive months dormant when it shouldn't be, should die.
     * Since most bushes have a 7 month non-dormant cycle, this means that it just needs to be in valid conditions for about 1 month a year in order to not die.
     * It won't produce (it needs more months to properly advance the cycle from dormant -> healthy -> flowering -> fruiting, requiring 4 months at least), but it won't outright die.
     */
    public static final int MONTHS_SPENT_DORMANT_TO_DIE = 4;
    public static final EnumProperty<Lifecycle> LIFECYCLE = TFCBlockStateProperties.LIFECYCLE;
    public static final FluidProperty FLUID = TFCBlockStateProperties.ALL_WATER;

    public static TFCFLeavesBlock create(ExtendedProperties properties, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, int maxDecayDistance, Supplier<ClimateRange> climateRange)
    {
        return create(properties, productItem, lifecycle, maxDecayDistance, climateRange, null, null, null);
    }

    public static TFCFLeavesBlock create(ExtendedProperties properties, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, int maxDecayDistance, Supplier<ClimateRange> climateRange, @Nullable Supplier<? extends Block> fallenLeaves, @Nullable Supplier<? extends Block> fallenTwig, @Nullable Supplier<? extends Block> sapling)
    {
        final IntegerProperty distanceProperty = getDistanceProperty(maxDecayDistance);
        return new TFCFLeavesBlock(properties, productItem, lifecycle, maxDecayDistance, climateRange, fallenLeaves, fallenTwig, sapling)
        {
            @Override
            protected IntegerProperty getDistanceProperty()
            {
                return distanceProperty;
            }
        };
    }

    public static IntegerProperty getDistanceProperty(int maxDecayDistance)
    {
        if (maxDecayDistance >= 7 && maxDecayDistance < 7 + TFCBlockStateProperties.DISTANCES.length)
        {
            return TFCBlockStateProperties.DISTANCES[maxDecayDistance - 7 + 1]; // we select one higher than max
        }
        throw new IllegalArgumentException("No property set for distance: " + maxDecayDistance);
    }

    /* The maximum value of the decay property. */
    public final int maxDecayDistance;
    public final ExtendedProperties properties;
    public final Supplier<? extends Item> productItem;
    public final Supplier<ClimateRange> climateRange;
    public final Lifecycle[] lifecycle;
    @Nullable public final Supplier<? extends Block> fallenLeaves;
    @Nullable public final Supplier<? extends Block> fallenTwig;
    @Nullable public final Supplier<? extends Block> sapling;
    public long lastUpdateTick;

    public TFCFLeavesBlock(ExtendedProperties properties, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, int maxDecayDistance, Supplier<ClimateRange> climateRange, @Nullable Supplier<? extends Block> fallenLeaves, @Nullable Supplier<? extends Block> fallenTwig, @Nullable Supplier<? extends Block> sapling)
    {
        super(properties, maxDecayDistance, fallenLeaves, fallenTwig);

        Preconditions.checkArgument(lifecycle.length == 12, "Lifecycle length must be 12");

        this.maxDecayDistance = maxDecayDistance;
        this.properties = properties;
        this.climateRange = climateRange;
        this.lifecycle = lifecycle;
        this.productItem = productItem;
        this.fallenLeaves = fallenLeaves;
        this.fallenTwig = fallenTwig;
        this.sapling = sapling;

        lastUpdateTick = Calendars.SERVER.getTicks();

        registerDefaultState(getStateDefinition().any().setValue(PERSISTENT, false).setValue(LIFECYCLE, Lifecycle.HEALTHY));
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    /*@Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return EntityBlockExtension.super.newBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> givenType)
    {
        return EntityBlockExtension.super.getTicker(level, state, givenType);
    }*/

    /**
     * The reason this is not a constructor parameter is because the super class (Block) will use this directly, and nothing else is initialized in time.
     */
    protected abstract IntegerProperty getDistanceProperty();

    // Start of mixing Seasonal FruitTreeLeavesBlock

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
                level.setBlock(pos, stateAfterPicking(state), Block.UPDATE_ALL);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random rand)
    {
        super.tick(state, level, pos, rand);
        int distance = updateDistanceNew(level, pos);
        if (distance > maxDecayDistance)
        {
            if (!state.getValue(PERSISTENT))
            {
                if (!TFCConfig.SERVER.enableLeavesDecaySlowly.get())
                {
                    level.removeBlock(pos, false);
                    doParticles(level, pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), 1);
                }
                else
                {
                    // max + 1 means it must decay next random tick
                    level.setBlock(pos, state.setValue(getDistanceProperty(), maxDecayDistance + 1), Block.UPDATE_ALL);
                }
            }
            else
            {
                level.setBlock(pos, state.setValue(getDistanceProperty(), maxDecayDistance), Block.UPDATE_ALL);
            }
        }
        else
        {
            level.setBlock(pos, state.setValue(getDistanceProperty(), distance), Block.UPDATE_ALL);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        super.randomTick(state, level, pos, random);
        if (state.getValue(getDistanceProperty()) > maxDecayDistance && !state.getValue(PERSISTENT))
        {
            level.removeBlock(pos, false);
            doParticles(level, pos.getX() + random.nextFloat(), pos.getY() + random.nextFloat(), pos.getZ() + random.nextFloat(), 1);
        }

        if (random.nextInt(Config.COMMON.fruitingLeavesUpdateChance.get()) == 0 && !state.getValue(PERSISTENT))
        {
            Lifecycle currentLifecycle = state.getValue(LIFECYCLE);
            Lifecycle expectedLifecycle = getLifecycleForCurrentMonth();

            if (currentLifecycle != expectedLifecycle && (level.getRawBrightness(pos, 0) >= 11 || level.isDay()))
            {
                onUpdate(level, pos, state);
                lastUpdateTick = Calendars.SERVER.getTicks();
            }
            if (this == TFCFBlocks.WOODS_SEASONAL_LEAVES.get(TFCFWood.MULBERRY).get() && !level.isDay() && state.getValue(LIFECYCLE) != Lifecycle.DORMANT)
            {
                Month currentMonth = Calendars.SERVER.getCalendarMonthOfYear();
                Season season = currentMonth.getSeason();
                if (random.nextInt(ICalendar.TICKS_IN_DAY + SilkmothNestBlockEntity.MIN_OCCUPATION_TICKS_NECTAR) == 0 && season == Season.SUMMER && Climate.getTemperature(level, pos) >= 2)
                {
                    boolean flag = !level.getBlockState(pos).getCollisionShape(level, pos).isEmpty();
                    Direction direction = Direction.getRandom(random);
                    Entity entity = TFCFEntities.SILKMOTH.get().create(level);
                    if (entity != null)
                    {
                        if (entity instanceof Silkmoth moth)
                        {
                            if (moth.getSavedTargetPos() != null && !moth.hasSavedTargetPos() && level.random.nextFloat() < 0.9F)
                            {
                                moth.setSavedTargetPos(moth.getSavedTargetPos());
                            }
                            float f = entity.getBbWidth();
                            double d3 = flag ? 0.0D : 0.55D + (double)(f / 2.0F);
                            double d0 = (double)pos.getX() + 0.5D + d3 * (double)direction.getStepX();
                            double d1 = (double)pos.getY() + 0.5D - (double)(entity.getBbHeight() / 2.0F);
                            double d2 = (double)pos.getZ() + 0.5D + d3 * (double)direction.getStepZ();
                            entity.moveTo(d0, d1, d2, entity.getYRot(), entity.getXRot());
                        }
                        level.playSound((Player)null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.addFreshEntity(entity);
                    }
                }
            }
        }

        if (Config.COMMON.leavesSaplingPlacementChance.get() > 0)
        {
            Season currentSeason = Calendars.get(level).getCalendarMonthOfYear().getSeason();
            if ((currentSeason == Season.FALL || currentSeason == Season.SPRING || state.getValue(LIFECYCLE) == Lifecycle.FRUITING) && level.getBlockState(pos.below()).isAir())
            {
                final ChunkDataProvider provider = ChunkDataProvider.get(level);
                final ChunkData data = provider.get(level, pos);

                final float rainfall = data.getRainfall(pos);
                final float rainfallInverted = ((ClimateModel.MAXIMUM_RAINFALL - rainfall) * 0.25F) + 1F;

                final float actualForestDensity = data.getForestDensity();
                final float forestDensity = actualForestDensity == 0 ? 0.001F : actualForestDensity; // Cannot divide by 0.

                if (random.nextFloat((Config.COMMON.leavesSaplingPlacementChance.get() / forestDensity) * rainfallInverted) == 0)
                {
                    int x = pos.getX() + (int) Math.round(random.nextGaussian() * Config.COMMON.leavesSaplingSpreadDistance.get());
                    int z = pos.getZ() + (int) Math.round(random.nextGaussian() * Config.COMMON.leavesSaplingSpreadDistance.get());
                    int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

                    BlockPos placementPos = new BlockPos(x, y, z);
                    BlockState placementState = level.getBlockState(placementPos);
                    final BlockState saplingState = sapling.get().defaultBlockState();

                    if ((Helpers.isBlock(placementState, TFCTags.Blocks.PLANTS) || EnvironmentHelpers.isWorldgenReplaceable(placementState)) && level.getMaxLocalRawBrightness(placementPos) >= 11 && saplingState.canSurvive(level, placementPos))
                    {
                        level.setBlock(placementPos, saplingState, Block.UPDATE_ALL);
                    }
                }
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        super.updateShape(state, facing, facingState, level, facingPos, facingPos);
        FluidHelpers.tickFluid(level, currentPos, state);
        final int distance = getDistanceNew(facingState) + 1;
        if (distance != 1 || state.getValue(getDistanceProperty()) != distance)
        {
            level.scheduleTick(currentPos, this, 1);
        }
        return state;
    }

    // this is superficially the same as the StationaryBerryBushBlock onUpdate, we can condense them
    @Override
    public void onUpdate(Level level, BlockPos pos, BlockState state)
    {
        // Fruit tree leaves work like berry bushes, but don't have propagation or growth functionality.
        // Which makes them relatively simple, as then they only need to keep track of their lifecycle.
        if (state.getValue(PERSISTENT)) return; // persistent leaves don't grow

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

    /**
     * Can this leaf block die, given that it spent {@code monthsSpentDying} consecutive months in a dormant state, when it should've been in a non-dormant state.
     */
    protected boolean mayDie(Level level, BlockPos pos, BlockState state, int monthsSpentDying)
    {
        return monthsSpentDying >= MONTHS_SPENT_DORMANT_TO_DIE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(LIFECYCLE); // avoid "STAGE" property
    }

    /**
     * Taking into account only environment rainfall, on a scale [0, 100]
     */
    public static int getHydration(Level level, BlockPos pos)
    {
        return (int) (Climate.getRainfall(level, pos) / 5);
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

    protected Lifecycle getLifecycleForCurrentMonth()
    {
        return getLifecycleForMonth(Calendars.SERVER.getCalendarMonthOfYear());
    }

    protected Lifecycle getLifecycleForMonth(Month month)
    {
        return lifecycle[month.ordinal()];
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return true; // Not for the purposes of leaf decay, but for the purposes of seasonal updates
    }

    private int updateDistanceNew(LevelAccessor level, BlockPos pos)
    {
        int distance = 1 + maxDecayDistance;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (Direction direction : Helpers.DIRECTIONS)
        {
            mutablePos.set(pos).move(direction);
            distance = Math.min(distance, getDistanceNew(level.getBlockState(mutablePos)) + 1);
            if (distance == 1)
            {
                break;
            }
        }
        return distance;
    }

    public int getDistanceNew(BlockState neighbor)
    {
        if (Helpers.isBlock(neighbor.getBlock(), BlockTags.LOGS))
        {
            return 0;
        }
        else
        {
            // Check against this leaf block only, not any leaves
            return neighbor.getBlock() == this ? neighbor.getValue(getDistanceProperty()) : maxDecayDistance;
        }
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos)
    {
        return true;
    }

    @Override
    public FluidProperty getFluidProperty()
    {
        return FLUID;
    }
}
