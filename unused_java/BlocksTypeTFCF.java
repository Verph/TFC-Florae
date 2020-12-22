package tfcflorae.objects.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import java.util.ArrayList;

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

import net.dries007.tfc.api.recipes.ChiselRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.blocks.BlockDecorativeStone;
import net.dries007.tfc.objects.blocks.BlockPeat;
import net.dries007.tfc.objects.blocks.BlockPeatGrass;
import net.dries007.tfc.objects.blocks.BlockSlabTFC;
import net.dries007.tfc.objects.blocks.BlockStairsTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.blocks.stone.BlockWallTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.Helpers;

import tfcflorae.TFCFlorae;
//import tfcflorae.objects.blocks.blocktype.BlockDecoration;
//import tfcflorae.objects.blocks.blocktype.BlockGrass;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.objects.blocks.blocktype.*;
//import tfcflorae.objects.blocks.blocktype.BlockSoil;
import tfcflorae.types.BlockTypesTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

import static net.dries007.tfc.api.types.Rock.Type.*;
import static net.dries007.tfc.objects.CreativeTabsTFC.*;
import static net.dries007.tfc.objects.fluids.FluidsTFC.*;
import static net.dries007.tfc.util.Helpers.getNull;

import static tfcflorae.types.BlockTypesTFCF.RockTFCF.*;
import static tfcflorae.TFCFlorae.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = TFCFlorae.MODID)
@GameRegistry.ObjectHolder(MODID)
public class BlocksTypeTFCF
{
    private static ArrayList<Block> normalBlocks = new ArrayList<>();
    private static ArrayList<Block> inventoryBlocks = new ArrayList<>();

    private static ImmutableList<ItemBlock> allNormalItemBlocks;
    private static ImmutableList<ItemBlock> allInventoryItemBlocks;
    private static ImmutableList<BlockRockVariantTFCF> allBlockRockVariantsTFCF = Helpers.getNull();
    private static ImmutableList<BlockWallTFCF> allWallBlocks = Helpers.getNull();
    private static ImmutableList<BlockStairsTFCF> allStairsBlocks = Helpers.getNull();
    private static ImmutableList<BlockSlabTFCF.Half> allSlabBlocks = Helpers.getNull();

    public static ImmutableList<ItemBlock> getAllNormalItemBlocks()
    {
        return allNormalItemBlocks;
    }

    public static ImmutableList<ItemBlock> getAllInventoryItemBlocks()
    {
        return allInventoryItemBlocks;
    }

    public static ArrayList<Block> getNormalBlocks() {
        return normalBlocks;
    }

    public static ArrayList<Block> getInventoryBlocks() {
        return inventoryBlocks;
    }

    public static ImmutableList<BlockRockVariantTFCF> getAllBlockRockVariantsTFCF()
    {
        return allBlockRockVariantsTFCF;
    }

    public static ImmutableList<BlockWallTFCF> getAllWallBlocks()
    {
        return allWallBlocks;
    }

    public static ImmutableList<BlockStairsTFCF> getAllStairsBlocks()
    {
        return allStairsBlocks;
    }

    public static ImmutableList<BlockSlabTFCF.Half> getAllSlabBlocks()
    {
        return allSlabBlocks;
    }

    /*
    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> r = event.getRegistry();
        
        for(Block block : inventoryBlocks)
        {
            ItemBlockTFC itemBlockTFC = new ItemBlockTFC(block);
            itemBlockTFC.setRegistryName(block.getRegistryName());
            itemBlockTFC.setTranslationKey(block.getTranslationKey());
            itemBlockTFC.setCreativeTab(block.getCreativeTab());
            r.register(itemBlockTFC);
        }
        for(Block block : normalBlocks)
        {
            ItemBlockTFC itemBlockTFC = new ItemBlockTFC(block);
            itemBlockTFC.setRegistryName(block.getRegistryName());
            itemBlockTFC.setTranslationKey(block.getTranslationKey());
            itemBlockTFC.setCreativeTab(block.getCreativeTab());
            r.register(itemBlockTFC);
        }
    }
    */

    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> r = event.getRegistry();

        Builder<ItemBlock> normalItemBlocks = ImmutableList.builder();
        Builder<ItemBlock> inventoryItemBlocks = ImmutableList.builder();

        ImmutableList.Builder<BlockRockVariantTFCF> blockRockVariantsTFCF = ImmutableList.builder();
        ImmutableList.Builder<BlockWallTFCF> blockWallTFCF = ImmutableList.builder();
        ImmutableList.Builder<BlockStairsTFCF> blockStairsTFC = new Builder<>();
        ImmutableList.Builder<BlockSlabTFCF.Half> blockSlabTFCF = new Builder<>();

        /*
        {
            Builder<BlockWallTFCF> b = ImmutableList.builder();
            Builder<BlockStairsTFCF> stairs = new Builder<>();
            Builder<BlockSlabTFCF.Half> slab = new Builder<>();

            // Walls
            for (RockTFCF rockTFCF : new RockTFCF[] {MUD_BRICKS})
                for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                    blockWallTFCF.add(register(r, ("wall/" + rockTFCF.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockWallTFCF(BlockRockVariantTFCF.get(rock, rockTFCF)), CT_DECORATIONS));

                    // Stairs
            for (RockTFCF rockTFCF : new RockTFCF[] {MUD_BRICKS})
                for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                    blockStairsTFC.add(register(r, "stairs/" + (rockTFCF.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockStairsTFCF(rock, rockTFCF), CT_DECORATIONS));

            // Full slabs are the same as full blocks, they are not saved to a list, they are kept track of by the halfslab version.
            for (RockTFCF rockTFCF : new RockTFCF[] {MUD_BRICKS})
                for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                    register(r, "double_slab/" + (rockTFCF.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockSlabTFCF.Double(rock, rockTFCF));

            // Slabs
            for (RockTFCF rockTFCF : new RockTFCF[] {MUD_BRICKS})
                for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                    blockSlabTFCF.add(register(r, "slab/" + (rockTFCF.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockSlabTFCF.Half(rock, rockTFCF), CT_DECORATIONS));

            allWallBlocks = blockWallTFCF.build();
            allStairsBlocks = blockStairsTFC.build();
            allSlabBlocks = blockSlabTFCF.build();
            allWallBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
            allStairsBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
        }
        */
        
        {
            //Builder<BlockRockVariantTFCF> blockRockVariantsTFCF = ImmutableList.builder();
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
            /*
            {
                {
                    normalItemBlocks.add(new ItemBlockTFC(x));
                }
            });
            */
        }

        allNormalItemBlocks = normalItemBlocks.build();
        allInventoryItemBlocks = inventoryItemBlocks.build();
    }
    
    public static boolean isRawStone(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return rockTFCF == MOSSY_RAW;
    }

    public static boolean isClay(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return 
        rockTFCF == SANDY_CLAY_LOAM || 
        rockTFCF == SANDY_CLAY || 
        rockTFCF == CLAY_LOAM || 
        rockTFCF == SILTY_CLAY_LOAM || 
        rockTFCF == SILTY_CLAY || 
        rockTFCF == COARSE_SANDY_CLAY_LOAM || 
        rockTFCF == COARSE_SANDY_CLAY || 
        rockTFCF == COARSE_CLAY_LOAM || 
        rockTFCF == COARSE_CLAY || 
        rockTFCF == COARSE_SILTY_CLAY || 
        rockTFCF == COARSE_SILTY_CLAY_LOAM || 
        rockTFCF == SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == SANDY_CLAY_LOAM_PODZOL || 
        rockTFCF == SANDY_CLAY_GRASS || 
        rockTFCF == SANDY_CLAY_PODZOL || 
        rockTFCF == CLAY_LOAM_GRASS || 
        rockTFCF == CLAY_LOAM_PODZOL || 
        rockTFCF == CLAY_PODZOL || 
        rockTFCF == SILTY_CLAY_GRASS || 
        rockTFCF == SILTY_CLAY_PODZOL || 
        rockTFCF == SILTY_CLAY_LOAM_GRASS || 
        rockTFCF == SILTY_CLAY_LOAM_PODZOL || 
        rockTFCF == DRY_SANDY_CLAY_LOAM_GRASS || 
        rockTFCF == DRY_SANDY_CLAY_GRASS || 
        rockTFCF == DRY_CLAY_LOAM_GRASS || 
        rockTFCF == DRY_CLAY_GRASS || 
        rockTFCF == DRY_SILTY_CLAY_GRASS || 
        rockTFCF == DRY_SILTY_CLAY_LOAM_GRASS;
    }

    public static boolean isDirt(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return 
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
        rockTFCF == RockTFCF.DRY_SILT_GRASS;
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
        rockTFCF == RockTFCF.DRY_SILT_GRASS;
    }

    public static boolean isSoilOrGravel(IBlockState current)
    {
        if (current.getBlock() instanceof BlockPeat) return true;
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
        rockTFCF == RockTFCF.DRY_SILT_GRASS;
    }

    public static boolean isGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
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
        rockTFCF == RockTFCF.DRY_SILT_GRASS;
    }

    public static boolean isDryGrass(IBlockState current)
    {
        if (!(current.getBlock() instanceof BlockRockVariantTFCF)) return false;
        RockTFCF rockTFCF = ((BlockRockVariantTFCF) current.getBlock()).getType();
        return
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
        rockTFCF == RockTFCF.DRY_SILT_GRASS;
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
        rockTFCF == RockTFCF.DRY_SILT_GRASS;
    }

    public static BlockRockVariantTFCF registerBlock(IForgeRegistry<Block> r, String name, Rock rock, RockTFCF rockTFCF)
    {
        BlockRockVariantTFCF block = BlockRockVariantTFCF.create(rock, rockTFCF);
        block.setRegistryName(TFCFlorae.MODID, name);
        block.setTranslationKey(TFCFlorae.MODID + "." + name.replace('/', '.'));
        block.setCreativeTab(CreativeTabsTFC.CT_ROCK_BLOCKS);
        r.register(block);
        return block;
    }

    /*
    public static BlockGrass registerBlockGrass(IForgeRegistry<Block> r, String name, Rock rock, BlockTypesTFCF BlockTypesTFCF)
    {
        BlockGrass block = BlockGrass.create(rock, BlockTypesTFCF);
        block.setRegistryName(TFCFlorae.MODID, name);
        block.setTranslationKey(TFCFlorae.MODID + "." + name.replace('/', '.'));
        block.setCreativeTab(CreativeTabsTFC.CT_ROCK_BLOCKS);
        r.register(block);
        return block;
    }

    public static BlockSoil registerBlockSoil(IForgeRegistry<Block> r, String name, Rock rock, BlockTypesTFCF BlockTypesTFCF)
    {
        BlockSoil block = BlockSoil.create(rock, BlockTypesTFCF);
        block.setRegistryName(TFCFlorae.MODID, name);
        block.setTranslationKey(TFCFlorae.MODID + "." + name.replace('/', '.'));
        block.setCreativeTab(CreativeTabsTFC.CT_ROCK_BLOCKS);
        r.register(block);
        return block;
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
