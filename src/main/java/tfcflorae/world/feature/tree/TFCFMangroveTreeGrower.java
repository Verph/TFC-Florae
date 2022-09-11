package tfcflorae.world.feature.tree;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GlowLichenConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
import net.minecraft.world.level.material.Fluids;
import tfcflorae.TFCFlorae;
import tfcflorae.world.feature.tree.mangrove.MangroveTreeFeature;

import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.List;
import java.util.Optional;

public class TFCFMangroveTreeGrower extends AbstractTreeGrower
{
    private final float tallChance;

    public TFCFMangroveTreeGrower(float tallChance)
    {
        this.tallChance = tallChance;
    }

    @Override @Nullable
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean bees)
    {
        return null;
        //return random.nextFloat() < this.tallChance ? WBWorldGeneration.TALL_MANGROVE : WBWorldGeneration.MANGROVE;
    }

    /*public static final Holder<MangroveTreeFeature> MANGROVE = config("mangrove", new MangroveTreeFeature(DynamicTreeConfig.CODEC), new DynamicTreeConfig.Builder(
        BlockStateProvider.simple(WBBlocks.MANGROVE_LOG.get()),
        new UpwardBranchingTrunk(2, 1, 4, UniformInt.of(1, 4), 0.5F, UniformInt.of(0, 1), Registry.BLOCK.getOrCreateTag(WBBlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
        BlockStateProvider.simple(WBBlocks.MANGROVE_LEAVES.get()),
        new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 70),
        Optional.of(new MangroveRootPlacer(UniformInt.of(1, 3), BlockStateProvider.simple(WBBlocks.MANGROVE_ROOTS.get()), Optional.of(new AboveRootPlacement(BlockStateProvider.simple(Blocks.MOSS_CARPET), 0.5F)), new MangroveRootPlacement(Registry.BLOCK.getOrCreateTag(WBBlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH), HolderSet.direct(Block::builtInRegistryHolder, WBBlocks.MUD.get(), WBBlocks.MUDDY_MANGROVE_ROOTS.get()), BlockStateProvider.simple(WBBlocks.MUDDY_MANGROVE_ROOTS.get()), 8, 15, 0.2F))),
        new TwoLayersFeatureSize(2, 0, 2)
    ).decorators(List.of(
        //new WeightedLeaveVineDecorator(0.125F),
        //new AttachedToLeavesDecorator(0.14F, 1, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(WBBlocks.MANGROVE_PROPAGULE.get().defaultBlockState().setValue(MangrovePropaguleBlock.HANGING, true)), MangrovePropaguleBlock.AGE, UniformInt.of(0, 4)), 2, List.of(Direction.DOWN)),
        new BeehiveDecorator(0.01F)
    )).ignoreVines().build());*/

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> config(String key, F feature, FC configuration) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, TFCFlorae.MOD_ID + ":" + key, new ConfiguredFeature<>(feature, configuration));
    }
}