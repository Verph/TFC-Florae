package tfcflorae.common.entities;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import net.dries007.tfc.common.blocks.plant.ITallPlant.Part;

import tfcflorae.common.TFCFTags;
import tfcflorae.common.blockentities.SilkmothNestBlockEntity;
import tfcflorae.common.blocks.devices.SilkmothNestBlock;
import tfcflorae.util.TFCFHelpers;

public class Silkmoth extends Animal implements FlyingAnimal
{
    public static final float FLAP_DEGREES_PER_TICK = 120.32113F;
    public static final int TICKS_PER_FLAP = Mth.ceil(1.4959966F);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Silkmoth.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Silkmoth.class, EntityDataSerializers.INT);
    private static final int FLAG_ROLL = 2;
    private static final int FLAG_HAS_STUNG = 4;
    private static final int FLAG_HAS_NECTAR = 8;
    private static final int STING_DEATH_COUNTDOWN = 1200;
    private static final int TICKS_BEFORE_GOING_TO_KNOWN_FLOWER = 2400;
    private static final int TICKS_WITHOUT_NECTAR_BEFORE_GOING_HOME = 3600;
    private static final int MIN_ATTACK_DIST = 4;
    private static final int MAX_CROPS_GROWABLE = 10;
    private static final int POISON_SECONDS_NORMAL = 10;
    private static final int POISON_SECONDS_HARD = 18;
    private static final int TOO_FAR_DISTANCE = 32;
    private static final int HIVE_CLOSE_ENOUGH_DISTANCE = 2;
    private static final int PATHFIND_TO_HIVE_WHEN_CLOSER_THAN = 16;
    private static final int HIVE_SEARCH_DISTANCE = 20;
    public static final String TAG_CROPS_GROWN_SINCE_POLLINATION = "crops_grown_since_pollination";
    public static final String TAG_CANNOT_ENTER_HIVE_TICKS = "cannot_enter_nest_ticks";
    public static final String TAG_TICKS_SINCE_POLLINATION = "ticks_since_pollination";
    public static final String TAG_HAS_STUNG = "has_stung";
    public static final String TAG_HAS_NECTAR = "has_nectar";
    public static final String TAG_FLOWER_POS = "target_pos";
    public static final String TAG_HIVE_POS = "nest_os";
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @Nullable
    private UUID persistentAngerTarget;
    private float rollAmount;
    private float rollAmountO;
    private int timeSinceSting;
    int ticksWithoutNectarSinceExitingNest;
    private int stayOutOfNestCountdown;
    private int numCropsGrownSincePollination;
    private static final int COOLDOWN_BEFORE_LOCATING_NEW_HIVE = 200;
    int remainingCooldownBeforeLocatingNewNest;
    private static final int COOLDOWN_BEFORE_LOCATING_NEW_FLOWER = 200;
    int remainingCooldownBeforeLocatingNewTarget = Mth.nextInt(this.random, 20, 60);
    @Nullable
    public BlockPos savedTargetPos;
    @Nullable
    BlockPos nestPos;
    Silkmoth.SilkmothPollinateGoal silkmothPollinateGoal;
    Silkmoth.SilkmothGoToNestGoal goToNestGoal;
    private Silkmoth.SilkmothGoToKnownTargetGoal goToKnownTargetGoal;
    private int underWaterTicks;

    public Silkmoth(EntityType<? extends Silkmoth> type, Level level)
    {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.lookControl = new Silkmoth.SilkmothLookControl(this);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0D).add(Attributes.FLYING_SPEED, (double)0.8F).add(Attributes.MOVEMENT_SPEED, (double)0.2F).add(Attributes.ATTACK_DAMAGE, 2.0D).add(Attributes.FOLLOW_RANGE, 100.0D);
    }

    public ResourceLocation getTextureLocation()
    {
        return TFCFHelpers.animalTexture("silk_moth");
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(1, new Silkmoth.SilkmothEnterNestGoal());
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(TFCFTags.Items.SILKMOTH_TEMPTATION_ITEMS), false));
        this.silkmothPollinateGoal = new Silkmoth.SilkmothPollinateGoal();
        this.goalSelector.addGoal(4, this.silkmothPollinateGoal);
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new Silkmoth.SilkmothLocateNestGoal());
        this.goToNestGoal = new Silkmoth.SilkmothGoToNestGoal();
        this.goalSelector.addGoal(5, this.goToNestGoal);
        this.goToKnownTargetGoal = new Silkmoth.SilkmothGoToKnownTargetGoal();
        this.goalSelector.addGoal(6, this.goToKnownTargetGoal);
        this.goalSelector.addGoal(7, new Silkmoth.SilkmothGrowCropGoal());
        this.goalSelector.addGoal(8, new Silkmoth.SilkmothWanderGoal());
        this.goalSelector.addGoal(9, new FloatGoal(this));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound)
    {
        super.addAdditionalSaveData(pCompound);
        if (this.hasNest())
        {
            pCompound.put(TAG_HIVE_POS, NbtUtils.writeBlockPos(this.getNestPos()));
        }
        if (this.hasSavedTargetPos())
        {
            pCompound.put(TAG_FLOWER_POS, NbtUtils.writeBlockPos(this.getSavedTargetPos()));
        }
        pCompound.putBoolean(TAG_HAS_NECTAR, this.hasNectar());
        pCompound.putInt(TAG_TICKS_SINCE_POLLINATION, this.ticksWithoutNectarSinceExitingNest);
        pCompound.putInt(TAG_CANNOT_ENTER_HIVE_TICKS, this.stayOutOfNestCountdown);
        pCompound.putInt(TAG_CROPS_GROWN_SINCE_POLLINATION, this.numCropsGrownSincePollination);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound)
    {
        this.nestPos = null;
        if (pCompound.contains(TAG_HIVE_POS))
        {
            this.nestPos = NbtUtils.readBlockPos(pCompound.getCompound(TAG_HIVE_POS));
        }

        this.savedTargetPos = null;
        if (pCompound.contains(TAG_FLOWER_POS))
        {
            this.savedTargetPos = NbtUtils.readBlockPos(pCompound.getCompound(TAG_FLOWER_POS));
        }

        super.readAdditionalSaveData(pCompound);
        this.setHasNectar(pCompound.getBoolean(TAG_HAS_NECTAR));
        this.ticksWithoutNectarSinceExitingNest = pCompound.getInt(TAG_TICKS_SINCE_POLLINATION);
        this.stayOutOfNestCountdown = pCompound.getInt(TAG_CANNOT_ENTER_HIVE_TICKS);
        this.numCropsGrownSincePollination = pCompound.getInt(TAG_CROPS_GROWN_SINCE_POLLINATION);
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }

    @Override
    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel)
    {
        return pLevel.getBlockState(pPos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel)
    {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel)
        {
            @Override
            public boolean isStableDestination(BlockPos p_27947_)
            {
                return !this.level.getBlockState(p_27947_.below()).isAir();
            }

            @Override
            public void tick()
            {
                if (!Silkmoth.this.silkmothPollinateGoal.isPollinating())
                {
                    super.tick();
                }
            }
        };

        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(false);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    public boolean hasNest()
    {
        return this.nestPos != null;
    }

    @Nullable
    public BlockPos getNestPos()
    {
        return this.nestPos;
    }

    @VisibleForDebug
    public GoalSelector getGoalSelector()
    {
        return this.goalSelector;
    }

    int getCropsGrownSincePollination()
    {
        return this.numCropsGrownSincePollination;
    }

    private void resetNumCropsGrownSincePollination()
    {
        this.numCropsGrownSincePollination = 0;
    }

    void incrementNumCropsGrownSincePollination()
    {
        ++this.numCropsGrownSincePollination;
    }

    boolean isNestValid()
    {
        if (!this.hasNest())
        {
            return false;
        }
        else
        {
            BlockEntity blockentity = this.level.getBlockEntity(this.nestPos);
            return (blockentity instanceof SilkmothNestBlockEntity nest && nest.getBlockState().getValue(SilkmothNestBlock.PART) == Part.UPPER);
        }
    }

    public boolean hasNectar()
    {
        return this.getFlag(8);
    }

    void setHasNectar(boolean bool)
    {
        if (bool)
        {
            this.resetTicksWithoutNectarSinceExitingNest();
        }
        this.setFlag(8, bool);
    }

    private boolean isRolling()
    {
        return this.getFlag(2);
    }

    private void setRolling(boolean bool)
    {
        this.setFlag(2, bool);
    }

    boolean isTooFarAway(BlockPos pos)
    {
        return !this.closerThan(pos, 32);
    }

    private void setFlag(int b1, boolean b2)
    {
        if (b2)
        {
            this.entityData.set(DATA_FLAGS_ID, (byte)(this.entityData.get(DATA_FLAGS_ID) | b1));
        }
        else
        {
            this.entityData.set(DATA_FLAGS_ID, (byte)(this.entityData.get(DATA_FLAGS_ID) & ~b1));
        }
    }

    private boolean getFlag(int flag)
    {
        return (this.entityData.get(DATA_FLAGS_ID) & flag) != 0;
    }

    boolean closerThan(BlockPos pos, int distance)
    {
        return pos.closerThan(this.blockPosition(), (double)distance);
    }


    void pathfindRandomlyTowards(BlockPos pPos)
    {
        Vec3 vec3 = Vec3.atBottomCenterOf(pPos);
        int i = 0;
        BlockPos blockpos = this.blockPosition();
        int j = (int)vec3.y - blockpos.getY();
        if (j > 2)
        {
            i = 4;
        }
        else if (j < -2)
        {
            i = -4;
        }

        int k = 6;
        int l = 8;
        int i1 = blockpos.distManhattan(pPos);
        if (i1 < 15)
        {
            k = i1 / 2;
            l = i1 / 2;
        }

        Vec3 vec31 = AirRandomPos.getPosTowards(this, k, l, i, vec3, (double)((float)Math.PI / 10F));
        if (vec31 != null)
        {
            this.navigation.setMaxVisitedNodesMultiplier(0.5F);
            this.navigation.moveTo(vec31.x, vec31.y, vec31.z, 1.0D);
        }
    }

    @Nullable
    public BlockPos getSavedTargetPos()
    {
        return this.savedTargetPos;
    }

    public boolean hasSavedTargetPos()
    {
        return this.savedTargetPos != null;
    }

    public void setSavedTargetPos(BlockPos pPos)
    {
        this.savedTargetPos = pPos;
    }

    public int getTravellingTicks()
    {
        return Math.max(this.goToNestGoal.travellingTicks, this.goToKnownTargetGoal.travellingTicks);
    }

    public List<BlockPos> getBlacklistedNests()
    {
        return this.goToNestGoal.blacklistedTargets;
    }

    private boolean isTiredOfLookingForNectar()
    {
        return this.ticksWithoutNectarSinceExitingNest > 3600;
    }

    boolean wantsToEnterNest()
    {
        if (this.stayOutOfNestCountdown <= 0 && !this.silkmothPollinateGoal.isPollinating() && this.getTarget() == null)
        {
            boolean flag = this.isTiredOfLookingForNectar() || this.level.isRaining() || this.level.isNight() || this.hasNectar();
            return flag && !this.isNestNearFire();
        }
        else
        {
            return false;
        }
    }

    public void setStayOutOfNestCountdown(int p_27916_)
    {
        this.stayOutOfNestCountdown = p_27916_;
    }

    public float getRollAmount(float p_27936_)
    {
        return Mth.lerp(p_27936_, this.rollAmountO, this.rollAmount);
    }

    private void updateRollAmount()
    {
        this.rollAmountO = this.rollAmount;
        if (this.isRolling())
        {
            this.rollAmount = Math.min(1.0F, this.rollAmount + 0.2F);
        }
        else
        {
            this.rollAmount = Math.max(0.0F, this.rollAmount - 0.24F);
        }
    }

    @Override
    protected void customServerAiStep()
    {
        if (this.isInWaterOrBubble())
        {
            ++this.underWaterTicks;
        }
        else
        {
            this.underWaterTicks = 0;
        }
        if (this.underWaterTicks > 20)
        {
            this.hurt(DamageSource.DROWN, 1.0F);
        }
        if (!this.hasNectar())
        {
            ++this.ticksWithoutNectarSinceExitingNest;
        }
    }

    public void resetTicksWithoutNectarSinceExitingNest()
    {
        this.ticksWithoutNectarSinceExitingNest = 0;
    }

    private boolean isNestNearFire()
    {
        if (this.nestPos == null)
        {
            return false;
        }
        else
        {
            BlockEntity blockentity = this.level.getBlockEntity(this.nestPos);
            return blockentity instanceof SilkmothNestBlockEntity && ((SilkmothNestBlockEntity)blockentity).isFireNearby();
        }
    }

    @Override
    public boolean isFood(ItemStack pStack)
    {
        return pStack.is(TFCFTags.Items.SILKMOTH_TEMPTATION_ITEMS);
    }

    boolean isTargetValid(BlockPos pPos)
    {
        return this.level.isLoaded(pPos) && this.level.getBlockState(pPos).is(TFCFTags.Blocks.SILKMOTH_TARGET_BLOCKS);
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock)
    {
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource)
    {
        return SoundEvents.BEE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.BEE_DEATH;
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    public Silkmoth getBreedOffspring(ServerLevel level, AgeableMob ageableMob)
    {
        return TFCFEntities.SILKMOTH.get().create(level);
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize)
    {
        return this.isBaby() ? pSize.height * 0.5F : pSize.height * 0.5F;
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource)
    {
        return false;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos)
    {
    }

    @Override
    public boolean isFlapping()
    {
        return this.isFlying() && this.tickCount % TICKS_PER_FLAP == 0;
    }

    public boolean isFlying()
    {
        return !this.onGround;
    }

    public void dropOffNectar()
    {
        this.setHasNectar(false);
        this.resetNumCropsGrownSincePollination();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount)
    {
        if (this.isInvulnerableTo(pSource))
        {
            return false;
        }
        else
        {
            if (!this.level.isClientSide)
            {
                this.silkmothPollinateGoal.stopPollinating();
            }
            return super.hurt(pSource, pAmount);
        }
    }

    @Override
    public MobType getMobType()
    {
        return MobType.ARTHROPOD;
    }

    @Override
    protected void jumpInLiquid(TagKey<Fluid> pFluidTag)
    {
        this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.01D, 0.0D));
    }

    @Override
    public Vec3 getLeashOffset()
    {
        return new Vec3(0.0D, (double)(0.5F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.2F));
    }

    private boolean doesNestHaveSpace(BlockPos pPos) 
    {
        BlockEntity blockentity = this.level.getBlockEntity(pPos);
        if (blockentity instanceof SilkmothNestBlockEntity)
        {
            return !((SilkmothNestBlockEntity)blockentity).isFull();
        }
        else
        {
            return false;
        }
    }

    public class SilkmothGoToNestGoal extends Silkmoth.BaseSilkmothGoal
    {
        public static final int MAX_TRAVELLING_TICKS = 600;
        int travellingTicks = Silkmoth.this.level.random.nextInt(10);
        private static final int MAX_BLACKLISTED_TARGETS = 3;
        final List<BlockPos> blacklistedTargets = Lists.newArrayList();
        @Nullable private Path lastPath;
        private static final int TICKS_BEFORE_HIVE_DROP = 60;
        private int ticksStuck;

        SilkmothGoToNestGoal()
        {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canSilkmothUse()
        {
            return Silkmoth.this.nestPos != null && !Silkmoth.this.hasRestriction() && Silkmoth.this.wantsToEnterNest() && !this.hasReachedTarget(Silkmoth.this.nestPos) && Silkmoth.this.level.getBlockState(Silkmoth.this.nestPos).is(BlockTags.BEEHIVES);
        }

        public boolean canSilkmothContinueToUse()
        {
            return this.canSilkmothUse();
        }

        @Override
        public void start()
        {
            this.travellingTicks = 0;
            this.ticksStuck = 0;
            super.start();
        }

        @Override
        public void stop()
        {
            this.travellingTicks = 0;
            this.ticksStuck = 0;
            Silkmoth.this.navigation.stop();
            Silkmoth.this.navigation.resetMaxVisitedNodesMultiplier();
        }

        @Override
        public void tick()
        {
            if (Silkmoth.this.nestPos != null)
            {
                ++this.travellingTicks;
                if (this.travellingTicks > this.adjustedTickDelay(600))
                {
                    this.dropAndBlacklistNest();
                }
                else if (!Silkmoth.this.navigation.isInProgress())
                {
                    if (!Silkmoth.this.closerThan(Silkmoth.this.nestPos, 16))
                    {
                        if (Silkmoth.this.isTooFarAway(Silkmoth.this.nestPos))
                        {
                            this.dropNest();
                        }
                        else
                        {
                            Silkmoth.this.pathfindRandomlyTowards(Silkmoth.this.nestPos);
                        }
                    }
                    else
                    {
                        boolean flag = this.pathfindDirectlyTowards(Silkmoth.this.nestPos);
                        if (!flag)
                        {
                            this.dropAndBlacklistNest();
                        }
                        else if (this.lastPath != null && Silkmoth.this.navigation.getPath().sameAs(this.lastPath))
                        {
                            ++this.ticksStuck;
                            if (this.ticksStuck > 60)
                            {
                                this.dropNest();
                                this.ticksStuck = 0;
                            }
                        }
                        else
                        {
                            this.lastPath = Silkmoth.this.navigation.getPath();
                        }
                    }
                }
            }
        }

        private boolean pathfindDirectlyTowards(BlockPos pPos)
        {
            Silkmoth.this.navigation.setMaxVisitedNodesMultiplier(10.0F);
            Silkmoth.this.navigation.moveTo((double)pPos.getX(), (double)pPos.getY(), (double)pPos.getZ(), 1.0D);
            return Silkmoth.this.navigation.getPath() != null && Silkmoth.this.navigation.getPath().canReach();
        }

        boolean isTargetBlacklisted(BlockPos pPos)
        {
            return this.blacklistedTargets.contains(pPos);
        }

        private void blacklistTarget(BlockPos pPos)
        {
            this.blacklistedTargets.add(pPos);
            while(this.blacklistedTargets.size() > 3)
            {
                this.blacklistedTargets.remove(0);
            }
        }

        void clearBlacklist()
        {
            this.blacklistedTargets.clear();
        }

        private void dropAndBlacklistNest()
        {
            if (Silkmoth.this.nestPos != null)
            {
                this.blacklistTarget(Silkmoth.this.nestPos);
            }
            this.dropNest();
        }

        private void dropNest()
        {
            Silkmoth.this.nestPos = null;
            Silkmoth.this.remainingCooldownBeforeLocatingNewNest = 200;
        }

        private boolean hasReachedTarget(BlockPos pPos)
        {
            if (Silkmoth.this.closerThan(pPos, 2))
            {
                return true;
            }
            else
            {
                Path path = Silkmoth.this.navigation.getPath();
                return path != null && path.getTarget().equals(pPos) && path.canReach() && path.isDone();
            }
        }
    }

    class SilkmothEnterNestGoal extends Silkmoth.BaseSilkmothGoal
    {
        public boolean canSilkmothUse()
        {
            if (Silkmoth.this.hasNest() && Silkmoth.this.wantsToEnterNest() && Silkmoth.this.nestPos.closerToCenterThan(Silkmoth.this.position(), 2.0D))
            {
                BlockEntity blockEntity = Silkmoth.this.level.getBlockEntity(Silkmoth.this.nestPos);
                if (blockEntity instanceof SilkmothNestBlockEntity)
                {
                    SilkmothNestBlockEntity nestBlockEntity = (SilkmothNestBlockEntity)blockEntity;
                    if (!nestBlockEntity.isFull())
                    {
                        return true;
                    }
                    Silkmoth.this.nestPos = null;
                }
            }
            return false;
        }

        public boolean canSilkmothContinueToUse()
        {
            return false;
        }

        @Override
        public void start()
        {
            BlockEntity blockEntity = Silkmoth.this.level.getBlockEntity(Silkmoth.this.nestPos);
            if (blockEntity instanceof SilkmothNestBlockEntity)
            {
                SilkmothNestBlockEntity nestBlockEntity = (SilkmothNestBlockEntity)blockEntity;
                nestBlockEntity.addOccupant(Silkmoth.this, Silkmoth.this.hasNectar());
            }
        }
    }

    abstract class BaseSilkmothGoal extends Goal
    {
        public abstract boolean canSilkmothUse();
        public abstract boolean canSilkmothContinueToUse();

        public boolean canUse()
        {
            return this.canSilkmothUse();
        }

        @Override
        public boolean canContinueToUse()
        {
            return this.canSilkmothContinueToUse();
        }
    }

    class SilkmothPollinateGoal extends Silkmoth.BaseSilkmothGoal
    {
        private static final int MIN_POLLINATION_TICKS = 400;
        private static final int MIN_FIND_FLOWER_RETRY_COOLDOWN = 20;
        private static final int MAX_FIND_FLOWER_RETRY_COOLDOWN = 60;
        private final Predicate<BlockState> VALID_POLLINATION_BLOCKS = (state) ->
        {
            if (state.is(TFCFTags.Blocks.SILKMOTH_TARGET_BLOCKS))
            {
                return true;
            }
            else
            {
                return false;
            }
        };

        private static final double ARRIVAL_THRESHOLD = 0.1D;
        private static final int POSITION_CHANGE_CHANCE = 25;
        private static final float SPEED_MODIFIER = 0.35F;
        private static final float HOVER_HEIGHT_WITHIN_FLOWER = 0.6F;
        private static final float HOVER_POS_OFFSET = 0.33333334F;
        private int successfulPollinatingTicks;
        private int lastSoundPlayedTick;
        private boolean pollinating;
        @Nullable
        private Vec3 hoverPos;
        private int pollinatingTicks;
        private static final int MAX_POLLINATING_TICKS = 600;

        SilkmothPollinateGoal()
        {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canSilkmothUse()
        {
            if (Silkmoth.this.remainingCooldownBeforeLocatingNewTarget > 0)
            {
                return false;
            }
            else if (Silkmoth.this.hasNectar())
            {
                return false;
            }
            else if (Silkmoth.this.level.isRaining())
            {
                return false;
            }
            else
            {
                Optional<BlockPos> optional = this.findNearbyTarget();
                if (optional.isPresent())
                {
                    Silkmoth.this.savedTargetPos = optional.get();
                    Silkmoth.this.navigation.moveTo((double)Silkmoth.this.savedTargetPos.getX() + 0.5D, (double)Silkmoth.this.savedTargetPos.getY() + 0.5D, (double)Silkmoth.this.savedTargetPos.getZ() + 0.5D, (double)1.2F);
                    return true;
                }
                else
                {
                    Silkmoth.this.remainingCooldownBeforeLocatingNewTarget = Mth.nextInt(Silkmoth.this.random, 20, 60);
                    return false;
                }
            }
        }

        public boolean canSilkmothContinueToUse()
        {
            if (!this.pollinating)
            {
                return false;
            }
            else if (!Silkmoth.this.hasSavedTargetPos())
            {
                return false;
            }
            else if (Silkmoth.this.level.isRaining())
            {
                return false;
            }
            else if (this.hasPollinatedLongEnough())
            {
                return Silkmoth.this.random.nextFloat() < 0.2F;
            }
            else if (Silkmoth.this.tickCount % 20 == 0 && !Silkmoth.this.isTargetValid(Silkmoth.this.savedTargetPos))
            {
                Silkmoth.this.savedTargetPos = null;
                return false;
            }
            else
            {
                return true;
            }
        }

        private boolean hasPollinatedLongEnough()
        {
            return this.successfulPollinatingTicks > 400;
        }

        boolean isPollinating()
        {
            return this.pollinating;
        }

        void stopPollinating()
        {
            this.pollinating = false;
        }

        @Override
        public void start()
        {
            this.successfulPollinatingTicks = 0;
            this.pollinatingTicks = 0;
            this.lastSoundPlayedTick = 0;
            this.pollinating = true;
            Silkmoth.this.resetTicksWithoutNectarSinceExitingNest();
        }

        @Override
        public void stop()
        {
            if (this.hasPollinatedLongEnough())
            {
                Silkmoth.this.setHasNectar(true);
            }

            this.pollinating = false;
            Silkmoth.this.navigation.stop();
            Silkmoth.this.remainingCooldownBeforeLocatingNewTarget = 200;
        }

        @Override
        public boolean requiresUpdateEveryTick()
        {
            return true;
        }

        @Override
        public void tick()
        {
            ++this.pollinatingTicks;
            if (this.pollinatingTicks > 600)
            {
                Silkmoth.this.savedTargetPos = null;
            }
            else
            {
                Vec3 vec3 = Vec3.atBottomCenterOf(Silkmoth.this.savedTargetPos).add(0.0D, (double)0.6F, 0.0D);
                if (vec3.distanceTo(Silkmoth.this.position()) > 1.0D)
                {
                    this.hoverPos = vec3;
                    this.setWantedPos();
                }
                else
                {
                    if (this.hoverPos == null)
                    {
                        this.hoverPos = vec3;
                    }

                    boolean flag = Silkmoth.this.position().distanceTo(this.hoverPos) <= 0.1D;
                    boolean flag1 = true;
                    if (!flag && this.pollinatingTicks > 600)
                    {
                        Silkmoth.this.savedTargetPos = null;
                    }
                    else
                    {
                        if (flag)
                        {
                            boolean flag2 = Silkmoth.this.random.nextInt(25) == 0;
                            if (flag2)
                            {
                                this.hoverPos = new Vec3(vec3.x() + (double)this.getOffset(), vec3.y(), vec3.z() + (double)this.getOffset());
                                Silkmoth.this.navigation.stop();
                            }
                            else
                            {
                                flag1 = false;
                            }
                            Silkmoth.this.getLookControl().setLookAt(vec3.x(), vec3.y(), vec3.z());
                        }
                        if (flag1)
                        {
                            this.setWantedPos();
                        }

                        ++this.successfulPollinatingTicks;

                        if (Silkmoth.this.random.nextFloat() < 0.05F && this.successfulPollinatingTicks > this.lastSoundPlayedTick + 60)
                        {
                            this.lastSoundPlayedTick = this.successfulPollinatingTicks;
                            Silkmoth.this.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);
                        }
                    }
                }
            }
        }

        private void setWantedPos()
        {
            Silkmoth.this.getMoveControl().setWantedPosition(this.hoverPos.x(), this.hoverPos.y(), this.hoverPos.z(), (double)0.35F);
        }

        private float getOffset()
        {
            return (Silkmoth.this.random.nextFloat() * 2.0F - 1.0F) * 0.33333334F;
        }

        private Optional<BlockPos> findNearbyTarget()
        {
            return this.findNearestBlock(this.VALID_POLLINATION_BLOCKS, 5.0D);
        }

        private Optional<BlockPos> findNearestBlock(Predicate<BlockState> p_28076_, double p_28077_)
        {
            BlockPos blockpos = Silkmoth.this.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            for(int i = 0; (double)i <= p_28077_; i = i > 0 ? -i : 1 - i)
            {
                for(int j = 0; (double)j < p_28077_; ++j)
                {
                    for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k)
                    {
                        for(int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l)
                        {
                            blockpos$mutableblockpos.setWithOffset(blockpos, k, i - 1, l);
                            if (blockpos.closerThan(blockpos$mutableblockpos, p_28077_) && p_28076_.test(Silkmoth.this.level.getBlockState(blockpos$mutableblockpos)))
                            {
                                return Optional.of(blockpos$mutableblockpos);
                            }
                        }
                    }
                }
            }
            return Optional.empty();
        }
    }

    class SilkmothLocateNestGoal extends Silkmoth.BaseSilkmothGoal
    {
        public boolean canSilkmothUse()
        {
            return Silkmoth.this.remainingCooldownBeforeLocatingNewNest == 0 && !Silkmoth.this.hasNest() && Silkmoth.this.wantsToEnterNest();
        }

        public boolean canSilkmothContinueToUse()
        {
            return false;
        }

        @Override
        public void start()
        {
            Silkmoth.this.remainingCooldownBeforeLocatingNewNest = 200;
            List<BlockPos> list = this.findNearbyNestsWithSpace();
            if (!list.isEmpty())
            {
                for(BlockPos blockpos : list)
                {
                    if (!Silkmoth.this.goToNestGoal.isTargetBlacklisted(blockpos))
                    {
                    Silkmoth.this.nestPos = blockpos;
                    return;
                    }
                }

                Silkmoth.this.goToNestGoal.clearBlacklist();
                Silkmoth.this.nestPos = list.get(0);
            }
        }

        private List<BlockPos> findNearbyNestsWithSpace()
        {
            BlockPos blockpos = Silkmoth.this.blockPosition();
            PoiManager poimanager = ((ServerLevel)Silkmoth.this.level).getPoiManager();
            Stream<PoiRecord> stream = poimanager.getInRange((p_28045_) -> {
                return p_28045_ == PoiType.BEEHIVE || p_28045_ == PoiType.BEE_NEST;
            }, blockpos, 20, PoiManager.Occupancy.ANY);
            return stream.map(PoiRecord::getPos).filter(Silkmoth.this::doesNestHaveSpace).sorted(Comparator.comparingDouble((p_148811_) -> {
                return p_148811_.distSqr(blockpos);
            })).collect(Collectors.toList());
        }
    }

    public class SilkmothGoToKnownTargetGoal extends Silkmoth.BaseSilkmothGoal
    {
        private static final int MAX_TRAVELLING_TICKS = 600;
        int travellingTicks = Silkmoth.this.level.random.nextInt(10);

        SilkmothGoToKnownTargetGoal()
        {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canSilkmothUse()
        {
            return Silkmoth.this.savedTargetPos != null && !Silkmoth.this.hasRestriction() && this.wantsToGoToKnownTarget() && Silkmoth.this.isTargetValid(Silkmoth.this.savedTargetPos) && !Silkmoth.this.closerThan(Silkmoth.this.savedTargetPos, 2);
        }

        public boolean canSilkmothContinueToUse()
        {
            return this.canSilkmothUse();
        }

        @Override
        public void start()
        {
            this.travellingTicks = 0;
            super.start();
        }

        @Override
        public void stop()
        {
            this.travellingTicks = 0;
            Silkmoth.this.navigation.stop();
            Silkmoth.this.navigation.resetMaxVisitedNodesMultiplier();
        }

        @Override
        public void tick()
        {
            if (Silkmoth.this.savedTargetPos != null)
            {
                ++this.travellingTicks;
                if (this.travellingTicks > this.adjustedTickDelay(MAX_TRAVELLING_TICKS))
                {
                    Silkmoth.this.savedTargetPos = null;
                }
                else if (!Silkmoth.this.navigation.isInProgress())
                {
                    if (Silkmoth.this.isTooFarAway(Silkmoth.this.savedTargetPos))
                    {
                        Silkmoth.this.savedTargetPos = null;
                    }
                    else
                    {
                        Silkmoth.this.pathfindRandomlyTowards(Silkmoth.this.savedTargetPos);
                    }
                }
            }
        }

        private boolean wantsToGoToKnownTarget()
        {
            return Silkmoth.this.ticksWithoutNectarSinceExitingNest > 2400;
        }
    }

    class SilkmothGrowCropGoal extends Silkmoth.BaseSilkmothGoal
    {
        static final int GROW_CHANCE = 30;

        public boolean canSilkmothUse()
        {
            if (Silkmoth.this.getCropsGrownSincePollination() >= 10)
            {
                return false;
            }
            else if (Silkmoth.this.random.nextFloat() < 0.3F)
            {
                return false;
            }
            else
            {
                return Silkmoth.this.hasNectar() && Silkmoth.this.isNestValid();
            }
        }

        public boolean canSilkmothContinueToUse()
        {
            return this.canSilkmothUse();
        }

        @Override
        public void tick()
        {
            if (Silkmoth.this.random.nextInt(this.adjustedTickDelay(30)) == 0)
            {
                for(int i = 1; i <= 2; ++i)
                {
                    BlockPos blockpos = Silkmoth.this.blockPosition().below(i);
                    BlockState blockstate = Silkmoth.this.level.getBlockState(blockpos);
                    Block block = blockstate.getBlock();
                    boolean flag = false;
                    IntegerProperty integerproperty = null;
                    if (blockstate.is(BlockTags.BEE_GROWABLES) || block instanceof net.dries007.tfc.common.blocks.crop.CropBlock)
                    {
                        if (block instanceof CropBlock)
                        {
                            CropBlock cropblock = (CropBlock)block;
                            if (!cropblock.isMaxAge(blockstate))
                            {
                                flag = true;
                                integerproperty = cropblock.getAgeProperty();
                            }
                        }
                        else if (block instanceof StemBlock)
                        {
                            int j = blockstate.getValue(StemBlock.AGE);
                            if (j < 7)
                            {
                                flag = true;
                                integerproperty = StemBlock.AGE;
                            }
                        }
                        else if (blockstate.is(Blocks.SWEET_BERRY_BUSH))
                        {
                            int k = blockstate.getValue(SweetBerryBushBlock.AGE);
                            if (k < 3)
                            {
                                flag = true;
                                integerproperty = SweetBerryBushBlock.AGE;
                            }
                        }
                        else if (blockstate.is(Blocks.CAVE_VINES) || blockstate.is(Blocks.CAVE_VINES_PLANT))
                        {
                            ((BonemealableBlock)blockstate.getBlock()).performBonemeal((ServerLevel)Silkmoth.this.level, Silkmoth.this.random, blockpos, blockstate);
                        }

                        if (flag)
                        {
                            Silkmoth.this.level.levelEvent(2005, blockpos, 0);
                            Silkmoth.this.level.setBlockAndUpdate(blockpos, blockstate.setValue(integerproperty, Integer.valueOf(blockstate.getValue(integerproperty) + 1)));
                            Silkmoth.this.incrementNumCropsGrownSincePollination();
                        }
                    }
                }
            }
        }
    }

    class SilkmothWanderGoal extends Goal
    {
        private static final int WANDER_THRESHOLD = 22;

        SilkmothWanderGoal()
        {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse()
        {
            return Silkmoth.this.navigation.isDone() && Silkmoth.this.random.nextInt(10) == 0;
        }

        @Override
        public boolean canContinueToUse()
        {
            return Silkmoth.this.navigation.isInProgress();
        }

        @Override
        public void start()
        {
            Vec3 vec3 = this.findPos();
            if (vec3 != null)
            {
                Silkmoth.this.navigation.moveTo(Silkmoth.this.navigation.createPath(new BlockPos(vec3), 1), 1.0D);
            }
        }

        @Nullable
        private Vec3 findPos()
        {
            Vec3 vec3;
            if (Silkmoth.this.isNestValid() && !Silkmoth.this.closerThan(Silkmoth.this.nestPos, WANDER_THRESHOLD))
            {
                Vec3 vec31 = Vec3.atCenterOf(Silkmoth.this.nestPos);
                vec3 = vec31.subtract(Silkmoth.this.position()).normalize();
            }
            else
            {
                vec3 = Silkmoth.this.getViewVector(0.0F);
            }

            int i = 8;
            Vec3 vec32 = HoverRandomPos.getPos(Silkmoth.this, 8, 7, vec3.x, vec3.z, ((float)Math.PI / 2F), 3, 1);
            return vec32 != null ? vec32 : AirAndWaterRandomPos.getPos(Silkmoth.this, 8, 4, -2, vec3.x, vec3.z, (double)((float)Math.PI / 2F));
        }
    }

    class SilkmothLookControl extends LookControl
    {
        SilkmothLookControl(Mob entity)
        {
            super(entity);
        }

        @Override
        protected boolean resetXRotOnTick()
        {
            return !Silkmoth.this.silkmothPollinateGoal.isPollinating();
        }
    }
}
