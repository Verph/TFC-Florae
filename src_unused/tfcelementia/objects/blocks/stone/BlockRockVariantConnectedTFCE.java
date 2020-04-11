package tfcelementia.objects.blocks.stone;

import java.util.Random;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.BlockPeat;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockShortGrassTFC;
import net.dries007.tfc.objects.entity.EntityFallingBlockTFC;
import net.dries007.tfc.util.IFallingBlock;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcelementia.api.types.*;
import tfcelementia.objects.blocks.stone.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockRockVariantConnectedTFCE extends BlockRockVariantFallableTFCE
{
    // Used for connected textures only.
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    public static void spreadGrass(World world, BlockPos pos, IBlockState us, Random rand)
    {
        IBlockState stateUp = world.getBlockState(pos.up());
        if ((world.getLightFromNeighbors(pos.up()) < 4 && stateUp.getLightOpacity(world, pos.up()) > 2) || stateUp.getMaterial().isLiquid())
        {
            if (us.getBlock() instanceof BlockRockVariantTFCE)
            {
                BlockRockVariantTFCE block = ((BlockRockVariantTFCE) us.getBlock());
                world.setBlockState(pos, block.getVariant(block.getType().getNonPodzolVersion()).getDefaultState());
            }
        }
        else
        {
            if (world.getLightFromNeighbors(pos.up()) < 9 || stateUp.getMaterial().isLiquid()) return;

            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
            {
                if (plant.getPlantType() == Plant.PlantType.SHORT_GRASS && rand.nextFloat() < 0.5f)
                {
                    float temp = ClimateTFC.getActualTemp(world, pos.up());
                    BlockShortGrassTFC plantBlock = BlockShortGrassTFC.get(plant);

                    if (world.isAirBlock(pos.up()) &&
                        plant.isValidLocation(temp, ChunkDataTFC.getRainfall(world, pos.up()), Math.subtractExact(world.getLightFor(EnumSkyBlock.SKY, pos.up()), world.getSkylightSubtracted())) &&
                        plant.isValidGrowthTemp(temp) &&
                        rand.nextDouble() < plantBlock.getGrowthRate(world, pos.up()))
                    {
                        world.setBlockState(pos.up(), plantBlock.getDefaultState());
                    }
                }
            }
        }
    }

    public BlockRockVariantConnectedTFCE(RockTFCE.Type type, RockTFCE rock)
    {
        super(type, rock);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        pos = pos.add(0, -1, 0);
        return state.withProperty(NORTH, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.NORTH))))
            .withProperty(EAST, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.EAST))))
            .withProperty(SOUTH, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.SOUTH))))
            .withProperty(WEST, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.WEST))));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH);
    }

    @Override
    public boolean checkFalling(World worldIn, BlockPos pos, IBlockState state)
    {
        BlockPos pos1 = getFallablePos(worldIn, pos);
        if (pos1 != null)
        {
            if (!BlockFalling.fallInstantly && worldIn.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32)))
            {
                if (!pos1.equals(pos))
                {
                    worldIn.setBlockToAir(pos);
                }
                // Replace grass with dirt
                if (type.getNonPodzolVersion() != type)
                {
                    worldIn.setBlockState(pos1, BlockRockVariantTFCE.get(rock, type.getNonPodzolVersion()).getDefaultState());
                }
                worldIn.spawnEntity(new EntityFallingBlockTFC(worldIn, pos1, this, worldIn.getBlockState(pos1)));
            }
            else
            {
                worldIn.setBlockToAir(pos);
                pos1 = pos1.down();
                while (IFallingBlock.canFallThrough(worldIn, pos1, state.getMaterial()) && pos1.getY() > 0)
                {
                    pos1 = pos1.down();
                }
                if (pos1.getY() > 0)
                {
                    worldIn.setBlockState(pos1.up(), state); // Includes Forge's fix for data loss.
                }
            }
            return true;
        }
        return false;
    }
}
