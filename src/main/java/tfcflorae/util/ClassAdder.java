package tfcflorae.util;

import java.io.*;
import java.util.Objects;

import org.apache.commons.io.FileUtils;

import net.minecraft.launchwrapper.Launch;

import tfcflorae.TFCFlorae;

public class ClassAdder
{
    // Files
    private static final String ITEMFIRESTARTER_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/ItemFireStarter.class";
    private static final String ITEM_SEEDS_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/ItemSeedsTFC.class";
    private static final String ITEMBLOCKBLOCKCROPDEAD_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/itemblock/ItemBlockCropDeadWaterTFC.class";
    private static final String ITEMBLOCKBLOCKCROP_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/itemblock/ItemBlockCropWaterTFC.class";
    private static final String ITEMPROSPECTORSPICKRESULTTYPE_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/metal/ItemProspectorPick$ProspectResult$Type.class";
    private static final String ITEMPROSPECTORSPICKRESULT_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/metal/ItemProspectorPick$ProspectResult.class";
    private static final String ITEMPROSPECTORSPICK_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/metal/ItemProspectorPick.class";
    private static final String ITEMPROSPECTORSPICK1_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/metal/ItemProspectorPick$1.class";

    private static final String BLOCKSTFC1_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/BlocksTFC$1.class";
    private static final String BLOCKSTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/BlocksTFC.class";
    private static final String BLOCK_CROPDEAD_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/agriculture/BlockCropDead.class";
    private static final String BLOCK_CROP_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/agriculture/BlockCropTFC.class";
    private static final String BLOCKPLANTTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockPlantTFC.class";
    private static final String BLOCKPLANTTFC1_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockPlantTFC$1.class";
    private static final String BLOCKSHORTGRASSTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockShortGrassTFC.class";
    private static final String BLOCKTALLGRASSTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockTallGrassTFC.class";
    private static final String BLOCKHANGINGTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockHangingPlantTFC.class";

    private static final String WORLDREGEN_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/util/WorldRegenHandler.class";

    private static final String CHUNKGENTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/ChunkGenTFC.class";
    private static final String DATALAYER_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/DataLayer.class";

    private static final String BIOMEDECORATOR_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomeDecoratorTFC.class";
    private static final String BIOMEDECORATOR1_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomeDecoratorTFC$1.class";
    private static final String BIOMEMESATFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomeMesaTFC.class";
    private static final String BIOMESTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomesTFC.class";
    private static final String BIOMETFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomeTFC.class";

    private static final String CHUNKDATATFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/chunkdata/ChunkDataTFC.class";
    private static final String CHUNKDATATFC_STORAGE_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/chunkdata/ChunkDataTFC$ChunkDataStorage.class";
    private static final String GENLAYERTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/GenLayerTFC.class";
    private static final String GENLAYERBIOMEEDGE_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/biome/GenLayerBiomeEdge.class";
    private static final String GENLAYERSHORETFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/biome/GenLayerShoreTFC.class";
    private static final String GENLAYERTREEINIT_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/datalayers/tree/GenLayerTreeInit.class";
    private static final String GENRIVERLAYER_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/datalayers/tree/GenRiverLayer.class";
    private static final String GENLAYERRIVERINITTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/river/GenLayerRiverInitTFC.class";
    private static final String GENLAYERRIVERMIXTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/river/GenLayerRiverMixTFC.class";
    private static final String GENLAYERRIVERTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/river/GenLayerRiverTFC.class";

    private static final String WORLDGENWILDCROPS_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/worldgen/WorldGenWildCrops.class";

    // Directories
    private static final String DIR = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc";
    private static final String SUBDIR_ITEM = DIR + "/objects/items";
    private static final String SUBDIR_ITEM_ITEMBLOCK = DIR + "/objects/items/itemblock";
    private static final String SUBDIR_ITEMPROSPECTORSPICKRESULTTYPE = DIR + "/objects/items/metal";
    private static final String SUBDIR_ITEMPROSPECTORSPICKRESULT = DIR + "/objects/items/metal";
    private static final String SUBDIR_ITEMPROSPECTORSPICK = DIR + "/objects/items/metal";
    private static final String SUBDIR_ITEMPROSPECTORSPICK1 = DIR + "/objects/items/metal";
    private static final String SUBDIR_BLOCK = DIR + "/objects/blocks";
    private static final String SUBDIR_BLOCK_AGRICULTURE = DIR + "/objects/blocks/agriculture";
    private static final String SUBDIR_BLOCKPLANT = DIR + "/objects/blocks/plants";
    private static final String SUBDIR_UTIL = DIR + "/util";
    private static final String SUBDIR_WORLD = DIR + "/world/classic";
    private static final String SUBDIR_WORLD_BIOMES = DIR + "/world/classic/biomes";
    private static final String SUBDIR_WORLD_CHUNKDATA = DIR + "/world/classic/chunkdata";
    private static final String SUBDIR_WORLD_GENLAYERS = DIR + "/world/classic/genlayers";
    private static final String SUBDIR_WORLD_GENLAYERS_BIOME = DIR + "/world/classic/genlayers/biome";
    private static final String SUBDIR_WORLD_GENLAYERS_DATALAYERS = DIR + "/world/classic/genlayers/datalayers";
    private static final String SUBDIR_WORLD_GENLAYERS_DATALAYERS_TREE = DIR + "/world/classic/genlayers/datalayers/tree";
    private static final String SUBDIR_WORLD_GENLAYERS_RIVER = DIR + "/world/classic/genlayers/river";
    private static final String SUBDIR_WORLD_WORLDGEN = DIR + "/world/classic/worldgen";
    //private static final String DIR = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes";

    public static void addClasses(File dir)
    {
        // Make directories if they are missing.
        File bansoukouFolder = new File(Launch.minecraftHome, DIR);
        if (!bansoukouFolder.exists())
        {
            bansoukouFolder.mkdirs();
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
        File subDirItemProspectorPickResultType = new File(Launch.minecraftHome, SUBDIR_ITEMPROSPECTORSPICKRESULTTYPE);
        if (!subDirItemProspectorPickResultType.exists())
        {
            subDirItemProspectorPickResultType.mkdirs();
        }
        File subDirItemProspectorPickResult = new File(Launch.minecraftHome, SUBDIR_ITEMPROSPECTORSPICKRESULT);
        if (!subDirItemProspectorPickResult.exists())
        {
            subDirItemProspectorPickResult.mkdirs();
        }
        File subDirItemProspectorPick = new File(Launch.minecraftHome, SUBDIR_ITEMPROSPECTORSPICK);
        if (!subDirItemProspectorPick.exists())
        {
            subDirItemProspectorPick.mkdirs();
        }
        File subDirItemProspectorPick1 = new File(Launch.minecraftHome, SUBDIR_ITEMPROSPECTORSPICK1);
        if (!subDirItemProspectorPick1.exists())
        {
            subDirItemProspectorPick1.mkdirs();
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
        File subDirUtil = new File(Launch.minecraftHome, SUBDIR_UTIL);
        if (!subDirUtil.exists())
        {
            subDirUtil.mkdirs();
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
        File subDirWorldGen = new File(Launch.minecraftHome, SUBDIR_WORLD_WORLDGEN);
        if (!subDirWorldGen.exists())
        {
            subDirWorldGen.mkdirs();
        }

        // Define file locations
        File itemFireStarter = new File(bansoukouFolder + "/objects/items", "ItemFireStarter.class");
        File itemSeeds = new File(bansoukouFolder + "/objects/items", "ItemSeedsTFC.class");
        File itemBlockCropWaterDead = new File(bansoukouFolder + "/objects/items/itemblock", "ItemBlockCropDeadWaterTFC.class");
        File itemBlockCropWater = new File(bansoukouFolder + "/objects/items/itemblock", "ItemBlockCropWaterTFC.class");
        File itemProspectorPickResultType = new File(bansoukouFolder + "/objects/items/metal", "ItemProspectorPick$ProspectResult$Type.class");
        File itemProspectorPickResult = new File(bansoukouFolder + "/objects/items/metal", "ItemProspectorPick$ProspectResult.class");
        File itemProspectorPick = new File(bansoukouFolder + "/objects/items/metal", "ItemProspectorPick.class");
        File itemProspectorPick1 = new File(bansoukouFolder + "/objects/items/metal", "ItemProspectorPick$1.class");
        File blockBlocksTFC1 = new File(bansoukouFolder + "/objects/blocks", "BlocksTFC$1.class");
        File blockBlocksTFC = new File(bansoukouFolder + "/objects/blocks", "BlocksTFC.class");
        File blockCropDead = new File(bansoukouFolder + "/objects/blocks/agriculture", "BlockCropDead.class");
        File blockCrop = new File(bansoukouFolder + "/objects/blocks/agriculture", "BlockCropTFC.class");
        File blockPlantTFC = new File(bansoukouFolder + "/objects/blocks/plants", "BlockPlantTFC.class");
        File blockPlantTFC1 = new File(bansoukouFolder + "/objects/blocks/plants", "BlockPlantTFC$1.class");
        File blockShortGrassTFC = new File(bansoukouFolder + "/objects/blocks/plants", "BlockShortGrassTFC.class");
        File blockTallGrassTFC = new File(bansoukouFolder + "/objects/blocks/plants", "BlockTallGrassTFC.class");
        File blockHangingTFC = new File(bansoukouFolder + "/objects/blocks/plants", "BlockHangingPlantTFC.class");
        File worldRegenHandler = new File(bansoukouFolder + "/util", "WorldRegenHandler.class");
        File chunkGenTFC = new File(bansoukouFolder + "/world/classic", "ChunkGenTFC.class");
        File dataLayer = new File(bansoukouFolder + "/world/classic", "DataLayer.class");
        File biomeDecoratorTFC = new File(bansoukouFolder + "/world/classic/biomes", "BiomeDecoratorTFC.class");
        File biomeDecoratorTFC1 = new File(bansoukouFolder + "/world/classic/biomes", "BiomeDecoratorTFC$1.class");
        File biomeMesaTFC = new File(bansoukouFolder + "/world/classic/biomes", "BiomeMesaTFC.class");
        File biomesTFC = new File(bansoukouFolder + "/world/classic/biomes", "BiomesTFC.class");
        File biomeTFC = new File(bansoukouFolder + "/world/classic/biomes", "BiomeTFC.class");
        File chunkDataTFC = new File(bansoukouFolder + "/world/classic/chunkdata", "ChunkDataTFC.class");
        File chunkDataTFCStorage = new File(bansoukouFolder + "/world/classic/chunkdata", "ChunkDataTFC$ChunkDataStorage.class");
        File genLayerTFC = new File(bansoukouFolder + "/world/classic/genlayers", "GenLayerTFC.class");
        File genLayerBiomeEdge = new File(bansoukouFolder + "/world/classic/genlayers/biome", "GenLayerBiomeEdge.class");
        File genLayerShoreTFC = new File(bansoukouFolder + "/world/classic/genlayers/biome", "GenLayerShoreTFC.class");
        File genLayerTreeInit = new File(bansoukouFolder + "/world/classic/genlayers/datalayers/tree", "GenLayerTreeInit.class");
        File genRiverLayer = new File(bansoukouFolder + "/world/classic/genlayers/datalayers/tree", "GenRiverLayer.class");
        /*File genLayerRiverInitTFC = new File(bansoukouFolder + "/world/classic/genlayers/river", "GenLayerRiverInitTFC.class");
        File genLayerRiverMixTFC = new File(bansoukouFolder + "/world/classic/genlayers/river", "GenLayerRiverMixTFC.class");
        File genLayerRiverTFC = new File(bansoukouFolder + "/world/classic/genlayers/river", "GenLayerRiverTFC.class");*/
        File genWildCrops = new File(bansoukouFolder + "/world/classic/worldgen", "WorldGenWildCrops.class");
        try
        {
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
            if (blockBlocksTFC1.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCKSTFC1_FROM)), blockBlocksTFC1);
            }
            if (blockBlocksTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCKSTFC_FROM)), blockBlocksTFC);
            }
            if (blockCropDead.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCK_CROPDEAD_FROM)), blockCropDead);
            }
            if (blockCrop.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BLOCK_CROP_FROM)), blockCrop);
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
            if (worldRegenHandler.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(WORLDREGEN_FROM)), worldRegenHandler);
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
            if (genLayerTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERTFC_FROM)), genLayerTFC);
            }
            if (genLayerBiomeEdge.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERBIOMEEDGE_FROM)), genLayerBiomeEdge);
            }
            if (genLayerShoreTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERSHORETFC_FROM)), genLayerShoreTFC);
            }
            if (genLayerTreeInit.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERTREEINIT_FROM)), genLayerTreeInit);
            }
            if (genRiverLayer.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENRIVERLAYER_FROM)), genRiverLayer);
            }
            /*if (genLayerRiverInitTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERRIVERINITTFC_FROM)), genLayerRiverInitTFC);
            }
            if (genLayerRiverMixTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERRIVERMIXTFC_FROM)), genLayerRiverMixTFC);
            }
            if (genLayerRiverTFC.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(GENLAYERRIVERTFC_FROM)), genLayerRiverTFC);
            }*/
            if (genWildCrops.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(WORLDGENWILDCROPS_FROM)), genWildCrops);
            }
        }
        catch (IOException e)
        {
            throw new Error(TFCFlorae.MODID + ": Sorry, but I couldn't copy the class files into Bansoukou's directory.", e);
        }
    }
}