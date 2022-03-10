package tfcflorae.objects.blocks;

import java.util.Random;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.util.Helpers;

import tfcflorae.objects.blocks.groundcover.BlockPowder;
import tfcflorae.objects.items.ItemPowderTFC;
import tfcflorae.objects.te.TEPowder;

@ParametersAreNonnullByDefault
public class BlockFluidLava extends BlockStaticLiquid
{
    protected BlockFluidLava(Material materialIn)
    {
        super(materialIn);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);
        if (this.material == Material.LAVA)
        {
            BlockPos genPos = pos.add(rand.nextInt(7) - 3, rand.nextInt(7) - 3, rand.nextInt(7) - 3);
            EnumFacing face = EnumFacing.random(rand);
            BlockPos posAt = genPos.offset(face);
            IBlockState stateAt = worldIn.getBlockState(posAt);

            ItemPowderTFC block = ItemPowderTFC.get(Powder.SULFUR);

            if (worldIn.isSideSolid(genPos, face) && stateAt.getMaterial() != Material.LAVA && stateAt.getMaterial() != Material.WATER)
            {
                if (stateAt.getBlock() instanceof BlockPowder)
                {
                    // Existing powder block
                    Powder powderItem = ((BlockPowder) stateAt.getBlock()).getPowder();
                    if (powderItem == block.powder)
                    {
                        placeBlock(worldIn, posAt, face);
                        return;
                    }
                }
                else if (stateAt.getBlock().isReplaceable(worldIn, posAt))
                {
                    // Place a new block
                    if (!worldIn.isRemote)
                    {
                        worldIn.setBlockState(posAt, BlockPowder.get(block.powder).getDefaultState());
                        placeBlock(worldIn, posAt, face);
                    }
                }
            }
        }
    }

    private void placeBlock(World world, BlockPos pos, EnumFacing facing)
    {
        TEPowder tile = Helpers.getTE(world, pos, TEPowder.class);
        if (tile != null && !tile.getFace(facing))
        {
            if (!world.isRemote)
            {
                tile.setFace(facing, true);
            }
        }
    }
}
