package tfcflorae.common.entities;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;

public class Silkmoth extends Bee
{
    @Nullable BlockPos savedTargetPos;
    @Nullable BlockPos hivePos;

    public Silkmoth(EntityType<? extends Bee> type, Level level)
    {
        super(type, level);
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

    public void setSavedTargetPos(BlockPos pos)
    {
        this.savedTargetPos = pos;
    }
}
