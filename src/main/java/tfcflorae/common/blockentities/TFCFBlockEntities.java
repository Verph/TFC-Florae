package tfcflorae.common.blockentities;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blockentities.BarrelBlockEntity;
import net.dries007.tfc.common.blockentities.BerryBushBlockEntity;
import net.dries007.tfc.common.blockentities.LargeVesselBlockEntity;
import net.dries007.tfc.common.blockentities.LoomBlockEntity;
import net.dries007.tfc.common.blockentities.SluiceBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blockentities.TFCChestBlockEntity;
import net.dries007.tfc.common.blockentities.TFCSignBlockEntity;
import net.dries007.tfc.common.blockentities.TFCTrappedChestBlockEntity;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.blockentities.ToolRackBlockEntity;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistrationHelpers;

import tfcflorae.common.blockentities.ceramics.*;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.common.blocks.wood.TFCFLeavesBlock;
import tfcflorae.common.blocks.wood.TFCFWood;

import static tfcflorae.TFCFlorae.MOD_ID;

@SuppressWarnings("unused")
public final class TFCFBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID);

    public static final RegistryObject<BlockEntityType<TickCounterBlockEntity>> TICK_COUNTER = register("tick_counter", TickCounterBlockEntity::new, Stream.of(
            TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.SAPLING)),
            TFCFBlocks.TFCFSOIL.get(TFCFSoil.DRYING_BRICKS).values()
        ).<Supplier<? extends Block>>flatMap(Helpers::flatten)
    );

    public static final RegistryObject<BlockEntityType<FruitTreeBlockEntity>> LARGE_FRUIT_TREE = register("large_fruit_tree", FruitTreeBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.LEAVES)).filter(i -> i.get() instanceof TFCFLeavesBlock).<Supplier<? extends Block>>flatMap(Helpers::flatten));
    public static final RegistryObject<BlockEntityType<TFCChestBlockEntity>> CHEST = register("chest", TFCChestBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.CHEST)));
    public static final RegistryObject<BlockEntityType<TFCTrappedChestBlockEntity>> TRAPPED_CHEST = register("trapped_chest", TFCTrappedChestBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.TRAPPED_CHEST)));
    public static final RegistryObject<BlockEntityType<BarrelBlockEntity>> BARREL = register("barrel", BarrelBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.BARREL)));
    public static final RegistryObject<BlockEntityType<LoomBlockEntity>> LOOM = register("loom", LoomBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.LOOM)));
    public static final RegistryObject<BlockEntityType<SluiceBlockEntity>> SLUICE = register("sluice", SluiceBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.SLUICE)));
    public static final RegistryObject<BlockEntityType<ToolRackBlockEntity>> TOOL_RACK = register("tool_rack", ToolRackBlockEntity::new, TFCFBlocks.WOODS.values().stream().map(map -> map.get(Wood.BlockType.TOOL_RACK)));
    public static final RegistryObject<BlockEntityType<TFCSignBlockEntity>> SIGN = register("sign", TFCSignBlockEntity::new, TFCFBlocks.WOODS.values().stream().flatMap(map -> Stream.of(Wood.BlockType.SIGN, Wood.BlockType.WALL_SIGN).map(map::get)));
    
    public static final RegistryObject<BlockEntityType<LargeEarthenwareVesselBlockEntity>> LARGE_EARTHENWARE_VESSEL = register("large_earthenware_vessel", LargeEarthenwareVesselBlockEntity::new, Stream.of(TFCFBlocks.LARGE_EARTHENWARE_VESSEL, TFCFBlocks.GLAZED_LARGE_EARTHENWARE_VESSELS.values()).<Supplier<? extends Block>>flatMap(Helpers::flatten));
    public static final RegistryObject<BlockEntityType<LargeKaoliniteVesselBlockEntity>> LARGE_KAOLINITE_VESSEL = register("large_kaolinite_vessel", LargeKaoliniteVesselBlockEntity::new, Stream.of(TFCFBlocks.LARGE_KAOLINITE_VESSEL, TFCFBlocks.GLAZED_LARGE_KAOLINITE_VESSELS.values()).<Supplier<? extends Block>>flatMap(Helpers::flatten));
    public static final RegistryObject<BlockEntityType<LargeStonewareVesselBlockEntity>> LARGE_STONEWARE_VESSEL = register("large_stoneware_vessel", LargeStonewareVesselBlockEntity::new, Stream.of(TFCFBlocks.LARGE_STONEWARE_VESSEL, TFCFBlocks.GLAZED_LARGE_STONEWARE_VESSELS.values()).<Supplier<? extends Block>>flatMap(Helpers::flatten));

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }
}
