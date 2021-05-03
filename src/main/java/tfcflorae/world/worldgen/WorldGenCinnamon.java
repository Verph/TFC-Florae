package tfcflorae.world.worldgen;

import java.util.Random;
import java.util.stream.IntStream;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.api.util.ITreeGenerator;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.world.classic.StructureHelper;
import net.dries007.tfc.world.classic.biomes.BiomeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.ConfigTFCF;
import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.types.TreesTFCF;

public class WorldGenCinnamon extends WorldGenerator
{
    private static final PlacementSettings settings = StructureHelper.getDefaultSettings();

    private static final int numVariants = 1; // should always be 1 or higher
    private static final String[] variants = IntStream.range(1, numVariants).mapToObj(String::valueOf).toArray(String[]::new);
    private static boolean useRotation = false;

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if (rand.nextInt(ConfigTFCF.General.WORLD.cinnamonRarity) < 5)
            return false;

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

        int gen = rand.nextInt(8);

        if (gen == 0 && rain > 250 && temp > 20 && density > 0.3f && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down())) || BlocksTFCF.isGround(world.getBlockState(pos.down()))))
        {
            //return generateCinnamon(world, rand, pos, TreesTFCF.CEYLON_CINNAMON_TREE, BlocksTFCF.CASSIA_CINNAMON_LOG.getDefaultState());
            return generateCinnamonVariant(world, rand, pos, TreesTFCF.CEYLON_CINNAMON_TREE, TreesTFCF.GEN_CASSIA_CINNAMON);
        }
        if (gen == 1 && rain > 250 && temp > 20 && density > 0.3f && world.isAirBlock(pos) && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGround(world.getBlockState(pos.down())) || BlocksTFCF.isGround(world.getBlockState(pos.down()))))
        {
            //return generateCinnamon(world, rand, pos, TreesTFCF.CEYLON_CINNAMON_TREE, BlocksTFCF.CEYLON_CINNAMON_LOG.getDefaultState());
            return generateCinnamonVariant(world, rand, pos, TreesTFCF.CEYLON_CINNAMON_TREE, TreesTFCF.GEN_CASSIA_CINNAMON);
        }
        return false;
    }

    private boolean generateCinnamonVariant(World world, Random rand, BlockPos pos, Tree tree, ITreeGenerator CinnamonToGen)
    {
        IBlockState state = world.getBlockState(pos.down());
        if (world.isAirBlock(pos) && state.isSideSolid(world, pos.down(), EnumFacing.UP) && (BlocksTFC.isGrowableSoil(state)))
        {
            TemplateManager manager = ((WorldServer) world).getStructureTemplateManager();
            /*String variant = variants[variants.length == 1 ? 0 : rand.nextInt(variants.length)];
            ResourceLocation base = new ResourceLocation(tree.getRegistryName() + "/" + variant);

            Template structureBase = manager.get(world.getMinecraftServer(), base);
            if (structureBase == null)
            {
                TFCFlorae.getLog().warn("Unable to find a template for " + base.toString());
                return false;
            }

            PlacementSettings settings2 = useRotation ? StructureHelper.getRandomSettings(rand) : settings;

            BlockPos size = structureBase.getSize().rotate(settings2.getRotation());
            // Begin rotation things
            pos = pos.add(-size.getX() / 2, 0, -size.getZ() / 2);
            StructureHelper.addStructureToWorld(world, pos, structureBase, settings2);*/

            CinnamonToGen.generateTree(manager, world, pos, tree, rand, true);
            return true;
        }
        return false;
    }

    /*private boolean generateCinnamon(World world, Random rand, BlockPos pos, Tree tree, IBlockState log)
    {
        IBlockState state = world.getBlockState(pos.down());
        if (world.isAirBlock(pos) && state.isSideSolid(world, pos.down(), EnumFacing.UP) && BlocksTFC.isGrowableSoil(state))
        {
            final TemplateManager manager = ((WorldServer) world).getStructureTemplateManager();

            PlacementSettings settingsFull = StructureHelper.getDefaultSettings();
            PlacementSettings settingsWeak = StructureHelper.getDefaultSettings().setIntegrity(0.5f);
            int heightMin = 2;
            int heightRange = 2;

            ResourceLocation base = new ResourceLocation(tree.getRegistryName() + "/base");
            ResourceLocation overlay = new ResourceLocation(tree.getRegistryName() + "/overlay");

            Template structureBase = manager.get(world.getMinecraftServer(), base);
            Template structureOverlay = manager.get(world.getMinecraftServer(), overlay);

            if (structureBase == null)
            {
                TFCFlorae.getLog().warn("TFCFlorae: Unable to find a template for " + base.toString());
                return false;
            }

            int height = heightMin + (heightRange > 0 ? rand.nextInt(heightRange) : 0);

            BlockPos size = structureBase.getSize();
            pos = pos.add(-size.getX() / 2, height, -size.getZ() / 2);

            StructureHelper.addStructureToWorld(world, pos, structureBase, settingsFull);
            if (structureOverlay != null)
            {
                StructureHelper.addStructureToWorld(world, pos, structureOverlay, settingsWeak);
            }

            //final IBlockState log = BlockLogTFC.get(tree).getDefaultState().withProperty(PLACED, false);
            for (int i = 0; i < height; i++)
                world.setBlockState(pos.add(size.getX() / 2, i - height, size.getZ() / 2), log);

            return true;
        }
        return false;
    }*/
}