package tfcflorae.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blockentities.SluiceBlockEntity;

public class TFCFSluiceBlockEntity extends SluiceBlockEntity
{
    public TFCFSluiceBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    protected TFCFSluiceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType()
    {
        return TFCFBlockEntities.SLUICE.get();
    }
}
