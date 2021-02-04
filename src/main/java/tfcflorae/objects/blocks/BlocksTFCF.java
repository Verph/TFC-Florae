/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package tfcflorae.objects.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
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
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.*;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.blocks.BlockFireBrick;
import net.dries007.tfc.objects.blocks.BlockFluidTFC;
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
import net.dries007.tfc.util.agriculture.BerryBush;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.agriculture.FruitTree;
import net.dries007.tfc.util.Helpers;

import tfcflorae.api.registries.TFCFRegistries;
import tfcflorae.api.types.PlantTFCF;
import tfcflorae.objects.blocks.*;
import tfcflorae.objects.blocks.fruitwood.*;
import tfcflorae.objects.blocks.fruitwood.cinnamon.*;
import tfcflorae.objects.blocks.wood.*;
import tfcflorae.objects.blocks.wood.bamboo.*;
import tfcflorae.objects.blocks.blocktype.*;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.objects.blocks.plants.BlockPlantTFCF;
import tfcflorae.objects.fluids.FluidsTFCF;
import tfcflorae.objects.items.ItemBlockRot;
import tfcflorae.objects.items.ItemFoodTFCF;
import tfcflorae.objects.te.TELargeKaoliniteVessel;
import tfcflorae.objects.te.TEStemCrop;
import tfcflorae.types.BlockTypesTFCF;
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
    @GameRegistry.ObjectHolder("ceramics/kaolinite_fired/large_vessel")
    public static final BlockLargeVessel FIRED_KAOLINITE_LARGE_VESSEL = Helpers.getNull();

    @GameRegistry.ObjectHolder("surface/bone")
    public static final BlockSurfaceBones BONES = Helpers.getNull();
    @GameRegistry.ObjectHolder("surface/flint")
    public static final BlockSurfaceFlint FLINT = Helpers.getNull();
    @GameRegistry.ObjectHolder("surface/seashell")
    public static final BlockSurfaceSeashells SEASHELLS = Helpers.getNull();
    @GameRegistry.ObjectHolder("plants/pumpkin_fruit")
    public static final BlockStemFruit PUMPKIN_FRUIT = Helpers.getNull();
    @GameRegistry.ObjectHolder("plants/melon_fruit")
    public static final BlockStemFruit MELON_FRUIT = Helpers.getNull();
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
    @GameRegistry.ObjectHolder("wood/leaves/red_bamboo")
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

    // All these are for use in model registration. Do not use for block lookups.
    // Use the static get methods in the classes instead.
    private static ImmutableList<ItemBlock> allNormalItemBlocks;
    private static ImmutableList<Block> allInventoryItemBlocks;
    private static ImmutableList<Block> allFoodItemBlocks = Helpers.getNull();
    private static ImmutableList<BlockFenceGateLog> allFenceGateLogBlocks = Helpers.getNull();
    private static ImmutableList<BlockFruitTreeLeaves> allFruitLeaves = Helpers.getNull();
    private static ImmutableList<BlockFruitTreeSapling> allFruitSapling = Helpers.getNull();
    private static ImmutableList<BlockFruitBarrel> allFruitBarrel = Helpers.getNull();
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
    private static ImmutableList<BlockFluidBase> allFluidBlocks = Helpers.getNull();
    private static ImmutableList<BlockCropTFC> allCropBlocksTFC = Helpers.getNull();
    private static ImmutableList<BlockCropDead> allDeadCrops = Helpers.getNull();
    private static ImmutableList<BlockStemCrop> allCropBlocks = Helpers.getNull();
    private static ImmutableList<BlockBerryBush> allBerryBushBlocks;
    private static ImmutableList<BlockRockVariantTFCF> allBlockRockVariantsTFCF = Helpers.getNull();
    private static ImmutableList<BlockWallTFCF> allWallBlocks = Helpers.getNull();
    private static ImmutableList<BlockStairsTFCF> allStairBlocks = Helpers.getNull();
    private static ImmutableList<BlockSlabTFCF.Half> allSlabBlocks = Helpers.getNull();
    private static ImmutableList<BlockSlabTFC.Half> allSlabBlocksTFC = Helpers.getNull();
    private static ImmutableList<BlockStairsTFC> allStairBlocksTFC = Helpers.getNull();
    private static ImmutableList<BlockPlanksTFC> allPlanksTFC = Helpers.getNull();
    private static ImmutableList<BlockPlantTFCF> allPlantBlocks = Helpers.getNull();
    private static ImmutableList<BlockSurfaceRock> allSurfaceRocks = Helpers.getNull();
    private static ImmutableList<BlockSurfaceSeashells> allSurfaceSeashells = Helpers.getNull();
    private static ImmutableList<BlockSurfaceFlint> allSurfaceFlint = Helpers.getNull();
    private static ImmutableList<BlockSurfaceBones> allSurfaceBones = Helpers.getNull();
    private static ImmutableList<Block> allBambooLog = Helpers.getNull();
    private static ImmutableList<Block> allBambooLeaves = Helpers.getNull();
    private static ImmutableList<Block> allBambooSapling = Helpers.getNull();
    private static ImmutableList<BlockSurfaceOreDeposit> allSurfaceOreBlocks = Helpers.getNull();

    public static String[] bamboo = {"arrow_bamboo", "black_bamboo", "blue_bamboo", "dragon_bamboo", "golden_bamboo", "narrow_leaf_bamboo", "red_bamboo", "temple_bamboo", "thorny_bamboo", "timber_bamboo", "tinwa_bamboo", "weavers_bamboo"};
    private static Tree[] bambooTrees = {TreesTFCF.ARROW_BAMBOO, TreesTFCF.BLACK_BAMBOO, TreesTFCF.BLUE_BAMBOO, TreesTFCF.DRAGON_BAMBOO, TreesTFCF.GOLDEN_BAMBOO, TreesTFCF.NARROW_LEAF_BAMBOO, TreesTFCF.RED_BAMBOO, TreesTFCF.TEMPLE_BAMBOO, TreesTFCF.THORNY_BAMBOO, TreesTFCF.TIMBER_BAMBOO, TreesTFCF.TINWA_BAMBOO, TreesTFCF.WEAVERS_BAMBOO};

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

    public static ImmutableList<BlockFruitBarrel> getAllFruitBarrel()
    {
        return allFruitBarrel;
    }

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

    public static ImmutableList<BlockCropTFC> getAllCropBlocksTFC()
    {
        return allCropBlocksTFC;
    }

    public static ImmutableList<BlockCropDead> getAllDeadCrops()
    {
        return allDeadCrops;
    }

    public static ImmutableList<BlockStemCrop> getAllCropBlocks()
    {
        return allCropBlocks;
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

    public static ImmutableList<BlockPlantTFCF> getAllPlantBlocks()
    {
        return allPlantBlocks;
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

    public static ImmutableList<BlockSurfaceOreDeposit> getAllSurfaceOreBlocks()
    {
        return allSurfaceOreBlocks;
    }

    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        // This is called here because it needs to wait until Metal registry has fired
        FluidsTFCF.registerFluids();
        IForgeRegistry<Block> r = event.getRegistry();
        
        ImmutableList.Builder<ItemBlock> normalItemBlocks = ImmutableList.builder();
        ImmutableList.Builder<Block> inventoryItemBlocks = ImmutableList.builder();
        ImmutableList.Builder<Block> itemBambooLog = ImmutableList.builder();
        ImmutableList.Builder<Block> itemBambooLeaves = ImmutableList.builder();
        ImmutableList.Builder<Block> itemBambooSapling = ImmutableList.builder();
        ImmutableList.Builder<Block> foodItemBlocks = ImmutableList.builder();
        ImmutableList.Builder<BlockFenceGateLog> fenceGatesLog = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitTreeLeaves> fruitLeaves = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitTreeSapling> fruitSapling = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitBarrel> fruitBarrel = ImmutableList.builder();
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
        ImmutableList.Builder<BlockCropTFC> cropBlocksTFC = ImmutableList.builder();
        ImmutableList.Builder<BlockCropDead> deadCrops = ImmutableList.builder();
        ImmutableList.Builder<BlockStemCrop> cropBlocks = ImmutableList.builder();
        ImmutableList.Builder<BlockBerryBush> cropBerryBushBlocks = ImmutableList.builder();
        ImmutableList.Builder<BlockRockVariantTFCF> blockRockVariantsTFCF = ImmutableList.builder();
        ImmutableList.Builder<BlockWallTFCF> blockWallTFCF = ImmutableList.builder();
        ImmutableList.Builder<BlockStairsTFCF> blockStairsTFC = new Builder<>();
        ImmutableList.Builder<BlockSlabTFCF.Half> blockSlabTFCF = new Builder<>();
        ImmutableList.Builder<BlockPlantTFCF> plants = ImmutableList.builder();
        ImmutableList.Builder<BlockSurfaceRock> surfaceRock = ImmutableList.builder();
        ImmutableList.Builder<BlockSurfaceSeashells> surfaceSeashell = ImmutableList.builder();
        ImmutableList.Builder<BlockSurfaceFlint> surfaceFlint = ImmutableList.builder();
        ImmutableList.Builder<BlockSurfaceBones> surfaceBone = ImmutableList.builder();
        ImmutableList.Builder<BlockSlabTFC.Half> blockSlabTFC = new Builder<>();
        ImmutableList.Builder<BlockStairsTFC> blockStairTFC = new Builder<>();
        ImmutableList.Builder<BlockPlanksTFC> planksTFC = ImmutableList.builder();
        ImmutableList.Builder<BlockSurfaceOreDeposit> surfaceOreBlocks = ImmutableList.builder();

        normalItemBlocks.add(new ItemBlockTFC(register(r, "ceramics/kaolinite/kaolinite_clay_block", new BlockKaoliniteClay(), CT_ROCK_BLOCKS)));
        normalItemBlocks.add(new ItemBlockTFC(register(r, "ceramics/kaolinite/kaolinite_bricks", new BlockFireBrick(), CT_DECORATIONS)));
        normalItemBlocks.add(new ItemBlockLargeVessel(register(r, "ceramics/kaolinite/fired/large_vessel", new BlockLargeVessel(), CT_POTTERY)));

        foodItemBlocks.add(register(r, "plants/pumpkin_fruit", new BlockStemFruit(), CT_FOOD));
        foodItemBlocks.add(register(r, "plants/melon_fruit", new BlockStemFruit(), CT_FOOD));

        {
            Builder<BlockCropTFC> b = ImmutableList.builder();

            for (CropTFCF crop : CropTFCF.values())
            {
                cropBlocksTFC.add(register(r, "crop/" + crop.name().toLowerCase(), crop.createGrowingBlock()));
            }
        }

        {
            Builder<BlockCropDead> b = ImmutableList.builder();

            for (CropTFCF crop : CropTFCF.values())
            {
                deadCrops.add(register(r, "dead_crop/" + crop.name().toLowerCase(), crop.createDeadBlock()));
            }
        }

        for (StemCrop crop : StemCrop.values())
        {
            deadCrops.add(register(r, "dead_crop/" + crop.name().toLowerCase(), new BlockCropDead(crop)));
            cropBlocks.add(register(r, "crop/" + crop.name().toLowerCase(), BlockStemCrop.create(crop)));
        }

        Builder<BlockBerryBush> fBerry = ImmutableList.builder();

        for (BerryBushTFCF bush : BerryBushTFCF.values())
        {
            fBerry.add(register(r, "berry_bush/" + bush.name().toLowerCase(), new BlockBerryBush(bush), CT_FOOD));
        }

        allBerryBushBlocks = fBerry.build();
        allBerryBushBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        allCropBlocksTFC = cropBlocksTFC.build();
        allDeadCrops = deadCrops.build();
        allCropBlocks = cropBlocks.build();

        register(TEStemCrop.class, "stem_crop");

        for (RockTFCF rockTFCF : RockTFCF.values())
        {
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
            {
                {
                    blockRockVariantsTFCF.add(register(r, rockTFCF.name().toLowerCase() + "/" + rock.getRegistryName().getPath(), BlockRockVariantTFCF.create(rock, rockTFCF), CT_ROCK_BLOCKS));
                }
            }
        }
        allBlockRockVariantsTFCF = blockRockVariantsTFCF.build();
        allBlockRockVariantsTFCF.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        {
            for (Ore ore : TFCRegistries.ORES.getValuesCollection())
                for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                surfaceOreBlocks.add(register(r, ("surface/ore/" + ore.getRegistryName().getPath() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockSurfaceOreDeposit(ore, rock), CT_ROCK_BLOCKS));

            allSurfaceOreBlocks = surfaceOreBlocks.build();
            allSurfaceOreBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        }

        {
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
            {
                surfaceRock.add(register(r, "surface/rock/" + rock.getRegistryName().getPath().toLowerCase(), new BlockSurfaceRock(rock), CT_ROCK_BLOCKS));
            }
            allSurfaceRocks = surfaceRock.build();
        }

        surfaceSeashell.add(register(r, "surface/seashell", new BlockSurfaceSeashells(), CT_FLORA));
        allSurfaceSeashells = surfaceSeashell.build();

		surfaceFlint.add(register(r, "surface/flint", new BlockSurfaceFlint(), CT_FLORA));
        allSurfaceFlint = surfaceFlint.build();

		surfaceBone.add(register(r, "surface/bone", new BlockSurfaceBones(), CT_FLORA));
        allSurfaceBones = surfaceBone.build();

        // Walls
        for (RockTFCF rockTFCF : new RockTFCF[] {RockTFCF.MUD_BRICKS})
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                blockWallTFCF.add(register(r, ("wall/" + rockTFCF.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockWallTFCF(BlockRockVariantTFCF.get(rock, rockTFCF)), CT_DECORATIONS));

        // Stairs
        for (RockTFCF rockTFCF : new RockTFCF[] {RockTFCF.MUD_BRICKS})
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                blockStairsTFC.add(register(r, "stairs/" + (rockTFCF.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockStairsTFCF(rock, rockTFCF), CT_DECORATIONS));

        // Full slabs are the same as full blocks, they are not saved to a list, they are kept track of by the halfslab version.
        for (RockTFCF rockTFCF : new RockTFCF[] {RockTFCF.MUD_BRICKS})
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                register(r, "double_slab/" + (rockTFCF.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockSlabTFCF.Double(rock, rockTFCF));

        // Slabs
        for (RockTFCF rockTFCF : new RockTFCF[] {RockTFCF.MUD_BRICKS})
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                blockSlabTFCF.add(register(r, "slab/" + (rockTFCF.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockSlabTFCF.Half(rock, rockTFCF), CT_DECORATIONS));

        allWallBlocks = blockWallTFCF.build();
        allStairBlocks = blockStairsTFC.build();
        allSlabBlocks = blockSlabTFCF.build();
        allWallBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        allStairBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));

        for (FruitTreeTFCF fruitTree : FruitTreeTFCF.values())
        {
            String name = fruitTree.getName().toLowerCase();
            register(r, "wood/fruit_tree/branch/" + name, new BlockFruitTreeBranch(fruitTree));
            fruitLeaves.add(register(r, "wood/fruit_tree/leaves/" + name, new BlockFruitTreeLeaves(fruitTree), CT_WOOD));
            fruitSapling.add(register(r, "wood/fruit_tree/sapling/" + name, new BlockFruitTreeSapling(fruitTree), CT_WOOD));
            register(r, "wood/fruit_tree/trunk/" + name, new BlockFruitTreeTrunk(fruitTree));
            fruitBarrel.add(register(r, "wood/fruit_tree/barrel/" + name, new BlockFruitBarrel(), CT_DECORATIONS));
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
        }

        for (IFruitTree fruitTree : FruitTree.values())
        {
            String name = fruitTree.getName().toLowerCase();
            fruitBarrel.add(register(r, "wood/fruit_tree/barrel/" + name, new BlockFruitBarrel(), CT_DECORATIONS));
            fruitBookshelves.add(register(r, "wood/fruit_tree/bookshelf/" + name, new BlockFruitBookshelves(), CT_DECORATIONS));
            fruitDoors.add(register(r, "wood/fruit_tree/door/" + name, new BlockFruitDoor(name), CT_DECORATIONS));
            fruitButton.add(register(r, "wood/fruit_tree/button/" + name, new BlockFruitButton(), CT_DECORATIONS));
            fruitPlanks.add(register(r, "wood/fruit_tree/planks/" + name, new BlockFruitPlanks(fruitTree), CT_WOOD));
            register(r, "wood/fruit_tree/double_slab/" + name, new BlockFruitSlab.Double(fruitTree));
            fruitSlab.add(register(r, "wood/fruit_tree/slab/" + name, new BlockFruitSlab.Half(fruitTree), CT_DECORATIONS));
            fruitStairs.add(register(r, "wood/fruit_tree/stairs/" + name, new BlockFruitStairs(fruitTree), CT_DECORATIONS));
            fruitPressurePlate.add(register(r, "wood/fruit_tree/pressure_plate/" + name, new BlockFruitPressurePlate(), CT_DECORATIONS));
            fruitFences.add(register(r, "wood/fruit_tree/fence/" + name, new BlockFruitFence(), CT_DECORATIONS));
            fruitFenceGates.add(register(r, "wood/fruit_tree/fence_gate/" + name, new BlockFruitFenceGate(), CT_DECORATIONS));
            fruitLogFences.add(register(r, "wood/fruit_tree/fence_log/" + name, new BlockFruitLogFence(), CT_DECORATIONS));
            fruitLogFenceGates.add(register(r, "wood/fruit_tree/fence_gate_log/" + name, new BlockFruitLogFenceGate(), CT_DECORATIONS));
            fruitSupport.add(register(r, "wood/fruit_tree/support/" + name, new BlockFruitSupport(), CT_DECORATIONS));
            fruitToolRack.add(register(r, "wood/fruit_tree/tool_rack/" + name, new BlockFruitToolRack(), CT_DECORATIONS));
            fruitTrapdoors.add(register(r, "wood/fruit_tree/trapdoor/" + name, new BlockFruitTrapDoor(), CT_DECORATIONS));
            fruitWorkbench.add(register(r, "wood/fruit_tree/workbench/" + name, new BlockFruitWorkbench(), CT_DECORATIONS));
        }

        // Cassia Cinnamon
        fruitBarrel.add(register(r, "wood/fruit_tree/barrel/cassia_cinnamon", new BlockFruitBarrel(), CT_DECORATIONS));
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

        // Ceylon Cinnamon
        fruitBarrel.add(register(r, "wood/fruit_tree/barrel/ceylon_cinnamon", new BlockFruitBarrel(), CT_DECORATIONS));
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

        inventoryItemBlocks.add(register(r, "wood/fruit_tree/log/cassia_cinnamon", new BlockCassiaCinnamonLog(), CT_WOOD));
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/leaves/cassia_cinnamon", new BlockCassiaCinnamonLeaves(), CT_WOOD));
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/sapling/cassia_cinnamon", new BlockCassiaCinnamonSapling(), CT_WOOD));
        
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/log/ceylon_cinnamon", new BlockCeylonCinnamonLog(), CT_WOOD));
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/leaves/ceylon_cinnamon", new BlockCeylonCinnamonLeaves(), CT_WOOD));
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/sapling/ceylon_cinnamon", new BlockCeylonCinnamonSapling(), CT_WOOD));

        // Bamboo
        for (int i = 0; i < bamboo.length; i++)
        {
            fruitBarrel.add(register(r, "wood/barrel/" + bamboo[i], new BlockFruitBarrel(), CT_DECORATIONS));
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

        ImmutableList.Builder<BlockFluidBase> fluids = ImmutableList.builder();
        for (FluidWrapper wrapper : FluidsTFCF.getAllFiniteFluids())
        {
            fluids.add(register(r, wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
        }

        for(Tree wood : TFCRegistries.TREES.getValuesCollection())
        {
            fenceGatesLog.add(register(r, "wood/fence_gate_log/" + wood.getRegistryName().getPath(), new BlockFenceGateLog(wood), CT_DECORATIONS));
        }

        allFluidBlocks = fluids.build();

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

        allFruitBarrel = fruitBarrel.build();
        allFruitBarrel.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

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

        allNormalItemBlocks = normalItemBlocks.build();

        FluidsTFC.getWrapper(FluidsTFCF.COCONUT_MILK.get());
        
        register(TELargeKaoliniteVessel.class, "large_kaolinite_vessel");
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
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS;
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
        rockTFCF == RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS;
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
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS;
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

    public static boolean isDirt(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.MUD || 
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
        rockTFCF == RockTFCF.COARSE_SILT;
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
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS;
    }

    public static boolean isGrowableSoil(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.COARSE_DIRT || 
        rockTFCF == RockTFCF.MUD || 
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
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS;
    }

    public static boolean isSoilOrGravel(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.COARSE_DIRT || 
        rockTFCF == RockTFCF.MUD || 
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
        rockTFCF == RockTFCF.HUMUS_GRASS || 
        rockTFCF == RockTFCF.DRY_HUMUS_GRASS;
    }

    public static boolean isGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return rockTFCF.isGrass;
        /*
        return
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
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS;
        */
    }

    public static boolean isPodzol(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.PODZOL || 
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
        rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_PODZOL;
    }

    public static boolean isDryGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.MUD || 
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
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS;
    }

    public static boolean isGround(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
        rockTFCF == RockTFCF.COARSE_DIRT || 
        rockTFCF == RockTFCF.MUD || 
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
        rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS;
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