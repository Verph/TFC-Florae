package tfcflorae.mixin.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.dries007.tfc.world.biome.BiomeExtension;

@Mixin(TFCBiomesMixin.class)
public interface TFCBiomesMixinAccessor
{
    @Accessor
    static BiomeExtension getGrasslands()
    {
        return getGrasslands();
    }

    @Accessor
    static BiomeExtension getWetlands()
    {
        return getWetlands();
    }

    @Accessor
    static BiomeExtension getMarshes()
    {
        return getMarshes();
    }

    @Accessor
    static BiomeExtension getSwamps()
    {
        return getSwamps();
    }
}
