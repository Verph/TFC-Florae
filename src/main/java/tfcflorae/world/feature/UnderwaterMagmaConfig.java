package tfcflorae.world.feature;

import java.util.List;
import java.util.Map;

import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dries007.tfc.world.Codecs;
import org.jetbrains.annotations.Nullable;

public record UnderwaterMagmaConfig(Map<Block, List<BlockState>> states, int floorSearchRange, int placementRadiusAroundFloor, float placementProbabilityPerValidPosition) implements FeatureConfiguration
{
    public static final Codec<UnderwaterMagmaConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codecs.mapListCodec(Codecs.recordPairCodec(
            Codecs.BLOCK, "rock",
            Codecs.BLOCK_STATE.listOf(), "blocks"
        )).fieldOf("states").forGetter(c -> c.states),
        Codec.intRange(0, 512).fieldOf("floorSearchRange").forGetter(c -> c.floorSearchRange),
        Codec.intRange(0, 64).fieldOf("placementRadiusAroundFloor").forGetter(c -> c.placementRadiusAroundFloor),
        Codec.floatRange(0.0F, 1.0F).fieldOf("placementProbabilityPerValidPosition").forGetter(c -> c.placementProbabilityPerValidPosition)
    ).apply(instance, UnderwaterMagmaConfig::new));

    @Nullable
    public List<BlockState> getStates(Block block)
    {
        return states.get(block);
    }
}