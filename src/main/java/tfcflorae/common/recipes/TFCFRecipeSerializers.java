package tfcflorae.common.recipes;

import java.util.function.Supplier;

import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static tfcflorae.TFCFlorae.MOD_ID;

@SuppressWarnings("unused")
public class TFCFRecipeSerializers
{
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);

    // Complex Recipes

    public static final RegistryObject<TFCFKnappingRecipe.Serializer> EARTHENWARE_CLAY_KNAPPING = register("earthenware_clay_knapping", () -> new TFCFKnappingRecipe.Serializer(TFCFRecipeTypes.EARTHENWARE_CLAY_KNAPPING));
    public static final RegistryObject<TFCFKnappingRecipe.Serializer> KAOLINITE_CLAY_KNAPPING = register("kaolinite_clay_knapping", () -> new TFCFKnappingRecipe.Serializer(TFCFRecipeTypes.KAOLINITE_CLAY_KNAPPING));
    public static final RegistryObject<TFCFKnappingRecipe.Serializer> STONEWARE_CLAY_KNAPPING = register("stoneware_clay_knapping", () -> new TFCFKnappingRecipe.Serializer(TFCFRecipeTypes.STONEWARE_CLAY_KNAPPING));

    private static <S extends RecipeSerializer<?>> RegistryObject<S> register(String name, Supplier<S> factory)
    {
        return RECIPE_SERIALIZERS.register(name, factory);
    }
}