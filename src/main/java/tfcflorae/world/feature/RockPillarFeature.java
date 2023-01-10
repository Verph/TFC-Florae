package tfcflorae.world.feature;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import com.mojang.serialization.Codec;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.settings.RockSettings;

public class RockPillarFeature extends Feature<RockPillarConfig>
{
    public RockPillarFeature(Codec<RockPillarConfig> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<RockPillarConfig> context)
    {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final Random random = context.random();
        final RockPillarConfig config = context.config();

        final ChunkDataProvider provider = ChunkDataProvider.get(context.chunkGenerator());
        final ChunkData data = provider.get(context.level(), pos);
        final RockSettings rock = data.getRockData().getRock(pos);
        final List<BlockState> states = config.getStates(rock.raw());

        if (states != null)
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

            if ((level.isEmptyBlock(pos) || DripstoneUtils.isEmptyOrWaterOrLava(level, pos)) && (!level.isEmptyBlock(pos.above()) || !DripstoneUtils.isEmptyOrWaterOrLava(level, pos.above())))
            {
                BlockPos.MutableBlockPos pos$mutableBlockPos = pos.mutable();
                BlockPos.MutableBlockPos pos$mutableBlockPos1 = pos.mutable();
                boolean flag = true;
                boolean flag1 = true;
                boolean flag2 = true;
                boolean flag3 = true;

                while(level.isEmptyBlock(pos$mutableBlockPos) || DripstoneUtils.isEmptyOrWaterOrLava(level, pos$mutableBlockPos))
                {
                    if (level.isOutsideBuildHeight(pos$mutableBlockPos))
                    {
                        return true;
                    }

                    level.setBlock(pos$mutableBlockPos, state.get(), 2);
                    flag = flag && this.placeHangOff(level, random, pos$mutableBlockPos1.setWithOffset(pos$mutableBlockPos, Direction.NORTH), states);
                    flag1 = flag1 && this.placeHangOff(level, random, pos$mutableBlockPos1.setWithOffset(pos$mutableBlockPos, Direction.SOUTH), states);
                    flag2 = flag2 && this.placeHangOff(level, random, pos$mutableBlockPos1.setWithOffset(pos$mutableBlockPos, Direction.WEST), states);
                    flag3 = flag3 && this.placeHangOff(level, random, pos$mutableBlockPos1.setWithOffset(pos$mutableBlockPos, Direction.EAST), states);
                    pos$mutableBlockPos.move(Direction.DOWN);
                }

                pos$mutableBlockPos.move(Direction.UP);
                this.placeBaseHangOff(level, random, pos$mutableBlockPos1.setWithOffset(pos$mutableBlockPos, Direction.NORTH), states);
                this.placeBaseHangOff(level, random, pos$mutableBlockPos1.setWithOffset(pos$mutableBlockPos, Direction.SOUTH), states);
                this.placeBaseHangOff(level, random, pos$mutableBlockPos1.setWithOffset(pos$mutableBlockPos, Direction.WEST), states);
                this.placeBaseHangOff(level, random, pos$mutableBlockPos1.setWithOffset(pos$mutableBlockPos, Direction.EAST), states);
                pos$mutableBlockPos.move(Direction.DOWN);
                BlockPos.MutableBlockPos pos$mutableBlockPos2 = new BlockPos.MutableBlockPos();

                for(int i = -3; i < 4; ++i)
                {
                    for(int j = -3; j < 4; ++j)
                    {
                        int k = Mth.abs(i) * Mth.abs(j);
                        if (random.nextInt(10) < 10 - k)
                        {
                            pos$mutableBlockPos2.set(pos$mutableBlockPos.offset(i, 0, j));
                            int l = 3;

                            while(level.isEmptyBlock(pos$mutableBlockPos1.setWithOffset(pos$mutableBlockPos2, Direction.DOWN)) || DripstoneUtils.isEmptyOrWaterOrLava(level, pos$mutableBlockPos1.setWithOffset(pos$mutableBlockPos2, Direction.DOWN)))
                            {
                                pos$mutableBlockPos2.move(Direction.DOWN);
                                --l;
                                if (l <= 0)
                                {
                                    break;
                                }
                            }
                            if (!level.isEmptyBlock(pos$mutableBlockPos1.setWithOffset(pos$mutableBlockPos2, Direction.DOWN)) || DripstoneUtils.isEmptyOrWaterOrLava(level, pos$mutableBlockPos1.setWithOffset(pos$mutableBlockPos2, Direction.DOWN)))
                            {
                                level.setBlock(pos$mutableBlockPos2, state.get(), 2);
                            }
                        }
                    }
                }
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }

    private void placeBaseHangOff(LevelAccessor level, Random random, BlockPos pos, List<BlockState> states)
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

        if (random.nextBoolean())
        {
            level.setBlock(pos, state.get(), 2);
        }
    }

    private boolean placeHangOff(LevelAccessor level, Random random, BlockPos pos, List<BlockState> states)
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
        if (random.nextInt(10) != 0)
        {
            level.setBlock(pos, state.get(), 2);
            return true;
        }
        else
        {
            return false;
        }
    }
}
