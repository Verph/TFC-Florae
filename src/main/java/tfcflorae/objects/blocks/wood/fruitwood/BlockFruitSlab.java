package tfcflorae.objects.blocks.wood.fruitwood;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.IFruitTree;

import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.util.agriculture.SeasonalTrees;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class BlockFruitSlab extends BlockSlab
{
    public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);
    public final Block modelBlock;
    protected Half halfSlab;

    private BlockFruitSlab(SeasonalTrees tree)
    {
        this(BlockFruitPlanks.getTFCF(tree));
        Block c = BlockFruitPlanks.getTFCF(tree);
        //noinspection ConstantConditions
        setHarvestLevel(c.getHarvestTool(c.getDefaultState()), c.getHarvestLevel(c.getDefaultState()));
        setSoundType(SoundType.WOOD);
        useNeighborBrightness = true;
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    private BlockFruitSlab(IFruitTree tree)
    {
        this(BlockFruitPlanks.getTFC(tree));
        Block c = BlockFruitPlanks.getTFC(tree);
        //noinspection ConstantConditions
        setHarvestLevel(c.getHarvestTool(c.getDefaultState()), c.getHarvestLevel(c.getDefaultState()));
        setSoundType(SoundType.WOOD);
        useNeighborBrightness = true;
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    private BlockFruitSlab(Block block)
    {
        super(block.getDefaultState().getMaterial());
        IBlockState state = blockState.getBaseState();
        if (!isDouble()) state = state.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        setDefaultState(state.withProperty(VARIANT, Variant.DEFAULT));
        this.modelBlock = block;
        setLightOpacity(255);
    }

    @Override
    public String getTranslationKey(int meta)
    {
        return super.getTranslationKey();
    }

    @Override
    public IProperty<?> getVariantProperty()
    {
        return VARIANT; // why is this not null-tolerable ...
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return Variant.DEFAULT;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, Variant.DEFAULT);

        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }

        return iblockstate;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
        {
            i |= 8;
        }

        return i;
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return modelBlock.getBlockHardness(blockState, worldIn, pos);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(halfSlab);
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getExplosionResistance(Entity exploder)
    {
        return modelBlock.getExplosionResistance(exploder);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(halfSlab);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
    }

    @SuppressWarnings("deprecation")
    @Override
    public SoundType getSoundType()
    {
        return modelBlock.getSoundType();
    }

    public enum Variant implements IStringSerializable
    {
        DEFAULT;

        @Override
        public String getName()
        {
            return "default";
        }
    }

    public static class Double extends BlockFruitSlab
    {
        private static final Map<SeasonalTrees, Double> TREE_MAP_TFCF = new HashMap<>();
        private static final Map<IFruitTree, Double> TREE_MAP_TFC = new HashMap<>();

        public static Double getTFCF(SeasonalTrees tree)
        {
            return TREE_MAP_TFCF.get(tree);
        }

        public static Double getTFC(IFruitTree tree)
        {
            return TREE_MAP_TFC.get(tree);
        }

        public Double(SeasonalTrees tree)
        {
            super(tree);
            if (TREE_MAP_TFCF.put(tree, this) != null) throw new IllegalStateException("There can only be one.");
            // No oredict, because no item.
        }

        public Double(IFruitTree tree)
        {
            super(tree);
            if (TREE_MAP_TFC.put(tree, this) != null) throw new IllegalStateException("There can only be one.");
            // No oredict, because no item.
        }

        @Override
        public boolean isDouble()
        {
            return true;
        }
    }

    public static class Half extends BlockFruitSlab
    {
        private static final Map<SeasonalTrees, Half> TREE_MAP_TFCF = new HashMap<>();
        private static final Map<IFruitTree, Half> TREE_MAP_TFC = new HashMap<>();

        public static Half getTFCF(SeasonalTrees tree)
        {
            return TREE_MAP_TFCF.get(tree);
        }

        public static Half getTFC(IFruitTree tree)
        {
            return TREE_MAP_TFC.get(tree);
        }

        public final Double doubleSlab;

        public Half(SeasonalTrees tree)
        {
            super(tree);
            if (TREE_MAP_TFCF.put(tree, this) != null) throw new IllegalStateException("There can only be one.");
            doubleSlab = Double.getTFCF(tree);
            doubleSlab.halfSlab = this;
            halfSlab = this;
            OreDictionaryHelper.register(this, "slab");
            OreDictionaryHelper.register(this, "slab", "wood");
            OreDictionaryHelper.register(this, "slab", "wood", tree);
        }

        public Half(IFruitTree tree)
        {
            super(tree);
            if (TREE_MAP_TFC.put(tree, this) != null) throw new IllegalStateException("There can only be one.");
            doubleSlab = Double.getTFC(tree);
            doubleSlab.halfSlab = this;
            halfSlab = this;
            OreDictionaryHelper.register(this, "slab");
            OreDictionaryHelper.register(this, "slab", "wood");
            OreDictionaryHelper.register(this, "slab", "wood", tree);
        }

        @Override
        public boolean isDouble()
        {
            return false;
        }
    }
}
