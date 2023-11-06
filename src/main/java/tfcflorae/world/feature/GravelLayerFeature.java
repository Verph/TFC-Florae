package tfcflorae.world.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;

import net.dries007.tfc.common.blocks.GroundcoverBlock;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryRock;
import net.dries007.tfc.world.TFCChunkGenerator;
import net.dries007.tfc.world.biome.BiomeExtension;
import net.dries007.tfc.world.biome.TFCBiomes;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.surface.SoilSurfaceState;

import tfcflorae.Config;
import tfcflorae.common.TFCFTags;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.MossGrowingBoulderBlock;
import tfcflorae.common.blocks.rock.MossSpreadingBoulderBlock;
import tfcflorae.common.blocks.rock.TFCFRock;
import tfcflorae.common.blocks.soil.Colors;
import tfcflorae.common.blocks.soil.SandLayerBlock;
import tfcflorae.interfaces.TFCBiomesMixinInterface;
import tfcflorae.util.TFCFHelpers;

import static net.dries007.tfc.world.TFCChunkGenerator.SEA_LEVEL_Y;

public class GravelLayerFeature extends Feature<NoneFeatureConfiguration>
{
    public GravelLayerFeature(Codec<NoneFeatureConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        if (!Config.COMMON.enableGravelLayers.get()) return false; // Stop early.

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
                    RegistryRock rock = TFCFHelpers.rockType(level, mutablePos);
                    BlockState sandLayer = null;

                    if (rock instanceof Rock)
                    {
                        sandLayer = TFCFBlocks.ROCK_BLOCKS.get(rock).get(TFCFRock.TFCFBlockType.GRAVEL_LAYER).get().defaultBlockState();
                    }
                    else if (rock instanceof TFCFRock)
                    {
                        sandLayer = TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(TFCFRock.TFCFBlockType.GRAVEL_LAYER).get().defaultBlockState();
                    }
                    if (y <= 62 && level.canSeeSky(mutablePos) && isShoreBiome(level, mutablePos))
                    {
                        sandLayer = TFCFBlocks.SAND_LAYERS.get(Colors.fromMaterialColour(ChunkDataProvider.get(level).get(level, mutablePos).getRockData().getRock(mutablePos).sand().defaultMaterialColor())).get().defaultBlockState();
                    }

                    if (sandLayer != null)
                    {
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
                                    if (sandLayerHeight > 0 || (y < 64 && sandLayerHeight > 2))
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
        }
        return placedAny;
    }

    public boolean isShoreBiome(WorldGenLevel level, BlockPos pos)
    {
        BiomeExtension biome = TFCBiomes.getExtension(level, level.getBiome(pos).value());
        TFCBiomes staticBiomes = new TFCBiomes();
        final BiomeExtension NEAR_SHORE = ((TFCBiomesMixinInterface) (Object) staticBiomes).getStaticNearShore();

        return biome == TFCBiomes.SHORE || biome == NEAR_SHORE;
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
                isSandy = Helpers.isBlock(level.getBlockState(pos.relative(direction)).getBlock(), TFCFTags.Blocks.GRAVEL);
                return isSandy || (isSandy && properRainfall(level, pos, random));
            }
        }
        else
        {
            for (Direction direction : TFCFHelpers.DIRECTIONS)
            {
                isSandy = Helpers.isBlock(level.getBlockState(pos.relative(direction)).getBlock(), TFCFTags.Blocks.GRAVEL);
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
}
