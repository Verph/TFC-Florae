package tfcflorae.common.blocks.plant;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.tags.BlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.IForgeBlockExtension;
import net.dries007.tfc.common.blocks.plant.PlantBlock;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.util.registry.RegistryPlant;

import tfcflorae.Config;
import tfcflorae.common.blocks.ICooldown;
import tfcflorae.util.TFCFHelpers;

public abstract class TFCFBodyPlantBlock extends GrowingPlantBodyBlock implements IForgeBlockExtension, ICooldown
{
    public static final VoxelShape BODY_SHAPE = box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    public static final VoxelShape THIN_BODY_SHAPE = box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    public static final VoxelShape WEEPING_SHAPE = box(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    public static final VoxelShape TWISTING_SHAPE = box(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);
    public static final VoxelShape TWISTING_THIN_SHAPE = box(5.0D, 0.0D, 5.0D, 11.0D, 12.0D, 11.0D);

    private final Supplier<? extends Block> headBlock;
    private final ExtendedProperties properties;

    public boolean canWalkThroughEffortlessly;
    public boolean isDead;
    public long cooldown;

    public static TFCFBodyPlantBlock create(RegistryPlant plant, ExtendedProperties properties, Supplier<? extends Block> headBlock, VoxelShape shape, Direction direction)
    {
        return new TFCFBodyPlantBlock(properties, headBlock, shape, direction)
        {
            @Override
            public RegistryPlant getPlant()
            {
                return plant;
            }
        };
    }

    protected TFCFBodyPlantBlock(ExtendedProperties properties, Supplier<? extends Block> headBlock, VoxelShape shape, Direction direction)
    {
        super(properties.properties(), direction, shape, true);
        this.headBlock = headBlock;
        this.properties = properties;
        this.cooldown = Long.MIN_VALUE;
        this.isDead = false;

        BlockState stateDefinition = getStateDefinition().any();
        IntegerProperty stageProperty = getPlant().getStageProperty();
        if (stageProperty != null)
        {
            stateDefinition = stateDefinition.setValue(stageProperty, 0);
        }
        registerDefaultState(stateDefinition);
        //registerDefaultState(stateDefinition.setValue(DEAD, Calendars.CLIENT.getCalendarMonthOfYear().getSeason() == Season.WINTER ? true : false));
    }

    /**
     * Gets the plant metadata for this block.
     *
     * The stage property is isolated and referenced via this as it is needed in the {@link Block} constructor - which builds the state container, and requires all property references to be computed in {@link Block#createBlockStateDefinition(StateDefinition.Builder)}.
     *
     * See the various {@link PlantBlock#create(RegistryPlant, ExtendedProperties)} methods and subclass versions for how to use.
     */
    public abstract RegistryPlant getPlant();

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return updateStateWithCurrentMonth(defaultBlockState());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        if (getPlant().getStageProperty() != null)
        {
            builder.add(getPlant().getStageProperty());
        }
    }

	@Override
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity)
    {
        if (entity != null)
        {
            if (TFCFHelpers.canWalkThroughEffortlessly(entity) || isDead)
            {
                canWalkThroughEffortlessly = true;
            }
            else
            {
                canWalkThroughEffortlessly = false;
            }
        }
	}

    @Override
    public float getSpeedFactor()
    {
        final float modifier = TFCConfig.SERVER.plantsMovementModifier.get().floatValue(); // 0.0 = full speed factor, 1.0 = no modifier
        return canWalkThroughEffortlessly ? 1.0F : Helpers.lerp(modifier, speedFactor, 1.0f);
    }

    protected BlockState updateStateWithCurrentMonth(BlockState state)
    {
        return getPlant().getStageProperty() != null ? state.setValue(getPlant().getStageProperty(), getPlant().stageFor(Calendars.SERVER.getCalendarMonthOfYear())) : state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        level.setBlockAndUpdate(pos, updateStateWithCurrentMonth(state));
        double tempThreshold = Config.COMMON.foliageDecayThreshold.get();

        if (!isDead)
        {
            if (Climate.getTemperature(level, pos) < tempThreshold && TFCFHelpers.getAverageDailyTemperature(level, pos) < tempThreshold) // Cold, thus leaves should wilt/die.
            {
                isDead = true;
                level.blockUpdated(pos, state.getBlock());
                return;
            }
            else
            {
                super.randomTick(state, level, pos, random);
            }
        }
        else if (isDead && getCooldown(level, cooldown))
        {
            if (Climate.getTemperature(level, pos) >= tempThreshold && TFCFHelpers.getAverageDailyTemperature(level, pos) >= tempThreshold) // It's warming up again.
            {
                setCooldown(level, ICalendar.TICKS_IN_DAY * 3, cooldown);
                isDead = false;
                level.blockUpdated(pos, state.getBlock());
                return;
            }
        }
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient)
    {
        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level level, Random rand, BlockPos pos, BlockState state)
    {
        return false;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random rand, BlockPos pos, BlockState state)
    {
    }

    @Override // lifted from AbstractPlantBlock to add leaves to it
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos blockpos = pos.relative(growthDirection.getOpposite());
        BlockState blockstate = level.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (!canAttachTo(blockstate))
        {
            return false;
        }
        else
        {
            return block == getHeadBlock() || block == getBodyBlock() || Helpers.isBlock(blockstate, BlockTags.LEAVES) || blockstate.isFaceSturdy(level, blockpos, growthDirection);
        }
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock()
    {
        return (GrowingPlantHeadBlock) headBlock.get();
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player)
    {
        return new ItemStack(getHeadBlock());
    }

    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return isDead ? RenderShape.INVISIBLE : super.getRenderShape(state); // Invisible when "dead"
    }

    /*@Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return isDead ? Shapes.empty() : super.getShape(state, level, pos, context); // Invisible when "dead"
    }*/

    @Override
    protected boolean isAir(BlockState state)
    {
        return isDead ? true : super.isAir(state); // Invisible when "dead"
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        return isDead ? Collections.emptyList() : super.getDrops(state, builder);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context)
    {
        return isDead ? true : super.canBeReplaced(state, context);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos)
    {
        return isDead ? 1.0F : super.getShadeBrightness(state, level, pos);
    }
}
