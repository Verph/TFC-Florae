package tfcflorae.types;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Plant;

import tfcflorae.ConfigTFCF;
import tfcflorae.TFCFlorae;
import tfcflorae.api.registries.TFCFRegistryEvent;
import tfcflorae.api.types.PlantTFCF;

import static tfcflorae.TFCFlorae.MODID;

@SuppressWarnings("WeakerAccess")
@Mod.EventBusSubscriber(modid = TFCFlorae.MODID)
public final class DefaultPlantsTFCF
{
    public static final ResourceLocation BADDERLOCKS = new ResourceLocation(MODID, "badderlocks");
    public static final ResourceLocation BROWN_ALGAE = new ResourceLocation(MODID, "brown_algae");
    public static final ResourceLocation COONTAIL = new ResourceLocation(MODID, "coontail");
    public static final ResourceLocation EEL_GRASS = new ResourceLocation(MODID, "eel_grass");
    public static final ResourceLocation GIANT_KELP = new ResourceLocation(MODID, "giant_kelp");
    public static final ResourceLocation GUTWEED = new ResourceLocation(MODID, "gutweed");
    public static final ResourceLocation HORNWORT = new ResourceLocation(MODID, "hornwort");
    public static final ResourceLocation LAMINARIA = new ResourceLocation(MODID, "laminaria");
    public static final ResourceLocation LEAFY_KELP = new ResourceLocation(MODID, "leafy_kelp");
    public static final ResourceLocation MANATEE_GRASS = new ResourceLocation(MODID, "manatee_grass");
    public static final ResourceLocation MILFOIL = new ResourceLocation(MODID, "milfoil");
    public static final ResourceLocation PONDWEED = new ResourceLocation(MODID, "pondweed");
    public static final ResourceLocation SAGO = new ResourceLocation(MODID, "sago");
    public static final ResourceLocation SEAWEED = new ResourceLocation(MODID, "seaweed");
    public static final ResourceLocation STAR_GRASS = new ResourceLocation(MODID, "star_grass");
    public static final ResourceLocation TURTLE_GRASS = new ResourceLocation(MODID, "turtle_grass");
    public static final ResourceLocation WINGED_KELP = new ResourceLocation(MODID, "winged_kelp");
    
    @SubscribeEvent
    public static void onPreRegisterPlantTFCF(TFCRegistryEvent.RegisterPreBlock<PlantTFCF> event)
    {
        event.getRegistry().registerAll(
            // Water plants
            new PlantTFCF(BADDERLOCKS, PlantTFCF.PlantTypeTFCF.TALL_WATER_SEA, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -18f, 2f, -33f, 17f, 150f, 500f, 0, 15, 6, 1, 64, 0.8D, "seaweed"),
            //new PlantTFCF(BROWN_ALGAE, PlantTFCF.PlantTypeTFCF.WATER, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -24f, -2f, -39f, 13f, 100f, 500f, 0, 15, 1, 1, 64, 0.6D, "seaweed"),
            new PlantTFCF(COONTAIL, PlantTFCF.PlantTypeTFCF.WATER, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 2f, 18f, -13f, 33f, 250f, 500f, 0, 15, 1, 0, 64, 0.7D, "seaweed"), //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
            new PlantTFCF(EEL_GRASS, PlantTFCF.PlantTypeTFCF.WATER, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 6f, 40f, -9f, 50f, 200f, 500f, 0, 15, 1, 0, 64, 0.9D, "seaweed"), //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
            new PlantTFCF(GIANT_KELP, PlantTFCF.PlantTypeTFCF.TALL_WATER_SEA, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -18f, 18f, -33f, 33f, 0f, 500f, 0, 15, 26, 1, 64, 0.9D, "seaweed"),
            new PlantTFCF(GUTWEED, PlantTFCF.PlantTypeTFCF.WATER, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -6f, 18f, -21f, 33f, 100f, 500f, 0, 15, 1, 0, 64, 0.9D, "seaweed"),
            new PlantTFCF(HORNWORT, PlantTFCF.PlantTypeTFCF.WATER, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 2f, 18f, -13f, 33f, 250f, 500f, 0, 15, 1, 0, 64, 0.7D, "seaweed"),
            new PlantTFCF(LAMINARIA, PlantTFCF.PlantTypeTFCF.WATER_SEA, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -16f, 4f, -31f, 19f, 250f, 400f, 0, 15, 1, 1, 64, 0.6D, "seaweed"),
            new PlantTFCF(LEAFY_KELP, PlantTFCF.PlantTypeTFCF.TALL_WATER_SEA, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -20f, 20f, -35f, 35f, 0f, 500f, 0, 15, 21, 1, 64, 0.9D, "seaweed"),
            new PlantTFCF(MANATEE_GRASS, PlantTFCF.PlantTypeTFCF.WATER, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 12f, 40f, -3f, 50f, 250f, 500f, 0, 15, 1, 0, 64, 0.9D, "seaweed"), //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
            new PlantTFCF(MILFOIL, PlantTFCF.PlantTypeTFCF.WATER, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -14f, 22f, -29f, 37f, 250f, 500f, 0, 15, 1, 0, 64, 0.7D, "seaweed"),
            new PlantTFCF(PONDWEED, PlantTFCF.PlantTypeTFCF.TALL_WATER, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -18f, 18f, -33f, 33f, 200f, 500f, 0, 15, 5, 0, 64, 0.7D, "seaweed"),
            new PlantTFCF(SAGO, PlantTFCF.PlantTypeTFCF.WATER, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 20f, -34f, 38f, 0f, 500f, 0, 15, 1, 0, 64, 0.9D, "seaweed"),
            new PlantTFCF(SEAWEED, PlantTFCF.PlantTypeTFCF.WATER_SEA, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 17f, 30f, -21f, 40f, 0f, 500f, 0, 15, 1, 1, 64, 0.9D, "seaweed"),
            new PlantTFCF(STAR_GRASS, PlantTFCF.PlantTypeTFCF.WATER_SEA, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 2f, 40f, -13f, 50f, 50f, 260f, 0, 15, 1, 0, 64, 0.9D, "seaweed"), //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
            new PlantTFCF(TURTLE_GRASS, PlantTFCF.PlantTypeTFCF.WATER_SEA, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 14f, 40f, -1f, 50f, 240f, 500f, 0, 15, 1, 0, 64, 0.9D, "seaweed"), //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
            new PlantTFCF(WINGED_KELP, PlantTFCF.PlantTypeTFCF.TALL_WATER_SEA, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -15f, 15f, -30f, 30f, 0f, 450f, 0, 15, 21, 1, 64, 0.8D, "seaweed")
        );
    }
}
