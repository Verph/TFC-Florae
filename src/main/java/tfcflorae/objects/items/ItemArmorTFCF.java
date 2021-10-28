package tfcflorae.objects.items;

import javax.annotation.Nonnull;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.dries007.tfc.api.capability.damage.IDamageResistance;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.IArmorMaterialTFC;

import tfcflorae.objects.ArmorMaterialsTFCF;

public class ItemArmorTFCF extends ItemArmor implements IItemSize, IDamageResistance
{
	public static final int DEFAULT_COLOR = 14277081; // Light Gray
	public static final int BURLAP_COLOR = 12497798;
	public static final int COTTON_COLOR = 13027525;
	public static final int HEMP_COLOR = 8164702;
	public static final int LINEN_COLOR = 10200219;
	public static final int PINEAPPLE_LEATHER_COLOR = 13818026;
	public static final int SILK_COLOR = 15658734;
	public static final int SISAL_COLOR = 13942943;
	public static final int WOOL_COLOR = 14079702;
	public static final int YUCCA_COLOR = 10585698;

    public final IArmorMaterialTFC materialTFC;

    public ItemArmorTFCF(IArmorMaterialTFC materialTFC, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
    {
        super(materialTFC.getMaterial(), renderIndexIn, equipmentSlotIn);
        this.materialTFC = materialTFC;
        setNoRepair();
    }

    @Override
    public float getCrushingModifier()
    {
        return materialTFC.getCrushingModifier();
    }

    @Override
    public float getPiercingModifier()
    {
        return materialTFC.getPiercingModifier();
    }

    @Override
    public float getSlashingModifier()
    {
        return materialTFC.getSlashingModifier();
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack)
    {
        return Size.LARGE; // Stored in chests
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack)
    {
        return Weight.HEAVY; // Stacksize is already restricted to 1
    }

    @Override
    public boolean canStack(@Nonnull ItemStack stack)
    {
        return false;
    }

    /**
     * Determines if this armor will be rendered with the secondary 'overlay' texture.
     * If this is true, the first texture will be rendered using a tint of the color
     * specified by getColor(ItemStack)
     *
     * @param stack The stack
     * @return true/false
     */
    @Override
    public boolean hasOverlay(ItemStack stack)
    {
        return true;
    }

    /**
     * Return whether the specified armor ItemStack has a color.
     */
    @Override
    public boolean hasColor(ItemStack stack)
    {
        if (this.materialTFC != ArmorMaterialsTFCF.PINEAPPLE_LEATHER || 
            this.materialTFC != ArmorMaterialsTFCF.BURLAP_CLOTH || 
            this.materialTFC != ArmorMaterialsTFCF.WOOL_CLOTH || 
            this.materialTFC != ArmorMaterialsTFCF.SILK_CLOTH || 
            this.materialTFC != ArmorMaterialsTFCF.SISAL_CLOTH || 
            this.materialTFC != ArmorMaterialsTFCF.COTTON_CLOTH || 
            this.materialTFC != ArmorMaterialsTFCF.LINEN_CLOTH || 
            this.materialTFC != ArmorMaterialsTFCF.HEMP_CLOTH || 
            this.materialTFC != ArmorMaterialsTFCF.YUCCA_CANVAS)
        {
            return false;
        }
        else
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            return nbttagcompound != null && nbttagcompound.hasKey("display", 10) ? nbttagcompound.getCompoundTag("display").hasKey("color", 3) : false;
        }
    	/*return !stack.hasTagCompound() ? 
            false : (!stack.getTagCompound().hasKey("display", 10) ? 
                false : stack.getTagCompound().getCompoundTag("display").hasKey("color", 3));*/
    }

    /**
     * Return the color for the specified armor ItemStack.
     */
    @Override
    public int getColor(ItemStack stack)
    {
        if (this.materialTFC != ArmorMaterialsTFCF.PINEAPPLE_LEATHER || 
            this.materialTFC != ArmorMaterialsTFCF.BURLAP_CLOTH || 
            this.materialTFC != ArmorMaterialsTFCF.WOOL_CLOTH || 
            this.materialTFC != ArmorMaterialsTFCF.SILK_CLOTH || 
            this.materialTFC != ArmorMaterialsTFCF.SISAL_CLOTH || 
            this.materialTFC != ArmorMaterialsTFCF.COTTON_CLOTH || 
            this.materialTFC != ArmorMaterialsTFCF.LINEN_CLOTH || 
            this.materialTFC != ArmorMaterialsTFCF.HEMP_CLOTH || 
            this.materialTFC != ArmorMaterialsTFCF.YUCCA_CANVAS)
        {
            return DEFAULT_COLOR;
        }
        else
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            if (nbttagcompound != null)
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
                if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
                {
                    return nbttagcompound1.getInteger("color");
                }
            }
            if (this.materialTFC == ArmorMaterialsTFCF.BURLAP_CLOTH)
            {
                return BURLAP_COLOR;
            }
            if (this.materialTFC == ArmorMaterialsTFCF.COTTON_CLOTH)
            {
                return COTTON_COLOR;
            }
            if (this.materialTFC == ArmorMaterialsTFCF.HEMP_CLOTH)
            {
                return HEMP_COLOR;
            }
            if (this.materialTFC == ArmorMaterialsTFCF.LINEN_CLOTH)
            {
                return LINEN_COLOR;
            }
            if (this.materialTFC == ArmorMaterialsTFCF.PINEAPPLE_LEATHER)
            {
                return PINEAPPLE_LEATHER_COLOR;
            }
            if (this.materialTFC == ArmorMaterialsTFCF.SILK_CLOTH)
            {
                return SILK_COLOR;
            }
            if (this.materialTFC == ArmorMaterialsTFCF.SISAL_CLOTH)
            {
                return SISAL_COLOR;
            }
            if (this.materialTFC == ArmorMaterialsTFCF.WOOL_CLOTH)
            {
                return WOOL_COLOR;
            }
            if (this.materialTFC == ArmorMaterialsTFCF.YUCCA_CANVAS)
            {
                return YUCCA_COLOR;
            }
        }
        return DEFAULT_COLOR;
    }

    /**
     * Remove the color from the specified armor ItemStack.
     */
    @Override
    public void removeColor(ItemStack stack)
    {
        if (this.materialTFC == ArmorMaterialsTFCF.PINEAPPLE_LEATHER || 
            this.materialTFC == ArmorMaterialsTFCF.BURLAP_CLOTH || 
            this.materialTFC == ArmorMaterialsTFCF.WOOL_CLOTH || 
            this.materialTFC == ArmorMaterialsTFCF.SILK_CLOTH || 
            this.materialTFC == ArmorMaterialsTFCF.SISAL_CLOTH || 
            this.materialTFC == ArmorMaterialsTFCF.COTTON_CLOTH || 
            this.materialTFC == ArmorMaterialsTFCF.LINEN_CLOTH || 
            this.materialTFC == ArmorMaterialsTFCF.HEMP_CLOTH || 
            this.materialTFC == ArmorMaterialsTFCF.YUCCA_CANVAS)
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            if (nbttagcompound != null)
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
                if (nbttagcompound1.hasKey("color"))
                {
                    nbttagcompound1.removeTag("color");
                }
            }
        }
    }

    @Override
    public void setColor(ItemStack stack, int color)
    {
    	NBTTagCompound nbttagcompound = stack.getTagCompound();
    	if (nbttagcompound == null)
        {
    		nbttagcompound = new NBTTagCompound();
    		stack.setTagCompound(nbttagcompound);
    	}
    	NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
    	if (!nbttagcompound.hasKey("display", 10))
        {
    		nbttagcompound.setTag("display", nbttagcompound1);
    	}
    	nbttagcompound1.setInteger("color", color);
    }	
}
