package tfcflorae.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.util.calendar.ICalendar;

public class HelpersTFCF
{
    public HelpersTFCF()
    {}

    public static boolean doesStackMatchTool(ItemStack stack, String toolClass)
    {
        Set<String> toolClasses = stack.getItem().getToolClasses(stack);
        return toolClasses.contains(toolClass);
    }

    public static void insertWhitelistFluids()
    {
        ConfigManager.sync(TerraFirmaCraft.MOD_ID, Config.Type.INSTANCE);

        // Fluids
        String[] fluidAdditions = {
            "distilled_water",
            "waste",
            "base_potash_liquor",
            "white_tea",
            "green_tea",
            "black_tea",
            "chamomile_tea",
            "dandelion_tea",
            "labrador_tea",
            "coffee",
            "agave_wine",
            "barley_wine",
            "banana_wine",
            "berry_wine",
            "cherry_wine",
            "juniper_wine",
            "lemon_wine",
            "orange_wine",
            "papaya_wine",
            "peach_wine",
            "pear_wine",
            "plum_wine",
            "mead",
            "red_wine",
            "wheat_wine",
            "white_wine",
            "calvados",
            "gin",
            "tequila",
            "shochu",
            "grappa",
            "banana_brandy",
            "cherry_brandy",
            "lemon_brandy",
            "orange_brandy",
            "papaya_brandy",
            "peach_brandy",
            "pear_brandy",
            "plum_brandy",
            "berry_brandy",
            "brandy",
            "cognac",
            "beer_barley",
            "beer_corn",
            "beer_rye",
            "beer_wheat",
            "beer_amaranth",
            "beer_buckwheat",
            "beer_fonio",
            "beer_millet",
            "beer_quinoa",
            "beer_spelt",
            "sugar_water",
            "honey_water",
            "rice_water",
            "soybean_water",
            "linseed_water",
            "rape_seed_water",
            "sunflower_seed_water",
            "opium_poppy_seed_water",
            "sugar_beet_water",
            "soy_milk",
            "linseed_oil",
            "rape_seed_oil",
            "sunflower_seed_oil",
            "opium_poppy_seed_oil",
            "wort",
            "sweet_sap",
            "sweet_syrup",
            "resin",
            "kino",
            "firma_cola",
            "juice_blackberry",
            "juice_blueberry",
            "juice_bunch_berry",
            "juice_cloud_berry",
            "juice_cranberry",
            "juice_elderberry",
            "juice_gooseberry",
            "juice_raspberry",
            "juice_snow_berry",
            "juice_strawberry",
            "juice_wintergreen_berry",
            "juice_agave",
            "juice_apple",
            "juice_banana",
            "juice_cherry",
            "juice_lemon",
            "juice_orange",
            "juice_papaya",
            "juice_peach",
            "juice_pear",
            "juice_plum",
            "juice_juniper",
            "juice_green_grape",
            "juice_purple_grape",
            "juice_barrel_cactus"
        };
        Set<String> woodenBucketSet = new HashSet<>(Arrays.asList(ConfigTFC.General.MISC.woodenBucketWhitelist));
        for (String a : fluidAdditions)
        {
            woodenBucketSet.add(a);
        }
        ConfigTFC.General.MISC.woodenBucketWhitelist = woodenBucketSet.toArray(new String[] {});

        Set<String> barrelSet = new HashSet<>(Arrays.asList(ConfigTFC.Devices.BARREL.fluidWhitelist));
        for (String a : fluidAdditions)
        {
            barrelSet.add(a);
        }
        ConfigTFC.Devices.BARREL.fluidWhitelist = barrelSet.toArray(new String[] {});

        // Oil Fuels
        String[] oilAdditions = {
            "linseed_oil",
            "rape_seed_oil",
            "sunflower_seed_oil",
            "opium_poppy_seed_oil"
        };
        
        Set<String> fuelSet = new HashSet<>(Arrays.asList(ConfigTFC.Devices.LAMP.fuels));
        for (String a : oilAdditions)
        {
            fuelSet.add(a);
        }
        ConfigTFC.Devices.LAMP.fuels = fuelSet.toArray(new String[] {});
    }

    public static ItemStack updateFoodFuzzed(ItemStack oldStack, ItemStack newStack)
    {
        ItemStack output = CapabilityFood.updateFoodFromPrevious(oldStack, newStack);
        IFood cap = output.getCapability(CapabilityFood.CAPABILITY, null);
        if (cap != null && !cap.isRotten())
        {
            cap.setCreationDate(cap.getCreationDate() - (cap.getCreationDate() % ICalendar.HOURS_IN_DAY));
        }
        return output;
    }
}