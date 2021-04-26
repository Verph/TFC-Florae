package tfcflorae.types;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import com.eerussianguy.firmalife.ConfigFL;
import com.eerussianguy.firmalife.FirmaLife;
import com.eerussianguy.firmalife.recipe.DryingRecipe;
import com.eerussianguy.firmalife.recipe.OvenRecipe;
import com.eerussianguy.firmalife.registry.BlocksFL;
import com.eerussianguy.firmalife.registry.ItemsFL;

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
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import tfcelementia.objects.items.metal.ItemMetalTFCE;
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
import net.dries007.tfc.api.types.IFruitTree;
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
import net.dries007.tfc.util.agriculture.FruitTree;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.fuel.FuelManager;
import net.dries007.tfc.util.skills.SmithingSkill;
import net.dries007.tfc.util.skills.SmithingSkill.Type;

import tfcflorae.TFCFlorae;
import tfcflorae.api.knapping.KnappingTypes;
import tfcflorae.api.recipes.CrackingRecipe;
import tfcflorae.api.recipes.NutRecipe;
import tfcflorae.compat.tfcelementia.ceramics.ItemKaoliniteMoldTFCE;
import tfcflorae.compat.tfcelementia.ceramics.ItemUnfiredKaoliniteMoldTFCE;
import tfcflorae.objects.PowderTFCF;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.blocktype.*;
import tfcflorae.objects.blocks.groundcover.*;
import tfcflorae.objects.blocks.wood.BlockLeavesTFCF;
import tfcflorae.objects.fluids.FluidsTFCF;
import tfcflorae.objects.items.*;
import tfcflorae.objects.items.ceramics.ItemKaoliniteMold;
import tfcflorae.objects.items.ceramics.ItemUnfiredKaoliniteMold;
import tfcflorae.objects.items.rock.ItemFiredMudBrick;
import tfcflorae.objects.items.rock.ItemMud;
import tfcflorae.objects.items.rock.ItemUnfiredMudBrick;
import tfcflorae.types.PlantsTFCF;
import tfcflorae.types.BlockTypesTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;
import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.util.agriculture.FoodDataTFCF;
import tfcflorae.util.agriculture.FruitTreeTFCF;

import static net.dries007.tfc.api.types.Metal.ItemType.*;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_MISC;
import static net.dries007.tfc.objects.fluids.FluidsTFC.*;
import static net.dries007.tfc.types.DefaultMetals.*;
import static net.dries007.tfc.util.Helpers.getNull;
import static net.dries007.tfc.util.forge.ForgeRule.*;
import static net.dries007.tfc.util.skills.SmithingSkill.Type.*;

import static tfcflorae.TFCFlorae.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public final class RecipesTFCF
{
    @SuppressWarnings("rawtypes")
    @SubscribeEvent
    public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event)
    {
        if (TFCFlorae.FirmaLifeAdded)
        {
            for (FruitTreeTFCF fruitTreeTFCF : FruitTreeTFCF.values())
            {
                String nameTFCF = fruitTreeTFCF.getName().toLowerCase();
                IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
                String[] regNames = {
                    "wood/fruit_tree/door/" + nameTFCF,
                    "wood/fruit_tree/fence/" + nameTFCF,
                    "wood/fruit_tree/fence_gate/" + nameTFCF,
                    "wood/fruit_tree/trapdoor/" + nameTFCF
                };
                for (String name : regNames)
                {
                    IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
                    if (recipe != null)
                    {
                        registry.remove(recipe.getRegistryName());
                        TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
                    }
                }
            }

            for (IFruitTree fruitTree : FruitTree.values())
            {
                String nameTFC = fruitTree.getName().toLowerCase();
                IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
                String[] regNames = {
                    "wood/fruit_tree/door/" + nameTFC,
                    "wood/fruit_tree/fence/" + nameTFC,
                    "wood/fruit_tree/fence_gate/" + nameTFC,
                    "wood/fruit_tree/trapdoor/" + nameTFC
                };
                for (String name : regNames)
                {
                    IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
                    if (recipe != null)
                    {
                        registry.remove(recipe.getRegistryName());
                        TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
                    }
                }
            }
        }

        if (!TFCFlorae.FirmaLifeAdded)
        {
            for (FruitTreeTFCF fruitTreeTFCF : FruitTreeTFCF.values())
            {
                String nameTFCF = fruitTreeTFCF.getName().toLowerCase();
                IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
                String[] regNames = {
                    "wood/fruit_tree/firmalife/door/" + nameTFCF,
                    "wood/fruit_tree/firmalife/fence/" + nameTFCF,
                    "wood/fruit_tree/firmalife/fence_gate/" + nameTFCF,
                    "wood/fruit_tree/firmalife/trapdoor/" + nameTFCF
                };
                for (String name : regNames)
                {
                    IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
                    if (recipe != null)
                    {
                        registry.remove(recipe.getRegistryName());
                        TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
                    }
                }
            }

            for(Tree wood : TFCRegistries.TREES.getValuesCollection())
            {
                String nameTFCF = wood.getRegistryName().getPath().toLowerCase();
                IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
                String[] regNames = {
                    "wood/fruit_tree/firmalife/door/" + nameTFCF,
                    "wood/fruit_tree/firmalife/fence/" + nameTFCF,
                    "wood/fruit_tree/firmalife/fence_gate/" + nameTFCF,
                    "wood/fruit_tree/firmalife/trapdoor/" + nameTFCF
                };
                for (String name : regNames)
                {
                    IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
                    if (recipe != null)
                    {
                        registry.remove(recipe.getRegistryName());
                        TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
                    }
                }
            }

            for (IFruitTree fruitTree : FruitTree.values())
            {
                String nameTFC = fruitTree.getName().toLowerCase();
                IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
                String[] regNames = {
                    "wood/fruit_tree/firmalife/door/" + nameTFC,
                    "wood/fruit_tree/firmalife/fence/" + nameTFC,
                    "wood/fruit_tree/firmalife/fence_gate/" + nameTFC,
                    "wood/fruit_tree/firmalife/trapdoor/" + nameTFC
                };
                for (String name : regNames)
                {
                    IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
                    if (recipe != null)
                    {
                        registry.remove(recipe.getRegistryName());
                        TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
                    }
                }
            }

            IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
            String[] regNames = {
                "food/flatbread_dough/amaranth", "food/flatbread_dough/buckwheat", "food/flatbread_dough/fonio", "food/flatbread_dough/millet", "food/flatbread_dough/quinoa", "food/flatbread_dough/spelt", "food/flatbread_dough/wild_rice",
                "food/dough_yeast/amaranth", "food/dough_yeast/buckwheat", "food/dough_yeast/fonio", "food/dough_yeast/millet", "food/dough_yeast/quinoa", "food/dough_yeast/spelt", "food/dough_yeast/wild_rice",
                "food/sandwich_slice/amaranth", "food/sandwich_slice/buckwheat", "food/sandwich_slice/fonio", "food/sandwich_slice/millet", "food/sandwich_slice/quinoa", "food/sandwich_slice/spelt", "food/sandwich_slice/wild_rice",
                "metal/unmold/mallet_head", "food/pinecone", "yeast"
            };
            for (String name : regNames)
            {
                IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
                if (recipe != null)
                {
                    registry.remove(recipe.getRegistryName());
                    TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
                }
            }
        }

        if (!TFCFlorae.TFCElementiaAdded)
        {
            IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
            String[] regNames = {
                "metal/unmold/halberd_blade",
                "metal/unmold/metal_block",
                "metal/unmold/nail",
                "metal/unmold/ring",
                "metal/unmold/sheet_metal_block"
            };
            for (String name : regNames)
            {
                IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
                if (recipe != null)
                {
                    registry.remove(recipe.getRegistryName());
                    TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
                }
            }
        }
    }

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
            
            // Fluid Production from paste

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

            // Dirty Nets
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_SISAL_NET), null, new ItemStack(ItemsTFCF.SISAL_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_SILK_NET), null, new ItemStack(ItemsTFCF.SILK_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_COTTON_NET), null, new ItemStack(ItemsTFCF.COTTON_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_LINEN_NET), null, new ItemStack(ItemsTFCF.LINEN_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_HEMP_NET), null, new ItemStack(ItemsTFCF.HEMP_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_hemp"),

            // Sugary Fluids
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("sugar_water_from_sugar_fresh"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of("dropHoney"), new FluidStack(FluidsTFCF.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("honey_water_from_drop_honey_fresh"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of("itemHoney"), new FluidStack(FluidsTFCF.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("honey_water_from_item_honey_fresh"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of("rawHoney"), new FluidStack(FluidsTFCF.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("honey_water_from_raw_honey_fresh"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsFL.HONEYCOMB), new FluidStack(FluidsTFCF.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("honey_water_from_fl_raw_honey_fresh"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of("materialHoneycomb"), new FluidStack(FluidsTFCF.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("honey_water_from_material_honeycomb_fresh"),

            //new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_WATER.get(), 125), null, null, new ItemStack(Items.SUGAR), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_from_sugar_water"),
            //new BarrelRecipe(IIngredient.of(FluidsTFCF.HONEY_WATER.get(), 125), null, null, new ItemStack(Items.SUGAR), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_from_honey_water"),

            // Dyes
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropAgave"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("green_dye_agave"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropIndigo"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_indigo"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropMadder"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.RED).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("red_dye_madder"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropWeld"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.YELLOW).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("yellow_dye_weld"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropWoad"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_woad"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropRape"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_rape"),
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
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_AGAVE.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.AGAVE_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("agave_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_BANANA.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.BANANA_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("banana_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_CHERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.CHERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cherry_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_GREEN_GRAPE.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.WHITE_WINE.get(), 500), new ItemStack(ItemsTFCF.POMACE), 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("white_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_JUNIPER.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.JUNIPER_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("juniper_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_LEMON.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.LEMON_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("lemon_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.HONEY_WATER.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.MEAD.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("mead"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_ORANGE.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.ORANGE_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("orange_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PAPAYA.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.PAPAYA_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("papaya_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PEACH.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.PEACH_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("peach_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PEAR.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.PEAR_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("pear_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PLUM.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.PLUM_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("plum_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PURPLE_GRAPE.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.RED_WINE.get(), 500), new ItemStack(ItemsTFCF.POMACE), 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("red_wine"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.RICE_WATER.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFC.SAKE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("sake_rice_water"),

            // Berry Wine
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_BLACKBERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_blackberry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_BLUEBERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_blueberry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_BUNCH_BERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_bunch_berry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_CLOUD_BERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_cloud_berry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_CRANBERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_cranberry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_ELDERBERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_elderberry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_GOOSEBERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_gooseberry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_RASPBERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_raspberry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_SNOW_BERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_snow_berry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_STRAWBERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_strawberry"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_WINTERGREEN_BERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_wintergreen_berry"),

            // "Distilled" Alcohol
            new BarrelRecipe(IIngredient.of(FluidsTFCF.AGAVE_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.TEQUILA.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("tequila"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.BANANA_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.BANANA_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("banana_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.BERRY_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.BERRY_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.CIDER.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.CALVADOS.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("calvados"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.CHERRY_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.CHERRY_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cherry_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.JUNIPER_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.GIN.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("gin"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.LEMON_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.LEMON_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("lemon_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.ORANGE_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.ORANGE_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("orange_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.PAPAYA_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.PAPAYA_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("papaya_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.PEACH_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.PEACH_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("peach_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.PEAR_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.PEAR_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("pear_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.PLUM_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.PLUM_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("plum_brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.RED_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("brandy"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.SAKE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.SHOCHU.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("shochu"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.WHITE_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.COGNAC.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cognac"),
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
            //new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("wildRice"), new FluidStack(FluidsTFCF.RICE_WATER.get(), 500), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("wild_rice_water"),

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
        event.getRegistry().register(r.setRegistryName(TFCFlorae.MODID, "knapping_mud_brick"));

        // Kaolinite Clay Items
        for (Metal.ItemType type : Metal.ItemType.values())
        {
            if (type.hasMold(null))
            {
                int amount = type == INGOT ? 2 : 1;
                event.getRegistry().register(new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemUnfiredKaoliniteMold.get(type), amount), type.getPattern()).setRegistryName(TFCFlorae.MODID, "kaolinite_" + type.name().toLowerCase() + "_mold"));
            }
        }

        event.getRegistry().registerAll(
            
            // Kaolinite Clay
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_BRICK, 3), "XXXXX", "     ", "XXXXX", "     ", "XXXXX").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_brick"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_VESSEL), " XXX ", "XXXXX", "XXXXX", "XXXXX", " XXX ").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_small_vessel"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_JUG), " X   ", "XXXX ", "XXX X", "XXXX ", "XXX  ").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_jug"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_POT), "X   X", "X   X", "X   X", "XXXXX", " XXX ").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_pot"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, false, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_BOWL, 2), "X   X", " XXX ").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_bowl"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_BOWL, 4), "X   X", " XXX ", "     ", "X   X", " XXX ").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_bowl_2"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_LARGE_VESSEL), "X   X", "X   X", "X   X", "X   X", "XXXXX").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_large_vessel"),

            // Flint Tool Heads
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_AXE_HEAD, 1), " X   ", "XXXX ", "XXXXX", "XXXX ", " X   ").setRegistryName(TFCFlorae.MODID, "flint_axe_head"),

            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HAMMER_HEAD, 1), "XXXXX", "XXXXX", "  X  ").setRegistryName(TFCFlorae.MODID, "flint_hammer_head"),

            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HOE_HEAD, 1), "XXXXX", "   XX").setRegistryName(TFCFlorae.MODID, "flint_hoe_head_1"),
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HOE_HEAD, 2), "XXXXX", "XX   ", "     ", "XXXXX", "XX   ").setRegistryName(TFCFlorae.MODID, "flint_hoe_head_2"),
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HOE_HEAD, 2), "XXXXX", "XX   ", "     ", "XXXXX", "   XX").setRegistryName(TFCFlorae.MODID, "flint_hoe_head_3"),

            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_JAVELIN_HEAD, 1), "XXX  ", "XXXX ", "XXXXX", " XXX ", "  X  ").setRegistryName(TFCFlorae.MODID, "flint_javelin_head"),

            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 1), "X ", "XX", "XX", "XX", "XX").setRegistryName(TFCFlorae.MODID, "flint_knife_head_1"),
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 1), " X", "XX", "XX", "XX", "XX").setRegistryName(TFCFlorae.MODID, "flint_knife_head_2"),
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 2), "X  X ", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName(TFCFlorae.MODID, "flint_knife_head_3"),
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 2), "X   X", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName(TFCFlorae.MODID, "flint_knife_head_4"),
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 2), " X X ", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName(TFCFlorae.MODID, "flint_knife_head_5"),

            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_SHOVEL_HEAD, 1), "XXX", "XXX", "XXX", "XXX", " X ").setRegistryName(TFCFlorae.MODID, "flint_shovel_head"),

            new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_URN), "XX XX", "X   X", "X   X", "X   X", "XXXXX").setRegistryName(TFCFlorae.MODID, "clay_urn"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_URN), "XX XX", "X   X", "X   X", "X   X", "XXXXX").setRegistryName(TFCFlorae.MODID, "kaolinite_urn")
        );
    }

    @SuppressWarnings("rawtypes")
    @SubscribeEvent
    public static void onRegisterHeatRecipeEvent(RegistryEvent.Register<HeatRecipe> event)
    {
        // Remove recipes
        IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) TFCRegistries.HEAT;
        String[] regNames = {"grilled_mushroom"};
        for (String name : regNames)
        {
            HeatRecipe recipe = TFCRegistries.HEAT.getValue(new ResourceLocation("tfc", name));
            if (recipe != null)
            {
                modRegistry.remove(recipe.getRegistryName());
                TFCFlorae.logger.info("Removed heating recipe tfc:{}", name);
            }
        }

        // Mud Pottery
        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
        {
            ItemFiredMudBrick firedMudBrick = ItemFiredMudBrick.get(ItemUnfiredMudBrick.get(rock));

            HeatRecipe r = new HeatRecipeSimple(IIngredient.of(ItemUnfiredMudBrick.get(rock)), new ItemStack(firedMudBrick), 600f, Metal.Tier.TIER_I);
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

        // Bread
        if (!ConfigFL.General.COMPAT.removeTFC)
        {
            r.registerAll(

            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.AMARANTH_DOUGH), new ItemStack(ItemsTFCF.AMARANTH_BREAD), 200, 480).setRegistryName("amaranth_bread"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.BUCKWHEAT_DOUGH), new ItemStack(ItemsTFCF.BUCKWHEAT_BREAD), 200, 480).setRegistryName("buckwheat_bread"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FONIO_DOUGH), new ItemStack(ItemsTFCF.FONIO_BREAD), 200, 480).setRegistryName("fonio_bread"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.MILLET_DOUGH), new ItemStack(ItemsTFCF.MILLET_BREAD), 200, 480).setRegistryName("millet_bread"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.QUINOA_DOUGH), new ItemStack(ItemsTFCF.QUINOA_BREAD), 200, 480).setRegistryName("quinoa_bread"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.SPELT_DOUGH), new ItemStack(ItemsTFCF.SPELT_BREAD), 200, 480).setRegistryName("spelt_bread")
            );
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

            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_URN), new ItemStack(BlocksTFCF.FIRED_URN), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_urn"),
            new HeatRecipeSimple(IIngredient.of(BlocksTFCF.FIRED_URN), new ItemStack(BlocksTFCF.FIRED_URN), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_urn"),

            // Bread
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.AMARANTH_BREAD), 480).setRegistryName("burned_barley_bread"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.BUCKWHEAT_BREAD), 480).setRegistryName("burned_cornbread"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.FONIO_BREAD), 480).setRegistryName("burned_oat_bread"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.MILLET_BREAD), 480).setRegistryName("burned_rice_bread"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.QUINOA_BREAD), 480).setRegistryName("burned_rye_bread"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.SPELT_BREAD), 480).setRegistryName("burned_wheat_bread"),

            // Epiphytes
            new HeatRecipeSimple(IIngredient.of("rawArtistsConk"), new ItemStack(ItemsTFCF.ROASTED_ARTISTS_CONK, 1), 200, 480).setRegistryName("roasted_artists_conk"),
            new HeatRecipeSimple(IIngredient.of("rawSulphurShelf"), new ItemStack(ItemsTFCF.ROASTED_SULPHUR_SHELF, 1), 200, 480).setRegistryName("roasted_sulphur_shelf"),
            new HeatRecipeSimple(IIngredient.of("rawTurkeyTail"), new ItemStack(ItemsTFCF.ROASTED_TURKEY_TAIL, 1), 200, 480).setRegistryName("roasted_turkey_tail"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_ARTISTS_CONK), 480).setRegistryName("burned_artists_conk"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_SULPHUR_SHELF), 480).setRegistryName("burned_sulphur_shelf"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_TURKEY_TAIL), 480).setRegistryName("burned_turkey_tail"),

            // Mushrooms
            new HeatRecipeSimple(IIngredient.of("rawPorcini"), new ItemStack(ItemsTFCF.ROASTED_PORCINI, 1), 200, 480).setRegistryName("roasted_porcini"),
            new HeatRecipeSimple(IIngredient.of("rawAmanita"), new ItemStack(ItemsTFCF.ROASTED_AMANITA, 1), 200, 480).setRegistryName("roasted_amanita"),
            new HeatRecipeSimple(IIngredient.of("rawBlackPowderpuff"), new ItemStack(ItemsTFCF.ROASTED_BLACK_POWDERPUFF, 1), 200, 480).setRegistryName("roasted_black_powderpuff"),
            new HeatRecipeSimple(IIngredient.of("rawChanterelle"), new ItemStack(ItemsTFCF.ROASTED_CHANTERELLE, 1), 200, 480).setRegistryName("roasted_chanterelle"),
            new HeatRecipeSimple(IIngredient.of("rawDeathCap"), new ItemStack(ItemsTFCF.ROASTED_DEATH_CAP, 1), 200, 480).setRegistryName("roasted_death_cap"),
            new HeatRecipeSimple(IIngredient.of("rawGiantClub"), new ItemStack(ItemsTFCF.ROASTED_GIANT_CLUB, 1), 200, 480).setRegistryName("roasted_giant_club"),
            new HeatRecipeSimple(IIngredient.of("rawParasolMushroom"), new ItemStack(ItemsTFCF.ROASTED_PARASOL_MUSHROOM, 1), 200, 480).setRegistryName("roasted_parasol_mushroom"),
            new HeatRecipeSimple(IIngredient.of("rawStinkhorn"), new ItemStack(ItemsTFCF.ROASTED_STINKHORN, 1), 200, 480).setRegistryName("roasted_stinkhorn"),
            new HeatRecipeSimple(IIngredient.of("rawWeepingMilkCap"), new ItemStack(ItemsTFCF.ROASTED_WEEPING_MILK_CAP, 1), 200, 480).setRegistryName("roasted_weeping_milk_cap"),
            new HeatRecipeSimple(IIngredient.of("rawWoodBlewit"), new ItemStack(ItemsTFCF.ROASTED_WOOD_BLEWIT, 1), 200, 480).setRegistryName("roasted_wood_blewit"),
            new HeatRecipeSimple(IIngredient.of("rawWoollyGomphus"), new ItemStack(ItemsTFCF.ROASTED_WOOLLY_GOMPHUS, 1), 200, 480).setRegistryName("roasted_woolly_gomphus"),

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
            new HeatRecipeSimple(IIngredient.of("rawEel"), new ItemStack(ItemsTFCF.COOKED_EEL, 1), 200, 480).setRegistryName("cooked_eel"),
            new HeatRecipeSimple(IIngredient.of("rawCrab"), new ItemStack(ItemsTFCF.COOKED_CRAB, 1), 200, 480).setRegistryName("cooked_crab"),
            new HeatRecipeSimple(IIngredient.of("rawClam"), new ItemStack(ItemsTFCF.COOKED_CLAM, 1), 200, 480).setRegistryName("cooked_clam"),
            new HeatRecipeSimple(IIngredient.of("rawScallop"), new ItemStack(ItemsTFCF.COOKED_SCALLOP, 1), 200, 480).setRegistryName("cooked_scallop"),
            new HeatRecipeSimple(IIngredient.of("rawStarfish"), new ItemStack(ItemsTFCF.COOKED_STARFISH, 1), 200, 480).setRegistryName("cooked_starfish"),
            new HeatRecipeSimple(IIngredient.of("rawSnail"), new ItemStack(ItemsTFCF.COOKED_SNAIL, 1), 200, 480).setRegistryName("cooked_snail"),
            new HeatRecipeSimple(IIngredient.of("rawWorm"), new ItemStack(ItemsTFCF.COOKED_WORM, 1), 200, 480).setRegistryName("cooked_worm"),

            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_EEL), 480).setRegistryName("burned_eel"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_CRAB), 480).setRegistryName("burned_crab"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_CLAM), 480).setRegistryName("burned_clam"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_SCALLOP), 480).setRegistryName("burned_scallop"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_STARFISH), 480).setRegistryName("burned_starfish"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_SNAIL), 480).setRegistryName("burned_snail"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_WORM), 480).setRegistryName("burned_worm"),

            // Nut Roasting
            /*
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.ALMOND_NUT)), new ItemStack(ItemsTFCF.ROASTED_ALMOND_NUT), 200, 480).setRegistryName("roasted_almond"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BRAZIL_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BRAZIL_NUT_NUT), 200, 480).setRegistryName("roasted_brazil_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BREADNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BREADNUT_NUT), 200, 480).setRegistryName("roasted_breadnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BUNYA_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BUNYA_NUT_NUT), 200, 480).setRegistryName("roasted_bunya_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CANDLENUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_CANDLENUT_NUT), 200, 480).setRegistryName("roasted_candlenut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CASHEW_NUT)), new ItemStack(ItemsTFCF.ROASTED_CASHEW_NUT), 200, 480).setRegistryName("roasted_cashew"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.HEARTNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HEARTNUT_NUT), 200, 480).setRegistryName("roasted_heartnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.KOLA_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_KOLA_NUT_NUT), 200, 480).setRegistryName("roasted_kola_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.KUKUI_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_KUKUI_NUT_NUT), 200, 480).setRegistryName("roasted_kukui_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.MACADAMIA_NUT)), new ItemStack(ItemsTFCF.ROASTED_MACADAMIA_NUT), 200, 480).setRegistryName("roasted_macadamia"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.MONGONGO_NUT)), new ItemStack(ItemsTFCF.ROASTED_MONGONGO_NUT), 200, 480).setRegistryName("roasted_mongongo"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.MONKEY_PUZZLE_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_MONKEY_PUZZLE_NUT_NUT), 200, 480).setRegistryName("roasted_monkey_puzzle_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.NUTMEG_NUT)), new ItemStack(ItemsTFCF.ROASTED_NUTMEG_NUT), 200, 480).setRegistryName("roasted_nutmeg"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PARADISE_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_PARADISE_NUT_NUT), 200, 480).setRegistryName("roasted_paradise_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PISTACHIO_NUT)), new ItemStack(ItemsTFCF.ROASTED_PISTACHIO_NUT), 200, 480).setRegistryName("roasted_pistachio"),
            */

            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BEECHNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BEECHNUT_NUT), 200, 480).setRegistryName("roasted_beechnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BLACK_WALNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BLACK_WALNUT_NUT), 200, 480).setRegistryName("roasted_black_walnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BUTTERNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BUTTERNUT_NUT), 200, 480).setRegistryName("roasted_butternut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.GINKGO_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_GINKGO_NUT_NUT), 200, 480).setRegistryName("roasted_ginkgo_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.HAZELNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HAZELNUT_NUT), 200, 480).setRegistryName("roasted_hazelnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.WALNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_WALNUT_NUT), 200, 480).setRegistryName("roasted_walnut"),

            // Nut Destroy
            /*
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_ALMOND_NUT), 480).setRegistryName("burned_almond"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BRAZIL_NUT_NUT), 480).setRegistryName("burned_brazil_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BREADNUT_NUT), 480).setRegistryName("burned_breadnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BUNYA_NUT_NUT), 480).setRegistryName("burned_bunya_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_CANDLENUT_NUT), 480).setRegistryName("burned_candlenut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_CASHEW_NUT), 480).setRegistryName("burned_cashew"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_HEARTNUT_NUT), 480).setRegistryName("burned_heartnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_KOLA_NUT_NUT), 480).setRegistryName("burned_kola_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_KUKUI_NUT_NUT), 480).setRegistryName("burned_kukui_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_MACADAMIA_NUT), 480).setRegistryName("burned_macadamia"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_MONGONGO_NUT), 480).setRegistryName("burned_mongongo"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_MONKEY_PUZZLE_NUT_NUT), 480).setRegistryName("burned_monkey_puzzle_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_NUTMEG_NUT), 480).setRegistryName("burned_nutmeg"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PARADISE_NUT_NUT), 480).setRegistryName("burned_paradise_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PISTACHIO_NUT), 480).setRegistryName("burned_pistachio"),
            */

            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BEECHNUT_NUT), 480).setRegistryName("burned_beechnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BLACK_WALNUT_NUT), 480).setRegistryName("burned_black_walnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BUTTERNUT_NUT), 480).setRegistryName("burned_butternut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_GINKGO_NUT_NUT), 480).setRegistryName("burned_ginkgo_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_HAZELNUT_NUT), 480).setRegistryName("burned_hazelnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_WALNUT_NUT), 480).setRegistryName("burned_walnut"),

            // Kaolinite Clay
            new HeatRecipeSimple(IIngredient.of("clayKaolinite"), new ItemStack(ItemPowder.get(Powder.KAOLINITE), 1), 150).setRegistryName("kaolinite_clay"),
            new HeatRecipeSimple(IIngredient.of(ItemPowder.get(Powder.KAOLINITE)), new ItemStack(ItemPowder.get(Powder.KAOLINITE), 1), 1599f, Metal.Tier.TIER_I).setRegistryName("burnt_kaolinite_clay"),

        	// Ash
            new HeatRecipeSimple(IIngredient.of("twigWood"), new ItemStack(Blocks.TORCH, 4), 50).setRegistryName("torch_twig_wood"),
            new HeatRecipeSimple(IIngredient.of("twig"), new ItemStack(Blocks.TORCH, 4), 50).setRegistryName("torch_twig"),
            new HeatRecipeSimple(IIngredient.of("branchWood"), new ItemStack(Blocks.TORCH, 4), 50).setRegistryName("torch_branch_wood"),
            
            new HeatRecipeSimple(IIngredient.of("torch"), new ItemStack(ItemsTFC.WOOD_ASH, 2), 425, 850).setRegistryName("torch_ore_ash"),
            new HeatRecipeSimple(IIngredient.of(Blocks.TORCH), new ItemStack(ItemsTFC.WOOD_ASH, 2), 425, 850).setRegistryName("torch_item_ash"),

            // Charred Bones
            new HeatRecipeSimple(IIngredient.of("bone"), new ItemStack(ItemsTFCF.CHARRED_BONES, 1), 425, 850).setRegistryName("charred_bones_heat"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CHARRED_BONES)), new ItemStack(ItemsTFCF.CHARRED_BONES, 1), 1599f, Metal.Tier.TIER_I).setRegistryName("burnt_charred_bones")
        );

        if (!TFCFlorae.FirmaLifeAdded)
        {
            r.registerAll(

            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.ACORN_NUT)), new ItemStack(ItemsTFCF.ROASTED_ACORN_NUT), 200, 480).setRegistryName("roasted_acorn"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CHESTNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_CHESTNUT_NUT), 200, 480).setRegistryName("roasted_chestnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.HICKORY_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HICKORY_NUT_NUT), 200, 480).setRegistryName("roasted_hickory_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PECAN_NUT)), new ItemStack(ItemsTFCF.ROASTED_PECAN_NUT), 200, 480).setRegistryName("roasted_pecan"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PINE_NUT)), new ItemStack(ItemsTFCF.ROASTED_PINE_NUT), 200, 480).setRegistryName("roasted_pine_nut"),

            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_ACORN_NUT), 480).setRegistryName("burned_acorn"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_CHESTNUT_NUT), 480).setRegistryName("burned_chestnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_HICKORY_NUT_NUT), 480).setRegistryName("burned_hickory_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PECAN_NUT), 480).setRegistryName("burned_pecan"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PINE_NUT), 480).setRegistryName("burned_pine_nut"),

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
            //new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.DRIED_COCOA_BEANS)), new ItemStack(ItemsTFCF.ROASTED_COCOA_BEANS), 200, 480).setRegistryName("roasted_cocoa_beans"),
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
            //HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_COCOA_BEANS), 480).setRegistryName("burned_cocoa_beans"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_COFFEE_BEANS), 480).setRegistryName("burned_coffea_cherries"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_CHAMOMILE_HEAD), 480).setRegistryName("burned_chamomile_head"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_DANDELION_HEAD), 480).setRegistryName("burned_dandelion_head"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD), 480).setRegistryName("burned_labrador_tea_head"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_SUNFLOWER_HEAD), 480).setRegistryName("burned_sunflower_head")
            );
        }
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

            new QuernRecipe(IIngredient.of("logWoodLogwood"), new ItemStack((ItemsTFCF.LOGWOOD_CHIPS), 3)).setRegistryName("chipped_logwood_log"),

            // Mashed Products
            new QuernRecipe(IIngredient.of("sugarcane"), new ItemStack((ItemsTFCF.MASHED_SUGAR_CANE), 1)).setRegistryName("mashed_sugar_cane_quern"),
            new QuernRecipe(IIngredient.of("cropSugarBeet"), new ItemStack((ItemsTFCF.MASHED_SUGAR_BEET), 1)).setRegistryName("mashed_sugar_beet_quern"),

            //Grain
            new QuernRecipe(IIngredient.of("grainAmaranth"), new ItemStack((ItemsTFCF.AMARANTH_FLOUR), 1)).setRegistryName("amaranth"),
            new QuernRecipe(IIngredient.of("grainBuckwheat"), new ItemStack((ItemsTFCF.BUCKWHEAT_FLOUR), 1)).setRegistryName("buckwheat"),
            new QuernRecipe(IIngredient.of("grainFonio"), new ItemStack((ItemsTFCF.FONIO_FLOUR), 1)).setRegistryName("fonio"),
            new QuernRecipe(IIngredient.of("grainMillet"), new ItemStack((ItemsTFCF.MILLET_FLOUR), 1)).setRegistryName("millet"),
            new QuernRecipe(IIngredient.of("grainQuinoa"), new ItemStack((ItemsTFCF.QUINOA_FLOUR), 1)).setRegistryName("quinoa"),
            new QuernRecipe(IIngredient.of("grainSpelt"), new ItemStack((ItemsTFCF.SPELT_FLOUR), 1)).setRegistryName("spelt"),

            // Ground Spices
            new QuernRecipe(IIngredient.of(ItemsTFCF.CASSIA_CINNAMON_BARK), new ItemStack(ItemsTFCF.GROUND_CASSIA_CINNAMON, 2)).setRegistryName("ground_cassia_cinnamon"),
            new QuernRecipe(IIngredient.of(ItemsTFCF.CEYLON_CINNAMON_BARK), new ItemStack(ItemsTFCF.GROUND_CEYLON_CINNAMON, 2)).setRegistryName("ground_ceylon_cinnamon"),
            //new QuernRecipe(IIngredient.of(ItemsTFCF.BLACK_PEPPER), new ItemStack(ItemsTFCF.GROUND_BLACK_PEPPER, 2)).setRegistryName("ground_black_pepper"),
            //new QuernRecipe(IIngredient.of(ItemsTFCF.ROASTED_COCOA_BEANS), new ItemStack(ItemsTFCF.COCOA_POWDER, 2)).setRegistryName("ground_cocoa_beans"),
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

            new QuernRecipe(IIngredient.of("cropMadder"), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("crushed_madder"),
            
            new QuernRecipe(IIngredient.of("cropWoad"), new ItemStack(ItemsTFC.DYE_BLUE, 2)).setRegistryName("crushed_woad"),
            new QuernRecipe(IIngredient.of("cropIndigo"), new ItemStack(ItemsTFC.DYE_BLUE, 2)).setRegistryName("crushed_indigo"),
            
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SUNFLOWER))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_sunflower"),
            new QuernRecipe(IIngredient.of("cropWeld"), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_weld"),
            new QuernRecipe(IIngredient.of("cropRape"), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_rape"),
            
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LILAC))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_lilac"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PEONY))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_peony"),
            
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LAVANDULA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage())).setRegistryName("crushed_lavandula"),
            
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CATTAIL))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("crushed_cattail"),

            new QuernRecipe(IIngredient.of("cropAgave"), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_agave"),
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
        Tree walnut = TFCRegistries.TREES.getValue(TreesTFCF.WALNUT);
        Tree white_cherry = TFCRegistries.TREES.getValue(TreesTFCF.WHITE_CHERRY);
        Tree whitebeam = TFCRegistries.TREES.getValue(TreesTFCF.WHITEBEAM);
        Tree yew = TFCRegistries.TREES.getValue(TreesTFCF.YEW);

        /*if (!TFCFlorae.FirmaLifeAdded)
        {
            r.registerAll(

            new NutRecipe(BlockLogTFC.get(oak), BlockLeavesTFC.get(oak), new ItemStack(ItemsTFCF.ACORN)).setRegistryName("oak_nut"),
            new NutRecipe(BlockLogTFC.get(chestnut), BlockLeavesTFC.get(chestnut), new ItemStack(ItemsTFCF.CHESTNUT)).setRegistryName("chestnut_nut"),
            new NutRecipe(BlockLogTFC.get(hickory), BlockLeavesTFC.get(hickory), new ItemStack(ItemsTFCF.PECAN)).setRegistryName("hickory_pecan"),
            new NutRecipe(BlockLogTFC.get(pine), BlockLeavesTFC.get(pine), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("pine_pinecone")
            );
        }*/
        r.registerAll(
            
            new NutRecipe(BlockLogTFC.get(european_oak), BlockLeavesTFC.get(european_oak), new ItemStack(ItemsTFCF.ACORN)).setRegistryName("european_oak_nut"),
            //new NutRecipe(BlockLogTFC.get(baobab), BlockLeavesTFCF.get(FruitTreeTFCF.BAOBAB), new ItemStack(ItemsTFCF.BAOBAB_FRUIT)).setRegistryName("baobab_fruit"),
            new NutRecipe(BlockLogTFC.get(beech), BlockLeavesTFC.get(beech), new ItemStack(ItemsTFCF.BEECHNUT)).setRegistryName("beech_nut"),
            new NutRecipe(BlockLogTFC.get(black_walnut), BlockLeavesTFC.get(black_walnut), new ItemStack(ItemsTFCF.BLACK_WALNUT)).setRegistryName("black_walnut_nut"),
            new NutRecipe(BlockLogTFC.get(butternut), BlockLeavesTFC.get(butternut), new ItemStack(ItemsTFCF.BUTTERNUT)).setRegistryName("butternut_nut"),
            new NutRecipe(BlockLogTFC.get(ginkgo), BlockLeavesTFC.get(ginkgo), new ItemStack(ItemsTFCF.GINKGO_NUT)).setRegistryName("ginkgo_nut"),
            //new NutRecipe(BlockLogTFC.get(hawthorn), BlockLeavesTFCF.get(FruitTreeTFCF.HAWTHORN), new ItemStack(ItemsTFCF.HAWTHORN)).setRegistryName("hawthorn_fruit"),
            new NutRecipe(BlockLogTFC.get(hazel), BlockLeavesTFC.get(hazel), new ItemStack(ItemsTFCF.HAZELNUT)).setRegistryName("hazel_nut"),
            new NutRecipe(BlockLogTFC.get(hemlock), BlockLeavesTFC.get(hemlock), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("hemlock_pinecone"),
            new NutRecipe(BlockLogTFC.get(hornbeam), BlockLeavesTFC.get(hornbeam), new ItemStack(ItemsTFCF.HOPS)).setRegistryName("hornbeam_hops"),
            //new NutRecipe(BlockLogTFC.get(hickory), BlockLeavesTFC.get(hickory), new ItemStack(ItemsTFCF.HICKORY_NUT)).setRegistryName("hickory_nut"),
            new NutRecipe(BlockLogTFC.get(ironwood), BlockLeavesTFC.get(ironwood), new ItemStack(ItemsTFCF.HOPS)).setRegistryName("ironwood_hops"),
            //new NutRecipe(BlockLogTFC.get(juniper), BlockLeavesTFCF.get(FruitTreeTFCF.JUNIPER), new ItemStack(ItemsTFCF.JUNIPER)).setRegistryName("juniper_fruit"),
            new NutRecipe(BlockLogTFC.get(kauri), BlockLeavesTFC.get(kauri), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("kauri_pinecone"),
            new NutRecipe(BlockLogTFC.get(larch), BlockLeavesTFCF.get(FruitTreeTFCF.LARCH), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("larch_pinecone"),
            //new NutRecipe(BlockLogTFC.get(maclura), BlockLeavesTFCF.get(FruitTreeTFCF.MACLURA), new ItemStack(ItemsTFCF.OSAGE_ORANGE)).setRegistryName("maclura_fruit"),
            //new NutRecipe(BlockLogTFC.get(mahogany), BlockLeavesTFCF.get(FruitTreeTFCF.MAHOGANY), new ItemStack(ItemsTFCF.SKY_FRUIT)).setRegistryName("mahogany_fruit"),
            new NutRecipe(BlockLogTFC.get(nordmann_fir), BlockLeavesTFC.get(nordmann_fir), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("nordmann_fir_pinecone"),
            new NutRecipe(BlockLogTFC.get(norway_spruce), BlockLeavesTFC.get(norway_spruce), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("norway_spruce_pinecone"),
            //new NutRecipe(BlockLogTFC.get(pink_cherry), BlockLeavesTFC.get(pink_cherry), new ItemStack(ItemsTFCF.WILD_CHERRY)).setRegistryName("pink_cherry_fruit"),
            //new NutRecipe(BlockLogTFC.get(pink_ivory), BlockLeavesTFCF.get(FruitTreeTFCF.PINK_IVORY), new ItemStack(ItemsTFCF.PINK_IVORY_DRUPE)).setRegistryName("pink_ivory_fruit"),
            //new NutRecipe(BlockLogTFC.get(red_cedar), BlockLeavesTFCF.get(red_cedar), new ItemStack(ItemsTFCF.JUNIPER)).setRegistryName("red_cedar_fruit"),
            new NutRecipe(BlockLogTFC.get(redwood), BlockLeavesTFC.get(redwood), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("redwood_pinecone"),
            //new NutRecipe(BlockLogTFC.get(rowan), BlockLeavesTFCF.get(FruitTreeTFCF.ROWAN), new ItemStack(ItemsTFCF.ROWAN_BERRY)).setRegistryName("rowan_fruit"),
            //new NutRecipe(BlockLogTFC.get(syzygium), BlockLeavesTFCF.get(FruitTreeTFCF.SYZYGIUM), new ItemStack(ItemsTFCF.RIBERRY)).setRegistryName("syzygium_fruit"),
            new NutRecipe(BlockLogTFC.get(walnut), BlockLeavesTFC.get(walnut), new ItemStack(ItemsTFCF.WALNUT)).setRegistryName("walnut_fruit"),
            //new NutRecipe(BlockLogTFC.get(white_cherry), BlockLeavesTFC.get(white_cherry), new ItemStack(ItemsTFCF.WILD_CHERRY)).setRegistryName("white_cherry_fruit"),
            new NutRecipe(BlockLogTFC.get(whitebeam), BlockLeavesTFC.get(whitebeam), new ItemStack(ItemsTFCF.RIBERRY)).setRegistryName("whitebeam_fruit")
            //new NutRecipe(BlockLogTFC.get(yew), BlockLeavesTFCF.get(FruitTreeTFCF.YEW), new ItemStack(ItemsTFCF.YEW_BERRY)).setRegistryName("yew_fruit")
        );
    }

    @SubscribeEvent
    public static void onRegisterCrackingRecipeEvent(RegistryEvent.Register<CrackingRecipe> event)
    {
        /*
        ItemStack filled_coconut = new ItemStack(ItemsTFCF.CRACKED_COCONUT);
        IFluidHandler fluidHandler = filled_coconut.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (fluidHandler != null)
            fluidHandler.fill(new FluidStack(FluidsTFCF.COCONUT_MILK.get(), 1000), true);
        */

        /*if (!TFCFlorae.FirmaLifeAdded)
        {
            IForgeRegistry<CrackingRecipe> r = event.getRegistry();
            r.registerAll(

            // Regular Trees
            new CrackingRecipe(IIngredient.of(ItemsTFCF.ACORN), new ItemStack(ItemsTFCF.ACORN_NUT), 0.5f).setRegistryName("acorn_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.CHESTNUT), new ItemStack(ItemsTFCF.CHESTNUT_NUT), 0.5f).setRegistryName("chestnut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.HICKORY_NUT), new ItemStack(ItemsTFCF.HICKORY_NUT_NUT), 0.5f).setRegistryName("hickory_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.PINECONE), new ItemStack(ItemsTFCF.PINE_NUT), 0.5f).setRegistryName("pine_nut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.PECAN), new ItemStack(ItemsTFCF.PECAN_NUT), 0.5f).setRegistryName("pecan_fruit")
            );
        }*/

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
            new CrackingRecipe(IIngredient.of(ItemsTFCF.PINECONE), new ItemStack(ItemsTFCF.PINE_NUT), 0.5f).setRegistryName("pine_nut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.PECAN), new ItemStack(ItemsTFCF.PECAN_NUT), 0.5f).setRegistryName("pecan_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.WALNUT), new ItemStack(ItemsTFCF.WALNUT_NUT), 0.5f).setRegistryName("walnut_fruit")
            //new CrackingRecipe(IIngredient.of(ItemsTFCF.COCONUT), filled_coconut, 0.5f).setRegistryName("coconut_milk"),

            // Fruit Trees
            /*
            new CrackingRecipe(IIngredient.of(ItemsTFCF.ALMOND), new ItemStack(ItemsTFCF.ALMOND_NUT), 0.5f).setRegistryName("almond_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.BRAZIL_NUT), new ItemStack(ItemsTFCF.BRAZIL_NUT_NUT), 0.5f).setRegistryName("brazil_nut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.BREADNUT), new ItemStack(ItemsTFCF.BREADNUT_NUT), 0.5f).setRegistryName("breadnut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.BUNYA_NUT), new ItemStack(ItemsTFCF.BUNYA_NUT_NUT), 0.5f).setRegistryName("bunya_nut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.CANDLENUT), new ItemStack(ItemsTFCF.CANDLENUT_NUT), 0.5f).setRegistryName("candlenut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.CHESTNUT), new ItemStack(ItemsTFCF.CHESTNUT_NUT), 0.5f).setRegistryName("chestnut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.HEARTNUT), new ItemStack(ItemsTFCF.HEARTNUT_NUT), 0.5f).setRegistryName("heartnut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.KOLA_NUT), new ItemStack(ItemsTFCF.KOLA_NUT_NUT), 0.5f).setRegistryName("kola_nut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.KUKUI_NUT), new ItemStack(ItemsTFCF.KUKUI_NUT_NUT), 0.5f).setRegistryName("kukui_nut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.MACADAMIA), new ItemStack(ItemsTFCF.MACADAMIA_NUT), 0.5f).setRegistryName("macadamia_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.MONGONGO), new ItemStack(ItemsTFCF.MONGONGO_NUT), 0.5f).setRegistryName("mongongo_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.MONKEY_PUZZLE_NUT), new ItemStack(ItemsTFCF.MONKEY_PUZZLE_NUT_NUT), 0.5f).setRegistryName("monkey_puzzle_nut_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.NUTMEG), new ItemStack(ItemsTFCF.NUTMEG_NUT), 0.5f).setRegistryName("nutmeg_fruit"),
            new CrackingRecipe(IIngredient.of(ItemsTFCF.PISTACHIO), new ItemStack(ItemsTFCF.PISTACHIO_NUT), 0.5f).setRegistryName("pistachio_fruit"),
            */

            // Other
            //new CrackingRecipe(IIngredient.of(ItemsTFCF.CACAO), new ItemStack(ItemsTFCF.COCOA_BEANS), 0.5f).setRegistryName("cocoa_beans")
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
    
    @SubscribeEvent
    public static void onRegisterKnappingRecipeEventFL(RegistryEvent.Register<KnappingRecipe> event)
    {
        if (TFCFlorae.FirmaLifeAdded)
        {
            event.getRegistry().registerAll(
                // Kaolinite Clay
                new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(BlocksFL.OVEN), "XXXXX", "XX XX", "X   X", "X   X", "XXXXX").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_oven"),
                new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(BlocksFL.OVEN_CHIMNEY), "XX XX", "X   X", "X   X", "X   X", "X   X").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_oven_chimney"),
                new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(BlocksFL.OVEN_WALL), "    X", "   XX", "   XX", "  XXX", "  XXX").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_oven_wall"),

                // Mallet Mold
                new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_MALLET_MOLD, 1), "XXXXX", "     ", "   X ", "XXXXX", "XXXXX").setRegistryName(TFCFlorae.MODID, "unfired_kaolinite_clay_mallet_mold")
            );
        }
    }

    @SuppressWarnings("rawtypes")
    @SubscribeEvent
    public static void onRegisterHeatRecipeEventFL(RegistryEvent.Register<HeatRecipe> event)
    {
        if (TFCFlorae.FirmaLifeAdded)
        {
            IForgeRegistry<HeatRecipe> r = event.getRegistry();
            event.getRegistry().registerAll(
                // Kaolinite Clay
                new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_KAOLINITE_MALLET_MOLD), new ItemStack(ItemsTFCF.KAOLINITE_MALLET_MOLD), 1599.0F, Metal.Tier.TIER_I).setRegistryName(TFCFlorae.MODID, "kaolinite_clay_mallet_mold")
            );

            //Remove recipes
            if (ConfigFL.General.COMPAT.removeTFC)
            {
                IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) TFCRegistries.HEAT;
                String[] regNames = {"amaranth_bread", "buckwheat_bread", "fonio_bread", "millet_bread", "quinoa_bread", "spelt_bread"};
                for (String name : regNames)
                {
                    HeatRecipe recipe = TFCRegistries.HEAT.getValue(new ResourceLocation("tfcflorae", name));
                    if (recipe != null)
                    {
                        modRegistry.remove(recipe.getRegistryName());
                        if (ConfigFL.General.COMPAT.logging)
                            FirmaLife.logger.info("Removed heating recipe tfcflorae:{}", name);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRecipeRegisterFL(RegistryEvent.Register<IRecipe> event)
    {
        if (TFCFlorae.FirmaLifeAdded)
        {
            if (ConfigFL.General.COMPAT.removeTFC)
            {
                IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
                String[] regNames = {
                    "food/dough/amaranth", "food/dough/buckwheat", "food/dough/fonio", "food/dough/millet", "food/dough/quinoa", "food/dough/spelt",
                    "food/sandwich/amaranth", "food/sandwich/buckwheat", "food/sandwich/fonio", "food/sandwich/millet", "food/sandwich/quinoa", "food/sandwich/spelt"
                };
                for (String name : regNames)
                {
                    IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
                    if (recipe != null)
                    {
                        registry.remove(recipe.getRegistryName());
                        if (ConfigFL.General.COMPAT.logging)
                            FirmaLife.logger.info("Removed crafting recipe tfcflorae:{}", name);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterOvenRecipeEventFL(RegistryEvent.Register<OvenRecipe> event)
    {
        if (TFCFlorae.FirmaLifeAdded)
        {
            IForgeRegistry<OvenRecipe> r = event.getRegistry();
            int hour = ICalendar.TICKS_IN_HOUR;
            r.registerAll(
                new OvenRecipe(IIngredient.of(ItemsTFCF.AMARANTH_DOUGH), new ItemStack(ItemsTFCF.AMARANTH_BREAD), 4 * hour).setRegistryName(FirmaLife.MOD_ID, "amaranth_dough"),
                new OvenRecipe(IIngredient.of(ItemsTFCF.BUCKWHEAT_DOUGH), new ItemStack(ItemsTFCF.BUCKWHEAT_BREAD), 4 * hour).setRegistryName(FirmaLife.MOD_ID, "buckwheat_dough"),
                new OvenRecipe(IIngredient.of(ItemsTFCF.FONIO_DOUGH), new ItemStack(ItemsTFCF.FONIO_BREAD), 4 * hour).setRegistryName(FirmaLife.MOD_ID, "fonio_dough"),
                new OvenRecipe(IIngredient.of(ItemsTFCF.MILLET_DOUGH), new ItemStack(ItemsTFCF.MILLET_BREAD), 4 * hour).setRegistryName(FirmaLife.MOD_ID, "millet_dough"),
                new OvenRecipe(IIngredient.of(ItemsTFCF.QUINOA_DOUGH), new ItemStack(ItemsTFCF.QUINOA_BREAD), 4 * hour).setRegistryName(FirmaLife.MOD_ID, "quinoa_dough"),
                new OvenRecipe(IIngredient.of(ItemsTFCF.SPELT_DOUGH), new ItemStack(ItemsTFCF.SPELT_BREAD), 4 * hour).setRegistryName(FirmaLife.MOD_ID, "spelt_dough"),

                new OvenRecipe(IIngredient.of("amaranthFlatbreadDough"), new ItemStack(ItemsTFCF.AMARANTH_FLATBREAD), 4 * hour).setRegistryName(FirmaLife.MOD_ID, "amaranth_flatbread_dough"),
                new OvenRecipe(IIngredient.of("buckwheatFlatbreadDough"), new ItemStack(ItemsTFCF.BUCKWHEAT_FLATBREAD), 4 * hour).setRegistryName(FirmaLife.MOD_ID, "buckwheat_flatbread_dough"),
                new OvenRecipe(IIngredient.of("fonioFlatbreadDough"), new ItemStack(ItemsTFCF.FONIO_FLATBREAD), 4 * hour).setRegistryName(FirmaLife.MOD_ID, "fonio_flatbread_dough"),
                new OvenRecipe(IIngredient.of("milletFlatbreadDough"), new ItemStack(ItemsTFCF.MILLET_FLATBREAD), 4 * hour).setRegistryName(FirmaLife.MOD_ID, "millet_flatbread_dough"),
                new OvenRecipe(IIngredient.of("quinoaFlatbreadDough"), new ItemStack(ItemsTFCF.QUINOA_FLATBREAD), 4 * hour).setRegistryName(FirmaLife.MOD_ID, "quinoa_flatbread_dough"),
                new OvenRecipe(IIngredient.of("speltFlatbreadDough"), new ItemStack(ItemsTFCF.SPELT_FLATBREAD), 4 * hour).setRegistryName(FirmaLife.MOD_ID, "spelt_flatbread_dough")
            );
        }
    }

    @SubscribeEvent
    public static void onRegisterDryingRecipeEventFL(RegistryEvent.Register<DryingRecipe> event)
    {
        if (TFCFlorae.FirmaLifeAdded)
        {
            IForgeRegistry<DryingRecipe> r = event.getRegistry();
            r.registerAll(
                new DryingRecipe(IIngredient.of(ItemsTFCF.BLACK_TEA), new ItemStack(ItemsTFCF.DRIED_BLACK_TEA), 24000).setRegistryName(FirmaLife.MOD_ID, "dried_black_tea"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.GREEN_TEA), new ItemStack(ItemsTFCF.DRIED_GREEN_TEA), 24000).setRegistryName(FirmaLife.MOD_ID, "dried_green_tea"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.WHITE_TEA), new ItemStack(ItemsTFCF.DRIED_WHITE_TEA), 24000).setRegistryName(FirmaLife.MOD_ID, "dried_white_tea"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.CANNABIS_BUD), new ItemStack(ItemsTFCF.DRIED_CANNABIS_BUD), 24000).setRegistryName(FirmaLife.MOD_ID, "dried_cannabis_bud"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.CANNABIS_LEAF), new ItemStack(ItemsTFCF.DRIED_CANNABIS_LEAF), 24000).setRegistryName(FirmaLife.MOD_ID, "dried_cannabis_leaf"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.COCA_LEAF), new ItemStack(ItemsTFCF.DRIED_COCA_LEAF), 24000).setRegistryName(FirmaLife.MOD_ID, "dried_coca_leaf"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.OPIUM_POPPY_BULB), new ItemStack(ItemsTFCF.DRIED_OPIUM_POPPY_BULB), 24000).setRegistryName(FirmaLife.MOD_ID, "dried_opium_poppy_bulb"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.PEYOTE), new ItemStack(ItemsTFCF.DRIED_PEYOTE), 24000).setRegistryName(FirmaLife.MOD_ID, "dried_peyote"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.TOBACCO_LEAF), new ItemStack(ItemsTFCF.DRIED_TOBACCO_LEAF), 24000).setRegistryName(FirmaLife.MOD_ID, "dried_tobacco_leaf"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.COFFEA_CHERRIES), new ItemStack(ItemsTFCF.ROASTED_COFFEE_BEANS), 24000).setRegistryName(FirmaLife.MOD_ID, "roasted_coffea_cherries"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.CHAMOMILE_HEAD), new ItemStack(ItemsTFCF.DRIED_CHAMOMILE_HEAD), 24000).setRegistryName(FirmaLife.MOD_ID, "roasted_chamomile_head"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.DANDELION_HEAD), new ItemStack(ItemsTFCF.DRIED_DANDELION_HEAD), 24000).setRegistryName(FirmaLife.MOD_ID, "roasted_dandelion_head"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.LABRADOR_TEA_HEAD), new ItemStack(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD), 24000).setRegistryName("roasted_labrador_tea_head"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.SUNFLOWER_HEAD), new ItemStack(ItemsTFCF.DRIED_SUNFLOWER_HEAD), 24000).setRegistryName(FirmaLife.MOD_ID, "roasted_sunflower_head")
            );

            // Mud Pottery
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
            {
                ItemFiredMudBrick firedMudBrick = ItemFiredMudBrick.get(ItemUnfiredMudBrick.get(rock));

                DryingRecipe mud = new DryingRecipe(IIngredient.of(ItemUnfiredMudBrick.get(rock)), new ItemStack(firedMudBrick), 6000);
                event.getRegistry().register(mud.setRegistryName(rock.getRegistryName().getPath().toLowerCase() + "_wet_mud_brick"));
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterKnappingRecipeEventTFCE(RegistryEvent.Register<KnappingRecipe> event)
    {
        if (TFCFlorae.TFCElementiaAdded)
        {
            IForgeRegistry<KnappingRecipe> r = event.getRegistry();
            
            for (ItemMetalTFCE.ItemType type : ItemMetalTFCE.ItemType.values())
            {
                if (type.hasMold(null))
                {
                    int amount = type == ItemMetalTFCE.ItemType.NAIL || type == ItemMetalTFCE.ItemType.RING ? 2 : 1;
                    event.getRegistry().register(new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemUnfiredKaoliniteMoldTFCE.get(type), amount), type.getPattern()).setRegistryName(type.name().toLowerCase() + "_kaolinite_mold"));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterHeatRecipeEventTFCE(RegistryEvent.Register<HeatRecipe> event)
    {
        if (TFCFlorae.TFCElementiaAdded)
        {
            IForgeRegistry<HeatRecipe> r = event.getRegistry();

            // Molds
            for (ItemMetalTFCE.ItemType type : ItemMetalTFCE.ItemType.values())
            {
                ItemUnfiredKaoliniteMoldTFCE unfiredMold = ItemUnfiredKaoliniteMoldTFCE.get(type);
                ItemKaoliniteMoldTFCE firedMold = ItemKaoliniteMoldTFCE.get(type);
                if (unfiredMold != null && firedMold != null)
                {
                    r.register(new HeatRecipeSimple(IIngredient.of(unfiredMold), new ItemStack(firedMold), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_kaolinite_mold_" + type.name().toLowerCase()));
                }
            }
        }
    }
}