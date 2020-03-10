package tfcelementia.types;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

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
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.inventory.ingredient.IngredientFluidItem;
import net.dries007.tfc.objects.items.ItemAnimalHide;
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
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.agriculture.Food;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.fuel.FuelManager;
import net.dries007.tfc.util.skills.SmithingSkill;

import tfcelementia.objects.GemTFCE;
import tfcelementia.objects.PowderTFCE;
import tfcelementia.objects.items.ItemPowderTFCE;
import tfcelementia.util.agriculture.FoodTFCE;
//import tfcelementia.util.OreDictionaryHelperTFCE;
import tfcelementia.util.ItemsRegistryHandler;
import tfcelementia.objects.fluids.FluidsTFCE;
import tfcelementia.objects.items.ItemFoodTFCE;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.Metal.ItemType.*;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_MISC;
import static net.dries007.tfc.objects.fluids.FluidsTFC.*;
import static net.dries007.tfc.types.DefaultMetals.*;
import static net.dries007.tfc.util.Helpers.getNull;
import static net.dries007.tfc.util.forge.ForgeRule.*;
import static net.dries007.tfc.util.skills.SmithingSkill.Type.*;

import static tfcelementia.objects.fluids.FluidsTFCE.*;
import static tfcelementia.types.MetalsTFCE.*;
import static tfcelementia.util.agriculture.FoodTFCE.Category.FRUIT;
import static tfcelementia.util.agriculture.FoodTFCE.Category.GRAIN;
import static tfcelementia.util.agriculture.FoodTFCE.Category.VEGETABLE;

/**
 * In 1.14+, every line in here needs to be a json file. Yay, but also ugh.
 */
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
public final class RecipesTFCE
{
    @SubscribeEvent
    public static void onRegisterBarrelRecipeEvent(RegistryEvent.Register<BarrelRecipe> event)
    {
        event.getRegistry().registerAll(
        		
        	// Misc
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemsRegistryHandler.AGAVE), null, new ItemStack(ItemsRegistryHandler.SISAL_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sisal_fiber"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemsRegistryHandler.FLAX), null, new ItemStack(ItemsRegistryHandler.FLAX_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("flax_fiber"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemsRegistryHandler.HEMP), null, new ItemStack(ItemsTFC.JUTE_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("hemp_fiber"),
            
            // Teas
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 200), IIngredient.of(ItemsRegistryHandler.WHITE_TEA, 2), new FluidStack(FluidsTFCE.WHITE_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("white_tea"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 200), IIngredient.of(ItemsRegistryHandler.GREEN_TEA, 2), new FluidStack(FluidsTFCE.GREEN_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("green_tea"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 200), IIngredient.of(ItemsRegistryHandler.BLACK_TEA, 2), new FluidStack(FluidsTFCE.BLACK_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("black_tea"),

            // Dyes
            new BarrelRecipe(IIngredient.of(250, FluidsTFC.BEER.get(), FluidsTFC.CIDER.get(), FluidsTFC.RUM.get(), FluidsTFC.SAKE.get(), FluidsTFC.VODKA.get(), FluidsTFC.WHISKEY.get(), FluidsTFC.CORN_WHISKEY.get(), FluidsTFC.RYE_WHISKEY.get()), IIngredient.of(ItemsRegistryHandler.AGAVE, 2), null, new ItemStack(Items.DYE,2, EnumDyeColor.GREEN.getDyeDamage()), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("green_dye"),
            new BarrelRecipe(IIngredient.of(250, FluidsTFC.BEER.get(), FluidsTFC.CIDER.get(), FluidsTFC.RUM.get(), FluidsTFC.SAKE.get(), FluidsTFC.VODKA.get(), FluidsTFC.WHISKEY.get(), FluidsTFC.CORN_WHISKEY.get(), FluidsTFC.RYE_WHISKEY.get()), IIngredient.of(ItemsRegistryHandler.MADDER, 2), null, new ItemStack(Items.DYE,1, EnumDyeColor.RED.getDyeDamage()), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("red_dye"),
            new BarrelRecipe(IIngredient.of(250, FluidsTFC.BEER.get(), FluidsTFC.CIDER.get(), FluidsTFC.RUM.get(), FluidsTFC.SAKE.get(), FluidsTFC.VODKA.get(), FluidsTFC.WHISKEY.get(), FluidsTFC.CORN_WHISKEY.get(), FluidsTFC.RYE_WHISKEY.get()), IIngredient.of(ItemsRegistryHandler.WELD, 2), null, new ItemStack(Items.DYE,11, EnumDyeColor.YELLOW.getDyeDamage()), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("yellow_dye"),
            new BarrelRecipe(IIngredient.of(250, FluidsTFC.BEER.get(), FluidsTFC.CIDER.get(), FluidsTFC.RUM.get(), FluidsTFC.SAKE.get(), FluidsTFC.VODKA.get(), FluidsTFC.WHISKEY.get(), FluidsTFC.CORN_WHISKEY.get(), FluidsTFC.RYE_WHISKEY.get()), IIngredient.of(ItemsRegistryHandler.WOAD, 2), null, new ItemStack(Items.DYE,4, EnumDyeColor.BLUE.getDyeDamage()), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye"),
            
            // Alcohol - Classic created 1000mb with 4oz, which would be 8 items per full barrel at 5 oz/item. Instead we now require 20 items, so conversion is 2 oz/item here
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.JUNIPER_BERRY)), new FluidStack(FluidsTFCE.GIN.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("gin"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of(ItemsRegistryHandler.AGAVE), new FluidStack(FluidsTFCE.TEQUILA.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("tequila"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.RED_GRAPES)), new FluidStack(FluidsTFCE.RED_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("red_wine"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.GREEN_GRAPES)), new FluidStack(FluidsTFCE.WHITE_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("white_wine"),

            new BarrelRecipeTemperature(IIngredient.of(DISTILLED_WATER.get(), 1), 50).setRegistryName("distilled_water_cooling")
        );
    }

    @SubscribeEvent
    public static void onRegisterHeatRecipeEvent(RegistryEvent.Register<HeatRecipe> event)
    {
        IForgeRegistry<HeatRecipe> r = event.getRegistry();

        // Standard / Simple recipes
        r.registerAll(
        	
            // Fired Mud Pottery - doesn't burn up
            new HeatRecipeSimple(IIngredient.of(ItemsRegistryHandler.UNFIRED_MUD_BRICK), new ItemStack(ItemsRegistryHandler.FIRED_MUD_BRICK), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_mud_brick")
            
        );
    }

    @SubscribeEvent
    public static void onRegisterLoomRecipeEvent(RegistryEvent.Register<LoomRecipe> event)
    {
        IForgeRegistry<LoomRecipe> r = event.getRegistry();
        
        r.registerAll(
            new LoomRecipe(new ResourceLocation(MOD_ID, "cotton_cloth"), IIngredient.of(ItemsRegistryHandler.COTTON_YARN, 12), new ItemStack(ItemsRegistryHandler.COTTON_CLOTH), 12, new ResourceLocation(MOD_ID, "textures/blocks/devices/loom/product/burlap.png")),
            new LoomRecipe(new ResourceLocation(MOD_ID, "linen_cloth"), IIngredient.of(ItemsRegistryHandler.LINEN_STRING, 12), new ItemStack(ItemsRegistryHandler.LINEN_CLOTH), 12, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png")),
            new LoomRecipe(new ResourceLocation(MOD_ID, "sisal_cloth"), IIngredient.of(ItemsRegistryHandler.SISAL_FIBER, 12), new ItemStack(ItemsTFC.BURLAP_CLOTH), 12, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png"))
        );
    }

    @SubscribeEvent
    public static void onRegisterQuernRecipeEvent(RegistryEvent.Register<QuernRecipe> event)
    {
        IForgeRegistry<QuernRecipe> r = event.getRegistry();

        r.registerAll(
            //Powders
            new QuernRecipe(IIngredient.of("gemCalcium"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.CALCIUM), 4)).setRegistryName("calcium_powder"),
            new QuernRecipe(IIngredient.of("gemFluorite"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.FLUORITE), 4)).setRegistryName("fluorite_powder"),
            new QuernRecipe(IIngredient.of("gemPhorphorite"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.PHOSPHORITE), 4)).setRegistryName("phosphorite_powder"),
            new QuernRecipe(IIngredient.of("gemSelenide"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.SELENIDE), 4)).setRegistryName("selenide_powder"),
            new QuernRecipe(IIngredient.of("gemIodate"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.IODATE), 4)).setRegistryName("iodate_powder")
        );
    }
}