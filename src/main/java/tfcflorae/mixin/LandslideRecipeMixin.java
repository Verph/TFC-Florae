package tfcflorae.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.client.TFCSounds;
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
                    final BlockState fallingState = recipe.getBlockCraftingResult(state);
                    if (level.getBlockState(pos).getBlock() instanceof SandLayerBlock sandLayer && level.getBlockState(fallPos).getBlock() instanceof SandLayerBlock && level.getBlockState(pos).getBlock() == level.getBlockState(fallPos).getBlock())
                    {
                        sandLayer.addSandLayer(level, level.getBlockState(fallPos), fallPos, level.getBlockState(pos).getValue(SandLayerBlock.LAYERS));
                        level.removeBlock(pos, false); // Remove the original position, which would be the falling block
                    }
                    else if (!fallPos.equals(pos))
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
                        level.setBlock(fallPos, fallingState, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
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