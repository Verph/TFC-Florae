package tfcflorae.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blockentities.TFCChestBlockEntity;
import net.dries007.tfc.common.capabilities.size.ItemSizeManager;
import net.dries007.tfc.common.container.RestrictedChestContainer;
import net.dries007.tfc.common.container.TFCContainerTypes;
import net.dries007.tfc.config.TFCConfig;

public class TFCFChestBlockEntity extends TFCChestBlockEntity
{
    public static boolean isValid(ItemStack stack)
    {
        return ItemSizeManager.get(stack).getSize(stack).isEqualOrSmallerThan(TFCConfig.SERVER.chestMaximumItemSize.get());
    }

    public TFCFChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public TFCFChestBlockEntity(BlockPos pos, BlockState state)
    {
        super(TFCFBlockEntities.CHEST.get(), pos, state);
    }

    @Override
    public int getContainerSize()
    {
        return 18;
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory)
    {
        return new RestrictedChestContainer(TFCContainerTypes.CHEST_9x2.get(), id, inventory, this, 2);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) // should be isItemValid but no access here
    {
        return isValid(stack);
    }
}
