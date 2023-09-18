package tfcflorae.world.feature.tree;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.RiverWaterBlock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.mixin.accessor.StructureTemplateAccessor;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.feature.tree.RandomTreeConfig;
import net.dries007.tfc.world.feature.tree.TreeFeature;
import net.dries007.tfc.world.feature.tree.TreeHelpers;
import net.dries007.tfc.world.feature.tree.TreePlacementConfig;

public class RandomBeachTreeFeature extends TreeFeature<RandomTreeConfig>
{
    public RandomBeachTreeFeature(Codec<RandomTreeConfig> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<RandomTreeConfig> context)
    {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final var random = context.random();
        final RandomTreeConfig config = context.config();

        final ChunkPos chunkPos = new ChunkPos(pos);
        final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos().set(pos);
        final StructureManager manager = TreeHelpers.getStructureManager(level);
        final StructurePlaceSettings settings = TreeHelpers.getPlacementSettings(level, chunkPos, random);
        final ResourceLocation structureId = config.structureNames().get(random.nextInt(config.structureNames().size()));
        final StructureTemplate structure = manager.getOrCreate(structureId);
        if (((StructureTemplateAccessor) structure).accessor$getPalettes().isEmpty())
        {
            throw new IllegalStateException("Empty structure: " + structureId);
        }

        if (TreeHelpers.isValidLocation(level, pos, settings, config.placement()) || isValidGround(level, pos, settings, config.placement()))
        {
            config.trunk().ifPresent(trunk -> {
                final int height = TreeHelpers.placeTrunk(level, mutablePos, random, settings, trunk);
                mutablePos.move(0, height, 0);
            });

            TreeHelpers.placeTemplate(structure, settings, level, mutablePos.subtract(TreeHelpers.transformCenter(structure.getSize(), settings)));
            return true;
        }
        return false;
    }

    public static boolean isValidGround(LevelAccessor level, BlockPos pos, StructurePlaceSettings settings, TreePlacementConfig config)
    {
        final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (int x = (1 - config.width()) / 2; x <= config.width() / 2; x++)
        {
            for (int z = (1 - config.width()) / 2; z <= config.width() / 2; z++)
            {
                mutablePos.set(x, 0, z);
                TreeHelpers.transformMutable(mutablePos, settings.getMirror(), settings.getRotation());
                mutablePos.move(pos);

                if (!(config.allowDeeplySubmerged() ? isValidPositionPossiblyUnderwater(level, mutablePos) : isValidPosition(level, mutablePos, config)))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValidPosition(LevelAccessor level, BlockPos.MutableBlockPos mutablePos, TreePlacementConfig config)
    {
        final BlockState stateAt = level.getBlockState(mutablePos);
        final boolean isInWater = stateAt.getFluidState().getType() == Fluids.WATER;
        if (!(config.allowSubmerged() && FluidHelpers.isAirOrEmptyFluid(stateAt) && isInWater)
            && !stateAt.isAir()
            && !(stateAt.getBlock() instanceof SaplingBlock))
        {
            return false;
        }

        mutablePos.move(0, -1, 0);

        final BlockState stateBelow = level.getBlockState(mutablePos);
        boolean treeGrowsOn = Helpers.isBlock(stateBelow, TFCTags.Blocks.TREE_GROWS_ON) || Helpers.isBlock(stateBelow, BlockTags.SAND);
        if (config.allowSubmerged() && isInWater)
        {
            treeGrowsOn |= Helpers.isBlock(stateBelow, TFCTags.Blocks.SEA_BUSH_PLANTABLE_ON);
        }
        return treeGrowsOn;
    }

    public static boolean isValidPositionPossiblyUnderwater(LevelAccessor level, BlockPos.MutableBlockPos mutablePos)
    {
        final BlockState stateAt = level.getBlockState(mutablePos);
        final FluidState fluid = stateAt.getFluidState();
        if (!Helpers.isFluid(fluid, FluidTags.WATER) || stateAt.hasProperty(RiverWaterBlock.FLOW))
        {
            return false;
        }

        mutablePos.move(0, -1, 0);
        final BlockState stateBelow = level.getBlockState(mutablePos);
        return Helpers.isBlock(stateBelow, TFCTags.Blocks.SEA_BUSH_PLANTABLE_ON) && Helpers.isBlock(stateBelow, TFCTags.Blocks.TREE_GROWS_ON);
    }
}