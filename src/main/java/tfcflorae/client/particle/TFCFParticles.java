package tfcflorae.client.particle;

import java.util.function.Function;

import com.mojang.serialization.Codec;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static tfcflorae.TFCFlorae.MOD_ID;

public final class TFCFParticles
{
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);

    public static final RegistryObject<SimpleParticleType> WATER_FLOW = register("water_flow");
    public static final RegistryObject<ParticleType<BlockParticleOption>> FALLING_LEAF = register("falling_leaf", BlockParticleOption.DESERIALIZER, BlockParticleOption::codec);

    @SuppressWarnings("deprecation")
    private static <T extends ParticleOptions> RegistryObject<ParticleType<T>> register(String name, ParticleOptions.Deserializer<T> deserializer, final Function<ParticleType<T>, Codec<T>> codec)
    {
        return PARTICLE_TYPES.register(name, () -> new ParticleType<>(false, deserializer) {
            @Override
            public Codec<T> codec()
            {
                return codec.apply(this);
            }
        });
    }

    private static RegistryObject<SimpleParticleType> register(String name)
    {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(false));
    }
}
