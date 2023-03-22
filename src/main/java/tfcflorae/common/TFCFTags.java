package tfcflorae.common;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import tfcflorae.util.TFCFHelpers;

public class TFCFTags
{
    public static class Blocks
    {
        public static final TagKey<Block> CLAY = create("clay");
        public static final TagKey<Block> MAGMA_BLOCKS = create("magma_blocks");
        public static final TagKey<Block> PODZOL = create("podzol");
        public static final TagKey<Block> ROOTED_DIRT = create("rooted_dirt");
        public static final TagKey<Block> MOSSY_PACKED_MUD = create("mossy_packed_mud");
        public static final TagKey<Block> MANGROVE_LOGS_CAN_GROW_THROUGH = create("mangrove_logs_can_grow_through");
        public static final TagKey<Block> MANGROVE_ROOTS_CAN_GROW_THROUGH = create("mangrove_roots_can_grow_through");

        private static TagKey<Block> create(String id)
        {
            return TagKey.create(Registry.BLOCK_REGISTRY, TFCFHelpers.identifier(id));
        }
    }

    public static class Items
    {
        public static final TagKey<Item> BRUSHES = create("brushes");
        public static final TagKey<Item> EARTHENWARE_CLAY_KNAPPING = create("earthenware_clay_knapping");
        public static final TagKey<Item> KAOLINITE_CLAY_KNAPPING = create("kaolinite_clay_knapping");
        public static final TagKey<Item> STONEWARE_CLAY_KNAPPING = create("stoneware_clay_knapping");
        public static final TagKey<Item> FLINT_KNAPPING = create("flint_knapping");
        public static final TagKey<Item> MINERAL_SHEETS = create("mineral_sheets"); // Sheets that can be added to piles

        private static TagKey<Item> create(String id)
        {
            return TagKey.create(Registry.ITEM_REGISTRY, TFCFHelpers.identifier(id));
        }
    }
}
