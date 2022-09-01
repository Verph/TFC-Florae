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
import net.dries007.tfc.client.TFCSounds;
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

import tfcflorae.common.blockentities.FruitTreeBlockEntity;
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

    public static final RegistryObject<Block> BOG_IRON = register("dirt/bog_iron", () -> new Block(Properties.of(Material.DIRT, MaterialColor.TERRACOTTA_BROWN).strength(3.0F).sound(TFCSounds.PEAT)), EARTH);
    public static final RegistryObject<Block> SPARSE_BOG_IRON_GRASS = register("sparse_grass/bog_iron", () -> new ConnectedGrassBlock(Properties.of(Material.GRASS).randomTicks().strength(3.0F).sound(TFCSounds.PEAT), BOG_IRON, null, null), EARTH);
    public static final RegistryObject<Block> DENSE_BOG_IRON_GRASS = register("dense_grass/bog_iron", () -> new ConnectedGrassBlock(Properties.of(Material.GRASS).randomTicks().strength(3.0F).sound(TFCSounds.PEAT), SPARSE_BOG_IRON_GRASS, null, null), EARTH);
    public static final RegistryObject<Block> BOG_IRON_GRASS = register("grass/bog_iron", () -> new ConnectedGrassBlock(Properties.of(Material.GRASS).randomTicks().strength(3.0F).sound(TFCSounds.PEAT), DENSE_BOG_IRON_GRASS, null, null), EARTH);
    public static final RegistryObject<Block> ROOTED_BOG_IRON = register("rooted_dirt/bog_iron", () -> new TFCRootedDirtBlock(Block.Properties.of(Material.DIRT, MaterialColor.TERRACOTTA_BROWN).strength(3.5F).sound(SoundType.ROOTED_DIRT), BOG_IRON), EARTH);

    // Rocky Soils

    public static final Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<Rock, RegistryObject<Block>>>> TFCROCKSOIL = TFCRockSoilMapper(TFCFRockSoil.class);
    public static final Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<Rock, RegistryObject<Block>>>> TFCFROCKSOIL = TFCFRockSoilMapper(TFCFRockSoil.class);

    public static final Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<Rock, DecorationBlockRegistryObject>>> TFCROCKSOILDECO = TFCRockSoilDecoMapper(TFCFRockSoil.class);
    public static final Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<Rock, DecorationBlockRegistryObject>>> TFCFROCKSOILDECO = TFCFRockSoilDecoMapper(TFCFRockSoil.class);

    // Rock Stuff

    public static final Map<Rock, Map<TFCFRock.TFCFBlockType, RegistryObject<Block>>> ROCK_BLOCKS = Helpers.mapOfKeys(Rock.class, rock ->
        Helpers.mapOfKeys(TFCFRock.TFCFBlockType.class, type ->
            register(("rock/" + type.name() + "/" + rock.name()), () -> type.create(rock), ROCK_STUFFS)
        )
    );

    public static final Map<Rock, Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject>> ROCK_DECORATIONS = RockDecoMapper(Rock.class);

    // Wood

    public static final Map<TFCFWood, Map<Wood.BlockType, RegistryObject<Block>>> WOODS = WoodMapper(TFCFWood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> LEAVES_ONLY = LeavesOnlyMapper(TFCFWood.class);

    // Misc

    public static final RegistryObject<SlabBlock> FIRE_BRICKS_SLAB = register(("ceramic/fire_bricks/brick_slab"), () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<StairBlock> FIRE_BRICKS_STAIRS = register(("ceramic/fire_bricks/brick_stairs"), () -> new StairBlock(() -> TFCBlocks.FIRE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<WallBlock> FIRE_BRICKS_WALL = register(("ceramic/fire_bricks/brick_wall"), () -> new WallBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);

    public static final RegistryObject<Block> EARTHENWARE_BRICKS_CLAY = register("ceramic/earthenware/clay_block", () -> new Block(Properties.of(Material.CLAY, MaterialColor.COLOR_ORANGE).strength(0.6F).sound(SoundType.GRAVEL)), DECORATIONS);
    public static final RegistryObject<Block> EARTHENWARE_BRICKS = register("ceramic/earthenware/bricks", () -> new Block(Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<SlabBlock> EARTHENWARE_BRICKS_SLAB = register(("ceramic/earthenware/brick_slab"), () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<StairBlock> EARTHENWARE_BRICKS_STAIRS = register(("ceramic/earthenware/brick_stairs"), () -> new StairBlock(() -> EARTHENWARE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<WallBlock> EARTHENWARE_BRICKS_WALL = register(("ceramic/earthenware/brick_wall"), () -> new WallBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);

    public static final RegistryObject<Block> KAOLINITE_BRICKS_CLAY = register("ceramic/kaolinite/clay_block", () -> new Block(Properties.of(Material.CLAY, MaterialColor.COLOR_PINK).strength(0.6F).sound(SoundType.GRAVEL)), DECORATIONS);
    public static final RegistryObject<Block> KAOLINITE_BRICKS = register("ceramic/kaolinite/bricks", () -> new Block(Properties.of(Material.STONE, MaterialColor.COLOR_PINK).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<SlabBlock> KAOLINITE_BRICKS_SLAB = register(("ceramic/kaolinite/brick_slab"), () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<StairBlock> KAOLINITE_BRICKS_STAIRS = register(("ceramic/kaolinite/brick_stairs"), () -> new StairBlock(() -> EARTHENWARE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<WallBlock> KAOLINITE_BRICKS_WALL = register(("ceramic/kaolinite/brick_wall"), () -> new WallBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);

    public static final RegistryObject<Block> STONEWARE_BRICKS_CLAY = register("ceramic/stoneware/clay_block", () -> new Block(Properties.of(Material.CLAY, MaterialColor.COLOR_GRAY).strength(0.6F).sound(SoundType.GRAVEL)), DECORATIONS);
    public static final RegistryObject<Block> STONEWARE_BRICKS = register("ceramic/stoneware/bricks", () -> new Block(Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<SlabBlock> STONEWARE_BRICKS_SLAB = register(("ceramic/stoneware/brick_slab"), () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<StairBlock> STONEWARE_BRICKS_STAIRS = register(("ceramic/stoneware/brick_stairs"), () -> new StairBlock(() -> EARTHENWARE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<WallBlock> STONEWARE_BRICKS_WALL = register(("ceramic/stoneware/brick_wall"), () -> new WallBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);

    public static final RegistryObject<Block> LARGE_EARTHENWARE_VESSEL = register("ceramic/earthenware/fired/large_vessel", () -> new LargeEarthenwareVesselBlock(ExtendedProperties.of(Properties.of(Material.CLAY).strength(2.5F).noOcclusion()).blockEntity(TFCFBlockEntities.LARGE_EARTHENWARE_VESSEL)), MISC);
    /*public static final Map<DyeColor, RegistryObject<Block>> GLAZED_LARGE_EARTHENWARE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/earthenware/fired/large_vessel/" + color.getName(), () -> new LargeEarthenwareVesselBlock(ExtendedProperties.of(Properties.of(Material.CLAY).strength(2.5F).noOcclusion()).blockEntity(TFCFBlockEntities.LARGE_EARTHENWARE_VESSEL)), MISC)
    );*/

    public static final RegistryObject<Block> LARGE_KAOLINITE_VESSEL = register("ceramic/kaolinite/fired/large_vessel", () -> new LargeKaoliniteVesselBlock(ExtendedProperties.of(Properties.of(Material.CLAY).strength(2.5F).noOcclusion()).blockEntity(TFCFBlockEntities.LARGE_KAOLINITE_VESSEL)), MISC);
    /*public static final Map<DyeColor, RegistryObject<Block>> GLAZED_LARGE_KAOLINITE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/kaolinite/fired/large_vessel/" + color.getName(), () -> new LargeKaoliniteVesselBlock(ExtendedProperties.of(Properties.of(Material.CLAY).strength(2.5F).noOcclusion()).blockEntity(TFCFBlockEntities.LARGE_KAOLINITE_VESSEL)), MISC)
    );*/

    public static final RegistryObject<Block> LARGE_STONEWARE_VESSEL = register("ceramic/stoneware/fired/large_vessel", () -> new LargeStonewareVesselBlock(ExtendedProperties.of(Properties.of(Material.CLAY).strength(2.5F).noOcclusion()).blockEntity(TFCFBlockEntities.LARGE_STONEWARE_VESSEL)), MISC);
    /*public static final Map<DyeColor, RegistryObject<Block>> GLAZED_LARGE_STONEWARE_VESSELS = Helpers.mapOfKeys(DyeColor.class, color ->
        register("ceramic/stoneware/fired/large_vessel/" + color.getName(), () -> new LargeStonewareVesselBlock(ExtendedProperties.of(Properties.of(Material.CLAY).strength(2.5F).noOcclusion()).blockEntity(TFCFBlockEntities.LARGE_STONEWARE_VESSEL)), MISC)
    );*/

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
                        TFCFLeavesBlock.create(ExtendedProperties.of(Block.Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never)).blockEntity(TFCFBlockEntities.LARGE_FRUIT_TREE).serverTicks(FruitTreeBlockEntity::serverTick).flammable(90, 60), 
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
            TFCFLeavesBlock.create(ExtendedProperties.of(Block.Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never)).blockEntity(TFCFBlockEntities.LARGE_FRUIT_TREE).serverTicks(FruitTreeBlockEntity::serverTick).flammable(90, 60), 
            wood.getProductItem(), wood.getStages(), wood.maxDecayDistance(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood)), blockItem));
        }
        return Map;
    }

    private static Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<Rock, RegistryObject<Block>>>> TFCRockSoilMapper(Class<TFCFRockSoil> enumClass)
    {
        Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<Rock, RegistryObject<Block>>>> Map = new HashMap<>();
        for (TFCFRockSoil soilBlockType : enumClass.getEnumConstants())
        {
            Map<SoilBlockType.Variant, Map<Rock, RegistryObject<Block>>> soilVariantMap = new HashMap<>();
            for (SoilBlockType.Variant soilVariant : SoilBlockType.Variant.values())
            {
                Map<Rock, RegistryObject<Block>> rockMap = new HashMap<>();
                for (Rock rock : Rock.values())
                {
                    rockMap.put(rock, register((soilBlockType.name() + "/" + soilVariant.name() + "/" + rock.name()), () -> soilBlockType.TFCCreate(soilVariant, rock), EARTH));
                }
                soilVariantMap.put(soilVariant, rockMap);
            }
            Map.put(soilBlockType, soilVariantMap);
        }
        return Map;
    }

    private static Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<Rock, RegistryObject<Block>>>> TFCFRockSoilMapper(Class<TFCFRockSoil> enumClass)
    {
        Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<Rock, RegistryObject<Block>>>> Map = new HashMap<>();
        for (TFCFRockSoil soilBlockType : enumClass.getEnumConstants())
        {
            Map<TFCFSoil.TFCFVariant, Map<Rock, RegistryObject<Block>>> soilVariantMap = new HashMap<>();
            for (TFCFSoil.TFCFVariant soilVariant : TFCFSoil.TFCFVariant.values())
            {
                Map<Rock, RegistryObject<Block>> rockMap = new HashMap<>();
                for (Rock rock : Rock.values())
                {
                    rockMap.put(rock, register((soilBlockType.name() + "/" + soilVariant.name() + "/" + rock.name()), () -> soilBlockType.TFCFCreate(soilVariant, rock), EARTH));
                }
                soilVariantMap.put(soilVariant, rockMap);
            }
            Map.put(soilBlockType, soilVariantMap);
        }
        return Map;
    }

    private static Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<Rock, DecorationBlockRegistryObject>>> TFCRockSoilDecoMapper(Class<TFCFRockSoil> enumClass)
    {
        Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<Rock, DecorationBlockRegistryObject>>> Map = new HashMap<>();
        for (TFCFRockSoil soilBlockType : enumClass.getEnumConstants())
        {
            Map<SoilBlockType.Variant, Map<Rock, DecorationBlockRegistryObject>> soilVariantMap = new HashMap<>();
            for (SoilBlockType.Variant soilVariant : SoilBlockType.Variant.values())
            {
                Map<Rock, DecorationBlockRegistryObject> rockMap = new HashMap<>();
                for (Rock rock : Rock.values())
                {
                    rockMap.put(rock, new DecorationBlockRegistryObject(
                        register((soilBlockType.name() + "/" + soilVariant.name() + "/slab/" + rock.name()), () -> new SlabBlock(BlockBehaviour.Properties.copy(TFCFBlocks.TFCROCKSOIL.get(soilBlockType).get(soilVariant).get(rock).get())), EARTH),
                        register((soilBlockType.name() + "/" + soilVariant.name() + "/stairs/" + rock.name()), () -> new StairBlock(() -> TFCFBlocks.TFCROCKSOIL.get(soilBlockType).get(soilVariant).get(rock).get().defaultBlockState(), BlockBehaviour.Properties.copy(TFCFBlocks.TFCROCKSOIL.get(soilBlockType).get(soilVariant).get(rock).get())), EARTH),
                        register((soilBlockType.name() + "/" + soilVariant.name() + "/wall/" + rock.name()), () -> new WallBlock(BlockBehaviour.Properties.copy(TFCFBlocks.TFCROCKSOIL.get(soilBlockType).get(soilVariant).get(rock).get())), EARTH)
                    ));
                }
                soilVariantMap.put(soilVariant, rockMap);
            }
            Map.put(soilBlockType, soilVariantMap);
        }
        return Map;
    }

    private static Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<Rock, DecorationBlockRegistryObject>>> TFCFRockSoilDecoMapper(Class<TFCFRockSoil> enumClass)
    {
        Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<Rock, DecorationBlockRegistryObject>>> Map = new HashMap<>();
        for (TFCFRockSoil soilBlockType : enumClass.getEnumConstants())
        {
            Map<TFCFSoil.TFCFVariant, Map<Rock, DecorationBlockRegistryObject>> soilVariantMap = new HashMap<>();
            for (TFCFSoil.TFCFVariant soilVariant : TFCFSoil.TFCFVariant.values())
            {
                Map<Rock, DecorationBlockRegistryObject> rockMap = new HashMap<>();
                for (Rock rock : Rock.values())
                {
                    rockMap.put(rock, new DecorationBlockRegistryObject(
                        register((soilBlockType.name() + "/" + soilVariant.name() + "/slab/" + rock.name()), () -> new SlabBlock(BlockBehaviour.Properties.copy(TFCFBlocks.TFCFROCKSOIL.get(soilBlockType).get(soilVariant).get(rock).get())), EARTH),
                        register((soilBlockType.name() + "/" + soilVariant.name() + "/stairs/" + rock.name()), () -> new StairBlock(() -> TFCFBlocks.TFCFROCKSOIL.get(soilBlockType).get(soilVariant).get(rock).get().defaultBlockState(), BlockBehaviour.Properties.copy(TFCFBlocks.TFCFROCKSOIL.get(soilBlockType).get(soilVariant).get(rock).get())), EARTH),
                        register((soilBlockType.name() + "/" + soilVariant.name() + "/wall/" + rock.name()), () -> new WallBlock(BlockBehaviour.Properties.copy(TFCFBlocks.TFCFROCKSOIL.get(soilBlockType).get(soilVariant).get(rock).get())), EARTH)
                    ));
                }
                soilVariantMap.put(soilVariant, rockMap);
            }
            Map.put(soilBlockType, soilVariantMap);
        }
        return Map;
    }

    private static Map<Rock, Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject>> RockDecoMapper(Class<Rock> enumClass)
    {
        Map<Rock, Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject>> Map = new HashMap<>();
        for (Rock rock : enumClass.getEnumConstants())
        {
            Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject> typeMap = new HashMap<>();
            for (TFCFRock.TFCFBlockType type : TFCFRock.TFCFBlockType.values())
            {
                if (type.hasVariants())
                {
                    typeMap.put(type, new DecorationBlockRegistryObject(
                        register(("rock/" + type.name() + "/slab/" + rock.name()), () -> new SlabBlock(BlockBehaviour.Properties.copy(TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS),
                        register(("rock/" + type.name() + "/stairs/" + rock.name()), () -> new StairBlock(() -> TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get().defaultBlockState(), BlockBehaviour.Properties.copy(TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS),
                        register(("rock/" + type.name() + "/wall/" + rock.name()), () -> new WallBlock(BlockBehaviour.Properties.copy(TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS)
                    ));
                }
            }
            Map.put(rock, typeMap);
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
