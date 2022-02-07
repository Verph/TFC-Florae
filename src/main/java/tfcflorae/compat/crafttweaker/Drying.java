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
import tfcflorae.objects.recipes.DryingRecipe;

@ZenClass("mods.tfcflorae.Drying")
@ZenRegister
@SuppressWarnings("unused")
public class Drying
{
    @ZenMethod
    public static void addRecipe(String recipe_name, IIngredient input, IItemStack output, int duration)
    {
        DryingRecipe recipe = new DryingRecipe(CTHelper.getInternalIngredient(input), InputHelper.toStack(output), duration).setRegistryName(recipe_name);
        CraftTweakerAPI.apply(new IAction()
        {
            @Override
            public void apply()
            {
                TFCFRegistries.DRYING.register(recipe);
            }

            @Override
            public String describe()
            {
                return "Adding Drying recipe " +recipe.getRegistryName().toString();
            }
        });
    }

    @ZenMethod
    public static void removeRecipe(String recipe_name)
    {
        DryingRecipe recipe = TFCFRegistries.DRYING.getValue(new ResourceLocation(recipe_name));

        if(recipe != null)
        {
            CraftTweakerAPI.apply(new IAction()
            {
                @Override
                public void apply()
                {
                    IForgeRegistryModifiable<DryingRecipe> DRYING = (IForgeRegistryModifiable<DryingRecipe>) TFCFRegistries.DRYING;
                    DRYING.remove(recipe.getRegistryName());
                }

                @Override
                public String describe()
                {
                    return "Removing Drying recipe " + recipe_name;
                }
            });
        }
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output)
    {
        if (output == null) throw new IllegalArgumentException("Output not allowed to be empty");
        ArrayList<DryingRecipe> removeList = new ArrayList<>();

        TFCFRegistries.DRYING.getValuesCollection()
                .stream()
                .filter(x -> x.getOutputItem(ItemStack.EMPTY).isItemEqual(InputHelper.toStack(output)))
                .forEach(removeList::add);

        for(DryingRecipe recipe : removeList)
        {
            CraftTweakerAPI.apply(new IAction()
            {
                @Override
                public void apply()
                {
                    IForgeRegistryModifiable<DryingRecipe> DRYING = (IForgeRegistryModifiable<DryingRecipe>) TFCFRegistries.DRYING;
                    DRYING.remove(recipe.getRegistryName());
                }

                @Override
                public String describe()
                {
                    return "Removing Drying recipe for output " + output.getDisplayName();
                }
            });
        }
    }
}