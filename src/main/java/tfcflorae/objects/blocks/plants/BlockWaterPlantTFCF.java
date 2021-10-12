package tfcflorae.objects.blocks.plants;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.objects.blocks.BlocksTFC;

import tfcflorae.api.types.PlantTFCF;

import static net.dries007.tfc.world.classic.ChunkGenTFC.SALT_WATER;

// todo: either pull some trickery to make this look like water or simply wait until 1.13 and implement ILiquidContainer
@ParametersAreNonnullByDefault
public class BlockWaterPlantTFCF extends BlockPlantTFCF
{
    private static final Map<PlantTFCF, BlockWaterPlantTFCF> MAP = new HashMap<>();

    public static BlockWaterPlantTFCF get(PlantTFCF plant)
    {
        return MAP.get(plant);
    }

    public BlockWaterPlantTFCF(PlantTFCF plant)
    {
        super(plant);
        if (MAP.put(plant, this) != null) throw new IllegalStateException("There can only be one.");
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        IBlockState soil = worldIn.getBlockState(pos.down());
        if (plant.getWaterType() == SALT_WATER)
            return BlocksTFC.isSaltWater(worldIn.getBlockState(pos)) && this.canSustainBush(soil);
        return BlocksTFC.isFreshWater(worldIn.getBlockState(pos)) && this.canSustainBush(soil);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        this.onBlockHarvested(world, pos, state, player);
        return world.setBlockState(pos, plant.getWaterType(), world.isRemote ? 11 : 3);
    }

    @Override
    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.canBlockStay(worldIn, pos, state))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockState(pos, plant.getWaterType());
        }
    }
}