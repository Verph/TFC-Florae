package tfcflorae.world.feature.tree;

import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;
import java.util.Optional;

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

    /*public static class Builder
    {
        public final BlockStateProvider trunkProvider;
        private final TrunkPlacer trunkPlacer;
        public final BlockStateProvider foliageProvider;
        private final FoliagePlacer foliagePlacer;
        private final Optional<RootPlacer> rootPlacer;
        private BlockStateProvider dirtProvider;
        private final FeatureSize minimumSize;
        private List<TreeDecorator> decorators = ImmutableList.of();
        private boolean ignoreVines;
        private boolean forceDirt;

        public Builder(BlockStateProvider trunkProvider, TrunkPlacer trunkPlacer, BlockStateProvider foliageProvider, FoliagePlacer foliagePlacer, Optional<RootPlacer> rootPlacer, FeatureSize minimumSize)
        {
            this.trunkProvider = trunkProvider;
            this.trunkPlacer = trunkPlacer;
            this.foliageProvider = foliageProvider;
            this.dirtProvider = BlockStateProvider.simple(Blocks.DIRT);
            this.foliagePlacer = foliagePlacer;
            this.rootPlacer = rootPlacer;
            this.minimumSize = minimumSize;
        }

        public Builder(BlockStateProvider trunkProvider, TrunkPlacer trunkPlacer, BlockStateProvider foliageProvider, FoliagePlacer foliagePlacer, FeatureSize minimumSize)
        {
            this(trunkProvider, trunkPlacer, foliageProvider, foliagePlacer, Optional.empty(), minimumSize);
        }

        public Builder dirtProvider(BlockStateProvider dirtProvider)
        {
            this.dirtProvider = dirtProvider;
            return this;
        }

        public Builder decorators(List<TreeDecorator> decorators)
        {
            this.decorators = decorators;
            return this;
        }

        public Builder ignoreVines()
        {
            this.ignoreVines = true;
            return this;
        }

        public Builder forceDirt()
        {
            this.forceDirt = true;
            return this;
        }

        public RootedTreeConfig build()
        {
            return new RootedTreeConfig(this.trunkProvider, this.trunkPlacer, this.foliageProvider, this.foliagePlacer, this.rootPlacer, this.dirtProvider, this.minimumSize, this.decorators, this.ignoreVines, this.forceDirt);
        }
    }*/
}