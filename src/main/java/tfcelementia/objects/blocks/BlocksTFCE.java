package tfcelementia.objects.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;
//import net.dries007.tfc.TerraFirmaCraft;
//import net.dries007.tfc.api.registries.TFCRegistries;
//import net.dries007.tfc.api.types.*;
import net.dries007.tfc.objects.blocks.BlockFluidTFC;
import net.dries007.tfc.objects.blocks.BlockFluidWater;
import net.dries007.tfc.objects.blocks.agriculture.BlockBerryBush;
import net.dries007.tfc.objects.blocks.devices.*;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
//import net.dries007.tfc.objects.blocks.stone.*;
import net.dries007.tfc.objects.blocks.wood.*;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.items.itemblock.*;
//import net.dries007.tfc.util.OreDictionaryHelper;

//import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
//import static net.dries007.tfc.api.types.Rock.Type.*;
import static net.dries007.tfc.objects.CreativeTabsTFC.*;
import static net.dries007.tfc.util.Helpers.getNull;

//import tfcelementia.api.types.RockTFCE;
//import tfcelementia.api.registries.TFCERegistries;
//import tfcelementia.objects.blocks.stone.*;
import tfcelementia.objects.blocks.agriculture.BlockCropDeadTFCE;
import tfcelementia.objects.blocks.agriculture.BlockCropTFCE;
import tfcelementia.objects.blocks.agriculture.BlockFruitTreeBranchTFCE;
import tfcelementia.objects.blocks.agriculture.BlockFruitTreeLeavesTFCE;
import tfcelementia.objects.blocks.agriculture.BlockFruitTreeSaplingTFCE;
import tfcelementia.objects.blocks.agriculture.BlockFruitTreeTrunkTFCE;
import tfcelementia.objects.fluids.FluidsTFCE;
import tfcelementia.objects.items.*;
//import tfcelementia.objects.blocks.stone.*;
import tfcelementia.objects.te.*;
import tfcelementia.util.OreDictionaryHelper;
//import tfcelementia.util.agriculture.BerryBushTFCE;
import tfcelementia.util.agriculture.CropTFCE;
import tfcelementia.util.agriculture.FoodTFCE;
import tfcelementia.util.agriculture.FruitTreeTFCE;
import tfcelementia.TFCElementia;

//import static tfcelementia.api.types.RockTFCE.Type.*;
import static tfcelementia.TFCElementia.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = TFCElementia.MODID)
@GameRegistry.ObjectHolder(MODID)
public final class BlocksTFCE 
{
    private static ImmutableList<ItemBlock> allNormalItemBlocks;
	private static ImmutableList<ItemBlock> allInventoryItemBlocks;
	
    private static ImmutableList<BlockFluidBase> allFluidBlocks;
    private static ImmutableList<BlockRockVariant> allBlockRockVariants;
    
    /*
    private static ImmutableList<BlockRockVariantTFCE> allBlockRockVariants;
    private static ImmutableList<BlockWallTFCE> allWallBlocks;
    private static ImmutableList<BlockStairsTFCE> allStairsBlocks;
    private static ImmutableList<BlockSlabTFCE.Half> allSlabBlocks;
    */
    
    private static ImmutableList<BlockCropTFCE> allCropBlocks;
    private static ImmutableList<BlockCropDeadTFCE> allDeadCropBlocks;

    private static ImmutableList<BlockFruitTreeSaplingTFCE> allFruitTreeSaplingBlocks;
    private static ImmutableList<BlockFruitTreeTrunkTFCE> allFruitTreeTrunkBlocks;
    private static ImmutableList<BlockFruitTreeBranchTFCE> allFruitTreeBranchBlocks;
    private static ImmutableList<BlockFruitTreeLeavesTFCE> allFruitTreeLeavesBlocks;

    private static ImmutableList<BlockBerryBush> allBerryBushBlocks;

    public static ImmutableList<ItemBlock> getAllNormalItemBlocks()
    {
        return allNormalItemBlocks;
    }

    public static ImmutableList<ItemBlock> getAllInventoryItemBlocks()
    {
        return allInventoryItemBlocks;
    }
    
    /*
    public static ImmutableList<BlockWallTFCE> getAllWallBlocks()
    {
        return allWallBlocks;
    }

    public static ImmutableList<BlockRockVariantTFCE> getAllBlockRockVariants()
    {
        return allBlockRockVariants;
    }

    public static ImmutableList<BlockStairsTFCE> getAllStairsBlocks()
    {
        return allStairsBlocks;
    }

    public static ImmutableList<BlockSlabTFCE.Half> getAllSlabBlocks()
    {
        return allSlabBlocks;
    }
    */
    
    public static ImmutableList<BlockCropTFCE> getAllCropBlocks()
    {
        return allCropBlocks;
    }

    public static ImmutableList<BlockCropDeadTFCE> getAllDeadCropBlocks()
    {
        return allDeadCropBlocks;
    }
    
    public static ImmutableList<BlockFluidBase> getAllFluidBlocks()
    {
        return allFluidBlocks;
    }

    public static ImmutableList<BlockRockVariant> getAllBlockRockVariants()
    {
        return allBlockRockVariants;
    }

    public static ImmutableList<BlockFruitTreeSaplingTFCE> getAllFruitTreeSaplingBlocks()
    {
        return allFruitTreeSaplingBlocks;
    }

    public static ImmutableList<BlockFruitTreeTrunkTFCE> getAllFruitTreeTrunkBlocks()
    {
        return allFruitTreeTrunkBlocks;
    }

    public static ImmutableList<BlockFruitTreeBranchTFCE> getAllFruitTreeBranchBlocks()
    {
        return allFruitTreeBranchBlocks;
    }

    public static ImmutableList<BlockFruitTreeLeavesTFCE> getAllFruitTreeLeavesBlocks()
    {
        return allFruitTreeLeavesBlocks;
    }

    public static ImmutableList<BlockBerryBush> getAllBerryBushBlocks()
    {
        return allBerryBushBlocks;
    }
    
    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
    	FluidsTFCE.registerFluids();
    	
    	IForgeRegistry<Block> r = event.getRegistry();

        Builder<ItemBlock> normalItemBlocks = ImmutableList.builder();
        Builder<ItemBlock> inventoryItemBlocks = ImmutableList.builder();
        
        {
            Builder<BlockFluidBase> b = ImmutableList.builder();
            b.add(
                    register(r, "fluid/distilled_water", new BlockFluidWater(FluidsTFCE.DISTILLED_WATER.get(), Material.WATER, false))
                );
            for (FluidWrapper wrapper : FluidsTFCE.getAllTeaFluids())
            {
                if (wrapper.isDefault())
                {
                    b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
                }
            }
            for (FluidWrapper wrapper : FluidsTFCE.getAllCoffeeFluids())
            {
                if (wrapper.isDefault())
                {
                    b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
                }
            }
            for (FluidWrapper wrapper : FluidsTFCE.getAllMiscFluids())
            {
                if (wrapper.isDefault())
                {
                    b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
                }
            }
            for (FluidWrapper wrapper : FluidsTFCE.getAllFermentedAlcoholsFluids())
            {
                if (wrapper.isDefault())
                {
                    b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
                }
            }
            for (FluidWrapper wrapper : FluidsTFCE.getAllAlcoholsFluids())
            {
                if (wrapper.isDefault())
                {
                    b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
                }
            }
            for (FluidWrapper wrapper : FluidsTFCE.getAllBeerFluids())
            {
                if (wrapper.isDefault())
                {
                    b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
                }
            }
            for (FluidWrapper wrapper : FluidsTFCE.getAllJuiceBerryFluids())
            {
                if (wrapper.isDefault())
                {
                    b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
                }
            }
            for (FluidWrapper wrapper : FluidsTFCE.getAllJuiceFruitFluids())
            {
                if (wrapper.isDefault())
                {
                    b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
                }
            }
            allFluidBlocks = b.build();
        }

        /*
        {
            Builder<BlockRockVariantTFCE> b = ImmutableList.builder();
            
            for (RockTFCE.Type type : RockTFCE.Type.values())
            {
                for (RockTFCE rock : TFCERegistries.ROCKS_TFCE.getValuesCollection())
                    b.add(register(r, type.name().toLowerCase() + "/" + rock.getRegistryName().getPath(), BlockRockVariantTFCE.create(rock, type), CT_ROCK_BLOCKS));
            }
            allBlockRockVariants = b.build();
		}
		*/
		
        /*
		{
			Builder<BlockRockVariant> b = ImmutableList.builder();
			
			for (Rock.Type type : Rock.Type.values())
			{
				for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
    					b.add(register(r, type.name().toLowerCase() + "/" + rock.getRegistryName().getPath(), BlockRockVariant.create(rock, type), CT_ROCK_BLOCKS));
    	
			}
            allBlockRockVariants = b.build();
            allBlockRockVariants.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
		}
		*/
		
		/*
        {
            Builder<BlockWallTFCE> b = ImmutableList.builder();
            Builder<BlockStairsTFCE> stairs = new Builder<>();
            Builder<BlockSlabTFCE.Half> slab = new Builder<>();

            // Walls
            for (RockTFCE.Type type : new RockTFCE.Type[] {MOSSY_BRICKS, CRACKED_BRICKS, MOSSY_COBBLE})
                for (RockTFCE rock : TFCERegistries.ROCKS_TFCE.getValuesCollection())
                    b.add(register(r, ("wall/" + type.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockWallTFCE(BlockRockVariantTFCE.get(rock, type)), CT_DECORATIONS));
            // Stairs
            for (RockTFCE.Type type : new RockTFCE.Type[] {MOSSY_BRICKS, CRACKED_BRICKS, MOSSY_COBBLE})
                for (RockTFCE rock : TFCERegistries.ROCKS_TFCE.getValuesCollection())
                    stairs.add(register(r, "stairs/" + (type.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockStairsTFCE(rock, type), CT_DECORATIONS));

            // Full slabs are the same as full blocks, they are not saved to a list, they are kept track of by the halfslab version.
            for (RockTFCE.Type type : new RockTFCE.Type[] {MOSSY_BRICKS, CRACKED_BRICKS, MOSSY_COBBLE})
                for (RockTFCE rock : TFCERegistries.ROCKS_TFCE.getValuesCollection())
                    register(r, "double_slab/" + (type.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockSlabTFCE.Double(rock, type));

            // Slabs
            for (RockTFCE.Type type : new RockTFCE.Type[] {MOSSY_BRICKS, CRACKED_BRICKS, MOSSY_COBBLE})
                for (RockTFCE rock : TFCERegistries.ROCKS_TFCE.getValuesCollection())
                    slab.add(register(r, "slab/" + (type.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockSlabTFCE.Half(rock, type), CT_DECORATIONS));

            for (RockTFCE rock : TFCERegistries.ROCKS_TFCE.getValuesCollection())
                inventoryItemBlocks.add(new ItemBlockTFC(register(r, "stone/button/" + rock.getRegistryName().getPath().toLowerCase(), new BlockButtonStoneTFCE(rock), CT_DECORATIONS)));

            allWallBlocks = b.build();
            allStairsBlocks = stairs.build();
            allSlabBlocks = slab.build();
            allWallBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
            allStairsBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
            // slabs are special. (ItemSlabTFC)
        }
        */
        
    	{
            Builder<BlockCropTFCE> b = ImmutableList.builder();

            for (CropTFCE crop : CropTFCE.values())
            {
                b.add(register(r, "crop/" + crop.name().toLowerCase(), crop.createGrowingBlock()));
            }

            allCropBlocks = b.build();
        }

        {
            Builder<BlockCropDeadTFCE> b = ImmutableList.builder();

            for (CropTFCE crop : CropTFCE.values())
            {
                b.add(register(r, "dead_crop/" + crop.name().toLowerCase(), crop.createDeadBlock()));
            }

            allDeadCropBlocks = b.build();
        }
        
        {
            Builder<BlockFruitTreeSaplingTFCE> fSaplings = ImmutableList.builder();
            Builder<BlockFruitTreeTrunkTFCE> fTrunks = ImmutableList.builder();
            Builder<BlockFruitTreeBranchTFCE> fBranches = ImmutableList.builder();
            Builder<BlockFruitTreeLeavesTFCE> fLeaves = ImmutableList.builder();

            for (FruitTreeTFCE tree : FruitTreeTFCE.values())
            {
                fSaplings.add(register(r, "fruit_trees/sapling/" + tree.name().toLowerCase(), new BlockFruitTreeSaplingTFCE(tree), CT_WOOD));
                fTrunks.add(register(r, "fruit_trees/trunk/" + tree.name().toLowerCase(), new BlockFruitTreeTrunkTFCE(tree)));
                fBranches.add(register(r, "fruit_trees/branch/" + tree.name().toLowerCase(), new BlockFruitTreeBranchTFCE(tree)));
                fLeaves.add(register(r, "fruit_trees/leaves/" + tree.name().toLowerCase(), new BlockFruitTreeLeavesTFCE(tree), CT_WOOD));
            }

            allFruitTreeSaplingBlocks = fSaplings.build();
            allFruitTreeTrunkBlocks = fTrunks.build();
            allFruitTreeBranchBlocks = fBranches.build();
            allFruitTreeLeavesBlocks = fLeaves.build();

            /*
            Builder<BlockBerryBush> fBerry = ImmutableList.builder();

            for (BerryBushTFCE bush : BerryBushTFCE.values())
            {
                fBerry.add(register(r, "berry_bush/" + bush.name().toLowerCase(), new BlockBerryBush(bush), CT_FOOD));
            }

            allBerryBushBlocks = fBerry.build();
            */
            
            //Add ItemBlocks
            allFruitTreeSaplingBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
            allFruitTreeLeavesBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
            //allBerryBushBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
        }

        register(TECropBaseTFCE.class, "crop_base_tfce");
        register(TECropSpreadingTFCE.class, "crop_spreading_tfce");
    }
    
    /*
    public static boolean isSoil(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCE)) return false;
        RockTFCE.Type type = ((BlockRockVariantTFCE) current.getBlock()).getType();
        return type == PODZOL;
    }

    public static boolean isGrowableSoil(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCE)) return false;
        RockTFCE.Type type = ((BlockRockVariantTFCE) current.getBlock()).getType();
        return type == PODZOL;
    }

    public static boolean isGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCE)) return false;
        RockTFCE.Type type = ((BlockRockVariantTFCE) current.getBlock()).getType();
        return type.isGrass;
    }

    public static boolean isGround(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCE)) return false;
        RockTFCE.Type type = ((BlockRockVariantTFCE) current.getBlock()).getType();
        return type == PODZOL;
    }
    */
    
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
