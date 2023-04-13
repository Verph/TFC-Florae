package tfcflorae.mixin.packet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.network.CompressionEncoder;

@Mixin({CompressionEncoder.class})
public class CompressionEncoderMixin
{
    /*@ModifyConstant(method = "encode(Lio/netty/channel/ChannelHandlerContext;Lio/netty/buffer/ByteBuf;Lio/netty/buffer/ByteBuf;)V", constant = @Constant(intValue = 2097152))
    private int packetDoubler(int value) 
    {
        return value*10;
    }*/
}