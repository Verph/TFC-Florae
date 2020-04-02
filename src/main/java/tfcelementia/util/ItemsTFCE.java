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
import tfcelementia.objects.PowderTFCE;
import tfcelementia.objects.items.*;
import tfcelementia.objects.items.food.ItemFoodTFCE;
import tfcelementia.objects.items.metal.ItemMetalTFCE;
import tfcelementia.objects.fluids.FluidsTFCE;
import tfcelementia.util.ItemsTFCE;
import tfcelementia.util.OreDictionaryHelper;
import tfcelementia.util.agriculture.FoodTFCE;
import tfcelementia.util.agriculture.CropTFCE;
import tfcelementia.TFCElementia;

//import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.CreativeTabsTFC.*;
import static net.dries007.tfc.util.Helpers.getNull;
import static tfcelementia.TFCElementia.MODID;

@Mod.EventBusSubscriber(modid = TFCElementia.MODID)
//@GameRegistry.ObjectHolder(TFCElementia.MODID)
@GameRegistry.ObjectHolder(MODID)
public final class ItemsTFCE
{
    @GameRegistry.ObjectHolder("ceramics/mud_ball")
    public static final ItemMiscTFCE MUD_BALL = getNull();

    @GameRegistry.ObjectHolder("crop/product/malt_barley")
    public static final ItemMiscTFCE MALT_BARLEY = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_corn")
    public static final ItemMiscTFCE MALT_CORN = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_rice")
    public static final ItemMiscTFCE MALT_RICE = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_rye")
    public static final ItemMiscTFCE MALT_RYE = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_wheat")
    public static final ItemMiscTFCE MALT_WHEAT = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_amaranth")
    public static final ItemMiscTFCE MALT_AMARANTH = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_buckwheat")
    public static final ItemMiscTFCE MALT_BUCKWHEAT = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_fonio")
    public static final ItemMiscTFCE MALT_FONIO = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_millet")
    public static final ItemMiscTFCE MALT_MILLET = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_quinoa")
    public static final ItemMiscTFCE MALT_QUINOA = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_spelt")
    public static final ItemMiscTFCE MALT_SPELT = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_wild_rice")
    public static final ItemMiscTFCE MALT_WILD_RICE = getNull();
    
    @GameRegistry.ObjectHolder("crop/product/cinnamon_bark")
    public static final ItemMiscTFCE CINNAMON_BARK = getNull();
    
    @GameRegistry.ObjectHolder("crop/product/agave")
    public static final ItemMiscTFCE AGAVE = getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_boll")
    public static final ItemMiscTFCE COTTON_BOLL = getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_cloth")
    public static final ItemMiscTFCE COTTON_CLOTH = getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_yarn")
    public static final ItemMiscTFCE COTTON_YARN = getNull();
    @GameRegistry.ObjectHolder("crop/product/flax")
    public static final ItemMiscTFCE FLAX = getNull();
    @GameRegistry.ObjectHolder("crop/product/flax_fiber")
    public static final ItemMiscTFCE FLAX_FIBER = getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp")
    public static final ItemMiscTFCE HEMP = getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp_fiber")
    public static final ItemMiscTFCE HEMP_FIBER = getNull();
    @GameRegistry.ObjectHolder("crop/product/indigo")
    public static final ItemMiscTFCE INDIGO = getNull();
    @GameRegistry.ObjectHolder("crop/product/linen_cloth")
    public static final ItemMiscTFCE LINEN_CLOTH = getNull();
    @GameRegistry.ObjectHolder("crop/product/linen_string")
    public static final ItemMiscTFCE LINEN_STRING = getNull();
    @GameRegistry.ObjectHolder("crop/product/madder")
    public static final ItemMiscTFCE MADDER = getNull();
    @GameRegistry.ObjectHolder("crop/product/sisal_fiber")
    public static final ItemMiscTFCE SISAL_FIBER = getNull();
    @GameRegistry.ObjectHolder("crop/product/weld")
    public static final ItemMiscTFCE WELD = getNull();
    @GameRegistry.ObjectHolder("crop/product/woad")
    public static final ItemMiscTFCE WOAD = getNull();
    @GameRegistry.ObjectHolder("crop/product/hops")
    public static final ItemMiscTFCE HOPS = getNull();
    @GameRegistry.ObjectHolder("crop/product/rape")
    public static final ItemMiscTFCE RAPE = getNull();
    @GameRegistry.ObjectHolder("crop/product/rape_flower")
    public static final ItemMiscTFCE RAPE_FLOWER = getNull();
    @GameRegistry.ObjectHolder("crop/product/tobacco_leaf")
    public static final ItemMiscTFCE TOBACCO_LEAF = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_tobacco_leaf")
    public static final ItemMiscTFCE DRIED_TOBACCO_LEAF = getNull();
    @GameRegistry.ObjectHolder("crop/product/coca_leaf")
    public static final ItemMiscTFCE COCA_LEAF = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_coca_leaf")
    public static final ItemMiscTFCE DRIED_COCA_LEAF = getNull();
    @GameRegistry.ObjectHolder("crop/product/cannabis_bud")
    public static final ItemMiscTFCE CANNABIS_BUD = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_cannabis_bud")
    public static final ItemMiscTFCE DRIED_CANNABIS_BUD = getNull();
    @GameRegistry.ObjectHolder("crop/product/cannabis_leaf")
    public static final ItemMiscTFCE CANNABIS_LEAF = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_cannabis_leaf")
    public static final ItemMiscTFCE DRIED_CANNABIS_LEAF = getNull();
    @GameRegistry.ObjectHolder("crop/product/opium_poppy_bulb")
    public static final ItemMiscTFCE OPIUM_POPPY_BULB = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_opium_poppy_bulb")
    public static final ItemMiscTFCE DRIED_OPIUM_POPPY_BULB = getNull();
    @GameRegistry.ObjectHolder("crop/product/sunflower_head")
    public static final ItemMiscTFCE SUNFLOWER_HEAD = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_sunflower_head")
    public static final ItemMiscTFCE DRIED_SUNFLOWER_HEAD = getNull();
    @GameRegistry.ObjectHolder("crop/product/black_tea")
    public static final ItemMiscTFCE BLACK_TEA = getNull();
    @GameRegistry.ObjectHolder("crop/product/green_tea")
    public static final ItemMiscTFCE GREEN_TEA = getNull();
    @GameRegistry.ObjectHolder("crop/product/white_tea")
    public static final ItemMiscTFCE WHITE_TEA = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_black_tea")
    public static final ItemMiscTFCE DRIED_BLACK_TEA = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_green_tea")
    public static final ItemMiscTFCE DRIED_GREEN_TEA = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_white_tea")
    public static final ItemMiscTFCE DRIED_WHITE_TEA = getNull();

    @GameRegistry.ObjectHolder("ceramics/unfired/clay_brick")
    public static final ItemPottery UNFIRED_CLAY_BRICK = getNull();
    @GameRegistry.ObjectHolder("ceramics/fired/clay_brick")
    public static final ItemPottery FIRED_CLAY_BRICK = getNull();
    @GameRegistry.ObjectHolder("ceramics/unfired/mud_brick")
    public static final ItemPottery UNFIRED_MUD_BRICK = getNull();
    @GameRegistry.ObjectHolder("ceramics/fired/mud_brick")
    public static final ItemPottery FIRED_MUD_BRICK = getNull();
    
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
            simpleItems.add(register(r, "ceramics/mud_ball", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "mud"), CT_MISC));
        	simpleItems.add(register(r, "ceramics/unfired/clay_brick", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "brickClayUnfired"), CT_POTTERY));
        	simpleItems.add(register(r, "ceramics/fired/clay_brick", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "brickClay"), CT_POTTERY));
        	simpleItems.add(register(r, "ceramics/unfired/mud_brick", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "brickMudUnfired"), CT_POTTERY));
        	simpleItems.add(register(r, "ceramics/fired/mud_brick", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "brickMud"), CT_POTTERY));
        	simpleItems.add(register(r, "ceramics/unfired/tea_cup", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT), CT_POTTERY));
        	simpleItems.add(register(r, "ceramics/fired/tea_cup", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT), CT_POTTERY));
        	simpleItems.add(register(r, "ceramics/unfired/teapot", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT), CT_POTTERY));
        	simpleItems.add(register(r, "ceramics/fired/teapot", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT), CT_POTTERY));
        }
        
        //Products
        simpleItems.add(register(r, "crop/product/agave", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropAgave"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_boll", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropCotton"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_cloth", new ItemMiscTFCE(Size.SMALL, Weight.LIGHT, "clothHighQuality"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_yarn", new ItemMiscTFCE(Size.SMALL, Weight.VERY_LIGHT, "string"), CT_MISC));
        simpleItems.add(register(r, "crop/product/flax", new ItemMiscTFCE(Size.SMALL, Weight.VERY_LIGHT, "cropFlax"), CT_MISC));
        simpleItems.add(register(r, "crop/product/flax_fiber", new ItemMiscTFCE(Size.SMALL, Weight.VERY_LIGHT, "fiber"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp", new ItemMiscTFCE(Size.SMALL, Weight.VERY_LIGHT, "cropHemp"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_fiber", new ItemMiscTFCE(Size.SMALL, Weight.VERY_LIGHT, "fiber"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_cloth", new ItemMiscTFCE(Size.SMALL, Weight.LIGHT, "clothHighQuality"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_string", new ItemMiscTFCE(Size.SMALL, Weight.VERY_LIGHT, "string"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_fiber", new ItemMiscTFCE(Size.SMALL, Weight.VERY_LIGHT, "fiber"), CT_MISC));
        simpleItems.add(register(r, "crop/product/madder", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropMadder"), CT_MISC));
        simpleItems.add(register(r, "crop/product/weld", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropWeld"), CT_MISC));
        simpleItems.add(register(r, "crop/product/woad", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropWoad"), CT_MISC));
        simpleItems.add(register(r, "crop/product/black_tea", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropTea"), CT_MISC));
        simpleItems.add(register(r, "crop/product/green_tea", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropTea"), CT_MISC));
        simpleItems.add(register(r, "crop/product/white_tea", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropTea"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_black_tea", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropTeaDried"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_green_tea", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropTeaDried"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_white_tea", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropTeaDried"), CT_MISC));
        simpleItems.add(register(r, "crop/product/indigo", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropIndigo"), CT_MISC));
        simpleItems.add(register(r, "crop/product/rape", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropRape"), CT_MISC));
        simpleItems.add(register(r, "crop/product/rape_flower", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropRapeFlower"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sunflower_head", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropSunflowerHead"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_sunflower_head", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropDriedSunflowerHead"), CT_MISC));
        simpleItems.add(register(r, "crop/product/tobacco_leaf", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropTobaccoLeaf"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_tobacco_leaf", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropDriedTobaccoLeaf"), CT_MISC));
        simpleItems.add(register(r, "crop/product/coca_leaf", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropCocaLead"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_coca_leaf", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropDriedCocaLeaf"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cannabis_bud", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropCannabisBud"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_cannabis_bud", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropDriedCannabisBud"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cannabis_leaf", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropCannabisLeaf"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_cannabis_leaf", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropDriedCannabisLeaf"), CT_MISC));
        simpleItems.add(register(r, "crop/product/opium_poppy_bulb", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropOpiumBulb"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_opium_poppy_bulb", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "cropDriedOpiumBulb"), CT_MISC));

        //Malted grains
        simpleItems.add(register(r, "crop/product/malt_barley", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "maltBarley"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_corn", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "maltCorn"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_rice", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "maltRice"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_rye", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "maltRye"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_wheat", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "maltWheat"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_amaranth", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "maltAmaranth"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_buckwheat", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "maltBuckwheat"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_fonio", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "maltFonio"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_millet", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "maltMillet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_quinoa", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "maltQuinoa"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_spelt", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "maltSpelt"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_wild_rice", new ItemMiscTFCE(Size.VERY_SMALL, Weight.VERY_LIGHT, "maltWildRice"), CT_FOOD));
                
        //Powders
        simpleItems.add(register(r, "powder/cast_iron_grit", new ItemMiscHeatableTFCE(Size.SMALL, Weight.VERY_LIGHT, 0.35f, 2000f, "gritCastIron"), CT_MISC));

        //Other
        simpleItems.add(register(r, "crop/product/cinnamon_bark", new ItemMiscTFCE(Size.SMALL, Weight.VERY_LIGHT, "cinnamon"), CT_MISC));
        
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
        item.setRegistryName(MODID, name);
        item.setTranslationKey(MODID + "." + name.replace('/', '.'));
        item.setCreativeTab(ct);
        r.register(item);
        return item;
    }
}