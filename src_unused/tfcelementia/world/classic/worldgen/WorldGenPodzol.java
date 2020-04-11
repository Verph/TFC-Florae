package tfcelementia.world.classic.worldgen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.dries007.tfc.world.classic.worldgen.*;

import tfcelementia.api.registries.TFCERegistries;
import tfcelementia.api.types.RockTFCE;
import tfcelementia.objects.blocks.BlocksTFCE;
import tfcelementia.objects.blocks.stone.BlockRockVariantTFCE;
import tfcelementia.world.classic.chunkdata.ChunkDataTFCE;
import tfcelementia.world.classic.worldgen.*;

/**
 * todo: make these bigger without causing cascading lag.
 * This will require larger re-writes on the scale of oregen
 * Wait for 1.14+ as AlcatrazEscapee is doing a worldgen rewrite anyway
 */
public class WorldGenPodzol implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (!(chunkGenerator instanceof ChunkGenTFC)) return;
        final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);

        BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generatePodzol(world, random, pos);

        pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generatePodzol(world, random, pos);
    }

    private boolean generatePodzol(World world, Random rng, BlockPos start)
    {
        // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
        // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
        int radius = rng.nextInt(6) + 2;
        int depth = rng.nextInt(1);
        
        if (rng.nextInt(60) != 0 || start.getY() > WorldTypeTFC.SEALEVEL + 1) return false;
        ChunkDataTFCE data = ChunkDataTFCE.get(world, start);
        if (data.isInitialized() && data.getRainfall() >= 190 && data.getFloraDiversity() >= 0.5f && data.getFloraDensity() >= 0.5f && world.getBiome(start).getHeightVariation() < 0.15)
            return false;

        for (int x = -radius; x <= radius; ++x)
        {
            for (int z = -radius; z <= radius; ++z)
            {
                if (x * x + z * z > radius * radius) continue;

                boolean flag = false;
                for (int y = -depth; y <= depth; ++y)
                {
                    final BlockPos pos = start.add(x, y, z);
                    final IBlockState current = world.getBlockState(pos);

                    if (BlocksTFCE.isGrass(current))
                    {
                    	world.setBlockState(pos, BlockRockVariantTFCE.get(ChunkDataTFCE.getRockHeight(world, pos), RockTFCE.Type.PODZOL).getDefaultState(), 2);
                        flag = true;
                    }
                }
            }
        }
        return true;
    }
}