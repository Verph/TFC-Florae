package tfcflorae.world.feature.tree.mangrove;


import com.google.common.collect.Lists;
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import tfcflorae.world.feature.tree.RootedTreeConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;

public class MangroveRootPlacer
{
    public static final Codec<MangroveRootPlacer> CODEC = RecordCodecBuilder.create(instance -> {
        return codec(instance).and(MangroveRootPlacement.CODEC.fieldOf("mangrove_root_placement").forGetter(placer -> {
            return placer.mangroveRootPlacement;
        })).apply(instance, MangroveRootPlacer::new);
    });
    protected final IntProvider trunkOffsetY;
    protected final BlockStateProvider rootProvider;
    protected final Optional<AboveRootPlacement> aboveRootPlacement;
    private final MangroveRootPlacement mangroveRootPlacement;

    protected static <P extends MangroveRootPlacer> Products.P3<RecordCodecBuilder.Mu<P>, IntProvider, BlockStateProvider, Optional<AboveRootPlacement>> codec(RecordCodecBuilder.Instance<P> instance) {
        return instance.group(IntProvider.CODEC.fieldOf("trunk_offset_y").forGetter(placer -> {
            return placer.trunkOffsetY;
        }), BlockStateProvider.CODEC.fieldOf("root_provider").forGetter(placer -> {
            return placer.rootProvider;
        }), AboveRootPlacement.CODEC.optionalFieldOf("above_root_placement").forGetter(placer -> {
            return placer.aboveRootPlacement;
        }));
    }

    public MangroveRootPlacer(IntProvider trunkOffsetY, BlockStateProvider rootProvider, Optional<AboveRootPlacement> aboveRootPlacement, MangroveRootPlacement mangroveRootPlacement)
    {
        this.trunkOffsetY = trunkOffsetY;
        this.rootProvider = rootProvider;
        this.aboveRootPlacement = aboveRootPlacement;
        this.mangroveRootPlacement = mangroveRootPlacement;
    }
    
    public boolean generate(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, BlockPos origin, RootedTreeConfig config) {
        ArrayList<BlockPos> positions = Lists.newArrayList();
        BlockPos.MutableBlockPos mutable = pos.mutable();

        while(mutable.getY() < origin.getY()) {
            if (!this.canGrowThrough(level, mutable)) return false;

            mutable.move(Direction.UP);
        }

        positions.add(origin.below());
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos position = origin.relative(direction);
            ArrayList<BlockPos> offshootPositions = Lists.newArrayList();
            if (!this.canGrow(level, random, position, direction, origin, offshootPositions, 0)) return false;

            positions.addAll(offshootPositions);
            positions.add(origin.relative(direction));
        }

        for(BlockPos position : positions) this.placeRoots(level, replacer, random, position, config);

        return true;
    }

    protected boolean canGrowThrough(LevelSimulatedReader level, BlockPos pos)
    {
        return TreeFeature.validTreePos(level, pos) || level.isStateAtPosition(pos, FluidHelpers::isAirOrEmptyFluid) || level.isStateAtPosition(pos, state -> state.is(TFCTags.Blocks.SINGLE_BLOCK_REPLACEABLE)) || level.isStateAtPosition(pos, state -> state.is(this.mangroveRootPlacement.canGrowThrough())) || level.isStateAtPosition(pos, state -> state.is(TFCBlocks.SALT_WATER.get())) || level.isStateAtPosition(pos, state -> state.is(TFCBlocks.RIVER_WATER.get())) || level.isStateAtPosition(pos, state -> state.is(TFCBlocks.SPRING_WATER.get()));
    }

    private boolean canGrow(LevelSimulatedReader level, Random random, BlockPos pos, Direction direction, BlockPos origin, List<BlockPos> offshootPositions, int rootLength) {
        int length = this.mangroveRootPlacement.maxRootLength();
        if (rootLength != length && offshootPositions.size() <= length) {
            for(BlockPos position : this.getOffshootPositions(pos, direction, random, origin)) {
                if (this.canGrowThrough(level, position)) {
                    offshootPositions.add(position);
                    if (!this.canGrow(level, random, position, direction, origin, offshootPositions, rootLength + 1)) return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    protected List<BlockPos> getOffshootPositions(BlockPos pos, Direction direction, Random random, BlockPos origin) {
        BlockPos below = pos.below();
        BlockPos offset = pos.relative(direction);
        int distance = pos.distManhattan(origin);
        int rootWidth = this.mangroveRootPlacement.maxRootWidth();
        float chance = this.mangroveRootPlacement.randomSkewChance();
        if (distance > rootWidth - 3 && distance <= rootWidth) {
            return random.nextFloat() < chance ? List.of(below, offset.below()) : List.of(below);
        } else if (distance > rootWidth) {
            return List.of(below);
        } else if (random.nextFloat() < chance) {
            return List.of(below);
        } else {
            return random.nextBoolean() ? List.of(offset) : List.of(below);
        }
    }

    protected void placeRoots(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, RootedTreeConfig config)
    {
        if (level.isStateAtPosition(pos, state -> state.is(this.mangroveRootPlacement.muddyRootsIn()))) {
            BlockState state = this.mangroveRootPlacement.muddyRootsProvider().getState(random, pos);
            replacer.accept(pos, this.applyWaterlogging(level, pos, state));
        } else {
            if (this.canGrowThrough(level, pos)) {
                replacer.accept(pos, this.applyWaterlogging(level, pos, this.rootProvider.getState(random, pos)));
                if (this.aboveRootPlacement.isPresent()) {
                    AboveRootPlacement placement = this.aboveRootPlacement.get();
                    BlockPos above = pos.above();
                    if (random.nextFloat() < placement.aboveRootPlacementChance() && level.isStateAtPosition(above, BlockBehaviour.BlockStateBase::isAir)) {
                        replacer.accept(above, this.applyWaterlogging(level, above, placement.aboveRootProvider().getState(random, above)));
                    }
                }
            }
        }
    }

    protected BlockState applyWaterlogging(LevelSimulatedReader level, BlockPos pos, BlockState state)
    {
        return state.hasProperty(BlockStateProperties.WATERLOGGED) ? state.setValue(BlockStateProperties.WATERLOGGED, level.isFluidAtPosition(pos, fluid -> (fluid.is(FluidTags.WATER) || fluid.is(TFCBlocks.SALT_WATER.get().getFluid())))) : state;
    }

    public BlockPos trunkOffset(BlockPos pos, Random random)
    {
        return pos.above(this.trunkOffsetY.sample(random));
    }
}
