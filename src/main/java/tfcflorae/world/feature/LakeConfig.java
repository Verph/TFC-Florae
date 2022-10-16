package tfcflorae.world.feature;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record LakeConfig(BlockStateProvider state, BlockStateProvider fluid) implements FeatureConfiguration
{
    public static final Codec<LakeConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        BlockStateProvider.CODEC.fieldOf("state").forGetter(c -> c.state),
        BlockStateProvider.CODEC.fieldOf("fluid").forGetter(c -> c.fluid)
    ).apply(instance, LakeConfig::new));
}