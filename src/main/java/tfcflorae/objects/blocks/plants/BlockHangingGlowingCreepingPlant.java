package tfcflorae.objects.blocks.plants;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFence;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFC;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.util.skills.SimpleSkill;
import net.dries007.tfc.util.skills.Skill;
import net.dries007.tfc.util.skills.SkillType;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.types.PlantsTFCF;
import tfcflorae.util.OreDictionaryHelper;

@ParametersAreNonnullByDefault
public class BlockHangingGlowingCreepingPlant extends BlockCreepingPlantTFCF implements IGrowable
{
    private static final PropertyBool BOTTOM = PropertyBool.create("bottom");
    private static final Map<Plant, BlockHangingGlowingCreepingPlant> MAP = new HashMap<>();

    public static BlockHangingGlowingCreepingPlant get(Plant plant)
    {
        return BlockHangingGlowingCreepingPlant.MAP.get(plant);
    }

    public BlockHangingGlowingCreepingPlant(Plant plant)
    {
        super(plant);
        if (MAP.put(plant, this) != null) throw new IllegalStateException("There can only be one.");

        plant.getOreDictName().ifPresent(name -> OreDictionaryHelper.register(this, name));
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE) && state.getValue(AGE) >= 3)
            return 14;
        else
            return 0;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos.down(2));
        Material material = iblockstate.getMaterial();

        int i;
        //noinspection StatementWithEmptyBody
        for (i = 1; worldIn.getBlockState(pos.up(i)).getBlock() == this; ++i) ;
        return i < plant.getMaxHeight() && worldIn.isAirBlock(pos.down()) && ((!material.isSolid() || material == Material.LEAVES)) && canBlockStay(worldIn, pos.down(), state);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return false;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        worldIn.setBlockState(pos.down(), this.getDefaultState(), 2);
        IBlockState iblockstate = state.withProperty(AGE, 0).withProperty(growthStageProperty, plant.getStageForMonth()).withProperty(BOTTOM, false);
        worldIn.setBlockState(pos, iblockstate, 2);
        iblockstate.neighborChanged(worldIn, pos.down(), this, pos);
    }

    public void shrink(World worldIn, BlockPos pos)
    {
        worldIn.setBlockToAir(pos);
        worldIn.getBlockState(pos).neighborChanged(worldIn, pos.up(), this, pos);
    }

    @Override
    @Nonnull
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        IBlockState actualState = super.getActualState(state, worldIn, pos);
        if (worldIn.getBlockState(pos.down()).getBlock() == this && actualState.getValue(UP) && !actualState.getValue(DOWN) && !actualState.getValue(NORTH) && !actualState.getValue(SOUTH) && !actualState.getValue(EAST) && !actualState.getValue(WEST))
        {
            actualState = actualState.withProperty(NORTH, true).withProperty(SOUTH, true).withProperty(EAST, true).withProperty(WEST, true);
        }
        if (worldIn.getBlockState(pos.up()).getBlock() == this && !actualState.getValue(UP) && !actualState.getValue(NORTH) && !actualState.getValue(SOUTH) && !actualState.getValue(EAST) && !actualState.getValue(WEST))
        {
            if (!actualState.getValue(DOWN))
            {
                actualState = actualState.getActualState(worldIn, pos.up()).withProperty(UP, false);
            }
            else
            {
                actualState = actualState.getActualState(worldIn, pos.up()).withProperty(DOWN, true).withProperty(UP, false);
            }
        }
        return actualState.withProperty(BOTTOM, getIsBottom(worldIn, pos));
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos, worldIn.getBlockState(pos));
        //return plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) && plant.isValidRain(ChunkDataTFC.getRainfall(worldIn, pos));
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        for (EnumFacing face : EnumFacing.values())
        {
            IBlockState blockState = worldIn.getBlockState(pos.offset(face));
            Material material = blockState.getMaterial();

            if (material == Material.LEAVES || material == Material.GROUND || material == Material.ROCK || material == Material.WOOD || BlocksTFC.isGround(blockState) || worldIn.getBlockState(pos.up()).getBlock() == this)
            {
                return plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) && plant.isValidRain(ChunkDataTFC.getRainfall(worldIn, pos));
            }
        }
        return false;
    }

    @Override
    @Nonnull
    protected BlockStateContainer createPlantBlockState()
    {
        return new BlockStateContainer(this, DOWN, UP, NORTH, EAST, WEST, SOUTH, growthStageProperty, DAYPERIOD, AGE, BOTTOM);
    }

    @Override
    protected boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing facing)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Material material = iblockstate.getMaterial();

        return material == Material.LEAVES || material == Material.GROUND || material == Material.ROCK || material == Material.WOOD || BlocksTFC.isGround(iblockstate);
    }

    @Override
    protected boolean canPlantConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        if (!super.canPlantConnectTo(world, pos, facing) && world.getBlockState(pos.up()).getBlock() == this && facing != EnumFacing.DOWN && facing != EnumFacing.UP)
        {
            return canPlantConnectTo(world, pos.up(), facing);
        }

        return super.canPlantConnectTo(world, pos, facing);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isAreaLoaded(pos, 1)) return;

        if (plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) && plant.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted())))
        {
            int j = state.getValue(AGE);

            if (rand.nextDouble() < getGrowthRate(worldIn, pos) && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos.down(), state, true))
            {
                if (j == 3)
                {
                    if (canGrow(worldIn, pos, state, worldIn.isRemote)) grow(worldIn, rand, pos, state);
                    else if (canGrowHorizontally(worldIn, pos, state)) growHorizontally(worldIn, rand, pos, state);
                    else if (canGrowDiagonally(worldIn, pos, state)) growDiagonally(worldIn, rand, pos, state);
                }
                else if (j < 3)
                {
                    worldIn.setBlockState(pos, state.withProperty(AGE, j + 1).withProperty(BOTTOM, getIsBottom(worldIn, pos)), 2);
                }
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
            }
        }
        else if (!plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) || !plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos)))
        {
            int j = state.getValue(AGE);

            if (rand.nextDouble() < getGrowthRate(worldIn, pos) && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true))
            {
                if (j == 0)
                {
                    if (canShrink(worldIn, pos)) shrink(worldIn, pos);
                    else if (canShrinkHorizontally(worldIn, pos)) shrinkHorizontally(worldIn, pos);
                }
                else if (j > 0)
                {
                    worldIn.setBlockState(pos, state.withProperty(AGE, j - 1).withProperty(BOTTOM, getIsBottom(worldIn, pos)), 2);
                }
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
            }
        }

        checkAndDropBlock(worldIn, pos, state);
    }

    private boolean canGrowDiagonally(World worldIn, BlockPos pos, IBlockState state)
    {
        boolean flag = false;
        if (!state.getValue(BOTTOM))
        {
            for (EnumFacing face : EnumFacing.Plane.HORIZONTAL.facings())
            {
                BlockPos sidePos = pos.offset(face);
                IBlockState sideState = worldIn.getBlockState(sidePos.down(2));
                Material sideMaterial = sideState.getMaterial();

                if (worldIn.isAirBlock(sidePos) && worldIn.isAirBlock(sidePos.down()) && (!sideMaterial.isSolid() || sideMaterial == Material.LEAVES || sideMaterial == Material.GROUND || sideMaterial == Material.ROCK || sideMaterial == Material.WOOD || BlocksTFC.isGround(sideState)) && canBlockStay(worldIn, sidePos.down(), state))
                {
                    flag = true;
                }
            }
        }
        return flag;
    }

    private void growDiagonally(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        if (!state.getValue(BOTTOM))
        {
            for (EnumFacing face : EnumFacing.Plane.HORIZONTAL.facings())
            {
                BlockPos sidePos = pos.offset(face);

                if (rand.nextDouble() < 0.5D && worldIn.isAirBlock(sidePos) && worldIn.isAirBlock(sidePos.down()))
                {
                    worldIn.setBlockState(sidePos.down(), this.getDefaultState(), 2);
                    IBlockState iblockstate = state.withProperty(AGE, 0).withProperty(growthStageProperty, plant.getStageForMonth());
                    worldIn.setBlockState(pos, iblockstate, 2);
                    iblockstate.neighborChanged(worldIn, sidePos.down(), this, pos);
                    break;
                }
            }
        }
    }

    private boolean canGrowHorizontally(World worldIn, BlockPos pos, IBlockState state)
    {
        boolean flag = false;
        for (EnumFacing face : EnumFacing.Plane.HORIZONTAL.facings())
        {
            BlockPos sidePos = pos.offset(face);
            IBlockState sideState = worldIn.getBlockState(sidePos.down());
            Material sideMaterial = sideState.getMaterial();

            if (worldIn.isAirBlock(sidePos) && (!sideMaterial.isSolid() || sideMaterial == Material.LEAVES || sideMaterial == Material.GROUND || sideMaterial == Material.ROCK || sideMaterial == Material.WOOD || BlocksTFC.isGround(sideState)) && canBlockStay(worldIn, sidePos, state))
            {
                flag = true;
            }
        }
        return flag;
    }

    private void growHorizontally(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        for (EnumFacing face : EnumFacing.Plane.HORIZONTAL.facings())
        {
            BlockPos sidePos = pos.offset(face);

            if (rand.nextDouble() < 0.01D && worldIn.isAirBlock(sidePos))
            {
                worldIn.setBlockState(sidePos, this.getDefaultState(), 2);
                IBlockState iblockstate = state.withProperty(AGE, 0).withProperty(growthStageProperty, plant.getStageForMonth());
                worldIn.setBlockState(pos, iblockstate, 2);
                iblockstate.neighborChanged(worldIn, sidePos, this, pos);
                break;
            }
        }
    }

    private void shrinkHorizontally(World worldIn, BlockPos pos)
    {
        worldIn.setBlockToAir(pos);
        IBlockState state = worldIn.getBlockState(pos);
        state.neighborChanged(worldIn, pos.east(), this, pos);
        state.neighborChanged(worldIn, pos.west(), this, pos);
        state.neighborChanged(worldIn, pos.north(), this, pos);
        state.neighborChanged(worldIn, pos.south(), this, pos);
    }

    private boolean canShrink(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.up()).getBlock() == this && worldIn.getBlockState(pos.down()).getBlock() != this;
    }

    private boolean canShrinkHorizontally(World worldIn, BlockPos pos)
    {
        boolean flag = false;
        for (EnumFacing face : EnumFacing.Plane.HORIZONTAL.facings())
        {
            if (worldIn.getBlockState(pos.offset(face)).getBlock() == this)
            {
                flag = true;
            }
        }
        return flag;
    }

    private boolean getIsBottom(IBlockAccess world, BlockPos pos)
    {
        IBlockState iblockstate = world.getBlockState(pos.down());
        Material material = iblockstate.getMaterial();

        return world.getBlockState(pos.down()).getBlock() != this && !material.isSolid();
    }

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!this.canBlockStay(worldIn, pos, state))
        {
            worldIn.destroyBlock(pos, false);
        }
	}

    @Override
    public void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.canBlockStay(worldIn, pos, state))
        {
            worldIn.destroyBlock(pos, false);
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote)
        {
            if (stack.getItem() == Items.SHEARS || stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1 || stack.getItem().getHarvestLevel(stack, "scythe", player, state) != -1)
            {
                spawnAsEntity(worldIn, pos, new ItemStack(this, 1));
            }
        }
    }

    public static int getSkillFoodBonus(Skill skill, Random random)
    {
        return random.nextInt(2 + (int) (6 * skill.getTotalLevel()));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (playerIn != null)
        {
            SimpleSkill skill = CapabilityPlayerData.getSkill(playerIn, SkillType.AGRICULTURE);

            if (skill != null && worldIn.getBlockState(pos).getValue(AGE) >= 3 && plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE))
            {
                if (!worldIn.isRemote)
                {
                    ItemHandlerHelper.giveItemToPlayer(playerIn, new ItemStack(ItemsTFCF.GLOWBERRY, 1 + BlockHangingGlowingPlant.getSkillFoodBonus(skill, RANDOM)));
                    worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(AGE, 0), 2);
                    TETickCounter te = Helpers.getTE(worldIn, pos, TETickCounter.class);
                    if (te != null)
                    {
                        te.resetCounter();
                    }
                }
                return true;
            }
        }
        return false;
    }
}
