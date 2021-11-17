package tfcflorae.world.worldgen.groundcover;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.ConfigTFCF;
import tfcflorae.objects.blocks.BlocksTFCF;

public class WorldGenSurfaceBones implements IWorldGenerator
{
    private double factor;

    public WorldGenSurfaceBones()
    {
        factor = 1;
    }

    public void setFactor(double factor)
    {
        if (factor < 0) factor = 0;
        if (factor > 1) factor = 1;
        this.factor = factor;
    }

    public int getBoneFrequency(World world, BlockPos pos, double groundBoneFrequency)
    {
        float rainfall = ChunkDataTFC.get(world, pos).getRainfall();

        if (rainfall <= 20)
            return (int)(groundBoneFrequency / (1D + Math.pow(0.7D, (double)rainfall - 5.9D)));
        else
            return (int)(groundBoneFrequency / (1D + Math.pow(1.15D, (double)rainfall - 50D)));
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

            //for (int i = 0; i < ConfigTFCF.General.WORLD.groundcoverBonesFrequency * factor; i++)
            for (int i = 0; i < getBoneFrequency(world, chunkBlockPos, ConfigTFCF.General.WORLD.groundcoverBonesFrequency); i++)
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
        ChunkDataTFC data = ChunkDataTFC.get(world, pos);
        if (pos.getY() > 146 && pos.getY() < 170 && data.getRainfall() <= 75)
        {
            if (world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down())) || BlocksTFCF.isGround(world.getBlockState(pos.down()))))
            {
                world.setBlockState(pos, BlocksTFCF.BONES.getDefaultState());
            }
        }
    }
}