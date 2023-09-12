package tfcflorae.common.blocks.plant;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.ITallPlant.Part;
import net.dries007.tfc.common.blocks.plant.PlantRegrowth;
import net.dries007.tfc.common.blocks.plant.WaterPlantBlock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.registry.RegistryPlant;

public abstract class TFCFSeagrassBlock extends WaterPlantBlock
{
    protected static final EnumProperty<Part> PART = TFCBlockStateProperties.TALL_PLANT_PART;
    public static final BooleanProperty SINGLE = BooleanProperty.create("single");
    protected static final VoxelShape PLANT_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    protected static final VoxelShape SHORTER_PLANT_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 8.0, 14.0);

    public static TFCFSeagrassBlock create(RegistryPlant plant, FluidProperty fluid, BlockBehaviour.Properties properties)
    {
        return new TFCFSeagrassBlock(ExtendedProperties.of(properties))
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

    protected TFCFSeagrassBlock(ExtendedProperties properties)
    {
        super(properties);

        BlockState stateDefinition = getStateDefinition().any().setValue(PART, Part.LOWER).setValue(SINGLE, false);
        IntegerProperty stageProperty = getPlant().getStageProperty();
        if (stageProperty != null)
        {
            stateDefinition = stateDefinition.setValue(stageProperty, 0);
        }
        registerDefaultState(stateDefinition);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos pos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(pos);
        BlockState state = updateStateWithCurrentMonth(defaultBlockState().setValue(SINGLE, true));
        if (getFluidProperty().canContain(fluidState.getType()))
        {
            state = state.setValue(getFluidProperty(), getFluidProperty().keyFor(fluidState.getType()));
        }
        return state;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        FluidHelpers.tickFluid(level, currentPos, state);
        Part part = state.getValue(PART);
        if (facing.getAxis() != Direction.Axis.Y || part == Part.LOWER != (facing == Direction.UP) || facingState.getBlock() == this && facingState.getValue(PART) != part)
        {
            if (facing.getAxis() != Direction.Axis.Y || part == Part.LOWER != (facing == Direction.UP) || facingState.getBlock() == this && facingState.getValue(PART) != part)
            {
                return part == Part.LOWER && facing == Direction.DOWN && !state.canSurvive(level, currentPos) ? placeWaterOrAir(level, currentPos) : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
            }
            else
            {
                return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
            }
        }
        else if (state.getValue(SINGLE) && level.getBlockState(currentPos.below()).getBlock() instanceof TFCFSeagrassBlock && level.getBlockState(currentPos.below()).getValue(SINGLE))
        {
            FluidState fluidState = level.getFluidState(currentPos.below());
            if (fluidState.getType() != Fluids.EMPTY)
            {
                return fluidState.createLegacyBlock();
            }
            return Blocks.AIR.defaultBlockState();
        }
        else
        {
            FluidState fluidState = level.getFluidState(currentPos);
            if (fluidState.getType() != Fluids.EMPTY)
            {
                return fluidState.createLegacyBlock();
            }
            return Blocks.AIR.defaultBlockState();
        }
    }

    public BlockState placeWaterOrAir(LevelAccessor level, BlockPos currentPos)
    {
        FluidState fluidState = level.getFluidState(currentPos.below());
        if (fluidState.getType() != Fluids.EMPTY)
        {
            return fluidState.createLegacyBlock();
        }
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (!state.canSurvive(level, pos))
        {
            placeWaterOrAir(level, pos);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        if (state.getValue(getFluidProperty()) != getFluidProperty().keyFor(Fluids.EMPTY))
        {
            if (state.getValue(PART) == Part.LOWER || state.getValue(SINGLE))
            {
                if (level.getBlockState(pos.below()).getBlock() instanceof TFCFSeagrassBlock)
                {
                    return !level.getBlockState(pos.below()).getValue(SINGLE);
                }
                return super.canSurvive(state, level, pos) && level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
            }
            else
            {
                BlockState blockstate = level.getBlockState(pos.below());
                if (state.getBlock() != this)
                {
                    return super.canSurvive(state, level, pos);
                }
                return blockstate.getBlock() == this && blockstate.getValue(PART) == Part.LOWER;
            }
        }
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        Part part = state.getValue(PART);
        Boolean single = state.getValue(SINGLE);
        if (part == Part.LOWER || single == false)
            return PLANT_SHAPE;
        return SHORTER_PLANT_SHAPE;
    }

    @Override
    public OffsetType getOffsetType()
    {
        return OffsetType.XZ;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        if (getPlant().getStageProperty() != null)
        {
            builder.add(getPlant().getStageProperty());
        }
        super.createBlockStateDefinition(builder.add(PART, SINGLE));
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        if (!level.isClientSide)
        {
            if (player.isCreative())
            {
                if (state.getValue(PART) == Part.UPPER)
                {
                    BlockPos blockpos = pos.below();
                    BlockState blockstate = level.getBlockState(blockpos);
                    if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(PART) == Part.LOWER)
                    {
                        level.setBlockAndUpdate(blockpos, state.setValue(AGE, 0).setValue(SINGLE, true));
                    }
                }
            }
            else
            {
                dropResources(state, level, pos, null, player, player.getMainHandItem());
                level.setBlockAndUpdate(pos.below(), state.setValue(AGE, 0).setValue(SINGLE, true));
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        super.randomTick(state, level, pos, random);
        int j = state.getValue(AGE);

        if (random.nextDouble() < TFCConfig.SERVER.plantGrowthChance.get())
        {
            if (j == 3 && canGrow(level, pos, state))
            {
                if (canContainDouble(level, pos))
                {
                    grow(level, random, pos, state);
                }
            }
            else if (j < 3)
            {
                FluidState fluidState = level.getFluidState(pos);
                if (getFluidProperty().canContain(fluidState.getType()))
                {
                    level.setBlockAndUpdate(pos, state.setValue(AGE, j + 1).setValue(PART, state.getValue(PART)).setValue(getFluidProperty(), getFluidProperty().keyFor(fluidState.getType())));
                }
            }
        }
        if (PlantRegrowth.canSpread(level, random))
        {
            final BlockPos newPos = PlantRegrowth.spreadSelf(state, level, pos, random, 2, 2, 4);
            if (newPos != null)
            {
                FluidState fluidState = level.getFluidState(newPos);
                if (getFluidProperty().canContain(fluidState.getType()))
                {
                    level.setBlockAndUpdate(newPos, updateStateWithCurrentMonth(state.setValue(AGE, 0).setValue(PART, Part.LOWER).setValue(SINGLE, true).setValue(getFluidProperty(), getFluidProperty().keyFor(fluidState.getType()))));
                }
            }
        }
    }

    public boolean canGrow(ServerLevel level, BlockPos pos, BlockState state)
    {
        return state.getValue(SINGLE) == true && !level.getBlockState(pos.above()).isAir();
    }

    public void grow(ServerLevel level, Random rand, BlockPos pos, BlockState state)
    {
        IntegerProperty stageProperty = getPlant().getStageProperty();
        FluidState fluidState = level.getFluidState(pos);
        FluidState fluidStateAbove = level.getFluidState(pos.above());
        if (stageProperty != null)
        {
            level.setBlockAndUpdate(pos.above(1), state.setValue(AGE, 0).setValue(stageProperty, getPlant().stageFor(Calendars.SERVER.getCalendarMonthOfYear())).setValue(PART, Part.UPPER).setValue(SINGLE, false).setValue(getFluidProperty(), getFluidProperty().keyFor(fluidStateAbove.getType())));
            BlockState blockState = state.setValue(AGE, 0).setValue(stageProperty, getPlant().stageFor(Calendars.SERVER.getCalendarMonthOfYear())).setValue(PART, Part.LOWER).setValue(SINGLE, false).setValue(getFluidProperty(), getFluidProperty().keyFor(fluidState.getType()));
            level.setBlockAndUpdate(pos, blockState);
            blockState.neighborChanged(level, pos.above(1), this, pos, false);
        }
        else
        {
            level.setBlockAndUpdate(pos.above(1), state.setValue(AGE, 0).setValue(PART, Part.UPPER).setValue(SINGLE, false).setValue(getFluidProperty(), getFluidProperty().keyFor(fluidStateAbove.getType())));
            BlockState blockState = state.setValue(AGE, 0).setValue(PART, Part.LOWER).setValue(SINGLE, false).setValue(getFluidProperty(), getFluidProperty().keyFor(fluidState.getType()));
            level.setBlockAndUpdate(pos, blockState);
            blockState.neighborChanged(level, pos.above(1), this, pos, false);
        }
    }

    public void placeTwoHalves(LevelAccessor level, BlockPos pos, int flags, Random random, Fluid fluid)
    {
        FluidState fluidState = level.getFluidState(pos);
        if (getFluidProperty().canContain(fluidState.getType()) && fluidState.getType() != Fluids.EMPTY)
        {
            if (level.getBlockState(pos.above()).isAir())
            {
                placeSingle(level, pos, flags, random, fluid);
            }
            else if (canContainDouble(level, pos))
            {
                int age = random.nextInt(3);
                level.setBlock(pos, updateStateWithCurrentMonth(defaultBlockState().setValue(TFCBlockStateProperties.TALL_PLANT_PART, Part.LOWER).setValue(TFCBlockStateProperties.AGE_3, age).setValue(SINGLE, false).setValue(getFluidProperty(), getFluidProperty().keyFor(fluid))), flags);
                level.setBlock(pos.above(), updateStateWithCurrentMonth(defaultBlockState().setValue(TFCBlockStateProperties.TALL_PLANT_PART, Part.UPPER).setValue(TFCBlockStateProperties.AGE_3, age).setValue(SINGLE, false).setValue(getFluidProperty(), getFluidProperty().keyFor(fluid))), flags);
            }
        }
    }

    public void placeSingle(LevelAccessor level, BlockPos pos, int flags, Random random, Fluid fluid)
    {
        int age = random.nextInt(3);
        level.setBlock(pos, updateStateWithCurrentMonth(defaultBlockState().setValue(TFCBlockStateProperties.TALL_PLANT_PART, Part.LOWER).setValue(TFCBlockStateProperties.AGE_3, age).setValue(SINGLE, true).setValue(getFluidProperty(), getFluidProperty().keyFor(fluid))), flags);
    }

    public boolean canContainDouble(LevelAccessor level, BlockPos pos)
    {
        FluidState fluidState = level.getFluidState(pos);
        FluidState fluidStateAbove = level.getFluidState(pos.above());
        return getFluidProperty().canContain(fluidState.getType()) && fluidState.getType() != Fluids.EMPTY && getFluidProperty().canContain(fluidStateAbove.getType()) && fluidStateAbove.getType() != Fluids.EMPTY;
    }
}
