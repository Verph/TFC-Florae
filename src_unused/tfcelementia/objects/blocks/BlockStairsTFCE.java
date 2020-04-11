package tfcelementia.objects.blocks;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import tfcelementia.api.types.RockTFCE;
import tfcelementia.objects.blocks.stone.BlockRockVariantTFCE;
import tfcelementia.util.OreDictionaryHelper;

public class BlockStairsTFCE extends BlockStairs
{
    private static final Map<RockTFCE, EnumMap<RockTFCE.Type, BlockStairsTFCE>> ROCK_TABLE = new HashMap<>();

    public static BlockStairsTFCE get(RockTFCE rock, RockTFCE.Type type)
    {
        return ROCK_TABLE.get(rock).get(type);
    }

    public BlockStairsTFCE(RockTFCE rock, RockTFCE.Type type)
    {
        super(BlockRockVariantTFCE.get(rock, type).getDefaultState());

        if (!ROCK_TABLE.containsKey(rock))
            ROCK_TABLE.put(rock, new EnumMap<>(RockTFCE.Type.class));
        ROCK_TABLE.get(rock).put(type, this);

        Block baseBlock = BlockRockVariantTFCE.get(rock, type);
        //noinspection ConstantConditions
        setHarvestLevel(baseBlock.getHarvestTool(baseBlock.getDefaultState()), baseBlock.getHarvestLevel(baseBlock.getDefaultState()));
        useNeighborBrightness = true;
        OreDictionaryHelper.register(this, "stair");
        OreDictionaryHelper.registerRockType(this, type, "stair");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        // Prevents cobble stairs from falling
    }

    @Override
    public void onBlockAdded(@Nonnull World worldIn, @Nonnull BlockPos pos, IBlockState state)
    {
        // Prevents cobble stairs from falling
    }
}