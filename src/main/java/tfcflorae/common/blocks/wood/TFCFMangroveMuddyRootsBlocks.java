package tfcflorae.common.blocks.wood;

import net.minecraft.world.level.block.Block;

import net.dries007.tfc.common.blocks.ExtendedProperties;

public class TFCFMangroveMuddyRootsBlocks extends Block
{
    public final TFCFWood wood;
    public final ExtendedProperties properties;

    public TFCFMangroveMuddyRootsBlocks(TFCFWood wood, ExtendedProperties properties)
    {
        super(properties.properties());
        this.wood = wood;
        this.properties = properties;
    }

    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }
}