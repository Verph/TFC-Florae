package tfcflorae.world.worldgen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGeneratorTFCF implements IWorldGenerator
{
    private final WorldGenCinnamon cinnamon_trees = new WorldGenCinnamon();
    private final WorldGenBamboo bamboo_trees = new WorldGenBamboo();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        BlockPos center = new BlockPos(chunkX * 16 + 8, world.getHeight(chunkX * 16 + 8, chunkZ * 16 + 8), chunkZ * 16 + 8);

        cinnamon_trees.generate(world, random, center);
        bamboo_trees.generate(world, random, center);
    }
}