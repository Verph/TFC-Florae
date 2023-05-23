package tfcflorae.mixin.world.features;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

import net.minecraftforge.registries.ForgeRegistries;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.plant.coral.CoralWallFanBlock;
import net.dries007.tfc.common.blocks.plant.coral.TFCSeaPickleBlock;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.feature.coral.CoralHelpers;

@Mixin(CoralHelpers.class)
public final class CoralHelpersMixin
{
    @Overwrite(remap = false)
    public static boolean placeCoralBlock(LevelAccessor level, Random rand, BlockPos pos, BlockState coralBlockState)
    {
        BlockPos abovePos = pos.above();
        BlockState blockstate = level.getBlockState(pos);
        if ((Helpers.isBlock(blockstate, TFCBlocks.SALT_WATER.get()) || Helpers.isBlock(blockstate, TFCTags.Blocks.CORALS)) && Helpers.isBlock(level.getBlockState(abovePos), TFCBlocks.SALT_WATER.get()))
        {
            level.setBlock(pos, coralBlockState, 3);
            if (rand.nextFloat() < 0.25F)
            {
                Helpers.getRandomElement(ForgeRegistries.BLOCKS, TFCTags.Blocks.CORALS, rand).ifPresent(block -> {
                    level.setBlock(abovePos, salty(block.defaultBlockState()), 2);
                });
            }
            else if (rand.nextFloat() < 0.05F && TFCBlockStateProperties.ALL_WATER.keyForOrEmpty(level.getFluidState(abovePos).getType()) != null)
            {
                placeSeaPickle(level, rand, abovePos, level.getBlockState(abovePos));
            }

            for (Direction direction : Direction.Plane.HORIZONTAL)
            {
                if (rand.nextFloat() < 0.2F)
                {
                    BlockPos relativePos = pos.relative(direction);
                    if (Helpers.isBlock(level.getBlockState(relativePos), TFCBlocks.SALT_WATER.get()))
                    {
                        Helpers.getRandomElement(ForgeRegistries.BLOCKS, TFCTags.Blocks.WALL_CORALS, rand).ifPresent(block -> {
                            BlockState wallCoralState = block.defaultBlockState();
                            if (wallCoralState.hasProperty(CoralWallFanBlock.FACING))
                            {
                                level.setBlock(relativePos, salty(wallCoralState.setValue(CoralWallFanBlock.FACING, direction)), 2);
                            }
                        });
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Shadow
    private static BlockState salty(BlockState state)
    {
        return state.setValue(TFCBlockStateProperties.SALT_WATER, TFCBlockStateProperties.SALT_WATER.keyFor(TFCFluids.SALT_WATER.getSource()));
    }

    @Unique
    private static boolean placeSeaPickle(LevelAccessor level, Random rand, BlockPos pos, BlockState state)
    {
        final FluidState fluidAt = level.getFluidState(pos);
        final TFCSeaPickleBlock pickleBlock = (TFCSeaPickleBlock) TFCBlocks.SEA_PICKLE.get();
        if (pickleBlock.getFluidProperty().canContain(fluidAt.getType()))
        {
            final BlockState pickleState = TFCBlocks.SEA_PICKLE.get().defaultBlockState().setValue(TFCSeaPickleBlock.PICKLES, rand.nextInt(4) + 1).setValue(pickleBlock.getFluidProperty(), pickleBlock.getFluidProperty().keyForOrEmpty(fluidAt.getType()));
            return level.setBlock(pos, pickleState, 2);
        }
        return false;
    }
}
