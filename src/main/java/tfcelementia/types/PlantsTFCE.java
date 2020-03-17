package tfcelementia.types;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tfcelementia.TFCElementia;
import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Plant;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@SuppressWarnings("WeakerAccess")
@Mod.EventBusSubscriber(modid = TFCElementia.MODID)
public final class PlantsTFCE
{
    /**
     * Default Plant ResourceLocations
     */
    public static final ResourceLocation BAMBOO = new ResourceLocation(MOD_ID, "bamboo");
    public static final ResourceLocation LAVANDULA = new ResourceLocation(MOD_ID, "lavandula");
    public static final ResourceLocation ORANGE_PEONY = new ResourceLocation(MOD_ID, "orange_peony");
    public static final ResourceLocation PINK_PEONY = new ResourceLocation(MOD_ID, "pink_peony");
    public static final ResourceLocation PURPLE_PEONY = new ResourceLocation(MOD_ID, "purple_peony");
    public static final ResourceLocation RED_PEONY = new ResourceLocation(MOD_ID, "red_peony");
    public static final ResourceLocation WHITE_PEONY = new ResourceLocation(MOD_ID, "white_peony");
    public static final ResourceLocation BLUE_HYDRANGEA = new ResourceLocation(MOD_ID, "blue_hydrangea");
    public static final ResourceLocation PINK_HYDRANGEA = new ResourceLocation(MOD_ID, "pink_hydrangea");
    public static final ResourceLocation PURPLE_HYDRANGEA = new ResourceLocation(MOD_ID, "purple_hydrangea");
    public static final ResourceLocation RED_HYDRANGEA = new ResourceLocation(MOD_ID, "red_hydrangea");
    public static final ResourceLocation WHITE_HYDRANGEA = new ResourceLocation(MOD_ID, "white_hydrangea");

    @SubscribeEvent
    public static void onPreRegisterPlant(TFCRegistryEvent.RegisterPreBlock<Plant> event)
    {
        event.getRegistry().registerAll(
            new Plant(BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 20, 0D, "bamboo"),
            new Plant(LAVANDULA, Plant.PlantType.STANDARD, new int[] {5, 5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4}, false, false, 5f, 35f, -29f, 40f, 0f, 300f, 9, 15, 1, 0.8D, null),
            new Plant(ORANGE_PEONY, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, true, false, 11f, 21f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(PINK_PEONY, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, true, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(PURPLE_PEONY, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, true, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(RED_PEONY, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, true, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(WHITE_PEONY, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, true, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(BLUE_HYDRANGEA, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, true, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(PINK_HYDRANGEA, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, true, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(PURPLE_HYDRANGEA, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, true, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(RED_HYDRANGEA, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, true, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(WHITE_HYDRANGEA, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, true, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null)
        );
    }
}