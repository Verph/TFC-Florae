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
import net.minecraft.world.level.block.entity.ChestBlockEntity;
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
import net.dries007.tfc.common.items.ChestBlockItem;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import tfcflorae.client.TFCFSounds;
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
<<<<<<< Updated upstream
=======
    public static final Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<TFCFRock, DecorationBlockRegistryObject>>> TFCROCKSOILDECO2 = TFCRockSoilDeco2Mapper(TFCFRockSoil.class);
    public static final Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<TFCFRock, DecorationBlockRegistryObject>>> TFCFROCKSOILDECO2 = TFCFRockSoilDeco2Mapper(TFCFRockSoil.class);

    // Ores

    public static final Map<TFCFRock, Map<Ore, RegistryObject<Block>>> ORES = Helpers.mapOfKeys(TFCFRock.class, rock ->
        Helpers.mapOfKeys(Ore.class, ore -> !ore.isGraded(), ore ->
            register(("ore/" + ore.name() + "/" + rock.name()), () -> ore.create(rock), TFCItemGroup.ORES)
        )
    );
    public static final Map<TFCFRock, Map<Ore, Map<Ore.Grade, RegistryObject<Block>>>> GRADED_ORES = Helpers.mapOfKeys(TFCFRock.class, rock ->
        Helpers.mapOfKeys(Ore.class, Ore::isGraded, ore ->
            Helpers.mapOfKeys(Ore.Grade.class, grade ->
                register(("ore/" + grade.name() + "_" + ore.name() + "/" + rock.name()), () -> ore.create(rock), TFCItemGroup.ORES)
            )
        )
    );
    public static final Map<TFCFRock, Map<OreDeposit, RegistryObject<Block>>> ORE_DEPOSITS = Helpers.mapOfKeys(TFCFRock.class, rock ->
        Helpers.mapOfKeys(OreDeposit.class, ore ->
            register("deposit/" + ore.name() + "/" + rock.name(), () -> new TFCFOreDepositBlock(Block.Properties.of(Material.SAND, MaterialColor.STONE).sound(SoundType.GRAVEL).strength(rock.category().hardness(2.0f)), rock, ore), TFCItemGroup.ORES) // Same hardness as gravel
        )
<<<<<<< Updated upstream
=======
    );

    // Rock Stuff

    public static final Map<Rock, Map<TFCFRock.TFCFBlockType, RegistryObject<Block>>> ROCK_BLOCKS = rockTypeTFCMapper(Rock.class);
    public static final Map<TFCFRock, Map<Rock.BlockType, RegistryObject<Block>>> TFCF_ROCK_BLOCKS = rockMapper(TFCFRock.class);
    public static final Map<TFCFRock, Map<TFCFRock.TFCFBlockType, RegistryObject<Block>>> TFCF_ROCKTYPE_BLOCKS = rockTypeMapper(TFCFRock.class);
    public static final Map<DripstoneRock, Map<Rock.BlockType, RegistryObject<Block>>> DRIPSTONE_BLOCKS = dripstoneMapper(DripstoneRock.class);

    public static final Map<Rock, RegistryObject<Block>> TFC_COLUMN = Helpers.mapOfKeys(Rock.class, rock ->
        register("rock/column/" + rock.name(), () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops()), ROCK_STUFFS)
    );
    public static final Map<TFCFRock, RegistryObject<Block>> TFCF_COLUMN = Helpers.mapOfKeys(TFCFRock.class, rock ->
        register("rock/column/" + rock.name(), () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops()), ROCK_STUFFS)
    );

    public static final Map<Rock, RegistryObject<Block>> TFC_POLISHED_COLUMN = Helpers.mapOfKeys(Rock.class, rock ->
        register("rock/polished_column/" + rock.name(), () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops()), ROCK_STUFFS)
    );
    public static final Map<TFCFRock, RegistryObject<Block>> TFCF_POLISHED_COLUMN = Helpers.mapOfKeys(TFCFRock.class, rock ->
        register("rock/polished_column/" + rock.name(), () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops()), ROCK_STUFFS)
    );

    public static final Map<Rock, RegistryObject<Block>> TFC_FLAGSTONE = Helpers.mapOfKeys(Rock.class, rock ->
        register("rock/flagstone/" + rock.name(), () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops()), ROCK_STUFFS)
    );
    public static final Map<TFCFRock, RegistryObject<Block>> TFCF_FLAGSTONE = Helpers.mapOfKeys(TFCFRock.class, rock ->
        register("rock/flagstone/" + rock.name(), () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops()), ROCK_STUFFS)
>>>>>>> Stashed changes
    );
>>>>>>> Stashed changes

    // Rock Stuff

    public static final Map<Rock, Map<TFCFRock.TFCFBlockType, RegistryObject<Block>>> ROCK_BLOCKS = Helpers.mapOfKeys(Rock.class, rock ->
        Helpers.mapOfKeys(TFCFRock.TFCFBlockType.class, type ->
            register(("rock/" + type.name() + "/" + rock.name()), () -> type.create(rock), ROCK_STUFFS)
        )
    );

<<<<<<< Updated upstream
    public static final Map<Rock, Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject>> ROCK_DECORATIONS = RockDecoMapper(Rock.class);
=======
    public static final Map<TFCFRock, RegistryObject<Block>> MAGMA_BLOCKS = Helpers.mapOfKeys(TFCFRock.class, rock -> rock.category() == RockCategory.IGNEOUS_EXTRUSIVE || rock.category() == RockCategory.IGNEOUS_INTRUSIVE, rock ->
        register("rock/magma/" + rock.name(), () -> new TFCMagmaBlock(Properties.of(Material.STONE, MaterialColor.NETHER).requiresCorrectToolForDrops().lightLevel(s -> 6).randomTicks().strength(0.5F).isValidSpawn((state, level, pos, type) -> type.fireImmune()).hasPostProcess(TFCFBlocks::always)), ROCK_STUFFS)
    );

    public static final Map<TFCFRock, RegistryObject<Block>> MAGMA_BLOCKS = Helpers.mapOfKeys(TFCFRock.class, rock -> rock.category() == RockCategory.IGNEOUS_EXTRUSIVE || rock.category() == RockCategory.IGNEOUS_INTRUSIVE, rock ->
        register("rock/magma/" + rock.name(), () -> new TFCMagmaBlock(Properties.of(Material.STONE, MaterialColor.NETHER).requiresCorrectToolForDrops().lightLevel(s -> 6).randomTicks().strength(0.5F).isValidSpawn((state, level, pos, type) -> type.fireImmune()).hasPostProcess(TFCFBlocks::always)), ROCK_STUFFS)
    );

    public static final Map<TFCFRock, Map<Rock.BlockType, DecorationBlockRegistryObject>> TFCF_ROCKTYPE_DECORATIONS = RockTypeDecoTFCMapper(TFCFRock.class);
    public static final Map<Rock, Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject>> TFC_ROCK_DECORATIONS = RockDecoTFCMapper(Rock.class);
    public static final Map<TFCFRock, Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject>> TFCF_ROCK_DECORATIONS = RockDecoTFCFMapper(TFCFRock.class);
>>>>>>> Stashed changes

    // Wood

    public static final RegistryObject<Block> CHARRED_TREE_LOG = register("wood/log/charred_tree", () -> new LogBlock(ExtendedProperties.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.TERRACOTTA_BLACK : MaterialColor.TERRACOTTA_BLACK).strength(8f).sound(TFCSounds.CHARCOAL).requiresCorrectToolForDrops().flammableLikeLogs(), TFCFBlocks.CHARRED_TREE_STRIPPED_LOG), WOOD);
    public static final RegistryObject<Block> CHARRED_TREE_STRIPPED_LOG = register("wood/stripped_log/charred_tree", () -> new LogBlock(ExtendedProperties.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.TERRACOTTA_BLACK : MaterialColor.TERRACOTTA_BLACK).strength(7.5f).sound(TFCSounds.CHARCOAL).requiresCorrectToolForDrops().flammableLikeLogs(), null), WOOD);
    public static final RegistryObject<Block> CHARRED_TREE_WOOD = register("wood/wood/charred_tree", () -> new LogBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.TERRACOTTA_BLACK).strength(8f).sound(TFCSounds.CHARCOAL).requiresCorrectToolForDrops().flammableLikeLogs(), TFCFBlocks.CHARRED_TREE_STRIPPED_WOOD), WOOD);
    public static final RegistryObject<Block> CHARRED_TREE_STRIPPED_WOOD = register("wood/stripped_wood/charred_tree", () -> new LogBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.TERRACOTTA_BLACK).strength(7.5f).sound(TFCSounds.CHARCOAL).requiresCorrectToolForDrops().flammableLikeLogs(), null), WOOD);
    public static final RegistryObject<Block> CHARRED_TREE_TWIG = register("wood/twig/charred_tree", () -> GroundcoverBlock.twig(ExtendedProperties.of(Material.GRASS).strength(0.05F, 0.0F).sound(TFCSounds.CHARCOAL).noCollission().flammableLikeWool()), WOOD);

    public static final Map<TFCFWood, Map<Wood.BlockType, RegistryObject<Block>>> WOODS = woodMapper(TFCFWood.class);
    public static final Map<Wood, RegistryObject<Block>> NORMAL_BOOKSHELF_TFC = normalBookshelfMapperTFC(Wood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> LEAVES_ONLY = leavesOnlyMapper(TFCFWood.class);
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
=======
>>>>>>> Stashed changes
    //public static final Map<Wood, RegistryObject<Block>> CHISELED_BOOKSHELF_TFC = chiseledBookshelfMapperTFC(Wood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> CHISELED_BOOKSHELF_TFCF = chiseledBookshelfMapperTFCF(TFCFWood.class);
    public static final Map<Wood, RegistryObject<Block>> LOG_WALL = woodWallMapperTFC(Wood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> WOOD_WALL = woodWallMapperTFCF(TFCFWood.class);
>>>>>>> Stashed changes
    public static final Map<TFCFWood, RegistryObject<Block>> MANGROVE_ROOTS = mangroveRootsMapper(TFCFWood.class);

    // Misc

    public static final RegistryObject<SlabBlock> FIRE_BRICKS_SLAB = register(("ceramic/fire_bricks/brick_slab"), () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<StairBlock> FIRE_BRICKS_STAIRS = register(("ceramic/fire_bricks/brick_stairs"), () -> new StairBlock(() -> TFCBlocks.FIRE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<WallBlock> FIRE_BRICKS_WALL = register(("ceramic/fire_bricks/brick_wall"), () -> new WallBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);

    public static final RegistryObject<Block> EARTHENWARE_CLAY = register("ceramic/earthenware/clay_block", () -> new Block(Properties.of(Material.CLAY, MaterialColor.COLOR_ORANGE).strength(0.6F).sound(TFCFSounds.MUD)), DECORATIONS);
    public static final RegistryObject<Block> EARTHENWARE_BRICKS = register("ceramic/earthenware/bricks", () -> new Block(Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<SlabBlock> EARTHENWARE_BRICKS_SLAB = register(("ceramic/earthenware/brick_slab"), () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<StairBlock> EARTHENWARE_BRICKS_STAIRS = register(("ceramic/earthenware/brick_stairs"), () -> new StairBlock(() -> EARTHENWARE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<WallBlock> EARTHENWARE_BRICKS_WALL = register(("ceramic/earthenware/brick_wall"), () -> new WallBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);

    public static final RegistryObject<Block> KAOLINITE__CLAY = register("ceramic/kaolinite/clay_block", () -> new Block(Properties.of(Material.CLAY, MaterialColor.COLOR_PINK).strength(0.6F).sound(TFCFSounds.MUD)), DECORATIONS);
    public static final RegistryObject<Block> KAOLINITE_BRICKS = register("ceramic/kaolinite/bricks", () -> new Block(Properties.of(Material.STONE, MaterialColor.COLOR_PINK).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<SlabBlock> KAOLINITE_BRICKS_SLAB = register(("ceramic/kaolinite/brick_slab"), () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<StairBlock> KAOLINITE_BRICKS_STAIRS = register(("ceramic/kaolinite/brick_stairs"), () -> new StairBlock(() -> EARTHENWARE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<WallBlock> KAOLINITE_BRICKS_WALL = register(("ceramic/kaolinite/brick_wall"), () -> new WallBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);

    public static final RegistryObject<Block> STONEWARE_CLAY = register("ceramic/stoneware/clay_block", () -> new Block(Properties.of(Material.CLAY, MaterialColor.COLOR_GRAY).strength(0.6F).sound(TFCFSounds.MUD)), DECORATIONS);
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

    private static Map<TFCFWood, Map<Wood.BlockType, RegistryObject<Block>>> woodMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood, Map<Wood.BlockType, RegistryObject<Block>>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (wood.hasLeavesOnly())
                continue;

            Map<Wood.BlockType, RegistryObject<Block>> subMap = new HashMap<>();
            for (Wood.BlockType type : Wood.BlockType.values())
            {
<<<<<<< Updated upstream
                if (type == BlockType.LEAVES && (wood.isFruitTree() || wood.isMangrove()))
                {
                    if (wood.isFruitTree())
                    {
                        subMap.put(type, register(("wood/leaves/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            TFCFLeavesBlock.create(ExtendedProperties.of(Block.Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never)).blockEntity(TFCFBlockEntities.LARGE_FRUIT_TREE).serverTicks(FruitTreeBlockEntity::serverTick).flammable(60, 30), 
=======
                if (type == BlockType.LOG && wood.isFruitTree() && wood.hasFruitingLog())
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFFruitingLogBlock(ExtendedProperties.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? wood.woodColor() : wood.barkColor()).strength(8f).sound(SoundType.WOOD).requiresCorrectToolForDrops().flammableLikeLogs().blockEntity(TFCFBlockEntities.LARGE_FRUIT_TREE).serverTicks(FruitTreeBlockEntity::serverTick), wood.getBlock(Wood.BlockType.STRIPPED_LOG), 
                        wood.getProductItem(), wood.getStages(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood)), type.createBlockItem(new Item.Properties().tab(WOOD))));
                }
                else if (type == BlockType.WOOD && wood.isFruitTree() && wood.hasFruitingLog())
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFFruitingLogBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(8f).requiresCorrectToolForDrops().flammableLikeLogs().blockEntity(TFCFBlockEntities.LARGE_FRUIT_TREE).serverTicks(FruitTreeBlockEntity::serverTick), wood.getBlock(Wood.BlockType.STRIPPED_WOOD), 
                        wood.getProductItem(), wood.getStages(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood)), type.createBlockItem(new Item.Properties().tab(WOOD))));
                }
                else if (type == BlockType.LEAVES && (wood.isFruitTree() || wood.isMangrove()) && !wood.hasFruitingLog())
                {
                    if (wood.isFruitTree())
                    {
                        subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            TFCFLeavesBlock.create(ExtendedProperties.of(Block.Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never)).blockEntity(TFCFBlockEntities.LARGE_FRUIT_TREE).serverTicks(FruitTreeBlockEntity::serverTick).flammableLikeLeaves(), 
>>>>>>> Stashed changes
                            wood.getProductItem(), wood.getStages(), wood.maxDecayDistance(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood)), type.createBlockItem(new Item.Properties().tab(WOOD))));
                    }
                    else if (wood.isMangrove())
                    {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
                        subMap.put(type, register(("wood/leaves/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            TFCFMangroveLeavesBlock.create(ExtendedProperties.of(Block.Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never)).flammable(60, 30), wood.maxDecayDistance()), type.createBlockItem(new Item.Properties().tab(WOOD))));
=======
=======
>>>>>>> Stashed changes
                        subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            TFCFMangroveLeavesBlock.create(ExtendedProperties.of(Block.Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never)).blockEntity(TFCFBlockEntities.LARGE_FRUIT_TREE).serverTicks(FruitTreeBlockEntity::serverTick).flammableLikeLeaves(), wood,
                            wood.getProductItem(), wood.getStages(), wood.maxDecayDistance(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood)), type.createBlockItem(new Item.Properties().tab(WOOD))));
>>>>>>> Stashed changes
                    }
                }
                else if (type == BlockType.SAPLING && wood.isMangrove())
                {
<<<<<<< Updated upstream
                    subMap.put(type, register(("wood/sapling/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFMangrovePropaguleBlock(wood, ExtendedProperties.of(Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0).sound(SoundType.GRASS)).flammable(60, 30).blockEntity(TFCBlockEntities.TICK_COUNTER), wood.daysToGrow()), type.createBlockItem(new Item.Properties().tab(WOOD))));
=======
                    if (wood.isMangrove())
                    {
                        subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new TFCFMangrovePropaguleBlock(wood, ExtendedProperties.of(Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0).sound(SoundType.GRASS)).flammableLikeLeaves().blockEntity(TFCBlockEntities.TICK_COUNTER), wood.daysToGrow()), type.createBlockItem(new Item.Properties().tab(WOOD))));
                    }
                    else
                    {
                        subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new TFCFSaplingBlock(wood, wood.tree(), ExtendedProperties.of(Material.PLANT).noCollission().randomTicks().strength(0).sound(SoundType.GRASS).flammableLikeLeaves().blockEntity(TFCBlockEntities.TICK_COUNTER), wood.daysToGrow()), type.createBlockItem(new Item.Properties().tab(WOOD))));
                    }
>>>>>>> Stashed changes
                }
                else if (type == BlockType.TRAPPED_CHEST)
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFTrappedChestBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.5F).flammableLikePlanks().blockEntity(TFCFBlockEntities.TRAPPED_CHEST).clientTicks(ChestBlockEntity::lidAnimateTick), wood.getSerializedName()), type.createBlockItem(new Item.Properties().tab(WOOD))));
                }
                else if (type == BlockType.CHEST)
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFChestBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.5F).flammableLikePlanks().blockEntity(TFCFBlockEntities.CHEST).clientTicks(ChestBlockEntity::lidAnimateTick), wood.getSerializedName()), type.createBlockItem(new Item.Properties().tab(WOOD))));
                }
                else if (type == BlockType.SIGN)
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCStandingSignBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).noCollission().strength(1F).flammableLikePlanks().blockEntity(TFCFBlockEntities.SIGN)), type.createBlockItem(new Item.Properties().tab(WOOD))));
                }
                else if (type == BlockType.WALL_SIGN)
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCWallSignBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).noCollission().strength(1F).dropsLike(wood.getBlock(BlockType.SIGN)).flammableLikePlanks().blockEntity(TFCFBlockEntities.SIGN)), type.createBlockItem(new Item.Properties().tab(WOOD))));
                }
                else if (type == BlockType.LECTERN)
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCLecternBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).noCollission().strength(2.5F).flammableLikePlanks().blockEntity(TFCFBlockEntities.LECTERN)), type.createBlockItem(new Item.Properties().tab(WOOD))));
                }
                else if (type == BlockType.BOOKSHELF)
                {
                    subMap.put(type, register(("wood/bookshelf/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new ExtendedBlock(ExtendedProperties.of(Block.Properties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.0F, 3.0F)).flammableLikePlanks()), WOOD));
                }
                else if (type == BlockType.TRAPPED_CHEST)
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFTrappedChestBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.5F).flammableLikePlanks().blockEntity(TFCFBlockEntities.TRAPPED_CHEST).clientTicks(ChestBlockEntity::lidAnimateTick), wood.getSerializedName()), type.createBlockItem(new Item.Properties().tab(WOOD))));
                }
                else if (type == BlockType.CHEST)
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFChestBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.5F).flammableLikePlanks().blockEntity(TFCFBlockEntities.CHEST).clientTicks(ChestBlockEntity::lidAnimateTick), wood.getSerializedName()), type.createBlockItem(new Item.Properties().tab(WOOD))));
                }
                else if (type == BlockType.SIGN)
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCStandingSignBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).noCollission().strength(1F).flammableLikePlanks().blockEntity(TFCFBlockEntities.SIGN)), type.createBlockItem(new Item.Properties().tab(WOOD))));
                }
                else if (type == BlockType.WALL_SIGN)
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCWallSignBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).noCollission().strength(1F).dropsLike(wood.getBlock(BlockType.SIGN)).flammableLikePlanks().blockEntity(TFCFBlockEntities.SIGN)), type.createBlockItem(new Item.Properties().tab(WOOD))));
                }
                else if (type == BlockType.LECTERN)
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCLecternBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).noCollission().strength(2.5F).flammableLikePlanks().blockEntity(TFCFBlockEntities.LECTERN)), type.createBlockItem(new Item.Properties().tab(WOOD))));
                }
                else if (type == BlockType.BOOKSHELF)
                {
                    subMap.put(type, register(("wood/bookshelf/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new ExtendedBlock(ExtendedProperties.of(Block.Properties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.0F, 3.0F)).flammableLikePlanks()), WOOD));
                }
                else
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), type.create(wood), type.createBlockItem(new Item.Properties().tab(WOOD))));
            }
            Map.put(wood, subMap);
        }
        return Map;
    }

    private static Map<Wood, RegistryObject<Block>> chiseledBookshelfMapperTFC(Class<Wood> enumClass)
    {
        Map<Wood, RegistryObject<Block>> Map = new HashMap<>();
        for (Wood wood : enumClass.getEnumConstants())
        {
            Map.put(wood, register(("wood/chiseled_bookshelf/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                new ChiseledBookshelfBlock(ExtendedProperties.of(Block.Properties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.0F, 3.0F)).blockEntity(TFCFBlockEntities.CHISELED_BOOKSHELF).flammableLikePlanks()), WOOD));
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Block>> chiseledBookshelfMapperTFCF(Class<TFCFWood> enumClass)
<<<<<<< Updated upstream
=======
    {
        Map<TFCFWood, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (wood.hasLeavesOnly())
                continue;

            Map.put(wood, register(("wood/chiseled_bookshelf/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                new ChiseledBookshelfBlock(ExtendedProperties.of(Block.Properties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.0F, 3.0F)).blockEntity(TFCFBlockEntities.CHISELED_BOOKSHELF).flammableLikePlanks()), WOOD));
        }
        return Map;
    }

    private static Map<Wood, RegistryObject<Block>> normalBookshelfMapperTFC(Class<Wood> enumClass)
    {
        Map<Wood, RegistryObject<Block>> Map = new HashMap<>();
        for (Wood wood : enumClass.getEnumConstants())
        {
            Map.put(wood, register(("wood/bookshelf/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                new ExtendedBlock(ExtendedProperties.of(Block.Properties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.0F, 3.0F)).flammableLikePlanks()), WOOD));
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Block>> leavesOnlyMapper(Class<TFCFWood> enumClass)
>>>>>>> Stashed changes
    {
        Map<TFCFWood, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (wood.hasLeavesOnly())
                continue;

            Map.put(wood, register(("wood/chiseled_bookshelf/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                new ChiseledBookshelfBlock(ExtendedProperties.of(Block.Properties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.0F, 3.0F)).blockEntity(TFCFBlockEntities.CHISELED_BOOKSHELF).flammableLikePlanks()), WOOD));
        }
        return Map;
    }

    private static Map<Wood, RegistryObject<Block>> normalBookshelfMapperTFC(Class<Wood> enumClass)
    {
        Map<Wood, RegistryObject<Block>> Map = new HashMap<>();
        for (Wood wood : enumClass.getEnumConstants())
        {
            Map.put(wood, register(("wood/bookshelf/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                new ExtendedBlock(ExtendedProperties.of(Block.Properties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.0F, 3.0F)).flammableLikePlanks()), WOOD));
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Block>> leavesOnlyMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood,  RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (!wood.hasLeavesOnly())
                continue;

            BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory = BlockItem::new;
            Function<Block, BlockItem> blockItem = block -> blockItemFactory.apply(block, new Item.Properties().tab(WOOD));

            Map.put(wood, register(("wood/leaves/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                TFCFLeavesBlock.create(ExtendedProperties.of(Block.Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never)).blockEntity(TFCFBlockEntities.LARGE_FRUIT_TREE).serverTicks(FruitTreeBlockEntity::serverTick).flammable(60, 30), 
                wood.getProductItem(), wood.getStages(), wood.maxDecayDistance(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood)), blockItem));
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Block>> mangroveRootsMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood,  RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (wood.isMangrove())
            {
                BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory = BlockItem::new;
                Function<Block, BlockItem> blockItem = block -> blockItemFactory.apply(block, new Item.Properties().tab(WOOD));

                Map.put(wood, register(("wood/roots/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
<<<<<<< Updated upstream
                    new TFCFMangroveRootsBlocks(wood, ExtendedProperties.of(Block.Properties.of(Material.WOOD).noCollission().randomTicks().strength(0.7F).sound(TFCFSounds.MANGROVE_ROOTS)).flammable(60, 30)), blockItem));
=======
                    new TFCFMangroveRootsBlocks(wood, ExtendedProperties.of(Block.Properties.of(Material.WOOD).noCollission().randomTicks().strength(0.7F).sound(TFCFSounds.MANGROVE_ROOTS)).flammableLikeLeaves()), blockItem));
            }
            else continue;
        }
        return Map;
    }

    private static Map<TFCFWood, Map<SoilBlockType.Variant, RegistryObject<Block>>> TFCmangroveRootsMuddyMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood, Map<SoilBlockType.Variant, RegistryObject<Block>>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (wood.isMangrove())
            {
                Map<SoilBlockType.Variant, RegistryObject<Block>> soilVariantMap = new HashMap<>();
                for (SoilBlockType.Variant soilVariant : SoilBlockType.Variant.values())
                {
                    BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory = BlockItem::new;
                    Function<Block, BlockItem> blockItem = block -> blockItemFactory.apply(block, new Item.Properties().tab(WOOD));

                    soilVariantMap.put(soilVariant, register(("wood/roots/" + wood.getSerializedName() + "/" + soilVariant.name()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFMangroveMuddyRootsBlocks(wood, ExtendedProperties.of(Block.Properties.of(Material.WOOD).noCollission().randomTicks().strength(0.7F).sound(TFCFSounds.MANGROVE_ROOTS)).flammableLikeLeaves()), blockItem));
                }
                Map.put(wood, soilVariantMap);
            }
            else continue;
        }
        return Map;
    }

    private static Map<TFCFWood, Map<TFCFSoil.TFCFVariant, RegistryObject<Block>>> TFCFmangroveRootsMuddyMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood, Map<TFCFSoil.TFCFVariant, RegistryObject<Block>>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (wood.isMangrove())
            {
                Map<TFCFSoil.TFCFVariant, RegistryObject<Block>> soilVariantMap = new HashMap<>();
                for (TFCFSoil.TFCFVariant soilVariant : TFCFSoil.TFCFVariant.values())
                {
                    BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory = BlockItem::new;
                    Function<Block, BlockItem> blockItem = block -> blockItemFactory.apply(block, new Item.Properties().tab(WOOD));

                    soilVariantMap.put(soilVariant, register(("wood/roots/" + wood.getSerializedName() + "/" + soilVariant.name()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFMangroveMuddyRootsBlocks(wood, ExtendedProperties.of(Block.Properties.of(Material.WOOD).noCollission().randomTicks().strength(0.7F).sound(TFCFSounds.MUDDY_MANGROVE_ROOTS)).flammableLikeLeaves()), blockItem));
                }
                Map.put(wood, soilVariantMap);
            }
            else continue;
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Block>> joshuaTrunkMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (wood.isJoshua())
            {
                BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory = BlockItem::new;
                Function<Block, BlockItem> blockItem = block -> blockItemFactory.apply(block, new Item.Properties().tab(WOOD));

                Map.put(wood, register(("wood/trunks/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                    TFCFJoshuaTrunkBlock.create(wood, BlockBehaviour.Properties.of(Material.WOOD).randomTicks().noOcclusion().strength(6F).sound(SoundType.WOOD).requiresCorrectToolForDrops().hasPostProcess(TFCFBlocks::always), ExtendedProperties.of(BlockBehaviour.Properties.of(Material.WOOD).randomTicks().noOcclusion().strength(6F).sound(SoundType.WOOD).requiresCorrectToolForDrops().hasPostProcess(TFCFBlocks::always)).flammableLikeLogs(), TFCBlockStateProperties.FRESH_WATER), blockItem));
            }
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Block>> joshuaLeavesMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (wood.isJoshua())
            {
                BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory = BlockItem::new;
                Function<Block, BlockItem> blockItem = block -> blockItemFactory.apply(block, new Item.Properties().tab(WOOD));

                Map.put(wood, register(("wood/leaves_head/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                    TFCFJoshuaLeavesBlock.create(wood, ExtendedProperties.of(Block.Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never).hasPostProcess(TFCFBlocks::always)).blockEntity(TFCFBlockEntities.LARGE_FRUIT_TREE).serverTicks(FruitTreeBlockEntity::serverTick).flammableLikeLeaves(), 
                    wood.getProductItem(), wood.getStages(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood)), blockItem));
                    
>>>>>>> Stashed changes
            }
            else continue;
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

<<<<<<< Updated upstream
    private static Map<Rock, Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject>> RockDecoMapper(Class<Rock> enumClass)
=======
    private static Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<TFCFRock, DecorationBlockRegistryObject>>> TFCRockSoilDeco2Mapper(Class<TFCFRockSoil> enumClass)
    {
        Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<TFCFRock, DecorationBlockRegistryObject>>> Map = new HashMap<>();
        for (TFCFRockSoil soilBlockType : enumClass.getEnumConstants())
        {
            if (soilBlockType.getTFCFFactory2() == null || soilBlockType.getTFCFactory2() == null)
                continue;

            Map<SoilBlockType.Variant, Map<TFCFRock, DecorationBlockRegistryObject>> soilVariantMap = new HashMap<>();
            for (SoilBlockType.Variant soilVariant : SoilBlockType.Variant.values())
            {
                Map<TFCFRock, DecorationBlockRegistryObject> rockMap = new HashMap<>();
                for (TFCFRock rock : TFCFRock.values())
                {
                    rockMap.put(rock, new DecorationBlockRegistryObject(
                        register((soilBlockType.name() + "/" + soilVariant.name() + "/slab/" + rock.name()), () -> new SlabBlock(BlockBehaviour.Properties.copy(TFCFBlocks.TFCROCKSOIL2.get(soilBlockType).get(soilVariant).get(rock).get())), EARTH),
                        register((soilBlockType.name() + "/" + soilVariant.name() + "/stairs/" + rock.name()), () -> new StairBlock(() -> TFCFBlocks.TFCROCKSOIL2.get(soilBlockType).get(soilVariant).get(rock).get().defaultBlockState(), BlockBehaviour.Properties.copy(TFCFBlocks.TFCROCKSOIL2.get(soilBlockType).get(soilVariant).get(rock).get())), EARTH),
                        register((soilBlockType.name() + "/" + soilVariant.name() + "/wall/" + rock.name()), () -> new WallBlock(BlockBehaviour.Properties.copy(TFCFBlocks.TFCROCKSOIL2.get(soilBlockType).get(soilVariant).get(rock).get())), EARTH)
                    ));
                }
                soilVariantMap.put(soilVariant, rockMap);
            }
            Map.put(soilBlockType, soilVariantMap);
        }
        return Map;
    }

    private static Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<TFCFRock, DecorationBlockRegistryObject>>> TFCFRockSoilDeco2Mapper(Class<TFCFRockSoil> enumClass)
    {
        Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<TFCFRock, DecorationBlockRegistryObject>>> Map = new HashMap<>();
        for (TFCFRockSoil soilBlockType : enumClass.getEnumConstants())
        {
            if (soilBlockType.getTFCFFactory2() == null || soilBlockType.getTFCFactory2() == null)
                continue;

            Map<TFCFSoil.TFCFVariant, Map<TFCFRock, DecorationBlockRegistryObject>> soilVariantMap = new HashMap<>();
            for (TFCFSoil.TFCFVariant soilVariant : TFCFSoil.TFCFVariant.values())
            {
                Map<TFCFRock, DecorationBlockRegistryObject> rockMap = new HashMap<>();
                for (TFCFRock rock : TFCFRock.values())
                {
                    rockMap.put(rock, new DecorationBlockRegistryObject(
                        register((soilBlockType.name() + "/" + soilVariant.name() + "/slab/" + rock.name()), () -> new SlabBlock(BlockBehaviour.Properties.copy(TFCFBlocks.TFCFROCKSOIL2.get(soilBlockType).get(soilVariant).get(rock).get())), EARTH),
                        register((soilBlockType.name() + "/" + soilVariant.name() + "/stairs/" + rock.name()), () -> new StairBlock(() -> TFCFBlocks.TFCFROCKSOIL2.get(soilBlockType).get(soilVariant).get(rock).get().defaultBlockState(), BlockBehaviour.Properties.copy(TFCFBlocks.TFCFROCKSOIL2.get(soilBlockType).get(soilVariant).get(rock).get())), EARTH),
                        register((soilBlockType.name() + "/" + soilVariant.name() + "/wall/" + rock.name()), () -> new WallBlock(BlockBehaviour.Properties.copy(TFCFBlocks.TFCFROCKSOIL2.get(soilBlockType).get(soilVariant).get(rock).get())), EARTH)
                    ));
                }
                soilVariantMap.put(soilVariant, rockMap);
            }
            Map.put(soilBlockType, soilVariantMap);
        }
        return Map;
    }

    private static Map<TFCFRock, Map<Rock.BlockType, DecorationBlockRegistryObject>> RockTypeDecoTFCMapper(Class<TFCFRock> enumClass)
    {
        Map<TFCFRock, Map<Rock.BlockType, DecorationBlockRegistryObject>> Map = new HashMap<>();
        for (TFCFRock rock : enumClass.getEnumConstants())
        {
            Map<Rock.BlockType, DecorationBlockRegistryObject> typeMap = new HashMap<>();
            for (Rock.BlockType type : Rock.BlockType.values())
            {
                if (type.hasVariants())
                {
                    typeMap.put(type, new DecorationBlockRegistryObject(
                        register(("rock/" + type.name() + "/slab/" + rock.name()), () -> type.createSlab(rock), ROCK_STUFFS),
                        register(("rock/" + type.name() + "/stairs/" + rock.name()), () -> type.createStairs(rock), ROCK_STUFFS),
                        register(("rock/" + type.name() + "/wall/" + rock.name()), () -> type.createWall(rock), ROCK_STUFFS)
                    ));
                }
            }
            Map.put(rock, typeMap);
        }
        return Map;
    }

    private static Map<Rock, Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject>> RockDecoTFCMapper(Class<Rock> enumClass)
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
=======
    private static Map<Rock, Map<TFCFRock.TFCFBlockType, RegistryObject<Block>>> rockTypeTFCMapper(Class<Rock> enumClass)
    {
        Map<Rock, Map<TFCFRock.TFCFBlockType, RegistryObject<Block>>> Map = new HashMap<>();
        for (Rock rock : enumClass.getEnumConstants())
        {
            Map<TFCFRock.TFCFBlockType, RegistryObject<Block>> typeMap = new HashMap<>();
            for (TFCFRock.TFCFBlockType type : TFCFRock.TFCFBlockType.values())
            {
                if (type.getTFCFactory() == null)
                    continue;

                typeMap.put(type, register(("rock/" + type.name() + "/" + rock.name()), () -> type.createTFC(rock), ROCK_STUFFS));
            }
            Map.put(rock, typeMap);
        }
        return Map;
    }

    private static Map<TFCFRock, Map<Rock.BlockType, RegistryObject<Block>>> rockMapper(Class<TFCFRock> enumClass)
    {
        Map<TFCFRock, Map<Rock.BlockType, RegistryObject<Block>>> Map = new HashMap<>();
        for (TFCFRock rock : enumClass.getEnumConstants())
        {
            Map<Rock.BlockType, RegistryObject<Block>> typeMap = new HashMap<>();
            for (Rock.BlockType type : Rock.BlockType.values())
            {
                typeMap.put(type, register(("rock/" + type.name() + "/" + rock.name()), () -> type.create(rock), ROCK_STUFFS));
            }
            Map.put(rock, typeMap);
        }
        return Map;
    }

    private static Map<TFCFRock, Map<TFCFRock.TFCFBlockType, RegistryObject<Block>>> rockTypeMapper(Class<TFCFRock> enumClass)
    {
        Map<TFCFRock, Map<TFCFRock.TFCFBlockType, RegistryObject<Block>>> Map = new HashMap<>();
        for (TFCFRock rock : enumClass.getEnumConstants())
        {
            Map<TFCFRock.TFCFBlockType, RegistryObject<Block>> typeMap = new HashMap<>();
            for (TFCFRock.TFCFBlockType type : TFCFRock.TFCFBlockType.values())
            {
                if (type.getTFCFFactory() == null)
                    continue;

                typeMap.put(type, register(("rock/" + type.name() + "/" + rock.name()), () -> type.createTFCF(rock), ROCK_STUFFS));
            }
            Map.put(rock, typeMap);
        }
        return Map;
    }

    public static void registerFlowerPotFlowers()
    {
        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
        POTTED_PLANTS.forEach((plant, reg) -> pot.addPlant(PLANTS.get(plant).getId(), reg));
        WOODS.forEach((wood, map) -> pot.addPlant(map.get(Wood.BlockType.SAPLING).getId(), map.get(Wood.BlockType.POTTED_SAPLING)));
    }

<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
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
