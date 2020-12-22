package tfcflorae.objects.blocks.blocktype;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.BlockPeatGrass;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariantConnected;

import tfcflorae.types.BlockTypesTFCF;
import tfcflorae.util.OreDictionaryHelper;

import static net.dries007.tfc.api.types.Rock.Type.*;
import static net.dries007.tfc.objects.CreativeTabsTFC.*;
import static net.dries007.tfc.objects.fluids.FluidsTFC.*;
import static net.dries007.tfc.util.Helpers.getNull;

@ParametersAreNonnullByDefault
public class BlockGrass extends BlockPeatGrass
{
    public BlockTypesTFCF type;
    public static HashMap<Rock, EnumMap<BlockTypesTFCF, BlockGrass>> table = new HashMap<>();
    
    public static BlockGrass get(Rock rock, BlockTypesTFCF rockTFCF)
    {
        return table.get(rock).get(rockTFCF);
    }

    public static BlockGrass create(Rock rock, BlockTypesTFCF rockTFCF)
    {
        if(rockTFCF.isFallable()) return new BlockGrassFallable(rock, rockTFCF);
        return new BlockGrass(rock, rockTFCF);
    }

    public BlockGrass(Rock rock, BlockTypesTFCF rockTFCF)
    {
        super(rockTFCF.getMaterial());
        this.type = rockTFCF;
        this.setSoundType(rockTFCF.getSoundType());
        this.setHardness(rockTFCF.getHardness());
        this.setHarvestLevel("shovel", 1);
        if(!table.containsKey(rock))
        {
            table.put(rock, new EnumMap<>(BlockTypesTFCF.class));
        }
        table.get(rock).put(rockTFCF, this);
    }

    // Used for connected textures only.
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        pos = pos.add(0, -1, 0);
        return state.withProperty(NORTH, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.NORTH))))
            .withProperty(EAST, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.EAST))))
            .withProperty(SOUTH, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.SOUTH))))
            .withProperty(WEST, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.WEST))));
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (world.isRemote) return;
        //BlockRockVariantConnected.spreadGrass(world, pos, state, rand);
    }
    
    /*
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        switch (type)
        {
            case PODZOL:
                return Item.getItemFromBlock(get(type, Rock.Type.DIRT));
            default:
                return super.getItemDropped(state, rand, fortune);
        }
    }
    */

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH);
    }
}
