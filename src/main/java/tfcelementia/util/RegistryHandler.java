package tfcelementia.util;

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

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.types.DefaultMetals.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Mod.EventBusSubscriber(modid = TFCElementia.MODID)
public final class RegistryHandler
{
	//Ores
    //Reactive nonmetals
    public static final ResourceLocation FLUORITE = new ResourceLocation(MOD_ID, "fluorite");
    public static final ResourceLocation PHOSPHORITE = new ResourceLocation(MOD_ID, "phosphorite");
    public static final ResourceLocation SELENIDE = new ResourceLocation(MOD_ID, "selenide");
    public static final ResourceLocation IODATE = new ResourceLocation(MOD_ID, "iodate");
    
    //Noble gases
    //None
    
    //Alkali metals
    public static final ResourceLocation SPODUMENE = new ResourceLocation(MOD_ID, "spodumene");
    public static final ResourceLocation HALITE = new ResourceLocation(MOD_ID, "halite");
    public static final ResourceLocation CARNALLITE = new ResourceLocation(MOD_ID, "carnallite ");
    public static final ResourceLocation LEPIDOLITE = new ResourceLocation(MOD_ID, "lepidolite");
    public static final ResourceLocation POLLUCITE = new ResourceLocation(MOD_ID, "pollucite");
    
    //Alkaline earth metals
    public static final ResourceLocation BERTRANDITE = new ResourceLocation(MOD_ID, "bertrandite");
    public static final ResourceLocation MAGNESITE = new ResourceLocation(MOD_ID, "magnesite");
    public static final ResourceLocation CELESTINE = new ResourceLocation(MOD_ID, "celestine");
    public static final ResourceLocation BARYTE = new ResourceLocation(MOD_ID, "baryte");
    
    //Transition metals
    public static final ResourceLocation THORTVEITITE = new ResourceLocation(MOD_ID, "thortveitite");
    public static final ResourceLocation RUTILE = new ResourceLocation(MOD_ID, "rutile"); //Titanium
    public static final ResourceLocation VANADINITE = new ResourceLocation(MOD_ID, "vanadinite");
    public static final ResourceLocation CHROMITE = new ResourceLocation(MOD_ID, "chromite");
    public static final ResourceLocation PYROLUSITE = new ResourceLocation(MOD_ID, "pyrolusite");
    public static final ResourceLocation COBALTITE = new ResourceLocation(MOD_ID, "cobaltite"); //Cobalt
    public static final ResourceLocation XENOTIME = new ResourceLocation(MOD_ID, "xenotime");
    public static final ResourceLocation ZIRCON = new ResourceLocation(MOD_ID, "zircon");
    public static final ResourceLocation COLUMBITE = new ResourceLocation(MOD_ID, "columbite");
    public static final ResourceLocation MOLYBDENITE = new ResourceLocation(MOD_ID, "molybdenite");
    public static final ResourceLocation PENTLANDITE = new ResourceLocation(MOD_ID, "pentlandite");
    public static final ResourceLocation NATIVE_RHODIUM = new ResourceLocation(MOD_ID, "native_rhodium");
    public static final ResourceLocation COOPERITE = new ResourceLocation(MOD_ID, "cooperite");
    public static final ResourceLocation HAFNON = new ResourceLocation(MOD_ID, "hafnon");
    public static final ResourceLocation TANTALITE = new ResourceLocation(MOD_ID, "tantalite");
    public static final ResourceLocation WOLFRAMITE = new ResourceLocation(MOD_ID, "wolframite"); //Tungsten
    public static final ResourceLocation RHENIITE = new ResourceLocation(MOD_ID, "rheniite");
    public static final ResourceLocation OSMIRIDIUM = new ResourceLocation(MOD_ID, "osmiridium");
    public static final ResourceLocation IRIDOSMIUM = new ResourceLocation(MOD_ID, "iridosmium");
    //public static final ResourceLocation NATIVE_PLATINUM = new ResourceLocation(MOD_ID, "native_platinum"); //Platinum
    
    //Post-transition metals
    public static final ResourceLocation BAUXITE = new ResourceLocation(MOD_ID, "bauxite"); //Aluminium
    public static final ResourceLocation GALLITE = new ResourceLocation(MOD_ID, "gallite");
    public static final ResourceLocation GREENOCKITE = new ResourceLocation(MOD_ID, "greenockite");
    public static final ResourceLocation ROQUESITE = new ResourceLocation(MOD_ID, "roquesite");
    public static final ResourceLocation LIVINGSTONITE = new ResourceLocation(MOD_ID, "livingstonite");
    public static final ResourceLocation LORANDITE = new ResourceLocation(MOD_ID, "lorandite");
    //public static final ResourceLocation GALENA = new ResourceLocation(MOD_ID, "galena"); //Lead
    
    //Metalloid
    public static final ResourceLocation ULEXITE = new ResourceLocation(MOD_ID, "boron");
    public static final ResourceLocation SILICA = new ResourceLocation(MOD_ID, "silica");
    public static final ResourceLocation GERMANITE = new ResourceLocation(MOD_ID, "germanite");
    public static final ResourceLocation REALGAR = new ResourceLocation(MOD_ID, "realgar");
    public static final ResourceLocation STIBNITE = new ResourceLocation(MOD_ID, "stibnite"); //Antimony
    public static final ResourceLocation CALAVERITE = new ResourceLocation(MOD_ID, "calaverite");
    
    //Lanthanides
    public static final ResourceLocation MONAZITE = new ResourceLocation(MOD_ID, "monazite");
    public static final ResourceLocation BASTNAESITE = new ResourceLocation(MOD_ID, "bastnaesite");
    public static final ResourceLocation KOZOITE = new ResourceLocation(MOD_ID, "kozoite");
    public static final ResourceLocation SAMARSKITE = new ResourceLocation(MOD_ID, "samarskite");
    public static final ResourceLocation LOPARITE = new ResourceLocation(MOD_ID, "loparite");
    public static final ResourceLocation GADOLINITE = new ResourceLocation(MOD_ID, "gadolinite");
    public static final ResourceLocation EUXENITE = new ResourceLocation(MOD_ID, "euxenite");
    public static final ResourceLocation FERGUSONITE = new ResourceLocation(MOD_ID, "fergusonite");
    
    //Actinides
    public static final ResourceLocation THORIANITE = new ResourceLocation(MOD_ID, "thorianite");
    public static final ResourceLocation URANINITE = new ResourceLocation(MOD_ID, "uraninite");
    //public static final ResourceLocation PITCHBLENDE = new ResourceLocation(MOD_ID, "pitchblende"); //Uraninite
    
    
    //Metals
    //Alkali metals
	public static final ResourceLocation LITHIUM = new ResourceLocation(MOD_ID, "lithium");
	public static final ResourceLocation NATRIUM = new ResourceLocation(MOD_ID, "natrium");
	public static final ResourceLocation KALIUM = new ResourceLocation(MOD_ID, "kalium");
	public static final ResourceLocation RUBIDIUM = new ResourceLocation(MOD_ID, "rubidium");
	public static final ResourceLocation CAESIUM = new ResourceLocation(MOD_ID, "caesium");
	public static final ResourceLocation FRANCIUM = new ResourceLocation(MOD_ID, "francium");
    
    //Alkaline earth metals
	public static final ResourceLocation BERYLLIUM = new ResourceLocation(MOD_ID, "beryllium");
	public static final ResourceLocation MAGNESIUM = new ResourceLocation(MOD_ID, "magnesium");
	public static final ResourceLocation CALCIUM = new ResourceLocation(MOD_ID, "calcium");
	public static final ResourceLocation STRONTIUM = new ResourceLocation(MOD_ID, "strontium");
	public static final ResourceLocation BARIUM = new ResourceLocation(MOD_ID, "barium");
	public static final ResourceLocation RADIUM = new ResourceLocation(MOD_ID, "radium");
    
    //Transition metals
	public static final ResourceLocation SCANDIUM = new ResourceLocation(MOD_ID, "scandium");
    public static final ResourceLocation TITANIUM = new ResourceLocation(MOD_ID, "titanium");
	public static final ResourceLocation VANADIUM = new ResourceLocation(MOD_ID, "vanadium");
	public static final ResourceLocation CHROMIUM = new ResourceLocation(MOD_ID, "chromium");
	public static final ResourceLocation MANGANESE = new ResourceLocation(MOD_ID, "manganese");
    public static final ResourceLocation COBALT = new ResourceLocation(MOD_ID, "cobalt");
	public static final ResourceLocation YTTRIUM = new ResourceLocation(MOD_ID, "ytterium");
	public static final ResourceLocation ZIRCONIUM = new ResourceLocation(MOD_ID, "zirconium");
	public static final ResourceLocation NIOBIUM = new ResourceLocation(MOD_ID, "niobium");
	public static final ResourceLocation MOLYBDENUM = new ResourceLocation(MOD_ID, "molybdenum");
	public static final ResourceLocation TECHNETIUM = new ResourceLocation(MOD_ID, "technetium");
	public static final ResourceLocation RUTHENIUM = new ResourceLocation(MOD_ID, "ruthenium");
	public static final ResourceLocation RHODIUM = new ResourceLocation(MOD_ID, "rhodium");
	public static final ResourceLocation PALLADIUM = new ResourceLocation(MOD_ID, "palladium");
	public static final ResourceLocation HAFNIUM = new ResourceLocation(MOD_ID, "hafnium");
	public static final ResourceLocation TANTALUM = new ResourceLocation(MOD_ID, "tantalum");
    public static final ResourceLocation TUNGSTEN = new ResourceLocation(MOD_ID, "tungsten");
	public static final ResourceLocation RHENIUM = new ResourceLocation(MOD_ID, "rhenium");
    public static final ResourceLocation OSMIUM = new ResourceLocation(MOD_ID, "osmium");
	public static final ResourceLocation IRIDIUM = new ResourceLocation(MOD_ID, "iridium");
    //public static final ResourceLocation PLATINUM = new ResourceLocation(MOD_ID, "platinum");
	public static final ResourceLocation RUTHERFORDIUM = new ResourceLocation(MOD_ID, "rutherfordium");
	public static final ResourceLocation DUBNIUM = new ResourceLocation(MOD_ID, "dubnium");
	public static final ResourceLocation SEABORGIUM = new ResourceLocation(MOD_ID, "seaborgium");
	public static final ResourceLocation BOHRIUM = new ResourceLocation(MOD_ID, "bohrium");
	public static final ResourceLocation HASSIUM = new ResourceLocation(MOD_ID, "hassium");
	public static final ResourceLocation COPERNICIUM = new ResourceLocation(MOD_ID, "copernicium");
    
    //Post-transition metals
    public static final ResourceLocation ALUMINIUM = new ResourceLocation(MOD_ID, "aluminium");
	public static final ResourceLocation GALLIUM = new ResourceLocation(MOD_ID, "gallium");
	public static final ResourceLocation CADMIUM = new ResourceLocation(MOD_ID, "cadmium");
	public static final ResourceLocation INDIUM = new ResourceLocation(MOD_ID, "indium");
	public static final ResourceLocation MERCURY = new ResourceLocation(MOD_ID, "mercury");
	public static final ResourceLocation THALLIUM = new ResourceLocation(MOD_ID, "thallium");
    //public static final ResourceLocation LEAD = new ResourceLocation(MOD_ID, "lead");
	public static final ResourceLocation POLONIUM = new ResourceLocation(MOD_ID, "polonium");
    
    //Metalloid
	public static final ResourceLocation BORON = new ResourceLocation(MOD_ID, "boron");
	public static final ResourceLocation SILICIUM = new ResourceLocation(MOD_ID, "silicium");
	public static final ResourceLocation GERMANIUM = new ResourceLocation(MOD_ID, "germanium");
	public static final ResourceLocation ARSENIC = new ResourceLocation(MOD_ID, "arsenic");
    public static final ResourceLocation ANTIMONY = new ResourceLocation(MOD_ID, "antimony");
	public static final ResourceLocation TELLURIUM = new ResourceLocation(MOD_ID, "tellurium");
	public static final ResourceLocation ASTATINE = new ResourceLocation(MOD_ID, "astatine");
    
    //Lanthanides
	public static final ResourceLocation LANTHANUM = new ResourceLocation(MOD_ID, "lanthanum");
	public static final ResourceLocation CERIUM = new ResourceLocation(MOD_ID, "cerium");
	public static final ResourceLocation PRASEODYMIUM = new ResourceLocation(MOD_ID, "praseodymium");
	public static final ResourceLocation NEODYMIUM = new ResourceLocation(MOD_ID, "neodymium");
	public static final ResourceLocation PROMETHIUM = new ResourceLocation(MOD_ID, "promethium");
	public static final ResourceLocation SAMARIUM = new ResourceLocation(MOD_ID, "samarium");
	public static final ResourceLocation EUROPIUM = new ResourceLocation(MOD_ID, "europium");
	public static final ResourceLocation GADOLINIUM = new ResourceLocation(MOD_ID, "gadolinium");
	public static final ResourceLocation TERBIUM = new ResourceLocation(MOD_ID, "terbium");
	public static final ResourceLocation DYSPROSIUM = new ResourceLocation(MOD_ID, "dysprosium");
	public static final ResourceLocation HOLMIUM = new ResourceLocation(MOD_ID, "holmium");
	public static final ResourceLocation ERBIUM = new ResourceLocation(MOD_ID, "erbium");
	public static final ResourceLocation THULIUM = new ResourceLocation(MOD_ID, "thulium");
	public static final ResourceLocation YTTERBIUM = new ResourceLocation(MOD_ID, "ytterbium");
	public static final ResourceLocation LUTETIUM = new ResourceLocation(MOD_ID, "lutetium");
    
    //Actinides
	public static final ResourceLocation ACTINIUM = new ResourceLocation(MOD_ID, "actinium");
	public static final ResourceLocation THORIUM = new ResourceLocation(MOD_ID, "thorium");
	public static final ResourceLocation PROTACTINIUM = new ResourceLocation(MOD_ID, "protactinium");
	public static final ResourceLocation URANIUM = new ResourceLocation(MOD_ID, "uranium");
	public static final ResourceLocation NEPTUNIUM = new ResourceLocation(MOD_ID, "neptunium");
	public static final ResourceLocation PLUTONIUM = new ResourceLocation(MOD_ID, "plutonium");
	public static final ResourceLocation AMERICIUM = new ResourceLocation(MOD_ID, "americium");
	public static final ResourceLocation CURIUM = new ResourceLocation(MOD_ID, "curium");
	public static final ResourceLocation BERKELIUM = new ResourceLocation(MOD_ID, "berkelium");
	public static final ResourceLocation CALIFORNIUM = new ResourceLocation(MOD_ID, "californium");
	public static final ResourceLocation EINSTEINIUM = new ResourceLocation(MOD_ID, "einsteinium");
	public static final ResourceLocation FERMIUM = new ResourceLocation(MOD_ID, "fermium");
	public static final ResourceLocation MENDELEVIUM = new ResourceLocation(MOD_ID, "mendelevium");
	public static final ResourceLocation NOBELIUM = new ResourceLocation(MOD_ID, "nobelium");
	public static final ResourceLocation LAWRENCIUM = new ResourceLocation(MOD_ID, "lawrencium");
    
    //Alloys
    public static final ResourceLocation ALUMINIUM_BRASS = new ResourceLocation(MOD_ID, "aluminium_brass");
    public static final ResourceLocation CONSTANTAN = new ResourceLocation(MOD_ID, "constantan");
    public static final ResourceLocation ELECTRUM = new ResourceLocation(MOD_ID, "electrum");
    public static final ResourceLocation INVAR = new ResourceLocation(MOD_ID, "invar");
    public static final ResourceLocation MANYULLYN = new ResourceLocation(MOD_ID, "manyullyn");
    public static final ResourceLocation MITHRIL = new ResourceLocation(MOD_ID, "mithril");
    public static final ResourceLocation NICKEL_SILVER = new ResourceLocation(MOD_ID, "nickel_silver"); // Copper + zinc + nickel
    public static final ResourceLocation ORICHALCUM  = new ResourceLocation(MOD_ID, "orichalcum");
    public static final ResourceLocation RED_ALLOY = new ResourceLocation(MOD_ID, "red_alloy"); // Copper + redstone (although not obtainable with just TFC + metallum
    public static final ResourceLocation TUNGSTEN_STEEL = new ResourceLocation(MOD_ID, "tungsten_steel");

    //Fantasy
    public static final ResourceLocation ARDITE = new ResourceLocation(MOD_ID, "ardite");
    public static final ResourceLocation NATIVE_ARDITE = new ResourceLocation(MOD_ID, "native_ardite"); //Ardite

    @SubscribeEvent
    public static void onPreRegisterMetal(TFCRegistryEvent.RegisterPreBlock<Metal> event)
    {
        IForgeRegistry<Metal> r = event.getRegistry();
		//Reactive nonmetals
        //No metal ingots
        
        //Noble gases
        //None
        
        //Alkali metals
        r.register(new Metal(LITHIUM, Metal.Tier.TIER_I, true, 0.25f, 180, 0xFFC9CECE, null, null));
        r.register(new Metal(NATRIUM, Metal.Tier.TIER_I, true, 0.28f, 98, 0xFF575755, null, null));
        r.register(new Metal(KALIUM, Metal.Tier.TIER_I, true, 0.30f, 64, 0xFFB8C0BC, null, null));
        r.register(new Metal(RUBIDIUM, Metal.Tier.TIER_I, true, 0.31f, 39, 0xFF908C7C, null, null));
        r.register(new Metal(CAESIUM, Metal.Tier.TIER_I, true, 0.32f, 29, 0xFFC1B484, null, null));
        r.register(new Metal(FRANCIUM, Metal.Tier.TIER_I, true, 0.34f, 8, 0xFF908C7C, null, null));
        
        //Alkaline earth metals
        r.register(new Metal(BERYLLIUM, Metal.Tier.TIER_III, true, 0.16f, 1287, 0xFF5C5B5D, null, null));
        r.register(new Metal(MAGNESIUM, Metal.Tier.TIER_I, true, 0.25f, 650, 0xFFB0AEA2, null, null));
        r.register(new Metal(CALCIUM, Metal.Tier.TIER_I, true, 0.26f, 842, 0xFF8E97A3, null, null));
        r.register(new Metal(STRONTIUM, Metal.Tier.TIER_I, true, 0.26f, 777, 0xFF9C8E74, null, null));
        r.register(new Metal(BARIUM, Metal.Tier.TIER_I, true, 0.28f, 727, 0xFF505156, null, null));
        r.register(new Metal(RADIUM, Metal.Tier.TIER_I, true, 0.29f, 700, 0xFF827562, null, null));
        
        //Transition metals
        r.register(new Metal(SCANDIUM, Metal.Tier.TIER_III, true, 0.26f, 1541, 0xFFDCD7C1, null, null));
        r.register(new Metal(TITANIUM, Metal.Tier.TIER_VI, true, 0.3f, 1700, 0xFFC2C4CC, ToolMaterialsTFCE.TITANIUM, ArmorMaterialsTFCE.TITANIUM));
        r.register(new Metal(VANADIUM, Metal.Tier.TIER_IV, true, 0.25f, 1910, 0xFF6F6D76, null, null));
        r.register(new Metal(CHROMIUM, Metal.Tier.TIER_V, true, 0.23f, 1907, 0xFFBCBCBB, null, null));
        r.register(new Metal(MANGANESE, Metal.Tier.TIER_IV, true, 0.26f, 1246, 0xFF97968E, null, null));
        r.register(new Metal(COBALT, Metal.Tier.TIER_VI, true, 0.3f, 1495, 0xFF6CA6E5, ToolMaterialsTFCE.COBALT, ArmorMaterialsTFCE.COBALT));
        r.register(new Metal(YTTRIUM, Metal.Tier.TIER_I, true, 0.27f, 1526, 0xFFB8B6B3, null, null));
        r.register(new Metal(ZIRCONIUM, Metal.Tier.TIER_III, true, 0.25f, 1855, 0xFF7E756B, null, null));
        r.register(new Metal(NIOBIUM, Metal.Tier.TIER_IV, true, 0.25f, 2477, 0xFF737270, null, null));
        r.register(new Metal(MOLYBDENUM, Metal.Tier.TIER_III, true, 0.24f, 2623, 0xFF7A7975, null, null));
        r.register(new Metal(TECHNETIUM, Metal.Tier.TIER_III, true, 0.24f, 2157, 0xFF5C524B, null, null));
        r.register(new Metal(RUTHENIUM, Metal.Tier.TIER_IV, true, 0.24f, 2334, 0xFFA4A3A6, null, null));
        r.register(new Metal(RHODIUM, Metal.Tier.TIER_IV, true, 0.25f, 1964, 0xFF9A8E8C, null, null));
        r.register(new Metal(PALLADIUM, Metal.Tier.TIER_III, true, 0.26f, 1555, 0xFFA29D9B, null, null));
        r.register(new Metal(HAFNIUM, Metal.Tier.TIER_III, true, 0.26f, 2233, 0xFF838D81, null, null));
        r.register(new Metal(TANTALUM, Metal.Tier.TIER_IV, true, 0.26f, 3017, 0xFF9D9C9F, null, null));
        r.register(new Metal(TUNGSTEN, Metal.Tier.TIER_VI, true, 0.2f, 3400, 0xFF40444A, ToolMaterialsTFCE.TUNGSTEN, ArmorMaterialsTFCE.TUNGSTEN));
        r.register(new Metal(RHENIUM, Metal.Tier.TIER_V, true, 0.25f, 3186, 0xFF787979, null, null));
        r.register(new Metal(OSMIUM, Metal.Tier.TIER_VI, true, 0.35f, 3025, 0xFFB8D8DE, ToolMaterialsTFCE.OSMIUM, ArmorMaterialsTFCE.OSMIUM));
        r.register(new Metal(IRIDIUM, Metal.Tier.TIER_IV, true, 0.25f, 2446, 0xFF554F44, null, null));
        r.register(new Metal(RUTHERFORDIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(DUBNIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(SEABORGIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(BOHRIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(HASSIUM, Metal.Tier.TIER_III, true, 0.25f, 2100, 0xFF7E756B, null, null));
        r.register(new Metal(COPERNICIUM, Metal.Tier.TIER_I, true, 0.26f, 10, 0xFFD4CDC2, null, null));
        
        //Post-transition metals
        r.register(new Metal(ALUMINIUM, Metal.Tier.TIER_IV, true, 0.3f, 660, 0xFFD9FBFC, ToolMaterialsTFCE.ALUMINIUM, ArmorMaterialsTFCE.ALUMINIUM));
        r.register(new Metal(GALLIUM, Metal.Tier.TIER_I, true, 0.26f, 30, 0xFF8A9191, null, null));
        r.register(new Metal(CADMIUM, Metal.Tier.TIER_II, true, 0.26f, 321, 0xFF726F69, null, null));
        r.register(new Metal(INDIUM, Metal.Tier.TIER_I, true, 0.27f, 157, 0xFF726E69, null, null));
        r.register(new Metal(MERCURY, Metal.Tier.TIER_I, true, 0.28f, 0, 0xFFD4CDC2, null, null));
        r.register(new Metal(THALLIUM, Metal.Tier.TIER_I, true, 0.26f, 304, 0xFF9A9999, null, null));
        /*if (ModConfig.METAL_ADDITIONS.lead)
        {
            r.register(new Metal(LEAD, Metal.Tier.TIER_I, true, 0.25f, 630, 0xFFE7E7F5, null, null)); // todo change these values accordingly if added
        }*/
        r.register(new Metal(POLONIUM, Metal.Tier.TIER_I, true, 0.26f, 254, 0xFFD4CDC2, null, null));
        
        //Metalloid
        r.register(new Metal(BORON, Metal.Tier.TIER_VI, true, 0.11f, 2076, 0xFF686261, null, null));
        r.register(new Metal(SILICIUM, Metal.Tier.TIER_IV, true, 0.20f, 1414, 0xFF7F8084, null, null));
        r.register(new Metal(GERMANIUM, Metal.Tier.TIER_IV, true, 0.23f, 938, 0xFF7C7F77, null, null));
        r.register(new Metal(ARSENIC, Metal.Tier.TIER_II, true, 0.25f, 615, 0xFF6B6D62, null, null));
        r.register(new Metal(ANTIMONY, Metal.Tier.TIER_I, true, 0.25f, 630, 0xFFE7E7F5, null, null));
        r.register(new Metal(TELLURIUM, Metal.Tier.TIER_II, true, 0.26f, 450, 0xFF808381, null, null));
        r.register(new Metal(ASTATINE, Metal.Tier.TIER_II, true, 0.11f, 150, 0xFF808381, null, null));
        
        //Lanthanides
        r.register(new Metal(LANTHANUM, Metal.Tier.TIER_II, true, 0.27f, 920, 0xFF92887E, null, null));
        r.register(new Metal(CERIUM, Metal.Tier.TIER_II, true, 0.27f, 795, 0xFF989188, null, null));
        r.register(new Metal(PRASEODYMIUM, Metal.Tier.TIER_II, true, 0.27f, 935, 0xFF4E4A46, null, null));
        r.register(new Metal(NEODYMIUM, Metal.Tier.TIER_II, true, 0.27f, 1024, 0xFF70706F, null, null));
        r.register(new Metal(PROMETHIUM, Metal.Tier.TIER_II, true, 0.27f, 1042, 0xFF70706F, null, null));
        r.register(new Metal(SAMARIUM, Metal.Tier.TIER_II, true, 0.30f, 1072, 0xFF8E877B, null, null));
        r.register(new Metal(EUROPIUM, Metal.Tier.TIER_II, true, 0.28f, 826, 0xFFCDC7BC, null, null));
        r.register(new Metal(GADOLINIUM, Metal.Tier.TIER_III, true, 0.37f, 1312, 0xFF828B75, null, null));
        r.register(new Metal(TERBIUM, Metal.Tier.TIER_III, true, 0.29f, 1356, 0xFF90958F, null, null));
        r.register(new Metal(DYSPROSIUM, Metal.Tier.TIER_III, true, 0.28f, 1407, 0xFF8F8F7E, null, null));
        r.register(new Metal(HOLMIUM, Metal.Tier.TIER_III, true, 0.27f, 1461, 0xFF85827C, null, null));
        r.register(new Metal(ERBIUM, Metal.Tier.TIER_III, true, 0.28f, 1529, 0xFF898B83, null, null));
        r.register(new Metal(THULIUM, Metal.Tier.TIER_II, true, 0.27f, 1545, 0xFF85827E, null, null));
        r.register(new Metal(YTTERBIUM, Metal.Tier.TIER_I, true, 0.27f, 824, 0xFF8F8F88, null, null));
        r.register(new Metal(LUTETIUM, Metal.Tier.TIER_III, true, 0.27f, 1652, 0xFF91918A, null, null));
        
        //Actinides
        r.register(new Metal(ACTINIUM, Metal.Tier.TIER_I, true, 0.27f, 1227, 0xFF92887E, null, null));
        r.register(new Metal(THORIUM, Metal.Tier.TIER_I, true, 0.26f, 1750, 0xFF92887E, null, null));
        r.register(new Metal(PROTACTINIUM, Metal.Tier.TIER_I, true, 0.27f, 1568, 0xFF918847, null, null));
        r.register(new Metal(URANIUM, Metal.Tier.TIER_I, true, 0.28f, 1132, 0xFF92887E, null, null));
        r.register(new Metal(NEPTUNIUM, Metal.Tier.TIER_I, true, 0.29f, 640, 0xFF92887E, null, null));
        r.register(new Metal(PLUTONIUM, Metal.Tier.TIER_I, true, 0.36f, 639, 0xFF92887E, null, null));
        r.register(new Metal(AMERICIUM, Metal.Tier.TIER_I, true, 0.63f, 1176, 0xFF92887E, null, null));
        r.register(new Metal(CURIUM, Metal.Tier.TIER_I, true, 0.25f, 1340, 0xFF92887E, null, null));
        r.register(new Metal(BERKELIUM, Metal.Tier.TIER_I, true, 0.25f, 986, 0xFF92887E, null, null));
        r.register(new Metal(CALIFORNIUM, Metal.Tier.TIER_I, true, 0.25f, 900, 0xFF92887E, null, null));
        r.register(new Metal(EINSTEINIUM, Metal.Tier.TIER_I, true, 0.25f, 860, 0xFF92887E, null, null));
        r.register(new Metal(FERMIUM, Metal.Tier.TIER_I, true, 0.25f, 1527, 0xFF92887E, null, null));
        r.register(new Metal(MENDELEVIUM, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFF92887E, null, null));
        r.register(new Metal(NOBELIUM, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFF92887E, null, null));
        r.register(new Metal(LAWRENCIUM, Metal.Tier.TIER_I, true, 0.25f, 827, 0xFF92887E, null, null));
        
        //Gases
        //None
        
        //Alloys
        r.register(new Metal(ALUMINIUM_BRASS, Metal.Tier.TIER_IV, true, 0.3f, 630, 0xFFDCDABE, null, null));
        r.register(new Metal(CONSTANTAN, Metal.Tier.TIER_II, true, 0.5f, 1200, 0xFFD28874, null, null));
        r.register(new Metal(ELECTRUM, Metal.Tier.TIER_II, true, 0.5f, 1200, 0xFFDFB950, null, null));
        r.register(new Metal(INVAR, Metal.Tier.TIER_III, true, 0.35f, 1450, 0xFF40444A, ToolMaterialsTFCE.INVAR, ArmorMaterialsTFCE.INVAR));
        r.register(new Metal(MANYULLYN, Metal.Tier.TIER_VI, true, 0.3f, 1550, 0xFF40444A, ToolMaterialsTFCE.MANYULLYN, ArmorMaterialsTFCE.MANYULLYN));
        r.register(new Metal(MITHRIL, Metal.Tier.TIER_II, true, 0.35f, 940, 0xFF8ADAF6, ToolMaterialsTFCE.MITHRIL, ArmorMaterialsTFCE.MITHRIL));
        r.register(new Metal(NICKEL_SILVER, Metal.Tier.TIER_II, true, 0.35f, 1450, 0xFFA4A4A5, ToolMaterialsTFCE.NICKEL_SILVER, ArmorMaterialsTFCE.NICKEL_SILVER));
        r.register(new Metal(ORICHALCUM, Metal.Tier.TIER_II, true, 0.5f, 1020, 0xFFB39F44, null, null));
        r.register(new Metal(RED_ALLOY, Metal.Tier.TIER_II, true, 0.35f, 1080, 0xFFDA6E6E, null, null));
        r.register(new Metal(TUNGSTEN_STEEL, Metal.Tier.TIER_VI, true, 0.2f, 3695, 0xFF565F6E, ToolMaterialsTFCE.TUNGSTEN_STEEL, ArmorMaterialsTFCE.TUNGSTEN_STEEL));
        
        //Fantasy
        r.register(new Metal(ARDITE, Metal.Tier.TIER_IV, true, 0.3f, 1050, 0xFF40444A, null, null));
    }


    @SubscribeEvent
    public static void onPreRegisterOre(TFCRegistryEvent.RegisterPreBlock<Ore> event)
    {
        IForgeRegistry<Ore> r = event.getRegistry();

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
        r.register(new Ore(CARNALLITE , KALIUM, true)); //Salt //Sylvite //Potash
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
    }
}
