package tfcflorae.objects.blocks.blocktype;

import java.util.Random;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mcp.MethodsReturnNonnullByDefault;

import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import tfcflorae.TFCFlorae;
import tfcflorae.objects.items.rock.ItemMud;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockRockVariantFallableTFCF extends BlockRockVariantTFCF
{
    public static boolean isSupportingSideBlock(IBlockState state)
    {
        return state.isNormalCube() || (state.getBlock() instanceof BlockRockVariantTFCF);
    }

    public BlockRockVariantFallableTFCF(RockTFCF rockTFCF, Rock rock)
    {
        super(rockTFCF, rock);
        if (rockTFCF.canFall())
        {
            FallingBlockManager.Specification spec = rockTFCF.getFallingSpecification();
            switch(rockTFCF)
            {
                case BOG_IRON_GRASS:
                case DRY_BOG_IRON_GRASS:
                case SPARSE_BOG_IRON_GRASS:
                case BOG_IRON_PODZOL:
                case LOAMY_SAND_GRASS:
                case LOAMY_SAND_PODZOL:
                case SANDY_LOAM_GRASS:
                case SANDY_LOAM_PODZOL:
                case SANDY_CLAY_LOAM_GRASS:
                case SANDY_CLAY_LOAM_PODZOL:
                case SANDY_CLAY_GRASS:
                case SANDY_CLAY_PODZOL:
                case LOAM_GRASS:
                case LOAM_PODZOL:
                case CLAY_LOAM_GRASS:
                case CLAY_LOAM_PODZOL:
                case SILTY_CLAY_GRASS:
                case SILTY_CLAY_PODZOL:
                case SILTY_CLAY_LOAM_GRASS:
                case SILTY_CLAY_LOAM_PODZOL:
                case SILT_LOAM_GRASS:
                case SILT_LOAM_PODZOL:
                case SILT_GRASS:
                case SILT_PODZOL:
                case DRY_LOAMY_SAND_GRASS:
                case DRY_SANDY_LOAM_GRASS:
                case DRY_SANDY_CLAY_LOAM_GRASS:
                case DRY_SANDY_CLAY_GRASS:
                case DRY_LOAM_GRASS:
                case DRY_CLAY_LOAM_GRASS:
                case DRY_SILTY_CLAY_GRASS:
                case DRY_SILTY_CLAY_LOAM_GRASS:
                case DRY_SILT_LOAM_GRASS:
                case DRY_SILT_GRASS:
                case HUMUS_GRASS:
                case DRY_HUMUS_GRASS:
                case CLAY_HUMUS_GRASS:
                case DRY_CLAY_HUMUS_GRASS:
                case EARTHENWARE_CLAY_GRASS:
                case SANDY_EARTHENWARE_CLAY_LOAM_GRASS:
                case SANDY_EARTHENWARE_CLAY_LOAM_PODZOL:
                case SANDY_EARTHENWARE_CLAY_GRASS:
                case SANDY_EARTHENWARE_CLAY_PODZOL:
                case EARTHENWARE_CLAY_LOAM_GRASS:
                case EARTHENWARE_CLAY_LOAM_PODZOL:
                case EARTHENWARE_CLAY_PODZOL:
                case SILTY_EARTHENWARE_CLAY_GRASS:
                case SILTY_EARTHENWARE_CLAY_PODZOL:
                case SILTY_EARTHENWARE_CLAY_LOAM_GRASS:
                case SILTY_EARTHENWARE_CLAY_LOAM_PODZOL:
                case DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS:
                case DRY_SANDY_EARTHENWARE_CLAY_GRASS:
                case DRY_EARTHENWARE_CLAY_LOAM_GRASS:
                case DRY_EARTHENWARE_CLAY_GRASS:
                case DRY_SILTY_EARTHENWARE_CLAY_GRASS:
                case DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS:
                case EARTHENWARE_CLAY_HUMUS_GRASS:
                case DRY_EARTHENWARE_CLAY_HUMUS_GRASS:
                case KAOLINITE_CLAY_GRASS:
                case SANDY_KAOLINITE_CLAY_LOAM_GRASS:
                case SANDY_KAOLINITE_CLAY_LOAM_PODZOL:
                case SANDY_KAOLINITE_CLAY_GRASS:
                case SANDY_KAOLINITE_CLAY_PODZOL:
                case KAOLINITE_CLAY_LOAM_GRASS:
                case KAOLINITE_CLAY_LOAM_PODZOL:
                case KAOLINITE_CLAY_PODZOL:
                case SILTY_KAOLINITE_CLAY_GRASS:
                case SILTY_KAOLINITE_CLAY_PODZOL:
                case SILTY_KAOLINITE_CLAY_LOAM_GRASS:
                case SILTY_KAOLINITE_CLAY_LOAM_PODZOL:
                case DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS:
                case DRY_SANDY_KAOLINITE_CLAY_GRASS:
                case DRY_KAOLINITE_CLAY_LOAM_GRASS:
                case DRY_KAOLINITE_CLAY_GRASS:
                case DRY_SILTY_KAOLINITE_CLAY_GRASS:
                case DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS:
                case KAOLINITE_CLAY_HUMUS_GRASS:
                case DRY_KAOLINITE_CLAY_HUMUS_GRASS:
                case STONEWARE_CLAY_GRASS:
                case SANDY_STONEWARE_CLAY_LOAM_GRASS:
                case SANDY_STONEWARE_CLAY_LOAM_PODZOL:
                case SANDY_STONEWARE_CLAY_GRASS:
                case SANDY_STONEWARE_CLAY_PODZOL:
                case STONEWARE_CLAY_LOAM_GRASS:
                case STONEWARE_CLAY_LOAM_PODZOL:
                case STONEWARE_CLAY_PODZOL:
                case SILTY_STONEWARE_CLAY_GRASS:
                case SILTY_STONEWARE_CLAY_PODZOL:
                case SILTY_STONEWARE_CLAY_LOAM_GRASS:
                case SILTY_STONEWARE_CLAY_LOAM_PODZOL:
                case DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS:
                case DRY_SANDY_STONEWARE_CLAY_GRASS:
                case DRY_STONEWARE_CLAY_LOAM_GRASS:
                case DRY_STONEWARE_CLAY_GRASS:
                case DRY_SILTY_STONEWARE_CLAY_GRASS:
                case DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS:
                case STONEWARE_CLAY_HUMUS_GRASS:
                case DRY_STONEWARE_CLAY_HUMUS_GRASS:
                case DRY_CLAY_GRASS:
                case CLAY_PODZOL:
                case PODZOL:
                case SPARSE_GRASS:
                case SPARSE_CLAY_GRASS:
                case SPARSE_LOAMY_SAND_GRASS:
                case SPARSE_SANDY_LOAM_GRASS:
                case SPARSE_SANDY_CLAY_LOAM_GRASS:
                case SPARSE_SANDY_CLAY_GRASS:
                case SPARSE_LOAM_GRASS:
                case SPARSE_CLAY_LOAM_GRASS:
                case SPARSE_SILTY_CLAY_GRASS:
                case SPARSE_SILTY_CLAY_LOAM_GRASS:
                case SPARSE_SILT_LOAM_GRASS:
                case SPARSE_SILT_GRASS:
                case SPARSE_HUMUS_GRASS:
                case SPARSE_CLAY_HUMUS_GRASS:
                case SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS:
                case SPARSE_SANDY_EARTHENWARE_CLAY_GRASS:
                case SPARSE_EARTHENWARE_CLAY_LOAM_GRASS:
                case SPARSE_EARTHENWARE_CLAY_GRASS:
                case SPARSE_SILTY_EARTHENWARE_CLAY_GRASS:
                case SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS:
                case SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS:
                case SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS:
                case SPARSE_SANDY_KAOLINITE_CLAY_GRASS:
                case SPARSE_KAOLINITE_CLAY_LOAM_GRASS:
                case SPARSE_KAOLINITE_CLAY_GRASS:
                case SPARSE_SILTY_KAOLINITE_CLAY_GRASS:
                case SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS:
                case SPARSE_KAOLINITE_CLAY_HUMUS_GRASS:
                case SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS:
                case SPARSE_SANDY_STONEWARE_CLAY_GRASS:
                case SPARSE_STONEWARE_CLAY_LOAM_GRASS:
                case SPARSE_STONEWARE_CLAY_GRASS:
                case SPARSE_SILTY_STONEWARE_CLAY_GRASS:
                case SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS:
                case SPARSE_STONEWARE_CLAY_HUMUS_GRASS:
                    spec = new FallingBlockManager.Specification(spec);

                    RockTFCF tempRock = RockTFCF.getNonGrassVersionStatic(rockTFCF);
                    if(tempRock != null)
                        spec.setResultingState(BlockRockVariantTFCF.get(rock, tempRock).getDefaultState());
                    else
                    {
                        Rock.Type tempRockTFC = RockTFCF.getNonGrassVersionTFCStatic(rockTFCF);
                        if (tempRockTFC != null)
                            spec.setResultingState(BlockRockVariant.get(rock, tempRockTFC).getDefaultState());
                    }

                    FallingBlockManager.registerFallable(this, spec);
                    break;
                default:
                    spec = new FallingBlockManager.Specification(spec);
                    FallingBlockManager.registerFallable(this, rockTFCF.getFallingSpecification());
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
        if (this.rockTFCF.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false))
        {
            double d0 = (float) pos.getX() + rand.nextFloat();
            double d1 = (double) pos.getY() - 0.05D;
            double d2 = (float) pos.getZ() + rand.nextFloat();
            world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if (rockTFCF == RockTFCF.MUD)
        {
            if (fortune > 3)
            {
                fortune = 3;
            }

            if (rand.nextInt(10 - fortune * 3) == 0)
            {
                return ItemMud.get(rock);
            }
        }
        return super.getItemDropped(state, rand, fortune);
    }

    @Nullable
    private BlockPos checkAreaClear(World world, BlockPos pos)
    {
        // Check that there are no entities in the area, otherwise it would collide with them
        if (!world.getEntitiesWithinAABB(EntityFallingBlock.class, new AxisAlignedBB(pos, pos.add(1, 1, 1))).isEmpty())
        {
            // If we can't fall due to a collision, wait for the block to move out of the way and try again later
            world.scheduleUpdate(pos, this, 20);
            return null;
        }
        return pos;
    }
}