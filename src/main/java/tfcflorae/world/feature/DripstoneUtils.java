package tfcflorae.world.feature;

import java.util.function.Consumer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.util.EnvironmentHelpers;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.DripstoneRock;

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

    public static void buildBaseToTipColumn(Direction direction, int height, boolean mergeTip, Consumer<BlockState> pBlockSetter)
    {
        if (height >= 3)
        {
            pBlockSetter.accept(createPointedDripstone(direction, DripstoneThickness.BASE));

            for(int i = 0; i < height - 3; ++i)
            {
                pBlockSetter.accept(createPointedDripstone(direction, DripstoneThickness.MIDDLE));
            }
        }

        if (height >= 2)
        {
            pBlockSetter.accept(createPointedDripstone(direction, DripstoneThickness.FRUSTUM));
        }

        if (height >= 1)
        {
            pBlockSetter.accept(createPointedDripstone(direction, mergeTip ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
        }
    }

    public static void growPointedDripstone(LevelAccessor level, BlockPos pos, Direction direction, int height, boolean mergeTip)
    {
        if (isDripstoneBase(level.getBlockState(pos.relative(direction.getOpposite()))))
        {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
            buildBaseToTipColumn(direction, height, mergeTip, (state) -> {
                if (state.is(TFCFBlocks.DRIPSTONE_BLOCKS.get(DripstoneRock.DRIPSTONE).get(Rock.BlockType.SPIKE).get()))
                {
                    state = state.setValue(PointedDripstoneBlock.WATERLOGGED, Boolean.valueOf(EnvironmentHelpers.isWater(level.getBlockState(blockpos$mutableblockpos))));
                }

                level.setBlock(blockpos$mutableblockpos, state, 2);
                blockpos$mutableblockpos.move(direction);
            });
        }
    }

    public static boolean placeDripstoneBlockIfPossible(LevelAccessor level, BlockPos pos)
    {
        BlockState blockstate = level.getBlockState(pos);
        if (blockstate.is(BlockTags.DRIPSTONE_REPLACEABLE))
        {
            level.setBlock(pos, TFCFBlocks.DRIPSTONE_BLOCKS.get(DripstoneRock.DRIPSTONE).get(Rock.BlockType.RAW).get().defaultBlockState(), 2);
            return true;
        }
        else
        {
            return false;
        }
    }

    public static BlockState createPointedDripstone(Direction direction, DripstoneThickness pDripstoneThickness)
    {
        return TFCFBlocks.DRIPSTONE_BLOCKS.get(DripstoneRock.DRIPSTONE).get(Rock.BlockType.SPIKE).get().defaultBlockState().setValue(PointedDripstoneBlock.TIP_DIRECTION, direction).setValue(PointedDripstoneBlock.THICKNESS, pDripstoneThickness);
    }

    public static boolean isDripstoneBaseOrLava(BlockState state)
    {
        return isDripstoneBase(state) || state.is(Blocks.LAVA) || EnvironmentHelpers.isWater(state) || FluidHelpers.isAirOrEmptyFluid(state);
    }

    public static boolean isDripstoneBase(BlockState state)
    {
        return state.is(TFCFBlocks.DRIPSTONE_BLOCKS.get(DripstoneRock.DRIPSTONE).get(Rock.BlockType.RAW).get()) || state.is(BlockTags.DRIPSTONE_REPLACEABLE);
    }

    public static boolean isEmptyOrWater(BlockState state)
    {
        return state.isAir() || state.is(Blocks.WATER) || EnvironmentHelpers.isWater(state) || FluidHelpers.isAirOrEmptyFluid(state);
    }

    public static boolean isNeitherEmptyNorWater(BlockState state)
    {
        return !state.isAir() && !state.is(Blocks.WATER) && !EnvironmentHelpers.isWater(state) && !FluidHelpers.isAirOrEmptyFluid(state);
    }

    public static boolean isEmptyOrWaterOrLava(BlockState state)
    {
        return state.isAir() || state.is(Blocks.WATER) || state.is(Blocks.LAVA) || EnvironmentHelpers.isWater(state) || FluidHelpers.isAirOrEmptyFluid(state);
    }
}
