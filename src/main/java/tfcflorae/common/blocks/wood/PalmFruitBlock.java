package tfcflorae.common.blocks.wood;

import java.util.Random;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.IForgeBlockExtension;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;

import tfcflorae.common.blocks.ICooldown;

public class PalmFruitBlock extends Block implements IForgeBlockExtension, IFluidLoggable, ICooldown
{
    public static final DirectionProperty DIRECTION = DirectionProperty.create("direction", Direction.Plane.HORIZONTAL);
    public static final IntegerProperty AGE = TFCBlockStateProperties.AGE_3;
    public static final FluidProperty FLUID = TFCBlockStateProperties.ALL_WATER;

    public final ExtendedProperties properties;
    @Nullable public final Supplier<? extends Block> trunk;
    @Nullable public final Supplier<? extends Item> productItem;

    public PalmFruitBlock(ExtendedProperties properties, @Nullable Supplier<? extends Block> trunk, Supplier<? extends Item> productItem)
    {
        super(properties.properties());
        this.properties = properties;
        this.trunk = trunk;
        this.productItem = productItem;
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(getFluidProperty(), AGE, DIRECTION);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Direction direction = context.getHorizontalDirection().getOpposite();
        final FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(getFluidProperty(), getFluidProperty().keyForOrEmpty(fluid.getType())).setValue(DIRECTION, direction).setValue(AGE, 0);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return level.getBlockState(pos.relative(state.getValue(DIRECTION).getOpposite())).getBlock().equals(trunk.get());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        FluidHelpers.tickFluid(level, pos, state);

        // Isn't directly connected to the palm head, but diagonally below from it, thus check the pos above the current.
        for (Direction around : Direction.Plane.HORIZONTAL)
        {
            if (level.getBlockState(pos.relative(direction.getOpposite()).above()).getBlock().equals(trunk.get()))
            {
                state.setValue(DIRECTION, around);
            }
        }
        return !state.canSurvive(level, pos) || !level.getBlockState(pos.relative(state.getValue(DIRECTION).getOpposite()).above()).getBlock().equals(trunk.get()) ? Blocks.AIR.defaultBlockState() : state;
    }

    @Override
    public OffsetType getOffsetType()
    {
        return OffsetType.XYZ;
    }

    @Override
    public FluidProperty getFluidProperty()
    {
        return FLUID;
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state)
    {
        return IFluidLoggable.super.getFluidState(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (state.getValue(AGE) == 3 && productItem != null)
        {
            level.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.PLAYERS, 1.0f, level.getRandom().nextFloat() + 0.7f + 0.3f);
            if (!level.isClientSide())
            {
                ItemHandlerHelper.giveItemToPlayer(player, getProductItem(level.random));
                level.destroyBlock(pos, false);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Nullable
    public ItemStack getProductItem(Random random)
    {
        return new ItemStack(productItem.get());
    }
}
