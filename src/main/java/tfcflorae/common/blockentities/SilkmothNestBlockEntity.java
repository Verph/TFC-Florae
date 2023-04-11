package tfcflorae.common.blockentities;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blockentities.TFCBlockEntity;

import tfcflorae.common.blocks.devices.SilkmothNestBlock;
import tfcflorae.common.entities.Silkmoth;

public class SilkmothNestBlockEntity extends TFCBlockEntity
{
    public static final String TAG_TARGET_POS = "TargetPos";
    public static final String MIN_OCCUPATION_TICKS = "MinOccupationTicks";
    public static final String ENTITY_DATA = "EntityData";
    public static final String TICKS_IN_NEST = "TicksInNest";
    public static final String HAS_NECTAR = "HasNectar";
    public static final String MOTHS = "Moths";
    public static final List<String> IGNORED_MOTH_TAGS = Arrays.asList("Air", "ArmorDropChances", "ArmorItems", "Brain", "CanPickUpLoot", "DeathTime", "FallDistance", "FallFlying", "Fire", "HandDropChances", "HandItems", "HurtByTimestamp", "HurtTime", "LeftHanded", "Motion", "NoGravity", "OnGround", "PortalCooldown", "Pos", "Rotation", "CannotEnterHiveTicks", "TicksSincePollination", "CropsGrownSincePollination", "HivePos", "Passengers", "Leash", "UUID");
    public static final int MAX_OCCUPANTS = 3;
    public static final int MIN_TICKS_BEFORE_REENTERING_NEST = 400;
    public static final int MIN_OCCUPATION_TICKS_NECTAR = 2400;
    public static final int MIN_OCCUPATION_TICKS_NECTARLESS = 600;
    public final List<SilkmothNestBlockEntity.MothData> stored = Lists.newArrayList();
    @Nullable public BlockPos savedTargetPos;

    public SilkmothNestBlockEntity(BlockPos pos, BlockState state)
    {
        super(TFCFBlockEntities.SILKMOTH_NEST.get(), pos, state);
    }

    @Override
    public void loadAdditional(CompoundTag tag)
    {
        super.loadAdditional(tag);
        this.stored.clear();
        ListTag listtag = tag.getList(MOTHS, 10);
        for(int i = 0; i < listtag.size(); ++i) 
        {
            CompoundTag compoundtag = listtag.getCompound(i);
            SilkmothNestBlockEntity.MothData SilkmothNestBlockEntity$mothdata = new SilkmothNestBlockEntity.MothData(compoundtag.getCompound(ENTITY_DATA), compoundtag.getInt(TICKS_IN_NEST), compoundtag.getInt(MIN_OCCUPATION_TICKS));
            this.stored.add(SilkmothNestBlockEntity$mothdata);
        }
        this.savedTargetPos = null;
        if (tag.contains("TargetPos"))
        {
            this.savedTargetPos = NbtUtils.readBlockPos(tag.getCompound("TargetPos"));
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put(MOTHS, this.writeMoths());
        if (this.hasSavedTargetPos())
        {
            tag.put("TargetPos", NbtUtils.writeBlockPos(this.savedTargetPos));
        }
    }

    public ListTag writeMoths()
    {
        ListTag listtag = new ListTag();
        for(SilkmothNestBlockEntity.MothData SilkmothNestBlockEntity$mothdata : this.stored)
        {
            CompoundTag compoundtag = SilkmothNestBlockEntity$mothdata.entityData.copy();
            compoundtag.remove("UUID");
            CompoundTag compoundtag1 = new CompoundTag();
            compoundtag1.put(ENTITY_DATA, compoundtag);
            compoundtag1.putInt(TICKS_IN_NEST, SilkmothNestBlockEntity$mothdata.ticksInNest);
            compoundtag1.putInt(MIN_OCCUPATION_TICKS, SilkmothNestBlockEntity$mothdata.minOccupationTicks);
            listtag.add(compoundtag1);
        }
        return listtag;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SilkmothNestBlockEntity blockEntity)
    {
        tickOccupants(level, pos, state, blockEntity.stored, blockEntity.savedTargetPos);
        if (!blockEntity.stored.isEmpty() && level.getRandom().nextDouble() < 0.005D)
        {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY();
            double d2 = (double)pos.getZ() + 0.5D;
            level.playSound((Player)null, d0, d1, d2, SoundEvents.BEEHIVE_WORK, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    static void removeIgnoredMothTags(CompoundTag tag)
    {
        for(String s : IGNORED_MOTH_TAGS)
        {
            tag.remove(s);
        }
    }

    public static void setMothReleaseData(int age, Silkmoth mothEntity)
    {
        int i = mothEntity.getAge();
        if (i < 0)
        {
            mothEntity.setAge(Math.min(0, i + age));
        }
        else if (i > 0)
        {
            mothEntity.setAge(Math.max(0, i - age));
        }
        mothEntity.setInLoveTime(Math.max(0, mothEntity.getInLoveTime() - age));
    }

    public boolean hasSavedTargetPos()
    {
        return this.savedTargetPos != null;
    }

    public static void tickOccupants(Level level, BlockPos pos, BlockState state, List<SilkmothNestBlockEntity.MothData> list, @Nullable BlockPos pos1)
    {
        boolean flag = false;

        SilkmothNestBlockEntity.MothData SilkmothNestBlockEntity$mothdata;
        for(Iterator<SilkmothNestBlockEntity.MothData> iterator = list.iterator(); iterator.hasNext(); ++SilkmothNestBlockEntity$mothdata.ticksInNest)
        {
            SilkmothNestBlockEntity$mothdata = iterator.next();
            if (SilkmothNestBlockEntity$mothdata.ticksInNest > SilkmothNestBlockEntity$mothdata.minOccupationTicks)
            {
                SilkmothNestBlockEntity.MothReleaseStatus SilkmothNestBlockEntity$MothReleaseStatus = SilkmothNestBlockEntity$mothdata.entityData.getBoolean("HasNectar") ? SilkmothNestBlockEntity.MothReleaseStatus.HONEY_DELIVERED : SilkmothNestBlockEntity.MothReleaseStatus.MOTH_RELEASED;
                if (releaseOccupant(level, pos, state, SilkmothNestBlockEntity$mothdata, (List<Entity>)null, SilkmothNestBlockEntity$MothReleaseStatus, pos1))
                {
                    flag = true;
                    iterator.remove();
                }
            }
        }
        if (flag)
        {
            setChanged(level, pos, state);
        }
    }

    public static boolean releaseOccupant(Level level, BlockPos pos, BlockState state, SilkmothNestBlockEntity.MothData blockEntity, @Nullable List<Entity> listEntity, SilkmothNestBlockEntity.MothReleaseStatus releaseStatus, @Nullable BlockPos pos1)
    {
        if ((level.isNight() || level.isRaining()) && releaseStatus != SilkmothNestBlockEntity.MothReleaseStatus.EMERGENCY)
        {
            return false;
        }
        else
        {
            CompoundTag compoundtag = blockEntity.entityData.copy();
            removeIgnoredMothTags(compoundtag);
            compoundtag.put("HivePos", NbtUtils.writeBlockPos(pos));
            compoundtag.putBoolean("NoGravity", true);
            Direction direction = state.getValue(SilkmothNestBlock.FACING);
            BlockPos blockpos = pos.relative(direction);
            boolean flag = !level.getBlockState(blockpos).getCollisionShape(level, blockpos).isEmpty();
            if (flag && releaseStatus != SilkmothNestBlockEntity.MothReleaseStatus.EMERGENCY)
            {
                return false;
            }
            else
            {
                Entity entity = EntityType.loadEntityRecursive(compoundtag, level, (p_58740_) -> {
                    return p_58740_;
                });
                if (entity != null)
                {
                    if (!entity.getType().is(EntityTypeTags.BEEHIVE_INHABITORS))
                    {
                        return false;
                    }
                    else
                    {
                        if (entity instanceof Silkmoth)
                        {
                            Silkmoth moth = (Silkmoth)entity;
                            if (pos1 != null && !moth.hasSavedTargetPos() && level.random.nextFloat() < 0.9F)
                            {
                                moth.setSavedTargetPos(pos1);
                            }
                            if (releaseStatus == SilkmothNestBlockEntity.MothReleaseStatus.HONEY_DELIVERED)
                            {
                                moth.dropOffNectar();
                                if (state.is(BlockTags.BEEHIVES, (stateBase) -> {
                                    return stateBase.hasProperty(SilkmothNestBlock.SILK_LEVEL);
                                }))
                                {
                                    int i = getSilkLevel(state);
                                    if (i < 5)
                                    {
                                        int j = level.random.nextInt(100) == 0 ? 2 : 1;
                                        if (i + j > 5)
                                        {
                                            --j;
                                        }
                                        level.setBlockAndUpdate(pos, state.setValue(SilkmothNestBlock.SILK_LEVEL, Integer.valueOf(i + j)));
                                    }
                                }
                            }
                            setMothReleaseData(blockEntity.ticksInNest, moth);
                            if (listEntity != null)
                            {
                                listEntity.add(moth);
                            }
                            float f = entity.getBbWidth();
                            double d3 = flag ? 0.0D : 0.55D + (double)(f / 2.0F);
                            double d0 = (double)pos.getX() + 0.5D + d3 * (double)direction.getStepX();
                            double d1 = (double)pos.getY() + 0.5D - (double)(entity.getBbHeight() / 2.0F);
                            double d2 = (double)pos.getZ() + 0.5D + d3 * (double)direction.getStepZ();
                            entity.moveTo(d0, d1, d2, entity.getYRot(), entity.getXRot());
                        }
                        level.playSound((Player)null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
                        return level.addFreshEntity(entity);
                    }
                }
                else
                {
                    return false;
                }
            }
        }
    }

    public void addOccupantWithPresetTicks(Entity entity, boolean minOccupationTime, int ticksInNest)
    {
        if (this.stored.size() < 3)
        {
            entity.stopRiding();
            entity.ejectPassengers();
            CompoundTag compoundtag = new CompoundTag();
            entity.save(compoundtag);
            this.storeBee(compoundtag, ticksInNest, minOccupationTime);
            if (this.level != null)
            {
                if (entity instanceof Silkmoth)
                {
                    Silkmoth moth = (Silkmoth)entity;
                    if (moth.hasSavedTargetPos() && (!this.hasSavedTargetPos() || this.level.random.nextBoolean()))
                    {
                        this.savedTargetPos = moth.getSavedTargetPos();
                    }
                }
                BlockPos blockpos = this.getBlockPos();
                this.level.playSound((Player)null, (double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            entity.discard();
            super.setChanged();
        }
    }

    public void storeBee(CompoundTag tag, int ticksInNest, boolean minOccupationTicks)
    {
        this.stored.add(new SilkmothNestBlockEntity.MothData(tag, ticksInNest, minOccupationTicks ? MIN_OCCUPATION_TICKS_NECTAR : MIN_OCCUPATION_TICKS_NECTARLESS));
    }

    @Override
    public void setChanged()
    {
        if (this.isFireNearby())
        {
            this.emptyAllLivingFromNest((Player)null, this.level.getBlockState(this.getBlockPos()), SilkmothNestBlockEntity.MothReleaseStatus.EMERGENCY);
        }
        super.setChanged();
    }

    public boolean isFireNearby()
    {
        if (this.level == null)
        {
            return false;
        }
        else
        {
            for(BlockPos blockpos : BlockPos.betweenClosed(this.worldPosition.offset(-1, -1, -1), this.worldPosition.offset(1, 1, 1)))
            {
                if (this.level.getBlockState(blockpos).getBlock() instanceof FireBlock)
                {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isEmpty()
    {
        return this.stored.isEmpty();
    }

    public boolean isFull()
    {
        return this.stored.size() == 3;
    }

    public void emptyAllLivingFromNest(@Nullable Player player, BlockState state, SilkmothNestBlockEntity.MothReleaseStatus releaseStatus)
    {
        List<Entity> list = this.releaseAllOccupants(state, releaseStatus);
        if (player != null)
        {
            for(Entity entity : list)
            {
                if (entity instanceof Silkmoth)
                {
                    Silkmoth moth = (Silkmoth)entity;
                    if (player.position().distanceToSqr(entity.position()) <= 16.0D)
                    {
                        if (!this.isSedated())
                        {
                            moth.setTarget(player);
                        }
                        else
                        {
                            moth.setStayOutOfNestCountdown(MIN_TICKS_BEFORE_REENTERING_NEST);
                        }
                    }
                }
            }
        }
    }

    public List<Entity> releaseAllOccupants(BlockState state, SilkmothNestBlockEntity.MothReleaseStatus releaseStatus)
    {
        List<Entity> list = Lists.newArrayList();
        this.stored.removeIf((p_58766_) -> {
            return releaseOccupant(this.level, this.worldPosition, state, p_58766_, list, releaseStatus, this.savedTargetPos);
        });
        if (!list.isEmpty())
        {
            super.setChanged();
        }
        return list;
    }

    public void addOccupant(Entity entity, boolean minOccupationTime)
    {
        this.addOccupantWithPresetTicks(entity, minOccupationTime, 0);
    }

    public int getOccupantCount()
    {
        return this.stored.size();
    }

    public static int getSilkLevel(BlockState state)
    {
        return state.getValue(SilkmothNestBlock.SILK_LEVEL);
    }

    public boolean isSedated()
    {
        return CampfireBlock.isSmokeyPos(this.level, this.getBlockPos());
    }

    public static class MothData
    {
        final CompoundTag entityData;
        int ticksInNest;
        final int minOccupationTicks;

        MothData(CompoundTag entityData, int ticksInNest, int minOccupationTicks)
        {
            SilkmothNestBlockEntity.removeIgnoredMothTags(entityData);
            this.entityData = entityData;
            this.ticksInNest = ticksInNest;
            this.minOccupationTicks = minOccupationTicks;
        }
    }

    public static enum MothReleaseStatus
    {
        HONEY_DELIVERED,
        MOTH_RELEASED,
        EMERGENCY;
    }
}
