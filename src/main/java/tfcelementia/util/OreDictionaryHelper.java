package tfcelementia.util;

import javax.annotation.Nonnull;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.damage.DamageType;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.items.ItemGem;
//import net.dries007.tfc.api.types.Rock;
import tfcelementia.objects.PowderGemTFCE;
//import tfcelementia.api.types.RockTFCE;
import tfcelementia.objects.PowderTFCE;
import tfcelementia.objects.items.ItemPowderGemTFCE;
import tfcelementia.objects.items.ItemPowderTFCE;
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

        // Vanilla ore dict values
        /*
        OreDictionary.registerOre("clay", Items.CLAY_BALL);
        OreDictionary.registerOre("gemCoal", new ItemStack(Items.COAL, 1, 0));
        OreDictionary.registerOre("charcoal", new ItemStack(Items.COAL, 1, 1));
        OreDictionary.registerOre("fireStarter", new ItemStack(Items.FLINT_AND_STEEL, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("fireStarter", new ItemStack(Items.FIRE_CHARGE));
        OreDictionary.registerOre("bowl", Items.BOWL);
        */
        
        //oredict support for TFC dyes
        OreDictionary.registerOre("dyeWhite", new ItemStack(ItemPowderTFCE.get(PowderTFCE.CALCIUM)));
        OreDictionary.registerOre("dyeGreen", new ItemStack(ItemPowderTFCE.get(PowderTFCE.FLUORITE)));
        OreDictionary.registerOre("dyePurple", new ItemStack(ItemPowderTFCE.get(PowderTFCE.IODATE)));
        OreDictionary.registerOre("dyeYellow", new ItemStack(ItemPowderTFCE.get(PowderTFCE.PHOSPHORITE)));
        OreDictionary.registerOre("dyeRed", new ItemStack(ItemPowderTFCE.get(PowderTFCE.SELENIDE)));
        
        /*
        //oredict support for TFC gem dusts
        OreDictionary.registerOre("dustAgate", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.AGATE)));
        OreDictionary.registerOre("dustAmethyst", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.AMETHYST)));
        OreDictionary.registerOre("dustBeryl", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.BERYL)));
        OreDictionary.registerOre("dustDiamond", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.DIAMOND)));
        OreDictionary.registerOre("dustEmerald", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.EMERALD)));
        OreDictionary.registerOre("dustGarnet", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.GARNET)));
        OreDictionary.registerOre("dustJade", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.JADE)));
        OreDictionary.registerOre("dustJasper", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.JASPER)));
        OreDictionary.registerOre("dustOpal", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.OPAL)));
        OreDictionary.registerOre("dustRuby", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.RUBY)));
        OreDictionary.registerOre("dustSapphire", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SAPPHIRE)));
        OreDictionary.registerOre("dustTopaz", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TOPAZ)));
        OreDictionary.registerOre("dustTourmaline", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TOURMALINE)));
        OreDictionary.registerOre("dustApatite", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.APATITE)));
        OreDictionary.registerOre("dustBromargyrite", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.BROMARGYRITE)));
        OreDictionary.registerOre("dustCitrine", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.CITRINE)));
        OreDictionary.registerOre("dustHeliodor", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.HELIODOR)));
        OreDictionary.registerOre("dustIodargyrite", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.IODARGYRITE)));
        OreDictionary.registerOre("dustKyanite", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.KYANITE)));
        OreDictionary.registerOre("dustMoldavite", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.MOLDAVITE)));
        OreDictionary.registerOre("dustMoonstone", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.MOONSTONE)));
        OreDictionary.registerOre("dustPyromorphite", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.PYROMORPHITE)));
        OreDictionary.registerOre("dustQuartz", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.QUARTZ)));
        OreDictionary.registerOre("dustSpinel", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SPINEL)));
        OreDictionary.registerOre("dustSunstone", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SUNSTONE)));
        OreDictionary.registerOre("dustTanzanite", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TANZANITE)));
        OreDictionary.registerOre("dustZircon", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.ZIRCON)));
        
        OreDictionary.registerOre("powderAgate", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.AGATE)));
        OreDictionary.registerOre("powderAmethyst", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.AMETHYST)));
        OreDictionary.registerOre("powderBeryl", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.BERYL)));
        OreDictionary.registerOre("powderDiamond", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.DIAMOND)));
        OreDictionary.registerOre("powderEmerald", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.EMERALD)));
        OreDictionary.registerOre("powderGarnet", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.GARNET)));
        OreDictionary.registerOre("powderJade", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.JADE)));
        OreDictionary.registerOre("powderJasper", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.JASPER)));
        OreDictionary.registerOre("powderOpal", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.OPAL)));
        OreDictionary.registerOre("powderRuby", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.RUBY)));
        OreDictionary.registerOre("powderSapphire", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SAPPHIRE)));
        OreDictionary.registerOre("powderTopaz", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TOPAZ)));
        OreDictionary.registerOre("powderTourmaline", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TOURMALINE)));
        OreDictionary.registerOre("powderApatite", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.APATITE)));
        OreDictionary.registerOre("powderBromargyrite", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.BROMARGYRITE)));
        OreDictionary.registerOre("powderCitrine", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.CITRINE)));
        OreDictionary.registerOre("powderHeliodor", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.HELIODOR)));
        OreDictionary.registerOre("powderIodargyrite", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.IODARGYRITE)));
        OreDictionary.registerOre("powderKyanite", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.KYANITE)));
        OreDictionary.registerOre("powderMoldavite", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.MOLDAVITE)));
        OreDictionary.registerOre("powderMoonstone", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.MOONSTONE)));
        OreDictionary.registerOre("powderPyromorphite", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.PYROMORPHITE)));
        OreDictionary.registerOre("powderQuartz", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.QUARTZ)));
        OreDictionary.registerOre("powderSpinel", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SPINEL)));
        OreDictionary.registerOre("powderSunstone", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.SUNSTONE)));
        OreDictionary.registerOre("powderTanzanite", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.TANZANITE)));
        OreDictionary.registerOre("powderZircon", new ItemStack(ItemPowderGemTFCE.get(PowderGemTFCE.ZIRCON)));
        */
        
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
            TerraFirmaCraft.getLog().warn("doesStackMatchOre called with non-existing name. stack: {} name: {}", stack, name);
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

    /*
    private static void registerRockType(Thing thing, RockTFCE.Type type, Object... prefixParts)
    {
        switch (type)
        {
            case MOSSY_COBBLE:
                MAP.put(thing, toString(prefixParts, "cobblestone"));
                break;
            case MOSSY_BRICKS:
            case CRACKED_BRICKS:
                MAP.put(thing, toString(prefixParts, "stone", "brick"));
                break;
            case PODZOL:
                break;
            default:
                MAP.put(thing, toString(prefixParts, type));
        }
    }
    */

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
