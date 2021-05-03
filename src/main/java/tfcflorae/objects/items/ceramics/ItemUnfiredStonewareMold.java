package tfcflorae.objects.items.ceramics;

import java.util.EnumMap;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.ceramics.ItemPottery;

public class ItemUnfiredStonewareMold extends ItemPottery
{
    private static final EnumMap<Metal.ItemType, ItemUnfiredStonewareMold> MAP = new EnumMap<>(Metal.ItemType.class);

    public static ItemUnfiredStonewareMold get(Metal.ItemType category)
    {
        return MAP.get(category);
    }

    public final Metal.ItemType type;

    public ItemUnfiredStonewareMold(Metal.ItemType type)
    {
        this.type = type;
        if (MAP.put(type, this) != null)
        {
            throw new IllegalStateException("There can only be one.");
        }
    }
}