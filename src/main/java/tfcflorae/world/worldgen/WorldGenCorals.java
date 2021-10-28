package tfcflorae.world.worldgen;

import scala.reflect.internal.Trees.Return;

import java.util.Random;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.biomes.BiomeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.ConfigTFCF;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.groundcover.BlockCoral;
import tfcflorae.objects.blocks.groundcover.BlockCoralBlock;

@ParametersAreNonnullByDefault
public class WorldGenCorals implements IWorldGenerator
{
	@Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (!(chunkGenerator instanceof ChunkGenTFC)) return;

        generateCoral(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
    }

    private void generateCoral(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        BlockPos chunkPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        ChunkPos forgeChunkPos = new ChunkPos(chunkPos); // actual ChunkPos instead of BlockPos, used for events
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, random, forgeChunkPos));

        ChunkDataTFC data = ChunkDataTFC.get(world, chunkPos);
        if (!data.isInitialized()) return;

        final float avgTemperature = ClimateTFC.getAvgTemp(world, chunkPos);
        final float rainfall = ChunkDataTFC.getRainfall(world, chunkPos);
        final float floraDensity = data.getFloraDensity(); // Use for various plant based decoration (tall grass, those vanilla jungle shrub things, etc.)
        final float floraDiversity = data.getFloraDiversity();

        if (chunkPos.getY() < WorldTypeTFC.SEALEVEL - 1 && chunkPos.getY() > 120 && floraDensity >= 0.4f && floraDiversity >= 0.3f && avgTemperature >= 10f && rainfall >= 100f && world.provider.getDimension() == 0)
        {
            final Biome b = world.getBiome(chunkPos);
            if (b == BiomesTFC.OCEAN || b == BiomesTFC.DEEP_OCEAN || b == BiomesTFC.BEACH || b == BiomesTFC.GRAVEL_BEACH)
            {
                int coralsInChunkOnCoral = 5 + random.nextInt(26);
                for (int i = 1; i < (coralsInChunkOnCoral + floraDensity) * 15; i++)
                {
                    BlockPos blockPos = world.getTopSolidOrLiquidBlock(chunkPos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8));
                    if (isValidPosition(world, blockPos))
                    {
                        int dyeColorRandom = random.nextInt(16);
                        if (dyeColorRandom == 0)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 1)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 2)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 3)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 4)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 5)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 6)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 7)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 8)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 9)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 10)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 11)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 12)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 13)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 14)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 15)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                        }
                    }
                }
                int coralsInChunkOnCoralBlock = 10 + random.nextInt(51);
                for (int i = 1; i < (coralsInChunkOnCoralBlock + floraDensity) * 15; i++)
                {
                    BlockPos blockPos = world.getTopSolidOrLiquidBlock(chunkPos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8));
                    if (isValidPositionOnCoral(world, blockPos))
                    {
                        int dyeColorRandom = random.nextInt(16);
                        if (dyeColorRandom == 0)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 1)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 2)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 3)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 4)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 5)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 6)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 7)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 8)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 9)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 10)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 11)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 12)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 13)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 14)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                            }
                        }
                        else if (dyeColorRandom == 15)
                        {
                            int coralType = random.nextInt(10);
                            if (coralType == 0)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 1)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 2)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 3)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 4)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 5)
                            {
                                world.setBlockState(blockPos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 6)
                            {
                                world.setBlockState(blockPos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 7)
                            {
                                world.setBlockState(blockPos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 8)
                            {
                                world.setBlockState(blockPos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                            else if (coralType == 9)
                            {
                                world.setBlockState(blockPos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                            }
                        }
                    }
                }
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, random, forgeChunkPos));
    }

    protected boolean isValidPosition(World world, BlockPos pos)
    {
        IBlockState up = world.getBlockState(pos.up());
        IBlockState down = world.getBlockState(pos.down());
        IBlockState north = world.getBlockState(pos.north());
        IBlockState south = world.getBlockState(pos.south());
        IBlockState east = world.getBlockState(pos.east());
        IBlockState west = world.getBlockState(pos.west());
        return (BlocksTFC.isGround(down) || down.getBlock() instanceof BlockCoralBlock || north.getBlock() instanceof BlockCoralBlock || south.getBlock() instanceof BlockCoralBlock || east.getBlock() instanceof BlockCoralBlock || west.getBlock() instanceof BlockCoralBlock) && (BlocksTFC.isSaltWater(world.getBlockState(pos.up())) || up.getBlock() instanceof BlockCoralBlock || up.getBlock() instanceof BlockCoral);
    }

    protected boolean isValidPositionOnCoral(World world, BlockPos pos)
    {
        IBlockState up = world.getBlockState(pos.up());
        IBlockState down = world.getBlockState(pos.down());
        IBlockState north = world.getBlockState(pos.north());
        IBlockState south = world.getBlockState(pos.south());
        IBlockState east = world.getBlockState(pos.east());
        IBlockState west = world.getBlockState(pos.west());
        return ((down.getBlock() instanceof BlockCoralBlock || north.getBlock() instanceof BlockCoralBlock || south.getBlock() instanceof BlockCoralBlock || east.getBlock() instanceof BlockCoralBlock || west.getBlock() instanceof BlockCoralBlock) && (BlocksTFC.isSaltWater(world.getBlockState(pos.up())) || up.getBlock() instanceof BlockCoralBlock || up.getBlock() instanceof BlockCoral));
    }
}