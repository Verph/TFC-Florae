package tfcflorae.mixin.accessor;

import net.dries007.tfc.world.chunkdata.TFCChunkDataGenerator;
import net.dries007.tfc.world.noise.Noise2D;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TFCChunkDataGenerator.class)
public interface TFCChunkDataGeneratorAccessor
{
    @Accessor("rainfallNoise")
    Noise2D accessor$getRainfallNoise();
}
