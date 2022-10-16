package tfcflorae.common.blocks.wood;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.IForgeBlockExtension;

public class TFCFWallBlock extends WallBlock implements IForgeBlockExtension
{
    private final ExtendedProperties extendedProperties;

    public TFCFWallBlock(ExtendedProperties extendedProperties, BlockBehaviour.Properties properties)
    {
        super(properties);
        this.extendedProperties = extendedProperties;
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return extendedProperties;
    }
}
