/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package tfcflorae.util.agriculture;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.util.agriculture.*;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.world.classic.worldgen.WorldGenFruitTrees;

import tfcflorae.objects.items.food.ItemFoodTFCF;
import tfcflorae.util.agriculture.*;

public enum FruitTreeTFCF implements IFruitTree  
{
    ABIU(FoodTFCF.ABIU, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    ACORN(FoodTFCF.ACORN, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    ALLSPICE(FoodTFCF.ALLSPICE, Month.MAY, 2, Month.OCTOBER, 2, 8f, 25f, 110f, 280f, 0.33f),
    ALMOND(FoodTFCF.ALMOND, Month.MAY, 2, Month.AUGUST, 1, 10f, 30f, 180f, 400f, 0.33f),
    AMLA(FoodTFCF.AMLA, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    APRICOT(FoodTFCF.APRICOT, Month.FEBRUARY, 3, Month.NOVEMBER, 1, 23f, 36f, 250f, 400f, 0.33f),
    AVOCADO(FoodTFCF.AVOCADO, Month.APRIL, 2, Month.SEPTEMBER, 1, 9f, 27f, 60f, 230f, 0.33f),
    BAEL(FoodTFCF.BAEL, Month.MAY, 2, Month.JULY, 2, 18f, 31f, 250f, 400f, 0.33f),
    BAY_LAUREL(FoodTFCF.BAY_LAUREL, Month.MAY, 2, Month.OCTOBER, 2, 9f, 25f, 100f, 280f, 0.33f),
    BEECHNUT(FoodTFCF.BEECHNUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BER(FoodTFCF.BER, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BLACK_CHERRY(FoodTFCF.BLACK_CHERRY, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BLACK_WALNUT(FoodTFCF.BLACK_WALNUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BLACKCURRANT(FoodTFCF.BLACKCURRANT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BLACKTHORN(FoodTFCF.BLACKTHORN, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BRAZIL_NUT(FoodTFCF.BRAZIL_NUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BREADNUT(FoodTFCF.BREADNUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BUDDHA_HAND(FoodTFCF.BUDDHA_HAND, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BUNYA_NUT(FoodTFCF.BUNYA_NUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BUTTERNUT(FoodTFCF.BUTTERNUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f);

    static
    {
        for (IFruitTree tree : values())
        {
            WorldGenFruitTrees.register(tree);
        }
    }

    private final FoodTFCF fruit;
    private final Month flowerMonthStart;
    private final int floweringMonths;
    private final Month harvestMonthStart;
    private final int harvestingMonths;
    private final float growthTime;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;

    FruitTreeTFCF(FoodTFCF fruit, Month flowerMonthStart, int floweringMonths, Month harvestMonthStart, int harvestingMonths, float minTemp, float maxTemp, float minRain, float maxRain, float growthTime)
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

    public FoodTFCF getFruit()
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
        return new ItemStack(ItemFoodTFCF.get(this.getFruit()));
    }

    @Override
    public String getName()
    {
        return this.name();
    }
}