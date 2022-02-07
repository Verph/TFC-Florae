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

import tfcflorae.ConfigTFCF;
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

        int gen = rand.nextInt(13);

        if (gen == 0)
        {
            generateArrowBamboo(world, rand, genPos);
        }
        else if (gen == 1)
        {
            generateBlackBamboo(world, rand, genPos);
        }
        else if (gen == 2)
        {
            generateBlueBamboo(world, rand, genPos);
        }
        else if (gen == 3)
        {
            generateDragonBamboo(world, rand, genPos);
        }
        else if (gen == 4)
        {
            generateGoldenBamboo(world, rand, genPos);
        }
        else if (gen == 5)
        {
            generateNarrowLeafBamboo(world, rand, genPos);
        }
        else if (gen == 6)
        {
            generateRedBamboo(world, rand, genPos);
        }
        else if (gen == 7)
        {
            generateTempleBamboo(world, rand, genPos);
        }
        else if (gen == 8)
        {
            generateThornyBamboo(world, rand, genPos);
        }
        else if (gen == 9)
        {
            generateTimberBamboo(world, rand, genPos);
        }
        else if (gen == 10)
        {
            generateTinwaBamboo(world, rand, genPos);
        }
        else
        {
            generateWeaversBamboo(world, rand, genPos);
        }

        return false;
    }

    private void generateBambooLeaves(World world, Random rand, BlockPos pos, int height, IBlockState leaves)
    {
        for (int trunk = height + 1; trunk > 3; trunk--)
        {
            BlockPos centerPos = pos.offset(EnumFacing.UP, trunk);

            if(trunk == height + 1)
            {
                world.setBlockState(centerPos, leaves);
                for (EnumFacing d : EnumFacing.HORIZONTALS)
                {
                    world.setBlockState(centerPos.offset(d, 1), leaves);
                }
            }
            else if (trunk == height)
            {
                for (EnumFacing d : EnumFacing.HORIZONTALS)
                {
                    world.setBlockState(centerPos.offset(d, 1), leaves);
                }
                if (rand.nextFloat() < 0.2f)
                    world.setBlockState(centerPos.add(1, 0, 1), leaves);
                if (rand.nextFloat() < 0.2f)
                    world.setBlockState(centerPos.add(1, 0, -1), leaves);
                if (rand.nextFloat() < 0.2f)
                    world.setBlockState(centerPos.add(-1, 0, 1), leaves);
                if (rand.nextFloat() < 0.2f)
                    world.setBlockState(centerPos.add(-1, 0, -1), leaves);
            }
            else if (trunk == height - 1)
            {
                for (EnumFacing d : EnumFacing.HORIZONTALS)
                {
                    world.setBlockState(centerPos.offset(d, 1), leaves);
                    world.setBlockState(centerPos.offset(d, 2), leaves);
                }
                //North-East
                world.setBlockState(centerPos.add(1, 0, 1), leaves);
                world.setBlockState(centerPos.add(1, 0, 2), leaves);
                world.setBlockState(centerPos.add(2, 0, 1), leaves);
                
                //North-West
                world.setBlockState(centerPos.add(-1, 0, 1), leaves);
                world.setBlockState(centerPos.add(-1, 0, 2), leaves);
                world.setBlockState(centerPos.add(-2, 0, 1), leaves);
                
                //South-East
                world.setBlockState(centerPos.add(1, 0, -1), leaves);
                world.setBlockState(centerPos.add(1, 0, -2), leaves);
                world.setBlockState(centerPos.add(2, 0, -1), leaves);
                
                //South-West
                world.setBlockState(centerPos.add(-1, 0, -1), leaves);
                world.setBlockState(centerPos.add(-1, 0, -2), leaves);
                world.setBlockState(centerPos.add(-2, 0, -1), leaves);
            }
            else if (trunk % 2 == 0)
            {
                for (EnumFacing d : EnumFacing.HORIZONTALS)
                {
                    world.setBlockState(centerPos.offset(d, 1), leaves);
                }
            }
            else
            {
                for (EnumFacing d : EnumFacing.HORIZONTALS)
                {
                    world.setBlockState(centerPos.offset(d, 1), leaves);
                }
                if (rand.nextFloat() < 0.8f)
                    world.setBlockState(centerPos.add(1, 0, 1), leaves);
                if (rand.nextFloat() < 0.8f)
                    world.setBlockState(centerPos.add(1, 0, -1), leaves);
                if (rand.nextFloat() < 0.8f)
                    world.setBlockState(centerPos.add(-1, 0, 1), leaves);
                if (rand.nextFloat() < 0.8f)
                    world.setBlockState(centerPos.add(-1, 0, -1), leaves);
            }
        }
    }

    private void generateArrowBamboo(World world, Random rand, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return;

        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        if (rand.nextInt(ConfigTFCF.General.WORLD.bambooRarity) == 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 290f && temp > 20f && density > 0.2f)
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

                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.ARROW_BAMBOO_LOG.getDefaultState());
                        /*if (trunk < 3)
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
                        }*/
                    }
                    //world.setBlockState(pos.offset(EnumFacing.UP, height), leaves);
                    generateBambooLeaves(world, rand, pos, height, BlocksTFCF.ARROW_BAMBOO_LEAVES.getDefaultState());
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

        if (rand.nextInt(ConfigTFCF.General.WORLD.bambooRarity) == 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 290f && temp > 20f && density > 0.2f)
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

                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.BLACK_BAMBOO_LOG.getDefaultState());
                    }
                    generateBambooLeaves(world, rand, pos, height, BlocksTFCF.BLACK_BAMBOO_LEAVES.getDefaultState());
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

        if (rand.nextInt(ConfigTFCF.General.WORLD.bambooRarity) == 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 290f && temp > 20f && density > 0.2f)
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

                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.BLUE_BAMBOO_LOG.getDefaultState());

                    }
                    generateBambooLeaves(world, rand, pos, height, BlocksTFCF.BLUE_BAMBOO_LEAVES.getDefaultState());
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

        if (rand.nextInt(ConfigTFCF.General.WORLD.bambooRarity) == 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 290f && temp > 20f && density > 0.2f)
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

                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.DRAGON_BAMBOO_LOG.getDefaultState());
                    }
                    generateBambooLeaves(world, rand, pos, height, BlocksTFCF.DRAGON_BAMBOO_LEAVES.getDefaultState());
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

        if (rand.nextInt(ConfigTFCF.General.WORLD.bambooRarity) == 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 290f && temp > 20f && density > 0.2f)
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

                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.GOLDEN_BAMBOO_LOG.getDefaultState());
                    }
                    generateBambooLeaves(world, rand, pos, height, BlocksTFCF.GOLDEN_BAMBOO_LEAVES.getDefaultState());
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

        if (rand.nextInt(ConfigTFCF.General.WORLD.bambooRarity) == 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 290f && temp > 20f && density > 0.2f)
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

                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.NARROW_LEAF_BAMBOO_LOG.getDefaultState());
                    }
                    generateBambooLeaves(world, rand, pos, height, BlocksTFCF.NARROW_LEAF_BAMBOO_LEAVES.getDefaultState());
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

        if (rand.nextInt(ConfigTFCF.General.WORLD.bambooRarity) == 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 290f && temp > 20f && density > 0.2f)
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

                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.RED_BAMBOO_LOG.getDefaultState());
                    }
                    generateBambooLeaves(world, rand, pos, height, BlocksTFCF.RED_BAMBOO_LEAVES.getDefaultState());
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

        if (rand.nextInt(ConfigTFCF.General.WORLD.bambooRarity) == 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 290f && temp > 20f && density > 0.2f)
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

                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.TEMPLE_BAMBOO_LOG.getDefaultState());
                    }
                    generateBambooLeaves(world, rand, pos, height, BlocksTFCF.TEMPLE_BAMBOO_LEAVES.getDefaultState());
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

        if (rand.nextInt(ConfigTFCF.General.WORLD.bambooRarity) == 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 290f && temp > 20f && density > 0.2f)
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

                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.THORNY_BAMBOO_LOG.getDefaultState());
                    }
                    generateBambooLeaves(world, rand, pos, height, BlocksTFCF.THORNY_BAMBOO_LEAVES.getDefaultState());
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

        if (rand.nextInt(ConfigTFCF.General.WORLD.bambooRarity) == 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 290f && temp > 20f && density > 0.2f)
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

                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.TIMBER_BAMBOO_LOG.getDefaultState());
                    }
                    generateBambooLeaves(world, rand, pos, height, BlocksTFCF.TIMBER_BAMBOO_LEAVES.getDefaultState());
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

        if (rand.nextInt(ConfigTFCF.General.WORLD.bambooRarity) == 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 290f && temp > 20f && density > 0.2f)
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

                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.TINWA_BAMBOO_LOG.getDefaultState());
                    }
                    generateBambooLeaves(world, rand, pos, height, BlocksTFCF.TINWA_BAMBOO_LEAVES.getDefaultState());
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

        if (rand.nextInt(ConfigTFCF.General.WORLD.bambooRarity) == 0 || pos.getY() > WorldTypeTFC.SEALEVEL && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down()))))
        {
            if (rain > 290f && temp > 20f && density > 0.2f)
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

                    for (int trunk = 0; trunk < height; trunk++)
                    {
                        BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
                        world.setBlockState(trunkPos, BlocksTFCF.WEAVERS_BAMBOO_LOG.getDefaultState());
                    }
                    generateBambooLeaves(world, rand, pos, height, BlocksTFCF.WEAVERS_BAMBOO_LEAVES.getDefaultState());
                }
            }
        }
    }
}