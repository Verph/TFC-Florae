package tfcflorae.common.blocks.spidercave;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.IForgeBlockExtension;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.config.TFCConfig;

import tfcflorae.common.blocks.TFCFBlocks;

public class EggBlock extends Block implements IForgeBlockExtension, IFluidLoggable
{
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final FluidProperty FLUID = TFCBlockStateProperties.WATER;
    public static final IntegerProperty AGE = TFCBlockStateProperties.AGE_3;
    private final ExtendedProperties properties;

    public EggBlock(ExtendedProperties properties)
    {
        super(properties.properties());
        this.properties = properties;

        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(AGE, 0));
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, AGE, getFluidProperty());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (random.nextDouble() < TFCConfig.SERVER.plantGrowthChance.get())
        {
            state = state.setValue(AGE, Math.min(state.getValue(AGE) + 1, 3));
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        final BlockState belowState = level.getBlockState(pos.below());
        return belowState.isFaceSturdy(level, pos, Direction.UP) && !(belowState.getBlock() instanceof EggBlock);
    }

    /*@Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        super.onRemove(state, level, pos, newState, isMoving);

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        Map<String, Object> $_dependencies = new HashMap<>();
        $_dependencies.put("x", Integer.valueOf(x));
        $_dependencies.put("y", Integer.valueOf(y));
        $_dependencies.put("z", Integer.valueOf(z));
        $_dependencies.put("level", level);

        if (level instanceof ServerLevel)
        {
            final Random random = new Random();
            final int chance = random.nextInt(5);
            if (chance > 1)
            {
                Spider spiderEntity = new Spider(EntityType.SPIDER, level);
                spiderEntity.moveTo(x, y, z, level.getRandom().nextFloat() * 360.0F, 0.0F);
                if (spiderEntity instanceof Mob)
                {
                    ((Mob)spiderEntity).finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(spiderEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null); 
                    level.addFreshEntity((Entity)spiderEntity);
                }
            }
            else
            {
                CaveSpider spiderEntity = new CaveSpider(EntityType.CAVE_SPIDER, level);
                spiderEntity.moveTo(x, y, z, level.getRandom().nextFloat() * 360.0F, 0.0F);
                if (spiderEntity instanceof Mob)
                {
                    ((Mob)spiderEntity).finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(spiderEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null); 
                    level.addFreshEntity((Entity)spiderEntity);
                }
            }
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid)
    {
        boolean retval = super.onDestroyedByPlayer(state, level, pos, entity, willHarvest, fluid);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        Map<String, Object> $_dependencies = new HashMap<>();
        $_dependencies.put("x", Integer.valueOf(x));
        $_dependencies.put("y", Integer.valueOf(y));
        $_dependencies.put("z", Integer.valueOf(z));
        $_dependencies.put("level", level);

        if (level instanceof ServerLevel)
        {
            final Random random = new Random();
            final int chance = random.nextInt(5);
            if (chance > 1)
            {
                Spider spiderEntity = new Spider(EntityType.SPIDER, level);
                spiderEntity.moveTo(x, y, z, level.getRandom().nextFloat() * 360.0F, 0.0F);
                if (spiderEntity instanceof Mob)
                {
                    ((Mob)spiderEntity).finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(spiderEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null); 
                    level.addFreshEntity((Entity)spiderEntity);
                }
            }
            else
            {
                CaveSpider spiderEntity = new CaveSpider(EntityType.CAVE_SPIDER, level);
                spiderEntity.moveTo(x, y, z, level.getRandom().nextFloat() * 360.0F, 0.0F);
                if (spiderEntity instanceof Mob)
                {
                    ((Mob)spiderEntity).finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(spiderEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null); 
                    level.addFreshEntity((Entity)spiderEntity);
                }
            }
        }
        return retval;
    }*/

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult result, Projectile projectile)
    {
        level.playSound((Player)null, result.getBlockPos(), SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
        level.destroyBlock(result.getBlockPos(), false);
        this.spawnSpider(level, result.getBlockPos());
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float val)
    {
        if (entity.getType() != EntityType.SPIDER && entity.getType() != EntityType.CAVE_SPIDER && entity.getType() != EntityType.SILVERFISH && entity.getType() != EntityType.ENDERMITE)
        {
            level.playSound((Player)null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
            level.destroyBlock(pos, false);
            this.spawnSpider(level, pos);
        }
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack itemStack)
    {
        super.spawnAfterBreak(state, level, pos, itemStack);
        if (level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) == 0)
        {
            this.spawnSpider(level, pos);
        }
    }

    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion)
    {
        if (level instanceof ServerLevel)
        {
            this.spawnSpider((ServerLevel)level, pos);
        }
    }

    public void spawnSpider(Level level, BlockPos pos)
    {
        final Random random = new Random();
        final int chance = random.nextInt(5);
        if (chance > 2 || level.getBlockState(pos).getBlock() == TFCFBlocks.LARGE_SPIDER_EGG.get())
        {
            Spider spider = EntityType.SPIDER.create(level);
            spider.moveTo((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
            level.addFreshEntity(spider);
            if (level.getBlockState(pos).getBlock() == TFCFBlocks.LARGE_SPIDER_EGG.get())
            {
                level.addFreshEntity(spider);
            }
        }
        else
        {
            CaveSpider spider = EntityType.CAVE_SPIDER.create(level);
            spider.moveTo((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
            level.addFreshEntity(spider);
            if (level.getBlockState(pos).getBlock() == TFCFBlocks.SPIDER_EGGS.get())
            {
                level.addFreshEntity(spider);
            }
        }
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader world, BlockPos pos, int fortune, int silkTouch)
    {
        final Random random = new Random();
        return 20 + random.nextInt(20) + random.nextInt(20);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction pDirection, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos)
    {
        FluidHelpers.tickFluid(level, currentPos, state);
        return state;
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockState rotate(BlockState state, Rotation rotation)
    {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
