package tfcflorae.world.feature;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import com.mojang.serialization.Codec;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.util.EnvironmentHelpers;

import tfcflorae.common.blocks.TFCFBlocks;

public class LooseFlintFeature extends Feature<NoneFeatureConfiguration>
{
    public LooseFlintFeature(Codec<NoneFeatureConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final Random random = context.random();

        final BlockState stateAt = level.getBlockState(pos);
        final BlockState rockState = FluidHelpers.fillWithFluid(TFCFBlocks.LOOSE_FLINT.get().defaultBlockState(), stateAt.getFluidState().getType());

        if (EnvironmentHelpers.isWorldgenReplaceable(stateAt) && rockState != null && rockState.canSurvive(level, pos))
        {
            setBlock(level, pos, rockState.setValue(TFCBlockStateProperties.COUNT_1_3, 1 + random.nextInt(2)));
            return true;
        }
        else
            return false;
    }
}
