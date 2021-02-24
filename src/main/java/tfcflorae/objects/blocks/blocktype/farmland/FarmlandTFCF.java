package tfcflorae.objects.blocks.blocktype.farmland;

import net.dries007.tfc.api.types.Rock;

import tfcflorae.objects.blocks.blocktype.BlockRockVariantFallableTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

public abstract class FarmlandTFCF extends BlockRockVariantFallableTFCF
{
    public FarmlandTFCF(RockTFCF rockTFCF, Rock rock)
    {
        super(rockTFCF, rock);
    }
}
