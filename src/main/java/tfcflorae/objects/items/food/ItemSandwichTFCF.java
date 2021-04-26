package tfcflorae.objects.items.food;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.objects.items.food.ItemSandwich;

import tfcflorae.objects.items.food.ItemFoodTFCF;
import tfcflorae.util.OreDictionaryHelper;

public class ItemSandwichTFCF extends ItemFoodTFCF
{
    private final FoodData data;

    public ItemSandwichTFCF(FoodData data, Object... oreNameParts)
    {
        super(data);
        this.data = data;

        for (Object obj : oreNameParts)
        {
            if (obj instanceof Object[])
                OreDictionaryHelper.register(this, (Object[]) obj);
            else
                OreDictionaryHelper.register(this, obj);
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new ItemSandwich.SandwichHandler(nbt, data);
    }
}