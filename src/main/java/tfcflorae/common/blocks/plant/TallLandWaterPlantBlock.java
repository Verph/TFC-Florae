package tfcflorae.common.blocks.plant;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.ITallPlant;
import net.dries007.tfc.common.blocks.plant.TFCTallGrassBlock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryPlant;
import org.jetbrains.annotations.Nullable;

import static net.dries007.tfc.world.TFCChunkGenerator.SEA_LEVEL_Y;

public abstract class TallLandWaterPlantBlock extends TFCTallGrassBlock implements IFluidLoggable
{
    public static final EnumProperty<ITallPlant.Part> PART = TFCBlockStateProperties.TALL_PLANT_PART;

    public static TallLandWaterPlantBlock create(RegistryPlant plant, FluidProperty fluid, Properties properties)
    {
        return new TallLandWaterPlantBlock(ExtendedProperties.of(properties))
        {
            @Override
            public RegistryPlant getPlant()
            {
                return plant;
            }

            @Override
            public FluidProperty getFluidProperty()
            {
                return fluid;
            }
        };
    }

    protected TallLandWaterPlantBlock(ExtendedProperties properties)
    {
        super(properties);

        registerDefaultState(getStateDefinition().any().setValue(getFluidProperty(), getFluidProperty().keyFor(Fluids.EMPTY)).setValue(PART, Part.LOWER));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        FluidHelpers.tickFluid(level, currentPos, state);
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockState belowState = level.getBlockState(pos.below());
        if (state.getValue(PART) == Part.LOWER)
        {
            if (pos.getY() >= SEA_LEVEL_Y)
            {
                return Helpers.isBlock(belowState, TFCTags.Blocks.BUSH_PLANTABLE_ON);
            }
            else
            {
                return Helpers.isBlock(belowState, TFCTags.Blocks.SEA_BUSH_PLANTABLE_ON) || Helpers.isBlock(belowState, TFCTags.Blocks.BUSH_PLANTABLE_ON);
            }
        }
        else
        {
            if (state.getBlock() != this)
            {
                if (pos.getY() >= SEA_LEVEL_Y)
                {
                    return Helpers.isBlock(belowState, TFCTags.Blocks.BUSH_PLANTABLE_ON);
                }
                else
                {
                    return Helpers.isBlock(belowState, TFCTags.Blocks.SEA_BUSH_PLANTABLE_ON) || Helpers.isBlock(belowState, TFCTags.Blocks.BUSH_PLANTABLE_ON);
                }
            }
            return belowState.getBlock() == this && belowState.getValue(PART) == Part.LOWER;
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos pos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(pos);
        BlockState state = updateStateWithCurrentMonth(defaultBlockState());

        if (getFluidProperty().canContain(fluidState.getType()))
        {
            state = state.setValue(getFluidProperty(), getFluidProperty().keyFor(fluidState.getType()));
        }

        return pos.getY() < context.getLevel().getMaxBuildHeight() - 1 && context.getLevel().getBlockState(pos.above()).canBeReplaced(context) ? state : null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(getFluidProperty()));
    }

    @Override
    public void placeTwoHalves(LevelAccessor level, BlockPos pos, int flags, Random random)
    {
        IntegerProperty stageProperty = getPlant().getStageProperty();
        final BlockPos posAbove = pos.above();
        final int age = random.nextInt(4);
        final Fluid fluidBottom = level.getFluidState(pos).getType();
        final Fluid fluidTop = level.getFluidState(posAbove).getType();
        if (!fluidBottom.isSame(Fluids.EMPTY))
        {
            BlockState state = FluidHelpers.fillWithFluid(defaultBlockState().setValue(AGE, age).setValue(PART, Part.LOWER), fluidBottom);
            BlockState stateUp = FluidHelpers.fillWithFluid(defaultBlockState().setValue(AGE, age).setValue(PART, Part.UPPER), fluidTop);

            if (stageProperty != null)
            {
                state = state.setValue(stageProperty, 2);
                stateUp = stateUp.setValue(stageProperty, 2);
            }

            if (state != null && stateUp != null)
            {
                level.setBlock(pos, state, flags);
                level.setBlock(posAbove, stateUp, flags);
            }
        }
        else if (fluidBottom.isSame(Fluids.EMPTY))
        {
            super.placeTwoHalves(level, pos, flags, random);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state)
    {
        return IFluidLoggable.super.getFluidState(state);
    }
}
