package tfcflorae.world.worldgen;

import scala.reflect.internal.Trees.Return;

import java.util.Random;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
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
        BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        ChunkDataTFC data = ChunkDataTFC.get(world, chunkBlockPos);

        Biome b = world.getBiome(chunkBlockPos);
        float avgTemperature = ClimateTFC.getAvgTemp(world, chunkBlockPos);
        float rainfall = ChunkDataTFC.getRainfall(world, chunkBlockPos);
        float floraDensity = data.getFloraDensity();
        float floraDiversity = data.getFloraDiversity();

        int coralsInChunk = 5 + random.nextInt(20);
        for (int i = 0; i < (coralsInChunk) * 5; i++)
        {
            final int x = (chunkX << 4) + random.nextInt(16) + 8;
            final int z = (chunkZ << 4) + random.nextInt(16) + 8;
            final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));

            if ((b == BiomesTFC.OCEAN || b == BiomesTFC.DEEP_OCEAN || b == BiomesTFC.BEACH || b == BiomesTFC.GRAVEL_BEACH) && world.provider.getDimension() == 0)
            {
                if (isValidPosition(world, pos) && pos.getY() < WorldTypeTFC.SEALEVEL - 1 && pos.getY() > 119 && floraDensity >= 0.4f && floraDiversity >= 0.3f && floraDensity <= 0.6f && floraDiversity <= 0.5f && avgTemperature >= 10f && avgTemperature <= 28f && rainfall >= 150f)
                {
                    int dyeColorRandom = random.nextInt(16);
                    if (dyeColorRandom == 0)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 1)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 2)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 3)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 4)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 5)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 6)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 7)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 8)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 9)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 10)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 11)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 12)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 13)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 14)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 15)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                    }
                }
            }
        }
        for (int i = 0; i < (coralsInChunk) * 100; i++)
        {
            final int x = (chunkX << 4) + random.nextInt(16) + 8;
            final int z = (chunkZ << 4) + random.nextInt(16) + 8;
            final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));

            if ((b == BiomesTFC.OCEAN || b == BiomesTFC.DEEP_OCEAN || b == BiomesTFC.BEACH || b == BiomesTFC.GRAVEL_BEACH) && world.provider.getDimension() == 0)
            {
                if (isValidPosition2(world, pos) && pos.getY() < WorldTypeTFC.SEALEVEL - 1 && pos.getY() > 119 && floraDensity >= 0.4f && floraDiversity >= 0.3f && avgTemperature >= 10f && rainfall >= 150f)
                {
                    int dyeColorRandom = random.nextInt(16);
                    if (dyeColorRandom == 0)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.BLACK).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 1)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.BLUE).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 2)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.BROWN).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 3)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.CYAN).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 4)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.GRAY).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 5)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.GREEN).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 6)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.LIGHT_BLUE).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 7)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.LIME).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 8)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.MAGENTA).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 9)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.ORANGE).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 10)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.PINK).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 11)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.PURPLE).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 12)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.RED).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 13)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.SILVER).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 14)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.WHITE).getDefaultState());
                        }
                    }
                    else if (dyeColorRandom == 15)
                    {
                        int coralType = random.nextInt(10);
                        if (coralType == 0)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 1)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 2)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 3)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 4)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 5)
                        {
                            world.setBlockState(pos, BlockCoral.TUBE_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 6)
                        {
                            world.setBlockState(pos, BlockCoral.BRAIN_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 7)
                        {
                            world.setBlockState(pos, BlockCoral.BUBBLE_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 8)
                        {
                            world.setBlockState(pos, BlockCoral.FIRE_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                        else if (coralType == 9)
                        {
                            world.setBlockState(pos, BlockCoral.HORN_CORAL_FAN.get(EnumDyeColor.YELLOW).getDefaultState());
                        }
                    }
                }
            }
        }
    }

    protected boolean isValidPosition(World world, BlockPos pos)
    {
        IBlockState up = world.getBlockState(pos.up());
        IBlockState down = world.getBlockState(pos.down());
        IBlockState north = world.getBlockState(pos.north());
        IBlockState south = world.getBlockState(pos.south());
        IBlockState east = world.getBlockState(pos.east());
        IBlockState west = world.getBlockState(pos.west());
        return ((BlocksTFC.isGround(down) || up.getBlock() instanceof BlockCoralBlock || down.getBlock() instanceof BlockCoralBlock || north.getBlock() instanceof BlockCoralBlock || south.getBlock() instanceof BlockCoralBlock || east.getBlock() instanceof BlockCoralBlock || west.getBlock() instanceof BlockCoralBlock) && !(world.isAirBlock(pos.up())));
    }

    protected boolean isValidPosition2(World world, BlockPos pos)
    {
        IBlockState up = world.getBlockState(pos.up());
        IBlockState down = world.getBlockState(pos.down());
        IBlockState north = world.getBlockState(pos.north());
        IBlockState south = world.getBlockState(pos.south());
        IBlockState east = world.getBlockState(pos.east());
        IBlockState west = world.getBlockState(pos.west());
        return ((up.getBlock() instanceof BlockCoralBlock || down.getBlock() instanceof BlockCoralBlock || north.getBlock() instanceof BlockCoralBlock || south.getBlock() instanceof BlockCoralBlock || east.getBlock() instanceof BlockCoralBlock || west.getBlock() instanceof BlockCoralBlock) && !(world.isAirBlock(pos.up())));
    }
}