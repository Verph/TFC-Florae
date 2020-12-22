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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
//import net.minecraftforge.fml.relauncher.ReflectionHelper;
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
import net.dries007.tfc.objects.items.rock.ItemRockToolHead;
import net.dries007.tfc.objects.recipes.ShapelessDamageRecipe;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.types.DefaultTrees;
//import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.agriculture.Food;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.fuel.FuelManager;
import net.dries007.tfc.util.skills.SmithingSkill;
import net.dries007.tfc.util.skills.SmithingSkill.Type;

import tfcflorae.TFCFlorae;
import tfcflorae.api.recipes.CrackingRecipe;
import tfcflorae.api.recipes.NutRecipe;
import tfcflorae.objects.PowderTFCF;
import tfcflorae.objects.blocks.blocktype.*;
import tfcflorae.objects.fluids.FluidsTFCF;
import tfcflorae.objects.items.ItemPowderTFCF;
import tfcflorae.objects.items.ItemTFCF;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.types.PlantsTFCF;
import tfcflorae.types.BlockTypesTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;
import tfcflorae.util.OreDictionaryHelper;

//import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
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
    @SubscribeEvent
    public static void onRegisterBarrelRecipeEventMoss(RegistryEvent.Register<BarrelRecipe> event)
    {
        for(Rock rock : TFCRegistries.ROCKS.getValuesCollection())
        {
            event.getRegistry().registerAll(
                //new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 200), IIngredient.of(BlockRockVariant.get(rock, Rock.Type.RAW)), new FluidStack(FRESH_WATER.get(), 50), new ItemStack(BlockDecoration.get(rock, BlockTypesTFCF.MOSSY_RAW), 1), 8* ICalendar.TICKS_IN_HOUR).setRegistryName(TFCFlorae.MODID, "mossy_raw_"+rock.getRegistryName().getPath())
                new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 200), IIngredient.of(BlockRockVariant.get(rock, Rock.Type.RAW)), new FluidStack(FRESH_WATER.get(), 50), new ItemStack(BlockRockVariantTFCF.get(rock, BlockTypesTFCF.RockTFCF.MOSSY_RAW), 1), 8* ICalendar.TICKS_IN_HOUR).setRegistryName(TFCFlorae.MODID, "mossy_raw_"+rock.getRegistryName().getPath())
            );
        }
    }

    @SubscribeEvent
    public static void onRegisterBarrelRecipeEvent(RegistryEvent.Register<BarrelRecipe> event)
    {
        IForgeRegistry<BarrelRecipe> r = event.getRegistry();

        r.registerAll(

            // Limewater
            /*
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of("dustCalcite"), new FluidStack(LIMEWATER.get(), 500), ItemStack.EMPTY, 0).setRegistryName("limewater_from_calcite"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of("dustQuicklime"), new FluidStack(LIMEWATER.get(), 500), ItemStack.EMPTY, 0).setRegistryName("limewater_from_quicklime"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of("dustSlakedLime"), new FluidStack(LIMEWATER.get(), 500), ItemStack.EMPTY, 0).setRegistryName("limewater_from_slaked_lime"),
            */

            // Sugar
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 600), IIngredient.of("sugarcane", 5), null, new ItemStack(Items.SUGAR), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_from_sugar_cane"),

            // Papyrus Fibers
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 600), IIngredient.of("pulpPapyrus", 3), null, new ItemStack(ItemsTFCF.PAPYRUS_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("papyrus_fiber_from_papyrus"),

        	// Fiber Processing
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of("cropAgave"), null, new ItemStack(ItemsTFCF.SISAL_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sisal_fiber"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of("cropFlax"), null, new ItemStack(ItemsTFCF.FLAX_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("flax_fiber"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of("cropHemp"), null, new ItemStack(ItemsTFCF.HEMP_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("hemp_fiber"),
            // Fluidproduction from paste
            // Olive
            new BarrelRecipe(IIngredient.of(OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("olive_oil_sisal"),
            new BarrelRecipe(IIngredient.of(OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("olive_oil_silk"),
            new BarrelRecipe(IIngredient.of(OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("olive_oil_cotton"),
            new BarrelRecipe(IIngredient.of(OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("olive_oil_linen"),
            new BarrelRecipe(IIngredient.of(OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("olive_oil_hemp"),

            // Dirty Nets
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_SISAL_NET), null, new ItemStack(ItemsTFCF.SISAL_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_sisal"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_SILK_NET), null, new ItemStack(ItemsTFCF.SILK_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_silk"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_COTTON_NET), null, new ItemStack(ItemsTFCF.COTTON_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_cotton"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_LINEN_NET), null, new ItemStack(ItemsTFCF.LINEN_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_linen"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_HEMP_NET), null, new ItemStack(ItemsTFCF.HEMP_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_hemp"),

            // Dyes
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of("cropAgave"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("green_dye_agave"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of("cropIndigo"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_indigo"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of("cropMadder"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.RED).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("red_dye_madder"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of("cropWeld"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.YELLOW).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("yellow_dye_weld"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of("cropWoad"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_woad"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of("boneCharred"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("black_dye_charred_bones"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of("dustBlackPearl"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("black_dye_black_pearl_powder"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of("dustPearl"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("pink_dye_pearl_powder"),
            new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of("dustLogwood"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("purple_dye_logwood_powder")
        );
    }
    
    @SubscribeEvent
    public static void onRegisterHeatRecipeEvent(RegistryEvent.Register<HeatRecipe> event)
    {
        IForgeRegistry<HeatRecipe> r = event.getRegistry();

        // Standard / Simple recipes
        r.registerAll(
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

            // Quicklime
            /*
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CALCITE_POT)), new ItemStack(ItemsTFCF.QUICKLIME_POT, 1), 1000f, Metal.Tier.TIER_I).setRegistryName("quicklime_pot"),
            new HeatRecipeSimple(IIngredient.of("dustPearl"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.QUICKLIME), 1), 1000f, Metal.Tier.TIER_I).setRegistryName("quicklime_pearl"),
            new HeatRecipeSimple(IIngredient.of("dustBlackPearl"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.QUICKLIME), 1), 1000f, Metal.Tier.TIER_I).setRegistryName("quicklime_black_pearl"),
            /*

        	// Ash
            new HeatRecipeSimple(IIngredient.of("pinecone"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.ASH), 1), 425, 850).setRegistryName("burnt_pinecone"),
            /*
            new HeatRecipeSimple(IIngredient.of("twigWood"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.ASH), 1), 425, 850).setRegistryName("burnt_twig_wood"),
            new HeatRecipeSimple(IIngredient.of("twig"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.ASH), 1), 425, 850).setRegistryName("burnt_twig"),
            new HeatRecipeSimple(IIngredient.of("branchWood"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.ASH), 1), 425, 850).setRegistryName("burnt_branch"),
            */
            new HeatRecipeSimple(IIngredient.of("twigWood"), new ItemStack(Blocks.TORCH, 4), 50).setRegistryName("torch_twig_wood"),
            new HeatRecipeSimple(IIngredient.of("twig"), new ItemStack(Blocks.TORCH, 4), 50).setRegistryName("torch_twig"),
            new HeatRecipeSimple(IIngredient.of("branchWood"), new ItemStack(Blocks.TORCH, 4), 50).setRegistryName("torch_branch_wood"),
            
            new HeatRecipeSimple(IIngredient.of("torch"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.ASH), 2), 425, 850).setRegistryName("torch_ore_ash"),
            new HeatRecipeSimple(IIngredient.of(Blocks.TORCH), new ItemStack(ItemPowderTFCF.get(PowderTFCF.ASH), 2), 425, 850).setRegistryName("torch_item_ash"),

            new HeatRecipeSimple(IIngredient.of(ItemPowderTFCF.get(PowderTFCF.ASH)), new ItemStack(ItemPowderTFCF.get(PowderTFCF.ASH), 1), 1599f, Metal.Tier.TIER_I).setRegistryName("burnt_ash"),

            // Charred Bones
            //new HeatRecipeSimple(IIngredient.of("bone"), new ItemStack(ItemsTFCF.CHARRED_BONES, 1), 425, 850).setRegistryName("burnt_bone"),
            //new HeatRecipeSimple(IIngredient.of("bones"), new ItemStack(ItemsTFCF.CHARRED_BONES, 1), 425, 850).setRegistryName("burnt_bones"),
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

            // Calcite
            /*	
            new QuernRecipe(IIngredient.of("seashell"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.CALCITE), 2)).setRegistryName("calcite_powder_from_seashells"),
            //new QuernRecipe(IIngredient.of("conch"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.CALCITE), 2)).setRegistryName("calcite_powder_from_conchs"),
            //new QuernRecipe(IIngredient.of("clam"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.CALCITE), 2)).setRegistryName("calcite_powder_from_clams"),
            //new QuernRecipe(IIngredient.of("scallop"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.CALCITE), 2)).setRegistryName("calcite_powder_from_scallops"),
            //new QuernRecipe(IIngredient.of("starfish"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.CALCITE), 2)).setRegistryName("calcite_powder_from_starfish"),
            */
                        
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
}