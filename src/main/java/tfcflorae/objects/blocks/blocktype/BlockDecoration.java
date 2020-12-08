package tfcflorae.objects.blocks.blocktype;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Rock;

import tfcflorae.types.BlockTypesTFCF;

public class BlockDecoration extends Block implements IItemSize
{
    public BlockTypesTFCF type;
    public static HashMap<Rock, EnumMap<BlockTypesTFCF, BlockDecoration>> table = new HashMap<>();

    private BlockTypesTFCF rockTFCF;

    public static BlockDecoration get(Rock rock, BlockTypesTFCF rockTFCF)
    {
        return table.get(rock).get(rockTFCF);
    }

    public static BlockDecoration create(Rock rock, BlockTypesTFCF rockTFCF)
    {
        if(rockTFCF.isFallable()) return new BlockFallable(rock, rockTFCF);
        if(rockTFCF == BlockTypesTFCF.PODZOL) return new BlockSoil(rock, rockTFCF);
        if(rockTFCF == BlockTypesTFCF.COARSE_DIRT) return new BlockSoil(rock, rockTFCF);
        if(rockTFCF == BlockTypesTFCF.MUD) return new BlockSoil(rock, rockTFCF);
        return new BlockDecoration(rock, rockTFCF);
    }

    public BlockDecoration(Rock rock, BlockTypesTFCF rockTFCF)
    {
        super(rockTFCF.getMaterial());
        this.type = rockTFCF;
        this.setSoundType(rockTFCF.getSoundType());
        this.setHardness(rockTFCF.getHardness());
        this.setHarvestLevel("pickaxe", 1);
        if(!table.containsKey(rock))
        {
            table.put(rock, new EnumMap<>(BlockTypesTFCF.class));
        }
        table.get(rock).put(rockTFCF, this);
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack itemStack) {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack itemStack) {
        return Weight.LIGHT;
    }
}
