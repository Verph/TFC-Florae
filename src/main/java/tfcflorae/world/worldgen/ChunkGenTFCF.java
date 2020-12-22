package tfcflorae.world.worldgen;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.registries.ForgeRegistry;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateHelper;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.CustomChunkPrimer;
import net.dries007.tfc.world.classic.DataLayer;
import net.dries007.tfc.world.classic.WorldEntitySpawnerTFC;
import net.dries007.tfc.world.classic.WorldGenSettings;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.dries007.tfc.world.classic.genlayers.GenLayerTFC;
import net.dries007.tfc.world.classic.genlayers.datalayers.drainage.GenDrainageLayer;
import net.dries007.tfc.world.classic.genlayers.datalayers.ph.GenPHLayer;
import net.dries007.tfc.world.classic.mapgen.MapGenCavesTFC;
import net.dries007.tfc.world.classic.mapgen.MapGenRavineTFC;
import net.dries007.tfc.world.classic.mapgen.MapGenRiverRavine;
import net.dries007.tfc.world.classic.worldgen.*;

import static net.dries007.tfc.world.classic.WorldTypeTFC.ROCKLAYER2;
import static net.dries007.tfc.world.classic.WorldTypeTFC.ROCKLAYER3;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS;

@SuppressWarnings("WeakerAccess")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChunkGenTFCF implements IChunkGenerator
{
    /* This is done here rather than GameRegistry.registerWorldGenerator since we need to control the ordering of them better */
    private static final IWorldGenerator SOIL_PITS_GEN_TFCF = new WorldGenSoilPitsTFCF();

    public final WorldGenSettings s;
    private final World world;
    private final long seed;
    private final Random rand;
    private final NoiseGeneratorOctaves noiseGen1;
    private final NoiseGeneratorOctaves noiseGen2;
    private final NoiseGeneratorOctaves noiseGen3;
    private final NoiseGeneratorOctaves noiseGen4;
    private final NoiseGeneratorOctaves noiseGen5;
    private final NoiseGeneratorOctaves noiseGen6;
    private final NoiseGeneratorOctaves mobSpawnerNoise;
    private final NoiseGeneratorPerlin noiseGen7; // Rainfall
    private final NoiseGeneratorPerlin noiseGen8; // Flora Density
    private final NoiseGeneratorPerlin noiseGen9; // Flora Diversity
    private final NoiseGeneratorPerlin noiseGen10; // Temperature
    private final GenLayerTFC rocksGenLayer1;
    private final GenLayerTFC rocksGenLayer2;
    private final GenLayerTFC rocksGenLayer3;
    private final GenLayerTFC stabilityGenLayer;
    private final GenLayerTFC phGenLayer;
    private final GenLayerTFC drainageGenLayer;
    private final double[] noise1 = new double[425];
    private final double[] noise2 = new double[425];
    private final double[] noise3 = new double[425];
    private final double[] noise4 = new double[256];
    private final double[] noise5 = new double[425];
    private final double[] noise6 = new double[425];
    private final double[] heightMap = new double[425];
    private final Biome[] biomes = new Biome[324];
    private final DataLayer[] stabilityLayer = new DataLayer[256];
    private final DataLayer[] drainageLayer = new DataLayer[256];
    private final int[] seaLevelOffsetMap = new int[256];
    private final int[] chunkHeightMap = new int[256];

    private final MapGenBase caveGen;
    private final MapGenBase surfaceRavineGen;
    private final MapGenBase ravineGen;
    private final MapGenBase riverRavineGen;

    private final int seaLevel = 32;
    private final int yOffset = 112;
    private final float rainfallSpread, floraDensitySpread, floraDiversitySpread;
    private int[] rockLayer1 = new int[256];
    private int[] rockLayer2 = new int[256];
    private int[] rockLayer3 = new int[256];
    private float rainfall;
    private float averageTemp;

    public ChunkGenTFCF(World w, String settingsString)
    {
        world = w;
        seed = world.getSeed();
        rand = new Random(seed);
        s = WorldGenSettings.fromString(settingsString).build();

        noiseGen1 = new NoiseGeneratorOctaves(rand, 4);
        noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
        noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
        noiseGen4 = new NoiseGeneratorOctaves(rand, 4);
        noiseGen5 = new NoiseGeneratorOctaves(rand, 2);
        noiseGen6 = new NoiseGeneratorOctaves(rand, 1);
        mobSpawnerNoise = new NoiseGeneratorOctaves(rand, 8);

        rocksGenLayer1 = GenLayerTFC.initializeRock(seed + 1, RockCategory.Layer.TOP, s.rockLayerSize);
        rocksGenLayer2 = GenLayerTFC.initializeRock(seed + 2, RockCategory.Layer.MIDDLE, s.rockLayerSize);
        rocksGenLayer3 = GenLayerTFC.initializeRock(seed + 3, RockCategory.Layer.BOTTOM, s.rockLayerSize);

        noiseGen7 = new NoiseGeneratorPerlin(new Random(seed + 4), 4);
        noiseGen8 = new NoiseGeneratorPerlin(new Random(seed + 5), 4);
        noiseGen9 = new NoiseGeneratorPerlin(new Random(seed + 6), 4);
        noiseGen10 = new NoiseGeneratorPerlin(new Random(seed + 7), 4);

        stabilityGenLayer = GenLayerTFC.initializeStability(seed + 9);
        phGenLayer = GenPHLayer.initializePH(seed + 10);
        drainageGenLayer = GenDrainageLayer.initialize(seed + 11);

        caveGen = TerrainGen.getModdedMapGen(new MapGenCavesTFC(stabilityLayer), InitMapGenEvent.EventType.CAVE);
        surfaceRavineGen = new MapGenRavineTFC(s.surfaceRavineRarity, s.surfaceRavineHeight, s.surfaceRavineVariability);
        ravineGen = new MapGenRavineTFC(s.ravineRarity, s.ravineHeight, s.ravineVariability);
        riverRavineGen = new MapGenRiverRavine(s.riverRavineRarity);

        // Load these now, because if config changes, shit will break
        rainfallSpread = (float) ConfigTFC.General.WORLD.rainfallSpreadFactor;
        floraDiversitySpread = (float) ConfigTFC.General.WORLD.floraDiversitySpreadFactor;
        floraDensitySpread = (float) ConfigTFC.General.WORLD.floraDensitySpreadFactor;
        world.setSeaLevel(WorldTypeTFC.SEALEVEL); // Set sea level so squids can spawn
        WorldEntitySpawnerTFC.init(); // Called here so only TFC Worlds are affected
    }

    @Override
    public Chunk generateChunk(int chunkX, int chunkZ)
    {
        Arrays.fill(noise1, 0);
        Arrays.fill(noise2, 0);
        Arrays.fill(noise3, 0);
        Arrays.fill(noise4, 0);
        Arrays.fill(noise5, 0);
        Arrays.fill(noise6, 0);
        Arrays.fill(seaLevelOffsetMap, 0);
        Arrays.fill(chunkHeightMap, 0);
        Arrays.fill(heightMap, 0);

        rand.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        ChunkPrimer chunkPrimerIn = new ChunkPrimer();

        world.getBiomeProvider().getBiomes(biomes, chunkX * 16 - 1, chunkZ * 16 - 1, 18, 18);

        rainfall = MathHelper.clamp(250f + 250f * rainfallSpread * (float) noiseGen7.getValue(chunkX * 0.005, chunkZ * 0.005), 0, 500);
        float floraDiversity = MathHelper.clamp(0.5f + 0.5f * floraDiversitySpread * (float) noiseGen9.getValue(chunkX * 0.005, chunkZ * 0.005), 0, 1);
        float floraDensity = MathHelper.clamp((0.3f + 0.2f * rainfall / 500f) + 0.4f * floraDensitySpread * (float) noiseGen8.getValue(chunkX * 0.05, chunkZ * 0.05), 0, 1);

        rockLayer1 = rocksGenLayer1.getInts(chunkX * 16, chunkZ * 16, 16, 16).clone();
        rockLayer2 = rocksGenLayer2.getInts(chunkX * 16, chunkZ * 16, 16, 16).clone();
        rockLayer3 = rocksGenLayer3.getInts(chunkX * 16, chunkZ * 16, 16, 16).clone();

        final float regionalFactor = 5f * 0.09f * (float) noiseGen10.getValue(chunkX * 0.05, chunkZ * 0.05); // Range -5 <> 5
        averageTemp = ClimateHelper.monthFactor(regionalFactor, Month.AVERAGE_TEMPERATURE_MODIFIER, chunkZ << 4);

        CustomChunkPrimer chunkPrimerOut = new CustomChunkPrimer();

        if (caveGen instanceof MapGenCavesTFC)
        {
            // Since this may be replaced by other mods (we give them the option, since 1.12 caves are bad)
            ((MapGenCavesTFC) caveGen).setGenerationData(rainfall, rockLayer1.clone());
        }
        caveGen.generate(world, chunkX, chunkZ, chunkPrimerOut);
        surfaceRavineGen.generate(world, chunkX, chunkZ, chunkPrimerOut);
        ravineGen.generate(world, chunkX, chunkZ, chunkPrimerOut);
        riverRavineGen.generate(world, chunkX, chunkZ, chunkPrimerOut);

        if (ConfigTFC.General.DEBUG.debugWorldGenDanger)
        {
            for (int x = 0; x < 16; ++x)
            {
                for (int z = 0; z < 16; ++z)
                {
                    chunkPrimerOut.setBlockState(x, 240, z, Blocks.STAINED_GLASS.getStateFromMeta(Biome.getIdForBiome(getBiomeOffset(x, z)) & 15));

                    chunkPrimerOut.setBlockState(x, 230, z, Blocks.STAINED_GLASS.getStateFromMeta(rockLayer1[z << 4 | x] & 15));
                    chunkPrimerOut.setBlockState(x, 220, z, Blocks.STAINED_GLASS.getStateFromMeta(rockLayer2[z << 4 | x] & 15));
                    chunkPrimerOut.setBlockState(x, 210, z, Blocks.STAINED_GLASS.getStateFromMeta(rockLayer3[z << 4 | x] & 15));

                    chunkPrimerOut.setBlockState(x, 252, z, Blocks.STAINED_GLASS.getStateFromMeta(stabilityLayer[x << 4 | z].layerID & 15));
                    chunkPrimerOut.setBlockState(x, 250, z, Blocks.STAINED_GLASS.getStateFromMeta(drainageLayer[x << 4 | z].layerID & 15));
                }
            }
        }

        Chunk chunk = new Chunk(world, chunkPrimerOut, chunkX, chunkZ);

        ChunkDataTFC chunkData = chunk.getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);
        if (chunkData == null) throw new IllegalStateException("ChunkData capability is missing.");
        chunkData.setGenerationData(rockLayer1, rockLayer2, rockLayer3, stabilityLayer, drainageLayer, seaLevelOffsetMap, rainfall, regionalFactor, averageTemp, floraDensity, floraDiversity);

        byte[] biomeIds = chunk.getBiomeArray();
        for (int x = 0; x < 16; ++x)
        {
            for (int z = 0; z < 16; ++z)
            {
                biomeIds[z << 4 | x] = (byte) Biome.getIdForBiome(getBiomeOffset(x, z));
            }
        }

        chunk.setHeightMap(chunkHeightMap);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int chunkX, int chunkZ)
    {
        ForgeEventFactory.onChunkPopulate(true, this, world, rand, chunkX, chunkZ, false);
        BlockFalling.fallInstantly = true;
        final int worldX = chunkX << 4;
        final int worldZ = chunkZ << 4;
        BlockPos blockpos = new BlockPos(worldX, 0, worldZ);
        final Biome biome = world.getBiome(blockpos.add(16, 0, 16));
        rand.setSeed(world.getSeed());
        rand.setSeed((long) chunkX * (rand.nextLong() / 2L * 2L + 1L) + (long) chunkZ * (rand.nextLong() / 2L * 2L + 1L) ^ world.getSeed());

        // First, do all terrain related features
        SOIL_PITS_GEN_TFCF.generate(rand, chunkX, chunkZ, world, this, world.getChunkProvider());

        ForgeEventFactory.onChunkPopulate(false, this, world, rand, chunkX, chunkZ, false);
        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z)
    {
        return false; //todo
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        // This is a temporary measure for making 1.12 closer to playable
        return world.getBiome(pos).getSpawnableList(creatureType);
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
    {
        return null; //todo
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z)
    {
        //todo
    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
    {
        return false; //todo
    }
    
    private Biome getBiomeOffset(int x, int z)
    {
        return biomes[(z + 1) * 18 + (x + 1)]; //todo: check, was (z + 1) + (x + 1) * 18
    }
}