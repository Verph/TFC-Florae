package tfcflorae.api.types;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.forge.ForgeableHeatableHandler;
import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Metal.Tier;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.types.DefaultMetals;
import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.metal.BlockMetalAlembic;

public class ItemMetalTFCF extends ItemTFC implements IMetalItem
{
    private static final Map<Metal, EnumMap<ItemType, ItemMetalTFCF>> TABLE = new HashMap<>();

    @Nullable
    public static Item get(Metal metal, ItemMetalTFCF.ItemType type)
    {
        return TABLE.get(metal).get(type);
    }

    protected final Metal metal;
    protected final ItemType type;

    public ItemMetalTFCF(Metal metal, ItemType type)
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
            case COPPER_ALEMBIC_CONDENSER:
            case COPPER_ALEMBIC:
                return Size.LARGE; // Tool heads fits in large vessels
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
            case COPPER_ALEMBIC_CONDENSER:
                return Weight.HEAVY;
            case COPPER_ALEMBIC:
                return Weight.VERY_HEAVY;
            default:
                return Weight.MEDIUM;
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt)
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
        COPPER_ALEMBIC(false, 800, false, (metal, itemType) -> new ItemBlock(BlockMetalAlembic.get(metal))), // Quadruple sheet at anvil (800 units)
        COPPER_ALEMBIC_CONDENSER(false, 600, false, (metal, itemType) -> new ItemBlock(BlockMetalAlembic.get(metal))); // Triple sheet at anvil (600 units)

        public static Item create(Metal metal, ItemType type)
        {
            return type.supplier.apply(metal, type);
        }

        private final boolean toolItem;
        private final int armorSlot; //Which armor slot this armor should go, from 0 = Helmet to 4 = Boots
        private final int smeltAmount;
        private final boolean hasMold;
        private final BiFunction<Metal, ItemType, Item> supplier;
        private final String[] pattern;
        private int isActive = 0; //Used as as a 3 value bool, where: 0=not specified, 1=active, 2=deactive

        ItemType(boolean toolItem, int armorSlot, int smeltAmount, @Nonnull BiFunction<Metal, ItemType, Item> supplier, boolean hasMold, String... moldPattern)
        {
            this.toolItem = toolItem;
            this.armorSlot = armorSlot;
            this.smeltAmount = smeltAmount;
            this.supplier = supplier;
            this.hasMold = hasMold;
            this.pattern = moldPattern;
        }

        ItemType(boolean toolItem, int smeltAmount, @Nonnull BiFunction<Metal, ItemType, Item> supplier, boolean hasMold, String... moldPattern)
        {
            this(toolItem, -1, smeltAmount, ItemMetalTFCF::new, hasMold, moldPattern);
        }
        
        ItemType(boolean toolItem, int smeltAmount, boolean hasMold, String... moldPattern)
        {
            this(toolItem, -1, smeltAmount, ItemMetalTFCF::new, hasMold, moldPattern);
        }

        ItemType(boolean toolItem, int smeltAmount, boolean hasMold, boolean isActive ,String... moldPattern)
        {
            this(toolItem, -1, smeltAmount, ItemMetalTFCF::new, hasMold, moldPattern);

            if (isActive)
                this.isActive = 1;
            else
                this.isActive = 2;
        }

        ItemType(boolean toolItem, int smeltAmount)
        {
            this(toolItem, smeltAmount, false);
        }

        ItemType(boolean toolItem, int armorSlot, int smeltAmount, @Nonnull BiFunction<Metal, ItemType, Item> supplier)
        {
            this(toolItem, armorSlot, smeltAmount, supplier, false);
        }

        ItemType(boolean toolItem, int smeltAmount, @Nonnull BiFunction<Metal, ItemType, Item> supplier)
        {
            this(toolItem, -1, smeltAmount, supplier, false);
        }

        ItemType(boolean toolItem, int smeltAmount, boolean isActive, @Nonnull BiFunction<Metal, ItemType, Item> supplier)
        {
            this(toolItem, -1, smeltAmount, supplier, false);

            if (isActive)
                this.isActive = 1;
            else
                this.isActive = 2;
        }

        public boolean hasType(Metal metal)
        {
            if (this == ItemType.COPPER_ALEMBIC)
            {
                return metal == TFCRegistries.METALS.getValue(DefaultMetals.COPPER);
            }
            return !this.isToolItem() || metal.getToolMetal() != null;
        }

        /**
         * Used to find out if the type has a mold
         *
         * @param metal Null, if checking across all types. If present, checks if the metal is compatible with the mold type
         * @return if the type + metal combo have a valid mold
         */
        public boolean hasMold(@Nullable Metal metal)
        {
            if (metal == null)
            {
                // Query for should the mold exist during registration
                return hasMold;
            }
            if (hasMold)
            {
                // All tool metals can be used in tool molds with tier at most II
                return metal.isToolMetal() && metal.getTier().isAtMost(Tier.TIER_II);
            }
            return false;
        }

        public boolean isToolItem()
        {
            return toolItem;
        }

        public int getSmeltAmount()
        {
            return smeltAmount;
        }

        public String[] getPattern()
        {
            return pattern;
        }

        public void setTypeActive(boolean Active)
        {
            if (isActive == 0)
                if(Active)
                    isActive = 1;
                else
                    isActive = 2;
            else
                TFCFlorae.getLog().warn("TFCFlorae: ItemType is already set, this is probably an error.");
        }

        public boolean isTypeActive()
        {
            if (isActive == 2)
                return false;
            if (isActive == 0)
                isActive = 1;
            return true;
        }
    }
}
