package tfcflorae.common.blocks;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WaterLilyBlockItem;
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
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.common.TFCItemGroup;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.DecorationBlockRegistryObject;
import net.dries007.tfc.common.blocks.ExtendedBlock;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.Gem;
import net.dries007.tfc.common.blocks.GroundcoverBlock;
import net.dries007.tfc.common.blocks.GroundcoverBlockType;
import net.dries007.tfc.common.blocks.OreDeposit;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.TFCMagmaBlock;
import net.dries007.tfc.common.blocks.TFCMaterials;
import net.dries007.tfc.common.blocks.devices.*;
import net.dries007.tfc.common.blocks.plant.BodyPlantBlock;
import net.dries007.tfc.common.blocks.rock.*;
import net.dries007.tfc.common.blocks.soil.*;
import net.dries007.tfc.common.blocks.wood.*;
import net.dries007.tfc.common.blocks.wood.Wood.BlockType;
import net.dries007.tfc.common.fluids.SimpleFluid;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.common.items.BarrelBlockItem;
import net.dries007.tfc.common.items.ChestBlockItem;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistrationHelpers;

import tfcflorae.Config;
import tfcflorae.TFCFlorae;
import tfcflorae.client.TFCFSounds;
import tfcflorae.common.blockentities.*;
import tfcflorae.common.blocks.ceramics.*;
import tfcflorae.common.blocks.devices.*;
import tfcflorae.common.blocks.plant.*;
import tfcflorae.common.blocks.rock.*;
import tfcflorae.common.blocks.soil.*;
import tfcflorae.common.blocks.spidercave.BodyWebBlock;
import tfcflorae.common.blocks.spidercave.CreepingSpiderWebBlock;
import tfcflorae.common.blocks.spidercave.EggBlock;
import tfcflorae.common.blocks.spidercave.WebbedBlock;
import tfcflorae.common.blocks.spidercave.WebbedChestBlock;
import tfcflorae.common.blocks.wood.*;
import tfcflorae.common.fluid.SimpleFluids;
import tfcflorae.common.fluid.TFCFFluids;
import tfcflorae.common.items.TFCFBarrelBlockItem;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.util.TFCFHelpers;
import tfcflorae.util.climate.TFCFClimateRanges;
import tfcflorae.world.feature.tree.BambooTreeGrower;
import tfcflorae.world.feature.tree.TFCFBambooTreeGrower;

import static tfcflorae.common.blocks.soil.TFCFSoil.TFCFVariant.*;
import static net.dries007.tfc.common.TFCItemGroup.*;
import static net.dries007.tfc.common.blocks.rock.RockCategory.*;

@SuppressWarnings("unused")
public final class TFCFBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TFCFlorae.MOD_ID);

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
    public static final RegistryObject<Block> SPARSE_BOG_IRON_GRASS = register("sparse_grass/bog_iron", () -> new ConnectedGrassBlock(Properties.of(Material.GRASS, MaterialColor.TERRACOTTA_BROWN).randomTicks().strength(3.0F).sound(TFCSounds.PEAT), BOG_IRON, null, null), EARTH);
    public static final RegistryObject<Block> DENSE_BOG_IRON_GRASS = register("dense_grass/bog_iron", () -> new ConnectedGrassBlock(Properties.of(Material.GRASS, MaterialColor.GRASS).randomTicks().strength(3.0F).sound(TFCSounds.PEAT), SPARSE_BOG_IRON_GRASS, null, null), EARTH);
    public static final RegistryObject<Block> BOG_IRON_GRASS = register("grass/bog_iron", () -> new ConnectedGrassBlock(Properties.of(Material.GRASS, MaterialColor.GRASS).randomTicks().strength(3.0F).sound(TFCSounds.PEAT), DENSE_BOG_IRON_GRASS, null, null), EARTH);
    public static final RegistryObject<Block> ROOTED_BOG_IRON = register("rooted_dirt/bog_iron", () -> new TFCRootedDirtBlock(Block.Properties.of(Material.DIRT, MaterialColor.TERRACOTTA_BROWN).strength(3.5F).sound(SoundType.ROOTED_DIRT), BOG_IRON), EARTH);
    public static final RegistryObject<Block> MYCELIUM_BOG_IRON = register("mycelium_dirt/bog_iron", () -> new TFCRootedDirtBlock(Block.Properties.of(Material.DIRT, MaterialColor.TERRACOTTA_BROWN).strength(3.5F).sound(SoundType.ROOTED_DIRT), BOG_IRON), EARTH);

    public static final Map<Colors, RegistryObject<Block>> SPARSE_SAND_GRASS = Helpers.mapOfKeys(Colors.class, type -> register(("sand/sparse_grass/" + type.name()), () -> new ConnectedGrassBlock(Properties.of(Material.GRASS, MaterialColor.SAND).randomTicks().strength(3.0F).sound(SoundType.BIG_DRIPLEAF), type.hasSandTFC() ? TFCBlocks.SAND.get(type.toSandTFC(true)) : TFCFBlocks.SAND.get(type), null, null), EARTH));
    public static final Map<Colors, RegistryObject<Block>> DENSE_SAND_GRASS = Helpers.mapOfKeys(Colors.class, type -> register(("sand/dense_grass/" + type.name()), () -> new ConnectedGrassBlock(Properties.of(Material.GRASS, MaterialColor.GRASS).randomTicks().strength(3.0F).sound(SoundType.BIG_DRIPLEAF), SPARSE_SAND_GRASS.get(type), null, null), EARTH));
    public static final Map<Colors, RegistryObject<Block>> SAND_GRASS = Helpers.mapOfKeys(Colors.class, type -> register(("sand/grass/" + type.name()), () -> new ConnectedGrassBlock(Properties.of(Material.GRASS, MaterialColor.GRASS).randomTicks().strength(3.0F).sound(SoundType.BIG_DRIPLEAF), DENSE_SAND_GRASS.get(type), null, null), EARTH));

    public static final Map<TFCFRockSand, Map<Colors, Map<Rock, RegistryObject<Block>>>> ROCKY_SAND_TFC = TFCRockSandMapper(TFCFRockSand.class);
    public static final Map<TFCFRockSand, Map<Colors, Map<TFCFRock, RegistryObject<Block>>>> ROCKY_SAND_TFCF = TFCFRockSandMapper(TFCFRockSand.class);

    public static final Map<Colors, Map<TFCFSandstoneBlockType, RegistryObject<Block>>> SANDSTONE = Helpers.mapOfKeys(Colors.class, color -> color.hasLayered(), color ->
        Helpers.mapOfKeys(TFCFSandstoneBlockType.class, type ->
            register(("sandstone/" + type.name() + "/" + color.name()), () -> new Block(type.properties(color).sound(SoundType.ANCIENT_DEBRIS)), DECORATIONS)
        )
    );

    public static final Map<Colors, Map<TFCFSandstoneBlockType, DecorationBlockRegistryObject>> SANDSTONE_DECORATIONS = Helpers.mapOfKeys(Colors.class, color -> color.hasLayered(), color ->
        Helpers.mapOfKeys(TFCFSandstoneBlockType.class, type -> new DecorationBlockRegistryObject(
            register(("sandstone/" + type.name() + "/slab/" + color.name()), () -> new SlabBlock(type.properties(color).sound(SoundType.ANCIENT_DEBRIS)), DECORATIONS),
            register(("sandstone/" + type.name() + "/stairs/" + color.name()), () -> new StairBlock(() -> SANDSTONE.get(color).get(type).get().defaultBlockState(), type.properties(color).sound(SoundType.ANCIENT_DEBRIS)), DECORATIONS),
            register(("sandstone/" + type.name() + "/wall/" + color.name()), () -> new WallBlock(type.properties(color).sound(SoundType.ANCIENT_DEBRIS)), DECORATIONS)
        ))
    );

    public static final Map<Colors, RegistryObject<Block>> WEATHERED_TERRACOTTA = Helpers.mapOfKeys(Colors.class, type ->
        register(("sand/weathered_terracotta/" + type.name()), type::create, EARTH)
    );

    public static final Map<Colors, RegistryObject<Block>> SAND = sandMap(Colors.class);
    public static final Map<Colors, RegistryObject<Block>> SAND_LAYERS = sandLayerMap(Colors.class);

    public static final Map<TFCFGroundcoverBlockType, RegistryObject<Block>> GROUNDCOVER = Helpers.mapOfKeys(TFCFGroundcoverBlockType.class, type ->
        register(("groundcover/" + type.name()), () -> new TFCFGroundcoverBlock(type), type.createBlockItem())
    );

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
            register("deposit/" + ore.name() + "/" + rock.name(), () -> new TFCFOreDepositBlock(Block.Properties.of(Material.SAND, rock.color()).sound(SoundType.GRAVEL).strength(rock.category().hardness(2.0f)), rock, ore), TFCItemGroup.ORES) // Same hardness as gravel
        )
    );

    // Florae Ores

    public static final Map<Rock, Map<TFCFOre, RegistryObject<Block>>> ORES_TFC_ROCK = Helpers.mapOfKeys(Rock.class, rock ->
        Helpers.mapOfKeys(TFCFOre.class, ore -> !ore.isGraded(), ore ->
            register(("ore/" + ore.name() + "/" + rock.name()), () -> ore.create(rock), TFCItemGroup.ORES)
        )
    );
    public static final Map<Rock, Map<TFCFOre, Map<TFCFOre.Grade, RegistryObject<Block>>>> GRADED_ORES_TFC_ROCK = Helpers.mapOfKeys(Rock.class, rock ->
        Helpers.mapOfKeys(TFCFOre.class, TFCFOre::isGraded, ore ->
            Helpers.mapOfKeys(TFCFOre.Grade.class, grade ->
                register(("ore/" + grade.name() + "_" + ore.name() + "/" + rock.name()), () -> ore.create(rock), TFCItemGroup.ORES)
            )
        )
    );

    public static final Map<TFCFRock, Map<TFCFOre, RegistryObject<Block>>> ORES_TFCF_ROCK = Helpers.mapOfKeys(TFCFRock.class, rock ->
        Helpers.mapOfKeys(TFCFOre.class, ore -> !ore.isGraded(), ore ->
            register(("ore/" + ore.name() + "/" + rock.name()), () -> ore.create(rock), TFCItemGroup.ORES)
        )
    );
    public static final Map<TFCFRock, Map<TFCFOre, Map<TFCFOre.Grade, RegistryObject<Block>>>> GRADED_ORES_TFCF_ROCK = Helpers.mapOfKeys(TFCFRock.class, rock ->
        Helpers.mapOfKeys(TFCFOre.class, TFCFOre::isGraded, ore ->
            Helpers.mapOfKeys(TFCFOre.Grade.class, grade ->
                register(("ore/" + grade.name() + "_" + ore.name() + "/" + rock.name()), () -> ore.create(rock), TFCItemGroup.ORES)
            )
        )
    );

    public static final RegistryObject<Block> GLOWSTONE = register("crystal/glowstone", () -> new AmethystBlock(BlockBehaviour.Properties.of(Material.AMETHYST, MaterialColor.COLOR_YELLOW).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().lightLevel((state) -> {return 15;})), TFCItemGroup.ORES);
    public static final RegistryObject<Block> GLOWSTONE_BUDDING = register("crystal/budding/glowstone", () -> new BuddingAmethystBlock(BlockBehaviour.Properties.of(Material.AMETHYST, MaterialColor.COLOR_YELLOW).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().lightLevel((state) -> {return 15;})), TFCItemGroup.ORES);
    public static final RegistryObject<Block> GLOWSTONE_CLUSTER =register("crystal/cluster/glowstone", () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.of(Material.AMETHYST, MaterialColor.COLOR_YELLOW).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> {return 10;})), TFCItemGroup.ORES);
    public static final RegistryObject<Block> LARGE_GLOWSTONE_BUD =register("crystal/large_bud/glowstone", () -> new AmethystClusterBlock(5, 3, BlockBehaviour.Properties.of(Material.AMETHYST, MaterialColor.COLOR_YELLOW).noOcclusion().randomTicks().sound(SoundType.LARGE_AMETHYST_BUD).strength(1.5F).lightLevel((state) -> {return 9;})), TFCItemGroup.ORES);
    public static final RegistryObject<Block> MEDIUM_GLOWSTONE_BUD =register("crystal/medium_bud/glowstone", () -> new AmethystClusterBlock(4, 3, BlockBehaviour.Properties.of(Material.AMETHYST, MaterialColor.COLOR_YELLOW).noOcclusion().randomTicks().sound(SoundType.MEDIUM_AMETHYST_BUD).strength(1.5F).lightLevel((state) -> {return 7;})), TFCItemGroup.ORES);
    public static final RegistryObject<Block> SMALL_GLOWSTONE_BUD = register("crystal/small_bud/glowstone", () -> new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.of(Material.AMETHYST, MaterialColor.COLOR_YELLOW).noOcclusion().randomTicks().sound(SoundType.SMALL_AMETHYST_BUD).strength(1.5F).lightLevel((state) -> {return 6;})), TFCItemGroup.ORES);

    public static final Map<Gem, RegistryObject<Block>> CRYSTAL = Helpers.mapOfKeys(Gem.class, gem ->
        register("crystal/" + gem.name(), () -> new AmethystBlock(BlockBehaviour.Properties.of(Material.AMETHYST, TFCFHelpers.gemColor(gem)).strength(1.5F).sound(SoundType.AMETHYST).lightLevel((state) -> {return 5;}).requiresCorrectToolForDrops()), TFCItemGroup.ORES)
    );
    public static final Map<Gem, RegistryObject<Block>> BUDDING_CRYSTAL = Helpers.mapOfKeys(Gem.class, gem ->
        register("crystal/budding/" + gem.name(), () -> new BuddingAmethystBlock(BlockBehaviour.Properties.of(Material.AMETHYST, TFCFHelpers.gemColor(gem)).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).lightLevel((state) -> {return 5;}).requiresCorrectToolForDrops()), TFCItemGroup.ORES)
    );
    public static final Map<Gem, RegistryObject<Block>> CLUSTER_CRYSTAL = Helpers.mapOfKeys(Gem.class, gem ->
        register("crystal/cluster/" + gem.name(), () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.of(Material.AMETHYST, TFCFHelpers.gemColor(gem)).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> {return 5;})), TFCItemGroup.ORES)
    );
    public static final Map<Gem, RegistryObject<Block>> LARGE_BUD_CRYSTAL = Helpers.mapOfKeys(Gem.class, gem ->
        register("crystal/large_bud/" + gem.name(), () -> new AmethystClusterBlock(5, 3, BlockBehaviour.Properties.of(Material.AMETHYST, TFCFHelpers.gemColor(gem)).noOcclusion().randomTicks().sound(SoundType.LARGE_AMETHYST_BUD).strength(1.5F).lightLevel((state) -> {return 4;})), TFCItemGroup.ORES)
    );
    public static final Map<Gem, RegistryObject<Block>> MEDIUM_BUD_CRYSTAL = Helpers.mapOfKeys(Gem.class, gem ->
        register("crystal/medium_bud/" + gem.name(), () -> new AmethystClusterBlock(4, 3, BlockBehaviour.Properties.of(Material.AMETHYST, TFCFHelpers.gemColor(gem)).noOcclusion().randomTicks().sound(SoundType.MEDIUM_AMETHYST_BUD).strength(1.5F).lightLevel((state) -> {return 2;})), TFCItemGroup.ORES)
    );
    public static final Map<Gem, RegistryObject<Block>> SMALL_BUD_CRYSTAL = Helpers.mapOfKeys(Gem.class, gem ->
        register("crystal/small_bud/" + gem.name(), () -> new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.of(Material.AMETHYST, TFCFHelpers.gemColor(gem)).noOcclusion().randomTicks().sound(SoundType.SMALL_AMETHYST_BUD).strength(1.5F).lightLevel((state) -> {return 1;})), TFCItemGroup.ORES)
    );

    // Rock Stuff

    public static final Map<Rock, RegistryObject<Block>> ROCK_CHESTS_TFC = Helpers.mapOfKeys(Rock.class, rock ->
        register("rock/chest/" + rock.name(), () -> new TFCFChestBlock(ExtendedProperties.of(Material.STONE, rock.color()).sound(SoundType.NETHER_BRICKS).strength(8, 10).requiresCorrectToolForDrops().blockEntity(TFCFBlockEntities.CHEST).clientTicks(ChestBlockEntity::lidAnimateTick), rock.name().toLowerCase()), b -> new ChestBlockItem(b, new Item.Properties().tab(ROCK_STUFFS)))
    );
    public static final Map<TFCFRock, RegistryObject<Block>> ROCK_CHESTS_TFCF = Helpers.mapOfKeys(TFCFRock.class, rock ->
        register("rock/chest/" + rock.name(), () -> new TFCFChestBlock(ExtendedProperties.of(Material.STONE, rock.color()).sound(SoundType.NETHER_BRICKS).strength(8, 10).requiresCorrectToolForDrops().blockEntity(TFCFBlockEntities.CHEST).clientTicks(ChestBlockEntity::lidAnimateTick), rock.name().toLowerCase()), b -> new ChestBlockItem(b, new Item.Properties().tab(ROCK_STUFFS)))
    );
    public static final RegistryObject<Block> ROCK_CHEST = register("rock/chest/rock", () -> new TFCFChestBlock(ExtendedProperties.of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).sound(SoundType.NETHER_BRICKS).strength(8, 10).requiresCorrectToolForDrops().blockEntity(TFCFBlockEntities.CHEST).clientTicks(ChestBlockEntity::lidAnimateTick), "rock"), b -> new ChestBlockItem(b, new Item.Properties().tab(ROCK_STUFFS)));

    public static final Map<Rock, RegistryObject<Block>> ROCK_TRAPPED_CHESTS_TFC = Helpers.mapOfKeys(Rock.class, rock ->
        register("rock/trapped_chest/" + rock.name(), () -> new TFCFTrappedChestBlock(ExtendedProperties.of(Material.STONE, rock.color()).sound(SoundType.NETHER_BRICKS).strength(8, 10).requiresCorrectToolForDrops().blockEntity(TFCFBlockEntities.TRAPPED_CHEST).clientTicks(ChestBlockEntity::lidAnimateTick), rock.name().toLowerCase()), b -> new ChestBlockItem(b, new Item.Properties().tab(ROCK_STUFFS)))
    );
    public static final Map<TFCFRock, RegistryObject<Block>> ROCK_TRAPPED_CHESTS_TFCF = Helpers.mapOfKeys(TFCFRock.class, rock ->
        register("rock/trapped_chest/" + rock.name(), () -> new TFCFTrappedChestBlock(ExtendedProperties.of(Material.STONE, rock.color()).sound(SoundType.NETHER_BRICKS).strength(8, 10).requiresCorrectToolForDrops().blockEntity(TFCFBlockEntities.TRAPPED_CHEST).clientTicks(ChestBlockEntity::lidAnimateTick), rock.name().toLowerCase()), b -> new ChestBlockItem(b, new Item.Properties().tab(ROCK_STUFFS)))
    );
    public static final RegistryObject<Block> ROCK_TRAPPED_CHEST = register("rock/trapped_chest/rock", () -> new TFCFTrappedChestBlock(ExtendedProperties.of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).sound(SoundType.NETHER_BRICKS).strength(8, 10).requiresCorrectToolForDrops().blockEntity(TFCFBlockEntities.TRAPPED_CHEST).clientTicks(ChestBlockEntity::lidAnimateTick), "rock"), b -> new ChestBlockItem(b, new Item.Properties().tab(ROCK_STUFFS)));

    public static final Map<Rock, Map<TFCFRock.TFCFBlockType, RegistryObject<Block>>> ROCK_BLOCKS = rockTypeTFCMapper(Rock.class);
    public static final Map<TFCFRock, Map<Rock.BlockType, RegistryObject<Block>>> TFCF_ROCK_BLOCKS = rockMapper(TFCFRock.class);
    public static final Map<TFCFRock, Map<TFCFRock.TFCFBlockType, RegistryObject<Block>>> TFCF_ROCKTYPE_BLOCKS = rockTypeMapper(TFCFRock.class);
    public static final Map<DripstoneRock, Map<Rock.BlockType, RegistryObject<Block>>> DRIPSTONE_BLOCKS = dripstoneMapper(DripstoneRock.class);

    public static final Map<Rock, RegistryObject<Block>> TFC_COLUMN = Helpers.mapOfKeys(Rock.class, rock ->
        register("rock/column/" + rock.name(), () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops()), ROCK_STUFFS)
    );
    public static final Map<TFCFRock, RegistryObject<Block>> TFCF_COLUMN = Helpers.mapOfKeys(TFCFRock.class, rock ->
        register("rock/column/" + rock.name(), () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops()), ROCK_STUFFS)
    );

    public static final Map<Rock, RegistryObject<Block>> TFC_POLISHED_COLUMN = Helpers.mapOfKeys(Rock.class, rock ->
        register("rock/polished_column/" + rock.name(), () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops()), ROCK_STUFFS)
    );
    public static final Map<TFCFRock, RegistryObject<Block>> TFCF_POLISHED_COLUMN = Helpers.mapOfKeys(TFCFRock.class, rock ->
        register("rock/polished_column/" + rock.name(), () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops()), ROCK_STUFFS)
    );

    public static final Map<Rock, RegistryObject<Block>> TFC_FLAGSTONE = Helpers.mapOfKeys(Rock.class, rock ->
        register("rock/flagstone/" + rock.name(), () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops()), ROCK_STUFFS)
    );
    public static final Map<TFCFRock, RegistryObject<Block>> TFCF_FLAGSTONE = Helpers.mapOfKeys(TFCFRock.class, rock ->
        register("rock/flagstone/" + rock.name(), () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops()), ROCK_STUFFS)
    );

    public static final Map<Rock, DecorationBlockRegistryObject> TFC_FLAGSTONE_DECORATIONS = Helpers.mapOfKeys(Rock.class, rock -> new DecorationBlockRegistryObject(
        register(("rock/flagstone/slab/" + rock.name()), () -> new SlabBlock(BlockBehaviour.Properties.copy(TFC_FLAGSTONE.get(rock).get())), ROCK_STUFFS),
        register(("rock/flagstone/stairs/" + rock.name()), () -> new StairBlock(() -> TFC_FLAGSTONE.get(rock).get().defaultBlockState(), BlockBehaviour.Properties.copy(TFC_FLAGSTONE.get(rock).get())), ROCK_STUFFS),
        register(("rock/flagstone/wall/" + rock.name()), () -> new WallBlock(BlockBehaviour.Properties.copy(TFC_FLAGSTONE.get(rock).get())), ROCK_STUFFS)
    ));
    public static final Map<TFCFRock, DecorationBlockRegistryObject> TFCF_FLAGSTONE_DECORATIONS = Helpers.mapOfKeys(TFCFRock.class, rock -> new DecorationBlockRegistryObject(
        register(("rock/flagstone/slab/" + rock.name()), () -> new SlabBlock(BlockBehaviour.Properties.copy(TFCF_FLAGSTONE.get(rock).get())), ROCK_STUFFS),
        register(("rock/flagstone/stairs/" + rock.name()), () -> new StairBlock(() -> TFCF_FLAGSTONE.get(rock).get().defaultBlockState(), BlockBehaviour.Properties.copy(TFCF_FLAGSTONE.get(rock).get())), ROCK_STUFFS),
        register(("rock/flagstone/wall/" + rock.name()), () -> new WallBlock(BlockBehaviour.Properties.copy(TFCF_FLAGSTONE.get(rock).get())), ROCK_STUFFS)
    ));

    public static final Map<Rock, RegistryObject<Block>> TFC_SMOKE_BLOCK = Helpers.mapOfKeys(Rock.class, rock ->
        register("rock/smoke_block/" + rock.name(), () -> new SmokeBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.CALCITE).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops(), true), ROCK_STUFFS)
    );
    public static final Map<TFCFRock, RegistryObject<Block>> TFCF_SMOKE_BLOCK = Helpers.mapOfKeys(TFCFRock.class, rock ->
        register("rock/smoke_block/" + rock.name(), () -> new SmokeBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.CALCITE).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops(), true), ROCK_STUFFS)
    );

    public static final Map<TFCFRock, RegistryObject<Block>> ROCK_ANVILS = Helpers.mapOfKeys(TFCFRock.class, rock -> rock.category() == RockCategory.IGNEOUS_EXTRUSIVE || rock.category() == RockCategory.IGNEOUS_INTRUSIVE, rock ->
        register("rock/anvil/" + rock.name(), () -> new TFCFRockAnvilBlock(ExtendedProperties.of(Material.STONE, rock.color()).sound(SoundType.STONE).strength(2, 10).requiresCorrectToolForDrops().blockEntity(TFCFBlockEntities.ANVIL), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rock).get(Rock.BlockType.RAW)), ROCK_STUFFS)
    );

    public static final Map<TFCFRock, RegistryObject<Block>> MAGMA_BLOCKS = Helpers.mapOfKeys(TFCFRock.class, rock -> rock.category() == RockCategory.IGNEOUS_EXTRUSIVE || rock.category() == RockCategory.IGNEOUS_INTRUSIVE, rock ->
        register("rock/magma/" + rock.name(), () -> new TFCMagmaBlock(Properties.of(Material.STONE, rock.color()).requiresCorrectToolForDrops().lightLevel(s -> 6).randomTicks().strength(0.5F).isValidSpawn((state, level, pos, type) -> type.fireImmune()).hasPostProcess(TFCFBlocks::always)), ROCK_STUFFS)
    );

    public static final Map<Rock, RegistryObject<Block>> GEYSER_TFC = Helpers.mapOfKeys(Rock.class, rock -> 
        register("rock/geyser/" + rock.name(), () -> Geyser.create(ExtendedProperties.of(Material.STONE, rock.color()).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops().noOcclusion().randomTicks().dynamicShape().hasPostProcess(TFCFBlocks::always), () -> TFCFBlocks.GEYSER_TFC.get(rock).get(), true), ROCK_STUFFS)
    );
    public static final Map<TFCFRock, RegistryObject<Block>> GEYSER_TFCF = Helpers.mapOfKeys(TFCFRock.class, rock -> 
        register("rock/geyser/" + rock.name(), () -> Geyser.create(ExtendedProperties.of(Material.STONE, rock.color()).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops().noOcclusion().randomTicks().dynamicShape().hasPostProcess(TFCFBlocks::always), () -> TFCFBlocks.GEYSER_TFCF.get(rock).get(), true), ROCK_STUFFS)
    );

    public static final Map<TFCFRock, Map<Rock.BlockType, DecorationBlockRegistryObject>> TFCF_ROCKTYPE_DECORATIONS = RockTypeDecoTFCMapper(TFCFRock.class);
    public static final Map<Rock, Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject>> TFC_ROCK_DECORATIONS = RockDecoTFCMapper(Rock.class);
    public static final Map<TFCFRock, Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject>> TFCF_ROCK_DECORATIONS = RockDecoTFCFMapper(TFCFRock.class);

    public static final RegistryObject<Block> LOOSE_FLINT = register("rock/loose/flint", () -> new LooseFlintBlock(Block.Properties.of(TFCMaterials.NON_SOLID_STONE, MaterialColor.DEEPSLATE).strength(0.05f, 0.0f).sound(SoundType.STONE).noCollission()), ROCK_STUFFS);

    public static final RegistryObject<Block> MINERAL_SHEET = register("mineral/mineral_sheet", () -> new MineralSheetBlock(ExtendedProperties.of(Material.STONE, MaterialColor.TERRACOTTA_GRAY).strength(0.5F, 4F).sound(TFCSounds.CHARCOAL).noCollission().noOcclusion().blockEntity(TFCFBlockEntities.MINERAL_SHEET)));

    // Wood

    public static final RegistryObject<Block> CHARRED_TREE_LOG = register("wood/log/charred_tree", () -> new LogBlock(ExtendedProperties.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.TERRACOTTA_BLACK : MaterialColor.TERRACOTTA_BLACK).strength(8f).sound(TFCSounds.CHARCOAL).requiresCorrectToolForDrops().flammableLikeLogs(), TFCFBlocks.CHARRED_TREE_STRIPPED_LOG), WOOD);
    public static final RegistryObject<Block> CHARRED_TREE_STRIPPED_LOG = register("wood/stripped_log/charred_tree", () -> new LogBlock(ExtendedProperties.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.TERRACOTTA_BLACK : MaterialColor.TERRACOTTA_BLACK).strength(7.5f).sound(TFCSounds.CHARCOAL).requiresCorrectToolForDrops().flammableLikeLogs(), null), WOOD);
    public static final RegistryObject<Block> CHARRED_TREE_WOOD = register("wood/wood/charred_tree", () -> new LogBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.TERRACOTTA_BLACK).strength(8f).sound(TFCSounds.CHARCOAL).requiresCorrectToolForDrops().flammableLikeLogs(), TFCFBlocks.CHARRED_TREE_STRIPPED_WOOD), WOOD);
    public static final RegistryObject<Block> CHARRED_TREE_STRIPPED_WOOD = register("wood/stripped_wood/charred_tree", () -> new LogBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.TERRACOTTA_BLACK).strength(7.5f).sound(TFCSounds.CHARCOAL).requiresCorrectToolForDrops().flammableLikeLogs(), null), WOOD);
    public static final RegistryObject<Block> CHARRED_TREE_TWIG = register("wood/twig/charred_tree", () -> GroundcoverBlock.twig(ExtendedProperties.of(Material.GRASS, MaterialColor.TERRACOTTA_BLACK).strength(0.05F, 0.0F).sound(TFCSounds.CHARCOAL).noCollission().flammableLikeWool()), WOOD);

    public static final Map<TFCFWood, Map<Wood.BlockType, RegistryObject<Block>>> WOODS = woodMapper(TFCFWood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> WOODS_SEASONAL_LEAVES = seasonalLeavesMapper(TFCFWood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> WOODS_SEASONAL_LOGS = seasonalLogMapper(TFCFWood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> WOODS_SEASONAL_WOOD = seasonalWoodMapper(TFCFWood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> LEAVES_ONLY = leavesOnlyMapper(TFCFWood.class);
    public static final Map<Wood, RegistryObject<Block>> NORMAL_BOOKSHELF_TFC = normalBookshelfMapperTFC(Wood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> CHISELED_BOOKSHELF_TFCF = chiseledBookshelfMapperTFCF(TFCFWood.class);
    public static final Map<Wood, RegistryObject<Block>> LOG_WALL_TFC = woodWallMapperTFC(Wood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> LOG_WALL_TFCF = woodWallMapperTFCF(TFCFWood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> MANGROVE_ROOTS = mangroveRootsMapper(TFCFWood.class);
    public static final Map<TFCFWood, Map<SoilBlockType.Variant, RegistryObject<Block>>> TFC_MUDDY_MANGROVE_ROOTS = TFCmangroveRootsMuddyMapper(TFCFWood.class);
    public static final Map<TFCFWood, Map<TFCFSoil.TFCFVariant, RegistryObject<Block>>> TFCF_MUDDY_MANGROVE_ROOTS = TFCFmangroveRootsMuddyMapper(TFCFWood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> ROOT_SPIKES = woodSpikeMapper(TFCFWood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> JOSHUA_TRUNK = joshuaTrunkMapper(TFCFWood.class);
    public static final Map<TFCFWood, RegistryObject<Block>> JOSHUA_LEAVES = joshuaLeavesMapper(TFCFWood.class);

    public static final Map<TFCFPlant, RegistryObject<Block>> BAMBOO_LOGS = bambooLogMapper(TFCFPlant.class);
    public static final Map<TFCFPlant, RegistryObject<Block>> STRIPPED_BAMBOO_LOGS = bambooStrippedLogMapper(TFCFPlant.class);
    public static final Map<TFCFPlant, RegistryObject<Block>> BAMBOO_LEAVES = bambooLeavesMapper(TFCFPlant.class);;
    public static final Map<TFCFPlant, RegistryObject<Block>> BAMBOO_SAPLINGS = bambooSaplingMapper(TFCFPlant.class);
    public static final RegistryObject<Block> VANILLA_BAMBOO_LOGS = register("wood/log/bamboo", () -> new BambooLogBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(5.5f).requiresCorrectToolForDrops().flammableLikeLogs().sound(SoundType.BAMBOO).noOcclusion().dynamicShape().hasPostProcess(TFCFBlocks::always), TFCFBlocks.VANILLA_STRIPPED_BAMBOO_LOGS), WOOD);
    public static final RegistryObject<Block> VANILLA_STRIPPED_BAMBOO_LOGS = register("wood/stripped_log/bamboo", () -> new BambooLogBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(5.5f).requiresCorrectToolForDrops().flammableLikeLogs().sound(SoundType.BAMBOO).noOcclusion().dynamicShape().hasPostProcess(TFCFBlocks::always), null), WOOD);
    public static final RegistryObject<Block> BAMBOO_MOSAIC = register("wood/mosaic/bamboo", () -> new ExtendedBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(1.5f, 3.0F).flammableLikePlanks()), WOOD);
    public static final Map<TFCFPlant, RegistryObject<Block>> BAMBOO_BUNDLED_LOGS = bambooBundledLogMapper(TFCFPlant.class);

    // Flora

    public static final Map<TFCFPlant, RegistryObject<Block>> PLANTS = normalPlantMapper(TFCFPlant.class);
    public static final Map<TFCFPlant, RegistryObject<Block>> FRUITING_PLANTS = seasonalPlantMapper(TFCFPlant.class);

    public static final Map<TFCFPlant, RegistryObject<Block>> POTTED_PLANTS = Helpers.mapOfKeys(TFCFPlant.class, TFCFPlant::hasFlowerPot, plant ->
        register(("plant/potted/" + plant.name()), () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PLANTS.get(plant), Properties.of(Material.DECORATION, MaterialColor.TERRACOTTA_ORANGE).instabreak().noOcclusion()))
    );

    // Misc

    public static final RegistryObject<SlabBlock> FIRE_BRICKS_SLAB = register(("ceramic/fire_bricks/brick_slab"), () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_RED).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<StairBlock> FIRE_BRICKS_STAIRS = register(("ceramic/fire_bricks/brick_stairs"), () -> new StairBlock(() -> TFCBlocks.FIRE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_RED).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);
    public static final RegistryObject<WallBlock> FIRE_BRICKS_WALL = register(("ceramic/fire_bricks/brick_wall"), () -> new WallBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_RED).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS);

    public static final Map<Clay, RegistryObject<Block>> CLAY_BLOCKS = clayBlocksMap(Clay.class);
    public static final Map<Clay, RegistryObject<Block>> CLAY_BRICKS = clayBricksMap(Clay.class);
    public static final Map<Clay, DecorationBlockRegistryObject> CLAY_DECO_BLOCKS = clayDecoMap(Clay.class);
    public static final Map<Clay, RegistryObject<Block>> CLAY_LARGE_VESSELS= clayLargeVesselMap(Clay.class);

    public static final RegistryObject<Block> SPIDER_EGG = register("spider_cave/spider_egg", () -> new EggBlock(ExtendedProperties.of(Material.EGG, MaterialColor.COLOR_CYAN).strength(1.0F).instabreak().lightLevel((state) -> {return 5;}).sound(TFCFSounds.MUD).speedFactor(0.3f).hasPostProcess(TFCFBlocks::always)), DECORATIONS);
    public static final RegistryObject<Block> SPIDER_EGGS = register("spider_cave/spider_eggs", () -> new EggBlock(ExtendedProperties.of(Material.EGG, MaterialColor.TERRACOTTA_WHITE).strength(1.0F).instabreak().noOcclusion().sound(TFCFSounds.MUD).speedFactor(0.3f).hasPostProcess(TFCFBlocks::always)), DECORATIONS);
    public static final RegistryObject<Block> LARGE_SPIDER_EGG = register("spider_cave/spider_egg_large", () -> new EggBlock(ExtendedProperties.of(Material.EGG, MaterialColor.TERRACOTTA_LIGHT_GRAY).strength(1.0F).instabreak().noOcclusion().sound(TFCFSounds.MUD).speedFactor(0.3f).hasPostProcess(TFCFBlocks::always)), DECORATIONS);

    public static final RegistryObject<Block> WEBBED_GLOW_BLOCK = register("spider_cave/webbed_glow", () -> new WebbedBlock(ExtendedProperties.of(Material.WEB, MaterialColor.TERRACOTTA_LIGHT_GREEN).strength(1.0F).noOcclusion().lightLevel((state) -> {return 6;}).sound(TFCFSounds.MUD).speedFactor(0.5f).hasPostProcess(TFCFBlocks::always)), DECORATIONS);
    public static final RegistryObject<Block> WEBBED_TORCH_BLOCK = register("spider_cave/webbed_torch", () -> new WebbedBlock(ExtendedProperties.of(Material.WEB, MaterialColor.TERRACOTTA_YELLOW).strength(1.0F).noOcclusion().lightLevel((state) -> {return 9;}).sound(TFCFSounds.MUD).speedFactor(0.5f).hasPostProcess(TFCFBlocks::always)), DECORATIONS);
    public static final RegistryObject<Block> WEBBED_CHEST = register("spider_cave/webbed_chest", () -> new WebbedChestBlock(ExtendedProperties.of(Material.WEB, MaterialColor.TERRACOTTA_RED).blockEntity(TFCFBlockEntities.WEBBED_CHEST).strength(1.0F).noOcclusion().sound(SoundType.WOOD).speedFactor(1f).hasPostProcess(TFCFBlocks::always)), DECORATIONS);

    public static final RegistryObject<Block> CREEPING_WEBS = register("spider_cave/creeping_webs", () -> new CreepingSpiderWebBlock(ExtendedProperties.of(Material.WEB, MaterialColor.COLOR_LIGHT_GRAY).noCollission().strength(1.0F).noOcclusion().sound(SoundType.TWISTING_VINES).speedFactor(0.7f).hasPostProcess(TFCFBlocks::always)), DECORATIONS);
    public static final RegistryObject<Block> HANGING_SPIDER_WEB_SLENDER = register("spider_cave/hanging_spider_web_slender", () -> new BodyWebBlock(ExtendedProperties.of(Material.WEB, MaterialColor.COLOR_LIGHT_GRAY).sound(SoundType.TWISTING_VINES).noCollission().strength(3.0F).noOcclusion().randomTicks().dynamicShape().speedFactor(0.7f).hasPostProcess(TFCFBlocks::always)), DECORATIONS);
    public static final RegistryObject<Block> HANGING_SPIDER_WEB_THICK = register("spider_cave/hanging_spider_web_thick", () -> new BodyWebBlock(ExtendedProperties.of(Material.WEB, MaterialColor.COLOR_LIGHT_GRAY).sound(SoundType.TWISTING_VINES).noCollission().strength(4.0F).noOcclusion().randomTicks().dynamicShape().speedFactor(0.9f).hasPostProcess(TFCFBlocks::always)), DECORATIONS);

    public static final RegistryObject<Block> SILKMOTH_NEST = register("devices/silkmoth_nest", () -> new SilkmothNestBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.HANGING_ROOTS).flammableLikeLeaves().strength(1.0F).noOcclusion().randomTicks().dynamicShape().blockEntity(TFCFBlockEntities.SILKMOTH_NEST).serverTicks(SilkmothNestBlockEntity::serverTick).hasPostProcess(TFCFBlocks::always)), MISC);

    public static final RegistryObject<Block> OCHRE_FROGLIGHT = register("frog/ochre_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.DECORATION, MaterialColor.COLOR_YELLOW).instabreak().lightLevel(value -> 15).sound(TFCFSounds.FROGLIGHT)), DECORATIONS);
    public static final RegistryObject<Block> VERDANT_FROGLIGHT = register("frog/verdant_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.DECORATION, MaterialColor.COLOR_GREEN).instabreak().lightLevel(value -> 15).sound(TFCFSounds.FROGLIGHT)), DECORATIONS);
    public static final RegistryObject<Block> PEARLESCENT_FROGLIGHT = register("frog/pearlescent_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.DECORATION, MaterialColor.COLOR_PURPLE).instabreak().lightLevel(value -> 15).sound(TFCFSounds.FROGLIGHT)), DECORATIONS);
    public static final RegistryObject<Block> FROGSPAWN = register("frog/frogspawn", () -> new FrogspawnBlock(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS, MaterialColor.COLOR_GRAY).instabreak().noDrops().noOcclusion().noCollission().sound(TFCFSounds.FROGSPAWN)), entry -> new WaterLilyBlockItem(entry, new Item.Properties().tab(MISC)));

    // Rocky Soils

    public static final Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<Rock, RegistryObject<Block>>>> TFCROCKSOIL = TFCRockSoilMapper(TFCFRockSoil.class);
    public static final Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<Rock, RegistryObject<Block>>>> TFCFROCKSOIL = TFCFRockSoilMapper(TFCFRockSoil.class);
    public static final Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<TFCFRock, RegistryObject<Block>>>> TFCROCKSOIL2 = TFCRockSoil2Mapper(TFCFRockSoil.class);
    public static final Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<TFCFRock, RegistryObject<Block>>>> TFCFROCKSOIL2 = TFCFRockSoil2Mapper(TFCFRockSoil.class);

    public static final Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<Rock, DecorationBlockRegistryObject>>> TFCROCKSOILDECO = TFCRockSoilDecoMapper(TFCFRockSoil.class);
    public static final Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<Rock, DecorationBlockRegistryObject>>> TFCFROCKSOILDECO = TFCFRockSoilDecoMapper(TFCFRockSoil.class);
    public static final Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<TFCFRock, DecorationBlockRegistryObject>>> TFCROCKSOILDECO2 = TFCRockSoilDeco2Mapper(TFCFRockSoil.class);
    public static final Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<TFCFRock, DecorationBlockRegistryObject>>> TFCFROCKSOILDECO2 = TFCFRockSoilDeco2Mapper(TFCFRockSoil.class);

    // Fluids

    public static final Map<SimpleFluids, RegistryObject<LiquidBlock>> SIMPLE_FLUIDS = Helpers.mapOfKeys(SimpleFluids.class, fluid ->
        register("fluid/ore/" + fluid.getId(), () -> new LiquidBlock(TFCFFluids.SIMPLE_FLUIDS.get(fluid).source(), Properties.of(TFCMaterials.MOLTEN_METAL).noCollission().strength(100f).lightLevel(state -> 15).noDrops()))
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

    private static Map<Clay, RegistryObject<Block>> clayBlocksMap(Class<Clay> enumClass)
    {
        Map<Clay, RegistryObject<Block>> Map = new HashMap<>();

        for (Clay clay : Clay.values())
        {
            if (clay.hasRock()) continue;
            Map.put(clay, register(("ceramic/" + clay.getSerializedName()).toLowerCase(Locale.ROOT) + "/clay_block", () -> 
                new Block(Properties.of(Material.CLAY, clay.getMaterialColor()).strength(0.6F).sound(TFCFSounds.MUD)), DECORATIONS));
        }
        return Map;
    }

    private static Map<Clay, RegistryObject<Block>> clayBricksMap(Class<Clay> enumClass)
    {
        Map<Clay, RegistryObject<Block>> Map = new HashMap<>();

        for (Clay clay : Clay.values())
        {
            if (clay.hasRock()) continue;
            Map.put(clay, register(("ceramic/" + clay.getSerializedName() + "/bricks").toLowerCase(Locale.ROOT), () -> 
                new Block(Properties.of(Material.STONE, clay.getMaterialColor()).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS));
        }
        return Map;
    }

    private static Map<Clay, DecorationBlockRegistryObject> clayDecoMap(Class<Clay> enumClass)
    {
        Map<Clay, DecorationBlockRegistryObject> Map = new HashMap<>();

        for (Clay clay : Clay.values())
        {
            if (clay.hasRock()) continue;
            Map.put(clay, new DecorationBlockRegistryObject(
                register(("ceramic/" + clay.getSerializedName() + "/brick_slab").toLowerCase(Locale.ROOT), () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, clay.getMaterialColor()).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS),
                register(("ceramic/" + clay.getSerializedName() + "/brick_stairs").toLowerCase(Locale.ROOT), () -> new StairBlock(() -> CLAY_BRICKS.get(clay).get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, clay.getMaterialColor()).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS),
                register(("ceramic/" + clay.getSerializedName() + "/brick_wall").toLowerCase(Locale.ROOT), () -> new WallBlock(BlockBehaviour.Properties.of(Material.STONE, clay.getMaterialColor()).requiresCorrectToolForDrops().strength(2.0F, 6.0F)), DECORATIONS)
            ));
        }
        return Map;
    }

    private static Map<Clay, RegistryObject<Block>> clayLargeVesselMap(Class<Clay> enumClass)
    {
        Map<Clay, RegistryObject<Block>> Map = new HashMap<>();

        for (Clay clay : Clay.values())
        {
            if (clay.hasRock()) continue;
            Map.put(clay, register(("ceramic/" + clay.getSerializedName()).toLowerCase(Locale.ROOT) + "/fired/large_vessel", () -> 
                new LargeVesselBlock(ExtendedProperties.of(Properties.of(Material.CLAY, clay.getMaterialColor()).strength(2.5F).noOcclusion()).blockEntity(TFCFBlockEntities.LARGE_VESSEL)), MISC));
        }
        return Map;
    }

    private static Map<Colors, RegistryObject<Block>> sandMap(Class<Colors> enumClass)
    {
        Map<Colors, RegistryObject<Block>> Map = new HashMap<>();

        for (Colors type : Colors.values())
        {
            if (!type.hasSandNew())
                continue;

            Map.put(type, register(("sand/sand/" + type.name()), () -> new TFCSandBlock(type.getDustColor(), BlockBehaviour.Properties.of(Material.SAND, type.getMaterialColor()).strength(0.5F).sound(SoundType.SAND)), EARTH));
        }
        return Map;
    }

    private static Map<Colors, RegistryObject<Block>> sandLayerMap(Class<Colors> enumClass)
    {
        Map<Colors, RegistryObject<Block>> Map = new HashMap<>();

        for (Colors type : Colors.values())
        {
            if (type.hasSandNew())
            {
                Map.put(type, register(("sand/sand_layer/" + type.name()), () -> new SandLayerBlock(type.getDustColor(), ExtendedProperties.of(Material.SAND, type.getMaterialColor()).strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SAND), SAND.get(type)), EARTH));
            }
            else
            {
                Map.put(type, register(("sand/sand_layer/" + type.name()), () -> new SandLayerBlock(type.getDustColor(), ExtendedProperties.of(Material.SAND, type.getMaterialColor()).strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SAND), TFCBlocks.SAND.get(type.toSandTFC(false))), EARTH));
            }
        }
        return Map;
    }

    private static Map<TFCFSoil, Map<SoilBlockType.Variant, RegistryObject<Block>>> TFCSoilMap(Class<TFCFSoil> enumClass)
    {
        Map<TFCFSoil, Map<SoilBlockType.Variant, RegistryObject<Block>>> Map = new HashMap<>();

        for (TFCFSoil soil : enumClass.getEnumConstants())
        {
            if (soil.getTFCFactory() == null)
                continue;
            Map<SoilBlockType.Variant, RegistryObject<Block>> subMap = new HashMap<>();

            for (SoilBlockType.Variant variant : SoilBlockType.Variant.values())
            {
                subMap.put(variant, register(soil.name() + "/" + variant.name(), () -> soil.TFCCreate(variant), EARTH));
            }
            Map.put(soil, subMap);
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
                if (wood == TFCFWood.BAMBOO)
                {
                    if (type == BlockType.PLANKS)
                    {
                        subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new ExtendedBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(1.5f, 3.0F).flammableLikePlanks()), WOOD));
                    }
                    else if (type == BlockType.LOG)
                    {
                        subMap.put(type, register(("wood/" + type.name() + "_bundle/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new LogBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(5.5f).requiresCorrectToolForDrops().flammableLikeLogs(), WOODS.get(wood).get(BlockType.STRIPPED_LOG)), WOOD));
                    }
                    else if (type == BlockType.WOOD)
                    {
                        subMap.put(type, register(("wood/" + type.name() + "_bundle/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new LogBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(5.5f).requiresCorrectToolForDrops().flammableLikeLogs(), WOODS.get(wood).get(BlockType.STRIPPED_WOOD)), WOOD));
                    }
                    else if (type == BlockType.STRIPPED_WOOD)
                    {
                        subMap.put(type, register(("wood/" + type.name() + "_bundle/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new LogBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(5.5f).requiresCorrectToolForDrops().flammableLikeLogs(), null), WOOD));
                    }
                    else if (type == BlockType.STRIPPED_LOG)
                    {
                        subMap.put(type, register(("wood/" + type.name() + "_bundle/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new LogBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(5.5f).requiresCorrectToolForDrops().flammableLikeLogs(), null), WOOD));
                    }
                    else if (type == BlockType.SAPLING)
                    {
                        subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new VanillaBambooSaplingBlock(new BambooTreeGrower(wood), ExtendedProperties.of(Material.PLANT, wood.woodColor()).noCollission().randomTicks().strength(0).sound(SoundType.GRASS).flammableLikeLeaves().blockEntity(TFCFBlockEntities.TICK_COUNTER), wood.daysToGrow()), WOOD));
                    }
                    else if (type == BlockType.LEAVES)
                    {
                        subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            TFCFLeavesBlockExt.create(ExtendedProperties.of(Material.LEAVES, wood.woodColor()).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never).flammableLikeLeaves(), 7, null, null, WOODS.get(wood).get(Wood.BlockType.SAPLING)), WOOD));
                    }
                    else if (type == BlockType.TRAPPED_CHEST)
                    {
                        subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new TFCFTrappedChestBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.5F).flammableLikePlanks().blockEntity(TFCFBlockEntities.TRAPPED_CHEST).clientTicks(TFCFChestBlockEntity::lidAnimateTick), wood.getSerializedName()), type.createBlockItem(new ChestBlockItem.Properties().tab(WOOD))));
                    }
                    else if (type == BlockType.CHEST)
                    {
                        subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new TFCFChestBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.5F).flammableLikePlanks().blockEntity(TFCFBlockEntities.CHEST).clientTicks(TFCFChestBlockEntity::lidAnimateTick), wood.getSerializedName()), type.createBlockItem(new ChestBlockItem.Properties().tab(WOOD))));
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
                            new ExtendedBlock(ExtendedProperties.of(Block.Properties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.0F, 3.0F)).enchantmentPower(1).flammableLikePlanks()), WOOD));
                    }
                    else if (type == BlockType.BARREL)
                    {
                        subMap.put(type, register(("wood/barrel/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new TFCFBarrelBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.5f).flammableLikePlanks().blockEntity(TFCFBlockEntities.BARREL).serverTicks(TFCFBarrelBlockEntity::serverTick)), WOOD));
                    }
                    else if (type == BlockType.LOOM)
                    {
                        subMap.put(type, register(("wood/loom/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new TFCFLoomBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.5F).noOcclusion().flammableLikePlanks().blockEntity(TFCFBlockEntities.LOOM).ticks(TFCFLoomBlockEntity::tick), TFCFHelpers.identifier("block/wood/planks/" + wood.getSerializedName())), WOOD));
                    }
                    else if (type == BlockType.SLUICE)
                    {
                        subMap.put(type, register(("wood/sluice/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new TFCFSluiceBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(3F).noOcclusion().flammableLikeLogs().blockEntity(TFCFBlockEntities.SLUICE).serverTicks(TFCFSluiceBlockEntity::serverTick)), WOOD));
                    }
                    else if (type == BlockType.TOOL_RACK)
                    {
                        subMap.put(type, register(("wood/tool_rack/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new TFCFToolRackBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.0F).noOcclusion().flammableLikePlanks().blockEntity(TFCFBlockEntities.TOOL_RACK)), WOOD));
                    }
                    else
                        subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), type.create(wood), type.createBlockItem(new Item.Properties().tab(WOOD))));
                }
                else if (type == BlockType.LOG && wood.isFruitTree() && wood.hasFruitingLog())
                {
                    continue;
                }
                else if (type == BlockType.WOOD && wood.isFruitTree() && wood.hasFruitingLog())
                {
                    continue;
                }
                else if (type == BlockType.LEAVES && (wood.isFruitTree() || wood.isMangrove()) && !wood.hasFruitingLog())
                {
                    continue;
                }
                else if (type == BlockType.SAPLING)
                {
                    if (wood.isMangrove())
                    {
                        subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new TFCFMangrovePropaguleBlock(wood, ExtendedProperties.of(Block.Properties.of(Material.PLANT, wood.woodColor()).noCollission().randomTicks().strength(0).sound(SoundType.GRASS)).flammableLikeLeaves().blockEntity(TFCFBlockEntities.TICK_COUNTER), wood.daysToGrow()), type.createBlockItem(new Item.Properties().tab(WOOD))));
                    }
                    else
                    {
                        subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                            new TFCFSaplingBlock(wood, wood.tree(), ExtendedProperties.of(Material.PLANT, wood.woodColor()).noCollission().randomTicks().strength(0).sound(SoundType.GRASS).flammableLikeLeaves().blockEntity(TFCFBlockEntities.TICK_COUNTER), wood.daysToGrow()), type.createBlockItem(new Item.Properties().tab(WOOD))));
                    }
                }
                else if (type == BlockType.LEAVES && !(wood.isFruitTree() || wood.isMangrove()))
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        TFCFLeavesBlockExt.create(ExtendedProperties.of(Material.LEAVES, wood.woodColor()).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never).flammableLikeLeaves(), 7, WOODS.get(wood).get(Wood.BlockType.FALLEN_LEAVES), WOODS.get(wood).get(Wood.BlockType.TWIG), WOODS.get(wood).get(Wood.BlockType.SAPLING)), WOOD));
                }
                else if (type == BlockType.TRAPPED_CHEST)
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFTrappedChestBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.5F).flammableLikePlanks().blockEntity(TFCFBlockEntities.TRAPPED_CHEST).clientTicks(TFCFChestBlockEntity::lidAnimateTick), wood.getSerializedName()), type.createBlockItem(new ChestBlockItem.Properties().tab(WOOD))));
                }
                else if (type == BlockType.CHEST)
                {
                    subMap.put(type, register(("wood/" + type.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFChestBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.5F).flammableLikePlanks().blockEntity(TFCFBlockEntities.CHEST).clientTicks(TFCFChestBlockEntity::lidAnimateTick), wood.getSerializedName()), type.createBlockItem(new ChestBlockItem.Properties().tab(WOOD))));
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
                else if (type == BlockType.BARREL)
                {
                    subMap.put(type, register(("wood/barrel/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFBarrelBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.5f).flammableLikePlanks().blockEntity(TFCFBlockEntities.BARREL).serverTicks(TFCFBarrelBlockEntity::serverTick)), type.createBlockItem(new TFCFBarrelBlockItem.Properties().tab(WOOD))));
                }
                else if (type == BlockType.LOOM)
                {
                    subMap.put(type, register(("wood/loom/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFLoomBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.5F).noOcclusion().flammableLikePlanks().blockEntity(TFCFBlockEntities.LOOM).ticks(TFCFLoomBlockEntity::tick), TFCFHelpers.identifier("block/wood/planks/" + wood.getSerializedName())), WOOD));
                }
                else if (type == BlockType.SLUICE)
                {
                    subMap.put(type, register(("wood/sluice/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFSluiceBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(3F).noOcclusion().flammableLikeLogs().blockEntity(TFCFBlockEntities.SLUICE).serverTicks(TFCFSluiceBlockEntity::serverTick)), WOOD));
                }
                else if (type == BlockType.TOOL_RACK)
                {
                    subMap.put(type, register(("wood/tool_rack/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        new TFCFToolRackBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.0F).noOcclusion().flammableLikePlanks().blockEntity(TFCFBlockEntities.TOOL_RACK)), WOOD));
                }
                else if (!(wood == TFCFWood.BAMBOO && (type == BlockType.PLANKS || type == BlockType.LOG || type == BlockType.WOOD || type == BlockType.STRIPPED_WOOD || type == BlockType.STRIPPED_LOG || type == BlockType.SAPLING)))
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
                new ExtendedBlock(ExtendedProperties.of(Block.Properties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(2.0F, 3.0F)).enchantmentPower(1).flammableLikePlanks()), WOOD));
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Block>> leavesOnlyMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (!wood.hasLeavesOnly())
                continue;

            BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory = BlockItem::new;
            Function<Block, BlockItem> blockItem = block -> blockItemFactory.apply(block, new Item.Properties().tab(WOOD));

            Map.put(wood, register(("wood/leaves/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                TFCFLeavesBlock.create(ExtendedProperties.of(Block.Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never)).randomTicks().hasPostProcess(TFCFBlocks::always).flammableLikeLeaves(), 
                wood.getProductItem(), wood.getStages(), wood.maxDecayDistance(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood), TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(BlockType.FALLEN_LEAVES), TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(BlockType.TWIG), TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(BlockType.SAPLING)), blockItem));
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Block>> seasonalLeavesMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (wood.hasLeavesOnly())
                continue;

            BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory = BlockItem::new;
            Function<Block, BlockItem> blockItem = block -> blockItemFactory.apply(block, new Item.Properties().tab(WOOD));

            if ((wood.isFruitTree() || wood.isMangrove()) && !wood.hasFruitingLog())
            {
                if (wood.isFruitTree())
                {
                    Map.put(wood, register(("wood/leaves/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        TFCFLeavesBlock.create(ExtendedProperties.of(Block.Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never)).randomTicks().hasPostProcess(TFCFBlocks::always).flammableLikeLeaves(), 
                        wood.getProductItem(), wood.getStages(), wood.maxDecayDistance(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood), WOODS.get(wood).get(BlockType.FALLEN_LEAVES), WOODS.get(wood).get(BlockType.TWIG), WOODS.get(wood).get(BlockType.SAPLING)), blockItem));
                }
                else if (wood.isMangrove())
                {
                    Map.put(wood, register(("wood/leaves/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                        TFCFMangroveLeavesBlock.create(ExtendedProperties.of(Block.Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never)).randomTicks().hasPostProcess(TFCFBlocks::always).flammableLikeLeaves(), wood,
                        wood.getProductItem(), wood.getStages(), wood.maxDecayDistance(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood), WOODS.get(wood).get(BlockType.FALLEN_LEAVES), WOODS.get(wood).get(BlockType.TWIG), WOODS.get(wood).get(BlockType.SAPLING)), blockItem));
                }
            }
            else continue;
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Block>> seasonalLogMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (wood.hasLeavesOnly())
                continue;

            BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory = BlockItem::new;
            Function<Block, BlockItem> blockItem = block -> blockItemFactory.apply(block, new Item.Properties().tab(WOOD));

            if (wood.isFruitTree() && wood.hasFruitingLog())
            {
                Map.put(wood, register(("wood/log/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                    new TFCFFruitingLogBlock(ExtendedProperties.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? wood.woodColor() : wood.barkColor()).strength(8f).sound(SoundType.WOOD).strength(8f).requiresCorrectToolForDrops().flammableLikeLogs().hasPostProcess(TFCFBlocks::always).randomTicks(), wood.getBlock(Wood.BlockType.STRIPPED_LOG), 
                    wood.getProductItem(), wood.getStages(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood)), blockItem));
            }
            else continue;
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Block>> seasonalWoodMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (wood.hasLeavesOnly())
                continue;

            BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory = BlockItem::new;
            Function<Block, BlockItem> blockItem = block -> blockItemFactory.apply(block, new Item.Properties().tab(WOOD));

            if (wood.isFruitTree() && wood.hasFruitingLog())
            {
                Map.put(wood, register(("wood/wood/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
                    new TFCFFruitingLogBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD).strength(8f).requiresCorrectToolForDrops().flammableLikeLogs().hasPostProcess(TFCFBlocks::always).randomTicks(), wood.getBlock(Wood.BlockType.STRIPPED_WOOD), 
                    wood.getProductItem(), wood.getStages(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood)), blockItem));
            }
            else continue;
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Block>> woodSpikeMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory = BlockItem::new;
            Function<Block, BlockItem> blockItem = block -> blockItemFactory.apply(block, new Item.Properties().tab(WOOD));

            if (wood.isMangrove() || wood == TFCFWood.BALD_CYPRESS || wood == TFCFWood.RED_CYPRESS)
            {
                Map.put(wood, register(("wood/spike/" + wood.getSerializedName()), () -> TFCFPointedDripstoneBlock.create(ExtendedProperties.of(Material.WOOD).sound(SoundType.ROOTS).strength(4F).requiresCorrectToolForDrops().noOcclusion().randomTicks().dynamicShape().flammableLikeLogs().hasPostProcess(TFCFBlocks::always), () -> TFCFBlocks.ROOT_SPIKES.get(wood).get()), blockItem));
            }
            else continue;
        }
        return Map;
    }

    private static Map<Wood, RegistryObject<Block>> woodWallMapperTFC(Class<Wood> enumClass)
    {
        Map<Wood, RegistryObject<Block>> Map = new HashMap<>();
        for (Wood wood : enumClass.getEnumConstants())
        {
            Map.put(wood, register("wood/log_wall/" + wood.name(), () -> new TFCFWallBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).strength(8f).sound(SoundType.WOOD).requiresCorrectToolForDrops().flammableLikeLogs(), 
                BlockBehaviour.Properties.of(Material.WOOD, wood.woodColor()).strength(8f).sound(SoundType.WOOD).requiresCorrectToolForDrops()), TFCItemGroup.WOOD));
            Map.put(wood, register("wood/wood_wall/" + wood.name(), () -> new TFCFWallBlock(ExtendedProperties.of(Material.WOOD, wood.barkColor()).strength(8f).sound(SoundType.WOOD).requiresCorrectToolForDrops().flammableLikeLogs(), 
                BlockBehaviour.Properties.of(Material.WOOD, wood.woodColor()).strength(8f).sound(SoundType.WOOD).requiresCorrectToolForDrops()), TFCItemGroup.WOOD));
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Block>> woodWallMapperTFCF(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (wood.hasLeavesOnly()) continue;

            Map.put(wood, register("wood/log_wall/" + wood.name(), () -> new TFCFWallBlock(ExtendedProperties.of(Material.WOOD, wood.woodColor()).strength(8f).sound(SoundType.WOOD).requiresCorrectToolForDrops().flammableLikeLogs(), 
                BlockBehaviour.Properties.of(Material.WOOD, wood.woodColor()).strength(8f).sound(SoundType.WOOD).requiresCorrectToolForDrops()), TFCItemGroup.WOOD));
            Map.put(wood, register("wood/wood_wall/" + wood.name(), () -> new TFCFWallBlock(ExtendedProperties.of(Material.WOOD, wood.barkColor()).strength(8f).sound(SoundType.WOOD).requiresCorrectToolForDrops().flammableLikeLogs(), 
                BlockBehaviour.Properties.of(Material.WOOD, wood.woodColor()).strength(8f).sound(SoundType.WOOD).requiresCorrectToolForDrops()), TFCItemGroup.WOOD));
        }
        return Map;
    }

    private static Map<TFCFWood, RegistryObject<Block>> mangroveRootsMapper(Class<TFCFWood> enumClass)
    {
        Map<TFCFWood, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFWood wood : enumClass.getEnumConstants())
        {
            if (wood.isMangrove())
            {
                BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory = BlockItem::new;
                Function<Block, BlockItem> blockItem = block -> blockItemFactory.apply(block, new Item.Properties().tab(WOOD));

                Map.put(wood, register(("wood/roots/" + wood.getSerializedName()).toLowerCase(Locale.ROOT), () -> 
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
                    TFCFJoshuaLeavesBlock.create(wood, ExtendedProperties.of(Block.Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never).hasPostProcess(TFCFBlocks::always)).randomTicks().flammableLikeLeaves(), 
                    wood.getProductItem(), wood.getStages(), TFCFClimateRanges.LARGE_FRUIT_TREES.get(wood)), blockItem));
                    
            }
            else continue;
        }
        return Map;
    }

    private static Map<TFCFPlant, RegistryObject<Block>> bambooLogMapper(Class<TFCFPlant> enumClass)
    {
        Map<TFCFPlant, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFPlant plant : TFCFPlant.values())
        {
            if (!(plant == TFCFPlant.BLUE_BAMBOO || plant == TFCFPlant.DRAGON_BAMBOO || plant == TFCFPlant.GOLDEN_BAMBOO || plant == TFCFPlant.RED_BAMBOO))
                continue;

            Map.put(plant, register(("wood/log/" + plant.name()).toLowerCase(Locale.ROOT), () -> new BambooLogBlock(ExtendedProperties.of(Material.WOOD).strength(8f).sound(SoundType.WOOD).strength(5.5f).requiresCorrectToolForDrops().flammableLikeLogs().sound(SoundType.BAMBOO).noOcclusion().dynamicShape().hasPostProcess(TFCFBlocks::always), TFCFBlocks.STRIPPED_BAMBOO_LOGS.get(plant)), plant.createBlockItem(new Item.Properties().tab(WOOD))));
        }
        return Map;
    }

    private static Map<TFCFPlant, RegistryObject<Block>> bambooLeavesMapper(Class<TFCFPlant> enumClass)
    {
        Map<TFCFPlant, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFPlant plant : TFCFPlant.values())
        {
            if (!(plant == TFCFPlant.BLUE_BAMBOO || plant == TFCFPlant.DRAGON_BAMBOO || plant == TFCFPlant.GOLDEN_BAMBOO || plant == TFCFPlant.RED_BAMBOO))
                continue;

            Map.put(plant, register(("wood/leaves/" + plant.name()).toLowerCase(Locale.ROOT), () -> TFCLeavesBlock.create(ExtendedProperties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCFBlocks::never).flammableLikeLeaves(), 7, null, null), plant.createBlockItem(new Item.Properties().tab(WOOD))));
        }
        return Map;
    }

    private static Map<TFCFPlant, RegistryObject<Block>> bambooStrippedLogMapper(Class<TFCFPlant> enumClass)
    {
        Map<TFCFPlant, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFPlant plant : TFCFPlant.values())
        {
            if (!(plant == TFCFPlant.BLUE_BAMBOO || plant == TFCFPlant.DRAGON_BAMBOO || plant == TFCFPlant.GOLDEN_BAMBOO || plant == TFCFPlant.RED_BAMBOO))
                continue;

            Map.put(plant, register(("wood/stripped_log/" + plant.name()).toLowerCase(Locale.ROOT), () -> new BambooLogBlock(ExtendedProperties.of(Material.WOOD).strength(8f).sound(SoundType.WOOD).strength(5.5f).requiresCorrectToolForDrops().flammableLikeLogs().sound(SoundType.BAMBOO).noOcclusion().dynamicShape().hasPostProcess(TFCFBlocks::always), null), plant.createBlockItem(new Item.Properties().tab(WOOD))));
        }
        return Map;
    }

    private static Map<TFCFPlant, RegistryObject<Block>> bambooSaplingMapper(Class<TFCFPlant> enumClass)
    {
        Map<TFCFPlant, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFPlant plant : TFCFPlant.values())
        {
            if (!(plant == TFCFPlant.BLUE_BAMBOO || plant == TFCFPlant.DRAGON_BAMBOO || plant == TFCFPlant.GOLDEN_BAMBOO || plant == TFCFPlant.RED_BAMBOO))
                continue;

            Map.put(plant, register(("wood/sapling/" + plant.name()).toLowerCase(Locale.ROOT), () -> new TFCFBambooSaplingBlock(new TFCFBambooTreeGrower(plant), ExtendedProperties.of(Material.PLANT).noCollission().randomTicks().strength(0).sound(SoundType.GRASS).flammableLikeLeaves().blockEntity(TFCFBlockEntities.TICK_COUNTER), TFCFWood.BAMBOO.daysToGrow()), plant.createBlockItem(new Item.Properties().tab(WOOD))));
        }
        return Map;
    }

    private static Map<TFCFPlant, RegistryObject<Block>> bambooBundledLogMapper(Class<TFCFPlant> enumClass)
    {
        Map<TFCFPlant, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFPlant plant : TFCFPlant.values())
        {
            if (!(plant == TFCFPlant.BLUE_BAMBOO || plant == TFCFPlant.DRAGON_BAMBOO || plant == TFCFPlant.GOLDEN_BAMBOO || plant == TFCFPlant.RED_BAMBOO))
                continue;

            Map.put(plant, register(("wood/log_bundle/" + plant.name()).toLowerCase(Locale.ROOT), () -> 
                new LogBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(5.5f).requiresCorrectToolForDrops().flammableLikeLogs(), WOODS.get(TFCFWood.BAMBOO).get(BlockType.STRIPPED_LOG)), WOOD));

            Map.put(plant, register(("wood/wood_bundle/" + plant.name()).toLowerCase(Locale.ROOT), () -> 
                new LogBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(5.5f).requiresCorrectToolForDrops().flammableLikeLogs(), WOODS.get(TFCFWood.BAMBOO).get(BlockType.STRIPPED_WOOD)), WOOD));
        }
        return Map;
    }

    /*private static Map<Wood.BlockType, RegistryObject<Block>> bambooWoodMapper(Class<Wood.BlockType> enumClass)
    {
        Map<Wood.BlockType, RegistryObject<Block>> Map = new HashMap<>();
        for (Wood.BlockType type : Wood.BlockType.values())
        {
            if (type == BlockType.LEAVES)
            {
                continue;
            }
            else if (type == BlockType.LOG)
            {
                Map.put(type, register(("wood/" + type.name() + "_bundle/bamboo").toLowerCase(Locale.ROOT), () -> 
                    new LogBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(5.5f).requiresCorrectToolForDrops().flammableLikeLogs(), BAMBOO_BLOCKS.get(BlockType.STRIPPED_LOG)), WOOD));
            }
            else if (type == BlockType.STRIPPED_LOG)
            {
                Map.put(type, register(("wood/" + type.name() + "_bundle/bamboo").toLowerCase(Locale.ROOT), () -> 
                    new LogBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(5.5f).requiresCorrectToolForDrops().flammableLikeLogs(), null), WOOD));
            }
            else if (type == BlockType.SAPLING)
            {
                Map.put(type, register(("wood/mosaic/bamboo").toLowerCase(Locale.ROOT), () -> 
                    new ExtendedBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(1.5f, 3.0F).flammableLikePlanks()), WOOD));
            }
            else if (type == BlockType.WOOD)
            {
                Map.put(type, register(("wood/" + type.name() + "/bamboo").toLowerCase(Locale.ROOT), () -> 
                    new LogBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(5.5f).requiresCorrectToolForDrops().flammableLikeLogs(), BAMBOO_BLOCKS.get(BlockType.STRIPPED_WOOD)), WOOD));
            }
            else if (type == BlockType.STRIPPED_WOOD)
            {
                Map.put(type, register(("wood/" + type.name() + "/bamboo").toLowerCase(Locale.ROOT), () -> 
                    new LogBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(5.5f).requiresCorrectToolForDrops().flammableLikeLogs(), null), WOOD));
            }
            else if (type == BlockType.TRAPPED_CHEST)
            {
                Map.put(type, register(("wood/" + type.name() + "/bamboo").toLowerCase(Locale.ROOT), () -> 
                    new TFCFTrappedChestBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(2.5F).flammableLikePlanks().blockEntity(TFCFBlockEntities.TRAPPED_CHEST).clientTicks(ChestBlockEntity::lidAnimateTick), "bamboo"), type.createBlockItem(new Item.Properties().tab(WOOD))));
            }
            else if (type == BlockType.CHEST)
            {
                Map.put(type, register(("wood/" + type.name() + "/bamboo").toLowerCase(Locale.ROOT), () -> 
                    new TFCFChestBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(2.5F).flammableLikePlanks().blockEntity(TFCFBlockEntities.CHEST).clientTicks(ChestBlockEntity::lidAnimateTick), "bamboo"), type.createBlockItem(new Item.Properties().tab(WOOD))));
            }
            else if (type == BlockType.SIGN)
            {
                Map.put(type, register(("wood/" + type.name() + "/bamboo").toLowerCase(Locale.ROOT), () -> 
                    new TFCStandingSignBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).noCollission().strength(1F).flammableLikePlanks().blockEntity(TFCFBlockEntities.SIGN)), type.createBlockItem(new Item.Properties().tab(WOOD))));
            }
            else if (type == BlockType.WALL_SIGN)
            {
                Map.put(type, register(("wood/" + type.name() + "/bamboo").toLowerCase(Locale.ROOT), () -> 
                    new TFCWallSignBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).noCollission().strength(1F).dropsLike(BAMBOO_BLOCKS.get(BlockType.SIGN)).flammableLikePlanks().blockEntity(TFCFBlockEntities.SIGN)), type.createBlockItem(new Item.Properties().tab(WOOD))));
            }
            else if (type == BlockType.LECTERN)
            {
                Map.put(type, register(("wood/" + type.name() + "/bamboo").toLowerCase(Locale.ROOT), () -> 
                    new TFCLecternBlock(ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).noCollission().strength(2.5F).flammableLikePlanks().blockEntity(TFCFBlockEntities.LECTERN)), type.createBlockItem(new Item.Properties().tab(WOOD))));
            }
            else if (type == BlockType.BOOKSHELF)
            {
                Map.put(type, register(("wood/" + type.name() + "/bamboo").toLowerCase(Locale.ROOT), () -> 
                    new ExtendedBlock(ExtendedProperties.of(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(2.0F, 3.0F)).flammableLikePlanks()), WOOD));

                Map.put(type, register(("wood/chiseled_" + type.name() + "/bamboo").toLowerCase(Locale.ROOT), () -> 
                    new ChiseledBookshelfBlock(ExtendedProperties.of(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(2.0F, 3.0F)).blockEntity(TFCFBlockEntities.CHISELED_BOOKSHELF).flammableLikePlanks()), WOOD));
            }
            else
                Map.put(type, register(("wood/" + type.name() + "/bamboo").toLowerCase(Locale.ROOT), type.create(TFCFWood.ALDER), type.createBlockItem(new Item.Properties().tab(WOOD))));
        }
        return Map;
    }*/

    private static Map<TFCFPlant, RegistryObject<Block>> normalPlantMapper(Class<TFCFPlant> enumClass)
    {
        Map<TFCFPlant, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFPlant plant : TFCFPlant.values())
        {
            if (plant.isSeasonalFruitPlant())
                continue;

            Map.put(plant, register(("plant/" + plant.name()).toLowerCase(Locale.ROOT), plant::create, plant.createBlockItem(new Item.Properties().tab(FLORA))));
        }
        return Map;
    }

    private static Map<TFCFPlant, RegistryObject<Block>> seasonalPlantMapper(Class<TFCFPlant> enumClass)
    {
        Map<TFCFPlant, RegistryObject<Block>> Map = new HashMap<>();
        for (TFCFPlant plant : TFCFPlant.values())
        {
            if (!plant.isSeasonalFruitPlant())
                continue;

            if (plant == TFCFPlant.PRICKLY_PEAR)
            {
                Map.put(plant, register(("plant/" + plant.name()).toLowerCase(Locale.ROOT), () -> 
                    ShortFruitingCactusBlock.create(plant, TFCFPlant.BlockType.fire(TFCFPlant.BlockType.solid().strength(0.25F).sound(SoundType.MOSS).hasPostProcess(TFCFBlocks::always)).pathType(BlockPathTypes.DAMAGE_CACTUS).randomTicks(), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant)), plant.createBlockItem(new Item.Properties().tab(FLORA))));
            }
            else if (plant == TFCFPlant.BARREL_CACTUS)
            {
                Map.put(plant, register(("plant/" + plant.name()).toLowerCase(Locale.ROOT), () -> 
                    TFCFFruitingCactusBlock.create(plant, TFCFPlant.BlockType.fire(TFCFPlant.BlockType.solid().strength(0.25F).sound(SoundType.MOSS).hasPostProcess(TFCFBlocks::always)).pathType(BlockPathTypes.DAMAGE_CACTUS).randomTicks(), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant)), plant.createBlockItem(new Item.Properties().tab(FLORA))));
            }
            else if (plant == TFCFPlant.SAGUARO_CACTUS)
            {
                Map.put(plant, register(("plant/" + plant.name()).toLowerCase(Locale.ROOT), () -> 
                    SaguaroCactusBlock.create(plant, TFCFPlant.BlockType.fire(TFCFPlant.BlockType.solid().strength(0.25F).sound(SoundType.MOSS).hasPostProcess(TFCFBlocks::always)).pathType(BlockPathTypes.DAMAGE_CACTUS).randomTicks(), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant)), plant.createBlockItem(new Item.Properties().tab(FLORA))));
            }
            else if ( plant == TFCFPlant.GLOW_VINES_PLANT)
            {
                Map.put(plant, register(("plant/" + plant.name()).toLowerCase(Locale.ROOT), () -> 
                    new FruitingBodyPlantBlock(TFCFPlant.BlockType.fire(TFCFPlant.BlockType.nonSolidTallPlant(plant).hasPostProcess(TFCFBlocks::always)).lightLevel(FruitingBodyPlantBlock.emission(14, true)).instabreak().sound(SoundType.CAVE_VINES).randomTicks(), plant.transformFruiting(), BodyPlantBlock.BODY_SHAPE, Direction.DOWN, plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant)), plant.createBlockItem(new Item.Properties().tab(FLORA))));
            }
            else if (plant == TFCFPlant.GLOW_VINES)
            {
                Map.put(plant, register(("plant/" + plant.name()).toLowerCase(Locale.ROOT), () -> 
                    new FruitingTopPlantBlock(TFCFPlant.BlockType.fire(TFCFPlant.BlockType.nonSolidTallPlant(plant).hasPostProcess(TFCFBlocks::always)).lightLevel(FruitingBodyPlantBlock.emission(14, true)).instabreak().sound(SoundType.CAVE_VINES).randomTicks(), plant.transformFruiting(), BodyPlantBlock.WEEPING_SHAPE, Direction.DOWN, plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant)), plant.createBlockItem(new Item.Properties().tab(FLORA))));
            }
            else if (plant == TFCFPlant.PITAHAYA)
            {
                Map.put(plant, register(("plant/" + plant.name()).toLowerCase(Locale.ROOT), () -> 
                    PitahayaBlock.create(plant, TFCFPlant.BlockType.fire(TFCFPlant.BlockType.solid().strength(0.25F).sound(SoundType.MOSS).hasPostProcess(TFCFBlocks::always)).pathType(BlockPathTypes.DAMAGE_CACTUS).randomTicks(), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant)), plant.createBlockItem(new Item.Properties().tab(FLORA))));
            }
            else if (plant == TFCFPlant.POKEWEED)
            {
                Map.put(plant, register(("plant/" + plant.name()).toLowerCase(Locale.ROOT), () -> 
                    FruitingTallPlantBlock.create(plant, TFCFPlant.BlockType.fire(TFCFPlant.BlockType.nonSolid(plant).hasPostProcess(TFCFBlocks::always)).randomTicks(), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant)), plant.createBlockItem(new Item.Properties().tab(FLORA))));
            }
            else continue;
        }
        return Map;
    }

    private static Map<TFCFRockSand, Map<Colors, Map<Rock, RegistryObject<Block>>>> TFCRockSandMapper(Class<TFCFRockSand> enumClass)
    {
        Map<TFCFRockSand, Map<Colors, Map<Rock, RegistryObject<Block>>>> Map = new HashMap<>();
        for (TFCFRockSand soilBlockType : enumClass.getEnumConstants())
        {
            if (soilBlockType.getTFCFactory() == null)
                continue;

            Map<Colors, Map<Rock, RegistryObject<Block>>> soilVariantMap = new HashMap<>();
            for (Colors sandColor : Colors.values())
            {
                Map<Rock, RegistryObject<Block>> rockMap = new HashMap<>();
                for (Rock rock : Rock.values())
                {
                    rockMap.put(rock, register(("sand/" + soilBlockType.name() + "/" + sandColor.name() + "/" + rock.name()).toLowerCase(Locale.ROOT), () -> soilBlockType.TFCCreate(sandColor, rock), EARTH));
                }
                soilVariantMap.put(sandColor, rockMap);
            }
            Map.put(soilBlockType, soilVariantMap);
        }
        return Map;
    }

    private static Map<TFCFRockSand, Map<Colors, Map<TFCFRock, RegistryObject<Block>>>> TFCFRockSandMapper(Class<TFCFRockSand> enumClass)
    {
        Map<TFCFRockSand, Map<Colors, Map<TFCFRock, RegistryObject<Block>>>> Map = new HashMap<>();
        for (TFCFRockSand soilBlockType : enumClass.getEnumConstants())
        {
            if (soilBlockType.getTFCFFactory() == null)
                continue;

            Map<Colors, Map<TFCFRock, RegistryObject<Block>>> soilVariantMap = new HashMap<>();
            for (Colors sandColor : Colors.values())
            {
                Map<TFCFRock, RegistryObject<Block>> rockMap = new HashMap<>();
                for (TFCFRock rock : TFCFRock.values())
                {
                    rockMap.put(rock, register(("sand/" + soilBlockType.name() + "/" + sandColor.name() + "/" + rock.name()).toLowerCase(Locale.ROOT), () -> soilBlockType.TFCFCreate(sandColor, rock), EARTH));
                }
                soilVariantMap.put(sandColor, rockMap);
            }
            Map.put(soilBlockType, soilVariantMap);
        }
        return Map;
    }

    private static Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<Rock, RegistryObject<Block>>>> TFCRockSoilMapper(Class<TFCFRockSoil> enumClass)
    {
        Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<Rock, RegistryObject<Block>>>> Map = new HashMap<>();
        for (TFCFRockSoil soilBlockType : enumClass.getEnumConstants())
        {
            if (soilBlockType.getTFCFFactory() == null || soilBlockType.getTFCFactory() == null)
                continue;

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
            if (soilBlockType.getTFCFFactory() == null || soilBlockType.getTFCFactory() == null)
                continue;

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

    private static Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<TFCFRock, RegistryObject<Block>>>> TFCRockSoil2Mapper(Class<TFCFRockSoil> enumClass)
    {
        Map<TFCFRockSoil, Map<SoilBlockType.Variant, Map<TFCFRock, RegistryObject<Block>>>> Map = new HashMap<>();
        for (TFCFRockSoil soilBlockType : enumClass.getEnumConstants())
        {
            if (soilBlockType.getTFCFFactory2() == null || soilBlockType.getTFCFactory2() == null)
                continue;

            Map<SoilBlockType.Variant, Map<TFCFRock, RegistryObject<Block>>> soilVariantMap = new HashMap<>();
            for (SoilBlockType.Variant soilVariant : SoilBlockType.Variant.values())
            {
                Map<TFCFRock, RegistryObject<Block>> rockMap = new HashMap<>();
                for (TFCFRock rock : TFCFRock.values())
                {
                    rockMap.put(rock, register((soilBlockType.name() + "/" + soilVariant.name() + "/" + rock.name()), () -> soilBlockType.TFCCreate2(soilVariant, rock), EARTH));
                }
                soilVariantMap.put(soilVariant, rockMap);
            }
            Map.put(soilBlockType, soilVariantMap);
        }
        return Map;
    }

    private static Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<TFCFRock, RegistryObject<Block>>>> TFCFRockSoil2Mapper(Class<TFCFRockSoil> enumClass)
    {
        Map<TFCFRockSoil, Map<TFCFSoil.TFCFVariant, Map<TFCFRock, RegistryObject<Block>>>> Map = new HashMap<>();
        for (TFCFRockSoil soilBlockType : enumClass.getEnumConstants())
        {
            if (soilBlockType.getTFCFFactory2() == null || soilBlockType.getTFCFactory2() == null)
                continue;

            Map<TFCFSoil.TFCFVariant, Map<TFCFRock, RegistryObject<Block>>> soilVariantMap = new HashMap<>();
            for (TFCFSoil.TFCFVariant soilVariant : TFCFSoil.TFCFVariant.values())
            {
                Map<TFCFRock, RegistryObject<Block>> rockMap = new HashMap<>();
                for (TFCFRock rock : TFCFRock.values())
                {
                    rockMap.put(rock, register((soilBlockType.name() + "/" + soilVariant.name() + "/" + rock.name()), () -> soilBlockType.TFCFCreate2(soilVariant, rock), EARTH));
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
            if (soilBlockType.getTFCFFactory() == null || soilBlockType.getTFCFactory() == null)
                continue;

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
            if (soilBlockType.getTFCFFactory() == null || soilBlockType.getTFCFactory() == null)
                continue;

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
    {
        Map<Rock, Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject>> Map = new HashMap<>();
        for (Rock rock : enumClass.getEnumConstants())
        {
            Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject> typeMap = new HashMap<>();
            for (TFCFRock.TFCFBlockType type : TFCFRock.TFCFBlockType.values())
            {
                if (type.getTFCFactory() != null && type.hasVariants())
                {
                    if (type == TFCFRock.TFCFBlockType.COBBLED_BRICKS || type == TFCFRock.TFCFBlockType.FLAGSTONE_BRICKS)
                    {
                        typeMap.put(type, new DecorationBlockRegistryObject(
                            register(("rock/" + type.name() + "/slab/" + rock.name()), () -> new MossGrowingSlabBlock(BlockBehaviour.Properties.copy(TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get()), TFCFBlocks.TFC_ROCK_DECORATIONS.get(rock).get(type.mossy()).slab()), ROCK_STUFFS),
                            register(("rock/" + type.name() + "/stairs/" + rock.name()), () -> new MossGrowingStairsBlock(() -> TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get().defaultBlockState(), BlockBehaviour.Properties.copy(TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get()), TFCFBlocks.TFC_ROCK_DECORATIONS.get(rock).get(type.mossy()).stair()), ROCK_STUFFS),
                            register(("rock/" + type.name() + "/wall/" + rock.name()), () -> new MossGrowingWallBlock(BlockBehaviour.Properties.copy(TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get()), TFCFBlocks.TFC_ROCK_DECORATIONS.get(rock).get(type.mossy()).wall()), ROCK_STUFFS)
                        ));
                    }
                    else if (type == TFCFRock.TFCFBlockType.MOSSY_COBBLED_BRICKS || type == TFCFRock.TFCFBlockType.MOSSY_FLAGSTONE_BRICKS)
                    {
                        typeMap.put(type, new DecorationBlockRegistryObject(
                            register(("rock/" + type.name() + "/slab/" + rock.name()), () -> new MossSpreadingSlabBlock(BlockBehaviour.Properties.copy(TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS),
                            register(("rock/" + type.name() + "/stairs/" + rock.name()), () -> new MossSpreadingStairBlock(() -> TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get().defaultBlockState(), BlockBehaviour.Properties.copy(TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS),
                            register(("rock/" + type.name() + "/wall/" + rock.name()), () -> new MossSpreadingWallBlock(BlockBehaviour.Properties.copy(TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS)
                        ));
                    }
                    else
                    {
                        typeMap.put(type, new DecorationBlockRegistryObject(
                            register(("rock/" + type.name() + "/slab/" + rock.name()), () -> new SlabBlock(BlockBehaviour.Properties.copy(TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS),
                            register(("rock/" + type.name() + "/stairs/" + rock.name()), () -> new StairBlock(() -> TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get().defaultBlockState(), BlockBehaviour.Properties.copy(TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS),
                            register(("rock/" + type.name() + "/wall/" + rock.name()), () -> new WallBlock(BlockBehaviour.Properties.copy(TFCFBlocks.ROCK_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS)
                        ));
                    }
                }
            }
            Map.put(rock, typeMap);
        }
        return Map;
    }

    private static Map<TFCFRock, Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject>> RockDecoTFCFMapper(Class<TFCFRock> enumClass)
    {
        Map<TFCFRock, Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject>> Map = new HashMap<>();
        for (TFCFRock rock : enumClass.getEnumConstants())
        {
            Map<TFCFRock.TFCFBlockType, DecorationBlockRegistryObject> typeMap = new HashMap<>();
            for (TFCFRock.TFCFBlockType type : TFCFRock.TFCFBlockType.values())
            {
                if (type.getTFCFFactory() != null && type.hasVariants())
                {
                    if (type == TFCFRock.TFCFBlockType.COBBLED_BRICKS || type == TFCFRock.TFCFBlockType.FLAGSTONE_BRICKS)
                    {
                        typeMap.put(type, new DecorationBlockRegistryObject(
                            register(("rock/" + type.name() + "/slab/" + rock.name()), () -> new MossGrowingSlabBlock(BlockBehaviour.Properties.copy(TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(type).get()), TFCFBlocks.TFCF_ROCK_DECORATIONS.get(rock).get(type.mossy()).slab()), ROCK_STUFFS),
                            register(("rock/" + type.name() + "/stairs/" + rock.name()), () -> new MossGrowingStairsBlock(() -> TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(type).get().defaultBlockState(), BlockBehaviour.Properties.copy(TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(type).get()), TFCFBlocks.TFCF_ROCK_DECORATIONS.get(rock).get(type.mossy()).stair()), ROCK_STUFFS),
                            register(("rock/" + type.name() + "/wall/" + rock.name()), () -> new MossGrowingWallBlock(BlockBehaviour.Properties.copy(TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(type).get()), TFCFBlocks.TFCF_ROCK_DECORATIONS.get(rock).get(type.mossy()).wall()), ROCK_STUFFS)
                        ));
                    }
                    else if (type == TFCFRock.TFCFBlockType.MOSSY_COBBLED_BRICKS || type == TFCFRock.TFCFBlockType.MOSSY_FLAGSTONE_BRICKS)
                    {
                        typeMap.put(type, new DecorationBlockRegistryObject(
                            register(("rock/" + type.name() + "/slab/" + rock.name()), () -> new MossSpreadingSlabBlock(BlockBehaviour.Properties.copy(TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS),
                            register(("rock/" + type.name() + "/stairs/" + rock.name()), () -> new MossSpreadingStairBlock(() -> TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(type).get().defaultBlockState(), BlockBehaviour.Properties.copy(TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS),
                            register(("rock/" + type.name() + "/wall/" + rock.name()), () -> new MossSpreadingWallBlock(BlockBehaviour.Properties.copy(TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS)
                        ));
                    }
                    else
                    {
                        typeMap.put(type, new DecorationBlockRegistryObject(
                            register(("rock/" + type.name() + "/slab/" + rock.name()), () -> new SlabBlock(BlockBehaviour.Properties.copy(TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS),
                            register(("rock/" + type.name() + "/stairs/" + rock.name()), () -> new StairBlock(() -> TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(type).get().defaultBlockState(), BlockBehaviour.Properties.copy(TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS),
                            register(("rock/" + type.name() + "/wall/" + rock.name()), () -> new WallBlock(BlockBehaviour.Properties.copy(TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(type).get())), ROCK_STUFFS)
                        ));
                    }
                }
            }
            Map.put(rock, typeMap);
        }
        return Map;
    }

    private static Map<DripstoneRock, Map<Rock.BlockType, RegistryObject<Block>>> dripstoneMapper(Class<DripstoneRock> enumClass)
    {
        Map<DripstoneRock, Map<Rock.BlockType, RegistryObject<Block>>> Map = new HashMap<>();
        for (DripstoneRock rock : enumClass.getEnumConstants())
        {
            Map<Rock.BlockType, RegistryObject<Block>> typeMap = new HashMap<>();
            for (Rock.BlockType type : Rock.BlockType.values())
            {
                if (type == Rock.BlockType.SPIKE)
                {
                    if (rock == DripstoneRock.GYPSUM)
                        typeMap.put(type, register(("rock/" + type.name() + "/" + rock.name()), () -> TFCFPointedDripstoneBlock.create(ExtendedProperties.of(Material.STONE).sound(SoundType.AMETHYST).strength(rock.category().hardness(2f), 7).requiresCorrectToolForDrops().noOcclusion().randomTicks().dynamicShape().hasPostProcess(TFCFBlocks::always).lightLevel((state) -> {return 6;}), () -> TFCFBlocks.DRIPSTONE_BLOCKS.get(rock).get(Rock.BlockType.SPIKE).get()), ROCK_STUFFS));
                    else
                        typeMap.put(type, register(("rock/" + type.name() + "/" + rock.name()), () -> TFCFPointedDripstoneBlock.create(ExtendedProperties.of(Material.STONE).sound(SoundType.POINTED_DRIPSTONE).strength(rock.category().hardness(4f), 10).requiresCorrectToolForDrops().noOcclusion().randomTicks().dynamicShape().hasPostProcess(TFCFBlocks::always), () -> TFCFBlocks.DRIPSTONE_BLOCKS.get(rock).get(Rock.BlockType.SPIKE).get()), ROCK_STUFFS));
                }
                else if (type == Rock.BlockType.RAW || type == Rock.BlockType.HARDENED || type == Rock.BlockType.LOOSE)
                {
                    if (type == Rock.BlockType.LOOSE && rock == DripstoneRock.GYPSUM)
                        continue;
                    else
                        typeMap.put(type, register(("rock/" + type.name() + "/" + rock.name()), () -> type.create(rock), ROCK_STUFFS));
                }
            }
            Map.put(rock, typeMap);
        }
        return Map;
    }

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
        /*String replaceName = name.replace("/", ".");
        String replace = name.replace("/", " ");
        String replace2 = replace.replace("_", " ");
        TFCFlorae.LOGGER.debug("\"block." + TFCFlorae.MOD_ID + "." + replaceName.toLowerCase() + "\": " + "\"" + StringUtils.capitalize(replace2) + "\"" + ",");*/
        /*for (Ore ore : Ore.values())
        {
            for (TFCFRock rock : TFCFRock.values())
            {
                if (ore.isGraded())
                {
                    for (Ore.Grade grade : Ore.Grade.values())
                    {
                        String replaceOre = ore.name().toLowerCase().replace("/", " ");
                        String replaceOre2 = replaceOre.replace("_", " ");
                        TFCFlorae.LOGGER.debug("\"block." + TFCFlorae.MOD_ID + ".ore." + grade.name().toLowerCase() + "_" + ore.name().toLowerCase() + "." + rock.name().toLowerCase() + ".prospected" + "\": " + "\"" + StringUtils.capitalize(replaceOre2) + "\"" + ",");
                    }
                }
                else
                {
                    String replaceOre = ore.name().toLowerCase().replace("/", " ");
                    String replaceOre2 = replaceOre.replace("_", " ");
                    TFCFlorae.LOGGER.debug("\"block." + TFCFlorae.MOD_ID + ".ore." + ore.name().toLowerCase() + "." + rock.name().toLowerCase() + ".prospected" + "\": " + "\"" + StringUtils.capitalize(replaceOre2) + "\"" + ",");
                }
            }
        }*/
        return RegistrationHelpers.registerBlock(TFCFBlocks.BLOCKS, TFCFItems.ITEMS, name, blockSupplier, blockItemFactory);
    }

    /*public static final Map<TFCFRock, Map<Ore, RegistryObject<Block>>> ORES = Helpers.mapOfKeys(TFCFRock.class, rock ->
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
    );*/

    /*private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        final String actualName = name.toLowerCase(Locale.ROOT);
        final RegistryObject<T> block = BLOCKS.register(actualName, blockSupplier);
        if (blockItemFactory != null)
        {
            TFCFItems.ITEMS.register(actualName, () -> blockItemFactory.apply(block.get()));
        }
        return block;
    }*/
}
