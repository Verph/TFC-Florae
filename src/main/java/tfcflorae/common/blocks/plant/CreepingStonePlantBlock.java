package tfcflorae.common.blocks.plant;

import java.util.Map;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.DirectionPropertyBlock;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.CreepingPlantBlock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryPlant;

import tfcflorae.common.TFCFTags;

public abstract class CreepingStonePlantBlock extends CreepingPlantBlock 
{
    public static CreepingStonePlantBlock createStone(RegistryPlant plant, ExtendedProperties properties)
    {
        return new CreepingStonePlantBlock(properties)
        {
            @Override
            public RegistryPlant getPlant()
            {
                return plant;
            }

            @Override
            public boolean canCreepOn(BlockState state)
            {
                return Helpers.isBlock(state, TFCFTags.Blocks.CREEPING_STONE_PLANTABLE_ON);
            }
        };
    }

    protected final Map<BlockState, VoxelShape> shapeCache;

    protected CreepingStonePlantBlock(ExtendedProperties properties)
    {
        super(properties);

        registerDefaultState(DirectionPropertyBlock.setAllDirections(getStateDefinition().any(), false));
        shapeCache = DirectionPropertyBlock.makeShapeCache(getStateDefinition(), SHAPES::get);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        state = state.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction), canCreepOn(facingState));
        return isEmpty(state) ? Blocks.AIR.defaultBlockState() : state;
    }

    private boolean isEmpty(BlockState state)
    {
        for (BooleanProperty property : SHAPES.keySet())
        {
            if (state.getValue(property))
            {
                return false;
            }
        }
        return true;
    }

    @NotNull
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return updateStateFromSides(context.getLevel(), context.getClickedPos(), updateStateWithCurrentMonth(defaultBlockState()));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (Direction direction : UPDATE_SHAPE_ORDER)
        {
            if (canCreepOn(level.getBlockState(mutablePos.setWithOffset(pos, direction))))
            {
                return true;
            }
        }
        return false;
    }

    private BlockState updateStateFromSides(LevelAccessor level, BlockPos pos, BlockState state)
    {
        final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        boolean hasEarth = false;
        for (Direction direction : UPDATE_SHAPE_ORDER)
        {
            mutablePos.setWithOffset(pos, direction);
            boolean ground = canCreepOn(level.getBlockState(mutablePos));

            state = state.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction), ground);
            hasEarth |= ground;
        }
        return hasEarth ? state : Blocks.AIR.defaultBlockState();
    }

    public boolean canCreepOn(BlockState state)
    {
        return Helpers.isBlock(state, TFCTags.Blocks.CREEPING_PLANTABLE_ON);
    }
}
