package tfcflorae.types;

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
import tfcflorae.TFCFlorae;

//import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.types.DefaultTrees.*;
import static net.dries007.tfc.types.DefaultTrees.GEN_TALL;
import static net.dries007.tfc.util.Helpers.getNull;

import static tfcflorae.TFCFlorae.MODID;

@SuppressWarnings({"unused", "WeakerAccess"})
@Mod.EventBusSubscriber(modid = TFCFlorae.MODID)
public final class TreesTFCF
{
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":african_padauk") public static final Tree AFRICAN_PADAUK = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":alder") public static final Tree ALDER = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":angelim") public static final Tree ANGELIM = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":baobab") public static final Tree BAOBAB = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":beech") public static final Tree BEECH = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":black_walnut") public static final Tree BLACK_WALNUT = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":box") public static final Tree BOX = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":brazilwood") public static final Tree BRAZILWOOD = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":butternut") public static final Tree BUTTERNUT = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":cinnamon") public static final Tree CINNAMON = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":cocobolo") public static final Tree COCOBOLO = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":cypress") public static final Tree CYPRESS = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":ebony") public static final Tree EBONY = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":eucalyptus") public static final Tree EUCALYPTUS = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":european_oak") public static final Tree EUROPEAN_OAK = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":fever") public static final Tree FEVER = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":fruitwood") public static final Tree FRUITWOOD = getNull();
    //@GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":giganteum") public static final Tree GIGANTEUM = getNull();	// Basically Redwood Trees
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":ginkgo") public static final Tree GINKGO = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":greenheart") public static final Tree GREENHEART = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":hawthorn") public static final Tree HAWTHORN = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":hazel") public static final Tree HAZEL = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":hemlock") public static final Tree HEMLOCK = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":holly") public static final Tree HOLLY = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":hornbeam") public static final Tree HORNBEAM = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":ipe") public static final Tree IPE = getNull();				// Has pink/yellow leaves
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":iroko") public static final Tree IROKO = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":ironwood") public static final Tree IRONWOOD = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":jacaranda") public static final Tree JACARANDA = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":kauri") public static final Tree KAURI = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":larch") public static final Tree LARCH = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":limba") public static final Tree LIMBA = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":locust") public static final Tree LOCUST = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":logwood") public static final Tree LOGWOOD = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":maclura") public static final Tree MACLURA = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":mahoe") public static final Tree MAHOE = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":mahogany") public static final Tree MAHOGANY = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":marblewood") public static final Tree MARBLEWOOD = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":messmate") public static final Tree MESSMATE = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":mountain_ash") public static final Tree MOUNTAIN_ASH = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":nordmann_fir") public static final Tree NORDMANN_FIR = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":norway_spruce") public static final Tree NORWAY_SPRUCE = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":pink_cherry") public static final Tree PINK_CHERRY = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":pink_ivory") public static final Tree PINK_IVORY = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":poplar") public static final Tree POPLAR = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":purpleheart") public static final Tree PURPLEHEART = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":red_cedar") public static final Tree RED_CEDAR = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":red_elm") public static final Tree RED_ELM = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":redwood") public static final Tree REDWOOD = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":rowan") public static final Tree ROWAN = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":rubber_fig") public static final Tree RUBBER_FIG = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":sweetgum") public static final Tree SWEETGUM = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":syzygium") public static final Tree SYZYGIUM = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":teak") public static final Tree TEAK = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":wenge") public static final Tree WENGE = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":white_cherry") public static final Tree WHITE_CHERRY = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":white_elm") public static final Tree WHITE_ELM = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":whitebeam") public static final Tree WHITEBEAM = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":yellow_meranti") public static final Tree YELLOW_MERANTI = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":yew") public static final Tree YEW = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":zebrawood") public static final Tree ZEBRAWOOD = getNull();
    
    //Fruit Trees	#Should have actual fruit trees at some point
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":citrus") public static final Tree CITRUS = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":elder") public static final Tree ELDER = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":juniper") public static final Tree JUNIPER = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":lime") public static final Tree LIME = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":papaya") public static final Tree PAPAYA = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":persimmon") public static final Tree PERSIMMON = getNull();
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":plum") public static final Tree PLUM = getNull();
    
    //Bamboo "Tree" Variants
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":arrow_bamboo") public static final Tree ARROW_BAMBOO = getNull();	// Pseudosasa japonica
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":black_bamboo") public static final Tree BLACK_BAMBOO = getNull(); // Phyllostachys nigra
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":blue_bamboo") public static final Tree BLUE_BAMBOO = getNull(); // Himalayacalamus hookerianus
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":dragon_bamboo") public static final Tree DRAGON_BAMBOO = getNull(); // Dendrocalamus giganteus
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":golden_bamboo") public static final Tree GOLDEN_BAMBOO = getNull(); // Alphonse Karr
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":narrow_leaf_bamboo") public static final Tree NARROW_LEAF_BAMBOO = getNull(); // Guadua angustifolia
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":red_bamboo") public static final Tree RED_BAMBOO = getNull(); // Fargesia nitida ‘Jiuzhaigou’
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":temple_bamboo") public static final Tree TEMPLE_BAMBOO = getNull(); // Semsiarundinaria fastuosa
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":thorny_bamboo") public static final Tree THORNY_BAMBOO = getNull(); // Chimonobambusa pachystachys
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":timber_bamboo") public static final Tree TIMBER_BAMBOO = getNull(); // Phyllostachys vivax
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":tinwa_bamboo") public static final Tree TINWA_BAMBOO = getNull(); // Cephalostachyum pergracile
    @GameRegistry.ObjectHolder(TerraFirmaCraft.MOD_ID + ":weavers_bamboo") public static final Tree WEAVERS_BAMBOO = getNull(); // Bambusa textilis

    // Custom Tree Models
    public static final ITreeGenerator GEN_AFRICAN_PADAUK = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_ALDER = new TreeGenVariants(true, 8);
    public static final ITreeGenerator GEN_ANGELIM = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_BAOBAB = new TreeGenVariants(true, 2);
    public static final ITreeGenerator GEN_BEECH = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_BLACK_WALNUT = new TreeGenVariants(true, 13);
    //public static final ITreeGenerator GEN_BOX = new TreeGenVariants(true, 9);			// Tall Tree Model
    public static final ITreeGenerator GEN_BRAZILWOOD = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_BUTTERNUT = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_CINNAMON = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_COCOBOLO = new TreeGenVariants(true, 9);
    //public static final ITreeGenerator GEN_CYPRESS = new TreeGenVariants(true, 9);		// Tall Tree Model
    public static final ITreeGenerator GEN_EBONY = new TreeGenVariants(true, 19);
    public static final ITreeGenerator GEN_ELDER = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_EUCALYPTUS = new TreeGenVariants(true, 7);
    public static final ITreeGenerator GEN_EUROPEAN_OAK = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_FEVER = new TreeGenVariants(true, 6);
    public static final ITreeGenerator GEN_FRUITWOOD = new TreeGenVariants(true, 3);
    //public static final ITreeGenerator GEN_GIGANTEUM = new TreeGenVariants(true, 10);		// Redwood
    public static final ITreeGenerator GEN_GINKGO = new TreeGenVariants(true, 12);
    public static final ITreeGenerator GEN_GREENHEART = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_HAWTHORN = new TreeGenVariants(true, 5);
    public static final ITreeGenerator GEN_HAZEL = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_HEMLOCK = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_HOLLY = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_HORNBEAM = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_IPE = new TreeGenVariants(true, 22);
    public static final ITreeGenerator GEN_IROKO = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_IRONWOOD = new TreeGenVariants(true, 15);
    public static final ITreeGenerator GEN_JACARANDA = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_JUNIPER = new TreeGenVariants(true, 11);
    public static final ITreeGenerator GEN_KAURI = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_LARCH = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_LIMBA = new TreeGenVariants(true, 7);
    public static final ITreeGenerator GEN_LOCUST = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_LOGWOOD = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_MACLURA = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_MAHOE = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_MAHOGANY = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_MARBLEWOOD = new TreeGenVariants(true, 18);
    public static final ITreeGenerator GEN_MESSMATE = new TreeGenVariants(true, 17);
    public static final ITreeGenerator GEN_MOUNTAIN_ASH = new TreeGenVariants(true, 14);
    public static final ITreeGenerator GEN_NORDMANN_FIR = new TreeGenVariants(true, 19);
    public static final ITreeGenerator GEN_NORWAY_SPRUCE = new TreeGenVariants(true, 9);
    public static final ITreeGenerator GEN_PERSIMMON = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_PINK_CHERRY = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_PINK_IVORY = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_POPLAR = new TreeGenVariants(true, 8);
    public static final ITreeGenerator GEN_PURPLEHEART = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_RED_CEDAR = new TreeGenVariants(true, 14);
    public static final ITreeGenerator GEN_RED_ELM = new TreeGenVariants(true, 14);
    public static final ITreeGenerator GEN_REDWOOD = new TreeGenVariants(true, 10);
    public static final ITreeGenerator GEN_ROWAN = new TreeGenVariants(true, 5);
    public static final ITreeGenerator GEN_RUBBER_FIG = new TreeGenVariants(true, 7);
    public static final ITreeGenerator GEN_SWEETGUM = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_SYZYGIUM = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_TEAK = new TreeGenVariants(true, 7);
    public static final ITreeGenerator GEN_WENGE = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_WHITE_CHERRY = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_WHITE_ELM = new TreeGenVariants(true, 14);
    public static final ITreeGenerator GEN_WHITEBEAM = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_YELLOW_MERANTI = new TreeGenVariants(true, 13);
    public static final ITreeGenerator GEN_YEW = new TreeGenVariants(true, 17);
    public static final ITreeGenerator GEN_ZEBRAWOOD = new TreeGenVariants(true, 13);
    
    //Bamboo "Tree" Variants
    public static final ITreeGenerator GEN_ARROW_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_BLACK_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_BLUE_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_DRAGON_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_GOLDEN_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_NARROW_LEAF_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_RED_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_TEMPLE_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_THORNY_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_TIMBER_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_TINWA_BAMBOO = new TreeGenVariants(true, 1);
    public static final ITreeGenerator GEN_WEAVERS_BAMBOO = new TreeGenVariants(true, 1);
    
    @SubscribeEvent
    public static void onPreRegisterTrees(TFCRegistryEvent.RegisterPreBlock<Tree> event)
    {
    	// Normal Trees
    	event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "african_padauk"), 195f, 500f, 21f, 42f, GEN_AFRICAN_PADAUK).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(0.2f, 2f).setBurnInfo(745f, 1500).build());
    	event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "alder"), 150f, 400f, -4f, 13f, GEN_ALDER).setGrowthTime(8).setBushes().setDensity(0.25f, 2f).setBurnInfo(601f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "angelim"), 240f, 500f, 22f, 45f, GEN_ANGELIM).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(0.2f, 2f).setBurnInfo(773f, 1200).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "baobab"), 10f, 150f, 21f, 40f, GEN_BAOBAB).setDecayDist(6).setGrowthTime(20).setDensity(0.1f, 0.3f).setBurnInfo(478f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "beech"), 220f, 300f, 2f, 17f, GEN_BEECH).setGrowthTime(8).setBushes().setTannin().setDensity(0.25f, 1f).setBurnInfo(703f, 1750).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "black_walnut"), 180f, 300f, 3f, 25f, GEN_BLACK_WALNUT).setGrowthTime(9).setBurnInfo(758f, 1800).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "box"), 180f, 400f, -8f, 15f, GEN_TALL).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(683f, 1500).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "brazilwood"), 290f, 550f, 10f, 30f, GEN_BRAZILWOOD).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(710f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "butternut"), 180f, 300f, 3f, 25f, GEN_BUTTERNUT).setGrowthTime(9).setBurnInfo(758f, 1800).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "cinnamon"), 290f, 550f, 14f, 30f, GEN_CINNAMON).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(710f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "cocobolo"), 175f, 500f, 19f, 42f, GEN_COCOBOLO).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(773f, 1000).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "cypress"), 180f, 350f, 15f, 30f, GEN_TALL).setHeight(16).setGrowthTime(8).setConifer().setBurnInfo(783f, 1100).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "ebony"), 180f, 320f, 20f, 38f, GEN_EBONY).setGrowthTime(8).setBurnInfo(795f, 1000).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "eucalyptus"), 120f, 300f, 18f, 39f, GEN_EUCALYPTUS).setGrowthTime(8).setBushes().setDensity(0.35f, 2f).setBurnInfo(705f, 1000).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "european_oak"), 140f, 430f, -8f, 17f, GEN_EUROPEAN_OAK).setGrowthTime(10).setTannin().setBurnInfo(728f, 2250).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "fever"), 90f, 250f, 14f, 50f, GEN_FEVER).setGrowthTime(10).setDensity(0.25f, 1f).setBurnInfo(590f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "fruitwood"), 180f, 550f, 10f, 30f, GEN_FRUITWOOD).setGrowthTime(9).setBurnInfo(720f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "ginkgo"), 290f, 550f, 10f, 30f, GEN_GINKGO).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(710f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "greenheart"), 230f, 500f, 23f, 42f, GEN_GREENHEART).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(0.2f, 2f).setBurnInfo(793f, 1700).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "hawthorn"), 180f, 400f, -8f, 14f, GEN_HAWTHORN).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(683f, 1500).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "hazel"), 150f, 400f, -8f, 14f, GEN_HAZEL).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(683f, 1500).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "hemlock"), 140f, 400f, -4f, 16f, GEN_HEMLOCK).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(609f, 1000).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "hornbeam"), 140f, 430f, -8f, 17f, GEN_HORNBEAM).setGrowthTime(10).setTannin().setBurnInfo(728f, 2250).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "holly"), 140f, 400f, -4f, 16f, GEN_HOLLY).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(609f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "ipe"), 150f, 350f, 20f, 43f, GEN_IPE).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(0.2f, 2f).setBurnInfo(785f, 1200).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "iroko"), 220f, 500f, 20f, 43f, GEN_IROKO).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(0.2f, 2f).setBurnInfo(785f, 1200).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "ironwood"), 30f, 210f, 19f, 31f, GEN_IRONWOOD).setDecayDist(6).setGrowthTime(11).setDensity(0.1f, 0.6f).setBurnInfo(694f, 1170).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "jacaranda"), 180f, 300f, 15f, 30f, GEN_JACARANDA).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(795f, 1250).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "juniper"), 80f, 350f, -8f, 20f, GEN_JUNIPER).setGrowthTime(8).setConifer().setDensity(0.25f, 0.75f).setTannin().setBurnInfo(632f, 1750).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "kauri"), 280f, 500f, 16f, 35f, GEN_KAURI).setGrowthTime(10).setDensity(0.2f, 2f).setBurnInfo(730f, 1250).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "larch"), 150f, 400f, -11f, 15f, GEN_LARCH).setGrowthTime(8).setConifer().setDensity(0.9f, 1.5f).setBurnInfo(632f, 1250).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "limba"), 290f, 550f, 17f, 40f, GEN_LIMBA).setGrowthTime(9).setDensity(0.25f, 1f).setBurnInfo(710f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "locust"), 120f, 290f, 4f, 30f, GEN_LOCUST).setGrowthTime(8).setBurnInfo(653f, 1750).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "logwood"), 180f, 430f, 17f, 35f, GEN_LOGWOOD).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(695f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "maclura"), 140f, 400f, -2f, 17f, GEN_MACLURA).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(773f, 1000).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "mahoe"), 180f, 350f, 15f, 30f, GEN_MAHOE).setHeight(16).setGrowthTime(8).setConifer().setBurnInfo(783f, 1100).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "mahogany"), 210f, 500f, 21f, 42f, GEN_MAHOGANY).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(0.2f, 2f).setBurnInfo(773f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "marblewood"), 180f, 500f, 21f, 42f, GEN_MARBLEWOOD).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(0.2f, 2f).setBurnInfo(837f, 1200).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "messmate"), 120f, 270f, 2f, 27f, GEN_MESSMATE).setGrowthTime(10).setDensity(0.2f, 2f).setBurnInfo(696f, 1250).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "mountain_ash"), 80f, 270f, 9f, 29f, GEN_MOUNTAIN_ASH).setGrowthTime(10).setDensity(0.4f, 2f).setBurnInfo(696f, 1250).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "nordmann_fir"), 100f, 380f, -16f, 7f, GEN_NORDMANN_FIR).setGrowthTime(8).setConifer().setDensity(0.1f, 0.9f).setBurnInfo(628f, 1500).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "norway_spruce"), 100f, 380f, -16f, 7f, GEN_NORWAY_SPRUCE).setGrowthTime(8).setConifer().setDensity(0.1f, 0.9f).setBurnInfo(628f, 1500).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "pink_cherry"), 180f, 300f, 15f, 30f, GEN_PINK_CHERRY).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(795f, 1250).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "pink_ivory"), 210f, 500f, 21f, 42f, GEN_PINK_IVORY).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(0.2f, 2f).setBurnInfo(773f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "poplar"), 140f, 400f, -4f, 16f, GEN_POPLAR).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(609f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "purpleheart"), 230f, 500f, 23f, 42f, GEN_PURPLEHEART).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(0.2f, 2f).setBurnInfo(793f, 1700).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "red_cedar"), 10f, 240f, -8f, 17f, GEN_RED_CEDAR).setDecayDist(6).setGrowthTime(18).setConifer().setBushes().setTannin().setDensity(0.4f, 2f).setBurnInfo(618f, 1750).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "red_elm"), 120f, 290f, 4f, 30f, GEN_RED_ELM).setDecayDist(6).setGrowthTime(18).setConifer().setBushes().setTannin().setDensity(0.4f, 2f).setBurnInfo(618f, 1750).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "redwood"), 180f, 400f, 0f, 17f, GEN_REDWOOD).setDecayDist(6).setGrowthTime(18).setConifer().setBushes().setTannin().setDensity(0.4f, 2f).setBurnInfo(618f, 1750).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "rowan"), 180f, 400f, -4f, 14f, GEN_ROWAN).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(645f, 2000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "rubber_fig"), 210f, 550f, 20f, 43f, GEN_RUBBER_FIG).setDecayDist(6).setGrowthTime(16).setBushes().setDensity(0.2f, 1f).setBurnInfo(785f, 1440).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "sweetgum"), 140f, 360f, 3f, 20f, GEN_SWEETGUM).setDecayDist(6).setGrowthTime(16).setBushes().setTannin().setDensity(0.2f, 1f).setBurnInfo(745f, 2000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "syzygium"), 140f, 360f, 3f, 20f, GEN_SYZYGIUM).setDecayDist(6).setGrowthTime(16).setBushes().setTannin().setDensity(0.2f, 1f).setBurnInfo(745f, 2000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "teak"), 180f, 430f, 17f, 35f, GEN_TEAK).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(695f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "wenge"), 175f, 500f, 19f, 42f, GEN_WENGE).setGrowthTime(8).setDensity(0.25f, 1f).setBurnInfo(773f, 1250).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "white_cherry"), 180f, 300f, 15f, 30f, GEN_WHITE_CHERRY).setGrowthTime(8).setDensity(0.25f, 2f).setBurnInfo(795f, 1250).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "white_elm"), 120f, 290f, 4f, 30f, GEN_WHITE_ELM).setGrowthTime(8).setBurnInfo(653f, 1750).build());
	    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "whitebeam"), 140f, 430f, -8f, 17f, GEN_WHITEBEAM).setGrowthTime(10).setTannin().setBurnInfo(728f, 1750).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "yellow_meranti"), 180f, 500f, 21f, 42f, GEN_YELLOW_MERANTI).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(0.2f, 2f).setBurnInfo(837f, 1200).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "yew"), 180f, 350f, -8f, 14f, GEN_YEW).setGrowthTime(10).setBushes().setBurnInfo(813f, 2150).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "zebrawood"), 200f, 500f, 23f, 42f, GEN_ZEBRAWOOD).setDecayDist(6).setGrowthTime(18).setBushes().setDensity(0.2f, 2f).setBurnInfo(822f, 1570).build());
    
        // Fruit Trees
        
        // Bamboo
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "arrow_bamboo"), 200f, 420f, 13f, 35f, GEN_ARROW_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setDensity(0f, 0f).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "black_bamboo"), 220f, 420f, 13f, 35f, GEN_BLACK_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setDensity(0f, 0f).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "blue_bamboo"), 180f, 420f, 10f, 35f, GEN_BLUE_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setDensity(0f, 0f).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "dragon_bamboo"), 200f, 420f, 13f, 35f, GEN_DRAGON_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setDensity(0f, 0f).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "golden_bamboo"), 200f, 420f, 13f, 35f, GEN_GOLDEN_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setDensity(0f, 0f).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "narrow_leaf_bamboo"), 200f, 420f, 13f, 35f, GEN_NARROW_LEAF_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setDensity(0f, 0f).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "red_bamboo"), 200f, 420f, 13f, 35f, GEN_RED_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setDensity(0f, 0f).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "temple_bamboo"), 200f, 420f, 13f, 35f, GEN_TEMPLE_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setDensity(0f, 0f).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "thorny_bamboo"), 200f, 420f, 13f, 35f, GEN_THORNY_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setDensity(0f, 0f).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "timber_bamboo"), 200f, 420f, 13f, 35f, GEN_TIMBER_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setDensity(0f, 0f).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "tinwa_bamboo"), 200f, 420f, 13f, 35f, GEN_TINWA_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setDensity(0f, 0f).setBurnInfo(420f, 1000).build());
        event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "weavers_bamboo"), 200f, 420f, 13f, 35f, GEN_WEAVERS_BAMBOO).setDominance(0f).setDecayDist(6).setGrowthTime(3).setDensity(0f, 0f).setBurnInfo(420f, 1000).build());
    }
}





