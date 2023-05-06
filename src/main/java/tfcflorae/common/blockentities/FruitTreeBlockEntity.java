package tfcflorae.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blockentities.BerryBushBlockEntity;

public class FruitTreeBlockEntity extends BerryBushBlockEntity
{
    public FruitTreeBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    protected FruitTreeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    /*@Override
    public BlockEntityType<?> getType()
    {
        return TFCFBlockEntities.BERRY_BUSH.get();
    }*/
}
