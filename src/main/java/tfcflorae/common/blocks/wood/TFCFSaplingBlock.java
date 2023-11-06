package tfcflorae.common.blocks.wood;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.ForgeEventFactory;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.wood.TFCSaplingBlock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.util.registry.RegistryWood;

import tfcflorae.common.blockentities.TFCFTickCounterBlockEntity;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.world.feature.tree.TFCFTreeGrower;

public class TFCFSaplingBlock extends TFCSaplingBlock implements IFluidLoggable
{
    public static final FluidProperty FLUID = TFCBlockStateProperties.ALL_WATER;

    public final RegistryWood wood;
    private final ExtendedProperties properties;
    private final int daysToGrow;

    public TFCFSaplingBlock(RegistryWood wood, TFCFTreeGrower tree, ExtendedProperties properties, int days)
    {
        super(tree, properties, days);
        this.properties = properties;
        this.daysToGrow = days;
        this.wood = wood;
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        final FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return super.defaultBlockState().setValue(getFluidProperty(), getFluidProperty().keyForOrEmpty(fluid.getType()));
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(getFluidProperty()));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        FluidHelpers.tickFluid(level, currentPos, state);
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (level.getMaxLocalRawBrightness(pos.above()) >= 9 && random.nextInt(7) == 0 && Climate.getTemperature(level, pos) >= 0)
        {
            if (!level.isAreaLoaded(pos, 1))
            {
                return;
            }
            if (level.getBlockEntity(pos) instanceof TFCFTickCounterBlockEntity counter)
            {
                if (counter.getTicksSinceUpdate() > ICalendar.TICKS_IN_DAY * getDaysToGrow() * TFCConfig.SERVER.globalSaplingGrowthModifier.get())
                {
                    this.advanceTree(level, pos, state.setValue(STAGE, 1), random);
                    if (ForgeEventFactory.saplingGrowTree(level, random, pos) && (wood instanceof TFCFWood woodTFCF && !woodTFCF.isPalmTree() || wood != Wood.PALM))
                    {
                        level.setBlock(pos, TFCFBlocks.WOODS.get(wood).get(Wood.BlockType.WOOD).get().defaultBlockState(), Block.UPDATE_ALL);
                    }
                }
            }
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        TFCFTickCounterBlockEntity.reset(level, pos);
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return super.mayPlaceOn(state, level, pos) || Helpers.isBlock(state.getBlock(), TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(state.getBlock(), TFCTags.Blocks.TREE_GROWS_ON) || Helpers.isBlock(state.getBlock(), BlockTags.SAND) || Helpers.isBlock(state.getBlock(), Tags.Blocks.GRAVEL);
    }

    @Override
    public int getDaysToGrow()
    {
        return daysToGrow;
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
}
