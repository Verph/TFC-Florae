package tfcflorae.interfaces;

import net.dries007.tfc.world.biome.BiomeExtension;

public interface TFCBiomesMixinInterface
{
    BiomeExtension getStaticGrasslands();

    BiomeExtension getStaticWetlands();

    BiomeExtension getStaticMarshes();

    BiomeExtension getStaticSwamps();
}
