package tfcelementia.util.agriculture;

import static tfcelementia.util.agriculture.FoodTFCE.Category.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum FoodTFCE 
{
    CACAO_POD(FRUIT, 0.6f, 5f, 0f, 1f, 0f, 0.5f, 0f, 1.25f),
    //COFFEE_CHERRIES(FRUIT, 0.6f, 5f, 0f, 1f, 0f, 0.5f, 0f, 1.25f),
    DATE(FRUIT, 0.6f, 5f, 0f, 1f, 0f, 0.5f, 0f, 2.25f),
    PAPAYA(FRUIT, 0.4f, 5f, 0.5f, 0f, 0f, 0.5f, 0f, 3.75f),
    ALMOND(FRUIT, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    CASHEW(FRUIT, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    COCONUT(FRUIT, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    JUNIPER_BERRY(FRUIT, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    GREEN_GRAPES(FRUIT, 0.4f, 5f, 0f, 0f, 0f, 0.5f, 0f, 4.5f),
    PURPLE_GRAPES(FRUIT, 0.4f, 5f, 0f, 0f, 0f, 0.5f, 0f, 4.5f),
    HAZELNUT(FRUIT, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    MACADAMIA(FRUIT, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    PISTACHIO(FRUIT, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    ACORN(FRUIT, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    CHESTNUT(FRUIT, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    PECAN(FRUIT, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    PINE_NUT(FRUIT, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    WALNUT(FRUIT, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    RUTABAGA(VEGETABLE, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f),
    BLACK_EYED_PEAS(VEGETABLE, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 3.5f),
    GREEN_CAYENNE_PEPPER(VEGETABLE, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f),
    RED_CAYENNE_PEPPER(VEGETABLE, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f),
    GINGER(VEGETABLE, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f),
    //GINSENG(VEGETABLE, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f),
    CELERY(VEGETABLE, 0.4f, 3f, 0f, 0f, 0f, 1f, 0f, 2.5f),
    LETTUCE(VEGETABLE, 0.4f, 5f, 0f, 0f, 0f, 1f, 0f, 2.5f),
    PEANUT(VEGETABLE, 0.6f, 3f, 0f, 1.5f, 2f, 0.5f, 0.5f, 2.5f),
    SWEET_POTATO(VEGETABLE, 0.8f, 3f, 2f, 0f, 1f, 1f, 0f, 3f),
    PIE(GRAIN, 0.8f, 0f, 2f, 0.5f, 0.5f, 0f, 0f, 0.8f, 1f, 480f),
    PIE_APPLE(FRUIT, 1.2f, 0f, 2.5f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f),
    PIE_BLACKBERRY(FRUIT, 1.2f, 0f, 2f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f),
    PIE_BLUEBERRY(FRUIT, 1.2f, 0f, 2.5f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f),
    PIE_CHERRY(FRUIT, 1.2f, 0f, 2.5f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f),
    PIE_LEMON(FRUIT, 1f, 0f, 2f, 0.5f, 0.5f, 1f, 0f, 0.8f, 1f, 480f),
    PIE_PEACH(FRUIT, 1.2f, 0f, 2f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f),
    PIE_PUMPKIN(VEGETABLE, 1.2f, 0f, 2f, 0.5f, 1f, 1f, 0f, 0.8f, 1f, 480f),
    PIE_RASPBERRY(FRUIT, 1.2f, 0f, 2f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f),
    PIE_STRAWBERRY(FRUIT, 1.2f, 0f, 2f, 0.5f, 0.5f, 1f, 0f, 0.8f, 1f, 480f);

    private final Category category;
    private final float calories;
    private final float water;
    private final float carbohydrates;
    private final float fat;
    private final float protein;
    private final float vitamins;
    private final float minerals;
    private final float decayModifier;

    private final boolean heatable;
    private final float heatCapacity;
    private final float cookingTemp;

    private final String[] oreDictNames;

    FoodTFCE(@Nonnull Category category, float calories, float water, float carbohydrates, float fat, float protein, float vitamins, float minerals, float decayModifier, String... oreNames)
    {
        this(category, calories, water, carbohydrates, fat, protein, vitamins, minerals, decayModifier, 0, -1, oreNames);
    }

    FoodTFCE(@Nonnull Category category, float calories, float water, float carbohydrates, float fat, float protein, float vitamins, float minerals, float decayModifier, float heatCapacity, float cookingTemp, String... oreNames)
    {
        this.category = category;
        this.calories = calories;
        this.water = water;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.protein = protein;
        this.vitamins = vitamins;
        this.minerals = minerals;
        this.decayModifier = decayModifier;

        this.heatable = cookingTemp >= 0;
        this.heatCapacity = heatCapacity;
        this.cookingTemp = cookingTemp;

        this.oreDictNames = oreNames == null || oreNames.length == 0 ? null : oreNames;
    }

    public float getCalories()
    {
        return calories;
    }

    @Nonnull
    public Category getCategory()
    {
        return category;
    }

    public float getDecayModifier()
    {
        return decayModifier;
    }

    public float getWater()
    {
        return water;
    }

    public float[] getNutrients()
    {
        return new float[] {carbohydrates, fat, protein, vitamins, minerals};
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
        VEGETABLE,
        MEAT,
        DAIRY
    }
}
