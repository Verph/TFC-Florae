package tfcflorae.objects.blocks.wood.fruitwood;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.api.types.IFruitTree;

import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.util.agriculture.SeasonalTrees;

@ParametersAreNonnullByDefault
public class BlockFruitStairs extends BlockStairs
{
    private static final Map<SeasonalTrees, BlockFruitStairs> TREE_MAP_TFCF = new HashMap<>();
    private static final Map<IFruitTree, BlockFruitStairs> TREE_MAP_TFC = new HashMap<>();

    public static BlockFruitStairs get(SeasonalTrees tree)
    {
        return TREE_MAP_TFCF.get(tree);
    }

    public static BlockFruitStairs get(IFruitTree tree)
    {
        return TREE_MAP_TFC.get(tree);
    }

    public BlockFruitStairs(SeasonalTrees tree)
    {
        super(BlockFruitPlanks.getTFCF(tree).getDefaultState());
        if (TREE_MAP_TFCF.put(tree, this) != null) throw new IllegalStateException("There can only be one.");

        Block baseBlock = BlockFruitPlanks.getTFCF(tree);
        //noinspection ConstantConditions
        setHarvestLevel(baseBlock.getHarvestTool(baseBlock.getDefaultState()), baseBlock.getHarvestLevel(baseBlock.getDefaultState()));
        setSoundType(SoundType.WOOD);
        useNeighborBrightness = true;

        OreDictionaryHelper.register(this, "stair");
        OreDictionaryHelper.register(this, "stair", "wood");
        OreDictionaryHelper.register(this, "stair", "wood", tree);

        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    public BlockFruitStairs(IFruitTree tree)
    {
        super(BlockFruitPlanks.getTFC(tree).getDefaultState());
        if (TREE_MAP_TFC.put(tree, this) != null) throw new IllegalStateException("There can only be one.");

        Block baseBlock = BlockFruitPlanks.getTFC(tree);
        //noinspection ConstantConditions
        setHarvestLevel(baseBlock.getHarvestTool(baseBlock.getDefaultState()), baseBlock.getHarvestLevel(baseBlock.getDefaultState()));
        setSoundType(SoundType.WOOD);
        useNeighborBrightness = true;

        OreDictionaryHelper.register(this, "stair");
        OreDictionaryHelper.register(this, "stair", "wood");
        OreDictionaryHelper.register(this, "stair", "wood", tree);

        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        // Prevents cobble stairs from falling
    }

    @Override
    public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state)
    {
        // Prevents chiseled smooth stone stairs from collapsing
    }

    @Override
    public void onBlockAdded(@Nonnull World worldIn, @Nonnull BlockPos pos, IBlockState state)
    {
        // Prevents cobble stairs from falling
    }
}
