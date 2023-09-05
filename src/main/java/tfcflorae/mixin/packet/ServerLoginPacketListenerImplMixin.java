package tfcflorae.mixin.packet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import net.minecraftforge.fml.ModList;

@Mixin(value = ServerLoginPacketListenerImpl.class, priority = 1)
public abstract class ServerLoginPacketListenerImplMixin implements IMixinConfigPlugin
{
    /*@ModifyConstant(method = "tick", constant = @Constant(intValue = 600))
    private int modifyConstant(int value)
    {
        return Integer.MAX_VALUE;
    }*/

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
	{
		return !ModList.get().isLoaded("connectivity");
	}
}