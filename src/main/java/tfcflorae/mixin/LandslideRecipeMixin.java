package tfcflorae.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.soil.FarmlandBlock;
import net.dries007.tfc.common.blocks.wood.FallenLeavesBlock;
import net.dries007.tfc.common.entities.TFCFallingBlockEntity;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.recipes.LandslideRecipe;
import net.dries007.tfc.config.TFCConfig;

import tfcflorae.common.blocks.soil.SandLayerBlock;

@Mixin(LandslideRecipe.class)
public class LandslideRecipeMixin
{
    @Overwrite(remap = false)
    public static boolean tryLandslide(Level level, BlockPos pos, BlockState state)
    {
        if (!level.isClientSide() && TFCConfig.SERVER.enableBlockLandslides.get())
        {
            final BlockPos fallPos = LandslideRecipe.getLandslidePos(level, pos, state);
            if (fallPos != null)
            {
                final LandslideRecipe recipe = LandslideRecipe.getRecipe(state);
                if (recipe != null)
                {
                    BlockState fallingState = recipe.getBlockCraftingResult(state);
                    if (level.getBlockState(pos).getBlock() instanceof SandLayerBlock originSand)
                    {
                        if (level.getBlockState(fallPos).getBlock() instanceof SandLayerBlock targetSand)
                        {
                            if (level.getBlockState(fallPos).getValue(SandLayerBlock.LAYERS) >= 7)
                            {
                                level.setBlock(fallPos, targetSand.transformsInto(), Block.UPDATE_ALL);
                            }
                            else
                            {
                                level.setBlock(fallPos, targetSand.defaultBlockState().setValue(SandLayerBlock.LAYERS, level.getBlockState(fallPos).getValue(SandLayerBlock.LAYERS) + 1), Block.UPDATE_ALL);
                            }
                            level.removeBlock(pos, false);
                        }
                        else if (level.getBlockState(fallPos).getMaterial().isReplaceable())
                        {
                            level.setBlock(fallPos, fallingState.setValue(SandLayerBlock.LAYERS, 1), Block.UPDATE_ALL);
                            level.removeBlock(pos, false);
                        }
                    }
                    if (level.getBlockState(pos).getBlock() instanceof FallenLeavesBlock originLeaves)
                    {
                        if (level.getBlockState(fallPos).getBlock() instanceof FarmlandBlock || level.getBlockState(pos.below()).getBlock() instanceof FarmlandBlock)
                        {
                            return false; // Don't break farmland!
                        }
                        if (level.getBlockState(fallPos).getBlock() instanceof FallenLeavesBlock targetLeaves)
                        {
                            if (level.getBlockState(fallPos).getValue(BlockStateProperties.LAYERS) == 8)
                            {
                                level.setBlock(fallPos.above(), targetLeaves.defaultBlockState().setValue(BlockStateProperties.LAYERS, 1), Block.UPDATE_ALL);
                            }
                            else
                            {
                                level.setBlock(fallPos, targetLeaves.defaultBlockState().setValue(BlockStateProperties.LAYERS, level.getBlockState(fallPos).getValue(BlockStateProperties.LAYERS) + 1), Block.UPDATE_ALL);
                            }
                            level.removeBlock(pos, false);
                        }
                        else if (level.getBlockState(fallPos).getMaterial().isReplaceable())
                        {
                            level.setBlock(fallPos, fallingState.setValue(BlockStateProperties.LAYERS, 1), Block.UPDATE_ALL);
                            level.removeBlock(pos, false);
                        }
                    }
                    else if (!(level.getBlockState(pos).getBlock() instanceof SandLayerBlock || level.getBlockState(pos).getBlock() instanceof FallenLeavesBlock))
                    {
                        if (!fallPos.equals(pos))
                        {
                            level.removeBlock(pos, false); // Remove the original position, which would be the falling block
                            if (!FluidHelpers.isAirOrEmptyFluid(level.getBlockState(fallPos)))
                            {
                                level.destroyBlock(fallPos, true); // Destroy the block that currently occupies the pos we are going to move sideways into
                            }
                        }
                        if (TFCConfig.SERVER.farmlandMakesTheBestRaceTracks.get())
                        {
                            // This is funny, but technically a bug. So it's left here as a disabled-by-default easter egg.
                            // By setting the block and updating, farmland below will turn into a solid block, and then this falling block will attempt falling again, proceeding in a cycle.
                            // We avoid that by not causing a block update.
                            level.setBlockAndUpdate(fallPos, fallingState);
                        }
                        else
                        {
                            if (state.hasProperty(TFCBlockStateProperties.NATURAL) && fallingState.hasProperty(TFCBlockStateProperties.NATURAL))
                            {
                                fallingState.setValue(TFCBlockStateProperties.NATURAL, state.getValue(TFCBlockStateProperties.NATURAL));
                            }
                            level.setBlock(fallPos, fallingState, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
                        }
                    }
                    level.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT.get(), SoundSource.BLOCKS, 0.4f, 1.0f);
                    level.addFreshEntity(new TFCFallingBlockEntity(level, fallPos.getX() + 0.5, fallPos.getY(), fallPos.getZ() + 0.5, fallingState, 0.8f, 10));
                }
                return true;
            }
        }
        return false;
    }
}