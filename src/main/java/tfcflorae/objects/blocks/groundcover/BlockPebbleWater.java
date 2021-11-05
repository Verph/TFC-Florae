package tfcflorae.objects.blocks.groundcover;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.BlockFluidTFC;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.items.rock.ItemRock;

import tfcflorae.util.OreDictionaryHelper;

import static net.dries007.tfc.world.classic.ChunkGenTFC.SALT_WATER;
import static net.dries007.tfc.world.classic.ChunkGenTFC.FRESH_WATER;

@ParametersAreNonnullByDefault
public class BlockPebbleWater extends BlockFluidTFC implements IItemSize
{
	public static final PropertyEnum<EnumPileType> PILE_TYPE = PropertyEnum.<EnumPileType>create("pile_type", EnumPileType.class);
	private static final AxisAlignedBB AABB_1_STONE = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.1875D, 0.75D);
	private static final AxisAlignedBB AABB_2_STONE = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.1875D, 0.75D);
	private static final AxisAlignedBB AABB_3_STONE = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.1875D, 0.8125D);
	private static final AxisAlignedBB AABB_4_STONE = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.1875D, 0.875D);
    private static final Map<Rock, BlockPebbleWater> MAP = new HashMap<>();

    public static BlockPebbleWater get(Rock rock)
    {
        return MAP.get(rock);
    }

    protected final Rock rock;

    public BlockPebbleWater(Fluid fluid, Rock rock)
    {
        this(fluid, Material.WATER, rock);
    }

    public BlockPebbleWater(Fluid fluid, Material materialIn, Rock rock)
    {
        super(fluid, Material.WATER, false);

        this.rock = rock;
		setHardness(0.5F);
		setResistance(2.0F);
		setSoundType(SoundType.STONE);
		setDefaultState(getDefaultState().withProperty(PILE_TYPE, EnumPileType.ONE).withProperty(LEVEL, 0));

        OreDictionaryHelper.register(this, rock);
    }

    @Nonnull
    @Override
    public boolean isReplaceable(IBlockAccess world, BlockPos pos)
    {
        return false;
    }

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
		if (state.getBlock() == null || world.isAirBlock(pos.down()))
        {
            this.dropBlockAsItem((World) world, pos, world.getBlockState(pos), 0);
            (world).setBlockToAir(pos);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        if (this.getFluid() == SALT_WATER)
            return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos) && worldIn.getBlockState(pos.down()).isFullBlock() && BlocksTFC.isSaltWater(worldIn.getBlockState(pos)) && BlocksTFC.isSaltWater(worldIn.getBlockState(pos.up())) && !worldIn.isAirBlock(pos.up());
        else if (this.getFluid() == FRESH_WATER)
            return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos) && worldIn.getBlockState(pos.down()).isFullBlock() && BlocksTFC.isFreshWater(worldIn.getBlockState(pos)) && BlocksTFC.isFreshWater(worldIn.getBlockState(pos.up())) && !worldIn.isAirBlock(pos.up());
        else
            return false;
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        return this.canPlaceBlockAt(worldIn, pos) && !(worldIn.isAirBlock(pos.up()));
    }

    @Override
    @Nonnull
    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XZ;
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack)
    {
        return Size.TINY; // Store anywhere
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack)
    {
        return Weight.VERY_LIGHT; // Stacksize = 64
    }

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
		state = state.getActualState(source, pos);
		switch ((EnumPileType) state.getValue(PILE_TYPE))
        {
		default:
		case ONE:
		case ONE_PLANT:
			return AABB_1_STONE;
		case TWO:
		case TWO_PLANT:
			return AABB_2_STONE;
		case THREE:
		case THREE_PLANT:
			return AABB_3_STONE;
		case FOUR:
		case FOUR_PLANT:
			return AABB_4_STONE;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> list)
    {
		list.add(new ItemStack(this, 1, EnumPileType.ONE.getMetadata()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
    {
		return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
    {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
    {
		return false;
	}

	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
    	return BlockFaceShape.UNDEFINED;
    }

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;

        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
        {
            if (!world.isRemote) {
                if (!player.getHeldItem(hand).isEmpty() && player.getHeldItem(hand).getItem() == ItemRock.get(rock))
                {
                    if(state.getValue(PILE_TYPE) == EnumPileType.FOUR || state.getValue(PILE_TYPE) == EnumPileType.FOUR_PLANT)
                        return false;
                    ItemStack stack = player.getHeldItem(hand).splitStack(1);
                    if (!stack.isEmpty())
                    {
                        IBlockState stateNew = state.cycleProperty(PILE_TYPE);
                        world.setBlockState(pos, stateNew);
                        return true;
                    }
                } 
                else if (player.getHeldItem(hand).isEmpty())
                {
                    ItemStack extracted = new ItemStack(ItemRock.get(rock), 1);
                    EntityItem item = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, extracted);
                    item.motionX = item.motionY = item.motionZ = 0D;
                    world.spawnEntity(item);

                    if(state.getValue(PILE_TYPE) == EnumPileType.FOUR)
                        world.setBlockState(pos, state.withProperty(PILE_TYPE, EnumPileType.THREE));
                    if(state.getValue(PILE_TYPE) == EnumPileType.THREE)
                        world.setBlockState(pos, state.withProperty(PILE_TYPE, EnumPileType.TWO));
                    if(state.getValue(PILE_TYPE) == EnumPileType.TWO)
                        world.setBlockState(pos, state.withProperty(PILE_TYPE, EnumPileType.ONE));
                    if(state.getValue(PILE_TYPE) == EnumPileType.ONE)
                        world.setBlockToAir(pos);

                    if(state.getValue(PILE_TYPE) == EnumPileType.FOUR_PLANT)
                        world.setBlockState(pos, state.withProperty(PILE_TYPE, EnumPileType.THREE_PLANT));
                    if(state.getValue(PILE_TYPE) == EnumPileType.THREE_PLANT)
                        world.setBlockState(pos, state.withProperty(PILE_TYPE, EnumPileType.TWO_PLANT));
                    if(state.getValue(PILE_TYPE) == EnumPileType.TWO_PLANT)
                        world.setBlockState(pos, state.withProperty(PILE_TYPE, EnumPileType.ONE_PLANT));
                    if(state.getValue(PILE_TYPE) == EnumPileType.ONE_PLANT)
                        world.setBlockToAir(pos);
                    return true;
                }
            }
        }
		return false;
	}


    @Nonnull
	@Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
		if (!(world.getBlockState(pos).getBlock() instanceof BlockPebbleWater))
        {
				if (world.rand.nextBoolean())
					return getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer) .withProperty(PILE_TYPE, EnumPileType.ONE_PLANT);
				else
					return getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer) .withProperty(PILE_TYPE, EnumPileType.ONE);
		}
        return getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
    }

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ItemRock.get(rock);
	}

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
		int dropCount = 0;
		int meta = state.getValue(PILE_TYPE).getMetadata();
		if(meta <= 3)
			dropCount = meta;
		if(meta >= 4 && meta <= 7)
			dropCount = meta - 4;
		return 1 + dropCount;
    }

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
		return new ItemStack(ItemRock.get(rock), 1);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
    {
		return this.getDefaultState().withProperty(PILE_TYPE, EnumPileType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
    {
		return ((EnumPileType)state.getValue(PILE_TYPE)).getMetadata();
	}

	@Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer.Builder(this).add(LEVEL).add(new IProperty[] { PILE_TYPE }).add(FLUID_RENDER_PROPS.toArray(new IUnlistedProperty<?>[0])).build();
	}

	@Override
	public int damageDropped(IBlockState state)
    {
		return ((EnumPileType)state.getValue(PILE_TYPE)).getMetadata();
	}

	public static enum EnumPileType implements IStringSerializable
    {
		ONE,
		TWO,
		THREE,
		FOUR,
		ONE_PLANT,
		TWO_PLANT,
		THREE_PLANT,
		FOUR_PLANT;

		private final String name;

		private EnumPileType()
        {
			this.name = name().toLowerCase(Locale.ENGLISH);
		}

		public int getMetadata()
        {
			return this.ordinal();
		}

		@Override
		public String toString()
        {
			return this.name;
		}

		public static EnumPileType byMetadata(int metadata)
        {
			if (metadata < 0 || metadata >= values().length)
            {
				metadata = 0;
			}
			return values()[metadata];
		}

		@Override
		public String getName()
        {
			return this.name;
		}
	}

	public ItemBlock getItemBlock()
    {
		return null;
	}
}
