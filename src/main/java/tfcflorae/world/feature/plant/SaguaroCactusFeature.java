package tfcflorae.world.feature.plant;

import java.util.Random;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.world.feature.BlockConfig;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.plant.SaguaroCactusBlock;
import tfcflorae.common.blocks.plant.TFCFPlant;

public class SaguaroCactusFeature extends Feature<BlockConfig<SaguaroCactusBlock>>
{
    public static final Codec<BlockConfig<SaguaroCactusBlock>> CODEC = BlockConfig.codec(b -> b instanceof SaguaroCactusBlock t ? t : null, "Must be a " + SaguaroCactusBlock.class.getSimpleName());

    public SaguaroCactusFeature(Codec<BlockConfig<SaguaroCactusBlock>> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockConfig<SaguaroCactusBlock>> context)
    {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final Random random = context.random();
        final BlockState state = level.getBlockState(pos);

        if (EnvironmentHelpers.isWorldgenReplaceable(state) && !state.getMaterial().isLiquid())
        {
            return context.config().block().generateCactus(TFCFBlocks.FRUITING_PLANTS.get(TFCFPlant.SAGUARO_CACTUS).get(), level, random.nextBoolean(), pos, random, false);
        }
        else
            return false;
    }
}