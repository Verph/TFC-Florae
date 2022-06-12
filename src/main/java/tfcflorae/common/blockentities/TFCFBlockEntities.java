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

import net.dries007.tfc.common.blockentities.LargeVesselBlockEntity;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistrationHelpers;

import tfcflorae.common.blockentities.ceramics.*;
import tfcflorae.common.blocks.TFCFBlocks;

import static tfcflorae.TFCFlorae.MOD_ID;

@SuppressWarnings("unused")
public final class TFCFBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID);

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
