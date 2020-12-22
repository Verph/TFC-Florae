package tfcflorae.objects.blocks.blocktype;

import javax.annotation.Nullable;


import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.entity.EntityFallingBlockTFC;
import net.dries007.tfc.util.IFallingBlock;

import tfcflorae.types.BlockTypesTFCF;

public class BlockGrassFallable extends BlockGrass implements IFallingBlock
{
    public BlockGrassFallable(Rock rock, BlockTypesTFCF BlocksRockTFCF)
    {
        super(rock, BlocksRockTFCF);
    }

    public static boolean isSupportingSideBlock(IBlockState state)
    {
        return state.isNormalCube();
    }

    @Nullable
    @Override
    public BlockPos getFallablePos(World world, BlockPos pos, boolean ignoreSupportChecks)
    {
        if (type.isFallable() && shouldFall(world, pos, pos, ignoreSupportChecks))
        {
            return checkAreaClear(world, pos);
        }
        if (type.isFallable())
        {
            // Check if supported by at least two horizontals, or one on top
            if (isSupportingSideBlock(world.getBlockState(pos.up())))
            {
                return null;
            }

            EnumFacing[] faces = Arrays.stream(EnumFacing.HORIZONTALS)
                    .filter(x -> isSupportingSideBlock(world.getBlockState(pos.offset(x))))
                    .toArray(EnumFacing[]::new);

            if (faces.length >= 2)
            {
                return null;
            }

            // Check if it can fall
            IBlockState originalState = world.getBlockState(pos);
            faces = Arrays.stream(EnumFacing.HORIZONTALS)
                    .filter(x -> shouldFall(world, pos.offset(x), pos, ignoreSupportChecks) && IFallingBlock.canFallThrough(world, pos.offset(x), originalState.getMaterial()))
                    .toArray(EnumFacing[]::new);

            if (faces.length >= 1)
            {
                return checkAreaClear(world, pos.offset(faces[RANDOM.nextInt(faces.length)]));
            }
        }
        return null;
    }

    @Nullable
    private BlockPos checkAreaClear(World world, BlockPos pos) {
        if (!world.getEntitiesWithinAABB(EntityFallingBlockTFC.class, new AxisAlignedBB(pos, pos.add(1, 1, 1))).isEmpty()) {
            world.scheduleUpdate(pos, this, 20);
            return null;
        } else {
            return pos;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (!this.type.isFallable()) return;
        if (rand.nextInt(16) != 0) return;
        if (shouldFall(worldIn, pos, pos))
        {
            double d0 = (float) pos.getX() + rand.nextFloat();
            double d1 = (double) pos.getY() - 0.05D;
            double d2 = (float) pos.getZ() + rand.nextFloat();
            worldIn.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(stateIn));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        if (checkFalling(worldIn, pos, state))
        {
            onRockSlide(worldIn, pos);
        }
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(worldIn, pos, state);
        if (checkFalling(worldIn, pos, state))
        {
            onRockSlide(worldIn, pos);
        }
    }

    protected void onRockSlide(World world, BlockPos pos)
    {
        switch (type)
        {
            case PODZOL:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case LOAMY_SAND_GRASS:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case LOAMY_SAND_PODZOL:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case SANDY_LOAM_GRASS:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case SANDY_LOAM_PODZOL:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case SANDY_CLAY_LOAM_GRASS:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case SANDY_CLAY_LOAM_PODZOL:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case SANDY_CLAY_GRASS:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case SANDY_CLAY_PODZOL:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case LOAM_GRASS:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case LOAM_PODZOL:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case CLAY_LOAM_GRASS:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case CLAY_LOAM_PODZOL:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case CLAY_PODZOL:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case SILTY_CLAY_GRASS:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case SILTY_CLAY_PODZOL:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case SILTY_CLAY_LOAM_GRASS:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case SILTY_CLAY_LOAM_PODZOL:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case SILT_LOAM_GRASS:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case SILT_LOAM_PODZOL:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case SILT_GRASS:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            case SILT_PODZOL:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            default:
                break;
        }
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return super.getItemDropped(state, rand, fortune);
    }

    /*@Override
    public boolean checkFalling(World worldIn, BlockPos pos, IBlockState state)
    {
        FMLLog.log.info("CheckFalling");
        BlockPos pos1 = getFallablePos(worldIn, pos);
        if (pos1 != null)
        {
            if (!BlockFalling.fallInstantly && worldIn.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32)))
            {
                if (!pos1.equals(pos))
                {
                    worldIn.setBlockToAir(pos);
                }
                // Replace grass with dirt
                if (type == BlockTypesTFCF.PODZOL)
                {
                    FMLLog.log.info("TFC Rocks: ");
                    for(Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                    {
                        FMLLog.log.info(rock.getRegistryName().toString());
                        FMLLog.log.info(rock.getRegistryName().getPath());

                        if(rock.getRegistryName().toString() == "tfc:dirt/granite")
                        {
                            worldIn.setBlockState(pos1, BlockRockVariant.get(rock, Rock.Type.DIRT).getDefaultState());
                        }
                    }
                    //worldIn.setBlockState(pos1, BlockRockVariant.get(rock, Rock.Type.DIRT).getDefaultState());
                }
                worldIn.spawnEntity(new EntityFallingBlockTFC(worldIn, pos1, this, worldIn.getBlockState(pos1)));
            }
            else
            {
                worldIn.setBlockToAir(pos);
                pos1 = pos1.down();
                while (IFallingBlock.canFallThrough(worldIn, pos1, state.getMaterial()) && pos1.getY() > 0)
                {
                    pos1 = pos1.down();
                }
                if (pos1.getY() > 0)
                {
                    worldIn.setBlockState(pos1.up(), state); // Includes Forge's fix for data loss.
                }
            }
            return true;
        }
        return false;
    }*/
}