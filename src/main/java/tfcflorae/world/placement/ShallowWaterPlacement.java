package tfcflorae.world.placement;

import java.util.Random;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.Codecs;

public class ShallowWaterPlacement extends PlacementModifier
{
    public static final Codec<ShallowWaterPlacement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codecs.POSITIVE_INT.optionalFieldOf("min_depth", 0).forGetter(c -> c.minDepth),
        Codecs.POSITIVE_INT.optionalFieldOf("max_depth", 5).forGetter(c -> c.maxDepth)
    ).apply(instance, ShallowWaterPlacement::new));

    private final int minDepth;
    private final int maxDepth;

    public ShallowWaterPlacement(int minDepth, int maxDepth)
    {
        this.minDepth = minDepth;
        this.maxDepth = maxDepth;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, Random random, BlockPos pos)
    {
        final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos().set(pos);
        for (int i = 0; i < maxDepth; i++)
        {
            mutablePos.move(0, -1, 0);
            if (!context.getLevel().isFluidAtPosition(mutablePos, state -> Helpers.isFluid(state, FluidTags.WATER)))
            {
                if (i < minDepth)
                {
                    return Stream.empty();
                }
                return random.nextFloat() > (double) i / maxDepth ? Stream.of(pos) : Stream.empty();
            }
        }
        return Stream.empty();
    }

    @Override
    public PlacementModifierType<?> type()
    {
        return TFCFPlacements.SHALLOW_WATER.get();
    }
}