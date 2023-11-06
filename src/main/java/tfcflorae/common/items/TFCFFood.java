package tfcflorae.common.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import net.dries007.tfc.common.TFCItemGroup;

public enum TFCFFood
{
    // Fruit
    BAOBAB_FRUIT(false, false),
    BARREL_CACTUS_FRUIT(false, false),
    CARAMBOLA(false, false),
    DRAGONBERRY(false, true, true),
    GLOW_BERRY(false, true),
    HAWTHORN(false, true),
    HOLLY_BERRY(false, true),
    JABUTICABA(false, true),
    JOSHUA_FRUIT(false, false),
    JUNIPER(false, true),
    KIWI(false, false),
    MEDLAR(false, false),
    MULBERRY(false, true),
    OSAGE_ORANGE(false, false),
    OTAHEITE_APPLE(false, false),
    PAPAYA(false, false),
    PEAR(false, false),
    PERSIMMON(false, false),
    PINK_IVORY_DRUPE(false, true),
    PITAHAYA(false, false),
    PRICKLY_PEAR(false, false),
    QUINCE(false, false),
    RIBERRY(false, true),
    ROWAN_BERRY(false, true),
    SAGUARO(false, false),
    SKY_FRUIT(false, false),
    SLOE(false, true),
    SORB_APPLE(false, false),
    YEW_BERRY(false, true),
    CRACKED_COCONUT(false, false),
    COCONUT_MEAT(false, false),
    DATE(false, true),
    DRIED_DATE(false, true),

    ACORN_SQUASH(false, false),
    AEHOBAK(false, false),
    BUTTERNUT(false, false),
    CROOKNECK(false, false),
    CUCUMBER(false, false),
    SCALLOPINI(false, false),
    TROMBONCINO(false, false),
    ZUCCHINI(false, false),

    FRESH_SEAWEED(false, true),

    // Fungi
    ROASTED_AMANITA(false, false, true),
    ROASTED_ARTISTS_CONK(false, false, true),
    ROASTED_BEEFSTEAK_FUNGUS(false, false),
    ROASTED_BIRCH_POLYPORE(false, false),
    ROASTED_BITTER_OYSTER(false, false),
    ROASTED_BLACK_POWDERPUFF(false, false, true),
    ROASTED_BOLETUS(false, false),
    ROASTED_BRIDAL_VEIL_STINKHORN(false, false),
    ROASTED_CHANTERELLE(false, false, true),
    ROASTED_CHLOROPHOS_FOXFIRE(false, false),
    ROASTED_DEATH_CAP(false, false, true),
    ROASTED_DEVILS_FINGERS(false, false),
    ROASTED_DRYADS_SADDLE(false, false),
    ROASTED_ENTOLOMA(false, false),
    ROASTED_GIANT_CLUB(false, false),
    ROASTED_INDIGO_MILK_CAP(false, false, true),
    ROASTED_LIONS_MANE(false, false, true),
    ROASTED_PARASOL_MUSHROOM(false, false),
    ROASTED_PORCINI(false, false),
    ROASTED_REISHI(false, false),
    ROASTED_SHAGGY_BRACKET(false, false),
    ROASTED_SHIITAKE(false, false),
    ROASTED_STINKHORN(false, false, true),
    ROASTED_SULPHUR_SHELF(false, false, true),
    ROASTED_TURKEY_TAIL(false, false, true),
    ROASTED_WEEPING_MILK_CAP(false, false),
    ROASTED_WOOD_BLEWIT(false, false),
    ROASTED_WOOLLY_GOMPHUS(false, false, true),

    // Other
    EUCALYPTUS_LEAVES(false, false, true),

    // Fish
    CRAPPIE(true, false),
    LAKE_TROUT(true, false),
    LARGEMOUTH_BASS(true, false),
    RAINBOW_TROUT(true, false),
    SMALLMOUTH_BASS(true, false),
    FROG_LEGS(true, false),
    COOKED_CRAPPIE(true, false),
    COOKED_LAKE_TROUT(true, false),
    COOKED_LARGEMOUTH_BASS(true, false),
    COOKED_RAINBOW_TROUT(true, false),
    COOKED_SMALLMOUTH_BASS(true, false),
    COOKED_FROG_LEGS(true, false);

    public final boolean meat, fast, hasEffect;

    TFCFFood(boolean meat, boolean fast)
    {
        this(false, false, false);
    }

    TFCFFood(boolean meat, boolean fast, boolean hasEffect)
    {
        this.meat = meat;
        this.fast = fast;
        this.hasEffect = hasEffect;
    }

    public FoodProperties getFoodProperties()
    {
        FoodProperties.Builder builder = new FoodProperties.Builder();
        if (meat) builder.meat();
        if (fast) builder.fast();
        return builder.nutrition(4).saturationMod(0.3f).build();
    }

    public Item.Properties createProperties()
    {
        Item.Properties props = new Item.Properties().food(getFoodProperties());
        return props.tab(TFCItemGroup.FOOD);
    }

    public boolean hasEffect()
    {
        return hasEffect;
    }
}