package tfcflorae.objects.items.rock;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.objects.items.ItemTFC;

import tfcflorae.util.OreDictionaryHelper;

@ParametersAreNonnullByDefault
public class ItemMudBrick extends ItemTFC implements IRockObject
{
    private static final Map<Rock, ItemMudBrick> MAP = new HashMap<>();

    public static ItemMudBrick get(Rock ore)
    {
        return MAP.get(ore);
    }

    public static ItemStack get(Rock ore, int amount)
    {
        return new ItemStack(MAP.get(ore), amount);
    }

    private final Rock rock;

    public ItemMudBrick(Rock rock)
    {
        this.rock = rock;
        if (MAP.put(rock, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
        OreDictionaryHelper.register(this, "mud", "brick");
        OreDictionaryHelper.register(this, "mud", "brick", rock.getRockCategory());
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack)
    {
        return Size.SMALL; // Stored everywhere
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack)
    {
        return Weight.LIGHT; // Stacksize = 32
    }

    @Nonnull
    @Override
    public Rock getRock(ItemStack stack)
    {
        return rock;
    }

    @Nonnull
    @Override
    public RockCategory getRockCategory(ItemStack stack)
    {
        return rock.getRockCategory();
    }
}
