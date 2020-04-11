package tfcelementia.util.agriculture;

import net.dries007.tfc.world.classic.worldgen.WorldGenBerryBushes;
import net.minecraft.item.ItemStack;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;

import java.util.function.Supplier;

import net.dries007.tfc.api.types.IBerryBush;

import tfcelementia.util.agriculture.FoodTFCE;
import tfcelementia.util.agriculture.CropTFCE.CropType;
import tfcelementia.objects.items.food.ItemFoodTFCE;
import tfcelementia.util.ItemsTFCE;

public enum LeafBushTFCE implements IBerryBush
{
    BLACK_TEA(() -> new ItemStack(ItemsTFCE.BLACK_TEA), Month.MAY, 4, 5.0f, 35.0f, 100.0f, 400.0f, 0.8f, false),
    GREEN_TEA(() -> new ItemStack(ItemsTFCE.GREEN_TEA), Month.MAY, 4, 5.0f, 35.0f, 100.0f, 400.0f, 0.8f, false),
    WHITE_TEA(() -> new ItemStack(ItemsTFCE.WHITE_TEA), Month.MAY, 4, 5.0f, 35.0f, 100.0f, 400.0f, 0.8f, false);

    private final Month harvestMonthStart;
    private final int harvestingMonths;
    private final float growthTime;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;
    private final boolean hasSpikes;

    private LeafBushTFCE(Supplier<ItemStack> foodDrop, Month harvestMonthStart, final int harvestingMonths, final float minTemp, final float maxTemp, final float minRain, final float maxRain, final float growthTime, final boolean spiky) {
        this.harvestMonthStart = harvestMonthStart;
        this.harvestingMonths = harvestingMonths;
        this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * 24.0f;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minRain = minRain;
        this.maxRain = maxRain;
        this.hasSpikes = spiky;
    }
    
    public float getGrowthTime() {
        return this.growthTime;
    }
    
    public boolean isHarvestMonth(final Month month) {
        Month testing = this.harvestMonthStart;
        for (int i = 0; i < this.harvestingMonths; ++i) {
            if (testing.equals((Object)month)) {
                return true;
            }
            testing = testing.next();
        }
        return false;
    }
    
    public boolean isValidConditions(final float temperature, final float rainfall) {
        return this.minTemp - 5.0f < temperature && temperature < this.maxTemp + 5.0f && this.minRain - 50.0f < rainfall && rainfall < this.maxRain + 50.0f;
    }
    
    public boolean isValidForGrowth(final float temperature, final float rainfall) {
        return this.minTemp < temperature && temperature < this.maxTemp && this.minRain < rainfall && rainfall < this.maxRain;
    }
    
    public ItemStack getFoodDrop() {
        return ItemStack.EMPTY;
    }
    
    public IBerryBush.Size getSize() {
        return null;
    }
    
    public String getName() {
        return this.name().toLowerCase();
    }
    
    public boolean isSpiky() {
        return this.hasSpikes;
    }
    
    static {
        for (final IBerryBush bush : values()) {
            WorldGenBerryBushes.register(bush);
        }
    }
}