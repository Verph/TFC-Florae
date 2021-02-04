package tfcflorae.types;

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
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

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
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.blocks.BlockDecorativeStone;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.inventory.ingredient.IngredientFluidItem;
import net.dries007.tfc.objects.inventory.ingredient.IngredientItemFood;
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
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.dries007.tfc.objects.items.rock.ItemRockToolHead;
import net.dries007.tfc.objects.recipes.ShapelessDamageRecipe;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.types.DefaultTrees;
import net.dries007.tfc.util.agriculture.Food;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.fuel.FuelManager;
import net.dries007.tfc.util.skills.SmithingSkill;
import net.dries007.tfc.util.skills.SmithingSkill.Type;

import tfcflorae.TFCFlorae;
import tfcflorae.api.knapping.KnappingTypes;
import tfcflorae.api.recipes.CrackingRecipe;
import tfcflorae.api.recipes.NutRecipe;
import tfcflorae.objects.PowderTFCF;
import tfcflorae.objects.blocks.BlockSurfaceRock;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.blocktype.*;
import tfcflorae.objects.fluids.FluidsTFCF;
import tfcflorae.objects.items.ItemPowderTFCF;
import tfcflorae.objects.items.ItemTFCF;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.objects.items.ceramics.ItemKaoliniteMold;
import tfcflorae.objects.items.ceramics.ItemUnfiredKaoliniteMold;
import tfcflorae.objects.items.rock.ItemFiredMudBrick;
import tfcflorae.objects.items.rock.ItemMud;
import tfcflorae.objects.items.rock.ItemUnfiredMudBrick;
import tfcflorae.types.PlantsTFCF;
import tfcflorae.types.BlockTypesTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;
import tfcflorae.util.OreDictionaryHelper;

import static net.dries007.tfc.api.types.Metal.ItemType.*;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_MISC;
import static net.dries007.tfc.objects.fluids.FluidsTFC.*;
import static net.dries007.tfc.types.DefaultMetals.*;
import static net.dries007.tfc.util.Helpers.getNull;
import static net.dries007.tfc.util.forge.ForgeRule.*;
import static net.dries007.tfc.util.skills.SmithingSkill.Type.*;

import static tfcflorae.TFCFlorae.MODID;

/**
 * In 1.14+, every line in here needs to be a json file. Yay, but also ugh.
 */
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public final class RecipesTFCF
{
    @SuppressWarnings("rawtypes")
    @SubscribeEvent
    public static void onRegisterBarrelRecipeEvent(RegistryEvent.Register<BarrelRecipe> event)
    {
        IForgeRegistry<BarrelRecipe> r = event.getRegistry();

        // Remove recipes
        IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) TFCRegistries.BARREL;
        String[] regNames = {"sugar", "beer", "sake"};
        for (String name : regNames)
        {
            BarrelRecipe recipe = TFCRegistries.BARREL.getValue(new ResourceLocation("tfc", name));
            if (recipe != null)
            {
                modRegistry.remove(recipe.getRegistryName());
                TFCFlorae.logger.info("Removed barrel recipe tfc:{}", name);
            }
        }

        for(Rock rock : TFCRegistries.ROCKS.getValuesCollection())
        {
            event.getRegistry().registerAll(
                new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of(BlockRockVariant.get(rock, Rock.Type.RAW)), new FluidStack(FluidsTFC.FRESH_WATER.get(), 50), new ItemStack(BlockRockVariantTFCF.get(rock, BlockTypesTFCF.RockTFCF.MOSSY_RAW), 1), 8* ICalendar.TICKS_IN_HOUR).setRegistryName(TFCFlorae.MODID, "mossy_raw_"+rock.getRegistryName().getPath())
            );
        }

        r.registerAll(

            // Sugar
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 600), IIngredient.of("sugarcane", 5), null, new ItemStack(Items.SUGAR), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_from_sugar_cane"),

            // Papyrus Fibers
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 600), IIngredient.of("pulpPapyrus", 3), null, new ItemStack(ItemsTFCF.PAPYRUS_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("papyrus_fiber_from_papyrus"),

        	// Fiber Processing
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("cropAgave"), null, new ItemStack(ItemsTFCF.SISAL_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sisal_fiber"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("cropFlax"), null, new ItemStack(ItemsTFCF.FLAX_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("flax_fiber"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("cropHemp"), null, new ItemStack(ItemsTFCF.HEMP_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("hemp_fiber"),
            
            // Fluidproduction from paste

            // Olive
            new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("olive_oil_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("olive_oil_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("olive_oil_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("olive_oil_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("olive_oil_hemp"),

            // Soybean
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteSoybean"), new FluidStack(FluidsTFCF.SOYBEAN_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("soybean_water"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("soy_milk_jute"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("soy_milk_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("soy_milk_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("soy_milk_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("soy_milk_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("soy_milk_hemp"),

            new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFCF.SOY_MILK.get(), 9), new IngredientFluidItem(FluidsTFC.VINEGAR.get(), 1), new FluidStack(FluidsTFC.MILK_VINEGAR.get(), 10), 0).setRegistryName("soy_milk_vinegar"),

            // Linseed
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteLinseed"), new FluidStack(FluidsTFCF.LINSEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("linseed_water"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("linseed_oil_jute"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("linseed_oil_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("linseed_oil_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("linseed_oil_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("linseed_oil_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("linseed_oil_hemp"),

            // Rape Seed
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteRapeSeed"), new FluidStack(FluidsTFCF.RAPE_SEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("rape_seed_water"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("rape_seed_oil_jute"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("rape_seed_oil_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("rape_seed_oil_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("rape_seed_oil_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("rape_seed_oil_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("rape_seed_oil_hemp"),

            // Sunflower Seed
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteSunflowerSeed"), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("sunflower_seed_water"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("sunflower_seed_oil_jute"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("sunflower_seed_oil_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("sunflower_seed_oil_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("sunflower_seed_oil_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("sunflower_seed_oil_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("sunflower_seed_oil_hemp"),

            // Opium Poppy Seed
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteOpiumPoppySeed"), new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("opium_poppy_seed_water"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("opium_poppy_seed_oil_jute"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("opium_poppy_seed_oil_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("opium_poppy_seed_oil_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("opium_poppy_seed_oil_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("opium_poppy_seed_oil_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("opium_poppy_seed_oil_hemp"),

            // Sugar Beet Water
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("mashedSugarBeet"), new FluidStack(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_beet_water"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("sugar_water_jute"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("sugar_water_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("sugar_water_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("sugar_water_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("sugar_water_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("sugar_water_hemp"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_WATER.get(), 125), null, null, new ItemStack(Items.SUGAR), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_from_sugar_water"),

			// Honey Water
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("itemHoney"), new FluidStack(FluidsTFCF.HONEY_WATER.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("honey_water_from_item_honey"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("itemHoneycomb"), new FluidStack(FluidsTFCF.HONEY_WATER.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("honey_water_from_item_honeycomb"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("materialHoneycomb"), new FluidStack(FluidsTFCF.HONEY_WATER.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("honey_water_from_material_honeycomb"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.HONEY_WATER.get(), 125), null, null, new ItemStack(Items.SUGAR), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_from_honey_water"),

            // Dirty Nets
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_SISAL_NET), null, new ItemStack(ItemsTFCF.SISAL_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_SILK_NET), null, new ItemStack(ItemsTFCF.SILK_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_COTTON_NET), null, new ItemStack(ItemsTFCF.COTTON_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_LINEN_NET), null, new ItemStack(ItemsTFCF.LINEN_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_HEMP_NET), null, new ItemStack(ItemsTFCF.HEMP_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_hemp"),

            
            // Dyes
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropAgave"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("green_dye_agave"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropIndigo"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_indigo"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropMadder"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.RED).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("red_dye_madder"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropWeld"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.YELLOW).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("yellow_dye_weld"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropWoad"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_woad"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("boneCharred"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("black_dye_charred_bones"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("dustBlackPearl"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("black_dye_black_pearl_powder"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("dustPearl"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("pink_dye_pearl_powder"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("dustLogwood"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("purple_dye_logwood_powder"),
            
            // Teas
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of("driedWhiteTea", 2), new FluidStack(FluidsTFCF.WHITE_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("white_tea"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of("driedGreenTea", 2), new FluidStack(FluidsTFCF.GREEN_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("green_tea"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of("driedBlackTea", 2), new FluidStack(FluidsTFCF.BLACK_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("black_tea"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of("driedChamomile", 2), new FluidStack(FluidsTFCF.CHAMOMILE_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("chamomile_tea"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of("driedDandelion", 2), new FluidStack(FluidsTFCF.DANDELION_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("dandelion_tea"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of("driedLabradorTea", 2), new FluidStack(FluidsTFCF.LABRADOR_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("labrador_tea"),

            // Coffee
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of("roastedCoffee", 2), new FluidStack(FluidsTFCF.COFFEE.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("coffee"),

            // Firma Cola
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_WATER.get(), 250), IIngredient.of("blendFirmaCola"), new FluidStack(FluidsTFCF.FIRMA_COLA.get(), 1000), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("firma_cola"),

            // Wort
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 500), IIngredient.of("hops"), new FluidStack(FluidsTFCF.WORT.get(), 500), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("wort"),
            
            // Fermented Alcohol
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_AGAVE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.AGAVE_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("agave_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_BANANA.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BANANA_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("banana_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_CHERRY.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.CHERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cherry_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_GREEN_GRAPE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.WHITE_WINE.get(), 500), new ItemStack(ItemsTFCF.POMACE), 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("white_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_JUNIPER.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.JUNIPER_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("juniper_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_LEMON.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.LEMON_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("lemon_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.HONEY_WATER.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.MEAD.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("mead"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_ORANGE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.ORANGE_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("orange_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PAPAYA.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.PAPAYA_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("papaya_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PEACH.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.PEACH_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("peach_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PEAR.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.PEAR_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("pear_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PLUM.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.PLUM_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("plum_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PURPLE_GRAPE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.RED_WINE.get(), 500), new ItemStack(ItemsTFCF.POMACE), 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("red_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.RICE_WATER.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFC.SAKE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("sake_rice_water"),

            // Berry Wine
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_BLACKBERRY.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_blackberry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_BLUEBERRY.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_blueberry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_BUNCH_BERRY.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_bunch_berry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_CLOUD_BERRY.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_cloud_berry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_CRANBERRY.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_cranberry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_ELDERBERRY.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_elderberry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_GOOSEBERRY.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_gooseberry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_RASPBERRY.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_raspberry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_SNOW_BERRY.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_snow_berry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_STRAWBERRY.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_strawberry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_WINTERGREEN_BERRY.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_wintergreen_berry"),

            // Distilled Alcohol
            new BarrelRecipe(IIngredient.of(FluidsTFCF.AGAVE_WINE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.TEQUILA.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("tequila"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.BANANA_WINE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BANANA_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("banana_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.BERRY_WINE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BERRY_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.CIDER.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.CALVADOS.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("calvados"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.CHERRY_WINE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.CHERRY_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cherry_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUNIPER_WINE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.GIN.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("gin"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.LEMON_WINE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.LEMON_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("lemon_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.ORANGE_WINE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.ORANGE_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("orange_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.PAPAYA_WINE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.PAPAYA_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("papaya_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.PEACH_WINE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.PEACH_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("peach_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.PEAR_WINE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.PEAR_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("pear_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.PLUM_WINE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.PLUM_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("plum_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.RED_WINE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.SAKE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.SHOCHU.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("shochu"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.WHITE_WINE.get(), 500), IIngredient.of("sugar"), new FluidStack(FluidsTFCF.COGNAC.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cognac"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.VODKA.get(), 500), IIngredient.of("pomace"), new FluidStack(FluidsTFCF.GRAPPA.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("grappa"),

            // Malted Grain
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainBarley"), null, new ItemStack(ItemsTFCF.MALT_BARLEY), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_barley"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainMaize"), null, new ItemStack(ItemsTFCF.MALT_CORN), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_corn"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainRye"), null, new ItemStack(ItemsTFCF.MALT_RYE), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_rye"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainRice"), null, new ItemStack(ItemsTFCF.MALT_RICE), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_rice"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainWheat"), null, new ItemStack(ItemsTFCF.MALT_WHEAT), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_wheat"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainAmaranth"), null, new ItemStack(ItemsTFCF.MALT_AMARANTH), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_amaranth"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainBuckwheat"), null, new ItemStack(ItemsTFCF.MALT_BUCKWHEAT), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_buckwheat"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainFonio"), null, new ItemStack(ItemsTFCF.MALT_FONIO), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_fonio"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainMillet"), null, new ItemStack(ItemsTFCF.MALT_MILLET), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_millet"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainQuinoa"), null, new ItemStack(ItemsTFCF.MALT_QUINOA), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_quinoa"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainSpelt"), null, new ItemStack(ItemsTFCF.MALT_SPELT), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_spelt"),

            // Beer
            new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltBarley"), new FluidStack(FluidsTFCF.BEER_BARLEY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_barley"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltCorn"), new FluidStack(FluidsTFCF.BEER_CORN.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_corn"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltRye"), new FluidStack(FluidsTFCF.BEER_RYE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_rye"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltWheat"), new FluidStack(FluidsTFCF.BEER_WHEAT.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_wheat"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltAmaranth"), new FluidStack(FluidsTFCF.BEER_AMARANTH.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_amaranth"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltBuckwheat"), new FluidStack(FluidsTFCF.BEER_BUCKWHEAT.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_buckwheat"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltFonio"), new FluidStack(FluidsTFCF.BEER_FONIO.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_fonio"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltMillet"), new FluidStack(FluidsTFCF.BEER_MILLET.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_millet"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltQuinoa"), new FluidStack(FluidsTFCF.BEER_QUINOA.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_quinoa"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltSpelt"), new FluidStack(FluidsTFCF.BEER_SPELT.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_spelt"),

            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("rice"), new FluidStack(FluidsTFCF.RICE_WATER.get(), 500), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("rice_water"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("wildRice"), new FluidStack(FluidsTFCF.RICE_WATER.get(), 500), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("wild_rice_water"),

            // Kaolinite Clay
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 100), IIngredient.of("dustKaolinite"), null, new ItemStack(ItemsTFCF.KAOLINITE_CLAY), 0).setRegistryName("kaolinite_clay"),

            // Cooling
            new BarrelRecipeTemperature(IIngredient.of(FluidsTFCF.DISTILLED_WATER.get(), 1), 50).setRegistryName("distilled_water_cooling")
        );
    }

    @SubscribeEvent
    public static void onRegisterKnappingRecipeEvent(RegistryEvent.Register<KnappingRecipe> event)
    {
        KnappingRecipe r = new KnappingRecipeStone(KnappingTypes.MUD, rockIn -> new ItemStack(ItemUnfiredMudBrick.get(rockIn), 3), "XXXXX", "     ", "XXXXX", "     ", "XXXXX");
        event.getRegistry().register(r.setRegistryName("knapping_mud_brick"));

        // Kaolinite Clay Items
        for (Metal.ItemType type : Metal.ItemType.values())
        {
            if (type.hasMold(null))
            {
                int amount = type == INGOT ? 2 : 1;
                event.getRegistry().register(new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemUnfiredKaoliniteMold.get(type), amount), type.getPattern()).setRegistryName("kaolinite_" + type.name().toLowerCase() + "_mold"));
            }
        }

        event.getRegistry().registerAll(
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_BRICK, 3), "XXXXX", "     ", "XXXXX", "     ", "XXXXX").setRegistryName("kaolinite_clay_brick"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_VESSEL), " XXX ", "XXXXX", "XXXXX", "XXXXX", " XXX ").setRegistryName("kaolinite_clay_small_vessel"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_JUG), " X   ", "XXXX ", "XXX X", "XXXX ", "XXX  ").setRegistryName("kaolinite_clay_jug"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_POT), "X   X", "X   X", "X   X", "XXXXX", " XXX ").setRegistryName("kaolinite_clay_pot"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, false, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_BOWL, 2), "X   X", " XXX ").setRegistryName(MODID, "kaolinite_clay_bowl"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_BOWL, 4), "X   X", " XXX ", "     ", "X   X", " XXX ").setRegistryName("kaolinite_clay_bowl_2"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_LARGE_VESSEL), "X   X", "X   X", "X   X", "X   X", "XXXXX").setRegistryName("kaolinite_clay_large_vessel")
        );
    }

    @SubscribeEvent
    public static void onRegisterHeatRecipeEvent(RegistryEvent.Register<HeatRecipe> event)
    {
        // Mud Pottery
        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
        {
            ItemFiredMudBrick firedMudBrick = ItemFiredMudBrick.get(ItemUnfiredMudBrick.get(rock));

            HeatRecipe r = new HeatRecipeSimple(IIngredient.of(ItemUnfiredMudBrick.get(rock)), new ItemStack(firedMudBrick), 1599f, Metal.Tier.TIER_I);
            event.getRegistry().register(r.setRegistryName(rock.getRegistryName().getPath().toLowerCase() + "_unfired_mud_brick"));
            
            // Fired Pottery - doesn't burn up
            r = new HeatRecipeSimple(IIngredient.of(firedMudBrick), new ItemStack(firedMudBrick), 1599f, Metal.Tier.TIER_I);
            event.getRegistry().register(r.setRegistryName(rock.getRegistryName().getPath().toLowerCase() + "_fired_mud_brick"));
        }

        IForgeRegistry<HeatRecipe> r = event.getRegistry();

        // Kaolinite Pottery Items with metadata
        for (EnumDyeColor dye : EnumDyeColor.values())
        {
            r.register(
                new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_VESSEL_GLAZED, 1, dye.getMetadata())), new ItemStack(ItemsTFCF.FIRED_KAOLINITE_VESSEL_GLAZED, 1, dye.getMetadata()), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_kaolinite_vessel_glazed_" + dye.getName())
            );
        }

        // Kaolinite Molds
        for (Metal.ItemType type : Metal.ItemType.values())
        {
            ItemUnfiredKaoliniteMold unfiredMold = ItemUnfiredKaoliniteMold.get(type);
            ItemKaoliniteMold firedMold = ItemKaoliniteMold.get(type);
            if (unfiredMold != null && firedMold != null)
            {
                r.register(new HeatRecipeSimple(IIngredient.of(unfiredMold), new ItemStack(firedMold), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_kaolinite_mold_" + type.name().toLowerCase()));
            }
        }

        // Standard / Simple recipes
        r.registerAll(

            // Kaolinite Pottery
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_KAOLINITE_BRICK), new ItemStack(ItemsTFCF.FIRED_KAOLINITE_BRICK), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_kaolinite_brick"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_KAOLINITE_VESSEL), new ItemStack(ItemsTFCF.FIRED_KAOLINITE_VESSEL), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_kaolinite_vessel"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_KAOLINITE_JUG), new ItemStack(ItemsTFCF.FIRED_KAOLINITE_JUG), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_kaolinite_jug"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_KAOLINITE_POT), new ItemStack(ItemsTFCF.FIRED_KAOLINITE_POT), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_kaolinite_pot"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_KAOLINITE_BOWL), new ItemStack(ItemsTFCF.FIRED_KAOLINITE_BOWL), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_kaolinite_bowl"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_KAOLINITE_LARGE_VESSEL), new ItemStack(BlocksTFCF.FIRED_KAOLINITE_LARGE_VESSEL), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_kaolinite_large_vessel"),

            // Fired Kaolinite Pottery - doesn't burn up
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FIRED_KAOLINITE_BRICK), new ItemStack(ItemsTFCF.FIRED_KAOLINITE_BRICK), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_kaolinite_brick"),
            new HeatRecipeVessel(IIngredient.of(ItemsTFCF.FIRED_KAOLINITE_VESSEL), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_kaolinite_vessel"),
            new HeatRecipeVessel(IIngredient.of(ItemsTFCF.FIRED_KAOLINITE_VESSEL_GLAZED), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_kaolinite_vessel_glazed_all"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FIRED_KAOLINITE_JUG), new ItemStack(ItemsTFCF.FIRED_KAOLINITE_JUG), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_kaolinite_jug"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FIRED_KAOLINITE_POT), new ItemStack(ItemsTFCF.FIRED_KAOLINITE_POT), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_kaolinite_pot"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FIRED_KAOLINITE_BOWL), new ItemStack(ItemsTFCF.FIRED_KAOLINITE_BOWL), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_kaolinite_bowl"),
            new HeatRecipeSimple(IIngredient.of(BlocksTFCF.FIRED_KAOLINITE_LARGE_VESSEL), new ItemStack(BlocksTFCF.FIRED_KAOLINITE_LARGE_VESSEL), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_kaolinite_large_vessel"),

            // Epiphytes
            new HeatRecipeSimple(IIngredient.of("epiphyteArtistsConk"), new ItemStack(ItemsTFCF.ROASTED_ARTISTS_CONK, 1), 200, 480).setRegistryName("roasted_artists_conk"),
            new HeatRecipeSimple(IIngredient.of("epiphyteSulphurShelf"), new ItemStack(ItemsTFCF.ROASTED_SULPHUR_SHELF, 1), 200, 480).setRegistryName("roasted_sulphur_shelf"),
            new HeatRecipeSimple(IIngredient.of("epiphyteTurkeyTail"), new ItemStack(ItemsTFCF.ROASTED_TURKEY_TAIL, 1), 200, 480).setRegistryName("roasted_turkey_tail"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_ARTISTS_CONK), 480).setRegistryName("burned_artists_conk"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_SULPHUR_SHELF), 480).setRegistryName("burned_sulphur_shelf"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_TURKEY_TAIL), 480).setRegistryName("burned_turkey_tail"),

            // Mushrooms
            new HeatRecipeSimple(IIngredient.of("mushroomPorcini"), new ItemStack(ItemsTFCF.ROASTED_PORCINI, 1), 200, 480).setRegistryName("roasted_porcini"),
            new HeatRecipeSimple(IIngredient.of("mushroomBrown"), new ItemStack(ItemsTFCF.ROASTED_PORCINI, 1), 200, 480).setRegistryName("roasted_porcini_mushroom_brown"),
            new HeatRecipeSimple(IIngredient.of("mushroomAmanita"), new ItemStack(ItemsTFCF.ROASTED_AMANITA, 1), 200, 480).setRegistryName("roasted_amanita"),
            new HeatRecipeSimple(IIngredient.of("mushroomRed"), new ItemStack(ItemsTFCF.ROASTED_AMANITA, 1), 200, 480).setRegistryName("roasted_amanita_mushroom_brown"),
            new HeatRecipeSimple(IIngredient.of("mushroomBlackPowderpuff"), new ItemStack(ItemsTFCF.ROASTED_BLACK_POWDERPUFF, 1), 200, 480).setRegistryName("roasted_black_powderpuff"),
            new HeatRecipeSimple(IIngredient.of("mushroomChanterelle"), new ItemStack(ItemsTFCF.ROASTED_CHANTERELLE, 1), 200, 480).setRegistryName("roasted_chanterelle"),
            new HeatRecipeSimple(IIngredient.of("mushroomDeathCap"), new ItemStack(ItemsTFCF.ROASTED_DEATH_CAP, 1), 200, 480).setRegistryName("roasted_death_cap"),
            new HeatRecipeSimple(IIngredient.of("mushroomGiantClub"), new ItemStack(ItemsTFCF.ROASTED_GIANT_CLUB, 1), 200, 480).setRegistryName("roasted_giant_club"),
            new HeatRecipeSimple(IIngredient.of("mushroomParasolMushroom"), new ItemStack(ItemsTFCF.ROASTED_PARASOL_MUSHROOM, 1), 200, 480).setRegistryName("roasted_parasol_mushroom"),
            new HeatRecipeSimple(IIngredient.of("mushroomStinkhorn"), new ItemStack(ItemsTFCF.ROASTED_STINKHORN, 1), 200, 480).setRegistryName("roasted_stinkhorn"),
            new HeatRecipeSimple(IIngredient.of("mushroomWeepingMilkCap"), new ItemStack(ItemsTFCF.ROASTED_WEEPING_MILK_CAP, 1), 200, 480).setRegistryName("roasted_weeping_milk_cap"),
            new HeatRecipeSimple(IIngredient.of("mushroomWoodBlewit"), new ItemStack(ItemsTFCF.ROASTED_WOOD_BLEWIT, 1), 200, 480).setRegistryName("roasted_wood_blewit"),
            new HeatRecipeSimple(IIngredient.of("mushroomWoollyGomphus"), new ItemStack(ItemsTFCF.ROASTED_WOOLLY_GOMPHUS, 1), 200, 480).setRegistryName("roasted_woolly_gomphus"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PORCINI), 480).setRegistryName("burned_porcini"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_AMANITA), 480).setRegistryName("burned_amanita"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BLACK_POWDERPUFF), 480).setRegistryName("burned_black_powderpuff"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_CHANTERELLE), 480).setRegistryName("burned_chanterelle"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_DEATH_CAP), 480).setRegistryName("burned_death_cap"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_GIANT_CLUB), 480).setRegistryName("burned_giant_club"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PARASOL_MUSHROOM), 480).setRegistryName("burned_parasol_mushroom"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_STINKHORN), 480).setRegistryName("burned_stinkhorn"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_WEEPING_MILK_CAP), 480).setRegistryName("burned_weeping_milk_cap"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_WOOD_BLEWIT), 480).setRegistryName("burned_wood_blewit"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_WOOLLY_GOMPHUS), 480).setRegistryName("burned_woolly_gomphus"),

            // Food
            new HeatRecipeSimple(IIngredient.of("clamRaw"), new ItemStack(ItemsTFCF.COOKED_CLAM, 1), 200, 480).setRegistryName("cooked_clam"),
            new HeatRecipeSimple(IIngredient.of("scallopRaw"), new ItemStack(ItemsTFCF.COOKED_SCALLOP, 1), 200, 480).setRegistryName("cooked_scallop"),
            new HeatRecipeSimple(IIngredient.of("starfishRaw"), new ItemStack(ItemsTFCF.COOKED_STARFISH, 1), 200, 480).setRegistryName("cooked_starfish"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_CLAM), 480).setRegistryName("burned_clam"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_SCALLOP), 480).setRegistryName("burned_scallop"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_STARFISH), 480).setRegistryName("burned_starfish"),
            
            // Food Roasting/Drying
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BLACK_TEA)), new ItemStack(ItemsTFCF.DRIED_BLACK_TEA), 200, 480).setRegistryName("dried_black_tea"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.GREEN_TEA)), new ItemStack(ItemsTFCF.DRIED_GREEN_TEA), 200, 480).setRegistryName("dried_green_tea"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.WHITE_TEA)), new ItemStack(ItemsTFCF.DRIED_WHITE_TEA), 200, 480).setRegistryName("dried_white_tea"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CANNABIS_BUD)), new ItemStack(ItemsTFCF.DRIED_CANNABIS_BUD), 200, 480).setRegistryName("dried_cannabis_bud"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CANNABIS_LEAF)), new ItemStack(ItemsTFCF.DRIED_CANNABIS_LEAF), 200, 480).setRegistryName("dried_cannabis_leaf"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.COCA_LEAF)), new ItemStack(ItemsTFCF.DRIED_COCA_LEAF), 200, 480).setRegistryName("dried_coca_leaf"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.OPIUM_POPPY_BULB)), new ItemStack(ItemsTFCF.DRIED_OPIUM_POPPY_BULB), 200, 480).setRegistryName("dried_opium_poppy_bulb"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PEYOTE)), new ItemStack(ItemsTFCF.DRIED_PEYOTE), 200, 480).setRegistryName("dried_peyote"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.TOBACCO_LEAF)), new ItemStack(ItemsTFCF.DRIED_TOBACCO_LEAF), 200, 480).setRegistryName("dried_tobacco_leaf"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.DRIED_COCOA_BEANS)), new ItemStack(ItemsTFCF.ROASTED_COCOA_BEANS), 200, 480).setRegistryName("roasted_cocoa_beans"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.COFFEA_CHERRIES)), new ItemStack(ItemsTFCF.ROASTED_COFFEE_BEANS), 200, 480).setRegistryName("roasted_coffea_cherries"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CHAMOMILE_HEAD)), new ItemStack(ItemsTFCF.DRIED_CHAMOMILE_HEAD), 200, 480).setRegistryName("roasted_chamomile_head"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.DANDELION_HEAD)), new ItemStack(ItemsTFCF.DRIED_DANDELION_HEAD), 200, 480).setRegistryName("roasted_dandelion_head"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.LABRADOR_TEA_HEAD)), new ItemStack(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD), 200, 480).setRegistryName("roasted_labrador_tea_head"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.SUNFLOWER_HEAD)), new ItemStack(ItemsTFCF.DRIED_SUNFLOWER_HEAD), 200, 480).setRegistryName("roasted_sunflower_head"),

            // Food Destroy
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_BLACK_TEA), 480).setRegistryName("burned_black_tea"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_GREEN_TEA), 480).setRegistryName("burned_green_tea"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_WHITE_TEA), 480).setRegistryName("burned_white_tea"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_BUD), 480).setRegistryName("burned_cannabis_bud"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_LEAF), 480).setRegistryName("burned_cannabis_leaf"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_COCA_LEAF), 480).setRegistryName("burned_coca_leaf"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_OPIUM_POPPY_BULB), 480).setRegistryName("burned_opium_poppy_bulb"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_PEYOTE), 480).setRegistryName("burned_peyote"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_TOBACCO_LEAF), 480).setRegistryName("burned_tobacco_leaf"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_COCOA_BEANS), 480).setRegistryName("burned_cocoa_beans"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_COFFEE_BEANS), 480).setRegistryName("burned_coffea_cherries"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_CHAMOMILE_HEAD), 480).setRegistryName("burned_chamomile_head"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_DANDELION_HEAD), 480).setRegistryName("burned_dandelion_head"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD), 480).setRegistryName("burned_labrador_tea_head"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_SUNFLOWER_HEAD), 480).setRegistryName("burned_sunflower_head"),

            // Nut Roasting            
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.ACORN_NUT)), new ItemStack(ItemsTFCF.ROASTED_ACORN_NUT), 200, 480).setRegistryName("roasted_acorn"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.ALMOND_NUT)), new ItemStack(ItemsTFCF.ROASTED_ALMOND_NUT), 200, 480).setRegistryName("roasted_almond"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BEECHNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BEECHNUT_NUT), 200, 480).setRegistryName("roasted_beechnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BLACK_WALNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BLACK_WALNUT_NUT), 200, 480).setRegistryName("roasted_black_walnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BRAZIL_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BRAZIL_NUT_NUT), 200, 480).setRegistryName("roasted_brazil_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BREADNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BREADNUT_NUT), 200, 480).setRegistryName("roasted_breadnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BUNYA_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BUNYA_NUT_NUT), 200, 480).setRegistryName("roasted_bunya_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BUTTERNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BUTTERNUT_NUT), 200, 480).setRegistryName("roasted_butternut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CANDLENUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_CANDLENUT_NUT), 200, 480).setRegistryName("roasted_candlenut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CASHEW_NUT)), new ItemStack(ItemsTFCF.ROASTED_CASHEW_NUT), 200, 480).setRegistryName("roasted_cashew"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CHESTNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_CHESTNUT_NUT), 200, 480).setRegistryName("roasted_chestnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.GINKGO_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_GINKGO_NUT_NUT), 200, 480).setRegistryName("roasted_ginkgo_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.HAZELNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HAZELNUT_NUT), 200, 480).setRegistryName("roasted_hazelnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.HEARTNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HEARTNUT_NUT), 200, 480).setRegistryName("roasted_heartnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.HICKORY_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HICKORY_NUT_NUT), 200, 480).setRegistryName("roasted_hickory_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.KOLA_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_KOLA_NUT_NUT), 200, 480).setRegistryName("roasted_kola_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.KUKUI_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_KUKUI_NUT_NUT), 200, 480).setRegistryName("roasted_kukui_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.MACADAMIA_NUT)), new ItemStack(ItemsTFCF.ROASTED_MACADAMIA_NUT), 200, 480).setRegistryName("roasted_macadamia"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.MONGONGO_NUT)), new ItemStack(ItemsTFCF.ROASTED_MONGONGO_NUT), 200, 480).setRegistryName("roasted_mongongo"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.MONKEY_PUZZLE_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_MONKEY_PUZZLE_NUT_NUT), 200, 480).setRegistryName("roasted_monkey_puzzle_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.NUTMEG_NUT)), new ItemStack(ItemsTFCF.ROASTED_NUTMEG_NUT), 200, 480).setRegistryName("roasted_nutmeg"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PARADISE_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_PARADISE_NUT_NUT), 200, 480).setRegistryName("roasted_paradise_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PECAN_NUT)), new ItemStack(ItemsTFCF.ROASTED_PECAN_NUT), 200, 480).setRegistryName("roasted_pecan"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PINE_NUT)), new ItemStack(ItemsTFCF.ROASTED_PINE_NUT), 200, 480).setRegistryName("roasted_pine_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PISTACHIO_NUT)), new ItemStack(ItemsTFCF.ROASTED_PISTACHIO_NUT), 200, 480).setRegistryName("roasted_pistachio"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.WALNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_WALNUT_NUT), 200, 480).setRegistryName("roasted_walnut"),

            // Nut Destroy            
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_ACORN_NUT), 480).setRegistryName("burned_acorn"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_ALMOND_NUT), 480).setRegistryName("burned_almond"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BEECHNUT_NUT), 480).setRegistryName("burned_beechnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BLACK_WALNUT_NUT), 480).setRegistryName("burned_black_walnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BRAZIL_NUT_NUT), 480).setRegistryName("burned_brazil_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BREADNUT_NUT), 480).setRegistryName("burned_breadnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BUNYA_NUT_NUT), 480).setRegistryName("burned_bunya_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BUTTERNUT_NUT), 480).setRegistryName("burned_butternut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_CANDLENUT_NUT), 480).setRegistryName("burned_candlenut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_CASHEW_NUT), 480).setRegistryName("burned_cashew"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_CHESTNUT_NUT), 480).setRegistryName("burned_chestnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_GINKGO_NUT_NUT), 480).setRegistryName("burned_ginkgo_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_HAZELNUT_NUT), 480).setRegistryName("burned_hazelnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_HEARTNUT_NUT), 480).setRegistryName("burned_heartnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_HICKORY_NUT_NUT), 480).setRegistryName("burned_hickory_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_KOLA_NUT_NUT), 480).setRegistryName("burned_kola_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_KUKUI_NUT_NUT), 480).setRegistryName("burned_kukui_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_MACADAMIA_NUT), 480).setRegistryName("burned_macadamia"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_MONGONGO_NUT), 480).setRegistryName("burned_mongongo"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_MONKEY_PUZZLE_NUT_NUT), 480).setRegistryName("burned_monkey_puzzle_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_NUTMEG_NUT), 480).setRegistryName("burned_nutmeg"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PARADISE_NUT_NUT), 480).setRegistryName("burned_paradise_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PECAN_NUT), 480).setRegistryName("burned_pecan"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PINE_NUT), 480).setRegistryName("burned_pine_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PISTACHIO_NUT), 480).setRegistryName("burned_pistachio"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_WALNUT_NUT), 480).setRegistryName("burned_walnut"),

            // Kaolinite Clay
            new HeatRecipeSimple(IIngredient.of("clayKaolinite"), new ItemStack(ItemPowder.get(Powder.KAOLINITE), 1), 150).setRegistryName("kaolinite_clay"),
            new HeatRecipeSimple(IIngredient.of(ItemPowder.get(Powder.KAOLINITE)), new ItemStack(ItemPowder.get(Powder.KAOLINITE), 1), 1599f, Metal.Tier.TIER_I).setRegistryName("burnt_kaolinite_clay"),

        	// Ash
            new HeatRecipeSimple(IIngredient.of("twigWood"), new ItemStack(Blocks.TORCH, 4), 50).setRegistryName("torch_twig_wood"),
            new HeatRecipeSimple(IIngredient.of("twig"), new ItemStack(Blocks.TORCH, 4), 50).setRegistryName("torch_twig"),
            new HeatRecipeSimple(IIngredient.of("branchWood"), new ItemStack(Blocks.TORCH, 4), 50).setRegistryName("torch_branch_wood"),
            
            new HeatRecipeSimple(IIngredient.of("torch"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.ASH), 2), 425, 850).setRegistryName("torch_ore_ash"),
            new HeatRecipeSimple(IIngredient.of(Blocks.TORCH), new ItemStack(ItemPowderTFCF.get(PowderTFCF.ASH), 2), 425, 850).setRegistryName("torch_item_ash"),

            new HeatRecipeSimple(IIngredient.of(ItemPowderTFCF.get(PowderTFCF.ASH)), new ItemStack(ItemPowderTFCF.get(PowderTFCF.ASH), 1), 1599f, Metal.Tier.TIER_I).setRegistryName("burnt_ash"),

            // Charred Bones
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CHARRED_BONES)), new ItemStack(ItemsTFCF.CHARRED_BONES, 1), 1599f, Metal.Tier.TIER_I).setRegistryName("burnt_charred_bones")
        );
    }

    @SubscribeEvent
    public static void onRegisterLoomRecipeEvent(RegistryEvent.Register<LoomRecipe> event)
    {
        IForgeRegistry<LoomRecipe> r = event.getRegistry();

        r.registerAll(

            new LoomRecipe(new ResourceLocation(MODID, "cotton_cloth"), IIngredient.of(ItemsTFCF.COTTON_YARN, 12), new ItemStack(ItemsTFCF.COTTON_CLOTH), 12, new ResourceLocation(MODID, "textures/blocks/devices/loom/product/cotton.png")),
            new LoomRecipe(new ResourceLocation(MODID, "hemp_cloth"), IIngredient.of(ItemsTFCF.HEMP_STRING, 12), new ItemStack(ItemsTFCF.HEMP_CLOTH), 12, new ResourceLocation(MODID, "textures/blocks/devices/loom/product/hemp.png")),
            new LoomRecipe(new ResourceLocation(MODID, "linen_cloth"), IIngredient.of(ItemsTFCF.LINEN_STRING, 12), new ItemStack(ItemsTFCF.LINEN_CLOTH), 12, new ResourceLocation(MODID, "textures/blocks/devices/loom/product/linen.png")),
            new LoomRecipe(new ResourceLocation(MODID, "sisal_cloth"), IIngredient.of(ItemsTFCF.SISAL_STRING, 12), new ItemStack(ItemsTFCF.SISAL_CLOTH), 12, new ResourceLocation(MODID, "textures/blocks/devices/loom/product/sisal.png")),

            new LoomRecipe(new ResourceLocation(MODID, "wool_block_cotton"), IIngredient.of(ItemsTFCF.COTTON_CLOTH, 4), new ItemStack(Blocks.WOOL, 8), 4, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png")),
            new LoomRecipe(new ResourceLocation(MODID, "wool_block_linen"), IIngredient.of(ItemsTFCF.LINEN_CLOTH, 4), new ItemStack(Blocks.WOOL, 8), 4, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png")),
            new LoomRecipe(new ResourceLocation(MODID, "wool_block_silk"), IIngredient.of(ItemsTFC.SILK_CLOTH, 4), new ItemStack(Blocks.WOOL, 8), 4, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png"))
        );
    }

    @SubscribeEvent
    public static void onRegisterQuernRecipeEvent(RegistryEvent.Register<QuernRecipe> event)
    {
        IForgeRegistry<QuernRecipe> r = event.getRegistry();

        r.registerAll(

            // Ground Spices
            new QuernRecipe(IIngredient.of(ItemsTFCF.CASSIA_CINNAMON_BARK), new ItemStack(ItemsTFCF.GROUND_CASSIA_CINNAMON, 2)).setRegistryName("ground_cassia_cinnamon"),
            new QuernRecipe(IIngredient.of(ItemsTFCF.CEYLON_CINNAMON_BARK), new ItemStack(ItemsTFCF.GROUND_CEYLON_CINNAMON, 2)).setRegistryName("ground_ceylon_cinnamon"),
            new QuernRecipe(IIngredient.of(ItemsTFCF.BLACK_PEPPER), new ItemStack(ItemsTFCF.GROUND_BLACK_PEPPER, 2)).setRegistryName("ground_black_pepper"),
            new QuernRecipe(IIngredient.of(ItemsTFCF.ROASTED_COCOA_BEANS), new ItemStack(ItemsTFCF.COCOA_POWDER, 2)).setRegistryName("ground_cocoa_beans"),
            new QuernRecipe(IIngredient.of(ItemsTFCF.ROASTED_COFFEE_BEANS), new ItemStack(ItemsTFCF.COFFEE_POWDER, 2)).setRegistryName("ground_coffee_beans"),

            // Pearl
            new QuernRecipe(IIngredient.of("pearl"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.PEARL), 1)).setRegistryName("crushed_pearl"),
            new QuernRecipe(IIngredient.of("pearlBlack"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.BLACK_PEARL), 1)).setRegistryName("crushed_black_pearl"),

            // Papyrus Pulp
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PAPYRUS))), new ItemStack(ItemsTFCF.PAPYRUS_PULP, 3)).setRegistryName("crushed_papyrus"),

            // Dye from plants
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CHAMOMILE))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_chamomile"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HYDRANGEA))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_hydrangea"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LILY_OF_THE_VALLEY))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_lily_of_the_valley"),

            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MADDER))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("crushed_madder"),
            
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WOAD))), new ItemStack(ItemsTFC.DYE_BLUE, 2)).setRegistryName("crushed_woad"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.INDIGO))), new ItemStack(ItemsTFC.DYE_BLUE, 2)).setRegistryName("crushed_indigo"),
            
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SUNFLOWER))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_sunflower"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WELD))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_weld"),
            
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LILAC))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_lilac"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PEONY))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_peony"),
            
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LAVANDULA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage())).setRegistryName("crushed_lavandula"),
            
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CATTAIL))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("crushed_cattail"),

            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.AGAVE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_agave"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.RATTAN))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_rattan"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SUGAR_CANE))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_sugar_cane"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BADDERLOCKS))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_badderlocks"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GUTWEED))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_gutweed"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SAGO))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_sago")

        );
    }

    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void onRegisterNutTreeEvent(RegistryEvent.Register<NutRecipe> event)
    {
        IForgeRegistry<NutRecipe> r = event.getRegistry();
        Tree oak = TFCRegistries.TREES.getValue(DefaultTrees.OAK);
        Tree baobab = TFCRegistries.TREES.getValue(TreesTFCF.BAOBAB);
        Tree beech = TFCRegistries.TREES.getValue(TreesTFCF.BEECH);
        Tree black_walnut = TFCRegistries.TREES.getValue(TreesTFCF.BLACK_WALNUT);
        Tree butternut = TFCRegistries.TREES.getValue(TreesTFCF.BUTTERNUT);
        Tree chestnut = TFCRegistries.TREES.getValue(DefaultTrees.CHESTNUT);
        Tree european_oak = TFCRegistries.TREES.getValue(TreesTFCF.EUROPEAN_OAK);
        Tree ginkgo = TFCRegistries.TREES.getValue(TreesTFCF.GINKGO);
        Tree hawthorn = TFCRegistries.TREES.getValue(TreesTFCF.HAWTHORN);
        Tree hazel = TFCRegistries.TREES.getValue(TreesTFCF.HAZEL);
        Tree hemlock = TFCRegistries.TREES.getValue(TreesTFCF.HEMLOCK);
        Tree hickory = TFCRegistries.TREES.getValue(DefaultTrees.HICKORY);
        Tree hornbeam = TFCRegistries.TREES.getValue(TreesTFCF.HORNBEAM);
        Tree ironwood = TFCRegistries.TREES.getValue(TreesTFCF.IRONWOOD);
        Tree juniper = TFCRegistries.TREES.getValue(TreesTFCF.JUNIPER);
        Tree kauri = TFCRegistries.TREES.getValue(TreesTFCF.KAURI);
        Tree larch = TFCRegistries.TREES.getValue(TreesTFCF.LARCH);
        Tree maclura = TFCRegistries.TREES.getValue(TreesTFCF.MACLURA);
        Tree mahogany = TFCRegistries.TREES.getValue(TreesTFCF.MAHOGANY);
        Tree nordmann_fir = TFCRegistries.TREES.getValue(TreesTFCF.NORDMANN_FIR);
        Tree norway_spruce = TFCRegistries.TREES.getValue(TreesTFCF.NORWAY_SPRUCE);
        Tree pink_cherry = TFCRegistries.TREES.getValue(TreesTFCF.PINK_CHERRY);
        Tree pink_ivory = TFCRegistries.TREES.getValue(TreesTFCF.PINK_IVORY);
        Tree palm = TFCRegistries.TREES.getValue(DefaultTrees.PALM);
        Tree pine = TFCRegistries.TREES.getValue(DefaultTrees.PINE);
        Tree red_cedar = TFCRegistries.TREES.getValue(TreesTFCF.RED_CEDAR);
        Tree redwood = TFCRegistries.TREES.getValue(TreesTFCF.REDWOOD);
        Tree rowan = TFCRegistries.TREES.getValue(TreesTFCF.ROWAN);
        Tree syzygium = TFCRegistries.TREES.getValue(TreesTFCF.SYZYGIUM);
        Tree white_cherry = TFCRegistries.TREES.getValue(TreesTFCF.WHITE_CHERRY);
        Tree whitebeam = TFCRegistries.TREES.getValue(TreesTFCF.WHITEBEAM);
        Tree yew = TFCRegistries.TREES.getValue(TreesTFCF.YEW);

        r.registerAll(
            new NutRecipe(BlockLogTFC.get(oak), BlockLeavesTFC.get(oak), new ItemStack(ItemsTFCF.ACORN)).setRegistryName("oak_nut"),
            new NutRecipe(BlockLogTFC.get(european_oak), BlockLeavesTFC.get(european_oak), new ItemStack(ItemsTFCF.ACORN)).setRegistryName("european_oak_nut"),
            new NutRecipe(BlockLogTFC.get(baobab), BlockLeavesTFC.get(baobab), new ItemStack(ItemsTFCF.BAOBAB_FRUIT)).setRegistryName("baobab_fruit"),
            new NutRecipe(BlockLogTFC.get(beech), BlockLeavesTFC.get(beech), new ItemStack(ItemsTFCF.BEECHNUT)).setRegistryName("beech_nut"),
            new NutRecipe(BlockLogTFC.get(black_walnut), BlockLeavesTFC.get(black_walnut), new ItemStack(ItemsTFCF.BLACK_WALNUT)).setRegistryName("black_walnut_nut"),
            new NutRecipe(BlockLogTFC.get(butternut), BlockLeavesTFC.get(butternut), new ItemStack(ItemsTFCF.BUTTERNUT)).setRegistryName("butternut_nut"),
            new NutRecipe(BlockLogTFC.get(chestnut), BlockLeavesTFC.get(chestnut), new ItemStack(ItemsTFCF.CHESTNUT)).setRegistryName("chestnut_nut"),
            new NutRecipe(BlockLogTFC.get(ginkgo), BlockLeavesTFC.get(ginkgo), new ItemStack(ItemsTFCF.GINKGO_NUT)).setRegistryName("ginkgo_nut"),
            new NutRecipe(BlockLogTFC.get(hawthorn), BlockLeavesTFC.get(hawthorn), new ItemStack(ItemsTFCF.HAWTHORN)).setRegistryName("hawthorn_fruit"),
            new NutRecipe(BlockLogTFC.get(hazel), BlockLeavesTFC.get(hazel), new ItemStack(ItemsTFCF.HAZELNUT)).setRegistryName("hazel_nut"),
            new NutRecipe(BlockLogTFC.get(hemlock), BlockLeavesTFC.get(hemlock), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("hemlock_pinecone"),
            new NutRecipe(BlockLogTFC.get(hickory), BlockLeavesTFC.get(hickory), new ItemStack(ItemsTFCF.PECAN)).setRegistryName("hickory_pecan"),
            new NutRecipe(BlockLogTFC.get(hornbeam), BlockLeavesTFC.get(hornbeam), new ItemStack(ItemsTFCF.HOPS)).setRegistryName("hornbeam_hops"),
            //new NutRecipe(BlockLogTFC.get(hickory), BlockLeavesTFC.get(hickory), new ItemStack(ItemsTFCF.HICKORY_NUT)).setRegistryName("hickory_nut"),
            new NutRecipe(BlockLogTFC.get(ironwood), BlockLeavesTFC.get(ironwood), new ItemStack(ItemsTFCF.HOPS)).setRegistryName("ironwood_hops"),
            new NutRecipe(BlockLogTFC.get(juniper), BlockLeavesTFC.get(juniper), new ItemStack(ItemsTFCF.JUNIPER)).setRegistryName("juniper_fruit"),
            new NutRecipe(BlockLogTFC.get(kauri), BlockLeavesTFC.get(kauri), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("kauri_pinecone"),
            new NutRecipe(BlockLogTFC.get(larch), BlockLeavesTFC.get(larch), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("larch_pinecone"),
            new NutRecipe(BlockLogTFC.get(maclura), BlockLeavesTFC.get(maclura), new ItemStack(ItemsTFCF.OSAGE_ORANGE)).setRegistryName("maclura_fruit"),
            new NutRecipe(BlockLogTFC.get(mahogany), BlockLeavesTFC.get(mahogany), new ItemStack(ItemsTFCF.SKY_FRUIT)).setRegistryName("mahogany_fruit"),
            new NutRecipe(BlockLogTFC.get(nordmann_fir), BlockLeavesTFC.get(nordmann_fir), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("nordmann_fir_pinecone"),
            new NutRecipe(BlockLogTFC.get(norway_spruce), BlockLeavesTFC.get(norway_spruce), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("norway_spruce_pinecone"),
            new NutRecipe(BlockLogTFC.get(pink_cherry), BlockLeavesTFC.get(pink_cherry), new ItemStack(ItemsTFCF.WILD_CHERRY)).setRegistryName("pink_cherry_fruit"),
            new NutRecipe(BlockLogTFC.get(pink_ivory), BlockLeavesTFC.get(pink_ivory), new ItemStack(ItemsTFCF.PINK_IVORY_DRUPE)).setRegistryName("pink_ivory_fruit"),
            new NutRecipe(BlockLogTFC.get(palm), BlockLeavesTFC.get(palm), new ItemStack(ItemsTFCF.COCONUT)).setRegistryName("coconut"),
            new NutRecipe(BlockLogTFC.get(pine), BlockLeavesTFC.get(pine), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("pine_pinecone"),
            new NutRecipe(BlockLogTFC.get(red_cedar), BlockLeavesTFC.get(red_cedar), new ItemStack(ItemsTFCF.JUNIPER)).setRegistryName("red_cedar_fruit"),
            new NutRecipe(BlockLogTFC.get(redwood), BlockLeavesTFC.get(redwood), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("redwood_pinecone"),
            new NutRecipe(BlockLogTFC.get(rowan), BlockLeavesTFC.get(rowan), new ItemStack(ItemsTFCF.ROWAN_BERRY)).setRegistryName("rowan_fruit"),
            new NutRecipe(BlockLogTFC.get(syzygium), BlockLeavesTFC.get(syzygium), new ItemStack(ItemsTFCF.RIBERRY)).setRegistryName("syzygium_fruit"),
            new NutRecipe(BlockLogTFC.get(white_cherry), BlockLeavesTFC.get(white_cherry), new ItemStack(ItemsTFCF.WILD_CHERRY)).setRegistryName("white_cherry_fruit"),
            new NutRecipe(BlockLogTFC.get(whitebeam), BlockLeavesTFC.get(whitebeam), new ItemStack(ItemsTFCF.RIBERRY)).setRegistryName("whitebeam_fruit"),
            new NutRecipe(BlockLogTFC.get(yew), BlockLeavesTFC.get(yew), new ItemStack(ItemsTFCF.YEW_BERRY)).setRegistryName("yew_fruit")

        );
    }

    @SubscribeEvent
    public static void onRegisterCrackingRecipeEvent(RegistryEvent.Register<CrackingRecipe> event)
    {
        ItemStack filled_coconut = new ItemStack(ItemsTFCF.CRACKED_COCONUT);
        IFluidHandler fluidHandler = filled_coconut.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (fluidHandler != null)
            fluidHandler.fill(new FluidStack(FluidsTFCF.COCONUT_MILK.get(), 1000), true);

        IForgeRegistry<CrackingRecipe> r = event.getRegistry();
        r.registerAll(
            // Regular Trees
            new CrackingRecipe(IIngredient.of(ItemsTFCF.ACORN), new ItemStack(ItemsTFCF.ACORN_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.BEECHNUT), new ItemStack(ItemsTFCF.BEECHNUT_NUT), 0.5f).setRegistryName("beechnut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.BLACK_WALNUT), new ItemStack(ItemsTFCF.BLACK_WALNUT_NUT), 0.5f).setRegistryName("black_walnut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.BUTTERNUT), new ItemStack(ItemsTFCF.BUTTERNUT_NUT), 0.5f).setRegistryName("butternut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.CHESTNUT), new ItemStack(ItemsTFCF.CHESTNUT_NUT), 0.5f).setRegistryName("chestnut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.GINKGO_NUT), new ItemStack(ItemsTFCF.GINKGO_NUT_NUT), 0.5f).setRegistryName("ginkgo_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.HAZELNUT), new ItemStack(ItemsTFCF.HAZELNUT_NUT), 0.5f).setRegistryName("hazelnut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.HICKORY_NUT), new ItemStack(ItemsTFCF.HICKORY_NUT_NUT), 0.5f).setRegistryName("hickory_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.PINECONE), new ItemStack(ItemsTFCF.PINE_NUT), 0.5f).setRegistryName("pine_nuts"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.PECAN), new ItemStack(ItemsTFCF.PECAN_NUT), 0.5f).setRegistryName("pecans"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.COCONUT), filled_coconut, 0.5f).setRegistryName("coconut_milk"),

            // Fruit Trees
            new CrackingRecipe(IIngredient.of(ItemsTFCF.ALMOND), new ItemStack(ItemsTFCF.ALMOND_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.BRAZIL_NUT), new ItemStack(ItemsTFCF.BRAZIL_NUT_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.BREADNUT), new ItemStack(ItemsTFCF.BREADNUT_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.BUNYA_NUT), new ItemStack(ItemsTFCF.BUNYA_NUT_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.CANDLENUT), new ItemStack(ItemsTFCF.CANDLENUT_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.CHESTNUT), new ItemStack(ItemsTFCF.CHESTNUT_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.HEARTNUT), new ItemStack(ItemsTFCF.HEARTNUT_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.KOLA_NUT), new ItemStack(ItemsTFCF.KOLA_NUT_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.KUKUI_NUT), new ItemStack(ItemsTFCF.KUKUI_NUT_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.MACADAMIA), new ItemStack(ItemsTFCF.MACADAMIA_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.MONGONGO), new ItemStack(ItemsTFCF.MONGONGO_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.MONKEY_PUZZLE_NUT), new ItemStack(ItemsTFCF.MONKEY_PUZZLE_NUT_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.NUTMEG), new ItemStack(ItemsTFCF.NUTMEG_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.PISTACHIO), new ItemStack(ItemsTFCF.PISTACHIO_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.WALNUT), new ItemStack(ItemsTFCF.WALNUT_NUT), 0.5f).setRegistryName("acorn_fruit"),

            // Other
            new CrackingRecipe(IIngredient.of(ItemsTFCF.CACAO), new ItemStack(ItemsTFCF.COCOA_BEANS), 0.5f).setRegistryName("cocoa_beans")
        );
    }
    @SubscribeEvent
    public static void onRegisterCraftingRecipeEvent(RegistryEvent.Register<IRecipe> event)
    {
        IForgeRegistry<IRecipe> r = event.getRegistry();
        //Register all strips
        List<ItemStack> allHammers = new ArrayList<>();
        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (!metal.isToolMetal())
                continue;
                allHammers.add(new ItemStack(ItemMetal.get(metal, Metal.ItemType.HAMMER), 1, OreDictionary.WILDCARD_VALUE));
        }
        Ingredient hammer = Ingredient.fromStacks(allHammers.toArray(new ItemStack[0]));

        ResourceLocation groupSurfaceRock = new ResourceLocation(MODID, "surface_rock");

        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
        {
            /*
             * Surface rocks to TFC rocks
             */
            Ingredient ingredient = Ingredient.fromStacks(new ItemStack(BlockSurfaceRock.get(rock)));
            ItemStack output = new ItemStack(ItemRock.get(rock), 1);
            if (!output.isEmpty())
            {
                NonNullList<Ingredient> list = NonNullList.create();
                list.add(hammer);
                list.add(ingredient);
                //noinspection ConstantConditions
                r.register(new ShapelessDamageRecipe(groupSurfaceRock, list, output, 1).setRegistryName(MODID, rock.getRegistryName().getPath().toLowerCase() + "_rock_hammer"));
            }
        }
    }
}