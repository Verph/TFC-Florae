package tfcelementia.objects.blocks.stone;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.BlockButtonStone;
import net.minecraft.block.SoundType;

import tfcelementia.api.types.RockTFCE;

public class BlockButtonStoneTFCE extends BlockButtonStone
{
    private static final Map<RockTFCE, BlockButtonStoneTFCE> MAP = new HashMap<>();

    public static BlockButtonStoneTFCE get(RockTFCE rock)
    {
        return MAP.get(rock);
    }

    public final RockTFCE rock;

    public BlockButtonStoneTFCE(RockTFCE rock)
    {
        this.rock = rock;
        if (MAP.put(rock, this) != null) throw new IllegalStateException("There can only be one.");
        setHardness(0.5F);
        setSoundType(SoundType.STONE);
    }
}
