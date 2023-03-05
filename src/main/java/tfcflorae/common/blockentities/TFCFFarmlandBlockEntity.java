package tfcflorae.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blockentities.FarmlandBlockEntity;

public class TFCFFarmlandBlockEntity extends FarmlandBlockEntity
{
    public TFCFFarmlandBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    protected TFCFFarmlandBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType()
    {
        return TFCFBlockEntities.FARMLAND.get();
    }
}
