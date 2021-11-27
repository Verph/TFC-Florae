package tfcflorae.world.worldgen.groundcover;

import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.dries007.tfc.world.classic.worldgen.vein.Vein;

import tfcflorae.objects.blocks.groundcover.BlockSurfaceRock;
import tfcflorae.ConfigTFCF;
import tfcflorae.objects.blocks.BlocksTFCF;

public class WorldGenSurfaceRocks implements IWorldGenerator
{
    private double factor;

    public WorldGenSurfaceRocks()
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
        if (chunkGenerator instanceof ChunkGenTFC && world.provider.getDimension() == 0)
        {
            final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
            final ChunkDataTFC baseChunkData = ChunkDataTFC.get(world, chunkBlockPos);

            int xoff = chunkX * 16 + 8;
            int zoff = chunkZ * 16 + 8;

            for (int i = 0; i < ConfigTFCF.General.WORLD.groundcoverRockFrequency * factor; i++)
            {
                BlockPos pos = new BlockPos(
                    xoff + random.nextInt(16),
                    0,
                    zoff + random.nextInt(16)
                );
                Rock rock = baseChunkData.getRock1(pos);
                generateRock(world, pos.up(world.getTopSolidOrLiquidBlock(pos).getY()), null, rock);
            }
        }
    }

    private void generateRock(World world, BlockPos pos, @Nullable Vein vein, Rock rock)
    {
        if (world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isSoil(world.getBlockState(pos.down())) || BlocksTFCF.isSoil(world.getBlockState(pos.down())) || BlocksTFC.isRawStone(world.getBlockState(pos.down()))))
        {
            world.setBlockState(pos, BlockSurfaceRock.get(rock).getDefaultState());
        }
    }
}