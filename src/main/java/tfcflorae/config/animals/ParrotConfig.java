package tfcflorae.config.animals;

import java.util.function.Function;

import net.minecraftforge.common.ForgeConfigSpec;

import net.dries007.tfc.config.animals.ProducingAnimalConfig;

public record ParrotConfig(ProducingAnimalConfig inner, ForgeConfigSpec.IntValue hatchDays, ForgeConfigSpec.IntValue produceTicks, ForgeConfigSpec.DoubleValue produceFamiliarity)
{
    public static ParrotConfig build(Function<String, ForgeConfigSpec.Builder> builder, String name, double familiarityCap, int adulthoodDays, int uses, boolean eatsRottenFood, int produceTicks, double produceFamiliarity, int hatchDays)
    {
        return new ParrotConfig(
            ProducingAnimalConfig.build(builder, name, familiarityCap, adulthoodDays, uses, eatsRottenFood, produceTicks, produceFamiliarity),
            builder.apply("%sProduceTicks".formatted(name)).comment("Ticks until produce is ready").defineInRange("%sProduceTicks".formatted(name), produceTicks, 0, Integer.MAX_VALUE),
            builder.apply("%sHatchDays".formatted(name)).comment("Ticks until egg is ready to hatch").defineInRange("%sHatchDays".formatted(name), hatchDays, 0, Integer.MAX_VALUE),
            builder.apply("%sMinProduceFamiliarity".formatted(name)).comment("Minimum familiarity [0-1] needed for produce. Set above 1 to disable produce.").defineInRange("%sMinProduceFamiliarity".formatted(name), produceFamiliarity, 0, Float.MAX_VALUE)
        );
    }
}
