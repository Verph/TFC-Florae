package tfcflorae.common.blocks.wood;

import java.util.Random;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.Heightmap;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.wood.TFCLeavesBlock;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.Season;
import net.dries007.tfc.util.climate.ClimateModel;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import tfcflorae.Config;

public abstract class TFCFLeavesBlockExt extends TFCLeavesBlock
{
    public static TFCFLeavesBlockExt create(ExtendedProperties properties, int maxDecayDistance)
    {
        return create(properties, maxDecayDistance, null, null, null);
    }

    public static TFCFLeavesBlockExt create(ExtendedProperties properties, int maxDecayDistance, @Nullable Supplier<? extends Block> fallenLeaves, @Nullable Supplier<? extends Block> fallenTwig, @Nullable Supplier<? extends Block> sapling)
    {
        final IntegerProperty distanceProperty = getDistanceProperty(maxDecayDistance);
        return new TFCFLeavesBlockExt(properties, maxDecayDistance, fallenLeaves, fallenTwig, sapling)
        {
            @Override
            protected IntegerProperty getDistanceProperty()
            {
                return distanceProperty;
            }
        };
    }

    private static IntegerProperty getDistanceProperty(int maxDecayDistance)
    {
        if (maxDecayDistance >= 7 && maxDecayDistance < 7 + TFCBlockStateProperties.DISTANCES.length)
        {
            return TFCBlockStateProperties.DISTANCES[maxDecayDistance - 7 + 1]; // we select one higher than max
        }
        throw new IllegalArgumentException("No property set for distance: " + maxDecayDistance);
    }

    /* The maximum value of the decay property. */
    @Nullable private final Supplier<? extends Block> fallenLeaves;
    @Nullable private final Supplier<? extends Block> fallenTwig;
    @Nullable private final Supplier<? extends Block> sapling;

    public TFCFLeavesBlockExt(ExtendedProperties properties, int maxDecayDistance, @Nullable Supplier<? extends Block> fallenLeaves, @Nullable Supplier<? extends Block> fallenTwig, @Nullable Supplier<? extends Block> sapling)
    {
        super(properties, maxDecayDistance, fallenLeaves, fallenTwig);

        this.fallenLeaves = fallenLeaves;
        this.fallenTwig = fallenTwig;
        this.sapling = sapling;

        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(this.getDistanceProperty(), 1)).setValue(PERSISTENT, false));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        super.randomTick(state, level, pos, random);
        if (Config.COMMON.leavesSaplingPlacementChance.get() > 0)
        {
            Season currentSeason = Calendars.get(level).getCalendarMonthOfYear().getSeason();
            if ((currentSeason == Season.FALL || currentSeason == Season.SPRING) && level.getBlockState(pos.below()).isAir())
            {
                final ChunkDataProvider provider = ChunkDataProvider.get(level);
                final ChunkData data = provider.get(level, pos);

                final float rainfall = data.getRainfall(pos);
                final float rainfallInverted = ((ClimateModel.MAXIMUM_RAINFALL - rainfall) * 0.25F) + 1F;

                final float actualForestDensity = data.getForestDensity();
                final float forestDensity = actualForestDensity == 0 ? 0.001F : actualForestDensity; // Cannot divide by 0.

                if (random.nextFloat((Config.COMMON.leavesSaplingPlacementChance.get() / forestDensity) * rainfallInverted) == 0)
                {
                    int x = pos.getX() + (int) Math.round(random.nextGaussian() * Config.COMMON.leavesSaplingSpreadDistance.get());
                    int z = pos.getZ() + (int) Math.round(random.nextGaussian() * Config.COMMON.leavesSaplingSpreadDistance.get());
                    int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

                    BlockPos placementPos = new BlockPos(x, y, z);
                    BlockState placementState = level.getBlockState(placementPos);
                    final BlockState saplingState = sapling.get().defaultBlockState();

                    if ((Helpers.isBlock(placementState, TFCTags.Blocks.PLANTS) || EnvironmentHelpers.isWorldgenReplaceable(placementState)) && level.getMaxLocalRawBrightness(placementPos) >= 11 && saplingState.canSurvive(level, placementPos))
                    {
                        level.setBlock(placementPos, saplingState, Block.UPDATE_ALL);
                    }
                }
            }
        }
    }
}
