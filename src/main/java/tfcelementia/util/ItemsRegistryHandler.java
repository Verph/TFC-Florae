package tfcelementia.util;

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
import net.dries007.tfc.objects.items.*;
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

import tfcelementia.objects.blocks.BlockMud;
import tfcelementia.objects.GemTFCE;
import tfcelementia.objects.PowderTFCE;
import tfcelementia.objects.items.*;
import tfcelementia.objects.items.food.ItemFoodTFCE;
import tfcelementia.objects.items.metal.ItemMetalTFCE;
import tfcelementia.objects.fluids.FluidsTFCE;
import tfcelementia.util.ItemsRegistryHandler;
import tfcelementia.util.OreDictionaryHelper;
import tfcelementia.util.agriculture.FoodTFCE;
import tfcelementia.util.agriculture.CropTFCE;
import tfcelementia.TFCElementia;

//import static tfcelementia.TFCElementia.MODID;
import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.CreativeTabsTFC.*;
import static net.dries007.tfc.util.Helpers.getNull;

@Mod.EventBusSubscriber(modid = TFCElementia.MODID)
//@GameRegistry.ObjectHolder(TFCElementia.MODID)
@GameRegistry.ObjectHolder(MOD_ID)
public final class ItemsRegistryHandler
{
    @GameRegistry.ObjectHolder("ceramics/mud_ball")
    public static final ItemMisc MUD_BALL = getNull();

    @GameRegistry.ObjectHolder("crop/product/malt_barley")
    public static final ItemMisc MALT_BARLEY = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_corn")
    public static final ItemMisc MALT_CORN = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_rice")
    public static final ItemMisc MALT_RICE = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_rye")
    public static final ItemMisc MALT_RYE = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_wheat")
    public static final ItemMisc MALT_WHEAT = getNull();
    
    @GameRegistry.ObjectHolder("crop/product/cinnamon_bark")
    public static final ItemMisc CINNAMON_BARK = getNull();
    
    @GameRegistry.ObjectHolder("crop/product/agave")
    public static final ItemMisc AGAVE = getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_boll")
    public static final ItemMisc COTTON_BOLL = getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_cloth")
    public static final ItemMisc COTTON_CLOTH = getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_yarn")
    public static final ItemMisc COTTON_YARN = getNull();
    @GameRegistry.ObjectHolder("crop/product/flax")
    public static final ItemMisc FLAX = getNull();
    @GameRegistry.ObjectHolder("crop/product/flax_fiber")
    public static final ItemMisc FLAX_FIBER = getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp")
    public static final ItemMisc HEMP = getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp_fiber")
    public static final ItemMisc HEMP_FIBER = getNull();
    @GameRegistry.ObjectHolder("crop/product/indigo")
    public static final ItemMisc INDIGO = getNull();
    @GameRegistry.ObjectHolder("crop/product/linen_cloth")
    public static final ItemMisc LINEN_CLOTH = getNull();
    @GameRegistry.ObjectHolder("crop/product/linen_string")
    public static final ItemMisc LINEN_STRING = getNull();
    @GameRegistry.ObjectHolder("crop/product/madder")
    public static final ItemMisc MADDER = getNull();
    @GameRegistry.ObjectHolder("crop/product/sisal_fiber")
    public static final ItemMisc SISAL_FIBER = getNull();
    @GameRegistry.ObjectHolder("crop/product/weld")
    public static final ItemMisc WELD = getNull();
    @GameRegistry.ObjectHolder("crop/product/woad")
    public static final ItemMisc WOAD = getNull();
    @GameRegistry.ObjectHolder("crop/product/hops")
    public static final ItemMisc HOPS = getNull();
    @GameRegistry.ObjectHolder("crop/product/black_tea")
    public static final ItemMisc BLACK_TEA = getNull();
    @GameRegistry.ObjectHolder("crop/product/green_tea")
    public static final ItemMisc GREEN_TEA = getNull();
    @GameRegistry.ObjectHolder("crop/product/white_tea")
    public static final ItemMisc WHITE_TEA = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_black_tea")
    public static final ItemMisc DRIED_BLACK_TEA = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_green_tea")
    public static final ItemMisc DRIED_GREEN_TEA = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_white_tea")
    public static final ItemMisc DRIED_WHITE_TEA = getNull();

    @GameRegistry.ObjectHolder("ceramics/unfired/clay_brick")
    public static final ItemPottery UNFIRED_CLAY_BRICK = getNull();
    @GameRegistry.ObjectHolder("ceramics/fired/clay_brick")
    public static final ItemPottery FIRED_CLAY_BRICK = getNull();
    @GameRegistry.ObjectHolder("ceramics/unfired/mud_brick")
    public static final ItemPottery UNFIRED_MUD_BRICK = getNull();
    @GameRegistry.ObjectHolder("ceramics/fired/mud_brick")
    public static final ItemPottery FIRED_MUD_BRICK = getNull();
    
    @GameRegistry.ObjectHolder("powder/sea_salt")
    public static final ItemPowder SEA_SALT = getNull();
    @GameRegistry.ObjectHolder("powder/calcium")
    public static final ItemPowder CALCIUM = getNull();
    @GameRegistry.ObjectHolder("powder/fluorite")
    public static final ItemPowder FLUORITE = getNull();
    @GameRegistry.ObjectHolder("powder/phosphorite")
    public static final ItemPowder PHOSPHORITE = getNull();
    @GameRegistry.ObjectHolder("powder/selenide")
    public static final ItemPowder SELENIDE = getNull();
    @GameRegistry.ObjectHolder("powder/iodate")
    public static final ItemPowder IODATE = getNull();

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

        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            //noinspection deprecation
            if (ReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            //noinspection ConstantConditions
            metalItems.add(register(r, "metal/plate/" + metal.getRegistryName().getPath().toLowerCase(), ItemMetalTFCE.ItemType.create(metal, ItemMetalTFCE.ItemType.PLATE), CT_METAL));
        }

        allMetalItems = metalItems.build();
        
        //Register oredict for metal item components
        for (Item metalItem : allMetalItems)
        {
            if (metalItem instanceof ItemMetalTFCE)
            {
            	ItemMetalTFCE techMetal = (ItemMetalTFCE) metalItem;
                OreDictionary.registerOre(OreDictionaryHelper.toString(techMetal.getType(), techMetal.getMetal(ItemStack.EMPTY)), new ItemStack(metalItem, 1, 0));
            }
            else
            {
                Metal metal = ((IMetalItem) metalItem).getMetal(ItemStack.EMPTY);
                OreDictionary.registerOre(OreDictionaryHelper.toString("plate"), new ItemStack(metalItem, 1, 0));
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
        
        for (CropTFCE crop : CropTFCE.values())
        {
            simpleItems.add(register(r, "crop/seeds/" + crop.name().toLowerCase(), new ItemSeedsTFCE(crop), CT_FOOD));
        }
        
        for (FoodTFCE food : FoodTFCE.values())
        {
            simpleItems.add(register(r, "food/" + food.name().toLowerCase(), new ItemFoodTFCE(food), CT_FOOD));
        }

        
        { // POTTERY
            simpleItems.add(register(r, "ceramics/mud_ball", new ItemMisc(Size.VERY_SMALL, Weight.MEDIUM), CT_MISC));
        	simpleItems.add(register(r, "ceramics/unfired/clay_brick", new ItemMisc(Size.VERY_SMALL, Weight.MEDIUM), CT_POTTERY));
        	simpleItems.add(register(r, "ceramics/fired/clay_brick", new ItemMisc(Size.VERY_SMALL, Weight.MEDIUM), CT_POTTERY));
        	simpleItems.add(register(r, "ceramics/unfired/mud_brick", new ItemMisc(Size.VERY_SMALL, Weight.MEDIUM), CT_POTTERY));
        	simpleItems.add(register(r, "ceramics/fired/mud_brick", new ItemMisc(Size.VERY_SMALL, Weight.MEDIUM), CT_POTTERY));
        }
        
        //Products
        simpleItems.add(register(r, "crop/product/agave", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_boll", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_cloth", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_yarn", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/flax", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/flax_fiber", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_fiber", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_cloth", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_string", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/madder", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_fiber", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/weld", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/woad", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/black_tea", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/green_tea", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/white_tea", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_black_tea", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_green_tea", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_white_tea", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/indigo", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));

        //Other
        simpleItems.add(register(r, "crop/product/cinnamon_bark", new ItemMisc(Size.VERY_SMALL, Weight.LIGHT), CT_MISC));
        
        //Malted grains
        simpleItems.add(register(r, "crop/product/malt_barley", new ItemMisc(Size.VERY_SMALL, Weight.LIGHT), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_corn", new ItemMisc(Size.VERY_SMALL, Weight.LIGHT), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_rice", new ItemMisc(Size.VERY_SMALL, Weight.LIGHT), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_rye", new ItemMisc(Size.VERY_SMALL, Weight.LIGHT), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_wheat", new ItemMisc(Size.VERY_SMALL, Weight.LIGHT), CT_FOOD));

        /*
        { // POTTERY
            simpleItems.add(register(r, "ceramics/mud_ball", new ItemMisc(Size.VERY_SMALL, Weight.MEDIUM, "mud", "mud_ball"), CT_MISC));
        	simpleItems.add(register(r, "ceramics/unfired/clay_brick", new ItemMisc(Size.VERY_SMALL, Weight.MEDIUM, "brickClayUnfired"), CT_POTTERY));
        	simpleItems.add(register(r, "ceramics/fired/clay_brick", new ItemMisc(Size.VERY_SMALL, Weight.MEDIUM, "brickClay"), CT_POTTERY));
        	simpleItems.add(register(r, "ceramics/unfired/mud_brick", new ItemMisc(Size.VERY_SMALL, Weight.MEDIUM, "brickMudUnfired"), CT_POTTERY));
        	simpleItems.add(register(r, "ceramics/fired/mud_brick", new ItemMisc(Size.VERY_SMALL, Weight.MEDIUM, "brickMud"), CT_POTTERY));
        }
        
        //Products
        simpleItems.add(register(r, "crop/product/agave", new ItemMisc(Size.TINY, Weight.LIGHT, "cropAgave"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_boll", new ItemMisc(Size.TINY, Weight.LIGHT, "cropCotton"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_cloth", new ItemMisc(Size.TINY, Weight.LIGHT, "cottonCloth", "cloth"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_yarn", new ItemMisc(Size.TINY, Weight.LIGHT, "cottonYarn", "yarn"), CT_MISC));
        simpleItems.add(register(r, "crop/product/flax", new ItemMisc(Size.TINY, Weight.LIGHT, "cropFlax"), CT_MISC));
        simpleItems.add(register(r, "crop/product/flax_fiber", new ItemMisc(Size.TINY, Weight.LIGHT, "fiberFlax", "fiber"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp", new ItemMisc(Size.TINY, Weight.LIGHT, "cropHemp"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_fiber", new ItemMisc(Size.TINY, Weight.LIGHT, "hempFiber", "fiber"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_cloth", new ItemMisc(Size.TINY, Weight.LIGHT, "linenCloth", "cloth"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_string", new ItemMisc(Size.TINY, Weight.LIGHT, "linenString", "string"), CT_MISC));
        simpleItems.add(register(r, "crop/product/madder", new ItemMisc(Size.TINY, Weight.LIGHT, "cropMadder"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_fiber", new ItemMisc(Size.TINY, Weight.LIGHT, "fiberSisal", "fiber"), CT_MISC));
        simpleItems.add(register(r, "crop/product/weld", new ItemMisc(Size.TINY, Weight.LIGHT, "cropWeld"), CT_MISC));
        simpleItems.add(register(r, "crop/product/woad", new ItemMisc(Size.TINY, Weight.LIGHT, "cropWoad"), CT_MISC));
        simpleItems.add(register(r, "crop/product/black_tea", new ItemMisc(Size.TINY, Weight.LIGHT, "cropBlackTea", "cropTea"), CT_MISC));
        simpleItems.add(register(r, "crop/product/green_tea", new ItemMisc(Size.TINY, Weight.LIGHT, "cropGreenTea", "cropTea"), CT_MISC));
        simpleItems.add(register(r, "crop/product/white_tea", new ItemMisc(Size.TINY, Weight.LIGHT, "cropWhiteTea", "cropTea"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_black_tea", new ItemMisc(Size.TINY, Weight.LIGHT, "teaBlackDried", "teaDried"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_green_tea", new ItemMisc(Size.TINY, Weight.LIGHT, "teaGreenDreid", "teaDried"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_white_tea", new ItemMisc(Size.TINY, Weight.LIGHT, "teaWhiteDried", "teaDried"), CT_MISC));
        simpleItems.add(register(r, "crop/product/indigo", new ItemMisc(Size.TINY, Weight.LIGHT, "cropIndigo"), CT_MISC));

        //Other
        simpleItems.add(register(r, "crop/product/cinnamon_bark", new ItemMisc(Size.VERY_SMALL, Weight.LIGHT, "cinnamon"), CT_MISC));
        
        //Malted grains
        simpleItems.add(register(r, "crop/product/malt_barley", new ItemMisc(Size.VERY_SMALL, Weight.LIGHT, "maltBarley"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_corn", new ItemMisc(Size.VERY_SMALL, Weight.LIGHT, "maltCorn"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_rice", new ItemMisc(Size.VERY_SMALL, Weight.LIGHT, "maltRice"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_rye", new ItemMisc(Size.VERY_SMALL, Weight.LIGHT, "maltRye"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_wheat", new ItemMisc(Size.VERY_SMALL, Weight.LIGHT, "maltWheat"), CT_FOOD));
		*/

        allSimpleItems = simpleItems.build();
        
        OreDictionaryHelper.init();
    }
    
    public static void init()
    {
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
        item.setRegistryName(MOD_ID, name);
        item.setTranslationKey(MOD_ID + "." + name.replace('/', '.'));
        item.setCreativeTab(ct);
        r.register(item);
        return item;
    }
}