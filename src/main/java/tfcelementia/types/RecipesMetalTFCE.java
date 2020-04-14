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
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
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
public class RecipesMetalTFCE
{
	@SubscribeEvent
    public static void onRegisterAnvilRecipeEvent(RegistryEvent.Register<AnvilRecipe> event)
    {
        IForgeRegistry<AnvilRecipe> r = event.getRegistry();
        
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
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.DOUBLE_PLATE));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_double_plate"), ingredient1, ingredient2, output, metal.getTier()));
            }
        }

        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.PLATE)));
            IIngredient<ItemStack> ingredient2 = IIngredient.of(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.DOUBLE_PLATE)));
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.TRIPLE_PLATE));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_triple_plate"), ingredient1, ingredient2, output, metal.getTier()));
            }
        }

        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.DOUBLE_PLATE)));
            IIngredient<ItemStack> ingredient2 = IIngredient.of(new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.DOUBLE_PLATE)));
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.QUADRUPLE_PLATE));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_quadruple_plate"), ingredient1, ingredient2, output, metal.getTier()));
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
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.TRIPLE_INGOT));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_triple_ingot"), ingredient1, ingredient2, output, metal.getTier()));
            }
        }

        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.DOUBLE_INGOT)));
            IIngredient<ItemStack> ingredient2 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.DOUBLE_INGOT)));
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.QUADRUPLE_INGOT));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_quadruple_ingot"), ingredient1, ingredient2, output, metal.getTier()));
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
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.TRIPLE_SHEET));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_triple_sheet"), ingredient1, ingredient2, output, metal.getTier()));
            }
        }

        for (Metal metal : TFCRegistries.METALS.getValuesCollection())
        {
            if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false))
                continue;
            IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.DOUBLE_SHEET)));
            IIngredient<ItemStack> ingredient2 = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.DOUBLE_SHEET)));
            
            ItemStack output = new ItemStack(ItemMetalTFCE.get(metal, ItemMetalTFCE.ItemType.QUADRUPLE_SHEET));
            if (!output.isEmpty())
            {
                //noinspection ConstantConditions
                r.register(new WeldingRecipe(new ResourceLocation(MODID, (metal.getRegistryName().getPath()).toLowerCase() + "_quadruple_sheet"), ingredient1, ingredient2, output, metal.getTier()));
            }
        };

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
    }

}
