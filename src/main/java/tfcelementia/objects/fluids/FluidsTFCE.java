package tfcelementia.objects.fluids;

import javax.annotation.Nonnull;

import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import net.dries007.tfc.Constants;
import net.dries007.tfc.api.capability.food.FoodStatsTFC;
import net.dries007.tfc.api.capability.food.FoodTrait;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.properties.DrinkableProperty;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.fluids.properties.MetalProperty;
import net.dries007.tfc.objects.fluids.properties.PreservingProperty;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.inventory.ingredient.IngredientItemFoodTrait;

import tfcelementia.util.OreDictionaryHelper;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public final class FluidsTFCE
{
    private static final ResourceLocation STILL = new ResourceLocation(MOD_ID, "blocks/fluid_still");
    private static final ResourceLocation FLOW = new ResourceLocation(MOD_ID, "blocks/fluid_flow");
    private static final HashBiMap<Fluid, FluidWrapper> WRAPPERS = HashBiMap.create();

    // Water variants
    public static FluidWrapper DISTILLED_WATER;
    
    // Tea
    public static FluidWrapper WHITE_TEA;
    public static FluidWrapper GREEN_TEA;
    public static FluidWrapper BLACK_TEA;
    public static FluidWrapper CHAMOMILE_TEA;
    public static FluidWrapper DANDELION_TEA;
    
    // Coffee
    public static FluidWrapper COFFEE;
    
    
    // Alcohols
    public static FluidWrapper GIN;
    public static FluidWrapper TEQUILA;
    public static FluidWrapper RED_WINE;
    public static FluidWrapper WHITE_WINE;

    private static ImmutableSet<FluidWrapper> allAlcoholsFluids;
    private static ImmutableSet<FluidWrapper> allTeaFluids;
    private static ImmutableSet<FluidWrapper> allCoffeeFluids;

    public static ImmutableSet<FluidWrapper> getAllAlcoholsFluids()
    {
        return allAlcoholsFluids;
    }

    public static ImmutableSet<FluidWrapper> getAllTeaFluids()
    {
        return allTeaFluids;
    }

    public static ImmutableSet<FluidWrapper> getAllCoffeeFluids()
    {
        return allCoffeeFluids;
    }
    
    public static void registerFluids()
    {
        DISTILLED_WATER = registerFluid(new Fluid("distilled_water", STILL, FLOW, 0xFF1F32DA)).with(DrinkableProperty.DRINKABLE, player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                ((FoodStatsTFC) player.getFoodStats()).addThirst(20);
            }
        });

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
                	DANDELION_TEA = registerFluid(new Fluid("dandelion_tea", STILL, FLOW, 0xFFE3BA66).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, teaProperty)
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
                	COFFEE = registerFluid(new Fluid("coffee", STILL, FLOW, 0xFF6F4E37).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, coffeeProperty)
                )
                .build();   

        DrinkableProperty alcoholProperty = player -> {
            if (player.getFoodStats() instanceof FoodStatsTFC)
            {
                ((FoodStatsTFC) player.getFoodStats()).addThirst(10);
                if (Constants.RNG.nextFloat() < 0.25f)
                {
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1200, 1));
                }
            }
        };
        allAlcoholsFluids = ImmutableSet.<FluidWrapper>builder()
            .add(
            	GIN = registerFluid(new Fluid("gin", STILL, FLOW, 0xFFDCDCDC).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
            	TEQUILA = registerFluid(new Fluid("tequila", STILL, FLOW, 0xFFDCDCDC).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
            	RED_WINE = registerFluid(new Fluid("red_wine", STILL, FLOW, 0xFF5E1224).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
                WHITE_WINE = registerFluid(new Fluid("white_wine", STILL, FLOW, 0xFFFCF1D2).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty)
            )
            .build();
    }
    
    private static FluidWrapper registerFluid(@Nonnull Fluid newFluid)
    {
        boolean isDefault = FluidRegistry.registerFluid(newFluid);
        if (!isDefault)
        {
            // Fluid was already registered with this name, default to that fluid
            newFluid = FluidRegistry.getFluid(newFluid.getName());
        }
        FluidRegistry.addBucketForFluid(newFluid);
        FluidWrapper properties = new FluidWrapper(newFluid, isDefault);
        WRAPPERS.put(newFluid, properties);
        return properties;
    }
}