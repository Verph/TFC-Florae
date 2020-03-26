package tfcelementia.util.agriculture;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.util.OreDictionaryHelper;

import static tfcelementia.util.agriculture.FoodTFCE.Category.*;

public enum FoodTFCE 
{
    CACAO_POD(FRUIT, 4, 0.6f, 5f, 0f, 1f, 0f, 0.5f, 0f, 1.25f),
    COFFEE_CHERRIES(FRUIT, 4, 0.6f, 5f, 0f, 1f, 0f, 0.5f, 0f, 1.25f),
    DATE(FRUIT, 4, 0.6f, 5f, 0f, 1f, 0f, 0.5f, 0f, 2.25f),
    PAPAYA(FRUIT, 4, 0.4f, 5f, 0.5f, 0f, 0f, 0.5f, 0f, 3.75f),
    ALMOND(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    CASHEW(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    COCONUT(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    JUNIPER_BERRY(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    GREEN_GRAPES(FRUIT, 4, 0.4f, 5f, 0f, 0f, 0f, 0.5f, 0f, 4.5f),
    PURPLE_GRAPES(FRUIT, 4, 0.4f, 5f, 0f, 0f, 0f, 0.5f, 0f, 4.5f),
    HAZELNUT(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    MACADAMIA(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    PISTACHIO(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    ACORN(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    CHESTNUT(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    PECAN(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    PINE_NUT(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    WALNUT(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    RUTABAGA(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f),
    BLACK_EYED_PEAS(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 3.5f),
    GREEN_CAYENNE_PEPPER(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f),
    RED_CAYENNE_PEPPER(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f),
    GINGER(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f),
    //GINSENG(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f),
    CELERY(VEGETABLE, 4, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f),
    LETTUCE(VEGETABLE, 4, 0.4f, 5f, 0f, 0f, 0f, 1f, 0f, 2.5f),
    PEANUT(VEGETABLE, 4, 0.6f, 3f, 0f, 1.5f, 2f, 0.5f, 0.5f, 2.5f),
    SWEET_POTATO(VEGETABLE, 4, 0.8f, 3f, 2f, 0f, 1f, 1f, 0f, 3f),
    PIE(GRAIN, 4, 0.8f, 0f, 2f, 0.5f, 0.5f, 0f, 0f, 0.8f, 1f, 480f),
    PIE_APPLE(FRUIT, 4, 1.2f, 0f, 2.5f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f),
    PIE_BLACKBERRY(FRUIT, 4, 1.2f, 0f, 2f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f),
    PIE_BLUEBERRY(FRUIT, 4, 1.2f, 0f, 2.5f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f),
    PIE_CHERRY(FRUIT, 4, 1.2f, 0f, 2.5f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f),
    PIE_LEMON(FRUIT, 4, 1f, 0f, 2f, 0.5f, 0.5f, 1f, 0f, 0.8f, 1f, 480f),
    PIE_PEACH(FRUIT, 4, 1.2f, 0f, 2f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f),
    PIE_PUMPKIN(VEGETABLE, 4, 1.2f, 0f, 2f, 0.5f, 1f, 1f, 0f, 0.8f, 1f, 480f),
    PIE_RASPBERRY(FRUIT, 4, 1.2f, 0f, 2f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f),
    PIE_STRAWBERRY(FRUIT, 4, 1.2f, 0f, 2f, 0.5f, 0.5f, 1f, 0f, 0.8f, 1f, 480f);

    private final Category category;
    private final FoodData foodData;

    private final boolean heatable;
    private final float heatCapacity;
    private final float cookingTemp;

    private final String[] oreDictNames;

    FoodTFCE(@Nonnull Category category, int hunger, float saturation, float water, float grain, float veg, float fruit, float meat, float dairy, float decayModifier, String... oreNames)
    {
        this(category, hunger, saturation, water, grain, veg, fruit, meat, dairy, decayModifier, 0, -1, oreNames);
    }

    FoodTFCE(@Nonnull Category category, int hunger, float saturation, float water, float grain, float veg, float fruit, float meat, float dairy, float decayModifier, float heatCapacity, float cookingTemp, String... oreNames)
    {
        this.category = category;
        this.foodData = new FoodData(hunger, water, saturation, grain, fruit, veg, meat, dairy, decayModifier);

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
    public FoodData getData()
    {
        return foodData;
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
