package tfcflorae.common.blockentities;

import java.util.stream.IntStream;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class ChiseledBookshelfBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer
{
    private NonNullList<ItemStack> stacks = NonNullList.withSize(6, ItemStack.EMPTY);

    private final LazyOptional<? extends IItemHandler>[] handlers = (LazyOptional<? extends IItemHandler>[])SidedInvWrapper.create(this, Direction.values());

    public ChiseledBookshelfBlockEntity(BlockPos position, BlockState state)
    {
        super((BlockEntityType)TFCFBlockEntities.CHISELED_BOOKSHELF.get(), position, state);
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        if (!tryLoadLootTable(compound))
            this.stacks = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY); 
        ContainerHelper.loadAllItems(compound, this.stacks);
    }

    @Override
    public void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);
        if (!trySaveLootTable(compound))
            ContainerHelper.saveAllItems(compound, this.stacks); 
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create((BlockEntity)this);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return saveWithFullMetadata();
    }

    @Override
    public int getContainerSize()
    {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.stacks)
        {
            if (!itemstack.isEmpty())
            return false; 
        } 
        return true;
    }

    @Override
    public Component getDefaultName()
    {
        return new TextComponent("tfcflorae.chiseled_bookshelf");
    }

    @Override
    public int getMaxStackSize()
    {
        return 1;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory)
    {
        return ChestMenu.threeRows(id, inventory);
    }

    @Override
    public Component getDisplayName()
    {
        return new TextComponent("Chiseled Bookshelf");
    }

    @Override
    protected NonNullList<ItemStack> getItems() 
    {
        return this.stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> stacks)
    {
        this.stacks = stacks;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack)
    {
        return true;
    }

    @Override
    public int[] getSlotsForFace(Direction side)
    {
        return IntStream.range(0, getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction)
    {
        return canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction)
    {
        return true;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing)
    {
        if (!this.remove && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return this.handlers[facing.ordinal()].cast(); 
        return super.getCapability(capability, facing);
    }

    @Override
    public void setRemoved()
    {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : this.handlers)
            handler.invalidate(); 
    }
}