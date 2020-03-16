package tfcelementia.types;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.api.util.ITreeGenerator;
import net.dries007.tfc.world.classic.worldgen.trees.*;
import tfcelementia.TFCElementia;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.types.DefaultTrees.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Mod.EventBusSubscriber(modid = TFCElementia.MODID)
public final class TreesTFCE
{
    public static final ResourceLocation ALDER = new ResourceLocation(MOD_ID, "alder");
    public static final ResourceLocation BAMBOO = new ResourceLocation(MOD_ID, "bamboo");
    public static final ResourceLocation BAOBAB = new ResourceLocation(MOD_ID, "baobab");
    public static final ResourceLocation BEECH = new ResourceLocation(MOD_ID, "beech");
    public static final ResourceLocation CINNAMON = new ResourceLocation(MOD_ID, "cinnamon");
    public static final ResourceLocation EBONY = new ResourceLocation(MOD_ID, "ebony");
    public static final ResourceLocation EUCALYPTUS = new ResourceLocation(MOD_ID, "eucalyptus");
    public static final ResourceLocation FEVER = new ResourceLocation(MOD_ID, "fever");
    public static final ResourceLocation FRUITWOOD = new ResourceLocation(MOD_ID, "fruitwood");
    public static final ResourceLocation GINKGO = new ResourceLocation(MOD_ID, "ginkgo");
    public static final ResourceLocation HAWTHORN = new ResourceLocation(MOD_ID, "hawthorn");
    public static final ResourceLocation JUNIPER = new ResourceLocation(MOD_ID, "juniper");
    public static final ResourceLocation LARCH = new ResourceLocation(MOD_ID, "larch");
    public static final ResourceLocation LIMBA = new ResourceLocation(MOD_ID, "limba");
    public static final ResourceLocation MAHOGANY = new ResourceLocation(MOD_ID, "mahogany");
    public static final ResourceLocation POPLAR = new ResourceLocation(MOD_ID, "poplar");
    public static final ResourceLocation REDWOOD = new ResourceLocation(MOD_ID, "redwood");
    public static final ResourceLocation ROWAN = new ResourceLocation(MOD_ID, "rowan");
    public static final ResourceLocation TEAK = new ResourceLocation(MOD_ID, "teak");
    public static final ResourceLocation WHITE_ELM = new ResourceLocation(MOD_ID, "white_elm");
    public static final ResourceLocation YEW = new ResourceLocation(MOD_ID, "yew");

    public static final ITreeGenerator GEN_NORMAL = new TreeGenNormal(1, 3);
    public static final ITreeGenerator GEN_MEDIUM = new TreeGenNormal(2, 2);
    public static final ITreeGenerator GEN_TALL = new TreeGenNormal(3, 3);
    public static final ITreeGenerator GEN_CONIFER = new TreeGenVariants(false, 7);
    public static final ITreeGenerator GEN_TROPICAL = new TreeGenVariants(true, 7);
    public static final ITreeGenerator GEN_WILLOW = new TreeGenWillow();
    public static final ITreeGenerator GEN_ACACIA = new TreeGenAcacia();
    public static final ITreeGenerator GEN_KAPOK = new TreeGenKapok();
    public static final ITreeGenerator GEN_SEQUOIA = new TreeGenSequoia();
    public static final ITreeGenerator GEN_KAPOK_COMPOSITE = new TreeGenComposite().add(0.4f, GEN_TALL).add(0.6f, GEN_KAPOK);

    // Custom Tree Models
    public static final ITreeGenerator GEN_ALDER = new TreeGenVariants(true, 8);
    //public static final ITreeGenerator GEN_BAMBOO = new TreeGenVariants(true, 10);
    public static final ITreeGenerator GEN_BAOBAB = new TreeGenVariants(true, 2);
    public static final ITreeGenerator GEN_BEECH = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_CINNAMON = new TreeGenVariants(true, 4);
    public static final ITreeGenerator GEN_EBONY = new TreeGenVariants(true, 8);
    public static final ITreeGenerator GEN_EUCALYPTUS = new TreeGenVariants(true, 4);
    public static final ITreeGenerator GEN_FEVER = new TreeGenVariants(true, 6);
    public static final ITreeGenerator GEN_FRUITWOOD = new TreeGenVariants(true, 3);
    public static final ITreeGenerator GEN_GINKGO = new TreeGenVariants(true, 12);
    public static final ITreeGenerator GEN_HAWTHORN = new TreeGenVariants(true, 5);
    public static final ITreeGenerator GEN_JUNIPER = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_LARCH = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_LIMBA = new TreeGenVariants(true, 7);
    public static final ITreeGenerator GEN_MAHOGANY = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_POPLAR = new TreeGenVariants(true, 8);
    public static final ITreeGenerator GEN_REDWOOD = new TreeGenVariants(true, 10);
    public static final ITreeGenerator GEN_ROWAN = new TreeGenVariants(true, 5);
    public static final ITreeGenerator GEN_TEAK = new TreeGenVariants(true, 7);
    public static final ITreeGenerator GEN_WHITE_ELM = new TreeGenVariants(true, 14);
    public static final ITreeGenerator GEN_YEW = new TreeGenVariants(true, 8);
    
    @SubscribeEvent
    public static void onPreRegisterRockCategory(TFCRegistryEvent.RegisterPreBlock<Tree> event)
    {
        IForgeRegistry<Tree> r = event.getRegistry();

        r.register(new Tree.Builder(ALDER, 150f, 450f, -2f, 18f, GEN_ALDER).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(601f, 1000).build());
	    r.register(new Tree.Builder(BAMBOO, 200f, 420f, 13f, 35f, GEN_TALL).setDecayDist(6).setGrowthTime(3).setDensity(0.6f, 10f).setBurnInfo(420f, 1000).build());
	    r.register(new Tree.Builder(BAOBAB, 20f, 200f, 21f, 40f, GEN_BAOBAB).setDecayDist(6).setGrowthTime(20).setDensity(0.4f, 0.7f).setBurnInfo(478f, 1000).build());
        r.register(new Tree.Builder(BEECH, 220f, 550f, 2f, 22f, GEN_BEECH).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(703f, 1750).build());
	    r.register(new Tree.Builder(CINNAMON, 180f, 550f, 15f, 30f, GEN_CINNAMON).setGrowthTime(8).setBushes().setDensity(0.25f, 1f).setBurnInfo(795f, 1000).build());
	    r.register(new Tree.Builder(EBONY, 180f, 550f, 20f, 38f, GEN_EBONY).setGrowthTime(8).setBushes().setBushes().setDensity(0.25f, 2f).setBurnInfo(795f, 1000).build());
	    r.register(new Tree.Builder(EUCALYPTUS, 10f, 300f, 18f, 42f, GEN_EUCALYPTUS).setGrowthTime(8).setBushes().setDensity(0.25f, 2f).setBurnInfo(705f, 1000).build());
        r.register(new Tree.Builder(FEVER, 10f, 250f, 14f, 50f, GEN_FEVER).setGrowthTime(10).setBushes().setDensity(0.25f, 2f).setBurnInfo(590f, 1000).build());
        r.register(new Tree.Builder(FRUITWOOD, 180f, 550f, 10f, 30f, GEN_FRUITWOOD).setGrowthTime(9).setDensity(0.25f, 2f).setBurnInfo(720f, 1000).build());
        r.register(new Tree.Builder(GINKGO, 290f, 550f, 10f, 30f, GEN_GINKGO).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(710f, 1000).build());
        r.register(new Tree.Builder(HAWTHORN, 180f, 430f, -8f, 18f, GEN_HAWTHORN).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(683f, 1500).build());
        r.register(new Tree.Builder(JUNIPER, 50f, 450f, -8f, 30f, GEN_JUNIPER).setGrowthTime(8).setConifer().setDensity(0.25f, 0.75f).setBurnInfo(632f, 1750).build());
        r.register(new Tree.Builder(LARCH, 150f, 430f, -4f, 18f, GEN_LARCH).setGrowthTime(8).setConifer().setDensity(1.7f, 2f).setBurnInfo(632f, 1250).build());
        r.register(new Tree.Builder(LIMBA, 290f, 550f, 17f, 40f, GEN_LIMBA).setGrowthTime(9).setBushes().setDensity(0.25f, 2f).setBurnInfo(710f, 1000).build());
        r.register(new Tree.Builder(MAHOGANY, 210f, 700f, 17f, 40f, GEN_MAHOGANY).setDominance(10f).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(1.5f, 2f).setBurnInfo(773f, 1000).build());
        r.register(new Tree.Builder(POPLAR, 140f, 430f, -2f, 18f, GEN_POPLAR).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(609f, 1000).build());
	    r.register(new Tree.Builder(REDWOOD, 200f, 400f, 2f, 18f, GEN_REDWOOD).setDecayDist(6).setGrowthTime(18).setConifer().setBushes().setTannin().setDensity(0.4f, 1.7f).setBurnInfo(618f, 1750).build());
        r.register(new Tree.Builder(ROWAN, 180f, 430f, -2f, 18f, GEN_ROWAN).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(645f, 2000).build());
        r.register(new Tree.Builder(TEAK, 180f, 430f, 17f, 40f, GEN_TEAK).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(695f, 1000).build());
        r.register(new Tree.Builder(WHITE_ELM, 120f, 290f, 4f, 30f, GEN_WHITE_ELM).setGrowthTime(8).setBurnInfo(653f, 1750).build());
        r.register(new Tree.Builder(YEW, 180f, 430f, -4f, 16f, GEN_YEW).setGrowthTime(10).setBushes().setBurnInfo(813f, 2150).build());
    }
}





