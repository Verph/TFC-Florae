package tfcflorae.util.agriculture;

import java.util.function.Supplier;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.world.classic.worldgen.WorldGenFruitTrees;

import tfcflorae.objects.items.ItemsTFCF;

public enum FruitTreeTFCF implements IFruitTree
{
    // Fruit Trees
    ABIU(() -> ItemsTFCF.ABIU, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    AMLA(() -> ItemsTFCF.AMLA, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    APRICOT(() -> ItemsTFCF.APRICOT, Month.FEBRUARY, 3, Month.NOVEMBER, 1, 23f, 36f, 250f, 400f, 0.33f),
    AVOCADO(() -> ItemsTFCF.AVOCADO, Month.APRIL, 2, Month.SEPTEMBER, 1, 9f, 27f, 60f, 230f, 0.33f),
    BAEL(() -> ItemsTFCF.BAEL, Month.MAY, 2, Month.JULY, 2, 18f, 31f, 250f, 400f, 0.33f),
    BAY_LAUREL(() -> ItemsTFCF.BAY_LAUREL, Month.MAY, 2, Month.OCTOBER, 2, 9f, 25f, 100f, 280f, 0.33f),
    BER(() -> ItemsTFCF.BER, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BERGAMOT(() -> ItemsTFCF.BERGAMOT, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    BLACK_CHERRY(() -> ItemsTFCF.BLACK_CHERRY, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BLACK_PEPPER(() -> ItemsTFCF.BLACK_PEPPER, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BLACKCURRANT(() -> ItemsTFCF.BLACKCURRANT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BLACKTHORN(() -> ItemsTFCF.BLACKTHORN, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BUDDHA_HAND(() -> ItemsTFCF.BUDDHA_HAND, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    CACAO(() -> ItemsTFCF.CACAO, Month.JUNE, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    CHERRY_PLUM(() -> ItemsTFCF.CHERRY_PLUM, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    CITRON(() -> ItemsTFCF.CITRON, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    COCONUT(() -> ItemsTFCF.COCONUT, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    COFFEA(() -> ItemsTFCF.COFFEA_CHERRIES, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    CRABAPPLE(() -> ItemsTFCF.CRABAPPLE, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    DAMSON_PLUM(() -> ItemsTFCF.DAMSON_PLUM, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    DATE(() -> ItemsTFCF.DATE, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    ELDER(() -> ItemsTFCF.ELDER, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    FIG(() -> ItemsTFCF.FIG, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    FINGER_LIME(() -> ItemsTFCF.FINGER_LIME, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    GRAPEFRUIT(() -> ItemsTFCF.GRAPEFRUIT, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    GUAVA(() -> ItemsTFCF.GUAVA, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    ICE_CREAM_BEAN(() -> ItemsTFCF.ICE_CREAM_BEAN, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    JACKFRUIT(() -> ItemsTFCF.JACKFRUIT, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    JUJUBE(() -> ItemsTFCF.JUJUBE, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    JUNIPER(() -> ItemsTFCF.JUNIPER, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    KAKI(() -> ItemsTFCF.KAKI, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    KEY_LIME(() -> ItemsTFCF.KEY_LIME, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    KLUWAK(() -> ItemsTFCF.KLUWAK, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    KUMQUAT(() -> ItemsTFCF.KUMQUAT, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    LONGAN(() -> ItemsTFCF.LONGAN, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    LOQUAT(() -> ItemsTFCF.LOQUAT, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    LYCHEE(() -> ItemsTFCF.LYCHEE, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    MAMEY_SAPOTE(() -> ItemsTFCF.MAMEY_SAPOTE, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    MANDERIN(() -> ItemsTFCF.MANDERIN, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    MANGO(() -> ItemsTFCF.MANGO, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    MANGOSTEEN(() -> ItemsTFCF.MANGOSTEEN, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    NECTARINE(() -> ItemsTFCF.NECTARINE, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    OHIA_AI(() -> ItemsTFCF.OHIA_AI, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    OSAGE_ORANGE(() -> ItemsTFCF.OSAGE_ORANGE, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    PAPAYA(() -> ItemsTFCF.PAPAYA, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    PASSION_FRUIT(() -> ItemsTFCF.PASSION_FRUIT, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    PEAR(() -> ItemsTFCF.PEAR, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    PERSIAN_LIME(() -> ItemsTFCF.PERSIAN_LIME, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    PERSIMMON(() -> ItemsTFCF.PERSIMMON, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    PERUVIAN_PEPPER(() -> ItemsTFCF.PERUVIAN_PEPPER, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    PLANTAIN(() -> ItemsTFCF.PLANTAIN, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    POMEGRANATE(() -> ItemsTFCF.POMEGRANATE, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    POMELO(() -> ItemsTFCF.POMELO, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    QUINCE(() -> ItemsTFCF.QUINCE, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    RAINIER_CHERRY(() -> ItemsTFCF.RAINIER_CHERRY, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    RED_BANANA(() -> ItemsTFCF.RED_BANANA, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    RED_CURRANT(() -> ItemsTFCF.RED_CURRANT, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    SAND_PEAR(() -> ItemsTFCF.SAND_PEAR, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    SAPODILLA(() -> ItemsTFCF.SAPODILLA, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    SATSUMA(() -> ItemsTFCF.SATSUMA, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    SOUR_CHERRY(() -> ItemsTFCF.SOUR_CHERRY, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    SOURSOP(() -> ItemsTFCF.SOURSOP, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    STARFRUIT(() -> ItemsTFCF.STARFRUIT, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    TAMARILLO(() -> ItemsTFCF.TAMARILLO, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    TANGERINE(() -> ItemsTFCF.TANGERINE, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    TROPICAL_APRICOT(() -> ItemsTFCF.TROPICAL_APRICOT, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    WILD_CHERRY(() -> ItemsTFCF.WILD_CHERRY, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),

    // Nut Trees
    //ACORN(() -> ItemsTFCF.ACORN, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    //ALMOND(() -> ItemsTFCF.ALMOND, Month.MAY, 2, Month.AUGUST, 1, 10f, 30f, 180f, 400f, 0.33f),
    //BEECHNUT(() -> ItemsTFCF.BEECHNUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    //BLACK_WALNUT(() -> ItemsTFCF.BLACK_WALNUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BRAZIL_NUT(() -> ItemsTFCF.BRAZIL_NUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BREADNUT(() -> ItemsTFCF.BREADNUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BUNYA(() -> ItemsTFCF.BUNYA_NUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BUTTERNUT(() -> ItemsTFCF.BUTTERNUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    CANDLENUT(() -> ItemsTFCF.CANDLENUT, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    CASHEW(() -> ItemsTFCF.CASHEW, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    //GINKGO(() -> ItemsTFCF.GINKGO_NUT, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    //HAZELNUT(() -> ItemsTFCF.HAZELNUT, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    HEARTNUT(() -> ItemsTFCF.HEARTNUT, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    //HICKORY(() -> ItemsTFCF.HICKORY_NUT, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    KOLA(() -> ItemsTFCF.KOLA_NUT, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    KUKUI(() -> ItemsTFCF.KUKUI_NUT, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    MACADAMIA(() -> ItemsTFCF.MACADAMIA, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    MONGONGO(() -> ItemsTFCF.MONGONGO, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    MONKEY_PUZZLE(() -> ItemsTFCF.MONKEY_PUZZLE_NUT, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    NUTMEG(() -> ItemsTFCF.NUTMEG, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    PARADISE_NUT(() -> ItemsTFCF.PARADISE_NUT, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    //PECAN(() -> ItemsTFCF.PECAN, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    //PINE(() -> ItemsTFCF.PINECONE, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    PISTACHIO(() -> ItemsTFCF.PISTACHIO, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    WALNUT(() -> ItemsTFCF.WALNUT, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f);

    static
    {
        for (FruitTreeTFCF tree : values())
        {
            WorldGenFruitTrees.register(tree);
        }
    }

    private final Supplier<Item> fruit;
    private final Month flowerMonthStart;
    private final int floweringMonths;
    private final Month harvestMonthStart;
    private final int harvestingMonths;
    private final float growthTime;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;

    FruitTreeTFCF(Supplier<Item> fruit, Month flowerMonthStart, int floweringMonths, Month harvestMonthStart, int harvestingMonths, float minTemp, float maxTemp, float minRain, float maxRain, float growthTime)
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
        return new ItemStack(this.fruit.get());
    }

    @Override
    public String getName()
    {
        return this.name();
    }
}