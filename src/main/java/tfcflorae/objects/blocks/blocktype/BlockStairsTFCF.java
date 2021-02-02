package tfcflorae.objects.blocks.blocktype;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.api.types.Rock;

import tfcflorae.types.BlockTypesTFCF.RockTFCF;
import tfcflorae.util.OreDictionaryHelper;

@ParametersAreNonnullByDefault
public class BlockStairsTFCF extends BlockStairs
{
    private static final Map<Rock, EnumMap<RockTFCF, BlockStairsTFCF>> ROCK_TABLE = new HashMap<>();

    public static BlockStairsTFCF get(Rock rock, RockTFCF rockTFCF)
    {
        return ROCK_TABLE.get(rock).get(rockTFCF);
    }

    public BlockStairsTFCF(Rock rock, RockTFCF rockTFCF)
    {
        super(BlockRockVariantTFCF.get(rock, rockTFCF).getDefaultState());

        if (!ROCK_TABLE.containsKey(rock))
            ROCK_TABLE.put(rock, new EnumMap<>(RockTFCF.class));
        ROCK_TABLE.get(rock).put(rockTFCF, this);

        Block baseBlock = BlockRockVariantTFCF.get(rock, rockTFCF);
        //noinspection ConstantConditions
        setHarvestLevel(baseBlock.getHarvestTool(baseBlock.getDefaultState()), baseBlock.getHarvestLevel(baseBlock.getDefaultState()));
        useNeighborBrightness = true;
        OreDictionaryHelper.register(this, "stair");
        OreDictionaryHelper.registerRockType(this, rockTFCF, "stair");
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
