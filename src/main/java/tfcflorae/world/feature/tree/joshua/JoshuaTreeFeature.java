package tfcflorae.world.feature.tree.joshua;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.TFCChunkGenerator;
import net.dries007.tfc.world.feature.BlockConfig;

import tfcflorae.common.blocks.wood.TFCFJoshuaLeavesBlock;
import tfcflorae.common.blocks.wood.TFCFJoshuaTrunkBlock;

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
        final Material materialAt = level.getBlockState(pos).getMaterial();
        final FluidState fluidState = context.level().getFluidState(pos);
        final int seaLevel = level.getLevel().getChunkSource().getGenerator().getSeaLevel();

        //if (pos.getY() > seaLevel && (materialAt != Material.WATER || !Helpers.isFluid(fluidState, FluidTags.WATER)) && TFCFJoshuaTrunkBlock.canConnectTo(state))
        //{
            final FluidState fluidAt = level.getFluidState(pos);
            return context.config().block().generatePlant(level, pos, context.random(), 8, fluidAt.getType());
        //}
        //return false;
    }
}
