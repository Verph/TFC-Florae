package tfcelementia.util.agriculture;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.types.IBerryBush;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.world.classic.worldgen.WorldGenBerryBushes;

import tfcelementia.objects.items.food.ItemFoodTFCE;

import static net.dries007.tfc.api.types.IBerryBush.Size.*;

public enum BerryBushTFCE implements IBerryBush
{
    BLACK_TEA(FoodTFCE.BLACK_TEA_LEAF, Month.MAY, 4, 5.0f, 35.0f, 100.0f, 400.0f, 0.8f, LARGE, false),
    GREEN_TEA(FoodTFCE.GREEN_TEA_LEAF, Month.MAY, 4, 5.0f, 35.0f, 100.0f, 400.0f, 0.8f, LARGE, false),
    WHITE_TEA(FoodTFCE.WHITE_TEA_LEAF, Month.MAY, 4, 5.0f, 35.0f, 100.0f, 400.0f, 0.8f, LARGE, false);

    static
    {
        for (IBerryBush bush : values())
        {
            WorldGenBerryBushes.register(bush);
        }
    }

    private final FoodTFCE fruit;
    private final Month harvestMonthStart;
    private final int harvestingMonths;
    private final float growthTime;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;
    private final Size size;
    private final boolean hasSpikes;

    BerryBushTFCE(FoodTFCE fruit, Month harvestMonthStart, int harvestingMonths, float minTemp, float maxTemp, float minRain, float maxRain, float growthTime, Size size, boolean spiky)
    {
        this.fruit = fruit;
        this.harvestMonthStart = harvestMonthStart;
        this.harvestingMonths = harvestingMonths;
        this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;

        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minRain = minRain;
        this.maxRain = maxRain;

        this.size = size;
        this.hasSpikes = spiky;
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
        return new ItemStack(ItemFoodTFCE.get(getFruit()));
    }

    public Size getSize() { return this.size; }

    @Override
    public boolean isSpiky()
    {
        return hasSpikes;
    }
}