package tfcelementia.util.agriculture;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.food.FoodData;
//import net.dries007.tfc.util.OreDictionaryHelper;

import tfcelementia.util.OreDictionaryHelper;

import static tfcelementia.util.agriculture.FoodTFCE.Category.*;

public enum FoodTFCE 
{
    CACAO_POD(FRUIT, 4, 0.6f, 5f, 0f, 1f, 0f, 0.5f, 0f, 1.25f, "crop_cacao", "crop_cocoa", "cocoa", "cacao"),
    //COFFEE_CHERRIES(FRUIT, 4, 0.6f, 5f, 0f, 1f, 0f, 0.5f, 0f, 1.25f, "cropCoffee"),
    //DRIED_COFFEE_CHERRIES(FRUIT, 4, 0.6f, 5f, 0f, 1f, 0f, 0.5f, 0f, 1.25f, "cropDriedCoffee"),
    //ROASTED_COFFEE_BEANS(FRUIT, 4, 0.6f, 5f, 0f, 1f, 0f, 0.5f, 0f, 1.25f, "cropRoastedCoffee"),
    DATE(FRUIT, 4, 0.6f, 5f, 0f, 1f, 0f, 0.5f, 0f, 2.25f),
    PAPAYA(FRUIT, 4, 0.4f, 5f, 0.5f, 0f, 0f, 0.5f, 0f, 3.75f),
    ALMOND(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.1f),
    CASHEW(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.1f),
    COCONUT(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.4f),
    PEAR(FRUIT, 4, 0f, 0f, 0f, 0f, 1.5f, 0f, 0f, 3.75f, "pear"),
    JUNIPER_BERRY(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f),
    GREEN_GRAPES(FRUIT, 4, 0.4f, 5f, 0f, 0f, 0f, 0.5f, 0f, 4.5f, "crop_green_grapes", "crop_grapes"),
    PURPLE_GRAPES(FRUIT, 4, 0.4f, 5f, 0f, 0f, 0f, 0.5f, 0f, 4.5f, "crop_purple_grapes", "crop_grapes"),
    HAZELNUT(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.1f),
    MACADAMIA(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.1f),
    PISTACHIO(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.1f),
    ACORN(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.1f),
    CHESTNUT(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.1f),
    PECAN(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.1f),
    PINE_NUT(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.1f),
    WALNUT(FRUIT, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.1f),
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
    LINSEED_PASTE(GRAIN, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f, "crop_linseed_paste"),
    RAPE_SEED(GRAIN, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f, "crop_rape_seed"),
    RAPE_SEED_PASTE(GRAIN, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f, "crop_rape_seed_paste"),
    SUNFLOWER_SEED(GRAIN, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f, "crop_sunflower_seed"),
    SUNFLOWER_SEED_PASTE(GRAIN, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f, "crop_sunflower_seedPaste"),
    OPIUM_POPPY_SEED(GRAIN, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, "crop_opium_poppy"),
    OPIUM_POPPY_SEED_PASTE(GRAIN, 4, 0.6f, 0f, 0.2f, 0.1f, 0.1f, 0f, 0f, 0.8f, "crop_opium_poppy_seed_paste"),
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
    MASHED_SUGAR_BEET(VEGETABLE, 4, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f, "mashed_sugar_beet"),
    SOYBEAN_PASTE(VEGETABLE, 4, 0.8f, 3f, 2f, 0f, 1f, 1f, 0f, 3f, "paste_soybean"),
    //BLACK_TEA_LEAF(FRUIT, 0, 0.5f, 0f, 0f, 0f, 0f,0f, 0f, 0.5f, "crop_tea"),
    //GREEN_TEA_LEAF(FRUIT, 0, 0.5f, 0f, 0f, 0f, 0f,0f, 0f, 0.5f, "crop_tea"),
    //WHITE_TEA_LEAF(FRUIT, 0, 0.5f, 0f, 0f, 0f, 0f,0f, 0f, 0.5f, "crop_tea"),
    PIE(GRAIN, 4, 0.8f, 0f, 2f, 0.5f, 0.5f, 0f, 0f, 0.8f, 1f, 480f, "pie"),
    PIE_APPLE(FRUIT, 4, 1.2f, 0f, 2.5f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f, "pieApple"),
    PIE_BLACKBERRY(FRUIT, 4, 1.2f, 0f, 2f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f, "pieBlackberry"),
    PIE_BLUEBERRY(FRUIT, 4, 1.2f, 0f, 2.5f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f, "pieBlueberry"),
    PIE_CHERRY(FRUIT, 4, 1.2f, 0f, 2.5f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f, "pieCherry"),
    PIE_LEMON(FRUIT, 4, 1f, 0f, 2f, 0.5f, 0.5f, 1f, 0f, 0.8f, 1f, 480f, "pieLemon"),
    PIE_PEACH(FRUIT, 4, 1.2f, 0f, 2f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f, "piePeach"),
    PIE_PUMPKIN(VEGETABLE, 4, 1.2f, 0f, 2f, 0.5f, 1f, 1f, 0f, 0.8f, 1f, 480f, "piePumpkin"),
    PIE_RASPBERRY(FRUIT, 4, 1.2f, 0f, 2f, 0.5f, 0.5f, 0.5f, 0f, 0.8f, 1f, 480f, "pieRaspberry"),
    PIE_STRAWBERRY(FRUIT, 4, 1.2f, 0f, 2f, 0.5f, 0.5f, 1f, 0f, 0.8f, 1f, 480f, "pieStrawberry"),
    AMARANTH_BREAD_SANDWICH(MEAL, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f, "sandwich"),
    BUCKWHEAT_BREAD_SANDWICH(MEAL, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f, "sandwich"),
    FONIO_BREAD_SANDWICH(MEAL, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f, "sandwich"),
    MILLET_BREAD_SANDWICH(MEAL, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f, "sandwich"),
    QUINOA_BREAD_SANDWICH(MEAL, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f, "sandwich"),
    SPELT_BREAD_SANDWICH(MEAL, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f, "sandwich"),
    WILD_RICE_BREAD_SANDWICH(MEAL, 4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f, "sandwich");

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
