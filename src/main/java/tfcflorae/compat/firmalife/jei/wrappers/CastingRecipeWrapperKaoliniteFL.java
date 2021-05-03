package tfcflorae.compat.firmalife.jei.wrappers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;

import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;

import tfcflorae.TFCFlorae;
import tfcflorae.objects.items.ItemsTFCF;

public class CastingRecipeWrapperKaoliniteFL implements IRecipeWrapper
{
    private final ItemStack mold;
    private final FluidStack input;

    public CastingRecipeWrapperKaoliniteFL(Metal metal, String type)
    {
        this.input = new FluidStack(FluidsTFC.getFluidFromMetal(metal), 100);
        this.mold = new ItemStack(ItemsTFCF.malletMoldKaolinite);
        IFluidHandler cap = this.mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        if (cap instanceof IMoldHandler)
        {
            cap.fill(this.input, true);
        }
    }

    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInput(VanillaTypes.FLUID, this.input);
        ingredients.setOutput(VanillaTypes.ITEM, this.mold);
    }
}