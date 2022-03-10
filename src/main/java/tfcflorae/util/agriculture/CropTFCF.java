package tfcflorae.util.agriculture;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.types.ICrop;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropDead;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropSimple;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropSpreading;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.skills.Skill;
import net.dries007.tfc.util.skills.SkillTier;
import net.dries007.tfc.world.classic.worldgen.WorldGenWildCrops;

import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.objects.items.food.*;
import tfcflorae.world.worldgen.WorldGenWildCropsTFCF;

import static tfcflorae.util.agriculture.CropTFCF.CropType.*;

public enum CropTFCF implements ICrop
{
    AMARANTH(() -> new ItemStack(ItemsTFCF.AMARANTH), () -> ItemStack.EMPTY, 0f, 8f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, SIMPLE),
    BUCKWHEAT(() -> new ItemStack(ItemsTFCF.BUCKWHEAT), () -> ItemStack.EMPTY, -5f, 0f, 30f, 35f, 50f, 100f, 400f, 450f, 6, 0.5f, SIMPLE),
    FONIO(() -> new ItemStack(ItemsTFCF.FONIO), () -> ItemStack.EMPTY, 7f, 15f, 40f, 50f, 50f, 70f, 200f, 250f, 6, 0.5f, SIMPLE),
    MILLET(() -> new ItemStack(ItemsTFCF.MILLET), () -> ItemStack.EMPTY, 0f, 4f, 35f, 40f, 70f, 90f, 400f, 450f, 6, 0.5f, SIMPLE),
    QUINOA(() -> new ItemStack(ItemsTFCF.QUINOA), () -> ItemStack.EMPTY, -10f, -5f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, SIMPLE),
    SPELT(() -> new ItemStack(ItemsTFCF.SPELT), () -> ItemStack.EMPTY, 0f, 4f, 35f, 40f, 70f, 90f, 400f, 450f, 6, 0.5f, SIMPLE),
	BLACK_EYED_PEAS(() -> new ItemStack(ItemsTFCF.BLACK_EYED_PEAS), () -> ItemStack.EMPTY, 0f, 8f, 35f, 40f, 50f, 100f, 400f, 450f, 7, 0.5f, PICKABLE),
    CAYENNE_PEPPER(() -> new ItemStack(ItemsTFCF.RED_CAYENNE_PEPPER), () -> new ItemStack(ItemsTFCF.GREEN_CAYENNE_PEPPER), 4f, 12f, 35f, 40f, 50f, 100f, 400f, 450f, 7, 0.5f, PICKABLE),
    GINGER(() -> new ItemStack(ItemsTFCF.GINGER), () -> ItemStack.EMPTY, 0f, 5f, 35f, 40f, 50f, 100f, 400f, 450f, 5, 0.5f, SIMPLE),
    GINSENG(() -> new ItemStack(ItemsTFCF.GINSENG), () -> ItemStack.EMPTY, 0f, 5f, 35f, 40f, 50f, 100f, 400f, 450f, 5, 0.5f, SIMPLE),
    RUTABAGA(() -> new ItemStack(ItemsTFCF.RUTABAGA), () -> ItemStack.EMPTY, 0f, 8f, 35f, 40f, 50f, 100f, 400f, 450f, 7, 0.5f, SIMPLE),
    TURNIP(() -> new ItemStack(ItemsTFCF.TURNIP), () -> ItemStack.EMPTY, 0f, 8f, 35f, 40f, 50f, 100f, 400f, 450f, 7, 0.5f, SIMPLE),
    SUGAR_BEET(() -> new ItemStack(ItemsTFCF.SUGAR_BEET), () -> ItemStack.EMPTY, 0f, 5f, 35f, 40f, 50f, 100f, 400f, 450f, 7, 0.5f, SIMPLE),
	PURPLE_GRAPE(() -> new ItemStack(ItemsTFCF.PURPLE_GRAPE), () -> ItemStack.EMPTY, 0f, 8f, 35f, 40f, 50f, 100f, 400f, 450f, 8, 0.5f, PICKABLE),
	GREEN_GRAPE(() -> new ItemStack(ItemsTFCF.GREEN_GRAPE), () -> ItemStack.EMPTY, 0f, 8f, 35f, 40f, 50f, 100f, 400f, 450f, 8, 0.5f, PICKABLE),
    LIQUORICE_ROOT(() -> new ItemStack(ItemsTFCF.LIQUORICE_ROOT), () -> ItemStack.EMPTY, -20f, -1f, 18f, 26f, 50f, 60f, 310f, 340f, 8, 0.5f, SIMPLE),
    COFFEA(() -> new ItemStack(ItemsTFCF.COFFEA_CHERRIES), () -> ItemStack.EMPTY, 7f, 15f, 40f, 50f, 50f, 70f, 200f, 250f, 8, 0.5f, PICKABLE),
    AGAVE(() -> new ItemStack(ItemsTFCF.AGAVE), () -> ItemStack.EMPTY, 12f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, SIMPLE),
    COCA(() -> new ItemStack(ItemsTFCF.COCA_LEAF), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, PICKABLE),
    COTTON(() -> new ItemStack(ItemsTFCF.COTTON_BOLL), () -> ItemStack.EMPTY, 0f, 8f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, PICKABLE),
    FLAX(() -> new ItemStack(ItemsTFCF.FLAX), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, SIMPLE),
    HEMP(() -> new ItemStack(ItemsTFCF.HEMP), () -> new ItemStack(ItemsTFCF.CANNABIS_BUD), 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 5, 0.5f, PICKABLE),
    HOP(() -> new ItemStack(ItemsTFCF.HOPS), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, PICKABLE),
    INDIGO(() -> new ItemStack(ItemsTFCF.INDIGO), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 5, 0.5f, SIMPLE),
    MADDER(() -> new ItemStack(ItemsTFCF.MADDER), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 5, 0.5f, SIMPLE),
    OPIUM_POPPY(() -> new ItemStack(ItemsTFCF.OPIUM_POPPY_BULB), () -> ItemStack.EMPTY, 0f, 4f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, PICKABLE),
    RAPE(() -> new ItemStack(ItemsTFCF.RAPE), () -> ItemStack.EMPTY, 0f, 10f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, SIMPLE),
    WELD(() -> new ItemStack(ItemsTFCF.WELD), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 5, 0.5f, SIMPLE),
    WOAD(() -> new ItemStack(ItemsTFCF.WOAD), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 6, 0.5f, SIMPLE),
    TOBACCO(() -> new ItemStack(ItemsTFCF.TOBACCO_LEAF), () -> ItemStack.EMPTY, 0f, 18f, 35f, 40f, 50f, 100f, 400f, 450f, 7, 0.5f, PICKABLE);

    static
    {
        for (ICrop crop : values())
        {
            WorldGenWildCrops.register(crop);
            WorldGenWildCropsTFCF.register(crop);
        }
    }

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

    CropTFCF(ItemFoodTFCF foodDrop, float tempMinAlive, float tempMinGrow, float tempMaxGrow, float tempMaxAlive,
            float rainMinAlive, float rainMinGrow, float rainMaxGrow, float rainMaxAlive, int growthStages,
            float growthTime, CropType type)
    {
        this(() -> new ItemStack(ItemFoodTFCF.get(foodDrop)), () -> ItemStack.EMPTY, tempMinAlive, tempMinGrow, tempMaxGrow, tempMaxAlive, rainMinAlive, rainMinGrow, rainMaxGrow, rainMaxAlive, growthStages, growthTime, type);
    }

    CropTFCF(Supplier<ItemStack> foodDrop, Supplier<ItemStack> foodDropEarly, float tempMinAlive, float tempMinGrow, float tempMaxGrow, float tempMaxAlive, float rainMinAlive, float rainMinGrow, float rainMaxGrow, float rainMaxAlive, int growthStages, float growthTime, CropType type)
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
    public long getGrowthTicks()
    {
        return (long) (growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.TICKS_IN_DAY);
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

    public BlockCropTFC createGrowingBlock()
    {
        if (type == CropType.SIMPLE || type == CropType.PICKABLE)
        {
            return BlockCropSimple.create(this, type == CropType.PICKABLE);
        }
        else if (type == CropType.SPREADING)
        {
            return BlockCropSpreading.create(this);
        }
        throw new IllegalStateException("Invalid growthstage property " + growthStages + " for crop");
    }

    public BlockCropDead createDeadBlock()
    {
        return new BlockCropDead(this);
    }

    @SideOnly(Side.CLIENT)
    public void addInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if (GuiScreen.isShiftKeyDown())
        {
            tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.climate_info"));
            tooltip.add(TextFormatting.BLUE + I18n.format("tfc.tooltip.climate_info_rainfall", (int) rainMinGrow, (int) rainMaxGrow));
            tooltip.add(TextFormatting.GOLD + I18n.format("tfc.tooltip.climate_info_temperature", String.format("%.1f", tempMinGrow), String.format("%.1f", tempMaxGrow)));
        }
        else
        {
            tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.hold_shift_for_climate_info"));
        }
    }

    enum CropType
    {
        SIMPLE, PICKABLE, SPREADING
    }
}
