package tfcflorae.world.feature;

import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import tfcflorae.common.blocks.TFCFBlocks;

public class TFCFStructurePiece
{
    public static BlockState reorientTFC(BlockGetter pLevel, BlockPos pPos, BlockState pState)
    {
        Direction direction = null;

        for(Direction direction1 : Direction.Plane.HORIZONTAL)
        {
            BlockPos blockpos = pPos.relative(direction1);
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (blockstate.is(TFCFBlocks.ROCK_CHEST.get()))
            {
                return pState;
            }
            else if (blockstate.is(Blocks.CHEST))
            {
                return pState;
            }

            if (blockstate.isSolidRender(pLevel, blockpos))
            {
                if (direction != null)
                {
                    direction = null;
                    break;
                }

                direction = direction1;
            }
        }

        if (direction != null)
        {
            return pState.setValue(HorizontalDirectionalBlock.FACING, direction.getOpposite());
        }
        else
        {
            Direction direction2 = pState.getValue(HorizontalDirectionalBlock.FACING);
            BlockPos blockpos1 = pPos.relative(direction2);
            if (pLevel.getBlockState(blockpos1).isSolidRender(pLevel, blockpos1))
            {
                direction2 = direction2.getOpposite();
                blockpos1 = pPos.relative(direction2);
            }

            if (pLevel.getBlockState(blockpos1).isSolidRender(pLevel, blockpos1))
            {
                direction2 = direction2.getClockWise();
                blockpos1 = pPos.relative(direction2);
            }

            if (pLevel.getBlockState(blockpos1).isSolidRender(pLevel, blockpos1))
            {
                direction2 = direction2.getOpposite();
                pPos.relative(direction2);
            }

            return pState.setValue(HorizontalDirectionalBlock.FACING, direction2);
        }
    }

    public static boolean createChestTFC(ServerLevelAccessor pLevel, BoundingBox pBox, Random pRandom, BlockPos pPos, ResourceLocation pLootTable, @Nullable BlockState pState)
    {
        if (pBox.isInside(pPos) && !pLevel.getBlockState(pPos).is(TFCFBlocks.ROCK_CHEST.get()))
        {
            if (pState == null)
            {
                pState = reorientTFC(pLevel, pPos, TFCFBlocks.ROCK_CHEST.get().defaultBlockState());
            }
            pLevel.setBlock(pPos, pState, 2);
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof ChestBlockEntity)
            {
                ((ChestBlockEntity)blockentity).setLootTable(pLootTable, pRandom.nextLong());
            }
            return true;
        }
        else if (pBox.isInside(pPos) && !pLevel.getBlockState(pPos).is(Blocks.CHEST))
        {
            if (pState == null)
            {
                pState = reorientTFC(pLevel, pPos, Blocks.CHEST.defaultBlockState());
            }
            pLevel.setBlock(pPos, pState, 2);
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof ChestBlockEntity)
            {
                ((ChestBlockEntity)blockentity).setLootTable(pLootTable, pRandom.nextLong());
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}
