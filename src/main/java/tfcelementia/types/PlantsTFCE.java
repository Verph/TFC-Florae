package tfcelementia.types;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tfcelementia.TFCElementia;
import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Plant;

//import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

import static tfcelementia.TFCElementia.MODID;

@SuppressWarnings("WeakerAccess")
@Mod.EventBusSubscriber(modid = TFCElementia.MODID)
public final class PlantsTFCE
{
    /**
     * Default Plant ResourceLocations
     */
    public static final ResourceLocation CATTAIL = new ResourceLocation(MODID, "cattail");
    public static final ResourceLocation CHAMOMILE = new ResourceLocation(MODID, "chamomile");
    public static final ResourceLocation LAVANDULA = new ResourceLocation(MODID, "lavandula");
    public static final ResourceLocation LILY_OF_THE_VALLEY = new ResourceLocation(MODID, "lily_of_the_valley");
    //public static final ResourceLocation PEYOTE = new ResourceLocation(MODID, "peyote");
    public static final ResourceLocation PEONY = new ResourceLocation(MODID, "peony");
    /*
    public static final ResourceLocation ORANGE_PEONY = new ResourceLocation(MODID, "orange_peony");
    public static final ResourceLocation PINK_PEONY = new ResourceLocation(MODID, "pink_peony");
    public static final ResourceLocation PURPLE_PEONY = new ResourceLocation(MODID, "purple_peony");
    public static final ResourceLocation RED_PEONY = new ResourceLocation(MODID, "red_peony");
    public static final ResourceLocation WHITE_PEONY = new ResourceLocation(MODID, "white_peony");
    */
    public static final ResourceLocation HYDRANGEA = new ResourceLocation(MODID, "hydrangea");
    /*
    public static final ResourceLocation BLUE_HYDRANGEA = new ResourceLocation(MODID, "blue_hydrangea");
    public static final ResourceLocation PINK_HYDRANGEA = new ResourceLocation(MODID, "pink_hydrangea");
    public static final ResourceLocation PURPLE_HYDRANGEA = new ResourceLocation(MODID, "purple_hydrangea");
    public static final ResourceLocation RED_HYDRANGEA = new ResourceLocation(MODID, "red_hydrangea");
    public static final ResourceLocation WHITE_HYDRANGEA = new ResourceLocation(MODID, "white_hydrangea");
    */
    public static final ResourceLocation LILAC = new ResourceLocation(MODID, "lilac");
    public static final ResourceLocation SUNFLOWER = new ResourceLocation(MODID, "sunflower");
    //public static final ResourceLocation DRY_SHRUB = new ResourceLocation(MODID, "dry_shrub");
    //public static final ResourceLocation LUSH_SHRUB = new ResourceLocation(MODID, "lush_shrub");
    //public static final ResourceLocation TEMPERATE_SHRUB = new ResourceLocation(MODID, "temperate_shrub");
    public static final ResourceLocation CHAPARRAL_SHRUB = new ResourceLocation(MODID, "chaparral_shrub");
    public static final ResourceLocation SHRUB = new ResourceLocation(MODID, "shrub");
    public static final ResourceLocation LEAF_LITTER = new ResourceLocation(MODID, "leaf_litter");
    public static final ResourceLocation LOW_UNDERGROWTH = new ResourceLocation(MODID, "undergrowth_low");
    public static final ResourceLocation TROPICAL_UNDERGROWTH = new ResourceLocation(MODID, "undergrowth_tropical");
    
    //Bamboo
    public static final ResourceLocation ARROW_BAMBOO = new ResourceLocation(MODID, "arrow_bamboo");
    public static final ResourceLocation BLACK_BAMBOO = new ResourceLocation(MODID, "black_bamboo");
    public static final ResourceLocation BLUE_BAMBOO = new ResourceLocation(MODID, "blue_bamboo");
    public static final ResourceLocation DRAGON_BAMBOO = new ResourceLocation(MODID, "dragon_bamboo");
    public static final ResourceLocation GOLDEN_BAMBOO = new ResourceLocation(MODID, "golden_bamboo");
    public static final ResourceLocation NARROW_LEAF_BAMBOO = new ResourceLocation(MODID, "narrow_leaf_bamboo");
    public static final ResourceLocation RED_BAMBOO = new ResourceLocation(MODID, "red_bamboo");
    public static final ResourceLocation TEMPLE_BAMBOO = new ResourceLocation(MODID, "temple_bamboo");
    public static final ResourceLocation THORNY_BAMBOO = new ResourceLocation(MODID, "thorny_bamboo");
    public static final ResourceLocation TIMBER_BAMBOO = new ResourceLocation(MODID, "timber_bamboo");
    public static final ResourceLocation TINWA_BAMBOO = new ResourceLocation(MODID, "tinwa_bamboo");
    public static final ResourceLocation WEAVERS_BAMBOO = new ResourceLocation(MODID, "weavers_bamboo");

    @SubscribeEvent
    public static void onPreRegisterPlant(TFCRegistryEvent.RegisterPreBlock<Plant> event)
    {
        event.getRegistry().registerAll(
            //new Plant(PEYOTE, Plant.PlantType.CACTUS, new int[] {0, 0, 0, 0, 1, 2, 2, 2, 2, 3, 3, 0}, false, false, 18f, 40f, -6f, 50f, 0f, 75f, 12, 15, 3, 0D, "peyote"),
            new Plant(CATTAIL, Plant.PlantType.FLOATING, new int[] {4, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 0}, false, false, 0f, 30f, -12f, 36f, 100f, 500f, 9, 15, 1, 1, 1, 0.8D, "cattail"),
            new Plant(CHAMOMILE, Plant.PlantType.STANDARD, new int[] {6, 6, 7, 0, 1, 1, 2, 2, 3, 4, 5, 6}, false, false, 5f, 25f, -40f, 40f, 75f, 400f, 10, 15, 1, 0.9D, "chamomile"),
            new Plant(LAVANDULA, Plant.PlantType.STANDARD, new int[] {5, 5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4}, false, false, 5f, 35f, -29f, 40f, 0f, 300f, 9, 15, 1, 0.8D, "lavender"),
            new Plant(LILY_OF_THE_VALLEY, Plant.PlantType.STANDARD, new int[] {5, 5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4}, false, false, 5f, 35f, -29f, 40f, 0f, 300f, 9, 15, 1, 0.8D, "lily_of_the_valley"),
            new Plant(PEONY, Plant.PlantType.TALL_PLANT, new int[] {5, 5, 0, 0, 1, 1, 2, 2, 3, 3, 3, 4}, false, false, 11f, 21f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, "peony"),
            /*
            new Plant(ORANGE_PEONY, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 21f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(PINK_PEONY, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(PURPLE_PEONY, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(RED_PEONY, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            */
            new Plant(HYDRANGEA, Plant.PlantType.TALL_PLANT, new int[] {5, 5, 0, 0, 1, 1, 2, 2, 3, 3, 3, 4}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, "hydrangea"),
            /*
            new Plant(WHITE_PEONY, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(BLUE_HYDRANGEA, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(PINK_HYDRANGEA, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(PURPLE_HYDRANGEA, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(RED_HYDRANGEA, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null),
            new Plant(WHITE_HYDRANGEA, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, null)
            */
            new Plant(LILAC, Plant.PlantType.TALL_PLANT, new int[] {5, 5, 0, 0, 1, 1, 2, 2, 3, 3, 3, 4}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 9, 15, 2, 0.9D, "lilac"),
            new Plant(SUNFLOWER, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9}, false, false, 8f, 31f, -29f, 34f, 100f, 300f, 9, 15, 2, 0.9D, "sunflower"),
            //new Plant(DRY_SHRUB, Plant.PlantType.SHORT_GRASS, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, -10f, 40f, 0f, 50f, 70f, 500f, 12, 15, 1, 0.1D, "shrub"),
            //new Plant(LUSH_SHRUB, Plant.PlantType.SHORT_GRASS, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, -10f, 40f, 0f, 50f, 70f, 500f, 12, 15, 1, 0.1D, "shrub"),
            //new Plant(TEMPERATE_SHRUB, Plant.PlantType.SHORT_GRASS, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, -10f, 40f, -20f, 50f, 70f, 500f, 12, 15, 1, 0.1D, "shrub"),
            new Plant(CHAPARRAL_SHRUB, Plant.PlantType.SHORT_GRASS, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 10f, 40f, 0f, 50f, 100f, 250f, 12, 15, 1, 2.2D, "shrub"),
            new Plant(SHRUB, Plant.PlantType.TALL_GRASS, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, -10f, 40f, 0f, 50f, 70f, 500f, 12, 15, 1, 2.2D, "shrub"),
            new Plant(LEAF_LITTER, Plant.PlantType.SHORT_GRASS, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -20f, 50f, -30f, 50f, 70f, 500f, 6, 14, 1, 0.9D, "litter_leaf"),
            new Plant(LOW_UNDERGROWTH, Plant.PlantType.TALL_GRASS, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 1, 0.9D, "undergrowth_low"),
            //new Plant(TROPICAL_UNDERGROWTH, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 4, 0.7D, "undergrowth_tropical"),
            
            //Bamboo
            new Plant(ARROW_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 260f, 500f, 9, 15, 12, 0D, "arrow_bamboo"),
            new Plant(BLACK_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 280f, 500f, 9, 15, 15, 0D, "black_bamboo"),
            new Plant(BLUE_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 230f, 500f, 9, 15, 12, 0D, "blue_bamboo"),
            new Plant(DRAGON_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 21, 0D, "dragon_bamboo"),
            new Plant(GOLDEN_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 13, 0D, "golden_bamboo"),
            new Plant(NARROW_LEAF_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 20, 0D, "narrow_leaf_bamboo"),
            new Plant(RED_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 18, 0D, "red_bamboo"),
            new Plant(TEMPLE_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 14, 0D, "temple_bamboo"),
            new Plant(THORNY_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 12, 0D, "thorny_bamboo"),
            new Plant(TIMBER_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 13, 0D, "timber_bamboo"),
            new Plant(TINWA_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 18, 0D, "tinwa_bamboo"),
            new Plant(WEAVERS_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 13, 0D, "weavers_bamboo")
            
        );
    }
}