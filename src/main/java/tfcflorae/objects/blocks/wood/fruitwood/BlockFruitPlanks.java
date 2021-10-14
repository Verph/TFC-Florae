package tfcflorae.objects.blocks.wood.fruitwood;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import net.dries007.tfc.api.types.IFruitTree;

import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.util.agriculture.SeasonalTrees;

public class BlockFruitPlanks extends Block
{
    private static final Map<SeasonalTrees, BlockFruitPlanks> MAP_TFCF = new HashMap<>();
    private static final Map<IFruitTree, BlockFruitPlanks> MAP_TFC = new HashMap<>();

    public static BlockFruitPlanks getTFCF(SeasonalTrees tree)
    {
        return MAP_TFCF.get(tree);
    }

    public static BlockFruitPlanks getTFC(IFruitTree tree)
    {
        return MAP_TFC.get(tree);
    }

    public final SeasonalTrees fruitTFCF;
    public final IFruitTree fruitTFC;

    public BlockFruitPlanks(SeasonalTrees tree)
    {
        super(Material.WOOD, Material.WOOD.getMaterialMapColor());
        if (MAP_TFCF.put(tree, this) != null) throw new IllegalStateException("There can only be one.");
        
        fruitTFCF = tree;
        fruitTFC = null;
        setHarvestLevel("axe", 0);
        setHardness(2.0F)
        .setResistance(5.0F);
        setSoundType(SoundType.WOOD);
        OreDictionaryHelper.register(this, "planks");
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    public BlockFruitPlanks(IFruitTree tree)
    {
        super(Material.WOOD, Material.WOOD.getMaterialMapColor());
        if (MAP_TFC.put(tree, this) != null) throw new IllegalStateException("There can only be one.");

        fruitTFC = tree;
        fruitTFCF = null;
        setHarvestLevel("axe", 0);
        setHardness(2.0F)
        .setResistance(5.0F);
        setSoundType(SoundType.WOOD);
        OreDictionaryHelper.register(this, "planks");
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}