package tfcflorae.common.items;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.*;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;

import tfcflorae.common.blocks.Blocks;
import tfcflorae.common.blocks.wood.TFCFWood;
import tfcflorae.common.entities.TFCFEntities;

import static net.dries007.tfc.common.TFCItemGroup.*;
import static tfcflorae.TFCFlorae.MOD_ID;

@SuppressWarnings("unused")
public class Items
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, tfcflorae.TFCFlorae.MOD_ID);

    // TFCFWood

    public static final Map<TFCFWood, RegistryObject<Item>> LUMBER = Helpers.mapOfKeys(TFCFWood.class, wood -> register("wood/lumber/" + wood.name(), WOOD));

    public static final Map<TFCFWood, RegistryObject<Item>> SUPPORTS = Helpers.mapOfKeys(TFCFWood.class, wood ->
        register("wood/support/" + wood.name(), () -> new StandingAndWallBlockItem(Blocks.WOODS.get(wood).get(Wood.BlockType.VERTICAL_SUPPORT).get(), Blocks.WOODS.get(wood).get(Wood.BlockType.HORIZONTAL_SUPPORT).get(), new Item.Properties().tab(WOOD)))
    );

    public static final Map<TFCFWood, RegistryObject<Item>> BOATS = Helpers.mapOfKeys(TFCFWood.class, wood -> register("wood/boat/" + wood.name(), () -> new TFCFBoatItem(TFCFEntities.BOATS.get(wood), new Item.Properties().tab(WOOD))));

    public static final Map<TFCFWood, RegistryObject<Item>> SIGNS = Helpers.mapOfKeys(TFCFWood.class, wood -> register("wood/sign/" + wood.name(), () -> new SignItem(new Item.Properties().tab(WOOD), Blocks.WOODS.get(wood).get(Wood.BlockType.SIGN).get(), Blocks.WOODS.get(wood).get(Wood.BlockType.WALL_SIGN).get())));

    // Misc

    public static final RegistryObject<Item> CINNAMON_BARK = register("cinnamon_bark", MISC);
    public static final RegistryObject<Item> CINNAMON = register("spice/cinnamon", MISC);

    // Fin

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
