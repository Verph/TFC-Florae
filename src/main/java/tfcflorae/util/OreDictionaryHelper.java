package tfcflorae.util;

import javax.annotation.Nonnull;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

//import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.damage.DamageType;
import tfcflorae.TFCFlorae;
/*
import tfcflorae.objects.blocks.blockRock.BlockRockVariantTFCF;
import tfcflorae.types.BlocksRockTFCF;
import tfcflorae.types.BlocksRockTFCF.TypeTFCF;
*/
import tfcflorae.types.BlockTypesTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

public class OreDictionaryHelper 
{
    private static final Multimap<Thing, String> MAP = HashMultimap.create();
    private static final Converter<String, String> UPPER_UNDERSCORE_TO_LOWER_CAMEL = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);
    private static final Joiner JOINER_UNDERSCORE = Joiner.on('_').skipNulls();
    private static boolean done = false;
    
    public static String toString(Object... parts)
    {
        return UPPER_UNDERSCORE_TO_LOWER_CAMEL.convert(JOINER_UNDERSCORE.join(parts));
    }

    public static String toString(Iterable<Object> parts)
    {
        return UPPER_UNDERSCORE_TO_LOWER_CAMEL.convert(JOINER_UNDERSCORE.join(parts));
    }

    public static String toString(Object[] prefix, Object... parts)
    {
        return toString(ImmutableList.builder().add(prefix).add(parts).build());
    }

    public static void register(Block thing, Object... parts)
    {
        register(new Thing(thing), parts);
    }

    public static void register(Item thing, Object... parts)
    {
        register(new Thing(thing), parts);
    }

    public static void registerMeta(Item thing, int meta, Object... parts)
    {
        register(new Thing(thing, meta), parts);
    }

    public static void registerRockType(Block thing, RockTFCF rockTFCF, Object... prefixParts)
    {
        registerRockType(new Thing(thing), rockTFCF, prefixParts);
    }

    public static void registerDamageType(Item thing, DamageType type)
    {
        register(thing, "damage", "type", type.name().toLowerCase());
    }

    public static void init()
    {
        done = true;
        MAP.forEach((t, s) -> OreDictionary.registerOre(s, t.toItemStack()));
        MAP.clear(); // No need to keep this stuff around
        
        // Register a name without any items
        OreDictionary.getOres("infiniteFire", true);
    }

    /**
     * Checks if an ItemStack has an OreDictionary entry that matches 'name'.
     */
    public static boolean doesStackMatchOre(@Nonnull ItemStack stack, String name)
    {
        if (!OreDictionary.doesOreNameExist(name))
        {
            TFCFlorae.getLog().warn("doesStackMatchOre called with non-existing name. stack: {} name: {}", stack, name);
            return false;
        }
        if (stack.isEmpty()) return false;
        int needle = OreDictionary.getOreID(name);
        for (int id : OreDictionary.getOreIDs(stack))
        {
            if (id == needle) return true;
        }
        return false;
    }

    private static void register(Thing thing, Object... parts)
    {
        if (done) throw new IllegalStateException("Cannot use the helper to register after postInit has past.");
        MAP.put(thing, toString(parts));
    }

    private static void registerRockType(Thing thing, RockTFCF rockTFCF, Object... prefixParts)
    {
        switch (rockTFCF)
        {

            case MOSSY_RAW:
                MAP.put(thing, toString(prefixParts, "stone"));
                break;
            case COARSE_DIRT:
            case COARSE_LOAMY_SAND:
            case COARSE_SANDY_LOAM:
            case COARSE_SANDY_CLAY_LOAM:
            case COARSE_SANDY_CLAY:
            case COARSE_LOAM:
            case COARSE_CLAY_LOAM:
            case COARSE_CLAY:
            case COARSE_SILTY_CLAY:
            case COARSE_SILTY_CLAY_LOAM:
            case COARSE_SILT_LOAM:
            case COARSE_SILT:
                MAP.put(thing, toString(prefixParts, rockTFCF, "coarse"));
                break;
            case LOAMY_SAND:
            case SANDY_LOAM:
            case SANDY_CLAY_LOAM:
            case SANDY_CLAY:
            case LOAM:
            case CLAY_LOAM:
            case SILTY_CLAY:
            case SILTY_CLAY_LOAM:
            case SILT_LOAM:
            case SILT:
                MAP.put(thing, toString(prefixParts, "block", rockTFCF, "dirt"));
                break;
            case LOAMY_SAND_GRASS:
            case SANDY_LOAM_GRASS:
            case SANDY_CLAY_LOAM_GRASS:
            case SANDY_CLAY_GRASS:
            case LOAM_GRASS:
            case CLAY_LOAM_GRASS:
            case SILTY_CLAY_GRASS:
            case SILTY_CLAY_LOAM_GRASS:
            case SILT_LOAM_GRASS:
            case SILT_GRASS:
                MAP.put(thing, toString(prefixParts, "block", rockTFCF));
                break;
            case PODZOL:
            case LOAMY_SAND_PODZOL:
            case SANDY_LOAM_PODZOL:
            case SANDY_CLAY_LOAM_PODZOL:
            case SANDY_CLAY_PODZOL:
            case LOAM_PODZOL:
            case CLAY_LOAM_PODZOL:
            case CLAY_PODZOL:
            case SILTY_CLAY_PODZOL:
            case SILTY_CLAY_LOAM_PODZOL:
            case SILT_LOAM_PODZOL:
            case SILT_PODZOL:
                MAP.put(thing, toString(prefixParts, rockTFCF, "podzol"));
                break;
            case MUD:
            default:
                MAP.put(thing, toString(prefixParts, rockTFCF));
        }
    }
    private static class Thing
    {
        private final Block block;
        private final Item item;
        private final int meta;

        private Thing(Block thing)
        {
            block = thing;
            item = null;
            meta = 0;
        }

        private Thing(Item thing)
        {
            this(thing, -1);
        }

        private Thing(Item thing, int meta)
        {
            block = null;
            item = thing;
            this.meta = meta;
        }

        private ItemStack toItemStack()
        {
            if (block != null)
            {
                return new ItemStack(block, 1, meta);
            }
            else if (item != null)
            {
                int meta = this.meta;
                if (meta == -1 && item.isDamageable())
                {
                    meta = OreDictionary.WILDCARD_VALUE;
                }
                return new ItemStack(item, 1, meta);
            }
            return ItemStack.EMPTY;
        }
    }
}
