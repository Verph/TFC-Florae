package tfcflorae.common.container;

import net.dries007.tfc.common.container.ItemStackContainerProvider;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;

/**
 * {@link MenuProvider} for static screen / container pairs that are not attached to a block entity or other object which makes sense to implement this on.
 */
public class TFCFContainerProviders
{
    public static final ItemStackContainerProvider EARTHENWARE_CLAY_KNAPPING = new ItemStackContainerProvider(TFCFKnappingContainer::createEarthenwareClay, new TranslatableComponent("tfcflorae.screen.earthenware_clay_knapping"));
    public static final ItemStackContainerProvider KAOLINITE_CLAY_KNAPPING = new ItemStackContainerProvider(TFCFKnappingContainer::createKaoliniteClay, new TranslatableComponent("tfcflorae.screen.kaolinite_clay_knapping"));
    public static final ItemStackContainerProvider STONEWARE_CLAY_KNAPPING = new ItemStackContainerProvider(TFCFKnappingContainer::createStonewareClay, new TranslatableComponent("tfcflorae.screen.stoneware_clay_knapping"));
}