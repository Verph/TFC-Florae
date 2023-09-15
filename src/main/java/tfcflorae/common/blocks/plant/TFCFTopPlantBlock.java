package tfcflorae.common.blocks.plant;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.tags.BlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.ForgeHooks;

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
import tfcflorae.util.TFCFHelpers;

import org.jetbrains.annotations.Nullable;

public abstract class TFCFTopPlantBlock extends GrowingPlantHeadBlock implements IForgeBlockExtension
{
    private final Supplier<? extends Block> bodyBlock;
    private final ExtendedProperties properties;

    public boolean canWalkThroughEffortlessly;
    public boolean isDead;
    public long cooldown;

    public static TFCFTopPlantBlock create(RegistryPlant plant, ExtendedProperties properties, Supplier<? extends Block> bodyBlock, Direction direction, VoxelShape shape)
    {
        return new TFCFTopPlantBlock(properties, bodyBlock, direction, shape)
        {
            @Override
            public RegistryPlant getPlant()
            {
                return plant;
            }
        };
    }

    protected TFCFTopPlantBlock(ExtendedProperties properties, Supplier<? extends Block> bodyBlock, Direction direction, VoxelShape shape)
    {
        super(properties.properties(), direction, shape, false, 0);
        this.bodyBlock = bodyBlock;
        this.properties = properties;
        this.cooldown = ICalendar.HOURS_IN_DAY * 10;
        this.isDead = false;

        BlockState stateDefinition = getStateDefinition().any();
        IntegerProperty stageProperty = getPlant().getStageProperty();
        if (stageProperty != null)
        {
            stateDefinition = stateDefinition.setValue(stageProperty, 0);
        }
        registerDefaultState(stateDefinition);
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
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        double tempThreshold = Config.COMMON.foliageDecayThreshold.get();
        boolean check = isDead;

        if (!isDead)
        {
            if (Climate.getTemperature(level, pos) < tempThreshold && TFCFHelpers.getAverageDailyTemperature(level, pos) < tempThreshold) // Cold, thus leaves should wilt/die.
            {
                isDead = true;
                level.blockUpdated(pos, state.getBlock());
            }
            else if (state.getValue(AGE) < 25 && ForgeHooks.onCropsGrowPre(level, pos.relative(growthDirection), level.getBlockState(pos.relative(growthDirection)), random.nextDouble() < TFCConfig.SERVER.plantGrowthChance.get()))
            {
                BlockPos blockpos = pos.relative(growthDirection);
                if (canGrowInto(level.getBlockState(blockpos)))
                {
                    level.setBlockAndUpdate(blockpos, state.cycle(AGE));
                    ForgeHooks.onCropsGrowPost(level, blockpos, level.getBlockState(blockpos));
                }
            }
        }
        else if (isDead && check && --cooldown <= 0)
        {
            if (Climate.getTemperature(level, pos) >= tempThreshold && TFCFHelpers.getAverageDailyTemperature(level, pos) >= tempThreshold) // It's warming up again.
            {
                cooldown = ICalendar.HOURS_IN_DAY * 10;
                isDead = false;
                level.blockUpdated(pos, state.getBlock());
            }
        }
        level.setBlockAndUpdate(pos, updateStateWithCurrentMonth(state));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        return state == null || getPlant().getStageProperty() == null ? state : state.setValue(AGE, Mth.nextInt(context.getLevel().getRandom(), 10, 18)).setValue(getPlant().getStageProperty(), getPlant().stageFor(Calendars.SERVER.getCalendarMonthOfYear()));
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient)
    {
        return false;
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(Random rand)
    {
        return 0;
    }

    @Override
    protected boolean canGrowInto(BlockState state)
    {
        return NetherVines.isValidGrowthState(state);
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
    protected Block getBodyBlock()
    {
        return bodyBlock.get();
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
