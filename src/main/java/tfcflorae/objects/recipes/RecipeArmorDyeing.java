package tfcflorae.objects.recipes;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.Lists;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.DyeUtils;
import net.minecraftforge.registries.IForgeRegistryEntry;

import tfcflorae.objects.ArmorMaterialsTFCF;
import tfcflorae.objects.items.ItemArmorTFCF;

@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
public class RecipeArmorDyeing extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    @Nonnull
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
        ItemStack itemStack = ItemStack.EMPTY;
        List<ItemStack> list = Lists.<ItemStack>newArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemStack1 = inv.getStackInSlot(i);

            if (!itemStack1.isEmpty())
            {
                if (itemStack1.getItem() instanceof ItemArmorTFCF)
                {
                    ItemArmorTFCF itemArmor = (ItemArmorTFCF)itemStack1.getItem();
                    itemStack = itemStack1;
                }
                else
                {
                    if (!DyeUtils.isDye(itemStack1))
                    {
                        return false;
                    }
                    list.add(itemStack1);
                }
            }
        }
        return !itemStack.isEmpty() && !list.isEmpty();
    }

    /* 
     * Returns an Item that is the result of this recipe
     */
    @Override
    @Nonnull
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
    	ItemStack itemStack = ItemStack.EMPTY;
        int[] aint = new int[3];
        int i = 0;
        int j = 0;
        ItemArmorTFCF itemArmor = null;

        for (int k = 0; k < inv.getSizeInventory(); ++k)
        {
            ItemStack itemStack1 = inv.getStackInSlot(k);

            if (!itemStack1.isEmpty())
            {
                if (itemStack1.getItem() instanceof ItemArmorTFCF)
                {
                    itemArmor = (ItemArmorTFCF)itemStack1.getItem();
                    itemStack = itemStack1.copy();
                    itemStack.setCount(1);
                    if (itemArmor.hasColor(itemStack1))
                    {
                        int l = itemArmor.getColor(itemStack);
                        float f = (float)(l >> 16 & 255) / 255.0F;
                        float f1 = (float)(l >> 8 & 255) / 255.0F;
                        float f2 = (float)(l & 255) / 255.0F;
                        i = (int)((float)i + Math.max(f, Math.max(f1, f2)) * 255.0F);
                        aint[0] = (int)((float)aint[0] + f * 255.0F);
                        aint[1] = (int)((float)aint[1] + f1 * 255.0F);
                        aint[2] = (int)((float)aint[2] + f2 * 255.0F);
                        ++j;
                    }
                }
                else
                { 
                    if (!DyeUtils.isDye(itemStack1))
                    {
                        return ItemStack.EMPTY;
                    }
                    float[] afloat = DyeUtils.colorFromStack(itemStack1).get().getColorComponentValues();
                    int l1 = (int)(afloat[0] * 255.0F);
                    int i2 = (int)(afloat[1] * 255.0F);
                    int j2 = (int)(afloat[2] * 255.0F);
                    i += Math.max(l1, Math.max(i2, j2));
                    aint[0] += l1;
                    aint[1] += i2;
                    aint[2] += j2;
                    ++j;
                }
            }
        }
        if (itemArmor == null)
        {
            return ItemStack.EMPTY;
        }
        else
        {
            int i1 = aint[0] / j;
            int j1 = aint[1] / j;
            int k1 = aint[2] / j;
            float f3 = (float)i / (float)j;
            float f4 = (float)Math.max(i1, Math.max(j1, k1));
            i1 = (int)((float)i1 * f3 / f4);
            j1 = (int)((float)j1 * f3 / f4);
            k1 = (int)((float)k1 * f3 / f4);
            int lvt_12_3_ = (i1 << 8) + j1;
            lvt_12_3_ = (lvt_12_3_ << 8) + k1;
            itemArmor.setColor(itemStack, lvt_12_3_);
            return itemStack;
        }
    }

    /* 
     *  Returns the size of the recipe area 
     */
    public int getRecipeSize()
    {
        return 10;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput()
    {
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            ItemStack itemStack = inv.getStackInSlot(i);
            nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemStack));
        }
        return nonnulllist;
    }

    @Override
    @Nonnull
    public boolean isDynamic()
    {
        return true;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    @Override
    @Nonnull
    public boolean canFit(int width, int height)
    {
        return width * height >= 2;
    }
}
