package tfcelementia.api.capability.food;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nullable;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.DumbStorage;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.api.capability.food.IFood;

//import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

import static tfcelementia.TFCElementia.MODID;

public class CapabilityFoodTFCE extends CapabilityFood
{
    @CapabilityInject(IFoodTFCE.class)
    public static final Capability<IFoodTFCE> CAPABILITY = Helpers.getNull();
    public static final ResourceLocation KEY = new ResourceLocation(MODID, "food");

    public static void preInit()
    {
        CapabilityManager.INSTANCE.register(IFoodTFCE.class, new DumbStorage<>(), FoodHandlerTFCE::new);
    }
}