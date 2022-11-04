package tfcflorae.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TFCFLecternBlockEntity extends LecternBlockEntity
{
    public TFCFLecternBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType()
    {
        return TFCFBlockEntities.LECTERN.get();
    }
}
