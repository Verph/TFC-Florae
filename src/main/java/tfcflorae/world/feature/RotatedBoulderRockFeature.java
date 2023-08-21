package tfcflorae.world.feature;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;

import com.mojang.serialization.Codec;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.settings.RockSettings;

public class RotatedBoulderRockFeature extends Feature<NoneFeatureConfiguration>
{
    public RotatedBoulderRockFeature(Codec<NoneFeatureConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final Random random = context.random();

        final ChunkDataProvider provider = ChunkDataProvider.get(context.chunkGenerator());
        final ChunkData data = provider.get(level, pos);
        final float rainfall = data.getRainfall(pos);
        final float forestDensity = data.getForestDensity();
        final float forestWeirdness = data.getForestWeirdness();

        final Boolean generateMossy = random.nextFloat(Mth.clamp(Mth.abs((rainfall + 1) * forestDensity), 1, Float.MAX_VALUE)) > random.nextFloat(Mth.clamp(Mth.abs((rainfall + 1) * forestWeirdness), 1, Float.MAX_VALUE));

        TFCFRock.TFCFBlockType rockType = TFCFRock.TFCFBlockType.ROCK_PILE;
        if (generateMossy)
        {
            rockType = TFCFRock.TFCFBlockType.MOSSY_ROCK_PILE;
        }

        final BlockState stateAt = level.getBlockState(pos);
        final BlockState rockState = FluidHelpers.fillWithFluid(rockPile(level, pos, rockType), stateAt.getFluidState().getType());

        final Direction direction = Direction.getRandom(random);
        final BlockPos blockPos = pos.relative(direction.getOpposite());
        final BlockState blockState = level.getBlockState(blockPos);

        if (EnvironmentHelpers.isWorldgenReplaceable(stateAt) && rockState != null && blockState.isFaceSturdy(level, blockPos, direction) && Helpers.isBlock(blockState, BlockTags.BASE_STONE_OVERWORLD))
        {
            setBlock(level, pos, rockState.setValue(BlockStateProperties.FACING, direction));
            return true;
        }
        return false;
    }

    public static BlockState rockPile(WorldGenLevel level, BlockPos pos, TFCFRock.TFCFBlockType rockType)
    {
        ChunkDataProvider provider = ChunkDataProvider.get(level);
        RockSettings surfaceRock = provider.get(level, pos).getRockData().getRock(pos);

        Rock rockTFC = null;
        TFCFRock rockTFCF = null;

        if (surfaceRock != null)
        {
            for (Rock r : Rock.values())
            {
                if (surfaceRock.raw() == TFCBlocks.ROCK_BLOCKS.get(r).get(Rock.BlockType.RAW).get())
                {
                    rockTFC = r;
                    break;
                }
                else
                {
                    for (TFCFRock r2 : TFCFRock.values())
                    {
                        if (surfaceRock.raw() == TFCFBlocks.TFCF_ROCK_BLOCKS.get(r2).get(Rock.BlockType.RAW).get())
                        {
                            rockTFCF = r2;
                            break;
                        }
                    }
                }
            }
        }
        if (rockTFC != null)
            return TFCFBlocks.ROCK_BLOCKS.get(rockTFC).get(rockType).get().defaultBlockState();
        else if (rockTFCF != null)
            return TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rockTFCF).get(rockType).get().defaultBlockState();
        else
            return TFCFBlocks.ROCK_BLOCKS.get(Rock.GRANITE).get(rockType).get().defaultBlockState();
    }
}
