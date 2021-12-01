package tfcflorae.objects.blocks.wood;

import java.util.*;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import mcp.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.player.IPlayerData;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.util.Helpers;
import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.types.TreesTFCF;
import tfcflorae.util.OreDictionaryHelper;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockJoshuaTreeLog extends Block
{
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    private static final Map<Tree, BlockJoshuaTreeLog> MAP = new HashMap<>();

    public static BlockJoshuaTreeLog get(Tree wood)
    {
        return MAP.get(wood);
    }

    private final Tree wood;

    public BlockJoshuaTreeLog(Tree wood)
    {
        super(Material.WOOD);
        this.wood = wood;
        if (MAP.put(wood, this) != null) throw new IllegalStateException("There can only be one.");

        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)).withProperty(UP, Boolean.valueOf(false)).withProperty(DOWN, Boolean.valueOf(false)));
        this.setHarvestLevel("axe", 0);
        this.setHardness(20.0F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.WOOD);
        OreDictionaryHelper.register(this, "log", "wood");
        //noinspection ConstantConditions
        OreDictionaryHelper.register(this, "log", "wood", wood.getRegistryName().getPath());
        if (wood.canMakeTannin())
        {
            OreDictionaryHelper.register(this, "log", "wood", "tannin");
        }

        Blocks.FIRE.setFireInfo(this, 5, 5);
        this.setTickRandomly(true);
    }

    public Tree getWood()
    {
        return wood;
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        Block block1 = worldIn.getBlockState(pos.up()).getBlock();
        Block block2 = worldIn.getBlockState(pos.north()).getBlock();
        Block block3 = worldIn.getBlockState(pos.east()).getBlock();
        Block block4 = worldIn.getBlockState(pos.south()).getBlock();
        Block block5 = worldIn.getBlockState(pos.west()).getBlock();
        return state.withProperty(DOWN, Boolean.valueOf(block == this || block == BlockJoshuaTreeFlower.get(wood) || BlocksTFC.isSand(worldIn.getBlockState(pos.down())) || BlocksTFC.isSoilOrGravel(worldIn.getBlockState(pos.down())) || BlocksTFCF.isSand(worldIn.getBlockState(pos.down())) || BlocksTFCF.isSoilOrGravel(worldIn.getBlockState(pos.down())) || block == Blocks.HARDENED_CLAY || block == Blocks.STAINED_HARDENED_CLAY)).withProperty(UP, Boolean.valueOf(block1 == this || block1 == BlockJoshuaTreeFlower.get(wood))).withProperty(NORTH, Boolean.valueOf(block2 == this || block2 == BlockJoshuaTreeFlower.get(wood))).withProperty(EAST, Boolean.valueOf(block3 == this || block3 == BlockJoshuaTreeFlower.get(wood))).withProperty(SOUTH, Boolean.valueOf(block4 == this || block4 == BlockJoshuaTreeFlower.get(wood))).withProperty(WEST, Boolean.valueOf(block5 == this || block5 == BlockJoshuaTreeFlower.get(wood)));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = state.getActualState(source, pos);
        float f = 0.1875F;
        float f1 = ((Boolean)state.getValue(WEST)).booleanValue() ? 0.0F : 0.1875F;
        float f2 = ((Boolean)state.getValue(DOWN)).booleanValue() ? 0.0F : 0.1875F;
        float f3 = ((Boolean)state.getValue(NORTH)).booleanValue() ? 0.0F : 0.1875F;
        float f4 = ((Boolean)state.getValue(EAST)).booleanValue() ? 1.0F : 0.8125F;
        float f5 = ((Boolean)state.getValue(UP)).booleanValue() ? 1.0F : 0.8125F;
        float f6 = ((Boolean)state.getValue(SOUTH)).booleanValue() ? 1.0F : 0.8125F;
        return new AxisAlignedBB((double)f1, (double)f2, (double)f3, (double)f4, (double)f5, (double)f6);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
        if (!isActualState)
        {
            state = state.getActualState(worldIn, pos);
        }

        float f = 0.1875F;
        float f1 = 0.8125F;
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D));

        if (((Boolean)state.getValue(WEST)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.0D, 0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D));
        }

        if (((Boolean)state.getValue(EAST)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.8125D, 0.1875D, 0.1875D, 1.0D, 0.8125D, 0.8125D));
        }

        if (((Boolean)state.getValue(UP)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.8125D, 0.1875D, 0.8125D, 1.0D, 0.8125D));
        }

        if (((Boolean)state.getValue(DOWN)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.1875D, 0.8125D));
        }

        if (((Boolean)state.getValue(NORTH)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.0D, 0.8125D, 0.8125D, 0.1875D));
        }

        if (((Boolean)state.getValue(SOUTH)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D, 1.0D));
        }
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!this.canSurviveAt(worldIn, pos))
        {
            worldIn.destroyBlock(pos, true);
        }
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        /*TFCFlorae.getLog().warn("This wood is " + wood);
        TFCFlorae.getLog().warn("Wood drop is " + BlockLogTFC.get(wood));*/
        if (BlockLogTFC.get(wood) != null)
            return BlockLogTFC.get(wood).getItemDropped(state, rand, fortune);
        else
            return BlockLogTFC.get(TFCRegistries.TREES.getValue(TreesTFCF.JOSHUA_TREE)).getItemDropped(state, rand, fortune);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    /*@Override
    public int quantityDropped(Random random)
    {
        return random.nextInt(2);
    }*/

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
        return super.canPlaceBlockAt(worldIn, pos) ? this.canSurviveAt(worldIn, pos) : false;
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!this.canSurviveAt(worldIn, pos))
        {
            worldIn.scheduleUpdate(pos, this, 1);
        }
    }

    public boolean canSurviveAt(World worldIn, BlockPos pos)
    {
        boolean flag = worldIn.isAirBlock(pos.up());
        boolean flag1 = worldIn.isAirBlock(pos.down());

        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            BlockPos blockpos = pos.offset(enumfacing);
            Block block = worldIn.getBlockState(blockpos).getBlock();

            if (block == this)
            {
                if (!flag && !flag1)
                {
                    return false;
                }

                Block block1 = worldIn.getBlockState(blockpos.down()).getBlock();

                if (block1 == this || BlocksTFC.isSand(worldIn.getBlockState(blockpos.down())) || BlocksTFC.isSoilOrGravel(worldIn.getBlockState(blockpos.down())) || BlocksTFCF.isSand(worldIn.getBlockState(blockpos.down())) || BlocksTFCF.isSoilOrGravel(worldIn.getBlockState(blockpos.down())) || block1 == Blocks.HARDENED_CLAY || block1 == Blocks.STAINED_HARDENED_CLAY)
                {
                    return true;
                }
            }
        }

        Block block2 = worldIn.getBlockState(pos.down()).getBlock();
        return block2 == this || BlocksTFC.isSand(worldIn.getBlockState(pos.down())) || BlocksTFC.isSoilOrGravel(worldIn.getBlockState(pos.down())) || BlocksTFCF.isSand(worldIn.getBlockState(pos.down())) || BlocksTFCF.isSoilOrGravel(worldIn.getBlockState(pos.down())) || block2 == Blocks.HARDENED_CLAY || block2 == Blocks.STAINED_HARDENED_CLAY;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, SOUTH, WEST, UP, DOWN});
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        Block block = blockAccess.getBlockState(pos.offset(side)).getBlock();
        return block != this && block != BlockJoshuaTreeFlower.get(wood) && (side != EnumFacing.DOWN || !BlocksTFC.isSand(blockAccess.getBlockState(pos.offset(side))) || !BlocksTFC.isSoilOrGravel(blockAccess.getBlockState(pos.offset(side))) || !BlocksTFCF.isSand(blockAccess.getBlockState(pos.offset(side))) || !BlocksTFCF.isSoilOrGravel(blockAccess.getBlockState(pos.offset(side))) || block == Blocks.HARDENED_CLAY || block == Blocks.STAINED_HARDENED_CLAY);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
