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

import com.eerussianguy.firmalife.registry.ItemsFL;

import tfcflorae.TFCFlorae;
import tfcflorae.objects.items.ItemsTFCF;

public class UnmoldRecipeWrapperStonewareFL implements IRecipeWrapper
{
    private final ItemStack mold;
    private final ItemStack output;

    public UnmoldRecipeWrapperStonewareFL(Metal metal, String type)
    {
        this.mold = new ItemStack(ItemsTFCF.malletMoldStoneware);
        IFluidHandler cap = this.mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        if (cap instanceof IMoldHandler)
        {
            cap.fill(new FluidStack(FluidsTFC.getFluidFromMetal(metal), 100), true);
        }
        this.output = new ItemStack(ItemsFL.getMetalMalletHead(metal));
    }

    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInput(VanillaTypes.ITEM, this.mold);
        ingredients.setOutput(VanillaTypes.ITEM, this.output);
    }
}