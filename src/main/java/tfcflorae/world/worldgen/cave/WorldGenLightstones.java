package tfcflorae.world.worldgen.cave;

import java.util.*;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.groundcover.BlockLightstone;
import tfcflorae.types.PlantsTFCF;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

public class WorldGenLightstones implements IWorldGenerator
{
    @Override
    public void generate(Random rng, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (!(chunkGenerator instanceof ChunkGenTFC)) return;

        int y = rng.nextInt(70) + 1;
        BlockPos chunkPos = new BlockPos(chunkX << 4, y, chunkZ << 4);

        ChunkDataTFC data = ChunkDataTFC.get(world, chunkPos);
        final float floraDensity = data.getFloraDensity();
        final float floraDiversity = data.getFloraDiversity();

        int lightstoneCount = (Constants.RNG.nextInt(8) + 1);
        for (int i = rng.nextInt(Math.round(1 / floraDiversity)); i < (4 + floraDensity + floraDiversity) * lightstoneCount; i++)
        {
            BlockPos blockPos = chunkPos.add(rng.nextInt(16) + 8, rng.nextInt(16), rng.nextInt(16) + 8);
            if (blockPos.getY() < WorldTypeTFC.SEALEVEL - 30 && blockPos.getY() > 10)
            {
                BlockLightstone lightstoneBlock = BlocksTFCF.LIGHTSTONE;
                IBlockState state = lightstoneBlock.getDefaultState();
                if (world.isAirBlock(blockPos) &&
                    world.getLightFor(EnumSkyBlock.SKY, blockPos) < 14 && !world.canSeeSky(blockPos) &&
                    lightstoneBlock.canBlockStay(world, blockPos, state) &&
                    lightstoneBlock.canPlaceBlockAt(world, blockPos))
                {
                    //TFCFlorae.getLog().warn("TFCFlorae: Lightstones attempted to generate at " + "X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ());
                    world.setBlockState(blockPos, BlocksTFCF.LIGHTSTONE.getDefaultState());
                }
            }
        }
    }
}