package tfcelementia.objects.recipes;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.recipes.RecipeUtils;

import tfcelementia.objects.items.ceramics.ItemMoldTFCE;
import tfcelementia.objects.items.metal.ItemMetalTFCE;

import static net.dries007.tfc.api.capability.heat.CapabilityItemHeat.ITEM_HEAT_CAPABILITY;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

/**
 * Since TFC has Metal.ItemType we can't reuse {@link net.dries007.tfc.objects.recipes.UnmoldRecipe} directly
 */
@SuppressWarnings("WeakerAccess")
public class UnmoldRecipe extends ShapelessOreRecipe
{
    private ItemMetalTFCE.ItemType type;
    /* This is return chance, not break chance */
    private float chance;

    public UnmoldRecipe(ResourceLocation group, NonNullList<Ingredient> input, @Nonnull ItemMetalTFCE.ItemType type, float chance)
    {
        super(group, input, ItemStack.EMPTY);
        this.type = type;
        this.chance = chance;
    }

    @Override
    @Nonnull
    public NonNullList<ItemStack> getRemainingItems(final InventoryCrafting inv)
    {
        // Return empty molds
        for (int slot = 0; slot < inv.getSizeInventory(); slot++)
        {
            ItemStack stack = inv.getStackInSlot(slot);
            if (!stack.isEmpty())
            {
                if (stack.getItem() instanceof ItemMoldTFCE)
                {
                    // No need to check for the mold, as it has already been checked earlier
                    EntityPlayer player = ForgeHooks.getCraftingPlayer();
                    if (!player.world.isRemote)
                    {
                        if (Constants.RNG.nextFloat() <= chance)
                        {
                            // This can't use the remaining items, because vanilla doesn't sync them on crafting, thus it gives a desync error
                            // To fix: ContainerWorkbench#onCraftMatrixChanged needs to call Container#detectAndSendChanges
                            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(stack.getItem()));
                        }
                        else
                        {
                            player.world.playSound(null, player.getPosition(), TFCSounds.CERAMIC_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        }
                    }
                }
            }
        }
        return super.getRemainingItems(inv);
    }

    @Override
    public boolean isDynamic()
    {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() { return ItemStack.EMPTY; }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack moldStack = null;
        for (int slot = 0; slot < inv.getSizeInventory(); slot++)
        {
            ItemStack stack = inv.getStackInSlot(slot);
            if (!stack.isEmpty())
            {
                if (stack.getItem() instanceof ItemMoldTFCE)
                {
                    ItemMoldTFCE tmp = ((ItemMoldTFCE) stack.getItem());
                    if (tmp.type.equals(this.type) && moldStack == null)
                    {
                        moldStack = stack;
                    }
                    else
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else
                {
                    return ItemStack.EMPTY;
                }
            }
        }
        if (moldStack != null)
        {
            IFluidHandler moldCap = moldStack.getCapability(FLUID_HANDLER_CAPABILITY, null);
            if (moldCap instanceof IMoldHandler)
            {
                IMoldHandler moldHandler = (IMoldHandler) moldCap;
                if (!moldHandler.isMolten() && moldHandler.getAmount() == 100)
                {
                    return getOutputItem(moldHandler, this.type);
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World world)
    {
        boolean foundMold = false;
        for (int slot = 0; slot < inv.getSizeInventory(); slot++)
        {
            ItemStack stack = inv.getStackInSlot(slot);
            if (!stack.isEmpty())
            {
                if (stack.getItem() instanceof ItemMoldTFCE)
                {
                    ItemMoldTFCE moldItem = ((ItemMoldTFCE) stack.getItem());
                    IFluidHandler cap = stack.getCapability(FLUID_HANDLER_CAPABILITY, null);

                    if (cap instanceof IMoldHandler)
                    {
                        IMoldHandler moldHandler = (IMoldHandler) cap;
                        if (!moldHandler.isMolten())
                        {
                            Metal metal = moldHandler.getMetal();
                            if (metal != null && moldItem.type.equals(this.type) && !foundMold)
                            {
                                foundMold = true;
                            }
                            else
                            {
                                return false;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
        }
        return foundMold;
    }

    @Override
    @Nonnull
    public String getGroup()
    {
        return group == null ? "" : group.toString();
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return true;
    }

    private ItemStack getOutputItem(final IMoldHandler moldHandler, final ItemMetalTFCE.ItemType type)
    {
        Metal m = moldHandler.getMetal();
        if (m != null)
        {
            //noinspection ConstantConditions
            ItemStack output = new ItemStack(ItemMetalTFCE.get(m, type));
            IItemHeat heat = output.getCapability(ITEM_HEAT_CAPABILITY, null);
            if (heat != null)
            {
                heat.setTemperature(moldHandler.getTemperature());
            }
            return output;
        }
        return ItemStack.EMPTY;
    }

    @SuppressWarnings("unused")
    public static class Factory implements IRecipeFactory
    {
        @Override
        public IRecipe parse(final JsonContext context, final JsonObject json)
        {
            final NonNullList<Ingredient> ingredients = RecipeUtils.parseShapeless(context, json);
            final String result = JsonUtils.getString(json, "result");
            final ItemMetalTFCE.ItemType type = ItemMetalTFCE.ItemType.valueOf(result.toUpperCase());
            final String group = JsonUtils.getString(json, "group", "");

            //Chance of getting the mold back
            float chance = 0;
            if (JsonUtils.hasField(json, "chance"))
            {
                chance = JsonUtils.getFloat(json, "chance");
            }

            return new UnmoldRecipe(group.isEmpty() ? new ResourceLocation(result) : new ResourceLocation(group), ingredients, type, chance);
        }
    }
}