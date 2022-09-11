package tfcflorae.world.feature.tree;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

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
}