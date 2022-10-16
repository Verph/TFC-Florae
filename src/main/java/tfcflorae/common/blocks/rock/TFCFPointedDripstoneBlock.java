package tfcflorae.common.blocks.rock;

import org.jetbrains.annotations.VisibleForTesting;

import java.util.Optional;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.dries007.tfc.common.blocks.rock.Rock;

import tfcflorae.common.blocks.TFCFBlocks;

public class TFCFPointedDripstoneBlock extends PointedDripstoneBlock implements Fallable, SimpleWaterloggedBlock
{
    public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final int MAX_SEARCH_LENGTH_WHEN_CHECKING_DRIP_TYPE = 11;
    private static final int DELAY_BEFORE_FALLING = 2;
    private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK = 0.02F;
    private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK_IF_UNDER_LIQUID_SOURCE = 0.12F;
    private static final int MAX_SEARCH_LENGTH_BETWEEN_STALACTITE_TIP_AND_CAULDRON = 11;
    private static final float WATER_CAULDRON_FILL_PROBABILITY_PER_RANDOM_TICK = 0.17578125F;
    private static final float LAVA_CAULDRON_FILL_PROBABILITY_PER_RANDOM_TICK = 0.05859375F;
    private static final double MIN_TRIDENT_VELOCITY_TO_BREAK_DRIPSTONE = 0.6D;
    private static final float STALACTITE_DAMAGE_PER_FALL_DISTANCE_AND_SIZE = 1.0F;
    private static final int STALACTITE_MAX_DAMAGE = 40;
    private static final int MAX_STALACTITE_HEIGHT_FOR_DAMAGE_CALCULATION = 6;
    private static final float STALAGMITE_FALL_DISTANCE_OFFSET = 2.0F;
    private static final int STALAGMITE_FALL_DAMAGE_MODIFIER = 2;
    private static final float AVERAGE_DAYS_PER_GROWTH = 5.0F;
    private static final float GROWTH_PROBABILITY_PER_RANDOM_TICK = 0.011377778F;
    private static final int MAX_GROWTH_LENGTH = 7;
    private static final int MAX_STALAGMITE_SEARCH_RANGE_WHEN_GROWING = 10;
    private static final float STALACTITE_DRIP_START_PIXEL = 0.6875F;
    private static final VoxelShape TIP_MERGE_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private static final VoxelShape TIP_SHAPE_UP = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);
    private static final VoxelShape TIP_SHAPE_DOWN = Block.box(5.0D, 5.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private static final VoxelShape FRUSTUM_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    private static final VoxelShape BASE_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final float MAX_HORIZONTAL_OFFSET = 0.125F;
    public static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public TFCFPointedDripstoneBlock(BlockBehaviour.Properties properties)
    {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any().setValue(TIP_DIRECTION, Direction.UP).setValue(THICKNESS, DripstoneThickness.TIP).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos)
    {
        return isValidPointedDripstonePlacement(pLevel, pPos, pState.getValue(TIP_DIRECTION));
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos)
    {
        if (pState.getValue(WATERLOGGED))
        {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        if (pDirection != Direction.UP && pDirection != Direction.DOWN)
        {
            return pState;
        }
        else
        {
            Direction direction = pState.getValue(TIP_DIRECTION);
            if (direction == Direction.DOWN && pLevel.getBlockTicks().hasScheduledTick(pCurrentPos, this))
            {
                return pState;
            }
            else if (pDirection == direction.getOpposite() && !this.canSurvive(pState, pLevel, pCurrentPos))
            {
                if (direction == Direction.DOWN)
                {
                    pLevel.scheduleTick(pCurrentPos, this, 2);
                }
                else
                {
                    pLevel.scheduleTick(pCurrentPos, this, 1);
                }
                return pState;
            }
            else
            {
                boolean flag = pState.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
                DripstoneThickness dripstonethickness = calculateDripstoneThickness(pLevel, pCurrentPos, direction, flag);
                return pState.setValue(THICKNESS, dripstonethickness);
            }
        }
    }

    @Override
    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile)
    {
        BlockPos blockpos = pHit.getBlockPos();
        if (!pLevel.isClientSide && pProjectile.mayInteract(pLevel, blockpos) && pProjectile instanceof ThrownTrident && pProjectile.getDeltaMovement().length() > 0.6D)
        {
            pLevel.destroyBlock(blockpos, true);
        }
    }

    @Override
    public void fallOn(Level p_154047_, BlockState p_154048_, BlockPos p_154049_, Entity p_154050_, float p_154051_)
    {
        if (p_154048_.getValue(TIP_DIRECTION) == Direction.UP && p_154048_.getValue(THICKNESS) == DripstoneThickness.TIP)
        {
            p_154050_.causeFallDamage(p_154051_ + 2.0F, 2.0F, DamageSource.STALAGMITE);
        }
        else
        {
            super.fallOn(p_154047_, p_154048_, p_154049_, p_154050_, p_154051_);
        }
    }

    /**
     * Called periodically clientside on blocks near the player to show effects (like furnace fire particles).
     */
    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom)
    {
        if (canDrip(pState))
        {
            float f = pRandom.nextFloat();
            if (!(f > 0.12F))
            {
                getFluidAboveStalactite(pLevel, pPos, pState).filter((p_154031_) -> {
                    return f < 0.02F || canFillCauldron(p_154031_);
                }).ifPresent((p_154220_) -> {
                    spawnDripParticle(pLevel, pPos, pState, p_154220_);
                });
            }
        }
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom)
    {
        if (isStalagmite(pState) && !this.canSurvive(pState, pLevel, pPos))
        {
            pLevel.destroyBlock(pPos, true);
        }
        else
        {
            spawnFallingStalactite(pState, pLevel, pPos);
        }
    }

    /**
     * Performs a random tick on a block.
     */
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom)
    {
        maybeFillCauldron(pState, pLevel, pPos, pRandom.nextFloat());
        if (pRandom.nextFloat() < 0.011377778F && isStalactiteStartPos(pState, pLevel, pPos))
        {
            growStalactiteOrStalagmiteIfPossible(pState, pLevel, pPos, pRandom);
        }
    }

    /**
     * @deprecated call via {@link
     * net.minecraft.world.level.block.state.BlockBehavior.BlockStateBase#getPistonPushReaction} whenever possible.
     * Implementing/overriding is fine.
     */
    @Override
    @SuppressWarnings("deprecation")
    public PushReaction getPistonPushReaction(BlockState pState)
    {
        return PushReaction.DESTROY;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        LevelAccessor levelaccessor = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        Direction direction = pContext.getNearestLookingVerticalDirection().getOpposite();
        Direction direction1 = calculateTipDirection(levelaccessor, blockpos, direction);
        if (direction1 == null)
        {
            return null;
        }
        else
        {
            boolean flag = !pContext.isSecondaryUseActive();
            DripstoneThickness dripstonethickness = calculateDripstoneThickness(levelaccessor, blockpos, direction1, flag);
            return dripstonethickness == null ? null : this.defaultBlockState().setValue(TIP_DIRECTION, direction1).setValue(THICKNESS, dripstonethickness).setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER));
        }
    }

    @Override
    public FluidState getFluidState(BlockState pState)
    {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos)
    {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        DripstoneThickness dripstonethickness = pState.getValue(THICKNESS);
        VoxelShape voxelshape;
        if (dripstonethickness == DripstoneThickness.TIP_MERGE)
        {
            voxelshape = TIP_MERGE_SHAPE;
        }
        else if (dripstonethickness == DripstoneThickness.TIP)
        {
            if (pState.getValue(TIP_DIRECTION) == Direction.DOWN)
            {
                voxelshape = TIP_SHAPE_DOWN;
            }
            else
            {
                voxelshape = TIP_SHAPE_UP;
            }
        }
        else if (dripstonethickness == DripstoneThickness.FRUSTUM)
        {
            voxelshape = FRUSTUM_SHAPE;
        }
        else if (dripstonethickness == DripstoneThickness.MIDDLE)
        {
            voxelshape = MIDDLE_SHAPE;
        }
        else
        {
            voxelshape = BASE_SHAPE;
        }
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return voxelshape.move(vec3.x, 0.0D, vec3.z);
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos)
    {
        return false;
    }

    /**
     * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
     */
    @Override
    public BlockBehaviour.OffsetType getOffsetType()
    {
        return BlockBehaviour.OffsetType.XZ;
    }

    @Override
    public float getMaxHorizontalOffset()
    {
        return 0.125F;
    }

    @Override
    public void onBrokenAfterFall(Level pLevel, BlockPos pPos, FallingBlockEntity pFallingBlock)
    {
        if (!pFallingBlock.isSilent())
        {
            pLevel.levelEvent(1045, pPos, 0);
        }
    }

    @Override
    public DamageSource getFallDamageSource()
    {
        return DamageSource.FALLING_STALACTITE;
    }

    @Override
    public Predicate<Entity> getHurtsEntitySelector()
    {
        return EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
    }

    private static void spawnFallingStalactite(BlockState pState, ServerLevel pLevel, BlockPos pPos)
    {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();
        for (BlockState blockstate = pState; isStalactite(blockstate); blockstate = pLevel.getBlockState(blockpos$mutableblockpos))
        {
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(pLevel, blockpos$mutableblockpos, blockstate);
            if (isTip(blockstate, true))
            {
                int i = Math.max(1 + pPos.getY() - blockpos$mutableblockpos.getY(), 6);
                float f = 1.0F * (float)i;
                fallingblockentity.setHurtsEntities(f, 40);
                break;
            }
            blockpos$mutableblockpos.move(Direction.DOWN);
        }
    }

    @VisibleForTesting
    public static void growStalactiteOrStalagmiteIfPossible(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        BlockState blockstate = level.getBlockState(pos.above(1));
        BlockState blockstate1 = level.getBlockState(pos.above(2));
        if (canGrow(blockstate, blockstate1))
        {
            BlockPos blockpos = findTip(state, level, pos, 7, false);
            if (blockpos != null)
            {
                BlockState blockstate2 = level.getBlockState(blockpos);
                if (canDrip(blockstate2) && canTipGrow(blockstate2, level, blockpos))
                {
                    if (random.nextBoolean())
                    {
                        grow(level, blockpos, Direction.DOWN);
                    }
                    else
                    {
                        growStalagmiteBelow(level, blockpos);
                    }
                }
            }
        }
    }

    public static void growStalagmiteBelow(ServerLevel pLevel, BlockPos pPos)
    {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();
        for(int i = 0; i < 10; ++i)
        {
            blockpos$mutableblockpos.move(Direction.DOWN);
            BlockState blockstate = pLevel.getBlockState(blockpos$mutableblockpos);
            if (!blockstate.getFluidState().isEmpty())
            {
                return;
            }
            if (isUnmergedTipWithDirection(blockstate, Direction.UP) && canTipGrow(blockstate, pLevel, blockpos$mutableblockpos))
            {
                grow(pLevel, blockpos$mutableblockpos, Direction.UP);
                return;
            }
            if (isValidPointedDripstonePlacement(pLevel, blockpos$mutableblockpos, Direction.UP) && !pLevel.isWaterAt(blockpos$mutableblockpos.below()))
            {
                grow(pLevel, blockpos$mutableblockpos.below(), Direction.UP);
                return;
            }
            if (!canDripThrough(pLevel, blockpos$mutableblockpos, blockstate))
            {
                return;
            }
        }
    }

    public static void grow(ServerLevel pServer, BlockPos pPos, Direction pDirection)
    {
        BlockPos blockpos = pPos.relative(pDirection);
        BlockState blockstate = pServer.getBlockState(blockpos);
        if (isUnmergedTipWithDirection(blockstate, pDirection.getOpposite()))
        {
            createMergedTips(blockstate, pServer, blockpos);
        }
        else if (blockstate.isAir() || blockstate.is(Blocks.WATER))
        {
            createDripstone(pServer, blockpos, pDirection, DripstoneThickness.TIP);
        }
    }

    public static void createDripstone(LevelAccessor pLevel, BlockPos pPos, Direction pDirection, DripstoneThickness pThickness)
    {
        BlockState blockstate = TFCFBlocks.DRIPSTONE_BLOCKS.get(DripstoneRock.DRIPSTONE).get(Rock.BlockType.SPIKE).get().defaultBlockState().setValue(TIP_DIRECTION, pDirection).setValue(THICKNESS, pThickness).setValue(WATERLOGGED, Boolean.valueOf(pLevel.getFluidState(pPos).getType() == Fluids.WATER));
        pLevel.setBlock(pPos, blockstate, 3);
    }

    public static void createMergedTips(BlockState pState, LevelAccessor pLevel, BlockPos pPos)
    {
        BlockPos blockpos;
        BlockPos blockpos1;
        if (pState.getValue(TIP_DIRECTION) == Direction.UP)
        {
            blockpos1 = pPos;
            blockpos = pPos.above();
        }
        else
        {
            blockpos = pPos;
            blockpos1 = pPos.below();
        }
        createDripstone(pLevel, blockpos, Direction.DOWN, DripstoneThickness.TIP_MERGE);
        createDripstone(pLevel, blockpos1, Direction.UP, DripstoneThickness.TIP_MERGE);
    }

    public static void spawnDripParticle(Level pLevel, BlockPos pPos, BlockState pState)
    {
        getFluidAboveStalactite(pLevel, pPos, pState).ifPresent((p_154189_) -> {
            spawnDripParticle(pLevel, pPos, pState, p_154189_);
        });
    }

    public static void spawnDripParticle(Level pLevel, BlockPos pPos, BlockState pState, Fluid pFluid)
    {
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        double d0 = 0.0625D;
        double d1 = (double)pPos.getX() + 0.5D + vec3.x;
        double d2 = (double)((float)(pPos.getY() + 1) - 0.6875F) - 0.0625D;
        double d3 = (double)pPos.getZ() + 0.5D + vec3.z;
        Fluid fluid = getDripFluid(pLevel, pFluid);
        ParticleOptions particleoptions = fluid.is(FluidTags.LAVA) ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
        pLevel.addParticle(particleoptions, d1, d2, d3, 0.0D, 0.0D, 0.0D);
    }

    @Nullable
    public static BlockPos findTip(BlockState p_154131_, LevelAccessor p_154132_, BlockPos p_154133_, int p_154134_, boolean p_154135_)
    {
        if (isTip(p_154131_, p_154135_))
        {
            return p_154133_;
        }
        else
        {
            Direction direction = p_154131_.getValue(TIP_DIRECTION);
            BiPredicate<BlockPos, BlockState> bipredicate = (p_202023_, p_202024_) -> {
                return p_202024_.is(TFCFBlocks.DRIPSTONE_BLOCKS.get(DripstoneRock.DRIPSTONE).get(Rock.BlockType.SPIKE).get()) && p_202024_.getValue(TIP_DIRECTION) == direction;
            };
            return findBlockVertical(p_154132_, p_154133_, direction.getAxisDirection(), bipredicate, (p_154168_) -> {
                return isTip(p_154168_, p_154135_);
            }, p_154134_).orElse((BlockPos)null);
        }
    }

    @Nullable
    public static Direction calculateTipDirection(LevelReader p_154191_, BlockPos p_154192_, Direction p_154193_)
    {
        Direction direction;
        if (isValidPointedDripstonePlacement(p_154191_, p_154192_, p_154193_))
        {
            direction = p_154193_;
        }
        else
        {
            if (!isValidPointedDripstonePlacement(p_154191_, p_154192_, p_154193_.getOpposite()))
            {
                return null;
            }
            direction = p_154193_.getOpposite();
        }
        return direction;
    }

    public static DripstoneThickness calculateDripstoneThickness(LevelReader p_154093_, BlockPos p_154094_, Direction p_154095_, boolean p_154096_)
    {
        Direction direction = p_154095_.getOpposite();
        BlockState blockstate = p_154093_.getBlockState(p_154094_.relative(p_154095_));
        if (isPointedDripstoneWithDirection(blockstate, direction))
        {
            return !p_154096_ && blockstate.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
        }
        else if (!isPointedDripstoneWithDirection(blockstate, p_154095_))
        {
            return DripstoneThickness.TIP;
        }
        else
        {
            DripstoneThickness dripstonethickness = blockstate.getValue(THICKNESS);
            if (dripstonethickness != DripstoneThickness.TIP && dripstonethickness != DripstoneThickness.TIP_MERGE)
            {
                BlockState blockstate1 = p_154093_.getBlockState(p_154094_.relative(direction));
                return !isPointedDripstoneWithDirection(blockstate1, p_154095_) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
            }
            else
            {
                return DripstoneThickness.FRUSTUM;
            }
        }
    }

    public static boolean canDrip(BlockState p_154239_)
    {
        return isStalactite(p_154239_) && p_154239_.getValue(THICKNESS) == DripstoneThickness.TIP && !p_154239_.getValue(WATERLOGGED);
    }

    public static boolean canTipGrow(BlockState pState, ServerLevel pLevel, BlockPos pPos)
    {
        Direction direction = pState.getValue(TIP_DIRECTION);
        BlockPos blockpos = pPos.relative(direction);
        BlockState blockstate = pLevel.getBlockState(blockpos);
        if (!blockstate.getFluidState().isEmpty())
        {
            return false;
        }
        else
        {
            return blockstate.isAir() ? true : isUnmergedTipWithDirection(blockstate, direction.getOpposite());
        }
    }

    public static Optional<BlockPos> findRootBlock(Level p_154067_, BlockPos p_154068_, BlockState p_154069_, int p_154070_)
    {
        Direction direction = p_154069_.getValue(TIP_DIRECTION);
        BiPredicate<BlockPos, BlockState> bipredicate = (p_202015_, p_202016_) -> {
            return p_202016_.is(TFCFBlocks.DRIPSTONE_BLOCKS.get(DripstoneRock.DRIPSTONE).get(Rock.BlockType.SPIKE).get()) && p_202016_.getValue(TIP_DIRECTION) == direction;
        };
        return findBlockVertical(p_154067_, p_154068_, direction.getOpposite().getAxisDirection(), bipredicate, (p_154245_) -> {
            return !p_154245_.is(TFCFBlocks.DRIPSTONE_BLOCKS.get(DripstoneRock.DRIPSTONE).get(Rock.BlockType.SPIKE).get());
        }, p_154070_);
    }

    public static boolean isValidPointedDripstonePlacement(LevelReader p_154222_, BlockPos p_154223_, Direction p_154224_)
    {
        BlockPos blockpos = p_154223_.relative(p_154224_.getOpposite());
        BlockState blockstate = p_154222_.getBlockState(blockpos);
        return blockstate.isFaceSturdy(p_154222_, blockpos, p_154224_) || isPointedDripstoneWithDirection(blockstate, p_154224_);
    }

    public static boolean isTip(BlockState p_154154_, boolean p_154155_)
    {
        if (!p_154154_.is(TFCFBlocks.DRIPSTONE_BLOCKS.get(DripstoneRock.DRIPSTONE).get(Rock.BlockType.SPIKE).get()))
        {
            return false;
        }
        else
        {
            DripstoneThickness dripstonethickness = p_154154_.getValue(THICKNESS);
            return dripstonethickness == DripstoneThickness.TIP || p_154155_ && dripstonethickness == DripstoneThickness.TIP_MERGE;
        }
    }

    public static boolean isUnmergedTipWithDirection(BlockState p_154144_, Direction p_154145_)
    {
        return isTip(p_154144_, false) && p_154144_.getValue(TIP_DIRECTION) == p_154145_;
    }

    public static boolean isStalactite(BlockState pState)
    {
        return isPointedDripstoneWithDirection(pState, Direction.DOWN);
    }

    public static boolean isStalagmite(BlockState pState)
    {
        return isPointedDripstoneWithDirection(pState, Direction.UP);
    }

    public static boolean isStalactiteStartPos(BlockState pState, LevelReader pLevel, BlockPos pPos)
    {
        return isStalactite(pState) && !pLevel.getBlockState(pPos.above()).is(TFCFBlocks.DRIPSTONE_BLOCKS.get(DripstoneRock.DRIPSTONE).get(Rock.BlockType.SPIKE).get());
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType)
    {
        return false;
    }

    public static boolean isPointedDripstoneWithDirection(BlockState p_154208_, Direction p_154209_)
    {
        return p_154208_.is(TFCFBlocks.DRIPSTONE_BLOCKS.get(DripstoneRock.DRIPSTONE).get(Rock.BlockType.SPIKE).get()) && p_154208_.getValue(TIP_DIRECTION) == p_154209_;
    }

    @Nullable
    public static BlockPos findStalactiteTipAboveCauldron(Level pLevel, BlockPos pPos)
    {
        BiPredicate<BlockPos, BlockState> bipredicate = (p_202030_, p_202031_) -> {
            return canDripThrough(pLevel, p_202030_, p_202031_);
        };
        return findBlockVertical(pLevel, pPos, Direction.UP.getAxisDirection(), bipredicate, TFCFPointedDripstoneBlock::canDrip, 11).orElse((BlockPos)null);
    }

    public static Fluid getCauldronFillFluidType(Level pLevel, BlockPos pPos)
    {
        return getFluidAboveStalactite(pLevel, pPos, pLevel.getBlockState(pPos)).filter(TFCFPointedDripstoneBlock::canFillCauldron).orElse(Fluids.EMPTY);
    }

    public static Optional<Fluid> getFluidAboveStalactite(Level pLevel, BlockPos pPos, BlockState pState)
    {
        return !isStalactite(pState) ? Optional.empty() : findRootBlock(pLevel, pPos, pState, 11).map((p_202027_) -> {
            return pLevel.getFluidState(p_202027_.above()).getType();
        });
    }

    public static boolean canFillCauldron(Fluid p_154159_)
    {
        return p_154159_ == Fluids.LAVA || p_154159_ == Fluids.WATER;
    }

    public static boolean canGrow(BlockState state, BlockState fluidState)
    {
        return state.is(TFCFBlocks.DRIPSTONE_BLOCKS.get(DripstoneRock.DRIPSTONE).get(Rock.BlockType.SPIKE).get()) && fluidState.is(Blocks.WATER) && fluidState.getFluidState().isSource();
    }

    public static Fluid getDripFluid(Level pLevel, Fluid pFluid)
    {
        if (pFluid.isSame(Fluids.EMPTY))
        {
            return pLevel.dimensionType().ultraWarm() ? Fluids.LAVA : Fluids.WATER;
        }
        else
        {
            return pFluid;
        }
    }

    public static Optional<BlockPos> findBlockVertical(LevelAccessor p_202007_, BlockPos p_202008_, Direction.AxisDirection p_202009_, BiPredicate<BlockPos, BlockState> p_202010_, Predicate<BlockState> p_202011_, int p_202012_)
    {
        Direction direction = Direction.get(p_202009_, Direction.Axis.Y);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = p_202008_.mutable();
        for(int i = 1; i < p_202012_; ++i)
        {
            blockpos$mutableblockpos.move(direction);
            BlockState blockstate = p_202007_.getBlockState(blockpos$mutableblockpos);
            if (p_202011_.test(blockstate))
            {
                return Optional.of(blockpos$mutableblockpos.immutable());
            }
            if (p_202007_.isOutsideBuildHeight(blockpos$mutableblockpos.getY()) || !p_202010_.test(blockpos$mutableblockpos, blockstate))
            {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public static boolean canDripThrough(BlockGetter p_202018_, BlockPos p_202019_, BlockState p_202020_)
    {
        if (p_202020_.isAir())
        {
            return true;
        }
        else if (p_202020_.isSolidRender(p_202018_, p_202019_))
        {
            return false;
        }
        else if (!p_202020_.getFluidState().isEmpty())
        {
            return false;
        }
        else
        {
            VoxelShape voxelshape = p_202020_.getCollisionShape(p_202018_, p_202019_);
            return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, voxelshape, BooleanOp.AND);
        }
    }
}
