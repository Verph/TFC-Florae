package tfcflorae.common.blocks;

import net.dries007.tfc.common.blocks.OreDeposit;
import net.dries007.tfc.common.blocks.OreDepositBlock;

import tfcflorae.common.blocks.rock.TFCFRock;

public class TFCFOreDepositBlock extends OreDepositBlock
{
    public TFCFOreDepositBlock(Properties properties, TFCFRock rock, OreDeposit ore)
    {
        super(properties, rock.ordinal(), ore.ordinal());
    }
}