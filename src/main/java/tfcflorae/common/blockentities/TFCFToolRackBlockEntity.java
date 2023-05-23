package tfcflorae.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blockentities.ToolRackBlockEntity;

public class TFCFToolRackBlockEntity extends ToolRackBlockEntity
{
    public TFCFToolRackBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    protected TFCFToolRackBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType()
    {
        return TFCFBlockEntities.TOOL_RACK.get();
    }
}
