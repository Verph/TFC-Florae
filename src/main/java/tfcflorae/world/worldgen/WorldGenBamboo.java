package tfcflorae.world.worldgen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;

import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.biomes.BiomeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.objects.blocks.BlocksTFCF;

public class WorldGenBamboo extends WorldGenerator
{
    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return false;

        final Biome b = world.getBiome(pos);
        if (!(b instanceof BiomeTFC) || b == BiomesTFC.OCEAN || b == BiomesTFC.DEEP_OCEAN)
            return false;

        final float diversity = chunkData.getFloraDiversity();
        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        int x = pos.getX() - 7 + rand.nextInt(14);
        int z = pos.getZ() - 7 + rand.nextInt(14);
        BlockPos genPos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));

        generateArrowBamboo(world, rand, genPos);
        generateBlackBamboo(world, rand, genPos);
        generateBlueBamboo(world, rand, genPos);
        generateDragonBamboo(world, rand, genPos);
        generateGoldenBamboo(world, rand, genPos);
        generateNarrowLeafBamboo(world, rand, genPos);
        generateRedBamboo(world, rand, genPos);
        generateTempleBamboo(world, rand, genPos);
        generateThornyBamboo(world, rand, genPos);
        generateTimberBamboo(world, rand, genPos);
        generateTinwaBamboo(world, rand, genPos);
        generateWeaversBamboo(world, rand, genPos);

        return false;
    }

    private void generateArrowBamboo(World world, Random rand, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return;

        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        if (rand.nextInt(15) != 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 240f && temp > 24f && density > 0.5f)
            {
                IBlockState state = world.getBlockState(pos.down());
                if (world.isAirBlock(pos) && state.isSideSolid(world, pos.down(), EnumFacing.UP) && BlocksTFC.isGrowableSoil(state))
                {
                    for (int air = 1; air < 15; air++)
                    {
                        if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                            return;
                    }
                    int height = 7 + rand.nextInt(5);
                    IBlockState leaves = BlocksTFCF.ARROW_BAMBOO_LEAVES.getDefaultState();
                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.ARROW_BAMBOO_LOG.getDefaultState());
                        if (trunk < 3)
                            continue;
                        for (EnumFacing d : EnumFacing.HORIZONTALS)
                        {
                            world.setBlockState(trunkPos.offset(d, 1), leaves);
                            if (rand.nextFloat() < 1 - (float) trunk / height)
                            {
                                world.setBlockState(trunkPos.offset(d, 2), leaves);
                            }
                            else { continue; }
                            if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                                world.setBlockState(trunkPos.offset(d, 3), leaves);
                        }
                    }
                    world.setBlockState(pos.offset(EnumFacing.UP, height), leaves);
                }
            }
        }
    }

    private void generateBlackBamboo(World world, Random rand, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return;

        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        if (rand.nextInt(15) != 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 240f && temp > 24f && density > 0.5f)
            {
                IBlockState state = world.getBlockState(pos.down());
                if (world.isAirBlock(pos) && state.isSideSolid(world, pos.down(), EnumFacing.UP) && BlocksTFC.isGrowableSoil(state))
                {
                    for (int air = 1; air < 15; air++)
                    {
                        if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                            return;
                    }
                    int height = 7 + rand.nextInt(5);
                    IBlockState leaves = BlocksTFCF.BLACK_BAMBOO_LEAVES.getDefaultState();
                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.BLACK_BAMBOO_LOG.getDefaultState());
                        if (trunk < 3)
                            continue;
                        for (EnumFacing d : EnumFacing.HORIZONTALS)
                        {
                            world.setBlockState(trunkPos.offset(d, 1), leaves);
                            if (rand.nextFloat() < 1 - (float) trunk / height)
                            {
                                world.setBlockState(trunkPos.offset(d, 2), leaves);
                            }
                            else { continue; }
                            if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                                world.setBlockState(trunkPos.offset(d, 3), leaves);
                        }
                    }
                    world.setBlockState(pos.offset(EnumFacing.UP, height), leaves);
                }
            }
        }
    }

    private void generateBlueBamboo(World world, Random rand, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return;

        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        if (rand.nextInt(15) != 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 240f && temp > 24f && density > 0.5f)
            {
                IBlockState state = world.getBlockState(pos.down());
                if (world.isAirBlock(pos) && state.isSideSolid(world, pos.down(), EnumFacing.UP) && BlocksTFC.isGrowableSoil(state))
                {
                    for (int air = 1; air < 15; air++)
                    {
                        if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                            return;
                    }
                    int height = 7 + rand.nextInt(5);
                    IBlockState leaves = BlocksTFCF.BLUE_BAMBOO_LEAVES.getDefaultState();
                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.BLUE_BAMBOO_LOG.getDefaultState());
                        if (trunk < 3)
                            continue;
                        for (EnumFacing d : EnumFacing.HORIZONTALS)
                        {
                            world.setBlockState(trunkPos.offset(d, 1), leaves);
                            if (rand.nextFloat() < 1 - (float) trunk / height)
                            {
                                world.setBlockState(trunkPos.offset(d, 2), leaves);
                            }
                            else { continue; }
                            if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                                world.setBlockState(trunkPos.offset(d, 3), leaves);
                        }
                    }
                    world.setBlockState(pos.offset(EnumFacing.UP, height), leaves);
                }
            }
        }
    }

    private void generateDragonBamboo(World world, Random rand, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return;

        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        if (rand.nextInt(15) != 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 240f && temp > 24f && density > 0.5f)
            {
                IBlockState state = world.getBlockState(pos.down());
                if (world.isAirBlock(pos) && state.isSideSolid(world, pos.down(), EnumFacing.UP) && BlocksTFC.isGrowableSoil(state))
                {
                    for (int air = 1; air < 15; air++)
                    {
                        if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                            return;
                    }
                    int height = 7 + rand.nextInt(5);
                    IBlockState leaves = BlocksTFCF.DRAGON_BAMBOO_LEAVES.getDefaultState();
                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.DRAGON_BAMBOO_LOG.getDefaultState());
                        if (trunk < 3)
                            continue;
                        for (EnumFacing d : EnumFacing.HORIZONTALS)
                        {
                            world.setBlockState(trunkPos.offset(d, 1), leaves);
                            if (rand.nextFloat() < 1 - (float) trunk / height)
                            {
                                world.setBlockState(trunkPos.offset(d, 2), leaves);
                            }
                            else { continue; }
                            if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                                world.setBlockState(trunkPos.offset(d, 3), leaves);
                        }
                    }
                    world.setBlockState(pos.offset(EnumFacing.UP, height), leaves);
                }
            }
        }
    }

    private void generateGoldenBamboo(World world, Random rand, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return;

        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        if (rand.nextInt(15) != 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 240f && temp > 24f && density > 0.5f)
            {
                IBlockState state = world.getBlockState(pos.down());
                if (world.isAirBlock(pos) && state.isSideSolid(world, pos.down(), EnumFacing.UP) && BlocksTFC.isGrowableSoil(state))
                {
                    for (int air = 1; air < 15; air++)
                    {
                        if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                            return;
                    }
                    int height = 7 + rand.nextInt(5);
                    IBlockState leaves = BlocksTFCF.GOLDEN_BAMBOO_LEAVES.getDefaultState();
                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.GOLDEN_BAMBOO_LOG.getDefaultState());
                        if (trunk < 3)
                            continue;
                        for (EnumFacing d : EnumFacing.HORIZONTALS)
                        {
                            world.setBlockState(trunkPos.offset(d, 1), leaves);
                            if (rand.nextFloat() < 1 - (float) trunk / height)
                            {
                                world.setBlockState(trunkPos.offset(d, 2), leaves);
                            }
                            else { continue; }
                            if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                                world.setBlockState(trunkPos.offset(d, 3), leaves);
                        }
                    }
                    world.setBlockState(pos.offset(EnumFacing.UP, height), leaves);
                }
            }
        }
    }

    private void generateNarrowLeafBamboo(World world, Random rand, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return;

        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        if (rand.nextInt(15) != 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 240f && temp > 24f && density > 0.5f)
            {
                IBlockState state = world.getBlockState(pos.down());
                if (world.isAirBlock(pos) && state.isSideSolid(world, pos.down(), EnumFacing.UP) && BlocksTFC.isGrowableSoil(state))
                {
                    for (int air = 1; air < 15; air++)
                    {
                        if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                            return;
                    }
                    int height = 7 + rand.nextInt(5);
                    IBlockState leaves = BlocksTFCF.NARROW_LEAF_BAMBOO_LEAVES.getDefaultState();
                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.NARROW_LEAF_BAMBOO_LOG.getDefaultState());
                        if (trunk < 3)
                            continue;
                        for (EnumFacing d : EnumFacing.HORIZONTALS)
                        {
                            world.setBlockState(trunkPos.offset(d, 1), leaves);
                            if (rand.nextFloat() < 1 - (float) trunk / height)
                            {
                                world.setBlockState(trunkPos.offset(d, 2), leaves);
                            }
                            else { continue; }
                            if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                                world.setBlockState(trunkPos.offset(d, 3), leaves);
                        }
                    }
                    world.setBlockState(pos.offset(EnumFacing.UP, height), leaves);
                }
            }
        }
    }

    private void generateRedBamboo(World world, Random rand, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return;

        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        if (rand.nextInt(15) != 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 240f && temp > 24f && density > 0.5f)
            {
                IBlockState state = world.getBlockState(pos.down());
                if (world.isAirBlock(pos) && state.isSideSolid(world, pos.down(), EnumFacing.UP) && BlocksTFC.isGrowableSoil(state))
                {
                    for (int air = 1; air < 15; air++)
                    {
                        if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                            return;
                    }
                    int height = 7 + rand.nextInt(5);
                    IBlockState leaves = BlocksTFCF.RED_BAMBOO_LEAVES.getDefaultState();
                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.RED_BAMBOO_LOG.getDefaultState());
                        if (trunk < 3)
                            continue;
                        for (EnumFacing d : EnumFacing.HORIZONTALS)
                        {
                            world.setBlockState(trunkPos.offset(d, 1), leaves);
                            if (rand.nextFloat() < 1 - (float) trunk / height)
                            {
                                world.setBlockState(trunkPos.offset(d, 2), leaves);
                            }
                            else { continue; }
                            if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                                world.setBlockState(trunkPos.offset(d, 3), leaves);
                        }
                    }
                    world.setBlockState(pos.offset(EnumFacing.UP, height), leaves);
                }
            }
        }
    }

    private void generateTempleBamboo(World world, Random rand, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return;

        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        if (rand.nextInt(15) != 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 240f && temp > 24f && density > 0.5f)
            {
                IBlockState state = world.getBlockState(pos.down());
                if (world.isAirBlock(pos) && state.isSideSolid(world, pos.down(), EnumFacing.UP) && BlocksTFC.isGrowableSoil(state))
                {
                    for (int air = 1; air < 15; air++)
                    {
                        if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                            return;
                    }
                    int height = 7 + rand.nextInt(5);
                    IBlockState leaves = BlocksTFCF.TEMPLE_BAMBOO_LEAVES.getDefaultState();
                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.TEMPLE_BAMBOO_LOG.getDefaultState());
                        if (trunk < 3)
                            continue;
                        for (EnumFacing d : EnumFacing.HORIZONTALS)
                        {
                            world.setBlockState(trunkPos.offset(d, 1), leaves);
                            if (rand.nextFloat() < 1 - (float) trunk / height)
                            {
                                world.setBlockState(trunkPos.offset(d, 2), leaves);
                            }
                            else { continue; }
                            if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                                world.setBlockState(trunkPos.offset(d, 3), leaves);
                        }
                    }
                    world.setBlockState(pos.offset(EnumFacing.UP, height), leaves);
                }
            }
        }
    }

    private void generateThornyBamboo(World world, Random rand, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return;

        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        if (rand.nextInt(15) != 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 240f && temp > 24f && density > 0.5f)
            {
                IBlockState state = world.getBlockState(pos.down());
                if (world.isAirBlock(pos) && state.isSideSolid(world, pos.down(), EnumFacing.UP) && BlocksTFC.isGrowableSoil(state))
                {
                    for (int air = 1; air < 15; air++)
                    {
                        if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                            return;
                    }
                    int height = 7 + rand.nextInt(5);
                    IBlockState leaves = BlocksTFCF.THORNY_BAMBOO_LEAVES.getDefaultState();
                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.THORNY_BAMBOO_LOG.getDefaultState());
                        if (trunk < 3)
                            continue;
                        for (EnumFacing d : EnumFacing.HORIZONTALS)
                        {
                            world.setBlockState(trunkPos.offset(d, 1), leaves);
                            if (rand.nextFloat() < 1 - (float) trunk / height)
                            {
                                world.setBlockState(trunkPos.offset(d, 2), leaves);
                            }
                            else { continue; }
                            if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                                world.setBlockState(trunkPos.offset(d, 3), leaves);
                        }
                    }
                    world.setBlockState(pos.offset(EnumFacing.UP, height), leaves);
                }
            }
        }
    }

    private void generateTimberBamboo(World world, Random rand, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return;

        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        if (rand.nextInt(15) != 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 240f && temp > 24f && density > 0.5f)
            {
                IBlockState state = world.getBlockState(pos.down());
                if (world.isAirBlock(pos) && state.isSideSolid(world, pos.down(), EnumFacing.UP) && BlocksTFC.isGrowableSoil(state))
                {
                    for (int air = 1; air < 15; air++)
                    {
                        if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                            return;
                    }
                    int height = 7 + rand.nextInt(5);
                    IBlockState leaves = BlocksTFCF.TIMBER_BAMBOO_LEAVES.getDefaultState();
                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.TIMBER_BAMBOO_LOG.getDefaultState());
                        if (trunk < 3)
                            continue;
                        for (EnumFacing d : EnumFacing.HORIZONTALS)
                        {
                            world.setBlockState(trunkPos.offset(d, 1), leaves);
                            if (rand.nextFloat() < 1 - (float) trunk / height)
                            {
                                world.setBlockState(trunkPos.offset(d, 2), leaves);
                            }
                            else { continue; }
                            if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                                world.setBlockState(trunkPos.offset(d, 3), leaves);
                        }
                    }
                    world.setBlockState(pos.offset(EnumFacing.UP, height), leaves);
                }
            }
        }
    }

    private void generateTinwaBamboo(World world, Random rand, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return;

        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        if (rand.nextInt(15) != 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 240f && temp > 24f && density > 0.5f)
            {
                IBlockState state = world.getBlockState(pos.down());
                if (world.isAirBlock(pos) && state.isSideSolid(world, pos.down(), EnumFacing.UP) && BlocksTFC.isGrowableSoil(state))
                {
                    for (int air = 1; air < 15; air++)
                    {
                        if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                            return;
                    }
                    int height = 7 + rand.nextInt(5);
                    IBlockState leaves = BlocksTFCF.TINWA_BAMBOO_LEAVES.getDefaultState();
                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.TINWA_BAMBOO_LOG.getDefaultState());
                        if (trunk < 3)
                            continue;
                        for (EnumFacing d : EnumFacing.HORIZONTALS)
                        {
                            world.setBlockState(trunkPos.offset(d, 1), leaves);
                            if (rand.nextFloat() < 1 - (float) trunk / height)
                            {
                                world.setBlockState(trunkPos.offset(d, 2), leaves);
                            }
                            else { continue; }
                            if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                                world.setBlockState(trunkPos.offset(d, 3), leaves);
                        }
                    }
                    world.setBlockState(pos.offset(EnumFacing.UP, height), leaves);
                }
            }
        }
    }

    private void generateWeaversBamboo(World world, Random rand, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return;

        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        if (rand.nextInt(15) != 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 240f && temp > 24f && density > 0.5f)
            {
                IBlockState state = world.getBlockState(pos.down());
                if (world.isAirBlock(pos) && state.isSideSolid(world, pos.down(), EnumFacing.UP) && BlocksTFC.isGrowableSoil(state))
                {
                    for (int air = 1; air < 15; air++)
                    {
                        if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                            return;
                    }
                    int height = 7 + rand.nextInt(5);
                    IBlockState leaves = BlocksTFCF.WEAVERS_BAMBOO_LEAVES.getDefaultState();
                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.WEAVERS_BAMBOO_LOG.getDefaultState());
                        if (trunk < 3)
                            continue;
                        for (EnumFacing d : EnumFacing.HORIZONTALS)
                        {
                            world.setBlockState(trunkPos.offset(d, 1), leaves);
                            if (rand.nextFloat() < 1 - (float) trunk / height)
                            {
                                world.setBlockState(trunkPos.offset(d, 2), leaves);
                            }
                            else { continue; }
                            if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                                world.setBlockState(trunkPos.offset(d, 3), leaves);
                        }
                    }
                    world.setBlockState(pos.offset(EnumFacing.UP, height), leaves);
                }
            }
        }
    }
}