package tfcflorae.common.items;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvents;
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
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.*;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.wood.TFCFWood;
import tfcflorae.common.entities.TFCFEntities;

import static net.dries007.tfc.common.TFCItemGroup.*;
import static tfcflorae.TFCFlorae.MOD_ID;

@SuppressWarnings("unused")
public class TFCFItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, tfcflorae.TFCFlorae.MOD_ID);

    // Ores

    public static final RegistryObject<Item> BOG_IRON = register("ore/small_bog_iron", TFCItemGroup.ORES);

    // Food

    public static final Map<TFCFFood, RegistryObject<Item>> FOOD = Helpers.mapOfKeys(TFCFFood.class, food ->
        register("food/" + food.name(), () -> new DecayingItem(food.createProperties()))
    );

    // Wood

    public static final Map<TFCFWood, RegistryObject<Item>> LUMBER = lumberWoodMapper();

    public static final Map<TFCFWood, RegistryObject<Item>> SUPPORTS = supportWoodMapper();

    public static final Map<TFCFWood, RegistryObject<Item>> BOATS = boatsWoodMapper();

    public static final Map<TFCFWood, RegistryObject<Item>> SIGNS = signWoodMapper();

    // Decorations

    public static final RegistryObject<Item> HUMUS_MUD_BRICK = register("mud_brick/humus", DECORATIONS);

    // Misc

    public static final RegistryObject<Item> CINNAMON_BARK = register("cinnamon_bark", MISC);
    public static final RegistryObject<Item> CINNAMON = register("spice/cinnamon", MISC);

    // Pottery

    /* Earthenware Clay Ceramics */
    public static final RegistryObject<Item> EARTHENWARE_CLAY_BALL = register("ceramic/earthenware/clay", MISC);

    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_BRICK = register("ceramic/earthenware/unfired_brick", MISC);
    public static final RegistryObject<Item> FIRED_EARTHENWARE_BRICK = register("ceramic/earthenware/fired_brick", MISC);
    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_CRUCIBLE = register("ceramic/earthenware/unfired_crucible", MISC);
    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_FLOWER_POT = register("ceramic/earthenware/unfired_flower_pot", MISC);
    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_PAN = register("ceramic/earthenware/unfired_pan", MISC);

    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_BOWL = register("ceramic/earthenware/unfired_bowl", MISC);
    public static final RegistryObject<Item> EARTHENWARE_BOWL = register("ceramic/earthenware/bowl", MISC);

    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_JUG = register("ceramic/earthenware/unfired_jug", MISC);
    public static final RegistryObject<Item> EARTHENWARE_JUG = register("ceramic/earthenware/jug", () -> new JugItem(new Item.Properties().tab(MISC).stacksTo(1), TFCConfig.SERVER.jugCapacity::get, TFCTags.Fluids.USABLE_IN_JUG));

    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_POT = register("ceramic/earthenware/unfired_pot", MISC);
    public static final RegistryObject<Item> EARTHENWARE_POT = register("ceramic/earthenware/pot", MISC);

    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_SPINDLE_HEAD = register("ceramic/earthenware/unfired_spindle_head", MISC);
    public static final RegistryObject<Item> EARTHENWARE_SPINDLE_HEAD = register("ceramic/earthenware/spindle_head", MISC);

    public static final RegistryObject<Item> UNFIRED_EARTHENWARE_VESSEL = register("ceramic/earthenware/unfired_vessel", MISC);
    public static final RegistryObject<Item> EARTHENWARE_VESSEL = register("ceramic/earthenware/vessel", () -> new VesselItem(new Item.Properties().tab(MISC).stacksTo(1)));

    public static final Map<DyeColor, RegistryObject<Item>> UNFIRED_GLAZED_EARTHENWARE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/earthenware/" + color + "_unfired_vessel", MISC)
    );

    public static final Map<DyeColor, RegistryObject<Item>> GLAZED_EARTHENWARE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/earthenware/" + color + "_glazed_vessel", () -> new VesselItem(new Item.Properties().tab(MISC).stacksTo(1)))
    );

    public static final Map<Metal.ItemType, RegistryObject<Item>> UNFIRED_EARTHENWARE_MOLDS = Helpers.mapOfKeys(Metal.ItemType.class, Metal.ItemType::hasMold, type ->
        register("ceramic/earthenware/unfired_" + type.name() + "_mold", MISC)
    );

    public static final Map<Metal.ItemType, RegistryObject<Item>> EARTHENWARE_MOLDS = Helpers.mapOfKeys(Metal.ItemType.class, Metal.ItemType::hasMold, type ->
        register("ceramic/earthenware/" + type.name() + "_mold", () -> new MoldItem(type, new Item.Properties().tab(MISC).stacksTo(1)))
    );

    public static final RegistryObject<Item> UNFIRED_LARGE_EARTHENWARE_VESSEL = register("ceramic/earthenware/unfired_large_vessel", MISC);
    public static final Map<DyeColor, RegistryObject<Item>> UNFIRED_GLAZED_LARGE_EARTHENWARE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/earthenware/unfired_large_vessel/" + color, MISC)
    );

    /* Kaolinite Clay Ceramics */
    public static final RegistryObject<Item> KAOLINITE_CLAY_BALL = register("ceramic/kaolinite/clay", MISC);

    public static final RegistryObject<Item> UNFIRED_KAOLINITE_BRICK = register("ceramic/kaolinite/unfired_brick", MISC);
    public static final RegistryObject<Item> FIRED_KAOLINITE_BRICK = register("ceramic/kaolinite/fired_brick", MISC);
    public static final RegistryObject<Item> UNFIRED_KAOLINITE_CRUCIBLE = register("ceramic/kaolinite/unfired_crucible", MISC);
    public static final RegistryObject<Item> UNFIRED_KAOLINITE_FLOWER_POT = register("ceramic/kaolinite/unfired_flower_pot", MISC);
    public static final RegistryObject<Item> UNFIRED_KAOLINITE_PAN = register("ceramic/kaolinite/unfired_pan", MISC);

    public static final RegistryObject<Item> UNFIRED_KAOLINITE_BOWL = register("ceramic/kaolinite/unfired_bowl", MISC);
    public static final RegistryObject<Item> KAOLINITE_BOWL = register("ceramic/kaolinite/bowl", MISC);

    public static final RegistryObject<Item> UNFIRED_KAOLINITE_JUG = register("ceramic/kaolinite/unfired_jug", MISC);
    public static final RegistryObject<Item> KAOLINITE_JUG = register("ceramic/kaolinite/jug", () -> new JugItem(new Item.Properties().tab(MISC).stacksTo(1), TFCConfig.SERVER.jugCapacity::get, TFCTags.Fluids.USABLE_IN_JUG));

    public static final RegistryObject<Item> UNFIRED_KAOLINITE_POT = register("ceramic/kaolinite/unfired_pot", MISC);
    public static final RegistryObject<Item> KAOLINITE_POT = register("ceramic/kaolinite/pot", MISC);

    public static final RegistryObject<Item> UNFIRED_KAOLINITE_SPINDLE_HEAD = register("ceramic/kaolinite/unfired_spindle_head", MISC);
    public static final RegistryObject<Item> KAOLINITE_SPINDLE_HEAD = register("ceramic/kaolinite/spindle_head", MISC);

    public static final RegistryObject<Item> UNFIRED_KAOLINITE_VESSEL = register("ceramic/kaolinite/unfired_vessel", MISC);
    public static final RegistryObject<Item> KAOLINITE_VESSEL = register("ceramic/kaolinite/vessel", () -> new VesselItem(new Item.Properties().tab(MISC).stacksTo(1)));

    public static final Map<DyeColor, RegistryObject<Item>> UNFIRED_GLAZED_KAOLINITE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/kaolinite/" + color + "_unfired_vessel", MISC)
    );

    public static final Map<DyeColor, RegistryObject<Item>> GLAZED_KAOLINITE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/kaolinite/" + color + "_glazed_vessel", () -> new VesselItem(new Item.Properties().tab(MISC).stacksTo(1)))
    );

    public static final Map<Metal.ItemType, RegistryObject<Item>> UNFIRED_KAOLINITE_MOLDS = Helpers.mapOfKeys(Metal.ItemType.class, Metal.ItemType::hasMold, type ->
        register("ceramic/kaolinite/unfired_" + type.name() + "_mold", MISC)
    );

    public static final Map<Metal.ItemType, RegistryObject<Item>> KAOLINITE_MOLDS = Helpers.mapOfKeys(Metal.ItemType.class, Metal.ItemType::hasMold, type ->
        register("ceramic/kaolinite/" + type.name() + "_mold", () -> new MoldItem(type, new Item.Properties().tab(MISC).stacksTo(1)))
    );

    public static final RegistryObject<Item> UNFIRED_LARGE_KAOLINITE_VESSEL = register("ceramic/kaolinite/unfired_large_vessel", MISC);
    public static final Map<DyeColor, RegistryObject<Item>> UNFIRED_GLAZED_LARGE_KAOLINITE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/kaolinite/unfired_large_vessel/" + color, MISC)
    );

    /* Stoneware Clay Ceramics */
    public static final RegistryObject<Item> STONEWARE_CLAY_BALL = register("ceramic/stoneware/clay", MISC);

    public static final RegistryObject<Item> UNFIRED_STONEWARE_BRICK = register("ceramic/stoneware/unfired_brick", MISC);
    public static final RegistryObject<Item> FIRED_STONEWARE_BRICK = register("ceramic/stoneware/fired_brick", MISC);
    public static final RegistryObject<Item> UNFIRED_STONEWARE_CRUCIBLE = register("ceramic/stoneware/unfired_crucible", MISC);
    public static final RegistryObject<Item> UNFIRED_STONEWARE_FLOWER_POT = register("ceramic/stoneware/unfired_flower_pot", MISC);
    public static final RegistryObject<Item> UNFIRED_STONEWARE_PAN = register("ceramic/stoneware/unfired_pan", MISC);

    public static final RegistryObject<Item> UNFIRED_STONEWARE_BOWL = register("ceramic/stoneware/unfired_bowl", MISC);
    public static final RegistryObject<Item> STONEWARE_BOWL = register("ceramic/stoneware/bowl", MISC);

    public static final RegistryObject<Item> UNFIRED_STONEWARE_JUG = register("ceramic/stoneware/unfired_jug", MISC);
    public static final RegistryObject<Item> STONEWARE_JUG = register("ceramic/stoneware/jug", () -> new JugItem(new Item.Properties().tab(MISC).stacksTo(1), TFCConfig.SERVER.jugCapacity::get, TFCTags.Fluids.USABLE_IN_JUG));

    public static final RegistryObject<Item> UNFIRED_STONEWARE_POT = register("ceramic/stoneware/unfired_pot", MISC);
    public static final RegistryObject<Item> STONEWARE_POT = register("ceramic/stoneware/pot", MISC);

    public static final RegistryObject<Item> UNFIRED_STONEWARE_SPINDLE_HEAD = register("ceramic/stoneware/unfired_spindle_head", MISC);
    public static final RegistryObject<Item> STONEWARE_SPINDLE_HEAD = register("ceramic/stoneware/spindle_head", MISC);

    public static final RegistryObject<Item> UNFIRED_STONEWARE_VESSEL = register("ceramic/stoneware/unfired_vessel", MISC);
    public static final RegistryObject<Item> STONEWARE_VESSEL = register("ceramic/stoneware/vessel", () -> new VesselItem(new Item.Properties().tab(MISC).stacksTo(1)));

    public static final Map<DyeColor, RegistryObject<Item>> UNFIRED_GLAZED_STONEWARE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/stoneware/" + color + "_unfired_vessel", MISC)
    );

    public static final Map<DyeColor, RegistryObject<Item>> GLAZED_STONEWARE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/stoneware/" + color + "_glazed_vessel", () -> new VesselItem(new Item.Properties().tab(MISC).stacksTo(1)))
    );

    public static final Map<Metal.ItemType, RegistryObject<Item>> UNFIRED_STONEWARE_MOLDS = Helpers.mapOfKeys(Metal.ItemType.class, Metal.ItemType::hasMold, type ->
        register("ceramic/stoneware/unfired_" + type.name() + "_mold", MISC)
    );

    public static final Map<Metal.ItemType, RegistryObject<Item>> STONEWARE_MOLDS = Helpers.mapOfKeys(Metal.ItemType.class, Metal.ItemType::hasMold, type ->
        register("ceramic/stoneware/" + type.name() + "_mold", () -> new MoldItem(type, new Item.Properties().tab(MISC).stacksTo(1)))
    );

    public static final RegistryObject<Item> UNFIRED_LARGE_STONEWARE_VESSEL = register("ceramic/stoneware/unfired_large_vessel", MISC);
    public static final Map<DyeColor, RegistryObject<Item>> UNFIRED_GLAZED_LARGE_STONEWARE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/stoneware/unfired_large_vessel/" + color, MISC)
    );

    // Fin

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

            Map.put(wood, register("wood/boat/" + wood.name(), () -> new TFCFBoatItem(TFCFEntities.BOATS.get(wood), new Item.Properties().tab(WOOD))));
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
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }
}
