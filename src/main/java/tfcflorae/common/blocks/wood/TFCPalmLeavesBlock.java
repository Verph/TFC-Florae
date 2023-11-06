package tfcflorae.common.blocks.wood;

import java.util.Random;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.wood.TFCLeavesBlock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.util.Helpers;

public abstract class TFCPalmLeavesBlock extends TFCLeavesBlock
{
    public static final DirectionProperty DIRECTION = DirectionProperty.create("direction", Direction.Plane.HORIZONTAL);
    public static final BooleanProperty CORNER_BLOCK = BooleanProperty.create("corner_block");
    public static final BooleanProperty CENTER_BLOCK = BooleanProperty.create("center_block");
    public static final BooleanProperty TOP_BLOCK = BooleanProperty.create("top_block");

    public final int maxDecayDistance;
    public final ExtendedProperties properties;
    @Nullable public final Supplier<? extends Block> fallenLeaves;
    @Nullable public final Supplier<? extends Block> fallenTwig;
    @Nullable public final Supplier<? extends Block> sapling;
    @Nullable public final Supplier<? extends Block> trunk;

    public static TFCPalmLeavesBlock create(ExtendedProperties properties, int maxDecayDistance, @Nullable Supplier<? extends Block> fallenLeaves, @Nullable Supplier<? extends Block> fallenTwig, @Nullable Supplier<? extends Block> sapling, @Nullable Supplier<? extends Block> trunk)
    {
        final IntegerProperty distanceProperty = getDistanceProperty(maxDecayDistance);
        return new TFCPalmLeavesBlock(properties, maxDecayDistance, fallenLeaves, fallenTwig, sapling, trunk)
        {
            @Override
            protected IntegerProperty getDistanceProperty()
            {
                return distanceProperty;
            }
        };
    }

    public TFCPalmLeavesBlock(ExtendedProperties properties, int maxDecayDistance, @Nullable Supplier<? extends Block> fallenLeaves, @Nullable Supplier<? extends Block> fallenTwig, @Nullable Supplier<? extends Block> sapling, @Nullable Supplier<? extends Block> trunk)
    {
        super(properties, maxDecayDistance, fallenLeaves, fallenTwig);

        this.maxDecayDistance = maxDecayDistance;
        this.properties = properties;
        this.fallenLeaves = fallenLeaves;
        this.fallenTwig = fallenTwig;
        this.sapling = sapling;
        this.trunk = trunk;

        this.registerDefaultState(this.stateDefinition.any().setValue(PERSISTENT, false).setValue(CORNER_BLOCK, false).setValue(CENTER_BLOCK, false).setValue(TOP_BLOCK, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(PERSISTENT, getDistanceProperty(), getFluidProperty(), DIRECTION, CORNER_BLOCK, CENTER_BLOCK, TOP_BLOCK);
    }

    public static IntegerProperty getDistanceProperty(int maxDecayDistance)
    {
        if (maxDecayDistance >= 7 && maxDecayDistance < 7 + TFCBlockStateProperties.DISTANCES.length)
        {
            return TFCBlockStateProperties.DISTANCES[maxDecayDistance - 7 + 1]; // we select one higher than max
        }
        throw new IllegalArgumentException("No property set for distance: " + maxDecayDistance);
    }

    protected abstract IntegerProperty getDistanceProperty();

    public boolean isTopBlock(BlockState state, LevelReader level, BlockPos pos)
    {
        return Helpers.isBlock(level.getBlockState(pos.below()), trunk.get()) && level.getBlockState(pos.below()).getValue(PalmTrunkBlock.TOP_BLOCK);
    }

    public boolean isCornerBlock(BlockState state, LevelReader level, BlockPos pos)
    {
        int connectedFaces = 0;
        for (Direction direction : Direction.values())
        {
            connectedFaces += Helpers.isBlock(level.getBlockState(pos.relative(direction)), trunk.get()) ? 1 : 0;
        }
        return connectedFaces <= 0;
    }

    public boolean isCenterBlock(BlockState state, LevelReader level, BlockPos pos)
    {
        int connectedFaces = 0;
        for (Direction direction : Direction.values())
        {
            connectedFaces += Helpers.isBlock(level.getBlockState(pos.relative(direction)), trunk.get()) ? 1 : 0;
        }
        return connectedFaces > 0;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos relativePos = pos.relative(state.getValue(DIRECTION).getOpposite());
        if (level.getBlockState(relativePos).getBlock() == trunk.get() || level.getBlockState(relativePos.below()).getBlock() == trunk.get() || level.getBlockState(relativePos).getBlock() instanceof TFCPalmLeavesBlock)
        {
            return true;
        }
        return isTopBlock(state, level, pos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        FluidHelpers.tickFluid(level, currentPos, state);

        if (!state.canSurvive(level, currentPos))
        {
            level.destroyBlock(currentPos, !state.getValue(PERSISTENT) ? true : false);
            return Blocks.AIR.defaultBlockState();
        }

        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            if (isCornerBlock(state, level, currentPos))
            {
                state.setValue(DIRECTION, direction).setValue(CORNER_BLOCK, true).setValue(CENTER_BLOCK, false);
            }
            else if (isCenterBlock(state, level, currentPos))
            {
                state.setValue(DIRECTION, direction).setValue(CORNER_BLOCK, false).setValue(CENTER_BLOCK, true);
            }
            else if (isTopBlock(state, level, currentPos))
            {
                state.setValue(DIRECTION, direction).setValue(TOP_BLOCK, true);
            }
        }
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        super.tick(state, level, pos, random);
        if (!state.canSurvive(level, pos))
        {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (state.getValue(getDistanceProperty()) > 2 && !state.getValue(PERSISTENT))
        {
            level.removeBlock(pos, false);
            if (random.nextFloat() < 0.01f) createDestructionEffects(state, level, pos, random, false);
            doParticles(level, pos.getX() + random.nextFloat(), pos.getY() + random.nextFloat(), pos.getZ() + random.nextFloat(), 1);
        }
        super.randomTick(state, level, pos, random);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
        return state.setValue(DIRECTION, rot.rotate(state.getValue(DIRECTION)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
        return state.rotate(mirrorIn.getRotation(state.getValue(DIRECTION)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = defaultBlockState();
        Direction direction = context.getHorizontalDirection().getOpposite();
        return FluidHelpers.fillWithFluid(state.setValue(DIRECTION, direction).setValue(CORNER_BLOCK, isCornerBlock(state, level, pos)).setValue(CENTER_BLOCK, isCenterBlock(state, level, pos)).setValue(TOP_BLOCK, isTopBlock(state, level, pos)), level.getBlockState(pos).getFluidState().getType());
    }

    @Override
    public OffsetType getOffsetType()
    {
        return OffsetType.XYZ;
    }
}
