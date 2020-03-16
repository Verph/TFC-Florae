package tfcelementia.objects.blocks.stone;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGrassPath;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.stone.BlockButtonStoneTFC;
import net.dries007.tfc.objects.blocks.stone.BlockFarmlandTFC;
import net.dries007.tfc.objects.blocks.stone.BlockOreTFC;
import net.dries007.tfc.objects.blocks.stone.BlockPathTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockRaw;
import net.dries007.tfc.objects.blocks.stone.BlockRockSpike;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariantConnected;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariantFallable;
import net.dries007.tfc.objects.blocks.stone.BlockStoneAnvil;
import net.dries007.tfc.objects.blocks.stone.BlockWallTFC;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.dries007.tfc.util.OreDictionaryHelper;

//import tfcelementia.api.types.RockTFCE;

import static net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC.WILD;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockRockVariantTFCE extends Block implements IItemSize
{
    private static final Map<Rock, EnumMap<Rock.Type, BlockRockVariantTFCE>> TABLE = new HashMap<>();

    public static BlockRockVariantTFCE get(Rock rock, Rock.Type type)
    {
        //noinspection ConstantConditions
        if (rock == null)
        {
            return TABLE.get(Rock.GRANITE).get(type);
        }
        return TABLE.get(rock).get(type);
    }

    @Nonnull
    @Override
    public static BlockRockVariantTFCE create(Rock rock, Rock.Type type)
    {
        switch (type)
        {
        	case MOSSY_COBBLE:
        		return new BlockRockVariantFallable(type, rock);
            case PODZOL:
                return new BlockRockVariantConnected(type, rock);
            case MUD:
            default:
                return new BlockRockVariantTFCE(type, rock);
        }
    }

    protected final Rock.Type type;
    protected final Rock rock;

    public BlockRockVariantTFCE(Rock.Type type, Rock rock)
    {
        super(type.material);

        if (!TABLE.containsKey(rock))
        {
            TABLE.put(rock, new EnumMap<>(Rock.Type.class));
        }
        TABLE.get(rock).put(type, this);

        this.type = type;
        this.rock = rock;
        if (type.isGrass) setTickRandomly(true);
        switch (type)
        {
            case MOSSY_COBBLE:
                setSoundType(SoundType.STONE);
                setHardness(rock.getRockCategory().getHardness() * 0.75F).setResistance(rock.getRockCategory().getResistance());
                setHarvestLevel("pickaxe", 0);
                break;
            case MUD:
                setSoundType(SoundType.GROUND);
                setHardness(rock.getRockCategory().getHardness() * 0.2F);
                setHarvestLevel("shovel", 0);
                break;
            case PODZOL:
                setSoundType(SoundType.PLANT);
                setHardness(rock.getRockCategory().getHardness() * 0.2F);
                setHarvestLevel("shovel", 0);
                break;
        }
        if (type != Rock.Type.SPIKE) //since spikes don't generate ItemBlocks
        {
            OreDictionaryHelper.registerRockType(this, type);
        }
    }

    @Nonnull
    @Override
    public BlockRockVariantTFCE getVariant(Rock.Type t)
    {
        return TABLE.get(rock).get(t);
    }

    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        switch (type)
        {
            case MUD:
                return Items.CLAY_BALL;
            default:
                return super.getItemDropped(state, rand, fortune);
            case PODZOL:
                return Item.getItemFromBlock(get(rock, Rock.Type.DIRT));
        }
    }

    @Nonnull
    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return type.isGrass ? BlockRenderLayer.CUTOUT : BlockRenderLayer.SOLID;
    }

    @Nonnull
    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        switch (type)
        {
            case MUD:
                return 4;
            default:
                return super.quantityDropped(state, fortune, random);
        }
    }

    @Nonnull
    @Override
    public Rock.Type getType()
    {
        return type;
    }

    @Nonnull
    @Override
    public Rock getRock()
    {
        return rock;
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack)
    {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack)
    {
        return Weight.MEDIUM;
    }

    @Nonnull
    @Override
    protected void onRockSlide(World world, BlockPos pos)
    {
        switch (type)
        {
            case PODZOL:
            case MUD:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                break;
            case MOSSY_COBBLE:
                world.playSound(null, pos, TFCSounds.ROCK_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }
}