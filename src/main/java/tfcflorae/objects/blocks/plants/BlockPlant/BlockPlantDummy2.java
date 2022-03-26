package tfcflorae.objects.blocks.plants.BlockPlant;

import java.util.HashMap;
import java.util.Map;

import net.dries007.tfc.api.types.Plant;

import tfcflorae.util.OreDictionaryHelper;

public class BlockPlantDummy2 extends BlockPlantTFCF
{
    private static final Map<Plant, BlockPlantDummy2> MAP = new HashMap<>();

    public static BlockPlantDummy2 get(Plant plant)
    {
        return MAP.get(plant);
    }

    public BlockPlantDummy2(Plant plant)
    {
        super(plant);
        if (MAP.put(plant, this) != null) throw new IllegalStateException("There can only be one.");

        plant.getOreDictName().ifPresent(name -> OreDictionaryHelper.register(this, name));
    }
    
}
