package tfcflorae.world.feature.plant;

import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import com.mojang.serialization.Codec;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.world.feature.BlockConfig;

import tfcflorae.common.blocks.plant.TallLandWaterPlantBlock;

public class LandEmergentPlantFeature extends Feature<BlockConfig<TallLandWaterPlantBlock>>
{
    public static final Codec<BlockConfig<TallLandWaterPlantBlock>> CODEC = BlockConfig.codec(b -> b instanceof TallLandWaterPlantBlock t ? t : null, "Must be a " + TallLandWaterPlantBlock.class.getSimpleName());

    public LandEmergentPlantFeature(Codec<BlockConfig<TallLandWaterPlantBlock>> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockConfig<TallLandWaterPlantBlock>> context)
    {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();

        final Fluid fluidTop = level.getFluidState(pos.above()).getType();
        if (fluidTop.isSame(Fluids.EMPTY) && EnvironmentHelpers.isWorldgenReplaceable(level, pos))
        {
            context.config().block().placeTwoHalves(level, pos, 2, context.random());
        }
        return true;
    }
}
