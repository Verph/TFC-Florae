package tfcelementia.types;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.api.recipes.AlloyRecipe;
import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.objects.ArmorMaterialTFC;
import tfcelementia.TFCElementia;
import tfcelementia.objects.ArmorMaterialsTFCE;
import tfcelementia.objects.ToolMaterialsTFCE;
import tfcelementia.objects.items.ItemsTFCE;

//import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.types.DefaultMetals.*;

import static tfcelementia.TFCElementia.MODID;

@SuppressWarnings({"unused", "WeakerAccess"})
@Mod.EventBusSubscriber(modid = TFCElementia.MODID)
public final class MetalsTFCE
{
	// Coal
    public static final ResourceLocation ANTHRACITE = new ResourceLocation(MODID, "anthracite");
	
    // Other Minerals
    public static final ResourceLocation SALAMMONIAC = new ResourceLocation(MODID, "salammoniac");
    
	// Ores
    // Reactive nonmetals
    public static final ResourceLocation FLUORITE = new ResourceLocation(MODID, "fluorite");
    public static final ResourceLocation IODATE = new ResourceLocation(MODID, "iodate");
    public static final ResourceLocation PHOSPHORITE = new ResourceLocation(MODID, "phosphorite");
    public static final ResourceLocation SELENIDE = new ResourceLocation(MODID, "selenide");
    
    // Noble gases
    // None
    
    // Alkali metals
    public static final ResourceLocation SPODUMENE = new ResourceLocation(MODID, "spodumene");
    public static final ResourceLocation HALITE = new ResourceLocation(MODID, "halite");
    public static final ResourceLocation CARNALLITE = new ResourceLocation(MODID, "carnallite");
    public static final ResourceLocation LEPIDOLITE = new ResourceLocation(MODID, "lepidolite");
    public static final ResourceLocation POLLUCITE = new ResourceLocation(MODID, "pollucite");
    
    // Alkaline earth metals
    public static final ResourceLocation BERTRANDITE = new ResourceLocation(MODID, "bertrandite");
    public static final ResourceLocation MAGNESITE = new ResourceLocation(MODID, "magnesite");
    public static final ResourceLocation CELESTINE = new ResourceLocation(MODID, "celestine");
    public static final ResourceLocation BARYTE = new ResourceLocation(MODID, "baryte");
    
    // Transition metals
    public static final ResourceLocation THORTVEITITE = new ResourceLocation(MODID, "thortveitite");
    public static final ResourceLocation RUTILE = new ResourceLocation(MODID, "rutile"); //Titanium
    public static final ResourceLocation VANADINITE = new ResourceLocation(MODID, "vanadinite");
    public static final ResourceLocation CHROMITE = new ResourceLocation(MODID, "chromite");
    public static final ResourceLocation PYROLUSITE = new ResourceLocation(MODID, "pyrolusite");
    public static final ResourceLocation PYRITE = new ResourceLocation(MODID, "pyrite"); //Iron
    public static final ResourceLocation COBALTITE = new ResourceLocation(MODID, "cobaltite"); //Cobalt
    public static final ResourceLocation CHALCOCITE = new ResourceLocation(MODID, "chalcocite"); //Copper
    public static final ResourceLocation CHALCOPYRITE = new ResourceLocation(MODID, "chalcopyrite"); //Copper
    public static final ResourceLocation XENOTIME = new ResourceLocation(MODID, "xenotime");
    public static final ResourceLocation ZIRCON = new ResourceLocation(MODID, "zircon");
    public static final ResourceLocation COLUMBITE = new ResourceLocation(MODID, "columbite");
    public static final ResourceLocation MOLYBDENITE = new ResourceLocation(MODID, "molybdenite");
    public static final ResourceLocation PENTLANDITE = new ResourceLocation(MODID, "pentlandite");
    public static final ResourceLocation RUTHENIRIDOSMIUM = new ResourceLocation(MODID, "rutheniridosmium"); //Ruthenium
    public static final ResourceLocation NATIVE_RHODIUM = new ResourceLocation(MODID, "native_rhodium");
    public static final ResourceLocation COOPERITE = new ResourceLocation(MODID, "cooperite"); //Palladium
    public static final ResourceLocation PYRARGYRITE = new ResourceLocation(MODID, "pyrargyrite"); //Silver
    public static final ResourceLocation HAFNON = new ResourceLocation(MODID, "hafnon");
    public static final ResourceLocation TANTALITE = new ResourceLocation(MODID, "tantalite");
    public static final ResourceLocation WOLFRAMITE = new ResourceLocation(MODID, "wolframite"); //Tungsten
    public static final ResourceLocation RHENIITE = new ResourceLocation(MODID, "rheniite");
    public static final ResourceLocation OSMIRIDIUM = new ResourceLocation(MODID, "osmiridium");
    public static final ResourceLocation IRIDOSMIUM = new ResourceLocation(MODID, "iridosmium");
    //public static final ResourceLocation NATIVE_PLATINUM = new ResourceLocation(MODID, "native_platinum"); //Platinum
    
    //Post-transition metals
    public static final ResourceLocation BAUXITE = new ResourceLocation(MODID, "bauxite"); //Aluminium
    public static final ResourceLocation GALLITE = new ResourceLocation(MODID, "gallite");
    public static final ResourceLocation GREENOCKITE = new ResourceLocation(MODID, "greenockite");
    public static final ResourceLocation ROQUESITE = new ResourceLocation(MODID, "roquesite");
    public static final ResourceLocation LIVINGSTONITE = new ResourceLocation(MODID, "livingstonite");
    public static final ResourceLocation LORANDITE = new ResourceLocation(MODID, "lorandite");
    //public static final ResourceLocation GALENA = new ResourceLocation(MODID, "galena"); //Lead
    
    // Metalloid
    public static final ResourceLocation ULEXITE = new ResourceLocation(MODID, "ulexite");
    public static final ResourceLocation SILICA = new ResourceLocation(MODID, "silica");
    public static final ResourceLocation GERMANITE = new ResourceLocation(MODID, "germanite");
    public static final ResourceLocation REALGAR = new ResourceLocation(MODID, "realgar");
    public static final ResourceLocation STIBNITE = new ResourceLocation(MODID, "stibnite"); //Antimony
    public static final ResourceLocation CALAVERITE = new ResourceLocation(MODID, "calaverite");
    
    // Lanthanides
    public static final ResourceLocation MONAZITE = new ResourceLocation(MODID, "monazite");
    public static final ResourceLocation BASTNAESITE = new ResourceLocation(MODID, "bastnaesite");
    public static final ResourceLocation KOZOITE = new ResourceLocation(MODID, "kozoite");
    public static final ResourceLocation SAMARSKITE = new ResourceLocation(MODID, "samarskite");
    public static final ResourceLocation LOPARITE = new ResourceLocation(MODID, "loparite");
    public static final ResourceLocation GADOLINITE = new ResourceLocation(MODID, "gadolinite");
    public static final ResourceLocation EUXENITE = new ResourceLocation(MODID, "euxenite");
    public static final ResourceLocation FERGUSONITE = new ResourceLocation(MODID, "fergusonite");
    
    // Actinides
    public static final ResourceLocation THORIANITE = new ResourceLocation(MODID, "thorianite");
    public static final ResourceLocation URANINITE = new ResourceLocation(MODID, "uraninite");
    //public static final ResourceLocation PITCHBLENDE = new ResourceLocation(MODID, "pitchblende"); //Uraninite
	
    // Geodes
    public static final ResourceLocation GEODE_AGATE = new ResourceLocation(MODID, "geode_agate");
    public static final ResourceLocation GEODE_AMETHYST = new ResourceLocation(MODID, "geode_amethyst");
    public static final ResourceLocation GEODE_BERYL = new ResourceLocation(MODID, "geode_beryl");
    public static final ResourceLocation GEODE_DIAMOND = new ResourceLocation(MODID, "geode_diamond");
    public static final ResourceLocation GEODE_EMERALD = new ResourceLocation(MODID, "geode_emerald");
    public static final ResourceLocation GEODE_GARNET = new ResourceLocation(MODID, "geode_garnet");
    public static final ResourceLocation GEODE_JADE = new ResourceLocation(MODID, "geode_jade");
    public static final ResourceLocation GEODE_JASPER = new ResourceLocation(MODID, "geode_jasper");
    public static final ResourceLocation GEODE_OPAL = new ResourceLocation(MODID, "geode_opal");
    public static final ResourceLocation GEODE_RUBY = new ResourceLocation(MODID, "geode_ruby");
    public static final ResourceLocation GEODE_SAPPHIRE = new ResourceLocation(MODID, "geode_sapphire");
    public static final ResourceLocation GEODE_TOPAZ = new ResourceLocation(MODID, "geode_topaz");
    public static final ResourceLocation GEODE_TOURMALINE = new ResourceLocation(MODID, "geode_tourmaline");
    public static final ResourceLocation GEODE_AMBER = new ResourceLocation(MODID, "geode_amber");
    public static final ResourceLocation GEODE_APATITE = new ResourceLocation(MODID, "geode_apatite");
    public static final ResourceLocation GEODE_AQUAMARINE = new ResourceLocation(MODID, "geode_aquamarine");
    public static final ResourceLocation GEODE_BROMARGYRITE = new ResourceLocation(MODID, "geode_bromargyrite");
    public static final ResourceLocation GEODE_CITRINE = new ResourceLocation(MODID, "geode_citrine");
    public static final ResourceLocation GEODE_HELIODOR = new ResourceLocation(MODID, "geode_heliodor");
    public static final ResourceLocation GEODE_IODARGYRITE = new ResourceLocation(MODID, "geode_iodargyrite");
    public static final ResourceLocation GEODE_KYANITE = new ResourceLocation(MODID, "geode_kyanite");
    public static final ResourceLocation GEODE_MOLDAVITE = new ResourceLocation(MODID, "geode_moldavite");
    public static final ResourceLocation GEODE_MOONSTONE = new ResourceLocation(MODID, "geode_moonstone");
    public static final ResourceLocation GEODE_PYROMORPHITE = new ResourceLocation(MODID, "geode_pyromorphite");
    public static final ResourceLocation GEODE_QUARTZ = new ResourceLocation(MODID, "geode_quartz");
    public static final ResourceLocation GEODE_SPINEL = new ResourceLocation(MODID, "geode_spinel");
    public static final ResourceLocation GEODE_SUNSTONE = new ResourceLocation(MODID, "geode_sunstone");
    public static final ResourceLocation GEODE_TANZANITE = new ResourceLocation(MODID, "geode_tanzanite");
    public static final ResourceLocation GEODE_ZIRCON = new ResourceLocation(MODID, "geode_zircon");

    // Fantasy
    public static final ResourceLocation NATIVE_ARDITE = new ResourceLocation(MODID, "native_ardite"); //Ardite
    public static final ResourceLocation ADAMANTITE = new ResourceLocation(MODID, "adamantite"); //Adamantium
    
    // Metals
    // Alkali metals
	public static final ResourceLocation LITHIUM = new ResourceLocation(MODID, "lithium");
	public static final ResourceLocation NATRIUM = new ResourceLocation(MODID, "natrium");
	public static final ResourceLocation KALIUM = new ResourceLocation(MODID, "kalium");
	public static final ResourceLocation RUBIDIUM = new ResourceLocation(MODID, "rubidium");
	public static final ResourceLocation CAESIUM = new ResourceLocation(MODID, "caesium");
	public static final ResourceLocation FRANCIUM = new ResourceLocation(MODID, "francium");
    
    // Alkaline earth metals
	public static final ResourceLocation BERYLLIUM = new ResourceLocation(MODID, "beryllium");
	public static final ResourceLocation MAGNESIUM = new ResourceLocation(MODID, "magnesium");
	public static final ResourceLocation CALCIUM = new ResourceLocation(MODID, "calcium");
	public static final ResourceLocation STRONTIUM = new ResourceLocation(MODID, "strontium");
	public static final ResourceLocation BARIUM = new ResourceLocation(MODID, "barium");
	public static final ResourceLocation RADIUM = new ResourceLocation(MODID, "radium");
    
    // Transition metals
	public static final ResourceLocation SCANDIUM = new ResourceLocation(MODID, "scandium");
    public static final ResourceLocation TITANIUM = new ResourceLocation(MODID, "titanium");
	public static final ResourceLocation VANADIUM = new ResourceLocation(MODID, "vanadium");
	public static final ResourceLocation CHROMIUM = new ResourceLocation(MODID, "chromium");
	public static final ResourceLocation MANGANESE = new ResourceLocation(MODID, "manganese");
    public static final ResourceLocation COBALT = new ResourceLocation(MODID, "cobalt");
	public static final ResourceLocation YTTRIUM = new ResourceLocation(MODID, "yttrium");
	public static final ResourceLocation ZIRCONIUM = new ResourceLocation(MODID, "zirconium");
	public static final ResourceLocation NIOBIUM = new ResourceLocation(MODID, "niobium");
	public static final ResourceLocation MOLYBDENUM = new ResourceLocation(MODID, "molybdenum");
	public static final ResourceLocation TECHNETIUM = new ResourceLocation(MODID, "technetium");
	public static final ResourceLocation RUTHENIUM = new ResourceLocation(MODID, "ruthenium");
	public static final ResourceLocation RHODIUM = new ResourceLocation(MODID, "rhodium");
	public static final ResourceLocation PALLADIUM = new ResourceLocation(MODID, "palladium");
	public static final ResourceLocation HAFNIUM = new ResourceLocation(MODID, "hafnium");
	public static final ResourceLocation TANTALUM = new ResourceLocation(MODID, "tantalum");
    public static final ResourceLocation TUNGSTEN = new ResourceLocation(MODID, "tungsten");
	public static final ResourceLocation RHENIUM = new ResourceLocation(MODID, "rhenium");
    public static final ResourceLocation OSMIUM = new ResourceLocation(MODID, "osmium");
	public static final ResourceLocation IRIDIUM = new ResourceLocation(MODID, "iridium");
    //public static final ResourceLocation PLATINUM = new ResourceLocation(MODID, "platinum");
	public static final ResourceLocation RUTHERFORDIUM = new ResourceLocation(MODID, "rutherfordium");
	public static final ResourceLocation DUBNIUM = new ResourceLocation(MODID, "dubnium");
	public static final ResourceLocation SEABORGIUM = new ResourceLocation(MODID, "seaborgium");
	public static final ResourceLocation BOHRIUM = new ResourceLocation(MODID, "bohrium");
	public static final ResourceLocation HASSIUM = new ResourceLocation(MODID, "hassium");
	public static final ResourceLocation COPERNICIUM = new ResourceLocation(MODID, "copernicium");
    
    // Post-transition metals
    public static final ResourceLocation ALUMINIUM = new ResourceLocation(MODID, "aluminium");
	public static final ResourceLocation GALLIUM = new ResourceLocation(MODID, "gallium");
	public static final ResourceLocation CADMIUM = new ResourceLocation(MODID, "cadmium");
	public static final ResourceLocation INDIUM = new ResourceLocation(MODID, "indium");
	public static final ResourceLocation MERCURY = new ResourceLocation(MODID, "mercury");
	public static final ResourceLocation THALLIUM = new ResourceLocation(MODID, "thallium");
    //public static final ResourceLocation LEAD = new ResourceLocation(MODID, "lead");
	public static final ResourceLocation POLONIUM = new ResourceLocation(MODID, "polonium");
    
    // Metalloid
	public static final ResourceLocation BORON = new ResourceLocation(MODID, "boron");
	public static final ResourceLocation SILICIUM = new ResourceLocation(MODID, "silicium");
	public static final ResourceLocation GERMANIUM = new ResourceLocation(MODID, "germanium");
	public static final ResourceLocation ARSENIC = new ResourceLocation(MODID, "arsenic");
    public static final ResourceLocation ANTIMONY = new ResourceLocation(MODID, "antimony");
	public static final ResourceLocation TELLURIUM = new ResourceLocation(MODID, "tellurium");
	public static final ResourceLocation ASTATINE = new ResourceLocation(MODID, "astatine");
    
    // Lanthanides
	public static final ResourceLocation LANTHANUM = new ResourceLocation(MODID, "lanthanum");
	public static final ResourceLocation CERIUM = new ResourceLocation(MODID, "cerium");
	public static final ResourceLocation PRASEODYMIUM = new ResourceLocation(MODID, "praseodymium");
	public static final ResourceLocation NEODYMIUM = new ResourceLocation(MODID, "neodymium");
	public static final ResourceLocation PROMETHIUM = new ResourceLocation(MODID, "promethium");
	public static final ResourceLocation SAMARIUM = new ResourceLocation(MODID, "samarium");
	public static final ResourceLocation EUROPIUM = new ResourceLocation(MODID, "europium");
	public static final ResourceLocation GADOLINIUM = new ResourceLocation(MODID, "gadolinium");
	public static final ResourceLocation TERBIUM = new ResourceLocation(MODID, "terbium");
	public static final ResourceLocation DYSPROSIUM = new ResourceLocation(MODID, "dysprosium");
	public static final ResourceLocation HOLMIUM = new ResourceLocation(MODID, "holmium");
	public static final ResourceLocation ERBIUM = new ResourceLocation(MODID, "erbium");
	public static final ResourceLocation THULIUM = new ResourceLocation(MODID, "thulium");
	public static final ResourceLocation YTTERBIUM = new ResourceLocation(MODID, "ytterbium");
	public static final ResourceLocation LUTETIUM = new ResourceLocation(MODID, "lutetium");
    
    // Actinides
	public static final ResourceLocation ACTINIUM = new ResourceLocation(MODID, "actinium");
	public static final ResourceLocation THORIUM = new ResourceLocation(MODID, "thorium");
	public static final ResourceLocation PROTACTINIUM = new ResourceLocation(MODID, "protactinium");
	public static final ResourceLocation URANIUM = new ResourceLocation(MODID, "uranium");
	public static final ResourceLocation NEPTUNIUM = new ResourceLocation(MODID, "neptunium");
	public static final ResourceLocation PLUTONIUM = new ResourceLocation(MODID, "plutonium");
	public static final ResourceLocation AMERICIUM = new ResourceLocation(MODID, "americium");
	public static final ResourceLocation CURIUM = new ResourceLocation(MODID, "curium");
	public static final ResourceLocation BERKELIUM = new ResourceLocation(MODID, "berkelium");
	public static final ResourceLocation CALIFORNIUM = new ResourceLocation(MODID, "californium");
	public static final ResourceLocation EINSTEINIUM = new ResourceLocation(MODID, "einsteinium");
	public static final ResourceLocation FERMIUM = new ResourceLocation(MODID, "fermium");
	public static final ResourceLocation MENDELEVIUM = new ResourceLocation(MODID, "mendelevium");
	public static final ResourceLocation NOBELIUM = new ResourceLocation(MODID, "nobelium");
	public static final ResourceLocation LAWRENCIUM = new ResourceLocation(MODID, "lawrencium");
    
    // Alloys & Other
    public static final ResourceLocation ALUMINIUM_BRASS = new ResourceLocation(MODID, "aluminium_brass");
    public static final ResourceLocation CONSTANTAN = new ResourceLocation(MODID, "constantan");
    public static final ResourceLocation ELECTRUM = new ResourceLocation(MODID, "electrum");
    public static final ResourceLocation INVAR = new ResourceLocation(MODID, "invar");
    public static final ResourceLocation NICKEL_SILVER = new ResourceLocation(MODID, "nickel_silver"); // Copper + zinc + nickel
    public static final ResourceLocation ORICHALCUM  = new ResourceLocation(MODID, "orichalcum");
    public static final ResourceLocation RED_ALLOY = new ResourceLocation(MODID, "red_alloy"); // Copper + redstone (although not obtainable with just TFC + metallum
    public static final ResourceLocation TUNGSTEN_STEEL = new ResourceLocation(MODID, "tungsten_steel");
    public static final ResourceLocation STAINLESS_STEEL = new ResourceLocation(MODID, "stainless_steel");
    public static final ResourceLocation LOCKALLOY = new ResourceLocation(MODID, "lockalloy");
    public static final ResourceLocation MANGANIN = new ResourceLocation(MODID, "manganin");
    public static final ResourceLocation GALINSTAN = new ResourceLocation(MODID, "galinstan");
    public static final ResourceLocation CROWN_GOLD = new ResourceLocation(MODID, "crown_gold");
    public static final ResourceLocation WHITE_GOLD = new ResourceLocation(MODID, "white_gold");
    public static final ResourceLocation SOLDER = new ResourceLocation(MODID, "solder");
    public static final ResourceLocation MAGNOX = new ResourceLocation(MODID, "magnox");
    public static final ResourceLocation PLATINUM_STERLING = new ResourceLocation(MODID, "platinum_sterling");
    public static final ResourceLocation TITANIUM_GOLD = new ResourceLocation(MODID, "titanium_gold");
    public static final ResourceLocation PEWTER = new ResourceLocation(MODID, "pewter");
    public static final ResourceLocation CAST_IRON = new ResourceLocation(MODID, "cast_iron");
    public static final ResourceLocation CAST_IRON_MANGANESE = new ResourceLocation(MODID, "cast_iron_manganese");
    public static final ResourceLocation CAST_IRON_SULFUR = new ResourceLocation(MODID, "cast_iron_sulfur");
    public static final ResourceLocation CAST_IRON_PHOSPHORUS = new ResourceLocation(MODID, "cast_iron_phosphorus");

    // Fantasy
    public static final ResourceLocation MITHRIL = new ResourceLocation(MODID, "mithril");
    public static final ResourceLocation ARDITE = new ResourceLocation(MODID, "ardite");
    public static final ResourceLocation MANYULLYN = new ResourceLocation(MODID, "manyullyn");
    public static final ResourceLocation ALCHEMICAL_BRASS = new ResourceLocation(MODID, "alchemical_brass");
    public static final ResourceLocation THAUMIUM = new ResourceLocation(MODID, "thaumium");
    public static final ResourceLocation VOIDMETAL = new ResourceLocation(MODID, "voidmetal");
    public static final ResourceLocation SIGNALUM = new ResourceLocation(MODID, "signalum");
    public static final ResourceLocation LUMIUM = new ResourceLocation(MODID, "lumium");
    public static final ResourceLocation ENDERIUM = new ResourceLocation(MODID, "enderium");
    public static final ResourceLocation ADAMANTIUM = new ResourceLocation(MODID, "adamantium");
    
    // Metal + Nickel Weld
    // Vanilla TFC Metals
    public static final ResourceLocation BISMUTH_NICKEL = new ResourceLocation(MODID, "bismuth_nickel");
    public static final ResourceLocation BISMUTH_BRONZE_NICKEL = new ResourceLocation(MODID, "bismuth_bronze_nickel");
    public static final ResourceLocation BLACK_BRONZE_NICKEL = new ResourceLocation(MODID, "black_bronze_nickel");
    public static final ResourceLocation BRASS_NICKEL = new ResourceLocation(MODID, "brass_nickel");
    public static final ResourceLocation BRONZE_NICKEL = new ResourceLocation(MODID, "bronze_nickel");
    public static final ResourceLocation COPPER_NICKEL = new ResourceLocation(MODID, "copper_nickel");
    public static final ResourceLocation GOLD_NICKEL = new ResourceLocation(MODID, "gold_nickel");
    public static final ResourceLocation ROSE_GOLD_NICKEL = new ResourceLocation(MODID, "rose_gold_nickel");
    public static final ResourceLocation SILVER_NICKEL = new ResourceLocation(MODID, "silver_nickel");
    public static final ResourceLocation TIN_NICKEL = new ResourceLocation(MODID, "tin_nickel");
    public static final ResourceLocation ZINC_NICKEL = new ResourceLocation(MODID, "zinc_nickel");
    public static final ResourceLocation STERLING_SILVER_NICKEL = new ResourceLocation(MODID, "sterling_silver_nickel");
    public static final ResourceLocation WROUGHT_IRON_NICKEL = new ResourceLocation(MODID, "wrought_iron_nickel");
    public static final ResourceLocation PIG_IRON_NICKEL = new ResourceLocation(MODID, "pig_iron_nickel");
    public static final ResourceLocation STEEL_NICKEL = new ResourceLocation(MODID, "steel_nickel");
    public static final ResourceLocation PLATINUM_NICKEL = new ResourceLocation(MODID, "platinum_nickel");
    public static final ResourceLocation BLACK_STEEL_NICKEL = new ResourceLocation(MODID, "black_steel_nickel");
    public static final ResourceLocation BLUE_STEEL_NICKEL = new ResourceLocation(MODID, "blue_steel_nickel");
    public static final ResourceLocation RED_STEEL_NICKEL = new ResourceLocation(MODID, "red_steel_nickel");

    // TFC Elementia Metals
    public static final ResourceLocation LITHIUM_NICKEL = new ResourceLocation(MODID, "lithium_nickel");
    public static final ResourceLocation NATRIUM_NICKEL = new ResourceLocation(MODID, "natrium_nickel");
    public static final ResourceLocation KALIUM_NICKEL = new ResourceLocation(MODID, "kalium_nickel");
    public static final ResourceLocation RUBIDIUM_NICKEL = new ResourceLocation(MODID, "rubidium_nickel");
    public static final ResourceLocation CAESIUM_NICKEL = new ResourceLocation(MODID, "caesium_nickel");
    public static final ResourceLocation FRANCIUM_NICKEL = new ResourceLocation(MODID, "francium_nickel");
    public static final ResourceLocation BERYLLIUM_NICKEL = new ResourceLocation(MODID, "beryllium_nickel");
    public static final ResourceLocation MAGNESIUM_NICKEL = new ResourceLocation(MODID, "magnesium_nickel");
    public static final ResourceLocation CALCIUM_NICKEL = new ResourceLocation(MODID, "calcium_nickel");
    public static final ResourceLocation STRONTIUM_NICKEL = new ResourceLocation(MODID, "strontium_nickel");
    public static final ResourceLocation BARIUM_NICKEL = new ResourceLocation(MODID, "barium_nickel");
    public static final ResourceLocation RADIUM_NICKEL = new ResourceLocation(MODID, "radium_nickel");
    public static final ResourceLocation SCANDIUM_NICKEL = new ResourceLocation(MODID, "scandium_nickel");
    public static final ResourceLocation TITANIUM_NICKEL = new ResourceLocation(MODID, "titanium_nickel");
    public static final ResourceLocation VANADIUM_NICKEL = new ResourceLocation(MODID, "vanadium_nickel");
    public static final ResourceLocation CHROMIUM_NICKEL = new ResourceLocation(MODID, "chromium_nickel");
    public static final ResourceLocation MANGANESE_NICKEL = new ResourceLocation(MODID, "manganese_nickel");
    public static final ResourceLocation COBALT_NICKEL = new ResourceLocation(MODID, "cobalt_nickel");
    public static final ResourceLocation YTTRIUM_NICKEL = new ResourceLocation(MODID, "yttrium_nickel");
    public static final ResourceLocation ZIRCONIUM_NICKEL = new ResourceLocation(MODID, "zirconium_nickel");
    public static final ResourceLocation NIOBIUM_NICKEL = new ResourceLocation(MODID, "niobium_nickel");
    public static final ResourceLocation MOLYBDENUM_NICKEL = new ResourceLocation(MODID, "molybdenum_nickel");
    public static final ResourceLocation TECHNETIUM_NICKEL = new ResourceLocation(MODID, "technetium_nickel");
    public static final ResourceLocation RUTHENIUM_NICKEL = new ResourceLocation(MODID, "ruthenium_nickel");
    public static final ResourceLocation RHODIUM_NICKEL = new ResourceLocation(MODID, "rhodium_nickel");
    public static final ResourceLocation PALLADIUM_NICKEL = new ResourceLocation(MODID, "palladium_nickel");
    public static final ResourceLocation HAFNIUM_NICKEL = new ResourceLocation(MODID, "hafnium_nickel");
    public static final ResourceLocation TANTALUM_NICKEL = new ResourceLocation(MODID, "tantalum_nickel");
    public static final ResourceLocation TUNGSTEN_NICKEL = new ResourceLocation(MODID, "tungsten_nickel");
    public static final ResourceLocation RHENIUM_NICKEL = new ResourceLocation(MODID, "rhenium_nickel");
    public static final ResourceLocation OSMIUM_NICKEL = new ResourceLocation(MODID, "osmium_nickel");
    public static final ResourceLocation IRIDIUM_NICKEL = new ResourceLocation(MODID, "iridium_nickel");
    public static final ResourceLocation RUTHERFORDIUM_NICKEL = new ResourceLocation(MODID, "rutherfordium_nickel");
    public static final ResourceLocation DUBNIUM_NICKEL = new ResourceLocation(MODID, "dubnium_nickel");
    public static final ResourceLocation SEABORGIUM_NICKEL = new ResourceLocation(MODID, "seaborgium_nickel");
    public static final ResourceLocation BOHRIUM_NICKEL = new ResourceLocation(MODID, "bohrium_nickel");
    public static final ResourceLocation HASSIUM_NICKEL = new ResourceLocation(MODID, "hassium_nickel");
    public static final ResourceLocation COPERNICIUM_NICKEL = new ResourceLocation(MODID, "copernicium_nickel");
    public static final ResourceLocation ALUMINIUM_NICKEL = new ResourceLocation(MODID, "aluminium_nickel");
    public static final ResourceLocation GALLIUM_NICKEL = new ResourceLocation(MODID, "gallium_nickel");
    public static final ResourceLocation CADMIUM_NICKEL = new ResourceLocation(MODID, "cadmium_nickel");
    public static final ResourceLocation INDIUM_NICKEL = new ResourceLocation(MODID, "indium_nickel");
    public static final ResourceLocation MERCURY_NICKEL = new ResourceLocation(MODID, "mercury_nickel");
    public static final ResourceLocation THALLIUM_NICKEL = new ResourceLocation(MODID, "thallium_nickel");
    public static final ResourceLocation POLONIUM_NICKEL = new ResourceLocation(MODID, "polonium_nickel");
    public static final ResourceLocation BORON_NICKEL = new ResourceLocation(MODID, "boron_nickel");
    public static final ResourceLocation SILICIUM_NICKEL = new ResourceLocation(MODID, "silicium_nickel");
    public static final ResourceLocation GERMANIUM_NICKEL = new ResourceLocation(MODID, "germanium_nickel");
    public static final ResourceLocation ARSENIC_NICKEL = new ResourceLocation(MODID, "arsenic_nickel");
    public static final ResourceLocation ANTIMONY_NICKEL = new ResourceLocation(MODID, "antimony_nickel");
    public static final ResourceLocation TELLURIUM_NICKEL = new ResourceLocation(MODID, "tellurium_nickel");
    public static final ResourceLocation ASTATINE_NICKEL = new ResourceLocation(MODID, "astatine_nickel");
    public static final ResourceLocation LANTHANUM_NICKEL = new ResourceLocation(MODID, "lanthanum_nickel");
    public static final ResourceLocation CERIUM_NICKEL = new ResourceLocation(MODID, "cerium_nickel");
    public static final ResourceLocation PRASEODYMIUM_NICKEL = new ResourceLocation(MODID, "praseodymium_nickel");
    public static final ResourceLocation NEODYMIUM_NICKEL = new ResourceLocation(MODID, "neodymium_nickel");
    public static final ResourceLocation PROMETHIUM_NICKEL = new ResourceLocation(MODID, "prometheum_nickel");
    public static final ResourceLocation SAMARIUM_NICKEL = new ResourceLocation(MODID, "samarium_nickel");
    public static final ResourceLocation EUROPIUM_NICKEL = new ResourceLocation(MODID, "europium_nickel");
    public static final ResourceLocation GADOLINIUM_NICKEL = new ResourceLocation(MODID, "gadolinium_nickel");
    public static final ResourceLocation TERBIUM_NICKEL = new ResourceLocation(MODID, "terbium_nickel");
    public static final ResourceLocation DYSPROSIUM_NICKEL = new ResourceLocation(MODID, "dysprosium_nickel");
    public static final ResourceLocation HOLMIUM_NICKEL = new ResourceLocation(MODID, "holmium_nickel");
    public static final ResourceLocation ERBIUM_NICKEL = new ResourceLocation(MODID, "erbium_nickel");
    public static final ResourceLocation THULIUM_NICKEL = new ResourceLocation(MODID, "thulium_nickel");
    public static final ResourceLocation YTTERBIUM_NICKEL = new ResourceLocation(MODID, "ytterbium_nickel");
    public static final ResourceLocation LUTETIUM_NICKEL = new ResourceLocation(MODID, "lutetium_nickel");
    public static final ResourceLocation ACTINIUM_NICKEL = new ResourceLocation(MODID, "actinium_nickel");
    public static final ResourceLocation THORIUM_NICKEL = new ResourceLocation(MODID, "thorium_nickel");
    public static final ResourceLocation PROTACTINIUM_NICKEL = new ResourceLocation(MODID, "protactinium_nickel");
    public static final ResourceLocation URANIUM_NICKEL = new ResourceLocation(MODID, "uranium_nickel");
    public static final ResourceLocation NEPTUNIUM_NICKEL = new ResourceLocation(MODID, "neptunium_nickel");
    public static final ResourceLocation PLUTONIUM_NICKEL = new ResourceLocation(MODID, "plutonium_nickel");
    public static final ResourceLocation AMERICIUM_NICKEL = new ResourceLocation(MODID, "americium_nickel");
    public static final ResourceLocation CURIUM_NICKEL = new ResourceLocation(MODID, "curium_nickel");
    public static final ResourceLocation BERKELIUM_NICKEL = new ResourceLocation(MODID, "berkelium_nickel");
    public static final ResourceLocation CALIFORNIUM_NICKEL = new ResourceLocation(MODID, "californium_nickel");
    public static final ResourceLocation EINSTEINIUM_NICKEL = new ResourceLocation(MODID, "einsteinium_nickel");
    public static final ResourceLocation FERMIUM_NICKEL = new ResourceLocation(MODID, "fermium_nickel");
    public static final ResourceLocation MENDELEVIUM_NICKEL = new ResourceLocation(MODID, "mendelevium_nickel");
    public static final ResourceLocation NOBELIUM_NICKEL = new ResourceLocation(MODID, "nobelium_nickel");
    public static final ResourceLocation LAWRENCIUM_NICKEL = new ResourceLocation(MODID, "lawrencium_nickel");
    public static final ResourceLocation ALUMINIUM_BRASS_NICKEL = new ResourceLocation(MODID, "aluminium_brass_nickel");
    public static final ResourceLocation CONSTANTAN_NICKEL = new ResourceLocation(MODID, "constantan_nickel");
    public static final ResourceLocation ELECTRUM_NICKEL = new ResourceLocation(MODID, "electrum_nickel");
    public static final ResourceLocation INVAR_NICKEL = new ResourceLocation(MODID, "invar_nickel");
    public static final ResourceLocation NICKEL_SILVER_NICKEL = new ResourceLocation(MODID, "nickel_silver_nickel");
    public static final ResourceLocation ORICHALCUM_NICKEL = new ResourceLocation(MODID, "orichalcum_nickel");
    public static final ResourceLocation RED_ALLOY_NICKEL = new ResourceLocation(MODID, "red_alloy_nickel");
    public static final ResourceLocation TUNGSTEN_STEEL_NICKEL = new ResourceLocation(MODID, "tungsten_steel_nickel");
    public static final ResourceLocation STAINLESS_STEEL_NICKEL = new ResourceLocation(MODID, "stainless_steel_nickel");
    public static final ResourceLocation LOCKALLOY_NICKEL = new ResourceLocation(MODID, "lockalloy_nickel");
    public static final ResourceLocation MANGANIN_NICKEL = new ResourceLocation(MODID, "manganin_nickel");
    public static final ResourceLocation GALINSTAN_NICKEL = new ResourceLocation(MODID, "galinstan_nickel");
    public static final ResourceLocation CROWN_GOLD_NICKEL = new ResourceLocation(MODID, "crown_gold_nickel");
    public static final ResourceLocation WHITE_GOLD_NICKEL = new ResourceLocation(MODID, "white_gold_nickel");
    public static final ResourceLocation SOLDER_NICKEL = new ResourceLocation(MODID, "solder_nickel");
    public static final ResourceLocation MAGNOX_NICKEL = new ResourceLocation(MODID, "magnox_nickel");
    public static final ResourceLocation PLATINUM_STERLING_NICKEL = new ResourceLocation(MODID, "platinum_sterling_nickel");
    public static final ResourceLocation TITANIUM_GOLD_NICKEL = new ResourceLocation(MODID, "titanium_gold_nickel");
    public static final ResourceLocation PEWTER_NICKEL = new ResourceLocation(MODID, "pewter_nickel");
    public static final ResourceLocation CAST_IRON_NICKEL = new ResourceLocation(MODID, "cast_iron_nickel");
    public static final ResourceLocation MITHRIL_NICKEL = new ResourceLocation(MODID, "mithril_nickel");
    public static final ResourceLocation ARDITE_NICKEL = new ResourceLocation(MODID, "ardite_nickel");
    public static final ResourceLocation MANYULLYN_NICKEL = new ResourceLocation(MODID, "manyullyn_nickel");
    public static final ResourceLocation ALCHEMICAL_BRASS_NICKEL = new ResourceLocation(MODID, "alchemical_brass_nickel");
    public static final ResourceLocation THAUMIUM_NICKEL = new ResourceLocation(MODID, "thaumium_nickel");
    public static final ResourceLocation VOIDMETAL_NICKEL = new ResourceLocation(MODID, "voidmetal_nickel");
    public static final ResourceLocation SIGNALUM_NICKEL = new ResourceLocation(MODID, "signalum_nickel");
    public static final ResourceLocation LUMIUM_NICKEL = new ResourceLocation(MODID, "lumium_nickel");
    public static final ResourceLocation ENDERIUM_NICKEL = new ResourceLocation(MODID, "enderium_nickel");
    public static final ResourceLocation ADAMANTIUM_NICKEL = new ResourceLocation(MODID, "adamantium_nickel");

    // Weak Damascus Metals
    // Vanilla TFC Metals
    public static final ResourceLocation WEAK_DAMASCUS_BISMUTH = new ResourceLocation(MODID, "weak_damascus_bismuth");
    public static final ResourceLocation WEAK_DAMASCUS_BISMUTH_BRONZE = new ResourceLocation(MODID, "weak_damascus_bismuth_bronze");
    public static final ResourceLocation WEAK_DAMASCUS_BLACK_BRONZE = new ResourceLocation(MODID, "weak_damascus_black_bronze");
    public static final ResourceLocation WEAK_DAMASCUS_BRASS = new ResourceLocation(MODID, "weak_damascus_brass");
    public static final ResourceLocation WEAK_DAMASCUS_BRONZE = new ResourceLocation(MODID, "weak_damascus_bronze");
    public static final ResourceLocation WEAK_DAMASCUS_COPPER = new ResourceLocation(MODID, "weak_damascus_copper");
    public static final ResourceLocation WEAK_DAMASCUS_GOLD = new ResourceLocation(MODID, "weak_damascus_gold");
    public static final ResourceLocation WEAK_DAMASCUS_ROSE_GOLD = new ResourceLocation(MODID, "weak_damascus_rose_gold");
    public static final ResourceLocation WEAK_DAMASCUS_SILVER = new ResourceLocation(MODID, "weak_damascus_silver");
    public static final ResourceLocation WEAK_DAMASCUS_TIN = new ResourceLocation(MODID, "weak_damascus_tin");
    public static final ResourceLocation WEAK_DAMASCUS_ZINC = new ResourceLocation(MODID, "weak_damascus_zinc");
    public static final ResourceLocation WEAK_DAMASCUS_STERLING_SILVER = new ResourceLocation(MODID, "weak_damascus_sterling_silver");
    public static final ResourceLocation WEAK_DAMASCUS_WROUGHT_IRON = new ResourceLocation(MODID, "weak_damascus_wrought_iron");
    public static final ResourceLocation WEAK_DAMASCUS_PIG_IRON = new ResourceLocation(MODID, "weak_damascus_pig_iron");
    public static final ResourceLocation WEAK_DAMASCUS_STEEL = new ResourceLocation(MODID, "weak_damascus_steel");
    public static final ResourceLocation WEAK_DAMASCUS_PLATINUM = new ResourceLocation(MODID, "weak_damascus_platinum");
    public static final ResourceLocation WEAK_DAMASCUS_BLACK_STEEL = new ResourceLocation(MODID, "weak_damascus_black_steel");
    public static final ResourceLocation WEAK_DAMASCUS_BLUE_STEEL = new ResourceLocation(MODID, "weak_damascus_blue_steel");
    public static final ResourceLocation WEAK_DAMASCUS_RED_STEEL = new ResourceLocation(MODID, "weak_damascus_red_steel");
    
    // TFC Elementia Metals
    public static final ResourceLocation WEAK_DAMASCUS_LITHIUM = new ResourceLocation(MODID, "weak_damascus_lithium");
    public static final ResourceLocation WEAK_DAMASCUS_NATRIUM = new ResourceLocation(MODID, "weak_damascus_natrium");
    public static final ResourceLocation WEAK_DAMASCUS_KALIUM = new ResourceLocation(MODID, "weak_damascus_kalium");
    public static final ResourceLocation WEAK_DAMASCUS_RUBIDIUM = new ResourceLocation(MODID, "weak_damascus_rubidium");
    public static final ResourceLocation WEAK_DAMASCUS_CAESIUM = new ResourceLocation(MODID, "weak_damascus_caesium");
    public static final ResourceLocation WEAK_DAMASCUS_FRANCIUM = new ResourceLocation(MODID, "weak_damascus_francium");
    public static final ResourceLocation WEAK_DAMASCUS_BERYLLIUM = new ResourceLocation(MODID, "weak_damascus_beryllium");
    public static final ResourceLocation WEAK_DAMASCUS_MAGNESIUM = new ResourceLocation(MODID, "weak_damascus_magnesium");
    public static final ResourceLocation WEAK_DAMASCUS_CALCIUM = new ResourceLocation(MODID, "weak_damascus_calcium");
    public static final ResourceLocation WEAK_DAMASCUS_STRONTIUM = new ResourceLocation(MODID, "weak_damascus_strontium");
    public static final ResourceLocation WEAK_DAMASCUS_BARIUM = new ResourceLocation(MODID, "weak_damascus_barium");
    public static final ResourceLocation WEAK_DAMASCUS_RADIUM = new ResourceLocation(MODID, "weak_damascus_radium");
    public static final ResourceLocation WEAK_DAMASCUS_SCANDIUM = new ResourceLocation(MODID, "weak_damascus_scandium");
    public static final ResourceLocation WEAK_DAMASCUS_TITANIUM = new ResourceLocation(MODID, "weak_damascus_titanium");
    public static final ResourceLocation WEAK_DAMASCUS_VANADIUM = new ResourceLocation(MODID, "weak_damascus_vanadium");
    public static final ResourceLocation WEAK_DAMASCUS_CHROMIUM = new ResourceLocation(MODID, "weak_damascus_chromium");
    public static final ResourceLocation WEAK_DAMASCUS_MANGANESE = new ResourceLocation(MODID, "weak_damascus_manganese");
    public static final ResourceLocation WEAK_DAMASCUS_COBALT = new ResourceLocation(MODID, "weak_damascus_cobalt");
    public static final ResourceLocation WEAK_DAMASCUS_YTTRIUM = new ResourceLocation(MODID, "weak_damascus_yttrium");
    public static final ResourceLocation WEAK_DAMASCUS_ZIRCONIUM = new ResourceLocation(MODID, "weak_damascus_zirconium");
    public static final ResourceLocation WEAK_DAMASCUS_NIOBIUM = new ResourceLocation(MODID, "weak_damascus_niobium");
    public static final ResourceLocation WEAK_DAMASCUS_MOLYBDENUM = new ResourceLocation(MODID, "weak_damascus_molybdenum");
    public static final ResourceLocation WEAK_DAMASCUS_TECHNETIUM = new ResourceLocation(MODID, "weak_damascus_technetium");
    public static final ResourceLocation WEAK_DAMASCUS_RUTHENIUM = new ResourceLocation(MODID, "weak_damascus_ruthenium");
    public static final ResourceLocation WEAK_DAMASCUS_RHODIUM = new ResourceLocation(MODID, "weak_damascus_rhodium");
    public static final ResourceLocation WEAK_DAMASCUS_PALLADIUM = new ResourceLocation(MODID, "weak_damascus_palladium");
    public static final ResourceLocation WEAK_DAMASCUS_HAFNIUM = new ResourceLocation(MODID, "weak_damascus_hafnium");
    public static final ResourceLocation WEAK_DAMASCUS_TANTALUM = new ResourceLocation(MODID, "weak_damascus_tantalum");
    public static final ResourceLocation WEAK_DAMASCUS_TUNGSTEN = new ResourceLocation(MODID, "weak_damascus_tungsten");
    public static final ResourceLocation WEAK_DAMASCUS_RHENIUM = new ResourceLocation(MODID, "weak_damascus_rhenium");
    public static final ResourceLocation WEAK_DAMASCUS_OSMIUM = new ResourceLocation(MODID, "weak_damascus_osmium");
    public static final ResourceLocation WEAK_DAMASCUS_IRIDIUM = new ResourceLocation(MODID, "weak_damascus_iridium");
    public static final ResourceLocation WEAK_DAMASCUS_RUTHERFORDIUM = new ResourceLocation(MODID, "weak_damascus_rutherfordium");
    public static final ResourceLocation WEAK_DAMASCUS_DUBNIUM = new ResourceLocation(MODID, "weak_damascus_dubnium");
    public static final ResourceLocation WEAK_DAMASCUS_SEABORGIUM = new ResourceLocation(MODID, "weak_damascus_seaborgium");
    public static final ResourceLocation WEAK_DAMASCUS_BOHRIUM = new ResourceLocation(MODID, "weak_damascus_bohrium");
    public static final ResourceLocation WEAK_DAMASCUS_HASSIUM = new ResourceLocation(MODID, "weak_damascus_hassium");
    public static final ResourceLocation WEAK_DAMASCUS_COPERNICIUM = new ResourceLocation(MODID, "weak_damascus_copernicium");
    public static final ResourceLocation WEAK_DAMASCUS_ALUMINIUM = new ResourceLocation(MODID, "weak_damascus_aluminium");
    public static final ResourceLocation WEAK_DAMASCUS_GALLIUM = new ResourceLocation(MODID, "weak_damascus_gallium");
    public static final ResourceLocation WEAK_DAMASCUS_CADMIUM = new ResourceLocation(MODID, "weak_damascus_cadmium");
    public static final ResourceLocation WEAK_DAMASCUS_INDIUM = new ResourceLocation(MODID, "weak_damascus_indium");
    public static final ResourceLocation WEAK_DAMASCUS_MERCURY = new ResourceLocation(MODID, "weak_damascus_mercury");
    public static final ResourceLocation WEAK_DAMASCUS_THALLIUM = new ResourceLocation(MODID, "weak_damascus_thallium");
    public static final ResourceLocation WEAK_DAMASCUS_POLONIUM = new ResourceLocation(MODID, "weak_damascus_polonium");
    public static final ResourceLocation WEAK_DAMASCUS_BORON = new ResourceLocation(MODID, "weak_damascus_boron");
    public static final ResourceLocation WEAK_DAMASCUS_SILICIUM = new ResourceLocation(MODID, "weak_damascus_silicium");
    public static final ResourceLocation WEAK_DAMASCUS_GERMANIUM = new ResourceLocation(MODID, "weak_damascus_germanium");
    public static final ResourceLocation WEAK_DAMASCUS_ARSENIC = new ResourceLocation(MODID, "weak_damascus_arsenic");
    public static final ResourceLocation WEAK_DAMASCUS_ANTIMONY = new ResourceLocation(MODID, "weak_damascus_antimony");
    public static final ResourceLocation WEAK_DAMASCUS_TELLURIUM = new ResourceLocation(MODID, "weak_damascus_tellurium");
    public static final ResourceLocation WEAK_DAMASCUS_ASTATINE = new ResourceLocation(MODID, "weak_damascus_astatine");
    public static final ResourceLocation WEAK_DAMASCUS_LANTHANUM = new ResourceLocation(MODID, "weak_damascus_lanthanum");
    public static final ResourceLocation WEAK_DAMASCUS_CERIUM = new ResourceLocation(MODID, "weak_damascus_cerium");
    public static final ResourceLocation WEAK_DAMASCUS_PRASEODYMIUM = new ResourceLocation(MODID, "weak_damascus_praseodymium");
    public static final ResourceLocation WEAK_DAMASCUS_NEODYMIUM = new ResourceLocation(MODID, "weak_damascus_neodymium");
    public static final ResourceLocation WEAK_DAMASCUS_PROMETHIUM = new ResourceLocation(MODID, "weak_damascus_prometheum");
    public static final ResourceLocation WEAK_DAMASCUS_SAMARIUM = new ResourceLocation(MODID, "weak_damascus_samarium");
    public static final ResourceLocation WEAK_DAMASCUS_EUROPIUM = new ResourceLocation(MODID, "weak_damascus_europium");
    public static final ResourceLocation WEAK_DAMASCUS_GADOLINIUM = new ResourceLocation(MODID, "weak_damascus_gadolinium");
    public static final ResourceLocation WEAK_DAMASCUS_TERBIUM = new ResourceLocation(MODID, "weak_damascus_terbium");
    public static final ResourceLocation WEAK_DAMASCUS_DYSPROSIUM = new ResourceLocation(MODID, "weak_damascus_dysprosium");
    public static final ResourceLocation WEAK_DAMASCUS_HOLMIUM = new ResourceLocation(MODID, "weak_damascus_holmium");
    public static final ResourceLocation WEAK_DAMASCUS_ERBIUM = new ResourceLocation(MODID, "weak_damascus_erbium");
    public static final ResourceLocation WEAK_DAMASCUS_THULIUM = new ResourceLocation(MODID, "weak_damascus_thulium");
    public static final ResourceLocation WEAK_DAMASCUS_YTTERBIUM = new ResourceLocation(MODID, "weak_damascus_ytterbium");
    public static final ResourceLocation WEAK_DAMASCUS_LUTETIUM = new ResourceLocation(MODID, "weak_damascus_lutetium");
    public static final ResourceLocation WEAK_DAMASCUS_ACTINIUM = new ResourceLocation(MODID, "weak_damascus_actinium");
    public static final ResourceLocation WEAK_DAMASCUS_THORIUM = new ResourceLocation(MODID, "weak_damascus_thorium");
    public static final ResourceLocation WEAK_DAMASCUS_PROTACTINIUM = new ResourceLocation(MODID, "weak_damascus_protactinium");
    public static final ResourceLocation WEAK_DAMASCUS_URANIUM = new ResourceLocation(MODID, "weak_damascus_uranium");
    public static final ResourceLocation WEAK_DAMASCUS_NEPTUNIUM = new ResourceLocation(MODID, "weak_damascus_neptunium");
    public static final ResourceLocation WEAK_DAMASCUS_PLUTONIUM = new ResourceLocation(MODID, "weak_damascus_plutonium");
    public static final ResourceLocation WEAK_DAMASCUS_AMERICIUM = new ResourceLocation(MODID, "weak_damascus_americium");
    public static final ResourceLocation WEAK_DAMASCUS_CURIUM = new ResourceLocation(MODID, "weak_damascus_curium");
    public static final ResourceLocation WEAK_DAMASCUS_BERKELIUM = new ResourceLocation(MODID, "weak_damascus_berkelium");
    public static final ResourceLocation WEAK_DAMASCUS_CALIFORNIUM = new ResourceLocation(MODID, "weak_damascus_californium");
    public static final ResourceLocation WEAK_DAMASCUS_EINSTEINIUM = new ResourceLocation(MODID, "weak_damascus_einsteinium");
    public static final ResourceLocation WEAK_DAMASCUS_FERMIUM = new ResourceLocation(MODID, "weak_damascus_fermium");
    public static final ResourceLocation WEAK_DAMASCUS_MENDELEVIUM = new ResourceLocation(MODID, "weak_damascus_mendelevium");
    public static final ResourceLocation WEAK_DAMASCUS_NOBELIUM = new ResourceLocation(MODID, "weak_damascus_nobelium");
    public static final ResourceLocation WEAK_DAMASCUS_LAWRENCIUM = new ResourceLocation(MODID, "weak_damascus_lawrencium");
    public static final ResourceLocation WEAK_DAMASCUS_ALUMINIUM_BRASS = new ResourceLocation(MODID, "weak_damascus_aluminium_brass");
    public static final ResourceLocation WEAK_DAMASCUS_CONSTANTAN = new ResourceLocation(MODID, "weak_damascus_constantan");
    public static final ResourceLocation WEAK_DAMASCUS_ELECTRUM = new ResourceLocation(MODID, "weak_damascus_electrum");
    public static final ResourceLocation WEAK_DAMASCUS_INVAR = new ResourceLocation(MODID, "weak_damascus_invar");
    public static final ResourceLocation WEAK_DAMASCUS_NICKEL_SILVER = new ResourceLocation(MODID, "weak_damascus_nickel_silver");
    public static final ResourceLocation WEAK_DAMASCUS_ORICHALCUM = new ResourceLocation(MODID, "weak_damascus_orichalcum");
    public static final ResourceLocation WEAK_DAMASCUS_RED_ALLOY = new ResourceLocation(MODID, "weak_damascus_red_alloy");
    public static final ResourceLocation WEAK_DAMASCUS_TUNGSTEN_STEEL = new ResourceLocation(MODID, "weak_damascus_tungsten_steel");
    public static final ResourceLocation WEAK_DAMASCUS_STAINLESS_STEEL = new ResourceLocation(MODID, "weak_damascus_stainless_steel");
    public static final ResourceLocation WEAK_DAMASCUS_LOCKALLOY = new ResourceLocation(MODID, "weak_damascus_lockalloy");
    public static final ResourceLocation WEAK_DAMASCUS_MANGANIN = new ResourceLocation(MODID, "weak_damascus_manganin");
    public static final ResourceLocation WEAK_DAMASCUS_GALINSTAN = new ResourceLocation(MODID, "weak_damascus_galinstan");
    public static final ResourceLocation WEAK_DAMASCUS_CROWN_GOLD = new ResourceLocation(MODID, "weak_damascus_crown_gold");
    public static final ResourceLocation WEAK_DAMASCUS_WHITE_GOLD = new ResourceLocation(MODID, "weak_damascus_white_gold");
    public static final ResourceLocation WEAK_DAMASCUS_SOLDER = new ResourceLocation(MODID, "weak_damascus_solder");
    public static final ResourceLocation WEAK_DAMASCUS_MAGNOX = new ResourceLocation(MODID, "weak_damascus_magnox");
    public static final ResourceLocation WEAK_DAMASCUS_PLATINUM_STERLING = new ResourceLocation(MODID, "weak_damascus_platinum_sterling");
    public static final ResourceLocation WEAK_DAMASCUS_TITANIUM_GOLD = new ResourceLocation(MODID, "weak_damascus_titanium_gold");
    public static final ResourceLocation WEAK_DAMASCUS_PEWTER = new ResourceLocation(MODID, "weak_damascus_pewter");
    public static final ResourceLocation WEAK_DAMASCUS_CAST_IRON = new ResourceLocation(MODID, "weak_damascus_cast_iron");
    public static final ResourceLocation WEAK_DAMASCUS_MITHRIL = new ResourceLocation(MODID, "weak_damascus_mithril");
    public static final ResourceLocation WEAK_DAMASCUS_ARDITE = new ResourceLocation(MODID, "weak_damascus_ardite");
    public static final ResourceLocation WEAK_DAMASCUS_MANYULLYN = new ResourceLocation(MODID, "weak_damascus_manyullyn");
    public static final ResourceLocation WEAK_DAMASCUS_ALCHEMICAL_BRASS = new ResourceLocation(MODID, "weak_damascus_alchemical_brass");
    public static final ResourceLocation WEAK_DAMASCUS_THAUMIUM = new ResourceLocation(MODID, "weak_damascus_thaumium");
    public static final ResourceLocation WEAK_DAMASCUS_VOIDMETAL = new ResourceLocation(MODID, "weak_damascus_voidmetal");
    public static final ResourceLocation WEAK_DAMASCUS_SIGNALUM = new ResourceLocation(MODID, "weak_damascus_signalum");
    public static final ResourceLocation WEAK_DAMASCUS_LUMIUM = new ResourceLocation(MODID, "weak_damascus_lumium");
    public static final ResourceLocation WEAK_DAMASCUS_ENDERIUM = new ResourceLocation(MODID, "weak_damascus_enderium");
    public static final ResourceLocation WEAK_DAMASCUS_ADAMANTIUM = new ResourceLocation(MODID, "weak_damascus_adamantium");
    
    // Damascus Metals
    // Vanilla TFC Metals
    public static final ResourceLocation DAMASCUS_BISMUTH = new ResourceLocation(MODID, "damascus_bismuth");
    public static final ResourceLocation DAMASCUS_BISMUTH_BRONZE = new ResourceLocation(MODID, "damascus_bismuth_bronze");
    public static final ResourceLocation DAMASCUS_BLACK_BRONZE = new ResourceLocation(MODID, "damascus_black_bronze");
    public static final ResourceLocation DAMASCUS_BRASS = new ResourceLocation(MODID, "damascus_brass");
    public static final ResourceLocation DAMASCUS_BRONZE = new ResourceLocation(MODID, "damascus_bronze");
    public static final ResourceLocation DAMASCUS_COPPER = new ResourceLocation(MODID, "damascus_copper");
    public static final ResourceLocation DAMASCUS_GOLD = new ResourceLocation(MODID, "damascus_gold");
    public static final ResourceLocation DAMASCUS_ROSE_GOLD = new ResourceLocation(MODID, "damascus_rose_gold");
    public static final ResourceLocation DAMASCUS_SILVER = new ResourceLocation(MODID, "damascus_silver");
    public static final ResourceLocation DAMASCUS_TIN = new ResourceLocation(MODID, "damascus_tin");
    public static final ResourceLocation DAMASCUS_ZINC = new ResourceLocation(MODID, "damascus_zinc");
    public static final ResourceLocation DAMASCUS_STERLING_SILVER = new ResourceLocation(MODID, "damascus_sterling_silver");
    public static final ResourceLocation DAMASCUS_WROUGHT_IRON = new ResourceLocation(MODID, "damascus_wrought_iron");
    public static final ResourceLocation DAMASCUS_PIG_IRON = new ResourceLocation(MODID, "damascus_pig_iron");
    public static final ResourceLocation DAMASCUS_STEEL = new ResourceLocation(MODID, "damascus_steel");
    public static final ResourceLocation DAMASCUS_PLATINUM = new ResourceLocation(MODID, "damascus_platinum");
    public static final ResourceLocation DAMASCUS_BLACK_STEEL = new ResourceLocation(MODID, "damascus_black_steel");
    public static final ResourceLocation DAMASCUS_BLUE_STEEL = new ResourceLocation(MODID, "damascus_blue_steel");
    public static final ResourceLocation DAMASCUS_RED_STEEL = new ResourceLocation(MODID, "damascus_red_steel");
    
    // TFC Elementia Metals
    public static final ResourceLocation DAMASCUS_LITHIUM = new ResourceLocation(MODID, "damascus_lithium");
    public static final ResourceLocation DAMASCUS_NATRIUM = new ResourceLocation(MODID, "damascus_natrium");
    public static final ResourceLocation DAMASCUS_KALIUM = new ResourceLocation(MODID, "damascus_kalium");
    public static final ResourceLocation DAMASCUS_RUBIDIUM = new ResourceLocation(MODID, "damascus_rubidium");
    public static final ResourceLocation DAMASCUS_CAESIUM = new ResourceLocation(MODID, "damascus_caesium");
    public static final ResourceLocation DAMASCUS_FRANCIUM = new ResourceLocation(MODID, "damascus_francium");
    public static final ResourceLocation DAMASCUS_BERYLLIUM = new ResourceLocation(MODID, "damascus_beryllium");
    public static final ResourceLocation DAMASCUS_MAGNESIUM = new ResourceLocation(MODID, "damascus_magnesium");
    public static final ResourceLocation DAMASCUS_CALCIUM = new ResourceLocation(MODID, "damascus_calcium");
    public static final ResourceLocation DAMASCUS_STRONTIUM = new ResourceLocation(MODID, "damascus_strontium");
    public static final ResourceLocation DAMASCUS_BARIUM = new ResourceLocation(MODID, "damascus_barium");
    public static final ResourceLocation DAMASCUS_RADIUM = new ResourceLocation(MODID, "damascus_radium");
    public static final ResourceLocation DAMASCUS_SCANDIUM = new ResourceLocation(MODID, "damascus_scandium");
    public static final ResourceLocation DAMASCUS_TITANIUM = new ResourceLocation(MODID, "damascus_titanium");
    public static final ResourceLocation DAMASCUS_VANADIUM = new ResourceLocation(MODID, "damascus_vanadium");
    public static final ResourceLocation DAMASCUS_CHROMIUM = new ResourceLocation(MODID, "damascus_chromium");
    public static final ResourceLocation DAMASCUS_MANGANESE = new ResourceLocation(MODID, "damascus_manganese");
    public static final ResourceLocation DAMASCUS_COBALT = new ResourceLocation(MODID, "damascus_cobalt");
    public static final ResourceLocation DAMASCUS_YTTRIUM = new ResourceLocation(MODID, "damascus_yttrium");
    public static final ResourceLocation DAMASCUS_ZIRCONIUM = new ResourceLocation(MODID, "damascus_zirconium");
    public static final ResourceLocation DAMASCUS_NIOBIUM = new ResourceLocation(MODID, "damascus_niobium");
    public static final ResourceLocation DAMASCUS_MOLYBDENUM = new ResourceLocation(MODID, "damascus_molybdenum");
    public static final ResourceLocation DAMASCUS_TECHNETIUM = new ResourceLocation(MODID, "damascus_technetium");
    public static final ResourceLocation DAMASCUS_RUTHENIUM = new ResourceLocation(MODID, "damascus_ruthenium");
    public static final ResourceLocation DAMASCUS_RHODIUM = new ResourceLocation(MODID, "damascus_rhodium");
    public static final ResourceLocation DAMASCUS_PALLADIUM = new ResourceLocation(MODID, "damascus_palladium");
    public static final ResourceLocation DAMASCUS_HAFNIUM = new ResourceLocation(MODID, "damascus_hafnium");
    public static final ResourceLocation DAMASCUS_TANTALUM = new ResourceLocation(MODID, "damascus_tantalum");
    public static final ResourceLocation DAMASCUS_TUNGSTEN = new ResourceLocation(MODID, "damascus_tungsten");
    public static final ResourceLocation DAMASCUS_RHENIUM = new ResourceLocation(MODID, "damascus_rhenium");
    public static final ResourceLocation DAMASCUS_OSMIUM = new ResourceLocation(MODID, "damascus_osmium");
    public static final ResourceLocation DAMASCUS_IRIDIUM = new ResourceLocation(MODID, "damascus_iridium");
    public static final ResourceLocation DAMASCUS_RUTHERFORDIUM = new ResourceLocation(MODID, "damascus_rutherfordium");
    public static final ResourceLocation DAMASCUS_DUBNIUM = new ResourceLocation(MODID, "damascus_dubnium");
    public static final ResourceLocation DAMASCUS_SEABORGIUM = new ResourceLocation(MODID, "damascus_seaborgium");
    public static final ResourceLocation DAMASCUS_BOHRIUM = new ResourceLocation(MODID, "damascus_bohrium");
    public static final ResourceLocation DAMASCUS_HASSIUM = new ResourceLocation(MODID, "damascus_hassium");
    public static final ResourceLocation DAMASCUS_COPERNICIUM = new ResourceLocation(MODID, "damascus_copernicium");
    public static final ResourceLocation DAMASCUS_ALUMINIUM = new ResourceLocation(MODID, "damascus_aluminium");
    public static final ResourceLocation DAMASCUS_GALLIUM = new ResourceLocation(MODID, "damascus_gallium");
    public static final ResourceLocation DAMASCUS_CADMIUM = new ResourceLocation(MODID, "damascus_cadmium");
    public static final ResourceLocation DAMASCUS_INDIUM = new ResourceLocation(MODID, "damascus_indium");
    public static final ResourceLocation DAMASCUS_MERCURY = new ResourceLocation(MODID, "damascus_mercury");
    public static final ResourceLocation DAMASCUS_THALLIUM = new ResourceLocation(MODID, "damascus_thallium");
    public static final ResourceLocation DAMASCUS_POLONIUM = new ResourceLocation(MODID, "damascus_polonium");
    public static final ResourceLocation DAMASCUS_BORON = new ResourceLocation(MODID, "damascus_boron");
    public static final ResourceLocation DAMASCUS_SILICIUM = new ResourceLocation(MODID, "damascus_silicium");
    public static final ResourceLocation DAMASCUS_GERMANIUM = new ResourceLocation(MODID, "damascus_germanium");
    public static final ResourceLocation DAMASCUS_ARSENIC = new ResourceLocation(MODID, "damascus_arsenic");
    public static final ResourceLocation DAMASCUS_ANTIMONY = new ResourceLocation(MODID, "damascus_antimony");
    public static final ResourceLocation DAMASCUS_TELLURIUM = new ResourceLocation(MODID, "damascus_tellurium");
    public static final ResourceLocation DAMASCUS_ASTATINE = new ResourceLocation(MODID, "damascus_astatine");
    public static final ResourceLocation DAMASCUS_LANTHANUM = new ResourceLocation(MODID, "damascus_lanthanum");
    public static final ResourceLocation DAMASCUS_CERIUM = new ResourceLocation(MODID, "damascus_cerium");
    public static final ResourceLocation DAMASCUS_PRASEODYMIUM = new ResourceLocation(MODID, "damascus_praseodymium");
    public static final ResourceLocation DAMASCUS_NEODYMIUM = new ResourceLocation(MODID, "damascus_neodymium");
    public static final ResourceLocation DAMASCUS_PROMETHIUM = new ResourceLocation(MODID, "damascus_prometheum");
    public static final ResourceLocation DAMASCUS_SAMARIUM = new ResourceLocation(MODID, "damascus_samarium");
    public static final ResourceLocation DAMASCUS_EUROPIUM = new ResourceLocation(MODID, "damascus_europium");
    public static final ResourceLocation DAMASCUS_GADOLINIUM = new ResourceLocation(MODID, "damascus_gadolinium");
    public static final ResourceLocation DAMASCUS_TERBIUM = new ResourceLocation(MODID, "damascus_terbium");
    public static final ResourceLocation DAMASCUS_DYSPROSIUM = new ResourceLocation(MODID, "damascus_dysprosium");
    public static final ResourceLocation DAMASCUS_HOLMIUM = new ResourceLocation(MODID, "damascus_holmium");
    public static final ResourceLocation DAMASCUS_ERBIUM = new ResourceLocation(MODID, "damascus_erbium");
    public static final ResourceLocation DAMASCUS_THULIUM = new ResourceLocation(MODID, "damascus_thulium");
    public static final ResourceLocation DAMASCUS_YTTERBIUM = new ResourceLocation(MODID, "damascus_ytterbium");
    public static final ResourceLocation DAMASCUS_LUTETIUM = new ResourceLocation(MODID, "damascus_lutetium");
    public static final ResourceLocation DAMASCUS_ACTINIUM = new ResourceLocation(MODID, "damascus_actinium");
    public static final ResourceLocation DAMASCUS_THORIUM = new ResourceLocation(MODID, "damascus_thorium");
    public static final ResourceLocation DAMASCUS_PROTACTINIUM = new ResourceLocation(MODID, "damascus_protactinium");
    public static final ResourceLocation DAMASCUS_URANIUM = new ResourceLocation(MODID, "damascus_uranium");
    public static final ResourceLocation DAMASCUS_NEPTUNIUM = new ResourceLocation(MODID, "damascus_neptunium");
    public static final ResourceLocation DAMASCUS_PLUTONIUM = new ResourceLocation(MODID, "damascus_plutonium");
    public static final ResourceLocation DAMASCUS_AMERICIUM = new ResourceLocation(MODID, "damascus_americium");
    public static final ResourceLocation DAMASCUS_CURIUM = new ResourceLocation(MODID, "damascus_curium");
    public static final ResourceLocation DAMASCUS_BERKELIUM = new ResourceLocation(MODID, "damascus_berkelium");
    public static final ResourceLocation DAMASCUS_CALIFORNIUM = new ResourceLocation(MODID, "damascus_californium");
    public static final ResourceLocation DAMASCUS_EINSTEINIUM = new ResourceLocation(MODID, "damascus_einsteinium");
    public static final ResourceLocation DAMASCUS_FERMIUM = new ResourceLocation(MODID, "damascus_fermium");
    public static final ResourceLocation DAMASCUS_MENDELEVIUM = new ResourceLocation(MODID, "damascus_mendelevium");
    public static final ResourceLocation DAMASCUS_NOBELIUM = new ResourceLocation(MODID, "damascus_nobelium");
    public static final ResourceLocation DAMASCUS_LAWRENCIUM = new ResourceLocation(MODID, "damascus_lawrencium");
    public static final ResourceLocation DAMASCUS_ALUMINIUM_BRASS = new ResourceLocation(MODID, "damascus_aluminium_brass");
    public static final ResourceLocation DAMASCUS_CONSTANTAN = new ResourceLocation(MODID, "damascus_constantan");
    public static final ResourceLocation DAMASCUS_ELECTRUM = new ResourceLocation(MODID, "damascus_electrum");
    public static final ResourceLocation DAMASCUS_INVAR = new ResourceLocation(MODID, "damascus_invar");
    public static final ResourceLocation DAMASCUS_NICKEL_SILVER = new ResourceLocation(MODID, "damascus_nickel_silver");
    public static final ResourceLocation DAMASCUS_ORICHALCUM = new ResourceLocation(MODID, "damascus_orichalcum");
    public static final ResourceLocation DAMASCUS_RED_ALLOY = new ResourceLocation(MODID, "damascus_red_alloy");
    public static final ResourceLocation DAMASCUS_TUNGSTEN_STEEL = new ResourceLocation(MODID, "damascus_tungsten_steel");
    public static final ResourceLocation DAMASCUS_STAINLESS_STEEL = new ResourceLocation(MODID, "damascus_stainless_steel");
    public static final ResourceLocation DAMASCUS_LOCKALLOY = new ResourceLocation(MODID, "damascus_lockalloy");
    public static final ResourceLocation DAMASCUS_MANGANIN = new ResourceLocation(MODID, "damascus_manganin");
    public static final ResourceLocation DAMASCUS_GALINSTAN = new ResourceLocation(MODID, "damascus_galinstan");
    public static final ResourceLocation DAMASCUS_CROWN_GOLD = new ResourceLocation(MODID, "damascus_crown_gold");
    public static final ResourceLocation DAMASCUS_WHITE_GOLD = new ResourceLocation(MODID, "damascus_white_gold");
    public static final ResourceLocation DAMASCUS_SOLDER = new ResourceLocation(MODID, "damascus_solder");
    public static final ResourceLocation DAMASCUS_MAGNOX = new ResourceLocation(MODID, "damascus_magnox");
    public static final ResourceLocation DAMASCUS_PLATINUM_STERLING = new ResourceLocation(MODID, "damascus_platinum_sterling");
    public static final ResourceLocation DAMASCUS_TITANIUM_GOLD = new ResourceLocation(MODID, "damascus_titanium_gold");
    public static final ResourceLocation DAMASCUS_PEWTER = new ResourceLocation(MODID, "damascus_pewter");
    public static final ResourceLocation DAMASCUS_CAST_IRON = new ResourceLocation(MODID, "damascus_cast_iron");
    public static final ResourceLocation DAMASCUS_MITHRIL = new ResourceLocation(MODID, "damascus_mithril");
    public static final ResourceLocation DAMASCUS_ARDITE = new ResourceLocation(MODID, "damascus_ardite");
    public static final ResourceLocation DAMASCUS_MANYULLYN = new ResourceLocation(MODID, "damascus_manyullyn");
    public static final ResourceLocation DAMASCUS_ALCHEMICAL_BRASS = new ResourceLocation(MODID, "damascus_alchemical_brass");
    public static final ResourceLocation DAMASCUS_THAUMIUM = new ResourceLocation(MODID, "damascus_thaumium");
    public static final ResourceLocation DAMASCUS_VOIDMETAL = new ResourceLocation(MODID, "damascus_voidmetal");
    public static final ResourceLocation DAMASCUS_SIGNALUM = new ResourceLocation(MODID, "damascus_signalum");
    public static final ResourceLocation DAMASCUS_LUMIUM = new ResourceLocation(MODID, "damascus_lumium");
    public static final ResourceLocation DAMASCUS_ENDERIUM = new ResourceLocation(MODID, "damascus_enderium");
    public static final ResourceLocation DAMASCUS_ADAMANTIUM = new ResourceLocation(MODID, "damascus_adamantium");
    
@SubscribeEvent
public static void onPreRegisterMetal(TFCRegistryEvent.RegisterPreBlock<Metal> event)
    {
        IForgeRegistry<Metal> r = event.getRegistry();
		// Reactive nonmetals
        // No metal ingots
        
        // Noble gases
        // None
        
        // Alkali metals
        r.register(new Metal(LITHIUM, Metal.Tier.TIER_I, true, 0.25f, 180, 0xFFC0C0C0, null, null));
        r.register(new Metal(NATRIUM, Metal.Tier.TIER_I, true, 0.28f, 98, 0xFFD3D3D3, null, null));
        r.register(new Metal(KALIUM, Metal.Tier.TIER_I, true, 0.30f, 64, 0xFFD3D3D3, null, null));
        r.register(new Metal(RUBIDIUM, Metal.Tier.TIER_I, true, 0.31f, 39, 0xFFD3D3D3, null, null));
        r.register(new Metal(CAESIUM, Metal.Tier.TIER_I, true, 0.32f, 29, 0xFFEEE8AC, null, null));
        r.register(new Metal(FRANCIUM, Metal.Tier.TIER_I, true, 0.34f, 8, 0xFFD3D3D3, null, null));
        
        // Alkaline earth metals
        r.register(new Metal(BERYLLIUM, Metal.Tier.TIER_III, true, 0.16f, 1287, 0xFFA1A2A4, null, null));
        r.register(new Metal(MAGNESIUM, Metal.Tier.TIER_I, true, 0.25f, 650, 0xFFD3D3D3, null, null));
        r.register(new Metal(CALCIUM, Metal.Tier.TIER_I, true, 0.26f, 842, 0xFF8A9597, null, null));
        r.register(new Metal(STRONTIUM, Metal.Tier.TIER_I, true, 0.26f, 777, 0xFFC0C0C0, null, null));
        r.register(new Metal(BARIUM, Metal.Tier.TIER_I, true, 0.28f, 727, 0xFFD3D3D3, null, null));
        r.register(new Metal(RADIUM, Metal.Tier.TIER_I, true, 0.29f, 700, 0xFFD3D3D3, null, null));
        
        // Transition metals
        r.register(new Metal(SCANDIUM, Metal.Tier.TIER_III, true, 0.26f, 1541, 0xFFD3D3D3, null, null));
        r.register(new Metal(TITANIUM, Metal.Tier.TIER_VI, true, 0.3f, 1700, 0xFFD3D3D3, ToolMaterialsTFCE.TITANIUM, ArmorMaterialsTFCE.TITANIUM));
        r.register(new Metal(VANADIUM, Metal.Tier.TIER_IV, true, 0.25f, 1910, 0xFFC0C0C0, null, null));
        r.register(new Metal(CHROMIUM, Metal.Tier.TIER_V, true, 0.23f, 1907, 0xFF8A9597, null, null));
        r.register(new Metal(MANGANESE, Metal.Tier.TIER_IV, true, 0.26f, 1246, 0xFFD3D3D3, null, null));
        r.register(new Metal(COBALT, Metal.Tier.TIER_VI, true, 0.3f, 1495, 0xFFD7DAEB, ToolMaterialsTFCE.COBALT, ArmorMaterialsTFCE.COBALT));
        r.register(new Metal(YTTRIUM, Metal.Tier.TIER_I, true, 0.27f, 1526, 0xFFD2D2D2, null, null));
        r.register(new Metal(ZIRCONIUM, Metal.Tier.TIER_III, true, 0.25f, 1855, 0xFFD3D3D3, null, null));
        r.register(new Metal(NIOBIUM, Metal.Tier.TIER_IV, true, 0.25f, 2477, 0xFFD3D3D3, null, null));
        r.register(new Metal(MOLYBDENUM, Metal.Tier.TIER_III, true, 0.24f, 2623, 0xFFD3D3D3, null, null));
        r.register(new Metal(TECHNETIUM, Metal.Tier.TIER_III, true, 0.24f, 2157, 0xFF89979A, null, null));
        r.register(new Metal(RUTHENIUM, Metal.Tier.TIER_IV, true, 0.24f, 2334, 0xFFA4A3A6, null, null));
        r.register(new Metal(RHODIUM, Metal.Tier.TIER_IV, true, 0.25f, 1964, 0xFFD3D3D3, null, null));
        r.register(new Metal(PALLADIUM, Metal.Tier.TIER_III, true, 0.26f, 1555, 0xFFD3D3D3, ToolMaterialsTFCE.PALLADIUM, ArmorMaterialsTFCE.PALLADIUM));
        r.register(new Metal(HAFNIUM, Metal.Tier.TIER_III, true, 0.26f, 2233, 0xFFC0C0C0, null, null));
        r.register(new Metal(TANTALUM, Metal.Tier.TIER_IV, true, 0.26f, 3017, 0xFF808080, null, null));
        r.register(new Metal(TUNGSTEN, Metal.Tier.TIER_VI, true, 0.2f, 3400, 0xFFD3D3D3, ToolMaterialsTFCE.TUNGSTEN, ArmorMaterialsTFCE.TUNGSTEN));
        r.register(new Metal(RHENIUM, Metal.Tier.TIER_V, true, 0.25f, 3186, 0xFFD3D3D3, null, null));
        r.register(new Metal(OSMIUM, Metal.Tier.TIER_VI, true, 0.35f, 3025, 0xFF65D1D4, ToolMaterialsTFCE.OSMIUM, ArmorMaterialsTFCE.OSMIUM));
        r.register(new Metal(IRIDIUM, Metal.Tier.TIER_IV, true, 0.25f, 2446, 0xFFD3D3D3, ToolMaterialsTFCE.IRIDIUM, ArmorMaterialsTFCE.IRIDIUM));
        r.register(new Metal(RUTHERFORDIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(DUBNIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(SEABORGIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(BOHRIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(HASSIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(COPERNICIUM, Metal.Tier.TIER_I, true, 0.26f, 10, 0xFFD4CDC2, null, null));
        
        // Post-transition metals
        r.register(new Metal(ALUMINIUM, Metal.Tier.TIER_IV, true, 0.3f, 660, 0xFFC0C0C0, ToolMaterialsTFCE.ALUMINIUM, ArmorMaterialsTFCE.ALUMINIUM));
        r.register(new Metal(GALLIUM, Metal.Tier.TIER_I, true, 0.26f, 30, 0xFFC0C0C0, null, null));
        r.register(new Metal(CADMIUM, Metal.Tier.TIER_II, true, 0.26f, 321, 0xFF65D1D4, null, null));
        r.register(new Metal(INDIUM, Metal.Tier.TIER_I, true, 0.27f, 157, 0xFFD3D3D3, null, null));
        r.register(new Metal(MERCURY, Metal.Tier.TIER_I, true, 0.28f, 0, 0xFFD3D3D3, null, null));
        r.register(new Metal(THALLIUM, Metal.Tier.TIER_I, true, 0.26f, 304, 0xFF8A9597, null, null));
        /*if (ModConfig.METAL_ADDITIONS.lead)
        {
            r.register(new Metal(LEAD, Metal.Tier.TIER_I, true, 0.25f, 630, 0xFFE7E7F5, null, null)); // todo change these values accordingly if added
        }*/
        r.register(new Metal(POLONIUM, Metal.Tier.TIER_I, true, 0.26f, 254, 0xFF8A9597, null, null));
        
        // Metalloid
        r.register(new Metal(BORON, Metal.Tier.TIER_VI, true, 0.11f, 2076, 0xFF4E4E4E, null, null));
        r.register(new Metal(SILICIUM, Metal.Tier.TIER_IV, true, 0.20f, 1414, 0xFFC0C0C0, null, null));
        r.register(new Metal(GERMANIUM, Metal.Tier.TIER_IV, true, 0.23f, 938, 0xFFD3D3D3, null, null));
        r.register(new Metal(ARSENIC, Metal.Tier.TIER_II, true, 0.25f, 615, 0xFF808080, null, null));
        r.register(new Metal(ANTIMONY, Metal.Tier.TIER_I, true, 0.25f, 630, 0xFF808080, null, null));
        r.register(new Metal(TELLURIUM, Metal.Tier.TIER_II, true, 0.26f, 450, 0xFFC0C0C0, null, null));
        r.register(new Metal(ASTATINE, Metal.Tier.TIER_II, true, 0.11f, 150, 0xFFC0C0C0, null, null));
        
        // Lanthanides
        r.register(new Metal(LANTHANUM, Metal.Tier.TIER_II, true, 0.27f, 920, 0xFFD3D3D3, null, null));
        r.register(new Metal(CERIUM, Metal.Tier.TIER_II, true, 0.27f, 795, 0xFF808080, null, null));
        r.register(new Metal(PRASEODYMIUM, Metal.Tier.TIER_II, true, 0.27f, 935, 0xFFD3D3D3, null, null));
        r.register(new Metal(NEODYMIUM, Metal.Tier.TIER_II, true, 0.27f, 1024, 0xFFD3D3D3, null, null));
        r.register(new Metal(PROMETHIUM, Metal.Tier.TIER_II, true, 0.27f, 1042, 0xFFD3D3D3, null, null));
        r.register(new Metal(SAMARIUM, Metal.Tier.TIER_II, true, 0.30f, 1072, 0xFFD3D3D3, null, null));
        r.register(new Metal(EUROPIUM, Metal.Tier.TIER_II, true, 0.28f, 826, 0xFFD3D3D3, null, null));
        r.register(new Metal(GADOLINIUM, Metal.Tier.TIER_III, true, 0.37f, 1312, 0xFFD3D3D3, null, null));
        r.register(new Metal(TERBIUM, Metal.Tier.TIER_III, true, 0.29f, 1356, 0xFF8A9597, null, null));
        r.register(new Metal(DYSPROSIUM, Metal.Tier.TIER_III, true, 0.28f, 1407, 0xFFD3D3D3, null, null));
        r.register(new Metal(HOLMIUM, Metal.Tier.TIER_III, true, 0.27f, 1461, 0xFFD3D3D3, null, null));
        r.register(new Metal(ERBIUM, Metal.Tier.TIER_III, true, 0.28f, 1529, 0xFFD3D3D3, null, null));
        r.register(new Metal(THULIUM, Metal.Tier.TIER_II, true, 0.27f, 1545, 0xFF8A9597, null, null));
        r.register(new Metal(YTTERBIUM, Metal.Tier.TIER_I, true, 0.27f, 824, 0xFF8F8F88, null, null));
        r.register(new Metal(LUTETIUM, Metal.Tier.TIER_III, true, 0.27f, 1652, 0xFFD3D3D3, null, null));
        
        // Actinides
        r.register(new Metal(ACTINIUM, Metal.Tier.TIER_I, true, 0.27f, 1227, 0xFFD3D3D3, null, null));
        r.register(new Metal(THORIUM, Metal.Tier.TIER_I, true, 0.26f, 1750, 0xFFC0C0C0, null, null));
        r.register(new Metal(PROTACTINIUM, Metal.Tier.TIER_I, true, 0.27f, 1568, 0xFFC0C0C0, null, null));
        r.register(new Metal(URANIUM, Metal.Tier.TIER_I, true, 0.28f, 1132, 0xFFD3D3D3, null, null));
        r.register(new Metal(NEPTUNIUM, Metal.Tier.TIER_I, true, 0.29f, 640, 0xFFC0C0C0, null, null));
        r.register(new Metal(PLUTONIUM, Metal.Tier.TIER_I, true, 0.36f, 639, 0xFFD3D3D3, null, null));
        r.register(new Metal(AMERICIUM, Metal.Tier.TIER_I, true, 0.63f, 1176, 0xFFD3D3D3, null, null));
        r.register(new Metal(CURIUM, Metal.Tier.TIER_I, true, 0.25f, 1340, 0xFFD3D3D3, null, null));
        r.register(new Metal(BERKELIUM, Metal.Tier.TIER_I, true, 0.25f, 986, 0xFFD3D3D3, null, null));
        r.register(new Metal(CALIFORNIUM, Metal.Tier.TIER_I, true, 0.25f, 900, 0xFFD3D3D3, null, null));
        r.register(new Metal(EINSTEINIUM, Metal.Tier.TIER_I, true, 0.25f, 860, 0xFFC0C0C0, null, null));
        r.register(new Metal(FERMIUM, Metal.Tier.TIER_I, true, 0.25f, 1527, 0xFFD3D3D3, null, null));
        r.register(new Metal(MENDELEVIUM, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFFD3D3D3, null, null));
        r.register(new Metal(NOBELIUM, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFFD3D3D3, null, null));
        r.register(new Metal(LAWRENCIUM, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFFD3D3D3, null, null));
        
        // Gases
        // None
        
        // Alloys & Other
        r.register(new Metal(ALUMINIUM_BRASS, Metal.Tier.TIER_IV, true, 0.3f, 630, 0xFFDCDABE, null, null));
        r.register(new Metal(CONSTANTAN, Metal.Tier.TIER_II, true, 0.5f, 1200, 0xFFD28874, null, null));
        r.register(new Metal(ELECTRUM, Metal.Tier.TIER_II, true, 0.5f, 1200, 0xFFDFB950, null, null));
        r.register(new Metal(INVAR, Metal.Tier.TIER_III, true, 0.35f, 1450, 0xFF40444A, ToolMaterialsTFCE.INVAR, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(NICKEL_SILVER, Metal.Tier.TIER_II, true, 0.35f, 1450, 0xFFA4A4A5, ToolMaterialsTFCE.NICKEL_SILVER, ArmorMaterialsTFCE.NICKEL_SILVER));
        r.register(new Metal(ORICHALCUM, Metal.Tier.TIER_II, true, 0.5f, 1020, 0xFFB39F44, null, null));
        r.register(new Metal(RED_ALLOY, Metal.Tier.TIER_II, true, 0.35f, 1080, 0xFFDA6E6E, null, null));
        r.register(new Metal(TUNGSTEN_STEEL, Metal.Tier.TIER_VI, true, 0.2f, 3695, 0xFF565F6E, ToolMaterialsTFCE.TUNGSTEN_STEEL, ArmorMaterialsTFCE.TUNGSTEN_STEEL));
        r.register(new Metal(STAINLESS_STEEL, Metal.Tier.TIER_VI, true, 0.2f, 1510, 0xFF5F5F5F, ToolMaterialsTFCE.STAINLESS_STEEL, ArmorMaterialsTFCE.STAINLESS_STEEL));
        r.register(new Metal(LOCKALLOY, Metal.Tier.TIER_V, true, 0.2f, 1435, 0xFF5F5F5F, null, null));
        r.register(new Metal(MANGANIN, Metal.Tier.TIER_II, true, 0.5f, 1020, 0xFFD28874, null, null));
        r.register(new Metal(GALINSTAN, Metal.Tier.TIER_I, true, 0.26f, 11, 0xFFC0C0C0, null, null));
        r.register(new Metal(CROWN_GOLD, Metal.Tier.TIER_I, true, 0.26f, 1030, 0xFFF0E630, null, null));
        r.register(new Metal(WHITE_GOLD, Metal.Tier.TIER_I, true, 0.26f, 1030, 0xFFF9F9F9, null, null));
        r.register(new Metal(SOLDER, Metal.Tier.TIER_I, true, 0.18f, 185, 0xFFD3D3D3, null, null));
        r.register(new Metal(MAGNOX, Metal.Tier.TIER_I, true, 0.25f, 850, 0xFFD3D3D3, null, null));
        r.register(new Metal(PLATINUM_STERLING, Metal.Tier.TIER_V, true, 0.35f, 1730, 0xFF9DADC0, null, null));
        r.register(new Metal(TITANIUM_GOLD, Metal.Tier.TIER_VI, true, 0.35f, 1768, 0xFFECD940, ToolMaterialsTFCE.TITANIUM_GOLD, ArmorMaterialsTFCE.TITANIUM_GOLD));
        r.register(new Metal(PEWTER, Metal.Tier.TIER_II, true, 0.35f, 230, 0xFF51707B, null, null));
        //r.register(new Metal(CAST_IRON, Metal.Tier.TIER_II, true, 0.35f, 1535, 0xFF5F5F5F, null, ArmorMaterialsTFCE.CAST_IRON));
        r.register(new Metal(CAST_IRON, Metal.Tier.TIER_II, true, 0.35f, 1535, 0xFF5F5F5F, null, null));
        r.register(new Metal(CAST_IRON_MANGANESE, Metal.Tier.TIER_II, false, 0.35f, 1535, 0xFF5F5F5F, null, null));
        r.register(new Metal(CAST_IRON_SULFUR, Metal.Tier.TIER_II, false, 0.35f, 1535, 0xFF5F5F5F, null, null));
        r.register(new Metal(CAST_IRON_PHOSPHORUS, Metal.Tier.TIER_II, false, 0.35f, 1535, 0xFF5F5F5F, null, null));
        
        // Fantasy
        r.register(new Metal(MITHRIL, Metal.Tier.TIER_II, true, 0.35f, 940, 0xFF8ADAF6, ToolMaterialsTFCE.MITHRIL, ArmorMaterialsTFCE.MITHRIL));
        r.register(new Metal(ARDITE, Metal.Tier.TIER_IV, true, 0.35f, 960, 0xFF40444A, null, null));
        r.register(new Metal(MANYULLYN, Metal.Tier.TIER_VI, true, 0.3f, 1550, 0xFF40444A, ToolMaterialsTFCE.MANYULLYN, ArmorMaterialsTFCE.MANYULLYN));
        r.register(new Metal(ALCHEMICAL_BRASS, Metal.Tier.TIER_IV, true, 0.3f, 630, 0xFFCD9438, null, null));
        r.register(new Metal(THAUMIUM, Metal.Tier.TIER_III, true, 0.3f, 1535, 0xFF493C70, null, null));
        r.register(new Metal(VOIDMETAL, Metal.Tier.TIER_IV, true, 0.3f, 1535, 0xFF140921, null, null));
        r.register(new Metal(SIGNALUM, Metal.Tier.TIER_II, true, 0.3f, 985, 0xFF140921, null, null));
        r.register(new Metal(LUMIUM, Metal.Tier.TIER_II, true, 0.3f, 420, 0xFF140921, null, null));
        r.register(new Metal(ENDERIUM, Metal.Tier.TIER_III, true, 0.3f, 420, 0xFF0D5E60, null, null));
        r.register(new Metal(ADAMANTIUM, Metal.Tier.TIER_V, true, 0.2f, 1635, 0xFFAD2335, ToolMaterialsTFCE.ADAMANTIUM, ArmorMaterialsTFCE.ADAMANTIUM));
    
        // Metal + Nickel Weld
        // Vanilla TFC Metals
        r.register(new Metal(BISMUTH_NICKEL, Metal.Tier.TIER_I, false, 0.14f, 270, 0xFF486B72, null, null));
        r.register(new Metal(BISMUTH_BRONZE_NICKEL, Metal.Tier.TIER_II, false, 0.35f, 985, 0xFF418E4F, null, null));
        r.register(new Metal(BLACK_BRONZE_NICKEL, Metal.Tier.TIER_II, false, 0.35f, 1070, 0xFF3B2636, null, null));
        r.register(new Metal(BRASS_NICKEL, Metal.Tier.TIER_I, false, 0.35f, 930, 0xFF96892E, null, null));
        r.register(new Metal(BRONZE_NICKEL, Metal.Tier.TIER_II, false, 0.35f, 950, 0xFF7C5E33, null, null));
        r.register(new Metal(COPPER_NICKEL, Metal.Tier.TIER_I, false, 0.35f, 950, 0xFF7C5E33, null, null));
        r.register(new Metal(GOLD_NICKEL, Metal.Tier.TIER_I, false, 0.6f, 1060, 0xFFDCBF1B, null, null));
        r.register(new Metal(ROSE_GOLD_NICKEL, Metal.Tier.TIER_I, false, 0.35f, 960, 0xFFEB7137, null, null));
        r.register(new Metal(SILVER_NICKEL, Metal.Tier.TIER_I, false, 0.48f, 961, 0xFF949495, null, null));
        r.register(new Metal(TIN_NICKEL, Metal.Tier.TIER_I, false, 0.14f, 230, 0xFF90A4BB, null, null));
        r.register(new Metal(ZINC_NICKEL, Metal.Tier.TIER_I, false, 0.21f, 420, 0xFFBBB9C4, null, null));
        r.register(new Metal(STERLING_SILVER_NICKEL, Metal.Tier.TIER_I, false, 0.35f, 900, 0xFFAC927B, null, null));
        r.register(new Metal(WROUGHT_IRON_NICKEL, Metal.Tier.TIER_III, false, 0.35f, 1535, 0xFF989897, null, null));
        r.register(new Metal(PIG_IRON_NICKEL, Metal.Tier.TIER_III, false, 0.35f, 1535, 0xFF6A595C, null, null));
        r.register(new Metal(STEEL_NICKEL, Metal.Tier.TIER_IV, false, 0.35f, 1540, 0xFF5F5F5F, null, null));
        r.register(new Metal(PLATINUM_NICKEL, Metal.Tier.TIER_V, false, 0.35f, 1730, 0xFF9DADC0, null, null));
        r.register(new Metal(BLACK_STEEL_NICKEL, Metal.Tier.TIER_V, false, 0.35f, 1485, 0xFF111111, null, null));
        r.register(new Metal(BLUE_STEEL_NICKEL, Metal.Tier.TIER_VI, false, 0.35f, 1540, 0xFF2D5596, null, null));
        r.register(new Metal(RED_STEEL_NICKEL, Metal.Tier.TIER_VI, false, 0.35f, 1540, 0xFF700503, null, null));

        // Alkali metals
        r.register(new Metal(LITHIUM_NICKEL, Metal.Tier.TIER_I, false, 0.25f, 180, 0xFFC0C0C0, null, null));
        r.register(new Metal(NATRIUM_NICKEL, Metal.Tier.TIER_I, false, 0.28f, 98, 0xFFD3D3D3, null, null));
        r.register(new Metal(KALIUM_NICKEL, Metal.Tier.TIER_I, false, 0.30f, 64, 0xFFD3D3D3, null, null));
        r.register(new Metal(RUBIDIUM_NICKEL, Metal.Tier.TIER_I, false, 0.31f, 39, 0xFFD3D3D3, null, null));
        r.register(new Metal(CAESIUM_NICKEL, Metal.Tier.TIER_I, false, 0.32f, 29, 0xFFEEE8AC, null, null));
        r.register(new Metal(FRANCIUM_NICKEL, Metal.Tier.TIER_I, false, 0.34f, 8, 0xFFD3D3D3, null, null));
        
        // Alkaline earth metals
        r.register(new Metal(BERYLLIUM_NICKEL, Metal.Tier.TIER_III, false, 0.16f, 1287, 0xFFA1A2A4, null, null));
        r.register(new Metal(MAGNESIUM_NICKEL, Metal.Tier.TIER_I, false, 0.25f, 650, 0xFFD3D3D3, null, null));
        r.register(new Metal(CALCIUM_NICKEL, Metal.Tier.TIER_I, false, 0.26f, 842, 0xFF8A9597, null, null));
        r.register(new Metal(STRONTIUM_NICKEL, Metal.Tier.TIER_I, false, 0.26f, 777, 0xFFC0C0C0, null, null));
        r.register(new Metal(BARIUM_NICKEL, Metal.Tier.TIER_I, false, 0.28f, 727, 0xFFD3D3D3, null, null));
        r.register(new Metal(RADIUM_NICKEL, Metal.Tier.TIER_I, false, 0.29f, 700, 0xFFD3D3D3, null, null));
        
        // Transition metals
        r.register(new Metal(SCANDIUM_NICKEL, Metal.Tier.TIER_III, false, 0.26f, 1541, 0xFFD3D3D3, null, null));
        r.register(new Metal(TITANIUM_NICKEL, Metal.Tier.TIER_VI, false, 0.3f, 1700, 0xFFD3D3D3, null, null));
        r.register(new Metal(VANADIUM_NICKEL, Metal.Tier.TIER_IV, false, 0.25f, 1910, 0xFFC0C0C0, null, null));
        r.register(new Metal(CHROMIUM_NICKEL, Metal.Tier.TIER_V, false, 0.23f, 1907, 0xFF8A9597, null, null));
        r.register(new Metal(MANGANESE_NICKEL, Metal.Tier.TIER_IV, false, 0.26f, 1246, 0xFFD3D3D3, null, null));
        r.register(new Metal(COBALT_NICKEL, Metal.Tier.TIER_VI, false, 0.3f, 1495, 0xFFD7DAEB, null, null));
        r.register(new Metal(YTTRIUM_NICKEL, Metal.Tier.TIER_I, false, 0.27f, 1526, 0xFFD2D2D2, null, null));
        r.register(new Metal(ZIRCONIUM_NICKEL, Metal.Tier.TIER_III, false, 0.25f, 1855, 0xFFD3D3D3, null, null));
        r.register(new Metal(NIOBIUM_NICKEL, Metal.Tier.TIER_IV, false, 0.25f, 2477, 0xFFD3D3D3, null, null));
        r.register(new Metal(MOLYBDENUM_NICKEL, Metal.Tier.TIER_III, false, 0.24f, 2623, 0xFFD3D3D3, null, null));
        r.register(new Metal(TECHNETIUM_NICKEL, Metal.Tier.TIER_III, false, 0.24f, 2157, 0xFF89979A, null, null));
        r.register(new Metal(RUTHENIUM_NICKEL, Metal.Tier.TIER_IV, false, 0.24f, 2334, 0xFFA4A3A6, null, null));
        r.register(new Metal(RHODIUM_NICKEL, Metal.Tier.TIER_IV, false, 0.25f, 1964, 0xFFD3D3D3, null, null));
        r.register(new Metal(PALLADIUM_NICKEL, Metal.Tier.TIER_III, false, 0.26f, 1555, 0xFFD3D3D3, null, null));
        r.register(new Metal(HAFNIUM_NICKEL, Metal.Tier.TIER_III, false, 0.26f, 2233, 0xFFC0C0C0, null, null));
        r.register(new Metal(TANTALUM_NICKEL, Metal.Tier.TIER_IV, false, 0.26f, 3017, 0xFF808080, null, null));
        r.register(new Metal(TUNGSTEN_NICKEL, Metal.Tier.TIER_VI, false, 0.2f, 3400, 0xFFD3D3D3, null, null));
        r.register(new Metal(RHENIUM_NICKEL, Metal.Tier.TIER_V, false, 0.25f, 3186, 0xFFD3D3D3, null, null));
        r.register(new Metal(OSMIUM_NICKEL, Metal.Tier.TIER_VI, false, 0.35f, 3025, 0xFF65D1D4, null, null));
        r.register(new Metal(IRIDIUM_NICKEL, Metal.Tier.TIER_IV, false, 0.25f, 2446, 0xFFD3D3D3, null, null));
        r.register(new Metal(RUTHERFORDIUM_NICKEL, Metal.Tier.TIER_III, false, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(DUBNIUM_NICKEL, Metal.Tier.TIER_III, false, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(SEABORGIUM_NICKEL, Metal.Tier.TIER_III, false, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(BOHRIUM_NICKEL, Metal.Tier.TIER_III, false, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(HASSIUM_NICKEL, Metal.Tier.TIER_III, false, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(COPERNICIUM_NICKEL, Metal.Tier.TIER_I, false, 0.26f, 10, 0xFFD4CDC2, null, null));
        
        // Post-transition metals
        r.register(new Metal(ALUMINIUM_NICKEL, Metal.Tier.TIER_IV, false, 0.3f, 660, 0xFFC0C0C0, null, null));
        r.register(new Metal(GALLIUM_NICKEL, Metal.Tier.TIER_I, false, 0.26f, 30, 0xFFC0C0C0, null, null));
        r.register(new Metal(CADMIUM_NICKEL, Metal.Tier.TIER_II, false, 0.26f, 321, 0xFF65D1D4, null, null));
        r.register(new Metal(INDIUM_NICKEL, Metal.Tier.TIER_I, false, 0.27f, 157, 0xFFD3D3D3, null, null));
        r.register(new Metal(MERCURY_NICKEL, Metal.Tier.TIER_I, false, 0.28f, 0, 0xFFD3D3D3, null, null));
        r.register(new Metal(THALLIUM_NICKEL, Metal.Tier.TIER_I, false, 0.26f, 304, 0xFF8A9597, null, null));
        r.register(new Metal(POLONIUM_NICKEL, Metal.Tier.TIER_I, false, 0.26f, 254, 0xFF8A9597, null, null));
        
        // Metalloid
        r.register(new Metal(BORON_NICKEL, Metal.Tier.TIER_VI, false, 0.11f, 2076, 0xFF4E4E4E, null, null));
        r.register(new Metal(SILICIUM_NICKEL, Metal.Tier.TIER_IV, false, 0.20f, 1414, 0xFFC0C0C0, null, null));
        r.register(new Metal(GERMANIUM_NICKEL, Metal.Tier.TIER_IV, false, 0.23f, 938, 0xFFD3D3D3, null, null));
        r.register(new Metal(ARSENIC_NICKEL, Metal.Tier.TIER_II, false, 0.25f, 615, 0xFF808080, null, null));
        r.register(new Metal(ANTIMONY_NICKEL, Metal.Tier.TIER_I, false, 0.25f, 630, 0xFF808080, null, null));
        r.register(new Metal(TELLURIUM_NICKEL, Metal.Tier.TIER_II, false, 0.26f, 450, 0xFFC0C0C0, null, null));
        r.register(new Metal(ASTATINE_NICKEL, Metal.Tier.TIER_II, false, 0.11f, 150, 0xFFC0C0C0, null, null));
        
        // Lanthanides
        r.register(new Metal(LANTHANUM_NICKEL, Metal.Tier.TIER_II, false, 0.27f, 920, 0xFFD3D3D3, null, null));
        r.register(new Metal(CERIUM_NICKEL, Metal.Tier.TIER_II, false, 0.27f, 795, 0xFF808080, null, null));
        r.register(new Metal(PRASEODYMIUM_NICKEL, Metal.Tier.TIER_II, false, 0.27f, 935, 0xFFD3D3D3, null, null));
        r.register(new Metal(NEODYMIUM_NICKEL, Metal.Tier.TIER_II, false, 0.27f, 1024, 0xFFD3D3D3, null, null));
        r.register(new Metal(PROMETHIUM_NICKEL, Metal.Tier.TIER_II, false, 0.27f, 1042, 0xFFD3D3D3, null, null));
        r.register(new Metal(SAMARIUM_NICKEL, Metal.Tier.TIER_II, false, 0.30f, 1072, 0xFFD3D3D3, null, null));
        r.register(new Metal(EUROPIUM_NICKEL, Metal.Tier.TIER_II, false, 0.28f, 826, 0xFFD3D3D3, null, null));
        r.register(new Metal(GADOLINIUM_NICKEL, Metal.Tier.TIER_III, false, 0.37f, 1312, 0xFFD3D3D3, null, null));
        r.register(new Metal(TERBIUM_NICKEL, Metal.Tier.TIER_III, false, 0.29f, 1356, 0xFF8A9597, null, null));
        r.register(new Metal(DYSPROSIUM_NICKEL, Metal.Tier.TIER_III, false, 0.28f, 1407, 0xFFD3D3D3, null, null));
        r.register(new Metal(HOLMIUM_NICKEL, Metal.Tier.TIER_III, false, 0.27f, 1461, 0xFFD3D3D3, null, null));
        r.register(new Metal(ERBIUM_NICKEL, Metal.Tier.TIER_III, false, 0.28f, 1529, 0xFFD3D3D3, null, null));
        r.register(new Metal(THULIUM_NICKEL, Metal.Tier.TIER_II, false, 0.27f, 1545, 0xFF8A9597, null, null));
        r.register(new Metal(YTTERBIUM_NICKEL, Metal.Tier.TIER_I, false, 0.27f, 824, 0xFF8F8F88, null, null));
        r.register(new Metal(LUTETIUM_NICKEL, Metal.Tier.TIER_III, false, 0.27f, 1652, 0xFFD3D3D3, null, null));
        
        // Actinides
        r.register(new Metal(ACTINIUM_NICKEL, Metal.Tier.TIER_I, false, 0.27f, 1227, 0xFFD3D3D3, null, null));
        r.register(new Metal(THORIUM_NICKEL, Metal.Tier.TIER_I, false, 0.26f, 1750, 0xFFC0C0C0, null, null));
        r.register(new Metal(PROTACTINIUM_NICKEL, Metal.Tier.TIER_I, false, 0.27f, 1568, 0xFFC0C0C0, null, null));
        r.register(new Metal(URANIUM_NICKEL, Metal.Tier.TIER_I, false, 0.28f, 1132, 0xFFD3D3D3, null, null));
        r.register(new Metal(NEPTUNIUM_NICKEL, Metal.Tier.TIER_I, false, 0.29f, 640, 0xFFC0C0C0, null, null));
        r.register(new Metal(PLUTONIUM_NICKEL, Metal.Tier.TIER_I, false, 0.36f, 639, 0xFFD3D3D3, null, null));
        r.register(new Metal(AMERICIUM_NICKEL, Metal.Tier.TIER_I, false, 0.63f, 1176, 0xFFD3D3D3, null, null));
        r.register(new Metal(CURIUM_NICKEL, Metal.Tier.TIER_I, false, 0.25f, 1340, 0xFFD3D3D3, null, null));
        r.register(new Metal(BERKELIUM_NICKEL, Metal.Tier.TIER_I, false, 0.25f, 986, 0xFFD3D3D3, null, null));
        r.register(new Metal(CALIFORNIUM_NICKEL, Metal.Tier.TIER_I, false, 0.25f, 900, 0xFFD3D3D3, null, null));
        r.register(new Metal(EINSTEINIUM_NICKEL, Metal.Tier.TIER_I, false, 0.25f, 860, 0xFFC0C0C0, null, null));
        r.register(new Metal(FERMIUM_NICKEL, Metal.Tier.TIER_I, false, 0.25f, 1527, 0xFFD3D3D3, null, null));
        r.register(new Metal(MENDELEVIUM_NICKEL, Metal.Tier.TIER_I, false, 0.25f, 827, 0xFFD3D3D3, null, null));
        r.register(new Metal(NOBELIUM_NICKEL, Metal.Tier.TIER_I, false, 0.25f, 827, 0xFFD3D3D3, null, null));
        r.register(new Metal(LAWRENCIUM_NICKEL, Metal.Tier.TIER_I, false, 0.25f, 827, 0xFFD3D3D3, null, null));

        // Alloys & Other
        r.register(new Metal(ALUMINIUM_BRASS_NICKEL, Metal.Tier.TIER_IV, false, 0.3f, 630, 0xFFDCDABE, null, null));
        r.register(new Metal(CONSTANTAN_NICKEL, Metal.Tier.TIER_II, false, 0.5f, 1200, 0xFFD28874, null, null));
        r.register(new Metal(ELECTRUM_NICKEL, Metal.Tier.TIER_II, false, 0.5f, 1200, 0xFFDFB950, null, null));
        r.register(new Metal(INVAR_NICKEL, Metal.Tier.TIER_III, false, 0.35f, 1450, 0xFF40444A, null, null));
        r.register(new Metal(NICKEL_SILVER_NICKEL, Metal.Tier.TIER_II, false, 0.35f, 1450, 0xFFA4A4A5, null, null));
        r.register(new Metal(ORICHALCUM_NICKEL, Metal.Tier.TIER_II, false, 0.5f, 1020, 0xFFB39F44, null, null));
        r.register(new Metal(RED_ALLOY_NICKEL, Metal.Tier.TIER_II, false, 0.35f, 1080, 0xFFDA6E6E, null, null));
        r.register(new Metal(TUNGSTEN_STEEL_NICKEL, Metal.Tier.TIER_VI, false, 0.2f, 3695, 0xFF565F6E, null, null));
        r.register(new Metal(STAINLESS_STEEL_NICKEL, Metal.Tier.TIER_VI, false, 0.2f, 1510, 0xFF5F5F5F, null, null));
        r.register(new Metal(LOCKALLOY_NICKEL, Metal.Tier.TIER_V, false, 0.2f, 1435, 0xFF5F5F5F, null, null));
        r.register(new Metal(MANGANIN_NICKEL, Metal.Tier.TIER_II, false, 0.5f, 1020, 0xFFD28874, null, null));
        r.register(new Metal(GALINSTAN_NICKEL, Metal.Tier.TIER_I, false, 0.26f, 11, 0xFFC0C0C0, null, null));
        r.register(new Metal(CROWN_GOLD_NICKEL, Metal.Tier.TIER_I, false, 0.26f, 1030, 0xFFF0E630, null, null));
        r.register(new Metal(WHITE_GOLD_NICKEL, Metal.Tier.TIER_I, false, 0.26f, 1030, 0xFFF9F9F9, null, null));
        r.register(new Metal(SOLDER_NICKEL, Metal.Tier.TIER_I, false, 0.18f, 185, 0xFFD3D3D3, null, null));
        r.register(new Metal(MAGNOX_NICKEL, Metal.Tier.TIER_I, false, 0.25f, 850, 0xFFD3D3D3, null, null));
        r.register(new Metal(PLATINUM_STERLING_NICKEL, Metal.Tier.TIER_V, false, 0.35f, 1730, 0xFF9DADC0, null, null));
        r.register(new Metal(TITANIUM_GOLD_NICKEL, Metal.Tier.TIER_VI, false, 0.35f, 1768, 0xFFECD940, null, null));
        r.register(new Metal(PEWTER_NICKEL, Metal.Tier.TIER_II, false, 0.35f, 230, 0xFF51707B, null, null));
        r.register(new Metal(CAST_IRON_NICKEL, Metal.Tier.TIER_II, false, 0.35f, 1535, 0xFF5F5F5F, null, null));
        
        // Fantasy
        r.register(new Metal(MITHRIL_NICKEL, Metal.Tier.TIER_II, false, 0.35f, 940, 0xFF8ADAF6, null, null));
        r.register(new Metal(ARDITE_NICKEL, Metal.Tier.TIER_IV, false, 0.35f, 960, 0xFF40444A, null, null));
        r.register(new Metal(MANYULLYN_NICKEL, Metal.Tier.TIER_VI, false, 0.3f, 1550, 0xFF40444A, null, null));
        r.register(new Metal(ALCHEMICAL_BRASS_NICKEL, Metal.Tier.TIER_IV, false, 0.3f, 630, 0xFFCD9438, null, null));
        r.register(new Metal(THAUMIUM_NICKEL, Metal.Tier.TIER_III, false, 0.3f, 1535, 0xFF493C70, null, null));
        r.register(new Metal(VOIDMETAL_NICKEL, Metal.Tier.TIER_IV, false, 0.3f, 1535, 0xFF140921, null, null));
        r.register(new Metal(SIGNALUM_NICKEL, Metal.Tier.TIER_II, false, 0.3f, 985, 0xFF140921, null, null));
        r.register(new Metal(LUMIUM_NICKEL, Metal.Tier.TIER_II, false, 0.3f, 420, 0xFF140921, null, null));
        r.register(new Metal(ENDERIUM_NICKEL, Metal.Tier.TIER_III, false, 0.3f, 420, 0xFF0D5E60, null, null));
        r.register(new Metal(ADAMANTIUM_NICKEL, Metal.Tier.TIER_V, false, 0.2f, 1635, 0xFFAD2335, null, null));
        
        // Vanilla TFC Metals
        /*
        r.register(new Metal(BISMUTH_NICKEL, Metal.Tier.TIER_I, true, 0.14f, 270, 0xFF486B72, null, null));
        r.register(new Metal(BISMUTH_BRONZE_NICKEL, Metal.Tier.TIER_II, true, 0.35f, 985, 0xFF418E4F, null, null));
        r.register(new Metal(BLACK_BRONZE_NICKEL, Metal.Tier.TIER_II, true, 0.35f, 1070, 0xFF3B2636, null, null));
        r.register(new Metal(BRASS_NICKEL, Metal.Tier.TIER_I, true, 0.35f, 930, 0xFF96892E, null, null));
        r.register(new Metal(BRONZE_NICKEL, Metal.Tier.TIER_II, true, 0.35f, 950, 0xFF7C5E33, null, null));
        r.register(new Metal(COPPER_NICKEL, Metal.Tier.TIER_I, true, 0.35f, 950, 0xFF7C5E33, null, null));
        r.register(new Metal(GOLD_NICKEL, Metal.Tier.TIER_I, true, 0.6f, 1060, 0xFFDCBF1B, null, null));
        r.register(new Metal(ROSE_GOLD_NICKEL, Metal.Tier.TIER_I, true, 0.35f, 960, 0xFFEB7137, null, null));
        r.register(new Metal(SILVER_NICKEL, Metal.Tier.TIER_I, true, 0.48f, 961, 0xFF949495, null, null));
        r.register(new Metal(TIN_NICKEL, Metal.Tier.TIER_I, true, 0.14f, 230, 0xFF90A4BB, null, null));
        r.register(new Metal(ZINC_NICKEL, Metal.Tier.TIER_I, true, 0.21f, 420, 0xFFBBB9C4, null, null));
        r.register(new Metal(STERLING_SILVER_NICKEL, Metal.Tier.TIER_I, true, 0.35f, 900, 0xFFAC927B, null, null));
        r.register(new Metal(WROUGHT_IRON_NICKEL, Metal.Tier.TIER_III, true, 0.35f, 1535, 0xFF989897, null, null));
        r.register(new Metal(PIG_IRON_NICKEL, Metal.Tier.TIER_III, true, 0.35f, 1535, 0xFF6A595C, null, null));
        r.register(new Metal(STEEL_NICKEL, Metal.Tier.TIER_IV, true, 0.35f, 1540, 0xFF5F5F5F, null, null));
        r.register(new Metal(PLATINUM_NICKEL, Metal.Tier.TIER_V, true, 0.35f, 1730, 0xFF9DADC0, null, null));
        r.register(new Metal(BLACK_STEEL_NICKEL, Metal.Tier.TIER_V, true, 0.35f, 1485, 0xFF111111, null, null));
        r.register(new Metal(BLUE_STEEL_NICKEL, Metal.Tier.TIER_VI, true, 0.35f, 1540, 0xFF2D5596, null, null));
        r.register(new Metal(RED_STEEL_NICKEL, Metal.Tier.TIER_VI, true, 0.35f, 1540, 0xFF700503, null, null));
        
        // Alkali metals
        r.register(new Metal(LITHIUM_NICKEL, Metal.Tier.TIER_I, true, 0.25f, 180, 0xFFC0C0C0, null, null));
        r.register(new Metal(NATRIUM_NICKEL, Metal.Tier.TIER_I, true, 0.28f, 98, 0xFFD3D3D3, null, null));
        r.register(new Metal(KALIUM_NICKEL, Metal.Tier.TIER_I, true, 0.30f, 64, 0xFFD3D3D3, null, null));
        r.register(new Metal(RUBIDIUM_NICKEL, Metal.Tier.TIER_I, true, 0.31f, 39, 0xFFD3D3D3, null, null));
        r.register(new Metal(CAESIUM_NICKEL, Metal.Tier.TIER_I, true, 0.32f, 29, 0xFFEEE8AC, null, null));
        r.register(new Metal(FRANCIUM_NICKEL, Metal.Tier.TIER_I, true, 0.34f, 8, 0xFFD3D3D3, null, null));
        
        // Alkaline earth metals
        r.register(new Metal(BERYLLIUM_NICKEL, Metal.Tier.TIER_III, true, 0.16f, 1287, 0xFFA1A2A4, null, null));
        r.register(new Metal(MAGNESIUM_NICKEL, Metal.Tier.TIER_I, true, 0.25f, 650, 0xFFD3D3D3, null, null));
        r.register(new Metal(CALCIUM_NICKEL, Metal.Tier.TIER_I, true, 0.26f, 842, 0xFF8A9597, null, null));
        r.register(new Metal(STRONTIUM_NICKEL, Metal.Tier.TIER_I, true, 0.26f, 777, 0xFFC0C0C0, null, null));
        r.register(new Metal(BARIUM_NICKEL, Metal.Tier.TIER_I, true, 0.28f, 727, 0xFFD3D3D3, null, null));
        r.register(new Metal(RADIUM_NICKEL, Metal.Tier.TIER_I, true, 0.29f, 700, 0xFFD3D3D3, null, null));
        
        // Transition metals
        r.register(new Metal(SCANDIUM_NICKEL, Metal.Tier.TIER_III, true, 0.26f, 1541, 0xFFD3D3D3, null, null));
        r.register(new Metal(TITANIUM_NICKEL, Metal.Tier.TIER_VI, true, 0.3f, 1700, 0xFFD3D3D3, null, null));
        r.register(new Metal(VANADIUM_NICKEL, Metal.Tier.TIER_IV, true, 0.25f, 1910, 0xFFC0C0C0, null, null));
        r.register(new Metal(CHROMIUM_NICKEL, Metal.Tier.TIER_V, true, 0.23f, 1907, 0xFF8A9597, null, null));
        r.register(new Metal(MANGANESE_NICKEL, Metal.Tier.TIER_IV, true, 0.26f, 1246, 0xFFD3D3D3, null, null));
        r.register(new Metal(COBALT_NICKEL, Metal.Tier.TIER_VI, true, 0.3f, 1495, 0xFFD7DAEB, null, null));
        r.register(new Metal(YTTRIUM_NICKEL, Metal.Tier.TIER_I, true, 0.27f, 1526, 0xFFD2D2D2, null, null));
        r.register(new Metal(ZIRCONIUM_NICKEL, Metal.Tier.TIER_III, true, 0.25f, 1855, 0xFFD3D3D3, null, null));
        r.register(new Metal(NIOBIUM_NICKEL, Metal.Tier.TIER_IV, true, 0.25f, 2477, 0xFFD3D3D3, null, null));
        r.register(new Metal(MOLYBDENUM_NICKEL, Metal.Tier.TIER_III, true, 0.24f, 2623, 0xFFD3D3D3, null, null));
        r.register(new Metal(TECHNETIUM_NICKEL, Metal.Tier.TIER_III, true, 0.24f, 2157, 0xFF89979A, null, null));
        r.register(new Metal(RUTHENIUM_NICKEL, Metal.Tier.TIER_IV, true, 0.24f, 2334, 0xFFA4A3A6, null, null));
        r.register(new Metal(RHODIUM_NICKEL, Metal.Tier.TIER_IV, true, 0.25f, 1964, 0xFFD3D3D3, null, null));
        r.register(new Metal(PALLADIUM_NICKEL, Metal.Tier.TIER_III, true, 0.26f, 1555, 0xFFD3D3D3, null, null));
        r.register(new Metal(HAFNIUM_NICKEL, Metal.Tier.TIER_III, true, 0.26f, 2233, 0xFFC0C0C0, null, null));
        r.register(new Metal(TANTALUM_NICKEL, Metal.Tier.TIER_IV, true, 0.26f, 3017, 0xFF808080, null, null));
        r.register(new Metal(TUNGSTEN_NICKEL, Metal.Tier.TIER_VI, true, 0.2f, 3400, 0xFFD3D3D3, null, null));
        r.register(new Metal(RHENIUM_NICKEL, Metal.Tier.TIER_V, true, 0.25f, 3186, 0xFFD3D3D3, null, null));
        r.register(new Metal(OSMIUM_NICKEL, Metal.Tier.TIER_VI, true, 0.35f, 3025, 0xFF65D1D4, null, null));
        r.register(new Metal(IRIDIUM_NICKEL, Metal.Tier.TIER_IV, true, 0.25f, 2446, 0xFFD3D3D3, null, null));
        r.register(new Metal(RUTHERFORDIUM_NICKEL, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(DUBNIUM_NICKEL, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(SEABORGIUM_NICKEL, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(BOHRIUM_NICKEL, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(HASSIUM_NICKEL, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(COPERNICIUM_NICKEL, Metal.Tier.TIER_I, true, 0.26f, 10, 0xFFD4CDC2, null, null));
        
        // Post-transition metals
        r.register(new Metal(ALUMINIUM_NICKEL, Metal.Tier.TIER_IV, true, 0.3f, 660, 0xFFC0C0C0, null, null));
        r.register(new Metal(GALLIUM_NICKEL, Metal.Tier.TIER_I, true, 0.26f, 30, 0xFFC0C0C0, null, null));
        r.register(new Metal(CADMIUM_NICKEL, Metal.Tier.TIER_II, true, 0.26f, 321, 0xFF65D1D4, null, null));
        r.register(new Metal(INDIUM_NICKEL, Metal.Tier.TIER_I, true, 0.27f, 157, 0xFFD3D3D3, null, null));
        r.register(new Metal(MERCURY_NICKEL, Metal.Tier.TIER_I, true, 0.28f, 0, 0xFFD3D3D3, null, null));
        r.register(new Metal(THALLIUM_NICKEL, Metal.Tier.TIER_I, true, 0.26f, 304, 0xFF8A9597, null, null));
        r.register(new Metal(POLONIUM_NICKEL, Metal.Tier.TIER_I, true, 0.26f, 254, 0xFF8A9597, null, null));
        
        // Metalloid
        r.register(new Metal(BORON_NICKEL, Metal.Tier.TIER_VI, true, 0.11f, 2076, 0xFF4E4E4E, null, null));
        r.register(new Metal(SILICIUM_NICKEL, Metal.Tier.TIER_IV, true, 0.20f, 1414, 0xFFC0C0C0, null, null));
        r.register(new Metal(GERMANIUM_NICKEL, Metal.Tier.TIER_IV, true, 0.23f, 938, 0xFFD3D3D3, null, null));
        r.register(new Metal(ARSENIC_NICKEL, Metal.Tier.TIER_II, true, 0.25f, 615, 0xFF808080, null, null));
        r.register(new Metal(ANTIMONY_NICKEL, Metal.Tier.TIER_I, true, 0.25f, 630, 0xFF808080, null, null));
        r.register(new Metal(TELLURIUM_NICKEL, Metal.Tier.TIER_II, true, 0.26f, 450, 0xFFC0C0C0, null, null));
        r.register(new Metal(ASTATINE_NICKEL, Metal.Tier.TIER_II, true, 0.11f, 150, 0xFFC0C0C0, null, null));
        
        // Lanthanides
        r.register(new Metal(LANTHANUM_NICKEL, Metal.Tier.TIER_II, true, 0.27f, 920, 0xFFD3D3D3, null, null));
        r.register(new Metal(CERIUM_NICKEL, Metal.Tier.TIER_II, true, 0.27f, 795, 0xFF808080, null, null));
        r.register(new Metal(PRASEODYMIUM_NICKEL, Metal.Tier.TIER_II, true, 0.27f, 935, 0xFFD3D3D3, null, null));
        r.register(new Metal(NEODYMIUM_NICKEL, Metal.Tier.TIER_II, true, 0.27f, 1024, 0xFFD3D3D3, null, null));
        r.register(new Metal(PROMETHIUM_NICKEL, Metal.Tier.TIER_II, true, 0.27f, 1042, 0xFFD3D3D3, null, null));
        r.register(new Metal(SAMARIUM_NICKEL, Metal.Tier.TIER_II, true, 0.30f, 1072, 0xFFD3D3D3, null, null));
        r.register(new Metal(EUROPIUM_NICKEL, Metal.Tier.TIER_II, true, 0.28f, 826, 0xFFD3D3D3, null, null));
        r.register(new Metal(GADOLINIUM_NICKEL, Metal.Tier.TIER_III, true, 0.37f, 1312, 0xFFD3D3D3, null, null));
        r.register(new Metal(TERBIUM_NICKEL, Metal.Tier.TIER_III, true, 0.29f, 1356, 0xFF8A9597, null, null));
        r.register(new Metal(DYSPROSIUM_NICKEL, Metal.Tier.TIER_III, true, 0.28f, 1407, 0xFFD3D3D3, null, null));
        r.register(new Metal(HOLMIUM_NICKEL, Metal.Tier.TIER_III, true, 0.27f, 1461, 0xFFD3D3D3, null, null));
        r.register(new Metal(ERBIUM_NICKEL, Metal.Tier.TIER_III, true, 0.28f, 1529, 0xFFD3D3D3, null, null));
        r.register(new Metal(THULIUM_NICKEL, Metal.Tier.TIER_II, true, 0.27f, 1545, 0xFF8A9597, null, null));
        r.register(new Metal(YTTERBIUM_NICKEL, Metal.Tier.TIER_I, true, 0.27f, 824, 0xFF8F8F88, null, null));
        r.register(new Metal(LUTETIUM_NICKEL, Metal.Tier.TIER_III, true, 0.27f, 1652, 0xFFD3D3D3, null, null));
        
        // Actinides
        r.register(new Metal(ACTINIUM_NICKEL, Metal.Tier.TIER_I, true, 0.27f, 1227, 0xFFD3D3D3, null, null));
        r.register(new Metal(THORIUM_NICKEL, Metal.Tier.TIER_I, true, 0.26f, 1750, 0xFFC0C0C0, null, null));
        r.register(new Metal(PROTACTINIUM_NICKEL, Metal.Tier.TIER_I, true, 0.27f, 1568, 0xFFC0C0C0, null, null));
        r.register(new Metal(URANIUM_NICKEL, Metal.Tier.TIER_I, true, 0.28f, 1132, 0xFFD3D3D3, null, null));
        r.register(new Metal(NEPTUNIUM_NICKEL, Metal.Tier.TIER_I, true, 0.29f, 640, 0xFFC0C0C0, null, null));
        r.register(new Metal(PLUTONIUM_NICKEL, Metal.Tier.TIER_I, true, 0.36f, 639, 0xFFD3D3D3, null, null));
        r.register(new Metal(AMERICIUM_NICKEL, Metal.Tier.TIER_I, true, 0.63f, 1176, 0xFFD3D3D3, null, null));
        r.register(new Metal(CURIUM_NICKEL, Metal.Tier.TIER_I, true, 0.25f, 1340, 0xFFD3D3D3, null, null));
        r.register(new Metal(BERKELIUM_NICKEL, Metal.Tier.TIER_I, true, 0.25f, 986, 0xFFD3D3D3, null, null));
        r.register(new Metal(CALIFORNIUM_NICKEL, Metal.Tier.TIER_I, true, 0.25f, 900, 0xFFD3D3D3, null, null));
        r.register(new Metal(EINSTEINIUM_NICKEL, Metal.Tier.TIER_I, true, 0.25f, 860, 0xFFC0C0C0, null, null));
        r.register(new Metal(FERMIUM_NICKEL, Metal.Tier.TIER_I, true, 0.25f, 1527, 0xFFD3D3D3, null, null));
        r.register(new Metal(MENDELEVIUM_NICKEL, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFFD3D3D3, null, null));
        r.register(new Metal(NOBELIUM_NICKEL, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFFD3D3D3, null, null));
        r.register(new Metal(LAWRENCIUM_NICKEL, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFFD3D3D3, null, null));

        // Alloys & Other
        r.register(new Metal(ALUMINIUM_BRASS_NICKEL, Metal.Tier.TIER_IV, true, 0.3f, 630, 0xFFDCDABE, null, null));
        r.register(new Metal(CONSTANTAN_NICKEL, Metal.Tier.TIER_II, true, 0.5f, 1200, 0xFFD28874, null, null));
        r.register(new Metal(ELECTRUM_NICKEL, Metal.Tier.TIER_II, true, 0.5f, 1200, 0xFFDFB950, null, null));
        r.register(new Metal(INVAR_NICKEL, Metal.Tier.TIER_III, true, 0.35f, 1450, 0xFF40444A, null, null));
        r.register(new Metal(NICKEL_SILVER_NICKEL, Metal.Tier.TIER_II, true, 0.35f, 1450, 0xFFA4A4A5, null, null));
        r.register(new Metal(ORICHALCUM_NICKEL, Metal.Tier.TIER_II, true, 0.5f, 1020, 0xFFB39F44, null, null));
        r.register(new Metal(RED_ALLOY_NICKEL, Metal.Tier.TIER_II, true, 0.35f, 1080, 0xFFDA6E6E, null, null));
        r.register(new Metal(TUNGSTEN_STEEL_NICKEL, Metal.Tier.TIER_VI, true, 0.2f, 3695, 0xFF565F6E, null, null));
        r.register(new Metal(STAINLESS_STEEL_NICKEL, Metal.Tier.TIER_VI, true, 0.2f, 1510, 0xFF5F5F5F, null, null));
        r.register(new Metal(LOCKALLOY_NICKEL, Metal.Tier.TIER_V, true, 0.2f, 1435, 0xFF5F5F5F, null, null));
        r.register(new Metal(MANGANIN_NICKEL, Metal.Tier.TIER_II, true, 0.5f, 1020, 0xFFD28874, null, null));
        r.register(new Metal(GALINSTAN_NICKEL, Metal.Tier.TIER_I, true, 0.26f, 11, 0xFFC0C0C0, null, null));
        r.register(new Metal(CROWN_GOLD_NICKEL, Metal.Tier.TIER_I, true, 0.26f, 1030, 0xFFF0E630, null, null));
        r.register(new Metal(WHITE_GOLD_NICKEL, Metal.Tier.TIER_I, true, 0.26f, 1030, 0xFFF9F9F9, null, null));
        r.register(new Metal(SOLDER_NICKEL, Metal.Tier.TIER_I, true, 0.18f, 185, 0xFFD3D3D3, null, null));
        r.register(new Metal(MAGNOX_NICKEL, Metal.Tier.TIER_I, true, 0.25f, 850, 0xFFD3D3D3, null, null));
        r.register(new Metal(PLATINUM_STERLING_NICKEL, Metal.Tier.TIER_V, true, 0.35f, 1730, 0xFF9DADC0, null, null));
        r.register(new Metal(TITANIUM_GOLD_NICKEL, Metal.Tier.TIER_VI, true, 0.35f, 1768, 0xFFECD940, null, null));
        r.register(new Metal(PEWTER_NICKEL, Metal.Tier.TIER_II, true, 0.35f, 230, 0xFF51707B, null, null));
        r.register(new Metal(CAST_IRON_NICKEL, Metal.Tier.TIER_II, true, 0.35f, 1535, 0xFF5F5F5F, null, null));
        
        // Fantasy
        r.register(new Metal(MITHRIL_NICKEL, Metal.Tier.TIER_II, true, 0.35f, 940, 0xFF8ADAF6, null, null));
        r.register(new Metal(ARDITE_NICKEL, Metal.Tier.TIER_IV, true, 0.35f, 960, 0xFF40444A, null, null));
        r.register(new Metal(MANYULLYN_NICKEL, Metal.Tier.TIER_VI, true, 0.3f, 1550, 0xFF40444A, null, null));
        r.register(new Metal(ALCHEMICAL_BRASS_NICKEL, Metal.Tier.TIER_IV, true, 0.3f, 630, 0xFFCD9438, null, null));
        r.register(new Metal(THAUMIUM_NICKEL, Metal.Tier.TIER_III, true, 0.3f, 1535, 0xFF493C70, null, null));
        r.register(new Metal(VOIDMETAL_NICKEL, Metal.Tier.TIER_IV, true, 0.3f, 1535, 0xFF140921, null, null));
        r.register(new Metal(SIGNALUM_NICKEL, Metal.Tier.TIER_II, true, 0.3f, 985, 0xFF140921, null, null));
        r.register(new Metal(LUMIUM_NICKEL, Metal.Tier.TIER_II, true, 0.3f, 420, 0xFF140921, null, null));
        r.register(new Metal(ENDERIUM_NICKEL, Metal.Tier.TIER_III, true, 0.3f, 420, 0xFF0D5E60, null, null));
        r.register(new Metal(ADAMANTIUM_NICKEL, Metal.Tier.TIER_V, true, 0.2f, 1635, 0xFFAD2335, null, null));
    	*/
    
        // Weak Damascus Metals
        // Vanilla TFC Metals
        r.register(new Metal(WEAK_DAMASCUS_BISMUTH, Metal.Tier.TIER_I, false, 0.14f, 270, 0xFF486B72, null, null));
        r.register(new Metal(WEAK_DAMASCUS_BISMUTH_BRONZE, Metal.Tier.TIER_II, false, 0.35f, 985, 0xFF418E4F, null, null));
        r.register(new Metal(WEAK_DAMASCUS_BLACK_BRONZE, Metal.Tier.TIER_II, false, 0.35f, 1070, 0xFF3B2636, null, null));
        r.register(new Metal(WEAK_DAMASCUS_BRASS, Metal.Tier.TIER_I, false, 0.35f, 930, 0xFF96892E, null, null));
        r.register(new Metal(WEAK_DAMASCUS_BRONZE, Metal.Tier.TIER_II, false, 0.35f, 950, 0xFF7C5E33, null, null));
        r.register(new Metal(WEAK_DAMASCUS_COPPER, Metal.Tier.TIER_I, false, 0.35f, 950, 0xFF7C5E33, null, null));
        r.register(new Metal(WEAK_DAMASCUS_GOLD, Metal.Tier.TIER_I, false, 0.6f, 1060, 0xFFDCBF1B, null, null));
        r.register(new Metal(WEAK_DAMASCUS_ROSE_GOLD, Metal.Tier.TIER_I, false, 0.35f, 960, 0xFFEB7137, null, null));
        r.register(new Metal(WEAK_DAMASCUS_SILVER, Metal.Tier.TIER_I, false, 0.48f, 961, 0xFF949495, null, null));
        r.register(new Metal(WEAK_DAMASCUS_TIN, Metal.Tier.TIER_I, false, 0.14f, 230, 0xFF90A4BB, null, null));
        r.register(new Metal(WEAK_DAMASCUS_ZINC, Metal.Tier.TIER_I, false, 0.21f, 420, 0xFFBBB9C4, null, null));
        r.register(new Metal(WEAK_DAMASCUS_STERLING_SILVER, Metal.Tier.TIER_I, false, 0.35f, 900, 0xFFAC927B, null, null));
        r.register(new Metal(WEAK_DAMASCUS_WROUGHT_IRON, Metal.Tier.TIER_III, false, 0.35f, 1535, 0xFF989897, null, null));
        r.register(new Metal(WEAK_DAMASCUS_PIG_IRON, Metal.Tier.TIER_III, false, 0.35f, 1535, 0xFF6A595C, null, null));
        r.register(new Metal(WEAK_DAMASCUS_STEEL, Metal.Tier.TIER_IV, false, 0.35f, 1540, 0xFF5F5F5F, null, null));
        r.register(new Metal(WEAK_DAMASCUS_PLATINUM, Metal.Tier.TIER_V, false, 0.35f, 1730, 0xFF9DADC0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_BLACK_STEEL, Metal.Tier.TIER_V, false, 0.35f, 1485, 0xFF111111, null, null));
        r.register(new Metal(WEAK_DAMASCUS_BLUE_STEEL, Metal.Tier.TIER_VI, false, 0.35f, 1540, 0xFF2D5596, null, null));
        r.register(new Metal(WEAK_DAMASCUS_RED_STEEL, Metal.Tier.TIER_VI, false, 0.35f, 1540, 0xFF700503, null, null));

        // Alkali metals
        r.register(new Metal(WEAK_DAMASCUS_LITHIUM, Metal.Tier.TIER_I, false, 0.25f, 180, 0xFFC0C0C0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_NATRIUM, Metal.Tier.TIER_I, false, 0.28f, 98, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_KALIUM, Metal.Tier.TIER_I, false, 0.30f, 64, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_RUBIDIUM, Metal.Tier.TIER_I, false, 0.31f, 39, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_CAESIUM, Metal.Tier.TIER_I, false, 0.32f, 29, 0xFFEEE8AC, null, null));
        r.register(new Metal(WEAK_DAMASCUS_FRANCIUM, Metal.Tier.TIER_I, false, 0.34f, 8, 0xFFD3D3D3, null, null));
        
        // Alkaline earth metals
        r.register(new Metal(WEAK_DAMASCUS_BERYLLIUM, Metal.Tier.TIER_III, false, 0.16f, 1287, 0xFFA1A2A4, null, null));
        r.register(new Metal(WEAK_DAMASCUS_MAGNESIUM, Metal.Tier.TIER_I, false, 0.25f, 650, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_CALCIUM, Metal.Tier.TIER_I, false, 0.26f, 842, 0xFF8A9597, null, null));
        r.register(new Metal(WEAK_DAMASCUS_STRONTIUM, Metal.Tier.TIER_I, false, 0.26f, 777, 0xFFC0C0C0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_BARIUM, Metal.Tier.TIER_I, false, 0.28f, 727, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_RADIUM, Metal.Tier.TIER_I, false, 0.29f, 700, 0xFFD3D3D3, null, null));
        
        // Transition metals
        r.register(new Metal(WEAK_DAMASCUS_SCANDIUM, Metal.Tier.TIER_III, false, 0.26f, 1541, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_TITANIUM, Metal.Tier.TIER_VI, false, 0.3f, 1700, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_VANADIUM, Metal.Tier.TIER_IV, false, 0.25f, 1910, 0xFFC0C0C0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_CHROMIUM, Metal.Tier.TIER_V, false, 0.23f, 1907, 0xFF8A9597, null, null));
        r.register(new Metal(WEAK_DAMASCUS_MANGANESE, Metal.Tier.TIER_IV, false, 0.26f, 1246, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_COBALT, Metal.Tier.TIER_VI, false, 0.3f, 1495, 0xFFD7DAEB, null, null));
        r.register(new Metal(WEAK_DAMASCUS_YTTRIUM, Metal.Tier.TIER_I, false, 0.27f, 1526, 0xFFD2D2D2, null, null));
        r.register(new Metal(WEAK_DAMASCUS_ZIRCONIUM, Metal.Tier.TIER_III, false, 0.25f, 1855, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_NIOBIUM, Metal.Tier.TIER_IV, false, 0.25f, 2477, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_MOLYBDENUM, Metal.Tier.TIER_III, false, 0.24f, 2623, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_TECHNETIUM, Metal.Tier.TIER_III, false, 0.24f, 2157, 0xFF89979A, null, null));
        r.register(new Metal(WEAK_DAMASCUS_RUTHENIUM, Metal.Tier.TIER_IV, false, 0.24f, 2334, 0xFFA4A3A6, null, null));
        r.register(new Metal(WEAK_DAMASCUS_RHODIUM, Metal.Tier.TIER_IV, false, 0.25f, 1964, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_PALLADIUM, Metal.Tier.TIER_III, false, 0.26f, 1555, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_HAFNIUM, Metal.Tier.TIER_III, false, 0.26f, 2233, 0xFFC0C0C0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_TANTALUM, Metal.Tier.TIER_IV, false, 0.26f, 3017, 0xFF808080, null, null));
        r.register(new Metal(WEAK_DAMASCUS_TUNGSTEN, Metal.Tier.TIER_VI, false, 0.2f, 3400, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_RHENIUM, Metal.Tier.TIER_V, false, 0.25f, 3186, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_OSMIUM, Metal.Tier.TIER_VI, false, 0.35f, 3025, 0xFF65D1D4, null, null));
        r.register(new Metal(WEAK_DAMASCUS_IRIDIUM, Metal.Tier.TIER_IV, false, 0.25f, 2446, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_RUTHERFORDIUM, Metal.Tier.TIER_III, false, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(WEAK_DAMASCUS_DUBNIUM, Metal.Tier.TIER_III, false, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(WEAK_DAMASCUS_SEABORGIUM, Metal.Tier.TIER_III, false, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(WEAK_DAMASCUS_BOHRIUM, Metal.Tier.TIER_III, false, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(WEAK_DAMASCUS_HASSIUM, Metal.Tier.TIER_III, false, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(WEAK_DAMASCUS_COPERNICIUM, Metal.Tier.TIER_I, false, 0.26f, 10, 0xFFD4CDC2, null, null));
        
        // Post-transition metals
        r.register(new Metal(WEAK_DAMASCUS_ALUMINIUM, Metal.Tier.TIER_IV, false, 0.3f, 660, 0xFFC0C0C0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_GALLIUM, Metal.Tier.TIER_I, false, 0.26f, 30, 0xFFC0C0C0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_CADMIUM, Metal.Tier.TIER_II, false, 0.26f, 321, 0xFF65D1D4, null, null));
        r.register(new Metal(WEAK_DAMASCUS_INDIUM, Metal.Tier.TIER_I, false, 0.27f, 157, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_MERCURY, Metal.Tier.TIER_I, false, 0.28f, 0, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_THALLIUM, Metal.Tier.TIER_I, false, 0.26f, 304, 0xFF8A9597, null, null));
        r.register(new Metal(WEAK_DAMASCUS_POLONIUM, Metal.Tier.TIER_I, false, 0.26f, 254, 0xFF8A9597, null, null));
        
        // Metalloid
        r.register(new Metal(WEAK_DAMASCUS_BORON, Metal.Tier.TIER_VI, false, 0.11f, 2076, 0xFF4E4E4E, null, null));
        r.register(new Metal(WEAK_DAMASCUS_SILICIUM, Metal.Tier.TIER_IV, false, 0.20f, 1414, 0xFFC0C0C0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_GERMANIUM, Metal.Tier.TIER_IV, false, 0.23f, 938, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_ARSENIC, Metal.Tier.TIER_II, false, 0.25f, 615, 0xFF808080, null, null));
        r.register(new Metal(WEAK_DAMASCUS_ANTIMONY, Metal.Tier.TIER_I, false, 0.25f, 630, 0xFF808080, null, null));
        r.register(new Metal(WEAK_DAMASCUS_TELLURIUM, Metal.Tier.TIER_II, false, 0.26f, 450, 0xFFC0C0C0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_ASTATINE, Metal.Tier.TIER_II, false, 0.11f, 150, 0xFFC0C0C0, null, null));
        
        // Lanthanides
        r.register(new Metal(WEAK_DAMASCUS_LANTHANUM, Metal.Tier.TIER_II, false, 0.27f, 920, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_CERIUM, Metal.Tier.TIER_II, false, 0.27f, 795, 0xFF808080, null, null));
        r.register(new Metal(WEAK_DAMASCUS_PRASEODYMIUM, Metal.Tier.TIER_II, false, 0.27f, 935, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_NEODYMIUM, Metal.Tier.TIER_II, false, 0.27f, 1024, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_PROMETHIUM, Metal.Tier.TIER_II, false, 0.27f, 1042, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_SAMARIUM, Metal.Tier.TIER_II, false, 0.30f, 1072, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_EUROPIUM, Metal.Tier.TIER_II, false, 0.28f, 826, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_GADOLINIUM, Metal.Tier.TIER_III, false, 0.37f, 1312, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_TERBIUM, Metal.Tier.TIER_III, false, 0.29f, 1356, 0xFF8A9597, null, null));
        r.register(new Metal(WEAK_DAMASCUS_DYSPROSIUM, Metal.Tier.TIER_III, false, 0.28f, 1407, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_HOLMIUM, Metal.Tier.TIER_III, false, 0.27f, 1461, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_ERBIUM, Metal.Tier.TIER_III, false, 0.28f, 1529, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_THULIUM, Metal.Tier.TIER_II, false, 0.27f, 1545, 0xFF8A9597, null, null));
        r.register(new Metal(WEAK_DAMASCUS_YTTERBIUM, Metal.Tier.TIER_I, false, 0.27f, 824, 0xFF8F8F88, null, null));
        r.register(new Metal(WEAK_DAMASCUS_LUTETIUM, Metal.Tier.TIER_III, false, 0.27f, 1652, 0xFFD3D3D3, null, null));
        
        // Actinides
        r.register(new Metal(WEAK_DAMASCUS_ACTINIUM, Metal.Tier.TIER_I, false, 0.27f, 1227, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_THORIUM, Metal.Tier.TIER_I, false, 0.26f, 1750, 0xFFC0C0C0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_PROTACTINIUM, Metal.Tier.TIER_I, false, 0.27f, 1568, 0xFFC0C0C0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_URANIUM, Metal.Tier.TIER_I, false, 0.28f, 1132, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_NEPTUNIUM, Metal.Tier.TIER_I, false, 0.29f, 640, 0xFFC0C0C0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_PLUTONIUM, Metal.Tier.TIER_I, false, 0.36f, 639, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_AMERICIUM, Metal.Tier.TIER_I, false, 0.63f, 1176, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_CURIUM, Metal.Tier.TIER_I, false, 0.25f, 1340, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_BERKELIUM, Metal.Tier.TIER_I, false, 0.25f, 986, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_CALIFORNIUM, Metal.Tier.TIER_I, false, 0.25f, 900, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_EINSTEINIUM, Metal.Tier.TIER_I, false, 0.25f, 860, 0xFFC0C0C0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_FERMIUM, Metal.Tier.TIER_I, false, 0.25f, 1527, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_MENDELEVIUM, Metal.Tier.TIER_I, false, 0.25f, 827, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_NOBELIUM, Metal.Tier.TIER_I, false, 0.25f, 827, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_LAWRENCIUM, Metal.Tier.TIER_I, false, 0.25f, 827, 0xFFD3D3D3, null, null));

        // Alloys & Other
        r.register(new Metal(WEAK_DAMASCUS_ALUMINIUM_BRASS, Metal.Tier.TIER_IV, false, 0.3f, 630, 0xFFDCDABE, null, null));
        r.register(new Metal(WEAK_DAMASCUS_CONSTANTAN, Metal.Tier.TIER_II, false, 0.5f, 1200, 0xFFD28874, null, null));
        r.register(new Metal(WEAK_DAMASCUS_ELECTRUM, Metal.Tier.TIER_II, false, 0.5f, 1200, 0xFFDFB950, null, null));
        r.register(new Metal(WEAK_DAMASCUS_INVAR, Metal.Tier.TIER_III, false, 0.35f, 1450, 0xFF40444A, null, null));
        r.register(new Metal(WEAK_DAMASCUS_NICKEL_SILVER, Metal.Tier.TIER_II, false, 0.35f, 1450, 0xFFA4A4A5, null, null));
        r.register(new Metal(WEAK_DAMASCUS_ORICHALCUM, Metal.Tier.TIER_II, false, 0.5f, 1020, 0xFFB39F44, null, null));
        r.register(new Metal(WEAK_DAMASCUS_RED_ALLOY, Metal.Tier.TIER_II, false, 0.35f, 1080, 0xFFDA6E6E, null, null));
        r.register(new Metal(WEAK_DAMASCUS_TUNGSTEN_STEEL, Metal.Tier.TIER_VI, false, 0.2f, 3695, 0xFF565F6E, null, null));
        r.register(new Metal(WEAK_DAMASCUS_STAINLESS_STEEL, Metal.Tier.TIER_VI, false, 0.2f, 1510, 0xFF5F5F5F, null, null));
        r.register(new Metal(WEAK_DAMASCUS_LOCKALLOY, Metal.Tier.TIER_V, false, 0.2f, 1435, 0xFF5F5F5F, null, null));
        r.register(new Metal(WEAK_DAMASCUS_MANGANIN, Metal.Tier.TIER_II, false, 0.5f, 1020, 0xFFD28874, null, null));
        r.register(new Metal(WEAK_DAMASCUS_GALINSTAN, Metal.Tier.TIER_I, false, 0.26f, 11, 0xFFC0C0C0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_CROWN_GOLD, Metal.Tier.TIER_I, false, 0.26f, 1030, 0xFFF0E630, null, null));
        r.register(new Metal(WEAK_DAMASCUS_WHITE_GOLD, Metal.Tier.TIER_I, false, 0.26f, 1030, 0xFFF9F9F9, null, null));
        r.register(new Metal(WEAK_DAMASCUS_SOLDER, Metal.Tier.TIER_I, false, 0.18f, 185, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_MAGNOX, Metal.Tier.TIER_I, false, 0.25f, 850, 0xFFD3D3D3, null, null));
        r.register(new Metal(WEAK_DAMASCUS_PLATINUM_STERLING, Metal.Tier.TIER_V, false, 0.35f, 1730, 0xFF9DADC0, null, null));
        r.register(new Metal(WEAK_DAMASCUS_TITANIUM_GOLD, Metal.Tier.TIER_VI, false, 0.35f, 1768, 0xFFECD940, null, null));
        r.register(new Metal(WEAK_DAMASCUS_PEWTER, Metal.Tier.TIER_II, false, 0.35f, 230, 0xFF51707B, null, null));
        r.register(new Metal(WEAK_DAMASCUS_CAST_IRON, Metal.Tier.TIER_II, false, 0.35f, 1535, 0xFF5F5F5F, null, null));
        
        // Fantasy
        r.register(new Metal(WEAK_DAMASCUS_MITHRIL, Metal.Tier.TIER_II, false, 0.35f, 940, 0xFF8ADAF6, null, null));
        r.register(new Metal(WEAK_DAMASCUS_ARDITE, Metal.Tier.TIER_IV, false, 0.35f, 960, 0xFF40444A, null, null));
        r.register(new Metal(WEAK_DAMASCUS_MANYULLYN, Metal.Tier.TIER_VI, false, 0.3f, 1550, 0xFF40444A, null, null));
        r.register(new Metal(WEAK_DAMASCUS_ALCHEMICAL_BRASS, Metal.Tier.TIER_IV, false, 0.3f, 630, 0xFFCD9438, null, null));
        r.register(new Metal(WEAK_DAMASCUS_THAUMIUM, Metal.Tier.TIER_III, false, 0.3f, 1535, 0xFF493C70, null, null));
        r.register(new Metal(WEAK_DAMASCUS_VOIDMETAL, Metal.Tier.TIER_IV, false, 0.3f, 1535, 0xFF140921, null, null));
        r.register(new Metal(WEAK_DAMASCUS_SIGNALUM, Metal.Tier.TIER_II, false, 0.3f, 985, 0xFF140921, null, null));
        r.register(new Metal(WEAK_DAMASCUS_LUMIUM, Metal.Tier.TIER_II, false, 0.3f, 420, 0xFF140921, null, null));
        r.register(new Metal(WEAK_DAMASCUS_ENDERIUM, Metal.Tier.TIER_III, false, 0.3f, 420, 0xFF0D5E60, null, null));
        r.register(new Metal(WEAK_DAMASCUS_ADAMANTIUM, Metal.Tier.TIER_V, false, 0.2f, 1635, 0xFFAD2335, null, null));
        
        // Damascus Metals
        // Vanilla TFC Metals
        /*
        r.register(new Metal(DAMASCUS_BISMUTH, Metal.Tier.TIER_I, true, 0.14f, 270, 0xFF486B72, null, null));
        r.register(new Metal(DAMASCUS_BISMUTH_BRONZE, Metal.Tier.TIER_II, true, 0.35f, 985, 0xFF418E4F, null, null));
        r.register(new Metal(DAMASCUS_BLACK_BRONZE, Metal.Tier.TIER_II, true, 0.35f, 1070, 0xFF3B2636, null, null));
        r.register(new Metal(DAMASCUS_BRASS, Metal.Tier.TIER_I, true, 0.35f, 930, 0xFF96892E, null, null));
        r.register(new Metal(DAMASCUS_BRONZE, Metal.Tier.TIER_II, true, 0.35f, 950, 0xFF7C5E33, null, null));
        r.register(new Metal(DAMASCUS_COPPER, Metal.Tier.TIER_I, true, 0.35f, 950, 0xFF7C5E33, null, null));
        r.register(new Metal(DAMASCUS_GOLD, Metal.Tier.TIER_I, true, 0.6f, 1060, 0xFFDCBF1B, null, null));
        r.register(new Metal(DAMASCUS_ROSE_GOLD, Metal.Tier.TIER_I, true, 0.35f, 960, 0xFFEB7137, null, null));
        r.register(new Metal(DAMASCUS_SILVER, Metal.Tier.TIER_I, true, 0.48f, 961, 0xFF949495, null, null));
        r.register(new Metal(DAMASCUS_TIN, Metal.Tier.TIER_I, true, 0.14f, 230, 0xFF90A4BB, null, null));
        r.register(new Metal(DAMASCUS_ZINC, Metal.Tier.TIER_I, true, 0.21f, 420, 0xFFBBB9C4, null, null));
        r.register(new Metal(DAMASCUS_STERLING_SILVER, Metal.Tier.TIER_I, true, 0.35f, 900, 0xFFAC927B, null, null));
        r.register(new Metal(DAMASCUS_WROUGHT_IRON, Metal.Tier.TIER_III, true, 0.35f, 1535, 0xFF989897, null, null));
        r.register(new Metal(DAMASCUS_PIG_IRON, Metal.Tier.TIER_III, true, 0.35f, 1535, 0xFF6A595C, null, null));
        r.register(new Metal(DAMASCUS_STEEL, Metal.Tier.TIER_IV, true, 0.35f, 1540, 0xFF5F5F5F, null, null));
        r.register(new Metal(DAMASCUS_PLATINUM, Metal.Tier.TIER_V, true, 0.35f, 1730, 0xFF9DADC0, null, null));
        r.register(new Metal(DAMASCUS_BLACK_STEEL, Metal.Tier.TIER_V, true, 0.35f, 1485, 0xFF111111, null, null));
        r.register(new Metal(DAMASCUS_BLUE_STEEL, Metal.Tier.TIER_VI, true, 0.35f, 1540, 0xFF2D5596, null, null));
        r.register(new Metal(DAMASCUS_RED_STEEL, Metal.Tier.TIER_VI, true, 0.35f, 1540, 0xFF700503, ToolMaterialsTFCE.DAMASCUS_RED_STEEL, ArmorMaterialsTFCE.ADAMANTIUM));
        */
        
        /*
        r.register(new Metal(DAMASCUS_BISMUTH, Metal.Tier.TIER_I, true, 0.14f, 270, 0xFF486B72, ToolMaterialsTFCE.DAMASCUS_BISMUTH, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_BISMUTH_BRONZE, Metal.Tier.TIER_II, true, 0.35f, 985, 0xFF418E4F, ToolMaterialsTFCE.DAMASCUS_BISMUTH_BRONZE, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_BLACK_BRONZE, Metal.Tier.TIER_II, true, 0.35f, 1070, 0xFF3B2636, ToolMaterialsTFCE.DAMASCUS_BLACK_BRONZE, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_BRASS, Metal.Tier.TIER_I, true, 0.35f, 930, 0xFF96892E, ToolMaterialsTFCE.DAMASCUS_BRASS, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_BRONZE, Metal.Tier.TIER_II, true, 0.35f, 950, 0xFF7C5E33, ToolMaterialsTFCE.DAMASCUS_BRONZE, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_COPPER, Metal.Tier.TIER_I, true, 0.35f, 950, 0xFF7C5E33, ToolMaterialsTFCE.DAMASCUS_COPPER, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_GOLD, Metal.Tier.TIER_I, true, 0.6f, 1060, 0xFFDCBF1B, ToolMaterialsTFCE.DAMASCUS_GOLD, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_ROSE_GOLD, Metal.Tier.TIER_I, true, 0.35f, 960, 0xFFEB7137, ToolMaterialsTFCE.DAMASCUS_ROSE_GOLD, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_SILVER, Metal.Tier.TIER_I, true, 0.48f, 961, 0xFF949495, ToolMaterialsTFCE.DAMASCUS_SILVER, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_TIN, Metal.Tier.TIER_I, true, 0.14f, 230, 0xFF90A4BB, ToolMaterialsTFCE.DAMASCUS_TIN, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_ZINC, Metal.Tier.TIER_I, true, 0.21f, 420, 0xFFBBB9C4, ToolMaterialsTFCE.DAMASCUS_ZINC, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_STERLING_SILVER, Metal.Tier.TIER_I, true, 0.35f, 900, 0xFFAC927B, ToolMaterialsTFCE.DAMASCUS_STERLING_SILVER, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_WROUGHT_IRON, Metal.Tier.TIER_III, true, 0.35f, 1535, 0xFF989897, ToolMaterialsTFCE.DAMASCUS_WROUGHT_IRON, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_PIG_IRON, Metal.Tier.TIER_III, true, 0.35f, 1535, 0xFF6A595C, ToolMaterialsTFCE.DAMASCUS_PIG_IRON, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_STEEL, Metal.Tier.TIER_IV, true, 0.35f, 1540, 0xFF5F5F5F, ToolMaterialsTFCE.DAMASCUS_STEEL, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_PLATINUM, Metal.Tier.TIER_V, true, 0.35f, 1730, 0xFF9DADC0, ToolMaterialsTFCE.DAMASCUS_PLATINUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_BLACK_STEEL, Metal.Tier.TIER_V, true, 0.35f, 1485, 0xFF111111, ToolMaterialsTFCE.DAMASCUS_BLACK_STEEL, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_BLUE_STEEL, Metal.Tier.TIER_VI, true, 0.35f, 1540, 0xFF2D5596, ToolMaterialsTFCE.DAMASCUS_BLUE_STEEL, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_RED_STEEL, Metal.Tier.TIER_VI, true, 0.35f, 1540, 0xFF700503, ToolMaterialsTFCE.DAMASCUS_RED_STEEL, ArmorMaterialsTFCE.INVAR));

        // Alkali metals
        r.register(new Metal(DAMASCUS_LITHIUM, Metal.Tier.TIER_I, true, 0.25f, 180, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_LITHIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_NATRIUM, Metal.Tier.TIER_I, true, 0.28f, 98, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_NATRIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_KALIUM, Metal.Tier.TIER_I, true, 0.30f, 64, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_KALIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_RUBIDIUM, Metal.Tier.TIER_I, true, 0.31f, 39, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_RUBIDIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_CAESIUM, Metal.Tier.TIER_I, true, 0.32f, 29, 0xFFEEE8AC, ToolMaterialsTFCE.DAMASCUS_CAESIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_FRANCIUM, Metal.Tier.TIER_I, true, 0.34f, 8, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_FRANCIUM, ArmorMaterialsTFCE.INVAR));
        
        // Alkaline earth metals
        r.register(new Metal(DAMASCUS_BERYLLIUM, Metal.Tier.TIER_III, true, 0.16f, 1287, 0xFFA1A2A4, ToolMaterialsTFCE.DAMASCUS_BERYLLIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_MAGNESIUM, Metal.Tier.TIER_I, true, 0.25f, 650, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_MAGNESIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_CALCIUM, Metal.Tier.TIER_I, true, 0.26f, 842, 0xFF8A9597, ToolMaterialsTFCE.DAMASCUS_CALCIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_STRONTIUM, Metal.Tier.TIER_I, true, 0.26f, 777, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_STRONTIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_BARIUM, Metal.Tier.TIER_I, true, 0.28f, 727, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_BARIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_RADIUM, Metal.Tier.TIER_I, true, 0.29f, 700, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_RADIUM, ArmorMaterialsTFCE.INVAR));
        
        // Transition metals
        r.register(new Metal(DAMASCUS_SCANDIUM, Metal.Tier.TIER_III, true, 0.26f, 1541, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_SCANDIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_TITANIUM, Metal.Tier.TIER_VI, true, 0.3f, 1700, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_TITANIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_VANADIUM, Metal.Tier.TIER_IV, true, 0.25f, 1910, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_VANADIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_CHROMIUM, Metal.Tier.TIER_V, true, 0.23f, 1907, 0xFF8A9597, ToolMaterialsTFCE.DAMASCUS_CHROMIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_MANGANESE, Metal.Tier.TIER_IV, true, 0.26f, 1246, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_MANGANESE, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_COBALT, Metal.Tier.TIER_VI, true, 0.3f, 1495, 0xFFD7DAEB, ToolMaterialsTFCE.DAMASCUS_COBALT, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_YTTRIUM, Metal.Tier.TIER_I, true, 0.27f, 1526, 0xFFD2D2D2, ToolMaterialsTFCE.DAMASCUS_YTTRIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_ZIRCONIUM, Metal.Tier.TIER_III, true, 0.25f, 1855, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_ZIRCONIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_NIOBIUM, Metal.Tier.TIER_IV, true, 0.25f, 2477, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_NIOBIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_MOLYBDENUM, Metal.Tier.TIER_III, true, 0.24f, 2623, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_MOLYBDENUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_TECHNETIUM, Metal.Tier.TIER_III, true, 0.24f, 2157, 0xFF89979A, ToolMaterialsTFCE.DAMASCUS_TECHNETIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_RUTHENIUM, Metal.Tier.TIER_IV, true, 0.24f, 2334, 0xFFA4A3A6, ToolMaterialsTFCE.DAMASCUS_RUTHENIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_RHODIUM, Metal.Tier.TIER_IV, true, 0.25f, 1964, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_RHODIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_PALLADIUM, Metal.Tier.TIER_III, true, 0.26f, 1555, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_PALLADIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_HAFNIUM, Metal.Tier.TIER_III, true, 0.26f, 2233, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_HAFNIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_TANTALUM, Metal.Tier.TIER_IV, true, 0.26f, 3017, 0xFF808080, ToolMaterialsTFCE.DAMASCUS_TANTALUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_TUNGSTEN, Metal.Tier.TIER_VI, true, 0.2f, 3400, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_TUNGSTEN, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_RHENIUM, Metal.Tier.TIER_V, true, 0.25f, 3186, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_RHENIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_OSMIUM, Metal.Tier.TIER_VI, true, 0.35f, 3025, 0xFF65D1D4, ToolMaterialsTFCE.DAMASCUS_OSMIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_IRIDIUM, Metal.Tier.TIER_IV, true, 0.25f, 2446, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_IRIDIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_RUTHERFORDIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, ToolMaterialsTFCE.DAMASCUS_RUTHERFORDIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_DUBNIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, ToolMaterialsTFCE.DAMASCUS_DUBNIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_SEABORGIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, ToolMaterialsTFCE.DAMASCUS_SEABORGIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_BOHRIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, ToolMaterialsTFCE.DAMASCUS_BOHRIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_HASSIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, ToolMaterialsTFCE.DAMASCUS_HASSIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_COPERNICIUM, Metal.Tier.TIER_I, true, 0.26f, 10, 0xFFD4CDC2, ToolMaterialsTFCE.DAMASCUS_COPERNICIUM, ArmorMaterialsTFCE.INVAR));
        
        // Post-transition metals
        r.register(new Metal(DAMASCUS_ALUMINIUM, Metal.Tier.TIER_IV, true, 0.3f, 660, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_ALUMINIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_GALLIUM, Metal.Tier.TIER_I, true, 0.26f, 30, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_GALLIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_CADMIUM, Metal.Tier.TIER_II, true, 0.26f, 321, 0xFF65D1D4, ToolMaterialsTFCE.DAMASCUS_CADMIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_INDIUM, Metal.Tier.TIER_I, true, 0.27f, 157, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_INDIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_MERCURY, Metal.Tier.TIER_I, true, 0.28f, 0, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_MERCURY, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_THALLIUM, Metal.Tier.TIER_I, true, 0.26f, 304, 0xFF8A9597, ToolMaterialsTFCE.DAMASCUS_THALLIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_POLONIUM, Metal.Tier.TIER_I, true, 0.26f, 254, 0xFF8A9597, ToolMaterialsTFCE.DAMASCUS_POLONIUM, ArmorMaterialsTFCE.INVAR));
        
        // Metalloid
        r.register(new Metal(DAMASCUS_BORON, Metal.Tier.TIER_VI, true, 0.11f, 2076, 0xFF4E4E4E, ToolMaterialsTFCE.DAMASCUS_BORON, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_SILICIUM, Metal.Tier.TIER_IV, true, 0.20f, 1414, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_SILICIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_GERMANIUM, Metal.Tier.TIER_IV, true, 0.23f, 938, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_GERMANIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_ARSENIC, Metal.Tier.TIER_II, true, 0.25f, 615, 0xFF808080, ToolMaterialsTFCE.DAMASCUS_ARSENIC, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_ANTIMONY, Metal.Tier.TIER_I, true, 0.25f, 630, 0xFF808080, ToolMaterialsTFCE.DAMASCUS_ANTIMONY, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_TELLURIUM, Metal.Tier.TIER_II, true, 0.26f, 450, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_TELLURIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_ASTATINE, Metal.Tier.TIER_II, true, 0.11f, 150, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_ASTATINE, ArmorMaterialsTFCE.INVAR));
        
        // Lanthanides
        r.register(new Metal(DAMASCUS_LANTHANUM, Metal.Tier.TIER_II, true, 0.27f, 920, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_LANTHANUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_CERIUM, Metal.Tier.TIER_II, true, 0.27f, 795, 0xFF808080, ToolMaterialsTFCE.DAMASCUS_CERIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_PRASEODYMIUM, Metal.Tier.TIER_II, true, 0.27f, 935, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_PRASEODYMIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_NEODYMIUM, Metal.Tier.TIER_II, true, 0.27f, 1024, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_NEODYMIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_PROMETHIUM, Metal.Tier.TIER_II, true, 0.27f, 1042, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_PROMETHIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_SAMARIUM, Metal.Tier.TIER_II, true, 0.30f, 1072, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_SAMARIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_EUROPIUM, Metal.Tier.TIER_II, true, 0.28f, 826, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_EUROPIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_GADOLINIUM, Metal.Tier.TIER_III, true, 0.37f, 1312, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_GADOLINIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_TERBIUM, Metal.Tier.TIER_III, true, 0.29f, 1356, 0xFF8A9597, ToolMaterialsTFCE.DAMASCUS_TERBIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_DYSPROSIUM, Metal.Tier.TIER_III, true, 0.28f, 1407, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_DYSPROSIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_HOLMIUM, Metal.Tier.TIER_III, true, 0.27f, 1461, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_HOLMIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_ERBIUM, Metal.Tier.TIER_III, true, 0.28f, 1529, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_ERBIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_THULIUM, Metal.Tier.TIER_II, true, 0.27f, 1545, 0xFF8A9597, ToolMaterialsTFCE.DAMASCUS_THULIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_YTTERBIUM, Metal.Tier.TIER_I, true, 0.27f, 824, 0xFF8F8F88, ToolMaterialsTFCE.DAMASCUS_YTTERBIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_LUTETIUM, Metal.Tier.TIER_III, true, 0.27f, 1652, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_LUTETIUM, ArmorMaterialsTFCE.INVAR));
        
        // Actinides
        r.register(new Metal(DAMASCUS_ACTINIUM, Metal.Tier.TIER_I, true, 0.27f, 1227, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_ACTINIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_THORIUM, Metal.Tier.TIER_I, true, 0.26f, 1750, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_THORIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_PROTACTINIUM, Metal.Tier.TIER_I, true, 0.27f, 1568, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_PROTACTINIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_URANIUM, Metal.Tier.TIER_I, true, 0.28f, 1132, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_URANIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_NEPTUNIUM, Metal.Tier.TIER_I, true, 0.29f, 640, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_NEPTUNIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_PLUTONIUM, Metal.Tier.TIER_I, true, 0.36f, 639, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_PLUTONIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_AMERICIUM, Metal.Tier.TIER_I, true, 0.63f, 1176, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_AMERICIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_CURIUM, Metal.Tier.TIER_I, true, 0.25f, 1340, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_CURIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_BERKELIUM, Metal.Tier.TIER_I, true, 0.25f, 986, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_BERKELIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_CALIFORNIUM, Metal.Tier.TIER_I, true, 0.25f, 900, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_CALIFORNIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_EINSTEINIUM, Metal.Tier.TIER_I, true, 0.25f, 860, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_EINSTEINIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_FERMIUM, Metal.Tier.TIER_I, true, 0.25f, 1527, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_FERMIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_MENDELEVIUM, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_MENDELEVIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_NOBELIUM, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_NOBELIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_LAWRENCIUM, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_LAWRENCIUM, ArmorMaterialsTFCE.INVAR));

        // Alloys & Other
        r.register(new Metal(DAMASCUS_ALUMINIUM_BRASS, Metal.Tier.TIER_IV, true, 0.3f, 630, 0xFFDCDABE, ToolMaterialsTFCE.DAMASCUS_ALUMINIUM_BRASS, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_CONSTANTAN, Metal.Tier.TIER_II, true, 0.5f, 1200, 0xFFD28874, ToolMaterialsTFCE.DAMASCUS_CONSTANTAN, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_ELECTRUM, Metal.Tier.TIER_II, true, 0.5f, 1200, 0xFFDFB950, ToolMaterialsTFCE.DAMASCUS_ELECTRUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_INVAR, Metal.Tier.TIER_III, true, 0.35f, 1450, 0xFF40444A, ToolMaterialsTFCE.DAMASCUS_INVAR, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_NICKEL_SILVER, Metal.Tier.TIER_II, true, 0.35f, 1450, 0xFFA4A4A5, ToolMaterialsTFCE.DAMASCUS_NICKEL_SILVER, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_ORICHALCUM, Metal.Tier.TIER_II, true, 0.5f, 1020, 0xFFB39F44, ToolMaterialsTFCE.DAMASCUS_ORICHALCUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_RED_ALLOY, Metal.Tier.TIER_II, true, 0.35f, 1080, 0xFFDA6E6E, ToolMaterialsTFCE.DAMASCUS_RED_ALLOY, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_TUNGSTEN_STEEL, Metal.Tier.TIER_VI, true, 0.2f, 3695, 0xFF565F6E, ToolMaterialsTFCE.DAMASCUS_TUNGSTEN_STEEL, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_STAINLESS_STEEL, Metal.Tier.TIER_VI, true, 0.2f, 1510, 0xFF5F5F5F, ToolMaterialsTFCE.DAMASCUS_STAINLESS_STEEL, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_LOCKALLOY, Metal.Tier.TIER_V, true, 0.2f, 1435, 0xFF5F5F5F, ToolMaterialsTFCE.DAMASCUS_LOCKALLOY, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_MANGANIN, Metal.Tier.TIER_II, true, 0.5f, 1020, 0xFFD28874, ToolMaterialsTFCE.DAMASCUS_MANGANIN, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_GALINSTAN, Metal.Tier.TIER_I, true, 0.26f, 11, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_GALINSTAN, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_CROWN_GOLD, Metal.Tier.TIER_I, true, 0.26f, 1030, 0xFFF0E630, ToolMaterialsTFCE.DAMASCUS_CROWN_GOLD, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_WHITE_GOLD, Metal.Tier.TIER_I, true, 0.26f, 1030, 0xFFF9F9F9, ToolMaterialsTFCE.DAMASCUS_WHITE_GOLD, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_SOLDER, Metal.Tier.TIER_I, true, 0.18f, 185, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_SOLDER, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_MAGNOX, Metal.Tier.TIER_I, true, 0.25f, 850, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_MAGNOX, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_PLATINUM_STERLING, Metal.Tier.TIER_V, true, 0.35f, 1730, 0xFF9DADC0, ToolMaterialsTFCE.DAMASCUS_PLATINUM_STERLING, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_TITANIUM_GOLD, Metal.Tier.TIER_VI, true, 0.35f, 1768, 0xFFECD940, ToolMaterialsTFCE.DAMASCUS_TITANIUM_GOLD, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_PEWTER, Metal.Tier.TIER_II, true, 0.35f, 230, 0xFF51707B, ToolMaterialsTFCE.DAMASCUS_PEWTER, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_CAST_IRON, Metal.Tier.TIER_II, true, 0.35f, 1535, 0xFF5F5F5F, ToolMaterialsTFCE.DAMASCUS_CAST_IRON, ArmorMaterialsTFCE.INVAR));
        
        // Fantasy
        r.register(new Metal(DAMASCUS_MITHRIL, Metal.Tier.TIER_II, true, 0.35f, 940, 0xFF8ADAF6, ToolMaterialsTFCE.DAMASCUS_MITHRIL, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_ARDITE, Metal.Tier.TIER_IV, true, 0.35f, 960, 0xFF40444A, ToolMaterialsTFCE.DAMASCUS_ARDITE, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_MANYULLYN, Metal.Tier.TIER_VI, true, 0.3f, 1550, 0xFF40444A, ToolMaterialsTFCE.DAMASCUS_MANYULLYN, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_ALCHEMICAL_BRASS, Metal.Tier.TIER_IV, true, 0.3f, 630, 0xFFCD9438, ToolMaterialsTFCE.DAMASCUS_ALCHEMICAL_BRASS, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_THAUMIUM, Metal.Tier.TIER_III, true, 0.3f, 1535, 0xFF493C70, ToolMaterialsTFCE.DAMASCUS_THAUMIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_VOIDMETAL, Metal.Tier.TIER_IV, true, 0.3f, 1535, 0xFF140921, ToolMaterialsTFCE.DAMASCUS_VOIDMETAL, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_SIGNALUM, Metal.Tier.TIER_II, true, 0.3f, 985, 0xFF140921, ToolMaterialsTFCE.DAMASCUS_SIGNALUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_LUMIUM, Metal.Tier.TIER_II, true, 0.3f, 420, 0xFF140921, ToolMaterialsTFCE.DAMASCUS_LUMIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_ENDERIUM, Metal.Tier.TIER_III, true, 0.3f, 420, 0xFF0D5E60, ToolMaterialsTFCE.DAMASCUS_ENDERIUM, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_ADAMANTIUM, Metal.Tier.TIER_V, true, 0.2f, 1635, 0xFFAD2335, ToolMaterialsTFCE.DAMASCUS_ADAMANTIUM, ArmorMaterialsTFCE.INVAR));
    	*/

        r.register(new Metal(DAMASCUS_BISMUTH, Metal.Tier.TIER_I, true, 0.14f, 270, 0xFF486B72, null, null));
        r.register(new Metal(DAMASCUS_BISMUTH_BRONZE, Metal.Tier.TIER_II, true, 0.35f, 985, 0xFF418E4F, ToolMaterialsTFCE.DAMASCUS_BISMUTH_BRONZE, ArmorMaterialsTFCE.TITANIUM));
        r.register(new Metal(DAMASCUS_BLACK_BRONZE, Metal.Tier.TIER_II, true, 0.35f, 1070, 0xFF3B2636, ToolMaterialsTFCE.DAMASCUS_BLACK_BRONZE, ArmorMaterialsTFCE.TITANIUM));
        r.register(new Metal(DAMASCUS_BRASS, Metal.Tier.TIER_I, true, 0.35f, 930, 0xFF96892E, null, null));
        r.register(new Metal(DAMASCUS_BRONZE, Metal.Tier.TIER_II, true, 0.35f, 950, 0xFF7C5E33, ToolMaterialsTFCE.DAMASCUS_BRONZE, ArmorMaterialsTFCE.TITANIUM));
        r.register(new Metal(DAMASCUS_COPPER, Metal.Tier.TIER_I, true, 0.35f, 950, 0xFF7C5E33, ToolMaterialsTFCE.DAMASCUS_COPPER, ArmorMaterialsTFCE.TITANIUM));
        r.register(new Metal(DAMASCUS_GOLD, Metal.Tier.TIER_I, true, 0.6f, 1060, 0xFFDCBF1B, null, null));
        r.register(new Metal(DAMASCUS_ROSE_GOLD, Metal.Tier.TIER_I, true, 0.35f, 960, 0xFFEB7137, null, null));
        r.register(new Metal(DAMASCUS_SILVER, Metal.Tier.TIER_I, true, 0.48f, 961, 0xFF949495, null, null));
        r.register(new Metal(DAMASCUS_TIN, Metal.Tier.TIER_I, true, 0.14f, 230, 0xFF90A4BB, null, null));
        r.register(new Metal(DAMASCUS_ZINC, Metal.Tier.TIER_I, true, 0.21f, 420, 0xFFBBB9C4, null, null));
        r.register(new Metal(DAMASCUS_STERLING_SILVER, Metal.Tier.TIER_I, true, 0.35f, 900, 0xFFAC927B, null, null));
        r.register(new Metal(DAMASCUS_WROUGHT_IRON, Metal.Tier.TIER_III, true, 0.35f, 1535, 0xFF989897, ToolMaterialsTFCE.DAMASCUS_WROUGHT_IRON, ArmorMaterialsTFCE.TITANIUM));
        r.register(new Metal(DAMASCUS_PIG_IRON, Metal.Tier.TIER_III, true, 0.35f, 1535, 0xFF6A595C, null, null));
        r.register(new Metal(DAMASCUS_STEEL, Metal.Tier.TIER_IV, true, 0.35f, 1540, 0xFF5F5F5F, ToolMaterialsTFCE.DAMASCUS_STEEL, ArmorMaterialsTFCE.TITANIUM));
        r.register(new Metal(DAMASCUS_PLATINUM, Metal.Tier.TIER_V, true, 0.35f, 1730, 0xFF9DADC0, null, null));
        r.register(new Metal(DAMASCUS_BLACK_STEEL, Metal.Tier.TIER_V, true, 0.35f, 1485, 0xFF111111, ToolMaterialsTFCE.DAMASCUS_BLACK_STEEL, ArmorMaterialsTFCE.TITANIUM));
        r.register(new Metal(DAMASCUS_BLUE_STEEL, Metal.Tier.TIER_VI, true, 0.35f, 1540, 0xFF2D5596, ToolMaterialsTFCE.DAMASCUS_BLUE_STEEL, ArmorMaterialsTFCE.TITANIUM));
        r.register(new Metal(DAMASCUS_RED_STEEL, Metal.Tier.TIER_VI, true, 0.35f, 1540, 0xFF700503, ToolMaterialsTFCE.DAMASCUS_RED_STEEL, ArmorMaterialsTFCE.TITANIUM));

        // Alkali metals
        r.register(new Metal(DAMASCUS_LITHIUM, Metal.Tier.TIER_I, true, 0.25f, 180, 0xFFC0C0C0, null, null));
        r.register(new Metal(DAMASCUS_NATRIUM, Metal.Tier.TIER_I, true, 0.28f, 98, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_KALIUM, Metal.Tier.TIER_I, true, 0.30f, 64, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_RUBIDIUM, Metal.Tier.TIER_I, true, 0.31f, 39, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_CAESIUM, Metal.Tier.TIER_I, true, 0.32f, 29, 0xFFEEE8AC, null, null));
        r.register(new Metal(DAMASCUS_FRANCIUM, Metal.Tier.TIER_I, true, 0.34f, 8, 0xFFD3D3D3, null, null));
        
        // Alkaline earth metals
        r.register(new Metal(DAMASCUS_BERYLLIUM, Metal.Tier.TIER_III, true, 0.16f, 1287, 0xFFA1A2A4, null, null));
        r.register(new Metal(DAMASCUS_MAGNESIUM, Metal.Tier.TIER_I, true, 0.25f, 650, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_CALCIUM, Metal.Tier.TIER_I, true, 0.26f, 842, 0xFF8A9597, null, null));
        r.register(new Metal(DAMASCUS_STRONTIUM, Metal.Tier.TIER_I, true, 0.26f, 777, 0xFFC0C0C0, null, null));
        r.register(new Metal(DAMASCUS_BARIUM, Metal.Tier.TIER_I, true, 0.28f, 727, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_RADIUM, Metal.Tier.TIER_I, true, 0.29f, 700, 0xFFD3D3D3, null, null));
        
        // Transition metals
        r.register(new Metal(DAMASCUS_SCANDIUM, Metal.Tier.TIER_III, true, 0.26f, 1541, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_TITANIUM, Metal.Tier.TIER_VI, true, 0.3f, 1700, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_TITANIUM, ArmorMaterialsTFCE.TITANIUM));
        r.register(new Metal(DAMASCUS_VANADIUM, Metal.Tier.TIER_IV, true, 0.25f, 1910, 0xFFC0C0C0, null, null));
        r.register(new Metal(DAMASCUS_CHROMIUM, Metal.Tier.TIER_V, true, 0.23f, 1907, 0xFF8A9597, null, null));
        r.register(new Metal(DAMASCUS_MANGANESE, Metal.Tier.TIER_IV, true, 0.26f, 1246, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_COBALT, Metal.Tier.TIER_VI, true, 0.3f, 1495, 0xFFD7DAEB, ToolMaterialsTFCE.DAMASCUS_COBALT, ArmorMaterialsTFCE.COBALT));
        r.register(new Metal(DAMASCUS_YTTRIUM, Metal.Tier.TIER_I, true, 0.27f, 1526, 0xFFD2D2D2, null, null));
        r.register(new Metal(DAMASCUS_ZIRCONIUM, Metal.Tier.TIER_III, true, 0.25f, 1855, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_NIOBIUM, Metal.Tier.TIER_IV, true, 0.25f, 2477, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_MOLYBDENUM, Metal.Tier.TIER_III, true, 0.24f, 2623, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_TECHNETIUM, Metal.Tier.TIER_III, true, 0.24f, 2157, 0xFF89979A, null, null));
        r.register(new Metal(DAMASCUS_RUTHENIUM, Metal.Tier.TIER_IV, true, 0.24f, 2334, 0xFFA4A3A6, null, null));
        r.register(new Metal(DAMASCUS_RHODIUM, Metal.Tier.TIER_IV, true, 0.25f, 1964, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_PALLADIUM, Metal.Tier.TIER_III, true, 0.26f, 1555, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_PALLADIUM, ArmorMaterialsTFCE.PALLADIUM));
        r.register(new Metal(DAMASCUS_HAFNIUM, Metal.Tier.TIER_III, true, 0.26f, 2233, 0xFFC0C0C0, null, null));
        r.register(new Metal(DAMASCUS_TANTALUM, Metal.Tier.TIER_IV, true, 0.26f, 3017, 0xFF808080, null, null));
        r.register(new Metal(DAMASCUS_TUNGSTEN, Metal.Tier.TIER_VI, true, 0.2f, 3400, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_TUNGSTEN, ArmorMaterialsTFCE.TUNGSTEN));
        r.register(new Metal(DAMASCUS_RHENIUM, Metal.Tier.TIER_V, true, 0.25f, 3186, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_OSMIUM, Metal.Tier.TIER_VI, true, 0.35f, 3025, 0xFF65D1D4, ToolMaterialsTFCE.DAMASCUS_OSMIUM, ArmorMaterialsTFCE.OSMIUM));
        r.register(new Metal(DAMASCUS_IRIDIUM, Metal.Tier.TIER_IV, true, 0.25f, 2446, 0xFFD3D3D3, ToolMaterialsTFCE.DAMASCUS_IRIDIUM, ArmorMaterialsTFCE.IRIDIUM));
        r.register(new Metal(DAMASCUS_RUTHERFORDIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(DAMASCUS_DUBNIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(DAMASCUS_SEABORGIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(DAMASCUS_BOHRIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(DAMASCUS_HASSIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(DAMASCUS_COPERNICIUM, Metal.Tier.TIER_I, true, 0.26f, 10, 0xFFD4CDC2, null, null));
        
        // Post-transition metals
        r.register(new Metal(DAMASCUS_ALUMINIUM, Metal.Tier.TIER_IV, true, 0.3f, 660, 0xFFC0C0C0, ToolMaterialsTFCE.DAMASCUS_ALUMINIUM, ArmorMaterialsTFCE.ALUMINIUM));
        r.register(new Metal(DAMASCUS_GALLIUM, Metal.Tier.TIER_I, true, 0.26f, 30, 0xFFC0C0C0, null, null));
        r.register(new Metal(DAMASCUS_CADMIUM, Metal.Tier.TIER_II, true, 0.26f, 321, 0xFF65D1D4, null, null));
        r.register(new Metal(DAMASCUS_INDIUM, Metal.Tier.TIER_I, true, 0.27f, 157, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_MERCURY, Metal.Tier.TIER_I, true, 0.28f, 0, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_THALLIUM, Metal.Tier.TIER_I, true, 0.26f, 304, 0xFF8A9597, null, null));
        r.register(new Metal(DAMASCUS_POLONIUM, Metal.Tier.TIER_I, true, 0.26f, 254, 0xFF8A9597, null, null));
        
        // Metalloid
        r.register(new Metal(DAMASCUS_BORON, Metal.Tier.TIER_VI, true, 0.11f, 2076, 0xFF4E4E4E, null, null));
        r.register(new Metal(DAMASCUS_SILICIUM, Metal.Tier.TIER_IV, true, 0.20f, 1414, 0xFFC0C0C0, null, null));
        r.register(new Metal(DAMASCUS_GERMANIUM, Metal.Tier.TIER_IV, true, 0.23f, 938, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_ARSENIC, Metal.Tier.TIER_II, true, 0.25f, 615, 0xFF808080, null, null));
        r.register(new Metal(DAMASCUS_ANTIMONY, Metal.Tier.TIER_I, true, 0.25f, 630, 0xFF808080, null, null));
        r.register(new Metal(DAMASCUS_TELLURIUM, Metal.Tier.TIER_II, true, 0.26f, 450, 0xFFC0C0C0, null, null));
        r.register(new Metal(DAMASCUS_ASTATINE, Metal.Tier.TIER_II, true, 0.11f, 150, 0xFFC0C0C0, null, null));
        
        // Lanthanides
        r.register(new Metal(DAMASCUS_LANTHANUM, Metal.Tier.TIER_II, true, 0.27f, 920, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_CERIUM, Metal.Tier.TIER_II, true, 0.27f, 795, 0xFF808080, null, null));
        r.register(new Metal(DAMASCUS_PRASEODYMIUM, Metal.Tier.TIER_II, true, 0.27f, 935, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_NEODYMIUM, Metal.Tier.TIER_II, true, 0.27f, 1024, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_PROMETHIUM, Metal.Tier.TIER_II, true, 0.27f, 1042, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_SAMARIUM, Metal.Tier.TIER_II, true, 0.30f, 1072, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_EUROPIUM, Metal.Tier.TIER_II, true, 0.28f, 826, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_GADOLINIUM, Metal.Tier.TIER_III, true, 0.37f, 1312, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_TERBIUM, Metal.Tier.TIER_III, true, 0.29f, 1356, 0xFF8A9597, null, null));
        r.register(new Metal(DAMASCUS_DYSPROSIUM, Metal.Tier.TIER_III, true, 0.28f, 1407, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_HOLMIUM, Metal.Tier.TIER_III, true, 0.27f, 1461, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_ERBIUM, Metal.Tier.TIER_III, true, 0.28f, 1529, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_THULIUM, Metal.Tier.TIER_II, true, 0.27f, 1545, 0xFF8A9597, null, null));
        r.register(new Metal(DAMASCUS_YTTERBIUM, Metal.Tier.TIER_I, true, 0.27f, 824, 0xFF8F8F88, null, null));
        r.register(new Metal(DAMASCUS_LUTETIUM, Metal.Tier.TIER_III, true, 0.27f, 1652, 0xFFD3D3D3, null, null));
        
        // Actinides
        r.register(new Metal(DAMASCUS_ACTINIUM, Metal.Tier.TIER_I, true, 0.27f, 1227, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_THORIUM, Metal.Tier.TIER_I, true, 0.26f, 1750, 0xFFC0C0C0, null, null));
        r.register(new Metal(DAMASCUS_PROTACTINIUM, Metal.Tier.TIER_I, true, 0.27f, 1568, 0xFFC0C0C0, null, null));
        r.register(new Metal(DAMASCUS_URANIUM, Metal.Tier.TIER_I, true, 0.28f, 1132, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_NEPTUNIUM, Metal.Tier.TIER_I, true, 0.29f, 640, 0xFFC0C0C0, null, null));
        r.register(new Metal(DAMASCUS_PLUTONIUM, Metal.Tier.TIER_I, true, 0.36f, 639, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_AMERICIUM, Metal.Tier.TIER_I, true, 0.63f, 1176, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_CURIUM, Metal.Tier.TIER_I, true, 0.25f, 1340, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_BERKELIUM, Metal.Tier.TIER_I, true, 0.25f, 986, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_CALIFORNIUM, Metal.Tier.TIER_I, true, 0.25f, 900, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_EINSTEINIUM, Metal.Tier.TIER_I, true, 0.25f, 860, 0xFFC0C0C0, null, null));
        r.register(new Metal(DAMASCUS_FERMIUM, Metal.Tier.TIER_I, true, 0.25f, 1527, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_MENDELEVIUM, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_NOBELIUM, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_LAWRENCIUM, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFFD3D3D3, null, null));

        // Alloys & Other
        r.register(new Metal(DAMASCUS_ALUMINIUM_BRASS, Metal.Tier.TIER_IV, true, 0.3f, 630, 0xFFDCDABE, null, null));
        r.register(new Metal(DAMASCUS_CONSTANTAN, Metal.Tier.TIER_II, true, 0.5f, 1200, 0xFFD28874, null, null));
        r.register(new Metal(DAMASCUS_ELECTRUM, Metal.Tier.TIER_II, true, 0.5f, 1200, 0xFFDFB950, null, null));
        r.register(new Metal(DAMASCUS_INVAR, Metal.Tier.TIER_III, true, 0.35f, 1450, 0xFF40444A, ToolMaterialsTFCE.DAMASCUS_INVAR, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(DAMASCUS_NICKEL_SILVER, Metal.Tier.TIER_II, true, 0.35f, 1450, 0xFFA4A4A5, ToolMaterialsTFCE.DAMASCUS_NICKEL_SILVER, ArmorMaterialsTFCE.NICKEL_SILVER));
        r.register(new Metal(DAMASCUS_ORICHALCUM, Metal.Tier.TIER_II, true, 0.5f, 1020, 0xFFB39F44, null, null));
        r.register(new Metal(DAMASCUS_RED_ALLOY, Metal.Tier.TIER_II, true, 0.35f, 1080, 0xFFDA6E6E, null, null));
        r.register(new Metal(DAMASCUS_TUNGSTEN_STEEL, Metal.Tier.TIER_VI, true, 0.2f, 3695, 0xFF565F6E, ToolMaterialsTFCE.DAMASCUS_TUNGSTEN_STEEL, ArmorMaterialsTFCE.TUNGSTEN_STEEL));
        r.register(new Metal(DAMASCUS_STAINLESS_STEEL, Metal.Tier.TIER_VI, true, 0.2f, 1510, 0xFF5F5F5F, ToolMaterialsTFCE.DAMASCUS_STAINLESS_STEEL, ArmorMaterialsTFCE.STAINLESS_STEEL));
        r.register(new Metal(DAMASCUS_LOCKALLOY, Metal.Tier.TIER_V, true, 0.2f, 1435, 0xFF5F5F5F, null, null));
        r.register(new Metal(DAMASCUS_MANGANIN, Metal.Tier.TIER_II, true, 0.5f, 1020, 0xFFD28874, null, null));
        r.register(new Metal(DAMASCUS_GALINSTAN, Metal.Tier.TIER_I, true, 0.26f, 11, 0xFFC0C0C0, null, null));
        r.register(new Metal(DAMASCUS_CROWN_GOLD, Metal.Tier.TIER_I, true, 0.26f, 1030, 0xFFF0E630, null, null));
        r.register(new Metal(DAMASCUS_WHITE_GOLD, Metal.Tier.TIER_I, true, 0.26f, 1030, 0xFFF9F9F9, null, null));
        r.register(new Metal(DAMASCUS_SOLDER, Metal.Tier.TIER_I, true, 0.18f, 185, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_MAGNOX, Metal.Tier.TIER_I, true, 0.25f, 850, 0xFFD3D3D3, null, null));
        r.register(new Metal(DAMASCUS_PLATINUM_STERLING, Metal.Tier.TIER_V, true, 0.35f, 1730, 0xFF9DADC0, null, null));
        r.register(new Metal(DAMASCUS_TITANIUM_GOLD, Metal.Tier.TIER_VI, true, 0.35f, 1768, 0xFFECD940, ToolMaterialsTFCE.DAMASCUS_TITANIUM_GOLD, ArmorMaterialsTFCE.TITANIUM_GOLD));
        r.register(new Metal(DAMASCUS_PEWTER, Metal.Tier.TIER_II, true, 0.35f, 230, 0xFF51707B, null, null));
        r.register(new Metal(DAMASCUS_CAST_IRON, Metal.Tier.TIER_II, true, 0.35f, 1535, 0xFF5F5F5F, null, null));
        
        // Fantasy
        r.register(new Metal(DAMASCUS_MITHRIL, Metal.Tier.TIER_II, true, 0.35f, 940, 0xFF8ADAF6, ToolMaterialsTFCE.DAMASCUS_MITHRIL, ArmorMaterialsTFCE.MITHRIL));
        r.register(new Metal(DAMASCUS_ARDITE, Metal.Tier.TIER_IV, true, 0.35f, 960, 0xFF40444A, null, null));
        r.register(new Metal(DAMASCUS_MANYULLYN, Metal.Tier.TIER_VI, true, 0.3f, 1550, 0xFF40444A, ToolMaterialsTFCE.DAMASCUS_MANYULLYN, ArmorMaterialsTFCE.MANYULLYN));
        r.register(new Metal(DAMASCUS_ALCHEMICAL_BRASS, Metal.Tier.TIER_IV, true, 0.3f, 630, 0xFFCD9438, null, null));
        r.register(new Metal(DAMASCUS_THAUMIUM, Metal.Tier.TIER_III, true, 0.3f, 1535, 0xFF493C70, null, null));
        r.register(new Metal(DAMASCUS_VOIDMETAL, Metal.Tier.TIER_IV, true, 0.3f, 1535, 0xFF140921, null, null));
        r.register(new Metal(DAMASCUS_SIGNALUM, Metal.Tier.TIER_II, true, 0.3f, 985, 0xFF140921, null, null));
        r.register(new Metal(DAMASCUS_LUMIUM, Metal.Tier.TIER_II, true, 0.3f, 420, 0xFF140921, null, null));
        r.register(new Metal(DAMASCUS_ENDERIUM, Metal.Tier.TIER_III, true, 0.3f, 420, 0xFF0D5E60, null, null));
        r.register(new Metal(DAMASCUS_ADAMANTIUM, Metal.Tier.TIER_V, true, 0.2f, 1635, 0xFFAD2335, ToolMaterialsTFCE.DAMASCUS_ADAMANTIUM, ArmorMaterialsTFCE.ADAMANTIUM));
    }

    @SubscribeEvent
    public static void onPreRegisterOre(TFCRegistryEvent.RegisterPreBlock<Ore> event)
    {
        IForgeRegistry<Ore> r = event.getRegistry();

        // Reactive nonmetals
        r.register(new Ore(ANTHRACITE)); //Coal
        
        // Other minerals
        
        // Reactive nonmetals
        r.register(new Ore(FLUORITE));
        r.register(new Ore(PHOSPHORITE));
        r.register(new Ore(SELENIDE));
        r.register(new Ore(IODATE));
        r.register(new Ore(SALAMMONIAC));
        
        // Noble gases
        // None
        
        // Alkali metals
        r.register(new Ore(SPODUMENE, LITHIUM, true)); //Lithium
        r.register(new Ore(HALITE, NATRIUM, true)); //Natrium/Sodium
        r.register(new Ore(CARNALLITE, KALIUM, true)); //Salt //Sylvite //Potash
        r.register(new Ore(LEPIDOLITE, RUBIDIUM, true)); //Rubidium
        r.register(new Ore(POLLUCITE, CAESIUM, true)); //Caesium
        //r.register(new Ore(URANINITE, FRANCIUM, false)); //Francium
        
        // Alkaline earth metals
        r.register(new Ore(BERTRANDITE, BERYLLIUM, true)); //Beryllium
        r.register(new Ore(MAGNESITE, MAGNESIUM, true)); //Magnesium
        //r.register(new Ore(CALCIUM, CALCIUM, true)); //Calcium //Gypsum, bones, shellfish and all that stuff
        r.register(new Ore(CELESTINE, STRONTIUM, true)); //Strontium
        r.register(new Ore(BARYTE, BARIUM, true)); //Barium
        //r.register(new Ore(URANINITE, RADIUM, true)); //Radium
        
        // Transition metals
        r.register(new Ore(THORTVEITITE, SCANDIUM, true)); //Scandium
        r.register(new Ore(RUTILE, TITANIUM, false)); //Titanium
        r.register(new Ore(VANADINITE, VANADIUM, false)); //Vanadium
        r.register(new Ore(CHROMITE, CHROMIUM, false)); //Chrome
        r.register(new Ore(PYROLUSITE, MANGANESE, false)); //Manganese
        r.register(new Ore(PYRITE, CAST_IRON, false)); //Iron
        r.register(new Ore(COBALTITE, COBALT, false)); //Cobalt
        r.register(new Ore(CHALCOCITE, COPPER, false)); //Copper
        r.register(new Ore(CHALCOPYRITE, COPPER, false)); //Copper
        r.register(new Ore(XENOTIME, YTTRIUM, false)); //Yttrium
        r.register(new Ore(ZIRCON, ZIRCONIUM, false)); //Zirconium
        r.register(new Ore(COLUMBITE, NIOBIUM, false)); //Niobium
        r.register(new Ore(MOLYBDENITE, MOLYBDENUM, false)); //Molybdenum
        //r.register(new Ore(URANINITE, TECHNETIUM, false)); //Technetium //Found in uranium/pitchblende ore
        r.register(new Ore(RUTHENIRIDOSMIUM, RUTHENIUM, false)); //Ruthenium
        r.register(new Ore(PENTLANDITE, RUTHENIUM, false)); //Ruthenium
        r.register(new Ore(NATIVE_RHODIUM, RHODIUM, false)); //Rhodium //Barely even occurs in the Earth's crust //Usually found mixed with other metals
        r.register(new Ore(COOPERITE, PALLADIUM, false)); //Palladium
        r.register(new Ore(PYRARGYRITE, SILVER, true)); //Silver
        r.register(new Ore(HAFNON, HAFNIUM, false)); //Hafnium
        r.register(new Ore(TANTALITE, TANTALUM, false)); //Tantalum
        r.register(new Ore(WOLFRAMITE, TUNGSTEN, false)); //Wolfram
        r.register(new Ore(RHENIITE, RHENIUM, false)); //Rhenium
        r.register(new Ore(OSMIRIDIUM, OSMIUM, false)); //Osmium
        r.register(new Ore(IRIDOSMIUM, IRIDIUM, false)); //Iridium
        /*if (ModConfig.ORE_ADDITIONS.platinum) //todo add these if base TFC removes them
        {
            r.register(new Ore(NATIVE_PLATINUM, PLATINUM, true));
        }*/
        //r.register(new Ore(URANINITE, RUTHERFORDIUM, false)); //Rutherfordium
        //r.register(new Ore(URANINITE, DUBNIUM, false)); //Dubnium
        //r.register(new Ore(URANINITE, SEABORGIUM, false)); //Seaborgium
        //r.register(new Ore(URANINITE, BOHRIUM, false)); //Bohrium
        //r.register(new Ore(URANINITE, HASSIUM, false)); //Hassium
        
        // Post-transition metals
        r.register(new Ore(BAUXITE, ALUMINIUM, false)); //Aluminium
        r.register(new Ore(GALLITE, GALLIUM, true)); //Gallium //Also a by-product from Bauxite or Sphalerite
        r.register(new Ore(GREENOCKITE, CADMIUM, true)); //Cadmium
        r.register(new Ore(ROQUESITE, INDIUM, true)); //Indium //Or found as trace constrituent in Sphalerite
        r.register(new Ore(LIVINGSTONITE, MERCURY, true)); //Mercury
        r.register(new Ore(LORANDITE, THALLIUM, true)); //Thallium //LORÁNDITE
        /*if (ModConfig.ORE_ADDITIONS.galena)
        {
            r.register(new Ore(GALENA, LEAD, true));
        }*/
        //r.register(new Ore(URANINITE, POLONIUM, true)); //Polonium
        //r.register(new Ore(URANINITE, COPERNICIUM, true)); //Copernicium
        
        // Metalloid
        r.register(new Ore(ULEXITE, BORON, false)); //Boron
        r.register(new Ore(SILICA, SILICIUM, false)); //Silicium //Just process sand
        r.register(new Ore(GERMANITE, GERMANIUM, false)); //Germanium
        r.register(new Ore(REALGAR, ARSENIC, true)); //Arsenic
        r.register(new Ore(STIBNITE, ANTIMONY, true)); //Antimony
        r.register(new Ore(CALAVERITE, TELLURIUM, true)); //Tellurium
        //r.register(new Ore(URANINITE, ASTATINE, true)); //Astatine
        
        // Lanthanides
        r.register(new Ore(MONAZITE, LANTHANUM, false)); //Lanthanum
        r.register(new Ore(BASTNAESITE, CERIUM, true)); //Cerium //BASTNÄSITE
        //r.register(new Ore(MONAZITE, PRASEODYMIUM, false)); //Occurs in a variant of monazite
        r.register(new Ore(KOZOITE, NEODYMIUM, false)); //Neodymium
        //r.register(new Ore(URANINITE, PROMETHIUM, false)); //Promethium
        r.register(new Ore(SAMARSKITE, SAMARIUM, false)); //Samarium
        r.register(new Ore(LOPARITE, EUROPIUM, true)); //Europium
        r.register(new Ore(GADOLINITE, GADOLINIUM, false)); //Gadolinium
        r.register(new Ore(EUXENITE, TERBIUM, false)); //Terbium
        r.register(new Ore(FERGUSONITE, DYSPROSIUM, false)); //Dysprosium
        //r.register(new Ore(MONAZITE, HOLMIUM, false)); //Holmium //Also found in monazite
        //r.register(new Ore(MONAZITE, ERBIUM, false)); //Erbium //Also found in monazite
        //r.register(new Ore(MONAZITE, THULIUM, false)); //Thulium //Also found in monazite, xenotime and euxenite
        //r.register(new Ore(MONAZITE, YTTERBIUM, false)); //Ytterbium //Also found in monazite, xenotime and euxenite
        //r.register(new Ore(MONAZITE, LUTETIUM, false)); //Lutetium //Also found in monazite
        
        // Actinides
        //r.register(new Ore(URANINITE, ACTINIUM, false)); //Actinium
        r.register(new Ore(THORIANITE, THORIUM, false)); //Thorium
        //r.register(new Ore(URANINITE, PROTACTINIUM, false)); //Protactinium
        r.register(new Ore(URANINITE, URANIUM, false)); //Uranium
        /*if (ModConfig.ORE_ADDITIONS.pitchblende) //todo only if base TFC removes it
        {
            r.register(new Ore(PITCHBLENDE));
        }
        */
        //r.register(new Ore(URANINITE, NEPTUNIUM, false)); //Neptunium
        //r.register(new Ore(URANINITE, PLUTONIUM, false)); //Plutonium
        //r.register(new Ore(URANINITE, AMERICIUM, false)); //Americum
        //r.register(new Ore(URANINITE, CURIUM, false)); //Curium
        //r.register(new Ore(URANINITE, BERKELIUM, false)); //Berkelium
        //r.register(new Ore(URANINITE, CALIFORNIUM, false)); //Californium
        //r.register(new Ore(URANINITE, EINSTEINIUM, false)); //Einsteinium
        //r.register(new Ore(URANINITE, FERMIUM, false)); //Fermium
        //r.register(new Ore(URANINITE, MENDELEVIUM, false)); //Mendelevium
        //r.register(new Ore(URANINITE, NOBELIUM, false)); //Nobelium
        //r.register(new Ore(URANINITE, LAWRENCIUM, false)); //Lawrencium
        
        //Gases
        //r.register(new Ore(URANINITE, MEITNERIUM, false)); //Meitner
        //r.register(new Ore(URANINITE, DARMSTADTIUM, false)); //Darmstadtium
        //r.register(new Ore(URANINITE, ROENTGENIUM, false)); //Roentgenium
        //r.register(new Ore(URANINITE, NIHONIUM, false)); //Nihonium
        //r.register(new Ore(URANINITE, FLEROVIUM, false)); //Flerovium
        //r.register(new Ore(URANINITE, MOSCOVIUM, false)); //Moscovium
        //r.register(new Ore(URANINITE, LIVERMORIUM, false)); //Livermorium
        //r.register(new Ore(URANINITE, TENNESSINE, false)); //Tennessine
        //r.register(new Ore(URANINITE, OGANESSON, false)); //Oganesson
        
        // Fantasy
        r.register(new Ore(NATIVE_ARDITE, ARDITE, true));
        r.register(new Ore(ADAMANTITE, ADAMANTIUM, true));
        
        // Geodes
        r.register(new Ore(GEODE_AGATE));
        r.register(new Ore(GEODE_AMETHYST));
        r.register(new Ore(GEODE_BERYL));
        r.register(new Ore(GEODE_DIAMOND));
        r.register(new Ore(GEODE_EMERALD));
        r.register(new Ore(GEODE_GARNET));
        r.register(new Ore(GEODE_JADE));
        r.register(new Ore(GEODE_JASPER));
        r.register(new Ore(GEODE_OPAL));
        r.register(new Ore(GEODE_RUBY));
        r.register(new Ore(GEODE_SAPPHIRE));
        r.register(new Ore(GEODE_TOPAZ));
        r.register(new Ore(GEODE_TOURMALINE));
        r.register(new Ore(GEODE_AMBER));
        r.register(new Ore(GEODE_APATITE));
        r.register(new Ore(GEODE_AQUAMARINE));
        r.register(new Ore(GEODE_BROMARGYRITE));
        r.register(new Ore(GEODE_CITRINE));
        r.register(new Ore(GEODE_HELIODOR));
        r.register(new Ore(GEODE_IODARGYRITE));
        r.register(new Ore(GEODE_KYANITE));
        r.register(new Ore(GEODE_MOLDAVITE));
        r.register(new Ore(GEODE_MOONSTONE));
        r.register(new Ore(GEODE_PYROMORPHITE));
        r.register(new Ore(GEODE_QUARTZ));
        r.register(new Ore(GEODE_SPINEL));
        r.register(new Ore(GEODE_SUNSTONE));
        r.register(new Ore(GEODE_TANZANITE));
        r.register(new Ore(GEODE_ZIRCON));
    }

    @SubscribeEvent
    public static void onRegisterAlloyRecipe(RegistryEvent.Register<AlloyRecipe> event)
    {
        IForgeRegistry<AlloyRecipe> r = event.getRegistry();
        r.register(new AlloyRecipe.Builder(ALUMINIUM_BRASS).add(ALUMINIUM, 0.65, 0.85).add(COPPER, 0.15, 0.35).build());
        r.register(new AlloyRecipe.Builder(CONSTANTAN).add(COPPER, 0.4, 0.6).add(NICKEL, 0.4, 0.6).build());
        //r.register(new AlloyRecipe.Builder(CONSTANTAN).add(COPPER_NICKEL, 1.0, 1.0).build());
        r.register(new AlloyRecipe.Builder(ELECTRUM).add(GOLD, 0.4, 0.6).add(SILVER, 0.4, 0.6).build());
        r.register(new AlloyRecipe.Builder(INVAR).add(WROUGHT_IRON, 0.6, 0.7).add(NICKEL, 0.3, 0.4).build());
        //r.register(new AlloyRecipe.Builder(INVAR).add(WROUGHT_IRON, 0.2, 0.4).add(WROUGHT_IRON_NICKEL, 0.6, 0.8).build());
        r.register(new AlloyRecipe.Builder(MANYULLYN).add(COBALT, 0.4, 0.6).add(ARDITE, 0.4, 0.6).build());
        r.register(new AlloyRecipe.Builder(MITHRIL).add(COPPER, 0.85, 0.90).add(ANTIMONY, 0.08, 0.1).add(MERCURY, 0.02, 0.05).build());
        r.register(new AlloyRecipe.Builder(ORICHALCUM).add(COPPER, 0.75, 0.8).add(ZINC, 0.20, 0.25).build());
        r.register(new AlloyRecipe.Builder(NICKEL_SILVER).add(COPPER, 0.40, 0.70).add(ZINC, 0.15, 0.3).add(NICKEL, 0.15, 0.3).build());
        //r.register(new AlloyRecipe.Builder(NICKEL_SILVER).add(COPPER, 0.10, 0.55).add(ZINC, 0.15, 0.3).add(COPPER_NICKEL, 0.3, 0.6).build());
        //r.register(new AlloyRecipe.Builder(NICKEL_SILVER).add(COPPER, 0.40, 0.70).add(ZINC_NICKEL, 0.3, 0.6).build());
        r.register(new AlloyRecipe.Builder(TUNGSTEN_STEEL).add(TUNGSTEN, 0.02, 0.18).add(STEEL, 0.72, 0.98).build());
        r.register(new AlloyRecipe.Builder(STAINLESS_STEEL).add(STEEL, 0.68, 0.80).add(CHROMIUM, 0.11, 0.18).add(NICKEL, 0.08, 0.1).add(MANGANESE, 0.01, 0.02).add(MOLYBDENUM, 0.0, 0.02).build());
        r.register(new AlloyRecipe.Builder(LOCKALLOY).add(BERYLLIUM, 0.64, 0.60).add(ALUMINIUM, 0.36, 0.40).build());
        r.register(new AlloyRecipe.Builder(MANGANIN).add(COPPER, 0.80, 0.86).add(MANGANESE, 0.11, 0.14).add(NICKEL, 0.03, 0.06).build());
        r.register(new AlloyRecipe.Builder(GALINSTAN).add(GALLIUM, 0.72, 0.67).add(INDIUM, 0.20, 0.22).add(TIN, 0.08, 0.11).build());
        r.register(new AlloyRecipe.Builder(CROWN_GOLD).add(GOLD, 0.90, 0.92).add(COPPER, 0.08, 0.10).build());
        r.register(new AlloyRecipe.Builder(WHITE_GOLD).add(GOLD, 0.90, 0.80).add(SILVER, 0.05, 0.10).add(PALLADIUM, 0.05, 0.10).build());
        r.register(new AlloyRecipe.Builder(SOLDER).add(TIN, 0.65, 0.55).add(LEAD, 0.35, 0.45).build());
        r.register(new AlloyRecipe.Builder(MAGNOX).add(MAGNESIUM, 0.98, 0.96).add(ALUMINIUM, 0.01, 0.02).add(BERYLLIUM, 0.01, 0.02).build());
        r.register(new AlloyRecipe.Builder(PLATINUM_STERLING).add(SILVER, 0.95, 0.90).add(PLATINUM, 0.05, 0.10).build());
        r.register(new AlloyRecipe.Builder(TITANIUM_GOLD).add(TITANIUM, 0.80, 0.70).add(GOLD, 0.20, 0.30).build());
        r.register(new AlloyRecipe.Builder(PEWTER).add(TIN, 0.92, 0.84).add(ANTIMONY, 0.05, 0.10).add(COPPER, 0.01, 0.02).add(BISMUTH, 0.01, 0.02).add(SILVER, 0.01, 0.02).build());
        //r.register(new AlloyRecipe.Builder(PEWTER).add(TIN, 0.90, 0.80).add(COPPER, 0.05, 0.10).add(LEAD, 0.05, 0.10).build());
        //r.register(new AlloyRecipe.Builder(SIGNALUM).add(COPPER, 0.80, 0.60).add(CINNABAR, 0.15, 0.30).add(SILVER, 0.05, 0.10).build());
        //r.register(new AlloyRecipe.Builder(LUMIUM).add(TIN, 0.80, 0.60).add(PHOSPHORITE, 0.15, 0.30).add(SILVER, 0.05, 0.10).build());
        //r.register(new AlloyRecipe.Builder(ENDERIUM).add(LEAD, 0.80, 0.60).add(FLUORITE, 0.15, 0.30).add(PLATINUM, 0.05, 0.10).build());
        r.register(new AlloyRecipe.Builder(CAST_IRON_MANGANESE).add(CAST_IRON, 0.95, 0.85).add(MANGANESE, 0.05, 0.15).build());
        //r.register(new AlloyRecipe.Builder(WEAK_STEEL).add(STEEL, 0.5, 0.7).add(BLACK_BRONZE_NICKEL, 0.3, 0.5).build());
        //r.register(new AlloyRecipe.Builder(WEAK_STEEL).add(STEEL, 0.25, 0.35).add(STEEL_NICKEL, 0.3, 0.5).add(BLACK_BRONZE, 0.15, 0.25).build());
    }
}
