package tfcflorae;

import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import net.dries007.tfc.util.Helpers;

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
    public final ForgeConfigSpec.BooleanValue enableDunes;
    public final ForgeConfigSpec.BooleanValue enableSandLayers;
    public final ForgeConfigSpec.BooleanValue enableUndergroundRivers;
    public final ForgeConfigSpec.BooleanValue toggleCheapSandColourCalculations;

    public final ForgeConfigSpec.IntValue maxFortressHeight;
    public final ForgeConfigSpec.IntValue minFortressHeight;
    public final ForgeConfigSpec.IntValue mineralGenFrequency;
    public final ForgeConfigSpec.IntValue fruitingLeavesUpdateChance;
    public final ForgeConfigSpec.IntValue leavesSaplingPlacementChance;
    public final ForgeConfigSpec.IntValue leavesSaplingSpreadDistance;

    public final ForgeConfigSpec.IntValue landBiomeSizes;
    public final ForgeConfigSpec.IntValue earlyBiomeSizes;
    public final ForgeConfigSpec.IntValue midBiomeSizes;
    public final ForgeConfigSpec.IntValue lateBiomeSizes;

    Config(ForgeConfigSpec.Builder innerBuilder)
    {
        Function<String, ForgeConfigSpec.Builder> builder = name -> innerBuilder.translation(MOD_ID + ".config.common." + name);

        innerBuilder.push("general");

        enableDebug = builder.apply("enableDebug").comment("Toggles debug mode").define("enableDebug", false);
        enableDunes = builder.apply("enableDunes").comment("Toggles generation of sand dunes in deserts. Disabling this (false) will shorten world generation time drastically.").define("enableDunes", false);
        enableSandLayers = builder.apply("enableSandLayers").comment("Toggles generation of sand layers. Disabling this (false) will shorten world generation time drastically.").define("enableSandLayers", true);
        enableUndergroundRivers = builder.apply("enableUndergroundRivers").comment("Toggles generation of underground rivers.").define("enableUndergroundRivers", false);
        toggleCheapSandColourCalculations = builder.apply("toggleCheapSandColourCalculations").comment("Toggles calculation method for sand colour detection. If true, then it simply checks for the colour of the block below, while false checks for every direction.").define("toggleCheapSandColourCalculations", true);

        maxFortressHeight = builder.apply("maxFortressHeight").comment("Heighest Y-value the Nether Fortress can generate at.").defineInRange("maxFortressHeight", -170, -256, 512);
        minFortressHeight = builder.apply("minFortressHeight").comment("Lowest Y-value the Nether Fortress can generate at.").defineInRange("minFortressHeight", -220, -256, 512);

        mineralGenFrequency = builder.apply("mineralGenFrequency").comment("How often minerals can generate near hot springs and lava.").defineInRange("mineralGenFrequency", 32, 0, Integer.MAX_VALUE);
        fruitingLeavesUpdateChance = builder.apply("fruitingLeavesUpdateChance").comment("How often seasonal/fruiting leaves should check for updates. Higher values --> slower updates.").defineInRange("fruitingLeavesUpdateChance", 70, 0, Integer.MAX_VALUE);

        leavesSaplingPlacementChance = builder.apply("leavesSaplingPlacementChance").comment("The chance for the tree leaves to drop saplings. Set to \"0\" to disable entirely.").defineInRange("leavesSaplingPlacementChance", 200, 0, Integer.MAX_VALUE);
        leavesSaplingSpreadDistance = builder.apply("leavesSaplingSpreadDistance").comment("Distance multiplication factor of which leaves will randomly spread and place saplings, for natural regrowth of the forests, during fall and spring seasons.").defineInRange("leavesSaplingSpreadDistance", 12, 0, 16);

        landBiomeSizes = builder.apply("landBiomeSizes").comment(
            "The amount of times to increase land biome sizes. Setting to 0 equates to regular TFC-sized land biomes.",
            "Game needs to restart in order to take effect."
        ).defineInRange("landBiomeSizes", 1, 0, 64);

        earlyBiomeSizes = builder.apply("earlyBiomeSizes").comment(
            "The amount of times to increase all biome sizes, after generating oceans, and prior to making edge biomes.",
            "This settings stacks on top of \"landBiomeSizes\", so choose wisely. Setting to 0 equates to smaller than regular TFC biome sizes.",
            "Game needs to restart in order to take effect."
        ).defineInRange("earlyBiomeSizes", 1, 0, 64);

        midBiomeSizes = builder.apply("midBiomeSizes").comment(
            "The amount of times to increase all biome sizes, after generating edge-biomes and before final biome expansions.",
            "This settings stacks on top of \"earlyBiomeSizes\", so choose wisely. Setting to 0 equates to smaller than regular TFC biome sizes.",
            "Game needs to restart in order to take effect.").defineInRange("midBiomeSizes", 1, 0, 64);

        lateBiomeSizes = builder.apply("lateBiomeSizes").comment(
            "The amount of times to increase all biome sizes, during the final touches.",
            "This includes beaches, shores, lakes and similar garnishes. This settings stacks on top of \"midBiomeSizes\", so choose wisely.",
            "Setting to 0 equates to smaller than regular TFC biome sizes. Game needs to restart in order to take effect."
        ).defineInRange("lateBiomeSizes", 4, 0, 64);
    }
}
