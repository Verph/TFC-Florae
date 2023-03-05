package tfcflorae.common.blocks.devices;

import net.minecraft.core.Direction;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.devices.SluiceBlock;

public class TFCFSluiceBlock extends SluiceBlock
{
    public TFCFSluiceBlock(ExtendedProperties properties)
    {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(UPPER, true).setValue(FACING, Direction.NORTH));
    }
}
