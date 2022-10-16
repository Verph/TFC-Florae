package tfcflorae.world.feature;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.settings.RockSettings;

public class RockColumnFeature extends Feature<RockColumnConfig>
{
    private static final ImmutableList<Block> CANNOT_PLACE_ON = ImmutableList.of(Blocks.LAVA, Blocks.BEDROCK, Blocks.MAGMA_BLOCK, Blocks.SOUL_SAND, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER);
    private static final int CLUSTERED_REACH = 5;
    private static final int CLUSTERED_SIZE = 50;
    private static final int UNCLUSTERED_REACH = 8;
    private static final int UNCLUSTERED_SIZE = 15;

    public RockColumnFeature(Codec<RockColumnConfig> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<RockColumnConfig> context)
    {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final Random random = context.random();
        final RockColumnConfig config = context.config();

        final ChunkDataProvider provider = ChunkDataProvider.get(context.chunkGenerator());
        final ChunkData data = provider.get(context.level(), pos);
        final RockSettings rock = data.getRockData().getRock(pos);
        final List<BlockState> states = config.getStates(rock.raw());
        final int seaLevel = level.getLevel().getChunkSource().getGenerator().getSeaLevel();

        if (states != null)
        {
            if (!canPlaceAt(level, seaLevel, pos.mutable()))
            {
                return false;
            }
            else
            {
                int j = config.height().sample(random);
                boolean flag = random.nextFloat() < 0.9F;
                int k = Math.min(j, flag ? 5 : 8);
                int l = flag ? 50 : 15;
                boolean flag1 = false;
                for(BlockPos blockpos1 : BlockPos.randomBetweenClosed(random, l, pos.getX() - k, pos.getY(), pos.getZ() - k, pos.getX() + k, pos.getY(), pos.getZ() + k))
                {
                    int i1 = j - blockpos1.distManhattan(pos);
                    if (i1 >= 0)
                    {
                        flag1 |= this.placeColumn(level, seaLevel, blockpos1, i1, config.reach().sample(random), states, random);
                    }
                }
                return flag1;
            }
        }
        return false;
    }

    private boolean placeColumn(LevelAccessor level, int seaLevel, BlockPos pos, int distance, int reach, List<BlockState> states, Random random)
    {
        Supplier<BlockState> state;
        if (states.size() == 1)
        {
            final BlockState onlyState = states.get(0);
            state = () -> onlyState;
        }
        else
        {
            state = () -> states.get(random.nextInt(states.size()));
        }

        boolean flag = false;
        for(BlockPos blockpos : BlockPos.betweenClosed(pos.getX() - reach, pos.getY(), pos.getZ() - reach, pos.getX() + reach, pos.getY(), pos.getZ() + reach))
        {
            int i = blockpos.distManhattan(pos);
            BlockPos blockpos1 = isAirOrLavaOcean(level, seaLevel, blockpos) ? findSurface(level, seaLevel, blockpos.mutable(), i) : findAir(level, blockpos.mutable(), i);
            if (blockpos1 != null)
            {
                int j = distance - i / 2;
                for(BlockPos.MutableBlockPos blockpos$mutableBlockPos = blockpos1.mutable(); j >= 0; --j)
                {
                    if (isAirOrLavaOcean(level, seaLevel, blockpos$mutableBlockPos))
                    {
                        this.setBlock(level, blockpos$mutableBlockPos, state.get());
                        blockpos$mutableBlockPos.move(Direction.UP);
                        flag = true;
                    }
                    else
                    {
                        if (!level.getBlockState(blockpos$mutableBlockPos).is(state.get().getBlock()))
                        {
                            break;
                        }
                        blockpos$mutableBlockPos.move(Direction.UP);
                    }
                }
            }
        }
        return flag;
    }

    @Nullable
    private static BlockPos findSurface(LevelAccessor level, int seaLevel, BlockPos.MutableBlockPos pos, int distance)
    {
        while(pos.getY() > level.getMinBuildHeight() + 1 && distance > 0)
        {
            --distance;
            if (canPlaceAt(level, seaLevel, pos))
            {
                return pos;
            }
            pos.move(Direction.DOWN);
        }
        return null;
    }

    private static boolean canPlaceAt(LevelAccessor level, int seaLevel, BlockPos.MutableBlockPos pos)
    {
        if (!isAirOrLavaOcean(level, seaLevel, pos))
        {
            return false;
        }
        else
        {
            BlockState blockstate = level.getBlockState(pos.move(Direction.DOWN));
            pos.move(Direction.UP);
            return !blockstate.isAir() && !CANNOT_PLACE_ON.contains(blockstate.getBlock());
        }
    }

    @Nullable
    private static BlockPos findAir(LevelAccessor level, BlockPos.MutableBlockPos pos, int distance)
    {
        while(pos.getY() < level.getMaxBuildHeight() && distance > 0)
        {
            --distance;
            BlockState blockstate = level.getBlockState(pos);
            if (CANNOT_PLACE_ON.contains(blockstate.getBlock()))
            {
                return null;
            }
            if (blockstate.isAir())
            {
                return pos;
            }
            pos.move(Direction.UP);
        }
        return null;
    }

    private static boolean isAirOrLavaOcean(LevelAccessor level, int seaLevel, BlockPos pos)
    {
        BlockState blockstate = level.getBlockState(pos);
        return blockstate.isAir() || blockstate.is(Blocks.LAVA) && pos.getY() <= seaLevel;
    }
}
