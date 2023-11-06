package tfcflorae.common.blocks.wood;

import java.util.Random;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryWood;
import net.dries007.tfc.world.TFCChunkGenerator;

import tfcflorae.util.TFCFHelpers;

public class TFCPalmTrunkBlock extends TFCFJoshuaTrunkBlock
{
    public static final int MONTHS_SPENT_DORMANT_TO_DIE = 4;

    public static final BooleanProperty NATURAL = TFCBlockStateProperties.NATURAL;
    public static final BooleanProperty TOP_BLOCK = BooleanProperty.create("top_block");
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
    public static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public final RegistryWood wood;
    @Nullable public final Supplier<? extends Block> leaves;
    @Nullable public final Supplier<? extends Block> trunk;

    public static TFCPalmTrunkBlock create(RegistryWood wood, ExtendedProperties properties, @Nullable Supplier<? extends Block> leaves, @Nullable Supplier<? extends Block> trunk)
    {
        return new TFCPalmTrunkBlock(wood, properties, leaves, trunk) {};
    }

    public TFCPalmTrunkBlock(RegistryWood wood, ExtendedProperties properties, @Nullable Supplier<? extends Block> leaves, @Nullable Supplier<? extends Block> trunk)
    {
        super(wood, properties.properties(), properties);
        this.wood = wood;
        this.leaves = leaves;
        this.trunk = trunk;
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(NATURAL, true).setValue(TOP_BLOCK, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(getFluidProperty(), AGE, NATURAL, TOP_BLOCK, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    public Block getTrunk()
    {
        return trunk.get();
    }

    public Block getLeaves()
    {
        return leaves.get();
    }

    public boolean isTrunkOrLeaves(BlockState state)
    {
        return isTrunk(state) || isLeaves(state);
    }

    public boolean isTrunk(BlockState state)
    {
        return Helpers.isBlock(state, getTrunk());
    }

    public boolean isLeaves(BlockState state)
    {
        return Helpers.isBlock(state, getLeaves());
    }

    @Override
    public boolean canConnectTo(BlockState state)
    {
        return isTrunk(state) || canConnectToSoil(state);
    }

    public boolean canConnectToSoil(BlockState state)
    {
        return Helpers.isBlock(state, TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(state, TFCTags.Blocks.TREE_GROWS_ON) || Helpers.isBlock(state, BlockTags.SAND) || Helpers.isBlock(state, Tags.Blocks.GRAVEL) || state.getMaterial().isSolid();
    }

    // Only return true if *one* block or less are connected
    public boolean isTopBlock(BlockState state, LevelReader level, BlockPos pos)
    {
        int connectedFaces = 0;
        for (Direction direction : Direction.values())
        {
            // If connected to a trunk block, increase connected faces by 1, else 0
            connectedFaces += level.getBlockState(pos.relative(direction)).getBlock().equals(getTrunk()) ? 1 : 0;
        }
        return connectedFaces <= 1 && !level.getBlockState(pos.above()).getBlock().equals(getTrunk());
    }

    public int neighbourTopBlocks(BlockState state, LevelReader level, BlockPos pos)
    {
        int connectedFaces = 0;
        for (Direction direction : Direction.values())
        {
            BlockState neighbourState = level.getBlockState(pos.relative(direction));
            connectedFaces += neighbourState.getBlock().equals(getTrunk()) && neighbourState.getValue(TOP_BLOCK) ? 1 : 0;
        }
        return connectedFaces;
    }

    public boolean enoughConnections(BlockState state, LevelReader level, BlockPos pos, int minimumFaces)
    {
        int connectedFaces = 0;
        for (Direction direction : Direction.values())
        {
            // If connected to a trunk block, increase connected faces by 1, else 0
            connectedFaces += level.getBlockState(pos.relative(direction)).getBlock().equals(getTrunk()) ? 1 : 0;
        }
        return connectedFaces >= minimumFaces;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        FluidHelpers.tickFluid(level, currentPos, state);

        if (!state.canSurvive(level, currentPos))
        {
            level.scheduleTick(currentPos, this, Block.UPDATE_ALL);
            return state;
        }
        else
        {
            boolean flag = isTrunk(facingState) || (facing == Direction.DOWN && canConnectTo(facingState) || (facing == Direction.UP && Helpers.isBlock(facingState, TFCTags.Blocks.FRUIT_TREE_SAPLING)));

            if (!isTrunkOrLeaves(level.getBlockState(currentPos.below())) && !isTopBlock(state, level, currentPos))
            {
                // Make it point up instead of connecting to the ground
                return state.setValue(PROPERTY_BY_DIRECTION.get(facing == Direction.DOWN ? Direction.UP : facing), flag).setValue(TOP_BLOCK, isTopBlock(state, level, currentPos));
            }
            else if ((isLeaves(facingState) && state.getValue(TOP_BLOCK)) || !isLeaves(facingState))
            {
                return state.setValue(PROPERTY_BY_DIRECTION.get(facing), flag).setValue(TOP_BLOCK, isTopBlock(state, level, currentPos));
            }
        }
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            BlockPos relativePos = pos.relative(direction);
            BlockState relativeStateBelow = level.getBlockState(relativePos.below());
            if (isTrunk(level.getBlockState(relativePos)) || isTrunk(relativeStateBelow) || canConnectTo(relativeStateBelow))
            {
                // If this is a top block, then one 1 connection is needed, or the block below should be soil or similar
                if (!state.getValue(TOP_BLOCK) && !canConnectToSoil(relativeStateBelow))
                {
                    // Needs to be connected to at least 2 trunks if the block below isn't soil or similar
                    // This check should be fine, since the placed block should be a top block, if only connected to 1 trunk
                    return enoughConnections(state, level, pos, 2);
                }
                else
                {
                    return true;
                }
            }
        }
        BlockState belowState = level.getBlockState(pos.below());
        return isTrunk(belowState) || canConnectTo(belowState) || level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
        /*if (state.getValue(NATURAL))
        {
            for (Direction direction : Direction.values())
            {
                if (state.getValue(TOP_BLOCK))
                {
                    return enoughConnections(state, level, pos, 1) && canConnectTo(level.getBlockState(pos.relative(direction))) && neighbourTopBlocks(state, level, pos) <= 0;
                }
                else
                {
                    return enoughConnections(state, level, pos, 2) && canConnectTo(level.getBlockState(pos.relative(direction))) && neighbourTopBlocks(state, level, pos) <= 1;
                }
            }
            BlockState belowState = level.getBlockState(pos.below());
            return isTrunk(belowState) || canConnectTo(belowState) || level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
        }
        else
        {
            return true;
        }*/
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

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = defaultBlockState();
        return FluidHelpers.fillWithFluid(getStateForPlacement(level, pos).setValue(NATURAL, context.getPlayer() == null).setValue(TOP_BLOCK, isTopBlock(state, level, pos)), level.getBlockState(pos).getFluidState().getType());
    }

    @Override
    public BlockState getStateForPlacement(BlockGetter level, BlockPos pos)
    {
        final Block trunkBlock = getTrunk();
        BlockState downBlock = level.getBlockState(pos.below());
        BlockState upBlock = level.getBlockState(pos.above());
        BlockState northBlock = level.getBlockState(pos.north());
        BlockState eastBlock = level.getBlockState(pos.east());
        BlockState southBlock = level.getBlockState(pos.south());
        BlockState westBlock = level.getBlockState(pos.west());
        return defaultBlockState()
            .setValue(DOWN, Helpers.isBlock(downBlock, trunkBlock) || Helpers.isBlock(downBlock, TFCTags.Blocks.BUSH_PLANTABLE_ON) || downBlock.isFaceSturdy(level, pos.below(), Direction.UP))
            .setValue(UP, Helpers.isBlock(upBlock, trunkBlock) || Helpers.isBlock(upBlock, getLeaves()))
            .setValue(NORTH, Helpers.isBlock(northBlock, trunkBlock))
            .setValue(EAST, Helpers.isBlock(eastBlock, trunkBlock))
            .setValue(SOUTH, Helpers.isBlock(southBlock, trunkBlock))
            .setValue(WEST, Helpers.isBlock(westBlock, trunkBlock));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        if (state.getValue(TOP_BLOCK))
        {
            return SHAPE;
        }
        else
        {
            return super.getShape(state, level, pos, context);
        }
    }

    /**
     * @return {@code true} if any plant blocks were placed.
     */
    public boolean generatePlant(LevelAccessor level, BlockPos pos, Random random, int maxHorizontalDistance)
    {
        if (pos.getY() >= TFCChunkGenerator.SEA_LEVEL_Y && level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP))
        {
            final BlockState originalState = level.getBlockState(pos);
            setTrunkWithFluid(level, pos);
            if (growTreeRecursive(level, pos, random, pos, maxHorizontalDistance, 0, Direction.Plane.HORIZONTAL.getRandomDirection(random)))
            {
                return true;
            }
            else
            {
                // Revert the original state
                level.setBlock(pos, originalState, Block.UPDATE_ALL);
            }
        }
        return false;
    }

    /**
     * @return {@code true} if any plant blocks were placed.
     */
    public boolean growTreeRecursive(LevelAccessor level, BlockPos branchPos, Random random, BlockPos originalBranchPos, int maxHorizontalDistance, int iterations, Direction originalDirection)
    {
        final int maxHeight = level.getHeight(Heightmap.Types.OCEAN_FLOOR, originalBranchPos.getX(), originalBranchPos.getZ()) + 10 + random.nextInt(6);

        Boolean horizontal = random.nextBoolean() && iterations < 3 && maxHorizontalDistance > 0;
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        boolean willContinue = false;

        if (horizontal)
        {
            BlockPos adjustedBranchPos = branchPos;
            if (iterations == 0)
            {
                setTrunkWithFluid(level, branchPos);
                adjustedBranchPos = branchPos.above();
            }
            if (isTrunk(level.getBlockState(adjustedBranchPos.relative(direction))))
            {
                direction = TFCFHelpers.chooseOtherDirection(direction, random);
            }
            if (iterations > 0)
            {
                direction = originalDirection;
            }

            int horizontalDistance = Mth.randomBetweenInclusive(random, 1, maxHorizontalDistance);
            if (iterations < 4)
            {
                willContinue = true;
                for (int l = 0; l < horizontalDistance; ++l)
                {
                    BlockPos aboveRelativePos = adjustedBranchPos.relative(direction, l + 1);
                    Boolean emptyNeighbours = allNeighborsEmpty(level, aboveRelativePos, direction.getOpposite());

                    if (!emptyNeighbours || aboveRelativePos.getY() >= maxHeight)
                    {
                        return false;
                    }
                    if (emptyNeighbours)
                    {
                        setTrunkWithFluid(level, aboveRelativePos);
                        setTrunkWithFluid(level, aboveRelativePos.relative(direction.getOpposite()));
                        growTreeRecursive(level, adjustedBranchPos.above().relative(direction, horizontalDistance), random, originalBranchPos, maxHorizontalDistance, iterations + random.nextInt(2) + 1, direction);
                    }
                    if (aboveRelativePos.getY() == maxHeight - 1)
                    {
                        placeGrownLeaves(level, aboveRelativePos, 5, Direction.UP);
                    }
                }
            }
            if (!willContinue)
            {
                placeGrownLeaves(level, adjustedBranchPos.relative(direction, horizontalDistance).above(), random.nextInt(10) == 1 ? 3 : 5, Direction.Plane.HORIZONTAL.getRandomDirection(random));
            }
        }
        else
        {
            int height = random.nextInt(5) + 1;
            if (iterations == 0)
            {
                ++height;
            }
            for (int j = 0; j < height; ++j)
            {
                BlockPos blockpos = branchPos.above(j + 1);
                if (!allNeighborsEmpty(level, blockpos, null) || blockpos.getY() >= maxHeight)
                {
                    return false;
                }
                if (blockpos.getY() == maxHeight - 1)
                {
                    placeGrownLeaves(level, blockpos, 5, Direction.UP);
                }
                else
                {
                    setTrunkWithFluid(level, blockpos);
                }
                setTrunkWithFluid(level, blockpos.below());
            }

            if (iterations < 4)
            {
                int branchAttempts = random.nextInt(3);
                if (iterations == 0)
                {
                    ++branchAttempts;
                }
                for (int k = 0; k < branchAttempts; ++k)
                {
                    BlockPos aboveRelativePos = branchPos.above(height).relative(direction);
                    if (Math.abs(aboveRelativePos.getX() - originalBranchPos.getX()) < maxHorizontalDistance * 10 && Math.abs(aboveRelativePos.getZ() - originalBranchPos.getZ()) < maxHorizontalDistance * 10 && level.isEmptyBlock(aboveRelativePos) && level.isEmptyBlock(aboveRelativePos.below()) && allNeighborsEmpty(level, aboveRelativePos, direction.getOpposite()))
                    {
                        willContinue = true;
                        setTrunkWithFluid(level, aboveRelativePos);
                        setTrunkWithFluid(level, aboveRelativePos.relative(direction.getOpposite()));
                        growTreeRecursive(level, aboveRelativePos, random, originalBranchPos, maxHorizontalDistance, iterations + random.nextInt(2) + 1, direction);
                    }
                }
            }
            if (!willContinue)
            {
                placeGrownLeaves(level, branchPos.above(height), random.nextInt(10) == 1 ? 3 : 5, Direction.Plane.HORIZONTAL.getRandomDirection(random));
            }
        }
        return true;
    }

    public boolean allNeighborsEmpty(LevelReader level, BlockPos pos, @Nullable Direction excludingSide)
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

    public boolean clearPassage(LevelReader level, BlockPos pos, Direction direction, int horizontalDistance)
    {
        int occupiedBlocks = 0;
        for (int m = 0; m < horizontalDistance; ++m)
        {
            occupiedBlocks += !level.isEmptyBlock(pos.relative(direction, m + 1)) ? 1 : 0;
        }
        return occupiedBlocks <= 0;
    }

    public void placeGrownLeaves(LevelAccessor level, BlockPos pos, int age, Direction facing)
    {
        level.setBlock(pos, FluidHelpers.fillWithFluid(getTrunk().defaultBlockState().setValue(TOP_BLOCK, true).setValue(AGE, age).setValue(PROPERTY_BY_DIRECTION.get(facing), true), level.getBlockState(pos).getFluidState().getType()).setValue(NATURAL, true), Block.UPDATE_ALL);
        placeLeavesAround(level, pos, level.getRandom().nextInt(Mth.clamp(5 - age, 1, 5)));
    }

    public void placeDeadLeaves(LevelAccessor level, BlockPos pos, Direction facing)
    {
        level.setBlock(pos, FluidHelpers.fillWithFluid(getTrunk().defaultBlockState().setValue(TOP_BLOCK, true).setValue(AGE, 5).setValue(PROPERTY_BY_DIRECTION.get(facing), true), level.getBlockState(pos).getFluidState().getType()).setValue(NATURAL, true), Block.UPDATE_ALL);
        placeLeavesAround(level, pos, 0);
    }

    public void setTrunkWithFluid(LevelAccessor level, BlockPos pos)
    {
        level.setBlock(pos, getBodyStateWithFluid(level, pos).setValue(NATURAL, true), Block.UPDATE_ALL);
    }

    public BlockState getBodyStateWithFluid(LevelAccessor level, BlockPos pos)
    {
        return FluidHelpers.fillWithFluid(this.getStateForPlacement(level, pos), level.getBlockState(pos).getFluidState().getType());
    }

    public void placeLeavesAround(LevelAccessor level, BlockPos pos, int chanceToGrowCornerLeaves)
    {
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            BlockPos relativePos = pos.relative(direction);
            BlockState relativeState = level.getBlockState(relativePos);
            BlockState leavesBlock = FluidHelpers.fillWithFluid(getLeaves().defaultBlockState(), level.getBlockState(pos).getFluidState().getType());

            if ((EnvironmentHelpers.isWorldgenReplaceable(relativeState) || relativeState.getMaterial().isReplaceable()) && !isTrunk(relativeState))
            {
                level.setBlock(relativePos, leavesBlock.setValue(PalmLeavesBlock.DIRECTION, direction).setValue(PalmLeavesBlock.CENTER_BLOCK, true).setValue(TFCPalmLeavesBlock.PERSISTENT, false), Block.UPDATE_ALL);
            }

            if (chanceToGrowCornerLeaves == 0)
            {
                BlockPos relativePosCorner = relativePos.relative(direction.getClockWise());
                BlockState relativeStateCorner = level.getBlockState(relativePosCorner);
                if ((EnvironmentHelpers.isWorldgenReplaceable(relativeStateCorner) || relativeStateCorner.getMaterial().isReplaceable()) && !isTrunk(relativeStateCorner))
                {
                    level.setBlock(relativePosCorner, leavesBlock.setValue(PalmLeavesBlock.DIRECTION, direction.getClockWise()).setValue(PalmLeavesBlock.CORNER_BLOCK, true).setValue(TFCPalmLeavesBlock.PERSISTENT, false), Block.UPDATE_ALL);
                }
            }
        }
    }

    public void removeLeavesAround(LevelAccessor level, BlockPos pos)
    {
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            BlockPos relativePos = pos.relative(direction);
            BlockPos relativePosCorner = relativePos.relative(direction.getClockWise());
            if (isLeaves(level.getBlockState(relativePos)))
            {
                level.destroyBlock(relativePos, false);
            }
            if (isLeaves(level.getBlockState(relativePosCorner)))
            {
                level.destroyBlock(relativePosCorner, false);
            }
        }
    }
}
