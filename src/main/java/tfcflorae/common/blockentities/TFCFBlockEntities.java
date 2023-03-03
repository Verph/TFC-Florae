package tfcflorae.common.blockentities;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blockentities.AnvilBlockEntity;
import net.dries007.tfc.common.blockentities.BarrelBlockEntity;
import net.dries007.tfc.common.blockentities.BerryBushBlockEntity;
import net.dries007.tfc.common.blockentities.FarmlandBlockEntity;
import net.dries007.tfc.common.blockentities.LargeVesselBlockEntity;
import net.dries007.tfc.common.blockentities.LoomBlockEntity;
import net.dries007.tfc.common.blockentities.SluiceBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blockentities.TFCChestBlockEntity;
import net.dries007.tfc.common.blockentities.TFCSignBlockEntity;
import net.dries007.tfc.common.blockentities.TFCTrappedChestBlockEntity;
import net.dries007.tfc.common.blockentities.ToolRackBlockEntity;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistrationHelpers;

import tfcflorae.TFCFlorae;
import tfcflorae.common.blockentities.ceramics.*;
import tfcflorae.common.blocks.*;
import tfcflorae.common.blocks.plant.*;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.common.blocks.wood.*;
import tfcflorae.mixin.accessor.BlockEntityTypeAccessor;

@SuppressWarnings("unused")
public class TFCFBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, TFCFlorae.MOD_ID);

    public static final RegistryObject<BlockEntityType<TFCFTickCounterBlockEntity>> TICK_COUNTER = register("tfcf_tick_counter", TFCFTickCounterBlockEntity::new, Stream.of(
            TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.SAPLING)),
            TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRYING_BRICKS).values(),
            TFCFBlocks.BAMBOO_SAPLINGS.values().stream()
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<BerryBushBlockEntity>> BERRY_BUSH = register("tfcf_berry_bush", FruitTreeBlockEntity::new, Stream.of(
            TFCFBlocks.WOODS_SEASONAL_LEAVES.values().stream(),
            TFCFBlocks.WOODS_SEASONAL_LOGS.values().stream(),
            TFCFBlocks.WOODS_SEASONAL_WOOD.values().stream(),
            TFCFBlocks.LEAVES_ONLY.values().stream(),
            TFCFBlocks.JOSHUA_LEAVES.values().stream(),
            TFCFBlocks.FRUITING_PLANTS.values().stream()
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<ChiseledBookshelfBlockEntity>> CHISELED_BOOKSHELF = register("chiseled_bookshelf", ChiseledBookshelfBlockEntity::new, Stream.of(
            TFCFBlocks.CHISELED_BOOKSHELF_TFCF.values().stream()
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<TFCFChestBlockEntity>> CHEST = register("tfcf_chest", TFCFChestBlockEntity::new, Stream.of(
            TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.CHEST)),
            TFCFBlocks.ROCK_CHEST,
            TFCFBlocks.ROCK_CHESTS_TFC.values().stream(),
            TFCFBlocks.ROCK_CHESTS_TFCF.values().stream()
            //TFCFBlocks.BAMBOO_BLOCKS.values().stream().flatMap(map -> Stream.of(Wood.BlockType.CHEST))
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<TFCFTrappedChestBlockEntity>> TRAPPED_CHEST = register("tfcf_trapped_chest", TFCFTrappedChestBlockEntity::new, Stream.of(
            TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.TRAPPED_CHEST)),
            TFCFBlocks.ROCK_TRAPPED_CHEST,
            TFCFBlocks.ROCK_TRAPPED_CHESTS_TFC.values().stream(),
            TFCFBlocks.ROCK_TRAPPED_CHESTS_TFCF.values().stream()
            //TFCFBlocks.BAMBOO_BLOCKS.values().stream().flatMap(map -> Stream.of(Wood.BlockType.TRAPPED_CHEST))
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<WebbedChestBlockEntity>> WEBBED_CHEST = register("webbed_chest", WebbedChestBlockEntity::new, Stream.of(
            TFCFBlocks.WEBBED_CHEST
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    /*public static final RegistryObject<BlockEntityType<BarrelBlockEntity>> BARREL = register("tfcf_barrel", BarrelBlockEntity::new, Stream.of(
            TFCFBlocks.WOODS.values().stream().flatMap(map -> Stream.of(Wood.BlockType.BARREL).map(map::get))
            //TFCFBlocks.BAMBOO_BLOCKS.values().stream().flatMap(map -> Stream.of(TFCFBlocks.BAMBOO_BLOCKS.get(Wood.BlockType.BARREL)))
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<LoomBlockEntity>> LOOM = register("tfcf_loom", LoomBlockEntity::new, Stream.of(
            TFCFBlocks.WOODS.values().stream().flatMap(map -> Stream.of(Wood.BlockType.LOOM).map(map::get))
            //TFCFBlocks.BAMBOO_BLOCKS.values().stream().flatMap(map -> Stream.of(TFCFBlocks.BAMBOO_BLOCKS.get(Wood.BlockType.LOOM)))
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<SluiceBlockEntity>> SLUICE = register("tfcf_sluice", SluiceBlockEntity::new, Stream.of(
            TFCFBlocks.WOODS.values().stream().flatMap(map -> Stream.of(Wood.BlockType.SLUICE).map(map::get))
            //TFCFBlocks.BAMBOO_BLOCKS.values().stream().flatMap(map -> Stream.of(TFCFBlocks.BAMBOO_BLOCKS.get(Wood.BlockType.SLUICE)))
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<TFCFSignBlockEntity>> SIGN = register("tfcf_sign", TFCFSignBlockEntity::new, Stream.of(
            TFCFBlocks.WOODS.values().stream().flatMap(map -> Stream.of(Wood.BlockType.SIGN, Wood.BlockType.WALL_SIGN).map(map::get))
            //TFCFBlocks.BAMBOO_BLOCKS.values().stream().flatMap(map -> Stream.of(TFCFBlocks.BAMBOO_BLOCKS.get(Wood.BlockType.SIGN), TFCFBlocks.BAMBOO_BLOCKS.get(Wood.BlockType.WALL_SIGN)))
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<LecternBlockEntity>> LECTERN = register("tfcf_lectern", TFCFLecternBlockEntity::new, Stream.of(
            TFCFBlocks.WOODS.values().stream().flatMap(map -> Stream.of(Wood.BlockType.LECTERN).map(map::get))
            //TFCFBlocks.BAMBOO_BLOCKS.values().stream().flatMap(map -> Stream.of(TFCFBlocks.BAMBOO_BLOCKS.get(Wood.BlockType.LECTERN)))
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );*/

    public static final RegistryObject<BlockEntityType<FarmlandBlockEntity>> FARMLAND = register("tfcf_farmland", FarmlandBlockEntity::new, TFCFBlocks.TFCFSOIL.get(TFCFSoil.FARMLAND).values().stream());
    public static final RegistryObject<BlockEntityType<BarrelBlockEntity>> BARREL = register("tfcf_barrel", BarrelBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.BARREL)));
    public static final RegistryObject<BlockEntityType<LoomBlockEntity>> LOOM = register("tfcf_loom", LoomBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.LOOM)));
    public static final RegistryObject<BlockEntityType<SluiceBlockEntity>> SLUICE = register("tfcf_sluice", SluiceBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.SLUICE)));
    public static final RegistryObject<BlockEntityType<TFCFSignBlockEntity>> SIGN = register("tfcf_sign", TFCFSignBlockEntity::new, TFCFBlocks.WOODS.values().stream().flatMap(map -> Stream.of(Wood.BlockType.SIGN, Wood.BlockType.WALL_SIGN).map(map::get)));
    public static final RegistryObject<BlockEntityType<LecternBlockEntity>> LECTERN = register("tfcf_lectern", TFCFLecternBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.LECTERN)));
    public static final RegistryObject<BlockEntityType<AnvilBlockEntity>> ANVIL = register("tfcf_anvil", AnvilBlockEntity::new, TFCFBlocks.ROCK_ANVILS.values().stream());

    public static final RegistryObject<BlockEntityType<LargeEarthenwareVesselBlockEntity>> LARGE_EARTHENWARE_VESSEL = register("large_earthenware_vessel", LargeEarthenwareVesselBlockEntity::new, Stream.of(TFCFBlocks.LARGE_EARTHENWARE_VESSEL).<Supplier<? extends Block>>flatMap(Helpers::flatten));
    public static final RegistryObject<BlockEntityType<LargeKaoliniteVesselBlockEntity>> LARGE_KAOLINITE_VESSEL = register("large_kaolinite_vessel", LargeKaoliniteVesselBlockEntity::new, Stream.of(TFCFBlocks.LARGE_KAOLINITE_VESSEL).<Supplier<? extends Block>>flatMap(Helpers::flatten));
    public static final RegistryObject<BlockEntityType<LargeStonewareVesselBlockEntity>> LARGE_STONEWARE_VESSEL = register("large_stoneware_vessel", LargeStonewareVesselBlockEntity::new, Stream.of(TFCFBlocks.LARGE_STONEWARE_VESSEL).<Supplier<? extends Block>>flatMap(Helpers::flatten));

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }
}