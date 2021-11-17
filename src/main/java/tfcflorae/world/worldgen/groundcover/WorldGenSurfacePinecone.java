package tfcflorae.world.worldgen.groundcover;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.ConfigTFCF;
import tfcflorae.objects.blocks.BlocksTFCF;

public class WorldGenSurfacePinecone implements IWorldGenerator
{
    private double factor;

    public WorldGenSurfacePinecone()
    {
        factor = 1;
    }

    public void setFactor(double factor)
    {
        if (factor < 0) factor = 0;
        if (factor > 1) factor = 1;
        this.factor = factor;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        final ChunkDataTFC baseChunkData = ChunkDataTFC.get(world, chunkBlockPos);

        if (chunkGenerator instanceof ChunkGenTFC && world.provider.getDimension() == 0)
        {

            int xoff = chunkX * 16 + 8;
            int zoff = chunkZ * 16 + 8;

            for (int i = 0; i < ConfigTFCF.General.WORLD.groundcoverPineconeFrequency * factor; i++)
            {
                BlockPos pos = new BlockPos(
                    xoff + random.nextInt(16),
                    0,
                    zoff + random.nextInt(16)
                );
                generateRock(world, pos.up(world.getTopSolidOrLiquidBlock(pos).getY()));
            }
        }
    }

    private void generateRock(World world, BlockPos pos)
    {
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);
        if (!chunkData.isInitialized()) return;

        final float diversity = chunkData.getFloraDiversity();
        final float density = chunkData.getFloraDensity();
        final float temp = chunkData.getAverageTemp();
        final float rain = chunkData.getRainfall();

        if (pos.getY() > 146 && pos.getY() < 170)
        {
            if (temp <= 15 && density > 0.3f)
            {
                if (world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP))
                {
                    if (BlocksTFC.isSoil(world.getBlockState(pos.down())) || BlocksTFCF.isSoil(world.getBlockState(pos.down())) || BlocksTFC.isSoilOrGravel(world.getBlockState(pos.down())) || BlocksTFCF.isSoilOrGravel(world.getBlockState(pos.down())))
                    {
                        world.setBlockState(pos, BlocksTFCF.PINECONE.getDefaultState());
                    }
                }
            }
        }
    }
}