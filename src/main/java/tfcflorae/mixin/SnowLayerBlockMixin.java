package tfcflorae.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.config.TFCConfig;

import tfcflorae.util.TFCFHelpers;

@Mixin(value = SnowLayerBlock.class, priority = 999999)
public abstract class SnowLayerBlockMixin extends Block
{
    @Unique private boolean canWalkThroughEffortlessly;

    public SnowLayerBlockMixin(Properties properties)
    {
        super(properties);
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
        return TFCConfig.SERVER.enableSnowSlowEntities.get() ? canWalkThroughEffortlessly ? 1.0F : 0.6F : 1.0f;
    }
}
