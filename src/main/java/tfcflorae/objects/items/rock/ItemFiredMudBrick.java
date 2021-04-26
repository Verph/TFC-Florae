package tfcflorae.objects.items.rock;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.heat.ItemHeatHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;

import tfcflorae.objects.items.ItemTFCF;
import tfcflorae.util.OreDictionaryHelper;

@ParametersAreNonnullByDefault
public class ItemFiredMudBrick extends ItemTFCF implements IRockObject
{
    private static final Map<ItemUnfiredMudBrick, ItemFiredMudBrick> MAP = new HashMap<>();

    public static ItemFiredMudBrick get(Rock rock)
    {
        return MAP.get(ItemUnfiredMudBrick.get(rock));
    }

    public static ItemFiredMudBrick get(ItemMud mud)
    {
        return MAP.get(ItemUnfiredMudBrick.get(mud));
    }

    public static ItemFiredMudBrick get(ItemUnfiredMudBrick rock)
    {
        return MAP.get(rock);
    }

    public static ItemStack get(ItemUnfiredMudBrick rock, int amount)
    {
        return new ItemStack(MAP.get(rock), amount);
    }

    private final ItemUnfiredMudBrick rock;

    public ItemFiredMudBrick(ItemUnfiredMudBrick rock)
    {
        this.rock = rock;
        if (MAP.put(rock, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
        OreDictionaryHelper.register(this, "mud", "brick");
        OreDictionaryHelper.register(this, "mud", "brick", rock.getRock());
        OreDictionaryHelper.register(this, "mud", "brick", rock.getRock().getRockCategory());
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
        return rock.getRock();
    }

    @Nonnull
    @Override
    public RockCategory getRockCategory(ItemStack stack)
    {
        return rock.getRock().getRockCategory();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        // Heat capability, as pottery needs to be able to be fired, or survive despite not having a heat capability
        return new ItemHeatHandler(nbt, 1.0f, 1599f);
    }
}
