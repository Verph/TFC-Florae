/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package tfcflorae.objects.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.*;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.blocks.BlockFluidTFC;
import net.dries007.tfc.objects.blocks.agriculture.*;
import net.dries007.tfc.objects.blocks.devices.*;
import net.dries007.tfc.objects.blocks.metal.BlockAnvilTFC;
import net.dries007.tfc.objects.blocks.metal.BlockIngotPile;
import net.dries007.tfc.objects.blocks.metal.BlockMetalLamp;
import net.dries007.tfc.objects.blocks.metal.BlockMetalSheet;
import net.dries007.tfc.objects.blocks.plants.BlockFloatingWaterTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
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

import tfcflorae.objects.blocks.*;
import tfcflorae.objects.blocks.FruitTree.*;
import tfcflorae.objects.blocks.FruitWood.*;
import tfcflorae.objects.blocks.wood.BlockFenceGateLog;
import tfcflorae.objects.fluids.FluidsTFCF;
import tfcflorae.objects.items.ItemBlockRot;
import tfcflorae.objects.items.ItemFoodTFCF;
import tfcflorae.objects.te.TEStemCrop;
import tfcflorae.util.agriculture.*;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.Rock.Type.*;
import static net.dries007.tfc.objects.CreativeTabsTFC.*;

import static tfcflorae.TFCFlorae.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
@GameRegistry.ObjectHolder(MODID)
public class BlocksTFCF
{
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

    // All these are for use in model registration. Do not use for block lookups.
    // Use the static get methods in the classes instead.
    private static ImmutableList<ItemBlock> allNormalItemBlocks;
    private static ImmutableList<Block> allInventoryItemBlocks;
    private static ImmutableList<Block> allFoodItemBlocks = Helpers.getNull();
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
    //private static ImmutableList<BlockFruitLoom> allFruitLoom = Helpers.getNull();
    private static ImmutableList<BlockFruitPlanks> allFruitPlanks = Helpers.getNull();
    /*
    private static ImmutableList<BlockFruitSlabBase> allFruitSlabBase = Helpers.getNull();
    private static ImmutableList<BlockFruitDoubleSlabBase> allFruitDoubleSlabBase = Helpers.getNull();
    private static ImmutableList<BlockFruitStairs> allFruitStairs = Helpers.getNull();
    */
    private static ImmutableList<BlockFruitSupport> allFruitSupport = Helpers.getNull();
    private static ImmutableList<BlockFruitToolRack> allFruitToolRack = Helpers.getNull();
    private static ImmutableList<BlockFruitTrapDoor> allFruitTrapDoors = Helpers.getNull();
    private static ImmutableList<BlockFruitWorkbench> allFruitWorkbench = Helpers.getNull();
    private static ImmutableList<BlockFluidBase> allFluidBlocks = Helpers.getNull();
    private static ImmutableList<BlockCropTFC> allCropBlocksTFC = Helpers.getNull();
    private static ImmutableList<BlockCropDead> allDeadCrops = Helpers.getNull();
    private static ImmutableList<BlockStemCrop> allCropBlocks = Helpers.getNull();
    private static ImmutableList<BlockBerryBush> allBerryBushBlocks;

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

    /*
    public static ImmutableList<BlockFruitSlabBase> getAllFruitSlabBase()
    {
        return allFruitSlabBase;
    }

    public static ImmutableList<BlockFruitDoubleSlabBase> getAllFruitDoubleSlabBase()
    {
        return allFruitDoubleSlabBase;
    }

    public static ImmutableList<BlockFruitStairs> getAllFruitStairs()
    {
        return allFruitStairs;
    }
    */

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

    /*
    public static ImmutableList<BlockFruitLoom> getAllFruitLoom()
    {
        return allFruitLoom;
    }
    */

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

    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        // This is called here because it needs to wait until Metal registry has fired
        FluidsTFCF.registerFluids();
        IForgeRegistry<Block> r = event.getRegistry();

        /*
        Builder<ItemBlock> normalItemBlocks = ImmutableList.builder();
        Builder<ItemBlock> inventoryItemBlocks = ImmutableList.builder();
        */
        
        ImmutableList.Builder<ItemBlock> normalItemBlocks = ImmutableList.builder();
        ImmutableList.Builder<Block> inventoryItemBlocks = ImmutableList.builder();
        ImmutableList.Builder<Block> foodItemBlocks = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitTreeLeaves> fruitLeaves = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitTreeSapling> fruitSapling = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitBarrel> fruitBarrel = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitBookshelves> fruitBookshelves = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitButton> fruitButton = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitDoor> fruitDoors = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitPlanks> fruitPlanks = ImmutableList.builder();
        /*
        ImmutableList.Builder<BlockFruitSlabBase> fruitSlabBase = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitDoubleSlabBase> fruitDoubleSlabBase = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitStairs> fruitStairs = ImmutableList.builder();
        */
        ImmutableList.Builder<BlockFruitPressurePlate> fruitPressurePlate = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitFence> fruitFences = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitFenceGate> fruitFenceGates = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitLogFence> fruitLogFences = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitLogFenceGate> fruitLogFenceGates = ImmutableList.builder();
        //ImmutableList.Builder<BlockFruitLoom> fruitLoom = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitSupport> fruitSupport = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitToolRack> fruitToolRack = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitTrapDoor> fruitTrapdoors = ImmutableList.builder();
        ImmutableList.Builder<BlockFruitWorkbench> fruitWorkbench = ImmutableList.builder();
        ImmutableList.Builder<BlockCropTFC> cropBlocksTFC = ImmutableList.builder();
        ImmutableList.Builder<BlockCropDead> deadCrops = ImmutableList.builder();
        ImmutableList.Builder<BlockStemCrop> cropBlocks = ImmutableList.builder();
        ImmutableList.Builder<BlockBerryBush> cropBerryBushBlocks = ImmutableList.builder();
        
        
        for (FruitTreeTFCF fruitTree : FruitTreeTFCF.values())
        {
            String name = fruitTree.getName().toLowerCase();
            register(r, "wood/fruit_tree/branch/" + name, new BlockFruitTreeBranch(fruitTree));
            fruitLeaves.add(register(r, "wood/fruit_tree/leaves/" + name, new BlockFruitTreeLeaves(fruitTree), CT_WOOD));
            fruitSapling.add(register(r, "wood/fruit_tree/sapling/" + name, new BlockFruitTreeSapling(fruitTree), CT_WOOD));
            register(r, "wood/fruit_tree/trunk/" + name, new BlockFruitTreeTrunk(fruitTree));
            fruitBarrel.add(register(r, "wood/fruit_tree/barrel/" +   name, new BlockFruitBarrel(), CT_DECORATIONS));
            fruitBookshelves.add(register(r, "wood/fruit_tree/bookshelf/" + name, new BlockFruitBookshelves(), CT_DECORATIONS));
            fruitButton.add(register(r, "wood/fruit_tree/button/" +   name, new BlockFruitButton(), CT_DECORATIONS));
            fruitDoors.add(register(r, "wood/fruit_tree/door/" +   name, new BlockFruitDoor(), CT_DECORATIONS));
            fruitPlanks.add(register(r, "wood/fruit_tree/planks/" + name, new BlockFruitPlanks(), CT_WOOD));
            //fruitSlabBase.add(register(r, "wood/fruit_tree/slab/" + name, new BlockFruitSlabBase(), CT_WOOD));
            //fruitDoubleSlabBase.add(register(r, "wood/fruit_tree/double_slab/" + name, new BlockFruitDoubleSlabBase()));
            //fruitStairs.add(register(r, "wood/fruit_tree/stairs/" + name, new BlockFruitStairs(), CT_WOOD));
            fruitPressurePlate.add(register(r, "wood/fruit_tree/pressure_plate/" + name, new BlockFruitPressurePlate(), CT_DECORATIONS));
            fruitFences.add(register(r, "wood/fruit_tree/fence/" +  name, new BlockFruitFence(), CT_DECORATIONS));
            fruitFenceGates.add(register(r, "wood/fruit_tree/fence_gate/" +  name, new BlockFruitFenceGate(), CT_DECORATIONS));
            fruitLogFences.add(register(r, "wood/fruit_tree/fence_log/" +  name, new BlockFruitLogFence(), CT_DECORATIONS));
            fruitLogFenceGates.add(register(r, "wood/fruit_tree/fence_gate_log/" +  name, new BlockFruitLogFenceGate(), CT_DECORATIONS));
            //fruitLoom.add(register(r, "wood/fruit_tree/loom/" +   name, new BlockFruitLoom(), CT_DECORATIONS));
            fruitSupport.add(register(r, "wood/fruit_tree/support/" +   name, new BlockFruitSupport(), CT_DECORATIONS));
            fruitToolRack.add(register(r, "wood/fruit_tree/tool_rack/" +   name, new BlockFruitToolRack(), CT_DECORATIONS));
            fruitTrapdoors.add(register(r, "wood/fruit_tree/trapdoor/" +   name, new BlockFruitTrapDoor(), CT_DECORATIONS));
            fruitWorkbench.add(register(r, "wood/fruit_tree/workbench/" +   name, new BlockFruitWorkbench(), CT_DECORATIONS));
        }

        fruitBarrel.add(register(r, "wood/fruit_tree/barrel/cassia_cinnamon", new BlockFruitBarrel(), CT_DECORATIONS));
        fruitBookshelves.add(register(r, "wood/fruit_tree/bookshelf/cassia_cinnamon", new BlockFruitBookshelves(), CT_DECORATIONS));
        fruitDoors.add(register(r, "wood/fruit_tree/door/cassia_cinnamon", new BlockFruitDoor(), CT_DECORATIONS));
        fruitButton.add(register(r, "wood/fruit_tree/button/cassia_cinnamon", new BlockFruitButton(), CT_DECORATIONS));
        fruitPlanks.add(register(r, "wood/fruit_tree/planks/cassia_cinnamon", new BlockFruitPlanks(), CT_WOOD));
        //fruitSlabBase.add(register(r, "wood/fruit_tree/slab/cassia_cinnamon", new BlockFruitSlabBase(), CT_WOOD));
        //fruitDoubleSlabBase.add(register(r, "wood/fruit_tree/double_slab/cassia_cinnamon", new BlockFruitDoubleSlabBase()));
        //fruitStairs.add(register(r, "wood/fruit_tree/stairs/cassia_cinnamon", new BlockFruitStairs(), CT_WOOD));
        fruitPressurePlate.add(register(r, "wood/fruit_tree/pressure_plate/cassia_cinnamon", new BlockFruitPressurePlate(), CT_DECORATIONS));
        fruitFences.add(register(r, "wood/fruit_tree/fence/cassia_cinnamon", new BlockFruitFence(), CT_DECORATIONS));
        fruitFenceGates.add(register(r, "wood/fruit_tree/fence_gate/cassia_cinnamon", new BlockFruitFenceGate(), CT_DECORATIONS));
        fruitLogFences.add(register(r, "wood/fruit_tree/fence_log/cassia_cinnamon", new BlockFruitLogFence(), CT_DECORATIONS));
        fruitLogFenceGates.add(register(r, "wood/fruit_tree/fence_gate_log/cassia_cinnamon", new BlockFruitLogFenceGate(), CT_DECORATIONS));
        //fruitLoom.add(register(r, "wood/fruit_tree/loom/cassia_cinnamon", new BlockFruitLoom(), CT_DECORATIONS));
        fruitSupport.add(register(r, "wood/fruit_tree/support/cassia_cinnamon", new BlockFruitSupport(), CT_DECORATIONS));
        fruitToolRack.add(register(r, "wood/fruit_tree/tool_rack/cassia_cinnamon", new BlockFruitToolRack(), CT_DECORATIONS));
        fruitTrapdoors.add(register(r, "wood/fruit_tree/trapdoor/cassia_cinnamon", new BlockFruitTrapDoor(), CT_DECORATIONS));
        fruitWorkbench.add(register(r, "wood/fruit_tree/workbench/cassia_cinnamon", new BlockFruitWorkbench(), CT_DECORATIONS));
        
        fruitBarrel.add(register(r, "wood/fruit_tree/barrel/ceylon_cinnamon", new BlockFruitBarrel(), CT_DECORATIONS));
        fruitBookshelves.add(register(r, "wood/fruit_tree/bookshelf/ceylon_cinnamon", new BlockFruitBookshelves(), CT_DECORATIONS));
        fruitButton.add(register(r, "wood/fruit_tree/button/ceylon_cinnamon", new BlockFruitButton(), CT_DECORATIONS));
        fruitDoors.add(register(r, "wood/fruit_tree/door/ceylon_cinnamon", new BlockFruitDoor(), CT_DECORATIONS));
        fruitPlanks.add(register(r, "wood/fruit_tree/planks/ceylon_cinnamon", new BlockFruitPlanks(), CT_WOOD));
        //fruitSlabBase.add(register(r, "wood/fruit_tree/slab/ceylon_cinnamon", new BlockFruitSlabBase(), CT_WOOD));
        //fruitDoubleSlabBase.add(register(r, "wood/fruit_tree/double_slab/ceylon_cinnamon", new BlockFruitDoubleSlabBase()));
        //fruitStairs.add(register(r, "wood/fruit_tree/stairs/ceylon_cinnamon", new BlockFruitStairs(), CT_WOOD));
        fruitPressurePlate.add(register(r, "wood/fruit_tree/pressure_plate/ceylon_cinnamon", new BlockFruitPressurePlate(), CT_DECORATIONS));
        fruitFences.add(register(r, "wood/fruit_tree/fence/ceylon_cinnamon", new BlockFruitFence(), CT_DECORATIONS));
        fruitFenceGates.add(register(r, "wood/fruit_tree/fence_gate/ceylon_cinnamon", new BlockFruitFenceGate(), CT_DECORATIONS));
        fruitLogFences.add(register(r, "wood/fruit_tree/fence_log/ceylon_cinnamon", new BlockFruitLogFence(), CT_DECORATIONS));
        fruitLogFenceGates.add(register(r, "wood/fruit_tree/fence_gate_log/ceylon_cinnamon", new BlockFruitLogFenceGate(), CT_DECORATIONS));
        //fruitLoom.add(register(r, "wood/fruit_tree/loom/ceylon_cinnamon", new BlockFruitLoom(), CT_DECORATIONS));
        fruitSupport.add(register(r, "wood/fruit_tree/support/ceylon_cinnamon", new BlockFruitSupport(), CT_DECORATIONS));
        fruitToolRack.add(register(r, "wood/fruit_tree/tool_rack/ceylon_cinnamon", new BlockFruitToolRack(), CT_DECORATIONS));
        fruitTrapdoors.add(register(r, "wood/fruit_tree/trapdoor/ceylon_cinnamon", new BlockFruitTrapDoor(), CT_DECORATIONS));
        fruitWorkbench.add(register(r, "wood/fruit_tree/workbench/ceylon_cinnamon", new BlockFruitWorkbench(), CT_DECORATIONS));

        for (IFruitTree fruitTree : FruitTree.values())
        {
            String name = fruitTree.getName().toLowerCase();
            fruitBarrel.add(register(r, "wood/fruit_tree/barrel/" +   name, new BlockFruitBarrel(), CT_DECORATIONS));
            fruitBookshelves.add(register(r, "wood/fruit_tree/bookshelf/" + name, new BlockFruitBookshelves(), CT_DECORATIONS));
            fruitDoors.add(register(r, "wood/fruit_tree/door/" +  name, new BlockFruitDoor(), CT_DECORATIONS));
            fruitButton.add(register(r, "wood/fruit_tree/button/" +   name, new BlockFruitButton(), CT_DECORATIONS));
            fruitPlanks.add(register(r, "wood/fruit_tree/planks/" + name, new BlockFruitPlanks(), CT_WOOD));
            //fruitSlabBase.add(register(r, "wood/fruit_tree/slab/" + name, new BlockFruitSlabBase(), CT_WOOD));
            //fruitDoubleSlabBase.add(register(r, "wood/fruit_tree/double_slab/" + name, new BlockFruitDoubleSlabBase()));
            //fruitStairs.add(register(r, "wood/fruit_tree/stairs/" + name, new BlockFruitStairs(), CT_WOOD));
            fruitPressurePlate.add(register(r, "wood/fruit_tree/pressure_plate/" + name, new BlockFruitPressurePlate(), CT_DECORATIONS));
            fruitFences.add(register(r, "wood/fruit_tree/fence/" + name, new BlockFruitFence(), CT_DECORATIONS));
            fruitFenceGates.add(register(r, "wood/fruit_tree/fence_gate/" + name, new BlockFruitFenceGate(), CT_DECORATIONS));
            fruitLogFences.add(register(r, "wood/fruit_tree/fence_log/" + name, new BlockFruitLogFence(), CT_DECORATIONS));
            fruitLogFenceGates.add(register(r, "wood/fruit_tree/fence_gate_log/" + name, new BlockFruitLogFenceGate(), CT_DECORATIONS));
            //fruitLoom.add(register(r, "wood/fruit_tree/loom/" +   name, new BlockFruitLoom(), CT_DECORATIONS));
            fruitSupport.add(register(r, "wood/fruit_tree/support/" +   name, new BlockFruitSupport(), CT_DECORATIONS));
            fruitToolRack.add(register(r, "wood/fruit_tree/tool_rack/" +   name, new BlockFruitToolRack(), CT_DECORATIONS));
            fruitTrapdoors.add(register(r, "wood/fruit_tree/trapdoor/" +  name, new BlockFruitTrapDoor(), CT_DECORATIONS));
            fruitWorkbench.add(register(r, "wood/fruit_tree/workbench/" +   name, new BlockFruitWorkbench(), CT_DECORATIONS));
        }

        inventoryItemBlocks.add(register(r, "wood/fruit_tree/log/cassia_cinnamon", new BlockCassiaCinnamonLog(), CT_WOOD));
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/leaves/cassia_cinnamon", new BlockCassiaCinnamonLeaves(), CT_WOOD));
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/sapling/cassia_cinnamon", new BlockCassiaCinnamonSapling(), CT_WOOD));
        
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/log/ceylon_cinnamon", new BlockCeylonCinnamonLog(), CT_WOOD));
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/leaves/ceylon_cinnamon", new BlockCeylonCinnamonLeaves(), CT_WOOD));
        inventoryItemBlocks.add(register(r, "wood/fruit_tree/sapling/ceylon_cinnamon", new BlockCeylonCinnamonSapling(), CT_WOOD));

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

        ImmutableList.Builder<BlockFluidBase> fluids = ImmutableList.builder();
        for (FluidWrapper wrapper : FluidsTFCF.getAllFiniteFluids())
        {
            fluids.add(register(r, wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
        }
        
        for(Tree tree : TFCRegistries.TREES.getValuesCollection())
        {
            inventoryItemBlocks.add(registerWoodBlock(r, "wood/fence_gate_log/"+tree.getRegistryName().getPath(), new BlockFenceGateLog(tree)));
        }

        Builder<BlockBerryBush> fBerry = ImmutableList.builder();

        for (BerryBushTFCF bush : BerryBushTFCF.values())
        {
            fBerry.add(register(r, "berry_bush/" + bush.name().toLowerCase(), new BlockBerryBush(bush), CT_FOOD));
        }

        allBerryBushBlocks = fBerry.build();
        allBerryBushBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));

        allFluidBlocks = fluids.build();

        allInventoryItemBlocks = inventoryItemBlocks.build();
        allInventoryItemBlocks.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFoodItemBlocks = foodItemBlocks.build();
        allFoodItemBlocks.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockRot(x));
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

        /*
        allFruitSlabBase = fruitSlabBase.build();
        allFruitSlabBase.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitDoubleSlabBase = fruitDoubleSlabBase.build();
        allFruitDoubleSlabBase.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });

        allFruitStairs = fruitStairs.build();
        allFruitStairs.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });
        */
        
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

        /*
        allFruitLoom = fruitLoom.build();
        allFruitLoom.forEach((x) -> {
            normalItemBlocks.add(new ItemBlockTFC(x));
        });
        */

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
        allCropBlocksTFC = cropBlocksTFC.build();
        allDeadCrops = deadCrops.build();
        allCropBlocks = cropBlocks.build();

        register(TEStemCrop.class, "stem_crop");

        FluidsTFC.getWrapper(FluidsTFCF.COCONUT_MILK.get());
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