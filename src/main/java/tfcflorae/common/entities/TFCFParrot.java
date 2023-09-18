package tfcflorae.common.entities;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.entities.EntityHelpers;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LandOnOwnersShoulderGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import tfcflorae.common.TFCFTags;

public class TFCFParrot extends Parrot
{
    public static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(Parrot.class, EntityDataSerializers.INT);
    public static final Predicate<Mob> NOT_PARROT_PREDICATE = new Predicate<Mob>()
    {
        public boolean test(@Nullable Mob entity)
        {
            return entity != null && TFCFParrot.MOB_SOUND_MAP.containsKey(entity.getType());
        }
    };
    public static final Ingredient POISONOUS_FOOD = Ingredient.of(TFCFTags.Items.POISONOUS_PARROT_FOOD);
    public static final Ingredient TAME_FOOD = Ingredient.of(TFCFTags.Items.PARROT_FOOD);
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(TFCFParrot.class, EntityDataSerializers.INT);

    static final Map<EntityType<?>, SoundEvent> MOB_SOUND_MAP = Util.make(Maps.newHashMap(), (entity) -> {
        entity.put(EntityType.BLAZE, SoundEvents.PARROT_IMITATE_BLAZE);
        entity.put(EntityType.CAVE_SPIDER, SoundEvents.PARROT_IMITATE_SPIDER);
        entity.put(EntityType.CREEPER, SoundEvents.PARROT_IMITATE_CREEPER);
        entity.put(EntityType.DROWNED, SoundEvents.PARROT_IMITATE_DROWNED);
        entity.put(EntityType.ELDER_GUARDIAN, SoundEvents.PARROT_IMITATE_ELDER_GUARDIAN);
        entity.put(EntityType.ENDER_DRAGON, SoundEvents.PARROT_IMITATE_ENDER_DRAGON);
        entity.put(EntityType.ENDERMITE, SoundEvents.PARROT_IMITATE_ENDERMITE);
        entity.put(EntityType.EVOKER, SoundEvents.PARROT_IMITATE_EVOKER);
        entity.put(EntityType.GHAST, SoundEvents.PARROT_IMITATE_GHAST);
        entity.put(EntityType.GUARDIAN, SoundEvents.PARROT_IMITATE_GUARDIAN);
        entity.put(EntityType.HOGLIN, SoundEvents.PARROT_IMITATE_HOGLIN);
        entity.put(EntityType.HUSK, SoundEvents.PARROT_IMITATE_HUSK);
        entity.put(EntityType.ILLUSIONER, SoundEvents.PARROT_IMITATE_ILLUSIONER);
        entity.put(EntityType.MAGMA_CUBE, SoundEvents.PARROT_IMITATE_MAGMA_CUBE);
        entity.put(EntityType.PHANTOM, SoundEvents.PARROT_IMITATE_PHANTOM);
        entity.put(EntityType.PIGLIN, SoundEvents.PARROT_IMITATE_PIGLIN);
        entity.put(EntityType.PIGLIN_BRUTE, SoundEvents.PARROT_IMITATE_PIGLIN_BRUTE);
        entity.put(EntityType.PILLAGER, SoundEvents.PARROT_IMITATE_PILLAGER);
        entity.put(EntityType.RAVAGER, SoundEvents.PARROT_IMITATE_RAVAGER);
        entity.put(EntityType.SHULKER, SoundEvents.PARROT_IMITATE_SHULKER);
        entity.put(EntityType.SILVERFISH, SoundEvents.PARROT_IMITATE_SILVERFISH);
        entity.put(EntityType.SKELETON, SoundEvents.PARROT_IMITATE_SKELETON);
        entity.put(EntityType.SLIME, SoundEvents.PARROT_IMITATE_SLIME);
        entity.put(EntityType.SPIDER, SoundEvents.PARROT_IMITATE_SPIDER);
        entity.put(EntityType.STRAY, SoundEvents.PARROT_IMITATE_STRAY);
        entity.put(EntityType.VEX, SoundEvents.PARROT_IMITATE_VEX);
        entity.put(EntityType.VINDICATOR, SoundEvents.PARROT_IMITATE_VINDICATOR);
        entity.put(EntityType.WITCH, SoundEvents.PARROT_IMITATE_WITCH);
        entity.put(EntityType.WITHER, SoundEvents.PARROT_IMITATE_WITHER);
        entity.put(EntityType.WITHER_SKELETON, SoundEvents.PARROT_IMITATE_WITHER_SKELETON);
        entity.put(EntityType.ZOGLIN, SoundEvents.PARROT_IMITATE_ZOGLIN);
        entity.put(EntityType.ZOMBIE, SoundEvents.PARROT_IMITATE_ZOMBIE);
        entity.put(EntityType.ZOMBIE_VILLAGER, SoundEvents.PARROT_IMITATE_ZOMBIE_VILLAGER);
    });

    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    public float nextFlap = 1.0F;
    public boolean partyParrot;

    @Nullable
    public BlockPos jukebox;

    public TFCFParrot(EntityType<? extends TFCFParrot> parrot, Level level)
    {
        super(parrot, level);
        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
        this.goalSelector.addGoal(2, new TFCFParrot.ParrotWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LandOnOwnersShoulderGoal(this));
        this.goalSelector.addGoal(3, new FollowMobGoal(this, 1.0D, 3.0F, 7.0F));
    }

    public Variant getParrotVariant()
    {
        return Variant.byId(this.entityData.get(VARIANT));
    }

    public void setVariant(Variant variant)
    {
        this.entityData.set(VARIANT, variant.getId());
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.putInt("variant", this.getParrotVariant().getId());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        this.setVariant(Variant.byId(tag.getInt("variant")));
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions)
    {
        return dimensions.height * 0.6F;
    }

    @Override
    public void aiStep()
    {
        if (this.jukebox == null || !this.jukebox.closerToCenterThan(this.position(), 3.46D) || !this.level.getBlockState(this.jukebox).is(Blocks.JUKEBOX))
        {
            this.partyParrot = false;
            this.jukebox = null;
        }
        if (this.level.random.nextInt(400) == 0)
        {
            imitateNearbyMobs(this.level, this);
        }

        super.aiStep();
        this.calculateFlapping();
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound()
    {
        return getAmbient(this.level, this.level.random);
    }

    public static SoundEvent getAmbient(Level level, Random random)
    {
        if (level.getDifficulty() != Difficulty.PEACEFUL && random.nextInt(1000) == 0)
        {
            List<EntityType<?>> list = Lists.newArrayList(MOB_SOUND_MAP.keySet());
            return getImitatedSound(list.get(random.nextInt(list.size())));
        }
        else
        {
            return SoundEvents.PARROT_AMBIENT;
        }
    }

    public static SoundEvent getImitatedSound(EntityType<?> entity)
    {
        return MOB_SOUND_MAP.getOrDefault(entity, SoundEvents.PARROT_AMBIENT);
    }

    public static boolean imitateNearbyMobs(Level level, Entity entity)
    {
        if (entity.isAlive() && !entity.isSilent() && level.random.nextInt(2) == 0)
        {
            List<Mob> list = level.getEntitiesOfClass(Mob.class, entity.getBoundingBox().inflate(20.0D), NOT_PARROT_PREDICATE);
            if (!list.isEmpty())
            {
                Mob mob = list.get(level.random.nextInt(list.size()));
                if (!mob.isSilent())
                {
                    SoundEvent soundevent = getImitatedSound(mob.getType());
                    level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), soundevent, entity.getSoundSource(), 0.7F, getPitch(level.random));
                    return true;
                }
            }
            return false;
        }
        else
        {
            return false;
        }
    }

    public void calculateFlapping()
    {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (float)(!this.onGround && !this.isPassenger() ? 4 : -1) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround && this.flapping < 1.0F)
        {
            this.flapping = 1.0F;
        }
        this.flapping *= 0.9F;
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround && vec3.y < 0.0D)
        {
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
        }
        this.flap += this.flapping * 2.0F;
    }

    @Override
    protected boolean isFlapping()
    {
        return this.flyDist > this.nextFlap;
    }

    @Override
    protected void onFlap()
    {
        this.playSound(SoundEvents.PARROT_FLY, 0.15F, 1.0F);
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag)
    {
        final int colour = accessor.getRandom().nextInt(6);
        Holder<Biome> biome = accessor.getBiome(this.blockPosition());

        BlockPos pos = this.blockPosition();
        ServerLevel level = accessor.getLevel();

        float temperature = 10;
        if (level != null && pos != null && level.isLoaded(pos) && level.getChunkSource().hasChunk(pos.getX(), pos.getZ()))
        {
            final ChunkData data = EntityHelpers.getChunkDataForSpawning(level, pos);
            temperature = data.getAverageTemp(pos);
        }
        else if (level == null || pos == null)
        {
            temperature = random.nextInt(23);
        }

        if (colour == 0)
        {
            this.setVariant(Variant.BLUE);
        }
        else if (colour == 1)
        {
            this.setVariant(Variant.GREEN);
        }
        else if (colour == 2)
        {
            this.setVariant(Variant.GRAY);
        }
        else if (colour == 3)
        {
            this.setVariant(Variant.RED);
        }
        /*else if (colour == 4 && temperature <= 5F && (biome.is(TFCFTags.Biomes.OCEANIC) || biome.is(TFCFTags.Biomes.COASTAL)))
        {
            this.setVariant(Variant.PUFFIN);
        }*/
        else
        {
            this.setVariant(Variant.YELLOW_BLUE);
        }

        if (groupData == null)
        {
            groupData = new AgeableMob.AgeableMobGroupData(false);
        }
        return super.finalizeSpawn(accessor, difficulty, spawnType, groupData, tag);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand)
    {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!this.isTame() && TAME_FOOD.test(itemstack))
        {
            if (!player.getAbilities().instabuild)
            {
                itemstack.shrink(1);
            }

            if (!this.isSilent())
            {
                this.level.playSound((Player)null, this.getX(), this.getY(), this.getZ(), SoundEvents.PARROT_EAT, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            }

            if (!this.level.isClientSide)
            {
                if (this.random.nextInt(10) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player))
                {
                    this.tame(player);
                    this.level.broadcastEntityEvent(this, (byte)7);
                }
                else
                {
                    this.level.broadcastEntityEvent(this, (byte)6);
                }
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        else if (POISONOUS_FOOD.test(itemstack))
        {
            if (!player.getAbilities().instabuild)
            {
                itemstack.shrink(1);
            }

            this.addEffect(new MobEffectInstance(MobEffects.POISON, 900));
            if (player.isCreative() || !this.isInvulnerable())
            {
                this.hurt(DamageSource.playerAttack(player), Float.MAX_VALUE);
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        else if (!this.isFlying() && this.isTame() && this.isOwnedBy(player))
        {
            if (!this.level.isClientSide)
            {
                this.setOrderedToSit(!this.isOrderedToSit());
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        else
        {
            return EntityHelpers.pluck(player, hand, this) ? InteractionResult.sidedSuccess(level.isClientSide) : super.mobInteract(player, hand);
        }
    }

    static class ParrotWanderGoal extends WaterAvoidingRandomFlyingGoal
    {
        public ParrotWanderGoal(PathfinderMob mob, double n)
        {
            super(mob, n);
        }

        @Nullable
        @Override
        protected Vec3 getPosition()
        {
            Vec3 vec3 = null;
            if (this.mob.isInWater())
            {
                vec3 = LandRandomPos.getPos(this.mob, 15, 15);
            }

            if (this.mob.getRandom().nextFloat() >= this.probability)
            {
                vec3 = this.getTreePos();
            }

            return vec3 == null ? super.getPosition() : vec3;
        }

        @Nullable
        public Vec3 getTreePos()
        {
            BlockPos blockpos = this.mob.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

            for(BlockPos blockpos1 : BlockPos.betweenClosed(Mth.floor(this.mob.getX() - 3.0D), Mth.floor(this.mob.getY() - 6.0D), Mth.floor(this.mob.getZ() - 3.0D), Mth.floor(this.mob.getX() + 3.0D), Mth.floor(this.mob.getY() + 6.0D), Mth.floor(this.mob.getZ() + 3.0D)))
            {
                if (!blockpos.equals(blockpos1))
                {
                    BlockState blockstate = this.mob.level.getBlockState(blockpos$mutableblockpos1.setWithOffset(blockpos1, Direction.DOWN));
                    boolean flag = blockstate.getBlock() instanceof LeavesBlock || blockstate.is(BlockTags.LOGS) || blockstate.is(BlockTags.LEAVES);
                    if (flag && this.mob.level.isEmptyBlock(blockpos1) && this.mob.level.isEmptyBlock(blockpos$mutableblockpos.setWithOffset(blockpos1, Direction.UP)))
                    {
                        return Vec3.atBottomCenterOf(blockpos1);
                    }
                }
            }
            return null;
        }
    }

    public enum Variant
    {
        BLUE(0, "blue"),
        GREEN(1, "green"),
        GRAY(2, "gray"),
        RED(3, "red"),
        YELLOW_BLUE(4, "yellow_blue"),
        PUFFIN(5, "puffin");

        public static final Variant[] VARIANTS = Arrays.stream(Variant.values()).sorted(Comparator.comparingInt(Variant::getId)).toArray(Variant[]::new);
        public final int id;
        public final String name;

        Variant(int id, String name)
        {
            this.id = id;
            this.name = name;
        }

        public int getId()
        {
            return this.id;
        }

        public String getName()
        {
            return this.name;
        }

        public static Variant byId(int id)
        {
            if (id < 0 || id >= VARIANTS.length)
            {
                id = 0;
            }

            return VARIANTS[id];
        }
    }

    @Override
    public boolean canBeLeashed(Player player)
    {
        return true;
    }
}
