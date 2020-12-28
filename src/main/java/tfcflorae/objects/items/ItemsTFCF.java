package tfcflorae.objects.items;

import java.util.HashMap;
import java.util.Map;
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

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.*;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.blocks.BlockSlabTFC;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.wood.BlockDoorTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.items.ItemMisc;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.ceramics.*;
import net.dries007.tfc.objects.items.food.ItemDynamicBowlFood;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.objects.items.food.ItemSandwich;
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
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.agriculture.Food;
import net.dries007.tfc.util.agriculture.FruitTree;
import net.dries007.tfc.util.Helpers;

import tfcflorae.objects.GemTFCF;
import tfcflorae.objects.PowderTFCF;
import tfcflorae.objects.blocks.*;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.FruitWood.*;
import tfcflorae.objects.items.*;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.objects.items.rock.ItemMud;
import tfcflorae.objects.items.rock.ItemMudBrick;
import tfcflorae.util.agriculture.*;
import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.TFCFlorae;

//import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.CreativeTabsTFC.*;
//import static net.dries007.tfc.util.Helpers;
import static tfcflorae.TFCFlorae.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
@GameRegistry.ObjectHolder(MODID)
public final class ItemsTFCF
{
    // Fruits
    @GameRegistry.ObjectHolder("food/abiu")
    public static final ItemFoodTFCF ABIU = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/amla")
    public static final ItemFoodTFCF AMLA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/apricot")
    public static final ItemFoodTFCF APRICOT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/avocado")
    public static final ItemFoodTFCF AVOCADO = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/bael")
    public static final ItemFoodTFCF BAEL = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/baobab_fruit")
    public static final ItemFoodTFCF BAOBAB_FRUIT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/bay_laurel")
    public static final ItemFoodTFCF BAY_LAUREL = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/ber")
    public static final ItemFoodTFCF BER = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/bergamot")
    public static final ItemFoodTFCF BERGAMOT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/black_cherry")
    public static final ItemFoodTFCF BLACK_CHERRY = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/black_pepper")
    public static final ItemFoodTFCF BLACK_PEPPER = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/blackcurrant")
    public static final ItemFoodTFCF BLACKCURRANT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/blackthorn")
    public static final ItemFoodTFCF BLACKTHORN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/buddha_hand")
    public static final ItemFoodTFCF BUDDHA_HAND = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cacao")
    public static final ItemFoodTFCF CACAO = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cherry_plum")
    public static final ItemFoodTFCF CHERRY_PLUM = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/citron")
    public static final ItemFoodTFCF CITRON = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/coconut")
    public static final ItemFoodTFCF COCONUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/coffea_cherries")
    public static final ItemFoodTFCF COFFEA_CHERRIES = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/crabapple")
    public static final ItemFoodTFCF CRABAPPLE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/damson_plum")
    public static final ItemFoodTFCF DAMSON_PLUM = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/date")
    public static final ItemFoodTFCF DATE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/elder")
    public static final ItemFoodTFCF ELDER = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/fig")
    public static final ItemFoodTFCF FIG = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/finger_lime")
    public static final ItemFoodTFCF FINGER_LIME = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/grapefruit")
    public static final ItemFoodTFCF GRAPEFRUIT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/guava")
    public static final ItemFoodTFCF GUAVA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/hawthorn")
    public static final ItemFoodTFCF HAWTHORN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/ice_cream_bean")
    public static final ItemFoodTFCF ICE_CREAM_BEAN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/jackfruit")
    public static final ItemFoodTFCF JACKFRUIT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/jujube")
    public static final ItemFoodTFCF JUJUBE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/juniper")
    public static final ItemFoodTFCF JUNIPER = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/kaki")
    public static final ItemFoodTFCF KAKI = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/key_lime")
    public static final ItemFoodTFCF KEY_LIME = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/kluwak")
    public static final ItemFoodTFCF KLUWAK = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/kumquat")
    public static final ItemFoodTFCF KUMQUAT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/persion_lime")
    public static final ItemFoodTFCF PERSIAN_LIME = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/longan")
    public static final ItemFoodTFCF LONGAN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/loquat")
    public static final ItemFoodTFCF LOQUAT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/lychee")
    public static final ItemFoodTFCF LYCHEE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/mamey_sapote")
    public static final ItemFoodTFCF MAMEY_SAPOTE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/manderin")
    public static final ItemFoodTFCF MANDERIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/mango")
    public static final ItemFoodTFCF MANGO = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/mangosteen")
    public static final ItemFoodTFCF MANGOSTEEN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/nectarine")
    public static final ItemFoodTFCF NECTARINE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/ohia_ai")
    public static final ItemFoodTFCF OHIA_AI = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/osage_orange")
    public static final ItemFoodTFCF OSAGE_ORANGE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/papaya")
    public static final ItemFoodTFCF PAPAYA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/passion_fruit")
    public static final ItemFoodTFCF PASSION_FRUIT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pear")
    public static final ItemFoodTFCF PEAR = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/persimmon")
    public static final ItemFoodTFCF PERSIMMON = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/peruvian_pepper")
    public static final ItemFoodTFCF PERUVIAN_PEPPER = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pink_ivory_drupe")
    public static final ItemFoodTFCF PINK_IVORY_DRUPE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/plantain")
    public static final ItemFoodTFCF PLANTAIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pomegranate")
    public static final ItemFoodTFCF POMEGRANATE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pomelo")
    public static final ItemFoodTFCF POMELO = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/quince")
    public static final ItemFoodTFCF QUINCE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/rainier_cherry")
    public static final ItemFoodTFCF RAINIER_CHERRY = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/red_banana")
    public static final ItemFoodTFCF RED_BANANA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/red_currant")
    public static final ItemFoodTFCF RED_CURRANT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/riberry")
    public static final ItemFoodTFCF RIBERRY = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/rowan_Berry")
    public static final ItemFoodTFCF ROWAN_BERRY = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/sand_pear")
    public static final ItemFoodTFCF SAND_PEAR = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/sapodilla")
    public static final ItemFoodTFCF SAPODILLA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/satsuma")
    public static final ItemFoodTFCF SATSUMA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/sky_fruit")
    public static final ItemFoodTFCF SKY_FRUIT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/sour_cherry")
    public static final ItemFoodTFCF SOUR_CHERRY = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/soursop")
    public static final ItemFoodTFCF SOURSOP = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/starfruit")
    public static final ItemFoodTFCF STARFRUIT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/tamarillo")
    public static final ItemFoodTFCF TAMARILLO = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/tangerine")
    public static final ItemFoodTFCF TANGERINE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/tropical_apricot")
    public static final ItemFoodTFCF TROPICAL_APRICOT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/wild_cherry")
    public static final ItemFoodTFCF WILD_CHERRY = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/yew_berry")
    public static final ItemFoodTFCF YEW_BERRY = Helpers.getNull();

    // Nuts
    @GameRegistry.ObjectHolder("food/acorn")
    public static final ItemFoodTFCF ACORN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/acorn_nut")
    public static final ItemFoodTFCF ACORN_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/almond")
    public static final ItemFoodTFCF ALMOND = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/almond_nut")
    public static final ItemFoodTFCF ALMOND_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/beechnut")
    public static final ItemFoodTFCF BEECHNUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/beechnut_nut")
    public static final ItemFoodTFCF BEECHNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/black_walnut")
    public static final ItemFoodTFCF BLACK_WALNUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/black_walnut_nut")
    public static final ItemFoodTFCF BLACK_WALNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/brazil_nut")
    public static final ItemFoodTFCF BRAZIL_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/brazil_nut_nut")
    public static final ItemFoodTFCF BRAZIL_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/breadnut")
    public static final ItemFoodTFCF BREADNUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/breadnut_nut")
    public static final ItemFoodTFCF BREADNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/bunya_nut")
    public static final ItemFoodTFCF BUNYA_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/bunya_nut_nut")
    public static final ItemFoodTFCF BUNYA_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/butternut")
    public static final ItemFoodTFCF BUTTERNUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/butternut_nut")
    public static final ItemFoodTFCF BUTTERNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/candlenut")
    public static final ItemFoodTFCF CANDLENUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/candlenut_nut")
    public static final ItemFoodTFCF CANDLENUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cashew")
    public static final ItemFoodTFCF CASHEW = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cashew_nut")
    public static final ItemFoodTFCF CASHEW_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/chestnut")
    public static final ItemFoodTFCF CHESTNUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/chestnut_nut")
    public static final ItemFoodTFCF CHESTNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/ginkgo_nut")
    public static final ItemFoodTFCF GINKGO_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/ginkgo_nut_nut")
    public static final ItemFoodTFCF GINKGO_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/hazelnut")
    public static final ItemFoodTFCF HAZELNUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/hazelnut_nut")
    public static final ItemFoodTFCF HAZELNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/heartnut")
    public static final ItemFoodTFCF HEARTNUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/heartnut_nut")
    public static final ItemFoodTFCF HEARTNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/hickory_nut")
    public static final ItemFoodTFCF HICKORY_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/hickory_nut_nut")
    public static final ItemFoodTFCF HICKORY_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/kola_nut")
    public static final ItemFoodTFCF KOLA_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/kola_nut_nut")
    public static final ItemFoodTFCF KOLA_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/kukui_nut")
    public static final ItemFoodTFCF KUKUI_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/kukui_nut_nut")
    public static final ItemFoodTFCF KUKUI_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/macadamia")
    public static final ItemFoodTFCF MACADAMIA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/macadamia_nut")
    public static final ItemFoodTFCF MACADAMIA_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/mongongo")
    public static final ItemFoodTFCF MONGONGO = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/mongongo_nut")
    public static final ItemFoodTFCF MONGONGO_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/monkey_puzzle_nut")
    public static final ItemFoodTFCF MONKEY_PUZZLE_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/monkey_puzzle_nut_nut")
    public static final ItemFoodTFCF MONKEY_PUZZLE_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/nutmeg")
    public static final ItemFoodTFCF NUTMEG = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/nutmeg_nut")
    public static final ItemFoodTFCF NUTMEG_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/paradise_nut")
    public static final ItemFoodTFCF PARADISE_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/paradise_nut_nut")
    public static final ItemFoodTFCF PARADISE_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pecan")
    public static final ItemFoodTFCF PECAN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pecan_nut")
    public static final ItemFoodTFCF PECAN_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pinecone")
    public static final ItemFoodTFCF PINECONE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pine_nut")
    public static final ItemFoodTFCF PINE_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pistachio")
    public static final ItemFoodTFCF PISTACHIO = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pistachio_nut")
    public static final ItemFoodTFCF PISTACHIO_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/walnut")
    public static final ItemFoodTFCF WALNUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/walnut_nut")
    public static final ItemFoodTFCF WALNUT_NUT = Helpers.getNull();
    
    // Processed Nuts
    @GameRegistry.ObjectHolder("food/roasted/acorn_nut")
    public static final ItemFoodTFCF ROASTED_ACORN_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/almond_nut")
    public static final ItemFoodTFCF ROASTED_ALMOND_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/beechnut_nut")
    public static final ItemFoodTFCF ROASTED_BEECHNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/black_walnut_nut")
    public static final ItemFoodTFCF ROASTED_BLACK_WALNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/brazil_nut_nut")
    public static final ItemFoodTFCF ROASTED_BRAZIL_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/breadnut_nut")
    public static final ItemFoodTFCF ROASTED_BREADNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/bunya_nut_nut")
    public static final ItemFoodTFCF ROASTED_BUNYA_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/butternut_nut")
    public static final ItemFoodTFCF ROASTED_BUTTERNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/candlenut_nut")
    public static final ItemFoodTFCF ROASTED_CANDLENUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/cashew_nut")
    public static final ItemFoodTFCF ROASTED_CASHEW_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/chestnut_nut")
    public static final ItemFoodTFCF ROASTED_CHESTNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/ginkgo_nut_nut")
    public static final ItemFoodTFCF ROASTED_GINKGO_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/hazelnut_nut")
    public static final ItemFoodTFCF ROASTED_HAZELNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/heartnut_nut")
    public static final ItemFoodTFCF ROASTED_HEARTNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/hickory_nut_nut")
    public static final ItemFoodTFCF ROASTED_HICKORY_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/kola_nut_nut")
    public static final ItemFoodTFCF ROASTED_KOLA_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/kukui_nut_nut")
    public static final ItemFoodTFCF ROASTED_KUKUI_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/macadamia_nut")
    public static final ItemFoodTFCF ROASTED_MACADAMIA_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/mongongo_nut")
    public static final ItemFoodTFCF ROASTED_MONGONGO_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/monkey_puzzle_nut_nut")
    public static final ItemFoodTFCF ROASTED_MONKEY_PUZZLE_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/nutmeg_nut")
    public static final ItemFoodTFCF ROASTED_NUTMEG_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/paradise_nut_nut")
    public static final ItemFoodTFCF ROASTED_PARADISE_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/pecan_nut")
    public static final ItemFoodTFCF ROASTED_PECAN_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/pine_nut")
    public static final ItemFoodTFCF ROASTED_PINE_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/pistachio_nut")
    public static final ItemFoodTFCF ROASTED_PISTACHIO_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/walnut_nut")
    public static final ItemFoodTFCF ROASTED_WALNUT_NUT = Helpers.getNull();

    // Foods
    @GameRegistry.ObjectHolder("food/allspice")
    public static final ItemFoodTFCF ALLSPICE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/clove")
    public static final ItemFoodTFCF CLOVE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/curry_leaf")
    public static final ItemFoodTFCF CURRY_LEAF = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/star_anise")
    public static final ItemFoodTFCF STAR_ANISE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/liquorice_root")
    public static final ItemFoodTFCF LIQUORICE_ROOT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cassia_cinnamon_bark")
    public static final ItemFoodTFCF CASSIA_CINNAMON_BARK = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cassia_cinnamon_bark")
    public static final ItemFoodTFCF GROUND_CASSIA_CINNAMON = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/ceylon_cinnamon_bark")
    public static final ItemFoodTFCF CEYLON_CINNAMON_BARK = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/ceylon_cinnamon_bark")
    public static final ItemFoodTFCF GROUND_CEYLON_CINNAMON = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/black_tea")
    public static final ItemFoodTFCF BLACK_TEA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/dried/black_tea")
    public static final ItemFoodTFCF DRIED_BLACK_TEA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/green_tea")
    public static final ItemFoodTFCF GREEN_TEA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/dried/green_tea")
    public static final ItemFoodTFCF DRIED_GREEN_TEA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/white_tea")
    public static final ItemFoodTFCF WHITE_TEA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/dried/white_tea")
    public static final ItemFoodTFCF DRIED_WHITE_TEA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cannabis_bud")
    public static final ItemFoodTFCF CANNABIS_BUD = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/dried/cannabis_bud")
    public static final ItemFoodTFCF DRIED_CANNABIS_BUD = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cannabis_leaf")
    public static final ItemFoodTFCF CANNABIS_LEAF = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/dried/cannabis_leaf")
    public static final ItemFoodTFCF DRIED_CANNABIS_LEAF = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/coca_leaf")
    public static final ItemFoodTFCF COCA_LEAF = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/dried/coca_leaf")
    public static final ItemFoodTFCF DRIED_COCA_LEAF = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/opium_poppy_bulb")
    public static final ItemFoodTFCF OPIUM_POPPY_BULB = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/dried/opium_poppy_bulb")
    public static final ItemFoodTFCF DRIED_OPIUM_POPPY_BULB = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/peyote")
    public static final ItemFoodTFCF PEYOTE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/dried/peyote")
    public static final ItemFoodTFCF DRIED_PEYOTE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/tobacco_leaf")
    public static final ItemFoodTFCF TOBACCO_LEAF = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/dried/tobacco_leaf")
    public static final ItemFoodTFCF DRIED_TOBACCO_LEAF = Helpers.getNull();

    // Miscellaneous Food Stuff
    @GameRegistry.ObjectHolder("food/cocoa_beans")
    public static final ItemFoodTFCF COCOA_BEANS = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/dried/cocoa_beans")
    public static final ItemFoodTFCF DRIED_COCOA_BEANS = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/cocoa_beans")
    public static final ItemFoodTFCF ROASTED_COCOA_BEANS = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cocoa_powder")
    public static final ItemFoodTFCF COCOA_POWDER = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cocoa_butter")
    public static final ItemFoodTFCF COCOA_BUTTER = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/chocolate")
    public static final ItemFoodTFCF CHOCOLATE = Helpers.getNull();
    @GameRegistry.ObjectHolder("cracked_coconut")
    public static final ItemWoodenBucket CRACKED_COCONUT = Helpers.getNull();
    /*
    @GameRegistry.ObjectHolder("food/dried/coffea_cherries")
    public static final ItemFoodTFCF DRIED_COFFEA_CHERRIES = Helpers.getNull();
    */
    @GameRegistry.ObjectHolder("food/roasted/coffee_beans")
    public static final ItemFoodTFCF ROASTED_COFFEE_BEANS = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/coffee_powder")
    public static final ItemFoodTFCF COFFEE_POWDER = Helpers.getNull();
    /*
    @GameRegistry.ObjectHolder("food/dried/black_pepper")
    public static final ItemFoodTFCF DRIED_BLACK_PEPPER = Helpers.getNull();
    */
    @GameRegistry.ObjectHolder("food/ground_black_pepper")
    public static final ItemFoodTFCF GROUND_BLACK_PEPPER = Helpers.getNull();
    @GameRegistry.ObjectHolder("peel")
    public static final ItemMiscTFCF PEEL = Helpers.getNull();
    @GameRegistry.ObjectHolder("fruit_leaf")
    public static final ItemMiscTFCF FRUIT_LEAF = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pumpkin")
    public static final ItemFoodTFCF PUMPKIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pumpkin_scooped")
    public static final ItemFoodTFCF PUMPKIN_SCOOPED = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pumpkin_chunks")
    public static final ItemFoodTFCF PUMPKIN_CHUNKS = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/melon")
    public static final ItemFoodTFCF MELON = Helpers.getNull();

    // Fish Foods
    @GameRegistry.ObjectHolder("food/raw_eel")
    public static final ItemFoodTFCF RAW_EEL = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cooked_eel")
    public static final ItemFoodTFCF COOKED_EEL = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/raw_crab")
    public static final ItemFoodTFCF RAW_CRAB = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cooked_crab")
    public static final ItemFoodTFCF COOKED_CRAB = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/raw_clam")
    public static final ItemFoodTFCF RAW_CLAM = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cooked_clam")
    public static final ItemFoodTFCF COOKED_CLAM = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/raw_scallop")
    public static final ItemFoodTFCF RAW_SCALLOP = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cooked_scallop")
    public static final ItemFoodTFCF COOKED_SCALLOP = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/raw_starfish")
    public static final ItemFoodTFCF RAW_STARFISH = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cooked_starfish")
    public static final ItemFoodTFCF COOKED_STARFISH = Helpers.getNull();
    
    // Normal foods
    @GameRegistry.ObjectHolder("food/amaranth")
    public static final ItemFoodTFCF AMARANTH = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/amaranth_grain")
    public static final ItemFoodTFCF AMARANTH_GRAIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/amaranth_flour")
    public static final ItemFoodTFCF AMARANTH_FLOUR = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/amaranth_dough")
    public static final ItemFoodTFCF AMARANTH_DOUGH = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/amaranth_bread")
    public static final ItemFoodTFCF AMARANTH_BREAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/buckwheat")
    public static final ItemFoodTFCF BUCKWHEAT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/buckwheat_grain")
    public static final ItemFoodTFCF BUCKWHEAT_GRAIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/buckwheat_flour")
    public static final ItemFoodTFCF BUCKWHEAT_FLOUR = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/buckwheat_dough")
    public static final ItemFoodTFCF BUCKWHEAT_DOUGH = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/buckwheat_bread")
    public static final ItemFoodTFCF BUCKWHEAT_BREAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/fonio")
    public static final ItemFoodTFCF FONIO = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/fonio_grain")
    public static final ItemFoodTFCF FONIO_GRAIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/fonio_flour")
    public static final ItemFoodTFCF FONIO_FLOUR = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/fonio_dough")
    public static final ItemFoodTFCF FONIO_DOUGH = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/fonio_bread")
    public static final ItemFoodTFCF FONIO_BREAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/millet")
    public static final ItemFoodTFCF MILLET = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/millet_grain")
    public static final ItemFoodTFCF MILLET_GRAIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/millet_flour")
    public static final ItemFoodTFCF MILLET_FLOUR = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/millet_dough")
    public static final ItemFoodTFCF MILLET_DOUGH = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/millet_bread")
    public static final ItemFoodTFCF MILLET_BREAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/quinoa")
    public static final ItemFoodTFCF QUINOA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/quinoa_grain")
    public static final ItemFoodTFCF QUINOA_GRAIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/quinoa_flour")
    public static final ItemFoodTFCF QUINOA_FLOUR = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/quinoa_dough")
    public static final ItemFoodTFCF QUINOA_DOUGH = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/quinoa_bread")
    public static final ItemFoodTFCF QUINOA_BREAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/spelt")
    public static final ItemFoodTFCF SPELT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/spelt_grain")
    public static final ItemFoodTFCF SPELT_GRAIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/spelt_flour")
    public static final ItemFoodTFCF SPELT_FLOUR = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/spelt_dough")
    public static final ItemFoodTFCF SPELT_DOUGH = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/spelt_bread")
    public static final ItemFoodTFCF SPELT_BREAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/wild_rice")
    public static final ItemFoodTFCF WILD_RICE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/wild_rice_grain")
    public static final ItemFoodTFCF WILD_RICE_GRAIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/wild_rice_flour")
    public static final ItemFoodTFCF WILD_RICE_FLOUR = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/wild_rice_dough")
    public static final ItemFoodTFCF WILD_RICE_DOUGH = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/wild_rice_bread")
    public static final ItemFoodTFCF WILD_RICE_BREAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/linseed")
    public static final ItemFoodTFCF LINSEED = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/rape_seed")
    public static final ItemFoodTFCF RAPE_SEED = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/sunflower_seed")
    public static final ItemFoodTFCF SUNFLOWER_SEED = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/opium_poppy_seed")
    public static final ItemFoodTFCF OPIUM_POPPY_SEED = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/hash_muffin")
    public static final ItemFoodTFCF HASH_MUFFIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/rutabaga")
    public static final ItemFoodTFCF RUTABAGA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/turnip")
    public static final ItemFoodTFCF TURNIP = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/mustard")
    public static final ItemFoodTFCF MUSTARD = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/black_eyed_peas")
    public static final ItemFoodTFCF BLACK_EYED_PEAS = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/green_cayenne_pepper")
    public static final ItemFoodTFCF GREEN_CAYENNE_PEPPER = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/red_cayenne_pepper")
    public static final ItemFoodTFCF RED_CAYENNE_PEPPER = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/ginger")
    public static final ItemFoodTFCF GINGER = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/ginseng")
    public static final ItemFoodTFCF GINSENG = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/celery")
    public static final ItemFoodTFCF CELERY  = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/lettuce")
    public static final ItemFoodTFCF LETTUCE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/peanut")
    public static final ItemFoodTFCF PEANUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/sweet_potato")
    public static final ItemFoodTFCF SWEET_POTATO = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/sugar_beet")
    public static final ItemFoodTFCF SUGAR_BEET = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/grapes")
    public static final ItemFoodTFCF GRAPES = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/linseed_paste")
    public static final ItemFoodTFCF LINSEED_PASTE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/rape_seed_paste")
    public static final ItemFoodTFCF RAPE_SEED_PASTE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/sunflower_seed_paste")
    public static final ItemFoodTFCF SUNFLOWER_SEED_PASTE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pumpkin_chunks")
    public static final ItemFoodTFCF OPIUM_POPPY_SEED_PASTE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/mashed_sugar_beet")
    public static final ItemFoodTFCF MASHED_SUGAR_BEET = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/mashed_sugar_cane")
    public static final ItemFoodTFCF MASHED_SUGAR_CANE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/soybean_paste")
    public static final ItemFoodTFCF SOYBEAN_PASTE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cow_cheese")
    public static final ItemFoodTFCF COW_CHEESE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/goat_cheese")
    public static final ItemFoodTFCF GOAT_CHEESE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/sheep_cheese")
    public static final ItemFoodTFCF SHEEP_CHEESE = Helpers.getNull();

    /*
    @GameRegistry.ObjectHolder("ceramics/pot_calcite")
    public static final ItemPottery CALCITE_POT = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/pot_quicklime")
    public static final ItemPottery QUICKLIME_POT = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/pot_slaked_lime")
    public static final ItemPottery SLAKED_LIME_POT = Helpers.getNull();
    */

    @GameRegistry.ObjectHolder("items/logwood_chips")
    public static final ItemMiscTFCF LOGWOOD_CHIPS = Helpers.getNull();
    @GameRegistry.ObjectHolder("items/resin")
    public static final ItemMiscTFCF RESIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("items/twig")
    public static final ItemMiscTFCF TWIG = Helpers.getNull();
    @GameRegistry.ObjectHolder("items/twig_leaves")
    public static final ItemMiscTFCF TWIG_LEAVES = Helpers.getNull();
    @GameRegistry.ObjectHolder("items/charred_bones")
    public static final ItemMiscTFCF CHARRED_BONES = Helpers.getNull();
    @GameRegistry.ObjectHolder("items/conch")
    public static final ItemMiscTFCF CONCH = Helpers.getNull();
    @GameRegistry.ObjectHolder("items/clam")
    public static final ItemMiscTFCF CLAM = Helpers.getNull();
    @GameRegistry.ObjectHolder("items/live_clam")
    public static final ItemMiscTFCF LIVE_CLAM = Helpers.getNull();
    @GameRegistry.ObjectHolder("items/scallop")
    public static final ItemMiscTFCF SCALLOP = Helpers.getNull();
    @GameRegistry.ObjectHolder("items/live_scallop")
    public static final ItemMiscTFCF LIVE_SCALLOP = Helpers.getNull();
    @GameRegistry.ObjectHolder("items/pearl")
    public static final ItemMiscTFCF PEARL = Helpers.getNull();
    @GameRegistry.ObjectHolder("items/black_pearl")
    public static final ItemMiscTFCF BLACK_PEARL = Helpers.getNull();
    @GameRegistry.ObjectHolder("items/live_starfish")
    public static final ItemMiscTFCF LIVE_STARFISH = Helpers.getNull();
    
    @GameRegistry.ObjectHolder("crop/product/papyrus_pulp")
    public static final ItemMiscTFCF PAPYRUS_PULP = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/papyrus_fiber")
    public static final ItemMiscTFCF PAPYRUS_FIBER = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/papyrus_paper")
    public static final ItemMiscTFCF PAPYRUS_PAPER = Helpers.getNull();
    
    @GameRegistry.ObjectHolder("crop/product/agave")
    public static final ItemMiscTFCF AGAVE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sisal_fiber")
    public static final ItemMiscTFCF SISAL_FIBER = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sisal_string")
    public static final ItemMiscTFCF SISAL_STRING = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sisal_cloth")
    public static final ItemMiscTFCF SISAL_CLOTH = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_boll")
    public static final ItemMiscTFCF COTTON_BOLL = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_yarn")
    public static final ItemMiscTFCF COTTON_YARN = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_cloth")
    public static final ItemMiscTFCF COTTON_CLOTH = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/flax")
    public static final ItemMiscTFCF FLAX = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/flax_fiber")
    public static final ItemMiscTFCF FLAX_FIBER = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/linen_string")
    public static final ItemMiscTFCF LINEN_STRING = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/linen_cloth")
    public static final ItemMiscTFCF LINEN_CLOTH = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp")
    public static final ItemMiscTFCF HEMP = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp_fiber")
    public static final ItemMiscTFCF HEMP_FIBER = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp_string")
    public static final ItemMiscTFCF HEMP_STRING = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp_cloth")
    public static final ItemMiscTFCF HEMP_CLOTH = Helpers.getNull();

    @GameRegistry.ObjectHolder("crop/product/silk_disc")
    public static final Item SILK_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sisal_disc")
    public static final Item SISAL_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_disc")
    public static final Item COTTON_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/linen_disc")
    public static final Item LINEN_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/papyrus_disc")
    public static final Item PAPYRUS_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp_disc")
    public static final Item HEMP_DISC = Helpers.getNull();

    @GameRegistry.ObjectHolder("crop/product/soybean_jute_disc")
    public static final Item SOYBEAN_JUTE_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/soybean_silk_disc")
    public static final Item SOYBEAN_SILK_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/soybean_sisal_disc")
    public static final Item SOYBEAN_SISAL_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/soybean_cotton_disc")
    public static final Item SOYBEAN_COTTON_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/soybean_linen_disc")
    public static final Item SOYBEAN_LINEN_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/soybean_papyrus_disc")
    public static final Item SOYBEAN_PAPYRUS_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/soybean_hemp_disc")
    public static final Item SOYBEAN_HEMP_DISC = Helpers.getNull();

    @GameRegistry.ObjectHolder("crop/product/linseed_jute_disc")
    public static final Item LINSEED_JUTE_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/linseed_silk_disc")
    public static final Item LINSEED_SILK_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/linseed_sisal_disc")
    public static final Item LINSEED_SISAL_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/linseed_cotton_disc")
    public static final Item LINSEED_COTTON_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/linseed_linen_disc")
    public static final Item LINSEED_LINEN_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/linseed_papyrus_disc")
    public static final Item LINSEED_PAPYRUS_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/linseed_hemp_disc")
    public static final Item LINSEED_HEMP_DISC = Helpers.getNull();

    @GameRegistry.ObjectHolder("crop/product/rape_seed_jute_disc")
    public static final Item RAPE_SEED_JUTE_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/rape_seed_silk_disc")
    public static final Item RAPE_SEED_SILK_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/rape_seed_sisal_disc")
    public static final Item RAPE_SEED_SISAL_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/rape_seed_cotton_disc")
    public static final Item RAPE_SEED_COTTON_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/rape_seed_linen_disc")
    public static final Item RAPE_SEED_LINEN_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/rape_seed_papyrus_disc")
    public static final Item RAPE_SEED_PAPYRUS_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/rape_seed_hemp_disc")
    public static final Item RAPE_SEED_HEMP_DISC = Helpers.getNull();

    @GameRegistry.ObjectHolder("crop/product/sunflower_seed_jute_disc")
    public static final Item SUNFLOWER_SEED_JUTE_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sunflower_seed_silk_disc")
    public static final Item SUNFLOWER_SEED_SILK_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sunflower_seed_sisal_disc")
    public static final Item SUNFLOWER_SEED_SISAL_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sunflower_seed_cotton_disc")
    public static final Item SUNFLOWER_SEED_COTTON_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sunflower_seed_linen_disc")
    public static final Item SUNFLOWER_SEED_LINEN_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sunflower_seed_papyrus_disc")
    public static final Item SUNFLOWER_SEED_PAPYRUS_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sunflower_seed_hemp_disc")
    public static final Item SUNFLOWER_SEED_HEMP_DISC = Helpers.getNull();

    @GameRegistry.ObjectHolder("crop/product/opium_poppy_seed_jute_disc")
    public static final Item OPIUM_POPPY_SEED_JUTE_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/opium_poppy_seed_silk_disc")
    public static final Item OPIUM_POPPY_SEED_SILK_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/opium_poppy_seed_sisal_disc")
    public static final Item OPIUM_POPPY_SEED_SISAL_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/opium_poppy_seed_cotton_disc")
    public static final Item OPIUM_POPPY_SEED_COTTON_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/opium_poppy_seed_linen_disc")
    public static final Item OPIUM_POPPY_SEED_LINEN_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/opium_poppy_seed_papyrus_disc")
    public static final Item OPIUM_POPPY_SEED_PAPYRUS_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/opium_poppy_seed_hemp_disc")
    public static final Item OPIUM_POPPY_SEED_HEMP_DISC = Helpers.getNull();

    @GameRegistry.ObjectHolder("crop/product/sugar_beet_jute_disc")
    public static final Item SUGAR_BEET_JUTE_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_beet_silk_disc")
    public static final Item SUGAR_BEET_SILK_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_beet_sisal_disc")
    public static final Item SUGAR_BEET_SISAL_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_beet_cotton_disc")
    public static final Item SUGAR_BEET_COTTON_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_beet_linen_disc")
    public static final Item SUGAR_BEET_LINEN_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_beet_papyrus_disc")
    public static final Item SUGAR_BEET_PAPYRUS_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_beet_hemp_disc")
    public static final Item SUGAR_BEET_HEMP_DISC = Helpers.getNull();

    @GameRegistry.ObjectHolder("crop/product/sugar_cane_jute_disc")
    public static final Item SUGAR_CANE_JUTE_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_cane_silk_disc")
    public static final Item SUGAR_CANE_SILK_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_cane_sisal_disc")
    public static final Item SUGAR_CANE_SISAL_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_cane_cotton_disc")
    public static final Item SUGAR_CANE_COTTON_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_cane_linen_disc")
    public static final Item SUGAR_CANE_LINEN_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_cane_papyrus_disc")
    public static final Item SUGAR_CANE_PAPYRUS_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_cane_hemp_disc")
    public static final Item SUGAR_CANE_HEMP_DISC = Helpers.getNull();


    @GameRegistry.ObjectHolder("crop/product/olive_silk_disc")
    public static final Item OLIVE_SILK_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/olive_sisal_disc")
    public static final Item OLIVE_SISAL_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/olive_cotton_disc")
    public static final Item OLIVE_COTTON_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/olive_linen_disc")
    public static final Item OLIVE_LINEN_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/olive_papyrus_disc")
    public static final Item OLIVE_PAPYRUS_DISC = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/olive_hemp_disc")
    public static final Item OLIVE_HEMP_DISC = Helpers.getNull();

    @GameRegistry.ObjectHolder("crop/product/silk_net")
    public static final Item SILK_NET = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sisal_net")
    public static final Item SISAL_NET = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_net")
    public static final Item COTTON_NET = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/linen_net")
    public static final Item LINEN_NET = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/papyrus_net")
    public static final Item PAPYRUS_NET = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp_net")
    public static final Item HEMP_NET = Helpers.getNull();

    @GameRegistry.ObjectHolder("crop/product/dirty_silk_net")
    public static final Item DIRTY_SILK_NET = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/dirty_sisal_net")
    public static final Item DIRTY_SISAL_NET = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/dirty_cotton_net")
    public static final Item DIRTY_COTTON_NET = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/dirty_linen_net")
    public static final Item DIRTY_LINEN_NET = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/dirty_papyrus_net")
    public static final Item DIRTY_PAPYRUS_NET = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/dirty_hemp_net")
    public static final Item DIRTY_HEMP_NET = Helpers.getNull();
    
    @GameRegistry.ObjectHolder("crop/product/indigo")
    public static final ItemMiscTFCF INDIGO = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/madder")
    public static final ItemMiscTFCF MADDER = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/weld")
    public static final ItemMiscTFCF WELD = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/woad")
    public static final ItemMiscTFCF WOAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/hops")
    public static final ItemMiscTFCF HOPS = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/rape")
    public static final ItemMiscTFCF RAPE = Helpers.getNull();
    
    @GameRegistry.ObjectHolder("crop/product/chamomile_head")
    public static final ItemMiscTFCF CHAMOMILE_HEAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/dried/chamomile_head")
    public static final ItemMiscTFCF DRIED_CHAMOMILE_HEAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/dandelion_head")
    public static final ItemMiscTFCF DANDELION_HEAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/dried/dandelion_head")
    public static final ItemMiscTFCF DRIED_DANDELION_HEAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/labrador_tea_head")
    public static final ItemMiscTFCF LABRADOR_TEA_HEAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/dried/labrador_tea_head")
    public static final ItemMiscTFCF DRIED_LABRADOR_TEA_HEAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/sunflower_head")
    public static final ItemMiscTFCF SUNFLOWER_HEAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/dried/sunflower_head")
    public static final ItemMiscTFCF DRIED_SUNFLOWER_HEAD = Helpers.getNull();

    @GameRegistry.ObjectHolder("crop/product/malt_barley")
    public static final ItemMiscTFCF MALT_BARLEY = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_corn")
    public static final ItemMiscTFCF MALT_CORN = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_rice")
    public static final ItemMiscTFCF MALT_RICE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_rye")
    public static final ItemMiscTFCF MALT_RYE = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_wheat")
    public static final ItemMiscTFCF MALT_WHEAT = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_amaranth")
    public static final ItemMiscTFCF MALT_AMARANTH = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_buckwheat")
    public static final ItemMiscTFCF MALT_BUCKWHEAT = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_fonio")
    public static final ItemMiscTFCF MALT_FONIO = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_millet")
    public static final ItemMiscTFCF MALT_MILLET = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_quinoa")
    public static final ItemMiscTFCF MALT_QUINOA = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_spelt")
    public static final ItemMiscTFCF MALT_SPELT = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_wild_rice")
    public static final ItemMiscTFCF MALT_WILD_RICE = Helpers.getNull();

    @GameRegistry.ObjectHolder("wood/fruit_tree/pole/cassia_cinnamon")
    public static final ItemMisc CASSIA_CINNAMON_POLE = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/fruit_tree/lumber/cassia_cinnamon")
    public static final ItemMisc CASSIA_CINNAMON_LUMBER = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/fruit_tree/pole/ceylon_cinnamon")
    public static final ItemMisc CEYLON_CINNAMON_POLE = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/fruit_tree/lumber/ceylon_cinnamon")
    public static final ItemMisc CEYLON_CINNAMON_LUMBER = Helpers.getNull();

    private static ImmutableList<Item> allSimpleItems;
    private static ImmutableList<Item> allFoodItems;
    private static ImmutableList<ItemGemTFCF> allGemTFCFItems;

    //private static ImmutableList<ItemFruitButton> allFruitButton;
    private static ImmutableList<ItemFruitDoor> allFruitDoors;
    //private static ImmutableList<ItemFruitPressurePlate> allFruitPressurePlate;
    

    public static ImmutableList<Item> getAllSimpleItems()
    {
        return allSimpleItems;
    }

    public static ImmutableList<Item> getAllFoodItems()
    {
        return allFoodItems;
    }

    public static ImmutableList<ItemGemTFCF> getAllGemTFCFItems()
    {
        return allGemTFCFItems;
    }

    /*
    public static ImmutableList<ItemFruitButton> getAllFruitButton() 
    { 
        return allFruitButton; 
    }
    */

    public static ImmutableList<ItemFruitDoor> getAllFruitDoors() 
    { 
        return allFruitDoors; 
    }

    /*
    public static ImmutableList<ItemFruitPressurePlate> getAllFruitPressurePlate() 
    { 
        return allFruitPressurePlate; 
    }
    */

    private static Map<FruitsTFCF, Item> driedFruits = new HashMap<>();

    public static Item getDriedFruit(FruitsTFCF fruit)
    {
        return driedFruits.get(fruit);
    }
    
    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> r = event.getRegistry();
        ImmutableList.Builder<Item> simpleItems = ImmutableList.builder();
        //ImmutableList.Builder<ItemFruitButton> fruitButton = ImmutableList.builder();
        ImmutableList.Builder<ItemFruitDoor> fruitDoors = ImmutableList.builder();
        //ImmutableList.Builder<ItemFruitPressurePlate> fruitPressurePlate = ImmutableList.builder();
        
        // Rock Type Items
        {
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                simpleItems.add(register(r, "mud_item/" + rock.getRegistryName().getPath().toLowerCase(), new ItemMud(rock), CT_ROCK_ITEMS));
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                simpleItems.add(register(r, "mud_brick_item/" + rock.getRegistryName().getPath().toLowerCase(), new ItemMudBrick(rock), CT_ROCK_ITEMS));
        }

        // Gems
        {
            Builder<ItemGemTFCF> b = new Builder<>();
            for (GemTFCF gem : GemTFCF.values())
                b.add(register(r, "gem/" + gem.name().toLowerCase(), new ItemGemTFCF(gem), CT_GEMS));
            allGemTFCFItems = b.build();
        }
        
        for (PowderTFCF powder : PowderTFCF.values())
            simpleItems.add(register(r, "powder/" + powder.name().toLowerCase(), new ItemPowderTFCF(powder), CT_MISC));

        /*
        simpleItems.add(register(r, "ceramics/pot_calcite", new ItemPottery(), CT_MISC));
        simpleItems.add(register(r, "ceramics/pot_quicklime", new ItemPottery()
        {
            @Nonnull
            @Override
            public ItemStack getContainerItem(@Nonnull ItemStack itemStack)
            {
                return new ItemStack(ItemsTFC.FIRED_POT);
            }

            @Override
            public boolean hasContainerItem(@Nonnull ItemStack stack)
            {
                return true;
            }
        }, CT_MISC));
        
        simpleItems.add(register(r, "ceramics/pot_slaked_lime", new ItemPottery()
        {
            @Nonnull
            @Override
            public ItemStack getContainerItem(@Nonnull ItemStack itemStack)
            {
                return new ItemStack(ItemsTFC.FIRED_POT);
            }

            @Override
            public boolean hasContainerItem(@Nonnull ItemStack stack)
            {
                return true;
            }
        }, CT_MISC));
        */

        // Foods
        simpleItems.add(register(r, "food/cocoa_beans", new ItemFoodTFCF(FoodDataTFCF.COCOA_BEANS), CT_FOOD));
        simpleItems.add(register(r, "food/dried/cocoa_beans", new ItemFoodTFCF(FoodDataTFCF.DRIED_COCOA_BEANS), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/cocoa_beans", new ItemFoodTFCF(FoodDataTFCF.ROASTED_COCOA_BEANS), CT_FOOD));
        simpleItems.add(register(r, "food/cocoa_powder", new ItemFoodTFCF(FoodDataTFCF.COCOA_POWDER), CT_FOOD));
        simpleItems.add(register(r, "food/cocoa_butter", new ItemFoodTFCF(FoodDataTFCF.COCOA_BUTTER), CT_FOOD));
        simpleItems.add(register(r, "food/coffea_cherries", new ItemFoodTFCF(FoodDataTFCF.COFFEA_CHERRIES), CT_FOOD));
        //simpleItems.add(register(r, "food/dried/coffea_cherries", new ItemFoodTFCF(FoodDataTFCF.DRIED_COFFEA_CHERRIES), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/coffee_beans", new ItemFoodTFCF(FoodDataTFCF.ROASTED_COFFEE_BEANS), CT_FOOD));
        simpleItems.add(register(r, "food/dark_chocolate", new ItemFoodTFCF(FoodDataTFCF.CHOCOLATE), CT_FOOD));
        simpleItems.add(register(r, "food/milk_chocolate", new ItemFoodTFCF(FoodDataTFCF.CHOCOLATE), CT_FOOD));
        simpleItems.add(register(r, "food/white_chocolate", new ItemFoodTFCF(FoodDataTFCF.CHOCOLATE), CT_FOOD));
        simpleItems.add(register(r, "food/pumpkin_scooped", new ItemFoodTFCF(FoodDataTFCF.PUMPKIN), CT_FOOD));
        simpleItems.add(register(r, "food/pumpkin_chunks", new ItemFoodTFCF(FoodDataTFCF.PUMPKIN), CT_FOOD));

        // Fishstuff
        simpleItems.add(register(r, "food/raw_eel", new ItemFoodTFCF(FoodDataTFCF.RAW_EEL), CT_FOOD));
        simpleItems.add(register(r, "food/cooked_eel", new ItemFoodTFCF(FoodDataTFCF.COOKED_EEL), CT_FOOD));
        simpleItems.add(register(r, "food/raw_crab", new ItemFoodTFCF(FoodDataTFCF.RAW_CRAB), CT_FOOD));
        simpleItems.add(register(r, "food/cooked_crab", new ItemFoodTFCF(FoodDataTFCF.COOKED_CRAB), CT_FOOD));
        simpleItems.add(register(r, "food/raw_clam", new ItemFoodTFCF(FoodDataTFCF.RAW_CLAM), CT_FOOD));
        simpleItems.add(register(r, "food/cooked_clam", new ItemFoodTFCF(FoodDataTFCF.COOKED_CLAM), CT_FOOD));
        simpleItems.add(register(r, "food/raw_scallop", new ItemFoodTFCF(FoodDataTFCF.RAW_SCALLOP), CT_FOOD));
        simpleItems.add(register(r, "food/cooked_scallop", new ItemFoodTFCF(FoodDataTFCF.COOKED_SCALLOP), CT_FOOD));
        simpleItems.add(register(r, "food/raw_starfish", new ItemFoodTFCF(FoodDataTFCF.RAW_STARFISH), CT_FOOD));
        simpleItems.add(register(r, "food/cooked_starfish", new ItemFoodTFCF(FoodDataTFCF.COOKED_STARFISH), CT_FOOD));

        // Dried Berries
        for (FruitsTFCF fruit : FruitsTFCF.values())
        {
            if (fruit.canDry())
            {
                ItemFoodTFCF dried = new ItemFoodTFCF(fruit.getDriedData());
                simpleItems.add(register(r, "food/dried/" + fruit.name().toLowerCase(), dried, CT_FOOD));
                OreDictionary.registerOre("dried_" + fruit.name().toLowerCase(), dried);
                OreDictionary.registerOre("fruitDry", dried);
                driedFruits.put(fruit, dried);
            }
        }

        simpleItems.add(register(r, "food/coconut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        
        simpleItems.add(register(r, "food/acorn", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/acorn_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/acorn_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/almond", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/almond_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/almond_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/beechnut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/beechnut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/beechnut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/black_walnut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/black_walnut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/black_walnut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/brazil_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/brazil_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/brazil_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/breadnut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/breadnut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/breadnut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/bunya_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/bunya_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/bunya_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/butternut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/butternut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/butternut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/candlenut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/candlenut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/candlenut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/cashew", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/cashew_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/cashew_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));
        
        simpleItems.add(register(r, "food/chestnut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/chestnut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/chestnut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/ginkgo_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/ginkgo_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/ginkgo_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/hazelnut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/hazelnut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/hazelnut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/heartnut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/heartnut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/heartnut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/hickory_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/hickory_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/hickory_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/kola_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/kola_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/kola_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/kukui_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/kukui_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/kukui_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/macadamia", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/macadamia_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/macadamia_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/mongongo", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/mongongo_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/mongongo_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/monkey_puzzle_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/monkey_puzzle_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/monkey_puzzle_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/nutmeg", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/nutmeg_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/nutmeg_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/paradise_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/paradise_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/paradise_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/pecan", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/pecan_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/pecan_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));
        
        simpleItems.add(register(r, "food/pinecone", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/pine_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/pine_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/pistachio", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/pistachio_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/pistachio_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        simpleItems.add(register(r, "food/walnut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT), CT_FOOD));
        simpleItems.add(register(r, "food/walnut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/walnut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT), CT_FOOD));

        // Flour & Dough 
        /*      
        simpleItems.add(register(r, "acorn_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "acorn_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "allspice_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "allspice_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "almond_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "almond_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "beechnut_nut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "beechnut_nut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "black_walnut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "black_walnut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "brazil_nut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "brazil_nut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "breadnut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "breadnut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "bunya_nut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "bunya_nut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "butternut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "butternut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "candlenut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "candlenut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "cashew_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "cashew_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "chestnut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "chestnut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "ginkgo_nut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "ginkgo_nut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "hazelnut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "hazelnut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "heartnut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "heartnut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "kola_nut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "kola_nut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "kukui_nut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "kukui_nut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "macadamia_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "macadamia_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "mongongo_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "mongongo_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "monkey_puzzle_nut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "monkey_puzzle_nut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "nutmeg_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "nutmeg_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "paradise_nut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "paradise_nut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "pecan_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "pecan_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "pine_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "pine_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "pistachio_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "pistachio_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        simpleItems.add(register(r, "walnut_nut_flour", new ItemFoodTFCF(FoodDataTFCF.FLOUR), CT_FOOD));
        simpleItems.add(register(r, "walnut_nut_dough", new ItemFoodTFCF(FoodDataTFCF.DOUGH), CT_FOOD));
        */

        /*
        for (String grain : new String[] {"barley", "corn", "oat", "rice", "rye", "wheat", "amaranth", "barley", "fonio", "millet", "quinoa", "spelt", "wild_rice"})
        {
            ItemFoodTFCF flatbread_dough = new ItemFoodTFCF(FoodDataTFCF.DOUGH);
            simpleItems.add(register(r, grain + "_flatbread_dough", flatbread_dough, CT_FOOD));
            OreDictionary.registerOre(grain + "_flatbread_dough", flatbread_dough);
            OreDictionary.registerOre("doughFlat", flatbread_dough);

            ItemFoodTFCF flatbread = new ItemFoodTFCF(FoodDataTFCF.FLATBREAD);
            simpleItems.add(register(r, grain + "_flatbread", flatbread, CT_FOOD));
            OreDictionary.registerOre("flatbread", flatbread);
        }
        */
        
        simpleItems.add(register(r, "food/allspice", new ItemFoodTFCF(FoodDataTFCF.ALLSPICE), CT_FOOD));
        simpleItems.add(register(r, "food/clove", new ItemFoodTFCF(FoodDataTFCF.CLOVE), CT_FOOD));
        simpleItems.add(register(r, "food/curry_leaf", new ItemFoodTFCF(FoodDataTFCF.CURRY_LEAF), CT_FOOD));
        simpleItems.add(register(r, "food/liquorice_root", new ItemFoodTFCF(FoodDataTFCF.LIQUORICE_ROOT), CT_FOOD));
        simpleItems.add(register(r, "food/cassia_cinnamon_bark", new ItemFoodTFCF(FoodDataTFCF.CASSIA_CINNAMON_BARK), CT_FOOD));
        simpleItems.add(register(r, "food/ground_cassia_cinnamon", new ItemFoodTFCF(FoodDataTFCF.GROUND_CASSIA_CINNAMON), CT_FOOD));
        simpleItems.add(register(r, "food/ceylon_cinnamon_bark", new ItemFoodTFCF(FoodDataTFCF.CEYLON_CINNAMON_BARK), CT_FOOD));
        simpleItems.add(register(r, "food/ground_ceylon_cinnamon", new ItemFoodTFCF(FoodDataTFCF.GROUND_CEYLON_CINNAMON), CT_FOOD));
        //simpleItems.add(register(r, "food/dried/black_pepper", new ItemFoodTFCF(FoodDataTFCF.DRIED_BLACK_PEPPER), CT_FOOD));
        simpleItems.add(register(r, "food/ground_black_pepper", new ItemFoodTFCF(FoodDataTFCF.GROUND_BLACK_PEPPER), CT_FOOD));
        simpleItems.add(register(r, "food/black_tea", new ItemFoodTFCF(FoodDataTFCF.BLACK_TEA), CT_FOOD));
        simpleItems.add(register(r, "food/dried/black_tea", new ItemFoodTFCF(FoodDataTFCF.DRIED_BLACK_TEA), CT_FOOD));
        simpleItems.add(register(r, "food/green_tea", new ItemFoodTFCF(FoodDataTFCF.GREEN_TEA), CT_FOOD));
        simpleItems.add(register(r, "food/dried/green_tea", new ItemFoodTFCF(FoodDataTFCF.DRIED_GREEN_TEA), CT_FOOD));
        simpleItems.add(register(r, "food/white_tea", new ItemFoodTFCF(FoodDataTFCF.WHITE_TEA), CT_FOOD));
        simpleItems.add(register(r, "food/dried/white_tea", new ItemFoodTFCF(FoodDataTFCF.DRIED_WHITE_TEA), CT_FOOD));
        simpleItems.add(register(r, "food/cannabis_bud", new ItemFoodTFCF(FoodDataTFCF.CANNABIS_BUD), CT_FOOD));
        simpleItems.add(register(r, "food/dried/cannabis_bud", new ItemFoodTFCF(FoodDataTFCF.DRIED_CANNABIS_BUD), CT_FOOD));
        simpleItems.add(register(r, "food/cannabis_leaf", new ItemFoodTFCF(FoodDataTFCF.CANNABIS_LEAF), CT_FOOD));
        simpleItems.add(register(r, "food/dried/cannabis_leaf", new ItemFoodTFCF(FoodDataTFCF.DRIED_CANNABIS_LEAF), CT_FOOD));
        simpleItems.add(register(r, "food/coca_leaf", new ItemFoodTFCF(FoodDataTFCF.COCA_LEAF), CT_FOOD));
        simpleItems.add(register(r, "food/dried/coca_leaf", new ItemFoodTFCF(FoodDataTFCF.DRIED_COCA_LEAF), CT_FOOD));
        simpleItems.add(register(r, "food/opium_poppy_bulb", new ItemFoodTFCF(FoodDataTFCF.OPIUM_POPPY_BULB), CT_FOOD));
        simpleItems.add(register(r, "food/dried/opium_poppy_bulb", new ItemFoodTFCF(FoodDataTFCF.DRIED_OPIUM_POPPY_BULB), CT_FOOD));
        simpleItems.add(register(r, "food/peyote", new ItemFoodTFCF(FoodDataTFCF.PEYOTE), CT_FOOD));
        simpleItems.add(register(r, "food/dried/peyote", new ItemFoodTFCF(FoodDataTFCF.DRIED_PEYOTE), CT_FOOD));
        simpleItems.add(register(r, "food/tobacco_leaf", new ItemFoodTFCF(FoodDataTFCF.TOBACCO_LEAF), CT_FOOD));
        simpleItems.add(register(r, "food/dried/tobacco_leaf", new ItemFoodTFCF(FoodDataTFCF.DRIED_TOBACCO_LEAF), CT_FOOD));

        // Miscellaneous Items

        simpleItems.add(register(r, "food/dark_chocolate_blend", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "food/milk_chocolate_blend", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "food/white_chocolate_blend", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT), CT_MISC));

        simpleItems.add(register(r, "peel", new ItemMiscTFCF(Size.LARGE, Weight.VERY_HEAVY), CT_MISC));

        ItemMiscTFCF fruit_leaf = new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT);
        simpleItems.add(register(r, "fruit_leaf", fruit_leaf, CT_MISC));
        OreDictionary.registerOre("fruitLeaf", fruit_leaf); //todo: Use our OreDict helper

        simpleItems.add(register(r, "resin", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "resin"), CT_MISC));
        simpleItems.add(register(r, "twig", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "twig", "twig_wood", "branch", "branch_wood"), CT_WOOD));
        simpleItems.add(register(r, "twig_leaves", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "twig", "twig_wood", "branch", "branch_wood"), CT_WOOD));
        simpleItems.add(register(r, "charred_bones", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "bone_charred"), CT_MISC));
        simpleItems.add(register(r, "conch", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "conch", "seashell"), CT_MISC));
        simpleItems.add(register(r, "clam", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "clam", "seashell"), CT_MISC));
        simpleItems.add(register(r, "live_clam", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "clam", "clam_live"), CT_MISC));
        simpleItems.add(register(r, "scallop", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "scallop", "seashell"), CT_MISC));
        simpleItems.add(register(r, "live_scallop", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "scallop", "scallop_live"), CT_MISC));
        simpleItems.add(register(r, "live_starfish", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "starfish", "starfish_live"), CT_MISC));
        simpleItems.add(register(r, "pearl", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "pearl"), CT_MISC));
        simpleItems.add(register(r, "black_pearl", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "pearl_black"), CT_MISC));


        simpleItems.add(register(r, "crop/product/papyrus_pulp", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "pulp", "pulp_papyrus"), CT_MISC));
        simpleItems.add(register(r, "crop/product/papyrus_fiber", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "fiber", "fiber_papyrus"), CT_MISC));
        simpleItems.add(register(r, "crop/product/papyrus_paper", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "paper", "paper_papyrus"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/agave", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_agave", "agave"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_fiber", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "fiber", "fiber_sisal"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_string", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "string", "string_sisal"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_cloth", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "cloth", "cloth_sisal", "fabric", "fabric_sisal", "fabric_hemp"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/cotton_boll", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_cotton", "cotton"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_yarn", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "string", "string_cotton", "yarn", "yarn_cotton"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_cloth", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "cloth", "cloth_high_quality", "cloth_cotton"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/flax", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "crop_flax", "flax"), CT_MISC));
        simpleItems.add(register(r, "crop/product/flax_fiber", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "fiber", "fiber_flax"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_string", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "string", "string_linen"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_cloth", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "cloth", "cloth_linen", "fabric", "fabric_linen", "fabric_hemp"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/hemp", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "crop_hemp", "hemp"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_fiber", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "fiber", "fiber_hemp"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_string", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "string", "string_hemp"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_cloth", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "cloth", "cloth_hemp", "fabric", "fabric_hemp"), CT_MISC));

        simpleItems.add(register(r, "crop/product/madder", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_madder", "madder"), CT_MISC));
        simpleItems.add(register(r, "crop/product/weld", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_weld", "weld"), CT_MISC));
        simpleItems.add(register(r, "crop/product/woad", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_woad", "woad"), CT_MISC));
        simpleItems.add(register(r, "crop/product/indigo", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_indigo", "indigo"), CT_MISC));
        simpleItems.add(register(r, "crop/product/rape", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_rape"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hops", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_hops"), CT_MISC));

        simpleItems.add(register(r, "crop/product/silk_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_silk"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen"), CT_MISC));
        simpleItems.add(register(r, "crop/product/papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_papyrus"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp"), CT_MISC));

        simpleItems.add(register(r, "crop/product/olive_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_silk_olive"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/olive_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_olive"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/olive_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_olive"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/olive_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_olive"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/olive_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_papyrus_olive"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/olive_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_olive"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/soybean_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_jute_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_silk_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_papyrus_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_soybean"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/linseed_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_jute_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_silk_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_papyrus_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_linseed"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/rape_seed_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_jute_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_silk_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_papyrus_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_rape_seed"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/sunflower_seed_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_jute_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_silk_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_papyrus_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_sunflower_seed"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/opium_poppy_seed_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_jute_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_silk_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_papyrus_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_opium_poppy_seed"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/sugar_beet_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_jute_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_silk_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_papyrus_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_sugar_beet"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/sugar_cane_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_silk_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_papyrus_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_sugar_cane"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/silk_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_silk"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_sisal"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_cotton"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_linen"), CT_MISC));
        simpleItems.add(register(r, "crop/product/papyrus_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_papyrus"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_hemp"), CT_MISC));

        simpleItems.add(register(r, "crop/product/dirty_silk_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_silk_dirty"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dirty_sisal_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_sisal_dirty"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dirty_cotton_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_cotton_dirty"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dirty_linen_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_linen_dirty"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dirty_papyrus_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_papyrus_dirty"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dirty_hemp_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_hemp_dirty"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/chamomile_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "item_chamomile_head", "material_chamomile_head", "chamomile"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried/chamomile_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "dried_chamomile", "dried_chamomile_head"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dandelion_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "item_dandelion_head", "material_dandelion_head", "dandelion"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried/dandelion_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "dried_dandelion", "dried_dandelion_head"), CT_MISC));
        simpleItems.add(register(r, "crop/product/labrador_tea_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "item_labrador_tea_head", "material_labrador_tea_head", "labrador_tea"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried/labrador_tea_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "dried_labrador_tea", "dried_labrador_tea_head"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sunflower_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_sunflower_head", "item_sunflower_head", "material_sunflower_head"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried/sunflower_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "item_dried_sunflower_head", "material_dried/sunflower_head"), CT_MISC));

        ItemMisc capole = new ItemMisc(Size.SMALL, Weight.MEDIUM);
        simpleItems.add(register(r, "wood/fruit_tree/pole/cassia_cinnamon", capole, CT_WOOD));
        OreDictionary.registerOre("poleCassiaCinnamon", capole);

        ItemMisc calumber = new ItemMisc(Size.SMALL, Weight.VERY_LIGHT);
        simpleItems.add(register(r, "wood/fruit_tree/lumber/cassia_cinnamon", calumber, CT_WOOD));
        OreDictionary.registerOre("lumberCassiaCinnamon", calumber);
        
        ItemMisc cepole = new ItemMisc(Size.SMALL, Weight.MEDIUM);
        simpleItems.add(register(r, "wood/fruit_tree/pole/ceylon_cinnamon", cepole, CT_WOOD));
        OreDictionary.registerOre("poleCeylonCinnamon", cepole);

        ItemMisc celumber = new ItemMisc(Size.SMALL, Weight.VERY_LIGHT);
        simpleItems.add(register(r, "wood/fruit_tree/lumber/ceylon_cinnamon", celumber, CT_WOOD));
        OreDictionary.registerOre("lumberCeylonCinnamon", celumber);

        for (FruitTreeTFCF fruitTree : FruitTreeTFCF.values())
        {
            String name = fruitTree.getName().toLowerCase();
            ItemMisc pole = new ItemMisc(Size.SMALL, Weight.MEDIUM);
            simpleItems.add(register(r, "wood/fruit_tree/pole/" + name, pole, CT_WOOD));
            //todo: Use our OreDict helper
            OreDictionary.registerOre("pole" + name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase(), pole);
        }

        for (FruitTreeTFCF fruitTree : FruitTreeTFCF.values())
        {
            String name = fruitTree.getName().toLowerCase();
            ItemMisc lumber = new ItemMisc(Size.SMALL, Weight.VERY_LIGHT);
            simpleItems.add(register(r, "wood/fruit_tree/lumber/" + name, lumber, CT_WOOD));
            //todo: Use our OreDict helper
            OreDictionary.registerOre("lumber" + name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase(), lumber);
        }

        for (IFruitTree fruitTree : FruitTree.values())
        {
            String name = fruitTree.getName().toLowerCase();
            ItemMisc pole = new ItemMisc(Size.SMALL, Weight.MEDIUM);
            simpleItems.add(register(r, "wood/fruit_tree/pole/" + name, pole, CT_WOOD));
            //todo: Use our OreDict helper
            OreDictionary.registerOre("pole" + name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase(), pole);
        }

        for (IFruitTree fruitTree : FruitTree.values())
        {
            String name = fruitTree.getName().toLowerCase();
            ItemMisc lumber = new ItemMisc(Size.SMALL, Weight.VERY_LIGHT);
            simpleItems.add(register(r, "wood/fruit_tree/lumber/" + name, lumber, CT_WOOD));
            //todo: Use our OreDict helper
            OreDictionary.registerOre("lumber" + name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase(), lumber);
        }

        for (BlockFruitDoor door : BlocksTFCF.getAllFruitDoors())
            fruitDoors.add(register(r, door.getRegistryName().getPath(), new ItemFruitDoor(door), CT_DECORATIONS));

        //uses a separate model loader
        register(r, "cracked_coconut", new ItemWoodenBucket(), CT_MISC);

        for (CropTFCF crop : CropTFCF.values())
        {
            simpleItems.add(register(r, "crop/seeds/" + crop.name().toLowerCase(), new ItemSeedsTFC(crop), CT_FOOD));
        }

        for (StemCrop crop : StemCrop.values())
        {
            simpleItems.add(register(r, "crop/seeds/" + crop.name().toLowerCase(), new ItemSeedsTFC(crop), CT_FOOD));
        }

        BlocksTFCF.getAllNormalItemBlocks().forEach((x) -> {
            registerItemBlock(r, x);
        });

        /*
        BlocksTFCF.getAllNormalItemBlocks().forEach(x -> registerItemBlock(r, x));
        BlocksTFCF.getAllInventoryItemBlocks().forEach(x -> registerItemBlock(r, x));
        */

        allSimpleItems = simpleItems.build();
        allFruitDoors = fruitDoors.build();

        OreDictionaryHelper.init();
    }
    
    /*
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
    */

    private static <T extends Item> T register(IForgeRegistry<Item> r, String name, T item, CreativeTabs ct)
    {
        item.setRegistryName(MODID, name);
        item.setTranslationKey(MODID + "." + name.replace('/', '.'));
        item.setCreativeTab(ct);
        r.register(item);
        return item;
    }
    
    @SuppressWarnings("ConstantConditions")
    private static void registerItemBlock(IForgeRegistry<Item> r, ItemBlock item)
    {
        item.setRegistryName(item.getBlock().getRegistryName());
        item.setCreativeTab(item.getBlock().getCreativeTab());
        r.register(item);
    }
}