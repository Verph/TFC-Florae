package tfcflorae.objects.items;

import java.util.function.Function;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSnow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.*;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.blocks.BlockSlabTFC;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.wood.BlockDoorTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.items.ItemMisc;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.ceramics.*;
import net.dries007.tfc.objects.items.food.ItemDynamicBowlFood;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.objects.items.food.ItemSandwich;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTorch;
import net.dries007.tfc.objects.items.metal.ItemMetal;
import net.dries007.tfc.objects.items.metal.ItemMetalBucket;
import net.dries007.tfc.objects.items.metal.ItemOreTFC;
import net.dries007.tfc.objects.items.metal.ItemSmallOre;
import net.dries007.tfc.objects.items.rock.ItemBrickTFC;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.dries007.tfc.objects.items.rock.ItemRockToolHead;
import net.dries007.tfc.objects.items.wood.ItemBoatTFC;
import net.dries007.tfc.objects.items.wood.ItemDoorTFC;
import net.dries007.tfc.objects.items.wood.ItemLumberTFC;
import net.dries007.tfc.objects.items.wood.ItemWoodenBucket;
//import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.agriculture.Food;

import tfcflorae.objects.PowderTFCF;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.objects.items.food.ItemFoodTFCF;
import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.util.agriculture.FoodTFCF;
import tfcflorae.TFCFlorae;

//import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.CreativeTabsTFC.*;
import static net.dries007.tfc.util.Helpers.getNull;
import static tfcflorae.TFCFlorae.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
@GameRegistry.ObjectHolder(MODID)
public final class ItemsTFCF
{
    @GameRegistry.ObjectHolder("ceramics/pot_calcite")
    public static final ItemPottery CALCITE_POT = getNull();
    @GameRegistry.ObjectHolder("ceramics/pot_quicklime")
    public static final ItemPottery QUICKLIME_POT = getNull();
    @GameRegistry.ObjectHolder("ceramics/pot_slaked_lime")
    public static final ItemPottery SLAKED_LIME_POT = getNull();
    
    @GameRegistry.ObjectHolder("items/twig")
    public static final ItemMiscTFCF TWIG = getNull();
    @GameRegistry.ObjectHolder("items/twig_leaves")
    public static final ItemMiscTFCF TWIG_LEAVES = getNull();
    @GameRegistry.ObjectHolder("items/pinecone")
    public static final ItemMiscTFCF PINECONE = getNull();
    @GameRegistry.ObjectHolder("items/charred_bones")
    public static final ItemMiscTFCF CHARRED_BONES = getNull();
    @GameRegistry.ObjectHolder("items/conch")
    public static final ItemMiscTFCF CONCH = getNull();
    @GameRegistry.ObjectHolder("items/clam")
    public static final ItemMiscTFCF CLAM = getNull();
    @GameRegistry.ObjectHolder("items/live_clam")
    public static final ItemMiscTFCF LIVE_CLAM = getNull();
    @GameRegistry.ObjectHolder("items/scallop")
    public static final ItemMiscTFCF SCALLOP = getNull();
    @GameRegistry.ObjectHolder("items/live_scallop")
    public static final ItemMiscTFCF LIVE_SCALLOP = getNull();
    @GameRegistry.ObjectHolder("items/pearl")
    public static final ItemMiscTFCF PEARL = getNull();
    @GameRegistry.ObjectHolder("items/black_pearl")
    public static final ItemMiscTFCF BLACK_PEARL = getNull();
    @GameRegistry.ObjectHolder("items/starfish")
    public static final ItemMiscTFCF STARFISH = getNull();
    
    @GameRegistry.ObjectHolder("crop/product/papyrus_pulp")
    public static final ItemMiscTFCF PAPYRUS_PULP = getNull();
    @GameRegistry.ObjectHolder("crop/product/papyrus_fiber")
    public static final ItemMiscTFCF PAPYRUS_FIBER = getNull();
    @GameRegistry.ObjectHolder("crop/product/papyrus_paper")
    public static final ItemMiscTFCF PAPYRUS_PAPER = getNull();
    
    @GameRegistry.ObjectHolder("crop/product/agave")
    public static final ItemMiscTFCF AGAVE = getNull();
    @GameRegistry.ObjectHolder("crop/product/sisal_fiber")
    public static final ItemMiscTFCF SISAL_FIBER = getNull();
    @GameRegistry.ObjectHolder("crop/product/sisal_string")
    public static final ItemMiscTFCF SISAL_STRING = getNull();
    @GameRegistry.ObjectHolder("crop/product/sisal_cloth")
    public static final ItemMiscTFCF SISAL_CLOTH = getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_boll")
    public static final ItemMiscTFCF COTTON_BOLL = getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_yarn")
    public static final ItemMiscTFCF COTTON_YARN = getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_cloth")
    public static final ItemMiscTFCF COTTON_CLOTH = getNull();
    @GameRegistry.ObjectHolder("crop/product/flax")
    public static final ItemMiscTFCF FLAX = getNull();
    @GameRegistry.ObjectHolder("crop/product/flax_fiber")
    public static final ItemMiscTFCF FLAX_FIBER = getNull();
    @GameRegistry.ObjectHolder("crop/product/linen_string")
    public static final ItemMiscTFCF LINEN_STRING = getNull();
    @GameRegistry.ObjectHolder("crop/product/linen_cloth")
    public static final ItemMiscTFCF LINEN_CLOTH = getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp")
    public static final ItemMiscTFCF HEMP = getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp_fiber")
    public static final ItemMiscTFCF HEMP_FIBER = getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp_string")
    public static final ItemMiscTFCF HEMP_STRING = getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp_cloth")
    public static final ItemMiscTFCF HEMP_CLOTH = getNull();
    
    @GameRegistry.ObjectHolder("crop/product/sisal_disc")
    public static final Item SISAL_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_disc")
    public static final Item COTTON_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/linen_disc")
    public static final Item LINEN_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp_disc")
    public static final Item HEMP_DISC = getNull();

    @GameRegistry.ObjectHolder("crop/product/soybean_jute_disc")
    public static final Item SOYBEAN_JUTE_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/soybean_sisal_disc")
    public static final Item SOYBEAN_SISAL_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/soybean_cotton_disc")
    public static final Item SOYBEAN_COTTON_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/soybean_linen_disc")
    public static final Item SOYBEAN_LINEN_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/soybean_hemp_disc")
    public static final Item SOYBEAN_HEMP_DISC = getNull();

    @GameRegistry.ObjectHolder("crop/product/linseed_jute_disc")
    public static final Item LINSEED_JUTE_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/linseed_sisal_disc")
    public static final Item LINSEED_SISAL_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/linseed_cotton_disc")
    public static final Item LINSEED_COTTON_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/linseed_linen_disc")
    public static final Item LINSEED_LINEN_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/linseed_hemp_disc")
    public static final Item LINSEED_HEMP_DISC = getNull();

    @GameRegistry.ObjectHolder("crop/product/rape_seed_jute_disc")
    public static final Item RAPE_SEED_JUTE_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/rape_seed_sisal_disc")
    public static final Item RAPE_SEED_SISAL_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/rape_seed_cotton_disc")
    public static final Item RAPE_SEED_COTTON_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/rape_seed_linen_disc")
    public static final Item RAPE_SEED_LINEN_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/rape_seed_hemp_disc")
    public static final Item RAPE_SEED_HEMP_DISC = getNull();

    @GameRegistry.ObjectHolder("crop/product/sunflower_seed_jute_disc")
    public static final Item SUNFLOWER_SEED_JUTE_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/sunflower_seed_sisal_disc")
    public static final Item SUNFLOWER_SEED_SISAL_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/sunflower_seed_cotton_disc")
    public static final Item SUNFLOWER_SEED_COTTON_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/sunflower_seed_linen_disc")
    public static final Item SUNFLOWER_SEED_LINEN_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/sunflower_seed_hemp_disc")
    public static final Item SUNFLOWER_SEED_HEMP_DISC = getNull();

    @GameRegistry.ObjectHolder("crop/product/opium_poppy_seed_jute_disc")
    public static final Item OPIUM_POPPY_SEED_JUTE_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/opium_poppy_seed_sisal_disc")
    public static final Item OPIUM_POPPY_SEED_SISAL_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/opium_poppy_seed_cotton_disc")
    public static final Item OPIUM_POPPY_SEED_COTTON_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/opium_poppy_seed_linen_disc")
    public static final Item OPIUM_POPPY_SEED_LINEN_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/opium_poppy_seed_hemp_disc")
    public static final Item OPIUM_POPPY_SEED_HEMP_DISC = getNull();

    @GameRegistry.ObjectHolder("crop/product/sugar_beet_jute_disc")
    public static final Item SUGAR_BEET_JUTE_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_beet_sisal_disc")
    public static final Item SUGAR_BEET_SISAL_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_beet_cotton_disc")
    public static final Item SUGAR_BEET_COTTON_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_beet_linen_disc")
    public static final Item SUGAR_BEET_LINEN_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_beet_hemp_disc")
    public static final Item SUGAR_BEET_HEMP_DISC = getNull();

    @GameRegistry.ObjectHolder("crop/product/sugar_cane_jute_disc")
    public static final Item SUGAR_CANE_JUTE_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_cane_sisal_disc")
    public static final Item SUGAR_CANE_SISAL_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_cane_cotton_disc")
    public static final Item SUGAR_CANE_COTTON_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_cane_linen_disc")
    public static final Item SUGAR_CANE_LINEN_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/sugar_cane_hemp_disc")
    public static final Item SUGAR_CANE_HEMP_DISC = getNull();


    @GameRegistry.ObjectHolder("crop/product/olive_sisal_disc")
    public static final Item OLIVE_SISAL_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/olive_cotton_disc")
    public static final Item OLIVE_COTTON_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/olive_linen_disc")
    public static final Item OLIVE_LINEN_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/olive_hemp_disc")
    public static final Item OLIVE_HEMP_DISC = getNull();

    @GameRegistry.ObjectHolder("crop/product/sisal_net")
    public static final Item SISAL_NET = getNull();
    @GameRegistry.ObjectHolder("crop/product/cotton_net")
    public static final Item COTTON_NET = getNull();
    @GameRegistry.ObjectHolder("crop/product/linen_net")
    public static final Item LINEN_NET = getNull();
    @GameRegistry.ObjectHolder("crop/product/hemp_net")
    public static final Item HEMP_NET = getNull();

    @GameRegistry.ObjectHolder("crop/product/dirty_sisal_net")
    public static final Item DIRTY_SISAL_NET = getNull();
    @GameRegistry.ObjectHolder("crop/product/dirty_cotton_net")
    public static final Item DIRTY_COTTON_NET = getNull();
    @GameRegistry.ObjectHolder("crop/product/dirty_linen_net")
    public static final Item DIRTY_LINEN_NET = getNull();
    @GameRegistry.ObjectHolder("crop/product/dirty_hemp_net")
    public static final Item DIRTY_HEMP_NET = getNull();
    
    @GameRegistry.ObjectHolder("crop/product/indigo")
    public static final ItemMiscTFCF INDIGO = getNull();
    @GameRegistry.ObjectHolder("crop/product/madder")
    public static final ItemMiscTFCF MADDER = getNull();
    @GameRegistry.ObjectHolder("crop/product/weld")
    public static final ItemMiscTFCF WELD = getNull();
    @GameRegistry.ObjectHolder("crop/product/woad")
    public static final ItemMiscTFCF WOAD = getNull();
    @GameRegistry.ObjectHolder("crop/product/hops")
    public static final ItemMiscTFCF HOPS = getNull();
    @GameRegistry.ObjectHolder("crop/product/rape")
    public static final ItemMiscTFCF RAPE = getNull();

    @GameRegistry.ObjectHolder("crop/product/cannabis_bud")
    public static final ItemMiscTFCF CANNABIS_BUD = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_cannabis_bud")
    public static final ItemMiscTFCF DRIED_CANNABIS_BUD = getNull();
    @GameRegistry.ObjectHolder("crop/product/cannabis_leaf")
    public static final ItemMiscTFCF CANNABIS_LEAF = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_cannabis_leaf")
    public static final ItemMiscTFCF DRIED_CANNABIS_LEAF = getNull();
    @GameRegistry.ObjectHolder("crop/product/coca_leaf")
    public static final ItemMiscTFCF COCA_LEAF = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_coca_leaf")
    public static final ItemMiscTFCF DRIED_COCA_LEAF = getNull();
    @GameRegistry.ObjectHolder("crop/product/opium_poppy_bulb")
    public static final ItemMiscTFCF OPIUM_POPPY_BULB = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_opium_poppy_bulb")
    public static final ItemMiscTFCF DRIED_OPIUM_POPPY_BULB = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_peyote")
    public static final ItemMiscTFCF DRIED_PEYOTE = getNull();
    @GameRegistry.ObjectHolder("crop/product/tobacco_leaf")
    public static final ItemMiscTFCF TOBACCO_LEAF = getNull();
    @GameRegistry.ObjectHolder("crop/product/dried_tobacco_leaf")
    public static final ItemMiscTFCF DRIED_TOBACCO_LEAF = getNull();

    @GameRegistry.ObjectHolder("crop/product/malt_barley")
    public static final ItemMiscTFCF MALT_BARLEY = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_corn")
    public static final ItemMiscTFCF MALT_CORN = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_rice")
    public static final ItemMiscTFCF MALT_RICE = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_rye")
    public static final ItemMiscTFCF MALT_RYE = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_wheat")
    public static final ItemMiscTFCF MALT_WHEAT = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_amaranth")
    public static final ItemMiscTFCF MALT_AMARANTH = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_buckwheat")
    public static final ItemMiscTFCF MALT_BUCKWHEAT = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_fonio")
    public static final ItemMiscTFCF MALT_FONIO = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_millet")
    public static final ItemMiscTFCF MALT_MILLET = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_quinoa")
    public static final ItemMiscTFCF MALT_QUINOA = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_spelt")
    public static final ItemMiscTFCF MALT_SPELT = getNull();
    @GameRegistry.ObjectHolder("crop/product/malt_wild_rice")
    public static final ItemMiscTFCF MALT_WILD_RICE = getNull();
    
    private static ImmutableList<Item> allSimpleItems;

    public static ImmutableList<Item> getAllSimpleItems()
    {
        return allSimpleItems;
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> r = event.getRegistry();
        ImmutableList.Builder<Item> simpleItems = ImmutableList.builder();

        BlocksTFCF.getAllNormalItemBlocks().forEach(x -> registerItemBlock(r, x));
        BlocksTFCF.getAllInventoryItemBlocks().forEach(x -> registerItemBlock(r, x));
        
        for (PowderTFCF powder : PowderTFCF.values())
            simpleItems.add(register(r, "powder/" + powder.name().toLowerCase(), new ItemPowderTFCF(powder), CT_MISC));

        simpleItems.add(register(r, "ceramics/pot_calcite", new ItemPottery(), CT_MISC));
        simpleItems.add(register(r, "ceramics/pot_quicklime", new ItemPottery()
        {
            @Nonnull
            @Override
            public ItemStack getContainerItem(@Nonnull ItemStack itemStack)
            {
                return new ItemStack(ItemsTFC.FIRED_POT);
            }

            @Override
            public boolean hasContainerItem(@Nonnull ItemStack stack)
            {
                return true;
            }
        }, CT_MISC));
        
        simpleItems.add(register(r, "ceramics/pot_slaked_lime", new ItemPottery()
        {
            @Nonnull
            @Override
            public ItemStack getContainerItem(@Nonnull ItemStack itemStack)
            {
                return new ItemStack(ItemsTFC.FIRED_POT);
            }

            @Override
            public boolean hasContainerItem(@Nonnull ItemStack stack)
            {
                return true;
            }
        }, CT_MISC));
        
        // All simple foods (not meals) just use ItemFood and are registered here
        for (FoodTFCF food : FoodTFCF.values())
        {
            if (food.getCategory() != FoodTFCF.Category.MEAL)
            {
                simpleItems.add(register(r, "food/" + food.name().toLowerCase(), new ItemFoodTFCF(food), CT_FOOD));
            }
        }
        		
        simpleItems.add(register(r, "twig", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "twig", "twig_wood", "branch", "branch_wood"), CT_WOOD));
        simpleItems.add(register(r, "twig_leaves", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "twig", "twig_wood", "branch", "branch_wood"), CT_WOOD));
        simpleItems.add(register(r, "pinecone", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "pinecone"), CT_MISC));
        simpleItems.add(register(r, "charred_bones", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "bone_charred"), CT_MISC));
        simpleItems.add(register(r, "conch", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "conch"), CT_MISC));
        simpleItems.add(register(r, "clam", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "clam"), CT_MISC));
        simpleItems.add(register(r, "live_clam", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "clam", "clam_raw"), CT_MISC));
        simpleItems.add(register(r, "scallop", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "scallop"), CT_MISC));
        simpleItems.add(register(r, "live_scallop", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "scallop", "scallop_raw"), CT_MISC));
        simpleItems.add(register(r, "pearl", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "pearl"), CT_MISC));
        simpleItems.add(register(r, "black_pearl", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "pearl_black"), CT_MISC));
        simpleItems.add(register(r, "starfish", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "starfish"), CT_MISC));

        simpleItems.add(register(r, "crop/product/papyrus_pulp", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "pulp", "pulp_papyrus"), CT_MISC));
        simpleItems.add(register(r, "crop/product/papyrus_fiber", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "fiber", "fiber_papyrus"), CT_MISC));
        simpleItems.add(register(r, "crop/product/papyrus_paper", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "paper", "paper_papyrus"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/agave", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_agave", "agave"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_fiber", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "fiber", "fiber_sisal"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_string", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "string", "string_sisal"), CT_MISC));
        simpleItems.add(register(r, "crop/product/sisal_cloth", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "cloth", "cloth_sisal", "fabric", "fabric_sisal", "fabric_hemp"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/cotton_boll", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_cotton", "cotton"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_yarn", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "string", "string_cotton", "yarn", "yarn_cotton"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_cloth", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "cloth", "cloth_high_quality", "cloth_cotton"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/flax", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "crop_flax", "flax"), CT_MISC));
        simpleItems.add(register(r, "crop/product/flax_fiber", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "fiber", "fiber_flax"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_string", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "string", "string_linen"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_cloth", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "cloth", "cloth_linen", "fabric", "fabric_linen", "fabric_hemp"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/hemp", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "crop_hemp", "hemp"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_fiber", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "fiber", "fiber_hemp"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_string", new ItemMiscTFCF(Size.SMALL, Weight.VERY_LIGHT, "string", "string_hemp"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_cloth", new ItemMiscTFCF(Size.SMALL, Weight.LIGHT, "cloth", "cloth_hemp", "fabric", "fabric_hemp"), CT_MISC));

        simpleItems.add(register(r, "crop/product/madder", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_madder", "madder"), CT_MISC));
        simpleItems.add(register(r, "crop/product/weld", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_weld", "weld"), CT_MISC));
        simpleItems.add(register(r, "crop/product/woad", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_woad", "woad"), CT_MISC));
        simpleItems.add(register(r, "crop/product/indigo", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_indigo", "indigo"), CT_MISC));
        simpleItems.add(register(r, "crop/product/rape", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_rape"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hops", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_hops"), CT_MISC));

        simpleItems.add(register(r, "crop/product/dried_sunflower_head", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "item_dried_sunflower_head", "material_dried_sunflower_head"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cannabis_bud", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_cannabis_bud", "item_cannabis_bud", "material_cannabis_bud"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_cannabis_bud", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "item_dried_cannabis_bud", "material_dried_cannabis_bud"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cannabis_leaf", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_cannabis_leaf", "item_cannabis_leaf", "material_cannabis_leaf"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_cannabis_leaf", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "item_dried_cannabis_leaf", "material_dried_cannabis_leaf"), CT_MISC));
        simpleItems.add(register(r, "crop/product/coca_leaf", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_coca_leaf", "item_coca_leaf", "material_coca_leaf"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_coca_leaf", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "item_dried_coca_leaf", "material_dried_coca_leaf"), CT_MISC));
        simpleItems.add(register(r, "crop/product/opium_poppy_bulb", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_opium_poppy_bulb", "item_opium_poppy_bulb", "material_opium_poppy_bulb"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_opium_poppy_bulb", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "item_dried_opium_poppy_bulb", "material_dried_opium_poppy_bulb"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_peyote", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "item_dried_peyote", "material_dried_peyote"), CT_MISC));
        simpleItems.add(register(r, "crop/product/tobacco_leaf", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "crop_tobacco_leaf", "item_tobacco_leaf", "material_tobacco_leaf"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dried_tobacco_leaf", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "item_dried_tobacco_leaf", "material_dried_tobacco_leaf"), CT_MISC));

        //Malted grains
        simpleItems.add(register(r, "crop/product/malt_barley", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt", "malt_barley"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_corn", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt", "malt_corn"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_rice", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt", "malt_rice"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_rye", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt", "malt_rye"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_wheat", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt", "malt_wheat"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_amaranth", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt", "malt_amaranth"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_buckwheat", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt", "malt_buckwheat"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_fonio", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt", "malt_fonio"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_millet", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt", "malt_millet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_quinoa", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt", "malt_quinoa"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_spelt", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt", "malt_spelt"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/malt_wild_rice", new ItemMiscTFCF(Size.VERY_SMALL, Weight.VERY_LIGHT, "malt", "malt_wild_rice"), CT_FOOD));
        
        simpleItems.add(register(r, "crop/product/sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/olive_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_olive"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/olive_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_olive"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/olive_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_olive"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/olive_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_olive"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/soybean_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_soybean"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/soybean_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_soybean"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/linseed_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_linseed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/linseed_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_linseed"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/rape_seed_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_rape_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/rape_seed_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_rape_seed"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/sunflower_seed_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_sunflower_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sunflower_seed_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_sunflower_seed"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/opium_poppy_seed_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_opium_poppy_seed"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/opium_poppy_seed_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_opium_poppy_seed"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/sugar_beet_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_sugar_beet"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_beet_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_sugar_beet"), CT_FOOD));

        simpleItems.add(register(r, "crop/product/sugar_cane_jute_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_sisal_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_sisal_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_cotton_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_cotton_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_linen_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_linen_sugar_cane"), CT_FOOD));
        simpleItems.add(register(r, "crop/product/sugar_cane_hemp_disc", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "disc", "disc_hemp_sugar_cane"), CT_FOOD));
        
        simpleItems.add(register(r, "crop/product/sisal_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_sisal"), CT_MISC));
        simpleItems.add(register(r, "crop/product/cotton_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_cotton"), CT_MISC));
        simpleItems.add(register(r, "crop/product/linen_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_linen"), CT_MISC));
        simpleItems.add(register(r, "crop/product/hemp_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_hemp"), CT_MISC));
        
        simpleItems.add(register(r, "crop/product/dirty_sisal_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_sisal_dirty"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dirty_cotton_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_cotton_dirty"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dirty_linen_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_linen_dirty"), CT_MISC));
        simpleItems.add(register(r, "crop/product/dirty_hemp_net", new ItemMiscTFCF(Size.VERY_SMALL,Weight.VERY_LIGHT, "net", "net_hemp_dirty"), CT_MISC));
        
        allSimpleItems = simpleItems.build();
        
        OreDictionaryHelper.init();
    }
    
    public static void init()
    {
    }
    
    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer)
    {
        ItemBlock itemBlock = producer.apply(block);
        //noinspection ConstantConditions
        itemBlock.setRegistryName(block.getRegistryName());
        return itemBlock;
    }
    
    @SuppressWarnings("ConstantConditions")
    private static void registerItemBlock(IForgeRegistry<Item> r, ItemBlock item)
    {
        item.setRegistryName(item.getBlock().getRegistryName());
        item.setCreativeTab(item.getBlock().getCreativeTab());
        r.register(item);
    }

    private static <T extends Item> T register(IForgeRegistry<Item> r, String name, T item, CreativeTabs ct)
    {
        item.setRegistryName(MODID, name);
        item.setTranslationKey(MODID + "." + name.replace('/', '.'));
        item.setCreativeTab(ct);
        r.register(item);
        return item;
    }
}