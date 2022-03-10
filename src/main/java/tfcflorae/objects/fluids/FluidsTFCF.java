package tfcflorae.objects.fluids;

import javax.annotation.Nonnull;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import net.dries007.tfc.Constants;
import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.api.capability.food.FoodStatsTFC;
import net.dries007.tfc.api.capability.food.IFoodStatsTFC;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.DrinkableProperty;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;

import net.dries007.tfc.TerraFirmaCraft;

public final class FluidsTFCF
{
    private static final ResourceLocation STILL = new ResourceLocation(TerraFirmaCraft.MOD_ID, "blocks/fluid_still");
    private static final ResourceLocation FLOW = new ResourceLocation(TerraFirmaCraft.MOD_ID, "blocks/fluid_flow");

    private static final HashBiMap<Fluid, FluidWrapper> WRAPPERS = HashBiMap.create();

    // Other
    public static FluidWrapper DISTILLED_WATER;
    public static FluidWrapper WASTE;
    public static FluidWrapper BASE_POTASH_LIQUOR;

    // Tea
    public static FluidWrapper WHITE_TEA;
    public static FluidWrapper GREEN_TEA;
    public static FluidWrapper BLACK_TEA;
    public static FluidWrapper CHAMOMILE_TEA;
    public static FluidWrapper DANDELION_TEA;
    public static FluidWrapper LABRADOR_TEA;

    // Coffee & Coke
    public static FluidWrapper COFFEE;
    public static FluidWrapper FIRMA_COLA; //Obviously a reference to Coca Cola.

    // Fermented Alcohols
    public static FluidWrapper AGAVE_WINE;
    public static FluidWrapper BARLEY_WINE;
    public static FluidWrapper BANANA_WINE;
    public static FluidWrapper BERRY_WINE;
    public static FluidWrapper CHERRY_WINE;
    public static FluidWrapper JUNIPER_WINE;
    public static FluidWrapper LEMON_WINE;
    public static FluidWrapper ORANGE_WINE;
    public static FluidWrapper PAPAYA_WINE;
    public static FluidWrapper PEACH_WINE;
    public static FluidWrapper PEAR_WINE;
    public static FluidWrapper PLUM_WINE;
    public static FluidWrapper MEAD;
    public static FluidWrapper RED_WINE;
    public static FluidWrapper WHEAT_WINE;
    public static FluidWrapper WHITE_WINE;

    // Alcohols
    public static FluidWrapper CALVADOS;
    public static FluidWrapper GIN;
    public static FluidWrapper TEQUILA;
    public static FluidWrapper SHOCHU;
    public static FluidWrapper BANANA_BRANDY;
    public static FluidWrapper CHERRY_BRANDY;
    public static FluidWrapper LEMON_BRANDY;
    public static FluidWrapper ORANGE_BRANDY;
    public static FluidWrapper PAPAYA_BRANDY;
    public static FluidWrapper PEACH_BRANDY;
    public static FluidWrapper PEAR_BRANDY;
    public static FluidWrapper PLUM_BRANDY;
    public static FluidWrapper BERRY_BRANDY;
    public static FluidWrapper BRANDY;
    public static FluidWrapper COGNAC;
    public static FluidWrapper GRAPPA;

    // Beer
    public static FluidWrapper BEER_BARLEY;
    public static FluidWrapper BEER_CORN;
    public static FluidWrapper BEER_RYE;
    public static FluidWrapper BEER_WHEAT;
    public static FluidWrapper BEER_AMARANTH;
    public static FluidWrapper BEER_BUCKWHEAT;
    public static FluidWrapper BEER_FONIO;
    public static FluidWrapper BEER_MILLET;
    public static FluidWrapper BEER_QUINOA;
    public static FluidWrapper BEER_SPELT;

    // Misc
    public static FluidWrapper SUGAR_WATER;
    public static FluidWrapper HONEY_WATER;
    public static FluidWrapper RICE_WATER;
    public static FluidWrapper SOYBEAN_WATER;
    public static FluidWrapper LINSEED_WATER;
    public static FluidWrapper RAPE_SEED_WATER;
    public static FluidWrapper SUNFLOWER_SEED_WATER;
    public static FluidWrapper OPIUM_POPPY_SEED_WATER;
    public static FluidWrapper SUGAR_BEET_WATER;
    public static FluidWrapper SUGAR_CANE_WATER;
    public static FluidWrapper SOY_MILK;
    public static FluidWrapper LINSEED_OIL;
    public static FluidWrapper RAPE_SEED_OIL;
    public static FluidWrapper SUNFLOWER_SEED_OIL;
    public static FluidWrapper OPIUM_POPPY_SEED_OIL;
    public static FluidWrapper WORT;
    public static FluidWrapper SWEET_SAP;
    public static FluidWrapper SWEET_SYRUP;
    public static FluidWrapper RESIN;
    public static FluidWrapper KINO;
    public static FluidWrapper SALAMMONIAC;

    // Juice - Berries
    public static FluidWrapper JUICE_BLACKBERRY;
    public static FluidWrapper JUICE_BLUEBERRY;
    public static FluidWrapper JUICE_BUNCH_BERRY;
    public static FluidWrapper JUICE_CLOUD_BERRY;
    public static FluidWrapper JUICE_CRANBERRY;
    public static FluidWrapper JUICE_ELDERBERRY;
    public static FluidWrapper JUICE_GOOSEBERRY;
    public static FluidWrapper JUICE_RASPBERRY;
    public static FluidWrapper JUICE_SNOW_BERRY;
    public static FluidWrapper JUICE_STRAWBERRY;
    public static FluidWrapper JUICE_WINTERGREEN_BERRY;

    // Juice - Fruits
    public static FluidWrapper JUICE_AGAVE;
    public static FluidWrapper JUICE_APPLE;
    public static FluidWrapper JUICE_BANANA;
    public static FluidWrapper JUICE_CHERRY;
    public static FluidWrapper JUICE_GREEN_GRAPE;
    public static FluidWrapper JUICE_JUNIPER;
    public static FluidWrapper JUICE_LEMON;
    public static FluidWrapper JUICE_ORANGE;
    public static FluidWrapper JUICE_PAPAYA;
    public static FluidWrapper JUICE_PEACH;
    public static FluidWrapper JUICE_PEAR;
    public static FluidWrapper JUICE_PLUM;
    public static FluidWrapper JUICE_PURPLE_GRAPE;
    public static FluidWrapper JUICE_BARREL_CACTUS;

    private static ImmutableSet<FluidWrapper> allFiniteFluids;
    private static ImmutableSet<FluidWrapper> allFermentedAlcoholsFluids;
    private static ImmutableSet<FluidWrapper> allAlcoholsFluids;
    private static ImmutableSet<FluidWrapper> allBeerFluids;
    private static ImmutableSet<FluidWrapper> allTeaFluids;
    private static ImmutableSet<FluidWrapper> allCoffeeFluids;
    private static ImmutableSet<FluidWrapper> allJuiceBerryFluids;
    private static ImmutableSet<FluidWrapper> allJuiceFruitFluids;
    private static ImmutableSet<FluidWrapper> allMiscFluids;

    public static ImmutableSet<FluidWrapper> getAllFiniteFluids()
    {
        return allFiniteFluids;
    }

    public static ImmutableSet<FluidWrapper> getAllFermentedAlcoholsFluids()
    {
        return allFermentedAlcoholsFluids;
    }

    public static ImmutableSet<FluidWrapper> getAllAlcoholsFluids()
    {
        return allAlcoholsFluids;
    }

    public static ImmutableSet<FluidWrapper> getAllBeerFluids()
    {
        return allBeerFluids;
    }

    public static ImmutableSet<FluidWrapper> getAllTeaFluids()
    {
        return allTeaFluids;
    }

    public static ImmutableSet<FluidWrapper> getAllCoffeeFluids()
    {
        return allCoffeeFluids;
    }

    public static ImmutableSet<FluidWrapper> getAllJuiceBerryFluids()
    {
        return allJuiceBerryFluids;
    }

    public static ImmutableSet<FluidWrapper> getAllJuiceFruitFluids()
    {
        return allJuiceFruitFluids;
    }

    public static ImmutableSet<FluidWrapper> getAllMiscFluids()
    {
        return allMiscFluids;
    }

    public static void registerFluids()
    {
        DrinkableProperty milkProperty = player -> {
            if (player.getFoodStats() instanceof IFoodStatsTFC)
            {
                IFoodStatsTFC foodStats = (IFoodStatsTFC) player.getFoodStats();
                foodStats.addThirst(10);
                foodStats.getNutrition().addBuff(FoodData.MILK);
            }
        };

        DISTILLED_WATER = registerFluid(new Fluid("distilled_water", STILL, FLOW, 0xFF1F32DA)).with(DrinkableProperty.DRINKABLE, player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                ((FoodStatsTFC) player.getFoodStats()).addThirst(20);
            }
        });
        WASTE = registerFluid(new Fluid("waste", STILL, FLOW, 0xFF858678)).with(DrinkableProperty.DRINKABLE, player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                ((FoodStatsTFC) player.getFoodStats()).addThirst(-20);
                if (Constants.RNG.nextFloat() < 0.25f)
                {
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 400, 1));
                    player.addPotionEffect(new PotionEffect(MobEffects.POISON, 400, 1));
                }
            }
        });
        BASE_POTASH_LIQUOR = registerFluid(new Fluid("base_potash_liquor", STILL, FLOW, 0xFF86888B)).with(DrinkableProperty.DRINKABLE, player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                ((FoodStatsTFC) player.getFoodStats()).addThirst(-20);
                if (Constants.RNG.nextFloat() < 0.25f)
                {
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 400, 1));
                    player.addPotionEffect(new PotionEffect(MobEffects.POISON, 400, 1));
                }
            }
        });
        SWEET_SAP = registerFluid(new Fluid("sweet_sap", STILL, FLOW, 0xFFFBC754)).with(DrinkableProperty.DRINKABLE, player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                ((FoodStatsTFC) player.getFoodStats()).addThirst(10);
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 300, 1));
            }
        });
        SWEET_SYRUP = registerFluid(new Fluid("sweet_syrup", STILL, FLOW, 0xFFE84E00)).with(DrinkableProperty.DRINKABLE, player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 800, 2));
            }
        });
        RESIN = registerFluid(new Fluid("resin", STILL, FLOW, 0xFFFABF0B));
        KINO = registerFluid(new Fluid("kino", STILL, FLOW, 0xFFA00814));
        SALAMMONIAC = registerFluid(new Fluid("salammoniac", STILL, FLOW, 0xFFF4F4F4));

        DrinkableProperty teaProperty = player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                ((FoodStatsTFC) player.getFoodStats()).addThirst(20);
                if (Constants.RNG.nextFloat() < 0.25f)
                {
                    player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1200, 1));
                }
            }
        };
        allTeaFluids = ImmutableSet.<FluidWrapper>builder()
                .add(
                	WHITE_TEA = registerFluid(new Fluid("white_tea", STILL, FLOW, 0xFFC48E69).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, teaProperty),
                	GREEN_TEA = registerFluid(new Fluid("green_tea", STILL, FLOW, 0xFFD9D08F).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, teaProperty),
                	BLACK_TEA = registerFluid(new Fluid("black_tea", STILL, FLOW, 0xFF923C01).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, teaProperty),
                	CHAMOMILE_TEA = registerFluid(new Fluid("chamomile_tea", STILL, FLOW, 0xFFFFE089).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, teaProperty),
                	DANDELION_TEA = registerFluid(new Fluid("dandelion_tea", STILL, FLOW, 0xFFE3BA66).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, teaProperty),
                	LABRADOR_TEA = registerFluid(new Fluid("labrador_tea", STILL, FLOW, 0xFFF6E469).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, teaProperty)
                )
                .build();  

        DrinkableProperty coffeeProperty = player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                ((FoodStatsTFC) player.getFoodStats()).addThirst(20);
                if (Constants.RNG.nextFloat() < 0.25f)
                {
                    player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 1));
                }
            }
        };

        allCoffeeFluids = ImmutableSet.<FluidWrapper>builder()
            .add(
                COFFEE = registerFluid(new Fluid("coffee", STILL, FLOW, 0xFF6F4E37).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, coffeeProperty),
            	FIRMA_COLA = registerFluid(new Fluid("firma_cola", STILL, FLOW, 0xFF521810).setRarity(EnumRarity.RARE)).with(DrinkableProperty.DRINKABLE, coffeeProperty)
            )
            .build();        

        DrinkableProperty miscFluidsProperty = player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                ((FoodStatsTFC) player.getFoodStats()).addThirst(15);
            }
        };
        allMiscFluids = ImmutableSet.<FluidWrapper>builder()
            .add(
            	SUGAR_WATER = registerFluid(new Fluid("sugar_water", STILL, FLOW, 0xFFEEEFDF).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
                HONEY_WATER = registerFluid(new Fluid("honey_water", STILL, FLOW, 0xFFFBE2A1).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
            	RICE_WATER = registerFluid(new Fluid("rice_water", STILL, FLOW, 0xFFEFE0CB).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
                SOYBEAN_WATER = registerFluid(new Fluid("soybean_water", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
                LINSEED_WATER = registerFluid(new Fluid("linseed_water", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
                RAPE_SEED_WATER = registerFluid(new Fluid("rape_seed_water", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
                SUNFLOWER_SEED_WATER = registerFluid(new Fluid("sunflower_seed_water", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
                OPIUM_POPPY_SEED_WATER = registerFluid(new Fluid("opium_poppy_seed_water", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
                SUGAR_BEET_WATER = registerFluid(new Fluid("sugar_beet_water", STILL, FLOW, 0xFFEEEFDF).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
                SUGAR_CANE_WATER = registerFluid(new Fluid("sugar_cane_water", STILL, FLOW, 0xFFEEEFDF).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
                SOY_MILK = registerFluid(new Fluid("soy_milk", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
                LINSEED_OIL = registerFluid(new Fluid("linseed_oil", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
                RAPE_SEED_OIL = registerFluid(new Fluid("rape_seed_oil", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
                SUNFLOWER_SEED_OIL = registerFluid(new Fluid("sunflower_seed_oil", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
                OPIUM_POPPY_SEED_OIL = registerFluid(new Fluid("opium_poppy_seed_oil", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty),
            	WORT = registerFluid(new Fluid("wort", STILL, FLOW, 0xFF654321).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, miscFluidsProperty)
            )
            .build();     

        DrinkableProperty fermentedAlcoholProperty = player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                ((FoodStatsTFC) player.getFoodStats()).addThirst(10);
                if (Constants.RNG.nextFloat() < 0.25f)
                {
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 400, 1));
                }
            }
        };
        allFermentedAlcoholsFluids = ImmutableSet.<FluidWrapper>builder()
            .add(
            	AGAVE_WINE = registerFluid(new Fluid("agave_wine", STILL, FLOW, 0xFFDBB35A).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
            	BARLEY_WINE = registerFluid(new Fluid("barley_wine", STILL, FLOW, 0xFF851401).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                BANANA_WINE = registerFluid(new Fluid("banana_wine", STILL, FLOW, 0xFFB76A02).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                BERRY_WINE = registerFluid(new Fluid("berry_wine", STILL, FLOW, 0xFFE13F43).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                CHERRY_WINE = registerFluid(new Fluid("cherry_wine", STILL, FLOW, 0xFF790604).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                JUNIPER_WINE = registerFluid(new Fluid("juniper_wine", STILL, FLOW, 0xFF863136).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                LEMON_WINE = registerFluid(new Fluid("lemon_wine", STILL, FLOW, 0xFFF7EAB7).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                MEAD = registerFluid(new Fluid("mead", STILL, FLOW, 0xFFE1701B).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
            	ORANGE_WINE = registerFluid(new Fluid("orange_wine", STILL, FLOW, 0xFFC56707).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
            	PAPAYA_WINE = registerFluid(new Fluid("papaya_wine", STILL, FLOW, 0xFFB37E2C).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
            	PEACH_WINE = registerFluid(new Fluid("peach_wine", STILL, FLOW, 0xFFD19088).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
            	PEAR_WINE = registerFluid(new Fluid("pear_Wine", STILL, FLOW, 0xFFF2E4BD).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
            	PLUM_WINE = registerFluid(new Fluid("plum_wine", STILL, FLOW, 0xFF870910).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                RED_WINE = registerFluid(new Fluid("red_wine", STILL, FLOW, 0xFF5E1224).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                WHEAT_WINE = registerFluid(new Fluid("wheat_wine", STILL, FLOW, 0xFF7C1B18).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                WHITE_WINE = registerFluid(new Fluid("white_wine", STILL, FLOW, 0xFFFCF1D2).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, fermentedAlcoholProperty)
            )
            .build();

        DrinkableProperty alcoholProperty = player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                ((FoodStatsTFC) player.getFoodStats()).addThirst(10);
                if (Constants.RNG.nextFloat() < 0.25f)
                {
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1200, 2));
                }
            }
        };
        allAlcoholsFluids = ImmutableSet.<FluidWrapper>builder()
            .add(
            	CALVADOS = registerFluid(new Fluid("calvados", STILL, FLOW, 0xFFBE2D02).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
                BANANA_BRANDY = registerFluid(new Fluid("banana_brandy", STILL, FLOW, 0xFFD49B36).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
                BERRY_BRANDY = registerFluid(new Fluid("berry_brandy", STILL, FLOW, 0xFF85413A).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
                BRANDY = registerFluid(new Fluid("brandy", STILL, FLOW, 0xFF87413F).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
                COGNAC = registerFluid(new Fluid("cognac", STILL, FLOW, 0xFFA3481B).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
            	GIN = registerFluid(new Fluid("gin", STILL, FLOW, 0xFFDAE2C8).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
                CHERRY_BRANDY = registerFluid(new Fluid("cherry_brandy", STILL, FLOW, 0xFFAD495D).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
            	LEMON_BRANDY = registerFluid(new Fluid("lemon_brandy", STILL, FLOW, 0xFFC89C4E).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
            	ORANGE_BRANDY = registerFluid(new Fluid("orange_brandy", STILL, FLOW, 0xFFCF7F26).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
            	PAPAYA_BRANDY = registerFluid(new Fluid("papaya_brandy", STILL, FLOW, 0xFFDF9724).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
            	PEACH_BRANDY = registerFluid(new Fluid("peach_brandy", STILL, FLOW, 0xFFCA8550).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
            	PEAR_BRANDY = registerFluid(new Fluid("pear_brandy", STILL, FLOW, 0xFFCC9A48).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
            	PLUM_BRANDY = registerFluid(new Fluid("plum_brandy", STILL, FLOW, 0xFF941254).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
                SHOCHU = registerFluid(new Fluid("shochu", STILL, FLOW, 0xFFF8F9F9).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
            	TEQUILA = registerFluid(new Fluid("tequila", STILL, FLOW, 0xFFF7D0A1).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
            	GRAPPA = registerFluid(new Fluid("grappa", STILL, FLOW, 0xFFF7D0A1).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty)
            )
            .build();

        DrinkableProperty alcoholBeer = player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                ((FoodStatsTFC) player.getFoodStats()).addThirst(10);
                if (Constants.RNG.nextFloat() < 0.25f)
                {
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 1));
                }
            }
        };
        allBeerFluids = ImmutableSet.<FluidWrapper>builder()
            .add(
            	BEER_BARLEY = registerFluid(new Fluid("beer_barley", STILL, FLOW, 0xFFF6B848).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholBeer),
            	BEER_CORN = registerFluid(new Fluid("beer_corn", STILL, FLOW, 0xFFE9BA40).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholBeer),
            	BEER_RYE = registerFluid(new Fluid("beer_rye", STILL, FLOW, 0xFF8B1A02).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholBeer),
            	BEER_WHEAT = registerFluid(new Fluid("beer_wheat", STILL, FLOW, 0xFFE3A926).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholBeer),
            	BEER_AMARANTH = registerFluid(new Fluid("beer_amaranth", STILL, FLOW, 0xFF51150B).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholBeer),
            	BEER_BUCKWHEAT = registerFluid(new Fluid("beer_buckwheat", STILL, FLOW, 0xFFB55506).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholBeer),
            	BEER_FONIO = registerFluid(new Fluid("beer_fonio", STILL, FLOW, 0xFFC28A10).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholBeer),
            	BEER_MILLET = registerFluid(new Fluid("beer_millet", STILL, FLOW, 0xFF982801).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholBeer),
            	BEER_QUINOA = registerFluid(new Fluid("beer_quinoa", STILL, FLOW, 0xFFB46419).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholBeer),
            	BEER_SPELT = registerFluid(new Fluid("beer_spelt", STILL, FLOW, 0xFFC87828).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholBeer)
            )
            .build();    

        DrinkableProperty juiceBerryProperty = player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                ((FoodStatsTFC) player.getFoodStats()).addThirst(15);
                if (Constants.RNG.nextFloat() < 0.25f)
                {
                    player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 300, 1));
                }
            }
        };
        allJuiceBerryFluids = ImmutableSet.<FluidWrapper>builder()
            .add(
            	JUICE_BLACKBERRY = registerFluid(new Fluid("juice_blackberry", STILL, FLOW, 0xFF32001B).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceBerryProperty),
            	JUICE_BLUEBERRY = registerFluid(new Fluid("juice_blueberry", STILL, FLOW, 0xFF70324E).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceBerryProperty),
            	JUICE_BUNCH_BERRY = registerFluid(new Fluid("juice_bunch_berry", STILL, FLOW, 0xFFC04C62).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceBerryProperty),
            	JUICE_CLOUD_BERRY = registerFluid(new Fluid("juice_cloud_berry", STILL, FLOW, 0xFFB6B3C0).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceBerryProperty),
            	JUICE_CRANBERRY = registerFluid(new Fluid("juice_cranberry", STILL, FLOW, 0xFFCB4C78).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceBerryProperty),
            	JUICE_ELDERBERRY = registerFluid(new Fluid("juice_elderberry", STILL, FLOW, 0xFF17182B).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceBerryProperty),
            	JUICE_GOOSEBERRY = registerFluid(new Fluid("juice_gooseberry", STILL, FLOW, 0xFFBEC1A4).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceBerryProperty),
            	JUICE_RASPBERRY = registerFluid(new Fluid("juice_raspberry", STILL, FLOW, 0xFFE30B5D).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceBerryProperty),
            	JUICE_SNOW_BERRY = registerFluid(new Fluid("juice_snow_berry", STILL, FLOW, 0xFFF6F9E1).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceBerryProperty),
            	JUICE_STRAWBERRY = registerFluid(new Fluid("juice_strawberry", STILL, FLOW, 0xFFC83F49).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceBerryProperty),
            	JUICE_WINTERGREEN_BERRY = registerFluid(new Fluid("juice_wintergreen_berry", STILL, FLOW, 0xFFEA3441).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceBerryProperty)
            )
            .build();   

        DrinkableProperty juiceFruitProperty = player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                ((FoodStatsTFC) player.getFoodStats()).addThirst(15);
                if (Constants.RNG.nextFloat() < 0.25f)
                {
                    player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 300, 1));
                }
            }
        };
        allJuiceFruitFluids = ImmutableSet.<FluidWrapper>builder()
            .add(
            	JUICE_AGAVE = registerFluid(new Fluid("juice_agave", STILL, FLOW, 0xFFCCD8D4).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceFruitProperty),
            	JUICE_APPLE = registerFluid(new Fluid("juice_apple", STILL, FLOW, 0xFFFEB500).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceFruitProperty),
            	JUICE_BANANA = registerFluid(new Fluid("juice_banana", STILL, FLOW, 0xFFFDE39F).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceFruitProperty),
                JUICE_CHERRY = registerFluid(new Fluid("juice_cherry", STILL, FLOW, 0xFF5E1224).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceFruitProperty),
            	JUICE_GREEN_GRAPE = registerFluid(new Fluid("juice_green_grape", STILL, FLOW, 0xFFCCE2A0).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceFruitProperty),
            	JUICE_JUNIPER = registerFluid(new Fluid("juice_juniper", STILL, FLOW, 0xFFBDC975).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceFruitProperty),
            	JUICE_LEMON = registerFluid(new Fluid("juice_lemon", STILL, FLOW, 0xFFFCFAC9).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceFruitProperty),
            	JUICE_ORANGE = registerFluid(new Fluid("juice_orange", STILL, FLOW, 0xFFFCA43C).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceFruitProperty),
            	JUICE_PAPAYA = registerFluid(new Fluid("juice_papaya", STILL, FLOW, 0xFFFCA018).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceFruitProperty),
                JUICE_PEACH = registerFluid(new Fluid("juice_peach", STILL, FLOW, 0xFFF7C0A2).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceFruitProperty),
               	JUICE_PEAR = registerFluid(new Fluid("juice_pear", STILL, FLOW, 0xFFE9DF96).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceFruitProperty),
                JUICE_PLUM = registerFluid(new Fluid("juice_plum", STILL, FLOW, 0xFF885375).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceFruitProperty),
            	JUICE_PURPLE_GRAPE = registerFluid(new Fluid("juice_purple_grape", STILL, FLOW, 0xFF63344B).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceFruitProperty),
                JUICE_BARREL_CACTUS = registerFluid(new Fluid("juice_barrel_cactus", STILL, FLOW, 0xFFBBE16A).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, juiceFruitProperty)
            )
            .build();
    }

    private static FluidWrapper registerFluid(@Nonnull Fluid newFluid)
    {
        /*boolean isDefault = !FluidRegistry.isFluidRegistered(newFluid.getName());

        if (!isDefault)
        {
            // Fluid was already registered with this name, default to that fluid
            newFluid = FluidRegistry.getFluid(newFluid.getName());
        }
        else
        {
            // No fluid found, we are safe to register our default
            FluidRegistry.registerFluid(newFluid);
        }
        FluidRegistry.addBucketForFluid(newFluid);
        FluidWrapper properties = new FluidWrapper(newFluid, isDefault);
        WRAPPERS.put(newFluid, properties);
        return properties;*/

    	boolean isDefault = FluidRegistry.registerFluid(newFluid);
        if (!isDefault)
        {
            newFluid = FluidRegistry.getFluid(newFluid.getName());
        }
        else
        {
            // No fluid found, we are safe to register our default
            FluidRegistry.registerFluid(newFluid);
        }
        FluidRegistry.addBucketForFluid(newFluid);
        FluidWrapper properties = FluidsTFC.getWrapper(newFluid);
        WRAPPERS.put(newFluid, properties);
        return properties;
    }
}