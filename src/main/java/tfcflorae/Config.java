package tfcflorae;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import net.dries007.tfc.util.Helpers;
import net.minecraft.resources.ResourceLocation;

import static tfcflorae.TFCFlorae.*;

public class Config
{
    public static final Config COMMON = register(ModConfig.Type.COMMON, Config::new);

    public static void init() {}

    private static <C> C register(ModConfig.Type type, Function<ForgeConfigSpec.Builder, C> factory)
    {
        Pair<C, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(factory);
        if (!Helpers.BOOTSTRAP_ENVIRONMENT) ModLoadingContext.get().registerConfig(type, specPair.getRight());
        return specPair.getLeft();
    }

    // General
    public final ForgeConfigSpec.BooleanValue enableDebug;

    public final ForgeConfigSpec.IntValue maxFortressHeight;
    public final ForgeConfigSpec.IntValue minFortressHeight;
    public final ForgeConfigSpec.IntValue mineralGenFrequency;

    Config(ForgeConfigSpec.Builder innerBuilder)
    {
        Function<String, ForgeConfigSpec.Builder> builder = name -> innerBuilder.translation(MOD_ID + ".config.common." + name);

        innerBuilder.push("general");

        enableDebug = builder.apply("enableDebug").comment("Toggles debug mode").define("enableDebug", false);

        maxFortressHeight = builder.apply("maxFortressHeight").comment("Heighest Y-value the Nether Fortress can generate at.").defineInRange("maxFortressHeight", -170, -256, 512);
        minFortressHeight = builder.apply("minFortressHeight").comment("Lowest Y-value the Nether Fortress can generate at.").defineInRange("minFortressHeight", -220, -256, 512);

        mineralGenFrequency = builder.apply("mineralGenFrequency").comment("How often minerals can generate near hot springs and lava.").defineInRange("mineralGenFrequency", 32, 0, 1000000);
    }
}
