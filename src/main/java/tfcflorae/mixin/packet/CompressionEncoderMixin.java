package tfcflorae.mixin.packet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.network.CompressionEncoder;
import net.minecraftforge.fml.ModList;

@Mixin(value = CompressionEncoder.class, priority = 1)
public abstract class CompressionEncoderMixin implements IMixinConfigPlugin
{
    /*@ModifyConstant(method = "encode(Lio/netty/channel/ChannelHandlerContext;Lio/netty/buffer/ByteBuf;Lio/netty/buffer/ByteBuf;)V", constant = @Constant(intValue = 2097152))
    private int modifyConstant(int value) 
    {
        return value*10;
    }*/

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
	{
		return !ModList.get().isLoaded("connectivity");
	}
}