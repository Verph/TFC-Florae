package tfcflorae.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkHooks;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.registry.RegistryRock;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.settings.RockSettings;

import tfcflorae.TFCFlorae;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;

public class TFCFHelpers
{
    public static final Random RANDOM = new Random();
    public static Direction[] NOT_DOWN = new Direction[] {Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.UP};
    public static final Direction[] DIRECTIONS = Direction.values();
    public static final Direction[] DIRECTIONS_HORIZONTAL = Arrays.stream(DIRECTIONS).filter(d -> d != Direction.DOWN && d != Direction.UP).toArray(Direction[]::new);

    public static ResourceLocation identifier(String id)
    {
        return new ResourceLocation(TFCFlorae.MOD_ID, id);
    }

    public static Direction getRandom(Random random)
    {
        return Util.getRandom(DIRECTIONS_HORIZONTAL, random);
    }

    public static Component blockEntityName(String name)
    {
        return new TranslatableComponent(TFCFlorae.MOD_ID + ".block_entity." + name);
    }

    public static InteractionResult insertOne(Level level, ItemStack item, int slot, IItemHandler inv, Player player)
    {
        if (!inv.isItemValid(slot, item)) return InteractionResult.PASS;
        return completeInsertion(level, item, inv, player, slot);
    }

    public static InteractionResult takeOne(Level level, int slot, IItemHandler inv, Player player)
    {
        ItemStack stack = inv.extractItem(slot, 1, false);
        if (stack.isEmpty()) return InteractionResult.PASS;
        if (!level.isClientSide) ItemHandlerHelper.giveItemToPlayer(player, stack);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public static InteractionResult insertOneAny(Level level, ItemStack item, int start, int end, IItemHandler inv, Player player)
    {
        for (int i = start; i <= end; i++)
        {
            if (inv.getStackInSlot(i).isEmpty())
            {
                return completeInsertion(level, item, inv, player, i);
            }
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult completeInsertion(Level level, ItemStack item, IItemHandler inv, Player player, int slot)
    {
        ItemStack stack = inv.insertItem(slot, item.split(1), false);
        if (stack.isEmpty()) return InteractionResult.sidedSuccess(level.isClientSide);
        if (!level.isClientSide)
        {
            ItemHandlerHelper.giveItemToPlayer(player, stack);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public static InteractionResult takeOneAny(Level level, int start, int end, IItemHandler inv, Player player)
    {
        for (int i = start; i <= end; i++)
        {
            ItemStack stack = inv.extractItem(i, 1, false);
            if (stack.isEmpty()) continue;
            if (!level.isClientSide) ItemHandlerHelper.giveItemToPlayer(player, stack);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public static Iterable<BlockPos> allPositionsCentered(BlockPos center, int radius, int height)
    {
        return BlockPos.betweenClosed(center.offset(-radius, -height, -radius), center.offset(radius, height, radius));
    }

    // todo: 1.19. inline and remove these
    public static void openScreen(ServerPlayer player, MenuProvider containerSupplier)
    {
        NetworkHooks.openGui(player, containerSupplier);
    }
    
    public static void openScreen(ServerPlayer player, MenuProvider containerSupplier, BlockPos pos)
    {
        NetworkHooks.openGui(player, containerSupplier, pos);
    }

    public static void openScreen(ServerPlayer player, MenuProvider containerSupplier, Consumer<FriendlyByteBuf> extraDataWriter)
    {
        NetworkHooks.openGui(player, containerSupplier, extraDataWriter);
    }

    public static int randomRange(int min, int max)
    {
        return RANDOM.nextInt(max - min) + min;
    }

    public static double randomRange(double min, double max)
    {
        return min + (max - min) * RANDOM.nextDouble();
    }

    public static float randomDirection(float value)
    {
        return RANDOM.nextBoolean() ? value : -value;
    }

    public static double randomDirection(double value)
    {
        return RANDOM.nextBoolean() ? value : -value;
    }

    public static int randomDirection(int value)
    {
        return RANDOM.nextBoolean() ? value : -value;
    }

    public static <T> T choose(T... values)
    {
        return values[RANDOM.nextInt(values.length)];
    }

    public static <T> T choose(List<T> values)
    {
        return values.get(RANDOM.nextInt(values.size()));
    }

    public static int randomBias(int min, int max)
    {
        int num = randomRange(min, max);
        int mid = (max / 2) - (min / 2);
        int halfMid = mid / 2;
        if(num > mid) num -= RANDOM.nextInt((halfMid + 1));
        else if(num < mid) num += RANDOM.nextInt((halfMid + 1));

        return num;
    }

    public static String toProperCase(final String s)
    {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    /**
     * Use over invoking the constructor, as Mojang refactors this in 1.19
     */
    public static TranslatableComponent translatable(String key)
    {
        return new TranslatableComponent(key);
    }

    public static RegistryRock rockType(ServerLevel level, BlockPos pos)
    {
        ChunkDataProvider provider = ChunkDataProvider.get(level);
        RockSettings surfaceRock = provider.get(level, pos).getRockData().getRock(pos);

        Rock rockTFC = null;
        TFCFRock rockTFCF = null;

        if (surfaceRock != null)
        {
            for (Rock r : Rock.values())
            {
                if (surfaceRock.raw() == TFCBlocks.ROCK_BLOCKS.get(r).get(Rock.BlockType.RAW).get())
                {
                    rockTFC = r;
                    break;
                }
                else
                {
                    for (TFCFRock r2 : TFCFRock.values())
                    {
                        if (surfaceRock.raw() == TFCFBlocks.TFCF_ROCK_BLOCKS.get(r2).get(Rock.BlockType.RAW).get())
                        {
                            rockTFCF = r2;
                            break;
                        }
                    }
                }
            }
        }
        if (rockTFC != null)
            return rockTFC;
        else if (rockTFCF != null)
            return rockTFCF;
        else
            return Rock.GRANITE;
    }

    public static ResourceLocation animalTexture(String name)
    {
        return identifier("textures/entity/animal/" + name + ".png");
    }
}
