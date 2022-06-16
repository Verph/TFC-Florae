package tfcflorae.util.climate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.RegisteredDataManager;
import net.dries007.tfc.util.climate.ClimateRange;

import tfcflorae.common.blocks.wood.TFCFWood;

public class TFCFClimateRanges
{
    public static final Map<TFCFWood, Supplier<ClimateRange>> LARGE_FRUIT_TREES = WoodClimateMapper(TFCFWood.class);

    private static Map<TFCFWood, Supplier<ClimateRange>> WoodClimateMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood, Supplier<ClimateRange>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (!wood.isFruitTree()) continue;

            Map.put(wood, register("tree/" + wood.name()));
        }
        return Map;
    }

    private static RegisteredDataManager.Entry<ClimateRange> register(String name)
    {
        return ClimateRange.MANAGER.register(Helpers.identifier(name.toLowerCase(Locale.ROOT)));
    }
}
