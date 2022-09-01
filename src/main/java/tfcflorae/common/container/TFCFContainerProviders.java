package tfcflorae.common.container;

import net.minecraft.world.MenuProvider;

import net.dries007.tfc.common.container.ItemStackContainerProvider;
import net.dries007.tfc.util.Helpers;

/**
 * {@link MenuProvider} for static screen / container pairs that are not attached to a block entity or other object which makes sense to implement this on.
 */
public class TFCFContainerProviders
{
    public static final ItemStackContainerProvider EARTHENWARE_CLAY_KNAPPING = new ItemStackContainerProvider(TFCFKnappingContainer::createEarthenwareClay, Helpers.translatable("tfcflorae.screen.earthenware_clay_knapping"));
    public static final ItemStackContainerProvider KAOLINITE_CLAY_KNAPPING = new ItemStackContainerProvider(TFCFKnappingContainer::createKaoliniteClay, Helpers.translatable("tfcflorae.screen.kaolinite_clay_knapping"));
    public static final ItemStackContainerProvider STONEWARE_CLAY_KNAPPING = new ItemStackContainerProvider(TFCFKnappingContainer::createStonewareClay, Helpers.translatable("tfcflorae.screen.stoneware_clay_knapping"));
}