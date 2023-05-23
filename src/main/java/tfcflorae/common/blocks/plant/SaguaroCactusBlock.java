package tfcflorae.common.blocks.plant;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.PlantBlock;
import net.dries007.tfc.common.blocks.plant.fruit.IBushBlock;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.soil.FarmlandBlock;
import net.dries007.tfc.common.blocks.soil.HoeOverlayBlock;
import net.dries007.tfc.common.blocks.wood.ILeavesBlock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.util.climate.ClimateRange;
import net.dries007.tfc.util.registry.RegistryPlant;

import tfcflorae.util.TFCFHelpers;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public abstract class SaguaroCactusBlock extends PlantBlock implements ILeavesBlock, IBushBlock, HoeOverlayBlock
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

    public static final BooleanProperty HORIZONTAL = BooleanProperty.create("horizontal");
    public static final DirectionProperty HORIZONTAL_DIRECTION = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    protected final Supplier<? extends Item> productItem;
    protected final Supplier<ClimateRange> climateRange;
    private final Lifecycle[] lifecycle;
    private long lastUpdateTick;

    public static SaguaroCactusBlock create(RegistryPlant plant, ExtendedProperties properties, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, Supplier<ClimateRange> climateRange)
    {
        return new SaguaroCactusBlock(properties, productItem, lifecycle, climateRange)
        {
            @Override
            public RegistryPlant getPlant()
            {
                return plant;
            }
        };
    }

    public SaguaroCactusBlock(ExtendedProperties properties, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, Supplier<ClimateRange> climateRange)
    {
        super(properties);

        Preconditions.checkArgument(lifecycle.length == 12, "Lifecycle length must be 12");

        this.climateRange = climateRange;
        this.lifecycle = lifecycle;
        this.productItem = productItem;

        lastUpdateTick = Calendars.SERVER.getTicks();

        this.registerDefaultState(getStateDefinition().any()
                .setValue(HORIZONTAL, false)
                .setValue(HORIZONTAL_DIRECTION, Direction.NORTH)
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false)
                .setValue(LIFECYCLE, Lifecycle.HEALTHY));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx)
    {
        BlockState st = defaultBlockState();
        if (ctx.getClickedFace().get2DDataValue() >= 0)
            st = st.setValue(HORIZONTAL, true).setValue(HORIZONTAL_DIRECTION, ctx.getClickedFace().getOpposite()).setValue(FACING_PROPERTIES.get(ctx.getClickedFace().getOpposite()), true);

        return st;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos posFrom)
    {
        if (level instanceof ServerLevel server && getLifecycleForCurrentMonth() != getLifecycleForMonth(Calendars.SERVER.getCalendarMonthOfYear()))
        {
            onUpdate(server, pos, state);
        }
        if (!canSurvive(state, level, pos))
        {
            level.scheduleTick(pos, this, 1);
            return super.updateShape(state, direction, newState, level, pos, posFrom);
        }
        if (direction.getAxis().isVertical())
            return super.updateShape(state, direction, newState, level, pos, posFrom);
        if (newState.getBlock() == this)
        {
            if (newState.getValue(HORIZONTAL) && newState.getValue(FACING_PROPERTIES.get(direction.getOpposite())))
                return state.setValue(FACING_PROPERTIES.get(direction), true);
        }
        else
        {
            return state.setValue(FACING_PROPERTIES.get(direction), false);
        }
        return super.updateShape(state, direction, newState, level, pos, posFrom);
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random)
    {
        if (!blockState.canSurvive(serverLevel, blockPos))
        {
            serverLevel.destroyBlock(blockPos, true);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos blockPos)
    {
        if (state.getValue(HORIZONTAL))
        {
            Direction offset = state.getValue(HORIZONTAL_DIRECTION);
            if (levelReader.getBlockState(blockPos.relative(offset)).is(this)) return true;
        }
        else
        {
            BlockState checkState = levelReader.getBlockState(blockPos.below());
            return (checkState.is(this) || Helpers.isBlock(checkState, BlockTags.SAND) || Helpers.isBlock(checkState, TFCTags.Blocks.GRASS_PLANTABLE_ON) || Helpers.isBlock(checkState, TFCTags.Blocks.BUSH_PLANTABLE_ON)) && !levelReader.getBlockState(blockPos.above()).getMaterial().isLiquid();
        }
        return false;
        //return super.canSurvive(state, levelReader, blockPos);
    }

    public boolean isGrowBlock(BlockState state)
    {
        return Helpers.isBlock(state, BlockTags.SAND) || Helpers.isBlock(state, TFCTags.Blocks.GRASS_PLANTABLE_ON) || Helpers.isBlock(state, TFCTags.Blocks.BUSH_PLANTABLE_ON);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
    {
        return getShape(blockState);
    }

    public VoxelShape getShape(BlockState state)
    {
        return shapes.get(state);
    }

    private final HashMap<BlockState, VoxelShape> shapes = Util.make(Maps.newHashMap(), m->getStateDefinition().getPossibleStates().forEach(st->m.put(st, getShapeForState(st))));

    private VoxelShape getShapeForState(BlockState state)
    {
        double size = 4;
        VoxelShape base;

        if (state.getValue(HORIZONTAL)) base = Block.box(size, size * 2, size, 16 - size, 15.98, 16 - size);
        else base = Block.box(size, 0, size, 16 - size, 15.98, 16 - size);

        List<VoxelShape> connections = Lists.newArrayList();
        for (Direction dir : Direction.values())
        {
            if (dir == Direction.DOWN || dir == Direction.UP) continue;

            if (state.getValue(FACING_PROPERTIES.get(dir)))
            {
                double x = dir == Direction.WEST ? 0 : dir == Direction.EAST ? 16D : size;
                double z = dir == Direction.NORTH ? 0 : dir == Direction.SOUTH ? 16D : size;

                double upper = 16D - size;

                double minX = Math.min(x, upper) / 16.0;
                double maxX = Math.max(x, upper) / 16.0;
                double minZ = Math.min(z, upper) / 16.0;
                double maxZ = Math.max(z, upper) / 16.0;

                VoxelShape sh = Shapes.box(minX, 8 / 16.0, minZ, maxX, 15.98 / 16.0, maxZ);
                connections.add(sh);
            }
        }
        return Shapes.or(base, connections.toArray(new VoxelShape[]{}));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(EAST, WEST, NORTH, SOUTH, HORIZONTAL, HORIZONTAL_DIRECTION, LIFECYCLE));
    }

    public static final Map<Direction, BooleanProperty> FACING_PROPERTIES = Util.make(Maps.newEnumMap(Direction.class), (enumMap)->
    {
        enumMap.put(Direction.NORTH, NORTH);
        enumMap.put(Direction.EAST, EAST);
        enumMap.put(Direction.SOUTH, SOUTH);
        enumMap.put(Direction.WEST, WEST);
    });

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random)
    {
        if (getLifecycleForCurrentMonth() != getLifecycleForMonth(Calendars.SERVER.getCalendarMonthOfYear()))
        {
            onUpdate(serverLevel, blockPos, blockState);
            lastUpdateTick = Calendars.SERVER.getTicks();
        }
        if (blockState.getValue(AGE) >= 3 && random.nextInt(10) == 0 && blockState.equals(defaultBlockState()) && isGrowBlock(serverLevel.getBlockState(blockPos.below())) && serverLevel.getBlockState(blockPos.above()).isAir())
        {
            generateCactus(this, serverLevel, random.nextBoolean(), blockPos, random, false);
        }
        super.randomTick(blockState, serverLevel, blockPos, random);
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity)
    {
        entity.hurt(DamageSource.CACTUS, 1.0f);
    }

    @Override
    public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType)
    {
        return false;
    }

    /*@Override
    public boolean isValidBonemealTarget(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, boolean isClient)
    {
        return blockState.equals(defaultBlockState()) && isGrowBlock(blockGetter.getBlockState(blockPos.below())) && blockGetter.getBlockState(blockPos.above()).isAir();
    }*/

    /*@Override
    public boolean isBonemealSuccess(Level level, Random random, BlockPos blockPos, BlockState blockState)
    {
        return (double) level.random.nextFloat() < 0.45D;
    }*/

    /*@Override
    public void performBonemeal(ServerLevel serverLevel, Random random, BlockPos blockPos, BlockState blockState)
    {
        generateCactus(this, serverLevel, random.nextBoolean(), blockPos, random, false);
    }*/

    private final static Direction[] NORTH_SOUTH = {Direction.NORTH, Direction.SOUTH};
    private final static Direction[] EAST_WEST = {Direction.EAST, Direction.WEST};
    public boolean generateCactus(Block block, WorldGenLevel world, boolean northSouth, BlockPos pos, Random random, boolean isBig)
    {
        if (!block.defaultBlockState().canSurvive(world, pos)) return false;

        boolean hasArms = random.nextInt(10) > 1;
        boolean has2Arms = random.nextInt(5) != 0;

        int centerHeight = TFCFHelpers.randomRange(4, 8);

        BlockPos.MutableBlockPos p = new BlockPos.MutableBlockPos(pos.getX(), pos.getY(), pos.getZ());
        for (int yy = 0; yy < centerHeight; yy++)
        {
            if (yy > 0)
            {
                if (!world.getBlockState(p).isAir()) break;
            }

            world.setBlock(p, block.defaultBlockState(), 2);
            p.move(Direction.UP);
        }

        if (!hasArms) return true;

        int centerEndY = p.getY();
        int armStart = TFCFHelpers.randomRange(1, centerHeight - 2);
        Direction[] directions = northSouth ? NORTH_SOUTH : EAST_WEST;

        if (has2Arms)
        {
            for (Direction d : directions)
            {
                generateArm(block, world, d, p.getX(), pos.getY() + armStart, p.getZ(), centerEndY);
                armStart = TFCFHelpers.randomRange(1, centerHeight - 2);
            }
        }
        else
        {
            generateArm(block, world, directions[random.nextInt(directions.length)], p.getX(), pos.getY() + armStart, p.getZ(), centerEndY);
        }

        if ((!isBig && random.nextInt(10) == 0) || (isBig && random.nextInt(50) == 0))
        {
            BlockPos nextPos = new BlockPos(pos.getX(), centerEndY, pos.getZ());
            if (world.getBlockState(nextPos).isAir()) generateCactus(block, world, random.nextBoolean(), nextPos, random, true);
        }
        return true;
    }

    private static void generateArm(Block block, WorldGenLevel world, Direction direction, int centerX, int armY, int centerZ, int centerHeight)
    {
        BlockPos.MutableBlockPos p = new BlockPos.MutableBlockPos(centerX + direction.getStepX(), armY, centerZ + direction.getStepZ());

        if (!world.getBlockState(p).isAir()) return;

        BlockPos centerPos = p.relative(direction.getOpposite());
        BlockState centerState = world.getBlockState(centerPos);
        if (!centerState.is(block)) return;

        world.setBlock(centerPos, centerState.setValue(SaguaroCactusBlock.FACING_PROPERTIES.get(direction), true), 2);
        world.setBlock(p, block.defaultBlockState().setValue(SaguaroCactusBlock.HORIZONTAL, true).setValue(SaguaroCactusBlock.HORIZONTAL_DIRECTION, direction.getOpposite()).setValue(SaguaroCactusBlock.FACING_PROPERTIES.get(direction.getOpposite()), true), 2);

        p.move(Direction.UP);
        int amt = Math.max(1, (centerHeight - p.getY()) + TFCFHelpers.randomRange(-3, -1));
        for (int i = 0; i < amt; i++)
        { 
            if(!world.getBlockState(p).isAir()) return;
            world.setBlock(p, block.defaultBlockState(), 2);
            p.move(Direction.UP);
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
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (state.getValue(LIFECYCLE) == Lifecycle.FRUITING && productItem != null)
        {
            level.playSound(player, pos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.PLAYERS, 1.0f, level.getRandom().nextFloat() + 0.7f + 0.3f);
            if (!level.isClientSide())
            {
                final ItemStack stack = player.getItemInHand(hand);
                if (!(Helpers.isItem(stack, TFCTags.Items.KNIVES) || Helpers.isItem(stack, Items.STICK)))
                {
                    player.hurt(DamageSource.CACTUS, 2.0F);
                }
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
        if (!state.getValue(HORIZONTAL)  && level.getBlockState(pos.above()).isAir() && level.getBlockState(pos.below()).is(this))
        {
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