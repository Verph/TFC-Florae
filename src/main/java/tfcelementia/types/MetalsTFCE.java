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
	//Coal
    public static final ResourceLocation ANTHRACITE = new ResourceLocation(MODID, "anthracite");
	
	//Ores
    //Reactive nonmetals
    public static final ResourceLocation FLUORITE = new ResourceLocation(MODID, "fluorite");
    public static final ResourceLocation PHOSPHORITE = new ResourceLocation(MODID, "phosphorite");
    public static final ResourceLocation SELENIDE = new ResourceLocation(MODID, "selenide");
    public static final ResourceLocation IODATE = new ResourceLocation(MODID, "iodate");
    
    //Noble gases
    //None
    
    //Alkali metals
    public static final ResourceLocation SPODUMENE = new ResourceLocation(MODID, "spodumene");
    public static final ResourceLocation HALITE = new ResourceLocation(MODID, "halite");
    public static final ResourceLocation CARNALLITE = new ResourceLocation(MODID, "carnallite");
    public static final ResourceLocation LEPIDOLITE = new ResourceLocation(MODID, "lepidolite");
    public static final ResourceLocation POLLUCITE = new ResourceLocation(MODID, "pollucite");
    
    //Alkaline earth metals
    public static final ResourceLocation BERTRANDITE = new ResourceLocation(MODID, "bertrandite");
    public static final ResourceLocation MAGNESITE = new ResourceLocation(MODID, "magnesite");
    public static final ResourceLocation CELESTINE = new ResourceLocation(MODID, "celestine");
    public static final ResourceLocation BARYTE = new ResourceLocation(MODID, "baryte");
    
    //Transition metals
    public static final ResourceLocation THORTVEITITE = new ResourceLocation(MODID, "thortveitite");
    public static final ResourceLocation RUTILE = new ResourceLocation(MODID, "rutile"); //Titanium
    public static final ResourceLocation VANADINITE = new ResourceLocation(MODID, "vanadinite");
    public static final ResourceLocation CHROMITE = new ResourceLocation(MODID, "chromite");
    public static final ResourceLocation PYROLUSITE = new ResourceLocation(MODID, "pyrolusite");
    public static final ResourceLocation COBALTITE = new ResourceLocation(MODID, "cobaltite"); //Cobalt
    public static final ResourceLocation XENOTIME = new ResourceLocation(MODID, "xenotime");
    public static final ResourceLocation ZIRCON = new ResourceLocation(MODID, "zircon");
    public static final ResourceLocation COLUMBITE = new ResourceLocation(MODID, "columbite");
    public static final ResourceLocation MOLYBDENITE = new ResourceLocation(MODID, "molybdenite");
    public static final ResourceLocation PENTLANDITE = new ResourceLocation(MODID, "pentlandite");
    public static final ResourceLocation NATIVE_RHODIUM = new ResourceLocation(MODID, "native_rhodium");
    public static final ResourceLocation COOPERITE = new ResourceLocation(MODID, "cooperite");
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
    
    //Metalloid
    public static final ResourceLocation ULEXITE = new ResourceLocation(MODID, "ulexite");
    public static final ResourceLocation SILICA = new ResourceLocation(MODID, "silica");
    public static final ResourceLocation GERMANITE = new ResourceLocation(MODID, "germanite");
    public static final ResourceLocation REALGAR = new ResourceLocation(MODID, "realgar");
    public static final ResourceLocation STIBNITE = new ResourceLocation(MODID, "stibnite"); //Antimony
    public static final ResourceLocation CALAVERITE = new ResourceLocation(MODID, "calaverite");
    
    //Lanthanides
    public static final ResourceLocation MONAZITE = new ResourceLocation(MODID, "monazite");
    public static final ResourceLocation BASTNAESITE = new ResourceLocation(MODID, "bastnaesite");
    public static final ResourceLocation KOZOITE = new ResourceLocation(MODID, "kozoite");
    public static final ResourceLocation SAMARSKITE = new ResourceLocation(MODID, "samarskite");
    public static final ResourceLocation LOPARITE = new ResourceLocation(MODID, "loparite");
    public static final ResourceLocation GADOLINITE = new ResourceLocation(MODID, "gadolinite");
    public static final ResourceLocation EUXENITE = new ResourceLocation(MODID, "euxenite");
    public static final ResourceLocation FERGUSONITE = new ResourceLocation(MODID, "fergusonite");
    
    //Actinides
    public static final ResourceLocation THORIANITE = new ResourceLocation(MODID, "thorianite");
    public static final ResourceLocation URANINITE = new ResourceLocation(MODID, "uraninite");
    //public static final ResourceLocation PITCHBLENDE = new ResourceLocation(MODID, "pitchblende"); //Uraninite

    //Fantasy
    public static final ResourceLocation NATIVE_ARDITE = new ResourceLocation(MODID, "native_ardite"); //Ardite
    
    //Metals
    //Alkali metals
	public static final ResourceLocation LITHIUM = new ResourceLocation(MODID, "lithium");
	public static final ResourceLocation NATRIUM = new ResourceLocation(MODID, "natrium");
	public static final ResourceLocation KALIUM = new ResourceLocation(MODID, "kalium");
	public static final ResourceLocation RUBIDIUM = new ResourceLocation(MODID, "rubidium");
	public static final ResourceLocation CAESIUM = new ResourceLocation(MODID, "caesium");
	public static final ResourceLocation FRANCIUM = new ResourceLocation(MODID, "francium");
    
    //Alkaline earth metals
	public static final ResourceLocation BERYLLIUM = new ResourceLocation(MODID, "beryllium");
	public static final ResourceLocation MAGNESIUM = new ResourceLocation(MODID, "magnesium");
	public static final ResourceLocation CALCIUM = new ResourceLocation(MODID, "calcium");
	public static final ResourceLocation STRONTIUM = new ResourceLocation(MODID, "strontium");
	public static final ResourceLocation BARIUM = new ResourceLocation(MODID, "barium");
	public static final ResourceLocation RADIUM = new ResourceLocation(MODID, "radium");
    
    //Transition metals
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
    
    //Post-transition metals
    public static final ResourceLocation ALUMINIUM = new ResourceLocation(MODID, "aluminium");
	public static final ResourceLocation GALLIUM = new ResourceLocation(MODID, "gallium");
	public static final ResourceLocation CADMIUM = new ResourceLocation(MODID, "cadmium");
	public static final ResourceLocation INDIUM = new ResourceLocation(MODID, "indium");
	public static final ResourceLocation MERCURY = new ResourceLocation(MODID, "mercury");
	public static final ResourceLocation THALLIUM = new ResourceLocation(MODID, "thallium");
    //public static final ResourceLocation LEAD = new ResourceLocation(MODID, "lead");
	public static final ResourceLocation POLONIUM = new ResourceLocation(MODID, "polonium");
    
    //Metalloid
	public static final ResourceLocation BORON = new ResourceLocation(MODID, "boron");
	public static final ResourceLocation SILICIUM = new ResourceLocation(MODID, "silicium");
	public static final ResourceLocation GERMANIUM = new ResourceLocation(MODID, "germanium");
	public static final ResourceLocation ARSENIC = new ResourceLocation(MODID, "arsenic");
    public static final ResourceLocation ANTIMONY = new ResourceLocation(MODID, "antimony");
	public static final ResourceLocation TELLURIUM = new ResourceLocation(MODID, "tellurium");
	public static final ResourceLocation ASTATINE = new ResourceLocation(MODID, "astatine");
    
    //Lanthanides
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
    
    //Actinides
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
    
    //Alloys & Other
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
    public static final ResourceLocation CAST_IRON = new ResourceLocation(MODID, "cast_iron");
    public static final ResourceLocation PEWTER = new ResourceLocation(MODID, "pewter");

    //Fantasy
    public static final ResourceLocation MITHRIL = new ResourceLocation(MODID, "mithril");
    public static final ResourceLocation ARDITE = new ResourceLocation(MODID, "ardite");
    public static final ResourceLocation MANYULLYN = new ResourceLocation(MODID, "manyullyn");

    @SubscribeEvent
    public static void onPreRegisterMetal(TFCRegistryEvent.RegisterPreBlock<Metal> event)
    {
        IForgeRegistry<Metal> r = event.getRegistry();
		//Reactive nonmetals
        //No metal ingots
        
        //Noble gases
        //None
        
        //Alkali metals
        r.register(new Metal(LITHIUM, Metal.Tier.TIER_I, true, 0.25f, 180, 0xFFC0C0C0, null, null));
        r.register(new Metal(NATRIUM, Metal.Tier.TIER_I, true, 0.28f, 98, 0xFFD3D3D3, null, null));
        r.register(new Metal(KALIUM, Metal.Tier.TIER_I, true, 0.30f, 64, 0xFFD3D3D3, null, null));
        r.register(new Metal(RUBIDIUM, Metal.Tier.TIER_I, true, 0.31f, 39, 0xFFD3D3D3, null, null));
        r.register(new Metal(CAESIUM, Metal.Tier.TIER_I, true, 0.32f, 29, 0xFFEEE8AC, null, null));
        r.register(new Metal(FRANCIUM, Metal.Tier.TIER_I, true, 0.34f, 8, 0xFFD3D3D3, null, null));
        
        //Alkaline earth metals
        r.register(new Metal(BERYLLIUM, Metal.Tier.TIER_III, true, 0.16f, 1287, 0xFFA1A2A4, null, null));
        r.register(new Metal(MAGNESIUM, Metal.Tier.TIER_I, true, 0.25f, 650, 0xFFD3D3D3, null, null));
        r.register(new Metal(CALCIUM, Metal.Tier.TIER_I, true, 0.26f, 842, 0xFF8A9597, null, null));
        r.register(new Metal(STRONTIUM, Metal.Tier.TIER_I, true, 0.26f, 777, 0xFFC0C0C0, null, null));
        r.register(new Metal(BARIUM, Metal.Tier.TIER_I, true, 0.28f, 727, 0xFFD3D3D3, null, null));
        r.register(new Metal(RADIUM, Metal.Tier.TIER_I, true, 0.29f, 700, 0xFFD3D3D3, null, null));
        
        //Transition metals
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
        r.register(new Metal(PALLADIUM, Metal.Tier.TIER_III, true, 0.26f, 1555, 0xFFD3D3D3, null, null));
        r.register(new Metal(HAFNIUM, Metal.Tier.TIER_III, true, 0.26f, 2233, 0xFFC0C0C0, null, null));
        r.register(new Metal(TANTALUM, Metal.Tier.TIER_IV, true, 0.26f, 3017, 0xFF808080, null, null));
        r.register(new Metal(TUNGSTEN, Metal.Tier.TIER_VI, true, 0.2f, 3400, 0xFFD3D3D3, ToolMaterialsTFCE.TUNGSTEN, ArmorMaterialsTFCE.TUNGSTEN));
        r.register(new Metal(RHENIUM, Metal.Tier.TIER_V, true, 0.25f, 3186, 0xFFD3D3D3, null, null));
        r.register(new Metal(OSMIUM, Metal.Tier.TIER_VI, true, 0.35f, 3025, 0xFF65D1D4, ToolMaterialsTFCE.OSMIUM, ArmorMaterialsTFCE.OSMIUM));
        r.register(new Metal(IRIDIUM, Metal.Tier.TIER_IV, true, 0.25f, 2446, 0xFFD3D3D3, null, null));
        r.register(new Metal(RUTHERFORDIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(DUBNIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(SEABORGIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(BOHRIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(HASSIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(COPERNICIUM, Metal.Tier.TIER_I, true, 0.26f, 10, 0xFFD4CDC2, null, null));
        
        //Post-transition metals
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
        
        //Metalloid
        r.register(new Metal(BORON, Metal.Tier.TIER_VI, true, 0.11f, 2076, 0xFF4E4E4E, null, null));
        r.register(new Metal(SILICIUM, Metal.Tier.TIER_IV, true, 0.20f, 1414, 0xFFC0C0C0, null, null));
        r.register(new Metal(GERMANIUM, Metal.Tier.TIER_IV, true, 0.23f, 938, 0xFFD3D3D3, null, null));
        r.register(new Metal(ARSENIC, Metal.Tier.TIER_II, true, 0.25f, 615, 0xFF808080, null, null));
        r.register(new Metal(ANTIMONY, Metal.Tier.TIER_I, true, 0.25f, 630, 0xFF808080, null, null));
        r.register(new Metal(TELLURIUM, Metal.Tier.TIER_II, true, 0.26f, 450, 0xFFC0C0C0, null, null));
        r.register(new Metal(ASTATINE, Metal.Tier.TIER_II, true, 0.11f, 150, 0xFFC0C0C0, null, null));
        
        //Lanthanides
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
        
        //Actinides
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
        
        //Gases
        //None
        
        //Alloys & Other
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
        r.register(new Metal(CAST_IRON, Metal.Tier.TIER_II, true, 0.35f, 1535, 0xFF5F5F5F, null, null));
        r.register(new Metal(PEWTER, Metal.Tier.TIER_II, true, 0.35f, 230, 0xFF51707B, null, null));
        
        //Fantasy
        r.register(new Metal(MITHRIL, Metal.Tier.TIER_II, true, 0.35f, 940, 0xFF8ADAF6, ToolMaterialsTFCE.MITHRIL, ArmorMaterialsTFCE.MITHRIL));
        r.register(new Metal(ARDITE, Metal.Tier.TIER_IV, true, 0.35f, 960, 0xFF40444A, null, null));
        r.register(new Metal(MANYULLYN, Metal.Tier.TIER_VI, true, 0.3f, 1550, 0xFF40444A, ToolMaterialsTFCE.MANYULLYN, ArmorMaterialsTFCE.MANYULLYN));
    }

    @SubscribeEvent
    public static void onPreRegisterOre(TFCRegistryEvent.RegisterPreBlock<Ore> event)
    {
        IForgeRegistry<Ore> r = event.getRegistry();

        //Reactive nonmetals
        r.register(new Ore(ANTHRACITE)); //Coal
        
        //Reactive nonmetals
        r.register(new Ore(FLUORITE));
        r.register(new Ore(PHOSPHORITE));
        r.register(new Ore(SELENIDE));
        r.register(new Ore(IODATE));
        
        //Noble gases
        //None
        
        //Alkali metals
        r.register(new Ore(SPODUMENE, LITHIUM, true)); //Lithium
        r.register(new Ore(HALITE, NATRIUM, true)); //Natrium/Sodium
        r.register(new Ore(CARNALLITE, KALIUM, true)); //Salt //Sylvite //Potash
        r.register(new Ore(LEPIDOLITE, RUBIDIUM, true)); //Rubidium
        r.register(new Ore(POLLUCITE, CAESIUM, false)); //Caesium
        //r.register(new Ore(URANINITE, FRANCIUM, false)); //Francium
        
        //Alkaline earth metals
        r.register(new Ore(BERTRANDITE, BERYLLIUM, true)); //Beryllium
        r.register(new Ore(MAGNESITE, MAGNESIUM, true)); //Magnesium
        //r.register(new Ore(CALCIUM, CALCIUM, true)); //Calcium //Gypsum, bones, shellfish and all that stuff
        r.register(new Ore(CELESTINE, STRONTIUM, true)); //Strontium
        r.register(new Ore(BARYTE, BARIUM, true)); //Barium
        //r.register(new Ore(URANINITE, RADIUM, true)); //Radium
        
        //Transition metals
        r.register(new Ore(THORTVEITITE, SCANDIUM, true)); //Scandium
        r.register(new Ore(RUTILE, TITANIUM, false)); //Titanium
        r.register(new Ore(VANADINITE, VANADIUM, false)); //Vanadium
        r.register(new Ore(CHROMITE, CHROMIUM, false)); //Chrome
        r.register(new Ore(PYROLUSITE, MANGANESE, true)); //Manganese
        r.register(new Ore(COBALTITE, COBALT, false)); //Cobalt
        r.register(new Ore(XENOTIME, YTTRIUM, true)); //Yttrium
        r.register(new Ore(ZIRCON, ZIRCONIUM, false)); //Zirconium
        r.register(new Ore(COLUMBITE, NIOBIUM, false)); //Niobium
        r.register(new Ore(MOLYBDENITE, MOLYBDENUM, false)); //Molybdenum
        //r.register(new Ore(URANINITE, TECHNETIUM, false)); //Technetium //Found in uranium/pitchblende ore
        r.register(new Ore(PENTLANDITE, RUTHENIUM, false)); //Ruthenium
        r.register(new Ore(NATIVE_RHODIUM, RHODIUM, false)); //Rhodium //Barely even occurs in the Earth's crust //Usually found mixed with other metals
        r.register(new Ore(COOPERITE, PALLADIUM, true)); //Palladium
        r.register(new Ore(HAFNON, HAFNIUM, false)); //Hafnium
        r.register(new Ore(TANTALITE, TANTALUM, false)); //Tantalum
        r.register(new Ore(WOLFRAMITE, TUNGSTEN, false)); //Wolfram
        r.register(new Ore(RHENIITE, RHENIUM, false)); //Rhenium
        r.register(new Ore(OSMIRIDIUM, OSMIUM, true)); //Osmium
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
        
        //Post-transition metals
        r.register(new Ore(BAUXITE, ALUMINIUM, false)); //Aluminium
        r.register(new Ore(GALLITE, GALLIUM, true)); //Gallium //Also a by-product from Bauxite or Sphalerite
        r.register(new Ore(GREENOCKITE, CADMIUM, true)); //Cadmium
        r.register(new Ore(ROQUESITE, INDIUM, true)); //Indium //Or found as trace constrituent in Sphalerite
        r.register(new Ore(LIVINGSTONITE, MERCURY, false)); //Mercury
        r.register(new Ore(LORANDITE, THALLIUM, true)); //Thallium //LORÁNDITE
        /*if (ModConfig.ORE_ADDITIONS.galena)
        {
            r.register(new Ore(GALENA, LEAD, true));
        }*/
        //r.register(new Ore(URANINITE, POLONIUM, true)); //Polonium
        //r.register(new Ore(URANINITE, COPERNICIUM, false)); //Copernicium
        
        //Metalloid
        r.register(new Ore(ULEXITE, BORON, false)); //Boron
        r.register(new Ore(SILICA, SILICIUM, true)); //Silicium //Just process sand
        r.register(new Ore(GERMANITE, GERMANIUM, true)); //Germanium
        r.register(new Ore(REALGAR, ARSENIC, true)); //Arsenic
        r.register(new Ore(STIBNITE, ANTIMONY, true)); //Antimony
        r.register(new Ore(CALAVERITE, TELLURIUM, true)); //Tellurium
        //r.register(new Ore(URANINITE, ASTATINE, false)); //Astatine
        
        //Lanthanides
        r.register(new Ore(MONAZITE, LANTHANUM, true)); //Lanthanum
        r.register(new Ore(BASTNAESITE, CERIUM, true)); //Cerium //BASTNÄSITE
        //r.register(new Ore(MONAZITE, PRASEODYMIUM, true)); //Occurs in a variant of monazite
        r.register(new Ore(KOZOITE, NEODYMIUM, true)); //Neodymium
        //r.register(new Ore(URANINITE, PROMETHIUM, true)); //Promethium
        r.register(new Ore(SAMARSKITE, SAMARIUM, true)); //Samarium
        r.register(new Ore(LOPARITE, EUROPIUM, true)); //Europium
        r.register(new Ore(GADOLINITE, GADOLINIUM, true)); //Gadolinium
        r.register(new Ore(EUXENITE, TERBIUM, true)); //Terbium
        r.register(new Ore(FERGUSONITE, DYSPROSIUM, true)); //Dysprosium
        //r.register(new Ore(MONAZITE, HOLMIUM, true)); //Holmium //Also found in monazite
        //r.register(new Ore(MONAZITE, ERBIUM, false)); //Erbium //Also found in monazite
        //r.register(new Ore(MONAZITE, THULIUM, true)); //Thulium //Also found in monazite, xenotime and euxenite
        //r.register(new Ore(MONAZITE, YTTERBIUM, true)); //Ytterbium //Also found in monazite, xenotime and euxenite
        //r.register(new Ore(MONAZITE, LUTETIUM, false)); //Lutetium //Also found in monazite
        
        //Actinides
        //r.register(new Ore(URANINITE, ACTINIUM, true)); //Actinium
        r.register(new Ore(THORIANITE, THORIUM, false)); //Thorium
        //r.register(new Ore(URANINITE, PROTACTINIUM, false)); //Protactinium
        r.register(new Ore(URANINITE, URANIUM, true)); //Uranium
        /*if (ModConfig.ORE_ADDITIONS.pitchblende) //todo only if base TFC removes it
        {
            r.register(new Ore(PITCHBLENDE));
        }
        */
        //r.register(new Ore(URANINITE, NEPTUNIUM, true)); //Neptunium
        //r.register(new Ore(URANINITE, PLUTONIUM, true)); //Plutonium
        //r.register(new Ore(URANINITE, AMERICIUM, true)); //Americum
        //r.register(new Ore(URANINITE, CURIUM, true)); //Curium
        //r.register(new Ore(URANINITE, BERKELIUM, true)); //Berkelium
        //r.register(new Ore(URANINITE, CALIFORNIUM, true)); //Californium
        //r.register(new Ore(URANINITE, EINSTEINIUM, true)); //Einsteinium
        //r.register(new Ore(URANINITE, FERMIUM, true)); //Fermium
        //r.register(new Ore(URANINITE, MENDELEVIUM, true)); //Mendelevium
        //r.register(new Ore(URANINITE, NOBELIUM, true)); //Nobelium
        //r.register(new Ore(URANINITE, LAWRENCIUM, false)); //Lawrencium
        
        //Gases
        //r.register(new Ore(URANINITE, MEITNERIUM, false)); //Meitner
        //r.register(new Ore(URANINITE, DARMSTADTIUM, false)); //Darmstadtium
        //r.register(new Ore(URANINITE, ROENTGENIUM, false)); //Roentgenium
        //r.register(new Ore(URANINITE, NIHONIUM, true)); //Nihonium
        //r.register(new Ore(URANINITE, FLEROVIUM, false)); //Flerovium
        //r.register(new Ore(URANINITE, MOSCOVIUM, true)); //Moscovium
        //r.register(new Ore(URANINITE, LIVERMORIUM, true)); //Livermorium
        //r.register(new Ore(URANINITE, TENNESSINE, true)); //Tennessine
        //r.register(new Ore(URANINITE, OGANESSON, true)); //Oganesson
        
        //Fantasy
        r.register(new Ore(NATIVE_ARDITE, ARDITE, true));
        
    }

    @SubscribeEvent
    public static void onRegisterAlloyRecipe(RegistryEvent.Register<AlloyRecipe> event)
    {
        IForgeRegistry<AlloyRecipe> r = event.getRegistry();
        r.register(new AlloyRecipe.Builder(ALUMINIUM_BRASS).add(ALUMINIUM, 0.65, 0.85).add(COPPER, 0.15, 0.35).build());
        r.register(new AlloyRecipe.Builder(CONSTANTAN).add(COPPER, 0.4, 0.6).add(NICKEL, 0.4, 0.6).build());
        r.register(new AlloyRecipe.Builder(ELECTRUM).add(GOLD, 0.4, 0.6).add(SILVER, 0.4, 0.6).build());
        r.register(new AlloyRecipe.Builder(INVAR).add(WROUGHT_IRON, 0.6, 0.7).add(NICKEL, 0.3, 0.4).build());
        r.register(new AlloyRecipe.Builder(MANYULLYN).add(COBALT, 0.4, 0.6).add(ARDITE, 0.4, 0.6).build());
        r.register(new AlloyRecipe.Builder(MITHRIL).add(COPPER, 0.88, 0.92).add(ANTIMONY, 0.08, 0.12).build());
        r.register(new AlloyRecipe.Builder(ORICHALCUM).add(COPPER, 0.75, 0.8).add(ZINC, 0.20, 0.25).build());
        r.register(new AlloyRecipe.Builder(NICKEL_SILVER).add(COPPER, 0.50, 0.65).add(ZINC, 0.1, 0.3).add(NICKEL, 0.1, 0.3).build());
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
    }
}
