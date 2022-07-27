package tfcflorae.common.recipes;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static tfcflorae.TFCFlorae.MOD_ID;

public class TFCFRecipeTypes
{
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, MOD_ID);

    public static final RegistryObject<RecipeType<TFCFKnappingRecipe>> EARTHENWARE_CLAY_KNAPPING = register("earthenware_clay_knapping");
    public static final RegistryObject<RecipeType<TFCFKnappingRecipe>> KAOLINITE_CLAY_KNAPPING = register("kaolinite_clay_knapping");
    public static final RegistryObject<RecipeType<TFCFKnappingRecipe>> STONEWARE_CLAY_KNAPPING = register("stoneware_clay_knapping");

    private static <R extends Recipe<?>> RegistryObject<RecipeType<R>> register(String name)
    {
        return RECIPE_TYPES.register(name, () -> new RecipeType<>() {
            @Override
            public String toString()
            {
                return name;
            }
        });
    }
}