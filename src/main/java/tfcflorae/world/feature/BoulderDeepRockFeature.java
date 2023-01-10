package tfcflorae.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;

import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.settings.RockSettings;

public class BoulderDeepRockFeature extends Feature<RockConfig>
{
    public BoulderDeepRockFeature(Codec<RockConfig> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<RockConfig> context)
    {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final RockConfig config = context.config();
        final Random random = context.random();

        final ChunkDataProvider provider = ChunkDataProvider.get(context.chunkGenerator());
        final ChunkData data = provider.get(level, pos);
        final RockSettings rock = data.getRockData().getRock(pos.getX(), 10, pos.getZ());
        final List<BlockState> states = config.getStates(rock.raw());

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

        final BlockState stateAt = level.getBlockState(pos);
        final BlockState rockState = FluidHelpers.fillWithFluid(state.get(), stateAt.getFluidState().getType());

        if (EnvironmentHelpers.isWorldgenReplaceable(stateAt) && rockState != null && rockState.canSurvive(level, pos))
        {
            setBlock(level, pos, rockState);
            return true;
        }
        return false;
    }
}
