package tfcelementia.objects.blocks;

import java.util.EnumMap;
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
import tfcelementia.api.types.RockTFCE;
import tfcelementia.objects.blocks.stone.BlockRockVariantTFCE;
import tfcelementia.util.OreDictionaryHelper;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class BlockSlabTFCE extends BlockSlab
{
    public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);
    public final Block modelBlock;
    protected Half halfSlab;

    private BlockSlabTFCE(RockTFCE rock, RockTFCE.Type type)
    {
        this(BlockRockVariantTFCE.get(rock, type));
        Block c = BlockRockVariantTFCE.get(rock, type);
        //noinspection ConstantConditions
        setHarvestLevel(c.getHarvestTool(c.getDefaultState()), c.getHarvestLevel(c.getDefaultState()));
        useNeighborBrightness = true;
    }

    private BlockSlabTFCE(Block block)
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

    public static class Double extends BlockSlabTFCE
    {
        private static final Map<RockTFCE, EnumMap<RockTFCE.Type, Double>> ROCK_TABLE = new HashMap<>();

        public static Double get(RockTFCE rock, RockTFCE.Type type)
        {
            return ROCK_TABLE.get(rock).get(type);
        }

        public Double(RockTFCE rock, RockTFCE.Type type)
        {
            super(rock, type);

            if (!ROCK_TABLE.containsKey(rock))
                ROCK_TABLE.put(rock, new EnumMap<>(RockTFCE.Type.class));
            ROCK_TABLE.get(rock).put(type, this);

            // No oredict, because no item.
        }

        @Override
        public boolean isDouble()
        {
            return true;
        }
    }

    public static class Half extends BlockSlabTFCE
    {
        private static final Map<RockTFCE, EnumMap<RockTFCE.Type, Half>> ROCK_TABLE = new HashMap<>();

        public static Half get(RockTFCE rock, RockTFCE.Type type)
        {
            return ROCK_TABLE.get(rock).get(type);
        }

        public final Double doubleSlab;

        public Half(RockTFCE rock, RockTFCE.Type type)
        {
            super(rock, type);

            if (!ROCK_TABLE.containsKey(rock))
                ROCK_TABLE.put(rock, new EnumMap<>(RockTFCE.Type.class));
            ROCK_TABLE.get(rock).put(type, this);

            doubleSlab = Double.get(rock, type);
            doubleSlab.halfSlab = this;
            halfSlab = this;
            OreDictionaryHelper.register(this, "slab");
            OreDictionaryHelper.registerRockType(this, type, "slab");
        }

        @Override
        public boolean isDouble()
        {
            return false;
        }
    }
}