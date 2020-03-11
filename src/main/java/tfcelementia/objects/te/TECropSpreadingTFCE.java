package tfcelementia.objects.te;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import tfcelementia.objects.blocks.agriculture.BlockCropSpreadingTFCE;

@ParametersAreNonnullByDefault
public class TECropSpreadingTFCE extends TECropBaseTFCE 
{
    private int maxGrowthStage; // The max value this crop can grow to
    private int baseAge; // The current age, including all spreading attempts
    private boolean isSeedPlant; // Was the plant the initial one that was planted? (controls whether it should drop a seed or not)

    public TECropSpreadingTFCE()
    {
        this.maxGrowthStage = 0;
        this.isSeedPlant = true;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        maxGrowthStage = nbt.getInteger("maxGrowthStage");
        baseAge = nbt.getInteger("baseAge");
        isSeedPlant = nbt.getBoolean("isSeedPlant");
        super.readFromNBT(nbt);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("maxGrowthStage", maxGrowthStage);
        nbt.setInteger("baseAge", baseAge);
        nbt.setBoolean("isSeedPlant", isSeedPlant);
        return super.writeToNBT(nbt);
    }

    public void onPlaced()
    {
        IBlockState state = world.getBlockState(pos);
        BlockCropSpreadingTFCE block = (BlockCropSpreadingTFCE) state.getBlock();
        // Calculate initial max growth stage
        maxGrowthStage = 3 + state.getValue(block.getStageProperty());
        if (maxGrowthStage > block.getCrop().getMaxStage())
        {
            maxGrowthStage = block.getCrop().getMaxStage();
        }
        // Reset counter
        resetCounter();
    }

    public int getMaxGrowthStage()
    {
        return maxGrowthStage;
    }

    public void setMaxGrowthStage(int maxGrowthStage)
    {
        this.maxGrowthStage = maxGrowthStage;
        if (this.maxGrowthStage > 7)
        {
            this.maxGrowthStage = 7;
        }
        markDirty();
    }

    public int getBaseAge()
    {
        return baseAge;
    }

    public void setBaseAge(int baseAge)
    {
        this.baseAge = baseAge;
        markDirty();
    }

    public boolean isSeedPlant()
    {
        return isSeedPlant;
    }

    public void setSeedPlant(boolean seedPlant)
    {
        isSeedPlant = seedPlant;
        markDirty();
    }
}