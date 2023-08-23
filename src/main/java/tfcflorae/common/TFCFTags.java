package tfcflorae.common;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
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
        public static final TagKey<Block> REPLACEABLE = create("replaceable");
        public static final TagKey<Block> SILKMOTH_TARGET_BLOCKS = create("silkmoth_target_blocks");
        public static final TagKey<Block> SILKMOTH_NESTS = create("silkmoth_nests");
        public static final TagKey<Block> FROG_PREFER_JUMP_TO = create("frog_prefer_jump_to");
        public static final TagKey<Block> FROGS_SPAWNABLE_ON = create("frogs_spawnable_on");
        public static final TagKey<Block> TIDE_POOL_BLOCKS = create("tide_pool_blocks"); // groundcover blocks that spawn in tide pools
        public static final TagKey<Block> CREEPING_STONE_PLANTABLE_ON = create("creeping_stone_plantable_on");
        public static final TagKey<Block> SAPLING_CAN_REPLACE = create("sapling_can_replace");
        public static final TagKey<Block> FUNGI_ON_FALLEN_LOGS = create("fungi_on_fallen_logs");

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
        public static final TagKey<Item> POTS = create("pots");
        public static final TagKey<Item> SILKMOTH_TEMPTATION_ITEMS = create("silkmoth_temptation_items");
        public static final TagKey<Item> FROG_TEMPTATION_ITEMS = create("frog_temptation_items");
        public static final TagKey<Item> PARROT_FOOD = create("parrot_food");
        public static final TagKey<Item> POISONOUS_PARROT_FOOD = create("poisonous_parrot_food");
        public static final TagKey<Item> SAPLINGS = create("saplings");

        private static TagKey<Item> create(String id)
        {
            return TagKey.create(Registry.ITEM_REGISTRY, TFCFHelpers.identifier(id));
        }
    }

    public static class Entities
    {
        public static final TagKey<EntityType<?>> FROG_FOOD = create("frog_food");

        private static TagKey<EntityType<?>> create(String id)
        {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, TFCFHelpers.identifier(id));
        }
    }

    public static class Biomes
    {
        public static final TagKey<Biome> OCEANIC = create("oceanic");
        public static final TagKey<Biome> COASTAL = create("coastal");
        public static final TagKey<Biome> IS_LAKE = create("is_lake");
        public static final TagKey<Biome> IS_RIVER = create("is_river");

        private static TagKey<Biome> create(String id)
        {
            return TagKey.create(Registry.BIOME_REGISTRY, TFCFHelpers.identifier(id));
        }
    }
}
