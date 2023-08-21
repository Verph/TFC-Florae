package tfcflorae.interfaces;

import net.dries007.tfc.world.noise.Noise2D;

public interface ChunkDataInterface
{
    Noise2D getTemperatureNoise();
    Noise2D getRainfallNoise();
    Noise2D getLayerHeightNoise();
    Noise2D getForestWeirdnessNoise();
    Noise2D getForestDensityNoise();
}
