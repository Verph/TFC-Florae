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
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.Mineral;
import tfcflorae.common.blocks.rock.TFCFRock;
import tfcflorae.common.blocks.soil.Colors;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.common.blocks.wood.TFCFWood;
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

    public static final RegistryObject<Item> BOG_IRON = register("ore/small_bog_iron", TFCItemGroup.ORES);

    public static final RegistryObject<Item> GLOWSTONE_SHARDS = register("crystal/shard/glowstone", TFCItemGroup.ORES);
    public static final Map<Gem, RegistryObject<Item>> GEM_SHARDS = Helpers.mapOfKeys(Gem.class, gem ->
        register("crystal/shard/" + gem.name(), TFCItemGroup.ORES)
    );

    public static final Map<Mineral, RegistryObject<Item>> MINERALS = Helpers.mapOfKeys(Mineral.class, mineral ->
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

    /* Earthenware Clay Ceramics */
    public static final RegistryObject<Item> EARTHENWARE_CLAY_BALL = register("ceramic/earthenware/clay", MISC);

    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_BRICK = register("ceramic/earthenware/unfired_brick", MISC);
    public static final RegistryObject<Item> FIRED_EARTHENWARE_BRICK = register("ceramic/earthenware/fired_brick", MISC);
    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_FLOWER_POT = register("ceramic/earthenware/unfired/flower_pot", MISC);
    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_PAN = register("ceramic/earthenware/unfired/pan", MISC);

    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_BOWL = register("ceramic/earthenware/unfired/bowl", MISC);
    public static final RegistryObject<Item> EARTHENWARE_BOWL = register("ceramic/earthenware/fired/bowl", MISC);

    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_JUG = register("ceramic/earthenware/unfired/jug", MISC);
    public static final RegistryObject<Item> EARTHENWARE_JUG = register("ceramic/earthenware/fired/jug", () -> new JugItem(new Item.Properties().tab(MISC).stacksTo(1), TFCConfig.SERVER.jugCapacity::get, TFCTags.Fluids.USABLE_IN_JUG));

    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_POT = register("ceramic/earthenware/unfired/pot", MISC);
    public static final RegistryObject<Item> EARTHENWARE_POT = register("ceramic/earthenware/fired/pot", MISC);

    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_SPINDLE_HEAD = register("ceramic/earthenware/unfired/spindle_head", MISC);
    public static final RegistryObject<Item> EARTHENWARE_SPINDLE_HEAD = register("ceramic/earthenware/fired/spindle_head", MISC);

    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_VESSEL = register("ceramic/earthenware/unfired/vessel", MISC);
    public static final RegistryObject<Item> EARTHENWARE_VESSEL = register("ceramic/earthenware/fired/vessel", () -> new VesselItem(new Item.Properties().tab(MISC).stacksTo(1)));

    public static final Map<DyeColor, RegistryObject<Item>> UNFIRED_GLAZED_EARTHENWARE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/earthenware/unfired/glazed_vessel/" + color, MISC)
    );

    public static final Map<DyeColor, RegistryObject<Item>> GLAZED_EARTHENWARE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/earthenware/fired/glazed_vessel/" + color, () -> new VesselItem(new Item.Properties().tab(MISC).stacksTo(1)))
    );

    public static final Map<Metal.ItemType, RegistryObject<Item>> UNFIRED_EARTHENWARE_MOLDS = Helpers.mapOfKeys(Metal.ItemType.class, Metal.ItemType::hasMold, type ->
        register("ceramic/earthenware/unfired/mold/" + type.name(), MISC)
    );

    public static final Map<Metal.ItemType, RegistryObject<Item>> EARTHENWARE_MOLDS = Helpers.mapOfKeys(Metal.ItemType.class, Metal.ItemType::hasMold, type ->
        register("ceramic/earthenware/fired/mold/" + type.name(), () -> new MoldItem(type, new Item.Properties().tab(MISC).stacksTo(1)))
    );

    public static final RegistryObject<Item> UNFIRED_LARGE_EARTHENWARE_VESSEL = register("ceramic/earthenware/unfired/large_vessel", MISC);

    /* Kaolinite Clay Ceramics */
    public static final RegistryObject<Item> KAOLINITE_CLAY_BALL = register("ceramic/kaolinite/clay", MISC);

    public static final RegistryObject<Item> UNFIRED_KAOLINITE_BRICK = register("ceramic/kaolinite/unfired_brick", MISC);
    public static final RegistryObject<Item> FIRED_KAOLINITE_BRICK = register("ceramic/kaolinite/fired_brick", MISC);
    public static final RegistryObject<Item> UNFIRED_KAOLINITE_FLOWER_POT = register("ceramic/kaolinite/unfired/flower_pot", MISC);
    public static final RegistryObject<Item> UNFIRED_KAOLINITE_PAN = register("ceramic/kaolinite/unfired/pan", MISC);

    public static final RegistryObject<Item> UNFIRED_KAOLINITE_BOWL = register("ceramic/kaolinite/unfired/bowl", MISC);
    public static final RegistryObject<Item> KAOLINITE_BOWL = register("ceramic/kaolinite/fired/bowl", MISC);

    public static final RegistryObject<Item> UNFIRED_KAOLINITE_JUG = register("ceramic/kaolinite/unfired/jug", MISC);
    public static final RegistryObject<Item> KAOLINITE_JUG = register("ceramic/kaolinite/fired/jug", () -> new JugItem(new Item.Properties().tab(MISC).stacksTo(1), TFCConfig.SERVER.jugCapacity::get, TFCTags.Fluids.USABLE_IN_JUG));

    public static final RegistryObject<Item> UNFIRED_KAOLINITE_POT = register("ceramic/kaolinite/unfired/pot", MISC);
    public static final RegistryObject<Item> KAOLINITE_POT = register("ceramic/kaolinite/fired/pot", MISC);

    public static final RegistryObject<Item> UNFIRED_KAOLINITE_SPINDLE_HEAD = register("ceramic/kaolinite/unfired/spindle_head", MISC);
    public static final RegistryObject<Item> KAOLINITE_SPINDLE_HEAD = register("ceramic/kaolinite/fired/spindle_head", MISC);

    public static final RegistryObject<Item> UNFIRED_KAOLINITE_VESSEL = register("ceramic/kaolinite/unfired/vessel", MISC);
    public static final RegistryObject<Item> KAOLINITE_VESSEL = register("ceramic/kaolinite/fired/vessel", () -> new VesselItem(new Item.Properties().tab(MISC).stacksTo(1)));

    public static final Map<DyeColor, RegistryObject<Item>> UNFIRED_GLAZED_KAOLINITE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/kaolinite/unfired/glazed_vessel/" + color, MISC)
    );

    public static final Map<DyeColor, RegistryObject<Item>> GLAZED_KAOLINITE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/kaolinite/fired/glazed_vessel/" + color, () -> new VesselItem(new Item.Properties().tab(MISC).stacksTo(1)))
    );

    public static final Map<Metal.ItemType, RegistryObject<Item>> UNFIRED_KAOLINITE_MOLDS = Helpers.mapOfKeys(Metal.ItemType.class, Metal.ItemType::hasMold, type ->
        register("ceramic/kaolinite/unfired/mold/" + type.name(), MISC)
    );

    public static final Map<Metal.ItemType, RegistryObject<Item>> KAOLINITE_MOLDS = Helpers.mapOfKeys(Metal.ItemType.class, Metal.ItemType::hasMold, type ->
        register("ceramic/kaolinite/fired/mold/" + type.name(), () -> new MoldItem(type, new Item.Properties().tab(MISC).stacksTo(1)))
    );

    public static final RegistryObject<Item> UNFIRED_LARGE_KAOLINITE_VESSEL = register("ceramic/kaolinite/unfired/large_vessel", MISC);

    /* Stoneware Clay Ceramics */
    public static final RegistryObject<Item> STONEWARE_CLAY_BALL = register("ceramic/stoneware/clay", MISC);

    public static final RegistryObject<Item> UNFIRED_STONEWARE_BRICK = register("ceramic/stoneware/unfired_brick", MISC);
    public static final RegistryObject<Item> FIRED_STONEWARE_BRICK = register("ceramic/stoneware/fired_brick", MISC);
    public static final RegistryObject<Item> UNFIRED_STONEWARE_FLOWER_POT = register("ceramic/stoneware/unfired/flower_pot", MISC);
    public static final RegistryObject<Item> UNFIRED_STONEWARE_PAN = register("ceramic/stoneware/unfired/pan", MISC);

    public static final RegistryObject<Item> UNFIRED_STONEWARE_BOWL = register("ceramic/stoneware/unfired/bowl", MISC);
    public static final RegistryObject<Item> STONEWARE_BOWL = register("ceramic/stoneware/fired/bowl", MISC);

    public static final RegistryObject<Item> UNFIRED_STONEWARE_JUG = register("ceramic/stoneware/unfired/jug", MISC);
    public static final RegistryObject<Item> STONEWARE_JUG = register("ceramic/stoneware/fired/jug", () -> new JugItem(new Item.Properties().tab(MISC).stacksTo(1), TFCConfig.SERVER.jugCapacity::get, TFCTags.Fluids.USABLE_IN_JUG));

    public static final RegistryObject<Item> UNFIRED_STONEWARE_POT = register("ceramic/stoneware/unfired/pot", MISC);
    public static final RegistryObject<Item> STONEWARE_POT = register("ceramic/stoneware/fired/pot", MISC);

    public static final RegistryObject<Item> UNFIRED_STONEWARE_SPINDLE_HEAD = register("ceramic/stoneware/unfired/spindle_head", MISC);
    public static final RegistryObject<Item> STONEWARE_SPINDLE_HEAD = register("ceramic/stoneware/fired/spindle_head", MISC);

    public static final RegistryObject<Item> UNFIRED_STONEWARE_VESSEL = register("ceramic/stoneware/unfired/vessel", MISC);
    public static final RegistryObject<Item> STONEWARE_VESSEL = register("ceramic/stoneware/fired/vessel", () -> new VesselItem(new Item.Properties().tab(MISC).stacksTo(1)));

    public static final Map<DyeColor, RegistryObject<Item>> UNFIRED_GLAZED_STONEWARE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/stoneware/unfired/glazed_vessel/" + color, MISC)
    );

    public static final Map<DyeColor, RegistryObject<Item>> GLAZED_STONEWARE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/stoneware/fired/glazed_vessel/" + color, () -> new VesselItem(new Item.Properties().tab(MISC).stacksTo(1)))
    );

    public static final Map<Metal.ItemType, RegistryObject<Item>> UNFIRED_STONEWARE_MOLDS = Helpers.mapOfKeys(Metal.ItemType.class, Metal.ItemType::hasMold, type ->
        register("ceramic/stoneware/unfired/mold/" + type.name(), MISC)
    );

    public static final Map<Metal.ItemType, RegistryObject<Item>> STONEWARE_MOLDS = Helpers.mapOfKeys(Metal.ItemType.class, Metal.ItemType::hasMold, type ->
        register("ceramic/stoneware/fired/mold/" + type.name(), () -> new MoldItem(type, new Item.Properties().tab(MISC).stacksTo(1)))
    );

    public static final RegistryObject<Item> UNFIRED_LARGE_STONEWARE_VESSEL = register("ceramic/stoneware/unfired/large_vessel", MISC);

    // Soil stuff

    public static final Map<SandBlockType, RegistryObject<Item>> SAND_PILE_TFC = Helpers.mapOfKeys(SandBlockType.class, type ->
        register("sand_pile/" + type.name(), EARTH)
    );

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

    // Fin

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
