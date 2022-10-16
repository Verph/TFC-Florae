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

public record RockPillarConfig(Map<Block, List<BlockState>> states) implements FeatureConfiguration
{
    public static final Codec<RockPillarConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codecs.mapListCodec(Codecs.recordPairCodec(
            Codecs.BLOCK, "rock",
            Codecs.BLOCK_STATE.listOf(), "blocks"
        )).fieldOf("states").forGetter(c -> c.states)
    ).apply(instance, RockPillarConfig::new));

    @Nullable
    public List<BlockState> getStates(Block block)
    {
        return states.get(block);
    }
}