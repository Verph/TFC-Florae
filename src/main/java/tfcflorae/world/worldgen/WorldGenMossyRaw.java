package tfcflorae.world.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.dries007.tfc.world.classic.worldgen.vein.Vein;
import net.dries007.tfc.world.classic.worldgen.vein.VeinRegistry;

import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

public class WorldGenMossyRaw implements IWorldGenerator
{
    public static final float RAINFALL_SAND = 75;
    public static final float RAINFALL_SAND_SANDY_MIX = 125;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (!(chunkGenerator instanceof ChunkGenTFC)) return;

        int y = random.nextInt(200 - WorldTypeTFC.ROCKLAYER2) + WorldTypeTFC.ROCKLAYER2;
        BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y, chunkZ << 4);

        int rarity = (random.nextInt(20) + 1);

        for (float r = rarity; r < (5 + rarity); r++)
        {
            ChunkDataTFC data = ChunkDataTFC.get(world, chunkBlockPos);
            final float floraDensity = data.getFloraDensity();
            final float floraDiversity = data.getFloraDiversity();

            if (data.isInitialized() && data.getRainfall() >= RAINFALL_SAND_SANDY_MIX)
            {
                int mossyCount = (random.nextInt(20) + 1);
                for (int i = random.nextInt(Math.round(1 + floraDiversity)); i < (mossyCount + floraDensity) * 10; i++)
                {
                    BlockPos blockPos = chunkBlockPos.add(random.nextInt(16) + 8, random.nextInt(16), random.nextInt(16) + 8);
                    if (BlocksTFC.isRawStone(world.getBlockState(blockPos)) && (world.isAirBlock(blockPos.up()) || world.isAirBlock(blockPos.down()) || world.isAirBlock(blockPos.north()) || world.isAirBlock(blockPos.south()) || world.isAirBlock(blockPos.east()) || world.isAirBlock(blockPos.west())) && 
                        world.getLightFor(EnumSkyBlock.SKY, blockPos) < 14 && !world.canSeeSky(blockPos))
                    {
                        world.setBlockState(blockPos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, blockPos), RockTFCF.MOSSY_RAW).getDefaultState(), 2);
                    }
                }
            }
        }
    }
}
