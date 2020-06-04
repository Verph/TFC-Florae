package tfcelementia.objects.items;

import java.util.function.Function;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSnow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.*;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.ToolMaterialsTFC;
import net.dries007.tfc.objects.blocks.BlockSlabTFC;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.wood.BlockDoorTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
//import net.dries007.tfc.objects.items.*;
import net.dries007.tfc.objects.items.ceramics.*;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.ceramics.*;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTorch;
import net.dries007.tfc.objects.items.metal.ItemMetal;
import net.dries007.tfc.objects.items.metal.ItemMetalBucket;
import net.dries007.tfc.objects.items.metal.ItemOreTFC;
import net.dries007.tfc.objects.items.metal.ItemSmallOre;
import net.dries007.tfc.objects.items.rock.ItemBrickTFC;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.dries007.tfc.objects.items.rock.ItemRockToolHead;
import net.dries007.tfc.objects.items.wood.ItemBoatTFC;
import net.dries007.tfc.objects.items.wood.ItemDoorTFC;
import net.dries007.tfc.objects.items.wood.ItemLumberTFC;
import net.dries007.tfc.objects.items.wood.ItemWoodenBucket;
//import net.dries007.tfc.util.OreDictionaryHelper;
//import net.dries007.tfc.util.agriculture.Crop;
//import net.dries007.tfc.util.agriculture.Food;

import tfcelementia.objects.GemTFCE;
import tfcelementia.objects.PowderGemTFCE;
import tfcelementia.objects.PowderTFCE;
import tfcelementia.objects.items.ItemsTFCE;
import tfcelementia.objects.items.ceramics.ItemMoldTFCE;
import tfcelementia.objects.items.metal.ItemMetalTFCE;
import tfcelementia.util.OreDictionaryHelper;
import tfcelementia.TFCElementia;

//import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.CreativeTabsTFC.*;
import static net.dries007.tfc.util.Helpers.getNull;
import static tfcelementia.TFCElementia.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
@GameRegistry.ObjectHolder(MODID)
public final class ItemsTFCE
{
	/*
    @GameRegistry.ObjectHolder("ceramics/mud_ball")
    public static final ItemMiscTFCE MUD_BALL = getNull();
	*/
    @GameRegistry.ObjectHolder("items/wood/branch")
    public static final ItemMiscTFCE BRANCH = getNull();
    @GameRegistry.ObjectHolder("ore/coke")
    public static final ItemMiscTFCE COKE = getNull();
    
    @GameRegistry.ObjectHolder("powder/sea_salt")
    public static final ItemPowderTFCE SEA_SALT = getNull();
    @GameRegistry.ObjectHolder("powder/calcium")
    public static final ItemPowderTFCE CALCIUM = getNull();
    @GameRegistry.ObjectHolder("powder/fluorite")
    public static final ItemPowderTFCE FLUORITE = getNull();
    @GameRegistry.ObjectHolder("powder/phosphorite")
    public static final ItemPowderTFCE PHOSPHORITE = getNull();
    @GameRegistry.ObjectHolder("powder/selenide")
    public static final ItemPowderTFCE SELENIDE = getNull();
    @GameRegistry.ObjectHolder("powder/iodate")
    public static final ItemPowderTFCE IODATE = getNull();
    
    @GameRegistry.ObjectHolder("powder/cast_iron_grit")
    public static final ItemPowderTFCE CAST_IRON_GRIT = getNull();

    @GameRegistry.ObjectHolder("powder/gems/agate")
    public static final ItemPowderGemTFCE AGATE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/amethyst")
    public static final ItemPowderGemTFCE AMETHYST = getNull();
    @GameRegistry.ObjectHolder("powder/gems/beryl")
    public static final ItemPowderGemTFCE BERYL = getNull();
    @GameRegistry.ObjectHolder("powder/gems/diamond")
    public static final ItemPowderGemTFCE DIAMOND = getNull();
    @GameRegistry.ObjectHolder("powder/gems/emerald")
    public static final ItemPowderGemTFCE EMERALD = getNull();
    @GameRegistry.ObjectHolder("powder/gems/garnet")
    public static final ItemPowderGemTFCE GARNET = getNull();
    @GameRegistry.ObjectHolder("powder/gems/jade")
    public static final ItemPowderGemTFCE JADE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/jasper")
    public static final ItemPowderGemTFCE JASPER = getNull();
    @GameRegistry.ObjectHolder("powder/gems/opal")
    public static final ItemPowderGemTFCE OPAL = getNull();
    @GameRegistry.ObjectHolder("powder/gems/ruby")
    public static final ItemPowderGemTFCE RUBY = getNull();
    @GameRegistry.ObjectHolder("powder/gems/sapphire")
    public static final ItemPowderGemTFCE SAPPHIRE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/topaz")
    public static final ItemPowderGemTFCE TOPAZ = getNull();
    @GameRegistry.ObjectHolder("powder/gems/tourmaline")
    public static final ItemPowderGemTFCE TOURMALINE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/amber")
    public static final ItemPowderGemTFCE AMBER = getNull();
    @GameRegistry.ObjectHolder("powder/gems/apatite")
    public static final ItemPowderGemTFCE APATITE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/aquamarine")
    public static final ItemPowderGemTFCE AQUAMARINE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/bromargyrite")
    public static final ItemPowderGemTFCE BROMARGYRITE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/citrine")
    public static final ItemPowderGemTFCE CITRINE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/heliodor")
    public static final ItemPowderGemTFCE HELIODOR = getNull();
    @GameRegistry.ObjectHolder("powder/gems/iodargyrite")
    public static final ItemPowderGemTFCE IODARGYRITE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/kyanite")
    public static final ItemPowderGemTFCE KYANITE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/moldavite")
    public static final ItemPowderGemTFCE MOLDAVITE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/moonstone")
    public static final ItemPowderGemTFCE MOONSTONE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/pyromorphite")
    public static final ItemPowderGemTFCE PYROMORPHITE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/quartz")
    public static final ItemPowderGemTFCE QUARTZ = getNull();
    @GameRegistry.ObjectHolder("powder/gems/spinel")
    public static final ItemPowderGemTFCE SPINEL = getNull();
    @GameRegistry.ObjectHolder("powder/gems/sunstone")
    public static final ItemPowderGemTFCE SUNSTONE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/tanzanite")
    public static final ItemPowderGemTFCE TANZANITE = getNull();
    @GameRegistry.ObjectHolder("powder/gems/zircon")
    public static final ItemPowderGemTFCE ZIRCON = getNull();

    @GameRegistry.ObjectHolder("ceramics/fired/mold/nugget")
    public static final ItemMoldTFCE MOLD_NUGGET = getNull();
    @GameRegistry.ObjectHolder("ceramics/unfired/mold/nugget")
    public static final ItemPottery UNFIRED_NUGGET = getNull();
    @GameRegistry.ObjectHolder("ceramics/fired/mold/nail")
    public static final ItemMoldTFCE MOLD_NAIL = getNull();
    @GameRegistry.ObjectHolder("ceramics/unfired/mold/nail")
    public static final ItemPottery UNFIRED_NAIL = getNull();
    @GameRegistry.ObjectHolder("ceramics/fired/mold/ring")
    public static final ItemMoldTFCE MOLD_RING = getNull();
    @GameRegistry.ObjectHolder("ceramics/unfired/mold/ring")
    public static final ItemPottery UNFIRED_RING = getNull();
    
    private static ImmutableList<Item> allSimpleItems;
    private static ImmutableList<ItemOreTFC> allOreItems;
    private static ImmutableList<ItemGemTFCE> allGemTFCEItems;
    private static ImmutableList<Item> allMetalItems;
    private static ImmutableList<Item> allCeramicMoldItems;

    public static ImmutableList<Item> getAllSimpleItems()
    {
        return allSimpleItems;
    }

    public static ImmutableList<ItemOreTFC> getAllOreItems()
    {
        return allOreItems;
    }

    public static ImmutableList<ItemGemTFCE> getAllGemTFCEItems()
    {
        return allGemTFCEItems;
    }

    public static ImmutableList<Item> getAllMetalItems()
    {
        return allMetalItems;
    }

    public static ImmutableList<Item> getAllCeramicMoldItems()
    {
        return allCeramicMoldItems;
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> r = event.getRegistry();
        ImmutableList.Builder<Item> simpleItems = ImmutableList.builder();
        ImmutableList.Builder<Item> metalItems = ImmutableList.builder();
        ImmutableList.Builder<Item> ceramicItems = ImmutableList.builder();

        // Pottery
        ceramicItems.add(register(r, "ceramics/fired/mold/nugget", new ItemMold(Metal.ItemType.NUGGET), CT_POTTERY));
        simpleItems.add(register(r, "ceramics/unfired/mold/nugget", new ItemPottery(), CT_POTTERY));
        
        ceramicItems.add(register(r, "ceramics/fired/mold/nail", new ItemMoldTFCE(ItemMetalTFCE.ItemType.NAIL), CT_POTTERY));
        simpleItems.add(register(r, "ceramics/unfired/mold/nail", new ItemPottery(), CT_POTTERY));

        ceramicItems.add(register(r, "ceramics/fired/mold/ring", new ItemMoldTFCE(ItemMetalTFCE.ItemType.RING), CT_POTTERY));
        simpleItems.add(register(r, "ceramics/unfired/mold/ring", new ItemPottery(), CT_POTTERY));

        
        allCeramicMoldItems = ceramicItems.build();
        
        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            // noinspection ConstantConditions
            // metalItems.add(register(r, "metal/" + metal.getRegistryName().getPath().toLowerCase() + "_nail", ItemMetalTFCE.ItemType.create(metal, ItemMetalTFCE.ItemType.NAIL), CT_METAL));
            metalItems.add(register(r, "metal/nail/" + metal.getRegistryName().getPath().toLowerCase(), ItemMetalTFCE.ItemType.create(metal, ItemMetalTFCE.ItemType.NAIL), CT_METAL));
            metalItems.add(register(r, "metal/ring/" + metal.getRegistryName().getPath().toLowerCase(), ItemMetalTFCE.ItemType.create(metal, ItemMetalTFCE.ItemType.RING), CT_METAL));
            metalItems.add(register(r, "metal/chain/" + metal.getRegistryName().getPath().toLowerCase(), ItemMetalTFCE.ItemType.create(metal, ItemMetalTFCE.ItemType.CHAIN), CT_METAL));
            metalItems.add(register(r, "metal/ring_mesh/" + metal.getRegistryName().getPath().toLowerCase(), ItemMetalTFCE.ItemType.create(metal, ItemMetalTFCE.ItemType.RING_MESH), CT_METAL));
            metalItems.add(register(r, "metal/plate/" + metal.getRegistryName().getPath().toLowerCase(), ItemMetalTFCE.ItemType.create(metal, ItemMetalTFCE.ItemType.PLATE), CT_METAL));
            metalItems.add(register(r, "metal/double_plate/" + metal.getRegistryName().getPath().toLowerCase(), ItemMetalTFCE.ItemType.create(metal, ItemMetalTFCE.ItemType.PLATE_DOUBLE), CT_METAL));
            metalItems.add(register(r, "metal/triple_plate/" + metal.getRegistryName().getPath().toLowerCase(), ItemMetalTFCE.ItemType.create(metal, ItemMetalTFCE.ItemType.PLATE_TRIPLE), CT_METAL));
            metalItems.add(register(r, "metal/quadruple_plate/" + metal.getRegistryName().getPath().toLowerCase(), ItemMetalTFCE.ItemType.create(metal, ItemMetalTFCE.ItemType.PLATE_QUADRUPLE), CT_METAL));
            metalItems.add(register(r, "metal/triple_ingot/" + metal.getRegistryName().getPath().toLowerCase(), ItemMetalTFCE.ItemType.create(metal, ItemMetalTFCE.ItemType.INGOT_TRIPLE), CT_METAL));
            metalItems.add(register(r, "metal/quadruple_ingot/" + metal.getRegistryName().getPath().toLowerCase(), ItemMetalTFCE.ItemType.create(metal, ItemMetalTFCE.ItemType.INGOT_QUADRUPLE), CT_METAL));
            metalItems.add(register(r, "metal/triple_sheet/" + metal.getRegistryName().getPath().toLowerCase(), ItemMetalTFCE.ItemType.create(metal, ItemMetalTFCE.ItemType.SHEET_TRIPLE), CT_METAL));
            metalItems.add(register(r, "metal/quadruple_sheet/" + metal.getRegistryName().getPath().toLowerCase(), ItemMetalTFCE.ItemType.create(metal, ItemMetalTFCE.ItemType.SHEET_QUADRUPLE), CT_METAL));
        }

        allMetalItems = metalItems.build();
        
        // Register oredict for metal item components
        for (Item metalItem : allMetalItems)
        {
            if (metalItem instanceof ItemMetalTFCE)
            {
            	ItemMetalTFCE tfceMetal = (ItemMetalTFCE) metalItem;
                OreDictionary.registerOre(OreDictionaryHelper.toString(tfceMetal.getType(), tfceMetal.getMetal(ItemStack.EMPTY)), new ItemStack(metalItem, 1, 0));
            }
        }
        
        {
            Builder<ItemGemTFCE> b = new Builder<>();
            for (GemTFCE gem : GemTFCE.values())
                b.add(register(r, "gem/" + gem.name().toLowerCase(), new ItemGemTFCE(gem), CT_GEMS));
            allGemTFCEItems = b.build();
        }

        for (PowderTFCE powder : PowderTFCE.values())
            simpleItems.add(register(r, "powder/" + powder.name().toLowerCase(), new ItemPowderTFCE(powder), CT_MISC));

        for (PowderGemTFCE powder : PowderGemTFCE.values())
            simpleItems.add(register(r, "powder/gems/" + powder.name().toLowerCase(), new ItemPowderGemTFCE(powder), CT_GEMS));
                
        // Powders
        simpleItems.add(register(r, "powder/cast_iron_grit", new ItemMiscHeatableTFCE(Size.SMALL, Weight.VERY_LIGHT, 0.35f, 2000f, "grit_cast_iron"), CT_MISC));

        // Other
        simpleItems.add(register(r, "wood/twig", new ItemMiscTFCE(Size.SMALL, Weight.VERY_LIGHT, "twig", "twig_wood", "branch", "branch_wood"), CT_MISC));
        simpleItems.add(register(r, "ore/coke", new ItemMiscTFCE(Size.SMALL, Weight.MEDIUM, "gem_coke", "coal_coke", "item_coke", "fuel_coke", "coke"), CT_MISC));
        
        allSimpleItems = simpleItems.build();
        
        OreDictionaryHelper.init();
        
        /*
        for (Item item : allSimpleItems)
        {
            if (item instanceof ItemMiscTFCE && ((ItemMiscTFCE) item).getOreDictionary() != null)
            {
                OreDictionary.registerOre(((ItemMiscTFCE) item).getOreDictionary(), item);
            }
        }*/
    }
    
    public static void init()
    {
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer)
    {
        ItemBlock itemBlock = producer.apply(block);
        //noinspection ConstantConditions
        itemBlock.setRegistryName(block.getRegistryName());
        return itemBlock;
    }
    
    @SuppressWarnings("ConstantConditions")
    private static void registerItemBlock(IForgeRegistry<Item> r, ItemBlock item)
    {
        item.setRegistryName(item.getBlock().getRegistryName());
        item.setCreativeTab(item.getBlock().getCreativeTab());
        r.register(item);
    }

    private static <T extends Item> T register(IForgeRegistry<Item> r, String name, T item, CreativeTabs ct)
    {
        item.setRegistryName(MODID, name);
        item.setTranslationKey(MODID + "." + name.replace('/', '.'));
        item.setCreativeTab(ct);
        r.register(item);
        return item;
    }
}