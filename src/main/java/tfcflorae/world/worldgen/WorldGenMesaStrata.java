package tfcflorae.world.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.types.DefaultRocks;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.dries007.tfc.world.classic.worldgen.vein.Vein;
import net.dries007.tfc.world.classic.worldgen.vein.VeinRegistry;
import tfcflorae.ConfigTFCF;
import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.objects.blocks.plants.BlockPlant.BlockPlantTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

public class WorldGenMesaStrata implements IWorldGenerator
{
    private RockCategory category;

    public static final float RAINFALL_DRY_GRASS = 150;
    protected static final IBlockState HARDENED_CLAY = Blocks.HARDENED_CLAY.getDefaultState();
    protected static final IBlockState STAINED_HARDENED_CLAY = Blocks.STAINED_HARDENED_CLAY.getDefaultState();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        ChunkDataTFC data = ChunkDataTFC.get(world, chunkBlockPos);
        if (!data.isInitialized() && !ConfigTFCF.General.WORLD.enableMesaStrata) return;

        for (int x = 0; x < 16; ++x)
        {
            for (int z = 0; z < 16; ++z)
            {
                BlockPos strataLayer = chunkBlockPos.add(x, WorldTypeTFC.SEALEVEL, z);
                //TFCFlorae.getLog().warn("TFCFlorae: Current 'strataLayer' is " + "X: " + strataLayer.getX() + ", Y: " + strataLayer.getY() + ", Z: " + strataLayer.getZ());
                final Biome b = world.getBiome(strataLayer);
                final float avgTemperature = ClimateTFC.getAvgTemp(world, strataLayer);
                final float rainfall = ChunkDataTFC.getRainfall(world, strataLayer);

                if (rainfall < +1.3 * random.nextGaussian() + RAINFALL_DRY_GRASS && avgTemperature >= 15f)
                {
                    //TFCFlorae.getLog().warn("Biome at X: " + strataLayer.getX() + " Z: " + strataLayer.getZ() + " is " + b);
                    //if (b == BiomesTFC.MESA || b == BiomesTFC.MESA_PLATEAU || b == BiomesTFC.MESA_BRYCE || b == BiomesTFC.MESA_PLATEAU_M || BiomesTFC.isMesaBiome(b))
                    if (BiomesTFC.isMesaBiome(b))
                    {
                        for (int y = WorldTypeTFC.SEALEVEL; y < world.getTopSolidOrLiquidBlock(strataLayer).getY(); ++y)
                        {
                            BlockPos currentBlock = chunkBlockPos.add(x, y, z);
                            IBlockState currentBlockState = world.getBlockState(currentBlock);
                            IBlockState currentBlockStateTop = world.getBlockState(currentBlock.up());
                            //TFCFlorae.getLog().warn("TFCFlorae: Current 'currentBlock' is " + "X: " + currentBlock.getX() + ", Y: " + currentBlock.getY() + ", Z: " + currentBlock.getZ());
                            //if (currentBlockState instanceof BlockRockVariant && ((BlockRockVariant)(currentBlockState.getBlock())).getRock().getRockCategory() == TFCRegistries.ROCK_CATEGORIES.getValue(DefaultRocks.SEDIMENTARY))
                            if ((y <= WorldTypeTFC.SEALEVEL + 5 && (BlocksTFC.isRawStone(currentBlockState) || BlocksTFC.isGround(currentBlockState) || BlocksTFCF.isGround(currentBlockState) || BlocksTFC.isSoil(currentBlockState) || BlocksTFCF.isSoil(currentBlockState)) && !(BlocksTFC.isGrass(currentBlockState) || BlocksTFC.isSand(currentBlockState) || BlocksTFC.isClay(currentBlockState) || BlocksTFCF.isGrass(currentBlockState) || BlocksTFCF.isSand(currentBlockState) || BlocksTFCF.isClay(currentBlockState))) || 
                                (y > WorldTypeTFC.SEALEVEL + 5 && (BlocksTFC.isRawStone(currentBlockState) || BlocksTFC.isGround(currentBlockState) || BlocksTFCF.isGround(currentBlockState) || BlocksTFC.isSoil(currentBlockState) || BlocksTFCF.isSoil(currentBlockState))))
                            {
                                if (y >= strataLayer.getY() && y <= strataLayer.getY() + 2)
                                {
                                    world.setBlockState(currentBlock, HARDENED_CLAY, 2);
                                }
                                if (y >= strataLayer.getY() + 3 && y <= strataLayer.getY() + 5)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.BLUE), 2);
                                }
                                if (y >= strataLayer.getY() + 6 && y <= strataLayer.getY() + 9)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.GREEN), 2);
                                }
                                if (y >= strataLayer.getY() + 10 && y <= strataLayer.getY() + 12)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE), 2);
                                }
                                if (y >= strataLayer.getY() + 13 && y <= strataLayer.getY() + 15)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.MAGENTA), 2);
                                }
                                if (y >= strataLayer.getY() + 16 && y <= strataLayer.getY() + 19)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.PINK), 2);
                                }
                                if (y >= strataLayer.getY() + 20 && y <= strataLayer.getY() + 23)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.PURPLE), 2);
                                }
                                if (y >= strataLayer.getY() + 24 && y <= strataLayer.getY() + 26)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE), 2);
                                }
                                if (y >= strataLayer.getY() + 27 && y <= strataLayer.getY() + 28)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.PINK), 2);
                                }
                                if (y >= strataLayer.getY() + 29 && y <= strataLayer.getY() + 32)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.WHITE), 2);
                                }
                                if (y >= strataLayer.getY() + 33 && y <= strataLayer.getY() + 35)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.SILVER), 2);
                                }
                                if (y >= strataLayer.getY() + 36 && y <= strataLayer.getY() + 38)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.CYAN), 2);
                                }
                                if (y >= strataLayer.getY() + 39 && y <= strataLayer.getY() + 46)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.LIGHT_BLUE), 2);
                                }
                                if (y >= strataLayer.getY() + 47 && y <= strataLayer.getY() + 54)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.RED), 2);
                                }
                                if (y >= strataLayer.getY() + 55 && y <= strataLayer.getY() + 56)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.YELLOW), 2);
                                }
                                if (y >= strataLayer.getY() + 57 && y <= strataLayer.getY() + 59)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.PINK), 2);
                                }
                                if (y >= strataLayer.getY() + 60 && y <= strataLayer.getY() + 61)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.BROWN), 2);
                                }
                                if (y >= strataLayer.getY() + 62 && y <= strataLayer.getY() + 65)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.PURPLE), 2);
                                }
                                if (y >= strataLayer.getY() + 66 && y <= strataLayer.getY() + 67)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.WHITE), 2);
                                }
                                if (y >= strataLayer.getY() + 68 && y <= strataLayer.getY() + 70)
                                {
                                    world.setBlockState(currentBlock, HARDENED_CLAY, 2);
                                }
                                if (y >= strataLayer.getY() + 71 && y <= strataLayer.getY() + 72)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE), 2);
                                }
                                if (y >= strataLayer.getY() + 73 && y <= strataLayer.getY() + 75)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.SILVER), 2);
                                }
                                if (y >= strataLayer.getY() + 76 && y <= strataLayer.getY() + 79)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.BLUE), 2);
                                }
                                if (y >= strataLayer.getY() + 80 && y <= strataLayer.getY() + 84)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.PURPLE), 2);
                                }
                                if (y >= strataLayer.getY() + 85 && y <= strataLayer.getY() + 86)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.LIME), 2);
                                }
                                if (y >= strataLayer.getY() + 87 && y <= strataLayer.getY() + 91)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.CYAN), 2);
                                }
                                if (y >= strataLayer.getY() + 92 && y <= strataLayer.getY() + 95)
                                {
                                    world.setBlockState(currentBlock, STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.YELLOW), 2);
                                }
                                if (y >= strataLayer.getY() + 95)
                                {
                                    world.setBlockState(currentBlock, HARDENED_CLAY, 2);
                                }
                            }
                            /*if (y + 1 >= WorldTypeTFC.SEALEVEL - 1 && (currentBlockStateTop instanceof BlockPlantTFC || currentBlockStateTop instanceof BlockPlantTFCF))
                            {
                                world.setBlockState(currentBlock, Blocks.AIR.getDefaultState(), 2);
                            }*/
                        }
                    }
                }
            }
        }
    }
}
