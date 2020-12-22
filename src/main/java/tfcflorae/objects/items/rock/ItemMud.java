package tfcflorae.objects.items.rock;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.objects.items.ItemTFC;

import tfcflorae.util.OreDictionaryHelper;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemMud extends ItemTFC implements IRockObject
{
    private static final Map<Rock, ItemMud> MAP = new HashMap<>();

    public static ItemMud get(Rock rock)
    {
        return MAP.get(rock);
    }

    public static ItemStack get(Rock rock, int amount)
    {
        return new ItemStack(MAP.get(rock), amount);
    }

    private final Rock rock;

    public ItemMud(Rock rock)
    {
        this.rock = rock;
        if (MAP.put(rock, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
        OreDictionaryHelper.register(this, "mud");
        OreDictionaryHelper.register(this, "mud", rock);
        OreDictionaryHelper.register(this, "mud", rock.getRockCategory());

        if (rock.isFluxStone())
        {
            OreDictionaryHelper.register(this, "mud", "flux");
        }
    }

    @Override
    @Nonnull
    public Rock getRock(ItemStack stack)
    {
        return rock;
    }

    @Override
    @Nonnull
    public RockCategory getRockCategory(ItemStack stack)
    {
        return rock.getRockCategory();
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
        return Weight.VERY_LIGHT; // Stacksize = 64
    }
}