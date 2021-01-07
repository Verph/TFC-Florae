package tfcflorae.objects.blocks.wood;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;

import net.dries007.tfc.api.types.Tree;

import tfcflorae.util.OreDictionaryHelper;

public class BlockFenceGateLog extends BlockFenceGate
{
    private static final Map<Tree, BlockFenceGateLog> MAP = new HashMap<>();

    public static BlockFenceGateLog get(Tree wood)
    {
        return MAP.get(wood);
    }

    public final Tree wood;

    public BlockFenceGateLog(Tree wood)
    {
        super(BlockPlanks.EnumType.OAK);
        if (MAP.put(wood, this) != null) throw new IllegalStateException("There can only be one.");
        this.wood = wood;
        setHarvestLevel("axe", 0);
        setHardness(2.0F); // match vanilla
        setResistance(15.0F);
        OreDictionaryHelper.register(this, "fence", "gate", "wood");
        OreDictionaryHelper.register(this, "fence", "gate", "wood", wood.getRegistryName().getPath());
        OreDictionaryHelper.register(this, "fence", "gate", "log", "wood");
        OreDictionaryHelper.register(this, "fence", "gate", "log", "wood", wood.getRegistryName().getPath());
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}