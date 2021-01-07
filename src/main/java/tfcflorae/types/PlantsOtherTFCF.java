package tfcflorae.types;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.api.registries.TFCRegistryEvent;

import tfcflorae.TFCFlorae;
import tfcflorae.api.registries.TFCFRegistryEvent;
import tfcflorae.api.types.PlantTFCF;

import static tfcflorae.TFCFlorae.MODID;

@SuppressWarnings("WeakerAccess")
@Mod.EventBusSubscriber(modid = TFCFlorae.MODID)
public final class PlantsOtherTFCF
{
    /**
     * Default Plant ResourceLocations
     */
    
    /*
    // Hanging
    public static final ResourceLocation CAVE_VINES = new ResourceLocation(MODID, "cave_vines");
    public static final ResourceLocation GLOW_VINES = new ResourceLocation(MODID, "glow_vines");
    public static final ResourceLocation ROOTS = new ResourceLocation(MODID, "roots");
    public static final ResourceLocation ICICLE = new ResourceLocation(MODID, "icicle");

    // Ground Cover
    public static final ResourceLocation ROCKS_ANDESITE = new ResourceLocation(MODID, "rocks_andesite");
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
    public static final ResourceLocation ROCKS_SLATE = new ResourceLocation(MODID, "rocks_slate");
    public static final ResourceLocation BONES = new ResourceLocation(MODID, "bones");
    public static final ResourceLocation PINECONES = new ResourceLocation(MODID, "pinecones");
    public static final ResourceLocation TWIGS = new ResourceLocation(MODID, "twigs");
    public static final ResourceLocation SEASHELLS = new ResourceLocation(MODID, "seashells");
    public static final ResourceLocation QUARTZ_CRYSTAL = new ResourceLocation(MODID, "quartz_crystal");
    */

    @SubscribeEvent
    public static void onPreRegisterPlant(TFCFRegistryEvent.RegisterPreBlock<PlantTFCF> event)
    {
        event.getRegistry().registerAll(

            /*
            // Hanging
            new PlantTFCF(CAVE_VINES, PlantTFCF.PlantType.HANGING_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -50f, 50f, -50f, 50f, 0f, 500f, 0, 5, 32, 0.7D, null),        
            new PlantTFCF(GLOW_VINES, PlantTFCF.PlantType.HANGING_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -50f, 50f, -50f, 50f, 0f, 500f, 0, 5, 32, 0.7D, null),
            new PlantTFCF(ROOTS, PlantTFCF.PlantType.HANGING_PLANT, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -50f, 50f, -50f, 50f, 0f, 500f, 0, 5, 2, 0.9D, null),
            new PlantTFCF(ICICLE, PlantTFCF.PlantType.HANGING_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -50f, 0f, -50f, 0f, 0f, 500f, 0, 15, 2, 0.9D, null),

            // Ground Cover
            new PlantTFCF(ROCKS_ANDESITE, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_andesite"),
            new PlantTFCF(ROCKS_BASALT, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_basalt"),
            new PlantTFCF(ROCKS_CHALK, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_chalk"),
            new PlantTFCF(ROCKS_CHERT, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_chert"),
            new PlantTFCF(ROCKS_CLAYSTONE, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_claystone"),
            new PlantTFCF(ROCKS_CONGLOMERATE, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_conglomerate"),
            new PlantTFCF(ROCKS_DACITE, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_dacite"),
            new PlantTFCF(ROCKS_DIORITE, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_diorite"),
            new PlantTFCF(ROCKS_DOLOMITE, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_dolomite"),
            new PlantTFCF(ROCKS_GABBRO, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_gabbro"),
            new PlantTFCF(ROCKS_GNEISS, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_gneiss"),
            new PlantTFCF(ROCKS_GRANITE, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_granite"),
            new PlantTFCF(ROCKS_LIMESTONE, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_limestone"),
            new PlantTFCF(ROCKS_MARBLE, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_marble"),
            new PlantTFCF(ROCKS_PHYLLITE, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_phyllite"),
            new PlantTFCF(ROCKS_QUARTZITE, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_quartzite"),
            new PlantTFCF(ROCKS_RHYOLITE, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_rhyolite"),
            new PlantTFCF(ROCKS_ROCKSALT, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_rocksalt"),
            new PlantTFCF(ROCKS_SCHIST, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_schist"),
            new PlantTFCF(ROCKS_SHALE, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_shale"),
            new PlantTFCF(ROCKS_SLATE, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 50f, -35f, 60f, 0f, 500f, 5, 15, 1, 0.5D, "rock_slate"),

            new PlantTFCF(BONES, PlantTFCF.PlantType.DESERT_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -40f, 40f, -50f, 50f, 0f, 100f, 12, 15, 1, 0.8D, "bone"),
            new PlantTFCF(PINECONES, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -40f, 40f, -50f, 50f, 0f, 500f, 5, 14, 1, 0.9D, "pinecone"),
            new PlantTFCF(SEASHELLS, PlantTFCF.PlantType.DESERT_TALL_PLANT_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -40f, 40f, -50f, 50f, 0f, 500f, 5, 15, 1, 0.9D, "seashell"),
            new PlantTFCF(TWIGS, PlantTFCF.PlantType.STANDARD_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -40f, 40f, -50f, 50f, 0f, 500f, 5, 15, 1, 0.9D, "twig"),

			// Crystals
            new PlantTFCF(QUARTZ_CRYSTAL, PlantTFCF.PlantType.CREEPING_ROCK, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -50f, 50f, -50f, 50f, 0f, 500f, 0, 5, 1, 0.5D, "gem_quartz")
            */
        );
    }
}