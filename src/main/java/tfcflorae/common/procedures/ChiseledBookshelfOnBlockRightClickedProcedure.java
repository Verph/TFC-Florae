package tfcflorae.common.procedures;

import java.util.concurrent.atomic.AtomicReference;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ChiseledBookshelfOnBlockRightClickedProcedure
{
    public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid)
    {
        AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
        BlockEntity _ent = world.getBlockEntity(pos);
        if (_ent != null)
            _ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
            .ifPresent(capability -> _retval.set(capability.getStackInSlot(slotid).copy())); 
        return _retval.get();
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity)
    {
        if (entity == null)
        return; 
        double repeated = 0.0D;
        double books = 0.0D;
        ItemStack itemgiven = ItemStack.EMPTY;
        LivingEntity _livEnt = (LivingEntity)entity;
        itemgiven = ((entity instanceof LivingEntity) ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
        if (!world.isClientSide())
        {
            for (int index0 = 0; index0 < 6; index0++)
            {
                if ((new Object()
                {
                    public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid)
                    {
                        AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
                        BlockEntity _ent = world.getBlockEntity(pos);
                        if (_ent != null)
                            _ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
                            .ifPresent(capability -> _retval.set(capability.getStackInSlot(slotid).copy())); 
                        return _retval.get();
                    }
                }).getItemStack(world, new BlockPos(x, y, z), (int)repeated).getItem() != ItemStack.EMPTY.getItem())
                books++; 
                repeated++;
            } 
            if (itemgiven.getDescriptionId().contains("book") && !itemgiven.getDescriptionId().contains("bookshelf"))
            {
                if (books < 6.0D)
                {
                    BlockEntity _ent = world.getBlockEntity(new BlockPos(x, y, z));
                    if (_ent != null)
                    {
                        int _slotid = (int)books;
                        ItemStack _setstack = itemgiven;
                        _setstack.setCount(1);
                        _ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
                            if (capability instanceof IItemHandlerModifiable)
                                ((IItemHandlerModifiable)capability).setStackInSlot(_slotid, _setstack); 
                            });
                    }
                    LivingEntity livingEntity = (LivingEntity)entity;
                    ((entity instanceof LivingEntity) ? livingEntity.getMainHandItem() : ItemStack.EMPTY).shrink(1);
                    books++;
                    if (world instanceof Level)
                    {
                        Level _level = (Level)world;
                        if (!_level.isClientSide())
                        {
                            _level.playSound(null, new BlockPos(x, y, z), (SoundEvent)ForgeRegistries.SOUND_EVENTS
                            .getValue(new ResourceLocation("item.book.put")), SoundSource.PLAYERS, 1.0F, 1.0F);
                        }
                        else
                        {
                            _level.playLocalSound(x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.book.put")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                        } 
                    } 
                } 
            }
            else
            {
                books--;
                if (books >= 0.0D)
                {
                    if (entity instanceof Player)
                    {
                        Player _player = (Player)entity;
                        ItemStack _setstack = (new Object()
                        {
                            public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid)
                            {
                                AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
                                BlockEntity _ent = world.getBlockEntity(pos);
                                if (_ent != null)
                                    _ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> _retval.set(capability.getStackInSlot(slotid).copy())); 
                                return _retval.get();
                            }
                        }).getItemStack(world, new BlockPos(x, y, z), (int)books);
                        _setstack.setCount(1);
                        ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
                    } 
                    BlockEntity _ent = world.getBlockEntity(new BlockPos(x, y, z));
                    if (_ent != null)
                    {
                        int _slotid = (int)books;
                        ItemStack _setstack = ItemStack.EMPTY;
                        _setstack.setCount(1);
                        _ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
                            if (capability instanceof IItemHandlerModifiable)
                                ((IItemHandlerModifiable)capability).setStackInSlot(_slotid, _setstack); 
                            });
                    } 
                } 
            } 
            int _value = (int)books;
            BlockPos _pos = new BlockPos(x, y, z);
            BlockState _bs = world.getBlockState(_pos);
            Property property = _bs.getBlock().getStateDefinition().getProperty("books");
            if (property instanceof IntegerProperty)
            {
                IntegerProperty _integerProp = (IntegerProperty)property;
                if (_integerProp.getPossibleValues().contains(Integer.valueOf(_value)))
                    world.setBlock(_pos, (BlockState)_bs.setValue((Property)_integerProp, Integer.valueOf(_value)), 3); 
            } 
        } 
    }
}
