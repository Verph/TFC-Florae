package tfcflorae.util;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.server.ServerLifecycleHooks;

import net.dries007.tfc.common.blocks.Gem;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.plant.fruit.FruitBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateModel;
import net.dries007.tfc.util.climate.OverworldClimateModel;
import net.dries007.tfc.util.registry.RegistryRock;
import net.dries007.tfc.util.registry.RegistrySoilVariant;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.chunkdata.LerpFloatLayer;
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;
import net.dries007.tfc.world.settings.ClimateSettings;
import net.dries007.tfc.world.settings.RockSettings;

import tfcflorae.TFCFlorae;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;
import tfcflorae.common.blocks.soil.Colors;
import tfcflorae.common.blocks.soil.TFCFSoil;

public class TFCFHelpers
{
    public static final Random RANDOM = new Random();
    public static Direction[] NOT_DOWN = new Direction[] {Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.UP};
    public static final Direction[] DIRECTIONS = Direction.values();
    public static final Direction[] DIRECTIONS_HORIZONTAL_FIRST = new Direction[] {Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.UP, Direction.DOWN};
    public static final Direction[] DIRECTIONS_HORIZONTAL = Arrays.stream(DIRECTIONS).filter(d -> d != Direction.DOWN && d != Direction.UP).toArray(Direction[]::new);
    public static final Direction[] DIRECTIONS_VERTICAL = Arrays.stream(DIRECTIONS).filter(d -> d == Direction.DOWN || d == Direction.UP).toArray(Direction[]::new);

    public static Random randomSeed(long seed)
    {
        final Random random = new Random(seed);
        random.setSeed(seed ^ random.nextLong());
        return random;
    }

    public static ServerLevel getLevel()
    {
        return ServerLifecycleHooks.getCurrentServer().overworld();
    }

    public static float getRainfall(long seed, int chunkX, int chunkZ)
    {
        if(getLevel() != null)
        {
            final ChunkData data = ChunkDataProvider.get(getLevel()).get(getLevel().getChunk(chunkX, chunkZ));
            ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
            return data.getRainfall(chunkPos.getMiddleBlockX(), chunkPos.getMiddleBlockZ());
        }
        else
        {
            final ClimateSettings rainfallSettings = ClimateSettings.DEFAULT;

            Noise2D rainfallNoise = ((Noise2D) (x, z) -> Helpers.triangle(1, 0, 1f / (4f * rainfallSettings.scale()), rainfallSettings.endlessPoles() ? Mth.clamp(x, -rainfallSettings.scale(), rainfallSettings.scale()) : x))
                .scaled(ClimateModel.MINIMUM_RAINFALL, ClimateModel.MAXIMUM_RAINFALL)
                .add(new OpenSimplex2D(randomSeed(seed).nextInt())
                    .octaves(2)
                    .spread(12f / rainfallSettings.scale())
                    .scaled(-OverworldClimateModel.REGIONAL_RAINFALL_SCALE, OverworldClimateModel.REGIONAL_RAINFALL_SCALE))
                .clamped(ClimateModel.MINIMUM_RAINFALL, ClimateModel.MAXIMUM_RAINFALL);

                float rainNW = rainfallNoise.noise(chunkX, chunkZ);
                float rainNE = rainfallNoise.noise(chunkX + 16, chunkZ);
                float rainSW = rainfallNoise.noise(chunkX, chunkZ + 16);
                float rainSE = rainfallNoise.noise(chunkX + 16, chunkZ + 16);

                return new LerpFloatLayer(rainNW, rainNE, rainSW, rainSE).getValue((chunkZ & 15) / 16f, 1 - ((chunkX & 15) / 16f));
        }
    }

    public static float getTemperature(long seed, int chunkX, int chunkZ)
    {
        if(getLevel() != null)
        {
            final ChunkData data = ChunkDataProvider.get(getLevel()).get(getLevel().getChunk(chunkX, chunkZ));
            ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
            return data.getAverageTemp(chunkPos.getMiddleBlockX(), chunkPos.getMiddleBlockZ());
        }
        else
        {
            final ClimateSettings temperatureSettings = ClimateSettings.DEFAULT;

            Noise2D temperatureNoise = ((Noise2D) (x, z) -> Helpers.triangle(1, 0, 1f / (4f * temperatureSettings.scale()), temperatureSettings.endlessPoles() ? Mth.clamp(z, -temperatureSettings.scale(), temperatureSettings.scale()) : z))
                .scaled(OverworldClimateModel.MINIMUM_TEMPERATURE_SCALE, OverworldClimateModel.MAXIMUM_TEMPERATURE_SCALE)
                .add(new OpenSimplex2D(randomSeed(seed).nextInt())
                    .octaves(2)
                    .spread(12f / temperatureSettings.scale())
                    .scaled(-OverworldClimateModel.REGIONAL_TEMPERATURE_SCALE, OverworldClimateModel.REGIONAL_TEMPERATURE_SCALE));

            float tempNW = temperatureNoise.noise(chunkX, chunkZ);
            float tempNE = temperatureNoise.noise(chunkX + 16, chunkZ);
            float tempSW = temperatureNoise.noise(chunkX, chunkZ + 16);
            float tempSE = temperatureNoise.noise(chunkX + 16, chunkZ + 16);

            return new LerpFloatLayer(tempNW, tempNE, tempSW, tempSE).getValue((chunkZ & 15) / 16f, 1 - ((chunkX & 15) / 16f));
        }
    }

    public static float getForestDensity(long seed, int chunkX, int chunkZ)
    {
        if(getLevel() != null)
        {
            final ChunkData data = ChunkDataProvider.get(getLevel()).get(getLevel().getChunk(chunkX, chunkZ));
            return data.getForestDensity();
        }
        else
        {
            return new OpenSimplex2D(randomSeed(seed).nextInt()).octaves(4).spread(0.0025f).scaled(-0.2f, 1.2f).clamped(0, 1).noise(chunkX + 8, chunkZ + 8);
        }
    }

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
        Rock defaultRock = Rock.GRANITE;
        ChunkDataProvider provider = ChunkDataProvider.get(level);
        if (provider != null)
        {
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
                return defaultRock;
        }
        return defaultRock;
    }

    public static RegistryRock rockType(WorldGenLevel level, BlockPos pos)
    {
        Rock defaultRock = Rock.GRANITE;
        ChunkDataProvider provider = ChunkDataProvider.get(level);
        if (provider != null)
        {
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
                return defaultRock;
        }
        return defaultRock;
    }

    public static RegistrySoilVariant getSoilVariant(WorldGenLevel level, BlockPos pos)
    {
        for (SoilBlockType.Variant soilVariant : SoilBlockType.Variant.values())
        {
            if (level.getBlockState(pos).getBlock().getName().toString().toLowerCase(Locale.ROOT).contains(soilVariant.name().toLowerCase(Locale.ROOT)))
            {
                return soilVariant;
            }
        }
        for (TFCFSoil.TFCFVariant soilVariant : TFCFSoil.TFCFVariant.values())
        {
            if (level.getBlockState(pos).getBlock().getName().toString().toLowerCase(Locale.ROOT).contains(soilVariant.name().toLowerCase(Locale.ROOT)))
            {
                return soilVariant;
            }
        }
        return SoilBlockType.Variant.LOAM;
    }

    public static boolean isVanillaSoilVariant(WorldGenLevel level, BlockPos pos)
    {
        for (SoilBlockType.Variant soilVariant : SoilBlockType.Variant.values())
        {
            if (TFCFHelpers.getSoilVariant(level, pos).toString().toLowerCase(Locale.ROOT).contains(soilVariant.name().toLowerCase(Locale.ROOT)))
            {
                return true;
            }
        }
        return false;
    }

    public static Block getSandBlock(WorldGenLevel level, BlockPos pos, boolean cheap)
    {
        Colors color = getSandColorTFCF(level, pos, cheap);
        if (color.hasSandNew())
        {
            return TFCFBlocks.SAND.get(color).get();
        }
        else
        {
            return TFCBlocks.SAND.get(color.toSandTFC(true)).get();
        }
    }

	public static SandBlockType getSandColor(WorldGenLevel level, BlockPos pos, boolean cheap)
	{
        boolean foundColour = false;
        if (!cheap)
        {
            for (Direction direction : TFCFHelpers.DIRECTIONS_HORIZONTAL_FIRST)
            {
                SandBlockType colour = Colors.toSandTFC(Colors.fromMaterialColour(level.getBlockState(pos.relative(direction)).getBlock().defaultMaterialColor()), true);
                if (colour != null)
                {
                    foundColour = true;
                    return colour;
                }
                else
                {
                    foundColour = false;
                }
            }
        }
        else if (cheap)
        {
            SandBlockType colour = Colors.toSandTFC(Colors.fromMaterialColour(level.getBlockState(pos.below()).getBlock().defaultMaterialColor()), true);
            if (colour != null)
            {
                foundColour = true;
                return colour;
            }
            else
            {
                foundColour = false;
            }
        }
        else if (!foundColour)
        {
            final ChunkDataProvider provider = ChunkDataProvider.get(level);
            if (provider != null)
            {
                final ChunkData data = provider.get(level, pos);
                final Block sand = data.getRockData().getRock(pos).sand();

                for (Colors sandColors : Colors.values())
                {
                    if (sand != null && (sand == TFCBlocks.SAND.get(Colors.toSandTFC(sandColors, true)).get() || sand == TFCFBlocks.SAND.get(Colors.nonTFC(sandColors)).get()))
                    {
                        return Colors.toSandTFC(sandColors, true);
                    }
                }
            }
        }
		return SandBlockType.YELLOW;
	}

	public static Colors getSandColorTFCF(WorldGenLevel level, BlockPos pos, boolean cheap)
	{
        boolean foundColour = false;
        if (!cheap)
        {
            for (Direction direction : TFCFHelpers.DIRECTIONS_HORIZONTAL_FIRST)
            {
                Colors colour = Colors.fromMaterialColour(level.getBlockState(pos.relative(direction)).getBlock().defaultMaterialColor());
                if (colour != null)
                {
                    foundColour = true;
                    return colour;
                }
                else
                {
                    foundColour = false;
                }
            }
        }
        else if (cheap)
        {
            Colors colour = Colors.fromMaterialColour(level.getBlockState(pos.below()).getBlock().defaultMaterialColor());
            if (colour != null)
            {
                foundColour = true;
                return colour;
            }
            else
            {
                foundColour = false;
            }
        }
        else if (!foundColour)
        {
            final ChunkDataProvider provider = ChunkDataProvider.get(level);
            if (provider != null)
            {
                final ChunkData data = provider.get(level, pos);
                final Block sand = data.getRockData().getRock(pos).sand();

                for (Colors sandColors : Colors.values())
                {
                    if (sand != null && (sand == TFCBlocks.SAND.get(Colors.toSandTFC(sandColors, true)).get() || sand == TFCFBlocks.SAND.get(Colors.nonTFC(sandColors)).get()))
                    {
                        return sandColors;
                    }
                }
            }
        }
		return Colors.ORANGE;
	}

    public static MaterialColor gemColor(Gem gem)
    {
        switch (gem)
        {
            case AMETHYST:
                return MaterialColor.COLOR_PURPLE;
            case DIAMOND:
                return MaterialColor.DIAMOND;
            case EMERALD:
                return MaterialColor.EMERALD;
            case LAPIS_LAZULI:
                return MaterialColor.LAPIS;
            case OPAL:
                return MaterialColor.COLOR_LIGHT_BLUE;
            case PYRITE:
                return MaterialColor.GOLD;
            case RUBY:
                return MaterialColor.COLOR_RED;
            case SAPPHIRE:
                return MaterialColor.COLOR_BLUE;
            case TOPAZ:
                return MaterialColor.COLOR_ORANGE;
            default:
                return MaterialColor.QUARTZ;
        }
    }

    public static int getFlowerColor(FruitBlocks.Tree tree)
    {
        switch (tree)
        {
            case CHERRY:
                return new Color(251, 135, 255).getRGB();
            case GREEN_APPLE:
                return new Color(252, 171, 255).getRGB();
            case LEMON:
                return new Color(215, 137, 217).getRGB();
            case OLIVE:
                return new Color(206, 198, 207).getRGB();
            case ORANGE:
                return new Color(251, 242, 252).getRGB();
            case PEACH:
                return new Color(230, 126, 188).getRGB();
            case PLUM:
                return new Color(165, 70, 189).getRGB();
            case RED_APPLE:
                return new Color(252, 171, 255).getRGB();
            default:
                return -1;
        }
    }

    @SuppressWarnings("deprecation")
    public static Color getAverageColor(Level level, BlockState state)
    {
        TextureAtlasSprite sprite = Minecraft.getInstance().getBlockRenderer().getBlockModel(state).getQuads(state, Direction.NORTH, level.getRandom()).get(0).getSprite();
        int x0 = sprite.getX();
        int y0 = sprite.getY();
        int x1 = x0 + sprite.getWidth();
        int y1 = y0 + sprite.getHeight();
        long sumr = 0, sumg = 0, sumb = 0;
        for (int xs = x0; xs < x1; xs++)
        {
            for (int ys = y0; ys < y1; ys++)
            {
                Color pixel = new Color(sprite.getPixelRGBA(0, xs, ys));
                sumr += pixel.getRed();
                sumg += pixel.getGreen();
                sumb += pixel.getBlue();
            }
        }
        int num = sprite.getWidth() * sprite.getHeight();
        return new Color(sumr / num, sumg / num, sumb / num);
    }

    public static ResourceLocation animalTexture(String name)
    {
        return identifier("textures/entity/animal/" + name + ".png");
    }

    public static RandomSource randomSource(int posX, int posY, int posZ)
    {
        long i = Mth.getSeed(posX, posY, posZ);
        return new LegacyRandomSource(i);
    }

    public static BlockPos getNearestBlock(WorldGenLevel level, BlockPos sourcePos, int radius, Block targetBlock)
    {      
        BlockPos closestPos = null;
        BlockPos checkPos = sourcePos;

        for (int x = sourcePos.getX() - radius; x < sourcePos.getX() + radius; x++)
        {
            for (int z = sourcePos.getZ() - radius; z < sourcePos.getZ() + radius; z++)
            {
                checkPos = new BlockPos(x, sourcePos.getY(), z);
                if (level.getBlockState(checkPos).getBlock() == targetBlock)
                {
                    // check if it is closer than any previously found position
                    if (closestPos == null || 
                            distanceToSqr(sourcePos, sourcePos.getX() - checkPos.getX(), 
                                                sourcePos.getY() - checkPos.getY(),
                                                sourcePos.getZ() - checkPos.getZ())
                            < distanceToSqr(sourcePos, sourcePos.getX() - closestPos.getX(), 
                                                sourcePos.getY() - closestPos.getY(),
                                                sourcePos.getZ() - closestPos.getZ()))
                    {
                        closestPos = checkPos;
                    }
                }
            }
        }
        return closestPos;
    }

    public static int distanceToSqr(BlockPos sourcePos, int x, int y, int z)
    {
        int d0 = sourcePos.getX() - x;
        int d1 = sourcePos.getY() - y;
        int d2 = sourcePos.getZ() - z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public static int distanceToNearestBlock(BlockPos sourcePos, BlockPos nearestBlockPos)
    {
        int d0 = sourcePos.getX() - nearestBlockPos.getX();
        int d1 = sourcePos.getY() - nearestBlockPos.getY();
        int d2 = sourcePos.getZ() - nearestBlockPos.getZ();
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public static float catmullrom(float delta, float startPoint, float start, float end, float endPoint)
    {
        return 0.5F * (2.0F * start + (end - startPoint) * delta + (2.0F * startPoint - 5.0F * start + 4.0F * end - endPoint) * delta * delta + (3.0F * start - startPoint - 3.0F * end + endPoint) * delta * delta * delta);
    }

    public static void spawnParticleBelow(Level level, BlockPos pos, Random random, ParticleOptions options)
    {
        double d0 = (double)pos.getX() + random.nextDouble();
        double d1 = (double)pos.getY() - 0.05D;
        double d2 = (double)pos.getZ() + random.nextDouble();
        level.addParticle(options, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }
}
