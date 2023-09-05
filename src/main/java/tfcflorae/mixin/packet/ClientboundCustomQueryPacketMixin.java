package tfcflorae.mixin.packet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraftforge.fml.ModList;

@Mixin(value = ClientboundCustomQueryPacket.class, priority = 1)
public abstract class ClientboundCustomQueryPacketMixin implements IMixinConfigPlugin
{
    @ModifyConstant(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", constant = @Constant(intValue = 1048576))
    private int modifyConstant(int value)
    {
        return Integer.MAX_VALUE;
    }

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
	{
		return !ModList.get().isLoaded("connectivity");
	}
}