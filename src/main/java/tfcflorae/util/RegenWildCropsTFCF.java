package tfcflorae.util;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockShortGrassTFC;

import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.world.worldgen.WorldGenWildCropsTFCF;

@ParametersAreNonnullByDefault
public class RegenWildCropsTFCF extends WorldGenWildCropsTFCF
{
    @Override
    protected boolean isValidPosition(World world, BlockPos pos)
    {
        //Modified to allow replacement of grass during spring regen
        Block block = world.getBlockState(pos).getBlock();
        return (block instanceof BlockShortGrassTFC || block.isAir(world.getBlockState(pos), world, pos) && (BlocksTFC.isSoil(world.getBlockState(pos.down())) || BlocksTFCF.isSoil(world.getBlockState(pos.down()))));
    }
    
}
