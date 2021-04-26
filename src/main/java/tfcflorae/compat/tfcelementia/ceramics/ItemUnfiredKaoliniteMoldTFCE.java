package tfcflorae.compat.tfcelementia.ceramics;

import java.util.EnumMap;

import net.dries007.tfc.objects.items.ceramics.ItemPottery;

import tfcelementia.objects.items.metal.ItemMetalTFCE;

public class ItemUnfiredKaoliniteMoldTFCE extends ItemPottery
{
    private static final EnumMap<ItemMetalTFCE.ItemType, ItemUnfiredKaoliniteMoldTFCE> MAP = new EnumMap<>(ItemMetalTFCE.ItemType.class);

    public static ItemUnfiredKaoliniteMoldTFCE get(ItemMetalTFCE.ItemType category)
    {
        return MAP.get(category);
    }

    public final ItemMetalTFCE.ItemType type;

    public ItemUnfiredKaoliniteMoldTFCE(ItemMetalTFCE.ItemType type)
    {
        this.type = type;
        if (MAP.put(type, this) != null)
        {
            throw new IllegalStateException("There can only be one.");
        }
    }
}