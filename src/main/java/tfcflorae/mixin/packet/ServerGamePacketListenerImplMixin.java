package tfcflorae.mixin.packet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.server.network.ServerGamePacketListenerImpl;

@Mixin(value = ServerGamePacketListenerImpl.class, priority = 999999)
public class ServerGamePacketListenerImplMixin
{
    @ModifyConstant(method = "tick", constant = @Constant(longValue = 15000L, ordinal = 0))
    private long injected(long value)
    {
        return Long.MAX_VALUE;
    }
}