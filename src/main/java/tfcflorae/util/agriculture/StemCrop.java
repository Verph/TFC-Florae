package tfcflorae.util.agriculture;

import java.util.function.Supplier;
import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.types.ICrop;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.world.classic.worldgen.WorldGenWildCrops;

import tfcflorae.objects.items.*;
import tfcflorae.util.agriculture.*;
import tfcflorae.objects.blocks.BlocksTFCF;

import static tfcflorae.util.agriculture.StemCrop.*;

public enum StemCrop implements ICrop
{
    PUMPKIN(() -> BlocksTFCF.PUMPKIN_FRUIT, 0f, 5f, 30f, 35f, 45f, 90f, 400f, 450f, 8, 0.5f),
    MELON(() -> BlocksTFCF.MELON_FRUIT, 5f, 11f, 35f, 40f, 150f, 200f, 420f, 460f, 8, 0.5f);

    static
    {
        for (ICrop crop : values())
        {
            WorldGenWildCrops.register(crop);
        }
    }

    // block to spawn when grown
    private final Supplier<Block> cropBlock;
    // temperature compatibility range
    private final float tempMinAlive, tempMinGrow, tempMaxGrow, tempMaxAlive;
    // rainfall compatibility range
    private final float rainMinAlive, rainMinGrow, rainMaxGrow, rainMaxAlive;
    // growth
    private final int growthStages; // the number of blockstates the crop has for growing, ignoring wild state
    private final float growthTime; // Time is measured in % of months, scales with calendar month length

    StemCrop(Supplier<Block> cropBlock, float tempMinAlive, float tempMinGrow, float tempMaxGrow, float tempMaxAlive, float rainMinAlive, float rainMinGrow, float rainMaxGrow, float rainMaxAlive, int growthStages, float growthTime)
    {
        this.cropBlock = cropBlock;

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
    }

    // crop life behavior copied from base tfc
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
    // end crop life behavior copied from base tfc

    //crop itself shouldn't drop anything
    @Nonnull
    @Override
    public ItemStack getFoodDrop(int currentStage)
    {
        return ItemStack.EMPTY;
    }

    public Block getCropBlock()
    {
        return cropBlock.get();
    }
}