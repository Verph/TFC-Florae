package tfcelementia.types;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.recipes.*;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipeMeasurable;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipeSplitting;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeFluidMixing;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeFoodTraits;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeTemperature;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipeMetalMelting;
import net.dries007.tfc.api.recipes.heat.HeatRecipeSimple;
import net.dries007.tfc.api.recipes.heat.HeatRecipeVessel;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeStone;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.recipes.quern.QuernRecipe;
import net.dries007.tfc.api.recipes.quern.QuernRecipeRandomGem;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Metal.ItemType;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.blocks.BlockDecorativeStone;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.inventory.ingredient.IngredientFluidItem;
import net.dries007.tfc.objects.items.ItemAnimalHide;
import net.dries007.tfc.objects.items.ItemGem;
import net.dries007.tfc.objects.items.ItemMisc;
import net.dries007.tfc.objects.items.ItemPowder;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.objects.items.ceramics.ItemUnfiredMold;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.objects.items.metal.ItemMetal;
import net.dries007.tfc.objects.items.metal.ItemMetalArmor;
import net.dries007.tfc.objects.items.metal.ItemOreTFC;
import net.dries007.tfc.objects.items.metal.ItemSmallOre;
import net.dries007.tfc.objects.items.rock.ItemRockToolHead;
import net.dries007.tfc.objects.recipes.ShapelessDamageRecipe;
//import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.agriculture.Food;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.fuel.FuelManager;
import net.dries007.tfc.util.skills.SmithingSkill;
import net.dries007.tfc.util.skills.SmithingSkill.Type;
import tfcelementia.TFCElementia;
import tfcelementia.objects.GemTFCE;
import tfcelementia.objects.PowderGemTFCE;
import tfcelementia.objects.PowderTFCE;
import tfcelementia.objects.items.ItemGemTFCE;
import tfcelementia.objects.items.ItemPowderGemTFCE;
import tfcelementia.objects.items.ItemPowderTFCE;
import tfcelementia.objects.items.ItemTFCE;
import tfcelementia.objects.items.ItemsTFCE;
import tfcelementia.objects.items.metal.ItemMetalTFCE;
import tfcelementia.types.PlantsTFCE;
import tfcelementia.util.OreDictionaryHelper;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.Metal.ItemType.*;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_MISC;
import static net.dries007.tfc.objects.fluids.FluidsTFC.*;
import static net.dries007.tfc.types.DefaultMetals.*;
import static net.dries007.tfc.util.Helpers.getNull;
import static net.dries007.tfc.util.forge.ForgeRule.*;
import static net.dries007.tfc.util.skills.SmithingSkill.Type.*;

import static tfcelementia.TFCElementia.MODID;
import static tfcelementia.types.MetalsTFCE.*;

/**
 * In 1.14+, every line in here needs to be a json file. Yay, but also ugh.
 */
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public class RecipesMetalTFCE
{

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void onRegisterBloomeryRecipeEvent(RegistryEvent.Register<BloomeryRecipe> event)
    {
        IForgeRegistry<BloomeryRecipe> registry = event.getRegistry();

        // Transition metals
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(MANGANESE), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(COBALT), FuelManager::isItemBloomeryFuel));
        
        // Metalloids
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(ALUMINIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(SILICIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(GERMANIUM), FuelManager::isItemBloomeryFuel));

        // Lanthanides
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(LANTHANUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(NEODYMIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(PROMETHIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(SAMARIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(GADOLINIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(TERBIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(DYSPROSIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(YTTERBIUM), FuelManager::isItemBloomeryFuel));

        // Actinides
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(ACTINIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(URANIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(NEPTUNIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(PLUTONIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(AMERICIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(CURIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(BERKELIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(CALIFORNIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(EINSTEINIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(MENDELEVIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(NOBELIUM), FuelManager::isItemBloomeryFuel));
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(LAWRENCIUM), FuelManager::isItemBloomeryFuel));
        
        // Fantasy
        registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(ARDITE), FuelManager::isItemBloomeryFuel));
        
    }

    @SubscribeEvent
    public static void onRegisterBlastFurnaceRecipeEvent(RegistryEvent.Register<BlastFurnaceRecipe> event)
    {
        IForgeRegistry<BlastFurnaceRecipe> registry = event.getRegistry();

        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(CAST_IRON_SULFUR), TFCRegistries.METALS.getValue(CAST_IRON_MANGANESE), IIngredient.of("dustSulfur")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(CAST_IRON_PHOSPHORUS), TFCRegistries.METALS.getValue(CAST_IRON_SULFUR), IIngredient.of("dustPhosphorite")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(WROUGHT_IRON), TFCRegistries.METALS.getValue(CAST_IRON_PHOSPHORUS), IIngredient.of("dustFlux")));
        
        // Transition metals
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(OSMIUM), TFCRegistries.METALS.getValue(OSMIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(SCANDIUM), TFCRegistries.METALS.getValue(SCANDIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(TITANIUM), TFCRegistries.METALS.getValue(TITANIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(VANADIUM), TFCRegistries.METALS.getValue(VANADIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(CHROMIUM), TFCRegistries.METALS.getValue(CHROMIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(YTTRIUM), TFCRegistries.METALS.getValue(YTTRIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(ZIRCONIUM), TFCRegistries.METALS.getValue(ZIRCONIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(NIOBIUM), TFCRegistries.METALS.getValue(NIOBIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(MOLYBDENUM), TFCRegistries.METALS.getValue(MOLYBDENUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(TECHNETIUM), TFCRegistries.METALS.getValue(TECHNETIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(RUTHENIUM), TFCRegistries.METALS.getValue(RUTHENIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(RHODIUM), TFCRegistries.METALS.getValue(RHODIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(PALLADIUM), TFCRegistries.METALS.getValue(PALLADIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(HAFNIUM), TFCRegistries.METALS.getValue(HAFNIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(TANTALUM), TFCRegistries.METALS.getValue(TANTALUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(TUNGSTEN), TFCRegistries.METALS.getValue(TUNGSTEN), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(RHENIUM), TFCRegistries.METALS.getValue(RHENIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(OSMIUM), TFCRegistries.METALS.getValue(OSMIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(IRIDIUM), TFCRegistries.METALS.getValue(IRIDIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(RUTHERFORDIUM), TFCRegistries.METALS.getValue(RUTHERFORDIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(DUBNIUM), TFCRegistries.METALS.getValue(DUBNIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(SEABORGIUM), TFCRegistries.METALS.getValue(SEABORGIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(BOHRIUM), TFCRegistries.METALS.getValue(BOHRIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(HASSIUM), TFCRegistries.METALS.getValue(HASSIUM), IIngredient.of("dustFlux")));

        // Metalloids
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(BORON), TFCRegistries.METALS.getValue(BORON), IIngredient.of("dustFlux")));

        // Lanthanides
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(HOLMIUM), TFCRegistries.METALS.getValue(HOLMIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(ERBIUM), TFCRegistries.METALS.getValue(ERBIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(THULIUM), TFCRegistries.METALS.getValue(THULIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(LUTETIUM), TFCRegistries.METALS.getValue(LUTETIUM), IIngredient.of("dustFlux")));

        // Actinides
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(THORIUM), TFCRegistries.METALS.getValue(THORIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(PROTACTINIUM), TFCRegistries.METALS.getValue(PROTACTINIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(FERMIUM), TFCRegistries.METALS.getValue(FERMIUM), IIngredient.of("dustFlux")));
    }
    
    /*
     * Old Recipes
    {
        IForgeRegistry<BlastFurnaceRecipe> registry = event.getRegistry();

        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "cast_iron_sulfur_mix"), TFCRegistries.METALS.getValue(CAST_IRON_SULFUR), TFCRegistries.METALS.getValue(CAST_IRON_MANGANESE), IIngredient.of("dustSulfur")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "cast_iron_phosphorite_mix"), TFCRegistries.METALS.getValue(CAST_IRON_PHOSPHORUS), TFCRegistries.METALS.getValue(CAST_IRON_SULFUR), IIngredient.of("dustPhosphorite")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "cast_iron_to_wrought_iron"), TFCRegistries.METALS.getValue(WROUGHT_IRON), TFCRegistries.METALS.getValue(CAST_IRON_PHOSPHORUS), IIngredient.of("dustFlux")));
        
        // Transition metals
        registry.register(new BlastFurnaceRecipe(TFCRegistries.METALS.getValue(OSMIUM), TFCRegistries.METALS.getValue(OSMIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "scandium"), TFCRegistries.METALS.getValue(SCANDIUM), TFCRegistries.METALS.getValue(SCANDIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "titanium"), TFCRegistries.METALS.getValue(TITANIUM), TFCRegistries.METALS.getValue(TITANIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "vanadium"), TFCRegistries.METALS.getValue(VANADIUM), TFCRegistries.METALS.getValue(VANADIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "chromium"), TFCRegistries.METALS.getValue(CHROMIUM), TFCRegistries.METALS.getValue(CHROMIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "yttrium"), TFCRegistries.METALS.getValue(YTTRIUM), TFCRegistries.METALS.getValue(YTTRIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "zirconium"), TFCRegistries.METALS.getValue(ZIRCONIUM), TFCRegistries.METALS.getValue(ZIRCONIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "niobium"), TFCRegistries.METALS.getValue(NIOBIUM), TFCRegistries.METALS.getValue(NIOBIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "molybdenum"), TFCRegistries.METALS.getValue(MOLYBDENUM), TFCRegistries.METALS.getValue(MOLYBDENUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "technetium"), TFCRegistries.METALS.getValue(TECHNETIUM), TFCRegistries.METALS.getValue(TECHNETIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "ruthenium"), TFCRegistries.METALS.getValue(RUTHENIUM), TFCRegistries.METALS.getValue(RUTHENIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "rhodium"), TFCRegistries.METALS.getValue(RHODIUM), TFCRegistries.METALS.getValue(RHODIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "palladium"), TFCRegistries.METALS.getValue(PALLADIUM), TFCRegistries.METALS.getValue(PALLADIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "hafnium"), TFCRegistries.METALS.getValue(HAFNIUM), TFCRegistries.METALS.getValue(HAFNIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "tantalum"), TFCRegistries.METALS.getValue(TANTALUM), TFCRegistries.METALS.getValue(TANTALUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "tungsten"), TFCRegistries.METALS.getValue(TUNGSTEN), TFCRegistries.METALS.getValue(TUNGSTEN), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "rhenium"), TFCRegistries.METALS.getValue(RHENIUM), TFCRegistries.METALS.getValue(RHENIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "osmium"), TFCRegistries.METALS.getValue(OSMIUM), TFCRegistries.METALS.getValue(OSMIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "iridium"), TFCRegistries.METALS.getValue(IRIDIUM), TFCRegistries.METALS.getValue(IRIDIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "rutherfordium"), TFCRegistries.METALS.getValue(RUTHERFORDIUM), TFCRegistries.METALS.getValue(RUTHERFORDIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "dubnium"), TFCRegistries.METALS.getValue(DUBNIUM), TFCRegistries.METALS.getValue(DUBNIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "seaborgium"), TFCRegistries.METALS.getValue(SEABORGIUM), TFCRegistries.METALS.getValue(SEABORGIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "bohrium"), TFCRegistries.METALS.getValue(BOHRIUM), TFCRegistries.METALS.getValue(BOHRIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "hassium"), TFCRegistries.METALS.getValue(HASSIUM), TFCRegistries.METALS.getValue(HASSIUM), IIngredient.of("dustFlux")));

        // Metalloids
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "boron"), TFCRegistries.METALS.getValue(BORON), TFCRegistries.METALS.getValue(BORON), IIngredient.of("dustFlux")));

        // Lanthanides
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "holmium"), TFCRegistries.METALS.getValue(HOLMIUM), TFCRegistries.METALS.getValue(HOLMIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "erbium"), TFCRegistries.METALS.getValue(ERBIUM), TFCRegistries.METALS.getValue(ERBIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "thulium"), TFCRegistries.METALS.getValue(THULIUM), TFCRegistries.METALS.getValue(THULIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "lutetium"), TFCRegistries.METALS.getValue(LUTETIUM), TFCRegistries.METALS.getValue(LUTETIUM), IIngredient.of("dustFlux")));

        // Actinides
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "thorium"), TFCRegistries.METALS.getValue(THORIUM), TFCRegistries.METALS.getValue(THORIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "protactinium"), TFCRegistries.METALS.getValue(PROTACTINIUM), TFCRegistries.METALS.getValue(PROTACTINIUM), IIngredient.of("dustFlux")));
        registry.register(new BlastFurnaceRecipe(new ResourceLocation(TFCElementia.MODID, "fermium"), TFCRegistries.METALS.getValue(FERMIUM), TFCRegistries.METALS.getValue(FERMIUM), IIngredient.of("dustFlux")));
    }
    */

    @SubscribeEvent
    public static void onRegisterAnvilRecipeEvent(RegistryEvent.Register<AnvilRecipe> event)
    {
    	// Blooms
        IForgeRegistry<AnvilRecipe> r = event.getRegistry();
        
        Metal manganese = TFCRegistries.METALS.getValue(MANGANESE);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "manganese_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == manganese && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(manganese, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal cobalt = TFCRegistries.METALS.getValue(COBALT);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "cobalt_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == cobalt && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(cobalt, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal aluminium = TFCRegistries.METALS.getValue(ALUMINIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "aluminium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == aluminium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        }, 
        new ItemStack(ItemMetal.get(aluminium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal silicium = TFCRegistries.METALS.getValue(SILICIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "silicium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == silicium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(silicium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal germanium = TFCRegistries.METALS.getValue(GERMANIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "germanium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == germanium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(germanium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal lanthanum = TFCRegistries.METALS.getValue(LANTHANUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "lanthanum_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == lanthanum && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(lanthanum, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal neodymium = TFCRegistries.METALS.getValue(NEODYMIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "neodymium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == neodymium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(neodymium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal promethium = TFCRegistries.METALS.getValue(PROMETHIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "promethium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == promethium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(promethium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal samarium = TFCRegistries.METALS.getValue(SAMARIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "samarium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == samarium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(samarium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal gadolinium = TFCRegistries.METALS.getValue(GADOLINIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "gadolinium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == gadolinium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(gadolinium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal terbium = TFCRegistries.METALS.getValue(TERBIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "terbium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == terbium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(terbium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal dysprosium = TFCRegistries.METALS.getValue(DYSPROSIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "dysprosium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == dysprosium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(dysprosium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal ytterbium = TFCRegistries.METALS.getValue(YTTERBIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "ytterbium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == ytterbium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(ytterbium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal actinium = TFCRegistries.METALS.getValue(ACTINIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "actinium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == actinium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(actinium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal uranium = TFCRegistries.METALS.getValue(URANIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "uranium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == uranium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(uranium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal neptunium = TFCRegistries.METALS.getValue(NEPTUNIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "neptunium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == neptunium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(neptunium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal plutonium = TFCRegistries.METALS.getValue(PLUTONIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "plutonium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == plutonium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(plutonium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal americium = TFCRegistries.METALS.getValue(AMERICIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "americium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == americium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(americium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal curium = TFCRegistries.METALS.getValue(CURIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "curium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == curium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(curium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal berkelium = TFCRegistries.METALS.getValue(BERKELIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "berkelium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == berkelium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(berkelium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal californium = TFCRegistries.METALS.getValue(CALIFORNIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "californium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == californium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(californium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal einsteinium = TFCRegistries.METALS.getValue(EINSTEINIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "einsteinium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == einsteinium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(einsteinium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal mendelevium = TFCRegistries.METALS.getValue(MENDELEVIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "mendelevium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == mendelevium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(mendelevium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal nobelium = TFCRegistries.METALS.getValue(NOBELIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "nobelium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == nobelium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(nobelium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal lawrencium = TFCRegistries.METALS.getValue(LAWRENCIUM);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "lawrencium_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == lawrencium && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(lawrencium, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        Metal ardite = TFCRegistries.METALS.getValue(ARDITE);
        r.register(new AnvilRecipe(new ResourceLocation(TFCElementia.MODID, "ardite_bloom"), x -> {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == ardite && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        },
        new ItemStack(ItemMetal.get(ardite, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        
        
        // Ingots, sheets & plates
        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
	    {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient;

            //Register all plates
            ingredient = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.INGOT)));
            Item item = ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.PLATE);
            if (item != null)
            {
                ItemStack output = new ItemStack(item);
                if (!output.isEmpty())
                {
                    //noinspection ConstantConditions
                    r.register(new AnvilRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_plate"), ingredient, output, metal.getTier(), null, ForgeRule.HIT_LAST, ForgeRule.SHRINK_SECOND_LAST, ForgeRule.HIT_THIRD_LAST));
                }
            }
	    }

	    /*
        IForgeRegistry<AnvilRecipe> r = event.getRegistry();
        
	    for (Metal metal : TFCRegistries.METALS.getValuesCollection())
	    {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            //IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.INGOT)));
            IIngredient<ItemStack> ingredient;
	        
            Item item = ItemMetal.get(metal, Metal.ItemType.INGOT);
            if (item != null)
            {
                ingredient = IIngredient.of(new ItemStack(item));
                Item outputItem = ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.PLATE);
                if (outputItem != null)
                {
                    ItemStack output = new ItemStack(outputItem);
                    if (!output.isEmpty())
		            {
		                //noinspection ConstantConditions
		                r.register(new AnvilRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_plate"), ingredient, output, metal.getTier(), null, ForgeRule.HIT_LAST, ForgeRule.SHRINK_SECOND_LAST, ForgeRule.HIT_THIRD_LAST));
		            }
                }
            }
	    }
	    */
        
        // Double Plate -> Weak Damascus Recipes (Second Step)
        addAnvil2(r, "weak_damascus_bismuth", INGOT, BISMUTH_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BISMUTH), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_bismuth_bronze", INGOT, BISMUTH_BRONZE_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BISMUTH_BRONZE), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_black_bronze", INGOT, BLACK_BRONZE_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BLACK_BRONZE), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_brass", INGOT, BRASS_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BRASS), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_bronze", INGOT, BRONZE_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BRONZE), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_copper", INGOT, COPPER_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_COPPER), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_gold", INGOT, GOLD_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_GOLD), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_rose_gold", INGOT, ROSE_GOLD_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ROSE_GOLD), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_silver", INGOT, SILVER_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_SILVER), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_tin", INGOT, TIN_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TIN), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_zinc", INGOT, ZINC_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ZINC), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_sterling", INGOT, STERLING_SILVER_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_STERLING_SILVER), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_wrought_iron", INGOT, WROUGHT_IRON_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_WROUGHT_IRON), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_pig_iron", INGOT, PIG_IRON_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PIG_IRON), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_steel", INGOT, STEEL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_STEEL), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_platinum", INGOT, PLATINUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PLATINUM), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_black_steel", INGOT, BLACK_STEEL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BLACK_STEEL), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_blue_steel", INGOT, BLUE_STEEL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BLUE_STEEL), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_red_steel", INGOT, RED_STEEL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RED_STEEL), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_lithium", INGOT, LITHIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_LITHIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_natrium", INGOT, NATRIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_NATRIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_kalium", INGOT, KALIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_KALIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_rubidium", INGOT, RUBIDIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RUBIDIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_caesium", INGOT, CAESIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CAESIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_francium", INGOT, FRANCIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_FRANCIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_beryllium", INGOT, BERYLLIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BERYLLIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_magnesium", INGOT, MAGNESIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MAGNESIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_calcium", INGOT, CALCIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CALCIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_strontium", INGOT, STRONTIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_STRONTIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_barium", INGOT, BARIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BARIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_radium", INGOT, RADIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RADIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_scandium", INGOT, SCANDIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_SCANDIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_titanium", INGOT, TITANIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TITANIUM), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_vandaium", INGOT, VANADIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_VANADIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_chromium", INGOT, CHROMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CHROMIUM), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_manganese", INGOT, MANGANESE_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MANGANESE), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_cobalt", INGOT, COBALT_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_COBALT), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_yttrium", INGOT, YTTRIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_YTTRIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_zirconium", INGOT, ZIRCONIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ZIRCONIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_niobium", INGOT, NIOBIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_NIOBIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_molybdenum", INGOT, MOLYBDENUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MOLYBDENUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_technetium", INGOT, TECHNETIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TECHNETIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_ruthenium", INGOT, RUTHENIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RUTHENIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_rhodium", INGOT, RHODIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RHODIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_palladium", INGOT, PALLADIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PALLADIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_hafnium", INGOT, HAFNIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_HAFNIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_tantalum", INGOT, TANTALUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TANTALUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_tungsten", INGOT, TUNGSTEN_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TUNGSTEN), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_rhenium", INGOT, RHENIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RHENIUM), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_osmium", INGOT, OSMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_OSMIUM), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_iridium", INGOT, IRIDIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_IRIDIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_rutherfordium", INGOT, RUTHERFORDIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RUTHERFORDIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_dubnium", INGOT, DUBNIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_DUBNIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_seaborgium", INGOT, SEABORGIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_SEABORGIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_bohrium", INGOT, BOHRIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BOHRIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_hassium", INGOT, HASSIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_HASSIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_copernicium", INGOT, COPERNICIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_COPERNICIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_aluminium", INGOT, ALUMINIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ALUMINIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_gallium", INGOT, GALLIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_GALLIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_cadmium", INGOT, CADMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CADMIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_indium", INGOT, INDIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_INDIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_mercury", INGOT, MERCURY_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MERCURY), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_thallium", INGOT, THALLIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_THALLIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_polonium", INGOT, POLONIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_POLONIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_boron", INGOT, BORON_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BORON), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_silicium", INGOT, SILICIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_SILICIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_germanium", INGOT, GERMANIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_GERMANIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_arsenic", INGOT, ARSENIC_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ARSENIC), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_antimony", INGOT, ANTIMONY_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ANTIMONY), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_tellurium", INGOT, TELLURIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TELLURIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_astatine", INGOT, ASTATINE_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ASTATINE), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_lanthanum", INGOT, LANTHANUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_LANTHANUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_cerium", INGOT, CERIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CERIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_praseodymium", INGOT, PRASEODYMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PRASEODYMIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_neodymium", INGOT, NEODYMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_NEODYMIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_promethium", INGOT, PROMETHIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PROMETHIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_samarium", INGOT, SAMARIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_SAMARIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_europium", INGOT, EUROPIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_EUROPIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_gadolinium", INGOT, GADOLINIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_GADOLINIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_terbium", INGOT, TERBIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TERBIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_dysprosium", INGOT, DYSPROSIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_DYSPROSIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_holmium", INGOT, HOLMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_HOLMIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_erbium", INGOT, ERBIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ERBIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_thulium", INGOT, THULIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_THULIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_ytterbium", INGOT, YTTERBIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_YTTERBIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_lutetium", INGOT, LUTETIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_LUTETIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_actinium", INGOT, ACTINIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ACTINIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_thorium", INGOT, THORIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_THORIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_protactinium", INGOT, PROTACTINIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PROTACTINIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_uranium", INGOT, URANIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_URANIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_neptunium", INGOT, NEPTUNIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_NEPTUNIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_plutonium", INGOT, PLUTONIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PLUTONIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_americium", INGOT, AMERICIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_AMERICIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_curium", INGOT, CURIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CURIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_berkelium", INGOT, BERKELIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BERKELIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_californium", INGOT, CALIFORNIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CALIFORNIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_einsteinium", INGOT, EINSTEINIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_EINSTEINIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_fermium", INGOT, FERMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_FERMIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_mendelevium", INGOT, MENDELEVIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MENDELEVIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_nobelium", INGOT, NOBELIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_NOBELIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_lawrencium", INGOT, LAWRENCIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_LAWRENCIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_aluminium_brass", INGOT, ALUMINIUM_BRASS_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ALUMINIUM_BRASS), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_constantan", INGOT, CONSTANTAN_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CONSTANTAN), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_electrum", INGOT, ELECTRUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ELECTRUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_invar", INGOT, INVAR_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_INVAR), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_nickel_silver", INGOT, NICKEL_SILVER_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_NICKEL_SILVER), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_orichalcum", INGOT, ORICHALCUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ORICHALCUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_red_alloy", INGOT, RED_ALLOY_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RED_ALLOY), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_tungsten_steel", INGOT, TUNGSTEN_STEEL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TUNGSTEN_STEEL), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_stainless_steel", INGOT, STAINLESS_STEEL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_STAINLESS_STEEL), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_lockalloy", INGOT, LOCKALLOY_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_LOCKALLOY), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_manganin", INGOT, MANGANIN_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MANGANIN), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_galinstan", INGOT, GALINSTAN_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_GALINSTAN), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_crown_gold", INGOT, CROWN_GOLD_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CROWN_GOLD), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_white_gold", INGOT, WHITE_GOLD_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_WHITE_GOLD), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_solder", INGOT, SOLDER_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_SOLDER), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_magnox", INGOT, MAGNOX_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MAGNOX), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_platinum_sterling", INGOT, PLATINUM_STERLING_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PLATINUM_STERLING), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_titanium_gold", INGOT, TITANIUM_GOLD_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TITANIUM_GOLD), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_pewter", INGOT, PEWTER_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PEWTER), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_cast_iron", INGOT, CAST_IRON_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CAST_IRON), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_mithril", INGOT, MITHRIL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MITHRIL), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_ardite", INGOT, ARDITE_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ARDITE), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_manyullyn", INGOT, MANYULLYN_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MANYULLYN), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_alchemical_brass", INGOT, ALCHEMICAL_BRASS_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ALCHEMICAL_BRASS), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_thaumium", INGOT, THAUMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_THAUMIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_voidmetal", INGOT, VOIDMETAL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_VOIDMETAL), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_signalum", INGOT, SIGNALUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_SIGNALUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_lumium", INGOT, LUMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_LUMIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_enderium", INGOT, ENDERIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ENDERIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "weak_damascus_adamantium", INGOT, ADAMANTIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ADAMANTIUM), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        
        /*
        addAnvil1(r, "weak_damascus_bismuth", ItemMetalTFCE.ItemType.PLATE_DOUBLE, BISMUTH_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BISMUTH), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_bismuth_bronze", ItemMetalTFCE.ItemType.PLATE_DOUBLE, BISMUTH_BRONZE_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BISMUTH_BRONZE), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_black_bronze", ItemMetalTFCE.ItemType.PLATE_DOUBLE, BLACK_BRONZE_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BLACK_BRONZE), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_brass", ItemMetalTFCE.ItemType.PLATE_DOUBLE, BRASS_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BRASS), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_bronze", ItemMetalTFCE.ItemType.PLATE_DOUBLE, BRONZE_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BRONZE), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_copper", ItemMetalTFCE.ItemType.PLATE_DOUBLE, COPPER_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_COPPER), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_gold", ItemMetalTFCE.ItemType.PLATE_DOUBLE, GOLD_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_GOLD), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_rose_gold", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ROSE_GOLD_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ROSE_GOLD), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_silver", ItemMetalTFCE.ItemType.PLATE_DOUBLE, SILVER_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_SILVER), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_tin", ItemMetalTFCE.ItemType.PLATE_DOUBLE, TIN_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TIN), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_zinc", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ZINC_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ZINC), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_sterling", ItemMetalTFCE.ItemType.PLATE_DOUBLE, STERLING_SILVER_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_STERLING_SILVER), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_wrought_iron", ItemMetalTFCE.ItemType.PLATE_DOUBLE, WROUGHT_IRON_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_WROUGHT_IRON), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_pig_iron", ItemMetalTFCE.ItemType.PLATE_DOUBLE, PIG_IRON_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PIG_IRON), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_steel", ItemMetalTFCE.ItemType.PLATE_DOUBLE, STEEL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_STEEL), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_platinum", ItemMetalTFCE.ItemType.PLATE_DOUBLE, PLATINUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PLATINUM), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_black_steel", ItemMetalTFCE.ItemType.PLATE_DOUBLE, BLACK_STEEL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BLACK_STEEL), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_blue_steel", ItemMetalTFCE.ItemType.PLATE_DOUBLE, BLUE_STEEL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BLUE_STEEL), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_red_steel", ItemMetalTFCE.ItemType.PLATE_DOUBLE, RED_STEEL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RED_STEEL), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_lithium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, LITHIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_LITHIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_natrium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, NATRIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_NATRIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_kalium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, KALIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_KALIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_rubidium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, RUBIDIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RUBIDIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_caesium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, CAESIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CAESIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_francium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, FRANCIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_FRANCIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_beryllium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, BERYLLIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BERYLLIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_magnesium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, MAGNESIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MAGNESIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_calcium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, CALCIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CALCIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_strontium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, STRONTIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_STRONTIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_barium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, BARIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BARIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_radium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, RADIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RADIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_scandium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, SCANDIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_SCANDIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_titanium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, TITANIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TITANIUM), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_vandaium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, VANADIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_VANADIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_chromium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, CHROMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CHROMIUM), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_manganese", ItemMetalTFCE.ItemType.PLATE_DOUBLE, MANGANESE_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MANGANESE), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_cobalt", ItemMetalTFCE.ItemType.PLATE_DOUBLE, COBALT_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_COBALT), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_yttrium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, YTTRIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_YTTRIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_zirconium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ZIRCONIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ZIRCONIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_niobium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, NIOBIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_NIOBIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_molybdenum", ItemMetalTFCE.ItemType.PLATE_DOUBLE, MOLYBDENUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MOLYBDENUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_technetium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, TECHNETIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TECHNETIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_ruthenium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, RUTHENIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RUTHENIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_rhodium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, RHODIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RHODIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_palladium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, PALLADIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PALLADIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_hafnium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, HAFNIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_HAFNIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_tantalum", ItemMetalTFCE.ItemType.PLATE_DOUBLE, TANTALUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TANTALUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_tungsten", ItemMetalTFCE.ItemType.PLATE_DOUBLE, TUNGSTEN_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TUNGSTEN), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_rhenium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, RHENIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RHENIUM), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_osmium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, OSMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_OSMIUM), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_iridium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, IRIDIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_IRIDIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_rutherfordium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, RUTHERFORDIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RUTHERFORDIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_dubnium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, DUBNIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_DUBNIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_seaborgium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, SEABORGIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_SEABORGIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_bohrium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, BOHRIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BOHRIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_hassium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, HASSIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_HASSIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_copernicium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, COPERNICIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_COPERNICIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_aluminium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ALUMINIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ALUMINIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_gallium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, GALLIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_GALLIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_cadmium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, CADMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CADMIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_indium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, INDIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_INDIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_mercury", ItemMetalTFCE.ItemType.PLATE_DOUBLE, MERCURY_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MERCURY), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_thallium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, THALLIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_THALLIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_polonium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, POLONIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_POLONIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_boron", ItemMetalTFCE.ItemType.PLATE_DOUBLE, BORON_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BORON), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_silicium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, SILICIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_SILICIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_germanium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, GERMANIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_GERMANIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_arsenic", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ARSENIC_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ARSENIC), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_antimony", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ANTIMONY_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ANTIMONY), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_tellurium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, TELLURIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TELLURIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_astatine", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ASTATINE_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ASTATINE), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_lanthanum", ItemMetalTFCE.ItemType.PLATE_DOUBLE, LANTHANUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_LANTHANUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_cerium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, CERIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CERIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_praseodymium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, PRASEODYMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PRASEODYMIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_neodymium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, NEODYMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_NEODYMIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_promethium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, PROMETHIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PROMETHIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_samarium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, SAMARIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_SAMARIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_europium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, EUROPIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_EUROPIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_gadolinium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, GADOLINIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_GADOLINIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_terbium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, TERBIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TERBIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_dysprosium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, DYSPROSIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_DYSPROSIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_holmium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, HOLMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_HOLMIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_erbium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ERBIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ERBIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_thulium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, THULIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_THULIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_ytterbium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, YTTERBIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_YTTERBIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_lutetium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, LUTETIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_LUTETIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_actinium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ACTINIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ACTINIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_thorium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, THORIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_THORIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_protactinium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, PROTACTINIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PROTACTINIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_uranium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, URANIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_URANIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_neptunium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, NEPTUNIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_NEPTUNIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_plutonium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, PLUTONIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PLUTONIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_americium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, AMERICIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_AMERICIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_curium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, CURIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CURIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_berkelium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, BERKELIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_BERKELIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_californium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, CALIFORNIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CALIFORNIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_einsteinium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, EINSTEINIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_EINSTEINIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_fermium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, FERMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_FERMIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_mendelevium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, MENDELEVIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MENDELEVIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_nobelium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, NOBELIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_NOBELIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_lawrencium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, LAWRENCIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_LAWRENCIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_aluminium_brass", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ALUMINIUM_BRASS_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ALUMINIUM_BRASS), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_constantan", ItemMetalTFCE.ItemType.PLATE_DOUBLE, CONSTANTAN_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CONSTANTAN), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_electrum", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ELECTRUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ELECTRUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_invar", ItemMetalTFCE.ItemType.PLATE_DOUBLE, INVAR_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_INVAR), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_nickel_silver", ItemMetalTFCE.ItemType.PLATE_DOUBLE, NICKEL_SILVER_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_NICKEL_SILVER), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_orichalcum", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ORICHALCUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ORICHALCUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_red_alloy", ItemMetalTFCE.ItemType.PLATE_DOUBLE, RED_ALLOY_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_RED_ALLOY), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_tungsten_steel", ItemMetalTFCE.ItemType.PLATE_DOUBLE, TUNGSTEN_STEEL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TUNGSTEN_STEEL), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_stainless_steel", ItemMetalTFCE.ItemType.PLATE_DOUBLE, STAINLESS_STEEL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_STAINLESS_STEEL), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_lockalloy", ItemMetalTFCE.ItemType.PLATE_DOUBLE, LOCKALLOY_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_LOCKALLOY), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_manganin", ItemMetalTFCE.ItemType.PLATE_DOUBLE, MANGANIN_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MANGANIN), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_galinstan", ItemMetalTFCE.ItemType.PLATE_DOUBLE, GALINSTAN_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_GALINSTAN), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_crown_gold", ItemMetalTFCE.ItemType.PLATE_DOUBLE, CROWN_GOLD_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CROWN_GOLD), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_white_gold", ItemMetalTFCE.ItemType.PLATE_DOUBLE, WHITE_GOLD_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_WHITE_GOLD), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_solder", ItemMetalTFCE.ItemType.PLATE_DOUBLE, SOLDER_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_SOLDER), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_magnox", ItemMetalTFCE.ItemType.PLATE_DOUBLE, MAGNOX_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MAGNOX), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_platinum_sterling", ItemMetalTFCE.ItemType.PLATE_DOUBLE, PLATINUM_STERLING_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PLATINUM_STERLING), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_titanium_gold", ItemMetalTFCE.ItemType.PLATE_DOUBLE, TITANIUM_GOLD_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_TITANIUM_GOLD), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_pewter", ItemMetalTFCE.ItemType.PLATE_DOUBLE, PEWTER_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_PEWTER), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_cast_iron", ItemMetalTFCE.ItemType.PLATE_DOUBLE, CAST_IRON_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_CAST_IRON), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_mithril", ItemMetalTFCE.ItemType.PLATE_DOUBLE, MITHRIL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MITHRIL), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_ardite", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ARDITE_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ARDITE), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_manyullyn", ItemMetalTFCE.ItemType.PLATE_DOUBLE, MANYULLYN_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_MANYULLYN), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_alchemical_brass", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ALCHEMICAL_BRASS_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ALCHEMICAL_BRASS), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_thaumium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, THAUMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_THAUMIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_voidmetal", ItemMetalTFCE.ItemType.PLATE_DOUBLE, VOIDMETAL_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_VOIDMETAL), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_signalum", ItemMetalTFCE.ItemType.PLATE_DOUBLE, SIGNALUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_SIGNALUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_lumium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, LUMIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_LUMIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_enderium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ENDERIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ENDERIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil1(r, "weak_damascus_adamantium", ItemMetalTFCE.ItemType.PLATE_DOUBLE, ADAMANTIUM_NICKEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(WEAK_DAMASCUS_ADAMANTIUM), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        */
        
        // Weak Damascus Ingots -> Damascus Ingot Recipes (Third & Last Step)
        // Vanilla TFC Metals
        addAnvil2(r, "damascus_bismuth", INGOT, WEAK_DAMASCUS_BISMUTH, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_BISMUTH), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_bismuth_bronze", INGOT, WEAK_DAMASCUS_BISMUTH_BRONZE, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_BISMUTH_BRONZE), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_black_bronze", INGOT, WEAK_DAMASCUS_BLACK_BRONZE, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_BLACK_BRONZE), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_brass", INGOT, WEAK_DAMASCUS_BRASS, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_BRASS), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_bronze", INGOT, WEAK_DAMASCUS_BRONZE, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_BRONZE), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_copper", INGOT, WEAK_DAMASCUS_COPPER, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_COPPER), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_gold", INGOT, WEAK_DAMASCUS_GOLD, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_GOLD), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_rose_gold", INGOT, WEAK_DAMASCUS_ROSE_GOLD, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ROSE_GOLD), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_silver", INGOT, WEAK_DAMASCUS_SILVER, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_SILVER), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_tin", INGOT, WEAK_DAMASCUS_TIN, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_TIN), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_zinc", INGOT, WEAK_DAMASCUS_ZINC, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ZINC), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_sterling", INGOT, WEAK_DAMASCUS_STERLING_SILVER, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_STERLING_SILVER), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_wrought_iron", INGOT, WEAK_DAMASCUS_WROUGHT_IRON, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_WROUGHT_IRON), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_pig_iron", INGOT, WEAK_DAMASCUS_PIG_IRON, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_PIG_IRON), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_steel", INGOT, WEAK_DAMASCUS_STEEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_STEEL), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_platinum", INGOT, WEAK_DAMASCUS_PLATINUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_PLATINUM), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_black_steel", INGOT, WEAK_DAMASCUS_BLACK_STEEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_BLACK_STEEL), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_blue_steel", INGOT, WEAK_DAMASCUS_BLUE_STEEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_BLUE_STEEL), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_red_steel", INGOT, WEAK_DAMASCUS_RED_STEEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_RED_STEEL), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_lithium", INGOT, WEAK_DAMASCUS_LITHIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_LITHIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_natrium", INGOT, WEAK_DAMASCUS_NATRIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_NATRIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_kalium", INGOT, WEAK_DAMASCUS_KALIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_KALIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_rubidium", INGOT, WEAK_DAMASCUS_RUBIDIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_RUBIDIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_caesium", INGOT, WEAK_DAMASCUS_CAESIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_CAESIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_francium", INGOT, WEAK_DAMASCUS_FRANCIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_FRANCIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_beryllium", INGOT, WEAK_DAMASCUS_BERYLLIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_BERYLLIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_magnesium", INGOT, WEAK_DAMASCUS_MAGNESIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_MAGNESIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_calcium", INGOT, WEAK_DAMASCUS_CALCIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_CALCIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_strontium", INGOT, WEAK_DAMASCUS_STRONTIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_STRONTIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_barium", INGOT, WEAK_DAMASCUS_BARIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_BARIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_radium", INGOT, WEAK_DAMASCUS_RADIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_RADIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_scandium", INGOT, WEAK_DAMASCUS_SCANDIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_SCANDIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_titanium", INGOT, WEAK_DAMASCUS_TITANIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_TITANIUM), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_vandaium", INGOT, WEAK_DAMASCUS_VANADIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_VANADIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_chromium", INGOT, WEAK_DAMASCUS_CHROMIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_CHROMIUM), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_manganese", INGOT, WEAK_DAMASCUS_MANGANESE, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_MANGANESE), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_cobalt", INGOT, WEAK_DAMASCUS_COBALT, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_COBALT), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_yttrium", INGOT, WEAK_DAMASCUS_YTTRIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_YTTRIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_zirconium", INGOT, WEAK_DAMASCUS_ZIRCONIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ZIRCONIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_niobium", INGOT, WEAK_DAMASCUS_NIOBIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_NIOBIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_molybdenum", INGOT, WEAK_DAMASCUS_MOLYBDENUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_MOLYBDENUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_technetium", INGOT, WEAK_DAMASCUS_TECHNETIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_TECHNETIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_ruthenium", INGOT, WEAK_DAMASCUS_RUTHENIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_RUTHENIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_rhodium", INGOT, WEAK_DAMASCUS_RHODIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_RHODIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_palladium", INGOT, WEAK_DAMASCUS_PALLADIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_PALLADIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_hafnium", INGOT, WEAK_DAMASCUS_HAFNIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_HAFNIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_tantalum", INGOT, WEAK_DAMASCUS_TANTALUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_TANTALUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_tungsten", INGOT, WEAK_DAMASCUS_TUNGSTEN, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_TUNGSTEN), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_rhenium", INGOT, WEAK_DAMASCUS_RHENIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_RHENIUM), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_osmium", INGOT, WEAK_DAMASCUS_OSMIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_OSMIUM), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_iridium", INGOT, WEAK_DAMASCUS_IRIDIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_IRIDIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_rutherfordium", INGOT, WEAK_DAMASCUS_RUTHERFORDIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_RUTHERFORDIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_dubnium", INGOT, WEAK_DAMASCUS_DUBNIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_DUBNIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_seaborgium", INGOT, WEAK_DAMASCUS_SEABORGIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_SEABORGIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_bohrium", INGOT, WEAK_DAMASCUS_BOHRIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_BOHRIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_hassium", INGOT, WEAK_DAMASCUS_HASSIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_HASSIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_copernicium", INGOT, WEAK_DAMASCUS_COPERNICIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_COPERNICIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_aluminium", INGOT, WEAK_DAMASCUS_ALUMINIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ALUMINIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_gallium", INGOT, WEAK_DAMASCUS_GALLIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_GALLIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_cadmium", INGOT, WEAK_DAMASCUS_CADMIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_CADMIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_indium", INGOT, WEAK_DAMASCUS_INDIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_INDIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_mercury", INGOT, WEAK_DAMASCUS_MERCURY, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_MERCURY), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_thallium", INGOT, WEAK_DAMASCUS_THALLIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_THALLIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_polonium", INGOT, WEAK_DAMASCUS_POLONIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_POLONIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_boron", INGOT, WEAK_DAMASCUS_BORON, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_BORON), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_silicium", INGOT, WEAK_DAMASCUS_SILICIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_SILICIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_germanium", INGOT, WEAK_DAMASCUS_GERMANIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_GERMANIUM), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_arsenic", INGOT, WEAK_DAMASCUS_ARSENIC, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ARSENIC), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_antimony", INGOT, WEAK_DAMASCUS_ANTIMONY, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ANTIMONY), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_tellurium", INGOT, WEAK_DAMASCUS_TELLURIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_TELLURIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_astatine", INGOT, WEAK_DAMASCUS_ASTATINE, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ASTATINE), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_lanthanum", INGOT, WEAK_DAMASCUS_LANTHANUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_LANTHANUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_cerium", INGOT, WEAK_DAMASCUS_CERIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_CERIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_praseodymium", INGOT, WEAK_DAMASCUS_PRASEODYMIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_PRASEODYMIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_neodymium", INGOT, WEAK_DAMASCUS_NEODYMIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_NEODYMIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_promethium", INGOT, WEAK_DAMASCUS_PROMETHIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_PROMETHIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_samarium", INGOT, WEAK_DAMASCUS_SAMARIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_SAMARIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_europium", INGOT, WEAK_DAMASCUS_EUROPIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_EUROPIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_gadolinium", INGOT, WEAK_DAMASCUS_GADOLINIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_GADOLINIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_terbium", INGOT, WEAK_DAMASCUS_TERBIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_TERBIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_dysprosium", INGOT, WEAK_DAMASCUS_DYSPROSIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_DYSPROSIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_holmium", INGOT, WEAK_DAMASCUS_HOLMIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_HOLMIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_erbium", INGOT, WEAK_DAMASCUS_ERBIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ERBIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_thulium", INGOT, WEAK_DAMASCUS_THULIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_THULIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_ytterbium", INGOT, WEAK_DAMASCUS_YTTERBIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_YTTERBIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_lutetium", INGOT, WEAK_DAMASCUS_LUTETIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_LUTETIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_actinium", INGOT, WEAK_DAMASCUS_ACTINIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ACTINIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_thorium", INGOT, WEAK_DAMASCUS_THORIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_THORIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_protactinium", INGOT, WEAK_DAMASCUS_PROTACTINIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_PROTACTINIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_uranium", INGOT, WEAK_DAMASCUS_URANIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_URANIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_neptunium", INGOT, WEAK_DAMASCUS_NEPTUNIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_NEPTUNIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_plutonium", INGOT, WEAK_DAMASCUS_PLUTONIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_PLUTONIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_americium", INGOT, WEAK_DAMASCUS_AMERICIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_AMERICIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_curium", INGOT, WEAK_DAMASCUS_CURIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_CURIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_berkelium", INGOT, WEAK_DAMASCUS_BERKELIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_BERKELIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_californium", INGOT, WEAK_DAMASCUS_CALIFORNIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_CALIFORNIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_einsteinium", INGOT, WEAK_DAMASCUS_EINSTEINIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_EINSTEINIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_fermium", INGOT, WEAK_DAMASCUS_FERMIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_FERMIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_mendelevium", INGOT, WEAK_DAMASCUS_MENDELEVIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_MENDELEVIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_nobelium", INGOT, WEAK_DAMASCUS_NOBELIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_NOBELIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_lawrencium", INGOT, WEAK_DAMASCUS_LAWRENCIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_LAWRENCIUM), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_aluminium_brass", INGOT, WEAK_DAMASCUS_ALUMINIUM_BRASS, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ALUMINIUM_BRASS), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_constantan", INGOT, WEAK_DAMASCUS_CONSTANTAN, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_CONSTANTAN), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_electrum", INGOT, WEAK_DAMASCUS_ELECTRUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ELECTRUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_invar", INGOT, WEAK_DAMASCUS_INVAR, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_INVAR), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_nickel_silver", INGOT, WEAK_DAMASCUS_NICKEL_SILVER, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_SILVER), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_orichalcum", INGOT, WEAK_DAMASCUS_ORICHALCUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ORICHALCUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_red_alloy", INGOT, WEAK_DAMASCUS_RED_ALLOY, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_RED_ALLOY), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_tungsten_steel", INGOT, WEAK_DAMASCUS_TUNGSTEN_STEEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_TUNGSTEN_STEEL), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_stainless_steel", INGOT, WEAK_DAMASCUS_STAINLESS_STEEL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_STAINLESS_STEEL), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_lockalloy", INGOT, WEAK_DAMASCUS_LOCKALLOY, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_LOCKALLOY), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_manganin", INGOT, WEAK_DAMASCUS_MANGANIN, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_MANGANIN), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_galinstan", INGOT, WEAK_DAMASCUS_GALINSTAN, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_GALINSTAN), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_crown_gold", INGOT, WEAK_DAMASCUS_CROWN_GOLD, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_CROWN_GOLD), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_white_gold", INGOT, WEAK_DAMASCUS_WHITE_GOLD, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_WHITE_GOLD), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_solder", INGOT, WEAK_DAMASCUS_SOLDER, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_SOLDER), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_magnox", INGOT, WEAK_DAMASCUS_MAGNOX, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_MAGNOX), INGOT)), Metal.Tier.TIER_I, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_platinum_sterling", INGOT, WEAK_DAMASCUS_PLATINUM_STERLING, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_PLATINUM_STERLING), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_titanium_gold", INGOT, WEAK_DAMASCUS_TITANIUM_GOLD, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_TITANIUM_GOLD), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_pewter", INGOT, WEAK_DAMASCUS_PEWTER, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_PEWTER), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_cast_iron", INGOT, WEAK_DAMASCUS_CAST_IRON, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_CAST_IRON), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_mithril", INGOT, WEAK_DAMASCUS_MITHRIL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_MITHRIL), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_ardite", INGOT, WEAK_DAMASCUS_ARDITE, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ARDITE), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_manyullyn", INGOT, WEAK_DAMASCUS_MANYULLYN, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_MANYULLYN), INGOT)), Metal.Tier.TIER_VI, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_alchemical_brass", INGOT, WEAK_DAMASCUS_ALCHEMICAL_BRASS, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ALCHEMICAL_BRASS), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_thaumium", INGOT, WEAK_DAMASCUS_THAUMIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_THAUMIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_voidmetal", INGOT, WEAK_DAMASCUS_VOIDMETAL, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_VOIDMETAL), INGOT)), Metal.Tier.TIER_IV, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_signalum", INGOT, WEAK_DAMASCUS_SIGNALUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_SIGNALUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_lumium", INGOT, WEAK_DAMASCUS_LUMIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_LUMIUM), INGOT)), Metal.Tier.TIER_II, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_enderium", INGOT, WEAK_DAMASCUS_ENDERIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ENDERIUM), INGOT)), Metal.Tier.TIER_III, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        addAnvil2(r, "damascus_adamantium", INGOT, WEAK_DAMASCUS_ADAMANTIUM, new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DAMASCUS_ADAMANTIUM), INGOT)), Metal.Tier.TIER_V, GENERAL, DRAW_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        
        
        
    }
    
    @SubscribeEvent
    public static void onRegisterWeldingRecipeEvent(RegistryEvent.Register<WeldingRecipe> event)
    {
        IForgeRegistry<WeldingRecipe> r = event.getRegistry();
        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.PLATE)));
            IIngredient<ItemStack> ingredient2 = IIngredient.of(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.PLATE)));
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.PLATE_DOUBLE));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_plate_double"), ingredient1, ingredient2, output, metal.getTier()));
            }
        }

        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.PLATE)));
            IIngredient<ItemStack> ingredient2 = IIngredient.of(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.PLATE_DOUBLE)));
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.PLATE_TRIPLE));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_plate_triple"), ingredient1, ingredient2, output, metal.getTier()));
            }
        }

        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.PLATE_DOUBLE)));
            IIngredient<ItemStack> ingredient2 = IIngredient.of(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.PLATE_DOUBLE)));
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.PLATE_QUADRUPLE));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_plate_quadruple"), ingredient1, ingredient2, output, metal.getTier()));
            }
        }

        /*
        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.PLATE)));
            IIngredient<ItemStack> ingredient2 = IIngredient.of(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.TRIPLE_PLATE)));
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.QUADRUPLE_PLATE));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_quadruple_plate"), ingredient1, ingredient2, output, metal.getTier()));
            }
        }
        */

        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.INGOT)));
            IIngredient<ItemStack> ingredient2 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.DOUBLE_INGOT)));
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.INGOT_TRIPLE));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_ingot_triple"), ingredient1, ingredient2, output, metal.getTier()));
            }
        }

        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.DOUBLE_INGOT)));
            IIngredient<ItemStack> ingredient2 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.DOUBLE_INGOT)));
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.INGOT_QUADRUPLE));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_ingot_quadruple"), ingredient1, ingredient2, output, metal.getTier()));
            }
        }

        /*
        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.INGOT)));
            IIngredient<ItemStack> ingredient2 = IIngredient.of(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.TRIPLE_INGOT)));
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.QUADRUPLE_INGOT));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_quadruple_ingot"), ingredient1, ingredient2, output, metal.getTier()));
            }
        }
        */

        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.SHEET)));
            IIngredient<ItemStack> ingredient2 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.DOUBLE_SHEET)));
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.SHEET_TRIPLE));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_sheet_triple"), ingredient1, ingredient2, output, metal.getTier()));
            }
        }

        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.DOUBLE_SHEET)));
            IIngredient<ItemStack> ingredient2 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.DOUBLE_SHEET)));
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.SHEET_QUADRUPLE));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_sheet_quadruple"), ingredient1, ingredient2, output, metal.getTier()));
            }
        }

        /*
        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.SHEET)));
            IIngredient<ItemStack> ingredient2 = IIngredient.of(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.TRIPLE_SHEET)));
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.QUADRUPLE_SHEET));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_quadruple_sheet"), ingredient1, ingredient2, output, metal.getTier()));
            }
        }
        */

        // Metal + Nickel Weld (First Step)
        addWelding1(r, BISMUTH, NICKEL, BISMUTH_NICKEL);
        addWelding1(r, BISMUTH_BRONZE, NICKEL, BISMUTH_BRONZE_NICKEL);
        addWelding1(r, BLACK_BRONZE, NICKEL, BLACK_BRONZE_NICKEL);
        addWelding1(r, BRASS, NICKEL, BRASS_NICKEL);
        addWelding1(r, BRONZE, NICKEL, BRONZE_NICKEL);
        addWelding1(r, COPPER, NICKEL, COPPER_NICKEL);
        addWelding1(r, GOLD, NICKEL, GOLD_NICKEL);
        addWelding1(r, ROSE_GOLD, NICKEL, ROSE_GOLD_NICKEL);
        addWelding1(r, SILVER, NICKEL, SILVER_NICKEL);
        addWelding1(r, TIN, NICKEL, TIN_NICKEL);
        addWelding1(r, ZINC, NICKEL, ZINC_NICKEL);
        addWelding1(r, STERLING_SILVER, NICKEL, STERLING_SILVER_NICKEL);
        addWelding1(r, WROUGHT_IRON, NICKEL, WROUGHT_IRON_NICKEL);
        addWelding1(r, PIG_IRON, NICKEL, PIG_IRON_NICKEL);
        addWelding1(r, STEEL, NICKEL, STEEL_NICKEL);
        addWelding1(r, PLATINUM, NICKEL, PLATINUM_NICKEL);
        addWelding1(r, BLACK_STEEL, NICKEL, BLACK_STEEL_NICKEL);
        addWelding1(r, BLUE_STEEL, NICKEL, BLUE_STEEL_NICKEL);
        addWelding1(r, RED_STEEL, NICKEL, RED_STEEL_NICKEL);
        addWelding1(r, LITHIUM, NICKEL, LITHIUM_NICKEL);
        addWelding1(r, NATRIUM, NICKEL, NATRIUM_NICKEL);
        addWelding1(r, KALIUM, NICKEL, KALIUM_NICKEL);
        addWelding1(r, RUBIDIUM, NICKEL, RUBIDIUM_NICKEL);
        addWelding1(r, CAESIUM, NICKEL, CAESIUM_NICKEL);
        addWelding1(r, FRANCIUM, NICKEL, FRANCIUM_NICKEL);
        addWelding1(r, BERYLLIUM, NICKEL, BERYLLIUM_NICKEL);
        addWelding1(r, MAGNESIUM, NICKEL, MAGNESIUM_NICKEL);
        addWelding1(r, CALCIUM, NICKEL, CALCIUM_NICKEL);
        addWelding1(r, STRONTIUM, NICKEL, STRONTIUM_NICKEL);
        addWelding1(r, BARIUM, NICKEL, BARIUM_NICKEL);
        addWelding1(r, RADIUM, NICKEL, RADIUM_NICKEL);
        addWelding1(r, SCANDIUM, NICKEL, SCANDIUM_NICKEL);
        addWelding1(r, TITANIUM, NICKEL, TITANIUM_NICKEL);
        addWelding1(r, VANADIUM, NICKEL, VANADIUM_NICKEL);
        addWelding1(r, CHROMIUM, NICKEL, CHROMIUM_NICKEL);
        addWelding1(r, MANGANESE, NICKEL, MANGANESE_NICKEL);
        addWelding1(r, COBALT, NICKEL, COBALT_NICKEL);
        addWelding1(r, YTTRIUM, NICKEL, YTTRIUM_NICKEL);
        addWelding1(r, ZIRCONIUM, NICKEL, ZIRCONIUM_NICKEL);
        addWelding1(r, NIOBIUM, NICKEL, NIOBIUM_NICKEL);
        addWelding1(r, MOLYBDENUM, NICKEL, MOLYBDENUM_NICKEL);
        addWelding1(r, TECHNETIUM, NICKEL, TECHNETIUM_NICKEL);
        addWelding1(r, RUTHENIUM, NICKEL, RUTHENIUM_NICKEL);
        addWelding1(r, RHODIUM, NICKEL, RHODIUM_NICKEL);
        addWelding1(r, PALLADIUM, NICKEL, PALLADIUM_NICKEL);
        addWelding1(r, HAFNIUM, NICKEL, HAFNIUM_NICKEL);
        addWelding1(r, TANTALUM, NICKEL, TANTALUM_NICKEL);
        addWelding1(r, TUNGSTEN, NICKEL, TUNGSTEN_NICKEL);
        addWelding1(r, RHENIUM, NICKEL, RHENIUM_NICKEL);
        addWelding1(r, OSMIUM, NICKEL, OSMIUM_NICKEL);
        addWelding1(r, IRIDIUM, NICKEL, IRIDIUM_NICKEL);
        addWelding1(r, RUTHERFORDIUM, NICKEL, RUTHERFORDIUM_NICKEL);
        addWelding1(r, DUBNIUM, NICKEL, DUBNIUM_NICKEL);
        addWelding1(r, SEABORGIUM, NICKEL, SEABORGIUM_NICKEL);
        addWelding1(r, BOHRIUM, NICKEL, BOHRIUM_NICKEL);
        addWelding1(r, HASSIUM, NICKEL, HASSIUM_NICKEL);
        addWelding1(r, COPERNICIUM, NICKEL, COPERNICIUM_NICKEL);
        addWelding1(r, ALUMINIUM, NICKEL, ALUMINIUM_NICKEL);
        addWelding1(r, GALLIUM, NICKEL, GALLIUM_NICKEL);
        addWelding1(r, CADMIUM, NICKEL, CADMIUM_NICKEL);
        addWelding1(r, INDIUM, NICKEL, INDIUM_NICKEL);
        addWelding1(r, MERCURY, NICKEL, MERCURY_NICKEL);
        addWelding1(r, THALLIUM, NICKEL, THALLIUM_NICKEL);
        addWelding1(r, POLONIUM, NICKEL, POLONIUM_NICKEL);
        addWelding1(r, BORON, NICKEL, BORON_NICKEL);
        addWelding1(r, SILICIUM, NICKEL, SILICIUM_NICKEL);
        addWelding1(r, GERMANIUM, NICKEL, GERMANIUM_NICKEL);
        addWelding1(r, ARSENIC, NICKEL, ARSENIC_NICKEL);
        addWelding1(r, ANTIMONY, NICKEL, ANTIMONY_NICKEL);
        addWelding1(r, TELLURIUM, NICKEL, TELLURIUM_NICKEL);
        addWelding1(r, ASTATINE, NICKEL, ASTATINE_NICKEL);
        addWelding1(r, LANTHANUM, NICKEL, LANTHANUM_NICKEL);
        addWelding1(r, CERIUM, NICKEL, CERIUM_NICKEL);
        addWelding1(r, PRASEODYMIUM, NICKEL, PRASEODYMIUM_NICKEL);
        addWelding1(r, NEODYMIUM, NICKEL, NEODYMIUM_NICKEL);
        addWelding1(r, PROMETHIUM, NICKEL, PROMETHIUM_NICKEL);
        addWelding1(r, SAMARIUM, NICKEL, SAMARIUM_NICKEL);
        addWelding1(r, EUROPIUM, NICKEL, EUROPIUM_NICKEL);
        addWelding1(r, GADOLINIUM, NICKEL, GADOLINIUM_NICKEL);
        addWelding1(r, TERBIUM, NICKEL, TERBIUM_NICKEL);
        addWelding1(r, DYSPROSIUM, NICKEL, DYSPROSIUM_NICKEL);
        addWelding1(r, HOLMIUM, NICKEL, HOLMIUM_NICKEL);
        addWelding1(r, ERBIUM, NICKEL, ERBIUM_NICKEL);
        addWelding1(r, THULIUM, NICKEL, THULIUM_NICKEL);
        addWelding1(r, YTTERBIUM, NICKEL, YTTERBIUM_NICKEL);
        addWelding1(r, LUTETIUM, NICKEL, LUTETIUM_NICKEL);
        addWelding1(r, ACTINIUM, NICKEL, ACTINIUM_NICKEL);
        addWelding1(r, THORIUM, NICKEL, THORIUM_NICKEL);
        addWelding1(r, PROTACTINIUM, NICKEL, PROTACTINIUM_NICKEL);
        addWelding1(r, URANIUM, NICKEL, URANIUM_NICKEL);
        addWelding1(r, NEPTUNIUM, NICKEL, NEPTUNIUM_NICKEL);
        addWelding1(r, PLUTONIUM, NICKEL, PLUTONIUM_NICKEL);
        addWelding1(r, AMERICIUM, NICKEL, AMERICIUM_NICKEL);
        addWelding1(r, CURIUM, NICKEL, CURIUM_NICKEL);
        addWelding1(r, BERKELIUM, NICKEL, BERKELIUM_NICKEL);
        addWelding1(r, CALIFORNIUM, NICKEL, CALIFORNIUM_NICKEL);
        addWelding1(r, EINSTEINIUM, NICKEL, EINSTEINIUM_NICKEL);
        addWelding1(r, FERMIUM, NICKEL, FERMIUM_NICKEL);
        addWelding1(r, MENDELEVIUM, NICKEL, MENDELEVIUM_NICKEL);
        addWelding1(r, NOBELIUM, NICKEL, NOBELIUM_NICKEL);
        addWelding1(r, LAWRENCIUM, NICKEL, LAWRENCIUM_NICKEL);
        addWelding1(r, ALUMINIUM_BRASS, NICKEL, ALUMINIUM_BRASS_NICKEL);
        addWelding1(r, CONSTANTAN, NICKEL, CONSTANTAN_NICKEL);
        addWelding1(r, ELECTRUM, NICKEL, ELECTRUM_NICKEL);
        addWelding1(r, INVAR, NICKEL, INVAR_NICKEL);
        addWelding1(r, NICKEL_SILVER, NICKEL, NICKEL_SILVER_NICKEL);
        addWelding1(r, ORICHALCUM, NICKEL, ORICHALCUM_NICKEL);
        addWelding1(r, RED_ALLOY, NICKEL, RED_ALLOY_NICKEL);
        addWelding1(r, TUNGSTEN_STEEL, NICKEL, TUNGSTEN_STEEL_NICKEL);
        addWelding1(r, STAINLESS_STEEL, NICKEL, STAINLESS_STEEL_NICKEL);
        addWelding1(r, LOCKALLOY, NICKEL, LOCKALLOY_NICKEL);
        addWelding1(r, MANGANIN, NICKEL, MANGANIN_NICKEL);
        addWelding1(r, GALINSTAN, NICKEL, GALINSTAN_NICKEL);
        addWelding1(r, CROWN_GOLD, NICKEL, CROWN_GOLD_NICKEL);
        addWelding1(r, WHITE_GOLD, NICKEL, WHITE_GOLD_NICKEL);
        addWelding1(r, SOLDER, NICKEL, SOLDER_NICKEL);
        addWelding1(r, MAGNOX, NICKEL, MAGNOX_NICKEL);
        addWelding1(r, PLATINUM_STERLING, NICKEL, PLATINUM_STERLING_NICKEL);
        addWelding1(r, TITANIUM_GOLD, NICKEL, TITANIUM_GOLD_NICKEL);
        addWelding1(r, PEWTER, NICKEL, PEWTER_NICKEL);
        addWelding1(r, CAST_IRON, NICKEL, CAST_IRON_NICKEL);
        addWelding1(r, MITHRIL, NICKEL, MITHRIL_NICKEL);
        addWelding1(r, ARDITE, NICKEL, ARDITE_NICKEL);
        addWelding1(r, MANYULLYN, NICKEL, MANYULLYN_NICKEL);
        addWelding1(r, ALCHEMICAL_BRASS, NICKEL, ALCHEMICAL_BRASS_NICKEL);
        addWelding1(r, THAUMIUM, NICKEL, THAUMIUM_NICKEL);
        addWelding1(r, VOIDMETAL, NICKEL, VOIDMETAL_NICKEL);
        addWelding1(r, SIGNALUM, NICKEL, SIGNALUM_NICKEL);
        addWelding1(r, LUMIUM, NICKEL, LUMIUM_NICKEL);
        addWelding1(r, ENDERIUM, NICKEL, ENDERIUM_NICKEL);
        addWelding1(r, ADAMANTIUM, NICKEL, ADAMANTIUM_NICKEL);
        
        // Vanilla TFC Metal + Nickel
        /*
        addWelding(r, BISMUTH, NICKEL, BISMUTH_NICKEL);
        addWelding(r, BISMUTH_BRONZE, NICKEL, BISMUTH_BRONZE_NICKEL);
        addWelding(r, BLACK_BRONZE, NICKEL, BLACK_BRONZE_NICKEL);
        addWelding(r, BRASS, NICKEL, BRASS_NICKEL);
        addWelding(r, BRONZE, NICKEL, BRONZE_NICKEL);
        addWelding(r, COPPER, NICKEL, COPPER_NICKEL);
        addWelding(r, GOLD, NICKEL, GOLD_NICKEL);
        addWelding(r, ROSE_GOLD, NICKEL, ROSE_GOLD_NICKEL);
        addWelding(r, SILVER, NICKEL, SILVER_NICKEL);
        addWelding(r, TIN, NICKEL, TIN_NICKEL);
        addWelding(r, ZINC, NICKEL, ZINC_NICKEL);
        addWelding(r, STERLING_SILVER, NICKEL, STERLING_SILVER_NICKEL);
        addWelding(r, WROUGHT_IRON, NICKEL, WROUGHT_IRON_NICKEL);
        addWelding(r, PIG_IRON, NICKEL, PIG_IRON_NICKEL);
        addWelding(r, STEEL, NICKEL, STEEL_NICKEL);
        addWelding(r, PLATINUM, NICKEL, PLATINUM_NICKEL);
        addWelding(r, BLACK_STEEL, NICKEL, BLACK_STEEL_NICKEL);
        addWelding(r, BLUE_STEEL, NICKEL, BLUE_STEEL_NICKEL);
        addWelding(r, RED_STEEL, NICKEL, RED_STEEL_NICKEL);
        */
        // TFC Elementia Metal + Nickel
        /*
        addWelding(r, LITHIUM, NICKEL, LITHIUM_NICKEL);
        addWelding(r, NATRIUM, NICKEL, NATRIUM_NICKEL);
        addWelding(r, KALIUM, NICKEL, KALIUM_NICKEL);
        addWelding(r, RUBIDIUM, NICKEL, RUBIDIUM_NICKEL);
        addWelding(r, CAESIUM, NICKEL, CAESIUM_NICKEL);
        addWelding(r, FRANCIUM, NICKEL, FRANCIUM_NICKEL);
        addWelding(r, BERYLLIUM, NICKEL, BERYLLIUM_NICKEL);
        addWelding(r, MAGNESIUM, NICKEL, MAGNESIUM_NICKEL);
        addWelding(r, CALCIUM, NICKEL, CALCIUM_NICKEL);
        addWelding(r, STRONTIUM, NICKEL, STRONTIUM_NICKEL);
        addWelding(r, BARIUM, NICKEL, BARIUM_NICKEL);
        addWelding(r, RADIUM, NICKEL, RADIUM_NICKEL);
        addWelding(r, SCANDIUM, NICKEL, SCANDIUM_NICKEL);
        addWelding(r, TITANIUM, NICKEL, TITANIUM_NICKEL);
        addWelding(r, VANADIUM, NICKEL, VANADIUM_NICKEL);
        addWelding(r, CHROMIUM, NICKEL, CHROMIUM_NICKEL);
        addWelding(r, MANGANESE, NICKEL, MANGANESE_NICKEL);
        addWelding(r, COBALT, NICKEL, COBALT_NICKEL);
        addWelding(r, YTTRIUM, NICKEL, YTTRIUM_NICKEL);
        addWelding(r, ZIRCONIUM, NICKEL, ZIRCONIUM_NICKEL);
        addWelding(r, NIOBIUM, NICKEL, NIOBIUM_NICKEL);
        addWelding(r, MOLYBDENUM, NICKEL, MOLYBDENUM_NICKEL);
        addWelding(r, TECHNETIUM, NICKEL, TECHNETIUM_NICKEL);
        addWelding(r, RUTHENIUM, NICKEL, RUTHENIUM_NICKEL);
        addWelding(r, RHODIUM, NICKEL, RHODIUM_NICKEL);
        addWelding(r, PALLADIUM, NICKEL, PALLADIUM_NICKEL);
        addWelding(r, HAFNIUM, NICKEL, HAFNIUM_NICKEL);
        addWelding(r, TANTALUM, NICKEL, TANTALUM_NICKEL);
        addWelding(r, TUNGSTEN, NICKEL, TUNGSTEN_NICKEL);
        addWelding(r, RHENIUM, NICKEL, RHENIUM_NICKEL);
        addWelding(r, OSMIUM, NICKEL, OSMIUM_NICKEL);
        addWelding(r, IRIDIUM, NICKEL, IRIDIUM_NICKEL);
        addWelding(r, RUTHERFORDIUM, NICKEL, RUTHERFORDIUM_NICKEL);
        addWelding(r, DUBNIUM, NICKEL, DUBNIUM_NICKEL);
        addWelding(r, SEABORGIUM, NICKEL, SEABORGIUM_NICKEL);
        addWelding(r, BOHRIUM, NICKEL, BOHRIUM_NICKEL);
        addWelding(r, HASSIUM, NICKEL, HASSIUM_NICKEL);
        addWelding(r, COPERNICIUM, NICKEL, COPERNICIUM_NICKEL);
        addWelding(r, ALUMINIUM, NICKEL, ALUMINIUM_NICKEL);
        addWelding(r, GALLIUM, NICKEL, GALLIUM_NICKEL);
        addWelding(r, CADMIUM, NICKEL, CADMIUM_NICKEL);
        addWelding(r, INDIUM, NICKEL, INDIUM_NICKEL);
        addWelding(r, MERCURY, NICKEL, MERCURY_NICKEL);
        addWelding(r, THALLIUM, NICKEL, THALLIUM_NICKEL);
        addWelding(r, POLONIUM, NICKEL, POLONIUM_NICKEL);
        addWelding(r, BORON, NICKEL, BORON_NICKEL);
        addWelding(r, SILICIUM, NICKEL, SILICIUM_NICKEL);
        addWelding(r, GERMANIUM, NICKEL, GERMANIUM_NICKEL);
        addWelding(r, ARSENIC, NICKEL, ARSENIC_NICKEL);
        addWelding(r, ANTIMONY, NICKEL, ANTIMONY_NICKEL);
        addWelding(r, TELLURIUM, NICKEL, TELLURIUM_NICKEL);
        addWelding(r, ASTATINE, NICKEL, ASTATINE_NICKEL);
        addWelding(r, LANTHANUM, NICKEL, LANTHANUM_NICKEL);
        addWelding(r, CERIUM, NICKEL, CERIUM_NICKEL);
        addWelding(r, PRASEODYMIUM, NICKEL, PRASEODYMIUM_NICKEL);
        addWelding(r, NEODYMIUM, NICKEL, NEODYMIUM_NICKEL);
        addWelding(r, PROMETHIUM, NICKEL, PROMETHIUM_NICKEL);
        addWelding(r, SAMARIUM, NICKEL, SAMARIUM_NICKEL);
        addWelding(r, EUROPIUM, NICKEL, EUROPIUM_NICKEL);
        addWelding(r, GADOLINIUM, NICKEL, GADOLINIUM_NICKEL);
        addWelding(r, TERBIUM, NICKEL, TERBIUM_NICKEL);
        addWelding(r, DYSPROSIUM, NICKEL, DYSPROSIUM_NICKEL);
        addWelding(r, HOLMIUM, NICKEL, HOLMIUM_NICKEL);
        addWelding(r, ERBIUM, NICKEL, ERBIUM_NICKEL);
        addWelding(r, THULIUM, NICKEL, THULIUM_NICKEL);
        addWelding(r, YTTERBIUM, NICKEL, YTTERBIUM_NICKEL);
        addWelding(r, LUTETIUM, NICKEL, LUTETIUM_NICKEL);
        addWelding(r, ACTINIUM, NICKEL, ACTINIUM_NICKEL);
        addWelding(r, THORIUM, NICKEL, THORIUM_NICKEL);
        addWelding(r, PROTACTINIUM, NICKEL, PROTACTINIUM_NICKEL);
        addWelding(r, URANIUM, NICKEL, URANIUM_NICKEL);
        addWelding(r, NEPTUNIUM, NICKEL, NEPTUNIUM_NICKEL);
        addWelding(r, PLUTONIUM, NICKEL, PLUTONIUM_NICKEL);
        addWelding(r, AMERICIUM, NICKEL, AMERICIUM_NICKEL);
        addWelding(r, CURIUM, NICKEL, CURIUM_NICKEL);
        addWelding(r, BERKELIUM, NICKEL, BERKELIUM_NICKEL);
        addWelding(r, CALIFORNIUM, NICKEL, CALIFORNIUM_NICKEL);
        addWelding(r, EINSTEINIUM, NICKEL, EINSTEINIUM_NICKEL);
        addWelding(r, FERMIUM, NICKEL, FERMIUM_NICKEL);
        addWelding(r, MENDELEVIUM, NICKEL, MENDELEVIUM_NICKEL);
        addWelding(r, NOBELIUM, NICKEL, NOBELIUM_NICKEL);
        addWelding(r, LAWRENCIUM, NICKEL, LAWRENCIUM_NICKEL);
        addWelding(r, ALUMINIUM_BRASS, NICKEL, ALUMINIUM_BRASS_NICKEL);
        addWelding(r, CONSTANTAN, NICKEL, CONSTANTAN_NICKEL);
        addWelding(r, ELECTRUM, NICKEL, ELECTRUM_NICKEL);
        addWelding(r, INVAR, NICKEL, INVAR_NICKEL);
        addWelding(r, NICKEL_SILVER, NICKEL, NICKEL_SILVER_NICKEL);
        addWelding(r, ORICHALCUM, NICKEL, ORICHALCUM_NICKEL);
        addWelding(r, RED_ALLOY, NICKEL, RED_ALLOY_NICKEL);
        addWelding(r, TUNGSTEN_STEEL, NICKEL, TUNGSTEN_STEEL_NICKEL);
        addWelding(r, STAINLESS_STEEL, NICKEL, STAINLESS_STEEL_NICKEL);
        addWelding(r, LOCKALLOY, NICKEL, LOCKALLOY_NICKEL);
        addWelding(r, MANGANIN, NICKEL, MANGANIN_NICKEL);
        addWelding(r, GALINSTAN, NICKEL, GALINSTAN_NICKEL);
        addWelding(r, CROWN_GOLD, NICKEL, CROWN_GOLD_NICKEL);
        addWelding(r, WHITE_GOLD, NICKEL, WHITE_GOLD_NICKEL);
        addWelding(r, SOLDER, NICKEL, SOLDER_NICKEL);
        addWelding(r, MAGNOX, NICKEL, MAGNOX_NICKEL);
        addWelding(r, PLATINUM_STERLING, NICKEL, PLATINUM_STERLING_NICKEL);
        addWelding(r, TITANIUM_GOLD, NICKEL, TITANIUM_GOLD_NICKEL);
        addWelding(r, PEWTER, NICKEL, PEWTER_NICKEL);
        addWelding(r, CAST_IRON, NICKEL, CAST_IRON_NICKEL);
        addWelding(r, MITHRIL, NICKEL, MITHRIL_NICKEL);
        addWelding(r, ARDITE, NICKEL, ARDITE_NICKEL);
        addWelding(r, MANYULLYN, NICKEL, MANYULLYN_NICKEL);
        addWelding(r, ALCHEMICAL_BRASS, NICKEL, ALCHEMICAL_BRASS_NICKEL);
        addWelding(r, THAUMIUM, NICKEL, THAUMIUM_NICKEL);
        addWelding(r, VOIDMETAL, NICKEL, VOIDMETAL_NICKEL);
        addWelding(r, SIGNALUM, NICKEL, SIGNALUM_NICKEL);
        addWelding(r, LUMIUM, NICKEL, LUMIUM_NICKEL);
        addWelding(r, ENDERIUM, NICKEL, ENDERIUM_NICKEL);
        addWelding(r, ADAMANTIUM, NICKEL, ADAMANTIUM_NICKEL);
        */
    }

    @SubscribeEvent
    public static void onRegisterCraftingRecipeEvent(RegistryEvent.Register<IRecipe> event)
    {
        IForgeRegistry<IRecipe> r = event.getRegistry();
        //Register all strips
        
        /*
        List<ItemStack> allChisels = new ArrayList<>();
        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (!metal.isToolMetal())
                continue;
            allChisels.add(new ItemStack(ItemMetal.get(metal, Metal.ItemType.CHISEL), 1, OreDictionary.WILDCARD_VALUE));
        }
        Ingredient chisel = Ingredient.fromStacks(allChisels.toArray(new ItemStack[0]));
        */
        
        /*
        ResourceLocation groupStrip = new ResourceLocation(MODID, "strip");
        ResourceLocation groupRod = new ResourceLocation(MODID, "rod");
        ResourceLocation groupBolt = new ResourceLocation(MODID, "bolt");
        ResourceLocation groupScrew = new ResourceLocation(MODID, "screw");
        ResourceLocation groupNail = new ResourceLocation(MODID, "nail");
        ResourceLocation groupRing = new ResourceLocation(MODID, "ring");
        ResourceLocation groupRingMesh = new ResourceLocation(MODID, "ring_mesh");
        */
        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            
		    /*
		     * Ring mesh
		     */
            Ingredient ingredient = Ingredient.fromStacks(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.RING)));
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.CHAIN));
		    if (!output.isEmpty())
		    {
		        NonNullList<Ingredient> list = NonNullList.create();
		        list.add(ingredient);
		        list.add(ingredient);
		        list.add(ingredient);
		        list.add(ingredient);
		        //noinspection ConstantConditions
		        r.register(new ShapedRecipes("chain", 2, 2, list, output).setRegistryName(MODID, metal.getRegistryName().getPath().toLowerCase() + "_chain"));
            }
		    
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            
		    /*
		     * Chains
		     */
            ingredient = Ingredient.fromStacks(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.CHAIN)));
            output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.RING_MESH));
		    if (!output.isEmpty())
		    {
		        NonNullList<Ingredient> list = NonNullList.create();
		        list.add(ingredient);
		        list.add(ingredient);
		        list.add(ingredient);
		        list.add(ingredient);
		        //noinspection ConstantConditions
		        r.register(new ShapedRecipes("ring_mesh", 2, 1, list, output).setRegistryName(MODID, metal.getRegistryName().getPath().toLowerCase() + "_ring_mesh"));
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void addAnvil1(IForgeRegistry<AnvilRecipe> registry, String recipeName, ItemMetalTFCE.ItemType inputType, ResourceLocation inputMetalRes, ItemStack output, Metal.Tier tier, @Nullable SmithingSkill.Type skillType, ForgeRule... rules)
    {
        // Helper method for adding METAL -> STACK
        Metal inputMetal = TFCRegistries.METALS.getValue(inputMetalRes);
        if (inputMetal != null && !output.isEmpty())
        {
            ItemStack input = new ItemStack(ItemMetalTFCE.get(inputMetal, inputType));
            if (!input.isEmpty() && !output.isEmpty())
            {
                registry.register(new AnvilRecipe(new ResourceLocation(MODID, recipeName), IIngredient.of(input), output, tier, skillType, rules));
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void addAnvil2(IForgeRegistry<AnvilRecipe> registry, String recipeName, Metal.ItemType inputType, ResourceLocation inputMetalRes, ItemStack output, Metal.Tier tier, @Nullable SmithingSkill.Type skillType, ForgeRule... rules)
    {
        // Helper method for adding METAL -> STACK
        Metal inputMetal = TFCRegistries.METALS.getValue(inputMetalRes);
        if (inputMetal != null && !output.isEmpty())
        {
            ItemStack input = new ItemStack(ItemMetal.get(inputMetal, inputType));
            if (!input.isEmpty() && !output.isEmpty())
            {
                registry.register(new AnvilRecipe(new ResourceLocation(MODID, recipeName), IIngredient.of(input), output, tier, skillType, rules));
            }
        }
    }

    // Ingots, sheets & plates
    /*
    for (Metal metal : TFCRegistries.METALS.getValuesCollection())
    {
        if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
            continue;
        IIngredient<ItemStack> ingredient;

        //Register all plates
        ingredient = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.INGOT)));
        Item item = ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.PLATE);
        if (item != null)
        {
            ItemStack output = new ItemStack(item);
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new AnvilRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_plate"), ingredient, output, metal.getTier(), null, ForgeRule.HIT_LAST, ForgeRule.SHRINK_SECOND_LAST, ForgeRule.HIT_THIRD_LAST));
            }
        }
    }
    */
    
    private static void addWelding(IForgeRegistry<WeldingRecipe> registry, ResourceLocation input1Loc, ResourceLocation input2Loc, ResourceLocation outputLoc)
    {
        Metal inputMetal1 = TFCRegistries.METALS.getValue(input1Loc);
        Metal inputMetal2 = TFCRegistries.METALS.getValue(input2Loc);
        Metal outputMetal = TFCRegistries.METALS.getValue(outputLoc);
        if (inputMetal1 != null && inputMetal2 != null && outputMetal != null)
        {
            // Create a recipe for each metal / item type combination
            ItemStack input1 = new ItemStack(ItemMetalTFCE.get(inputMetal1, ItemMetalTFCE.ItemType.PLATE));
            ItemStack input2 = new ItemStack(ItemMetalTFCE.get(inputMetal2, ItemMetalTFCE.ItemType.PLATE));
            ItemStack output = new ItemStack(ItemMetalTFCE.get(outputMetal, ItemMetalTFCE.ItemType.PLATE_DOUBLE));
            if (!input1.isEmpty() && !input2.isEmpty() && !output.isEmpty())
            {
                // Note: Welding recipes require one less than the tier of the metal
                //noinspection ConstantConditions
                registry.register(new WeldingRecipe(new ResourceLocation(MODID, ("double_plate_" + outputMetal.getRegistryName().getPath()).toLowerCase()), IIngredient.of(input1), IIngredient.of(input2), output, outputMetal.getTier().previous()));
            }
        }
    }
    
    private static void addWelding1(IForgeRegistry<WeldingRecipe> registry, ResourceLocation input1Loc, ResourceLocation input2Loc, ResourceLocation outputLoc)
    {
        Metal inputMetal1 = TFCRegistries.METALS.getValue(input1Loc);
        Metal inputMetal2 = TFCRegistries.METALS.getValue(input2Loc);
        Metal outputMetal = TFCRegistries.METALS.getValue(outputLoc);
        if (inputMetal1 != null && inputMetal2 != null && outputMetal != null)
        {
            // Create a recipe for each metal / item type combination
            ItemStack input1 = new ItemStack(ItemMetal.get(inputMetal1, INGOT));
            ItemStack input2 = new ItemStack(ItemMetal.get(inputMetal2, INGOT));
            ItemStack output = new ItemStack(ItemMetal.get(outputMetal, INGOT));
            if (!input1.isEmpty() && !input2.isEmpty() && !output.isEmpty())
            {
                // Note: Welding recipes require one less than the tier of the metal
                //noinspection ConstantConditions
                registry.register(new WeldingRecipe(new ResourceLocation(MODID, ("ingot_" + outputMetal.getRegistryName().getPath()).toLowerCase()), IIngredient.of(input1), IIngredient.of(input2), output, outputMetal.getTier().previous()));
            }
        }
    }
}
