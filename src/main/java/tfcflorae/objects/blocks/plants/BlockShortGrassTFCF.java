package tfcflorae.objects.blocks.plants;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import net.dries007.tfc.Constants;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateTFC;

import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.plants.BlockPlant.BlockPlantTFCF;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.types.PlantsTFCF;

@ParametersAreNonnullByDefault
public class BlockShortGrassTFCF extends BlockPlantTFCF implements IShearable
{
    private static final AxisAlignedBB GRASS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
    private static final AxisAlignedBB SHORTER_GRASS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.5D, 0.875D);
    private static final AxisAlignedBB SHORT_GRASS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.75D, 0.875D);
    private static final AxisAlignedBB SHORTEST_GRASS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.25D, 0.875D);
    private static final Map<Plant, BlockShortGrassTFCF> MAP = new HashMap<>();

    public static BlockShortGrassTFCF get(Plant plant)
    {
        return BlockShortGrassTFCF.MAP.get(plant);
    }

    public BlockShortGrassTFCF(Plant plant)
    {
        super(plant);
        if (MAP.put(plant, this) != null) throw new IllegalStateException("There can only be one.");
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        Month currentMonth = CalendarTFC.CALENDAR_TIME.getMonthOfYear();
        int currentStage = state.getValue(growthStageProperty);
        int expectedStage = plant.getStageForMonth(currentMonth);
        int age = state.getValue(AGE);

        if (!worldIn.isRemote)
        {
            if (stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1 || stack.getItem().getHarvestLevel(stack, "scythe", player, state) != -1)
            {
                /*if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_BARLEY))
                {
                    if (age == 3 && (currentStage == 1 || expectedStage == 1))
                    {
                        spawnAsEntity(worldIn, pos, new ItemStack(ItemsTFCF.WILD_BARLEY, 1 + Constants.RNG.nextInt(2)));
                        spawnAsEntity(worldIn, pos, new ItemStack(ItemSeedsTFC.get(Crop.BARLEY), Constants.RNG.nextInt(2)));
                    }
                    else
                    {
                        if (Constants.RNG.nextDouble() <= (age + 1) / 4.0D) //+25% change for each age
                        {
                            spawnAsEntity(worldIn, pos, new ItemStack(ItemsTFC.STRAW, 1));
                            int chance = Constants.RNG.nextInt(2);
                            if (chance == 0)
                            {
                                spawnAsEntity(worldIn, pos, new ItemStack(ItemSeedsTFC.get(Crop.BARLEY), 1));
                            }
                        }
                    }
                }
                else if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_WHEAT))
                {
                    if (age == 3 && (currentStage == 1 || expectedStage == 1))
                    {
                        spawnAsEntity(worldIn, pos, new ItemStack(ItemsTFCF.WILD_WHEAT, 1 + Constants.RNG.nextInt(2)));
                        spawnAsEntity(worldIn, pos, new ItemStack(ItemSeedsTFC.get(Crop.WHEAT), Constants.RNG.nextInt(2)));
                    }
                    else
                    {
                        if (Constants.RNG.nextDouble() <= (age + 1) / 4.0D) //+25% change for each age
                        {
                            spawnAsEntity(worldIn, pos, new ItemStack(ItemsTFC.STRAW, 1));
                            int chance = Constants.RNG.nextInt(2);
                            if (chance == 0)
                            {
                                spawnAsEntity(worldIn, pos, new ItemStack(ItemSeedsTFC.get(Crop.WHEAT), 1));
                            }
                        }
                    }
                }
                else if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_RICE))
                {
                    if (age == 3 && (currentStage == 1 || expectedStage == 1))
                    {
                        spawnAsEntity(worldIn, pos, new ItemStack(ItemsTFCF.WILD_RICE, 1 + Constants.RNG.nextInt(2)));
                        spawnAsEntity(worldIn, pos, new ItemStack(ItemSeedsTFC.get(Crop.RICE), Constants.RNG.nextInt(2)));
                    }
                    else
                    {
                        if (Constants.RNG.nextDouble() <= (age + 1) / 4.0D) //+25% change for each age
                        {
                            spawnAsEntity(worldIn, pos, new ItemStack(ItemsTFC.STRAW, 1));
                            int chance = Constants.RNG.nextInt(2);
                            if (chance == 0)
                            {
                                spawnAsEntity(worldIn, pos, new ItemStack(ItemSeedsTFC.get(Crop.RICE), 1));
                            }
                        }
                    }
                }
                else*/
                {
                    if (Constants.RNG.nextDouble() <= (age + 1) / 4.0D) //+25% change for each age
                    {
                        spawnAsEntity(worldIn, pos, new ItemStack(ItemsTFC.STRAW, 1));
                    }
                }
            }
            else if (stack.getItem() == Items.SHEARS)
            {
                spawnAsEntity(worldIn, pos, new ItemStack(this, 1));
            }
        }
        //super.harvestBlock(worldIn, player, pos, state, te, stack);
    }

    @Override
    @Nonnull
    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XZ;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isAreaLoaded(pos, 1)) return;

        if (plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) && plant.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted())))
        {
            int j = state.getValue(AGE);

            if (rand.nextDouble() < getGrowthRate(worldIn, pos) && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true))
            {
                if (j < 3)
                {
                    worldIn.setBlockState(pos, state.withProperty(AGE, j + 1));
                }
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
            }
        }
        else if (!plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) || !plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos)))
        {
            int j = state.getValue(AGE);

            if (rand.nextDouble() < getGrowthRate(worldIn, pos) && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true))
            {
                if (j > 0)
                {
                    worldIn.setBlockState(pos, state.withProperty(AGE, j - 1));
                }
                else
                {
                    worldIn.setBlockToAir(pos);
                }
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
            }
        }

        checkAndDropBlock(worldIn, pos, state);
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (state.getValue(AGE))
        {
            case 0:
                return SHORTEST_GRASS_AABB.offset(state.getOffset(source, pos));
            case 1:
                return SHORTER_GRASS_AABB.offset(state.getOffset(source, pos));
            case 2:
                return SHORT_GRASS_AABB.offset(state.getOffset(source, pos));
            default:
                return GRASS_AABB.offset(state.getOffset(source, pos));
        }
    }

    @Override
    @Nonnull
    protected BlockStateContainer createPlantBlockState()
    {
        return new BlockStateContainer(this, AGE, growthStageProperty, DAYPERIOD);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return 1 + random.nextInt(fortune * 2 + 1);
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1);
    }

    @Override
    @Nonnull
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1);
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    @Override
    @Nonnull
    public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        return NonNullList.withSize(1, new ItemStack(this, 1));
    }
}