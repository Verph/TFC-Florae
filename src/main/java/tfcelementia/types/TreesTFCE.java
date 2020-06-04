package tfcelementia.types;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.api.util.ITreeGenerator;
import net.dries007.tfc.world.classic.worldgen.trees.*;
import tfcelementia.TFCElementia;

//import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.types.DefaultTrees.*;
import static net.dries007.tfc.types.DefaultTrees.GEN_TALL;
import static net.dries007.tfc.util.Helpers.getNull;

import static tfcelementia.TFCElementia.MODID;

@SuppressWarnings({"unused", "WeakerAccess"})
@Mod.EventBusSubscriber(modid = TFCElementia.MODID)
public final class TreesTFCE
{
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":alder") public static final Tree ALDER = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":baobab") public static final Tree BAOBAB = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":beech") public static final Tree BEECH = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":black_walnut") public static final Tree BLACK_WALNUT = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":cypress") public static final Tree CYPRESS = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":ebony") public static final Tree EBONY = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":eucalyptus") public static final Tree EUCALYPTUS = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":fever") public static final Tree FEVER = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":fruitwood") public static final Tree FRUITWOOD = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":ginkgo") public static final Tree GINKGO = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":hawthorn") public static final Tree HAWTHORN = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":iroko") public static final Tree IROKO = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":jacaranda") public static final Tree JACARANDA = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":juniper") public static final Tree JUNIPER = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":larch") public static final Tree LARCH = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":limba") public static final Tree LIMBA = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":mahogany") public static final Tree MAHOGANY = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":norway_spruce") public static final Tree NORWAY_SPRUCE = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":pink_cherry") public static final Tree PINK_CHERRY = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":poplar") public static final Tree POPLAR = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":redwood") public static final Tree REDWOOD = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":rowan") public static final Tree ROWAN = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":teak") public static final Tree TEAK = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":white_cherry") public static final Tree WHITE_CHERRY = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":white_elm") public static final Tree WHITE_ELM = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":yew") public static final Tree YEW = getNull();
    
    //Bamboo "Tree" Variants
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":arrow_bamboo") public static final Tree ARROW_BAMBOO = getNull(); //Pseudosasa japonica
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":black_bamboo") public static final Tree BLACK_BAMBOO = getNull(); //Phyllostachys nigra
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":blue_bamboo") public static final Tree BLUE_BAMBOO = getNull(); //Himalayacalamus hookerianus
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":dragon_bamboo") public static final Tree DRAGON_BAMBOO = getNull(); //Dendrocalamus giganteus
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":giant_timber_bamboo") public static final Tree GIANT_TIMBER_BAMBOO = getNull(); //Bambusa oldhamii
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":golden_bamboo") public static final Tree GOLDEN_BAMBOO = getNull(); //Alphonse Karr
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":narrow_leaf_bamboo") public static final Tree NARROW_LEAF_BAMBOO = getNull(); //Guadua angustifolia
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":temple_bamboo") public static final Tree TEMPLE_BAMBOO = getNull(); //Semiarundinaria fastuosa
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":thorny_bamboo") public static final Tree THORNY_BAMBOO = getNull(); //Chimonobambusa pachystachys
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":timber_bamboo") public static final Tree TIMBER_BAMBOO = getNull(); //Phyllostachys vivax
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":tinwa_bamboo") public static final Tree TINWA_BAMBOO = getNull(); //Cephalostachyum pergracile
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":weavers_bamboo") public static final Tree WEAVERS_BAMBOO = getNull(); //Bambusa textilis

    // Custom Tree Models
    public static final ITreeGenerator GEN_ALDER = new TreeGenVariants(true, 8);
    public static final ITreeGenerator GEN_BAOBAB = new TreeGenVariants(true, 2);
    public static final ITreeGenerator GEN_BEECH = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_BLACK_WALNUT = new TreeGenVariants(true, 13);
    //public static final ITreeGenerator GEN_CYPRESS = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_EBONY = new TreeGenVariants(true, 19);
    public static final ITreeGenerator GEN_EUCALYPTUS = new TreeGenVariants(true, 7);
    public static final ITreeGenerator GEN_FEVER = new TreeGenVariants(true, 6);
    public static final ITreeGenerator GEN_FRUITWOOD = new TreeGenVariants(true, 3);
    public static final ITreeGenerator GEN_GINKGO = new TreeGenVariants(true, 12);
    public static final ITreeGenerator GEN_HAWTHORN = new TreeGenVariants(true, 5);
    public static final ITreeGenerator GEN_IROKO = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_JACARANDA = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_JUNIPER = new TreeGenVariants(true, 11);
    public static final ITreeGenerator GEN_LARCH = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_LIMBA = new TreeGenVariants(true, 7);
    public static final ITreeGenerator GEN_MAHOGANY = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_NORWAY_SPRUCE = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_PINK_CHERRY = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_POPLAR = new TreeGenVariants(true, 8);
    public static final ITreeGenerator GEN_REDWOOD = new TreeGenVariants(true, 10);
    public static final ITreeGenerator GEN_ROWAN = new TreeGenVariants(true, 5);
    public static final ITreeGenerator GEN_TEAK = new TreeGenVariants(true, 7);
    public static final ITreeGenerator GEN_WHITE_CHERRY = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_WHITE_ELM = new TreeGenVariants(true, 14);
    public static final ITreeGenerator GEN_YEW = new TreeGenVariants(true, 17);
    
    //Bamboo "Tree" Variants
    public static final ITreeGenerator GEN_ARROW_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_BLACK_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_BLUE_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_DRAGON_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_GIANT_TIMBER_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_GOLDEN_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_NARROW_LEAF_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_TEMPLE_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_THORNY_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_TIMBER_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_TINWA_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_WEAVERS_BAMBOO = new TreeGenVariants(true, 1);
    
    @SubscribeEvent
    public static void onPreRegisterTrees(TFCRegistryEvent.RegisterPreBlock<Tree> event)
    {
    	event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "alder"), 150f, 400f, -4f, 13f, GEN_ALDER).setGrowthTime(8).setBushes().setDensity(0.25f, 2f).setBurnInfo(601f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "baobab"), 10f, 150f, 21f, 40f, GEN_BAOBAB).setDecayDist(6).setGrowthTime(20).setDensity(0.4f, 0.7f).setBurnInfo(478f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "beech"), 220f, 500f, 2f, 17f, GEN_BEECH).setGrowthTime(8).setBushes().setTannin().setDensity(0.25f, 1f).setBurnInfo(703f, 1750).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "black_walnut"), 180f, 300f, 3f, 25f, GEN_BLACK_WALNUT).setGrowthTime(9).setBurnInfo(758f, 1800).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "cypress"), 180f, 350f, 15f, 30f, GEN_TALL).setHeight(16).setGrowthTime(8).setConifer().setBurnInfo(783f, 1100).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "ebony"), 180f, 320f, 20f, 38f, GEN_EBONY).setGrowthTime(8).setBurnInfo(795f, 1000).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "eucalyptus"), 10f, 300f, 18f, 39f, GEN_EUCALYPTUS).setGrowthTime(8).setBushes().setDensity(0.35f, 2f).setBurnInfo(705f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "fever"), 10f, 250f, 14f, 50f, GEN_FEVER).setGrowthTime(10).setDensity(0.25f, 1f).setBurnInfo(590f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "fruitwood"), 180f, 550f, 10f, 30f, GEN_FRUITWOOD).setGrowthTime(9).setBurnInfo(720f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "ginkgo"), 290f, 550f, 10f, 30f, GEN_GINKGO).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(710f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "hawthorn"), 180f, 400f, -8f, 14f, GEN_HAWTHORN).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(683f, 1500).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "iroko"), 220f, 500f, 20f, 43f, GEN_IROKO).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(0.2f, 2f).setBurnInfo(785f, 1200).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "jacaranda"), 180f, 400f, 15f, 30f, GEN_JACARANDA).setDominance(0f).setGrowthTime(8).setDensity(0f, 0f).setBurnInfo(795f, 1250).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "juniper"), 50f, 450f, -8f, 20f, GEN_JUNIPER).setGrowthTime(8).setConifer().setDensity(0.25f, 0.75f).setBurnInfo(632f, 1750).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "larch"), 150f, 400f, -11f, 15f, GEN_LARCH).setGrowthTime(8).setConifer().setDensity(0.9f, 1.5f).setBurnInfo(632f, 1250).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "limba"), 290f, 550f, 17f, 40f, GEN_LIMBA).setGrowthTime(9).setDensity(0.25f, 1f).setBurnInfo(710f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "mahogany"), 210f, 500f, 21f, 42f, GEN_MAHOGANY).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(0.2f, 2f).setBurnInfo(773f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "norway_spruce"), 100f, 380f, -16f, 7f, GEN_NORWAY_SPRUCE).setGrowthTime(8).setConifer().setDensity(0.1f, 0.9f).setBurnInfo(628f, 1500).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "pink_cherry"), 180f, 400f, 15f, 30f, GEN_PINK_CHERRY).setDominance(0f).setGrowthTime(8).setDensity(0f, 0f).setBurnInfo(795f, 1250).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "poplar"), 140f, 400f, -4f, 16f, GEN_POPLAR).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(609f, 1000).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "redwood"), 200f, 400f, 0f, 17f, GEN_REDWOOD).setDecayDist(6).setGrowthTime(18).setConifer().setBushes().setTannin().setDensity(0.4f, 2f).setBurnInfo(618f, 1750).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "rowan"), 180f, 400f, -4f, 14f, GEN_ROWAN).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(645f, 2000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "teak"), 180f, 430f, 17f, 35f, GEN_TEAK).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(695f, 1000).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "white_cherry"), 180f, 400f, 15f, 30f, GEN_WHITE_CHERRY).setDominance(0f).setGrowthTime(8).setDensity(0f, 0f).setBurnInfo(795f, 1250).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "white_elm"), 120f, 290f, 4f, 30f, GEN_WHITE_ELM).setGrowthTime(8).setBurnInfo(653f, 1750).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "yew"), 180f, 400f, -8f, 14f, GEN_YEW).setGrowthTime(10).setBushes().setBurnInfo(813f, 2150).build());
    
        //Bamboo
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "arrow_bamboo"), 200f, 420f, 13f, 35f, GEN_ARROW_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "black_bamboo"), 220f, 420f, 13f, 35f, GEN_BLACK_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "blue_bamboo"), 180f, 420f, 10f, 35f, GEN_BLUE_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "dragon_bamboo"), 200f, 420f, 13f, 35f, GEN_DRAGON_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "giant_timber_bamboo"), 200f, 420f, 13f, 35f, GEN_GIANT_TIMBER_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "golden_bamboo"), 200f, 420f, 13f, 35f, GEN_GOLDEN_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "narrow_leaf_bamboo"), 200f, 420f, 13f, 35f, GEN_NARROW_LEAF_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "temple_bamboo"), 200f, 420f, 13f, 35f, GEN_TEMPLE_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "thorny_bamboo"), 200f, 420f, 13f, 35f, GEN_THORNY_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "timber_bamboo"), 200f, 420f, 13f, 35f, GEN_TIMBER_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "tinwa_bamboo"), 200f, 420f, 13f, 35f, GEN_TINWA_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "weavers_bamboo"), 200f, 420f, 13f, 35f, GEN_WEAVERS_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setBurnInfo(420f, 1000).build());
    }
}





