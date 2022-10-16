package tfcflorae.common.blocks.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;

import tfcflorae.common.blocks.TFCFBlocks;

public class TFCFMangroveRootsBlocks extends Block implements IFluidLoggable
{
    public static final FluidProperty FLUID = TFCBlockStateProperties.WATER;

    public final TFCFWood wood;
    public final ExtendedProperties properties;

    public TFCFMangroveRootsBlocks(TFCFWood wood, ExtendedProperties properties)
    {
        super(properties.properties());
        this.wood = wood;
        this.properties = properties;

        this.registerDefaultState(getStateDefinition().any());
    }

    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction)
    {
        return stateFrom.is(TFCFBlocks.MANGROVE_ROOTS.get(wood).get()) && direction.getAxis() == Direction.Axis.Y;
    }

    @Override @Nullable @SuppressWarnings("ConstantConditions")
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        final FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return defaultBlockState().setValue(getFluidProperty(), getFluidProperty().keyForOrEmpty(fluid.getType()));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(getFluidProperty());
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
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos)
    {
        return 1;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return Shapes.empty();
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos)
    {
        return 0.2F;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        if (entity.getBoundingBox().getYsize() > 1.0D || !(entity instanceof WaterAnimal))
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
        }
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
}