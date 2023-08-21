package tfcflorae.world;

import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.world.ForgeWorldPreset;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.world.TFCChunkGenerator;
import net.dries007.tfc.world.TFCWorldType;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class ContinentalWorldType
{
    public static final DeferredRegister<ForgeWorldPreset> WORLD_TYPES = DeferredRegister.create(ForgeRegistries.Keys.WORLD_TYPES, MOD_ID);

    public static final RegistryObject<ForgeWorldPreset> WORLD_TYPE = WORLD_TYPES.register("continental", () -> new ForgeWorldPreset((registries, seed, settings) -> {
        final Registry<StructureSet> structures = registries.registryOrThrow(Registry.STRUCTURE_SET_REGISTRY);
        final Registry<NormalNoise.NoiseParameters> noiseParameters = registries.registryOrThrow(Registry.NOISE_REGISTRY);
        final Registry<NoiseGeneratorSettings> noiseGeneratorSettings = registries.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
        final Registry<Biome> biomes = registries.registryOrThrow(Registry.BIOME_REGISTRY);

        return TFCChunkGenerator.defaultChunkGenerator(structures, noiseParameters, noiseGeneratorSettings.getHolderOrThrow(NoiseGeneratorSettings.OVERWORLD), biomes, seed);
    }));

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void overrideDefaultWorldType()
    {
        if (TFCConfig.COMMON.setTFCWorldTypeAsDefault.get() && (ForgeConfig.COMMON.defaultWorldType.get().equals("default") || ForgeConfig.COMMON.defaultWorldType.get().equals(TFCWorldType.WORLD_TYPE.getId().toString())))
        {
            ((ForgeConfigSpec.ConfigValue) ForgeConfig.COMMON.defaultWorldType).set(ContinentalWorldType.WORLD_TYPE.getId().toString());
        }
    }
}
