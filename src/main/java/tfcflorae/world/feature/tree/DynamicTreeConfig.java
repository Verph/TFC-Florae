package tfcflorae.world.feature.tree;

import java.util.Optional;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.dries007.tfc.world.Codecs;
import net.dries007.tfc.world.feature.tree.*;

public record DynamicTreeConfig(Optional<TrunkConfig> trunk, int minHeight, int radius, TreePlacementConfig placement, BlockState logState, BlockState woodState, BlockState leavesState) implements FeatureConfiguration
{
    public static final Codec<DynamicTreeConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        TrunkConfig.CODEC.optionalFieldOf("trunk").forGetter(c -> c.trunk),
        Codec.INT.fieldOf("minHeight").forGetter(c -> c.minHeight),
        Codec.INT.fieldOf("radius").forGetter(c -> c.radius),
        TreePlacementConfig.CODEC.fieldOf("placement").forGetter(c -> c.placement),
        Codecs.BLOCK_STATE.fieldOf("logState").forGetter(c -> c.logState),
        Codecs.BLOCK_STATE.fieldOf("woodState").forGetter(c -> c.woodState),
        Codecs.BLOCK_STATE.fieldOf("leavesState").forGetter(c -> c.leavesState)
    ).apply(instance, DynamicTreeConfig::new));
}