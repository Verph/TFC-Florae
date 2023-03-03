package tfcflorae.world.feature;

import java.util.List;
import java.util.Map;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dries007.tfc.world.Codecs;
import org.jetbrains.annotations.Nullable;

public record BoulderConfig(Map<Block, List<BlockState>> states, int sizeMin, int sizeRandom) implements FeatureConfiguration
{
    public static final Codec<BoulderConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codecs.mapListCodec(Codecs.recordPairCodec(
            Codecs.BLOCK, "rock",
            Codecs.BLOCK_STATE.listOf(), "blocks"
        )).fieldOf("states").forGetter(c -> c.states),
        Codec.intRange(0, 512).fieldOf("minimum_size").forGetter(c -> c.sizeMin),
        Codec.intRange(0, 512).fieldOf("extra_random_radius").forGetter(c -> c.sizeRandom)
    ).apply(instance, BoulderConfig::new));

    @Nullable
    public List<BlockState> getStates(Block block)
    {
        return states.get(block);
    }
}