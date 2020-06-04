package tfcelementia.compat.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.compat.jei.wrappers.SimpleRecipeWrapper;
import tfcelementia.compat.jei.wrappers.CastingRecipeTFCEWrapper;
import tfcelementia.compat.jei.wrappers.UnmoldRecipeTFCEWrapper;
import tfcelementia.objects.items.ItemsTFCE;
import tfcelementia.objects.items.metal.ItemMetalTFCE;

import static tfcelementia.TFCElementia.MODID;

@JEIPlugin
public class TFCEJEIPlugin implements IModPlugin
{
    @Override
    public void register(IModRegistry registry)
    {
        //Wraps all unmold and casting recipes
        List<UnmoldRecipeTFCEWrapper> unmoldList = new ArrayList<>();
        List<CastingRecipeTFCEWrapper> castingList = new ArrayList<>();
        TFCRegistries.METALS.getValuesCollection()
            .forEach(metal -> {
                if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(true))
                {
                    for (ItemMetalTFCE.ItemType type : ItemMetalTFCE.ItemType.values())
                    {
                        if (type.hasMold())
                        {
                            unmoldList.add(new UnmoldRecipeTFCEWrapper(metal, type));
                            castingList.add(new CastingRecipeTFCEWrapper(metal, type));
                        }
                    }
                }
            }
        );
        registry.addRecipes(unmoldList, VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipes(castingList, "tfc.casting");
    }
}