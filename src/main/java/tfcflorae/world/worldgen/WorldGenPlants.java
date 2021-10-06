package tfcflorae.world.worldgen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.registries.ForgeRegistry;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockEpiphyteTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.plants.BlockShortGrassTFC;
import net.dries007.tfc.objects.blocks.plants.BlockTallGrassTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.ConfigTFCF;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.types.PlantsTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

public class WorldGenPlants extends WorldGenerator
{
    public static final float RAINFALL_SAND = 75;
    public static final float RAINFALL_SAND_SANDY_MIX = 125;
    public static final float RAINFALL_SANDY = 200; // Upper thresholds
    public static final float RAINFALL_SILTY = 275; // Lower thresholds
    public static final float RAINFALL_SILT_SILTY_MIX = 350;
    public static final float RAINFALL_SILT = 400;

    private Plant plant;

    public void setGeneratedPlant(Plant plantIn)
    {
        this.plant = plantIn;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        switch (plant.getPlantType())
        {
            case SHORT_GRASS:
            {
                BlockShortGrassTFC plantBlock = BlockShortGrassTFC.get(plant);
                IBlockState state = plantBlock.getDefaultState();

                ChunkDataTFC data = ChunkDataTFC.get(worldIn, position);
                if (data.isInitialized() && data.getRainfall() >= 200f)
                {
                    for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 4; ++i)
                    {
                        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                        if (plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.isAirBlock(blockpos) &&
                            plantBlock.canBlockStay(worldIn, blockpos, state))
                        {
                            int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                            setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockShortGrassTFC.AGE, plantAge));
                        }
                    }
                    break;
                }
            }
            case TALL_GRASS:
            {
                BlockTallGrassTFC plantBlock = BlockTallGrassTFC.get(plant);
                IBlockState state = plantBlock.getDefaultState();

                ChunkDataTFC data = ChunkDataTFC.get(worldIn, position);
                if (data.isInitialized() && data.getRainfall() >= 200f)
                {
                    for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 4; ++i)
                    {
                        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                        int j = 1 + rand.nextInt(plant.getMaxHeight());

                        for (int k = 0; k < j; ++k)
                        {
                            if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                                worldIn.isAirBlock(blockpos.up(k)) &&
                                plantBlock.canBlockStay(worldIn, blockpos.up(k), state))
                            {
                                int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                                setBlockAndNotifyAdequately(worldIn, blockpos.up(k), state.withProperty(BlockShortGrassTFC.AGE, plantAge));
                            }
                        }
                    }
                    break;
                }
            }
            case EPIPHYTE: // Only for monstera plants
            {
                BlockEpiphyteTFC plantBlock = BlockEpiphyteTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MONSTERA_EPIPHYTE));

                ChunkDataTFC data = ChunkDataTFC.get(worldIn, position);
                if (data.isInitialized())
                {
                    for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position); ++i)
                    {
                        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                        if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos) &&
                            plantBlock.canPlaceBlockAt(worldIn, blockpos))
                        {
                            int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                            setBlockAndNotifyAdequately(worldIn, blockpos, plantBlock.getStateForWorldGen(worldIn, blockpos).withProperty(BlockEpiphyteTFC.AGE, plantAge));
                        }
                    }
                    break;
                }
            }
        }
        return true;
    }
}