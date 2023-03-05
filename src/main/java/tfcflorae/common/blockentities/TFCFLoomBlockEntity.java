package tfcflorae.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blockentities.LoomBlockEntity;

public class TFCFLoomBlockEntity extends LoomBlockEntity
{
    public TFCFLoomBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    protected TFCFLoomBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType()
    {
        return TFCFBlockEntities.LOOM.get();
    }
}
