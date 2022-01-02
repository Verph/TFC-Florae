package tfcflorae.objects.blocks;

import com.eerussianguy.firmalife.init.FruitTreeFL;
import com.eerussianguy.firmalife.init.PlantsFL;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.material.MapColor;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.*;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.blocks.BlockFireBrick;
import net.dries007.tfc.objects.blocks.BlockFluidTFC;
import net.dries007.tfc.objects.blocks.BlockFluidWater;
import net.dries007.tfc.objects.blocks.BlockLargeVessel;
import net.dries007.tfc.objects.blocks.BlockSlabTFC;
import net.dries007.tfc.objects.blocks.BlockStairsTFC;
import net.dries007.tfc.objects.blocks.agriculture.*;
import net.dries007.tfc.objects.blocks.devices.*;
import net.dries007.tfc.objects.blocks.metal.BlockAnvilTFC;
import net.dries007.tfc.objects.blocks.metal.BlockIngotPile;
import net.dries007.tfc.objects.blocks.metal.BlockMetalLamp;
import net.dries007.tfc.objects.blocks.metal.BlockMetalSheet;
import net.dries007.tfc.objects.blocks.plants.BlockFloatingWaterTFC;
import net.dries007.tfc.objects.blocks.stone.*;
import net.dries007.tfc.objects.blocks.wood.*;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.items.itemblock.*;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.objects.te.*;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.util.agriculture.BerryBush;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.agriculture.FruitTree;
import net.dries007.tfc.util.Helpers;

import tfcflorae.ConfigTFCF;
import tfcflorae.TFCFlorae;
import tfcflorae.api.registries.TFCFRegistries;
import tfcflorae.objects.blocks.*;
import tfcflorae.objects.blocks.groundcover.*;
import tfcflorae.objects.blocks.plants.*;
import tfcflorae.objects.blocks.plants.BlockPlant.*;
/*import tfcflorae.objects.blocks.multiblock.BlockCampfire;
import tfcflorae.objects.blocks.multiblock.BlockDummyHalf;
import tfcflorae.objects.blocks.multiblock.MultiBlockBase;*/
import tfcflorae.objects.blocks.wood.*;
import tfcflorae.objects.blocks.wood.bamboo.*;
import tfcflorae.objects.blocks.wood.cinnamon.*;
import tfcflorae.objects.blocks.wood.fruitwood.*;
import tfcflorae.objects.blocks.blocktype.*;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.objects.fluids.FluidsTFCF;
import tfcflorae.objects.items.food.*;
import tfcflorae.objects.items.groundcover.*;
import tfcflorae.objects.items.itemblock.ItemBlockCrate;
import tfcflorae.objects.items.itemblock.ItemBlockJoshuaTreeSapling;
import tfcflorae.objects.items.itemblock.ItemBlockTallGrassWater;
import tfcflorae.objects.items.itemblock.ItemBlockUrn;
import tfcflorae.objects.items.itemblock.ItemBlockUrnLoot;
import tfcflorae.objects.te.*;
import tfcflorae.types.BlockTypesTFCF;
import tfcflorae.types.PlantsTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;
import tfcflorae.types.TreesTFCF;
import tfcflorae.util.agriculture.*;

import static net.dries007.tfc.objects.fluids.FluidsTFC.*;
import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.Rock.Type.*;
import static net.dries007.tfc.objects.CreativeTabsTFC.*;
import static net.dries007.tfc.util.Helpers.getNull;

import static tfcflorae.TFCFlorae.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
@GameRegistry.ObjectHolder(MODID)
public final class BlocksTFCF
{
    @GameRegistry.ObjectHolder("ceramics/earthenware/fired/large_vessel")
    public static final BlockLargeVessel FIRED_EARTHENWARE_LARGE_VESSEL = getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/fired/large_vessel")
    public static final BlockLargeVessel FIRED_KAOLINITE_LARGE_VESSEL = getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/fired/large_vessel")
    public static final BlockLargeVessel FIRED_STONEWARE_LARGE_VESSEL = getNull();

    @GameRegistry.ObjectHolder("storage/urn")
    public static final BlockUrn FIRED_URN = getNull();
    @GameRegistry.ObjectHolder("storage/urn_loot")
    public static final BlockUrnLoot URN_LOOT = getNull();
    @GameRegistry.ObjectHolder("storage/crate")
    public static final BlockCrate CRATE = getNull();

    @GameRegistry.ObjectHolder("groundcover/bone")
    public static final BlockSurfaceBones BONES = Helpers.getNull();
    @GameRegistry.ObjectHolder("groundcover/driftwood")
    public static final BlockDriftwood DRIFTWOOD = Helpers.getNull();
    @GameRegistry.ObjectHolder("groundcover/flint")
    public static final BlockSurfaceFlint FLINT = Helpers.getNull();
    @GameRegistry.ObjectHolder("groundcover/pinecone")
    public static final BlockPinecone PINECONE = Helpers.getNull();
    @GameRegistry.ObjectHolder("groundcover/seashell")
    public static final BlockSurfaceSeashells SEASHELLS = Helpers.getNull();
    @GameRegistry.ObjectHolder("groundcover/twig")
    public static final BlockTwig TWIG = Helpers.getNull();

    @GameRegistry.ObjectHolder("wood/fruit_tree/log/cassia_cinnamon")
    public static final BlockCassiaCinnamonLog CASSIA_CINNAMON_LOG = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/fruit_tree/leaves/cassia_cinnamon")
    public static final BlockCassiaCinnamonLeaves CASSIA_CINNAMON_LEAVES = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/fruit_tree/sapling/cassia_cinnamon")
    public static final BlockCassiaCinnamonSapling CASSIA_CINNAMON_SAPLING = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/fruit_tree/log/ceylon_cinnamon")
    public static final BlockCeylonCinnamonLog CEYLON_CINNAMON_LOG = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/fruit_tree/leaves/ceylon_cinnamon")
    public static final BlockCeylonCinnamonLeaves CEYLON_CINNAMON_LEAVES = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/fruit_tree/sapling/ceylon_cinnamon")
    public static final BlockCeylonCinnamonSapling CEYLON_CINNAMON_SAPLING = Helpers.getNull();

    // Bales
    @GameRegistry.ObjectHolder("crop/bales/yucca/yucca_bale")
    public static final BlockBale YUCCA_BALE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/bales/yucca/yucca_fiber_bale")
    public static final BlockBale YUCCA_FIBER_BALE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/bales/cotton/cotton_bale")
    public static final BlockBale COTTON_BALE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/bales/cotton/cotton_yarn_bale")
    public static final BlockBale COTTON_YARN_BALE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/bales/flax/flax_bale")
    public static final BlockBale FLAX_BALE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/bales/flax/flax_fiber_bale")
    public static final BlockBale FLAX_FIBER_BALE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/bales/hemp/hemp_bale")
    public static final BlockBale HEMP_BALE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/bales/hemp/hemp_fiber_bale")
    public static final BlockBale HEMP_FIBER_BALE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/bales/jute/jute_bale")
    public static final BlockBale JUTE_BALE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/bales/jute/jute_fiber_bale")
    public static final BlockBale JUTE_FIBER_BALE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/bales/linen/linen_bale")
    public static final BlockBale LINEN_BALE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/bales/linen/linen_string_bale")
    public static final BlockBale LINEN_STRING_BALE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/bales/papyrus/papyrus_fiber_bale")
    public static final BlockBale PAPYRUS_FIBER_BALE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/bales/silk/silk_string_bale")
    public static final BlockBale SILK_STRING_BALE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/bales/sisal/sisal_fiber_bale")
    public static final BlockBale SISAL_FIBER_BALE = Helpers.getNull();

    // Bamboo Blocks
    @GameRegistry.ObjectHolder("wood/log/arrow_bamboo")
    public static final BlockBambooLog ARROW_BAMBOO_LOG = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/leaves/arrow_bamboo")
    public static final BlockBambooLeaves ARROW_BAMBOO_LEAVES = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/log/black_bamboo")
    public static final BlockBambooLog BLACK_BAMBOO_LOG = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/leaves/black_bamboo")
    public static final BlockBambooLeaves BLACK_BAMBOO_LEAVES = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/log/blue_bamboo")
    public static final BlockBambooLog BLUE_BAMBOO_LOG = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/leaves/blue_bamboo")
    public static final BlockBambooLeaves BLUE_BAMBOO_LEAVES = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/log/dragon_bamboo")
    public static final BlockBambooLog DRAGON_BAMBOO_LOG = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/leaves/dragon_bamboo")
    public static final BlockBambooLeaves DRAGON_BAMBOO_LEAVES = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/log/golden_bamboo")
    public static final BlockBambooLog GOLDEN_BAMBOO_LOG = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/leaves/golden_bamboo")
    public static final BlockBambooLeaves GOLDEN_BAMBOO_LEAVES = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/log/narrow_leaf_bamboo")
    public static final BlockBambooLog NARROW_LEAF_BAMBOO_LOG = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/leaves/narrow_leaf_bamboo")
    public static final BlockBambooLeaves NARROW_LEAF_BAMBOO_LEAVES = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/log/red_bamboo")
    public static final BlockBambooLog RED_BAMBOO_LOG = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/leaves/rbricked_bamboo")
    public static final BlockBambooLeaves RED_BAMBOO_LEAVES = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/log/temple_bamboo")
    public static final BlockBambooLog TEMPLE_BAMBOO_LOG = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/leaves/temple_bamboo")
    public static final BlockBambooLeaves TEMPLE_BAMBOO_LEAVES = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/log/thorny_bamboo")
    public static final BlockBambooLog THORNY_BAMBOO_LOG = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/leaves/thorny_bamboo")
    public static final BlockBambooLeaves THORNY_BAMBOO_LEAVES = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/log/timber_bamboo")
    public static final BlockBambooLog TIMBER_BAMBOO_LOG = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/leaves/timber_bamboo")
    public static final BlockBambooLeaves TIMBER_BAMBOO_LEAVES = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/log/tinwa_bamboo")
    public static final BlockBambooLog TINWA_BAMBOO_LOG = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/leaves/tinwa_bamboo")
    public static final BlockBambooLeaves TINWA_BAMBOO_LEAVES = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/log/weavers_bamboo")
    public static final BlockBambooLog WEAVERS_BAMBOO_LOG = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/leaves/weavers_bamboo")
    public static final BlockBambooLeaves WEAVERS_BAMBOO_LEAVES = Helpers.getNull();

    @GameRegistry.ObjectHolder("coral/brain/dead")
    public static final BlockCoral BRAIN_CORAL_DEAD = getNull();
    @GameRegistry.ObjectHolder("coral/bubble/dead")
    public static final BlockCoral BUBBLE_CORAL_DEAD = getNull();
    @GameRegistry.ObjectHolder("coral/fire/dead")
    public static final BlockCoral FIRE_CORAL_DEAD = getNull();
    @GameRegistry.ObjectHolder("coral/horn/dead")
    public static final BlockCoral HORN_CORAL_DEAD = getNull();
    @GameRegistry.ObjectHolder("coral/tube/dead")
    public static final BlockCoral TUBE_CORAL_DEAD = getNull();

    @GameRegistry.ObjectHolder("coral/fan/brain/dead")
    public static final BlockCoral BRAIN_CORAL_FAN_DEAD = getNull();
    @GameRegistry.ObjectHolder("coral/fan/bubble/dead")
    public static final BlockCoral BUBBLE_CORAL_FAN_DEAD = getNull();
    @GameRegistry.ObjectHolder("coral/fan/fire/dead")
    public static final BlockCoral FIRE_CORAL_FAN_DEAD = getNull();
    @GameRegistry.ObjectHolder("coral/fan/horn/dead")
    public static final BlockCoral HORN_CORAL_FAN_DEAD = getNull();
    @GameRegistry.ObjectHolder("coral/fan/tube/dead")
    public static final BlockCoral TUBE_CORAL_FAN_DEAD = getNull();

    @GameRegistry.ObjectHolder("coral/block/brain/dead")
    public static final BlockCoralBlock BRAIN_CORAL_BLOCK_DEAD = getNull();
    @GameRegistry.ObjectHolder("coral/block/bubble/dead")
    public static final BlockCoralBlock BUBBLE_CORAL_BLOCK_DEAD = getNull();
    @GameRegistry.ObjectHolder("coral/block/fire/dead")
    public static final BlockCoralBlock FIRE_CORAL_BLOCK_DEAD = getNull();
    @GameRegistry.ObjectHolder("coral/block/horn/dead")
    public static final BlockCoralBlock HORN_CORAL_BLOCK_DEAD = getNull();
    @GameRegistry.ObjectHolder("coral/block/tube/dead")
    public static final BlockCoralBlock TUBE_CORAL_BLOCK_DEAD = getNull();

    @GameRegistry.ObjectHolder("plants/glowing_sea_banana")
    public static final BlockWaterGlowPlant GLOWING_SEA_BANANA = getNull();
    @GameRegistry.ObjectHolder("plants/blueshroom")
    public static final BlockCaveMushroom BLUESHROOM = getNull();
    @GameRegistry.ObjectHolder("plants/glowshroom")
    public static final BlockCaveMushroom GLOWSHROOM = getNull();
    @GameRegistry.ObjectHolder("plants/magma_shroom")
    public static final BlockCaveMushroom MAGMA_SHROOM = getNull();
    @GameRegistry.ObjectHolder("plants/poison_shroom")
    public static final BlockCaveMushroom POISON_SHROOM = getNull();
    @GameRegistry.ObjectHolder("plants/sulphur_shroom")
    public static final BlockCaveMushroom SULPHUR_SHROOM = getNull();

    @GameRegistry.ObjectHolder("groundcover/lightstone")
    public static final BlockLightstone LIGHTSTONE = getNull();

    //MultiBlocks
    /*@GameRegistry.ObjectHolder("multiblock/campfire")
    public static final BlockCampfire Campfire = Helpers.getNull();
    @GameRegistry.ObjectHolder("multiblock/dummyHalf")
    public static final BlockCampfire DummyHalf = Helpers.getNull();*/

    private static ImmutableList<ItemBlock> allNormalItemBlocks;
    private static ImmutableList<Block> allInventoryItemBlocks = Helpers.getNull();
    private static ImmutableList<Block> allFoodItemBlocks = Helpers.getNull();
    private static ImmutableList<BlockFenceGateLog> allFenceGateLogBlocks = Helpers.getNull();
    private static ImmutableList<BlockFruitTreeLeaves> allFruitLeaves = Helpers.getNull();
    private static ImmutableList<BlockFruitTreeSapling> allFruitSapling = Helpers.getNull();
    //private static ImmutableList<BlockFruitBarrelTest> allFruitBarrel = Helpers.getNull();
    private static ImmutableList<BlockFruitBookshelves> allFruitBookshelves = Helpers.getNull();
    private static ImmutableList<BlockFruitButton> allFruitButton = Helpers.getNull();
    private static ImmutableList<BlockFruitDoor> allFruitDoors = Helpers.getNull();
    private static ImmutableList<BlockFruitPressurePlate> allFruitPressurePlate = Helpers.getNull();
    private static ImmutableList<BlockFruitFence> allFruitFences = Helpers.getNull();
    private static ImmutableList<BlockFruitFenceGate> allFruitFenceGates = Helpers.getNull();
    private static ImmutableList<BlockFruitLogFence> allFruitLogFences = Helpers.getNull();
    private static ImmutableList<BlockFruitLogFenceGate> allFruitLogFenceGates = Helpers.getNull();
    private static ImmutableList<BlockFruitPlanks> allFruitPlanks = Helpers.getNull();
    private static ImmutableList<BlockFruitSlab.Half> allFruitSlabBlocks = Helpers.getNull();
    private static ImmutableList<BlockFruitStairs> allFruitStairBlocks = Helpers.getNull();
    private static ImmutableList<BlockFruitSupport> allFruitSupport = Helpers.getNull();
    private static ImmutableList<BlockFruitToolRack> allFruitToolRack = Helpers.getNull();
    private static ImmutableList<BlockFruitTrapDoor> allFruitTrapDoors = Helpers.getNull();
    private static ImmutableList<BlockFruitWorkbench> allFruitWorkbench = Helpers.getNull();
    private static ImmutableList<BlockFruitChestTFCF> allFruitChestBlocks = Helpers.getNull();
    private static ImmutableList<BlockFruitLoom> allFruitLoomBlocks = Helpers.getNull();
    private static ImmutableList<BlockFluidBase> allFluidBlocks = Helpers.getNull();
    private static ImmutableList<BlockCropTFC> allCropBlocks = Helpers.getNull();
    private static ImmutableList<BlockCropDead> allDeadCrops = Helpers.getNull();
    private static ImmutableList<BlockBerryBush> allBerryBushBlocks = Helpers.getNull();
    private static ImmutableList<BlockRockVariantTFCF> allBlockRockVariantsTFCF = Helpers.getNull();
    private static ImmutableList<BlockWallTFCF> allWallBlocks = Helpers.getNull();
    private static ImmutableList<BlockStairsTFCF> allStairBlocks = Helpers.getNull();
    private static ImmutableList<BlockSlabTFCF.Half> allSlabBlocks = Helpers.getNull();
    private static ImmutableList<BlockSlabTFC.Half> allSlabBlocksTFC = Helpers.getNull();
    private static ImmutableList<BlockStairsTFC> allStairBlocksTFC = Helpers.getNull();
    private static ImmutableList<BlockPlanksTFC> allPlanksTFC = Helpers.getNull();
    private static ImmutableList<BlockSurfaceRock> allSurfaceRocks = Helpers.getNull();
    private static ImmutableList<BlockSurfaceSeashells> allSurfaceSeashells = Helpers.getNull();
    private static ImmutableList<BlockSurfaceFlint> allSurfaceFlint = Helpers.getNull();
    private static ImmutableList<BlockSurfaceBones> allSurfaceBones = Helpers.getNull();
    private static ImmutableList<BlockDriftwood> allSurfaceDriftwood = Helpers.getNull();
    private static ImmutableList<BlockTwig> allSurfaceTwig = Helpers.getNull();
    private static ImmutableList<BlockPinecone> allSurfacePinecone = Helpers.getNull();
    private static ImmutableList<Block> allBambooLog = Helpers.getNull();
    private static ImmutableList<Block> allBambooLeaves = Helpers.getNull();
    private static ImmutableList<Block> allBambooSapling = Helpers.getNull();
    private static ImmutableList<BlockLeavesTFCF> allNormalTreeLeaves = Helpers.getNull();
    private static ImmutableList<BlockLogTFCF> allNormalTreeLog = Helpers.getNull();
    private static ImmutableList<BlockSurfaceOreDeposit> allSurfaceOreBlocks = Helpers.getNull();
    private static ImmutableList<BlockCoral> allCoralPlants = Helpers.getNull();
    private static ImmutableList<BlockWaterGlowPlant> allGlowWaterPlants = Helpers.getNull();
    private static ImmutableList<BlockWaterPlantTFCF> allWaterPlantBlocks = Helpers.getNull();
    private static ImmutableList<BlockHangingPlantTFCF> allHangingPlantBlocks = Helpers.getNull();
    private static ImmutableList<BlockHangingCreepingPlantTFCF> allHangingCreepingPlantBlocks = Helpers.getNull();
    private static ImmutableList<BlockCreepingPlantTFCF> allCreepingPlantBlocks = Helpers.getNull();
    private static ImmutableList<BlockTallGrassWater> allTallGrassWaterBlocks = Helpers.getNull();
    private static ImmutableList<BlockShortGrassTFCF> allShortGrassBlocks = Helpers.getNull();
    private static ImmutableList<BlockTallGrassTFCF> allTallGrassBlocks = Helpers.getNull();
    private static ImmutableList<BlockPlantDummy1> allStandardBlocks = Helpers.getNull();
    private static ImmutableList<BlockCaveMushroom> allMushroomPlantBlocks = Helpers.getNull();
    private static ImmutableList<BlockPebbleWater> allPebbleWater = Helpers.getNull();
    private static ImmutableList<BlockLightstone> allLightstoneBlocks = Helpers.getNull();
    private static ImmutableList<BlockJoshuaTreeFlower> allJoshuaTreeFlowerBlocks = Helpers.getNull();
    private static ImmutableList<BlockJoshuaTreeLog> allJoshuaTreeLogBlocks = Helpers.getNull();
    private static ImmutableList<BlockJoshuaTreeSapling> allJoshuaTreeSaplingBlocks = Helpers.getNull();
    //private static ImmutableList<MultiBlockBase> allMultiBlocks = Helpers.getNull();

    public static String[] bamboo = {"arrow_bamboo", "black_bamboo", "blue_bamboo", "dragon_bamboo", "golden_bamboo", "narrow_leaf_bamboo", "red_bamboo", "temple_bamboo", "thorny_bamboo", "timber_bamboo", "tinwa_bamboo", "weavers_bamboo"};
    public static Tree[] bambooTrees = {TreesTFCF.ARROW_BAMBOO, TreesTFCF.BLACK_BAMBOO, TreesTFCF.BLUE_BAMBOO, TreesTFCF.DRAGON_BAMBOO, TreesTFCF.GOLDEN_BAMBOO, TreesTFCF.NARROW_LEAF_BAMBOO, TreesTFCF.RED_BAMBOO, TreesTFCF.TEMPLE_BAMBOO, TreesTFCF.THORNY_BAMBOO, TreesTFCF.TIMBER_BAMBOO, TreesTFCF.TINWA_BAMBOO, TreesTFCF.WEAVERS_BAMBOO};

    public static ImmutableList<ItemBlock> getAllNormalItemBlocks()
    {
        return allNormalItemBlocks;
    }

    public static ImmutableList<Block> getAllInventoryItemBlocks()
    {
        return allInventoryItemBlocks;
    }

    public static ImmutableList<Block> getAllFoodIBs()
    {
        return allFoodItemBlocks;
    }

    public static ImmutableList<BlockFenceGateLog> getAllFenceGateLogBlocks()
    {
        return allFenceGateLogBlocks;
    }

    public static ImmutableList<BlockFruitTreeLeaves> getAllFruitLeaves()
    {
        return allFruitLeaves;
    }

    public static ImmutableList<BlockFruitTreeSapling> getAllFruitSapling()
    {
        return allFruitSapling;
    }

    /*public static ImmutableList<BlockFruitBarrelTest> getAllFruitBarrel()
    {
        return allFruitBarrel;
    }*/

    public static ImmutableList<BlockFruitBookshelves> getAllFruitBookshelves()
    {
        return allFruitBookshelves;
    }

    public static ImmutableList<BlockFruitButton> getAllFruitButton()
    {
        return allFruitButton;
    }

    public static ImmutableList<BlockFruitDoor> getAllFruitDoors() 
    { 
        return allFruitDoors; 
    }

    public static ImmutableList<BlockFruitPlanks> getAllFruitPlanks()
    {
        return allFruitPlanks;
    }

    public static ImmutableList<BlockPlanksTFC> getAllPlanksTFC()
    {
        return allPlanksTFC;
    }

    public static ImmutableList<BlockFruitSlab.Half> getAllFruitSlabBlocks()
    {
        return allFruitSlabBlocks;
    }

    public static ImmutableList<BlockFruitStairs> getAllFruitStairBlocks()
    {
        return allFruitStairBlocks;
    }

    public static ImmutableList<BlockFruitPressurePlate> getAllFruitPressurePlate()
    {
        return allFruitPressurePlate;
    }

    public static ImmutableList<BlockFruitFence> getAllFruitFences() 
    { 
        return allFruitFences; 
    }

    public static ImmutableList<BlockFruitFenceGate> getAllFruitFenceGates() 
    { 
        return allFruitFenceGates; 
    }

    public static ImmutableList<BlockFruitLogFence> getAllFruitLogFences() 
    { 
        return allFruitLogFences; 
    }

    public static ImmutableList<BlockFruitLogFenceGate> getAllFruitLogFenceGates() 
    { 
        return allFruitLogFenceGates; 
    }

    public static ImmutableList<BlockFruitSupport> getAllFruitSupport()
    {
        return allFruitSupport;
    }

    public static ImmutableList<BlockFruitToolRack> getAllFruitToolRack()
    {
        return allFruitToolRack;
    }

    public static ImmutableList<BlockFruitTrapDoor> getAllFruitTrapdoors() 
    { 
        return allFruitTrapDoors; 
    }

    public static ImmutableList<BlockFruitWorkbench> getAllFruitWorkbench()
    {
        return allFruitWorkbench;
    }

    public static ImmutableList<BlockFruitChestTFCF> getAllFruitChestBlocks()
    {
        return allFruitChestBlocks;
    }

    public static ImmutableList<BlockFruitLoom> getAllFruitLoomBlocks()
    {
        return allFruitLoomBlocks;
    }

    public static ImmutableList<BlockCropTFC> getAllCropBlocks()
    {
        return allCropBlocks;
    }

    public static ImmutableList<BlockCropDead> getAllDeadCrops()
    {
        return allDeadCrops;
    }

    public static ImmutableList<BlockFluidBase> getAllFluidBlocks()
    {
        return allFluidBlocks;
    }

    public static ImmutableList<BlockBerryBush> getAllBerryBushBlocks()
    {
        return allBerryBushBlocks;
    }

    public static ImmutableList<BlockRockVariantTFCF> getAllBlockRockVariantsTFCF()
    {
        return allBlockRockVariantsTFCF;
    }

    public static ImmutableList<BlockWallTFCF> getAllWallBlocks()
    {
        return allWallBlocks;
    }

    public static ImmutableList<BlockStairsTFCF> getAllStairBlocks()
    {
        return allStairBlocks;
    }

    public static ImmutableList<BlockSlabTFCF.Half> getAllSlabBlocks()
    {
        return allSlabBlocks;
    }

    public static ImmutableList<BlockSlabTFC.Half> getAllSlabBlocksTFC()
    {
        return allSlabBlocksTFC;
    }

    public static ImmutableList<BlockStairsTFC> getAllStairBlocksTFC()
    {
        return allStairBlocksTFC;
    }

    public static ImmutableList<BlockSurfaceRock> getAllSurfaceRocks()
    {
        return allSurfaceRocks;
    }

    public static ImmutableList<BlockSurfaceSeashells> getAllSurfaceSeashells()
    {
        return allSurfaceSeashells;
    }

    public static ImmutableList<BlockSurfaceFlint> getAllSurfaceFlint()
    {
        return allSurfaceFlint;
    }

    public static ImmutableList<BlockSurfaceBones> getAllSurfaceBones()
    {
        return allSurfaceBones;
    }

    public static ImmutableList<BlockDriftwood> getAllSurfaceDriftwood()
    {
        return allSurfaceDriftwood;
    }

    public static ImmutableList<BlockTwig> getAllSurfaceTwig()
    {
        return allSurfaceTwig;
    }

    public static ImmutableList<BlockPinecone> getAllSurfacePinecone()
    {
        return allSurfacePinecone;
    }

    public static ImmutableList<Block> getAllBambooLog()
    {
        return allBambooLog;
    }

    public static ImmutableList<Block> getAllBambooLeaves()
    {
        return allBambooLeaves;
    }

    public static ImmutableList<Block> getAllBambooSapling()
    {
        return allBambooSapling;
    }
    
    public static ImmutableList<BlockLeavesTFCF> getAllNormalTreeLeaves()
    {
        return allNormalTreeLeaves;
    }
    
    public static ImmutableList<BlockLogTFCF> getAllNormalTreeLog()
    {
        return allNormalTreeLog;
    }

    public static ImmutableList<BlockSurfaceOreDeposit> getAllSurfaceOreBlocks()
    {
        return allSurfaceOreBlocks;
    }

    public static ImmutableList<BlockCoral> getAllCoralPlants()
    {
        return allCoralPlants;
    }

    public static ImmutableList<BlockWaterGlowPlant> getAllGlowWaterPlants()
    {
        return allGlowWaterPlants;
    }

    public static ImmutableList<BlockWaterPlantTFCF> getAllWaterPlantBlocks()
    {
        return allWaterPlantBlocks;
    }

    public static ImmutableList<BlockHangingPlantTFCF> getAllHangingPlantBlocks()
    {
        return allHangingPlantBlocks;
    }

    public static ImmutableList<BlockHangingCreepingPlantTFCF> getAllHangingCreepingPlantBlocks()
    {
        return allHangingCreepingPlantBlocks;
    }

    public static ImmutableList<BlockCreepingPlantTFCF> getAllCreepingPlantBlocks()
    {
        return allCreepingPlantBlocks;
    }

    public static ImmutableList<BlockTallGrassWater> getAllTallGrassWaterBlocks()
    {
        return allTallGrassWaterBlocks;
    }

    public static ImmutableList<BlockShortGrassTFCF> getAllShortGrassBlocks()
    {
        return allShortGrassBlocks;
    }

    public static ImmutableList<BlockTallGrassTFCF> getAllTallGrassBlocks()
    {
        return allTallGrassBlocks;
    }

    public static ImmutableList<BlockPlantDummy1> getAllStandardBlocks()
    {
        return allStandardBlocks;
    }

    public static ImmutableList<BlockCaveMushroom> getAllMushroomPlantBlocks()
    {
        return allMushroomPlantBlocks;
    }

    public static ImmutableList<BlockPebbleWater> getAllPebbleWater()
    {
        return allPebbleWater;
    }

    public static ImmutableList<BlockLightstone> getAllLightstoneBlocks()
    {
        return allLightstoneBlocks;
    }

    public static ImmutableList<BlockJoshuaTreeFlower> getAllJoshuaTreeFlowerBlocks()
    {
        return allJoshuaTreeFlowerBlocks;
    }

    public static ImmutableList<BlockJoshuaTreeLog> getAllJoshuaTreeLogBlocks()
    {
        return allJoshuaTreeLogBlocks;
    }

    public static ImmutableList<BlockJoshuaTreeSapling> getAllJoshuaTreeSaplingBlocks()
    {
        return allJoshuaTreeSaplingBlocks;
    }

    /*public static ImmutableList<MultiBlockBase> getAllMultiBlocks()
    {
        return allMultiBlocks;
    }*/

    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        // This is called here because it needs to wait until Metal registry has fired
        FluidsTFCF.registerFluids();
        IForgeRegistry<Block> r = event.getRegistry();

        ImmutableList.Builder<ItemBlock> normalItemBlocks = ImmutableList.builder();
        ImmutableList.Builder<Block> inventoryItemBlocks = ImmutableList.builder();
        ImmutableList.Builder<BlockFluidBase> fluids = ImmutableList.builder();
        ImmutableList.Builder<Block> itemBambooLog = ImmutableList.builder();
        ImmutableList.Builder<Block> itemBambooLeaves = ImmutableList.builder();
        ImmutableList.Builder<Block> itemBambooSapling = ImmutableList.builder();
        ImmutableList.Builder<BlockLeavesTFCF> itemNormalTreeLeaves = ImmutableList.builder();
        ImmutableList.Builder<BlockLogTFCF> normalTreeLog = ImmutableList.builder();
        ImmutableList.Builder<Block> foodItemBlocks = ImmutableList.builder();
        ImmutableList.Builder<BlockFenceGateLog> fenceGatesLog = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitTreeLeaves> fruitLeaves = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitTreeSapling> fruitSapling = ImmutableList.builder();
        //ImmutableList.Builder<BlockFruitBarrelTest> fruitBarrel = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitBookshelves> fruitBookshelves = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitButton> fruitButton = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitDoor> fruitDoors = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitPlanks> fruitPlanks = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitSlab.Half> fruitSlab = new Builder<>();
        ImmutableList.Builder<BlockFruitStairs> fruitStairs = new Builder<>();
        ImmutableList.Builder<BlockFruitPressurePlate> fruitPressurePlate = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitFence> fruitFences = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitFenceGate> fruitFenceGates = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitLogFence> fruitLogFences = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitLogFenceGate> fruitLogFenceGates = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitSupport> fruitSupport = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitToolRack> fruitToolRack = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitTrapDoor> fruitTrapdoors = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitWorkbench> fruitWorkbench = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitChestTFCF> fruitChests = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitLoom> fruitLoom = ImmutableList.builder();
        ImmutableList.Builder<BlockCropTFC> cropBlocks = ImmutableList.builder();
        ImmutableList.Builder<BlockCropDead> deadCrops = ImmutableList.builder();
        ImmutableList.Builder<BlockBerryBush> cropBerryBushBlocks = ImmutableList.builder();
        ImmutableList.Builder<BlockRockVariantTFCF> blockRockVariantsTFCF = ImmutableList.builder();
        ImmutableList.Builder<BlockWallTFCF> blockWallTFCF = ImmutableList.builder();
        ImmutableList.Builder<BlockStairsTFCF> blockStairsTFC = new Builder<>();
        ImmutableList.Builder<BlockSlabTFCF.Half> blockSlabTFCF = new Builder<>();
        ImmutableList.Builder<BlockSurfaceRock> surfaceRock = ImmutableList.builder();
        ImmutableList.Builder<BlockSurfaceSeashells> surfaceSeashell = ImmutableList.builder();
        ImmutableList.Builder<BlockSurfaceFlint> surfaceFlint = ImmutableList.builder();
        ImmutableList.Builder<BlockSurfaceBones> surfaceBone = ImmutableList.builder();
        ImmutableList.Builder<BlockDriftwood> surfaceDriftwood = ImmutableList.builder();
        ImmutableList.Builder<BlockTwig> surfaceTwig = ImmutableList.builder();
        ImmutableList.Builder<BlockPinecone> surfacePinecone = ImmutableList.builder();
        ImmutableList.Builder<BlockSlabTFC.Half> blockSlabTFC = new Builder<>();
        ImmutableList.Builder<BlockStairsTFC> blockStairTFC = new Builder<>();
        ImmutableList.Builder<BlockPlanksTFC> planksTFC = ImmutableList.builder();
        ImmutableList.Builder<BlockSurfaceOreDeposit> surfaceOreBlocks = ImmutableList.builder();
        ImmutableList.Builder<BlockPebbleWater> pebbleWater = ImmutableList.builder();
        ImmutableList.Builder<BlockCoral> plantCoral = ImmutableList.builder();
        ImmutableList.Builder<BlockWaterGlowPlant> plantGlowWater = ImmutableList.builder();
        ImmutableList.Builder<BlockLightstone> blockLightstone = ImmutableList.builder();
        //ImmutableList.Builder<MultiBlockBase> multiBlock = ImmutableList.builder();

        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/yucca/yucca_bale", new BlockBale(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/yucca/yucca_fiber_bale", new BlockBale(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/cotton/cotton_bale", new BlockBale(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/cotton/cotton_yarn_bale", new BlockBale(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/flax/flax_bale", new BlockBale(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/flax/flax_fiber_bale", new BlockBale(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/hemp/hemp_bale", new BlockBale(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/hemp/hemp_fiber_bale", new BlockBale(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/jute/jute_bale", new BlockBale(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/jute/jute_fiber_bale", new BlockBale(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/linen/linen_bale", new BlockBale(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/linen/linen_string_bale", new BlockBale(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/papyrus/papyrus_fiber_bale", new BlockBale(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/silk/silk_string_bale", new BlockBale(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "crop/bales/sisal/sisal_fiber_bale", new BlockBale(), CT_DECORATIONS)));

        if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
        {
            normalItemBlocks.add(new ItemBlockTFC(register(r, "ceramics/earthenware/earthenware_clay_block", new BlockEarthenwareClay(), CT_ROCK_BLOCKS)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "ceramics/earthenware/earthenware_bricks", new BlockFireBrick(), CT_DECORATIONS)));
            normalItemBlocks.add(new ItemBlockLargeVessel(register(r, "ceramics/earthenware/fired/large_vessel", new BlockLargeVessel(), CT_POTTERY)));
        }

        if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
        {
            normalItemBlocks.add(new ItemBlockTFC(register(r, "ceramics/kaolinite/kaolinite_clay_block", new BlockKaoliniteClay(), CT_ROCK_BLOCKS)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "ceramics/kaolinite/kaolinite_bricks", new BlockFireBrick(), CT_DECORATIONS)));
            normalItemBlocks.add(new ItemBlockLargeVessel(register(r, "ceramics/kaolinite/fired/large_vessel", new BlockLargeVessel(), CT_POTTERY)));
        }

        if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
        {
            normalItemBlocks.add(new ItemBlockTFC(register(r, "ceramics/stoneware/stoneware_clay_block", new BlockStonewareClay(), CT_ROCK_BLOCKS)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "ceramics/stoneware/stoneware_bricks", new BlockFireBrick(), CT_DECORATIONS)));
            normalItemBlocks.add(new ItemBlockLargeVessel(register(r, "ceramics/stoneware/fired/large_vessel", new BlockLargeVessel(), CT_POTTERY)));
        }

        normalItemBlocks.add(new ItemBlockUrn(register(r, "storage/urn", new BlockUrn(), CT_POTTERY)));
        normalItemBlocks.add(new ItemBlockUrnLoot(register(r, "storage/urn_loot", new BlockUrnLoot(), CT_POTTERY)));
        normalItemBlocks.add(new ItemBlockCrate(register(r, "storage/crate", new BlockCrate(), CT_DECORATIONS)));

        /*normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/tube/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/brain/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/bubble/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/fire/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/horn/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA)));

        for (EnumDyeColor dyeColor : EnumDyeColor.values())
        {
            BlockCoral brain = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral bubble = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral fire = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral horn = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral tube = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));

            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/brain/" + dyeColor.getName(), brain, CT_FLORA)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/bubble/" + dyeColor.getName(), bubble, CT_FLORA)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/fire/" + dyeColor.getName(), fire, CT_FLORA)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/horn/" + dyeColor.getName(), horn, CT_FLORA)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/tube/" + dyeColor.getName(), tube, CT_FLORA)));

            BlockCoral.BRAIN_CORAL.put(dyeColor, brain);
            BlockCoral.BUBBLE_CORAL.put(dyeColor, bubble);
            BlockCoral.FIRE_CORAL.put(dyeColor, fire);
            BlockCoral.HORN_CORAL.put(dyeColor, horn);
            BlockCoral.TUBE_CORAL.put(dyeColor, tube);
        }

        normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/fan/tube/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/fan/brain/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/fan/bubble/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/fan/fire/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/fan/horn/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA)));

        for (EnumDyeColor dyeColor : EnumDyeColor.values())
        {
            BlockCoral brain = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral bubble = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral fire = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral horn = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral tube = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));

            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/fan/brain/" + dyeColor.getName(), brain, CT_FLORA)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/fan/bubble/" + dyeColor.getName(), bubble, CT_FLORA)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/fan/fire/" + dyeColor.getName(), fire, CT_FLORA)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/fan/horn/" + dyeColor.getName(), horn, CT_FLORA)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/fan/tube/" + dyeColor.getName(), tube, CT_FLORA)));

            BlockCoral.BRAIN_CORAL_FAN.put(dyeColor, brain);
            BlockCoral.BUBBLE_CORAL_FAN.put(dyeColor, bubble);
            BlockCoral.FIRE_CORAL_FAN.put(dyeColor, fire);
            BlockCoral.HORN_CORAL_FAN.put(dyeColor, horn);
            BlockCoral.TUBE_CORAL_FAN.put(dyeColor, tube);
        }*/

        // Normal Corals
        plantCoral.add(register(r, "coral/tube/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA));
        plantCoral.add(register(r, "coral/brain/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA));
        plantCoral.add(register(r, "coral/bubble/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA));
        plantCoral.add(register(r, "coral/fire/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA));
        plantCoral.add(register(r, "coral/horn/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA));

        for (EnumDyeColor dyeColor : EnumDyeColor.values())
        {
            
            BlockCoral brainNormal = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral bubbleNormal = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral fireNormal = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral hornNormal = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral tubeNormal = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));

            plantCoral.add(register(r, "coral/brain/" + dyeColor.getName(), brainNormal, CT_FLORA));
            plantCoral.add(register(r, "coral/bubble/" + dyeColor.getName(), bubbleNormal, CT_FLORA));
            plantCoral.add(register(r, "coral/fire/" + dyeColor.getName(), fireNormal, CT_FLORA));
            plantCoral.add(register(r, "coral/horn/" + dyeColor.getName(), hornNormal, CT_FLORA));
            plantCoral.add(register(r, "coral/tube/" + dyeColor.getName(), tubeNormal, CT_FLORA));

            BlockCoral.BRAIN_CORAL.put(dyeColor, brainNormal);
            BlockCoral.BUBBLE_CORAL.put(dyeColor, bubbleNormal);
            BlockCoral.FIRE_CORAL.put(dyeColor, fireNormal);
            BlockCoral.HORN_CORAL.put(dyeColor, hornNormal);
            BlockCoral.TUBE_CORAL.put(dyeColor, tubeNormal);
        }

        // Fan Corals
        plantCoral.add(register(r, "coral/fan/tube/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA));
        plantCoral.add(register(r, "coral/fan/brain/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA));
        plantCoral.add(register(r, "coral/fan/bubble/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA));
        plantCoral.add(register(r, "coral/fan/fire/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA));
        plantCoral.add(register(r, "coral/fan/horn/dead", new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.SNOW), CT_FLORA));

        for (EnumDyeColor dyeColor : EnumDyeColor.values())
        {
            BlockCoral brainFan = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral bubbleFan = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral fireFan = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral hornFan = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));
            BlockCoral tubeFan = new BlockCoral(FluidsTFC.SALT_WATER.get(), MapColor.getBlockColor(dyeColor));

            plantCoral.add(register(r, "coral/fan/brain/" + dyeColor.getName(), brainFan, CT_FLORA));
            plantCoral.add(register(r, "coral/fan/bubble/" + dyeColor.getName(), bubbleFan, CT_FLORA));
            plantCoral.add(register(r, "coral/fan/fire/" + dyeColor.getName(), fireFan, CT_FLORA));
            plantCoral.add(register(r, "coral/fan/horn/" + dyeColor.getName(), hornFan, CT_FLORA));
            plantCoral.add(register(r, "coral/fan/tube/" + dyeColor.getName(), tubeFan, CT_FLORA));

            BlockCoral.BRAIN_CORAL_FAN.put(dyeColor, brainFan);
            BlockCoral.BUBBLE_CORAL_FAN.put(dyeColor, bubbleFan);
            BlockCoral.FIRE_CORAL_FAN.put(dyeColor, fireFan);
            BlockCoral.HORN_CORAL_FAN.put(dyeColor, hornFan);
            BlockCoral.TUBE_CORAL_FAN.put(dyeColor, tubeFan);
        }
        allCoralPlants = plantCoral.build();
        for (BlockCoral plantCoralBlock : allCoralPlants)
        {
            normalItemBlocks.add(new ItemBlockTFC(plantCoralBlock));
        }

        normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/block/brain/dead", new BlockCoralBlock(MapColor.SNOW), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/block/bubble/dead", new BlockCoralBlock(MapColor.SNOW), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/block/fire/dead", new BlockCoralBlock(MapColor.SNOW), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/block/horn/dead", new BlockCoralBlock(MapColor.SNOW), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/block/tube/dead", new BlockCoralBlock(MapColor.SNOW), CT_FLORA)));

        for (EnumDyeColor dyeColor : EnumDyeColor.values())
        {
            BlockCoralBlock brain = new BlockCoralBlock(MapColor.getBlockColor(dyeColor));
            BlockCoralBlock bubble = new BlockCoralBlock(MapColor.getBlockColor(dyeColor));
            BlockCoralBlock fire = new BlockCoralBlock(MapColor.getBlockColor(dyeColor));
            BlockCoralBlock horn = new BlockCoralBlock(MapColor.getBlockColor(dyeColor));
            BlockCoralBlock tube = new BlockCoralBlock(MapColor.getBlockColor(dyeColor));

            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/block/brain/" + dyeColor.getName(), brain, CT_FLORA)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/block/bubble/" + dyeColor.getName(), bubble, CT_FLORA)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/block/fire/" + dyeColor.getName(), fire, CT_FLORA)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/block/horn/" + dyeColor.getName(), horn, CT_FLORA)));
            normalItemBlocks.add(new ItemBlockTFC(register(r, "coral/block/tube/" + dyeColor.getName(), tube, CT_FLORA)));

            BlockCoralBlock.BRAIN_CORAL_BLOCK.put(dyeColor, brain);
            BlockCoralBlock.BUBBLE_CORAL_BLOCK.put(dyeColor, bubble);
            BlockCoralBlock.FIRE_CORAL_BLOCK.put(dyeColor, fire);
            BlockCoralBlock.HORN_CORAL_BLOCK.put(dyeColor, horn);
            BlockCoralBlock.TUBE_CORAL_BLOCK.put(dyeColor, tube);
        }

        {
            plantGlowWater.add(register(r, "plants/glowing_sea_banana", new BlockWaterGlowPlant(FluidsTFC.SALT_WATER.get()), CT_FLORA));
        }
        allGlowWaterPlants = plantGlowWater.build();
        for (BlockWaterGlowPlant plantGlowWaterBlock : allGlowWaterPlants)
        {
            normalItemBlocks.add(new ItemBlockTFC(plantGlowWaterBlock));
        }

        normalItemBlocks.add(new ItemBlockTFC(register(r, "plants/blueshroom", new BlockCaveMushroom(0.3F, FoodDataTFCF.RAW_BLUESHROOM, new PotionEffectToHave(MobEffects.HUNGER, 610, 1, 4), new PotionEffectToHave(MobEffects.HASTE, 610, 1, 4), "blueshroom", "mushroom", "category_vegetable"), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "plants/glowshroom", new BlockCaveMushroom(0.5F, FoodDataTFCF.RAW_GLOWSHROOM, new PotionEffectToHave(MobEffects.HUNGER, 610, 1, 4), new PotionEffectToHave(MobEffects.GLOWING, 610, 1, 4), "glowshroom", "mushroom", "category_vegetable"), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "plants/magma_shroom", new BlockCaveMushroom(0.2F, FoodDataTFCF.RAW_MAGMA_SHROOM, new PotionEffectToHave(MobEffects.HUNGER, 610, 1, 4), new PotionEffectToHave(MobEffects.FIRE_RESISTANCE, 610, 1, 4), "magma_shroom", "mushroom", "category_vegetable"), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "plants/poison_shroom", new BlockCaveMushroom(0.1F, FoodDataTFCF.RAW_POISON_SHROOM, new PotionEffectToHave(MobEffects.POISON, 610, 1, 4), new PotionEffectToHave(MobEffects.ABSORPTION, 610, 1, 4), "poison_shroom", "mushroom", "category_vegetable"), CT_FLORA)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "plants/sulphur_shroom", new BlockCaveMushroom(0.1F, FoodDataTFCF.RAW_SULPHUR_SHROOM, new PotionEffectToHave(MobEffects.MINING_FATIGUE, 610, 1, 4), new PotionEffectToHave(MobEffects.LUCK, 610, 1, 4), "sulphur_shroom", "mushroom", "category_vegetable"), CT_FLORA)));

        {
            Builder<BlockWaterPlantTFCF> plantWaterBlock = ImmutableList.builder();
            Builder<BlockHangingPlantTFCF> plantHangingBlock = ImmutableList.builder();
            Builder<BlockHangingCreepingPlantTFCF> plantHangingCreepingBlock = ImmutableList.builder();
            Builder<BlockCreepingPlantTFCF> plantCreepingBlock = ImmutableList.builder();
            Builder<BlockTallGrassWater> plantTallGrassWaterBlock = ImmutableList.builder();
            Builder<BlockShortGrassTFCF> plantShortGrassBlock = ImmutableList.builder();
            Builder<BlockTallGrassTFCF> plantTallGrassBlock = ImmutableList.builder();
            Builder<BlockPlantDummy1> plantStandardBlock = ImmutableList.builder();

            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
            {
                if (plant.getPlantType() == Plant.PlantType.WATER)
                {
                    plantWaterBlock.add(register(r, "plants/" + plant.getRegistryName().getPath(), new BlockWaterPlantTFCF(FluidsTFC.FRESH_WATER.get(), plant), CT_FLORA));
                }
                else if (plant.getPlantType() == Plant.PlantType.WATER_SEA)
                {
                    plantWaterBlock.add(register(r, "plants/" + plant.getRegistryName().getPath(), new BlockWaterPlantTFCF(FluidsTFC.SALT_WATER.get(), plant), CT_FLORA));
                }
                else if (plant.getPlantType() == Plant.PlantType.TALL_WATER)
                {
                    plantWaterBlock.add(register(r, "plants/" + plant.getRegistryName().getPath(), new BlockTallWaterPlantTFCF(FluidsTFC.FRESH_WATER.get(), plant), CT_FLORA));
                }
                else if (plant.getPlantType() == Plant.PlantType.TALL_WATER_SEA)
                {
                    plantWaterBlock.add(register(r, "plants/" + plant.getRegistryName().getPath(), new BlockTallWaterPlantTFCF(FluidsTFC.SALT_WATER.get(), plant), CT_FLORA));
                }
                else if (plant.getPlantType() == Plant.PlantType.HANGING && (
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.BEARDED_MOSS) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.LIANA) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.HANGING_VINE) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.JUNGLE_VINE)))
                {
                    plantHangingBlock.add(register(r, "plants/" + plant.getRegistryName().getPath(), new BlockHangingPlantTFCF(plant), CT_FLORA));
                    plantHangingCreepingBlock.add(register(r, "plants/" + plant.getRegistryName().getPath() + "_creeping", new BlockHangingCreepingPlantTFCF(plant), CT_FLORA));
                }
                else if (plant.getPlantType() == Plant.PlantType.CREEPING && (
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.TACKWEED) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.TAKAKIA) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.IVY) || 
                    plant == TFCRegistries.PLANTS.getValue(DefaultPlants.MORNING_GLORY) || 
                    plant == TFCRegistries.PLANTS.getValue(DefaultPlants.MOSS) || 
                    plant == TFCRegistries.PLANTS.getValue(DefaultPlants.REINDEER_LICHEN)))
                {
                    plantCreepingBlock.add(register(r, "plants/" + plant.getRegistryName().getPath(), new BlockCreepingPlantTFCF(plant), CT_FLORA));
                }
                else if (plant.getPlantType() == Plant.PlantType.TALL_GRASS && (
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.SAWGRASS)))
                {
                    plantTallGrassWaterBlock.add(register(r, "plants/" + plant.getRegistryName().getPath(), new BlockTallGrassWater(plant), CT_FLORA));
                }
                /*else if (plant.getPlantType() == Plant.PlantType.SHORT_GRASS && (
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_BARLEY) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_RICE) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_WHEAT)))
                {
                    plantShortGrassBlock.add(register(r, "plants/" + plant.getRegistryName().getPath(), new BlockShortGrassTFCF(plant), CT_FLORA));
                }
                else if (plant.getPlantType() == Plant.PlantType.TALL_GRASS && (
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_BARLEY) || 
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_WHEAT)))
                {
                    plantTallGrassBlock.add(register(r, "plants/" + plant.getRegistryName().getPath(), new BlockTallGrassTFCF(plant), CT_FLORA));
                }
                else if (plant.getPlantType() == Plant.PlantType.STANDARD && (
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.BLUE_GINGER)))
                {
                    plantStandardBlock.add(register(r, "plants/" + plant.getRegistryName().getPath(), new BlockPlantDummy1(plant), CT_FLORA));
                }*/
            }
            allWaterPlantBlocks = plantWaterBlock.build();
            for (BlockWaterPlantTFCF blockWaterPlant : allWaterPlantBlocks)
            {
                normalItemBlocks.add(new ItemBlockTFC(blockWaterPlant));
            }
            allHangingPlantBlocks = plantHangingBlock.build();
            for (BlockHangingPlantTFCF blockHangingPlant : allHangingPlantBlocks)
            {
                normalItemBlocks.add(new ItemBlockTFC(blockHangingPlant));
            }
            allHangingCreepingPlantBlocks = plantHangingCreepingBlock.build();
            for (BlockHangingCreepingPlantTFCF blockHangingCreepingPlant : allHangingCreepingPlantBlocks)
            {
                normalItemBlocks.add(new ItemBlockTFC(blockHangingCreepingPlant));
            }
            allCreepingPlantBlocks = plantCreepingBlock.build();
            for (BlockCreepingPlantTFCF blockCreepingPlant : allCreepingPlantBlocks)
            {
                normalItemBlocks.add(new ItemBlockTFC(blockCreepingPlant));
            }
            allTallGrassWaterBlocks = plantTallGrassWaterBlock.build();
            for (BlockTallGrassWater blockTallGrassWaterPlant : allTallGrassWaterBlocks)
            {
                normalItemBlocks.add(new ItemBlockTallGrassWater((BlockTallGrassWater) blockTallGrassWaterPlant));
            }
            allShortGrassBlocks = plantShortGrassBlock.build();
            for (BlockShortGrassTFCF blockShortGrassPlant : allShortGrassBlocks)
            {
                normalItemBlocks.add(new ItemBlockTFC(blockShortGrassPlant));
            }
            allTallGrassBlocks = plantTallGrassBlock.build();
            for (BlockTallGrassTFCF blockTallGrassPlant : allTallGrassBlocks)
            {
                normalItemBlocks.add(new ItemBlockTFC(blockTallGrassPlant));
            }
            allStandardBlocks = plantStandardBlock.build();
            for (BlockPlantDummy1 blockStandardPlant : allStandardBlocks)
            {
                normalItemBlocks.add(new ItemBlockTFC(blockStandardPlant));
            }
        }

        {
            Builder<BlockCropTFC> b = ImmutableList.builder();

            for (CropTFCF crop : CropTFCF.values())
            {
                cropBlocks.add(register(r, "crop/" + crop.name().toLowerCase(), crop.createGrowingBlock()));
            }
        }

        {
            Builder<BlockCropDead> b = ImmutableList.builder();

            for (CropTFCF crop : CropTFCF.values())
            {
                deadCrops.add(register(r, "dead_crop/" + crop.name().toLowerCase(), crop.createDeadBlock()));
            }
        }

        Builder<BlockBerryBush> fBerry = ImmutableList.builder();

        for (BerryBushTFCF bush : BerryBushTFCF.values())
        {
            fBerry.add(register(r, "berry_bush/" + bush.name().toLowerCase(), new BlockBerryBush(bush), CT_FOOD));
        }

        allBerryBushBlocks = fBerry.build();
        allBerryBushBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        allCropBlocks = cropBlocks.build();
        allDeadCrops = deadCrops.build();

        /*for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
        {
            pebbleWater.add(register(r, "pebble/" + rock.getRegistryName().getPath().toLowerCase(), new BlockPebbleWater(FluidsTFC.SALT_WATER.get(), rock), CT_DECORATIONS));
        }
        allPebbleWater = pebbleWater.build();
        allPebbleWater.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });*/

        {
            blockLightstone.add(register(r, "groundcover/lightstone", new BlockLightstone(0.8f), CT_GEMS));
        }
        allLightstoneBlocks = blockLightstone.build();
        for (BlockLightstone lightstone : allLightstoneBlocks)
        {
            normalItemBlocks.add(new ItemBlockTFC(lightstone));
        }

        for (RockTFCF rockTFCF : RockTFCF.values())
        {
            if (ConfigTFCF.General.WORLD.enableAllBlockRockTypes || rockTFCF.shouldRockify())
            {
                for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                {
                    if (ConfigTFCF.General.WORLD.enableAllBlockTypes)
                    {
                        blockRockVariantsTFCF.add(register(r, rockTFCF.name().toLowerCase() + "/" + rock.getRegistryName().getPath(), BlockRockVariantTFCF.create(rock, rockTFCF), CT_ROCK_BLOCKS));
                    }
                    else
                    {
                        if (rockTFCF == RockTFCF.MOSSY_RAW || rockTFCF == RockTFCF.MUD_BRICKS || rockTFCF == RockTFCF.MUD)
                        {
                            blockRockVariantsTFCF.add(register(r, rockTFCF.name().toLowerCase() + "/" + rock.getRegistryName().getPath(), BlockRockVariantTFCF.create(rock, rockTFCF), CT_ROCK_BLOCKS));
                        }
                    }
                }
            }
            else
            {
                if (ConfigTFCF.General.WORLD.enableAllBlockTypes)
                {
                    blockRockVariantsTFCF.add(register(r, "single/" + rockTFCF.name().toLowerCase(), BlockRockVariantTFCF.create(null, rockTFCF), CT_ROCK_BLOCKS));
                }
            }
        }
        allBlockRockVariantsTFCF = blockRockVariantsTFCF.build();
        allBlockRockVariantsTFCF.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        if (ConfigTFCF.General.WORLD.enableGroundcoverOreDeposit)
        {
            for (Ore ore : TFCRegistries.ORES.getValuesCollection())
                for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                    surfaceOreBlocks.add(register(r, ("groundcover/ore/" + ore.getRegistryName().getPath().toLowerCase() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockSurfaceOreDeposit(ore, rock), CT_ROCK_BLOCKS));

            allSurfaceOreBlocks = surfaceOreBlocks.build();
            allSurfaceOreBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        }

        if (ConfigTFCF.General.WORLD.enableGroundcoverRock)
        {
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
            {
                surfaceRock.add(register(r, "groundcover/rock/" + rock.getRegistryName().getPath().toLowerCase(), new BlockSurfaceRock(rock), CT_ROCK_BLOCKS));
            }
            allSurfaceRocks = surfaceRock.build();
            allSurfaceRocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        }

        if (ConfigTFCF.General.WORLD.enableGroundcoverBones)
        {
            surfaceBone.add(register(r, "groundcover/bone", new BlockSurfaceBones(), CT_FLORA));
            allSurfaceBones = surfaceBone.build();
            allSurfaceBones.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        }

        if (ConfigTFCF.General.WORLD.enableGroundcoverDriftwood)
        {
            surfaceDriftwood.add(register(r, "groundcover/driftwood", new BlockDriftwood(), CT_FLORA));
            allSurfaceDriftwood = surfaceDriftwood.build();
            allSurfaceDriftwood.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        }

        if (ConfigTFCF.General.WORLD.enableGroundcoverFlint)
        {
            surfaceFlint.add(register(r, "groundcover/flint", new BlockSurfaceFlint(), CT_FLORA));
            allSurfaceFlint = surfaceFlint.build();
            allSurfaceFlint.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        }

        if (ConfigTFCF.General.WORLD.enableGroundcoverPinecone)
        {
            surfacePinecone.add(register(r, "groundcover/pinecone", new BlockPinecone(), CT_FLORA));
            allSurfacePinecone = surfacePinecone.build();
            allSurfacePinecone.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        }

        if (ConfigTFCF.General.WORLD.enableGroundcoverSeashell)
        {
            surfaceSeashell.add(register(r, "groundcover/seashell", new BlockSurfaceSeashells(), CT_FLORA));
            allSurfaceSeashells = surfaceSeashell.build();
            allSurfaceSeashells.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        }

        if (ConfigTFCF.General.WORLD.enableGroundcoverTwig)
        {
            surfaceTwig.add(register(r, "groundcover/twig", new BlockTwig(), CT_FLORA));
            allSurfaceTwig = surfaceTwig.build();
            allSurfaceTwig.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        }

        {
            // Walls
            for (RockTFCF rockTFCF : new RockTFCF[] {RockTFCF.MUD_BRICKS})
                for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                    blockWallTFCF.add(register(r, "wall/" + rockTFCF.name().toLowerCase() + "/" + rock.getRegistryName().getPath().toLowerCase(), new BlockWallTFCF(BlockRockVariantTFCF.get(rock, rockTFCF)), CT_DECORATIONS));

            // Stairs
            for (RockTFCF rockTFCF : new RockTFCF[] {RockTFCF.MUD_BRICKS})
                for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                    blockStairsTFC.add(register(r, "stairs/" + (rockTFCF.name().toLowerCase() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockStairsTFCF(rock, rockTFCF), CT_DECORATIONS));

            // Full slabs are the same as full blocks, they are not saved to a list, they are kept track of by the halfslab version.
            for (RockTFCF rockTFCF : new RockTFCF[] {RockTFCF.MUD_BRICKS})
                for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                    register(r, "double_slab/" + (rockTFCF.name().toLowerCase() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockSlabTFCF.Double(rock, rockTFCF));

            // Slabs
            for (RockTFCF rockTFCF : new RockTFCF[] {RockTFCF.MUD_BRICKS})
                for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                    blockSlabTFCF.add(register(r, "slab/" + (rockTFCF.name().toLowerCase() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockSlabTFCF.Half(rock, rockTFCF), CT_DECORATIONS));

            allWallBlocks = blockWallTFCF.build();
            allStairBlocks = blockStairsTFC.build();
            allSlabBlocks = blockSlabTFCF.build();
            allWallBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
            allStairBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        }

        // TFC Stairs
        for (Rock.Type type : new Rock.Type[] {Rock.Type.RAW})
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                blockStairTFC.add(register(r, "stairs/" + (type.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockStairsTFC(rock, type), CT_DECORATIONS));

        // TFC Double Slabs
        // Full slabs are the same as full blocks, they are not saved to a list, they are kept track of by the halfslab version.
        for (Rock.Type type : new Rock.Type[] {Rock.Type.RAW})
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                register(r, "double_slab/" + (type.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockSlabTFC.Double(rock, type));

        // TFC Slabs
        for (Rock.Type type : new Rock.Type[] {Rock.Type.RAW})
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                blockSlabTFC.add(register(r, "slab/" + (type.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockSlabTFC.Half(rock, type), CT_DECORATIONS));

        for (SeasonalTrees fruitTree : SeasonalTrees.values())
        {
            if (fruitTree.isNormalTree)
            {
                String name = fruitTree.getName().toLowerCase();
                if (!fruitTree.isSpecialBlock)
                {
                    itemNormalTreeLeaves.add(register(r, "wood/leaves/" + name, new BlockLeavesTFCF(fruitTree.normalTree, fruitTree), CT_WOOD));
                }
                if (fruitTree.isCustomLog)
                {
                    normalTreeLog.add(register(r, "wood/log/" + name, new BlockLogTFCF(fruitTree.normalTree, fruitTree), CT_WOOD));
                }
                if (fruitTree.normalTree == TreesTFCF.JOSHUA_TREE_TREE)
                {
                    Builder<BlockJoshuaTreeFlower> flowerJoshuaTree = ImmutableList.builder();
                    String name1 = fruitTree.getName().toLowerCase();

                    flowerJoshuaTree.add(register(r, "wood/leaves/" + name1, new BlockJoshuaTreeFlower(fruitTree.normalTree, fruitTree), CT_WOOD));

                    allJoshuaTreeFlowerBlocks = flowerJoshuaTree.build();
                    for (BlockJoshuaTreeFlower blockJoshuaTreeFlower : allJoshuaTreeFlowerBlocks)
                    {
                        normalItemBlocks.add(new ItemBlockTFC(blockJoshuaTreeFlower));
                    }
                }
            }
            /*else
            {
                String name = fruitTree.getName().toLowerCase();
                register(r, "wood/fruit_tree/branch/" + name, new BlockFruitTreeBranch(fruitTree));
                fruitLeaves.add(register(r, "wood/fruit_tree/leaves/" + name, new BlockFruitTreeLeaves(fruitTree), CT_WOOD));
                fruitSapling.add(register(r, "wood/fruit_tree/sapling/" + name, new BlockFruitTreeSapling(fruitTree), CT_WOOD));
                register(r, "wood/fruit_tree/trunk/" + name, new BlockFruitTreeTrunk(fruitTree));
                //fruitBarrel.add(register(r, "wood/fruit_tree/barrel/" + name, new BlockFruitBarrelTest(), CT_DECORATIONS));
                fruitBookshelves.add(register(r, "wood/fruit_tree/bookshelf/" + name, new BlockFruitBookshelves(), CT_DECORATIONS));
                fruitButton.add(register(r, "wood/fruit_tree/button/" + name, new BlockFruitButton(), CT_DECORATIONS));
                fruitDoors.add(register(r, "wood/fruit_tree/door/" + name, new BlockFruitDoor(name), CT_DECORATIONS));
                fruitPlanks.add(register(r, "wood/fruit_tree/planks/" + name, new BlockFruitPlanks(fruitTree), CT_WOOD));
                register(r, "wood/fruit_tree/double_slab/" + name, new BlockFruitSlab.Double(fruitTree));
                fruitSlab.add(register(r, "wood/fruit_tree/slab/" + name, new BlockFruitSlab.Half(fruitTree), CT_DECORATIONS));
                fruitStairs.add(register(r, "wood/fruit_tree/stairs/" + name, new BlockFruitStairs(fruitTree), CT_DECORATIONS));
                fruitPressurePlate.add(register(r, "wood/fruit_tree/pressure_plate/" + name, new BlockFruitPressurePlate(), CT_DECORATIONS));
                fruitFences.add(register(r, "wood/fruit_tree/fence/" +  name, new BlockFruitFence(), CT_DECORATIONS));
                fruitFenceGates.add(register(r, "wood/fruit_tree/fence_gate/" + name, new BlockFruitFenceGate(), CT_DECORATIONS));
                fruitLogFences.add(register(r, "wood/fruit_tree/fence_log/" + name, new BlockFruitLogFence(), CT_DECORATIONS));
                fruitLogFenceGates.add(register(r, "wood/fruit_tree/fence_gate_log/" + name, new BlockFruitLogFenceGate(), CT_DECORATIONS));
                fruitSupport.add(register(r, "wood/fruit_tree/support/" +  name, new BlockFruitSupport(), CT_DECORATIONS));
                fruitToolRack.add(register(r, "wood/fruit_tree/tool_rack/" + name, new BlockFruitToolRack(), CT_DECORATIONS));
                fruitTrapdoors.add(register(r, "wood/fruit_tree/trapdoor/" + name, new BlockFruitTrapDoor(), CT_DECORATIONS));
                fruitWorkbench.add(register(r, "wood/fruit_tree/workbench/" + name, new BlockFruitWorkbench(), CT_DECORATIONS));
                fruitChests.add(register(r, "wood/fruit_tree/chest/" + name, new BlockFruitChestTFCF(BlockFruitChestTFCF.TFCBASIC, fruitTree), CT_DECORATIONS));
                fruitChests.add(register(r, "wood/fruit_tree/chest_trap/" + name, new BlockFruitChestTFCF(BlockFruitChestTFCF.TFCTRAP, fruitTree), CT_DECORATIONS));
                fruitLoom.add(register(r, "wood/fruit_tree/loom/" + name, new BlockFruitLoom(fruitTree), CT_WOOD));
            }*/
        }

        for (IFruitTree fruitTree : FruitTree.values())
        {
            String name = fruitTree.getName().toLowerCase();
            //fruitBarrel.add(register(r, "wood/fruit_tree/barrel/" + name, new BlockFruitBarrelTest(), CT_DECORATIONS));
            fruitBookshelves.add(register(r, "wood/fruit_tree/bookshelf/" + name, new BlockFruitBookshelves(), CT_DECORATIONS));
            if (!TFCFlorae.FirmaLifeAdded)
            {
                fruitDoors.add(register(r, "wood/fruit_tree/door/" + name, new BlockFruitDoor(name), CT_DECORATIONS));
            }
            fruitButton.add(register(r, "wood/fruit_tree/button/" + name, new BlockFruitButton(), CT_DECORATIONS));
            fruitPlanks.add(register(r, "wood/fruit_tree/planks/" + name, new BlockFruitPlanks(fruitTree), CT_WOOD));
            register(r, "wood/fruit_tree/double_slab/" + name, new BlockFruitSlab.Double(fruitTree));
            fruitSlab.add(register(r, "wood/fruit_tree/slab/" + name, new BlockFruitSlab.Half(fruitTree), CT_DECORATIONS));
            fruitStairs.add(register(r, "wood/fruit_tree/stairs/" + name, new BlockFruitStairs(fruitTree), CT_DECORATIONS));
            fruitPressurePlate.add(register(r, "wood/fruit_tree/pressure_plate/" + name, new BlockFruitPressurePlate(), CT_DECORATIONS));
            if (!TFCFlorae.FirmaLifeAdded)
            {
                fruitFences.add(register(r, "wood/fruit_tree/fence/" + name, new BlockFruitFence(), CT_DECORATIONS));
                fruitFenceGates.add(register(r, "wood/fruit_tree/fence_gate/" + name, new BlockFruitFenceGate(), CT_DECORATIONS));
            }
            fruitLogFences.add(register(r, "wood/fruit_tree/fence_log/" + name, new BlockFruitLogFence(), CT_DECORATIONS));
            fruitLogFenceGates.add(register(r, "wood/fruit_tree/fence_gate_log/" + name, new BlockFruitLogFenceGate(), CT_DECORATIONS));
            fruitSupport.add(register(r, "wood/fruit_tree/support/" + name, new BlockFruitSupport(), CT_DECORATIONS));
            fruitToolRack.add(register(r, "wood/fruit_tree/tool_rack/" + name, new BlockFruitToolRack(), CT_DECORATIONS));
            if (!TFCFlorae.FirmaLifeAdded)
            {
                fruitTrapdoors.add(register(r, "wood/fruit_tree/trapdoor/" + name, new BlockFruitTrapDoor(), CT_DECORATIONS));
            }
            fruitWorkbench.add(register(r, "wood/fruit_tree/workbench/" + name, new BlockFruitWorkbench(), CT_DECORATIONS));
            fruitChests.add(register(r, "wood/fruit_tree/chest/" + name, new BlockFruitChestTFCF(BlockFruitChestTFCF.TFCBASIC, fruitTree), CT_DECORATIONS));
            fruitChests.add(register(r, "wood/fruit_tree/chest_trap/" + name, new BlockFruitChestTFCF(BlockFruitChestTFCF.TFCTRAP, fruitTree), CT_DECORATIONS));
            fruitLoom.add(register(r, "wood/fruit_tree/loom/" + name, new BlockFruitLoom(fruitTree), CT_WOOD));
        }

        // Cassia Cinnamon
        //fruitBarrel.add(register(r, "wood/fruit_tree/barrel/cassia_cinnamon", new BlockFruitBarrelTest(), CT_DECORATIONS));
        fruitBookshelves.add(register(r, "wood/fruit_tree/bookshelf/cassia_cinnamon", new BlockFruitBookshelves(), CT_DECORATIONS));
        fruitDoors.add(register(r, "wood/fruit_tree/door/cassia_cinnamon", new BlockFruitDoor("cassia_cinnamon"), CT_DECORATIONS));
        fruitButton.add(register(r, "wood/fruit_tree/button/cassia_cinnamon", new BlockFruitButton(), CT_DECORATIONS));
        planksTFC.add(register(r, "wood/fruit_tree/planks/cassia_cinnamon", new BlockPlanksTFC(TreesTFCF.CASSIA_CINNAMON_TREE), CT_WOOD));
        fruitPressurePlate.add(register(r, "wood/fruit_tree/pressure_plate/cassia_cinnamon", new BlockFruitPressurePlate(), CT_DECORATIONS));
        fruitFences.add(register(r, "wood/fruit_tree/fence/cassia_cinnamon", new BlockFruitFence(), CT_DECORATIONS));
        fruitFenceGates.add(register(r, "wood/fruit_tree/fence_gate/cassia_cinnamon", new BlockFruitFenceGate(), CT_DECORATIONS));
        fruitLogFences.add(register(r, "wood/fruit_tree/fence_log/cassia_cinnamon", new BlockFruitLogFence(), CT_DECORATIONS));
        fruitLogFenceGates.add(register(r, "wood/fruit_tree/fence_gate_log/cassia_cinnamon", new BlockFruitLogFenceGate(), CT_DECORATIONS));
        fruitSupport.add(register(r, "wood/fruit_tree/support/cassia_cinnamon", new BlockFruitSupport(), CT_DECORATIONS));
        fruitToolRack.add(register(r, "wood/fruit_tree/tool_rack/cassia_cinnamon", new BlockFruitToolRack(), CT_DECORATIONS));
        fruitTrapdoors.add(register(r, "wood/fruit_tree/trapdoor/cassia_cinnamon", new BlockFruitTrapDoor(), CT_DECORATIONS));
        fruitWorkbench.add(register(r, "wood/fruit_tree/workbench/cassia_cinnamon", new BlockFruitWorkbench(), CT_DECORATIONS));
        register(r, "wood/fruit_tree/double_slab/cassia_cinnamon", new BlockSlabTFC.Double(TreesTFCF.CASSIA_CINNAMON_TREE));
        blockSlabTFC.add(register(r, "wood/fruit_tree/slab/cassia_cinnamon", new BlockSlabTFC.Half(TreesTFCF.CASSIA_CINNAMON_TREE), CT_DECORATIONS));
        blockStairTFC.add(register(r, "wood/fruit_tree/stairs/cassia_cinnamon", new BlockStairsTFC(TreesTFCF.CASSIA_CINNAMON_TREE), CT_DECORATIONS));
        fruitChests.add(register(r, "wood/fruit_tree/chest/cassia_cinnamon", new BlockFruitChestTFCF(BlockFruitChestTFCF.TFCBASIC, TreesTFCF.CASSIA_CINNAMON_TREE), CT_DECORATIONS));
        fruitChests.add(register(r, "wood/fruit_tree/chest_trap/cassia_cinnamon", new BlockFruitChestTFCF(BlockFruitChestTFCF.TFCTRAP, TreesTFCF.CASSIA_CINNAMON_TREE), CT_DECORATIONS));
        fruitLoom.add(register(r, "wood/fruit_tree/loom/cassia_cinnamon", new BlockFruitLoom(TreesTFCF.CASSIA_CINNAMON_TREE), CT_WOOD));

        // Ceylon Cinnamon
        //fruitBarrel.add(register(r, "wood/fruit_tree/barrel/ceylon_cinnamon", new BlockFruitBarrelTest(), CT_DECORATIONS));
        fruitBookshelves.add(register(r, "wood/fruit_tree/bookshelf/ceylon_cinnamon", new BlockFruitBookshelves(), CT_DECORATIONS));
        fruitButton.add(register(r, "wood/fruit_tree/button/ceylon_cinnamon", new BlockFruitButton(), CT_DECORATIONS));
        fruitDoors.add(register(r, "wood/fruit_tree/door/ceylon_cinnamon", new BlockFruitDoor("ceylon_cinnamon"), CT_DECORATIONS));
        planksTFC.add(register(r, "wood/fruit_tree/planks/ceylon_cinnamon", new BlockPlanksTFC(TreesTFCF.CEYLON_CINNAMON_TREE), CT_WOOD));
        fruitPressurePlate.add(register(r, "wood/fruit_tree/pressure_plate/ceylon_cinnamon", new BlockFruitPressurePlate(), CT_DECORATIONS));
        fruitFences.add(register(r, "wood/fruit_tree/fence/ceylon_cinnamon", new BlockFruitFence(), CT_DECORATIONS));
        fruitFenceGates.add(register(r, "wood/fruit_tree/fence_gate/ceylon_cinnamon", new BlockFruitFenceGate(), CT_DECORATIONS));
        fruitLogFences.add(register(r, "wood/fruit_tree/fence_log/ceylon_cinnamon", new BlockFruitLogFence(), CT_DECORATIONS));
        fruitLogFenceGates.add(register(r, "wood/fruit_tree/fence_gate_log/ceylon_cinnamon", new BlockFruitLogFenceGate(), CT_DECORATIONS));
        fruitSupport.add(register(r, "wood/fruit_tree/support/ceylon_cinnamon", new BlockFruitSupport(), CT_DECORATIONS));
        fruitToolRack.add(register(r, "wood/fruit_tree/tool_rack/ceylon_cinnamon", new BlockFruitToolRack(), CT_DECORATIONS));
        fruitTrapdoors.add(register(r, "wood/fruit_tree/trapdoor/ceylon_cinnamon", new BlockFruitTrapDoor(), CT_DECORATIONS));
        fruitWorkbench.add(register(r, "wood/fruit_tree/workbench/ceylon_cinnamon", new BlockFruitWorkbench(), CT_DECORATIONS));
        register(r, "wood/fruit_tree/double_slab/ceylon_cinnamon", new BlockSlabTFC.Double(TreesTFCF.CEYLON_CINNAMON_TREE));
        blockSlabTFC.add(register(r, "wood/fruit_tree/slab/ceylon_cinnamon", new BlockSlabTFC.Half(TreesTFCF.CEYLON_CINNAMON_TREE), CT_DECORATIONS));
        blockStairTFC.add(register(r, "wood/fruit_tree/stairs/ceylon_cinnamon", new BlockStairsTFC(TreesTFCF.CEYLON_CINNAMON_TREE), CT_DECORATIONS));
        fruitChests.add(register(r, "wood/fruit_tree/chest/ceylon_cinnamon", new BlockFruitChestTFCF(BlockFruitChestTFCF.TFCBASIC, TreesTFCF.CEYLON_CINNAMON_TREE), CT_DECORATIONS));
        fruitChests.add(register(r, "wood/fruit_tree/chest_trap/ceylon_cinnamon", new BlockFruitChestTFCF(BlockFruitChestTFCF.TFCTRAP, TreesTFCF.CEYLON_CINNAMON_TREE), CT_DECORATIONS));
        fruitLoom.add(register(r, "wood/fruit_tree/loom/ceylon_cinnamon", new BlockFruitLoom(TreesTFCF.CEYLON_CINNAMON_TREE), CT_WOOD));

        inventoryItemBlocks.add(register(r, "wood/fruit_tree/log/cassia_cinnamon", new BlockCassiaCinnamonLog(), CT_WOOD));
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/leaves/cassia_cinnamon", new BlockCassiaCinnamonLeaves(), CT_WOOD));
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/sapling/cassia_cinnamon", new BlockCassiaCinnamonSapling(), CT_WOOD));
        
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/log/ceylon_cinnamon", new BlockCeylonCinnamonLog(), CT_WOOD));
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/leaves/ceylon_cinnamon", new BlockCeylonCinnamonLeaves(), CT_WOOD));
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/sapling/ceylon_cinnamon", new BlockCeylonCinnamonSapling(), CT_WOOD));

        // Bamboo
        for (int i = 0; i < bamboo.length; i++)
        {
            //fruitBarrel.add(register(r, "wood/barrel/" + bamboo[i], new BlockFruitBarrelTest(), CT_DECORATIONS));
            fruitBookshelves.add(register(r, "wood/bookshelf/" + bamboo[i], new BlockFruitBookshelves(), CT_DECORATIONS));
            fruitButton.add(register(r, "wood/button/" + bamboo[i], new BlockFruitButton(), CT_DECORATIONS));
            fruitDoors.add(register(r, "wood/door/" + bamboo[i], new BlockFruitDoor(bamboo[i]), CT_DECORATIONS));
            planksTFC.add(register(r, "wood/planks/" + bamboo[i], new BlockPlanksTFC(bambooTrees[i]), CT_WOOD));
            fruitPressurePlate.add(register(r, "wood/pressure_plate/" + bamboo[i], new BlockFruitPressurePlate(), CT_DECORATIONS));
            fruitFences.add(register(r, "wood/fence/" + bamboo[i], new BlockFruitFence(), CT_DECORATIONS));
            fruitFenceGates.add(register(r, "wood/fence_gate/" + bamboo[i], new BlockFruitFenceGate(), CT_DECORATIONS));
            fruitLogFences.add(register(r, "wood/fence_log/" + bamboo[i], new BlockFruitLogFence(), CT_DECORATIONS));
            fruitLogFenceGates.add(register(r, "wood/fence_gate_log/" + bamboo[i], new BlockFruitLogFenceGate(), CT_DECORATIONS));
            fruitSupport.add(register(r, "wood/support/" + bamboo[i], new BlockFruitSupport(), CT_DECORATIONS));
            fruitToolRack.add(register(r, "wood/tool_rack/" + bamboo[i], new BlockFruitToolRack(), CT_DECORATIONS));
            fruitTrapdoors.add(register(r, "wood/trapdoor/" + bamboo[i], new BlockFruitTrapDoor(), CT_DECORATIONS));
            fruitWorkbench.add(register(r, "wood/workbench/" + bamboo[i], new BlockFruitWorkbench(), CT_DECORATIONS));
            register(r, "wood/double_slab/" + bamboo[i], new BlockSlabTFC.Double(bambooTrees[i]));
            blockSlabTFC.add(register(r, "wood/slab/" + bamboo[i], new BlockSlabTFC.Half(bambooTrees[i]), CT_DECORATIONS));
            blockStairTFC.add(register(r, "wood/stairs/" + bamboo[i], new BlockStairsTFC(bambooTrees[i]), CT_DECORATIONS));

            fruitChests.add(register(r, "wood/chest/" + bamboo[i], new BlockFruitChestTFCF(BlockFruitChestTFCF.TFCBASIC, bambooTrees[i]), CT_DECORATIONS));
            fruitChests.add(register(r, "wood/chest_trap/" + bamboo[i], new BlockFruitChestTFCF(BlockFruitChestTFCF.TFCTRAP, bambooTrees[i]), CT_DECORATIONS));
            fruitLoom.add(register(r, "wood/loom/" + bamboo[i], new BlockFruitLoom(bambooTrees[i]), CT_WOOD));

            Block bambooBlock = register(r, "wood/log/" + bamboo[i], new BlockBambooLog(), CT_WOOD);
            BlockBambooLeaves leaves = new BlockBambooLeaves(bambooTrees[i]);
            Block bambooLeaves = register(r, "wood/leaves/" + bamboo[i], leaves, CT_WOOD);

            BlockBambooSapling sapling = new BlockBambooSapling(bambooTrees[i], bambooLeaves, bambooBlock);
            Block bambooSapling = register(r, "wood/sapling/" + bamboo[i], sapling, CT_WOOD);
            leaves.setBambooSapling(sapling);

            itemBambooLog.add(bambooBlock);
            itemBambooLeaves.add(bambooLeaves);
            itemBambooSapling.add(bambooSapling);
        }

        if (TFCFlorae.FirmaLifeAdded)
        {
            // Cocoa
            for (FruitTreeFL fruitTree : FruitTreeFL.values())
            {
                String name = fruitTree.getName().toLowerCase();
                //fruitBarrel.add(register(r, "wood/fruit_tree/barrel/" + name, new BlockFruitBarrelTest(), CT_DECORATIONS));
                fruitBookshelves.add(register(r, "wood/fruit_tree/bookshelf/" + name, new BlockFruitBookshelves(), CT_DECORATIONS));
                fruitButton.add(register(r, "wood/fruit_tree/button/" + name, new BlockFruitButton(), CT_DECORATIONS));
                fruitPlanks.add(register(r, "wood/fruit_tree/planks/" + name, new BlockFruitPlanks(fruitTree), CT_WOOD));
                register(r, "wood/fruit_tree/double_slab/" + name, new BlockFruitSlab.Double(fruitTree));
                fruitSlab.add(register(r, "wood/fruit_tree/slab/" + name, new BlockFruitSlab.Half(fruitTree), CT_DECORATIONS));
                fruitStairs.add(register(r, "wood/fruit_tree/stairs/" + name, new BlockFruitStairs(fruitTree), CT_DECORATIONS));
                fruitPressurePlate.add(register(r, "wood/fruit_tree/pressure_plate/" + name, new BlockFruitPressurePlate(), CT_DECORATIONS));
                fruitLogFences.add(register(r, "wood/fruit_tree/fence_log/" + name, new BlockFruitLogFence(), CT_DECORATIONS));
                fruitLogFenceGates.add(register(r, "wood/fruit_tree/fence_gate_log/" + name, new BlockFruitLogFenceGate(), CT_DECORATIONS));
                fruitSupport.add(register(r, "wood/fruit_tree/support/" +  name, new BlockFruitSupport(), CT_DECORATIONS));
                fruitToolRack.add(register(r, "wood/fruit_tree/tool_rack/" + name, new BlockFruitToolRack(), CT_DECORATIONS));
                fruitWorkbench.add(register(r, "wood/fruit_tree/workbench/" + name, new BlockFruitWorkbench(), CT_DECORATIONS));
                fruitChests.add(register(r, "wood/fruit_tree/chest/" + name, new BlockFruitChestTFCF(BlockFruitChestTFCF.TFCBASIC, fruitTree), CT_DECORATIONS));
                fruitChests.add(register(r, "wood/fruit_tree/chest_trap/" + name, new BlockFruitChestTFCF(BlockFruitChestTFCF.TFCTRAP, fruitTree), CT_DECORATIONS));
                fruitLoom.add(register(r, "wood/fruit_tree/loom/" + name, new BlockFruitLoom(fruitTree), CT_WOOD));
            }

            // Cinnamon
            //fruitBarrel.add(register(r, "wood/fruit_tree/barrel/cinnamon", new BlockFruitBarrelTest(), CT_DECORATIONS));
            fruitBookshelves.add(register(r, "wood/fruit_tree/bookshelf/cinnamon", new BlockFruitBookshelves(), CT_DECORATIONS));
            fruitButton.add(register(r, "wood/fruit_tree/button/cinnamon", new BlockFruitButton(), CT_DECORATIONS));
            planksTFC.add(register(r, "wood/fruit_tree/planks/cinnamon", new BlockPlanksTFC(PlantsFL.CINNAMON_TREE), CT_WOOD));
            register(r, "wood/fruit_tree/double_slab/cinnamon", new BlockSlabTFC.Double(PlantsFL.CINNAMON_TREE));
            blockSlabTFC.add(register(r, "wood/fruit_tree/slab/cinnamon", new BlockSlabTFC.Half(PlantsFL.CINNAMON_TREE), CT_DECORATIONS));
            blockStairTFC.add(register(r, "wood/fruit_tree/stairs/cinnamon", new BlockStairsTFC(PlantsFL.CINNAMON_TREE), CT_DECORATIONS));
            fruitPressurePlate.add(register(r, "wood/fruit_tree/pressure_plate/cinnamon", new BlockFruitPressurePlate(), CT_DECORATIONS));
            fruitLogFences.add(register(r, "wood/fruit_tree/fence_log/cinnamon", new BlockFruitLogFence(), CT_DECORATIONS));
            fruitLogFenceGates.add(register(r, "wood/fruit_tree/fence_gate_log/cinnamon", new BlockFruitLogFenceGate(), CT_DECORATIONS));
            fruitSupport.add(register(r, "wood/fruit_tree/support/cinnamon", new BlockFruitSupport(), CT_DECORATIONS));
            fruitToolRack.add(register(r, "wood/fruit_tree/tool_rack/cinnamon", new BlockFruitToolRack(), CT_DECORATIONS));
            fruitWorkbench.add(register(r, "wood/fruit_tree/workbench/cinnamon", new BlockFruitWorkbench(), CT_DECORATIONS));
            fruitChests.add(register(r, "wood/fruit_tree/chest/cinnamon", new BlockFruitChestTFCF(BlockFruitChestTFCF.TFCBASIC, PlantsFL.CINNAMON_TREE), CT_DECORATIONS));
            fruitChests.add(register(r, "wood/fruit_tree/chest_trap/cinnamon", new BlockFruitChestTFCF(BlockFruitChestTFCF.TFCTRAP, PlantsFL.CINNAMON_TREE), CT_DECORATIONS));
            fruitLoom.add(register(r, "wood/fruit_tree/loom/cinnamon", new BlockFruitLoom(PlantsFL.CINNAMON_TREE), CT_WOOD));
        }

        //multiBlock.add(register(r, "multiblock/campfire", new BlockCampfire(Material.ROCK), CT_MISC));
        //multiBlock.add(register(r, "multiblock/dummyHalf", new BlockDummyHalf(), CT_MISC));

        for(Tree wood : TFCRegistries.TREES.getValuesCollection())
        {
            fenceGatesLog.add(register(r, "wood/fence_gate_log/" + wood.getRegistryName().getPath(), new BlockFenceGateLog(wood), CT_DECORATIONS));

            if (wood == TFCRegistries.TREES.getValue(TreesTFCF.JOSHUA_TREE))
            {
                Builder<BlockJoshuaTreeLog> logJoshuaTree = ImmutableList.builder();
                Builder<BlockJoshuaTreeSapling> saplingJoshuaTree = ImmutableList.builder();

                logJoshuaTree.add(register(r, "wood/log/" + wood.getRegistryName().getPath(), new BlockJoshuaTreeLog(wood), CT_WOOD));
                saplingJoshuaTree.add(register(r, "wood/sapling/" + wood.getRegistryName().getPath(), new BlockJoshuaTreeSapling(wood), CT_WOOD));

                allJoshuaTreeLogBlocks = logJoshuaTree.build();
                for (BlockJoshuaTreeLog blockJoshuaTreeLog : allJoshuaTreeLogBlocks)
                {
                    normalItemBlocks.add(new ItemBlockTFC(blockJoshuaTreeLog));
                }
                allJoshuaTreeSaplingBlocks = saplingJoshuaTree.build();
                for (BlockJoshuaTreeSapling blockJoshuaTreeSapling : allJoshuaTreeSaplingBlocks)
                {
                    normalItemBlocks.add(new ItemBlockTFC(blockJoshuaTreeSapling));
                }
            }
        }

        {
            fluids.add(
                register(r, "fluid/distilled_water", new BlockFluidTFC(FluidsTFCF.DISTILLED_WATER.get(), Material.WATER, false)),
                register(r, "fluid/waste", new BlockFluidTFC(FluidsTFCF.WASTE.get(), Material.WATER, false)),
                register(r, "fluid/base_potash_liquor", new BlockFluidTFC(FluidsTFCF.BASE_POTASH_LIQUOR.get(), Material.WATER, false))
            );
            for (FluidWrapper wrapper : FluidsTFCF.getAllFermentedAlcoholsFluids())
            {
                fluids.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER, false)));
            }
            for (FluidWrapper wrapper : FluidsTFCF.getAllAlcoholsFluids())
            {
                fluids.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER, false)));
            }
            for (FluidWrapper wrapper : FluidsTFCF.getAllBeerFluids())
            {
                fluids.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER, false)));
            }
            for (FluidWrapper wrapper : FluidsTFCF.getAllTeaFluids())
            {
                fluids.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER, false)));
            }
            for (FluidWrapper wrapper : FluidsTFCF.getAllCoffeeFluids())
            {
                fluids.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER, false)));
            }
            for (FluidWrapper wrapper : FluidsTFCF.getAllJuiceBerryFluids())
            {
                fluids.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER, false)));
            }
            for (FluidWrapper wrapper : FluidsTFCF.getAllJuiceFruitFluids())
            {
                fluids.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER, false)));
            }
            for (FluidWrapper wrapper : FluidsTFCF.getAllMiscFluids())
            {
                fluids.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER, false)));
            }
            allFluidBlocks = fluids.build();
        }

        allInventoryItemBlocks = inventoryItemBlocks.build();
        allInventoryItemBlocks.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allBambooLog = itemBambooLog.build();
        allBambooLog.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allBambooLeaves = itemBambooLeaves.build();
        allBambooLeaves.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allNormalTreeLeaves = itemNormalTreeLeaves.build();
        allNormalTreeLeaves.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allNormalTreeLog = normalTreeLog.build();

        allBambooSapling = itemBambooSapling.build();
        allBambooSapling.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFoodItemBlocks = foodItemBlocks.build();
        allFoodItemBlocks.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockRot(x));
        });

        allFenceGateLogBlocks = fenceGatesLog.build();
        allFenceGateLogBlocks.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitLeaves = fruitLeaves.build();
        allFruitLeaves.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitSapling = fruitSapling.build();
        allFruitSapling.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        /*allFruitBarrel = fruitBarrel.build();
        allFruitBarrel.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });*/

        allFruitBookshelves = fruitBookshelves.build();
        allFruitBookshelves.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitButton = fruitButton.build();
        allFruitButton.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitDoors = fruitDoors.build();

        allFruitPlanks = fruitPlanks.build();
        allFruitPlanks.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allPlanksTFC = planksTFC.build();
        allPlanksTFC.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allSlabBlocksTFC = blockSlabTFC.build();
        allStairBlocksTFC = blockStairTFC.build();
        allStairBlocksTFC.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));

        allFruitSlabBlocks = fruitSlab.build();
        allFruitStairBlocks = fruitStairs.build();
        allFruitStairBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        
        allFruitPressurePlate = fruitPressurePlate.build();
        allFruitPressurePlate.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitFences = fruitFences.build();
        allFruitFences.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitFenceGates = fruitFenceGates.build();
        allFruitFenceGates.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitLogFences = fruitLogFences.build();
        allFruitLogFences.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitLogFenceGates = fruitLogFenceGates.build();
        allFruitLogFenceGates.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitSupport = fruitSupport.build();
        allFruitSupport.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitToolRack = fruitToolRack.build();
        allFruitToolRack.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitTrapDoors = fruitTrapdoors.build();
        allFruitTrapDoors.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitWorkbench = fruitWorkbench.build();
        allFruitWorkbench.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitChestBlocks = fruitChests.build();
        allFruitChestBlocks.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitLoomBlocks = fruitLoom.build();
        allFruitLoomBlocks.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        /*allMultiBlocks = multiBlock.build();
        allMultiBlocks.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });*/

        allNormalItemBlocks = normalItemBlocks.build();

        if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
        {
            register(TELargeEarthenwareVessel.class, "large_earthenware_vessel");
        }
        if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
        {
            register(TELargeKaoliniteVessel.class, "large_kaolinite_vessel");
        }
        if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
        {
            register(TELargeStonewareVessel.class, "large_stoneware_vessel");
        }
        register(TEFruitChest.class, "fruit_chest");
        register(TEFruitLoom.class, "fruit_loom");
        register(TEUrn.class, "urn");
        register(TECrate.class, "crate");
    }

    public static boolean isRawStone(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        Rock.Type type = ((BlockRockVariant) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.MOSSY_RAW || 
        type == Rock.Type.RAW || 
        type == Rock.Type.COBBLE || 
        type == Rock.Type.SMOOTH;
    }

    public static boolean isClay(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.MUD || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_CLAY || 
        rockTFCF == RockTFCF.CLAY_LOAM || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM || 
        rockTFCF == RockTFCF.SILTY_CLAY || 
        rockTFCF == RockTFCF.COARSE_SANDY_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_CLAY || 
        rockTFCF == RockTFCF.COARSE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_CLAY_PODZOL || 
        rockTFCF == RockTFCF.CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.CLAY_HUMUS || 
        rockTFCF == RockTFCF.CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS;
    }

    public static boolean isClayGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        //Rock.Type type = ((BlockRockVariant) current.getBlock()).getType();
        return
        //type == Rock.Type.CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.CLAY_HUMUS_GRASS;
    }

    public static boolean isClayDryGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_HUMUS_GRASS;
    }

    public static boolean isKaoliniteClayGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS_GRASS;
    }

    public static boolean isKaoliniteClayDryGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_HUMUS_GRASS;
    }

    public static boolean isKaoliniteClayPodzol(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_PODZOL;
    }

    public static boolean isKaoliniteClayDirt(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS;
    }

    public static boolean isStonewareClayGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS_GRASS;
    }

    public static boolean isStonewareClayDryGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_HUMUS_GRASS;
    }

    public static boolean isStonewareClayPodzol(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_PODZOL;
    }

    public static boolean isStonewareClayDirt(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.STONEWARE_CLAY || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS;
    }

    public static boolean isEarthenwareClayGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS_GRASS;
    }

    public static boolean isEarthenwareClayDryGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS;
    }

    public static boolean isEarthenwareClayPodzol(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_PODZOL;
    }

    public static boolean isEarthenwareClayDirt(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS;
    }

    public static boolean isClayPodzol(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.CLAY_PODZOL || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_CLAY_PODZOL || 
        rockTFCF == RockTFCF.CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SILTY_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM_PODZOL;
    }

    public static boolean isClayDirt(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        //Rock.Type type = ((BlockRockVariant) current.getBlock()).getType();
        return
        //type == Rock.Type.CLAY || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_CLAY || 
        rockTFCF == RockTFCF.CLAY_LOAM || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM || 
        rockTFCF == RockTFCF.SILTY_CLAY || 
        rockTFCF == RockTFCF.CLAY_HUMUS;
    }

    public static boolean isDirt(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.MUD || 
        rockTFCF == RockTFCF.BOG_IRON || 
        rockTFCF == RockTFCF.COARSE_DIRT || 
        rockTFCF == RockTFCF.LOAMY_SAND || 
        rockTFCF == RockTFCF.SANDY_LOAM || 
        rockTFCF == RockTFCF.LOAM || 
        rockTFCF == RockTFCF.SILT_LOAM || 
        rockTFCF == RockTFCF.SILT || 
        rockTFCF == RockTFCF.COARSE_LOAMY_SAND || 
        rockTFCF == RockTFCF.COARSE_SANDY_LOAM || 
        rockTFCF == RockTFCF.COARSE_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILT_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILT || 
        rockTFCF == RockTFCF.COARSE_HUMUS;
    }

    public static boolean isSand(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariant)) return false;
        Rock.Type type = ((BlockRockVariant) current.getBlock()).getType();
        return type == DIRT;
    }
    
    public static boolean isSoil(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.COARSE_DIRT || 
        rockTFCF == RockTFCF.MUD || 
        rockTFCF == RockTFCF.BOG_IRON || 
        rockTFCF == RockTFCF.BOG_IRON_GRASS || 
        rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS || 
        rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS || 
        rockTFCF == RockTFCF.BOG_IRON_PODZOL || 
        rockTFCF == RockTFCF.LOAMY_SAND || 
        rockTFCF == RockTFCF.SANDY_LOAM || 
        rockTFCF == RockTFCF.LOAM || 
        rockTFCF == RockTFCF.SILT_LOAM || 
        rockTFCF == RockTFCF.SILT || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_CLAY || 
        rockTFCF == RockTFCF.CLAY_LOAM || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM || 
        rockTFCF == RockTFCF.SILTY_CLAY || 
        rockTFCF == RockTFCF.COARSE_LOAMY_SAND || 
        rockTFCF == RockTFCF.COARSE_SANDY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_CLAY || 
        rockTFCF == RockTFCF.COARSE_LOAM || 
        rockTFCF == RockTFCF.COARSE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILT_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILT || 
        rockTFCF == RockTFCF.PODZOL || 
        rockTFCF == RockTFCF.LOAMY_SAND_GRASS || 
        rockTFCF == RockTFCF.LOAMY_SAND_PODZOL || 
        rockTFCF == RockTFCF.SANDY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_CLAY_PODZOL || 
        rockTFCF == RockTFCF.LOAM_GRASS || 
        rockTFCF == RockTFCF.LOAM_PODZOL || 
        rockTFCF == RockTFCF.CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SILT_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILT_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SILT_GRASS || 
        rockTFCF == RockTFCF.SILT_PODZOL || 
        rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SILT_GRASS || 
        rockTFCF == RockTFCF.HUMUS || 
        rockTFCF == RockTFCF.COARSE_HUMUS || 
        rockTFCF == RockTFCF.HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.CLAY_HUMUS || 
        rockTFCF == RockTFCF.CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILT_GRASS || 
        rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS;
    }

    public static boolean isGrowableSoil(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.COARSE_DIRT || 
        rockTFCF == RockTFCF.MUD || 
        rockTFCF == RockTFCF.BOG_IRON || 
        rockTFCF == RockTFCF.BOG_IRON_GRASS || 
        rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS || 
        rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS || 
        rockTFCF == RockTFCF.BOG_IRON_PODZOL || 
        rockTFCF == RockTFCF.LOAMY_SAND || 
        rockTFCF == RockTFCF.SANDY_LOAM || 
        rockTFCF == RockTFCF.LOAM || 
        rockTFCF == RockTFCF.SILT_LOAM || 
        rockTFCF == RockTFCF.SILT || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_CLAY || 
        rockTFCF == RockTFCF.CLAY_LOAM || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM || 
        rockTFCF == RockTFCF.SILTY_CLAY || 
        rockTFCF == RockTFCF.COARSE_LOAMY_SAND || 
        rockTFCF == RockTFCF.COARSE_SANDY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_CLAY || 
        rockTFCF == RockTFCF.COARSE_LOAM || 
        rockTFCF == RockTFCF.COARSE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILT_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILT || 
        rockTFCF == RockTFCF.PODZOL || 
        rockTFCF == RockTFCF.LOAMY_SAND_GRASS || 
        rockTFCF == RockTFCF.LOAMY_SAND_PODZOL || 
        rockTFCF == RockTFCF.SANDY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_CLAY_PODZOL || 
        rockTFCF == RockTFCF.LOAM_GRASS || 
        rockTFCF == RockTFCF.LOAM_PODZOL || 
        rockTFCF == RockTFCF.CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SILT_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILT_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SILT_GRASS || 
        rockTFCF == RockTFCF.SILT_PODZOL || 
        rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SILT_GRASS || 
        rockTFCF == RockTFCF.HUMUS || 
        rockTFCF == RockTFCF.COARSE_HUMUS || 
        rockTFCF == RockTFCF.HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.CLAY_HUMUS || 
        rockTFCF == RockTFCF.CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILT_GRASS || 
        rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS;
    }

    public static boolean isSoilOrGravel(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.COARSE_DIRT || 
        rockTFCF == RockTFCF.MUD || 
        rockTFCF == RockTFCF.BOG_IRON || 
        rockTFCF == RockTFCF.BOG_IRON_GRASS || 
        rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS || 
        rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS || 
        rockTFCF == RockTFCF.BOG_IRON_PODZOL || 
        rockTFCF == RockTFCF.LOAMY_SAND || 
        rockTFCF == RockTFCF.SANDY_LOAM || 
        rockTFCF == RockTFCF.LOAM || 
        rockTFCF == RockTFCF.SILT_LOAM || 
        rockTFCF == RockTFCF.SILT || 
        rockTFCF == RockTFCF.COARSE_LOAMY_SAND || 
        rockTFCF == RockTFCF.COARSE_SANDY_LOAM || 
        rockTFCF == RockTFCF.COARSE_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILT_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILT || 
        rockTFCF == RockTFCF.PODZOL || 
        rockTFCF == RockTFCF.LOAMY_SAND_GRASS || 
        rockTFCF == RockTFCF.LOAMY_SAND_PODZOL || 
        rockTFCF == RockTFCF.SANDY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.LOAM_GRASS || 
        rockTFCF == RockTFCF.LOAM_PODZOL || 
        rockTFCF == RockTFCF.SILT_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILT_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SILT_GRASS || 
        rockTFCF == RockTFCF.SILT_PODZOL || 
        rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SILT_GRASS || 
        rockTFCF == RockTFCF.HUMUS || 
        rockTFCF == RockTFCF.COARSE_HUMUS || 
        rockTFCF == RockTFCF.HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_GRASS || 
        rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILT_GRASS || 
        rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS;
    }

    public static boolean isGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return rockTFCF.isGrass;
    }

    public static boolean isPodzol(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.PODZOL || 
        rockTFCF == RockTFCF.BOG_IRON_PODZOL || 
        rockTFCF == RockTFCF.LOAMY_SAND_PODZOL || 
        rockTFCF == RockTFCF.SANDY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_CLAY_PODZOL || 
        rockTFCF == RockTFCF.LOAM_PODZOL || 
        rockTFCF == RockTFCF.CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SILT_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SILT_PODZOL || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_PODZOL;
    }

    public static boolean isSparseGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.SPARSE_GRASS || 
        rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILT_GRASS || 
        rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS;
    }

    public static boolean isDryGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS || 
        rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SILT_GRASS || 
        rockTFCF == RockTFCF.DRY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_HUMUS_GRASS;
    }

    public static boolean isGround(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.COARSE_DIRT || 
        rockTFCF == RockTFCF.MUD || 
        rockTFCF == RockTFCF.BOG_IRON || 
        rockTFCF == RockTFCF.BOG_IRON_GRASS || 
        rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS || 
        rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS || 
        rockTFCF == RockTFCF.BOG_IRON_PODZOL || 
        rockTFCF == RockTFCF.LOAMY_SAND || 
        rockTFCF == RockTFCF.SANDY_LOAM || 
        rockTFCF == RockTFCF.LOAM || 
        rockTFCF == RockTFCF.SILT_LOAM || 
        rockTFCF == RockTFCF.SILT || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_CLAY || 
        rockTFCF == RockTFCF.CLAY_LOAM || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM || 
        rockTFCF == RockTFCF.SILTY_CLAY || 
        rockTFCF == RockTFCF.COARSE_LOAMY_SAND || 
        rockTFCF == RockTFCF.COARSE_SANDY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_CLAY || 
        rockTFCF == RockTFCF.COARSE_LOAM || 
        rockTFCF == RockTFCF.COARSE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILT_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILT || 
        rockTFCF == RockTFCF.PODZOL || 
        rockTFCF == RockTFCF.LOAMY_SAND_GRASS || 
        rockTFCF == RockTFCF.LOAMY_SAND_PODZOL || 
        rockTFCF == RockTFCF.SANDY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_CLAY_PODZOL || 
        rockTFCF == RockTFCF.LOAM_GRASS || 
        rockTFCF == RockTFCF.LOAM_PODZOL || 
        rockTFCF == RockTFCF.CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SILT_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILT_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SILT_GRASS || 
        rockTFCF == RockTFCF.SILT_PODZOL || 
        rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SILT_GRASS || 
        rockTFCF == RockTFCF.HUMUS || 
        rockTFCF == RockTFCF.COARSE_HUMUS || 
        rockTFCF == RockTFCF.HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.CLAY_HUMUS || 
        rockTFCF == RockTFCF.CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY_LOAM || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILT_GRASS || 
        rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY_LOAM || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_PODZOL || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_PODZOL || 
        rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_HUMUS_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_GRASS || 
        rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS || 
        rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS;
    }

    public static Block registerWoodBlock(IForgeRegistry<Block> r, String name, Block block)
    {
        block.setRegistryName(MODID, name);
        block.setTranslationKey(MODID + "." + name.replace('/', '.'));
        block.setCreativeTab(CreativeTabsTFC.CT_DECORATIONS);
        r.register(block);
        return block;
    }

    private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block, CreativeTabs ct)
    {
        block.setCreativeTab(ct);
        return register(r, name, block);
    }

    private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block)
    {
        block.setRegistryName(MODID, name);
        block.setTranslationKey(MODID + "." + name.replace('/', '.'));
        r.register(block);
        return block;
    }

    private static <T extends TileEntity> void register(Class<T> te, String name)
    {
        TileEntity.register(MODID + ":" + name, te);
    }
}