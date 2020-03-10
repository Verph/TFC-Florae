package tfcelementia.util;

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
public final class TreeRegistryHandler
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
    public static final ResourceLocation GINGKO = new ResourceLocation(MOD_ID, "gingko");
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
    
    @SubscribeEvent
    public static void onPreRegisterRockCategory(TFCRegistryEvent.RegisterPreBlock<Tree> event)
    {
        IForgeRegistry<Tree> r = event.getRegistry();

        r.register(new Tree.Builder(ALDER, 150f, 450f, -2f, 18f, GEN_TALL).setHeight(16).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(601f, 1000).build());
	    r.register(new Tree.Builder(BAMBOO, 200f, 420f, 13f, 35f, GEN_MEDIUM).setRadius(1).setHeight(18).setDecayDist(6).setGrowthTime(3).setDensity(0.6f, 10f).setBurnInfo(420f, 1000).build());
	    r.register(new Tree.Builder(BAOBAB, 20f, 200f, 21f, 40f, GEN_KAPOK).setRadius(4).setHeight(9).setDecayDist(6).setGrowthTime(20).setDensity(0.4f, 1f).setBurnInfo(478f, 1000).build());
        r.register(new Tree.Builder(BEECH, 220f, 550f, 2f, 22f, GEN_TALL).setHeight(16).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(703f, 1750).build());
	    r.register(new Tree.Builder(CINNAMON, 180f, 550f, 15f, 30f, GEN_TALL).setDominance(1f).setHeight(13).setGrowthTime(8).setBushes().setDensity(0.25f, 1f).setBurnInfo(795f, 1000).build());
	    r.register(new Tree.Builder(EBONY, 180f, 550f, 20f, 38f, GEN_TALL).setDominance(2f).setHeight(13).setGrowthTime(8).setBushes().setDensity(0.25f, 2f).setBurnInfo(795f, 1000).build());
	    r.register(new Tree.Builder(EUCALYPTUS, 10f, 300f, 20f, 42f, GEN_TALL).setDominance(1.5f).setHeight(13).setGrowthTime(8).setBushes().setDensity(0.25f, 2f).setBurnInfo(705f, 1000).build());
        r.register(new Tree.Builder(FEVER, 10f, 250f, 14f, 50f, GEN_ACACIA).setHeight(13).setGrowthTime(10).setBushes().setDensity(0.25f, 2f).setBurnInfo(590f, 1000).build());
        r.register(new Tree.Builder(FRUITWOOD, 180f, 550f, 10f, 30f, GEN_TALL).setDominance(2f).setHeight(13).setGrowthTime(9).setDensity(0.25f, 2f).setBurnInfo(720f, 1000).build());
        r.register(new Tree.Builder(GINGKO, 290f, 550f, 10f, 30f, GEN_TALL).setDominance(2f).setHeight(13).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(710f, 1000).build());
        r.register(new Tree.Builder(LARCH, 150f, 430f, -4f, 18f, GEN_MEDIUM).setHeight(16).setGrowthTime(8).setConifer().setDensity(0.25f, 2f).setBurnInfo(632f, 1250).build());
        r.register(new Tree.Builder(HAWTHORN, 180f, 430f, -8f, 18f, GEN_MEDIUM).setHeight(16).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(683f, 1500).build());
        r.register(new Tree.Builder(JUNIPER, 50f, 450f, -8f, 30f, GEN_MEDIUM).setHeight(16).setGrowthTime(8).setConifer().setDensity(0.25f, 0.75f).setBurnInfo(632f, 1750).build());
        r.register(new Tree.Builder(LIMBA, 290f, 550f, 18f, 40f, GEN_TALL).setDominance(2f).setHeight(13).setGrowthTime(9).setBushes().setDensity(0.25f, 2f).setBurnInfo(710f, 1000).build());
        r.register(new Tree.Builder(MAHOGANY, 210f, 700f, 18f, 40f, GEN_KAPOK_COMPOSITE).setDominance(15f).setRadius(3).setHeight(24).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(0.6f, 7f).setBurnInfo(773f, 1000).build());
        r.register(new Tree.Builder(POPLAR, 140f, 430f, -2f, 18f, GEN_TALL).setHeight(16).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(609f, 1000).build());
	    r.register(new Tree.Builder(REDWOOD, 200f, 400f, 2f, 24f, GEN_SEQUOIA).setRadius(2).setHeight(30).setDecayDist(6).setGrowthTime(18).setConifer().setBushes().setTannin().setDensity(0.4f, 1.5f).setBurnInfo(618f, 1750).build());
        r.register(new Tree.Builder(ROWAN, 180f, 430f, -2f, 18f, GEN_MEDIUM).setHeight(16).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(645f, 2000).build());
        r.register(new Tree.Builder(TEAK, 180f, 430f, 17f, 40f, GEN_MEDIUM).setDominance(2f).setHeight(16).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(695f, 1000).build());
        r.register(new Tree.Builder(WHITE_ELM, 120f, 290f, 4f, 30f, GEN_TALL).setHeight(16).setBurnInfo(653f, 1750).build());
        r.register(new Tree.Builder(YEW, 180f, 430f, -4f, 16f, GEN_WILLOW).setHeight(16).setBurnInfo(813f, 2150).build());
    }
}





