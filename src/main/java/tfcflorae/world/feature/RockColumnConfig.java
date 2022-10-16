package tfcflorae.world.feature;

import java.util.List;
import java.util.Map;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dries007.tfc.world.Codecs;
import org.jetbrains.annotations.Nullable;

public record RockColumnConfig(Map<Block, List<BlockState>> states, IntProvider reach, IntProvider height) implements FeatureConfiguration
{
    public static final Codec<RockColumnConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codecs.mapListCodec(Codecs.recordPairCodec(
            Codecs.BLOCK, "rock",
            Codecs.BLOCK_STATE.listOf(), "blocks"
        )).fieldOf("states").forGetter(c -> c.states),
        IntProvider.codec(0, 3).fieldOf("reach").forGetter(c -> c.reach),
        IntProvider.codec(1, 10).fieldOf("height").forGetter(c -> c.height)
    ).apply(instance, RockColumnConfig::new));

    @Nullable
    public List<BlockState> getStates(Block block)
    {
        return states.get(block);
    }
}