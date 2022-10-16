package tfcflorae.world.feature.tree.mangrove;

import com.mojang.serialization.Codec;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.plant.Plant;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import tfcflorae.world.feature.TFCFFeatures;

public class WeightedLeaveVineDecorator extends TreeDecorator
{
    public static final Codec<WeightedLeaveVineDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(WeightedLeaveVineDecorator::new, decorator -> {
        return decorator.probability;
    }).codec();
    private final float probability;

    public WeightedLeaveVineDecorator(float probability)
    {
        this.probability = probability;
    }

    @Override
    public void place(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> trunkPositions, List<BlockPos> foliagePositions)
    {
        foliagePositions.forEach(pos -> {
            if (random.nextFloat() < this.probability && Feature.isAir(level, pos.west())) addHangingVine(level, pos.west(), VineBlock.EAST, replacer);
            if (random.nextFloat() < this.probability && Feature.isAir(level, pos.east())) addHangingVine(level, pos.east(), VineBlock.WEST, replacer);
            if (random.nextFloat() < this.probability && Feature.isAir(level, pos.north())) addHangingVine(level, pos.north(), VineBlock.SOUTH, replacer);
            if (random.nextFloat() < this.probability && Feature.isAir(level, pos.south())) addHangingVine(level, pos.south(), VineBlock.NORTH, replacer);
        });
    }

    private static void addHangingVine(LevelSimulatedReader level, BlockPos pos, BooleanProperty property, BiConsumer<BlockPos, BlockState> replacer)
    {
        placeVine(replacer, pos, property);
        pos = pos.below();

        for (int i = 4; Feature.isAir(level, pos) && i > 0; --i)
        {
            placeVine(replacer, pos, property);
            pos = pos.below();
        }
    }

    @Override
    protected TreeDecoratorType<?> type()
    {
        return TFCFFeatures.WEIGHTED_LEAVE_VINE.get();
    }

    protected static void placeVine(BiConsumer<BlockPos, BlockState> blockSetter, BlockPos pos, BooleanProperty sideProperty)
    {
        blockSetter.accept(pos, TFCBlocks.PLANTS.get(Plant.JUNGLE_VINES).get().defaultBlockState().setValue(sideProperty, Boolean.valueOf(true)));
    }
}