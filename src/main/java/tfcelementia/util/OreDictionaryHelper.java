package tfcelementia.util;

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
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.items.ItemGem;
//import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.items.metal.ItemOreTFC;
import tfcelementia.TFCElementia;
import tfcelementia.objects.PowderGemTFCE;
//import tfcelementia.api.types.RockTFCE;
import tfcelementia.objects.PowderTFCE;
import tfcelementia.objects.items.ItemPowderGemTFCE;
import tfcelementia.objects.items.ItemPowderTFCE;
import tfcelementia.objects.items.ItemsTFCE;
import tfcelementia.types.MetalsTFCE;

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

    /*
    public static void registerRockType(Block thing, RockTFCE.Type type, Object... prefixParts)
    {
        registerRockType(new Thing(thing), type, prefixParts);
    }
    */

    public static void registerDamageType(Item thing, DamageType type)
    {
        register(thing, "damage", "type", type.name().toLowerCase());
    }

    public static void init()
    {
        done = true;
        MAP.forEach((t, s) -> OreDictionary.registerOre(s, t.toItemStack()));
        MAP.clear(); // No need to keep this stuff around
        
        // Oredict support for TFC dyes
        OreDictionary.registerOre("dyeWhite", new ItemStack(ItemPowderTFCE.get(PowderTFCE.CALCIUM)));
        OreDictionary.registerOre("dyeGreen", new ItemStack(ItemPowderTFCE.get(PowderTFCE.FLUORITE)));
        OreDictionary.registerOre("dyePurple", new ItemStack(ItemPowderTFCE.get(PowderTFCE.IODATE)));
        OreDictionary.registerOre("dyeYellow", new ItemStack(ItemPowderTFCE.get(PowderTFCE.PHOSPHORITE)));
        OreDictionary.registerOre("dyeRed", new ItemStack(ItemPowderTFCE.get(PowderTFCE.SELENIDE)));
        
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
            TFCElementia.getLog().warn("doesStackMatchOre called with non-existing name. stack: {} name: {}", stack, name);
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
