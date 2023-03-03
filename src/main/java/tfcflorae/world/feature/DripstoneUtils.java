package tfcflorae.world.feature;

import java.util.function.Consumer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.Tags;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;

import tfcflorae.common.blocks.rock.TFCFPointedDripstoneBlock;

public class DripstoneUtils
{
    /**
     * The formula used to control dripstone columns radius.
     * @see <a href="https://twitter.com/henrikkniberg/status/1334180031900360707">This tweet by Henrik.</a>
     */
    public static double getDripstoneHeight(double pRadius, double pMaxRadius, double pScale, double pMinRadius)
    {
        if (pRadius < pMinRadius)
        {
            pRadius = pMinRadius;
        }

        double d0 = 0.384D;
        double d1 = pRadius / pMaxRadius * 0.384D;
        double d2 = 0.75D * Math.pow(d1, 1.3333333333333333D);
        double d3 = Math.pow(d1, 0.6666666666666666D);
        double d4 = 0.3333333333333333D * Math.log(d1);
        double d5 = pScale * (d2 - d3 - d4);
        d5 = Math.max(d5, 0.0D);
        return d5 / 0.384D * pMaxRadius;
    }

    public static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel level, BlockPos pos, int pRadius)
    {
        if (isEmptyOrWaterOrLava(level, pos))
        {
            return false;
        }
        else
        {
            float f = 6.0F;
            float f1 = 6.0F / (float)pRadius;

            for(float f2 = 0.0F; f2 < ((float)Math.PI * 2F); f2 += f1)
            {
                int i = (int)(Mth.cos(f2) * (float)pRadius);
                int j = (int)(Mth.sin(f2) * (float)pRadius);
                if (isEmptyOrWaterOrLava(level, pos.offset(i, 0, j)))
                {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isEmptyOrWater(LevelAccessor level, BlockPos pos)
    {
        return level.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWater);
    }

    public static boolean isEmptyOrWaterOrLava(LevelAccessor level, BlockPos pos)
    {
        return level.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWaterOrLava);
    }

    public static void buildBaseToTipColumn(LevelAccessor level, BlockPos pos, BlockState inputState, Direction direction, int height, boolean mergeTip, Consumer<BlockState> pBlockSetter)
    {
        if (height >= 3)
        {
            pBlockSetter.accept(createPointedDripstone(level, pos, inputState, direction, DripstoneThickness.BASE));

            for(int i = 0; i < height - 3; ++i)
            {
                pBlockSetter.accept(createPointedDripstone(level, pos, inputState, direction, DripstoneThickness.MIDDLE));
            }
        }

        if (height >= 2)
        {
            pBlockSetter.accept(createPointedDripstone(level, pos, inputState, direction, DripstoneThickness.FRUSTUM));
        }

        if (height >= 1)
        {
            pBlockSetter.accept(createPointedDripstone(level, pos, inputState, direction, mergeTip ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
        }
    }

    public static void growPointedDripstone(BlockState inputState, Boolean hasSurface, LevelAccessor level, BlockPos pos, Direction direction, int height, boolean mergeTip)
    {
        if (isDripstoneBase(level.getBlockState(pos.relative(direction.getOpposite())), inputState))
        {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
            buildBaseToTipColumn(level, pos, inputState, direction, height, mergeTip, (state) -> {
                //if (state == inputState)
                if (state.getBlock() instanceof TFCFPointedDripstoneBlock)
                {
                    final FluidProperty property = ((IFluidLoggable) state.getBlock()).getFluidProperty();
                    final FluidState fluid = level.getFluidState(blockpos$mutableblockpos);
                    state = state.setValue(property, property.keyForOrEmpty(fluid.getType()));
                }

                level.setBlock(blockpos$mutableblockpos, state, 2);
                blockpos$mutableblockpos.move(direction);
            });
        }
    }

    public static boolean placeDripstoneBlockIfPossible(BlockState inputSurfaceState, LevelAccessor level, BlockPos pos, Boolean hasSurface)
    {
        BlockState blockstate = level.getBlockState(pos);
        if (hasSurface && (isBaseState(blockstate)))
        {
            if (inputSurfaceState != null)
            {
                level.setBlock(pos, inputSurfaceState, 2);
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    public static BlockState createPointedDripstone(LevelAccessor level, BlockPos pos, BlockState inputState, Direction direction, DripstoneThickness pDripstoneThickness)
    {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
        if (inputState.getBlock() instanceof TFCFPointedDripstoneBlock)
        {
            final FluidProperty property = ((IFluidLoggable) inputState.getBlock()).getFluidProperty();
            final FluidState fluid = level.getFluidState(blockpos$mutableblockpos);
            inputState = inputState.setValue(property, property.keyForOrEmpty(fluid.getType()));
        }
        return inputState.setValue(TFCFPointedDripstoneBlock.TIP_DIRECTION, direction).setValue(TFCFPointedDripstoneBlock.THICKNESS, pDripstoneThickness);
    }

    public static boolean isDripstoneBaseOrLava(BlockState state, BlockState inputSurfaceState)
    {
        return isDripstoneBase(state, inputSurfaceState) || state.is(Blocks.LAVA) || EnvironmentHelpers.isWater(state) || FluidHelpers.isAirOrEmptyFluid(state);
    }

    public static boolean isDripstoneBase(BlockState state, BlockState inputSurfaceState)
    {
        return state.is(inputSurfaceState.getBlock()) || isBaseState(state);
    }

    public static boolean isEmptyOrWater(BlockState state)
    {
        return state.isAir() || state.is(Blocks.WATER) || state.is(TFCBlocks.SALT_WATER.get()) || EnvironmentHelpers.isWater(state) || FluidHelpers.isAirOrEmptyFluid(state);
    }

    public static boolean isNeitherEmptyNorWater(BlockState state)
    {
        return !state.isAir() && !state.is(Blocks.WATER) && !state.is(TFCBlocks.SALT_WATER.get()) && !EnvironmentHelpers.isWater(state) && !FluidHelpers.isAirOrEmptyFluid(state);
    }

    public static boolean isEmptyOrWaterOrLava(BlockState state)
    {
        return state.isAir() || state.is(Blocks.WATER) || state.is(TFCBlocks.SALT_WATER.get()) || state.is(Blocks.LAVA) || EnvironmentHelpers.isWater(state) || FluidHelpers.isAirOrEmptyFluid(state);
    }

    public static boolean isBaseState(BlockState state)
    {
        return state.is(BlockTags.DRIPSTONE_REPLACEABLE) || state.is(Tags.Blocks.STONE) || state.is(Tags.Blocks.GRAVEL) || state.is(Tags.Blocks.SAND) || state.is(BlockTags.DIRT) || Helpers.isBlock(state, TFCTags.Blocks.SEA_BUSH_PLANTABLE_ON);
    }
}
