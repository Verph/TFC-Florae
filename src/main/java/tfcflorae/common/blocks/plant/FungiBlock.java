package tfcflorae.common.blocks.plant;

import java.util.Random;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraftforge.common.Tags;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.plant.TFCBushBlock;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.Season;
import net.dries007.tfc.util.registry.RegistryPlant;

import tfcflorae.client.particle.TFCFParticles;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.util.TFCFHelpers;

public abstract class FungiBlock extends TFCBushBlock
{
    public static final IntegerProperty AGE = TFCBlockStateProperties.AGE_3;
    protected static final VoxelShape PLANT_SHAPE = box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    public final TFCFPlant plantTFCF;

    public boolean canWalkThroughEffortlessly;

    public static FungiBlock create(RegistryPlant plant, TFCFPlant plantTFCF, ExtendedProperties properties)
    {
        return new FungiBlock(properties, plantTFCF)
        {
            @Override
            public RegistryPlant getPlant()
            {
                return plant;
            }
        };
    }

    protected FungiBlock(ExtendedProperties properties, TFCFPlant plantTFCF)
    {
        super(properties);
        this.plantTFCF = plantTFCF;

        BlockState stateDefinition = getStateDefinition().any().setValue(AGE, 0);
        IntegerProperty stageProperty = getPlant().getStageProperty();
        if (stageProperty != null)
        {
            stateDefinition = stateDefinition.setValue(stageProperty, 0);
        }
        registerDefaultState(stateDefinition);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random)
    {
        if (plantTFCF.getSporeColor() != -1 && level.getRawBrightness(pos, 0) > 9 && (Calendars.get(level).getCalendarMonthOfYear().getSeason() == Season.SUMMER || Calendars.get(level).getCalendarMonthOfYear().getSeason() == Season.FALL))
        {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

            int sporeSpread = plantTFCF.getSporeSpread();
            for (int l = 0; l <= sporeSpread + random.nextInt(3); ++l)
            {
                mutablePos.set(i + Mth.nextInt(random, -sporeSpread, sporeSpread), j - random.nextInt(sporeSpread), k + Mth.nextInt(random, -sporeSpread, sporeSpread));
                BlockState blockstate = level.getBlockState(mutablePos);

                if (!blockstate.isCollisionShapeFullBlock(level, mutablePos))
                {
                    level.addParticle(new BlockParticleOption(TFCFParticles.FALLING_SPORE.get(), state), mutablePos.getX() + random.nextDouble(), mutablePos.getY() + random.nextDouble(), mutablePos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return Helpers.isBlock(state, Tags.Blocks.STONE) || state.is(BlockTags.MUSHROOM_GROW_BLOCK) || Helpers.isBlock(state.getBlock(), BlockTags.LOGS) || Helpers.isBlock(state.getBlock(), TFCTags.Blocks.SEA_BUSH_PLANTABLE_ON) || Helpers.isBlock(state.getBlock(), TFCTags.Blocks.GRASS_PLANTABLE_ON) || Helpers.isBlock(state.getBlock(), TFCTags.Blocks.BUSH_PLANTABLE_ON) || super.mayPlaceOn(state, level, pos);
    }

    @Override
    public OffsetType getOffsetType()
    {
        return OffsetType.XZ;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return PLANT_SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (random.nextDouble() < TFCConfig.SERVER.plantGrowthChance.get())
        {
            state = state.setValue(AGE, Math.min(state.getValue(AGE) + 1, 3));

            int i = 5;
            int j = 4;

            for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, -1, -4), pos.offset(4, 1, 4)))
            {
                if (level.getBlockState(blockpos).is(this))
                {
                    --i;
                    if (i <= 0) {
                        return;
                    }
                }
            }
            BlockPos blockpos1 = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
            for(int k = 0; k < 4; ++k)
            {
                if (level.isEmptyBlock(blockpos1) && state.canSurvive(level, blockpos1))
                {
                    pos = blockpos1;
                }
                blockpos1 = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
            }
            if (level.isEmptyBlock(blockpos1) && state.canSurvive(level, blockpos1))
            {
                level.setBlock(blockpos1, state, 2);
            }

            final BlockPos posBelow = pos.below();
            final Block blockBelow = level.getBlockState(posBelow).getBlock();
            if (blockBelow == TFCFBlocks.ROOTED_BOG_IRON.get() || blockBelow == TFCFBlocks.BOG_IRON.get())
            {
                level.setBlock(posBelow, TFCFBlocks.MYCELIUM_BOG_IRON.get().defaultBlockState(), Block.UPDATE_ALL);
                level.setBlockAndUpdate(pos, updateStateWithCurrentMonth(state));
                return;
            }
            else
            {
                for (SoilBlockType.Variant soilVariant : SoilBlockType.Variant.values())
                {
                    if (blockBelow == TFCBlocks.SOIL.get(SoilBlockType.ROOTED_DIRT).get(soilVariant).get() || blockBelow == TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(soilVariant).get())
                    {
                        level.setBlock(posBelow, TFCFBlocks.TFCSOIL.get(TFCFSoil.MYCELIUM_DIRT).get(soilVariant).get().defaultBlockState(), Block.UPDATE_ALL);
                        level.setBlockAndUpdate(pos, updateStateWithCurrentMonth(state));
                        return;
                    }
                }
                for (TFCFSoil.TFCFVariant soilVariant : TFCFSoil.TFCFVariant.values())
                {
                    if (blockBelow == TFCFBlocks.TFCFSOIL.get(TFCFSoil.ROOTED_DIRT).get(soilVariant).get() || blockBelow == TFCFBlocks.TFCFSOIL.get(TFCFSoil.DIRT).get(soilVariant).get())
                    {
                        level.setBlock(posBelow, TFCFBlocks.TFCFSOIL.get(TFCFSoil.MYCELIUM_DIRT).get(soilVariant).get().defaultBlockState(), Block.UPDATE_ALL);
                        level.setBlockAndUpdate(pos, updateStateWithCurrentMonth(state));
                        return;
                    }
                }
            }
        }
        level.setBlockAndUpdate(pos, updateStateWithCurrentMonth(state));
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos blockpos = pos.below();
        BlockState blockstate = level.getBlockState(blockpos);

        return (mayPlaceOn(blockstate, level, pos) && level.getRawBrightness(pos, 0) < 14);
    }

    /**
     * Gets the plant metadata for this block.
     *
     * The stage property is isolated and referenced via this as it is needed in the {@link Block} constructor - which builds the state container, and requires all property references to be computed in {@link Block#createBlockStateDefinition(StateDefinition.Builder)}.
     *
     * See the various {@link FungiBlock#create(RegistryPlant, ExtendedProperties)} methods and subclass versions for how to use.
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
        if (getPlant().getStageProperty() != null)
        {
            builder.add(getPlant().getStageProperty());
        }
        builder.add(AGE);
    }

	@Override
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity)
    {
        if (entity != null)
        {
            if (TFCFHelpers.canWalkThroughEffortlessly(entity))
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
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
    {
        if (!canSurvive(state, level, pos))
        {
            level.destroyBlock(pos, false);
        }
    }
}
