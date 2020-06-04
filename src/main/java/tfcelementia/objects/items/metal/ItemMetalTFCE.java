package tfcelementia.objects.items.metal;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.forge.ForgeableHeatableHandler;
import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;

/**
 * Since TFC has Metal.ItemType we can't reuse {@link net.dries007.tfc.objects.items.metal.ItemMetal} directly
 */
public class ItemMetalTFCE extends ItemTFC implements IMetalItem
{
    private static final Map<Metal, EnumMap<ItemType, ItemMetalTFCE>> TABLE = new HashMap<>();

    @Nullable
    public static ItemMetalTFCE get(Metal metal, ItemMetalTFCE.ItemType type)
    {
        return (ItemMetalTFCE) ((EnumMap) TABLE.get(metal)).get(type);
    }

    protected final Metal metal;
    protected final ItemType type;

    public ItemMetalTFCE(Metal metal, ItemType type)
    {
        this.metal = metal;
        this.type = type;
        if (!TABLE.containsKey(metal))
        {
            TABLE.put(metal, new EnumMap<>(ItemType.class));
        }
        TABLE.get(metal).put(type, this);
        setNoRepair();
    }

    public ItemType getType()
    {
        return type;
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack itemStack)
    {
        switch (type)
        {
        	case NAIL:
    		case RING:
    		case RING_MESH:
    		case CHAIN:
        		return Size.SMALL;
            case PLATE:
            case PLATE_DOUBLE:
            case PLATE_TRIPLE:
            case PLATE_QUADRUPLE:
            case INGOT_TRIPLE:
            case SHEET_TRIPLE:
            case INGOT_QUADRUPLE:
            case SHEET_QUADRUPLE:
                return Size.NORMAL;
            default:
                return Size.LARGE;
        }
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack itemStack)
    {
        switch (type)
        {
    		case NAIL:
    		case RING:
    		case RING_MESH:
    		case CHAIN:
    			return Weight.VERY_LIGHT;
            case PLATE:
            case PLATE_DOUBLE:
            case PLATE_TRIPLE:
            case PLATE_QUADRUPLE:
            case INGOT_TRIPLE:
            case INGOT_QUADRUPLE:
                return Weight.LIGHT;
            case SHEET_TRIPLE:
                return Weight.MEDIUM;
            case SHEET_QUADRUPLE:
                return Weight.HEAVY;
            default:
                return Weight.MEDIUM;
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new ForgeableHeatableHandler(nbt, metal.getSpecificHeat(), metal.getMeltTemp());
    }

    @Nonnull
    @Override
    public Metal getMetal(ItemStack itemStack)
    {
        return metal;
    }

    @Override
    public int getSmeltAmount(ItemStack itemStack)
    {
        return type.getSmeltAmount();
    }

    public enum ItemType
    {
        NAIL(10, true),
        RING(10, true),
        CHAIN(40),
        RING_MESH(80),
        PLATE(100),
        PLATE_DOUBLE(200),
        PLATE_TRIPLE(300),
        PLATE_QUADRUPLE(400),
        INGOT_TRIPLE(300),
        SHEET_TRIPLE(600),
        INGOT_QUADRUPLE(400),
        SHEET_QUADRUPLE(800);

        public static Item create(Metal metal, ItemType type)
        {
            return type.supplier.apply(metal, type);
        }

        private final int smeltAmount;
        private final boolean hasMold;
        private final BiFunction<Metal, ItemType, Item> supplier;

        ItemType(int smeltAmount)
        {
            this(smeltAmount, false);
        }

        ItemType(int smeltAmount, boolean hasMold)
        {
            this(smeltAmount, hasMold, ItemMetalTFCE::new);
        }

        ItemType(int smeltAmount, boolean hasMold, @Nonnull BiFunction<Metal, ItemType, Item> supplier)
        {
            this.smeltAmount = smeltAmount;
            this.hasMold = hasMold;
            this.supplier = supplier;
        }

        public int getSmeltAmount()
        {
            return smeltAmount;
        }

        public boolean hasMold()
        {
            return hasMold;
        }
    }
}