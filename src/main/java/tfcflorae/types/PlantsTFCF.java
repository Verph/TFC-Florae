package tfcflorae.types;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Plant;

import tfcflorae.TFCFlorae;

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
    public static final ResourceLocation BROMELIA_HEMISPHERICA = new ResourceLocation(MODID, "bromelia_hemispherica");
    public static final ResourceLocation BROMELIA_LACINIOSA = new ResourceLocation(MODID, "bromelia_laciniosa");
    public static final ResourceLocation KAIETEUR_FALLS = new ResourceLocation(MODID, "kaieteur_falls");
    public static final ResourceLocation MATTEUCCIA = new ResourceLocation(MODID, "matteuccia");
    public static final ResourceLocation CORD_GRASS = new ResourceLocation(MODID, "cord_grass");
    public static final ResourceLocation REED_MANNAGRASS = new ResourceLocation(MODID, "reed_mannagrass");
    public static final ResourceLocation PRAIRIE_JUNEGRASS = new ResourceLocation(MODID, "prairie_junegrass");
    public static final ResourceLocation WOOLLY_BUSH = new ResourceLocation(MODID, "woolly_bush");
    public static final ResourceLocation CINNAMON_FERN = new ResourceLocation(MODID, "cinnamon_fern");
    public static final ResourceLocation JAPANESE_PIERIS = new ResourceLocation(MODID, "japanese_pieris");
    public static final ResourceLocation BURNING_BUSH = new ResourceLocation(MODID, "burning_bush");

    public static final ResourceLocation CAVE_VINES = new ResourceLocation(MODID, "cave_vines");
    public static final ResourceLocation GLOW_VINES = new ResourceLocation(MODID, "glow_vines");
    public static final ResourceLocation ROOTS = new ResourceLocation(MODID, "roots");
    public static final ResourceLocation ICICLE = new ResourceLocation(MODID, "icicle");

    // Vine/Ivy
    public static final ResourceLocation RATTAN = new ResourceLocation(MODID, "rattan");
    public static final ResourceLocation HANGING_VINES = new ResourceLocation(MODID, "hanging_vines");
    public static final ResourceLocation BLUE_SKYFLOWER = new ResourceLocation(MODID, "blue_skyflower");
    public static final ResourceLocation JADE_VINE = new ResourceLocation(MODID, "jade_vine");
    public static final ResourceLocation JAPANESE_IVY = new ResourceLocation(MODID, "japanese_ivy");
    public static final ResourceLocation MADEIRA_VINE = new ResourceLocation(MODID, "madeira_vine");
    public static final ResourceLocation MYSORE_TRUMPETVINE = new ResourceLocation(MODID, "mysore_trumpetvine");
    public static final ResourceLocation SILVERVEIN_CREEPER = new ResourceLocation(MODID, "silvervein_creeper");
    public static final ResourceLocation SWEDISH_IVY = new ResourceLocation(MODID, "swedish_ivy");
    public static final ResourceLocation VARIEGATED_PERSIAN_IVY = new ResourceLocation(MODID, "variegated_persian_ivy");
    
    // Epiphytes
    public static final ResourceLocation APACHE_DWARF = new ResourceLocation(MODID, "apache_dwarf");
    public static final ResourceLocation ARTISTS_CONK = new ResourceLocation(MODID, "artists_conk");
    public static final ResourceLocation CLIMBING_CACTUS = new ResourceLocation(MODID, "climbing_cactus");
    public static final ResourceLocation CRIMSON_CATTLEYA = new ResourceLocation(MODID, "crimson_cattleya");
    public static final ResourceLocation CREEPING_MISTLETOE = new ResourceLocation(MODID, "creeping_mistletoe");
    public static final ResourceLocation CUTHBERTS_DENDROBIUM = new ResourceLocation(MODID, "cuthberts_dendrobium");
    public static final ResourceLocation FISH_BONE_CACTUS = new ResourceLocation(MODID, "fish_bone_cactus");
    public static final ResourceLocation FRAGRANT_FERN = new ResourceLocation(MODID, "fragrant_fern");
    public static final ResourceLocation HARLEQUIN_MISTLETOE = new ResourceLocation(MODID, "harlequin_mistletoe");
    public static final ResourceLocation KING_ORCHID = new ResourceLocation(MODID, "king_orchid");
    public static final ResourceLocation LANTERN_OF_THE_FOREST = new ResourceLocation(MODID, "lantern_of_the_forest");
    public static final ResourceLocation LARGE_FOOT_DENDROBIUM = new ResourceLocation(MODID, "large_foot_dendrobium");
    public static final ResourceLocation COMMON_MISTLETOE = new ResourceLocation(MODID, "common_mistletoe");
    public static final ResourceLocation SKY_PLANT = new ResourceLocation(MODID, "sky_plant");
    public static final ResourceLocation SULPHUR_SHELF = new ResourceLocation(MODID, "sulphur_shelf");
    public static final ResourceLocation TAMPA_BUTTERFLY_ORCHID = new ResourceLocation(MODID, "tampa_butterfly_orchid");
    public static final ResourceLocation TURKEY_TAIL = new ResourceLocation(MODID, "turkey_tail");
    public static final ResourceLocation WILDFIRE = new ResourceLocation(MODID, "wildfire");
    
    // Mushrooms
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
    public static final ResourceLocation BELL_TREE_DAHLIA = new ResourceLocation(MODID, "bell_tree_dahlia");
    public static final ResourceLocation BIG_LEAF_PALM = new ResourceLocation(MODID, "big_leaf_palm");
    public static final ResourceLocation DRAKENSBERG_CYCAD = new ResourceLocation(MODID, "drakensberg_cycad");
    public static final ResourceLocation DWARF_SUGAR_PALM = new ResourceLocation(MODID, "dwarf_sugar_palm");
    public static final ResourceLocation GIANT_CANE = new ResourceLocation(MODID, "giant_cane");
    public static final ResourceLocation GIANT_ELEPHANT_EAR = new ResourceLocation(MODID, "giant_elephant_ear");
    public static final ResourceLocation GIANT_FEATHER_GRASS = new ResourceLocation(MODID, "giant_feather_grass");
    public static final ResourceLocation MADAGASCAR_OCOTILLO = new ResourceLocation(MODID, "madagascar_ocotillo");
    public static final ResourceLocation MALAGASY_TREE_ALOE = new ResourceLocation(MODID, "malagasy_tree_aloe");
    public static final ResourceLocation MOUNTAIN_CABBAGE_TREE = new ResourceLocation(MODID, "mountain_cabbage_tree");
    public static final ResourceLocation PYGMY_DATE_PALM = new ResourceLocation(MODID, "pygmy_date_palm");
    public static final ResourceLocation QUEEN_SAGO = new ResourceLocation(MODID, "queen_sago");
    public static final ResourceLocation RED_SEALING_WAX_PALM = new ResourceLocation(MODID, "red_sealing_wax_palm");
    public static final ResourceLocation SUMMER_ASPHODEL = new ResourceLocation(MODID, "summer_asphodel");
    public static final ResourceLocation ZIMBABWE_ALOE = new ResourceLocation(MODID, "zimbabwe_aloe");
    
    // Ground Cover
    /*public static final ResourceLocation ROCKS_ANDESITE = new ResourceLocation(MODID, "rocks_andesite");
    public static final ResourceLocation ROCKS_BASALT = new ResourceLocation(MODID, "rocks_basalt");
    public static final ResourceLocation ROCKS_CHALK = new ResourceLocation(MODID, "rocks_chalk");
    public static final ResourceLocation ROCKS_CHERT = new ResourceLocation(MODID, "rocks_chert");
    public static final ResourceLocation ROCKS_CLAYSTONE = new ResourceLocation(MODID, "rocks_claystone");
    public static final ResourceLocation ROCKS_CONGLOMERATE = new ResourceLocation(MODID, "rocks_conglomerate");
    public static final ResourceLocation ROCKS_DACITE = new ResourceLocation(MODID, "rocks_dacite");
    public static final ResourceLocation ROCKS_DIORITE = new ResourceLocation(MODID, "rocks_diorite");
    public static final ResourceLocation ROCKS_DOLOMITE = new ResourceLocation(MODID, "rocks_dolomite");
    public static final ResourceLocation ROCKS_GABBRO = new ResourceLocation(MODID, "rocks_gabbro");
    public static final ResourceLocation ROCKS_GNEISS = new ResourceLocation(MODID, "rocks_gneiss");
    public static final ResourceLocation ROCKS_GRANITE = new ResourceLocation(MODID, "rocks_granite");
    public static final ResourceLocation ROCKS_LIMESTONE = new ResourceLocation(MODID, "rocks_limestone");
    public static final ResourceLocation ROCKS_MARBLE = new ResourceLocation(MODID, "rocks_marble");
    public static final ResourceLocation ROCKS_PHYLLITE = new ResourceLocation(MODID, "rocks_phyllite");
    public static final ResourceLocation ROCKS_QUARTZITE = new ResourceLocation(MODID, "rocks_quartzite");
    public static final ResourceLocation ROCKS_RHYOLITE = new ResourceLocation(MODID, "rocks_rhyolite");
    public static final ResourceLocation ROCKS_ROCKSALT = new ResourceLocation(MODID, "rocks_rocksalt");
    public static final ResourceLocation ROCKS_SCHIST = new ResourceLocation(MODID, "rocks_schist");
    public static final ResourceLocation ROCKS_SHALE = new ResourceLocation(MODID, "rocks_shale");
    public static final ResourceLocation ROCKS_SLATE = new ResourceLocation(MODID, "rocks_slate");*/
    // public static final ResourceLocation ROCKS = new ResourceLocation(MODID, "rocks");
    public static final ResourceLocation BONES = new ResourceLocation(MODID, "bones");
    public static final ResourceLocation PINECONES = new ResourceLocation(MODID, "pinecones");
    public static final ResourceLocation TWIGS = new ResourceLocation(MODID, "twigs");
    public static final ResourceLocation SEASHELLS = new ResourceLocation(MODID, "seashells");
    public static final ResourceLocation QUARTZ_CRYSTAL = new ResourceLocation(MODID, "quartz_crystal");
    
    // Bamboo
    public static final ResourceLocation BAMBOO = new ResourceLocation(MODID, "bamboo");
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
            //new Plant(AGAVE, Plant.PlantType.STANDARD, new int[] {0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6}, false, false, 18f, 40f, 10f, 50f, 0f, 180f, 10, 15, 1, 0.8D, "agave"),
            //new Plant(BADDERLOCKS, Plant.PlantType.TALL_WATER_SEA, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 12f, -20f, 16f, 0f, 500f, 0, 15, 2, 3, 14, 1.0D, "seaweed"),
            new Plant(CATTAIL, Plant.PlantType.FLOATING, new int[] {4, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 4}, false, false, 0f, 30f, -12f, 36f, 100f, 500f, 9, 15, 1, 1, 1, 0.5D, "cattail"),
            new Plant(CHAMOMILE, Plant.PlantType.STANDARD, new int[] {6, 6, 7, 0, 1, 1, 2, 2, 3, 4, 5, 6}, false, false, 5f, 25f, -40f, 40f, 75f, 400f, 10, 15, 1, 0.9D, "chamomile"),
            new Plant(DEVILS_TONGUE, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 16f, 45f, 10f, 50f, 270f, 500f, 6, 15, 2, 0.9D, "devils_tongue"),
            //new Plant(FLAX, Plant.PlantType.STANDARD, new int[] {6, 0, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6}, false, false, 10f, 25f, -5f, 31f, 100f, 500f, 10, 15, 1, 0.8D, "flax"),
            //new Plant(GUTWEED, Plant.PlantType.WATER_SEA, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 17f, 30f, -21f, 40f, 0f, 500f, 0, 15, 1, 1, 7, 1.0D, "seaweed"),
            new Plant(HYDRANGEA, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 10, 15, 2, 0.9D, "hydrangea"),
            //new Plant(INDIGO, Plant.PlantType.STANDARD, new int[] {6, 0, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6}, false, false, 15f, 30f, 0f, 39f, 100f, 500f, 10, 15, 1, 0.8D, "indigo"),
            new Plant(LAVANDULA, Plant.PlantType.STANDARD, new int[] {5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4, 5}, false, false, 5f, 35f, -29f, 40f, 0f, 300f, 10, 15, 1, 0.8D, "lavender"),
            new Plant(LILAC, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0}, false, false, 11f, 31f, -29f, 34f, 125f, 300f, 10, 15, 2, 0.9D, "lilac"),
            new Plant(LEAF_LITTER, Plant.PlantType.SHORT_GRASS, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -20f, 50f, -30f, 50f, 70f, 500f, 6, 14, 1, 0.9D, "leaves"),
            //new Plant(LEAF_LITTER, Plant.PlantType.SHORT_GRASS, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 18f, 35f, -17f, 50f, 240f, 500f, 12, 15, 1, 0.7D, null),
            new Plant(LILY_OF_THE_VALLEY, Plant.PlantType.STANDARD, new int[] {5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4, 5}, false, false, 5f, 35f, -29f, 40f, 0f, 300f, 10, 15, 1, 0.8D, "lily_of_the_valley"),
            //new Plant(MADDER, Plant.PlantType.STANDARD, new int[] {5, 0, 1, 1, 2, 2, 3, 3, 3, 4, 4, 5}, false, false, 10f, 25f, -5f, 31f, 100f, 500f, 10, 15, 1, 0.8D, "madder"),
            new Plant(OCOTILLO, Plant.PlantType.DRY, new int[] {0, 0, 0, 1, 1, 1, 2, 2, 2, 1, 0, 0}, false, false, 15f, 50f, -10f, 50f, 0f, 90f, 9, 15, 1, 0.9D, "ocotillo"),
            new Plant(PAPYRUS, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 17f, 40f, 10f, 50f, 150f, 400f, 10, 15, 1, 0.8D, "papyrus"),
            new Plant(PEONY, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0}, false, false, 11f, 21f, -29f, 34f, 125f, 300f, 10, 15, 2, 0.9D, "peony"),
            //new Plant(SAGO, Plant.PlantType.WATER, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 20f, -34f, 38f, 0f, 500f, 0, 15, 1, 1, 3, 1.0D, "seaweed"),
            //new Plant(SEAWEED, Plant.PlantType.WATER_SEA, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 17f, 30f, -21f, 40f, 0f, 500f, 0, 15, 1, 1, 7, 1.0D, "seaweed"),
            new Plant(SHRUB, Plant.PlantType.TALL_GRASS, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, -10f, 40f, 0f, 50f, 70f, 500f, 10, 15, 1, 0.1D, "shrub"),
            new Plant(CHAPARRAL_SHRUB, Plant.PlantType.DRY, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 10f, 40f, 0f, 60f, 100f, 250f, 10, 15, 1, 0.1D, "shrub"),
            new Plant(SUGAR_CANE, Plant.PlantType.TALL_REED, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 18f, 40f, 10f, 40f, 300f, 500f, 10, 15, 3, 0.5D, "sugarcane"),
            new Plant(SUNFLOWER, Plant.PlantType.TALL_PLANT, new int[] {9, 0, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9}, false, false, 8f, 31f, -29f, 34f, 100f, 300f, 10, 15, 2, 0.9D, "sunflower"),
            new Plant(TACKWEED, Plant.PlantType.CREEPING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -2f, 25f, -10f, 40f, 250f, 500f, 0, 14, 1, 0.7D, "moss"),
            new Plant(TAKAKIA, Plant.PlantType.CREEPING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -2f, 25f, -10f, 40f, 250f, 500f, 0, 14, 1, 0.7D, "moss"),
            new Plant(LOW_UNDERGROWTH, Plant.PlantType.TALL_GRASS, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 18f, 40f, 10f, 50f, 260f, 500f, 5, 15, 1, 0.9D, "undergrowth_low"),
            new Plant(VOODOO_LILY, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 16f, 45f, 10f, 50f, 270f, 500f, 6, 15, 2, 0.9D, "voodoo_lily"),
            //new Plant(WELD, Plant.PlantType.STANDARD, new int[] {5, 0, 1, 1, 2, 2, 3, 3, 3, 4, 4, 5}, false, false, 10f, 25f, -5f, 31f, 100f, 500f, 10, 15, 1, 0.8D, "weld"),
            //new Plant(WOAD, Plant.PlantType.STANDARD, new int[] {6, 0, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6}, false, false, 10f, 25f, -5f, 31f, 100f, 500f, 10, 15, 1, 0.8D, "woad"),
            new Plant(BROMELIA_HEMISPHERICA, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 16f, 40f, 10f, 50f, 250f, 500f, 6, 15, 1, 0.8D, null),
            new Plant(BROMELIA_LACINIOSA, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 16f, 40f, 10f, 50f, 250f, 500f, 6, 15, 1, 0.8D, null),
            new Plant(KAIETEUR_FALLS, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0}, false, false, 16f, 40f, 10f, 50f, 250f, 500f, 6, 15, 2, 0.8D, null),
            new Plant(MATTEUCCIA, Plant.PlantType.TALL_GRASS, new int[] {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0}, false, false, 20f, 40f, 0f, 50f, 210f, 500f, 6, 15, 2, 0.5D, null),
            new Plant(CORD_GRASS, Plant.PlantType.TALL_REED, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 17f, 40f, 0f, 50f, 190f, 500f, 6, 15, 2, 0.5D, null),
            new Plant(REED_MANNAGRASS, Plant.PlantType.TALL_REED, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 18f, 40f, 0f, 210f, 200f, 500f, 6, 15, 1, 0.7D, null),
            new Plant(PRAIRIE_JUNEGRASS, Plant.PlantType.SHORT_GRASS, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 13f, 25f, -29f, 32f, 100f, 300f, 6, 15, 1, 0.7D, null),
            new Plant(WOOLLY_BUSH, Plant.PlantType.SHORT_GRASS, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 30f, 5f, 33f, 75f, 190f, 6, 15, 1, 0.7D, null),
            new Plant(CINNAMON_FERN, Plant.PlantType.SHORT_GRASS, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 18f, 25f, -40f, 30f, 100f, 500f, 6, 15, 1, 0.5D, null),
            new Plant(JAPANESE_PIERIS, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 25f, -29f, 30f, 220f, 500f, 6, 15, 1, 0.5D, null),
            new Plant(BURNING_BUSH, Plant.PlantType.TALL_GRASS, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 21f, -29f, 34f, 125f, 300f, 6, 15, 1, 0.5D, null),

            //new Plant(CAVE_VINES, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -50f, 50f, -50f, 50f, 0f, 500f, 0, 5, 32, 0.7D, null),
            //new Plant(GLOW_VINES, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -50f, 50f, -50f, 50f, 0f, 500f, 0, 5, 32, 0.7D, null),
            //new Plant(ROOTS, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -50f, 50f, -50f, 50f, 0f, 500f, 0, 5, 2, 0.9D, null),
            //new Plant(ICICLE, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -50f, 0f, -50f, 0f, 0f, 500f, 0, 15, 2, 0.9D, null),

            // Vine/Ivy
            new Plant(RATTAN, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 16f, 40f, 10f, 40f, 210f, 500f, 0, 15, 16, 0.5D, "vine"),
            new Plant(HANGING_VINES, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 18f, 40f, 10f, 50f, 210f, 500f, 0, 15, 16, 0.7D, "vine"),
            new Plant(BLUE_SKYFLOWER, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 40f, 15f, 50f, 300f, 500f, 0, 15, 16, 0.5D, "vine"),
            new Plant(JADE_VINE, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 10f, 50f, 250f, 500f, 0, 15, 16, 0.5D, "vine"),
            new Plant(JAPANESE_IVY, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 35f, -6f, 36f, 120f, 300f, 0, 15, 16, 0.5D, "vine"),
            new Plant(MADEIRA_VINE, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 5f, 50f, 100f, 350f, 0, 15, 16, 0.5D, "vine"),
            new Plant(MYSORE_TRUMPETVINE, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 10f, 50f, 250f, 500f, 0, 15, 16, 0.5D, "vine"),
            new Plant(SILVERVEIN_CREEPER, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 40f, 15f, 50f, 300f, 500f, 0, 15, 16, 0.5D, "vine"),
            new Plant(SWEDISH_IVY, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 5f, 50f, 50f, 300f, 0, 15, 16, 0.5D, "vine"),
            new Plant(VARIEGATED_PERSIAN_IVY, Plant.PlantType.HANGING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 5f, 50f, 50f, 300f, 0, 15, 16, 0.5D, "vine"),

            // Epiphytes
            new Plant(APACHE_DWARF, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 0f, 40f, 5f, 50f, 100f, 500f, 4, 14, 1, 0.8D, null),
            new Plant(ARTISTS_CONK, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 40f, -12f, 50f, 200f, 500f, 4, 14, 1, 0.8D, "epiphyte_artists_conk"),
            new Plant(CLIMBING_CACTUS, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 11f, 31f, -29f, 34f, 0f, 150f, 4, 14, 1, 0.8D, null),
            new Plant(CRIMSON_CATTLEYA, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 4, 14, 1, 0.8D, null),
            new Plant(CREEPING_MISTLETOE, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 5f, 35f, -29f, 40f, 0f, 300f, 4, 14, 1, 0.8D, null),
            new Plant(CUTHBERTS_DENDROBIUM, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 4, 14, 1, 0.8D, null),
            new Plant(FISH_BONE_CACTUS, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 15f, 40f, 10f, 50f, 130f, 300f, 4, 14, 1, 0.8D, null),
            new Plant(FRAGRANT_FERN, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 0f, 40f, -5f, 50f, 200f, 500f, 4, 14, 1, 0.8D, null),
            new Plant(HARLEQUIN_MISTLETOE, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 0f, 40f, -12f, 50f, 200f, 500f, 4, 14, 1, 0.8D, null),
            new Plant(KING_ORCHID, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 0f, 40f, -5f, 50f, 190f, 500f, 4, 14, 1, 0.8D, null),
            new Plant(LANTERN_OF_THE_FOREST, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 0f, 40f, -5f, 50f, 190f, 500f, 4, 14, 1, 0.8D, null),
            new Plant(LARGE_FOOT_DENDROBIUM, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 0f, 40f, -5f, 50f, 190f, 500f, 4, 14, 1, 0.8D, null),
            new Plant(COMMON_MISTLETOE, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 5f, 35f, -29f, 40f, 0f, 300f, 4, 14, 1, 0.8D, null),
            new Plant(SKY_PLANT, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 15f, 40f, 10f, 50f, 130f, 300f, 4, 14, 1, 0.8D, null),
            new Plant(SULPHUR_SHELF, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 40f, -12f, 50f, 200f, 500f, 4, 14, 1, 0.8D, "epiphyte_sulphur_shelf"),
            new Plant(TAMPA_BUTTERFLY_ORCHID, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 0f, 40f, -5f, 50f, 190f, 500f, 4, 14, 1, 0.8D, null),
            new Plant(TURKEY_TAIL, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 40f, -12f, 50f, 200f, 500f, 4, 14, 1, 0.8D, "epiphyte_turkey_tail"),
            new Plant(WILDFIRE, Plant.PlantType.EPIPHYTE, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, 15f, 40f, 10f, 50f, 130f, 300f, 4, 14, 1, 0.8D, null),

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

            // Large Plants
            new Plant(BELL_TREE_DAHLIA, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 30f, -12f, 36f, 75f, 200f, 9, 15, 2, 0.2D, null),
            new Plant(BIG_LEAF_PALM, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 10f, 50f, 250f, 500f, 9, 15, 2, 0.2D, null),
            new Plant(DRAKENSBERG_CYCAD, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 30f, -34f, 36f, 0f, 75f, 9, 15, 2, 0.2D, null),
            new Plant(DWARF_SUGAR_PALM, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 10f, 50f, 300f, 500f, 9, 15, 2, 0.2D, null),
            new Plant(GIANT_CANE, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 18f, 30f, -12f, 36f, 150f, 500f, 9, 15, 2, 0.2D, null),
            new Plant(GIANT_ELEPHANT_EAR, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 10f, 50f, 250f, 500f, 9, 15, 2, 0.2D, null),
            new Plant(GIANT_FEATHER_GRASS, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 23f, -29f, 32f, 75f, 300f, 9, 15, 2, 0.2D, null),
            new Plant(MADAGASCAR_OCOTILLO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 30f, -34f, 36f, 0f, 75f, 9, 15, 2, 0.2D, null),
            new Plant(MALAGASY_TREE_ALOE, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 30f, -34f, 36f, 0f, 75f, 9, 15, 2, 0.2D, null),
            new Plant(MOUNTAIN_CABBAGE_TREE, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 10f, 50f, 250f, 500f, 9, 15, 2, 0.2D, null),
            new Plant(PYGMY_DATE_PALM, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 10f, 50f, 250f, 500f, 9, 15, 2, 0.2D, null),
            new Plant(QUEEN_SAGO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 10f, 50f, 250f, 500f, 9, 15, 2, 0.2D, null),
            new Plant(RED_SEALING_WAX_PALM, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 10f, 50f, 250f, 500f, 9, 15, 2, 0.2D, null),
            new Plant(SUMMER_ASPHODEL, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 25f, -34f, 33f, 150f, 300f, 9, 15, 2, 0.2D, null),
            new Plant(ZIMBABWE_ALOE, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 30f, -34f, 36f, 0f, 75f, 9, 15, 2, 0.2D, null),

            // Ground Cover
            /*
            new Plant(ROCKS_ANDESITE, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_andesite"),
            new Plant(ROCKS_BASALT, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_basalt"),
            new Plant(ROCKS_CHALK, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_chalk"),
            new Plant(ROCKS_CHERT, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_chert"),
            new Plant(ROCKS_CLAYSTONE, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_claystone"),
            new Plant(ROCKS_CONGLOMERATE, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_conglomerate"),
            new Plant(ROCKS_DACITE, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_dacite"),
            new Plant(ROCKS_DIORITE, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_diorite"),
            new Plant(ROCKS_DOLOMITE, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_dolomite"),
            new Plant(ROCKS_GABBRO, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_gabbro"),
            new Plant(ROCKS_GNEISS, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_gneiss"),
            new Plant(ROCKS_GRANITE, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_granite"),
            new Plant(ROCKS_LIMESTONE, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_limestone"),
            new Plant(ROCKS_MARBLE, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_marble"),
            new Plant(ROCKS_PHYLLITE, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_phyllite"),
            new Plant(ROCKS_QUARTZITE, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_quartzite"),
            new Plant(ROCKS_RHYOLITE, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_rhyolite"),
            new Plant(ROCKS_ROCKSALT, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_rocksalt"),
            new Plant(ROCKS_SCHIST, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_schist"),
            new Plant(ROCKS_SHALE, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_shale"),
            new Plant(ROCKS_SLATE, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_slate"),
            */

            // new Plant(ROCKS, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock"),
            //new Plant(BONES, Plant.PlantType.DESERT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -40f, 40f, -50f, 50f, 0f, 100f, 12, 15, 1, 0.8D, "bone"),
            new Plant(PINECONES, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -40f, 40f, -50f, 50f, 0f, 500f, 5, 13, 1, 0.9D, "pinecone"),
            //new Plant(SEASHELLS, Plant.PlantType.DESERT_TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -40f, 40f, -50f, 50f, 0f, 500f, 5, 15, 1, 0.9D, "seashell"),
            new Plant(TWIGS, Plant.PlantType.STANDARD, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -40f, 40f, -50f, 50f, 0f, 500f, 5, 13, 1, 0.9D, "twig"),

            // Crystals
			new Plant(QUARTZ_CRYSTAL, Plant.PlantType.CREEPING, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -50f, 50f, -50f, 50f, 0f, 500f, 0, 5, 1, 0.5D, "gem_quartz"),

            // Bamboo
            new Plant(BAMBOO, Plant.PlantType.TALL_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 40f, 10f, 50f, 270f, 500f, 9, 15, 12, 0D, "bamboo")
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