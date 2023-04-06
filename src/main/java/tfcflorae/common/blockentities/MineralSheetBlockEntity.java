package tfcflorae.common.blockentities;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.dries007.tfc.common.blockentities.TFCBlockEntity;
import net.dries007.tfc.util.Helpers;

import tfcflorae.common.blocks.rock.Mineral;
import tfcflorae.common.blocks.rock.MineralSheetBlock;

public class MineralSheetBlockEntity extends TFCBlockEntity
{
    public final ItemStack[] stacks;
    public final Mineral[] cachedMinerals;

    public static final DirectionProperty FACING = MineralSheetBlock.FACING;
    public static final BooleanProperty MIRROR = MineralSheetBlock.MIRROR;

    /**
     * Sheet piles use a seperate rotation + mirror block state property to map their states (up, down, left, etc.) to an index.
     * We do this as we want to be able to mirror and rotate the block (which only provide a state, not a block entity).
     * So, this method takes the state and a requested direction, and converts it to a index into the (un-rotated) {@code stacks} array.
     * <p>
     * A mirror of {@code Mirror.NONE} and a rotation of {@code Direction.NORTH} is considered the reference frame of no-rotation, i.e. where the {@code stacks} is indexed by direction ordinal.
     */
    public int faceToIndex(Direction face)
    {
        final BlockState state = this.getBlockState();

        if (face.getAxis() == Direction.Axis.Y)
        {
            return face.ordinal();
        }

        final Mirror mirror = state.getValue(MIRROR) ? Mirror.FRONT_BACK : Mirror.NONE;
        final Rotation rot = switch (state.getValue(FACING))
            {
                case SOUTH -> Rotation.CLOCKWISE_180;
                case EAST -> Rotation.COUNTERCLOCKWISE_90;
                case WEST -> Rotation.CLOCKWISE_90;
                default -> Rotation.NONE;
            };

        return mirror.mirror(rot.rotate(face)).ordinal();
    }

    public MineralSheetBlockEntity(BlockPos pos, BlockState state)
    {
        super(TFCFBlockEntities.MINERAL_SHEET.get(), pos, state);

        this.stacks = new ItemStack[6];
        this.cachedMinerals = new Mineral[6];

        Arrays.fill(stacks, ItemStack.EMPTY);
    }

    @Override
    public BlockEntityType<?> getType()
    {
        return TFCFBlockEntities.MINERAL_SHEET.get();
    }

    public void addSheet(Direction direction, ItemStack stack)
    {
        final int index = faceToIndex(direction);
        stacks[index] = stack;
        cachedMinerals[index] = null;
        markForSync();
    }

    public ItemStack removeSheet(Direction direction)
    {
        final int index = faceToIndex(direction);
        final ItemStack stack = stacks[index];
        stacks[index] = ItemStack.EMPTY;
        cachedMinerals[index] = null;
        markForSync();
        return stack;
    }

    public ItemStack getSheet(Direction direction)
    {
        return stacks[faceToIndex(direction)].copy();
    }

    public Mineral getOrCacheMineral(Direction direction)
    {
        final int index = faceToIndex(direction);
        final ItemStack stack = stacks[index];

        Mineral mineral = cachedMinerals[index];
        if (mineral == null)
        {
            mineral = Mineral.getMineral(stack);
            if (mineral == null)
            {
                mineral = Mineral.BRIMSTONE;
            }
            cachedMinerals[index] = mineral;
        }
        return mineral;
    }

    public void setAllMineralsFromOutsideWorld(Mineral mineral)
    {
        Arrays.fill(cachedMinerals, mineral);
    }

    @Override
    public void saveAdditional(CompoundTag tag)
    {
        tag.put("stacks", Helpers.writeItemStacksToNbt(stacks));
        super.saveAdditional(tag);
    }

    @Override
    public void loadAdditional(CompoundTag tag)
    {
        Helpers.readItemStacksFromNbt(stacks, tag.getList("stacks", Tag.TAG_COMPOUND));
        Arrays.fill(cachedMinerals, null); // Invalidate metal cache
        super.loadAdditional(tag);
    }

	public String mineralNameRandom(Mineral mineral, RandomSource random)
	{
        String suffix = String.valueOf(random.nextInt(4));
        return "block/mineral/" + mineral.name().toLowerCase(Locale.ROOT) + "_" + suffix;
	}

	public String mineralName(Mineral mineral, Direction direction, Random random)
	{
        String suffix = String.valueOf(random.nextInt(4));
        //String suffix = String.valueOf(Mth.clamp(direction.ordinal(), 0, 3));
        if (suffix == null)
        {
            suffix = "0";
        }

        switch (direction)
        {
            case NORTH:
                return "block/mineral/" + mineral.name().toLowerCase(Locale.ROOT) + "_" + suffix;
            case SOUTH:
                return "block/mineral/" + mineral.name().toLowerCase(Locale.ROOT) + "_" + suffix;
            case EAST:
                return "block/mineral/" + mineral.name().toLowerCase(Locale.ROOT) + "_" + suffix;
            case WEST:
                return "block/mineral/" + mineral.name().toLowerCase(Locale.ROOT) + "_" + suffix;
            case UP:
                return "block/mineral/" + mineral.name().toLowerCase(Locale.ROOT) + "_" + suffix;
            case DOWN:
                return "block/mineral/" + mineral.name().toLowerCase(Locale.ROOT) + "_" + suffix;
            default:
                return "block/mineral/" + mineral.name().toLowerCase(Locale.ROOT) + "_" + suffix;
        }
	}
}
