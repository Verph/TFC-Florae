package tfcflorae.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;

public class TFCFTickCounterBlockEntity extends TickCounterBlockEntity
{
    public TFCFTickCounterBlockEntity(BlockPos pos, BlockState state)
    {
        super(TFCFBlockEntities.TICK_COUNTER.get(), pos, state);
    }

    @Override
    public BlockEntityType<?> getType()
    {
        return TFCFBlockEntities.TICK_COUNTER.get();
    }
}
