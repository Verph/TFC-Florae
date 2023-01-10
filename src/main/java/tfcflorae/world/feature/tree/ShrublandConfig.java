package tfcflorae.world.feature.tree;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.dries007.tfc.util.collections.IWeighted;
import net.dries007.tfc.world.Codecs;
import net.dries007.tfc.world.chunkdata.ForestType;

public record ShrublandConfig(HolderSet<ConfiguredFeature<?, ?>> entries, Map<ForestType, Type> typeMap, boolean useWeirdness) implements FeatureConfiguration
{
    public static final Codec<ShrublandConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ExtraCodecs.nonEmptyHolderSet(ConfiguredFeature.LIST_CODEC).fieldOf("entries").forGetter(c -> c.entries),
        Codec.unboundedMap(ForestType.CODEC, Type.CODEC).fieldOf("types").forGetter(c -> c.typeMap),
        Codec.BOOL.fieldOf("use_weirdness").orElse(true).forGetter(c -> c.useWeirdness)
    ).apply(instance, ShrublandConfig::new));

    public record Type(IntProvider treeCount, IntProvider groundcoverCount, float perChunkChance, IntProvider bushCount, boolean hasSpoilers, boolean allowOldGrowth, boolean enableTrees)
    {
        public static final Codec<Type> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IntProvider.CODEC.fieldOf("tree_count").orElse(UniformInt.of(0, 0)).forGetter(c -> c.treeCount),
            IntProvider.CODEC.fieldOf("groundcover_count").orElse(UniformInt.of(0, 0)).forGetter(c -> c.groundcoverCount),
            Codec.FLOAT.fieldOf("per_chunk_chance").orElse(1f).forGetter(c -> c.perChunkChance),
            IntProvider.CODEC.fieldOf("bush_count").orElse(UniformInt.of(0, 0)).forGetter(c -> c.bushCount),
            Codec.BOOL.fieldOf("has_spoiler_old_growth").orElse(false).forGetter(c -> c.hasSpoilers),
            Codec.BOOL.fieldOf("allows_old_growth").orElse(false).forGetter(c -> c.allowOldGrowth),
            Codec.BOOL.fieldOf("enable_trees").orElse(false).forGetter(c -> c.enableTrees)
        ).apply(instance, Type::new));
    }
}
