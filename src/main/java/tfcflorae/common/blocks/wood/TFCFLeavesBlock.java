package tfcflorae.common.blocks.wood;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.client.particle.TFCParticles;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.IForgeBlockExtension;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.fruit.IBushBlock;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.plant.fruit.SeasonalPlantBlock;
import net.dries007.tfc.common.blocks.wood.ILeavesBlock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.util.climate.ClimateRange;

import tfcflorae.common.blockentities.TFCFBlockEntities;

public abstract class TFCFLeavesBlock extends SeasonalPlantBlock implements IForgeBlockExtension, ILeavesBlock, IBushBlock
{
    public static void doParticles(ServerLevel level, double x, double y, double z, int count)
    {
        level.sendParticles(TFCParticles.LEAF.get(), x, y, z, count, Helpers.triangle(level.random), Helpers.triangle(level.random), Helpers.triangle(level.random), 0.3f);
    }

    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    public static final EnumProperty<Lifecycle> LIFECYCLE = TFCBlockStateProperties.LIFECYCLE;

    private final int maxDecayDistance;
    private final Supplier<ClimateRange> climateRange;

    public static TFCFLeavesBlock create(ExtendedProperties properties, Supplier<? extends Item> productItem, Lifecycle[] stages, int maxDecayDistance, Supplier<ClimateRange> climateRange)
    {
        final IntegerProperty distanceProperty = getDistanceProperty(maxDecayDistance);
        return new TFCFLeavesBlock(properties, productItem, stages, maxDecayDistance, climateRange)
        {
            @Override
            protected IntegerProperty getDistanceProperty()
            {
                return distanceProperty;
            }
        };
    }

    private static IntegerProperty getDistanceProperty(int maxDecayDistance)
    {
        if (maxDecayDistance >= 7 && maxDecayDistance < 7 + TFCBlockStateProperties.DISTANCES.length)
        {
            return TFCBlockStateProperties.DISTANCES[maxDecayDistance - 7];
        }
        throw new IllegalArgumentException("No property set for distance: " + maxDecayDistance);
    }

    public TFCFLeavesBlock(ExtendedProperties properties, Supplier<? extends Item> productItem, Lifecycle[] stages, int maxDecayDistance, Supplier<ClimateRange> climateRange)
    {
        super(properties, productItem, stages);

        this.maxDecayDistance = maxDecayDistance;
        this.climateRange = climateRange;

        registerDefaultState(getStateDefinition().any().setValue(PERSISTENT, false).setValue(LIFECYCLE, Lifecycle.HEALTHY));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return Shapes.block();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return defaultBlockState().setValue(PERSISTENT, context.getPlayer() != null);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (!getTrimItemStack().isEmpty())
        {
            // Flowering bushes can be cut to create trimmings. This is how one moves or creates new bushes.
            // The larger the bush is (higher stage), the better chance you have of
            // 1. damaging it less (i.e. reducing the stage, or killing it), and
            // 2. making a clipping.
            if (state.getValue(LIFECYCLE) == Lifecycle.FLOWERING)
            {
                final ItemStack held = player.getItemInHand(hand);
                if (Helpers.isItem(held.getItem(), TFCTags.Items.BUSH_CUTTING_TOOLS))
                {
                    level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 0.5f, 1.0f);
                    if (!level.isClientSide())
                    {
                        level.getBlockEntity(pos, TFCFBlockEntities.LARGE_FRUIT_TREE.get()).ifPresent(bush -> {
                            final int finalStage = state.getValue(STAGE) - 1 - level.getRandom().nextInt(2);
                            if (finalStage >= 0)
                            {
                                // We didn't kill the bush, but we have cut the flowers off
                                level.setBlock(pos, state.setValue(STAGE, finalStage).setValue(LIFECYCLE, Lifecycle.HEALTHY), 3);
                            }
                            else
                            {
                                // Oops
                                level.destroyBlock(pos, false, player);
                            }

                            held.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(hand));

                            // But, if we were successful, we have obtained a clipping (2 / 3 chance)
                            if (level.getRandom().nextInt(3) != 0)
                            {
                                ItemHandlerHelper.giveItemToPlayer(player, getTrimItemStack());
                            }
                        });
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        if (state.getValue(LIFECYCLE) == Lifecycle.FRUITING)
        {
            level.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.PLAYERS, 1.0f, level.getRandom().nextFloat() + 0.7f + 0.3f);
            if (!level.isClientSide())
            {
                level.getBlockEntity(pos, TFCFBlockEntities.LARGE_FRUIT_TREE.get()).ifPresent(bush -> {
                    ItemHandlerHelper.giveItemToPlayer(player, getProductItem(level.random));
                    level.setBlockAndUpdate(pos, stateAfterPicking(state));
                });
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onUpdate(Level level, BlockPos pos, BlockState state)
    {
        // Fruit tree leaves work like berry bushes, but don't have propagation or growth functionality.
        // Which makes them relatively simple, as then they only need to keep track of their lifecycle.
        if (state.getValue(PERSISTENT)) return; // persistent leaves don't grow
        level.getBlockEntity(pos, TFCFBlockEntities.LARGE_FRUIT_TREE.get()).ifPresent(leaves -> {
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
                // todo: include root water?
                final int hydration = (int) Climate.getRainfall(level, pos) / 5;

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
                    currentLifecycle = currentLifecycle.advanceTowards(lifecycleAtNextTick);

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

                if (monthsSpentDying > 0 && level.getRandom().nextInt(16) < monthsSpentDying)
                {
                    // It may have died, as it spent too many consecutive months when it should've been healthy, in invalid conditions.
                    newState = Blocks.AIR.defaultBlockState();
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
            leaves.afterUpdate();
        });
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(LIFECYCLE, PERSISTENT, getDistanceProperty()); // avoid "STAGE" property
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        int distance = getDistance(facingState) + 1;
        if (distance != 1 || stateIn.getValue(getDistanceProperty()) != distance)
        {
            level.scheduleTick(currentPos, this, 1);
        }
        return stateIn;
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
                level.removeBlock(pos, false);
                doParticles(level, pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), 1);
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

    private boolean isValid(LevelAccessor level, BlockPos pos, BlockState state)
    {
        if (state.getValue(PERSISTENT))
        {
            return true;
        }
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (Direction direction : Helpers.DIRECTIONS)
        {
            mutablePos.set(pos).move(direction);
            if (Helpers.isBlock(level.getBlockState(mutablePos), TFCTags.Blocks.FRUIT_TREE_BRANCH))
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
        /*if (TFCConfig.SERVER.enableLeavesSlowEntities.get())
        {
            Helpers.slowEntityInBlock(entity, 0.3f, 5);
        }*/
        Helpers.slowEntityInBlock(entity, 0.3f, 5);
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

    private int updateDistance(LevelAccessor level, BlockPos pos)
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

    private int getDistance(BlockState neighbor)
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
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return true;
    }
}
