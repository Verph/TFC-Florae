package tfcflorae.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.VineBlock;

import net.dries007.tfc.common.blocks.wood.ILeavesBlock;

@Mixin(value = VineBlock.class, priority = 999999)
public abstract class VineBlockMixin
{
    @Inject(method = "isAcceptableNeighbour", at = @At("HEAD"), cancellable = true)
    private static void inject$isAcceptableNeighbour(BlockGetter level, BlockPos pos, Direction dir, CallbackInfoReturnable<Boolean> cir)
    {
        if (level.getBlockState(pos).getBlock() instanceof ILeavesBlock)
        {
            cir.setReturnValue(true);
        }
    }
}
