package tfcflorae.api.types;

import net.minecraft.block.material.Material;

import tfcflorae.objects.blocks.plants.BlockPlantTFCF;

public interface IPlantTypeTFCF
{
    BlockPlantTFCF create(PlantTFCF plantTFCF);

    Material getPlantMaterial();
}
