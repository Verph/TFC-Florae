package tfcflorae.common.blocks.plant;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.EpiphytePlantBlock;
import net.dries007.tfc.common.blocks.plant.Plant;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryPlant;

public abstract class FungiEpiphyteSolidBlock extends EpiphytePlantBlock
{
    public static final IntegerProperty AGE = TFCBlockStateProperties.AGE_3;

    protected static final VoxelShape NORTH_SHAPE_COLLISION = box(0.0, 6.0, 4.0, 16.0, 10.0, 16.0);
    protected static final VoxelShape SOUTH_SHAPE_COLLISION = box(0.0, 6.0, 0.0, 16.0, 10.0, 12.0);
    protected static final VoxelShape WEST_SHAPE_COLLISION = box(4.0, 6.0, 0.0, 16.0, 10.0, 16.0);
    protected static final VoxelShape EAST_SHAPE_COLLISION = box(0.0, 6.0, 0.0, 12.0, 10.0, 16.0);

    protected static final Map<Direction, VoxelShape> SHAPES_COLLISION = ImmutableMap.of(Direction.NORTH, NORTH_SHAPE_COLLISION, Direction.SOUTH, SOUTH_SHAPE_COLLISION, Direction.WEST, WEST_SHAPE_COLLISION, Direction.EAST, EAST_SHAPE_COLLISION);

    public static FungiEpiphyteSolidBlock create(RegistryPlant plant, ExtendedProperties properties)
    {
        return new FungiEpiphyteSolidBlock(properties)
        {
            @Override
            public RegistryPlant getPlant()
            {
                return plant;
            }
        };
    }

    protected FungiEpiphyteSolidBlock(ExtendedProperties properties)
    {
        super(properties);

        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (random.nextDouble() < TFCConfig.SERVER.plantGrowthChance.get())
        {
            state = state.setValue(AGE, Math.min(state.getValue(AGE) + 1, 3));

            if (random.nextInt(25) < TFCConfig.SERVER.plantGrowthChance.get())
            {
                int i = 5;
                int j = 4;

                for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, -1, -4), pos.offset(4, 1, 4)))
                {
                    if (level.getBlockState(blockpos).is(this))
                    {
                        --i;
                        if (i <= 0) {
                            return;
                        }
                    }
                }

                BlockPos blockpos1 = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
                for(int k = 0; k < 4; ++k)
                {
                    if (level.isEmptyBlock(blockpos1) && state.canSurvive(level, blockpos1))
                    {
                        pos = blockpos1;
                    }

                    blockpos1 = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
                }

                if (level.isEmptyBlock(blockpos1) && state.canSurvive(level, blockpos1))
                {
                    level.setBlock(blockpos1, state, 2);
                }
            }
        }
        level.setBlockAndUpdate(pos, updateStateWithCurrentMonth(state));
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockState attachedState = level.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));
        return (Helpers.isBlock(attachedState, BlockTags.LOGS) && level.getRawBrightness(pos, 0) < 13);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        if (state.getValue(AGE) >= 2)
        {
            return SHAPES_COLLISION.get(state.getValue(FACING));
        }
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        if (state.getValue(AGE) >= 2)
        {
            return SHAPES_COLLISION.get(state.getValue(FACING));
        }
        return Shapes.empty();
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type)
    {
        return false;
    }
}
