package tfcflorae.common.container;

import net.dries007.tfc.common.container.ItemStackContainerProvider;

public class TFCFContainerProviders
{
    public static final ItemStackContainerProvider EARTHENWARE_CLAY_KNAPPING = new ItemStackContainerProvider(TFCFContainerTypes::createEarthenwareClay);
    public static final ItemStackContainerProvider KAOLINITE_CLAY_KNAPPING = new ItemStackContainerProvider(TFCFContainerTypes::createKaoliniteClay);
    public static final ItemStackContainerProvider STONEWARE_CLAY_KNAPPING = new ItemStackContainerProvider(TFCFContainerTypes::createStonewareClay);
}