package tfcflorae.objects.items;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nonnull;

import com.eerussianguy.firmalife.init.FruitTreeFL;
import com.eerussianguy.firmalife.init.PlantsFL;
import com.eerussianguy.firmalife.items.ItemMetalMalletMold;
import com.eerussianguy.firmalife.registry.ItemsFL;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSnow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.wood.BlockDoorTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.items.ItemArmorTFC;
import net.dries007.tfc.objects.items.ItemMisc;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.ItemSlabTFC;
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
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.agriculture.Food;
import net.dries007.tfc.util.agriculture.FruitTree;
import net.dries007.tfc.util.Helpers;
import tfcelementia.TFCElementia;
import tfcelementia.objects.PowderTFCE;
import tfcelementia.objects.items.metal.ItemMetalTFCE;

import tfcflorae.objects.*;
import tfcflorae.objects.blocks.*;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.groundcover.*;
import tfcflorae.objects.blocks.wood.cinnamon.*;
import tfcflorae.objects.blocks.wood.fruitwood.*;
import tfcflorae.objects.blocks.wood.BlockLogTFCF;
import tfcflorae.objects.blocks.wood.bamboo.BlockBambooLog;
import tfcflorae.objects.blocks.blocktype.BlockSlabTFCF;
import tfcflorae.objects.items.*;
import tfcflorae.objects.items.ceramics.*;
import tfcflorae.objects.items.ceramics.ItemUnfiredUrn;
import tfcflorae.objects.items.devices.*;
import tfcflorae.objects.items.food.*;
import tfcflorae.objects.items.itemblock.ItemBlockStickBundle;
import tfcflorae.objects.items.rock.ItemMud;
import tfcflorae.objects.items.rock.ItemFiredMudBrick;
import tfcflorae.objects.items.rock.ItemUnfiredMudBrick;
import tfcflorae.objects.items.tools.ItemAxeTFCF;
import tfcflorae.objects.items.tools.ItemHammerTFCF;
import tfcflorae.objects.items.tools.ItemHoeTFCF;
import tfcflorae.objects.items.tools.ItemJavelinTFCF;
import tfcflorae.objects.items.tools.ItemKnifeTFCF;
import tfcflorae.objects.items.tools.ItemShovelTFCF;
import tfcflorae.objects.items.tools.ItemWalkingStick;
import tfcflorae.objects.items.tools.ItemBowTFCF;
import tfcflorae.types.PlantsTFCF;
import tfcflorae.types.TreesTFCF;
import tfcflorae.util.agriculture.*;
import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.ConfigTFCF;
import tfcflorae.TFCFlorae;
import tfcflorae.compat.firmalife.ceramics.*;
import tfcflorae.compat.tfcelementia.ceramics.*;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.CreativeTabsTFC.*;
import static tfcflorae.TFCFlorae.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
@GameRegistry.ObjectHolder(MODID)
public final class ItemsTFCF
{
    /*
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
    @GameRegistry.ObjectHolder("food/ice_cream_bean")
    public static final ItemFoodTFCF ICE_CREAM_BEAN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/jackfruit")
    public static final ItemFoodTFCF JACKFRUIT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/jujube")
    public static final ItemFoodTFCF JUJUBE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/kaki")
    public static final ItemFoodTFCF KAKI = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/key_lime")
    public static final ItemFoodTFCF KEY_LIME = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/kluwak")
    public static final ItemFoodTFCF KLUWAK = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/kumquat")
    public static final ItemFoodTFCF KUMQUAT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/persian_lime")
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
    @GameRegistry.ObjectHolder("food/sand_pear")
    public static final ItemFoodTFCF SAND_PEAR = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/sapodilla")
    public static final ItemFoodTFCF SAPODILLA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/satsuma")
    public static final ItemFoodTFCF SATSUMA = Helpers.getNull();
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
    @GameRegistry.ObjectHolder("food/vanilla")
    public static final ItemFoodTFCF VANILLA = Helpers.getNull();

    // Nuts
    @GameRegistry.ObjectHolder("food/almond")
    public static final ItemFoodTFCF ALMOND = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/almond_nut")
    public static final ItemFoodTFCF ALMOND_NUT = Helpers.getNull();
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
    @GameRegistry.ObjectHolder("food/candlenut")
    public static final ItemFoodTFCF CANDLENUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/candlenut_nut")
    public static final ItemFoodTFCF CANDLENUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cashew")
    public static final ItemFoodTFCF CASHEW = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cashew_nut")
    public static final ItemFoodTFCF CASHEW_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/heartnut")
    public static final ItemFoodTFCF HEARTNUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/heartnut_nut")
    public static final ItemFoodTFCF HEARTNUT_NUT = Helpers.getNull();
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
    @GameRegistry.ObjectHolder("food/nutmeg_powder")
    public static final ItemFoodTFCF NUTMEG_POWDER = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/paradise_nut")
    public static final ItemFoodTFCF PARADISE_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/paradise_nut_nut")
    public static final ItemFoodTFCF PARADISE_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pistachio")
    public static final ItemFoodTFCF PISTACHIO = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pistachio_nut")
    public static final ItemFoodTFCF PISTACHIO_NUT = Helpers.getNull();
    */

    // Normal Trees Fruits
    @GameRegistry.ObjectHolder("food/baobab_fruit")
    public static final ItemFoodTFCF BAOBAB_FRUIT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/barrel_cactus_fruit")
    public static final ItemFoodTFCF BARREL_CACTUS_FRUIT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/hawthorn")
    public static final ItemFoodTFCF HAWTHORN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/juniper")
    public static final ItemFoodTFCF JUNIPER = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/osage_orange")
    public static final ItemFoodTFCF OSAGE_ORANGE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pink_ivory_drupe")
    public static final ItemFoodTFCF PINK_IVORY_DRUPE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/riberry")
    public static final ItemFoodTFCF RIBERRY = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/rowan_berry")
    public static final ItemFoodTFCF ROWAN_BERRY = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/sky_fruit")
    public static final ItemFoodTFCF SKY_FRUIT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/yew_berry")
    public static final ItemFoodTFCF YEW_BERRY = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/yew_berry")
    public static final ItemFoodTFCF ROASTED_YEW_BERRY = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/mulberry")
    public static final ItemFoodTFCF MULBERRY = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/glowberry")
    public static final ItemFoodTFCF GLOWBERRY = Helpers.getNull();

    // Normal Trees Nuts
    @GameRegistry.ObjectHolder("food/acorn")
    public static final ItemFoodTFCF ACORN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/acorn_nut")
    public static final ItemFoodTFCF ACORN_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/beechnut")
    public static final ItemFoodTFCF BEECHNUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/beechnut_nut")
    public static final ItemFoodTFCF BEECHNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/black_walnut")
    public static final ItemFoodTFCF BLACK_WALNUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/black_walnut_nut")
    public static final ItemFoodTFCF BLACK_WALNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/butternut")
    public static final ItemFoodTFCF BUTTERNUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/butternut_nut")
    public static final ItemFoodTFCF BUTTERNUT_NUT = Helpers.getNull();
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
    @GameRegistry.ObjectHolder("food/hickory_nut")
    public static final ItemFoodTFCF HICKORY_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/hickory_nut_nut")
    public static final ItemFoodTFCF HICKORY_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pecan")
    public static final ItemFoodTFCF PECAN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pecan_nut")
    public static final ItemFoodTFCF PECAN_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pinecone")
    public static final ItemFoodTFCF PINECONE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/pine_nut")
    public static final ItemFoodTFCF PINE_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/walnut")
    public static final ItemFoodTFCF WALNUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/walnut_nut")
    public static final ItemFoodTFCF WALNUT_NUT = Helpers.getNull();

    // Processed Nuts
    /*
    @GameRegistry.ObjectHolder("food/roasted/almond_nut")
    public static final ItemFoodTFCF ROASTED_ALMOND_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/brazil_nut_nut")
    public static final ItemFoodTFCF ROASTED_BRAZIL_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/breadnut_nut")
    public static final ItemFoodTFCF ROASTED_BREADNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/bunya_nut_nut")
    public static final ItemFoodTFCF ROASTED_BUNYA_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/candlenut_nut")
    public static final ItemFoodTFCF ROASTED_CANDLENUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/cashew_nut")
    public static final ItemFoodTFCF ROASTED_CASHEW_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/heartnut_nut")
    public static final ItemFoodTFCF ROASTED_HEARTNUT_NUT = Helpers.getNull();
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
    @GameRegistry.ObjectHolder("food/roasted/pistachio_nut")
    public static final ItemFoodTFCF ROASTED_PISTACHIO_NUT = Helpers.getNull();
    */

    @GameRegistry.ObjectHolder("food/roasted/acorn_nut")
    public static final ItemFoodTFCF ROASTED_ACORN_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/beechnut_nut")
    public static final ItemFoodTFCF ROASTED_BEECHNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/black_walnut_nut")
    public static final ItemFoodTFCF ROASTED_BLACK_WALNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/butternut_nut")
    public static final ItemFoodTFCF ROASTED_BUTTERNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/chestnut_nut")
    public static final ItemFoodTFCF ROASTED_CHESTNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/ginkgo_nut_nut")
    public static final ItemFoodTFCF ROASTED_GINKGO_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/hazelnut_nut")
    public static final ItemFoodTFCF ROASTED_HAZELNUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/hickory_nut_nut")
    public static final ItemFoodTFCF ROASTED_HICKORY_NUT_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/pecan_nut")
    public static final ItemFoodTFCF ROASTED_PECAN_NUT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/pine_nut")
    public static final ItemFoodTFCF ROASTED_PINE_NUT = Helpers.getNull();
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
    @GameRegistry.ObjectHolder("food/ground_cassia_cinnamon_bark")
    public static final ItemFoodTFCF GROUND_CASSIA_CINNAMON = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/ceylon_cinnamon_bark")
    public static final ItemFoodTFCF CEYLON_CINNAMON_BARK = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/ground_ceylon_cinnamon_bark")
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

    // Miscellaneous Food Stuff
    @GameRegistry.ObjectHolder("pomace")
    public static final ItemMiscTFCF POMACE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/coffea_cherries")
    public static final ItemFoodTFCF COFFEA_CHERRIES = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/dried/coffea_cherries")
    public static final ItemFoodTFCF DRIED_COFFEA_CHERRIES = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/coffee_beans")
    public static final ItemFoodTFCF ROASTED_COFFEE_BEANS = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/coffee_powder")
    public static final ItemFoodTFCF COFFEE_POWDER = Helpers.getNull();

    /*
    // Epiphytes
    @GameRegistry.ObjectHolder("food/artists_conk")
    public static final ItemFoodTFCF RAW_ARTISTS_CONK = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/sulphur_shelf")
    public static final ItemFoodTFCF RAW_SULPHUR_SHELF = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/turkey_tail")
    public static final ItemFoodTFCF RAW_TURKEY_TAIL = Helpers.getNull();

    // Mushrooms
    @GameRegistry.ObjectHolder("food/porcini")
    public static final ItemFoodTFCF RAW_PORCINI = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/amanita")
    public static final ItemFoodTFCF RAW_AMANITA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/black_powderpuff")
    public static final ItemFoodTFCF RAW_BLACK_POWDERPUFF = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/chanterelle")
    public static final ItemFoodTFCF RAW_CHANTERELLE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/death_cap")
    public static final ItemFoodTFCF RAW_DEATH_CAP = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/giant_club")
    public static final ItemFoodTFCF RAW_GIANT_CLUB = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/parasol_mushroom")
    public static final ItemFoodTFCF RAW_PARASOL_MUSHROOM = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/stinkhorn")
    public static final ItemFoodTFCF RAW_STINKHORN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/weeping_milk_cap")
    public static final ItemFoodTFCF RAW_WEEPING_MILK_CAP = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/wood_blewit")
    public static final ItemFoodTFCF RAW_WOOD_BLEWIT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/woolly_gomphus")
    public static final ItemFoodTFCF RAW_WOOLLY_GOMPHUS = Helpers.getNull();
    */

    // Epiphytes
    @GameRegistry.ObjectHolder("food/roasted/artists_conk")
    public static final ItemFoodTFCF ROASTED_ARTISTS_CONK = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/sulphur_shelf")
    public static final ItemFoodTFCF ROASTED_SULPHUR_SHELF = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/turkey_tail")
    public static final ItemFoodTFCF ROASTED_TURKEY_TAIL = Helpers.getNull();

    // Mushrooms
    @GameRegistry.ObjectHolder("food/roasted/porcini")
    public static final ItemFoodTFCF ROASTED_PORCINI = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/amanita")
    public static final ItemFoodTFCF ROASTED_AMANITA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/black_powderpuff")
    public static final ItemFoodTFCF ROASTED_BLACK_POWDERPUFF = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/chanterelle")
    public static final ItemFoodTFCF ROASTED_CHANTERELLE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/death_cap")
    public static final ItemFoodTFCF ROASTED_DEATH_CAP = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/giant_club")
    public static final ItemFoodTFCF ROASTED_GIANT_CLUB = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/parasol_mushroom")
    public static final ItemFoodTFCF ROASTED_PARASOL_MUSHROOM = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/stinkhorn")
    public static final ItemFoodTFCF ROASTED_STINKHORN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/weeping_milk_cap")
    public static final ItemFoodTFCF ROASTED_WEEPING_MILK_CAP = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/wood_blewit")
    public static final ItemFoodTFCF ROASTED_WOOD_BLEWIT = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/roasted/woolly_gomphus")
    public static final ItemFoodTFCF ROASTED_WOOLLY_GOMPHUS = Helpers.getNull();

    // Normal foods
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
    @GameRegistry.ObjectHolder("food/raw_snail")
    public static final ItemFoodTFCF RAW_SNAIL = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cooked_snail")
    public static final ItemFoodTFCF COOKED_SNAIL = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/raw_worm")
    public static final ItemFoodTFCF RAW_WORM = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/cooked_worm")
    public static final ItemFoodTFCF COOKED_WORM = Helpers.getNull();

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
    @GameRegistry.ObjectHolder("food/amaranth_flatbread")
    public static final ItemFoodTFCF AMARANTH_FLATBREAD = Helpers.getNull();
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
    @GameRegistry.ObjectHolder("food/buckwheat_flatbread")
    public static final ItemFoodTFCF BUCKWHEAT_FLATBREAD = Helpers.getNull();
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
    @GameRegistry.ObjectHolder("food/fonio_flatbread")
    public static final ItemFoodTFCF FONIO_FLATBREAD = Helpers.getNull();
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
    @GameRegistry.ObjectHolder("food/millet_flatbread")
    public static final ItemFoodTFCF MILLET_FLATBREAD = Helpers.getNull();
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
    @GameRegistry.ObjectHolder("food/quinoa_flatbread")
    public static final ItemFoodTFCF QUINOA_FLATBREAD = Helpers.getNull();
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
    @GameRegistry.ObjectHolder("food/spelt_flatbread")
    public static final ItemFoodTFCF SPELT_FLATBREAD = Helpers.getNull();

    @GameRegistry.ObjectHolder("food/wild_barley")
    public static final ItemFoodTFCF WILD_BARLEY = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/wild_rice")
    public static final ItemFoodTFCF WILD_RICE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/wild_wheat")
    public static final ItemFoodTFCF WILD_WHEAT = Helpers.getNull();

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

    @GameRegistry.ObjectHolder("food/linseed")
    public static final ItemFoodTFCF LINSEED = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/rape_seed")
    public static final ItemFoodTFCF RAPE_SEED = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/sunflower_seed")
    public static final ItemFoodTFCF SUNFLOWER_SEED = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/opium_poppy_seed")
    public static final ItemFoodTFCF OPIUM_POPPY_SEED = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/hash_muffin_dough")
    public static final ItemFoodTFCF HASH_MUFFIN_DOUGH = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/hash_muffin")
    public static final ItemFoodTFCF HASH_MUFFIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/rutabaga")
    public static final ItemFoodTFCF RUTABAGA = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/turnip")
    public static final ItemFoodTFCF TURNIP = Helpers.getNull();
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
    @GameRegistry.ObjectHolder("food/sugar_beet")
    public static final ItemFoodTFCF SUGAR_BEET = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/purple_grape")
    public static final ItemFoodTFCF PURPLE_GRAPE = Helpers.getNull();
    @GameRegistry.ObjectHolder("food/green_grape")
    public static final ItemFoodTFCF GREEN_GRAPE = Helpers.getNull();
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

    @GameRegistry.ObjectHolder("yeast")
    public static final ItemMiscTFCF YEAST = Helpers.getNull();
    @GameRegistry.ObjectHolder("firma_cola_mix")
    public static final ItemMiscTFCF FIRMA_COLA_MIX = Helpers.getNull();
    @GameRegistry.ObjectHolder("firma_cola_oils")
    public static final ItemMiscTFCF FIRMA_COLA_OILS = Helpers.getNull();
    @GameRegistry.ObjectHolder("firma_cola_blend")
    public static final ItemMiscTFCF FIRMA_COLA_BLEND = Helpers.getNull();

    // Normal Items
    @GameRegistry.ObjectHolder("tools/walking_Stick")
    public static final ItemWalkingStick WALKING_STICK = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/flint/axe/flint")
    public static final ItemAxeTFCF FLINT_AXE = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/flint/hammer/flint")
    public static final ItemHammerTFCF FLINT_HAMMER = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/flint/hoe/flint")
    public static final ItemHoeTFCF FLINT_HOE = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/flint/javelin/flint")
    public static final ItemJavelinTFCF FLINT_JAVELIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/flint/knife/flint")
    public static final ItemKnifeTFCF FLINT_KNIFE = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/flint/shovel/flint")
    public static final ItemShovelTFCF FLINT_SHOVEL = Helpers.getNull();

    @GameRegistry.ObjectHolder("tools/flint/axe_head/flint")
    public static final ItemMiscTFCF FLINT_AXE_HEAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/flint/hammer_head/flint")
    public static final ItemMiscTFCF FLINT_HAMMER_HEAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/flint/hoe_head/flint")
    public static final ItemMiscTFCF FLINT_HOE_HEAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/flint/javelin_head/flint")
    public static final ItemMiscTFCF FLINT_JAVELIN_HEAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/flint/knife_head/flint")
    public static final ItemMiscTFCF FLINT_KNIFE_HEAD = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/flint/shovel_head/flint")
    public static final ItemMiscTFCF FLINT_SHOVEL_HEAD = Helpers.getNull();

    /*@GameRegistry.ObjectHolder("tools/bows/shortbow/shortbow")
    public static final ItemBowTFCF SHORTBOW = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/bows/longbow/longbow")
    public static final ItemBowTFCF LONGBOW = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/bows/bonebow/bonebow")
    public static final ItemBowTFCF BONEBOW = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/bows/bow_of_lost_souls/bow_of_lost_souls")
    public static final ItemBowTFCF BOW_OF_LOST_SOULS = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/bows/elite_power_bow/elite_power_bow")
    public static final ItemBowTFCF ELITE_POWER_BOW = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/bows/green_menace/green_menace")
    public static final ItemBowTFCF GREEN_MENACE = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/bows/hunting_bow/hunting_bow")
    public static final ItemBowTFCF HUNTING_BOW = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/bows/nocturnal_bow/nocturnal_bow")
    public static final ItemBowTFCF NOCTURNAL_BOW = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/bows/red_snake/red_snake")
    public static final ItemBowTFCF RED_SNAKE = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/bows/rosebow/rosebow")
    public static final ItemBowTFCF ROSEBOW = Helpers.getNull();
    @GameRegistry.ObjectHolder("tools/bows/sabrewing/sabrewing")
    public static final ItemBowTFCF SABREWING = Helpers.getNull();*/

    @GameRegistry.ObjectHolder("container/leather_bag_piece")
    public static final ItemMiscTFCF LEATHER_BAG_PIECE = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/leather_bag")
    public static final ItemBag LEATHER_BAG = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/pineapple_leather_bag_piece")
    public static final ItemMiscTFCF PINEAPPLE_LEATHER_BAG_PIECE = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/pineapple_leather_bag")
    public static final ItemBag PINEAPPLE_LEATHER_BAG = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/burlap_sack_piece")
    public static final ItemMiscTFCF BURLAP_SACK_PIECE = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/burlap_sack")
    public static final ItemSack BURLAP_SACK = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/wool_sack_piece")
    public static final ItemMiscTFCF WOOL_SACK_PIECE = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/wool_sack")
    public static final ItemSack WOOL_SACK = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/silk_sack_piece")
    public static final ItemMiscTFCF SILK_SACK_PIECE = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/silk_sack")
    public static final ItemSack SILK_SACK = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/cotton_sack_piece")
    public static final ItemMiscTFCF COTTON_SACK_PIECE = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/cotton_sack")
    public static final ItemSack COTTON_SACK = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/hemp_sack_piece")
    public static final ItemMiscTFCF HEMP_SACK_PIECE = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/hemp_sack")
    public static final ItemSack HEMP_SACK = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/linen_sack_piece")
    public static final ItemMiscTFCF LINEN_SACK_PIECE = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/linen_sack")
    public static final ItemSack LINEN_SACK = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/sisal_sack_piece")
    public static final ItemMiscTFCF SISAL_SACK_PIECE = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/sisal_sack")
    public static final ItemSack SISAL_SACK = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/yucca_sack_piece")
    public static final ItemMiscTFCF YUCCA_SACK_PIECE = Helpers.getNull();
    @GameRegistry.ObjectHolder("container/yucca_sack")
    public static final ItemSack YUCCA_SACK = Helpers.getNull();

    @GameRegistry.ObjectHolder("logwood_chips")
    public static final ItemMiscTFCF LOGWOOD_CHIPS = Helpers.getNull();
    //@GameRegistry.ObjectHolder("resin")
    //public static final ItemMiscTFCF RESIN = Helpers.getNull();
    @GameRegistry.ObjectHolder("charred_bones")
    public static final ItemMiscTFCF CHARRED_BONES = Helpers.getNull();
    @GameRegistry.ObjectHolder("conch")
    public static final ItemMiscTFCF CONCH = Helpers.getNull();
    @GameRegistry.ObjectHolder("clam")
    public static final ItemMiscTFCF CLAM = Helpers.getNull();
    @GameRegistry.ObjectHolder("live_clam")
    public static final ItemMiscTFCF LIVE_CLAM = Helpers.getNull();
    @GameRegistry.ObjectHolder("scallop")
    public static final ItemMiscTFCF SCALLOP = Helpers.getNull();
    @GameRegistry.ObjectHolder("live_scallop")
    public static final ItemMiscTFCF LIVE_SCALLOP = Helpers.getNull();
    @GameRegistry.ObjectHolder("pearl")
    public static final ItemMiscTFCF PEARL = Helpers.getNull();
    @GameRegistry.ObjectHolder("black_pearl")
    public static final ItemMiscTFCF BLACK_PEARL = Helpers.getNull();
    @GameRegistry.ObjectHolder("live_starfish")
    public static final ItemMiscTFCF LIVE_STARFISH = Helpers.getNull();
    @GameRegistry.ObjectHolder("animal/product/silk_moth_egg")
    public static final ItemMiscTFCF SILK_MOTH_EGG = Helpers.getNull();
    @GameRegistry.ObjectHolder("animal/product/silk_worm_hatchery")
    public static final ItemMiscTFCF SILK_WORM_HATCHERY = Helpers.getNull();
    @GameRegistry.ObjectHolder("animal/product/silk_worm")
    public static final ItemMiscTFCF SILK_WORM = Helpers.getNull();
    @GameRegistry.ObjectHolder("animal/product/silk_worm_cocoon")
    public static final ItemMiscTFCF SILK_WORM_COCOON = Helpers.getNull();
    @GameRegistry.ObjectHolder("animal/product/silk_worm_cocoon_boiled")
    public static final ItemMiscTFCF SILK_WORM_COCOON_BOILED = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/mulberry_leaf")
    public static final ItemMiscTFCF MULBERRY_LEAF = Helpers.getNull();

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

    @GameRegistry.ObjectHolder("cellulose_fibers")
    public static final ItemMiscTFCF CELLULOSE_FIBERS = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/yucca_fiber")
    public static final ItemMiscTFCF YUCCA_FIBER = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/yucca_string")
    public static final ItemMiscTFCF YUCCA_STRING = Helpers.getNull();
    @GameRegistry.ObjectHolder("crop/product/yucca_canvas")
    public static final ItemMiscTFCF YUCCA_CANVAS = Helpers.getNull();

    @GameRegistry.ObjectHolder("wood/fruit_tree/pole/cassia_cinnamon")
    public static final ItemMisc CASSIA_CINNAMON_POLE = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/fruit_tree/lumber/cassia_cinnamon")
    public static final ItemMisc CASSIA_CINNAMON_LUMBER = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/fruit_tree/pole/ceylon_cinnamon")
    public static final ItemMisc CEYLON_CINNAMON_POLE = Helpers.getNull();
    @GameRegistry.ObjectHolder("wood/fruit_tree/lumber/ceylon_cinnamon")
    public static final ItemMisc CEYLON_CINNAMON_LUMBER = Helpers.getNull();

    // Earthenware Clay Ceramics
    @GameRegistry.ObjectHolder("ceramics/earthenware/earthenware_clay")
    public static final ItemClayEarthenware EARTHENWARE_CLAY = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/unfired/earthenware_brick")
    public static final ItemPottery UNFIRED_EARTHENWARE_BRICK = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/fired/earthenware_brick")
    public static final ItemPottery FIRED_EARTHENWARE_BRICK = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/unfired/vessel")
    public static final ItemPottery UNFIRED_EARTHENWARE_VESSEL = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/fired/vessel")
    public static final ItemPottery FIRED_EARTHENWARE_VESSEL = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/unfired/vessel_glazed")
    public static final ItemPottery UNFIRED_EARTHENWARE_VESSEL_GLAZED = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/fired/vessel_glazed")
    public static final ItemPottery FIRED_EARTHENWARE_VESSEL_GLAZED = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/unfired/jug")
    public static final ItemPottery UNFIRED_EARTHENWARE_JUG = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/fired/jug")
    public static final ItemPottery FIRED_EARTHENWARE_JUG = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/unfired/pot")
    public static final ItemPottery UNFIRED_EARTHENWARE_POT = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/fired/pot")
    public static final ItemPottery FIRED_EARTHENWARE_POT = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/unfired/bowl")
    public static final ItemPottery UNFIRED_EARTHENWARE_BOWL = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/fired/bowl")
    public static final ItemPottery FIRED_EARTHENWARE_BOWL = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/unfired/spindle")
    public static final ItemPottery UNFIRED_EARTHENWARE_SPINDLE = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/fired/spindle")
    public static final ItemPottery FIRED_EARTHENWARE_SPINDLE = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/unfired/large_vessel")
    public static final ItemPottery UNFIRED_EARTHENWARE_LARGE_VESSEL = Helpers.getNull();

    @GameRegistry.ObjectHolder("ceramics/earthenware/unfired/mold/mallet_head")
    public static final ItemPottery UNFIRED_EARTHENWARE_MALLET_MOLD = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/earthenware/fired/mold/mallet_head")
    public static final ItemPottery EARTHENWARE_MALLET_MOLD = Helpers.getNull();

    // Kaolinite Clay Ceramics
    @GameRegistry.ObjectHolder("ceramics/kaolinite/kaolinite_clay")
    public static final ItemClayKaolinite KAOLINITE_CLAY = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/unfired/kaolinite_brick")
    public static final ItemPottery UNFIRED_KAOLINITE_BRICK = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/fired/kaolinite_brick")
    public static final ItemPottery FIRED_KAOLINITE_BRICK = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/unfired/vessel")
    public static final ItemPottery UNFIRED_KAOLINITE_VESSEL = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/fired/vessel")
    public static final ItemPottery FIRED_KAOLINITE_VESSEL = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/unfired/vessel_glazed")
    public static final ItemPottery UNFIRED_KAOLINITE_VESSEL_GLAZED = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/fired/vessel_glazed")
    public static final ItemPottery FIRED_KAOLINITE_VESSEL_GLAZED = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/unfired/jug")
    public static final ItemPottery UNFIRED_KAOLINITE_JUG = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/fired/jug")
    public static final ItemPottery FIRED_KAOLINITE_JUG = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/unfired/pot")
    public static final ItemPottery UNFIRED_KAOLINITE_POT = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/fired/pot")
    public static final ItemPottery FIRED_KAOLINITE_POT = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/unfired/bowl")
    public static final ItemPottery UNFIRED_KAOLINITE_BOWL = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/fired/bowl")
    public static final ItemPottery FIRED_KAOLINITE_BOWL = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/unfired/spindle")
    public static final ItemPottery UNFIRED_KAOLINITE_SPINDLE = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/fired/spindle")
    public static final ItemPottery FIRED_KAOLINITE_SPINDLE = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/unfired/large_vessel")
    public static final ItemPottery UNFIRED_KAOLINITE_LARGE_VESSEL = Helpers.getNull();

    @GameRegistry.ObjectHolder("ceramics/kaolinite/unfired/mold/mallet_head")
    public static final ItemPottery UNFIRED_KAOLINITE_MALLET_MOLD = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/kaolinite/fired/mold/mallet_head")
    public static final ItemPottery KAOLINITE_MALLET_MOLD = Helpers.getNull();

    // Stoneware Clay Ceramics
    @GameRegistry.ObjectHolder("ceramics/stoneware/stoneware_clay")
    public static final ItemClayStoneware STONEWARE_CLAY = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/unfired/stoneware_brick")
    public static final ItemPottery UNFIRED_STONEWARE_BRICK = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/fired/stoneware_brick")
    public static final ItemPottery FIRED_STONEWARE_BRICK = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/unfired/vessel")
    public static final ItemPottery UNFIRED_STONEWARE_VESSEL = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/fired/vessel")
    public static final ItemPottery FIRED_STONEWARE_VESSEL = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/unfired/vessel_glazed")
    public static final ItemPottery UNFIRED_STONEWARE_VESSEL_GLAZED = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/fired/vessel_glazed")
    public static final ItemPottery FIRED_STONEWARE_VESSEL_GLAZED = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/unfired/jug")
    public static final ItemPottery UNFIRED_STONEWARE_JUG = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/fired/jug")
    public static final ItemPottery FIRED_STONEWARE_JUG = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/unfired/pot")
    public static final ItemPottery UNFIRED_STONEWARE_POT = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/fired/pot")
    public static final ItemPottery FIRED_STONEWARE_POT = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/unfired/bowl")
    public static final ItemPottery UNFIRED_STONEWARE_BOWL = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/fired/bowl")
    public static final ItemPottery FIRED_STONEWARE_BOWL = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/unfired/spindle")
    public static final ItemPottery UNFIRED_STONEWARE_SPINDLE = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/fired/spindle")
    public static final ItemPottery FIRED_STONEWARE_SPINDLE = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/unfired/large_vessel")
    public static final ItemPottery UNFIRED_STONEWARE_LARGE_VESSEL = Helpers.getNull();

    @GameRegistry.ObjectHolder("ceramics/stoneware/unfired/mold/mallet_head")
    public static final ItemPottery UNFIRED_STONEWARE_MALLET_MOLD = Helpers.getNull();
    @GameRegistry.ObjectHolder("ceramics/stoneware/fired/mold/mallet_head")
    public static final ItemPottery STONEWARE_MALLET_MOLD = Helpers.getNull();

    @GameRegistry.ObjectHolder("storage/unfired/urn")
    public static final ItemPottery UNFIRED_URN = Helpers.getNull();
    @GameRegistry.ObjectHolder("wooden_bucket_salt")
    public static final ItemMiscTFCF WOODEN_BUCKET_SALT = Helpers.getNull();
    @GameRegistry.ObjectHolder("wooden_bucket_sugar")
    public static final ItemMiscTFCF WOODEN_BUCKET_SUGAR = Helpers.getNull();

    @GameRegistry.ObjectHolder("armor/helmet/pineapple_leather")
    public static final ItemArmorTFCF PINEAPPLE_LEATHER_HELMET = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/chestplate/pineapple_leather")
    public static final ItemArmorTFCF PINEAPPLE_LEATHER_CHESTPLATE = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/leggings/pineapple_leather")
    public static final ItemArmorTFCF PINEAPPLE_LEATHER_LEGGINGS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/boots/pineapple_leather")
    public static final ItemArmorTFCF PINEAPPLE_LEATHER_BOOTS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/helmet/burlap_cloth")
    public static final ItemArmorTFCF BURLAP_CLOTH_HELMET = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/chestplate/burlap_cloth")
    public static final ItemArmorTFCF BURLAP_CLOTH_CHESTPLATE = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/leggings/burlap_cloth")
    public static final ItemArmorTFCF BURLAP_CLOTH_LEGGINGS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/boots/burlap_cloth")
    public static final ItemArmorTFCF BURLAP_CLOTH_BOOTS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/helmet/wool_cloth")
    public static final ItemArmorTFCF WOOL_CLOTH_HELMET = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/chestplate/wool_cloth")
    public static final ItemArmorTFCF WOOL_CLOTH_CHESTPLATE = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/leggings/wool_cloth")
    public static final ItemArmorTFCF WOOL_CLOTH_LEGGINGS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/boots/wool_cloth")
    public static final ItemArmorTFCF WOOL_CLOTH_BOOTS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/helmet/silk_cloth")
    public static final ItemArmorTFCF SILK_CLOTH_HELMET = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/chestplate/silk_cloth")
    public static final ItemArmorTFCF SILK_CLOTH_CHESTPLATE = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/leggings/silk_cloth")
    public static final ItemArmorTFCF SILK_CLOTH_LEGGINGS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/boots/silk_cloth")
    public static final ItemArmorTFCF SILK_CLOTH_BOOTS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/helmet/sisal_cloth")
    public static final ItemArmorTFCF SISAL_CLOTH_HELMET = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/chestplate/sisal_cloth")
    public static final ItemArmorTFCF SISAL_CLOTH_CHESTPLATE = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/leggings/sisal_cloth")
    public static final ItemArmorTFCF SISAL_CLOTH_LEGGINGS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/boots/sisal_cloth")
    public static final ItemArmorTFCF SISAL_CLOTH_BOOTS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/helmet/cotton_cloth")
    public static final ItemArmorTFCF COTTON_CLOTH_HELMET = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/chestplate/cotton_cloth")
    public static final ItemArmorTFCF COTTON_CLOTH_CHESTPLATE = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/leggings/cotton_cloth")
    public static final ItemArmorTFCF COTTON_CLOTH_LEGGINGS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/boots/cotton_cloth")
    public static final ItemArmorTFCF COTTON_CLOTH_BOOTS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/helmet/linen_cloth")
    public static final ItemArmorTFCF LINEN_CLOTH_HELMET = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/chestplate/linen_cloth")
    public static final ItemArmorTFCF LINEN_CLOTH_CHESTPLATE = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/leggings/linen_cloth")
    public static final ItemArmorTFCF LINEN_CLOTH_LEGGINGS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/boots/linen_cloth")
    public static final ItemArmorTFCF LINEN_CLOTH_BOOTS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/helmet/hemp_cloth")
    public static final ItemArmorTFCF HEMP_CLOTH_HELMET = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/chestplate/hemp_cloth")
    public static final ItemArmorTFCF HEMP_CLOTH_CHESTPLATE = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/leggings/hemp_cloth")
    public static final ItemArmorTFCF HEMP_CLOTH_LEGGINGS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/boots/hemp_cloth")
    public static final ItemArmorTFCF HEMP_CLOTH_BOOTS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/helmet/yucca_canvas")
    public static final ItemArmorTFCF YUCCA_CANVAS_HELMET = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/chestplate/yucca_canvas")
    public static final ItemArmorTFCF YUCCA_CANVAS_CHESTPLATE = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/leggings/yucca_canvas")
    public static final ItemArmorTFCF YUCCA_CANVAS_LEGGINGS = Helpers.getNull();
    @GameRegistry.ObjectHolder("armor/boots/yucca_canvas")
    public static final ItemArmorTFCF YUCCA_CANVAS_BOOTS = Helpers.getNull();

    @GameRegistry.ObjectHolder("devices/flora_density_meter")
    public static final ItemFloraDensity FLORA_DENSITY_METER = Helpers.getNull();
    @GameRegistry.ObjectHolder("devices/season_clock")
    public static final ItemCalendarClock CALENDAR_CLOCK = Helpers.getNull();

    private static ImmutableList<Item> allSimpleItems;
    //private static ImmutableList<ItemBowTFCF> allItemBows;
    private static ImmutableList<Item> allFoodItems;
    private static ImmutableList<ItemGemTFCF> allGemTFCFItems;
    private static ImmutableList<BlockSurfaceOreDeposit> allSurfaceOreBlocks;
    private static ImmutableList<ItemFruitDoor> allFruitDoors;
    private static ImmutableList<Item> allCeramicMoldItems;
    private static ImmutableList<ItemArmorTFCF> allArmorItems;

    public static ImmutableList<Item> getAllSimpleItems()
    {
        return allSimpleItems;
    }

    /*public static ImmutableList<ItemBowTFCF> getAllItemBows()
    {
        return allItemBows;
    }*/

    public static ImmutableList<Item> getAllFoodItems()
    {
        return allFoodItems;
    }

    public static ImmutableList<ItemGemTFCF> getAllGemTFCFItems()
    {
        return allGemTFCFItems;
    }

    public static ImmutableList<BlockSurfaceOreDeposit> getAllSurfaceOreBlocks()
    {
        return allSurfaceOreBlocks;
    }

    public static ImmutableList<ItemFruitDoor> getAllFruitDoors() 
    { 
        return allFruitDoors; 
    }

    private static Map<FruitsTFCF, Item> driedFruits = new HashMap<>();

    public static Item getDriedFruit(FruitsTFCF fruit)
    {
        return driedFruits.get(fruit);
    }

    public static ImmutableList<Item> getAllCeramicMoldItems()
    {
        return allCeramicMoldItems;
    }

    public static ImmutableList<ItemArmorTFCF> getAllArmorItems()
    {
        return allArmorItems;
    }

    public static ItemEarthenwareMalletMoldFL malletMoldEarthenware;
    public static ItemKaoliniteMalletMoldFL malletMoldKaolinite;
    public static ItemStonewareMalletMoldFL malletMoldStoneware;

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> r = event.getRegistry();

        ImmutableList.Builder<Item> simpleItems = ImmutableList.builder();
        //ImmutableList.Builder<ItemBowTFCF> itemBows = ImmutableList.builder();
        ImmutableList.Builder<BlockSurfaceOreDeposit> surfaceOreBlocks = new Builder<>();
        ImmutableList.Builder<ItemFruitDoor> fruitDoors = ImmutableList.builder();
        ImmutableList.Builder<Item> ceramicItems = ImmutableList.builder();
        ImmutableList.Builder<ItemArmorTFCF> armorItems = ImmutableList.builder();

        // Fruit Tree Fruits
        /*
        simpleItems.add(register(r, "food/abiu", new ItemFoodTFCF(FoodDataTFCF.ABIU, "abiu", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/amla", new ItemFoodTFCF(FoodDataTFCF.AMLA, "amla", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/apricot", new ItemFoodTFCF(FoodDataTFCF.APRICOT, "apricot", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/avocado", new ItemFoodTFCF(FoodDataTFCF.AVOCADO, "avocado", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/bael", new ItemFoodTFCF(FoodDataTFCF.BAEL, "bael", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/bay_laurel", new ItemFoodTFCF(FoodDataTFCF.BAY_LAUREL, "bay_laurel", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/ber", new ItemFoodTFCF(FoodDataTFCF.BER, "ber", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/bergamot", new ItemFoodTFCF(FoodDataTFCF.BERGAMOT, "bergamot", "citrus", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/black_cherry", new ItemFoodTFCF(FoodDataTFCF.BLACK_CHERRY, "black_cherry", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/black_pepper", new ItemFoodTFCF(FoodDataTFCF.BLACK_PEPPER, "black_pepper", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/blackcurrant", new ItemFoodTFCF(FoodDataTFCF.BLACKCURRANT, "blackcurrant", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/blackthorn", new ItemFoodTFCF(FoodDataTFCF.BLACKTHORN, "blackthorn", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/buddha_hand", new ItemFoodTFCF(FoodDataTFCF.BUDDHA_HAND, "buddha_hand", "citrus", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/cacao", new ItemFoodTFCF(FoodDataTFCF.CACAO, "cacao", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/cherry_plum", new ItemFoodTFCF(FoodDataTFCF.CHERRY_PLUM, "cherry_plum", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/citron", new ItemFoodTFCF(FoodDataTFCF.CITRON, "citron", "citrus", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/crabapple", new ItemFoodTFCF(FoodDataTFCF.CRABAPPLE, "crabapple", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/damson_plum", new ItemFoodTFCF(FoodDataTFCF.DAMSON_PLUM, "damson_plum", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/date", new ItemFoodTFCF(FoodDataTFCF.DATE, "date", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/elder", new ItemFoodTFCF(FoodDataTFCF.ELDER, "elder", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/fig", new ItemFoodTFCF(FoodDataTFCF.FIG, "fig", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/finger_lime", new ItemFoodTFCF(FoodDataTFCF.FINGER_LIME, "finger_lime", "citrus", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/grapefruit", new ItemFoodTFCF(FoodDataTFCF.GRAPEFRUIT, "grapefruit", "citrus", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/guava", new ItemFoodTFCF(FoodDataTFCF.GUAVA, "guava", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/ice_cream_bean", new ItemFoodTFCF(FoodDataTFCF.ICE_CREAM_BEAN, "ice_cream_bean", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/jackfruit", new ItemFoodTFCF(FoodDataTFCF.JACKFRUIT, "jackfruit", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/jujube", new ItemFoodTFCF(FoodDataTFCF.JUJUBE, "jujube", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/kaki", new ItemFoodTFCF(FoodDataTFCF.KAKI, "kaki", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/key_lime", new ItemFoodTFCF(FoodDataTFCF.KEY_LIME, "key_lime", "citrus", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/kluwak", new ItemFoodTFCF(FoodDataTFCF.KLUWAK, "kluwak", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/kumquat", new ItemFoodTFCF(FoodDataTFCF.KUMQUAT, "kumquat", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/persian_lime", new ItemFoodTFCF(FoodDataTFCF.PERSIAN_LIME, "persian_lime", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/longan", new ItemFoodTFCF(FoodDataTFCF.LONGAN, "longan", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/loquat", new ItemFoodTFCF(FoodDataTFCF.LOQUAT, "loquat", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/lychee", new ItemFoodTFCF(FoodDataTFCF.LYCHEE, "lychee", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/mamey_sapote", new ItemFoodTFCF(FoodDataTFCF.MAMEY_SAPOTE, "mamey_sapote", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/manderin", new ItemFoodTFCF(FoodDataTFCF.MANDERIN, "manderin", "citrus", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/mango", new ItemFoodTFCF(FoodDataTFCF.MANGO, "mango", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/mangosteen", new ItemFoodTFCF(FoodDataTFCF.MANGOSTEEN, "mangosteen", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/nectarine", new ItemFoodTFCF(FoodDataTFCF.NECTARINE, "nectarine", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/ohia_ai", new ItemFoodTFCF(FoodDataTFCF.OHIA_AI, "ohia_ai", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/papaya", new ItemFoodTFCF(FoodDataTFCF.PAPAYA, "papaya", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/passion_fruit", new ItemFoodTFCF(FoodDataTFCF.PASSION_FRUIT, "passion_fruit", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/pear", new ItemFoodTFCF(FoodDataTFCF.PEAR, "pear", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/persimmon", new ItemFoodTFCF(FoodDataTFCF.PERSIMMON, "persimmon", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/peruvian_pepper", new ItemFoodTFCF(FoodDataTFCF.PERUVIAN_PEPPER, "peruvian_pepper", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/plantain", new ItemFoodTFCF(FoodDataTFCF.PLANTAIN, "plantain", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/pomegranate", new ItemFoodTFCF(FoodDataTFCF.POMEGRANATE, "pomegranate", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/pomelo", new ItemFoodTFCF(FoodDataTFCF.POMELO, "pomelo", "citrus", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/quince", new ItemFoodTFCF(FoodDataTFCF.QUINCE, "quince", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/rainier_cherry", new ItemFoodTFCF(FoodDataTFCF.RAINIER_CHERRY, "rainier_cherry", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/red_banana", new ItemFoodTFCF(FoodDataTFCF.RED_BANANA, "red_banana", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/red_currant", new ItemFoodTFCF(FoodDataTFCF.RED_CURRANT, "red_currant", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/sand_pear", new ItemFoodTFCF(FoodDataTFCF.SAND_PEAR, "sand_pear", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/sapodilla", new ItemFoodTFCF(FoodDataTFCF.SAPODILLA, "sapodilla", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/satsuma", new ItemFoodTFCF(FoodDataTFCF.SATSUMA, "satsuma", "citrus", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/sour_cherry", new ItemFoodTFCF(FoodDataTFCF.SOUR_CHERRY, "sour_cherry", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/soursop", new ItemFoodTFCF(FoodDataTFCF.SOURSOP, "soursop", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/starfruit", new ItemFoodTFCF(FoodDataTFCF.STARFRUIT, "starfruit", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/tamarillo", new ItemFoodTFCF(FoodDataTFCF.TAMARILLO, "tamarillo", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/tangerine", new ItemFoodTFCF(FoodDataTFCF.TANGERINE, "tangerine", "citrus", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/tropical_apricot", new ItemFoodTFCF(FoodDataTFCF.TROPICAL_APRICOT, "tropical_apricot", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/vanilla", new ItemFoodTFCF(FoodDataTFCF.VANILLA, "vanilla", "category_fruit"), CT_FOOD));
        */

        // Normal Tree Fruits
        simpleItems.add(register(r, "food/baobab_fruit", new ItemFoodTFCF(FoodDataTFCF.BAOBAB_FRUIT, "baobab_fruit", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/barrel_cactus_fruit", new ItemFoodTFCF(FoodDataTFCF.BARREL_CACTUS_FRUIT, "barrel_cactus_fruit", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/hawthorn", new ItemFoodTFCF(FoodDataTFCF.HAWTHORN, "hawthorn", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/juniper", new ItemFoodTFCF(FoodDataTFCF.JUNIPER, "juniper", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/osage_orange", new ItemFoodTFCF(FoodDataTFCF.OSAGE_ORANGE, "osage_orange", "citrus", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/pink_ivory_drupe", new ItemFoodTFCF(FoodDataTFCF.PINK_IVORY_DRUPE, "pink_ivory_drupe", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/riberry", new ItemFoodTFCF(FoodDataTFCF.RIBERRY, "riberry", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/rowan_berry", new ItemFoodTFCF(FoodDataTFCF.ROWAN_BERRY, "rowan_berry", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/sky_fruit", new ItemFoodTFCF(FoodDataTFCF.SKY_FRUIT, "sky_fruit", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/yew_berry", new ItemFoodTFCF(FoodDataTFCF.YEW_BERRY, new PotionEffectToHave(MobEffects.POISON, 610, 1, 3), new PotionEffectToHave(MobEffects.NAUSEA, 610, 1, 1), "yew_berry", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/yew_berry", new ItemFoodTFCF(FoodDataTFCF.ROASTED_YEW_BERRY, "roasted_yew_berry", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/mulberry", new ItemFoodTFCF(FoodDataTFCF.MULBERRY, "mulberry", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/glowberry", new ItemFoodTFCF(FoodDataTFCF.GLOWBERRY, new PotionEffectToHave(MobEffects.GLOWING, 300, 1, 3), new PotionEffectToHave(MobEffects.LUCK, 150, 2, 6), "glowberry", "category_fruit"), CT_FOOD));

        // Dried Berries & Fruits
        for (FruitsTFCF fruit : FruitsTFCF.values())
        {
            if (TFCFlorae.FirmaLifeAdded)
            {
                if (!fruit.isVanillaFood && fruit.canDry())
                {
                    ItemFoodTFCF dried = new ItemFoodTFCF(fruit.getDriedData());
                    simpleItems.add(register(r, "food/dried/" + fruit.name().toLowerCase(), dried, CT_FOOD));
                    OreDictionary.registerOre(OreDictionaryHelper.toString("dried_" + fruit.name().toLowerCase()), dried);
                    OreDictionary.registerOre("fruitDry", dried);
                    driedFruits.put(fruit, dried);
                }
            }
            else
            {
                if (fruit.canDry())
                {
                    ItemFoodTFCF dried = new ItemFoodTFCF(fruit.getDriedData());
                    simpleItems.add(register(r, "food/dried/" + fruit.name().toLowerCase(), dried, CT_FOOD));
                    OreDictionary.registerOre(OreDictionaryHelper.toString("dried_" + fruit.name().toLowerCase()), dried);
                    OreDictionary.registerOre("fruitDry", dried);
                    driedFruits.put(fruit, dried);
                }
            }
        }

        // Nuts & variants

        // Fruit Tree Nuts
        /*
        simpleItems.add(register(r, "food/almond", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "almond", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/almond_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "almond_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/almond_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_almond_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/brazil_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "brazil_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/brazil_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "brazil_nut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/brazil_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_brazil_nut_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/breadnut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "breadnut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/breadnut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "breadnut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/breadnut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_breadnut_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/bunya_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "bunya_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/bunya_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "bunya_nut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/bunya_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/candlenut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "candlenut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/candlenut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "candlenut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/candlenut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_candlenut_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/cashew", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "cashew", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/cashew_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "cashew_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/cashew_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_cashew_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/heartnut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "heartnut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/heartnut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "heartnut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/heartnut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_heartnut_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/kola_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "kola_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/kola_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "kola_nut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/kola_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_kola_nut_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/kukui_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "kukui_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/kukui_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "roasted_kukui_nut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/kukui_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_kukui_nut_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/macadamia", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "macadamia", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/macadamia_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "macadamia_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/macadamia_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_macadamia_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/mongongo", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "mongongo", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/mongongo_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "mongongo_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/mongongo_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_mongongo_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/monkey_puzzle_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "monkey_puzzle_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/monkey_puzzle_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "monkey_puzzle_nut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/monkey_puzzle_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_monkey_puzzle_nut_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/nutmeg", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "nutmeg", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/nutmeg_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "nutmeg_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/nutmeg_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_nutmeg_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/paradise_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "paradise_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/paradise_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "paradise_nut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/paradise_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_paradise_nut_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/pistachio", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "pistachio", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/pistachio_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "pistachio_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/pistachio_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_pistachio_nut", "category_fruit"), CT_FOOD));
        */

        // Normal Tree Nuts

        if (!TFCFlorae.FirmaLifeAdded)
        {
            simpleItems.add(register(r, "food/acorn", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "acorn", "category_fruit"), CT_FOOD));
            simpleItems.add(register(r, "food/acorn_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "acorn_nut", "category_fruit"), CT_FOOD));
            simpleItems.add(register(r, "food/roasted/acorn_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_acorn_nut", "category_fruit"), CT_FOOD));
        }

        simpleItems.add(register(r, "food/beechnut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "beechnut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/beechnut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "beechnut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/beechnut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_beechnut_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/black_walnut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "black_walnut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/black_walnut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "black_walnut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/black_walnut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_black_walnut_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/butternut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "butternut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/butternut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "butternut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/butternut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_butternut_nut", "category_fruit"), CT_FOOD));

        if (!TFCFlorae.FirmaLifeAdded)
        {
            simpleItems.add(register(r, "food/chestnut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "chestnut", "category_fruit"), CT_FOOD));
            simpleItems.add(register(r, "food/chestnut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "chestnut_nut", "category_fruit"), CT_FOOD));
            simpleItems.add(register(r, "food/roasted/chestnut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_chestnut_nut", "category_fruit"), CT_FOOD));
        }

        simpleItems.add(register(r, "food/ginkgo_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "ginkgo_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/ginkgo_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "ginkgo_nut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/ginkgo_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_ginkgo_nut_nut", "category_fruit"), CT_FOOD));

        simpleItems.add(register(r, "food/hazelnut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "hazelnut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/hazelnut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "hazelnut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/hazelnut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_", "category_fruit"), CT_FOOD));

        if (!TFCFlorae.FirmaLifeAdded)
        {
            simpleItems.add(register(r, "food/hickory_nut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "hickory_nut", "category_fruit"), CT_FOOD));
            simpleItems.add(register(r, "food/hickory_nut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "hickory_nut_nut", "category_fruit"), CT_FOOD));
            simpleItems.add(register(r, "food/roasted/hickory_nut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_hickory_nut_nut", "category_fruit"), CT_FOOD));

            simpleItems.add(register(r, "food/pecan", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "pecan", "category_fruit"), CT_FOOD));
            simpleItems.add(register(r, "food/pecan_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "pecan_nut", "category_fruit"), CT_FOOD));
            simpleItems.add(register(r, "food/roasted/pecan_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_pecan_nut", "category_fruit"), CT_FOOD));

            simpleItems.add(register(r, "food/pinecone", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "pinecone", "category_fruit"), CT_FOOD));
            simpleItems.add(register(r, "food/pine_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "pine_nut", "category_fruit"), CT_FOOD));
            simpleItems.add(register(r, "food/roasted/pine_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_pine_nut", "category_fruit"), CT_FOOD));
        }

        simpleItems.add(register(r, "food/walnut", new ItemFoodTFCF(FoodDataTFCF.UNCRACKED_NUT, "walnut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/walnut_nut", new ItemFoodTFCF(FoodDataTFCF.NUT, "walnut_nut", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/walnut_nut", new ItemFoodTFCF(FoodDataTFCF.ROASTED_NUT, "roasted_walnut_nut", "category_fruit"), CT_FOOD));

        /*
        // Uncooked Fungi
        // Epiphytes
        simpleItems.add(register(r, "food/artists_conk", new ItemFoodTFCF(FoodDataTFCF.RAW_ARTISTS_CONK, "raw_artists_conk", "epiphyte_artists_conk", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/sulphur_shelf", new ItemFoodTFCF(FoodDataTFCF.RAW_SULPHUR_SHELF, "raw_sulphur_shelf", "epiphyte_sulphur_shelf", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/turkey_tail", new ItemFoodTFCF(FoodDataTFCF.RAW_TURKEY_TAIL, "raw_turkey_tail", "epiphyte_turkey_tail", "category_vegetable"), CT_FOOD));

        // Mushrooms
        simpleItems.add(register(r, "food/porcini", new ItemFoodTFCF(FoodDataTFCF.RAW_PORCINI, "raw_porcini", "mushroom_porcini", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/amanita", new ItemFoodTFCF(FoodDataTFCF.RAW_AMANITA, new PotionEffectToHave(MobEffects.POISON, 610, 2, 1), new PotionEffectToHave(MobEffects.NAUSEA, 610, 3, 1), new PotionEffectToHave(MobEffects.INSTANT_DAMAGE, 1, 1, 2), "raw_amanita", "mushroom_amanita", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/black_powderpuff", new ItemFoodTFCF(FoodDataTFCF.RAW_BLACK_POWDERPUFF, "raw_black_powderpuff", "mushroom_black_powderpuff", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/chanterelle", new ItemFoodTFCF(FoodDataTFCF.RAW_CHANTERELLE, "raw_chanterelle", "mushroom_chanterelle", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/death_cap", new ItemFoodTFCF(FoodDataTFCF.RAW_DEATH_CAP, new PotionEffectToHave(MobEffects.POISON, 610, 2, 1), new PotionEffectToHave(MobEffects.NAUSEA, 610, 3, 1), new PotionEffectToHave(MobEffects.INSTANT_DAMAGE, 1, 1, 2), "raw_death_cap", "mushroom_death_cap", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/giant_club", new ItemFoodTFCF(FoodDataTFCF.RAW_GIANT_CLUB, "raw_giant_club", "mushroom_giant_club", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/parasol_mushroom", new ItemFoodTFCF(FoodDataTFCF.RAW_PARASOL_MUSHROOM, "raw_parasol_mushroom", "mushroom_parasol_mushroom", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/stinkhorn", new ItemFoodTFCF(FoodDataTFCF.RAW_STINKHORN, "raw_stinkhorn", "mushroom_stinkhorn", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/weeping_milk_cap", new ItemFoodTFCF(FoodDataTFCF.RAW_WEEPING_MILK_CAP, "raw_weeping_milk_cap", "mushroom_weeping_milk_cap", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/wood_blewit", new ItemFoodTFCF(FoodDataTFCF.RAW_WOOD_BLEWIT, "raw_wood_blewit", "mushroom_wood_blewit", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/woolly_gomphus", new ItemFoodTFCF(FoodDataTFCF.RAW_WOOLLY_GOMPHUS, new PotionEffectToHave(MobEffects.NAUSEA, 800, 1, 3), "raw_woolly_gomphus", "mushroom_woolly_gomphus", "category_vegetable"), CT_FOOD));
        */

        // Cooked Fungi
        // Epiphytes
        simpleItems.add(register(r, "food/roasted/artists_conk", new ItemFoodTFCF(FoodDataTFCF.ROASTED_ARTISTS_CONK, new PotionEffectToHave(MobEffects.NAUSEA, 600, 1, 2), new PotionEffectToHave(MobEffects.SLOWNESS, 600, 1, 2), "roasted_artists_conk", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/sulphur_shelf", new ItemFoodTFCF(FoodDataTFCF.ROASTED_SULPHUR_SHELF, new PotionEffectToHave(MobEffects.NAUSEA, 600, 1, 2), new PotionEffectToHave(MobEffects.SLOWNESS, 600, 1, 2), "roasted_sulphur_shelf", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/turkey_tail", new ItemFoodTFCF(FoodDataTFCF.ROASTED_TURKEY_TAIL, new PotionEffectToHave(MobEffects.NAUSEA, 600, 1, 2), new PotionEffectToHave(MobEffects.SLOWNESS, 600, 1, 2), "roasted_turkey_tail", "category_vegetable"), CT_FOOD));

        // Mushrooms
        simpleItems.add(register(r, "food/roasted/porcini", new ItemFoodTFCF(FoodDataTFCF.ROASTED_PORCINI, "roasted_porcini", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/amanita", new ItemFoodTFCF(FoodDataTFCF.ROASTED_AMANITA, new PotionEffectToHave(MobEffects.POISON, 610, 2, 1), new PotionEffectToHave(MobEffects.NAUSEA, 610, 3, 1), new PotionEffectToHave(MobEffects.INSTANT_DAMAGE, 1, 1, 2), "roasted_amanita", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/black_powderpuff", new ItemFoodTFCF(FoodDataTFCF.ROASTED_BLACK_POWDERPUFF, new PotionEffectToHave(MobEffects.INVISIBILITY, 610, 1, 2), "roasted_black_powderpuff", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/chanterelle", new ItemFoodTFCF(FoodDataTFCF.ROASTED_CHANTERELLE, new PotionEffectToHave(MobEffects.SPEED, 610, 2, 2), "roasted_chanterelle", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/death_cap", new ItemFoodTFCF(FoodDataTFCF.ROASTED_DEATH_CAP, new PotionEffectToHave(MobEffects.POISON, 610, 2, 1), new PotionEffectToHave(MobEffects.NAUSEA, 610, 3, 1), new PotionEffectToHave(MobEffects.INSTANT_DAMAGE, 1, 1, 2), "roasted_death_cap", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/giant_club", new ItemFoodTFCF(FoodDataTFCF.ROASTED_GIANT_CLUB, "roasted_giant_club", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/parasol_mushroom", new ItemFoodTFCF(FoodDataTFCF.ROASTED_PARASOL_MUSHROOM, "roasted_parasol_mushroom", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/stinkhorn", new ItemFoodTFCF(FoodDataTFCF.ROASTED_STINKHORN, "roasted_stinkhorn", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/weeping_milk_cap", new ItemFoodTFCF(FoodDataTFCF.ROASTED_WEEPING_MILK_CAP, "roasted_weeping_milk_cap", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/wood_blewit", new ItemFoodTFCF(FoodDataTFCF.ROASTED_WOOD_BLEWIT, new PotionEffectToHave(MobEffects.STRENGTH, 400, 1, 2), "roasted_wood_blewit", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/woolly_gomphus", new ItemFoodTFCF(FoodDataTFCF.ROASTED_WOOLLY_GOMPHUS, new PotionEffectToHave(MobEffects.NAUSEA, 800, 1, 2), "roasted_woolly_gomphus", "category_vegetable"), CT_FOOD));

        simpleItems.add(register(r, "food/raw_eel", new ItemFoodTFCF(FoodDataTFCF.RAW_EEL, new PotionEffectToHave(MobEffects.POISON, 610, 1, 3), new PotionEffectToHave(MobEffects.NAUSEA, 610, 2, 2), "raw_eel", "category_meat"), CT_FOOD));
        simpleItems.add(register(r, "food/cooked_eel", new ItemFoodTFCF(FoodDataTFCF.COOKED_EEL, "cooked_eel", "category_cooked_meat"), CT_FOOD));
        simpleItems.add(register(r, "food/raw_crab", new ItemFoodTFCF(FoodDataTFCF.RAW_CRAB, "raw_crab", "category_meat"), CT_FOOD));
        simpleItems.add(register(r, "food/cooked_crab", new ItemFoodTFCF(FoodDataTFCF.COOKED_CRAB, "cooked_crab", "category_cooked_meat"), CT_FOOD));
        simpleItems.add(register(r, "food/raw_clam", new ItemFoodTFCF(FoodDataTFCF.RAW_CLAM, new PotionEffectToHave(MobEffects.POISON, 610, 1, 3), new PotionEffectToHave(MobEffects.NAUSEA, 610, 2, 2), "raw_clam", "category_meat"), CT_FOOD));
        simpleItems.add(register(r, "food/cooked_clam", new ItemFoodTFCF(FoodDataTFCF.COOKED_CLAM, "cooked_clam", "category_cooked_meat"), CT_FOOD));
        simpleItems.add(register(r, "food/raw_scallop", new ItemFoodTFCF(FoodDataTFCF.RAW_SCALLOP, new PotionEffectToHave(MobEffects.POISON, 610, 1, 3), new PotionEffectToHave(MobEffects.NAUSEA, 610, 2, 2), "raw_scallop", "category_meat"), CT_FOOD));
        simpleItems.add(register(r, "food/cooked_scallop", new ItemFoodTFCF(FoodDataTFCF.COOKED_SCALLOP, "cooked_scallop", "category_cooked_meat"), CT_FOOD));
        simpleItems.add(register(r, "food/raw_starfish", new ItemFoodTFCF(FoodDataTFCF.RAW_STARFISH, new PotionEffectToHave(MobEffects.POISON, 610, 1, 3), new PotionEffectToHave(MobEffects.NAUSEA, 610, 2, 2), "raw_starfish", "category_meat"), CT_FOOD));
        simpleItems.add(register(r, "food/cooked_starfish", new ItemFoodTFCF(FoodDataTFCF.COOKED_STARFISH, "cooked_starfish", "category_cooked_meat"), CT_FOOD));
        simpleItems.add(register(r, "food/raw_snail", new ItemFoodTFCF(FoodDataTFCF.RAW_SNAIL, "raw_snail", "category_meat"), CT_FOOD));
        simpleItems.add(register(r, "food/cooked_snail", new ItemFoodTFCF(FoodDataTFCF.COOKED_SNAIL, "cooked_snail", "category_cooked_meat"), CT_FOOD));
        simpleItems.add(register(r, "food/raw_worm", new ItemFoodTFCF(FoodDataTFCF.RAW_WORM, "raw_worm", "category_meat"), CT_FOOD));
        simpleItems.add(register(r, "food/cooked_worm", new ItemFoodTFCF(FoodDataTFCF.COOKED_WORM, "cooked_worm", "category_cooked_meat"), CT_FOOD));

        simpleItems.add(register(r, "food/coffea_cherries", new ItemFoodTFCF(FoodDataTFCF.COFFEA_CHERRIES, "coffea_cherries", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/dried/coffea_cherries", new ItemFoodTFCF(FoodDataTFCF.DRIED_COFFEA_CHERRIES, "dried_coffea_cherries", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/roasted/coffee_beans", new ItemFoodTFCF(FoodDataTFCF.ROASTED_COFFEE_BEANS, "roasted_coffee", "roasted_coffee_beans", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/coffee_powder", new ItemFoodTFCF(FoodDataTFCF.COFFEE_POWDER, "dust_coffee", "powder_coffee", "category_fruit"), CT_FOOD));

        //simpleItems.add(register(r, "food/dried/black_pepper", new ItemFoodTFCF(FoodDataTFCF.DRIED_BLACK_PEPPER, "dried_black_pepper"), CT_FOOD));
        //simpleItems.add(register(r, "food/ground_black_pepper", new ItemFoodTFCF(FoodDataTFCF.GROUND_BLACK_PEPPER, "ground_black_pepper"), CT_FOOD));
        simpleItems.add(register(r, "food/allspice", new ItemFoodTFCF(FoodDataTFCF.ALLSPICE, "allspice", "crop_allspice", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/clove", new ItemFoodTFCF(FoodDataTFCF.CLOVE, "clove", "crop_clove", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/curry_leaf", new ItemFoodTFCF(FoodDataTFCF.CURRY_LEAF, "curry_leaf", "crop_curry_leaf"), CT_FOOD));
        simpleItems.add(register(r, "food/liquorice_root", new ItemFoodTFCF(FoodDataTFCF.LIQUORICE_ROOT, "liquorice_root", "crop_liquorice_root", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/cassia_cinnamon_bark", new ItemFoodTFCF(FoodDataTFCF.CASSIA_CINNAMON_BARK, "cassia_cinnamon_bark", "crop_cinnamon"), CT_FOOD));
        simpleItems.add(register(r, "food/ground_cassia_cinnamon", new ItemFoodTFCF(FoodDataTFCF.GROUND_CASSIA_CINNAMON, "ground_cassia_cinnamon", "powder_cassia_cinnamon", "dust_cinnamon", "powder_cinnamon", "food_groundcinnamon"), CT_FOOD));
        simpleItems.add(register(r, "food/ceylon_cinnamon_bark", new ItemFoodTFCF(FoodDataTFCF.CEYLON_CINNAMON_BARK, "ceylon_cinnamon_bark", "crop_Cinnamon"), CT_FOOD));
        simpleItems.add(register(r, "food/ground_ceylon_cinnamon", new ItemFoodTFCF(FoodDataTFCF.GROUND_CEYLON_CINNAMON, "ground_ceylon_cinnamon", "powder_ceylon_cinnamon", "dust_cinnamon", "powder_cinnamon", "food_groundcinnamon"), CT_FOOD));
        simpleItems.add(register(r, "food/black_tea", new ItemFoodTFCF(FoodDataTFCF.BLACK_TEA, "black_tea", "crop_black_tea"), CT_FOOD));
        simpleItems.add(register(r, "food/dried/black_tea", new ItemFoodTFCF(FoodDataTFCF.DRIED_BLACK_TEA, "dried_black_tea"), CT_FOOD));
        simpleItems.add(register(r, "food/green_tea", new ItemFoodTFCF(FoodDataTFCF.GREEN_TEA, "green_tea", "crop_green_tea"), CT_FOOD));
        simpleItems.add(register(r, "food/dried/green_tea", new ItemFoodTFCF(FoodDataTFCF.DRIED_GREEN_TEA, "dried_green_tea"), CT_FOOD));
        simpleItems.add(register(r, "food/white_tea", new ItemFoodTFCF(FoodDataTFCF.WHITE_TEA, "white_tea", "crop_white_tea"), CT_FOOD));
        simpleItems.add(register(r, "food/dried/white_tea", new ItemFoodTFCF(FoodDataTFCF.DRIED_WHITE_TEA, "dried_white_tea"), CT_FOOD));

        simpleItems.add(register(r, "food/amaranth", new ItemFoodTFCF(FoodDataTFCF.AMARANTH, "amaranth", "crop_amaranth", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/amaranth_grain", new ItemFoodTFCF(FoodDataTFCF.AMARANTH_GRAIN, "grain_amaranth", "grain", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/amaranth_flour", new ItemFoodTFCF(FoodDataTFCF.AMARANTH_FLOUR, "flour_amaranth", "flour", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/amaranth_dough", new ItemFoodTFCF(FoodDataTFCF.AMARANTH_DOUGH, "dough_amaranth", "dough", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/amaranth_bread", new ItemFoodTFCF(FoodDataTFCF.AMARANTH_BREAD, "bread_amaranth", "bread", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/buckwheat", new ItemFoodTFCF(FoodDataTFCF.BUCKWHEAT, "buckwheat", "crop_buckwheat", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/buckwheat_grain", new ItemFoodTFCF(FoodDataTFCF.BUCKWHEAT_GRAIN, "grain_buckwheat", "grain", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/buckwheat_flour", new ItemFoodTFCF(FoodDataTFCF.BUCKWHEAT_FLOUR, "flour_buckwheat", "flour", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/buckwheat_dough", new ItemFoodTFCF(FoodDataTFCF.BUCKWHEAT_DOUGH, "dough_buckwheat", "dough", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/buckwheat_bread", new ItemFoodTFCF(FoodDataTFCF.BUCKWHEAT_BREAD, "bread_buckwheat", "bread", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/fonio", new ItemFoodTFCF(FoodDataTFCF.FONIO, "fonio", "crop_fonio", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/fonio_grain", new ItemFoodTFCF(FoodDataTFCF.FONIO_GRAIN, "grain_fonio", "grain", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/fonio_flour", new ItemFoodTFCF(FoodDataTFCF.FONIO_FLOUR, "flour_fonio", "flour", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/fonio_dough", new ItemFoodTFCF(FoodDataTFCF.FONIO_DOUGH, "dough_fonio", "dough", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/fonio_bread", new ItemFoodTFCF(FoodDataTFCF.FONIO_BREAD, "bread_fonio", "bread", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/millet", new ItemFoodTFCF(FoodDataTFCF.MILLET, "millet", "crop_millet", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/millet_grain", new ItemFoodTFCF(FoodDataTFCF.MILLET_GRAIN, "grain_millet", "grain", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/millet_flour", new ItemFoodTFCF(FoodDataTFCF.MILLET_FLOUR, "flour_millet", "flour", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/millet_dough", new ItemFoodTFCF(FoodDataTFCF.MILLET_DOUGH, "dough_millet", "dough", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/millet_bread", new ItemFoodTFCF(FoodDataTFCF.MILLET_BREAD, "bread_millet", "bread", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/quinoa", new ItemFoodTFCF(FoodDataTFCF.QUINOA, "quinoa", "crop_quinoa", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/quinoa_grain", new ItemFoodTFCF(FoodDataTFCF.QUINOA_GRAIN, "grain_quinoa", "grain", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/quinoa_flour", new ItemFoodTFCF(FoodDataTFCF.QUINOA_FLOUR, "flour_quinoa", "flour", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/quinoa_dough", new ItemFoodTFCF(FoodDataTFCF.QUINOA_DOUGH, "dough_quinoa", "dough", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/quinoa_bread", new ItemFoodTFCF(FoodDataTFCF.QUINOA_BREAD, "bread_quinoa", "bread", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/spelt", new ItemFoodTFCF(FoodDataTFCF.SPELT, "spelt", "crop_spelt", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/spelt_grain", new ItemFoodTFCF(FoodDataTFCF.SPELT_GRAIN, "grain_spelt", "grain", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/spelt_flour", new ItemFoodTFCF(FoodDataTFCF.SPELT_FLOUR, "flour_spelt", "flour", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/spelt_dough", new ItemFoodTFCF(FoodDataTFCF.SPELT_DOUGH, "dough_spelt", "dough", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/spelt_bread", new ItemFoodTFCF(FoodDataTFCF.SPELT_BREAD, "bread_spelt", "bread", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/wild_barley", new ItemFoodTFCF(FoodDataTFCF.WILD_BARLEY, "wild_barley", "barley", "crop_barley", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/wild_rice", new ItemFoodTFCF(FoodDataTFCF.WILD_RICE, "wild_rice", "rice", "crop_wild_rice", "crop_rice", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/wild_wheat", new ItemFoodTFCF(FoodDataTFCF.WILD_WHEAT, "wild_wheat", "wheat", "crop_wild_wheat", "crop_wheat", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/linseed", new ItemFoodTFCF(FoodDataTFCF.LINSEED, "linseed", "crop_linseed", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/rape_seed", new ItemFoodTFCF(FoodDataTFCF.RAPE_SEED, "rape_seed", "crop_rape_seed", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/sunflower_seed", new ItemFoodTFCF(FoodDataTFCF.SUNFLOWER_SEED, "sunflower_seed", "crop_sunflower_seed", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/opium_poppy_seed", new ItemFoodTFCF(FoodDataTFCF.OPIUM_POPPY_SEED, "opium_poppy_seed", "crop_opium_poppy_seed", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/hash_muffin_dough", new ItemFoodTFCF(FoodDataTFCF.HASH_MUFFIN_DOUGH, new PotionEffectToHave(MobEffects.NAUSEA, 300, 2, 3), "dough_hash_muffin", "dough", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "food/hash_muffin", new ItemFoodTFCF(FoodDataTFCF.HASH_MUFFIN, new PotionEffectToHave(MobEffects.NAUSEA, 610, 3, 2), "hash_muffin", "bread", "category_meal"), CT_FOOD));
        simpleItems.add(register(r, "food/rutabaga", new ItemFoodTFCF(FoodDataTFCF.RUTABAGA, "rutabaga", "crop_rutabaga", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/turnip", new ItemFoodTFCF(FoodDataTFCF.TURNIP, "turnip", "crop_turnip", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/black_eyed_peas", new ItemFoodTFCF(FoodDataTFCF.BLACK_EYED_PEAS, "black_eyed_peas", "crop_black_eyed_peas", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/green_cayenne_pepper", new ItemFoodTFCF(FoodDataTFCF.GREEN_CAYENNE_PEPPER, "green_cayenne_pepper", "crop_green_cayenne_pepper", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/red_cayenne_pepper", new ItemFoodTFCF(FoodDataTFCF.RED_CAYENNE_PEPPER, "red_cayenne_pepper", "crop_red_cayenne_pepper", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/ginger", new ItemFoodTFCF(FoodDataTFCF.GINGER, "ginger", "crop_ginger", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/ginseng", new ItemFoodTFCF(FoodDataTFCF.GINSENG, "ginseng", "crop_ginseng", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/sugar_beet", new ItemFoodTFCF(FoodDataTFCF.SUGAR_BEET, "sugar_beet", "crop_sugar_beet", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/purple_grape", new ItemFoodTFCF(FoodDataTFCF.PURPLE_GRAPE, "purple_grape", "crop_purple_grape", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/green_grape", new ItemFoodTFCF(FoodDataTFCF.GREEN_GRAPE, "green_grape", "crop_green_grape", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/linseed_paste", new ItemFoodTFCF(FoodDataTFCF.LINSEED_PASTE, "paste_linseed", "linseed_paste", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/rape_seed_paste", new ItemFoodTFCF(FoodDataTFCF.RAPE_SEED_PASTE, "paste_rape_seed", "rape_seed_paste", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/sunflower_seed_paste", new ItemFoodTFCF(FoodDataTFCF.SUNFLOWER_SEED_PASTE, "paste_sunflower_seed", "sunflower_seed_paste", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/opium_poppy_seed_paste", new ItemFoodTFCF(FoodDataTFCF.OPIUM_POPPY_SEED_PASTE, "paste_opium_poppy_seed", "opium_poppy_seed_paste", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/mashed_sugar_beet", new ItemFoodTFCF(FoodDataTFCF.MASHED_SUGAR_BEET, "mashed_sugar_beet", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/mashed_sugar_cane", new ItemFoodTFCF(FoodDataTFCF.MASHED_SUGAR_CANE, "mashed_sugar_cane", "category_vegetable"), CT_FOOD));
        simpleItems.add(register(r, "food/soybean_paste", new ItemFoodTFCF(FoodDataTFCF.SOYBEAN_PASTE, "paste_soybean", "soybean_paste", "category_vegetable"), CT_FOOD));

        // Sandwiches
        simpleItems.add(register(r, "food/amaranth_bread_sandwich", new ItemSandwichTFCF(FoodDataTFCF.AMARANTH_BREAD_SANDWICH, "sandwich", "category_meal"), CT_FOOD));
        simpleItems.add(register(r, "food/buckwheat_bread_sandwich", new ItemSandwichTFCF(FoodDataTFCF.BUCKWHEAT_BREAD_SANDWICH, "sandwich", "category_meal"), CT_FOOD));
        simpleItems.add(register(r, "food/fonio_bread_sandwich", new ItemSandwichTFCF(FoodDataTFCF.FONIO_BREAD_SANDWICH, "sandwich", "category_meal"), CT_FOOD));
        simpleItems.add(register(r, "food/millet_bread_sandwich", new ItemSandwichTFCF(FoodDataTFCF.MILLET_BREAD_SANDWICH, "sandwich", "category_meal"), CT_FOOD));
        simpleItems.add(register(r, "food/quinoa_bread_sandwich", new ItemSandwichTFCF(FoodDataTFCF.QUINOA_BREAD_SANDWICH, "sandwich", "category_meal"), CT_FOOD));
        simpleItems.add(register(r, "food/spelt_bread_sandwich", new ItemSandwichTFCF(FoodDataTFCF.SPELT_BREAD_SANDWICH, "sandwich", "category_meal"), CT_FOOD));

        if (TFCFlorae.FirmaLifeAdded)
        {
            for (String grain : new String[] {"amaranth", "buckwheat", "fonio", "millet", "quinoa", "spelt"})
            {
                ItemFoodTFCF flatbread_dough = new ItemFoodTFCF(FoodDataTFCF.DOUGH);
                simpleItems.add(register(r, "food/" + grain + "_flatbread_dough", flatbread_dough, CT_FOOD));
                OreDictionary.registerOre(OreDictionaryHelper.toString(grain + "_flatbread_dough"), flatbread_dough);
                OreDictionary.registerOre("doughFlat", flatbread_dough);

                ItemFoodTFCF flatbread = new ItemFoodTFCF(FoodDataTFCF.FLATBREAD);
                simpleItems.add(register(r, "food/" + grain + "_flatbread", flatbread, CT_FOOD));
                OreDictionary.registerOre("flatbread", flatbread);
                OreDictionary.registerOre("categoryBread", flatbread);

                ItemFoodTFCF slice = new ItemFoodTFCF(FoodDataTFCF.SLICE);
                simpleItems.add(register(r, "food/" + grain + "_slice", slice, CT_FOOD));
                OreDictionary.registerOre("slice", slice);
                OreDictionary.registerOre("categoryBread", slice);
            }
        }

        simpleItems.add(register(r, "pomace", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "pomace", "category_fruit"), CT_MISC));
        simpleItems.add(register(r, "yeast", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "yeast"), CT_MISC));

        simpleItems.add(register(r, "firma_cola_mix", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "mix_firma_cola"), CT_MISC));
        simpleItems.add(register(r, "firma_cola_oils", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "oils_firma_cola"), CT_MISC));
        simpleItems.add(register(r, "firma_cola_blend", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "blend_firma_cola"), CT_MISC));

        simpleItems.add(register(r, "crop/product/chamomile_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "chamomile_head", "chamomile"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried/chamomile_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "dried_chamomile"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dandelion_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "dandelion_head", "dandelion"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried/dandelion_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "dried_dandelion"), CT_MISC));
        simpleItems.add(register(r, "crop/product/labrador_tea_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "labrador_tea_head", "labrador_tea"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried/labrador_tea_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "dried_labrador_tea"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sunflower_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "sunflower_head", "sunflower"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried/sunflower_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "dried_sunflower_head"), CT_MISC));

        simpleItems.add(register(r, "crop/product/malt_barley", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt_barley", "malt", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_corn", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt_corn", "malt", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_rice", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt_rice", "malt", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_rye", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt_rye", "malt", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_wheat", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt_wheat", "malt", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_amaranth", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt_amaranth", "malt", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_buckwheat", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt_buckwheat", "malt", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_fonio", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt_fonio", "malt", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_millet", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt_millet", "malt", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_quinoa", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt_quinoa", "malt", "category_grain"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_spelt", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt_spelt", "malt", "category_grain"), CT_FOOD));

        for (CropTFCF crop : CropTFCF.values())
        {
            simpleItems.add(register(r, "crop/seeds/" + crop.name().toLowerCase(), new ItemSeedsTFC(crop), CT_FOOD));
        }

        // Pottery
        {
            // Earthenware

            if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
            {
                for (Metal.ItemType type : Metal.ItemType.values())
                {
                    if (type.hasMold(null))
                    {
                        // Not using registerPottery here because the ItemMold uses a custom ItemModelMesher, meaning it can't be in simpleItems
                        ItemPottery item = new ItemEarthenwareMold(type);
                        ceramicItems.add(register(r, "ceramics/earthenware/fired/mold/" + type.name().toLowerCase(), item, CT_POTTERY));
                        simpleItems.add(register(r, "ceramics/earthenware/unfired/mold/" + type.name().toLowerCase(), new ItemUnfiredEarthenwareMold(type), CT_POTTERY));
                    }
                }

                if (TFCFlorae.TFCElementiaAdded)
                {
                    for (ItemMetalTFCE.ItemType type : ItemMetalTFCE.ItemType.values())
                    {
                        if (type.hasMold(null))
                        {
                            ItemPottery item = new ItemEarthenwareMoldTFCE(type, type.getSmeltAmount());
                            if (ItemMetalTFCE.ItemType.NAIL.isTypeActive() && type == ItemMetalTFCE.ItemType.NAIL)
                            {
                                ceramicItems.add(register(r, "ceramics/earthenware/fired/mold/" + type.name().toLowerCase(), item, CT_POTTERY));
                                simpleItems.add(register(r, "ceramics/earthenware/unfired/mold/" + type.name().toLowerCase(), new ItemUnfiredEarthenwareMoldTFCE(type), CT_POTTERY));
                            }
                            else if (ItemMetalTFCE.ItemType.RING.isTypeActive() && type == ItemMetalTFCE.ItemType.RING)
                            {
                                ceramicItems.add(register(r, "ceramics/earthenware/fired/mold/" + type.name().toLowerCase(), item, CT_POTTERY));
                                simpleItems.add(register(r, "ceramics/earthenware/unfired/mold/" + type.name().toLowerCase(), new ItemUnfiredEarthenwareMoldTFCE(type), CT_POTTERY));
                            }
                            else if (type == ItemMetalTFCE.ItemType.HALBERD_BLADE)
                            {
                                ceramicItems.add(register(r, "ceramics/earthenware/fired/mold/" + type.name().toLowerCase(), item, CT_POTTERY));
                                simpleItems.add(register(r, "ceramics/earthenware/unfired/mold/" + type.name().toLowerCase(), new ItemUnfiredEarthenwareMoldTFCE(type), CT_POTTERY));
                            }
                        }
                    }
                }

                if (TFCFlorae.FirmaLifeAdded)
                {
                    simpleItems.add(register(r, "ceramics/earthenware/unfired/mold/mallet_head", new ItemPottery(), CT_POTTERY));
                    malletMoldEarthenware = register(r, "ceramics/earthenware/fired/mold/mallet_head", new ItemEarthenwareMalletMoldFL("mallet"), CT_POTTERY);
                }

                simpleItems.add(register(r, "ceramics/earthenware/earthenware_clay", new ItemClayEarthenware(Size.VERY_SMALL, Weight.VERY_LIGHT, "clay_earthenware"), CT_MISC));
                registerPottery(simpleItems, r, "ceramics/earthenware/unfired/earthenware_brick", "ceramics/earthenware/fired/earthenware_brick");

                simpleItems.add(register(r, "ceramics/earthenware/unfired/large_vessel", new ItemUnfiredLargeVessel(), CT_POTTERY));

                registerPottery(simpleItems, r, "ceramics/earthenware/unfired/vessel", "ceramics/earthenware/fired/vessel", new ItemUnfiredSmallVessel(false), new ItemSmallVessel(false));
                registerPottery(null, r, "ceramics/earthenware/unfired/vessel_glazed", "ceramics/earthenware/fired/vessel_glazed", new ItemUnfiredSmallVessel(true), new ItemSmallVessel(true));

                ItemPottery firedPotEarthenware = new ItemPottery(Size.LARGE, Weight.LIGHT);
                registerPottery(simpleItems, r, "ceramics/earthenware/unfired/pot", "ceramics/earthenware/fired/pot", new ItemPottery(Size.LARGE, Weight.LIGHT), firedPotEarthenware);
                OreDictionaryHelper.register(firedPotEarthenware, "cooking_pot");

                ItemPottery firedBowlEarthenware = new ItemPottery(Size.VERY_SMALL, Weight.VERY_LIGHT);
                registerPottery(simpleItems, r, "ceramics/earthenware/unfired/bowl", "ceramics/earthenware/fired/bowl", new ItemPottery(Size.VERY_SMALL, Weight.VERY_LIGHT), firedBowlEarthenware);
                OreDictionaryHelper.register(firedBowlEarthenware, "bowl");

                simpleItems.add(register(r, "ceramics/earthenware/unfired/jug", new ItemPottery(), CT_POTTERY));
                register(r, "ceramics/earthenware/fired/jug", new ItemJug(), CT_POTTERY);
            }


            // Kaolinite

            if (ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
            {
                for (Metal.ItemType type : Metal.ItemType.values())
                {
                    if (type.hasMold(null))
                    {
                        // Not using registerPottery here because the ItemMold uses a custom ItemModelMesher, meaning it can't be in simpleItems
                        ItemPottery item = new ItemKaoliniteMold(type);
                        ceramicItems.add(register(r, "ceramics/kaolinite/fired/mold/" + type.name().toLowerCase(), item, CT_POTTERY));
                        simpleItems.add(register(r, "ceramics/kaolinite/unfired/mold/" + type.name().toLowerCase(), new ItemUnfiredKaoliniteMold(type), CT_POTTERY));
                    }
                }

                if (TFCFlorae.TFCElementiaAdded)
                {
                    for (ItemMetalTFCE.ItemType type : ItemMetalTFCE.ItemType.values())
                    {
                        if (type.hasMold(null))
                        {
                            ItemPottery item = new ItemKaoliniteMoldTFCE(type, type.getSmeltAmount());
                            if (ItemMetalTFCE.ItemType.NAIL.isTypeActive() && type == ItemMetalTFCE.ItemType.NAIL)
                            {
                                ceramicItems.add(register(r, "ceramics/kaolinite/fired/mold/" + type.name().toLowerCase(), item, CT_POTTERY));
                                simpleItems.add(register(r, "ceramics/kaolinite/unfired/mold/" + type.name().toLowerCase(), new ItemUnfiredKaoliniteMoldTFCE(type), CT_POTTERY));
                            }
                            else if (ItemMetalTFCE.ItemType.RING.isTypeActive() && type == ItemMetalTFCE.ItemType.RING)
                            {
                                ceramicItems.add(register(r, "ceramics/kaolinite/fired/mold/" + type.name().toLowerCase(), item, CT_POTTERY));
                                simpleItems.add(register(r, "ceramics/kaolinite/unfired/mold/" + type.name().toLowerCase(), new ItemUnfiredKaoliniteMoldTFCE(type), CT_POTTERY));
                            }
                            else if (type == ItemMetalTFCE.ItemType.HALBERD_BLADE)
                            {
                                ceramicItems.add(register(r, "ceramics/kaolinite/fired/mold/" + type.name().toLowerCase(), item, CT_POTTERY));
                                simpleItems.add(register(r, "ceramics/kaolinite/unfired/mold/" + type.name().toLowerCase(), new ItemUnfiredKaoliniteMoldTFCE(type), CT_POTTERY));
                            }
                        }
                    }
                }

                if (TFCFlorae.FirmaLifeAdded)
                {
                    simpleItems.add(register(r, "ceramics/kaolinite/unfired/mold/mallet_head", new ItemPottery(), CT_POTTERY));
                    malletMoldKaolinite = register(r, "ceramics/kaolinite/fired/mold/mallet_head", new ItemKaoliniteMalletMoldFL("mallet"), CT_POTTERY);
                }

                simpleItems.add(register(r, "ceramics/kaolinite/kaolinite_clay", new ItemClayKaolinite(Size.VERY_SMALL, Weight.VERY_LIGHT, "clay_kaolinite"), CT_MISC));
                registerPottery(simpleItems, r, "ceramics/kaolinite/unfired/kaolinite_brick", "ceramics/kaolinite/fired/kaolinite_brick");

                simpleItems.add(register(r, "ceramics/kaolinite/unfired/large_vessel", new ItemUnfiredLargeVessel(), CT_POTTERY));

                registerPottery(simpleItems, r, "ceramics/kaolinite/unfired/vessel", "ceramics/kaolinite/fired/vessel", new ItemUnfiredSmallVessel(false), new ItemSmallVessel(false));
                registerPottery(null, r, "ceramics/kaolinite/unfired/vessel_glazed", "ceramics/kaolinite/fired/vessel_glazed", new ItemUnfiredSmallVessel(true), new ItemSmallVessel(true));

                ItemPottery firedPotKaolinite = new ItemPottery(Size.LARGE, Weight.LIGHT);
                registerPottery(simpleItems, r, "ceramics/kaolinite/unfired/pot", "ceramics/kaolinite/fired/pot", new ItemPottery(Size.LARGE, Weight.LIGHT), firedPotKaolinite);
                OreDictionaryHelper.register(firedPotKaolinite, "cooking_pot");

                ItemPottery firedBowlKaolinite = new ItemPottery(Size.VERY_SMALL, Weight.VERY_LIGHT);
                registerPottery(simpleItems, r, "ceramics/kaolinite/unfired/bowl", "ceramics/kaolinite/fired/bowl", new ItemPottery(Size.VERY_SMALL, Weight.VERY_LIGHT), firedBowlKaolinite);
                OreDictionaryHelper.register(firedBowlKaolinite, "bowl");

                simpleItems.add(register(r, "ceramics/kaolinite/unfired/jug", new ItemPottery(), CT_POTTERY));
                register(r, "ceramics/kaolinite/fired/jug", new ItemJug(), CT_POTTERY);
            }


            // Stoneware

            if (ConfigTFCF.General.WORLD.enableAllStonewareClay)
            {
                for (Metal.ItemType type : Metal.ItemType.values())
                {
                    if (type.hasMold(null))
                    {
                        // Not using registerPottery here because the ItemMold uses a custom ItemModelMesher, meaning it can't be in simpleItems
                        ItemPottery item = new ItemStonewareMold(type);
                        ceramicItems.add(register(r, "ceramics/stoneware/fired/mold/" + type.name().toLowerCase(), item, CT_POTTERY));
                        simpleItems.add(register(r, "ceramics/stoneware/unfired/mold/" + type.name().toLowerCase(), new ItemUnfiredStonewareMold(type), CT_POTTERY));
                    }
                }

                if (TFCFlorae.TFCElementiaAdded)
                {
                    for (ItemMetalTFCE.ItemType type : ItemMetalTFCE.ItemType.values())
                    {
                        if (type.hasMold(null))
                        {
                            ItemPottery item = new ItemStonewareMoldTFCE(type, type.getSmeltAmount());
                            if (ItemMetalTFCE.ItemType.NAIL.isTypeActive() && type == ItemMetalTFCE.ItemType.NAIL)
                            {
                                ceramicItems.add(register(r, "ceramics/stoneware/fired/mold/" + type.name().toLowerCase(), item, CT_POTTERY));
                                simpleItems.add(register(r, "ceramics/stoneware/unfired/mold/" + type.name().toLowerCase(), new ItemUnfiredStonewareMoldTFCE(type), CT_POTTERY));
                            }
                            else if (ItemMetalTFCE.ItemType.RING.isTypeActive() && type == ItemMetalTFCE.ItemType.RING)
                            {
                                ceramicItems.add(register(r, "ceramics/stoneware/fired/mold/" + type.name().toLowerCase(), item, CT_POTTERY));
                                simpleItems.add(register(r, "ceramics/stoneware/unfired/mold/" + type.name().toLowerCase(), new ItemUnfiredStonewareMoldTFCE(type), CT_POTTERY));
                            }
                            else if (type == ItemMetalTFCE.ItemType.HALBERD_BLADE)
                            {
                                ceramicItems.add(register(r, "ceramics/stoneware/fired/mold/" + type.name().toLowerCase(), item, CT_POTTERY));
                                simpleItems.add(register(r, "ceramics/stoneware/unfired/mold/" + type.name().toLowerCase(), new ItemUnfiredStonewareMoldTFCE(type), CT_POTTERY));
                            }
                        }
                    }
                }

                if (TFCFlorae.FirmaLifeAdded)
                {
                    simpleItems.add(register(r, "ceramics/stoneware/unfired/mold/mallet_head", new ItemPottery(), CT_POTTERY));
                    malletMoldStoneware = register(r, "ceramics/stoneware/fired/mold/mallet_head", new ItemStonewareMalletMoldFL("mallet"), CT_POTTERY);
                }

                simpleItems.add(register(r, "ceramics/stoneware/stoneware_clay", new ItemClayStoneware(Size.VERY_SMALL, Weight.VERY_LIGHT, "clay_stoneware"), CT_MISC));
                registerPottery(simpleItems, r, "ceramics/stoneware/unfired/stoneware_brick", "ceramics/stoneware/fired/stoneware_brick");

                simpleItems.add(register(r, "ceramics/stoneware/unfired/large_vessel", new ItemUnfiredLargeVessel(), CT_POTTERY));

                registerPottery(simpleItems, r, "ceramics/stoneware/unfired/vessel", "ceramics/stoneware/fired/vessel", new ItemUnfiredSmallVessel(false), new ItemSmallVessel(false));
                registerPottery(null, r, "ceramics/stoneware/unfired/vessel_glazed", "ceramics/stoneware/fired/vessel_glazed", new ItemUnfiredSmallVessel(true), new ItemSmallVessel(true));

                ItemPottery firedPotStoneware = new ItemPottery(Size.LARGE, Weight.LIGHT);
                registerPottery(simpleItems, r, "ceramics/stoneware/unfired/pot", "ceramics/stoneware/fired/pot", new ItemPottery(Size.LARGE, Weight.LIGHT), firedPotStoneware);
                OreDictionaryHelper.register(firedPotStoneware, "cooking_pot");

                ItemPottery firedBowlStoneware = new ItemPottery(Size.VERY_SMALL, Weight.VERY_LIGHT);
                registerPottery(simpleItems, r, "ceramics/stoneware/unfired/bowl", "ceramics/stoneware/fired/bowl", new ItemPottery(Size.VERY_SMALL, Weight.VERY_LIGHT), firedBowlStoneware);
                OreDictionaryHelper.register(firedBowlStoneware, "bowl");

                simpleItems.add(register(r, "ceramics/stoneware/unfired/jug", new ItemPottery(), CT_POTTERY));
                register(r, "ceramics/stoneware/fired/jug", new ItemJug(), CT_POTTERY);
            }

            simpleItems.add(register(r, "storage/unfired/urn", new ItemUnfiredUrn(), CT_POTTERY));
        }

        for (BlockLogTFCF log : BlocksTFCF.getAllNormalTreeLog())
            simpleItems.add(register(r, log.getRegistryName().getPath(), new ItemBlockTFC(log), CT_WOOD));

        // Rock Type Items
        {
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
            {
                ItemMud mud = new ItemMud(rock);
                ItemUnfiredMudBrick unfiredMudBrick = new ItemUnfiredMudBrick(mud, rock);
                simpleItems.add(register(r, "mud/mud_ball/" + rock.getRegistryName().getPath().toLowerCase(), mud, CT_ROCK_ITEMS));
                simpleItems.add(register(r, "mud/unfired/mud_brick/" + rock.getRegistryName().getPath().toLowerCase(), unfiredMudBrick, CT_ROCK_ITEMS));
                simpleItems.add(register(r, "mud/fired/mud_brick/" + rock.getRegistryName().getPath().toLowerCase(), new ItemFiredMudBrick(unfiredMudBrick), CT_ROCK_ITEMS));
            }
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

        // Tools
        simpleItems.add(register(r, "tools/walking_stick", new ItemWalkingStick(ToolMaterial.WOOD, 1f, 1.5f, 0.02f, 96, "stick_wood", "walking_stick"), CT_MISC));

        simpleItems.add(register(r, "tools/flint/axe/flint", new ItemAxeTFCF(ToolMaterialsTFCF.FLINT, 2.5f, -3f, 50, "axe", "axe_flint", "axe_stone", "axe_stone_sedimentary"), CT_ROCK_ITEMS));
        simpleItems.add(register(r, "tools/flint/hammer/flint", new ItemHammerTFCF(ToolMaterialsTFCF.FLINT, 2.5f, -3f, 50, "hammer", "hammer_flint", "hammer_stone", "hammer_stone_sedimentary"), CT_ROCK_ITEMS));
        simpleItems.add(register(r, "tools/flint/hoe/flint", new ItemHoeTFCF(ToolMaterialsTFCF.FLINT, 2.25f, -2f, 50, "hoe", "hoe_flint", "hoe_stone", "hoe_stone_sedimentary"), CT_ROCK_ITEMS));
        simpleItems.add(register(r, "tools/flint/javelin/flint", new ItemJavelinTFCF(ToolMaterialsTFCF.FLINT, 1.95f, -1.8f, 50, "javelin", "javelin_flint", "javelin_stone", "javelin_stone_sedimentary"), CT_ROCK_ITEMS));
        simpleItems.add(register(r, "tools/flint/knife/flint", new ItemKnifeTFCF(ToolMaterialsTFCF.FLINT, 1.5f, -1.5f, 50, "knife", "knife_flint", "knife_stone", "knife_stone_sedimentary"), CT_ROCK_ITEMS));
        simpleItems.add(register(r, "tools/flint/shovel/flint", new ItemShovelTFCF(ToolMaterialsTFCF.FLINT, 1.25f, -3f, 50, "shovel", "shovel_flint", "shovel_stone", "shovel_stone_sedimentary"), CT_ROCK_ITEMS));

        // Toolheads
        simpleItems.add(register(r, "tools/flint/axe_head/flint", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "flint_head", "flint_head_axe"), CT_ROCK_ITEMS));
        simpleItems.add(register(r, "tools/flint/hammer_head/flint", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "flint_head", "flint_head_hammer"), CT_ROCK_ITEMS));
        simpleItems.add(register(r, "tools/flint/hoe_head/flint", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "flint_head", "flint_head_hoe"), CT_ROCK_ITEMS));
        simpleItems.add(register(r, "tools/flint/javelin_head/flint", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "flint_head", "flint_head_javelin"), CT_ROCK_ITEMS));
        simpleItems.add(register(r, "tools/flint/knife_head/flint", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "flint_head", "flint_head_knife"), CT_ROCK_ITEMS));
        simpleItems.add(register(r, "tools/flint/shovel_head/flint", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "flint_head", "flint_head_shovel"), CT_ROCK_ITEMS));

        // Bows
        /*itemBows.add(register(r, "tools/bows/shortbow/shortbow", new ItemBowTFCF(Size.VERY_SMALL, Weight.LIGHT, 250, 0, "bow", "bow_shortbow", "bow_wooden_shortbow"), CT_MISC));
        itemBows.add(register(r, "tools/bows/longbow/longbow", new ItemBowTFCF(Size.SMALL, Weight.MEDIUM, 450, 3, "bow", "bow_longbow", "bow_wooden_longbow"), CT_MISC));
        itemBows.add(register(r, "tools/bows/bonebow/bonebow", new ItemBowTFCF(Size.VERY_SMALL, Weight.LIGHT, 320, 2, "bow", "bow_bonebow"), CT_MISC));
        itemBows.add(register(r, "tools/bows/bow_of_lost_souls/bow_of_lost_souls", new ItemBowTFCF(Size.VERY_SMALL, Weight.LIGHT, 320, 2, "bow", "bow_bow_of_lost_souls"), CT_MISC));
        itemBows.add(register(r, "tools/bows/elite_power_bow/elite_power_bow", new ItemBowTFCF(Size.VERY_SMALL, Weight.LIGHT, 360, 3, "bow", "bow_elite_power_bow"), CT_MISC));
        itemBows.add(register(r, "tools/bows/green_menace/green_menace", new ItemBowTFCF(Size.VERY_SMALL, Weight.LIGHT, 384, 1, "bow", "bow_green_menace"), CT_MISC));
        itemBows.add(register(r, "tools/bows/hunting_bow/hunting_bow", new ItemBowTFCF(Size.VERY_SMALL, Weight.LIGHT, 384, 1, "bow", "bow_hunting_bow"), CT_MISC));
        itemBows.add(register(r, "tools/bows/nocturnal_bow/nocturnal_bow", new ItemBowTFCF(Size.VERY_SMALL, Weight.LIGHT, 384, 1, "bow", "bow_nocturnal_bow"), CT_MISC));
        itemBows.add(register(r, "tools/bows/red_snake/red_snake", new ItemBowTFCF(Size.VERY_SMALL, Weight.LIGHT, 384, 1, "bow", "bow_red_snake"), CT_MISC));
        itemBows.add(register(r, "tools/bows/rosebow/rosebow", new ItemBowTFCF(Size.VERY_SMALL, Weight.LIGHT, 384, 1, "bow", "bow_rosebow"), CT_MISC));
        itemBows.add(register(r, "tools/bows/sabrewing/sabrewing", new ItemBowTFCF(Size.VERY_SMALL, Weight.LIGHT, 384, 1, "bow", "bow_sabrewing"), CT_MISC));*/

        // Containers
        simpleItems.add(register(r, "container/leather_bag_piece", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "bag_piece", "bag_piece_leather"), CT_MISC));
        simpleItems.add(register(r, "container/leather_bag", new ItemBag("bag", "bag_leather"), CT_MISC));
        simpleItems.add(register(r, "container/pineapple_leather_bag_piece", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "bag_piece", "bag_piece_pineapple_leather"), CT_MISC));
        simpleItems.add(register(r, "container/pineapple_leather_bag", new ItemBag("bag", "bag_pineapple_leather"), CT_MISC));
        simpleItems.add(register(r, "container/burlap_sack_piece", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "sack_piece", "sack_piece_burlap"), CT_MISC));
        simpleItems.add(register(r, "container/burlap_sack", new ItemSack("sack", "sack_burlap"), CT_MISC));
        simpleItems.add(register(r, "container/wool_sack_piece", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "sack_piece", "sack_piece_wool"), CT_MISC));
        simpleItems.add(register(r, "container/wool_sack", new ItemSack("sack", "sack_wool"), CT_MISC));
        simpleItems.add(register(r, "container/silk_sack_piece", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "sack_piece", "sack_piece_silk"), CT_MISC));
        simpleItems.add(register(r, "container/silk_sack", new ItemSack("sack", "sack_silk"), CT_MISC));
        simpleItems.add(register(r, "container/cotton_sack_piece", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "sack_piece", "sack_piece_cotton"), CT_MISC));
        simpleItems.add(register(r, "container/cotton_sack", new ItemSack("sack", "sack_cotton"), CT_MISC));
        simpleItems.add(register(r, "container/hemp_sack_piece", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "sack_piece", "sack_piece_hemp"), CT_MISC));
        simpleItems.add(register(r, "container/hemp_sack", new ItemSack("sack", "sack_hemp"), CT_MISC));
        simpleItems.add(register(r, "container/linen_sack_piece", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "sack_piece", "sack_piece_linen"), CT_MISC));
        simpleItems.add(register(r, "container/linen_sack", new ItemSack("sack", "sack_linen"), CT_MISC));
        simpleItems.add(register(r, "container/sisal_sack_piece", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "sack_piece", "sack_piece_sisal"), CT_MISC));
        simpleItems.add(register(r, "container/sisal_sack", new ItemSack("sack", "sack_sisal"), CT_MISC));
        simpleItems.add(register(r, "container/yucca_sack_piece", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "sack_piece", "sack_piece_yucca"), CT_MISC));
        simpleItems.add(register(r, "container/yucca_sack", new ItemSack("sack", "sack_yucca"), CT_MISC));

        // Items
        simpleItems.add(register(r, "wooden_bucket_salt", new ItemMiscTFCF(Size.LARGE, Weight.MEDIUM, "bucket_salt", "bucket_wooden_salt"), CT_MISC));
        simpleItems.add(register(r, "wooden_bucket_sugar", new ItemMiscTFCF(Size.LARGE, Weight.MEDIUM, "bucket_sugar", "bucket_wooden_sugar"), CT_MISC));
        simpleItems.add(register(r, "animal/product/silk_moth_egg", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "egg_silk_moth", "egg"), CT_MISC));
        simpleItems.add(register(r, "animal/product/silk_worm_hatchery", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "hatchery", "hatchery_silk_worm"), CT_MISC));
        simpleItems.add(register(r, "animal/product/silk_worm", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "silk_worm"), CT_MISC));
        simpleItems.add(register(r, "animal/product/silk_worm_cocoon", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "cocoon", "cocoon_silk_worm"), CT_MISC));
        simpleItems.add(register(r, "animal/product/silk_worm_cocoon_boiled", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "cocoon_boiled", "cocoon_silk_worm_boiled"), CT_MISC));
        simpleItems.add(register(r, "crop/product/mulberry_leaf", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "leaf", "leaf_mulberry", "leaves", "leaves_mulberry"), CT_MISC));
        simpleItems.add(register(r, "logwood_chips", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "chips_logwood", "dust_logwood", "powder_logwood"), CT_MISC));
        //simpleItems.add(register(r, "resin", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "resin", "glue"), CT_MISC));
        simpleItems.add(register(r, "charred_bones", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "bone_charred"), CT_MISC));
        simpleItems.add(register(r, "conch", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "conch", "seashell"), CT_MISC));
        simpleItems.add(register(r, "clam", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "clam", "seashell"), CT_MISC));
        simpleItems.add(register(r, "live_clam", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "clam", "clam_live"), CT_MISC));
        simpleItems.add(register(r, "scallop", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "scallop", "seashell"), CT_MISC));
        simpleItems.add(register(r, "live_scallop", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "scallop", "scallop_live"), CT_MISC));
        simpleItems.add(register(r, "live_starfish", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "starfish", "starfish_live"), CT_MISC));
        simpleItems.add(register(r, "pearl", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "pearl"), CT_MISC));
        simpleItems.add(register(r, "black_pearl", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "pearl_black"), CT_MISC));

        simpleItems.add(register(r, "food/cannabis_bud", new ItemFoodTFCF(FoodDataTFCF.CANNABIS_BUD, new PotionEffectToHave(MobEffects.NAUSEA, 300, 2, 3), "cannabis_bud"), CT_FOOD));
        simpleItems.add(register(r, "food/dried/cannabis_bud", new ItemFoodTFCF(FoodDataTFCF.DRIED_CANNABIS_BUD, new PotionEffectToHave(MobEffects.NAUSEA, 300, 2, 3), "dried_cannabis_bud"), CT_FOOD));
        simpleItems.add(register(r, "food/cannabis_leaf", new ItemFoodTFCF(FoodDataTFCF.CANNABIS_LEAF, new PotionEffectToHave(MobEffects.NAUSEA, 300, 2, 3), "cannabis_leaf"), CT_FOOD));
        simpleItems.add(register(r, "food/dried/cannabis_leaf", new ItemFoodTFCF(FoodDataTFCF.DRIED_CANNABIS_LEAF, new PotionEffectToHave(MobEffects.NAUSEA, 300, 2, 3), "dried_cannabis_leaf"), CT_FOOD));
        simpleItems.add(register(r, "food/coca_leaf", new ItemFoodTFCF(FoodDataTFCF.COCA_LEAF, new PotionEffectToHave(MobEffects.NAUSEA, 300, 2, 3), "coca_leaf"), CT_FOOD));
        simpleItems.add(register(r, "food/dried/coca_leaf", new ItemFoodTFCF(FoodDataTFCF.DRIED_COCA_LEAF, new PotionEffectToHave(MobEffects.NAUSEA, 300, 2, 3), "dried_coca_leaf"), CT_FOOD));
        simpleItems.add(register(r, "food/opium_poppy_bulb", new ItemFoodTFCF(FoodDataTFCF.OPIUM_POPPY_BULB, new PotionEffectToHave(MobEffects.NAUSEA, 300, 2, 3), "opium_poppy_bulb"), CT_FOOD));
        simpleItems.add(register(r, "food/dried/opium_poppy_bulb", new ItemFoodTFCF(FoodDataTFCF.DRIED_OPIUM_POPPY_BULB, new PotionEffectToHave(MobEffects.NAUSEA, 300, 2, 3), "dried_opium_poppy_bulb"), CT_FOOD));
        simpleItems.add(register(r, "food/peyote", new ItemFoodTFCF(FoodDataTFCF.PEYOTE, new PotionEffectToHave(MobEffects.NAUSEA, 300, 2, 3), "peyote", "category_fruit"), CT_FOOD));
        simpleItems.add(register(r, "food/dried/peyote", new ItemFoodTFCF(FoodDataTFCF.DRIED_PEYOTE, new PotionEffectToHave(MobEffects.NAUSEA, 300, 2, 3), "dried_peyote"), CT_FOOD));
        simpleItems.add(register(r, "food/tobacco_leaf", new ItemFoodTFCF(FoodDataTFCF.TOBACCO_LEAF, new PotionEffectToHave(MobEffects.NAUSEA, 300, 2, 3), "tobacco_leaf"), CT_FOOD));
        simpleItems.add(register(r, "food/dried/tobacco_leaf", new ItemFoodTFCF(FoodDataTFCF.DRIED_TOBACCO_LEAF, new PotionEffectToHave(MobEffects.NAUSEA, 300, 2, 3), "dried_tobacco_leaf"), CT_FOOD));

        simpleItems.add(register(r, "cellulose_fibers", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "fiber", "fiber_cellulose"), CT_MISC));

        simpleItems.add(register(r, "crop/product/yucca_fiber", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "fiber", "fiber_yucca"), CT_MISC));
        simpleItems.add(register(r, "crop/product/yucca_string", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "string", "string_yucca"), CT_MISC));
        simpleItems.add(register(r, "crop/product/yucca_canvas", new ItemMiscTFCF(Size.VERY_SMALL, Weight.LIGHT, "cloth", "cloth_yucca", "fabric", "fabric_yucca", "canvas", "canvas_yucca"), CT_MISC));

        simpleItems.add(register(r, "crop/product/papyrus_pulp", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "pulp", "pulp_papyrus"), CT_MISC));
        simpleItems.add(register(r, "crop/product/papyrus_fiber", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "fiber", "fiber_papyrus"), CT_MISC));
        simpleItems.add(register(r, "crop/product/papyrus_paper", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "paper", "paper_papyrus"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/agave", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_agave", "agave"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_fiber", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "fiber", "fiber_sisal"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_string", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "string", "string_sisal"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_cloth", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "cloth", "cloth_sisal", "fabric", "fabric_sisal"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/cotton_boll", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_cotton", "cotton"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_yarn", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "string", "string_cotton", "yarn", "yarn_cotton"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_cloth", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "cloth", "cloth_high_quality", "cloth_cotton"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/flax", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "crop_flax", "flax"), CT_MISC));
        simpleItems.add(register(r, "crop/product/flax_fiber", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "fiber", "fiber_flax"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_string", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "string", "string_linen"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_cloth", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "cloth", "cloth_linen", "fabric", "fabric_linen"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/hemp", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "crop_hemp", "hemp"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_fiber", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "fiber", "fiber_hemp"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_string", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "string", "string_hemp"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_cloth", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "cloth", "cloth_hemp", "fabric", "fabric_hemp"), CT_MISC));

        simpleItems.add(register(r, "crop/product/madder", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_madder", "madder"), CT_MISC));
        simpleItems.add(register(r, "crop/product/weld", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_weld", "weld"), CT_MISC));
        simpleItems.add(register(r, "crop/product/woad", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_woad", "woad"), CT_MISC));
        simpleItems.add(register(r, "crop/product/indigo", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_indigo", "indigo"), CT_MISC));
        simpleItems.add(register(r, "crop/product/rape", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_rape", "rape"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hops", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_hops", "hops"), CT_MISC));

        simpleItems.add(register(r, "crop/product/silk_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_silk"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_sisal"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_cotton"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_linen"), CT_MISC));
        simpleItems.add(register(r, "crop/product/papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_papyrus"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_hemp"), CT_MISC));

        simpleItems.add(register(r, "crop/product/olive_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_silk_olive"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/olive_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_sisal_olive"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/olive_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_cotton_olive"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/olive_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_linen_olive"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/olive_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_papyrus_olive"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/olive_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_hemp_olive"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/soybean_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_jute_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_silk_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_sisal_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_cotton_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_linen_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_papyrus_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_hemp_soybean"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/linseed_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_jute_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_silk_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_sisal_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_cotton_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_linen_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_papyrus_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_hemp_linseed"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/rape_seed_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_jute_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_silk_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_sisal_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_cotton_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_linen_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_papyrus_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_hemp_rape_seed"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/sunflower_seed_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_jute_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_silk_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_sisal_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_cotton_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_linen_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_papyrus_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_hemp_sunflower_seed"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/opium_poppy_seed_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_jute_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_silk_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_sisal_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_cotton_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_linen_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_papyrus_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_hemp_opium_poppy_seed"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/sugar_beet_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_jute_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_silk_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_sisal_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_cotton_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_linen_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_papyrus_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_hemp_sugar_beet"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/sugar_cane_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_sisal_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_silk_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_silk_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_sisal_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_cotton_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_linen_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_papyrus_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_papyrus_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "disc", "disc_hemp_sugar_cane"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/silk_net", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "net", "net_silk"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_net", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "net", "net_sisal"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_net", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "net", "net_cotton"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_net", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "net", "net_linen"), CT_MISC));
        simpleItems.add(register(r, "crop/product/papyrus_net", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "net", "net_papyrus"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_net", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "net", "net_hemp"), CT_MISC));

        simpleItems.add(register(r, "crop/product/dirty_silk_net", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "net", "net_silk_dirty"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dirty_sisal_net", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "net", "net_sisal_dirty"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dirty_cotton_net", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "net", "net_cotton_dirty"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dirty_linen_net", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "net", "net_linen_dirty"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dirty_papyrus_net", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "net", "net_papyrus_dirty"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dirty_hemp_net", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "net", "net_hemp_dirty"), CT_MISC));

        // Armors
        /*armorItems.add(register(r, "armor/helmet/pineapple_leather", new ItemArmorTFCF(ArmorMaterialsTFCF.PINEAPPLE_LEATHER, 0, EntityEquipmentSlot.HEAD), CT_MISC));
        armorItems.add(register(r, "armor/chestplate/pineapple_leather", new ItemArmorTFCF(ArmorMaterialsTFCF.PINEAPPLE_LEATHER, 1, EntityEquipmentSlot.CHEST), CT_MISC));
        armorItems.add(register(r, "armor/leggings/pineapple_leather", new ItemArmorTFCF(ArmorMaterialsTFCF.PINEAPPLE_LEATHER, 2, EntityEquipmentSlot.LEGS), CT_MISC));
        armorItems.add(register(r, "armor/boots/pineapple_leather", new ItemArmorTFCF(ArmorMaterialsTFCF.PINEAPPLE_LEATHER, 3, EntityEquipmentSlot.FEET), CT_MISC));

        armorItems.add(register(r, "armor/helmet/burlap_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.BURLAP_CLOTH, 0, EntityEquipmentSlot.HEAD), CT_MISC));
        armorItems.add(register(r, "armor/chestplate/burlap_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.BURLAP_CLOTH, 1, EntityEquipmentSlot.CHEST), CT_MISC));
        armorItems.add(register(r, "armor/leggings/burlap_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.BURLAP_CLOTH, 2, EntityEquipmentSlot.LEGS), CT_MISC));
        armorItems.add(register(r, "armor/boots/burlap_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.BURLAP_CLOTH, 3, EntityEquipmentSlot.FEET), CT_MISC));

        armorItems.add(register(r, "armor/helmet/wool_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.WOOL_CLOTH, 0, EntityEquipmentSlot.HEAD), CT_MISC));
        armorItems.add(register(r, "armor/chestplate/wool_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.WOOL_CLOTH, 1, EntityEquipmentSlot.CHEST), CT_MISC));
        armorItems.add(register(r, "armor/leggings/wool_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.WOOL_CLOTH, 2, EntityEquipmentSlot.LEGS), CT_MISC));
        armorItems.add(register(r, "armor/boots/wool_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.WOOL_CLOTH, 3, EntityEquipmentSlot.FEET), CT_MISC));

        armorItems.add(register(r, "armor/helmet/silk_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.SILK_CLOTH, 0, EntityEquipmentSlot.HEAD), CT_MISC));
        armorItems.add(register(r, "armor/chestplate/silk_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.SILK_CLOTH, 1, EntityEquipmentSlot.CHEST), CT_MISC));
        armorItems.add(register(r, "armor/leggings/silk_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.SILK_CLOTH, 2, EntityEquipmentSlot.LEGS), CT_MISC));
        armorItems.add(register(r, "armor/boots/silk_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.SILK_CLOTH, 3, EntityEquipmentSlot.FEET), CT_MISC));

        armorItems.add(register(r, "armor/helmet/sisal_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.SISAL_CLOTH, 0, EntityEquipmentSlot.HEAD), CT_MISC));
        armorItems.add(register(r, "armor/chestplate/sisal_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.SISAL_CLOTH, 1, EntityEquipmentSlot.CHEST), CT_MISC));
        armorItems.add(register(r, "armor/leggings/sisal_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.SISAL_CLOTH, 2, EntityEquipmentSlot.LEGS), CT_MISC));
        armorItems.add(register(r, "armor/boots/sisal_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.SISAL_CLOTH, 3, EntityEquipmentSlot.FEET), CT_MISC));

        armorItems.add(register(r, "armor/helmet/cotton_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.COTTON_CLOTH, 0, EntityEquipmentSlot.HEAD), CT_MISC));
        armorItems.add(register(r, "armor/chestplate/cotton_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.COTTON_CLOTH, 1, EntityEquipmentSlot.CHEST), CT_MISC));
        armorItems.add(register(r, "armor/leggings/cotton_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.COTTON_CLOTH, 2, EntityEquipmentSlot.LEGS), CT_MISC));
        armorItems.add(register(r, "armor/boots/cotton_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.COTTON_CLOTH, 3, EntityEquipmentSlot.FEET), CT_MISC));

        armorItems.add(register(r, "armor/helmet/linen_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.LINEN_CLOTH, 0, EntityEquipmentSlot.HEAD), CT_MISC));
        armorItems.add(register(r, "armor/chestplate/linen_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.LINEN_CLOTH, 1, EntityEquipmentSlot.CHEST), CT_MISC));
        armorItems.add(register(r, "armor/leggings/linen_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.LINEN_CLOTH, 2, EntityEquipmentSlot.LEGS), CT_MISC));
        armorItems.add(register(r, "armor/boots/linen_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.LINEN_CLOTH, 3, EntityEquipmentSlot.FEET), CT_MISC));

        armorItems.add(register(r, "armor/helmet/hemp_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.HEMP_CLOTH, 0, EntityEquipmentSlot.HEAD), CT_MISC));
        armorItems.add(register(r, "armor/chestplate/hemp_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.HEMP_CLOTH, 1, EntityEquipmentSlot.CHEST), CT_MISC));
        armorItems.add(register(r, "armor/leggings/hemp_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.HEMP_CLOTH, 2, EntityEquipmentSlot.LEGS), CT_MISC));
        armorItems.add(register(r, "armor/boots/hemp_cloth", new ItemArmorTFCF(ArmorMaterialsTFCF.HEMP_CLOTH, 3, EntityEquipmentSlot.FEET), CT_MISC));

        armorItems.add(register(r, "armor/helmet/yucca_canvas", new ItemArmorTFCF(ArmorMaterialsTFCF.YUCCA_CANVAS, 0, EntityEquipmentSlot.HEAD), CT_MISC));
        armorItems.add(register(r, "armor/chestplate/yucca_canvas", new ItemArmorTFCF(ArmorMaterialsTFCF.YUCCA_CANVAS, 1, EntityEquipmentSlot.CHEST), CT_MISC));
        armorItems.add(register(r, "armor/leggings/yucca_canvas", new ItemArmorTFCF(ArmorMaterialsTFCF.YUCCA_CANVAS, 2, EntityEquipmentSlot.LEGS), CT_MISC));
        armorItems.add(register(r, "armor/boots/yucca_canvas", new ItemArmorTFCF(ArmorMaterialsTFCF.YUCCA_CANVAS, 3, EntityEquipmentSlot.FEET), CT_MISC));*/

        simpleItems.add(register(r, "devices/flora_density_meter", new ItemFloraDensity(Size.VERY_SMALL, Weight.VERY_LIGHT), CT_MISC));
        simpleItems.add(register(r, "devices/season_clock", new ItemCalendarClock(Size.VERY_SMALL, Weight.VERY_LIGHT), CT_MISC));

        // Cassia cinnamon
        ItemMisc cassiaPole = new ItemMisc(Size.SMALL, Weight.MEDIUM);
        simpleItems.add(register(r, "wood/fruit_tree/pole/cassia_cinnamon", cassiaPole, CT_WOOD));
        OreDictionary.registerOre("poleCassiaCinnamon", cassiaPole);
        OreDictionary.registerOre("poleWooden", cassiaPole);

        ItemMisc cassiaLumber = new ItemMisc(Size.SMALL, Weight.VERY_LIGHT);
        simpleItems.add(register(r, "wood/fruit_tree/lumber/cassia_cinnamon", cassiaLumber, CT_WOOD));
        OreDictionary.registerOre("lumberCassiaCinnamon", cassiaLumber);
        
        simpleItems.add(register(r, "wood/fruit_tree/boat/cassia_cinnamon", new ItemBoatTFCF(TreesTFCF.CASSIA_CINNAMON_TREE), CT_WOOD));

        // Ceylon cinnamon
        ItemMisc ceylonPole = new ItemMisc(Size.SMALL, Weight.MEDIUM);
        simpleItems.add(register(r, "wood/fruit_tree/pole/ceylon_cinnamon", ceylonPole, CT_WOOD));
        OreDictionary.registerOre("poleCeylonCinnamon", ceylonPole);
        OreDictionary.registerOre("poleWooden", ceylonPole);

        ItemMisc ceylonLumber = new ItemMisc(Size.SMALL, Weight.VERY_LIGHT);
        simpleItems.add(register(r, "wood/fruit_tree/lumber/ceylon_cinnamon", ceylonLumber, CT_WOOD));
        OreDictionary.registerOre("lumberCeylonCinnamon", ceylonLumber);
        
        simpleItems.add(register(r, "wood/fruit_tree/boat/ceylon_cinnamon", new ItemBoatTFCF(TreesTFCF.CEYLON_CINNAMON_TREE), CT_WOOD));

        for (int i = 0; i < BlocksTFCF.bamboo.length; i++)
        {
            ItemMisc bambooPole = new ItemMisc(Size.SMALL, Weight.MEDIUM);
            simpleItems.add(register(r, "wood/pole/" + BlocksTFCF.bamboo[i], bambooPole, CT_WOOD));
            OreDictionary.registerOre(OreDictionaryHelper.toString("pole_" + BlocksTFCF.bamboo[i]), bambooPole);
            ((BlockBambooLog) BlocksTFCF.getAllBambooLog().get(i)).setDrop(bambooPole);

            ItemMisc bambooLumber = new ItemMisc(Size.SMALL, Weight.VERY_LIGHT);
            simpleItems.add(register(r, "wood/lumber/" + BlocksTFCF.bamboo[i], bambooLumber, CT_WOOD));
            OreDictionary.registerOre(OreDictionaryHelper.toString("lumber_" + BlocksTFCF.bamboo[i]), bambooLumber);
            
            simpleItems.add(register(r, "wood/boat/" + BlocksTFCF.bamboo[i], new ItemBoatTFCF(BlocksTFCF.bambooTrees[i]), CT_WOOD));
        }

        /*for (SeasonalTrees fruitTree : SeasonalTrees.values())
        {
            if (!fruitTree.isNormalTree)
            {
                // Poles
                String name = fruitTree.getName().toLowerCase();
                ItemMisc pole = new ItemMisc(Size.SMALL, Weight.MEDIUM);
                simpleItems.add(register(r, "wood/fruit_tree/pole/" + name, pole, CT_WOOD));
                OreDictionary.registerOre(OreDictionaryHelper.toString("pole_" + name.substring(0,1).toLowerCase() + name.substring(1).toLowerCase()), pole);

                // Lumber
                ItemMisc lumber = new ItemMisc(Size.SMALL, Weight.VERY_LIGHT);
                simpleItems.add(register(r, "wood/fruit_tree/lumber/" + name, lumber, CT_WOOD));
                OreDictionary.registerOre(OreDictionaryHelper.toString("lumber_" + name.substring(0,1).toLowerCase() + name.substring(1).toLowerCase()), lumber);

                simpleItems.add(register(r, "wood/fruit_tree/boat/" + name, new ItemBoatTFCF(fruitTree), CT_WOOD));
            }
        }*/

        for (IFruitTree fruitTree : FruitTree.values())
        {
            String name = fruitTree.getName().toLowerCase();

            // Poles
            if (!TFCFlorae.FirmaLifeAdded)
            {
                ItemMisc pole = new ItemMisc(Size.SMALL, Weight.MEDIUM);
                simpleItems.add(register(r, "wood/fruit_tree/pole/" + name, pole, CT_WOOD));
                OreDictionary.registerOre(OreDictionaryHelper.toString("pole_" + name.substring(0,1).toLowerCase() + name.substring(1).toLowerCase()), pole);
            }

            // Lumber
            ItemMisc lumber = new ItemMisc(Size.SMALL, Weight.VERY_LIGHT);
            simpleItems.add(register(r, "wood/fruit_tree/lumber/" + name, lumber, CT_WOOD));
            OreDictionary.registerOre(OreDictionaryHelper.toString("lumber_" + name.substring(0,1).toLowerCase() + name.substring(1).toLowerCase()), lumber);

            simpleItems.add(register(r, "wood/fruit_tree/boat/" + name, new ItemBoatTFCF(fruitTree), CT_WOOD));
        }

        for (BlockFruitDoor blockDoor : BlocksTFCF.getAllFruitDoors())
        {
            ItemFruitDoor itemDoor = new ItemFruitDoor(blockDoor);
            fruitDoors.add(register(r, blockDoor.getRegistryName().getPath(), itemDoor, CT_DECORATIONS));
            OreDictionary.registerOre(OreDictionaryHelper.toString("door_wood"), itemDoor);
            OreDictionary.registerOre(OreDictionaryHelper.toString("door_wood_" + blockDoor.Name), itemDoor);
        }

        for (BlockFruitSlab.Half slab : BlocksTFCF.getAllFruitSlabBlocks())
            simpleItems.add(register(r, slab.getRegistryName().getPath(), new ItemSlabTFCF(slab, slab, slab.doubleSlab), CT_DECORATIONS));

        for (BlockSlabTFC.Half slab : BlocksTFCF.getAllSlabBlocksTFC())
                simpleItems.add(register(r, slab.getRegistryName().getPath(), new ItemSlabTFC(slab, slab, slab.doubleSlab), CT_DECORATIONS));

        for (BlockSlabTFCF.Half slab : BlocksTFCF.getAllSlabBlocks())
            simpleItems.add(register(r, slab.getRegistryName().getPath(), new ItemSlabTFCF(slab, slab, slab.doubleSlab), CT_DECORATIONS));

        if (TFCFlorae.FirmaLifeAdded)
        {
            // Cinnamon
            ItemMisc cinnamonLumber = new ItemMisc(Size.SMALL, Weight.VERY_LIGHT);
            simpleItems.add(register(r, "wood/fruit_tree/lumber/cinnamon", cinnamonLumber, CT_WOOD));
            OreDictionary.registerOre("lumberCinnamon", cinnamonLumber);

            simpleItems.add(register(r, "wood/fruit_tree/boat/cinnamon", new ItemBoatTFCF(PlantsFL.CINNAMON_TREE), CT_WOOD));
    
            for (FruitTreeFL fruitTree : FruitTreeFL.values())
            {
                // Lumber
                String name = fruitTree.getName().toLowerCase();
                ItemMisc lumber = new ItemMisc(Size.SMALL, Weight.VERY_LIGHT);
                simpleItems.add(register(r, "wood/fruit_tree/lumber/" + name, lumber, CT_WOOD));
                OreDictionary.registerOre(OreDictionaryHelper.toString("lumber_" + name.substring(0,1).toLowerCase() + name.substring(1).toLowerCase()), lumber);

                simpleItems.add(register(r, "wood/fruit_tree/boat/" + name, new ItemBoatTFCF(fruitTree), CT_WOOD));
            }
        }

        allFruitDoors = fruitDoors.build();

        BlocksTFCF.getAllNormalItemBlocks().forEach((x) -> {
            registerItemBlock(r, x);
        });
        BlocksTFCF.getAllItemBlockCondenser().forEach(x -> registerItemBlock(r, x));

        allSimpleItems = simpleItems.build();
        //allItemBows = itemBows.build();
        allArmorItems = armorItems.build();

        if (ConfigTFCF.General.WORLD.enableAllEarthenwareClay || ConfigTFCF.General.WORLD.enableAllKaoliniteClay || ConfigTFCF.General.WORLD.enableAllStonewareClay)
        {
            allCeramicMoldItems = ceramicItems.build();
        }

        OreDictionaryHelper.init();
    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerVanillaOverrides(RegistryEvent.Register<Item> event)
    {
        // Vanilla Overrides. Used for small tweaks on vanilla items, rather than replacing them outright
        TFCFlorae.getLog().info("The below warnings about unintended overrides are normal. The overrides are intended - deal with it. ;)");
        event.getRegistry().registerAll(
            new ItemFlint(Size.VERY_SMALL, Weight.VERY_LIGHT).setRegistryName(Items.FLINT.getRegistryName()).setTranslationKey("flint"),
            new ItemBlockStickBundle(BlocksTFCF.STICK_BUNDLE).setRegistryName(MOD_ID, "stick_bundle")
        );

        for (Powder powder : Powder.values())
        {
            event.getRegistry().registerAll(
                new ItemPowderTFC(powder).setRegistryName(MOD_ID, "powder/" + powder.name().toLowerCase()).setTranslationKey("powder." + powder.name().toLowerCase())
            );
        }

        for (PowderTFCE powder : PowderTFCE.values())
        {
            event.getRegistry().registerAll(
                new ItemPowderTFCE(powder).setRegistryName(TFCElementia.MODID, "powder/" + powder.name().toLowerCase()).setTranslationKey("powder." + powder.name().toLowerCase())
            );
        }
    }

    private static void registerPottery(Builder<Item> items, IForgeRegistry<Item> r, String nameUnfired, String nameFired)
    {
        registerPottery(items, r, nameUnfired, nameFired, new ItemPottery(), new ItemPottery());
    }

    private static void registerPottery(Builder<Item> items, IForgeRegistry<Item> r, String nameUnfired, String nameFired, ItemPottery unfiredItem, ItemPottery firedItem)
    {
        register(r, nameFired, firedItem, CT_POTTERY);
        register(r, nameUnfired, unfiredItem, CT_POTTERY);

        if (items != null)
        {
            items.add(firedItem, unfiredItem);
        }
    }

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