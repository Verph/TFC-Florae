package tfcflorae.world.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;

import net.dries007.tfc.common.blocks.GroundcoverBlock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.TFCChunkGenerator;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.surface.SoilSurfaceState;

import tfcflorae.Config;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.MossGrowingBoulderBlock;
import tfcflorae.common.blocks.rock.MossSpreadingBoulderBlock;
import tfcflorae.common.blocks.soil.Colors;
import tfcflorae.common.blocks.soil.SandLayerBlock;
import tfcflorae.util.TFCFHelpers;

import static net.dries007.tfc.world.TFCChunkGenerator.SEA_LEVEL_Y;

public class SandLayerFeature extends Feature<NoneFeatureConfiguration>
{
    public SandLayerFeature(Codec<NoneFeatureConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        if (!Config.COMMON.enableSandLayers.get()) return false; // Stop early.

        boolean placedAny = false;
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final Random random = context.random();

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int i = 0; i < 16; ++i)
        {
            for (int j = 0; j < 16; ++j)
            {
                int x = pos.getX() + i;
                int z = pos.getZ() + j;
                int y = level.getHeight(Heightmap.Types.OCEAN_FLOOR, x, z);
                mutablePos.set(x, y, z);

                if (context.chunkGenerator() instanceof TFCChunkGenerator chunkGen && y >= SEA_LEVEL_Y - 1 && !(level.getBlockState(mutablePos).getBlock() instanceof SandLayerBlock))
                {
                    Colors sandColor = TFCFHelpers.getSandColorTFCF(level, mutablePos, Config.COMMON.toggleCheapSandColourCalculations.get());
                    BlockState sandLayer = TFCFBlocks.SAND_LAYERS.get(sandColor).get().defaultBlockState();

                    for (Direction facing : Direction.Plane.HORIZONTAL)
                    {
                        if (level.getBlockState(mutablePos.below()).getMaterial().isSolid() && (level.isEmptyBlock(mutablePos.above()) || level.getBlockState(mutablePos.above()).getMaterial().isLiquid() || DripstoneUtils.isEmptyOrWater(level.getBlockState(mutablePos.above()))) && canReplace(level, mutablePos, random, sandLayer))
                        {
                            final boolean isBelowSeaLevel = y < SEA_LEVEL_Y; // Y 63 is right above sea level (in air)
                            final boolean shouldWaterLog = isBelowSeaLevel || level.getBlockState(mutablePos).getMaterial().isLiquid();
                            final boolean canPlaceInLiquidIfBelowSeaLevel = isBelowSeaLevel ? true : !level.getBlockState(mutablePos).getMaterial().isLiquid();
                            final boolean hasSturdyFaceNearby = isBelowSeaLevel ? true : level.getBlockState(mutablePos.relative(facing).below()).isFaceSturdy(level, mutablePos.relative(facing).below(), Direction.UP);

                            if (hasNearbySandOrIsDryEnough(level, mutablePos, random, false) && hasSturdyFaceNearby && canPlaceInLiquidIfBelowSeaLevel)
                            {
                                int sandLayerHeight = sandLayerHeight(chunkGen, mutablePos, random, false);
                                if (sandLayerHeight > 0)
                                {
                                    sandLayer = FluidHelpers.fillWithFluid(sandLayer, level.getBlockState(mutablePos).getFluidState().getType());
                                    SandLayerBlock.placeSandPileStatic(level, sandLayer, mutablePos, level.getBlockState(mutablePos), sandLayerHeight, false, shouldWaterLog);
                                    placedAny = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return placedAny;
    }

    public static int sandLayerHeight(TFCChunkGenerator chunkGen, BlockPos inputPos, Random random, boolean isShort)
    {
        BlockPos pos = new BlockPos(inputPos.getX(), inputPos.getY() - 1, inputPos.getZ());
        int y1 = pos.getY();
        ChunkPos chunkPos = new ChunkPos(pos);
        double actualHeight = chunkGen.createHeightFillerForChunk(chunkPos).sampleHeight(pos.getX(), pos.getZ());
        double actualHeightToInt = (double) Mth.floor(actualHeight);

        if (actualHeightToInt < y1 && y1 > 62) return 0;

        int sandLayerHeight = 0;
        if (isShort)
        {
            double adjustedSandLayerHeight = ((actualHeight - actualHeightToInt) * 8) + (random.nextGaussian() * 0.2F);
            sandLayerHeight = Mth.clamp(Mth.floor((Math.pow(0.75D, -adjustedSandLayerHeight * 1.125D) - 2.5D)), 0, 7);
        }
        else
        {
            double adjustedSandLayerHeight = ((actualHeight - actualHeightToInt) * 8) + (random.nextGaussian() * 0.2F);
            sandLayerHeight = Mth.clamp(Mth.floor((Math.pow(0.75D, -adjustedSandLayerHeight * 1.1D) - 1D)), 0, 7);
        }
        return y1 > 62 ? sandLayerHeight - 1 : sandLayerHeight;
    }

    public boolean hasNearbySandOrIsDryEnough(WorldGenLevel level, BlockPos pos, Random random, boolean isPicky)
    {
        boolean isSandy = false;
        if (isPicky)
        {
            for (Direction direction : TFCFHelpers.DIRECTIONS_HORIZONTAL)
            {
                isSandy = Helpers.isBlock(level.getBlockState(pos.relative(direction)).getBlock(), BlockTags.SAND);
                return isSandy || (isSandy && properRainfall(level, pos, random));
            }
        }
        else
        {
            for (Direction direction : TFCFHelpers.DIRECTIONS)
            {
                isSandy = Helpers.isBlock(level.getBlockState(pos.relative(direction)).getBlock(), BlockTags.SAND);
                return isSandy || (isSandy && properRainfall(level, pos, random));
            }
        }
        return false;
    }

    public boolean canReplace(WorldGenLevel level, BlockPos pos, Random random, BlockState sandLayer)
    {
        BlockState state = level.getBlockState(pos);

        return SandLayerBlock.canPlaceSandPileStatic(level, pos, state, sandLayer) || (FluidHelpers.isAirOrEmptyFluid(state) || EnvironmentHelpers.isWorldgenReplaceable(level, pos) || state.getBlock() instanceof GroundcoverBlock || state.getBlock() instanceof MossGrowingBoulderBlock || state.getBlock() instanceof MossSpreadingBoulderBlock || state.getMaterial() == Material.PLANT || state.getMaterial() == Material.REPLACEABLE_PLANT);
    }

    public boolean isSmallerThanNew(WorldGenLevel level, BlockPos pos, int height)
    {
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof SandLayerBlock && state.getValue(SandLayerBlock.LAYERS) >= height;
    }

	public boolean properRainfall(WorldGenLevel level, BlockPos pos, Random random)
	{
        final ChunkDataProvider provider = ChunkDataProvider.get(level);
        final ChunkData data = provider.get(level, pos);

        final float rainfall = data.getRainfall(pos);

        final float noise = SoilSurfaceState.PATCH_NOISE.noise(pos.getX(), pos.getZ());
        final float noiseGauss = noise + (2 * (float) random.nextGaussian());
        final float noiseRainfall = rainfall + (10 * (float) random.nextGaussian());

        return noiseRainfall <= 80 ? (noiseRainfall <= 50 || noiseGauss > 0.2F) : false;
	}

	public int maxHeightSmallerThanPrevious(WorldGenLevel level, BlockPos pos, Direction facing, int distance, int inputHeight, int maxHeight, int minHeight)
	{
        BlockState state = level.getBlockState(pos.relative(facing));
        if (distance > 3)
        {
            return 1;
        }
        else if (distance > 0 && state.getBlock() instanceof SandLayerBlock && state.getValue(SandLayerBlock.LAYERS) >= inputHeight)
        {
            int heightDifference = state.getValue(SandLayerBlock.LAYERS) - inputHeight;
            return Mth.clamp(inputHeight - (heightDifference + 1), minHeight, maxHeight);
        }
        return inputHeight;
	}

	/*public SandBlockType getSandColor(WorldGenLevel level, BlockPos pos, Random random)
	{
        final ChunkDataProvider provider = ChunkDataProvider.get(level);
        final ChunkData data = provider.get(level, pos);

		RockSettings rockData = data.getRockData().getRock(pos);

		for (SandBlockType sandColors : SandBlockType.values())
		{
            for (Direction direction : TFCFHelpers.DIRECTIONS)
            {
                if (level.getBlockState(pos.relative(direction)).getBlock().getRegistryName().toString().toLowerCase(Locale.ROOT).contains(sandColors.toString().toLowerCase(Locale.ROOT)))
                {
                    return sandColors;
                }
            }
            if (rockData.sand() != null && rockData.sand() == TFCBlocks.SAND.get(sandColors).get())
            {
                return sandColors;
            }
		}
		return SandBlockType.YELLOW;
	}*/

    public BlockPos getNearestSandBlock(WorldGenLevel level, BlockPos sourcePos, int radius)
    {      
        BlockPos closestPos = null;
        BlockPos checkPos = sourcePos;

        for (int x = sourcePos.getX() - radius; x < sourcePos.getX() + radius; x++)
        {
            for (int z = sourcePos.getZ() - radius; z < sourcePos.getZ() + radius; z++)
            {
                checkPos = new BlockPos(x, sourcePos.getY(), z);
                if (level.getBlockState(checkPos).getBlock() instanceof SandBlock && !(level.getBlockState(checkPos).getBlock() instanceof SandLayerBlock))
                {
                    // check if it is closer than any previously found position
                    if (closestPos == null || 
                            TFCFHelpers.distanceToSqr(sourcePos, sourcePos.getX() - checkPos.getX(), 
                                                sourcePos.getY() - checkPos.getY(),
                                                sourcePos.getZ() - checkPos.getZ())
                            < TFCFHelpers.distanceToSqr(sourcePos, sourcePos.getX() - closestPos.getX(), 
                                                sourcePos.getY() - closestPos.getY(),
                                                sourcePos.getZ() - closestPos.getZ()))
                    {
                        closestPos = checkPos;
                    }
                }
            }
        }
        return closestPos;
    }
}
