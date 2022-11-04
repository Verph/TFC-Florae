package tfcflorae.common.blocks.rock;

import net.minecraft.world.level.block.Block;

import net.dries007.tfc.common.blocks.ItemPropertyProviderBlock;
import net.dries007.tfc.common.blocks.OreDeposit;

public class TFCFOreDepositBlock extends Block implements ItemPropertyProviderBlock
{
    private final int rockProperty;
    private final int oreProperty;

    public TFCFOreDepositBlock(Properties properties, TFCFRock rock, OreDeposit ore)
    {
        this(properties, rock.ordinal(), ore.ordinal());
    }

    protected TFCFOreDepositBlock(Properties properties, int rockProperty, int oreProperty)
    {
        super(properties);

        this.rockProperty = rockProperty;
        this.oreProperty = oreProperty;
    }

    @Override
    public int getValue(Type type)
    {
        if (type == OreDeposit.ROCK_PROPERTY)
        {
            return rockProperty;
        }
        if (type == OreDeposit.ORE_PROPERTY)
        {
            return oreProperty;
        }
        return 0;
    }
}
