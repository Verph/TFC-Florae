package tfcflorae.world.feature.tree.willow;

import java.util.Random;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.material.Material;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.biome.BiomeExtension;
import net.dries007.tfc.world.biome.TFCBiomes;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.feature.tree.TreeFeature;
import net.dries007.tfc.world.feature.tree.TreeHelpers;

import tfcflorae.TFCFlorae;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.wood.TFCFWood;
import tfcflorae.world.feature.tree.DynamicTreeConfig;

public class BlackWillowTreeFeature extends TreeFeature<DynamicTreeConfig>
{
    public BlackWillowTreeFeature(Codec<DynamicTreeConfig> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<DynamicTreeConfig> context)
    {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final Random random = context.random();
        final BlockState state = level.getBlockState(pos);
        final DynamicTreeConfig config = context.config();
        config.placement().height();

        final ChunkPos chunkPos = new ChunkPos(pos);
        final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos().set(pos);
        //BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        //mutablePos.set(pos).move(0, -1, 0);
        final StructureManager manager = TreeHelpers.getStructureManager(level);
        final StructurePlaceSettings settings = TreeHelpers.getPlacementSettings(level, chunkPos, random);
        final Biome biome = level.getBiome(pos).value();
        final BiomeExtension variants = TFCBiomes.getExtensionOrThrow(level, biome);
        final ChunkData data = ChunkData.get(level, pos);

        final int seaLevel = level.getLevel().getChunkSource().getGenerator().getSeaLevel();

        if (TreeHelpers.isValidLocation(level, pos, settings, config.placement()) && !Helpers.isBlock(level.getBlockState(mutablePos), TFCBlocks.SALT_WATER.get()))
        {
            if ((pos.getY() <= seaLevel + 10 || mutablePos.getY() <= seaLevel + 10) && (variants == TFCBiomes.LOWLANDS || variants == TFCBiomes.LOW_CANYONS || variants == TFCBiomes.LAKE || variants == TFCBiomes.OLD_MOUNTAIN_LAKE || variants == TFCBiomes.MOUNTAIN_LAKE || variants == TFCBiomes.RIVER || variants == TFCBiomes.OLD_MOUNTAIN_RIVER || variants == TFCBiomes.MOUNTAIN_RIVER))
            {
                config.trunk().ifPresent(trunk -> {
                    final int height = TreeHelpers.placeTrunk(level, mutablePos, random, settings, trunk);
                    mutablePos.move(0, height, 0);
                });

                final BlockState willowTreeLog = config.logState();
                final BlockState willowTreeLeaves = config.leavesState();
                final BlockState willowTreeWood = config.woodState();

                final int chance = random.nextInt(4);
                if (chance == 0)
                {
                    TFCFlorae.LOGGER.debug("generating tree at XZ" + pos.getX() + ", " + pos.getZ());
                    buildWillowVariant1(config, level, random, mutablePos, pos, willowTreeLog, willowTreeLeaves, willowTreeWood);
                    return true;
                }
                else if (chance == 1)
                {
                    TFCFlorae.LOGGER.debug("generating tree at XZ" + pos.getX() + ", " + pos.getZ());
                    buildWillowVariant2(config, level, random, mutablePos, pos, willowTreeLog, willowTreeLeaves, willowTreeWood);
                    return true;
                }
                else if (chance == 2)
                {
                    TFCFlorae.LOGGER.debug("generating tree at XZ" + pos.getX() + ", " + pos.getZ());
                    buildWillowVariant3(config, level, random, mutablePos, pos, willowTreeLog, willowTreeLeaves, willowTreeWood);
                    return true;
                }
                else
                {
                    TFCFlorae.LOGGER.debug("generating tree at XZ" + pos.getX() + ", " + pos.getZ());
                    buildWillowVariantDead(config, level, random, mutablePos, pos, willowTreeLog, willowTreeLeaves, willowTreeWood);
                    return true;
                }
            }
        }
        return false;
    }

    private void buildWillowVariant1(DynamicTreeConfig config, WorldGenLevel level, Random random, BlockPos.MutableBlockPos mutablePos, BlockPos pos, BlockState willowTreeLog, BlockState willowTreeLeaves, BlockState willowTreeWood)
    {
        int willowHeight = config.minHeight() + random.nextInt(config.placement().height());

        /*BlockState willowTreeLog = TFCFBlocks.WOODS.get(TFCFWood.BLACK_WILLOW).get(Wood.BlockType.WOOD).get().defaultBlockState();
        BlockState willowTreeLeaves = TFCFBlocks.WOODS.get(TFCFWood.BLACK_WILLOW).get(Wood.BlockType.LEAVES).get().defaultBlockState();
        BlockState willowTreeWood = TFCFBlocks.WOODS.get(TFCFWood.BLACK_WILLOW).get(Wood.BlockType.LOG).get().defaultBlockState();*/

        if (pos.getY() + willowHeight + 1 < level.getMaxBuildHeight())
        {
            for (int buildTrunk = 2; buildTrunk <= willowHeight; buildTrunk++)
            {
                placeTrunk(pos, random, level, mutablePos, willowTreeWood);
                mutablePos.move(Direction.UP);
            }
            mutablePos.set(pos);

            BlockPos.MutableBlockPos rootMutable = new BlockPos.MutableBlockPos().set(mutablePos.offset(0, 0, -2));
            BlockPos.MutableBlockPos rootMutable2 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-2, 0, 0));
            BlockPos.MutableBlockPos rootMutable3 = new BlockPos.MutableBlockPos().set(mutablePos.offset(2, 0, 0));
            BlockPos.MutableBlockPos rootMutable4 = new BlockPos.MutableBlockPos().set(mutablePos.offset(0, 0, 2));

            for (int buildRoot = 0; buildRoot <= 5; buildRoot++)
            {
                placeBranch(pos, random, level, rootMutable, willowTreeLog);
                placeBranch(pos, random, level, rootMutable2, willowTreeLog);
                placeBranch(pos, random, level, rootMutable3, willowTreeLog);
                placeBranch(pos, random, level, rootMutable4, willowTreeLog);

                rootMutable.move(Direction.DOWN);
                rootMutable2.move(Direction.DOWN);
                rootMutable3.move(Direction.DOWN);
                rootMutable4.move(Direction.DOWN);
            }

            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 1, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 1, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 1, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, 1, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 1, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 2, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 2, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 2, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 2, 1), willowTreeLog);


            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight, -6), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight, -5), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, -4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(5, willowHeight, -4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, 5), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, -5), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, -4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, -4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, -4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, -3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, -3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 6), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 7), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 5), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 5), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 6), willowTreeLog);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 4, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 3, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 3, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 3, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 3, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 3, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 2, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 2, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 2, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 2, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 2, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 1, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 1, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, -7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight + 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, -6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 3, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 3, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 3, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 3, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 3, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 3, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 4, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 4, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 4, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 4, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 4, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 4, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 4, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 4, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 4, 6), willowTreeLeaves);
        }
    }

    private void buildWillowVariant2(DynamicTreeConfig config, WorldGenLevel level, Random random, BlockPos.MutableBlockPos mutablePos, BlockPos pos, BlockState willowTreeLog, BlockState willowTreeLeaves, BlockState willowTreeWood)
    {
        int willowHeight = config.minHeight() + random.nextInt(config.placement().height());

        /*BlockState willowTreeLog = TFCFBlocks.WOODS.get(TFCFWood.BLACK_WILLOW).get(Wood.BlockType.WOOD).get().defaultBlockState();
        BlockState willowTreeLeaves = TFCFBlocks.WOODS.get(TFCFWood.BLACK_WILLOW).get(Wood.BlockType.LEAVES).get().defaultBlockState();
        BlockState willowTreeWood = TFCFBlocks.WOODS.get(TFCFWood.BLACK_WILLOW).get(Wood.BlockType.LOG).get().defaultBlockState();*/

        if (pos.getY() + willowHeight + 1 < level.getMaxBuildHeight())
        {
            for (int buildTrunk = 0; buildTrunk <= willowHeight; buildTrunk++)
            {
                placeTrunk(pos, random, level, mutablePos, willowTreeWood);
                mutablePos.move(Direction.UP);
            }
            mutablePos.set(pos);

            BlockPos.MutableBlockPos rootMutable = new BlockPos.MutableBlockPos().set(mutablePos.offset(0, 0, -2));
            BlockPos.MutableBlockPos rootMutable2 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-2, 0, 0));
            BlockPos.MutableBlockPos rootMutable3 = new BlockPos.MutableBlockPos().set(mutablePos.offset(2, 0, 0));
            BlockPos.MutableBlockPos rootMutable4 = new BlockPos.MutableBlockPos().set(mutablePos.offset(0, 0, 2));

            for (int buildRoot = 0; buildRoot <= 5; buildRoot++)
            {
                placeBranch(pos, random, level, rootMutable, willowTreeLog);
                placeBranch(pos, random, level, rootMutable2, willowTreeLog);
                placeBranch(pos, random, level, rootMutable3, willowTreeLog);
                placeBranch(pos, random, level, rootMutable4, willowTreeLog);

                rootMutable.move(Direction.DOWN);
                rootMutable2.move(Direction.DOWN);
                rootMutable3.move(Direction.DOWN);
                rootMutable4.move(Direction.DOWN);
            }

            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 1, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 1, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, 1, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 1, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 2, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 2, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 2, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 2, 1), willowTreeLog);

            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 4, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 4, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 2, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 2, 7), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 1, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 1, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-8, willowHeight - 1, 5), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, 5), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 1, 5), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 1, 6), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 1, 6), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, 9), willowTreeLog);

            placeBranch(pos, random, level, mutablePos.set(pos).move(-6, willowHeight, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-9, willowHeight, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-8, willowHeight, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-7, willowHeight, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-6, willowHeight, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-7, willowHeight, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, 5), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 5), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-7, willowHeight, 6), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, 6), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight, 6), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 6), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight, 7), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 7), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 8), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight, 8), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 9), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 6), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 2, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 5), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 6), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, 5), willowTreeLog);

            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 4, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 4, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 4, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 4, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 4, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 4, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, willowHeight - 4, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight - 4, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight - 4, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, willowHeight - 4, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 4, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 4, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight - 3, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 3, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight - 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight - 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 3, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 3, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight - 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight - 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, willowHeight - 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight - 3, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight - 3, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, willowHeight - 3, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight - 3, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 3, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 3, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 3, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, willowHeight - 3, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 3, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 3, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 3, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 3, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight - 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight - 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight - 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight - 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-10, willowHeight - 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-10, willowHeight - 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight - 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight - 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight - 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight - 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, willowHeight - 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight - 2, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight - 2, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 2, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 2, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, willowHeight - 2, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 2, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 2, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 2, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 2, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 2, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 2, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 2, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 2, 10), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 2, 10), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight - 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight - 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight - 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-10, willowHeight - 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight - 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight - 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight - 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight - 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-10, willowHeight - 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight - 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight - 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight - 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-10, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight - 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight - 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight - 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, willowHeight - 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight - 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight - 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, willowHeight - 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 1, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, 10), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 1, 10), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, 10), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-10, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-10, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-10, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, willowHeight, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, willowHeight, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 10), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, 10), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 10), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-9, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight + 1, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight + 1, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, 8), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 9), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-8, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, willowHeight + 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, 6), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 7), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, 7), willowTreeLeaves);
        }
    }

    private void buildWillowVariant3(DynamicTreeConfig config, WorldGenLevel level, Random random, BlockPos.MutableBlockPos mutablePos, BlockPos pos, BlockState willowTreeLog, BlockState willowTreeLeaves, BlockState willowTreeWood)
    {
        int willowHeight = config.minHeight() + random.nextInt(config.placement().height());

        /*BlockState willowTreeLog = TFCFBlocks.WOODS.get(TFCFWood.BLACK_WILLOW).get(Wood.BlockType.WOOD).get().defaultBlockState();
        BlockState willowTreeLeaves = TFCFBlocks.WOODS.get(TFCFWood.BLACK_WILLOW).get(Wood.BlockType.LEAVES).get().defaultBlockState();
        BlockState willowTreeWood = TFCFBlocks.WOODS.get(TFCFWood.BLACK_WILLOW).get(Wood.BlockType.LOG).get().defaultBlockState();*/

        BlockPos.MutableBlockPos mainmutable2 = mutablePos.set(pos.relative(Direction.NORTH));
        BlockPos.MutableBlockPos mainmutable3 = mutablePos.set(pos.relative(Direction.SOUTH));
        BlockPos.MutableBlockPos mainmutable4 = mutablePos.set(pos.relative(Direction.WEST));
        BlockPos.MutableBlockPos mainmutable5 = mutablePos.set(pos.relative(Direction.EAST));

        if (pos.getY() + willowHeight + 1 < level.getMaxBuildHeight())
        {
            for (int buildTrunk = 3; buildTrunk <= willowHeight; buildTrunk++)
            {
                if (buildTrunk == 3)
                mutablePos.move(Direction.UP, 3);
                if (buildTrunk == 3)
                {
                    mainmutable2.move(Direction.UP, 4);
                    mainmutable3.move(Direction.UP, 4);
                    mainmutable4.move(Direction.UP, 4);
                    mainmutable5.move(Direction.UP, 4);
                }
                placeTrunk(pos, random, level, mutablePos, willowTreeWood);

                if (buildTrunk <= willowHeight - 3)
                {
                    placeTrunk(pos, random, level, mainmutable2, willowTreeWood);
                    placeTrunk(pos, random, level, mainmutable3, willowTreeWood);
                    placeTrunk(pos, random, level, mainmutable4, willowTreeWood);
                    placeTrunk(pos, random, level, mainmutable5, willowTreeWood);
                }

                mutablePos.move(Direction.UP);
                mainmutable2.move(Direction.UP);
                mainmutable3.move(Direction.UP);
                mainmutable4.move(Direction.UP);
                mainmutable5.move(Direction.UP);
            }
            mutablePos.set(pos);

            //Roots
            BlockPos.MutableBlockPos rootMutable = new BlockPos.MutableBlockPos().set(mutablePos.offset(-4, 0, 0));
            BlockPos.MutableBlockPos rootMutable2 = new BlockPos.MutableBlockPos().set(mutablePos.offset(4, 0, 0));
            BlockPos.MutableBlockPos rootMutable3 = new BlockPos.MutableBlockPos().set(mutablePos.offset(0, 0, -4));
            BlockPos.MutableBlockPos rootMutable4 = new BlockPos.MutableBlockPos().set(mutablePos.offset(0, 0, 4));

            for (int buildRoot = 0; buildRoot <= 5; buildRoot++)
            {
                placeBranch(pos, random, level, rootMutable, willowTreeLog);
                placeBranch(pos, random, level, rootMutable2, willowTreeLog);
                placeBranch(pos, random, level, rootMutable3, willowTreeLog);
                placeBranch(pos, random, level, rootMutable4, willowTreeLog);

                for (Direction direction : Direction.Plane.HORIZONTAL)
                {
                    if (direction != Direction.WEST)
                        placeBranch(pos, random, level, rootMutable.relative(direction), willowTreeLog);
                    if (direction != Direction.EAST)
                        placeBranch(pos, random, level, rootMutable2.relative(direction), willowTreeLog);
                    if (direction != Direction.NORTH)
                        placeBranch(pos, random, level, rootMutable3.relative(direction), willowTreeLog);
                    if (direction != Direction.SOUTH)
                        placeBranch(pos, random, level, rootMutable4.relative(direction), willowTreeLog);
                }

                rootMutable.move(Direction.DOWN);
                rootMutable2.move(Direction.DOWN);
                rootMutable3.move(Direction.DOWN);
                rootMutable4.move(Direction.DOWN);
            }

            //Roots/Stump
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 1, -4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 1, -4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 1, -4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-4, 1, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, 1, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-4, 1, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, 1, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-4, 1, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, 1, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 1, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 1, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 1, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 2, -4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-4, 2, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, 2, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 2, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 3, -3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, 3, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, 3, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 3, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 4, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 4, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, 4, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 4, 2), willowTreeLog);

            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 1, -4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 1, -3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 1, -3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 1, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 1, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 1, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight, -4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, -3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight, -3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, -4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, -3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, -3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, -3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 4), willowTreeLog);

            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 6, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 5, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 5, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 5, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 5, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 5, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 5, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 5, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 4, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 4, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 4, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 4, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 4, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 4, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 4, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 4, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 4, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 4, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 4, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 4, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 4, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 4, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 4, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 4, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 4, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 4, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 3, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 3, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 3, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 3, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 3, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 3, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 3, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 3, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 3, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 3, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 3, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 3, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 3, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 3, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 3, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 3, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 3, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 3, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 3, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 2, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 2, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 2, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 2, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 2, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 2, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight - 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight - 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight - 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight - 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, -5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 2, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 5), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 3, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 3, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 3, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 3, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 3, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 3, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 3, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 3, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 3, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 3, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 3, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 3, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 3, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 3, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 3, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 3, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, 4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, 4), willowTreeLeaves);
        }
    }

    private void buildWillowVariantDead(DynamicTreeConfig config, WorldGenLevel level, Random random, BlockPos.MutableBlockPos mutablePos, BlockPos pos, BlockState willowTreeLog, BlockState willowTreeLeaves, BlockState willowTreeWood)
    {
        int willowHeight = config.minHeight() + random.nextInt(config.placement().height());

        /*BlockState willowTreeLog = TFCFBlocks.WOODS.get(TFCFWood.BLACK_WILLOW).get(Wood.BlockType.WOOD).get().defaultBlockState();
        BlockState willowTreeLeaves = TFCFBlocks.WOODS.get(TFCFWood.BLACK_WILLOW).get(Wood.BlockType.LEAVES).get().defaultBlockState();
        BlockState willowTreeWood = TFCFBlocks.WOODS.get(TFCFWood.BLACK_WILLOW).get(Wood.BlockType.LOG).get().defaultBlockState();*/

        if (pos.getY() + willowHeight + 1 < level.getMaxBuildHeight())
        {
            mutablePos.set(pos).move(-1, 0, -2).immutable();
            mutablePos.set(pos).move(0, 0, -2).immutable();
            mutablePos.set(pos).move(1, 0, -2).immutable();
            mutablePos.set(pos).move(-1, 0, -1).immutable();
            mutablePos.set(pos).move(0, 0, -1).immutable();
            mutablePos.set(pos).move(3, 0, -1).immutable();
            mutablePos.set(pos).move(-2, 0, 0).immutable();
            mutablePos.set(pos).move(-1, 0, 0).immutable();
            mutablePos.set(pos).move(1, 0, 0).immutable();
            mutablePos.set(pos).move(-1, 0, 1).immutable();
            mutablePos.set(pos).move(0, 0, 1).immutable();
            mutablePos.set(pos).move(1, 0, 1).immutable();

            for (int buildTrunk = 0; buildTrunk <= willowHeight; buildTrunk++)
            {
                placeTrunk(pos, random, level, mutablePos, willowTreeWood);
                mutablePos.move(Direction.UP);
            }
            mutablePos.set(pos);

            //Stump
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 0, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 0, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 0, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 0, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 0, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, 0, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 0, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 0, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 0, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 0, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 0, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 0, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 1, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 1, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 1, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 1, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, 1, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 1, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 1, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 1, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 2, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 2, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 3, -1), willowTreeLog);

            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-4, willowHeight + 1, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, 0), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, -4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, -3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 2, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, -1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(5, willowHeight + 2, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 2, 4), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 3, -3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 3, -2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 3, 1), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 3, 2), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 3, 3), willowTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 1, 1), willowTreeLog);

            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 2, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight - 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight - 1, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 1, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 1, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 1, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, willowHeight + 1, 0), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 1, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 2, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, willowHeight + 2, -3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, willowHeight + 2, -2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 2, 2), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 2, 3), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, willowHeight + 3, -4), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, willowHeight + 3, -1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, willowHeight + 3, 1), willowTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, willowHeight + 3, 2), willowTreeLeaves);
        }
    }

    public boolean canLogPlaceHere(LevelSimulatedReader level, BlockPos pos)
    {
        //final BlockState stateAt = level.getBlockState(pos);
        return level.isStateAtPosition(pos, (stateAt) -> stateAt.getMaterial() == Material.AIR || stateAt.getMaterial() == Material.WATER || EnvironmentHelpers.isWorldgenReplaceable(stateAt) || Helpers.isBlock(stateAt.getBlock(), BlockTags.LEAVES));
    }

    public void placeTrunk(BlockPos startPos, Random random, WorldGenLevel level, BlockPos pos, BlockState state)
    {
        pos = getTransformedPos(startPos, pos);
        if (canLogPlaceHere(level, pos))
        {
            this.setFinalBlockState(level, pos, state);
        }
    }

    public void placeBranch(BlockPos startPos, Random random, WorldGenLevel level, BlockPos pos, BlockState state)
    {
        pos = getTransformedPos(startPos, pos);
        if (canLogPlaceHere(level, pos))
        {
            this.setFinalBlockState(level, pos, state);
        }
    }

    private void placeLeaves(BlockPos startPos, Random random, WorldGenLevel level, BlockPos pos, BlockState state)
    {
        if (isAir(level, pos))
        {
            this.setFinalBlockState(level, pos, state);
        }
    }

    public final void setFinalBlockState(LevelWriter  level, BlockPos pos, BlockState blockState)
    {
        this.setBlockStateWithoutUpdates(level, pos, blockState);
    }

    public void setBlockStateWithoutUpdates(LevelWriter  level, BlockPos pos, BlockState blockState)
    {
        level.setBlock(pos, blockState, 18);
    }

    public void setBlockStateWithoutUpdates(LevelWriter  level, BlockPos pos, BlockState blockState, int flags)
    {
        level.setBlock(pos, blockState, flags);
    }

    public BlockPos getTransformedPos(BlockPos startPos, BlockPos pos)
    {
        BlockPos blockPos = extractOffset(startPos, pos);
        return blockPos.offset(startPos.getX(), 0, startPos.getY());
    }

    public BlockPos extractOffset(BlockPos startPos, BlockPos pos)
    {
        return new BlockPos(startPos.getX() - pos.getX(), pos.getY(), startPos.getZ() - pos.getZ());
    }
}