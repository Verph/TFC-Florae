package tfcflorae.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tfcflorae.common.container.TFCFAnvilContainer;
import tfcflorae.common.container.TFCFAnvilPlanContainer;

import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blockentities.AnvilBlockEntity;

public class TFCFAnvilBlockEntity extends AnvilBlockEntity
{
    public TFCFAnvilBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    protected TFCFAnvilBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType()
    {
        return TFCFBlockEntities.ANVIL.get();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player)
    {
        return TFCFAnvilContainer.create(this, player.getInventory(), containerId);
    }

    @Nullable
    public AbstractContainerMenu createPlanContainer(int containerId, Inventory inventory, Player player)
    {
        return TFCFAnvilPlanContainer.create(this, player.getInventory(), containerId);
    }
}
