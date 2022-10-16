package tfcflorae.common.blocks.plant;

import java.util.Locale;

import net.minecraft.util.StringRepresentable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

public interface TFCFITallPlant
{
    default Part getPlantPart(BlockGetter level, BlockPos pos)
    {
        if (level.getBlockState(pos.below()).getBlock() != this && level.getBlockState(pos.above()).getBlock() == this)
        {
            return Part.LOWER;
        }
        if (level.getBlockState(pos.below()).getBlock() == this && level.getBlockState(pos.above()).getBlock() == this)
        {
            return Part.MIDDLE;
        }
        if (level.getBlockState(pos.below()).getBlock() == this && level.getBlockState(pos.above()).getBlock() != this)
        {
            return Part.UPPER;
        }
        return Part.SINGLE;
    }

    enum Part implements StringRepresentable
    {
        UPPER,
        MIDDLE,
        LOWER,
        SINGLE;

        private final String serializedName;

        Part()
        {
            serializedName = name().toLowerCase(Locale.ROOT);
        }

        @Override
        public String getSerializedName()
        {
            return serializedName;
        }
    }
}
