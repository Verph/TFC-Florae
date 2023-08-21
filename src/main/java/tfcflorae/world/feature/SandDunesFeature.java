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
import net.minecraft.world.level.block.SoundType;
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
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;

import tfcflorae.Config;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.MossGrowingBoulderBlock;
import tfcflorae.common.blocks.rock.MossSpreadingBoulderBlock;
import tfcflorae.common.blocks.soil.SandLayerBlock;
import tfcflorae.util.TFCFHelpers;

import static net.dries007.tfc.world.TFCChunkGenerator.SEA_LEVEL_Y;

public class SandDunesFeature extends Feature<NoneFeatureConfiguration>
{
    public SandDunesFeature(Codec<NoneFeatureConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        if (!Config.COMMON.enableDunes.get()) return false; // Stop early.

        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final Random random = context.random();
        final ChunkDataProvider provider = ChunkDataProvider.get(level);

        if (provider.get(level, pos).getRainfall(pos) > 75) return false; // Stop early -- only in dry areas.
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int i = 0; i < 16; ++i)
        {
            for (int j = 0; j < 16; ++j)
            {
                int x = pos.getX() + i;
                int z = pos.getZ() + j;
                int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

                int duneHeightNoise = 0;
                float duneLimiter = 0f;
                mutablePos.set(x, y, z);

                final ChunkData data = provider.get(level, mutablePos);
                float rainfall = data.getRainfall(mutablePos) - 500f;

                if (context.chunkGenerator() instanceof TFCChunkGenerator chunkGen && rainfall < 75f && !FluidHelpers.isAirOrEmptyFluid(level.getBlockState(mutablePos.below())))
                {
                    ChunkPos chunkPos = new ChunkPos(mutablePos);
                    double actualHeight = chunkGen.createHeightFillerForChunk(chunkPos).sampleHeight(x, z);

                    if (rainfall < 72f)
                    {
                        float heightModifier = Mth.clamp(((float) actualHeight * 0.1f) - 6.5f, 0, 1); // If y < 65, then dune height should be 0, in order to avoid abrupt cuts/walls, but gradually increase the higher the terrain is.
                        duneLimiter = Mth.clamp((float) Math.pow(0.99f, rainfall - 128) - 1.8f, 0f, 1f);
                        duneLimiter = heightModifier > 0 ? duneLimiter * heightModifier : 0;
                        duneHeightNoise = Mth.clamp(Mth.floor(dunes(level.getSeed(), SEA_LEVEL_Y, y).noise(mutablePos.getX(), mutablePos.getZ()) * duneLimiter), 0, y + 12);
                    }

                    if (duneHeightNoise > 0)
                    {
                        for (int yDune = y; yDune <= duneHeightNoise; ++yDune)
                        {
                            mutablePos.set(x, yDune, z);
                            if (canReplace(level, mutablePos, random))
                            {
                                for (Direction facing : Direction.Plane.HORIZONTAL)
                                {
                                    BlockPos posOffset = mutablePos.relative(facing).below();
                                    if (level.getBlockState(posOffset).isFaceSturdy(level, posOffset, Direction.UP))
                                    {
                                        level.setBlock(mutablePos, TFCFHelpers.getSandBlock(level, mutablePos, Config.COMMON.toggleCheapSandColourCalculations.get()).defaultBlockState(), 2);

                                        mutablePos.set(x, mutablePos.getY(), z).above();
                                        if (hasNearbySandOrIsDryEnough(level, mutablePos, random, true) && canReplace(level, mutablePos, random) && !(level.getBlockState(mutablePos).getBlock() instanceof SandLayerBlock))
                                        {
                                            int layers = Mth.nextInt(random, 1, 5);
                                            SandLayerBlock.placeSandPileStatic(level, TFCFBlocks.SAND_LAYERS.get(TFCFHelpers.getSandColorTFCF(level, mutablePos, Config.COMMON.toggleCheapSandColourCalculations.get())).get().defaultBlockState(), mutablePos, level.getBlockState(mutablePos), layers, false, false);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean hasNearbySandOrIsDryEnough(WorldGenLevel level, BlockPos pos, Random random, boolean isPicky)
    {
        for (Direction direction : TFCFHelpers.DIRECTIONS)
        {
            boolean isSandy = false;
            if (isPicky)
            {
                isSandy = Helpers.isBlock(level.getBlockState(pos.relative(direction)).getBlock(), BlockTags.SAND);
            }
            else
            {
                isSandy = level.getBlockState(pos.relative(direction)).getBlock() instanceof SandBlock || (level.getBlockState(pos.relative(direction)).getMaterial() == Material.SAND && level.getBlockState(pos.relative(direction)).getSoundType() == SoundType.SAND);
            }
            return isSandy || (isSandy && properRainfall(level, pos, random));
        }
        return false;
    }

    public static Noise2D dunes(long seed, int minHeight, int maxHeight) // TODO: make it look better
    {
        final OpenSimplex2D warp = new OpenSimplex2D(seed).octaves(4).spread(0.2f).scaled(-100f, 100f, minHeight, maxHeight);

        final Noise2D baseNoise = new OpenSimplex2D(seed).octaves(3).spread(0.25f).scaled(-3f, 3f).clamped(0, 1);

        final Noise2D starDunes = new OpenSimplex2D(seed)
            .octaves(6) //4
            .spread(0.25f) //0.14f
            .add(new OpenSimplex2D(seed + 1)
                .octaves(2)
                .spread(0.25f)
                .scaled(-0.7f, 0.7f)
                .ridged()
            )
            .map(x -> {
                final float x0 = 0.125f * (x + 1) * (x + 1) * (x + 1);
                return minHeight + (maxHeight - SEA_LEVEL_Y) * x0;
            });

        final Noise2D randomDunes = new OpenSimplex2D(seed)
            .octaves(6) //4
            .spread(0.25f) //0.14f
            .warped(warp)
            .ridged()
            .map(x -> {
                final float x0 = 0.125f * (x + 1) * (x + 1.25f) * (x + 1);
                return minHeight + (maxHeight - SEA_LEVEL_Y) * x0;
            });

        return (x, z) -> {
            float baseHeight = baseNoise.noise(x, z);
            float starDuneHeight = starDunes.noise(x, z);
            float randomDunesHeight = randomDunes.noise(x, z);

            return (randomDunesHeight * (1 - baseHeight)) + (starDuneHeight * baseHeight);
        };
    }

    /*public boolean hasNearbySandOrIsDryEnough(WorldGenLevel level, BlockPos pos, Random random)
    {
        for (Direction direction : TFCFHelpers.DIRECTIONS)
        {
            boolean isSandy = level.getBlockState(pos.relative(direction)).getBlock() instanceof SandBlock || level.getBlockState(pos.relative(direction)).getMaterial() == Material.SAND || level.getBlockState(pos.relative(direction)).getSoundType() == SoundType.SAND;
            return isSandy || (isSandy && properRainfall(level, pos, random));
        }
        return false;
    }*/

    public boolean canReplace(WorldGenLevel level, BlockPos pos, Random random)
    {
        return EnvironmentHelpers.isWorldgenReplaceable(level, pos) || level.getBlockState(pos).getBlock() instanceof GroundcoverBlock || level.getBlockState(pos).getBlock() instanceof MossGrowingBoulderBlock || level.getBlockState(pos).getBlock() instanceof MossSpreadingBoulderBlock || level.getBlockState(pos).getMaterial() == Material.PLANT || level.getBlockState(pos).getMaterial() == Material.REPLACEABLE_PLANT;
    }

    public boolean canReplace(WorldGenLevel level, BlockPos pos, Random random, BlockState sandLayer)
    {
        BlockState state = level.getBlockState(pos);
        return !level.getBlockState(pos).getMaterial().isLiquid() && (SandLayerBlock.canPlaceSandPileStatic(level, pos, state, sandLayer) || (EnvironmentHelpers.isWorldgenReplaceable(level, pos) || state.getBlock() instanceof GroundcoverBlock || state.getBlock() instanceof MossGrowingBoulderBlock || state.getBlock() instanceof MossSpreadingBoulderBlock || state.getMaterial() == Material.PLANT || state.getMaterial() == Material.REPLACEABLE_PLANT));
    }

	public boolean properRainfall(WorldGenLevel level, BlockPos pos, Random random)
	{
        final ChunkDataProvider provider = ChunkDataProvider.get(level);
        final ChunkData data = provider.get(level, pos);

        final float rainfall = data.getRainfall(pos);

        //final float noise = SoilSurfaceState.PATCH_NOISE.noise(pos.getX(), pos.getZ());
        //final float noiseGauss = noise + (2 * (float) random.nextGaussian());
        final float noiseRainfall = rainfall + (10 * (float) random.nextGaussian());

        return noiseRainfall <= 80;
        //return noiseRainfall <= 80 ? (noiseRainfall <= 50 || noiseGauss > 0.2F) : false;
	}

	/*public BlockState getSandBlock(WorldGenLevel level, BlockPos pos, Random random)
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
                    return TFCBlocks.SAND.get(sandColors).get().defaultBlockState();
                }
            }
            if (rockData.sand() != null && rockData.sand() == TFCBlocks.SAND.get(sandColors).get())
            {
                return TFCBlocks.SAND.get(sandColors).get().defaultBlockState();
            }
		}
		return TFCBlocks.SAND.get(SandBlockType.YELLOW).get().defaultBlockState();
	}*/
}
