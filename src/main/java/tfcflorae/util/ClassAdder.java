package tfcflorae.util;

import java.io.*;
import java.util.Objects;

import com.google.common.io.Files;

import org.apache.commons.io.FileUtils;
import org.jline.terminal.impl.jna.freebsd.CLibrary.winsize;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;

import net.dries007.tfc.TerraFirmaCraft;

import tfcflorae.TFCFlorae;

public class ClassAdder
{
    static final File TFC = Loader.instance().getIndexedModList().get(TerraFirmaCraft.MOD_ID).getSource();
    static final String TFC_NAME = removeAt(Files.getNameWithoutExtension(TFC.getName()), "-patched");

    // Files
    private static final String CEH_FROM = "assets/tfcflorae/bansoukou/tfc/CommonEventHandler.class";

    private static final String FALLINGBLOCKMANAGER_FROM = "assets/tfcflorae/bansoukou/tfc/api/util/FallingBlockManager.class";
    private static final String FALLINGBLOCKMANAGER1_FROM = "assets/tfcflorae/bansoukou/tfc/api/util/FallingBlockManager$Specification$IBeginFallCallback.class";
    private static final String FALLINGBLOCKMANAGER2_FROM = "assets/tfcflorae/bansoukou/tfc/api/util/FallingBlockManager$Specification$ICollapseChecker.class";
    private static final String FALLINGBLOCKMANAGER3_FROM = "assets/tfcflorae/bansoukou/tfc/api/util/FallingBlockManager$Specification$IEndFallCallback.class";
    private static final String FALLINGBLOCKMANAGER4_FROM = "assets/tfcflorae/bansoukou/tfc/api/util/FallingBlockManager$Specification$IFallDropsProvider.class";
    private static final String FALLINGBLOCKMANAGER5_FROM = "assets/tfcflorae/bansoukou/tfc/api/util/FallingBlockManager$Specification.class";
    private static final String IFRUITTREEGENERATOR_FROM = "assets/tfcflorae/bansoukou/tfc/api/util/IFruitTreeGenerator.class";
    private static final String ITREEGENERATOR_FROM = "assets/tfcflorae/bansoukou/tfc/api/util/ITreeGenerator.class";

    private static final String ENTITYANIMALTFC_FROM = "assets/tfcflorae/bansoukou/tfc/objects/entity/animal/EntityAnimalTFC.class";
    private static final String ENTITYDONKEYTFC_FROM = "assets/tfcflorae/bansoukou/tfc/objects/entity/animal/EntityDonkeyTFC.class";
    private static final String ENTITYHORSETFC_FROM = "assets/tfcflorae/bansoukou/tfc/objects/entity/animal/EntityHorseTFC.class";
    private static final String ENTITYLLAMATFC_FROM = "assets/tfcflorae/bansoukou/tfc/objects/entity/animal/EntityLlamaTFC.class";
    private static final String ENTITYMULETFC_FROM = "assets/tfcflorae/bansoukou/tfc/objects/entity/animal/EntityMuleTFC.class";
    private static final String ENTITYOCELOTTFC_FROM = "assets/tfcflorae/bansoukou/tfc/objects/entity/animal/EntityOcelotTFC.class";
    private static final String ENTITYPARROTTFC_FROM = "assets/tfcflorae/bansoukou/tfc/objects/entity/animal/EntityParrotTFC.class";
    private static final String ENTITYPOLARBEARTFC_FROM = "assets/tfcflorae/bansoukou/tfc/objects/entity/animal/EntityPolarBearTFC.class";
    private static final String ENTITYWOLFTFC_FROM = "assets/tfcflorae/bansoukou/tfc/objects/entity/animal/EntityWolfTFC.class";

    private static final String BLOCKSTFC1_FROM = "assets/tfcflorae/bansoukou/tfc/objects/blocks/BlocksTFC$1.class";
    private static final String BLOCKSTFC_FROM = "assets/tfcflorae/bansoukou/tfc/objects/blocks/BlocksTFC.class";
    private static final String BLOCKFLUIDHOTWATER_FROM = "assets/tfcflorae/bansoukou/tfc/objects/blocks/BlockFluidHotWater.class";

    private static final String BLOCKBERRYBUSH1_FROM = "assets/tfcflorae/bansoukou/tfc/objects/blocks/agriculture/BlockBerryBush$1.class";
    private static final String BLOCKBERRYBUSH_FROM = "assets/tfcflorae/bansoukou/tfc/objects/blocks/agriculture/BlockBerryBush.class";
    private static final String BLOCK_CROPDEAD_FROM = "assets/tfcflorae/bansoukou/tfc/objects/blocks/agriculture/BlockCropDead.class";
    private static final String BLOCK_CROP_FROM = "assets/tfcflorae/bansoukou/tfc/objects/blocks/agriculture/BlockCropTFC.class";
    private static final String BLOCKFRUITTREETRUNK_FROM = "assets/tfcflorae/bansoukou/tfc/objects/blocks/agriculture/BlockFruitTreeTrunk.class";

    private static final String BLOCKPLANTTFC_FROM = "assets/tfcflorae/bansoukou/tfc/objects/blocks/plants/BlockPlantTFC.class";
    private static final String BLOCKPLANTTFC1_FROM = "assets/tfcflorae/bansoukou/tfc/objects/blocks/plants/BlockPlantTFC$1.class";
    private static final String BLOCKSHORTGRASSTFC_FROM = "assets/tfcflorae/bansoukou/tfc/objects/blocks/plants/BlockShortGrassTFC.class";
    private static final String BLOCKTALLGRASSTFC_FROM = "assets/tfcflorae/bansoukou/tfc/objects/blocks/plants/BlockTallGrassTFC.class";
    private static final String BLOCKHANGINGTFC_FROM = "assets/tfcflorae/bansoukou/tfc/objects/blocks/plants/BlockHangingPlantTFC.class";

    private static final String BLOCKROCKVARIANTCONNECTED_FROM = "assets/tfcflorae/bansoukou/tfc/objects/blocks/stone/BlockRockVariantConnected.class";

    private static final String ITEMFIRESTARTER_FROM = "assets/tfcflorae/bansoukou/tfc/objects/items/ItemFireStarter.class";
    private static final String ITEM_SEEDS_FROM = "assets/tfcflorae/bansoukou/tfc/objects/items/ItemSeedsTFC.class";
    private static final String ITEMBLOCKBLOCKCROPDEAD_FROM = "assets/tfcflorae/bansoukou/tfc/objects/items/itemblock/ItemBlockCropDeadWaterTFC.class";
    private static final String ITEMBLOCKBLOCKCROP_FROM = "assets/tfcflorae/bansoukou/tfc/objects/items/itemblock/ItemBlockCropWaterTFC.class";
    private static final String ITEMPROSPECTORSPICKRESULTTYPE_FROM = "assets/tfcflorae/bansoukou/tfc/objects/items/metal/ItemProspectorPick$ProspectResult$Type.class";
    private static final String ITEMPROSPECTORSPICKRESULT_FROM = "assets/tfcflorae/bansoukou/tfc/objects/items/metal/ItemProspectorPick$ProspectResult.class";
    private static final String ITEMPROSPECTORSPICK_FROM = "assets/tfcflorae/bansoukou/tfc/objects/items/metal/ItemProspectorPick.class";
    private static final String ITEMPROSPECTORSPICK1_FROM = "assets/tfcflorae/bansoukou/tfc/objects/items/metal/ItemProspectorPick$1.class";
    private static final String ITEMMETALTOOL1_FROM = "assets/tfcflorae/bansoukou/tfc/objects/items/metal/ItemMetalTool$1.class";
    private static final String ITEMMETALTOOL_FROM = "assets/tfcflorae/bansoukou/tfc/objects/items/metal/ItemMetalTool.class";
    private static final String ITEMROCKSHOVEL_FROM = "assets/tfcflorae/bansoukou/tfc/objects/items/rock/ItemRockShovel.class";

    private static final String REGENWILDCROPS_FROM = "assets/tfcflorae/bansoukou/tfc/util/RegenWildCrops.class";
    private static final String WORLDREGEN_FROM = "assets/tfcflorae/bansoukou/tfc/util/WorldRegenHandler.class";
    private static final String CLIMATETFC_FROM = "assets/tfcflorae/bansoukou/tfc/util/climate/ClimateTFC.class";

    private static final String CHUNKGENTFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/ChunkGenTFC.class";
    private static final String DATALAYER_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/DataLayer.class";

    private static final String BIOMEDECORATOR_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/biomes/BiomeDecoratorTFC.class";
    private static final String BIOMEDECORATOR1_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/biomes/BiomeDecoratorTFC$1.class";
    private static final String BIOMEMESATFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/biomes/BiomeMesaTFC.class";
    private static final String BIOMESTFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/biomes/BiomesTFC.class";
    private static final String BIOMETFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/biomes/BiomeTFC.class";

    private static final String MAPGENCAVESTFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/mapgen/MapGenCavesTFC.class";
    private static final String MAPGENRAVINETFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/mapgen/MapGenRavineTFC.class";
    private static final String MAPGENRIVERRAVINE_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/mapgen/MapGenRiverRavine.class";

    private static final String CHUNKDATATFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/chunkdata/ChunkDataTFC.class";
    private static final String CHUNKDATATFC_STORAGE_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/chunkdata/ChunkDataTFC$ChunkDataStorage.class";
    private static final String CHUNKDATABLOCK_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/chunkdata/ChunkDataBlock.class";
    private static final String CHUNKDATACACHE_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/chunkdata/ChunkDataCache.class";

    private static final String SMOOTHBIOMETFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/GenLayerSmoothBiomeTFC.class";
    private static final String GENLAYERTFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/GenLayerTFC.class";

    private static final String GENLAYERBEACHTFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/biome/GenLayerBeachTFC.class";
    private static final String GENLAYERBIOMEEDGE_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/biome/GenLayerBiomeEdge.class";
    private static final String GENLAYERLAKESHORE_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/biome/GenLayerLakeShore.class";
    private static final String GENLAYERSHORETFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/biome/GenLayerShoreTFC.class";

    private static final String GENLAYERTREEINIT_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/datalayers/tree/GenLayerTreeInit.class";
    private static final String GENREGIONLAYER_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/datalayers/tree/GenRegionLayer.class";
    private static final String GENRIVERLAYER_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/datalayers/tree/GenRiverLayer.class";

    private static final String GENLAYERRIVERINITTFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/river/GenLayerRiverInitTFC.class";
    private static final String GENLAYERRIVERMIXTFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/river/GenLayerRiverMixTFC.class";
    private static final String GENLAYERRIVERTFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/river/GenLayerRiverTFC.class";
    private static final String GENLAYERRIDGEINITTFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/ridge/GenLayerRidgeInitTFC.class";
    private static final String GENLAYERRIDGEMIXTFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/ridge/GenLayerRidgeMixTFC.class";
    private static final String GENLAYERRIDGETFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/genlayers/ridge/GenLayerRidgeTFC.class";

    private static final String WORLDGENFISSURE_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/worldgen/WorldGenFissure.class";
    private static final String WORLDGENLARGEROCKS_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/worldgen/WorldGenLargeRocks.class";
    private static final String WORLDGENLOOSEROCKS_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/worldgen/WorldGenLooseRocks.class";
    private static final String WGPLANTS1_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/worldgen/WorldGenPlantTFC$1.class";
    private static final String WGPLANTS_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/worldgen/WorldGenPlantTFC.class";
    private static final String WORLDGENSANDTFC_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/worldgen/WorldGenSandTFC.class";
    private static final String WORLDGENSOILPITS_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/worldgen/WorldGenSoilPits.class";
    private static final String WORLDGENTREES_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/worldgen/WorldGenTrees.class";
    private static final String WORLDGENWILDCROPS_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/worldgen/WorldGenWildCrops.class";

    private static final String TREEGENBUSHES_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/worldgen/trees/TreeGenBushes.class";
    private static final String TREEGENKAPOK_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/worldgen/trees/TreeGenKapok.class";
    private static final String TREEGENSEQUOIA_FROM = "assets/tfcflorae/bansoukou/tfc/world/classic/worldgen/trees/TreeGenSequoia.class";

    // Main directory
    private static final String DIR = "bansoukou/" + TFC_NAME + "/net/dries007/tfc";

    // Subdirectories
    private static final String SUBDIR_API_UTIL = DIR + "/api/util";
    private static final String SUBDIR_ANIMAL = DIR + "/objects/entity/animal";
    private static final String SUBDIR_ITEM = DIR + "/objects/items";
    private static final String SUBDIR_ITEM_ITEMBLOCK = DIR + "/objects/items/itemblock";
    private static final String SUBDIR_ITEMMETALTOOL = DIR + "/objects/items/metal";
    private static final String SUBDIR_ITEMROCKTOOL = DIR + "/objects/items/rock";
    private static final String SUBDIR_BLOCK = DIR + "/objects/blocks";
    private static final String SUBDIR_BLOCK_AGRICULTURE = DIR + "/objects/blocks/agriculture";
    private static final String SUBDIR_BLOCKPLANT = DIR + "/objects/blocks/plants";
    private static final String SUBDIR_STONE = DIR + "/objects/blocks/stone";
    private static final String SUBDIR_UTIL = DIR + "/util";
    private static final String SUBDIR_CLIMATE = DIR + "/util/climate";
    private static final String SUBDIR_WORLD = DIR + "/world/classic";
    private static final String SUBDIR_WORLD_BIOMES = DIR + "/world/classic/biomes";
    private static final String SUBDIR_WORLD_MAPGEN = DIR + "/world/classic/mapgen";
    private static final String SUBDIR_WORLD_CHUNKDATA = DIR + "/world/classic/chunkdata";
    private static final String SUBDIR_WORLD_GENLAYERS = DIR + "/world/classic/genlayers";
    private static final String SUBDIR_WORLD_GENLAYERS_BIOME = DIR + "/world/classic/genlayers/biome";
    private static final String SUBDIR_WORLD_GENLAYERS_DATALAYERS = DIR + "/world/classic/genlayers/datalayers";
    private static final String SUBDIR_WORLD_GENLAYERS_DATALAYERS_TREE = DIR + "/world/classic/genlayers/datalayers/tree";
    private static final String SUBDIR_WORLD_GENLAYERS_RIVER = DIR + "/world/classic/genlayers/river";
    private static final String SUBDIR_WORLD_GENLAYERS_RIDGE = DIR + "/world/classic/genlayers/ridge";
    private static final String SUBDIR_WORLD_WORLDGEN = DIR + "/world/classic/worldgen";
    private static final String SUBDIR_WORLD_TREES = DIR + "/world/classic/worldgen/trees";
    //private static final String DIR = "bansoukou/" + TFC_NAME + "/net/dries007/tfc/world/classic/biomes";

    public static void addClasses(File dir)
    {
        // Make directories if they are missing.
        File bansoukouFolder = new File(Launch.minecraftHome, DIR);
        if (!bansoukouFolder.exists())
        {
            bansoukouFolder.mkdirs();
        }
        File subDirApiUtil = new File(Launch.minecraftHome, SUBDIR_API_UTIL);
        if (!subDirApiUtil.exists())
        {
            subDirApiUtil.mkdirs();
        }
        File subDirAnimal = new File(Launch.minecraftHome, SUBDIR_ANIMAL);
        if (!subDirAnimal.exists())
        {
            subDirAnimal.mkdirs();
        }
        File subDirItem = new File(Launch.minecraftHome, SUBDIR_ITEM);
        if (!subDirItem.exists())
        {
            subDirItem.mkdirs();
        }
        File subDirItemItemBlock = new File(Launch.minecraftHome, SUBDIR_ITEM_ITEMBLOCK);
        if (!subDirItemItemBlock.exists())
        {
            subDirItemItemBlock.mkdirs();
        }
        File subDirItemMetalTool = new File(Launch.minecraftHome, SUBDIR_ITEMMETALTOOL);
        if (!subDirItemMetalTool.exists())
        {
            subDirItemMetalTool.mkdirs();
        }
        File subDirItemRockTool = new File(Launch.minecraftHome, SUBDIR_ITEMROCKTOOL);
        if (!subDirItemRockTool.exists())
        {
            subDirItemRockTool.mkdirs();
        }
        File subDirBlock = new File(Launch.minecraftHome, SUBDIR_BLOCK);
        if (!subDirBlock.exists())
        {
            subDirBlock.mkdirs();
        }
        File subDirBlockAgriculture = new File(Launch.minecraftHome, SUBDIR_BLOCK_AGRICULTURE);
        if (!subDirBlockAgriculture.exists())
        {
            subDirBlockAgriculture.mkdirs();
        }
        File subDirBlockPlant = new File(Launch.minecraftHome, SUBDIR_BLOCKPLANT);
        if (!subDirBlockPlant.exists())
        {
            subDirBlockPlant.mkdirs();
        }
        File subDirBlockStone = new File(Launch.minecraftHome, SUBDIR_STONE);
        if (!subDirBlockStone.exists())
        {
            subDirBlockStone.mkdirs();
        }
        File subDirUtil = new File(Launch.minecraftHome, SUBDIR_UTIL);
        if (!subDirUtil.exists())
        {
            subDirUtil.mkdirs();
        }
        File subDirClimate = new File(Launch.minecraftHome, SUBDIR_CLIMATE);
        if (!subDirClimate.exists())
        {
            subDirClimate.mkdirs();
        }
        File subDirWorld = new File(Launch.minecraftHome, SUBDIR_WORLD);
        if (!subDirWorld.exists())
        {
            subDirWorld.mkdirs();
        }
        File subDirWorldBiomes = new File(Launch.minecraftHome, SUBDIR_WORLD_BIOMES);
        if (!subDirWorldBiomes.exists())
        {
            subDirWorldBiomes.mkdirs();
        }
        File subDirWorldMapgen = new File(Launch.minecraftHome, SUBDIR_WORLD_MAPGEN);
        if (!subDirWorldMapgen.exists())
        {
            subDirWorldMapgen.mkdirs();
        }
        File subDirWorldChunkData = new File(Launch.minecraftHome, SUBDIR_WORLD_CHUNKDATA);
        if (!subDirWorldChunkData.exists())
        {
            subDirWorldChunkData.mkdirs();
        }
        File subDirWorldGenLayers = new File(Launch.minecraftHome, SUBDIR_WORLD_GENLAYERS);
        if (!subDirWorldGenLayers.exists())
        {
            subDirWorldGenLayers.mkdirs();
        }
        File subDirWorldGenLayersBiome = new File(Launch.minecraftHome, SUBDIR_WORLD_GENLAYERS_BIOME);
        if (!subDirWorldGenLayersBiome.exists())
        {
            subDirWorldGenLayersBiome.mkdirs();
        }
        File subDirWorldGenLayersDataLayers = new File(Launch.minecraftHome, SUBDIR_WORLD_GENLAYERS_DATALAYERS);
        if (!subDirWorldGenLayersDataLayers.exists())
        {
            subDirWorldGenLayersDataLayers.mkdirs();
        }
        File subDirWorldGenLayersDataLayersTree = new File(Launch.minecraftHome, SUBDIR_WORLD_GENLAYERS_DATALAYERS_TREE);
        if (!subDirWorldGenLayersDataLayersTree.exists())
        {
            subDirWorldGenLayersDataLayersTree.mkdirs();
        }
        File subDirWorldGenLayersRiver = new File(Launch.minecraftHome, SUBDIR_WORLD_GENLAYERS_RIVER);
        if (!subDirWorldGenLayersRiver.exists())
        {
            subDirWorldGenLayersRiver.mkdirs();
        }
        File subDirWorldGenLayersRidge = new File(Launch.minecraftHome, SUBDIR_WORLD_GENLAYERS_RIDGE);
        if (!subDirWorldGenLayersRidge.exists())
        {
            subDirWorldGenLayersRidge.mkdirs();
        }
        File subDirWorldGen = new File(Launch.minecraftHome, SUBDIR_WORLD_WORLDGEN);
        if (!subDirWorldGen.exists())
        {
            subDirWorldGen.mkdirs();
        }
        File subDirTrees = new File(Launch.minecraftHome, SUBDIR_WORLD_TREES);
        if (!subDirTrees.exists())
        {
            subDirTrees.mkdirs();
        }

        // Define file locations

        // Home/Main Directory
        File commonEventHandler = new File(bansoukouFolder + "/", "CommonEventHandler.class");

        // API Classes
        File fallingBlockManager = new File(bansoukouFolder + "/api/util", "FallingBlockManager.class");
        File fallingBlockManager1 = new File(bansoukouFolder + "/api/util", "FallingBlockManager$Specification$IBeginFallCallback.class");
        File fallingBlockManager2 = new File(bansoukouFolder + "/api/util", "FallingBlockManager$Specification$ICollapseChecker.class");
        File fallingBlockManager3 = new File(bansoukouFolder + "/api/util", "FallingBlockManager$Specification$IEndFallCallback.class");
        File fallingBlockManager4 = new File(bansoukouFolder + "/api/util", "FallingBlockManager$Specification$IFallDropsProvider.class");
        File fallingBlockManager5 = new File(bansoukouFolder + "/api/util", "FallingBlockManager$Specification.class");
        File IFruitTreeGenerator = new File(bansoukouFolder + "/api/util", "IFruitTreeGenerator.class");
        File ITreeGenerator = new File(bansoukouFolder + "/api/util", "ITreeGenerator.class");

        // Animal Classes
        File EntityAnimalTFC = new File(bansoukouFolder + "/objects/entity/animal", "EntityAnimalTFC.class");
        File EntityDonkeyTFC = new File(bansoukouFolder + "/objects/entity/animal", "EntityDonkeyTFC.class");
        File EntityHorseTFC = new File(bansoukouFolder + "/objects/entity/animal", "EntityHorseTFC.class");
        File EntityLlamaTFC = new File(bansoukouFolder + "/objects/entity/animal", "EntityLlamaTFC.class");
        File EntityMuleTFC = new File(bansoukouFolder + "/objects/entity/animal", "EntityMuleTFC.class");
        File EntityOcelotTFC = new File(bansoukouFolder + "/objects/entity/animal", "EntityOcelotTFC.class");
        File EntityParrotTFC = new File(bansoukouFolder + "/objects/entity/animal", "EntityParrotTFC.class");
        File EntityPolarBearTFC = new File(bansoukouFolder + "/objects/entity/animal", "EntityPolarBearTFC.class");
        File EntityWolfTFC = new File(bansoukouFolder + "/objects/entity/animal", "EntityWolfTFC.class");

        // Item Classes
        File itemFireStarter = new File(bansoukouFolder + "/objects/items", "ItemFireStarter.class");
        File itemSeeds = new File(bansoukouFolder + "/objects/items", "ItemSeedsTFC.class");
        File itemBlockCropWaterDead = new File(bansoukouFolder + "/objects/items/itemblock", "ItemBlockCropDeadWaterTFC.class");
        File itemBlockCropWater = new File(bansoukouFolder + "/objects/items/itemblock", "ItemBlockCropWaterTFC.class");
        File itemProspectorPickResultType = new File(bansoukouFolder + "/objects/items/metal", "ItemProspectorPick$ProspectResult$Type.class");
        File itemProspectorPickResult = new File(bansoukouFolder + "/objects/items/metal", "ItemProspectorPick$ProspectResult.class");
        File itemProspectorPick = new File(bansoukouFolder + "/objects/items/metal", "ItemProspectorPick.class");
        File itemProspectorPick1 = new File(bansoukouFolder + "/objects/items/metal", "ItemProspectorPick$1.class");
        File itemMetalTool1 = new File(bansoukouFolder + "/objects/items/metal", "ItemMetalTool$1.class");
        File itemMetalTool = new File(bansoukouFolder + "/objects/items/metal", "ItemMetalTool.class");
        File itemRockShovel = new File(bansoukouFolder + "/objects/items/rock", "ItemRockShovel.class");

        // Block Classes
        File blockBlocksTFC1 = new File(bansoukouFolder + "/objects/blocks", "BlocksTFC$1.class");
        File blockBlocksTFC = new File(bansoukouFolder + "/objects/blocks", "BlocksTFC.class");
        File blockBlockFluidHotWater = new File(bansoukouFolder + "/objects/blocks", "BlockFluidHotWater.class");

        // Agriculture Classes
        File BlockBerryBush1 = new File(bansoukouFolder + "/objects/blocks/agriculture", "BlockBerryBush$1.class");
        File BlockBerryBush = new File(bansoukouFolder + "/objects/blocks/agriculture", "BlockBerryBush.class");
        File blockCropDead = new File(bansoukouFolder + "/objects/blocks/agriculture", "BlockCropDead.class");
        File blockCrop = new File(bansoukouFolder + "/objects/blocks/agriculture", "BlockCropTFC.class");
        File BlockFruitTreeTrunk = new File(bansoukouFolder + "/objects/blocks/agriculture", "BlockFruitTreeTrunk.class");

        // Stone Classes
        File BlockRockVariantConnected = new File(bansoukouFolder + "/objects/blocks/stone", "BlockRockVariantConnected.class");

        // Mapgen
        File MapGenCavesTFC = new File(bansoukouFolder + "/world/classic/mapgen", "MapGenCavesTFC.class");
        File MapGenRavineTFC = new File(bansoukouFolder + "/world/classic/mapgen", "MapGenRavineTFC.class");
        File MapGenRiverRavine = new File(bansoukouFolder + "/world/classic/mapgen", "MapGenRiverRavine.class");

        // Plant Blocks
        File blockPlantTFC = new File(bansoukouFolder + "/objects/blocks/plants", "BlockPlantTFC.class");
        File blockPlantTFC1 = new File(bansoukouFolder + "/objects/blocks/plants", "BlockPlantTFC$1.class");
        File blockShortGrassTFC = new File(bansoukouFolder + "/objects/blocks/plants", "BlockShortGrassTFC.class");
        File blockTallGrassTFC = new File(bansoukouFolder + "/objects/blocks/plants", "BlockTallGrassTFC.class");
        File blockHangingTFC = new File(bansoukouFolder + "/objects/blocks/plants", "BlockHangingPlantTFC.class");

        // Util
        File RegenWildCrops = new File(bansoukouFolder + "/util", "RegenWildCrops.class");
        File worldRegenHandler = new File(bansoukouFolder + "/util", "WorldRegenHandler.class");
        File ClimateTFC = new File(bansoukouFolder + "/util/climate", "ClimateTFC.class");

        // World Classic
        File chunkGenTFC = new File(bansoukouFolder + "/world/classic", "ChunkGenTFC.class");
        File dataLayer = new File(bansoukouFolder + "/world/classic", "DataLayer.class");

        // Biomes
        File biomeDecoratorTFC = new File(bansoukouFolder + "/world/classic/biomes", "BiomeDecoratorTFC.class");
        File biomeDecoratorTFC1 = new File(bansoukouFolder + "/world/classic/biomes", "BiomeDecoratorTFC$1.class");
        File biomeMesaTFC = new File(bansoukouFolder + "/world/classic/biomes", "BiomeMesaTFC.class");
        File biomesTFC = new File(bansoukouFolder + "/world/classic/biomes", "BiomesTFC.class");
        File biomeTFC = new File(bansoukouFolder + "/world/classic/biomes", "BiomeTFC.class");

        // ChunkData
        File chunkDataTFC = new File(bansoukouFolder + "/world/classic/chunkdata", "ChunkDataTFC.class");
        File chunkDataTFCStorage = new File(bansoukouFolder + "/world/classic/chunkdata", "ChunkDataTFC$ChunkDataStorage.class");
        File ChunkDataBlock = new File(bansoukouFolder + "/world/classic/chunkdata", "ChunkDataBlock.class");
        File ChunkDataCache = new File(bansoukouFolder + "/world/classic/chunkdata", "ChunkDataCache.class");

        // GenLayer & stuff
        File GenLayerSmoothBiomeTFC = new File(bansoukouFolder + "/world/classic/genlayers", "GenLayerSmoothBiomeTFC.class");
        File genLayerTFC = new File(bansoukouFolder + "/world/classic/genlayers", "GenLayerTFC.class");
        File GenLayerBeachTFC = new File(bansoukouFolder + "/world/classic/genlayers/biome", "GenLayerBeachTFC.class");
        File genLayerBiomeEdge = new File(bansoukouFolder + "/world/classic/genlayers/biome", "GenLayerBiomeEdge.class");
        File GenLayerLakeShore = new File(bansoukouFolder + "/world/classic/genlayers/biome", "GenLayerLakeShore.class");
        File genLayerShoreTFC = new File(bansoukouFolder + "/world/classic/genlayers/biome", "GenLayerShoreTFC.class");

        File GenLayerTreeInit = new File(bansoukouFolder + "/world/classic/genlayers/datalayers/tree", "GenLayerTreeInit.class");
        File GenRegionLayer = new File(bansoukouFolder + "/world/classic/genlayers/datalayers/tree", "GenRegionLayer.class");
        File GenRiverLayer = new File(bansoukouFolder + "/world/classic/genlayers/datalayers/tree", "GenRiverLayer.class");

        File GenLayerRiverInitTFC = new File(bansoukouFolder + "/world/classic/genlayers/river", "GenLayerRiverInitTFC.class");
        File GenLayerRiverMixTFC = new File(bansoukouFolder + "/world/classic/genlayers/river", "GenLayerRiverMixTFC.class");
        File GenLayerRiverTFC = new File(bansoukouFolder + "/world/classic/genlayers/river", "GenLayerRiverTFC.class");
        File GenLayerRidgeInitTFC = new File(bansoukouFolder + "/world/classic/genlayers/ridge", "GenLayerRidgeInitTFC.class");
        File GenLayerRidgeMixTFC = new File(bansoukouFolder + "/world/classic/genlayers/ridge", "GenLayerRidgeMixTFC.class");
        File GenLayerRidgeTFC = new File(bansoukouFolder + "/world/classic/genlayers/ridge", "GenLayerRidgeTFC.class");

        // Worldgen
        File WorldGenFissure = new File(bansoukouFolder + "/world/classic/worldgen", "WorldGenFissure.class");
        File WorldGenLargeRocks = new File(bansoukouFolder + "/world/classic/worldgen", "WorldGenLargeRocks.class");
        File WorldGenLooseRocks = new File(bansoukouFolder + "/world/classic/worldgen", "WorldGenLooseRocks.class");
        File wgPlants1 = new File(bansoukouFolder + "/world/classic/worldgen", "WorldGenPlantTFC$1.class");
        File wgPlants = new File(bansoukouFolder + "/world/classic/worldgen", "WorldGenPlantTFC.class");
        File WorldGenSandTFC = new File(bansoukouFolder + "/world/classic/worldgen", "WorldGenSandTFC.class");
        File WorldGenSoilPits = new File(bansoukouFolder + "/world/classic/worldgen", "WorldGenSoilPits.class");
        File WorldGenTrees = new File(bansoukouFolder + "/world/classic/worldgen", "WorldGenTrees.class");
        File genWildCrops = new File(bansoukouFolder + "/world/classic/worldgen", "WorldGenWildCrops.class");

        // Trees
        File TreeGenBushes = new File(bansoukouFolder + "/world/classic/worldgen/trees", "TreeGenBushes.class");
        File TreeGenKapok = new File(bansoukouFolder + "/world/classic/worldgen/trees", "TreeGenKapok.class");
        File TreeGenSequoia = new File(bansoukouFolder + "/world/classic/worldgen/trees", "TreeGenSequoia.class");

        try
        {
            if (commonEventHandler.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(CEH_FROM)), commonEventHandler);
            }
            if (fallingBlockManager.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(FALLINGBLOCKMANAGER_FROM)), fallingBlockManager);
            }
            if (fallingBlockManager1.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(FALLINGBLOCKMANAGER1_FROM)), fallingBlockManager1);
            }
            if (fallingBlockManager2.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(FALLINGBLOCKMANAGER2_FROM)), fallingBlockManager2);
            }
            if (fallingBlockManager3.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(FALLINGBLOCKMANAGER3_FROM)), fallingBlockManager3);
            }
            if (fallingBlockManager4.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(FALLINGBLOCKMANAGER4_FROM)), fallingBlockManager4);
            }
            if (fallingBlockManager5.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(FALLINGBLOCKMANAGER5_FROM)), fallingBlockManager5);
            }
            if (IFruitTreeGenerator.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(IFRUITTREEGENERATOR_FROM)), IFruitTreeGenerator);
            }
            if (ITreeGenerator.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ITREEGENERATOR_FROM)), ITreeGenerator);
            }

            if (EntityAnimalTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ENTITYANIMALTFC_FROM)), EntityAnimalTFC);
            }
            if (EntityDonkeyTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ENTITYDONKEYTFC_FROM)), EntityDonkeyTFC);
            }
            if (EntityHorseTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ENTITYHORSETFC_FROM)), EntityHorseTFC);
            }
            if (EntityLlamaTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ENTITYLLAMATFC_FROM)), EntityLlamaTFC);
            }
            if (EntityMuleTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ENTITYMULETFC_FROM)), EntityMuleTFC);
            }
            if (EntityOcelotTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ENTITYOCELOTTFC_FROM)), EntityOcelotTFC);
            }
            if (EntityParrotTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ENTITYPARROTTFC_FROM)), EntityParrotTFC);
            }
            if (EntityPolarBearTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ENTITYPOLARBEARTFC_FROM)), EntityPolarBearTFC);
            }
            if (EntityWolfTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ENTITYWOLFTFC_FROM)), EntityWolfTFC);
            }

            if (itemFireStarter.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ITEMFIRESTARTER_FROM)), itemFireStarter);
            }
            if (itemSeeds.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ITEM_SEEDS_FROM)), itemSeeds);
            }
            if (itemBlockCropWaterDead.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ITEMBLOCKBLOCKCROPDEAD_FROM)), itemBlockCropWaterDead);
            }
            if (itemBlockCropWater.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ITEMBLOCKBLOCKCROP_FROM)), itemBlockCropWater);
            }
            /*if (itemProspectorPickResultType.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ITEMPROSPECTORSPICKRESULTTYPE_FROM)), itemProspectorPickResultType);
            }
            if (itemProspectorPickResult.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ITEMPROSPECTORSPICKRESULT_FROM)), itemProspectorPickResult);
            }
            if (itemProspectorPick.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ITEMPROSPECTORSPICK_FROM)), itemProspectorPick);
            }
            if (itemProspectorPick1.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ITEMPROSPECTORSPICK1_FROM)), itemProspectorPick1);
            }*/
            if (itemMetalTool1.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ITEMMETALTOOL1_FROM)), itemMetalTool1);
            }
            if (itemMetalTool.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ITEMMETALTOOL_FROM)), itemMetalTool);
            }
            if (itemRockShovel.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(ITEMROCKSHOVEL_FROM)), itemRockShovel);
            }
            if (blockBlocksTFC1.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCKSTFC1_FROM)), blockBlocksTFC1);
            }
            if (blockBlocksTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCKSTFC_FROM)), blockBlocksTFC);
            }
            if (blockBlockFluidHotWater.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCKFLUIDHOTWATER_FROM)), blockBlockFluidHotWater);
            }

            if (BlockBerryBush1.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCKBERRYBUSH1_FROM)), BlockBerryBush1);
            }
            if (BlockBerryBush.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCKBERRYBUSH_FROM)), BlockBerryBush);
            }
            if (blockCropDead.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCK_CROPDEAD_FROM)), blockCropDead);
            }
            if (blockCrop.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCK_CROP_FROM)), blockCrop);
            }
            if (BlockFruitTreeTrunk.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCKFRUITTREETRUNK_FROM)), BlockFruitTreeTrunk);
            }

            if (BlockRockVariantConnected.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCKROCKVARIANTCONNECTED_FROM)), BlockRockVariantConnected);
            }

            if (MapGenCavesTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(MAPGENCAVESTFC_FROM)), MapGenCavesTFC);
            }
            if (MapGenRavineTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(MAPGENRAVINETFC_FROM)), MapGenRavineTFC);
            }
            if (MapGenRiverRavine.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(MAPGENRIVERRAVINE_FROM)), MapGenRiverRavine);
            }

            if (blockPlantTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCKPLANTTFC_FROM)), blockPlantTFC);
            }
            if (blockPlantTFC1.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCKPLANTTFC1_FROM)), blockPlantTFC1);
            }
            if (blockShortGrassTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCKSHORTGRASSTFC_FROM)), blockShortGrassTFC);
            }
            if (blockTallGrassTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCKTALLGRASSTFC_FROM)), blockTallGrassTFC);
            }
            if (blockHangingTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCKHANGINGTFC_FROM)), blockHangingTFC);
            }

            if (RegenWildCrops.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(REGENWILDCROPS_FROM)), RegenWildCrops);
            }
            if (worldRegenHandler.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(WORLDREGEN_FROM)), worldRegenHandler);
            }
            if (ClimateTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(CLIMATETFC_FROM)), ClimateTFC);
            }

            if (chunkGenTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(CHUNKGENTFC_FROM)), chunkGenTFC);
            }
            if (dataLayer.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(DATALAYER_FROM)), dataLayer);
            }
            if (biomeDecoratorTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BIOMEDECORATOR_FROM)), biomeDecoratorTFC);
            }
            if (biomeDecoratorTFC1.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BIOMEDECORATOR1_FROM)), biomeDecoratorTFC1);
            }
            if (biomeMesaTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BIOMEMESATFC_FROM)), biomeMesaTFC);
            }
            if (biomesTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BIOMESTFC_FROM)), biomesTFC);
            }
            if (biomeTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BIOMETFC_FROM)), biomeTFC);
            }

            if (chunkDataTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(CHUNKDATATFC_FROM)), chunkDataTFC);
            }
            if (chunkDataTFCStorage.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(CHUNKDATATFC_STORAGE_FROM)), chunkDataTFCStorage);
            }
            if (ChunkDataBlock.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(CHUNKDATABLOCK_FROM)), ChunkDataBlock);
            }
            if (ChunkDataCache.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(CHUNKDATACACHE_FROM)), ChunkDataCache);
            }

            if (GenLayerSmoothBiomeTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(SMOOTHBIOMETFC_FROM)), GenLayerSmoothBiomeTFC);
            }
            if (genLayerTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERTFC_FROM)), genLayerTFC);
            }
            if (GenLayerBeachTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERBEACHTFC_FROM)), GenLayerBeachTFC);
            }
            if (genLayerBiomeEdge.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERBIOMEEDGE_FROM)), genLayerBiomeEdge);
            }
            if (GenLayerLakeShore.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERLAKESHORE_FROM)), GenLayerLakeShore);
            }
            if (genLayerShoreTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERSHORETFC_FROM)), genLayerShoreTFC);
            }

            if (GenLayerTreeInit.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERTREEINIT_FROM)), GenLayerTreeInit);
            }
            if (GenRegionLayer.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENREGIONLAYER_FROM)), GenRegionLayer);
            }
            if (GenRiverLayer.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENRIVERLAYER_FROM)), GenRiverLayer);
            }

            if (GenLayerRiverInitTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERRIVERINITTFC_FROM)), GenLayerRiverInitTFC);
            }
            if (GenLayerRiverMixTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERRIVERMIXTFC_FROM)), GenLayerRiverMixTFC);
            }
            if (GenLayerRiverTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERRIVERTFC_FROM)), GenLayerRiverTFC);
            }
            if (GenLayerRidgeInitTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERRIDGEINITTFC_FROM)), GenLayerRidgeInitTFC);
            }
            if (GenLayerRidgeMixTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERRIDGEMIXTFC_FROM)), GenLayerRidgeMixTFC);
            }
            if (GenLayerRidgeTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERRIDGETFC_FROM)), GenLayerRidgeTFC);
            }

            if (WorldGenFissure.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(WORLDGENFISSURE_FROM)), WorldGenFissure);
            }
            if (WorldGenLargeRocks.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(WORLDGENLARGEROCKS_FROM)), WorldGenLargeRocks);
            }
            if (WorldGenLooseRocks.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(WORLDGENLOOSEROCKS_FROM)), WorldGenLooseRocks);
            }
            if (wgPlants1.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(WGPLANTS1_FROM)), wgPlants1);
            }
            if (wgPlants.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(WGPLANTS_FROM)), wgPlants);
            }
            if (WorldGenSandTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(WORLDGENSANDTFC_FROM)), WorldGenSandTFC);
            }
            if (WorldGenSoilPits.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(WORLDGENSOILPITS_FROM)), WorldGenSoilPits);
            }
            if (WorldGenTrees.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(WORLDGENTREES_FROM)), WorldGenTrees);
            }
            if (genWildCrops.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(WORLDGENWILDCROPS_FROM)), genWildCrops);
            }

            if (TreeGenBushes.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(TREEGENBUSHES_FROM)), TreeGenBushes);
            }
            if (TreeGenKapok.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(TREEGENKAPOK_FROM)), TreeGenKapok);
            }
            if (TreeGenSequoia.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(TREEGENSEQUOIA_FROM)), TreeGenSequoia);
            }
        }
        catch (IOException e)
        {
            throw new Error(TFCFlorae.MODID + ": Sorry, but I couldn't copy the class files into Bansoukou's directory.", e);
        }
    }

    // This function removes string from first encounter of RemWordAt to end of input string
    private static String removeAt(String inString, String remWordAt)
    {
        String[] split = inString.split(remWordAt);
        return split[0];
    }
}