package tfcflorae.common.container;

import java.util.function.Supplier;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blockentities.*;
import net.dries007.tfc.common.container.*;
import net.dries007.tfc.util.registry.RegistrationHelpers;

import tfcflorae.common.blockentities.TFCFBlockEntities;
import tfcflorae.common.blockentities.ceramics.*;
import tfcflorae.common.container.ceramics.*;
//import tfcflorae.common.container.ceramics.LargeVesselContainer;

import static tfcflorae.TFCFlorae.MOD_ID;

@SuppressWarnings("RedundantTypeArguments") // For some reason javac dies on the cases where these are explicitly specified, I have no idea why
public class TFCFContainerTypes
{
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);

    public static final RegistryObject<MenuType<LargeEarthenwareVesselContainer>> LARGE_EARTHENWARE_VESSEL = TFCFContainerTypes.<LargeEarthenwareVesselBlockEntity, LargeEarthenwareVesselContainer>registerBlock("large_earthenware_vessel", TFCFBlockEntities.LARGE_EARTHENWARE_VESSEL, LargeEarthenwareVesselContainer::create);
    public static final RegistryObject<MenuType<LargeKaoliniteVesselContainer>> LARGE_KAOLINITE_VESSEL = TFCFContainerTypes.<LargeKaoliniteVesselBlockEntity, LargeKaoliniteVesselContainer>registerBlock("large_kaolinite_vessel", TFCFBlockEntities.LARGE_KAOLINITE_VESSEL, LargeKaoliniteVesselContainer::create);
    public static final RegistryObject<MenuType<LargeStonewareVesselContainer>> LARGE_STONEWARE_VESSEL = TFCFContainerTypes.<LargeStonewareVesselBlockEntity, LargeStonewareVesselContainer>registerBlock("large_stoneware_vessel", TFCFBlockEntities.LARGE_STONEWARE_VESSEL, LargeStonewareVesselContainer::create);
    //public static final RegistryObject<MenuType<LargeVesselContainer>> LARGE_STONEWARE_VESSEL = TFCFContainerTypes.<LargeKaoliniteVesselBlockEntity, LargeVesselContainer>registerBlock("large_stoneware_vessel", TFCFBlockEntities.LARGE_STONEWARE_VESSEL, LargeVesselContainer::create);

    public static final RegistryObject<MenuType<TFCFKnappingContainer>> EARTHENWARE_CLAY_KNAPPING = registerItem("earthenware_clay_knapping", TFCFKnappingContainer::createEarthenwareClay);
    public static final RegistryObject<MenuType<TFCFKnappingContainer>> KAOLINITE_CLAY_KNAPPING = registerItem("kaolinite_clay_knapping", TFCFKnappingContainer::createKaoliniteClay);
    public static final RegistryObject<MenuType<TFCFKnappingContainer>> STONEWARE_CLAY_KNAPPING = registerItem("stoneware_clay_knapping", TFCFKnappingContainer::createStonewareClay);

    private static <T extends InventoryBlockEntity<?>, C extends BlockEntityContainer<T>> RegistryObject<MenuType<C>> registerBlock(String name, Supplier<BlockEntityType<T>> type, BlockEntityContainer.Factory<T, C> factory)
    {
        return RegistrationHelpers.registerBlockEntityContainer(CONTAINERS, name, type, factory);
    }

    private static <C extends ItemStackContainer> RegistryObject<MenuType<C>> registerItem(String name, ItemStackContainer.Factory<C> factory)
    {
        return RegistrationHelpers.registerItemStackContainer(CONTAINERS, name, factory);
    }

    private static <C extends AbstractContainerMenu> RegistryObject<MenuType<C>> register(String name, IContainerFactory<C> factory)
    {
        return RegistrationHelpers.registerContainer(CONTAINERS, name, factory);
    }
}