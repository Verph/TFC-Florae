package tfcflorae.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.TreeFeature;

import tfcflorae.world.feature.DripstoneUtils;

@Mixin(TreeFeature.class)
public abstract class TreeFeatureMixin
{
    @Inject(method = "validTreePos", at = @At("HEAD"), cancellable = true)
    private static void inject$validTreePos(LevelSimulatedReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(level.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWater));
    }
}
