package tfcflorae.objects.blocks.plants;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.dries007.tfc.objects.blocks.property.ITallPlant;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.api.types.PlantTFCF;

@ParametersAreNonnullByDefault
public class BlockTallPlantTFCF extends BlockPlantTFCF implements IGrowable, ITallPlant
{
    private static final PropertyEnum<EnumBlockPart> PART = PropertyEnum.create("part", EnumBlockPart.class);
    private static final Map<PlantTFCF, BlockTallPlantTFCF> MAP = new HashMap<>();

    public static BlockTallPlantTFCF get(PlantTFCF plantTFCF)
    {
        return BlockTallPlantTFCF.MAP.get(plantTFCF);
    }

    public BlockTallPlantTFCF(PlantTFCF plantTFCF)
    {
        super(plantTFCF);
        if (MAP.put(plantTFCF, this) != null) throw new IllegalStateException("There can only be one.");
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        int i;
        //noinspection StatementWithEmptyBody
        for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i) ;
        return i < plantTFCF.getMaxHeight() && worldIn.isAirBlock(pos.up()) && canBlockStay(worldIn, pos.up(), state);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return false;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        worldIn.setBlockState(pos.up(), this.getDefaultState());
        IBlockState iblockstate = state.withProperty(AGE, 0).withProperty(growthStageProperty, plantTFCF.getStageForMonth()).withProperty(PART, getPlantPart(worldIn, pos));
        worldIn.setBlockState(pos, iblockstate);
        iblockstate.neighborChanged(worldIn, pos.up(), this, pos);
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
        IBlockState plantTFCF = plantable.getPlant(world, pos.offset(direction));

        if (plantTFCF.getBlock() == this)
        {
            return true;
        }
        return super.canSustainPlant(state, world, pos, direction, plantable);
    }

    @Override
    @Nonnull
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return super.getActualState(state, worldIn, pos).withProperty(PART, getPlantPart(worldIn, pos));
    }

    @Override
    @Nonnull
    public Block.EnumOffsetType getOffsetType()
    {
        return EnumOffsetType.XYZ;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos, worldIn.getBlockState(pos));
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isAreaLoaded(pos, 1)) return;

        if (plantTFCF.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) && plantTFCF.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted())))
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
        else if (!plantTFCF.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) || !plantTFCF.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos)))
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
        }

        checkAndDropBlock(worldIn, pos, state);
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        IBlockState soil = worldIn.getBlockState(pos.down());

        if (worldIn.getBlockState(pos.down(plantTFCF.getMaxHeight())).getBlock() == this) return false;
        if (state.getBlock() == this)
        {
            return soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this) && plantTFCF.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) && plantTFCF.isValidRain(ChunkDataTFC.getRainfall(worldIn, pos));
        }
        return this.canSustainBush(soil);
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return getTallBoundingBax(state.getValue(AGE), state, source, pos);
    }

    @Override
    @Nonnull
    protected BlockStateContainer createPlantBlockState()
    {
        return new BlockStateContainer(this, AGE, growthStageProperty, PART, DAYPERIOD);
    }

    private boolean canShrink(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).getBlock() == this && worldIn.getBlockState(pos.up()).getBlock() != this;
    }
}
