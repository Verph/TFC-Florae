package tfcflorae.common.blockentities;

import java.util.function.Supplier;
import java.util.stream.Stream;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blockentities.FarmlandBlockEntity;
import net.dries007.tfc.common.blockentities.LoomBlockEntity;
import net.dries007.tfc.common.blockentities.SluiceBlockEntity;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;

import tfcflorae.TFCFlorae;
import tfcflorae.common.blockentities.ceramics.LargeVesselBlockEntity;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;
import tfcflorae.common.blocks.soil.TFCFSoil;

@SuppressWarnings("RedundantTypeArguments")
public class TFCFBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, TFCFlorae.MOD_ID);

    public static final RegistryObject<BlockEntityType<SandPileBlockEntity>> SAND_PILE = register("sand_pile", SandPileBlockEntity::new, Stream.of(
            //TFCFBlocks.SAND_LAYERS.values().stream(),
            //TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.values().stream().map(map -> map.get(TFCFRock.TFCFBlockType.GRAVEL_LAYER)),
            //TFCFBlocks.ROCK_BLOCKS.values().stream().map(map -> map.get(TFCFRock.TFCFBlockType.GRAVEL_LAYER))
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<TFCFTickCounterBlockEntity>> TICK_COUNTER = register("tfcf_tick_counter", TFCFTickCounterBlockEntity::new, Stream.of(
            TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.SAPLING)),
            TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRYING_BRICKS).values(),
            TFCFBlocks.BAMBOO_SAPLINGS.values().stream()
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    /*public static final RegistryObject<BlockEntityType<FruitTreeBlockEntity>> BERRY_BUSH = register("tfcf_berry_bush", FruitTreeBlockEntity::new, Stream.of(
            TFCFBlocks.WOODS_SEASONAL_LEAVES.values().stream(),
            TFCFBlocks.WOODS_SEASONAL_LOGS.values().stream(),
            TFCFBlocks.WOODS_SEASONAL_WOOD.values().stream(),
            TFCFBlocks.LEAVES_ONLY.values().stream(),
            TFCFBlocks.JOSHUA_LEAVES.values().stream(),
            TFCFBlocks.FRUITING_PLANTS.values().stream()
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );*/

    public static final RegistryObject<BlockEntityType<ChiseledBookshelfBlockEntity>> CHISELED_BOOKSHELF = register("chiseled_bookshelf", ChiseledBookshelfBlockEntity::new, Stream.of(
            TFCFBlocks.CHISELED_BOOKSHELF_TFCF.values().stream()
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<TFCFChestBlockEntity>> CHEST = register("tfcf_chest", TFCFChestBlockEntity::new, Stream.of(
            TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.CHEST)),
            TFCFBlocks.ROCK_CHEST,
            TFCFBlocks.ROCK_CHESTS_TFC.values().stream(),
            TFCFBlocks.ROCK_CHESTS_TFCF.values().stream()
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<TFCFTrappedChestBlockEntity>> TRAPPED_CHEST = register("tfcf_trapped_chest", TFCFTrappedChestBlockEntity::new, Stream.of(
            TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.TRAPPED_CHEST)),
            TFCFBlocks.ROCK_TRAPPED_CHEST,
            TFCFBlocks.ROCK_TRAPPED_CHESTS_TFC.values().stream(),
            TFCFBlocks.ROCK_TRAPPED_CHESTS_TFCF.values().stream()
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<WebbedChestBlockEntity>> WEBBED_CHEST = register("webbed_chest", WebbedChestBlockEntity::new, Stream.of(
            TFCFBlocks.WEBBED_CHEST
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<LargeVesselBlockEntity>> LARGE_VESSEL = register("large_vessel", LargeVesselBlockEntity::new, Stream.of(
            TFCFBlocks.CLAY_LARGE_VESSELS.values().stream()
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<SilkmothNestBlockEntity>> SILKMOTH_NEST = register("silkmoth_nest", SilkmothNestBlockEntity::new, Stream.of(
            TFCFBlocks.SILKMOTH_NEST
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<FarmlandBlockEntity>> FARMLAND = register("tfcf_farmland", TFCFFarmlandBlockEntity::new, TFCFBlocks.TFCFSOIL.get(TFCFSoil.FARMLAND).values().stream());
    public static final RegistryObject<BlockEntityType<TFCFBarrelBlockEntity>> BARREL = register("tfcf_barrel", TFCFBarrelBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.BARREL)));
    public static final RegistryObject<BlockEntityType<LoomBlockEntity>> LOOM = register("tfcf_loom", TFCFLoomBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.LOOM)));
    public static final RegistryObject<BlockEntityType<SluiceBlockEntity>> SLUICE = register("tfcf_sluice", TFCFSluiceBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.SLUICE)));
    public static final RegistryObject<BlockEntityType<TFCFSignBlockEntity>> SIGN = register("tfcf_sign", TFCFSignBlockEntity::new, TFCFBlocks.WOODS.values().stream().flatMap(map -> Stream.of(Wood.BlockType.SIGN, Wood.BlockType.WALL_SIGN).map(map::get)));
    public static final RegistryObject<BlockEntityType<LecternBlockEntity>> LECTERN = register("tfcf_lectern", TFCFLecternBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.LECTERN)));
    public static final RegistryObject<BlockEntityType<TFCFToolRackBlockEntity>> TOOL_RACK = register("tfcf_tool_rack", TFCFToolRackBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.TOOL_RACK)));
    public static final RegistryObject<BlockEntityType<TFCFAnvilBlockEntity>> ANVIL = register("tfcf_anvil", TFCFAnvilBlockEntity::new, TFCFBlocks.ROCK_ANVILS.values().stream());

    public static final RegistryObject<BlockEntityType<MineralSheetBlockEntity>> MINERAL_SHEET = register("mineral_sheet", MineralSheetBlockEntity::new, TFCFBlocks.MINERAL_SHEET);

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }
}