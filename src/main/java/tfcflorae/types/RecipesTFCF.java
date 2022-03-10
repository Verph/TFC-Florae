package tfcflorae.types;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import com.eerussianguy.firmalife.ConfigFL;
import com.eerussianguy.firmalife.FirmaLife;
import com.eerussianguy.firmalife.init.FoodFL;
import com.eerussianguy.firmalife.init.Fruit;
import com.eerussianguy.firmalife.init.KnappingFL;
import com.eerussianguy.firmalife.recipe.CrackingRecipe;
import com.eerussianguy.firmalife.recipe.DryingRecipe;
import com.eerussianguy.firmalife.recipe.NutRecipe;
import com.eerussianguy.firmalife.recipe.OvenRecipe;
import com.eerussianguy.firmalife.recipe.PlanterRecipe;
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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
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
import net.dries007.tfc.objects.items.ItemSeedsTFC;
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
import net.dries007.tfc.objects.items.wood.ItemWoodenBucket;
import net.dries007.tfc.objects.recipes.ShapelessDamageRecipe;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.types.DefaultTrees;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.agriculture.Food;
import net.dries007.tfc.util.agriculture.FruitTree;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.fuel.FuelManager;
import net.dries007.tfc.util.skills.SmithingSkill;
import net.dries007.tfc.util.skills.SmithingSkill.Type;

import tfcflorae.ConfigTFCF;
import tfcflorae.TFCFlorae;
import tfcflorae.api.knapping.KnappingTypes;
import tfcflorae.compat.tfcelementia.ceramics.*;
import tfcflorae.objects.ArmorMaterialsTFCF;
import tfcflorae.objects.PowderTFCF;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.blocktype.*;
import tfcflorae.objects.blocks.groundcover.*;
import tfcflorae.objects.blocks.wood.BlockLeavesTFCF;
import tfcflorae.objects.fluids.FluidsTFCF;
import tfcflorae.objects.items.*;
import tfcflorae.objects.items.ceramics.*;
import tfcflorae.objects.items.food.ItemFoodTFCF;
import tfcflorae.objects.items.rock.ItemFiredMudBrick;
import tfcflorae.objects.items.rock.ItemMud;
import tfcflorae.objects.items.rock.ItemUnfiredMudBrick;
import tfcflorae.objects.recipes.AlembicRecipe;
import tfcflorae.objects.recipes.StickBundleRecipe;
import tfcflorae.types.PlantsTFCF;
import tfcflorae.types.BlockTypesTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;
import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.util.agriculture.CropTFCF;
import tfcflorae.util.agriculture.FoodDataTFCF;
import tfcflorae.util.agriculture.SeasonalTrees;

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
            for (SeasonalTrees fruitTreeTFCF : SeasonalTrees.values())
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
            for (SeasonalTrees fruitTreeTFCF : SeasonalTrees.values())
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

        if (!ConfigTFCF.General.WORLD.enableAllCoarse)
        {
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
            {
                IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
                String[] regNames = {
                    "stone/tfcflorae/coarse_dirt/" + rock
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
            if (!(ConfigTFCF.General.WORLD.enableAllCoarse && ConfigTFCF.General.WORLD.enableAllSpecialSoil))
            {
                for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
                {
                    IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
                    String[] regNames = {
                        "stone/tfcflorae/coarse_humus/" + rock,
                        "stone/tfcflorae/coarse_loam/" + rock,
                        "stone/tfcflorae/coarse_loamy_sand/" + rock,
                        "stone/tfcflorae/coarse_sandy_loam/" + rock,
                        "stone/tfcflorae/coarse_silt/" + rock,
                        "stone/tfcflorae/coarse_silt_loam/" + rock
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
        }
        if (!ConfigTFCF.General.WORLD.enableAllEarthenwareClay)
        {
            for (Metal.ItemType type : Metal.ItemType.values())
            {
                if (type.hasMold(null))
                {
                    IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
                    String[] regNames = {
                        "metal/unmold/earthenware/" + type,
                        "ceramics/unfired_clay_recycle_earthenware/" + type
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
            for (EnumDyeColor dyeColor : EnumDyeColor.values())
            {
                IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
                String[] regNames = {
                    "ceramics/glazed_vessel_earthenware/" + dyeColor,
                    "ceramics/unfired_clay_recycle_earthenware/vessel_glazed/" + dyeColor
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
                "ceramics/unfired_clay_recycle_earthenware/bowl",
                "ceramics/unfired_clay_recycle_earthenware/flowerpot",
                "ceramics/unfired_clay_recycle_earthenware/jug",
                "ceramics/unfired_clay_recycle_earthenware/large_vessel",
                "ceramics/unfired_clay_recycle_earthenware/pot",
                "ceramics/unfired_clay_recycle_earthenware/spindle",
                "ceramics/unfired_clay_recycle_earthenware/vessel",
                "earthenware_block",
                "earthenware_bricks",
                "earthenware_clay",
                "unfired_spindle_head_earthenware"
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
        if (!ConfigTFCF.General.WORLD.enableAllEarthenwareClay || !TFCFlorae.FirmaLifeAdded)
        {
            IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
            String[] regNames = {
                "metal/unmold/earthenware/mallet_head",
                "ceramics/unfired_clay_recycle_earthenware/mallet",
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
        if (!ConfigTFCF.General.WORLD.enableAllEarthenwareClay || !TFCFlorae.TFCElementiaAdded)
        {
            IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
            String[] regNames = {
                "metal/unmold/earthenware/nail",
                "metal/unmold/earthenware/ring",
                "metal/unmold/earthenware/halberd_blade",
                "metal/unmold/earthenware/metal_block",
                "ceramics/unfired_clay_recycle_earthenware/nail",
                "ceramics/unfired_clay_recycle_earthenware/ring",
                "ceramics/unfired_clay_recycle_earthenware/halberd_blade",
                "ceramics/unfired_clay_recycle_earthenware/metal_block"
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
        if (!ConfigTFCF.General.WORLD.enableAllKaoliniteClay)
        {
            for (Metal.ItemType type : Metal.ItemType.values())
            {
                if (type.hasMold(null))
                {
                    IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
                    String[] regNames = {
                        "metal/unmold/kaolinite/" + type,
                        "ceramics/unfired_clay_recycle_kaolinite/" + type
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
            for (EnumDyeColor dyeColor : EnumDyeColor.values())
            {
                IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
                String[] regNames = {
                    "ceramics/glazed_vessel_kaolinite/" + dyeColor,
                    "ceramics/unfired_clay_recycle_kaolinite/vessel_glazed/" + dyeColor
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
                "ceramics/unfired_clay_recycle_kaolinite/bowl",
                "ceramics/unfired_clay_recycle_kaolinite/flowerpot",
                "ceramics/unfired_clay_recycle_kaolinite/jug",
                "ceramics/unfired_clay_recycle_kaolinite/large_vessel",
                "ceramics/unfired_clay_recycle_kaolinite/pot",
                "ceramics/unfired_clay_recycle_kaolinite/spindle",
                "ceramics/unfired_clay_recycle_kaolinite/vessel",
                "kaolinite_block",
                "kaolinite_bricks",
                "kaolinite_clay",
                "unfired_spindle_head_kaolinite"
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
        if (!ConfigTFCF.General.WORLD.enableAllKaoliniteClay || !TFCFlorae.FirmaLifeAdded)
        {
            IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
            String[] regNames = {
                "metal/unmold/kaolinite/mallet_head",
                "ceramics/unfired_clay_recycle_kaolinite/mallet",
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
        if (!ConfigTFCF.General.WORLD.enableAllKaoliniteClay || !TFCFlorae.TFCElementiaAdded)
        {
            IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
            String[] regNames = {
                "metal/unmold/kaolinite/nail",
                "metal/unmold/kaolinite/ring",
                "metal/unmold/kaolinite/halberd_blade",
                "metal/unmold/kaolinite/metal_block",
                "ceramics/unfired_clay_recycle_kaolinite/nail",
                "ceramics/unfired_clay_recycle_kaolinite/ring",
                "ceramics/unfired_clay_recycle_kaolinite/halberd_blade",
                "ceramics/unfired_clay_recycle_kaolinite/metal_block"
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
        if (!ConfigTFCF.General.WORLD.enableAllStonewareClay)
        {
            for (Metal.ItemType type : Metal.ItemType.values())
            {
                if (type.hasMold(null))
                {
                    IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
                    String[] regNames = {
                        "metal/unmold/stoneware/" + type,
                        "ceramics/unfired_clay_recycle_stoneware/" + type
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
            for (EnumDyeColor dyeColor : EnumDyeColor.values())
            {
                IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
                String[] regNames = {
                    "ceramics/glazed_vessel_stoneware/" + dyeColor,
                    "ceramics/unfired_clay_recycle_stoneware/vessel_glazed/" + dyeColor
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
                "ceramics/unfired_clay_recycle_stoneware/bowl",
                "ceramics/unfired_clay_recycle_stoneware/flowerpot",
                "ceramics/unfired_clay_recycle_stoneware/jug",
                "ceramics/unfired_clay_recycle_stoneware/large_vessel",
                "ceramics/unfired_clay_recycle_stoneware/pot",
                "ceramics/unfired_clay_recycle_stoneware/spindle",
                "ceramics/unfired_clay_recycle_stoneware/vessel",
                "stoneware_block",
                "stoneware_bricks",
                "stoneware_clay",
                "unfired_spindle_head_stoneware"
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
        if (!ConfigTFCF.General.WORLD.enableAllStonewareClay || !TFCFlorae.FirmaLifeAdded)
        {
            IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
            String[] regNames = {
                "metal/unmold/stoneware/mallet_head",
                "ceramics/unfired_clay_recycle_stoneware/mallet",
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
        if (!ConfigTFCF.General.WORLD.enableAllStonewareClay || !TFCFlorae.TFCElementiaAdded)
        {
            IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
            String[] regNames = {
                "metal/unmold/stoneware/nail",
                "metal/unmold/stoneware/ring",
                "metal/unmold/stoneware/halberd_blade",
                "metal/unmold/stoneware/metal_block",
                "ceramics/unfired_clay_recycle_stoneware/nail",
                "ceramics/unfired_clay_recycle_stoneware/ring",
                "ceramics/unfired_clay_recycle_stoneware/halberd_blade",
                "ceramics/unfired_clay_recycle_stoneware/metal_block"
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

            new BarrelRecipe(IIngredient.of(FluidsTFCF.SALAMMONIAC.get(), 300), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.SMALL)), null, new ItemStack(Items.LEATHER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("leather_small_hide_salmiak"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SALAMMONIAC.get(), 400), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.MEDIUM)), null, new ItemStack(Items.LEATHER, 2), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("leather_medium_hide_salmiak"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SALAMMONIAC.get(), 500), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.LARGE)), null, new ItemStack(Items.LEATHER, 3), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("leather_large_hide_salmiak"),
            new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 1000), IIngredient.of("dustAmmoniumChloride"), new FluidStack(FluidsTFCF.SALAMMONIAC.get(), 1000), ItemStack.EMPTY, 0).setRegistryName("salammoniac"),

            // Sugar
            //new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 600), IIngredient.of("sugarcane", 5), null, new ItemStack(Items.SUGAR), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_from_sugar_cane"),

            // Base Potash Liquor
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("dustPotash"), new FluidStack(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 500), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("base_potash_liquor_from_potash"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("dustAsh"), new FluidStack(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 500), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("base_potash_liquor_from_ash"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("dustWood"), new FluidStack(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 500), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("base_potash_liquor_from_wood_dust"),

            // Cellulose Fibers
            new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of(ItemFoodTFC.get(Food.SUGARCANE)), new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_sugarcane_1"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("sugarcane"), new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_sugarcane_2"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("pulp"), new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_pulp"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("cropAgave"), new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_agave_crop"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("cropFlax"), new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_flax_crop"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("cropHemp"), new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_hemp_crop"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("pulpPapyrus"), new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_papyrus_crop"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.YUCCA))), new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_yucca_crop"),

            // Papyrus Fibers
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 600), IIngredient.of("pulpPapyrus", 3), null, new ItemStack(ItemsTFCF.PAPYRUS_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("papyrus_fiber_from_papyrus"),

        	// Fiber Processing
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("cropAgave"), null, new ItemStack(ItemsTFCF.SISAL_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sisal_fiber"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("cropFlax"), null, new ItemStack(ItemsTFCF.FLAX_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("flax_fiber"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("cropHemp"), null, new ItemStack(ItemsTFCF.HEMP_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("hemp_fiber"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 300), IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.YUCCA))), null, new ItemStack(ItemsTFCF.YUCCA_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("yucca_fiber"),
            
            // Fluid Production from paste

            // Olive
            new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 250), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("olive_oil_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 250), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("olive_oil_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 250), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("olive_oil_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 250), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("olive_oil_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 250), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("olive_oil_hemp"),

            // Soybean
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteSoybean"), new FluidStack(FluidsTFCF.SOYBEAN_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("soybean_water"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("groundSoybeans"), new FluidStack(FluidsTFCF.SOYBEAN_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("soybean_water_firmalife"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 250), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("soy_milk_jute"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 250), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("soy_milk_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 250), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("soy_milk_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 250), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("soy_milk_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 250), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("soy_milk_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 250), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("soy_milk_hemp"),

            new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFCF.SOY_MILK.get(), 9), new IngredientFluidItem(FluidsTFC.VINEGAR.get(), 1), new FluidStack(FluidsTFC.MILK_VINEGAR.get(), 10), 0).setRegistryName("soy_milk_vinegar"),

            // Linseed
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteLinseed"), new FluidStack(FluidsTFCF.LINSEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("linseed_water"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 250), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("linseed_oil_jute"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("linseed_oil_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("linseed_oil_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("linseed_oil_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("linseed_oil_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("linseed_oil_hemp"),

            // Rape Seed
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteRapeSeed"), new FluidStack(FluidsTFCF.RAPE_SEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("rape_seed_water"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 250), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("rape_seed_oil_jute"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("rape_seed_oil_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("rape_seed_oil_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("rape_seed_oil_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("rape_seed_oil_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("rape_seed_oil_hemp"),

            // Sunflower Seed
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteSunflowerSeed"), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("sunflower_seed_water"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 250), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("sunflower_seed_oil_jute"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("sunflower_seed_oil_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("sunflower_seed_oil_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("sunflower_seed_oil_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("sunflower_seed_oil_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("sunflower_seed_oil_hemp"),

            // Opium Poppy Seed
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteOpiumPoppySeed"), new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("opium_poppy_seed_water"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 250), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("opium_poppy_seed_oil_jute"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("opium_poppy_seed_oil_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("opium_poppy_seed_oil_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("opium_poppy_seed_oil_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("opium_poppy_seed_oil_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 250), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("opium_poppy_seed_oil_hemp"),

            // Sugar Beet Water
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("mashedSugarBeet"), new FluidStack(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_beet_water"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 250), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("sugar_beet_water_jute"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 250), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("sugar_beet_water_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 250), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("sugar_beet_water_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 250), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("sugar_beet_water_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 250), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("sugar_beet_water_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 250), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("sugar_beet_water_hemp"),

            // Sugarcane Water
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("mashedSugarCane"), new FluidStack(FluidsTFCF.SUGAR_CANE_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_cane_water"),

            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_CANE_WATER.get(), 250), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("sugar_cane_water_jute"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_CANE_WATER.get(), 250), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("sugar_cane_water_sisal"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_CANE_WATER.get(), 250), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("sugar_cane_water_silk"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_CANE_WATER.get(), 250), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("sugar_cane_water_cotton"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_CANE_WATER.get(), 250), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("sugar_cane_water_linen"),
            new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_CANE_WATER.get(), 250), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("sugar_cane_water_hemp"),

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

            // Special Clay Washing
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 100), IIngredient.of("clayEarthenware"), null, new ItemStack(Items.CLAY_BALL), 0).setRegistryName("earthenware_clay_wash"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 100), IIngredient.of("clayKaolinite"), null, new ItemStack(Items.CLAY_BALL), 0).setRegistryName("kaolinite_clay_wash"),
            new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 100), IIngredient.of("clayStoneware"), null, new ItemStack(Items.CLAY_BALL), 0).setRegistryName("stoneware_clay_wash"),

            // Silk Worm Stuff
            new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 100), IIngredient.of("cocoonSilkWorm"), null, new ItemStack(ItemsTFCF.SILK_WORM_COCOON_BOILED), 250).setRegistryName("boiled_cocoon"),

            // Cooling
            new BarrelRecipeTemperature(IIngredient.of(FluidsTFCF.DISTILLED_WATER.get(), 1), 50).setRegistryName("distilled_water_cooling")
        );
    }

    @SubscribeEvent
    public static void onRegisterKnappingRecipeEvent(RegistryEvent.Register<KnappingRecipe> event)
    {
        KnappingRecipe r = new KnappingRecipeStone(KnappingTypes.MUD, rockIn -> new ItemStack(ItemUnfiredMudBrick.get(rockIn), 3), "XXXXX", "     ", "XXXXX", "     ", "XXXXX");
        event.getRegistry().register(r.setRegistryName(TFCFlorae.MODID, "knapping_mud_brick"));

        // Clay Items
        for (Metal.ItemType type : Metal.ItemType.values())
        {
            if (type.hasMold(null))
            {
                int amount = type == INGOT ? 2 : 1;
                event.getRegistry().register(new KnappingRecipeSimple(KnappingTypes.EARTHENWARE_CLAY, true, new ItemStack(ItemUnfiredEarthenwareMold.get(type), amount), type.getPattern()).setRegistryName(TFCFlorae.MODID, "earthenware_" + type.name().toLowerCase() + "_mold"));
                event.getRegistry().register(new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemUnfiredKaoliniteMold.get(type), amount), type.getPattern()).setRegistryName(TFCFlorae.MODID, "kaolinite_" + type.name().toLowerCase() + "_mold"));
                event.getRegistry().register(new KnappingRecipeSimple(KnappingTypes.STONEWARE_CLAY, true, new ItemStack(ItemUnfiredStonewareMold.get(type), amount), type.getPattern()).setRegistryName(TFCFlorae.MODID, "stoneware_" + type.name().toLowerCase() + "_mold"));
            }
        }

        event.getRegistry().registerAll(

            // Earthenware
            new KnappingRecipeSimple(KnappingTypes.EARTHENWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_EARTHENWARE_BRICK, 3), "XXXXX", "     ", "XXXXX", "     ", "XXXXX").setRegistryName(TFCFlorae.MODID, "earthenware_clay_brick"),
            new KnappingRecipeSimple(KnappingTypes.EARTHENWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_EARTHENWARE_VESSEL), " XXX ", "XXXXX", "XXXXX", "XXXXX", " XXX ").setRegistryName(TFCFlorae.MODID, "earthenware_clay_small_vessel"),
            new KnappingRecipeSimple(KnappingTypes.EARTHENWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_EARTHENWARE_JUG), " X   ", "XXXX ", "XXX X", "XXXX ", "XXX  ").setRegistryName(TFCFlorae.MODID, "earthenware_clay_jug"),
            new KnappingRecipeSimple(KnappingTypes.EARTHENWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_EARTHENWARE_POT), "X   X", "X   X", "X   X", "XXXXX", " XXX ").setRegistryName(TFCFlorae.MODID, "earthenware_clay_pot"),
            new KnappingRecipeSimple(KnappingTypes.EARTHENWARE_CLAY, false, new ItemStack(ItemsTFCF.UNFIRED_EARTHENWARE_BOWL, 2), "X   X", " XXX ").setRegistryName(TFCFlorae.MODID, "earthenware_clay_bowl"),
            new KnappingRecipeSimple(KnappingTypes.EARTHENWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_EARTHENWARE_BOWL, 4), "X   X", " XXX ", "     ", "X   X", " XXX ").setRegistryName(TFCFlorae.MODID, "earthenware_clay_bowl_2"),
            new KnappingRecipeSimple(KnappingTypes.EARTHENWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_EARTHENWARE_LARGE_VESSEL), "X   X", "X   X", "X   X", "X   X", "XXXXX").setRegistryName(TFCFlorae.MODID, "earthenware_clay_large_vessel"),

            // Kaolinite Clay
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_BRICK, 3), "XXXXX", "     ", "XXXXX", "     ", "XXXXX").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_brick"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_VESSEL), " XXX ", "XXXXX", "XXXXX", "XXXXX", " XXX ").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_small_vessel"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_JUG), " X   ", "XXXX ", "XXX X", "XXXX ", "XXX  ").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_jug"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_POT), "X   X", "X   X", "X   X", "XXXXX", " XXX ").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_pot"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, false, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_BOWL, 2), "X   X", " XXX ").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_bowl"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_BOWL, 4), "X   X", " XXX ", "     ", "X   X", " XXX ").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_bowl_2"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_LARGE_VESSEL), "X   X", "X   X", "X   X", "X   X", "XXXXX").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_large_vessel"),

            // Stoneware
            new KnappingRecipeSimple(KnappingTypes.STONEWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_STONEWARE_BRICK, 3), "XXXXX", "     ", "XXXXX", "     ", "XXXXX").setRegistryName(TFCFlorae.MODID, "stoneware_clay_brick"),
            new KnappingRecipeSimple(KnappingTypes.STONEWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_STONEWARE_VESSEL), " XXX ", "XXXXX", "XXXXX", "XXXXX", " XXX ").setRegistryName(TFCFlorae.MODID, "stoneware_clay_small_vessel"),
            new KnappingRecipeSimple(KnappingTypes.STONEWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_STONEWARE_JUG), " X   ", "XXXX ", "XXX X", "XXXX ", "XXX  ").setRegistryName(TFCFlorae.MODID, "stoneware_clay_jug"),
            new KnappingRecipeSimple(KnappingTypes.STONEWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_STONEWARE_POT), "X   X", "X   X", "X   X", "XXXXX", " XXX ").setRegistryName(TFCFlorae.MODID, "stoneware_clay_pot"),
            new KnappingRecipeSimple(KnappingTypes.STONEWARE_CLAY, false, new ItemStack(ItemsTFCF.UNFIRED_STONEWARE_BOWL, 2), "X   X", " XXX ").setRegistryName(TFCFlorae.MODID, "stoneware_clay_bowl"),
            new KnappingRecipeSimple(KnappingTypes.STONEWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_STONEWARE_BOWL, 4), "X   X", " XXX ", "     ", "X   X", " XXX ").setRegistryName(TFCFlorae.MODID, "stoneware_clay_bowl_2"),
            new KnappingRecipeSimple(KnappingTypes.STONEWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_STONEWARE_LARGE_VESSEL), "X   X", "X   X", "X   X", "X   X", "XXXXX").setRegistryName(TFCFlorae.MODID, "stoneware_clay_large_vessel"),

            // Flint Tool Heads
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_AXE_HEAD, 1), " X   ", "XXXX ", "XXXXX", "XXXX ", " X   ").setRegistryName(TFCFlorae.MODID, "flint_axe_head"),

            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HAMMER_HEAD, 1), "     ", "XXXXX", "XXXXX", "  X  ", "     ").setRegistryName(TFCFlorae.MODID, "flint_hammer_head"),

            //new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HOE_HEAD, 1), "XXXXX", "   XX").setRegistryName(TFCFlorae.MODID, "flint_hoe_head_1"),
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HOE_HEAD, 2), "XXXXX", "XX   ", "     ", "XXXXX", "XX   ").setRegistryName(TFCFlorae.MODID, "flint_hoe_head_2"),
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HOE_HEAD, 2), "XXXXX", "XX   ", "     ", "XXXXX", "   XX").setRegistryName(TFCFlorae.MODID, "flint_hoe_head_3"),

            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_JAVELIN_HEAD, 1), "XXX  ", "XXXX ", "XXXXX", " XXX ", "  X  ").setRegistryName(TFCFlorae.MODID, "flint_javelin_head"),

            /*new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 1), "X ", "XX", "XX", "XX", "XX").setRegistryName(TFCFlorae.MODID, "flint_knife_head_1"),
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 1), " X", "XX", "XX", "XX", "XX").setRegistryName(TFCFlorae.MODID, "flint_knife_head_2"),*/
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 2), "X  X ", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName(TFCFlorae.MODID, "flint_knife_head_3"),
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 2), "X   X", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName(TFCFlorae.MODID, "flint_knife_head_4"),
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 2), " X X ", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName(TFCFlorae.MODID, "flint_knife_head_5"),

            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_SHOVEL_HEAD, 1), " XXX ", " XXX ", " XXX ", " XXX ", "  X  ").setRegistryName(TFCFlorae.MODID, "flint_shovel_head"),

            new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_URN), "XX XX", "X   X", "X   X", "X   X", "XXXXX").setRegistryName(TFCFlorae.MODID, "clay_urn"),
            new KnappingRecipeSimple(KnappingTypes.EARTHENWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_URN), "XX XX", "X   X", "X   X", "X   X", "XXXXX").setRegistryName(TFCFlorae.MODID, "earthenware_urn"),
            new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_URN), "XX XX", "X   X", "X   X", "X   X", "XXXXX").setRegistryName(TFCFlorae.MODID, "kaolinite_urn"),
            new KnappingRecipeSimple(KnappingTypes.STONEWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_URN), "XX XX", "X   X", "X   X", "X   X", "XXXXX").setRegistryName(TFCFlorae.MODID, "stoneware_urn"),

            // Containers
            new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(ItemsTFCF.LEATHER_BAG_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("leather_bag_pieces_horizontal"),
            new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(ItemsTFCF.LEATHER_BAG_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("leather_bag_pieces_vertical"),
            new KnappingRecipeSimple(KnappingTypes.BURLAP_CLOTH, true, new ItemStack(ItemsTFCF.BURLAP_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("burlap_sack_pieces_horizontal"),
            new KnappingRecipeSimple(KnappingTypes.BURLAP_CLOTH, true, new ItemStack(ItemsTFCF.BURLAP_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("burlap_sack_pieces_vertical"),
            new KnappingRecipeSimple(KnappingTypes.WOOL_CLOTH, true, new ItemStack(ItemsTFCF.WOOL_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("wool_sack_pieces_horizontal"),
            new KnappingRecipeSimple(KnappingTypes.WOOL_CLOTH, true, new ItemStack(ItemsTFCF.WOOL_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("wool_sack_pieces_vertical"),
            new KnappingRecipeSimple(KnappingTypes.SILK_CLOTH, true, new ItemStack(ItemsTFCF.SILK_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("silk_sack_pieces_horizontal"),
            new KnappingRecipeSimple(KnappingTypes.SILK_CLOTH, true, new ItemStack(ItemsTFCF.SILK_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("silk_sack_pieces_vertical"),
            new KnappingRecipeSimple(KnappingTypes.SISAL_CLOTH, true, new ItemStack(ItemsTFCF.SISAL_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("sisal_sack_pieces_horizontal"),
            new KnappingRecipeSimple(KnappingTypes.SISAL_CLOTH, true, new ItemStack(ItemsTFCF.SISAL_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("sisal_sack_pieces_vertical"),
            new KnappingRecipeSimple(KnappingTypes.COTTON_CLOTH, true, new ItemStack(ItemsTFCF.COTTON_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("cotton_sack_pieces_horizontal"),
            new KnappingRecipeSimple(KnappingTypes.COTTON_CLOTH, true, new ItemStack(ItemsTFCF.COTTON_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("cotton_sack_pieces_vertical"),
            new KnappingRecipeSimple(KnappingTypes.LINEN_CLOTH, true, new ItemStack(ItemsTFCF.LINEN_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("linen_sack_pieces_horizontal"),
            new KnappingRecipeSimple(KnappingTypes.LINEN_CLOTH, true, new ItemStack(ItemsTFCF.LINEN_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("linen_sack_pieces_vertical"),
            new KnappingRecipeSimple(KnappingTypes.HEMP_CLOTH, true, new ItemStack(ItemsTFCF.HEMP_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("hemp_sack_pieces_horizontal"),
            new KnappingRecipeSimple(KnappingTypes.HEMP_CLOTH, true, new ItemStack(ItemsTFCF.HEMP_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("hemp_sack_pieces_vertical"),
            new KnappingRecipeSimple(KnappingTypes.YUCCA_CANVAS, true, new ItemStack(ItemsTFCF.YUCCA_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("yucca_sack_pieces_horizontal"),
            new KnappingRecipeSimple(KnappingTypes.YUCCA_CANVAS, true, new ItemStack(ItemsTFCF.YUCCA_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("yucca_sack_pieces_vertical"),
            new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFCF.PINEAPPLE_LEATHER_BAG_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("pineapple_leather_bag_pieces_horizontal"),
            new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFCF.PINEAPPLE_LEATHER_BAG_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("pineapple_leather_bag_pieces_vertical"),

            // Pineapple Leather
            new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(Items.SADDLE), "  X  ", "XXXXX", "XXXXX", "XXXXX", "  X  ").setRegistryName("pineapple_leather_saddle"),
            new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFC.QUIVER), " XXXX", "X XXX", "X XXX", "X XXX", " XXXX").setRegistryName("pineapple_leather_quiver")

            // Armor Knapping
            /*new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFCF.PINEAPPLE_LEATHER_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("pineapple_leather_helmet"),
            new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFCF.PINEAPPLE_LEATHER_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("pineapple_leather_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFCF.PINEAPPLE_LEATHER_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("pineapple_leather_leggings"),
            new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFCF.PINEAPPLE_LEATHER_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("pineapple_leather_boots"),

            new KnappingRecipeSimple(KnappingTypes.BURLAP_CLOTH, true, new ItemStack(ItemsTFCF.BURLAP_CLOTH_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("burlap_cloth_helmet"),
            new KnappingRecipeSimple(KnappingTypes.BURLAP_CLOTH, true, new ItemStack(ItemsTFCF.BURLAP_CLOTH_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("burlap_cloth_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.BURLAP_CLOTH, true, new ItemStack(ItemsTFCF.BURLAP_CLOTH_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("burlap_cloth_leggings"),
            new KnappingRecipeSimple(KnappingTypes.BURLAP_CLOTH, true, new ItemStack(ItemsTFCF.BURLAP_CLOTH_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("burlap_cloth_boots"),

            new KnappingRecipeSimple(KnappingTypes.WOOL_CLOTH, true, new ItemStack(ItemsTFCF.WOOL_CLOTH_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("wool_cloth_helmet"),
            new KnappingRecipeSimple(KnappingTypes.WOOL_CLOTH, true, new ItemStack(ItemsTFCF.WOOL_CLOTH_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("wool_cloth_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.WOOL_CLOTH, true, new ItemStack(ItemsTFCF.WOOL_CLOTH_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("wool_cloth_leggings"),
            new KnappingRecipeSimple(KnappingTypes.WOOL_CLOTH, true, new ItemStack(ItemsTFCF.WOOL_CLOTH_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("wool_cloth_boots"),

            new KnappingRecipeSimple(KnappingTypes.SILK_CLOTH, true, new ItemStack(ItemsTFCF.SILK_CLOTH_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("silk_cloth_helmet"),
            new KnappingRecipeSimple(KnappingTypes.SILK_CLOTH, true, new ItemStack(ItemsTFCF.SILK_CLOTH_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("silk_cloth_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.SILK_CLOTH, true, new ItemStack(ItemsTFCF.SILK_CLOTH_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("silk_cloth_leggings"),
            new KnappingRecipeSimple(KnappingTypes.SILK_CLOTH, true, new ItemStack(ItemsTFCF.SILK_CLOTH_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("silk_cloth_boots"),

            new KnappingRecipeSimple(KnappingTypes.SISAL_CLOTH, true, new ItemStack(ItemsTFCF.SISAL_CLOTH_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("sisal_cloth_helmet"),
            new KnappingRecipeSimple(KnappingTypes.SISAL_CLOTH, true, new ItemStack(ItemsTFCF.SISAL_CLOTH_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("sisal_cloth_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.SISAL_CLOTH, true, new ItemStack(ItemsTFCF.SISAL_CLOTH_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("sisal_cloth_leggings"),
            new KnappingRecipeSimple(KnappingTypes.SISAL_CLOTH, true, new ItemStack(ItemsTFCF.SISAL_CLOTH_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("sisal_cloth_boots"),

            new KnappingRecipeSimple(KnappingTypes.COTTON_CLOTH, true, new ItemStack(ItemsTFCF.COTTON_CLOTH_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("cotton_cloth_helmet"),
            new KnappingRecipeSimple(KnappingTypes.COTTON_CLOTH, true, new ItemStack(ItemsTFCF.COTTON_CLOTH_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("cotton_cloth_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.COTTON_CLOTH, true, new ItemStack(ItemsTFCF.COTTON_CLOTH_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("cotton_cloth_leggings"),
            new KnappingRecipeSimple(KnappingTypes.COTTON_CLOTH, true, new ItemStack(ItemsTFCF.COTTON_CLOTH_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("cotton_cloth_boots"),

            new KnappingRecipeSimple(KnappingTypes.LINEN_CLOTH, true, new ItemStack(ItemsTFCF.LINEN_CLOTH_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("linen_cloth_helmet"),
            new KnappingRecipeSimple(KnappingTypes.LINEN_CLOTH, true, new ItemStack(ItemsTFCF.LINEN_CLOTH_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("linen_cloth_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.LINEN_CLOTH, true, new ItemStack(ItemsTFCF.LINEN_CLOTH_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("linen_cloth_leggings"),
            new KnappingRecipeSimple(KnappingTypes.LINEN_CLOTH, true, new ItemStack(ItemsTFCF.LINEN_CLOTH_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("linen_cloth_boots"),

            new KnappingRecipeSimple(KnappingTypes.HEMP_CLOTH, true, new ItemStack(ItemsTFCF.HEMP_CLOTH_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("hemp_cloth_helmet"),
            new KnappingRecipeSimple(KnappingTypes.HEMP_CLOTH, true, new ItemStack(ItemsTFCF.HEMP_CLOTH_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("hemp_cloth_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.HEMP_CLOTH, true, new ItemStack(ItemsTFCF.HEMP_CLOTH_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("hemp_cloth_leggings"),
            new KnappingRecipeSimple(KnappingTypes.HEMP_CLOTH, true, new ItemStack(ItemsTFCF.HEMP_CLOTH_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("hemp_cloth_boots"),

            new KnappingRecipeSimple(KnappingTypes.YUCCA_CANVAS, true, new ItemStack(ItemsTFCF.YUCCA_CANVAS_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("yucca_canvas_helmet"),
            new KnappingRecipeSimple(KnappingTypes.YUCCA_CANVAS, true, new ItemStack(ItemsTFCF.YUCCA_CANVAS_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("yucca_canvas_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.YUCCA_CANVAS, true, new ItemStack(ItemsTFCF.YUCCA_CANVAS_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("yucca_canvas_leggings"),
            new KnappingRecipeSimple(KnappingTypes.YUCCA_CANVAS, true, new ItemStack(ItemsTFCF.YUCCA_CANVAS_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("yucca_canvas_boots")*/
        );
    }

    @SuppressWarnings("rawtypes")
    @SubscribeEvent
    public static void onRegisterHeatRecipeEvent(RegistryEvent.Register<HeatRecipe> event)
    {
        IForgeRegistry<HeatRecipe> r = event.getRegistry();

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

            HeatRecipe recipe = new HeatRecipeSimple(IIngredient.of(ItemUnfiredMudBrick.get(rock)), new ItemStack(firedMudBrick), 600f, Metal.Tier.TIER_I);
            event.getRegistry().register(recipe.setRegistryName(rock.getRegistryName().getPath().toLowerCase() + "_unfired_mud_brick"));
            
            // Fired Pottery - doesn't burn up
            recipe = new HeatRecipeSimple(IIngredient.of(firedMudBrick), new ItemStack(firedMudBrick), 1599f, Metal.Tier.TIER_I);
            event.getRegistry().register(recipe.setRegistryName(rock.getRegistryName().getPath().toLowerCase() + "_fired_mud_brick"));
        }

        // Clay Pottery Items with metadata
        for (EnumDyeColor dye : EnumDyeColor.values())
        {
            r.register(new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.UNFIRED_EARTHENWARE_VESSEL_GLAZED, 1, dye.getMetadata())), new ItemStack(ItemsTFCF.FIRED_EARTHENWARE_VESSEL_GLAZED, 1, dye.getMetadata()), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_earthenware_vessel_glazed_" + dye.getName()));
            r.register(new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_VESSEL_GLAZED, 1, dye.getMetadata())), new ItemStack(ItemsTFCF.FIRED_KAOLINITE_VESSEL_GLAZED, 1, dye.getMetadata()), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_kaolinite_vessel_glazed_" + dye.getName()));
            r.register(new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.UNFIRED_STONEWARE_VESSEL_GLAZED, 1, dye.getMetadata())), new ItemStack(ItemsTFCF.FIRED_STONEWARE_VESSEL_GLAZED, 1, dye.getMetadata()), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_stoneware_vessel_glazed_" + dye.getName()));
        }

        // Clay Molds
        for (Metal.ItemType type : Metal.ItemType.values())
        {
            ItemUnfiredEarthenwareMold unfiredMoldEarthenware = ItemUnfiredEarthenwareMold.get(type);
            ItemEarthenwareMold firedMoldEarthenware = ItemEarthenwareMold.get(type);
            if (unfiredMoldEarthenware != null && firedMoldEarthenware != null)
            {
                r.register(new HeatRecipeSimple(IIngredient.of(unfiredMoldEarthenware), new ItemStack(firedMoldEarthenware), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_earthenware_mold_" + type.name().toLowerCase()));
            }

            ItemUnfiredKaoliniteMold unfiredMoldKaolinite = ItemUnfiredKaoliniteMold.get(type);
            ItemKaoliniteMold firedMoldKaolinite = ItemKaoliniteMold.get(type);
            if (unfiredMoldKaolinite != null && firedMoldKaolinite != null)
            {
                r.register(new HeatRecipeSimple(IIngredient.of(unfiredMoldKaolinite), new ItemStack(firedMoldKaolinite), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_kaolinite_mold_" + type.name().toLowerCase()));
            }

            ItemUnfiredStonewareMold unfiredMoldStoneware = ItemUnfiredStonewareMold.get(type);
            ItemStonewareMold firedMoldStoneware = ItemStonewareMold.get(type);
            if (unfiredMoldStoneware != null && firedMoldStoneware != null)
            {
                r.register(new HeatRecipeSimple(IIngredient.of(unfiredMoldStoneware), new ItemStack(firedMoldStoneware), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_stoneware_mold_" + type.name().toLowerCase()));
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

        // Bucket Stuff
        /*ItemStack woodenBucket = new ItemStack(ItemsTFC.WOODEN_BUCKET);
        IFluidHandler woodenBucketSaltWater = woodenBucket.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        woodenBucketSaltWater.fill(new FluidStack(FluidsTFC.SALT_WATER.get(), Fluid.BUCKET_VOLUME), true);
        IFluidHandler woodenBucketSweetSap = woodenBucket.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        woodenBucketSweetSap.fill(new FluidStack(FluidsTFCF.SWEET_SAP.get(), Fluid.BUCKET_VOLUME), true);
        IFluidHandler woodenBucketSweetSyrup = woodenBucket.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        woodenBucketSweetSyrup.fill(new FluidStack(FluidsTFCF.SWEET_SYRUP.get(), Fluid.BUCKET_VOLUME), true);

        r.registerAll(

            new HeatRecipeSimple(IIngredient.of((Item) woodenBucketSaltWater), new ItemStack(ItemsTFCF.WOODEN_BUCKET_SALT, 1), 480, 500).setRegistryName("bucket_salt"),
            new HeatRecipeSimple(IIngredient.of((Item) woodenBucketSweetSap), new ItemStack((Item) woodenBucketSweetSyrup, 1), 480, 500).setRegistryName("bucket_syrup"),
            new HeatRecipeSimple(IIngredient.of((Item) woodenBucketSweetSyrup), new ItemStack(ItemsTFCF.WOODEN_BUCKET_SUGAR, 1), 480, 500).setRegistryName("bucket_sugar")
        );*/

        // Standard / Simple recipes
        r.registerAll(

            // Earthenware Pottery
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_EARTHENWARE_BRICK), new ItemStack(ItemsTFCF.FIRED_EARTHENWARE_BRICK), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_earthenware_brick"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_EARTHENWARE_VESSEL), new ItemStack(ItemsTFCF.FIRED_EARTHENWARE_VESSEL), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_earthenware_vessel"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_EARTHENWARE_JUG), new ItemStack(ItemsTFCF.FIRED_EARTHENWARE_JUG), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_earthenware_jug"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_EARTHENWARE_POT), new ItemStack(ItemsTFCF.FIRED_EARTHENWARE_POT), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_earthenware_pot"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_EARTHENWARE_BOWL), new ItemStack(ItemsTFCF.FIRED_EARTHENWARE_BOWL), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_earthenware_bowl"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_EARTHENWARE_LARGE_VESSEL), new ItemStack(BlocksTFCF.FIRED_EARTHENWARE_LARGE_VESSEL), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_earthenware_large_vessel"),

            // Fired Earthenware Pottery - doesn't burn up
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FIRED_EARTHENWARE_BRICK), new ItemStack(ItemsTFCF.FIRED_EARTHENWARE_BRICK), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_earthenware_brick"),
            new HeatRecipeVessel(IIngredient.of(ItemsTFCF.FIRED_EARTHENWARE_VESSEL), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_earthenware_vessel"),
            new HeatRecipeVessel(IIngredient.of(ItemsTFCF.FIRED_EARTHENWARE_VESSEL_GLAZED), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_earthenware_vessel_glazed_all"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FIRED_EARTHENWARE_JUG), new ItemStack(ItemsTFCF.FIRED_EARTHENWARE_JUG), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_earthenware_jug"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FIRED_EARTHENWARE_POT), new ItemStack(ItemsTFCF.FIRED_EARTHENWARE_POT), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_earthenware_pot"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FIRED_EARTHENWARE_BOWL), new ItemStack(ItemsTFCF.FIRED_EARTHENWARE_BOWL), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_earthenware_bowl"),
            new HeatRecipeSimple(IIngredient.of(BlocksTFCF.FIRED_EARTHENWARE_LARGE_VESSEL), new ItemStack(BlocksTFCF.FIRED_EARTHENWARE_LARGE_VESSEL), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_earthenware_large_vessel"),

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

            // Stoneware Pottery
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_STONEWARE_BRICK), new ItemStack(ItemsTFCF.FIRED_STONEWARE_BRICK), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_stoneware_brick"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_STONEWARE_VESSEL), new ItemStack(ItemsTFCF.FIRED_STONEWARE_VESSEL), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_stoneware_vessel"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_STONEWARE_JUG), new ItemStack(ItemsTFCF.FIRED_STONEWARE_JUG), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_stoneware_jug"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_STONEWARE_POT), new ItemStack(ItemsTFCF.FIRED_STONEWARE_POT), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_stoneware_pot"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_STONEWARE_BOWL), new ItemStack(ItemsTFCF.FIRED_STONEWARE_BOWL), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_stoneware_bowl"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_STONEWARE_LARGE_VESSEL), new ItemStack(BlocksTFCF.FIRED_STONEWARE_LARGE_VESSEL), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_stoneware_large_vessel"),

            // Fired Stoneware Pottery - doesn't burn up
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FIRED_STONEWARE_BRICK), new ItemStack(ItemsTFCF.FIRED_STONEWARE_BRICK), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_stoneware_brick"),
            new HeatRecipeVessel(IIngredient.of(ItemsTFCF.FIRED_STONEWARE_VESSEL), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_stoneware_vessel"),
            new HeatRecipeVessel(IIngredient.of(ItemsTFCF.FIRED_STONEWARE_VESSEL_GLAZED), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_stoneware_vessel_glazed_all"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FIRED_STONEWARE_JUG), new ItemStack(ItemsTFCF.FIRED_STONEWARE_JUG), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_stoneware_jug"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FIRED_STONEWARE_POT), new ItemStack(ItemsTFCF.FIRED_STONEWARE_POT), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_stoneware_pot"),
            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FIRED_STONEWARE_BOWL), new ItemStack(ItemsTFCF.FIRED_STONEWARE_BOWL), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_stoneware_bowl"),
            new HeatRecipeSimple(IIngredient.of(BlocksTFCF.FIRED_STONEWARE_LARGE_VESSEL), new ItemStack(BlocksTFCF.FIRED_STONEWARE_LARGE_VESSEL), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_stoneware_large_vessel"),

            new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_URN), new ItemStack(BlocksTFCF.FIRED_URN), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_urn"),
            new HeatRecipeSimple(IIngredient.of(BlocksTFCF.FIRED_URN), new ItemStack(BlocksTFCF.FIRED_URN), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_urn"),

            // Bread
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.HASH_MUFFIN), 480).setRegistryName("burned_hash_muffin"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.AMARANTH_BREAD), 480).setRegistryName("burned_barley_bread"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.BUCKWHEAT_BREAD), 480).setRegistryName("burned_cornbread"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.FONIO_BREAD), 480).setRegistryName("burned_oat_bread"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.MILLET_BREAD), 480).setRegistryName("burned_rice_bread"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.QUINOA_BREAD), 480).setRegistryName("burned_rye_bread"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.SPELT_BREAD), 480).setRegistryName("burned_wheat_bread"),

            // Epiphytes
            new HeatRecipeSimple(IIngredient.of("epiphyteArtistsConk"), new ItemStack(ItemsTFCF.ROASTED_ARTISTS_CONK), 200, 480).setRegistryName("roasted_artists_conk"),
            new HeatRecipeSimple(IIngredient.of("epiphyteSulphurShelf"), new ItemStack(ItemsTFCF.ROASTED_SULPHUR_SHELF), 200, 480).setRegistryName("roasted_sulphur_shelf"),
            new HeatRecipeSimple(IIngredient.of("epiphyteTurkeyTail"), new ItemStack(ItemsTFCF.ROASTED_TURKEY_TAIL), 200, 480).setRegistryName("roasted_turkey_tail"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_ARTISTS_CONK), 480).setRegistryName("burned_artists_conk"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_SULPHUR_SHELF), 480).setRegistryName("burned_sulphur_shelf"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_TURKEY_TAIL), 480).setRegistryName("burned_turkey_tail"),

            // Mushrooms
            new HeatRecipeSimple(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.PORCINI))), new ItemStack(ItemsTFCF.ROASTED_PORCINI), 200, 480).setRegistryName("roasted_porcini_specific"),
            new HeatRecipeSimple(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.AMANITA))), new ItemStack(ItemsTFCF.ROASTED_AMANITA), 200, 480).setRegistryName("roasted_amanita_specific"),
            new HeatRecipeSimple(IIngredient.of("mushroomPorcini"), new ItemStack(ItemsTFCF.ROASTED_PORCINI), 200, 480).setRegistryName("roasted_porcini"),
            new HeatRecipeSimple(IIngredient.of("mushroomAmanita"), new ItemStack(ItemsTFCF.ROASTED_AMANITA), 200, 480).setRegistryName("roasted_amanita"),
            new HeatRecipeSimple(IIngredient.of("mushroomBlackPowderpuff"), new ItemStack(ItemsTFCF.ROASTED_BLACK_POWDERPUFF), 200, 480).setRegistryName("roasted_black_powderpuff"),
            new HeatRecipeSimple(IIngredient.of("mushroomChanterelle"), new ItemStack(ItemsTFCF.ROASTED_CHANTERELLE), 200, 480).setRegistryName("roasted_chanterelle"),
            new HeatRecipeSimple(IIngredient.of("mushroomDeathCap"), new ItemStack(ItemsTFCF.ROASTED_DEATH_CAP), 200, 480).setRegistryName("roasted_death_cap"),
            new HeatRecipeSimple(IIngredient.of("mushroomGiantClub"), new ItemStack(ItemsTFCF.ROASTED_GIANT_CLUB), 200, 480).setRegistryName("roasted_giant_club"),
            new HeatRecipeSimple(IIngredient.of("mushroomParasol"), new ItemStack(ItemsTFCF.ROASTED_PARASOL_MUSHROOM), 200, 480).setRegistryName("roasted_parasol_mushroom"),
            new HeatRecipeSimple(IIngredient.of("mushroomStinkhorn"), new ItemStack(ItemsTFCF.ROASTED_STINKHORN), 200, 480).setRegistryName("roasted_stinkhorn"),
            new HeatRecipeSimple(IIngredient.of("mushroomWeepingMilkCap"), new ItemStack(ItemsTFCF.ROASTED_WEEPING_MILK_CAP), 200, 480).setRegistryName("roasted_weeping_milk_cap"),
            new HeatRecipeSimple(IIngredient.of("mushroomWoodBlewit"), new ItemStack(ItemsTFCF.ROASTED_WOOD_BLEWIT), 200, 480).setRegistryName("roasted_wood_blewit"),
            new HeatRecipeSimple(IIngredient.of("mushroomWoollyGomphus"), new ItemStack(ItemsTFCF.ROASTED_WOOLLY_GOMPHUS), 200, 480).setRegistryName("roasted_woolly_gomphus"),

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
            new HeatRecipeSimple(IIngredient.of("rawEel"), new ItemStack(ItemsTFCF.COOKED_EEL), 200, 480).setRegistryName("cooked_eel"),
            new HeatRecipeSimple(IIngredient.of("rawCrab"), new ItemStack(ItemsTFCF.COOKED_CRAB), 200, 480).setRegistryName("cooked_crab"),
            new HeatRecipeSimple(IIngredient.of("rawClam"), new ItemStack(ItemsTFCF.COOKED_CLAM), 200, 480).setRegistryName("cooked_clam"),
            new HeatRecipeSimple(IIngredient.of("rawScallop"), new ItemStack(ItemsTFCF.COOKED_SCALLOP), 200, 480).setRegistryName("cooked_scallop"),
            new HeatRecipeSimple(IIngredient.of("rawStarfish"), new ItemStack(ItemsTFCF.COOKED_STARFISH), 200, 480).setRegistryName("cooked_starfish"),
            new HeatRecipeSimple(IIngredient.of("rawSnail"), new ItemStack(ItemsTFCF.COOKED_SNAIL), 200, 480).setRegistryName("cooked_snail"),
            new HeatRecipeSimple(IIngredient.of("rawWorm"), new ItemStack(ItemsTFCF.COOKED_WORM), 200, 480).setRegistryName("cooked_worm"),

            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_EEL), 480).setRegistryName("burned_eel"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_CRAB), 480).setRegistryName("burned_crab"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_CLAM), 480).setRegistryName("burned_clam"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_SCALLOP), 480).setRegistryName("burned_scallop"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_STARFISH), 480).setRegistryName("burned_starfish"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_SNAIL), 480).setRegistryName("burned_snail"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_WORM), 480).setRegistryName("burned_worm"),

            // Nut Roasting
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BEECHNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BEECHNUT_NUT), 200, 480).setRegistryName("roasted_beechnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BLACK_WALNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BLACK_WALNUT_NUT), 200, 480).setRegistryName("roasted_black_walnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BUTTERNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BUTTERNUT_NUT), 200, 480).setRegistryName("roasted_butternut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.GINKGO_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_GINKGO_NUT_NUT), 200, 480).setRegistryName("roasted_ginkgo_nut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.HAZELNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HAZELNUT_NUT), 200, 480).setRegistryName("roasted_hazelnut"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.WALNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_WALNUT_NUT), 200, 480).setRegistryName("roasted_walnut"),

            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BEECHNUT_NUT), 480).setRegistryName("burned_beechnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BLACK_WALNUT_NUT), 480).setRegistryName("burned_black_walnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BUTTERNUT_NUT), 480).setRegistryName("burned_butternut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_GINKGO_NUT_NUT), 480).setRegistryName("burned_ginkgo_nut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_HAZELNUT_NUT), 480).setRegistryName("burned_hazelnut"),
            HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_WALNUT_NUT), 480).setRegistryName("burned_walnut"),

            // Kaolinite Clay
            new HeatRecipeSimple(IIngredient.of("clayKaolinite"), new ItemStack(ItemPowder.get(Powder.KAOLINITE)), 200).setRegistryName("kaolinite_clay"),
            new HeatRecipeSimple(IIngredient.of(ItemPowder.get(Powder.KAOLINITE)), new ItemStack(ItemPowder.get(Powder.KAOLINITE)), 1599f, Metal.Tier.TIER_I).setRegistryName("burnt_kaolinite_clay"),

            // Torches
            new HeatRecipeSimple(IIngredient.of("twig"), new ItemStack(Blocks.TORCH, 6), 50).setRegistryName("torch_twig"),
            new HeatRecipeSimple(IIngredient.of("driftwood"), new ItemStack(Blocks.TORCH, 12), 60).setRegistryName("torch_driftwood"),

        	// Ash
            new HeatRecipeSimple(IIngredient.of("straw"), new ItemStack(ItemsTFC.WOOD_ASH, 1), 350, 750).setRegistryName("straw_ash"),
            new HeatRecipeSimple(IIngredient.of("twig"), new ItemStack(ItemsTFC.WOOD_ASH, 2), 350, 750).setRegistryName("twig_ash"),
            new HeatRecipeSimple(IIngredient.of("torch"), new ItemStack(ItemsTFC.WOOD_ASH, 2), 350, 750).setRegistryName("torch_ash_1"),
            new HeatRecipeSimple(IIngredient.of(Blocks.TORCH), new ItemStack(ItemsTFC.WOOD_ASH, 2), 350, 750).setRegistryName("torch_ash_2"),

            // Charred Bones
            new HeatRecipeSimple(IIngredient.of("bone"), new ItemStack(ItemsTFCF.CHARRED_BONES), 425, 850).setRegistryName("charred_bones_heat"),
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CHARRED_BONES)), new ItemStack(ItemsTFCF.CHARRED_BONES), 1599f, Metal.Tier.TIER_I).setRegistryName("burnt_charred_bones")
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
            new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.DRIED_COFFEA_CHERRIES)), new ItemStack(ItemsTFCF.ROASTED_COFFEE_BEANS), 200, 480).setRegistryName("roasted_coffea_cherries_firepit"),
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

            new LoomRecipe(new ResourceLocation(MODID, "yucca_canvas"), IIngredient.of(ItemsTFCF.YUCCA_STRING, 12), new ItemStack(ItemsTFCF.YUCCA_CANVAS), 12, new ResourceLocation(MODID, "textures/blocks/devices/loom/product/yucca.png")),
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
            new QuernRecipe(IIngredient.of("sugarcane"), new ItemStack((ItemsTFCF.MASHED_SUGAR_CANE))).setRegistryName("mashed_sugar_cane_quern_1"),
            new QuernRecipe(IIngredient.of(ItemFoodTFC.get(Food.SUGARCANE)), new ItemStack((ItemsTFCF.MASHED_SUGAR_CANE))).setRegistryName("mashed_sugar_cane_quern_2"),
            new QuernRecipe(IIngredient.of("cropSugarBeet"), new ItemStack((ItemsTFCF.MASHED_SUGAR_BEET))).setRegistryName("mashed_sugar_beet_quern"),
            new QuernRecipe(IIngredient.of("grainAmaranth"), new ItemStack((ItemsTFCF.AMARANTH_FLOUR))).setRegistryName("amaranth"),
            new QuernRecipe(IIngredient.of("grainBuckwheat"), new ItemStack((ItemsTFCF.BUCKWHEAT_FLOUR))).setRegistryName("buckwheat"),
            new QuernRecipe(IIngredient.of("grainFonio"), new ItemStack((ItemsTFCF.FONIO_FLOUR))).setRegistryName("fonio"),
            new QuernRecipe(IIngredient.of("grainMillet"), new ItemStack((ItemsTFCF.MILLET_FLOUR))).setRegistryName("millet"),
            new QuernRecipe(IIngredient.of("grainQuinoa"), new ItemStack((ItemsTFCF.QUINOA_FLOUR))).setRegistryName("quinoa"),
            new QuernRecipe(IIngredient.of("grainSpelt"), new ItemStack((ItemsTFCF.SPELT_FLOUR))).setRegistryName("spelt"),
            new QuernRecipe(IIngredient.of(ItemsTFCF.CASSIA_CINNAMON_BARK), new ItemStack(ItemsTFCF.GROUND_CASSIA_CINNAMON, 2)).setRegistryName("ground_cassia_cinnamon"),
            new QuernRecipe(IIngredient.of(ItemsTFCF.CEYLON_CINNAMON_BARK), new ItemStack(ItemsTFCF.GROUND_CEYLON_CINNAMON, 2)).setRegistryName("ground_ceylon_cinnamon"),
            //new QuernRecipe(IIngredient.of(ItemsTFCF.BLACK_PEPPER), new ItemStack(ItemsTFCF.GROUND_BLACK_PEPPER, 2)).setRegistryName("ground_black_pepper"),
            new QuernRecipe(IIngredient.of(ItemsTFCF.ROASTED_COFFEE_BEANS), new ItemStack(ItemsTFCF.COFFEE_POWDER, 2)).setRegistryName("ground_coffee_beans"),
            new QuernRecipe(IIngredient.of("pearl"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.PEARL))).setRegistryName("crushed_pearl"),
            new QuernRecipe(IIngredient.of("pearlBlack"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.BLACK_PEARL))).setRegistryName("crushed_black_pearl"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PAPYRUS))), new ItemStack(ItemsTFCF.PAPYRUS_PULP, 3)).setRegistryName("crushed_papyrus"),
            new QuernRecipe(IIngredient.of("linseed"), new ItemStack(ItemsTFCF.LINSEED_PASTE, 1)).setRegistryName("crushed_linseed"),
            new QuernRecipe(IIngredient.of("rapeSeed"), new ItemStack(ItemsTFCF.RAPE_SEED_PASTE, 1)).setRegistryName("crushed_rape_seed"),
            new QuernRecipe(IIngredient.of("sunflowerSeed"), new ItemStack(ItemsTFCF.SUNFLOWER_SEED_PASTE, 1)).setRegistryName("crushed_sunflower_seed"),
            new QuernRecipe(IIngredient.of("opiumPoppySeed"), new ItemStack(ItemsTFCF.OPIUM_POPPY_SEED_PASTE, 1)).setRegistryName("crushed_opium_poppy_seed"),
            new QuernRecipe(IIngredient.of("cropSoybean"), new ItemStack(ItemsTFCF.SOYBEAN_PASTE, 1)).setRegistryName("crushed_soybean"),

            // Dye from plants
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CHAMOMILE))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_chamomile"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HYDRANGEA))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_hydrangea"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LILY_OF_THE_VALLEY))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_lily_of_the_valley"),
            new QuernRecipe(IIngredient.of("cropMadder"), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("crushed_madder"),
            new QuernRecipe(IIngredient.of("cropWoad"), new ItemStack(ItemsTFC.DYE_BLUE, 2)).setRegistryName("crushed_woad"),
            new QuernRecipe(IIngredient.of("cropIndigo"), new ItemStack(ItemsTFC.DYE_BLUE, 2)).setRegistryName("crushed_indigo"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SUNFLOWER))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_sunflower"),
            new QuernRecipe(IIngredient.of("cropWeld"), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_weld"),
            new QuernRecipe(IIngredient.of("cropRape"), new ItemStack(ItemsTFCF.RAPE, 2)).setRegistryName("crushed_rape"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LILAC))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_lilac"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PEONY))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_peony"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LAVANDULA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage())).setRegistryName("crushed_lavandula"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CATTAIL))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("crushed_cattail"),
            new QuernRecipe(IIngredient.of("cropAgave"), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_agave"),
            new QuernRecipe(IIngredient.of("resin"), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("yellow_dye_resin"),
            new QuernRecipe(IIngredient.of("treeLeavesTeak"), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("green_dye_teak_leaves"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SUGAR_CANE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_sugar_cane_1"),
            new QuernRecipe(IIngredient.of(Blocks.REEDS), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_sugar_cane_2"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.TACKWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_tackweed"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.TAKAKIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_takakia"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.VOODOO_LILY))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_voodoo_lily"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.DEVILS_TONGUE))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_devils_tongue"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BROMELIA_HEMISPHERICA))), new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage())).setRegistryName("magenta_dye_bromelia_hemispherica"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BROMELIA_LACINIOSA))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_bromelia_laciniosa"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.KAIETEUR_FALLS))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_kaieteur_falls"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MATTEUCCIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_matteuccia"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CORD_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_cord_grass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.REED_MANNAGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_reed_mannagrass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PRAIRIE_JUNEGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_prairie_junegrass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WOOLLY_BUSH))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_woolly_bush"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CINNAMON_FERN))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_cinnamon_fern"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.JAPANESE_PIERIS))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_japanese_pieris"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BURNING_BUSH))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dye_burning_bush"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.UNDERGROWTH_SHRUB))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_undergrowth_shrub"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.UNDERGROWTH_SHRUB_SMALL))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_undergrowth_shrub_small"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SEA_OATS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_sea_oats"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BUNCH_GRASS_FLOATING))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dyebunch_grass_floating"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BUNCH_GRASS_REED))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dye_bunch_grass_reed"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CROWNGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_crowngrass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CAT_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_cat_grass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GOOSEGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_goosegrass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WHEATGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_wheatgrass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HALFA_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_halfa_grass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LEYMUS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_leymus"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MARRAM_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_marram_grass"),
            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_BARLEY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_wild_barley"),
            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_RICE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_wild_rice"),
            //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_WHEAT))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_wild_wheat"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.RATTAN))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dye_rattan"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_hanging_vines"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BLUE_SKYFLOWER))), new ItemStack(Items.DYE, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage())).setRegistryName("light_blue_dye_blue_skyflower"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.JADE_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage())).setRegistryName("light_blue_dye_jade_vine"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.JAPANESE_IVY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_japanese_ivy"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MADEIRA_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_madeira_vine"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MYSORE_TRUMPETVINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_mysore_trumpetvine"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SILVERVEIN_CREEPER))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_silvervein_creeper"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SWEDISH_IVY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_swedish_ivy"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.VARIEGATED_PERSIAN_IVY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_variegated_persian_ivy"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.APACHE_DWARF))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("yellow_dye_apache_dwarf"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.ARTISTS_CONK))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dye_artists_conk"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CLIMBING_CACTUS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_climbing_cactus"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CRIMSON_CATTLEYA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage())).setRegistryName("purple_dye_crimson_cattleya"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CREEPING_MISTLETOE))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("orange_dye_creeping_mistletoe"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CUTHBERTS_DENDROBIUM))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("orange_dye_cuthberts_dendrobium"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.FISH_BONE_CACTUS))), new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage())).setRegistryName("magenta_dye_fish_bone_cactus"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.FRAGRANT_FERN))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_fragrant_fern"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HARLEQUIN_MISTLETOE))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("orange_dye_harlequin_mistletoe"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.KING_ORCHID))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("white_dye_king_orchid"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LANTERN_OF_THE_FOREST))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_lantern_of_the_forest"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LARGE_FOOT_DENDROBIUM))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_large_foot_dendrobium"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.COMMON_MISTLETOE))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("white_dye_common_mistletoe"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SKY_PLANT))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_sky_plant"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SULPHUR_SHELF))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("orange_dye_sulphur_shelf"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.TAMPA_BUTTERFLY_ORCHID))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("yellow_dye_tampa_butterfly_orchid"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.TURKEY_TAIL))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dye_turkey_tail"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WILDFIRE))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_wildfire"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BELL_TREE_DAHLIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_bell_tree_dahlia"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BIG_LEAF_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_big_leaf_palm"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.DRAKENSBERG_CYCAD))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_drakensberg_cycad"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.DWARF_SUGAR_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_dwarf_sugar_palm"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GIANT_CANE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_giant_cane"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GIANT_ELEPHANT_EAR))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_giant_elephant_ear"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GIANT_FEATHER_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_giant_feather_grass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MADAGASCAR_OCOTILLO))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_madagascar_ocotillo"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MALAGASY_TREE_ALOE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_malagasy_tree_aloe"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MOUNTAIN_CABBAGE_TREE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_mountain_cabbage_tree"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PYGMY_DATE_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_pygmy_date_palm"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.QUEEN_SAGO))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_queen_sago"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.RED_SEALING_WAX_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_red_sealing_wax_palm"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SUMMER_ASPHODEL))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_summer_asphodel"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.ZIMBABWE_ALOE))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_zimbabwe_aloe"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.ANTHURIUM))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_anthurium"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.ARROWHEAD))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_arrowhead"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.ARUNDO))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_arundo"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BLUEGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_bluegrass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BLUE_GINGER))), new ItemStack(ItemsTFC.DYE_BLUE, 2)).setRegistryName("blue_dye_blue_ginger"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BROMEGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_bromegrass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BUR_REED))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("white_dye_bur_reed"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.DESERT_FLAME))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("yellow_dye_desert_flame"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HELICONIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_heliconia"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HIBISCUS))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("white_dye_hibiscus"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.KANGAROO_PAW))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_kangaroo_paw"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.KING_FERN))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_king_fern"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LIPSTICK_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage())).setRegistryName("magenta_dye_lipstick_palm"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MARIGOLD))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("yellow_dye_marigold"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MONSTERA_EPIPHYTE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_monstera_epiphyte"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MONSTERA_GROUND))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_monstera_ground"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PHRAGMITE))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_phragmite"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PICKERELWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage())).setRegistryName("purple_dye_pickerelweed"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BADDERLOCKS))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dye_badderlocks"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.COONTAIL))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_coontail"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.EEL_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_eel_grass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GIANT_KELP))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_giant_kelp"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GUTWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_gutweed"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HORNWORT))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_hornwort"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LAMINARIA))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dye_laminaria"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LEAFY_KELP))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_leafy_kelp"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MANATEE_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_manatee_grass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MILFOIL))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_milfoil"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PONDWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_pondweed"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SAGO))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_sago"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SEAGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_seagrass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SEAWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_seaweed"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.STAR_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_star_grass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.TURTLE_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_turtle_grass"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WINGED_KELP))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_winged_kelp"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.RED_ALGAE))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_red_algae"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.RED_SEA_WHIP))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_red_sea_whip"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SEA_ANEMONE))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_sea_anemone"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BEARDED_MOSS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_bearded_moss"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_glow_vine"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HANGING_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_hanging_vine"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.JUNGLE_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_jungle_vine"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LIANA))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_liana"),
            new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.IVY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_ivy"),
            new QuernRecipe(IIngredient.of(BlocksTFCF.BLUESHROOM), new ItemStack(Items.DYE, 1, EnumDyeColor.CYAN.getDyeDamage())).setRegistryName("cyan_dye_blueshroom"),
            new QuernRecipe(IIngredient.of(BlocksTFCF.GLOWSHROOM), new ItemStack(Items.GLOWSTONE_DUST, 1)).setRegistryName("glowstone_dust_glowshroom"),
            new QuernRecipe(IIngredient.of(BlocksTFCF.MAGMA_SHROOM), new ItemStack(Items.MAGMA_CREAM, 1)).setRegistryName("magma_cream_magma_shroom"),
            new QuernRecipe(IIngredient.of(BlocksTFCF.POISON_SHROOM), new ItemStack(Items.DYE, 1, EnumDyeColor.MAGENTA.getDyeDamage())).setRegistryName("magenta_dye_poison_shroom"),
            new QuernRecipe(IIngredient.of(BlocksTFCF.SULPHUR_SHROOM), new ItemStack(ItemPowder.get(Powder.SULFUR), 1)).setRegistryName("sulphur_powder_shroom"),
            new QuernRecipe(IIngredient.of(BlocksTFCF.GLOWING_SEA_BANANA), new ItemStack(Items.GLOWSTONE_DUST, 2)).setRegistryName("glowing_sea_banana_glowstone_dust"),
            new QuernRecipe(IIngredient.of(BlocksTFCF.LIGHTSTONE), new ItemStack(Items.GLOWSTONE_DUST, 2)).setRegistryName("glowstone_dust_lightstone")
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
                // Earthenware Clay
                new KnappingRecipeSimple(KnappingTypes.EARTHENWARE_CLAY, true, new ItemStack(BlocksFL.OVEN), "XXXXX", "XX XX", "X   X", "X   X", "XXXXX").setRegistryName(TFCFlorae.MODID, "earthenware_clay_oven"),
                new KnappingRecipeSimple(KnappingTypes.EARTHENWARE_CLAY, true, new ItemStack(BlocksFL.OVEN_CHIMNEY), "XX XX", "X   X", "X   X", "X   X", "X   X").setRegistryName(TFCFlorae.MODID, "earthenware_clay_oven_chimney"),
                new KnappingRecipeSimple(KnappingTypes.EARTHENWARE_CLAY, true, new ItemStack(BlocksFL.OVEN_WALL), "    X", "   XX", "   XX", "  XXX", "  XXX").setRegistryName(TFCFlorae.MODID, "earthenware_clay_oven_wall"),

                // Earthenware Mallet Mold
                new KnappingRecipeSimple(KnappingTypes.EARTHENWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_EARTHENWARE_MALLET_MOLD, 1), "XXXXX", "     ", "   X ", "XXXXX", "XXXXX").setRegistryName(TFCFlorae.MODID, "unfired_earthenware_clay_mallet_mold"),

                // Kaolinite Clay
                new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(BlocksFL.OVEN), "XXXXX", "XX XX", "X   X", "X   X", "XXXXX").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_oven"),
                new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(BlocksFL.OVEN_CHIMNEY), "XX XX", "X   X", "X   X", "X   X", "X   X").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_oven_chimney"),
                new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(BlocksFL.OVEN_WALL), "    X", "   XX", "   XX", "  XXX", "  XXX").setRegistryName(TFCFlorae.MODID, "kaolinite_clay_oven_wall"),

                // Kaolinite Mallet Mold
                new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_KAOLINITE_MALLET_MOLD, 1), "XXXXX", "     ", "   X ", "XXXXX", "XXXXX").setRegistryName(TFCFlorae.MODID, "unfired_kaolinite_clay_mallet_mold"),

                // Stoneware Clay
                new KnappingRecipeSimple(KnappingTypes.STONEWARE_CLAY, true, new ItemStack(BlocksFL.OVEN), "XXXXX", "XX XX", "X   X", "X   X", "XXXXX").setRegistryName(TFCFlorae.MODID, "stoneware_clay_oven"),
                new KnappingRecipeSimple(KnappingTypes.STONEWARE_CLAY, true, new ItemStack(BlocksFL.OVEN_CHIMNEY), "XX XX", "X   X", "X   X", "X   X", "X   X").setRegistryName(TFCFlorae.MODID, "stoneware_clay_oven_chimney"),
                new KnappingRecipeSimple(KnappingTypes.STONEWARE_CLAY, true, new ItemStack(BlocksFL.OVEN_WALL), "    X", "   XX", "   XX", "  XXX", "  XXX").setRegistryName(TFCFlorae.MODID, "stoneware_clay_oven_wall"),

                // Stoneware Mallet Mold
                new KnappingRecipeSimple(KnappingTypes.STONEWARE_CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_STONEWARE_MALLET_MOLD, 1), "XXXXX", "     ", "   X ", "XXXXX", "XXXXX").setRegistryName(TFCFlorae.MODID, "unfired_stoneware_clay_mallet_mold")
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

                new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_EARTHENWARE_MALLET_MOLD), new ItemStack(ItemsTFCF.EARTHENWARE_MALLET_MOLD), 1599.0F, Metal.Tier.TIER_I).setRegistryName(TFCFlorae.MODID, "earthenware_clay_mallet_mold"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_KAOLINITE_MALLET_MOLD), new ItemStack(ItemsTFCF.KAOLINITE_MALLET_MOLD), 1599.0F, Metal.Tier.TIER_I).setRegistryName(TFCFlorae.MODID, "kaolinite_clay_mallet_mold"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_STONEWARE_MALLET_MOLD), new ItemStack(ItemsTFCF.STONEWARE_MALLET_MOLD), 1599.0F, Metal.Tier.TIER_I).setRegistryName(TFCFlorae.MODID, "stoneware_clay_mallet_mold")
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
    @SuppressWarnings("ConstantConditions")
    public static void onRegisterNutTreeEvent(RegistryEvent.Register<NutRecipe> event)
    {
        if (TFCFlorae.FirmaLifeAdded)
        {
            IForgeRegistry<NutRecipe> r = event.getRegistry();

            Tree chestnut = TFCRegistries.TREES.getValue(DefaultTrees.CHESTNUT);
            Tree oak = TFCRegistries.TREES.getValue(DefaultTrees.OAK);
            Tree hickory = TFCRegistries.TREES.getValue(DefaultTrees.HICKORY);
            Tree beech = TFCRegistries.TREES.getValue(TreesTFCF.BEECH);
            Tree black_walnut = TFCRegistries.TREES.getValue(TreesTFCF.BLACK_WALNUT);
            Tree butternut = TFCRegistries.TREES.getValue(TreesTFCF.BUTTERNUT);
            Tree european_oak = TFCRegistries.TREES.getValue(TreesTFCF.EUROPEAN_OAK);
            Tree ginkgo = TFCRegistries.TREES.getValue(TreesTFCF.GINKGO);
            Tree hazel = TFCRegistries.TREES.getValue(TreesTFCF.HAZEL);
            Tree hemlock = TFCRegistries.TREES.getValue(TreesTFCF.HEMLOCK);
            Tree ironwood = TFCRegistries.TREES.getValue(TreesTFCF.IRONWOOD);
            Tree kauri = TFCRegistries.TREES.getValue(TreesTFCF.KAURI);
            Tree larch = TFCRegistries.TREES.getValue(TreesTFCF.LARCH);
            Tree nordmann_fir = TFCRegistries.TREES.getValue(TreesTFCF.NORDMANN_FIR);
            Tree norway_spruce = TFCRegistries.TREES.getValue(TreesTFCF.NORWAY_SPRUCE);
            Tree redwood = TFCRegistries.TREES.getValue(TreesTFCF.REDWOOD);
            Tree walnut = TFCRegistries.TREES.getValue(TreesTFCF.WALNUT);

            r.registerAll(
                
                new NutRecipe(BlockLogTFC.get(european_oak), BlockLeavesTFC.get(european_oak), new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("european_oak_nut"),
                new NutRecipe(BlockLogTFC.get(european_oak), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_EUROPEAN_OAK), new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("european_oak_nut_yellow"),
                new NutRecipe(BlockLogTFC.get(european_oak), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_EUROPEAN_OAK), new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("european_oak_nut_orange"),
                new NutRecipe(BlockLogTFC.get(european_oak), BlockLeavesTFCF.get(SeasonalTrees.RED_EUROPEAN_OAK), new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("european_oak_nut_red"),
                new NutRecipe(BlockLogTFC.get(oak), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_OAK), new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("oak_nut_yellow"),
                new NutRecipe(BlockLogTFC.get(oak), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_OAK), new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("oak_nut_orange"),
                new NutRecipe(BlockLogTFC.get(oak), BlockLeavesTFCF.get(SeasonalTrees.RED_OAK), new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("oak_nut_red"),
                new NutRecipe(BlockLogTFC.get(chestnut), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_CHESTNUT), new ItemStack(ItemsFL.getFood(FoodFL.CHESTNUTS))).setRegistryName("chestnut_nut_yellow"),
                new NutRecipe(BlockLogTFC.get(chestnut), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_CHESTNUT), new ItemStack(ItemsFL.getFood(FoodFL.CHESTNUTS))).setRegistryName("chestnut_nut_orange"),
                new NutRecipe(BlockLogTFC.get(chestnut), BlockLeavesTFCF.get(SeasonalTrees.RED_CHESTNUT), new ItemStack(ItemsFL.getFood(FoodFL.CHESTNUTS))).setRegistryName("chestnut_nut_red"),
                new NutRecipe(BlockLogTFC.get(hickory), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_HICKORY), new ItemStack(ItemsFL.getFood(FoodFL.PECAN_NUTS))).setRegistryName("hickory_nut_yellow"),
                new NutRecipe(BlockLogTFC.get(hickory), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_HICKORY), new ItemStack(ItemsFL.getFood(FoodFL.PECAN_NUTS))).setRegistryName("hickory_nut_orange"),
                new NutRecipe(BlockLogTFC.get(hickory), BlockLeavesTFCF.get(SeasonalTrees.RED_HICKORY), new ItemStack(ItemsFL.getFood(FoodFL.PECAN_NUTS))).setRegistryName("hickory_nut_red"),
                new NutRecipe(BlockLogTFC.get(beech), BlockLeavesTFC.get(beech), new ItemStack(ItemsTFCF.BEECHNUT)).setRegistryName("beech_nut"),
                new NutRecipe(BlockLogTFC.get(beech), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_BEECH), new ItemStack(ItemsTFCF.BEECHNUT)).setRegistryName("beech_nut_yellow"),
                new NutRecipe(BlockLogTFC.get(beech), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_BEECH), new ItemStack(ItemsTFCF.BEECHNUT)).setRegistryName("beech_nut_orange"),
                new NutRecipe(BlockLogTFC.get(beech), BlockLeavesTFCF.get(SeasonalTrees.RED_BEECH), new ItemStack(ItemsTFCF.BEECHNUT)).setRegistryName("beech_nut_red"),
                new NutRecipe(BlockLogTFC.get(black_walnut), BlockLeavesTFC.get(black_walnut), new ItemStack(ItemsTFCF.BLACK_WALNUT)).setRegistryName("black_walnut_nut"),
                new NutRecipe(BlockLogTFC.get(black_walnut), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_BLACK_WALNUT), new ItemStack(ItemsTFCF.BLACK_WALNUT)).setRegistryName("black_walnut_nut_yellow"),
                new NutRecipe(BlockLogTFC.get(black_walnut), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_BLACK_WALNUT), new ItemStack(ItemsTFCF.BLACK_WALNUT)).setRegistryName("black_walnut_nut_orange"),
                new NutRecipe(BlockLogTFC.get(black_walnut), BlockLeavesTFCF.get(SeasonalTrees.RED_BLACK_WALNUT), new ItemStack(ItemsTFCF.BLACK_WALNUT)).setRegistryName("black_walnut_nut_red"),
                new NutRecipe(BlockLogTFC.get(butternut), BlockLeavesTFC.get(butternut), new ItemStack(ItemsTFCF.BUTTERNUT)).setRegistryName("butternut_nut"),
                new NutRecipe(BlockLogTFC.get(butternut), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_BUTTERNUT), new ItemStack(ItemsTFCF.BUTTERNUT)).setRegistryName("butternut_nut_yellow"),
                new NutRecipe(BlockLogTFC.get(butternut), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_BUTTERNUT), new ItemStack(ItemsTFCF.BUTTERNUT)).setRegistryName("butternut_nut_orange"),
                new NutRecipe(BlockLogTFC.get(butternut), BlockLeavesTFCF.get(SeasonalTrees.RED_BUTTERNUT), new ItemStack(ItemsTFCF.BUTTERNUT)).setRegistryName("butternut_nut_red"),
                new NutRecipe(BlockLogTFC.get(ginkgo), BlockLeavesTFC.get(ginkgo), new ItemStack(ItemsTFCF.GINKGO_NUT)).setRegistryName("ginkgo_nut"),
                new NutRecipe(BlockLogTFC.get(ginkgo), BlockLeavesTFCF.get(SeasonalTrees.GINKGO), new ItemStack(ItemsTFCF.GINKGO_NUT)).setRegistryName("ginkgo_nut_yellow"),
                new NutRecipe(BlockLogTFC.get(hazel), BlockLeavesTFC.get(hazel), new ItemStack(ItemsTFCF.HAZELNUT)).setRegistryName("hazel_nut"),
                new NutRecipe(BlockLogTFC.get(hazel), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_HAZEL), new ItemStack(ItemsTFCF.HAZELNUT)).setRegistryName("hazel_nut_yellow"),
                new NutRecipe(BlockLogTFC.get(hazel), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_HAZEL), new ItemStack(ItemsTFCF.HAZELNUT)).setRegistryName("hazel_nut_orange"),
                new NutRecipe(BlockLogTFC.get(hazel), BlockLeavesTFCF.get(SeasonalTrees.RED_HAZEL), new ItemStack(ItemsTFCF.HAZELNUT)).setRegistryName("hazel_nut_red"),
                new NutRecipe(BlockLogTFC.get(hemlock), BlockLeavesTFC.get(hemlock), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("hemlock_pinecone"),
                new NutRecipe(BlockLogTFC.get(ironwood), BlockLeavesTFC.get(ironwood), new ItemStack(ItemsTFCF.HOPS)).setRegistryName("ironwood_hops"),
                new NutRecipe(BlockLogTFC.get(kauri), BlockLeavesTFC.get(kauri), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("kauri_pinecone"),
                new NutRecipe(BlockLogTFC.get(larch), BlockLeavesTFCF.get(SeasonalTrees.LARCH), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("larch_pinecone"),
                new NutRecipe(BlockLogTFC.get(nordmann_fir), BlockLeavesTFC.get(nordmann_fir), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("nordmann_fir_pinecone"),
                new NutRecipe(BlockLogTFC.get(norway_spruce), BlockLeavesTFC.get(norway_spruce), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("norway_spruce_pinecone"),
                new NutRecipe(BlockLogTFC.get(redwood), BlockLeavesTFC.get(redwood), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("redwood_pinecone"),
                new NutRecipe(BlockLogTFC.get(walnut), BlockLeavesTFC.get(walnut), new ItemStack(ItemsTFCF.WALNUT)).setRegistryName("walnut_fruit"),
                new NutRecipe(BlockLogTFC.get(walnut), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_WALNUT), new ItemStack(ItemsTFCF.WALNUT)).setRegistryName("walnut_nut_yellow"),
                new NutRecipe(BlockLogTFC.get(walnut), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_WALNUT), new ItemStack(ItemsTFCF.WALNUT)).setRegistryName("walnut_nut_orange"),
                new NutRecipe(BlockLogTFC.get(walnut), BlockLeavesTFCF.get(SeasonalTrees.RED_WALNUT), new ItemStack(ItemsTFCF.WALNUT)).setRegistryName("walnut_nut_red")
            );
        }
    }

    @SubscribeEvent
    public static void onRegisterCrackingRecipeEvent(RegistryEvent.Register<CrackingRecipe> event)
    {
        if (TFCFlorae.FirmaLifeAdded)
        {
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
            );
        }
    }

    @SubscribeEvent
    public static void onRegisterOvenRecipeEventFL(RegistryEvent.Register<OvenRecipe> event)
    {
        if (TFCFlorae.FirmaLifeAdded)
        {
            IForgeRegistry<OvenRecipe> r = event.getRegistry();
            int hour = ICalendar.TICKS_IN_HOUR;

            // Mud Pottery
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
            {
                ItemFiredMudBrick firedMudBrick = ItemFiredMudBrick.get(ItemUnfiredMudBrick.get(rock));

                OvenRecipe mudBrick = new OvenRecipe(IIngredient.of(ItemUnfiredMudBrick.get(rock)), new ItemStack(firedMudBrick), 4 * hour);
                event.getRegistry().register(mudBrick.setRegistryName(rock.getRegistryName().getPath().toLowerCase() + "_unfired_mud_brick"));
            }

            r.registerAll(
                new OvenRecipe(IIngredient.of(ItemsTFCF.HASH_MUFFIN_DOUGH), new ItemStack(ItemsTFCF.HASH_MUFFIN), 4 * hour).setRegistryName(TFCFlorae.MODID, "hash_muffin_dough_oven"),
                new OvenRecipe(IIngredient.of(ItemsTFCF.AMARANTH_DOUGH), new ItemStack(ItemsTFCF.AMARANTH_BREAD), 4 * hour).setRegistryName(TFCFlorae.MODID, "amaranth_dough_oven"),
                new OvenRecipe(IIngredient.of(ItemsTFCF.BUCKWHEAT_DOUGH), new ItemStack(ItemsTFCF.BUCKWHEAT_BREAD), 4 * hour).setRegistryName(TFCFlorae.MODID, "buckwheat_dough_oven"),
                new OvenRecipe(IIngredient.of(ItemsTFCF.FONIO_DOUGH), new ItemStack(ItemsTFCF.FONIO_BREAD), 4 * hour).setRegistryName(TFCFlorae.MODID, "fonio_dough_oven"),
                new OvenRecipe(IIngredient.of(ItemsTFCF.MILLET_DOUGH), new ItemStack(ItemsTFCF.MILLET_BREAD), 4 * hour).setRegistryName(TFCFlorae.MODID, "millet_dough_oven"),
                new OvenRecipe(IIngredient.of(ItemsTFCF.QUINOA_DOUGH), new ItemStack(ItemsTFCF.QUINOA_BREAD), 4 * hour).setRegistryName(TFCFlorae.MODID, "quinoa_dough_oven"),
                new OvenRecipe(IIngredient.of(ItemsTFCF.SPELT_DOUGH), new ItemStack(ItemsTFCF.SPELT_BREAD), 4 * hour).setRegistryName(TFCFlorae.MODID, "spelt_dough_oven"),

                new OvenRecipe(IIngredient.of("amaranthFlatbreadDough"), new ItemStack(ItemsTFCF.AMARANTH_FLATBREAD), 4 * hour).setRegistryName(TFCFlorae.MODID, "amaranth_flatbread_dough_oven"),
                new OvenRecipe(IIngredient.of("buckwheatFlatbreadDough"), new ItemStack(ItemsTFCF.BUCKWHEAT_FLATBREAD), 4 * hour).setRegistryName(TFCFlorae.MODID, "buckwheat_flatbread_dough_oven"),
                new OvenRecipe(IIngredient.of("fonioFlatbreadDough"), new ItemStack(ItemsTFCF.FONIO_FLATBREAD), 4 * hour).setRegistryName(TFCFlorae.MODID, "fonio_flatbread_dough_oven"),
                new OvenRecipe(IIngredient.of("milletFlatbreadDough"), new ItemStack(ItemsTFCF.MILLET_FLATBREAD), 4 * hour).setRegistryName(TFCFlorae.MODID, "millet_flatbread_dough_oven"),
                new OvenRecipe(IIngredient.of("quinoaFlatbreadDough"), new ItemStack(ItemsTFCF.QUINOA_FLATBREAD), 4 * hour).setRegistryName(TFCFlorae.MODID, "quinoa_flatbread_dough_oven"),
                new OvenRecipe(IIngredient.of("speltFlatbreadDough"), new ItemStack(ItemsTFCF.SPELT_FLATBREAD), 4 * hour).setRegistryName(TFCFlorae.MODID, "spelt_flatbread_dough_oven"),

                //new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.ACORN_NUT)), new ItemStack(ItemsTFCF.ROASTED_ACORN_NUT), 2 * hour).setRegistryName("acorn_roasted_oven"),
                new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.BEECHNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BEECHNUT_NUT), 2 * hour).setRegistryName("beechnut_roasted_oven"),
                new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.BLACK_WALNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BLACK_WALNUT_NUT), 2 * hour).setRegistryName("black_walnut_roasted_oven"),
                new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.BUTTERNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BUTTERNUT_NUT), 2 * hour).setRegistryName("butternut_roasted_oven"),
                //new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.CHESTNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_CHESTNUT_NUT), 2 * hour).setRegistryName("chestnut_roasted_oven"),
                new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.GINKGO_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_GINKGO_NUT_NUT), 2 * hour).setRegistryName("ginkgo_roasted_oven"),
                new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.HAZELNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HAZELNUT_NUT), 2 * hour).setRegistryName("hazelnut_roasted_oven"),
                //new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.HICKORY_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HICKORY_NUT_NUT), 2 * hour).setRegistryName("hickory_roasted_oven"),
                //new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.PINE_NUT)), new ItemStack(ItemsTFCF.ROASTED_PINE_NUT), 2 * hour).setRegistryName("pine_nut_roasted_oven"),
                //new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.PECAN_NUT)), new ItemStack(ItemsTFCF.ROASTED_PECAN_NUT), 2 * hour).setRegistryName("pecan_roasted_oven"),
                new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.WALNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_WALNUT_NUT), 2 * hour).setRegistryName("walnut_roasted_oven"),
                new OvenRecipe(IIngredient.of(new ItemStack(ItemsFL.getFood(FoodFL.ACORN_FRUIT))), new ItemStack(ItemsTFCF.ROASTED_ACORN_NUT), 2 * hour).setRegistryName("acorn_roasted_oven_fl"),
                new OvenRecipe(IIngredient.of(new ItemStack(ItemsFL.getFood(FoodFL.PINE_NUTS))), new ItemStack(ItemsTFCF.ROASTED_PINE_NUT), 2 * hour).setRegistryName("pine_nut_roasted_oven_fl"),
                new OvenRecipe(IIngredient.of(new ItemStack(ItemsFL.getFood(FoodFL.PECAN_NUTS))), new ItemStack(ItemsTFCF.ROASTED_PECAN_NUT), 2 * hour).setRegistryName("pecan_roasted_oven_fl"),
                
                new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.DRIED_COFFEA_CHERRIES)), new ItemStack(ItemsTFCF.ROASTED_COFFEE_BEANS), 2 * hour).setRegistryName("coffee_beans_roasted_oven"),
                
                new OvenRecipe(IIngredient.of("epiphyteArtistsConk"), new ItemStack(ItemsTFCF.ROASTED_ARTISTS_CONK), 2 * hour).setRegistryName("roasted_artists_conk_oven"),
                new OvenRecipe(IIngredient.of("epiphyteSulphurShelf"), new ItemStack(ItemsTFCF.ROASTED_SULPHUR_SHELF), 2 * hour).setRegistryName("roasted_sulphur_shelf_oven"),
                new OvenRecipe(IIngredient.of("epiphyteTurkeyTail"), new ItemStack(ItemsTFCF.ROASTED_TURKEY_TAIL), 2 * hour).setRegistryName("roasted_turkey_tail_oven"),
                new OvenRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.PORCINI))), new ItemStack(ItemsTFCF.ROASTED_PORCINI), 2 * hour).setRegistryName("roasted_porcini_oven_specific"),
                new OvenRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.AMANITA))), new ItemStack(ItemsTFCF.ROASTED_AMANITA), 2 * hour).setRegistryName("roasted_amanita_oven_specific"),
                new OvenRecipe(IIngredient.of("mushroomPorcini"), new ItemStack(ItemsTFCF.ROASTED_PORCINI), 2 * hour).setRegistryName("roasted_porcini_oven"),
                new OvenRecipe(IIngredient.of("mushroomAmanita"), new ItemStack(ItemsTFCF.ROASTED_AMANITA), 2 * hour).setRegistryName("roasted_amanita_oven"),
                new OvenRecipe(IIngredient.of("mushroomBlackPowderpuff"), new ItemStack(ItemsTFCF.ROASTED_BLACK_POWDERPUFF), 2 * hour).setRegistryName("roasted_black_powderpuff_oven"),
                new OvenRecipe(IIngredient.of("mushroomChanterelle"), new ItemStack(ItemsTFCF.ROASTED_CHANTERELLE), 2 * hour).setRegistryName("roasted_chanterelle_oven"),
                new OvenRecipe(IIngredient.of("mushroomDeathCap"), new ItemStack(ItemsTFCF.ROASTED_DEATH_CAP), 2 * hour).setRegistryName("roasted_death_cap_oven"),
                new OvenRecipe(IIngredient.of("mushroomGiantClub"), new ItemStack(ItemsTFCF.ROASTED_GIANT_CLUB), 2 * hour).setRegistryName("roasted_giant_club_oven"),
                new OvenRecipe(IIngredient.of("mushroomParasol"), new ItemStack(ItemsTFCF.ROASTED_PARASOL_MUSHROOM), 2 * hour).setRegistryName("roasted_parasol_mushroom_oven"),
                new OvenRecipe(IIngredient.of("mushroomStinkhorn"), new ItemStack(ItemsTFCF.ROASTED_STINKHORN), 2 * hour).setRegistryName("roasted_stinkhorn_oven"),
                new OvenRecipe(IIngredient.of("mushroomWeepingMilkCap"), new ItemStack(ItemsTFCF.ROASTED_WEEPING_MILK_CAP), 2 * hour).setRegistryName("roasted_weeping_milk_cap_oven"),
                new OvenRecipe(IIngredient.of("mushroomWoodBlewit"), new ItemStack(ItemsTFCF.ROASTED_WOOD_BLEWIT), 2 * hour).setRegistryName("roasted_wood_blewit_oven"),
                new OvenRecipe(IIngredient.of("mushroomWoollyGomphus"), new ItemStack(ItemsTFCF.ROASTED_WOOLLY_GOMPHUS), 2 * hour).setRegistryName("roasted_woolly_gomphus_oven"),

                new OvenRecipe(IIngredient.of("rawEel"), new ItemStack(ItemsTFCF.COOKED_EEL), 2 * hour).setRegistryName("cooked_eel_oven"),
                new OvenRecipe(IIngredient.of("rawCrab"), new ItemStack(ItemsTFCF.COOKED_CRAB), 2 * hour).setRegistryName("cooked_crab_oven"),
                new OvenRecipe(IIngredient.of("rawClam"), new ItemStack(ItemsTFCF.COOKED_CLAM), 2 * hour).setRegistryName("cooked_clam_oven"),
                new OvenRecipe(IIngredient.of("rawScallop"), new ItemStack(ItemsTFCF.COOKED_SCALLOP), 2 * hour).setRegistryName("cooked_scallop_oven"),
                new OvenRecipe(IIngredient.of("rawStarfish"), new ItemStack(ItemsTFCF.COOKED_STARFISH), 2 * hour).setRegistryName("cooked_starfish_oven"),
                new OvenRecipe(IIngredient.of("rawSnail"), new ItemStack(ItemsTFCF.COOKED_SNAIL), 2 * hour).setRegistryName("cooked_snail_oven"),
                new OvenRecipe(IIngredient.of("rawWorm"), new ItemStack(ItemsTFCF.COOKED_WORM), 2 * hour).setRegistryName("cooked_worm_oven"),

                new OvenRecipe(IIngredient.of("clayKaolinite"), new ItemStack(ItemPowder.get(Powder.KAOLINITE)), 1 * hour).setRegistryName("kaolinite_clay_oven"),
                new OvenRecipe(IIngredient.of("bone"), new ItemStack(ItemsTFCF.CHARRED_BONES), 2 * hour).setRegistryName("charred_bones_heat_oven")
            );
        }
    }

    @SubscribeEvent
    public static void onRegisterAlembicRecipeEvent(RegistryEvent.Register<AlembicRecipe> event)
    {
        IForgeRegistry<AlembicRecipe> r = event.getRegistry();
        int day = ICalendar.TICKS_IN_DAY;

        r.registerAll(
            //new AlembicRecipe(IIngredient.of(FluidsTFC.SALT_WATER.get(), 1000), IIngredient.of("pasteSunflowerSeed"), new FluidStack(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("salt_water_distillation"),
        );

        /*
         * inputFluid + time => outputStack + outputFluid
         */
    }

    @SubscribeEvent
    public static void onRegisterStickBundleRecipeEvent(RegistryEvent.Register<StickBundleRecipe> event)
    {
        IForgeRegistry<StickBundleRecipe> r = event.getRegistry();
        int day = ICalendar.TICKS_IN_DAY;

        r.registerAll(
            new StickBundleRecipe(IIngredient.of(ItemsTFCF.SILK_WORM), new ItemStack(ItemsTFCF.SILK_WORM_COCOON), 3 * day).setRegistryName("silk_worm_cocoon")
        );
    }

    @SubscribeEvent
    public static void onRegisterDryingRecipeEvent(RegistryEvent.Register<tfcflorae.objects.recipes.DryingRecipe> event)
    {
        IForgeRegistry<tfcflorae.objects.recipes.DryingRecipe> r = event.getRegistry();
        int day = ICalendar.TICKS_IN_DAY;

        r.registerAll(
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.SILK_WORM_HATCHERY), new ItemStack(ItemsTFCF.SILK_WORM), 24000).setRegistryName("silk_worm_hatchery"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.CELLULOSE_FIBERS), new ItemStack(Items.PAPER), 24000).setRegistryName(TFCFlorae.MODID, "paper_from_cellulose_fibers"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.BLACK_TEA), new ItemStack(ItemsTFCF.DRIED_BLACK_TEA), 24000).setRegistryName(TFCFlorae.MODID, "dried_black_tea"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.GREEN_TEA), new ItemStack(ItemsTFCF.DRIED_GREEN_TEA), 24000).setRegistryName(TFCFlorae.MODID, "dried_green_tea"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.WHITE_TEA), new ItemStack(ItemsTFCF.DRIED_WHITE_TEA), 24000).setRegistryName(TFCFlorae.MODID, "dried_white_tea"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.CANNABIS_BUD), new ItemStack(ItemsTFCF.DRIED_CANNABIS_BUD), 24000).setRegistryName(TFCFlorae.MODID, "dried_cannabis_bud"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.CANNABIS_LEAF), new ItemStack(ItemsTFCF.DRIED_CANNABIS_LEAF), 24000).setRegistryName(TFCFlorae.MODID, "dried_cannabis_leaf"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.COCA_LEAF), new ItemStack(ItemsTFCF.DRIED_COCA_LEAF), 24000).setRegistryName(TFCFlorae.MODID, "dried_coca_leaf"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.OPIUM_POPPY_BULB), new ItemStack(ItemsTFCF.DRIED_OPIUM_POPPY_BULB), 24000).setRegistryName(TFCFlorae.MODID, "dried_opium_poppy_bulb"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.PEYOTE), new ItemStack(ItemsTFCF.DRIED_PEYOTE), 24000).setRegistryName(TFCFlorae.MODID, "dried_peyote"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.TOBACCO_LEAF), new ItemStack(ItemsTFCF.DRIED_TOBACCO_LEAF), 24000).setRegistryName(TFCFlorae.MODID, "dried_tobacco_leaf"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.COFFEA_CHERRIES), new ItemStack(ItemsTFCF.DRIED_COFFEA_CHERRIES), 24000).setRegistryName(TFCFlorae.MODID, "dried_coffea_cherries"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.CHAMOMILE_HEAD), new ItemStack(ItemsTFCF.DRIED_CHAMOMILE_HEAD), 24000).setRegistryName(TFCFlorae.MODID, "dried_chamomile_head"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.DANDELION_HEAD), new ItemStack(ItemsTFCF.DRIED_DANDELION_HEAD), 24000).setRegistryName(TFCFlorae.MODID, "dried_dandelion_head"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.LABRADOR_TEA_HEAD), new ItemStack(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD), 24000).setRegistryName("dried_labrador_tea_head"),
            new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemsTFCF.SUNFLOWER_HEAD), new ItemStack(ItemsTFCF.DRIED_SUNFLOWER_HEAD), 24000).setRegistryName(TFCFlorae.MODID, "dried_sunflower_head")
        );

        // Mud Pottery
        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
        {
            ItemFiredMudBrick firedMudBrick = ItemFiredMudBrick.get(ItemUnfiredMudBrick.get(rock));

            tfcflorae.objects.recipes.DryingRecipe mud = new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(ItemUnfiredMudBrick.get(rock)), new ItemStack(firedMudBrick), 6000);
            event.getRegistry().register(mud.setRegistryName(TFCFlorae.MODID, rock.getRegistryName().getPath().toLowerCase() + "_wet_mud_brick"));
        }

        if (TFCFlorae.FirmaLifeAdded)
        {
            for (Fruit fruit : Fruit.values())
            {
                r.register(new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(fruit.getFruit()), new ItemStack(ItemsFL.getDriedFruit(fruit)), day / 2).setRegistryName(TFCFlorae.MODID, fruit.name().toLowerCase()));
            }

            r.registerAll(
                new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(new ItemStack(ItemsFL.CINNAMON_BARK)), new ItemStack(ItemsFL.CINNAMON), day).setRegistryName(TFCFlorae.MODID, "cinnamon_bark"),
                new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(new ItemStack(ItemsFL.getFood(FoodFL.COCOA_BEANS))), new ItemStack(ItemsFL.getFood(FoodFL.DRIED_COCOA_BEANS)), day / 2).setRegistryName(TFCFlorae.MODID, "cocoa_beans"),
                new tfcflorae.objects.recipes.DryingRecipe(IIngredient.of(new ItemStack(ItemsFL.getFood(FoodFL.PINEAPPLE))), new ItemStack(ItemsFL.DRIED_PINEAPPLE), day / 2).setRegistryName(TFCFlorae.MODID, "pineapple")
            );
        }
    }

    @SubscribeEvent
    public static void onRegisterDryingRecipeEventFL(RegistryEvent.Register<DryingRecipe> event)
    {
        int day = ICalendar.TICKS_IN_DAY;

        if (TFCFlorae.FirmaLifeAdded)
        {
            IForgeRegistry<DryingRecipe> r = event.getRegistry();
            r.registerAll(
                new DryingRecipe(IIngredient.of(ItemsTFCF.SILK_WORM_HATCHERY), new ItemStack(ItemsTFCF.SILK_WORM), 24000).setRegistryName(TFCFlorae.MODID, "silk_worm_hatchery_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.CELLULOSE_FIBERS), new ItemStack(Items.PAPER), 24000).setRegistryName(TFCFlorae.MODID, "paper_from_cellulose_fibers_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.BLACK_TEA), new ItemStack(ItemsTFCF.DRIED_BLACK_TEA), 24000).setRegistryName(TFCFlorae.MODID, "dried_black_tea_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.GREEN_TEA), new ItemStack(ItemsTFCF.DRIED_GREEN_TEA), 24000).setRegistryName(TFCFlorae.MODID, "dried_green_tea_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.WHITE_TEA), new ItemStack(ItemsTFCF.DRIED_WHITE_TEA), 24000).setRegistryName(TFCFlorae.MODID, "dried_white_tea_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.CANNABIS_BUD), new ItemStack(ItemsTFCF.DRIED_CANNABIS_BUD), 24000).setRegistryName(TFCFlorae.MODID, "dried_cannabis_bud_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.CANNABIS_LEAF), new ItemStack(ItemsTFCF.DRIED_CANNABIS_LEAF), 24000).setRegistryName(TFCFlorae.MODID, "dried_cannabis_leaf_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.COCA_LEAF), new ItemStack(ItemsTFCF.DRIED_COCA_LEAF), 24000).setRegistryName(TFCFlorae.MODID, "dried_coca_leaf_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.OPIUM_POPPY_BULB), new ItemStack(ItemsTFCF.DRIED_OPIUM_POPPY_BULB), 24000).setRegistryName(TFCFlorae.MODID, "dried_opium_poppy_bulb_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.PEYOTE), new ItemStack(ItemsTFCF.DRIED_PEYOTE), 24000).setRegistryName(TFCFlorae.MODID, "dried_peyote_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.TOBACCO_LEAF), new ItemStack(ItemsTFCF.DRIED_TOBACCO_LEAF), 24000).setRegistryName(TFCFlorae.MODID, "dried_tobacco_leaf_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.COFFEA_CHERRIES), new ItemStack(ItemsTFCF.DRIED_COFFEA_CHERRIES), 24000).setRegistryName(TFCFlorae.MODID, "dried_coffea_cherries_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.CHAMOMILE_HEAD), new ItemStack(ItemsTFCF.DRIED_CHAMOMILE_HEAD), 24000).setRegistryName(TFCFlorae.MODID, "dried_chamomile_head_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.DANDELION_HEAD), new ItemStack(ItemsTFCF.DRIED_DANDELION_HEAD), 24000).setRegistryName(TFCFlorae.MODID, "dried_dandelion_head_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.LABRADOR_TEA_HEAD), new ItemStack(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD), 24000).setRegistryName("dried_labrador_tea_head_firmalife"),
                new DryingRecipe(IIngredient.of(ItemsTFCF.SUNFLOWER_HEAD), new ItemStack(ItemsTFCF.DRIED_SUNFLOWER_HEAD), 24000).setRegistryName(TFCFlorae.MODID, "dried_sunflower_head_firmalife")
            );

            // Mud Pottery
            for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
            {
                ItemFiredMudBrick firedMudBrick = ItemFiredMudBrick.get(ItemUnfiredMudBrick.get(rock));

                DryingRecipe mud = new DryingRecipe(IIngredient.of(ItemUnfiredMudBrick.get(rock)), new ItemStack(firedMudBrick), 6000);
                event.getRegistry().register(mud.setRegistryName(TFCFlorae.MODID, rock.getRegistryName().getPath().toLowerCase() + "_wet_mud_brick_firmalife"));
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterPlanterEvent(RegistryEvent.Register<PlanterRecipe> event)
    {
        IForgeRegistry<PlanterRecipe> r = event.getRegistry();
        r.registerAll(
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.BLACK_EYED_PEAS)), new ItemStack(ItemsTFCF.BLACK_EYED_PEAS), 7, true).setRegistryName("black_eyed_peas"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.CAYENNE_PEPPER)), new ItemStack(ItemsTFCF.RED_CAYENNE_PEPPER), 7, true).setRegistryName("red_cayenne_pepper"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.GINSENG)), new ItemStack(ItemsTFCF.GINSENG), 5, false).setRegistryName("ginseng"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.RUTABAGA)), new ItemStack(ItemsTFCF.RUTABAGA), 7, false).setRegistryName("rutabaga"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.TURNIP)), new ItemStack(ItemsTFCF.TURNIP), 8, false).setRegistryName("turnip"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.SUGAR_BEET)), new ItemStack(ItemsTFCF.SUGAR_BEET), 7, false).setRegistryName("sugar_beet"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.PURPLE_GRAPE)), new ItemStack(ItemsTFCF.PURPLE_GRAPE), 8, false).setRegistryName("purple_grape"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.GREEN_GRAPE)), new ItemStack(ItemsTFCF.GREEN_GRAPE), 8, false).setRegistryName("green_grape"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.LIQUORICE_ROOT)), new ItemStack(ItemsTFCF.LIQUORICE_ROOT), 8, false).setRegistryName("liquorice_root"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.COFFEA)), new ItemStack(ItemsTFCF.COFFEA_CHERRIES), 8, false).setRegistryName("coffea_cherries"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.AGAVE)), new ItemStack(ItemsTFCF.AGAVE), 6, false).setRegistryName("agave"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.COCA)), new ItemStack(ItemsTFCF.COCA_LEAF), 6, true).setRegistryName("coca"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.COTTON)), new ItemStack(ItemsTFCF.COTTON_BOLL), 6, false).setRegistryName("cotton"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.FLAX)), new ItemStack(ItemsTFCF.FLAX), 6, true).setRegistryName("flax"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.HEMP)), new ItemStack(ItemsTFCF.HEMP), 5, true).setRegistryName("hemp"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.HOP)), new ItemStack(ItemsTFCF.HOPS), 6, true).setRegistryName("hop"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.INDIGO)), new ItemStack(ItemsTFCF.INDIGO), 5, true).setRegistryName("indigo"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.MADDER)), new ItemStack(ItemsTFCF.MADDER), 5, false).setRegistryName("madder"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.OPIUM_POPPY)), new ItemStack(ItemsTFCF.OPIUM_POPPY_BULB), 6, false).setRegistryName("opium_poppy"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.RAPE)), new ItemStack(ItemsTFCF.RAPE), 6, false).setRegistryName("rape"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.WELD)), new ItemStack(ItemsTFCF.WELD), 5, true).setRegistryName("weld"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.WOAD)), new ItemStack(ItemsTFCF.WOAD), 6, false).setRegistryName("woad"),
            new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.TOBACCO)), new ItemStack(ItemsTFCF.TOBACCO_LEAF), 7, true).setRegistryName("tobacco")
        );
    }

    @SubscribeEvent
    public static void onRegisterKnappingRecipeEventTFCE(RegistryEvent.Register<KnappingRecipe> event)
    {
        if (TFCFlorae.TFCElementiaAdded)
        {
            IForgeRegistry<KnappingRecipe> r = event.getRegistry();
            
            for (ItemMetalTFCE.ItemType type : ItemMetalTFCE.ItemType.values())
            {
                if (!type.isTypeActive()) continue;
                if (type.hasMold(null))
                {
                    int amount = type == ItemMetalTFCE.ItemType.NAIL || type == ItemMetalTFCE.ItemType.RING ? 2 : 1;
                    event.getRegistry().register(new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemUnfiredEarthenwareMoldTFCE.get(type), amount), type.getPattern()).setRegistryName(type.name().toLowerCase() + "_earthenware_mold"));
                    event.getRegistry().register(new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemUnfiredKaoliniteMoldTFCE.get(type), amount), type.getPattern()).setRegistryName(type.name().toLowerCase() + "_kaolinite_mold"));
                    event.getRegistry().register(new KnappingRecipeSimple(KnappingTypes.KAOLINITE_CLAY, true, new ItemStack(ItemUnfiredStonewareMoldTFCE.get(type), amount), type.getPattern()).setRegistryName(type.name().toLowerCase() + "_stoneware_mold"));
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
                if (!type.isTypeActive()) continue;
                ItemUnfiredEarthenwareMoldTFCE unfiredMoldEarthenware = ItemUnfiredEarthenwareMoldTFCE.get(type);
                ItemEarthenwareMoldTFCE firedMoldEarthenware = ItemEarthenwareMoldTFCE.get(type);
                if (unfiredMoldEarthenware != null && firedMoldEarthenware != null)
                {
                    r.register(new HeatRecipeSimple(IIngredient.of(unfiredMoldEarthenware), new ItemStack(firedMoldEarthenware), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_earthenware_mold_" + type.name().toLowerCase()));
                }

                if (!type.isTypeActive()) continue;
                ItemUnfiredKaoliniteMoldTFCE unfiredMoldKaolinite = ItemUnfiredKaoliniteMoldTFCE.get(type);
                ItemKaoliniteMoldTFCE firedMoldKaolinite = ItemKaoliniteMoldTFCE.get(type);
                if (unfiredMoldKaolinite != null && firedMoldKaolinite != null)
                {
                    r.register(new HeatRecipeSimple(IIngredient.of(unfiredMoldKaolinite), new ItemStack(firedMoldKaolinite), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_kaolinite_mold_" + type.name().toLowerCase()));
                }

                if (!type.isTypeActive()) continue;
                ItemUnfiredStonewareMoldTFCE unfiredMoldStoneware = ItemUnfiredStonewareMoldTFCE.get(type);
                ItemStonewareMoldTFCE firedMoldStoneware = ItemStonewareMoldTFCE.get(type);
                if (unfiredMoldStoneware != null && firedMoldStoneware != null)
                {
                    r.register(new HeatRecipeSimple(IIngredient.of(unfiredMoldStoneware), new ItemStack(firedMoldStoneware), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_stoneware_mold_" + type.name().toLowerCase()));
                }
            }
        }
    }
}