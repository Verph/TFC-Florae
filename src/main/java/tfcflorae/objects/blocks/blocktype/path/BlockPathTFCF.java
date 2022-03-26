package tfcflorae.objects.blocks.blocktype.path;

import net.dries007.tfc.api.types.Rock;

import tfcflorae.objects.blocks.blocktype.BlockRockVariantFallableTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

public abstract class BlockPathTFCF extends BlockRockVariantFallableTFCF
{
    public BlockPathTFCF(RockTFCF rockTFCF, Rock rock)
    {
        super(rockTFCF, rock);
    }
}
