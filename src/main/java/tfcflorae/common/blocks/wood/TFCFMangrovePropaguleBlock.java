package tfcflorae.common.blocks.wood;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jetbrains.annotations.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.blocks.EntityBlockExtension;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.IForgeBlockExtension;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.wood.TFCSaplingBlock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.FluidType;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.world.feature.tree.TFCTreeGrower;

import tfcflorae.TFCFlorae;
import tfcflorae.common.TFCFTags;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.world.feature.tree.TFCFMangroveTreeGrower;

public class TFCFMangrovePropaguleBlock extends SaplingBlock implements IForgeBlockExtension, EntityBlockExtension, IFluidLoggable
{
    private static final VoxelShape[] SHAPES = new VoxelShape[]{Block.box(7.0D, 13.0D, 7.0D, 9.0D, 16.0D, 9.0D), Block.box(7.0D, 10.0D, 7.0D, 9.0D, 16.0D, 9.0D), Block.box(7.0D, 7.0D, 7.0D, 9.0D, 16.0D, 9.0D), Block.box(7.0D, 3.0D, 7.0D, 9.0D, 16.0D, 9.0D), Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D)};
    public static final IntegerProperty AGE = TFCBlockStateProperties.AGE_4;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    public static final FluidProperty FLUID = TFCBlockStateProperties.WATER;

    public final TFCFWood wood;
    public final ExtendedProperties properties;
    public final int daysToGrow;

    public TFCFMangrovePropaguleBlock(TFCFWood wood, ExtendedProperties properties, int days)
    {
        super(new TFCFMangroveTreeGrower(wood, 0.85F), properties.properties());
        this.wood = wood;
        this.properties = properties;
        this.daysToGrow = days;

        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0).setValue(AGE, 0).setValue(WATERLOGGED, false).setValue(HANGING, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return EntityBlockExtension.super.newBlockEntity(pos, state);
    }

    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(STAGE, AGE, WATERLOGGED, HANGING, getFluidProperty());
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter block, BlockPos pos)
    {
        return super.mayPlaceOn(state, block, pos) || Helpers.isBlock(state.getBlock(), BlockTags.SAND) || Helpers.isBlock(state.getBlock(), Tags.Blocks.SAND) || Helpers.isBlock(state.getBlock(), TFCFTags.Blocks.CLAY) || Helpers.isBlock(state.getBlock(), TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(state, TFCTags.Blocks.SEA_BUSH_PLANTABLE_ON) || Helpers.isBlock(state.getBlock(), TFCTags.Blocks.TREE_GROWS_ON);
    }

    @Override @Nullable @SuppressWarnings("ConstantConditions")
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        final FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(getFluidProperty(), getFluidProperty().keyForOrEmpty(fluid.getType())).setValue(AGE, 4);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter block, BlockPos pos, CollisionContext context)
    {
        Vec3 offset = state.getOffset(block, pos);
        VoxelShape shape = !state.getValue(HANGING) ? SHAPES[4] : SHAPES[state.getValue(AGE)];
        return shape.move(offset.x, offset.y, offset.z);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        FluidState fluidState = level.getFluidState(pos);
        return isHanging(state) ? level.getBlockState(pos.above()).is(TFCFBlocks.WOODS_SEASONAL_LEAVES.get(wood).get()) : (super.canSurvive(state, level, pos) || Helpers.isBlock(state, TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(state, TFCTags.Blocks.SEA_BUSH_PLANTABLE_ON) || fluidState.getType() == TFCFluids.SALT_WATER.getSource() || fluidState.getType() == TFCFluids.SPRING_WATER.getSource() ||  fluidState.getType() == Fluids.WATER.getSource());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        FluidHelpers.tickFluid(level, pos, state);
        return direction == Direction.UP && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, newState, level, pos, newPos);
    }

    @Override
    public OffsetType getOffsetType()
    {
        return OffsetType.XZ;
    }

    @Override
    public FluidProperty getFluidProperty()
    {
        return FLUID;
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state)
    {
        return IFluidLoggable.super.getFluidState(state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (!isHanging(state) && random.nextInt(7) == 0)
        {
            level.getBlockEntity(pos, TFCBlockEntities.TICK_COUNTER.get()).ifPresent(counter -> {
                long days = counter.getTicksSinceUpdate() / ICalendar.TICKS_IN_DAY;
                if (days > daysToGrow)
                {
                    this.advanceTree(level, pos, state.setValue(STAGE, 1), random);
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                }
            });
        }
        else
        {
            if (!ageAtMax(state)) level.setBlock(pos, state.cycle(AGE), 2);
        }
    }

    private static boolean isHanging(BlockState state)
    {
        return state.getValue(HANGING);
    }

    private static boolean ageAtMax(BlockState state)
    {
        return state.getValue(AGE) == 4;
    }

    public BlockState createPropagule()
    {
        return createPropagule(0);
    }

    public BlockState createPropagule(int age)
    {
        return TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.SAPLING).get().defaultBlockState().setValue(HANGING, true).setValue(AGE, age);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        TickCounterBlockEntity.reset(level, pos);
        super.setPlacedBy(level, pos, state, placer, stack);
    }
}
