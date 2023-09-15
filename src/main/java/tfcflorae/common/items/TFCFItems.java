package tfcflorae.common.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.*;
import net.dries007.tfc.common.blocks.*;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.RockCategory;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.common.items.*;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;

import tfcflorae.Config;
import tfcflorae.TFCFlorae;
import tfcflorae.client.TFCFSounds;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.ceramics.Clay;
import tfcflorae.common.blocks.rock.Mineral;
import tfcflorae.common.blocks.rock.TFCFOre;
import tfcflorae.common.blocks.rock.TFCFRock;
import tfcflorae.common.blocks.soil.Colors;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.common.blocks.wood.TFCFWood;
import tfcflorae.common.entities.Fish;
import tfcflorae.common.entities.TFCFEntities;
import tfcflorae.util.TFCFHelpers;

import static net.dries007.tfc.common.TFCItemGroup.*;

@SuppressWarnings("unused")
public final class TFCFItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TFCFlorae.MOD_ID);

    // Sand

    public static final Map<Colors, RegistryObject<Item>> WEATHERED_TERRACOTTA = Helpers.mapOfKeys(Colors.class, type ->
        register("sand/weathered_terracotta_shard/" + type.name(), EARTH)
    );

    // Ores

    public static final RegistryObject<Item> COKE = register("ore/coke", TFCItemGroup.ORES);
    public static final Map<TFCFOre, RegistryObject<Item>> ORES = Helpers.mapOfKeys(TFCFOre.class, ore -> !ore.isGraded(), type ->
        register("ore/" + type.name(), TFCItemGroup.ORES)
    );
    public static final Map<TFCFOre, Map<TFCFOre.Grade, RegistryObject<Item>>> GRADED_ORES = Helpers.mapOfKeys(TFCFOre.class, TFCFOre::isGraded, ore ->
        Helpers.mapOfKeys(TFCFOre.Grade.class, grade ->
            register("ore/" + grade.name() + '_' + ore.name(), TFCItemGroup.ORES)
        )
    );

    public static final Map<TFCFPowder, RegistryObject<Item>> POWDERS = Helpers.mapOfKeys(TFCFPowder.class, powder ->
        register("powder/" + powder.name(), MISC)
    );

    public static final RegistryObject<Item> BOG_IRON = register("ore/small_bog_iron", TFCItemGroup.ORES);

    public static final RegistryObject<Item> GLOWSTONE_SHARDS = register("crystal/shard/glowstone", TFCItemGroup.ORES);
    public static final Map<Gem, RegistryObject<Item>> GEM_SHARDS = Helpers.mapOfKeys(Gem.class, gem ->
        register("crystal/shard/" + gem.name(), TFCItemGroup.ORES)
    );

    public static final Map<Mineral, RegistryObject<Item>> MINERALS = Helpers.mapOfKeys(Mineral.class, mineral ->
        register("mineral/" + mineral.name(), TFCItemGroup.ORES)
    );

    public static final Map<Clay, RegistryObject<Item>> CLAY_MINERALS = Helpers.mapOfKeys(Clay.class, Clay::hasRock, mineral ->
        register("mineral/" + mineral.name(), TFCItemGroup.ORES)
    );

    // Rock Stuff

    public static final Map<TFCFRock, RegistryObject<Item>> BRICKS = Helpers.mapOfKeys(TFCFRock.class, type ->
        register("brick/" + type.name(), ROCK_STUFFS)
    );

    // Food

    public static final Map<TFCFFood, RegistryObject<Item>> FOOD = foodMapper();

    // Wood

    public static final Map<TFCFWood, RegistryObject<Item>> LUMBER = lumberWoodMapper();
    public static final Map<TFCFWood, RegistryObject<Item>> SUPPORTS = supportWoodMapper();
    public static final Map<TFCFWood, RegistryObject<Item>> BOATS = boatsWoodMapper();
    public static final Map<TFCFWood, RegistryObject<Item>> CHEST_MINECARTS = minecartChestWoodMapper();
    public static final Map<TFCFWood, RegistryObject<Item>> SIGNS = signWoodMapper();

    // Decorations

    public static final RegistryObject<Item> HUMUS_MUD_BRICK = register("mud_brick/humus", DECORATIONS);

    // Pottery

    public static final Map<Clay, RegistryObject<Item>> CLAY_BALLS = clayBallMapper();
    public static final Map<Clay, RegistryObject<Item>> CLAY_ITEMS = clayItemMapper();
    public static final Map<Clay, Map<DyeColor, RegistryObject<Item>>> CLAY_VESSELS = clayVesselMapper();
    public static final Map<Clay, Map<Metal.ItemType, RegistryObject<Item>>> CLAY_MOLDS = clayMoldMapper();

    // Soil stuff

    public static final Map<SoilBlockType.Variant, RegistryObject<Item>> SOIL_PILE_TFC = Helpers.mapOfKeys(SoilBlockType.Variant.class, variant ->
        register("soil_pile/" + variant.name(), EARTH)
    );

    public static final Map<TFCFSoil.TFCFVariant, RegistryObject<Item>> SOIL_PILE_TFCF = Helpers.mapOfKeys(TFCFSoil.TFCFVariant.class, variant ->
        register("soil_pile/" + variant.name(), EARTH)
    );

    // Tools

    public static final Map<RockCategory, Map<RockCategory.ItemType, RegistryObject<Item>>> FLINT_TOOLS = flintToolMapper();

    public static final RegistryObject<Item> BRUSHES = register("tools/brush", () -> new BrushItem(new Item.Properties().tab(MISC).stacksTo(1).durability(250)));
    public static final RegistryObject<Item> WALKING_CANES = register("tools/walking_cane", () -> new WalkingCaneItem(new Item.Properties().tab(MISC).stacksTo(1).durability(200)));

    public static final RegistryObject<Item> MULBERRY_LEAVES = register("wood/leaf/mulberry", TFCItemGroup.MISC);
    public static final RegistryObject<Item> SILK_MOTH_EGG = register("animal/product/silk_moth_egg", TFCItemGroup.MISC);
    public static final RegistryObject<Item> SILK_WORM_HATCHERY = register("animal/product/silk_moth_hatchery", TFCItemGroup.MISC);
    public static final RegistryObject<Item> SILK_WORM = register("animal/product/silk_worm", TFCItemGroup.MISC);
    public static final RegistryObject<Item> SILK_WORM_COCOON = register("animal/product/silk_worm_cocoon", TFCItemGroup.MISC);
    public static final RegistryObject<Item> SILK_WORM_COCOON_BOILED = register("animal/product/silk_worm_cocoon_boiled", TFCItemGroup.MISC);

    public static final RegistryObject<Item> TADPOLE_BUCKET = register("bucket/tadpole", () -> new MobBucketItem(TFCFEntities.TADPOLE, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(MISC)));
    public static final Map<Fish, RegistryObject<MobBucketItem>> FRESHWATER_FISH_BUCKETS = Helpers.mapOfKeys(Fish.class, fish -> register("bucket/" + fish.getSerializedName(), () -> new MobBucketItem(TFCFEntities.FRESHWATER_FISH.get(fish), () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))));

    public static final RegistryObject<Item> SILKMOTH_EGG = registerSpawnEgg(TFCFEntities.SILKMOTH, 0xd78ed7, 0xd9fff9);
    public static final RegistryObject<Item> FROG_EGG = registerSpawnEgg(TFCFEntities.FROG, 0xD07444, 0xFFC77C);
    public static final RegistryObject<Item> TADPOLE = registerSpawnEgg(TFCFEntities.TADPOLE, 0x6D533D, 0x160A00);
    public static final RegistryObject<Item> PARROT = registerSpawnEgg(TFCFEntities.PARROT, 0x0DA70B, 0xFF0000);
    public static final Map<Fish, RegistryObject<Item>> FRESHWATER_FISH_EGGS = Helpers.mapOfKeys(Fish.class, fish -> !fish.isCod(), fish -> registerSpawnEgg(TFCFEntities.FRESHWATER_FISH.get(fish), fish.getEggColor1(), fish.getEggColor2()));
    public static final Map<Fish, RegistryObject<Item>> FRESHWATER_COD_FISH_EGGS = Helpers.mapOfKeys(Fish.class, fish -> fish.isCod(), fish -> registerSpawnEgg(TFCFEntities.FRESHWATER_COD_FISH.get(fish), fish.getEggColor1(), fish.getEggColor2()));

    // Fin

    private static Map<Clay, RegistryObject<Item>> clayBallMapper()
    {
        Map<Clay,  RegistryObject<Item>> Map = new HashMap<>();
        for (Clay clay : Clay.values())
        {
            if (clay.hasRock()) continue;

            String clayName = clay.getSerializedName().toLowerCase(Locale.ROOT);

            Map.put(clay, register("ceramic/" + clayName + "/clay", MISC));
        }
        return Map;
    }

    private static Map<Clay, RegistryObject<Item>> clayItemMapper()
    {
        Map<Clay,  RegistryObject<Item>> Map = new HashMap<>();
        for (Clay clay : Clay.values())
        {
            if (clay.hasRock()) continue;
            String clayName = clay.getSerializedName().toLowerCase(Locale.ROOT);

            Map.put(clay, register("ceramic/" + clayName + "/unfired_brick", MISC));
            Map.put(clay, register("ceramic/" + clayName + "/fired_brick", MISC));
            Map.put(clay, register("ceramic/" + clayName + "/unfired/flower_pot", MISC));
            Map.put(clay, register("ceramic/" + clayName + "/unfired/pan", MISC));
            Map.put(clay, register("ceramic/" + clayName + "/unfired/bowl", MISC));
            Map.put(clay, register("ceramic/" + clayName + "/fired/bowl", MISC));
            Map.put(clay, register("ceramic/" + clayName + "/unfired/jug", MISC));
            Map.put(clay, register("ceramic/" + clayName + "/fired/jug", () -> new JugItem(new Item.Properties().tab(MISC).stacksTo(1), TFCConfig.SERVER.jugCapacity::get, TFCTags.Fluids.USABLE_IN_JUG)));
            Map.put(clay, register("ceramic/" + clayName + "/unfired/pot", MISC));
            Map.put(clay, register("ceramic/" + clayName + "/fired/pot", MISC));
            Map.put(clay, register("ceramic/" + clayName + "/unfired/spindle_head", MISC));
            Map.put(clay, register("ceramic/" + clayName + "/fired/spindle_head", MISC));
            Map.put(clay, register("ceramic/" + clayName + "/unfired/vessel", MISC));
            Map.put(clay, register("ceramic/" + clayName + "/fired/vessel", () -> new VesselItem(new Item.Properties().tab(MISC).stacksTo(1))));
            Map.put(clay, register("ceramic/" + clayName + "/unfired/large_vessel", MISC));
        }
        return Map;
    }

    private static Map<Clay, Map<DyeColor, RegistryObject<Item>>> clayVesselMapper()
    {
        Map<Clay, Map<DyeColor, RegistryObject<Item>>> Map = new HashMap<>();
        for (Clay clay : Clay.values())
        {
            if (clay.hasRock()) continue;
            Map<DyeColor, RegistryObject<Item>> subMap = new HashMap<>();
            for (DyeColor color : DyeColor.values())
            {
                String clayName = clay.getSerializedName().toLowerCase(Locale.ROOT);
                String colorName = color.name().toLowerCase(Locale.ROOT);

                subMap.put(color, register("ceramic/" + clayName + "/unfired/glazed_vessel/" + colorName, MISC));
                subMap.put(color, register("ceramic/" + clayName + "/fired/glazed_vessel/" + colorName, () -> new VesselItem(new Item.Properties().tab(MISC).stacksTo(1))));
            }
            Map.put(clay, subMap);
        }
        return Map;
    }

    private static Map<Clay, Map<Metal.ItemType, RegistryObject<Item>>> clayMoldMapper()
    {
        Map<Clay, Map<Metal.ItemType, RegistryObject<Item>>> Map = new HashMap<>();
        for (Clay clay : Clay.values())
        {
            if (clay.hasRock()) continue;
            Map<Metal.ItemType, RegistryObject<Item>> subMap = new HashMap<>();
            for (Metal.ItemType type : Metal.ItemType.values())
            {
                if (!type.hasMold())
                    continue;

                String clayName = clay.getSerializedName().toLowerCase(Locale.ROOT);
                String typeName = type.name().toLowerCase(Locale.ROOT);

                subMap.put(type, register("ceramic/" + clayName + "/unfired/mold/" + typeName, MISC));
                subMap.put(type, register("ceramic/" + clayName + "/fired/mold/" + typeName, () -> new MoldItem(type, new Item.Properties().tab(MISC).stacksTo(1))));
            }
            Map.put(clay, subMap);
        }
        return Map;
    }

    private static Map<RockCategory, Map<RockCategory.ItemType, RegistryObject<Item>>> flintToolMapper()
    {
        Map<RockCategory, Map<RockCategory.ItemType, RegistryObject<Item>>> Map = new HashMap<>();
        for (RockCategory category : RockCategory.class.getEnumConstants())
        {
            if (category != RockCategory.METAMORPHIC)
                continue;

            Map<RockCategory.ItemType, RegistryObject<Item>> subMap = new HashMap<>();
            for (RockCategory.ItemType type : RockCategory.ItemType.values())
            {
                subMap.put(type, register("tools/" + type.name() + "/flint", () -> type.create(category)));
            }
            Map.put(category, subMap);
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Item>> lumberWoodMapper()
    {
        Map<TFCFWood,  RegistryObject<Item>> Map = new HashMap<>();
        for (TFCFWood wood : TFCFWood.class.getEnumConstants())
        {
            if (TFCFBlocks.WOODS.get(wood) == null)
                continue;

            Map.put(wood, register("wood/lumber/" + wood.name(), WOOD));
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Item>> supportWoodMapper()
    {
        Map<TFCFWood,  RegistryObject<Item>> Map = new HashMap<>();
        for (TFCFWood wood : TFCFWood.class.getEnumConstants())
        {
            if (TFCFBlocks.WOODS.get(wood) == null)
                continue;

            Map.put(wood, register("wood/support/" + wood.name(), () -> new StandingAndWallBlockItem(TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.VERTICAL_SUPPORT).get(), TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.HORIZONTAL_SUPPORT).get(), new Item.Properties().tab(WOOD))));
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Item>> boatsWoodMapper()
    {
        Map<TFCFWood,  RegistryObject<Item>> Map = new HashMap<>();
        for (TFCFWood wood : TFCFWood.class.getEnumConstants())
        {
            if (TFCFBlocks.WOODS.get(wood) == null)
                continue;
            
            if (wood == TFCFWood.BAMBOO)
                Map.put(wood, register("wood/boat/" + wood.name(), () -> new TFCFBoatItem(TFCFEntities.BOATS.get(wood), new Item.Properties().tab(WOOD))));
            else
                Map.put(wood, register("wood/boat/" + wood.name(), () -> new TFCFBoatItem(TFCFEntities.BOATS.get(wood), new Item.Properties().tab(WOOD))));
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Item>> minecartChestWoodMapper()
    {
        Map<TFCFWood,  RegistryObject<Item>> Map = new HashMap<>();
        for (TFCFWood wood : TFCFWood.class.getEnumConstants())
        {
            if (TFCFBlocks.WOODS.get(wood) == null)
                continue;

            Map.put(wood, register("wood/chest_minecart/" + wood.name(), () -> new TFCMinecartItem(new Item.Properties().tab(WOOD), TFCEntities.CHEST_MINECART, () -> TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.CHEST).get().asItem())));
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Item>> signWoodMapper()
    {
        Map<TFCFWood,  RegistryObject<Item>> Map = new HashMap<>();
        for (TFCFWood wood : TFCFWood.class.getEnumConstants())
        {
            if (TFCFBlocks.WOODS.get(wood) == null)
                continue;

            Map.put(wood, register("wood/sign/" + wood.name(), () -> new SignItem(new Item.Properties().tab(WOOD), TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.SIGN).get(), TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.WALL_SIGN).get())));
        }
        return Map;
    }

    @SuppressWarnings("deprecation")
    private static Map<TFCFFood, RegistryObject<Item>> foodMapper()
    {
        Map<TFCFFood,  RegistryObject<Item>> Map = new HashMap<>();
        for (TFCFFood food : TFCFFood.class.getEnumConstants())
        {
            if (food.hasEffect())
            {
                if (food == TFCFFood.DRAGONBERRY)
                    Map.put(food, register("food/" + food.name(), () -> new DecayingItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f)
                        .effect(new MobEffectInstance(MobEffects.POISON, 50, 0), 0.3F).alwaysEat().build()).tab(TFCItemGroup.FOOD))));
                else if (food == TFCFFood.ROASTED_ARTISTS_CONK)
                    Map.put(food, register("food/" + food.name(), () -> new DecayingItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f)
                        .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 0.5F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 0), 0.25F).alwaysEat().build()).tab(TFCItemGroup.FOOD))));
                else if (food == TFCFFood.ROASTED_SULPHUR_SHELF)
                    Map.put(food, register("food/" + food.name(), () -> new DecayingItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f)
                        .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 0.5F).effect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 300, 0), 0.25F).alwaysEat().build()).tab(TFCItemGroup.FOOD))));
                else if (food == TFCFFood.ROASTED_TURKEY_TAIL)
                    Map.put(food, register("food/" + food.name(), () -> new DecayingItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f)
                        .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 0.5F).effect(new MobEffectInstance(MobEffects.BLINDNESS, 300, 0), 0.25F).alwaysEat().build()).tab(TFCItemGroup.FOOD))));
                else if (food == TFCFFood.ROASTED_AMANITA)
                    Map.put(food, register("food/" + food.name(), () -> new DecayingItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f)
                        .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 0.5F).effect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 0), 0.5F).effect(new MobEffectInstance(MobEffects.WITHER, 150, 1), 1F).alwaysEat().build()).tab(TFCItemGroup.FOOD))));
                else if (food == TFCFFood.ROASTED_DEATH_CAP)
                    Map.put(food, register("food/" + food.name(), () -> new DecayingItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f)
                        .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 0.5F).effect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 0), 0.5F).effect(new MobEffectInstance(MobEffects.WITHER, 150, 1), 1F).alwaysEat().build()).tab(TFCItemGroup.FOOD))));
                else if (food == TFCFFood.ROASTED_WOOLLY_GOMPHUS)
                    Map.put(food, register("food/" + food.name(), () -> new DecayingItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f)
                        .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 0.5F).effect(new MobEffectInstance(MobEffects.POISON, 300, 0), 0.25F).alwaysEat().build()).tab(TFCItemGroup.FOOD))));
                else if (food == TFCFFood.ROASTED_CHANTERELLE)
                    Map.put(food, register("food/" + food.name(), () -> new DecayingItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f)
                        .effect(new MobEffectInstance(MobEffects.REGENERATION, 80, 0), 0.5F).alwaysEat().build()).tab(TFCItemGroup.FOOD))));
                else if (food == TFCFFood.ROASTED_STINKHORN)
                    Map.put(food, register("food/" + food.name(), () -> new DecayingItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f)
                        .effect(new MobEffectInstance(MobEffects.UNLUCK, 200, 0), 0.5F).alwaysEat().build()).tab(TFCItemGroup.FOOD))));
                else if (food == TFCFFood.ROASTED_INDIGO_MILK_CAP)
                    Map.put(food, register("food/" + food.name(), () -> new DecayingItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f)
                        .effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 50, 0), 0.3F).alwaysEat().build()).tab(TFCItemGroup.FOOD))));
                else if (food == TFCFFood.ROASTED_LIONS_MANE)
                    Map.put(food, register("food/" + food.name(), () -> new DecayingItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f)
                        .effect(new MobEffectInstance(MobEffects.REGENERATION, 75, 0), 0.3F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0), 0.4F).alwaysEat().build()).tab(TFCItemGroup.FOOD))));
                else if (food == TFCFFood.ROASTED_BLACK_POWDERPUFF)
                    Map.put(food, register("food/" + food.name(), () -> new DecayingItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f)
                        .effect(new MobEffectInstance(MobEffects.INVISIBILITY, 25, 0), 0.5F).alwaysEat().build()).tab(TFCItemGroup.FOOD))));
                else if (food == TFCFFood.EUCALYPTUS_LEAVES)
                    Map.put(food, register("food/" + food.name(), () -> new DecayingItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(0).saturationMod(0)
                        .effect(new MobEffectInstance(MobEffects.POISON, 35, 0), 0.2F).alwaysEat().build()).tab(TFCItemGroup.FOOD))));
                else
                    Map.put(food, register("food/" + food.name(), () -> new DecayingItem(food.createProperties())));
            }
            else
                Map.put(food, register("food/" + food.name(), () -> new DecayingItem(food.createProperties())));
        }
        return Map;
    }

    private static Item.Properties prop()
    {
        return new Item.Properties().tab(MISC);
    }

    private static <T extends EntityType<? extends Mob>> RegistryObject<Item> registerSpawnEgg(RegistryObject<T> entity, int color1, int color2)
    {
        return register("spawn_egg/" + entity.getId().getPath(), () -> new ForgeSpawnEggItem(entity, color1, color2, new Item.Properties().tab(MISC)));
    }

    private static RegistryObject<Item> register(String name, CreativeModeTab group)
    {
        return register(name, () -> new Item(new Item.Properties().tab(group)));
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
    {
        /*String replace = name.replace("/", " ");
        String replace2 = replace.replace("_", " ");
        TFCFlorae.LOGGER.debug("\"item." + TFCFlorae.MOD_ID + "." + name.toLowerCase() + "\": " + "\"" + StringUtils.capitalize(replace2) + "\"" + ",");*/
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }
}
