package tfcflorae.mixin.packet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.network.PacketEncoder;
import net.minecraftforge.fml.ModList;

@Mixin(value = PacketEncoder.class, priority = 1)
public abstract class PacketEncoderMixin implements IMixinConfigPlugin
{
    /*@ModifyConstant(method = "encode(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;Lio/netty/buffer/ByteBuf;)V", constant = @Constant(intValue = 8388608))
    private int modifyConstant(int value)
    {
        return value*100;
    }*/

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
	{
		return !ModList.get().isLoaded("connectivity");
	}
}