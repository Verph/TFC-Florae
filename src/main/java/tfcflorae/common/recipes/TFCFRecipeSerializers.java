package tfcflorae.common.recipes;

import java.util.function.Supplier;

import net.dries007.tfc.common.recipes.KnappingRecipe;
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

    public static final RegistryObject<BrushingRecipe.Serializer> BRUSHING = register("brushing", BrushingRecipe.Serializer::new);

    public static final RegistryObject<KnappingRecipe.Serializer> EARTHENWARE_CLAY_KNAPPING = register("earthenware_clay_knapping", () -> new KnappingRecipe.Serializer(TFCFRecipeTypes.EARTHENWARE_CLAY_KNAPPING));
    public static final RegistryObject<KnappingRecipe.Serializer> KAOLINITE_CLAY_KNAPPING = register("kaolinite_clay_knapping", () -> new KnappingRecipe.Serializer(TFCFRecipeTypes.KAOLINITE_CLAY_KNAPPING));
    public static final RegistryObject<KnappingRecipe.Serializer> STONEWARE_CLAY_KNAPPING = register("stoneware_clay_knapping", () -> new KnappingRecipe.Serializer(TFCFRecipeTypes.STONEWARE_CLAY_KNAPPING));
    public static final RegistryObject<KnappingRecipe.Serializer> FLINT_KNAPPING = register("flint_knapping", () -> new KnappingRecipe.Serializer(TFCFRecipeTypes.FLINT_KNAPPING));

    private static <S extends RecipeSerializer<?>> RegistryObject<S> register(String name, Supplier<S> factory)
    {
        return RECIPE_SERIALIZERS.register(name, factory);
    }
}