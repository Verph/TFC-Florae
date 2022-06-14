package tfcflorae.common.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import net.dries007.tfc.common.TFCItemGroup;

public enum TFCFFood
{
    // Fruit
    BAOBAB_FRUIT,
    BARREL_CACTUS_FRUIT,
    HAWTHORN,
    HOLLY_BERRY,
    JUNIPER,
    MULBERRY,
    OSAGE_ORANGE,
    PINK_IVORY_DRUPE,
    RIBERRY,
    ROWAN_BERRY,
    SKY_FRUIT,
    YEW_BERRY;

    private final boolean meat, fast;

    TFCFFood()
    {
        this(false, false);
    }

    TFCFFood(boolean meat, boolean fast)
    {
        this.meat = meat;
        this.fast = fast;
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
}