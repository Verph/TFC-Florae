package tfcelementia.util.agriculture;

import java.util.Random;
import java.util.function.Supplier;
import javax.annotation.Nonnull;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.types.ICrop;
//import net.dries007.tfc.objects.blocks.agriculture.BlockCropDead;
//import net.dries007.tfc.objects.blocks.agriculture.BlockCropSimple;
//import net.dries007.tfc.objects.blocks.agriculture.BlockCropSpreading;
//import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;
//import net.dries007.tfc.objects.items.ItemsTFC;
//import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.skills.Skill;
import net.dries007.tfc.util.skills.SkillTier;

import tfcelementia.objects.blocks.agriculture.BlockCropDeadTFCE;
import tfcelementia.objects.blocks.agriculture.BlockCropSimpleTFCE;
import tfcelementia.objects.blocks.agriculture.BlockCropSpreadingTFCE;
import tfcelementia.objects.blocks.agriculture.BlockCropTFCE;
import tfcelementia.util.agriculture.FoodTFCE;
import tfcelementia.util.ItemsRegistryHandler;
import tfcelementia.objects.items.ItemFoodTFCE;

import static tfcelementia.util.agriculture.CropTFCE.CropType.*;

public enum CropTFCE implements ICrop
{
    // todo: unique rain tolerances for each crop
    // todo: unique temp range for beets
    // todo: unique temp range for pumpkins
    // todo: unique temp range for melons
    // these definitions are defined in the spreadsheet at
    // https://docs.google.com/spreadsheets/d/1Ghw3dCmVO5Gv0MMGBydUxox_nwLYmmcZkGSbbf0QSAE/edit#gid=893781093
    // It should be modified first, and then the resulting definitions copied to this space here
	BLACK_EYED_PEAS(FoodTFCE.BLACK_EYED_PEAS, 0f, 8f, 35f, 40f, 50f, 100f, 400f, 450f, 7, 0.5f, PICKABLE),
    CAYENNE_PEPPER(() -> new ItemStack(ItemFoodTFCE.get(FoodTFCE.RED_CAYENNE_PEPPER)), () -> new ItemStack(ItemFoodTFCE.get(FoodTFCE.GREEN_CAYENNE_PEPPER)), 4f, 12f, 35f, 40f, 50f, 100f, 400f, 450f, 7, 0.5f, PICKABLE),
    CELERY(FoodTFCE.CELERY, 0f, 8f, 35f, 40f, 50f, 100f, 400f, 450f, 7, 0.5f, SIMPLE),
    GINGER(FoodTFCE.GINGER, 0f, 5f, 35f, 40f, 50f, 100f, 400f, 450f, 7, 0.5f, SIMPLE),
    //GINSENG(FoodTFCE.GINSENG, 0f, 5f, 35f, 40f, 50f, 100f, 400f, 450f, 5, 0.5f, SIMPLE),
    LETTUCE(FoodTFCE.LETTUCE, 0f, 10f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, SIMPLE),
    PEANUT(FoodTFCE.PEANUT, 0f, 8f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, SIMPLE),
    RUTABAGA(FoodTFCE.RUTABAGA, 0f, 8f, 35f, 40f, 50f, 100f, 400f, 450f, 7, 0.5f, SIMPLE),
    SWEET_POTATO(FoodTFCE.SWEET_POTATO, 0f, 4f, 35f, 40f, 50f, 100f, 400f, 450f, 7, 0.5f, SIMPLE),
    AGAVE(() -> new ItemStack(ItemsRegistryHandler.AGAVE), () -> ItemStack.EMPTY, 12f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, SIMPLE),
    COTTON(() -> new ItemStack(ItemsRegistryHandler.COTTON_BOLL), () -> ItemStack.EMPTY, 0f, 8f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, PICKABLE),
    FLAX(() -> new ItemStack(ItemsRegistryHandler.FLAX), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, SIMPLE),
    HEMP(() -> new ItemStack(ItemsRegistryHandler.HEMP), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 5, 0.5f, SIMPLE),
    HOPS(() -> new ItemStack(ItemsRegistryHandler.HOPS), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 8, 0.5f, SIMPLE),
    INDIGO(() -> new ItemStack(ItemsRegistryHandler.INDIGO), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 5, 0.5f, SIMPLE),
    MADDER(() -> new ItemStack(ItemsRegistryHandler.MADDER), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 5, 0.5f, SIMPLE),
    WELD(() -> new ItemStack(ItemsRegistryHandler.WELD), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 5, 0.5f, SIMPLE),
    WOAD(() -> new ItemStack(ItemsRegistryHandler.WOAD), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, SIMPLE);

    /**
     * the count to add to the amount of food dropped when applying the skill bonus
     *
     * @param skill  agriculture skill of the harvester
     * @param random random instance to use, generally Block.RANDOM
     * @return amount to add to item stack count
     */
    public static int getSkillFoodBonus(Skill skill, Random random)
    {
        return random.nextInt(2 + (int) (6 * skill.getTotalLevel()));
    }

    /**
     * the count to add to the amount of seeds dropped when applying the skill bonus
     *
     * @param skill  agriculture skill of the harvester
     * @param random random instance to use, generally Block.RANDOM
     * @return amount to add to item stack count
     */
    public static int getSkillSeedBonus(Skill skill, Random random)
    {
        if (skill.getTier().isAtLeast(SkillTier.ADEPT) && random.nextInt(10 - 2 * skill.getTier().ordinal()) == 0)
            return 1;
        else
            return 0;
    }

    // how this crop generates food items
    private final Supplier<ItemStack> foodDrop;
    private final Supplier<ItemStack> foodDropEarly;
    // temperature compatibility range
    private final float tempMinAlive, tempMinGrow, tempMaxGrow, tempMaxAlive;
    // rainfall compatibility range
    private final float rainMinAlive, rainMinGrow, rainMaxGrow, rainMaxAlive;
    // growth
    private final int growthStages; // the number of blockstates the crop has for growing, ignoring wild state
    private final float growthTime; // Time is measured in % of months, scales with calendar month length
    // which crop block behavior implementation is used
    private final CropType type;

    CropTFCE(FoodTFCE foodDrop, float tempMinAlive, float tempMinGrow, float tempMaxGrow, float tempMaxAlive, float rainMinAlive, float rainMinGrow, float rainMaxGrow, float rainMaxAlive, int growthStages, float growthTime, CropType type)
    {
        this(() -> new ItemStack(ItemFoodTFCE.get(foodDrop)), () -> ItemStack.EMPTY, tempMinAlive, tempMinGrow, tempMaxGrow, tempMaxAlive, rainMinAlive, rainMinGrow, rainMaxGrow, rainMaxAlive, growthStages, growthTime, type);
    }

    CropTFCE(Supplier<ItemStack> foodDrop, Supplier<ItemStack> foodDropEarly, float tempMinAlive, float tempMinGrow, float tempMaxGrow, float tempMaxAlive, float rainMinAlive, float rainMinGrow, float rainMaxGrow, float rainMaxAlive, int growthStages, float growthTime, CropType type)
    {
        this.foodDrop = foodDrop;
        this.foodDropEarly = foodDropEarly;

        this.tempMinAlive = tempMinAlive;
        this.tempMinGrow = tempMinGrow;
        this.tempMaxGrow = tempMaxGrow;
        this.tempMaxAlive = tempMaxAlive;

        this.rainMinAlive = rainMinAlive;
        this.rainMinGrow = rainMinGrow;
        this.rainMaxGrow = rainMaxGrow;
        this.rainMaxAlive = rainMaxAlive;

        this.growthStages = growthStages;
        this.growthTime = growthTime; // This is measured in % of months

        this.type = type;
    }

    @Override
    public float getGrowthTime()
    {
        return growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.TICKS_IN_DAY;
    }

    @Override
    public int getMaxStage()
    {
        return growthStages - 1;
    }

    @Override
    public boolean isValidConditions(float temperature, float rainfall)
    {
        return tempMinAlive < temperature && temperature < tempMaxAlive && rainMinAlive < rainfall && rainfall < rainMaxAlive;
    }

    @Override
    public boolean isValidForGrowth(float temperature, float rainfall)
    {
        return tempMinGrow < temperature && temperature < tempMaxGrow && rainMinGrow < rainfall && rainfall < rainMaxGrow;
    }

    @Nonnull
    @Override
    public ItemStack getFoodDrop(int currentStage)
    {
        if (currentStage == getMaxStage())
        {
            return foodDrop.get();
        }
        else if (currentStage == getMaxStage() - 1)
        {
            return foodDropEarly.get();
        }
        return ItemStack.EMPTY;
    }

    public BlockCropTFCE createGrowingBlock()
    {
        if (type == SIMPLE || type == PICKABLE)
        {
            return BlockCropSimpleTFCE.create(this, type == PICKABLE);
        }
        else if (type == SPREADING)
        {
            return BlockCropSpreadingTFCE.create(this);
        }
        throw new IllegalStateException("Invalid growthstage property " + growthStages + " for crop");
    }

    public BlockCropDeadTFCE createDeadBlock()
    {
        return new BlockCropDeadTFCE(this);
    }

    enum CropType
    {
        SIMPLE, PICKABLE, SPREADING
    }
}
