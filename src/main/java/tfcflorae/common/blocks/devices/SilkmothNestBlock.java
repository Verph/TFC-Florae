package tfcflorae.common.blocks.devices;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.common.blocks.EntityBlockExtension;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.devices.DeviceBlock;
import net.dries007.tfc.common.blocks.plant.ITallPlant;
import net.dries007.tfc.util.calendar.ICalendar;

import tfcflorae.common.blockentities.SilkmothNestBlockEntity;
import tfcflorae.common.blockentities.SilkmothNestBlockEntity.*;
import tfcflorae.common.entities.Silkmoth;
import tfcflorae.common.entities.TFCFEntities;
import tfcflorae.common.blockentities.TFCFBlockEntities;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.util.TFCFHelpers;

public class SilkmothNestBlock extends DeviceBlock implements ITallPlant
{
    public static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<Part> PART = TFCBlockStateProperties.TALL_PLANT_PART;
    public static final int MIN_SILK_LEVELS = 0;
    public static final int MAX_SILK_LEVELS = 3;
    public static final int MIN_SILK_WORM_EGGS = 0;
    public static final int MAX_SILK_WORM_EGGS = 8;
    public static final int MIN_MULBERRY_LEAVES = 0;
    public static final int MAX_MULBERRY_LEAVES = 16;
    public static final IntegerProperty SILK_LEVEL = IntegerProperty.create("silk_level", MIN_SILK_LEVELS, MAX_SILK_LEVELS);
    public static final IntegerProperty SILK_WORM_EGGS = IntegerProperty.create("silk_worm_eggs", MIN_SILK_WORM_EGGS, MAX_SILK_WORM_EGGS);
    public static final IntegerProperty MULBERRY_LEAVES = IntegerProperty.create("mulberry_leaves", MIN_MULBERRY_LEAVES, MAX_MULBERRY_LEAVES);

    public SilkmothNestBlock(ExtendedProperties properties)
    {
        super(properties, InventoryRemoveBehavior.NOOP);

        registerDefaultState(stateDefinition.any().setValue(SILK_LEVEL, 0).setValue(SILK_WORM_EGGS, 0).setValue(MULBERRY_LEAVES, 0).setValue(PART, Part.UPPER));
    }

    @Override
    public OffsetType getOffsetType()
    {
        return OffsetType.XZ;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state)
    {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
    {
        return state.getValue(SILK_LEVEL);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(PART, FACING, SILK_LEVEL, SILK_WORM_EGGS));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    public void placeTwoHalves(LevelAccessor world, BlockPos pos, int flags, Random random)
    {
        Direction direction = TFCFHelpers.getRandom(random);
        world.setBlock(pos, defaultBlockState().setValue(FACING, direction).setValue(SILK_LEVEL, random.nextInt(MAX_SILK_LEVELS) + 1).setValue(TFCBlockStateProperties.TALL_PLANT_PART, Part.UPPER), flags);
        world.setBlock(pos.below(), defaultBlockState().setValue(FACING, direction).setValue(SILK_LEVEL, random.nextInt(MAX_SILK_LEVELS) + 1).setValue(TFCBlockStateProperties.TALL_PLANT_PART, Part.LOWER), flags);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        level.setBlockAndUpdate(pos.below(), defaultBlockState().setValue(FACING, state.getValue(FACING)).setValue(PART, Part.LOWER));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Direction direction = context.getHorizontalDirection();
        BlockPos pos = context.getClickedPos();
        return pos.getY() > context.getLevel().getMinBuildHeight() + 1 && context.getLevel().getBlockState(pos.below()).canBeReplaced(context) ? super.getStateForPlacement(context).setValue(FACING, direction).setValue(SILK_LEVEL, 0).setValue(SILK_WORM_EGGS, 0).setValue(MULBERRY_LEAVES, 0) : null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        if (state.getValue(PART) == Part.UPPER)
        {
            return super.canSurvive(state, level, pos) && level.getBlockState(pos.below()).getValue(PART) == Part.LOWER;
        }
        else
        {
            BlockState blockstate = level.getBlockState(pos.above());
            if (state.getBlock() != this)
            {
                return super.canSurvive(state, level, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            }
            return blockstate.getBlock() == this && blockstate.getValue(PART) == Part.UPPER;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        //super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        Part part = state.getValue(PART);
        if (facing.getAxis() != Direction.Axis.Y || part == Part.LOWER != (facing == Direction.UP) || facingState.getBlock() == this && facingState.getValue(PART) != part)
        {
            return part == Part.LOWER && facing == Direction.DOWN && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        }
        else
        {
            return Blocks.AIR.defaultBlockState();
        }
    }

    public static void dropSilkwormPupae(Level level, BlockPos pos)
    {
        popResource(level, pos, new ItemStack(TFCFItems.SILK_WORM_COCOON.get(), level.getBlockState(pos).getValue(SILK_LEVEL)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (level.isAreaLoaded(pos, 4) && level.getBlockEntity(pos) instanceof SilkmothNestBlockEntity blockEntity)
        {
            if (blockEntity.getOccupantCount() > 0)
            {
                final int delay = (int) (ICalendar.TICKS_IN_DAY * Mth.clamp((random.nextFloat(0.75f)), 0.25f, 0.75f));
                if (state.getValue(SILK_WORM_EGGS) >= MIN_SILK_WORM_EGGS && state.getValue(SILK_WORM_EGGS) <= MAX_SILK_WORM_EGGS)
                {
                    if (delay > SilkmothNestBlockEntity.MIN_OCCUPATION_TICKS_NECTAR && random.nextInt(SilkmothNestBlockEntity.MIN_TICKS_BEFORE_REENTERING_NEST - 200) == 0)
                    {
                        state.setValue(SILK_WORM_EGGS, state.getValue(SILK_WORM_EGGS) + 1);
                    }
                }
                if (state.getValue(MULBERRY_LEAVES) >= MIN_MULBERRY_LEAVES && state.getValue(MULBERRY_LEAVES) <= MAX_MULBERRY_LEAVES)
                {
                    if (delay > SilkmothNestBlockEntity.MIN_OCCUPATION_TICKS_NECTAR && random.nextInt(blockEntity.getOccupantCount() * (state.getValue(SILK_LEVEL) + 1)) > 2)
                    {
                        state.setValue(MULBERRY_LEAVES, state.getValue(MULBERRY_LEAVES) - 1);
                    }
                }
                if (state.getValue(SILK_WORM_EGGS) >= MIN_SILK_WORM_EGGS)
                {
                    if (delay > SilkmothNestBlockEntity.MIN_OCCUPATION_TICKS_NECTAR && random.nextInt(SilkmothNestBlockEntity.MIN_TICKS_BEFORE_REENTERING_NEST) == 0 && blockEntity.getOccupantCount() >= SilkmothNestBlockEntity.MAX_OCCUPANTS)
                    {
                        blockEntity.addOccupant(TFCFEntities.SILKMOTH.get().create(level), true);
                        state.setValue(SILK_WORM_EGGS, state.getValue(SILK_WORM_EGGS) - 1);
                    }
                    else
                    {
                        boolean flag = !level.getBlockState(pos).getCollisionShape(level, pos).isEmpty();
                        Direction direction = state.getValue(FACING);
                        state.setValue(SILK_WORM_EGGS, state.getValue(SILK_WORM_EGGS) - 1);
                        Entity entity = TFCFEntities.SILKMOTH.get().create(level);
                        if (entity != null)
                        {
                            if (entity instanceof Silkmoth moth)
                            {
                                if (moth.getSavedTargetPos() != null && !moth.hasSavedTargetPos() && level.random.nextFloat() < 0.9F)
                                {
                                    moth.setSavedTargetPos(moth.getSavedTargetPos());
                                }
                                float f = entity.getBbWidth();
                                double d3 = flag ? 0.0D : 0.55D + (double)(f / 2.0F);
                                double d0 = (double)pos.getX() + 0.5D + d3 * (double)direction.getStepX();
                                double d1 = (double)pos.getY() + 0.5D - (double)(entity.getBbHeight() / 2.0F);
                                double d2 = (double)pos.getZ() + 0.5D + d3 * (double)direction.getStepZ();
                                entity.moveTo(d0, d1, d2, entity.getYRot(), entity.getXRot());
                            }
                            level.playSound((Player)null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
                            level.addFreshEntity(entity);
                        }
                    }
                }
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        ItemStack stack = player.getItemInHand(hand);
        int i = state.getValue(SILK_LEVEL);
        boolean flag = false;
        if (i >= MAX_SILK_LEVELS)
        {
            Item item = stack.getItem();
            if (stack.isEmpty())
            {
                if (state.getValue(SILK_WORM_EGGS) > MIN_SILK_WORM_EGGS && state.getValue(SILK_WORM_EGGS) <= MAX_SILK_WORM_EGGS)
                {
                    state.setValue(SILK_WORM_EGGS, state.getValue(SILK_WORM_EGGS) - 1);
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(TFCFItems.SILK_MOTH_EGG.get()));
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
            if (stack.canPerformAction(net.minecraftforge.common.ToolActions.SHEARS_HARVEST))
            {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BEEHIVE_SHEAR, SoundSource.NEUTRAL, 1.0F, 1.0F);
                dropSilkwormPupae(level, pos);
                stack.hurtAndBreak(1, player, (player1) -> {
                    player1.broadcastBreakEvent(hand);
                });
                flag = true;
                level.gameEvent(player, GameEvent.SHEAR, pos);
            }
            if (!level.isClientSide() && flag)
            {
                player.awardStat(Stats.ITEM_USED.get(item));
            }
        }
        if (stack.getItem() == TFCFItems.MULBERRY_LEAVES.get())
        {
            if (state.getValue(MULBERRY_LEAVES) >= MIN_MULBERRY_LEAVES && state.getValue(MULBERRY_LEAVES) <= MAX_MULBERRY_LEAVES)
            {
                state.setValue(MULBERRY_LEAVES, state.getValue(MULBERRY_LEAVES) + 1);
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.SWEET_BERRY_BUSH_PLACE, SoundSource.NEUTRAL, 1.0F, 1.0F);
                stack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        if (stack.getItem() == TFCFItems.SILK_WORM.get())
        {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SilkmothNestBlockEntity silkmothBlockEntity) 
            {
                silkmothBlockEntity.addOccupant(TFCFEntities.SILKMOTH.get().create(level), true);
                stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (flag)
        {
            if (!CampfireBlock.isSmokeyPos(level, pos))
            {
                this.releaseMothsAndResetSilkLevel(level, state, pos, player, SilkmothNestBlockEntity.MothReleaseStatus.EMERGENCY);
            }
            else
            {
                this.resetSilkLevel(level, state, pos);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        else
        {
            return super.use(state, level, pos, player, hand, hit);
        }
    }

    public void resetSilkLevel(Level level, BlockState state, BlockPos pos)
    {
        level.setBlock(pos, state.setValue(SILK_LEVEL, MIN_SILK_LEVELS), 3);
    }

    private boolean nestContainsMoths(Level level, BlockPos pos)
    {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof SilkmothNestBlockEntity)
        {
            SilkmothNestBlockEntity silkmothBlockEntity = (SilkmothNestBlockEntity)blockEntity;
            return !silkmothBlockEntity.isEmpty();
        }
        else
        {
            return false;
        }
    }

    public void releaseMothsAndResetSilkLevel(Level level, BlockState state, BlockPos pos, @Nullable Player player, MothReleaseStatus mothReleaseStatus)
    {
        this.resetSilkLevel(level, state, pos);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof SilkmothNestBlockEntity) 
        {
            SilkmothNestBlockEntity silkmothBlockEntity = (SilkmothNestBlockEntity)blockEntity;
            silkmothBlockEntity.emptyAllLivingFromNest(player, state, mothReleaseStatus);
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        if (!level.isClientSide && player.isCreative() && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS))
        {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SilkmothNestBlockEntity)
            {
                SilkmothNestBlockEntity silkmothBlockEntity = (SilkmothNestBlockEntity)blockEntity;
                ItemStack itemstack = new ItemStack(this);
                int i = state.getValue(SILK_LEVEL);
                boolean flag = !silkmothBlockEntity.isEmpty();
                if (flag || i > 0)
                {
                    if (flag)
                    {
                        CompoundTag compoundtag = new CompoundTag();
                        compoundtag.put("Moths", silkmothBlockEntity.writeMoths());
                        BlockItem.setBlockEntityData(itemstack, TFCFBlockEntities.SILKMOTH_NEST.get(), compoundtag);
                    }
                    CompoundTag compoundtag1 = new CompoundTag();
                    compoundtag1.putInt("silk_level", i);
                    itemstack.addTagElement("BlockStateTag", compoundtag1);
                    ItemEntity itemEntity = new ItemEntity(level, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemstack);
                    itemEntity.setDefaultPickUpDelay();
                    level.addFreshEntity(itemEntity);
                }
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }
}