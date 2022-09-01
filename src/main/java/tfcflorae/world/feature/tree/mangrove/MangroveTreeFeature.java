package tfcflorae.world.feature.tree.mangrove;

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
import net.dries007.tfc.common.blocks.TFCMaterials;
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

public class MangroveTreeFeature extends TreeFeature<DynamicTreeConfig>
{
    public MangroveTreeFeature(Codec<DynamicTreeConfig> codec)
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
        final StructurePlaceSettings settings = TreeHelpers.getPlacementSettings(level, chunkPos, random);
        final Biome biome = level.getBiome(pos).value();
        final BiomeExtension variants = TFCBiomes.getExtensionOrThrow(level, biome);
        final int seaLevel = level.getLevel().getChunkSource().getGenerator().getSeaLevel();

        if (TreeHelpers.isValidLocation(level, pos, settings, config.placement()) || isSaltWater(level, pos))
        {
            if ((pos.getY() <= seaLevel + 10 || mutablePos.getY() <= seaLevel + 10) && (variants == TFCBiomes.SHORE || variants == TFCBiomes.OCEANIC_MOUNTAIN_RIVER || variants == TFCBiomes.VOLCANIC_OCEANIC_MOUNTAIN_RIVER || variants == TFCBiomes.OCEANIC_MOUNTAIN_LAKE || variants == TFCBiomes.VOLCANIC_OCEANIC_MOUNTAIN_LAKE))
            {
                config.trunk().ifPresent(trunk -> {
                    final int height = TreeHelpers.placeTrunk(level, mutablePos, random, settings, trunk);
                    mutablePos.move(0, height, 0);
                });

                final BlockState mangroveTreeLog = config.logState();
                final BlockState mangroveTreeLeaves = config.leavesState();
                final BlockState mangroveTreeWood = config.woodState();

                final int chance = random.nextInt(5);
                if (chance == 0)
                {
                    TFCFlorae.LOGGER.debug("generating tree at XZ" + pos.getX() + ", " + pos.getZ());
                    buildMangroveVariant1(config, level, random, mutablePos, pos, mangroveTreeLog, mangroveTreeLeaves, mangroveTreeWood);
                    return true;
                }
                else if (chance == 1)
                {
                    TFCFlorae.LOGGER.debug("generating tree at XZ" + pos.getX() + ", " + pos.getZ());
                    buildMangroveVariant2(config, level, random, mutablePos, pos, mangroveTreeLog, mangroveTreeLeaves, mangroveTreeWood);
                    return true;
                }
                else if (chance == 2)
                {
                    TFCFlorae.LOGGER.debug("generating tree at XZ" + pos.getX() + ", " + pos.getZ());
                    buildMangroveVariant3(config, level, random, mutablePos, pos, mangroveTreeLog, mangroveTreeLeaves, mangroveTreeWood);
                    return true;
                }
                else if (chance == 3)
                {
                    TFCFlorae.LOGGER.debug("generating tree at XZ" + pos.getX() + ", " + pos.getZ());
                    buildMangroveVariant4(config, level, random, mutablePos, pos, mangroveTreeLog, mangroveTreeLeaves, mangroveTreeWood);
                    return true;
                }
                else
                {
                    TFCFlorae.LOGGER.debug("generating tree at XZ" + pos.getX() + ", " + pos.getZ());
                    buildMangroveVariant5(config, level, random, mutablePos, pos, mangroveTreeLog, mangroveTreeLeaves, mangroveTreeWood);
                    return true;
                }
            }
        }
        return false;
    }

    private void buildMangroveVariant1(DynamicTreeConfig config, WorldGenLevel level, Random random, BlockPos.MutableBlockPos mutablePos, BlockPos pos, BlockState mangroveTreeLog, BlockState mangroveTreeLeaves, BlockState mangroveTreeWood)
    {
        int mangroveHeight = config.minHeight() + random.nextInt(config.placement().height());

        if (pos.getY() + mangroveHeight + 1 < level.getMaxBuildHeight())
        {
            for (int buildTrunk = 3; buildTrunk <= mangroveHeight; buildTrunk++)
            {
                if (buildTrunk == 3)
                    mutablePos.move(Direction.UP, 3);
                placeTrunk(pos, random, level, mutablePos, mangroveTreeWood);

                mutablePos.move(Direction.UP);
            }
            mutablePos.set(pos);

            //Roots
            BlockPos.MutableBlockPos rootMutable = new BlockPos.MutableBlockPos().set(mutablePos.offset(0, 0, -2));
            BlockPos.MutableBlockPos rootMutable2 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-1, 0, -1));
            BlockPos.MutableBlockPos rootMutable3 = new BlockPos.MutableBlockPos().set(mutablePos.offset(2, 0, -4));
            BlockPos.MutableBlockPos rootMutable4 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-3, 0, 1));
            BlockPos.MutableBlockPos rootMutable5 = new BlockPos.MutableBlockPos().set(mutablePos.offset(1, 0, 1));
            BlockPos.MutableBlockPos rootMutable6 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-2, 0, 2));

            //Stump
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 0, -2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 0, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 0, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, 0, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 0, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 0, 2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 1, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 1, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 1, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 2, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 2, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 2, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 3, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 3, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 3, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 4, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 4, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 4, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 5, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 6, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, 2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, 3), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 3), mangroveTreeLog);

            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight - 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight - 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight - 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight - 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight - 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight - 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight - 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight - 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight - 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight - 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight - 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight - 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight - 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight - 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight - 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight - 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, 6), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 6), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, 3), mangroveTreeLeaves);
        }
    }

    private void buildMangroveVariant2(DynamicTreeConfig config, WorldGenLevel level, Random random, BlockPos.MutableBlockPos mutablePos, BlockPos pos, BlockState mangroveTreeLog, BlockState mangroveTreeLeaves, BlockState mangroveTreeWood)
    {
        int mangroveHeight = config.minHeight() + random.nextInt(config.placement().height());

        if (pos.getY() + mangroveHeight + 1 < level.getMaxBuildHeight())
        {
            for (int buildTrunk = 3; buildTrunk <= mangroveHeight; buildTrunk++)
            {
                if (buildTrunk == 3)
                    mutablePos.move(Direction.UP, 3);
                placeTrunk(pos, random, level, mutablePos, mangroveTreeWood);

                mutablePos.move(Direction.UP);
            }
            mutablePos.set(pos);

            //Roots
            BlockPos.MutableBlockPos rootMutable = new BlockPos.MutableBlockPos().set(mutablePos.offset(1, 0, -2));
            BlockPos.MutableBlockPos rootMutable2 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-1, 0, -1));
            BlockPos.MutableBlockPos rootMutable3 = new BlockPos.MutableBlockPos().set(mutablePos.offset(1, 0, -1));
            BlockPos.MutableBlockPos rootMutable4 = new BlockPos.MutableBlockPos().set(mutablePos.offset(2, 0, -1));
            BlockPos.MutableBlockPos rootMutable5 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-2, 0, 0));
            BlockPos.MutableBlockPos rootMutable6 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-1, 0, 0));
            BlockPos.MutableBlockPos rootMutable7 = new BlockPos.MutableBlockPos().set(mutablePos.offset(0, 0, 1));
            BlockPos.MutableBlockPos rootMutable8 = new BlockPos.MutableBlockPos().set(mutablePos.offset(1, 0, 1));
            BlockPos.MutableBlockPos rootMutable9 = new BlockPos.MutableBlockPos().set(mutablePos.offset(0, 0, 2));

            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 0, -2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 0, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 0, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 0, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 0, 2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 1, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 1, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 2, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 2, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 2, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 3, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, -2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, -2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, 2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, 2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, 3), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 3), mangroveTreeLog);

            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight - 1, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight - 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight - 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight - 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight - 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight - 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight - 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight - 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight - 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight - 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight - 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight - 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight - 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight - 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight - 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight - 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight - 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight - 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight - 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight - 1, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight - 1, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, -5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 6), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, 3), mangroveTreeLeaves);
        }
    }

    private void buildMangroveVariant3(DynamicTreeConfig config, WorldGenLevel level, Random random, BlockPos.MutableBlockPos mutablePos, BlockPos pos, BlockState mangroveTreeLog, BlockState mangroveTreeLeaves, BlockState mangroveTreeWood)
    {
        int mangroveHeight = config.minHeight() + random.nextInt(config.placement().height());

        if (pos.getY() + mangroveHeight + 1 < level.getMaxBuildHeight())
        {
            for (int buildTrunk = 3; buildTrunk <= mangroveHeight; buildTrunk++)
            {
                if (buildTrunk == 3)
                    mutablePos.move(Direction.UP, 3);
                placeTrunk(pos, random, level, mutablePos, mangroveTreeWood);

                mutablePos.move(Direction.UP);
            }
            mutablePos.set(pos);

            //Roots
            BlockPos.MutableBlockPos rootMutable = new BlockPos.MutableBlockPos().set(mutablePos.offset(1, 0, -3));
            BlockPos.MutableBlockPos rootMutable2 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-2, 0, -2));
            BlockPos.MutableBlockPos rootMutable3 = new BlockPos.MutableBlockPos().set(mutablePos.offset(2, 0, -2));
            BlockPos.MutableBlockPos rootMutable4 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-3, 0, -1));
            BlockPos.MutableBlockPos rootMutable5 = new BlockPos.MutableBlockPos().set(mutablePos.offset(3, 0, 1));
            BlockPos.MutableBlockPos rootMutable6 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-2, 0, 2));
            BlockPos.MutableBlockPos rootMutable7 = new BlockPos.MutableBlockPos().set(mutablePos.offset(2, 0, 2));
            BlockPos.MutableBlockPos rootMutable8 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-1, 0, 3));

            //Stump
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 1, -2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 1, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, 1, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 1, 2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 2, -2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 2, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, 2, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 2, 2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 3, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 3, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 3, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 3, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight - 1, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight - 1, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight - 1, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight - 1, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, -2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, 2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 1, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 1, 1), mangroveTreeLog);

            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, -5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 1, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 1, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 1, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-7, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 1, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 1, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 2, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 2, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 2, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 2, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 2, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 2, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 2, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 2, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 2, 2), mangroveTreeLeaves);
        }
    }

    private void buildMangroveVariant4(DynamicTreeConfig config, WorldGenLevel level, Random random, BlockPos.MutableBlockPos mutablePos, BlockPos pos, BlockState mangroveTreeLog, BlockState mangroveTreeLeaves, BlockState mangroveTreeWood)
    {
        int mangroveHeight = config.minHeight() + random.nextInt(config.placement().height());

        if (pos.getY() + mangroveHeight + 1 < level.getMaxBuildHeight())
        {
            for (int buildTrunk = 3; buildTrunk <= mangroveHeight; buildTrunk++)
            {
                if (buildTrunk == 3)
                    mutablePos.move(Direction.UP, 3);
                placeTrunk(pos, random, level, mutablePos, mangroveTreeWood);

                mutablePos.move(Direction.UP);
            }
            mutablePos.set(pos);

            //Roots
            BlockPos.MutableBlockPos rootMutable = new BlockPos.MutableBlockPos().set(mutablePos.offset(1, 0, -3));
            BlockPos.MutableBlockPos rootMutable2 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-2, 0, -2));
            BlockPos.MutableBlockPos rootMutable3 = new BlockPos.MutableBlockPos().set(mutablePos.offset(2, 0, -2));
            BlockPos.MutableBlockPos rootMutable4 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-3, 0, -1));
            BlockPos.MutableBlockPos rootMutable5 = new BlockPos.MutableBlockPos().set(mutablePos.offset(3, 0, 1));
            BlockPos.MutableBlockPos rootMutable6 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-2, 0, 2));
            BlockPos.MutableBlockPos rootMutable7 = new BlockPos.MutableBlockPos().set(mutablePos.offset(2, 0, 2));
            BlockPos.MutableBlockPos rootMutable8 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-1, 0, 3));
            BlockPos.MutableBlockPos rootMutable9 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-1, 0, 3));
            BlockPos.MutableBlockPos rootMutable10 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-1, 0, 3));
            BlockPos.MutableBlockPos rootMutable11 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-1, 0, 3));
            BlockPos.MutableBlockPos rootMutable12 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-1, 0, 3));

            //Stump
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 1, -2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 1, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, 1, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 1, 2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 2, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 2, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 2, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 2, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 3, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 3, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 2, -2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 2, -2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 2, 2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 3, -3), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 3, 3), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 3, 4), mangroveTreeLog);

            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 2, -7), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 2, -6), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 2, -6), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, -6), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 2, -5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 2, -5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, -5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 2, -5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 2, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 2, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, mangroveHeight + 2, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 2, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 2, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 2, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, mangroveHeight + 2, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 2, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 2, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 2, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 2, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, mangroveHeight + 2, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, mangroveHeight + 2, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, mangroveHeight + 2, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 2, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 2, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, mangroveHeight + 2, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, mangroveHeight + 2, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 2, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 2, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 2, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-6, mangroveHeight + 2, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 2, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 2, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, mangroveHeight + 2, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 2, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 2, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 2, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 2, 6), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 2, 6), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 2, 6), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 3, -6), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 3, -5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 3, -5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 3, -5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 3, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 3, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 3, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 3, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 3, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 3, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 3, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 3, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 3, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 3, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 3, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 3, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 3, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 3, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 3, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 3, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 3, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 3, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 3, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 3, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 3, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 3, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 3, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 3, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 3, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 3, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 3, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 3, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 3, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 3, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, mangroveHeight + 3, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 3, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 3, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 3, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, mangroveHeight + 3, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 3, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 3, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 3, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, mangroveHeight + 3, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 3, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 3, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 3, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 3, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 3, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 3, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 3, 6), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 4, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 4, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 4, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 4, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 4, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 4, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 4, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 4, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 4, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 4, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 4, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 4, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 4, 4), mangroveTreeLeaves);
        }
    }

    private void buildMangroveVariant5(DynamicTreeConfig config, WorldGenLevel level, Random random, BlockPos.MutableBlockPos mutablePos, BlockPos pos, BlockState mangroveTreeLog, BlockState mangroveTreeLeaves, BlockState mangroveTreeWood)
    {
        int mangroveHeight = config.minHeight() + random.nextInt(config.placement().height());

        if (pos.getY() + mangroveHeight + 1 < level.getMaxBuildHeight())
        {
            for (int buildTrunk = 3; buildTrunk <= mangroveHeight; buildTrunk++)
            {
                if (buildTrunk == 3)
                    mutablePos.move(Direction.UP, 3);
                placeTrunk(pos, random, level, mutablePos, mangroveTreeWood);

                mutablePos.move(Direction.UP);
            }
            mutablePos.set(pos);

            //Roots
            BlockPos.MutableBlockPos rootMutable = new BlockPos.MutableBlockPos().set(mutablePos.offset(0, 0, -3));
            BlockPos.MutableBlockPos rootMutable2 = new BlockPos.MutableBlockPos().set(mutablePos.offset(-3, 0, 0));
            BlockPos.MutableBlockPos rootMutable3 = new BlockPos.MutableBlockPos().set(mutablePos.offset(3, 0, 0));
            BlockPos.MutableBlockPos rootMutable4 = new BlockPos.MutableBlockPos().set(mutablePos.offset(0, 0, 3));

            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 1, -2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 1, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, 1, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 1, 2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 2, -2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-2, 2, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, 2, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 2, 2), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 3, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, 3, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, 3, 0), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(0, 3, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, -1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 1, 1), mangroveTreeLog);
            placeBranch(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 2, 2), mangroveTreeLog);

            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, -4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 1, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-5, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 1, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-4, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 1, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 1, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 1, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 2, -3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 2, -2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-3, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 2, -1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-2, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, 0), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 2, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(-1, mangroveHeight + 2, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 2, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 2, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 2, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 2, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, mangroveHeight + 2, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(0, mangroveHeight + 2, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 2, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 2, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 2, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 2, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, mangroveHeight + 2, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(7, mangroveHeight + 2, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 2, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 2, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 2, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 2, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(6, mangroveHeight + 2, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 2, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 2, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 2, 5), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 3, 1), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 3, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 3, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 3, 2), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(1, mangroveHeight + 3, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(2, mangroveHeight + 3, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 3, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 3, 3), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(3, mangroveHeight + 3, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(4, mangroveHeight + 3, 4), mangroveTreeLeaves);
            placeLeaves(pos, random, level, mutablePos.set(pos).move(5, mangroveHeight + 3, 4), mangroveTreeLeaves);
        }
    }

    public boolean isSaltWater(LevelSimulatedReader level, BlockPos pos)
    {
        return level.isStateAtPosition(pos, (stateAt) -> stateAt.getMaterial() == TFCMaterials.SALT_WATER || stateAt.getBlock() == TFCBlocks.SALT_WATER.get());
    }

    public boolean canLogPlaceHere(LevelSimulatedReader level, BlockPos pos)
    {
        return level.isStateAtPosition(pos, (stateAt) -> stateAt.getMaterial() == Material.AIR || stateAt.getMaterial() == Material.WATER || EnvironmentHelpers.isWorldgenReplaceable(stateAt) || Helpers.isBlock(stateAt.getBlock(), BlockTags.LEAVES));
    }

    public void placeTrunk(BlockPos startPos, Random random, WorldGenLevel level, BlockPos pos, BlockState state)
    {
        pos = getTransformedPos(startPos, pos);
        if (canLogPlaceHere(level, pos) || isSaltWater(level, pos))
        {
            this.setFinalBlockState(level, pos, state);
        }
    }

    public void placeBranch(BlockPos startPos, Random random, WorldGenLevel level, BlockPos pos, BlockState state)
    {
        pos = getTransformedPos(startPos, pos);
        if (canLogPlaceHere(level, pos) || isSaltWater(level, pos))
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