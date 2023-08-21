package tfcflorae.world.carver;

import java.util.function.Function;

import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import com.mojang.serialization.Codec;

import static tfcflorae.TFCFlorae.MOD_ID;

@SuppressWarnings("unused")
public class TFCFCarvers
{
    public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, MOD_ID);

    public static final RegistryObject<CavernCaverFeature> DEEP_CAVES = register("deep_caves", CavernCaverFeature::new, CaveCarverConfiguration.CODEC);

    private static <C extends CarverConfiguration, WC extends WorldCarver<C>> RegistryObject<WC> register(String name, Function<Codec<C>, WC> factory, Codec<C> codec)
    {
        return CARVERS.register(name, () -> factory.apply(codec));
    }
}