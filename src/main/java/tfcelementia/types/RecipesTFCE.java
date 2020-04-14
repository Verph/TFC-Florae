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
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;
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
import tfcelementia.objects.GemTFCE;
import tfcelementia.objects.PowderGemTFCE;
import tfcelementia.objects.PowderTFCE;
import tfcelementia.objects.fluids.FluidsTFCE;
import tfcelementia.objects.items.ItemGemTFCE;
import tfcelementia.objects.items.ItemPowderGemTFCE;
import tfcelementia.objects.items.ItemPowderTFCE;
import tfcelementia.objects.items.ItemTFCE;
import tfcelementia.objects.items.ItemsTFCE;
import tfcelementia.objects.items.food.ItemFoodTFCE;
import tfcelementia.objects.items.metal.ItemMetalTFCE;
import tfcelementia.types.PlantsTFCE;
import tfcelementia.util.agriculture.FoodTFCE;
import tfcelementia.util.OreDictionaryHelper;

//import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.Metal.ItemType.*;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_MISC;
import static net.dries007.tfc.objects.fluids.FluidsTFC.*;
import static net.dries007.tfc.types.DefaultMetals.*;
import static net.dries007.tfc.util.Helpers.getNull;
import static net.dries007.tfc.util.forge.ForgeRule.*;
import static net.dries007.tfc.util.skills.SmithingSkill.Type.*;

import static tfcelementia.TFCElementia.MODID;
import static tfcelementia.objects.fluids.FluidsTFCE.*;
import static tfcelementia.types.MetalsTFCE.*;
import static tfcelementia.util.agriculture.FoodTFCE.Category.FRUIT;
import static tfcelementia.util.agriculture.FoodTFCE.Category.GRAIN;
import static tfcelementia.util.agriculture.FoodTFCE.Category.VEGETABLE;

/**
 * In 1.14+, every line in here needs to be a json file. Yay, but also ugh.
 */
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public final class RecipesTFCE
{
    @SubscribeEvent
    public static void onRegisterBarrelRecipeEvent(RegistryEvent.Register<BarrelRecipe> event)
    {
        event.getRegistry().registerAll(

        	// Fibers
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemsTFCE.AGAVE), null, new ItemStack(ItemsTFCE.SISAL_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sisal_fiber"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemsTFCE.FLAX), null, new ItemStack(ItemsTFCE.FLAX_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("flax_fiber"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemsTFCE.HEMP), null, new ItemStack(ItemsTFCE.HEMP_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("hemp_fiber"),

            // Fluids from paste
            // Olive
            new BarrelRecipe(IIngredient.of(OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCE.SISAL_NET), new FluidStack(OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_SISAL_NET), 0).setRegistryName("olive_oil_sisal"),
            new BarrelRecipe(IIngredient.of(OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCE.COTTON_NET), new FluidStack(OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_COTTON_NET), 0).setRegistryName("olive_oil_cotton"),
            new BarrelRecipe(IIngredient.of(OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCE.LINEN_NET), new FluidStack(OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_LINEN_NET), 0).setRegistryName("olive_oil_linen"),
            new BarrelRecipe(IIngredient.of(OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCE.HEMP_NET), new FluidStack(OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_HEMP_NET), 0).setRegistryName("olive_oil_hemp"),
            
            // Soybean
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 125), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.SOYBEAN_PASTE)), new FluidStack(FluidsTFCE.SOYBEAN_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("soybean_Water"),
            
            new BarrelRecipe(IIngredient.of(SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(SOY_MILK.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("soy_milk_jute"),
            new BarrelRecipe(IIngredient.of(SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCE.SISAL_NET), new FluidStack(SOY_MILK.get(), 25), new ItemStack(ItemsTFCE.DIRTY_SISAL_NET), 0).setRegistryName("soy_milk_sisal"),
            new BarrelRecipe(IIngredient.of(SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCE.COTTON_NET), new FluidStack(SOY_MILK.get(), 25), new ItemStack(ItemsTFCE.DIRTY_COTTON_NET), 0).setRegistryName("soy_milk_cotton"),
            new BarrelRecipe(IIngredient.of(SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCE.LINEN_NET), new FluidStack(SOY_MILK.get(), 25), new ItemStack(ItemsTFCE.DIRTY_LINEN_NET), 0).setRegistryName("soy_milk_linen"),
            new BarrelRecipe(IIngredient.of(SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCE.HEMP_NET), new FluidStack(SOY_MILK.get(), 25), new ItemStack(ItemsTFCE.DIRTY_HEMP_NET), 0).setRegistryName("soy_milk_hemp"),
            
            new BarrelRecipeFluidMixing(IIngredient.of(SOY_MILK.get(), 9), new IngredientFluidItem(VINEGAR.get(), 1), new FluidStack(MILK_VINEGAR.get(), 10), 0).setRegistryName("soy_milk_vinegar"),

            // Linseed
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 125), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.LINSEED_PASTE)), new FluidStack(LINSEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("linseed_water"),
            
            new BarrelRecipe(IIngredient.of(LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(LINSEED_OIL.get(),25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("linseed_oil_jute"),
            new BarrelRecipe(IIngredient.of(LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.SISAL_NET), new FluidStack(LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_SISAL_NET), 0).setRegistryName("linseed_oil_sisal"),
            new BarrelRecipe(IIngredient.of(LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.COTTON_NET), new FluidStack(LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_COTTON_NET), 0).setRegistryName("linseed_oil_cotton"),
            new BarrelRecipe(IIngredient.of(LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.LINEN_NET), new FluidStack(LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_LINEN_NET), 0).setRegistryName("linseed_oil_linen"),
            new BarrelRecipe(IIngredient.of(LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.HEMP_NET), new FluidStack(LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_HEMP_NET), 0).setRegistryName("linseed_oil_hemp"),

            // Rape Seed
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 125), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.RAPE_SEED_PASTE)), new FluidStack(RAPE_SEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("rape_seed_water"),
            
            new BarrelRecipe(IIngredient.of(RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(RAPE_SEED_OIL.get(),25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("rape_seed_oil_jute"),
            new BarrelRecipe(IIngredient.of(RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.SISAL_NET), new FluidStack(RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_SISAL_NET), 0).setRegistryName("rape_seed_oil_sisal"),
            new BarrelRecipe(IIngredient.of(RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.COTTON_NET), new FluidStack(RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_COTTON_NET), 0).setRegistryName("rape_seed_oil_cotton"),
            new BarrelRecipe(IIngredient.of(RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.LINEN_NET), new FluidStack(RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_LINEN_NET), 0).setRegistryName("rape_seed_oil_linen"),
            new BarrelRecipe(IIngredient.of(RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.HEMP_NET), new FluidStack(RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_HEMP_NET), 0).setRegistryName("rape_seed_oil_hemp"),

            // Sunflower Seed
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 125), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.SUNFLOWER_SEED_PASTE)), new FluidStack(SUNFLOWER_SEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("sunflower_seed_water"),
            
            new BarrelRecipe(IIngredient.of(SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(SUNFLOWER_SEED_OIL.get(),25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("sunflower_seed_oil_jute"),
            new BarrelRecipe(IIngredient.of(SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.SISAL_NET), new FluidStack(SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_SISAL_NET), 0).setRegistryName("sunflower_seed_oil_sisal"),
            new BarrelRecipe(IIngredient.of(SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.COTTON_NET), new FluidStack(SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_COTTON_NET), 0).setRegistryName("sunflower_seed_oil_cotton"),
            new BarrelRecipe(IIngredient.of(SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.LINEN_NET), new FluidStack(SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_LINEN_NET), 0).setRegistryName("sunflower_seed_oil_linen"),
            new BarrelRecipe(IIngredient.of(SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.HEMP_NET), new FluidStack(SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_HEMP_NET), 0).setRegistryName("sunflower_seed_oil_hemp"),

            // Opium Poppy Seed
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 125), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.OPIUM_POPPY_SEED_PASTE)), new FluidStack(OPIUM_POPPY_SEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("opium_poppy_seed_water"),
            
            new BarrelRecipe(IIngredient.of(OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(OPIUM_POPPY_SEED_OIL.get(),25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("opium_poppy_seed_oil_jute"),
            new BarrelRecipe(IIngredient.of(OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.SISAL_NET), new FluidStack(OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_SISAL_NET), 0).setRegistryName("opium_poppy_seed_oil_sisal"),
            new BarrelRecipe(IIngredient.of(OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.COTTON_NET), new FluidStack(OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_COTTON_NET), 0).setRegistryName("opium_poppy_seed_oil_cotton"),
            new BarrelRecipe(IIngredient.of(OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.LINEN_NET), new FluidStack(OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_LINEN_NET), 0).setRegistryName("opium_poppy_seed_oil_linen"),
            new BarrelRecipe(IIngredient.of(OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCE.HEMP_NET), new FluidStack(OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCE.DIRTY_HEMP_NET), 0).setRegistryName("opium_poppy_seed_oil_hemp"),

            // Sugar Beet Water
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 125), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.MASHED_SUGAR_BEET)), new FluidStack(SUGAR_BEET_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_beet_water"),
            
            new BarrelRecipe(IIngredient.of(SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(SUGAR_WATER.get(),25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("sugar_water_jute"),
            new BarrelRecipe(IIngredient.of(SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCE.SISAL_NET), new FluidStack(SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCE.DIRTY_SISAL_NET), 0).setRegistryName("sugar_water_sisal"),
            new BarrelRecipe(IIngredient.of(SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCE.COTTON_NET), new FluidStack(SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCE.DIRTY_COTTON_NET), 0).setRegistryName("sugar_water_cotton"),
            new BarrelRecipe(IIngredient.of(SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCE.LINEN_NET), new FluidStack(SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCE.DIRTY_LINEN_NET), 0).setRegistryName("sugar_water_linen"),
            new BarrelRecipe(IIngredient.of(SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCE.HEMP_NET), new FluidStack(SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCE.DIRTY_HEMP_NET), 0).setRegistryName("sugar_water_hemp"),

            /*
            new BarrelRecipe(IIngredient.of(SUGAR_WATER.get(), 125), null, null, new ItemStack(Items.SUGAR), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_from_sugar_water"),
            */
            
			// Honey Water
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of("itemHoney"), new FluidStack(FluidsTFCE.HONEY_WATER.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("honey_water_from_item_honey"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of("itemHoneycomb"), new FluidStack(FluidsTFCE.HONEY_WATER.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("honey_water_from_item_honeycomb"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of("materialHoneycomb"), new FluidStack(FluidsTFCE.HONEY_WATER.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("honey_water_from_material_honeycomb"),

            // Dirty Nets
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 125), IIngredient.of(ItemsTFCE.DIRTY_SISAL_NET), new FluidStack(HOT_WATER.get(), 100), new ItemStack(ItemsTFCE.SISAL_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_sisal"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 125), IIngredient.of(ItemsTFCE.DIRTY_COTTON_NET), new FluidStack(HOT_WATER.get(), 100), new ItemStack(ItemsTFCE.COTTON_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_cotton"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 125), IIngredient.of(ItemsTFCE.DIRTY_LINEN_NET), new FluidStack(HOT_WATER.get(), 100), new ItemStack(ItemsTFCE.LINEN_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_linen"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 125), IIngredient.of(ItemsTFCE.DIRTY_HEMP_NET), new FluidStack(HOT_WATER.get(), 100), new ItemStack(ItemsTFCE.HEMP_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_hemp"),

            // Teas
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 200), IIngredient.of(ItemsTFCE.DRIED_WHITE_TEA, 2), new FluidStack(FluidsTFCE.WHITE_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("white_tea"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 200), IIngredient.of(ItemsTFCE.DRIED_GREEN_TEA, 2), new FluidStack(FluidsTFCE.GREEN_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("green_tea"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 200), IIngredient.of(ItemsTFCE.DRIED_BLACK_TEA, 2), new FluidStack(FluidsTFCE.BLACK_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("black_tea"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 200), IIngredient.of(ItemsTFCE.DRIED_CHAMOMILE_HEAD, 2), new FluidStack(FluidsTFCE.CHAMOMILE_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("chamomile_tea"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 200), IIngredient.of(ItemsTFCE.DRIED_DANDELION_HEAD, 2), new FluidStack(FluidsTFCE.DANDELION_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("dandelion_tea"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 200), IIngredient.of(ItemsTFCE.DRIED_LABRADOR_TEA_HEAD, 2), new FluidStack(FluidsTFCE.LABRADOR_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("labrador_tea"),

            // Dyes
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of(ItemsTFCE.AGAVE), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("green_dye_agave"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of(ItemsTFCE.INDIGO), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_indigo"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of(ItemsTFCE.RAPE_FLOWER), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.YELLOW).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("yellow_dye_rape_flower"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of(ItemsTFCE.MADDER), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.RED).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("red_dye_madder"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of(ItemsTFCE.WELD), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.YELLOW).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("yellow_dye_weld"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of(ItemsTFCE.WOAD), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_woad"),
            
            // Wort
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 500), IIngredient.of(ItemsTFCE.HOPS), new FluidStack(FluidsTFCE.WORT.get(), 500), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("wort"),

            // Fermented Alcohol
            new BarrelRecipe(IIngredient.of(JUICE_AGAVE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.AGAVE_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("agave_wine"),
            new BarrelRecipe(IIngredient.of(JUICE_BANANA.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BANANA_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("banana_wine"),
            new BarrelRecipe(IIngredient.of(JUICE_CHERRY.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.CHERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cherry_wine"),
            new BarrelRecipe(IIngredient.of(JUICE_GREEN_GRAPE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.WHITE_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("white_wine"),
            new BarrelRecipe(IIngredient.of(JUICE_JUNIPER.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.JUNIPER_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("juniper_wine"),
            new BarrelRecipe(IIngredient.of(JUICE_LEMON.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.LEMON_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("lemon_wine"),
            new BarrelRecipe(IIngredient.of(HONEY_WATER.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.MEAD.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("mead"),
            new BarrelRecipe(IIngredient.of(JUICE_ORANGE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.ORANGE_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("orange_wine"),
            new BarrelRecipe(IIngredient.of(JUICE_PAPAYA.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.PAPAYA_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("papaya_wine"),
            new BarrelRecipe(IIngredient.of(JUICE_PEACH.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.PEACH_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("peach_wine"),
            new BarrelRecipe(IIngredient.of(JUICE_PEAR.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.PEAR_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("pear_wine"),
            new BarrelRecipe(IIngredient.of(JUICE_PLUM.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.PLUM_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("plum_wine"),
            new BarrelRecipe(IIngredient.of(JUICE_PURPLE_GRAPE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.RED_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("red_wine"),
            new BarrelRecipe(IIngredient.of(RICE_WATER.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFC.SAKE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("sake_rice_water"),

            // Berry Wine
            new BarrelRecipe(IIngredient.of(JUICE_BLACKBERRY.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_blackberry"),
            new BarrelRecipe(IIngredient.of(JUICE_BLUEBERRY.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_blueberry"),
            new BarrelRecipe(IIngredient.of(JUICE_BUNCH_BERRY.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_bunch_berry"),
            new BarrelRecipe(IIngredient.of(JUICE_CLOUD_BERRY.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_cloud_berry"),
            new BarrelRecipe(IIngredient.of(JUICE_CRANBERRY.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_cranberry"),
            new BarrelRecipe(IIngredient.of(JUICE_ELDERBERRY.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_elderberry"),
            new BarrelRecipe(IIngredient.of(JUICE_GOOSEBERRY.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_gooseberry"),
            new BarrelRecipe(IIngredient.of(JUICE_RASPBERRY.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_raspberry"),
            new BarrelRecipe(IIngredient.of(JUICE_SNOW_BERRY.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_snow_berry"),
            new BarrelRecipe(IIngredient.of(JUICE_STRAWBERRY.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_strawberry"),
            new BarrelRecipe(IIngredient.of(JUICE_WINTERGREEN_BERRY.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_wintergreen_berry"),

            // Distilled Alcohol
            new BarrelRecipe(IIngredient.of(AGAVE_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.TEQUILA.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("tequila"),
            new BarrelRecipe(IIngredient.of(BANANA_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BANANA_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("banana_brandy"),
            new BarrelRecipe(IIngredient.of(BERRY_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BERRY_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_brandy"),
            new BarrelRecipe(IIngredient.of(CIDER.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.CALVADOS.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("calvados"),
            new BarrelRecipe(IIngredient.of(CHERRY_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.CHERRY_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cherry_brandy"),
            new BarrelRecipe(IIngredient.of(JUNIPER_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.GIN.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("gin"),
            new BarrelRecipe(IIngredient.of(LEMON_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.LEMON_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("lemon_brandy"),
            new BarrelRecipe(IIngredient.of(ORANGE_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.ORANGE_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("orange_brandy"),
            new BarrelRecipe(IIngredient.of(PAPAYA_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.PAPAYA_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("papaya_brandy"),
            new BarrelRecipe(IIngredient.of(PEACH_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.PEACH_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("peach_brandy"),
            new BarrelRecipe(IIngredient.of(PEAR_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.PEAR_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("pear_brandy"),
            new BarrelRecipe(IIngredient.of(PLUM_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.PLUM_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("plum_brandy"),
            new BarrelRecipe(IIngredient.of(RED_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("brandy"),
            new BarrelRecipe(IIngredient.of(SAKE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.SHOCHU.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("shochu"),
            new BarrelRecipe(IIngredient.of(WHITE_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCE.COGNAC.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cognac"),
            
            // Malted Grain
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemFoodTFC.get(Food.BARLEY_GRAIN)), null, new ItemStack(ItemsTFCE.MALT_BARLEY), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_barley"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemFoodTFC.get(Food.MAIZE)), null, new ItemStack(ItemsTFCE.MALT_BARLEY), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_corn"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemFoodTFC.get(Food.RYE_GRAIN)), null, new ItemStack(ItemsTFCE.MALT_BARLEY), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_rye"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemFoodTFC.get(Food.RICE_GRAIN)), null, new ItemStack(ItemsTFCE.MALT_BARLEY), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_rice"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemFoodTFC.get(Food.WHEAT_GRAIN)), null, new ItemStack(ItemsTFCE.MALT_BARLEY), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_wheat"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.AMARANTH_GRAIN)), null, new ItemStack(ItemsTFCE.MALT_BARLEY), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_amaranth"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.BUCKWHEAT_GRAIN)), null, new ItemStack(ItemsTFCE.MALT_BARLEY), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_buckwheat"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.FONIO_GRAIN)), null, new ItemStack(ItemsTFCE.MALT_BARLEY), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_fonio"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.MILLET_GRAIN)), null, new ItemStack(ItemsTFCE.MALT_BARLEY), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_millet"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.QUINOA_GRAIN)), null, new ItemStack(ItemsTFCE.MALT_BARLEY), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_quinoa"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.SPELT_GRAIN)), null, new ItemStack(ItemsTFCE.MALT_BARLEY), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_spelt"),
            
            // Beer
            new BarrelRecipe(IIngredient.of(WORT.get(), 500), IIngredient.of(ItemsTFCE.MALT_BARLEY), new FluidStack(FluidsTFCE.BEER_BARLEY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_barley"),
            new BarrelRecipe(IIngredient.of(WORT.get(), 500), IIngredient.of(ItemsTFCE.MALT_CORN), new FluidStack(FluidsTFCE.BEER_CORN.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_corn"),
            new BarrelRecipe(IIngredient.of(WORT.get(), 500), IIngredient.of(ItemsTFCE.MALT_RYE), new FluidStack(FluidsTFCE.BEER_RYE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_rye"),
            new BarrelRecipe(IIngredient.of(WORT.get(), 500), IIngredient.of(ItemsTFCE.MALT_WHEAT), new FluidStack(FluidsTFCE.BEER_WHEAT.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_wheat"),
            new BarrelRecipe(IIngredient.of(WORT.get(), 500), IIngredient.of(ItemsTFCE.MALT_AMARANTH), new FluidStack(FluidsTFCE.BEER_AMARANTH.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_amaranth"),
            new BarrelRecipe(IIngredient.of(WORT.get(), 500), IIngredient.of(ItemsTFCE.MALT_BUCKWHEAT), new FluidStack(FluidsTFCE.BEER_BUCKWHEAT.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_buckwheat"),
            new BarrelRecipe(IIngredient.of(WORT.get(), 500), IIngredient.of(ItemsTFCE.MALT_FONIO), new FluidStack(FluidsTFCE.BEER_FONIO.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_fonio"),
            new BarrelRecipe(IIngredient.of(WORT.get(), 500), IIngredient.of(ItemsTFCE.MALT_MILLET), new FluidStack(FluidsTFCE.BEER_MILLET.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_millet"),
            new BarrelRecipe(IIngredient.of(WORT.get(), 500), IIngredient.of(ItemsTFCE.MALT_QUINOA), new FluidStack(FluidsTFCE.BEER_QUINOA.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_quinoa"),
            new BarrelRecipe(IIngredient.of(WORT.get(), 500), IIngredient.of(ItemsTFCE.MALT_SPELT), new FluidStack(FluidsTFCE.BEER_SPELT.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_spelt"),
            
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of(ItemFoodTFC.get(Food.RICE)), new FluidStack(FluidsTFCE.RICE_WATER.get(), 500), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("rice_water"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.WILD_RICE)), new FluidStack(FluidsTFCE.RICE_WATER.get(), 500), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("wild_rice_water"),

            new BarrelRecipeTemperature(IIngredient.of(DISTILLED_WATER.get(), 1), 50).setRegistryName("distilled_water_cooling")
        );
    }

    @SubscribeEvent
    public static void onRegisterHeatRecipeEvent(RegistryEvent.Register<HeatRecipe> event)
    {
        IForgeRegistry<HeatRecipe> r = event.getRegistry();

        // Standard / Simple recipes
        r.registerAll(

        	// Ash
            new HeatRecipeSimple(IIngredient.of("twigWood"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.ASH), 3), 425, 850f).setRegistryName("burnt_twig"),
            new HeatRecipeSimple(IIngredient.of("branchWood"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.ASH), 3), 425, 850f).setRegistryName("burnt_branch"),
            new HeatRecipeSimple(IIngredient.of("stickWood"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.ASH), 1), 425, 850f).setRegistryName("burnt_stick"),
                
            // Bread
            new HeatRecipeSimple(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.AMARANTH_DOUGH)), new ItemStack(ItemFoodTFCE.get(FoodTFCE.AMARANTH_BREAD)), 200, 480).setRegistryName("amaranth_bread"),
            new HeatRecipeSimple(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.BUCKWHEAT_DOUGH)), new ItemStack(ItemFoodTFCE.get(FoodTFCE.BUCKWHEAT_BREAD)), 200, 480).setRegistryName("buckwheat_bread"),
            new HeatRecipeSimple(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.FONIO_DOUGH)), new ItemStack(ItemFoodTFCE.get(FoodTFCE.FONIO_BREAD)), 200, 480).setRegistryName("fonio_bread"),
            new HeatRecipeSimple(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.MILLET_DOUGH)), new ItemStack(ItemFoodTFCE.get(FoodTFCE.MILLET_BREAD)), 200, 480).setRegistryName("millet_bread"),
            new HeatRecipeSimple(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.QUINOA_DOUGH)), new ItemStack(ItemFoodTFCE.get(FoodTFCE.QUINOA_BREAD)), 200, 480).setRegistryName("quinoa_bread"),
            new HeatRecipeSimple(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.SPELT_DOUGH)), new ItemStack(ItemFoodTFCE.get(FoodTFCE.SPELT_BREAD)), 200, 480).setRegistryName("spelt_bread"),
            new HeatRecipeSimple(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.WILD_RICE_DOUGH)), new ItemStack(ItemFoodTFCE.get(FoodTFCE.WILD_RICE_BREAD)), 200, 480).setRegistryName("wild_rice_bread"),
            
            // "Drying" items
            new HeatRecipeSimple(IIngredient.of(ItemsTFCE.CANNABIS_BUD), new ItemStack(ItemsTFCE.DRIED_CANNABIS_BUD), 200, 480).setRegistryName("dried_cannabis_bud"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCE.CANNABIS_LEAF), new ItemStack(ItemsTFCE.DRIED_CANNABIS_LEAF), 200, 480).setRegistryName("dried_cannabis_leaf"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCE.COCA_LEAF), new ItemStack(ItemsTFCE.DRIED_COCA_LEAF), 200, 480).setRegistryName("dried_coca_leaf"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCE.OPIUM_POPPY_BULB), new ItemStack(ItemsTFCE.DRIED_OPIUM_POPPY_BULB), 200, 480).setRegistryName("dried_opium_poppy_bulb"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCE.SUNFLOWER_HEAD), new ItemStack(ItemsTFCE.DRIED_SUNFLOWER_HEAD), 200, 480).setRegistryName("dried_sunflower_head"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCE.TOBACCO_LEAF), new ItemStack(ItemsTFCE.DRIED_TOBACCO_LEAF), 200, 480).setRegistryName("dried_tobacco_leaf"),
            
            new HeatRecipeSimple(IIngredient.of(ItemsTFCE.CHAMOMILE_HEAD), new ItemStack(ItemsTFCE.DRIED_CHAMOMILE_HEAD), 200, 480).setRegistryName("dried_chamomile_head"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCE.DANDELION_HEAD), new ItemStack(ItemsTFCE.DRIED_DANDELION_HEAD), 200, 480).setRegistryName("dried_dandelion_head"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCE.LABRADOR_TEA_HEAD), new ItemStack(ItemsTFCE.DRIED_LABRADOR_TEA_HEAD), 200, 480).setRegistryName("dried_labrador_tea_head"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCE.BLACK_TEA), new ItemStack(ItemsTFCE.DRIED_BLACK_TEA), 200, 480).setRegistryName("dried_black_tea"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCE.GREEN_TEA), new ItemStack(ItemsTFCE.DRIED_GREEN_TEA), 200, 480).setRegistryName("dried_green_tea"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCE.WHITE_TEA), new ItemStack(ItemsTFCE.DRIED_WHITE_TEA), 200, 480).setRegistryName("dried_white_tea"),

            // Bread
            HeatRecipe.destroy(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.AMARANTH_BREAD)), 480).setRegistryName("burned_amaranth_bread"),
            HeatRecipe.destroy(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.BUCKWHEAT_BREAD)), 480).setRegistryName("burned_buckwheat_bread"),
            HeatRecipe.destroy(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.FONIO_BREAD)), 480).setRegistryName("burned_fonio_bread"),
            HeatRecipe.destroy(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.MILLET_BREAD)), 480).setRegistryName("burned_millet_bread"),
            HeatRecipe.destroy(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.QUINOA_BREAD)), 480).setRegistryName("burned_quinoa_bread"),
            HeatRecipe.destroy(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.SPELT_BREAD)), 480).setRegistryName("burned_spelt_bread"),
            HeatRecipe.destroy(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.WILD_RICE_BREAD)), 480).setRegistryName("burned_wild_rice_bread"),

            // Burnt "dried" items
            HeatRecipe.destroy(IIngredient.of(ItemsTFCE.DRIED_CANNABIS_BUD), 480).setRegistryName("burned_cannabis_bud"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCE.DRIED_CANNABIS_LEAF), 480).setRegistryName("burned_cannabis_leaf"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCE.DRIED_COCA_LEAF), 480).setRegistryName("burned_coca_leaf"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCE.DRIED_OPIUM_POPPY_BULB), 480).setRegistryName("burned_opium_poppy_bulb"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCE.DRIED_SUNFLOWER_HEAD), 480).setRegistryName("burned_sunflower_head"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCE.DRIED_TOBACCO_LEAF), 480).setRegistryName("burned_tobacco_leaf"),
            
            HeatRecipe.destroy(IIngredient.of(ItemsTFCE.DRIED_CHAMOMILE_HEAD), 480).setRegistryName("burned_chamomile_head"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCE.DRIED_DANDELION_HEAD), 480).setRegistryName("burned_dandelion_head"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCE.DRIED_LABRADOR_TEA_HEAD), 480).setRegistryName("burned_labrador_tea_head"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCE.DRIED_BLACK_TEA), 480).setRegistryName("burned_black_tea"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCE.DRIED_GREEN_TEA), 480).setRegistryName("burned_green_tea"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCE.DRIED_WHITE_TEA), 480).setRegistryName("burned_white_tea")
        );
    }
    
    @SubscribeEvent
    public static void onRegisterLoomRecipeEvent(RegistryEvent.Register<LoomRecipe> event)
    {
        IForgeRegistry<LoomRecipe> r = event.getRegistry();
        
        r.registerAll(
            new LoomRecipe(new ResourceLocation(MODID, "cotton_cloth"), IIngredient.of(ItemsTFCE.COTTON_YARN, 12), new ItemStack(ItemsTFCE.COTTON_CLOTH), 12, new ResourceLocation(MODID, "textures/blocks/devices/loom/product/cotton.png")),
            new LoomRecipe(new ResourceLocation(MODID, "hemp_cloth"), IIngredient.of(ItemsTFCE.HEMP_STRING, 12), new ItemStack(ItemsTFCE.HEMP_CLOTH), 12, new ResourceLocation(MODID, "textures/blocks/devices/loom/product/hemp.png")),
            new LoomRecipe(new ResourceLocation(MODID, "linen_cloth"), IIngredient.of(ItemsTFCE.LINEN_STRING, 12), new ItemStack(ItemsTFCE.LINEN_CLOTH), 12, new ResourceLocation(MODID, "textures/blocks/devices/loom/product/linen.png")),
            new LoomRecipe(new ResourceLocation(MODID, "sisal_cloth"), IIngredient.of(ItemsTFCE.SISAL_STRING, 12), new ItemStack(ItemsTFCE.SISAL_CLOTH), 12, new ResourceLocation(MODID, "textures/blocks/devices/loom/product/sisal.png")),

            new LoomRecipe(new ResourceLocation(MODID, "wool_block_cotton"), IIngredient.of(ItemsTFCE.COTTON_CLOTH, 4), new ItemStack(Blocks.WOOL), 4, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png"))
        );
    }

    @SubscribeEvent
    public static void onRegisterQuernRecipeEvent(RegistryEvent.Register<QuernRecipe> event)
    {
        IForgeRegistry<QuernRecipe> r = event.getRegistry();

        r.registerAll(
            // Mineral Powders
            new QuernRecipe(IIngredient.of("gemCalcium"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.CALCIUM), 4)).setRegistryName("calcium_powder_from_calcium"),
            new QuernRecipe(IIngredient.of("gemFluorite"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.FLUORITE), 4)).setRegistryName("fluorite_powder_from_fluorite"),
            new QuernRecipe(IIngredient.of("gemPhosphorite"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.PHOSPHORITE), 4)).setRegistryName("phosphorite_powder_from_phosphorite"),
            new QuernRecipe(IIngredient.of("gemSelenide"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.SELENIDE), 4)).setRegistryName("selenide_powder_from_selenide"),
            new QuernRecipe(IIngredient.of("gemIodate"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.IODATE), 4)).setRegistryName("iodate_powder_from_iodate"),
            
            // Gem Powders
            // TFC Gems
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.AGATE, Gem.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.AGATE), 1)).setRegistryName("agate_powder_from_chipped_agate"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.AGATE, Gem.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.AGATE), 2)).setRegistryName("agate_powder_from_flawed_agate"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.AGATE, Gem.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.AGATE), 4)).setRegistryName("agate_powder_from_normal_agate"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.AGATE, Gem.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.AGATE), 8)).setRegistryName("agate_powder_from_flawless_agate"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.AGATE, Gem.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.AGATE), 16)).setRegistryName("agate_powder_from_exquisite_agate"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.AMETHYST, Gem.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.AMETHYST), 1)).setRegistryName("amethyst_powder_from_chipped_amethyst"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.AMETHYST, Gem.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.AMETHYST), 2)).setRegistryName("amethyst_powder_from_flawed_amethyst"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.AMETHYST, Gem.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.AMETHYST), 4)).setRegistryName("amethyst_powder_from_normal_amethyst"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.AMETHYST, Gem.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.AMETHYST), 8)).setRegistryName("amethyst_powder_from_flawless_amethyst"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.AMETHYST, Gem.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.AMETHYST), 16)).setRegistryName("beryl_powder_from_exquisite_amethyst"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.BERYL, Gem.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.BERYL), 1)).setRegistryName("beryl_powder_from_chipped_beryl"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.BERYL, Gem.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.BERYL), 2)).setRegistryName("beryl_powder_from_flawed_beryl"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.BERYL, Gem.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.BERYL), 4)).setRegistryName("beryl_powder_from_normal_beryl"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.BERYL, Gem.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.BERYL), 8)).setRegistryName("beryl_powder_from_flawless_beryl"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.BERYL, Gem.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.BERYL), 16)).setRegistryName("beryl_powder_from_exquisite_beryl"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.DIAMOND, Gem.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.DIAMOND), 1)).setRegistryName("diamond_powder_from_chipped_diamond"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.DIAMOND, Gem.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.DIAMOND), 2)).setRegistryName("diamond_powder_from_flawed_diamond"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.DIAMOND, Gem.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.DIAMOND), 4)).setRegistryName("diamond_powder_from_normal_diamond"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.DIAMOND, Gem.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.DIAMOND), 8)).setRegistryName("diamond_powder_from_flawless_diamond"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.DIAMOND, Gem.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.DIAMOND), 16)).setRegistryName("diamond_powder_from_exquisite_diamond"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.EMERALD, Gem.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.EMERALD), 1)).setRegistryName("emerald_powder_from_chipped_emerald"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.EMERALD, Gem.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.EMERALD), 2)).setRegistryName("emerald_powder_from_flawed_emerald"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.EMERALD, Gem.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.EMERALD), 4)).setRegistryName("emerald_powder_from_normal_emerald"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.EMERALD, Gem.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.EMERALD), 8)).setRegistryName("emerald_powder_from_flawless_emerald"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.EMERALD, Gem.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.EMERALD), 16)).setRegistryName("emerald_powder_from_exquisite_emerald"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.GARNET, Gem.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.GARNET), 1)).setRegistryName("garnet_powder_from_chipped_garnet"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.GARNET, Gem.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.GARNET), 2)).setRegistryName("garnet_powder_from_flawed_garnet"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.GARNET, Gem.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.GARNET), 4)).setRegistryName("garnet_powder_from_normal_garnet"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.GARNET, Gem.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.GARNET), 8)).setRegistryName("garnet_powder_from_flawless_garnet"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.GARNET, Gem.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.GARNET), 16)).setRegistryName("garnet_powder_from_exquisite_garnet"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.JADE, Gem.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.JADE), 1)).setRegistryName("jade_powder_from_chipped_jade"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.JADE, Gem.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.JADE), 2)).setRegistryName("jade_powder_from_flawed_jade"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.JADE, Gem.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.JADE), 4)).setRegistryName("jade_powder_from_normal_jade"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.JADE, Gem.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.JADE), 8)).setRegistryName("jade_powder_from_flawless_jade"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.JADE, Gem.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.JADE), 16)).setRegistryName("jade_powder_from_exquisite_jade"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.JASPER, Gem.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.JASPER), 1)).setRegistryName("jasper_powder_from_chipped_jasper"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.JASPER, Gem.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.JASPER), 2)).setRegistryName("jasper_powder_from_flawed_jasper"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.JASPER, Gem.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.JASPER), 4)).setRegistryName("jasper_powder_from_normal_jasper"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.JASPER, Gem.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.JASPER), 8)).setRegistryName("jasper_powder_from_flawless_jasper"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.JASPER, Gem.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.JASPER), 16)).setRegistryName("jasper_powder_from_exquisite_jasper"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.OPAL, Gem.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.OPAL), 1)).setRegistryName("opal_powder_from_chipped_opal"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.OPAL, Gem.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.OPAL), 2)).setRegistryName("opal_powder_from_flawed_opal"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.OPAL, Gem.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.OPAL), 4)).setRegistryName("opal_powder_from_normal_opal"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.OPAL, Gem.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.OPAL), 8)).setRegistryName("opal_powder_from_flawless_opal"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.OPAL, Gem.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.OPAL), 16)).setRegistryName("opal_powder_from_exquisite_opal"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.RUBY, Gem.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.RUBY), 1)).setRegistryName("ruby_powder_from_chipped_ruby"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.RUBY, Gem.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.RUBY), 2)).setRegistryName("ruby_powder_from_flawed_ruby"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.RUBY, Gem.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.RUBY), 4)).setRegistryName("ruby_powder_from_normal_ruby"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.RUBY, Gem.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.RUBY), 8)).setRegistryName("ruby_powder_from_flawless_ruby"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.RUBY, Gem.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.RUBY), 16)).setRegistryName("ruby_powder_from_exquisite_ruby"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.SAPPHIRE, Gem.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SAPPHIRE), 1)).setRegistryName("sapphire_powder_from_chipped_sapphire"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.SAPPHIRE, Gem.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SAPPHIRE), 2)).setRegistryName("sapphire_powder_from_flawed_sapphire"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.SAPPHIRE, Gem.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SAPPHIRE), 4)).setRegistryName("sapphire_powder_from_normal_sapphire"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.SAPPHIRE, Gem.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SAPPHIRE), 8)).setRegistryName("sapphire_powder_from_flawless_sapphire"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.SAPPHIRE, Gem.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SAPPHIRE), 16)).setRegistryName("sapphire_powder_from_exquisite_sapphire"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.TOPAZ, Gem.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TOPAZ), 1)).setRegistryName("topaz_powder_from_chipped_topaz"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.TOPAZ, Gem.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TOPAZ), 2)).setRegistryName("topaz_powder_from_flawed_topaz"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.TOPAZ, Gem.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TOPAZ), 4)).setRegistryName("topaz_powder_from_normal_topaz"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.TOPAZ, Gem.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TOPAZ), 8)).setRegistryName("topaz_powder_from_flawless_topaz"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.TOPAZ, Gem.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TOPAZ), 16)).setRegistryName("topaz_powder_from_exquisite_topaz"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.TOURMALINE, Gem.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TOURMALINE), 1)).setRegistryName("tourmaline_powder_from_chipped_tourmaline"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.TOURMALINE, Gem.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TOURMALINE), 2)).setRegistryName("tourmaline_powder_from_flawed_tourmaline"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.TOURMALINE, Gem.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TOURMALINE), 4)).setRegistryName("tourmaline_powder_from_normal_tourmaline"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.TOURMALINE, Gem.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TOURMALINE), 8)).setRegistryName("tourmaline_powder_from_flawless_tourmaline"),
            new QuernRecipe(IIngredient.of(ItemGem.get(Gem.TOURMALINE, Gem.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TOURMALINE), 16)).setRegistryName("tourmaline_powder_from_exquisite_tourmaline"),
            
            // TFCE Gems
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.APATITE, GemTFCE.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.APATITE), 1)).setRegistryName("apatite_powder_from_chipped_apatite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.APATITE, GemTFCE.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.APATITE), 2)).setRegistryName("apatite_powder_from_flawed_apatite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.APATITE, GemTFCE.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.APATITE), 4)).setRegistryName("apatite_powder_from_normal_apatite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.APATITE, GemTFCE.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.APATITE), 8)).setRegistryName("apatite_powder_from_flawless_apatite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.APATITE, GemTFCE.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.APATITE), 16)).setRegistryName("apatite_powder_from_exquisite_apatite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.BROMARGYRITE, GemTFCE.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.BROMARGYRITE), 1)).setRegistryName("bromargyrite_powder_from_chipped_bromargyrite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.BROMARGYRITE, GemTFCE.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.BROMARGYRITE), 2)).setRegistryName("bromargyrite_powder_from_flawed_bromargyrite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.BROMARGYRITE, GemTFCE.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.BROMARGYRITE), 4)).setRegistryName("bromargyrite_powder_from_normal_bromargyrite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.BROMARGYRITE, GemTFCE.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.BROMARGYRITE), 8)).setRegistryName("bromargyrite_powder_from_flawless_bromargyrite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.BROMARGYRITE, GemTFCE.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.BROMARGYRITE), 16)).setRegistryName("bromargyrite_powder_from_exquisite_bromargyrite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.CITRINE, GemTFCE.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.CITRINE), 1)).setRegistryName("citrine_powder_from_chipped_citrine"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.CITRINE, GemTFCE.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.CITRINE), 2)).setRegistryName("citrine_powder_from_flawed_citrine"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.CITRINE, GemTFCE.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.CITRINE), 4)).setRegistryName("citrine_powder_from_normal_citrine"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.CITRINE, GemTFCE.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.CITRINE), 8)).setRegistryName("citrine_powder_from_flawless_citrine"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.CITRINE, GemTFCE.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.CITRINE), 16)).setRegistryName("citrine_powder_from_exquisite_citrine"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.HELIODOR, GemTFCE.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.HELIODOR), 1)).setRegistryName("heliodor_powder_from_chipped_heliodor"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.HELIODOR, GemTFCE.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.HELIODOR), 2)).setRegistryName("heliodor_powder_from_flawed_heliodor"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.HELIODOR, GemTFCE.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.HELIODOR), 4)).setRegistryName("heliodor_powder_from_normal_heliodor"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.HELIODOR, GemTFCE.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.HELIODOR), 8)).setRegistryName("heliodor_powder_from_flawless_heliodor"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.HELIODOR, GemTFCE.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.HELIODOR), 16)).setRegistryName("heliodor_powder_from_exquisite_heliodor"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.IODARGYRITE, GemTFCE.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.IODARGYRITE), 1)).setRegistryName("iodargyrite_powder_from_chipped_iodargyrite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.IODARGYRITE, GemTFCE.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.IODARGYRITE), 2)).setRegistryName("iodargyrite_powder_from_flawed_iodargyrite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.IODARGYRITE, GemTFCE.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.IODARGYRITE), 4)).setRegistryName("iodargyrite_powder_from_normal_iodargyrite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.IODARGYRITE, GemTFCE.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.IODARGYRITE), 8)).setRegistryName("iodargyrite_powder_from_flawless_iodargyrite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.IODARGYRITE, GemTFCE.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.IODARGYRITE), 16)).setRegistryName("iodargyrite_powder_from_exquisite_iodargyrite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.KYANITE, GemTFCE.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.KYANITE), 1)).setRegistryName("kyanite_powder_from_chipped_kyanite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.KYANITE, GemTFCE.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.KYANITE), 2)).setRegistryName("kyanite_powder_from_flawed_kyanite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.KYANITE, GemTFCE.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.KYANITE), 4)).setRegistryName("kyanite_powder_from_normal_kyanite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.KYANITE, GemTFCE.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.KYANITE), 8)).setRegistryName("kyanite_powder_from_flawless_kyanite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.KYANITE, GemTFCE.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.KYANITE), 16)).setRegistryName("kyanite_powder_from_exquisite_kyanite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.MOLDAVITE, GemTFCE.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.MOLDAVITE), 1)).setRegistryName("moldavite_powder_from_chipped_moldavite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.MOLDAVITE, GemTFCE.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.MOLDAVITE), 2)).setRegistryName("moldavite_powder_from_flawed_moldavite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.MOLDAVITE, GemTFCE.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.MOLDAVITE), 4)).setRegistryName("moldavite_powder_from_normal_moldavite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.MOLDAVITE, GemTFCE.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.MOLDAVITE), 8)).setRegistryName("moldavite_powder_from_flawless_moldavite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.MOLDAVITE, GemTFCE.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.MOLDAVITE), 16)).setRegistryName("moldavite_powder_from_exquisite_moldavite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.MOONSTONE, GemTFCE.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.MOONSTONE), 1)).setRegistryName("moonstone_powder_from_chipped_moonstone"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.MOONSTONE, GemTFCE.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.MOONSTONE), 2)).setRegistryName("moonstone_powder_from_flawed_moonstone"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.MOONSTONE, GemTFCE.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.MOONSTONE), 4)).setRegistryName("moonstone_powder_from_normal_moonstone"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.MOONSTONE, GemTFCE.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.MOONSTONE), 8)).setRegistryName("moonstone_powder_from_flawless_moonstone"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.MOONSTONE, GemTFCE.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.MOONSTONE), 16)).setRegistryName("moonstone_powder_from_exquisite_moonstone"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.PYROMORPHITE, GemTFCE.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.PYROMORPHITE), 1)).setRegistryName("pyromorphite_powder_from_chipped_pyromorphite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.PYROMORPHITE, GemTFCE.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.PYROMORPHITE), 2)).setRegistryName("pyromorphite_powder_from_flawed_pyromorphite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.PYROMORPHITE, GemTFCE.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.PYROMORPHITE), 4)).setRegistryName("pyromorphite_powder_from_normal_pyromorphite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.PYROMORPHITE, GemTFCE.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.PYROMORPHITE), 8)).setRegistryName("pyromorphite_powder_from_flawless_pyromorphite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.PYROMORPHITE, GemTFCE.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.PYROMORPHITE), 16)).setRegistryName("pyromorphite_powder_from_exquisite_pyromorphite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.QUARTZ, GemTFCE.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.QUARTZ), 1)).setRegistryName("quartz_powder_from_chipped_quartz"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.QUARTZ, GemTFCE.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.QUARTZ), 2)).setRegistryName("quartz_powder_from_flawed_quartz"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.QUARTZ, GemTFCE.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.QUARTZ), 4)).setRegistryName("quartz_powder_from_normal_quartz"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.QUARTZ, GemTFCE.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.QUARTZ), 8)).setRegistryName("quartz_powder_from_flawless_quartz"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.QUARTZ, GemTFCE.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.QUARTZ), 16)).setRegistryName("quartz_powder_from_exquisite_quartz"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.SPINEL, GemTFCE.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SPINEL), 1)).setRegistryName("spinel_powder_from_chipped_spinel"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.SPINEL, GemTFCE.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SPINEL), 2)).setRegistryName("spinel_powder_from_flawed_spinel"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.SPINEL, GemTFCE.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SPINEL), 4)).setRegistryName("spinel_powder_from_normal_spinel"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.SPINEL, GemTFCE.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SPINEL), 8)).setRegistryName("spinel_powder_from_flawless_spinel"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.SPINEL, GemTFCE.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SPINEL), 16)).setRegistryName("spinel_powder_from_exquisite_spinel"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.SUNSTONE, GemTFCE.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SUNSTONE), 1)).setRegistryName("sunstone_powder_from_chipped_sunstone"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.SUNSTONE, GemTFCE.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SUNSTONE), 2)).setRegistryName("sunstone_powder_from_flawed_sunstone"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.SUNSTONE, GemTFCE.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SUNSTONE), 4)).setRegistryName("sunstone_powder_from_normal_sunstone"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.SUNSTONE, GemTFCE.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SUNSTONE), 8)).setRegistryName("sunstone_powder_from_flawless_sunstone"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.SUNSTONE, GemTFCE.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SUNSTONE), 16)).setRegistryName("sunstone_powder_from_exquisite_sunstone"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.TANZANITE, GemTFCE.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TANZANITE), 1)).setRegistryName("tanzanite_powder_from_chipped_tanzanite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.TANZANITE, GemTFCE.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TANZANITE), 2)).setRegistryName("tanzanite_powder_from_flawed_tanzanite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.TANZANITE, GemTFCE.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TANZANITE), 4)).setRegistryName("tanzanite_powder_from_normal_tanzanite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.TANZANITE, GemTFCE.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TANZANITE), 8)).setRegistryName("tanzanite_powder_from_flawless_tanzanite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.TANZANITE, GemTFCE.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TANZANITE), 16)).setRegistryName("tanzanite_powder_from_exquisite_tanzanite"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.ZIRCON, GemTFCE.Grade.CHIPPED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.ZIRCON), 1)).setRegistryName("zircon_powder_from_chipped_zircon"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.ZIRCON, GemTFCE.Grade.FLAWED, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.ZIRCON), 2)).setRegistryName("zircon_powder_from_flawed_zircon"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.ZIRCON, GemTFCE.Grade.NORMAL, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.ZIRCON), 4)).setRegistryName("zircon_powder_from_normal_zircon"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.ZIRCON, GemTFCE.Grade.FLAWLESS, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.ZIRCON), 8)).setRegistryName("zircon_powder_from_flawless_zircon"),
            new QuernRecipe(IIngredient.of(ItemGemTFCE.get(GemTFCE.ZIRCON, GemTFCE.Grade.EXQUISITE, 1)), new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.ZIRCON), 16)).setRegistryName("zircon_powder_from_exquisite_zircon"),
            
            // Paste
            new QuernRecipe(IIngredient.of(ItemFoodTFC.get(Food.SOYBEAN)), new ItemStack(ItemFoodTFCE.get(FoodTFCE.SOYBEAN_PASTE), 1)).setRegistryName("soybean_paste_from_soybean"),
            new QuernRecipe(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.LINSEED)), new ItemStack(ItemFoodTFCE.get(FoodTFCE.LINSEED_PASTE), 1)).setRegistryName("linseed_paste_from_linseed"), //To be iterated further upon when the press gets added
            new QuernRecipe(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.RAPE_SEED)), new ItemStack(ItemFoodTFCE.get(FoodTFCE.RAPE_SEED_PASTE), 1)).setRegistryName("rape_seed_paste_from_rape_seed"), //To be iterated further upon when the press gets added
            new QuernRecipe(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.SUNFLOWER_SEED)), new ItemStack(ItemFoodTFCE.get(FoodTFCE.SUNFLOWER_SEED_PASTE), 1)).setRegistryName("sunflower_seed_paste_from_sunflower_seed"), //To be iterated further upon when the press gets added
            new QuernRecipe(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.OPIUM_POPPY_SEED)), new ItemStack(ItemFoodTFCE.get(FoodTFCE.OPIUM_POPPY_SEED_PASTE), 1)).setRegistryName("opium_poppy_seed_paste_from_opium_poppy_bulb"), //To be iterated further upon when the press gets added
            new QuernRecipe(IIngredient.of(ItemFoodTFCE.get(FoodTFCE.SUGAR_BEET)), new ItemStack(ItemFoodTFCE.get(FoodTFCE.MASHED_SUGAR_BEET), 1)).setRegistryName("mashed_sugar_beet_from_sugar_beet"),

            //Dye from plants
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.CHAMOMILE))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_chamomile"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.HYDRANGEA))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_hydrangea"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.LILY_OF_THE_VALLEY))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_lily_of_the_valley"),
            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.WHITE_HYDRANGEA))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_white_hydrangea"),
            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.WHITE_PEONY))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_white_peony"),
            
            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.ORANGE_PEONY))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("crushed_orange_peony"),

            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.SUNFLOWER))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_sunflower"),
            new QuernRecipe(IIngredient.of(ItemsTFCE.SUNFLOWER_HEAD), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_sunflower_head"),

            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.LILAC))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_lilac"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.PEONY))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_peony"),
            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.PINK_HYDRANGEA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_pink_hydrangea"),
            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.PINK_PEONY))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_pink_peony"),

            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.PURPLE_HYDRANGEA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage())).setRegistryName("crushed_purple_hydrangea"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.LAVANDULA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage())).setRegistryName("crushed_lavandula"),
            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.PURPLE_PEONY))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage())).setRegistryName("crushed_purple_peony"),

            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.BLUE_HYDRANGEA))), new ItemStack(ItemsTFC.DYE_BLUE, 2)).setRegistryName("crushed_blue_hydrangea"),
            
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.CATTAIL))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("crushed_cattail")

            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.RED_HYDRANGEA))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("crushed_red_hydrangea"),
            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.RED_PEONY))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("crushed_red_peony")
            
        );
    }
}