package tfcflorae.mixin;

import java.util.Locale;
import java.util.Random;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.blocks.wood.ILeavesBlock;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateModel;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.chunkdata.ForestType;
import net.dries007.tfc.world.feature.tree.ForestConfig;
import net.dries007.tfc.world.feature.tree.ForestFeature;

import tfcflorae.common.TFCFTags;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.plant.TFCFPlant;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.util.TFCFHelpers;

@Mixin(ForestFeature.class)
public abstract class ForestFeatureMixin extends Feature<ForestConfig>
{
    public ForestFeatureMixin(Codec<ForestConfig> codec)
    {
        super(codec);
    }

    @Overwrite(remap = false)
    private void placeFallenTree(WorldGenLevel level, Random random, BlockPos chunkBlockPos, ForestConfig config, ChunkData data, BlockPos.MutableBlockPos mutablePos)
    {
        final int chunkX = chunkBlockPos.getX();
        final int chunkZ = chunkBlockPos.getZ();

        mutablePos.set(chunkX + random.nextInt(16), 0, chunkZ + random.nextInt(16));
        mutablePos.setY(level.getHeight(Heightmap.Types.OCEAN_FLOOR, mutablePos.getX(), mutablePos.getZ()));

        mutablePos.move(Direction.DOWN);
        BlockState downState = level.getBlockState(mutablePos);
        mutablePos.move(Direction.UP);
        if (Helpers.isBlock(downState, TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(downState, TFCTags.Blocks.SEA_BUSH_PLANTABLE_ON))
        {
            final ForestConfig.Entry entry = getTree(data, random, config, mutablePos);
            if (entry != null)
            {
                final int fallChance = entry.fallenChance();
                if (fallChance > 0 && level.getRandom().nextInt(fallChance) == 0)
                {
                    BlockState log = entry.fallenLog().orElse(null);
                    if (log != null)
                    {
                        final Direction axis = Direction.Plane.HORIZONTAL.getRandomDirection(random);

                        log = Helpers.setProperty(log, TFCBlockStateProperties.NATURAL, false);
                        log = Helpers.setProperty(log, BlockStateProperties.AXIS, axis.getAxis());

                        final int length = 4 + random.nextInt(10);
                        final BlockPos start = mutablePos.immutable();
                        final boolean[] moment = new boolean[length];

                        mutablePos.set(start);
                        int valid = 0;
                        for (; valid < length; valid++)
                        {
                            final BlockState replaceState = level.getBlockState(mutablePos);
                            if (EnvironmentHelpers.isWorldgenReplaceable(replaceState) || replaceState.getBlock() instanceof ILeavesBlock)
                            {
                                mutablePos.move(Direction.DOWN);
                                moment[valid] = level.getBlockState(mutablePos).isFaceSturdy(level, mutablePos, Direction.UP);
                            }
                            else
                            {
                                break;
                            }

                            mutablePos.move(Direction.UP);
                            mutablePos.move(axis);
                        }

                        int left = 0, right = valid - 1;
                        for (; left < moment.length; left++)
                        {
                            if (moment[left]) break;
                        }
                        for (; right >= 0; right--)
                        {
                            if (moment[right]) break;
                        }

                        if (left <= valid / 2 && right >= valid / 2 && valid >= 3)
                        {
                            // Balanced
                            mutablePos.set(start);
                            for (int i = 0; i < length; i++)
                            {
                                level.setBlock(mutablePos, log, Block.UPDATE_ALL);

                                BlockPos.MutableBlockPos mutablePosMycelium = new BlockPos.MutableBlockPos(mutablePos.getX(), mutablePos.getY(), mutablePos.getZ());

                                final ChunkDataProvider provider = ChunkDataProvider.get(level);
                                final ChunkData dataNew = provider.get(level, mutablePosMycelium);

                                final float rainfall = dataNew.getRainfall(mutablePosMycelium);
                                final float rainfallInverted = ((ClimateModel.MAXIMUM_RAINFALL - rainfall) + 10F) * 0.1F;

                                final float actualForestDensity = dataNew.getForestDensity();
                                final float forestDensity = actualForestDensity == 0 ? 0.001F : actualForestDensity; // Cannot divide by 0.

                                final ForestType forestType = data.getForestType();
                                final BlockState fungiState = Helpers.getRandomElement(ForgeRegistries.BLOCKS, TFCFTags.Blocks.FUNGI_ON_FALLEN_LOGS, random).map(Block::defaultBlockState).orElse(TFCFBlocks.PLANTS.get(TFCFPlant.PORCINI).get().defaultBlockState());

                                if (random.nextFloat(1F - actualForestDensity) <= forestType.ordinal())
                                {
                                    if (fungiState != null && fungiState.canSurvive(level, mutablePosMycelium.above()))
                                    {
                                        level.setBlock(mutablePosMycelium.above(), fungiState, Block.UPDATE_ALL);
                                    }
                                }

                                final int radius = Math.round((forestDensity * 1.5F) * forestType.ordinal());
                                if (radius > 0 && random.nextInt(Math.round(((rainfallInverted * 1.2F) / forestDensity) * (5 - forestType.ordinal()) * 0.75F)) <= forestType.ordinal())
                                {
                                    final int radiusSquared = radius * radius;

                                    final int posX = mutablePosMycelium.getX();
                                    final int posZ = mutablePosMycelium.getZ();
                                    final int posY = mutablePosMycelium.getY();

                                    for (int x = posX - radius; x <= posX + radius; ++x)
                                    {
                                        for (int z = posZ - radius; z <= posZ + radius; ++z)
                                        {
                                            final int relX = x - posX;
                                            final int relZ = z - posZ;

                                            if (relX * relX + relZ * relZ <= radiusSquared)
                                            {
                                                for (int y = posY - radius; y <= posY; ++y)
                                                {
                                                    mutablePosMycelium.set(x, y, z);

                                                    if (level.getBlockState(mutablePosMycelium.above()).isAir())
                                                    {
                                                        final BlockState stateAt = level.getBlockState(mutablePosMycelium);

                                                        if (Helpers.isBlock(stateAt, TFCFTags.Blocks.IS_BOG_IRON))
                                                        {
                                                            level.setBlock(mutablePosMycelium, TFCFBlocks.MYCELIUM_BOG_IRON.get().defaultBlockState(), Block.UPDATE_ALL);
                                                        }
                                                        else if (Helpers.isBlock(stateAt, TFCFTags.Blocks.IS_LOAM))
                                                        {
                                                            level.setBlock(mutablePosMycelium, TFCFBlocks.TFCSOIL.get(TFCFSoil.MYCELIUM_DIRT).get(SoilBlockType.Variant.LOAM).get().defaultBlockState(), Block.UPDATE_ALL);
                                                        }
                                                        else if (Helpers.isBlock(stateAt, TFCFTags.Blocks.IS_SANDY_LOAM))
                                                        {
                                                            level.setBlock(mutablePosMycelium, TFCFBlocks.TFCSOIL.get(TFCFSoil.MYCELIUM_DIRT).get(SoilBlockType.Variant.SANDY_LOAM).get().defaultBlockState(), Block.UPDATE_ALL);
                                                        }
                                                        else if (Helpers.isBlock(stateAt, TFCFTags.Blocks.IS_SILT))
                                                        {
                                                            level.setBlock(mutablePosMycelium, TFCFBlocks.TFCSOIL.get(TFCFSoil.MYCELIUM_DIRT).get(SoilBlockType.Variant.SILT).get().defaultBlockState(), Block.UPDATE_ALL);
                                                        }
                                                        else if (Helpers.isBlock(stateAt, TFCFTags.Blocks.IS_SILTY_LOAM))
                                                        {
                                                            level.setBlock(mutablePosMycelium, TFCFBlocks.TFCSOIL.get(TFCFSoil.MYCELIUM_DIRT).get(SoilBlockType.Variant.SILTY_LOAM).get().defaultBlockState(), Block.UPDATE_ALL);
                                                        }
                                                        else if (Helpers.isBlock(stateAt, TFCFTags.Blocks.IS_HUMUS))
                                                        {
                                                            level.setBlock(mutablePosMycelium, TFCFBlocks.TFCFSOIL.get(TFCFSoil.MYCELIUM_DIRT).get(TFCFSoil.TFCFVariant.HUMUS).get().defaultBlockState(), Block.UPDATE_ALL);
                                                        }

                                                        if (random.nextFloat(1F - actualForestDensity) <= forestType.ordinal())
                                                        {
                                                            if (fungiState != null && fungiState.canSurvive(level, mutablePosMycelium.above()))
                                                            {
                                                                level.setBlock(mutablePosMycelium.above(), fungiState, Block.UPDATE_ALL);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } 
                                }
                                mutablePos.move(axis);
                            }
                        }
                    }
                }
            }
        }
    }

    @Shadow
    @Nullable
    private ForestConfig.Entry getTree(ChunkData chunkData, Random random, ForestConfig config, BlockPos pos)
    {
        return null;
    }
}
