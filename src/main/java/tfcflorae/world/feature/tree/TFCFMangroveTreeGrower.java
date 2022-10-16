package tfcflorae.world.feature.tree;

import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.world.feature.tree.TreePlacementConfig;

import tfcflorae.common.TFCFTags;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.wood.*;
import tfcflorae.world.feature.TFCFFeatures;
import tfcflorae.world.feature.tree.mangrove.*;

@SuppressWarnings("deprecation")
public class TFCFMangroveTreeGrower extends AbstractTreeGrower
{
    private final float tallChance;
    private TFCFWood wood;
    private RootedTreeConfig config;

    public TFCFMangroveTreeGrower(TFCFWood wood, float tallChance)
    {
        this.tallChance = tallChance;
        this.wood = wood;
    }

    /**
     * TODO: make soil variant, where used, get the local variant in the world, instead of always loam
     */ 
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean bees)
    {
        if (random.nextFloat() < this.tallChance)
        {
            return TFCFFeatures.config("tree/" + wood.getSerializedName().toLowerCase(Locale.ROOT) + "_tall", TFCFFeatures.VANILLA_MANGROVE_TREE.get(),
                new RootedTreeConfig.TreeConfigurationBuilder(
                    BlockStateProvider.simple(TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.LOG).get().defaultBlockState()),
                new UpwardBranchingTrunk(4, 1, 9,
                    UniformInt.of(1, 6), 0.5F,
                    UniformInt.of(0, 1),
                    Registry.BLOCK.getOrCreateTag(TFCFTags.Blocks.MANGROVE_LOGS_CAN_GROW_THROUGH)),
                    BlockStateProvider.simple(TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.LEAVES).get().defaultBlockState().setValue(TFCFMangroveLeavesBlock.PERSISTENT, false).setValue(TFCFMangroveLeavesBlock.LIFECYCLE, Lifecycle.HEALTHY)),
                new RandomSpreadFoliagePlacer(ConstantInt.of(3), 
                    ConstantInt.of(0), 
                    ConstantInt.of(2), 70),
                new MangroveRootPlacer(UniformInt.of(3, 7), 
                    BlockStateProvider.simple(TFCFBlocks.MANGROVE_ROOTS.get(wood).get().defaultBlockState()), 
                    Optional.of(new AboveRootPlacement(BlockStateProvider.simple(Blocks.MOSS_CARPET), 0.5F)), 
                new MangroveRootPlacement(Registry.BLOCK.getOrCreateTag(TFCFTags.Blocks.MANGROVE_ROOTS_CAN_GROW_THROUGH), 
                    HolderSet.direct(Block::builtInRegistryHolder, TFCBlocks.SOIL.get(SoilBlockType.MUD).get(SoilBlockType.Variant.LOAM).get(), TFCFBlocks.TFC_MUDDY_MANGROVE_ROOTS.get(wood).get(SoilBlockType.Variant.LOAM).get()), 
                    BlockStateProvider.simple(TFCFBlocks.MANGROVE_ROOTS.get(wood).get()), 8, 15, 0.2F)),
                new TwoLayersFeatureSize(3, 0, 2),
                2, 
                new TreePlacementConfig(1, 14, true, true)
            ).decorators(List.of(
                new WeightedLeaveVineDecorator(0.125F),
                new AttachedToLeavesDecorator(0.14F, 1, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.SAPLING).get().defaultBlockState().setValue(TFCFMangrovePropaguleBlock.HANGING, true)), TFCFMangrovePropaguleBlock.AGE, UniformInt.of(0, 4)), 2, List.of(Direction.DOWN)),
                new BeehiveDecorator(0F)
            )).ignoreVines().build());
        }
        else
        {
            return TFCFFeatures.config("tree/" + wood.getSerializedName().toLowerCase(Locale.ROOT), TFCFFeatures.VANILLA_MANGROVE_TREE.get(),
                new RootedTreeConfig.TreeConfigurationBuilder(
                    BlockStateProvider.simple(TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.LOG).get().defaultBlockState()),
                new UpwardBranchingTrunk(2, 1, 4,
                    UniformInt.of(1, 4), 0.5F, 
                    UniformInt.of(0, 1), 
                    Registry.BLOCK.getOrCreateTag(TFCFTags.Blocks.MANGROVE_LOGS_CAN_GROW_THROUGH)),
                    BlockStateProvider.simple(TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.LEAVES).get().defaultBlockState().setValue(TFCFMangroveLeavesBlock.PERSISTENT, false).setValue(TFCFMangroveLeavesBlock.LIFECYCLE, Lifecycle.HEALTHY)),
                new RandomSpreadFoliagePlacer(ConstantInt.of(3), 
                    ConstantInt.of(0), 
                    ConstantInt.of(2), 70),
                new MangroveRootPlacer(UniformInt.of(1, 3), 
                    BlockStateProvider.simple(TFCFBlocks.MANGROVE_ROOTS.get(wood).get().defaultBlockState()), 
                    Optional.of(new AboveRootPlacement(BlockStateProvider.simple(Blocks.MOSS_CARPET), 0.5F)), 
                new MangroveRootPlacement(Registry.BLOCK.getOrCreateTag(TFCFTags.Blocks.MANGROVE_ROOTS_CAN_GROW_THROUGH), 
                    HolderSet.direct(Block::builtInRegistryHolder, TFCBlocks.SOIL.get(SoilBlockType.MUD).get(SoilBlockType.Variant.LOAM).get(), TFCFBlocks.TFC_MUDDY_MANGROVE_ROOTS.get(wood).get(SoilBlockType.Variant.LOAM).get()), 
                    BlockStateProvider.simple(TFCFBlocks.TFC_MUDDY_MANGROVE_ROOTS.get(wood).get(SoilBlockType.Variant.LOAM).get().defaultBlockState()), 8, 15, 0.2F)),
                new TwoLayersFeatureSize(2, 0, 2),
                2, 
                new TreePlacementConfig(1, 14, true, true)
            ).decorators(List.of(
                new WeightedLeaveVineDecorator(0.125F),
                new AttachedToLeavesDecorator(0.14F, 1, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.SAPLING).get().defaultBlockState().setValue(TFCFMangrovePropaguleBlock.HANGING, true)), TFCFMangrovePropaguleBlock.AGE, UniformInt.of(0, 4)), 2, List.of(Direction.DOWN)),
                new BeehiveDecorator(0F)
            )).ignoreVines().build());
        }
    }
}