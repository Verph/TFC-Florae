package tfcflorae.world.feature.tree.joshua;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.core.BlockPos;

import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.world.feature.BlockConfig;

import tfcflorae.common.blocks.wood.TFCFJoshuaLeavesBlock;

public class JoshuaTreeFeature extends Feature<BlockConfig<TFCFJoshuaLeavesBlock>>
{
    public static final Codec<BlockConfig<TFCFJoshuaLeavesBlock>> CODEC = BlockConfig.codec(b -> b instanceof TFCFJoshuaLeavesBlock t ? t : null, "Must be a " + TFCFJoshuaLeavesBlock.class.getSimpleName());

    public JoshuaTreeFeature(Codec<BlockConfig<TFCFJoshuaLeavesBlock>> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockConfig<TFCFJoshuaLeavesBlock>> context)
    {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final BlockState state = level.getBlockState(pos);

        //if (pos.getY() > seaLevel && (materialAt != Material.WATER || !Helpers.isFluid(fluidState, FluidTags.WATER)) && TFCFJoshuaTrunkBlock.canConnectTo(state))
        if (EnvironmentHelpers.isWorldgenReplaceable(state) && !state.getMaterial().isLiquid())
        {
            final FluidState fluidAt = level.getFluidState(pos);
            return context.config().block().generatePlant(level, pos, context.random(), 8, fluidAt.getType());
        }
        else
            return false;
    }
}
