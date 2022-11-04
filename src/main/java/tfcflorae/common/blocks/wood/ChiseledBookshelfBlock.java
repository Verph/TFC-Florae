package tfcflorae.common.blocks.wood;

import java.util.Collections;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;

import net.dries007.tfc.common.blocks.EntityBlockExtension;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.IForgeBlockExtension;

import tfcflorae.common.blockentities.ChiseledBookshelfBlockEntity;
import tfcflorae.common.procedures.ChiseledBookshelfOnBlockRightClickedProcedure;

public class ChiseledBookshelfBlock extends Block implements IForgeBlockExtension, EntityBlockExtension
{
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final IntegerProperty BOOKS = IntegerProperty.create("books", 0, 6);
    private final ExtendedProperties properties;

    public ChiseledBookshelfBlock(ExtendedProperties properties)
    {
        super(properties.properties());
        this.properties = properties;

        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(new Property[] {FACING, BOOKS});
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return (defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite())).setValue(BOOKS, Integer.valueOf(0));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader world, BlockPos pos)
    {
        return state.getValue(BOOKS).intValue() * 0.33F;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state)
    {
        return PushReaction.BLOCK;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal; 
        return Collections.singletonList(new ItemStack((ItemLike)this, 1));
    }

    @Override
    public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit)
    {
        super.use(blockstate, world, pos, entity, hand, hit);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        double hitX = (hit.getLocation()).x;
        double hitY = (hit.getLocation()).y;
        double hitZ = (hit.getLocation()).z;
        Direction direction = hit.getDirection();
        ChiseledBookshelfOnBlockRightClickedProcedure.execute((LevelAccessor)world, x, y, z, (Entity)entity);
        return InteractionResult.SUCCESS;
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos)
    {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        MenuProvider menuProvider = (MenuProvider)tileEntity;
        return (tileEntity instanceof MenuProvider) ? menuProvider : null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ChiseledBookshelfBlockEntity(pos, state);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam)
    {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return (blockEntity == null) ? false : blockEntity.triggerEvent(eventID, eventParam);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (state.getBlock() != newState.getBlock())
        {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ChiseledBookshelfBlockEntity)
            {
                ChiseledBookshelfBlockEntity be = (ChiseledBookshelfBlockEntity) blockEntity;
                Containers.dropContents(world, pos, (Container)be);
                world.updateNeighbourForOutputSignal(pos, this);
            } 
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state)
    {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos)
    {
        BlockEntity tileentity = world.getBlockEntity(pos);
        if (tileentity instanceof ChiseledBookshelfBlockEntity)
        {
            ChiseledBookshelfBlockEntity be = (ChiseledBookshelfBlockEntity) tileentity;
            return AbstractContainerMenu.getRedstoneSignalFromContainer((Container)be);
        } 
        return 0;
    }
}