package tfcflorae.common.blocks;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.TFCItemGroup;
import net.dries007.tfc.common.blockentities.BerryBushBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.*;
import net.dries007.tfc.common.blocks.devices.*;
import net.dries007.tfc.common.blocks.rock.*;
import net.dries007.tfc.common.blocks.soil.*;
import net.dries007.tfc.common.blocks.wood.*;
import net.dries007.tfc.common.blocks.wood.Wood.BlockType;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import tfcflorae.common.blockentities.TFCFBlockEntities;
import tfcflorae.common.blocks.ceramics.*;
import tfcflorae.common.blocks.rock.*;
import tfcflorae.common.blocks.soil.*;
import tfcflorae.common.blocks.wood.*;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.util.climate.TFCFClimateRanges;

import static tfcflorae.common.blocks.soil.TFCFSoil.TFCFVariant.*;
import static net.dries007.tfc.common.TFCItemGroup.*;

@SuppressWarnings("unused")
public class TFCFBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, tfcflorae.TFCFlorae.MOD_ID);

    // Earth #wow

    public static final Map<TFCFSoil, Map<TFCFSoil.TFCFVariant, RegistryObject<Block>>> TFCFSOIL = Helpers.mapOfKeys(TFCFSoil.class, type -> 
        Helpers.mapOfKeys(TFCFSoil.TFCFVariant.class, variant ->
            register((type.name() + "/" + variant.name()), () -> type.TFCFCreate(variant), EARTH)
        )
    );

    public static final Map<TFCFSoil, Map<SoilBlockType.Variant, RegistryObject<Block>>> TFCSOIL = TFCSoilMap(TFCFSoil.class);

    public static final Map<TFCFSoil.TFCFVariant, DecorationBlockRegistryObject> MUD_BRICK_DECORATIONS = Helpers.mapOfKeys(TFCFSoil.TFCFVariant.class, variant -> new DecorationBlockRegistryObject(
        register(("mud_bricks/" + variant.name() + "_slab"), () -> new SlabBlock(SoilBlockType.mudProperties()), DECORATIONS),
        register(("mud_bricks/" + variant.name() + "_stairs"), () -> new StairBlock(() -> TFCFSOIL.get(TFCFSoil.MUD_BRICKS).get(variant).get().defaultBlockState(), SoilBlockType.mudProperties()), DECORATIONS),
        register(("mud_bricks/" + variant.name() + "_wall"), () -> new WallBlock(SoilBlockType.mudProperties()), DECORATIONS)
    ));

    // Ores

    public static final Map<TFCFRock, Map<Ore, RegistryObject<Block>>> ORES = Helpers.mapOfKeys(TFCFRock.class, rock ->
        Helpers.mapOfKeys(Ore.class, ore -> !ore.isGraded(), ore ->
            register(("ore/" + ore.name() + "/" + rock.name()), ore::create, TFCItemGroup.ORES)
        )
    );

    public static final Map<TFCFRock, Map<Ore, Map<Ore.Grade, RegistryObject<Block>>>> GRADED_ORES = Helpers.mapOfKeys(TFCFRock.class, rock ->
        Helpers.mapOfKeys(Ore.class, Ore::isGraded, ore ->
            Helpers.mapOfKeys(Ore.Grade.class, grade ->
                register(("ore/" + grade.name() + "_" + ore.name() + "/" + rock.name()), ore::create, TFCItemGroup.ORES)
            )
        )
    );

    public static final Map<TFCFRock, Map<OreDeposit, RegistryObject<Block>>> ORE_DEPOSITS = Helpers.mapOfKeys(TFCFRock.class, rock ->
        Helpers.mapOfKeys(OreDeposit.class, ore ->
            register("deposit/" + ore.name() + "/" + rock.name(), () -> new TFCFOreDepositBlock(Block.Properties.of(Material.SAND, MaterialColor.STONE).sound(SoundType.GRAVEL).strength(0.8f), rock, ore), TFCItemGroup.ORES)
        )
    );

    // Rock Stuff #less wow :(

    public static final Map<TFCFRock, Map<Rock.BlockType, RegistryObject<Block>>> ROCK_BLOCKS = Helpers.mapOfKeys(TFCFRock.class, rock ->
        Helpers.mapOfKeys(Rock.BlockType.class, type ->
            register(("rock/" + type.name() + "/" + rock.name()), () -> type.create(rock), ROCK_STUFFS)
        )
    );

    public static final Map<TFCFRock, Map<Rock.BlockType, DecorationBlockRegistryObject>> ROCK_DECORATIONS = Helpers.mapOfKeys(TFCFRock.class, rock ->
        Helpers.mapOfKeys(Rock.BlockType.class, Rock.BlockType::hasVariants, type -> new DecorationBlockRegistryObject(
            register(("rock/" + type.name() + "/" + rock.name()) + "_slab", () -> rock.createSlab(type), ROCK_STUFFS),
            register(("rock/" + type.name() + "/" + rock.name()) + "_stairs", () -> rock.createStairs(type), ROCK_STUFFS),
            register(("rock/" + type.name() + "/" + rock.name()) + "_wall", () -> rock.createWall(type), ROCK_STUFFS)
        ))
    );

    public static final Map<TFCFRock, RegistryObject<Block>> ROCK_ANVILS = Helpers.mapOfKeys(TFCFRock.class, rock -> rock.category() == RockCategory.IGNEOUS_EXTRUSIVE || rock.category() == RockCategory.IGNEOUS_INTRUSIVE, rock ->
        register("rock/anvil/" + rock.name(), () -> new RockAnvilBlock(ExtendedProperties.of(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2, 10).requiresCorrectToolForDrops()).blockEntity(TFCBlockEntities.ANVIL), ROCK_BLOCKS.get(rock).get(Rock.BlockType.RAW)), ROCK_STUFFS)
    );

    public static final Map<TFCFRock, RegistryObject<Block>> MAGMA_BLOCKS = Helpers.mapOfKeys(TFCFRock.class, rock -> rock.category() == RockCategory.IGNEOUS_EXTRUSIVE || rock.category() == RockCategory.IGNEOUS_INTRUSIVE, rock ->
        register("rock/magma/" + rock.name(), () -> new TFCMagmaBlock(Properties.of(Material.STONE, MaterialColor.NETHER).requiresCorrectToolForDrops().lightLevel(s -> 6).randomTicks().strength(0.5F).isValidSpawn((state, level, pos, type) -> type.fireImmune()).hasPostProcess(TFCBlocks::always)), ROCK_STUFFS)
    );

    // Wood

    public static final Map<TFCFWood, Map<Wood.BlockType, RegistryObject<Block>>> WOODS = WoodMapper(TFCFWood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> LeavesOnly = LeavesOnlyMapper(TFCFWood.class);

    // Misc

    public static final RegistryObject<Block> LARGE_EARTHENWARE_VESSEL = register("ceramic/earthenware/large_vessel", () -> new LargeEarthenwareVesselBlock(ExtendedProperties.of(Properties.of(Material.CLAY).strength(2.5F).noOcclusion()).blockEntity(TFCFBlockEntities.LARGE_EARTHENWARE_VESSEL)), MISC);
    public static final Map<DyeColor, RegistryObject<Block>> GLAZED_LARGE_EARTHENWARE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/earthenware/large_vessel/" + color.getName(), () -> new LargeEarthenwareVesselBlock(ExtendedProperties.of(Properties.of(Material.CLAY).strength(2.5F).noOcclusion()).blockEntity(TFCFBlockEntities.LARGE_EARTHENWARE_VESSEL)), MISC)
    );

    public static final RegistryObject<Block> LARGE_KAOLINITE_VESSEL = register("ceramic/kaolinite/large_vessel", () -> new LargeKaoliniteVesselBlock(ExtendedProperties.of(Properties.of(Material.CLAY).strength(2.5F).noOcclusion()).blockEntity(TFCFBlockEntities.LARGE_KAOLINITE_VESSEL)), MISC);
    public static final Map<DyeColor, RegistryObject<Block>> GLAZED_LARGE_KAOLINITE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/kaolinite/large_vessel/" + color.getName(), () -> new LargeKaoliniteVesselBlock(ExtendedProperties.of(Properties.of(Material.CLAY).strength(2.5F).noOcclusion()).blockEntity(TFCFBlockEntities.LARGE_KAOLINITE_VESSEL)), MISC)
    );

    public static final RegistryObject<Block> LARGE_STONEWARE_VESSEL = register("ceramic/stoneware/large_vessel", () -> new LargeStonewareVesselBlock(ExtendedProperties.of(Properties.of(Material.CLAY).strength(2.5F).noOcclusion()).blockEntity(TFCFBlockEntities.LARGE_STONEWARE_VESSEL)), MISC);
    public static final Map<DyeColor, RegistryObject<Block>> GLAZED_LARGE_STONEWARE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/stoneware/large_vessel/" + color.getName(), () -> new LargeStonewareVesselBlock(ExtendedProperties.of(Properties.of(Material.CLAY).strength(2.5F).noOcclusion()).blockEntity(TFCFBlockEntities.LARGE_STONEWARE_VESSEL)), MISC)
    );

    public static boolean always(BlockState state, BlockGetter level, BlockPos pos)
    {
        return true;
    }

    public static boolean never(BlockState state, BlockGetter level, BlockPos pos)
    {
        return false;
    }

    public static boolean never(BlockState state, BlockGetter world, BlockPos pos, EntityType<?> type)
    {
        return false;
    }

    public static int lightEmission(BlockState state)
    {
        return state.getValue(BlockStateProperties.LIT) ? 15 : 0;
    }

    private static Map<TFCFSoil, Map<SoilBlockType.Variant, RegistryObject<Block>>> TFCSoilMap(Class<TFCFSoil> enumClass)
    {
        Map<TFCFSoil, Map<SoilBlockType.Variant, RegistryObject<Block>>> Map = new HashMap<>();

        for (TFCFSoil i : enumClass.getEnumConstants())
        {
            if (i.getTFCFactory() == null)
                continue;
            Map<SoilBlockType.Variant, RegistryObject<Block>> subMap = new HashMap<>();

            for (SoilBlockType.Variant j : SoilBlockType.Variant.values())
            {
                subMap.put(j, register(i.name() + "/" + j.name(), () -> i.TFCCreate(j), EARTH));
            }
            Map.put(i, subMap);
        }
        return Map;
    }

    private static Map<TFCFWood, Map<Wood.BlockType, RegistryObject<Block>>> WoodMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood, Map<Wood.BlockType, RegistryObject<Block>>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (wood.hasLeavesOnly())
                continue;

            Map<Wood.BlockType, RegistryObject<Block>> subMap = new HashMap<>();
            for (Wood.BlockType type : Wood.BlockType.values())
            {
                if (type == BlockType.LEAVES && wood.isFruitTree())
                {
                    subMap.put(type, register(("wood/leaves/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        TFCFLeavesBlock.create(ExtendedProperties.of(Block.Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never)).blockEntity(TFCBlockEntities.BERRY_BUSH).serverTicks(BerryBushBlockEntity::serverTick).flammable(90, 60), 
                        wood.getProductItem(), wood.getStages(), wood.maxDecayDistance(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood)), type.createBlockItem(new Item.Properties().tab(WOOD))));
                }
                else
                    subMap.put(type, register(type.nameFor(wood), type.create(wood), type.createBlockItem(new Item.Properties().tab(WOOD))));
            }
            Map.put(wood, subMap);
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Block>> LeavesOnlyMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood,  RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (!wood.hasLeavesOnly())
                continue;

            BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory = BlockItem::new;
            Function<Block, BlockItem> blockItem = block -> blockItemFactory.apply(block, new Item.Properties().tab(WOOD));

            Map.put(wood, register(("wood/leaves/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
            TFCFLeavesBlock.create(ExtendedProperties.of(Block.Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never)).blockEntity(TFCBlockEntities.BERRY_BUSH).serverTicks(BerryBushBlockEntity::serverTick).flammable(90, 60), 
            wood.getProductItem(), wood.getStages(), wood.maxDecayDistance(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood)), blockItem));
        }
        return Map;
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, CreativeModeTab group)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties().tab(group)));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Item.Properties blockItemProperties)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, blockItemProperties));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        final String actualName = name.toLowerCase(Locale.ROOT);
        final RegistryObject<T> block = BLOCKS.register(actualName, blockSupplier);
        if (blockItemFactory != null)
        {
            TFCFItems.ITEMS.register(actualName, () -> blockItemFactory.apply(block.get()));
        }
        return block;
    }
}
