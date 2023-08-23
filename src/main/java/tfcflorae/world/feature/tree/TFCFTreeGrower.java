package tfcflorae.world.feature.tree;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.common.Tags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateModel;
import net.dries007.tfc.util.registry.RegistryWood;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.chunkdata.ForestType;
import net.dries007.tfc.world.feature.tree.TFCTreeGrower;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.wood.TFCFWood;

public class TFCFTreeGrower extends TFCTreeGrower
{
    public final RegistryWood wood;

    public TFCFTreeGrower(RegistryWood wood, ResourceLocation normalTree, ResourceLocation oldGrowthFeatureFactory)
    {
        super(normalTree, oldGrowthFeatureFactory);
        this.wood = wood;
    }

    @Override
    public boolean growTree(ServerLevel level, ChunkGenerator generator, BlockPos originPos, BlockState state, Random random)
    {
        int x = originPos.getX();
        int z = originPos.getZ();
        int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
        BlockPos pos = new BlockPos(x, y, z);

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

        /*if (!mayPlaceOn(level.getBlockState(pos.below())))
        {
            pos = pos.below().below();
        }*/
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);

        if (feature.place(level, generator, random, pos))
        {
            level.setBlock(pos, wood == TFCFWood.WHITE_CEDAR ? TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(Wood.BlockType.WOOD).get().defaultBlockState() : TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.WOOD).get().defaultBlockState(), Block.UPDATE_ALL);
            return true;
        }
        else
        {
            level.setBlock(pos, state, 4);
            return false;
        }
    }

    public static boolean mayPlaceOn(BlockState state)
    {
        return state.is(BlockTags.DIRT) || state.is(Blocks.FARMLAND) || Helpers.isBlock(state.getBlock(), TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(state.getBlock(), TFCTags.Blocks.TREE_GROWS_ON) || Helpers.isBlock(state.getBlock(), BlockTags.SAND) || Helpers.isBlock(state.getBlock(), Tags.Blocks.GRAVEL);
    }
}