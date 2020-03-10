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

import tfcelementia.util.agriculture.FoodTFCE;
import tfcelementia.util.OreDictionaryHelperTFCE;
import tfcelementia.util.ItemsRegistryHandler;
import tfcelementia.objects.fluids.FluidsTFCE;
import tfcelementia.objects.items.ItemFoodTFCE;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.Metal.ItemType.*;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_MISC;
import static net.dries007.tfc.objects.fluids.FluidsTFC.*;
import static net.dries007.tfc.types.DefaultMetals.*;
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
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.RED_GRAPES)), new FluidStack(FluidsTFC.RED_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("red_wine"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of(ItemFoodTFCE.get(FoodTFCE.GREEN_GRAPES)), new FluidStack(FluidsTFC.WHITE_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("white_wine")
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

        //Products
        simpleItems.add(register(r, "crop/product/agave", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_boll", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_cloth", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_yarn", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/flax", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/flax_fiber", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_fiber", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_cloth", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_string", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/madder", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_fiber", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/weld", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/woad", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/white_tea", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/green_tea", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        simpleItems.add(register(r, "crop/product/black_tea", new ItemMisc(Size.TINY, Weight.LIGHT), CT_MISC));
        
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
            //Grain
            new QuernRecipe(IIngredient.of("grainBarley"), new ItemStack(ItemFoodTFC.get(Food.BARLEY_FLOUR), 1)).setRegistryName("barley"),
            new QuernRecipe(IIngredient.of("grainOat"), new ItemStack(ItemFoodTFC.get(Food.OAT_FLOUR), 1)).setRegistryName("oat"),
            new QuernRecipe(IIngredient.of("grainRice"), new ItemStack(ItemFoodTFC.get(Food.RICE_FLOUR), 1)).setRegistryName("rice"),
            new QuernRecipe(IIngredient.of("grainRye"), new ItemStack(ItemFoodTFC.get(Food.RYE_FLOUR), 1)).setRegistryName("rye"),
            new QuernRecipe(IIngredient.of("grainWheat"), new ItemStack(ItemFoodTFC.get(Food.WHEAT_FLOUR), 1)).setRegistryName("wheat"),
            new QuernRecipe(IIngredient.of("maize"), new ItemStack(ItemFoodTFC.get(Food.CORNMEAL_FLOUR), 1)).setRegistryName("maize"),

            //Flux
            new QuernRecipe(IIngredient.of("gemBorax"), new ItemStack(ItemPowder.get(Powder.FLUX), 6)).setRegistryName("boarx"),
            new QuernRecipe(IIngredient.of("rockFlux"), new ItemStack(ItemPowder.get(Powder.FLUX), 2)).setRegistryName("flux"),

            //Redstone
            new QuernRecipe(IIngredient.of("gemCinnabar"), new ItemStack(Items.REDSTONE, 8)).setRegistryName("cinnabar"),
            new QuernRecipe(IIngredient.of("gemCryolite"), new ItemStack(Items.REDSTONE, 8)).setRegistryName("cryolite"),

            //Hematite
            new QuernRecipe(IIngredient.of(ItemSmallOre.get(Ore.HEMATITE, 1)), new ItemStack(ItemPowder.get(Powder.HEMATITE), 2)).setRegistryName("hematite_powder_from_small"),
            new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.HEMATITE, Ore.Grade.POOR, 1)), new ItemStack(ItemPowder.get(Powder.HEMATITE), 3)).setRegistryName("hematite_powder_from_poor"),
            new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.HEMATITE, Ore.Grade.NORMAL, 1)), new ItemStack(ItemPowder.get(Powder.HEMATITE), 5)).setRegistryName("hematite_powder_from_normal"),
            new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.HEMATITE, Ore.Grade.RICH, 1)), new ItemStack(ItemPowder.get(Powder.HEMATITE), 7)).setRegistryName("hematite_powder_from_rich"),

            //Limonite
            new QuernRecipe(IIngredient.of(ItemSmallOre.get(Ore.LIMONITE, 1)), new ItemStack(ItemPowder.get(Powder.LIMONITE), 2)).setRegistryName("limonite_powder_from_small"),
            new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.LIMONITE, Ore.Grade.POOR, 1)), new ItemStack(ItemPowder.get(Powder.LIMONITE), 3)).setRegistryName("limonite_powder_from_poor"),
            new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.LIMONITE, Ore.Grade.NORMAL, 1)), new ItemStack(ItemPowder.get(Powder.LIMONITE), 5)).setRegistryName("limonite_powder_from_normal"),
            new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.LIMONITE, Ore.Grade.RICH, 1)), new ItemStack(ItemPowder.get(Powder.LIMONITE), 7)).setRegistryName("limonite_powder_from_rich"),

            //Malachite
            new QuernRecipe(IIngredient.of(ItemSmallOre.get(Ore.MALACHITE, 1)), new ItemStack(ItemPowder.get(Powder.MALACHITE), 2)).setRegistryName("malachite_powder_from_small"),
            new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.MALACHITE, Ore.Grade.POOR, 1)), new ItemStack(ItemPowder.get(Powder.MALACHITE), 3)).setRegistryName("malachite_powder_from_poor"),
            new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.MALACHITE, Ore.Grade.NORMAL, 1)), new ItemStack(ItemPowder.get(Powder.MALACHITE), 5)).setRegistryName("malachite_powder_from_normal"),
            new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.MALACHITE, Ore.Grade.RICH, 1)), new ItemStack(ItemPowder.get(Powder.MALACHITE), 7)).setRegistryName("malachite_powder_from_rich"),

            //Bone meal
            new QuernRecipe(IIngredient.of("bone"), new ItemStack(Items.DYE, 3, EnumDyeColor.WHITE.getDyeDamage())).setRegistryName("bone_meal_from_bone"),
            new QuernRecipe(IIngredient.of(Blocks.BONE_BLOCK), new ItemStack(Items.DYE, 9, EnumDyeColor.WHITE.getDyeDamage())).setRegistryName("bone_meal_from_bone_block"),

            //Misc
            new QuernRecipe(IIngredient.of("gemSylvite"), new ItemStack(ItemPowder.get(Powder.FERTILIZER), 4)).setRegistryName("sylvite"),
            new QuernRecipe(IIngredient.of("gemSulfur"), new ItemStack(ItemPowder.get(Powder.SULFUR), 4)).setRegistryName("sulfur"),
            new QuernRecipe(IIngredient.of("gemSaltpeter"), new ItemStack(ItemPowder.get(Powder.SALTPETER), 4)).setRegistryName("saltpeter"),
            new QuernRecipe(IIngredient.of("rockRocksalt"), new ItemStack(ItemPowder.get(Powder.SALT), 4)).setRegistryName("rocksalt"),
            new QuernRecipe(IIngredient.of(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER, 2)).setRegistryName("blaze_powder"),
            new QuernRecipe(IIngredient.of("gemLapisLazuli"), new ItemStack(ItemPowder.get(Powder.LAPIS_LAZULI), 4)).setRegistryName("lapis_lazuli"),
            new QuernRecipe(IIngredient.of("gemGraphite"), new ItemStack(ItemPowder.get(Powder.GRAPHITE), 4)).setRegistryName("graphite_powder"),
            new QuernRecipe(IIngredient.of("gemKaolinite"), new ItemStack(ItemPowder.get(Powder.KAOLINITE), 4)).setRegistryName("kaolinite_powder"),
            new QuernRecipeRandomGem(IIngredient.of("gemKimberlite"), Gem.DIAMOND).setRegistryName("diamonds")
        );
    }
}