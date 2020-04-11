package tfcelementia.util.agriculture;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.world.classic.worldgen.WorldGenFruitTrees;

import tfcelementia.objects.items.food.ItemFoodTFCE;

public enum FruitTreeTFCE implements IFruitTree
{
    CACAO(FoodTFCE.CACAO_POD, Month.APRIL, 2, Month.OCTOBER, 2, 5f, 35f, 100f, 400f, 0.33f),
    //COFFEA(FoodTFCE.COFFEE_CHERRIES, Month.APRIL, 2, Month.OCTOBER, 2, 5f, 35f, 100f, 400f, 0.33f),
    PAPAYA(FoodTFCE.PAPAYA, Month.MAY, 2, Month.SEPTEMBER, 2, 5f, 35f, 100f, 400f, 0.33f),
    //DATE(FoodTFCE.DATE, Month.APRIL, 2, Month.SEPTEMBER, 2, 5f, 35f, 100f, 400f, 0.33f),
    ALMOND(FoodTFCE.ALMOND, Month.MARCH, 1, Month.OCTOBER, 1, 5f, 35f, 100f, 400f, 0.33f),
    CASHEW(FoodTFCE.CASHEW, Month.DECEMBER, 1, Month.MAY, 1, 5f, 35f, 100f, 400f, 0.33f),
    //COCONUT(FoodTFCE.COCONUT, Month.APRIL, 1, Month.JULY, 2, 5f, 35f, 100f, 400f, 0.33f),
    PEAR(FoodTFCE.PEAR, Month.MAY, 2, Month.OCTOBER, 2, 5f, 35f, 100f, 400f, 0.33f),
    HAZELNUT(FoodTFCE.HAZELNUT, Month.DECEMBER, 1, Month.OCTOBER, 1, 5f, 35f, 100f, 400f, 0.33f),
    MACADAMIA(FoodTFCE.MACADAMIA, Month.MARCH, 1, Month.SEPTEMBER, 1, 5f, 35f, 100f, 400f, 0.33f),
    PISTACHIO(FoodTFCE.PISTACHIO, Month.APRIL, 1, Month.OCTOBER, 1, 5f, 35f, 100f, 400f, 0.33f);

    static
    {
        for (IFruitTree tree : values())
        {
            WorldGenFruitTrees.register(tree);
        }
    }

    private final FoodTFCE fruit;
    private final Month flowerMonthStart;
    private final int floweringMonths;
    private final Month harvestMonthStart;
    private final int harvestingMonths;
    private final float growthTime;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;

    FruitTreeTFCE(FoodTFCE fruit, Month flowerMonthStart, int floweringMonths, Month harvestMonthStart, int harvestingMonths, float minTemp, float maxTemp, float minRain, float maxRain, float growthTime)
    {
        this.fruit = fruit;
        this.flowerMonthStart = flowerMonthStart;
        this.floweringMonths = floweringMonths;
        this.harvestMonthStart = harvestMonthStart;
        this.harvestingMonths = harvestingMonths;
        this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;

        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minRain = minRain;
        this.maxRain = maxRain;
    }

    public FoodTFCE getFruit()
    {
        return this.fruit;
    }

    @Override
    public float getGrowthTime()
    {
        return this.growthTime;
    }

    @Override
    public boolean isFlowerMonth(Month month)
    {
        Month testing = this.flowerMonthStart;
        for (int i = 0; i < this.floweringMonths; i++)
        {
            if (testing.equals(month)) return true;
            testing = testing.next();
        }
        return false;
    }

    @Override
    public boolean isHarvestMonth(Month month)
    {
        Month testing = this.harvestMonthStart;
        for (int i = 0; i < this.harvestingMonths; i++)
        {
            if (testing.equals(month)) return true;
            testing = testing.next();
        }
        return false;
    }

    @Override
    public boolean isValidConditions(float temperature, float rainfall)
    {
        return minTemp - 5 < temperature && temperature < maxTemp + 5 && minRain - 50 < rainfall && rainfall < maxRain + 50;
    }

    @Override
    public boolean isValidForGrowth(float temperature, float rainfall)
    {
        return minTemp < temperature && temperature < maxTemp && minRain < rainfall && rainfall < maxRain;
    }

    @Override
    public ItemStack getFoodDrop()
    {
        return new ItemStack(ItemFoodTFCE.get(this.getFruit()));
    }

    @Override
    public String getName()
    {
        return this.name();
    }
}