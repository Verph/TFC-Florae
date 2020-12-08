/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package tfcflorae.util.agriculture;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

//import net.dries007.tfc.api.capability.food.FoodData;
import tfcflorae.api.capability.food.FoodDataTFCF;
import tfcflorae.util.OreDictionaryHelper;

import static tfcflorae.util.agriculture.FoodTFCF.Category.*;

public enum FoodTFCF
{
	// Fruits
    ABIU(FRUIT, 4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f, "abiu"),
    AMLA(FRUIT, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f, "amla"),
    APRICOT(FRUIT, 4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "apricot"),
    AVOCADO(FRUIT, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 1.8f, "avocado"),
    BAEL(FRUIT, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f, "bael"),
    BAY_LAUREL(FRUIT, 4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "bay_laurel"),
    BER(FRUIT, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f, "ber"),
    BERGAMOT(FRUIT, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f, "bergamot"),
    BLACK_CHERRY(FRUIT, 4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 1.6f, "black_cherry"),
    BLACKCURRANT(FRUIT, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f, "blackcurrant"),
    BLACKTHORN(FRUIT, 4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f, "blackthorn"),
    BUDDHA_HAND(FRUIT, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f, "buddha_hand"),
    CACAO(FRUIT, 4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f, "cacao", "cocoa"),
    CHERRY_PLUM(FRUIT, 4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f, "cherry_plum"),
    CITRON(FRUIT, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f, "citron", "lemon"),
    COCONUT(FRUIT, 4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f, "coconut"),
    COFFEA(FRUIT, 4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f, "coffee"),
    CRABAPPLE(FRUIT, 4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f, "crabapple"),
    DAMSON_PLUM(FRUIT, 4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f, "damson_plum"),
    DATE(FRUIT, 4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "date"),
    ELDER(FRUIT, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f, "elder"),
    FIG(FRUIT, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f, "fig"),
    FINGER_LIME(FRUIT, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f, "finger_lime", "lime"),
    GRAPEFRUIT(FRUIT, 4, 0.4f, 7f, 0f, 0f, 0.6f, 0f, 0f, 2.1f, "grapefruit"),
    GUAVA(FRUIT, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f, "guava"),
    ICE_CREAM_BEAN(FRUIT, 4, 2f, 0f, 0f, 0.5f, 0f, 1f, 0f, 2.5f, "ice_cream_bean"),
    JACKFRUIT(FRUIT, 4, 2f, 0f, 0f, 0.5f, 0f, 1f, 0f, 2.5f, "jackfruit"),
    JUJUBE(FRUIT, 4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f, "jujube"),
    JUNIPER(FRUIT, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 1.8f, "juniper"),
    KAKI(FRUIT, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f, "kaki"),
    KEY_LIME(FRUIT, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f, "key_lime", "lime"),
    KLUWAK(FRUIT, 4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "kluwak"),
    KUMQUAT(FRUIT, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f, "kumquat"),
    PERSIAN_LIME(FRUIT, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f, "persian_lime", "lime"),
    LONGAN(FRUIT, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f, "longan"),
    LOQUAT(FRUIT, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f, "loquat"),
    LYCHEE(FRUIT, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f, "lychee"),
    MAMEY_SAPOTE(FRUIT, 4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 2.5f, "mamey_sapote"),
    MANDERIN(FRUIT, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f, "manderin"),
    MANGO(FRUIT, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f, "mango"),
    MANGOSTEEN(FRUIT, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f, "mangosteen"),
    NECTARINE(FRUIT, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f, "nectarine"),
    OHIA_AI(FRUIT, 4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f, "ohia_ai"),
    OSAGE_ORANGE(FRUIT, 4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 1.6f, "osage_orange"),
    PAPAYA(FRUIT, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f, "papaya"),
    PASSION_FRUIT(FRUIT, 4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "passion_fruit"),
    PEAR(FRUIT, 4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f, "pear"),
    PERSIMMON(FRUIT, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f, "persimmon"),
    PERUVIAN_PEPPER(VEGETABLE, 4, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f, "peruvian_pepper"),
    PLANTAIN(FRUIT, 4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f, "plantain"),
    POMEGRANATE(FRUIT, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "pomegranate"),
    POMELO(FRUIT, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f, "pomelo"),
    QUINCE(FRUIT, 4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f, "quince"),
    RAINIER_CHERRY(FRUIT, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f, "rainier_cherry"),
    RED_BANANA(FRUIT, 4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f, "red_banana"),
    RED_CURRANT(FRUIT, 4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "red_currant"),
    SAND_PEAR(FRUIT, 4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f, "sand_pear"),
    SAPODILLA(FRUIT, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f, "sapodilla"),
    SATSUMA(FRUIT, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f, "satsuma"),
    SOUR_CHERRY(FRUIT, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f, "sour_cherry"),
    SOURSOP(FRUIT, 4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f, "soursop"),
    STARFRUIT(FRUIT, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 4.9f, "starfruit"),
    TAMARILLO(VEGETABLE, 4, 0.5f, 5f, 0f, 1.5f, 0f, 0f, 0f, 3.5f, "tamarillo"),
    TANGERINE(FRUIT, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f, "tangerine"),
    TROPICAL_APRICOT(FRUIT, 4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f, "tropical_apricot"),
    WILD_CHERRY(FRUIT, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f, "wild_cherry"),
    
    // Deez Nuts
    ACORN(OTHER, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "acorn"),
    ALMOND(OTHER, 4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "almond"),
    BEECHNUT(OTHER, 4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 2.5f, "beechnut"),
    BLACK_WALNUT(OTHER, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f, "black_walnut"),
    BRAZIL_NUT(OTHER, 4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "brazil_nut"),
    BREADNUT(OTHER, 4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f, "breadnut"),
    BUNYA_NUT(OTHER, 4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 4.9f, "bunya_nut"),
    BUTTERNUT(OTHER, 4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f, "butternut"),
    CANDLENUT(OTHER, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "candlenut"),
    CASHEW(OTHER, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "cashew"),
    GINKGO_NUT(OTHER, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "ginkgo_nut"),
    HAZELNUT(OTHER, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "hazelnut"),
    HEARTNUT(OTHER, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "heartnut"),
    HICKORY_NUT(OTHER, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "hickory_nut"),
    KOLA_NUT(OTHER, 4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f, "kola_nut"),
    KUKUI_NUT(OTHER, 4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f, "kukui_nut"),
    MACADAMIA(OTHER, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f, "macadamia"),
    MONGONGO(OTHER, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f, "mongongo"),
    MONKEY_PUZZLE_NUT(OTHER, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f, "monkey_puzzle_nut"),
    NUTMEG(OTHER, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f, "nutmeg"),
    PARADISE_NUT(OTHER, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f, "paradise_nut"),
    PECAN(OTHER, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f, "pecan"),
    PINE_NUT(OTHER, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f, "pine_nut"),
    PISTACHIO(OTHER, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f, "pistachio"),
    WALNUT(OTHER, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f, "walnut"),
    
    // Other
    ALLSPICE(OTHER, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f, "allspice"),
    CLOVE(OTHER, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f, "clove"),
    CURRY_LEAF(OTHER, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f, "curry_leaf"),
    STAR_ANISE(OTHER, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f, "star_anise"),

    // Seafood
    EEL(MEAT, 4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f, 1f, 200f),
    COOKED_EEL(COOKED_MEAT, 4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f),
    CRAB(MEAT, 4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f, 1f, 200f),
    COOKED_CRAB(COOKED_MEAT, 4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f),
    LIVE_CLAM(MEAT, 4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f, 1f, 200f),
    COOKED_CLAM(COOKED_MEAT, 4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f),
    LIVE_SCALLOP(MEAT, 4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f, 1f, 200f),
    COOKED_SCALLOP(COOKED_MEAT, 4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f),
    LIVE_STARFISH(MEAT, 4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f, 1f, 200f),
    COOKED_STARFISH(COOKED_MEAT, 4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f),

    // Normal foods
    DRIED_COFFEE_CHERRIES(FRUIT, 4, 0.6f, 5f, 0f, 1f, 0f, 0.5f, 0f, 1.25f, "cropDriedCoffee"),
    ROASTED_COFFEE_BEANS(FRUIT, 4, 0.6f, 5f, 0f, 1f, 0f, 0.5f, 0f, 1.25f, "cropRoastedCoffee"),
    AMARANTH(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "amaranth"),
    AMARANTH_GRAIN(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.4f, "grain_amaranth", "grain"),
    AMARANTH_FLOUR(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "flour_amaranth", "flour"),
    AMARANTH_DOUGH(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, 1f, 200f, "dough_amaranth", "dough"),
    AMARANTH_BREAD(BREAD, 4, 0f, 0f, 1.5f, 0f, 0f, 0f, 0f, 0.8f, 1f, 480f, "bread_amaranth", "bread"),
    BUCKWHEAT(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "buckwheat"),
    BUCKWHEAT_GRAIN(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.4f, "grain_buckwheat", "grain"),
    BUCKWHEAT_FLOUR(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "flour_buckwheat", "flour"),
    BUCKWHEAT_DOUGH(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, 1f, 200f, "dough_buckwheat", "dough"),
    BUCKWHEAT_BREAD(BREAD, 4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 0.8f, 1f, 480f, "bread_buckwheat", "bread"),
    FONIO(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "fonio"),
    FONIO_GRAIN(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.4f, "grain_fonio", "grain"),
    FONIO_FLOUR(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "flour_fonio", "flour"),
    FONIO_DOUGH(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, 1f, 200f, "dough_fonio", "dough"),
    FONIO_BREAD(BREAD, 4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 0.8f, 1f, 480f, "bread_fonio", "bread"),
    MILLET(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "millet"),
    MILLET_GRAIN(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.4f, "grain_millet", "grain"),
    MILLET_FLOUR(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "flour_millet", "flour"),
    MILLET_DOUGH(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, 1f, 200f, "dough_millet", "dough"),
    MILLET_BREAD(BREAD, 4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 0.8f, 1f, 480f, "bread_millet", "bread"),
    QUINOA(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "quinoa"),
    QUINOA_GRAIN(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.4f, "grain_quinoa", "grain"),
    QUINOA_FLOUR(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "flour_quinoa", "flour"),
    QUINOA_DOUGH(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, 1f, 200f, "dough_quinoa", "dough"),
    QUINOA_BREAD(BREAD, 4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 0.8f, 1f, 480f, "bread_quinoa", "bread"),
    SPELT(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "spelt"),
    SPELT_GRAIN(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.4f, "grain_spelt", "grain"),
    SPELT_FLOUR(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "flour_spelt", "flour"),
    SPELT_DOUGH(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, 1f, 200f, "dough_spelt", "dough"),
    SPELT_BREAD(BREAD, 4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 0.8f, 1f, 480f, "bread_spelt", "bread"),
    WILD_RICE(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "wild_rice"),
    WILD_RICE_GRAIN(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.4f, "grain_wild_rice", "grain"),
    WILD_RICE_FLOUR(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "flour_wild_rice", "flour"),
    WILD_RICE_DOUGH(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, 1f, 200f, "dough_wild_rice", "dough"),
    WILD_RICE_BREAD(BREAD, 4, 0f, 0f, 1.7f, 0f, 0f, 0f, 0f, 0.8f, 1f, 480f, "bread_wild_rice", "bread"),
    LINSEED(GRAIN, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f, "crop_flax_seed"),
    RAPE_SEED(GRAIN, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f, "crop_rape_seed"),
    SUNFLOWER_SEED(GRAIN, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f, "crop_sunflower_seed"),
    OPIUM_POPPY_SEED(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "crop_opium_poppy"),
    HASH_MUFFIN(BREAD, 4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 0.8f, 1f, 480f, "food_hash_muffin"),
    PEYOTE(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f, "peyote"),
    RUTABAGA(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f, "crop_rutabaga"),
    TURNIP(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f, "crop_turnip"),
    MUSTARD(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f, "crop_mustard"),
    BLACK_EYED_PEAS(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 3.5f, "crop_black_eyed_peas"),
    GREEN_CAYENNE_PEPPER(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f, "crop_cayenne_pepper"),
    RED_CAYENNE_PEPPER(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f, "crop_cayenne_pepper"),
    GINGER(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f, "crop_ginger"),
    GINSENG(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f, "crop_ginseng"),
    CELERY(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f, "crop_celery"),
    LETTUCE(VEGETABLE, 4, 0.4f, 5f, 0f, 0f, 0f, 1f, 0f, 2.5f, "crop_lettuce"),
    PEANUT(VEGETABLE, 4, 0.6f, 3f, 0f, 1.5f, 2f, 0.5f, 0.5f, 2f, "crop_peanut"),
    SWEET_POTATO(VEGETABLE, 4, 0.8f, 3f, 2f, 0f, 1f, 1f, 0f, 3f, "crop_sweet_potato"),
    SUGAR_BEET(VEGETABLE, 4, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f, "crop_sugar_beet"),
    LINSEED_PASTE(GRAIN, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f, "crop_linseed_paste"),
    RAPE_SEED_PASTE(GRAIN, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f, "crop_rape_seed_paste"),
    SUNFLOWER_SEED_PASTE(GRAIN, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f, "crop_sunflower_seedPaste"),
    OPIUM_POPPY_SEED_PASTE(GRAIN, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f, "crop_opium_poppy_seed_paste"),
    MASHED_SUGAR_BEET(VEGETABLE, 4, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f, "mashed_sugar_beet"),
    MASHED_SUGAR_CANE(VEGETABLE, 4, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f, "mashed_sugar_cane"),
    SOYBEAN_PASTE(VEGETABLE, 4, 0.8f, 3f, 2f, 0f, 1f, 1f, 0f, 3f, "paste_soybean"),
    COW_CHEESE(DAIRY, 4, 2f, 0f, 0f, 0f, 0f, 0f, 3f, 0.3f, "cheese_cow","cow_cheese", "cheese"),
    GOAT_CHEESE(DAIRY, 4, 2f, 0f, 0f, 0f, 0f, 0f, 3f, 0.3f, "cheese_goat", "goat_cheese", "cheese"),
    SHEEP_CHEESE(DAIRY, 4, 2f, 0f, 0f, 0f, 0f, 0f, 3f, 0.3f, "cheese_sheep", "sheep_cheese", "cheese"),
    AMARANTH_BREAD_SANDWICH(MEAL, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f, "sandwich"),
    BUCKWHEAT_BREAD_SANDWICH(MEAL, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f, "sandwich"),
    FONIO_BREAD_SANDWICH(MEAL, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f, "sandwich"),
    MILLET_BREAD_SANDWICH(MEAL, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f, "sandwich"),
    QUINOA_BREAD_SANDWICH(MEAL, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f, "sandwich"),
    SPELT_BREAD_SANDWICH(MEAL, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f, "sandwich"),
    WILD_RICE_BREAD_SANDWICH(MEAL, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f, "sandwich");
	
    private final Category category;
    private final FoodDataTFCF foodDataTFCF;

    private final boolean heatable;
    private final float heatCapacity;
    private final float cookingTemp;

    private final String[] oreDictNames;

    FoodTFCF(@Nonnull Category category, int hunger, float saturation, float water, float grain, float veg, float fruit, float meat, float dairy, float decayModifier, String... oreNames)
    {
        this(category, hunger, saturation, water, grain, veg, fruit, meat, dairy, decayModifier, 0, -1, oreNames);
    }

    FoodTFCF(@Nonnull Category category, int hunger, float saturation, float water, float grain, float veg, float fruit, float meat, float dairy, float decayModifier, float heatCapacity, float cookingTemp, String... oreNames)
    {
        this.category = category;
        this.foodDataTFCF = new FoodDataTFCF(hunger, water, saturation, grain, fruit, veg, meat, dairy, decayModifier);

        this.heatable = cookingTemp >= 0;
        this.heatCapacity = heatCapacity;
        this.cookingTemp = cookingTemp;

        this.oreDictNames = oreNames == null || oreNames.length == 0 ? null : oreNames;
    }

    @Nonnull
    public Category getCategory()
    {
        return category;
    }

    @Nonnull
    public FoodDataTFCF getData()
    {
        return foodDataTFCF;
    }

    public boolean isHeatable()
    {
        return heatable;
    }

    public float getHeatCapacity()
    {
        return heatCapacity;
    }

    public float getCookingTemp()
    {
        return cookingTemp;
    }

    @Nullable
    public String[] getOreDictNames()
    {
        return oreDictNames;
    }

    public enum Category
    {
        FRUIT,
        GRAIN,
        BREAD,
        VEGETABLE,
        MEAT,
        COOKED_MEAT,
        DAIRY,
        MEAL,
        OTHER; // Provided for addons / other mods

        public static boolean doesStackMatchCategories(ItemStack stack, Category... categories)
        {
            for (Category cat : categories)
            {
                if (OreDictionaryHelper.doesStackMatchOre(stack, OreDictionaryHelper.toString("category_" + cat.name())))
                {
                    return true;
                }
            }
            return false;
        }
    }
}