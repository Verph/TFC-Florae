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
import tfcelementia.api.recipes.quern.QuernRecipeRandomGemTFCE;
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

//import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
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
public final class RecipesTFCE
{
    @SubscribeEvent
    public static void onRegisterKnappingRecipeEvent(RegistryEvent.Register<KnappingRecipe> event)
    {
        IForgeRegistry<KnappingRecipe> r = event.getRegistry();

        r.registerAll(
            new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFCE.UNFIRED_NUGGET), "XX XX", "X  XX", "X   X", "XX XX", "XXXXX").setRegistryName("clay_nugget"),
            new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFCE.UNFIRED_NAIL), "X   X", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName("clay_nail"),
            new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFCE.UNFIRED_RING), "XXXXX", "X   X", "X X X", "X   X", "XXXXX").setRegistryName("clay_ring")
        );
    }

    @SubscribeEvent
    public static void onRegisterHeatRecipeEventMold(RegistryEvent.Register<HeatRecipe> event)
    {
        event.getRegistry().registerAll(
        		
        	// Ceramic Molds
        	new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCE.UNFIRED_NUGGET)), new ItemStack(ItemsTFCE.MOLD_NUGGET), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_mold_nugget"),
           	new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCE.UNFIRED_NAIL)), new ItemStack(ItemsTFCE.MOLD_NAIL), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_mold_nail"),
           	new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCE.UNFIRED_RING)), new ItemStack(ItemsTFCE.MOLD_RING), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_mold_ring")
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
            new HeatRecipeSimple(IIngredient.of("stickWood"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.ASH), 1), 425, 850f).setRegistryName("burnt_stick")
        );
    }

    @SubscribeEvent
    public static void onRegisterQuernRecipeEvent(RegistryEvent.Register<QuernRecipe> event)
    {
        IForgeRegistry<QuernRecipe> r = event.getRegistry();

        r.registerAll(
            /*
        	// Celestine
            new QuernRecipe(IIngredient.of(ItemSmallOre.get(Ore.CELESTINE, 1)), new ItemStack(ItemPowderTFCE.get(PowderTFCE.CELESTINE), 2)).setRegistryName("celestine_powder_from_small"),
            new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.CELESTINE, Ore.Grade.POOR, 1)), new ItemStack(ItemPowderTFCE.get(PowderTFCE.CELESTINE), 3)).setRegistryName("celestine_powder_from_poor"),
            new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.CELESTINE, Ore.Grade.NORMAL, 1)), new ItemStack(ItemPowderTFCE.get(PowderTFCE.CELESTINE), 5)).setRegistryName("celestine_powder_from_normal"),
            new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.CELESTINE, Ore.Grade.RICH, 1)), new ItemStack(ItemPowderTFCE.get(PowderTFCE.CELESTINE), 7)).setRegistryName("celestine_powder_from_rich"),
            */
            
            // Mineral Powders
            new QuernRecipe(IIngredient.of("gemCalcium"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.CALCIUM), 4)).setRegistryName("calcium_powder_from_calcium"),
            new QuernRecipe(IIngredient.of("oreStrontiumSmall"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.CELESTINE), 2)).setRegistryName("celestine_powder_from_small_celestine_ore"),
            new QuernRecipe(IIngredient.of("oreStrontiumPoor"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.CELESTINE), 3)).setRegistryName("celestine_powder_from_poor_celestine_ore"),
            new QuernRecipe(IIngredient.of("oreStrontiumNormal"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.CELESTINE), 5)).setRegistryName("celestine_powder_from_normal_celestine_ore"),
            new QuernRecipe(IIngredient.of("oreStrontiumRich"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.CELESTINE), 7)).setRegistryName("celestine_powder_from_rich_celestine_ore"),
            new QuernRecipe(IIngredient.of("gemFluorite"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.FLUORITE), 4)).setRegistryName("fluorite_powder_from_fluorite"),
            new QuernRecipe(IIngredient.of("gemIodate"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.IODATE), 4)).setRegistryName("iodate_powder_from_iodate"),
            new QuernRecipe(IIngredient.of("gemPhosphorite"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.PHOSPHORITE), 4)).setRegistryName("phosphorite_powder_from_phosphorite"),
            new QuernRecipe(IIngredient.of("gemSalammoniac"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.AMMONIUM_CHLORIDE), 4)).setRegistryName("ammonium_chloride_powder_from_salammoniac"),
            new QuernRecipe(IIngredient.of("gemSelenide"), new ItemStack(ItemPowderTFCE.get(PowderTFCE.SELENIDE), 4)).setRegistryName("selenide_powder_from_selenide"),
            new QuernRecipe(IIngredient.of("gemCoke"), new ItemStack(ItemPowder.get(Powder.COKE), 4)).setRegistryName("coke_powder_from_coke"),
            
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

            // Gems from geodes
            new QuernRecipeRandomGem(IIngredient.of("gemGeodeAgate"), Gem.AGATE).setRegistryName("geode_agate"),
            new QuernRecipeRandomGem(IIngredient.of("gemGeodeAmethyst"), Gem.AMETHYST).setRegistryName("geode_amethyst"),
            new QuernRecipeRandomGem(IIngredient.of("gemGeodeBeryl"), Gem.BERYL).setRegistryName("geode_beryl"),
            new QuernRecipeRandomGem(IIngredient.of("gemGeodeDiamond"), Gem.DIAMOND).setRegistryName("geode_diamond"),
            new QuernRecipeRandomGem(IIngredient.of("gemGeodeEmerald"), Gem.EMERALD).setRegistryName("geode_emerald"),
            new QuernRecipeRandomGem(IIngredient.of("gemGeodeGarnet"), Gem.GARNET).setRegistryName("geode_garnet"),
            new QuernRecipeRandomGem(IIngredient.of("gemGeodeJade"), Gem.JADE).setRegistryName("geode_jade"),
            new QuernRecipeRandomGem(IIngredient.of("gemGeodeJasper"), Gem.JASPER).setRegistryName("geode_jasper"),
            new QuernRecipeRandomGem(IIngredient.of("gemGeodeOpal"), Gem.OPAL).setRegistryName("geode_opal"),
            new QuernRecipeRandomGem(IIngredient.of("gemGeodeRuby"), Gem.RUBY).setRegistryName("geode_ruby"),
            new QuernRecipeRandomGem(IIngredient.of("gemGeodeSapphire"), Gem.SAPPHIRE).setRegistryName("geode_sapphire"),
            new QuernRecipeRandomGem(IIngredient.of("gemGeodeTopaz"), Gem.TOPAZ).setRegistryName("geode_topaz"),
            new QuernRecipeRandomGem(IIngredient.of("gemGeodeTourmaline"), Gem.TOURMALINE).setRegistryName("geode_tourmaline"),
            new QuernRecipeRandomGemTFCE(IIngredient.of("gemGeodeAmber"), GemTFCE.AMBER).setRegistryName("geode_amber"),
            new QuernRecipeRandomGemTFCE(IIngredient.of("gemGeodeBromargyrite"), GemTFCE.BROMARGYRITE).setRegistryName("geode_bromargyrite"),
            new QuernRecipeRandomGemTFCE(IIngredient.of("gemGeodeCitrine"), GemTFCE.CITRINE).setRegistryName("geode_citrine"),
            new QuernRecipeRandomGemTFCE(IIngredient.of("gemGeodeHeliodor"), GemTFCE.HELIODOR).setRegistryName("geode_heliodor"),
            new QuernRecipeRandomGemTFCE(IIngredient.of("gemGeodeIodargyrite"), GemTFCE.IODARGYRITE).setRegistryName("geode_iodargyrite"),
            new QuernRecipeRandomGemTFCE(IIngredient.of("gemGeodeKyanite"), GemTFCE.KYANITE).setRegistryName("geode_kyanite"),
            new QuernRecipeRandomGemTFCE(IIngredient.of("gemGeodeMoldavite"), GemTFCE.MOLDAVITE).setRegistryName("geode_moldavite"),
            new QuernRecipeRandomGemTFCE(IIngredient.of("gemGeodeMoonstone"), GemTFCE.MOONSTONE).setRegistryName("geode_moonstone"),
            new QuernRecipeRandomGemTFCE(IIngredient.of("gemGeodePyromorphite"), GemTFCE.PYROMORPHITE).setRegistryName("geode_pyromorphite"),
            new QuernRecipeRandomGemTFCE(IIngredient.of("gemGeodeQuartz"), GemTFCE.QUARTZ).setRegistryName("geode_quartz"),
            new QuernRecipeRandomGemTFCE(IIngredient.of("gemGeodeSpinel"), GemTFCE.SPINEL).setRegistryName("geode_spinel"),
            new QuernRecipeRandomGemTFCE(IIngredient.of("gemGeodeSunstone"), GemTFCE.SUNSTONE).setRegistryName("geode_sunstone"),
            new QuernRecipeRandomGemTFCE(IIngredient.of("gemGeodeTanzanite"), GemTFCE.TANZANITE).setRegistryName("geode_tanzanite"),
            new QuernRecipeRandomGemTFCE(IIngredient.of("gemGeodeZircon"), GemTFCE.ZIRCON).setRegistryName("geode_zircon"),
            
            // Dye from plants
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.CHAMOMILE))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_chamomile"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.HYDRANGEA))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_hydrangea"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.LILY_OF_THE_VALLEY))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_lily_of_the_valley"),
            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.WHITE_HYDRANGEA))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_white_hydrangea"),
            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.WHITE_PEONY))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_white_peony"),
            
            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.ORANGE_PEONY))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("crushed_orange_peony"),

            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCE.SUNFLOWER))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_sunflower"),

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