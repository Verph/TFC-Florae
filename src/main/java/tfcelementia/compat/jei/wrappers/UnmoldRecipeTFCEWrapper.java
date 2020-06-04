package tfcelementia.compat.jei.wrappers;

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
import tfcelementia.objects.items.ceramics.ItemMoldTFCE;
import tfcelementia.objects.items.metal.ItemMetalTFCE;

public class UnmoldRecipeTFCEWrapper implements IRecipeWrapper
{
    private ItemStack mold;
    private ItemStack output;

    public UnmoldRecipeTFCEWrapper(Metal metal, ItemMetalTFCE.ItemType type)
    {
        mold = new ItemStack(ItemMoldTFCE.get(type));
        IFluidHandler cap = mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        if (cap instanceof IMoldHandler)
        {
            cap.fill(new FluidStack(FluidsTFC.getFluidFromMetal(metal), 100), true);
        }
        //noinspection ConstantConditions
        output = new ItemStack(ItemMetalTFCE.get(metal, type));
    }

    public UnmoldRecipeTFCEWrapper(ItemStack inputMold, ItemStack output)
    {
        this.mold = inputMold;
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInput(VanillaTypes.ITEM, mold);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}