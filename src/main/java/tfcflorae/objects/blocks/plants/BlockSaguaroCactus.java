package tfcflorae.objects.blocks.plants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.ibm.icu.impl.CalendarAstronomer.Horizon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import net.dries007.tfc.Constants;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.property.ITallPlant;
import net.dries007.tfc.objects.blocks.property.ITallPlant.EnumBlockPart;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.dries007.tfc.util.Helpers;

import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.plants.BlockPlant.BlockPlantTFCF;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.objects.te.TESaguaroCactus;
import tfcflorae.types.PlantsTFCF;

@ParametersAreNonnullByDefault
public class BlockSaguaroCactus extends BlockPlantTFCF implements IGrowable, ITallPlant
{
    public static final PropertyDirection HORIZONTAL_DIRECTION  = PropertyDirection.create("facing");
    public static final PropertyBool HORIZONTAL  = PropertyBool.create("horizontal");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    private static final Map<Plant, BlockSaguaroCactus> MAP = new HashMap<>();
    
    public static final AxisAlignedBB CACTUS_ROOT = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);

    public static BlockSaguaroCactus get(Plant plant)
    {
        return BlockSaguaroCactus.MAP.get(plant);
    }

    public BlockSaguaroCactus(Plant plant)
    {
        super(plant);
        if (MAP.put(plant, this) != null) throw new IllegalStateException("There can only be one.");
        this.setDefaultState(this.blockState.getBaseState().withProperty(HORIZONTAL_DIRECTION, EnumFacing.NORTH)
                                                            .withProperty(HORIZONTAL, Boolean.valueOf(false))
                                                            .withProperty(NORTH, Boolean.valueOf(false))
                                                            .withProperty(EAST, Boolean.valueOf(false))
                                                            .withProperty(SOUTH, Boolean.valueOf(false))
                                                            .withProperty(WEST, Boolean.valueOf(false)));
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack)
    {
        return Size.SMALL; // Can store everywhere
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack)
    {
        return Weight.MEDIUM; // stacksize = 16
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return false;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        /*worldIn.setBlockState(pos.up(), this.getDefaultState());
        IBlockState iblockstate = state.withProperty(AGE, 0).withProperty(growthStageProperty, plant.getStageForMonth()).withProperty(PART, getPlantPart(worldIn, pos));
        worldIn.setBlockState(pos, iblockstate);
        iblockstate.neighborChanged(worldIn, pos.up(), this, pos);*/
    }

    public void shrink(World worldIn, BlockPos pos)
    {
        worldIn.setBlockToAir(pos);
        worldIn.getBlockState(pos).neighborChanged(worldIn, pos.down(), this, pos);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, net.minecraftforge.common.IPlantable plantable)
    {
        return true;
        /*IBlockState plant = plantable.getPlant(world, pos.offset(direction));

        if (plant.getBlock() == this)
        {
            return true;
        }
        return super.canSustainPlant(state, world, pos, direction, plantable);*/
    }

    @Override
    @Nonnull
    public Block.EnumOffsetType getOffsetType()
    {
        return EnumOffsetType.NONE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!this.canBlockStay(worldIn, pos, state))
        {
            worldIn.scheduleUpdate(pos, this, 1);
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isAreaLoaded(pos, 1)) return;

        /*if (plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) && plant.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted())))
        {
            int j = state.getValue(AGE);

            if (rand.nextDouble() < getGrowthRate(worldIn, pos) && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true))
            {
                if (j == 3 && canGrow(worldIn, pos, state, worldIn.isRemote))
                {
                    grow(worldIn, rand, pos, state);
                }
                else if (j < 3)
                {
                    worldIn.setBlockState(pos, state.withProperty(AGE, j + 1).withProperty(PART, getPlantPart(worldIn, pos)));
                }
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
            }
        }
        else if (!plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) || !plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos)))
        {
            int j = state.getValue(AGE);

            if (rand.nextDouble() < getGrowthRate(worldIn, pos) && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true))
            {
                if (j == 0 && canShrink(worldIn, pos))
                {
                    shrink(worldIn, pos);
                }
                else if (j > 0)
                {
                    worldIn.setBlockState(pos, state.withProperty(AGE, j - 1).withProperty(PART, getPlantPart(worldIn, pos)));
                }
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
            }
        }*/

        checkAndDropBlock(worldIn, pos, state);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos) ? this.placeable(worldIn, pos) : false;
        //return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos, worldIn.getBlockState(pos));
    }

    private boolean placeable(World worldIn, BlockPos pos)
    {
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            BlockPos blockpos = pos.offset(enumfacing);
            IBlockState blockstate = worldIn.getBlockState(blockpos);

            if (blockstate.getBlock() == this)
            {
                TESaguaroCactus tile = Helpers.getTE(worldIn, blockpos, TESaguaroCactus.class);
                

                if (blockState.getBlock() == this &&
                    !worldIn.getBlockState(blockpos).getValue(HORIZONTAL)/* && 
                    plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) && 
                    plant.isValidRain(ChunkDataTFC.getRainfall(worldIn, pos))*/)
                {
                    return true;
                }
            }
        }

        Block block2 = worldIn.getBlockState(pos.down()).getBlock();

        return (block2 == this || 
                BlocksTFC.isSand(worldIn.getBlockState(pos.down())) ||
                BlocksTFC.isSoilOrGravel(worldIn.getBlockState(pos.down())) ||
                BlocksTFCF.isSand(worldIn.getBlockState(pos.down())) ||
                BlocksTFCF.isSoilOrGravel(worldIn.getBlockState(pos.down())) ||
                block2 == Blocks.HARDENED_CLAY ||
                block2 == Blocks.STAINED_HARDENED_CLAY) /*&& 
                plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) && 
                plant.isValidRain(ChunkDataTFC.getRainfall(worldIn, pos))*/;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        TESaguaroCactus tile = Helpers.getTE(worldIn, pos, TESaguaroCactus.class);
        if (state.getBlock() instanceof BlockSaguaroCactus && tile.isSet()) state = state.getBlock().getActualState(state, worldIn, pos);

        if (state.getBlock() instanceof BlockSaguaroCactus && state.getValue(HORIZONTAL))
        {
            IBlockState stateHorizontal = worldIn.getBlockState(pos.offset(state.getValue(HORIZONTAL_DIRECTION)));

            if(stateHorizontal.getBlock() instanceof BlockSaguaroCactus)
            {
                TESaguaroCactus tileHorizontal = Helpers.getTE(worldIn, pos.offset(state.getValue(HORIZONTAL_DIRECTION)), TESaguaroCactus.class);
                if (tileHorizontal.isSet()) stateHorizontal = stateHorizontal.getBlock().getActualState(stateHorizontal, worldIn, pos.offset(state.getValue(HORIZONTAL_DIRECTION)));
                
                if(stateHorizontal.getValue(HORIZONTAL)) return false;
                else
                {
                    return true /*&& 
                    plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) && 
                    plant.isValidRain(ChunkDataTFC.getRainfall(worldIn, pos))*/;
                }
            }
            return false;
        }

        Block block2 = worldIn.getBlockState(pos.down()).getBlock();

        return (block2 == this || 
                BlocksTFC.isSand(worldIn.getBlockState(pos.down())) ||
                BlocksTFC.isSoilOrGravel(worldIn.getBlockState(pos.down())) ||
                BlocksTFCF.isSand(worldIn.getBlockState(pos.down())) ||
                BlocksTFCF.isSoilOrGravel(worldIn.getBlockState(pos.down())) ||
                block2 == Blocks.HARDENED_CLAY ||
                block2 == Blocks.STAINED_HARDENED_CLAY) /*&& 
                plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) && 
                plant.isValidRain(ChunkDataTFC.getRainfall(worldIn, pos))*/;
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CACTUS_ROOT;
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TESaguaroCactus tile = Helpers.getTE(worldIn, pos, TESaguaroCactus.class);
        if (tile != null)
        {
            for (EnumFacing face : EnumFacing.values())
            {
                switch (face)
                {
                    case NORTH:
                        state = state.withProperty(NORTH, tile.getFace(face));
                        break;
                    case EAST:
                        state = state.withProperty(EAST, tile.getFace(face));
                        break;
                    case SOUTH:
                        state = state.withProperty(SOUTH, tile.getFace(face));
                        break;
                    case WEST:
                        state = state.withProperty(WEST, tile.getFace(face));
                        break;
                }
            }

            state = state.withProperty(HORIZONTAL, tile.getHorizontal());
            state = state.withProperty(HORIZONTAL_DIRECTION, tile.getFacing());
        }
        return state;
    }

    @Override
    @Nonnull
    protected BlockStateContainer createPlantBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {growthStageProperty, DAYPERIOD, AGE, NORTH, EAST, SOUTH, WEST, HORIZONTAL, HORIZONTAL_DIRECTION});
    }

    private boolean canShrink(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).getBlock() == this && worldIn.getBlockState(pos.up()).getBlock() != this;
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TESaguaroCactus();
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
}
