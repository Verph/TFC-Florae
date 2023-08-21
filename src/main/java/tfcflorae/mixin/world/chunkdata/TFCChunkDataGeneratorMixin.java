package tfcflorae.mixin.world.chunkdata;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.util.Mth;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.IArtist;
import net.dries007.tfc.util.climate.ClimateModel;
import net.dries007.tfc.util.climate.OverworldClimateModel;
import net.dries007.tfc.world.biome.TFCBiomes;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataGenerator;
import net.dries007.tfc.world.chunkdata.ForestType;
import net.dries007.tfc.world.chunkdata.PlateTectonicsClassification;
import net.dries007.tfc.world.chunkdata.TFCChunkDataGenerator;
import net.dries007.tfc.world.layer.TFCLayers;
import net.dries007.tfc.world.layer.framework.ConcurrentArea;
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;
import net.dries007.tfc.world.settings.ClimateSettings;
import net.dries007.tfc.world.settings.RockLayerSettings;

import tfcflorae.interfaces.ChunkDataInterface;

@Mixin(TFCChunkDataGenerator.class)
public class TFCChunkDataGeneratorMixin implements ChunkDataGenerator, ChunkDataInterface
{
    @Unique private static TFCBiomes staticBiomes = new TFCBiomes();

    @Shadow private final ConcurrentArea<ForestType> forestTypeLayer;

    @Shadow private final Noise2D temperatureNoise;
    @Shadow private final Noise2D rainfallNoise;
    @Shadow private final Noise2D layerHeightNoise;
    @Shadow private final Noise2D forestWeirdnessNoise;
    @Shadow private final Noise2D forestDensityNoise;

    @Shadow private final ConcurrentArea<PlateTectonicsClassification> plateTectonicsInfo;

    private TFCChunkDataGeneratorMixin(long worldSeed, RockLayerSettings rockLayerSettings, ClimateSettings temperatureSettings, ClimateSettings rainfallSettings)
    {
        final Random random = new Random(worldSeed);
        random.setSeed(worldSeed ^ random.nextLong());

        this.layerHeightNoise = new OpenSimplex2D(random.nextInt()).octaves(2).scaled(-10, 10).spread(0.03f);

        // Climate
        temperatureNoise = ((Noise2D) (x, z) -> Helpers.triangle(1, 0, 1f / (4f * temperatureSettings.scale()), temperatureSettings.endlessPoles() ? Mth.clamp(z, -temperatureSettings.scale(), temperatureSettings.scale()) : z))
            .scaled(OverworldClimateModel.MINIMUM_TEMPERATURE_SCALE, OverworldClimateModel.MAXIMUM_TEMPERATURE_SCALE)
            .add(new OpenSimplex2D(random.nextInt())
                .octaves(2)
                .spread(12f / temperatureSettings.scale())
                .scaled(-OverworldClimateModel.REGIONAL_TEMPERATURE_SCALE, OverworldClimateModel.REGIONAL_TEMPERATURE_SCALE));
        rainfallNoise = ((Noise2D) (x, z) -> Helpers.triangle(1, 0, 1f / (4f * rainfallSettings.scale()), rainfallSettings.endlessPoles() ? Mth.clamp(x, -rainfallSettings.scale(), rainfallSettings.scale()) : x))
            .scaled(ClimateModel.MINIMUM_RAINFALL, ClimateModel.MAXIMUM_RAINFALL)
            .add(new OpenSimplex2D(random.nextInt())
                .octaves(2)
                .spread(12f / rainfallSettings.scale())
                .scaled(-OverworldClimateModel.REGIONAL_RAINFALL_SCALE, OverworldClimateModel.REGIONAL_RAINFALL_SCALE))
            .clamped(ClimateModel.MINIMUM_RAINFALL, ClimateModel.MAXIMUM_RAINFALL);

        // Flora
        forestTypeLayer = new ConcurrentArea<>(TFCLayers.createOverworldForestLayer(random.nextLong(), IArtist.nope()), ForestType::valueOf);
        forestWeirdnessNoise = new OpenSimplex2D(random.nextInt()).octaves(4).spread(0.0025f).map(x -> 1.1f * Math.abs(x)).clamped(0, 1);
        forestDensityNoise = new OpenSimplex2D(random.nextInt()).octaves(4).spread(0.0025f).scaled(-0.2f, 1.2f).clamped(0, 1);

        // Plate Tectonics
        plateTectonicsInfo = new ConcurrentArea<>(TFCLayers.createOverworldPlateTectonicInfoLayer(worldSeed), PlateTectonicsClassification::valueOf);
    }

    @Shadow
    @Override
    public void generate(ChunkData data) {}

    @Unique @Override
    public Noise2D getTemperatureNoise()
    {
        return temperatureNoise;
    }

    @Unique @Override
    public Noise2D getRainfallNoise()
    {
        return rainfallNoise;
    }

    @Unique @Override
    public Noise2D getLayerHeightNoise()
    {
        return layerHeightNoise;
    }

    @Unique @Override
    public Noise2D getForestWeirdnessNoise()
    {
        return forestWeirdnessNoise;
    }

    @Unique @Override
    public Noise2D getForestDensityNoise()
    {
        return forestDensityNoise;
    }
}
