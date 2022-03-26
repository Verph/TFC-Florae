package tfcflorae.objects.blocks.wood;

import java.util.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import mcp.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.player.IPlayerData;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.api.util.IGrowingPlant;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.util.skills.SimpleSkill;
import net.dries007.tfc.util.skills.SkillType;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.util.agriculture.SeasonalTrees;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockJoshuaTreeFlower extends Block implements IGrowingPlant
{
    public static final PropertyEnum<EnumLeafState> LEAF_STATE = PropertyEnum.create("state", BlockJoshuaTreeFlower.EnumLeafState.class);
    public static final PropertyBool HARVESTABLE = PropertyBool.create("harvestable");
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 5);
    private static final Map<Tree, BlockJoshuaTreeFlower> MAP = new HashMap<>();

    public static BlockJoshuaTreeFlower get(Tree wood)
    {
        return MAP.get(wood);
    }

    private final Tree wood;
    public final SeasonalTrees fruitTree;

    public BlockJoshuaTreeFlower(Tree wood, SeasonalTrees tree)
    {
        super(Material.LEAVES);
        this.wood = wood;
        this.fruitTree = tree;
        if (MAP.put(wood, this) != null) throw new IllegalStateException("There can only be one.");

        this.setDefaultState(this.blockState.getBaseState().withProperty(LEAF_STATE, EnumLeafState.NORMAL).withProperty(HARVESTABLE, true).withProperty(AGE, Integer.valueOf(0)));
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        this.setSoundType(SoundType.PLANT);
        OreDictionaryHelper.register(this, "tree", "leaves");
        OreDictionaryHelper.register(this, "tree", "leaves", wood.getRegistryName().getPath());

        Blocks.FIRE.setFireInfo(this, 30, 60);
        this.setTickRandomly(true);
    }

    public Tree getWood()
    {
        return wood;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!this.canSurvive(worldIn, pos))
        {
            worldIn.destroyBlock(pos, true);
        }
        else
        {
            BlockPos blockpos = pos.up();

            if (worldIn.isAirBlock(blockpos) && blockpos.getY() < 256)
            {
                int i = ((Integer)state.getValue(AGE)).intValue();

                if (i < 5 &&  net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, blockpos, state, rand.nextInt(1) == 0))
                {
                    boolean flag = false;
                    boolean flag1 = false;
                    IBlockState iblockstate = worldIn.getBlockState(pos.down());
                    Block block = iblockstate.getBlock();

                    if (BlocksTFC.isSand(iblockstate) || BlocksTFC.isSoilOrGravel(iblockstate) || BlocksTFCF.isSand(iblockstate) || BlocksTFCF.isSoilOrGravel(iblockstate) || iblockstate == Blocks.HARDENED_CLAY || iblockstate == Blocks.STAINED_HARDENED_CLAY)
                    {
                        flag = true;
                    }
                    else if (block == BlockJoshuaTreeLog.get(wood))
                    {
                        int j = 1;

                        for (int k = 0; k < 4; ++k)
                        {
                            Block block1 = worldIn.getBlockState(pos.down(j + 1)).getBlock();

                            if (block1 != BlockJoshuaTreeLog.get(wood))
                            {
                                if (BlocksTFC.isSand(worldIn.getBlockState(pos.down(j + 1))) || BlocksTFC.isSoilOrGravel(worldIn.getBlockState(pos.down(j + 1))) || BlocksTFCF.isSand(worldIn.getBlockState(pos.down(j + 1))) || BlocksTFCF.isSoilOrGravel(worldIn.getBlockState(pos.down(j + 1))) || block1 == Blocks.HARDENED_CLAY || block1 == Blocks.STAINED_HARDENED_CLAY)
                                {
                                    flag1 = true;
                                }

                                break;
                            }

                            ++j;
                        }

                        int growthMultiplier = rand.nextInt(2);
                        int i1 = 2;

                        if (flag1)
                        {
                            if (j < 2 || rand.nextInt(i1 + growthMultiplier) >= j)
                            {
                                flag = true;
                            }
                        }
                        else
                        {
                            if ((rand.nextInt(i1) - rand.nextInt(i1)) >= j)
                            {
                                flag = true;
                            }
                        }
                    }
                    else if (iblockstate.getMaterial() == Material.AIR)
                    {
                        flag = true;
                    }

                    if (flag && areAllNeighborsEmpty(worldIn, blockpos, (EnumFacing)null) && worldIn.isAirBlock(pos.up(2)))
                    {
                        worldIn.setBlockState(pos, BlockJoshuaTreeLog.get(wood).getDefaultState(), 2);
                        this.placeGrownFlower(worldIn, blockpos, i);
                    }
                    else if (i < 4)
                    {
                        int l = 0;
                        if (i < 2)
                        {
                            l =  2 + rand.nextInt(4);
                        }
                        else
                        {
                            l = 2 + rand.nextInt(8);
                        }
                        boolean flag2 = false;

                        if (flag1)
                        {
                            ++l;
                        }

                        for (int j1 = 0; j1 < l; ++j1)
                        {
                            EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
                            BlockPos blockpos1 = pos.offset(enumfacing);

                            if (worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.down()) && areAllNeighborsEmpty(worldIn, blockpos1, enumfacing.getOpposite()))
                            {
                                this.placeGrownFlower(worldIn, blockpos1, i + 1);
                                flag2 = true;
                            }
                        }

                        if (flag2)
                        {
                            worldIn.setBlockState(pos, BlockJoshuaTreeLog.get(wood).getDefaultState(), 2);
                        }
                        else
                        {
                            this.placeDeadFlower(worldIn, pos);
                        }
                    }
                    else if (i == 4)
                    {
                        this.placeDeadFlower(worldIn, pos);
                    }
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            }
        }
    }

    public void generatePlant(World worldIn, BlockPos pos, Random rand, int p_185603_3_)
    {
        worldIn.setBlockState(pos, BlockJoshuaTreeLog.get(wood).getDefaultState(), 2);
        //growTreeRecursive(worldIn, pos, rand, p_185603_3_, 0);
        growTreeRecursive(worldIn, pos, rand, 0);
    }

    private void growTreeRecursive(World worldIn, BlockPos currentBlock, Random rand, int age)
    {
        int status = 0;
        BlockPos pos = currentBlock;
        status = GrowStep(worldIn, pos, rand, age);
        while (status == 0)
        {
            pos = pos.up();
            status = GrowStep(worldIn, pos, rand, age);
        }

        /*boolean flag = false;

        if (age < 4)
        {
            int l = 0;
            if (age < 2)
            {
                l =  2 + rand.nextInt(4);
            }
            else
            {
                l = 2 + rand.nextInt(8);
            }

            if (age == 0)
            {
                ++l;
            }

            //Grow out
            for (int k = 0; k < l; ++k)
            {
                EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
                BlockPos blockpos1 = currentBlock.up(growthLength).offset(enumfacing);

                if (Math.abs(blockpos1.getX() - baseBlock.getX()) < distFromCenter && Math.abs(blockpos1.getZ() - baseBlock.getZ()) < distFromCenter && worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.down()) && areAllNeighborsEmpty(worldIn, blockpos1, enumfacing.getOpposite()))
                {
                    flag = true;
                    worldIn.setBlockState(blockpos1, BlockJoshuaTreeLog.get(wood).getDefaultState(), 2);
                    growTreeRecursive(worldIn, blockpos1, rand, baseBlock, distFromCenter, age + 1);
                }
            }
        }*/
    }

    public int GrowStep(World worldIn, BlockPos currentBlock, Random rand, int age)
    {
        BlockPos blockpos = currentBlock.up();

        if (worldIn.isAirBlock(blockpos) && blockpos.getY() < 256)
        {
            if (age < 5)
            {
                boolean flag = false;
                boolean flag1 = false;
                IBlockState iblockstate = worldIn.getBlockState(currentBlock.down());
                Block block = iblockstate.getBlock();

                if (BlocksTFC.isSand(iblockstate) || BlocksTFC.isSoilOrGravel(iblockstate) || BlocksTFCF.isSand(iblockstate) || BlocksTFCF.isSoilOrGravel(iblockstate) || block == Blocks.HARDENED_CLAY || block == Blocks.STAINED_HARDENED_CLAY)
                {
                    flag = true;
                }
                else if (block == BlockJoshuaTreeLog.get(wood))
                {
                    int j = 1;

                    for (int k = 0; k < 4; ++k)
                    {
                        Block block1 = worldIn.getBlockState(currentBlock.down(j + 1)).getBlock();

                        if (block1 != BlockJoshuaTreeLog.get(wood))
                        {
                            if (BlocksTFC.isSand(worldIn.getBlockState(currentBlock.down(j + 1))) || BlocksTFC.isSoilOrGravel(worldIn.getBlockState(currentBlock.down(j + 1))) || BlocksTFCF.isSand(worldIn.getBlockState(currentBlock.down(j + 1))) || BlocksTFCF.isSoilOrGravel(worldIn.getBlockState(currentBlock.down(j + 1))) || block1 == Blocks.HARDENED_CLAY || block1 == Blocks.STAINED_HARDENED_CLAY)
                            {
                                flag1 = true;
                            }

                            break;
                        }

                        ++j;
                    }

                    int growthMultiplier = rand.nextInt(2);
                    int i1 = 2;

                    if (flag1)
                    {
                        if (j < 2 || rand.nextInt(i1 + growthMultiplier) >= j)
                        {
                            flag = true;
                        }
                    }
                    else
                    {
                        if ((rand.nextInt(i1) - rand.nextInt(i1)) >= j)
                        {
                            flag = true;
                        }
                    }
                }
                else if (iblockstate.getMaterial() == Material.AIR)
                {
                    flag = true;
                }

                if (flag && areAllNeighborsEmpty(worldIn, blockpos, (EnumFacing)null) && worldIn.isAirBlock(currentBlock.up(2)))
                {
                    worldIn.setBlockState(currentBlock, BlockJoshuaTreeLog.get(wood).getDefaultState(), 2);
                    worldIn.setBlockState(blockpos, BlockJoshuaTreeFlower.get(wood).getDefaultState().withProperty(AGE, Integer.valueOf(5)), 2);
                    return 0;
                }
                else if (age < 4)
                {
                    int l = 0;
                    if (age < 2)
                    {
                        l =  2 + rand.nextInt(4);
                    }
                    else
                    {
                        l = 2 + rand.nextInt(8);
                    }
                    boolean flag2 = false;

                    if (flag1)
                    {
                        ++l;
                    }

                    for (int j1 = 0; j1 < l; ++j1)
                    {
                        EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
                        BlockPos blockpos1 = currentBlock.offset(enumfacing);

                        if (worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.down()) && areAllNeighborsEmpty(worldIn, blockpos1, enumfacing.getOpposite()))
                        {
                            //this.placeGrownFlower(worldIn, blockpos1, age + 1);
                            worldIn.setBlockState(currentBlock, BlockJoshuaTreeLog.get(wood).getDefaultState(), 2);
                            worldIn.setBlockState(blockpos1, BlockJoshuaTreeLog.get(wood).getDefaultState(), 2);
                            worldIn.setBlockState(blockpos1.up(), BlockJoshuaTreeFlower.get(wood).getDefaultState().withProperty(AGE, Integer.valueOf(5)), 2);
                            //this.placeGrownFlower(worldIn, blockpos1.up(), age + 1);
                            growTreeRecursive(worldIn, blockpos1.up(), rand, MathHelper.clamp(age + rand.nextInt(5 - age) + 1, 0, 4));
                            flag2 = true;
                        }
                    }

                    if (flag2)
                    {
                        return 1;
                    }
                    else
                    {
                        //worldIn.setBlockState(blockpos, BlockJoshuaTreeLog.get(wood).getDefaultState(), 2);
                        worldIn.setBlockState(currentBlock, BlockJoshuaTreeFlower.get(wood).getDefaultState().withProperty(AGE, Integer.valueOf(5)), 2);
                        return 2;
                    }
                }
                else if (age == 4)
                {
                    worldIn.setBlockState(currentBlock, BlockJoshuaTreeFlower.get(wood).getDefaultState().withProperty(AGE, Integer.valueOf(5)), 2);
                    return 2;
                    //this.placeDeadFlower(worldIn, pos);
                }
            }
        }
        return -1;
    }

    /*private void growTreeRecursive(World worldIn, BlockPos currentBlock, Random rand, BlockPos baseBlock, int distFromCenter, int age)
    {
        boolean flag1 = false;
        int distToGround = 0;

        for (int k = 0; k < 4; ++k)
        {
            Block block1 = worldIn.getBlockState(currentBlock.down(distToGround + 1)).getBlock();

            if (block1 != BlockJoshuaTreeLog.get(wood))
            {
                if (BlocksTFC.isSand(worldIn.getBlockState(currentBlock.down(distToGround + 1))) || BlocksTFC.isSoilOrGravel(worldIn.getBlockState(currentBlock.down(distToGround + 1))) || BlocksTFCF.isSand(worldIn.getBlockState(currentBlock.down(distToGround + 1))) || BlocksTFCF.isSoilOrGravel(worldIn.getBlockState(currentBlock.down(distToGround + 1))))
                {
                    flag1 = true;
                }

                break;
            }

            ++distToGround;
        }

        //int i = rand.nextInt(3) + rand.nextInt(2);
        int i = rand.nextInt(4) + 1;

        if (age == 0)
        {
            ++i;
        }

        int growthLength = 0;

        if (flag1)
        {
            for (int j = 0; j < distToGround; j++)
            {
                if (distToGround < 2 || rand.nextInt(i) >= distToGround)
                {
                    growthLength++;
                }
            }
        }
        else
        {
            for (int j = 0; j < distToGround; j++)
            {
                if ((rand.nextInt(i) - rand.nextInt(i)) >= distToGround)
                {
                    growthLength++;
                }
            }
        }



        //Grow up
        for (int j = 0; j < growthLength; ++j)
        {
            BlockPos blockpos = currentBlock.up(j + 1);

            if (!areAllNeighborsEmpty(worldIn, blockpos, (EnumFacing)null))
            {
                return;
            }

            worldIn.setBlockState(blockpos, BlockJoshuaTreeLog.get(wood).getDefaultState(), 2);
        }

        boolean flag = false;

        if (age < 4)
        {
            int l = 0;
            if (age < 2)
            {
                l =  2 + rand.nextInt(4);
            }
            else
            {
                l = 2 + rand.nextInt(8);
            }

            if (age == 0)
            {
                ++l;
            }

            //Grow out
            for (int k = 0; k < l; ++k)
            {
                EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
                BlockPos blockpos1 = currentBlock.up(growthLength).offset(enumfacing);

                if (Math.abs(blockpos1.getX() - baseBlock.getX()) < distFromCenter && Math.abs(blockpos1.getZ() - baseBlock.getZ()) < distFromCenter && worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.down()) && areAllNeighborsEmpty(worldIn, blockpos1, enumfacing.getOpposite()))
                {
                    flag = true;
                    worldIn.setBlockState(blockpos1, BlockJoshuaTreeLog.get(wood).getDefaultState(), 2);
                    growTreeRecursive(worldIn, blockpos1, rand, baseBlock, distFromCenter, age + 1);
                }
            }
        }

        if (!flag)
        {
            worldIn.setBlockState(currentBlock.up(i), BlockJoshuaTreeFlower.get(wood).getDefaultState().withProperty(AGE, Integer.valueOf(5)), 2);
        }
    }*/

    @Override
    public int tickRate(World worldIn)
    {
        return 10;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        TETickCounter tile = Helpers.getTE(worldIn, pos, TETickCounter.class);
        if (tile != null)
        {
            tile.resetCounter();
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (playerIn != null)
        {
            SimpleSkill skill = CapabilityPlayerData.getSkill(playerIn, SkillType.AGRICULTURE);

            if (skill != null && worldIn.getBlockState(pos).getValue(LEAF_STATE) == EnumLeafState.FRUIT && fruitTree.getDrop() != null)
            {
                if (!worldIn.isRemote)
                {
                    ItemHandlerHelper.giveItemToPlayer(playerIn, new ItemStack(fruitTree.getFoodDrop(), 1 + BlockLeavesTFCF.getSkillFoodBonus(skill, RANDOM)));
                    worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(LEAF_STATE, EnumLeafState.NORMAL));
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

    private void placeGrownFlower(World worldIn, BlockPos pos, int age)
    {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(age)), 2);
        worldIn.playEvent(1033, pos, 0);
    }

    private void placeDeadFlower(World worldIn, BlockPos pos)
    {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(5)), 2);
        worldIn.playEvent(1034, pos, 0);
    }

    private static boolean areAllNeighborsEmpty(World worldIn, BlockPos pos, EnumFacing excludingSide)
    {
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            if (enumfacing != excludingSide && !worldIn.isAirBlock(pos.offset(enumfacing)))
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    /**
     * Checks if this block can be placed exactly at the given position.
     */
    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        return ((super.canPlaceBlockAt(worldIn, pos) && this.canSurvive(worldIn, pos)) || (BlocksTFC.isSand(worldIn.getBlockState(pos.down())) || BlocksTFC.isSoilOrGravel(worldIn.getBlockState(pos.down())) || BlocksTFCF.isSand(worldIn.getBlockState(pos.down())) || BlocksTFCF.isSoilOrGravel(worldIn.getBlockState(pos.down())) || block == Blocks.HARDENED_CLAY || block == Blocks.STAINED_HARDENED_CLAY));
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!this.canSurvive(worldIn, pos))
        {
            worldIn.scheduleUpdate(pos, this, 1);
        }
    }

    public boolean canSurvive(World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos.down());
        Block block = iblockstate.getBlock();

        if (block != BlockJoshuaTreeLog.get(wood) && !(BlocksTFC.isSand(iblockstate) || BlocksTFC.isSoilOrGravel(iblockstate) || BlocksTFCF.isSand(iblockstate) || BlocksTFCF.isSoilOrGravel(iblockstate) || iblockstate == Blocks.HARDENED_CLAY || iblockstate == Blocks.STAINED_HARDENED_CLAY))
        {
            if (iblockstate.getMaterial() == Material.AIR)
            {
                int i = 0;

                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
                {
                    IBlockState iblockstate1 = worldIn.getBlockState(pos.offset(enumfacing));
                    Block block1 = iblockstate1.getBlock();

                    if (block1 == BlockJoshuaTreeLog.get(wood))
                    {
                        ++i;
                    }
                    else if (iblockstate1.getMaterial() != Material.AIR)
                    {
                        return false;
                    }
                }

                return i == 1;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    /*public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this)));
    }*/

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        // Do this check again, so we can drop items now
        final Set<String> toolClasses = stack.getItem().getToolClasses(stack);
        if (toolClasses.contains("axe") || toolClasses.contains("saw"))
        {
            // Harvest the block normally, saws and axes are valid tools regardless
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
        else if (toolClasses.contains("hammer") && ConfigTFC.General.TREE.enableHammerSticks)
        {
            // Hammers drop sticks here - we duplicate the original method
            //noinspection ConstantConditions
            player.addStat(StatList.getBlockStats(this));
            player.addExhaustion(0.005F);

            if (!worldIn.isRemote)
            {
                Helpers.spawnItemStack(worldIn, pos.add(0.5D, 0.5D, 0.5D), new ItemStack(Items.STICK, 1 + (int) (Math.random() * 3)));
            }
        }
        else if (ConfigTFC.General.TREE.requiresAxe)
        {
            // Here, there was no valid tool used. Deny spawning any drops since logs require axes
            //noinspection ConstantConditions
            player.addStat(StatList.getBlockStats(this));
            player.addExhaustion(0.005F);
        }
        else
        {
            // No tool, but handle normally
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    /*@Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return ItemStack.EMPTY;
    }*/

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(HARVESTABLE, meta > 3).withProperty(LEAF_STATE, EnumLeafState.valueOf(meta & 0b11)).withProperty(AGE, meta);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(AGE);
        //return state.getValue(AGE) + state.getValue(LEAF_STATE).ordinal() + (state.getValue(HARVESTABLE) ? 4 : 0);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer.Builder(this)
            .add(LEAF_STATE)
            .add(HARVESTABLE)
            .add(AGE)
            .build();
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!(entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).isCreative()))
        {
            // Player will take damage when falling through leaves if fall is over 9 blocks, fall damage is then set to 0.
            entityIn.fall((entityIn.fallDistance - 6), 1.0F);
            entityIn.fallDistance = 0;
            // Entity motion is reduced by leaves.
            entityIn.motionX *= ConfigTFC.General.MISC.leafMovementModifier;
            if (entityIn.motionY < 0)
            {
                entityIn.motionY *= ConfigTFC.General.MISC.leafMovementModifier;
            }
            entityIn.motionZ *= ConfigTFC.General.MISC.leafMovementModifier;
        }
    }

    public double getGrowthRate(World world, BlockPos pos)
    {
        if (world.isRainingAt(pos)) return ConfigTFC.General.MISC.plantGrowthRate * 5d;
        else return ConfigTFC.General.MISC.plantGrowthRate;
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random)
    {
        if (!world.isAreaLoaded(pos, 1)) return;

        if (!world.isRemote)
        {
            Month currentMonth = CalendarTFC.CALENDAR_TIME.getMonthOfYear();
            int expectedStage = fruitTree.getStageForMonth(currentMonth);
            float avgTemperature = ClimateTFC.getAvgTemp(world, pos);

            switch (expectedStage)
            {
                case 1:
                    if (state.getValue(LEAF_STATE) != EnumLeafState.NORMAL)
                        world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.NORMAL), 2);
                    else if (state.getValue(LEAF_STATE) == EnumLeafState.FLOWERING)
                        world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.NORMAL), 2);
                    break;
                case 2:
                    if (state.getValue(LEAF_STATE) != EnumLeafState.FLOWERING)
                        world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.FLOWERING), 2);
                    break;
                case 3:
                    if (state.getValue(LEAF_STATE) != EnumLeafState.FRUIT)
                    {
                        TETickCounter te = Helpers.getTE(world, pos, TETickCounter.class);
                        if (te != null)
                        {
                            long hours = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_HOUR;
                            if (hours > (fruitTree.getGrowthTime() * ConfigTFC.General.FOOD.fruitTreeGrowthTimeModifier))
                            {
                                world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.FRUIT), 2);
                                te.resetCounter();
                            }
                        }
                    }
                    break;
                default:
                    world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.NORMAL), 2);
                    break;
            }
            world.scheduleUpdate(pos, this, 1);
        }
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ConfigTFC.General.TREE.enableSaplings ? Item.getItemFromBlock(BlockJoshuaTreeSapling.get(wood)) : Items.AIR;
    }

    protected int getSaplingDropChance(IBlockState state)
    {
        return 25;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        int chance = this.getSaplingDropChance(state);
        if (chance > 0)
        {
            if (fortune > 0)
            {
                chance -= 2 << fortune;
                if (chance < 10) chance = 10;
            }

            if (RANDOM.nextInt(chance) == 0)
            {
                ItemStack drop = new ItemStack(getItemDropped(state, RANDOM, fortune), 1, damageDropped(state));
                if (!drop.isEmpty())
                {
                    drops.add(drop);
                }
            }
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    public enum EnumLeafState implements IStringSerializable
    {
        NORMAL, FLOWERING, FRUIT;

        private static final EnumLeafState[] VALUES = values();

        @Nonnull
        public static EnumLeafState valueOf(int index)
        {
            return index < 0 || index > VALUES.length - 1 ? NORMAL : VALUES[index];
        }

        @Override
        public String getName()
        {
            return this.name().toLowerCase();
        }
    }

    @Override
    public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos)
    {
        return GrowthStatus.GROWING;
    }
}