package tfcflorae.objects.blocks.blocktype;

import net.dries007.tfc.api.types.Rock;

import tfcflorae.types.BlockTypesTFCF;

public class BlockSoil extends BlockFallable
{
    private Rock rock;
    public BlockSoil(Rock rock, BlockTypesTFCF decorationType)
    {
        super(rock, decorationType);
        this.rock = rock;
    }

    public Rock getRock() {
        return rock;
    }
}
