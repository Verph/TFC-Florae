package tfcflorae.util.agriculture;

import net.dries007.tfc.api.capability.food.FoodData;

public class FoodDataTFCF
{
    /*
	// Fruits
    public static final FoodData ABIU = new FoodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f);
    public static final FoodData AMLA = new FoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f);
    public static final FoodData APRICOT = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f);
    public static final FoodData AVOCADO = new FoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 1.8f);
    public static final FoodData BAEL = new FoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f);
    public static final FoodData BAY_LAUREL = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f);
    public static final FoodData BER = new FoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f);
    public static final FoodData BERGAMOT = new FoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f);
    public static final FoodData BLACK_CHERRY = new FoodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 1.6f);
    public static final FoodData BLACK_PEPPER = new FoodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 1.6f);
    public static final FoodData BLACKCURRANT = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f);
    public static final FoodData BLACKTHORN = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f);
    public static final FoodData BUDDHA_HAND = new FoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f);
    public static final FoodData CACAO = new FoodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f);
    public static final FoodData CHERRY_PLUM = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f);
    public static final FoodData CITRON = new FoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f);
    public static final FoodData CRABAPPLE = new FoodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f);
    public static final FoodData DAMSON_PLUM = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f);
    public static final FoodData DATE = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f);
    public static final FoodData ELDER = new FoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f);
    public static final FoodData FIG = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f);
    public static final FoodData FINGER_LIME = new FoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f);
    public static final FoodData GRAPEFRUIT = new FoodData(4, 0.4f, 7f, 0f, 0f, 0.6f, 0f, 0f, 2.1f);
    public static final FoodData GUAVA = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f);
    public static final FoodData ICE_CREAM_BEAN = new FoodData(4, 2f, 0f, 0f, 0.5f, 0f, 1f, 0f, 2.5f);
    public static final FoodData JACKFRUIT = new FoodData(4, 2f, 0f, 0f, 0.5f, 0f, 1f, 0f, 2.5f);
    public static final FoodData JUJUBE = new FoodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f);
    public static final FoodData KAKI = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f);
    public static final FoodData KEY_LIME = new FoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f);
    public static final FoodData KLUWAK = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f);
    public static final FoodData KUMQUAT = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f);
    public static final FoodData PERSIAN_LIME = new FoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f);
    public static final FoodData LONGAN = new FoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f);
    public static final FoodData LOQUAT = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f);
    public static final FoodData LYCHEE = new FoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f);
    public static final FoodData MAMEY_SAPOTE = new FoodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 2.5f);
    public static final FoodData MANDERIN = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f);
    public static final FoodData MANGO = new FoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f);
    public static final FoodData MANGOSTEEN = new FoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f);
    public static final FoodData NECTARINE = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f);
    public static final FoodData OHIA_AI = new FoodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f);
    public static final FoodData PAPAYA = new FoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f);
    public static final FoodData PASSION_FRUIT = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f);
    public static final FoodData PEAR = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f);
    public static final FoodData PERSIMMON = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f);
    public static final FoodData PERUVIAN_PEPPER = new FoodData(4, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f);
    public static final FoodData PLANTAIN = new FoodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f);
    public static final FoodData POMEGRANATE = new FoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f);
    public static final FoodData POMELO = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f);
    public static final FoodData QUINCE = new FoodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f);
    public static final FoodData RAINIER_CHERRY = new FoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f);
    public static final FoodData RED_BANANA = new FoodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f);
    public static final FoodData RED_CURRANT = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f);
    public static final FoodData SAND_PEAR = new FoodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f);
    public static final FoodData SAPODILLA = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f);
    public static final FoodData SATSUMA = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f);
    public static final FoodData SOUR_CHERRY = new FoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f);
    public static final FoodData SOURSOP = new FoodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f);
    public static final FoodData STARFRUIT = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 4.9f);
    public static final FoodData TAMARILLO = new FoodData(4, 0.5f, 5f, 0f, 1.5f, 0f, 0f, 0f, 3.5f);
    public static final FoodData TANGERINE = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f);
    public static final FoodData TROPICAL_APRICOT = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f);
    public static final FoodData VANILLA = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f);

    // Nuts
    public static final FoodData ALMOND = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f);
    public static final FoodData BRAZIL_NUT = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f);
    public static final FoodData BREADNUT = new FoodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f);
    public static final FoodData BUNYA_NUT = new FoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 4.9f);
    public static final FoodData CANDLENUT = new FoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f);
    public static final FoodData CASHEW = new FoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f);
    public static final FoodData HEARTNUT = new FoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f);
    public static final FoodData KOLA_NUT = new FoodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f);
    public static final FoodData KUKUI_NUT = new FoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f);
    public static final FoodData MACADAMIA = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    public static final FoodData MONGONGO = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    public static final FoodData MONKEY_PUZZLE_NUT = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    public static final FoodData NUTMEG = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    public static final FoodData PARADISE_NUT = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    public static final FoodData PISTACHIO = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    */

    // Normal Trees Fruits
    public static final FoodData BAOBAB_FRUIT = new FoodData(4, 1f, 0.2f, 0f, 1f, 0f, 0f, 0f, 1.6f);
    public static final FoodData BARREL_CACTUS_FRUIT = new FoodData(4, 1f, 0.5f, 0f, 1.2f, 0f, 0f, 0f, 1.7f);
    public static final FoodData HAWTHORN = new FoodData(4, 5f, 0.2f, 0f, 1f, 0f, 0f, 0f, 1.8f);
    public static final FoodData JUNIPER = new FoodData(4, 5f, 0.2f, 0f, 1f, 0f, 0f, 0f, 1.8f);
    public static final FoodData OSAGE_ORANGE = new FoodData(4, 10f, 0.5f, 0f, 0.5f, 0f, 0f, 0f, 2.2f);
    public static final FoodData PINK_IVORY_DRUPE = new FoodData(4, 5f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 2.8f);
    public static final FoodData RIBERRY = new FoodData(4, 5f, 0.2f, 0f, 1f, 0f, 0f, 0f, 4.9f);
    public static final FoodData ROWAN_BERRY = new FoodData(4, 5f, 0.2f, 0f, 1f, 0f, 0f, 0f, 4.9f);
    public static final FoodData SKY_FRUIT = new FoodData(4, 5f, 0.2f, 0f, 0.75f, 0f, 0f, 0f, 2f);
    public static final FoodData WILD_CHERRY = new FoodData(4, 5f, 0.2f, 0f, 1f, 0f, 0f, 0f, 4f);
    public static final FoodData YEW_BERRY = new FoodData(4, 5f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 4.9f);
    public static final FoodData ROASTED_YEW_BERRY = new FoodData(4, 0f, 2f, 0f, 1.1f, 0f, 0f, 0f, 2.6f);
    public static final FoodData MULBERRY = new FoodData(4, 4.5f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 4.9f);
    public static final FoodData GLOWBERRY = new FoodData(2, 7.5f, 0.25f, 0f, 1f, 0f, 0f, 0f, 4.9f);

    // Normal Trees Nuts
    public static final FoodData ACORN = new FoodData(4, 0f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 0.25f);
    public static final FoodData BEECHNUT = new FoodData(4, 0f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 0.25f);
    public static final FoodData BLACK_WALNUT = new FoodData(4, 0f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 0.25f);
    public static final FoodData BUTTERNUT = new FoodData(4, 0f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 0.25f);
    public static final FoodData CHESTNUT = new FoodData(4, 0f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 0.25f);
    public static final FoodData GINKGO_NUT = new FoodData(4, 0f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 0.25f);
    public static final FoodData HAZELNUT = new FoodData(4, 0f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 0.25f);
    public static final FoodData HICKORY_NUT = new FoodData(4, 0f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 0.25f);
    public static final FoodData PECAN = new FoodData(4, 0f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 0.25f);
    public static final FoodData PINE_NUT = new FoodData(4, 0f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 0.25f);
    public static final FoodData WALNUT = new FoodData(4, 0f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 0.25f);

    // Dried Foods
    public static final FoodData DRIED_FRUIT_SATURATION = new FoodData(4, 0f, 1f, 0f, 1f, 0f, 0f, 0f, 0.8f);
    public static final FoodData DRIED_FRUIT_DECAY = new FoodData(4, 0f, 1f, 0f, 1f, 0f, 0f, 0f, 0.8f);
    public static final FoodData DRIED_FRUIT_CATEGORY = new FoodData(4, 0f, 1f, 0f, 1f, 0f, 0f, 0f, 0.8f);

    // Cracked & Roasted Nuts
    public static final FoodData UNCRACKED_NUT = new FoodData(4, 0f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 0.25f);
    public static final FoodData NUT = new FoodData(4, 0f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 0.25f);
    public static final FoodData ROASTED_NUT = new FoodData(4, 0f, 0.5f, 0f, 0.75f, 0f, 0f, 0f, 0.25f);

    // Raw Epiphytes
    public static final FoodData RAW_ARTISTS_CONK = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_SULPHUR_SHELF = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_TURKEY_TAIL = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);

    // raw Mushrooms
    public static final FoodData RAW_PORCINI = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_AMANITA = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_BLACK_POWDERPUFF = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_CHANTERELLE = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_DEATH_CAP = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_GIANT_CLUB = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_PARASOL_MUSHROOM = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_STINKHORN = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_WEEPING_MILK_CAP = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_WOOD_BLEWIT = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_WOOLLY_GOMPHUS = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_BLUESHROOM = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_GLOWSHROOM = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_MAGMA_SHROOM = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_POISON_SHROOM = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);
    public static final FoodData RAW_SULPHUR_SHROOM = new FoodData(4, 0f, 0.5f, 0f, 0.1f, 0.1f, 0.5f, 0f, 2f);

    // Roasted Epiphytes
    public static final FoodData ROASTED_ARTISTS_CONK = new FoodData(4, 0f, 2f, 0f, 0.3f, 0.3f, 0.7f, 0f, 3.5f);
    public static final FoodData ROASTED_SULPHUR_SHELF = new FoodData(4, 0f, 2f, 0f, 0.3f, 0.3f, 0.7f, 0f, 3.5f);
    public static final FoodData ROASTED_TURKEY_TAIL = new FoodData(4, 0f, 2f, 0f, 0.3f, 0.3f, 0.7f, 0f, 3.5f);

    // Roasted Mushrooms
    public static final FoodData ROASTED_PORCINI = new FoodData(4, 0f, 2f, 0f, 0.3f, 0.3f, 0.7f, 0f, 3.5f);
    public static final FoodData ROASTED_AMANITA = new FoodData(4, 0f, 2f, 0f, 0.3f, 0.3f, 0.7f, 0f, 3.5f);
    public static final FoodData ROASTED_BLACK_POWDERPUFF = new FoodData(4, 0f, 2f, 0f, 0.3f, 0.3f, 0.7f, 0f, 3.5f);
    public static final FoodData ROASTED_CHANTERELLE = new FoodData(4, 0f, 2f, 0f, 0.3f, 0.3f, 0.7f, 0f, 3.5f);
    public static final FoodData ROASTED_DEATH_CAP = new FoodData(4, 0f, 2f, 0f, 0.3f, 0.3f, 0.7f, 0f, 3.5f);
    public static final FoodData ROASTED_GIANT_CLUB = new FoodData(4, 0f, 2f, 0f, 0.3f, 0.3f, 0.7f, 0f, 3.5f);
    public static final FoodData ROASTED_PARASOL_MUSHROOM = new FoodData(4, 0f, 2f, 0f, 0.3f, 0.3f, 0.7f, 0f, 3.5f);
    public static final FoodData ROASTED_STINKHORN = new FoodData(4, 0f, 2f, 0f, 0.3f, 0.3f, 0.7f, 0f, 3.5f);
    public static final FoodData ROASTED_WEEPING_MILK_CAP = new FoodData(4, 0f, 2f, 0f, 0.3f, 0.3f, 0.7f, 0f, 3.5f);
    public static final FoodData ROASTED_WOOD_BLEWIT = new FoodData(4, 0f, 2f, 0f, 0.3f, 0.3f, 0.7f, 0f, 3.5f);
    public static final FoodData ROASTED_WOOLLY_GOMPHUS = new FoodData(4, 0f, 2f, 0f, 0.3f, 0.3f, 0.7f, 0f, 3.5f);

    public static final FoodData COFFEA_CHERRIES = new FoodData(4, 0.5f, 0.5f, 0f, 1f, 0f, 0f, 0f, 1.9f);
    public static final FoodData DRIED_COFFEA_CHERRIES = new FoodData(4, 0f, 0.5f, 0f, 1f, 0f, 0f, 0f, 0.5f);
    public static final FoodData ALLSPICE = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData CLOVE = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData CURRY_LEAF = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.8f);
    public static final FoodData STAR_ANISE = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData LIQUORICE_ROOT = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.2f);
    public static final FoodData CASSIA_CINNAMON_BARK = new FoodData(4, 0f, 0.5f, 0f, 1f, 0f, 0f, 0f, 0.3f);
    public static final FoodData GROUND_CASSIA_CINNAMON = new FoodData(4, 0f, 0.5f, 0f, 1f, 0f, 0f, 0f, 0.2f);
    public static final FoodData CEYLON_CINNAMON_BARK = new FoodData(4, 0f, 0.5f, 0f, 1f, 0f, 0f, 0f, 0.3f);
    public static final FoodData GROUND_CEYLON_CINNAMON = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.2f);
    //public static final FoodData GROUND_BLACK_PEPPER = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.2f);
    public static final FoodData BLACK_TEA = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData DRIED_BLACK_TEA = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData GREEN_TEA = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData DRIED_GREEN_TEA = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData WHITE_TEA = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData DRIED_WHITE_TEA = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData CANNABIS_BUD = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.8f);
    public static final FoodData DRIED_CANNABIS_BUD = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData CANNABIS_LEAF = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.8f);
    public static final FoodData DRIED_CANNABIS_LEAF = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData COCA_LEAF = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.8f);
    public static final FoodData DRIED_COCA_LEAF = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData OPIUM_POPPY_BULB = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData DRIED_OPIUM_POPPY_BULB = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData PEYOTE = new FoodData(4, 1f, 3f, 0f, 0f, 1f, 0f, 0f, 1f);
    public static final FoodData DRIED_PEYOTE = new FoodData(4, 0f, 3f, 0f, 0f, 2f, 0f, 0f, 0.5f);
    public static final FoodData TOBACCO_LEAF = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.8f);
    public static final FoodData DRIED_TOBACCO_LEAF = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);

    public static final FoodData RAW_EEL = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f);
    public static final FoodData COOKED_EEL = new FoodData(4, 0f, 1f, 0f, 0f, 0f, 1.5f, 0f, 2.25f);
    public static final FoodData RAW_CRAB = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f);
    public static final FoodData COOKED_CRAB = new FoodData(4, 0f, 1f, 0f, 0f, 0f, 1.5f, 0f, 2.25f);
    public static final FoodData RAW_CLAM = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f);
    public static final FoodData COOKED_CLAM = new FoodData(4, 0f, 1f, 0f, 0f, 0f, 1.5f, 0f, 2.25f);
    public static final FoodData RAW_SCALLOP = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f);
    public static final FoodData COOKED_SCALLOP = new FoodData(4, 0f, 1f, 0f, 0f, 0f, 1.5f, 0f, 2.25f);
    public static final FoodData RAW_STARFISH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f);
    public static final FoodData COOKED_STARFISH = new FoodData(4, 0f, 1f, 0f, 0f, 0f, 1.5f, 0f, 2.25f);
    public static final FoodData RAW_SNAIL = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f);
    public static final FoodData COOKED_SNAIL = new FoodData(4, 0f, 1f, 0f, 0f, 0f, 1.5f, 0f, 2.25f);
    public static final FoodData RAW_WORM = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f);
    public static final FoodData COOKED_WORM = new FoodData(4, 0f, 1f, 0f, 0f, 0f, 1.5f, 0f, 2.25f);

    public static final FoodData ROASTED_COFFEE_BEANS = new FoodData(4, 0f, 0.5f, 0f, 1f, 0f, 0f, 0f, 0.5f);
    public static final FoodData COFFEE_POWDER = new FoodData(4, 0f, 0.5f, 0f, 1f, 0f, 0f, 0f, 0.5f);
    public static final FoodData AMARANTH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    public static final FoodData AMARANTH_GRAIN = new FoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f);
    public static final FoodData AMARANTH_FLOUR = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f);
    public static final FoodData AMARANTH_DOUGH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f);
    public static final FoodData AMARANTH_BREAD = new FoodData(4, 0f, 1f, 1.5f, 0f, 0f, 0f, 0f, 1f);
    public static final FoodData BUCKWHEAT = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    public static final FoodData BUCKWHEAT_GRAIN = new FoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.4f);
    public static final FoodData BUCKWHEAT_FLOUR = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f);
    public static final FoodData BUCKWHEAT_DOUGH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f);
    public static final FoodData BUCKWHEAT_BREAD = new FoodData(4, 0f, 1f, 1.5f, 0f, 0f, 0f, 0f, 1f);
    public static final FoodData FONIO = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    public static final FoodData FONIO_GRAIN = new FoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.4f);
    public static final FoodData FONIO_FLOUR = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f,0.5f);
    public static final FoodData FONIO_DOUGH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f);
    public static final FoodData FONIO_BREAD = new FoodData(4, 0f, 1f, 1.5f, 0f, 0f, 0f, 0f, 1f);
    public static final FoodData MILLET = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    public static final FoodData MILLET_GRAIN = new FoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.4f);
    public static final FoodData MILLET_FLOUR = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f);
    public static final FoodData MILLET_DOUGH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f);
    public static final FoodData MILLET_BREAD = new FoodData(4, 0f, 1f, 1.5f, 0f, 0f, 0f, 0f, 1f);
    public static final FoodData QUINOA = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    public static final FoodData QUINOA_GRAIN = new FoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.4f);
    public static final FoodData QUINOA_FLOUR = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f);
    public static final FoodData QUINOA_DOUGH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f);
    public static final FoodData QUINOA_BREAD = new FoodData(4, 0f, 1f, 1.5f, 0f, 0f, 0f, 0f, 1f);
    public static final FoodData SPELT = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    public static final FoodData SPELT_GRAIN = new FoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.4f);
    public static final FoodData SPELT_FLOUR = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f);
    public static final FoodData SPELT_DOUGH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f);
    public static final FoodData SPELT_BREAD = new FoodData(4, 0f, 1f, 1.5f, 0f, 0f, 0f, 0f, 1f);
    public static final FoodData WILD_BARLEY = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    public static final FoodData WILD_RICE = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    public static final FoodData WILD_WHEAT = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f);
    public static final FoodData AMARANTH_BREAD_SANDWICH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f);
    public static final FoodData BUCKWHEAT_BREAD_SANDWICH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f);
    public static final FoodData FONIO_BREAD_SANDWICH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f);
    public static final FoodData MILLET_BREAD_SANDWICH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f);
    public static final FoodData QUINOA_BREAD_SANDWICH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f);
    public static final FoodData SPELT_BREAD_SANDWICH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f);
    public static final FoodData DOUGH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f);
    public static final FoodData FLATBREAD = new FoodData(4, 1.0f, 0f, 1.0f, 0f, 0f, 0f, 0f, 1.0f);
    public static final FoodData SLICE = new FoodData(4, 0f, 0.5f, 0.5f, 0f, 0f, 0f, 0f, 1.5f);
    public static final FoodData SANDWICH = new FoodData(4, 0f, 3.0f, 0f, 0f, 0f, 0f, 0f, 5f);
    public static final FoodData HASH_MUFFIN_DOUGH = new FoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f);
    public static final FoodData HASH_MUFFIN = new FoodData(4, 0f, 1f, 1.5f, 0f, 0f, 0f, 0f, 1f);
    public static final FoodData LINSEED = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.8f);
    public static final FoodData RAPE_SEED = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.8f);
    public static final FoodData SUNFLOWER_SEED = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.8f);
    public static final FoodData OPIUM_POPPY_SEED = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.8f);
    public static final FoodData RUTABAGA = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData TURNIP = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.5f);
    public static final FoodData BLACK_EYED_PEAS = new FoodData(4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 3.5f);
    public static final FoodData GREEN_CAYENNE_PEPPER = new FoodData(4, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 2.5f);
    public static final FoodData RED_CAYENNE_PEPPER = new FoodData(4, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 2.5f);
    public static final FoodData GINGER = new FoodData(4, 0f, 2f, 0f, 0f, 1f, 0f, 0f, 0.7f);
    public static final FoodData GINSENG = new FoodData(4, 0f, 2f, 0f, 0f, 1f, 0f, 0f, 0.7f);
    public static final FoodData SUGAR_BEET = new FoodData(4, 0f, 2f, 0f, 0f, 1f, 0f, 0f, 0.7f);
    public static final FoodData PURPLE_GRAPE = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f);
    public static final FoodData GREEN_GRAPE = new FoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f);
    public static final FoodData LINSEED_PASTE = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.8f);
    public static final FoodData RAPE_SEED_PASTE = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.8f);
    public static final FoodData SUNFLOWER_SEED_PASTE = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.8f);
    public static final FoodData OPIUM_POPPY_SEED_PASTE = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 0.8f);
    public static final FoodData MASHED_SUGAR_BEET = new FoodData(4, 0f, 2f, 0f, 0f, 1f, 0f, 0f, 1.5f);
    public static final FoodData MASHED_SUGAR_CANE = new FoodData(4, 0f, 0.5f, 0f, 0f, 1f, 0f, 0f, 2f);
    public static final FoodData SOYBEAN_PASTE = new FoodData(4, 0f, 1f, 0f, 0f, 0.5f, 1f, 0f, 0.8f);
}