package tfcflorae.common.blocks.wood;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import tfcflorae.common.blocks.TFCFBlocks;
import net.dries007.tfc.common.blocks.ExtendedProperties;
<<<<<<< Updated upstream
import net.dries007.tfc.common.blocks.wood.TFCLeavesBlock;
import net.dries007.tfc.common.blocks.wood.Wood;

public abstract class TFCFMangroveLeavesBlock extends TFCLeavesBlock implements BonemealableBlock
=======
import net.dries007.tfc.common.blocks.IForgeBlockExtension;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.plant.fruit.SeasonalPlantBlock;
import net.dries007.tfc.common.blocks.soil.FarmlandBlock;
import net.dries007.tfc.common.blocks.soil.HoeOverlayBlock;
import net.dries007.tfc.common.blocks.wood.ILeavesBlock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.util.climate.ClimateRange;
import tfcflorae.common.blockentities.FruitTreeBlockEntity;
import tfcflorae.common.blockentities.TFCFBlockEntities;
import tfcflorae.common.blocks.TFCFBlocks;

public abstract class TFCFMangroveLeavesBlock extends SeasonalPlantBlock implements IForgeBlockExtension, ILeavesBlock, ISeasonalLeavesBlock, HoeOverlayBlock, IFluidLoggable
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
{
    public final TFCFWood wood;

    public TFCFMangroveLeavesBlock(TFCFWood wood, ExtendedProperties properties, int maxDecayDistance)
    {
        super(properties, maxDecayDistance);

        this.wood = wood;
    }

    public TFCFWood getWood()
    {
        return wood;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter block, BlockPos pos, BlockState state, boolean flag)
    {
<<<<<<< Updated upstream
        return block.getBlockState(pos.below()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, Random random, BlockPos pos, BlockState state)
=======
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

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        //super.randomTick(state, level, pos, random); // super calls tick()
        ISeasonalLeavesBlock.randomTick(this, state, level, pos, random);

        Lifecycle currentLifecycle = state.getValue(LIFECYCLE);
        int chance = level.random.nextInt(6);
        if (chance == 0 && currentLifecycle == Lifecycle.FRUITING && (level.getBlockState(pos.below()).isAir() || FluidHelpers.isAirOrEmptyFluid(level.getBlockState(pos.below())) || EnvironmentHelpers.isWorldgenReplaceable(level, pos.below())))
        {
            level.setBlockAndUpdate(pos.below(), TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.SAPLING).get().defaultBlockState().setValue(TFCFMangrovePropaguleBlock.STAGE, 0).setValue(TFCFMangrovePropaguleBlock.AGE, 0).setValue(TFCFMangrovePropaguleBlock.WATERLOGGED, false).setValue(TFCFMangrovePropaguleBlock.HANGING, true).setValue(TFCFMangrovePropaguleBlock.FLUID, TFCFMangrovePropaguleBlock.FLUID.keyFor(Fluids.EMPTY)));
        }
    }

    @Override
    public void onUpdate(Level level, BlockPos pos, BlockState state)
    {
        // Fruit tree leaves work like berry bushes, but don't have propagation or growth functionality.
        // Which makes them relatively simple, as then they only need to keep track of their lifecycle.
        if (state.getValue(PERSISTENT)) return; // persistent leaves don't grow
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
                    newState = Blocks.AIR.defaultBlockState();
                }
                else
                {
                    final int chance = level.random.nextInt(6);
                    if (chance == 0)
                    {
                        newState = growPropaguleBelow(level, pos, level.getRandom(), state.setValue(TFCFMangrovePropaguleBlock.STAGE, 0).setValue(TFCFMangrovePropaguleBlock.AGE, 0).setValue(TFCFMangrovePropaguleBlock.WATERLOGGED, false).setValue(TFCFMangrovePropaguleBlock.HANGING, true).setValue(TFCFMangrovePropaguleBlock.FLUID, TFCFMangrovePropaguleBlock.FLUID.keyFor(Fluids.EMPTY)));
                    }
                    else
                    {
                        newState = state.setValue(LIFECYCLE, currentLifecycle);
                    }
                }

                // And update the block
                if (state != newState)
                {
                    level.setBlock(pos, newState, 3);
                }
            }
        }
    }

    protected BlockState growPropaguleBelow(Level level, BlockPos pos, Random random, BlockState state)
    {
        if (!state.getValue(LIFECYCLE).active())
        {
            // Only grow when active
            return state;
        }

        // Grow a propagule block downwards
        final BlockPos belowPos = pos.below();
        if (level.isEmptyBlock(belowPos))
        {
            level.setBlockAndUpdate(belowPos, TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.SAPLING).get().defaultBlockState().setValue(TFCFMangrovePropaguleBlock.STAGE, 0).setValue(TFCFMangrovePropaguleBlock.AGE, 0).setValue(TFCFMangrovePropaguleBlock.WATERLOGGED, false).setValue(TFCFMangrovePropaguleBlock.HANGING, true).setValue(TFCFMangrovePropaguleBlock.FLUID, TFCFMangrovePropaguleBlock.FLUID.keyFor(Fluids.EMPTY)));
            return state;
        }
        return state;
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
        return monthsSpentDying >= MONTHS_SPENT_DORMANT_TO_DIE && state.getValue(getDistanceProperty()) > maxDecayDistance && !state.getValue(PERSISTENT);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(LIFECYCLE, PERSISTENT, getDistanceProperty(), getFluidProperty()); // avoid "STAGE" property
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        FluidHelpers.tickFluid(level, currentPos, state);
        final int distance = getDistance(facingState) + 1;
        if (distance != 1 || state.getValue(getDistanceProperty()) != distance)
        {
            level.scheduleTick(currentPos, this, 1);
        }
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos)
    {
        return 1;
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos)
    {
        return 0.2F;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return Shapes.empty();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random rand)
    {
        int distance = updateDistance(level, pos);
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
                    level.setBlockAndUpdate(pos, state.setValue(getDistanceProperty(), maxDecayDistance + 1));
                }
            }
            else
            {
                level.setBlock(pos, state.setValue(getDistanceProperty(), maxDecayDistance), 3);
            }
        }
        else
        {
            level.setBlock(pos, state.setValue(getDistanceProperty(), distance), 3);
        }
    }

    public boolean isValid(LevelAccessor level, BlockPos pos, BlockState state)
    {
        if (state.getValue(PERSISTENT))
        {
            return true;
        }
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (Direction direction : Helpers.DIRECTIONS)
        {
            mutablePos.set(pos).move(direction);
            if (Helpers.isBlock(level.getBlockState(mutablePos), BlockTags.LOGS))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        final float modifier = TFCConfig.SERVER.leavesMovementModifier.get().floatValue();
        if (modifier < 1 && state.getValue(getFluidProperty()).getFluid() == Fluids.EMPTY)
        {
            Helpers.slowEntityInBlock(entity, modifier, 5);
        }
        if (Helpers.isEntity(entity, TFCTags.Entities.DESTROYED_BY_LEAVES))
        {
            entity.kill();
        }
        if (level.random.nextInt(20) == 0 && level instanceof ServerLevel server)
        {
            doParticles(server, entity.getX(), entity.getEyeY() - 0.25D, entity.getZ(), 3);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return true; // Not for the purposes of leaf decay, but for the purposes of seasonal updates
    }

    /**
     * The reason this is not a constructor parameter is because the super class (Block) will use this directly, and nothing else is initialized in time.
     */
    protected abstract IntegerProperty getDistanceProperty();

    public int updateDistance(LevelAccessor level, BlockPos pos)
    {
        int distance = 1 + maxDecayDistance;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (Direction direction : Direction.values())
        {
            mutablePos.set(pos).move(direction);
            distance = Math.min(distance, getDistance(level.getBlockState(mutablePos)) + 1);
            if (distance == 1)
            {
                break;
            }
        }
        return distance;
    }

    public int getDistance(BlockState neighbor)
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
    public boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
>>>>>>> Stashed changes
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state)
    {
        level.setBlock(pos.below(), TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.SAPLING).get().defaultBlockState(), 2);
    }
}
