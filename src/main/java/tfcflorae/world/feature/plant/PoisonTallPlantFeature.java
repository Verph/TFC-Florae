package tfcflorae.world.feature.plant;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import com.mojang.serialization.Codec;
import net.dries007.tfc.world.feature.BlockConfig;

import tfcflorae.common.blocks.plant.TFCFTallGrassBlock;

public class PoisonTallPlantFeature extends Feature<BlockConfig<TFCFTallGrassBlock>>
{
    public static final Codec<BlockConfig<TFCFTallGrassBlock>> CODEC = BlockConfig.codec(b -> b instanceof TFCFTallGrassBlock t ? t : null, "Must be a " + TFCFTallGrassBlock.class.getSimpleName());

    public PoisonTallPlantFeature(Codec<BlockConfig<TFCFTallGrassBlock>> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockConfig<TFCFTallGrassBlock>> context)
    {
        context.config().block().placeTwoHalves(context.level(), context.origin(), 2, context.random());
        return true;
    }
}
