package tfcflorae.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.PlantBlock;
import net.dries007.tfc.common.blocks.plant.TFCBushBlock;

import tfcflorae.util.TFCFHelpers;

@Mixin(PlantBlock.class)
public abstract class PlantBlockMixin extends TFCBushBlock
{
    @Unique private boolean canWalkThroughEffortlessly;

    public PlantBlockMixin(ExtendedProperties properties)
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

    @Inject(method = "getSpeedFactor", at = @At("INVOKE"), cancellable = true)
    private void inject$getSpeedFactor(CallbackInfoReturnable<Float> cir)
    {
        if (canWalkThroughEffortlessly)
        {
            cir.setReturnValue(1.0F);
        }
        else
        {
            cir.getReturnValue();
        }
    }
}
