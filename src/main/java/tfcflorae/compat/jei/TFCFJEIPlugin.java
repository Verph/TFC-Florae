package tfcflorae.compat.jei;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.registries.ForgeRegistries;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.dries007.tfc.util.Helpers;

import tfcflorae.TFCFlorae;
import tfcflorae.common.TFCFTags;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.common.recipes.TFCFKnappingRecipe;
import tfcflorae.common.recipes.TFCFRecipeTypes;
import tfcflorae.compat.jei.category.TFCFKnappingRecipeCategory;
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

    private static final ResourceLocation EARTHENWARE_CLAY_TEXTURE = Helpers.identifier("textures/gui/knapping/earthenware_clay_ball.png");
    private static final ResourceLocation KAOLINITE_CLAY_TEXTURE = Helpers.identifier("textures/gui/knapping/kaolinite_clay_ball.png");
    private static final ResourceLocation STONEWARE_CLAY_TEXTURE = Helpers.identifier("textures/gui/knapping/stoneware_clay_ball.png");
    private static final ResourceLocation EARTHENWARE_CLAY_DISABLED_TEXTURE = Helpers.identifier("textures/gui/knapping/earthenware_clay_ball_disabled.png");
    private static final ResourceLocation KAOLINITE_CLAY_DISABLED_TEXTURE = Helpers.identifier("textures/gui/knapping/kaolinite_clay_ball_disabled.png");
    private static final ResourceLocation STONEWARE_CLAY_DISABLED_TEXTURE = Helpers.identifier("textures/gui/knapping/stoneware_clay_ball_disabled.png");

    public static final RecipeType<TFCFKnappingRecipe> EARTHENWARE_CLAY_KNAPPING = type("earthenware_clay_knapping", TFCFKnappingRecipe.class);
    public static final RecipeType<TFCFKnappingRecipe> KAOLINITE_CLAY_KNAPPING = type("kaolinite_clay_knapping", TFCFKnappingRecipe.class);
    public static final RecipeType<TFCFKnappingRecipe> STONEWARE_CLAY_KNAPPING = type("stoneware_clay_knapping", TFCFKnappingRecipe.class);

    @Override
    public ResourceLocation getPluginUid()
    {
        return TFCFHelpers.identifier("jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration r)
    {
        IGuiHelper gui = r.getJeiHelpers().getGuiHelper();
        r.addRecipeCategories(new TFCFKnappingRecipeCategory<>(EARTHENWARE_CLAY_KNAPPING, gui, new ItemStack(TFCFItems.EARTHENWARE_CLAY_BALL.get()), EARTHENWARE_CLAY_TEXTURE, EARTHENWARE_CLAY_DISABLED_TEXTURE));
        r.addRecipeCategories(new TFCFKnappingRecipeCategory<>(KAOLINITE_CLAY_KNAPPING, gui, new ItemStack(TFCFItems.KAOLINITE_CLAY_BALL.get()), KAOLINITE_CLAY_TEXTURE, KAOLINITE_CLAY_DISABLED_TEXTURE));
        r.addRecipeCategories(new TFCFKnappingRecipeCategory<>(STONEWARE_CLAY_KNAPPING, gui, new ItemStack(TFCFItems.STONEWARE_CLAY_BALL.get()), STONEWARE_CLAY_TEXTURE, STONEWARE_CLAY_DISABLED_TEXTURE));
    }

    @Override
    public void registerRecipes(IRecipeRegistration r)
    {
        r.addRecipes(EARTHENWARE_CLAY_KNAPPING, getRecipes(TFCFRecipeTypes.EARTHENWARE_CLAY_KNAPPING.get()));
        r.addRecipes(KAOLINITE_CLAY_KNAPPING, getRecipes(TFCFRecipeTypes.KAOLINITE_CLAY_KNAPPING.get()));
        r.addRecipes(STONEWARE_CLAY_KNAPPING, getRecipes(TFCFRecipeTypes.STONEWARE_CLAY_KNAPPING.get()));

        addIngredientInfo(r);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration r)
    {
        addCatalystTag(r, TFCFTags.Items.EARTHENWARE_CLAY_KNAPPING, EARTHENWARE_CLAY_KNAPPING);
        addCatalystTag(r, TFCFTags.Items.KAOLINITE_CLAY_KNAPPING, KAOLINITE_CLAY_KNAPPING);
        addCatalystTag(r, TFCFTags.Items.STONEWARE_CLAY_KNAPPING, STONEWARE_CLAY_KNAPPING);
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration r)
    {
        // todo: add bait, salting recipes
    }

    private void addIngredientInfo(IRecipeRegistration r)
    {
    }
}
