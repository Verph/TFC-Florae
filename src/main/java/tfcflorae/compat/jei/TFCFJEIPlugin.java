package tfcflorae.compat.jei;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;

import net.dries007.tfc.common.recipes.KnappingRecipe;
import net.dries007.tfc.compat.jei.category.KnappingRecipeCategory;
import net.dries007.tfc.util.Helpers;

import tfcflorae.TFCFlorae;
import tfcflorae.common.TFCFTags;
import tfcflorae.common.blocks.ceramics.Clay;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.common.recipes.BrushingRecipe;
import tfcflorae.common.recipes.TFCFRecipeSerializers;
import tfcflorae.common.recipes.TFCFRecipeTypes;
import tfcflorae.compat.jei.category.BrushingRecipeCategory;
import tfcflorae.util.TFCFHelpers;

@JeiPlugin
public class TFCFJEIPlugin implements IModPlugin
{
    private static <C extends Container, T extends Recipe<C>> List<T> getRecipes(net.minecraft.world.item.crafting.RecipeType<T> type)
    {
        ClientLevel level = Minecraft.getInstance().level;
        assert level != null;
        return level.getRecipeManager().getAllRecipesFor(type);
    }

    private static <C extends Container, T extends Recipe<C>> List<T> getRecipes(net.minecraft.world.item.crafting.RecipeType<T> type, Predicate<T> filter)
    {
        return getRecipes(type).stream().filter(filter).collect(Collectors.toList());
    }

    private static void addCatalystTag(IRecipeCatalystRegistration r, TagKey<Item> tag, RecipeType<?> recipeType)
    {
        Helpers.getAllTagValues(tag, ForgeRegistries.ITEMS).forEach(item -> r.addRecipeCatalyst(new ItemStack(item), recipeType));
    }

    private static List<ItemStack> tagToItemList(TagKey<Item> tag)
    {
        return Helpers.getAllTagValues(tag, ForgeRegistries.ITEMS).stream().map(Item::getDefaultInstance).collect(Collectors.toList());
    }

    private static <T> RecipeType<T> type(String name, Class<T> tClass)
    {
        return RecipeType.create(TFCFlorae.MOD_ID, name, tClass);
    }

    private static final ResourceLocation EARTHENWARE_CLAY_TEXTURE = TFCFHelpers.identifier("textures/gui/knapping/ceramic/earthenware/clay.png");
    private static final ResourceLocation KAOLINITE_CLAY_TEXTURE = TFCFHelpers.identifier("textures/gui/knapping/ceramic/kaolinite/clay.png");
    private static final ResourceLocation STONEWARE_CLAY_TEXTURE = TFCFHelpers.identifier("textures/gui/knapping/ceramic/stoneware/clay.png");
    private static final ResourceLocation EARTHENWARE_CLAY_DISABLED_TEXTURE = TFCFHelpers.identifier("textures/gui/knapping/ceramic/earthenware/clay_disabled.png");
    private static final ResourceLocation KAOLINITE_CLAY_DISABLED_TEXTURE = TFCFHelpers.identifier("textures/gui/knapping/ceramic/kaolinite/clay_disabled.png");
    private static final ResourceLocation STONEWARE_CLAY_DISABLED_TEXTURE = TFCFHelpers.identifier("textures/gui/knapping/ceramic/stoneware/clay_disabled.png");
    private static final ResourceLocation FLINT_TEXTURE = TFCFHelpers.identifier("textures/gui/knapping/flint.png");

    public static final RecipeType<BrushingRecipe> BRUSHING = type("brushing", BrushingRecipe.class);
    public static final RecipeType<KnappingRecipe> EARTHENWARE_CLAY_KNAPPING = type("earthenware_clay_knapping", KnappingRecipe.class);
    public static final RecipeType<KnappingRecipe> KAOLINITE_CLAY_KNAPPING = type("kaolinite_clay_knapping", KnappingRecipe.class);
    public static final RecipeType<KnappingRecipe> STONEWARE_CLAY_KNAPPING = type("stoneware_clay_knapping", KnappingRecipe.class);
    public static final RecipeType<KnappingRecipe> FLINT_KNAPPING = type("flint_knapping", KnappingRecipe.class);

    @Override
    public ResourceLocation getPluginUid()
    {
        return TFCFHelpers.identifier("jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration r)
    {
        IGuiHelper gui = r.getJeiHelpers().getGuiHelper();
        r.addRecipeCategories(new BrushingRecipeCategory(BRUSHING, gui));
        r.addRecipeCategories(new KnappingRecipeCategory<>(EARTHENWARE_CLAY_KNAPPING, gui, new ItemStack(TFCFItems.CLAY_BALLS.get(Clay.EARTHENWARE).get()), EARTHENWARE_CLAY_TEXTURE, EARTHENWARE_CLAY_DISABLED_TEXTURE));
        r.addRecipeCategories(new KnappingRecipeCategory<>(KAOLINITE_CLAY_KNAPPING, gui, new ItemStack(TFCFItems.CLAY_BALLS.get(Clay.KAOLINITE).get()), KAOLINITE_CLAY_TEXTURE, KAOLINITE_CLAY_DISABLED_TEXTURE));
        r.addRecipeCategories(new KnappingRecipeCategory<>(STONEWARE_CLAY_KNAPPING, gui, new ItemStack(TFCFItems.CLAY_BALLS.get(Clay.STONEWARE).get()), STONEWARE_CLAY_TEXTURE, STONEWARE_CLAY_DISABLED_TEXTURE));
        r.addRecipeCategories(new KnappingRecipeCategory<>(FLINT_KNAPPING, gui, new ItemStack(Items.FLINT), FLINT_TEXTURE, null));
    }

    @Override
    public void registerRecipes(IRecipeRegistration r)
    {
        r.addRecipes(BRUSHING, getRecipes(TFCFRecipeTypes.BRUSHING.get()));
        r.addRecipes(EARTHENWARE_CLAY_KNAPPING, getRecipes(TFCFRecipeTypes.EARTHENWARE_CLAY_KNAPPING.get(), recipe -> recipe.getSerializer() == TFCFRecipeSerializers.EARTHENWARE_CLAY_KNAPPING.get()));
        r.addRecipes(KAOLINITE_CLAY_KNAPPING, getRecipes(TFCFRecipeTypes.KAOLINITE_CLAY_KNAPPING.get(), recipe -> recipe.getSerializer() == TFCFRecipeSerializers.KAOLINITE_CLAY_KNAPPING.get()));
        r.addRecipes(STONEWARE_CLAY_KNAPPING, getRecipes(TFCFRecipeTypes.STONEWARE_CLAY_KNAPPING.get(), recipe -> recipe.getSerializer() == TFCFRecipeSerializers.STONEWARE_CLAY_KNAPPING.get()));
        r.addRecipes(FLINT_KNAPPING, getRecipes(TFCFRecipeTypes.FLINT_KNAPPING.get(), recipe -> recipe.getSerializer() == TFCFRecipeSerializers.FLINT_KNAPPING.get()));

        addIngredientInfo(r);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration r)
    {
        addCatalystTag(r, TFCFTags.Items.EARTHENWARE_CLAY_KNAPPING, EARTHENWARE_CLAY_KNAPPING);
        addCatalystTag(r, TFCFTags.Items.KAOLINITE_CLAY_KNAPPING, KAOLINITE_CLAY_KNAPPING);
        addCatalystTag(r, TFCFTags.Items.STONEWARE_CLAY_KNAPPING, STONEWARE_CLAY_KNAPPING);
        addCatalystTag(r, TFCFTags.Items.FLINT_KNAPPING, FLINT_KNAPPING);
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration r)
    {
        // todo: add bait, salting recipes
    }

    private void addIngredientInfo(IRecipeRegistration r)
    {
    }

    private static void cat(IRecipeCatalystRegistration r, Supplier<? extends Block> supplier, RecipeType<?> type)
    {
        r.addRecipeCatalyst(new ItemStack(supplier.get()), type);
    }

    private static void cat(IRecipeCatalystRegistration r, Item item, RecipeType<?> type)
    {
        r.addRecipeCatalyst(new ItemStack(item), type);
    }
}
