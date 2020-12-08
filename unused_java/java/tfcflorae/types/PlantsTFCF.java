package tfcflorae.types;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tfcflorae.TFCFlorae;
import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Plant;

//import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

import static tfcflorae.TFCFlorae.MODID;

@SuppressWarnings("WeakerAccess")
@Mod.EventBusSubscriber(modid = TFCFlorae.MODID)
public final class PlantsTFCF
{
    /**
     * Default Plant ResourceLocations
     */
    public static final ResourceLocation AGAVE = new ResourceLocation(MODID, "agave");
    public static final ResourceLocation BADDERLOCKS = new ResourceLocation(MODID, "badderlocks");
    public static final ResourceLocation CATTAIL = new ResourceLocation(MODID, "cattail");
    public static final ResourceLocation CHAMOMILE = new ResourceLocation(MODID, "chamomile");
    public static final ResourceLocation DEVILS_TONGUE = new ResourceLocation(MODID, "devils_tongue");
    public static final ResourceLocation FLAX = new ResourceLocation(MODID, "flax");
    public static final ResourceLocation GUTWEED = new ResourceLocation(MODID, "gutweed");
    public static final ResourceLocation HYDRANGEA = new ResourceLocation(MODID, "hydrangea");
    public static final ResourceLocation INDIGO = new ResourceLocation(MODID, "indigo");
    public static final ResourceLocation LAVANDULA = new ResourceLocation(MODID, "lavandula");
    public static final ResourceLocation LEAF_LITTER = new ResourceLocation(MODID, "leaf_litter");
    public static final ResourceLocation LILAC = new ResourceLocation(MODID, "lilac");
    public static final ResourceLocation LILY_OF_THE_VALLEY = new ResourceLocation(MODID, "lily_of_the_valley");
    public static final ResourceLocation MADDER = new ResourceLocation(MODID, "madder");
    public static final ResourceLocation OCOTILLO = new ResourceLocation(MODID, "ocotillo");
    public static final ResourceLocation PAPYRUS = new ResourceLocation(MODID, "papyrus");
    public static final ResourceLocation PEONY = new ResourceLocation(MODID, "peony");
    public static final ResourceLocation RATTAN = new ResourceLocation(MODID, "rattan");
    public static final ResourceLocation SAGO = new ResourceLocation(MODID, "sago");
    public static final ResourceLocation SEAWEED = new ResourceLocation(MODID, "seaweed");
    public static final ResourceLocation SHRUB = new ResourceLocation(MODID, "shrub");
    public static final ResourceLocation CHAPARRAL_SHRUB = new ResourceLocation(MODID, "chaparral_shrub");
    public static final ResourceLocation SUGAR_CANE = new ResourceLocation(MODID, "sugar_cane");
    public static final ResourceLocation SUNFLOWER = new ResourceLocation(MODID, "sunflower");
    public static final ResourceLocation TACKWEED = new ResourceLocation(MODID, "tackweed");
    public static final ResourceLocation TAKAKIA = new ResourceLocation(MODID, "takakia");
    public static final ResourceLocation LOW_UNDERGROWTH = new ResourceLocation(MODID, "undergrowth_low");
    public static final ResourceLocation TROPICAL_UNDERGROWTH = new ResourceLocation(MODID, "undergrowth_tropical");
    public static final ResourceLocation VOODOO_LILY = new ResourceLocation(MODID, "voodoo_lily");
    public static final ResourceLocation WELD = new ResourceLocation(MODID, "weld");
    public static final ResourceLocation WOAD = new ResourceLocation(MODID, "woad");
    
    public static final ResourceLocation AMANITA = new ResourceLocation(MODID, "amanita");
    public static final ResourceLocation BLACK_POWDERPUFF = new ResourceLocation(MODID, "black_powderpuff");
    public static final ResourceLocation CHANTERELLE = new ResourceLocation(MODID, "chanterelle");
    public static final ResourceLocation DEATH_CAP = new ResourceLocation(MODID, "death_cap");
    public static final ResourceLocation GIANT_CLUB = new ResourceLocation(MODID, "giant_club");
    public static final ResourceLocation PARASOL_MUSHROOM = new ResourceLocation(MODID, "parasol_mushroom");
    public static final ResourceLocation STINKHORN = new ResourceLocation(MODID, "stinkhorn");
    public static final ResourceLocation WEEPING_MILK_CAP = new ResourceLocation(MODID, "weeping_milk_cap");
    public static final ResourceLocation WOOD_BLEWIT = new ResourceLocation(MODID, "wood_blewit");
    public static final ResourceLocation WOOLLY_GOMPHUS = new ResourceLocation(MODID, "woolly_gomphus");
    
    // Ground Cover
    public static final ResourceLocation BONES = new ResourceLocation(MODID, "bones");
    public static final ResourceLocation PINECONES = new ResourceLocation(MODID, "pinecones");
    public static final ResourceLocation ROCKS = new ResourceLocation(MODID, "rocks");
    public static final ResourceLocation TWIGS = new ResourceLocation(MODID, "twigs");
    public static final ResourceLocation SEASHELLS = new ResourceLocation(MODID, "seashells");
    
    // Bamboo
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
            new Plant(AGAVE, Plant.PlantType.STANDARD, new int[] {0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6}, false, false, 18f, 40f, 10f, 50f, 0f, 180f, 10, 15, 1, 0.8D, "agave"),
            new Plant(BADDERLOCKS, Plant.PlantType.TALL_WATER_SEA, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 12f, -20f, 16f, 0f, 500f, 0, 15, 2, 3, 14, 1.0D, "seaweed"),
            new Plant(CATTAIL, Plant.PlantType.FLOATING, new int[] {4, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 4}, false, false, 0f, 30f, -12f, 36f, 100f, 500f, 9, 15, 1, 1, 1, 0.8D, "cattail"),
            new Plant(CHAMOMILE, Plant.PlantType.STANDARD, new int[] {6, 6, 7, 0, 1, 1, 2, 2, 3, 4, 5, 6}, false, false, 5f, 25f, -40f, 40f, 75f, 400f, 10, 15, 1, 0.9D, "chamomile"),
            new Plant(DEVILS_TONGUE, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 16f, 45f, 10f, 50f, 270f, 500f, 6, 15, 2, 0.9D, "devils_tongue"),
            new Plant(FLAX, Plant.PlantType.STANDARD, new int[] {6, 0, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6}, false, false, 10f, 25f, -5f, 31f, 100f, 500f, 10, 15, 1, 0.8D, "flax"),
            new Plant(GUTWEED, Plant.PlantType.WATER_SEA, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 17f, 30f, -21f, 40f, 0f, 500f, 0, 15, 1, 1, 7, 1.0D, "seaweed"),
            new Plant(HYDRANGEA, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 10, 15, 2, 0.9D, "hydrangea"),
            new Plant(INDIGO, Plant.PlantType.STANDARD, new int[] {6, 0, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6}, false, false, 15f, 30f, 0f, 39f, 100f, 500f, 10, 15, 1, 0.8D, "indigo"),
            new Plant(LAVANDULA, Plant.PlantType.STANDARD, new int[] {5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4, 5}, false, false, 5f, 35f, -29f, 40f, 0f, 300f, 10, 15, 1, 0.8D, "lavender"),
            new Plant(LILAC, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 10, 15, 2, 0.9D, "lilac"),
            new Plant(LEAF_LITTER, Plant.PlantType.SHORT_GRASS, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -20f, 50f, -30f, 50f, 70f, 500f, 6, 14, 1, 0.9D, "leaves"),
            new Plant(LILY_OF_THE_VALLEY, Plant.PlantType.STANDARD, new int[] {5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4, 5}, false, false, 5f, 35f, -29f, 40f, 0f, 300f, 10, 15, 1, 0.8D, "lily_of_the_valley"),
            new Plant(MADDER, Plant.PlantType.STANDARD, new int[] {5, 0, 1, 1, 2, 2, 3, 3, 3, 4, 4, 5}, false, false, 10f, 25f, -5f, 31f, 100f, 500f, 10, 15, 1, 0.8D, "madder"),
            new Plant(OCOTILLO, Plant.PlantType.DRY, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 31f, -29f, 34f, 0f, 100f, 9, 15, 2, 0.9D, "ocotillo"),
            new Plant(PAPYRUS, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 16f, 40f, 10f, 40f, 270f, 500f, 10, 15, 1, 0.8D, "papyrus"),
            new Plant(PEONY, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0}, false, false, 11f, 21f, -29f, 34f, 125f, 300f, 10, 15, 2, 0.9D, "peony"),
            new Plant(RATTAN, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 10f, 40f, 300f, 500f, 0, 15, 200, 0.5D, "vine"),
            new Plant(SAGO, Plant.PlantType.WATER, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 20f, -34f, 38f, 0f, 500f, 0, 15, 1, 1, 3, 1.0D, "seaweed"),
            new Plant(SEAWEED, Plant.PlantType.WATER_SEA, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 17f, 30f, -21f, 40f, 0f, 500f, 0, 15, 1, 1, 7, 1.0D, "seaweed"),
            new Plant(SHRUB, Plant.PlantType.TALL_GRASS, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, -10f, 40f, 0f, 50f, 70f, 500f, 10, 15, 1, 0.1D, "shrub"),
            new Plant(CHAPARRAL_SHRUB, Plant.PlantType.DRY, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 10f, 40f, 0f, 60f, 100f, 250f, 10, 15, 1, 0.1D, "shrub"),
            new Plant(SUGAR_CANE, Plant.PlantType.TALL_REED, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 18f, 40f, 10f, 40f, 300f, 500f, 10, 15, 3, 0.5D, "sugarcane"),
            new Plant(SUNFLOWER, Plant.PlantType.TALL_PLANT, new int[] {9, 0, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9}, false, false, 8f, 31f, -29f, 34f, 100f, 300f, 10, 15, 2, 0.9D, "sunflower"),
            new Plant(TACKWEED, Plant.PlantType.CREEPING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -2f, 25f, -10f, 40f, 250f, 500f, 0, 14, 1, 0.7D, "moss"),
            new Plant(TAKAKIA, Plant.PlantType.CREEPING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -2f, 25f, -10f, 40f, 250f, 500f, 0, 14, 1, 0.7D, "moss"),
            new Plant(LOW_UNDERGROWTH, Plant.PlantType.TALL_GRASS, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 5, 15, 1, 0.9D, "undergrowth_low"),
            new Plant(VOODOO_LILY, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 16f, 45f, 10f, 50f, 270f, 500f, 6, 15, 2, 0.9D, "voodoo_lily"),
            new Plant(WELD, Plant.PlantType.STANDARD, new int[] {5, 0, 1, 1, 2, 2, 3, 3, 3, 4, 4, 5}, false, false, 10f, 25f, -5f, 31f, 100f, 500f, 10, 15, 1, 0.8D, "weld"),
            new Plant(WOAD, Plant.PlantType.STANDARD, new int[] {6, 0, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6}, false, false, 10f, 25f, -5f, 31f, 100f, 500f, 10, 15, 1, 0.8D, "woad"),
            
            // Mushrooms
            new Plant(AMANITA, Plant.PlantType.MUSHROOM, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 13f, 40f, 0f, 30f, 250f, 500f, 0, 12, 1, 0.8D, "mushroom_red"),
            new Plant(BLACK_POWDERPUFF, Plant.PlantType.MUSHROOM, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 13f, 40f, 0f, 30f, 250f, 500f, 0, 12, 1, 0.8D, "mushroom_black_powderpuff"),
            new Plant(CHANTERELLE, Plant.PlantType.MUSHROOM, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 13f, 40f, 0f, 30f, 250f, 500f, 0, 12, 1, 0.8D, "mushroom_chanterelle"),
            new Plant(DEATH_CAP, Plant.PlantType.MUSHROOM, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 13f, 40f, 0f, 30f, 250f, 500f, 0, 12, 1, 0.8D, "mushroom_death_cap"),
            new Plant(GIANT_CLUB, Plant.PlantType.MUSHROOM, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 13f, 40f, 0f, 30f, 250f, 500f, 0, 12, 1, 0.8D, "mushroom_giant_club"),
            new Plant(PARASOL_MUSHROOM, Plant.PlantType.MUSHROOM, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 13f, 40f, 0f, 30f, 250f, 500f, 0, 12, 1, 0.8D, "mushroom_parasol"),
            new Plant(STINKHORN, Plant.PlantType.MUSHROOM, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 13f, 40f, 0f, 30f, 250f, 500f, 0, 12, 1, 0.8D, "mushroom_stinkhorn"),
            new Plant(WEEPING_MILK_CAP, Plant.PlantType.MUSHROOM, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 13f, 40f, 0f, 30f, 250f, 500f, 0, 12, 1, 0.8D, "mushroom_weeping_milk_cap"),
            new Plant(WOOD_BLEWIT, Plant.PlantType.MUSHROOM, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 13f, 40f, 0f, 30f, 250f, 500f, 0, 12, 1, 0.8D, "mushroom_wood_blewit"),
            new Plant(WOOLLY_GOMPHUS, Plant.PlantType.MUSHROOM, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 13f, 40f, 0f, 30f, 250f, 500f, 0, 12, 1, 0.8D, "mushroom_wooly_gomphus"),
            
            // Ground Cover
            new Plant(BONES, Plant.PlantType.DESERT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 18f, 35f, -34f, 50f, 0f, 100f, 12, 15, 1, 0.5D, "bone"),
            new Plant(PINECONES, Plant.PlantType.TALL_GRASS, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -16f, 7f, -35f, 31f, 95f, 380f, 5, 14, 1, 0.8D, "pinecone"),
            new Plant(ROCKS, Plant.PlantType.TALL_GRASS, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.8D, "rock"),
            new Plant(SEASHELLS, Plant.PlantType.DESERT_TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -40f, 40f, -50f, 50f, 70f, 500f, 5, 15, 1, 0.8D, "seashell"),
            new Plant(TWIGS, Plant.PlantType.TALL_GRASS, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.8D, "twig"),
                        
            // Bamboo
            new Plant(ARROW_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 12, 0D, "arrow_bamboo")
            /*
            new Plant(BLACK_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 15, 0D, "black_bamboo"),
            new Plant(BLUE_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 12, 0D, "blue_bamboo"),
            new Plant(DRAGON_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 21, 0D, "dragon_bamboo"),
            new Plant(GOLDEN_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 13, 0D, "golden_bamboo"),
            new Plant(NARROW_LEAF_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 20, 0D, "narrow_leaf_bamboo"),
            new Plant(RED_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 18, 0D, "red_bamboo"),
            new Plant(TEMPLE_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 14, 0D, "temple_bamboo"),
            new Plant(THORNY_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 12, 0D, "thorny_bamboo"),
            new Plant(TIMBER_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 13, 0D, "timber_bamboo"),
            new Plant(TINWA_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 18, 0D, "tinwa_bamboo"),
            new Plant(WEAVERS_BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 13, 0D, "weavers_bamboo")
            */
        );
    }
}