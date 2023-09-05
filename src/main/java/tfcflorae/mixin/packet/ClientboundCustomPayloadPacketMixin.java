package tfcflorae.mixin.packet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraftforge.fml.ModList;

@Mixin(value = ClientboundCustomPayloadPacket.class, priority = 1)
public abstract class ClientboundCustomPayloadPacketMixin implements IMixinConfigPlugin
{
    /*@ModifyConstant(method = "<init>*", constant = @Constant(intValue = 1048576))
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