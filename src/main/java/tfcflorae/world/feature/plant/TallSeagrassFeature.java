package tfcflorae.world.feature.plant;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import com.mojang.serialization.Codec;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.feature.BlockConfig;

import tfcflorae.common.blocks.plant.TFCFSeagrassBlock;

public class TallSeagrassFeature extends Feature<BlockConfig<TFCFSeagrassBlock>>
{
    public static final Codec<BlockConfig<TFCFSeagrassBlock>> CODEC = BlockConfig.codec(b -> b instanceof TFCFSeagrassBlock t ? t : null, "Must be a " + TFCFSeagrassBlock.class.getSimpleName());

    public TallSeagrassFeature(Codec<BlockConfig<TFCFSeagrassBlock>> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockConfig<TFCFSeagrassBlock>> context)
    {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final FluidState fluidAt = level.getFluidState(pos);

        final Fluid fluidHere = level.getFluidState(pos).getType();
        final Fluid fluidTop = level.getFluidState(pos.above()).getType();

        if (!fluidHere.isSame(Fluids.EMPTY) && !fluidTop.isSame(Fluids.EMPTY) && EnvironmentHelpers.isWorldgenReplaceable(level, pos))
        {
            final int random = context.random().nextInt(5);
            if (random <= 1)
            {
                context.config().block().placeTwoHalves(level, pos, 2, context.random(), fluidAt.getType());
            }
            else
            {
                context.config().block().placeSingle(level, pos, 2, context.random(), fluidAt.getType());
            }
        }
        return true;
    }
}
