package tfcflorae.util.agriculture;

import net.minecraft.item.Item;

import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.util.agriculture.Food;

import tfcflorae.objects.items.*;
import tfcflorae.objects.items.food.*;

import static tfcflorae.util.agriculture.FoodDataTFCF.*;

/**
 * This is an easy way to wrap all the TFC fruits with our data
 */
public enum FruitsTFCF
{
    // TFC Foods
    /*
    BANANA(ItemFoodTFC.get(Food.BANANA), true, DRIED_FRUIT_DECAY, true),
    BLACKBERRY(ItemFoodTFC.get(Food.BLACKBERRY), true, DRIED_FRUIT_CATEGORY, true),
    BLUEBERRY(ItemFoodTFC.get(Food.BLUEBERRY), true, DRIED_FRUIT_CATEGORY, true),
    BUNCH_BERRY(ItemFoodTFC.get(Food.BUNCH_BERRY), true, DRIED_FRUIT_SATURATION, true),
    CHERRY(ItemFoodTFC.get(Food.CHERRY), true, DRIED_FRUIT_DECAY, true),
    CLOUD_BERRY(ItemFoodTFC.get(Food.CLOUD_BERRY), true, DRIED_FRUIT_DECAY, true),
    CRANBERRY(ItemFoodTFC.get(Food.CRANBERRY), true, DRIED_FRUIT_SATURATION, true),
    ELDERBERRY(ItemFoodTFC.get(Food.ELDERBERRY), true, DRIED_FRUIT_CATEGORY, true),
    GOOSEBERRY(ItemFoodTFC.get(Food.GOOSEBERRY), true, DRIED_FRUIT_SATURATION, true),
    GREEN_APPLE(ItemFoodTFC.get(Food.GREEN_APPLE), true, DRIED_FRUIT_DECAY, true),
    LEMON(ItemFoodTFC.get(Food.LEMON), true, DRIED_FRUIT_DECAY, true),
    OLIVE(ItemFoodTFC.get(Food.OLIVE), true, DRIED_FRUIT_DECAY, true),
    ORANGE(ItemFoodTFC.get(Food.ORANGE), true, DRIED_FRUIT_DECAY, true),
    PEACH(ItemFoodTFC.get(Food.PEACH), true, DRIED_FRUIT_SATURATION, true),
    PLUM(ItemFoodTFC.get(Food.PLUM), true, DRIED_FRUIT_SATURATION, true),
    RASPBERRY(ItemFoodTFC.get(Food.RASPBERRY), true, DRIED_FRUIT_SATURATION, true),
    RED_APPLE(ItemFoodTFC.get(Food.RED_APPLE), true, DRIED_FRUIT_DECAY, true),
    SNOW_BERRY(ItemFoodTFC.get(Food.SNOW_BERRY), true, DRIED_FRUIT_CATEGORY, true),
    STRAWBERRY(ItemFoodTFC.get(Food.STRAWBERRY), true, DRIED_FRUIT_SATURATION, true),
    WINTERGREEN_BERRY(ItemFoodTFC.get(Food.WINTERGREEN_BERRY), true, DRIED_FRUIT_CATEGORY, true),

    // Fruit Tree Fruits
    ABIU(ItemFoodTFCF.get(ItemsTFCF.ABIU), true, DRIED_FRUIT_DECAY, false),
    AMLA(ItemFoodTFCF.get(ItemsTFCF.AMLA), true, DRIED_FRUIT_DECAY, false),
    APRICOT(ItemFoodTFCF.get(ItemsTFCF.APRICOT), true, DRIED_FRUIT_DECAY, false),
    AVOCADO(ItemFoodTFCF.get(ItemsTFCF.AVOCADO), true, DRIED_FRUIT_DECAY, false),
    BAEL(ItemFoodTFCF.get(ItemsTFCF.BAEL), true, DRIED_FRUIT_DECAY, false),
    BAY_LAUREL(ItemFoodTFCF.get(ItemsTFCF.BAY_LAUREL), true, DRIED_FRUIT_DECAY, false),
    BER(ItemFoodTFCF.get(ItemsTFCF.BER), true, DRIED_FRUIT_DECAY, false),
    BERGAMOT(ItemFoodTFCF.get(ItemsTFCF.BERGAMOT), true, DRIED_FRUIT_DECAY, false),
    BLACK_CHERRY(ItemFoodTFCF.get(ItemsTFCF.BLACK_CHERRY), true, DRIED_FRUIT_DECAY, false),
    BLACK_PEPPER(ItemFoodTFCF.get(ItemsTFCF.BLACK_PEPPER), true, DRIED_FRUIT_DECAY, false),
    BLACKCURRANT(ItemFoodTFCF.get(ItemsTFCF.BLACKCURRANT), true, DRIED_FRUIT_DECAY, false),
    BLACKTHORN(ItemFoodTFCF.get(ItemsTFCF.BLACKTHORN), true, DRIED_FRUIT_SATURATION, false),
    BUDDHA_HAND(ItemFoodTFCF.get(ItemsTFCF.BUDDHA_HAND), true, DRIED_FRUIT_DECAY, false),
    CACAO(ItemFoodTFCF.get(ItemsTFCF.CACAO), true, DRIED_FRUIT_DECAY, false),
    CHERRY_PLUM(ItemFoodTFCF.get(ItemsTFCF.CHERRY_PLUM), true, DRIED_FRUIT_SATURATION, false),
    CITRON(ItemFoodTFCF.get(ItemsTFCF.CITRON), true, DRIED_FRUIT_DECAY, false),
    CRABAPPLE(ItemFoodTFCF.get(ItemsTFCF.CRABAPPLE), true, DRIED_FRUIT_DECAY, false),
    DAMSON_PLUM(ItemFoodTFCF.get(ItemsTFCF.DAMSON_PLUM), true, DRIED_FRUIT_SATURATION, false),
    DATE(ItemFoodTFCF.get(ItemsTFCF.DATE), true, DRIED_FRUIT_DECAY, false),
    ELDER(ItemFoodTFCF.get(ItemsTFCF.ELDER), true, DRIED_FRUIT_DECAY, false),
    FIG(ItemFoodTFCF.get(ItemsTFCF.FIG), true, DRIED_FRUIT_DECAY, false),
    FINGER_LIME(ItemFoodTFCF.get(ItemsTFCF.FINGER_LIME), true, DRIED_FRUIT_DECAY, false),
    GRAPEFRUIT(ItemFoodTFCF.get(ItemsTFCF.GRAPEFRUIT), true, DRIED_FRUIT_DECAY, false),
    GUAVA(ItemFoodTFCF.get(ItemsTFCF.GUAVA), true, DRIED_FRUIT_DECAY, false),
    ICE_CREAM_BEAN(ItemFoodTFCF.get(ItemsTFCF.ICE_CREAM_BEAN), true, DRIED_FRUIT_SATURATION, false),
    JACKFRUIT(ItemFoodTFCF.get(ItemsTFCF.JACKFRUIT), true, DRIED_FRUIT_SATURATION, false),
    JUJUBE(ItemFoodTFCF.get(ItemsTFCF.JUJUBE), true, DRIED_FRUIT_DECAY, false),
    JUNIPER(ItemFoodTFCF.get(ItemsTFCF.BER), true, DRIED_FRUIT_DECAY, false),
    KAKI(ItemFoodTFCF.get(ItemsTFCF.KAKI), true, DRIED_FRUIT_DECAY, false),
    KEY_LIME(ItemFoodTFCF.get(ItemsTFCF.KEY_LIME), true, DRIED_FRUIT_DECAY, false),
    KLUWAK(ItemFoodTFCF.get(ItemsTFCF.KLUWAK), true, DRIED_FRUIT_DECAY, false),
    KUMQUAT(ItemFoodTFCF.get(ItemsTFCF.KUMQUAT), true, DRIED_FRUIT_SATURATION, false),
    PERSIAN_LIME(ItemFoodTFCF.get(ItemsTFCF.PERSIAN_LIME), true, DRIED_FRUIT_DECAY, false),
    LONGAN(ItemFoodTFCF.get(ItemsTFCF.LONGAN), true, DRIED_FRUIT_DECAY, false),
    LOQUAT(ItemFoodTFCF.get(ItemsTFCF.LOQUAT), true, DRIED_FRUIT_SATURATION, false),
    LYCHEE(ItemFoodTFCF.get(ItemsTFCF.LYCHEE), true, DRIED_FRUIT_DECAY, false),
    MAMEY_SAPOTE(ItemFoodTFCF.get(ItemsTFCF.MAMEY_SAPOTE), true, DRIED_FRUIT_DECAY, false),
    MANDERIN(ItemFoodTFCF.get(ItemsTFCF.MANDERIN), true, DRIED_FRUIT_SATURATION, false),
    MANGO(ItemFoodTFCF.get(ItemsTFCF.MANGO), true, DRIED_FRUIT_DECAY, false),
    MANGOSTEEN(ItemFoodTFCF.get(ItemsTFCF.MANGOSTEEN), true, DRIED_FRUIT_DECAY, false),
    NECTARINE(ItemFoodTFCF.get(ItemsTFCF.NECTARINE), true, DRIED_FRUIT_DECAY, false),
    OHIA_AI(ItemFoodTFCF.get(ItemsTFCF.OHIA_AI), true, DRIED_FRUIT_DECAY, false),
    PAPAYA(ItemFoodTFCF.get(ItemsTFCF.PAPAYA), true, DRIED_FRUIT_DECAY, false),
    PASSION_FRUIT(ItemFoodTFCF.get(ItemsTFCF.PASSION_FRUIT), true, DRIED_FRUIT_DECAY, false),
    PEAR(ItemFoodTFCF.get(ItemsTFCF.PEAR), true, DRIED_FRUIT_SATURATION, false),
    PERSIMMON(ItemFoodTFCF.get(ItemsTFCF.PERSIMMON), true, DRIED_FRUIT_DECAY, false),
    PERUVIAN_PEPPER(ItemFoodTFCF.get(ItemsTFCF.PERUVIAN_PEPPER), true, DRIED_FRUIT_DECAY, false),
    PLANTAIN(ItemFoodTFCF.get(ItemsTFCF.PLANTAIN), true, DRIED_FRUIT_DECAY, false),
    POMEGRANATE(ItemFoodTFCF.get(ItemsTFCF.POMEGRANATE), true, DRIED_FRUIT_DECAY, false),
    POMELO(ItemFoodTFCF.get(ItemsTFCF.POMELO), true, DRIED_FRUIT_SATURATION, false),
    QUINCE(ItemFoodTFCF.get(ItemsTFCF.QUINCE), true, DRIED_FRUIT_DECAY, false),
    RAINIER_CHERRY(ItemFoodTFCF.get(ItemsTFCF.RAINIER_CHERRY), true, DRIED_FRUIT_DECAY, false),
    RED_BANANA(ItemFoodTFCF.get(ItemsTFCF.RED_BANANA), true, DRIED_FRUIT_DECAY, false),
    RED_CURRANT(ItemFoodTFCF.get(ItemsTFCF.RED_CURRANT), true, DRIED_FRUIT_DECAY, false),
    SAND_PEAR(ItemFoodTFCF.get(ItemsTFCF.SAND_PEAR), true, DRIED_FRUIT_DECAY, false),
    SAPODILLA(ItemFoodTFCF.get(ItemsTFCF.SAPODILLA), true, DRIED_FRUIT_DECAY, false),
    SATSUMA(ItemFoodTFCF.get(ItemsTFCF.SATSUMA), true, DRIED_FRUIT_SATURATION, false),
    SOUR_CHERRY(ItemFoodTFCF.get(ItemsTFCF.SOUR_CHERRY), true, DRIED_FRUIT_DECAY, false),
    SOURSOP(ItemFoodTFCF.get(ItemsTFCF.SOURSOP), true, DRIED_FRUIT_DECAY, false),
    STARFRUIT(ItemFoodTFCF.get(ItemsTFCF.STARFRUIT), true, DRIED_FRUIT_DECAY, false),
    TAMARILLO(ItemFoodTFCF.get(ItemsTFCF.TAMARILLO), true, DRIED_FRUIT_DECAY, false),
    TANGERINE(ItemFoodTFCF.get(ItemsTFCF.TANGERINE), true, DRIED_FRUIT_SATURATION, false),
    TROPICAL_APRICOT(ItemFoodTFCF.get(ItemsTFCF.TROPICAL_APRICOT), true, DRIED_FRUIT_SATURATION, false),
    VANILLA(ItemFoodTFCF.get(ItemsTFCF.VANILLA), true, DRIED_FRUIT_DECAY, false),
    */

    // Normal Tree Fruits
    BAOBAB_FRUIT(ItemFoodTFCF.get(ItemsTFCF.BAOBAB_FRUIT), true, DRIED_FRUIT_DECAY, false),
    BARREL_CACTUS_FRUIT(ItemFoodTFCF.get(ItemsTFCF.BARREL_CACTUS_FRUIT), true, DRIED_FRUIT_DECAY, false),
    HAWTHORN(ItemFoodTFCF.get(ItemsTFCF.HAWTHORN), true, DRIED_FRUIT_DECAY, false),
    OSAGE_ORANGE(ItemFoodTFCF.get(ItemsTFCF.OSAGE_ORANGE), true, DRIED_FRUIT_DECAY, false),
    PINK_IVORY_DRUPE(ItemFoodTFCF.get(ItemsTFCF.PINK_IVORY_DRUPE), true, DRIED_FRUIT_DECAY, false),
    RIBERRY(ItemFoodTFCF.get(ItemsTFCF.RIBERRY), true, DRIED_FRUIT_DECAY, false),
    ROWAN_BERRY(ItemFoodTFCF.get(ItemsTFCF.ROWAN_BERRY), true, DRIED_FRUIT_DECAY, false),
    SKY_FRUIT(ItemFoodTFCF.get(ItemsTFCF.SKY_FRUIT), true, DRIED_FRUIT_DECAY, false),
    YEW_BERRY(ItemFoodTFCF.get(ItemsTFCF.YEW_BERRY), true, DRIED_FRUIT_DECAY, false),

    // Crop Foods
    GREEN_GRAPE(ItemFoodTFCF.get(ItemsTFCF.GREEN_GRAPE), true, DRIED_FRUIT_DECAY, false),
    PURPLE_GRAPE(ItemFoodTFCF.get(ItemsTFCF.PURPLE_GRAPE), true, DRIED_FRUIT_DECAY, false);

    private final Item fruit;
    private final boolean dry;
    private final FoodData driedData;
    public final boolean isVanillaFood;

    FruitsTFCF(Item fruit, boolean dry, FoodData driedData, boolean isVanillaFood)
    {
        this.fruit = fruit;
        this.dry = dry;
        this.driedData = driedData;
        this.isVanillaFood = isVanillaFood;
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