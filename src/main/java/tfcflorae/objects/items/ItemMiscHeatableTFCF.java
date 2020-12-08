package tfcflorae.objects.items;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.heat.ItemHeatHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

@SuppressWarnings("WeakerAccess")
public class ItemMiscHeatableTFCF extends ItemMiscTFCF
{

    private float heatCapacity, meltTemp;

    public ItemMiscHeatableTFCF(Size size, Weight weight, float heatCapacity, float meltTemp)
    {
        super(size, weight);
        this.heatCapacity = heatCapacity;
        this.meltTemp = meltTemp;
    }

    public ItemMiscHeatableTFCF(Size size, Weight weight, float heatCapacity, float meltTemp, String oreDictionary)
    {
        super(size, weight, oreDictionary);
        this.heatCapacity = heatCapacity;
        this.meltTemp = meltTemp;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new ItemHeatHandler(nbt, heatCapacity, meltTemp);
    }
}
