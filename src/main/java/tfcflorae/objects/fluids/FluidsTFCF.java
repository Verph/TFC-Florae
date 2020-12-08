package tfcflorae.objects.fluids;

import java.awt.*;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.EnumRarity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.api.capability.food.FoodStatsTFC;
import net.dries007.tfc.api.capability.food.IFoodStatsTFC;
import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.player.IPlayerData;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.properties.DrinkableProperty;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.fluids.properties.MetalProperty;
import net.dries007.tfc.objects.potioneffects.PotionEffectsTFC;
import net.dries007.tfc.util.calendar.ICalendar;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.objects.fluids.FluidsTFC;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

//duplicating the fluid registration logic from TFC as not to create conflicts
public final class FluidsTFCF
{
    private static final ResourceLocation STILL = new ResourceLocation(TerraFirmaCraft.MOD_ID, "blocks/fluid_still");
    private static final ResourceLocation FLOW = new ResourceLocation(TerraFirmaCraft.MOD_ID, "blocks/fluid_flow");

    private static final HashBiMap<Fluid, FluidWrapper> WRAPPERS = HashBiMap.create();

    public static FluidWrapper COCONUT_MILK;

    private static ImmutableSet<FluidWrapper> allFiniteFluids;

    public static ImmutableSet<FluidWrapper> getAllFiniteFluids()
    {
        return allFiniteFluids;
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

        allFiniteFluids = ImmutableSet.<FluidWrapper>builder().add(
            COCONUT_MILK = registerFluid(new Fluid("coconut_milk", STILL, FLOW, 0xFFfcfae2)).with(DrinkableProperty.DRINKABLE, milkProperty)
        ).build();
    }

    private static FluidWrapper registerFluid(@Nonnull Fluid newFluid)
    {
        boolean isDefault = !FluidRegistry.isFluidRegistered(newFluid.getName());

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
        return properties;
    }
}