package tfcflorae.common.blocks.wood;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.client.particle.TFCParticles;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.IForgeBlockExtension;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.fruit.IBushBlock;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.plant.fruit.SeasonalPlantBlock;
import net.dries007.tfc.common.blocks.soil.FarmlandBlock;
import net.dries007.tfc.common.blocks.soil.HoeOverlayBlock;
import net.dries007.tfc.common.blocks.wood.ILeavesBlock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.util.climate.ClimateRange;

import tfcflorae.common.blockentities.TFCFBlockEntities;
import tfcflorae.common.blocks.TFCFBlocks;

import org.jetbrains.annotations.Nullable;

public abstract class TFCFJoshuaLeavesBlock extends SeasonalPlantBlock implements IFluidLoggable, IForgeBlockExtension, ILeavesBlock, IBushBlock, HoeOverlayBlock
{
    public static void doParticles(ServerLevel level, double x, double y, double z, int count)
    {
        level.sendParticles(TFCParticles.LEAF.get(), x, y, z, count, Helpers.triangle(level.random), Helpers.triangle(level.random), Helpers.triangle(level.random), 0.3f);
    }

    /**
     * Taking into account only environment rainfall, on a scale [0, 100]
     */
    public static int getHydration(Level level, BlockPos pos)
    {
        return (int) (Climate.getRainfall(level, pos) / 5);
    }

    public static final FluidProperty FLUID = TFCBlockStateProperties.FRESH_WATER;
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    public static final EnumProperty<Lifecycle> LIFECYCLE = TFCBlockStateProperties.LIFECYCLE;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private static final VoxelShape SHAPE = Block.box(1.0D, 1.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    /* The maximum value of the decay property. */
    private final ExtendedProperties properties;

    public static final int MONTHS_SPENT_DORMANT_TO_DIE = 4;
    public final TFCFWood wood;

    public static TFCFJoshuaLeavesBlock create(TFCFWood wood, ExtendedProperties properties, Supplier<? extends Item> productItem, Lifecycle[] stages, Supplier<ClimateRange> climateRange)
    {
        return new TFCFJoshuaLeavesBlock(wood, properties, productItem, stages, climateRange)
        {
            @Override
            public FluidProperty getFluidProperty()
            {
                return FLUID;
            }
        };
    }

    protected TFCFJoshuaLeavesBlock(TFCFWood wood, ExtendedProperties properties, Supplier<? extends Item> productItem, Lifecycle[] stages, Supplier<ClimateRange> climateRange)
    {
        super(properties, climateRange, productItem, stages);

        this.wood = wood;
        this.properties = properties;

        registerDefaultState(stateDefinition.any().setValue(AGE, 0).setValue(getFluidProperty(), getFluidProperty().keyFor(Fluids.EMPTY)).setValue(FACING, Direction.UP).setValue(PERSISTENT, false).setValue(LIFECYCLE, Lifecycle.HEALTHY));
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    @Override
    public FluidProperty getFluidProperty()
    {
        return FLUID;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return true;
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(getFluidProperty(), AGE, FACING, LIFECYCLE, PERSISTENT);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        FluidHelpers.tickFluid(level, currentPos, state);
        if (facing != Direction.UP && !state.canSurvive(level, currentPos))
        {
            level.scheduleTick(currentPos, this, 1);
            return Blocks.AIR.defaultBlockState();
        }
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state)
    {
        return IFluidLoggable.super.getFluidState(state);
    }

    /*@Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockState blockstate = level.getBlockState(pos.below());
        if (blockstate.getBlock() != TFCFBlocks.JOSHUA_TRUNK.get(wood).get() && !(Helpers.isBlock(blockstate, TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(blockstate, TFCTags.Blocks.TREE_GROWS_ON) || Helpers.isBlock(blockstate, BlockTags.SAND) || Helpers.isBlock(blockstate, Tags.Blocks.GRAVEL)))
        {
            if (!level.isEmptyBlock(pos.below()))
            {
                return false;
            }
            else
            {
                boolean isValid = false;
                for (Direction direction : Direction.Plane.HORIZONTAL)
                {
                    BlockState relativeState = level.getBlockState(pos.relative(direction));
                    if (Helpers.isBlock(relativeState, TFCFBlocks.JOSHUA_TRUNK.get(wood).get()))
                    {
                        if (isValid)
                        {
                            return false;
                        }

                        isValid = true;
                    }
                    else if (!level.isEmptyBlock(pos.relative(direction)))
                    {
                        return false;
                    }
                }

                return isValid;
            }
        }
        else
        {
            return true;
        }
    }*/

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockState blockstate = level.getBlockState(pos.below());
        if (!blockstate.is(TFCFBlocks.JOSHUA_TRUNK.get(wood).get()) && !(Helpers.isBlock(blockstate, TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(blockstate, TFCTags.Blocks.TREE_GROWS_ON) || Helpers.isBlock(blockstate, BlockTags.SAND) || Helpers.isBlock(blockstate, Tags.Blocks.GRAVEL)))
        {
            if (!blockstate.isAir())
            {
                return false;
            }
            else
            {
                boolean flag = false;
                for(Direction direction : Direction.Plane.HORIZONTAL)
                {
                    BlockState blockstate1 = level.getBlockState(pos.relative(direction));
                    if (blockstate1.is(TFCFBlocks.JOSHUA_TRUNK.get(wood).get()))
                    {
                        if (flag)
                        {
                            return false;
                        }
                            flag = true;
                    }
                    else if (!blockstate1.isAir())
                    {
                        return false;
                    }
                }
                return flag;
            }
        }
        else
        {
            return true;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (state.getValue(LIFECYCLE) == Lifecycle.FRUITING && productItem != null && state.getValue(AGE) >= 4)
        {
            level.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.PLAYERS, 1.0f, level.getRandom().nextFloat() + 0.7f + 0.3f);
            if (!level.isClientSide())
            {
                level.getBlockEntity(pos, TFCFBlockEntities.LARGE_FRUIT_TREE.get()).ifPresent(bush -> {
                    ItemHandlerHelper.giveItemToPlayer(player, getProductItem(level.random));
                    level.setBlock(pos, stateAfterPicking(state), 3);
                });
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (state.getValue(PERSISTENT)) return; // persistent leaves don't grow
        IBushBlock.randomTick(this, state, level, pos, random);
        Fluid fluid = state.getValue(getFluidProperty()).getFluid();

        BlockPos abovePos = pos.above();
        if (level.isEmptyBlock(abovePos) && abovePos.getY() < level.getMaxBuildHeight() && TFCConfig.SERVER.plantGrowthChance.get() > random.nextDouble())
        {
            int i = state.getValue(AGE);
            if (i < 5 && ForgeHooks.onCropsGrowPre(level, abovePos, state, true))
            {
                boolean shouldPlaceNewBody = false;
                boolean foundGroundFurtherDown = false;
                BlockState belowState = level.getBlockState(pos.below());
                Block belowBlock = belowState.getBlock();
                if (Helpers.isBlock(belowBlock, TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(belowBlock, TFCTags.Blocks.TREE_GROWS_ON) || Helpers.isBlock(belowBlock, BlockTags.SAND) || Helpers.isBlock(belowBlock, Tags.Blocks.GRAVEL))
                {
                    shouldPlaceNewBody = true;
                }
                else if (belowBlock == TFCFBlocks.JOSHUA_TRUNK.get(wood).get())
                {
                    int j = 1;

                    for (int k = 0; k < 4; ++k)
                    {
                        Block belowBlockOffset = level.getBlockState(pos.below(j + 1)).getBlock();
                        if (belowBlockOffset != TFCFBlocks.JOSHUA_TRUNK.get(wood).get())
                        {
                            if (Helpers.isBlock(belowBlockOffset, TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(belowBlockOffset, TFCTags.Blocks.TREE_GROWS_ON) || Helpers.isBlock(belowBlockOffset, BlockTags.SAND) || Helpers.isBlock(belowBlockOffset, Tags.Blocks.GRAVEL))
                            {
                                foundGroundFurtherDown = true;
                            }
                            break;
                        }

                        ++j;
                    }

                    if (j < 2 || j <= random.nextInt(foundGroundFurtherDown ? 5 : 4))
                    {
                        shouldPlaceNewBody = true;
                    }
                }
                else if (level.isEmptyBlock(pos.below()))
                {
                    shouldPlaceNewBody = true;
                }

                if (shouldPlaceNewBody && allNeighborsEmpty(level, abovePos, null) && level.isEmptyBlock(pos.above(2)))
                {
                    setTrunkWithFluid(level, pos, fluid);
                    placeGrownLeaves(level, abovePos, i, Direction.UP);
                }
                else if (i < 4)
                {
                    int l = 0;
                    if (i < 2)
                    {
                        l =  2 + random.nextInt(4);
                    }
                    else
                    {
                        l = 2 + random.nextInt(8);
                    }
                    boolean foundValidGrowthSpace = false;

                    if (foundGroundFurtherDown)
                    {
                        ++l;
                    }

                    for (int i1 = 0; i1 < l; ++i1)
                    {
                        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                        BlockPos relativePos = pos.relative(direction);
                        if (level.isEmptyBlock(relativePos) && level.isEmptyBlock(relativePos.below()) && allNeighborsEmpty(level, relativePos, direction.getOpposite()))
                        {
                            placeGrownLeaves(level, relativePos, i + 1, direction);
                            foundValidGrowthSpace = true;
                        }
                    }
                    if (foundValidGrowthSpace)
                    {
                        setTrunkWithFluid(level, pos, fluid);
                    }
                    else
                    {
                        placeDeadLeaves(level, pos, level.getBlockState(pos).getValue(FACING));
                    }
                }
                else
                {
                    placeDeadLeaves(level, pos, level.getBlockState(pos).getValue(FACING));
                }
                ForgeHooks.onCropsGrowPost(level, pos, state);
            }
        }
    }


    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random rand)
    {
        if (!state.canSurvive(level, pos))
        {
            level.destroyBlock(pos, true);
        }
    }

    /**
     * @return {@code true} if any plant blocks were placed.
     */
    public boolean generatePlant(LevelAccessor level, BlockPos pos, Random rand, int maxHorizontalDistance, Fluid fluid)
    {
        if (getFluidProperty().canContain(fluid))
        {
            final BlockState originalState = level.getBlockState(pos);
            setTrunkWithFluid(level, pos, fluid);
            if (growTreeRecursive(level, pos, rand, pos, maxHorizontalDistance, 0, fluid))
            {
                return true;
            }
            else
            {
                // Revert the original state
                level.setBlock(pos, originalState, 2);
            }
        }
        return false;
    }

    /**
     * @return {@code true} if any plant blocks were placed.
     */
    private boolean growTreeRecursive(LevelAccessor level, BlockPos branchPos, Random rand, BlockPos originalBranchPos, int maxHorizontalDistance, int iterations, Fluid fluid)
    {
        int status = 0;
        status = GrowStep(level, branchPos, originalBranchPos, rand, maxHorizontalDistance, iterations, fluid);
        while (status == 0)
        {
            branchPos = branchPos.above();
            status = GrowStep(level, branchPos, originalBranchPos, rand, maxHorizontalDistance, iterations, fluid);
        }
        return true;
    }

    public int GrowStep(LevelAccessor level, BlockPos currentBlock, BlockPos originalBranchPos, Random random, int maxHorizontalDistance, int iterations, Fluid fluid)
    {
        BlockPos blockpos = currentBlock.above();

        if (level.getBlockState(blockpos).getBlock() == Blocks.AIR)
        {
            if (iterations < 5)
            {
                boolean flag = false;
                boolean flag1 = false;
                BlockState state = level.getBlockState(currentBlock.below());
                Block block = state.getBlock();

                if (Helpers.isBlock(state, TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(state, TFCTags.Blocks.TREE_GROWS_ON) || Helpers.isBlock(state, BlockTags.SAND) || Helpers.isBlock(state, Tags.Blocks.GRAVEL))
                {
                    flag = true;
                }
                else if (block == TFCFBlocks.JOSHUA_TRUNK.get(wood).get())
                {
                    int j = 1;

                    for (int k = 0; k < 4; ++k)
                    {
                        Block block1 = level.getBlockState(currentBlock.below(j + 1)).getBlock();

                        if (block1 != TFCFBlocks.JOSHUA_TRUNK.get(wood).get())
                        {
                            BlockState currentBlockStateBelow = level.getBlockState(currentBlock.below(j + 1));

                            if (Helpers.isBlock(currentBlockStateBelow, TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(currentBlockStateBelow, TFCTags.Blocks.TREE_GROWS_ON) || Helpers.isBlock(currentBlockStateBelow, BlockTags.SAND) || Helpers.isBlock(currentBlockStateBelow, Tags.Blocks.GRAVEL))
                            {
                                flag1 = true;
                            }
                            break;
                        }

                        ++j;
                    }

                    int growthMultiplier = random.nextInt(2);
                    int i1 = 2;

                    if (flag1)
                    {
                        if (j < 2 || random.nextInt(i1 + growthMultiplier) >= j)
                        {
                            flag = true;
                        }
                    }
                    else
                    {
                        if ((random.nextInt(i1) - random.nextInt(i1)) >= j)
                        {
                            flag = true;
                        }
                    }
                }
                else if (state.getMaterial() == Material.AIR)
                {
                    flag = true;
                }

                if (flag && allNeighborsEmpty(level, blockpos, null) && level.getBlockState(currentBlock.above(2)).getBlock() == Blocks.AIR)
                {
                    setTrunkWithFluid(level, currentBlock, fluid);
                    placeGrownLeaves(level, blockpos, 5, Direction.UP);
                    return 0;
                }
                else if (iterations < 4)
                {
                    int l = 0;
                    if (iterations < 2)
                    {
                        l =  2 + random.nextInt(4);
                    }
                    else
                    {
                        l = 2 + random.nextInt(8);
                    }
                    boolean flag2 = false;

                    if (flag1)
                    {
                        ++l;
                    }

                    for (int j1 = 0; j1 < l; ++j1)
                    {
                        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                        BlockPos blockpos1 = currentBlock.relative(direction);

                        if (level.getBlockState(blockpos1).getBlock() == Blocks.AIR && level.getBlockState(blockpos1.below()).getBlock() == Blocks.AIR && allNeighborsEmpty(level, blockpos1, direction.getOpposite()) && Math.abs(blockpos1.getX() - originalBranchPos.getX()) < maxHorizontalDistance && Math.abs(blockpos1.getZ() - originalBranchPos.getZ()) < maxHorizontalDistance && level.isEmptyBlock(blockpos1))
                        {
                            setTrunkWithFluid(level, currentBlock, fluid);
                            setTrunkWithFluid(level, blockpos1, fluid);
                            placeGrownLeaves(level, blockpos1.above(), 5, Direction.UP);
                            growTreeRecursive(level, blockpos1.above(), random, originalBranchPos, maxHorizontalDistance, Mth.clamp(iterations + random.nextInt(5 - iterations) + 1, 0, 4), fluid);
                            flag2 = true;
                        }
                    }

                    if (flag2)
                    {
                        return 1;
                    }
                    else
                    {
                        placeGrownLeaves(level, currentBlock, 5, Direction.UP);
                        return 2;
                    }
                }
                else if (iterations == 4)
                {
                    placeGrownLeaves(level, currentBlock, 5, Direction.UP);
                    return 2;
                }
            }
        }
        return -1;
    }

    /*protected boolean isEmptyBlock(LevelReader level, BlockPos pos)
    {
        return Helpers.isBlock(level.getBlockState(pos).getBlock(), TFCTags.Blocks.SINGLE_BLOCK_REPLACEABLE) || 
            Helpers.isBlock(level.getBlockState(pos).getBlock(), BlockTags.REPLACEABLE_PLANTS) || 
            level.getBlockState(pos).getBlock() == Blocks.AIR;
    }*/

    protected boolean allNeighborsEmpty(LevelReader level, BlockPos pos, @Nullable Direction excludingSide)
    {
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            if (direction != excludingSide && !level.isEmptyBlock(pos.relative(direction)))
            {
                return false;
            }
        }
        return true;
    }

    protected void placeGrownLeaves(LevelAccessor level, BlockPos pos, int age, Direction facing)
    {
        Fluid fluid = level.getFluidState(pos).getType();
        level.setBlock(pos, defaultBlockState().setValue(getFluidProperty(), getFluidProperty().keyFor(fluid)).setValue(AGE, age).setValue(FACING, facing), 2);
    }

    protected void placeDeadLeaves(LevelAccessor level, BlockPos pos, Direction facing)
    {
        Fluid fluid = level.getFluidState(pos).getType();
        level.setBlock(pos, defaultBlockState().setValue(getFluidProperty(), getFluidProperty().keyFor(fluid)).setValue(AGE, 5).setValue(FACING, facing), 2);
    }

    protected void setTrunkWithFluid(LevelAccessor level, BlockPos pos, Fluid fluid)
    {
        BlockState state = getBodyStateWithFluid(level, pos, fluid);
        level.setBlock(pos, state, 2);
    }

    protected BlockState getBodyStateWithFluid(LevelAccessor level, BlockPos pos, Fluid fluid)
    {
        TFCFJoshuaTrunkBlock block = (TFCFJoshuaTrunkBlock) TFCFBlocks.JOSHUA_TRUNK.get(wood).get();
        return block.getStateForPlacement(level, pos).setValue(getFluidProperty(), getFluidProperty().keyFor(fluid));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockState state = defaultBlockState();
        if (world.getBlockState(pos.relative(direction.getOpposite())).is(TFCFBlocks.JOSHUA_TRUNK.get(wood).get()))
        {
            state = state.setValue(FACING, context.getClickedFace());
        }
        FluidState fluidState = world.getFluidState(context.getClickedPos());
        if (!fluidState.isEmpty() && getFluidProperty().canContain(fluidState.getType()))
        {
            return state.setValue(getFluidProperty(), getFluidProperty().keyFor(fluidState.getType()));
        }
        return defaultBlockState().setValue(PERSISTENT, context.getPlayer() != null);
    }

    @Override
    public void addHoeOverlayInfo(Level level, BlockPos pos, BlockState state, List<Component> text, boolean isDebug)
    {
        final ClimateRange range = climateRange.get();
        text.add(FarmlandBlock.getHydrationTooltip(level, pos, range, false, getHydration(level, pos)));
        text.add(FarmlandBlock.getTemperatureTooltip(level, pos, range, false));
    }

    // this is superficially the same as the StationaryBerryBushBlock onUpdate, we can condense them
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
                    Fluid fluid = level.getFluidState(pos).getType();
                    newState = TFCFBlocks.JOSHUA_LEAVES.get(wood).get().defaultBlockState().setValue(getFluidProperty(), getFluidProperty().keyFor(fluid)).setValue(AGE, 5).setValue(FACING, Direction.UP);
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
        });
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

    /**
     * Can this leaf block die, given that it spent {@code monthsSpentDying} consecutive months in a dormant state, when it should've been in a non-dormant state.
     */
    protected boolean mayDie(Level level, BlockPos pos, BlockState state, int monthsSpentDying)
    {
        return monthsSpentDying >= MONTHS_SPENT_DORMANT_TO_DIE && !state.getValue(PERSISTENT);
    }

    @Override
    public boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return true;
    }
}