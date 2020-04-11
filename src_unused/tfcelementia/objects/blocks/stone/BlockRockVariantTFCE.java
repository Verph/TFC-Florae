package tfcelementia.objects.blocks.stone;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
import net.dries007.tfc.api.types.Rock.Type;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.items.rock.ItemRock;
//import net.dries007.tfc.util.OreDictionaryHelper;

import tfcelementia.api.types.RockTFCE;
import tfcelementia.objects.blocks.BlocksTFCE;
import tfcelementia.util.OreDictionaryHelper;

import static net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC.WILD;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockRockVariantTFCE extends Block implements IItemSize
{
    private static final Map<RockTFCE, EnumMap<RockTFCE.Type, BlockRockVariantTFCE>> TABLE = new HashMap<>();

    public static BlockRockVariantTFCE get(RockTFCE rock, RockTFCE.Type type)
    {
        //noinspection ConstantConditions
        if (rock == null)
        {
            //return TABLE.get(RockTFCE.GRANITE).get(type);
        }
        return TABLE.get(rock).get(type);
    }

    public static BlockRockVariantTFCE create(RockTFCE rock, RockTFCE.Type type)
    {
        switch (type)
        {
            case PODZOL:
                return new BlockRockVariantConnectedTFCE(type, rock);
            case MOSSY_COBBLE:
                return new BlockRockVariantFallableTFCE(type, rock);
            default:
                return new BlockRockVariantTFCE(type, rock);
        }
    }

    protected final RockTFCE.Type type;
    protected final RockTFCE rock;

    public BlockRockVariantTFCE(RockTFCE.Type type, RockTFCE rock)
    {
        super(type.material);

        if (!TABLE.containsKey(rock))
        {
            TABLE.put(rock, new EnumMap<>(RockTFCE.Type.class));
        }
        TABLE.get(rock).put(type, this);

        this.type = type;
        this.rock = rock;
        if (type.isGrass) setTickRandomly(true);
        switch (type)
        {
	        case MOSSY_BRICKS:
	        case CRACKED_BRICKS:
	            setSoundType(SoundType.STONE);
	            setHardness(rock.getRockCategory().getHardness()).setResistance(rock.getRockCategory().getResistance());
	            setHarvestLevel("pickaxe", 0);
	            break;
            case MOSSY_COBBLE:
                setSoundType(SoundType.STONE);
                setHardness(rock.getRockCategory().getHardness() * 0.75F).setResistance(rock.getRockCategory().getResistance());
                setHarvestLevel("pickaxe", 0);
                break;
            case PODZOL:
                setSoundType(SoundType.PLANT);
                setHardness(rock.getRockCategory().getHardness() * 0.2F);
                setHarvestLevel("shovel", 0);
                break;
        }
    }

    public BlockRockVariantTFCE getVariant(RockTFCE.Type t)
    {
        return TABLE.get(rock).get(t);
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (world.isRemote) return;
        if (type.isGrass) BlockRockVariantConnectedTFCE.spreadGrass(world, pos, state, rand);
        super.randomTick(world, pos, state, rand);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        switch (type)
        {
            default:
                return super.getItemDropped(state, rand, fortune);
            case PODZOL:
                return Item.getItemFromBlock(get(rock, RockTFCE.Type.PODZOL)); //Make it drop DIRT, not PODZOL
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return type.isGrass ? BlockRenderLayer.CUTOUT : BlockRenderLayer.SOLID;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        switch (type)
        {
            default:
                return super.quantityDropped(state, fortune, random);
        }
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
    {
        int beachDistance = 2;

        if (plantable instanceof BlockPlantTFC)
        {
            switch (((BlockPlantTFC) plantable).getPlantTypeTFC())
            {
                case CLAY:
                    return type == RockTFCE.Type.PODZOL;
                case FRESH_WATER:
                    return type == RockTFCE.Type.PODZOL;
                case SALT_WATER:
                    return type == RockTFCE.Type.PODZOL;
                case FRESH_BEACH:
                {
                    boolean flag = false;
                    for (EnumFacing facing : EnumFacing.HORIZONTALS)
                    {
                        for (int i = 1; i <= beachDistance; i++)
                        {
                            if (BlocksTFC.isFreshWaterOrIce(world.getBlockState(pos.offset(facing, i))))
                            {
                                flag = true;
                                break;
                            }
                        }
                    }
                    return (type == RockTFCE.Type.PODZOL) && flag;
                }
                case SALT_BEACH:
                {
                    boolean flag = false;
                    for (EnumFacing facing : EnumFacing.HORIZONTALS)
                    {
                        for (int i = 1; i <= beachDistance; i++)
                            if (BlocksTFC.isSaltWater(world.getBlockState(pos.offset(facing, i))))
                            {
                                flag = true;
                            }
                    }
                    return (type == RockTFCE.Type.PODZOL) && flag;
                }
            }
        }
        else if (plantable instanceof BlockCropTFC)
        {
            IBlockState cropState = world.getBlockState(pos.up());
            if (cropState.getBlock() instanceof BlockCropTFC)
            {
                boolean isWild = cropState.getValue(WILD);
                if (isWild)
                {
                    if (type == RockTFCE.Type.PODZOL)
                    {
                        return true;
                    }
                }
                //return type == Rock.Type.FARMLAND;
            }
        }

        switch (plantable.getPlantType(world, pos.offset(direction)))
        {
            case Plains:
                return type == RockTFCE.Type.PODZOL;
            case Crop:
                return type == RockTFCE.Type.PODZOL;
            case Cave:
                return true;
            case Water:
                return false;
            case Beach:
            {
                boolean flag = false;
                for (EnumFacing facing : EnumFacing.HORIZONTALS)
                {
                    for (int i = 1; i <= beachDistance; i++)
                        if (BlocksTFC.isWater(world.getBlockState(pos.offset(facing, i))))
                        {
                            flag = true;
                        }
                }
                return (type == RockTFCE.Type.PODZOL) && flag;
            }
            case Nether:
                return false;
        }

        return false;
    }

    public RockTFCE.Type getType()
    {
        return type;
    }

    public RockTFCE getRock()
    {
        return rock;
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack)
    {
        return Size.SMALL; // Store anywhere
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack)
    {
        return Weight.LIGHT; // Stacksize = 32
    }

    protected void onRockSlide(World world, BlockPos pos)
    {
        switch (type)
        {
            case PODZOL:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                break;
            case MOSSY_COBBLE:
                world.playSound(null, pos, TFCSounds.ROCK_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }
}