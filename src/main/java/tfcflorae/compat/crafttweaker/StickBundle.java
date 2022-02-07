package tfcflorae.compat.crafttweaker;

import java.util.ArrayList;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import net.dries007.tfc.compat.crafttweaker.CTHelper;

import tfcflorae.api.registries.TFCFRegistries;
import tfcflorae.objects.recipes.StickBundleRecipe;

@ZenClass("mods.tfcflorae.StickBundle")
@ZenRegister
@SuppressWarnings("unused")
public class StickBundle
{
    @ZenMethod
    public static void addRecipe(String recipe_name, IIngredient input, IItemStack output, int duration)
    {
        StickBundleRecipe recipe = new StickBundleRecipe(CTHelper.getInternalIngredient(input), InputHelper.toStack(output), duration).setRegistryName(recipe_name);
        CraftTweakerAPI.apply(new IAction()
        {
            @Override
            public void apply()
            {
                TFCFRegistries.STICK_BUNDLE.register(recipe);
            }

            @Override
            public String describe()
            {
                return "Adding StickBundle recipe " +recipe.getRegistryName().toString();
            }
        });
    }

    @ZenMethod
    public static void removeRecipe(String recipe_name)
    {
        StickBundleRecipe recipe = TFCFRegistries.STICK_BUNDLE.getValue(new ResourceLocation(recipe_name));

        if(recipe != null)
        {
            CraftTweakerAPI.apply(new IAction()
            {
                @Override
                public void apply()
                {
                    IForgeRegistryModifiable<StickBundleRecipe> STICK_BUNDLE = (IForgeRegistryModifiable<StickBundleRecipe>) TFCFRegistries.STICK_BUNDLE;
                    STICK_BUNDLE.remove(recipe.getRegistryName());
                }

                @Override
                public String describe()
                {
                    return "Removing StickBundle recipe " + recipe_name;
                }
            });
        }
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output)
    {
        if (output == null) throw new IllegalArgumentException("Output not allowed to be empty");
        ArrayList<StickBundleRecipe> removeList = new ArrayList<>();

        TFCFRegistries.STICK_BUNDLE.getValuesCollection()
                .stream()
                .filter(x -> x.getOutputItem(ItemStack.EMPTY).isItemEqual(InputHelper.toStack(output)))
                .forEach(removeList::add);

        for(StickBundleRecipe recipe : removeList)
        {
            CraftTweakerAPI.apply(new IAction()
            {
                @Override
                public void apply()
                {
                    IForgeRegistryModifiable<StickBundleRecipe> STICK_BUNDLE = (IForgeRegistryModifiable<StickBundleRecipe>) TFCFRegistries.STICK_BUNDLE;
                    STICK_BUNDLE.remove(recipe.getRegistryName());
                }

                @Override
                public String describe()
                {
                    return "Removing StickBundle recipe for output " + output.getDisplayName();
                }
            });
        }
    }
}