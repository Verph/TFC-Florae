package tfcflorae.util.agriculture;

import net.minecraft.item.Item;

import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.util.agriculture.Food;

import tfcflorae.objects.items.*;
import tfcflorae.objects.items.ItemsTFCF;

import static tfcflorae.util.agriculture.FoodDataTFCF.*;

/**
 * This is an easy way to wrap all the TFC fruits with our data
 */
public enum FruitsTFCF
{
    BANANA(ItemFoodTFC.get(Food.BANANA), true, DRIED_FRUIT_DECAY),
    BLACKBERRY(ItemFoodTFC.get(Food.BLACKBERRY), true, DRIED_FRUIT_CATEGORY),
    BLUEBERRY(ItemFoodTFC.get(Food.BLUEBERRY), true, DRIED_FRUIT_CATEGORY),
    BUNCH_BERRY(ItemFoodTFC.get(Food.BUNCH_BERRY), true, DRIED_FRUIT_SATURATION),
    CHERRY(ItemFoodTFC.get(Food.CHERRY), true, DRIED_FRUIT_DECAY),
    CLOUD_BERRY(ItemFoodTFC.get(Food.CLOUD_BERRY), true, DRIED_FRUIT_DECAY),
    CRANBERRY(ItemFoodTFC.get(Food.CRANBERRY), true, DRIED_FRUIT_SATURATION),
    ELDERBERRY(ItemFoodTFC.get(Food.ELDERBERRY), true, DRIED_FRUIT_CATEGORY),
    GOOSEBERRY(ItemFoodTFC.get(Food.GOOSEBERRY), true, DRIED_FRUIT_SATURATION),
    GREEN_APPLE(ItemFoodTFC.get(Food.GREEN_APPLE), true, DRIED_FRUIT_DECAY),
    LEMON(ItemFoodTFC.get(Food.LEMON), true, DRIED_FRUIT_DECAY),
    OLIVE(ItemFoodTFC.get(Food.OLIVE), true, DRIED_FRUIT_DECAY),
    ORANGE(ItemFoodTFC.get(Food.ORANGE), true, DRIED_FRUIT_DECAY),
    PEACH(ItemFoodTFC.get(Food.PEACH), true, DRIED_FRUIT_SATURATION),
    PLUM(ItemFoodTFC.get(Food.PLUM), true, DRIED_FRUIT_SATURATION),
    RASPBERRY(ItemFoodTFC.get(Food.RASPBERRY), true, DRIED_FRUIT_SATURATION),
    RED_APPLE(ItemFoodTFC.get(Food.RED_APPLE), true, DRIED_FRUIT_DECAY),
    SNOW_BERRY(ItemFoodTFC.get(Food.SNOW_BERRY), true, DRIED_FRUIT_CATEGORY),
    STRAWBERRY(ItemFoodTFC.get(Food.STRAWBERRY), true, DRIED_FRUIT_SATURATION),
    WINTERGREEN_BERRY(ItemFoodTFC.get(Food.WINTERGREEN_BERRY), true, DRIED_FRUIT_CATEGORY),

    // TFC Florae Foods
    ABIU(ItemFoodTFCF.get(ItemsTFCF.ABIU), true, DRIED_FRUIT_DECAY),
    AMLA(ItemFoodTFCF.get(ItemsTFCF.AMLA), true, DRIED_FRUIT_DECAY),
    APRICOT(ItemFoodTFCF.get(ItemsTFCF.APRICOT), true, DRIED_FRUIT_DECAY),
    AVOCADO(ItemFoodTFCF.get(ItemsTFCF.AVOCADO), true, DRIED_FRUIT_DECAY),
    BAEL(ItemFoodTFCF.get(ItemsTFCF.BAEL), true, DRIED_FRUIT_DECAY),
    BAOBAB_FRUIT(ItemFoodTFCF.get(ItemsTFCF.BAOBAB_FRUIT), true, DRIED_FRUIT_DECAY),
    BAY_LAUREL(ItemFoodTFCF.get(ItemsTFCF.BAY_LAUREL), true, DRIED_FRUIT_DECAY),
    BER(ItemFoodTFCF.get(ItemsTFCF.BER), true, DRIED_FRUIT_DECAY),
    BERGAMOT(ItemFoodTFCF.get(ItemsTFCF.BERGAMOT), true, DRIED_FRUIT_DECAY),
    BLACK_CHERRY(ItemFoodTFCF.get(ItemsTFCF.BLACK_CHERRY), true, DRIED_FRUIT_DECAY),
    BLACK_PEPPER(ItemFoodTFCF.get(ItemsTFCF.BLACK_PEPPER), true, DRIED_FRUIT_DECAY),
    BLACKCURRANT(ItemFoodTFCF.get(ItemsTFCF.BLACKCURRANT), true, DRIED_FRUIT_DECAY),
    BLACKTHORN(ItemFoodTFCF.get(ItemsTFCF.BLACKTHORN), true, DRIED_FRUIT_SATURATION),
    BUDDHA_HAND(ItemFoodTFCF.get(ItemsTFCF.BUDDHA_HAND), true, DRIED_FRUIT_DECAY),
    CACAO(ItemFoodTFCF.get(ItemsTFCF.CACAO), true, DRIED_FRUIT_DECAY),
    CHERRY_PLUM(ItemFoodTFCF.get(ItemsTFCF.CHERRY_PLUM), true, DRIED_FRUIT_SATURATION),
    CITRON(ItemFoodTFCF.get(ItemsTFCF.CITRON), true, DRIED_FRUIT_DECAY),
    COCONUT(ItemFoodTFCF.get(ItemsTFCF.COCONUT), true, DRIED_FRUIT_DECAY),
    COFFEA_CHERRIES(ItemFoodTFCF.get(ItemsTFCF.COFFEA_CHERRIES), true, DRIED_FRUIT_DECAY),
    CRABAPPLE(ItemFoodTFCF.get(ItemsTFCF.CRABAPPLE), true, DRIED_FRUIT_DECAY),
    DAMSON_PLUM(ItemFoodTFCF.get(ItemsTFCF.DAMSON_PLUM), true, DRIED_FRUIT_SATURATION),
    DATE(ItemFoodTFCF.get(ItemsTFCF.DATE), true, DRIED_FRUIT_DECAY),
    ELDER(ItemFoodTFCF.get(ItemsTFCF.ELDER), true, DRIED_FRUIT_DECAY),
    FIG(ItemFoodTFCF.get(ItemsTFCF.FIG), true, DRIED_FRUIT_DECAY),
    FINGER_LIME(ItemFoodTFCF.get(ItemsTFCF.FINGER_LIME), true, DRIED_FRUIT_DECAY),
    GRAPEFRUIT(ItemFoodTFCF.get(ItemsTFCF.GRAPEFRUIT), true, DRIED_FRUIT_DECAY),
    GUAVA(ItemFoodTFCF.get(ItemsTFCF.GUAVA), true, DRIED_FRUIT_DECAY),
    HAWTHORN(ItemFoodTFCF.get(ItemsTFCF.HAWTHORN), true, DRIED_FRUIT_DECAY),
    ICE_CREAM_BEAN(ItemFoodTFCF.get(ItemsTFCF.ICE_CREAM_BEAN), true, DRIED_FRUIT_SATURATION),
    JACKFRUIT(ItemFoodTFCF.get(ItemsTFCF.JACKFRUIT), true, DRIED_FRUIT_SATURATION),
    JUJUBE(ItemFoodTFCF.get(ItemsTFCF.JUJUBE), true, DRIED_FRUIT_DECAY),
    JUNIPER(ItemFoodTFCF.get(ItemsTFCF.BER), true, DRIED_FRUIT_DECAY),
    KAKI(ItemFoodTFCF.get(ItemsTFCF.KAKI), true, DRIED_FRUIT_DECAY),
    KEY_LIME(ItemFoodTFCF.get(ItemsTFCF.KEY_LIME), true, DRIED_FRUIT_DECAY),
    KLUWAK(ItemFoodTFCF.get(ItemsTFCF.KLUWAK), true, DRIED_FRUIT_DECAY),
    KUMQUAT(ItemFoodTFCF.get(ItemsTFCF.KUMQUAT), true, DRIED_FRUIT_SATURATION),
    PERSIAN_LIME(ItemFoodTFCF.get(ItemsTFCF.PERSIAN_LIME), true, DRIED_FRUIT_DECAY),
    LONGAN(ItemFoodTFCF.get(ItemsTFCF.LONGAN), true, DRIED_FRUIT_DECAY),
    LOQUAT(ItemFoodTFCF.get(ItemsTFCF.LOQUAT), true, DRIED_FRUIT_SATURATION),
    LYCHEE(ItemFoodTFCF.get(ItemsTFCF.LYCHEE), true, DRIED_FRUIT_DECAY),
    MAMEY_SAPOTE(ItemFoodTFCF.get(ItemsTFCF.MAMEY_SAPOTE), true, DRIED_FRUIT_DECAY),
    MANDERIN(ItemFoodTFCF.get(ItemsTFCF.MANDERIN), true, DRIED_FRUIT_SATURATION),
    MANGO(ItemFoodTFCF.get(ItemsTFCF.MANGO), true, DRIED_FRUIT_DECAY),
    MANGOSTEEN(ItemFoodTFCF.get(ItemsTFCF.MANGOSTEEN), true, DRIED_FRUIT_DECAY),
    NECTARINE(ItemFoodTFCF.get(ItemsTFCF.NECTARINE), true, DRIED_FRUIT_DECAY),
    OHIA_AI(ItemFoodTFCF.get(ItemsTFCF.OHIA_AI), true, DRIED_FRUIT_DECAY),
    OSAGE_ORANGE(ItemFoodTFCF.get(ItemsTFCF.OSAGE_ORANGE), true, DRIED_FRUIT_DECAY),
    PAPAYA(ItemFoodTFCF.get(ItemsTFCF.PAPAYA), true, DRIED_FRUIT_DECAY),
    PASSION_FRUIT(ItemFoodTFCF.get(ItemsTFCF.PASSION_FRUIT), true, DRIED_FRUIT_DECAY),
    PEAR(ItemFoodTFCF.get(ItemsTFCF.PEAR), true, DRIED_FRUIT_SATURATION),
    PERSIMMON(ItemFoodTFCF.get(ItemsTFCF.PERSIMMON), true, DRIED_FRUIT_DECAY),
    PERUVIAN_PEPPER(ItemFoodTFCF.get(ItemsTFCF.PERUVIAN_PEPPER), true, DRIED_FRUIT_DECAY),
    PINK_IVORY_DRUPE(ItemFoodTFCF.get(ItemsTFCF.PINK_IVORY_DRUPE), true, DRIED_FRUIT_DECAY),
    PLANTAIN(ItemFoodTFCF.get(ItemsTFCF.PLANTAIN), true, DRIED_FRUIT_DECAY),
    POMEGRANATE(ItemFoodTFCF.get(ItemsTFCF.POMEGRANATE), true, DRIED_FRUIT_DECAY),
    POMELO(ItemFoodTFCF.get(ItemsTFCF.POMELO), true, DRIED_FRUIT_SATURATION),
    QUINCE(ItemFoodTFCF.get(ItemsTFCF.QUINCE), true, DRIED_FRUIT_DECAY),
    RAINIER_CHERRY(ItemFoodTFCF.get(ItemsTFCF.RAINIER_CHERRY), true, DRIED_FRUIT_DECAY),
    RED_BANANA(ItemFoodTFCF.get(ItemsTFCF.RED_BANANA), true, DRIED_FRUIT_DECAY),
    RED_CURRANT(ItemFoodTFCF.get(ItemsTFCF.RED_CURRANT), true, DRIED_FRUIT_DECAY),
    RIBERRY(ItemFoodTFCF.get(ItemsTFCF.RIBERRY), true, DRIED_FRUIT_DECAY),
    ROWAN_BERRY(ItemFoodTFCF.get(ItemsTFCF.ROWAN_BERRY), true, DRIED_FRUIT_DECAY),
    SAND_PEAR(ItemFoodTFCF.get(ItemsTFCF.SAND_PEAR), true, DRIED_FRUIT_DECAY),
    SAPODILLA(ItemFoodTFCF.get(ItemsTFCF.SAPODILLA), true, DRIED_FRUIT_DECAY),
    SATSUMA(ItemFoodTFCF.get(ItemsTFCF.SATSUMA), true, DRIED_FRUIT_SATURATION),
    SKY_FRUIT(ItemFoodTFCF.get(ItemsTFCF.SKY_FRUIT), true, DRIED_FRUIT_DECAY),
    SOUR_CHERRY(ItemFoodTFCF.get(ItemsTFCF.SOUR_CHERRY), true, DRIED_FRUIT_DECAY),
    SOURSOP(ItemFoodTFCF.get(ItemsTFCF.SOURSOP), true, DRIED_FRUIT_DECAY),
    STARFRUIT(ItemFoodTFCF.get(ItemsTFCF.STARFRUIT), true, DRIED_FRUIT_DECAY),
    TAMARILLO(ItemFoodTFCF.get(ItemsTFCF.TAMARILLO), true, DRIED_FRUIT_DECAY),
    TANGERINE(ItemFoodTFCF.get(ItemsTFCF.TANGERINE), true, DRIED_FRUIT_SATURATION),
    TROPICAL_APRICOT(ItemFoodTFCF.get(ItemsTFCF.TROPICAL_APRICOT), true, DRIED_FRUIT_SATURATION),
    VANILLA(ItemFoodTFCF.get(ItemsTFCF.VANILLA), true, DRIED_FRUIT_DECAY),
    WILD_CHERRY(ItemFoodTFCF.get(ItemsTFCF.WILD_CHERRY), true, DRIED_FRUIT_DECAY),
    YEW_BERRY(ItemFoodTFCF.get(ItemsTFCF.YEW_BERRY), true, DRIED_FRUIT_DECAY),
    PURPLE_GRAPE(ItemFoodTFCF.get(ItemsTFCF.PURPLE_GRAPE), true, DRIED_FRUIT_DECAY),
    GREEN_GRAPE(ItemFoodTFCF.get(ItemsTFCF.GREEN_GRAPE), true, DRIED_FRUIT_DECAY);

    private final Item fruit;
    private final boolean dry;
    private final FoodData driedData;

    FruitsTFCF(Item fruit, boolean dry, FoodData driedData)
    {
        this.fruit = fruit;
        this.dry = dry;
        this.driedData = driedData;
    }

    public Item getFruit()
    {
        return this.fruit;
    }

    public boolean canDry()
    {
        return this.dry;
    }

    public FoodData getDriedData()
    {
        return this.driedData;
    }
}