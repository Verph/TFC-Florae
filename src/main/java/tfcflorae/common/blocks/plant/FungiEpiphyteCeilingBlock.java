package tfcflorae.common.blocks.plant;

import java.util.Map;
import java.util.Random;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableMap;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraftforge.common.Tags;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.PlantBlock;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.Season;
import net.dries007.tfc.util.registry.RegistryPlant;

import tfcflorae.client.particle.TFCFParticles;

public abstract class FungiEpiphyteCeilingBlock extends PlantBlock
{
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final IntegerProperty AGE = TFCBlockStateProperties.AGE_3;

    protected static final VoxelShape UP_SHAPE = box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    protected static final VoxelShape DOWN_SHAPE = box(0.0, 4.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape NORTH_SHAPE = box(0.0, 0.0, 4.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape SOUTH_SHAPE = box(0.0, 0.0, 0.0, 16.0, 16.0, 12.0);
    protected static final VoxelShape WEST_SHAPE = box(4.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EAST_SHAPE = box(0.0, 0.0, 0.0, 12.0, 16.0, 16.0);

    protected static final Map<Direction, VoxelShape> SHAPES = ImmutableMap.of(Direction.UP, UP_SHAPE, Direction.DOWN, DOWN_SHAPE, Direction.NORTH, NORTH_SHAPE, Direction.SOUTH, SOUTH_SHAPE, Direction.WEST, WEST_SHAPE, Direction.EAST, EAST_SHAPE);

    public static FungiEpiphyteCeilingBlock create(RegistryPlant plant, ExtendedProperties properties)
    {
        return new FungiEpiphyteCeilingBlock(properties)
        {
            @Override
            public RegistryPlant getPlant()
            {
                return plant;
            }
        };
    }

    protected FungiEpiphyteCeilingBlock(ExtendedProperties properties)
    {
        super(properties);

        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random)
    {
        if (getPlant() instanceof TFCFPlant plantTFCF && plantTFCF.getSporeColor() > -1 && level.isDay() && level.getSkyDarken() < 2 && (Calendars.CLIENT.getCalendarMonthOfYear().getSeason() == Season.SUMMER || Calendars.CLIENT.getCalendarMonthOfYear().getSeason() == Season.FALL))
        {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int l = 0; l < 14; ++l)
            {
                int sporeSpread = plantTFCF.getSporeSpread();
                blockpos$mutableblockpos.set(i + Mth.nextInt(random, -sporeSpread, sporeSpread), j - random.nextInt(sporeSpread), k + Mth.nextInt(random, -sporeSpread, sporeSpread));
                BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);

                if (!blockstate.isCollisionShapeFullBlock(level, blockpos$mutableblockpos))
                {
                    level.addParticle(new BlockParticleOption(TFCFParticles.FALLING_SPORE.get(), state), (double)blockpos$mutableblockpos.getX() + random.nextDouble(), (double)blockpos$mutableblockpos.getY() + random.nextDouble(), (double)blockpos$mutableblockpos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
                }
            }
        }
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
        return ((Helpers.isBlock(attachedState, Tags.Blocks.STONE) || Helpers.isBlock(attachedState, BlockTags.LOGS) || attachedState.is(BlockTags.MUSHROOM_GROW_BLOCK) || Helpers.isBlock(attachedState.getBlock(), TFCTags.Blocks.SEA_BUSH_PLANTABLE_ON) || Helpers.isBlock(attachedState.getBlock(), TFCTags.Blocks.GRASS_PLANTABLE_ON) || Helpers.isBlock(attachedState.getBlock(), TFCTags.Blocks.BUSH_PLANTABLE_ON)) && level.getRawBrightness(pos, 0) < 13);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type)
    {
        return false;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Direction direction = context.getClickedFace();
        if (direction != Direction.UP)
        {
            return updateStateWithCurrentMonth(defaultBlockState()).setValue(FACING, direction);
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(FACING));
    }
}
