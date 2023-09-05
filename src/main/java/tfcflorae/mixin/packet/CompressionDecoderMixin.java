package tfcflorae.mixin.packet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.network.CompressionDecoder;
import net.minecraftforge.fml.ModList;

@Mixin(value = CompressionDecoder.class, priority = 1)
public abstract class CompressionDecoderMixin implements IMixinConfigPlugin
{
    /*@ModifyConstant(method = "decode", constant = @Constant(intValue = 8388608))
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