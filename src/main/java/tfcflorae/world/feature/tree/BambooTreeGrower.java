package tfcflorae.world.feature.tree;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;

import java.util.Random;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.blocks.wood.TFCLeavesBlock;
import net.dries007.tfc.common.blocks.wood.Wood;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.wood.BambooLogBlock;
import tfcflorae.common.blocks.wood.TFCFWood;
import tfcflorae.world.feature.TFCFFeatures;

@SuppressWarnings("deprecation")
public class BambooTreeGrower extends AbstractTreeGrower
{
    public TFCFWood bamboo;

    public BambooTreeGrower(TFCFWood bamboo)
    {
        this.bamboo = bamboo;
    }

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean bees)
    {
        return TFCFFeatures.config("tree/grower/bamboo", TFCFFeatures.VANILLA_BAMBOO_TREE.get(),
            new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(TFCFBlocks.VANILLA_BAMBOO_LOGS.get().defaultBlockState().setValue(BambooLogBlock.NATURAL, true)),
                new StraightTrunkPlacer(12, 9, 0), 
                BlockStateProvider.simple(TFCFBlocks.WOODS.get(bamboo).get(Wood.BlockType.LEAVES).get().defaultBlockState().setValue(TFCLeavesBlock.PERSISTENT, false)),
                new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), ConstantInt.of(5), 150), 
                new TwoLayersFeatureSize(1, 0, 1)).decorators(ImmutableList.of(BambooLeavesDecorator.INSTANCE)).ignoreVines().dirt(BlockStateProvider.simple(TFCBlocks.SOIL.get(SoilBlockType.ROOTED_DIRT).get(SoilBlockType.Variant.LOAM).get().defaultBlockState())).build());
    }
}