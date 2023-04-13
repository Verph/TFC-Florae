package tfcflorae.mixin.packet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.network.CompressionDecoder;

@Mixin({CompressionDecoder.class})
public class CompressionDecoderMixin
{
    @ModifyConstant(method = "decode", constant = @Constant(intValue = 8388608))
    private int packetDoubler(int value)
    {
        return value*10;
    }
}