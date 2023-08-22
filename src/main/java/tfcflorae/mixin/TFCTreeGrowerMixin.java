package tfcflorae.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import net.dries007.tfc.util.climate.ClimateModel;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.chunkdata.ForestType;
import net.dries007.tfc.world.feature.tree.TFCTreeGrower;

@Mixin(TFCTreeGrower.class)
public abstract class TFCTreeGrowerMixin extends AbstractTreeGrower
{
    @Shadow private final ResourceLocation normalTree;
    @Shadow private final ResourceLocation oldGrowthTree;

    public TFCTreeGrowerMixin(ResourceLocation normalTree, ResourceLocation oldGrowthFeatureFactory)
    {
        this.normalTree = normalTree;
        this.oldGrowthTree = oldGrowthFeatureFactory;
    }

    @Shadow
    public ConfiguredFeature<?, ?> getNormalFeature(Registry<ConfiguredFeature<?, ?>> registry)
    {
        return registry.getOptional(normalTree).orElseThrow(() -> new IllegalStateException("Missing tree feature: " + normalTree));
    }

    @Shadow
    public ConfiguredFeature<?, ?> getOldGrowthFeature(Registry<ConfiguredFeature<?, ?>> registry)
    {
        return registry.getOptional(oldGrowthTree).orElseGet(() -> getNormalFeature(registry));
    }

    @Overwrite(remap = false)
    @Override
    public boolean growTree(ServerLevel level, ChunkGenerator generator, BlockPos pos, BlockState state, Random random)
    {
        ConfiguredFeature<?, ?> feature = null;
        final ConfiguredFeature<?, ?> smallTree = getNormalFeature(level.registryAccess().registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY));
        final ConfiguredFeature<?, ?> largeTree = getOldGrowthFeature(level.registryAccess().registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY));

        final ChunkDataProvider provider = ChunkDataProvider.get(level);
        final ChunkData data = provider.get(level, pos);

        final float rainfall = data.getRainfall(pos);
        final float rainfallInverted = (ClimateModel.MAXIMUM_RAINFALL - rainfall) + 10F;

        final float actualForestDensity = data.getForestDensity();
        final float forestDensity = actualForestDensity == 0 ? 0.001F : actualForestDensity; // Cannot divide by 0.

        final ForestType forestType = data.getForestType();

        if (forestType != ForestType.OLD_GROWTH)
        {
            feature = forestType != ForestType.NONE && random.nextFloat((rainfallInverted * 1.2F) / forestDensity) == 0 ? largeTree : smallTree;
        }
        else
        {
            feature = random.nextFloat(rainfallInverted * 1.2F) == 0 ? largeTree : smallTree;
        }

        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);

        if (feature.place(level, generator, random, pos))
        {
            return true;
        }
        else
        {
            level.setBlock(pos, state, 4);
            return false;
        }
    }
}
