package tfcflorae.mixin.packet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;

@Mixin(ServerboundCustomPayloadPacket.class)
public class ServerboundCustomPayloadPacketMixin
{
    @ModifyConstant(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", constant = @Constant(intValue = 32767))
    private int injected(int value)
    {
        return /*value*10*/ Integer.MAX_VALUE;
    }
}